(function(window) {
  window["env"] = window["env"] || {};

  // Environment variables

  window["env"]["production"] = true;
  window["env"]["AUTHENTICATION_BASE_URL"] = "../rest/vigilanza-autenticazione-ms-sa/v1/protected/gestioneUtenti";
  window["env"]["GESTIONE_AUTENTICAZIONE_MS_BASE_URL"] = "../rest/vigilanza-autenticazione-ms-sa/v1/protected/gestioneUtenti";
  window["env"]["GESTIONE_TABELLATI_BASE_URL"] = "../rest/vigilanza-tabellati-ms-sa/v1/protected/gestioneTabellati";
  window["env"]["GESTIONE_TABELLATI_PUBLIC_BASE_URL"] = "../rest/vigilanza-tabellati-ms-sa/v1/public/gestioneTabellati";
  window["env"]["GESTIONE_PROGRAMMI_BASE_URL"] = "../rest/vigilanza-programmi-ms-sa/v1/protected/gestioneProgrammi";
  window["env"]["PUBBLICAZIONE_PROGRAMMI_BASE_URL"] = "../rest/vigilanza-pubblprogrammi-ms-or/v1/protected/pubblicazioneProgrammi";
  window["env"]["APP_SECRET"] = "74e4c51f-8101-41c8-92f1-0c9bd217c1ef";
  window["env"]["LOGGER_LEVEL"] = "WARN";
  window["env"]["LOGIN_MODE"] = 1;
  window["env"]["BANDI_AVVISI_ESITI_GARA"] = "https://sicopat2.provincia.tn.it/pubblicazioni/";
  window["env"]["PROGRAMMAZIONE_LAVORI_BENI_SERVIZI"] = "https://sicopat2.provincia.tn.it/pubblicazioni/#/programmi/ricerca";
  window["env"]["DEVELOPMENT_INFO"] = "";
  window["env"]["SINGLE_APPLICATION"] = false;
  window["env"]["INTERNAL_LOGIN_BASE_URL"] = "../rest/common/sso-integr-ms/internalAuthentication/v1";
  window["env"]["GESTIONE_UTENTI_BASE_URL"] = "../rest/common/sso-integr-ms/gestioneUtenti/v1";
  window["env"]["GESTIONE_UTENTI_PUBLIC_BASE_URL"] = "../rest/common/sso-integr-ms/gestioneUtenti/public/v1";
  window["env"]["GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL"] = "../rest/common/sso-integr-ms/amministrazione/v1";
  window["env"]["APP_LAUNCHER_CONTEXT_URL"] = "../launcher-fe/#/";
  window["env"]["LOGOUT_PATH"] = "../launcher-fe/#/";
  window["env"]["FRIENDLY_CAPTCHA_SITE_KEY"] = "FCMV995O03V7RIMQ";
})(this);