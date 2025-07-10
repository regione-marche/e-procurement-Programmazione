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

import { FaseStipulaAccordoQuadroEntry } from '../../../../../../models/fasi/fase-stipula-accordo-quadro.model';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';



@Component({
    templateUrl: `nuova-fase-stipula-accordo-quadro-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseStipulaAccordoQuadroSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `nuova-fase-stipula-accordo-quadro-section`;

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

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseStipulaAccordoQuadroEntry, dynamicField?: boolean) => {
        let mapping: boolean = true;
        
        const keyAny: any = get(restObject, field.mappingInput);
        const key: string = dynamicField === true ? field.data : toString(keyAny);
        
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

    protected loadDettaglio = (): Observable<any> => {
        return undefined;
    }

    protected tabellati(): string[] {
        return [];
    }

    // #endregion

    // #region Private

    
    // #endregion
}
