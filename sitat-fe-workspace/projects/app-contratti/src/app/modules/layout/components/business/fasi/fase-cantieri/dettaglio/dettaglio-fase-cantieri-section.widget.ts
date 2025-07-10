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
import { SdkFormBuilderField, SdkTextOutput } from '@maggioli/sdk-controls';
import { cloneDeep, each, get, has, isEmpty, isObject, isString, set, toString } from 'lodash-es';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseCantieriEntry, FaseCantieriImpEntry } from '../../../../../../models/fasi/fase-cantieri.model';
import { FaseCantieriService } from '../../../../../../services/fasi/fase-cantieri.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';


@Component({
    templateUrl: `dettaglio-fase-cantieri-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioFaseCantieriSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `dettaglio-fase-cantieri-section`;

    private fase: FaseCantieriEntry;
    private comuniMap: IDictionary<string> = {};

    // #endregion

    constructor(injector: Injector, cdr: ChangeDetectorRef) {
        super(injector, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        this.loadListaTabellati = true;

        this.tabellatiCacheService.getValoriTabellato(Constants.COMUNI).subscribe((comuni: Array<ValoreTabellato>) => {
            each(comuni, (comune: ValoreTabellato) => {
                this.comuniMap[comune.codistat] = comune.descrizione;
            });
        });
        super.onInit();

        this.modalConfig = {
            code: 'modal',
            title: '',
            openSubject: new Subject()
        };
        this.modalConfigObs.next(this.modalConfig);
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get faseCantieriService(): FaseCantieriService { return this.injectable(FaseCantieriService) }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void { }

    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: config.modalComponent,
                componentConfig: {
                    ...config.modalComponentConfig,
                    impresa: config.textModalContent
                }
            };
            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    // #endregion

    // #region Protected
    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseCantieriEntry | FaseCantieriImpEntry, dynamicField?: boolean) => {

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
        } else if (field.code === 'cod-istat-comune') {
            if (has(restObject, field.mappingInput) && !isEmpty(get(restObject, field.mappingInput))) {
                field.data = this.comuniMap[(restObject as FaseCantieriEntry).codIstatComune];
                mapping = false;
            }
        } else if (field.code === 'nomest') {
            set(field, 'textModalContent', (<FaseCantieriImpEntry>restObject).impresa);
        } else if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
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
