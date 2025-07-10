import { Injectable, Injector } from "@angular/core";
import { SdkBaseService } from "@maggioli/sdk-commons";

@Injectable({
    providedIn: "root"
})
export class SdkDettaglioReportStoreService extends SdkBaseService {

    private _idRicerca: number;
    private _codice: string;
    private _nome: string;
    private _schedula: string;

    constructor(inj: Injector){
        super(inj);
    }

    public clear(): void{
        this.logger.debug('CLEAR');
        delete this._idRicerca;
        delete this._codice;
    }

    public get idRicerca(): number{
        this.logger.debug('GET idRicerca >>>', this._idRicerca);
        return this._idRicerca;
    }

    public set idRicerca(idRicerca: number) {
        this.logger.debug('SET idRicerca >>>', idRicerca);
        this._idRicerca = idRicerca;
    }

    
    public get codice(): string{
        this.logger.debug('GET codice >>>', this._codice);
        return this._codice;
    }

    public set codice(codice: string) {
        this.logger.debug('SET codice >>>', codice);
        this._codice = codice;
    }

    public get nome(): string {
        this.logger.debug('GET nome >>>', this._nome);
        return this._nome;
    }

    public set nome(nome: string){
        this.logger.debug('SET nome >>>', nome);
        this._nome = nome;
    }

    public get schedula(): string {
        this.logger.debug('GET schedula >>>', this._schedula);
        return this._schedula;
    }

    public set schedula(schedula: string){
        this.logger.debug('SET schedula >>>', schedula);
        this._schedula = schedula;
    }

}