import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable()
export class SdkListaDettaglioTabellatoStoreService extends SdkBaseService {

    private _provenienza: string;
    private _codiceTabellato: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._provenienza;
        delete this._codiceTabellato;
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

}