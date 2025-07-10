import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, findIndex, get, isEmpty, isObject, isString, sum, toNumber, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseAdesioneAccordoQuadroEntry } from '../../../../../../models/fasi/fase-adesione-accordo-quadro.model';
import { FaseAdesioneAccordoQuadroService } from '../../../../../../services/fasi/fase-adesione-accordo-quadro.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';



@Component({
    templateUrl: `modifica-fase-adesione-accordo-quadro-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseAdesioneAccordoQuadroSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `modifica-fase-adesione-accordo-quadro-section`;


    private fase: FaseAdesioneAccordoQuadroEntry;


    // #endregion

    constructor(injector: Injector, cdr: ChangeDetectorRef) {
        super(injector, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        // set update state
        this.setUpdateState(true);
        this.loadListaTabellati = true;
        this.updateFase = true;

        super.onInit();
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get faseAdesioneAccordoQuadroService(): FaseAdesioneAccordoQuadroService {
        return this.injectable(FaseAdesioneAccordoQuadroService);
    }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        if (field.code === 'importo-lavori' || field.code === 'importo-servizi' || field.code === 'importo-forniture') {
            this.calcolaSum(field, 
                'dati-economici-adesione-data',
                ['importo-lavori', 'importo-servizi', 'importo-forniture'],
                ['importo-subtotale']);
        } else if (field.code === 'importo-subtotale' || field.code === 'importo-attuazione-sicurezza' || field.code === 'importo-progettazione' || field.code === 'importo-non-assog') { 
            this.calcolaSum(field,
                'dati-economici-adesione-data',
                ['importo-subtotale', 'importo-attuazione-sicurezza', 'importo-progettazione', 'importo-non-assog'],
                ['importo-compl-appalto', 'importo-compl-intervento']);
        }
    }

    // #endregion

    // #region Protected


    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseAdesioneAccordoQuadroEntry, dynamicField?: boolean) => {

        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if (field.code.startsWith('data-') && field.type === 'TEXT') {
            let value = get(restObject, field.mappingInput);
            if (value != null) {
                field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                key = field.data;
                mapping = false;
            }
        }

        if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
            [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
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
        return Constants.FASE_ADESIONE_ACCORDO_QUADRO_TABELLATI;
    }


    protected loadDettaglio = (): Observable<FaseAdesioneAccordoQuadroEntry> => {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseAdesioneAccordoQuadroEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseAdesioneAccordoQuadroEntry> {
        return () => {
            return this.faseAdesioneAccordoQuadroService.getFase(codGara, codLotto, progressivo);
        }
    }

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
        let currentValueField: SdkFormBuilderField;
        let currentValue: number;
        if (field.code === fieldCode) {
            currentValueField = field;
            currentValue = currentValueField.data ? currentValueField.data : 0;
        } else {
            currentValueField = this.getField(sectionCode, fieldCode);
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
        })
    }

    // #endregion
}
