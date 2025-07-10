import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable()
export class DettaglioImpresaStoreService extends SdkBaseService {

    private _codiceImpresa: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._codiceImpresa;
    }

    public get codiceImpresa(): string {
        this.logger.debug('GET codiceImpresa >>>', this._codiceImpresa);
        return this._codiceImpresa;
    }

    public set codiceImpresa(codiceImpresa: string) {
        this.logger.debug('SET codiceImpresa >>>', codiceImpresa);
        this._codiceImpresa = codiceImpresa;
    }

    public clearCodiceImpresa(): void {
        this.logger.debug('CLEAR codiceImpresa');
        delete this._codiceImpresa;
    }
}