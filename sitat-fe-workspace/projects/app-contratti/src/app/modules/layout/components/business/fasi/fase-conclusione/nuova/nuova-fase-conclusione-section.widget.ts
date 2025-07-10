import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isString, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { InizFaseConclusioneContrattoEntry } from '../../../../../../models/fasi/fase-conclusione.model';
import { FaseConclusioneService } from '../../../../../../services/fasi/fase-conclusione.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';



@Component({
    templateUrl: `nuova-fase-conclusione-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseConclusioneSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `nuova-fase-conclusione-section`;

    private fase: InizFaseConclusioneContrattoEntry;

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

    // #region Getters

    private get faseConclusioneService(): FaseConclusioneService {
        return this.injectable(FaseConclusioneService);
    }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        if (field.code === 'num-infortuni') {
            const value = field.data === undefined ? undefined : field.data;
            if (value === 0 || value === undefined) {
                ['num-inf-perm', 'num-inf-mort'].forEach((fieldCode: string) => {
                    this.formBuilderDataSubject.next({
                        code: fieldCode,
                        data: value
                    });
                });
            }
        } else if(field.code === 'intantic' && field.type !== 'TEXT') {
            this.formBuilderDataSubject.next({
                code: 'intantic-hidden',
                data: field.data.key
            });
        }
    }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: InizFaseConclusioneContrattoEntry, dynamicField?: boolean) => {
        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if (field.code === 'id-motivo-interr' && this.gara.versioneSimog === 1) {
            field.listCode = 'MotivoInterruzioneSimogEq1'
        }

        if (field.code === 'id-motivo-risol' && this.gara.versioneSimog === 1) {
            field.listCode = 'MotivoRisoluzioneSimoqEq1'
        }

        // inizializzazione a 1 se non esiste la fase iniziale
        if((field.code === 'intantic' || field.code === 'intantic-hidden') && restObject.faseInizialeExists === false) {
            if(field.code === 'intantic') {
                field.type = 'TEXT';
                field.mappingOutput = null;
            }
            field.data = '1';
            key = '1';
            mapping = false;
        }

        if (field.code.startsWith('data-') && field.type === 'TEXT') {
            const value = get(restObject, field.mappingInput);
            if (value != null) {
                field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                key = field.data;
                mapping = false;
            }
        }

        if(this.gara.pcp){
            if(field.code === 'id-motivo-risol' ||
                field.code === 'data-verbale-consegna' ||
                field.code === 'data-termine-contratto-ult' ||
                field.code === 'num-giorni-proroga' ||
                field.code === 'ore-lavorate'){
                    field.visible = false;
                    mapping = false;
            }
        }
        //VIGILANZA2-507: Adeguamento tabellato W3013 per causa interruzione anticipata
        else {
            if(restObject.faseInizialeExists === true) {
                this.valoriTabellati.MotivoInterruzione = this.valoriTabellati?.MotivoInterruzione.filter((elem) => Number(elem.codice) <= 8);
            }
            else {
                let daEscludere = ['2', '4', '5', '6', '8'];
                this.valoriTabellati.MotivoInterruzione = this.valoriTabellati?.MotivoInterruzione.filter((elem) => !daEscludere.includes(elem.codice));
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
        return Constants.FASE_CONCLUSIONE_TABELLATI;
    }

    /**
     * Metodo che carica le informazioni da W9Iniz e W9Avan
     */
    protected loadDettaglio = (): Observable<InizFaseConclusioneContrattoEntry> => {
        const factory = this.datiInizializzazioneFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: InizFaseConclusioneContrattoEntry) => {
                    this.fase = result;
                    return this.fase;
                })
            );
    }

    // #endregion

    // #region Private

    private datiInizializzazioneFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<InizFaseConclusioneContrattoEntry> {
        return () => {
            return this.faseConclusioneService.getDatiInizializzazione(codGara, codLotto, progressivo);
        }
    }

    // #endregion
}
