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
} from '@maggioli/sdk-controls';
import { SdkTableModule } from '@maggioli/sdk-table';
import { TranslateModule } from '@ngx-translate/core';
import { ButtonModule } from 'primeng/button';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { TableModule } from 'primeng/table';
import { MnemonicoModalWidget } from './components/layout/mnemonico-modal/mnemonico-modal.widget';
import { MnemonicoService } from './services/mnemonico.service';



//import { SdkWdocdigBreadcrumbProvider } from './providers/sdk-wdocdig-breadcrumb.provider';
//import { SdkWdocdigProvider } from './providers/sdk-wdocdig.provider';

const components: Array<any> = [
  // SdkWdocdigCreateSectionWidget,
  // SdkWdocdigDetailsSectionWidget,
  MnemonicoModalWidget
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
    SdkClickModule
  ],
  declarations: [
    components
  ],
  exports: [
    components
  ]
})
export class SdkAppaltiecontrattiBaseModule {
  static forRoot(params?: IDictionary<string>): ModuleWithProviders<SdkAppaltiecontrattiBaseModule> {
    return {
      ngModule: SdkAppaltiecontrattiBaseModule,
      providers: [

        //SdkWdocdigBreadcrumbProvider,
        //SdkWdocdigProvider,
        MnemonicoService
      ]
    }
  }
}
