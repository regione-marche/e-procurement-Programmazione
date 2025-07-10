import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkComboBoxItem, SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, find, get, isEmpty, isString, set, toString } from 'lodash-es';
import { Observable, map, of } from 'rxjs';

import { Constants } from '../../../../../../../app.constants';
import { FaseSospensioneEntry, InizFaseRipresaPrestazioneEntry } from '../../../../../../models/fasi/fase-sospensione.model';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';
import { FaseRipresaPrestazioneService } from 'projects/app-contratti/src/app/modules/services/fasi/fase-ripresa-prestazione.service';
import { InizFaseInizialeContrattoEntry } from 'projects/app-contratti/src/app/modules/models/fasi/fase-iniziale.model';

@Component({
    templateUrl: `nuova-fase-ripresa-prestazione-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseRipresaPrestazioneSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `nuova-fase-ripresa-prestazione-section`;

    private fase: InizFaseRipresaPrestazioneEntry;

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
        if(field.code == 'num-sosp'){
            if (field.data != null) {
                this.fase.listaSosp.forEach((fase: FaseSospensioneEntry) => {
                    if(fase.num.toString() === field.data.key){

                        
                        let sosParzialeValue = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, this.getFieldByCode('sosp-parziale'), fase.sospParziale, true);
                        if(sosParzialeValue != null && sosParzialeValue.length > 0){
                            this.formBuilderDataSubject.next({
                                code: 'sosp-parziale',
                                data: sosParzialeValue[0].data
                            });
                        } else{
                            this.formBuilderDataSubject.next({
                                code: 'sosp-parziale',
                                data: null
                            });
                        }
                        
            
                        let dataVerbSospValue: string;
                        let value = fase.dataVerbSosp;  
                        if (value != null) {
                            dataVerbSospValue = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);       
                            this.formBuilderDataSubject.next({
                                code: 'data-verb-sosp',
                                data: dataVerbSospValue
                            });                             
                        }else{
                            this.formBuilderDataSubject.next({
                                code: 'data-verb-sosp',
                                data: null
                            });  
                        }
                       
            
                        let motivoSospValue  = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, this.getFieldByCode('motivo-sosp'), fase.motivoSosp.toString(), true);
                        if(motivoSospValue != null && motivoSospValue.length > 0 && motivoSospValue[0].data != null){
                            this.formBuilderDataSubject.next({
                                code: 'motivo-sosp',
                                data: motivoSospValue[0].data
                            });
                        } else{
                            this.formBuilderDataSubject.next({
                                code: 'motivo-sosp',
                                data: null
                            });
                        }
                        
                    }
                });
            }

            
        }
    }

    private getFieldByCode(fieldCode: string): SdkFormBuilderField {
        const section: SdkFormBuilderField = find(this.formBuilderConfig.fields, (one: SdkFormBuilderField) => one.code === 'sospensione-data');
        const field: SdkFormBuilderField = find(section.fieldSections, (one: SdkFormBuilderField) => one.code === fieldCode);
        return field;
    }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseSospensioneEntry, dynamicField?: boolean) => {

        let mapping: boolean = true;
        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        // TODO

        
            

        if(field.code === 'num-sosp'){     

            if(this.fase.listaSosp != null && this.fase.listaSosp.length == 1){
                field.label = 'FASE-SOSPENSIONE.SOSPENSIONE.SOSPENSIONE-SELEZIONATA';
                field.data = this.fase.listaSosp[0].num;
                field.type = 'TEXT';
                mapping = false;
            } else{
                let list: Array<SdkComboBoxItem> = new Array();
                this.fase.listaSosp.forEach(function(key) {
                list.push({
                      key: toString(key.num),
                      value: 'Sospensione '+toString(key.num)
                  });
                });
                field.itemsProvider = () => {
                    return of(list as Array<SdkComboBoxItem>);
                }             
                mapping = false;
            }

            
        }

        if(field.code === 'sosp-parziale'){  
            if(this.fase.listaSosp != null && this.fase.listaSosp.length == 1){                
                field.data = this.fase.listaSosp[0].sospParziale;                
                key = this.fase.listaSosp[0].sospParziale?.toString();
                mapping = false;
            }
        }
        if(field.code === 'data-verb-sosp'){  
            if(this.fase.listaSosp != null && this.fase.listaSosp.length == 1){                                 
                let value = this.fase.listaSosp[0].dataVerbSosp;  
                if (value != null && field.type === 'TEXT') {
                    field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);            
                    mapping = false;
                }                          
            }
        }
        if(field.code === 'motivo-sosp'){  
            if(this.fase.listaSosp != null && this.fase.listaSosp.length == 1){                
                field.data = this.fase.listaSosp[0].motivoSosp;                
                key = toString(this.fase.listaSosp[0].motivoSosp);
                mapping = false;
            }            
        }     
        
        if(field.code === 'flag-supero-tempo'){
            if(this.fase.listaSosp != null && this.fase.listaSosp.length == 1){                
                field.data = this.fase.listaSosp[0].flagSuperoTempo;                
                key = this.fase.listaSosp[0].flagSuperoTempo;

                /*if(field.data == '1'){
                    set(field, 'showHelp',false);
                    set(field, 'helpDescription',null);                  
                }*/
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
        return Constants.FASE_SOSPENSIONE_TABELLATI;
    }

    protected loadDettaglio = (): Observable<any> => {
        const factory = this.datiInizializzazioneFaseFactory(this.codGara, this.codLotto);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: InizFaseRipresaPrestazioneEntry) => {
                    this.fase = result;                    
                    return this.fase;
                })
            );
    }

    // #region Private

    private datiInizializzazioneFaseFactory(codGara: string, codLotto: string): () => Observable<InizFaseRipresaPrestazioneEntry> {
        return () => {
            return this.faseRipresaPrestazioneService.getDatiInizializzazione(codGara, codLotto);
        }
    }

    // #endregion
    
    // #region Private

    private get faseRipresaPrestazioneService(): FaseRipresaPrestazioneService { return this.injectable(FaseRipresaPrestazioneService) }

    // #endregion
}
