import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkDatepickerConfig, SdkDatepickerInput, SdkDatepickerOutput } from '@maggioli/sdk-controls';
import { BehaviorSubject, Observable, of } from 'rxjs';

@Component({
  selector: 'datepicker',
  templateUrl: './datepicker.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DatepickerComponent implements OnInit {

  public datepickerConfig: Observable<SdkDatepickerConfig> = of({
    code: 'id-datepicker',
    label: 'Label del datepicker',
    format: 'dd/mm/yy',
    infoBox: true
  } as SdkDatepickerConfig);

  public datepickerData: BehaviorSubject<SdkDatepickerInput> = new BehaviorSubject({
    data: new Date(2019, 3, 18),
  } as SdkDatepickerInput);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkDatepickerOutput): void {
    console.log(item);
  }

  public onOutputInfoBox(item: SdkDatepickerConfig): void {
    console.log(item);
  }

  public clear(): void {
    this.datepickerData.next({ data: null });
  }

}
