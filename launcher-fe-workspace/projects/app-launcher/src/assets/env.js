(function(window) {
  window["env"] = window["env"] || {};

  // Environment variables

  window["env"]["production"] = true;
  window["env"]["ENV"] = "PRODUCTION";
  window["env"]["LOGIN_MODE"] = 1;
  window["env"]["LOGOUT_PATH"] = "/Shibboleth.sso/Logout?return=https%3A%2F%2Fsicopat2.provincia.tn.it%2Flauncher-fe%2F";
  window["env"]["PANELS_CONFIGURATION"] = {
    "W9-PT": {
      "CONTEXT_URL": "../sicopatprogrammi-fe-or/#/",
      "SSO": [
        {
          "AUTHENTICATION_SSO_CODE": "SSO",
          "AUTHENTICATION_SSO_ENABLED": true,
          "AUTHENTICATION_SSO_LOGIN_URL": "../launcher-fe-or/login.html"
        }
      ],
      "AUTHENTICATION_INTERNAL_ENABLED": true,
      "AUTHENTICATION_INTERNAL_LOGIN_URL": "../sicopatprogrammi-fe-or/#/page/internal-login-page",
      "AUTHENTICATION_CHECK_USER_BASE_URL": "../rest/vigilanza-autenticazione-ms-or/v1/protected/gestioneUtenti",
      "AUTHENTICATION_GET_TOKEN_BASE_URL": "../rest/vigilanza-autenticazione-ms-or/v1/public/gestioneUtenti",
      "AUTHENTICATION_TABELLATI_BASE_URL": "../rest/vigilanza-tabellati-ms-or/v1/public/gestioneTabellati"
    },
    "W9-AEC": {
      "CONTEXT_URL": "../sicopatcontratti-fe-or/#/",
      "SSO": [
        {
          "AUTHENTICATION_SSO_CODE": "SSO",
          "AUTHENTICATION_SSO_ENABLED": true,
          "AUTHENTICATION_SSO_LOGIN_URL": "../launcher-fe-or/login.html"
        },
      ],
      "AUTHENTICATION_INTERNAL_ENABLED": true,
      "AUTHENTICATION_INTERNAL_LOGIN_URL": "../sicopatcontratti-fe-or/#/page/internal-login-page",
      "AUTHENTICATION_CHECK_USER_BASE_URL": "../rest/vigilanza-autenticazione-ms-or/v1/protected/gestioneUtenti",
      "AUTHENTICATION_GET_TOKEN_BASE_URL": "../rest/vigilanza-autenticazione-ms-or/v1/public/gestioneUtenti",
      "AUTHENTICATION_TABELLATI_BASE_URL": "../rest/vigilanza-tabellati-ms-or/v1/public/gestioneTabellati"
    },
    "W9-INBOX": {
      "CONTEXT_URL": "../sicopatinbox-fe-or/#/",
      "SSO": [
        {
          "AUTHENTICATION_SSO_CODE": "SSO",
          "AUTHENTICATION_SSO_ENABLED": true,
          "AUTHENTICATION_SSO_LOGIN_URL": "../launcher-fe-or/login.html"
        }
      ],
      "AUTHENTICATION_INTERNAL_ENABLED": true,
      "AUTHENTICATION_INTERNAL_LOGIN_URL": "../sicopatinbox-fe-or/#/page/internal-login-page",
      "AUTHENTICATION_CHECK_USER_BASE_URL": "../rest/vigilanza-autenticazione-ms-or/v1/protected/gestioneUtenti",
      "AUTHENTICATION_GET_TOKEN_BASE_URL": "../rest/vigilanza-autenticazione-ms-or/v1/public/gestioneUtenti",
      "AUTHENTICATION_TABELLATI_BASE_URL": "../rest/vigilanza-tabellati-ms-or/v1/public/gestioneTabellati"
    }
  };
  window["env"]["GESTIONE_UTENTI_PUBLIC_BASE_URL"] = "../sso-integr-ms-or/gestioneUtenti/public/v1";
  window["env"]["SPID_AUTHENTICATION_BASE_URL"]="http://localhost:8080/ssoAuthentication";
})(this);