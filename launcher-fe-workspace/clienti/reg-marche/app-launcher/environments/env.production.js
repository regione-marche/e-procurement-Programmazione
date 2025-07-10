(function(window) {
  window["env"] = window["env"] || {};

  // Environment variables

  window["env"]["production"] = true;
  window["env"]["ENV"] = "PRODUCTION";
  window["env"]["LOGIN_MODE"] = 1;
  window["env"]["LOGOUT_PATH"] = "/launcher-fe";
  window["env"]["PANELS_CONFIGURATION"] = {
    "W9-PT": {
      "CONTEXT_URL": "../programmi-fe/#/",
      "SSO": [        
        {
          "AUTHENTICATION_SSO_CODE": "SSO",
          "AUTHENTICATION_SSO_ENABLED": true,
          "AUTHENTICATION_SSO_LOGIN_URL": "https://appaltisuam.regione.marche.it/CohesionServlet/AuthenticationProgrammazione"
        }
      ],
      "AUTHENTICATION_INTERNAL_ENABLED": false,
      "AUTHENTICATION_INTERNAL_LOGIN_URL": "#",
      "AUTHENTICATION_CHECK_USER_BASE_URL": "../rest/vigilanza-autenticazione-ms-sa/v1/protected/gestioneUtenti",
      "AUTHENTICATION_GET_TOKEN_BASE_URL": "../rest/vigilanza-autenticazione-ms-sa/v1/public/gestioneUtenti",
      "AUTHENTICATION_TABELLATI_BASE_URL": "../rest/vigilanza-tabellati-ms-sa/v1/public/gestioneTabellati"
    }, 
    "W9-AEC": {
      "CONTEXT_URL": "../contratti-fe/#/",
      "SSO": [
        {
          "AUTHENTICATION_SSO_CODE": "SSO",
          "AUTHENTICATION_SSO_ENABLED": true,
          "AUTHENTICATION_SSO_LOGIN_URL": "https://appaltisuam.regione.marche.it/CohesionServlet/AuthenticationProgrammazione"
        }
      ],
      "AUTHENTICATION_INTERNAL_ENABLED": false,
      "AUTHENTICATION_INTERNAL_LOGIN_URL": "#",
      "AUTHENTICATION_CHECK_USER_BASE_URL": "../rest/vigilanza-autenticazione-ms-sa/v1/protected/gestioneUtenti",
      "AUTHENTICATION_GET_TOKEN_BASE_URL": "../rest/vigilanza-autenticazione-ms-sa/v1/public/gestioneUtenti",
      "AUTHENTICATION_TABELLATI_BASE_URL": "../rest/vigilanza-tabellati-ms-sa/v1/public/gestioneTabellati"
    }
  };
  window["env"]["GESTIONE_UTENTI_PUBLIC_BASE_URL"] = "../rest/common/sso-integr-ms/gestioneUtenti/public/v1";
  window["env"]["SPID_AUTHENTICATION_BASE_URL"]="http://localhost:8080/ssoAuthentication";
  window["env"]["FRIENDLY_CAPTCHA_SITE_KEY"] = "FCMV995O03V7RIMQ";
})(this);