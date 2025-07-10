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
import { isObject, toString } from 'lodash-es';
import { Observable } from 'rxjs';

import { Constants } from '../../app-commons.constants';
import { ResponseResult } from '../../models/common/common.model';
import { MessageEntry } from '../../models/tabellati/tabellato.model';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';
import { TabellatiService } from '../../services/tabellati/tabellati.service';

@Component({
    templateUrl: `messaggi-avvisi-overlay.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class MessaggiAvvisiOverlayWidget extends SdkAbstractComponent<any, void, void> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    private userProfile: UserProfile;
    public messaggiInArrivo: Array<MessageEntry>;

    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onAfterViewInit(): void {
        this.load();
    }

    protected onInit(): void {
        this.isReady = true;
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void { }

    protected onOutput(_data: void): void { }

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

    private getMessaggiFactory(origin: string): () => Observable<Array<MessageEntry>> {
        return () => {
            return this.tabellatiService.getMessages(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, this.userProfile.syscon, origin);
        }
    }

    private load(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
            if (isObject(this.userProfile)) {
                this.loadMessaggiInArrivo();
            }
        }));
    }

    private loadMessaggiInArrivo(): void {
        let factory = this.getMessaggiFactory(Constants.INBOX_ORIGIN);
        this.requestHelper.begin(factory, this.messagesPanel, false).subscribe((result: Array<MessageEntry>) => {
            this.markForCheck(() => this.messaggiInArrivo = result);
        });
    }

    private setMessageSeenFactory(messageId: number, letto: number): () => Observable<any> {
        return () => {
            return this.tabellatiService.setMessageSeen(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, messageId, letto);
        }
    }

    // #endregion

    // #region Public

    public trackByCode(index: number, message: MessageEntry): string {
        return isObject(message) ? toString(message.id) + toString(message.letto) : toString(index);
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

    public deleteCurrentMessaggioInArrivo(messaggio: MessageEntry): void {
        if (isObject(messaggio)) {
            let factory = this.deleteCurrentMessageFactory(messaggio.id);
            this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: ResponseResult<boolean>) => {
                this.loadMessaggiInArrivo();
            });
        }
    }

    private deleteCurrentMessageFactory(messageId: number): () => Observable<ResponseResult<boolean>> {
        return () => {
            return this.tabellatiService.deleteCurrentMessage(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, messageId);
        }
    }

    // #endregion

    // #region Getters

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    // #endregion
}
