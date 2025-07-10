import {
	ChangeDetectorRef,
	Component,
	ElementRef,
	Injector,
	OnDestroy,
	OnInit,
	ViewChild,
} from "@angular/core";
import {
	IDictionary,
	SdkBusinessAbstractWidget,
	SdkStoreService,
	UserProfile,
} from "@maggioli/sdk-commons";
import {
	SdkButtonGroupInput,
	SdkButtonGroupSingleInput,
	SdkDropdownButtonData,
} from "@maggioli/sdk-controls";

import { get, has, isEmpty, isObject, reduce } from "lodash-es";
import { SdkC0oggassParamsStoreService } from "projects/sdk-docassociati/src/lib/sdk-docassociati/components/c0oggass/sdk-c0oggass-params-store.service";
import { C0oggassListRequest } from "projects/sdk-docassociati/src/lib/sdk-docassociati/model/sdk-docassociati.model";
import { SdkDocAssociatiService } from "projects/sdk-docassociati/src/lib/sdk-docassociati/services/sdk-docassociati.service";
import { BehaviorSubject, Observable, Observer, map, of, tap } from "rxjs";
import { ProfiloConfigurationItem } from "../../model/profilo/profilo.model";
import { StringResponse } from "../../model/sdk-base.model";
import { StazioneAppaltanteBaseInfo } from "../../model/stazione-appaltante/stazione-appaltante.model";
import { SdkAppaltiecontrattiBaseConstants } from "../../sdk-appaltiecontratti-base.constants";
import { HttpRequestHelper } from "../../services/http/http-request-helper.service";

@Component({
	template: "",
})
export abstract class SdkBaseBusinessWidget
	extends SdkBusinessAbstractWidget<void>
	implements OnInit, OnDestroy
{
	@ViewChild("messages") _messagesPanel: ElementRef;

	protected buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;

	private configLocal: any;
	protected stazioneAppaltanteLocal: StazioneAppaltanteBaseInfo;
	protected userProfileLocal: UserProfile;

	protected pageType?: string;
	protected pageFeatures?: string[];
	protected entityName?: string;
	protected entityKeys: string[];

	constructor(inj: Injector, cdr: ChangeDetectorRef) {
		super(inj, cdr);
	}

	protected onConfig(config: any): void {
		if (isObject(config)) {
			this.configLocal = { ...config };

			this.pageType = this.configLocal.pageType;
			this.pageFeatures = this.configLocal.pageFeatures;
			if (isObject(this.configLocal.entity)) {
				this.entityName = this.configLocal.entity.name;
				this.entityKeys = this.configLocal.entity.keys;
			}
		}
	}

	protected onInit(): void {
		console.log("onInit");
		this.addSubscription(
			this.store
				.select<UserProfile>(
					SdkAppaltiecontrattiBaseConstants.USER_PROFILE_SELECT
				)
				.subscribe((userProfile: UserProfile) => {
					this.userProfileLocal = userProfile;
				})
		);

		this.addSubscription(
			this.store
				.select(SdkAppaltiecontrattiBaseConstants.SA_INFO_SELECT)
				.subscribe((saInfo: StazioneAppaltanteBaseInfo) => {
					this.stazioneAppaltanteLocal = saInfo;
				})
		);
	}

	protected get store(): SdkStoreService {
		return this.injectable(SdkStoreService);
	}

	protected injectDocAssociatiButton = (buttons: any) => {
		let myButtons = buttons.buttons ? buttons.buttons : buttons;

		let btnWithDropdown: SdkButtonGroupSingleInput = undefined;
		if (this.isDocAssociatiEnabled()) {
			for (let btn of myButtons) {
				if (isObject(btn.dropdownData)) {
					btnWithDropdown = btn;
					break;
				}
			}

			if (btnWithDropdown === undefined) {
				btnWithDropdown = {
					code: "altre-azioni",
					label: "BUTTONS.ALTRE-AZIONI",
					icon: "mgg-icons-menu-more",
					dropdown: true,
					dropdownData: [],
					type: "functions",
					look: "icon",
				};
				buttons.buttons.push(btnWithDropdown);
			} else {
				let emptyButtonGroupId = true;
				btnWithDropdown.dropdownData.forEach((item) => {
					if (!isEmpty(item.buttonGroupId)) {
						emptyButtonGroupId = false;
					}
				});

				if (emptyButtonGroupId) {
					btnWithDropdown.dropdownData.forEach((item) => {
						item.buttonGroupId = "dummy-group";
					});
				}
			}

			let btnDocAss: SdkDropdownButtonData = {
				code: "docassociati-list",
				label: "SDK-DOC-ASSOCIATI.BUTTONS.DOC-ASSOCIATI-LIST",
				provider: "SDK_DOC_ASSOCIATI_C0OGGASS",
				buttonGroupId: "docassociati",
			};

			btnWithDropdown.dropdownData.push(btnDocAss);

			return this.setDocAssButtonLabelParam(btnDocAss);
		}

		return new Observable<void>((ob: Observer<void>) => {
			ob.next(null);
			ob.complete();
		});
	};
	protected injectModelliButton = (buttons: any) => {
		let myButtons = buttons.buttons ? buttons.buttons : buttons;

		let btnWithDropdown: SdkButtonGroupSingleInput = undefined;
		if (this.isModelliEnabled()) {
			for (let btn of myButtons) {
				if (isObject(btn.dropdownData)) {
					btnWithDropdown = btn;
					break;
				}
			}

			if (btnWithDropdown === undefined) {
				btnWithDropdown = {
					code: "altre-azioni",
					label: "BUTTONS.ALTRE-AZIONI",
					icon: "mgg-icons-menu-more",
					dropdown: true,
					dropdownData: [],
					type: "functions",
					look: "icon",
				};
				buttons.buttons.push(btnWithDropdown);
			} else {
				let emptyButtonGroupId = true;
				btnWithDropdown.dropdownData.forEach((item) => {
					if (!isEmpty(item.buttonGroupId)) {
						emptyButtonGroupId = false;
					}
				});

				if (emptyButtonGroupId) {
					btnWithDropdown.dropdownData.forEach((item) => {
						item.buttonGroupId = "dummy-group";
					});
				}
			}

			let btnModelli: SdkDropdownButtonData = {
				code: "go-to-modelli-predisposti",
				label: "SDK-GESTIONE-MODELLI.BUTTONS.MODELLI-LIST",
				provider: "SDK_GESTIONE_MODELLI_PREDISPOSTI",
				buttonGroupId: "modelli",
			};

			btnWithDropdown.dropdownData.push(btnModelli);

			return this.setModelliButtonLabelParam(btnModelli);
		}

		return new Observable<void>((ob: Observer<void>) => {
			ob.next(null);
			ob.complete();
		});
	};

	public setDocAssButtonLabelParam(
		btnDocAss: SdkDropdownButtonData
	): Observable<any> {
		let req: C0oggassListRequest = {
			codein: this.stazioneAppaltanteLocal.codice,
			idProfilo: this.userProfileLocal.configurations
				? this.userProfileLocal.configurations.idProfilo
				: null,
			c0aent: this.sdkC0oggassParamsStoreService.c0oggassParams.c0aent,
			c0akey1: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey1,
			c0akey2: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey2,
			c0akey3: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey3,
			c0akey4: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey4,
			c0akey5: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey5,
		};

		return this.c0oggassGetCountByKeys(req).pipe(
			map((result: StringResponse) => {
				btnDocAss.labelParams = { value: result.data };
			})
		);
	}
	public setModelliButtonLabelParam(
		btnModelli: SdkDropdownButtonData
	): Observable<any> {
		//     let req: C0oggassListRequest = {
		//         codein: this.stazioneAppaltanteLocal.codice,
		//         idProfilo: this.userProfileLocal.configurations ? this.userProfileLocal.configurations.idProfilo : null,
		//         c0aent: this.sdkC0oggassParamsStoreService.c0oggassParams.c0aent,
		//         c0akey1: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey1,
		//         c0akey2: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey2,
		//         c0akey3: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey3,
		//         c0akey4: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey4,
		//         c0akey5: this.sdkC0oggassParamsStoreService.c0oggassParams.c0akey5,
		//     };

		//     return this.c0oggassGetCountByKeys(req).pipe(
		//         map((result: StringResponse) => {
		//             btnModelli.labelParams = { value: result.data }
		//         })
		//     );
		return of({});
	}

	protected get sdkDocAssociatiService(): SdkDocAssociatiService {
		return this.injectable(SdkDocAssociatiService);
	}

	protected get sdkC0oggassParamsStoreService(): SdkC0oggassParamsStoreService {
		return this.injectable(SdkC0oggassParamsStoreService);
	}

	protected get messagesPanel(): HTMLElement {
		return isObject(this._messagesPanel)
			? this._messagesPanel.nativeElement
			: undefined;
	}

	private c0oggassGetCountByKeys = (
		req: C0oggassListRequest
	): Observable<StringResponse> => {
		let factory = () => {
			return this.sdkDocAssociatiService.c0oggassGetCountByKeys(req);
		};
		return this.requestHelper.begin(factory, this.messagesPanel);
	};

	protected get requestHelper(): HttpRequestHelper {
		return this.injectable(HttpRequestHelper);
	}

	private isDocAssociatiEnabled() {
		let ok = this.isDocAssociatiConfiguredOk();
		if (ok) {
			ok = !this.isDocAssociatiHiddenByProfile(this.entityName);
		}

		return ok;
	}

	private isDocAssociatiConfiguredOk() {
		let ok =
			this.hasPageFeature("docassociati") &&
			!isEmpty(this.entityName) &&
			this.entityName.length <= 16 &&
			isObject(this.entityKeys) &&
			this.entityKeys.length > 0 &&
			this.entityKeys.length <= 5;
		return ok;
	}
	private isDocAssociatiHiddenByProfile(entityName: string) {
		let hide = true;

		if (this.userProfileLocal.configurations.documentiAssociatiDB) {
			let protezioniMap = this.getProtezioniMap();

			let docAssProfileKeyOk = this.checkProtezioniValue(
				protezioniMap,
				"FUNZ.VIS.ALT.GENE.C0OGGASS",
				false,
				true
			);
			let wOggettiKeyForEntityOk = this.hasWOggetti(
				"FUNZ-ALT.GENE.C0OGGASS." + entityName.toUpperCase()
			);
			let docAssProfileKeyForEntityOk = this.checkProtezioniValue(
				protezioniMap,
				"FUNZ.VIS.ALT.GENE.C0OGGASS." + entityName.toUpperCase(),
				false,
				false
			);

			hide =
				!docAssProfileKeyOk &&
				(!wOggettiKeyForEntityOk || !docAssProfileKeyForEntityOk);
		}

		return hide;
	}

	private isModelliEnabled() {
		let ok = this.isModelliConfiguredOk();
		if (ok) {
			ok = !this.isModelliHiddenByProfile(this.entityName);
		}
		return ok;
	}

	private isModelliConfiguredOk() {
		let ok = this.hasPageFeature("modelli");
		// && !isEmpty(this.entityName)
		// && this.entityName.length <= 16
		// && isObject(this.entityKeys)
		// && this.entityKeys.length > 0
		// && this.entityKeys.length <= 5;
		return ok;
	}
	private isModelliHiddenByProfile(entityName: string ) {

		let hide = true;

		let protezioniMap = this.getProtezioniMap();

		let modelliProfileKeyOk = this.checkProtezioniValue(
			protezioniMap,
			"FUNZ.VIS.ALT.GENE.W_MODELLI",
			false,
			true
		);
		let wOggettiKeyForEntityOk = this.hasWOggetti(
			"FUNZ-ALT.GENE.W_MODELLI." + entityName.toUpperCase()
		);
		let modelliProfileKeyForEntityOk = this.checkProtezioniValue(
			protezioniMap,
			"FUNZ.VIS.ALT.GENE.W_MODELLI." + entityName.toUpperCase(),
			false,
			false
		);

		hide =
			!modelliProfileKeyOk &&
			(!wOggettiKeyForEntityOk || !modelliProfileKeyForEntityOk);

		return hide;
	}
	private hasPageFeature(featureName: string) {
		let ok =
			isObject(this.pageFeatures) && this.pageFeatures.includes(featureName);
		return ok;
	}

	protected hasWOggetti(key: string) {
		return this.userProfileLocal.configurations.woggetti.includes(key);
	}

	protected getProtezioniMap(): IDictionary<ProfiloConfigurationItem> {
		let protectionMap: IDictionary<ProfiloConfigurationItem> = reduce(
			this.userProfileLocal.configurations.configurazioni,
			(map: IDictionary<any>, one: ProfiloConfigurationItem) => {
				map[one.key] = one;
				return map;
			},
			{}
		);
		return protectionMap;
	}

	protected getProtezioniValue(
		key: string,
		defaultVal: boolean,
		checkSubKeys: boolean = true
	): boolean {
		let protezioniMap = this.getProtezioniMap();
		return this.checkProtezioniValue(
			protezioniMap,
			key,
			defaultVal,
			checkSubKeys
		);
	}

	protected checkProtezioniValue(
		protezioniMap: IDictionary<ProfiloConfigurationItem>,
		key: string,
		defaultVal: boolean,
		checkSubKeys: boolean = true
	): boolean {
		if (has(protezioniMap, key)) {
			let value: ProfiloConfigurationItem = get(protezioniMap, key);
			return value.valore;
		} else if (checkSubKeys) {
			if (key.endsWith(".*")) {
				key = key.substring(0, key.lastIndexOf(".*"));
			}
			if (key.indexOf(".") >= 0) {
				let subKey = key.substring(0, key.lastIndexOf("."));
				return this.getProtezioniValue(subKey + ".*", defaultVal);
			}
		}
		return defaultVal;
	}
}


