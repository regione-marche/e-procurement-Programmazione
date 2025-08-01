import { ChangeDetectorRef, Component, ElementRef, Injector, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { SdkAbstractComponent } from '@maggioli/sdk-commons';
import { SdkDialogConfig } from '@maggioli/sdk-controls';
import { isObject, isFunction } from 'lodash-es';
import { Confirmation, ConfirmationService } from 'primeng/api';
import { Subscription } from 'rxjs';

/**
 * Componente per renderizzare un dialog
 */
@Component({
  selector: 'sdk-dialog',
  templateUrl: './sdk-dialog.component.html'
})
export class SdkDialogComponent extends SdkAbstractComponent<SdkDialogConfig, void, void> implements OnInit, OnDestroy {

  // #region Variables

  /**
   * @ignore
   */
  public config: SdkDialogConfig;

  /**
   * @ignore
   */
  private openSubjectSubscription: Subscription;

  /**
   * @ignore
   */
  @ViewChild('dialog') public _dialog: ElementRef;

  // #endregion

  /**
   * @ignore
   */
  constructor(inj: Injector, cdr: ChangeDetectorRef, private confirmationService: ConfirmationService) { super(inj, cdr) }

  // #region Hooks

  /**
   * @ignore
   */
  protected onInit(): void { }

  /**
   * @ignore
   */
  protected onAfterViewInit(): void {
    this.dialog.focus();
  }

  /**
   * @ignore
   */
  protected onDestroy(): void {
    this.unsubscribeOpenSubject();
  }

  /**
   * @ignore
   */
  protected onOutput(_data: void): void { }

  /**
   * @ignore
   */
  protected onConfig(config: SdkDialogConfig): void {
    if (config != null) {
      this.markForCheck(() => {
        this.config = {
          ...config,
          dialogId: config.dialogId || 'dialogKey'
        };
        this.unsubscribeOpenSubject();
        this.openSubjectSubscription = this.config.open.subscribe((callback: Function) => {
          if (isFunction(callback)) {
            this.markForCheck(() => this.open(callback));
          }
        });
        this.isReady = true;
      });
    }
  }

  /**
   * @ignore
   */
  protected onData(_data: void): void { }

  /**
   * @ignore
   */
  protected onUpdateState(state: boolean): void { }

  // #endregion

  // #region Private

  /**
   * @ignore
   */
  private unsubscribeOpenSubject(): void {
    if (isObject(this.openSubjectSubscription)) {
      this.openSubjectSubscription.unsubscribe();
    }
  }

  /**
   * @ignore
   */
  private open = (callback: Function): void => {
    this.confirmationService.confirm({
      message: this.config.message,
      header: this.config.header,
      accept: callback,
      acceptLabel: this.config.acceptLabel,
      rejectLabel: this.config.rejectLabel,
      key: this.config.dialogId || 'dialogKey'
    } as Confirmation);
  }

  /**
   * @ignore
   */
  private get dialog(): HTMLElement {
    return this._dialog ? this._dialog.nativeElement : undefined;
  }

  // #endregion
}
