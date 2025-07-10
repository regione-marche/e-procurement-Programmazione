import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable({ providedIn: 'root' })
export class DettaglioInterventoStore extends SdkBaseService {

    private _idProgramma: string;
    private _tipologia: string;
    private _idIntervento: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._idProgramma;
        delete this._tipologia;
        delete this._idIntervento;
    }

    public get idProgramma(): string {
        this.logger.debug('GET idProgramma >>>', this._idProgramma);
        return this._idProgramma;
    }

    public set idProgramma(idProgramma: string) {
        this.logger.debug('SET idProgramma >>>', idProgramma);
        this._idProgramma = idProgramma;
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
        this._tipologia = tipologia;
    }

    public clearTipologia(): void {
        this.logger.debug('CLEAR tipologia');
        delete this._tipologia;
    }

    public get idIntervento(): string {
        this.logger.debug('GET idIntervento >>>', this._idIntervento);
        return this._idIntervento;
    }

    public set idIntervento(idIntervento: string) {
        this.logger.debug('SET idIntervento >>>', idIntervento);
        this._idIntervento = idIntervento;
    }

    public clearIdIntervento(): void {
        this.logger.debug('CLEAR idIntervento');
        delete this._idIntervento;
    }
}