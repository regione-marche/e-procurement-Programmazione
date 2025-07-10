import { ChangeDetectorRef, Component, Injector, OnDestroy, OnInit } from '@angular/core';
import { SdkAbstractComponent } from '@maggioli/sdk-commons';
import { SdkComboBoxItem, SdkDialogConfig } from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { isFunction, isObject } from 'lodash-es';
import { Subscription } from 'rxjs';

/**
 * Componente per renderizzare un motivazione dialog
 */
@Component({
  selector: 'sdk-double-choice-dialog',
  templateUrl: './sdk-double-choice-dialog.component.html',
  styleUrls: ['./sdk-double-choice-dialog.component.scss']
})
export class SdkDoubleChoiceDialogComponent extends SdkAbstractComponent<SdkDialogConfig, void, void> implements OnInit, OnDestroy {

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
  public isOpen: boolean = false;

  /**
   * @ignore
   */
  public firstChoice: SdkComboBoxItem;

  /**
   * @ignore
   */
  public secondChoice: SdkComboBoxItem;

  /**
   * @ignore
   */
  private currentCallback: Function;

  /**
   * @ignore
   */
  private choices: Array<SdkComboBoxItem> = [];

  // #endregion

  /**
   * @ignore
   */
  constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

  // #region Hooks

  /**
   * @ignore
   */
  protected onInit(): void { }

  /**
   * @ignore
   */
  protected onAfterViewInit(): void { }

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
        this.populateChoices();
        this.config = {
          ...config
        };
        delete this.firstChoice;
        delete this.secondChoice;
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
    this.currentCallback = callback;
    this.isOpen = true;
  }

  /**
   * @ignore
   */
  private populateChoices(): void {
    this.choices = [
        {
            key: '1',
            value: this.translateService.instant('COMBOBOX.SI')
        },
        {
            key: '2',
            value: this.translateService.instant('COMBOBOX.NO')
        }
    ]
  }

  // #endregion

  //#region Public

  /**
   * @ignore
   */
  public accept = (): void => {
    this.markForCheck(() => {
      this.isOpen = false;
    });
    this.firstChoice = {
        ...this.firstChoice,
        desc: "firstChoice"
    }
    this.secondChoice = {
        ...this.secondChoice,
        desc: "secondChoice"
    }
    this.currentCallback([this.firstChoice,this.secondChoice]);
  }

  /**
   * @ignore
   */
  public reject(): void {
    this.markForCheck(() => this.isOpen = false);
  }

  //#endregion

  //#region Getters

  private get translateService(): TranslateService { return this.injectable(TranslateService) }

  //#endregion
}
