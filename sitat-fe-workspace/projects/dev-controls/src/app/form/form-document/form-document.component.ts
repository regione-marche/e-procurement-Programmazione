import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkDocumentConfig, SdkDocumentInput, SdkDocumentOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-document',
  templateUrl: './form-document.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormDocumentComponent implements OnInit {

  public documentConfig: Observable<SdkDocumentConfig> = of({
    code: 'id-sdk-document',
    switchUrlLabel: 'URL',
    switchFileLabel: 'Upload file',
    urlLabel: 'Upload URL',
    fileInputLabel: 'Upload FILE'
  } as SdkDocumentConfig);

  public documentInput: Observable<SdkDocumentInput> = of({
    fileSwitch: true
  } as SdkDocumentInput);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkDocumentOutput): void {
    console.log(item);
  }

}
