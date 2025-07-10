const CUSTOMERS = {
    1: {
        code: 'default',
        description: 'Installazione di default'
    },  
    2: {
        code: 'reg-marche',
        description: 'regione-marche'
    }
};

const PROJECT_TYPES = {
    1: {
        code: 'sa',
        description: 'Sa'
    }
};

const PROJECT_ENVIRONMENTS = {   
    'app-contratti': {
        1: {
            code: 'development',
            description: 'Development'
        },
        2: {
            code: 'production',
            description: 'Production'
        },
        3: {
            code: 'staging',
            description: 'Staging'
        }
    },
    'app-programmi': {
        1: {
            code: 'development',
            description: 'Development'
        },
        2: {
            code: 'production',
            description: 'Production'
        },
        3: {
            code: 'staging',
            description: 'Staging'
        }
    }
};

const PROJECT_STYLES = {    
    'app-contratti': [
        {
            src: ['cms', 'app', 'app-contratti.scss'],
            dest: ['cms', 'app', 'app-contratti.css']
        },
        {
            src: ['cms', 'layouts', 'base-layout.scss'],
            dest: ['cms', 'layouts', 'base-layout.css']
        },
        {
            src: ['cms', 'layouts', 'base-menu-tabs-layout.scss'],
            dest: ['cms', 'layouts', 'base-menu-tabs-layout.css']
        },
        {
            src: ['cms', 'pages', 'home-page.scss'],
            dest: ['cms', 'pages', 'home-page.css']
        },
        {
            src: ['cms', 'layouts', 'choose-ente-layout.scss'],
            dest: ['cms', 'layouts', 'choose-ente-layout.css']
        },
        {
            src: ['cms', 'layouts', 'choose-profile-layout.scss'],
            dest: ['cms', 'layouts', 'choose-profile-layout.css']
        },
        {
            src: ['cms', 'layouts', 'login-layout.scss'],
            dest: ['cms', 'layouts', 'login-layout.css']
        },
        {
            src: ['cms', 'pages', 'nuovo-avviso-page.scss'],
            dest: ['cms', 'pages', 'nuovo-avviso-page.css']
        },
        {
            src: ['cms', 'pages', 'ricerca-avanzata-avvisi-page.scss'],
            dest: ['cms', 'pages', 'ricerca-avanzata-avvisi-page.css']
        },
        {
            src: ['cms', 'pages', 'dettaglio-avviso-page.scss'],
            dest: ['cms', 'pages', 'dettaglio-avviso-page.css']
        },
        {
            src: ['cms', 'pages', 'modifica-avviso-page.scss'],
            dest: ['cms', 'pages', 'modifica-avviso-page.css']
        },
        {
            src: ['cms', 'pages', 'ricerca-avanzata-gare-page.scss'],
            dest: ['cms', 'pages', 'ricerca-avanzata-gare-page.css']
        },
        {
            src: ['cms', 'pages', 'lista-atti-page.scss'],
            dest: ['cms', 'pages', 'lista-atti-page.css']
        },
        {
            src: ['cms', 'pages', 'lotti-atto-page.scss'],
            dest: ['cms', 'pages', 'lotti-atto-page.css']
        },
        {
            src: ['cms', 'pages', 'lista-fasi-lotto-page.scss'],
            dest: ['cms', 'pages', 'lista-fasi-lotto-page.css']
        },
        {
            src: ['cms', 'pages', 'nuova-fase-page.scss'],
            dest: ['cms', 'pages', 'nuova-fase-page.css']
        },
        {
            src: ['cms', 'pages', 'lista-imprese-aggiudicatarie-page.scss'],
            dest: ['cms', 'pages', 'lista-imprese-aggiudicatarie-page.css']
        },
        {
            src: ['cms', 'pages', 'lista-elenco-impr-inv-parte-page.scss'],
            dest: ['cms', 'pages', 'lista-elenco-impr-inv-parte-page.css']
        },
        {
            src: ['cms', 'pages', 'pubblica-gara-page.scss'],
            dest: ['cms', 'pages', 'pubblica-gara-page.css']
        },
        {
            src: ['cms', 'pages', 'pubblica-atto-page.scss'],
            dest: ['cms', 'pages', 'pubblica-atto-page.css']
        },
        {
            src: ['cms', 'pages', 'grafica-page.scss'],
            dest: ['cms', 'pages', 'grafica-page.css']
        },
        {
            src: ['cms', 'pages', 'pubblica-avviso-page.scss'],
            dest: ['cms', 'pages', 'pubblica-avviso-page.css']
        },
        {
            src: ['cms', 'pages', 'pubblica-fase-lotto-page.scss'],
            dest: ['cms', 'pages', 'pubblica-fase-lotto-page.css']
        },
        {
            src: ['cms', 'pages', 'importa-gara-simog-page.scss'],
            dest: ['cms', 'pages', 'importa-gara-simog-page.css']
        },
        {
            src: ['cms', 'pages', 'accorpamento-lotti-page.scss'],
            dest: ['cms', 'pages', 'accorpamento-lotti-page.css']
        },
        {
            src: ['cms', 'pages', 'riepilogo-accorpamento-lotti-page.scss'],
            dest: ['cms', 'pages', 'riepilogo-accorpamento-lotti-page.css']
        },
        {
            src: ['cms', 'pages', 'gestione-smartcig-page.scss'],
            dest: ['cms', 'pages', 'gestione-smartcig-page.css']
        },
        {
            src: ['cms', 'pages', 'sdk-login-page.scss'],
            dest: ['cms', 'pages', 'sdk-login-page.css']
        },
        {
            src: ['cms', 'pages', 'sdk-change-password-page.scss'],
            dest: ['cms', 'pages', 'sdk-change-password-page.css']
        },
        {
            src: ['cms', 'pages', 'spid-login-page.scss'],
            dest: ['cms', 'pages', 'spid-login-page.css']
        },
        {
            src: ['cms', 'pages', 'risultato-report-page.scss'],
            dest: ['cms', 'pages', 'risultato-report-page.css']
        },
        {
            src: ['cms', 'pages', 'crea-nuovo-report-page.scss'],
            dest: ['cms', 'pages', 'crea-nuovo-report-page.css']
        },
        {
            src: ['cms', 'pages', 'modifica-dati-generali-report-page.scss'],
            dest: ['cms', 'pages', 'modifica-dati-generali-report-page.css']
        },
        {
            src: ['cms', 'pages', 'dettaglio-page.scss'],
            dest: ['cms', 'pages', 'dettaglio-page.css']
        }
    ],
    'app-programmi': [
        {
            src: ['cms', 'app', 'app-programmi.scss'],
            dest: ['cms', 'app', 'app-programmi.css']
        },
        {
            src: ['cms', 'layouts', 'base-layout.scss'],
            dest: ['cms', 'layouts', 'base-layout.css']
        },
        {
            src: ['cms', 'layouts', 'base-menu-tabs-layout.scss'],
            dest: ['cms', 'layouts', 'base-menu-tabs-layout.css']
        },
        {
            src: ['cms', 'pages', 'home-page.scss'],
            dest: ['cms', 'pages', 'home-page.css']
        },
        {
            src: ['cms', 'layouts', 'choose-ente-layout.scss'],
            dest: ['cms', 'layouts', 'choose-ente-layout.css']
        },
        {
            src: ['cms', 'layouts', 'choose-profile-layout.scss'],
            dest: ['cms', 'layouts', 'choose-profile-layout.css']
        },
        {
            src: ['cms', 'layouts', 'login-layout.scss'],
            dest: ['cms', 'layouts', 'login-layout.css']
        },
        {
            src: ['cms', 'pages', 'dett-prog-pubblica-page.scss'],
            dest: ['cms', 'pages', 'dett-prog-pubblica-page.css']
        },
        {
            src: ['cms', 'pages', 'import-interventi-non-riproposti-page.scss'],
            dest: ['cms', 'pages', 'import-interventi-non-riproposti-page.css']
        },
        {
            src: ['cms', 'pages', 'import-interventi-page.scss'],
            dest: ['cms', 'pages', 'import-interventi-page.css']
        },
        {
            src: ['cms', 'pages', 'dett-opera-incompiuta-page.scss'],
            dest: ['cms', 'pages', 'dett-opera-incompiuta-page.css']
        },
        {
            src: ['cms', 'pages', 'nuova-opera-incompiuta-page.scss'],
            dest: ['cms', 'pages', 'nuova-opera-incompiuta-page.css']
        },
        {
            src: ['cms', 'pages', 'modifica-opera-incompiuta-page.scss'],
            dest: ['cms', 'pages', 'modifica-opera-incompiuta-page.css']
        },
        {
            src: ['cms', 'pages', 'dett-intervento-page.scss'],
            dest: ['cms', 'pages', 'dett-intervento-page.css']
        },
        {
            src: ['cms', 'pages', 'nuovo-intervento-page.scss'],
            dest: ['cms', 'pages', 'nuovo-intervento-page.css']
        },
        {
            src: ['cms', 'pages', 'modifica-intervento-page.scss'],
            dest: ['cms', 'pages', 'modifica-intervento-page.css']
        },
        {
            src: ['cms', 'pages', 'lista-archivio-tecnici-page.scss'],
            dest: ['cms', 'pages', 'lista-archivio-tecnici-page.css']
        },
        {
            src: ['cms', 'pages', 'sdk-login-page.scss'],
            dest: ['cms', 'pages', 'sdk-login-page.css']
        },
        {
            src: ['cms', 'pages', 'sdk-change-password-page.scss'],
            dest: ['cms', 'pages', 'sdk-change-password-page.css']
        },        
        {
            src: ['cms', 'pages', 'spid-login-page.scss'],
            dest: ['cms', 'pages', 'spid-login-page.css']
        }
    ]
};

const SDK_MODULES = [
    'sdk-gestione-utenti',
    'sdk-gestione-modelli',
    'sdk-widgets',
    'sdk-gestione-reports',
    'sdk-appaltiecontratti-base',
    'sdk-docassociati'
];

module.exports = {
    CUSTOMERS: Object.freeze(CUSTOMERS),
    PROJECT_TYPES: Object.freeze(PROJECT_TYPES),
    PROJECT_ENVIRONMENTS: Object.freeze(PROJECT_ENVIRONMENTS),
    PROJECT_STYLES: Object.freeze(PROJECT_STYLES),
    SDK_MODULES: Object.freeze(SDK_MODULES)
};