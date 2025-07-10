import { cloneDeep, get, isArray, isEmpty, isObject } from "lodash-es";
import { Injector, ChangeDetectorRef, Component } from "@angular/core";
import { SdkTableConfig, SdkTableDatasource } from "@maggioli/sdk-table";
import { Subject } from "rxjs";
import { map } from "rxjs/operators";
import { SdkButtonGroupOutput, SdkFormBuilderConfiguration, SdkFormBuilderField } from "@maggioli/sdk-controls";
import { IDictionary } from "@maggioli/sdk-commons";
import { SdkBaseSectionWidget } from "./sdk-base-section-widget";
import { FormBuilderUtilsService } from "../../services/utils/form-builder-utils.service";


@Component({
    // to avoid NG0912: Component ID generation collision detected with SdkBaseListSectionWidget (do not use this selector!)
    selector: 'sdk-base-list-section-local',
    template: "",
})
export abstract class SdkBaseListSectionWidget extends SdkBaseSectionWidget {
    //ABSTRACT METHODS
    protected abstract getDataSource(): SdkTableDatasource;
    protected abstract initPerformers();
    //END ABSTRACT METHODS

    public resetTable: Subject<boolean> = new Subject();

    protected datasource: SdkTableDatasource;
    protected gridConfig: SdkTableConfig;

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    protected reloadGrid(): void {
        this.resetTable.next(true);
    }

    protected onAfterViewInit(): void {
        this.loadTabellati().pipe(
            map(this.elaborateTabellati)            
        ).subscribe(() => {
            setTimeout(() => {
                this.initPerformers();
                this.initGrid();
                this.checkInfoBox();
            });
        });
    }

    protected initGrid(): void {
        this.datasource = this.getDataSource();

        this.gridConfig = cloneDeep(this.config.body.grid);
        if (this.gridConfig.filterable === true) {
            this.gridConfig.filter.fields = this.loadFilterForm(this.gridConfig.filter.fields);
        }
        this.gridConfig = this.gridUtilsService.parseDescriptor(
            this.gridConfig,
            this.datasource,
            this.performers,
            this.config.locale.dateFormat,
            this.userProfile.configurations
        );
        this.configSub.next(this.gridConfig);
    }

    protected loadFilterForm(fields: Array<SdkFormBuilderField>): Array<SdkFormBuilderField> {
        if (isArray(fields)) {
            let formConfig: SdkFormBuilderConfiguration = {
                fields
            };

            formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations);
            formConfig = this.formBuilderUtilsService.populateForm(this.config, formConfig, false, undefined, undefined, {
                stazioneAppaltante: this.stazioneAppaltante
            });

            return formConfig.fields;
        }
        return fields;
    }

    protected manageDeleteResult = (result: string) => {
        if (isObject(result) && get(result, "reload") === true) {
            this.reloadGrid();
        }
    };

    public onButtonClick(button: SdkButtonGroupOutput): void {
        if (isObject(button) && isEmpty(button.provider) === false) {
            let data: IDictionary<any> = {
                button: button,
                buttonCode: button.code,
                messagesPanel: this.messagesPanel,
                setUpdateState: this.setUpdateState,
                stazioneAppaltante: this.stazioneAppaltante,
                syscon: this.userProfile.syscon,
                idProfilo: this.userProfile.configurations ? this.userProfile.configurations.idProfilo : null,
                //item: this.getParentItem()
            };
            this.provider.run(button.provider, data).subscribe();
        }
    }

    //protected abstract getParentItem(): any;


}
