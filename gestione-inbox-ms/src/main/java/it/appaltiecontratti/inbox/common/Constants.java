package it.appaltiecontratti.inbox.common;

import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String ERRORE_PRECEDENZA_CANCELLAZIONE_FASE = "ERRORE_PRECEDENZA_CANCELLAZIONE_FASE";

    public static final Map<Long, String> TipiSchede = new HashMap<Long, String>() {
        private static final long serialVersionUID = -5688382098282527126L;

        {
            put(Long.valueOf(1), "AGGIUDICAZIONE");
            put(Long.valueOf(987), "SOTTOSOGLIA");
            put(Long.valueOf(12), "ADESIONE");
            put(Long.valueOf(2), "FASE_INIZIALE");
            put(Long.valueOf(11), "STIPULA");
            put(Long.valueOf(3), "STATO_AVANZAMENTO");
            put(Long.valueOf(4), "FINE_LAVORI");
            put(Long.valueOf(5), "COLLAUDO");
            put(Long.valueOf(8), "ACCORDO_BONARIO");
            put(Long.valueOf(6), "SOSPENSIONE");
            put(Long.valueOf(7), "VARIANTE");
            put(Long.valueOf(9), "SUBAPPALTO");
            put(Long.valueOf(10), "IPOTESI_RECESSO");
        }
    };

	public static final Map<Long, String> TipiSchedeAggiudicazioneOEquivalente = new HashMap<Long, String>() {

		{
			put(Long.valueOf(1), "AGGIUDICAZIONE");
			put(Long.valueOf(987), "SOTTOSOGLIA");
			put(Long.valueOf(12), "ADESIONE");
		}
	};

    public static final String TIPO_SCHEDA_DATI_COMUNI = "DATI_COMUNI";

    /**
     * Stato della cancellazione di una scheda di aggiudicazione che ha ricevuto
     * errori nella cancellazione dei dati comuni
     */
    public static final String STATO_IMPORTATA_ERRORE_CANCELLAZIONE_DATI_COMUNI = "STATO_IMPORTATA_ERRORE_CANCELLAZIONE_DATI_COMUNI";
}
