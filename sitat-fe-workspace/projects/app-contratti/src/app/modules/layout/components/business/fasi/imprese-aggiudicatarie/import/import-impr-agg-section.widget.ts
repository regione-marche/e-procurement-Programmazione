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
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkMenuTab, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { find, get, isEmpty, isObject, toString } from 'lodash-es';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseImpresaEntry, RuoloImpresa } from '../../../../../../models/fasi/elenco-impr-inv-parte.model';
import { ElencoImpreseInvitatePartecipantiService } from '../../../../../../services/fasi/elenco-impr-inv-parte.service';


@Component({
    templateUrl: `import-impr-agg-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ImportImpreseAggiudicatarieSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `import-imprese-aggiudicatarie-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private userProfile: UserProfile;
    private codGara: string;
    private codLotto: string;
    private codiceFase: string;
    private numeroProgressivo: string;
    public listaImpresePartecipanti: Array<FaseImpresaEntry>;
    private menuTabs: Array<SdkMenuTab>;
    private providerCode: string;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private selectedImpresa: FaseImpresaEntry;

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
        this.checkInfoBox();
        this.loadTabellati().pipe(
            map(this.elaborateTabellati),
            mergeMap(this.loadListaImpresePartecipanti),
            map(this.elaborateListaImpresePartecipanti)
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.providerCode = this.config.body.provider;
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

    private get elencoImpreseInvitatePartecipantiService(): ElencoImpreseInvitatePartecipantiService { return this.injectable(ElencoImpreseInvitatePartecipantiService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.executeButtonProvider(button);
        }
    }

    public trackByCode(index: number, item: FaseImpresaEntry): string {
        return isObject(item) ? toString(item.num) : toString(index);
    }

    public manageCheckboxes(item: FaseImpresaEntry, event: any): void {
        if (event.target.value === 'on') {
            this.selectedImpresa = item;
        }
    }

    public getPartecipante(impresa: FaseImpresaEntry): string {
        if (isObject(impresa)) {
            return this.getDescrizioneTabellato('ImpresaPartecipante', toString(impresa.partecipante));
        }
        return '';
    }

    public getRuolo(impresa: RuoloImpresa): string {
        if (isObject(impresa)) {
            return this.getDescrizioneTabellato('RuoloAssociazione', toString(impresa.ruolo));
        }
        return '';
    }

    public getTipoImpresa(impresa: FaseImpresaEntry): string {
        if (isObject(impresa)) {
            return this.getDescrizioneTabellato('TipologiaAggiudicatario', toString(impresa.tipologiaSoggetto));
        }
        return '';
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
            codiceFase: this.codiceFase,
            numeroProgressivo: this.numeroProgressivo,
            selectedImpresa: this.selectedImpresa
        }).subscribe();
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.codLotto = paramsMap.get('codLotto');
        this.codiceFase = paramsMap.get('codiceFase');
        this.numeroProgressivo = paramsMap.get('numeroProgressivo');
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

    private dettaglioListaImpresePartecipantiFactory(codGara: string, codLotto: string): () => Observable<Array<FaseImpresaEntry>> {
        return () => {
            return this.elencoImpreseInvitatePartecipantiService.getImpresePartecipanti(codGara, codLotto);
        }
    }

    private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
        return this.tabellatiCacheService.getValoriTabellati(Constants.ELENCO_IMPRESE_INVITATE_PARTECIPANTI);
    }

    private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
        this.valoriTabellati = result;
    }

    private loadListaImpresePartecipanti = (): Observable<Array<FaseImpresaEntry>> => {
        let factory = this.dettaglioListaImpresePartecipantiFactory(this.codGara, this.codLotto);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateListaImpresePartecipanti = (result: Array<FaseImpresaEntry>) => {
        this.markForCheck(() => {
            this.listaImpresePartecipanti = result;
        });
    };

    private reloadGrid(): void {
        this.loadListaImpresePartecipanti().pipe(
            map(this.elaborateListaImpresePartecipanti)
        ).subscribe();
    }

    private getDescrizioneTabellato(tabellatoType: string, tabellatoKey: string): string {
        if (tabellatoType && tabellatoKey) {
            let tabellati: Array<ValoreTabellato> = get(this.valoriTabellati, tabellatoType);
            let found: ValoreTabellato = find(tabellati, (one: ValoreTabellato) => one.codice === tabellatoKey);
            return found ? found.descrizione : tabellatoKey;
        }
        return '';
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion
}
