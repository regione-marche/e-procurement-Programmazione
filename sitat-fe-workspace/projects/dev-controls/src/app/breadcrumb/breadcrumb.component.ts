import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkBreadcrumbConfig, SdkBreadcrumbOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'breadcrumb',
  templateUrl: './breadcrumb.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class BreadcrumbComponent implements OnInit {

  public breadcrumbConfig: Observable<SdkBreadcrumbConfig> = of({
    items: [
      {
        code: 'item1',
        label: 'Item 1'
      },
      {
        code: 'item2',
        label: 'Item 2'
      }, {
        code: 'item3',
        label: 'Item3'
      }
    ]
  } as SdkBreadcrumbConfig);

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkBreadcrumbOutput): void {
    console.log(item);
  }

}
