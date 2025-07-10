import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable()
export class SdkDettaglioEventoStoreService extends SdkBaseService {

    private _idEvento: number;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._idEvento;
    }

    public get idEvento(): number {
        this.logger.debug('GET idEvento >>>', this._idEvento);
        return this._idEvento;
    }

    public set idEvento(idEvento: number) {
        this.logger.debug('SET idEvento >>>', idEvento);
        this._idEvento = idEvento;
    }

}