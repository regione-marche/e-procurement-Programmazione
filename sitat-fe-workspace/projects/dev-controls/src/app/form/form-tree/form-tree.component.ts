import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import { SdkTreeConfig, SdkTreeOutput } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'form-tree',
  templateUrl: './form-tree.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class FormTreeComponent implements OnInit {

  public dataconfig: Observable<SdkTreeConfig> = of(
    {
      code: 'tree',
      items: [
        {
          text: 'Furniture',
          items: [
            { text: 'Tables & Chairs' },
            { text: 'Sofas' },
            { text: 'Occasional Furniture' }
          ]
        },
        {
          text: 'Decor',
          items: [
            { text: 'Bed Linen' },
            { text: 'Curtains & Blinds' },
            { text: 'Carpets' }
          ]
        }
      ]
    } as SdkTreeConfig
  );

  constructor() { }

  ngOnInit() {
  }

  public onOutput(item: SdkTreeOutput): void { }

}
