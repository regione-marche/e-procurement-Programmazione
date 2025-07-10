import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkMultiselectConfig, SdkMultiselectInput, SdkMultiselectItem, SdkMultiselectOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-multiselect',
  templateUrl: './form-multiselect.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormMultiselectComponent implements OnInit {

  public multiselectConfig: Observable<SdkMultiselectConfig> = of({
    code: 'id-multiselect',
    label: 'Label della Multiselect',
    itemsProvider: () => {
      return of([
        {
          key: '01',
          value: 'Primo valore'
        },
        {
          key: '02',
          value: 'Secondo valore'
        },
        {
          key: '03',
          value: 'Terzo valore'
        },
        {
          key: '04',
          value: 'Quarto valore'
        }
      ] as Array<SdkMultiselectItem>);
    }
  } as SdkMultiselectConfig);

  public multiselectData: Observable<SdkMultiselectInput> = of({
    data: [
      {
        key: '02',
        value: 'Secondo Valore'
      },
      {
        key: '03',
        value: 'Terzo Valore'
      }
    ]
  } as SdkMultiselectInput);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkMultiselectOutput): void {
    console.log(item);
  }

}
