import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable()
export class DettaglioCdcStoreService extends SdkBaseService {

    private _idCentro: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._idCentro;
    }

    public get idCentro(): string {
        this.logger.debug('GET idCentro >>>', this._idCentro);
        return this._idCentro;
    }

    public set idCentro(idCentro: string) {
        this.logger.debug('SET idCentro >>>', idCentro);
        this._idCentro = idCentro;
    }

    public clearIdCentro(): void {
        this.logger.debug('CLEAR idCentro');
        delete this._idCentro;
    }
}