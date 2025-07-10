import { Injectable, Injector } from '@angular/core';
import { ComboDto, ComboDtoCacheInfo, ComboMultiResponse, ComboResponse, HttpRequestHelper } from '@maggioli/sdk-appaltiecontratti-base';
import { IDictionary, SdkBaseService, SdkLocalStorageService } from '@maggioli/sdk-commons';
import { cloneDeep, each, isEmpty, isObject, set } from 'lodash-es';
import { Observable, Observer } from 'rxjs';
import { map } from 'rxjs/operators';
import { SdkDocAssociatiConstants } from '../sdk-docassociati.constants';
import { SdkDocAssociatiService } from './sdk-docassociati.service';



@Injectable({ providedIn: 'root' })
export class TabellatiCacheService extends SdkBaseService {

    constructor(inj: Injector) { super(inj); }

    public tabellatiGetComboData(tabcod: string): Observable<Array<ComboDto>> {
        let items: ComboDtoCacheInfo = this.sdkLocalStorageService.getItem(tabcod, SdkDocAssociatiConstants.LOCAL_STORAGE_PREFIX);
        if (isObject(items) && new Date(items.expiration).getTime() >= new Date().getTime()) {
            return new Observable((observer: Observer<Array<ComboDto>>) => {
                let cloned = cloneDeep(items.data);
                observer.next(cloned);
                observer.complete();
            });
        }
        let func = this.getTabGetComboDataFactory(tabcod);
        return this.requestHelper.begin(func);
    }

    private getTabGetComboDataFactory(tabcod: string): () => Observable<any> {
        return () => {
            return this.service.tabellatiGetComboData(tabcod).pipe(map((result: ComboResponse) => {
                let items: Array<ComboDto> = cloneDeep(result.data);
                let expirationDate = new Date();
                expirationDate.setDate(expirationDate.getDate() + 1);
                let resultToStore: ComboDtoCacheInfo = {
                    data: items,
                    expiration: expirationDate
                };
                this.sdkLocalStorageService.setItem(tabcod, resultToStore, SdkDocAssociatiConstants.LOCAL_STORAGE_PREFIX);
                return resultToStore.data;
            }));
        }
    }


    public tabGetMultiComboData(tabcods: Array<string>): Observable<IDictionary<Array<ComboDto>>> {
        let codesToBeRetrieved: Array<string> = new Array();
        let finalValues: IDictionary<Array<ComboDto>> = {};

        each(tabcods, (tabcod: string) => {
            let storedValues: ComboDtoCacheInfo = this.sdkLocalStorageService.getItem(tabcod, SdkDocAssociatiConstants.LOCAL_STORAGE_PREFIX);
            if (isObject(storedValues) && new Date(storedValues.expiration).getTime() >= new Date().getTime()) {
                set(finalValues, tabcod, storedValues.data);
            } else {
                codesToBeRetrieved.push(tabcod);
            }
        });
        if (isEmpty(codesToBeRetrieved)) {
            return new Observable((ob: Observer<IDictionary<Array<ComboDto>>>) => {
                ob.next(finalValues);
                ob.complete();
            });
        }
        return this.getTabGetMultiComboDataFactory(codesToBeRetrieved).pipe(map((result: IDictionary<Array<ComboDto>>) => {
            return {
                ...finalValues,
                ...result
            };
        }));
    }

    private getTabGetMultiComboDataFactory(tabcods: string[]) : Observable<IDictionary<Array<ComboDto>>> {
        return this.service.tabellatiGetMultiComboData(tabcods).pipe(map((result: ComboMultiResponse) => {
            let items: IDictionary<Array<ComboDto>> = cloneDeep(result.data);
            let expirationDate = new Date();
            expirationDate.setDate(expirationDate.getDate() + 1);
            each(items, (value: Array<ComboDto>, key: string) => {
                let resultToStore: ComboDtoCacheInfo = {
                    data: value,
                    expiration: expirationDate
                };
                this.sdkLocalStorageService.setItem(key, resultToStore, SdkDocAssociatiConstants.LOCAL_STORAGE_PREFIX);
            });
            return items;
        }));
    }

    private get service(): SdkDocAssociatiService { return this.injectable(SdkDocAssociatiService); };

    private get sdkLocalStorageService(): SdkLocalStorageService { return this.injectable(SdkLocalStorageService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

}