export interface RegistrazioneApplicazioneResponse {
    authenticationUrl: string;
    appCode: string;
    authCode: string;
    expiresIn: number;
    interval: number;
}

export interface RichiestaTokenRequest {
    appSecret: string;
    appCode: string
}

export interface RichiestaTokenResponse {
    token: string;
    dataCreazione: string;
    refreshToken: string;
    loa?: string;
}

export interface UserAccountForm {
    applicativiSelezionati?: Array<string>;
    codiceFiscale?: string;
    codiceFiscaleLogin?: string;
    cognome?: string;
    confermaPassword?: string;
    documentoFirmato?: string;
    email?: string;
    fileExt?: string;
    fileName?: string;
    messaggioAmministratore?: string;
    nome?: string;
    nomeCognomeDirigente?: string;
    password?: string;
    servizioDirigente?: string;
    stazioneAppaltante?: string;
    telefono?: string;
    captchaSolution: string;
}


export interface UserRegistrationResponse {
    /**
     * syscon utente creato
     */
    data?: string;
    /**
     * Eventuale messaggio di errore in caso di operazione fallita
     */
    errorData?: string;
    /**
     * Risultato operazione di inserimento
     */
    esito?: boolean;
    /**
     * Eventuale messaggi di errore di formalita' password
     */
    passwordErrors?: Array<string>;
}



export interface UserExistsInfo {
    exist?: string;
    abilitaRegistrazione?: boolean;
}

export interface SSOTokenInfo {
    jwtToken?: string;
    refreshToken?: string;
    createdAt?: Date;
    secondiValidita?: number;
    loa?: string;
}

export interface LoginWithCredentialsForm {
    applicativo?: string;
    password?: string;
    passwordAmministratore?: string;
    username?: string;
}

export interface CredentialsEntry {
    token?: string;
    refreshToken?: string;
    validLogin?: CredentialsEntry.ValidLoginEnum;
}

export namespace CredentialsEntry {
    export type ValidLoginEnum = 'ADMIN_NOT_FOUND' | 'USER_NOT_FOUND' | 'WRONG_ADMIN_PASSWORD' | 'WRONG_CREDENTIALS' | 'OK';
    export const ValidLoginEnum = {
        ADMINNOTFOUND: 'ADMIN_NOT_FOUND' as ValidLoginEnum,
        USERNOTFOUND: 'USER_NOT_FOUND' as ValidLoginEnum,
        WRONGADMINPASSWORD: 'WRONG_ADMIN_PASSWORD' as ValidLoginEnum,
        WRONGCREDENTIALS: 'WRONG_CREDENTIALS' as ValidLoginEnum,
        OK: 'OK' as ValidLoginEnum
    };
}


// #region InterfacceToscana
export interface UserDetailsToscana {
    name?: string;
    family_name?: string;
    preferred_username?: string;
    gender?: string;
    birthdate?: Date;
    birthplace?: string;
    fiscal_number?: string;
    iva_code?: string;
    address?: string;
    email?: string;
    phone_number?: string;
    iat?: number;
    exp?: number;
    rt_roles?: Array<string>;
}

export interface AuthenticationTokenInfo {
    token: string;
    dataCreazione: string;
    refreshToken: string;
}

export interface RefreshTokenRequest {
    appSecret: string;
    appCode: string;
    refreshToken: string;
}

export interface TokenContent {
    USER_NAME: string;
    USER_SURNAME: string;
    USER_CF: string;
    USER_LOGIN: String;
    user: string;
    ID_APPLICATIVO: string;
    aud: string;
    iss: string;
    iat: number;
    sub: string;
    exp: number;
    name?: string;
    family_name?: string;
    preferred_username?: string;
    SSO_LOGOUT_URL?: string;
}


// #endregion