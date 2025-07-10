import { ChangeDetectionStrategy, Component, OnInit } from '@angular/core';
import {
  SdkBasicButtonInput,
  SdkBasicButtonOutput,
  SdkMenuConfig,
  SdkMenuOutput,
  SdkModalConfig,
  SdkModalOutput,
} from '@maggioli/sdk-controls';
import { Observable, of, Subject } from 'rxjs';

@Component({
  selector: 'modal',
  templateUrl: './modal.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class ModalComponent implements OnInit {

  private modalOpenbutton: SdkBasicButtonInput;
  public modalOpenbuttonObs: Observable<SdkBasicButtonInput>;

  private modalConfig: SdkModalConfig<SdkMenuConfig, void, SdkMenuOutput>;
  public modalConfigObs: Observable<SdkModalConfig<SdkMenuConfig, void, SdkMenuOutput>>;

  constructor() { }

  public ngOnInit(): void {
    this.modalOpenbutton = {
      code: 'modal-open-button',
      label: '',
      icon: 'mgg-icons-menu-main',
      look: 'flat'
    };

    this.modalOpenbuttonObs = of(this.modalOpenbutton);

    this.modalConfig = {
      code: 'modal',
      title: 'titolo modale',
      openSubject: new Subject(),
      component: 'sdk-menu-component',
      componentConfig: {
        vertical: false,
        menuTitle: '',
        items: [
          {
            code: 'home',
            label: 'MENU.HOME',
            cssClass: 'header-menu-item',
            slug: 'home-page'
          },
          {
            code: 'nuovo-avviso',
            label: 'MENU.NUOVO-AVVISO',
            cssClass: 'header-menu-item',
            slug: 'nuovo-avviso-page'
          },
          {
            code: 'lista-avvisi',
            label: 'MENU.LISTA-AVVISI',
            cssClass: 'header-menu-item',
            slug: 'lista-avvisi-page'
          },
          {
            code: 'ricerca-avanzata',
            label: 'MENU.RICERCA-AVANZATA',
            cssClass: 'header-menu-item',
            slug: 'ricerca-avanzata-page'
          }
        ]
      }
    };
    this.modalConfigObs = of(this.modalConfig);
  }

  public openModal(_button: SdkBasicButtonOutput): void {
    this.modalConfig.openSubject.next(true);
  }

  public manageModalOutput(item: SdkModalOutput<SdkMenuOutput>): void {
    console.log(item);
  }

}
