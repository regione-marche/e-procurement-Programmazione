import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from "@angular/core";
import { ParamMap } from "@angular/router";
import { IDictionary } from "@maggioli/sdk-commons";
import { isEmpty, isObject, remove, toNumber } from "lodash-es";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkButtonGroupSingleInput,
    SdkFormBuilderField,
    SdkTextOutput,
} from "@maggioli/sdk-controls";
import { C0oggassDetailsResponse, C0oggassForm } from "../../../model/sdk-docassociati.model";
import { SdkC0oggassDetailsStoreService } from "../sdk-c0oggass-details-store.service";
import { SdkDocAssociatiBaseDetailSectionWidget } from "../../base/sdk-docassociati-base-detail-section-widget";


@Component({
    templateUrl: `sdk-c0oggass-edit-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SdkC0oggassEditSectionWidget extends SdkDocAssociatiBaseDetailSectionWidget {

    @HostBinding("class") classNames = `c0oggass-edit-section`;

    private form: C0oggassForm;
    
    private c0acod?: number;
    private allowDownload?: boolean;

    protected getValoriTabellatiConst(): string[] {
        return [];
    }

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    protected onInit(): void {
        super.onInit();
        this.setUpdateState(true);
        this.loadQueryParams();
        this.allowDownload = this.getProtezioniValue('COLS.VIS.GENEWEB.W_DOCDIG.DIGOGG', false);
    }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.setupUI();
                this.isReady = true;
            });
        }
    }

    private setupUI() {
        let configBreadcrumbs = this.config.breadcrumbs; //.slice(1);
        this.config.breadcrumbs = [...this.sdkC0oggassParamsStoreService.parentBreadcrumbs, ...configBreadcrumbs];
        this.breadcrumbs.emit(this.config.breadcrumbs);
    }

    protected checkButtonsStatus(buttons: SdkButtonGroupInput): SdkButtonGroupInput {
        
        remove(buttons.buttons, (one: SdkButtonGroupSingleInput) => {
            if (one.code == 'download' && !this.allowDownload) {
                return true;
            }

            return false;
        });

        return buttons;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.c0acod = toNumber(paramsMap.get("c0acod")); 
        if(!this.c0acod) {
            this.c0acod = this.sdkC0oggassDetailsStoreService.listDataItem.c0acod;
        }
    }

    protected onAfterViewInit(): void {
        super.onAfterViewInit();

        this.getDetails()
            .pipe(map(this.elaborateDetails))
            .subscribe(() => {
                setTimeout(() => {
                    this.checkInfoBox();
                    this.showButtons();
                });
            });
    }

    private getC0oggassGetDetailsFactory() {
        return () => {
            return this.sdkDocAssociatiService.c0oggassGetDetails(this.stazioneAppaltante.codice, this.c0acod);
        };
    }

    private getDetails = (): Observable<C0oggassDetailsResponse> => {
        let factory = this.getC0oggassGetDetailsFactory();
        return this.requestHelper.begin(factory, this.messagesPanel);
    }

    private elaborateDetails = (result: C0oggassDetailsResponse) => {
        //Load details data
        this.form = new C0oggassForm();
            this.form.item = {
            ...result.item
        }

        this.allowedFileExtensions = result.allowedFileExtensions;
        this.maxAllowedFileSize = result.maxAllowedFileSize;

        this.refreshBreadcrumbs();
        this.loadForm();
    };

    protected getEntity(): any {
        return this.form.item;
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
                item: this.sdkC0oggassDetailsStoreService.listDataItem,
                form: this.form,
                c0aprg: this.sdkC0oggassDetailsStoreService.listDataItem.c0aprg,
                c0acod:  this.c0acod,
            };

            if (button.code === 'back-to-detail') {
                this.backWithConfirm(button, data);
            } else {
                this.provider.run(button.provider, data).subscribe();
            }
        }
    }

    private get sdkC0oggassDetailsStoreService(): SdkC0oggassDetailsStoreService {
        return this.injectable(SdkC0oggassDetailsStoreService);
    }

    public manageOutputField(field: SdkFormBuilderField): void {}

	public manageFormClick(config: SdkTextOutput): void {
		let data: IDictionary<any> = {
                messagesPanel: this.messagesPanel,
                config: config,
                buttonCode: "download",
                setUpdateState: this.setUpdateState,
                stazioneAppaltante: this.stazioneAppaltante,
                syscon: this.userProfile.syscon,
                defaultFormBuilderConfig: this.defaultFormBuilderConfig,
                formBuilderConfig: this.formBuilderConfig,
                idProfilo: this.userProfile.configurations ? this.userProfile.configurations.idProfilo : null,
                item: this.sdkC0oggassDetailsStoreService.listDataItem,
                form: this.form,
                c0aprg: this.sdkC0oggassDetailsStoreService.listDataItem.c0aprg,
                c0acod:  this.c0acod,
            };

			this.provider.run("SDK_DOC_ASSOCIATI_C0OGGASS", data).subscribe();
	}
}
