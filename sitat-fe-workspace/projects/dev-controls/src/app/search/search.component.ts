import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import {
  SdkAdvancedSearchConfig,
  SdkAdvancedSearchItemResult,
  SdkMixedSearchConfig,
  SdkMixedSearchOutput,
  SdkSimpleSearchConfig,
  SdkSimpleSearchInput,
  SdkSimpleSearchOutput,
} from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';


@Component({
  selector: 'search',
  templateUrl: './search.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class SearchComponent implements OnInit {

  public searchConfig: Observable<SdkSimpleSearchConfig> = of({
    code: 'search',
    label: 'Ricerca'
  } as SdkSimpleSearchConfig);


  public searchConfig2: Observable<SdkMixedSearchConfig> = of({
    code: 'search',
    label: 'Ricerca',
    filterList: [
      {
        value: '1',
        label: "Ricerca nei Lavori"
      },
      {
        value: '2',
        label: 'Ricerca nelle Forniture e Servizi'
      },
      {
        value: '0',
        label: 'Ricerca per Entrambi',
        default: true
      }
    ]
  } as SdkMixedSearchConfig);

  public advancedSearchConfig: Observable<SdkAdvancedSearchConfig> = of({
    code: 'advanced-search',
    label: 'Ricerca avanzata',
    categories: [
      {
        label: 'Categoria 1',
        searchFunction: (searchTerm: string) => {
          console.log('searched >>>', searchTerm);
          const result: Array<SdkAdvancedSearchItemResult> = [
            {
              label: 'Item 1',
              original: 'item 1'
            },
            {
              label: 'Item 2',
              original: 'item 2'
            },
            {
              label: 'Item 3',
              original: 'item 3'
            },
            {
              label: 'Item 4',
              original: 'item 4'
            },
            {
              label: 'Item 5',
              original: 'item 5'
            },
            {
              label: 'Item 6',
              original: 'item 6'
            },
            {
              label: 'Item 7',
              original: 'item 7'
            },
            {
              label: 'Item 8',
              original: 'item 8'
            },
            {
              label: 'Item 9',
              original: 'item 9'
            }
          ];

          return of(result).pipe(delay(2000));
        },
        selectItemFunction: (selectedItem: SdkAdvancedSearchItemResult) => {
          console.log('selected >>>', selectedItem);
        }
      },
      {
        label: 'Categoria 2',
        searchFunction: (searchTerm: string) => {
          console.log('searched 2 >>>', searchTerm);
          const result: Array<SdkAdvancedSearchItemResult> = [
            {
              label: 'Item 4',
              original: 'item 4'
            },
            {
              label: 'Item 5',
              original: 'item 5'
            },
            {
              label: 'Item 6',
              original: 'item 6'
            }
          ];

          return of(result).pipe(delay(2000));
        },
        selectItemFunction: (selectedItem: SdkAdvancedSearchItemResult) => {
          console.log('selected 2 >>>', selectedItem);
        }
      }
    ]
  } as SdkAdvancedSearchConfig);

  public searchData: Observable<SdkSimpleSearchInput> = of({
    data: 'test'
  } as SdkSimpleSearchInput);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkSimpleSearchOutput): void {
    console.log(item);
  }

  public onMixedOutput(item: SdkMixedSearchOutput): void {
    console.log(item);
  }
}
