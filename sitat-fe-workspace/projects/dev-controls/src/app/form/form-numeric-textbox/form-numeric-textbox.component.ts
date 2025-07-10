import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkNumericTextboxConfig, SdkNumericTextboxInput, SdkNumericTextboxOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-numeric-textbox',
  templateUrl: './form-numeric-textbox.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormNumericTextboxComponent implements OnInit {

  public numericTextboxConfig: Observable<SdkNumericTextboxConfig> = of({
    code: 'id-numerictextbox',
    label: 'Label della Numeric Textbox',
    decimals: 2,
    maxLength: 5
  } as SdkNumericTextboxConfig);

  public numericTextboxData: Observable<SdkNumericTextboxInput> = of({
    data: 123.12
  } as SdkNumericTextboxInput);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkNumericTextboxOutput): void {
    console.log(item);
  }

}
