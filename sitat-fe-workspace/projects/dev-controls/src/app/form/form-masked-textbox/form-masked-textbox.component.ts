import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkMaskedTextboxConfig, SdkMaskedTextboxInput, SdkMaskedTextboxOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-masked-textbox',
  templateUrl: './form-masked-textbox.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormMaskedTextboxComponent implements OnInit {

  public maskedTextboxConfig: Observable<SdkMaskedTextboxConfig> = of({
    code: 'id-masked-textbox',
    label: 'Label della Masked Textbox',
    mask: '(999) 999-99-99-99'
  } as SdkMaskedTextboxConfig);

  public maskedTextboxData: Observable<SdkMaskedTextboxInput> = of({
    data: '359884123321'
  } as SdkMaskedTextboxInput);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkMaskedTextboxOutput): void {
    console.log(item);
  }
}
