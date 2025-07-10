import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable({ providedIn: 'root' })
export class SdkDettaglioParametroStoreService extends SdkBaseService {

    private _idModello: number;
    private _idParametro: number;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._idModello;
    }

    public get idModello(): number {
        this.logger.debug('GET _idModello >>>', this._idModello);
        return this._idModello;
    }

    public set idModello(idModello: number) {
        this.logger.debug('SET _idModello >>>', idModello);
        this._idModello = idModello;
    }

    public get idParametro(): number {
        this.logger.debug('GET _idModello >>>', this._idParametro);
        return this._idParametro;
    }

    public set idParametro(idParametro: number) {
        this.logger.debug('SET idParametro >>>', idParametro);
        this._idParametro = idParametro;
    }

}