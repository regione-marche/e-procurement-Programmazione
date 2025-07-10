import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';

import { AvvisoAbstractWidget } from '../abstract/avviso-abstract.widget';

@Component({
    templateUrl: `nuovo-avviso-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovoAvvisoSectionWidget extends AvvisoAbstractWidget {

    @HostBinding('class') classNames = `nuovo-avviso-section`;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    protected onInit(): void {
        super.onInit();
    }

    protected onAfterViewInit(): void {
        super.onAfterViewInit();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        super.onConfig(config);
    }

}
