import { Injectable, Injector } from '@angular/core';
import { AbstractValidationUtilsService } from '@maggioli/app-commons';
import { SdkFormBuilderField, SdkMessagePanelConfig } from '@maggioli/sdk-controls';
import { isObject, isUndefined } from 'lodash-es';

@Injectable({ providedIn: 'root' })
export class NuovaOperaIncompiutaValidationUtilsService extends AbstractValidationUtilsService {

    constructor(inj: Injector) {
        super(inj);
    }

    protected elaborateValidOne(field: SdkFormBuilderField, valids: Array<boolean>, messagesPanel: HTMLElement, messagesConfig: Array<SdkMessagePanelConfig>): Array<boolean> {
        if (isObject(field)) {
            if (!isUndefined(field.valid)) {
                valids.push(field.valid);
            } else {
                let valid: boolean = this.executeValidators(field, messagesConfig);
                valids.push(valid);
            }
        }
        return valids;
    }

}