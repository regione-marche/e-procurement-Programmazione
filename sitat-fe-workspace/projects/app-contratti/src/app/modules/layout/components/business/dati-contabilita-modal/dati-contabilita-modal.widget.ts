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
    CustomParamsFunction,
    FormBuilderUtilsService,
    HttpRequestHelper,
    ImpresaEntry,
    ProtectionUtilsService,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    ValoreTabellato,
} from '@maggioli/app-commons';
import { IDictionary, SdkAbstractComponent, SdkDateHelper, SdkLocaleService, SdkNumberFormatService, SdkProviderService, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMenuItem,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, find, get, has, isEmpty, isObject, isString, remove, set, sum, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../app.constants';
import { GareService } from '../../../../services/gare/gare.service';
import { AssociazionePagamentiEntry, ImpegnoEntry, PagamentoEntry, ResponseDatiContabilita } from '../../../../models/gare/gare.model';
import { TabViewModule } from 'primeng/tabview';
import { MenuItem } from 'primeng/api';
import { TranslateService } from '@ngx-translate/core';
import { TabMenuModule } from 'primeng/tabmenu';

@Component({
    templateUrl: `dati-contabilita-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class DatiContabilitaModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    public config: any;
    public data: void;

    private buttons: SdkButtonGroupInput;
    private buttonsSelection: SdkButtonGroupInput;
    
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);

    private userProfile: UserProfile;
    
    private stazioneAppaltante: StazioneAppaltanteInfo;
    
    private isSelection: boolean;
    private codGara: string;
    private codLotto: string;
    private cig: string;
    private associazionePagamenti?: AssociazionePagamentiEntry;
    

    public datiContabilita?: ResponseDatiContabilita;
    public impegni?: Array<ImpegnoEntry>;
    public pagamenti?: Array<PagamentoEntry>;
    public selectedPagamenti?: Array<PagamentoEntry>;

    public menuItems: MenuItem[] | undefined;
    public activeMenuItem: MenuItem;

    public selectedMenu: string = 'pagamenti';

    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {


        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltante = saInfo;
        }));

        this.loadButtons();
        
    }

    protected onAfterViewInit(): void {
        this.loadDatiContabilita().pipe(
            map(this.elaborateDatiContabilita),
        ).subscribe();
    }

    private loadDatiContabilita = (): Observable<ResponseDatiContabilita> => {
        var factory = () => {
            return this.gareService.getDatiContabilita(this.codGara, this.cig);
        }
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateDatiContabilita = (result: ResponseDatiContabilita) => {
        this.markForCheck(() => {
            
            this.loadMenuTabs();
            
            this.datiContabilita = result;
            this.impegni = result.impegni;
            this.pagamenti = result.pagamenti;
            
        });
      }

    private loadMenuTabs() {
        this.menuItems =
            [
                {
                    id: 'pagamenti',
                    label: this.translateService.instant('DATI-CONTABILITA.PAGAMENTI.LIST-TITLE'),
                    command: (event) => {
                        this.selectedMenu = 'pagamenti';
                    }
                },
                {
                    id: 'impegni',
                    label: this.translateService.instant('DATI-CONTABILITA.IMPEGNI.LIST-TITLE'),
                    command: (event) => {
                        this.selectedMenu = 'impegni';
                    }
                }
            ];

        this.activeMenuItem = this.menuItems[0];
    }

    protected onDestroy(): void { }

    protected onOutput(_data: void): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isSelection = this.config.isSelection;
                this.codGara = this.config.codGara;
                this.codLotto = this.config.codLotto;
                this.cig = this.config.cig;
                this.associazionePagamenti = this.config.associazionePagamenti;
                this.isReady = true;

                this.selectedPagamenti = new Array();
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

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }

    protected get sdkDateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper); }

    protected get sdkNumberFormatService(): SdkNumberFormatService { return this.injectable(SdkNumberFormatService); }

    protected get sdkLocaleService(): SdkLocaleService { return this.injectable(SdkLocaleService); }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                
                

            });
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (has(obj, 'close') && get(obj, 'close') === true) {
                this.emitOutput(undefined);
            }
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.buttons, this.userProfile.configurations)
        };
        

        this.buttonsSelection = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.buttonsSelection, this.userProfile.configurations)
        };

        this.showButtons();
    }

    private showButtons(): void {
        
        if (this.config.isSelection) {
             this.buttonsSubj.next(this.buttonsSelection);
        } else {
            this.buttonsSubj.next(this.buttons);
        }
    }
    

    // #endregion

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if(button.code === 'conferma-dati-contabilita') {
            
            this.associazionePagamenti.pagamenti = this.selectedPagamenti
            this.associazionePagamenti.importoTotalePagamenti = sum(this.associazionePagamenti.pagamenti.map((one: PagamentoEntry) => one.importo)),
            
            //Close the window
            this.emitOutput(this.associazionePagamenti);   
            
        } else if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                type: 'BUTTON',
                data: {
                    code: button.code,
                    messagesPanel: this.messagesPanel,
                    stazioneAppaltante: this.stazioneAppaltante.codice
                }
            }).subscribe(this.manageExecutionProvider)
        }
    }

    public manageCheckboxes(idx: number, event: any): void {

        if (event.target.checked === true) {
            let found: PagamentoEntry = find(this.selectedPagamenti, (one: PagamentoEntry) => one.idx === idx);
            if (isEmpty(found)) {
                let item: PagamentoEntry = find(this.pagamenti, (one: PagamentoEntry) => one.idx === idx);
                this.selectedPagamenti.push(item);
            }
        } else {
            remove(this.selectedPagamenti, (one: PagamentoEntry) => one.idx === idx);
        }
    }

     public selectAll() {
        
        this.selectedPagamenti = new Array();

        each(this.pagamenti, (pagamento: PagamentoEntry) => {
            let checkbox: any = document.getElementById('check_' + pagamento.idx);
            checkbox.checked = true;
            this.selectedPagamenti.push(pagamento);
        });

    }
    
        public deselectAll() {
            
            each(this.pagamenti, (pagamento: PagamentoEntry) => {
                let checkbox: any = document.getElementById('check_' + pagamento.idx);
                checkbox.checked = false;
            });

            this.selectedPagamenti = new Array();
        }
    
    

}
