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
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkComboboxConfig,
    SdkComboboxInput,
    SdkComboBoxItem,
    SdkComboboxOutput,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkTextboxConfig,
    SdkTextboxOutput,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, filter, get, has, includes, isEmpty, isObject, isUndefined, map, remove, size, toLower, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

import { Constants } from '../../../../../../app.constants';
import {
    ChiaviIntervento,
    InterventiDaRiproporre,
    InterventiDaRiproporreExtended,
} from '../../../../../models/interventi/interventi-import.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';



@Component({
    templateUrl: `import-interventi-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ImportInterventiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `import-interventi-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    private buttons: SdkButtonGroupInput;
    private buttons2: SdkButtonGroupInput;
    private buttonsBack: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    private originalInterventi: Array<InterventiDaRiproporreExtended>;
    public interventi: Array<InterventiDaRiproporreExtended>;
    public interventiFiltered: Array<InterventiDaRiproporreExtended>;
    public piatriIds: { key: string, value: string }[];
    private idProgramma: string;
    public tipologia: string;
    private menuTabs: Array<SdkMenuTab>;
    private checkedRows: Array<ChiaviIntervento> = [];
    private userProfile: UserProfile;
    public locale: string;
    public currency: string;
    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    public searchConfigObs: Observable<SdkTextboxConfig>;
    public debounce: Subject<SdkTextboxOutput> = new Subject<SdkTextboxOutput>();
    public interventoComboboxConfig: Observable<SdkComboboxConfig>;
    public interventoComboData: Observable<SdkComboboxInput>;
    private currentFilter: string;
    public showEmptyPanel = false;
    public isHeaderChecked: boolean = false;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.addSubscription(this.debounce.pipe(
            debounceTime(350),
            distinctUntilChanged())
            .subscribe((value: SdkTextboxOutput) => {
                this.search(value);
            }));

        this.loadButtons();
        this.loadQueryParams();
        this.refreshTabs();

    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.getData();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.locale = this.config.locale.langCode;
                this.currency = this.config.locale.currencyCode;
                this.menuTabs = this.config.menuTabs;
                this.searchConfigObs = of(this.config.body.searchField);
                this.modalConfig = {
                    code: 'modal',
                    title: '',
                    openSubject: new Subject()
                };
                this.modalConfigObs.next(this.modalConfig);
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            if(button.code === 'import'){
                this.modalConfig = {
                    ...this.modalConfig,
                    component: 'import-interventi-modal-widget',
                    componentConfig: this.config.body.modalConfig
                }
                this.modalConfigObs.next(this.modalConfig);
                setTimeout(() => this.modalConfig.openSubject.next(true));
            } else {
                this.provider.run(button.provider, {
                    buttonCode: button.code,
                    messagesPanel: this.messagesPanel,
                    idProgramma: this.idProgramma,
                    tipologia: this.tipologia,
                    importData: this.checkedRows,
                    isNoRiproposto: false
                }).subscribe((data: IDictionary<any>) => {
                    if (has(data, 'reload') && get(data, 'reload') === true) {
                        this.checkedRows = new Array();
                        this.getData();
                    }
                });
            }
        }
    }

    public manageCheckboxes(idProgramma: string, idIntervento: string, event: any) {
        const isChecked = event.target.checked;
        const intervento = this.interventiFiltered?.find(i => toString(i.id) === toString(idIntervento) && toString(i.idProgramma) === toString(idProgramma))

        if(intervento) {
            intervento.checked = isChecked;
        }

        this.applyChecked(idProgramma, idIntervento, isChecked);
        if (isChecked === true) {
            // se l'elemento selezionato e' il primo, disabilito tutti gli interventi degli altri programmi
            this.checkedRows?.push({ idProgramma, idIntervento });

            if (size(this.checkedRows) === 1) {
                this.disabilitaAltriProgrammi(idProgramma);
            }
        } else {
            this.checkedRows = this.checkedRows?.filter(row => !(row.idIntervento === idIntervento && row.idProgramma === idProgramma));

            // riabilito tutti i programmi perche' ho de-selezionato tutto
            if (this.checkedRows?.length === 0) {
                this.abilitaTuttiProgrammi();
            }
        }

        this.isHeaderChecked = this.areAllChecked(this.interventiFiltered);     
    }

    public manageAllCheck(event: any) {
        const isChecked = event.target.checked;

        this.interventiFiltered?.forEach(intervento => {
            intervento.checked = isChecked;
        });

        if(this.interventiFiltered?.length > 0) {
            if(isChecked) {
                this.checkedRows = this.interventiFiltered?.map(i => ({
                    idProgramma: toString(i.idProgramma),
                    idIntervento: toString(i.id)
                }));
            } else {
                this.checkedRows = [];
                this.abilitaTuttiProgrammi();
            }
        }

        this.isHeaderChecked = isChecked;
    }

    public areAllChecked(interventiFiltered: any) {
        return interventiFiltered?.length > 0 && interventiFiltered?.every(intervento => intervento.checked === true) 
    }

    // #endregion

    // #region Private

    private getData() {
        let factory = this.listaInterventiImportFactory(this.idProgramma);        
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: Array<InterventiDaRiproporre>) => {
            if(isEmpty(result)){
                this.buttonsSubj.next(this.buttonsBack);
                //this.showEmptyPanel = true;
            } 
            this.markForCheck(() => {               
                this.originalInterventi = map(result, (one: InterventiDaRiproporre) => {
                    let newOne: InterventiDaRiproporreExtended = {
                        ...one,
                        disabled: false,
                        checked: false
                    };
                    return newOne;
                });
                this.interventi = cloneDeep(this.originalInterventi);
                
                this.piatriIds = this.interventi.map(intervento => {
                    return { key: intervento.piatriId || "", value: intervento.piatriId || "" };
                });
                if(this.piatriIds != null && this.piatriIds.length > 0){
                    this.interventiFiltered = this.interventi.filter(intervento => intervento.piatriId === this.piatriIds[0].key);
                    let seenKeys = new Set<string>();                
                    this.piatriIds = this.piatriIds.filter(({ key }) => {
                        
                        if (!seenKeys.has(key)) {
                            seenKeys.add(key);
                            return true;
                        }
                        return false;
                    });
                    
                    this.interventoComboboxConfig = of({
                        code: 'intervento',
                        label: 'IMPORT-INTERVENTO.SELEZIONA-PROGRAMMA',                                        
                        itemsProvider: () => {                        
                            return of(this.piatriIds as Array<SdkComboBoxItem>);
                        }
                    });
                    if(isEmpty(this.piatriIds[0]) || isEmpty(this.piatriIds[0].key)){
                        this.interventoComboData = of(undefined);
                    } else{
                        this.interventoComboData = of({
                            data: {
                              key: this.piatriIds[0].key,
                              value: this.piatriIds[0].value
                            }
                        } as SdkComboboxInput); 
                    }
                                                              
                } else{
                    this.showEmptyPanel = true;
                    this.interventiFiltered = [];
                    this.isHeaderChecked = false;
                }
                
                this.isReady = true;
            });
        });
    }


    private listaInterventiImportFactory(idProgramma: string): () => Observable<Array<InterventiDaRiproporre>> {
        return () => {
            return this.programmiService.getListaInterventiImport(idProgramma);
        }
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idProgramma = paramsMap.get('idProgramma');
        this.tipologia = paramsMap.get('tipologia');

        this.initButtons();
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
                let visible: boolean = this.provider.run(one.visible, { tipologia: this.tipologia });
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);
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

    private disabilitaAltriProgrammi(idProgrammaSelezionato: string): void {
        this.markForCheck(() => {
            each(this.originalInterventi, (one: InterventiDaRiproporreExtended) => {
                if (one.idProgramma !== +idProgrammaSelezionato) {
                    one.disabled = true;
                }
            });
            this.applyFilter();
        });
    }

    private abilitaTuttiProgrammi(): void {
        this.markForCheck(() => {
            each(this.originalInterventi, (one: InterventiDaRiproporreExtended) => {
                one.disabled = false;
            });
            this.applyFilter();
        });
    }

    private search(item: SdkTextboxOutput): void {
        this.markForCheck(() => {
            delete this.currentFilter;
            if (item != null && item.data != null && !isEmpty(item.data)) {
                this.currentFilter = item.data;
            }
            this.applyFilter();
        });
    }

    private applyFilter(): void {
        if (this.currentFilter != null) {
            this.interventi = filter(cloneDeep(this.originalInterventi), (one: InterventiDaRiproporreExtended) => {
                return includes(toLower(one.cui), toLower(this.currentFilter)) || includes(toLower(one.descrizione), toLower(this.currentFilter));
            });
        } else {
            this.interventi = cloneDeep(this.originalInterventi);
        }
    }

    private applyChecked(idProgramma: string, idIntervento: string, checked: boolean): void {
        each(this.originalInterventi, (one: InterventiDaRiproporreExtended) => {
            if (one.idProgramma === +idProgramma && one.id === +idIntervento) {
                one.checked = checked;
            }
        });
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttons2 = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons2, this.userProfile.configurations)
        };
        this.buttonsBack = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsBack, this.userProfile.configurations)
        };
    }

    public onOutput(item:any){
        if(isObject(item) && !isEmpty(get(item, 'data'))){
            this.provider.run('APP_PROGRAMMI_IMPORTA_INTERVENTI', {
                buttonCode: 'import',
                messagesPanel: this.messagesPanel,
                idProgramma: this.idProgramma,
                tipologia: this.tipologia,
                importData: this.checkedRows,
                isNoRiproposto: false,
                choice: get(item, 'data')
            }).subscribe((data: IDictionary<any>) => {
                if (has(data, 'reload') && get(data, 'reload') === true) {
                    this.checkedRows = new Array();
                    this.getData();
                }
            });
        }
    }


    private initButtons(): void {
        if (this.tipologia === '1') {
            this.buttonsSubj.next(this.buttons);
        } else if (this.tipologia === '2') {
            this.buttonsSubj.next(this.buttons2);
        }
    }

    public onComboChange(element: SdkComboboxOutput) {    
        if (element.code === 'intervento') {
            if (!isUndefined(element) && !isUndefined(element.data) && !isUndefined(element.data.key)) {
                this.interventiFiltered = this.interventi.filter(intervento => intervento.piatriId === element.data.key);
                this.showEmptyPanel = false;
            } else{
                this.interventiFiltered = [];
                this.showEmptyPanel = true;
            }
        }
    }

    // #endregion
}
