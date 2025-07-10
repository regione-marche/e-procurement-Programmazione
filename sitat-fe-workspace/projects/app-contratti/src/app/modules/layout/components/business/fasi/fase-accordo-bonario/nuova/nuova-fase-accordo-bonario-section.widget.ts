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

import { FaseAccordoBonarioEntry } from '../../../../../../models/fasi/fase-accordo-bonario.model';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';



@Component({
    templateUrl: `nuova-fase-accordo-bonario-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseAccordoBonarioSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `nuova-fase-accordo-bonario-section`;

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
        if(field.nomeCampo) {
            super.setValueInAssociazionePagamenti(field);
        }
    }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseAccordoBonarioEntry, dynamicField?: boolean) => {
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

    protected loadDettaglio = (): Observable<any> => {
        return undefined;
    }

    // #endregion

    // #region Private


    // #endregion
}
