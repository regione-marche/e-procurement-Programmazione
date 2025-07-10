import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkBasicButtonOutput, SdkButtonGroupInput, SdkDialogConfig } from '@maggioli/sdk-controls';
import { Observable, of, Subject } from 'rxjs';

@Component({
  selector: 'dialog-demo',
  templateUrl: './dialog.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DialogComponent implements OnInit {

  private dialogConfig: SdkDialogConfig;
  public dialogConfigObs: Observable<SdkDialogConfig>;

  public inputGroup: Observable<SdkButtonGroupInput> = of({
    buttons: [
      { code: 'btn', label: 'Testo semplice' }
    ]
  } as SdkButtonGroupInput);

  constructor() { }

  ngOnInit() {
    this.initDialog();
  }

  private initDialog(): void {
    this.dialogConfig = {
      header: 'Titolo',
      message: 'Messaggio',
      acceptLabel: 'Si',
      rejectLabel: 'No',
      open: new Subject()
    };
    this.dialogConfigObs = of(this.dialogConfig);
  }

  public onClick(output: SdkBasicButtonOutput): void {
    console.log(output);
    if (output.code === 'btn') {
      let func = this.confirm('prova');
      this.dialogConfig.open.next(func);
    }
  }

  private confirm(result: string): any {
    return () => {
      console.log('result >>>', result);
    }
  }
}
