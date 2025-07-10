import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkFormBuilderField, SdkTextboxOutput } from '@maggioli/sdk-controls';
import { cloneDeep, get, isString, set } from 'lodash-es';
import { map, Observable } from 'rxjs';

import { ResponseMaxNumAvan } from 'projects/app-contratti/src/app/modules/models/fasi/fase-avanzamento.model';
import { FaseAvanzamentoService } from 'projects/app-contratti/src/app/modules/services/fasi/fase-avanzamento.service';
import { Constants } from '../../../../../../../app.constants';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';


@Component({
    templateUrl: `nuova-fase-avanz-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseAvanzamentoSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `nuova-fase-avanzamento-section`;

    private maxNumAvan: number;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

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
        if(field.nomeCampo) {
            super.setValueInAssociazionePagamenti(field);
        }
    }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: any, dynamicField?: boolean) => {
        // TODO implement
        let mapping = null;

        if(field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
            field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
        }

        if(this.maxNumAvan === 1 && field.code === 'num-avan') {
            mapping = false;
            set(field, 'data', this.maxNumAvan);
        }

        return {
            mapping,
            field
        };
    }

    protected tabellati(): Array<string> {
        return Constants.FASE_AVANZAMENTO_TABELLATI;
    }

    protected loadDettaglio = (): Observable<any> => {
        return this.loadMaxNumAvan().pipe(
            map(() => {
                return {};
            })
        );
    }

    protected loadMaxNumAvan = (): Observable<number> => {
        let factory = this.maxNumAvanFactory(+this.codGara, +this.codLotto);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: number) => {
                    this.maxNumAvan = result;
                    return result;
                })
            );
    }

    // #endregion

    // #region Private

    private maxNumAvanFactory(codGara: number, codLotto: number): () => Observable<ResponseMaxNumAvan> {
        return () => {
            return this.faseAvanzamentoService.getMaxNumAvan(codGara, codLotto);
        }
    }

    // #endregion

    // #region Getters
    
    private get faseAvanzamentoService(): FaseAvanzamentoService { return this.injectable(FaseAvanzamentoService) }

    // #endregion
}
