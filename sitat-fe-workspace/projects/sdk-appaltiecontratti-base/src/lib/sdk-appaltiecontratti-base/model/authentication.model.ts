export interface AuthenticationTokenInfo {
    token: string;
    tipoToken: string;
    dataCreazione: string;
    secondiValidita: number;
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
    USER_LOGIN: string;
    USER_LOA?: string;
    USER_IDP_TYPE?: string;
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
    given_name?: string;
    auth_level?: string;
    auth_type?: string;
    internal?: boolean;
}
