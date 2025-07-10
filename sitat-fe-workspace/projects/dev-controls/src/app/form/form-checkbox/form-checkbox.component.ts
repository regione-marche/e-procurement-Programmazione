import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkCheckboxOutput, SdkCheckboxConfig, SdkCheckboxInput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-checkbox',
  templateUrl: './form-checkbox.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormCheckboxComponent implements OnInit {

  public config: Observable<SdkCheckboxConfig> = of(
    {
      code: 'checkbox',
      label: 'Checkbox',
      items: [
        {
          code: '1',
          label: 'Scelta 1',
          checked: true
        },
        {
          code: '2',
          label: 'Scelta 2'
        }
      ]
    }
  );

  public data: Observable<SdkCheckboxInput> = of(
    {
      code: 'checkbox',
      items: [
        {
          code: '2',
          checked: true
        }
      ]
    }
  );

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkCheckboxOutput): void {
    console.log(item);
  }

}
