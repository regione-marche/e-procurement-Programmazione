import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkBasicButtonOutput, SdkButtonGroupInput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'button-group',
  templateUrl: './button-group.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ButtonGroupComponent implements OnInit {

  public inputGroup: Observable<SdkButtonGroupInput> = of({
    buttons: [
      { code: 'btn', icon: 'mgg-icons-file-folder', label: 'Click' },
      { code: 'btn2', icon: 'mgg-icons-file-folder', label: 'Click 2' },
      { code: 'btn3', icon: 'mgg-icons-file-folder', label: 'Click 3' },
      {
        code: 'btn',
        label: 'Dropdown',
        dropdown: true,
        dropdownData: [
          { code: 'btnInner', icon: 'mgg-icons-file-folder', label: 'Click' },
          { code: 'btnInner2', icon: 'mgg-icons-file-folder', label: 'Click 2' },
          { code: 'btnInner3', icon: 'mgg-icons-file-folder', label: 'Click 3' }
        ]
      }
    ]
  } as SdkButtonGroupInput);

  constructor() { }

  ngOnInit() {
  }

  public onClick(output: SdkBasicButtonOutput): void {
    console.log(output);
  }
}
