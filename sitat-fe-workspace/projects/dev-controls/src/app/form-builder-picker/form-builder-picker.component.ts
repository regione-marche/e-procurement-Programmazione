import { AfterViewInit, ChangeDetectionStrategy, Component } from '@angular/core';
import { CustomParamsFunctionResponse, FormBuilderUtilsService } from '@maggioli/app-commons';
import {
  SdkComboBoxItem,
  SdkFormBuilderConfiguration,
  SdkFormBuilderField,
  SdkFormBuilderInput
} from '@maggioli/sdk-controls';
import { isObject, set } from 'lodash-es';
import { Subject } from 'rxjs';

@Component({
  selector: 'form-builder-picker',
  templateUrl: './form-builder-picker.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormBuilderPickerComponent implements AfterViewInit {

  public formBuilderConfigSubject: Subject<SdkFormBuilderConfiguration> = new Subject();
  public formBuilderConfigSubject2: Subject<SdkFormBuilderConfiguration> = new Subject();
  public dataSubject: Subject<SdkFormBuilderInput> = new Subject();
  public dataSubject2: Subject<SdkFormBuilderInput> = new Subject();
  private formConfig: SdkFormBuilderConfiguration;

  constructor(public formBuilderUtilsService: FormBuilderUtilsService) { }

  public ngAfterViewInit(): void {
    this.formConfig = {
      fields: [
        {
          type: 'FORM-GROUP',
          code: 'partecipanti',
          label: '',
          minGroupsNumber: 0,
          maxGroupsNumber: 5,
          emptyGroupMessage: 'Vuoto',
          defaultFormGroupFields: [
            {
              type: 'TEXT',
              code: 'ragioneSociale',
              label: 'CRITERIO-COPPIE.COPPIE.RAGIONE_SOCIALE',
              mappingInput: 'ragioneSociale'
            },
            {
              type: 'FORM-GROUP',
              code: 'valori',
              label: '',
              minGroupsNumber: 0,
              maxGroupsNumber: 5,
              emptyGroupMessage: 'Vuoto 2',
              defaultFormGroupFields: [
                {
                  type: 'TEXTBOX',
                  code: 'test-val3',
                  label: 'XTESTVAL3',
                  mappingInput: 'garpesiNumpesi',
                  mappingOutput: 'garpesiNumpesi'
                }
              ]
            }
          ]
        }
      ]
    };
    this.formBuilderConfigSubject2.next(this.formConfig);
  }

  public manageFormOutput(config: SdkFormBuilderConfiguration): void {
    console.log('output config >>>', config);
  }

  public manageFormOutput2(config: SdkFormBuilderConfiguration): void {
    console.log('output config 2 >>>', config);
    this.formConfig = config;
  }

  public manageOutputField(field: SdkFormBuilderField): void {
    // if (field != null) {
    //   console.log('output field >>>', field);
    //   if (field.type === 'FORM-GROUP') {
    //     let currentGroupCodePrimoLivello: string = field.groupCode;
    //     if (field.fieldGroups[0] != null) {
    //       let currentData: string = field.fieldGroups[0].fields[0].data;

    //       if(currentData != null) {
    //         each(this.formConfig.fields[0].fieldGroups, (one: SdkFormFieldGroupConfiguration) => {
    //           if (one.code !== currentGroupCodePrimoLivello) {
    //             one.fields.forEach((second: SdkFormBuilderField) => {
    //               if (second.type === 'FORM-GROUP' && second.fieldGroups != null) {
    //                 let toUpdateGroupCodeSecondoLivello: string = second.fieldGroups[0].code;
    //                 console.log('toUpdateGroupCodeSecondoLivello >>>', toUpdateGroupCodeSecondoLivello);
    //                 this.dataSubject2.next({
    //                   code: 'test-val3',
    //                   groupCode: toUpdateGroupCodeSecondoLivello,
    //                   data: currentData + 'aggiornato'
    //                 });
    //               }
    //             });
    //           }
    //         });
    //       }
    //     }
    //   }
    // }
  }

  public manageFileSelect(event: any): void {
    let target: HTMLInputElement = event.target;
    if (isObject(target)) {
      let files: FileList = target.files;
      let file: File = files.item(0);

      if (file != null) {
        let reader = new FileReader();
        reader.onload = this.handleReaderLoaded;
        reader.readAsBinaryString(file);
      }
    }
  }

  private handleReaderLoaded = (readerEvt: any) => {
    let content = readerEvt.target.result;
    let parsed = JSON.parse(content);
    if (parsed != null) {
      let formConfig: SdkFormBuilderConfiguration = {
        fields: parsed.fields
      };
      formConfig = this.formBuilderUtilsService.populateForm(formConfig, false, this.customPopulateFunction);
      this.formBuilderConfigSubject.next(formConfig);
    }
  }

  private customPopulateFunction = (field: SdkFormBuilderField, restObject?: any, dynamicField?: boolean): CustomParamsFunctionResponse => {
    let mapping: boolean = true;
    if (field.type === 'COMBOBOX') {
      let comboItem: SdkComboBoxItem = {
        key: '2',
        value: '2',
        disabled: false
      }
      set(field, 'data', comboItem);
      mapping = false;
    }
    return {
      mapping,
      field
    };
  }
}
