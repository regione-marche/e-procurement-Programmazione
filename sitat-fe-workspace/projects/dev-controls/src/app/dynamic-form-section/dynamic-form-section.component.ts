import { ChangeDetectionStrategy, Component, AfterViewInit } from '@angular/core';
import { SdkFormBuilderConfiguration, SdkFormBuilderField, SdkFormBuilderInput } from '@maggioli/sdk-controls';
import { Observable, of, Subject } from 'rxjs';

@Component({
  selector: 'dynamic-form-section',
  templateUrl: './dynamic-form-section.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DynamicFormSectionComponent implements AfterViewInit {

  public formBuilderConfig: Observable<SdkFormBuilderConfiguration> = of(
    {
      fields: [
        {
          type: 'DYNAMIC-FORM-SECTION',
          code: 'prova',
          label: 'Sezione dinamica',
          dynamicFieldValues: [
            {
              descrizione: 'Demolizione',
              codice: 7,
              value: 2
            },
            {
              descrizione: 'Recupero',
              codice: 8,
              value: 2
            },
            {
              descrizione: 'Ristrutturazione',
              codice: 9,
              value: 1
            },
            {
              descrizione: 'Restauro',
              codice: 10,
              value: 2
            },
            {
              descrizione: 'Manutenzione Ordinaria',
              codice: 12,
              value: 2
            },
            {
              descrizione: 'Manutenzione straordinaria',
              codice: 13,
              value: 2
            }
          ]
        }
      ]
    } as SdkFormBuilderConfiguration
  );

  public dataSubject: Subject<SdkFormBuilderInput> = new Subject();

  constructor() { }

  public ngAfterViewInit(): void {
    setTimeout(() => {
      this.dataSubject.next({
        code: 'prova',
        dynamicFieldValues: [
          {
            descrizione: 'Costruzione',
            codice: 6,
            value: 1
          },
          {
            descrizione: 'Demolizione',
            codice: 7,
            value: 2
          },
          {
            descrizione: 'Recupero',
            codice: 8,
            value: 2
          },
          {
            descrizione: 'Ristrutturazione',
            codice: 9,
            value: 1
          },
          {
            descrizione: 'Restauro',
            codice: 10,
            value: 2
          },
          {
            descrizione: 'Manutenzione Ordinaria',
            codice: 12,
            value: 2
          },
          {
            descrizione: 'Manutenzione straordinaria',
            codice: 13,
            value: 1
          }
        ]
      });
    }, 2000);
  }

  public manageFormOutput(config: SdkFormBuilderConfiguration): void {
    console.log('output config >>>', config);
  }

  public manageOutputField(field: SdkFormBuilderField): void {
    console.log('manageOutputField >>>', field);
  }

  public manageOutputInfoBox(item: SdkFormBuilderField): void {
    console.log('manageOutputInfoBox >>>', item);
  }

}
