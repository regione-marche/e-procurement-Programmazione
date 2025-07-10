export interface ConfigurazioneUtente {
    sysab3?: string;
    centroCosto?: string;
    idCentroCosto?: string;
    centriCosto?: Array<centroCosto>;
}

export interface ConfigurazioneUtenteInsertForm {
    sysab3?: string;
    idCentroCosto?: string;
    syscon?:string;    
}

export interface centroCosto {
    id:number;
	denom:string;
}

