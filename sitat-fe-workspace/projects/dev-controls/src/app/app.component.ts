import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { SdkLocaleService } from '@maggioli/sdk-commons';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  constructor(translate: TranslateService, private sdkLocaleService: SdkLocaleService) {
    // this language will be used as a fallback when a translation isn't found in the current language
    translate.setDefaultLang('it');

    // the lang to use, if the lang isn't available, it will use the current loader to get them
    translate.use('it');
  }

  ngOnInit() {
    // this.localeId = 'it';
    this.sdkLocaleService.locale = 'it';
    this.sdkLocaleService.currency = 'EUR';
  }
}
