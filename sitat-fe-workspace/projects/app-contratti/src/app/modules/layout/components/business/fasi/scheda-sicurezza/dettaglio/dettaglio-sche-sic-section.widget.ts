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
    ResponseResult,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBreadcrumbsMessageService,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkBasicButtonInput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMenuTab,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, filter, get, has, isEmpty, isEqual, isObject, isString, remove, toString } from 'lodash-es';
import { Observable, of, Subject, throwError } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { LottoEntry } from '../../../../../../models/gare/gare.model';
import { SchedaSicurezzaEntry } from '../../../../../../models/scheda-sicurezza/scheda-sicurezza.model';
import { GareService } from '../../../../../../services/gare/gare.service';
import { SchedaSicurezzaService } from '../../../../../../services/scheda-sicurezza/scheda-sicurezza.service';
import { DettaglioFaseStoreService } from '../../dettaglio-fase-store.service';


@Component({
    templateUrl: `dettaglio-sche-sic-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioSchedaSicurezzaSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-scheda-sicurezza-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    private buttonsWithoutDettaglio: SdkButtonGroupInput;
    private buttonsErr: SdkButtonGroupInput;
    private buttonsLS: SdkButtonGroupInput;
    public buttonsSubj: Subject<SdkButtonGroupInput> = new Subject();
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private userProfile: UserProfile;
    private codGara: string;
    private codLotto: string;
    private codiceFase: string;
    private numeroProgressivo: string;
    private lotto: LottoEntry;
    public schedaSicurezza: SchedaSicurezzaEntry;
    private menuTabs: Array<SdkMenuTab>;
    private menuTabsLS: Array<SdkMenuTab>;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    private current: boolean = true;
    private riaggiudicazione: boolean = false;
    private cigFiglio: boolean = false;
    private fromLS: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        if (this.dettaglioFaseStoreService.current != null) {
            this.current = this.dettaglioFaseStoreService.current;
        }

        if (this.dettaglioFaseStoreService.riaggiudicazione != null) {
            this.riaggiudicazione = this.dettaglioFaseStoreService.riaggiudicazione;
        }

        if (this.dettaglioFaseStoreService.cigFiglio != null) {
            this.cigFiglio = this.dettaglioFaseStoreService.cigFiglio;
        }

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.loadLotto().pipe(
            map(this.elaborateLotto),
            map(() => this.refreshTabs()),
            map(() => this.checkInfoBox()),
            mergeMap(this.loadSchedaSicurezza),
            map(this.elaborateSchedaSicurezza),
            map(() => this.refreshBreadcrumb()),
            catchError(this.handleError)
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
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

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get schedaSicurezzaService(): SchedaSicurezzaService { return this.injectable(SchedaSicurezzaService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get dettaglioFaseStoreService(): DettaglioFaseStoreService { return this.injectable(DettaglioFaseStoreService) }

    protected get breadcrumbs(): SdkBreadcrumbsMessageService { return this.injectable(SdkBreadcrumbsMessageService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.executeButtonProvider(button);
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

    private executeButtonProvider(button: SdkButtonGroupOutput): void {
        if (isObject(button)) {
            if (button.code === 'delete-scheda-sicurezza') {
                this.deleteScheda(button);
            } else {
                this.provider.run(button.provider, {
                    buttonCode: button.code,
                    messagesPanel: this.messagesPanel,
                    defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                    formBuilderConfig: this.formBuilderConfig,
                    codGara: this.codGara,
                    codLotto: this.codLotto,
                    codiceFase: this.codiceFase,
                    numeroProgressivo: this.numeroProgressivo,
                    riaggiudicazione: this.riaggiudicazione
                }).subscribe();
            }
        }
    }

    private deleteScheda(button: SdkButtonGroupOutput): void {
        let func = this.deleteSchedaConfirm(button);
        this.dialogConfig.open.next(func);
    }

    private deleteSchedaConfirm(button: SdkButtonGroupOutput): any {
        return () => {
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                codGara: this.codGara,
                codLotto: this.codLotto,
                codiceFase: this.codiceFase,
                numeroProgressivo: this.numeroProgressivo,
                riaggiudicazione: this.riaggiudicazione
            }).subscribe((data: IDictionary<any>) => {
                if (has(data, 'reload') && get(data, 'reload') === true) {
                    this.reloadDettaglio();
                }
            });
        }
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.codiceFase = paramsMap.get('codiceFase');
        this.numeroProgressivo = paramsMap.get('numeroProgressivo');
        this.fromLS = paramsMap.get('fromLS');
    }

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: SchedaSicurezzaEntry, dynamicField: boolean) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);

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

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.schedaSicurezza);

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

    private dettaglioLottoFactory(codGara: string, codLotto: string): () => Observable<LottoEntry> {
        return () => {
            return this.gareService.dettaglioLotto(codGara, codLotto);
        }
    }

    private loadLotto(): Observable<LottoEntry> {
        let factory = this.dettaglioLottoFactory(this.codGara, this.codLotto);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateLotto = (result: LottoEntry) => {
        this.lotto = result;
    };

    private schedaSicurezzaFactory(codGara: string, codLotto: string, numeroProgressivo: string): () => Observable<ResponseResult<SchedaSicurezzaEntry>> {
        return () => {
            return this.schedaSicurezzaService.getSchedaSicurezza(codGara, codLotto, numeroProgressivo);
        }
    }

    private loadSchedaSicurezza = (): Observable<ResponseResult<SchedaSicurezzaEntry>> => {
        let factory = this.schedaSicurezzaFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateSchedaSicurezza = (result: ResponseResult<SchedaSicurezzaEntry>) => {
        this.markForCheck(() => {
            if (this.fromLS !== null && isEqual(this.fromLS, 'LS')) {
                this.buttonsSubj.next(this.buttonsLS);
            }
            else {
                if (isObject(result.data)) {
                    this.buttonsSubj.next(this.buttons);
                    this.schedaSicurezza = result.data;
                    setTimeout(() => this.loadForm());
                } else {
                    this.buttonsSubj.next(this.buttonsWithoutDettaglio);
                }
            }
        });
    }

    private refreshTabs(): void {
        const isLS = this.fromLS !== null && isEqual(this.fromLS, 'LS');
        const tabsToFilter = isLS ? this.config.menuTabsLS : this.menuTabs;

        const filteredTabs = tabsToFilter.filter(tab => {
            if (!isEmpty(tab.oggettoProtezione)) {
                const visible = this.protectionUtilsService.isMenuTabVisible(
                    tab.oggettoProtezione, 
                    this.userProfile.configurations
                );
                if (!visible) {
                    return false;
                }
            }
    
            if (!isEmpty(tab.visible)) {
                let visible: boolean = this.provider.run(tab.visible, { lotto: this.lotto });
                return visible === false;
            }
    
            return true;
        });

        this.sdkLayoutMenuTabs.emit(filteredTabs);
        if (isLS) {
            this.menuTabsLS = filteredTabs;
        } else {
            this.menuTabs = filteredTabs;
        }
    }

    private handleError = (err: any) => {
        this.buttonsSubj.next(this.buttonsErr);
        return throwError(() => err);
    }

    private reloadDettaglio(): void {
        this.markForCheck(() => delete this.schedaSicurezza);
        this.loadSchedaSicurezza().pipe(
            map(this.elaborateSchedaSicurezza),
            catchError(this.handleError)
        ).subscribe();
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.DELETE-TITLE'),
            message: this.translateService.instant('DIALOG.DELETE-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs = of(this.dialogConfig);
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsWithoutDettaglio = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsWithoutDettaglio, this.userProfile.configurations)
        };
        this.buttonsErr = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsErr, this.userProfile.configurations)
        };
        this.buttonsLS = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsLS, this.userProfile.configurations)
        }

        if (this.current === false || this.cigFiglio === true) {
            this.buttons.buttons = filter(this.buttons.buttons, (one: SdkBasicButtonInput) => (
                one.code !== 'delete-scheda-sicurezza' &&
                one.code !== 'go-to-update-scheda-sicurezza' &&
                one.code !== 'go-to-nuova-scheda-sicurezza'
            ));
            this.buttonsWithoutDettaglio.buttons = filter(this.buttonsWithoutDettaglio.buttons, (one: SdkBasicButtonInput) => (
                one.code !== 'delete-scheda-sicurezza' &&
                one.code !== 'go-to-update-scheda-sicurezza' &&
                one.code !== 'go-to-nuova-scheda-sicurezza'
            ));
            this.buttonsErr.buttons = filter(this.buttonsErr.buttons, (one: SdkBasicButtonInput) => (
                one.code !== 'delete-scheda-sicurezza' &&
                one.code !== 'go-to-update-scheda-sicurezza' &&
                one.code !== 'go-to-nuova-scheda-sicurezza'
            ));
        }
    }

    private refreshBreadcrumb = () => {
        if (this.fromLS !== null && isEqual(this.fromLS, 'LS')) {
            this.breadcrumbs.emit(this.config.breadcrumbsLS);
        }
        else {
            this.breadcrumbs.emit(this.config.breadcrumbs);
        }
    }

    // #endregion
}
