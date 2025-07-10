import { cloneDeep, find, get, isEmpty, isObject, isString, set, toNumber, toString } from "lodash-es";
import { Injector, ChangeDetectorRef, Component } from "@angular/core";
import { Subject } from "rxjs";
import { SdkButtonGroupOutput, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkFormBuilderInput } from "@maggioli/sdk-controls";
import { IDictionary } from "@maggioli/sdk-commons";
import { SdkBaseSectionWidget } from "./sdk-base-section-widget";
import { CustomParamsFunction, FormBuilderUtilsService } from "../../services/utils/form-builder-utils.service";

@Component({
    template: "",
})
export abstract class SdkBaseDetailSectionWidget extends SdkBaseSectionWidget {

    protected abstract getEntity(): any;

    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    public formBuilderFieldSubject: Subject<SdkFormBuilderField> = new Subject();

    protected defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    protected formBuilderConfig: SdkFormBuilderConfiguration;

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    protected get formBuilderUtilsService(): FormBuilderUtilsService {
        return this.injectable(FormBuilderUtilsService);
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputField(field: SdkFormBuilderField): void {
        //Override and oimplment as needed
    }

    protected clearMessagePanel() {
        while (this.messagesPanel.firstChild) {
            this.messagesPanel.firstChild.remove()
        }
    }

    protected loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, "body.fields"));
        let allowDownloadDoc: boolean = this.getProtezioniValue('COLS.VIS.GENEWEB.W_DOCDIG.DIGOGG', false);   

        let formConfig: SdkFormBuilderConfiguration = {
            fields,
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
            let mapping: boolean = true;
            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);

            if (field.code === 'c0anomogg') {
                if(!allowDownloadDoc) {
                    set(field, 'link', false);
                }
            }

            if (!isEmpty(field.listCode) && field.type === "TEXT" && !isEmpty(key)) {
                [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
            }

            if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
            }

            mapping = this.manageFieldsForCustomPopulateFunction(field, mapping);

            return {
                mapping,
                field,
            };
        };

        let providerArgs: IDictionary<any> = {
            stazioneAppaltante: this.stazioneAppaltante,
            idProfilo: this.userProfile.configurations ? this.userProfile.configurations.idProfilo : null,
            messagesPanel: this.messagesPanel
        }

        let infoBox: boolean = !this.detailPage ? this.formBuilderUtilsService.isAmministratore(this.userProfile.abilitazioni) : false;

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);

        formConfig = this.formBuilderUtilsService.populateForm(this.config, formConfig, true, customPopulateFunction, this.getEntity(), providerArgs, infoBox);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
    }

    protected manageFieldsForCustomPopulateFunction(field: SdkFormBuilderField, mapping: boolean) {
        //default implementation does nothing
        return mapping;
    }


    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            let data: IDictionary<any> = {
                button: button,
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                setUpdateState: this.setUpdateState,
                stazioneAppaltante: this.stazioneAppaltante,
                syscon: this.userProfile.syscon,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                idProfilo: this.userProfile.configurations ? this.userProfile.configurations.idProfilo : null,
            };
            this.provider.run(button.provider, data).subscribe();
        }
    }

    public manageOutputInfoBox(item: SdkFormBuilderField): void {
        if (isObject(this.infoBoxModalConfig) && isObject(item)) {
            if (this.modalConfig == null) {
                this.modalConfig = {
                    code: 'modal',
                    title: '',
                    openSubject: new Subject()
                };
            }
            this.modalConfig = {
                ...this.modalConfig,
                component: get(this.infoBoxModalConfig, 'component'),
                componentConfig: {
                    fields: get(this.infoBoxModalConfig, 'fields'),
                    buttons: get(this.infoBoxModalConfig, 'buttons'),
                    userProfile: this.userProfile,
                    mnemonico: item.mnemonico
                }
            }
            this.modalConfigObs.next(this.modalConfig);
            setTimeout(() => this.modalConfig.openSubject.next(true));
        }
    }


    protected getFieldByCode(fieldCode: string): SdkFormBuilderField {
        return this.getFieldByCodeAndSection(fieldCode, 'general-data')
    }

    protected getFieldByCodeAndSection(fieldCode: string, sectionCode: string): SdkFormBuilderField {
        const section: SdkFormBuilderField = find(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === sectionCode);
        const field: SdkFormBuilderField = find(section.fieldSections, (one: SdkFormBuilderField) => one.code === fieldCode);
        return field;
    }

    protected isFieldEmpty(field: SdkFormBuilderField): boolean {
        return field.data == null;
    }

    protected updateFieldDataIfEmpty(field: SdkFormBuilderField, data: any): void {
        if (this.isFieldEmpty(field)) {
            this.updateFieldData(field, data);
            field.data = data;
        }
    }

    protected updateFieldData(field: SdkFormBuilderField, data: any): void {
        const toUpdate: SdkFormBuilderInput = {
            code: field.code,
            data: data
        };

        this.formBuilderDataSubject.next(toUpdate);
    }

    protected updateFieldDataByFieldCode(fieldCode: string, data: any): void {
        const toUpdate: SdkFormBuilderInput = {
            code: fieldCode,
            data: data
        };

        this.formBuilderDataSubject.next(toUpdate);
    }


    protected nullToZero(val: number): number {
        if (val == null) {
            return 0;
        }
        return toNumber(val);
    }

    protected zeroToNull(val: number): number {
        if (val == 0) {
            return null;
        }
        return toNumber(val);
    }

    protected setFieldVisibile(field: SdkFormBuilderField, visible: boolean): void {

        field.visible = visible;

        this.formBuilderFieldSubject.next(field);
    }

    protected setFieldVisibileByCodeAndSection(fieldCode: string, sectionCode: string, visible: boolean): void {
        let field = this.getFieldByCodeAndSection(fieldCode, sectionCode);
        if (field) {
            field.visible = visible;
            this.formBuilderFieldSubject.next(field);
        }
    }

    protected setFieldVisibileAndDataByCodeAndSection(fieldCode: string, sectionCode: string, visible: boolean, data: any): void {
        let field = this.getFieldByCodeAndSection(fieldCode, sectionCode);
        if (field) {
            field.visible = visible;
            field.data = data;
            this.formBuilderFieldSubject.next(field);
        }
    }

    protected getDataValueFromFieldOrForm(field: SdkFormBuilderField, fieldCode: string, sectionCode: string): any {
        if (field.code == fieldCode) {
            return field.data
        }
        return this.getFieldByCodeAndSection(fieldCode, sectionCode).data;
    }


    protected showError(errCode: string) {
        this.sdkMessagePanelService.showError(this.messagesPanel, [
            { message: this.translateService.instant(errCode) },
        ]);
        this.scrollToMessagePanel(this.messagesPanel);
    }

    protected showWarning(errCode: string) {
        this.sdkMessagePanelService.showWarning(this.messagesPanel, [
            { message: this.translateService.instant(errCode) },
        ]);
        this.scrollToMessagePanel(this.messagesPanel);
    }

    protected showInfo(errCode: string) {
        this.sdkMessagePanelService.showInfo(this.messagesPanel, [
            { message: this.translateService.instant(errCode) },
        ]);
        this.scrollToMessagePanel(this.messagesPanel);
    }

    protected showSuccess(errCode: string) {
        this.sdkMessagePanelService.showSuccess(this.messagesPanel, [
            { message: this.translateService.instant(errCode) },
        ]);
        this.scrollToMessagePanel(this.messagesPanel);
    }

    protected setFieldVisible(field: SdkFormBuilderField, visible: boolean) {
        set(field, 'visible', visible);
    }

    protected copyFieldData(sectionCodeFrom: string, fieldCodeFrom: string, fieldCodeTo: string, isNumber: boolean = false) {
        let fieldFrom = this.getFieldByCodeAndSection(fieldCodeFrom, sectionCodeFrom);

        if (isNumber) {
            this.updateFieldDataByFieldCode(fieldCodeTo, this.zeroToNull(toNumber(fieldFrom.data)));
        } else {
            this.updateFieldDataByFieldCode(fieldCodeTo, fieldFrom.data);
        }
    }


    protected initDialogBackConfirm(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.BACK-TITLE'),
            message: this.translateService.instant('DIALOG.BACK-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigSubj.next(this.dialogConfig);
    }

    protected backWithConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        let func = this.backConfirm(button, data);
        this.initDialogBackConfirm();
        this.dialogConfig.open.next(func);
    }

    protected backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.provider.run(button.provider, data).subscribe();
        }
    }

}
