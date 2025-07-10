import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import {
  SdkBasicButtonOutput,
  SdkButtonGroupInput,
  SdkNotificationConfig,
  SdkNotificationService,
} from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'notifications',
  templateUrl: './notifications.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class NotificationsComponent implements OnInit {

  private notificationConfigSemplice: SdkNotificationConfig = {
    content: 'Test Notification',
    severity: 'success'
  };

  public inputGroup: Observable<SdkButtonGroupInput> = of({
    buttons: [
      { code: 'btn', label: 'Testo semplice' }
    ]
  } as SdkButtonGroupInput);

  constructor(private sdkNotificationService: SdkNotificationService) { }

  ngOnInit() {
  }

  public onClick(output: SdkBasicButtonOutput): void {
    console.log(output);
    if (output.code === 'btn') {
      this.sdkNotificationService.show(this.notificationConfigSemplice);
    }
  }

}
