import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService } from '@maggioli/sdk-commons';

@Injectable({ providedIn: 'root' })
export class SdkListaModelliPredispostiStoreService extends SdkBaseService {

    private _from: string;
    private _entita: string;
    private _queryParams: IDictionary<any>;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._from;
        delete this._queryParams;
    }

    public get from(): string {
        this.logger.debug('GET from >>>', this._from);
        return this._from;
    }

    public set from(from: string) {
        this.logger.debug('SET _from >>>', from);
        this._from = from;
    }

    public get entita(): string {
        this.logger.debug('GET from >>>', this._entita);
        return this._from;
    }

    public set entita(entita: string) {
        this.logger.debug('SET _from >>>', entita);
        this._entita = entita;
    }

    public get queryParams(): IDictionary<any> {
        this.logger.debug('GET queryParams >>>', this._queryParams);
        return this._queryParams;
    }

    public set queryParams(queryParams: IDictionary<any>) {
        this.logger.debug('SET queryParams >>>', queryParams);
        this._queryParams = queryParams;
    }

}