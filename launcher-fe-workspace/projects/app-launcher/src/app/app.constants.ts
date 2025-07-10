export class Constants {

    public static get APP_NAME(): string {
        return 'app-launcher';
    }

    public static get AUTHENTICATION_TOKEN_INFO(): string {
        return 'AUTHENTICATION_TOKEN_INFO';
    }
    
    public static get APP_CODE(): string {
        return 'APP_CODE';
    }

    public static get AUTHENTICATION_CODE(): string {
        return 'AUTHENTICATION_CODE';
    }

    public static get USER_PROFILE_TO_LAUNCHER(): string {
        return 'USER_PROFILE_TO_LAUNCHER';
    }

    public static get LOCAL_STORAGE_PREFIX(): string {
        return 'SSA';
    }

    public static get CURRENT_MODULE(): string {
        return 'CURRENT_MODULE';
    }

    public static get USER_PROFILE_DISPATCHER(): string {
        return 'USER';
    }

    public static get USER_PROFILE_SELECT(): string {
        return 'userProfile';
    }

    public static get SERVER_DATE_FORMAT(): string {
        return 'yyyy-MM-dd';
    }

    public static get LOGOUT_PATH(): string {
        return 'LOGOUT_PATH';
    }

    public static get AUTHENTICATION_APP_CODE(): string {
        return '5df76564c9931b0001721594';
    }

    public static get SESSION_EXPIRED(): string {
        return 'SESSION_EXPIRED';
    }

    public static get SPID_AUTH_ID(): string {
        return 'spid-auth-id';
    }

    public static get PANEL_CODE_SELEZIONATO(): string {
        return 'PANEL_CODE_SELEZIONATO';
    }

    public static REGISTRAZIONE_UTENTE_FILE_NAME(codiceFiscale: string, estensione: string): string {
        let fileName: string = `consenso-registrazione-${codiceFiscale.toUpperCase().trim()}`;
        if (estensione != null) {
            fileName += `.${estensione}`;
        }
        return fileName;
    }
}

export type TEnvs = 'DEVELOPMENT' | 'STAGING' | 'PRODUCTION' | 'DEVELOPMENT-PRODUCTION' | 'STAGING-LOCAL';