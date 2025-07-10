import { Injectable } from '@angular/core';
import { IDictionary } from '@maggioli/sdk-commons';
import { SdkFormBuilderConfiguration } from '@maggioli/sdk-controls';
import { cloneDeep, isEqual, toString } from 'lodash-es';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';

import { ProgrammaInsertForm, ProgrammaUpdateForm } from '../../models/programmi/programmi.model';
import { AbstractProgrammaProvider } from './abstract-programma.provider';



@Injectable({ providedIn: 'root' })
export class ModificaProgrammaProvider extends AbstractProgrammaProvider {


    protected handleButtons(data: IDictionary<any>): Observable<IDictionary<any>> {
        if (data.code === 'back-button') {
            let setUpdateState: Function = data.setUpdateState;
            setUpdateState(false);
            let params: IDictionary<any> = {
                idProgramma: data.idProgramma,
                tipologia: data.tipologia
            }
            this.routerService.navigateToPage('dett-prog-dati-generali-page', params);
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
            // controllo che il modello sia cambiato dal default
            if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {

                // controllo la validita' del modello
                let valid: boolean = this.nuovoProgrammaValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
                if (valid === true) {
                    let request: ProgrammaUpdateForm = this.populateRequest(formBuilderConfig, stazioneAppaltante, syscon, tipologia);
                    request.id = data.idProgramma;
                    let modificaProgrammaFactory = this.modificaProgrammaFactory(request);
                    return this.requestHelper.begin(modificaProgrammaFactory, messagesPanel).pipe(map((result: any): IDictionary<any> => {
                        let idProgramma: number = request.id;
                        this.dettaglioProgrammaStore.clearIdProgramma();
                        this.dettaglioProgrammaStore.idProgramma = toString(idProgramma);
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


    private modificaProgrammaFactory(form: ProgrammaUpdateForm) {
        return () => {
            return this.programmiService.modificaProgramma(form)
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