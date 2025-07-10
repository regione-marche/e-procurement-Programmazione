import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkTextboxConfig, SdkTextboxInput, SdkTextboxOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-textbox',
  templateUrl: './form-textbox.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormTextboxComponent implements OnInit {

  public textboxConfig: Observable<SdkTextboxConfig> = of({
    code: 'id-textbox',
    label: 'Label della textbox',
    infoBox: true
  } as SdkTextboxConfig);

  public textboxConfig2: Observable<SdkTextboxConfig> = of({
    code: 'id-textbox2',
    label: 'Label della textbox 2',
    unlockable: true,
    disabled: true
  } as SdkTextboxConfig);

  public textboxConfig3: Observable<SdkTextboxConfig> = of({
    code: 'id-textbox3',
    label: 'Label della textbox 3',
    actionButtonLabel: 'BUTTONS.ESEGUI-AZIONE'
  } as SdkTextboxConfig);

  public textboxData: Observable<SdkTextboxInput> = of({
    data: 'prova'
  } as SdkTextboxInput);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkTextboxOutput): void {
    console.log(item);
  }

  public onOutputInfoBox(item: SdkTextboxConfig): void {
    console.log(item);
  }

  public manageOutputActionClick(item: SdkTextboxOutput): void {
    console.info('manageOutputActionClick >>>', item);
  }

}
