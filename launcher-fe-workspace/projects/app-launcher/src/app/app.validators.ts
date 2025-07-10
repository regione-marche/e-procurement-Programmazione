import { Type } from '@angular/core';
import { IDictionary, SdkValidatorServiceFunction } from '@maggioli/sdk-commons';

import { ComboMandatoryValidator } from './modules/validators/combo-mandatory.validator';
import { MandatoryValidator } from './modules/validators/mandatory.validator';
import { RegexpValidator } from './modules/validators/regexp.validator';

export function validators(): IDictionary<Type<SdkValidatorServiceFunction>> {
    return {
        MANDATORY: MandatoryValidator,
        REGEXP: RegexpValidator,
        COMBO_MANDATORY: ComboMandatoryValidator
    };
}
