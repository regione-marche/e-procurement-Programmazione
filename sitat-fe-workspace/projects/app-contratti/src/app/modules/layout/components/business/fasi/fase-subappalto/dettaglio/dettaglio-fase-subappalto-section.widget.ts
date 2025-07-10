import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkFormBuilderField, SdkTextOutput } from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isObject, isString, toString } from 'lodash-es';
import { Observable, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseSubappaltoEntry, InizFaseSubappaltoEntry } from '../../../../../../models/fasi/fase-subappalto.model';
import { FaseSubappaltoService } from '../../../../../../services/fasi/fase-subappalto.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';


@Component({
    templateUrl: `dettaglio-fase-subappalto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioFaseSubappaltoSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `dettaglio-fase-subappalto-section`;

    private iniz: InizFaseSubappaltoEntry;
    private fase: FaseSubappaltoEntry;

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

    private get faseSubappaltoService(): FaseSubappaltoService { return this.injectable(FaseSubappaltoService) }

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

            if (config.code === 'nomest-aggiudicatrice') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    impresa: this.fase.impresaAggiudicatrice
                }
            } else if (config.code === 'nomest-subappaltatrice') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    impresa: this.fase.impresaSubappaltatrice
                }
            }

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    // #endregion

    // #region Protected
    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseSubappaltoEntry, dynamicField?: boolean) => {

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

        if (field.code === 'nomest-aggiudicatrice' && this.iniz.singolaImpresa === true) {
            field.visible = false;
        }

        if(field.code === 'tipo-impresa'){
            if(this.fase.mandanti != null){
                field.data = this.translateService.instant('COMBOBOX.MANDATARIA');
            }else{
                field.data = this.translateService.instant('COMBOBOX.IMPRESA-SINGOLA');
            }
            mapping = false;
        }

        if(field.code === 'nome-impresa'){
            let data  = get(restObject, field.mappingInput);
            if (data != null) {               
                field.data = data;
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
        return Constants.FASE_SUBAPPALTO_TABELLATI;
    }

    protected loadDettaglio = (): Observable<FaseSubappaltoEntry> => {
        return this.getInizObservable().pipe(
            mergeMap(() => this.getDettaglioObservable())
        );
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseSubappaltoEntry> {
        return () => {
            return this.faseSubappaltoService.getFase(codGara, codLotto, progressivo);
        }
    }

    private datiInizializzazioneFaseFactory(codGara: string, codLotto: string): () => Observable<InizFaseSubappaltoEntry> {
        return () => {
            return this.faseSubappaltoService.getDatiInizializzazione(codGara, codLotto);
        }
    }

    private getInizObservable(): Observable<InizFaseSubappaltoEntry> {
        const factory = this.datiInizializzazioneFaseFactory(this.codGara, this.codLotto);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: InizFaseSubappaltoEntry) => {
                    this.iniz = result;
                    return result;
                })
            );
    }

    private getDettaglioObservable(): Observable<FaseSubappaltoEntry> {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseSubappaltoEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion
}
