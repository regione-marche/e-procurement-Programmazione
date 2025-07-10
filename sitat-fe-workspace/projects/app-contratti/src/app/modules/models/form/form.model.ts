import { SdkFormFieldCommon, SdkTextOutput } from '@maggioli/sdk-controls';


export interface DetailFieldCommon extends SdkFormFieldCommon<void>, SdkTextOutput{

    /**
    * Valore del campo di input 
    */
    value? : any;
    /**
     * Proprietà mappata dall'oggetto di business
     */
    mapping?: string;
    /**
     * Determina se l'elemento è cliccabile o no
     */
    link?: boolean
}