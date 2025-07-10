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
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';
import { FaseConclusioneAffidamentiDirettiEntry } from 'projects/app-contratti/src/app/modules/models/fasi/fase-conclusione-affidamenti-diretti.model';
import { InizFaseConclusioneContrattoEntry } from 'projects/app-contratti/src/app/modules/models/fasi/fase-conclusione.model';
import { FaseConclusioneAffidamentiDirettiService } from 'projects/app-contratti/src/app/modules/services/fasi/fase-conclusione-affidamenti-diretti.service';



@Component({
    templateUrl: `modifica-fase-conclusione-affidamenti-diretti-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseConclusioneAffidamentiDirettiSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `modifica-fase-conclusione-affidamenti-diretti-section`;

    private fase: FaseConclusioneAffidamentiDirettiEntry;

    private execute: boolean = false;
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

    private get faseConclusioneAffidamentiDirettiService(): FaseConclusioneAffidamentiDirettiService {
        return this.injectable(FaseConclusioneAffidamentiDirettiService);
    }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        
        if(field.nomeCampo) {
            super.setValueInAssociazionePagamenti(field);
        }

        if (field.code === 'num-infortuni') {
            const value = field.data === undefined ? undefined : field.data;
            if ((value === 0 || value === undefined) && this.execute === true) {
                ['num-inf-perm', 'num-inf-mort'].forEach((fieldCode: string) => {
                    this.formBuilderDataSubject.next({
                        code: fieldCode,
                        data: value
                    });
                });
            }
            this.execute = true;
        }
    }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseConclusioneAffidamentiDirettiEntry, dynamicField?: boolean) => {

        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        // inizializzazione a 1 se non esiste la fase iniziale        

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
        return Constants.FASE_CONCLUSIONE_TABELLATI;
    }

    protected loadDettaglio = (): Observable<FaseConclusioneAffidamentiDirettiEntry> => {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(                
                map((result: FaseConclusioneAffidamentiDirettiEntry) => {
                    this.fase = result;                    
                    return result;
                })
            );
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseConclusioneAffidamentiDirettiEntry> {
        return () => {
            return this.faseConclusioneAffidamentiDirettiService.getFase(codGara, codLotto, progressivo);
        }
    }

    // #endregion
}
