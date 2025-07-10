import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import {
    SdkComboBoxItem,
    SdkFormBuilderField
} from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isString, toString } from 'lodash-es';
import { map, Observable, Observer } from 'rxjs';

import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';
import { InizFaseVariazioneAggiudicatariEntry } from 'projects/app-contratti/src/app/modules/models/fasi/fase-variazione-aggiudicatari.model';
import { FaseVariazioneAggiudicatariService } from 'projects/app-contratti/src/app/modules/services/fasi/fase-variazione-aggiudicatari.service';

@Component({
    templateUrl: `nuovo-fase-variazione-aggiudicatari-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovaFaseVariazioneAggiudicatariSectionWidget extends FaseAbstractSectionWidget {


    // #region Variables

    @HostBinding('class') classNames = `nuova-fase-variazione-aggiudicatari-section`;

    private iniz: Array<any>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

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

    public manageOutputField(field: SdkFormBuilderField): void { }



    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: any, dynamicField?: boolean) => {
        let mapping: boolean = true;

        let keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if (field.code === 'id-partecipante') {
            field.itemsProvider = () => {
                return new Observable<Array<SdkComboBoxItem>>((ob: Observer<Array<SdkComboBoxItem>>) => {
                    const listaIdPartecip: Array<SdkComboBoxItem> = this.iniz.map((one:any) => ({
                        key: one.idPartecipante,
                        value: one.ragioneSociale
                    }));
                    ob.next(listaIdPartecip);
                    ob.complete();
                });
            };          
            if(this.iniz.length == 1){
                field.itemsProvider = undefined;
                field.type = 'TEXT';
                if(this.iniz[0].tipoAgg == 1){
                    field.data = 'RTI '+this.iniz[0].ragioneSociale;
                } else{
                    field.data = this.iniz[0].ragioneSociale;
                }              
            }  
            mapping = false;
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

    private getInizObservable(): Observable<Array<String>> {
        const factory = this.datiInizializzazioneFaseFactory(this.codGara, this.codLotto);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: Array<string>) => {
                    this.iniz = result;                                        
                    return result;
                })
            );
    }

    protected tabellati(): Array<string> {
        return undefined;
    }

    protected loadDettaglio = (): Observable<any> => {
        return this.getInizObservable();
    }

    

    private datiInizializzazioneFaseFactory(codGara: string, codLotto: string): () => Observable<InizFaseVariazioneAggiudicatariEntry> {
        return () => {
            return this.faseVariazioneAggiudicatariService.getDatiInizializzazione(codGara, codLotto);
        }
    }


    // #endregion

    // #region Private

    private get faseVariazioneAggiudicatariService(): FaseVariazioneAggiudicatariService { return this.injectable(FaseVariazioneAggiudicatariService) }


    // #endregion

    // #region Getters



    // #endregion
}
