import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService } from '@maggioli/sdk-commons';

@Injectable({ providedIn: 'root' })
export class DettaglioAttoStoreService extends SdkBaseService {

    private _codGara: string;
    private _codLotto: string;
    private _tipoDocumento: string;
    private _numPubb: string;
    private _campiObbligatori: string;
    private _campiVisibili: string;
    private _attiMap: IDictionary<string>;

    constructor(inj: Injector) {
        super(inj);
    }

    public clear(): void {
        this.logger.debug('CLEAR');
        delete this._codGara;
        delete this._codLotto;
        delete this._tipoDocumento;
        delete this._numPubb;
        delete this._campiObbligatori;
        delete this._campiVisibili;
        delete this._attiMap;
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

    public get attiMap(): IDictionary<string> {
        this.logger.debug('GET attiMap >>>', this._attiMap);
        return this._attiMap;
    }

    public set attiMap(attiMap: IDictionary<string>) {
        this.logger.debug('SET attiMap >>>', attiMap);
        this._attiMap = attiMap;
    }

    public clearAttiMap(): void {
        this.logger.debug('CLEAR attiMap');
        delete this._attiMap;
    }
}