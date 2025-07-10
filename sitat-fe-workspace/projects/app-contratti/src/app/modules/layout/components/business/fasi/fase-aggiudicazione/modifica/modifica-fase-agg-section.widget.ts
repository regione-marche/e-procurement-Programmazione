import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, findIndex, get, isEmpty, isObject, isString, remove, sum, toNumber, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseAggiudicazioneEntry } from '../../../../../../models/fasi/fase-aggiudicazione.model';
import { FaseAggiudicazioneService } from '../../../../../../services/fasi/fase-aggiudicazione.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';


@Component({
    templateUrl: `modifica-fase-agg-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseAggiudicazioneSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `modifica-fase-aggiudicazione-section`;

    private fase: FaseAggiudicazioneEntry;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        // set update state
        this.setUpdateState(true);
        this.loadListaTabellati = true;
        this.updateFase = true;

        super.onInit();

        if (this.riaggiudicazione === true) {
            let fields: Array<SdkFormBuilderField> = this.config.body.fields;
            remove(fields, (one: SdkFormBuilderField) => {
                return one.code === 'dati-economici-data' || one.code === 'dati-procedurali-appalto-data' || one.code === 'inviti-offerte-soglia-data';
            });
            this.config.body.fields = fields;
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get faseAggiudicazioneService(): FaseAggiudicazioneService { return this.injectable(FaseAggiudicazioneService) }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        if (field.code === 'importo-lavori' || field.code === 'importo-servizi' || field.code === 'importo-forniture') {
            this.calcolaImportoSubtotale(field);
        } else if (field.code === 'importo-subtotale' || field.code === 'importo-attuazione-sicurezza' || field.code === 'importo-progettazione' || field.code === 'importo-non-assog') {
            this.calcolaImportoComplAppalto(field);
        } else if (field.code === 'iva') {
            this.calcolaImportoIva(field);
        } else if (field.code === 'importo-iva' || field.code === 'altre-somme') {
            this.calcolaImportoDisposizione(field);
        } else if (field.code === 'importo-compl-appalto') {
            this.calcolaImportoIva(field);
            this.calcolaImportoComplIntervento(field);
        } else if (field.code === 'importo-disposizione') {
            this.calcolaImportoComplIntervento(field);
        } else if (field.code === 'importo-opzioni') {
            this.calcolaImportoComplIntervento(field);
        } else if (field.code === 'importo-ripetizioni') {
            this.calcolaImportoComplIntervento(field);
        }
    }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseAggiudicazioneEntry, dynamicField?: boolean) => {
        let mapping: boolean = true;

        let keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if(this.gara?.pcp){
            if(field.code === 'iva' ||
               field.code === 'importo-iva' || 
               field.code === 'altre-somme' || 
               field.code === 'offerta-massimo' ||
               field.code === 'offerta-minima' ||
               field.code === 'importo-opzioni' ||
               field.code === 'importo-ripetizioni') 
            {
                field.visible = false;
                mapping = false;
            }
        }
        
        if (
            (
                field.code === 'data-manif-interesse' ||
                field.code === 'data-scadenza-richiesta-invito' ||
                field.code === 'data-invito' ||
                field.code === 'data-scadenza-pres-offerta' ||
                field.code === 'data-verb-aggiudicazione' ||
                field.code === 'data-atto'
            ) && field.type === 'TEXT'
        ) {
            let value = get(restObject, field.mappingInput);
            if (value != null) {
                field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                key = field.data;
                mapping = false;
            }
        }

        if (field.code === 'data-scadenza-richiesta-invito' || field.code === 'num-imprese-richiedenti') {
            field.visible = (this.lotto.sceltaContraente === 2 || this.lotto.sceltaContraente === 9);
        } else if (field.code === 'data-invito' || field.code === 'num-imprese-invitate') {
            field.visible = this.lotto.sceltaContraente !== 1;
        } else if (field.code === 'requisiti-sett-spec') {
            field.visible = this.gara.tipoSettore === 'S';
        } else if (field.code === 'id-modo-indizione') {
            field.visible = (this.gara.versioneSimog === 1 && this.gara.tipoSettore === 'S');
        } else if (field.code === 'modalita-riaggiudicazione' && this.riaggiudicazione === true) {
            field.type = 'COMBOBOX';
        } else if (field.code === 'flag-relazione-unica') {
            field.visible = this.gara.versioneSimog >= 6;
        }

        if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
            [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
        }

        if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
            field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
        }

        return {
            mapping,
            field
        };
    }

    protected tabellati(): Array<string> {
        return Constants.FASE_AGGIUDICAZIONE_TABELLATI;
    }

    protected loadDettaglio = (): Observable<FaseAggiudicazioneEntry> => {
        let factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseAggiudicazioneEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseAggiudicazioneEntry> {
        return () => {
            return this.faseAggiudicazioneService.getFase(codGara, codLotto, progressivo);
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
     * Metodo per calcolare l'importo subtotale tra i campi "Importo Lavori", "Importo Servizi" e "Importo Forniture"
     * @param field Il campo attualmente modificato
     */
    private calcolaImportoSubtotale(field: SdkFormBuilderField): void {

        let importoLavoriValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-lavori'));
        let importoServiziValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-servizi'));
        let importoFornitureValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-forniture'));

        let somma: number = sum([importoLavoriValue, importoServiziValue, importoFornitureValue]);

        this.formBuilderDataSubject.next({
            code: 'importo-subtotale',
            data: toString(somma)
        });
    }

    /**
     * Metodo per calcolare l'importo compl appalto
     * @param field Il campo attualmente modificato
     */
    private calcolaImportoComplAppalto(field: SdkFormBuilderField): void {

        let importoSubtotaleValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-subtotale'));
        let importoAttuazioneSicurezzaValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-attuazione-sicurezza'));
        let importoProgettazioneValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-progettazione'));
        let importoNonAssogValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-non-assog'));

        let somma: number = sum([importoSubtotaleValue, importoAttuazioneSicurezzaValue, importoProgettazioneValue, importoNonAssogValue]);

        this.formBuilderDataSubject.next({
            code: 'importo-compl-appalto',
            data: toString(somma)
        });
    }

    /**
     * Metodo per calcolare l'importo IVA
     * @param field Il campo attualmente modificato
     */
    private calcolaImportoIva(field: SdkFormBuilderField): void {

        let importoComplAppaltoValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-compl-appalto'));
        let iva: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'iva'));

        let result: number = +((importoComplAppaltoValue * iva) / 100).toFixed(2);

        this.formBuilderDataSubject.next({
            code: 'importo-iva',
            data: result
        });
    }

    /**
     * Metodo per calcolare l'importo disposizione
     * @param field Il campo attualmente modificato
     */
    private calcolaImportoDisposizione(field: SdkFormBuilderField): void {

        let importoIvaValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-iva'));
        let altreSommeValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'altre-somme'));

        let somma: number = sum([importoIvaValue, altreSommeValue]);

        this.formBuilderDataSubject.next({
            code: 'importo-disposizione',
            data: toString(somma)
        });
    }

    /**
     * Metodo per calcolare l'importo compl intervento
     * @param field Il campo attualmente modificato
     */
    private calcolaImportoComplIntervento(field: SdkFormBuilderField): void {

        let importoComplAppaltoValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-compl-appalto'));
        let importoDisposizioneValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-disposizione'));
        let importoOpzioniValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-opzioni'));
        let importoRipetizioniValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-ripetizioni'));

        let somma: number = sum([importoComplAppaltoValue, importoDisposizioneValue, importoOpzioniValue, importoRipetizioniValue]);

        this.formBuilderDataSubject.next({
            code: 'importo-compl-intervento',
            data: toString(somma)
        });
    }

    // #endregion
}
