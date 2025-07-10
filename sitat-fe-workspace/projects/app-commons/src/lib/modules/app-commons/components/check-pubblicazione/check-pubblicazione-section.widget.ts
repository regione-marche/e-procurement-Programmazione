import {
    ChangeDetectionStrategy,
    ChangeDetectorRef,
    Component,
    HostBinding,
    Input,
    OnDestroy,
    OnInit,
    ViewEncapsulation,
} from '@angular/core';
import { SdkBasicButtonInput, SdkButtonGroupInput } from '@maggioli/sdk-controls';
import { each, isObject } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subscription } from 'rxjs';
import { CheckBlockingErrors, CheckData } from '../../models/pubblicazione/pubblicazione.model';

@Component({
    templateUrl: `check-pubblicazione-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class CheckPubblicazioneSectionWidget implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `check-pubblicazione-section`;

    private subscription: Subscription;

    public checkSuccessActionDescr: string = '';

    public config: any;

   public buttonPopupConfig: Observable<SdkBasicButtonInput>;

    public checkData: CheckData;

    public checkPubbErrors: boolean;
    /**
     * Observable con la configurazione
     */
    @Input('config') public config$: Observable<any>;

    constructor(private cdr: ChangeDetectorRef) { }

    public ngOnInit(): void {

        this.subscription = this.config$.subscribe((config: any) => {
            this.config = config;
            this.checkSuccessActionDescr = this.config.checkSuccessActionDescr || '';

            const errors: Array<CheckBlockingErrors> = new Array();
            const warnings: Array<CheckBlockingErrors> = new Array();
            if (isObject(this.config.validate)) {
                each(this.config.validate, (element: any) => {
                    if (element.tipo == 'E') {
                        errors.push({
                            title: element.nome,
                            desc: element.messaggio
                        });
                    } else if (element.tipo == 'W') {
                        warnings.push({
                            title: element.nome,
                            desc: element.messaggio
                        });
                    }
                });
            }
            this.checkData = {
                blockingErrorNum: errors.length,
                blockingErrors: errors,
                warningErrorNum: warnings.length,
                warningErrors: warnings
            }
            this.checkPubbErrors = this.config.validate ? this.config.validate.length > 0 : false;
            this.buttonPopupConfig = of(
                {
                    code: 'popup',
                    label: 'BUTTONS.POPUP',
                    disabled: this.checkPubbErrors == false,
                    title: 'BUTTONS.POPUP'
                } as SdkBasicButtonInput
            );


            this.cdr.markForCheck();
        });
    }

    public ngOnDestroy(): void {
        if (isObject(this.subscription)) {
            this.subscription.unsubscribe();
        }
    }

    public onButtonClick($event){
        var win = window.open("", "Validazione dati", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,width=780,height=500,top="+(screen.height-200)+",left="+(screen.width-440));
        win.document.body.innerHTML = this.getHtml();
    }

    private getHtml() : string{
        let html : string = '<table  style="border: 1px solid black;background: #06c;font-family:Titillium Web,sans-serif;border-radius:5px;"><thead><th style="color:white;padding:1em;">Campo</th></th><th style="color:white;padding:1em;">Descrizione</th><th style="color:white;padding:1em;">Severità</th></thead>';
        each(this.config.validate, (element: any) => {
            console.log(element.tipo);
            if(element.tipo === 'E'){
                element.tipo = 'Errore';
            }else {
                element.tipo = 'Avviso';
            };

            html += '<tr style="border: 1px solid black;background: #fff;"><td style="border: 1px solid black;padding:1em;">' + element.nome + '</td><td style="border: 1px solid black;padding:1em;">'+element.messaggio+'</td><td style="border: 1px solid black;padding:1em;">' + element.tipo + '</td></tr>';          
        }); 
        html += '</table>';
        return html;
    }


}
