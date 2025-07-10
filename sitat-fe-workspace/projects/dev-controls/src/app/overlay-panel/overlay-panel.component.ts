import { AfterViewInit, ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { SdkOverlayPanelConfig } from '@maggioli/sdk-controls';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'overlay-panel',
  templateUrl: './overlay-panel.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class OverlayPanelComponent implements OnInit, AfterViewInit {

  private opConfig: SdkOverlayPanelConfig = {
    code: 'op',
    openButton: {
      code: 'open',
      label: '',
      title: 'Messaggi in arrivo',
      icon: 'mgg-icons-notifications'
    },
    component: 'grid-component-element'
  };
  public opConfigObs: Observable<SdkOverlayPanelConfig> = of(this.opConfig);

  constructor(private cdr: ChangeDetectorRef) { }

  ngOnInit() {
  }

  ngAfterViewInit(): void {

  }

}
