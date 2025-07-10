export class Constants {
    public static get APP_NAME(): string {
        return 'app-programmi';
    }



    // serve per salvare l'oggetto AuthenticationTokenInfo dopo l'arrivo sull'applicazione
    public static get AUTHENTICATION_TOKEN_INFO(): string {
        return 'AUTHENTICATION_TOKEN_INFO';
    }

    // serve per salvare l'appCode di autenticazione
    public static get AUTHENTICATION_APP_CODE(): string {
        return 'AUTHENTICATION_APP_CODE';
    }

    public static get USER_PROFILE_TO_LAUNCHER(): string {
        return 'USER_PROFILE_TO_LAUNCHER';
    }

    // serve per settare il codice applicativo del modulo selezionato
    public static get APP_CODE(): string {
        return 'APP_CODE';
    }

    public static get SESSION_EXPIRED(): string {
        return 'SESSION_EXPIRED';
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

    public static get TIPO_PROGRAMMA(): string {
        return 'TipoProgramma';
    }

    public static get OPERE_INCOMPIUTE_TABELLATI(): Array<string> {
        return [
            'Determinazioni',
            'Ambito',
            'Causa',
            'StatoRealizzazione',
            'DestinazioneUso',
            'CategoriaIntervento',
            'TipologiaIntervento',
            'TipoProprieta',
            'ProgrammaDismissione',
            'TipoDisponibilita',
            'TrasferimentoImmobile',
            'ImmobileDisponibile',
            'Comuni',
            'Province'
        ];
    }

    public static get DETT_PROG(): Array<string> {
        return ['TipoNorma'];
    }

    public static get DETT_INT_NR(): Array<string> {
        return ['Priorita'];
    }

    public static get REGIONI(): string {
        return 'Regioni';
    }

    public static get COMUNI(): string {
        return 'Comuni';
    }

    public static get PROVINCE(): string {
        return 'Province';
    }

    public static get INTERVENTO_TABELLATI(): Array<string> {
        return [
            'TrasferimentoImmobile',
            'ProgrammaDismissione',
            'StatoProgettazione',
            'Finalita',
            'CategoriaIntervento',
            'MesePrevisto',
            'Priorita',
            'TipologiaIntervento',
            'TipoAppalto',
            'UnitaMisura',
            'ProceduraAffidamento',
            'Acquisto',
            'VariatoLavori',
            'VariatoAcquisti',
            'Valutazione',
            'AcquistoRicompreso',
            'TipologiaCapitalePrivato',
            'ImmobileDisponibile'
        ];
    }

    public static get SERVER_DATE_FORMAT(): string {
        return 'yyyy-MM-dd';
    }

    public static get INTERVENTO_NON_RIPROPOSTO_TABELLATI(): Array<string> {
        return [
            'Priorita'
        ];
    }

    public static get LOCAL_STORAGE_PREFIX(): string {
        return 'SSA';
    }

    public static get LOGOUT_PATH(): string {
        return 'LOGOUT_PATH';
    }

    public static get LISTA_STAZIONI_APPALTANTI_COUNT_DISPATCHER(): string {
        return 'STAZIONI_APPALTANTI_COUNT';
    }

    public static get LISTA_STAZIONI_APPALTANTI_COUNT_SELECT(): string {
        return 'stazioniAppaltantiCount';
    }

    public static get MANUALI_URL_STORAGE_CODE(): string {
        return 'MANUALI_URL';
    }

}