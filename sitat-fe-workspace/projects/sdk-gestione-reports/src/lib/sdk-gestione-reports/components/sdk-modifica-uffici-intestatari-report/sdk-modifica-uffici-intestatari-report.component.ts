import { HttpErrorResponse } from '@angular/common/http';
import {
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    FilterObject,
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkMessagePanelService,
    SdkMessagePanelTranslate,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { each, find, findIndex, isEmpty, map as mapArray } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject, throwError } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';

import { ResponseDTO, UfficioIntestatarioCheckDTO, UfficioIntestatarioDTO } from '../../model/lib.model';
import { SdkGestioneReportConstants } from '../../sdk-gestione-report.constants';
import { GestioneReportService } from '../../services/gestione-report.service';
import { ProtectionUtilsService } from '../../utils/protection-utils.service';
import { SdkDettaglioReportStoreService } from '../sdk-dati-generali-report/sdk-dati-generali-report-store.service';

@Component({
    templateUrl: './sdk-modifica-uffici-intestatari-report.component.html',
    styleUrls: []
})
export class SdkModificaUfficiIntestatariReportComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `sdk-modifica-uffici-intestatari-report-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};

    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    public listaUffIntEdit: Array<UfficioIntestatarioCheckDTO>;

    private buttons: SdkButtonGroupInput;
    private userProfile: UserProfile;
    private syscon: number;
    private dialogConfig: SdkDialogConfig;
    private listaUffInt: Array<UfficioIntestatarioDTO>;
    private listaUffIntReport: Array<UfficioIntestatarioDTO>;
    private currentFilter: FilterObject<UfficioIntestatarioCheckDTO>;
    private idRicerca: number;
    private idProfilo: string;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        // set update state
        this.setUpdateState(true);

        this.addSubscription(this.store.select<UserProfile>(SdkGestioneReportConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.checkInfoBox();
        this.loadListaUffInt()
        .pipe(
            map(this.elaborateListaUffInt),
            mergeMap(this.loadListaUffIntReport),
            map(this.elaborateListaUffIntReport),
            map(this.elaborateListaUffIntEdit)
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (config != null) {
            this.markForCheck(() => {
                this.config = { ...config };
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

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get gestioneReportService(): GestioneReportService { return this.injectable(GestioneReportService) }

    private get sdkDettaglioReportStoreService(): SdkDettaglioReportStoreService { return this.injectable(SdkDettaglioReportStoreService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (button != null && isEmpty(button.provider) === false) {
            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                syscon: this.syscon,
                setUpdateState: this.setUpdateState,
                listaUffInt: this.listaUffIntEdit,
                currentFilter: this.currentFilter,
                idRicerca: this.sdkDettaglioReportStoreService.idRicerca,
                idProfilo: this.idProfilo 
            };

            if (button.code === 'back-to-uffici-intestatari-report') {
                this.back(button, data);
            } else {
                this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
            }
        }
    }

    public manageCheckboxes(codiceSA: string, event: any): void {
        let index: number = findIndex(this.listaUffIntEdit, (one: UfficioIntestatarioCheckDTO) => one.codice === codiceSA);
        if (index != null && index > -1) {
            this.listaUffIntEdit[index].checked = event.target.checked === true;
        }
    }

    public onFilter(event: FilterObject<UfficioIntestatarioCheckDTO>): void {
        this.currentFilter = event;
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
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.listaUffIntEdit != null) {
            this.listaUffIntEdit = obj.listaUffIntEdit;
        }
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.syscon = +this.userProfile?.syscon;
        this.idRicerca = +paramsMap.get('idRicerca');
        this.idProfilo = this.userProfile?.configurations?.idProfilo;
    }

    private loadListaUffInt = (): Observable<ResponseDTO<Array<UfficioIntestatarioDTO>>> => {
        return this.gestioneReportService.getListaUfficiIntestatari()
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

    private elaborateListaUffInt = (result: ResponseDTO<Array<UfficioIntestatarioDTO>>) => {
        this.listaUffInt = result.response;
    }

    private loadListaUffIntReport = (): Observable<ResponseDTO<Array<UfficioIntestatarioDTO>>> => {
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

    private elaborateListaUffIntReport = (result: ResponseDTO<Array<UfficioIntestatarioDTO>>) => {
        this.listaUffIntReport = result.response;
    }

    private elaborateListaUffIntEdit = () => {
        if (this.listaUffInt != null && this.listaUffInt.length > 0) {
            // carico la lista di tutte le stazioni appaltanti
            this.listaUffIntEdit = mapArray(this.listaUffInt, (one: UfficioIntestatarioDTO) => {
                return {
                    ...one,
                    checked: false
                };
            });
        }

        if (this.listaUffIntReport != null && this.listaUffIntReport.length > 0) {
            // applico il checked a quelle già assegnate all'utente
            each(this.listaUffIntEdit, (one: UfficioIntestatarioCheckDTO) => {
                let saPresente: UfficioIntestatarioDTO = find(this.listaUffIntReport, (item: UfficioIntestatarioDTO) => one.codice === item.codice);
                if (saPresente != null) {
                    one.checked = true;
                }
            });
        }
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.BACK-TITLE'),
            message: this.translateService.instant('DIALOG.BACK-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs = of(this.dialogConfig);
    }

    private back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        let func = this.backConfirm(button, data);
        this.dialogConfig.open.next(func);
    }

    private backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.provider.run(button.provider, data).subscribe();
        }
    }

    // #endregion

}
