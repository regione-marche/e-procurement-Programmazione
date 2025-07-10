import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Injector,
    ViewChild,
    ViewEncapsulation
} from '@angular/core';
import {
    RupEntry
} from '@maggioli/app-commons';
import {
    SdkAutocompleteItem,
    SdkFormBuilderField,
    SdkModalOutput,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { cloneDeep, get, isEmpty, isObject, isString, set, toString } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { FaseIncarichiProfessionaliService } from 'projects/app-contratti/src/app/modules/services/fasi/fase-incarichi-professionali.service';
import { Constants } from '../../../../../../../app.constants';
import { IncarichiProfEntry, ResponseIncarichiProfListEntry } from '../../../../../../models/incarichi-professionali/incarichi-professionali.model';
import { FaseAbstractSectionWidget } from '../../abstract/fase-abstract-section.widget';


@Component({
    templateUrl: `modifica-fase-inc-prof-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModificaFaseIncarichiProfessionaliSectionWidget extends FaseAbstractSectionWidget {

    // #region Variables

    @HostBinding('class') classNames = `modifica-fase-incarichi-professionali-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;


    protected fase: IncarichiProfEntry;

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



    protected get faseIncarichiProfessionaliService(): FaseIncarichiProfessionaliService { return this.injectable(FaseIncarichiProfessionaliService) }

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

            if (config.code === 'nomtec') {
                this.modalConfig.componentConfig = {
                    gara: this.gara,
                    ...this.modalConfig.componentConfig,
                    selectedItem: config.textModalContent,
                    readOnly: true
                }
            }

            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }

    public manageModalOutput(item: SdkModalOutput<any>): void {
        
    }


    public manageDeletePanel(field: SdkFormBuilderField): void { }

    // #endregion

    // #region Protected

    protected customPopulateFunction = (field: SdkFormBuilderField, restObject?: IncarichiProfEntry, dynamicField?: boolean) => {

        let mapping: boolean = true;

        let keyAny: any = get(restObject, field.mappingInput);
        let key: string = dynamicField === true ? field.data : toString(keyAny);

        let idRuolo: any = get(restObject, 'idRuolo');

        if (field.code === 'nomtec') {
           
            if (isObject(get(restObject, field.mappingInput))) {
                let data: RupEntry = get(restObject, field.mappingInput);
                let item: SdkAutocompleteItem = {
                    ...data,
                    text: `${data.nominativo} (${data.cf})`,
                    _key: data.codice
                };
                set(field, 'data', item);
                mapping = false;
            }
        }

        if(Object.keys(restObject).length > 0 && restObject?.pcp === true && field.code === 'id-ruolo') {
            field.visible = false;
            mapping = false;
        }

        if(Object.keys(restObject).length > 0 && restObject?.pcp !== true && field.code === 'id-ruolo-pcp') {
            field.visible = false;
            mapping = false;
        }

        if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
            [field, mapping] = this.formBuilderUtilsService.populateListCode(this.valoriTabellati, field, key, mapping);
        }

        if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
            field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
        }

        return {
            mapping,
            field
        };
    }

    protected tabellati(): Array<string> {
        return Constants.FASE_INCARICHI_PROFESSIONALI;
    }

    protected loadDettaglio = (): Observable<IncarichiProfEntry> => {
        return this.getDettaglioObservable();
    }

    // #endregion

    // #region Private

    protected dettaglioFaseFactory(codGara: string, codLotto: string, progressivo: string): () => Observable<IncarichiProfEntry> {
        return () => {
            return this.faseIncarichiProfessionaliService.getFase(codGara, codLotto, progressivo);
        }
    }

    protected getDettaglioObservable(): Observable<any> {
        const factory = this.dettaglioFaseFactory(this.codGara, this.codLotto, this.numeroProgressivo);
        return this.requestHelper.begin(factory, this.messagesPanel)
            .pipe(
                map((result: ResponseIncarichiProfListEntry) => {                   
                    return {incarichi : result.incarichi };
                })
            );
    }

    protected manageExecutionProvider = (obj: any) => {

        if (isObject(obj)) {
            if (get(obj, 'action') === 'open-sidebar' && isObject(get(this.config.body, 'sidebar'))) {
                this.sidebarConfig.componentConfig = obj;
                this.sidebarConfigObs.next(this.sidebarConfig);
                this.sidebarConfig.openSubject.next(true);
            }
        }
    }

    protected scrollToMessagePanel(messagesPanel: HTMLElement): void {
        messagesPanel.scrollIntoView();
    }

    // #endregion
}
