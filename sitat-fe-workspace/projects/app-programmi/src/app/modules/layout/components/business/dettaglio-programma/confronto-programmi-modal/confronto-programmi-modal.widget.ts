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
import { HttpRequestHelper, ProtectionUtilsService } from '@maggioli/app-commons';
import { SdkAbstractComponent, SdkProviderService, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
import {
    SdkBasicButtonOutput,
    SdkButtonGroupInput
} from '@maggioli/sdk-controls';
import { get, has, isEmpty, isObject, toString } from 'lodash-es';
import { BehaviorSubject, Observable, throwError } from 'rxjs';
import { Constants } from '../../../../../../app.constants';
import { InterventiDaConfrontoDTO, ProgrammaBaseEntry } from '../../../../../models/programmi/programmi.model';
import { ProgrammiService } from '../../../../../services/programmi/programmi.service';
import { catchError, map } from 'rxjs/operators';
import { TranslateService } from '@ngx-translate/core';

@Component({
    templateUrl: `confronto-programmi-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ConfrontoProgrammiModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    private buttons: SdkButtonGroupInput;
    private buttonsInterventi: SdkButtonGroupInput;
    private userProfile: UserProfile;
    private programmaDaConfronto: ProgrammaBaseEntry;

    public config: any;
    public programmiDaConfronto: Array<ProgrammaBaseEntry>;
    public idProgramma: string;
    public data: any;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public showProgrammi: boolean = true;
    public interventiDaConfronto: Array<InterventiDaConfrontoDTO>;

    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.loadProgrammi();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.idProgramma = this.config.idProgramma;
                this.isReady = true;
            });
        }
    }

    protected onOutput(data: any): void { }


    protected onData(_data: void): void { }

    /**
     * @ignore
     */
    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.data.buttons, this.userProfile.configurations)
        };
        this.buttonsInterventi = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.data.buttonsInterventi, this.userProfile.configurations)
        };
        this.buttonsSubj.next(this.buttons);
    }

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && has(obj, 'close') && get(obj, 'close') === true) {
            this.emitOutput(undefined);
        }
    }

    private loadProgrammi(): void {
        this.loadListaProgrammiDaConfronto()
            .pipe(
                map(this.elaborateListaProgrammiDaConfronto),
                catchError(this.handleError)
            ).subscribe(() => {
                this.markForCheck();
            });
    }

    private listaProgrammiDaConfrontoFactory(contri: string): () => Observable<Array<ProgrammaBaseEntry>> {
        return () => {
            return this.programmiService.getListaProgrammiDaConfronto(contri);
        }
    }

    private loadListaProgrammiDaConfronto = (): Observable<Array<ProgrammaBaseEntry>> => {
        let factory = this.listaProgrammiDaConfrontoFactory(this.config.contri);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateListaProgrammiDaConfronto = (programmiDaConfronto: Array<ProgrammaBaseEntry>) => {
        this.programmiDaConfronto = programmiDaConfronto;
        // this.programmiDaConfronto = [];
    }

    private listaInterventiDaConfrontoFactory(contri: string, contriDaConfrontare: string): () => Observable<Array<InterventiDaConfrontoDTO>> {
        return () => {
            return this.programmiService.getListaInterventiDaConfronto(contri, contriDaConfrontare);
        }
    }

    private loadListaInterventiDaConfronto = (contriDaConfrontare: string): Observable<Array<InterventiDaConfrontoDTO>> => {
        let factory = this.listaInterventiDaConfrontoFactory(this.config.contri, contriDaConfrontare);
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateListaInterventiDaConfronto = (interventiDaConfronto: Array<InterventiDaConfrontoDTO>) => {
        this.markForCheck(() => {
            this.interventiDaConfronto = interventiDaConfronto;
            this.showProgrammi = false;
            this.buttonsSubj.next(this.buttonsInterventi);
        });
    }

    private handleError = (err: any) => {
        this.programmiDaConfronto = [];
        return throwError(() => err);
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkBasicButtonOutput): void {
        if (button != null) {
            if (button.code === 'indietro') {
                this.markForCheck(() => {
                    this.showProgrammi = true;
                    delete this.interventiDaConfronto;
                    delete this.programmaDaConfronto;
                    delete this.programmiDaConfronto;
                    this.buttonsSubj.next(this.buttons);
                    this.loadProgrammi();
                });
            } else if (!isEmpty(button.provider)) {
                this.provider.run(button.provider, {
                    data: {
                        code: button.code
                    },
                    type: 'BUTTON',
                    buttonCode: button.code,
                    contri: this.config.contri,
                    idProgramma: this.idProgramma,
                    contriDaConfrontare: this.programmaDaConfronto?.id,
                    idProgrammaDaConfrontare: this.programmaDaConfronto?.idProgramma,
                    messagesPanel: this.messagesPanel
                }).subscribe(this.manageExecutionProvider)
            }
        }
    }

    public manageSelectProgramma(programma: ProgrammaBaseEntry): void {
        if (programma != null) {
            this.programmaDaConfronto = programma;
            this.loadListaInterventiDaConfronto(toString(programma.id)).pipe(
                map(this.elaborateListaInterventiDaConfronto)
            ).subscribe();
        }
    }

    public getVariazione(intervento: InterventiDaConfrontoDTO): string {
        switch (intervento.variazione) {
            case 'TABELLATO':
                return intervento.descrizioneTabellato;
            case 'AGGIUNTO':
            case 'ELIMINATO':
            case 'VARIATA_ANNUALITA':
            case 'VARIATO_QUADRO_ECONOMICO':
                return this.translateService.instant(`CONFRONTO-PROGRAMMI.${intervento.variazione}`);
            default:
                return intervento.variazione;
        }
    }

    // #endregion

    // #region Getters

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion
}
