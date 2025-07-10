import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

import { GaraEntry } from '../../../../models/gare/gare.model';
import { SmartCigEntry } from '../../../../models/smartcig/smartcig.model';

@Injectable({ providedIn: 'root' })
export class DettaglioIdGaraStoreService extends SdkBaseService {

    private _gara: GaraEntry | SmartCigEntry;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._gara;
    }

    public get gara(): GaraEntry | SmartCigEntry {
        this.logger.debug('GET gara >>>', this._gara);
        return this._gara;
    }

    public set gara(gara: GaraEntry | SmartCigEntry) {
        this.logger.debug('SET gara >>>', gara);
        this._gara = gara;
    }

    public clearGara(): void {
        this.logger.debug('CLEAR gara');
        delete this._gara;
    }
}