import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { ImpresaEntry } from '@maggioli/app-commons';
import { SdkAutocompleteItem, SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isObject, isString, set, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { FaseInadempienzeSicurezzaEntry } from '../../../../../../models/fasi/fase-inadempienze-sicurezza.model';
import { FaseInadempienzeSicurezzaService } from '../../../../../../services/fasi/fase-inadempienze-sicurezza.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';



@Component({
    templateUrl: `modifica-fase-inadempienze-sicurezza-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseInadempienzeSicurezzaSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `modifica-fase-inadempienze-sicurezza-section`;

    private fase: FaseInadempienzeSicurezzaEntry;

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

    private get faseInadempienzeSicurezzaService(): FaseInadempienzeSicurezzaService {
        return this.injectable(FaseInadempienzeSicurezzaService);
    }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void { }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseInadempienzeSicurezzaEntry, dynamicField?: boolean) => {

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
        } else if (field.type === 'AUTOCOMPLETE') {
            let data: ImpresaEntry = get(restObject, field.mappingInput);
            if (isObject(data)) {
                let item: SdkAutocompleteItem = {
                    ...data,
                    text: `${data.codiceFiscale} ${data.ragioneSociale}`,
                    _key: data.codiceImpresa
                };
                set(field, 'data', item);
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

    protected loadDettaglio = (): Observable<FaseInadempienzeSicurezzaEntry> => {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseInadempienzeSicurezzaEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseInadempienzeSicurezzaEntry> {
        return () => {
            return this.faseInadempienzeSicurezzaService.getFase(codGara, codLotto, progressivo);
        }
    }

    // #endregion
}
