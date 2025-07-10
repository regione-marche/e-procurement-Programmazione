import { Component, OnInit } from '@angular/core';
import { SdkToolbarInput, SdkToolbarOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'toolbar',
  templateUrl: './toolbar.component.html'
})
export class ToolbarComponent implements OnInit {

  public inputToolbar: Observable<SdkToolbarInput> = of({
    buttons: [
      { code: 'btn', icon: 'mgg-icons-file-folder', label: 'Click', widgetCode: 'BUTTON' },
      { code: 'btn2', icon: 'mgg-icons-file-folder', label: 'Click 2', widgetCode: 'BUTTON' },
      { code: 'btn3', icon: 'mgg-icons-file-folder', label: 'Click 3', widgetCode: 'BUTTON' },
      {
        code: 'btn',
        widgetCode: 'DROPDOWN',
        label: 'Dropdown',
        dropdownData: [
          { code: 'btnInner', icon: 'mgg-icons-file-folder', label: 'Click' },
          { code: 'btnInner2', icon: 'mgg-icons-file-folder', label: 'Click 2' },
          { code: 'btnInner3', icon: 'mgg-icons-file-folder', label: 'Click 3' }
        ]
      }
    ]
  } as SdkToolbarInput);

  constructor() { }

  ngOnInit() {
  }

  public onClickToolbar(output: SdkToolbarOutput): void {
    console.log(output);
  }

}
