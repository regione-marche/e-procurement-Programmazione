import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkSwitchConfig, SdkSwitchInput, SdkSwitchOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-switch',
  templateUrl: './form-switch.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormSwitchComponent implements OnInit {

  public switchConfig: Observable<SdkSwitchConfig> = of({
    code: 'id-switch',
    label: 'Label dello switch',
    onLabel: 'On',
    offLabel: 'Off'
  } as SdkSwitchConfig);

  public switchData: Observable<SdkSwitchInput> = of({
    checked: true
  } as SdkSwitchInput);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkSwitchOutput): void {
    console.log(item);
  }

}
