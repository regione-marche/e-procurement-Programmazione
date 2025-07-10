import { Injectable, Injector } from "@angular/core";
import { IDictionary, SdkBaseService, SdkProvider } from "@maggioli/sdk-commons";
import { Observable, of } from "rxjs";

// componente creato ma attualmente la funzionalità è usata tramite page, questo è utile in caso di conversione
@Injectable()
export class SdkModelliPredispostiModalProvider extends SdkBaseService implements SdkProvider {
    constructor(inj: Injector) {
        super(inj);
    }


    run(args: IDictionary<any> | any): Observable<IDictionary<any>> {
        this.logger.debug('SdkModelliPredispostiModalProvider', args);
        if (args.buttonCode === 'gestione-modelli-list') {
            this.ApriModaleModelli(args);
        }
        return of(args);
    }

    private ApriModaleModelli(args): void {
        let isModelliConfig = args.config;
        isModelliConfig.modalComponentConfigModelli = {
            ...isModelliConfig.modalComponentConfigModelli,
            ...args.config
        }
        args.modalConfig = {
            ...args.modalConfig,
            component: "lista-modelli-modal-widget",
            componentConfig: isModelliConfig.modalComponentConfigModelli
        };
        args.modalConfigObs.next(args.modalConfig);
        args.modalConfig.openSubject.next(true);
    }

}