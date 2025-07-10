import { Inject, Injectable, Injector } from '@angular/core';
import { IDictionary, SDK_APP_CONFIG, SdkAppEnvConfig, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';

@Injectable()
export class ApplicazioniVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector, @Inject(SDK_APP_CONFIG) public appConfig: SdkAppEnvConfig) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {
        if (this.appConfig != null && this.appConfig.environment != null) {
            if (this.appConfig.environment.SINGLE_APPLICATION != null) {
                return !!this.appConfig.environment.SINGLE_APPLICATION ? false : true;
            }
        }
        return true;
    }
}