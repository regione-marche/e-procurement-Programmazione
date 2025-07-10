import { Injectable, Injector } from '@angular/core';
import {
    SdkBaseService,
    SdkValidationMessage,
    SdkValidatorFunction,
    SdkValidatorInput,
    SdkValidatorOutput,
    SdkValidatorServiceFunction,
} from '@maggioli/sdk-commons';
import { SdkCheckboxItem } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { each, get, has, isArray, isEmpty, isObject, some } from 'lodash-es';

@Injectable({ providedIn: 'root' })
export class MandatoryValidator extends SdkBaseService implements SdkValidatorServiceFunction {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(messages?: Array<SdkValidationMessage>): SdkValidatorFunction<any> {
        return (input: SdkValidatorInput<any>): SdkValidatorOutput => {
            if (isObject(input.data) && !isArray(input.data) && isEmpty(input.data) && typeof get(input.data, 'getMonth') !== 'function') {
                delete input.data;
            }
            let valid = input.data != null &&
                (
                    (isObject(input.data) && !isArray(input.data) && !isEmpty(input.data) && !has(input.data, 'key')) ||
                    (!isObject(input.data) && !isArray(input.data) && !isEmpty(`${input.data}`.trim())) ||
                    (input.data != null && typeof input.data.getMonth === 'function' && !isNaN(input.data.valueOf())) ||
                    (isArray(input.data) && this.verifyCheckedCheckbox(input.data)) ||
                    (isObject(input.data) && has(input.data, 'key') && !isEmpty(get(input.data, 'key')))
                );
            let defaultMessages: Array<SdkValidationMessage> = [
                {
                    level: 'error',
                    text: this.translateService.instant('VALIDATORS.MANDATORY')
                }
            ];
            return {
                valid,
                messages: !isEmpty(messages) ? this.parseMessages(messages) : defaultMessages
            };
        }
    }

    private parseMessages(messages: Array<SdkValidationMessage>): Array<SdkValidationMessage> {
        if (isEmpty(messages)) {
            return new Array();
        }
        each(messages, (one: SdkValidationMessage) => {
            if (one.params != null) {
                one.text = this.translateService.instant(one.text, one.params);
            } else {
                one.text = this.translateService.instant(one.text);
            }
        });
        return messages;
    }

    private verifyCheckedCheckbox(data: Array<SdkCheckboxItem>): boolean {
        const checked: boolean = some(data, one => one.checked === true);
        return checked;
    }

    // #region Getters

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

}