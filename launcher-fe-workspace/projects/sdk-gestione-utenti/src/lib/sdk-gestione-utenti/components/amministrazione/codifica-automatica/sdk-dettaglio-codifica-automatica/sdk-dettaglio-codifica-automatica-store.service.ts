import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable()
export class SdkDettaglioCodificaAutomaticaStoreService extends SdkBaseService {

    private _nomEnt: string;
    private _nomCam: string;
    private _tipCam: number;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._nomEnt;
        delete this._nomCam;
        delete this._tipCam;
    }

    public get nomEnt(): string {
        this.logger.debug('GET nomEnt >>>', this._nomEnt);
        return this._nomEnt;
    }

    public set nomEnt(nomEnt: string) {
        this.logger.debug('SET nomEnt >>>', nomEnt);
        this._nomEnt = nomEnt;
    }

    public get nomCam(): string {
        this.logger.debug('GET nomCam >>>', this._nomCam);
        return this._nomCam;
    }

    public set nomCam(nomCam: string) {
        this.logger.debug('SET nomCam >>>', nomCam);
        this._nomCam = nomCam;
    }

    public get tipCam(): number {
        this.logger.debug('GET tipCam >>>', this._tipCam);
        return this._tipCam;
    }

    public set tipCam(tipCam: number) {
        this.logger.debug('SET tipCam >>>', tipCam);
        this._tipCam = tipCam;
    }

}