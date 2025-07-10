import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkSliderConfig, SdkSliderInput, SdkSliderOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-slider',
  templateUrl: './form-slider.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormSliderComponent implements OnInit {

  public sliderConfig: Observable<SdkSliderConfig> = of({
    code: 'id-slider',
    label: 'Label dello slider',
    min: 2,
    max: 15,
    step: 1
  } as SdkSliderConfig);

  public sliderData: Observable<SdkSliderInput> = of({
    data: 7
  } as SdkSliderInput);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkSliderOutput): void {
    console.log(item);
  }

}
