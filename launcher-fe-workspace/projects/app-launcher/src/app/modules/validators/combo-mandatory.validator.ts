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
import { each, has, isEmpty, isObject, some } from 'lodash-es';

@Injectable({ providedIn: 'root' })
export class ComboMandatoryValidator extends SdkBaseService implements SdkValidatorServiceFunction {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(messages?: Array<SdkValidationMessage>): SdkValidatorFunction<any> {
        return (input: SdkValidatorInput<any>): SdkValidatorOutput => {
            let valid = input.data != null &&
                isObject(input.data) && has(input.data, 'key');
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
            one.text = this.translateService.instant(one.text);
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