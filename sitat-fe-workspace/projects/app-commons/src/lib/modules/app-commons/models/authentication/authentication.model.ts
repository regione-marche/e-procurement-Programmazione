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
    USER_LOGIN: string;
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
    internal?: boolean;
    USER_IDP_TYPE?: string;
    USER_LOA?: string;
    SSO_LOGOUT_URL?: string;
}
