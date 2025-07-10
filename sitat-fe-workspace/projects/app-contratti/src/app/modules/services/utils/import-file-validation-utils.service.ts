import { Injectable, Injector } from '@angular/core';
import { AbstractValidationUtilsService } from '@maggioli/app-commons';
import { SdkFormBuilderField, SdkMessagePanelConfig } from '@maggioli/sdk-controls';
import { isObject, isUndefined } from 'lodash-es';

@Injectable({ providedIn: 'root' })
export class ImportFileValidationUtilsService extends AbstractValidationUtilsService {

    constructor(inj: Injector) {
        super(inj);
    }

    protected elaborateValidOne(field: SdkFormBuilderField, valids: Array<boolean>, messagesPanel: HTMLElement, messagesConfig: Array<SdkMessagePanelConfig>): Array<boolean> {
        if (isObject(field)) {
            if (field.type === 'DOCUMENT') {
                //let valid: boolean = field.file != null;
                //valids.push(valid);
                //if (valid === false) {
                //    this.messagesPanelUtilsService.fillError(messagesConfig, [
                //        {
                //            message: `VALIDATORS.DOCUMENTS-MANDATORY`
                //        }
                //    ]);
                //}
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