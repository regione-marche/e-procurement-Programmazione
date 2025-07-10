import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkTimepickerConfig, SdkTimepickerInput, SdkTimepickerOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'timepicker',
  templateUrl: './timepicker.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TimepickerComponent implements OnInit {

  public timepickerConfig: Observable<SdkTimepickerConfig> = of({
    code: 'id-timepicker',
    label: 'Label del timepicker'
  } as SdkTimepickerConfig);

  public timepickerData: Observable<SdkTimepickerInput> = of({
    data: new Date(2019, 3, 18, 12, 13)
  } as SdkTimepickerInput);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkTimepickerOutput): void {
    console.log(item);
  }

}
