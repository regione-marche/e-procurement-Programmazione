import { ChangeDetectionStrategy, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { SdkPanelbarConfig, SdkPanelbarItem, SdkPanelbarOutput } from '@maggioli/sdk-controls';
import { each, isEmpty, isObject, join, toString } from 'lodash-es';
import { BehaviorSubject } from 'rxjs';

import { data } from './data';

@Component({
  selector: 'panelbar',
  templateUrl: './panelbar.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class PanelbarComponent implements OnInit {

  private listaAtti: Array<any> = data;
  public listaAttiSubject: BehaviorSubject<SdkPanelbarConfig> = new BehaviorSubject(null);

  constructor(private cdr: ChangeDetectorRef) { }

  ngOnInit() {
    let panelbarConfig: SdkPanelbarConfig = this.buildAccordion(this.listaAtti);
    this.listaAttiSubject.next(panelbarConfig);
  }

  private buildAccordion(lista: Array<any>): SdkPanelbarConfig {
    let panelbarConfig: SdkPanelbarConfig = {
      items: []
    };
    each(lista, (one: any) => {
      let item: SdkPanelbarItem = {
        code: toString(one.id),
        text: one.nome,
        children: []
      };
      if (isObject(one.statiId) && !isEmpty(one.statiId.ids)) {
        item.cssClass = 'bold';
        if (one.statiId.ids.length > 1) {
          each(one.statiId.ids, (id: number) => {
            let child: SdkPanelbarItem = {
              code: join([toString(one.id), toString(id)], '|'),
              label: 'ATTO-FIGLIO-PROGRESSIVO',
              labelParams: {
                value: toString(id)
              }
            };
            item.children.push(child);
          });
        }
      }
      panelbarConfig.items.push(item);
    });
    return panelbarConfig;
  }

  public managePanelbarOutput(obj: SdkPanelbarOutput): void {
    console.log('output >>>', obj);
  }

}
