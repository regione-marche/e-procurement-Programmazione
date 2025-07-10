import { Type } from '@angular/core';
import { IDictionary, SdkValidatorServiceFunction } from '@maggioli/sdk-commons';

import { MandatoryValidator } from './modules/validators/mandatory.validator';
import { RegexpValidator } from './modules/validators/regexp.validator';

export function validators(): IDictionary<Type<SdkValidatorServiceFunction>> {
    return {
        MANDATORY: MandatoryValidator,
        REGEXP: RegexpValidator
    };
}
