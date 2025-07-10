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
import { CustomParamsFunction, FormBuilderUtilsService, ProtectionUtilsService } from '@maggioli/app-commons';
import { SdkAbstractComponent, SdkProviderService, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkComboBoxItem,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { cloneDeep, find, get, isEmpty, isObject, isString, map, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Observer, Subject } from 'rxjs';

import { Constants } from '../../../../../../app.constants';
import { BaseRupInfo, Collaborazione } from '../../../../../models/gare/importa-gara.model';

@Component({
    templateUrl: `lista-collaborazioni-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ListaCollaborazioniModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    @ViewChild('messages') _messagesPanel: ElementRef;

    public config: any;
    public data: void;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();

    private buttons: SdkButtonGroupInput;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private userProfile: UserProfile;
    private listaCollaborazioni: Array<Collaborazione>;

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
                this.listaCollaborazioni = this.config.listaCollaborazioni;
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

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }
    
    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private elaborateConfig(): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields: get(this.config, 'listaCollaborazioniData.fields')
                };

                let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
                    let mapping: boolean = true;

                    let keyAny: any = get(restObject, field.mappingInput);
                    let key: string = toString(keyAny);

                    if (field.code === 'lista-collaborazioni') {
                        field.itemsProvider = () => {
                            return new Observable<Array<SdkComboBoxItem>>((ob: Observer<Array<SdkComboBoxItem>>) => {
                                const listaCollaborazioniCombo: Array<SdkComboBoxItem> = map(this.listaCollaborazioni, (one: Collaborazione) => {
                                    const item: SdkComboBoxItem = {
                                        key: one.index,
                                        value: one.ufficioDenominazione
                                    };
                                    return item;
                                });
                                ob.next(listaCollaborazioniCombo);
                                ob.complete();
                            });
                        };
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

                formConfig = this.formBuilderUtilsService.populateForm(formConfig, false, customPopulateFunction);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);

                this.sdkMessagePanelService.showInfo(this.messagesPanel, [
                    {
                        message: 'IMPORTA-GARA.LISTA-COLLABORAZIONI-MODAL.INFO'
                    }
                ]);
            });
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (obj != null && obj.close === true) {
            if (obj.data != null) {
                const indexCollaborazione: string = get(obj, 'data');
                const collaborazione: Collaborazione = find(this.listaCollaborazioni, (one: Collaborazione) => one.index === indexCollaborazione);
                this.emitOutput({ code: 'collaborazione', data: collaborazione });
            } else {
                this.emitOutput(undefined);
            }

        }
    }

    private loadButtons(): void {
        this.buttons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.listaCollaborazioniData.buttons, this.userProfile.configurations)
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

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    // #endregion
}
