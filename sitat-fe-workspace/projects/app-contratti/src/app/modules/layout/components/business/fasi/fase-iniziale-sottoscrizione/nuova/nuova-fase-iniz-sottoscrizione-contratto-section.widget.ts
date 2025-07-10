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
import {
    InizFaseInizialeContrattoEntry
} from '../../../../../../models/fasi/fase-iniziale.model';
import { FaseInizialeService } from '../../../../../../services/fasi/fase-iniziale.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';


@Component({
    selector: 'nuova-fase-iniz-sottoscrizione-contratto',
    templateUrl: `nuova-fase-iniz-sottoscrizione-contratto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseInizSottoscrizioneContrattoSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `nuova-fase-iniz-sottoscrizione-contratto-section`;

    private fase: InizFaseInizialeContrattoEntry;

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

    public manageOutputField(field: SdkFormBuilderField): void { }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: InizFaseInizialeContrattoEntry, dynamicField?: boolean) => {
        let mapping: boolean = true;

        let keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        // if (field.code === 'data-stipula' && restObject.dataVerbale != null) {
        //     if (field.type === 'TEXT') {
        //         field.data = this.dateHelper.format(restObject.dataVerbale, this.config.locale.dateFormat);
        //         mapping = false;
        //     } else {
        //         field.data = restObject.dataVerbale;
        //         mapping = false;
        //     }
        // }


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

    protected loadDettaglio = (): Observable<any> => {
        const factory = this.datiInizializzazioneFaseFactory(this.codGara, this.codLotto);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: InizFaseInizialeContrattoEntry) => {
                    this.fase = result;
                    return this.fase;
                })
            );
    }

    // #endregion

    // #region Private

    private datiInizializzazioneFaseFactory(codGara: string, codLotto: string): () => Observable<InizFaseInizialeContrattoEntry> {
        return () => {
            return this.faseInizialeService.getDatiInizializzazione(codGara, codLotto);
        }
    }

    // #endregion

    // #region Getters

    private get faseInizialeService(): FaseInizialeService { return this.injectable(FaseInizialeService) }

    // #endregion
}
