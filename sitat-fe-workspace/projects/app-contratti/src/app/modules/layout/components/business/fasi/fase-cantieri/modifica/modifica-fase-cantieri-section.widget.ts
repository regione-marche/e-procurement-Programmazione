import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { ImpresaEntry, ValoreTabellato } from '@maggioli/app-commons';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem, SdkFormBuilderField, SdkFormBuilderInput } from '@maggioli/sdk-controls';
import { cloneDeep, each, get, has, isEmpty, isObject, isString, set, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseCantieriEntry } from '../../../../../../models/fasi/fase-cantieri.model';
import { FaseCantieriService } from '../../../../../../services/fasi/fase-cantieri.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';



@Component({
    templateUrl: `modifica-fase-cantieri-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseCantieriSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables
    private provinceMap: IDictionary<string> = {};
    private comuniMap: IDictionary<ValoreTabellato> = {};

    @HostBinding('class')
    public classNames = `modifica-fase-cantieri-section`;

    private fase: FaseCantieriEntry;

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

        this.tabellatiCacheService.getValoriTabellato(Constants.PROVINCE).subscribe((province: Array<ValoreTabellato>) => {
            each(province, (provincia: ValoreTabellato) => {
                this.provinceMap[provincia.codistat] = provincia.codice;
            });
        });
        this.tabellatiCacheService.getValoriTabellato(Constants.COMUNI).subscribe((comuni: Array<ValoreTabellato>) => {
            each(comuni, (comune: ValoreTabellato) => {
                this.comuniMap[comune.codistat] = comune;
            });
        });
        super.onInit();
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get faseCantieriService(): FaseCantieriService {
        return this.injectable(FaseCantieriService);
    }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        if (field.code === 'cod-istat-comune') {
            const value: ValoreTabellato = field.data;
            if (value) {
                const codIstatProvincia = `0${value.codistat.substring(3, 6)}`;
                const descrProvincia = this.provinceMap[codIstatProvincia];
                const fieldBuilder: SdkFormBuilderInput = {
                    code: 'provincia',
                    data: descrProvincia
                }
                if (field.groupCode) {
                    fieldBuilder.groupCode = field.groupCode;
                }
                this.formBuilderDataSubject.next(fieldBuilder);
            }
        }
    }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseCantieriEntry, dynamicField?: boolean) => {

        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if (field.type === 'AUTOCOMPLETE' && field.code === 'cod-istat-comune') {
            if (isObject(restObject) && has(restObject, field.mappingInput) && get(restObject, field.mappingInput) != undefined) {
                const comune: ValoreTabellato = this.comuniMap[get(restObject, field.mappingInput) as any];
                field.data = {
                    ...comune,
                    text: `${comune.descrizione} (${comune.codiceProvincia})`
                };
                mapping = false;
            }
        } else if (field.type === 'AUTOCOMPLETE' && field.code === 'nomest') {
            const data: ImpresaEntry = get(restObject, field.mappingInput);
            if (isObject(data)) {
                const item: SdkAutocompleteItem = {
                    ...data,
                    text: `${data.codiceFiscale} ${data.ragioneSociale}`,
                    _key: data.codiceImpresa
                };
                set(field, 'data', item);
                mapping = false;
            }
        } else if (field.code.startsWith('data-') && field.type === 'TEXT') {
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

        if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
            field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
        }

        return {
            mapping,
            field
        };
    }

    protected tabellati(): Array<string> {
        return Constants.FASE_CANTIERI_TABELLATI;
    }

    protected loadDettaglio = (): Observable<FaseCantieriEntry> => {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseCantieriEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseCantieriEntry> {
        return () => {
            return this.faseCantieriService.getFase(codGara, codLotto, progressivo);
        }
    }

    // #endregion
}
