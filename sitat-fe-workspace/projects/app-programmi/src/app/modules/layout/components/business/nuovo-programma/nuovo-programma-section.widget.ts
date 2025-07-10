import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { map } from 'rxjs/operators';
import { InizNuovoProgramma } from '../../../../models/programmi/programmi.model';

import { ProgrammaAbstractWidget } from '../abstract/programma-abstract.widget';

@Component({
    templateUrl: `nuovo-programma-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class NuovoProgrammaSectionWidget extends ProgrammaAbstractWidget {

    // #region Variables

    @HostBinding('class') classNames = `nuovo-programma-section`;
    

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
