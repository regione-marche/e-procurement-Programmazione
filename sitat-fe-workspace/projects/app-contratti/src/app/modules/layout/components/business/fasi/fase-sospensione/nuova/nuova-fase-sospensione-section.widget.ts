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

import { Constants } from '../../../../../../../app.constants';
import { FaseSospensioneEntry } from '../../../../../../models/fasi/fase-sospensione.model';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';

@Component({
    templateUrl: `nuova-fase-sospensione-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseSospensioneSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `nuova-fase-sospensione-section`;

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

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        // TODO implement
    }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseSospensioneEntry, dynamicField?: boolean) => {

        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        const key: string = dynamicField === true ? field.data : toString(keyAny);

        // TODO

        if(this.gara.pcp){
            if(field.code === 'data-verb-ripr' ||
               field.code === 'flag-supero-tempo' ||
               field.code === 'flag-riserve' ||
               field.code === 'flag-verbale' ){
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
        return Constants.FASE_SOSPENSIONE_TABELLATI;
    }

    protected loadDettaglio = (): Observable<any> => {
        return undefined;
    }

    // #endregion

    // #region Private


    // #endregion
}
