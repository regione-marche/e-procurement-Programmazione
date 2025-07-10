(function(window) {
  window["env"] = window["env"] || {};

  // Environment variables

  window["env"]["production"] = false;
  window["env"]["ENV"] = "DEVELOPMENT";
  window["env"]["AUTHENTICATION_BASE_URL"] = "http://localhost:8080/rest/vigilanza-autenticazione-ms-sa/v1/protected/gestioneUtenti";
  window["env"]["GESTIONE_AUTENTICAZIONE_MS_BASE_URL"] = "http://localhost:8080/rest/vigilanza-autenticazione-ms-sa/v1/protected/gestioneUtenti";
  window["env"]["GESTIONE_AUTENTICAZIONE_MS_PUBLIC_BASE_URL"] = "http://localhost:8080/rest/vigilanza-autenticazione-ms-sa/v1/public/gestioneUtenti";
  window["env"]["GESTIONE_TABELLATI_BASE_URL"] = "http://localhost:8081/rest/vigilanza-tabellati-ms-sa/v1/protected/gestioneTabellati";
  window["env"]["GESTIONE_TABELLATI_PUBLIC_BASE_URL"] = "http://localhost:8081/rest/vigilanza-tabellati-ms-sa/v1/public/gestioneTabellati";
  window["env"]["GESTIONE_AVVISI_BASE_URL"] = "http://localhost:8082/rest/vigilanza-contratti-ms-sa/v1/protected/gestioneAvvisi";
  window["env"]["GESTIONE_ATTI_GENERALI_BASE_URL"] = "http://localhost:8082/rest/vigilanza-contratti-ms-sa/v1/protected/gestioneAttiGenerali";
  window["env"]["PUBBLICAZIONE_AVVISI_BASE_URL"] = "http://localhost:8084/rest/vigilanza-pubblatti-ms-or/v1/protected/pubblicazioneAvvisi";
  window["env"]["GESTIONE_CONTRATTI_BASE_URL"] = "http://localhost:8082/rest/vigilanza-contratti-ms-sa/v1/protected/gestioneContratti";
  window["env"]["PUBBLICAZIONE_ATTI_BASE_URL"] = "http://localhost:8084/rest/vigilanza-pubblatti-ms-or/v1/protected/pubblicazioneAtti";
  window["env"]["PUBBLICAZIONE_CONTRATTI_BASE_URL"] = "http://localhost:8086/rest/vigilanza-pubblcontratti-ms-or/v1/protected/publicazioneContratti";
  window["env"]["APP_SECRET"] = "74e4c51f-8101-41c8-92f1-0c9bd217c1ef";
  window["env"]["CLIENT_ID"] = "SItatSA";
  window["env"]["LOGGER_LEVEL"] = "DEBUG";
  window["env"]["LOGIN_MODE"] = 1;
  window["env"]["INVIO_MAIL_RICHIESTA_SIMOG"] = true;
  window["env"]["BANDI_AVVISI_ESITI_GARA"] = "https://sicopat-test.tndigit.it/pubblicazioni/";
  window["env"]["PROGRAMMAZIONE_LAVORI_BENI_SERVIZI"] = "https://sicopat-test.tndigit.it/pubblicazioni/#/programmi/ricerca";
  window["env"]["DEVELOPMENT_INFO"] = "";
  window["env"]["SINGLE_APPLICATION"] = false;
  window["env"]["INTERNAL_LOGIN_BASE_URL"] = "http://localhost:8880/sso-integr-ms/internalAuthentication/v1";
  window["env"]["GESTIONE_UTENTI_BASE_URL"] = "http://localhost:8880/sso-integr-ms/gestioneUtenti/v1";
  window["env"]["GESTIONE_UTENTI_PUBLIC_BASE_URL"] = "http://localhost:8880/sso-integr-ms/gestioneUtenti/public/v1";
  window["env"]["GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL"] = "http://localhost:8880/sso-integr-ms/amministrazione/v1";
  window["env"]["GESTIONE_REPORTS_LISTA_URL"] = "http://localhost:8899/rest/gestione-reports-ms/v1/protected/gestioneReports";
  window["env"]["APP_LAUNCHER_CONTEXT_URL"] = "http://localhost:8100/#/";
  window["env"]["LOGOUT_PATH"] = "http://localhost:8100/#/";
  window["env"]["FRIENDLY_CAPTCHA_SITE_KEY"] = "FCMV995O03V7RIMQ";
  window["env"]["DOCASSOCIATI_MS_BASE_URL"] = "http://localhost:8090/rest/docassociati-ms/v1/protected";
})(this);