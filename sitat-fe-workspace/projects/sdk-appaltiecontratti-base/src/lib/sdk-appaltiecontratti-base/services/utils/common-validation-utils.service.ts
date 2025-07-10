import { Injectable, Injector } from '@angular/core';
import { SdkFormBuilderField, SdkMessagePanelConfig } from '@maggioli/sdk-controls';
import { isEmpty, isObject, isUndefined, startsWith } from 'lodash-es';

import { AbstractValidationUtilsService } from '../abstract/abstract-validation-utils.service';

@Injectable({ providedIn: 'root' })
export class CommonValidationUtilsService extends AbstractValidationUtilsService {

    constructor(inj: Injector) {
        super(inj);
    }

    protected elaborateValidOne(field: SdkFormBuilderField, valids: Array<boolean>, messagesPanel: HTMLElement, messagesConfig: Array<SdkMessagePanelConfig>): Array<boolean> {
        if (isObject(field)) {
            if (field.type === 'DOCUMENT') {
                if (!isUndefined(field.mandatoryLabel) && field.mandatoryLabel != '') {
                    
                    let validMandatory: boolean = 
                        (
                            (field.onlyFile === true && !isEmpty(field.file)) ||
                            (field.onlyFileWithTitle === true && !isEmpty(field.file) && !isEmpty(field.title)) ||
                            ((isUndefined(field.fileSwitch) || field.fileSwitch === false) && !isEmpty(field.url))
                        );
                    //let validUrl: boolean = field.fileSwitch === true ||
                    //    ((isUndefined(field.fileSwitch) || field.fileSwitch === false) && (startsWith(field.url, 'http://') || startsWith(field.url, 'https://')))
                    //let valid: boolean = validMandatory && validUrl;
                    let valid: boolean = validMandatory;
                    valids.push(valid);
                    if (validMandatory === false) {
                        this.messagesPanelUtilsService.fillError(messagesConfig, [
                            {
                                message: field.mandatoryLabel
                            }
                        ]);
                    } 
                    // else if (validUrl === false) {
                    //     this.messagesPanelUtilsService.fillError(messagesConfig, [
                    //         {
                    //             message: `VALIDATORS.DOCUMENTS-URL-ERRATO`
                    //         }
                    //     ]);
                    // }
                }
            } else {
                if (!isUndefined(field.valid)) {
                    valids.push(field.valid);
                } else {
                    if (field.visible !== false) {
                        let valid: boolean = this.executeValidators(field, messagesConfig);
                        valids.push(valid);
                    } else {
                        valids.push(true);
                    }
                }
            }
        }
        return valids;
    }

}