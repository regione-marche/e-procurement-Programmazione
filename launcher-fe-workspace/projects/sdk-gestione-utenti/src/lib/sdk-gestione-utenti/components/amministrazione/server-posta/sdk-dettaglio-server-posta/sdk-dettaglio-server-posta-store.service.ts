import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable()
export class SdkDettaglioServerPostaStoreService extends SdkBaseService {

    private _idCfg: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._idCfg;
    }

    public get idCfg(): string {
        this.logger.debug('GET idCfg >>>', this._idCfg);
        return this._idCfg;
    }

    public set idCfg(idCfg: string) {
        this.logger.debug('SET idCfg >>>', idCfg);
        this._idCfg = idCfg;
    }

}