(function(window) {
  window["env"] = window["env"] || {};

  // Environment variables

  window["env"]["production"] = true;
  window["env"]["ENV"] = "PRODUCTION";
  window["env"]["AUTHENTICATION_BASE_URL"] = "../rest/vigilanza-autenticazione-ms-sa/v1/protected/gestioneUtenti";
  window["env"]["GESTIONE_AUTENTICAZIONE_MS_BASE_URL"] = "../rest/vigilanza-autenticazione-ms-sa/v1/protected/gestioneUtenti";
  window["env"]["GESTIONE_AUTENTICAZIONE_MS_PUBLIC_BASE_URL"] = "../rest/vigilanza-autenticazione-ms-sa/v1/public/gestioneUtenti";
  window["env"]["GESTIONE_TABELLATI_BASE_URL"] = "../rest/vigilanza-tabellati-ms-sa/v1/protected/gestioneTabellati";
  window["env"]["GESTIONE_TABELLATI_PUBLIC_BASE_URL"] = "../rest/vigilanza-tabellati-ms-sa/v1/public/gestioneTabellati";
  window["env"]["GESTIONE_AVVISI_BASE_URL"] = "../rest/vigilanza-contratti-ms-sa/v1/protected/gestioneAvvisi";
  window["env"]["GESTIONE_ATTI_GENERALI_BASE_URL"] = "../rest/vigilanza-contratti-ms-sa/v1/protected/gestioneAttiGenerali";
  window["env"]["PUBBLICAZIONE_AVVISI_BASE_URL"] = "../rest/vigilanza-pubblatti-ms-or/v1/protected/pubblicazioneAvvisi";
  window["env"]["GESTIONE_CONTRATTI_BASE_URL"] = "../rest/vigilanza-contratti-ms-sa/v1/protected/gestioneContratti";
  window["env"]["PUBBLICAZIONE_ATTI_BASE_URL"] = "../rest/vigilanza-pubblatti-ms-or/v1/protected/pubblicazioneAtti";
  window["env"]["PUBBLICAZIONE_CONTRATTI_BASE_URL"] = "../rest/vigilanza-pubblcontratti-ms-or/v1/protected/publicazioneContratti";
  window["env"]["APP_SECRET"] = "74e4c51f-8101-41c8-92f1-0c9bd217c1ef";
  window["env"]["CLIENT_ID"] = "SItatSA";
  window["env"]["LOGGER_LEVEL"] = "WARN";
  window["env"]["LOGIN_MODE"] = 1;
  window["env"]["INVIO_MAIL_RICHIESTA_SIMOG"] = true;
  window["env"]["BANDI_AVVISI_ESITI_GARA"] = "https://siav2.collaudo.regione.veneto.it/pubblicazioni/";
  window["env"]["PROGRAMMAZIONE_LAVORI_BENI_SERVIZI"] = "https://siav2.collaudo.regione.veneto.it/pubblicazioni/#/programmi/ricerca";
  window["env"]["DEVELOPMENT_INFO"] = "";
  window["env"]["SINGLE_APPLICATION"] = false;
  window["env"]["INTERNAL_LOGIN_BASE_URL"] = "../rest/common/sso-integr-ms/internalAuthentication/v1";
  window["env"]["GESTIONE_UTENTI_BASE_URL"] = "../rest/common/sso-integr-ms/gestioneUtenti/v1";
  window["env"]["GESTIONE_UTENTI_PUBLIC_BASE_URL"] = "../rest/common/sso-integr-ms/gestioneUtenti/public/v1";
  window["env"]["GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL"] = "../rest/common/sso-integr-ms/amministrazione/v1";
  window["env"]["APP_LAUNCHER_CONTEXT_URL"] = "../launcher-fe/#/";
  window["env"]["LOGOUT_PATH"] = "../launcher-fe/#/";
  window["env"]["FRIENDLY_CAPTCHA_SITE_KEY"] = "FCMV995O03V7RIMQ";
  window["env"]["GESTIONE_REPORTS_LISTA_URL"] = "../rest/gestione-reports-ms/v1/protected/gestioneReports";
  window["env"]["DOCASSOCIATI_MS_BASE_URL"] = "../rest/docassociati-ms/v1/protected";
})(this);