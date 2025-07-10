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
import { Title } from '@angular/platform-browser';
import {
    SDK_APP_CONFIG,
    SdkAbstractComponent,
    SdkAppEnvConfig,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkButtonGroupOutput } from '@maggioli/sdk-controls';
import { get, has, isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject, Observable } from 'rxjs';

import packageIson from '../../../../../../../../package.json';
import { Constants } from '../../app-commons.constants';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';
import { HttpRequestHelper } from '../../services/http/http-request-helper.service';
import { AppInfoEntry } from '../../models/appinfo/app-info.model';
import { AppInfoService } from '../../services/appinfo/app-info.service';

@Component({
    templateUrl: `app-info-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppInfoModalModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    private buttons: SdkButtonGroupInput;

    public userProfile: UserProfile;
    public config: any;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public appTitle: string;
    public appVersion: string;
    public showInfoSviluppo: boolean = false;

    public nomeComponente: string;
    public versioneComponente: string;
    public nomePiattaforma: string;
    public versionePiattaforma: string;

    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) public appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        console.log("version: ", packageIson.version)
        this.appTitle = this.title.getTitle();
        this.appVersion = packageIson.version;
        this.showInfoSviluppo = this.appConfig.environment.DEVELOPMENT_INFO != null && this.appConfig.environment.DEVELOPMENT_INFO.trim() != '';
    
        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        let factory = this.getAppInfo();
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((res:AppInfoEntry)=>{
            this.markForCheck(() => {
                if(res != null){
                    this.nomeComponente = res.nomeComponente
                    this.versioneComponente = res.versioneComponente
                    this.nomePiattaforma = res.nomePiattaforma
                    this.versionePiattaforma = res.versionePiattaforma
                }                                    
            });
        });

        
    }

    protected onDestroy(): void { }

    protected onOutput(_data: void): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true;
            });
        }
    }

    protected onData(_data: void): void { }

    /**
     * @ignore
     */
    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get title(): Title { return this.injectable(Title) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get appInfoService(): AppInfoService { return this.injectable(AppInfoService) }

    


    // #endregion

    // #region Private

    private getAppInfo(): () => Observable<AppInfoEntry> {
        return () => {
            return this.appInfoService.getAppinfo(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL);
        }
    }

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

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider.run(button.provider, {
                type: 'BUTTON',
                data: {
                    code: button.code,
                    messagesPanel: this.messagesPanel
                }
            }).subscribe(this.manageExecutionProvider)
        }
    }

    // #endregion
}
