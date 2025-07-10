import { Injectable, Injector } from '@angular/core';
import { SdkBaseService } from '@maggioli/sdk-commons';

import { ImpresaAggiudicatariaRaggEntry } from '../../../../../models/imprese-aggiudicatarie/imprese-aggiudicatarie.model';

@Injectable({ providedIn: 'root' })
export class DettaglioImpresaAggiudicatariaStoreService extends SdkBaseService {

    // Gara
    private _codGara: string;
    // Lotti e schede
    private _codLotto: string;
    private _codiceFase: string;
    private _numeroProgressivo: string;
    private _impresa: ImpresaAggiudicatariaRaggEntry;

    // Atti
    private _tipoDocumento: string;
    private _numPubb: string;
    private _campiVisibili: string;
    private _campiObbligatori: string;

    //Schede trasmesse a PCP
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
        delete this._impresa;
        delete this._tipoDocumento;
        delete this._numPubb;
        delete this._campiVisibili;
        delete this._campiObbligatori;
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

    public get impresa(): ImpresaAggiudicatariaRaggEntry {
        this.logger.debug('GET impresa >>>', this._impresa);
        return this._impresa;
    }

    public set impresa(impresa: ImpresaAggiudicatariaRaggEntry) {
        this.logger.debug('SET impresa >>>', impresa);
        this._impresa = impresa;
    }

    public clearImpresa(): void {
        this.logger.debug('CLEAR impresa');
        delete this._impresa;
    }

    public get tipoDocumento(): string {
        this.logger.debug('GET tipoDocumento >>>', this._tipoDocumento);
        return this._tipoDocumento;
    }

    public set tipoDocumento(tipoDocumento: string) {
        this.logger.debug('SET tipoDocumento >>>', tipoDocumento);
        this._tipoDocumento = tipoDocumento;
    }

    public clearTipoDocumento(): void {
        this.logger.debug('CLEAR tipoDocumento');
        delete this._tipoDocumento;
    }

    public get numPubb(): string {
        this.logger.debug('GET numPubb >>>', this._numPubb);
        return this._numPubb;
    }

    public set numPubb(numPubb: string) {
        this.logger.debug('SET numPubb >>>', numPubb);
        this._numPubb = numPubb;
    }

    public clearNumPubb(): void {
        this.logger.debug('CLEAR numPubb');
        delete this._numPubb;
    }

    public get campiVisibili(): string {
        this.logger.debug('GET campiVisibili >>>', this._campiVisibili);
        return this._campiVisibili;
    }

    public set campiVisibili(campiVisibili: string) {
        this.logger.debug('SET campiVisibili >>>', campiVisibili);
        this._campiVisibili = campiVisibili;
    }

    public clearCampiVisibili(): void {
        this.logger.debug('CLEAR campiVisibili');
        delete this._campiVisibili;
    }

    public get campiObbligatori(): string {
        this.logger.debug('GET campiObbligatori >>>', this._campiObbligatori);
        return this._campiObbligatori;
    }

    public set campiObbligatori(campiObbligatori: string) {
        this.logger.debug('SET campiObbligatori >>>', campiObbligatori);
        this._campiObbligatori = campiObbligatori;
    }

    public clearCampiObbligatori(): void {
        this.logger.debug('CLEAR campiObbligatori');
        delete this._campiObbligatori;
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