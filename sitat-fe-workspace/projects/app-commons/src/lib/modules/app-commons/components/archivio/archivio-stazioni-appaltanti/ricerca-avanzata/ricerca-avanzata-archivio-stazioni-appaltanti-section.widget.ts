import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, get, has, isEmpty, isObject, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../app-commons.constants';
import {
    RicercaAvanzataArchivioStazioniAppaltantiForm,
} from '../../../../models/stazione-appaltante/stazione-appaltante.model';
import { ValoreTabellato } from '../../../../models/tabellati/tabellato.model';
import { TabellatiCacheService } from '../../../../services/tabellati/tabellati-cache.service';
import { CustomParamsFunction, FormBuilderUtilsService } from '../../../../services/utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../../../services/utils/protection-utils.service';

@Component({
    templateUrl: `ricerca-avanzata-archivio-stazioni-appaltanti-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RicercaAvanzataArchivioStazioniAppaltantiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `ricerca-avanzata-archivio-stazioni-appaltanti-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();

    private buttons: SdkButtonGroupInput;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private form: RicercaAvanzataArchivioStazioniAppaltantiForm;
    private userProfile: UserProfile;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private provinceMap: IDictionary<ValoreTabellato> = {};
    private comuniMap: IDictionary<ValoreTabellato> = {};

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SEARCH_FORM_ARCHIVIO_STAZIONI_APPALTANTI_SELECT).subscribe((form: RicercaAvanzataArchivioStazioniAppaltantiForm) => {
            this.form = form;
        }));

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            mergeMap(this.loadProvince),
            map(this.elaborateProvince),
            mergeMap(this.loadComuni),
            map(this.elaborateComuni),
            map(() => this.checkInfoBox()),
            map(() => this.loadForm())
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Public

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig
            }).subscribe(this.manageExecutionProvider);
        }
    }

    // #endregion

    // #region Private

    private loadForm = () => {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: cloneDeep(get(this.config, 'body.fields'))
                };

                let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
                    let mapping: boolean = true;

                    let keyAny: any = get(restObject, field.mappingInput);
                    let key: string = dynamicField === true ? field.data : toString(keyAny);

                    if (field.code === 'comune' && field.type === 'AUTOCOMPLETE') {
                        if (has(this.comuniMap, get(restObject, field.mappingInput))) {
                            const comune: ValoreTabellato = get(this.comuniMap, get(restObject, field.mappingInput));
                            const istatProvincia: string = `0${comune.codistat.substring(3, 6)}`;
                            let item: SdkAutocompleteItem = {
                                ...comune,
                                text: `${comune.descrizione} - ${this.provinceMap[istatProvincia].descrizione} (${comune.codistat})`,
                                _key: comune.codistat
                            };
                            set(field, 'data', item);
                            mapping = false;
                        }
                    }

                    if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                        [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
                    }

                    return {
                        mapping,
                        field
                    };
                }

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, false, false, false);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.form, {
                    provinceMap: this.provinceMap,
                    advancedSearch: true
                });

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;

                this.formBuilderConfigObs.next(formConfig);
            });

        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.cleanSearch === true) {
            this.loadForm();
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private get infoBox(): HTMLElement {
        return isObject(this._infoBox) ? this._infoBox.nativeElement : undefined;
    }

    private checkInfoBox(): void {
        if (!isEmpty(this.config.infoBox)) {
            this.sdkMessagePanelService.clear(this.infoBox);
            this.sdkMessagePanelService.showInfoBox(this.infoBox, {
                message: this.config.infoBox
            });
        }
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.RICERCA_AVANZATA_ARCHIVIO_STAZIONI_APPALTANTI_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private loadProvince = (): Observable<Array<ValoreTabellato>> => {
        return this.tabellatiCacheService.getValoriTabellato(Constants.PROVINCE);
    }

    private elaborateProvince = (province: Array<ValoreTabellato>) => {
        each(province, (provincia: ValoreTabellato) => {
            this.provinceMap[provincia.codistat] = provincia;
        });
    }

    private loadComuni = (): Observable<Array<ValoreTabellato>> => {
        return this.tabellatiCacheService.getValoriTabellato(Constants.COMUNI);
    }

    private elaborateComuni = (comuni: Array<ValoreTabellato>) => {
        each(comuni, (comune: ValoreTabellato) => {
            this.comuniMap[comune.codistat] = comune;
        });
    }

    // #endregion

}
