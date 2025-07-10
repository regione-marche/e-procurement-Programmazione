import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable({ providedIn: 'root' })
export class DettaglioAvvisoStoreService extends SdkBaseService {

    private _idAvviso: string;
    private _stazioneAppaltante: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public get idAvviso(): string {
        this.logger.debug('GET idAvviso >>>', this._idAvviso);
        return this._idAvviso;
    }

    public set idAvviso(idAvviso: string) {
        this.logger.debug('SET idAvviso >>>', idAvviso);
        this._idAvviso = idAvviso;
    }

    public clearIdAvviso(): void {
        this.logger.debug('CLEAR idAvviso');
        delete this._idAvviso;
    }

    public get stazioneAppaltante(): string {
        this.logger.debug('GET stazioneAppaltante >>>', this._stazioneAppaltante);
        return this._stazioneAppaltante;
    }

    public set stazioneAppaltante(stazioneAppaltante: string) {
        this.logger.debug('SET stazioneAppaltante >>>', stazioneAppaltante);
        this._stazioneAppaltante = stazioneAppaltante;
    }

    public clearStazioneAppaltante(): void {
        this.logger.debug('CLEAR stazioneAppaltante');
        delete this._stazioneAppaltante;
    }
}