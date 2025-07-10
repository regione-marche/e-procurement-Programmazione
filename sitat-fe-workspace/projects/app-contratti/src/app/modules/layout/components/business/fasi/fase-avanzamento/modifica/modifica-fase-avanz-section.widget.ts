import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { get, isEmpty, toString, isString, cloneDeep } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { SdkFormBuilderField, SdkTextboxOutput } from '@maggioli/sdk-controls';

import { Constants } from '../../../../../../../app.constants';
import { FaseAvanzamentoEntry } from '../../../../../../models/fasi/fase-avanzamento.model';
import { FaseAvanzamentoService } from '../../../../../../services/fasi/fase-avanzamento.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';


@Component({
    templateUrl: `modifica-fase-avanz-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseAvanzamentoSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `modifica-fase-avanzamento-section`;

    private fase: FaseAvanzamentoEntry;

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

    private get faseAvanzamentoService(): FaseAvanzamentoService {
        return this.injectable(FaseAvanzamentoService);
    }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        if(field.nomeCampo) {
            super.setValueInAssociazionePagamenti(field);
        }
    }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseAvanzamentoEntry, dynamicField?: boolean) => {
        let mapping: boolean = true;

        let keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

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

        if(field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
            field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
        }

        return {
            mapping,
            field
        };
    }

    protected tabellati(): Array<string> {
        return Constants.FASE_AVANZAMENTO_TABELLATI;
    }

    protected loadDettaglio = (): Observable<FaseAvanzamentoEntry> => {
        let factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseAvanzamentoEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseAvanzamentoEntry> {
        return () => {
            return this.faseAvanzamentoService.getFase(codGara, codLotto, progressivo);
        }
    }

    // #endregion
}
