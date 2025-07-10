// import 'zone.js';

// import 'hammerjs';

import { enableProdMode, ViewEncapsulation } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppContrattiModule } from './app/app.module';
import { environment } from './environments/environment';

if (environment.production) {
  enableProdMode();
}

platformBrowserDynamic().bootstrapModule(AppContrattiModule, {
  defaultEncapsulation: ViewEncapsulation.Emulated,
  ngZone: 'zone.js'
});