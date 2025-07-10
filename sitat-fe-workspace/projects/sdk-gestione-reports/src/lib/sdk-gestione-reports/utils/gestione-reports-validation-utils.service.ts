import { Injectable, Injector } from '@angular/core';
import { SdkFormBuilderField, SdkMessagePanelConfig } from '@maggioli/sdk-controls';
import { isEmpty, isObject, isUndefined, startsWith } from 'lodash-es';

import { SdkGestioneReportConstants } from '../sdk-gestione-report.constants';
import { AbstractValidationUtilsService } from './abstract-validation-utils.service';

@Injectable()
export class GestioneReportsValidationUtilsService extends AbstractValidationUtilsService {

    constructor(inj: Injector) {
        super(inj);
    }

    protected elaborateValidOne(field: SdkFormBuilderField, valids: Array<boolean>, messagesPanel: HTMLElement, messagesConfig: Array<SdkMessagePanelConfig>): Array<boolean> {
        if (isObject(field)) {
            if (field.code === 'upload-documento') {
                valids.push(true);
            } else if (field.type === 'DOCUMENT') {
                let validMandatory: boolean = !isEmpty(field.title) &&
                    (
                        (field.fileSwitch === true && !isEmpty(field.file)) ||
                        ((isUndefined(field.fileSwitch) || field.fileSwitch === false) && !isEmpty(field.url))
                    );
                let validUrl: boolean = field.fileSwitch === true ||
                    ((isUndefined(field.fileSwitch) || field.fileSwitch === false) && (startsWith(field.url, 'http://') || startsWith(field.url, 'https://')))
                let valid: boolean = validMandatory && validUrl;
                valids.push(valid);
                if (validMandatory === false) {
                    this.messagesPanelUtilsService.fillError(messagesConfig, [
                        {
                            message: `VALIDATORS.DOCUMENTS-MANDATORY`
                        }
                    ]);
                } else if (validUrl === false) {
                    this.messagesPanelUtilsService.fillError(messagesConfig, [
                        {
                            message: `VALIDATORS.DOCUMENTS-URL-ERRATO`
                        }
                    ]);
                }
            } else {
                if (!isUndefined(field.valid)) {
                    valids.push(field.valid);
                } else {
                    if (field.visible == null || field.visible == true) {
                        let valid: boolean = this.executeValidators(field, messagesConfig);
                        valids.push(valid);
                    }
                }
            }
        }
        return valids;
    }

    //#region Public

    public checkModifyingOperations(input: string): boolean {
        const modifyingOperationsKeys = SdkGestioneReportConstants.MODIFYING_OPERATIONS;
        const paroleRegexp = new RegExp(`\\b(${modifyingOperationsKeys.join('|')})\\b`, 'i'); // '\b' indica il limite di parola
    
        return !paroleRegexp.test(input);
    }

    //#endregion

}