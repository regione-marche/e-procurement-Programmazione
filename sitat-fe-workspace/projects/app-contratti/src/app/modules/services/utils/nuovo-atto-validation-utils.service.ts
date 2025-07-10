import { Injectable, Injector } from '@angular/core';
import { AbstractValidationUtilsService } from '@maggioli/app-commons';
import { SdkFormBuilderField, SdkMessagePanelConfig } from '@maggioli/sdk-controls';
import { isEmpty, isObject, isUndefined, startsWith } from 'lodash-es';

@Injectable({ providedIn: 'root' })
export class NuovoAttoValidationUtilsService extends AbstractValidationUtilsService {

    constructor(inj: Injector) {
        super(inj);
    }

    protected elaborateValidOne(field: SdkFormBuilderField, valids: Array<boolean>, messagesPanel: HTMLElement, messagesConfig: Array<SdkMessagePanelConfig>): Array<boolean> {
        if (isObject(field)) {
            if (field.code === 'doucment-field-url' || field.code === 'doucment-field') {
                valids.push(true);
            } else if (field.type === 'DOCUMENT') {
                let validMandatory: boolean = 
                (isEmpty(field.title) && isEmpty(field.url) && isEmpty(field.file)) || // valid if empty (only on update)
                (!isEmpty(field.title) && // or check normal condition
                    (
                        (field.fileSwitch === true && !isEmpty(field.file)) ||
                        ((isUndefined(field.fileSwitch) || field.fileSwitch === false) && !isEmpty(field.url))
                    )
                );
                let validUrl: boolean = 
                    (isEmpty(field.title) && isEmpty(field.url) && isEmpty(field.file)) ||
                    (field.fileSwitch === true ||
                        (
                            (isUndefined(field.fileSwitch) || field.fileSwitch === false) && (startsWith(field.url, 'http://') ||
                                startsWith(field.url, 'https://'))
                        )
                    )
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
                if(field.code === 'tipo-collaudo'){
                    let valid: boolean = this.executeValidators(field, messagesConfig);
                        valids.push(valid);
                }
                else if (!isUndefined(field.valid)) {
                    valids.push(field.valid);
                } else {
                    if (field.visible !== false) {
                        let valid: boolean = this.executeValidators(field, messagesConfig);
                        valids.push(valid);
                    }
                }
            }
        }
        return valids;
    }

}