export interface AuthenticationDTO {
    certificato?: string;
    motivazione?: string;
    password: string;
    username: string;
}

export interface ChangePasswordDTO {
    username: string;
    oldPassword: string;
    newPassword: string;
    confirmNewPassword: string;
}

export interface LoginSuccessDTO {
    token: string;
    refreshToken: string;
}

export interface PasswordRecoveryRequestDTO {
    captchaSolution: string;
    currentUrl?: string;
    username: string;
}

export interface PasswordRecoveryExecutionDTO {
    confermaPassword: string;
    password: string;
    token: string;
}

export interface ChangePasswordUserDTO {
    confirmNewPassword: string;
    newPassword: string;
    oldPassword: string;
}

export interface CheckMTokenDTO {
    username: string;
}
