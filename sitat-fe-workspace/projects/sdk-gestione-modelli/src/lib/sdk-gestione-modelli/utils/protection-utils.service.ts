import { Injectable, Injector } from '@angular/core';
import { IDictionary, SdkBaseService, SdkValidator } from '@maggioli/sdk-commons';
import {
    SdkBasicButtonInput,
    SdkButtonGroupSingleInput,
    SdkDropdownButtonData,
    SdkFormBuilderConfiguration,
    SdkFormBuilderField,
    SdkFormFieldGroupConfiguration,
    SdkTextboxMatrixCellConfig,
    SdkTextboxMatrixRowConfig,
} from '@maggioli/sdk-controls';
import { TranslateService } from '@ngx-translate/core';
import {
    each,
    filter,
    find,
    get,
    has,
    head,
    includes,
    isEmpty,
    isObject,
    isUndefined,
    reduce,
    remove,
    split,
} from 'lodash-es';

import { ProfiloConfiguration, ProfiloConfigurationItem } from '../model/lib.model';
import { FormBuilderUtilsService } from './form-builder-utils.service';

@Injectable({
    providedIn: 'root'
  })
export class ProtectionUtilsService extends SdkBaseService {

    private readonly typeMap: IDictionary<string> = {
        COLONNE: 'COLS',
        FUNZIONI: 'FUNZ',
        MASCHERE: 'MASC',
        MENU: 'MENU',
        PAGINE: 'PAGE',
        SEZIONI: 'SEZ',
        SUBMENU: 'SUBMENU',
        TABS: 'TABS'
    };

    private readonly actionVis: string = 'VIS';
    private readonly actionMan: string = 'MAN';
    private readonly actionMod: string = 'MOD';
    private readonly actionDel: string = 'DEL';

    constructor(inj: Injector) {
        super(inj);
    }

    // #region Public

    /**
     * Metodo che applica alla form le protezioni recuperate dal server
     * @param form La form
     * @param profiloConfiguration La configurazione di profilo contenente le protezioni
     * @returns La form con le protezioni applicate
     */
    public applyFormProtections(form: SdkFormBuilderConfiguration, profiloConfiguration: ProfiloConfiguration, checkEdit: boolean = true, checkCampiSolaLetturaDaConfig: boolean = true, checkMandatory: boolean = true): SdkFormBuilderConfiguration {
        let protectionMap: IDictionary<ProfiloConfigurationItem> = this.getProtezioniMap(profiloConfiguration);
        if (isObject(form) && isObject(protectionMap)) {
            form.fields = this.elaborateSectionProtection(form.fields, protectionMap, checkEdit);
            form.fields = this.elaborateOneProtection(form.fields, protectionMap, checkEdit, checkMandatory);
            each(form.fields, (field: SdkFormBuilderField, index: number) => {
                if (field.type === 'FORM-SECTION') {
                    field = this.elaborateSection(field, protectionMap, checkEdit, checkMandatory);
                } else if (field.type === 'FORM-GROUP') {
                    field = this.elaborateGroup(field, protectionMap, checkEdit, checkMandatory);
                }
                form.fields[index] = field;
            });
        }
        if (checkCampiSolaLetturaDaConfig === true) {
            form = this.checkCampiSolaLetturaDaConfig(form, profiloConfiguration);
        }
        return form;
    }

    /**
     * Metodo che verifica la visibilita' di un menu tab
     * @param menuTabProtectionCode Il codice protezione da verificare
     * @param profiloConfiguration La configurazione di profilo contenente le protezioni
     * @returns Ritorna se il menu tab e' visibile o nascosto
     */
    public isMenuTabVisible(menuTabProtectionCode: string, profiloConfiguration: ProfiloConfiguration): boolean {
        if (!isEmpty(menuTabProtectionCode)) {
            let protectionMap: IDictionary<ProfiloConfigurationItem> = this.getProtezioniMap(profiloConfiguration);
            let type: string = get(this.typeMap, 'PAGINE');
            let keyVis: string = `${type}.${this.actionVis}.${menuTabProtectionCode}`;
            let visible: boolean = this.checkKey(protectionMap, keyVis);
            if (visible === true) {
                // se e' visibile mi fermo qua
                return true;
            } else if (visible === false) {
                // se non e' visibile flaggo il campo come "da cancellare"
                return false;
            } else {
                // altrimenti verifico per il gruppo schema.entity.*
                let schema: string = head(split(menuTabProtectionCode, '.'));
                let entity: string = split(menuTabProtectionCode, '.')[1];
                let schemaEntityKey: string = `${type}.${this.actionVis}.${schema}.${entity}.*`;
                visible = this.checkKey(protectionMap, schemaEntityKey);
                if (visible === true) {
                    // se e' visibile mi fermo qua
                    return true;
                } else if (visible === false) {
                    // se non e' visibile flaggo il campo come "da cancellare"
                    return false;
                } else {
                    // altrimenti verifico per il gruppo schema.*
                    let schemaKey: string = `${type}.${this.actionVis}.${schema}.*`;
                    visible = this.checkKey(protectionMap, schemaKey);
                    if (visible === true) {
                        // se e' visibile mi fermo qua
                        return true;
                    } else if (visible === false) {
                        // se non e' visibile flaggo il campo come "da cancellare"
                        return false;
                    } else {
                        // altrimenti verifico per il gruppo *
                        keyVis = `${type}.${this.actionVis}.*`;
                        visible = this.checkKey(protectionMap, keyVis);
                        if (visible === false) {
                            // se non e' visibile flaggo il campo come "da cancellare" altrimenti il campo va lasciato
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * @description Metodo che filtra una lista di pulsanti tramite oggettoProtezione
     * @author Cristiano Perin
     * @date 2020-02-24
     * @param {Array<SdkBasicButtonInput>} buttons
     * @param {ProfiloConfiguration} profiloConfiguration
     * @returns {Array<SdkBasicButtonInput>}
     * @memberof ProtectionUtilsService
     */
    public checkButtonsProtection(buttons: Array<SdkBasicButtonInput>, profiloConfiguration: ProfiloConfiguration): Array<SdkBasicButtonInput> {
        if (buttons != null && !isEmpty(buttons)) {
            // filter VIS
            buttons = filter(buttons, (one: SdkBasicButtonInput) => {
                return this.checkButtonProtection(one, profiloConfiguration, this.actionVis);
            });
            // filter MOD
            buttons = filter(buttons, (one: SdkBasicButtonInput) => {
                return this.checkButtonProtection(one, profiloConfiguration, this.actionMod);
            });
            // filter DEL
            buttons = filter(buttons, (one: SdkBasicButtonInput) => {
                return this.checkButtonProtection(one, profiloConfiguration, this.actionDel);
            });

            // check dropdown
            each(buttons, (one: SdkButtonGroupSingleInput) => {
                if (one.dropdown === true && one.dropdownData != null && one.dropdownData.length > 0) {
                    // filter VIS
                    one.dropdownData = filter(one.dropdownData, (one: SdkDropdownButtonData) => {
                        return this.checkButtonProtection(one, profiloConfiguration, this.actionVis);
                    });
                    // filter MOD
                    one.dropdownData = filter(one.dropdownData, (one: SdkDropdownButtonData) => {
                        return this.checkButtonProtection(one, profiloConfiguration, this.actionMod);
                    });
                    // filter DEL
                    one.dropdownData = filter(one.dropdownData, (one: SdkDropdownButtonData) => {
                        return this.checkButtonProtection(one, profiloConfiguration, this.actionDel);
                    });

                }
            });
            return buttons;
        }
        return null;
    }

    /**
     * @description Metodo che filtra una lista di colonne della griglia tramite oggettoProtezione
     * @author Cristiano Perin
     * @date 2020-02-25
     * @param {string} oggettoProtezione
     * @param {ProfiloConfiguration} profiloConfiguration
     * @returns {boolean}
     * @memberof ProtectionUtilsService
     */
    public checkGridColumnProtection(oggettoProtezione: string, profiloConfiguration: ProfiloConfiguration): boolean {
        if (!isEmpty(oggettoProtezione)) {
            const azione: string = this.actionVis;
            let protectionMap: IDictionary<ProfiloConfigurationItem> = this.getProtezioniMap(profiloConfiguration);
            let type: string = get(this.typeMap, 'COLONNE');
            let keyVis: string = `${type}.${azione}.${oggettoProtezione}`;
            let visible: boolean = this.checkKey(protectionMap, keyVis);
            if (visible === true) {
                // se e' visibile mi fermo qua
                return true;
            } else if (visible === false) {
                // se non e' visibile flaggo il campo come "da cancellare"
                return false;
            } else {
                // altrimenti verifico per il gruppo schema.entity.*
                let schema: string = head(split(oggettoProtezione, '.'));
                let entity: string = split(oggettoProtezione, '.')[1];
                let schemaEntityKey: string = `${type}.${azione}.${schema}.${entity}.*`;
                visible = this.checkKey(protectionMap, schemaEntityKey);
                if (visible === true) {
                    // se e' visibile mi fermo qua
                    return true;
                } else if (visible === false) {
                    // se non e' visibile flaggo il campo come "da cancellare"
                    return false;
                } else {
                    // altrimenti verifico per il gruppo schema.*
                    let schemaKey: string = `${type}.${azione}.${schema}.*`;
                    visible = this.checkKey(protectionMap, schemaKey);
                    if (visible === true) {
                        // se e' visibile mi fermo qua
                        return true;
                    } else if (visible === false) {
                        // se non e' visibile flaggo il campo come "da cancellare"
                        return false;
                    } else {
                        // altrimenti verifico per il gruppo *
                        keyVis = `${type}.${azione}.*`;
                        visible = this.checkKey(protectionMap, keyVis);
                        if (visible === false) {
                            // se non e' visibile flaggo il campo come "da cancellare" altrimenti il campo va lasciato
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public checkCampiSolaLetturaDaConfig(form: SdkFormBuilderConfiguration, profiloConfiguration: ProfiloConfiguration): SdkFormBuilderConfiguration {
        const campiSolaLettura: Array<string> = profiloConfiguration.campiSolaLettura;
        if (form != null && campiSolaLettura != null) {
            each(form.fields, (field: SdkFormBuilderField, index: number) => {
                if (field.type === 'FORM-SECTION') {
                    field = this.elaborateSectionSolaLettura(field, campiSolaLettura);
                } else if (field.type === 'FORM-GROUP') {
                    field = this.elaborateGroupSolaLettura(field, campiSolaLettura);
                } else {
                    field = this.elaborateOneSolaLettura(field, campiSolaLettura);
                }
                form.fields[index] = field;
            });
        }
        return form;
    }

    public checkGridActionProtection(oggettoProtezione: string, profiloConfiguration: ProfiloConfiguration): boolean {
        if (!isEmpty(oggettoProtezione)) {
            let protectionMap: IDictionary<ProfiloConfigurationItem> = this.getProtezioniMap(profiloConfiguration);
            let type: string = get(this.typeMap, 'FUNZIONI');
            let keyVis: string = `${type}.${this.actionVis}.${oggettoProtezione}`;
            let visible: boolean = this.checkKey(protectionMap, keyVis);
            if (visible === true) {
                // se e' visibile mi fermo qua
                return true;
            } else if (visible === false) {
                // se non e' visibile flaggo il campo come "da cancellare"
                return false;
            } else {
                // altrimenti verifico per il gruppo schema.entity.*
                let schema: string = head(split(oggettoProtezione, '.'));
                let entity: string = split(oggettoProtezione, '.')[1];
                let schemaEntityKey: string = `${type}.${this.actionVis}.${schema}.${entity}.*`;
                visible = this.checkKey(protectionMap, schemaEntityKey);
                if (visible === true) {
                    // se e' visibile mi fermo qua
                    return true;
                } else if (visible === false) {
                    // se non e' visibile flaggo il campo come "da cancellare"
                    return false;
                } else {
                    // altrimenti verifico per il gruppo schema.*
                    let schemaKey: string = `${type}.${this.actionVis}.${schema}.*`;
                    visible = this.checkKey(protectionMap, schemaKey);
                    if (visible === true) {
                        // se e' visibile mi fermo qua
                        return true;
                    } else if (visible === false) {
                        // se non e' visibile flaggo il campo come "da cancellare"
                        return false;
                    } else {
                        // altrimenti verifico per il gruppo *
                        keyVis = `${type}.${this.actionVis}.*`;
                        visible = this.checkKey(protectionMap, keyVis);
                        if (visible === false) {
                            // se non e' visibile flaggo il campo come "da cancellare" altrimenti il campo va lasciato
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public checkGridLinkProtection(oggettoProtezione: string, profiloConfiguration: ProfiloConfiguration, oggettoProtezioneType?: string): boolean {
        if (!isEmpty(oggettoProtezione)) {
            let protectionMap: IDictionary<ProfiloConfigurationItem> = this.getProtezioniMap(profiloConfiguration);

            let type: string;

            if (!isEmpty(oggettoProtezioneType)) {
                type = oggettoProtezioneType;
            } else {
                type = get(this.typeMap, 'MASC');
            }

            let keyVis: string = `${type}.${this.actionVis}.${oggettoProtezione}`;
            let visible: boolean = this.checkKey(protectionMap, keyVis);
            if (visible === true) {
                // se e' visibile mi fermo qua
                return true;
            } else if (visible === false) {
                // se non e' visibile flaggo il campo come "da cancellare"
                return false;
            } else {
                // altrimenti verifico per il gruppo schema.entity.*
                let schema: string = head(split(oggettoProtezione, '.'));
                let entity: string = split(oggettoProtezione, '.')[1];
                let schemaEntityKey: string = `${type}.${this.actionVis}.${schema}.${entity}.*`;
                visible = this.checkKey(protectionMap, schemaEntityKey);
                if (visible === true) {
                    // se e' visibile mi fermo qua
                    return true;
                } else if (visible === false) {
                    // se non e' visibile flaggo il campo come "da cancellare"
                    return false;
                } else {
                    // altrimenti verifico per il gruppo schema.*
                    let schemaKey: string = `${type}.${this.actionVis}.${schema}.*`;
                    visible = this.checkKey(protectionMap, schemaKey);
                    if (visible === true) {
                        // se e' visibile mi fermo qua
                        return true;
                    } else if (visible === false) {
                        // se non e' visibile flaggo il campo come "da cancellare"
                        return false;
                    } else {
                        // altrimenti verifico per il gruppo *
                        keyVis = `${type}.${this.actionVis}.*`;
                        visible = this.checkKey(protectionMap, keyVis);
                        if (visible === false) {
                            // se non e' visibile flaggo il campo come "da cancellare" altrimenti il campo va lasciato
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    // #endregion

    // #region private

    private getProtezioniMap(profiloConfiguration: ProfiloConfiguration): IDictionary<ProfiloConfigurationItem> {
        let protectionMap: IDictionary<ProfiloConfigurationItem> = reduce(profiloConfiguration.configurazioni, (map: IDictionary<any>, one: ProfiloConfigurationItem) => {
            map[one.key] = one;
            return map;
        }, {});
        return protectionMap;
    }

    private elaborateSection(field: SdkFormBuilderField, protectionMap: IDictionary<ProfiloConfigurationItem>, checkEdit: boolean, checkMandatory: boolean): SdkFormBuilderField {
        field.fieldSections = this.elaborateSectionProtection(field.fieldSections, protectionMap, checkEdit);
        field.fieldSections = this.elaborateOneProtection(field.fieldSections, protectionMap, checkEdit, checkMandatory);
        each(field.fieldSections, (one: SdkFormBuilderField, index: number) => {
            if (one.type === 'FORM-SECTION') {
                one = this.elaborateSection(one, protectionMap, checkEdit, checkMandatory);
            } else if (one.type === 'FORM-GROUP') {
                one = this.elaborateGroup(one, protectionMap, checkEdit, checkMandatory);
            }
            field.fieldSections[index] = one;
        });
        return field;
    }

    private elaborateGroup(field: SdkFormBuilderField, protectionMap: IDictionary<ProfiloConfigurationItem>, checkEdit: boolean, checkMandatory: boolean): SdkFormBuilderField {

        if (!isEmpty(field['oggettoProtezioneInsert'])) {
            let showButtonAdd: boolean = this.checkFormGroupFunc(field['oggettoProtezioneInsert'], protectionMap);
            field.showButtonAdd = showButtonAdd;
        }
        if (!isEmpty(field['oggettoProtezioneDelete'])) {
            let showButtonRemove: boolean = this.checkFormGroupFunc(field['oggettoProtezioneInsert'], protectionMap);
            field.showButtonRemove = showButtonRemove;
        }

        each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration, index: number) => {
            group.fields = this.elaborateSectionProtection(group.fields, protectionMap, checkEdit);
            group.fields = this.elaborateOneProtection(group.fields, protectionMap, checkEdit, checkMandatory);
            each(group.fields, (one: SdkFormBuilderField, index2: number) => {
                if (one.type === 'FORM-SECTION') {
                    one = this.elaborateSection(one, protectionMap, checkEdit, checkMandatory);
                } else if (one.type === 'FORM-GROUP') {
                    one = this.elaborateGroup(one, protectionMap, checkEdit, checkMandatory);
                }
                group.fields[index2] = one;
            });
            field.fieldGroups[index] = group;
        });
        return field;
    }


    checkFormGroupFunc(oggettoProtezione: string, protectionMap: IDictionary<ProfiloConfigurationItem>): boolean {
        let type: string = get(this.typeMap, 'FUNZIONI');
        let keyVis: string = `${type}.${this.actionVis}.${oggettoProtezione}`;
        let visible: boolean = this.checkKey(protectionMap, keyVis);
        if (visible == null || visible == undefined) {
            return true;
        }
        else return visible;
    }

    private elaborateSectionProtection(fields: Array<SdkFormBuilderField>, protectionMap: IDictionary<ProfiloConfigurationItem>, checkEdit: boolean): Array<SdkFormBuilderField> {
        each(fields, (field: SdkFormBuilderField) => {
            if (field.type === 'FORM-SECTION' && !isEmpty(field.oggettoProtezione)) {
                // costruisco la chiave relativa alla sezione per controllare la visibilita'
                let type: string = get(this.typeMap, 'SEZIONI');
                let keyVis: string = `${type}.${this.actionVis}.${field.oggettoProtezione}`;
                let visible: boolean = this.checkKey(protectionMap, keyVis);
                if (visible === false) {
                    // se non e' visibile flaggo il campo come "da cancellare"
                    field.delete = true;
                } else if (isUndefined(visible)) {
                    // altrimenti verifico per il gruppo schema.entity.*
                    let schema: string = head(split(field.oggettoProtezione, '.'));
                    let entity: string = split(field.oggettoProtezione, '.')[1];
                    let schemaEntityKey: string = `${type}.${this.actionVis}.${schema}.${entity}.*`;
                    visible = this.checkKey(protectionMap, schemaEntityKey);
                    if (visible === false) {
                        // se non e' visibile flaggo il campo come "da cancellare"
                        field.delete = true;
                    } else if (isUndefined(visible)) {
                        // altrimenti verifico per il gruppo schema.*
                        let schemaKey: string = `${type}.${this.actionVis}.${schema}.*`;
                        visible = this.checkKey(protectionMap, schemaKey);
                        if (visible === false) {
                            // se non e' visibile flaggo il campo come "da cancellare"
                            field.delete = true;
                        } else if (isUndefined(visible)) {
                            // altrimenti verifico per il gruppo *
                            keyVis = `${type}.${this.actionVis}.*`;
                            visible = this.checkKey(protectionMap, keyVis);
                            if (visible === false) {
                                // se non e' visibile flaggo il campo come "da cancellare" altrimenti la sezione va lasciata
                                field.delete = true;
                            }
                        }
                    }
                }
            }
        });
        // rimuovo le sezioni non visibili
        remove(fields, (one: SdkFormBuilderField) => one.delete === true);

        if (checkEdit === true) {
            // per le sezioni rimaste controllo la modificabilita'
            each(fields, (field: SdkFormBuilderField) => {
                if (field.type === 'FORM-SECTION' && !isEmpty(field.oggettoProtezione)) {
                    // costruisco la chiave relativa alla sezione per controllare la modificabilita'
                    let type: string = get(this.typeMap, 'SEZIONI');
                    let keyMod: string = `${type}.${this.actionMod}.${field.oggettoProtezione}`;
                    let editable: boolean = this.checkKey(protectionMap, keyMod);
                    if (editable === false) {
                        // se non e' modificabile flaggo il campo come editable = false
                        field.editable = false;
                    } else if (isUndefined(editable)) {
                        // altrimenti verifico per il gruppo schema.entity.*
                        let schema: string = head(split(field.oggettoProtezione, '.'));
                        let entity: string = split(field.oggettoProtezione, '.')[1];
                        let schemaEntityKey: string = `${type}.${this.actionMod}.${schema}.${entity}.*`;
                        editable = this.checkKey(protectionMap, schemaEntityKey);
                        if (editable === false) {
                            // se non e' modificabile flaggo il campo come editable = false
                            field.editable = false;
                        } else if (isUndefined(editable)) {
                            // altrimenti verifico per il gruppo schema.*
                            let schemaKey: string = `${type}.${this.actionMod}.${schema}.*`;
                            editable = this.checkKey(protectionMap, schemaKey);
                            if (editable === false) {
                                // se non e' modificabile flaggo il campo come editable = false
                                field.editable = false;
                            } else if (isUndefined(editable)) {
                                // altrimenti verifico per il gruppo *
                                keyMod = `${type}.${this.actionMod}.*`;
                                editable = this.checkKey(protectionMap, keyMod);
                                if (editable === false) {
                                    // se non e' modificabile flaggo il campo come editable = false altrimenti la sezione non va modificata
                                    field.editable = false;
                                }
                            }
                        }
                    }
                }
            });

            // elaboro le sezioni non modificate
            each(fields, (field: SdkFormBuilderField, index: number) => {
                if (field.editable === false) {
                    delete field.editable;
                    if (field.type === 'FORM-SECTION') {
                        field = this.elaborateNotEditableSection(field);
                    } else if (field.type === 'FORM-GROUP') {
                        field = this.elaborateNotEditableGroup(field);
                    } else {
                        field = this.elaborateNotEditableOne(field);
                    }
                    fields[index] = field;
                }
            });
        }

        return fields;
    }

    private elaborateNotEditableOne(field: SdkFormBuilderField): SdkFormBuilderField {
        if (isObject(field)) {
            if (field.type === 'COMBOBOX') {
                field.disabled = true;
            } else if (field.type === 'DATEPICKER') {
                field.disabled = true;
            } else {
                field.type = 'TEXT';
            }
        }
        return field;
    }

    private elaborateNotEditableSection(field: SdkFormBuilderField): SdkFormBuilderField {
        each(field.fieldSections, (one: SdkFormBuilderField, index: number) => {
            if (one.type === 'FORM-SECTION') {
                one = this.elaborateNotEditableSection(one);
            } else if (one.type === 'FORM-GROUP') {
                one = this.elaborateNotEditableGroup(one);
            } else {
                one = this.elaborateNotEditableOne(one);
            }
            field.fieldSections[index] = one;
        });
        return field;
    }

    private elaborateNotEditableGroup(field: SdkFormBuilderField): SdkFormBuilderField {
        each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration, index: number) => {
            each(group.fields, (one: SdkFormBuilderField, index2: number) => {
                if (one.type === 'FORM-SECTION') {
                    one = this.elaborateNotEditableSection(one);
                } else if (one.type === 'FORM-GROUP') {
                    one = this.elaborateNotEditableGroup(one);
                } else {
                    one = this.elaborateNotEditableOne(one);
                }
                group.fields[index2] = one;
            });
            field.fieldGroups[index] = group;
        });
        return field;
    }

    /**
     * Metodo che verifica se una chiave e' presente tra le protezioni
     * @param protectionMap Mappa di protezioni
     * @param key Chiave
     * @returns Ritorna true se la chiave non e' stata trovata tra le protezioni altrimenti torna il valore del campo "valore" della protezione
     */
    private checkKey(protectionMap: IDictionary<ProfiloConfigurationItem>, key: string): boolean {
        if (has(protectionMap, key)) {
            let protValue: ProfiloConfigurationItem = get(protectionMap, key);
            return protValue.valore;
        }
        return undefined;
    }

    private elaborateOneProtection(fields: Array<SdkFormBuilderField>, protectionMap: IDictionary<ProfiloConfigurationItem>, checkEdit: boolean, checkMandatory: boolean): Array<SdkFormBuilderField> {
        each(fields, (field: SdkFormBuilderField) => {
            if (field.type !== 'FORM-SECTION' && field.type !== 'FORM-GROUP' && !isEmpty(field.oggettoProtezione)) {
                // costruisco la chiave relativa al campo per controllare la visibilita'
                let type: string = get(this.typeMap, 'COLONNE');
                let keyVis: string = `${type}.${this.actionVis}.${field.oggettoProtezione}`;
                let visible: boolean = this.checkKey(protectionMap, keyVis);
                if (visible === false) {
                    // se non e' visibile flaggo il campo come "da cancellare"
                    field.delete = true;
                } else if (isUndefined(visible)) {
                    // altrimenti verifico per il gruppo schema.entity.*
                    let schema: string = head(split(field.oggettoProtezione, '.'));
                    let entity: string = split(field.oggettoProtezione, '.')[1];
                    let schemaEntityKey: string = `${type}.${this.actionVis}.${schema}.${entity}.*`;
                    visible = this.checkKey(protectionMap, schemaEntityKey);
                    if (visible === false) {
                        // se non e' visibile flaggo il campo come "da cancellare"
                        field.delete = true;
                    } else if (isUndefined(visible)) {
                        // altrimenti verifico per il gruppo schema.*
                        let schemaKey: string = `${type}.${this.actionVis}.${schema}.*`;
                        visible = this.checkKey(protectionMap, schemaKey);
                        if (visible === false) {
                            // se non e' visibile flaggo il campo come "da cancellare"
                            field.delete = true;
                        } else if (isUndefined(visible)) {
                            // altrimenti verifico per il gruppo *
                            keyVis = `${type}.${this.actionVis}.*`;
                            visible = this.checkKey(protectionMap, keyVis);
                            if (visible === false) {
                                // se non e' visibile flaggo il campo come "da cancellare" altrimenti il campo va lasciato
                                field.delete = true;
                            }
                        }
                    }
                }

                if (field.type === 'TEXTBOX-MATRIX' && field.delete !== true) {
                    // controllo le singole righe della matrice
                    let rowsToDelete: Array<string> = new Array();

                    each(field.rows, (row: SdkTextboxMatrixRowConfig) => {
                        each(row.cells, (cell: SdkTextboxMatrixCellConfig) => {
                            if (!isEmpty(cell.oggettoProtezione)) {
                                let toBeDeleted: boolean = this.elaborateMatrixCellVisibility(cell, protectionMap);
                                if (toBeDeleted === true) {
                                    rowsToDelete.push(row.code);
                                    return false;
                                }
                            }
                        });
                    });

                    if (!isEmpty(rowsToDelete)) {
                        // rimuovo le righe che contengono celle nascoste
                        remove(field.rows, (row: SdkTextboxMatrixRowConfig) => includes(rowsToDelete, row.code) === true);
                    }
                }
            }
        });
        // rimuovo i campi non visibili
        remove(fields, (one: SdkFormBuilderField) => one.delete === true);

        if (checkEdit === true) {
            // per i campi rimasti controllo la modificabilita'
            each(fields, (field: SdkFormBuilderField) => {
                if (field.type !== 'FORM-SECTION' && field.type !== 'FORM-GROUP' && !isEmpty(field.oggettoProtezione)) {
                    // costruisco la chiave relativa al campo per controllare la modificabilita'
                    let type: string = get(this.typeMap, 'COLONNE');
                    let keyMod: string = `${type}.${this.actionMod}.${field.oggettoProtezione}`;
                    let editable: boolean = this.checkKey(protectionMap, keyMod);
                    if (editable === false) {
                        // se non e' modificabile flaggo il campo come editable = false
                        field.editable = false;
                    } else if (isUndefined(editable)) {
                        // altrimenti verifico per il gruppo schema.entity.*
                        let schema: string = head(split(field.oggettoProtezione, '.'));
                        let entity: string = split(field.oggettoProtezione, '.')[1];
                        let schemaEntityKey: string = `${type}.${this.actionMod}.${schema}.${entity}.*`;
                        editable = this.checkKey(protectionMap, schemaEntityKey);
                        if (editable === false) {
                            // se non e' modificabile flaggo il campo come editable = false
                            field.editable = false;
                        } else if (isUndefined(editable)) {
                            // altrimenti verifico per il gruppo schema.*
                            let schemaKey: string = `${type}.${this.actionMod}.${schema}.*`;
                            editable = this.checkKey(protectionMap, schemaKey);
                            if (editable === false) {
                                // se non e' modificabile flaggo il campo come editable = false
                                field.editable = false;
                            } else if (isUndefined(editable)) {
                                // altrimenti verifico per il gruppo *
                                keyMod = `${type}.${this.actionMod}.*`;
                                editable = this.checkKey(protectionMap, keyMod);
                                if (editable === false) {
                                    // se non e' modificabile flaggo il campo come editable = false altrimenti il campo non va modificato
                                    field.editable = false;
                                }
                            }
                        }
                    }
                }
            });

            // elaboro i campi non modificati
            each(fields, (field: SdkFormBuilderField, index: number) => {
                if (field.editable === false) {
                    delete field.editable;
                    if (field.type !== 'FORM-SECTION' && field.type !== 'FORM-GROUP') {
                        field = this.elaborateNotEditableOne(field);
                    }
                    fields[index] = field;
                }
            });

            fields = this.elaborateNotEditableMatrixCell(fields, protectionMap);
        }

        if (checkMandatory === true) {
            // per i campi rimasti controllo l'obbligatorieta'
            each(fields, (field: SdkFormBuilderField) => {
                if (field.type !== 'FORM-SECTION' && field.type !== 'FORM-GROUP' && !isEmpty(field.oggettoProtezione)) {
                    // costruisco la chiave relativa al campo per controllare la modificabilita'
                    let type: string = get(this.typeMap, 'COLONNE');
                    let keyMan: string = `${type}.${this.actionMan}.${field.oggettoProtezione}`;
                    let mandatory: boolean = this.checkKey(protectionMap, keyMan);
                    if (mandatory === true) {
                        // se e' obbligatorio flaggo il campo come mandatory = true
                        field.mandatory = true;
                    } else if (isUndefined(mandatory)) {
                        // altrimenti verifico per il gruppo schema.entity.*
                        let schema: string = head(split(field.oggettoProtezione, '.'));
                        let entity: string = split(field.oggettoProtezione, '.')[1];
                        let schemaEntityKey: string = `${type}.${this.actionMan}.${schema}.${entity}.*`;
                        mandatory = this.checkKey(protectionMap, schemaEntityKey);
                        if (mandatory === true) {
                            // se e' obbligatorio flaggo il campo come mandatory = true
                            field.mandatory = true;
                        } else if (isUndefined(mandatory)) {
                            // altrimenti verifico per il gruppo schema.*
                            let schemaKey: string = `${type}.${this.actionMan}.${schema}.*`;
                            mandatory = this.checkKey(protectionMap, schemaKey);
                            if (mandatory === true) {
                                // se e' obbligatorio flaggo il campo come mandatory = true
                                field.mandatory = true;
                            } else if (isUndefined(mandatory)) {
                                // altrimenti verifico per il gruppo *
                                keyMan = `${type}.${this.actionMan}.*`;
                                mandatory = this.checkKey(protectionMap, keyMan);
                                if (mandatory === true) {
                                    // se e' obbligatorio flaggo il campo come mandatory = true altrimenti il campo non va modificato
                                    field.mandatory = true;
                                }
                            }
                        }
                    }
                }
            });

            // elaboro i campi obbligatori
            each(fields, (field: SdkFormBuilderField, index: number) => {
                if (field.mandatory === true) {
                    delete field.mandatory;
                    if (field.type !== 'FORM-SECTION' && field.type !== 'FORM-GROUP') {
                        field = this.elaborateOneMandatory(field);
                    }
                    fields[index] = field;
                }
            });
        }

        return fields;
    }

    private elaborateMatrixCellVisibility(field: SdkTextboxMatrixCellConfig, protectionMap: IDictionary<ProfiloConfigurationItem>): boolean {
        // costruisco la chiave relativa al campo per controllare la visibilita'
        let type: string = get(this.typeMap, 'COLONNE');
        let keyVis: string = `${type}.${this.actionVis}.${field.oggettoProtezione}`;
        let visible: boolean = this.checkKey(protectionMap, keyVis);
        if (visible === false) {
            // se non e' visibile flaggo il campo come "da cancellare"
            return true;
        } else if (isUndefined(visible)) {
            // altrimenti verifico per il gruppo schema.entity.*
            let schema: string = head(split(field.oggettoProtezione, '.'));
            let entity: string = split(field.oggettoProtezione, '.')[1];
            let schemaEntityKey: string = `${type}.${this.actionVis}.${schema}.${entity}.*`;
            visible = this.checkKey(protectionMap, schemaEntityKey);
            if (visible === false) {
                // se non e' visibile flaggo il campo come "da cancellare"
                return true;
            } else if (isUndefined(visible)) {
                // altrimenti verifico per il gruppo schema.*
                let schemaKey: string = `${type}.${this.actionVis}.${schema}.*`;
                visible = this.checkKey(protectionMap, schemaKey);
                if (visible === false) {
                    // se non e' visibile flaggo il campo come "da cancellare"
                    return true;
                } else if (isUndefined(visible)) {
                    // altrimenti verifico per il gruppo *
                    keyVis = `${type}.${this.actionVis}.*`;
                    visible = this.checkKey(protectionMap, keyVis);
                    if (visible === false) {
                        // se non e' visibile flaggo il campo come "da cancellare" altrimenti il campo va lasciato
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private elaborateMatrixCellEditable(field: SdkTextboxMatrixCellConfig, protectionMap: IDictionary<ProfiloConfigurationItem>): boolean {
        // costruisco la chiave relativa al campo per controllare la modificabilita'
        let type: string = get(this.typeMap, 'COLONNE');
        let keyMod: string = `${type}.${this.actionMod}.${field.oggettoProtezione}`;
        let editable: boolean = this.checkKey(protectionMap, keyMod);
        if (editable === false) {
            // se non e' modificabile flaggo il campo come editable = false
            return false;
        } else if (isUndefined(editable)) {
            // altrimenti verifico per il gruppo schema.entity.*
            let schema: string = head(split(field.oggettoProtezione, '.'));
            let entity: string = split(field.oggettoProtezione, '.')[1];
            let schemaEntityKey: string = `${type}.${this.actionMod}.${schema}.${entity}.*`;
            editable = this.checkKey(protectionMap, schemaEntityKey);
            if (editable === false) {
                // se non e' modificabile flaggo il campo come editable = false
                return false;
            } else if (isUndefined(editable)) {
                // altrimenti verifico per il gruppo schema.*
                let schemaKey: string = `${type}.${this.actionMod}.${schema}.*`;
                editable = this.checkKey(protectionMap, schemaKey);
                if (editable === false) {
                    // se non e' modificabile flaggo il campo come editable = false
                    return false;
                } else if (isUndefined(editable)) {
                    // altrimenti verifico per il gruppo *
                    keyMod = `${type}.${this.actionMod}.*`;
                    editable = this.checkKey(protectionMap, keyMod);
                    if (editable === false) {
                        // se non e' modificabile flaggo il campo come editable = false altrimenti il campo non va modificato
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private elaborateNotEditableMatrixCell(fields: Array<SdkFormBuilderField>, protectionMap: IDictionary<ProfiloConfigurationItem>): Array<SdkFormBuilderField> {
        each(fields, (field: SdkFormBuilderField) => {
            if (field.type === 'TEXTBOX-MATRIX') {
                // controllo le singole righe della matrice
                each(field.rows, (row: SdkTextboxMatrixRowConfig) => {
                    each(row.cells, (cell: SdkTextboxMatrixCellConfig) => {
                        if (!isEmpty(cell.oggettoProtezione)) {
                            let tBeDisabled: boolean = this.elaborateMatrixCellEditable(cell, protectionMap);
                            if (tBeDisabled === false) {
                                cell.type = 'TEXT';
                            }
                        }
                    });
                });
            }
        });
        return fields;
    }

    private checkButtonProtection(button: SdkButtonGroupSingleInput, profiloConfiguration: ProfiloConfiguration, azione: string): boolean {
        if (button != null && !isEmpty(button.oggettoProtezione)) {
            let protectionMap: IDictionary<ProfiloConfigurationItem> = this.getProtezioniMap(profiloConfiguration);
            let type: string = get(this.typeMap, 'FUNZIONI');
            let keyVis: string = `${type}.${azione}.${button.oggettoProtezione}`;
            let visible: boolean = this.checkKey(protectionMap, keyVis);
            if (visible === true) {
                // se e' visibile mi fermo qua
                return true;
            } else if (visible === false) {
                // se non e' visibile flaggo il campo come "da cancellare"
                return false;
            } else {
                // altrimenti verifico per il gruppo schema.entity.*
                let schema: string = head(split(button.oggettoProtezione, '.'));
                let entity: string = split(button.oggettoProtezione, '.')[1];
                let schemaEntityKey: string = `${type}.${azione}.${schema}.${entity}.*`;
                visible = this.checkKey(protectionMap, schemaEntityKey);
                if (visible === true) {
                    // se e' visibile mi fermo qua
                    return true;
                } else if (visible === false) {
                    // se non e' visibile flaggo il campo come "da cancellare"
                    return false;
                } else {
                    // altrimenti verifico per il gruppo schema.*
                    let schemaKey: string = `${type}.${azione}.${schema}.*`;
                    visible = this.checkKey(protectionMap, schemaKey);
                    if (visible === true) {
                        // se e' visibile mi fermo qua
                        return true;
                    } else if (visible === false) {
                        // se non e' visibile flaggo il campo come "da cancellare"
                        return false;
                    } else {
                        // altrimenti verifico per il gruppo *
                        keyVis = `${type}.${azione}.*`;
                        visible = this.checkKey(protectionMap, keyVis);
                        if (visible === false) {
                            // se non e' visibile flaggo il campo come "da cancellare" altrimenti il campo va lasciato
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private elaborateSectionSolaLettura(field: SdkFormBuilderField, campiSolaLettura: Array<string>): SdkFormBuilderField {
        each(field.fieldSections, (one: SdkFormBuilderField, index: number) => {
            if (one.type === 'FORM-SECTION') {
                one = this.elaborateSectionSolaLettura(one, campiSolaLettura);
            } else if (one.type === 'FORM-GROUP') {
                one = this.elaborateGroupSolaLettura(one, campiSolaLettura);
            } else {
                one = this.elaborateOneSolaLettura(one, campiSolaLettura);
            }
            field.fieldSections[index] = one;
        });
        return field;
    }

    private elaborateGroupSolaLettura(field: SdkFormBuilderField, campiSolaLettura: Array<string>): SdkFormBuilderField {
        each(field.fieldGroups, (group: SdkFormFieldGroupConfiguration, index: number) => {
            each(group.fields, (one: SdkFormBuilderField, index2: number) => {
                if (one.type === 'FORM-SECTION') {
                    one = this.elaborateSectionSolaLettura(one, campiSolaLettura);
                } else if (one.type === 'FORM-GROUP') {
                    one = this.elaborateGroupSolaLettura(one, campiSolaLettura);
                } else {
                    one = this.elaborateOneSolaLettura(one, campiSolaLettura);
                }
                group.fields[index2] = one;
            });
            field.fieldGroups[index] = group;
        });
        return field;
    }

    private elaborateOneSolaLettura(field: SdkFormBuilderField, campiSolaLettura: Array<string>): SdkFormBuilderField {
        if (field != null && field.type != 'FORM-GROUP' && field.type != 'FORM-SECTION' && field.oggettoProtezione != null) {
            each(campiSolaLettura, (campo: string) => {
                if (field.oggettoProtezione.includes(campo)) {
                    field = this.elaborateNotEditableOneDaConfig(field);
                }
            });
        }
        return field;
    }

    private elaborateOneMandatory(field: SdkFormBuilderField): SdkFormBuilderField {
        if (field != null && field.type != 'FORM-GROUP' && field.type != 'FORM-SECTION' && field.type != 'TEXT') {
            if (field.validators == null) {
                field.validators = [];
            }

            let add: boolean = true;
            if (field.validators != null && field.validators.length > 0) {
                // cerco se esiste gia' un validatore di obbligatorieta' e non aggiungo quello da profilo se gia' presente
                const found: SdkValidator<any> = find(field.validators, (one: SdkValidator<any>) => {
                    return one.config != null && get(one.config, 'required') == true;
                });
                add = found == null;
            }

            if (add) {
                const validator: SdkValidator<any> = {
                    config: {
                        required: true
                    },
                    validator: null,
                    messages: [
                        {
                            level: 'error',
                            text: 'VALIDATORS.CAMPO-OBBLIGATORIO-PARAMS',
                            params: {
                                campo: this.translateService.instant(field.label)
                            }
                        }
                    ]
                };
                field.validators.push(validator);
                field = this.formBuilderUtilsService.elaborateFieldValidators(field);
            }
        }
        return field;
    }

    private elaborateNotEditableOneDaConfig(field: SdkFormBuilderField): SdkFormBuilderField {
        if (isObject(field)) {
            field.disabled = true;
            field.unlockable = true;
        }
        return field;
    }

    // #endregion

    // #region Getters

    private get formBuilderUtilsService(): FormBuilderUtilsService { return this.injectable(FormBuilderUtilsService) }

    private get translateService(): TranslateService { return this.injectable(TranslateService) }

    // #endregion
}