import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkRadioConfig, SdkRadioInput, SdkRadioOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-radio',
  templateUrl: './form-radio.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormRadioComponent implements OnInit {

  public radioConfig: Observable<SdkRadioConfig> = of({
    code: 'id-radio',
    label: 'Label del radio',
    choices: [
      {
        code: '1',
        label: 'asd1',
        checked: true
      },
      {
        code: '2',
        label: 'asd2'
      },
      {
        code: '3',
        label: 'asd3'
      }
    ]
  } as SdkRadioConfig);

  public radioData: Observable<SdkRadioInput> = of({
    data: {
      code: '2',
      label: 'asd2'
    }
  } as SdkRadioInput);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkRadioOutput): void {
    console.log(item);
  }

}
