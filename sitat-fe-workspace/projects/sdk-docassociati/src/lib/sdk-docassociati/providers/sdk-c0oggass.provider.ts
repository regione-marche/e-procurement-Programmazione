import { Injectable, Injector } from "@angular/core";
import { BaseResponse, FileDownloadResponse, SdkBaseProvider, StazioneAppaltanteBaseInfo, WDocdigForm } from "@maggioli/sdk-appaltiecontratti-base";
import { IDictionary, SdkProvider } from "@maggioli/sdk-commons";
import { SdkDialogConfig, SdkFormBuilderConfiguration } from "@maggioli/sdk-controls";
import { isEqual, isFunction } from "lodash-es";
import { BehaviorSubject, Observable, Observer, of, throwError } from "rxjs";
import { catchError, map } from "rxjs/operators";

import { SdkC0oggassDetailsStoreService } from "../components/c0oggass/sdk-c0oggass-details-store.service";
import { SdkC0oggassParamsStoreService } from "../components/c0oggass/sdk-c0oggass-params-store.service";
import { C0oggass, C0oggassDetailsResponse, C0oggassForm, C0oggassListDto, CheckSignResponse } from "../model/sdk-docassociati.model";
import { SdkDocAssociatiService } from "../services/sdk-docassociati.service";
import { TranslateService } from "@ngx-translate/core";



export interface C0oggassProviderArgs extends IDictionary<any> {
    action: "DETAIL";
    stazioneAppaltante?: StazioneAppaltanteBaseInfo;
    searchForm?: string;
    messagesPanel?: HTMLElement;
    buttonCode?: string;
    setUpdateState?: Function;
    syscon?: number;
    dialogConfigSubj?: BehaviorSubject<SdkDialogConfig>;
    item?: C0oggassListDto;
    c0acod?: number;
    c0aprg?: string;
    form: C0oggassForm;
}


@Injectable({ providedIn: "root" })
export class SdkC0oggassProvider extends SdkBaseProvider implements SdkProvider {
    constructor(inj: Injector) {
        super(inj);
    }

    public run(args: C0oggassProviderArgs): Observable<IDictionary<any>> {

        this.logger.debug("C0oggassProvider - C0oggassProviderArgs >>>", args);

        if (args.action === "DETAIL") {
            return this.c0oggassDetail(args);
        } else if (args.buttonCode === "back-to-detail") {
            return this.c0oggassDetail(args);
        } else if (args.action === "DELETE") {
            return this.c0oggassDelete(args);
        } else if (args.buttonCode === "back") {
            return this.back(args);
        } else if (args.action === "DOWNLOAD") {
            return this.downloadFromList(args);
        } else if (args.buttonCode === "download") {
            return this.downloadFromDetails(args);
        } else if (args.buttonCode === "docassociati-list") {
            return this.backToList(args);
        } else if (args.buttonCode === "back-to-c0oggass-list") {
            return this.backToList(args);
        } else if (args.buttonCode === "create") {
            return this.c0oggassCreate(args);
        } else if (args.buttonCode === "edit") {
            return this.c0oggassUpdate(args);
        } else if (args.buttonCode === "save-create" || args.buttonCode === "save-edit") {
            return this.c0oggassSave(args);
        } else if (args.buttonCode === "check-sign") {
            return this.checkSign(args);
        }

        return of(args);
    }

    private getDownloadfactoryFactory(codein: string, c0acod: number, c0aprg: string) {
        return () => {
            return this.sdkDocAssociatiService.c0oggassDownloadFile(codein, c0acod, c0aprg);
        };
    }

    private downloadFromDetails(args: C0oggassProviderArgs) : Observable<IDictionary<any>> {
        return this.download(args, args.c0acod, args.c0aprg);
    }

    private checkSignFactory(codein: string, c0acod: number, c0aprg: string) {
        return () => {
            return this.sdkDocAssociatiService.checkSign(codein, c0acod, c0aprg);
        };
    }

    private checkSign(args: C0oggassProviderArgs) : Observable<IDictionary<any>> {
        return this.checkSignFile(args, args.c0acod, args.c0aprg);
    }

    private checkSignFile(args: C0oggassProviderArgs, c0acod: number, c0aprg: string) : Observable<IDictionary<any>> {

        const messagesPanel: HTMLElement = args.messagesPanel;

        let factory = this.checkSignFactory(args.stazioneAppaltante.codice, c0acod, c0aprg);

        //Invia richiesta e gestisci risultato
        return this.requestHelper.begin(factory, messagesPanel).pipe(
            map((result: CheckSignResponse) => {
                if (result.esito) {

                    console.log("risposta")

                    return { open: true, signatory: result.signatory };

                } else {
                    if(result.errorData == 'ERROR-INVALID-SIGNATURE'){    
                        return { open: true, signatory: result.signatory, errorData:"SDK-DOC-ASSOCIATI.ERRORS."+result.errorData  };                       
                    } else{
                        this.sdkMessagePanelService.showError(messagesPanel, [{ message: "SDK-DOC-ASSOCIATI.ERRORS."+result.errorData }]);
                        this.scrollToMessagePanel(messagesPanel);
                    }
                    
                }
            }),
            catchError((error: any, caught: Observable<any>) => {
                this.scrollToMessagePanel(messagesPanel);
                return throwError(error);
            })
        );

    }

    private downloadFromList(args: C0oggassProviderArgs) : Observable<IDictionary<any>> {
        return this.download(args, args.item.c0acod, args.item.c0aprg);
    }

    private download(args: C0oggassProviderArgs, c0acod: number, c0aprg: string) : Observable<IDictionary<any>> {

        const messagesPanel: HTMLElement = args.messagesPanel;

        let factory = this.getDownloadfactoryFactory(args.stazioneAppaltante.codice, c0acod, c0aprg);

        //Invia richiesta e gestisci risultato
        return this.requestHelper.begin(factory, messagesPanel).pipe(
            map((result: FileDownloadResponse) => {
                if (result.esito) {

                    let link = document.createElement('a');
                    
                    link.download = result.fileName;
                    link.href = 'data:application/octet-stream;base64,' + escape(result.fileContent);
                    link.click();

                    return new Observable((ob: Observer<IDictionary<any>>) => {
                        ob.next(undefined);
                        ob.complete();
                    });

                } else {
                    this.sdkMessagePanelService.showError(messagesPanel, [{ message: result.errorData }]);
                    this.scrollToMessagePanel(messagesPanel);
                }
            }),
            catchError((error: any, caught: Observable<any>) => {
                this.scrollToMessagePanel(messagesPanel);
                return throwError(error);
            })
        );

    }

    protected back(args: C0oggassProviderArgs): Observable<IDictionary<any>> {

        let params: IDictionary<any> = {};

        let lastBreadcrumb = this.sdkC0oggassParamsStoreService.parentBreadcrumbs[this.sdkC0oggassParamsStoreService.parentBreadcrumbs.length - 1];

        this.routerService.navigateToPage(lastBreadcrumb.slug, params);

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    protected getDefaultFormItem(args: any, stazioneAppaltante: StazioneAppaltanteBaseInfo, syscon: number) {
        let item = args.form && args.form.item ? args.form.item : new C0oggass();
        return item;
    }

    private c0oggassDetail(args: C0oggassProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.c0oggassDetailsStoreService.clear();
        this.c0oggassDetailsStoreService.listDataItem = args.item;

        let params: IDictionary<any> = {
            c0acod: args.item.c0acod,
        };

        this.routerService.navigateToPage("sdk-c0oggass-details-page", params);

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private c0oggassDelete(args: C0oggassProviderArgs): Observable<any> {
        const messagesPanel: HTMLElement = args.messagesPanel;
        let factory = this.getC0oggassDeleteFactory(args.stazioneAppaltante.codice, args.item.c0acod);
        return this.requestHelper.begin(factory, args.messagesPanel).pipe(
            map((result: BaseResponse) => {
                if (!result.esito) {
                    this.sdkMessagePanelService.showError(messagesPanel, [{ message: result.errorData }]);
                    this.scrollToMessagePanel(messagesPanel);
                } else {
                    return { reload: true };
                }
            }),
            catchError((error: any, caught: Observable<any>) => {
                this.scrollToMessagePanel(messagesPanel);
                return throwError(error);
            })
        );
    }
    private getC0oggassDeleteFactory(codein: string, c0acod: number) {
        return () => {
            return this.sdkDocAssociatiService.c0oggassDelete(codein, c0acod);
        };
    }

    private c0oggassCreate(args: C0oggassProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
        };

        this.routerService.navigateToPage("sdk-c0oggass-create-page", params);

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    private c0oggassUpdate(args: C0oggassProviderArgs): Observable<IDictionary<any>> {
        let params: IDictionary<any> = {
            c0acod: args.item.c0acod,
        };

        this.routerService.navigateToPage("sdk-c0oggass-edit-page", params);

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }



    private backToList(args: C0oggassProviderArgs): Observable<IDictionary<any>> {
        let setUpdateState: Function = args.setUpdateState;
        if (isFunction(setUpdateState)) {
            setUpdateState(false);
        }

        this.routerService.navigateToPage("sdk-c0oggass-list-page", { load: "true" });

        return new Observable((ob: Observer<IDictionary<any>>) => {
            ob.next(undefined);
            ob.complete();
        });
    }

    // #region Getters

    private get c0oggassDetailsStoreService(): SdkC0oggassDetailsStoreService {
        return this.injectable(SdkC0oggassDetailsStoreService);
    }

    private getC0oggassCreateFactory(form: C0oggassForm) {
        return () => {
            return this.sdkDocAssociatiService.c0oggassCreate(form);
        };
    }

    private getC0oggassEditFactory(form: C0oggassForm) {
        return () => {
            return this.sdkDocAssociatiService.c0oggassUpdate(form);
        };
    }

    private c0oggassSave(args: C0oggassProviderArgs) {
        const defaultFormBuilderConfig: SdkFormBuilderConfiguration = args.defaultFormBuilderConfig;
        const formBuilderConfig: SdkFormBuilderConfiguration = args.formBuilderConfig;
        const messagesPanel: HTMLElement = args.messagesPanel;
        const setUpdateState: Function = args.setUpdateState;
        const stazioneAppaltante: StazioneAppaltanteBaseInfo = args.stazioneAppaltante;
        const syscon: number = args.syscon;

        // controllo che il modello sia cambiato dal default
        if (!isEqual(defaultFormBuilderConfig, formBuilderConfig)) {
            // controllo la validita' del modello
            let valid: boolean = this.commonValidationUtilsService.executeValidations(formBuilderConfig, messagesPanel);
            if (valid === true) {
                let factory: Function;

                //Genero l'oggetto di richiesta
                let item: C0oggass = this.populateRequest(formBuilderConfig, args, stazioneAppaltante, syscon);
                let wDocdigItem: WDocdigForm = this.populateRequest(formBuilderConfig, args, stazioneAppaltante, syscon, new WDocdigForm());

                let form = new C0oggassForm();
                form.stazioneAppaltante = stazioneAppaltante.codice;
                form.syscon = syscon;
                form.item = item;
                form.wDocdigItem = wDocdigItem;

                let isCreate = args.buttonCode === "save-create";

                //Aggiorna dati
                if (isCreate) {
                    factory = this.getC0oggassCreateFactory(form);
                } else {
                    factory = this.getC0oggassEditFactory(form);
                }

                //Invia richiesta e gestisci risultato
                return this.requestHelper.begin(factory, messagesPanel).pipe(
                    map((result: C0oggassDetailsResponse) => {
                        if (result.esito) {
                            setUpdateState(false);

                            //Return to details page
                            let params: IDictionary<any> = {
                                c0acod: result.item.c0acod,
                            };

                            if (isCreate) {
                                this.c0oggassDetailsStoreService.clear();
                                this.c0oggassDetailsStoreService.listDataItem = {
                                    ...result.item
                                }
                            }

                            this.routerService.navigateToPage("sdk-c0oggass-details-page", params);

                            return {
                                ...result,
                                defaultFormBuilderConfig,
                                formBuilderConfig,
                            };
                        } else {
                            this.sdkMessagePanelService.showError(messagesPanel, [{ message: result.errorData }]);
                            this.scrollToMessagePanel(messagesPanel);
                        }
                    }),
                    catchError((error: any, caught: Observable<any>) => {
                        this.scrollToMessagePanel(messagesPanel);
                        return throwError(error);
                    })
                );
            } else {
                this.scrollToMessagePanel(messagesPanel);
            }
        } else {
            this.sdkMessagePanelService.showError(messagesPanel, [
                {
                    message: "VALIDATORS.FORM-NON-COMPILATA",
                },
            ]);
            this.scrollToMessagePanel(messagesPanel);
        }
    }
    // #endregion

    protected get sdkDocAssociatiService(): SdkDocAssociatiService {
        return this.injectable(SdkDocAssociatiService);
    }

    protected get sdkC0oggassParamsStoreService(): SdkC0oggassParamsStoreService {
        return this.injectable(SdkC0oggassParamsStoreService);
    }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

}
