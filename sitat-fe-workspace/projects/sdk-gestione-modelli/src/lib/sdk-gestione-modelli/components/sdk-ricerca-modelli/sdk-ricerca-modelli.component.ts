import {
	ChangeDetectorRef,
	Component,
	ElementRef,
	HostBinding,
	Injector,
	OnDestroy,
	OnInit,
	ViewChild,
} from "@angular/core";
import {
	IDictionary,
	SdkBusinessAbstractWidget,
	SdkProviderService,
	SdkStoreService,
	UserProfile,
} from "@maggioli/sdk-commons";
import {
	SdkAutocompleteItem,
	SdkButtonGroupInput,
	SdkButtonGroupOutput,
	SdkComboBoxItem,
	SdkFormBuilderConfiguration,
	SdkFormBuilderField,
	SdkMessagePanelService,
} from "@maggioli/sdk-controls";
import { cloneDeep, each, get, isEmpty, set, toString } from "lodash-es";
import { BehaviorSubject, Observable, Subject } from "rxjs";
import { map, tap, switchMap } from "rxjs/operators";
import {
	CustomParamsFunction,
	StazioneAppaltanteInfo,
	ValoreTabellato,
} from "../../model/lib.model";
import { TabellatiCacheService } from "../../services/tabellati/tabellati-cache.service";
import { FormBuilderUtilsService } from "../../utils/form-builder-utils.service";
import { ProtectionUtilsService } from "../../utils/protection-utils.service";
import { SdkGestioneModelliConstants } from "../../sdk-gestione-modelli.constants";
import { RicercaModelliForm } from "../../model/gestione-utenti.model";
import { TabellatiService } from "../../services/tabellati/tabellati.service";

@Component({
	templateUrl: "./sdk-ricerca-modelli.component.html",
	styleUrls: ["./sdk-ricerca-modelli.component.scss"],
})
export class SdkRicercaModelliComponent
	extends SdkBusinessAbstractWidget<void>
	implements OnInit, OnDestroy
{
	// #region Variables

	@HostBinding("class") classNames = `sdk-ricerca-modelli-section`;

	@ViewChild("messages") _messagesPanel: ElementRef;
	@ViewChild("infoBox") _infoBox: ElementRef;

	public config: any = {};

	public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
	public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> =
		new Subject();

	private buttons: SdkButtonGroupInput;
	private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
	private formBuilderConfig: SdkFormBuilderConfiguration;
	private userProfile: UserProfile;
	private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
	private form: RicercaModelliForm;
	private stazioneAppaltanteInfo: StazioneAppaltanteInfo;
	private tabellatiutente: any;
	// #endregion

	constructor(inj: Injector, cdr: ChangeDetectorRef) {
		super(inj, cdr);
	}

	// #region Hooks

	protected onInit(): void {
		this.addSubscription(
			this.store
				.select(SdkGestioneModelliConstants.SA_INFO_SELECT)
				.subscribe((saInfo: StazioneAppaltanteInfo) => {
					this.stazioneAppaltanteInfo = saInfo;
				})
		);

		this.addSubscription(
			this.store
				.select<UserProfile>(SdkGestioneModelliConstants.USER_PROFILE_SELECT)
				.subscribe((userProfile: UserProfile) => {
					this.userProfile = userProfile;
				})
		);
		this.addSubscription(
			this.store
				.select(SdkGestioneModelliConstants.SEARCH_FORM_MODELLI_SELECT)
				.subscribe((form: RicercaModelliForm) => {
					this.form = form;
				})
		);

		this.loadButtons();
	}

	protected onAfterViewInit(): void {
		this.loadTabellati()
			.pipe(
				switchMap(() => this.loadTabellatiUtenti()),
				map(this.elaborateTabellati),
				map(this.loadForm)
			)
			.subscribe();
	}

	private loadTabellatiUtenti = (): Observable<any> => {
		return this.tabellatiService
			.listaOpzioniUtenti(this.userProfile.configurations.idProfilo)
			.pipe(
				tap((result: any) => {
					this.tabellatiutente = result.utentiOptions;
				})
			);
	};

	protected onDestroy(): void {}

	protected onConfig(config: any): void {
		if (config != null) {
			this.markForCheck(() => {
				this.config = { ...config };
				this.isReady = true;
			});
		}
	}

	protected onUpdateState(_state: boolean): void {}

	// #endregion

	// #region Getters

	private get store(): SdkStoreService {
		return this.injectable(SdkStoreService);
	}

	private get formBuilderUtilsService(): FormBuilderUtilsService {
		return this.injectable(FormBuilderUtilsService);
	}

	private get protectionUtilsService(): ProtectionUtilsService {
		return this.injectable(ProtectionUtilsService);
	}

	private get tabellatiCacheService(): TabellatiCacheService {
		return this.injectable(TabellatiCacheService);
	}

	private get provider(): SdkProviderService {
		return this.injectable(SdkProviderService);
	}
	private get tabellatiService(): TabellatiService {
		return this.injectable(TabellatiService);
	}

	// #endregion

	// #region Public

	public manageFormOutput(config: SdkFormBuilderConfiguration): void {
		this.formBuilderConfig = config;
	}

	public onButtonClick(button: SdkButtonGroupOutput): void {
		if (button != null && isEmpty(button.provider) === false) {
			this.addSubscription(
				this.provider
					.run(button.provider, {
						buttonCode: button.code,
						defaultFormBuilderConfig: this.defaultFormBuilderConfig,
						formBuilderConfig: this.formBuilderConfig,
						codiceProfilo: this.userProfile.configurations.idProfilo,
						syscon: this.userProfile.syscon,
					})
					.subscribe(this.manageExecutionProvider)
			);
		}
	}

	// #endregion

	// #region Private

	private get messagesPanel(): HTMLElement {
		return this._messagesPanel != null
			? this._messagesPanel.nativeElement
			: undefined;
	}

	private get infoBox(): HTMLElement {
		return this._infoBox != null ? this._infoBox.nativeElement : undefined;
	}

	private loadTabellati = (): Observable<
		IDictionary<Array<ValoreTabellato>>
	> => {
		return this.tabellatiCacheService.getValoriTabellati(
			SdkGestioneModelliConstants.RICERCA_MODELLI_TABELLATI
		);
	};

	private elaborateTabellati = (
		result: IDictionary<Array<ValoreTabellato>>
	) => {
		this.valoriTabellati = result;
	};

	private loadForm = () => {
		if (this.config != null) {
			this.markForCheck(() => {
				if (this.form == null) {
					this.form = {};
				}
				this.form.codProfiloAttivo = this.userProfile.configurations.idProfilo;
				this.form.syscon = this.userProfile.syscon;
				let formConfig: SdkFormBuilderConfiguration = {
					fields: cloneDeep(get(this.config, "body.fields")),
				};

				let providerArgs: IDictionary<any> = {
					stazioneAppaltante: this.stazioneAppaltanteInfo.codice,
					codiceProfilo: this.userProfile.configurations.idProfilo,
					syscon: this.userProfile.syscon,
				};

				this.form.codProfiloAttivo = this.userProfile.configurations;
				this.form.syscon = this.userProfile.syscon;
				this.form.codProfiloAttivo = this.userProfile.configurations;
				this.form.syscon = this.userProfile.syscon;
				let customPopulateFunction: CustomParamsFunction = (
					field: SdkFormBuilderField,
					restObject: RicercaModelliForm,
					dynamicField: boolean
				) => {
					let mapping: boolean = true;

					let keyAny: any = get(restObject, field.mappingInput);
					let key: string =
						dynamicField === true ? field.data : toString(keyAny);
					if (
						!isEmpty(field.listCode) &&
						field.type === "TEXT" &&
						!isEmpty(key)
					) {
						[field, mapping] = this.formBuilderUtilsService.populateListCode(
							this.valoriTabellati,
							field,
							key,
							mapping
						);
					}
					if (field.code === "utente-creatore" && this.form.utenteCreatore) {

						let item: SdkComboBoxItem = {
							key: `${this.form.utenteCreatore}`,
							value: this.tabellatiutente.find(
								(utente) => utente.sysCon == this.form.utenteCreatore
							).sysUte,
						};
						set(field, "data", item);
						mapping = false;
					}
					if (field.code === "isOU50") {
						const enabled = this.userProfile.abilitazioni.includes('ou50');
						set(field, "data", enabled ? 1 : 0);
						mapping = false;
					}
					return {
						mapping,
						field,
					};
				};

				formConfig = this.protectionUtilsService.applyFormProtections(
					formConfig,
					this.userProfile.configurations,
					false,
					false,
					false
				);
				formConfig = this.formBuilderUtilsService.populateForm(
					formConfig,
					true,
					customPopulateFunction,
					this.form,
					providerArgs
				);

				this.defaultFormBuilderConfig = cloneDeep(formConfig);
				this.formBuilderConfig = formConfig;

				this.formBuilderConfigObs.next(formConfig);
			});
		}
	};

	private loadButtons(): void {
		this.buttons = {
			buttons: this.protectionUtilsService.checkButtonsProtection(
				this.config.body.buttons,
				this.userProfile.configurations
			),
		};
		this.buttonsSubj = new BehaviorSubject(this.buttons);
	}

	private manageExecutionProvider = (obj: any) => {
		if (obj != null && obj.cleanSearch === true) {
			this.loadForm();
		}
	};

	// #endregion
}
