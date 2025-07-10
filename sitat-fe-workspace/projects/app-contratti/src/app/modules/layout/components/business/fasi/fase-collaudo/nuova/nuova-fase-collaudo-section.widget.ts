import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, filter, find, findIndex, get, isEmpty, isObject, isString, sum, toNumber, toString } from 'lodash-es';
import { Observable } from 'rxjs';

import { Constants } from '../../../../../../../app.constants';
import { FaseCollaudoEntry } from '../../../../../../models/fasi/fase-collaudo.model';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';
import { GaraEntry } from '@maggioli/app-commons';



@Component({
    templateUrl: `nuova-fase-collaudo-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseCollaudoSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `nuova-fase-collaudo-section`;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        // set update state
        this.setUpdateState(true);
        this.loadListaTabellati = true;

        super.onInit();
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        if (field.code === 'imp-finale-lavori' || field.code === 'imp-final-servizi' || field.code === 'imp-finale-forniture') {
            this.calcolaSum(field, 
                'collaudo-conformita-prestazioni-data',
                ['imp-finale-lavori', 'imp-final-servizi', 'imp-finale-forniture'],
                ['imp-subtotale']);
        } else if (field.code === 'imp-subtotale' || field.code === 'imp-finale-secur' || field.code === 'imp-progettazione' || field.code === 'imp-non-assog') { 
            this.calcolaSum(field,
                'collaudo-conformita-prestazioni-data',
                ['imp-subtotale', 'imp-finale-secur', 'imp-progettazione','imp-non-assog'],
                ['imp-compl-appalto']);
        } else if (field.code === 'imp-compl-appalto' || 
            field.code === 'imp-disposizione' || 
            field.code === 'imp-finale-opzioni' || 
            field.code === 'imp-finale-ripetizioni'
        ) {
            this.calcolaSum(field,
                'collaudo-conformita-prestazioni-data',
                ['imp-compl-appalto', 'imp-disposizione', 'imp-finale-opzioni', 'imp-finale-ripetizioni'],
                ['imp-compl-intervento']);
        }
    }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseCollaudoEntry, dynamicField?: boolean) => {
        let mapping: boolean = true;
        
        const keyAny: any = get(restObject, field.mappingInput);
        const key: string = dynamicField === true ? field.data : toString(keyAny);
        
        // TODO implement
        
        if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
            [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
        }

        if(!this.gara.pcp){
            if(field.code == 'imp-non-assog'){
                field.visible = false;
                mapping = false;
            }
            if(field.code == 'imp-finale-opzioni'){
                field.visible = false;
                mapping = false;
            }
            if(field.code == 'imp-finale-ripetizioni'){
                field.visible = false;
                mapping = false;
            }
            if(field.code == 'num-riserve'){
                field.visible = false;
                mapping = false;
            }
            if(field.code == 'oneri-derivanti'){
                field.visible = false;
                mapping = false;
            }
        }
        if(this.gara.pcp){
            if(field.code == 'amm-imp-richiesto'){
                field.visible = false;
                mapping = false;
            } 
            
            /* rimossi temporameamente per err40
            issue anac https://github.com/anticorruzione/npa/issues/1192 */
            if(field.code == 'amm-num-dadef'){
                field.visible = false;
                mapping = false;
            } 

            if(field.code == 'amm-num-definitive'){
                field.visible = false;
                mapping = false;
            } 

            /** */
        }

        if(field.code === 'tipo-collaudo' && field.data === undefined){
            field.data = null;
        }

        if(field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
            field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
        }

        return {
            mapping,
            field
        };
    }

    protected tabellati(): Array<string> {
        return Constants.FASE_COLLAUDO_TABELLATI;
    }

    protected loadDettaglio = (): Observable<any> => {
        return undefined;
    }

    protected elaborateGara = (result: GaraEntry) => {
        this.gara = result;
        if(this.gara.pcp){
            this.config.body.fields = filter(this.config.body.fields, (one) => one.code !== 'ulteriori-riserve-in-via-arbitrale-data' 
            && one.code !== 'ulteriori-riserve-in-via-giudiziale-data'
            && one.code !== 'ulteriori-riserve-in-via-transattiva-data');        
            
            find(this.config.body.fields, (one: SdkFormBuilderField) => one.code === 'ulteriori-riserve-in-via-amministrativa-data' ? one.label='FASE-COLLAUDO.ULTERIORI-RISERVE-IN-VIA-AMMINISTRATIVA.ULTERIORI-RISERVE-IN-VIA-AMMINISTRATIVA-PCP' : '');            
        }
    }

    // #endregion

    // #region Private

    private getField(sectionCode: string, fieldCode: string): SdkFormBuilderField {
        if (sectionCode && fieldCode) {
            let sectionIndex: number = findIndex(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === sectionCode);
            if (sectionIndex > -1) {
                let fieldIndex: number = findIndex(this.formBuilderConfig.fields[sectionIndex].fieldSections, (one: SdkFormBuilderField) => one.code === fieldCode);
                return fieldIndex > -1 ? this.formBuilderConfig.fields[sectionIndex].fieldSections[fieldIndex] : undefined;
            }
        }
    }

    private getCurrentNumberValue(field: SdkFormBuilderField, sectionCode: string, fieldCode: string): any {
        let currentValue: number;
        if (field.code === fieldCode) {
            currentValue = field.data ? field.data : 0;
        } else {
            const currentValueField = this.getField(sectionCode, fieldCode);
            currentValue = isObject(currentValueField) && currentValueField.data ? currentValueField.data : 0;
        }
        return currentValue;
    }

    /**
     * Metodo per i campi importo-subtotale, importo-compl-appalto e importo-compl-intervento
     * @param field Il campo attualmente modificato
     * @param sectionCode Il nome della sezione che racchiude i campi
     * @param inputFields La lista dei campi da considerare per il calcolo
     * @param outputFields La lista dei campi da aggiornare a seguito del calcolo
     */
    private calcolaSum(field: SdkFormBuilderField, sectionCode: string, inputFields: Array<string>, outputFields: Array<string>): void {
        const imports: Array<number> = inputFields.map((inField: string) => toNumber(this.getCurrentNumberValue(field, sectionCode, inField)));
        const somma: number = sum(imports);
        outputFields.forEach((fieldName: string) => {
            this.formBuilderDataSubject.next({
                code: fieldName,
                data: toString(somma)
            });
        });
    }

    
    // #endregion
}
