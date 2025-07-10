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
import { isEmpty, isObject } from "lodash-es";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";
import {
    SdkButtonGroupOutput,
    SdkFormBuilderField,
} from "@maggioli/sdk-controls";
import { C0oggassDetailsResponse, C0oggassForm } from "../../../model/sdk-docassociati.model";
import { SdkDocAssociatiBaseDetailSectionWidget } from "../../base/sdk-docassociati-base-detail-section-widget";

@Component({
    templateUrl: `sdk-c0oggass-create-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SdkC0oggassCreateSectionWidget extends SdkDocAssociatiBaseDetailSectionWidget {

    // #region Variables
    @HostBinding("class") classNames = `c0oggass-create-section`;

    private form: C0oggassForm;

    // #endregion

    // #region Abstract implementation

    protected getValoriTabellatiConst(): string[] {
        return [];
    }

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    protected onInit(): void {
        super.onInit();
        this.setUpdateState(true);
        this.loadQueryParams();
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

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
    }

    protected onAfterViewInit(): void {
        super.onAfterViewInit();

        this.loadDefaultForm()
            .pipe(map(this.elaborateDefaultForm))
            .subscribe(() => {
                setTimeout(() => {
                    this.checkInfoBox();
                    this.showButtons();
                });
            });
    }

    private getC0oggassCreateInitFactory() {
        return () => {
            return this.sdkDocAssociatiService.c0oggassCreateInit(this.stazioneAppaltante.codice);
        };
    }

    private loadDefaultForm = (): Observable<C0oggassDetailsResponse> => {
        let factory = this.getC0oggassCreateInitFactory();
        return this.requestHelper.begin(factory, this.messagesPanel);
    };

    private elaborateDefaultForm = (result: C0oggassDetailsResponse) => {
        //Init default form data
        this.form = new C0oggassForm();
        this.form.item = {
            ...result.item
        }

        this.allowedFileExtensions = result.allowedFileExtensions;
        this.maxAllowedFileSize = result.maxAllowedFileSize;

        this.form.item.c0aprg = this.sdkC0oggassParamsStoreService.c0oggassParams.c0aprg;
        this.form.item.c0aent = this.sdkC0oggassParamsStoreService.c0oggassParams.c0aent;
        this.form.item.c0akey1 = this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey1;
        this.form.item.c0akey2 = this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey2;
        this.form.item.c0akey3 = this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey3;
        this.form.item.c0akey4 = this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey4;
        this.form.item.c0akey5 = this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey5;

        this.refreshBreadcrumbs();
        this.checkInfoBox();
        this.loadForm();
    }

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
                form: this.form,
            };
            
            if (button.code === 'back-to-c0oggass-list') {
                this.backWithConfirm(button, data);
            } else {
                this.provider.run(button.provider, data).subscribe();
            }
        }
    }

    // #endregion
    public manageOutputField(field: SdkFormBuilderField): void { }
}
