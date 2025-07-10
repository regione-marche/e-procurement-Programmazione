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
import { HttpRequestHelper, ProtectionUtilsService } from '@maggioli/app-commons';
import {
    IDictionary,
    SdkAbstractComponent,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import { SdkButtonGroupInput, SdkButtonGroupOutput } from '@maggioli/sdk-controls';
import { each, forEach, get, has, isEmpty, isObject } from 'lodash-es';
import { BehaviorSubject, Observable } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { GareService } from '../../../../../services/gare/gare.service';


@Component({
    templateUrl: `matrice-atti-modal-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class MatriceAttiModalWidget  extends SdkAbstractComponent<any, void, any>  implements OnInit, OnDestroy {


    // #region Variables

    @HostBinding('class') classNames = `lotti-atto-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>  = new BehaviorSubject(null);
    private userProfile: UserProfile;
    private codGara: string;
    public columns: Array<String>;
    public rows: Array<any>;    
    public data: IDictionary<any>;
    public collapse: boolean = false;

    // #endregion

    constructor(
        inj: Injector,
        cdr: ChangeDetectorRef
    ) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));
        this.loadData();
    }

    protected onAfterViewInit(): void {
        
        this.loadButtons();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config }; 
                this.codGara = this.config.codGara;
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }
    protected onData(data: void): void {}
    protected onOutput(data: any): void {}

    // #endregion

    // #region Getters

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get gareService(): GareService { return this.injectable(GareService) }
 
    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            this.provider
                .run(button.provider, {
                    type: "BUTTON",
                    data: {
                        code: button.code,
                        messagesPanel: this.messagesPanel,
                    },
                })
                .subscribe(this.manageExecutionProvider);
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (has(obj, 'close') && get(obj, 'close') === true) {
                this.emitOutput('');
            }
        }
    }

    public checkCigPubblicato(cig : string, tipoAtto: string){
        let cigsAtto: Array<any> = get(this.data, tipoAtto);
        for(let i = 0; i< cigsAtto.length; i++){

            if(cig === cigsAtto[i].cig){
                if(cigsAtto[i].pubblicato === true){
                    return 'fas fa-flag matrice-atti-black-flag';
                } else {
                    return 'far fa-flag';
                }
            }
        }
        return '';
    }

    public collassaEspandi(): void {
        this.collapse = !this.collapse;
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadData(){
        let factory = this.loadMatriceFactory();
        return this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: any) => {
            this.markForCheck(() => {
                this.columns = result.columns;
                this.rows = result.rows;
                this.data = result.attiLotti;
            });
        });        
    }
    private loadMatriceFactory() {
        return () => {
            return this.gareService.matriceAtti(this.codGara);
        }
    }

    private loadButtons(): void {
        
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.buttons, this.userProfile.configurations)
        };

       this.buttonsSubj.next(this.buttons);
      
    }

    
    // #endregion
}
