import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { ValoreTabellato } from '@maggioli/app-commons';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkFormBuilderField, SdkFormBuilderInput } from '@maggioli/sdk-controls';
import { cloneDeep, each, get, isEmpty, isString, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseCantieriEntry, InizFaseCantieriEntry } from '../../../../../../models/fasi/fase-cantieri.model';
import { FaseCantieriService } from '../../../../../../services/fasi/fase-cantieri.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';



@Component({
    templateUrl: `nuova-fase-cantieri-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseCantieriSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables
    private provinceMap: IDictionary<string> = {};

    private fase: InizFaseCantieriEntry;

    @HostBinding('class')
    public classNames = `nuova-fase-cantieri-section`;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        // set update state
        this.setUpdateState(true);
        this.loadListaTabellati = true;

        this.tabellatiCacheService.getValoriTabellato(Constants.PROVINCE).subscribe((province: Array<ValoreTabellato>) => {
            each(province, (provincia: ValoreTabellato) => {
                this.provinceMap[provincia.codistat] = provincia.codice;
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

    protected loadDettaglio = (): Observable<any> => {
        const factory = this.datiInizializzazioneFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: InizFaseCantieriEntry) => {
                    this.fase = result;
                    return this.fase;
                })
            );
    }

    // #endregion

    // #region Private

    private datiInizializzazioneFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<InizFaseCantieriEntry> {
        return () => {
            return this.faseCantieriService.getDatiInizializzazione(codGara, codLotto, progressivo);
        }
    }


    // #endregion
}
