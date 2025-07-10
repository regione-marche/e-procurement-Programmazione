import { Type } from '@angular/core';
import { IDictionary, SdkAbstractWidget } from '@maggioli/sdk-commons';
import { SdkRichiestaAssistenzaCompletataComponent, SdkRichiestaAssistenzaComponent } from '@maggioli/sdk-gestione-utenti';
import {
    SdkLayoutBreadcrumbsWidget,
    SdkLayoutContentWidget,
    SdkLayoutFooterWidget,
    SdkLayoutHeaderBottomWidget,
    SdkLayoutHeaderMidWidget,
    SdkLayoutHeaderTopWidget,
    SdkLayoutHeaderTopBandWidget,
    SdkLayoutSectionWidget,
    SdkLayoutTitleWidget,
} from '@maggioli/sdk-widgets';

import { IndexSectionWidget } from './modules/layout/components/business/index/index-section.widget';
import { LoginSectionWidget } from './modules/layout/components/business/login/login-section.widget';
import {
    RegistrazioneUtenteCompletataSectionWidget,
} from './modules/layout/components/business/registrazione-utente-completata/registrazione-utente-completata-section.widget';
import {
    RegistrazioneUtenteSectionWidget,
} from './modules/layout/components/business/registrazione-utente/registrazione-utente-section.widget';
import { SpidLoginSectionWidget } from './modules/layout/components/business/spid-login/spid-login-section.widget';
import { OauthLoginSectionWidget } from './modules/layout/components/business/oauth-login/oauth-login-section.widget';

export function elementsMap(): IDictionary<Type<SdkAbstractWidget<any>>> {
    return {
        'sdk-layout-header-top': SdkLayoutHeaderTopWidget,
        'sdk-layout-header-top-band': SdkLayoutHeaderTopBandWidget,
        'sdk-layout-header-mid': SdkLayoutHeaderMidWidget,
        'sdk-layout-header-bottom': SdkLayoutHeaderBottomWidget,

        'sdk-layout-breadcrumbs': SdkLayoutBreadcrumbsWidget,
        'sdk-layout-section': SdkLayoutSectionWidget,

        'sdk-layout-content': SdkLayoutContentWidget,

        'sdk-layout-footer': SdkLayoutFooterWidget,

        'sdk-layout-title': SdkLayoutTitleWidget,

        'index-section': IndexSectionWidget,
        'login-section': LoginSectionWidget,
        'registrazione-utente-section': RegistrazioneUtenteSectionWidget,
        'registrazione-utente-completata-section': RegistrazioneUtenteCompletataSectionWidget,
        'spid-login-section': SpidLoginSectionWidget,
        'oauth-login-section': OauthLoginSectionWidget,

        // sdk-gestione-utenti
        'sdk-richiesta-assistenza-section': SdkRichiestaAssistenzaComponent,
        'sdk-richiesta-assistenza-completata-section': SdkRichiestaAssistenzaCompletataComponent,
    };
}
