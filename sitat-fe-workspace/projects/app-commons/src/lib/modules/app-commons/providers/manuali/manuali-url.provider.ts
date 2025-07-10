import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider, SdkSessionStorageService } from '@maggioli/sdk-commons';
import { Constants } from '../../app-commons.constants';

@Injectable()
export class ManualiUrlProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): string {
        this.logger.debug('ManualiUrlProvider', args);
        return this.sdkSessionStorageService.getItem<string>(Constants.MANUALI_URL_STORAGE_CODE, Constants.LOCAL_STORAGE_PREFIX);
    }

    // #region Getters

    private get sdkSessionStorageService(): SdkSessionStorageService { return this.injectable(SdkSessionStorageService) }

    // #endregion
}