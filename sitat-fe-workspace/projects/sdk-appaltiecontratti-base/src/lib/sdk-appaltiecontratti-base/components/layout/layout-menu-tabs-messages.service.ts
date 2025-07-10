import { Injectable, Injector } from '@angular/core';
import { SdkBaseBehaviorSubjectService } from '@maggioli/sdk-commons';
import { SdkMenuTab } from '@maggioli/sdk-controls';

@Injectable({ providedIn: 'root' })
export class LayoutMenuTabsMessageService extends SdkBaseBehaviorSubjectService<Array<SdkMenuTab>> {

    constructor(inj: Injector) { super(inj, []) }

}
