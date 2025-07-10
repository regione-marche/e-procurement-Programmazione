import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { ParamMap } from '@angular/router';
import { Observable } from 'rxjs';

import { ProgrammaEntry } from '../../../../models/programmi/programmi.model';
import { ProgrammaAbstractWidget } from '../abstract/programma-abstract.widget';

@Component({
    templateUrl: `modifica-programma-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaProgrammaSectionWidget extends ProgrammaAbstractWidget {

    // #region Variables

    @HostBinding('class') classNames = `modifica-programma-section`;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    protected onInit(): void {
        super.onInit();
    }

    protected onAfterViewInit(): void {

        this.loadQueryParams();
        let factory = this.dettaglioProgrammaFactory(this.idProgramma);
        this.requestHelper.begin(factory, this.messagesPanel).subscribe((result: ProgrammaEntry) => {
            this.programma = result;
            if (this.programma.idRicevuto) {
                this.setUpdateState(false);
                history.back();
            }
            super.onAfterViewInit();
        });

    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        super.onConfig(config);
    }

    // #region Private

    private dettaglioProgrammaFactory(idProgramma: string): () => Observable<ProgrammaEntry> {
        return () => {
            return this.programmiService.dettaglioProgramma(idProgramma);
        }
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idProgramma = paramsMap.get('idProgramma');
    }

    // #endregion

    // #region Getters

    // #endregion
}
