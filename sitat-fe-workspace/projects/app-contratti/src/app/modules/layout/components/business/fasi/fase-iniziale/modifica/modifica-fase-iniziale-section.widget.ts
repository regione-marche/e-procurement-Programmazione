import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, filter, get, isEmpty, isString, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import {
    FaseInizialeEsecuzioneEntry,
    InizFaseInizialeContrattoEntry,
} from '../../../../../../models/fasi/fase-iniziale.model';
import { FaseInizialeService } from '../../../../../../services/fasi/fase-iniziale.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';
import { GaraEntry } from '@maggioli/app-commons';


@Component({
    templateUrl: `modifica-fase-iniziale-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseInizialeSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `modifica-fase-iniziale-section`;

    private iniz: InizFaseInizialeContrattoEntry;
    private fase: FaseInizialeEsecuzioneEntry;

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

    private get faseInizialeService(): FaseInizialeService { return this.injectable(FaseInizialeService) }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void { }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseInizialeEsecuzioneEntry, dynamicField?: boolean) => {
        let mapping: boolean = true;

        let keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if (
            field.code === 'data-stipula' ||
            field.code === 'data-esecutivita' ||
            field.code === 'data-ini-prog-esec' ||
            field.code === 'data-app-prog-esec' ||
            field.code === 'data-verbale-cons' ||
            field.code === 'data-verbale-def' ||
            field.code === 'data-verb-inizio' ||
            field.code === 'data-termine' ||
            field.code === 'data-guce' ||
            field.code === 'data-guri' ||
            field.code === 'data-albo'
        ) {
            let value = get(restObject, field.mappingInput);
            if (value != null) {
                if (field.type === 'TEXT') {
                    field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                    key = field.data;
                    mapping = false;
                }
            }
        }

        if(this.lotto.tipologia === 'F' || this.lotto.tipologia === 'S'){
            if(field.code === 'flag-frazionata'){
                field.label = "FASE-INIZIALE.TERMINE-ESECUZIONE-DATA.FLAG-FRAZIONATA-FS";
            }
            if(field.code === 'data-verbale-cons'){
                field.label = "FASE-INIZIALE.TERMINE-ESECUZIONE-DATA.DATA-VERBALE-CONS-FS";
            }
            if(field.code === 'data-verbale-def'){
                field.label = "FASE-INIZIALE.TERMINE-ESECUZIONE-DATA.DATA-VERBALE-DEF-FS";
            }
        }

        if (field.code === 'incontri-vigil' && this.iniz.incontriVigilVisible === false) {
            field.visible = false;
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
        return Constants.FASE_INIZIALE_TABELLATI;
    }

    protected loadDettaglio = (): Observable<FaseInizialeEsecuzioneEntry> => {
        return this.getInizObservable().pipe(
            mergeMap(() => this.getDettaglioObservable())
        );
    }

    protected elaborateGara = (result: GaraEntry) => {
        this.gara = result;
        if(this.gara.pcp){
            this.config.body.fields = filter(this.config.body.fields, (one) => one.code !== 'contratto-appalto-data' && one.code !== 'pubblicazione-esito-data');            
        }
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseInizialeEsecuzioneEntry> {
        return () => {
            return this.faseInizialeService.getFase(codGara, codLotto, progressivo);
        }
    }

    private datiInizializzazioneFaseFactory(codGara: string, codLotto: string): () => Observable<InizFaseInizialeContrattoEntry> {
        return () => {
            return this.faseInizialeService.getDatiInizializzazione(codGara, codLotto);
        }
    }

    private getInizObservable(): Observable<InizFaseInizialeContrattoEntry> {
        const factory = this.datiInizializzazioneFaseFactory(this.codGara, this.codLotto);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: InizFaseInizialeContrattoEntry) => {
                    this.iniz = result;
                    return result;
                })
            );
    }

    private getDettaglioObservable(): Observable<FaseInizialeEsecuzioneEntry> {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseInizialeEsecuzioneEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion
}
