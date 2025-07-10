import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { ResponseResult } from '@maggioli/app-commons';
import { SdkButtonGroupOutput, SdkComboBoxItem, SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, findIndex, get, isEmpty, isObject, isString, sum, toNumber, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseAdesioneAccordoQuadroEntry } from '../../../../../../models/fasi/fase-adesione-accordo-quadro.model';
import { InizFaseAdesioneAccordoQuadroEntry } from '../../../../../../models/fasi/fase-iniziale.model';
import { FaseAdesioneAccordoQuadroService } from '../../../../../../services/fasi/fase-adesione-accordo-quadro.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';
import { IDictionary } from '@maggioli/sdk-commons';



@Component({
    templateUrl: `nuova-fase-adesione-accordo-quadro-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseAdesioneAccordoQuadroSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `nuova-fase-adesione-accordo-quadro-section`;

    private iniz: InizFaseAdesioneAccordoQuadroEntry;
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
        const key: string = dynamicField === true ? field.data : toString(keyAny);

        // TODO

        if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
            [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
        }

        if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
            field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
        }
        if (isObject(this.iniz)) {
            if (field.code === 'perc-ribasso-agg') {
                field.data = this.iniz.percRibassoAgg;
                mapping = false;
            }
            if (field.code === 'perc-off-aumento') {
                field.data = this.iniz.percOffAumento;
                mapping = false;
            }
            if (field.code === 'importo-aggiudicazione') {
                field.data = this.iniz.importoAgg;
                mapping = false;
            }
            if (field.code === 'data-verb-aggiudicazione' && this.iniz.dataVerbaleAgg != null) {
                field.data = this.dateHelper.format(new Date(this.iniz.dataVerbaleAgg), this.config.locale.dateFormat);
                mapping = false;
            }
            if (field.code === 'flag-rich-subappalto') {

                let key = this.iniz.flagSubappalto;
                let value = key === '1' ? 'SI' : 'NO';
                let comboItem: SdkComboBoxItem = {
                    key: key,
                    value: value
                }
                field.data = comboItem;
                mapping = false;
            }
        }
        return {
            mapping,
            field
        };
    }

    protected tabellati(): Array<string> {
        return Constants.FASE_ADESIONE_ACCORDO_QUADRO_TABELLATI;
    }

    protected loadDettaglio = (): Observable<InizFaseAdesioneAccordoQuadroEntry> => {
        return this.getDettaglioObservable();

    }

    private getDettaglioObservable(): Observable<InizFaseAdesioneAccordoQuadroEntry> {
        let factory = this.datiInizializzazioneFaseFactory(this.codGara, this.codLotto);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: InizFaseAdesioneAccordoQuadroEntry) => {
                    this.iniz = result;
                    return result
                })
            );
    }

    private datiInizializzazioneFaseFactory(codGara: string, codLotto: string): () => Observable<InizFaseAdesioneAccordoQuadroEntry> {
        return () => {
            return this.faseAdesioneAccordoQuadroService.getDatiInizializzazione(codGara, codLotto)
                .pipe(map((result: ResponseResult<InizFaseAdesioneAccordoQuadroEntry>) => {
                    if (result.errorData != null) {
                        this.sdkMessagePanelService.showWarning(this.messagesPanel, [
                            {
                                message: `ERRORS.${result.errorData}`
                            }
                        ]);
                    }
                    if(result.data != null && result.data.inibita != null){
                        if(result.data != null && result.data.inibita != null){
                            if(result.data.inibita === 'W'){
                                this.sdkMessagePanelService.showWarning(this.messagesPanel, [
                                    {
                                        message: 'ERRORS.NO-ATTO-ESITO-WARNING'
                                    }
                                ]);
                            }
                            else if(result.data.inibita === 'S'){
                                this.sdkMessagePanelService.showError(this.messagesPanel, [
                                    {
                                        message: 'ERRORS.NO-ATTO-ESITO'
                                    }
                                ]);
                                this.gara.readOnly=true;                        }
                        }
                    }

                    return result.data != null ? result.data : {};
                }));
        }
    }

    protected back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        this.provider.run(button.provider, data).subscribe();
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

    // #region Getters

    private get faseAdesioneAccordoQuadroService(): FaseAdesioneAccordoQuadroService { return this.injectable(FaseAdesioneAccordoQuadroService) }

    // #endregion
}
