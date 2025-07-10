import {
  ChangeDetectorRef,
  Component,
  ElementRef,
  HostBinding,
  Injector,
  OnDestroy,
  OnInit,
  ViewChild,
} from '@angular/core';
import {
  IDictionary,
  SdkBusinessAbstractWidget,
  SdkProviderService,
  SdkStoreService,
  UserProfile,
} from '@maggioli/sdk-commons';
import {
  SdkAutocompleteItem,
  SdkButtonGroupInput,
  SdkButtonGroupOutput,
  SdkFormBuilderConfiguration,
  SdkFormBuilderField,
  SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { cloneDeep, get, has, isEmpty, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { InitRicercaUtentiDTO, ProfiloDTO, RicercaUtentiFormDTOInternal, UfficioIntestatarioDTO } from '../../model/gestione-utenti.model';
import { CustomParamsFunction, ResponseDTO, ValoreTabellato } from '../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../sdk-gestione-utenti.constants';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';
import { TabellatiCacheService } from '../../services/tabellati/tabellati-cache.service';
import { FormBuilderUtilsService } from '../../utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../utils/protection-utils.service';

@Component({
  templateUrl: './sdk-ricerca-utenti.component.html'
})
export class SdkRicercaUtentiComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

  // #region Variables

  @HostBinding('class') classNames = `sdk-ricerca-utenti-section`;

  @ViewChild('messages') _messagesPanel: ElementRef;
  @ViewChild('infoBox') _infoBox: ElementRef;

  public config: any = {};

  public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
  public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();

  private buttons: SdkButtonGroupInput;
  private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
  private formBuilderConfig: SdkFormBuilderConfiguration;
  private userProfile: UserProfile;
  private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
  private form: RicercaUtentiFormDTOInternal;
  private homeSlug: string;
  private initRicercaUtenti: InitRicercaUtentiDTO;

  // #endregion

  constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

  // #region Hooks

  protected onInit(): void {

    this.addSubscription(this.store.select(SdkGestioneUtentiConstants.SEARCH_FORM_UTENTI_SELECT).subscribe((form: RicercaUtentiFormDTOInternal) => {
      this.form = form;
    }));

    this.addSubscription(this.store.select<UserProfile>(SdkGestioneUtentiConstants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
      this.userProfile = userProfile;
    }));

    this.loadButtons();
  }

  protected onAfterViewInit(): void {
    this.loadTabellati().pipe(
      map(this.elaborateTabellati),
      mergeMap(this.loadUfficioIntestatario),
      map(this.elaborateUfficioIntestatario),
      mergeMap(this.loadProfilo),
      map(this.elaborateProfilo),
      mergeMap(this.loadInitRicercaUtenti),
      map(this.elaborateInitRicercaUtenti),
      map(() => this.checkInfoBox()),
      map(() => this.loadForm())
    ).subscribe();
  }

  protected onDestroy(): void { }

  protected onConfig(config: any): void {
    if (config != null) {
      this.markForCheck(() => {
        this.config = { ...config };
        this.homeSlug = this.config.homeSlug;
        this.isReady = true
      });
    }
  }

  protected onUpdateState(_state: boolean): void { }

  // #endregion

  // #region Getters

  private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

  private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

  private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

  private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

  private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

  private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

  private get gestioneUtentiService(): GestioneUtentiService { return this.injectable(GestioneUtentiService) }

  // #endregion

  // #region Public

  public manageFormOutput(config: SdkFormBuilderConfiguration): void {
    this.formBuilderConfig = config;
  }

  public onButtonClick(button: SdkButtonGroupOutput): void {
    if (button != null && isEmpty(button.provider) === false) {
      this.addSubscription(
        this.provider.run(button.provider, {
          buttonCode: button.code,
          defaultFormBuilderConfig: this.defaultFormBuilderConfig,
          formBuilderConfig: this.formBuilderConfig,
          homeSlug: this.homeSlug
        }).subscribe(this.manageExecutionProvider)
      );
    }
  }

  // #endregion

  // #region Private

  private get messagesPanel(): HTMLElement {
    return this._messagesPanel != null ? this._messagesPanel.nativeElement : undefined;
  }

  private get infoBox(): HTMLElement {
    return this._infoBox != null ? this._infoBox.nativeElement : undefined;
  }

  private loadTabellati = (): Observable<IDictionary<Array<ValoreTabellato>>> => {
    return this.tabellatiCacheService.getValoriTabellati(SdkGestioneUtentiConstants.RICERCA_UTENTI_TABELLATI);
  }

  private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
    this.valoriTabellati = result;
  }

  private checkInfoBox(): void {
    if (!isEmpty(this.config.infoBox)) {
      this.sdkMessagePanelService.clear(this.infoBox);
      this.sdkMessagePanelService.showInfoBox(this.infoBox, {
        message: this.config.infoBox
      });
    }
  }

  private loadForm = () => {
    if (this.config != null) {
      this.markForCheck(() => {
        let formConfig: SdkFormBuilderConfiguration = {
          fields: cloneDeep(get(this.config, 'body.fields'))
        };

        let providerArgs: IDictionary<any> = {
          advancedSearch: true
        };

        if (this.form == null) {
          this.form = {};
        }

        const searchForm: IDictionary<any> = {
          ...this.form,
          ufficioAppartenenzaVisible: !this.isEmptyUfficioAppartenenza(),
          categoriaVisible: !this.isEmptyCategoria()
        }

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
          let mapping: boolean = true;

          let keyAny: any = get(restObject, field.mappingInput);
          let key: string = dynamicField === true ? field.data : toString(keyAny);

          if (field.code === 'ufficio-intestatario') {
            let data: UfficioIntestatarioDTO = get(restObject, field.mappingInput);
            if (data != null) {
              let item: SdkAutocompleteItem = {
                ...data,
                text: `${data.denominazione} (${data.codice})`,
                _key: data.codice
              };
              set(field, 'data', item);
              mapping = false;
            }
          } else if (field.code === 'profilo') {
            let data: ProfiloDTO = get(restObject, field.mappingInput);
            if (data != null) {
              let item: SdkAutocompleteItem = {
                ...data,
                text: `${data.nome} (${data.codice})`,
                _key: data.codice
              };
              set(field, 'data', item);
              mapping = false;
            }
          }

          if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
            [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
          }

          return {
            mapping,
            field
          };
        }

        let utenteDelegato: string = this.initRicercaUtenti != null && this.initRicercaUtenti.utenteDelegatoGestioneUtenti ? '1' : '2';
        let registrazioneLoginCF: string = this.initRicercaUtenti != null && this.initRicercaUtenti.registrazioneLoginCF ? '1' : '2';

        let sf: IDictionary<any> = {
          ...searchForm,
          utenteDelegato,
          registrazioneLoginCF
        };

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, false, false, false);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, sf, providerArgs);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;

        this.formBuilderConfigObs.next(formConfig);
      });

    }
  }

  private loadButtons(): void {
    this.buttons = {
      buttons: this.protectionUtilsService.checkButtonsProtection(this.config.body.buttons, this.userProfile.configurations)
    };
    this.buttonsSubj = new BehaviorSubject(this.buttons);
  }

  private manageExecutionProvider = (obj: any) => {
    if (obj != null && obj.cleanSearch === true) {
      this.loadForm();
    }
  }

  private isEmptyUfficioAppartenenza(): boolean {
    let empty: boolean = true;

    if (this.valoriTabellati != null) {
      if (has(this.valoriTabellati, 'UfficioAppartenenza')) {
        const valori: Array<ValoreTabellato> = get(this.valoriTabellati, 'UfficioAppartenenza');
        empty = !(valori != null && valori.length > 0);
      } else {
        empty = true;
      }
    }

    return empty;
  }

  private isEmptyCategoria(): boolean {
    let empty: boolean = true;

    if (this.valoriTabellati != null) {
      if (has(this.valoriTabellati, 'CategoriaUtente')) {
        const valori: Array<ValoreTabellato> = get(this.valoriTabellati, 'CategoriaUtente');
        empty = !(valori != null && valori.length > 0);
      } else {
        empty = true;
      }
    }

    return empty;
  }

  private loadUfficioIntestatario = (): Observable<UfficioIntestatarioDTO> => {
    if (this.form != null && this.form.ufficioIntestatarioKey != null) {
      return this.gestioneUtentiService.getUfficioIntestatario(this.form.ufficioIntestatarioKey)
        .pipe(
          map((result: ResponseDTO<UfficioIntestatarioDTO>) => result.response)
        );
    }
    return of({});
  }

  private elaborateUfficioIntestatario = (result: UfficioIntestatarioDTO): void => {
    if (this.form != null && this.form.ufficioIntestatarioKey != null && result != null) {
      this.form.ufficioIntestatario = result;
    }
  }

  private loadProfilo = (): Observable<ProfiloDTO> => {
    if (this.form != null && this.form.profiloKey != null) {
      return this.gestioneUtentiService.getProfilo(this.form.profiloKey)
        .pipe(
          map((result: ResponseDTO<ProfiloDTO>) => result.response)
        );
    }
    return of({});
  }

  private elaborateProfilo = (result: ProfiloDTO): void => {
    if (this.form != null && this.form.profiloKey != null && result != null) {
      this.form.profilo = result;
    }
  }

  private loadInitRicercaUtenti = (): Observable<InitRicercaUtentiDTO> => {
    return this.gestioneUtentiService.initRicercaUtenti()
      .pipe(
        map((result: ResponseDTO<InitRicercaUtentiDTO>) => result.response)
      );
  }

  private elaborateInitRicercaUtenti = (result: InitRicercaUtentiDTO): void => {
    this.initRicercaUtenti = result;
  }

  // #endregion

}
