import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable()
export class UtenteStazioneAppaltanteStoreService extends SdkBaseService {

    private _syscon: number;
    private _codice: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._syscon;
        delete this._codice;
    }

    public get syscon(): number {
        this.logger.debug('GET syscon >>>', this._syscon);
        return this._syscon;
    }

    public set syscon(syscon: number) {
        this.logger.debug('SET syscon >>>', syscon);
        this._syscon = syscon;
    }

    public get codice(): string {
        this.logger.debug('GET codice >>>', this._codice);
        return this._codice;
    }

    public set codice(codice: string) {
        this.logger.debug('SET codice >>>', codice);
        this._codice = codice;
    }

}