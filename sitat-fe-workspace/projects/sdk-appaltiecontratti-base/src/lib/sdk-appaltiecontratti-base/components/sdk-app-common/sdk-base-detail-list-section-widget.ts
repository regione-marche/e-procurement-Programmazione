import { cloneDeep, get, isEmpty, isString, toString } from "lodash-es";
import { Injector, ChangeDetectorRef, Component } from "@angular/core";
import { SdkTableConfig, SdkTableDatasource } from "@maggioli/sdk-table";
import { Subject } from "rxjs";
import { SdkFormBuilderConfiguration, SdkFormBuilderField, SdkFormBuilderInput } from "@maggioli/sdk-controls";
import { SdkBaseSectionWidget } from "./sdk-base-section-widget";
import { CustomParamsFunction, FormBuilderUtilsService } from "../../services/utils/form-builder-utils.service";

@Component({
    template: "",
})
export abstract class SdkBaseDetailListSectionWidget extends SdkBaseSectionWidget {
    //ABSTRACT METHODS
    protected abstract getEntity(): any;
    protected abstract getDataSource(): SdkTableDatasource;
    //END ABSTRACT METHODS

    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();

    protected defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    protected formBuilderConfig: SdkFormBuilderConfiguration;

    public resetTable: Subject<boolean> = new Subject();

    protected datasource: SdkTableDatasource;
    protected gridConfig: SdkTableConfig;

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    protected reloadGrid(): void {
        this.resetTable.next(true);
    }

    protected get formBuilderUtilsService(): FormBuilderUtilsService {
        return this.injectable(FormBuilderUtilsService);
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputField(field: SdkFormBuilderField): void {
        //Override when needed
    }

    protected initGrid(): void {
        this.datasource = this.getDataSource();

        this.gridConfig = cloneDeep(this.config.body.grid);
        this.gridConfig = this.gridUtilsService.parseDescriptor(
            this.gridConfig,
            this.datasource,
            this.performers,
            this.config.locale.dateFormat,
            this.userProfile.configurations
        );
        this.configSub.next(this.gridConfig);
    }

    protected loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, "body.fields"));

        let formConfig: SdkFormBuilderConfiguration = {
            fields,
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
            let mapping: boolean = true;
            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = dynamicField === true ? field.data : toString(keyAny);

            if (!isEmpty(field.listCode) && field.type === "TEXT" && !isEmpty(key)) {
                [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
            }

            if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
            }

            return {
                mapping,
                field,
            };
        };

        
        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);

        formConfig = this.formBuilderUtilsService.populateForm(this.config, formConfig, true, customPopulateFunction, this.getEntity());

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
    }
}
