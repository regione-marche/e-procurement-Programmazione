import { CommonModule } from '@angular/common';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { IDictionary, SdkClickModule } from '@maggioli/sdk-commons';
import {
  SdkButtonModule,
  SdkDialogModule,
  SdkFormBuilderModule,
  SdkFormModule,
  SdkMessagePanelModule,
  SdkModalModule,
} from '@maggioli/sdk-controls';
import { SdkTableModule } from '@maggioli/sdk-table';
import { TranslateModule } from '@ngx-translate/core';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { TableModule } from 'primeng/table';
import { CalendarModule } from 'primeng/calendar';
import { InputNumberModule } from 'primeng/inputnumber';
import { MultiSelectModule } from 'primeng/multiselect';
import { InputTextareaModule } from 'primeng/inputtextarea';

import { SdkListaModelliComponent } from './components/sdk-lista-modelli/sdk-lista-modelli.component';
import { SdkRicercaModelliComponent } from './components/sdk-ricerca-modelli/sdk-ricerca-modelli.component';
import { SdkGestioneModelliTabellatiComboProvider } from './providers/tabellati.provider';

import { GestioneTabellatiService } from './services/gestione-tabellati.service';
import { GestioneModelliService } from './services/gestione-modelli.service';
import { TabellatiCacheService } from './services/tabellati/tabellati-cache.service';
import { TabellatiService } from './services/tabellati/tabellati.service';
import { FileUtilsService } from './utils/file-utils.service';
import { FormBuilderUtilsService } from './utils/form-builder-utils.service';
import { GestioneModelliValidationUtilsService } from './utils/gestione-modelli-validation-utils.service';
import { GridUtilsService } from './utils/grid-utils.service';
import { MessagesPanelUtilsService } from './utils/messages-panel-utils.service';
import { ProtectionUtilsService } from './utils/protection-utils.service';
import { SdkSearchFormModelliReducer } from './reducers/sdk-search-form-modelli-reducer';
import { SdkDettaglioModelloComponent } from './components/sdk-dettaglio-modello/sdk-dettaglio-modello.component';
import { SdkNuovoModelloComponent } from './components/sdk-nuovo-modello/sdk-nuovo-modello.component';
import { SdkModificaModelloComponent } from './components/sdk-modifica-modello/sdk-modifica-modello.component';
import { SdkModelloProvider } from './providers/sdk-modello.provider';
import { SDKFiltroEnteModalWidget } from './components/sdk-filtroente-modal/sdk-filtroente-modal.widget';
import { SdkListaParametriComponent } from './components/sdk-lista-parametri/sdk-lista-parametri.component';
import { SdkDettaglioParametroComponent } from './components/sdk-dettaglio-parametro/sdk-dettaglio-parametro.component';
import { SdkNuovoParametroComponent } from './components/sdk-nuovo-parametro/sdk-nuovo-parametro.component';
import { SdkModificaParametroComponent } from './components/sdk-modifica-parametro/sdk-modifica-parametro.component';
import { SdkListaModelliProvider } from './providers/sdk-lista-modelli-provider';
import { SdkRicercaModelliProvider } from './providers/sdk-ricerca-modelli-provider';
import { SdkListaModelliPredispostiComponent } from './components/sdk-lista-modelli-predisposti/sdk-lista-modelli-predisposti.component';
import { SdkBreadcrumbsModelliStoreService } from './components/sdk-lista-modelli-predisposti/sdk-breadcrumbsmodelli-store.service';
import { SdkComponiParametriModalModalWidget } from './components/sdk-componi-parametri-modal/sdk-componi-parametri-modal.widget';
import { SdkDateModule } from "../../../../sdk-controls/src/lib/modules/sdk-date/sdk-date.module";
import { SdkCompositoreProvider } from './providers/sdk-compositore.provider';
import { SdkDettaglioModelloStoreService } from './components/sdk-dettaglio-modello/sdk-dettaglio-modello-store.service';
import { SdkDettaglioParametroStoreService } from './components/sdk-dettaglio-parametro/sdk-dettaglio-parametro-store.service';
import { SdkListaModelliPredispostiStoreService } from './components/sdk-lista-modelli-predisposti/sdk-lista-modelli-predisposti-store.service';
import { SdkListaParametriParamsProvider } from './providers/sdk-lista-parametri-params.provider';
import { SdkDettaglioModelloParamsProvider } from './providers/sdk-dettaglio-modello-params.provider';
import { SdkParametroProvider } from './providers/sdk-parametro.provider';
import { SdkGestioneModelliMenuVisibleProvider } from './providers/sdk-gestione-modelli-menu-visible.provider';
import { SdkModelliPredispostiProvider } from './providers/sdk-modelli-predisposti.provider';
import { SdkModelliPredispostiModalProvider } from './providers/sdk-modelli-predisposti-modal.provider';
import { SdkRicercaModelliAutocompleteProvider } from './providers/sdk-ricerca-modelli-autocomplete.provider';
import { DecimalInputDirective } from './directives/decimal-input.directive';

const components: Array<any> = [
  SdkListaModelliComponent,
  SdkDettaglioModelloComponent,
  SdkRicercaModelliComponent,
  SdkNuovoModelloComponent,
  SdkModificaModelloComponent,
  SDKFiltroEnteModalWidget,
  SdkListaParametriComponent,
  SdkDettaglioParametroComponent,
  SdkNuovoParametroComponent,
  SdkModificaParametroComponent,
  SdkListaModelliPredispostiComponent,
  SdkComponiParametriModalModalWidget,
  DecimalInputDirective
];
@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    SdkMessagePanelModule,
    TranslateModule,
    SdkButtonModule,
    SdkFormModule,
    SdkFormBuilderModule,
    SdkTableModule,
    SdkDialogModule,
    TableModule,
    DropdownModule,
    SdkClickModule,
    SdkModalModule,
    SdkDateModule,
    CalendarModule,
    InputNumberModule,
    MultiSelectModule,
    InputTextareaModule,
    FormsModule
  ],
  declarations: [
    components
  ],
  exports: [
    components
  ]
})
export class SdkGestioneModelliModule {
  static forRoot(params?: IDictionary<string>): ModuleWithProviders<SdkGestioneModelliModule> {
    return {
      ngModule: SdkGestioneModelliModule,
      providers: [
        FormBuilderUtilsService,
        ProtectionUtilsService,
        GridUtilsService,
        TabellatiCacheService,
        TabellatiService,
        SdkGestioneModelliTabellatiComboProvider,
        SdkCompositoreProvider,
        SdkModelloProvider,
        GestioneModelliService,
        GestioneModelliValidationUtilsService,
        MessagesPanelUtilsService,
        SdkSearchFormModelliReducer,
        SdkDettaglioModelloStoreService,
        SdkDettaglioParametroStoreService,
        SdkListaModelliPredispostiStoreService,
        FileUtilsService,
        GestioneTabellatiService,
        SdkBreadcrumbsModelliStoreService,
        SdkListaParametriParamsProvider,
        SdkDettaglioModelloParamsProvider,
        SdkParametroProvider,
        SdkGestioneModelliMenuVisibleProvider,
        SdkRicercaModelliProvider,
        SdkListaModelliProvider,
        SdkModelliPredispostiProvider,
        SdkModelliPredispostiModalProvider,
        SdkRicercaModelliAutocompleteProvider,
        {
          provide: 'localStoragePrefix',
          useValue: params != null ? params.localStoragePrefix : null
        }
      ]
    }
  }
}
