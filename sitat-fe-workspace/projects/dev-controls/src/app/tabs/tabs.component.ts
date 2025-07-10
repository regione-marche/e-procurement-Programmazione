import { AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { SdkTabsConfig, SdkTabsOutput } from '@maggioli/sdk-controls';
import { Subject } from 'rxjs';

@Component({
  selector: 'tabs',
  templateUrl: './tabs.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class TabsComponent implements OnInit, AfterViewInit {

  public tabsConfig: Subject<SdkTabsConfig> = new Subject();

  constructor(private cdr: ChangeDetectorRef) { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      let config = {
        tabs: [
          {
            code: 'tab1',
            title: 'Tab 1',
            selected: true
          },
          {
            code: 'tab2',
            title: 'Tab 2',
            content: 'basic-button-element',
            contentConfig: {
              code: 'btn',
              icon: 'far fa-check-outline',
              label: 'Click',
              look: 'flat'
            }
          },
          {
            code: 'tab3',
            title: 'Tab 3'
          },
          {
            code: 'tab4',
            title: 'Tab 4'
          }
        ]
      };
      this.tabsConfig.next(config);
    });
  }

  public onOutput(item: SdkTabsOutput): void {
    console.log(item);
  }

}
