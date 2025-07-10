import { HttpErrorResponse } from '@angular/common/http';
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
    ViewEncapsulation
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkMessagePanelTranslate
} from '@maggioli/sdk-controls';
import { isEmpty, map as mapArray, remove, toString } from 'lodash-es';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';
import { ResponseDTO, ResponseListaDTO, UfficioIntestatarioDTO } from '../../model/lib.model';
import { WRicercheDTO } from '../../model/lista-report.model';
import { SdkGestioneReportConstants } from '../../sdk-gestione-report.constants';
import { GestioneReportService } from '../../services/gestione-report.service';
import { ProtectionUtilsService } from '../../utils/protection-utils.service';
import { SdkDettaglioReportStoreService } from '../sdk-dati-generali-report/sdk-dati-generali-report-store.service';


@Component({
    templateUrl: `./sdk-uffici-intestatari-report.component.html`,
    styleUrl: './sdk-uffici-intestatari-report.component.scss',
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class SdkUfficiIntestatariReportComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `sdk-uffici-intestatari-report-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public uffIntReport: Array<UfficioIntestatarioDTO>;

    private buttons: SdkButtonGroupInput;
    private buttonsReadonly: SdkButtonGroupInput;
    private userProfile: UserProfile;
    private syscon: number;
    private menuTabs: Array<SdkMenuTab>;
    private dettaglioReport: WRicercheDTO;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    private idRicerca: number;
    private idProfilo: string;
    private abilitazioneUffInt: number;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.addSubscription(this.store.select<UserProfile>(SdkGestioneReportConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQueryParams();
    }

    protected onAfterViewInit(): void {
        this.loadUffIntReport()
            .pipe(
                map(this.elaborateUffIntReport),
                map(this.elaborateButtons),
                mergeMap(this.loadDettaglioReport),
                map(this.elaborateDettaglioReport),
                mergeMap(this.loadAbilitazioneUffInt),
                map(this.elaborateConfigUffInt),
                map(() => this.refreshTabs()),
                map(() => this.checkInfoBox()),
                catchError(this.handleError)
            ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (config != null) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
                this.isReady = true
            });
        }
    }

    protected onUpdateState(_state: boolean): void { }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get gestioneReportService(): GestioneReportService { return this.injectable(GestioneReportService) }

    private get sdkDettaglioReportStoreService(): SdkDettaglioReportStoreService { return this.injectable(SdkDettaglioReportStoreService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (button != null && isEmpty(button.provider) === false) {
            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                setUpdateState: this.setUpdateState,
                syscon: this.syscon,
                idRicerca: this.sdkDettaglioReportStoreService.idRicerca
            };

            this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
        }
    }

    public trackByCode(index: number, sa: UfficioIntestatarioDTO): string {
        return sa != null ? sa.codice : toString(index);
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return this._messagesPanel != null ? this._messagesPanel.nativeElement : undefined;
    }

    private get infoBox(): HTMLElement {
        return this._infoBox != null ? this._infoBox.nativeElement : undefined;
    }

    private checkInfoBox(): void {
        if (!isEmpty(this.config.infoBox)) {
            this.sdkMessagePanelService.clear(this.infoBox);
            this.sdkMessagePanelService.showInfoBox(this.infoBox, {
                message: this.config.infoBox
            });
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };

        this.buttonsReadonly = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsReadonly, this.userProfile.configurations)
        };
    }

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.cleanSearch === true) {
            //this.loadForm();
        }
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.syscon = +this.userProfile.syscon;
        this.idRicerca = +paramsMap.get('idRicerca');
        this.idProfilo = this.userProfile?.configurations?.idProfilo;
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
                let visible: boolean = this.provider.run(one.visible, {
                    utente: this.dettaglioReport,
                    userProfile: this.userProfile,
                    configUffInt: this.abilitazioneUffInt
                });
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);
    }

    private loadAbilitazioneUffInt = (): Observable<ResponseListaDTO<number>> => {

        return this.gestioneReportService.getAbilitazioneUffInt()
            .pipe(
            catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                let err: ResponseDTO<any> = error.error;
                if (err != null && err.messages != null && err.messages.length > 0) {
                    let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
                        let message: SdkMessagePanelTranslate = {
                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                        };
                        return message;
                    });
                    this.sdkMessagePanelService.showError(this.messagesPanel, messages);
                }
                return throwError(() => error);
            })
        );
    }

    private elaborateConfigUffInt = (result: ResponseListaDTO<number>) => {
        this.abilitazioneUffInt = result.response;
    }

    private loadUffIntReport = (): Observable<ResponseDTO<Array<UfficioIntestatarioDTO>>> => {
        return this.gestioneReportService.getListaUfficiIntestatariReport(this.idRicerca)
        .pipe(
            catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                let err: ResponseDTO<any> = error.error;
                if (err != null && err.messages != null && err.messages.length > 0) {
                    let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
                        let message: SdkMessagePanelTranslate = {
                            message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                        };
                        return message;
                    });
                    this.sdkMessagePanelService.showError(this.messagesPanel, messages);
                }
                return throwError(() => error);
            })
        );
    }

    private elaborateUffIntReport = (result: ResponseDTO<Array<UfficioIntestatarioDTO>>) => {
        this.uffIntReport = result.response;
    }

    private elaborateButtons = () => {
        this.buttonsSubj.next(this.buttons);
    }

    private handleError = (err: any) => {
        this.buttonsSubj.next(this.buttonsReadonly);
        return throwError(() => err);
    }

    private loadDettaglioReport = (): Observable<ResponseDTO<WRicercheDTO>> => {

        return this.gestioneReportService.getDetailReport(this.sdkDettaglioReportStoreService.idRicerca)
            .pipe(
            catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                let err: ResponseDTO<any> = error.error;
                if (err != null && err.messages != null && err.messages.length > 0) {
                let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
                    let message: SdkMessagePanelTranslate = {
                    message: `SDK-DETTAGLIO-REPORT.VALIDATORS.${one}`
                    };
                    return message;
                });
                this.sdkMessagePanelService.showError(this.messagesPanel, messages);
                }
                return throwError(() => error);
            })
        );
    }
    
    private elaborateDettaglioReport = (result: ResponseDTO<WRicercheDTO>) => {
        this.dettaglioReport = result.response;
    }

  // #endregion
}
