import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable({ providedIn: 'root' })
export class DettaglioDelegaStoreService extends SdkBaseService {

    private _id: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._id;
    }

    public get id(): string {
        this.logger.debug('GET id >>>', this._id);
        return this._id;
    }

    public set id(id: string) {
        this.logger.debug('SET id >>>', id);
        this._id = id;
    }

    public clearid(): void {
        this.logger.debug('CLEAR id');
        delete this._id;
    }

}
