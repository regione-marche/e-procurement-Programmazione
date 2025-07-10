import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Inject,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    IDictionary,
    SDK_APP_CONFIG,
    SdkAppEnvConfig,
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
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../app-commons.constants';
import { CentroDiCostoEntry } from '../../../../models/archivio/archivio-centri-di-costo.models';
import { StazioneAppaltanteInfo } from '../../../../models/stazione-appaltante/stazione-appaltante.model';
import { ValoreTabellato } from '../../../../models/tabellati/tabellato.model';
import { ArchivioCentriDiCostoService } from '../../../../services/archivio/archivio-centri-di-costo.service';
import { HttpRequestHelper } from '../../../../services/http/http-request-helper.service';
import { TabellatiCacheService } from '../../../../services/tabellati/tabellati-cache.service';
import { AbilitazioniUtilsService } from '../../../../services/utils/abilitazioni-utils.service';
import { FormBuilderUtilsService } from '../../../../services/utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../../../services/utils/protection-utils.service';


@Component({
    templateUrl: `modifica-cdc-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaCdcSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `modifica-cdc-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private stazioneAppaltante: string;
    private idCentro: string;
    private centroDiCosto: CentroDiCostoEntry;
    private userProfile: UserProfile;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;

    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private infoBoxModalConfig: IDictionary<any>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        // set update state
        this.setUpdateState(true);

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltante = saInfo.codice;
        }));

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();

        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            map(this.loadFactory),
            mergeMap(this.loadCdc),
            map(this.elaborateCdc)
        ).subscribe();
    }

    protected onAfterViewInit(): void { }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.modalConfig = {
                    code: 'modal',
                    title: '',
                    openSubject: new Subject()
                };
                this.modalConfigObs.next(this.modalConfig);
                this.infoBoxModalConfig = this.config.infoBoxModalConfig;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get archivioCdcService(): ArchivioCentriDiCostoService { return this.injectable(ArchivioCentriDiCostoService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                stazioneAppaltante: this.stazioneAppaltante,
                currentCodice: this.idCentro,
                idCentro: this.idCentro
            };

            if (button.code === 'back-to-dettaglio-cdc' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
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

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let infoBox: boolean = this.abilitazioniUtilsService.isAmministratore(this.userProfile.abilitazioni);

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, undefined, this.centroDiCosto, undefined, infoBox);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;
        this.formBuilderConfigObs.next(formConfig);
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

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idCentro = paramsMap.get('idCentro');
    }

    private dettaglioCdcFactory(idCentro: string): () => Observable<CentroDiCostoEntry> {
        return () => {
            return this.archivioCdcService.getCdc(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, idCentro)
        }
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.CDC_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result
    }

    private loadFactory = (): Function => {
        let factory = this.dettaglioCdcFactory(this.idCentro);
        return factory;
    }

    private loadCdc = (factory: Function): Observable<CentroDiCostoEntry> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateCdc = (result: CentroDiCostoEntry) => {
        this.centroDiCosto = result;
        this.checkInfoBox();
        this.loadForm();
    };

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
