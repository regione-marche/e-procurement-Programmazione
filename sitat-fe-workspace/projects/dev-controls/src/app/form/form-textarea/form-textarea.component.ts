import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkTextareaConfig, SdkTextareaInput, SdkTextareaOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-textarea',
  templateUrl: './form-textarea.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormTextareaComponent implements OnInit {

  public textareaConfig: Observable<SdkTextareaConfig> = of({
    code: 'id-textarea',
    label: 'Label della textarea'
  } as SdkTextareaConfig);

  public textareaData: Observable<SdkTextareaInput> = of({
    data: 'prova'
  } as SdkTextareaInput);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkTextareaOutput): void {
    console.log(item);
  }


}
