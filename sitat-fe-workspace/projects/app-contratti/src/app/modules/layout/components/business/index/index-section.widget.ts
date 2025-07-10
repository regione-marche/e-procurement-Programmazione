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
import { HomePageCard, HttpRequestHelper, ProtectionUtilsService } from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkRedirectService,
    SdkRouterService,
    SdkStoreAction,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import { SdkDialogConfig, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { isEmpty, isObject, remove } from 'lodash-es';

import { environment } from '../../../../../../environments/environment';
import { Constants, TEnvs } from '../../../../../app.constants';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { TranslateService } from '@ngx-translate/core';
import { AttiGeneraliService } from '../../../../services/atti-generali/atti-generali.service';
import { ResponseResult } from '../../../../../../../../app-commons/src/lib/modules/app-commons/models/common/common.model';
import { DettaglioImpresaAggiudicatariaStoreService } from '../fasi/imprese-aggiudicatarie/dettaglio-impresa-aggiudicataria-store.service';
import { DettaglioFaseStoreService } from '../fasi/dettaglio-fase-store.service';
import { DettaglioLottoStoreService } from '../lotti/dettaglio-lotto-store.service';
import { DettaglioImpresaInvitataPartecipanteStoreService } from '../fasi/elenco-impr-inv-parte/dettaglio-impr-inv-parte-store.service';

@Component({
    templateUrl: `index-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class IndexSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `index-section`;

    @ViewChild('infoBox') _infoBox: ElementRef;

    @ViewChild('messages') public _messagesPanel: ElementRef;

    public config: any = {};

    public dialogConfigObs: BehaviorSubject<SdkDialogConfig> = new BehaviorSubject(null);
    private dialogConfig: SdkDialogConfig;

    public gareCards: Array<HomePageCard> = new Array();
    public attiGeneraliCards: Array<HomePageCard> = new Array();
    public isTest: boolean = environment.ENV as TEnvs != 'PRODUCTION' && environment.ENV as TEnvs != 'DEVELOPMENT-PRODUCTION';

    private userProfile: UserProfile;
    private urlGeneratoAnac: string;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.clearSearches();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.loadCards();
        this.checkInfoBox();
        this.checkMessages();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true;
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get attiGeneraliService(): AttiGeneraliService { return this.injectable(AttiGeneraliService) }

    private get dettaglioImpresaAggiudicatariaStoreService(): DettaglioImpresaAggiudicatariaStoreService { return this.injectable(DettaglioImpresaAggiudicatariaStoreService) }

    private get dettaglioFaseStoreService(): DettaglioFaseStoreService { return this.injectable(DettaglioFaseStoreService) }

    private get dettaglioLottoStoreService(): DettaglioLottoStoreService { return this.injectable(DettaglioLottoStoreService) }

    private get dettaglioImprInvParteStoreService(): DettaglioImpresaInvitataPartecipanteStoreService { return this.injectable(DettaglioImpresaInvitataPartecipanteStoreService) }
    
    // #endregion

    // #region Public

    public getIcons(card: HomePageCard): Array<string> {
        let classes: Array<string> = [];

        if (isObject(card) && !isEmpty(card.icon)) {
            classes.push(card.icon);
        }

        return classes;
    }

    public manageCardClick(card: HomePageCard): void {
        if (isObject(card)) {
            if (!isEmpty(card.slug)) {
                let params: IDictionary<string> = null;
                if (isObject(card.params)) {
                    params = card.params
                }
                if (card.slug === 'lista-gare-page') {
                    this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_GARE_DISPATCHER, undefined));
                }
                else if(card.slug === 'ricerca-schede-trasmesse-pcp-page') {
                    this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_RICERCHE_SCHEDE_TRASMESSE_PCP_DISPATCHER, undefined));
                }

                this.routerService.navigateToPage(card.slug, params);
            } else if (!isEmpty(card.url)) {
                this.sdkRedirectService.redirect(card.url);
            }
            else if (!isEmpty(card.code)) {
                if(card.code === 'genera-url-anac') {
                    this.generaUrlAnac().pipe(
                        map(this.elaborateUrlGeneratoAnac)
                    ).subscribe();
                }
            }
        }
    }

    // #endregion

    // #region Private

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.URL-PER-ANAC-HEADER'),
            message: this.translateService.instant('DIALOG.URL-PER-ANAC-MESSAGE'),
            multiPurposeString: this.urlGeneratoAnac,
            rejectLabel: this.translateService.instant('DIALOG.URL-PER-ANAC-CLOSE'),
            open: new Subject()
        };
        this.dialogConfigObs.next(this.dialogConfig);
    }

    private generaUrlDialog(): void {
        const func = this.placeholderFunction();
        this.dialogConfig.open.next(func);
    }

    private placeholderFunction(): any {
        return () => {
            
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

    private loadCards(): void {
        this.markForCheck(() => {
            this.gareCards = this.checkCardsProtections(this.config.gareCards);    
            this.attiGeneraliCards = this.checkCardsProtections(this.config.attiGeneraliCards);        

            // rimozione pulsante nuova gara se sono in ambiente di produzione
            remove(this.gareCards, (one: HomePageCard) => one.code != null && one.code === 'nuova-gara' && this.isTest === false);
            // rimozione funzionalità di trasparenza se sono in ambiente di produzione
            remove(this.attiGeneraliCards, (one: HomePageCard) => one.code != null && this.isTest === false);
        });
    }

    private checkCardsProtections(cards: Array<HomePageCard>): Array<HomePageCard> {
        return this.protectionUtilsService.checkHomePageCardsProtection(cards, this.userProfile.configurations);
    }

    private clearSearches(): void {
        this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_CDC_DISPATCHER, undefined));
        this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_IMPRESE_DISPATCHER, undefined));
        this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_STAZIONI_APPALTANTI_DISPATCHER, undefined));
        this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ARCHIVIO_TECNICI_DISPATCHER, undefined));
        this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_AVVISI_DISPATCHER, undefined));

        this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_RICERCHE_SCHEDE_TRASMESSE_PCP_DISPATCHER, undefined));

        this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ATTI_GENERALI_DISPATCHER, undefined));
        
        
        //Pulizia dettagli per schede trasmesse a PCP
        this.dettaglioFaseStoreService.clearFromLS();
        this.dettaglioLottoStoreService.clearFromLS();
        this.dettaglioImpresaAggiudicatariaStoreService.clearFromLS();
        this.dettaglioImprInvParteStoreService.clearFromLS();

        //this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_GARE_DISPATCHER, undefined));
    }

    private checkMessages(): void {
        let paramMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        if (paramMap != null && paramMap.get('successMessage') != null) {
            const successMessage: string = paramMap.get('successMessage');
            if (!isEmpty(successMessage)) {
                this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
                    {
                        message: successMessage
                    }
                ]);
            }
        }
    }    

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private generaUrlAnac(): Observable<ResponseResult<string>> {
        const factory = this.generaUrlAnacFactory();
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private generaUrlAnacFactory(): () => Observable<ResponseResult<string>> {
        return () => {
            return this.attiGeneraliService.generaUrlAnac();
        }
    }

    private elaborateUrlGeneratoAnac = (result: ResponseResult<string>) => {
        this.urlGeneratoAnac = result.data;
        this.initDialog();
        this.generaUrlDialog();
    }

    // #endregion

}
