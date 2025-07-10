import { SdkFormBuilderField } from "@maggioli/sdk-controls";
import { WRicParamDTO } from "./lista-params.model";
import { IDictionary } from "@maggioli/sdk-commons";


//#region Risposta parametrica
export interface ResponseDTO<T> {
	done: string,
	response?: T,
	messages?: Array<string>;
}

export interface ResponseListaDTO<T> extends ResponseDTO<T> {
    totalCount?: number;
    detailedErrorQuery?: string;
    mandatoryParamsNotUsed?: Array<string>;
    maxNumRecordConfig?: number;
}

//#endregion

//#region Tabellato
export interface ValoreTabellato {
    archiviato: string;
    cap?: string;
    codice: string;
    codiceProvincia?: string;
    codistat?: string;
    descrizione: string;
}

//#endregion

//#region TabellatoInfo

export interface TabellatoInfo {
    data: Array<ValoreTabellato>;
    expiration?: Date;
}
//#endregion

export interface MenuFieldTabellato {
    codice?: number;
    descrizione?: string;
}

export interface ResponseResult<T> {
    data?: T;
    error?: string;
    errorData?: string;
    esito?: boolean;
}

//#region DTO per definizione report edit
export interface DefinizioneReportEditDTO { 
    idRicerca?: number;
    defSql?: string;
}
//#endregion

export type CustomParamsFunction = (field: SdkFormBuilderField, restObject?: any, dynamicField?: boolean) => CustomParamsFunctionResponse;

export interface CustomParamsFunctionResponse {
    mapping: boolean;
    field: SdkFormBuilderField;
}

export interface ProfiloConfiguration {
    idProfilo: string;
    ok: boolean;
    nome: string;
    configurazioni: Array<ProfiloConfigurationItem>;
    campiSolaLettura: Array<string>;
}

export interface ProfiloConfigurationItem {
    key?: string;
    valore?: boolean;
    ok?: boolean;
    valDefault?: number;
    okDefault?: boolean;
    default?: boolean;
}

export interface DynamicValue {
    codice?: number;
    descrizione?: string;
    value?: number;
}

export interface PubblicatoValue {
    key?: number,
    value?: string
}

//#region Form per inviare value a backend per esecuzione report

export interface DynamicFormValueDTO {
    [key:string]: any;
}
//#endregion

//#region Form complesso per gestione WCacheRicPar ed esecuzione report a backend.

export interface GenericDynamicFormValueDTO{
    dynamicFormValueDTO?: any;
    allParams?: Array<WRicParamDTO>
}
//#endregion

//#region DTO per l'utente

export interface UserDTO { 
    categoria?: number;
    codiceFiscale?: string;
    cognome?: string;
    dataScadenzaAccount?: Date;
    dataUltimoAccesso?: Date;
    deletable?: boolean;
    disabilitato?: boolean;
    email?: string;
    ldap?: boolean;
    nome?: string;
    opzioniUtente?: Array<string>;
    sysab3?: string;
    sysabg?: string;
    syscon?: number;
    ufficiIntestatari?: Array<string>;
    ufficioAppartenenza?: number;
    username?: string;
}
//#endregion

export interface InitRicercaUtentiDTO { 
    profiliAssociati?: Array<string>;
    registrazioneLoginCF?: boolean;
    stazioniAppaltantiAssociate?: Array<string>;
    utenteDelegatoGestioneUtenti?: boolean;
}

//#endregion

//#region DTO per il profilo da inviare al backend

export interface ProfiloDTO {
    codice?: string;
    codiceApplicazione?: string;
    descrizione?: string;
    nome?: string;
}

//#endregion

//#region DTO per il profilo con il valore checked per indicare i profili selezionati in "Modifica profili"

export interface ProfiloCheckDTO {
    codice?: string;
    codiceApplicazione?: string;
    descrizione?: string;
    nome?: string;
    checked?: boolean;
}
//#endregion

export interface ProfiliUtenteEditDTO {
    listaProfili?: Array<string>;
}

export interface StazioneAppaltanteEntry { 
    cap?: string;
    cfAnac?: string;
    codFisc?: string;
    codIstat?: string;
    codein?: string;
    denominazione?: string;
    email?: string;
    fax?: string;
    idAmministrazione?: number;
    indirizzo?: string;
    isCuc?: string;
    nCivico?: string;
    telefono?: string;
    tipologia?: number;
    urlProfiloC?: string;
    codausa?: string;
}

//#region DTO per l'ufficio intestatario da associare al report
export interface UfficioIntestatarioDTO {
    codice?: string;
    codiceFiscale?: string;
    denominazione?: string;
}
//#endregion

//#region DTO per l'ufficio intestatario con il valore checked per indicare gli uff-int selezionati in "Modifica uffici intestatari"
export interface UfficioIntestatarioCheckDTO {
    codice?: string;
    codiceFiscale?: string;
    denominazione?: string;
    checked?: boolean;
}
//#endregion

export interface UfficioIntestatarioEditDTO {
    listaUfficiIntestatari?: Array<string>;
}

//#region 
//- DTO per il form contenente i nomi delle colonne che arrivano dal backend
//- la lista di values che rappresenta il risultato di un report
//- la query SQL eseguita e già sostituita con tutti i parametri
export interface ResultQueryExecutionFormDTO{
    columnNames?: Array<string>;
    [values: string]: any;
    queryReplacedWithParams?: string;
    paramsToShowUser?: { [key: string]: any };
    mandatoryParamsNotUsed?: Array<string>;
}
//#endregion

//#region DTO per il form contenente i nomi delle colonne che arrivano dal backend con la lista di values che rappresenta il risultato di un report con aggiunta di syscon e idRicerca.

export interface ResultReportQueryParamsDTO{
    columnNames?: Array<string>;
    [values: string]: any;
    idRicerca?: number;
    syscon?: number;
}
//#endregion

//#region DTO per il form di estrazione dati in formato JSON per report predefiniti
export interface JsonResponseDTO {
    [key: string]: any;
}

export interface SdkBreadcrumbItem {
    /**
     * Codice univoco che identifica l'item
     */
    code: string;
    /**
     * Codice label da utilizzare su ngx-translate per recuperare la label in lingua
     */
    label?: string;
    /**
     * Parametri da passare a ngx-translate per recuperare la label in lingua
     */
    labelParams?: IDictionary<string>;
    /**
     * Slug di navigazione al click
     */
    slug?: string;
    /**
     * Mappa di parametri aggiuntivi
     */
    additionalParams?: IDictionary<any>;
    /**
     * Codice provider da eseguire per nascondere la breadcrumb (se non valorizzato l'item e' sempre visibile)
     */
    visible?: string;
    /**
     * Provider da eseguire per recuperare una stringa (di business) da passare al translate service come parametro.
     * Se presente anche "labelParams" gli oggetti verrano uniti assieme e il risultato del provider prendera' chiave "value"
     * Es.
     * {
     *    "value": "" // label ritornata dal provider
     *    ... // altre chiavi di labelParams
     * }
     */
    labelProvider?: string;
}