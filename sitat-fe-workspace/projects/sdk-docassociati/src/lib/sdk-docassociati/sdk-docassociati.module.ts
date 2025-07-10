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

import { SdkC0oggassCreateSectionWidget } from './components/c0oggass/c0oggass-create/sdk-c0oggass-create-section.widget';
import { SdkC0oggassEditSectionWidget } from './components/c0oggass/c0oggass-edit/sdk-c0oggass-edit-section.widget';
import { SdkC0oggassDetailsSectionWidget } from './components/c0oggass/c0oggass-details/sdk-c0oggass-details-section.widget';
import { SdkC0oggassListSectionWidget } from './components/c0oggass/c0oggass-list/sdk-c0oggass-list-section.widget';
import { SdkC0oggassBreadcrumbProvider } from './providers/sdk-c0oggass-breadcrumb.provider';
import { SdkC0oggassProvider } from './providers/sdk-c0oggass.provider';
import { SdkDocAssociatiService } from './services/sdk-docassociati.service';
import { SdkC0oggassDetailsStoreService } from './components/c0oggass/sdk-c0oggass-details-store.service';
import { SdkDocAssociatiTabellatiComboProvider } from './providers/sdk-docassociati-tabellati-combo.provider';
import { SdkSignModalWidget } from './components/c0oggass/c0oggass-details/sign-modal/sdk-sign-modal.widget';

const components: Array<any> = [
  SdkC0oggassListSectionWidget,
  SdkC0oggassDetailsSectionWidget,
  SdkC0oggassCreateSectionWidget,
  SdkC0oggassEditSectionWidget,
  SdkSignModalWidget
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
    SdkModalModule
  ],
  declarations: [
    components
  ],
  exports: [
    components
  ]
})
export class SdkDocAssociatiModule {
  static forRoot(params?: IDictionary<string>): ModuleWithProviders<SdkDocAssociatiModule> {
    return {
      ngModule: SdkDocAssociatiModule,
      providers: [

        SdkC0oggassBreadcrumbProvider,
        SdkC0oggassProvider,
        SdkC0oggassDetailsStoreService,

        SdkDocAssociatiTabellatiComboProvider,

        SdkDocAssociatiService,


      ]
    }
  }
}
