import { Injectable } from '@angular/core';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkFormBuilderConfiguration } from '@maggioli/sdk-controls';
import { cloneDeep, isEqual, toString } from 'lodash-es';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { InizNuovoProgramma, ProgrammaInsertForm } from '../../models/programmi/programmi.model';
import { AbstractProgrammaProvider } from './abstract-programma.provider';



@Injectable({ providedIn: 'root' })
export class NuovoProgrammaProvider extends AbstractProgrammaProvider {


    protected handleButtons(data: IDictionary<any>): Observable<IDictionary<any>> {
        if (data.code === 'back-button') {
            let setUpdateState: Function = data.setUpdateState;
            setUpdateState(false);
            this.routerService.navigateToPage('home-page');
        } else if (data.code === 'clean-button') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = data.defaultFormBuilderConfig;
            let messagesPanel: HTMLElement = data.messagesPanel;
            this.sdkMessagePanelService.clear(messagesPanel);
            return of({
                formBuilderConfig: cloneDeep(defaultFormBuilderConfig)
            });
        } else if (data.code === 'save-button') {
            let defaultFormBuilderConfig: SdkFormBuilderConfiguration = data.defaultFormBuilderConfig;
            let formBuilderConfig: SdkFormBuilderConfiguration = data.formBuilderConfig;
            let stazioneAppaltante: string = data.stazioneAppaltante;
            let syscon: number = data.syscon;            
            let tipologia: number = data.tipologia;
            let messagesPanel: HTMLElement = data.messagesPanel;
            let setUpdateState: Function = data.setUpdateState;
            let iniz: InizNuovoProgramma = data.iniz;
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {
                // controllo la validita' del modello
                let valid: boolean = this.nuovoProgrammaValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    let request: ProgrammaInsertForm = this.populateRequest(formBuilderConfig, stazioneAppaltante, syscon, tipologia);
                    if(iniz != null && iniz.idUfficio != null){
                        request.idUfficio = Number(iniz.idUfficio);
                    }                    
                    let inserisciProgrammaFactory = this.inserisciProgrammaFactory(request);
                    return this.requestHelper.begin(inserisciProgrammaFactory, messagesPanel).pipe(map((result: any): IDictionary<any> => {
                        let idProgramma: number = result.data;
                        this.dettaglioProgrammaStore.clearIdProgramma();
                        this.dettaglioProgrammaStore.idProgramma = toString(idProgramma);
                        this.dettaglioProgrammaStore.clearTipologia();
                        this.dettaglioProgrammaStore.tipologia = toString(tipologia);
                        let params: IDictionary<any> = {
                            idProgramma,
                            tipologia
                        }
                        setUpdateState(false);
                        this.routerService.navigateToPage('dett-prog-dati-generali-page', params);
                        return {
                            ...result,
                            defaultFormBuilderConfig,
                            formBuilderConfig
                        }
                    }));
                } else {
                    this.scrollToMessagePanel(messagesPanel);
                }
            } else {
                this.sdkMessagePanelService.showError(messagesPanel, [
                    {
                        message: 'VALIDATORS.FORM-NON-COMPILATA'
                    }
                ]);
                this.scrollToMessagePanel(messagesPanel);
            }
        }
        return of(undefined);
    }


    private inserisciProgrammaFactory(insertForm: ProgrammaInsertForm) {
        return () => {
            return this.programmiService.createProgramma(insertForm)
        }
    }

    protected getDefaultForm(stazioneappaltante: string, syscon: number, tipologia: number): ProgrammaInsertForm {
        return {
            tipoProg: tipologia,
            stazioneappaltante,
            syscon
        };
    }



}
