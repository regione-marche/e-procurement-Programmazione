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
import {
    SdkAbstractComponent,
    SdkDateHelper,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, has, isEmpty, isObject, isString } from 'lodash-es';
import { BehaviorSubject, Subject } from 'rxjs';

import { Constants } from '../../app-commons.constants';
import { DettaglioPubblicazione } from '../../models/pubblicazione/pubblicazione.model';
import { CustomParamsFunctionResponse, FormBuilderUtilsService } from '../../services/utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';
import { PubblicazioneGaraService } from 'projects/app-contratti/src/app/modules/services/pubblicazioni/pubblicazione-gara.service';
import { GareService } from 'projects/app-contratti/src/app/modules/services/gare/gare.service';


@Component({
    templateUrl: `dettaglio-pubblicazione-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioPubblicazioneModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    public config: any;
    public data: void;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    private buttons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private userProfile: UserProfile;
    private dettaglio: DettaglioPubblicazione;
    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.elaborateConfig();
    }

    protected onDestroy(): void { }

    protected onOutput(_data: void): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.dettaglio = this.config.dettaglio;
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

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get pubblicazioneGaraService(): PubblicazioneGaraService { return this.injectable(PubblicazioneGaraService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: get(this.config, 'dettaglioPubblicazione.fields')
                };

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, this.customPopulateFunction, this.dettaglio);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            });
        }
    }

    private customPopulateFunction = (field: SdkFormBuilderField, restObject?: any, dynamicField?: boolean): CustomParamsFunctionResponse => {
        let mapping: boolean = true;

        const value = get(restObject, field.mappingInput);
        if (value != null) {
            if (field.code.startsWith('data-') && field.type === 'TEXT') {
                field.data = this.dateHelper.format(new Date(value), this.config.locale.fullDateformat);
                mapping = false;
            } else if (field.code === 'num-fase') {
                field.data = this.translateService.instant(`TIPOLOGIAFASI.${value}`);
                mapping = false;
            } else if (field.code === 'tipo-invio') {
                field.data = this.translateService.instant(value);
                mapping = false;
            }
        }

        if (field.code === 'xml') {
            field.data = this.translateService.instant("BUTTONS.CLICCA");
            mapping = false;
        }

        if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
            field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
        }

        return {
            mapping,
            field
        };
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj) && has(obj, 'close') && get(obj, 'close') === true) {
            this.emitOutput(undefined);
        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.dettaglioPubblicazione.buttons, this.userProfile.configurations)
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
                    defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                    formBuilderConfig: this.formBuilderConfig,
                    messagesPanel: this.messagesPanel
                }
            }).subscribe(this.manageExecutionProvider)
        }
    }

    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {
            if (isObject(config)) {
                if (config.code === 'xml') {
                    if(this.dettaglio.contri != null){
                        this.download(this.dettaglio?.idSchedaLocale ? `${this.dettaglio?.idSchedaLocale}.json` : 'flusso.json',this.dettaglio.xml);
                    } else{
                        this.pubblicazioneGaraService.getTracciatoFlusso(this.dettaglio.idFlusso).subscribe((xml: string) => {
                            this.download(this.dettaglio?.idSchedaLocale ? `${this.dettaglio?.idSchedaLocale}.json` : 'flusso.json',xml);
                        });
                    }
                                               
                }
            }
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    private download(filename: string, text: string) {
        //let blob = this.base64ToBlob(text);
        
        var blob = new Blob([text], {type: "application/json"});
        let data = window.URL.createObjectURL(blob);
        let link = document.createElement('a');
        document.body.appendChild(link);
        link.href = data;
        link.download = filename;
        link.click();
        window.URL.revokeObjectURL(data);
        link.remove();
    }

    public base64ToBlob(b64Data, sliceSize = 512) {
        let byteCharacters = atob(b64Data); //data.file there
        let byteArrays = [];
        for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
            let slice = byteCharacters.slice(offset, offset + sliceSize);

            let byteNumbers = new Array(slice.length);
            for (var i = 0; i < slice.length; i++) {
                byteNumbers[i] = slice.charCodeAt(i);
            }
            let byteArray = new Uint8Array(byteNumbers);
            byteArrays.push(byteArray);
        }
        return new Blob(byteArrays, { type: 'application/xhtml+xml' });
    }

    // #endregion
}
