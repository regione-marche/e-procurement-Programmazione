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
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    CustomParamsFunction,
    FormBuilderUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isObject, isString, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { ProgrammaEntry } from '../../../../../models/programmi/programmi.model';
import { RisorsaCapitoloEntry } from '../../../../../models/risorse-capitolo/risorsa-capitolo.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';
import { DettaglioRisorsaDiCapitoloStore } from '../dettaglio-risorsa-di-capitolo-store.service';


@Component({
    templateUrl: `dettaglio-risorsa-di-capitolo-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioRisorsaDiCapitoloSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-risorsa-di-capitolo-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private idProgramma: string;
    private tipologia: string;
    private idIntervento: string;
    private idRisorsaDiCapitolo: string;
    private buttons: SdkButtonGroupInput;
    private buttonsReadonly: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private risorsa: RisorsaCapitoloEntry;
    private userProfile: UserProfile;
    private programma: ProgrammaEntry;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQueryParams();

        let factory = this.dettaglioProgrammaFactory(this.idProgramma);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: ProgrammaEntry) => {
            this.programma = result;
            let factory2 = this.dettaglioRisorsaFactory(this.idProgramma, this.idIntervento, this.idRisorsaDiCapitolo);
            this.requestHelper.begin(factory2, this.messagesPanel).subscribe((result: RisorsaCapitoloEntry) => {
                this.risorsa = result;
                this.elaborateConfig();
            });
        });


    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get dettaglioRisorsaDiCapitoloStore(): DettaglioRisorsaDiCapitoloStore { return this.injectable(DettaglioRisorsaDiCapitoloStore) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                idProgramma: this.idProgramma,
                idIntervento: this.idIntervento,
                tipologia: this.tipologia,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                idRisorsaDiCapitolo: this.idRisorsaDiCapitolo,
                setUpdateState: this.setUpdateState
            }).subscribe();
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idProgramma = paramsMap.get('idProgramma');
        this.tipologia = paramsMap.get('tipologia');
        this.idIntervento = paramsMap.get('idIntervento');
        this.idRisorsaDiCapitolo = paramsMap.get('idRisorsaDiCapitolo');
        this.dettaglioRisorsaDiCapitoloStore.idProgramma = this.idProgramma;
        this.dettaglioRisorsaDiCapitoloStore.tipologia = this.tipologia;
        this.dettaglioRisorsaDiCapitoloStore.idIntervento = this.idIntervento;
        this.dettaglioRisorsaDiCapitoloStore.idRisorsaDiCapitolo = this.idRisorsaDiCapitolo;
    }

    private dettaglioRisorsaFactory(idProgramma: string, idIntervento: string, idRisorsaDiCapitolo: string): () => Observable<RisorsaCapitoloEntry> {
        return () => {
            return this.programmiService.dettaglioRisorsaDiCapitolo(idProgramma, idIntervento, idRisorsaDiCapitolo);
        }
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                this.initButtons();

                let fields: Array<SdkFormBuilderField>;
                if (this.tipologia === '1') {
                    fields = get(this.config, 'body.lavoriFields');
                } else if (this.tipologia === '2') {
                    fields = get(this.config, 'body.acquistiFields');
                }

                let formConfig: SdkFormBuilderConfiguration = {
                    fields
                };

                let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
                    let mapping: boolean = true;

                    let keyAny: any = get(restObject, field.mappingInput);
                    let key: string = toString(keyAny);

                    if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                        [field, mapping] = this.formBuilderUtilsService.populateListCode({}, field, key, mapping);
                    }

                    if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                        field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
                    }

                    return {
                        mapping,
                        field
                    };
                }

                let providerArgs: IDictionary<any> = {
                    tipologia: this.tipologia
                };

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.risorsa, providerArgs);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            });
        }
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

    private dettaglioProgrammaFactory(idProgramma: string): () => Observable<ProgrammaEntry> {
        return () => {
            return this.programmiService.dettaglioProgramma(idProgramma);
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsReadonly = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.readOnlyButtons, this.userProfile.configurations)
        };
    }

    private initButtons(): void {
        if (this.programma.idRicevuto) {
            this.buttonsSubj.next(this.buttonsReadonly);
        } else {
            this.buttonsSubj.next(this.buttons);
        }
    }

    // #endregion
}
