import { Inject, Injectable, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { Observable } from 'rxjs';

import { HttpRequestHelper } from '../../services/http/http-request-helper.service';
import { TabellatiService } from '../../services/tabellati/tabellati.service';

@Injectable()
export class MessaggiAvvisiStatusProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) private appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: any): Observable<IDictionary<any>> {
        this.logger.debug('MessaggiAvvisiStatusProvider', args);

        return this.requestHelper.begin(this.getCurrentMessagesStatusFactory(), null, false);
    }

    private getCurrentMessagesStatusFactory() {
        return () => {
            return this.tabellatiService.getCurrentMessagesStatus(this.appConfig.environment.GESTIONE_TABELLATI_BASE_URL);
        }
    }

    // #region Getters

    private get tabellatiService(): TabellatiService { return this.injectable(TabellatiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion
}