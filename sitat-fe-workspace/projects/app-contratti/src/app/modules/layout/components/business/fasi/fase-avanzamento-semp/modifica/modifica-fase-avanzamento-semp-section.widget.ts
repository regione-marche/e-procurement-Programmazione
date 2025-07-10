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

import { FaseAvanzamentoSempEntry } from '../../../../../../models/fasi/fase-avanzamento-semp.model';
import { FaseAvanzamentoSempService } from '../../../../../../services/fasi/fase-avanzamento-semp.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';



@Component({
    templateUrl: `modifica-fase-avanzamento-semp-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseAvanzamentoSempSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `modifica-fase-avanzamento-semp-section`;


    private fase: FaseAvanzamentoSempEntry;


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

    private get faseAvanzamentoSempService(): FaseAvanzamentoSempService {
        return this.injectable(FaseAvanzamentoSempService);
    }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        // TODO
    }

    // #endregion

    // #region Protected


    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseAvanzamentoSempEntry, dynamicField?: boolean) => {
        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if (field.code.startsWith('data-') && field.type === 'TEXT') {
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

        if(field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
            field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
        }

        return {
            mapping,
            field
        };
    }

    protected tabellati(): Array<string> {
        return [];
    }

    protected loadDettaglio = (): Observable<FaseAvanzamentoSempEntry> => {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseAvanzamentoSempEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseAvanzamentoSempEntry> {
        return () => {
            return this.faseAvanzamentoSempService.getFase(codGara, codLotto, progressivo);
        }
    }

    // #endregion
}
