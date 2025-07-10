import { CommonModule } from '@angular/common';
import { ModuleWithProviders, NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FileUtilsService, GridUtilsService, MessagesPanelUtilsService } from '@maggioli/app-commons';
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
import { CalendarModule } from 'primeng/calendar';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { TableModule } from 'primeng/table';
import { TooltipModule } from 'primeng/tooltip';
import { GestioneTabellatiService } from 'projects/sdk-gestione-utenti/src/lib/sdk-gestione-utenti/services/gestione-tabellati.service';
import { SdkDettaglioParametroReportComponent } from './components/sdk-dettaglio-parametro-report/sdk-dettaglio-parametro-report.component';
import { SdkInserisciParametriReportComponent } from './components/sdk-inserisci-parametri-report/sdk-inserisci-parametri-report.component';
import { SdkListaReportComponent } from './components/sdk-lista-report/sdk-lista-report.component';
import { SdkModificaUfficiIntestatariReportComponent } from './components/sdk-modifica-uffici-intestatari-report/sdk-modifica-uffici-intestatari-report.component';
import { SdkNuovoParametroReportComponent } from './components/sdk-nuovo-parametro-report/sdk-nuovo-parametro-report.component';
import { SdkParametriReportComponent } from './components/sdk-parametri-report/sdk-parametri-report.component';
import { SdkProfiliReportComponent } from './components/sdk-profili-report/sdk-profili-report.component';
import { SdkUfficiIntestatariReportComponent } from './components/sdk-uffici-intestatari-report/sdk-uffici-intestatari-report.component';
import {
  SdkCreaNuovoReportComponent,
  SdkDatiGeneraliReportComponent,
  SdkDefinizioneReportComponent,
  SdkListaReportPredefinitiComponent,
  SdkModificaDatiGeneraliReportComponent,
  SdkModificaDefinizioneReportComponent,
  SdkModificaDettaglioParametroComponent,
  SdkModificaProfiliReportComponent,
  SdkRisultatoEsecuzioneReportComponent,
  SdkRisultatoEsecuzioneReportPredefinitiComponent
} from './sdk-gestione-report.exports';
import { GestioneParametriService } from './services/gestione-parametri.service';
import { GestioneReportService } from './services/gestione-report.service';
import { TabellatiCacheService } from './services/tabellati/tabellati-cache.service';
import { TabellatiService } from './services/tabellati/tabellati.service';
import { FormBuilderUtilsService } from './utils/form-builder-utils.service';
import { GestioneReportsValidationUtilsService } from './utils/gestione-reports-validation-utils.service';
import { ProtectionUtilsService } from './utils/protection-utils.service';
import { GestioneReportPredefinitiService } from './services/gestione-report-predefiniti.service';
import { AccordionModule } from 'primeng/accordion';
import { MessagesModule } from 'primeng/messages';

const components: Array<any> = [
  SdkListaReportComponent,
  SdkDatiGeneraliReportComponent,
  SdkDefinizioneReportComponent,
  SdkParametriReportComponent,
  SdkNuovoParametroReportComponent,
  SdkModificaDatiGeneraliReportComponent,
  SdkModificaDefinizioneReportComponent,
  SdkDettaglioParametroReportComponent,
  SdkModificaDettaglioParametroComponent,
  SdkProfiliReportComponent,
  SdkInserisciParametriReportComponent,
  SdkModificaProfiliReportComponent,
  SdkCreaNuovoReportComponent,
  SdkUfficiIntestatariReportComponent,
  SdkModificaUfficiIntestatariReportComponent,
  SdkRisultatoEsecuzioneReportComponent,
  SdkListaReportPredefinitiComponent,
  SdkRisultatoEsecuzioneReportPredefinitiComponent
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
    CalendarModule,
    TooltipModule,
    AccordionModule,
    MessagesModule
  ],
  declarations: [
    components
  ],
  exports: [
    components
  ]
})
export class SdkGestioneReportsModule {
  static forRoot(params?: IDictionary<string>): ModuleWithProviders<SdkGestioneReportsModule> {
    return {
      ngModule: SdkGestioneReportsModule,
      providers: [
        FormBuilderUtilsService,
        ProtectionUtilsService,
        GridUtilsService,
        TabellatiCacheService,
        TabellatiService,
        MessagesPanelUtilsService,
        FileUtilsService,
        GestioneTabellatiService,
        GestioneReportService,
        GestioneParametriService,
        GestioneReportsValidationUtilsService,
        GestioneReportPredefinitiService,
        {
          provide: 'localStoragePrefix',
          useValue: params != null ? params.localStoragePrefix : null
        }
      ]
    }
  }
}
