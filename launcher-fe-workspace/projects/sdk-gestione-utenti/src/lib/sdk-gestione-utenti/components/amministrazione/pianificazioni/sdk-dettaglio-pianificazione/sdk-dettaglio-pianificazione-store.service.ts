import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable()
export class SdkDettaglioPianificazioneStoreService extends SdkBaseService {

    private _codApp: string;
    private _beanId: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._codApp;
        delete this._beanId;
    }

    public get codApp(): string {
        this.logger.debug('GET codApp >>>', this._codApp);
        return this._codApp;
    }

    public set codApp(codApp: string) {
        this.logger.debug('SET codApp >>>', codApp);
        this._codApp = codApp;
    }

    public get beanId(): string {
        this.logger.debug('GET beanId >>>', this._beanId);
        return this._beanId;
    }

    public set beanId(beanId: string) {
        this.logger.debug('SET beanId >>>', beanId);
        this._beanId = beanId;
    }

}