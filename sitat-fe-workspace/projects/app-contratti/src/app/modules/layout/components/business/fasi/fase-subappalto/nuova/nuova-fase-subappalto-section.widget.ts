import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkAutocompleteItem, SdkComboBoxItem, SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, find, get, isEmpty, isObject, isString, map as mapArray, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Observer } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { InizFaseSubappaltoEntry } from '../../../../../../models/fasi/fase-subappalto.model';
import { CPVSecondario } from '../../../../../../models/gare/gare.model';
import { FaseSubappaltoService } from '../../../../../../services/fasi/fase-subappalto.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';



@Component({
    templateUrl: `nuova-fase-subappalto-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseSubappaltoSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `nuova-fase-subappalto-section`;

    protected fase: InizFaseSubappaltoEntry;
    protected listaCpvSub: BehaviorSubject<Array<SdkComboBoxItem>>= new BehaviorSubject(null);

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

    protected onUpdateState(state: boolean): void { }

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

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: InizFaseSubappaltoEntry, dynamicField?: boolean) => {
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
        if (field.type === 'AUTOCOMPLETE') {
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
            }             
        }

        if(field.code === 'cpv'){                                         
            let listaCpv: Array<SdkComboBoxItem> = mapArray(this.fase.listaCpv, (one: CPVSecondario) => {
                
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
            mapping = false;          
                     
        }

        if (field.code === 'nomest-aggiudicatrice' && restObject.singolaImpresa === true) {
            field.visible = false;
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

    protected loadDettaglio = (): Observable<any> => {
        const factory = this.datiInizializzazioneFaseFactory(this.codGara, this.codLotto);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: InizFaseSubappaltoEntry) => {
                    this.fase = result;
                    return this.fase;
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

    // #region Private

    protected datiInizializzazioneFaseFactory(codGara: string, codLotto: string): () => Observable<InizFaseSubappaltoEntry> {
        return () => {
            return this.faseSubappaltoService.getDatiInizializzazione(codGara, codLotto);
        }
    }

    // #endregion

    // #region Getters

    protected get faseSubappaltoService(): FaseSubappaltoService { return this.injectable(FaseSubappaltoService) }

    // #endregion
}
