import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { ImpresaEntry } from '@maggioli/app-commons';
import { SdkAutocompleteItem, SdkComboBoxItem, SdkDocumentItem, SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, each, find, get, isEmpty, isObject, isString, map as mapArray, set, toString } from 'lodash-es';
import { Observable, Observer, of } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseSubappaltoEntry, InizFaseSubappaltoEntry } from '../../../../../../models/fasi/fase-subappalto.model';
import { CPVSecondario } from '../../../../../../models/gare/gare.model';
import { FaseSubappaltoService } from '../../../../../../services/fasi/fase-subappalto.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';
import { FaseConclusioneSubappaltoService } from 'projects/app-contratti/src/app/modules/services/fasi/fase-conclusione-subappalto.service';



@Component({
    templateUrl: `modifica-fase-conclusione-subappalto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseConclusioneSubappaltoSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `modifica-fase-conclusione-subappalto-section`;

    protected iniz: InizFaseSubappaltoEntry;
    protected fase: FaseSubappaltoEntry;

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
    
    

    protected get faseConclusioneSubappaltoService(): FaseConclusioneSubappaltoService { return this.injectable(FaseConclusioneSubappaltoService) }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        if(field.code == 'data-ultimazione'){
            if(field.data != null){
                this.formBuilderDataSubject.next({
                    code: 'esecuzione-suba',
                    data: {
                        key: '1',
                        value:'Eseguito'
                    }
                });
            } else{
                this.formBuilderDataSubject.next({
                    code: 'esecuzione-suba',
                    data: {
                        key: '2',
                        value:'Non eseguito'
                    }
                });
            }
        }
    }


    public manageDeletePanel(field: SdkFormBuilderField): void {
        let section: SdkFormBuilderField = find(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === 'subappalto-data');
        let mandantiGroup: SdkFormBuilderField = find(section.fieldSections, (one: SdkFormBuilderField) => one.code === 'mandanti-group');
        let tipoImpresaHidden = {
            code: "tipo-impresa",
            data: '2'
        };        
        let tipoImpresaCombo = {
            code: "tipo-impresa-combo",
            data : {
                key: '2',
                value: this.translateService.instant('COMBOBOX.MANDATARIA')
            }
        };       
        if(mandantiGroup.fieldGroups.length === 0){
            tipoImpresaHidden.data = '1';
            tipoImpresaCombo.data ={
                key: '1',
                value: this.translateService.instant('COMBOBOX.IMPRESA-SINGOLA')
            }                         
            this.formBuilderDataSubject.next(tipoImpresaHidden);
            this.formBuilderDataSubject.next(tipoImpresaCombo);
            
        }

        
    }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseSubappaltoEntry, dynamicField?: boolean) => {

        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);
     
        if(field.code === 'tipo-impresa-text' || field.code === 'tipo-impresa-combo'){
            if(this.fase.mandanti == null){                    
                field.data = this.translateService.instant('COMBOBOX.IMPRESA-SINGOLA')                       
            } else{               
                field.data = this.translateService.instant('COMBOBOX.MANDATARIA')                             
            }
            mapping = false;
        }
        
        if(field.code === 'tipo-impresa-combo'){
            if(this.fase.mandanti == null){    
                field.data ={
                    key: '1',
                    value: this.translateService.instant('COMBOBOX.IMPRESA-SINGOLA')
                }                                          
            } else{               
                field.data = {
                    key: '2',
                    value: this.translateService.instant('COMBOBOX.MANDATARIA')
                }                    
            }
            mapping = false;
        }
                       

        
        

        if (field.code.startsWith('data-') && field.type === 'TEXT') {
            const value = get(restObject, field.mappingInput);
            if (value != null) {
                field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                key = field.data;
                mapping = false;
            }
        } else if (field.type === 'AUTOCOMPLETE') {
            if(field.code === 'nome-impresa'){
                let data : any = restObject;
                if (isObject(data) && !isEmpty(data)) {               
                    let codiceImpresa = get(data, 'codiceImpresa');
                    let ragioneSociale = get(data, 'ragioneSociale'); 
                    let item: SdkAutocompleteItem = {
                        ...data,
                        text: `${codiceImpresa} ${ragioneSociale}`,
                        _key: codiceImpresa
                    };
                    set(field, 'data', item);
                    mapping = false;
                }
            } else{
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
            
        }

        if(field.code === 'cpv'){
            field.itemsProvider = () => {
                return new Observable<Array<SdkComboBoxItem>>((ob: Observer<Array<SdkComboBoxItem>>) => {
                    const listaCpv: Array<SdkComboBoxItem> = mapArray(this.iniz.listaCpv, (one: CPVSecondario) => {
                        const item: SdkComboBoxItem = {
                            key: one.codCpv,
                           value:  one.descCpv + ' (' +one.codCpv+')'
                        };
                        return item;
                    });
                    ob.next(listaCpv);
                    ob.complete();                    
                });
            };
            each(this.iniz.listaCpv, (element: CPVSecondario) => {
                if (restObject.idCpv === element.codCpv) {
                    const data: SdkComboBoxItem = {
                        key: element.codCpv,
                        value:  element.descCpv + ' (' +element.codCpv+')'
                    };
                    field.data = data;
                } 
            });
            mapping = false;
        }

        /*if (field.code === 'nomest-aggiudicatrice' && this.iniz.singolaImpresa === true) {
            field.visible = false;
        }*/

        if (field.type === 'DOCUMENTS-LIST' && field.code === 'dgue') {
            let existingDocuments: any = get(this.fase, field.mappingInput);
            
            if(existingDocuments != null){
                let sdkExistingDocuments: Array<SdkDocumentItem> = [{
                    titolo: 'dgue',
                    fileDownloadCallback() {
                        return of(existingDocuments);
                    },
                    code: '1',
                    tipoFile: 'xml',
                    descrizione: 'File DGUE'
                }]
                set(field, 'documents', sdkExistingDocuments);
                mapping = false;
            }
        }
        
        if(field.code === 'data-autorizzazione'){
            if(this.fase.dataAuth == null){
                field.visible = false;
                mapping = false;
            }
        }

        if(field.code === 'motivo-mancato-sub'){
            if(this.fase.motivoMancatoSub == null){
                field.visible = false;
                mapping = false;
            }
        }

        if(field.code === 'esito'){
            if(this.fase.dataAuth != null){
                field.data = 'Positivo';
                mapping = false;
            }

            if(this.fase.motivoMancatoSub != null){
                field.data = 'Negativo';
                mapping = false;
            }
        }

        if(!this.gara.pcp){
            if(field.code === 'data-ultimazione'){                
                field.visible = false;
                mapping = false;                
            }
            if(field.code === 'motivo-mancata-esec'){                
                field.visible = false;
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

    protected dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseSubappaltoEntry> {
        return () => {
            return this.faseConclusioneSubappaltoService.getFase(codGara, codLotto, progressivo);
        }
    }

    protected datiInizializzazioneFaseFactory(codGara: string, codLotto: string): () => Observable<InizFaseSubappaltoEntry> {
        return () => {
            return this.faseConclusioneSubappaltoService.getDatiInizializzazione(codGara, codLotto);
        }
    }

    protected getInizObservable(): Observable<InizFaseSubappaltoEntry> {
        const factory = this.datiInizializzazioneFaseFactory(this.codGara, this.codLotto);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: InizFaseSubappaltoEntry) => {
                    this.iniz = result;
                    return result;
                })
            );
    }

    protected getDettaglioObservable(): Observable<FaseSubappaltoEntry> {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseSubappaltoEntry) => {                                        
                    if(result.mandanti == null){    
                        set(result, 'tipoImpresaHidden', '1');                                                    
                    } else{               
                        set(result, 'tipoImpresaHidden', '2');                          
                    }                    
                    this.fase = result;
                    return result;
                })
            );
    }

    protected manageExecutionProvider = (obj: any) => {
       
        if (isObject(obj)) {
            if (get(obj, 'action') === 'open-sidebar' && isObject(get(this.config.body, 'sidebar'))) {
                this.sidebarConfig.componentConfig = obj;
                this.sidebarConfigObs.next(this.sidebarConfig);
                this.sidebarConfig.openSubject.next(true);
            }
            if (get(obj, 'errorMandante') === true ) {
                this.sdkMessagePanelService.showError(this.messagesPanel, [
                    {
                        message: this.translateService.instant('FASE-SUBAPPALTO.SUBAPPALTO-DATA.ERRORE-MANDANTI-NON-PRESENTI')                 
                    }
                ]);
                this.scrollToMessagePanel(this.messagesPanel)
            }   
            if (get(obj, 'errorMandataria') === true ) {
                this.sdkMessagePanelService.showError(this.messagesPanel, [
                    {
                        message: this.translateService.instant('FASE-SUBAPPALTO.SUBAPPALTO-DATA.ERRORE-MANDATARIA-PRESENTE')                 
                    }
                ]);
                this.scrollToMessagePanel(this.messagesPanel)
            }            
        }
    }

    protected scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    // #endregion
}
