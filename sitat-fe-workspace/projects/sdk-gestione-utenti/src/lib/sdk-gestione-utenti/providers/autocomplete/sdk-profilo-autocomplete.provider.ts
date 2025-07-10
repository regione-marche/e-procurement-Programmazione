import { Injectable, Injector } from '@angular/core';
import { SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem } from '@maggioli/sdk-controls';
import { each } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ProfiloDTO } from '../../model/gestione-utenti.model';
import { ResponseDTO } from '../../model/lib.model';
import { GestioneUtentiService } from '../../services/gestione-utenti.service';

@Injectable()
export class SdkProfiloAutocompleteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Function {
        return (data?: string): Observable<Array<SdkAutocompleteItem>> => {
            return this.gestioneUtentiService.listaOpzioniProfili(data)
                .pipe(
                    map((result: ResponseDTO<Array<ProfiloDTO>>): Array<SdkAutocompleteItem> => {
                        let arr: Array<SdkAutocompleteItem> = [];
                        if (result.response != null) {
                            each(result.response, (tipo: ProfiloDTO) => {
                                let item: SdkAutocompleteItem = {
                                    ...tipo,
                                    text: `${tipo.nome} (${tipo.codice})`,
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