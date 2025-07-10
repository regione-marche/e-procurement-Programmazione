import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

import { FaseImpresaEntry } from '../../../../../models/fasi/elenco-impr-inv-parte.model';

@Injectable({ providedIn: 'root' })
export class DettaglioImpresaInvitataPartecipanteStoreService extends SdkBaseService {

    private _codGara: string;
    private _codLotto: string;
    private _codiceFase: string;
    private _numeroProgressivo: string;
    private _num: string;
    private _numRagg: string;
    private _impresa: FaseImpresaEntry;
    private _fromLS: string;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._codGara;
        delete this._codLotto;
        delete this._codiceFase;
        delete this._numeroProgressivo;
        delete this._num;
        delete this._numRagg;
        delete this._impresa;
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

    public get codiceFase(): string {
        this.logger.debug('GET codiceFase >>>', this._codiceFase);
        return this._codiceFase;
    }

    public set codiceFase(codiceFase: string) {
        this.logger.debug('SET codiceFase >>>', codiceFase);
        this._codiceFase = codiceFase;
    }

    public clearCodiceFase(): void {
        this.logger.debug('CLEAR codiceFase');
        delete this._codiceFase;
    }

    public get numeroProgressivo(): string {
        this.logger.debug('GET numeroProgressivo >>>', this._numeroProgressivo);
        return this._numeroProgressivo;
    }

    public set numeroProgressivo(numeroProgressivo: string) {
        this.logger.debug('SET numeroProgressivo >>>', numeroProgressivo);
        this._numeroProgressivo = numeroProgressivo;
    }

    public clearNumeroProgressivo(): void {
        this.logger.debug('CLEAR numeroProgressivo');
        delete this._numeroProgressivo;
    }

    public get num(): string {
        this.logger.debug('GET num >>>', this._num);
        return this._num;
    }

    public set num(num: string) {
        this.logger.debug('SET num >>>', num);
        this._num = num;
    }

    public clearNum(): void {
        this.logger.debug('CLEAR num');
        delete this._num;
    }

    public get numRagg(): string {
        this.logger.debug('GET numRagg >>>', this._numRagg);
        return this._numRagg;
    }

    public set numRagg(numRagg: string) {
        this.logger.debug('SET numRagg >>>', numRagg);
        this._numRagg = numRagg;
    }

    public clearNumRagg(): void {
        this.logger.debug('CLEAR numRagg');
        delete this._numRagg;
    }

    public get impresa(): FaseImpresaEntry {
        this.logger.debug('GET impresa >>>', this._impresa);
        return this._impresa;
    }

    public set impresa(impresa: FaseImpresaEntry) {
        this.logger.debug('SET impresa >>>', impresa);
        this._impresa = impresa;
    }

    public clearImpresa(): void {
        this.logger.debug('CLEAR impresa');
        delete this._impresa;
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