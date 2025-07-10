import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkDropdownButtonInput, SdkDropdownButtonOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'button-dropdown',
  templateUrl: './button-dropdown.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ButtonDropdownComponent implements OnInit {

  public inputDropdown: Observable<SdkDropdownButtonInput> = of({
    code: 'btn',
    label: 'Dropdown',
    dropdownData: [
      { code: 'btnInner', icon: 'mgg-icons-file-folder', label: 'Click' },
      { code: 'btnInner2', icon: 'mgg-icons-file-folder', label: 'Click 2' },
      { code: 'btnInner3', icon: 'mgg-icons-file-folder', label: 'Click 3' }
    ]
  } as SdkDropdownButtonInput);

  constructor() { }

  public ngOnInit(): void { }

  public onClickDropdown(output: SdkDropdownButtonOutput): void {
    console.log(output);
  }

}
