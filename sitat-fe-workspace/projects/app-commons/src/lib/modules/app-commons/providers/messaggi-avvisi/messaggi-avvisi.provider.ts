import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, UserProfile } from '@maggioli/sdk-commons';
import { SdkModalConfig } from '@maggioli/sdk-controls';
import { get } from 'lodash-es';
import { Observable, Observer, Subject } from 'rxjs';

import { AbilitazioniUtilsService } from '../../services/utils/abilitazioni-utils.service';

export interface MessaggiAvvisiProviderArgs extends IDictionary<any> {
    config: IDictionary<any>;
    modalConfig: SdkModalConfig<any, void, any>;
    modalConfigObs: Subject<SdkModalConfig<any, void, any>>;
    userProfile: UserProfile;
}

@Injectable()
export class MessaggiAvvisiProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: MessaggiAvvisiProviderArgs): Observable<IDictionary<any>> {
        this.logger.debug('MessaggiAvvisiProvider', args);

        let userProfile: UserProfile = args.userProfile;
        if (userProfile != null) {
            let isAmministratore: boolean = this.abilitazioniUtilsService.isAmministratore(userProfile.abilitazioni);
            if (isAmministratore === true) {
                // se l'utente e' amministratore allora apro il modale

                let modalConfig = args.modalConfig;
                modalConfig = {
                    ...modalConfig,
                    component: get(args.config, 'modalComponent'),
                    componentConfig: get(args.config, 'modalComponentConfig')
                };
                let modalConfigObs = args.modalConfigObs;
                modalConfigObs.next(modalConfig);
                modalConfig.openSubject.next(true);
            }
        }

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(args);
            ob.complete();
        });
    }

    // #region Getters

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    // #endregion
}