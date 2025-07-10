import { Injectable, Injector } from '@angular/core';
import {
    SdkBaseService,
    SdkValidationMessage,
    SdkValidatorConfig,
    SdkValidatorFunction,
    SdkValidatorInput,
    SdkValidatorOutput,
    SdkValidatorServiceFunction,
} from '@maggioli/sdk-commons';
import { TranslateService } from '@ngx-translate/core';
import { each, isEmpty, padStart } from 'lodash-es';

@Injectable({ providedIn: 'root' })
export class CigValidator extends SdkBaseService implements SdkValidatorServiceFunction {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(messages: Array<SdkValidationMessage>): SdkValidatorFunction<any> {
        return (input: SdkValidatorInput<any>): SdkValidatorOutput => {
            const config: SdkValidatorConfig = input.config;
            const data: string = input.data;
            const valid: boolean = isEmpty(data) || this.isCigValido(data) || this.isSmartCigValido(data);
            return {
                valid,
                messages: this.parseMessages(messages)
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

    private isCigValido(codiceCIG: string): boolean {
        // TEMPORANEMANETE DISATTIVATO CONTROLLO PER CAMBIO FORMATO CIG
        return true;
    }

    private isSmartCigValido(codiceSmartCIG: string): boolean {
        let result: boolean = false;

        if (!isEmpty(codiceSmartCIG)) {

            const codCig: string = codiceSmartCIG.trim().toUpperCase();
            if (codCig.length === 10) {
                if (codCig.startsWith('X') || codCig.startsWith('Z') || codCig.startsWith('Y')) {
                    try {
                        const strK: string = codCig.substring(1, 3);// Estraggo la firma
                        const nDecStrK: number = this.toDecimalNumber(strK); // trasformo in decimale
                        const strC4_10: string = codCig.substring(3, 10);
                        const nDecStrC4_10: number = this.toDecimalNumber(strC4_10); // trasformo in decimale
                        // Calcola Firma
                        const nDecStrK_chk: number = ((nDecStrC4_10 * 1 / 1) * 211 % 251);
                        if (nDecStrK == nDecStrK_chk) {
                            result = true;
                        }
                    } catch (ex) {
                        this.logger.error('Impossibile calcolare la firma');
                    }
                }
            }
        }
        return result;
    }

    private isAlphanumeric(value: string): boolean {
        let regExp: RegExp = new RegExp(/^[a-z0-9]+$/i);
        return regExp.test(value);
    }

    private toHexString(value: number): string {
        return value.toString(16);
    }

    private toDecimalNumber(value: string): number {
        return parseInt(value, 16);
    }

    private isNumber(value: string): boolean {
        let regExp: RegExp = new RegExp(/^[0-9]+$/i);
        return regExp.test(value);
    }

    // #region Getters

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion

}