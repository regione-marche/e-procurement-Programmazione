import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Injector,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import {
    RupEntry,
} from '@maggioli/app-commons';
import {
    SdkFormBuilderField,
    SdkModalOutput,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isObject, isString, set, toString } from 'lodash-es';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';

import { FaseIncarichiProfessionaliService } from 'projects/app-contratti/src/app/modules/services/fasi/fase-incarichi-professionali.service';
import { Constants } from '../../../../../../../app.constants';
import { IncarichiProfEntry, ResponseIncarichiProfListEntry } from '../../../../../../models/incarichi-professionali/incarichi-professionali.model';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';

@Component({
    templateUrl: `dettaglio-fase-inc-prof-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioFaseIncarichiProfessionaliSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-fase-incarichi-professionali-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    private fase: IncarichiProfEntry;

    // #endregion

    constructor(injector: Injector, cdr: ChangeDetectorRef) {
        super(injector, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        this.loadListaTabellati = true;

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

    private get faseIncarichiProfessionaliService(): FaseIncarichiProfessionaliService { return this.injectable(FaseIncarichiProfessionaliService) }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void { }

    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: config.modalComponent,
                componentConfig: config.modalComponentConfig
            };

            if (config.code === 'nomtec') {
                this.modalConfig.componentConfig = {
                    gara: this.gara,
                    ...this.modalConfig.componentConfig,
                    selectedItem: config.textModalContent,
                    readOnly: true
                }
            }

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public manageModalOutput(item: SdkModalOutput<any>): void {
        
    }

    // #endregion

    // #region Protected
    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: IncarichiProfEntry, dynamicField?: boolean) => {

        let mapping: boolean = true;

        let keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if (field.code === 'nomtec') {
            if (isObject(get(restObject, field.mappingInput))) {
                let data: RupEntry = get(restObject, field.mappingInput);
                set(field, 'data', `${data.nominativo} (${data.cf})`);
                set(field, 'textModalContent', data);
                mapping = false;
            }
        }

        if (
            field.code === 'data-aff-prog-esterna' ||
            field.code === 'data-cons-prog-esterna'
        ) {
            let value = get(restObject, field.mappingInput);
            if (value != null && field.type === 'TEXT') {
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
        return Constants.FASE_INCARICHI_PROFESSIONALI;
    }

    protected loadDettaglio = (): Observable<any> => {
        return this.getDettaglioObservable();
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<IncarichiProfEntry> {
        return () => {
            return this.faseIncarichiProfessionaliService.getFase(codGara, codLotto, progressivo);
        }
    }

    private getDettaglioObservable(): Observable<any> {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: ResponseIncarichiProfListEntry) => {                    
                    return { incarichi :result.incarichi, pubblicata: result.pubblicata  };
                })
            );
    }

    // #endregion
}

