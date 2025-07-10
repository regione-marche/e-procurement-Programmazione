import { ChangeDetectionStrategy, Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { SdkBasicButtonInput, SdkBasicButtonOutput } from '@maggioli/sdk-controls';
import { Observable, of, Subscription } from 'rxjs';
import { isObject } from 'lodash-es';

@Component({
  selector: 'basic-button',
  templateUrl: './basic-button.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BasicButtonComponent implements OnInit, OnDestroy {

  /**
     * Observable con la configurazione
     */
  @Input('config') public config$: Observable<SdkBasicButtonInput>;

  /**
   * EventEmitter con l'output
   */
  @Output('output') public output$ = new EventEmitter<SdkBasicButtonOutput>();

  private subscription: Subscription;

  public input: Observable<SdkBasicButtonInput> = of({
    code: 'btn',
    icon: 'mgg-icons-file-folder',
    label: 'Click',
    look: 'outline'
  } as SdkBasicButtonInput);

  constructor() { }

  public ngOnInit(): void {
    if (isObject(this.config$)) {
      this.subscription = this.config$.subscribe((data: SdkBasicButtonInput) => {
        this.input = of(data);
      });
    }
  }

  public ngOnDestroy(): void {
    if (isObject(this.subscription)) {
      this.subscription.unsubscribe();
    }
  }

  public onClick(output: SdkBasicButtonOutput): void {
    console.log(output);
    this.output$.emit(output);
  }

}
