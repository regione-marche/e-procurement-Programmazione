import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkMenuConfig, SdkMenuOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'menu',
  templateUrl: './menu.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class MenuComponent implements OnInit {

  public menuConfig: Observable<SdkMenuConfig> = of({
    vertical: true,
    items: [
      {
        code: 'item1',
        label: 'Item1',
        items: [
          {
            code: 'item1.1',
            label: 'Item1.1'
          },
          {
            code: 'item1.2',
            label: 'Item1.2',
            items: [
              {
                code: 'item1.2.1',
                label: 'Item1.2.1'
              },
              {
                code: 'item1.2.2',
                label: 'Item1.2.2'
              }
            ]
          }
        ]
      },
      {
        code: 'item2',
        label: 'Item2',
        items: [
          {
            code: 'item2.1',
            label: 'Item2.1'
          },
          {
            code: 'item2.2',
            label: 'Item2.2'
          },
          {
            code: 'item2.3',
            label: 'Item2.3'
          }
        ]
      }, {
        code: 'item3',
        label: 'Item3',
        active: true
      }
    ]
  } as SdkMenuConfig);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkMenuOutput): void {
    console.log(item);
  }

}
