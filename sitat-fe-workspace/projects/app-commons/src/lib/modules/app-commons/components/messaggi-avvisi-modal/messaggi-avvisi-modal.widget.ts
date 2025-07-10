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
import {
    SDK_APP_CONFIG,
    SdkAbstractComponent,
    SdkAppEnvConfig,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkAutocompleteConfig,
    SdkAutocompleteItem,
    SdkAutocompleteOutput,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDocumentsListConfig,
    SdkDocumentsListInput,
    SdkDocumentsListOutput,
    SdkMessagePanelService,
    SdkTextareaConfig,
    SdkTextareaInput,
    SdkTextareaOutput,
    SdkTextboxConfig,
    SdkTextboxInput,
    SdkTextboxOutput,
} from '@maggioli/sdk-controls';
import { each, findIndex, get, has, isEmpty, isObject, isUndefined, map as mapArray, remove, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../app-commons.constants';
import { MessageEntry, MessageForm, MessageReceiverEntry } from '../../models/tabellati/tabellato.model';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';
import { TabellatiService } from '../../services/tabellati/tabellati.service';
import { AbilitazioniUtilsService } from '../../services/utils/abilitazioni-utils.service';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';

@Component({
    templateUrl: `messaggi-avvisi-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class MessaggiAvvisiModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    public config: any;
    public data: any;
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private destinatariAutocomplete: SdkAutocompleteConfig;
    public destinatariAutocompleteConfig: Observable<SdkAutocompleteConfig>;
    public messaggiInArrivoVisible: boolean = true;
    public messaggiInviatiVisible: boolean = false;
    public nuovoMessaggioVisible: boolean = false;
    public isAmministratore: boolean = false;
    public messaggiInArrivoButtons: Observable<SdkButtonGroupInput>;
    public messaggiInviatiButtons: Observable<SdkButtonGroupInput>;
    public nuovoMessaggioButtons: Observable<SdkButtonGroupInput>;
    private userProfile: UserProfile;
    public messaggiInArrivo: Array<MessageEntry>;
    public messaggiInviati: Array<MessageEntry>;
    public bodyConfig: Observable<SdkTextareaConfig>;
    private body: string;
    private destinatari: Array<MessageReceiverEntry>;
    private destinatariSelezionati: SdkDocumentsListConfig;
    public destinatariSelezionatiConfig: Observable<SdkDocumentsListConfig>;
    public destinatariSelezionatiInput: Subject<SdkDocumentsListInput> = new Subject();
    public oggettoConfig: Observable<SdkTextboxConfig>;
    private oggetto: string;
    public oggettoData: Subject<SdkTextboxInput> = new Subject();
    public bodyData: Subject<SdkTextareaInput> = new Subject();

    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onAfterViewInit(): void { }

    protected onInit(): void {
        if (isUndefined(this.appConfig)) {
            this.emitOutput(undefined);
        }
        this.initNuovoMessaggio();
        this.initButtons();
        this.load();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true;
            });
        }
    }

    protected onOutput(_data: any): void { }

    protected onData(_data: void): void { }

    /**
     * @ignore
     */
    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (has(obj, 'close') && get(obj, 'close') === true) {
                this.emitOutput(undefined);
            }
        }
    }

    private initNuovoMessaggio(): void {
        this.destinatariAutocomplete = {
            code: 'destinatari',
            label: 'MESSAGGI-AVVISI.RICERCA-DESTINATARI',
            noDataLabel: 'NO-DATA',
            itemsProvider: (data?: string) => {
                let factory = this.getDestinatariFactory(data);
                return this.requestHelper.begin(factory, this.messagesPanel);
            },
            newEditAvailable: false,
            clearSubject: new Subject()
        };
        this.destinatariAutocompleteConfig = of(this.destinatariAutocomplete);
        this.destinatariSelezionati = {
            code: 'destinatari-selezionati',
            label: 'MESSAGGI-AVVISI.DESTINATARI-SELEZIONATI',
            documents: new Array(),
            clickable: false,
            clearSubject: new Subject()
        };
        this.destinatariSelezionatiConfig = of(this.destinatariSelezionati);
        this.bodyConfig = of({
            code: 'body',
            label: 'MESSAGGI-AVVISI.CORPO'
        } as SdkTextareaConfig);
        this.oggettoConfig = of({
            code: 'oggetto',
            label: 'MESSAGGI-AVVISI.OGGETTO'
        } as SdkTextboxConfig);
    }

    private getDestinatariFactory(data?: string): () => Observable<any> {
        return () => {
            return this.tabellatiService.getMessageReceiver(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, data)
                .pipe(
                    map((result: Array<MessageReceiverEntry>): Array<SdkAutocompleteItem> => {
                        let arr: Array<SdkAutocompleteItem> = [];
                        each(result, (tipo: MessageReceiverEntry) => {
                            let item: SdkAutocompleteItem = {
                                ...tipo,
                                text: tipo.nominativo,
                                _key: toString(tipo.syscon)
                            };
                            arr.push(item);
                        });
                        return arr;
                    })
                );
        }
    }

    private getMessaggiFactory(origin: string): () => Observable<Array<MessageEntry>> {
        return () => {
            return this.tabellatiService.getMessages(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, this.userProfile.syscon, origin);
        }
    }

    private initButtons(): void {
        this.messaggiInArrivoButtons = of({
            buttons: [
                {
                    code: 'messaggi-inviati',
                    title: 'BUTTONS.MESSAGGI-INVIATI',
                    icon: 'mgg-icons-action-upload'
                },
                {
                    code: 'nuovo-messaggio',
                    title: 'BUTTONS.NUOVO-MESSAGGIO',
                    icon: 'mgg-icons-crud-edit'
                }
            ]
        } as SdkButtonGroupInput);
        this.messaggiInviatiButtons = of({
            buttons: [
                {
                    code: 'messaggi-in-arrivo',
                    title: 'BUTTONS.MESSAGGI-IN-ARRIVO',
                    icon: 'mgg-icons-action-download'
                },
                {
                    code: 'nuovo-messaggio',
                    title: 'BUTTONS.NUOVO-MESSAGGIO',
                    icon: 'mgg-icons-crud-edit'
                }
            ]
        } as SdkButtonGroupInput);
        this.nuovoMessaggioButtons = of({
            buttons: [
                {
                    code: 'messaggi-inviati',
                    title: 'BUTTONS.MESSAGGI-INVIATI',
                    icon: 'mgg-icons-action-upload'
                },
                {
                    code: 'messaggi-in-arrivo',
                    title: 'BUTTONS.MESSAGGI-IN-ARRIVO',
                    icon: 'mgg-icons-action-download'
                },
                {
                    code: 'invia-messaggio',
                    title: 'BUTTONS.INVIA-MESSAGGIO',
                    icon: 'mgg-icons-crud-save'
                }
            ]
        } as SdkButtonGroupInput);
    }

    private load(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
            if (isObject(this.userProfile)) {
                this.loadButtons();
                let isAmministratore: boolean = this.abilitazioniUtilsService.isAmministratore(this.userProfile.abilitazioni);
                this.markForCheck(() => this.isAmministratore = isAmministratore);

                this.loadMessaggiInArrivo();
            }
        }));
    }

    private loadMessaggiInArrivo(): void {
        let factory = this.getMessaggiFactory(Constants.INBOX_ORIGIN);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: Array<MessageEntry>) => {
            this.markForCheck(() => this.messaggiInArrivo = result);
        });
    }

    private loadMessaggiInviati(): void {
        let factory = this.getMessaggiFactory(Constants.OUTBOX_ORIGIN);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: Array<MessageEntry>) => {
            this.markForCheck(() => this.messaggiInviati = result);
        });
    }


    private showMessaggiInArrivo(): void {
        this.markForCheck(() => {
            this.messaggiInArrivoVisible = true;
            this.messaggiInviatiVisible = false;
            this.nuovoMessaggioVisible = false;
            this.sdkMessagePanelService.clear(this.messagesPanel);
        });
        this.loadMessaggiInArrivo();
    }

    private showMessaggiInviati(): void {
        this.markForCheck(() => {
            this.messaggiInArrivoVisible = false;
            this.messaggiInviatiVisible = true;
            this.nuovoMessaggioVisible = false;
            this.sdkMessagePanelService.clear(this.messagesPanel);
        });
        this.loadMessaggiInviati();
    }

    private showNuovoMessaggio(): void {
        this.markForCheck(() => {
            this.messaggiInArrivoVisible = false;
            this.messaggiInviatiVisible = false;
            this.nuovoMessaggioVisible = true;
            this.sdkMessagePanelService.clear(this.messagesPanel);
        });
    }

    private addNewDestinatario(item: SdkAutocompleteItem): void {
        let receiver: MessageReceiverEntry = item as MessageReceiverEntry;
        let index: number = findIndex(this.destinatari, (one: MessageReceiverEntry) => one.syscon === receiver.syscon);
        if (index === -1) {
            if (isUndefined(this.destinatari)) {
                this.destinatari = new Array();
            }
            this.destinatari.push({ syscon: receiver.syscon, nominativo: receiver.nominativo });
            this.destinatariSelezionatiInput.next({
                item: {
                    code: toString(receiver.syscon),
                    titolo: receiver.nominativo
                }
            } as SdkDocumentsListInput);
            this.destinatariAutocomplete.clearSubject.next(true);
        }
    }

    private inviaMessaggio(): void {
        if (this.body && this.oggetto && !isEmpty(this.destinatari)) {
            let messageForm: MessageForm = {
                corpoMessaggio: this.body,
                oggetto: this.oggetto,
                dataMessaggio: new Date(),
                sysconSender: +this.userProfile.syscon,
                sysconReceivers: mapArray(this.destinatari, (one: MessageReceiverEntry) => one.syscon)
            };
            let factory = this.sendMessageFactory(messageForm);
            this.requestHelper.begin(factory, this.messagesPanel).subscribe((data: string) => {
                this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
                    {
                        message: 'MESSAGGI-AVVISI.MESSAGGIO-PUBBLICATO'
                    }
                ])
                this.reset();
            });
        }
    }

    private sendMessageFactory(form: MessageForm): () => Observable<any> {
        return () => {
            return this.tabellatiService.sendMessage(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, form);
        }
    }

    private reset(): void {
        this.markForCheck(() => {
            delete this.destinatari;
            delete this.body;
            delete this.oggetto;
        });
        this.destinatariAutocomplete.clearSubject.next(true);
        this.destinatariSelezionati.clearSubject.next(true);
        this.oggettoData.next({
            data: undefined
        } as SdkTextboxInput);
        this.bodyData.next({
            data: undefined
        } as SdkTextareaInput);
    }

    private setMessageSeenFactory(messageId: number, letto: number): () => Observable<any> {
        return () => {
            return this.tabellatiService.setMessageSeen(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, messageId, letto);
        }
    }

    private deleteMessageFactory(messageId: number, origin: string): () => Observable<any> {
        return () => {
            return this.tabellatiService.deleteMessage(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, messageId, origin);
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.data.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button)) {
            if (button.code === 'messaggi-inviati') {
                this.showMessaggiInviati();
            } else if (button.code === 'nuovo-messaggio') {
                this.showNuovoMessaggio();
            } else if (button.code === 'messaggi-in-arrivo') {
                this.showMessaggiInArrivo();
            } else if (button.code === 'invia-messaggio') {
                this.inviaMessaggio();
            } else if (isEmpty(button.provider) === false) {
                this.provider.run(button.provider, {
                    type: 'BUTTON',
                    data: {
                        code: button.code,
                        messagesPanel: this.messagesPanel
                    }
                }).subscribe(this.manageExecutionProvider)
            }
        }
    }

    public trackByCode(index: number, message: MessageEntry): string {
        return isObject(message) ? toString(message.id) + toString(message.letto) : toString(index);
    }

    public manageBodyOutput(data: SdkTextareaOutput): void {
        if (isObject(data)) {
            this.body = data.data;
        }
    }

    public manageDestinatariOutput(data: SdkAutocompleteOutput): void {
        if (isObject(data) && isObject(data.data)) {
            this.addNewDestinatario(data.data);
        }
    }

    public manageRemoveDestinatarioSelezionato(data: SdkDocumentsListOutput): void {
        if (isObject(data) && data.code) {
            let nCode: number = +data.code;
            remove(this.destinatari, (one: MessageReceiverEntry) => one.syscon === nCode);
        }
    }

    public manageOggettoOutput(data: SdkTextboxOutput): void {
        if (isObject(data)) {
            this.oggetto = data.data;
        }
    }

    public markReadUnread(messaggio: MessageEntry): void {
        if (isObject(messaggio)) {
            let letto: number = messaggio.letto === Constants.LETTO ? Constants.NON_LETTO : Constants.LETTO;
            let factory = this.setMessageSeenFactory(messaggio.id, letto);
            this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: any) => {
                this.loadMessaggiInArrivo();
            });
        }
    }

    public deleteMessaggioInArrivo(messaggio: MessageEntry): void {
        if (isObject(messaggio)) {
            let factory = this.deleteMessageFactory(messaggio.id, Constants.INBOX_ORIGIN);
            this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: any) => {
                this.loadMessaggiInArrivo();
            });
        }
    }

    public deleteMessaggioInviato(messaggio: MessageEntry): void {
        if (isObject(messaggio)) {
            let factory = this.deleteMessageFactory(messaggio.id, Constants.OUTBOX_ORIGIN);
            this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: any) => {
                this.loadMessaggiInviati();
            });
        }
    }

    // #endregion

    // #region Getters

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    // #endregion
}
