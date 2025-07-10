import { ChangeDetectionStrategy, Component, OnInit, Type } from '@angular/core';
import { IDictionary, SdkReducer, SdkStateMap, SdkStoreAction, SdkStoreService } from '@maggioli/sdk-commons';
import { Observable } from 'rxjs';

import { DeleteReducer } from './reducers/delete-reducer';
import { SetReducer } from './reducers/set-reducer';
import { UpdateReducer } from './reducers/update-reducer';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class AppComponent implements OnInit {
  public title: string = 'dev-commons';

  public state$: Observable<any>;
  public spanish$: Observable<any>;
  public all$: Observable<any>;

  constructor(private store: SdkStoreService) { }

  public ngOnInit(): void {

    this.store.init(new SdkStateMap(undefined), this.map);

    this.state$ = this.store.state();
    this.spanish$ = this.store.select('spanish.hola');
    this.all$ = this.store.select('');
  }

  public set(): void {
    this.store.dispatch(new SdkStoreAction('SET', { hello: 'world' }));
  }

  public update(): void {
    this.store.dispatch(new SdkStoreAction('UPDATE', { spanish: { hola: 'mundo' } }));
  }

  public delete(): void {
    this.store.dispatch(new SdkStoreAction('DELETE', 'spanish'));
  }

  private get map(): IDictionary<Type<SdkReducer>> {
    return {
      SET: SetReducer,
      UPDATE: UpdateReducer,
      DELETE: DeleteReducer,
    };
  }

}



