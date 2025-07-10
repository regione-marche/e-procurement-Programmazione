import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable({ providedIn: 'root' })
export class ListaProgrammiStoreService extends SdkBaseService {

    private _searchString: string;
    private _tipologia: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._searchString;
        delete this._tipologia;
    }

    public get searchString(): string {
        this.logger.debug('GET searchString >>>', this._searchString);
        return this._searchString;
    }

    public set searchString(searchString: string) {
        this.logger.debug('SET searchString >>>', searchString);
        this._searchString = searchString;
    }

    public clearSearchString(): void {
        this.logger.debug('CLEAR searchString');
        delete this._searchString;
    }

    public get tipologia(): string {
        this.logger.debug('GET tipologia >>>', this._tipologia);
        return this._tipologia;
    }

    public set tipologia(tipologia: string) {
        this.logger.debug('SET tipologia >>>', tipologia);
        this._tipologia = tipologia;
    }

    public clearTipologia(): void {
        this.logger.debug('CLEAR tipologia');
        delete this._tipologia;
    }
}