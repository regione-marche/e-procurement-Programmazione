import { AfterViewInit, ChangeDetectionStrategy, Component, ElementRef, ViewChild } from '@angular/core';
import { SdkMessagePanelService } from '@maggioli/sdk-controls';

@Component({
  selector: 'message-panel',
  templateUrl: './message-panel.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MessagePanelComponent implements AfterViewInit {

  @ViewChild('messagePanel1') public _messagePanel1: ElementRef;
  @ViewChild('messagePanel2') public _messagePanel2: ElementRef;
  @ViewChild('messagePanel3') public _messagePanel3: ElementRef;
  @ViewChild('messagePanel4') public _messagePanel4: ElementRef;

  constructor(private sdkMessagePanelService: SdkMessagePanelService) { }

  public ngAfterViewInit(): void {
    this.sdkMessagePanelService.showError(this.messagePanel1, [
      {
        message: 'Messaggio di errore'
      }
    ]);

    this.sdkMessagePanelService.showInfo(this.messagePanel2, [
      {
        message: 'Messaggio di info'
      }
    ]);

    this.sdkMessagePanelService.showWarning(this.messagePanel3, [
      {
        message: 'Messaggio di warning'
      }
    ]);

    this.sdkMessagePanelService.showSuccess(this.messagePanel4, [
      {
        message: 'Messaggio di success'
      }
    ]);
  }

  private get messagePanel1(): HTMLElement {
    return this._messagePanel1.nativeElement;
  }

  private get messagePanel2(): HTMLElement {
    return this._messagePanel2.nativeElement;
  }

  private get messagePanel3(): HTMLElement {
    return this._messagePanel3.nativeElement;
  }

  private get messagePanel4(): HTMLElement {
    return this._messagePanel4.nativeElement;
  }
}
