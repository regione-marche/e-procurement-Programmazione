
import { SAEntry } from "@maggioli/app-commons";
import { SdkAutocompleteItem } from "@maggioli/sdk-controls";

export interface ProfiloDTO {
    codice?: string;
    codiceApplicazione?: string;
    descrizione?: string;
    nome?: string;
}

export interface RicercaSchedePcp {
    stazioneAppaltante?: string;
    uffInt?: Array<string>;
    rupData?: Array<SdkAutocompleteItem>;
    autore?: string;
    dataTrasmissioneDa?: Date;
    dataTrasmissioneA?: Date;
    idAppalto?: string;
    cig?: string;
    tipoScheda?: number;
    progressivoScheda?: number;
}

export interface RicercaSchedePcpEntry {
    codGara?: string;
    stazioneAppaltante?: string;
    cigLottoNumber?: string;
    faseEsecuzione?: number;
    progressivoScheda?: string;
    datInv?: Date;
    autore?: string;
    codLotto?: number;
}

export interface ResponseSchedeTrasmessePcp {
    data?: Array<RicercaSchedePcpEntry>;
    errorData?: string;
    esito?: boolean;
    totalCount?: number;
}

export interface ListaRicercaSchedeTrasmessePcpParams {
    stazioneAppaltante?: string;
    autore?: string;
    dataTrasmissioneDa?: Date;
    dataTrasmissioneA?: Date;
    idAppalto?: string;
    cig?: string;
    tipoScheda?: number;
    progressivoScheda?: number;
    syscon?: number;
    uffInt?: Array<string>;
}