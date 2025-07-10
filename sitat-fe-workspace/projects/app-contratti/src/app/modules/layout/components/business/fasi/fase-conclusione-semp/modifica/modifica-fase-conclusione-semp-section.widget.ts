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
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseConclusioneSempEntry } from '../../../../../../models/fasi/fase-conclusione-semp.model';
import { InizFaseConclusioneContrattoEntry } from '../../../../../../models/fasi/fase-conclusione.model';
import { FaseConclusioneSempService } from '../../../../../../services/fasi/fase-conclusione-semp.service';
import { FaseConclusioneService } from '../../../../../../services/fasi/fase-conclusione.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';



@Component({
    templateUrl: `modifica-fase-conclusione-semp-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseConclusioneSempSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `modifica-fase-conclusione-semp-section`;

    private fase: FaseConclusioneSempEntry;
    private iniz: InizFaseConclusioneContrattoEntry;

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

    private get faseConclusioneSempService(): FaseConclusioneSempService {
        return this.injectable(FaseConclusioneSempService);
    }

    private get faseConclusioneService(): FaseConclusioneService {
        return this.injectable(FaseConclusioneService);
    }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
    }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseConclusioneSempEntry, dynamicField?: boolean) => {

        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        // inizializzazione a 1 se non esiste la fase iniziale
        if ((field.code === 'intantic' || field.code === 'intantic-hidden') && this.iniz.faseInizialeExists === false) {
            if (field.code === 'intantic') {
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
        return Constants.FASE_CONCLUSIONE_SEMP_TABELLATI;
    }

    protected loadDettaglio = (): Observable<FaseConclusioneSempEntry> => {
        const inizFactory = this.datiInizializzazioneFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(inizFactory, this.messagesPanel)
            .pipe(
                map((result: InizFaseConclusioneContrattoEntry) => {
                    this.iniz = result;
                    return this.iniz;
                }),
                mergeMap(() => {
                    const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
                    return this.requestHelper.begin(factory, this.messagesPanel);
                }),
                map((result: FaseConclusioneSempEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseConclusioneSempEntry> {
        return () => {
            return this.faseConclusioneSempService.getFase(codGara, codLotto, progressivo);
        }
    }

    private datiInizializzazioneFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<InizFaseConclusioneContrattoEntry> {
        return () => {
            return this.faseConclusioneService.getDatiInizializzazione(codGara, codLotto, progressivo);
        }
    }

    // #endregion
}
