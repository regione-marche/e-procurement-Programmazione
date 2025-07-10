import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import {
    SdkFormBuilderField
} from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isString, toString } from 'lodash-es';
import { Observable } from 'rxjs';

import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';

@Component({
    templateUrl: `nuovo-fase-inc-prof-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseIncarichiProfessionaliSectionWidget extends FaseAbstractSectionWidget {


    // #region Variables

    @HostBinding('class') classNames = `nuova-fase-incarichi-professionali-section`;



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

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: any, dynamicField?: boolean) => {
        let mapping: boolean = true;

        let keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if(Object.keys(restObject).length > 0 && restObject?.pcp === true && field.code === 'id-ruolo') {
            field.visible = false;
            mapping = false;
        }

        if(Object.keys(restObject).length > 0 && restObject?.pcp !== true && field.code === 'id-ruolo-pcp') {
            field.visible = false;
            mapping = false;
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
        return undefined;
    }

    protected loadDettaglio = (): Observable<any> => {
        return undefined;
    }


    // #endregion

    // #region Private



    // #endregion

    // #region Getters



    // #endregion
}
