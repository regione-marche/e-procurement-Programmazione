import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable({ providedIn: 'root' })
export class SdkDettaglioModelloStoreService extends SdkBaseService {

    private _idModello: number;
    private _progr: number;
    private _entita: string;

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
        this.logger.debug('SET syscon >>>', idModello);
        this._idModello = idModello;
    }

    public get progr(): number {
        this.logger.debug('GET _prog >>>', this._progr);
        return this._progr;
    }

    public set progr(progr: number) {
        this.logger.debug('SET prog >>>', progr);
        this._progr = progr;
    }

    public get entita(): string {
        this.logger.debug('GET _entita >>>', this._entita);
        return this._entita;
    }

    public set entita(entita: string) {
        this.logger.debug('SET entita >>>', entita);
        this._entita = entita;
    }
}