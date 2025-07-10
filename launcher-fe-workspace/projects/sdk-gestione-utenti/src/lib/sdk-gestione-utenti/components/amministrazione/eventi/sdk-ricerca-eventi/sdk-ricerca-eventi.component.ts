import { HttpErrorResponse } from '@angular/common/http';
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
  SdkButtonGroupInput,
  SdkButtonGroupOutput,
  SdkFormBuilderConfiguration,
  SdkFormBuilderField,
  SdkMessagePanelService,
} from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject, throwError } from 'rxjs';
import { catchError, map, mergeMap } from 'rxjs/operators';

import { RicercaEventiFormDTO, RicercaEventiInizDTO } from '../../../../model/amministrazione.model';
import { CustomParamsFunction, ResponseDTO, ValoreTabellato } from '../../../../model/lib.model';
import { SdkGestioneUtentiConstants } from '../../../../sdk-gestione-utenti.constants';
import { GestioneEventiService } from '../../../../services/gestione-eventi.service';
import { TabellatiCacheService } from '../../../../services/tabellati/tabellati-cache.service';
import { FormBuilderUtilsService } from '../../../../utils/form-builder-utils.service';
import { ProtectionUtilsService } from '../../../../utils/protection-utils.service';

@Component({
  templateUrl: './sdk-ricerca-eventi.component.html'
})
export class SdkRicercaEventiComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

  // #region Variables

  @HostBinding('class') classNames = `sdk-ricerca-eventi-section`;

  @ViewChild('messages') _messagesPanel: ElementRef;
  @ViewChild('infoBox') _infoBox: ElementRef;

  public config: any = {};

  public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
  public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();

  private buttons: SdkButtonGroupInput;
  private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
  private formBuilderConfig: SdkFormBuilderConfiguration;
  private userProfile: UserProfile;
  private form: RicercaEventiFormDTO;
  private inizRicerca: RicercaEventiInizDTO;
  private valoriTabellati: IDictionary<Array<ValoreTabellato>>;
  private homeSlug: string;

  // #endregion

  constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

  // #region Hooks

  protected onInit(): void {

    this.addSubscription(this.store.select(SdkGestioneUtentiConstants.SEARCH_FORM_GESTIONE_EVENTI_SELECT).subscribe((form: RicercaEventiFormDTO) => {
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
      mergeMap(this.loadInizRicerca),
      map(this.elaborateInizRicerca),
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

  private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

  private get gestioneEventiService(): GestioneEventiService { return this.injectable(GestioneEventiService) }

  private get tabellatiCacheService(): TabellatiCacheService { return this.injectable(TabellatiCacheService) }

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
    return this.tabellatiCacheService.getValoriTabellati(SdkGestioneUtentiConstants.RICERCA_EVENTI_TABELLATI);
  }

  private elaborateTabellati = (result: IDictionary<Array<ValoreTabellato>>) => {
    this.valoriTabellati = result;
  }

  private loadInizRicerca = (): Observable<ResponseDTO<RicercaEventiInizDTO>> => {
    return this.gestioneEventiService.getInizRicercaEventi()
      .pipe(
        catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
          this.sdkMessagePanelService.showError(this.messagesPanel, [
            {
              message: `ERRORS.UNEXPECTED-ERROR`
            }
          ]);
          return throwError(() => error);
        })
      );
  }

  private elaborateInizRicerca = (result: ResponseDTO<RicercaEventiInizDTO>) => {
    this.inizRicerca = result.response;
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
          advancedSearch: true,
          codiciEventi: this.inizRicerca != null && this.inizRicerca.listaCodiciEventi != null ? this.inizRicerca.listaCodiciEventi : []
        };

        if (this.form == null) {
          this.form = {};
        }

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
          let mapping: boolean = true;

          let keyAny: any = get(restObject, field.mappingInput);
          let key: string = dynamicField === true ? field.data : toString(keyAny);

          if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
            [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
          }

          return {
            mapping,
            field
          };
        }

        formConfig = this.protectionUtilsService.applyFormProtections(formConfig, this.userProfile.configurations, false, false, false);
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, this.form, providerArgs);

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

  // #endregion

}
