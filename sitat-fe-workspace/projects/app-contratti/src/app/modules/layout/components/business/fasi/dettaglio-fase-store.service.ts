import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

@Injectable({ providedIn: 'root' })
export class DettaglioFaseStoreService extends SdkBaseService {

    private _codGara: string;
    private _codLotto: string;
    private _codiceFase: string;
    private _numeroProgressivo: string;
    private _current: boolean;
    private _riaggiudicazione: boolean;
    private _cigFiglio: boolean;
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
        delete this._current;
        delete this._riaggiudicazione;
        delete this._cigFiglio;
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

    public get current(): boolean {
        this.logger.debug('GET current >>>', this._current);
        return this._current;
    }

    public set current(current: boolean) {
        this.logger.debug('SET current >>>', current);
        this._current = current;
    }

    public clearCurrent(): void {
        this.logger.debug('CLEAR current');
        delete this._current;
    }

    public get riaggiudicazione(): boolean {
        this.logger.debug('GET riaggiudicazione >>>', this._riaggiudicazione);
        return this._riaggiudicazione;
    }

    public set riaggiudicazione(riaggiudicazione: boolean) {
        this.logger.debug('SET riaggiudicazione >>>', riaggiudicazione);
        this._riaggiudicazione = riaggiudicazione;
    }

    public clearRiaggiudicazione(): void {
        this.logger.debug('CLEAR riaggiudicazione');
        delete this._riaggiudicazione;
    }

    public get cigFiglio(): boolean {
        this.logger.debug('GET cigFiglio >>>', this._cigFiglio);
        return this._cigFiglio;
    }

    public set cigFiglio(cigFiglio: boolean) {
        this.logger.debug('SET cigFiglio >>>', cigFiglio);
        this._cigFiglio = cigFiglio;
    }

    public clearCigFiglio(): void {
        this.logger.debug('CLEAR cigFiglio');
        delete this._cigFiglio;
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