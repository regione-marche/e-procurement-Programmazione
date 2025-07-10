import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { SdkClickModule } from '@maggioli/sdk-commons';
import { ConfirmationService } from 'primeng/api';

import { CalendarModule } from 'primeng/calendar';
import { MessageModule } from 'primeng/message';
import { PrimeNGModule } from '../../imports/primeng.module';
import { SdkDatePickerDialogComponent } from './components/sdk-datepicker-dialog/sdk-datepicker-dialog.component';
import { SdkDialogNoLabelsComponent } from './components/sdk-dialog-no-confirm-label/sdk-dialog-no-confirm-label.component';
import { SdkDialogComponent } from './components/sdk-dialog/sdk-dialog.component';
import { SdkMotivazioneDialogComponent } from './components/sdk-motivazione-dialog/sdk-motivazione-dialog.component';
import { SdkDoubleChoiceDialogComponent } from './components/sdk-double-choice-dialog/sdk-double-choice-dialog.component';

@NgModule({
  declarations: [
    SdkDialogComponent,
    SdkDialogNoLabelsComponent,
    SdkMotivazioneDialogComponent,
    SdkDatePickerDialogComponent,
    SdkDoubleChoiceDialogComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    PrimeNGModule,
    SdkClickModule,
    CalendarModule,
    MessageModule
  ],
  providers: [
    ConfirmationService
  ],
  exports: [
    SdkDialogComponent,
    SdkDialogNoLabelsComponent,
    SdkMotivazioneDialogComponent,
    SdkDatePickerDialogComponent,
    SdkDoubleChoiceDialogComponent
  ]
})
export class SdkDialogModule { }
