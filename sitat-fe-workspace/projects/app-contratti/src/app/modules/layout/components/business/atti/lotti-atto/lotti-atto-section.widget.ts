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
import { HttpRequestHelper, ProtectionUtilsService } from '@maggioli/app-commons';
import {
    SdkBusinessAbstractWidget,
    SdkNumberFormatService,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkButtonGroupOutput, SdkMenuTab, SdkMessagePanelService } from '@maggioli/sdk-controls';
import { each, filter, find, isEmpty, isObject, isUndefined, remove, size, split, trim } from 'lodash-es';
import { BehaviorSubject, Observable } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { AttoLottoEntry, GaraEntry } from '../../../../../models/gare/gare.model';
import { GareService } from '../../../../../services/gare/gare.service';
import { FlussiAtto } from 'projects/app-contratti/src/app/modules/models/pubblicazioni/pubblicazione-atto.model';
import { PubblicazioneAttoService } from 'projects/app-contratti/src/app/modules/services/pubblicazioni/pubblicazione-atto.service';


@Component({
    templateUrl: `lotti-atto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class LottiAttoSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `lotti-atto-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private userProfile: UserProfile;
    private codGara: string;
    private tipoDocumento: string;
    private numPubb: string;
    private campiVisibili: Array<string>;
    private campiVisibiliString: string;
    private campiObbligatori: Array<string>;
    private campiObbligatoriString: string;
    public lottiAtto: Array<AttoLottoEntry>;
    private menuTabs: Array<SdkMenuTab>;
    private listaAssociazioni: Array<string>;
    public locale: string;
    public currency: string;
    public pubblicazioni: Array<FlussiAtto>;
    public gara: GaraEntry;

    // #endregion

    constructor(
        inj: Injector,
        cdr: ChangeDetectorRef
    ) { super(inj, cdr) }

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
        this.loadLottiAtto();
        this.getListaPubblicazioni();

    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.locale = this.config.locale.langCode;
                this.currency = this.config.locale.currencyCode;
                this.menuTabs = this.config.menuTabs;
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

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    public get sdkNumberFormatService(): SdkNumberFormatService { return this.injectable(SdkNumberFormatService) }

    private get pubblicazioneAttoService(): PubblicazioneAttoService { return this.injectable(PubblicazioneAttoService) }


    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.executeButtonProvider(button);
        }
    }

    public manageCheckboxes(codLotto: string, event: any): void {
        if (event.target.checked === true) {
            let found: string = find(this.listaAssociazioni, (one: string) => one === codLotto);
            if (isEmpty(found)) {
                this.listaAssociazioni.push(codLotto);
            }
        } else {
            remove(this.listaAssociazioni, (one: string) => one === codLotto);
        }
    }

    // #endregion

    // #region Private

    private dettaglioGaraFactory(codGara: string): () => Observable<GaraEntry> {
        return () => {
            return this.gareService.dettaglioGara(codGara);
        }
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {
        this.sdkMessagePanelService.clear(this.messagesPanel);
        this.provider.run(button.provider, {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            codGara: this.codGara,
            tipoDocumento: this.tipoDocumento,
            numPubb: this.numPubb,
            campiVisibili: this.campiVisibiliString,
            campiObbligatori: this.campiObbligatoriString,
            listaAssociazioni: this.listaAssociazioni
        }).subscribe(this.manageProviderExecution);
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.codGara = paramsMap.get('codGara');
        this.tipoDocumento = paramsMap.get('tipoDocumento');
        this.numPubb = paramsMap.get('numPubb');

        let campiVisibili: string = paramsMap.get('campiVisibili');
        this.campiVisibiliString = campiVisibili;
        if (!isEmpty(campiVisibili)) {
            let trimmed: string = trim(campiVisibili, '|');
            this.campiVisibili = split(trimmed, '|');
        }
        let campiObbligatori: string = paramsMap.get('campiObbligatori');
        this.campiObbligatoriString = campiObbligatori;
        if (!isEmpty(campiObbligatori)) {
            let trimmed: string = trim(campiObbligatori, '|');
            this.campiObbligatori = split(trimmed, '|');
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



    private loadLottiAtto(): void {


        let factory = this.lottiAttoFactory(this.codGara, this.numPubb);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: Array<AttoLottoEntry>) => {
            let factoryGara = this.dettaglioGaraFactory(this.codGara);
            this.requestHelper.begin(factoryGara, this.messagesPanel).subscribe((resultGara) => {
                this.markForCheck(() => {
                    this.gara = resultGara;
                    if (this.gara.pcp) {
                        this.menuTabs = filter(this.menuTabs, (one) => one.code !== 'pubblica-atto');
                    }
                    if (result && size(result) > 0) {
                        this.lottiAtto = result;
                    } else {
                        this.lottiAtto = new Array();
                    }
                    this.buildListaAssociazioni();
                    this.refreshTabs();
                });
            });


        });
    }

    private lottiAttoFactory(codGara: string, numPubb: string): () => Observable<Array<AttoLottoEntry>> {
        return () => {
            return this.gareService.getLottiAtto(codGara, numPubb);
        }
    }

    private refreshTabs(): void {
        remove(this.menuTabs, (one: SdkMenuTab) => {
            if (!isEmpty(one.oggettoProtezione)) {
                let visible: boolean = this.protectionUtilsService.isMenuTabVisible(one.oggettoProtezione, this.userProfile.configurations);
                if (visible !== true) {
                    return true;
                }
            }
            if (!isEmpty(one.visible)) {
                let visible: boolean = this.provider.run(one.visible, { tipoDocumento: this.tipoDocumento, atto: { countLotti: this.lottiAtto.length } });
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);
    }

    private buildListaAssociazioni(): void {
        if (isUndefined(this.listaAssociazioni)) {
            this.listaAssociazioni = new Array();
        }
        each(this.lottiAtto, (one: AttoLottoEntry) => {
            if (one.associato === true) {
                this.listaAssociazioni.push(one.codLotto);
            }
        })
    }

    private manageProviderExecution = (result: any) => {
        if (result && result.reload === true) {
            this.sdkMessagePanelService.clear(this.messagesPanel);
            this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
                {
                    message: 'LOTTI-ATTO.UPDATE-SUCCESS'
                }
            ]);
            delete this.listaAssociazioni;
            this.loadLottiAtto();
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private getListaPubblicazioni() {
        let factory = this.listaPubblicazioniFactory(this.codGara, this.numPubb);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result) => {
            this.pubblicazioni = result;

            if (this.pubblicazioni != null && this.pubblicazioni.length > 0) {

                try {
                    document.getElementById('pubblica-atto_header').classList.remove('red-highlights');
                    //  document.getElementById('pubblica-atto_header').classList.add('p-panelmenu-header-link');
                } catch (e) {
                }
            } else {
                try {
                    document.getElementById('pubblica-atto_header').classList.add('red-highlights');
                    //  document.getElementById('pubblica-atto_header').classList.remove('p-panelmenu-header-link');
                } catch (e) {
                }
            }
        });
    }



    private listaPubblicazioniFactory(codGara: string, num: string): () => Observable<Array<FlussiAtto>> {
        return () => {
            return this.pubblicazioneAttoService.getListaPubblicazioniAtto(codGara, num);
        }
    }

    public selectAll() {
        for (let index = 0; index < this.lottiAtto.length; index++) {
            let checkbox: any = document.getElementById('check_' + index);
            checkbox.checked = true;
        }


        this.listaAssociazioni = new Array();

        each(this.lottiAtto, (one: AttoLottoEntry) => {
            this.listaAssociazioni.push(one.codLotto);
        })

    }

    public deselectAll() {
        for (let index = 0; index < this.lottiAtto.length; index++) {
            let checkbox: any = document.getElementById('check_' + index);
            checkbox.checked = false;
        }

        this.listaAssociazioni = new Array();
    }


    // #endregion
}
