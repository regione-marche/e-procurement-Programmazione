import { HttpClient, HttpClientModule } from '@angular/common/http';
import { Injector, NgModule, Type } from '@angular/core';
import { createCustomElement } from '@angular/elements';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormBuilderUtilsService } from '@maggioli/app-commons';
import { createTranslateLoader, IDictionary, SDK_APP_CONFIG } from '@maggioli/sdk-commons';
import {
  SdkBreadcrumbModule,
  SdkButtonModule,
  SdkDateModule,
  SdkDialogModule,
  SdkExtendedTableModule,
  SdkFormBuilderModule,
  SdkFormModule,
  SdkMenuComponent,
  SdkMenuModule,
  SdkMessagePanelModule,
  SdkModalModule,
  SdkNotificationModule,
  SdkOverlayPanelModule,
  SdkPanelbarModule,
  SdkSearchModule,
  SdkTabsModule,
} from '@maggioli/sdk-controls';
import { SdkTableModule } from '@maggioli/sdk-table';
import { TranslateLoader, TranslateModule } from '@ngx-translate/core';
import { forOwn } from 'lodash-es';

import { environment } from '../environments/environment';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BreadcrumbComponent } from './breadcrumb/breadcrumb.component';
import { BasicButtonComponent } from './button/basic-button/basic-button.component';
import { ButtonDropdownComponent } from './button/button-dropdown/button-dropdown.component';
import { ButtonGroupComponent } from './button/button-group/button-group.component';
import { ToolbarComponent } from './button/toolbar/toolbar.component';
import { DatepickerComponent } from './date/datepicker/datepicker.component';
import { TimepickerComponent } from './date/timepicker/timepicker.component';
import { DialogComponent } from './dialog/dialog.component';
import { DynamicFormSectionComponent } from './dynamic-form-section/dynamic-form-section.component';
import { ExtendedTableComponent } from './extended-table/extended-table.component';
import {
  FormBuilderAdvancedAutocompleteComponent,
} from './form-builder-advanced-autocomplete/form-builder-advanced-autocomplete.component';
import { FormBuilderPickerComponent } from './form-builder-picker/form-builder-picker.component';
import { FormBuilderComponent } from './form-builder/form-builder.component';
import { FormAutocompleteComponent } from './form/form-autocomplete/form-autocomplete.component';
import { FormCheckboxComponent } from './form/form-checkbox/form-checkbox.component';
import { FormComboboxComponent } from './form/form-combobox/form-combobox.component';
import { FormDocumentComponent } from './form/form-document/form-document.component';
import { FormDocumentsListComponent } from './form/form-documents-list/form-documents-list.component';
import { FormMaskedTextboxComponent } from './form/form-masked-textbox/form-masked-textbox.component';
import { FormMultiselectComponent } from './form/form-multiselect/form-multiselect.component';
import { FormNumericTextboxComponent } from './form/form-numeric-textbox/form-numeric-textbox.component';
import { FormRadioComponent } from './form/form-radio/form-radio.component';
import { FormSliderComponent } from './form/form-slider/form-slider.component';
import { FormSwitchComponent } from './form/form-switch/form-switch.component';
import { FormTextComponent } from './form/form-text/form-text.component';
import { FormTextareaComponent } from './form/form-textarea/form-textarea.component';
import { FormTextboxMatrixComponent } from './form/form-textbox-matrix/form-textbox-matrix.component';
import { FormTextboxComponent } from './form/form-textbox/form-textbox.component';
import { FormTreeComponent } from './form/form-tree/form-tree.component';
import { GridComponent } from './grid/grid.component';
import { HomeComponent } from './home/home.component';
import { MenuComponent } from './menu/menu.component';
import { MessagePanelComponent } from './message-panel/message-panel.component';
import { ModalComponent } from './modal/modal.component';
import { NotificationsComponent } from './notifications/notifications.component';
import { OverlayPanelComponent } from './overlay-panel/overlay-panel.component';
import { PanelbarComponent } from './panelbar/panelbar.component';
import { SearchComponent } from './search/search.component';
import { TabsComponent } from './tabs/tabs.component';

@NgModule({
  declarations: [
    AppComponent,
    BasicButtonComponent,
    HomeComponent,
    ButtonGroupComponent,
    ButtonDropdownComponent,
    ToolbarComponent,
    FormTextboxComponent,
    FormTextareaComponent,
    FormSwitchComponent,
    FormSliderComponent,
    FormNumericTextboxComponent,
    FormMaskedTextboxComponent,
    FormComboboxComponent,
    FormMultiselectComponent,
    DatepickerComponent,
    TimepickerComponent,
    FormAutocompleteComponent,
    MenuComponent,
    BreadcrumbComponent,
    SearchComponent,
    TabsComponent,
    NotificationsComponent,
    DialogComponent,
    MessagePanelComponent,
    FormBuilderComponent,
    FormDocumentComponent,
    ModalComponent,
    FormDocumentsListComponent,
    FormRadioComponent,
    FormTextboxMatrixComponent,
    FormTreeComponent,
    GridComponent,
    OverlayPanelComponent,
    PanelbarComponent,
    FormBuilderPickerComponent,
    FormCheckboxComponent,
    DynamicFormSectionComponent,
    FormTextComponent,
    ExtendedTableComponent,
    FormBuilderAdvancedAutocompleteComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    AppRoutingModule,
    SdkButtonModule,
    SdkFormModule,
    SdkDateModule,
    SdkMenuModule,
    SdkBreadcrumbModule,
    SdkSearchModule,
    SdkTabsModule,
    SdkNotificationModule,
    SdkDialogModule,
    SdkMessagePanelModule,
    SdkFormBuilderModule,
    SdkModalModule,
    SdkOverlayPanelModule,
    SdkPanelbarModule,
    SdkExtendedTableModule,
    SdkTableModule.forRoot(),
    TranslateModule.forRoot({
      loader: {
        provide: TranslateLoader,
        useFactory: (createTranslateLoader),
        deps: [HttpClient]
      }
    })
  ],
  providers: [
    FormBuilderUtilsService,
    {
      provide: SDK_APP_CONFIG,
      useValue: {
        environment
      }
    }
  ],
  bootstrap: [AppComponent]
})
export class DevControlsModule {

  constructor(private inj: Injector) { this.defineElements() }

  protected defineElements(): void {
    forOwn(elementsMap(), this.defineElement)
  }

  protected defineElement = (type: Type<any>, key: string): void => {
    customElements.define(key, createCustomElement(type, { injector: this.inj }))
  }
}

function elementsMap(): IDictionary<Type<any>> {
  return {
    'basic-button-element': BasicButtonComponent,
    'sdk-menu-component': SdkMenuComponent,
    'grid-component-element': GridComponent
  };
}
