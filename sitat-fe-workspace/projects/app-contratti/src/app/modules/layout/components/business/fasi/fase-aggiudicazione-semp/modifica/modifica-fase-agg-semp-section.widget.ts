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
import { FaseAggiudicazioneSempEntry } from '../../../../../../models/fasi/fase-aggiudicazione-semp.model';
import { FaseAggiudicazioneSempService } from '../../../../../../services/fasi/fase-aggiudicazione-semp.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';


@Component({
    templateUrl: `modifica-fase-agg-semp-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseAggiudicazioneSempSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `modifica-fase-aggiudicazione-semp-section`;

    private fase: FaseAggiudicazioneSempEntry;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

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

    private get faseAggiudicazioneSempService(): FaseAggiudicazioneSempService { return this.injectable(FaseAggiudicazioneSempService) }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        if (field.code === 'importo-subtotale' || field.code === 'importo-attuazione-sicurezza') {
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
        }
    }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseAggiudicazioneSempEntry, dynamicField?: boolean) => {
        let mapping: boolean = true;

        let keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if (
            (
                field.code === 'data-verb-aggiudicazione' ||
                field.code === 'data-atto' ||
                field.code === 'data-stipula'
            ) && field.type === 'TEXT'
        ) {
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

        if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
            field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
        }

        return {
            mapping,
            field
        };
    }

    protected tabellati(): Array<string> {
        return Constants.FASE_AGGIUDICAZIONE_SEMP_TABELLATI;
    }

    protected loadDettaglio = (): Observable<FaseAggiudicazioneSempEntry> => {
        let factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseAggiudicazioneSempEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseAggiudicazioneSempEntry> {
        return () => {
            return this.faseAggiudicazioneSempService.getFase(codGara, codLotto, progressivo);
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
     * Metodo per calcolare l'importo compl appalto
     * @param field Il campo attualmente modificato
     */
    private calcolaImportoComplAppalto(field: SdkFormBuilderField): void {

        let importoSubtotaleValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-subtotale'));
        let importoAttuazioneSicurezzaValue: number = toNumber(this.getCurrentNumberValue(field, 'dati-economici-data', 'importo-attuazione-sicurezza'));

        let somma: number = sum([importoSubtotaleValue, importoAttuazioneSicurezzaValue]);

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

        let somma: number = sum([importoComplAppaltoValue, importoDisposizioneValue]);

        this.formBuilderDataSubject.next({
            code: 'importo-compl-intervento',
            data: toString(somma)
        });
    }

    // #endregion
}
