export interface ListaDelegheParams extends RicercaDelegheForm {}

export interface RicercaDelegheForm {
    stazioneAppaltante?: string;
    cfrup?: string;
}

export interface ResponseListaDeleghe {
    data?: Array<DelegaBaseEntry>;
    errorData?: string;
    esito?: boolean;
    totalCount?: number;
}

export interface DelegaBaseEntry {
    id?: number;
    sysute?: string;
    codrup?: string;
    cfrup?: string;
    codtec?: string;
    idCollaboratore?: number;
    ruolo?: number;
    desRuolo?: string;
}

export interface DelegaEntry {
    id?: number;
    codrup?: string;
    cfrup?: string;
    codtec?: string;
    idCollaboratore?: number;
    ruolo?: number;
}

export interface DelegaInsertForm extends DelegaEntry {
    stazioneAppaltante?: string;
    syscon?: number;
    idProfilo?: string;
}

export interface ResponseInserisciDelegaResult {
    id?: string;
    errorData?: string;
    esito?: boolean;
}

export interface ResponseModificaDelegaResult {
    errorData?: string;
    esito?: boolean;
}

export interface ResponseCancellaDelegaResult {
    errorData?: string;
    esito?: boolean;
}

export interface LoaderAppaltoUsrEntry {
    syscon?: number;
    simoguser?: string;
    simogpass?: string;

    //Extra info
    rpntLoginAvailable?: boolean;
    collaboratoriPresenti?: boolean;
}

export interface CredenzialiSimogUpdateForm extends LoaderAppaltoUsrEntry {
    stazioneAppaltante?: string;
    cfStazioneAppaltante?: string;
}
