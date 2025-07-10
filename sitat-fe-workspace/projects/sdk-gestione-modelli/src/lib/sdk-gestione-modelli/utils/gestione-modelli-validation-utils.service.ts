import { Injectable, Injector } from "@angular/core";
import {
	SdkFormBuilderField,
	SdkMessagePanelConfig,
} from "@maggioli/sdk-controls";
import { isEmpty, isObject, isUndefined, startsWith } from "lodash-es";

import { AbstractValidationUtilsService } from "./abstract-validation-utils.service";

@Injectable({ providedIn: "root" })
export class GestioneModelliValidationUtilsService extends AbstractValidationUtilsService {
	constructor(inj: Injector) {
		super(inj);
	}

	protected elaborateValidOne(
		field: SdkFormBuilderField,
		valids: Array<boolean>,
		messagesPanel: HTMLElement,
		messagesConfig: Array<SdkMessagePanelConfig>
	): Array<boolean> {
		if (isObject(field)) {
			if (field.code === "upload-documento") {
				valids.push(true);
			} else if (field.type === "DOCUMENT") {

                const isValid = 
                       ( field.mandatory && !isEmpty(field.fileName) && !isEmpty(field.file) )
                    || ( !field.mandatory && isEmpty(field.fileName) && isEmpty(field.file) )
					|| (!field.mandatory && !isEmpty(field.fileName) && !isEmpty(field.file));
                       
				valids.push(isValid);
				if (!isValid) {
                    const errorMessage = [
                        { condition: field.mandatory && isEmpty(field.fileName), error: "VALIDATORS.DOCUMENTS-MANDATORY" },
                        { condition: field.mandatory && !isEmpty(field.fileName) && isEmpty(field.file), error: "VALIDATORS.DOCUMENTS-FILE-VUOTO" },
                        { condition: !field.mandatory && !isEmpty(field.fileName) && isEmpty(field.file), error: "VALIDATORS.DOCUMENTS-FILE-VUOTO" },
                    ].find(tc => tc.condition)?.error || "";
					this.messagesPanelUtilsService.fillError(messagesConfig, [
						{
							message: errorMessage,
							messageParams:{name: field.fileName}
						},
					]);
				}
			} else {
				if (!isUndefined(field.valid)) {
					valids.push(field.valid);
				} else {
					if (field.visible == null || field.visible == true) {
						let valid: boolean = this.executeValidators(field, messagesConfig);
						valids.push(valid);
					}
				}
			}
		}
		return valids;
	}
}
