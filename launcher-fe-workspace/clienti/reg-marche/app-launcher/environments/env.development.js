(function(window) {
  window["env"] = window["env"] || {};

  // Environment variables

  window["env"]["production"] = false;
  window["env"]["ENV"] = "DEVELOPMENT";
  window["env"]["LOGIN_MODE"] = 1;
  window["env"]["LOGOUT_PATH"] = "/launcher-fe";
  window["env"]["PANELS_CONFIGURATION"] = {
    "W9-PT": {
      
      "CONTEXT_URL": "http://localhost:5300/#/",
      "SSO": [        
        {
          "AUTHENTICATION_SSO_CODE": "SSO",
          "AUTHENTICATION_SSO_ENABLED": true,
          "AUTHENTICATION_SSO_LOGIN_URL": "http://localhost:9090/CohesionServlet/AuthenticationPortaleAppalti",
          "AUTHENTICATION_SSO_DEFAULT_LOA": "3"
        }
      ],
      "AUTHENTICATION_INTERNAL_ENABLED": true,
      "AUTHENTICATION_INTERNAL_LOGIN_URL": "http://localhost:5300/#/page/internal-login-page",
      "AUTHENTICATION_CHECK_USER_BASE_URL": "http://localhost:8080/rest/vigilanza-autenticazione-ms-sa/v1/protected/gestioneUtenti",
      "AUTHENTICATION_GET_TOKEN_BASE_URL": "http://localhost:8080/rest/vigilanza-autenticazione-ms-sa/v1/public/gestioneUtenti",
      "AUTHENTICATION_TABELLATI_BASE_URL": "../rest/vigilanza-tabellati-ms-sa/v1/public/gestioneTabellati"
    },
    "W9-AEC": {
      "CONTEXT_URL": "http://localhost:5200/#/",
      "SSO": [
        {
          "AUTHENTICATION_SSO_CODE": "SPID",
          "AUTHENTICATION_SSO_ENABLED": true,
          "AUTHENTICATION_SSO_LOGIN_URL": "http://localhost:5200/#/page/spid-login-page",
          "AUTHENTICATION_SSO_INTERNAL_PAGE_PARAMS": { "provider": "SPID" }
        },
        {
          "AUTHENTICATION_SSO_CODE": "CIE",
          "AUTHENTICATION_SSO_ENABLED": true,
          "AUTHENTICATION_SSO_LOGIN_URL": "",
          "AUTHENTICATION_SSO_INTERNAL_PAGE_URL": "spid-login-page",
          "AUTHENTICATION_SSO_INTERNAL_PAGE_PARAMS": { "provider": "CIE" }
        }
      ],
      "AUTHENTICATION_INTERNAL_ENABLED": true,
      "AUTHENTICATION_INTERNAL_LOGIN_URL": "http://localhost:5200/#/page/internal-login-page",
      "AUTHENTICATION_CHECK_USER_BASE_URL": "http://localhost:8080/rest/vigilanza-autenticazione-ms-sa/v1/protected/gestioneUtenti",
      "AUTHENTICATION_GET_TOKEN_BASE_URL": "http://localhost:8080/rest/vigilanza-autenticazione-ms-sa/v1/public/gestioneUtenti",
      "AUTHENTICATION_TABELLATI_BASE_URL": "http://localhost:8081/rest/vigilanza-tabellati-ms-sa/v1/public/gestioneTabellati"
    }
   
  };
  window["env"]["GESTIONE_UTENTI_PUBLIC_BASE_URL"] = "http://localhost:8880/sso-integr-ms/gestioneUtenti/public/v1";
  window["env"]["SPID_AUTHENTICATION_BASE_URL"]="http://localhost:8080/ssoAuthentication";
  window["env"]["FRIENDLY_CAPTCHA_SITE_KEY"] = "FCMV995O03V7RIMQ";
})(this);