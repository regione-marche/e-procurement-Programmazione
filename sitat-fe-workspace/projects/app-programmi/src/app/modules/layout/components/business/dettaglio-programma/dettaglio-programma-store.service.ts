import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';
import { clone } from 'lodash-es';

@Injectable({ providedIn: 'root' })
export class DettaglioProgrammaStoreService extends SdkBaseService {

    private _idProgramma: string;
    private _tipologia: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._idProgramma;
        delete this._tipologia;
    }

    public get idProgramma(): string {
        this.logger.debug('GET idProgramma >>>', this._idProgramma);
        return this._idProgramma;
    }

    public set idProgramma(idProgramma: string) {
        this.logger.debug('SET idProgramma >>>', idProgramma);
        this._idProgramma = clone(idProgramma);
    }

    public clearIdProgramma(): void {
        this.logger.debug('CLEAR idProgramma');
        delete this._idProgramma;
    }

    public get tipologia(): string {
        this.logger.debug('GET tipologia >>>', this._tipologia);
        return this._tipologia;
    }

    public set tipologia(tipologia: string) {
        this.logger.debug('SET tipologia >>>', tipologia);
        this._tipologia = clone(tipologia);
    }

    public clearTipologia(): void {
        this.logger.debug('CLEAR tipologia');
        delete this._tipologia;
    }
}