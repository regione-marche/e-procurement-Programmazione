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
import { HttpRequestHelper, ProtectionUtilsService, TabellatiCacheService, ValoreTabellato } from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { cloneDeep, each, get, isEmpty, isObject, isUndefined, toString } from 'lodash-es';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import { TabellatoFaseEntry } from '../../../../../models/fasi/fasi.model';
import { GareService } from '../../../../../services/gare/gare.service';
import { GaraEntry } from 'projects/app-contratti/src/app/modules/models/gare/gare.model';


@Component({
    templateUrl: `nuova-fase-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `nuova-fase-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    private buttonsEmpty: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    private userProfile: UserProfile;
    private codGara: string;
    private codLotto: string;
    public valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    public fasiVisibili: Array<TabellatoFaseEntry>;
    public fasiRilevazioneDati: Array<TabellatoFaseEntry>;
    public eventiSignificativi: Array<TabellatoFaseEntry>;
    private selectedCodiceFase: number;
    private fasiSlugMap: IDictionary<string>;
    private gara: GaraEntry;
    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQueryParams();        
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            map(() => this.checkInfoBox()),            
            map(this.loadGaraFactory),
            mergeMap(this.loadGara),
            map(this.elaborateGara),
            map(this.loadListaFasiVisibiliFactory),
            mergeMap(this.loadListaFasiVisibili),
            map(this.elaborateListaFasiVisibili)
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.fasiSlugMap = this.config.body.fasiSlugMap;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.executeButtonProvider(button);
        }
    }

    public manageCheckboxes(codiceFase: number, event: any): void {
        if (event.target.value === 'on') {
            this.selectedCodiceFase = codiceFase;
        }
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {
        this.provider.run(button.provider, {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            codGara: this.codGara,
            codLotto: this.codLotto,
            codiceFase: toString(this.selectedCodiceFase),
            fasePage: this.getNuovaFasePage(toString(this.selectedCodiceFase))
        }).subscribe();
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');    
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

    private listaFasiVisibiliFactory(codGara: string, numLotto: string): () => Observable<Array<TabellatoFaseEntry>> {
        return () => {
            return this.gareService.getListaFasiVisibili(codGara, numLotto);
        }
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.FASI_LOTTO_TABELLATI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }
    
    private dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    private loadGaraFactory = (): Function => {
        let factory = this.dettaglioGaraFactory(this.codGara);
        return factory;
    }

    private loadGara = (factory: Function): Observable<GaraEntry> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }
    private elaborateGara = (result: GaraEntry) => {
        this.gara = result;       
    };

    private loadListaFasiVisibiliFactory = (): Function => {
        let factory = this.listaFasiVisibiliFactory(this.codGara, this.codLotto);
        return factory;
    }

    private loadListaFasiVisibili = (factory: Function): Observable<Array<TabellatoFaseEntry>> => {
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateListaFasiVisibili = (result: Array<TabellatoFaseEntry>) => {
        this.markForCheck(() => {
            this.fasiVisibili = result;
            this.elaborateButtons();
            if (isUndefined(this.fasiRilevazioneDati)) {
                this.fasiRilevazioneDati = new Array();
            }
            if (isUndefined(this.eventiSignificativi)) {
                this.eventiSignificativi = new Array();
            }
            each(this.fasiVisibili, (one: TabellatoFaseEntry) => {          
                if(this.gara.pcp){
                    if (this.condizioneDivisioneSezioniPcp(one.codice) === true) {
                        this.eventiSignificativi.push(cloneDeep(one));
                    } else {
                        this.fasiRilevazioneDati.push(cloneDeep(one));
                    }
                } else{
                    if (this.condizioneDivisioneSezioni(one.codice) === true) {
                        this.eventiSignificativi.push(cloneDeep(one));
                    } else {
                        this.fasiRilevazioneDati.push(cloneDeep(one));
                    }
                }   
               
            });
        });
    };

    private getNuovaFasePage(codiceFase: string): string {
        if (isUndefined(codiceFase)) {
            return undefined;
        }
        let pageSlug: string = get(this.fasiSlugMap, codiceFase);
        if (isUndefined(pageSlug) || isEmpty(pageSlug)) {
            return undefined;
        }
        return pageSlug;
    }

    private condizioneDivisioneSezioni(codice: number): boolean {
        return (codice >= 6 && codice <= 10) || codice >= 994;
    }

    private condizioneDivisioneSezioniPcp(codice: number): boolean {
        return (codice >= 6 && codice <= 10) || codice >= 994 || codice == 20 || 
        codice == 14 || codice == 15 ||
        codice == 16 || codice == 17 || codice == 18 || codice == 21;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsEmpty = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsEmpty, this.userProfile.configurations)
        }
    }

    private elaborateButtons(): void {
        if (this.fasiVisibili != null && this.fasiVisibili.length > 0) {
            this.buttonsSubj.next(this.buttons);
        } else {
            this.buttonsSubj.next(this.buttonsEmpty);
        }
    }

    // #endregion
}
