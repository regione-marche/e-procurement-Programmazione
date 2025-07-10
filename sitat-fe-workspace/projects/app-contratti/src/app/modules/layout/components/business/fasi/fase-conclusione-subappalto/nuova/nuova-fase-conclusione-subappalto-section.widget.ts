import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
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
import { ImpresaEntry } from '@maggioli/app-commons';
import { IDictionary, SdkProviderService } from '@maggioli/sdk-commons';



@Component({
    templateUrl: `nuova-fase-conclusione-subappalto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseConclusioneSubappaltoSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `nuova-fase-conclusione-subappalto-section`;
    
    protected iniz: InizFaseSubappaltoEntry;
    protected fase: FaseSubappaltoEntry;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) {
        super(inj, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        // set update state
        this.setUpdateState(true);
        this.loadListaTabellati = true;        
        super.onInit();
    }


    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        if(field.code == 'num-suba'){
            if (field.data != null) {
                this.iniz.listaSuba.forEach((fase: FaseSubappaltoEntry) => {
                    if(field.data.key === fase.num.toString()){                       
                        
                        let oggetto = fase.oggetto;
                        if(oggetto != null){
                            this.formBuilderDataSubject.next({
                                code: 'oggetto-subappalto',
                                data: oggetto
                            });
                        } else{
                            this.formBuilderDataSubject.next({
                                code: 'oggetto-subappalto',
                                data: null
                            });
                        }
                        

                        let impresaAggiudicatrice = fase.impresaAggiudicatrice?.ragioneSociale;
                        if(impresaAggiudicatrice != null){
                            this.formBuilderDataSubject.next({
                                code: 'nomest-aggiudicatrice',
                                data: impresaAggiudicatrice
                            });
                        }else{
                            this.formBuilderDataSubject.next({
                                code: 'nomest-aggiudicatrice',
                                data: null
                            });
                        }

                        let importoPresunto = fase.importoPresunto;
                        if(importoPresunto != null){
                            this.formBuilderDataSubject.next({
                                code: 'importo-presunto',
                                data: importoPresunto
                            });
                        }else{
                            this.formBuilderDataSubject.next({
                                code: 'importo-presunto',
                                data: null
                            });
                        }

                        let impresaSubappaltatrice = fase.impresaSubappaltatrice?.ragioneSociale;
                        if(impresaSubappaltatrice != null){
                            this.formBuilderDataSubject.next({
                                code: 'nomest-subappaltatrice',
                                data: impresaSubappaltatrice
                            });
                        }else{
                            this.formBuilderDataSubject.next({
                                code: 'nomest-subappaltatrice',
                                data: null
                            });
                        }

                        let idCategoria = fase.idCategoria;
                        if(idCategoria != null){
                            this.formBuilderDataSubject.next({
                                code: 'id-categoria',
                                data: idCategoria
                            });
                        }else{
                            this.formBuilderDataSubject.next({
                                code: 'id-categoria',
                                data: null
                            });
                        }

                        let idCpv = fase.idCpv;
                        if(idCpv != null){
                            this.formBuilderDataSubject.next({
                                code: 'cpv',
                                data: idCpv
                            });
                        }else{
                            this.formBuilderDataSubject.next({
                                code: 'cpv',
                                data: null
                            });
                        }

                        let descCpv = fase.descCpv;
                        if(descCpv != null){
                            this.formBuilderDataSubject.next({
                                code: 'descrizione-cpv',
                                data: descCpv
                            });
                        }else{
                            this.formBuilderDataSubject.next({
                                code: 'descrizione-cpv',
                                data: null
                            });
                        }
                        
                        
                        let existingDocuments: any = get(fase, 'dgue');            
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
                            this.formBuilderDataSubject.next({
                                code: 'dgue',
                                documents: sdkExistingDocuments
                            });
                        } else{
                            this.formBuilderDataSubject.next({
                                code: 'dgue',
                                documents: null
                            });
                        }
            
                        let dataAuth = fase.dataAuth;
                        if(dataAuth != null){
                            this.formBuilderDataSubject.next({
                                code: 'data-autorizzazione',
                                data: this.dateHelper.format(new Date(dataAuth), this.config.locale.dateFormat),
                                visible: true
                            });
                        } else{
                            this.formBuilderDataSubject.next({
                                code: 'data-autorizzazione',
                                visible: false
                            });
                        }


                        let motivoMancatoSub = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, this.getFieldByCode('motivo-mancato-sub'), fase.motivoMancatoSub, true);
                        if(motivoMancatoSub != null && motivoMancatoSub.length > 0 && motivoMancatoSub[0].data > 0){
                            this.formBuilderDataSubject.next({
                                code: 'motivo-mancato-sub',
                                data: motivoMancatoSub[0].data,
                                visible: true
                            });
                        } else{
                            this.formBuilderDataSubject.next({
                                code: 'motivo-mancato-sub',
                                visible: false
                            });
                        }
                        
                        
                        let esito = '';
                        
                        if(fase.dataAuth != null){
                            esito = 'Positivo';
                        } else if(fase.motivoMancatoSub != null){
                            esito = 'Negativo';
                        }

                        this.formBuilderDataSubject.next({
                            code: 'esito',
                            data:esito
                        });
                        
                        this.markForCheck();
                    }                    
                });
            }
        } else if(field.code == 'data-ultimazione'){
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
             
        let tipoImpresaCombo = {
            code: "tipo-impresa",
            data : {
                key: '2',
                value: this.translateService.instant('COMBOBOX.MANDATARIA')
            }
        };       
        if(mandantiGroup.fieldGroups.length === 0){
          
            tipoImpresaCombo.data ={
                key: '1',
                value: this.translateService.instant('COMBOBOX.IMPRESA-SINGOLA')
            }                         
            
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

        if(field.code === 'num-suba'){     
            
            if(this.iniz != null && this.iniz.listaSuba.length == 1){
                field.label = 'FASE-SUBAPPALTO.SUBAPPALTO-DATA.SUBAPPALTO-SELEZIONATO';
                field.data = this.iniz.listaSuba[0].num;
                field.type = 'TEXT';
                mapping = false;
            } else{
                let list: Array<SdkComboBoxItem> = new Array();
                this.iniz.listaSuba.forEach(function(key) {
                list.push({
                        key: toString(key.num),
                        value: 'Subappalto '+toString(key.num)
                    });
                });
                field.itemsProvider = () => {
                    return of(list as Array<SdkComboBoxItem>);
                }             
                mapping = false;
            }
            
        }

        if(this.iniz != null && this.iniz.listaSuba.length == 1){
            if(field.code === 'data-autorizzazione'){
                if(field.data == null || isEmpty(field.data)){
                    field.visible = false;
                    mapping = false;
                }
            }
    
            if(field.code === 'motivo-mancato-sub'){
                if(field.data == null || isEmpty(field.data)){
                    field.visible = false;
                    mapping = false;
                }
            }
    
            if(field.code === 'esito'){
                if(this.fase != null){
                    if(this.fase.dataAuth != null){
                        field.data = 'Positivo';
                        mapping = false;
                    }
        
                    if(this.fase.motivoMancatoSub != null){
                        field.data = 'Negativo';
                        mapping = false;
                    }
                }
                
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

    protected getDettaglioObservable(): Observable<FaseSubappaltoEntry> {
        if(this.iniz.listaSuba != null && this.iniz.listaSuba.length == 1){
            this.fase = this.iniz.listaSuba[0];
            return of(this.fase);
        }
        return of(undefined)
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

    protected getFieldByCode(fieldCode: string): SdkFormBuilderField {
        const section: SdkFormBuilderField = find(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === 'subappalto-data');
        const field: SdkFormBuilderField = find(section.fieldSections, (one: SdkFormBuilderField) => one.code === fieldCode);
        return field;
    }

    // #endregion

    // #region Private

    protected datiInizializzazioneFaseFactory(codGara: string, codLotto: string): () => Observable<InizFaseSubappaltoEntry> {
        return () => {
            return this.faseConclusioneSubappaltoService.getDatiInizializzazione(codGara, codLotto);
        }
    }

    // #endregion

    // #region Getters    

    protected get faseConclusioneSubappaltoService(): FaseConclusioneSubappaltoService { return this.injectable(FaseConclusioneSubappaltoService) }

    // #endregion
}
