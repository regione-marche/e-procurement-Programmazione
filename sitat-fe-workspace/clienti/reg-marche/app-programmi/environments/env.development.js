(function(window) {
  window["env"] = window["env"] || {};

  // Environment variables

  window["env"]["production"] = false;
  window["env"]["AUTHENTICATION_BASE_URL"] = "http://localhost:8080/rest/vigilanza-autenticazione-ms-sa/v1/protected/gestioneUtenti";
  window["env"]["GESTIONE_AUTENTICAZIONE_MS_BASE_URL"] = "http://localhost:8080/rest/vigilanza-autenticazione-ms-sa/v1/protected/gestioneUtenti";
  window["env"]["GESTIONE_TABELLATI_BASE_URL"] = "http://localhost:8081/rest/vigilanza-tabellati-ms-sa/v1/protected/gestioneTabellati";
  window["env"]["GESTIONE_TABELLATI_PUBLIC_BASE_URL"] = "http://localhost:8081/rest/vigilanza-tabellati-ms-sa/v1/public/gestioneTabellati";
  window["env"]["GESTIONE_PROGRAMMI_BASE_URL"] = "http://localhost:8083/rest/vigilanza-programmi-ms-sa/v1/protected/gestioneProgrammi";
  window["env"]["PUBBLICAZIONE_PROGRAMMI_BASE_URL"] = "http://localhost:8085/rest/vigilanza-pubblprogrammi-ms-or/v1/protected/pubblicazioneProgrammi";
  window["env"]["APP_SECRET"] = "74e4c51f-8101-41c8-92f1-0c9bd217c1ef";
  window["env"]["LOGGER_LEVEL"] = "DEBUG";
  window["env"]["LOGIN_MODE"] = 1;
  window["env"]["BANDI_AVVISI_ESITI_GARA"] = "https://sicopat-test.tndigit.it/pubblicazioni/";
  window["env"]["PROGRAMMAZIONE_LAVORI_BENI_SERVIZI"] = "https://sicopat-test.tndigit.it/pubblicazioni/#/programmi/ricerca";
  window["env"]["DEVELOPMENT_INFO"] = "";
  window["env"]["SINGLE_APPLICATION"] = false;
  window["env"]["INTERNAL_LOGIN_BASE_URL"] = "http://localhost:8880/sso-integr-ms/internalAuthentication/v1";
  window["env"]["GESTIONE_UTENTI_BASE_URL"] = "http://localhost:8880/sso-integr-ms/gestioneUtenti/v1";
  window["env"]["GESTIONE_UTENTI_PUBLIC_BASE_URL"] = "http://localhost:8880/sso-integr-ms/gestioneUtenti/public/v1";
  window["env"]["GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL"] = "http://localhost:8880/sso-integr-ms/amministrazione/v1";
  window["env"]["APP_LAUNCHER_CONTEXT_URL"] = "http://localhost:8100/#/";
  window["env"]["GESTIONE_MODELLI_BASE_URL"] = "http://localhost:8090/v1/protected/gestioneModelli";
  window["env"]["LOGOUT_PATH"] = "http://localhost:8100/#/";
  window["env"]["FRIENDLY_CAPTCHA_SITE_KEY"] = "FCMV995O03V7RIMQ";
  window["env"]["GESTIONE_REPORTS_LISTA_URL"] = "http://localhost:8899/rest/gestione-reports-ms/v1/protected/gestioneReports";
})(this);