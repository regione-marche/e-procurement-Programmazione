const CUSTOMERS = {
    1: {
        code: 'default',
        description: 'Installazione di default'
    },
    2: {
        code: 'reg-marche',
        description: 'Installazione di Regione Marche'
    }
};

const PROJECT_TYPES = {
    1: {
        code: 'sa',
        description: 'Sa'
    }
};

const PROJECT_ENVIRONMENTS = {
    'app-launcher': {
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
    'app-launcher': [
        {
            src: ['cms', 'app', 'app-launcher.scss'],
            dest: ['cms', 'app', 'app-launcher.css']
        },
        {
            src: ['cms', 'layouts', 'base-layout.scss'],
            dest: ['cms', 'layouts', 'base-layout.css']
        },
        {
            src: ['cms', 'pages', 'home-page.scss'],
            dest: ['cms', 'pages', 'home-page.css']
        },
        {
            src: ['cms', 'pages', 'registrazione-utente-page.scss'],
            dest: ['cms', 'pages', 'registrazione-utente-page.css']
        },
        {
            src: ['cms', 'pages', 'spid-login-page.scss'],
            dest: ['cms', 'pages', 'spid-login-page.css']
        }
    ]
};

const SDK_MODULES = [
    'sdk-gestione-utenti'
];

module.exports = {
    CUSTOMERS: Object.freeze(CUSTOMERS),
    PROJECT_TYPES: Object.freeze(PROJECT_TYPES),
    PROJECT_ENVIRONMENTS: Object.freeze(PROJECT_ENVIRONMENTS),
    PROJECT_STYLES: Object.freeze(PROJECT_STYLES),
    SDK_MODULES: Object.freeze(SDK_MODULES)
};