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
} from '@angular/core';
import { ProtectionUtilsService } from '@maggioli/app-commons';
import {
    SDK_APP_CONFIG,
    SdkAbstractComponent,
    SdkAppEnvConfig,
    SdkProviderService,
    SdkStoreService,
    UserProfile
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkRadioConfig,
    SdkRadioOutput
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { get, has, isEmpty, isObject } from 'lodash-es';
import { Constants } from 'projects/app-programmi/src/app/app.constants';
import { BehaviorSubject, Observable, of } from 'rxjs';



@Component({
    templateUrl: `import-interventi-modal.widget.html`,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ImportInterventiModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables
    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private userProfile: UserProfile;
    private choice: string ='1';


    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj, cdr) }

    // #region Hooks

    protected onAfterViewInit(): void {   


    
    }

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));
        this.loadButtons();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        this.markForCheck(() => {
            this.config = { ...config };
            this.isReady = true;
        });
    }

    protected onData(_data: void): void { }

    /**
     * @ignore
     */
    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    public radioConfig: Observable<SdkRadioConfig> = of({
        code: 'id-radio',
        label:  '',
        choices: [
          {
            code: '1',
            label: this.translateService.instant('IMPORT-INTERVENTO.RADIO-LABEL1'),
            checked: true
          },
          {
            code: '2',
            label: this.translateService.instant('IMPORT-INTERVENTO.RADIO-LABEL2'),
          },
          {
            code: '3',
            label: this.translateService.instant('IMPORT-INTERVENTO.RADIO-LABEL3'),
          }
        ]
      } as SdkRadioConfig);
    
          
      public onOutput(item: SdkRadioOutput): void {
        if(isObject(item) && isObject(item.data)){
            this.choice = item.data.code;
        }
      }

      public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {

            if(button.code === 'modal-close-button'){
                this.choice= undefined;
            }

            this.provider.run('APP_PROGRAMMI_MODAL_WINDOW', {
                type: 'BUTTON',
                data: {
                    code: 'modal-close-button'
                }
            }).subscribe(this.manageExecutionProvider)
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (has(obj, 'close') && get(obj, 'close') === true) {
                this.emitOutput(this.choice);
            }
        }
    }


      private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);

    }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }
}

