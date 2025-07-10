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
    SdkMenuTab,
    SdkMessagePanelService,
    SdkTextboxConfig,
    SdkTextboxOutput,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, filter, get, has, includes, isEmpty, isObject, map, remove, size, toLower, toString } from 'lodash-es';
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
    templateUrl: `import-interventi-non-riproposti-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ImportInterventiNonRipropostiSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `dett-prog-interventi-non-riproposti-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    private buttons: SdkButtonGroupInput;
    private buttons2: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    private originalInterventi: Array<InterventiDaRiproporreExtended>;
    public interventi: Array<InterventiDaRiproporreExtended>;
    private idProgramma: string;
    public tipologia: string;
    private menuTabs: Array<SdkMenuTab>;
    private checkedRows: Array<ChiaviIntervento> = [];
    private userProfile: UserProfile;
    public locale: string;
    public currency: string;
    public searchConfigObs: Observable<SdkTextboxConfig>;
    public debounce: Subject<SdkTextboxOutput> = new Subject<SdkTextboxOutput>();
    public isHeaderChecked: boolean = false;
    public showEmptyPanel = false;

    private currentFilter: string;

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

        this.loadQueryParams();
        this.loadButtons();
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
            this.provider.run(button.provider, {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                idProgramma: this.idProgramma,
                tipologia: this.tipologia,
                importData: this.checkedRows,
                isNoRiproposto: true
            }).subscribe((data: IDictionary<any>) => {
                if (has(data, 'reload') && get(data, 'reload') === true) {
                    this.checkedRows = new Array();
                    this.getData();
                }
            });
        }
    }

    public manageCheckboxes(idProgramma: string, idIntervento: string, event: any) {
        const isChecked = event.target.checked;
        const intervento = this.interventi?.find(i => toString(i.id) === toString(idIntervento) && toString(i.idProgramma) === toString(idProgramma))

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

        this.isHeaderChecked = this.areAllChecked(this.interventi);     
    }

    public manageAllCheck(event: any) {
        const isChecked = event.target.checked;

        this.interventi?.forEach(intervento => {
            intervento.checked = isChecked;
        });

        if(this.interventi?.length > 0) {
            if(isChecked) {
                this.checkedRows = this.interventi?.map(i => ({
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

    public areAllChecked(interventi: any) {
        return interventi?.length > 0 && interventi?.every(intervento => intervento.checked === true) 
    }

    // #endregion

    // #region Private

    private getData(): void {
        let factory = this.listaInterventiImportFactory(this.idProgramma);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: Array<InterventiDaRiproporre>) => {
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
                this.isReady = true;
                this.isHeaderChecked = false;

                if(this.interventi?.length === 0 || this.interventi == null || this.interventi == undefined) {
                    this.showEmptyPanel = true;
                }
            });
        });
    }


    private listaInterventiImportFactory(idProgramma: string): () => Observable<Array<InterventiDaRiproporre>> {
        return () => {
            return this.programmiService.getListaInterventiNRImport(this.idProgramma);
        }
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idProgramma = paramsMap.get('idProgramma');
        this.tipologia = paramsMap.get('tipologia');
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

        if (this.tipologia === '1') {
            this.buttonsSubj.next(this.buttons);
        } else if (this.tipologia === '2') {
            this.buttonsSubj.next(this.buttons2);
        }
    }

    // #endregion
}
