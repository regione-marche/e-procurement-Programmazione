import { Injectable, Injector } from '@angular/core';
import { ArchivioImpreseService, HttpRequestHelper, ImpresaEntry, StazioneAppaltanteInfo } from '@maggioli/app-commons';
import { SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { SdkAutocompleteItem } from '@maggioli/sdk-controls';
import { each, get } from 'lodash-es';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { environment } from '../../../../environments/environment';

@Injectable({ providedIn: 'root' })
export class ImpreseVaraggiAutocompleteProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: any): Function {
        const stazioneAppaltante: StazioneAppaltanteInfo = get(args, 'stazioneAppaltante');
        const codGara: string = get(args, 'codGara');
        const codLotto: string = get(args, 'codLotto');
        return (data?: string): Observable<Array<SdkAutocompleteItem>> => {
            let func = this.getListaOpzioniImpreseAggiudicatarieFactory(stazioneAppaltante, codGara, codLotto, data);
            return this.requestHelper.begin(func, undefined, false);
        }
    }

    private getListaOpzioniImpreseAggiudicatarieFactory(stazioneAppaltante: StazioneAppaltanteInfo, codGara: string, codLotto: string, data?: string): () => Observable<any> {
        return () => {
            return this.archivioImpreseService.getImpreseOptionsVaraggi(environment.GESTIONE_CONTRATTI_BASE_URL, stazioneAppaltante, codGara, codLotto, data)
                .pipe(
                    map((result: Array<ImpresaEntry>): Array<SdkAutocompleteItem> => {
                        let arr: Array<SdkAutocompleteItem> = [];
                        each(result, (tipo: ImpresaEntry) => {
                            let item: SdkAutocompleteItem = {
                                ...tipo,
                                text: `${tipo.codiceImpresa} ${tipo.ragioneSociale}`,
                                _key: tipo.codiceImpresa
                            };
                            arr.push(item);
                        });
                        return arr;
                    })
                );
        }
    }

    // #region Getters

    private get archivioImpreseService(): ArchivioImpreseService { return this.injectable(ArchivioImpreseService) }

    private get requestHelper(): HttpRequestHelper { return this.injectable(HttpRequestHelper) }

    // #endregion

}