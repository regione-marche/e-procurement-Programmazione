import {
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
import {  SDK_APP_CONFIG, SdkAppEnvConfig, SdkBusinessAbstractWidget, SdkHttpLoaderService, SdkHttpLoaderType, SdkProviderService,  } from '@maggioli/sdk-commons';
import { isObject } from 'lodash-es';
import { OAuthService } from 'angular-oauth2-oidc';

@Component({
    templateUrl: `oauth-login-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.Default
})
export class OauthLoginSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit,  OnDestroy {

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any = {};
    
    protected onInit(): void { 
        this.sdkHttpLoaderService.showLoader(SdkHttpLoaderType.Operation);            
        this.oauthService.initCodeFlow();
    }
    
    protected onConfig(config: void): void {
        this.config= config;  
    }

    protected back(): void {  
        this.provider.run(this.config.body.buttonProvider, {
            type: 'BUTTON',
            data: {
                code: 'logout',
                messagesPanel: this.messagesPanel
            }
        }).subscribe();     
    }   

    protected onDestroy(): void {

    }
    protected onAfterViewInit(): void {
    }

    protected onUpdateState(state: boolean): void {

    }

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    private get oauthService(): OAuthService { return this.injectable(OAuthService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private get sdkHttpLoaderService(): SdkHttpLoaderService { return this.injectable(SdkHttpLoaderService) }
  
}
