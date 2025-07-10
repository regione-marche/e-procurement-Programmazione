import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { StazioneAppaltanteInfo } from '@maggioli/app-commons';

import { Constants } from '../../../../../../app.constants';
import { AvvisoEntry } from '../../../../../models/avviso/avviso.model';
import { AvvisoAbstractWidget } from '../abstract/avviso-abstract.widget';

@Component({
    templateUrl: `modifica-avviso-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaAvvisoSectionWidget extends AvvisoAbstractWidget {

    @HostBinding('class') classNames = `modifica-avviso-section`;

    private idAvviso: string;
    private stazioneAppaltante: string;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.SA_INFO_SELECT).subscribe((saInfo: StazioneAppaltanteInfo) => {
            this.stazioneAppaltanteInfo = saInfo;
        }));
        super.onInit();
    }

    protected onAfterViewInit(): void {
        let queryParams: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.idAvviso = queryParams.get('idAvviso');
        this.stazioneAppaltante = queryParams.get('stazioneAppaltante');
        let dettaglioAvvisoFactory = this.getDettaglioAvvisoFactory();
        this.requestHelper.begin(dettaglioAvvisoFactory, this.messagesPanel).subscribe((response: AvvisoEntry) => {
            this.avviso = response;
            super.onAfterViewInit();
        });
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        super.onConfig(config);
    }

    private getDettaglioAvvisoFactory() {
        return () => {
            return this.avvisiService.dettaglioAvviso(this.idAvviso, this.stazioneAppaltante);
        }
    }

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    // #endregion

}