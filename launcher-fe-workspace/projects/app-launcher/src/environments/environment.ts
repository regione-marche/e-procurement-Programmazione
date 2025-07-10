// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: window['env']['production'] || false,
  ENV: window['env']['ENV'] || 'DEVELOPMENT',
  LOGIN_MODE: window['env']['LOGIN_MODE'] || 0,
  LOGOUT_PATH: window['env']['LOGOUT_PATH'] || '',
  LOGGER_LEVEL: window['env']['LOGGER_LEVEL'] || 'WARN',
  SINGLE_APP: window['env']['SINGLE_APP'] || false,
  PANELS_CONFIGURATION: window['env']['PANELS_CONFIGURATION'] || {},
  OAUTH2_CONFIGURATION: window['env']['OAUTH2_CONFIGURATION'] || '',
  LOGIN_MODE_EXCLUDED_BASE_URL: window['env']['LOGIN_MODE_EXCLUDED_BASE_URL'] || [],
  HOME_PATH: window['env']['HOME_PATH'] || '',
  SPID_AUTHENTICATION_BASE_URL: window['env']['SPID_AUTHENTICATION_BASE_URL'],
  GESTIONE_UTENTI_PUBLIC_BASE_URL: window['env']['GESTIONE_UTENTI_PUBLIC_BASE_URL'] || '',
  FRIENDLY_CAPTCHA_SITE_KEY: window['env']['FRIENDLY_CAPTCHA_SITE_KEY'] || '',
  EXTERNAL_LOGIN_PAGE: window['env']['EXTERNAL_LOGIN_PAGE'] || false
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
