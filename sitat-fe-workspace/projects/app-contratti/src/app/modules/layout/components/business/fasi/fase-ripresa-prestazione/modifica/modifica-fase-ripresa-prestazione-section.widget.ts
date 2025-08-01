import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Injector,
    ViewEncapsulation,
} from '@angular/core';
import { SdkFormBuilderField } from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isString, set, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { Constants } from '../../../../../../../app.constants';
import { FaseSospensioneEntry } from '../../../../../../models/fasi/fase-sospensione.model';
import { FaseSospensioneService } from '../../../../../../services/fasi/fase-sospensione.service';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';
import { FaseRipresaPrestazioneService } from 'projects/app-contratti/src/app/modules/services/fasi/fase-ripresa-prestazione.service';



@Component({
    templateUrl: `modifica-fase-ripresa-prestazione-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseRipresaPrestazioneSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `modifica-fase-ripresa-prestazione-section`;


    private fase: FaseSospensioneEntry;


    // #endregion

    constructor(injector: Injector, cdr: ChangeDetectorRef) {
        super(injector, cdr);
    }

    // #region Hooks

    protected onInit(): void {

        // set update state
        this.setUpdateState(true);
        this.loadListaTabellati = true;
        this.updateFase = true;

        super.onInit();
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get faseRipresaPrestazioneService(): FaseRipresaPrestazioneService {
        return this.injectable(FaseRipresaPrestazioneService);
    }

    // #endregion

    // #region Public

    public manageOutputField(field: SdkFormBuilderField): void {
        // TODO
    }

    // #endregion

    // #region Protected


    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: FaseSospensioneEntry, dynamicField?: boolean) => {

        let mapping: boolean = true;

        const keyAny: any = get(restObject, field.mappingInput);
        const key: string = dynamicField === true ? field.data : toString(keyAny);

        // TODO

        

        if (field.code === 'data-verb-sosp') {
            let value = get(restObject, field.mappingInput);
            if (value != null && field.type === 'TEXT') {
                field.data = this.dateHelper.format(new Date(value), this.config.locale.dateFormat);            
                mapping = false;
            }
        }   

        if(field.code === 'flag-supero-tempo'){
            
            if(field.data == '1'){
                set(field, 'showHelp',false);
                set(field, 'helpDescription',null);                  
            }
            mapping = false;
                       
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
        return Constants.FASE_SOSPENSIONE_TABELLATI;
    }


    protected loadDettaglio = (): Observable<FaseSospensioneEntry> => {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: FaseSospensioneEntry) => {
                    this.fase = result;
                    return result;
                })
            );
    }

    // #endregion

    // #region Private

    private dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<FaseSospensioneEntry> {
        return () => {
            return this.faseRipresaPrestazioneService.getFase(codGara, codLotto, progressivo);
        }
    }

    // #endregion
}
