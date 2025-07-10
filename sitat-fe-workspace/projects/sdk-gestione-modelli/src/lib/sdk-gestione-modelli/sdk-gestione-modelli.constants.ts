export class SdkGestioneModelliConstants {

    public static get AUTHENTICATION_TOKEN_INFO(): string {
        return 'AUTHENTICATION_TOKEN_INFO';
    }

    public static get AUTHENTICATION_APP_CODE(): string {
        return 'AUTHENTICATION_APP_CODE';
    }

    public static get APP_CODE(): string {
        return 'APP_CODE';
    }

    public static get SESSION_EXPIRED(): string {
        return 'SESSION_EXPIRED';
    }


    public static get SA_INFO_SELECT(): string {
        return 'saInfo';
    }

    public static get LOCAL_STORAGE_PREFIX(): string {
        return 'SSA';
    }

    public static get USER_PROFILE_DISPATCHER(): string {
        return 'USER';
    }

    public static get USER_PROFILE_SELECT(): string {
        return 'userProfile';
    }

    public static get RICERCA_MODELLI_TABELLATI(): Array<string> {
        return [
            'TipoDocumentoModello',
            'DimensioneDocumento',
            'ListaTabellati'
        ];
    }


    public static get LISTA_MODELLI_TABELLATI(): Array<string> {
        return [
            'TipoDocumentoModello'
        ];
    }

    public static get LISTA_MODELLI_PREDISPOST_TABELLATI(): Array<string> {
        return [
            'TipoDocumentoModello'
        ];
    }

    public static get LISTA_PARAMETRI_TABELLATI(): Array<string> {
        return [
            'TipoParametroModello',
            'ListaTabellati'
        ];
    }

    public static get DETTAGLIO_MODELLO_TABELLATI(): Array<string> {
        return [
            'TipoDocumentoModello',
            'ListaTabellati'
        ];
    }

    public static get DETTAGLIO_PARAMETRO_TABELLATI(): Array<string> {
        return [
            'TipoParametroModello',
            'ListaTabellati'
        ];
    }

    public static get SEARCH_FORM_MODELLI_DISPATCHER(): string {
        return 'searchFormModelliD';
    }

    public static get SEARCH_FORM_MODELLI_SELECT(): string {
        return 'searchFormModelliS';
    }

    public static get RESPONSE_N(): string {
        return 'N';
    }

    public static get RESPONSE_Y(): string {
        return 'Y';
    }

    public static get CODIFICA_AUTOMATICA_DEFAULT_CODAPP(): string {
        return 'G_';
    }

    public static get GESTIONE_TABELLATI_TABELLATO_BLOCCATO(): string {
        return 'GESTIONE_TABELLATI_TABELLATO_BLOCCATO';
    }

    public static get OU_GESTIONE_MODELLI_COMPLETA(): string {
        return 'ou50';
    }

    public static get OU_GESTIONE_MODELLI_SOLO_MODELLI_PERSONALI(): string {
        return 'ou51';
    }
}