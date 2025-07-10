import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

import { LottoEntry } from '../../../../models/gare/gare.model';

@Injectable({ providedIn: 'root' })
export class DettaglioCigLottoStoreService extends SdkBaseService {

    private _lotto: LottoEntry;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._lotto;
    }

    public get lotto(): LottoEntry {
        this.logger.debug('GET lotto >>>', this._lotto);
        return this._lotto;
    }

    public set lotto(lotto: LottoEntry) {
        this.logger.debug('SET lotto >>>', lotto);
        this._lotto = lotto;
    }

    public clearLotto(): void {
        this.logger.debug('CLEAR lotto');
        delete this._lotto;
    }
}