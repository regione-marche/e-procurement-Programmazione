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
    AbilitazioniUtilsService,
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
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService,
    SdkModalConfig,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, isEmpty, isEqual, isObject } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { ProgrammaEntry } from '../../../../../models/programmi/programmi.model';
import { RisorsaCapitoloEntry } from '../../../../../models/risorse-capitolo/risorsa-capitolo.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';


@Component({
    templateUrl: `modifica-risorsa-di-capitolo-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaRisorsaDiCapitoloSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `modifica-risorsa-di-capitolo-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private idProgramma: string;
    private tipologia: string;
    private idIntervento: string;
    private idRisorsaDiCapitolo: string;
    private risorsa: RisorsaCapitoloEntry;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private userProfile: UserProfile;
    private programma: ProgrammaEntry;
    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private infoBoxModalConfig: IDictionary<any>;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        // set update state
        this.setUpdateState(true);

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onAfterViewInit(): void {

        let factory = this.dettaglioProgrammaFactory(this.idProgramma);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: ProgrammaEntry) => {
            this.programma = result;
            if (this.programma.idRicevuto) {
                this.setUpdateState(false);
                history.back();
            } else {
                this.checkInfoBox();
                this.loadRisorsaDiCapitolo();
            }
        });

    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.infoBoxModalConfig = this.config.infoBoxModalConfig;
                this.modalConfig = {
                    code: 'modal',
                    title: '',
                    openSubject: new Subject()
                };
                this.modalConfigObs.next(this.modalConfig);
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

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion


    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                idProgramma: this.idProgramma,
                idIntervento: this.idIntervento,
                idRisorsaDiCapitolo: this.idRisorsaDiCapitolo,
                tipologia: this.tipologia,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState
            };

            if (button.code === 'back-to-risorse-di-capitolo' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
                this.back(button, data);
            } else {
                this.provider.run(button.provider, data).subscribe();
            }
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputInfoBox(item: SdkFormBuilderField): void {
        if (isObject(this.infoBoxModalConfig) && isObject(item)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: get(this.infoBoxModalConfig, 'component'),
                componentConfig: {
                    fields: get(this.infoBoxModalConfig, 'fields'),
                    buttons: get(this.infoBoxModalConfig, 'buttons'),
                    userProfile: this.userProfile,
                    mnemonico: item.mnemonico
                }
            }
            this.modalConfigObs.next(this.modalConfig);
            setTimeout(() => this.modalConfig.openSubject.next(true));
        }
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
    }

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField>;

        if (this.tipologia === '1') {
            fields = get(this.config, 'body.lavoriFields')
        } else if (this.tipologia === '2') {
            fields = get(this.config, 'body.acquistiFields')
        }

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };


        let providerArgs: IDictionary<any> = {
            tipologia: this.tipologia
        };

        let infoBox: boolean = this.abilitazioniUtilsService.isAmministratore(this.userProfile.abilitazioni);

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, undefined, this.risorsa, providerArgs, infoBox);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;
        this.formBuilderConfigObs.next(formConfig);
    }


    private loadRisorsaDiCapitolo(): void {
        let factory = this.dettaglioRisorsaDiCapitoloFactory(this.idProgramma, this.idIntervento, this.idRisorsaDiCapitolo);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: RisorsaCapitoloEntry) => {
            this.risorsa = result;
            this.loadForm();
        });
    }

    private dettaglioRisorsaDiCapitoloFactory(idProgramma: string, idIntervento: string, idRisorsaDiCapitolo: string): () => Observable<RisorsaCapitoloEntry> {
        return () => {
            return this.programmiService.dettaglioRisorsaDiCapitolo(idProgramma, idIntervento, idRisorsaDiCapitolo)
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

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.BACK-TITLE'),
            message: this.translateService.instant('DIALOG.BACK-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs = of(this.dialogConfig);
    }

    private back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        let func = this.backConfirm(button, data);
        this.dialogConfig.open.next(func);
    }

    private backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.provider.run(button.provider, data).subscribe();
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion
}
