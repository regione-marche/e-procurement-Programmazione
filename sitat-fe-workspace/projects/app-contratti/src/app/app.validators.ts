import { Type } from '@angular/core';
import { IDictionary, SdkValidatorServiceFunction } from '@maggioli/sdk-commons';

import { CigValidator } from './modules/validators/cig.validator';
import { MandatoryValidator } from './modules/validators/mandatory.validator';
import { RegexpValidator } from './modules/validators/regexp.validator';
import { SmartCigValidator } from './modules/validators/smartcig.validator';

export function validators(): IDictionary<Type<SdkValidatorServiceFunction>> {
    return {
        MANDATORY: MandatoryValidator,
        REGEXP: RegexpValidator,
        CIG: CigValidator,
        SMARTCIG: SmartCigValidator
    };
}
