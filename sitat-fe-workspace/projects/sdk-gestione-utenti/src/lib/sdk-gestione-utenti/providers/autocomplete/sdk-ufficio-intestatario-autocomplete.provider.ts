import { Injectable, Injector } from '@angular/core';
import { SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem } from '@maggioli/sdk-controls';
import { each } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { UfficioIntestatarioDTO } from '../../model/gestione-utenti.model';
import { ResponseDTO } from '../../model/lib.model';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';

@Injectable()
export class SdkUfficioIntestatarioAutocompleteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Function {
        return (data?: string): Observable<Array<SdkAutocompleteItem>> => {
            return this.gestioneUtentiService.listaOpzioniUfficiIntestatari(data)
                .pipe(
                    map((result: ResponseDTO<Array<UfficioIntestatarioDTO>>): Array<SdkAutocompleteItem> => {
                        let arr: Array<SdkAutocompleteItem> = [];
                        if (result.response != null) {
                            each(result.response, (tipo: UfficioIntestatarioDTO) => {
                                let item: SdkAutocompleteItem = {
                                    ...tipo,
                                    text: `${tipo.denominazione} (${tipo.codice})`,
                                    _key: tipo.codice
                                };
                                arr.push(item);
                            });
                        }
                        return arr;
                    })
                );
        }
    }

    // #region Getters

    private get gestioneUtentiService(): GestioneUtentiService { return this.injectable(GestioneUtentiService) }

    // #endregion

}