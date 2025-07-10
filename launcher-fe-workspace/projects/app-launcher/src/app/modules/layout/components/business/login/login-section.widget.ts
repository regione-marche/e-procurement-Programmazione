import {
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
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkLocalStorageService,
    SdkRedirectService,
    SdkRouterService,
    SdkSessionStorageService
} from '@maggioli/sdk-commons';
import { AppHomeIndexPanel } from '@maggioli/sdk-widgets';
import { TranslateService } from '@ngx-translate/core';
import { filter, get } from 'lodash-es';

import { environment } from '../../../../../../environments/environment';
import { Constants } from '../../../../../app.constants';
import { ApplicationLoginConfiguration, ApplicationLoginSSOConfiguration } from '../../../../models/application.model';

@Component({
    templateUrl: `login-section.widget.html`,
    encapsulation: ViewEncapsulation.None
})
export class LoginSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `login-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any = {};

    public isError: boolean = false;
    public error: string = '';
    public showInternalAuthentication: boolean = false;
    public ssoConfigurations: Array<ApplicationLoginSSOConfiguration>;
    public internalAuthDescription: string = '';
    public isMultiApp: boolean = true;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks


    protected onInit(): void {

        const appLoginConfig: ApplicationLoginConfiguration = this.getApplicationLoginConfiguration();
        this.isMultiApp = environment.SINGLE_APP === false;

        this.ssoConfigurations = this.loadSSOConfigurations(appLoginConfig.SSO);
        this.showInternalAuthentication = !!appLoginConfig.AUTHENTICATION_INTERNAL_ENABLED;
        if (this.showInternalAuthentication === true) {
            this.internalAuthDescription = this.translateService.instant('LOGIN-PAGE.INTERNAL.DESCRIPTION')
        }

        this.checkSingleAuthenticationMethods();
    }

    protected onAfterViewInit(): void {
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (config != null) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true;
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Public

    public loginSSO(item: ApplicationLoginSSOConfiguration): void {

        if (item.AUTHENTICATION_SSO_INTERNAL_PAGE_URL != null) {
            this.sdkLocalStorageService.removeItem(Constants.SPID_AUTH_ID, Constants.LOCAL_STORAGE_PREFIX);
            this.sdkRouterService.navigateToPage(item.AUTHENTICATION_SSO_INTERNAL_PAGE_URL, item.AUTHENTICATION_SSO_INTERNAL_PAGE_PARAMS);
        }
        else if (item.AUTHENTICATION_SSO_LOGIN_URL != null) {
            this.sdkRedirectService.redirect(item.AUTHENTICATION_SSO_LOGIN_URL, item.AUTHENTICATION_SSO_LOGIN_URL_PARAMS);
        } else {
            this.logger.error('AUTHENTICATION_SSO_LOGIN_URL null');
        }
    }

    public loginInternal(): void {
        const appLoginConfig: ApplicationLoginConfiguration = this.getApplicationLoginConfiguration();
        if (appLoginConfig.AUTHENTICATION_INTERNAL_LOGIN_URL != null) {
            const moduloSelezionato: AppHomeIndexPanel = this.sdkSessionStorageService.getItem<AppHomeIndexPanel>(Constants.CURRENT_MODULE, Constants.LOCAL_STORAGE_PREFIX);
            let params: IDictionary<string> = {
                    appCode: moduloSelezionato.panelCode,
                }
            this.sdkRedirectService.redirect(appLoginConfig.AUTHENTICATION_INTERNAL_LOGIN_URL,params);
        } else {
            this.logger.error('AUTHENTICATION_INTERNAL_LOGIN_URL null');
        }
    }

    public getSSOTitle(item: ApplicationLoginSSOConfiguration): string {
        return `LOGIN-PAGE.SSO-${item.AUTHENTICATION_SSO_CODE}.TITLE`;
    }

    public getSSOButton(item: ApplicationLoginSSOConfiguration): string {
        return `LOGIN-PAGE.SSO-${item.AUTHENTICATION_SSO_CODE}.BUTTON`;
    }

    public getSSODescription(item: ApplicationLoginSSOConfiguration): string {
        return `LOGIN-PAGE.SSO-${item.AUTHENTICATION_SSO_CODE}.DESCRIPTION`;
    }

    public goBack(): void {
        this.sdkRouterService.navigateToPage('home-page');
    }

    // #endregion

    // #region Getters

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    private get sdkRouterService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get sdkRedirectService(): SdkRedirectService { return this.injectable(SdkRedirectService) }

    private get sdkLocalStorageService(): SdkLocalStorageService { return this.injectable(SdkLocalStorageService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }


    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return this._messagesPanel != null ? this._messagesPanel.nativeElement : undefined;
    }

    private getApplicationLoginConfiguration(): ApplicationLoginConfiguration {
        const moduloSelezionato: AppHomeIndexPanel = this.sdkSessionStorageService.getItem<AppHomeIndexPanel>(Constants.CURRENT_MODULE, Constants.LOCAL_STORAGE_PREFIX);
        if (moduloSelezionato == null) {
            console.log('no module!!!!!');
            this.sdkRouterService.navigateToPage('home-page');
        }

        const panelCode: string = moduloSelezionato.panelCode;

        const appLoginConfig: ApplicationLoginConfiguration = get(environment.PANELS_CONFIGURATION, panelCode);

        if (appLoginConfig == null) {
            this.sdkRouterService.navigateToPage('home-page');
        }

        return appLoginConfig;
    }

    private clearAuthInfo(): void {
        this.sdkSessionStorageService.removeItem(Constants.AUTHENTICATION_TOKEN_INFO, Constants.LOCAL_STORAGE_PREFIX);
        this.sdkSessionStorageService.removeItem(Constants.CURRENT_MODULE, Constants.LOCAL_STORAGE_PREFIX);
    }

    private loadSSOConfigurations(configurations: Array<ApplicationLoginSSOConfiguration>): Array<ApplicationLoginSSOConfiguration> {

        if (configurations != null && configurations.length > 0) {
            return filter(configurations, (one: ApplicationLoginSSOConfiguration) => one.AUTHENTICATION_SSO_ENABLED === true);
        }

        return [];
    }

    private checkSingleAuthenticationMethods(): void {

        let i: number = 0;
        i += this.ssoConfigurations.length;

        if (this.showInternalAuthentication)
            i++;

        let hasSingleAuthenticationMethod: boolean = i == 1;

        if (hasSingleAuthenticationMethod) {

            this.isReady = false;

            if (this.showInternalAuthentication) {
                // controllo internal
                this.loginInternal();
            } else {
                // controllo SSO
                let ssoItem: ApplicationLoginSSOConfiguration = this.ssoConfigurations[0];
                this.loginSSO(ssoItem);
            }

        }
    }



    // #endregion
}
