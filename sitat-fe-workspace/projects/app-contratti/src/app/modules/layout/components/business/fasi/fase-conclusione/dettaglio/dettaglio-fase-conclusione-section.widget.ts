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
import { FaseConclusioneEntry } from '../../../../../../models/fasi/fase-conclusione.model';
import { FaseConclusioneService } from '../../../../../../services/fasi/fase-conclusione.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';


@Component({
    templateUrl: `dettaglio-fase-conclusione-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioFaseConclusioneSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `dettaglio-fase-conclusione-section`;

    private fase: FaseConclusioneEntry;

    // #endregion

    constructor(injector: Injector, cdr: ChangeDetectorRef) {
        super(injector, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        this.loadListaTabellati = true;

        super.onInit();
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get faseConclusioneService(): FaseConclusioneService { return this.injectable(FaseConclusioneService) }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void { }

    // #endregion

    // #region Protected
    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseConclusioneEntry, dynamicField?: boolean) => {

        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if (field.code.startsWith('data-') && field.type === 'TEXT') {
            const value = get(restObject, field.mappingInput);
            if (value != null) {
                field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                key = field.data;
                mapping = false;
            }
        }

        if(this.gara.pcp){
            const value = get(restObject, field.mappingInput);
            if(field.code === 'id-motivo-risol' ||
                field.code === 'data-verbale-consegna' ||
                field.code === 'data-termine-contratto-ult' ||
                field.code === 'num-giorni-proroga' ||
                field.code === 'ore-lavorate'){
                    field.visible = false;
                    mapping = false;
            }
            if((field.code === 'scost-data-fine' ||
                field.code === 'gg-fine-ese') && value == null){
                field.visible = false;
                mapping = false;
            } 
        } else{
            if(field.code === 'scost-data-fine' ||
                field.code === 'gg-fine-ese'){
                    field.visible = false;
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
        return Constants.FASE_CONCLUSIONE_TABELLATI;
    }

    protected loadDettaglio = (): Observable<FaseConclusioneEntry> => {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseConclusioneEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion

    // #region Private
    
    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseConclusioneEntry> {
        return () => {
            return this.faseConclusioneService.getFase(codGara, codLotto, progressivo);
        }
    }

    // #endregion
}
