import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkComboboxConfig, SdkComboboxInput, SdkComboBoxItem, SdkComboboxOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-combobox',
  templateUrl: './form-combobox.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormComboboxComponent implements OnInit {

  public comboboxConfig: Observable<SdkComboboxConfig> = of({
    code: 'id-combobox',
    label: 'Label della Combobox',
    itemsProvider: () => {
      return of([
        {
          key: '01',
          value: 'Primo valore'
        },
        {
          key: '02',
          value: 'Secondo valore',
          disabled: true
        },
        {
          key: '03',
          value: 'Terzo valore'
        },
        {
          key: '04',
          value: 'Quarto valore'
        }
      ] as Array<SdkComboBoxItem>);
    }
  } as SdkComboboxConfig);

  public comboboxData: Observable<SdkComboboxInput> = of({
    data: {
      key: '02',
      value: undefined
    }
  } as SdkComboboxInput);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkComboboxOutput): void {
    console.log(item);
  }

}
