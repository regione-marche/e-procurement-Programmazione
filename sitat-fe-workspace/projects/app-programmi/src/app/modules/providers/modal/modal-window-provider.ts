import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkProvider } from '@maggioli/sdk-commons';
import { Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class ModalWindowProvider extends SdkBaseService implements SdkProvider {

    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: IDictionary<any>): Observable<IDictionary<any>> {
        this.logger.debug('ModalWindowProvider', args);

        if (args.type === 'BUTTON') {
            return this.handleButtons(args.data);
        }
        return of(args);
    }

    private handleButtons(data: IDictionary<any>): Observable<IDictionary<any>> {
        if (data.code === 'modal-close-button') {
            return of({ close: true });
        }
        return of(undefined);
    }
}
