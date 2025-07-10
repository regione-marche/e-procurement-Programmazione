import { SdkComboBoxItem } from '@maggioli/sdk-controls';
import { Subject } from 'rxjs';

// #region Dialog

/**
 * Interfaccia di configurazione di un dialog
 */
export interface SdkDialogConfig {
    /**
     * Titolo del dialog, se omesso non verra' renderizzato il pulsante di chiusura
     */
    header?: string;
    /**
     * Contenuto del dialog
     */
    message?: string;
    /**
     * Eventuale contenuto del dialog
     */
    secondMessage?: string;
    /**
     * Eventuale contenuto del dialog
     */
    thirdMessage?: string;
    /**
     * Eventuale contenuto del dialog
     */
    fourthMessage?: string;
    /**
     * Subject per l'apertura del dialog, presenta una callback che verra' chiamata se il dialog verra' confermato
     */
    open: Subject<Function>;
    /**
     * Label tradotta per il pulsante di conferma
     */
    acceptLabel?: string;
    /**
     * Label tradotta per il pulsante di rifiuto
     */
    rejectLabel?: string;
    /**
     * Identificativo della dialog nel caso ce ne siano più di una.
     */
    dialogId?: string;
    /**
     * Label tradotta per il campo di motivazione
     */
    motivazioneLabel?: string;
    /**
     * Label tradotta per il campo di data nel datepicker-dialog.
     */
    dataDialogLabel?: string;
    /**
     * Label tradotta per campo aggiuntivo. Utilizzabile come stringa jolly per il dialog senza conferma.
     */
    multiPurposeString? : string;
    /**
     * Scelta combobox nr. 1 
     */
    firstChoice?: SdkComboBoxItem;
    /**
     * Scelta combox nr. 2
     */
    secondChoice?: SdkComboBoxItem;
}

// #endregion