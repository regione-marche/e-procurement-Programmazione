export class SdkGestioneUtentiConstants {

    public static get LOGIN_UNAUTHORIZED(): string {
        return 'LOGIN_UNAUTHORIZED';
    }

    public static get LOGIN_MAX_TENTATIVI(): string {
        return 'LOGIN_MAX_TENTATIVI';
    }

    public static get LOGIN_ACCOUNT_EXPIRED(): string {
        return 'LOGIN_ACCOUNT_EXPIRED';
    }

    public static get LOGIN_PASSWORD_EXPIRED(): string {
        return 'LOGIN_PASSWORD_EXPIRED';
    }

    public static get LOGIN_FIRST_ACCESS(): string {
        return 'LOGIN_FIRST_ACCESS';
    }

    public static get SA_INFO_SELECT(): string {
        return 'saInfo';
    }

    public static get USER_PROFILE_DISPATCHER(): string {
        return 'USER';
    }

    public static get USER_PROFILE_SELECT(): string {
        return 'userProfile';
    }

    public static get LOCAL_STORAGE_PREFIX(): string {
        return 'SSA';
    }

    public static get RICERCA_UTENTI_TABELLATI(): Array<string> {
        return [
            'UfficioAppartenenza',
            'CategoriaUtente'
        ];
    }

    public static get RICERCA_EVENTI_TABELLATI(): Array<string> {
        return [
            'LivelloEvento'
        ];
    }

    // Store
    public static get SEARCH_FORM_UTENTI_DISPATCHER(): string {
        return 'searchFormUtentiD';
    }

    public static get SEARCH_FORM_UTENTI_SELECT(): string {
        return 'searchFormUtentiS';
    }

    public static get SEARCH_FORM_GESTIONE_CONFIGURAZIONI_DISPATCHER(): string {
        return 'searchFormGestioneConfigurazioniD';
    }

    public static get SEARCH_FORM_GESTIONE_CONFIGURAZIONI_SELECT(): string {
        return 'searchFormGestioneConfigurazioniS';
    }

    public static get SEARCH_FORM_GESTIONE_EVENTI_DISPATCHER(): string {
        return 'searchFormGestioneEventiD';
    }

    public static get SEARCH_FORM_GESTIONE_EVENTI_SELECT(): string {
        return 'searchFormGestioneEventiS';
    }

    public static get SEARCH_FORM_GESTIONE_TABELLATI_DISPATCHER(): string {
        return 'searchFormGestioneTabellatiD';
    }

    public static get SEARCH_FORM_GESTIONE_TABELLATI_SELECT(): string {
        return 'searchFormGestioneTabellatiS';
    }

    public static get OP_100_LDAP(): string {
        return 'OP100';
    }

    public static get OU_89_AMMINISTRATORE(): string {
        return 'ou89';
    }

    public static get RESPONSE_N(): string {
        return 'N';
    }

    public static get RESPONSE_Y(): string {
        return 'Y';
    }

    public static get OU_GESTIONE_UTENTI_COMPLETA(): string {
        return 'ou11';
    }

    public static get OU_GESTIONE_UTENTI_OU12(): string {
        return 'ou12';
    }

    public static get OU_INSERIMENTO_NOTE(): string {
        return 'ou59';
    }

    public static get OU_BLOCCA_MODIFICA_UFFICI_INTESTATARI(): string {
        return 'ou214';
    }

    public static get OU_CONTROLLI_GDPR(): string {
        return 'ou39';
    }

    public static get OP_101_GESTIONE_UTENTI(): string {
        return 'OP101';
    }

    public static get OU_ABILITA_TUTTI_UFFICI_INTESTATARI(): string {
        return 'ou238';
    }

    public static get CODIFICA_AUTOMATICA_DEFAULT_CODAPP(): string {
        return 'G_';
    }

    public static get GESTIONE_TABELLATI_TABELLATO_BLOCCATO(): string {
        return 'GESTIONE_TABELLATI_TABELLATO_BLOCCATO';
    }

    public static get OU_MENU_STRUMENTI(): string {
        return 'ou30';
    }

    public static get OU_GESTIONE_MODELLI_COMPLETA(): string {
        return 'ou50';
    }

    public static get OU_GESTIONE_MODELLI_SOLO_MODELLI_PERSONALI(): string {
        return 'ou51';
    }
}