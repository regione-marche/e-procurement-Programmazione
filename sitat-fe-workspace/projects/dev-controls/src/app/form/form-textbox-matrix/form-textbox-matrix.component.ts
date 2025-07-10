import { AfterViewInit, ChangeDetectionStrategy, Component } from '@angular/core';
import { SdkTextboxMatrixConfig, SdkTextboxMatrixInput, SdkTextboxMatrixOutput } from '@maggioli/sdk-controls';
import { Observable, of, Subject } from 'rxjs';

@Component({
  selector: 'form-textbox-matrix',
  templateUrl: './form-textbox-matrix.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormTextboxMatrixComponent implements AfterViewInit {

  public textboxMatrixConfig: Observable<SdkTextboxMatrixConfig> = of({
    code: 'id-textbox-matrix',
    label: 'Label della textbox',
    header: [
      {
        code: this.generateUniqueCode(),
        cells: [
          {
            code: this.generateUniqueCode(),
            label: 'Quadro risorse',
            colspan: 6
          }
        ]
      },
      {
        code: this.generateUniqueCode(),
        cells: [
          {
            code: this.generateUniqueCode(),
            label: 'Tipologia Risorse',
            rowspan: 2
          },
          {
            code: this.generateUniqueCode(),
            label: 'Stima dei costi',
            colspan: 5
          }
        ]
      },
      {
        code: this.generateUniqueCode(),
        cells: [
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
        ]
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
  } as SdkTextboxMatrixConfig);

  public data: Subject<SdkTextboxMatrixInput> = new Subject();

  constructor() { }

  ngAfterViewInit() {
    setTimeout(() => this.data.next({ cellCode: 'IV1TRI', data: 12 }), 5000);
  }

  public onOutput(item: SdkTextboxMatrixOutput): void {
    console.log(item);
  }


  private generateUniqueCode(): string {
    return Math.random().toString(36).slice(2); //NOSONAR
  }
}
