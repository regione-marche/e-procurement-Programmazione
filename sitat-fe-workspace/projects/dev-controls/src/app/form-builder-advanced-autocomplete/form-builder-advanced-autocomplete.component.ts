import { AfterViewInit, ChangeDetectionStrategy, Component } from '@angular/core';
import { IDictionary, IHttpHeaders, SdkRestHelperService } from '@maggioli/sdk-commons';
import { SdkAutocompleteAdvancedModalPageSortFilter, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkFormBuilderInput } from '@maggioli/sdk-controls';
import { isEmpty, toString } from 'lodash-es';
import { Observable, of, Subject } from 'rxjs';

@Component({
  selector: 'form-builder-advanced-autocomplete',
  templateUrl: './form-builder-advanced-autocomplete.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormBuilderAdvancedAutocompleteComponent implements AfterViewInit {

  public formBuilderConfig: Observable<SdkFormBuilderConfiguration> = of(
    {
      fields: [
        {
          type: 'FORM-SECTION',
          code: 'id-form-section',
          label: 'Label form section',
          fieldSections: [
            {
              type: 'AUTOCOMPLETE',
              code: 'autocomplete1',
              label: 'Autocomplete 1',
              advanced: true,
              advancedModalComponentConfig: {
                paginationEnabled: true,
                defaultSortField: 'username',
                defaultSortDirection: 'desc',
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
                  },
                  {
                    code: 'codiceFiscale',
                    label: 'Codice Fiscale',
                    mappingInput: 'codiceFiscale'
                  }
                ],
                entityKey: 'nome',
                entityText: (data: any) => {
                  return `${data.nome}`;
                },
                itemsProvider: (baseFilters: IDictionary<any>, detailData: string, tablePageSortFilter: SdkAutocompleteAdvancedModalPageSortFilter) => {

                  let params: IDictionary<any> = {
                    skip: tablePageSortFilter != null && tablePageSortFilter.skip != null ? toString(tablePageSortFilter.skip) : '0',
                    take: tablePageSortFilter != null && tablePageSortFilter.take != null ? toString(tablePageSortFilter.take) : '10000',
                    sort: tablePageSortFilter != null && tablePageSortFilter.sortField != null ? tablePageSortFilter.sortField : 'sysute',
                    sortDirection: tablePageSortFilter != null && tablePageSortFilter.sortDirection != null ? tablePageSortFilter.sortDirection : 'asc'
                  };

                  if (baseFilters != null) {
                    console.log('baseFilters >>>', baseFilters);
                    params.denominazione = baseFilters['autocomplete1'];
                    if (baseFilters['autocomplete2'] != null) {
                      params.codiceFiscale = baseFilters['autocomplete2'];
                    }
                  }

                  if (!isEmpty(detailData)) {
                    params.codiceFiscale = detailData;
                  }

                  let headers: IHttpHeaders = {
                    'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NzE0NjE4NzQsInN1YiI6Ik1BTkFHRVIiLCJpc3MiOiJJU1NVRVJfVEVTVCIsImF1ZCI6Ik1BTkFHRVIiLCJleHAiOjE3MDI5OTc4NzQsIlVTRVJfQ0YiOiJNQU5BR0VSIiwiVVNFUl9OQU1FIjoiTUFOQUdFUiIsIlVTRVJfU1VSTkFNRSI6Ik1BTkFHRVIiLCJVU0VSX0RFU0NSSVBUSU9OIjoiTUFOQUdFUiIsIm5hbWUiOiJNQU5BR0VSIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiTUFOQUdFUiIsImZhbWlseV9uYW1lIjoiTUFOQUdFUiJ9.1x6TyV6HKl9OU0C-eQzW6NsYDBNi3p9mKOJQvgD1-jEYvLGzxh2zyeWxZi0Rqpw4bATCcY8g7OeRDe52DE4_jA',
                    'X-loginMode': '1'
                  };

                  return this.sdkRestHelperService.get('http://localhost:8880/sso-integr-ms/gestioneUtenti/v1/lista', params, headers);
                },
                actionsLabel: 'form.actions',
                searchLabel: 'form.search'
              },
              advancedAutocompleteCampiCollegati: [
                // 'autocomplete2',
                'textarea1'
              ]
            },
            // {
            //   type: 'AUTOCOMPLETE',
            //   code: 'autocomplete2',
            //   label: 'Autocomplete 2',
            //   advanced: true,
            //   advancedModalComponentConfig: {
            //     columns: [
            //       {
            //         code: 'syscon',
            //         label: 'Syscon',
            //         mappingInput: 'syscon'
            //       },
            //       {
            //         code: 'username',
            //         label: 'Username',
            //         mappingInput: 'username'
            //       },
            //       {
            //         code: 'nome',
            //         label: 'Nome',
            //         mappingInput: 'nome'
            //       },
            //       {
            //         code: 'codiceFiscale',
            //         label: 'Codice Fiscale',
            //         mappingInput: 'codiceFiscale'
            //       }
            //     ],
            //     entityKey: 'codiceFiscale',
            //     entityText: (data: any) => {
            //       return `${data.codiceFiscale}`;
            //     },
            //     itemsProvider: (baseFilters: IDictionary<any>, detailData: string, skip: number, take: number) => {

            //       let params: IDictionary<any> = {
            //         skip: skip != null ? toString(skip) : '0',
            //         take: take != null ? toString(take) : '10000',
            //         sort: 'sysute',
            //         sortDirection: 'asc'
            //       };

            //       if (baseFilters != null) {
            //         console.log('baseFilters >>>', baseFilters);
            //         params.codiceFiscale = baseFilters['autocomplete2'];
            //         if (baseFilters['autocomplete1'] != null) {
            //           params.denominazione = baseFilters['autocomplete1'];
            //         }
            //       }

            //       if (!isEmpty(detailData)) {
            //         params.codiceFiscale = detailData;
            //       }

            //       let headers: IHttpHeaders = {
            //         'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJpYXQiOjE2NzE0NjE4NzQsInN1YiI6Ik1BTkFHRVIiLCJpc3MiOiJJU1NVRVJfVEVTVCIsImF1ZCI6Ik1BTkFHRVIiLCJleHAiOjE3MDI5OTc4NzQsIlVTRVJfQ0YiOiJNQU5BR0VSIiwiVVNFUl9OQU1FIjoiTUFOQUdFUiIsIlVTRVJfU1VSTkFNRSI6Ik1BTkFHRVIiLCJVU0VSX0RFU0NSSVBUSU9OIjoiTUFOQUdFUiIsIm5hbWUiOiJNQU5BR0VSIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiTUFOQUdFUiIsImZhbWlseV9uYW1lIjoiTUFOQUdFUiJ9.1x6TyV6HKl9OU0C-eQzW6NsYDBNi3p9mKOJQvgD1-jEYvLGzxh2zyeWxZi0Rqpw4bATCcY8g7OeRDe52DE4_jA',
            //         'X-loginMode': '1'
            //       };

            //       return this.sdkRestHelperService.get('http://localhost:8880/sso-integr-ms/gestioneUtenti/v1/lista', params, headers);
            //     },
            //     actionsLabel: 'form.actions',
            //     searchLabel: 'form.search'
            //   },
            //   advancedAutocompleteCampiCollegati: [
            //     'autocomplete1',
            //     'textarea1'
            //   ]
            // },
            {
              type: 'TEXTAREA',
              code: 'textarea1',
              label: 'Textarea 1'
            }
          ]
        }
      ]
    } as SdkFormBuilderConfiguration
  );

  public formBuilderData: Subject<SdkFormBuilderInput> = new Subject();

  constructor(private sdkRestHelperService: SdkRestHelperService) { }

  public ngAfterViewInit(): void {
    setTimeout(() => {
      this.formBuilderData.next({
        code: 'textarea1',
        data: 'aaaa'
      })
    }, 500);
  }

  public manageFormOutput(config: SdkFormBuilderConfiguration): void {
    console.log('output config >>>', config);
  }

  public manageOutputAdvancedModalClose(item: SdkFormBuilderField): void {
    console.log('manageOutputAdvancedModalClose >>>', item);
  }

}
