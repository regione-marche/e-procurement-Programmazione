import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkFormBuilderField, SdkTextOutput } from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isObject, isString, toString } from 'lodash-es';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseInidoneitaContributivaEntry } from '../../../../../../models/fasi/fase-inidoneita-contributiva.model';
import { FaseInidoneitaContributivaService } from '../../../../../../services/fasi/fase-inidoneita-contributiva.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';


@Component({
    templateUrl: `dettaglio-fase-inidoneita-contributiva-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class DettaglioFaseInidoneitaContributivaSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class')
    public classNames = `dettaglio-fase-inidoneita-contributiva-section`;

    private fase: FaseInidoneitaContributivaEntry;

    // #endregion

    constructor(injector: Injector, cdr: ChangeDetectorRef) {
        super(injector, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        this.loadListaTabellati = true;

        super.onInit();

        this.modalConfig = {
            code: 'modal',
            title: '',
            openSubject: new Subject()
        };
        this.modalConfigObs.next(this.modalConfig);
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get faseInidoneitaContributivaService(): FaseInidoneitaContributivaService { return this.injectable(FaseInidoneitaContributivaService) }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void { }

    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: config.modalComponent,
                componentConfig: config.modalComponentConfig
            };

            if (config.code === 'nomest') {
                this.modalConfig.componentConfig = {
                    ...this.modalConfig.componentConfig,
                    impresa: this.fase.impresa
                }
            }

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    // #endregion

    // #region Protected
    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseInidoneitaContributivaEntry, dynamicField?: boolean) => {

        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        if (field.code.startsWith('data-') && field.type === 'TEXT') {
            const value = get(restObject, field.mappingInput);
            if (value != null) {
                field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);
                key = field.data;
                mapping = false;
            }
        }


        if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
            [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
        }

        if(field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
            field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
        }

        return {
            mapping,
            field
        };
    }

    protected tabellati(): Array<string> {
        return Constants.FASE_INIDONEITA_CONTRIBUTIVA_TABELLATI;
    }

    protected loadDettaglio = (): Observable<FaseInidoneitaContributivaEntry> => {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseInidoneitaContributivaEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseInidoneitaContributivaEntry> {
        return () => {
            return this.faseInidoneitaContributivaService.getFase(codGara, codLotto, progressivo);
        }
    }

    // #endregion
}
