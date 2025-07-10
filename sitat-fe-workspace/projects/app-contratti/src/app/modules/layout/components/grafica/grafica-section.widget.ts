import {
    ChangeDetectionStrategy,
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
import {
    AbilitazioniUtilsService,
    CustomParamsFunction,
    FormBuilderUtilsService,
    GridUtilsService,
    ProtectionUtilsService,
} from '@maggioli/app-commons';
import {
    IDictionary,
    SdkBusinessAbstractWidget,
    SdkProviderService,
    SdkStoreService,
    UserProfile,
} from '@maggioli/sdk-commons';
import {
    SdkButtonGroupInput,
    SdkButtonGroupOutput,
    SdkDialogConfig,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormBuilderInput,
    SdkMenuTab,
    SdkMessagePanelService,
    SdkModalConfig,
    SdkSidebarConfig,
    SdkTextOutput,
} from '@maggioli/sdk-controls';
import { SdkDatasourceService, SdkTableConfig, SdkTableToolbarActionPerfomer } from '@maggioli/sdk-table';
import { TranslateService } from '@ngx-translate/core';
import { cloneDeep, get, isEmpty, isObject, isString, remove, toString } from 'lodash-es';
import { BehaviorSubject, Observable, Subject } from 'rxjs';

import { Constants } from '../../../../app.constants';
import { EsempioDatasource } from './grafica.datasource.service';

@Component({
    templateUrl: `grafica-section.widget.html`,
    encapsulation: ViewEncapsulation.None,
    changeDetection: ChangeDetectionStrategy.OnPush
})
export class GraficaSectionWidget extends SdkBusinessAbstractWidget<void> implements OnInit, OnDestroy {

    // #region Variables

    @HostBinding('class') classNames = `grafica-section`;

    @ViewChild('messages') _messagesPanel: ElementRef;

    @ViewChild('infoBox') _infoBox: ElementRef;

    public config: any = {};
    private buttons: SdkButtonGroupInput;
    public formBuilderConfigObs: Subject<SdkFormBuilderConfiguration> = new Subject();
    public formBuilderDataSubject: Subject<SdkFormBuilderInput> = new Subject();
    private formBuilderConfig: SdkFormBuilderConfiguration;
    private modalConfig: SdkModalConfig<any, void, any>;
    public modalConfigObs: Subject<SdkModalConfig<any, void, any>> = new Subject();
    private infoBoxModalConfig: IDictionary<any>;
    public buttonsSubj: BehaviorSubject<SdkButtonGroupInput>;
    private defaultFormBuilderConfig: SdkFormBuilderConfiguration;
    private menuTabs: Array<SdkMenuTab>;
    public configSub = new BehaviorSubject<SdkTableConfig>(null);
    private gridConfig: SdkTableConfig;
    public resetTable: Subject<boolean> = new Subject();
    private datasource: EsempioDatasource;
    private performers: IDictionary<SdkTableToolbarActionPerfomer>;
    private userProfile: UserProfile;
    private fasiSlugMap: IDictionary<string>;
    private statoIcons: IDictionary<string>;
    private sidebarConfig: SdkSidebarConfig;
    public sidebarConfigObs: Subject<SdkSidebarConfig> = new Subject();
    private dialogConfig: SdkDialogConfig;
    public dialogConfigObs: Observable<SdkDialogConfig>;

    // #endregion

    constructor(inj: Injector, cdr: ChangeDetectorRef) { super(inj, cdr) }

    // #region Hooks

    protected onInit(): void {
        this.addSubscription(this.store.select(Constants.USER_PROFILE_SELECT).subscribe((userProfile: UserProfile) => {
            this.userProfile = userProfile;
        }));


        this.loadButtons();



    }

    protected onAfterViewInit(): void {
        this.refreshTabs();
        this.loadForm();


    }

    protected onDestroy(): void { }

    protected onConfig(config: any): void {
        if (isObject(config)) {
            this.markForCheck(() => {
                this.config = { ...config };
                this.menuTabs = this.config.menuTabs;
                if (isObject(get(this.config.body, 'sidebar'))) {
                    this.sidebarConfig = get(this.config.body, 'sidebar');
                    this.sidebarConfig.openSubject = new Subject();
                    this.sidebarConfigObs.next(this.sidebarConfig);
                }
                this.isReady = true;
            });
        }
    }

    protected onUpdateState(state: boolean): void { }

    // #endregion

    // #region Getters

    private get protectionUtilsService(): ProtectionUtilsService { return this.injectable(ProtectionUtilsService) }

    private get provider(): SdkProviderService { return this.injectable(SdkProviderService) }

    private get abilitazioniUtilsService(): AbilitazioniUtilsService { return this.injectable(AbilitazioniUtilsService) }

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get gridUtilsService(): GridUtilsService { return this.injectable(GridUtilsService) }

    private get factory(): SdkDatasourceService { return this.injectable(SdkDatasourceService) }

    private get store(): SdkStoreService { return this.injectable(SdkStoreService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    private get sdkMessagePanelService(): SdkMessagePanelService { return this.injectable(SdkMessagePanelService) }

    // #endregion

    // #region Public

    public manageFormOutput(formConfig: SdkFormBuilderConfiguration): void {
        this.formBuilderConfig = formConfig;
    }

    public manageOutputField(field: SdkFormBuilderField): void { }

    public manageFormClick(config: SdkTextOutput): void {
        if (isObject(config)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: config.modalComponent,
                componentConfig: config.modalComponentConfig
            };
            this.modalConfigObs.next(this.modalConfig);
            this.modalConfig.openSubject.next(true);
        }
    }



    public manageOutputInfoBox(item: SdkFormBuilderField): void {
        if (isObject(this.infoBoxModalConfig) && isObject(item)) {
            this.modalConfig = {
                ...this.modalConfig,
                component: get(this.infoBoxModalConfig, 'component'),
                componentConfig: {
                    fields: get(this.infoBoxModalConfig, 'fields'),
                    buttons: get(this.infoBoxModalConfig, 'buttons'),
                    mnemonico: item.mnemonico
                }
            }
            this.modalConfigObs.next(this.modalConfig);
            setTimeout(() => this.modalConfig.openSubject.next(true));
        }
    }


    public onButtonClick(button: SdkButtonGroupOutput): void {

        if (isObject(button) && isEmpty(button.provider) === false) {

            let data: IDictionary<any> = {

            };

            this.manageExecutionProvider(get(this.config.body, 'sidebar'));
            if (button.provider != 'TEST') {
                this.provider.run(button.provider, data).subscribe();
            }


        }
    }


    // #endregion

    // #region Private



    private loadButtons(): void {
        this.buttons = {
            buttons: this.config.body.buttons
        };
        this.buttonsSubj = new BehaviorSubject(this.buttons);
    }

    private loadForm(): void {
        let fields: Array<SdkFormBuilderField> = cloneDeep(get(this.config, 'body.fields'));

        let formConfig: SdkFormBuilderConfiguration = {
            fields
        };

        let customPopulateFunction: CustomParamsFunction = (field: SdkFormBuilderField, restObject: any) => {
            let mapping: boolean = true;

            let keyAny: any = get(restObject, field.mappingInput);
            let key: string = toString(keyAny);

            if (!isEmpty(field.listCode) && field.type === 'TEXT' && !isEmpty(key)) {
                [field, mapping] = this.formBuilderUtilsService.populateListCode(undefined, field, key, mapping);
            }

            if (field.modalComponentConfig != null && isString(field.modalComponentConfig)) {
                field.modalComponentConfig = cloneDeep(get(this.config, field.modalComponentConfig));
            }

            return {
                mapping,
                field
            };
        }

        let esempioText = 'PROVA';
        let esempioTextbox = '';
        let esempioModale = 'Clicca qui'
        formConfig = this.formBuilderUtilsService.populateForm(formConfig, true, customPopulateFunction, {
            esempioTextbox,
            esempioText,
            esempioModale
        }, undefined);

        this.defaultFormBuilderConfig = cloneDeep(formConfig);
        this.formBuilderConfig = formConfig;
        this.formBuilderConfigObs.next(formConfig);

        this.modalConfig = {
            code: 'modal',
            title: '',
            openSubject: new Subject()
        };
        this.modalConfigObs.next(this.modalConfig);
    }


    private refreshTabs(): void {
        remove(this.menuTabs, (one: SdkMenuTab) => {
            if (!isEmpty(one.oggettoProtezione)) {
                let visible: boolean = this.protectionUtilsService.isMenuTabVisible(one.oggettoProtezione, this.userProfile.configurations);
                if (visible !== true) {
                    return true;
                }
            }
            if (!isEmpty(one.visible)) {
                let visible: boolean = this.provider.run(one.visible);
                return visible === false;
            }
            return false;
        });
        this.sdkLayoutMenuTabs.emit(this.menuTabs);
    }


    private initGrid(): void {

        this.datasource = this.factory.create(EsempioDatasource, {

        });

        this.gridConfig = cloneDeep(this.config.body.grid);
        this.gridConfig = this.gridUtilsService.parseDescriptor(this.gridConfig, this.datasource, this.performers, this.config.locale.dateFormat, this.userProfile.configurations);

        this.configSub.next(this.gridConfig);
    }



    private manageExecutionProvider(obj: any) {
        this.sidebarConfig.componentConfig = obj.componentConfig;
        this.sidebarConfigObs.next(this.sidebarConfig);
        this.sidebarConfig.openSubject.next(true);

    }



    // #endregion
}
