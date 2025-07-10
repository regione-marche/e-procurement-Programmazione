import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from "@angular/core";
import { SdkTableDatasource, SdkTableRowDto } from "@maggioli/sdk-table";


import { isObject } from "lodash-es";
import { C0oggassListDto } from "../../../model/sdk-docassociati.model";
import { SdkDocAssociatiBaseListSectionWidget } from "../../base/sdk-docassociati-base-list-section-widget";
import { SdkC0oggassParamsStoreService } from "../sdk-c0oggass-params-store.service";
import { SdkC0oggassListDatasource } from "./sdk-c0oggass-list.datasource.service";

@Component({
    templateUrl: `sdk-c0oggass-list-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SdkC0oggassListSectionWidget extends SdkDocAssociatiBaseListSectionWidget {

    // #region Variables
    @HostBinding("class") classNames = `c0oggass-list-section`;
    // #endregion

    private allowDownload?: boolean;

    // #region Construct
    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }
    // #endregion

    protected onInit(): void {
        super.onInit();
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

    protected get sdkC0oggassParamsStoreService(): SdkC0oggassParamsStoreService {
        return this.injectable(SdkC0oggassParamsStoreService);
    }

    protected getValoriTabellatiConst(): string[] {
        return [];
    }

    protected getDataSource(): SdkTableDatasource {
        return this.factory.create(SdkC0oggassListDatasource, {
            codein: this.stazioneAppaltante.codice,
            idProfilo: this.userProfile.configurations ? this.userProfile.configurations.idProfilo : null,
            valoriTabellati: this.valoriTabellati,
            messagesPanel: this.messagesPanel,
        });
    }

    protected initPerformers(): void {
        this.performers = {
            details: (selectedRow: { rowIndex: number; dataItem: SdkTableRowDto }) => {
                let item: C0oggassListDto = selectedRow.dataItem as C0oggassListDto;
                this.detailC0oggass(item);
            },
            delete: (selectedRow: { rowIndex: number; dataItem: SdkTableRowDto }) => {
                let item: C0oggassListDto = selectedRow.dataItem as C0oggassListDto;
                this.deleteC0oggass(item);
            },
            deleteHidden: (selectedRow: { rowIndex: number; dataItem: SdkTableRowDto }) => {
                return false;
            },
            deleteNotHidden: (selectedRow: { rowIndex: number; dataItem: SdkTableRowDto }) => {
                return true;
            },
            download: (selectedRow: { rowIndex: number; dataItem: SdkTableRowDto }) => {
                let item: C0oggassListDto = selectedRow.dataItem as C0oggassListDto;
                this.downloadC0oggass(item);
            },
            downloadHidden: (selectedRow: { rowIndex: number; dataItem: SdkTableRowDto }) => {
                return !this.allowDownload;
            },
        };
    }

    private downloadC0oggass(item: C0oggassListDto): void {
        this.sdkMessagePanelService.clear(this.messagesPanel);
        
        this.addSubscription(
            this.provider
                .run("SDK_DOC_ASSOCIATI_C0OGGASS", {
                    action: "DOWNLOAD",
                    stazioneAppaltante: this.stazioneAppaltante,
                    syscon: this.userProfile.syscon,
                    idProfilo: this.userProfile.configurations ? this.userProfile.configurations.idProfilo : null,
                    messagesPanel: this.messagesPanel,
                    item: item
                })
                .subscribe(this.manageDeleteResult)
        );
    }


    private detailC0oggass(c0oggass: C0oggassListDto): void {
        this.sdkMessagePanelService.clear(this.messagesPanel);
        this.addSubscription(
            this.provider.run("SDK_DOC_ASSOCIATI_C0OGGASS", {
                action: "DETAIL",
                stazioneAppaltante: this.stazioneAppaltante,
                item: c0oggass
            })
                .subscribe()
        );
    }

    private deleteC0oggass(item: C0oggassListDto): void {
        let func = this.deleteConfirmC0oggass(item);
        this.initDeleteConfirmDialog();
        this.dialogConfig.open.next(func);
    }

    private deleteConfirmC0oggass(item: C0oggassListDto): any {
        return () => {
            this.addSubscription(
                this.provider
                    .run("SDK_DOC_ASSOCIATI_C0OGGASS", {
                        action: "DELETE",
                        syscon: this.userProfile.syscon,
                        stazioneAppaltante: this.stazioneAppaltante,
                        idProfilo: this.userProfile.configurations ? this.userProfile.configurations.idProfilo : null,
                        messagesPanel: this.messagesPanel,
                        item: item
                    })
                    .subscribe(this.manageDeleteResult)
            );
        };
    }

}
