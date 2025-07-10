import { SdkC0oggassDetailsStoreService } from "../sdk-c0oggass-details-store.service";
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
import {
	SdkButtonGroupInput,
	SdkButtonGroupOutput,
	SdkButtonGroupSingleInput,
	SdkFormBuilderConfiguration,
	SdkFormBuilderField,
	SdkModalOutput,
	SdkTextOutput,
} from "@maggioli/sdk-controls";
import { cloneDeep, get, has, isEmpty, isObject, remove, set, toNumber } from "lodash-es";
import { Observable, Subject } from "rxjs";
import { map, mergeMap } from "rxjs/operators";
import {
	C0oggassDetailsResponse,
	C0oggassDto,
	SignatoryDto,
} from "../../../model/sdk-docassociati.model";
import { SdkDocAssociatiBaseDetailSectionWidget } from "../../base/sdk-docassociati-base-detail-section-widget";
import { SdkDocAssociatiService } from "@maggioli/sdk-docassociati";
import { Action } from "rxjs/internal/scheduler/Action";

@Component({
	templateUrl: `sdk-c0oggass-details-section.widget.html`,
	encapsulation: ViewEncapsulation.None,
	changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SdkC0oggassDetailsSectionWidget extends SdkDocAssociatiBaseDetailSectionWidget {
	@HostBinding("class") classNames = `c0oggass-details-section`;

	private item?: C0oggassDto;

	private c0acod?: number;

	protected allowDownload?: boolean;

	protected buttonsSign: SdkButtonGroupInput;

	constructor(inj: Injector, cdr: ChangeDetectorRef) {
		super(inj, cdr);
	}

	protected onInit(): void {
		super.onInit();
		this.loadButtons();
		this.loadQueryParams();
		this.allowDownload = this.getProtezioniValue(
			"COLS.VIS.GENEWEB.W_DOCDIG.DIGOGG",
			false
		);
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
		this.config.breadcrumbs = [
			...this.sdkC0oggassParamsStoreService.parentBreadcrumbs,
			...configBreadcrumbs,
		];
		this.breadcrumbs.emit(this.config.breadcrumbs);
	}

	protected checkButtonsStatus(
		buttons: SdkButtonGroupInput
	): SdkButtonGroupInput {
		remove(buttons.buttons, (one: SdkButtonGroupSingleInput) => {
			if (one.code == "download" && !this.allowDownload) {
				return true;
			}

			return false;
		});

		return buttons;
	}

	protected getEntity() {
		return this.item;
	}

	protected getValoriTabellatiConst(): string[] {
		return ["AgzC5"];
	}

	private loadQueryParams(): void {
		let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
		this.c0acod = toNumber(paramsMap.get("c0acod"));
		if (!this.c0acod) {
			this.c0acod = this.sdkC0oggassDetailsStoreService.listDataItem.c0acod;
		}
	}

	protected onAfterViewInit(): void {
		this.loadTabellati()
			.pipe(
				map(this.elaborateTabellati),
				mergeMap(this.getDetails),
				map(this.elaborateDetails)
			)
			.subscribe(() => {
				setTimeout(() => {
					this.checkInfoBox();
					this.showButtons();
				});
			});
	}

	private getC0oggassGetDetailsFactory() {
		return () => {
			return this.sdkDocAssociatiService.c0oggassGetDetails(
				this.stazioneAppaltante.codice,
				this.c0acod
			);
		};
	}

	private getDetails = (): Observable<C0oggassDetailsResponse> => {
		let factory = this.getC0oggassGetDetailsFactory();
		return this.requestHelper.begin(factory, this.messagesPanel);
	};

	private elaborateDetails = (result: C0oggassDetailsResponse) => {
		//Load details data
		this.item = result.item;

		this.refreshBreadcrumbs();
		this.loadForm();
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
				idProfilo: this.userProfile.configurations
					? this.userProfile.configurations.idProfilo
					: null,
				item: this.item,
				c0aprg: this.item.c0aprg,
				c0acod: this.c0acod,
			};

			this.provider.run(button.provider, data).subscribe(this.manageExecutionProvider);
		}
	}

	private manageExecutionProvider = (obj: any) => {
		if (isObject(obj)) {
			if (has(obj, 'open') && has(obj, 'signatory') && get(obj, 'open') === true) {
				let signatory: SignatoryDto = get(obj, 'signatory');
				let errorData: string = get(obj, 'errorData');
				this.openModal(signatory, errorData);
			}
		}
	}

	public openModal(signatory: SignatoryDto, errorData: string): void {
		this.modalConfig = {
			code: 'modal',
			title: 'sign',
			openSubject: new Subject(),
			component: 'sdk-sign-modal-widget',
			componentConfig: {
				vertical: false,
				menuTitle: '',
				signatory: {signatory},
				errorData,
				...this.config.body.signModalConfig,
				locale:this.config.locale
			}
		};
		this.modalConfigObs.next(this.modalConfig);
		this.modalConfig.openSubject.next(true);
  	}

	private get sdkC0oggassDetailsStoreService(): SdkC0oggassDetailsStoreService {
		return this.injectable(SdkC0oggassDetailsStoreService);
	}

	protected manageFieldsForCustomPopulateFunction(
		field: SdkFormBuilderField,
		mapping: boolean
	) {
		if (field.code === "c0adat") {
			const formatted = this.dateHelper.format(
				new Date(this.item.c0adat),
				this.config.locale.dateFormat
			);
			set(field, "data", formatted);
			mapping = false;
		}

		return mapping;
	}
	public manageFormClick(config: SdkTextOutput): void {
		let data: IDictionary<any> = {
			config: config,
            buttonCode: "download",
			messagesPanel: this.messagesPanel,
			setUpdateState: this.setUpdateState,
			stazioneAppaltante: this.stazioneAppaltante,
			syscon: this.userProfile.syscon,
			idProfilo: this.userProfile.configurations
				? this.userProfile.configurations.idProfilo
				: null,
			item: this.item,
			c0aprg: this.item.c0aprg,
			c0acod: this.c0acod,
		};

			this.provider.run("SDK_DOC_ASSOCIATI_C0OGGASS", data).subscribe();
	}



	private get docAssociatiService(): SdkDocAssociatiService {
		return this.injectable(SdkDocAssociatiService);
	}

	public manageModalOutput(_item: SdkModalOutput<any>): void {
		
	}

	protected showButtons(){
		if(this.item.c0anomogg?.endsWith('.p7m')){
			this.buttonsSign = {
				buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttonsSign, this.userProfile.configurations),
			};
			super.showButtons(this.buttonsSign);
		} else{
			super.showButtons();
		}		
	}
}
