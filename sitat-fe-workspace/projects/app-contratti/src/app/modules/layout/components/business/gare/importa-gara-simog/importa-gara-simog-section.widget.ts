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
    CustomParamsFunction,
    FormBuilderUtilsService,
    HttpRequestHelper,
    ProtectionUtilsService,
    StazioneAppaltanteInfo,
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
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkModalOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, isEmpty, isObject, isString } from 'lodash-es';
import { Observable, Subject } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { MigrazioneGaraForm } from '../../../../../models/gare/gare.model';
import {
    BaseRupInfo,
    Collaborazione,
    ConsultaGaraOperations,
    PresaInCaricoGaraDelegataForm,
    SABaseEntry,
} from '../../../../../models/gare/importa-gara.model';
import { ImportaGaraService } from '../../../../../services/gare/importa-gara.service';
import { GareService } from 'projects/app-contratti/src/app/modules/services/gare/gare.service';

@Component({
    templateUrl: `importa-gara-simog-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ImportaGaraSimogSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `importa-gara-simog-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    public buttonsSubj: Subject<SdkButtonGroupInput> = new Subject();
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();

    private buttons: SdkButtonGroupInput;
    private buttonsAfterImport: SdkButtonGroupInput;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private userProfile: UserProfile;
    private codGara: string;
    private idAvGara: string;
    private isCigInDb: boolean;
    private origineSAInfo: SABaseEntry;
    private cfTecnico: string;
    private existSimogEndpoint: boolean = false;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigSub: Subject<SdkDialogConfig> = new Subject();

    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigSub: Subject<SdkModalConfig<any, void, any>> = new Subject();

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.setUpdateState(true);

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.loadExistSimogEndpoint();
        this.checkInfoUserLoa();

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

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get importaGaraService(): ImportaGaraService { return this.injectable(ImportaGaraService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    // #endregion

    // #region Private

    private loadExistSimogEndpoint(): void {
        let factory = this.getExistSimogEndpoint();
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: any) => {
            if (result.data === 'true') {
                this.existSimogEndpoint = true;
            }
            this.checkInfoBox();
            this.elaborateConfig();
        });
    }

    private getExistSimogEndpoint(): () => Observable<any> {
        return () => {
            return this.gareService.existSimogEndpoint();
        }
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {

                let formConfig: SdkFormBuilderConfiguration = {
                    fields: cloneDeep(get(this.config, 'body.fields'))
                };

                let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField) => {
                    let mapping: boolean = true;

                    if (field.code === 'cig-idavgara' && !this.existSimogEndpoint) {
                        field.label = 'IMPORTA-GARA.CARICA-LOTTO-SIMOG.CIG-IDAPPALTO';
                        mapping = false;
                    }

                    if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                        field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
                    }

                    return {
                        mapping,
                        field
                    };
                }

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, false, customPopulateFunction);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);

                this.buttonsSubj.next(this.buttons);
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

    private manageProviderExecution = (result: IDictionary<any>) => {
        if (result != null) {
            if (result.presaCarico === true) {
                this.initPresaInCaricoPcpDialog();
                this.presaInCaricoPcp();
            } else if (result.success === true) {
                this.codGara = result.codGara;
                this.idAvGara = result.idAvGara;
                this.isCigInDb = result.cigInDb;
                this.origineSAInfo = result.origineSAInfo;
                if (result.operation === ConsultaGaraOperations.OP_PRESA_IN_CARICO) {
                    const codGara: string = result.codGara;
                    const cfRup: string = result.cfRup;
                    const migraGaraForm: MigrazioneGaraForm = {
                        codGara: +codGara,
                        codiceFiscaleRupGara: cfRup,
                        stazioneAppaltante: this.stazioneAppaltanteInfo.codice
                    };
                    this.initPresaInCaricoDialog(this.origineSAInfo.nome);
                    this.migraGara(migraGaraForm);
                } else if (result.operation === ConsultaGaraOperations.OP_PRESA_IN_CARICO_DELEGA_AC || result.operation === ConsultaGaraOperations.OP_PRESA_IN_CARICO_DELEGA_U) {
                    // presa in carico gara delegata
                    if (result.operation === ConsultaGaraOperations.OP_PRESA_IN_CARICO_DELEGA_AC) {
                        // se utente A o C allora mostro il modale di scelta tecnico
                        const saInfo: SABaseEntry = result.origineSAInfo;
                        this.initListaTecniciModal(saInfo.listaRup);
                        this.modalConfig.openSubject.next(true);
                    } else {
                        // altrimenti sono U e quindi scelgo direttamente il tecnico indicato
                        const saInfo: SABaseEntry = result.origineSAInfo;
                        const tecnico: BaseRupInfo = saInfo.listaRup != null && saInfo.listaRup.length > 0 ? saInfo.listaRup[0] : null;
                        if (tecnico != null) {
                            // chiamo loginRPNT
                            this.executeLoginRPNT(tecnico.cf);
                        } else {
                            // messaggio
                        }
                    }
                } else {
                    if (!isEmpty(this.codGara)) {
                        this.buttonsSubj.next(this.buttonsAfterImport);
                    }
                }
            } else if (result.success === false) {
                this.formBuilderConfig = cloneDeep(this.defaultFormBuilderConfig);
                this.formBuilderConfigObs.next(this.formBuilderConfig);
            }
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsAfterImport = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsAfterImport, this.userProfile.configurations)
        };
    }

    private migraGara(migraGaraForm: MigrazioneGaraForm): void {
        this.sdkMessagePanelService.clear(this.messagesPanel);
        let func = this.migraGaraConfirm(migraGaraForm);
        this.dialogConfig.open.next(func);
    }

    private migraGaraConfirm(migraGaraForm: MigrazioneGaraForm): any {
        return () => {
            this.provider.run('APP_GARE_IMPORTA_GARA', {
                migraGaraForm,
                buttonCode: 'migra-gara',
                messagesPanel: this.messagesPanel
            }).subscribe(this.manageMigraGaraConfirm)
        }
    }

    private manageMigraGaraConfirm = (result: IDictionary<any>) => {
        if (result != null) {
            this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
                {
                    message: 'IMPORTA-GARA.PRESA-IN-CARICO-SUCCESS'
                }
            ])
            if (!isEmpty(this.codGara)) {
                this.buttonsSubj.next(this.buttonsAfterImport);
            }
        } else {
            this.formBuilderConfig = cloneDeep(this.defaultFormBuilderConfig);
            this.formBuilderConfigObs.next(this.formBuilderConfig);
        }
    }

    private presaInCaricoPcp(): void {
        this.sdkMessagePanelService.clear(this.messagesPanel);
        let func = this.presaInCaricoPcpConfirm();
        this.dialogConfig.open.next(func);
    }

    private presaInCaricoPcpConfirm(): any {
        return () => {
           this.provider.run('APP_GARE_IMPORTA_GARA', {
                buttonCode: 'importa-gara',
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                stazioneAppaltante: this.stazioneAppaltanteInfo,
                userProfile: this.userProfile,
                codGara: this.codGara,
                presaCarico: true
            }).subscribe(this.manageProviderExecution);
        }
    }

    private initPresaInCaricoPcpDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.PRESA-IN-CARICO-TITLE'),
            message: this.translateService.instant('DIALOG.PRESA-IN-CARICO-PCP-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigSub.next(this.dialogConfig);
    }

    private initPresaInCaricoDialog(nomeSA: string): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.PRESA-IN-CARICO-TITLE'),
            message: this.translateService.instant('DIALOG.PRESA-IN-CARICO-TEXT', { nomeSA }),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigSub.next(this.dialogConfig);
    }

    private initPresaInCaricoGaraDelegataDialog(nomeSA: string, nomeUfficio: string): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.PRESA-IN-CARICO-GARA-DELEGATA-TITLE'),
            message: this.translateService.instant('DIALOG.PRESA-IN-CARICO-GARA-DELEGATA-TEXT', { nomeSA, nomeUfficio }),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigSub.next(this.dialogConfig);
    }

    private initListaTecniciModal(listaTecnici: Array<BaseRupInfo>) {
        this.modalConfig = {
            code: 'tecnico',
            title: '',
            openSubject: new Subject(),
            component: 'lista-tecnici-modal-widget',
            componentConfig: {
                listaTecniciData: this.config.body.listaTecniciData,
                listaTecnici
            }
        };
        this.modalConfigSub.next(this.modalConfig);
    }

    private executeLoginRPNT(cfTecnico: string): void {
        const factory = this.getListaCollaborazioniFactory(cfTecnico);
        this.requestHelper.begin(factory, this.messagesPanel)
            .subscribe((result: Array<Collaborazione>) => {
                if (result != null) {
                    this.initListaCollaborazioniModal(result);
                    this.modalConfig.openSubject.next(true);
                }
            });
    }

    private getListaCollaborazioniFactory(cfTecnico: string) {
        return () => {
            return this.importaGaraService.getListaCollaborazioni(cfTecnico);
        }
    }

    private initListaCollaborazioniModal(listaCollaborazioni: Array<Collaborazione>) {
        this.modalConfig = {
            code: 'collaborazione',
            title: '',
            openSubject: new Subject(),
            component: 'lista-collaborazioni-modal-widget',
            componentConfig: {
                listaCollaborazioniData: this.config.body.listaCollaborazioniData,
                listaCollaborazioni
            }
        };
        this.modalConfigSub.next(this.modalConfig);
    }

    private showPresaInCaricoGaraDelegataDialog(collaborazione: Collaborazione): void {
        this.sdkMessagePanelService.clear(this.messagesPanel);
        let func = this.presaInCaricoGaraDelegataConfirm(collaborazione);
        this.dialogConfig.open.next(func);
    }

    private presaInCaricoGaraDelegataConfirm(collaborazione: Collaborazione) {
        return () => {
            const presaInCaricoGaraDelegataForm: PresaInCaricoGaraDelegataForm = {
                cfRup: this.cfTecnico,
                idAvGara: this.idAvGara,
                indexCollaborazione: collaborazione.index
            };
            this.provider.run('APP_GARE_IMPORTA_GARA', {
                presaInCaricoGaraDelegataForm,
                buttonCode: 'presa-in-carico-gara-delegata',
                messagesPanel: this.messagesPanel
            }).subscribe(this.managePresaInCaricoGaraDelegataConfirm)
        }
    }

    private managePresaInCaricoGaraDelegataConfirm = (result: IDictionary<any>) => {
        if (result != null) {
            if (this.isCigInDb === true) {
                // presa in carico
                this.sdkMessagePanelService.clear(this.messagesPanel);
                const migraGaraForm: MigrazioneGaraForm = {
                    codGara: +this.codGara,
                    codiceFiscaleRupGara: this.cfTecnico,
                    stazioneAppaltante: this.stazioneAppaltanteInfo.codice
                };
                this.clearLocalVariables(false);
                this.provider.run('APP_GARE_IMPORTA_GARA', {
                    migraGaraForm,
                    buttonCode: 'migra-gara',
                    messagesPanel: this.messagesPanel
                }).subscribe(this.manageMigraGaraConfirm)
            } else {
                // consulta gara
                this.clearLocalVariables();
                this.consultaGara();
            }
        }
    }

    private consultaGara(): void {
        this.sdkMessagePanelService.clear(this.messagesPanel);
        this.provider.run('APP_GARE_IMPORTA_GARA', {
            buttonCode: 'importa-gara',
            messagesPanel: this.messagesPanel,
            defaultFormBuilderConfig: this.defaultFormBuilderConfig,
            formBuilderConfig: this.formBuilderConfig,
            setUpdateState: this.setUpdateState,
            stazioneAppaltante: this.stazioneAppaltanteInfo,
            userProfile: this.userProfile,
            codGara: this.codGara
        }).subscribe(this.manageProviderExecution);
    }

    private clearLocalVariables(deleteCodGara: boolean = true): void {
        if (deleteCodGara === true) {
            delete this.codGara;
        }
        delete this.cfTecnico;
        delete this.isCigInDb;
        delete this.origineSAInfo;
        delete this.idAvGara;
    }

    // #endregion

    // #region Public

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {

            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                stazioneAppaltante: this.stazioneAppaltanteInfo,
                userProfile: this.userProfile,
                codGara: this.codGara,
                presaCarico:false
            }).subscribe(this.manageProviderExecution);
        }
    }

    public manageModalOutput(item: SdkModalOutput<any>): void {
        if (item != null) {
            if (item.code === 'tecnico' && item.data != null) {
                const cfTecnico: string = item.data.data;
                this.cfTecnico = cfTecnico;
                // chiamo loginRPNT
                this.executeLoginRPNT(cfTecnico);
            } else if (item.code === 'collaborazione' && item.data != null) {
                const collaborazione: Collaborazione = item.data.data;
                // mostro dialog di conferma
                this.initPresaInCaricoGaraDelegataDialog(this.origineSAInfo.nome, collaborazione.ufficioDenominazione);
                this.showPresaInCaricoGaraDelegataDialog(collaborazione);
            }
        }
    }

    private checkInfoUserLoa(): void {
        if (this.messagesPanel != undefined) {
            this.sdkMessagePanelService.clear(this.messagesPanel);
            if (isEmpty(this.userProfile.loa) || (!isEmpty(this.userProfile.loa) && +this.userProfile.loa < 3)) {
                this.sdkMessagePanelService.showWarning(this.messagesPanel, [
                    {
                        message: 'DIALOG.INSUFFICIENT-LOA'
                    }
                ]);
            }
        }
    }

    // #endregion
}
