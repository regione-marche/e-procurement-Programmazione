import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, filter, get, isEmpty, isEqual, isString, remove, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseAggiudicazioneEntry } from '../../../../../../models/fasi/fase-aggiudicazione.model';
import { FaseAggiudicazioneService } from '../../../../../../services/fasi/fase-aggiudicazione.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';
import { GaraEntry } from '@maggioli/app-commons';


@Component({
    templateUrl: `dettaglio-fase-agg-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioFaseAggiudicazioneSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-fase-aggiudicazione-section`;

    private fase: FaseAggiudicazioneEntry;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.loadListaTabellati = true;

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

    public manageOutputField(field: SdkFormBuilderField): void { }

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
               field.code === 'offerta-minima') 
            {
                field.visible = false;
                mapping = false;
            }
        }

        if (
            field.code === 'data-manif-interesse' ||
            field.code === 'data-scadenza-richiesta-invito' ||
            field.code === 'data-invito' ||
            field.code === 'data-scadenza-pres-offerta' ||
            field.code === 'data-verb-aggiudicazione' ||
            field.code === 'data-atto'
        ) {
            let value = get(restObject, field.mappingInput);
            if (value != null && field.type === 'TEXT') {
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
            field.type = 'TEXT';
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

    protected elaborateGara = (result: GaraEntry) => {
        this.gara = result;
        if(this.gara.pcp){            
            this.config.menuTabs = filter(this.config.menuTabs, (one) => one.code !== 'dettaglio-inc-prof-fase-agg');
            this.menuTabs = this.config.menuTabs;
        }
    }

    protected showButtons(): void {
        if(this.fromLS !== null && isEqual(this.fromLS, 'LS')) {
            this.buttonsSubj.next(this.buttonsLS);
        }
        else {
            if(this.gara.readOnly){
                this.buttonsSubj.next(this.buttonsRO);
            } else if(this.gara.pcp){
                this.buttonsSubj.next(this.buttonsRO);
            } else{
                this.buttonsSubj.next(this.buttons);
            } 
        }
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseAggiudicazioneEntry> {
        return () => {
            return this.faseAggiudicazioneService.getFase(codGara, codLotto, progressivo);
        }
    }

    // #endregion
}
