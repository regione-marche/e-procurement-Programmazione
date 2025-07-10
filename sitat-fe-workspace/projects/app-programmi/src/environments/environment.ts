// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: window['env']['production'] || false,
  AUTHENTICATION_BASE_URL: window['env']['AUTHENTICATION_BASE_URL'] || '',
  GESTIONE_AUTENTICAZIONE_MS_BASE_URL: window['env']['GESTIONE_AUTENTICAZIONE_MS_BASE_URL'] || '',
  GESTIONE_AUTENTICAZIONE_MS_PUBLIC_BASE_URL: window['env']['GESTIONE_AUTENTICAZIONE_MS_PUBLIC_BASE_URL'] || '',
  GESTIONE_TABELLATI_BASE_URL: window['env']['GESTIONE_TABELLATI_BASE_URL'] || '',
  GESTIONE_TABELLATI_PUBLIC_BASE_URL: window['env']['GESTIONE_TABELLATI_PUBLIC_BASE_URL'] || '',
  GESTIONE_PROGRAMMI_BASE_URL: window['env']['GESTIONE_PROGRAMMI_BASE_URL'] || '',
  PUBBLICAZIONE_PROGRAMMI_BASE_URL: window['env']['PUBBLICAZIONE_PROGRAMMI_BASE_URL'] || '',
  APP_SECRET: window['env']['APP_SECRET'] || '',
  LOGGER_LEVEL: window['env']['LOGGER_LEVEL'] || 'WARN',
  LOGIN_MODE: window['env']['LOGIN_MODE'] || 0,
  BANDI_AVVISI_ESITI_GARA: window['env']['BANDI_AVVISI_ESITI_GARA'] || '',
  PROGRAMMAZIONE_LAVORI_BENI_SERVIZI: window['env']['PROGRAMMAZIONE_LAVORI_BENI_SERVIZI'] || '',
  DEVELOPMENT_INFO: window['env']['DEVELOPMENT_INFO'] || '',
  SINGLE_APPLICATION: window['env']['SINGLE_APPLICATION'] || false,
  GESTIONE_UTENTI_BASE_URL: window['env']['GESTIONE_UTENTI_BASE_URL'] || '',
  GESTIONE_UTENTI_PUBLIC_BASE_URL: window['env']['GESTIONE_UTENTI_PUBLIC_BASE_URL'] || '',
  OAUTH2_CONFIGURATION: window['env']['OAUTH2_CONFIGURATION'] || '',
  LOGIN_MODE_EXCLUDED_BASE_URL: window['env']['LOGIN_MODE_EXCLUDED_BASE_URL'] || [],
  GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL: window['env']['GESTIONE_UTENTI_AMMINISTRAZIONE_BASE_URL'] || '',
  INTERNAL_LOGIN_BASE_URL: window['env']['INTERNAL_LOGIN_BASE_URL'] || '',
  APP_CODE: window['env']['APP_CODE'] || 'W9-PT',
  APP_LAUNCHER_CONTEXT_URL: window['env']['APP_LAUNCHER_CONTEXT_URL'] || '',
  GESTIONE_MODELLI_BASE_URL: window["env"]['GESTIONE_MODELLI_BASE_URL'] || '',
  GESTIONE_PDF_BASE_URL: window['env']['GESTIONE_PDF_BASE_URL'] || '',
  GESTIONE_REPORTS_LISTA_URL: window['env']['GESTIONE_REPORTS_LISTA_URL'] || '',
  // Se viene valorizzata allora uso il nuovo captcha altrimenti quello vecchio solo frontend (per toscana,veneto,emilia romagna, trento)
  FRIENDLY_CAPTCHA_SITE_KEY: window['env']['FRIENDLY_CAPTCHA_SITE_KEY'] || ''
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
