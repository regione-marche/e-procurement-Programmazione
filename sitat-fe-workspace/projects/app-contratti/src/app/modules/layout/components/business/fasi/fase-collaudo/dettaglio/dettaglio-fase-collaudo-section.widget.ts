import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, filter, find, get, isEmpty, isString, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseCollaudoEntry } from '../../../../../../models/fasi/fase-collaudo.model';
import { FaseCollaudoService } from '../../../../../../services/fasi/fase-collaudo.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';
import { GaraEntry } from '@maggioli/app-commons';


@Component({
    templateUrl: `dettaglio-fase-collaudo-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioFaseCollaudoSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `dettaglio-fase-collaudo-section`;

    private fase: FaseCollaudoEntry;

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

    private get faseCollaudoService(): FaseCollaudoService { return this.injectable(FaseCollaudoService) }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void { }

    // #endregion

    // #region Protected
    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseCollaudoEntry, dynamicField?: boolean) => {

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

        if(!this.gara.pcp){
            if(field.code == 'imp-non-assog'){
                field.visible = false;
                mapping = false;
            }
            if(field.code == 'imp-finale-opzioni'){
                field.visible = false;
                mapping = false;
            }
            if(field.code == 'imp-finale-ripetizioni'){
                field.visible = false;
                mapping = false;
            }
            if(field.code == 'num-riserve'){
                field.visible = false;
                mapping = false;
            }
            if(field.code == 'oneri-derivanti'){
                field.visible = false;
                mapping = false;
            }
            if(field.code == 'scost-imp-prev'){
                field.visible = false;
                mapping = false;
            }
        }
        if(this.gara.pcp){
            const value = get(restObject, field.mappingInput);
            if(field.code == 'lavori-estesi'){
                field.visible = false;
                mapping = false;
            }            

            /* rimossi temporameamente per err40
            issue anac https://github.com/anticorruzione/npa/issues/1192 */
            if(field.code == 'amm-num-dadef'){
                field.visible = false;
                mapping = false;
            } 

            if(field.code == 'amm-num-definitive'){
                field.visible = false;
                mapping = false;
            } 

            /** */

            if(field.code == 'scost-imp-prev' && value == null){
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
        return Constants.FASE_COLLAUDO_TABELLATI;
    }

    protected loadDettaglio = (): Observable<FaseCollaudoEntry> => {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseCollaudoEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    protected elaborateGara = (result: GaraEntry) => {
        this.gara = result;
        if(this.gara.pcp){
            this.config.body.fields = filter(this.config.body.fields, (one) => one.code !== 'ulteriori-riserve-in-via-arbitrale-data' 
            && one.code !== 'ulteriori-riserve-in-via-giudiziale-data'
            && one.code !== 'ulteriori-riserve-in-via-transattiva-data');        
            
            find(this.config.body.fields, (one: SdkFormBuilderField) => one.code === 'ulteriori-riserve-in-via-amministrativa-data' ? one.label='FASE-COLLAUDO.ULTERIORI-RISERVE-IN-VIA-AMMINISTRATIVA.ULTERIORI-RISERVE-IN-VIA-AMMINISTRATIVA-PCP' : '');            

            this.config.menuTabs = filter(this.config.menuTabs, (one) => one.code !== 'dettaglio-inc-prof-fase-collaudo');
            this.menuTabs = this.config.menuTabs;
        }
    }

    // #endregion

    // #region Private
    
    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseCollaudoEntry> {
        return () => {
            return this.faseCollaudoService.getFase(codGara, codLotto, progressivo);
        }
    }

    // #endregion
}
