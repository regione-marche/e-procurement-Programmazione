import { Injectable, Injector } from '@angular/core';
import { HttpRequestHelper, StazioneAppaltanteInfo } from '@maggioli/app-commons';
import { SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem } from '@maggioli/sdk-controls';
import { each, get } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { CuiSearchForm } from '../models/interventi/interventi.model';
import { ProgrammiService } from '../services/programmi/programmi.service';

@Injectable({ providedIn: 'root' })
export class CuiInterventoProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Function {
        let stazioneAppaltante: StazioneAppaltanteInfo = get(args, 'stazioneAppaltante');
        return (data?: string): Observable<Array<SdkAutocompleteItem>> => {
            let func = this.getListaOpzioniCuiFactory(stazioneAppaltante, data);
            return this.requestHelper.begin(func, undefined, false);
        }
    }

    private getListaOpzioniCuiFactory(stazioneAppaltante: StazioneAppaltanteInfo, data?: string): () => Observable<any> {
        return () => {
            const form: CuiSearchForm = {
                stazioneAppaltante: stazioneAppaltante.codice,
                desc: data
            };
            return this.programmiService.listaOpzioniCui(form)
                .pipe(
                    map((result: Array<string>): Array<SdkAutocompleteItem> => {
                        let arr: Array<SdkAutocompleteItem> = [];
                        each(result, (cui: string) => {
                            let item: SdkAutocompleteItem = {
                                cui,
                                text: cui,
                                _key: cui
                            };
                            arr.push(item);
                        });
                        return arr;
                    })
                );
        }
    }

    // #region Getters

    private get programmiService(): ProgrammiService { return this.injectable(ProgrammiService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion

}