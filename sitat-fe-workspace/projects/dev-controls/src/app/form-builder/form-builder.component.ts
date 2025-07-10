import { ChangeDetectionStrategy, Component, AfterViewInit } from '@angular/core';
import { SdkValidatorInput, SdkValidatorOutput } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem, SdkFormBuilderConfiguration, SdkFormBuilderInput, SdkFormBuilderField } from '@maggioli/sdk-controls';
import { isEmpty, isObject } from 'lodash-es';
import { Observable, of, Subject } from 'rxjs';

@Component({
  selector: 'form-builder',
  templateUrl: './form-builder.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormBuilderComponent implements AfterViewInit {

  public formBuilderConfig: Observable<SdkFormBuilderConfiguration> = of(
    {
      fields: [
        {
          type: 'TEXTBOX',
          code: 'id-textbox',
          label: 'Label della textbox',
          mnemonico: 'text',
          actionButtonLabel: 'BUTTONS.ESEGUI-AZIONE',
          infoBox: true,
          validators: [
            {
              config: {
                required: true
              },
              validator: (input: SdkValidatorInput<string>) => {
                return { valid: !isEmpty(input.data), messages: [{ level: 'error', text: 'invalido' }] } as SdkValidatorOutput;
              }
            },
            {
              config: {
                required: true
              },
              validator: (input: SdkValidatorInput<string>) => {
                return {
                  valid: !isEmpty(input.data), messages: [
                    { level: 'error', text: 'aaaaa' },
                    { level: 'warning', text: 'asdasda' }
                  ]
                } as SdkValidatorOutput;
              }
            }
          ]
        },
        {
          type: 'AUTOCOMPLETE',
          code: 'id-autocomplete',
          label: 'Label dell\'autocomplete',
          suggest: true,
          itemsProvider: () => {
            return of([
              {
                text: 'Primo valore'
              },
              {
                text: 'Secondo valore'
              },
              {
                text: 'Terzo valore'
              },
              {
                text: 'Quarto valore'
              }
            ] as Array<SdkAutocompleteItem>);
          },
          mnemonico: 'auto',
          validators: [
            {
              config: {
                required: true
              },
              validator: (input: SdkValidatorInput<string>) => {
                return { valid: input.data === 'Primo valore' || isEmpty(input.data), messages: [{ level: 'error', text: 'invalido' }] } as SdkValidatorOutput;
              }
            }
          ]
        },
        {
          type: 'FORM-SECTION',
          code: 'id-form-section',
          label: 'Label form section',
          mnemonico: 'section',
          fieldSections: [
            {
              type: 'TEXTBOX',
              code: 'id-textbox1',
              label: 'Label della textbox1',
              mnemonico: 'text1',
              actionButtonLabel: 'BUTTONS.ESEGUI-AZIONE'
            },
            {
              type: 'COMBOBOX',
              code: 'id-combobox',
              label: 'Label della combobox',
              mnemonico: 'combobox',
              itemsProvider: () => {
                return of([
                  {
                    key: '1',
                    value: 'Si'
                  },
                  {
                    key: '0',
                    value: 'No'
                  }
                ]);
              }
            },
            {
              type: 'COMBOBOX',
              code: 'id-combobox123',
              label: 'Label della combobox123',
              mnemonico: 'combobox123',
              itemsProvider: () => {
                return of([
                  {
                    key: '1',
                    value: 'Si'
                  },
                  {
                    key: '0',
                    value: 'No'
                  }
                ]);
              }
            },
            {
              type: 'TEXTBOX',
              code: 'id-textbox2',
              label: 'Label della textbox2',
              mnemonico: 'text2',
              visible: false,
              visibleCondition: {
                and: {
                  'id-combobox': {
                    values: [
                      {
                        value: '1',
                        operation: '='
                      },
                      {
                        value: '0',
                        operation: '='
                      }
                    ],
                    visible: false
                  },
                  'id-combobox123': {
                    values: [
                      {
                        value: '0',
                        operation: '='
                      }
                    ],
                    visible: false
                  }
                }
              }
            }
          ]
        },
        {
          type: 'FORM-GROUP',
          code: 'id-form-group',
          label: 'Label form group',
          mnemonico: 'group',
          minGroupsNumber: 0,
          maxGroupsNumber: 5,
          defaultFormGroupFields: [
            {
              type: 'TEXTBOX',
              code: 'id-textbox14',
              label: 'Label della textbox1',
              mnemonico: 'text1',
              actionButtonLabel: 'BUTTONS.ESEGUI-AZIONE'
            },
            {
              type: 'TEXTBOX',
              code: 'id-textbox24',
              label: 'Label della textbox2',
              mnemonico: 'text2'
            },
            {
              type: 'TEXT',
              code: 'provatext',
              label: 'Label provatext',
              mnemonico: 'provatext'
            }
          ]
        },
        {
          type: 'FORM-GROUP',
          code: 'id-form-group1',
          label: 'Label form group1',
          mnemonico: 'group1',
          minGroupsNumber: 1,
          maxGroupsNumber: 3,
          defaultFormGroupFields: [
            {
              type: 'DOCUMENT',
              code: 'id-document1',
              label: 'Label della document1',
              mnemonico: 'document1',
              switchFileLabel: 'Upload File',
              switchUrlLabel: 'Upload URL',
              urlLabel: 'URL',
              fileInputLabel: 'CHOOSE FILE',
              maxFileSize: '2 MB'
            }
          ]
        },
        {
          type: 'FORM-SECTION',
          code: 'id-form-section2',
          label: 'Documents Listaaaaaa',
          mnemonico: 'section2',
          fieldSections: [
            {
              type: 'DOCUMENTS-LIST',
              code: 'id-sdk-document',
              documents: [
                {
                  code: 'id1',
                  titolo: 'titolo1',
                  binary: 'asd'
                },
                {
                  code: 'id2',
                  titolo: 'titolo2',
                  url: 'url2'
                }
              ]
            },
            {
              type: 'TEXTBOX-MATRIX',
              code: 'id-textbox-matrix',
              label: 'Label della textbox',
              header: [
                {
                  code: this.generateUniqueCode(),
                  label: 'Primo Anno'
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Secondo Anno'
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Terzo Anno'
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Annualita\' successive'
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Totale'
                }
              ],
              rows: [
                {
                  code: this.generateUniqueCode(),
                  label: 'Risorse derivanti da entrate aventi destinazione vincolata per legge',
                  cells: [
                    {
                      code: 'DV1TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'DV2TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'DV3TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'DV9TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'DVnTRI',
                      type: 'TEXT',
                      operation: '{0}+{1}+{2}+{3}',
                      operationParams: ['DV1TRI', 'DV2TRI', 'DV3TRI', 'DV9TRI']
                    }
                  ]
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Risorse derivanti da entrate acquisite mediante contrazione di mutuo',
                  cells: [
                    {
                      code: 'MU1TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'MU2TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'MU3TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'MU9TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'MUnTRI',
                      type: 'TEXT',
                      operation: '{0}+{1}+{2}+{3}',
                      operationParams: ['MU1TRI', 'MU2TRI', 'MU3TRI', 'MU9TRI']
                    }
                  ]
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Risorse acquisite mediante apporti di capitale privato',
                  cells: [
                    {
                      code: 'PR1TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'PR2TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'PR3TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'PR9TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'ICPINT',
                      type: 'TEXT',
                      operation: '{0}+{1}+{2}+{3}',
                      operationParams: ['PR1TRI', 'PR2TRI', 'PR3TRI', 'PR9TRI']
                    }
                  ]
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Stanziamenti di bilancio',
                  cells: [
                    {
                      code: 'BI1TRI',
                      type: 'TEXTBOX',
                      value: 539816.24,
                      decimals: 2
                    },
                    {
                      code: 'BI2TRI',
                      type: 'TEXTBOX',
                      decimals: 2
                    },
                    {
                      code: 'BI3TRI',
                      type: 'TEXTBOX',
                      decimals: 2
                    },
                    {
                      code: 'BI9TRI',
                      type: 'TEXTBOX',
                      decimals: 2
                    },
                    {
                      code: 'BInTRI',
                      type: 'TEXT',
                      operation: '{0}+{1}+{2}+{3}',
                      operationParams: ['BI1TRI', 'BI2TRI', 'BI3TRI', 'BI9TRI']
                    }
                  ]
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Finanziamenti art. 3 DL 310/1990',
                  cells: [
                    {
                      code: 'AP1TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'AP2TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'AP3TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'AP9TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'APnTRI',
                      type: 'TEXT',
                      operation: '{0}+{1}+{2}+{3}',
                      operationParams: ['AP1TRI', 'AP2TRI', 'AP3TRI', 'AP9TRI']
                    }
                  ]
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Risorse derivanti da trasferimento immobili',
                  cells: [
                    {
                      code: 'IM1TRI',
                      type: 'TEXT'
                    },
                    {
                      code: 'IM2TRI',
                      type: 'TEXT'
                    },
                    {
                      code: 'IM3TRI',
                      type: 'TEXT'
                    },
                    {
                      code: 'IM9TRI',
                      type: 'TEXT'
                    },
                    {
                      code: 'IMnTRI',
                      type: 'TEXT',
                      operation: '{0}+{1}+{2}+{3}',
                      operationParams: ['IM1TRI', 'IM2TRI', 'IM3TRI', 'IM9TRI']
                    }
                  ]
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Altra tipologia',
                  cells: [
                    {
                      code: 'AL1TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'AL2TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'AL3TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'AL9TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'ALnTRI',
                      type: 'TEXT',
                      operation: '{0}+{1}+{2}+{3}',
                      operationParams: ['AL1TRI', 'AL2TRI', 'AL3TRI', 'AL9TRI']
                    }
                  ]
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Importo disponibilità finanziaria al netto di capitali privati',
                  hidden: true,
                  cells: [
                    {
                      code: 'DI1INT',
                      type: 'TEXT',
                      operation: '{0}+{1}+{2}+{3}+{4}+{5}',
                      operationParams: ['BI1TRI', 'DV1TRI', 'IM1TRI', 'MU1TRI', 'AL1TRI', 'AP1TRI']
                    },
                    {
                      code: 'DI2INT',
                      type: 'TEXT',
                      operation: '{0}+{1}+{2}+{3}+{4}+{5}',
                      operationParams: ['BI2TRI', 'DV2TRI', 'IM2TRI', 'MU2TRI', 'AL2TRI', 'AP2TRI']
                    },
                    {
                      code: 'DI3INT',
                      type: 'TEXT',
                      operation: '{0}+{1}+{2}+{3}+{4}+{5}',
                      operationParams: ['BI3TRI', 'DV3TRI', 'IM3TRI', 'MU3TRI', 'AL3TRI', 'AP3TRI']
                    },
                    {
                      code: 'DI9INT',
                      type: 'TEXT',
                      operation: '{0}+{1}+{2}+{3}+{4}+{5}',
                      operationParams: ['BI9TRI', 'DV9TRI', 'IM9TRI', 'MU9TRI', 'AL9TRI', 'AP9TRI']
                    },
                    {
                      code: 'DInINT',
                      type: 'TEXT'
                    }
                  ]
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Importo complessivo',
                  cells: [
                    {
                      code: 'DN1INTcomp',
                      type: 'TEXT',
                      operation: '{0}+{1}',
                      operationParams: ['DI1INT', 'PR1TRI']
                    },
                    {
                      code: 'DN2INTcomp',
                      type: 'TEXT',
                      operation: '{0}+{1}',
                      operationParams: ['DI2INT', 'PR2TRI']
                    },
                    {
                      code: 'DN3INTcomp',
                      type: 'TEXT',
                      operation: '{0}+{1}',
                      operationParams: ['DI3INT', 'PR3TRI']
                    },
                    {
                      code: 'DN9INTcomp',
                      type: 'TEXT',
                      operation: '{0}+{1}',
                      operationParams: ['DI9INT', 'PR9TRI']
                    },
                    {
                      code: 'DNnINTcomp',
                      type: 'TEXT'
                    }
                  ]
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Spese gia\' sostenute',
                  cells: [
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT'
                    },
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT'
                    },
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT'
                    },
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT'
                    },
                    {
                      code: 'SPESESOST',
                      type: 'TEXT',
                      value: 1
                    }
                  ]
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Totale',
                  cells: [
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT'
                    },
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT'
                    },
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT'
                    },
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT'
                    },
                    {
                      code: 'TOTINT',
                      type: 'TEXT',
                      operation: '{0}+{1}+{2}',
                      operationParams: ['DITINT', 'ICPINT', 'SPESESOST']
                    }
                  ]
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Di cui IVA',
                  cells: [
                    {
                      code: 'IV1TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'IV2TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT'
                    },
                    {
                      code: 'IV9TRI',
                      type: 'TEXTBOX'
                    },
                    {
                      code: 'IVnTRI',
                      type: 'TEXT',
                      operation: '{0}+{1}+{2}',
                      operationParams: ['IV1TRI', 'IV2TRI', 'IV9TRI']
                    }
                  ]
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Importo al netto di IVA',
                  cells: [
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT',
                      operation: '{0}+{1}-{2}',
                      operationParams: ['DI1INT', 'PR1TRI', 'IV1TRI']
                    },
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT',
                      operation: '{0}+{1}-{2}',
                      operationParams: ['DI2INT', 'PR2TRI', 'IV2TRI']
                    },
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT'
                    },
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT',
                      operation: '{0}+{1}-{2}',
                      operationParams: ['DI9INT', 'PR9TRI', 'IV9TRI']
                    },
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT',
                      operation: '{0}-{1}',
                      operationParams: ['TOTINT', 'IVnTRI']
                    }
                  ]
                },
                {
                  code: this.generateUniqueCode(),
                  label: 'Totale importo disp. finanziaria',
                  hidden: true,
                  cells: [
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT'
                    },
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT'
                    },
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT'
                    },
                    {
                      code: this.generateUniqueCode(),
                      type: 'TEXT'
                    },
                    {
                      code: 'DITINT',
                      type: 'TEXT',
                      operation: '{0}+{1}+{2}+{3}',
                      operationParams: ['DI1INT', 'DI2INT', 'DI3INT', 'DI9INT']
                    }
                  ]
                }
              ]
            },
            {
              type: 'CHECKBOX',
              code: 'checkbox',
              label: 'Checkbox',
              items: [
                {
                  code: '1',
                  label: 'Scelta 1'
                },
                {
                  code: '2',
                  label: 'Scelta 2'
                }
              ]
            }
          ],
          visible: false,
          visibleCondition: {
            or: {
              'id-combobox': {
                values: [
                  {
                    value: '1',
                    operation: '='
                  }
                ],
                visible: false
              }
            }
          }
        }
      ]
    } as SdkFormBuilderConfiguration
  );

  public dataSubject: Subject<SdkFormBuilderInput> = new Subject();

  constructor() { }

  public ngAfterViewInit(): void {
    setTimeout(() => {
      console.log('sent');
      this.dataSubject.next({
        code: 'id-textbox1',
        data: 'ciao'
      });
    }, 5000);
  }

  public manageFormOutput(config: SdkFormBuilderConfiguration): void {
    console.log('output config >>>', config);
  }

  private generateUniqueCode(): string {
    return Math.random().toString(36).slice(2); //NOSONAR
  }

  public manageOutputField(field: SdkFormBuilderField): void {
    if (isObject(field)) {
      if (field.code === 'id-textbox14') {
        let input: SdkFormBuilderInput = {
          code: 'id-textbox24',
          groupCode: field.groupCode,
          data: 'test'
        };
        this.dataSubject.next(input);

        input = {
          code: 'provatext',
          groupCode: field.groupCode,
          data: 'provatext'
        };
        this.dataSubject.next(input);
      }
    }
  }

  public manageOutputInfoBox(item: SdkFormBuilderField): void {
    console.log('manageOutputInfoBox >>>', item);
  }

  public manageOutputActionClick(item: SdkFormBuilderField): void {
    console.info('manageOutputActionClick >>>', item);
  }

}
