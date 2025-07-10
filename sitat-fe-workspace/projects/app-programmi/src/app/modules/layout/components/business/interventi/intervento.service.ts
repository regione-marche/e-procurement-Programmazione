import { Injectable, Injector } from '@angular/core';
import { ProfiloConfiguration, ProtectionUtilsService } from '@maggioli/app-commons';
import { SdkBaseService } from '@maggioli/sdk-commons';
import {
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkTextboxMatrixCellConfig,
    SdkTextboxMatrixRowConfig,
} from '@maggioli/sdk-controls';
import { each, includes, isObject } from 'lodash-es';

@Injectable({ providedIn: 'root' })
export class InterventoService extends SdkBaseService {

    constructor(inj: Injector) {
        super(inj);
    }

    // #region Public

    public isRisorseDiCapitoloVisible(profiloConfiguration: ProfiloConfiguration, risorseTabProtectionCode: string): boolean {

        let visible: boolean = this.protectionUtilsService.isMenuTabVisible(risorseTabProtectionCode, profiloConfiguration);

        return visible;
    }

    public updateProtectionsForRisorseDiCapitolo(form: SdkFormBuilderConfiguration, profiloConfiguration: ProfiloConfiguration, risorseTabProtectionCode: string): SdkFormBuilderConfiguration {

        each(form.fields, (field: SdkFormBuilderField) => {
            if (field.type === 'FORM-SECTION') {
                field = this.elaborateSection(field, profiloConfiguration, risorseTabProtectionCode);
            } else if (field.type === 'FORM-GROUP') {
                field = this.elaborateGroup(field, profiloConfiguration, risorseTabProtectionCode);
            } else {
                field = this.elaborateOne(field, profiloConfiguration, risorseTabProtectionCode);
            }
        });

        return form;
    }

    // #endregion

    // #region Private

    private elaborateSection(field: SdkFormBuilderField, profiloConfiguration: ProfiloConfiguration, risorseTabProtectionCode: string): SdkFormBuilderField {
        each(field.fieldSections, (one: SdkFormBuilderField) => {
            if (one.type === 'FORM-SECTION') {
                one = this.elaborateSection(one, profiloConfiguration, risorseTabProtectionCode);
            } else if (one.type === 'FORM-GROUP') {
                one = this.elaborateGroup(one, profiloConfiguration, risorseTabProtectionCode);
            } else {
                one = this.elaborateOne(one, profiloConfiguration, risorseTabProtectionCode);
            }
        });
        return field;
    }

    private elaborateGroup(field: SdkFormBuilderField, profiloConfiguration: ProfiloConfiguration, risorseTabProtectionCode: string): SdkFormBuilderField {
        each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration, index: number) => {
            each(group.fields, (one: SdkFormBuilderField) => {
                if (one.type === 'FORM-SECTION') {
                    one = this.elaborateSection(one, profiloConfiguration, risorseTabProtectionCode);
                } else if (one.type === 'FORM-GROUP') {
                    one = this.elaborateGroup(one, profiloConfiguration, risorseTabProtectionCode);
                } else {
                    one = this.elaborateOne(one, profiloConfiguration, risorseTabProtectionCode);
                }
            });
            field.fieldGroups[index] = group;
        });
        return field;
    }

    private elaborateOne(field: SdkFormBuilderField, profiloConfiguration: ProfiloConfiguration, risorseTabProtectionCode: string): SdkFormBuilderField {
        if (isObject(field) && field.type === 'TEXTBOX-MATRIX') {
            let risorseDiCapitoloVisible: boolean = this.isRisorseDiCapitoloVisible(profiloConfiguration, risorseTabProtectionCode);
            if (risorseDiCapitoloVisible === true) {

                let cellNotToBeUpdated: Array<string> = [
                    'PR1TRI', 'PR2TRI', 'PR3TRI', 'PR9TRI'
                ];
                each(field.rows, (row: SdkTextboxMatrixRowConfig) => {
                    each(row.cells, (cell: SdkTextboxMatrixCellConfig) => {
                        if (includes(cellNotToBeUpdated, cell.code) === false && cell.type !== 'TEXT') {
                            cell.type = 'TEXT';
                        }
                    });
                });
            }
        }
        return field;
    }

    // #endregion

    // #region Getters

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    // #endregion
}