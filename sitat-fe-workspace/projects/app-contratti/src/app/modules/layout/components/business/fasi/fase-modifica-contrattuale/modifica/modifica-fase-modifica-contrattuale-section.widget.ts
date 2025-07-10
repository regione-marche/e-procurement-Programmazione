import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkDocumentItem, SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, findIndex, get, isEmpty, isObject, isString, set, sum, toNumber, toString } from 'lodash-es';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseVarianteEntry } from '../../../../../../models/fasi/fase-modifica-contrattuale.model';
import { FaseModificaContrattualeService } from '../../../../../../services/fasi/fase-modifica-contrattuale.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';



@Component({
    templateUrl: `modifica-fase-modifica-contrattuale-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseModificaContrattualeSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `modifica-fase-modifica-contrattuale-section`;


    private fase: FaseVarianteEntry;


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

    private get faseModificaContrattualeService(): FaseModificaContrattualeService { return this.injectable(FaseModificaContrattualeService) }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        if (field.code === 'imp-ridet-lavori' || field.code === 'imp-ridet-servizi' || field.code === 'imp-ridet-fornit') {
            this.calcolaImportoSubtotale(field);
        } else if (field.code === 'imp-subtotale' || field.code === 'imp-sicurezza' || field.code === 'imp-progettazione' || field.code === 'imp-non-assog') {
            this.calcolaImportoComplAppalto(field);
        } else if (field.code === 'imp-compl-appalto' || field.code === 'imp-disposizione') {
            this.calcolaImportoComplIntervento(field);
        }
    }

    // #endregion

    // #region Protected


    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseVarianteEntry, dynamicField?: boolean) => {

        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if (
            field.code === 'data-verb-appr' ||
            field.code === 'data-atto-aggiuntivo'
        ) {
            let value = get(restObject, field.mappingInput);
            if (value != null && field.type === 'TEXT') {
                field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                key = field.data;
                mapping = false;
            }
        }

        if(this.gara.pcp){
            if(this.gara.tipoApp === 3 ||
                this.gara.tipoApp === 4 ||
                this.gara.tipoApp === 20 ||
                this.gara.tipoApp === 21){
                if(field.code === 'imp-progettazione' || field.code === 'numero-giorni-proroga'){
                    field.visible = false;
                    mapping = false;
                }                              
            } else{
                if(field.code === 'imp-finpa' || field.code === 'entr-utenza' || field.code === 'intr-attivo' || field.code === 'imp-opzioni'){
                    field.visible = false;
                    mapping = false;
                }
            }

            if(this.lotto.sottoSoglia){
                if(field.code === 'eform'){
                    field.visible = false;
                    mapping = false;
                }  
                if(field.code === 'new-eform'){
                    field.visible = false;
                    mapping = false;
                }  
            }

            if (field.type === 'DOCUMENTS-LIST' && field.code === 'eform') {
                let existingDocuments: any = get(this.fase, field.mappingInput);
                
                if(existingDocuments != null){
                    let sdkExistingDocuments: Array<SdkDocumentItem> = [{
                        titolo: 'eForm',
                        fileDownloadCallback() {
                            return of(existingDocuments);
                        },
                        code: '1',
                        tipoFile: 'xml',
                        descrizione: 'File eForm'
                    }]
                    set(field, 'documents', sdkExistingDocuments);                    
                    mapping = false;
                }
            }
            if(field.code === 'altre-motivazioni'){
                field.visible = false;
                mapping = false;
            }
            if(field.code === 'cig-nuova-proc'){
                field.visible = false;
                mapping = false;
            }            
            if(field.code === 'motivazioni-modifica-contrattuale-data'){
                field.visible = false;
                mapping = false;
            }
            if(field.code === 'motivo-rev-prezzi') {
                field.visible = false;
                mapping = false;
                field.visibleCondition = null;
            }
        } else{
            if(field.code === 'imp-finpa' || field.code === 'entr-utenza' || field.code === 'intr-attivo' || field.code === 'imp-opzioni' || field.code === 'eform' || field.code === 'new-eform'){
                field.visible = false;
                mapping = false;
            }
            if(field.code === 'altre-motivazioni-pcp'){
                field.visible = false;
                mapping = false;
            }
            if(field.code === 'cig-nuova-proc-pcp'){
                field.visible = false;
                mapping = false;
            }
            if(field.code === 'motivazioni-modifica-contrattuale-data-pcp'){
                field.visible = false;
                mapping = false;
            } 
            if(field.code === 'url-varianti-co'){
                field.visible = false;
                mapping = false;
            } 
            if(field.code === 'url-varianti-co-mandatory'){
                field.visible = false;
                mapping = false;
            } 
            if(field.code === 'motivo-rev-prezzi-pcp') {
                field.visible = false;
                mapping = false;
                field.visibleCondition = null;
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
        return Constants.FASE_MODIFICA_CONTRATTUALE_TABELLATI;
    }


    protected loadDettaglio = (): Observable<FaseVarianteEntry> => {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseVarianteEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseVarianteEntry> {
        return () => {
            return this.faseModificaContrattualeService.getFase(codGara, codLotto, progressivo);
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
     * Metodo per calcolare l'importo subtotale tra i campi "Importo contrattuale rideterminato Lavori",
     * "Importo contrattuale rideterminato Servizi" e "Importo contrattuale rideterminato Forniture"
     * @param field Il campo attualmente modificato
     */
    private calcolaImportoSubtotale(field: SdkFormBuilderField): void {

        let importoLavoriValue: number = toNumber(this.getCurrentNumberValue(field, 'quadro-economico-data', 'imp-ridet-lavori'));
        let importoServiziValue: number = toNumber(this.getCurrentNumberValue(field, 'quadro-economico-data', 'imp-ridet-servizi'));
        let importoFornitureValue: number = toNumber(this.getCurrentNumberValue(field, 'quadro-economico-data', 'imp-ridet-fornit'));

        let somma: number = sum([importoLavoriValue, importoServiziValue, importoFornitureValue]);

        this.formBuilderDataSubject.next({
            code: 'imp-subtotale',
            data: toString(somma)
        });
    }

    /**
     * Metodo per calcolare l'importo compl appalto
     * @param field Il campo attualmente modificato
     */
    private calcolaImportoComplAppalto(field: SdkFormBuilderField): void {

        let importoSubtotaleValue: number = toNumber(this.getCurrentNumberValue(field, 'quadro-economico-data', 'imp-subtotale'));
        let importoSicurezzaValue: number = toNumber(this.getCurrentNumberValue(field, 'quadro-economico-data', 'imp-sicurezza'));
        let importoProgettazioneValue: number = toNumber(this.getCurrentNumberValue(field, 'quadro-economico-data', 'imp-progettazione'));
        let importoNonAssogValue: number = toNumber(this.getCurrentNumberValue(field, 'quadro-economico-data', 'imp-non-assog'));

        let somma: number = sum([importoSubtotaleValue, importoSicurezzaValue, importoProgettazioneValue, importoNonAssogValue]);

        this.formBuilderDataSubject.next({
            code: 'imp-compl-appalto',
            data: toString(somma)
        });
    }

    /**
     * Metodo per calcolare l'importo compl intervento
     * @param field Il campo attualmente modificato
     */
    private calcolaImportoComplIntervento(field: SdkFormBuilderField): void {

        let importoComplAppaltoValue: number = toNumber(this.getCurrentNumberValue(field, 'quadro-economico-data', 'imp-compl-appalto'));
        let importoDisposizioneValue: number = toNumber(this.getCurrentNumberValue(field, 'quadro-economico-data', 'imp-disposizione'));

        let somma: number = sum([importoComplAppaltoValue, importoDisposizioneValue]);

        this.formBuilderDataSubject.next({
            code: 'imp-compl-intervento',
            data: toString(somma)
        });
    }

    // #endregion
}
