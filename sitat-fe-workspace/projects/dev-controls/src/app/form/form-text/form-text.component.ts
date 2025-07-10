import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkTextConfig, SdkTextInput, SdkTextOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-text',
  templateUrl: './form-text.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormTextComponent implements OnInit {

  public textConfig: Observable<SdkTextConfig> = of({
    code: 'id-text',
    label: 'Label del text',
    currency: true,
    decimals: 5,
    customSymbol: 'mq'
  } as SdkTextConfig);

  public textData: Observable<SdkTextInput> = of({
    data: '123.1234'
  } as SdkTextInput);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkTextOutput): void {
    console.log(item);
  }
}
