import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkDocumentsListConfig, SdkDocumentsListOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-documents-list',
  templateUrl: './form-documents-list.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormDocumentsListComponent implements OnInit {

  public documentsListConfig: Observable<SdkDocumentsListConfig> = of({
    code: 'id-sdk-document',
    documents: [
      {
        code: 'id1',
        titolo: 'titolo1',
        binary: 'asd',
        descrizione: 'desc\r\nA capo'
      },
      {
        code: 'id2',
        titolo: 'titolo2',
        url: 'url2'
      }
    ]
  } as SdkDocumentsListConfig);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkDocumentsListOutput): void {
    console.log(item);
  }

}
