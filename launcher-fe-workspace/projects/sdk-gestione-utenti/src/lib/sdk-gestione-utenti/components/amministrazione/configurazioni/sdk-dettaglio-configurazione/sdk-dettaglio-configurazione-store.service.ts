import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable()
export class SdkDettaglioConfigurazioneStoreService extends SdkBaseService {

    private _codApp: string;
    private _chiave: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._codApp;
        delete this._chiave;
    }

    public get codApp(): string {
        this.logger.debug('GET codApp >>>', this._codApp);
        return this._codApp;
    }

    public set codApp(codApp: string) {
        this.logger.debug('SET codApp >>>', codApp);
        this._codApp = codApp;
    }

    public get chiave(): string {
        this.logger.debug('GET chiave >>>', this._chiave);
        return this._chiave;
    }

    public set chiave(chiave: string) {
        this.logger.debug('SET chiave >>>', chiave);
        this._chiave = chiave;
    }

}