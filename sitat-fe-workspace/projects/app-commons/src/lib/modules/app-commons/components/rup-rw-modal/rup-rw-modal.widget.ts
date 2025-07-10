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
import { IDictionary, SdkAbstractComponent, SdkProviderService, SdkStoreService, UserProfile } from '@maggioli/sdk-commons';
import {
    SdkAutocompleteItem,
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
} from '@maggioli/sdk-controls';
import { cloneDeep, each, find, get, has, isEmpty, isObject, isString, join, set } from 'lodash-es';
import { BehaviorSubject, Subject } from 'rxjs';

import { Constants } from '../../app-commons.constants';
import { StazioneAppaltanteInfo } from '../../models/stazione-appaltante/stazione-appaltante.model';
import { ValoreTabellato } from '../../models/tabellati/tabellato.model';
import { Tecnico } from '../../models/tecnico/tecnico.model';
import { TabellatiCacheService } from '../../services/tabellati/tabellati-cache.service';
import { CustomParamsFunction, FormBuilderUtilsService } from '../../services/utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../services/utils/protection-utils.service';

@Component({
    templateUrl: `rup-rw-modal.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class RupRWModalWidget extends SdkAbstractComponent<any, void, any> implements OnInit, AfterViewInit, OnDestroy {

    // #region Variables

    public config: any;
    public data: void;
    private buttonsRO: SdkButtonGroupInput;
    private detailButtons: SdkButtonGroupInput;
    private updateButtons: SdkButtonGroupInput;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private detailFields: Array<SdkFormBuilderField>;
    private updateFields: Array<SdkFormBuilderField>;
    private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
    private selectedItem: SdkAutocompleteItem;
    private provinceMap: IDictionary<string> = {};
    private userProfile: UserProfile;
    private update: boolean = false;

    @ViewChild('messages') _messagesPanel: ElementRef;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));

        this.addSubscription(this.store.select<UserProfile>(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));

        this.tabellatiCacheService.getValoriTabellato(Constants.PROVINCE).subscribe((province: Array<ValoreTabellato>) => {
            each(province, (provincia: ValoreTabellato) => {
                this.provinceMap[provincia.codistat] = provincia.codice;
            });
        });

        this.loadButtons();
    }

    protected onAfterViewInit(): void {
        this.elaborateConfig(this.detailFields);
    }

    protected onDestroy(): void { }

    protected onOutput(_data: void): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.detailFields = cloneDeep(this.config.rupData.detailFields);
                this.updateFields = cloneDeep(this.config.rupData.updateFields);
                this.update = false;
                this.selectedItem = this.config.selectedItem;
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

    protected get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private elaborateConfig(fields: Array<SdkFormBuilderField>): void {
        if (isObject(this.config)) {
            this.markForCheck(() => {
                let formConfig: SdkFormBuilderConfiguration = {
                    fields
                };

                let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
                    let mapping: boolean = true;

                    if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                        field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
                    }

                    if (field.code === 'comune' && field.type === 'AUTOCOMPLETE' && restObject != null && restObject.comune != null) {
                        let item: SdkAutocompleteItem = {
                            text: restObject.comune,
                            _key: restObject.comune
                        };
                        set(field, 'data', item);
                        mapping = false;
                    }

                    return {
                        mapping,
                        field
                    };
                }

                formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
                formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.selectedItem);

                this.defaultFormBuilderConfig = cloneDeep(formConfig);
                this.formBuilderConfig = formConfig;
                this.formBuilderConfigObs.next(formConfig);
            });
        }
    }

    private manageExecutionProvider = (obj: any) => {
        if (isObject(obj)) {
            if (has(obj, 'update') && get(obj, 'update') === true) {
                this.goToUpdate();
            } else if (has(obj, 'formBuilderConfig')) {
                let formBuilderConfig = get(obj, 'formBuilderConfig');
                this.formBuilderConfig = formBuilderConfig;
                this.formBuilderConfigObs.next(formBuilderConfig);
            } else if (has(obj, 'close') && get(obj, 'close') === true) {
                if (has(obj, 'data') && isObject(get(obj, 'data'))) {
                    let data: Tecnico = get(obj, 'data');
                    let item: SdkAutocompleteItem = {
                        ...data,
                        text: `${data.nominativo} (${data.cf})`,
                        _key: data.codice
                    };
                    this.emitOutput(item);
                } else {
                    this.emitOutput(undefined);
                }
            }
        }
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

        let codIstatField: any = {
            code: istatCode,
            data: codIstat
        };

        if (field.groupCode) {
            capField.groupCode = field.groupCode;
            provinciaField.groupCode = field.groupCode;
            codIstatField.groupCode = field.groupCode;
        }

        this.formBuilderDataSubject.next(capField);
        this.formBuilderDataSubject.next(provinciaField);
        this.formBuilderDataSubject.next(codIstatField);
    }

    private goToUpdate() {
        if(this.config.gara.readOnly){
            this.buttonsSubj.next(this.buttonsRO);
        } else{
            this.elaborateConfig(this.updateFields);
            this.buttonsSubj.next(this.updateButtons);
            this.update = true;
        }
        
    }



    private loadButtons(): void {
        this.detailButtons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.rupData.detailButtons, this.userProfile.configurations)
        };
        this.buttonsRO = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.rupData.buttonsRO, this.userProfile.configurations)
        };
        this.updateButtons = {
            buttons: this.protectionUtilsService.checkButtonsProtection(this.config.rupData.updateButtons, this.userProfile.configurations)
        };
        this.showButtons();
    }

    private manageIntestazione(field: SdkFormBuilderField): void {

        const nomeCode: string = 'nome';
        const cognomeCode: string = 'cognome';
        const intestazioneCode: string = 'intestazione';
        let nomeValue: string = '';
        let cognomeValue: string = '';
        let intestazioneValue: string = '';

        if (field.code === nomeCode) {
            nomeValue = field.data;
            // recupero il valore del cognome
            const cognomeField: SdkFormBuilderField = this.getFieldByCode(cognomeCode);
            cognomeValue = cognomeField.data != null ? cognomeField.data.trim() : '';
        } else if (field.code === cognomeCode) {
            cognomeValue = field.data;
            // recupero il valore del nome
            const nomeField: SdkFormBuilderField = this.getFieldByCode(nomeCode);
            nomeValue = nomeField.data != null ? nomeField.data.trim() : '';
        }

        intestazioneValue = join([cognomeValue, nomeValue], ' ').trim();

        const toUpdate: SdkFormBuilderInput = {
            code: intestazioneCode,
            data: intestazioneValue
        };

        this.formBuilderDataSubject.next(toUpdate);

    }

    private getFieldByCode(fieldCode: string): SdkFormBuilderField {
        const section: SdkFormBuilderField = find(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === 'rup-data');
        const field: SdkFormBuilderField = find(section.fieldSections, (one: SdkFormBuilderField) => one.code === fieldCode);
        return field;
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
                    messagesPanel: this.messagesPanel,
                    stazioneAppaltante: this.stazioneAppaltanteInfo,
                    selectedItem: this.selectedItem
                }
            }).subscribe(this.manageExecutionProvider)
        }
    }

    public manageFormOutput(config: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = config;
    }

    public manageOutputField(field: SdkFormBuilderField): void {
        if (isObject(field) && this.update === true) {
            if (field.code === 'comune') {
                this.manageComune(field);
            } else if (field.code === 'nome' || field.code === 'cognome') {
                this.manageIntestazione(field);
            }
        }
    }

    private showButtons(): void {
        if(this.config.readOnly === true){
            this.buttonsSubj.next(this.buttonsRO);
        } else{        
            if(this.config.gara != null){
                if(this.config.gara.readOnly){
                    this.buttonsSubj.next(this.buttonsRO);
                } else{
                    this.buttonsSubj.next(this.detailButtons);
                }  
            } else{                
                this.buttonsSubj.next(this.detailButtons);                                
            }
        }
              
    }

    // #endregion
}
