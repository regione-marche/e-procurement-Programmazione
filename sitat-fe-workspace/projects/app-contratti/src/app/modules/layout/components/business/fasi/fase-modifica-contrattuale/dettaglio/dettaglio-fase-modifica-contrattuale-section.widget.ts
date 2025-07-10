import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkDocumentItem, SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isString, set, toString } from 'lodash-es';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseVarianteEntry } from '../../../../../../models/fasi/fase-modifica-contrattuale.model';
import { FaseModificaContrattualeService } from '../../../../../../services/fasi/fase-modifica-contrattuale.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';


@Component({
    templateUrl: `dettaglio-fase-modifica-contrattuale-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioFaseModificaContrattualeSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `dettaglio-fase-modifica-contrattuale-section`;

    private fase: FaseVarianteEntry;

    // #endregion

    constructor(injector: Injector, cdr: ChangeDetectorRef) {
        super(injector, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        this.loadListaTabellati = true;

        super.onInit();
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get faseModificaContrattualeService(): FaseModificaContrattualeService { return this.injectable(FaseModificaContrattualeService) }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void { }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseVarianteEntry, dynamicField?: boolean) => {


        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if (
            field.code === 'data-verb-appr' ||
            field.code === 'data-atto-aggiuntivo'
        ) {
            let value = get(restObject, field.mappingInput);
            if (value != null && field.type === 'TEXT') {
                field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                key = field.data;
                mapping = false;
            }
        }

        if(this.gara.pcp){
            const value = get(restObject, field.mappingInput);
            if(this.gara.tipoApp === 3 ||
                this.gara.tipoApp === 4 ||
                this.gara.tipoApp === 20 ||
                this.gara.tipoApp === 21){
                if(field.code === 'imp-progettazione' || field.code === 'numero-giorni-proroga'){
                    field.visible = false;
                    mapping = false;
                }                              
            } else{
                if(field.code === 'imp-finpa' || field.code === 'entr-utenza' || field.code === 'intr-attivo' || field.code === 'imp-opzioni'){
                    field.visible = false;
                    mapping = false;
                }
            }

            if(this.lotto.sottoSoglia){
                if(field.code === 'eform'){
                    field.visible = false;
                    mapping = false;
                }  
            }

            if (field.type === 'DOCUMENTS-LIST' && field.code === 'eform') {
                let existingDocuments: any = get(this.fase, field.mappingInput);
                
                if(existingDocuments != null){
                    let sdkExistingDocuments: Array<SdkDocumentItem> = [{
                        titolo: 'eForm',
                        fileDownloadCallback() {
                            return of(existingDocuments);
                        },
                        code: '1',
                        tipoFile: 'xml',
                        descrizione: 'File eForm'
                    }]
                    set(field, 'documents', sdkExistingDocuments);
                    mapping = false;
                }
            }

            
            if(field.code === 'altre-motivazioni'){
                field.visible = false;
                mapping = false;
            }
            if(field.code === 'cig-nuova-proc'){
                field.visible = false;
            }
            if(field.code === 'motivazioni-modifica-contrattuale-data'){
                field.visible = false;
                mapping = false;
            }
            if(field.code === 'motivo-rev-prezzi') {
                field.visible = false;
                mapping = false;
                field.visibleCondition = null;
            }
            if(field.code === 'cig-nuova-proc') {
                field.visible = false;
                mapping = false;
                field.visibleCondition = null;
            }
            if(field.code === 'imp-modifica' && value == null ){
                field.visible = false;
                mapping = false;
            } 
        } else{
            if(field.code === 'imp-finpa' || field.code === 'entr-utenza' || field.code === 'intr-attivo' || field.code === 'imp-opzioni' || field.code === 'eform'){
                field.visible = false;
                mapping = false;
            }
            if(field.code === 'altre-motivazioni-pcp'){
                field.visible = false;
                mapping = false;
            }
            if(field.code === 'cig-nuova-proc-pcp'){
                field.visible = false;
                mapping = false;
            }
            if(field.code === 'motivazioni-modifica-contrattuale-data-pcp'){
                field.visible = false;
                mapping = false;
            } 
            if(field.code === 'motivo-rev-prezzi-pcp') {
                field.visible = false;
                mapping = false;
                field.visibleCondition = null;
            }
            if(field.code === 'cig-nuova-proc-pcp') {
                field.visible = false;
                mapping = false;
                field.visibleCondition = null;
            }
            if(field.code === 'imp-modifica'){
                field.visible = false;
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
        return Constants.FASE_MODIFICA_CONTRATTUALE_TABELLATI;
    }


    protected loadDettaglio = (): Observable<FaseVarianteEntry> => {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseVarianteEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }


    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseVarianteEntry> {
        return () => {
            return this.faseModificaContrattualeService.getFase(codGara, codLotto, progressivo);
        }
    }

    // #endregion
}
