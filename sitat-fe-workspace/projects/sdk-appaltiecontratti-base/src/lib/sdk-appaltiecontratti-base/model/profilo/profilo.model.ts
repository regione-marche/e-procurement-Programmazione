export interface ProfiloInfo {
    codCliente?: number;
    codProfilo?: string;
    codapp?: string;
    crc?: number;
    descrizione?: string;
    flagInterno?: number;
    nome?: string;
    ok?: boolean;
}

export interface ProfiloConfiguration {
    idProfilo: string;
    ok: boolean;
    nome: string;
    configurazioni: Array<ProfiloConfigurationItem>;
    campiSolaLettura: Array<string>;
    documentiAssociatiDB: boolean;
    woggetti: Array<string>;
}

export interface ProfiloConfigurationItem {
    key?: string;
    valore?: boolean;
    ok?: boolean;
    valDefault?: number;
    okDefault?: boolean;
    default?: boolean;
}