import {
    AfterViewInit,
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    Inject,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import { SDK_APP_CONFIG, SdkAbstractComponent, SdkAppEnvConfig, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkComboboxConfig,
    SdkComboboxInput,
    SdkComboBoxItem,
    SdkComboboxOutput,
    SdkFormBuilderConfiguration,
    SdkMessagePanelService,
    SdkTextConfig,
    SdkTextInput,
} from '@maggioli/sdk-controls';
import { each, isEmpty, isObject, isUndefined, has, get, orderBy } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { Constants } from '../../app-commons.constants';
import { DataNuts, TabellatoNuts, AREA_ORDER_MAP } from '../../models/nuts/nuts-data.model';
import { StazioneAppaltanteInfo } from '../../models/stazione-appaltante/stazione-appaltante.model';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';
import { TabellatiService } from '../../services/tabellati/tabellati.service';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';

@Component({
    templateUrl: `nuts-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NutsModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    private currentPaese: TabellatoNuts;
    private currentArea: TabellatoNuts;
    private currentRegione: TabellatoNuts;
    private currentProvincia: TabellatoNuts;
    public config: any;
    public data: void;
    public calculatedData: string;
    private userProfile: UserProfile;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public paeseComboboxConfig: Observable<SdkComboboxConfig>;
    public areaComboboxConfig: Observable<SdkComboboxConfig>;
    public regioneComboboxConfig: Observable<SdkComboboxConfig>;
    public provinciaComboboxConfig: Observable<SdkComboboxConfig>;
    public nutsConfigurationObs: Observable<SdkTextConfig>;
    public paeseComboData: Subject<SdkComboboxInput> = new Subject();
    public areaComboData: Subject<SdkComboboxInput> = new Subject();
    public regioneComboData: Subject<SdkComboboxInput> = new Subject();
    public provinciaComboData: Subject<SdkComboboxInput> = new Subject();
    private selectedItem: string;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private areaProviderSubject: Subject<Array<SdkComboBoxItem>> = new Subject();
    private regioniProviderSubject: Subject<Array<SdkComboBoxItem>> = new Subject();
    private provinceProviderSubject: Subject<Array<SdkComboBoxItem>> = new Subject();

    private calculateDataSubject: Subject<SdkTextInput> = new Subject();
    private currentValue: string;
    private nutsData: DataNuts;

    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onAfterViewInit(): void {
        let factory = this.nutsTabsFactory();
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: any) => {
            this.nutsData = result;
            this.orderNutsData();
            this.populateData();
            setTimeout(() => {
                if (!isEmpty(this.selectedItem)) {
    
                    let paeseValue = this.selectedItem.substring(0, 2);
                    let areaValue = this.selectedItem.substring(2, 3);
                    let regioneValue = this.selectedItem.substring(3, 4);
                    let provinciaValue = this.selectedItem.substring(4, 5);
                    this.paeseComboData.next({ data: { key: paeseValue, value: '' } })
                    let config = [];
                    each(this.nutsData.aree, (area: TabellatoNuts) => {
                        if (area.paese === paeseValue) {
                            config.push({
                                key: area.area,
                                value: area.descrizione
                            });
    
                        }
                    })
                    each(this.nutsData.paesi, (paese: TabellatoNuts) => {
                        if (paese.paese === paeseValue) {
                            this.currentPaese = paese;
                        }
                    })
                    this.areaProviderSubject.next(config);
                    config = [];
                    this.areaComboData.next({ data: { key: areaValue, value: '' } })
                    each(this.nutsData.regioni, (regione: TabellatoNuts) => {
                        if (regione.area === areaValue && regione.paese === this.currentPaese.codice) {
                            config.push({
                                key: regione.regione,
                                value: regione.descrizione
                            });
                        }
                    })
    
                    each(this.nutsData.aree, (area: TabellatoNuts) => {
                        if (area.area === areaValue) {
                            this.currentArea = area;
                        }
                    })
                    this.regioniProviderSubject.next(config);
                    config = [];
                    this.regioneComboData.next({ data: { key: regioneValue, value: '' } })
                    each(this.nutsData.province, (provincia: TabellatoNuts) => {
                        if (provincia.regione === regioneValue && provincia.area === areaValue && provincia.paese === this.currentPaese.codice) {
                            config.push({
                                key: provincia.provincia,
                                value: provincia.descrizione
                            });
                        }
                    })
                    each(this.nutsData.regioni, (regione: TabellatoNuts) => {
                        if (regione.regione === regioneValue && regione.area === areaValue) {
                            this.currentRegione = regione;
                        }
                    })
                    this.provinceProviderSubject.next(config);
                    this.provinciaComboData.next({ data: { key: provinciaValue, value: '' } })
    
    
                    each(this.nutsData.province, (provincia: TabellatoNuts) => {
                        if (provincia.provincia === provinciaValue &&
                            provincia.area === areaValue &&
                            provincia.regione === regioneValue && 
                            provincia.paese === this.currentPaese.codice)
                            this.currentProvincia = provincia;
                    })
                    this.markForCheck(() => {
                        this.calculateDataSubject.next({
                            data: this.selectedItem
                        });
                        this.currentValue = this.selectedItem;
                    })
    
                }
            }, 500);
        });

        
    }

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
       
    }

    protected onDestroy(): void { }

    protected onOutput(_data: void): void { }

    public onComboChange(element: SdkComboboxOutput) {
        let config = [];
        if (element.code === 'paese') {
            if (!isUndefined(element.data)) {
                each(this.nutsData.aree, (area: TabellatoNuts) => {
                    if (area.paese === element.data.key) {
                        config.push({
                            key: area.area,
                            value: area.descrizione
                        });

                    }
                })
                each(this.nutsData.paesi, (paese: TabellatoNuts) => {
                    if (paese.paese === element.data.key) {
                        this.markForCheck(() => {
                            this.calculateDataSubject.next({
                                data: paese.codice
                            });
                            this.currentPaese = paese;
                            this.currentValue = paese.codice;
                        })
                    }
                })
            }
            this.areaProviderSubject.next(config);
            this.regioniProviderSubject.next([]);
            this.currentRegione = null;
            this.provinceProviderSubject.next([]);
            this.currentProvincia = null;

            this.areaComboData.next({ data: { key: '', value: '' } });
            this.regioneComboData.next({ data: { key: '', value: '' } });
            this.provinciaComboData.next({ data: { key: '', value: '' } });


        }
        if (element.code === 'area') {
            if (!isUndefined(element.data)) {
                each(this.nutsData.regioni, (regione: TabellatoNuts) => {
                    if (regione.area === element.data.key && this.currentPaese.codice === regione.paese) {
                        config.push({
                            key: regione.regione,
                            value: regione.descrizione
                        });
                    }
                })

                each(this.nutsData.aree, (area: TabellatoNuts) => {
                    if (area.area === element.data.key && this.currentPaese.codice === area.paese) {
                        this.markForCheck(() => {
                            this.calculateDataSubject.next({
                                data: area.codice
                            });
                            this.currentArea = area;
                            this.currentValue = area.codice;
                        })
                    }
                })
            }
            this.regioniProviderSubject.next(config);
            this.provinceProviderSubject.next([]);
            this.currentProvincia = null;
            this.regioneComboData.next({ data: { key: '', value: '' } });
            this.provinciaComboData.next({ data: { key: '', value: '' } });
        }
        if (element.code === 'regione') {
            if (!isUndefined(element.data)) {
                each(this.nutsData.province, (provincia: TabellatoNuts) => {
                    if (provincia.regione === element.data.key && provincia.area === this.currentArea.area && this.currentPaese.codice === provincia.paese) {
                        config.push({
                            key: provincia.provincia,
                            value: provincia.descrizione
                        });
                    }
                })
                each(this.nutsData.regioni, (regione: TabellatoNuts) => {
                    if (regione.regione === element.data.key && regione.area === this.currentArea.area && this.currentPaese.codice === regione.paese) {
                        this.markForCheck(() => {
                            this.calculateDataSubject.next({
                                data: regione.codice
                            });
                            this.currentRegione = regione;
                            this.currentValue = regione.codice;
                        })
                    }
                })
            }
            this.provinceProviderSubject.next(config);
            this.provinciaComboData.next({ data: { key: '', value: '' } });
        }
        if (element.code === 'provincia') {
            if (!isUndefined(element.data)) {
                each(this.nutsData.province, (provincia: TabellatoNuts) => {
                    if (provincia.provincia === element.data.key &&
                        provincia.area === this.currentArea.area &&
                        provincia.regione === this.currentRegione.regione && 
                        provincia.paese === this.currentPaese.codice) {
                        this.markForCheck(() => {
                            this.calculateDataSubject.next({
                                data: provincia.codice
                            });
                            this.currentProvincia = provincia;
                            this.currentValue = provincia.codice;
                        })
                    }
                })
            }
        }

    }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.selectedItem = this.config.selectedItem;
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

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion 
    // #region Private

    private moveItaliaToFront(array: TabellatoNuts[]): TabellatoNuts[] {
        const index = array.findIndex(item => item.codice === "IT");    
        if (index > 0) {
            const [italiaElement] = array.splice(index, 1);
            array.unshift(italiaElement);
        }
        return array;
    }

    private populateData(): void {
        this.markForCheck(() => {
            this.paeseComboboxConfig = of({
                code: 'paese',
                label: 'NUTS.PAESE',
                itemsProvider: () => {
                    let paese = this.moveItaliaToFront(this.nutsData.paesi);
                    let paesi: Array<SdkComboBoxItem> = [];                    
                    paese.forEach(element => {
                        paesi.push({
                            key: element.paese,
                            value: element.descrizione
                        });
                    });
                    return of(paesi);
                }
            });
            this.areaComboboxConfig = of({
                code: 'area',
                label: 'NUTS.AREA',
                itemsProvider: () => {
                    return this.areaProviderSubject;
                }
            } as SdkComboboxConfig);
            this.regioneComboboxConfig = of({
                code: 'regione',
                label: 'NUTS.REGIONE',
                itemsProvider: () => {
                    return this.regioniProviderSubject;
                }
            } as SdkComboboxConfig);
            this.provinciaComboboxConfig = of({
                code: 'provincia',
                label: 'NUTS.PROVINCIA',
                itemsProvider: () => {
                    return this.provinceProviderSubject;
                }
            } as SdkComboboxConfig);
            this.nutsConfigurationObs = of({
                code: 'calculated',
                label: 'NUTS.CALCULATED',
                disabled: true
            } as SdkTextConfig);

            this.isReady = true;

        });

    }

    private nutsTabsFactory(): () => Observable<any> {
        return () => {
            return this.tabellatiService.nutsData(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL);
        }
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.data.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private orderNutsData(): void {
        if (this.nutsData != null) {
            // Aree
            each(this.nutsData.aree, (one: TabellatoNuts) => {
                if (has(AREA_ORDER_MAP, one.area) == true) {
                    one.order = get(AREA_ORDER_MAP, one.area);
                }
            });
            this.nutsData.aree = orderBy(this.nutsData.aree, 'order', 'asc');

            // Regioni
            this.nutsData.regioni = orderBy(this.nutsData.regioni, 'descrizione', 'asc');

            // Province
            this.nutsData.province = orderBy(this.nutsData.province, 'descrizione', 'asc');
        }
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (button.code === 'set-button') {
            if (isObject(this.currentPaese)) {


                this.emitOutput({
                    op: 'update',
                    value: this.currentValue
                });
            } else {
                this.sdkMessagePanelService.showError(this.messagesPanel, [
                    {
                        message: 'NUTS.VALIDATORS.COMBO-REQUIRED'
                    }
                ]);
            }
        } else {
            this.emitOutput(undefined);
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    // #endregion
}
