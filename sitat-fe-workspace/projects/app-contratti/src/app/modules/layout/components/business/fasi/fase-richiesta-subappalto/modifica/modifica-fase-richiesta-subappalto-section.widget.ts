import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { ImpresaEntry } from '@maggioli/app-commons';
import { SdkAutocompleteItem, SdkComboBoxItem, SdkDocumentItem, SdkFormBuilderField, SdkFormBuilderInput, SdkModalOutput } from '@maggioli/sdk-controls';
import { cloneDeep, each, find, get, isEmpty, isObject, isString, map as mapArray, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Observer, Subject, of } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseSubappaltoEntry, InizFaseSubappaltoEntry } from '../../../../../../models/fasi/fase-subappalto.model';
import { CPVSecondario } from '../../../../../../models/gare/gare.model';
import { FaseSubappaltoService } from '../../../../../../services/fasi/fase-subappalto.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';
import { FaseRichiestaSubappaltoService } from 'projects/app-contratti/src/app/modules/services/fasi/fase-richiesta-subappalto.service';



@Component({
    templateUrl: `modifica-fase-richiesta-subappalto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseRichiestaSubappaltoSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `modifica-fase-richiesta-subappalto-section`;

    protected iniz: InizFaseSubappaltoEntry;
    protected fase: FaseSubappaltoEntry;

    protected listaCpvSub: BehaviorSubject<Array<SdkComboBoxItem>>= new BehaviorSubject(null);
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    

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
    
    

    protected get faseRichiestaSubappaltoService(): FaseRichiestaSubappaltoService {
        return this.injectable(FaseRichiestaSubappaltoService);
    }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {     
        if(field != null && field.code === 'cpv'){
            if(field.data != null && field.data.key == '99'){
                let cpvField: any = {
                    code: 'cpv',
                    data: null
                };                
                let componentConfig = this.config;
                componentConfig = {componentConfig,...{data: {buttons:this.config.body.cpvButtons.buttons}}}
                this.modalConfig = {
                    ...this.modalConfig,
                    component: 'cpv-modal-widget',
                    componentConfig: componentConfig,
                    
                };
                this.formBuilderDataSubject.next(cpvField);
                this.modalConfigObs.next(this.modalConfig);
                this.modalConfig.openSubject.next(true);
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
            let listaCpv: Array<SdkComboBoxItem> = mapArray(this.iniz.listaCpv, (one: CPVSecondario) => {
                
                const item: SdkComboBoxItem = {
                    key: one.codCpv,
                    value:   one.descCpv + ' (' + one.codCpv +')'
                };
                return item;
                            
            });
            const otherItem: SdkComboBoxItem = {
                key: '99',
                value: 'Seleziona altro valore'
            };
            listaCpv.push(otherItem);
            this.listaCpvSub.next(listaCpv);   
            field.itemsProvider = () => {
                return this.listaCpvSub
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

        if (field.code === 'nomest-aggiudicatrice' && this.iniz.singolaImpresa === true) {
            field.visible = false;
        }

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
            return this.faseRichiestaSubappaltoService.getFase(codGara, codLotto, progressivo);
        }
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
                    let cpv: CPVSecondario={
                        codCpv: this.fase.idCpv,
                        descCpv: this.fase.descCpv
                    };
                    this.iniz.listaCpv.push(cpv);
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

    public manageModalOutput(_item: SdkModalOutput<any>): void {
        if (isObject(_item) && _item.code === 'modal') {
            if (isObject(_item.data)) {                
                let data: any = _item.data;
                const listaCpv: Array<SdkComboBoxItem> = mapArray(this.iniz.listaCpv, (one: CPVSecondario) => {
                    const item: SdkComboBoxItem = {
                        key: one.codCpv != null ? one.codCpv : '',
                        value:   one.descCpv != null ? one.descCpv : '' + ' (' + one.codCpv != null ? one.codCpv : ''+')'
                    };
                    return item;
                });
                const newItem: SdkComboBoxItem = {
                    key: data.item.data,
                    value: data.item.label + ' (' +data.item.data+')'
                };
                const otherItem: SdkComboBoxItem = {
                    key: '99',
                    value: 'Seleziona altro valore'
                };
                listaCpv.push(newItem);
                listaCpv.push(otherItem);
                       
                let cpvField: any = {
                    code: 'cpv',
                    data: newItem
                };  
                this.formBuilderDataSubject.next(cpvField);
                this.listaCpvSub.next(listaCpv);
                
            }
        }
    }


    
    protected datiInizializzazioneFaseFactory(codGara: string, codLotto: string): () => Observable<InizFaseSubappaltoEntry> {
        return () => {
            return this.faseRichiestaSubappaltoService.getDatiInizializzazione(codGara, codLotto);
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

    protected scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    // #endregion
}
