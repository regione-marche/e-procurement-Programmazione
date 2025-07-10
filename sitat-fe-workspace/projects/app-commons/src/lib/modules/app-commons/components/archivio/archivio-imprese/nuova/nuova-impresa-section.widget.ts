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
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkComboBoxItem,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, each, get, isEmpty, isEqual, isObject } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';

import { Constants } from '../../../../app-commons.constants';
import { StazioneAppaltanteInfo } from '../../../../models/stazione-appaltante/stazione-appaltante.model';
import { ValoreTabellato } from '../../../../models/tabellati/tabellato.model';
import { TabellatiCacheService } from '../../../../services/tabellati/tabellati-cache.service';
import { CustomParamsFunction, FormBuilderUtilsService } from '../../../../services/utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../../../services/utils/protection-utils.service';


@Component({
    templateUrl: `nuova-impresa-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaImpresaSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `nuova-impresa-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private userProfile: UserProfile;
    private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
    private stazioneAppaltante: string;
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;
    private provinceMap: IDictionary<string> = {};

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        // set update state
        this.setUpdateState(true);

        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltante = saInfo.codice;
        }));

        this.tabellatiCacheService.getValoriTabellato(Constants.PROVINCE).subscribe((province: Array<ValoreTabellato>) => {
            each(province, (provincia: ValoreTabellato) => {
                this.provinceMap[provincia.codistat] = provincia.codice;
            });
        });
       
        this.loadButtons();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.tabellatiCacheService.getValoriTabellati(Constants.IMPRESA_TABELLATI).subscribe((result: IDictionary<Array<ValoreTabellato>>) => {
            this.valoriTabellati = result;
            this.checkInfoBox();
            this.loadForm();
        });
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

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {

            let data: IDictionary<any> = {
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                setUpdateState: this.setUpdateState,
                stazioneAppaltante: this.stazioneAppaltante
            };

            if (button.code === 'back-to-archivio-imprese' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
                this.back(button, data);
            } else {
                this.provider.run(button.provider, data).subscribe();
            }
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputField(field: SdkFormBuilderField): void {
        if (isObject(field)) {
            if (field.code === 'comune') {
                this.manageComune(field);
            }
        }
    }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };


        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
            let mapping: boolean = true;


            if (field.code === 'ambito-territoriale') {
                let data: SdkComboBoxItem = { key: '1', value: '' };
                field.data = data;
                mapping = false;
            }

            return {
                mapping,
                field
            };
        }

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, true, false);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, false, customPopulateFunction);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;
        this.formBuilderConfigObs.next(formConfig);
    }

    private get infoBox(): HTMLElement {
        return isObject(this._infoBox) ? this._infoBox.nativeElement : undefined;
    }

    private checkInfoBox(): void {
        if (!isEmpty(this.config.infoBox)) {
            this.sdkMessagePanelService.clear(this.infoBox);
            this.sdkMessagePanelService.showInfoBox(this.infoBox, {
                message: this.config.infoBox
            });
        }
    }

    private manageCap(field: SdkFormBuilderField, capCode: string): void {
        let cap: string = undefined;
        if (field.data) {
            cap = field.data.cap;
        }
        let capField: any = {
            code: capCode,
            data: cap
        }
        if (field.groupCode) {
            capField.groupCode = field.groupCode;
        }

        this.formBuilderDataSubject.next(capField);
    }

    private manageComune(field: SdkFormBuilderField): void {

        let capCode: string = 'cap';
        let provinciaCode: string = 'provincia';
        let istatCode: string = 'cod-istat';

        let cap: string = undefined;
        let codIstat: string = undefined;
        let codIstatProvincia: string = undefined;
        if (field.data) {
            codIstat = field.data.codistat;
            codIstatProvincia = `0${codIstat.substring(3, 6)}`;
            cap = field.data.cap;
        }

        let capField: any = {
            code: capCode,
            data: cap
        };

        let provinciaField: any = {
            code: provinciaCode,
            data: get(this.provinceMap, codIstatProvincia)
        };

        if (field.groupCode) {
            capField.groupCode = field.groupCode;
            provinciaField.groupCode = field.groupCode;
        }

        this.formBuilderDataSubject.next(capField);
        this.formBuilderDataSubject.next(provinciaField);
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

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    // #endregion
}
