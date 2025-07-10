import { Inject, Injectable, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig, SdkBaseService, SdkLocalStorageService } from '@maggioli/sdk-commons';
import { cloneDeep, each, isEmpty, isObject, set } from 'lodash-es';
import { Observable, Observer } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../app-commons.constants';
import { TabellatoInfo, ValoreTabellato } from '../../models/tabellati/tabellato.model';
import { HttpRequestHelper } from '../http/http-request-helper.service';
import { TabellatiService } from './tabellati.service';


@Injectable()
export class TabellatiCacheService extends SdkBaseService {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) { super(inj); }

    // #region Public

    public getValoriTabellato(codice: string): Observable<Array<ValoreTabellato>> {
        let storedValues: TabellatoInfo = this.sdkLocalStorageService.getItem(codice, Constants.LOCAL_STORAGE_PREFIX);
        if (isObject(storedValues) && new Date(storedValues.expiration).getTime() >= new Date().getTime()) {
            return new Observable((observer: Observer<Array<ValoreTabellato>>) => {
                let cloned = cloneDeep(storedValues.data);
                observer.next(cloned);
                observer.complete();
            });
        }
        let func = this.getListaValoriTabellatoFactory(codice);
        return this.requestHelper.begin(func);
    }

    public getValoriTabellati(codici: Array<string>): Observable<IDictionary<Array<ValoreTabellato>>> {
        let codesToBeRetrieved: Array<string> = new Array();
        let finalValues: IDictionary<Array<ValoreTabellato>> = {};
        each(codici, (codice: string) => {
            let storedValues: TabellatoInfo = this.sdkLocalStorageService.getItem(codice, Constants.LOCAL_STORAGE_PREFIX);
            if (isObject(storedValues) && new Date(storedValues.expiration).getTime() >= new Date().getTime()) {
                set(finalValues, codice, storedValues.data);
            } else {
                codesToBeRetrieved.push(codice);
            }
        });
        if (isEmpty(codesToBeRetrieved)) {
            return new Observable((ob: Observer<IDictionary<Array<ValoreTabellato>>>) => {
                ob.next(finalValues);
                ob.complete();
            });
        }
        let func = this.getListaValoriTabellatiFactory(codesToBeRetrieved);
        return this.requestHelper.begin(func).pipe(map((result: IDictionary<Array<ValoreTabellato>>) => {
            return {
                ...finalValues,
                ...result
            };
        }));
    }

    // #endregion

    // #region Private

    private getListaValoriTabellatoFactory(codice: string): () => Observable<any> {
        return () => {
            return this.tabellatiService.listaValori(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, codice).pipe(map((result: Array<ValoreTabellato>) => {
                let list: Array<ValoreTabellato> = cloneDeep(result);
                let expirationDate = new Date();
                expirationDate.setDate(expirationDate.getDate() + 1);
                let resultToStore: TabellatoInfo = {
                    data: list,
                    expiration: expirationDate
                };
                this.sdkLocalStorageService.setItem(codice, resultToStore, Constants.LOCAL_STORAGE_PREFIX);
                return resultToStore.data;
            }));
        }
    }

    private getListaValoriTabellatiFactory(codici: Array<string>): () => Observable<IDictionary<Array<ValoreTabellato>>> {
        return () => {
            return this.tabellatiService.getMultipleListaValori(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL, codici).pipe(map((result: IDictionary<Array<ValoreTabellato>>) => {
                let mappaTabellati: IDictionary<Array<ValoreTabellato>> = cloneDeep(result);
                let expirationDate = new Date();
                expirationDate.setDate(expirationDate.getDate() + 1);
                each(mappaTabellati, (value: Array<ValoreTabellato>, key: string) => {
                    let resultToStore: TabellatoInfo = {
                        data: value,
                        expiration: expirationDate
                    };
                    this.sdkLocalStorageService.setItem(key, resultToStore, Constants.LOCAL_STORAGE_PREFIX);
                });
                return mappaTabellati;
            }));
        }
    }

    // #endregion

    // #region Getters

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService); };

    private get sdkLocalStorageService(): SdkLocalStorageService { return this.injectable(SdkLocalStorageService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion
}