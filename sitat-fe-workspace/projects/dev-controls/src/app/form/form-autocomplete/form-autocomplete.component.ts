import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { IDictionary, IHttpHeaders, SdkRestHelperService } from '@maggioli/sdk-commons';
import {
  SdkAutocompleteAdvancedModalPageSortFilter,
  SdkAutocompleteConfig,
  SdkAutocompleteInput,
  SdkAutocompleteItem,
  SdkAutocompleteOutput,
} from '@maggioli/sdk-controls';
import { toString } from 'lodash-es';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-autocomplete',
  templateUrl: './form-autocomplete.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormAutocompleteComponent implements OnInit {

  public autocompleteConfig: Observable<SdkAutocompleteConfig> = of({
    code: 'id-autocomplete',
    label: 'Label dell\'autocomplete',
    suggest: true,
    itemsProvider: () => {
      return of([
        {
          _key: 'pri',
          text: 'Primo valore'
        },
        {
          _key: 'sec',
          text: 'Secondo valore'
        },
        {
          _key: 'ter',
          text: 'Terzo valore'
        },
        {
          _key: 'qua',
          text: 'Quarto valore'
        }
      ] as Array<SdkAutocompleteItem>);
    },
    newEditAvailable: true,
    newItemButton: {
      code: 'new',
      label: 'NEW'
    },
    editItemButton: {
      code: 'new',
      label: 'NEW'
    },
    advanced: true,
    advancedModalComponentConfig: {
      columns: [
        {
          code: 'syscon',
          label: 'Syscon',
          mappingInput: 'syscon'
        },
        {
          code: 'username',
          label: 'Username',
          mappingInput: 'username'
        },
        {
          code: 'nome',
          label: 'Nome',
          mappingInput: 'nome'
        }
      ],
      entityKey: 'codtec',
      entityText: (data: any) => {
        return `${data.nome} (${data.syscon})`;
      },
      itemsProvider: (baseFilters: IDictionary<any>, detailData: string, tablePageSortFilter: SdkAutocompleteAdvancedModalPageSortFilter) => {

        let params: IDictionary<any> = {
          skip: toString(tablePageSortFilter.skip),
          take: toString(tablePageSortFilter.take),
          sort: tablePageSortFilter != null && tablePageSortFilter.sortField != null ? tablePageSortFilter.sortField : 'sysute',
          sortDirection: tablePageSortFilter != null && tablePageSortFilter.sortDirection != null ? tablePageSortFilter.sortDirection : 'asc'
        };

        if (baseFilters != null) {
          params.denominazione = baseFilters['id-autocomplete'];
        }

        let headers: IHttpHeaders = {
          'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NzE0NjE4NzQsInN1YiI6Ik1BTkFHRVIiLCJpc3MiOiJJU1NVRVJfVEVTVCIsImF1ZCI6Ik1BTkFHRVIiLCJleHAiOjE3MDI5OTc4NzQsIlVTRVJfQ0YiOiJNQU5BR0VSIiwiVVNFUl9OQU1FIjoiTUFOQUdFUiIsIlVTRVJfU1VSTkFNRSI6Ik1BTkFHRVIiLCJVU0VSX0RFU0NSSVBUSU9OIjoiTUFOQUdFUiIsIm5hbWUiOiJNQU5BR0VSIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiTUFOQUdFUiIsImZhbWlseV9uYW1lIjoiTUFOQUdFUiJ9.1x6TyV6HKl9OU0C-eQzW6NsYDBNi3p9mKOJQvgD1-jEYvLGzxh2zyeWxZi0Rqpw4bATCcY8g7OeRDe52DE4_jA',
          'X-loginMode': '1'
        };

        return this.sdkRestHelperService.get('http://localhost:8880/sso-integr-ms/gestioneUtenti/v1/lista', params, headers);
      },
      actionsLabel: 'form.actions',
      searchLabel: 'form.search'
    }
  } as SdkAutocompleteConfig);

  public autocompleteData: Observable<SdkAutocompleteInput> = of({
    data: {
      text: 'manager'
    }
  } as SdkAutocompleteInput);

  constructor(private sdkRestHelperService: SdkRestHelperService) { }

  ngOnInit() {
  }

  public onOutput(item: SdkAutocompleteOutput): void {
    console.log(item);
  }

}
