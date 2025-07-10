export class SdkAppaltiecontrattiBaseConstants {

	public static get RESPONSE_N(): string {
        return 'N';
    }

    public static get RESPONSE_Y(): string {
        return 'Y';
    }
    
    
    public static get USER_PROFILE_DISPATCHER(): string {
        return 'USER';
    }

    public static get USER_PROFILE_SELECT(): string {
        return 'userProfile';
    }

    public static get SA_INFO_DISPATCHER(): string {
        return 'SAINFO';
    }

    public static get SA_INFO_SELECT(): string {
        return 'saInfo';
    }

    

    public static get LOCAL_STORAGE_PREFIX(): string {
        return 'SSA';
    }
		
    // serve per salvare l'oggetto AuthenticationTokenInfo dopo l'arrivo sull'applicazione
    public static get AUTHENTICATION_TOKEN_INFO(): string {
        return 'AUTHENTICATION_TOKEN_INFO';
    }

    // serve per salvare l'appCode di autenticazione
    public static get AUTHENTICATION_APP_CODE(): string {
        return 'AUTHENTICATION_APP_CODE';
    }

    // serve per settare il codice applicativo del modulo selezionato
    public static get APP_CODE(): string {
        return 'APP_CODE';
    }

    public static get SESSION_EXPIRED(): string {
        return 'SESSION_EXPIRED';
    }

    public static get SERVER_DATE_FORMAT(): string {
        return 'yyyy-MM-dd';
    }

}