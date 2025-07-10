import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkBasicButtonInput, SdkDocumentItem, SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isString, remove, set, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseComunicazioneEntry } from '../../../../../../models/fasi/fase-comunicazione-esito.model';
import { FaseComunicazioneEsitoService } from '../../../../../../services/fasi/fase-comunicazione.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';


@Component({
    templateUrl: `dettaglio-com-esito-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioFaseComunicazioneEsitoSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-comunicazione-esito-section`;

    private fase: FaseComunicazioneEntry;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {

        this.loadListaTabellati = true;

        super.onInit();
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get faseComunicazioneEsitoService(): FaseComunicazioneEsitoService { return this.injectable(FaseComunicazioneEsitoService) }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void { }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseComunicazioneEntry, dynamicField?: boolean) => {
        let mapping: boolean = true;

        let keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);


        if (
            field.code === 'data-verb-aggiudicazione'
        ) {

            let esitoValue: number = get(restObject, 'esito.esito');

            if (esitoValue > 1) {
                field.label = 'COMUNICAZIONE-ESITO.PUBBLICAZIONE-DATA.DATA-VERBALE';
            }

            let value = get(restObject, field.mappingInput);
            if (value != null && field.type === 'TEXT') {
                field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                key = field.data;
                mapping = false;
            }
        }

        if (field.code === 'documents-list') {
            let binary: string = get(restObject, field.mappingInput);
            if (binary) {
                let sdkExistingDocuments: Array<SdkDocumentItem> = [
                    {
                        code: 'documento',
                        binary,
                        titolo: 'Download'
                    }
                ];
                set(field, 'documents', sdkExistingDocuments);
            }
            mapping = false;
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
        return Constants.FASE_COMUNICAZIONE_ESITO_TABELLATI;
    }

    protected loadDettaglio = (): Observable<FaseComunicazioneEntry> => {
        let factory = this.dettaglioFaseFactory(this.codGara, this.codLotto);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseComunicazioneEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string): () => Observable<FaseComunicazioneEntry> {
        return () => {
            return this.faseComunicazioneEsitoService.getFase(codGara, codLotto);
        }
    }

    protected loadForm(): void {
        if(this.fase.esito.dataAggiudicazione == null  &&  this.fase.esito.importoAggiudicazione == null && this.fase.esito.fileAllegato == null){
            this.config.body.fields = [this.config.body.fields[0]];           
        } else {
            if(this.fase.esito.dataAggiudicazione == null){
                remove(this.config.body.fields[1].fieldSections, function(currentObject) {
                    return get(currentObject, 'code') === 'data-verb-aggiudicazione';
                });
            }
            if( this.fase.esito.importoAggiudicazione == null){
                remove(this.config.body.fields[1].fieldSections, function(currentObject) {
                    return get(currentObject, 'code') === 'importo-aggiudicazione';
                });
            }
            if(this.fase.esito.fileAllegato == null){
                remove(this.config.body.fields[1].fieldSections, function(currentObject) {
                    return get(currentObject, 'code') === 'documents-list';
                });
            }
        }
        super.loadForm();
        if(this.fase.hasAggiudicazione === true || this.fase?.pubblicata){
            this.buttons.buttons = remove(this.buttons.buttons, (one: SdkBasicButtonInput) =>
                one.code !== 'go-to-update-fase'
            );
            this.buttonsSubj.next(this.buttons);
        }

    }

    // #endregion
}
