package it.appaltiecontratti.monitoraggiocontratti.contratti.factory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ChiaveGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseCollaudoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.IncarichiProfEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ValidateEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.utils.CostantiW9;

public class FaseCollaudoFactory extends AbstractFactory {

	private static final Long FASE_COLLAUDO = new Long(Integer.valueOf(CostantiW9.COLLAUDO_CONTRATTO).longValue());

	@Override
	public List<ValidateEntry> valida(Long codGara, Long codLotto, Long codFase, Long num, ContrattiMapper contrattiMapper, SqlMapper sqlMapper)
			throws Exception {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		FaseCollaudoEntry fase = contrattiMapper.getFaseCollaudo(codGara, codLotto, num);
		ChiaveGaraEntry chiaveGara = new ChiaveGaraEntry(codGara,codLotto);
		LottoEntry lotto = sqlMapper.getDettaglioLotto(codGara, codLotto);
//		if (super.esisteFase(sqlMapper, chiaveGara, FASE_COLLAUDO, fase.getNum())) {
//			if (tipoInvio == 1L) {
//				ValidateEntry entry = new ValidateEntry("Tipo invio", "La fase è già stata inviata", "W");
//				risultato.add(entry);
//			}
//		} else {
//			if (tipoInvio == 2L) {
//				ValidateEntry entry = new ValidateEntry("Tipo invio", "La fase non è mai stata inviata in precedenza",
//						"W");
//				risultato.add(entry);
//			}
//		}

		if(fase.getTipoCollaudo() == null) {
			ValidateEntry entry = new ValidateEntry("Tipologia di collaudo",
					"Specificare un valore",
					"E");
			risultato.add(entry);
		}
		
		if (fase.getDataCertEsecuzione() == null && fase.getTipoCollaudo() == 2L) {
			ValidateEntry entry = new ValidateEntry("Data del certificato di regolare esecuzione",
					"Specificare la data del certificato di regolare esecuzione",
					"E");
			risultato.add(entry);
		}
		
		Date dataUltimazione = null;
		if(chiaveGara != null) {
			dataUltimazione = sqlMapper.getDataUltimazione(chiaveGara.getCodGara(), chiaveGara.getCodLotto(),
					fase.getNumAppa());
		}
		
		if (fase.getDataCertEsecuzione() != null && dataUltimazione != null
				&& fase.getDataCertEsecuzione().before(dataUltimazione)) {
			ValidateEntry entry = new ValidateEntry("Data del certificato di regolare esecuzione",
					"Deve essere successiva alla data di ultimazione", "E");
			risultato.add(entry);
		}
		Date dataVerbInizio = null;
		if(chiaveGara != null) {
			dataVerbInizio = sqlMapper.getDataVerbInizio(chiaveGara.getCodGara(), chiaveGara.getCodLotto(),
				fase.getNumAppa());
		}
		if (fase.getDataCollaudoStatico() != null && dataVerbInizio != null
				&& fase.getDataCollaudoStatico().before(dataVerbInizio)) {
			ValidateEntry entry = new ValidateEntry("Data del collaudo statico",
					"Deve essere successiva alla data del verbale d'inizio", "E");
			risultato.add(entry);
		}
		
		if (fase.getModalitaCollaudo() == null  && fase.getTipoCollaudo() == 1L) {
			ValidateEntry entry = new ValidateEntry("Modalità del collaudo tecnico amministrativo",
					"Specificare le modalità del collaudo tecnico-amministrativo",
					"E");
			risultato.add(entry);
		}
		
		if (fase.getDataNomina() == null && fase.getTipoCollaudo()==1L) {
			ValidateEntry entry = new ValidateEntry("Data nomina collaudatore/commissione", "Valorizzare il campo",
					"E");
			risultato.add(entry);
		}
		
		if (fase.getDataInizioOperazioni() == null && fase.getTipoCollaudo() != null && fase.getTipoCollaudo() == 1L) {
			ValidateEntry entry = new ValidateEntry("Data inizio operazioni di collaudo", "Valorizzare il campo", "E");
			risultato.add(entry);
		}
		
		if (fase.getDataInizioOperazioni() != null && fase.getDataNomina() != null
				&& fase.getDataNomina().after(fase.getDataInizioOperazioni())) {
			ValidateEntry entry = new ValidateEntry("Data inizio operazioni di collaudo",
					"Deve essere successiva alla data di nomina", "E");
			risultato.add(entry);
		}
		
		if (fase.getDataRedazioneCertificato() == null && fase.getTipoCollaudo() != null && fase.getTipoCollaudo() == 1L) {
			ValidateEntry entry = new ValidateEntry("Data redazione certificato di collaudo", "Valorizzare il campo",
					"E");
			risultato.add(entry);
		}
		
		if (fase.getDataInizioOperazioni() != null && fase.getDataRedazioneCertificato() != null
				&& fase.getDataRedazioneCertificato().before(fase.getDataInizioOperazioni())) {
			ValidateEntry entry = new ValidateEntry("Data redazione certificato di collaudo",
					"Deve essere successiva alla data di inizio operazioni", "E");
			risultato.add(entry);
		}
		
		if (fase.getDataDelibera() != null && fase.getDataRedazioneCertificato() != null
				&& fase.getDataDelibera().before(fase.getDataRedazioneCertificato())) {
			ValidateEntry entry = new ValidateEntry("Data delibera di ammissibilità del collaudo",
					"Deve essere successiva alla data di redazione certificato", "E");
			risultato.add(entry);
		}
		
		if (fase.getEsitoCollaudo() == null || "".equals(fase.getEsitoCollaudo())) {
			ValidateEntry entry = new ValidateEntry("Esito del collaudo", "Valorizzare il campo", "E");
			risultato.add(entry);
		}
		
		if ("L".equals(lotto.getTipologia())
				&& (fase.getImportoFinaleLavori() == null || fase.getImportoFinaleLavori() <= 0d)) {
			ValidateEntry entry = new ValidateEntry("Importo componente lavori al netto della sicurezza",
					"Valorizzare il campo con valore maggiore di 0", "E");
			risultato.add(entry);
		}
		
		if ("S".equals(lotto.getTipologia())
				&& (fase.getImportoFinaleServizi() == null || fase.getImportoFinaleServizi() <= 0d)) {
			ValidateEntry entry = new ValidateEntry("Importo componente servizi al netto della sicurezza",
					"Valorizzare il campo con valore maggiore di 0", "E");
			risultato.add(entry);
		}

		if ("F".equals(lotto.getTipologia())
				&& (fase.getImportoFinaleForniture() == null || fase.getImportoFinaleForniture() <= 0d)) {
			ValidateEntry entry = new ValidateEntry("Importo componente forniture al netto della sicurezza",
					"Valorizzare il campo con valore maggiore di 0", "E");
			risultato.add(entry);
		}
		
		if (fase.getImportoFinaleSicurezza() != null && fase.getImportoSubtotale() != null
				&& fase.getImportoFinaleSicurezza() >= fase.getImportoSubtotale()) {
			ValidateEntry entry = new ValidateEntry("Importo totale per l'attuazione della sicurezza",
					"Dev'essere inferiore all'importo subtotale", "W");
			risultato.add(entry);
		}

		if (fase.getImportoProgettazione() != null && fase.getImportoSubtotale() != null
				&& fase.getImportoProgettazione() >= fase.getImportoSubtotale()) {
			ValidateEntry entry = new ValidateEntry("Importo progettazione",
					"Dev'essere inferiore all'importo subtotale", "W");
			risultato.add(entry);
		}
		
		if (fase.getImportoDisposizione() == null) {
			ValidateEntry entry = new ValidateEntry("Importo totale somme a disposizione", "Valorizzare il campo",
					"E");
			risultato.add(entry);
		}		
		
		if (fase.getImportoSubtotale() == null) {
			ValidateEntry entry = new ValidateEntry("Importo al netto della sicurezza", "Valorizzare il campo", "E");
			risultato.add(entry);
		}
		BigDecimal impDisposizione = new BigDecimal(super.convertiImporto(fase.getImportoDisposizione()));
		BigDecimal impFinaleLavori = new BigDecimal(super.convertiImporto(fase.getImportoFinaleLavori()));
		BigDecimal impFinaleServizi = new BigDecimal(super.convertiImporto(fase.getImportoFinaleServizi()));
		BigDecimal impFinaleForniture = new BigDecimal(super.convertiImporto(fase.getImportoFinaleForniture()));

		BigDecimal impSubtotale = new BigDecimal(super.convertiImporto(fase.getImportoSubtotale()));
		BigDecimal impProgAppalto = new BigDecimal(super.convertiImporto(fase.getImportoComplessivoAppalto()));

		BigDecimal impSicurezza = new BigDecimal(super.convertiImporto(fase.getImportoFinaleSicurezza()));
		BigDecimal impProgettazione = new BigDecimal(super.convertiImporto(fase.getImportoProgettazione()));

		BigDecimal impComplIntervento = new BigDecimal(super.convertiImporto(fase.getImportoComplessivoIntervento()));

		if (!impSubtotale.equals(super.sommaImporti(impFinaleLavori, impFinaleServizi, impFinaleForniture))) {
			ValidateEntry entry = new ValidateEntry("Importo al netto della sicurezza",
					"Deve essere uguale alla somma di importo finale lavori + importo finale servizi + importo finale forniture",
					"E");
			risultato.add(entry);
		}

		if (!impComplIntervento.equals(super.sommaImporti(impProgAppalto, impDisposizione))) {
			ValidateEntry entry = new ValidateEntry("Importo complessivo intervento",
					"Deve essere uguale alla somma di importo somme a disposizione + importo finale complessivo dell'appalto",
					"E");
			risultato.add(entry);
		}

		if(fase.getNumRiserve() != null && fase.getNumRiserve() > 0 &&
				(fase.getOneriDerivanti() == null || (fase.getOneriDerivanti() != null && fase.getOneriDerivanti() <= 0))){
			ValidateEntry entry = new ValidateEntry("Oneri complessivi derivanti",
					"Valorizzare il campo",
					"E");
			risultato.add(entry);
		}

		return risultato;

	}

	
}
