import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import {
    AbilitazioniUtilsService,
    CustomParamsFunction,
    FormBuilderUtilsService,
    HttpRequestHelper,
    ImpresaEntry,
    ProtectionUtilsService,
    RupEntry,
    StazioneAppaltanteInfo,
    TabellatiCacheService,
    ValoreTabellato,
} from '@maggioli/app-commons';
import {
    SdkAutocompleteItem,
    SdkComboBoxItem,
    SdkFormBuilderField,
    SdkModalOutput,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, find, get, isEmpty, isEqual, isObject, isString, set, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Observer, of, Subject } from 'rxjs';
import { map, mergeMap } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { IncarichiProfEntry, ResponseIncarichiProfListEntry } from '../../../../../../models/incarichi-professionali/incarichi-professionali.model';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';
import { FaseIncarichiProfessionaliService } from 'projects/app-contratti/src/app/modules/services/fasi/fase-incarichi-professionali.service';
import { FaseVariazioneAggiudicatariEntry, InizFaseVariazioneAggiudicatariEntry } from 'projects/app-contratti/src/app/modules/models/fasi/fase-variazione-aggiudicatari.model';
import { FaseVariazioneAggiudicatariService } from 'projects/app-contratti/src/app/modules/services/fasi/fase-variazione-aggiudicatari.service';


@Component({
    templateUrl: `modifica-fase-variazione-aggiudicatari-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseVariazioneAggiudicatariSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `modifica-fase-variazione-aggiudicatari-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;


    protected fase: FaseVariazioneAggiudicatariEntry;
    protected iniz: Array<string>;

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



   protected get faseVariazioneAggiudicatariService(): FaseVariazioneAggiudicatariService { return this.injectable(FaseVariazioneAggiudicatariService) }
   

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void { }

    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: config.modalComponent,
                componentConfig: config.modalComponentConfig
            };

            if (config.code === 'nomtec') {
                this.modalConfig.componentConfig = {
                    gara: this.gara,
                    ...this.modalConfig.componentConfig,
                    selectedItem: config.textModalContent,
                    readOnly: true
                }
            }

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public manageModalOutput(item: SdkModalOutput<any>): void {
        
    }


    public manageDeletePanel(field: SdkFormBuilderField): void { }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: any, dynamicField?: boolean) => {

        let mapping: boolean = true;

        let keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        let idRuolo: any = get(restObject, 'idRuolo');

        if (field.code === 'denominazione-impresa') {
           
            if (isObject(get(restObject, field.mappingInput))) {
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
            let data: SdkComboBoxItem = {
                key: restObject.idPartecipante,
                value: restObject.ragioneSociale
            };            
            field.data = data;
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

    protected tabellati(): Array<string> {
        return Constants.FASE_VARAIZIONE_AGGIUDICATARI;
    }

    protected loadDettaglio = (): Observable<FaseVariazioneAggiudicatariEntry> => {
        return this.getInizObservable().pipe(
            mergeMap(() => this.getDettaglioObservable())
        );
    }

    // #endregion

    // #region Private

    protected dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseVariazioneAggiudicatariEntry> {
        return () => {
            return this.faseVariazioneAggiudicatariService.getFase(codGara, codLotto, progressivo);
        }
    }

    protected getDettaglioObservable(): Observable<any> {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseVariazioneAggiudicatariEntry) => {                   
                    return result;
                })
            );
    }



        protected getInizObservable(): Observable<Array<String>> {
            const factory = this.datiInizializzazioneFaseFactory(this.codGara, this.codLotto);
            return this.requestHelper.begin(factory, this.messagesPanel)
                .pipe(
                    map((result: Array<string>) => {
                        this.iniz = result;                                        
                        return result;
                    })
                );
        }
    
        
    
    protected datiInizializzazioneFaseFactory(codGara: string, codLotto: string): () => Observable<InizFaseVariazioneAggiudicatariEntry> {
        return () => {
            return this.faseVariazioneAggiudicatariService.getDatiInizializzazione(codGara, codLotto);
        }
    }

    protected manageExecutionProvider = (obj: any) => {

        if (isObject(obj)) {
            if (get(obj, 'action') === 'open-sidebar' && isObject(get(this.config.body, 'sidebar'))) {
                this.sidebarConfig.componentConfig = obj;
                this.sidebarConfigObs.next(this.sidebarConfig);
                this.sidebarConfig.openSubject.next(true);
            }
        }
    }

    protected scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    // #endregion
}
