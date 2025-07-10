import { Injectable, Injector } from "@angular/core";
import { IDictionary, SdkBaseService, SdkProvider } from "@maggioli/sdk-commons";

@Injectable()
export class UltimiAccessiVisibleProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): boolean {

        this.logger.debug('UltimiAccessiVisibleProvider', args);

        return true;
    }
}