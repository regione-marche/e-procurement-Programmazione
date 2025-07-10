import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable()
export class SdkDettaglioTabellatoStoreService extends SdkBaseService {

    private _provenienza: string;
    private _codiceTabellato: string;
    private _identificativo: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._provenienza;
        delete this._codiceTabellato;
        delete this._identificativo;
    }

    public get provenienza(): string {
        this.logger.debug('GET provenienza >>>', this._provenienza);
        return this._provenienza;
    }

    public set provenienza(provenienza: string) {
        this.logger.debug('SET provenienza >>>', provenienza);
        this._provenienza = provenienza;
    }

    public get codiceTabellato(): string {
        this.logger.debug('GET codiceTabellato >>>', this._codiceTabellato);
        return this._codiceTabellato;
    }

    public set codiceTabellato(codiceTabellato: string) {
        this.logger.debug('SET codiceTabellato >>>', codiceTabellato);
        this._codiceTabellato = codiceTabellato;
    }

    public get identificativo(): string {
        this.logger.debug('GET identificativo >>>', this._identificativo);
        return this._identificativo;
    }

    public set identificativo(identificativo: string) {
        this.logger.debug('SET identificativo >>>', identificativo);
        this._identificativo = identificativo;
    }

}