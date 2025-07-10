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
import { Router } from '@angular/router';
import {
    HttpRequestHelper,
    ProfiloConfiguration,
    ProfiloInfo,
    StazioneAppaltanteInfo,
    UserService,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkRouterService,
    SdkStoreAction,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import { SdkMessagePanelService } from '@maggioli/sdk-controls';
import { get, head, isObject, size, toString } from 'lodash-es';
import { fromEvent, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged } from 'rxjs/operators';

import { environment } from '../../../../../../environments/environment';
import { Constants } from '../../../../../app.constants';

@Component({
    templateUrl: `choose-profile-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ChooseProfileSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `choose-profile-section-widget`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('content') _content: ElementRef;

    public config: any = {};

    private userProfile: UserProfile;

    public profili: Array<ProfiloInfo>;

    private debounce: Subject<any> = new Subject<any>();

    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;

    constructor(inj: Injector, cdr: ChangeDetectorRef, private router: Router) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.addSubscription(this.debounce.pipe(
            debounceTime(350),
            distinctUntilChanged())
            .subscribe((value: IDictionary<number>) => {
                this.resizeComponent(value.offsetTop, value.viewportHeight);
            }));
    }

    protected onAfterViewInit(): void {
        this.initResizeObservable();
        this.getProfiles();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get userService(): UserService { return this.injectable(UserService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Private

    private getProfiles(): void {
        this.addSubscription(
            this.store.select<StazioneAppaltanteInfo>(Constants.SA_INFO_SELECT).subscribe((sa: StazioneAppaltanteInfo) => {
                this.stazioneAppaltanteInfo = sa;
                if (this.stazioneAppaltanteInfo != null && this.stazioneAppaltanteInfo.profili != null) {
                    this.profili = get(this.stazioneAppaltanteInfo, 'profili');
                    if (size(this.profili) === 1) {
                        let first: ProfiloInfo = head(this.profili);
                        this.selezionaProfilo(first.codProfilo);
                    } else {
                        this.markForCheck();
                        if(size(this.profili) === 0){
                            this.sdkMessagePanelService.showError(this.messagesPanel, [
                                {
                                    message: 'ERRORS.NO-PROFILES'
                                }
                            ]);
                        }
                    }
                }
            })
        );
    }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private selezionaProfilo(codiceProfilo: string): void {

        let getSelezioneProfilo = this.getSelezioneProfiloFactory(codiceProfilo);
        this.requestHelper.begin(getSelezioneProfilo, this.messagesPanel).subscribe(this.manageSelezioneProfilo);
    }

    private getSelezioneProfiloFactory(codiceProfilo: string) {
        return () => {
            return this.userService.getConfigProfilo(environment.GESTIONE_AUTENTICAZIONE_MS_BASE_URL, codiceProfilo)
        }
    }

    private manageSelezioneProfilo = (response: ProfiloConfiguration) => {

        // salvataggio utente aggiornato con campo syscon
        this.userProfile = {
            ...this.userProfile,
            configurations: response
        };

        this.store.dispatch(new SdkStoreAction(Constants.USER_PROFILE_DISPATCHER, this.userProfile));
        this.routerService.navigateToPage('home-page');
    }

    private get content(): HTMLElement {
        return this._content.nativeElement;
    }

    private initResizeObservable(): void {
        if (this.content) {
            const initialOffsetTop: number = this.content.offsetTop;
            const initialViewportHeight: number = window.innerHeight;
            this.resizeComponent(initialOffsetTop, initialViewportHeight);
            this.addSubscription(fromEvent(window, 'resize').subscribe((event: any) => {
                const currentOffsetTop: number = this.content.offsetTop;
                const currentViewportHeight: number = event.target.innerHeight;
                this.debounce.next({
                    offsetTop: currentOffsetTop,
                    viewportHeight: currentViewportHeight
                });
            }));
        }
    }

    private resizeComponent(offsetTop: number, viewportHeight: number): void {
        if (isFinite(offsetTop) && isFinite(viewportHeight) && viewportHeight >= offsetTop) {
            const totalHeight: number = viewportHeight - offsetTop;
            this.content.style.height = `${totalHeight}px`;
        }
    }

    // #endregion

    // #region Public

    public trackByCodProfilo(index: number, p: ProfiloInfo): string {
        return isObject(p) ? p.codProfilo : toString(index);
    }

    // #endregion
}
