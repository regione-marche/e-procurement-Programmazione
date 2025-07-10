import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper, StazioneAppaltanteInfo } from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBaseService,
    SdkDateHelper,
    SdkProvider,
    SdkRouterService,
    SdkStoreAction,
    SdkStoreService,
} from '@maggioli/sdk-commons';
import { each, get, isEmpty, isFunction, isObject, isUndefined, set } from 'lodash-es';
import { BehaviorSubject, map, Observable, Observer, of } from 'rxjs';

import { SdkAutocompleteItem, SdkComboBoxItem, SdkDialogConfig, SdkFormBuilderConfiguration, SdkFormBuilderField, SdkFormFieldGroupConfiguration, SdkTextboxMatrixCellConfig, SdkTextboxMatrixRowConfig } from '@maggioli/sdk-controls';
import { Constants } from '../../../app.constants';
import { AttoGeneraleEntry, RicercaAttiGeneraliForm } from '../../models/atti-generali/atti-generali.model';
import { AttiGeneraliService } from '../../services/atti-generali/atti-generali.service';

export interface ListaAttiGeneraliProviderArgs extends IDictionary<any> {
    action: 'DELETE' | 'DETAIL';
    idAtto?: string;
    attoGenerale?: AttoGeneraleEntry;
    stazioneAppaltante?: StazioneAppaltanteInfo;
    searchForm?: RicercaAttiGeneraliForm;
    setUpdateState?: Function;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    syscon?: number;
    formBuilderConfig?: SdkFormBuilderConfiguration;
    dialogConfigSubj?: BehaviorSubject<SdkDialogConfig>;
    motivoCanc?: string;
    dataProgrammazione?: Date;
    codProfilo?: string;
}

@Injectable({ providedIn: 'root' })
export class AttiGeneraliProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: ListaAttiGeneraliProviderArgs): Observable<IDictionary<any>> {
        
        this.logger.debug('ListaAttiGeneraliProviderArgs >>>', args);

        if (args.action === 'DETAIL') {
            return this.detailAttoGenerale(args.searchForm);
        } else if (args.action === 'DELETE') {
            return this.deleteAttoGenerale(args.idAtto, args.codProfilo, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if(args.action === 'DELETE-DATA-PUBB-SISTEMA') {
            return this.annullaPubblicazione(args.idAtto, args.codProfilo, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true };
            }));
        } else if(args.action === 'ANNULLA-PUBBLICAZIONE-MOTIVAZIONE-SECOND') {
            return this.annullaPubblicazioneMotivazione(args.idAtto, args.codProfilo, args.motivoCanc, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true, esito: data.esito };
            }));
        } else if (args.buttonCode === 'back-home') {
            return this.back(args);
        } else if (args.buttonCode === 'back-lista-atti') {
            return this.backListaAtti(args);
        } else if (args.buttonCode === 'back-dettaglio-atto') {
            return this.backToDettaglioAtto(args);
        } else if (args.buttonCode === 'pulisciFiltriRicercaAttiGenerali') {
            return this.clean();
        } else if(args.buttonCode === 'search-atti-generali') {
            return this.searchAttiGenerali(args);
        } else if(args.buttonCode === 'modifica-atto') {
            return this.goToModificaAtto(args);
        } else if (args.action === 'PUBBLICA-ATTO') {
            return this.pubblicaAttoGenerale(+args?.idAtto, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true, pubblica: true };
            }));
        } else if (args.action === 'DATEPICKER-PUBB') {
            return this.pubblicaAttoGeneraleDataFutura(+args.idAtto, args.dataProgrammazione, args.messagesPanel).pipe(map((data: any) => {
                return { reload: true, programmato: true };
            }));
        }

        return of(args);
    }

    private detailAttoGenerale(searchForm: RicercaAttiGeneraliForm): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            searchForm: JSON.stringify(searchForm)
        }
        this.routerService.navigateToPage('dettaglio-atto-generale-page', params);
        
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private back(args: ListaAttiGeneraliProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState = args.setUpdateState;
        const stateForm: RicercaAttiGeneraliForm = args.stateForm;

        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        if (stateForm != null && !isEmpty(stateForm)) {
            this.routerService.navigateToPage('ricerca-atti-generali-page');
        } else {
            this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ATTI_GENERALI_DISPATCHER, undefined));
            this.routerService.navigateToPage('home-page');
        }
        
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private backListaAtti(args: ListaAttiGeneraliProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState = args.setUpdateState;

        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.routerService.navigateToPage('lista-atti-generali-page');
        
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private backToDettaglioAtto(args: ListaAttiGeneraliProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState = args.setUpdateState;
        let attoGenerale: AttoGeneraleEntry = args.attoGenerale;

        const params: IDictionary<any> = {
            searchForm: JSON.stringify(attoGenerale),
            idAtto: attoGenerale.idAtto
        };

        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.routerService.navigateToPage('dettaglio-atto-generale-page', params);
        
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private clean(): Observable<IDictionary<any>> {
        this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ATTI_GENERALI_DISPATCHER, undefined));
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next({
                cleanSearch: true
            });
            ob.complete();
        });
    }

    private searchAttiGenerali(args: ListaAttiGeneraliProviderArgs): Observable<IDictionary<any>> {

        let formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
        let params: IDictionary<any> = this.populateRequest(formBuilderConfig);

        const paramsNav: IDictionary<any> = {
            ricerca: 'S'
        };

        this.store.dispatch(new SdkStoreAction(Constants.SEARCH_FORM_ATTI_GENERALI_DISPATCHER, params));
        this.routerService.navigateToPage('lista-atti-generali-page', paramsNav);
    
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    } 

    private goToModificaAtto(args: ListaAttiGeneraliProviderArgs): Observable<IDictionary<any>> {

        let searchForm: RicercaAttiGeneraliForm = args.searchForm;
        let attoGenerale: AttoGeneraleEntry = args.attoGenerale;

        const params: IDictionary<any> = {
            searchForm: JSON.stringify(searchForm),
            attoGenerale: JSON.stringify(attoGenerale)
        };

        this.routerService.navigateToPage('modifica-dettaglio-atto-generale-page', params);
    
        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    private deleteAttoGenerale(idAtto: string, codProfilo: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getDeleteAttoGeneraleFactory(idAtto, codProfilo);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getDeleteAttoGeneraleFactory(idAtto: string, codProfilo: string) {
        return () => {
            return this.attiGeneraliService.deleteAttoGeneraleSingolo(idAtto, codProfilo);
        }
    }

    private annullaPubblicazione(idAtto: string, codProfilo: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getAnnullaPubblicazioneFactory(idAtto, codProfilo);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getAnnullaPubblicazioneFactory(idAtto: string, codProfilo: string) {
        return () => {
            return this.attiGeneraliService.annullaPubblicazione(idAtto, codProfilo);
        }
    }

    private annullaPubblicazioneMotivazione(idAtto:string, codProfilo: string, motivoCanc: string, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getAnnullaPubblicazioneMotivazioneFactory(idAtto, codProfilo, motivoCanc);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getAnnullaPubblicazioneMotivazioneFactory(idAtto:string, codProfilo: string, motivoCanc: string) {
        return () => {
            return this.attiGeneraliService.annullaPubblicazioneMotivazione(idAtto, codProfilo, motivoCanc);
        }
    }

    private pubblicaAttoGenerale(idAtto: number, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.getPubblicaAttoGeneraleFactory(idAtto);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private getPubblicaAttoGeneraleFactory(idAtto: number) {
        return () => {
            return this.attiGeneraliService.pubblicaAttoGenerale(idAtto);
        }
    }

    private pubblicaAttoGeneraleDataFutura(idAtto: number, dataProgrammazione: Date, messagesPanel: HTMLElement): Observable<any> {
        let factory = this.pubblicaAttoGeneraleDataFuturaFactory(idAtto, dataProgrammazione);
        return this.requestHelper.begin(factory, messagesPanel);
    }

    private pubblicaAttoGeneraleDataFuturaFactory(idAtto: number, dataProgrammazione: Date) {
        return () => {
            return this.attiGeneraliService.pubblicaAttoGeneraleDataFutura(idAtto, dataProgrammazione);
        }
    }

    private populateRequest(formBuilderConfig: SdkFormBuilderConfiguration): RicercaAttiGeneraliForm {

        let request: RicercaAttiGeneraliForm = this.getDefaultForm();
        each(formBuilderConfig.fields, (field: SdkFormBuilderField) => {
            if (field.type === 'FORM-SECTION') {
                request = this.elaborateSection(field, request);
            } else if (field.type === 'FORM-GROUP') {
                request = this.elaborateGroup(field, request);
            } else {
                request = this.elaborateOne(field, request);
            }
        });
        return request;
    }

    private getDefaultForm(): RicercaAttiGeneraliForm {
        let item: RicercaAttiGeneraliForm = {};
        return item;
    }

    private elaborateOne(field: SdkFormBuilderField, request: any): any {
        if (isObject(field)) {
            if (field.visible !== false) {
                if (field.type === 'NUMERIC-TEXTBOX'){
                    let item: any = get(field, 'data');
                    set(request, field.mappingInput, item); 
                }else if (field.type === 'COMBOBOX') {
                    let item: SdkComboBoxItem = get(field, 'data');
                    if (isObject(item) && !isEmpty(field.mappingOutput)) {
                        set(request, field.mappingOutput, item.key);
                    }
                } else if (field.type === 'TEXTBOX-MATRIX') {
                    each(field.rows, (row: SdkTextboxMatrixRowConfig) => {
                        each(row.cells, (cell: SdkTextboxMatrixCellConfig) => {
                            if (!isEmpty(cell.mappingOutput)) {
                                set(request, cell.mappingOutput, cell.value);
                            }
                        });
                    })
                } else if (field.type === 'MULTIPLE-AUTOCOMPLETE') {
                    if (field.data != null) {
                        let items: Array<SdkAutocompleteItem> = get(field, 'data');
                        let keys: Array<String> = [];
                        each(items, (item: SdkAutocompleteItem) => {
                            if (!isEmpty(item._key)) {
                                keys.push(item._key)
                            }
                        });

                        set(request, field.mappingInput, keys);

                        if(field.code === 'stazione-appaltante'){
                            set(request, 'stazioneAppaltanteData', items);
                        } else if(field.code === 'rup'){
                            set(request, 'rupData', items);
                        }
                    }
                }
                else if (field.type === 'DATEPICKER' && field.data) {
                    let finalDate: string = this.dateHelper.format(<Date>field.data, 'yyyy/MM/dd');
                    set(request, field.mappingOutput, finalDate);
                } else if (!isUndefined(field.data)) {
                    if (!isEmpty(field.mappingOutput)) {
                        set(request, field.mappingOutput, field.data);
                    }
                }
            } else {
                set(request, field.mappingOutput, undefined);
            }
        }
        return request;
    }

    private elaborateSection(field: SdkFormBuilderField, request: any): any {
        each(field.fieldSections, (one: SdkFormBuilderField) => {
            if (one.type === 'FORM-SECTION') {
                request = this.elaborateSection(one, request);
            } else if (one.type === 'FORM-GROUP') {
                request = this.elaborateGroup(one, request);
            } else {
                request = this.elaborateOne(one, request);
            }
        });
        return request;
    }

    private elaborateGroup(field: SdkFormBuilderField, request: any): any {
        each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration, index: number) => {
            each(group.fields, (one: SdkFormBuilderField) => {
                if (one.type === 'FORM-SECTION') {
                    request = this.elaborateSection(one, request);
                } else if (one.type === 'FORM-GROUP') {
                    request = this.elaborateGroup(one, request);
                } else {
                    request = this.elaborateOne(one, request);
                }
            });
            field.fieldGroups[index] = group;
        });
        return request;
    }

    // #region public

    // #endregion

    // #region Getters

    private get routerService(): SdkRouterService { return this.injectable(SdkRouterService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    private get dateHelper(): SdkDateHelper { return this.injectable(SdkDateHelper) };

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get attiGeneraliService(): AttiGeneraliService { return this.injectable(AttiGeneraliService) } 

    // #endregion

}
