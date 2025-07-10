import { HttpErrorResponse } from '@angular/common/http';
import {
    ChangeDetectorRef,
    Component,
    ElementRef,
    HostBinding,
    Injector,
    OnDestroy,
    OnInit,
    ViewChild,
    ViewEncapsulation,
} from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { IDictionary, SdkBusinessAbstractWidget, SdkProviderService } from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkMessagePanelService,
    SdkMessagePanelTranslate,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, isEmpty, isEqual, isObject, map as mapArray, toString } from 'lodash-es';
import { BehaviorSubject, Observable, of, Subject, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { CustomParamsFunction, ResponseDTO } from '../../model/lib.model';

import { AuthenticationService } from '../../services/authentication.service';
import { FormBuilderUtilsService } from '../../utils/form-builder-utils.service';

@Component({
    templateUrl: 'sdk-esegui-recupero-password.component.html',
    styleUrls: ['./sdk-esegui-recupero-password.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class SdkEseguiRecuperoPasswordComponent extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    @HostBinding('class') classNames = `sdk-esegui-recupero-password-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;
    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    public formBuilderConfigObs: BehaviorSubject<SdkFormBuilderConfiguration> = new BehaviorSubject(null);
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput> = new BehaviorSubject(null);
    public dialogConfigObs: Observable<SdkDialogConfig>;
    public showForm: boolean = false;

    private buttons: SdkButtonGroupInput;
    private buttonsError: SdkButtonGroupInput;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private dialogConfig: SdkDialogConfig;
    private token: string;
    private validToken: boolean = false;

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.initButtons();
        this.loadQueryParams();
        this.initDialog();
    }

    protected onAfterViewInit(): void {
        this.loadCheckToken().pipe(
            map(this.elaborateCheckToken),
            map(() => this.checkInfoBox()),
            map(() => this.elaborateButtons()),
            map(() => this.loadForm()),
            catchError(this.handleError)
        ).subscribe();
    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.isReady = true
            })
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Public

    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {
            this.executeButtonProvider(button);
        }
    }

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    // #endregion

    // #region Getters

    private get activatedRoute(): ActivatedRoute { return this.injectable(ActivatedRoute) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get authenticationService(): AuthenticationService { return this.injectable(AuthenticationService) }

    // #endregion

    // #region Private

    private get messagesPanel(): HTMLElement {
        return isObject(this._messagesPanel) ? this._messagesPanel.nativeElement : undefined;
    }

    private get infoBox(): HTMLElement {
        return this._infoBox != null ? this._infoBox.nativeElement : undefined;
    }

    private loadQueryParams(): void {
        let paramsMap: ParamMap = this.activatedRoute.snapshot.queryParamMap;
        this.token = paramsMap.get('token');
    }

    private loadForm(): void {
        if (this.showForm) {

            let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

            let formConfig: SdkFormBuilderConfiguration = {
                fields
            };

            let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any, dynamicField: boolean) => {
                let mapping: boolean = true;

                let keyAny: any = get(restObject, field.mappingInput);
                let key: string = dynamicField === true ? field.data : toString(keyAny);

                if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                    [field, mapping] = this.formBuilderUtilsService.populateListCode({}, field, key, mapping);
                }

                return {
                    mapping,
                    field
                };
            }

            formConfig = this.formBuilderUtilsService.populateForm(formConfig, false, customPopulateFunction);

            this.defaultFormBuilderConfig = cloneDeep(formConfig);
            this.formBuilderConfig = formConfig;

            this.formBuilderConfigObs.next(formConfig);
        }
    }

    private executeButtonProvider(button: SdkButtonGroupOutput): void {
        let data: IDictionary<any> = {
            buttonCode: button.code,
            messagesPanel: this.messagesPanel,
            defaultFormBuilderConfig: this.defaultFormBuilderConfig,
            formBuilderConfig: this.formBuilderConfig,
            setUpdateState: this.setUpdateState,
            token: this.token
        };

        if (button.code === 'back-to-home-page' && isEqual(this.defaultFormBuilderConfig, this.formBuilderConfig) === false) {
            this.back(button, data);
        } else {
            this.provider.run(button.provider, data).subscribe();
        }
    }

    private initButtons(): void {
        this.buttons = {
            buttons: this.config.body.buttons
        };

        this.buttonsError = {
            buttons: this.config.body.buttonsError
        };
    }

    private elaborateButtons = () => {
        if (this.showForm) {
            this.buttonsSubj.next(this.buttons);
        } else {
            this.buttonsSubj.next(this.buttonsError);
        }
    }

    private handleError = (err: any) => {
        this.buttonsSubj.next(this.buttonsError);
        return throwError(() => err);
    }

    private initDialog(): void {
        this.dialogConfig = {
            header: this.translateService.instant('DIALOG.BACK-TITLE'),
            message: this.translateService.instant('DIALOG.BACK-TEXT'),
            acceptLabel: this.translateService.instant('DIALOG.CONFIRM-ACTION'),
            rejectLabel: this.translateService.instant('DIALOG.CANCEL-ACTION'),
            open: new Subject()
        };
        this.dialogConfigObs = of(this.dialogConfig);
    }

    private back(button: SdkButtonGroupOutput, data: IDictionary<any>): void {
        let func = this.backConfirm(button, data);
        this.dialogConfig.open.next(func);
    }

    private backConfirm(button: SdkButtonGroupOutput, data: IDictionary<any>): any {
        return () => {
            this.provider.run(button.provider, data).subscribe();
        }
    }

    private checkInfoBox(): void {
        if (!isEmpty(this.config.infoBox)) {
            this.sdkMessagePanelService.clear(this.infoBox);
            this.sdkMessagePanelService.showInfoBox(this.infoBox, {
                message: this.config.infoBox
            });
        }
    }

    private loadCheckToken(): Observable<ResponseDTO<boolean>> {
        return this.authenticationService.checkPasswordRecoveryToken(this.token)
            .pipe(
                catchError((error: HttpErrorResponse, _caught: Observable<any>) => {
                    let err: ResponseDTO<any> = error.error;
                    if (err != null && err.messages != null && err.messages.length > 0) {
                        let messages: Array<SdkMessagePanelTranslate> = mapArray(err.messages, (one: string) => {
                            let message: SdkMessagePanelTranslate = {
                                message: `SDK-RECUPERO-PASSWORD.VALIDATORS.${one}`
                            };
                            return message;
                        });
                        this.sdkMessagePanelService.showError(this.messagesPanel, messages);
                    }
                    return throwError(() => error);
                })
            );
    }

    private elaborateCheckToken = (result: ResponseDTO<boolean>) => {
        this.validToken = result.response;
        this.showForm = !!this.validToken;
        if (!this.showForm) {
            this.sdkMessagePanelService.showWarning(this.messagesPanel, [
                {
                    message: 'SDK-RECUPERO-PASSWORD.INVALID-TOKEN'
                }
            ]);
        }
        this.markForCheck();
    }

    // #endregion
}