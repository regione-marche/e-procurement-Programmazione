import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkExtendedTableConfig } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'extended-table-demo',
  templateUrl: './extended-table.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ExtendedTableComponent implements OnInit {

  private extendedTableConfig: SdkExtendedTableConfig;
  public extendedTableConfigObs: Observable<SdkExtendedTableConfig>;

  constructor() { }

  ngOnInit() {
    this.extendedTableConfig = {
      header: [
        {
          code: 'header',
          cells: [
            {
              code: 'col1',
              label: 'quadro-economico.header.col1',
              labelParams: {
                value: 'L00001'
              }
            },
            {
              code: 'col2',
              label: 'quadro-economico.header.col2'
            },
            {
              code: 'col3',
              label: 'quadro-economico.header.col3'
            },
            {
              code: 'col4',
              label: 'quadro-economico.header.col4'
            },
            {
              code: 'col5',
              label: 'quadro-economico.header.col5'
            },
            {
              code: 'col6',
              label: 'quadro-economico.header.col6'
            }
          ]
        }
      ],
      rows: [
        {
          code: 'importi-commessa-row-1',
          cells: [
            {
              code: 'importi-commessa',
              label: 'Importi commessa',
              colspan: 6
            }
          ]
        },
        {
          code: 'importi-commessa-row-2',
          rowClasses: ['yellow-background'],
          cells: [
            {
              code: 'ic-col1',
              label: 'Totale commessa ed anticipazioni'
            },
            {
              code: 'ic-col2',
              value: 100000
            },
            {
              code: 'ic-col3',
              value: 100000
            },
            {
              code: 'ic-col4'
            },
            {
              code: 'ic-col5'
            },
            {
              code: 'ic-col6'
            }
          ]
        },
        {
          code: 'test-children-1',
          cells: [
            {
              code: 'indennita-definitive',
              label: 'Intennita\' definitive',
              colspan: 5
            },
            {
              code: 'indennita-definitive-importo-1',
              value: 10100.91,
              cellClasses: ['yellow-background']
            }
          ],
          children: [
            {
              code: 'child-1',
              rowClasses: ['green-background'],
              cells: [
                {
                  code: 'child-cell-1',
                  label: 'Descrizione figlio',
                  colspan: 6
                }
              ],
              children: [
                {
                  code: 'child-2',
                  cells: [
                    {
                      code: 'child-cell-2',
                      label: 'Descrizione figlio 2',
                      colspan: 6
                    }
                  ]
                }
              ]
            }
          ]
        }
      ],
      legend: [
        {
          code: 'legend-row-head',
          cells: [
            {
              code: 'legend-cell',
              label: 'Legenda: ',
              colspan: 2
            }
          ]
        },
        {
          code: 'legend-row-1',
          cells: [
            {
              code: 'legend-iua',
              label: 'Importi ultima approvazione'
            },
            {
              code: 'legend-iua-desc',
              label: 'Importi del quadro economico relativo all\'ultima approvazione della commessa, ovvero importi dell\'ultimo progetto presente nella scheda "Progetti e Q.E." con data di approvazione valorizzata'
            }
          ]
        }
      ]
    };
    this.extendedTableConfigObs = of(this.extendedTableConfig);
  }

}
