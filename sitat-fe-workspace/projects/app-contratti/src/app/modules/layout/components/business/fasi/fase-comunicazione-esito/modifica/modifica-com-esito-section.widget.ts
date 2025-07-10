import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkDocumentItem, SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, find, findIndex, get, isEmpty, isObject, isString, set, toNumber, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseComunicazioneEntry } from '../../../../../../models/fasi/fase-comunicazione-esito.model';
import { FaseComunicazioneEsitoService } from '../../../../../../services/fasi/fase-comunicazione.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';


@Component({
    templateUrl: `modifica-com-esito-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaComunicazioneEsitoSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `modifica-comunicazione-esito-section`;

    private fase: FaseComunicazioneEntry;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

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

    private get faseComunicazioneEsitoService(): FaseComunicazioneEsitoService { return this.injectable(FaseComunicazioneEsitoService) }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        if (field.code === 'esito-procedura') {
            let currentDataSection: SdkFormBuilderField = find(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === 'pubblicazione-data');
            let currentDataField: SdkFormBuilderField = find(currentDataSection.fieldSections, (one: SdkFormBuilderField) => one.code === 'data-verb-aggiudicazione');
            let toBeUpdated = {
                code: 'data-verb-aggiudicazione',
                label: '',
                data: currentDataField.data != null ? currentDataField.data : null
            };
            if (isObject(field.data)) {
                let value: number = toNumber(get(field, 'data.key'));
                toBeUpdated.label = value > 1 ? 'COMUNICAZIONE-ESITO.PUBBLICAZIONE-DATA.DATA-VERBALE' : 'COMUNICAZIONE-ESITO.PUBBLICAZIONE-DATA.DATA-VERB-AGGIUDICAZIONE';
            } else {
                toBeUpdated.label = 'COMUNICAZIONE-ESITO.PUBBLICAZIONE-DATA.DATA-VERB-AGGIUDICAZIONE';
            }
            this.formBuilderDataSubject.next(toBeUpdated);
        } else if (field.code === 'documents-list') {
            setTimeout(() => {
                this.formBuilderConfigObs.next({
                    fields: []
                });

                let sectionIndex: number = findIndex(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === 'pubblicazione-data');
                let fieldIndex: number = findIndex(this.formBuilderConfig.fields[sectionIndex].fieldSections, (one: SdkFormBuilderField) => one.code === 'doucment-field');
                this.formBuilderConfig.fields[sectionIndex].fieldSections[fieldIndex].visible = isEmpty(field.documents);
                this.formBuilderConfigObs.next(this.formBuilderConfig);
            });
        }
    }

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
        } else if (field.code === 'doucment-field') {
            if (isEmpty(this.fase.esito.binary)) {
                field.visible = true;
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

    // #endregion
}
