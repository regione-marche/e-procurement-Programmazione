import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable()
export class DettaglioTecnicoStoreService extends SdkBaseService {

    private _codice: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._codice;
    }

    public get codice(): string {
        this.logger.debug('GET codice >>>', this._codice);
        return this._codice;
    }

    public set codice(codice: string) {
        this.logger.debug('SET codice >>>', codice);
        this._codice = codice;
    }

    public clearCodice(): void {
        this.logger.debug('CLEAR codice');
        delete this._codice;
    }
}