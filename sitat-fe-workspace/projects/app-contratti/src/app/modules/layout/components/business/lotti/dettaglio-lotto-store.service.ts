import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable({ providedIn: 'root' })
export class DettaglioLottoStoreService extends SdkBaseService {

    private _codGara: string;
    private _codLotto: string;
    private _fromLS: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._codGara;
        delete this._codLotto;
        delete this._fromLS;
    }

    public get codGara(): string {
        this.logger.debug('GET codGara >>>', this._codGara);
        return this._codGara;
    }

    public set codGara(codGara: string) {
        this.logger.debug('SET codGara >>>', codGara);
        this._codGara = codGara;
    }

    public clearCodGara(): void {
        this.logger.debug('CLEAR codGara');
        delete this._codGara;
    }

    public get codLotto(): string {
        this.logger.debug('GET codLotto >>>', this._codLotto);
        return this._codLotto;
    }

    public set codLotto(codLotto: string) {
        this.logger.debug('SET codLotto >>>', codLotto);
        this._codLotto = codLotto;
    }

    public clearCodLotto(): void {
        this.logger.debug('CLEAR codLotto');
        delete this._codLotto;
    }

    public get fromLS(): string {
        this.logger.debug('GET fromLS >>>', this._fromLS);
        return this._fromLS;
    }

    public set fromLS(fromLS: string) {
        this.logger.debug('SET fromLS >>>', fromLS);
        this._fromLS = fromLS;
    }

    public clearFromLS(): void {
        this.logger.debug('CLEAR fromLS');
        delete this._fromLS;
    }
}