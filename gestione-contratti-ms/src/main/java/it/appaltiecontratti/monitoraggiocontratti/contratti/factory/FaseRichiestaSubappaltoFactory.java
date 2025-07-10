package it.appaltiecontratti.monitoraggiocontratti.contratti.factory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ChiaveGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseSubappaltoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.ValidateEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.utils.CostantiW9;

public class FaseRichiestaSubappaltoFactory extends AbstractFactory {

	private static final Long FASE_SUBAPPALTO = new Long(Integer.valueOf(CostantiW9.SUBAPPALTO).longValue());

	@Override
	public List<ValidateEntry> valida(Long codGara, Long codLotto, Long codFase, Long num, ContrattiMapper contrattiMapper, SqlMapper sqlMapper)
			throws Exception {

		List<ValidateEntry> risultato = new ArrayList<ValidateEntry>();
		FaseSubappaltoEntry fase = contrattiMapper.getFaseSubappalto(codGara, codLotto, num);
		ChiaveGaraEntry chiaveGara = new ChiaveGaraEntry(codGara,codLotto);
		LottoEntry lotto = sqlMapper.getDettaglioLotto(codGara, codLotto);
		if (chiaveGara != null ) {

//			if (super.esisteFase(sqlMapper, chiaveGara, FASE_SUBAPPALTO, fase.getNum())) {
//				if (tipoInvio == 1L) {
//					ValidateEntry entry = new ValidateEntry("Tipo invio", "La fase è già stata inviata", "W");
//					risultato.add(entry);
//				}
//			} else {
//				if (tipoInvio == 2L) {
//					ValidateEntry entry = new ValidateEntry("Tipo invio", "La fase non è mai stata inviata in precedenza",
//							"W");
//					risultato.add(entry);
//				}
//			}
		}

		Date dataMinVerbAggiudicazione = getDataVerbAggiudicazione(chiaveGara.getCodGara(), chiaveGara.getCodLotto(),
				fase.getNumAppa(), lotto, sqlMapper, true);

		BigDecimal importoPresunto = new BigDecimal(super.convertiImporto(fase.getImportoPresunto()));
		if (fase.getImportoPresunto() == null
				|| importoPresunto.compareTo(new BigDecimal(super.convertiImporto(1d))) == -1) {
			ValidateEntry entry = new ValidateEntry("Importo presunto", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getIdCategoria() == null || "".equals(fase.getIdCategoria())) {
			ValidateEntry entry = new ValidateEntry("Categoria", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getIdCpv() == null) {
			ValidateEntry entry = new ValidateEntry("Codice CPV", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		if (fase.getCodImpresa() == null) {
			ValidateEntry entry = new ValidateEntry("Impresa subappaltatrice", "Inserire almeno un impresa", "E");
			risultato.add(entry);
		}
		
		if (StringUtils.isBlank(fase.getDgue())) {
			ValidateEntry entry = new ValidateEntry("File DGUE", "Valorizzare il campo", "E");
			risultato.add(entry);
		}

		return risultato;

	}

	
	
}
