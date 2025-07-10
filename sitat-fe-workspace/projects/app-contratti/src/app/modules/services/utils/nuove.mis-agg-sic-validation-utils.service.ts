import { Injectable, Injector } from '@angular/core';
import { AbstractValidationUtilsService } from '@maggioli/app-commons';
import { SdkFormBuilderField, SdkMessagePanelConfig } from '@maggioli/sdk-controls';
import { isEmpty, isObject, isUndefined } from 'lodash-es';

@Injectable({ providedIn: 'root' })
export class NuoveMisureAggiuntiveSicurezzaValidationUtilsService extends AbstractValidationUtilsService {

    constructor(inj: Injector) {
        super(inj);
    }

    protected elaborateValidOne(field: SdkFormBuilderField, valids: Array<boolean>, messagesPanel: HTMLElement, messagesConfig: Array<SdkMessagePanelConfig>): Array<boolean> {
        if (isObject(field)) {
            if (field.type === 'DOCUMENT') {
                let validMandatory: boolean = (isEmpty(field.title) && isEmpty(field.file)) || (!isEmpty(field.title) && !isEmpty(field.file));
                let valid: boolean = validMandatory;
                valids.push(valid);
                if (validMandatory === false) {
                    this.messagesPanelUtilsService.fillError(messagesConfig, [
                        {
                            message: `VALIDATORS.DOCUMENTS-FILE-MANDATORY`
                        }
                    ]);
                }
            } else {
                if (!isUndefined(field.valid)) {
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