import {
    AfterViewInit,
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import {
    IDictionary,
    SdkAbstractComponent,
    SdkProviderService,
    SdkStoreService,
    SdkValidatorConfig,
    SdkValidatorService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkFormFieldGroupConfiguration,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, find, get, has, isEmpty, isObject, join, set } from 'lodash-es';
import { BehaviorSubject, Subject } from 'rxjs';

import { Constants } from '../../app-commons.constants';
import { StazioneAppaltanteInfo } from '../../models/stazione-appaltante/stazione-appaltante.model';
import { ValoreTabellato } from '../../models/tabellati/tabellato.model';
import { Tecnico } from '../../models/tecnico/tecnico.model';
import { TabellatiCacheService } from '../../services/tabellati/tabellati-cache.service';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';

@Component({
    templateUrl: `rup-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RupModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    public config: any;
    public data: void;
    private userProfile: UserProfile;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private selectedItem: SdkAutocompleteItem;
    private provinceMap: IDictionary<string> = {};

    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.tabellatiCacheService.getValoriTabellato(Constants.PROVINCE).subscribe((province: Array<ValoreTabellato>) => {
            each(province, (provincia: ValoreTabellato) => {
                this.provinceMap[provincia.codistat] = provincia.codice;
            });
        });

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.elaborateConfig();
    }

    protected onDestroy(): void { }

    protected onOutput(_data: void): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.selectedItem = this.config.selectedItem;
                this.isReady = true;
            });
        }
    }

    protected onData(_data: void): void { }

    /**
     * @ignore
     */
    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get validator(): SdkValidatorService { return this.injectable(SdkValidatorService) }

    protected get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: get(this.config, 'rupData.fields')
                };
                each(formConfig.fields, (one: SdkFormBuilderField) => {
                    if (one.type === 'FORM-SECTION') {
                        one = this.elaborateSection(one);
                    } else if (one.type === 'FORM-GROUP') {
                        one = this.elaborateGroup(one);
                    } else {
                        one = this.elaborateProvider(one);
                    }
                });

                let checkCampiSolaLetturaDaConfig: boolean = true;
                if (this.selectedItem == null || isEmpty(this.selectedItem)) {
                    checkCampiSolaLetturaDaConfig = false;
                }

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, true, checkCampiSolaLetturaDaConfig);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            });
        }
    }

    private elaborateProvider(field: SdkFormBuilderField): SdkFormBuilderField {
        if (!isEmpty(field.itemsProviderCode)) {
            field.itemsProvider = this.provider.run(field.itemsProviderCode);
        }

        if (!isEmpty(field.validators)) {
            each(field.validators, (one: any) => {
                if (isObject(one.config)) {
                    let config: SdkValidatorConfig = one.config;
                    if (get(config, 'required') === true) {
                        one.validator = this.validator.run('MANDATORY', one.messages);
                    } else if (!isEmpty(get(config, 'regexp'))) {
                        one.validator = this.validator.run('REGEXP', one.messages);
                    } else {
                        one.validator = this.validator.run(one.validator, one.messages);
                    }
                }
            });
        }
        if (isObject(this.selectedItem)) {
            if (field.type === 'AUTOCOMPLETE') {
                let restItem: string = get(this.selectedItem, field.mappingInput);
                let item: SdkAutocompleteItem = {
                    _key: restItem,
                    text: restItem
                };
                set(field, 'data', item);
            } else {
                set(field, 'data', get(this.selectedItem, field.mappingInput));
            }
        }

        return field;
    }

    private elaborateSection(field: SdkFormBuilderField): SdkFormBuilderField {
        each(field.fieldSections, (one: SdkFormBuilderField) => {
            if (one.type === 'FORM-SECTION') {
                one = this.elaborateSection(one);
            } else if (one.type === 'FORM-GROUP') {
                one = this.elaborateGroup(one);
            } else {
                one = this.elaborateProvider(one);
            }
        });
        return field;
    }

    private elaborateGroup(field: SdkFormBuilderField): SdkFormBuilderField {
        each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration, index: number) => {
            each(group.fields, (one: SdkFormBuilderField) => {
                if (one.type === 'FORM-SECTION') {
                    one = this.elaborateSection(one);
                } else if (one.type === 'FORM-GROUP') {
                    one = this.elaborateGroup(one);
                } else {
                    one = this.elaborateProvider(one);
                }
            });
            field.fieldGroups[index] = group;
        });
        return field;
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (has(obj, 'formBuilderConfig')) {
                let formBuilderConfig = get(obj, 'formBuilderConfig');
                this.formBuilderConfig = formBuilderConfig;
                this.formBuilderConfigObs.next(formBuilderConfig);
            } else if (has(obj, 'close') && get(obj, 'close') === true) {
                if (has(obj, 'data') && isObject(get(obj, 'data'))) {
                    let data: Tecnico = get(obj, 'data');
                    let text: string = data.nominativo == undefined? data.cf: `${data.nominativo} (${data.cf})`;
                    let item: SdkAutocompleteItem = {
                        ...data,
                        text: text,
                        _key: data.codice
                    };
                    this.emitOutput(item);
                } else {
                    this.emitOutput(undefined);
                }
            }
        }
    }

    private manageComune(field: SdkFormBuilderField): void {

        let capCode: string = 'cap';
        let provinciaCode: string = 'provincia';
        let istatCode: string = 'cod-istat';

        let cap: string = undefined;
        let codIstat: string = undefined;
        let codIstatProvincia: string = undefined;
        if (field.data) {
            codIstat = field.data.codistat;
            codIstatProvincia = `0${codIstat.substring(3, 6)}`;
            cap = field.data.cap;
        }

        let capField: any = {
            code: capCode,
            data: cap
        };

        let provinciaField: any = {
            code: provinciaCode,
            data: get(this.provinceMap, codIstatProvincia)
        };

        let codIstatField: any = {
            code: istatCode,
            data: codIstat
        };

        if (field.groupCode) {
            capField.groupCode = field.groupCode;
            provinciaField.groupCode = field.groupCode;
            codIstatField.groupCode = field.groupCode;
        }

        this.formBuilderDataSubject.next(capField);
        this.formBuilderDataSubject.next(provinciaField);
        this.formBuilderDataSubject.next(codIstatField);
    }

    private manageIntestazione(field: SdkFormBuilderField): void {

        const nomeCode: string = 'nome';
        const cognomeCode: string = 'cognome';
        const intestazioneCode: string = 'intestazione';
        let nomeValue: string = '';
        let cognomeValue: string = '';
        let intestazioneValue: string = '';

        if (field.code === nomeCode) {
            nomeValue = field.data;
            // recupero il valore del cognome
            const cognomeField: SdkFormBuilderField = this.getFieldByCode(cognomeCode);
            cognomeValue = cognomeField.data != null ? cognomeField.data.trim() : '';
        } else if (field.code === cognomeCode) {
            cognomeValue = field.data;
            // recupero il valore del nome
            const nomeField: SdkFormBuilderField = this.getFieldByCode(nomeCode);
            nomeValue = nomeField.data != null ? nomeField.data.trim() : '';
        }

        intestazioneValue = join([cognomeValue, nomeValue], ' ').trim();

        const toUpdate: SdkFormBuilderInput = {
            code: intestazioneCode,
            data: intestazioneValue
        };

        this.formBuilderDataSubject.next(toUpdate);

    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.rupData.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private getFieldByCode(fieldCode: string): SdkFormBuilderField {
        const section: SdkFormBuilderField = find(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === 'rup-data');
        const field: SdkFormBuilderField = find(section.fieldSections, (one: SdkFormBuilderField) => one.code === fieldCode);
        return field;
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                type: 'BUTTON',
                data: {
                    code: button.code,
                    defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                    formBuilderConfig: this.formBuilderConfig,
                    messagesPanel: this.messagesPanel,
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    selectedItem: this.selectedItem
                }
            }).subscribe(this.manageExecutionProvider)
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    public manageOutputField(field: SdkFormBuilderField): void {
        if (isObject(field)) {
            if (field.code === 'comune') {
                this.manageComune(field);
            } else if (field.code === 'nome' || field.code === 'cognome') {
                this.manageIntestazione(field);
            }
        }
    }

    // #endregion
}
