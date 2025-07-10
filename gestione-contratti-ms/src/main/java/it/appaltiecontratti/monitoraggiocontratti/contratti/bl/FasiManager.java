package it.appaltiecontratti.monitoraggiocontratti.contratti.bl;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.MandanteEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.ResponseResult;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CPVSecondario;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.LottoDynamicValue;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.RuoloImpresa;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.W9AssociazioneCampi;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AssociazionePagamentiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AttiPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DocumentoFaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ExistingDocumentoFaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAccordoBonarioEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAdesioneAccordoQuadroEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAggiudicazioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAggiudicazioneSempEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAvanzamentoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAvanzamentoSempEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseCantieriEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseCantieriImpEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseCollaudoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseComunicazioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseConclusioneAffidamentiDirettiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseConclusioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseConclusioneSempEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseImpresaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInadempienzeSicurezzaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInfortuniEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInidoneitaContributivaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInidoneitaTecnicoProfEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInizialeEsecuzioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseInizialeSemplificataEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseIstanzaRecessoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseSospensioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseStipulaAccordoQuadroEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseSubappaltoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseVarianteEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FasiInfo;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FinanziamentiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FullDettaglioAttoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraFullEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaAggiudicatariaAttoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaAggiudicatariaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.IncarichiProfEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseAdesioneAccordoQuadro;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseAggiudicazione;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseAggiudicazioneSemp;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseCantieriEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseConclusioneContrattoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseInizialeContrattoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseRipresaPrestazioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.InizFaseSubappaltoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LegaleRappresentanteEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.MisureAggiuntiveSicurezzaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.PagamentoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.RecordFaseImpreseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.SchedaSicurezzaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.TabellatoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.TabellatoFaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.TabellatoIstatEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.DatiIdSchedeW9FasiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.DocumentoFasePubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseAccordoBonarioPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseAdesioneAccordoQuadroPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseAggiudicazioneSempPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseAvanzamentoPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseAvanzamentoSempPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseCantieriPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseCollaudoPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseComPubbEsitoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseConclusionePubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseConclusioneSemplificataPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseImpresaPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseInadempienzeSicurezzaPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseInfortuniPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseInidoneitaContributivaPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseInidoneitaTecnicoProfPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseInizPubbEsitoPubbForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseInizialePubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseInizialeSemplificataPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseIstanzaRecessoPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseSospensionePubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseStipulaAccordoQuadroPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseSubappaltoPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FaseVariantePubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FinanziamentiPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.ImpresaAggiudicatariaPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.ImpresaFasePubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.IncarichiProfPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.ListaFaseImpresePubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.MisureAggiuntiveSicurezzaPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.SchedaSicurezzaPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.exceptions.ImportImpresaAggiudicatariaException;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.Constants;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.AggiudicatariType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.AggiudicatarioType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.AppaltoType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SchedaType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SoggAggiudicatarioType;
import it.appaltiecontratti.monitoraggiocontratti.simog.bl.AnacRestClientManager;
import it.appaltiecontratti.monitoraggiocontratti.simog.responses.ResponseConsultaGaraIdGara;
import it.appaltiecontratti.monitoraggiocontratti.simog.utils.CostantiW9;

@Component(value = "fasiManager")
public class FasiManager extends AbstractManager {

	@Autowired
	private SqlMapper sqlMapper; 

	@Autowired
	private AnacRestClientManager anacrestclientmanager;

	/** Logger di classe. */
	private static Logger logger = LogManager.getLogger(ContrattiManager.class);

	public ResponseFasiLotto getFasiLotto(Long codGara, Long codLotto, Long numAppa) {

		ResponseFasiLotto risultato = new ResponseFasiLotto();
		try {
			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
			
			if(gara.getVersioneSimog() != null && gara.getVersioneSimog() == 9L) {
				gara.setPcp(true);
			}
			
			Long maxNumAppa = this.contrattiMapper.getMaxNumAppa(codGara, codLotto);

			if (numAppa == null) {
				numAppa = maxNumAppa == null ? 1L : maxNumAppa;
			}

			// Trovo la lista delle fasi associate al lotto
			List<FaseEntry> fasi = new ArrayList<FaseEntry>();
			
			fasi = this.contrattiMapper.getFasiLottoNuova(codGara, codLotto, numAppa);
						
			List<Long> fasiPresentiw9Gara = new ArrayList<Long>();
			List<Long> fasiPresentiw9Flussi = new ArrayList<Long>();
			HashMap<Long, List<Long>> mappaFasi = new HashMap<>();
			// Trovo la lista delle fasi inviate
			fasiPresentiw9Flussi = this.contrattiMapper.getFasiInviate(codGara, codLotto, numAppa);

			// Popolo la lista dei codici delle fasi presenti nella W9GAra
			for (FaseEntry f : fasi) {
				fasiPresentiw9Gara.add(f.getFase());
				
				if (!mappaFasi.containsKey(f.getFase())) {
				    List<Long> list = new ArrayList<Long>();
				    list.add(f.getProgressivo());

				    mappaFasi.put(f.getFase(), list);
				} else {
					mappaFasi.get(f.getFase()).add(f.getProgressivo());
				}
			}
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, codLotto);
			boolean riaggiudicabile = true;
			// Calcolo la possibilità di cancellazione fisica e logica delle fasi associate
			// al lotto

			for (FaseEntry f : fasi) {

				boolean pubblicata = f.getNumFlussi() != null && f.getNumFlussi() > 0;
				f.setPubblicata(pubblicata);
				if (pubblicata == false) {
					riaggiudicabile = false;
				}

				if (f.getNumRichCanc() != null && f.getNumRichCanc() > 0) {
					f.setDeleteFisica(false);
					f.setDeleteLogica(false);
					f.setPubblicabile(false);
				} else {
					
					f.setDeleteFisica(checkCancellazioneFisicaFase(lotto, f, fasiPresentiw9Gara, f.getNumFlussi()));
					f.setDeleteLogica(checkCancellazioneLogicaFase(lotto, f, fasiPresentiw9Flussi, f.getNumFlussi()));
					if (f.isPubblicata()) {
						f.setPubblicabile(true);
					} else {
						f.setPubblicabile(checkFasePubblicabile(f, fasiPresentiw9Flussi, fasiPresentiw9Gara, lotto,gara.isPcp()));
					}
				}
				
				if(gara.isPcp() ) {
					if(StringUtils.isNotBlank(f.getDaExportDb())) {
						f.setDaExportNum(Long.valueOf(f.getDaExportDb()));
					}
					
					
					
					f.setRecordTinvio2Uguale1(this.contrattiMapper.getFlussiTinvio2equals1(codGara, codLotto, f.getFase(), f.getProgressivo()));
					f.setRecordTinvio2Uguale3(this.contrattiMapper.getFlussiTinvio2equals3(codGara, codLotto, f.getFase(), f.getProgressivo()));
					
					if(f.isDeleteLogica() == true){
						if(f.getDaExportNum() != 1L || f.getRecordTinvio2Uguale1() == 0 || f.getRecordTinvio2Uguale3() != 0) {
							f.setDeleteLogica(false);
						}
					}
					
					if(f.isDeleteFisica() == true){
						if(f.isPubblicata()) {
							f.setDeleteFisica(false);
						}
					}
					
					if(f.getFase() == 1L || f.getFase() == 101L) {
						f.setDeleteFisica(false);
						f.setDeleteLogica(false);
						f.setPubblicabile(false);
					}
					
					
					if(f.getFase() == 6L && ((fasiPresentiw9Gara.contains(14L) && mappaFasi.get(14L).contains(f.getProgressivo())) || (fasiPresentiw9Gara.contains(15L) && mappaFasi.get(15L).contains(f.getProgressivo())))) {
						f.setDeleteFisica(false);
						f.setDeleteLogica(false);
					}
					
					if(f.getFase() == 14L && fasiPresentiw9Gara.contains(15L) && mappaFasi.get(15L).contains(f.getProgressivo())) {
						f.setDeleteFisica(false);
						f.setDeleteLogica(false);
					}
					
					if(f.getFase() == 16L && ((fasiPresentiw9Gara.contains(17L) && mappaFasi.get(17L).contains(f.getProgressivo())) || (fasiPresentiw9Gara.contains(18L) && mappaFasi.get(18L).contains(f.getProgressivo())))) {
						f.setDeleteFisica(false);
						f.setDeleteLogica(false);
					}
					
					if(f.getFase() == 17L && fasiPresentiw9Gara.contains(18L) && mappaFasi.get(18L).contains(f.getProgressivo())) {
						f.setDeleteFisica(false);
						f.setDeleteLogica(false);
					}
					
					
				}

			}
			Long interruzioneAnticipata = this.contrattiMapper.getInterruzioneAnticipata(codGara, codLotto, numAppa);
			riaggiudicabile = riaggiudicabile
					&& (interruzioneAnticipata == null ? false : interruzioneAnticipata == 1L) && !gara.isPcp();
			List<Long> storicoFasi = new ArrayList<>();			
			storicoFasi = this.contrattiMapper.getStoricoNumAppa(codGara, codLotto);			
			if(storicoFasi.isEmpty()) {
				storicoFasi = new ArrayList<>();		
				storicoFasi.add(1L);
			}
			

			FasiInfo infoFasi = new FasiInfo();
			infoFasi.setListaFasi(fasi);
			infoFasi.setListaStoricoFasi(storicoFasi);
			infoFasi.setRiaggiudicabile(riaggiudicabile);
			infoFasi.setCurrent(maxNumAppa == null || numAppa == maxNumAppa);
			risultato.setData(infoFasi);
			risultato.setEsito(true);

		} catch (Throwable t) {
			logger.error("Errore inaspettato durante l'estrazione delle fasi del lotto - codgara = " + codGara
					+ " codLotto=" + codLotto, t);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
	
	private boolean isFasePubblicata(Long codGara, Long codLotto, Long numFase, Long progressivoFase) {
		List<String> pubblicazioni = this.contrattiMapper.listaChiaviPubblicazioniFase(codGara, codLotto, numFase, progressivoFase);
		return pubblicazioni == null ? false : pubblicazioni.size() > 0;
	}

	private boolean checkCancellazioneFisicaFase(LottoEntry lotto, FaseEntry fase, List<Long> fasiPresenti, Long numflussiFase) {


	
		if (numflussiFase != 0L) {
			return false;
		}

		// Condizioni di cancellazione. Una fase non può essere cancellata se è presente
		// per il lotto un'altra fase che ne dipenda
		switch (fase.getFase().intValue()) {
		case 101:
		case 984:
		case 3:
		case 102:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 997:
		case 995:
		case 994:
		case 998:
		case 5:
			return true;
		case 1:
			return !fasiPresenti.contains(2L) && !fasiPresenti.contains(986L) && !fasiPresenti.contains(11L)
					&& !fasiPresenti.contains(4L) && !fasiPresenti.contains(985L) && !fasiPresenti.contains(7L)
					&& !fasiPresenti.contains(9L) && !fasiPresenti.contains(997L) && !fasiPresenti.contains(10L)
					&& !fasiPresenti.contains(995L) && !fasiPresenti.contains(998L);

		case 987:
			return !fasiPresenti.contains(986L) && !fasiPresenti.contains(985L) && !fasiPresenti.contains(11L)
					&& !fasiPresenti.contains(7L) && !fasiPresenti.contains(9L) && !fasiPresenti.contains(995L)
					&& !fasiPresenti.contains(997L);

		case 12:
			return !fasiPresenti.contains(2L) && !fasiPresenti.contains(986L) && !fasiPresenti.contains(4L)
					&& !fasiPresenti.contains(985L) && !fasiPresenti.contains(7L) && !fasiPresenti.contains(9L)
					&& !fasiPresenti.contains(998L) && !fasiPresenti.contains(10L) && !fasiPresenti.contains(995L)
					&& !fasiPresenti.contains(997L);

		case 996:
			return true;

		case 986:
			return !fasiPresenti.contains(102L) && !fasiPresenti.contains(6L) && !fasiPresenti.contains(3L)
					&& !fasiPresenti.contains(8L) && !fasiPresenti.contains(994L);
		case 13:
			return !fasiPresenti.contains(2L) && !fasiPresenti.contains(10L) && !fasiPresenti.contains(7L)
					 && !fasiPresenti.contains(9L);
		case 2:
			return !fasiPresenti.contains(102L) && !fasiPresenti.contains(6L) && !fasiPresenti.contains(3L)
					&& !fasiPresenti.contains(8L) && !fasiPresenti.contains(994L)
					&& !fasiPresenti.contains(4L);

		case 11:
			return !fasiPresenti.contains(102L) && !fasiPresenti.contains(6L) && !fasiPresenti.contains(3L)
					&& !fasiPresenti.contains(8L);

		case 4:
			return !fasiPresenti.contains(5L);
		case 985:
			return !fasiPresenti.contains(6L);
		}

		return true;
	}

	private boolean checkCancellazioneLogicaFase(LottoEntry lotto, FaseEntry fase, List<Long> fasiPresenti, Long numflussiFase) {
		// Già presente record in flussi
		
		
		if (numflussiFase == 0L) {
			return false;
		}
		// Condizioni di cancellazione. Una fase non può essere cancellata se è presente
		// per il lotto un'altra fase che ne dipenda
		switch (fase.getFase().intValue()) {
		case 101:
		case 984:
		case 3:
		case 102:
		case 6:
		case 7:
		case 8:
		case 9:
		case 10:
		case 997:
		case 995:
		case 994:
		case 998:
		case 5:
			return true;
		case 1:
			return !fasiPresenti.contains(2L) && !fasiPresenti.contains(986L) && !fasiPresenti.contains(11L)
					&& !fasiPresenti.contains(4L) && !fasiPresenti.contains(985L) && !fasiPresenti.contains(7L)
					&& !fasiPresenti.contains(9L) && !fasiPresenti.contains(997L) && !fasiPresenti.contains(10L)
					&& !fasiPresenti.contains(995L) && !fasiPresenti.contains(998L);

		case 987:
			return !fasiPresenti.contains(986L) && !fasiPresenti.contains(985L) && !fasiPresenti.contains(11L)
					&& !fasiPresenti.contains(7L) && !fasiPresenti.contains(9L) && !fasiPresenti.contains(995L)
					&& !fasiPresenti.contains(997L);

		case 12:
			return !fasiPresenti.contains(2L) && !fasiPresenti.contains(986L) && !fasiPresenti.contains(4L)
					&& !fasiPresenti.contains(985L) && !fasiPresenti.contains(7L) && !fasiPresenti.contains(9L)
					&& !fasiPresenti.contains(998L) && !fasiPresenti.contains(10L) && !fasiPresenti.contains(995L)
					&& !fasiPresenti.contains(997L);

		case 996:
			return true;

		case 986:
			return !fasiPresenti.contains(102L) && !fasiPresenti.contains(6L) && !fasiPresenti.contains(3L)
					&& !fasiPresenti.contains(8L) && !fasiPresenti.contains(994L);
		case 13:
			return !fasiPresenti.contains(2L) && !fasiPresenti.contains(10L) && !fasiPresenti.contains(7L)
					 && !fasiPresenti.contains(9L);
		case 2:
			return !fasiPresenti.contains(102L) && !fasiPresenti.contains(6L) && !fasiPresenti.contains(3L)
					&& !fasiPresenti.contains(8L) && !fasiPresenti.contains(994L)
					&& !fasiPresenti.contains(4L);

		case 11:
			return !fasiPresenti.contains(102L) && !fasiPresenti.contains(6L) && !fasiPresenti.contains(3L)
					&& !fasiPresenti.contains(8L);

		case 4:
			return !fasiPresenti.contains(5L);
		case 985:
			return !fasiPresenti.contains(6L);
		}

		return true;
	}

	public ResponseFasiAbilitate listaFasiAbilitate(Long codGara, Long numLotto) {

		ResponseFasiAbilitate risultato = new ResponseFasiAbilitate();
		risultato.setEsito(true);
		try {
			Long numAppa = getNumAppa(codGara, numLotto, false);
			// Fasi presenti nel lotto
			List<FaseEntry> fasiLotto = this.contrattiMapper.getFasiLotto(codGara, numLotto, numAppa);
			// Lista di tutte le fasi associabili al lotto
			List<TabellatoFaseEntry> allFasi = this.contrattiMapper.getAllFasiLotto();
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, numLotto);
			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
			
			boolean pcp = gara.getVersioneSimog() != null && gara.getVersioneSimog() == 9L;
			
			List<Long> esitoProceduraList = this.contrattiMapper.getEsitoProcedura(codGara, numLotto);
			Long esitoProcedura = null;
			if(esitoProceduraList != null && esitoProceduraList.size() > 0) {
				esitoProcedura = esitoProceduraList.get(0);
			}
			List<TabellatoFaseEntry> fasiVisibili = new ArrayList<TabellatoFaseEntry>();
			// Fase condizione fondamentale per tutte le altre fasi. Se non esiste non
			// possono essere inserite fasi.

			for (TabellatoFaseEntry fase : allFasi) {
				// Controllo se la fase è visibile per il lotto. In tal caso eseguo il
				// successivo controllo se sia abilitata.
				if (checkFaseVisibility(fase, lotto, gara, esitoProcedura, pcp)) {
					fase.setAbilitato(checkFaseAbilitata(fase, fasiLotto, pcp,codGara,numLotto));
					fasiVisibili.add(fase);
				}			
			}
			Long idFDelegate = gara.getIdFDelegate();
			String cfStazioneAppaltante = this.contrattiMapper.getCFSAByCode(gara.getCodiceStazioneAppaltante());
			String cfSaAgente = gara.getCfAgenteStazioneAppaltante();

			if (idFDelegate != null && idFDelegate != 3L
					&& (StringUtils.isNotBlank(cfSaAgente) && !cfStazioneAppaltante.equals(cfSaAgente))) {
				List<TabellatoFaseEntry> fasiTemp = new ArrayList<TabellatoFaseEntry>();
				for (TabellatoFaseEntry f : fasiVisibili) {
					if ((idFDelegate == 1L || idFDelegate == 2L)
							&& (f.getCodice() == 1L || f.getCodice() == 101L || f.getCodice() == 984L)) {
						fasiTemp.add(f);
					}
					if ((idFDelegate == 4L) && (f.getCodice() == 101L || f.getCodice() == 984L)) {
						fasiTemp.add(f);
					}
				}
				fasiVisibili = fasiTemp;
			}

			risultato.setData(fasiVisibili);
		}catch (Exception e) {
			logger.error("Errore nel metodo listaFasiAbilitate per codgara: {}",codGara,e);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			risultato.setEsito(false);
		}
		
		return risultato;

	}

	private boolean checkFasePubblicabile(FaseEntry fase, List<Long> codiciFasiPubblicate,
			List<Long> codiciFasiPresenti, LottoEntry lotto,boolean pcp) {

		switch (fase.getFase().intValue()) {

		case 101:
		case 984:
		case 1:
		case 987:
		case 12:
		case 996:
			return true;
		case 2:
			return (((codiciFasiPubblicate.contains(1L) || codiciFasiPubblicate.contains(12L)) && !pcp) || (pcp && codiciFasiPubblicate.contains(13L))) && !codiciFasiPubblicate.contains(4L);
		case 20:
			return true;
		case 21:
			return true;
		case 13:
			return codiciFasiPubblicate.contains(1L);
		case 986:
			return codiciFasiPubblicate.contains(1L) || codiciFasiPubblicate.contains(987L)
					|| codiciFasiPubblicate.contains(12L);
		case 11:
			return codiciFasiPubblicate.contains(987L) || codiciFasiPubblicate.contains(1L);
		case 3: return (codiciFasiPubblicate.contains(2L) || codiciFasiPubblicate.contains(986L)  || codiciFasiPubblicate.contains(11L)) && !codiciFasiPubblicate.contains(4L);
		case 102:
			return (codiciFasiPubblicate.contains(986L)
					|| (codiciFasiPubblicate.contains(2L) || codiciFasiPubblicate.contains(11L))
							&& !codiciFasiPubblicate.contains(5L));
		case 4: // Conclusione
			boolean esisteAggiudicazionePubblicata = codiciFasiPubblicate.contains(12L)
					|| codiciFasiPubblicate.contains(1L);
			boolean nonEsisteInizio = !codiciFasiPresenti.contains(2L);
			boolean esisteInizioPubblicato = codiciFasiPresenti.contains(2L) && codiciFasiPubblicate.contains(2L);
			boolean esistonofasiprecNonPubblicate = false;
			List<Long> c = new ArrayList<Long>(codiciFasiPresenti);
			c.removeAll(codiciFasiPubblicate);
			if (!c.isEmpty() && !(c.contains(4L) || c.contains(5L))) {
				esistonofasiprecNonPubblicate = true;
			}
			return esisteAggiudicazionePubblicata && (nonEsisteInizio || esisteInizioPubblicato)
					&& !esistonofasiprecNonPubblicate && lotto.getSituazione() != 5L;
		case 985:
			boolean esisteAggiudicazionePubblicataSemp = codiciFasiPubblicate.contains(987L)
					|| codiciFasiPubblicate.contains(1L) || codiciFasiPubblicate.contains(12L);
			boolean nonEsisteInizioSemp = !codiciFasiPresenti.contains(986L);
			boolean esisteInizioPubblicatoSemp = codiciFasiPresenti.contains(986L)
					&& codiciFasiPubblicate.contains(986L);
			return esisteAggiudicazionePubblicataSemp && (nonEsisteInizioSemp || esisteInizioPubblicatoSemp);
		case 19:
			return codiciFasiPubblicate.contains(1L);
		case 5:
			return codiciFasiPubblicate.contains(4L);
		case 6:
			return (codiciFasiPubblicate.contains(986L) || codiciFasiPubblicate.contains(2L)
					|| codiciFasiPubblicate.contains(11L)) && !codiciFasiPubblicate.contains(4L)
					&& !codiciFasiPubblicate.contains(985L);
		case 7:
			return (((codiciFasiPubblicate.contains(987L) || codiciFasiPubblicate.contains(1L)
					|| codiciFasiPubblicate.contains(12L)) && !pcp) || codiciFasiPubblicate.contains(2L))  && !codiciFasiPubblicate.contains(5L);
		case 8:
			return (codiciFasiPubblicate.contains(986L) || codiciFasiPubblicate.contains(2L)
					|| codiciFasiPubblicate.contains(11L));
		case 14:			
			return codiciFasiPubblicate.contains(2L) && !codiciFasiPubblicate.contains(4L) && codiciFasiPubblicate.contains(6L);
		case 15:			
			return codiciFasiPubblicate.contains(2L) && !codiciFasiPubblicate.contains(4L) && codiciFasiPubblicate.contains(6L);
		case 9:
			return (((codiciFasiPubblicate.contains(1L) || codiciFasiPubblicate.contains(987L)))
					|| codiciFasiPubblicate.contains(12L)) && !codiciFasiPubblicate.contains(5L);			
		case 16:
			return codiciFasiPubblicate.contains(2L) && !codiciFasiPubblicate.contains(4L);
		case 17:
			return codiciFasiPubblicate.contains(2L) && !codiciFasiPubblicate.contains(4L) && codiciFasiPubblicate.contains(16L);
		case 18:
			return codiciFasiPubblicate.contains(2L) && !codiciFasiPubblicate.contains(4L) && codiciFasiPubblicate.contains(17L);
		case 10:
			return ((codiciFasiPubblicate.contains(1L)) || codiciFasiPubblicate.contains(12L))
					&& !codiciFasiPubblicate.contains(2L) && !codiciFasiPubblicate.contains(986L);
		case 997:
		case 995:
			return ((codiciFasiPubblicate.contains(1L) || codiciFasiPubblicate.contains(987L)))
					|| codiciFasiPubblicate.contains(12L);
		case 994:
			return codiciFasiPubblicate.contains(2L) || codiciFasiPubblicate.contains(986L);
		case 998:
			return codiciFasiPubblicate.contains(1L) || codiciFasiPubblicate.contains(12L);
		}
		return false;

	}

	private boolean checkFaseVisibility(TabellatoFaseEntry fase, LottoEntry lotto, GaraEntry gara,
			Long esitoProcedura, boolean pcp) {

		boolean l, t2, s3, r, saq, aaq, ea, all, ord, d1, s4;
//		Ann e Bnn	numero identificativo della fase descritto nel RFC 125
//		L	Contratto di lavori (W9LOTT.TIPO_CONTRATTO =‘L’)
//		t2	W9LOTT.IMPORTO_TOT ≥ 40.000,00 euro e (W9LOTT.EXSOTTOSOGLIA = ‘2’ o null)
//		S3	W9LOTT.IMPORTO_TOT ≥ 500.000,00 euro
//		R	campo manodopera/posa in opera = ’Si’
//		E1	campo “Contratto escluso ex art 10, 20, 21, 22, 23, 24, 25, 26 D. Lgs. 163/06?” = “Sì”
//		SAQ	Stipula accordo quadro (TIPO_APP=9)
//		AAQ	Adesione accordo quadro senza confronto competitivo (TIPO_APP=11 o 21)
//		EA	Lotto aggiudicato (W9ESITO.ESITO_PROCEDURA=1, se esiste il record di W9ESITO)
//		All	Intervento per ricostruzione alluvione Lunigiana e Elba (W9GARA.RIC_ALLUV =‘1’)
//		Ord	Settori ordinari (W9LOTT.FLAG_ENTE_SPECIALE=‘O’)

		Long numAppa = getNumAppa(new Long(lotto.getCodgara()), new Long(lotto.getCodLotto()), false);
//		FaseConclusioneEntry faseConc = this.contrattiMapper.getFaseConclusioneContrattoByNumAppa(
//				new Long(lotto.getCodgara()), new Long(lotto.getCodLotto()), numAppa);
		
		l = "L".equals(lotto.getTipologia());
		t2 = (lotto.getExsottosoglia() == null
				|| "2".equals(lotto.getExsottosoglia())) && (lotto.getArtE1() == null || (lotto.getArtE1() != null && "2".contentEquals(lotto.getArtE1())));
		s3 = t2 && lotto.getImportoTotale() != null && lotto.getImportoTotale() >= 500000;
		r = "1".equals(lotto.getManodopera());
		// e1 = "1".equals(lotto.getContrattoEscluso());
		saq = gara.getTipoApp() != null
				&& (9L == gara.getTipoApp() || 17L == gara.getTipoApp() || 18L == gara.getTipoApp());
		aaq = gara.getTipoApp() != null && (11L == gara.getTipoApp() || 21 == gara.getTipoApp());
		ea = (esitoProcedura == null || (esitoProcedura != null && (1L == esitoProcedura || 5L == esitoProcedura)));
		all = gara.getRicostruzioneAlluvione() == null ? false
				: "1".equals(gara.getRicostruzioneAlluvione().toString());
		ord = "O".equals(gara.getTipoSettore()) || gara.getVersioneSimog() >= 4L;
		String cfSa = this.contrattiMapper.getCFSAByCode(gara.getCodiceStazioneAppaltante());
		d1 = (gara.getIdFDelegate() != null && (gara.getIdFDelegate() == 1L || gara.getIdFDelegate() == 2L))
				&& (!cfSa.equals(gara.getCfAgenteStazioneAppaltante()));
		boolean permettiAgg = true;
		s4 = (lotto.getExsottosoglia() == null || "2".equals(lotto.getExsottosoglia()))  || aaq;
		
//      Una fase è visibile e se si soddisfano i seguenti criteri
		switch (fase.getCodice().intValue()) {

		case 101: 
			return !aaq && !pcp;
		case 20:
			return pcp && s4;
		case 21:
			return pcp && s4;
		case 984:
			return !aaq && numAppa < 2 && !pcp;
		case 1:
			return t2 && !aaq && ea && permettiAgg && !pcp;
		case 987:
			return !t2 && !aaq && ea && permettiAgg && !pcp;
		case 12:
			return aaq && ea && permettiAgg && !pcp;
		case 13:
			return s4 && pcp;		
		case 2:
			return (t2 && !saq && ea && ord && !d1 && !pcp) || (pcp && s4);
		case 986:
			return (!t2 || !ord) && !saq && ea && !d1 && !pcp;
		case 11:
			return saq && ea && !d1 && !pcp;
		case 3:
			return (((s3 || all) && ea && ord && !d1 && !pcp) || (pcp && s4)) && !saq;
		case 102:
			return ((!s3 && !all) || !ord) && !saq && ea && !d1 && !pcp;
		case 4:
			return (t2 && ea && ord && !d1 && !pcp) || (pcp && s4);
		case 985:
			return (!t2 || !ord) && ea && !saq && !d1 && !pcp;
		case 19:
			return pcp && !s4;
		case 5:
			return ((t2 && ea && ord && !d1 && !pcp) || (pcp && s4)) && !saq;
		case 6:
			return (((t2 || all || r) && ea && ord && !d1 && !pcp) || (pcp && s4)) && !saq;
		case 7:
			//return (((t2 || all) && ea && ord && !d1) && !pcp) || (pcp && s4);
			//return t2 && ea  && !aaq && !d1 && (ord || pcp);
			return t2 && ea && !d1 && ord;
		case 8:
			return ((t2 && ea && ord && !d1 && !pcp) || (pcp && s4)) && !saq;
		case 14:
			return s4 && pcp;
		case 15:
			return s4 && pcp;
		case 9:
			return (t2 || all) && ea && ord && !d1 && !pcp;
		case 16:
			return s4 && pcp;
		case 17:
			return s4 && pcp;
		case 18:
			return s4 && pcp;
		case 10:			
			return ((l && (t2 || all) && ea && ord && !d1 && !pcp) || (pcp && s4)) && !saq;
		case 997:
			return !saq && ea && !d1 && !pcp;
		case 996:
			return !saq && ea && !d1 && !pcp;
		case 995:
			return r && !saq && ea && !d1 && !pcp;
		case 994:
			return r && !saq && ea && !d1 && !pcp;
		case 998:
			return r && !saq && ea && !d1 && !pcp;
		}

		return false;
	}

	private boolean checkFaseAbilitata(TabellatoFaseEntry fase, List<FaseEntry> fasiLotto, boolean pcp,Long codGara, Long numLotto) {

		// Lista delle fasi esistenti per il lotto
		List<Long> codiciFasiEsistenti = new ArrayList<Long>();
		for (FaseEntry f : fasiLotto) {
			codiciFasiEsistenti.add(f.getFase());
		}

		if (codiciFasiEsistenti.contains(fase.getCodice())) {
			if (fase.getCodice() == 101L || fase.getCodice() == 984L || fase.getCodice() == 1L
					|| fase.getCodice() == 987L || fase.getCodice() == 12L || fase.getCodice() == 2L
					|| fase.getCodice() == 986L || fase.getCodice() == 11L || fase.getCodice() == 4L
					|| fase.getCodice() == 985L || fase.getCodice() == 5L || fase.getCodice() == 13L
					|| fase.getCodice() == 19L || fase.getCodice() == 10L) {
				return false;
			}
		}
		
		List<FaseSubappaltoEntry> esitoSuba = this.contrattiMapper.getNumSubaEsitoSuba(codGara, numLotto);
		List<FaseSubappaltoEntry> conclusioneSuba = this.contrattiMapper.getNumSubaConclusioneSuba(codGara, numLotto);
		List<FaseSospensioneEntry> sospList = this.contrattiMapper.getFasiSospensione(codGara, numLotto);

		// Una fase è abilitata se visibile e se si soddisfano le seguenti dipendenze
		// con le altre fasi
		switch (fase.getCodice().intValue()) {
		case 101:
		case 20:
			return true;
		case 21:
			return codiciFasiEsistenti.contains(1L);
		case 1:
		case 12:
		case 996:
			return true;
		case 984:
		case 987:
			return !codiciFasiEsistenti.contains(1L) && !codiciFasiEsistenti.contains(987L);
		case 13:
			return codiciFasiEsistenti.contains(1L);
		case 2:
			return (((codiciFasiEsistenti.contains(1L) || codiciFasiEsistenti.contains(12L)) && !pcp) || (pcp && codiciFasiEsistenti.contains(13L)))
					&& !codiciFasiEsistenti.contains(4L);
		case 986:
			return (codiciFasiEsistenti.contains(1L) || codiciFasiEsistenti.contains(987L)
					|| codiciFasiEsistenti.contains(12L)) && !codiciFasiEsistenti.contains(985L);
		case 11:
			return codiciFasiEsistenti.contains(987L) || codiciFasiEsistenti.contains(1L);
		case 3:
			return (codiciFasiEsistenti.contains(986L)
					|| (codiciFasiEsistenti.contains(2L) || codiciFasiEsistenti.contains(11L))
							&& !codiciFasiEsistenti.contains(4L));
		case 102:
			return (codiciFasiEsistenti.contains(986L)
					|| (codiciFasiEsistenti.contains(2L) || codiciFasiEsistenti.contains(11L))
							&& !(codiciFasiEsistenti.contains(4L) || codiciFasiEsistenti.contains(985L)));
		case 4:
			return codiciFasiEsistenti.contains(12L) || codiciFasiEsistenti.contains(1L);
		case 985:
			return codiciFasiEsistenti.contains(987L) || codiciFasiEsistenti.contains(1L)
					|| codiciFasiEsistenti.contains(12L);
		case 19:
			return codiciFasiEsistenti.contains(1L);
		case 5:
			return codiciFasiEsistenti.contains(4L);
		case 6:
			return (codiciFasiEsistenti.contains(986L) || codiciFasiEsistenti.contains(2L)
					|| codiciFasiEsistenti.contains(11L)) && !codiciFasiEsistenti.contains(4L)
					&& !codiciFasiEsistenti.contains(985L);
		case 14:
			return codiciFasiEsistenti.contains(2L) && !codiciFasiEsistenti.contains(4L) && codiciFasiEsistenti.contains(6L) && (sospList != null && sospList.size() > 0);
		case 15:
			return codiciFasiEsistenti.contains(2L) && !codiciFasiEsistenti.contains(4L) && codiciFasiEsistenti.contains(6L) && (sospList != null && sospList.size() > 0);
		case 7:
			return (((codiciFasiEsistenti.contains(987L) || codiciFasiEsistenti.contains(1L)
					|| codiciFasiEsistenti.contains(12L)) && !pcp) || codiciFasiEsistenti.contains(2L))
					&& !codiciFasiEsistenti.contains(5L);
		case 8:
			return (codiciFasiEsistenti.contains(986L) || codiciFasiEsistenti.contains(2L)
					|| codiciFasiEsistenti.contains(11L));
		case 9:
			return (((codiciFasiEsistenti.contains(1L) || codiciFasiEsistenti.contains(987L)))
					|| codiciFasiEsistenti.contains(12L)) && !codiciFasiEsistenti.contains(4L);
		case 16:
			return codiciFasiEsistenti.contains(2L) && !codiciFasiEsistenti.contains(4L);
		case 17:
			return codiciFasiEsistenti.contains(2L) && !codiciFasiEsistenti.contains(4L) && codiciFasiEsistenti.contains(16L) && (esitoSuba != null && esitoSuba.size() > 0);
		case 18:
			return codiciFasiEsistenti.contains(2L) && !codiciFasiEsistenti.contains(4L) && codiciFasiEsistenti.contains(17L) && (conclusioneSuba != null && conclusioneSuba.size() > 0);
		case 10:
			return ((((codiciFasiEsistenti.contains(1L)) || codiciFasiEsistenti.contains(12L)) && !pcp) || codiciFasiEsistenti.contains(13L))
					&& !codiciFasiEsistenti.contains(4L) && !codiciFasiEsistenti.contains(985L);
		case 997:
		case 995:
			return ((codiciFasiEsistenti.contains(1L) || codiciFasiEsistenti.contains(987L)))
					|| codiciFasiEsistenti.contains(12L);
		case 994:
			return codiciFasiEsistenti.contains(2L) || codiciFasiEsistenti.contains(986L);
		case 998:
			return codiciFasiEsistenti.contains(1L) || codiciFasiEsistenti.contains(12L);
		}
		return false;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseComunicazione(FaseComEsitoForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {

			if (form.getBinary() != null) {
				byte[] dedcodedFile = Base64.getDecoder().decode(form.getBinary());
				form.setFileAllegato(dedcodedFile);
			}

			this.contrattiMapper.insertFaseComEsito(form);
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());

			// Calcolo l'id per l'inserimento del record nella w9Fasi
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 984L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			String idScheda = lotto.getCig() + "_984_" + calcolaIdScheda(id);

			// PER FASE COM ESITO NUM_APPA = 1 sempre. 21/10/2020
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 984L, id, idScheda, 1L, null);

		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase comunicazione. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseComunicazione getFaseComunicazione(Long codGara, Long codLotto, Long numAppa) {
		ResponseFaseComunicazione risultato = new ResponseFaseComunicazione();
		FaseComunicazioneEntry entry = new FaseComunicazioneEntry();
		risultato.setEsito(true);
		try {
			if (numAppa == null) {
				numAppa = getNumAppa(codGara, codLotto, false);
			}

			FaseComEsitoForm faseComEsito = this.contrattiMapper.getFaseComEsito(codGara, codLotto, numAppa);
			if (faseComEsito.getFileAllegato() != null) {
				byte[] encodedFile = Base64.getEncoder().encode(faseComEsito.getFileAllegato());
				faseComEsito.setFileAllegato(encodedFile);
				faseComEsito.setBinary(new String(encodedFile));
			}

			entry.setEsito(faseComEsito);
			entry.setPubblicazioneEsito(this.contrattiMapper.getFaseComPubbEsito(codGara, codLotto, 1L));
			entry.setPubblicata(isFasePubblicata(codGara, codLotto, 984L, 1L));

			List<FaseEntry> fasiLotto = this.contrattiMapper.getFasiLotto(codGara, codLotto, numAppa);
			boolean hasAggiudicazione = false;
			for (FaseEntry f : fasiLotto) {
				if (f.getFase() == 1L || f.getFase() == 987L) {
					hasAggiudicazione = true;
				}
			}
			entry.setHasAggiudicazione(hasAggiudicazione);
			risultato.setData(entry);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase comunicazione. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseComunicazione(Long codGara, Long codLotto) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseComEsito(codGara, codLotto);
			this.contrattiMapper.deleteFaseComPubbEsito(codGara, codLotto);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 984L, 1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase comunicazione. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseComunicazione(FaseComEsitoForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {

			if (form.getBinary() != null) {
				byte[] dedcodedFile = Base64.getDecoder().decode(form.getBinary());
				form.setFileAllegato(dedcodedFile);
			}
			this.contrattiMapper.updateFaseComEsito(form);

			// In seguito all'update di una fase il corrispettivo record nella w9fasi va
			// settato da esportare
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 984L, 1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase comunicazione. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseAggiudicazione(FaseAggInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);

		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());

			// Se in fase di riaggiudicazione travaso i dati di gran parte della scheda da
			// quella di aggiudicazione precedente
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), true);
			if (numAppa > 1) {
				form = mergeAggiudicazione(form, numAppa - 1);
			}
			form.setNum(numAppa);
			risultato.setData(numAppa);
			this.contrattiMapper.insertFaseAggiudicazione(form);
			// Calcolo l'id per l'inserimento del record nella w9Fasi
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 1L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}

			String idScheda = lotto.getCig() + "_1_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 1L, id, idScheda, numAppa, null);
			// All'inserimento della fase si inserisce di default un incarico professionale
			this.contrattiMapper.insertIncaricoProfessionale(form.getCodGara(), form.getCodLotto(), numAppa, 1L, 14L,
					"RA", lotto.getRup());
			if (numAppa > 1) {
				List<FinanziamentiEntry> finanziamenti = this.contrattiMapper.getFinanziamenti(form.getCodGara(),
						form.getCodLotto(), numAppa - 1);
				if (finanziamenti != null) {
					// Long nmFina = this.contrattiMapper.getmaxNumFina(form.getCodGara(),
					// form.getCodLotto());
					for (FinanziamentiEntry f : finanziamenti) {
						FinanziamentoInsertForm insertform = new FinanziamentoInsertForm();
						insertform.setIdFinanziamento(f.getIdFinanziamento());
						insertform.setImportoFinanziamento(f.getImportoFinanziamento());
						this.contrattiMapper.insertFinanziamentoForm(form.getCodGara(), form.getCodLotto(), numAppa,
								f.getNumFina(), insertform);
					}
				}
			}

			if (numAppa > 1) {
				Long situazioneLotto = getSituazioneLotto(form.getCodGara(), form.getCodLotto());
				this.contrattiMapper.updateSituazioneLotto(form.getCodGara(), form.getCodLotto(), situazioneLotto);
			}

			List<Long> attoPubb = this.contrattiMapper.checkIfGaraHAsAttoFaseAggiudicazione(form.getCodGara(),
					form.getCodLotto());
			if (attoPubb != null && attoPubb.size() > 0) {

				List<ImpresaAggiudicatariaAttoEntry> impreseAtto = this.contrattiMapper
						.getImpreseAggiudicatarieAtto(form.getCodGara(), attoPubb.get(0));
				ImpresaAggiudicatariaInsertForm insertForm = null;
				for (ImpresaAggiudicatariaAttoEntry imp : impreseAtto) {
					insertForm = new ImpresaAggiudicatariaInsertForm();
					insertForm.setCodGara(form.getCodGara());
					insertForm.setCodLotto(form.getCodLotto());
					insertForm.setNumAppa(numAppa);
					insertForm.setNumAggi(imp.getNumAggi());
					insertForm.setIdTipoAgg(imp.getIdTipoAgg());
					insertForm.setRuolo(imp.getRuolo());
					insertForm.setCodImpresa(imp.getCodImpresa());
					insertForm.setIdGruppo(imp.getIdGruppo());
					this.contrattiMapper.insertImpresaAggiudicataria(insertForm);
				}

			}

			risultato.setData(id);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase aggiudicazione. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	private FaseAggInsertForm mergeAggiudicazione(FaseAggInsertForm form, Long numAppa) {

		FaseAggiudicazioneEntry aggPrecedente = this.contrattiMapper.getFaseAggiudicazione(form.getCodGara(),
				form.getCodLotto(), numAppa);
		if (aggPrecedente == null) {
			return form;
		}
		form.setAltreSomme(aggPrecedente.getAltreSomme());
		form.setAstaElettronica(aggPrecedente.getAstaElettronica());
		form.setCodGara(aggPrecedente.getCodGara());
		form.setCodLotto(aggPrecedente.getCodLotto());
		form.setCodStrumento(aggPrecedente.getCodStrumento());
		form.setDataInvito(aggPrecedente.getDataInvito());
		form.setDataManifestInteresse(aggPrecedente.getDataManifestInteresse());
		form.setDataScadenzaPresOfferta(aggPrecedente.getDataScadenzaPresOfferta());
		form.setFlagImporti(aggPrecedente.getFlagImporti());
		form.setFlagLivelloProgettuale(aggPrecedente.getFlagLivelloProgettuale());
		form.setFlagSicurezza(aggPrecedente.getFlagSicurezza());
		form.setFlagSubappFinReg(aggPrecedente.getFlagSubappFinReg());
		form.setIdIndizione(aggPrecedente.getIdIndizione());
		form.setImpEsclusInsuffGiust(aggPrecedente.getImpEsclusInsuffGiust());
		form.setImpNonAssog(aggPrecedente.getImpNonAssog());
		form.setImportoComplAppalto(aggPrecedente.getImportoComplAppalto());
		form.setImportoComplIntervento(aggPrecedente.getImportoComplIntervento());
		form.setImportoDisposizione(aggPrecedente.getImportoDisposizione());
		form.setImportoForniture(aggPrecedente.getImportoForniture());
		form.setImportoIva(aggPrecedente.getImportoIva());
		form.setImportoLavori(aggPrecedente.getImportoLavori());
		form.setImportoProgettazione(aggPrecedente.getImportoProgettazione());
		form.setImportoServizi(aggPrecedente.getImportoServizi());
		form.setImportoSicurezza(aggPrecedente.getImportoSicurezza());
		form.setImportoSommeOpzioniRinnovi(aggPrecedente.getImportoSommeOpzioniRinnovi());
		form.setImportoSommeripetizioni(aggPrecedente.getImportoSommeripetizioni());
		form.setImportosubtotale(aggPrecedente.getImportosubtotale());
		form.setIva(aggPrecedente.getIva());
		form.setNumImpreseInvitate(aggPrecedente.getNumImpreseInvitate());
		form.setNumImpreseOfferenti(aggPrecedente.getNumImpreseOfferenti());
		form.setNumImpreseRichiedenti(aggPrecedente.getNumImpreseRichiedenti());
		form.setNumManifestInteresse(aggPrecedente.getNumManifestInteresse());
		form.setNumOfferteAmmesse(aggPrecedente.getNumOfferteAmmesse());
		form.setNumOfferteEscluse(aggPrecedente.getNumOfferteEscluse());
		form.setNumOfferteFuoriSoglia(aggPrecedente.getNumOfferteFuoriSoglia());
		form.setOffertaMassima(aggPrecedente.getOffertaMassima());
		form.setOffertaMinima(aggPrecedente.getOffertaMinima());
		form.setOpereUrbanizScomputo(aggPrecedente.getOpereUrbanizScomputo());
		form.setPreinformazione(aggPrecedente.getPreinformazione());
		form.setProceduraAcc(aggPrecedente.getProceduraAcc());
		form.setTermineRidotto(aggPrecedente.getTermineRidotto());
		form.setValoreSogliaAnomalia(aggPrecedente.getValoreSogliaAnomalia());
		form.setVerificaCampione(aggPrecedente.getVerificaCampione());
		return form;
	}

	public ResponseFaseAggiudicazione getFaseAggiudicazione(Long codGara, Long codLotto, Long numAppa) {
		ResponseFaseAggiudicazione risultato = new ResponseFaseAggiudicazione();
		risultato.setEsito(true);
		try {
			if (numAppa == null) {
				numAppa = getNumAppa(codGara, codLotto, false);
			}
			FaseAggiudicazioneEntry fase = this.contrattiMapper.getFaseAggiudicazione(codGara, codLotto, numAppa);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 1L, numAppa));
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase aggiudicazione. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseAggiudicazione(FaseAggInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			// Se in fase di riaggiudicazione travaso i dati di gran parte della scheda da
			// quella di aggiudicazione precedente
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			if (numAppa > 1) {
				form = mergeAggiudicazione(form, numAppa);
			}
			form.setNum(numAppa);
			this.contrattiMapper.updateFaseAggiudicazione(form);
			// In seguito all'update di una fase il corrispettivo record nella w9fasi va
			// settato da esportare
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 1L, 1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase aggiudicazione. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseAggiudicazione(Long codGara, Long codLotto, Long numAppa) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseAggiudicazione(codGara, codLotto, numAppa);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 1L, numAppa);

			// delete imprese aggiudicatarie
			this.contrattiMapper.deleteImpreseAggiudicatarie(codGara, codLotto, numAppa);
			this.contrattiMapper.deleteFinanziamenti(codGara, codLotto, numAppa);
			// delete incarichi professionali
			List<String> sezioni = getSezioneIncaricoByFase(1L);
			for (String sezione : sezioni) {
				this.contrattiMapper.deleteIncarichiProf(codGara, codLotto, sezione, numAppa);
			}

			if (numAppa > 1) {
				Long situazioneLotto = getSituazioneLotto(codGara, codLotto);
				this.contrattiMapper.updateSituazioneLotto(codGara, codLotto, situazioneLotto);
			}
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase aggiudicazione. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseAggiudicazioneSemp(FaseAggSempInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.insertFaseAggiudicazioneSemp(form);

			// Calcolo l'id per l'inserimento del record nella w9Fasi
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 987L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}

			String idScheda = lotto.getCig() + "_987_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 987L, id, idScheda, 1L, null);
			// All'inserimento della fase va in automatico aggiunto un incarico
			// professionale
			String sezione = "1".equals(lotto.getContrattoEscluso()) ? "RE" : "RS";
			this.contrattiMapper.insertIncaricoProfessionale(form.getCodGara(), form.getCodLotto(), 1L, 1L, 14L,
					sezione, lotto.getRup());
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			List<Long> attoPubb = this.contrattiMapper.checkIfGaraHAsAttoFaseAggiudicazione(form.getCodGara(),
					form.getCodLotto());
			if (attoPubb != null && attoPubb.size() > 0) {

				List<ImpresaAggiudicatariaAttoEntry> impreseAtto = this.contrattiMapper
						.getImpreseAggiudicatarieAtto(form.getCodGara(), attoPubb.get(0));
				ImpresaAggiudicatariaInsertForm insertForm = null;
				for (ImpresaAggiudicatariaAttoEntry imp : impreseAtto) {
					insertForm = new ImpresaAggiudicatariaInsertForm();
					insertForm.setCodGara(form.getCodGara());
					insertForm.setCodLotto(form.getCodLotto());
					insertForm.setNumAppa(numAppa);
					insertForm.setNumAggi(imp.getNumAggi());
					insertForm.setIdTipoAgg(imp.getIdTipoAgg());
					insertForm.setRuolo(imp.getRuolo());
					insertForm.setCodImpresa(imp.getCodImpresa());
					insertForm.setIdGruppo(imp.getIdGruppo());
					this.contrattiMapper.insertImpresaAggiudicataria(insertForm);
				}

			}

			risultato.setData(1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase aggiudicazione semplificata. codgara = "
					+ form.getCodGara() + " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseAggiudicazioneSemp getFaseAggiudicazioneSemp(Long codGara, Long codLotto, Long numAppa) {
		ResponseFaseAggiudicazioneSemp risultato = new ResponseFaseAggiudicazioneSemp();
		risultato.setEsito(true);
		try {
			if (numAppa == null) {
				numAppa = getNumAppa(codGara, codLotto, false);
			}
			FaseAggiudicazioneSempEntry fase = this.contrattiMapper.getFaseAggiudicazioneSemp(codGara, codLotto,
					numAppa);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 987L, 1L));
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase aggiudicazione semplificata. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseAggiudicazioneSemp(FaseAggSempInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseAggiudicazioneSemp(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 987L, 1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase aggiudicazione semplice. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseAggiudicazioneSemp(Long codGara, Long codLotto, Long numAppa) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseAggiudicazioneSemp(codGara, codLotto, numAppa);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 987L, 1L);

			// delete imprese aggiudicatarie
			this.contrattiMapper.deleteImpreseAggiudicatarie(codGara, codLotto, numAppa);
			// delete incarichi professionali
			List<String> sezioni = getSezioneIncaricoByFase(987L);
			for (String sezione : sezioni) {
				this.contrattiMapper.deleteIncarichiProf(codGara, codLotto, sezione, numAppa);
			}
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase aggiudicazione semplice. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}

	public ResponseImprAggiudicatarie getImpreseAggiudicatarie(Long codGara, Long codLotto, Long numAppa) {
		ResponseImprAggiudicatarie risultato = new ResponseImprAggiudicatarie();
		risultato.setEsito(true);
		try {
			List<ImpresaAggiudicatariaEntry> imprese = this.contrattiMapper.getImpreseAggiudicatarie(codGara, codLotto,
					numAppa);
			for (ImpresaAggiudicatariaEntry i : imprese) {
				i.setImpresa(this.getImpresa(i.getCodImpresa()));
				i.setImpresaAusiliaria(this.getImpresa(i.getCodImpAus()));
			}
			risultato.setData(imprese);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura imprese aggiudicatarie. codgara = " + codGara + " codLotto = "
					+ codLotto + " numAppa " + numAppa, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseImprAggiudicatarieAtto getImpreseAggiudicatarieAtto(Long codGara, Long numPubb) {
		ResponseImprAggiudicatarieAtto risultato = new ResponseImprAggiudicatarieAtto();
		risultato.setEsito(true);
		try {
			List<ImpresaAggiudicatariaAttoEntry> imprese = this.contrattiMapper.getImpreseAggiudicatarieAtto(codGara,
					numPubb);
			for (ImpresaAggiudicatariaAttoEntry i : imprese) {
				i.setImpresa(this.getImpresa(i.getCodImpresa()));
			}
			risultato.setData(imprese);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura imprese aggiudicatarie atto. codgara = " + codGara
					+ " codLotto = " + " numPubb " + numPubb, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse importImpresaAggiudicataria(ImportImpresaAgiudicatariaForm form) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		List<RecordFaseImpreseEntry> impreseInvitate = new ArrayList<RecordFaseImpreseEntry>();
		try {
			boolean exists = checkImpresaAggiudicatariaExists(form);
			if (exists) {
				throw new ImportImpresaAggiudicatariaException();
			}
			if (form.getNumRagg() != null && form.getNumRagg() != 0) {
				impreseInvitate = this.contrattiMapper.getFaseImpreseByRagg(form.getCodGara(), form.getCodLotto(),
						form.getNumRagg());
			} else {
				impreseInvitate.add(this.contrattiMapper.dettaglioFaseImprese(form.getCodGara(), form.getCodLotto(),
						form.getNum()));
			}
			for (RecordFaseImpreseEntry impInvitata : impreseInvitate) {				
				ImpresaAggiudicatariaInsertForm insertForm = this.getImpresaAggFromInvitata(impInvitata);
				LegaleRappresentanteEntry legRap = this.contrattiMapper.getLegaleRappresentante(impInvitata.getCodImpresa());
				if(legRap != null) {
					insertForm.setCfLegRap(legRap.getCf());
					insertForm.setNomeLegRap(legRap.getNome());
					insertForm.setCognomeLegRap(legRap.getCognome());
				}
				this.contrattiMapper.insertImpresaAggiudicataria(insertForm);
			}

		} catch (ImportImpresaAggiudicatariaException e) {
			logger.error("Errore inaspettato durante l'import dell'impresa aggiudicataria. L'impresa codGara [ "
					+ form.getCodGara() + " ], codLotto [ " + form.getCodLotto() + " ], numAppa [ " + 1L
					+ " ], numAggi [ " + form.getNum() + " ], idGruppo [ " + form.getNumRagg()
					+ " ] e' gia' stata inserita", e);
			risultato.setEsito(false);
			risultato.setErrorData(Constants.ERROR_IMPRESA_GIA_IMPORTATA);
		} catch (Exception e) {
			logger.error("Errore inaspettato durante l'import dell'impresa aggiudicataria.", e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	private boolean checkImpresaAggiudicatariaExists(ImportImpresaAgiudicatariaForm form) {
		Long codGara = form.getCodGara();
		Long codLotto = form.getCodLotto();
		Long numAppa = getNumAppa(codGara, codLotto, false);
		Long numAggi = form.getNum();
		Long idGruppo = form.getNumRagg();
		if (idGruppo == null) {
			ImpresaAggiudicatariaEntry impresa = this.contrattiMapper.getImpresaAggiudicataria(codGara, codLotto,
					numAppa, numAggi);
			return impresa != null;
		} else {
			List<ImpresaAggiudicatariaEntry> imprese = this.contrattiMapper.getImpreseAggiudicatarieGroup(codGara,
					codLotto, numAppa, idGruppo);
			return imprese != null && imprese.size() > 0;
		}
	}

	private ImpresaAggiudicatariaInsertForm getImpresaAggFromInvitata(RecordFaseImpreseEntry impInvitata) {
		ImpresaAggiudicatariaInsertForm risultato = new ImpresaAggiudicatariaInsertForm();

		risultato.setCodGara(impInvitata.getCodGara());
		risultato.setCodImpresa(impInvitata.getCodImpresa());
		risultato.setCodLotto(impInvitata.getCodLotto());
		risultato.setIdGruppo(impInvitata.getNumRaggruppamento());
		risultato.setNumAggi(impInvitata.getNum());
		risultato.setNumAppa(getNumAppa(impInvitata.getCodGara(), impInvitata.getCodLotto(), false));
		risultato.setRuolo(impInvitata.getRuolo());
		risultato.setIdTipoAgg(impInvitata.getTipologiaSoggetto());

		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertImpresaAggiudicataria(ImpreseAggiudicatarieInsertForm form) {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {

			if(form.getIdTipoAgg() != null && form.getIdTipoAgg() == 2L) {
				if(form.getImprese() == null || (form.getImprese() != null && form.getImprese().size() < 2)) {
					risultato.setEsito(false);
					risultato.setErrorData(ERROR_NUM_IMPRESE);
					return risultato;
				}
			}
			
			
			Long numAppa = getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			// Calcolo l'id per l'inserimento del record
			Long id = this.contrattiMapper.getMaxId(form.getCodGara(), form.getCodLotto());
			Long idGruppo = this.contrattiMapper.getMaxIdGruppo(form.getCodGara(), form.getCodLotto());
			id = (id == null) ? 1L : id + 1;
			idGruppo = (idGruppo == null) ? 1L : idGruppo + 1;
			if (form.getImprese() != null) {
				for (ImpresaAggiudicatariaInsertForm impresa : form.getImprese()) {
					impresa.setNumAppa(numAppa);
					impresa.setNumAggi(id);
					impresa.setCodGara(form.getCodGara());
					impresa.setCodLotto(form.getCodLotto());
					impresa.setIdTipoAgg(form.getIdTipoAgg());

					if (form.getImprese().size() > 1) {
						impresa.setIdGruppo(idGruppo);
					}
					this.contrattiMapper.insertImpresaAggiudicataria(impresa);
					id++;
				}
			}

		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento impresa aggiudicataria.", e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateImpresaAggiudicataria(ImpreseAggiudicatarieUpdateForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {

			if (form.getImpreseDaCancellare() != null && form.getImpreseDaCancellare().size() > 0) {
				for (Long numAggi : form.getImpreseDaCancellare()) {
					this.deleteImpresaAggiudicataria(form.getCodGara(), form.getCodLotto(), form.getNumAppa(), numAggi);
				}
			}
			// Calcolo l'id per l'inserimento del record
			Long id = this.contrattiMapper.getMaxId(form.getCodGara(), form.getCodLotto());
			Long idGruppo = this.contrattiMapper.getMaxIdGruppo(form.getCodGara(), form.getCodLotto());
			id = (id == null) ? 1L : id + 1;
			idGruppo = (idGruppo == null) ? 1L : idGruppo + 1;
			Long numAppa = getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			if (form.getImprese() != null) {
				for (ImpresaAggiudicatariaInsertForm impresa : form.getImprese()) {
					impresa.setNumAppa(numAppa);
					impresa.setNumAggi(id);
					impresa.setCodGara(form.getCodGara());
					impresa.setCodLotto(form.getCodLotto());
					impresa.setIdTipoAgg(form.getIdTipoAgg());
					if (form.getImprese().size() > 1) {
						impresa.setIdGruppo(idGruppo);
					}
										
					this.contrattiMapper.insertImpresaAggiudicataria(impresa);
					id++;
				}
			}
		} catch (Exception e) {
			logger.error("Errore inaspettato la modifica impresa aggiudicataria. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteImpresaAggiudicataria(Long codGara, Long codLotto, Long numAppa, Long numAggi) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			Long idGruppo = this.contrattiMapper.getIdGruppo(codGara, codLotto, numAppa, numAggi);
			if (idGruppo != null) {
				this.contrattiMapper.deleteImpreseAggiudicatarieIdGroup(codGara, codLotto, numAppa, idGruppo);
			} else {
				this.contrattiMapper.deleteImpreseAggiudicatarieNoIdGroup(codGara, codLotto, numAppa, numAggi);
			}
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione impresa aggiudicataria. codgara = " + codGara
					+ " codLotto = " + codLotto + " numAggi= " + numAggi + " numAppa = " + numAppa, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteImpresaAggiudicatariaAtto(Long codGara, Long numPubb) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteImpreseAggiudicatarieAtto(codGara, numPubb);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione impresa aggiudicataria atto. codgara = " + codGara
					+ " numPubb = " + numPubb, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseIncarichiProfessionali getIncarichiProfessionali(Long codGara, Long codLotto, Long numAppa,
			Long codFase) {
		ResponseIncarichiProfessionali risultato = new ResponseIncarichiProfessionali();
		risultato.setEsito(true);
		List<String> sezioni = getSezioneIncaricoByFase(codFase);
		List<IncarichiProfEntry> incarichi = new ArrayList<IncarichiProfEntry>();
		try {
			for (String sezione : sezioni) {
				List<IncarichiProfEntry> incarichiPerSezione = this.contrattiMapper
						.getIncarichiProfessionaliBySezione(codGara, codLotto, numAppa, sezione);
				if (incarichiPerSezione != null && !incarichiPerSezione.isEmpty()) {
					incarichi.addAll(incarichiPerSezione);
				}
			}

			// Prendo il tecnico associato all'incarico dal codice RUP
			for (IncarichiProfEntry incarico : incarichi) {
				incarico.setTecnico(this.contrattiMapper.getTecnico(incarico.getCodiceTecnico()));
			}
			risultato.setData(incarichi);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura incarichi professionali. codgara = " + codGara + " codLotto = "
					+ codLotto + " numAppa " + numAppa, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateIncarichiProfessionali(IncarichiProfessionaliInsertForm form) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(false);
		try {
			Long codGara = form.getCodGara();
			Long codLotto = form.getCodLotto();

			Long numAppa = form.getNumAppa() == null ? 1L : form.getNumAppa();
			if (form.getIncarichi() == null) {
				throw new Exception("Lista null");
			}

			List<String> sezioni = getSezioneIncaricoByFase(form.getCodFase());

			for (String sezione : sezioni) {
				this.contrattiMapper.deleteIncarichiProf(codGara, codLotto, sezione, numAppa);
			}
			Long idIncarico = 1L;
			for (IncaricoProfessionaleInsertForm incarico : form.getIncarichi()) {
				this.contrattiMapper.insertIncaricoProfessionaleForm(codGara, codLotto, numAppa, idIncarico, incarico);
				idIncarico++;
			}
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore inaspettato la modifica degli incarichi professionali. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto() + " numAppa " + form.getNumAppa(), e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteIncaricoProfessionale(Long codGara, Long codLotto, Long numAppa, Long numInca) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteIncaricoProf(codGara, codLotto, numAppa, numInca);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione incarico. codgara = " + codGara + " codLotto = "
					+ codLotto + " numAppa= " + numAppa + " numInca = " + numInca, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
	
	public ResponseFaseIncarichiProfessionali getFaseIncarichiProfessionali(Long codGara, Long codLotto, Long num) {
		ResponseFaseIncarichiProfessionali risultato = new ResponseFaseIncarichiProfessionali();
		risultato.setEsito(true);		
		
		try {
			//VIGILANZA2-511: Recupero il dettaglio gara per adeguare il tabellato appartenente ad idRuolo in modo da mostrare solo i
			//valori: 5-8, 10, 13, 18 nel caso di una gara PCP. In una gara SIMOG si vedono tutti dall'1 al 19.
			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);

			List<IncarichiProfEntry> incarichi = this.contrattiMapper
					.getIncarichiProfessionali(codGara, codLotto, num);
			
			risultato.setPubblicata(isFasePubblicata(codGara, codLotto, 20L, num));
			// Prendo il tecnico associato all'incarico dal codice RUP
			for (IncarichiProfEntry incarico : incarichi) {
				incarico.setTecnico(this.contrattiMapper.getTecnico(incarico.getCodiceTecnico()));

				if(gara.getVersioneSimog() != null && gara.getVersioneSimog() == 9L) {
					incarico.setPcp(true);
				}
			}

			risultato.setIncarichi(incarichi);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase incarichi professionali. codgara = " + codGara + " codLotto = "
					+ codLotto + " num " + num, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseIncarichiProfessionali(IncarichiProfessionaliInsertForm form) {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(false);
		try {
			Long codGara = form.getCodGara();
			Long codLotto = form.getCodLotto();
			
			if (form.getIncarichi() == null) {
				throw new Exception("Lista null");
			}

			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			//Long numAppa = form.getNumAppa() == null ? 1L : form.getNumAppa();
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			//form.setNumAppa(numAppa);
						
			Long idIncarico = this.contrattiMapper.getMaxNumInca(codGara, codLotto, numAppa);
			if (idIncarico == null) {
				idIncarico = 1L;
			} else {
				idIncarico++;
			}

			
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 20L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			
			for (IncaricoProfessionaleInsertForm incarico : form.getIncarichi()) {
			this.contrattiMapper.insertIncaricoProfessionaleForm(codGara, codLotto, id, idIncarico, incarico);
			idIncarico++;
		}
			
			String idScheda = lotto.getCig() + "_20_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 20L, id, idScheda, numAppa, null);
			risultato.setData(id);							
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore inaspettato durante l'insert della fase incarichi professionali. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto() + " numAppa " + form.getNumAppa(), e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseIncarichiProfessionali(IncarichiProfessionaliInsertForm form) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(false);
		try {
			Long codGara = form.getCodGara();
			Long codLotto = form.getCodLotto();

			Long numAppa = form.getNumAppa() == null ? 1L : form.getNumAppa();
			if (form.getIncarichi() == null) {
				throw new Exception("Lista null");
			}	
			
			this.contrattiMapper.deleteIncarichiProfWithoutSezione(codGara, codLotto, form.getNum());
			
			Long idIncarico = this.contrattiMapper.getMaxNumInca(codGara, codLotto, form.getNum());
			if (idIncarico == null) {
				idIncarico = 1L;
			} else {
				idIncarico++;
			}
			for (IncaricoProfessionaleInsertForm incarico : form.getIncarichi()) {
				this.contrattiMapper.insertIncaricoProfessionaleForm(codGara, codLotto, form.getNum(), idIncarico, incarico);
				idIncarico++;
			}
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 20L, form.getNum());
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore inaspettato la modifica della fase incarichi professionali. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto() + " numAppa " + form.getNumAppa(), e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseIncaricoProfessionale(Long codGara, Long codLotto, Long num) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 20L, num);
			this.contrattiMapper.deleteIncaricoProfWithoutNumInca(codGara, codLotto, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione della fase incarichi professionali. codgara = " + codGara + " codLotto = "
					+ codLotto + " num= " + num, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseVariazioneAggiudicatari(FaseVariazioneAggiudicatariInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			Long numVaraggi = this.contrattiMapper.getMaxNumVaraggi(form.getCodGara(), form.getCodLotto());
			if (numVaraggi == null) {
				numVaraggi = 1L;
			} else {
				numVaraggi++;
			}
			form.setNum(numVaraggi);
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			List<String> idPartecipantiList = this.contrattiMapper.getIdPartecipanteAggi(form.getCodGara(), form.getCodLotto(),1L);
			if(idPartecipantiList != null && idPartecipantiList.size() == 1){
				form.setIdPartecipante(idPartecipantiList.get(0));
			}
			this.contrattiMapper.insertFaseVariazioneAggiudicatari(form);
			// Trovo l'id per l'inserimento enlla w9fasi
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 21L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			String idScheda = lotto.getCig() + "_5_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 21L, id, idScheda, numAppa, null);
			risultato.setData(numVaraggi);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase collaudo. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseVariazioneAggiudicatari getFaseVariazioneAggiudicatari(Long codGara, Long codLotto, Long num) {
		ResponseFaseVariazioneAggiudicatari risultato = new ResponseFaseVariazioneAggiudicatari();
		risultato.setEsito(true);
		try {
			FaseVariazioneAggiudicatariEntry fase = this.contrattiMapper.getFaseVariazioneAggiudicatari(codGara, codLotto, num);
			if (fase.getCodImpresa() != null) {
				fase.setImpresa(this.getImpresa(fase.getCodImpresa()));
			}
			List<String> partecipanteList = this.contrattiMapper.getPartecipanteNomestAggi(codGara, codLotto, 1L);
			String parteciapante = null;
			if(partecipanteList != null && !partecipanteList.isEmpty()){
				parteciapante = partecipanteList.get(0);
			}
			fase.setPartecipante(parteciapante);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 21L, num));
			fase.setCodiceFase(21L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura fase collaudo. codgara = " + codGara + " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseVariazioneAggiudicatari(FaseVariazioneAggiudicatariInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseVariazioneAggiudicatari(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 21L, form.getNum());
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase collaudo. codgara = " + form.getCodGara() + " codLotto = "
					+ form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseVariazioneAggiudicatari(Long codGara, Long codLotto, Long num) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseVariazioneAggiudicatari(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 21L, num);

		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase conclusione semplificata contratto. codgara = "
					+ codGara + " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseFinanziamenti getFinanziamenti(Long codGara, Long codLotto, Long numAppa) {
		ResponseFinanziamenti risultato = new ResponseFinanziamenti();
		risultato.setEsito(true);
		try {
			List<FinanziamentiEntry> finanziamenti = this.contrattiMapper.getFinanziamenti(codGara, codLotto, numAppa);
			risultato.setData(finanziamenti);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura finanziamenti. codgara = " + codGara + " codLotto = " + codLotto
					+ " numAppa " + numAppa, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFinanziamenti(FinanziamentiInsertForm form) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(false);
		try {
			Long codGara = form.getCodGara();
			Long codLotto = form.getCodLotto();
			Long numAppa = form.getNumAppa();

			if (form.getFinanziamenti() == null) {
				throw new Exception("Lista null");
			}

			// delete di tutti i finanziamenti per codGara, codLotto, numAppa
			this.contrattiMapper.deleteFinanziamenti(codGara, codLotto, numAppa);

			Long idFinanziamento = this.contrattiMapper.getMaxIdFinanziamenti(codGara, codLotto, numAppa);
			if (idFinanziamento == null || idFinanziamento < 1) {
				idFinanziamento = 1L;
			} else {
				idFinanziamento++;
			}

			for (FinanziamentoInsertForm finanziamento : form.getFinanziamenti()) {
				this.contrattiMapper.insertFinanziamentoForm(codGara, codLotto, numAppa, idFinanziamento,
						finanziamento);
				idFinanziamento++;
			}
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error("Errore inaspettato nella modifica dei finanziamenti. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto() + " numAppa " + form.getNumAppa(), e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public BaseResponse deleteFinanziamento(Long codGara, Long codLotto, Long num, Long numFina) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFinanziamento(codGara, codLotto, num, numFina);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione finanziamento. codgara = " + codGara + " codLotto = "
					+ codLotto + " num= " + num + " numFina = " + numFina, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseSchedaSicurezza getSchedaSicurezza(Long codGara, Long codLotto, Long num) {
		ResponseSchedaSicurezza risultato = new ResponseSchedaSicurezza();
		risultato.setEsito(true);
		try {
			SchedaSicurezzaEntry sicurezza = this.contrattiMapper.getschedaSicurezza(codGara, codLotto, num);
			risultato.setData(sicurezza);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura scheda sicurezza. codgara = " + codGara + " codLotto = " + codLotto);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public BaseResponse updateSchedaSicurezza(SchedaSicurezzaInsertForm form) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateSchedaSicurezza(form);
		} catch (Exception e) {
			logger.error("Errore inaspettato la modifica della scheda sicurezza. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseInsert insertSchedaSicurezza(SchedaSicurezzaInsertForm form) {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			Long numSic = this.contrattiMapper.getMaxNumSic(form.getCodGara(), form.getCodLotto());
			if (numSic == null) {
				numSic = 1L;
			} else {
				numSic++;
			}
			form.setNum(numSic);
			this.contrattiMapper.insertSchedaSicurezza(form);
			risultato.setData(numSic);
		} catch (Exception e) {
			logger.error("Errore inaspettato l'inserimento della scheda sicurezza. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public BaseResponse deleteSchedaSicurezza(Long codGara, Long codLotto, Long num) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteSchedaSicurezza(codGara, codLotto, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione scheda sicurezza. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseMisureAggiuntiveSicurezza getMisureAggiuntiveSicurezza(Long codGara, Long codLotto, Long codFase,
			Long numeroProgressivoFase) {
		ResponseMisureAggiuntiveSicurezza risultato = new ResponseMisureAggiuntiveSicurezza();
		risultato.setEsito(true);
		try {
			MisureAggiuntiveSicurezzaEntry sicurezza = this.contrattiMapper.getMisureAggiuntiveSicurezza(codGara,
					codLotto, numeroProgressivoFase);
			if (sicurezza != null) {

				if (sicurezza.getCodiceImpresa() != null) {
					sicurezza.setImpresa(this.getImpresa(sicurezza.getCodiceImpresa()));
				}

				List<DocumentoFaseEntry> documents = this.contrattiMapper.getDocumentoFase(codGara, codLotto, codFase,
						numeroProgressivoFase);
				List<ExistingDocumentoFaseEntry> existingDocuments = new ArrayList<>();
				if (documents != null) {
					for (DocumentoFaseEntry d : documents) {
						ExistingDocumentoFaseEntry existingDocument = new ExistingDocumentoFaseEntry();

						existingDocument.setCodGara(d.getCodGara());
						existingDocument.setCodLotto(d.getCodLotto());
						existingDocument.setProgressivoDocumento(d.getProgressivoDocumento());
						existingDocument.setTitolo(d.getTitolo());

						if (d.getFileAllegato() != null) {
							existingDocument.setTipoFile("pdf");
						}
						existingDocuments.add(existingDocument);
					}
				}
				sicurezza.setExistingDocuments(existingDocuments);
			}
			risultato.setData(sicurezza);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura scheda misure aggiuntive sicurezza. codgara = " + codGara
					+ " codLotto = " + codLotto);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateSchedaMisureaggiuntiveSicurezza(MisureAggiuntivesicurezzaUpdateForm form)
			throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateMisureAggiuntiveSicurezza(form);

			if (form.getExistingDocuments() != null && form.getExistingDocuments().size() > 0) {
				List<DocumentoFaseEntry> savedDocs = this.contrattiMapper.getDocumentiFaseWithoutAllegato(
						form.getCodGara(), form.getCodLotto(), form.getCodFase(), form.getNumIniz());
				if (savedDocs != null && savedDocs.size() > 0) {
					for (DocumentoFaseEntry savedDoc : savedDocs) {
						boolean isPresent = form.getExistingDocuments().stream()
								.map(ExistingDocumentoFaseEntry::getProgressivoDocumento)
								.anyMatch(numDoc -> savedDoc.getProgressivoDocumento() == numDoc);
						if (!isPresent) {
							this.contrattiMapper.deleteDocumentoFaseByNumDoc(form.getCodGara(), form.getCodLotto(),
									form.getCodFase(), form.getNumIniz(), savedDoc.getProgressivoDocumento());
						}
					}
				}
			} else {
				// delete all
				this.contrattiMapper.deleteDocumentoFase(form.getCodGara(), form.getCodLotto(), form.getCodFase(),
						form.getNumIniz());
			}

			if (form.getNewDocuments() != null) {

				Long numDoc = this.contrattiMapper.getMaxNumDocFase(form.getCodGara(), form.getCodLotto(),
						form.getCodFase(), form.getNumIniz());
				numDoc++;

				for (DocumentoFaseInsertForm d : form.getNewDocuments()) {
					d.setCodGara(form.getCodGara());
					d.setCodLotto(form.getCodLotto());
					d.setCodFase(form.getCodFase());
					d.setNumeroProgressivoFase(form.getNumIniz());
					d.setProgressivoDocumento(numDoc);

					if (StringUtils.isNotBlank(d.getBinary())) {
						byte[] dedcodedFile = Base64.getDecoder().decode(d.getBinary().getBytes());
						d.setFileAllegato(dedcodedFile);
					}

					this.contrattiMapper.insertDocumentoFase(d);
					numDoc++;
				}
			}
		} catch (Exception e) {
			logger.error("Errore inaspettato la modifica della scheda misure aggiuntive sicurezza. codgara = "
					+ form.getCodGara() + " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertMisureAggiuntiveSicurezza(MisureAggiuntivesicurezzaInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			Long numMiss = this.contrattiMapper.getMaxNumMiss(form.getCodGara(), form.getCodLotto());
			if (numMiss == null) {
				numMiss = 1L;
			} else {
				numMiss++;
			}
			form.setNum(numMiss);
			this.contrattiMapper.insertMisureAggiuntiveSicurezza(form);
			if (form.getDocuments() != null) {
				Long num = 1L;
				for (DocumentoFaseInsertForm d : form.getDocuments()) {
					d.setCodGara(form.getCodGara());
					d.setCodLotto(form.getCodLotto());
					d.setCodFase(form.getCodFase());
					d.setNumeroProgressivoFase(form.getNum());
					d.setProgressivoDocumento(num);

					if (StringUtils.isNotBlank(d.getBinary())) {
						byte[] dedcodedFile = Base64.getDecoder().decode(d.getBinary().getBytes());
						d.setFileAllegato(dedcodedFile);
					}

					this.contrattiMapper.insertDocumentoFase(d);
					num++;
				}
			}
			risultato.setData(numMiss);
		} catch (Exception e) {
			logger.error("Errore inaspettato l'inserimento della scheda misure aggiuntive di sicurezza. codgara = "
					+ form.getCodGara() + " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteMisureAggiuntiveSicurezza(Long codGara, Long codLotto, Long codFase, Long num)
			throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteMisureAggiuntiveSicurezza(codGara, codLotto, num);
			this.contrattiMapper.deleteDocumentoFase(codGara, codLotto, codFase, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione scheda sicurezza. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			throw e;
		}
		return risultato;
	}


	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseImpresa(FaseImpresaInsertForm form, boolean isSmartCig) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			Long numRaggruppamento = null;
			
			if(form.getTipologiaSoggetto() != null && form.getTipologiaSoggetto() == 2L) {
				if(form.getImprese() == null || (form.getImprese() != null && form.getImprese().size() < 2)) {
					risultato.setEsito(false);
					risultato.setErrorData(ERROR_NUM_IMPRESE);
					return risultato;
				}
			}
			
			
			// Il numRaggruppamento collega le imprese per la presentazione klato client nel
			// caso di tipologiaSoggetto che prevede più imprese a consorzio
			if (form.getTipologiaSoggetto() != null && !form.getTipologiaSoggetto().equals(3L)) {
				numRaggruppamento = this.contrattiMapper.getMaxNumRaggruppamento(form.getCodGara(), form.getCodLotto());
				if (numRaggruppamento == null || numRaggruppamento < 1) {
					numRaggruppamento = 1L;
				} else {
					numRaggruppamento++;
				}
			}

			List<RuoloImpresa> imprese = form.getImprese();
			// Inserisco le imprese calcolando l'id della tabella e usando l'eventuale
			// raggruppamento calcolato
			for (RuoloImpresa i : imprese) {
				Long id = this.contrattiMapper.getMaxIdFaseImpresa(form.getCodGara(), form.getCodLotto());
				if (id == null || id < 1) {
					id = 1L;
				} else {
					id++;
				}
				risultato.setData(id);
				this.contrattiMapper.insertFaseImpresa(form.getCodGara(), form.getCodLotto(), id, i.getCodImpresa(),
						form.getTipologiaSoggetto(), i.getRuolo(), form.getPartecipante(), numRaggruppamento);

			}
			// Controllo se già esiste il record nella w9Fasi, in caso contrario inserisco
			// la fase.
			Long numAppa = getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			List<FaseEntry> fasi = this.contrattiMapper.getFasiLotto(form.getCodGara(), form.getCodLotto(), numAppa);
			boolean alreadyExist = false;
			for (FaseEntry f : fasi) {
				if (f.getFase() == 101L) {
					alreadyExist = true;
				}
			}

			if (!alreadyExist) {
				LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
				Long idNumFase = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 101L);
				if (idNumFase == null) {
					idNumFase = 1L;
				} else {
					idNumFase++;
				}

				String idScheda = lotto.getCig() + "_101_" + calcolaIdScheda(idNumFase);
				// PER FASE IMPRESE NUM_APPA = 1 sempre. 21/10/2020
				this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 101L, idNumFase, idScheda, 1L,
						null);
			}
			if (isSmartCig) {
				if (form.isUpdateDaexport()) {
					this.contrattiMapper.setLottiDaexport(3L, form.getCodGara());
				} else {
					this.contrattiMapper.setLottiDaexport(1L, form.getCodGara());
				}
			}

		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase aggiudicazione. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert updateFaseImpresa(FaseImpresaInsertForm form, boolean isSmartCig) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			if(form.getTipologiaSoggetto() != null && form.getTipologiaSoggetto() == 2L) {
				if(form.getImprese() == null || (form.getImprese() != null && form.getImprese().size() < 2)) {
					risultato.setEsito(false);
					risultato.setErrorData(ERROR_NUM_IMPRESE);
					return risultato;
				}
			}
			
			deleteFaseImpresa(form.getCodGara(), form.getCodLotto(), form.getNum(), form.getNumRagg(), true, isSmartCig,
					false);
			return insertFaseImpresa(form, isSmartCig);

		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase aggiudicazione. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
	}

	public ResponseFaseImprese getFaseImprese(Long codGara, Long codLotto, Long numAppa) {

		ResponseFaseImprese risultato = new ResponseFaseImprese();
		risultato.setEsito(true);
		try {
			List<FaseImpresaEntry> imprese = new ArrayList<FaseImpresaEntry>();
			if (numAppa == null) {
				numAppa = getNumAppa(codLotto, codLotto, false);
			}

			List<RecordFaseImpreseEntry> records = this.contrattiMapper.getFaseImprese(codGara, codLotto);
			List<Long> parsedNumRagg = new ArrayList<Long>();
			boolean pubblicata = isFasePubblicata(codGara, codLotto, 101L, 1L);
			// Itero tra i record della fase impresa e li raggruppo per la presentazione al
			// client
			for (RecordFaseImpreseEntry record : records) {
				if (record.getNumRaggruppamento() == null || !parsedNumRagg.contains(record.getNumRaggruppamento())) {
					FaseImpresaEntry entry = new FaseImpresaEntry();
					entry.setCodGara(record.getCodGara());
					entry.setCodLotto(record.getCodLotto());
					entry.setNum(record.getNum());
					entry.setPartecipante(record.getPartecipante());
					entry.setTipologiaSoggetto(record.getTipologiaSoggetto());
					entry.setNumRagg(record.getNumRaggruppamento());
					List<RuoloImpresa> ruoliImpresa = new ArrayList<RuoloImpresa>();
					RuoloImpresa rI = new RuoloImpresa();
					rI.setCodImpresa(record.getCodImpresa());
					rI.setImpresa(this.getImpresa(record.getCodImpresa()));
					rI.setRuolo(record.getRuolo());
					ruoliImpresa.add(rI);
					if (record.getNumRaggruppamento() != null) {
						for (RecordFaseImpreseEntry r : records) {
							if (record.getNumRaggruppamento() == r.getNumRaggruppamento()
									&& !(record.getCodGara() == r.getCodGara()
											&& record.getCodLotto() == r.getCodLotto()
											&& record.getNum() == r.getNum())) {
								rI = new RuoloImpresa();
								rI.setCodImpresa(r.getCodImpresa());
								rI.setImpresa(this.getImpresa(r.getCodImpresa()));
								rI.setRuolo(r.getRuolo());
								ruoliImpresa.add(rI);
								parsedNumRagg.add(r.getNumRaggruppamento());
							}

						}
					}
					entry.setImprese(ruoliImpresa);
					entry.setPubblicata(pubblicata);
					imprese.add(entry);
				}
			}

			risultato.setData(imprese);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase aggiudicazione. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseFaseImprese dettaglioFaseImprese(Long codGara, Long codLotto, Long num, Long numRagg) {

		ResponseFaseImprese risultato = new ResponseFaseImprese();
		risultato.setEsito(true);
		try {
			List<FaseImpresaEntry> imprese = new ArrayList<FaseImpresaEntry>();
			List<RecordFaseImpreseEntry> records = new ArrayList<RecordFaseImpreseEntry>();
			// Se è valorizzato il numero raggruppamento la lista delle imprese è costituita
			// da più record
			if (numRagg != null) {
				records = this.contrattiMapper.getFaseImpreseByRagg(codGara, codLotto, numRagg);
			} else {
				records.add(this.contrattiMapper.dettaglioFaseImprese(codGara, codLotto, num));
			}
			RecordFaseImpreseEntry record = records.get(0);
			FaseImpresaEntry entry = new FaseImpresaEntry();
			if(record != null) {
				entry.setCodGara(record.getCodGara());
				entry.setCodLotto(record.getCodLotto());
				entry.setNum(record.getNum());
				entry.setPartecipante(record.getPartecipante());
				entry.setTipologiaSoggetto(record.getTipologiaSoggetto());
				entry.setNumRagg(record.getNumRaggruppamento());
				List<RuoloImpresa> ruoliImpresa = new ArrayList<RuoloImpresa>();
				RuoloImpresa rI = new RuoloImpresa();
				rI.setCodImpresa(record.getCodImpresa());
				rI.setRuolo(record.getRuolo());
				ruoliImpresa.add(rI);
				// Se è valorizzato il numero raggruppamento preparo la lista delle imprese per
				// il client
				if (record.getNumRaggruppamento() != null) {
					for (RecordFaseImpreseEntry r : records) {
						if (record.getNumRaggruppamento() == r.getNumRaggruppamento()
								&& !(record.getCodGara() == r.getCodGara() && record.getCodLotto() == r.getCodLotto()
										&& record.getNum() == r.getNum())) {
							rI = new RuoloImpresa();
							rI.setCodImpresa(r.getCodImpresa());
							rI.setRuolo(r.getRuolo());
							ruoliImpresa.add(rI);
						}

					}
				}
				entry.setImprese(ruoliImpresa);
			}
			
			
			imprese.add(entry);
			risultato.setData(imprese);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in dettaglio fase imprese. codgara = " + codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	/**
	 * Metodo che elimina la fase delle imprese partecipanti oppure elimina la
	 * singola impresa all'interno della fase Se sono valorizzati solamente il
	 * codGara e codLotto elimina tutte le imprese della fase e elimina la fase
	 * stessa. Se sono valorizzati anche num ed eventualmente numRagg elimina
	 * solamente l'impresa interessata. L'eliminazione della fase dipende anche dal
	 * parametro forUpdate che in fase di deleteFaseImpresa del controller è false e
	 * in fase di update impresa è true
	 * 
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseImpresa(Long codGara, Long codLotto, Long num, Long numRagg, boolean forUpdate,
			boolean isSmartCig, Boolean updateDaexport) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			// Se è valorizzato il numero raggruppamento cancello tutti i record associati
			// tra di loro, altrimenti il record singolo evidenziato dal num
			if (numRagg != null) {
				this.contrattiMapper.deleteFaseImpresaByRaggruppamento(codGara, codLotto, numRagg);
			} else if (num != null) {
				this.contrattiMapper.deleteFaseImpresaByNum(codGara, codLotto, num);
			} else {
				this.contrattiMapper.deleteAllImpreseFase(codGara, codLotto);
			}
			Long counter = this.contrattiMapper.countFaseImprese(codGara, codLotto);
			if (counter == 0 && !forUpdate) {
				this.contrattiMapper.deleteW9Fase(codGara, codLotto, 101L, 1L);
			}

			if (isSmartCig) {
				if (updateDaexport) {
					this.contrattiMapper.setLottiDaexport(3L, codGara);
				} else {
					this.contrattiMapper.setLottiDaexport(1L, codGara);
				}
			}

		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase imprese. codgara = " + codGara + " codLotto = "
					+ codLotto + " num= " + num + " numRagg = " + numRagg, e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseAdesioneAccordo(FaseAdesioneAccordoQuadroInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {

			GaraEntry gara = this.contrattiMapper.dettaglioGara(form.getCodGara());
			String cig = gara.getCigQuadro();
			if (cig == null) {
				risultato.setEsito(false);
				risultato.setErrorData(ResponseInizFaseAdesioneAccordoQuadro.INIZ_ADESIONE_GARA_NOT_FOUND);
				return risultato;
			}
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNum(numAppa);
			this.contrattiMapper.insertFaseAdesioneAccordo(form);
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			// Trovo l'id per l'inserimento nella w9fasi
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 12L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			String idScheda = lotto.getCig() + "_12_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 12L, id, idScheda, numAppa, null);
			ResponseConsultaGaraIdGara response = anacrestclientmanager.consultaGara(cig);
			if (response != null && response.isEsito()) {
				SchedaType scheda = response.getData();
				if (hasAggiudicazione(scheda)) {
					List<SoggAggiudicatarioType> aggiudicatari = scheda.getDatiScheda().getSchedaCompleta().get(0)
							.getAggiudicazione().getAggiudicatari();
					if (aggiudicatari != null) {
						Long counter = 1L;
						for (SoggAggiudicatarioType aggiudicatario : aggiudicatari) {

							String codImp = getCodImpresa(aggiudicatario.getCODICEFISCALEAGGIUDICATARIO(),
									gara.getCodiceStazioneAppaltante(), scheda.getAggiudicatari());
							String codImpAus = getCodImpresa(aggiudicatario.getCFAUSILIARIA(),
									gara.getCodiceStazioneAppaltante(), scheda.getAggiudicatari());

							ImpresaAggiudicatariaInsertForm impInsertForm = new ImpresaAggiudicatariaInsertForm();
							impInsertForm.setCodGara(form.getCodGara());
							impInsertForm.setCodImpAus(codImpAus);
							impInsertForm.setCodImpresa(codImp);
							impInsertForm.setCodLotto(form.getCodLotto());
							impInsertForm.setNumAppa(numAppa);
							impInsertForm.setNumAggi(counter);
							impInsertForm.setFlagAvvallamento(aggiudicatario.getFLAGAVVALIMENTO());
							impInsertForm.setIdGruppo(aggiudicatario.getIDGRUPPO() == null ? null
									: aggiudicatario.getIDGRUPPO().longValue());
							impInsertForm
									.setImportoAggiudicazione(aggiudicatario.getIMPORTOAGGIUDICAZIONE() == null ? null
											: Double.valueOf(aggiudicatario.getIMPORTOAGGIUDICAZIONE()));
							impInsertForm.setIdTipoAgg(aggiudicatario.getIDTIPOAGG() == null ? null
									: new Long(aggiudicatario.getIDTIPOAGG()));
							impInsertForm.setNumAggi(counter);
							impInsertForm.setOffertaAumento(aggiudicatario.getPERCOFFAUMENTO() == null ? null
									: Double.valueOf(aggiudicatario.getPERCOFFAUMENTO()));
							impInsertForm.setRibassoAggiudicazione(aggiudicatario.getPERCRIBASSOAGG() == null ? null
									: Double.valueOf(aggiudicatario.getPERCRIBASSOAGG()));
							impInsertForm.setRuolo(
									aggiudicatario.getRUOLO() == null ? null : new Long(aggiudicatario.getRUOLO()));
							this.contrattiMapper.insertImpresaAggiudicataria(impInsertForm);
							counter++;
						}
					} else {
						List<Long> attoPubb = this.contrattiMapper.checkIfGaraHAsAttoFaseAggiudicazione(form.getCodGara(),
								form.getCodLotto());
						if (attoPubb != null && attoPubb.size() > 0) {

							List<ImpresaAggiudicatariaAttoEntry> impreseAtto = this.contrattiMapper
									.getImpreseAggiudicatarieAtto(form.getCodGara(), attoPubb.get(0));
							ImpresaAggiudicatariaInsertForm insertForm = null;
							List<String> codimpInseriti = new ArrayList<String>();
							for (ImpresaAggiudicatariaAttoEntry imp : impreseAtto) {
								insertForm = new ImpresaAggiudicatariaInsertForm();
								insertForm.setCodGara(form.getCodGara());
								insertForm.setCodLotto(form.getCodLotto());
								insertForm.setNumAppa(numAppa);
								insertForm.setNumAggi(imp.getNumAggi());
								insertForm.setIdTipoAgg(imp.getIdTipoAgg());
								insertForm.setRuolo(imp.getRuolo());
								insertForm.setCodImpresa(imp.getCodImpresa());
								insertForm.setIdGruppo(imp.getIdGruppo());
								if(imp.getCodImpresa() != null && !codimpInseriti.contains(imp.getCodImpresa())){
									codimpInseriti.add(imp.getCodImpresa());
									this.contrattiMapper.insertImpresaAggiudicataria(insertForm);
								}
							}
						}
					}
				}
			}
			risultato.setData(1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase adesione accordo quadro. codgara = "
					+ form.getCodGara() + " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseAdesioneAccordoQuadro getFaseAdesioneAccordo(Long codGara, Long codLotto, Long num) {
		ResponseFaseAdesioneAccordoQuadro risultato = new ResponseFaseAdesioneAccordoQuadro();
		risultato.setEsito(true);
		try {
			FaseAdesioneAccordoQuadroEntry fase = this.contrattiMapper.getFaseAdesioneAccordo(codGara, codLotto, num);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 12L, 1L));
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase adesione accordo quadro. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseAdesioneAccordoQuadro(FaseAdesioneAccordoQuadroInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseAdesioneAccordo(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 12L, 1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase adesione accordo quadro. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseAdesioneAccordo(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseAdesioneAccordo(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 12L, num);
			// delete imprese aggiudicatarie
			this.contrattiMapper.deleteImpreseAggiudicatarie(codGara, codLotto, num);
			this.contrattiMapper.deleteFinanziamenti(codGara, codLotto, num);
			List<String> sezioni = getSezioneIncaricoByFase(12L);
			for (String sezione : sezioni) {
				this.contrattiMapper.deleteIncarichiProf(codGara, codLotto, sezione, num);
			}
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase adesione accordo quadro. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseConclusioneContratto(FaseConclusioneInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			Long num = this.contrattiMapper.getMaxNumConc(form.getCodGara(), form.getCodLotto());
			if (num == null) {
				num = 1L;
			} else {
				num++;
			}
			form.setNum(num);
			form.setNumAppa(numAppa);
			this.contrattiMapper.insertFaseConclusioneContratto(form);
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 4L);
			// Trovo l'id per l'inserimento enlla w9fasi
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			String idScheda = lotto.getCig() + "_4_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 4L, id, idScheda, numAppa, null);
			risultato.setData(num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase conclusione contratto accordo quadro. codgara = "
					+ form.getCodGara() + " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseConclusioneContratto getFaseConclusioneContratto(Long codGara, Long codLotto, Long num) {
		ResponseFaseConclusioneContratto risultato = new ResponseFaseConclusioneContratto();
		risultato.setEsito(true);
		try {
			FaseConclusioneEntry fase = this.contrattiMapper.getFaseConclusioneContratto(codGara, codLotto, num);
			Long countFaseIniziale = this.contrattiMapper.countFaseIniziale(codGara, codLotto);
			FaseInizialeEsecuzioneEntry iniz = this.contrattiMapper.getFaseInizialeEsecuzione(codGara, codLotto, 1L);
			Long ggProroga = this.contrattiMapper.ggProrogaModificaContrattuale(codGara, codLotto);
			ggProroga = ggProroga != null ? ggProroga : 0L;

			if(fase.getDataUltimazione() != null && iniz.getDataTermine() != null){
				LocalDate dataUltimazione = fase.getDataUltimazione().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate dataTermine = iniz.getDataTermine().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				long giorni = ChronoUnit.DAYS.between(dataTermine,dataUltimazione) + ggProroga;
				fase.setScostDataFine(giorni);
			}
			if(fase.getDataUltimazione() != null && iniz.getDataInizioProg() != null){
				LocalDate dataUltimazione = fase.getDataUltimazione().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate datInizio = iniz.getDataInizioProg().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				long giorni = ChronoUnit.DAYS.between(datInizio,dataUltimazione);
				fase.setGgFineEse(giorni);
			}

			fase.setFaseInizialeExists(countFaseIniziale != null && countFaseIniziale > 0);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 4L, num));
			fase.setCodiceFase(4L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase conclusione contratto. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseInizFaseConclusioneContratto getInizFaseConclusioneContratto(Long codGara, Long codLotto) {
		ResponseInizFaseConclusioneContratto risultato = new ResponseInizFaseConclusioneContratto();
		InizFaseConclusioneContrattoEntry data = new InizFaseConclusioneContrattoEntry();
		risultato.setEsito(true);
		try {
			Long numAppa = getNumAppa(codGara, codLotto, false);
			FaseInizialeEsecuzioneEntry faseIniz = this.contrattiMapper.getFaseInizialeEsecuzioneByNumAppa(codGara,
					codLotto, numAppa);
			Long fasiIniz = this.contrattiMapper.checkExistsFasiIniziali(codGara,
					codLotto, numAppa);
			List<FaseAvanzamentoEntry> fasiAvanzamento = this.contrattiMapper.getFasiAvanzamentoLotto(codGara, codLotto,
					numAppa);
			data.setFaseInizialeExists(fasiIniz != null && fasiIniz > 0);
			if (faseIniz != null) {
				data.setDataTermine(faseIniz.getDataTermine());
				data.setDataVerbale(faseIniz.getDataVerbaleDef());
			}
			Long numGiorniProroga = 0L;
			if (fasiAvanzamento != null) {
				for (FaseAvanzamentoEntry fase : fasiAvanzamento) {
					if (fase.getNumGiorniProroga() != null) {
						numGiorniProroga += fase.getNumGiorniProroga();
					}
				}
			}

			data.setNumGiorniProroga(numGiorniProroga);
			risultato.setData(data);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura dati per inizializzazione fase conclusione contratto. codgara = "
							+ codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseInizFaseAggiudicazione getInizFaseAggiudicazione(Long codGara, Long codLotto) {
		ResponseInizFaseAggiudicazione risultato = new ResponseInizFaseAggiudicazione();
		InizFaseAggiudicazione data = new InizFaseAggiudicazione();
		risultato.setEsito(true);
		try {
			
			String valore = this.contrattiMapper.getConfigurazione("W9", "AggiudicazioneInibita");
			if (valore != null && ("S".equals(valore) || "W".equals(valore))) {
				Long pubblicazioni = this.contrattiMapper.getNumeroPubblicazioniAttiEsito(codGara);
				if(pubblicazioni== null || pubblicazioni == 0L) {
					data.setInibita(valore);
				}
			}
			List<Long> attoPubb = this.contrattiMapper.checkIfGaraHAsAttoFaseAggiudicazione(codGara, codLotto);
			if (attoPubb != null && attoPubb.size() > 0) {
				FullDettaglioAttoEntry atto = this.contrattiMapper.getAtto(codGara, attoPubb.get(0));
				if (atto.getDataVerbAggiudicazione() != null) {
					data.setDataVerbaleAgg(atto.getDataVerbAggiudicazione());
				}

				int count = this.contrattiMapper.checkAttoLotto(codGara, attoPubb.get(0));
				if (count > 0) {
					if (atto.getImportoAggiudicazione() != null) {
						data.setImportoAgg(atto.getImportoAggiudicazione());
					}
					if (atto.getPercRibassoAgg() != null) {
						data.setPercRibassoAgg(atto.getPercRibassoAgg());
					}
				}
			}
			risultato.setData(data);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura dati per inizializzazione fase aggiudicazione. codgara = "
					+ codGara + " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseInizFaseAggiudicazioneSemp getInizFaseAggiudicazioneSemp(Long codGara, Long codLotto) {
		ResponseInizFaseAggiudicazioneSemp risultato = new ResponseInizFaseAggiudicazioneSemp();
		InizFaseAggiudicazioneSemp data = new InizFaseAggiudicazioneSemp();
		risultato.setEsito(true);
		try {
			String valore = this.contrattiMapper.getConfigurazione("W9", "AggiudicazioneInibita");
			if (valore != null && ("S".equals(valore) || "W".equals(valore))) {
				Long pubblicazioni = this.contrattiMapper.getNumeroPubblicazioniAttiEsito(codGara);
				if(pubblicazioni== null || pubblicazioni == 0L) {
					data.setInibita(valore);
				}
			}

			List<Long> attoPubb = this.contrattiMapper.checkIfGaraHAsAttoFaseAggiudicazione(codGara, codLotto);
			if (attoPubb != null && attoPubb.size() > 0) {
				FullDettaglioAttoEntry atto = this.contrattiMapper.getAtto(codGara, attoPubb.get(0));
				if (atto.getDataVerbAggiudicazione() != null) {
					data.setDataVerbaleAgg(atto.getDataVerbAggiudicazione());
				}

				int count = this.contrattiMapper.checkAttoLotto(codGara, attoPubb.get(0));
				if (count > 0) {
					if (atto.getImportoAggiudicazione() != null) {
						data.setImportoAgg(atto.getImportoAggiudicazione());
					}
					if (atto.getPercRibassoAgg() != null) {
						data.setPercRibassoAgg(atto.getPercRibassoAgg());
					}
				}
			}
			risultato.setData(data);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura dati per inizializzazione fase aggiudicazione semplificata. codgara = "
							+ codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseInizFaseAdesioneAccordoQuadro getInizFaseAdesioneAccordoQuadro(Long codGara, Long codLotto) {
		ResponseInizFaseAdesioneAccordoQuadro risultato = new ResponseInizFaseAdesioneAccordoQuadro();
		InizFaseAdesioneAccordoQuadro data = new InizFaseAdesioneAccordoQuadro();
		risultato.setEsito(true);
		try {

			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
			String cig = gara.getCigQuadro();
			if (cig == null) {
				risultato.setEsito(false);
				risultato.setErrorData(ResponseInizFaseAdesioneAccordoQuadro.INIZ_ADESIONE_GARA_NOT_FOUND);
				return risultato;
			}

			String valore = this.contrattiMapper.getConfigurazione("W9", "AggiudicazioneInibita");
			if (valore != null && ("S".equals(valore) || "W".equals(valore))) {
				Long pubblicazioni = this.contrattiMapper.getNumeroPubblicazioniAttiEsito(codGara);
				if(pubblicazioni== null || pubblicazioni == 0L) {
					data.setInibita(valore);
				}
			}
			
			if (valore != null && "S".equals(valore)) {
				List<Long> attoPubb = this.contrattiMapper.checkIfGaraHAsAttoFaseAggiudicazione(codGara, codLotto);
				if (attoPubb != null && attoPubb.size() > 0) {
					FullDettaglioAttoEntry atto = this.contrattiMapper.getAtto(codGara, attoPubb.get(0));
					if (atto.getDataVerbAggiudicazione() != null) {
						data.setDataVerbaleAgg(atto.getDataVerbAggiudicazione());
					}

				}
			}

			ResponseConsultaGaraIdGara response = anacrestclientmanager.consultaGara(cig);
			if (response != null && response.isEsito()) {
				SchedaType scheda = response.getData();
				if (hasAggiudicazione(scheda)) {
					AppaltoType appalto = scheda.getDatiScheda().getSchedaCompleta().get(0).getAggiudicazione()
							.getAppalto();
					data.setPercOffAumento(
							appalto.getPERCOFFAUMENTO() == null ? null : new Double(appalto.getPERCOFFAUMENTO()));
					data.setPercRibassoAgg(
							appalto.getPERCRIBASSOAGG() == null ? null : new Double(appalto.getPERCOFFAUMENTO()));
					data.setDataVerbaleAgg(appalto.getDATAVERBAGGIUDICAZIONE() == null ? null
							: appalto.getDATAVERBAGGIUDICAZIONE().toGregorianCalendar().getTime());
					data.setFlagSubappalto(
							appalto.getFLAGRICHSUBAPPALTO() == null ? null : appalto.getFLAGRICHSUBAPPALTO());
					data.setImportoAgg(appalto.getIMPORTOAGGIUDICAZIONE() == null ? null
							: new Double(appalto.getIMPORTOAGGIUDICAZIONE()));
					
				} else {
					risultato.setEsito(false);
					risultato
							.setErrorData(ResponseInizFaseAdesioneAccordoQuadro.INIZ_ADESIONE_AGGIUDICAZIONE_NOT_FOUND);
					return risultato;
				}
				risultato.setData(data);
				return risultato;
			} else {
				risultato.setEsito(false);
				if (StringUtils.isNotBlank(response.getErrorData())) {
					String errorData = response.getErrorData();
					logger.error("Errore durante la richiesta di consulta gara per cig [{}] {}", cig, errorData);

					risultato.setErrorData(errorData);
					if (errorData.startsWith("SIMOG_RIC_004")) {
						risultato.setEsito(true);
						risultato.setErrorData(ResponseInizFaseAdesioneAccordoQuadro.INIZ_ADESIONE_ERRORE_SIMOG_004);
					}
				} else {
					risultato.setErrorData(ResponseInizFaseAdesioneAccordoQuadro.INIZ_ADESIONE_GARA_NOT_FOUND);
				}
				return risultato;
			}

		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura dati per inizializzazione fase adesione acccordo quadro. codgara = "
							+ codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseInizFaseInizialeContratto getInizFaseInizialeContratto(Long codGara, Long codLotto) {
		ResponseInizFaseInizialeContratto risultato = new ResponseInizFaseInizialeContratto();
		InizFaseInizialeContrattoEntry data = new InizFaseInizialeContrattoEntry();
		risultato.setEsito(true);
		try {

			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, codLotto);
			Long numAppa = getNumAppa(codGara, codLotto, false);

			FaseAggiudicazioneEntry faseAggiudicazione = this.contrattiMapper.getFaseAggiudicazione(codGara, codLotto,
					numAppa);
			List<FaseCantieriEntry> fasiCantiere = this.contrattiMapper.getFasiCantiere(codGara, codLotto, numAppa);
			if ("L".equalsIgnoreCase(lotto.getTipologia()) && faseAggiudicazione != null
					&& faseAggiudicazione.getImportoAggiudicazione() != null
					&& faseAggiudicazione.getImportoAggiudicazione() != 0
					&& faseAggiudicazione.getImportoAggiudicazione() > 1500000L) {
				data.setIncontriVigilVisible(true);
			} else {
				data.setIncontriVigilVisible(false);
			}
			if (fasiCantiere != null && fasiCantiere.size() > 0) {
				data.setDataVerbale(fasiCantiere.get(0).getDataInizio());
			} else {
				data.setDataVerbale(null);
			}
			risultato.setData(data);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura dati per inizializzazione fase iniziale contratto. codgara = "
					+ codGara + " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseConclusioneContratto(FaseConclusioneInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			if(StringUtils.isBlank(form.getInterruzioneAnticipata())) {
				this.contrattiMapper.updateFaseConclusioneContrattoWithoutIntantic(form);
			} else {
				this.contrattiMapper.updateFaseConclusioneContratto(form);
			}
			
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 4L, 1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase conclusione contratto. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseConclusioneContratto(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseConclusioneContratto(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 4L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase conclusione contratto. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseConclusioneAffidamentiDiretti(FaseConclusioneAffidamentiDirettiInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			
			this.contrattiMapper.updateFaseAffidamentiDirettiIniz(form);
			this.contrattiMapper.updateFaseAffidamentiDirettiAvan(form);
			this.contrattiMapper.updateFaseAffidamentiDirettiConc(form);
			this.insertW9AssociazioneCampi(form.getCodGara(), form.getCodLotto(), 19L, form.getNum(), form.getAssociazioniPagamenti());
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 19L, 1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase conclusione affidamenti diretti. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseConclusioneAffidamentiDiretti(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseConclusioneContratto(codGara, codLotto, num);
			this.contrattiMapper.deleteFaseAvanzamento(codGara, codLotto, num);
			this.contrattiMapper.deleteFaseInizialeEsecuzione(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 19L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase conclusione affidamenti diretti. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseConclusioneAffidamentiDiretti(FaseConclusioneAffidamentiDirettiInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			
			
			FaseInizialeEsecuzioneInsertForm formIniz = new FaseInizialeEsecuzioneInsertForm();
			formIniz.setCodGara(form.getCodGara());
			formIniz.setCodLotto(form.getCodLotto());
			formIniz.setNum(1L);
			formIniz.setNumAppa(getNumAppa(form.getCodGara(), form.getCodLotto(), false));
			if (form.getDataVerbInizio() != null) {
				formIniz.setDataVerbInizio(form.getDataVerbInizio());
			}
			contrattiMapper.insertFaseInizialeEsecuzione(formIniz);

			
			
			FaseConclusioneInsertForm formConclusione = new FaseConclusioneInsertForm();
			formConclusione.setCodGara(form.getCodGara());
			formConclusione.setCodLotto(form.getCodLotto());
			formConclusione.setNum(1L);
			formConclusione.setNumAppa(getNumAppa(form.getCodGara(), form.getCodLotto(), false));
			if (form.getDataUltimazione() != null) {
				formConclusione.setDataUltimazione(form.getDataUltimazione());
			}
			contrattiMapper.insertFaseConclusioneContratto(formConclusione);
			

			
			FaseAvanzamentoInsertForm formAvanzamento = new FaseAvanzamentoInsertForm();
			formAvanzamento.setCodGara(form.getCodGara());
			formAvanzamento.setCodLotto(form.getCodLotto());
			formAvanzamento.setNum(1L);
			formAvanzamento.setNumAppa(getNumAppa(form.getCodGara(), form.getCodLotto(), false));
			if (form.getImportoCertificato() != null) {
				formAvanzamento.setImportoCertificato(form.getImportoCertificato());
			}
			contrattiMapper.insertFaseAvanzamento(formAvanzamento);
			
			
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 4L);
			// Trovo l'id per l'inserimento enlla w9fasi
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			String idScheda = lotto.getCig() + "_19_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 19L, id, idScheda, numAppa, null);
			this.insertW9AssociazioneCampi(form.getCodGara(), form.getCodLotto(), 19L, 1L, form.getAssociazioniPagamenti());
			risultato.setData(1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase conclusione affidamenti diretti. codgara = "
					+ form.getCodGara() + " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseConclusioneAffidamentiDiretti getFaseConclusioneAffidamentiDiretti(Long codGara, Long codLotto, Long num) {
		ResponseFaseConclusioneAffidamentiDiretti risultato = new ResponseFaseConclusioneAffidamentiDiretti();
		risultato.setEsito(true);
		try {
			FaseConclusioneAffidamentiDirettiEntry fase = new FaseConclusioneAffidamentiDirettiEntry();
				
			Date dataInizio = this.contrattiMapper.getFaseAffidamentiDirettiIniz(codGara, codLotto, num);
			Double importoCertificato = this.contrattiMapper.getFaseAffidamentiDirettiAvan(codGara, codLotto, num);
			Date dataUltimazione = this.contrattiMapper.getFaseAffidamentiDirettiConc(codGara, codLotto, num);
			
			if(dataInizio != null) {
				fase.setDataVerbInizio(dataInizio);
			}
			if(dataUltimazione != null) {
				fase.setDataUltimazione(dataUltimazione);	
			}
			if(importoCertificato != null) {
				fase.setImportoCertificato(importoCertificato);
			}
			
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 19L, num));
			fase.setCodGara(codGara);
			fase.setCodLotto(codLotto);
			fase.setCodiceFase(19L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase conclusione affidamenti diretti. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseConclusioneSempContratto(FaseConclusioneSempInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			Long num = this.contrattiMapper.getMaxNumConc(form.getCodGara(), form.getCodLotto());
			if (num == null) {
				num = 1L;
			} else {
				num++;
			}
			form.setNum(num);
			this.contrattiMapper.insertFaseConclusioneSempContratto(form);
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			// Trovo l'id per l'inserimento enlla w9fasi
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 985L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			String idScheda = lotto.getCig() + "_985_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 985L, id, idScheda, 1L, null);
			risultato.setData(num);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in inserimento fase conclusione semplificata contratto accordo quadro. codgara = "
							+ form.getCodGara() + " codLotto = " + form.getCodLotto(),
					e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseConclusioneSempContratto getFaseConclusioneSempContratto(Long codGara, Long codLotto, Long num) {
		ResponseFaseConclusioneSempContratto risultato = new ResponseFaseConclusioneSempContratto();
		risultato.setEsito(true);
		try {
			FaseConclusioneSempEntry fase = this.contrattiMapper.getFaseConclusioneSempContratto(codGara, codLotto,
					num);
			Long countFaseIniziale = this.contrattiMapper.countFaseIniziale(codGara, codLotto);
			fase.setFaseInizialeExists(countFaseIniziale != null && countFaseIniziale > 0);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 985L, num));
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase conclusione semplificata contratto. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseConclusioneSempContratto(FaseConclusioneSempInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseConclusioneSempContratto(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 985L, 1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase conclusione semplificata contratto. codgara = "
					+ form.getCodGara() + " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseConclusioneSempContratto(Long codGara, Long codLotto, Long num) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseConclusioneSempContratto(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 985L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase conclusione semplificata contratto. codgara = "
					+ codGara + " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseCollaudo(FaseCollaudoInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			Long numColl = this.contrattiMapper.getMaxNumColl(form.getCodGara(), form.getCodLotto());
			if (numColl == null) {
				numColl = 1L;
			} else {
				numColl++;
			}
			form.setNum(numColl);
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			this.contrattiMapper.insertFaseCollaudo(form);
			// Trovo l'id per l'inserimento enlla w9fasi
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 5L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			String idScheda = lotto.getCig() + "_5_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 5L, id, idScheda, numAppa, null);

			risultato.setData(numColl);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase collaudo. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseCollaudo getFaseCollaudo(Long codGara, Long codLotto, Long num) {
		ResponseFaseCollaudo risultato = new ResponseFaseCollaudo();
		risultato.setEsito(true);
		try {
			FaseCollaudoEntry fase = this.contrattiMapper.getFaseCollaudo(codGara, codLotto, num);
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, codLotto);
			Double importoComplessivoAppalto = getImportoComplessivoAppalto(codGara, codLotto, fase.getNumAppa(), lotto, sqlMapper);
			Double importoFaseVariante = sqlMapper.getImportoUltimaFaseVariante(codGara, codLotto);
			if(fase.getImportoComplessivoAppalto() != null && importoFaseVariante != null){
				fase.setScostImpPrev(fase.getImportoComplessivoAppalto() - importoFaseVariante);
			} else if(fase.getImportoComplessivoAppalto() != null && importoComplessivoAppalto != null){
				fase.setScostImpPrev(fase.getImportoComplessivoAppalto() - importoComplessivoAppalto);
			}
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 5L, num));
			fase.setCodiceFase(5L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura fase collaudo. codgara = " + codGara + " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseCollaudo(FaseCollaudoInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseCollaudo(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 5L, form.getNum());
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase collaudo. codgara = " + form.getCodGara() + " codLotto = "
					+ form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseCollaudo(Long codGara, Long codLotto, Long num) {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseCollaudo(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 5L, 1L);

			List<String> sezioni = getSezioneIncaricoByFase(5L);
			for (String sezione : sezioni) {
				this.contrattiMapper.deleteIncarichiProf(codGara, codLotto, sezione, num);
			}

		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase conclusione semplificata contratto. codgara = "
					+ codGara + " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseFaseInizialeEsecuzione getFaseInizialeEsecuzione(Long codGara, Long codLotto, Long num) {
		ResponseFaseInizialeEsecuzione risultato = new ResponseFaseInizialeEsecuzione();
		risultato.setEsito(true);
		try {

			FaseInizialeEsecuzioneEntry fase = this.contrattiMapper.getFaseInizialeEsecuzione(codGara, codLotto, num);
			FaseInizPubbEsitoForm esito = this.contrattiMapper.getFaseInizialeEsecuzionePubbEsito(codGara, codLotto,
					num);
			if(fase.getDataInizioProg() != null && fase.getDataStipula() != null){
				LocalDate dataInizio = fase.getDataInizioProg().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate dataStipula = fase.getDataStipula().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				long giorni = ChronoUnit.DAYS.between(dataStipula,dataInizio);
				fase.setGgDataStipula(giorni);
			}
			fase.setPubblicazioneEsito(esito);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 2L, num));
			fase.setCodiceFase(2L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase iniziale esecuzione. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseInizialeEsecuzione(FaseInizialeEsecuzioneInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
					
			GaraEntry gara = this.contrattiMapper.dettaglioGara(form.getCodGara());
			if(gara.getVersioneSimog() != null && gara.getVersioneSimog() == 9L) {
				gara.setPcp(true);
			}
			if(!gara.isPcp()) {
				Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
				form.setNumAppa(numAppa);
				Long numIniz = this.contrattiMapper.getMaxNumIniz(form.getCodGara(), form.getCodLotto());
				if (numIniz == null) {
					numIniz = 1L;
				} else {
					numIniz++;
				}
				form.setNum(numIniz);
				form.getPubblicazioneEsito().setNumIniziale(numIniz);
				this.contrattiMapper.insertFaseInizialeEsecuzione(form);
				this.contrattiMapper.insertFaseInizPubbEsito(form.getPubblicazioneEsito());
				LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
				// Trovo l'id per l'inserimento enlla w9fasi
				Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 2L);
				if (id == null) {
					id = 1L;
				} else {
					id++;
				}

				SchedaSicurezzaInsertForm sicurezzaInsertForm = new SchedaSicurezzaInsertForm();
				sicurezzaInsertForm.setCodGara(form.getCodGara());
				sicurezzaInsertForm.setCodLotto(form.getCodLotto());
				sicurezzaInsertForm.setDirettoreOperativo("2");
				sicurezzaInsertForm.setNum(numIniz);
				sicurezzaInsertForm.setNumIniz(numIniz);
				sicurezzaInsertForm.setPianoSicurezza("2");
				sicurezzaInsertForm.setTutor("2");
				this.contrattiMapper.insertSchedaSicurezza(sicurezzaInsertForm);

				String idScheda = lotto.getCig() + "_2_" + calcolaIdScheda(id);
				this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 2L, id, idScheda, numAppa, null);

				risultato.setData(numIniz);
			} else {				
				FaseInizialeEsecuzioneEntry w9iniz = this.contrattiMapper.getFaseInizialeEsecuzioneMaxNumIniz(form.getCodGara(), form.getCodLotto());
				form.setNum(w9iniz.getNum());
				form.setNumAppa(w9iniz.getNumAppa());
				this.contrattiMapper.updateFaseInizialeEsecuzionePcp(form);

				LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
				// Trovo l'id per l'inserimento enlla w9fasi
				Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 2L);
				if (id == null) {
					id = 1L;
				} else {
					id++;
				}
				
				String idScheda = lotto.getCig() + "_2_" + calcolaIdScheda(id);
				this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 2L, id, idScheda, form.getNumAppa(), null);
				risultato.setData(w9iniz.getNum());
			}
			

		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase iniziale esecuzione. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseInizialeEsecuzione(FaseInizialeEsecuzioneInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			GaraEntry gara = this.contrattiMapper.dettaglioGara(form.getCodGara());
			if(gara.getVersioneSimog() != null && gara.getVersioneSimog() == 9L) {
				gara.setPcp(true);
			}
			if(!gara.isPcp()) {
				this.contrattiMapper.updateFaseInizialeEsecuzione(form);
				FaseComPubbEsitoForm pubblicazione = this.contrattiMapper.getFaseComPubbEsito(form.getCodGara(),
						form.getCodLotto(), form.getNum());
				if (pubblicazione == null) {
					form.getPubblicazioneEsito().setNumIniziale(form.getNum());
					this.contrattiMapper.insertFaseInizPubbEsito(form.getPubblicazioneEsito());
				} else {
					form.getPubblicazioneEsito().setNumIniziale(pubblicazione.getNumIniziale());
					this.contrattiMapper.updateFaseInizPubbEsito(form.getPubblicazioneEsito());
				}
			} else {
				this.contrattiMapper.updateFaseInizialeEsecuzionePcp(form);
			}

			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 2L, 1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase iniziale esecuzione. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseInizialeEsecuzione(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
			if(gara.getVersioneSimog() != null && gara.getVersioneSimog() == 9L) {
				gara.setPcp(true);
			}
			if(!gara.isPcp()) {
				this.contrattiMapper.deleteFaseInizialeEsecuzione(codGara, codLotto, num);
				this.contrattiMapper.deleteFaseInizPubbEsito(codGara, codLotto, num);
				this.contrattiMapper.deleteW9Fase(codGara, codLotto, 2L, num);

				// delete incarichi professionali
				List<String> sezioni = getSezioneIncaricoByFase(2L);
				for (String sezione : sezioni) {
					this.contrattiMapper.deleteIncarichiProf(codGara, codLotto, sezione, num);
				}
				this.contrattiMapper.deleteSchedaSicurezza(codGara, codLotto, num);
				this.contrattiMapper.deleteMisureAggiuntiveSicurezza(codGara, codLotto, num);
			} else {
				this.contrattiMapper.deleteW9Fase(codGara, codLotto, 2L, num);
			}
			
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase iniziale esecuzione. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}		
	
	public ResponseFaseInizialeEsecuzione getFaseInizialeSottoscrizioneContratto(Long codGara, Long codLotto, Long num) {
		ResponseFaseInizialeEsecuzione risultato = new ResponseFaseInizialeEsecuzione();
		risultato.setEsito(true);
		try {
			FaseAggiudicazioneEntry agg = this.contrattiMapper.getFaseAggiudicazione(codGara, codLotto, 1L);
			FaseInizialeEsecuzioneEntry fase = this.contrattiMapper.getFaseInizialeSottoscrizioneContratto(codGara, codLotto, num);
			FaseInizPubbEsitoForm esito = this.contrattiMapper.getFaseInizialeEsecuzionePubbEsito(codGara, codLotto,
					num);
			if(fase.getDataStipula() != null && agg.getDataVerbAggiudicazione() != null){
				LocalDate dataStipula = fase.getDataStipula().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				LocalDate dataVerbale = agg.getDataVerbAggiudicazione().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				long giorni = ChronoUnit.DAYS.between(dataVerbale, dataStipula);
				fase.setGgDataAgg(giorni);
			}
			fase.setPubblicazioneEsito(esito);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 13L, num));
			fase.setCodiceFase(13L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase iniziale sottoscrizione contratto. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseInizialeSottoscrizioneContratto(FaseInizialeEsecuzioneInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			Long numIniz = this.contrattiMapper.getMaxNumIniz(form.getCodGara(), form.getCodLotto());
			if (numIniz == null) {
				numIniz = 1L;
			} else {
				numIniz++;
			}
			form.setNum(numIniz);
			this.contrattiMapper.insertFaseInizialeEsecuzione(form);
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			// Trovo l'id per l'inserimento enlla w9fasi
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 13L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}

			String idScheda = lotto.getCig() + "_13_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 13L, id, idScheda, numAppa, null);
			risultato.setData(numIniz);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase iniziale sottoscrizione contratto. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseInizialeSottoscrizioneContratto(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseInizialeEsecuzione(codGara, codLotto, num);
			this.contrattiMapper.deleteFaseInizPubbEsito(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 13L, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 2L, num);

			// delete incarichi professionali
			List<String> sezioni = getSezioneIncaricoByFase(2L);
			for (String sezione : sezioni) {
				this.contrattiMapper.deleteIncarichiProf(codGara, codLotto, sezione, num);
			}
			this.contrattiMapper.deleteSchedaSicurezza(codGara, codLotto, num);
			this.contrattiMapper.deleteMisureAggiuntiveSicurezza(codGara, codLotto, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase iniziale sottoscrizione contratto. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}
	
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseInizialeSottoscrizioneContratto(FaseInizialeEsecuzioneInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseInizialeSottoscrizioneContratto(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 13L, 1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase iniziale iniziale sottoscrizione contratto. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseInizialeSemplificata getFaseInizialeSemplificata(Long codGara, Long codLotto, Long num) {
		ResponseFaseInizialeSemplificata risultato = new ResponseFaseInizialeSemplificata();
		risultato.setEsito(true);
		try {
			FaseInizialeSemplificataEntry fase = this.contrattiMapper.getFaseInizialeSemplificata(codGara, codLotto,
					num);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 986L, num));
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase iniziale semplificata. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseInizialeSemplificata(FaseInizialeSemplificataInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			Long numIniz = this.contrattiMapper.getMaxNumIniz(form.getCodGara(), form.getCodLotto());
			if (numIniz == null) {
				numIniz = 1L;
			} else {
				numIniz++;
			}
			form.setNum(numIniz);
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			this.contrattiMapper.insertFaseInizialeSemplificata(form);
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			// Trovo l'id per l'inserimento enlla w9fasi
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 986L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}

			SchedaSicurezzaInsertForm sicurezzaInsertForm = new SchedaSicurezzaInsertForm();
			sicurezzaInsertForm.setCodGara(form.getCodGara());
			sicurezzaInsertForm.setCodLotto(form.getCodLotto());
			sicurezzaInsertForm.setDirettoreOperativo("2");
			sicurezzaInsertForm.setNum(1L);
			sicurezzaInsertForm.setNumIniz(numIniz);
			sicurezzaInsertForm.setPianoSicurezza("2");
			sicurezzaInsertForm.setTutor("2");
			this.contrattiMapper.insertSchedaSicurezza(sicurezzaInsertForm);

			String idScheda = lotto.getCig() + "_986_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 986L, id, idScheda, numAppa, null);
			risultato.setData(numIniz);

		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase iniziale semplificata. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseInizialeSemplificata(FaseInizialeSemplificataInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseInizialeSemplificata(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 986L, 1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase iniziale semplificata. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseInizialeSemplificata(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseInizialeSemplificata(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 986L, num);

			// delete incarichi professionali
			List<String> sezioni = getSezioneIncaricoByFase(986L);
			for (String sezione : sezioni) {
				this.contrattiMapper.deleteIncarichiProf(codGara, codLotto, sezione, num);
			}
			this.contrattiMapper.deleteSchedaSicurezza(codGara, codLotto, num);
			this.contrattiMapper.deleteMisureAggiuntiveSicurezza(codGara, codLotto, num);

		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase iniziale semplificata. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseStipulaAccordoQuadro getFaseStipulaAccordoQuadro(Long codGara, Long codLotto, Long num) {
		ResponseFaseStipulaAccordoQuadro risultato = new ResponseFaseStipulaAccordoQuadro();
		risultato.setEsito(true);
		try {
			FaseStipulaAccordoQuadroEntry fase = this.contrattiMapper.getFaseStipulaAccordoQuadro(codGara, codLotto,
					num);
			FaseStipulaPubbEsitoForm esito = this.contrattiMapper.getFaseStipulaPubbEsito(codGara, codLotto, num);
			fase.setPubblicazioneEsito(esito);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 11L, num));
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase stipula accordo quadro. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseStipulaAccordoQuadro(FaseStipulaAccordoQuadroInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);

			Long numIniz = this.contrattiMapper.getMaxNumIniz(form.getCodGara(), form.getCodLotto());
			if (numIniz == null) {
				numIniz = 1L;
			} else {
				numIniz++;
			}
			form.setNum(numIniz);
			form.getPubblicazioneEsito().setNumIniziale(numIniz);
			form.setNumAppa(numAppa);
			this.contrattiMapper.insertFaseStipulaAccordoQuadro(form);
			this.contrattiMapper.insertFaseStipulaPubbEsito(form.getPubblicazioneEsito());
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 11L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			String idScheda = lotto.getCig() + "_11_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 11L, id, idScheda, numAppa, null);
			risultato.setData(numIniz);

		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase stipula accordo quadro. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseStipulaAccordoQuadro(FaseStipulaAccordoQuadroInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseStipulaAccordoQuadro(form);

			if (this.contrattiMapper.getFaseComPubbEsito(form.getCodGara(), form.getCodLotto(),
					form.getNum()) == null) {
				form.getPubblicazioneEsito().setNumIniziale(form.getNum());
				this.contrattiMapper.insertFaseStipulaPubbEsito(form.getPubblicazioneEsito());
			} else {
				form.getPubblicazioneEsito().setNumIniziale(form.getNum());
				this.contrattiMapper.updateFaseStipulaPubbEsito(form.getPubblicazioneEsito());
			}
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 11L, 1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase stipula accordo quadro. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseStipulaAccordoQuadro(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseStipulaAccordoQuadro(codGara, codLotto, num);
			this.contrattiMapper.deleteFaseStipulaPubbEsito(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 11L, 1L);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase stipula accordo quadro. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseAvanzamento getFaseAvanzamento(Long codGara, Long codLotto, Long num) {
		ResponseFaseAvanzamento risultato = new ResponseFaseAvanzamento();
		risultato.setEsito(true);
		try {
			FaseAvanzamentoEntry fase = this.contrattiMapper.getFaseAvanzamento(codGara, codLotto, num);
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, codLotto);
			Double importoComplessivoAppalto = getImportoComplessivoAppalto(codGara, codLotto, fase.getNumAppa(), lotto, sqlMapper);
			Double importoFaseVariante = sqlMapper.getImportoUltimaFaseVariante(codGara, codLotto);
			if(fase.getImportoSal() != null && importoFaseVariante != null){
				fase.setPercentualeSal(fase.getImportoSal()/importoFaseVariante);
			} else if(fase.getImportoSal() != null && importoComplessivoAppalto != null){
				fase.setPercentualeSal(fase.getImportoSal()/importoComplessivoAppalto);
			}

			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 3L, num));
			fase.setCodiceFase(3L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura fase avanzamento. codgara = " + codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseMaxNumAvan getMaxNumAvan(Long codGara, Long codLotto) {
		ResponseMaxNumAvan risultato = new ResponseMaxNumAvan();
		risultato.setEsito(true);
		try {
			Long maxNumAvan = this.contrattiMapper.getMaxNumAvan(codGara, codLotto);
			if (maxNumAvan == null) {
				maxNumAvan = 1L;
			} else {
				maxNumAvan++;
			}

			risultato.setData(maxNumAvan);

		} catch (Exception e) {
            logger.error("Errore inaspettato in getMaxNumAvan. codGara = {}, codLotto = {}", codGara, codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseAvanzamento(FaseAvanzamentoInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {

			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			Long numAvan = this.contrattiMapper.getMaxNumAvan(form.getCodGara(), form.getCodLotto());
			if (numAvan == null) {
				numAvan = 1L;
			} else {
				numAvan++;
			}
			form.setNum(numAvan);
			this.contrattiMapper.insertFaseAvanzamento(form);
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 3L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			String idScheda = lotto.getCig() + "_3_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 3L, id, idScheda, numAppa, null);

			this.insertW9AssociazioneCampi(form.getCodGara(), form.getCodLotto(), 3L, form.getNum(), form.getAssociazioniPagamenti());

			risultato.setData(numAvan);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase avanzamento. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseAvanzamento(FaseAvanzamentoInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseAvanzamento(form);
			this.insertW9AssociazioneCampi(form.getCodGara(), form.getCodLotto(), 3L, form.getNum(), form.getAssociazioniPagamenti());
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 3L, form.getNum());
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase collaudo. codgara = " + form.getCodGara() + " codLotto = "
					+ form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseAvanzamento(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseAvanzamento(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 3L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase iniziale esecuzione. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseAvanzamentoSemp getFaseAvanzamentoSemp(Long codGara, Long codLotto, Long num) {
		ResponseFaseAvanzamentoSemp risultato = new ResponseFaseAvanzamentoSemp();
		risultato.setEsito(true);
		try {
			FaseAvanzamentoSempEntry fase = this.contrattiMapper.getFaseAvanzamentoSemp(codGara, codLotto, num);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 102L, num));
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase avanzamento Semp. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseAvanzamentoSemp(FaseAvanzamentoSempInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 102L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			Long numAvan = this.contrattiMapper.getMaxNumAvan(form.getCodGara(), form.getCodLotto());
			if (numAvan == null) {
				numAvan = 1L;
			} else {
				numAvan++;
			}
			form.setNum(numAvan);
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			this.contrattiMapper.insertFaseAvanzamentoSemp(form);
			String idScheda = lotto.getCig() + "_102_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 102L, numAvan, idScheda, numAppa,
					null);
			risultato.setData(numAvan);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase avanzamento semplificata. codgara = "
					+ form.getCodGara() + " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseAvanzamentoSemp(FaseAvanzamentoSempInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseAvanzamentoSemp(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 102L, form.getNum());
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase avanzamento semplificata. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseAvanzamentoSemp(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseAvanzamentoSemp(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 102L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase avanzamento semplificata. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseSospensione getFaseSospensione(Long codGara, Long codLotto, Long num) {
		ResponseFaseSospensione risultato = new ResponseFaseSospensione();
		risultato.setEsito(true);
		try {
			FaseSospensioneEntry fase = this.contrattiMapper.getFaseSospensione(codGara, codLotto, num);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 6L, num));
			fase.setCodiceFase(6L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura fase sospensione. codgara = " + codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseSospensione(FaseSospensioneInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 6L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}

			Long numSosp = this.contrattiMapper.getMaxNumSosp(form.getCodGara(), form.getCodLotto());
			if (numSosp == null) {
				numSosp = 1L;
			} else {
				numSosp++;
			}
			form.setNum(numSosp);
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			form.setFlagSuperoTempo("2");
			this.contrattiMapper.insertFaseSospensione(form);
			String idScheda = lotto.getCig() + "_6_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 6L, id, idScheda, numAppa, null);
			risultato.setData(numSosp);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase sospensione. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseSospensione(FaseSospensioneInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			GaraEntry gara = this.contrattiMapper.dettaglioGara(form.getCodGara());
			if(gara != null && gara.getVersioneSimog() != null && gara.getVersioneSimog() == 9L) {
				form.setFlagSuperoTempo("2");
			}					
			this.contrattiMapper.updateFaseSospensione(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 6L, form.getNum());
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase sospensione. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseSospensione(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseSospensione(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 6L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase sospensione. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			throw e;
		}
		return risultato;
	}
	
	public ResponseInizFaseRipresaPrestazione getInizFaseRirpesaPrestazione(Long codGara, Long codLotto) {
		ResponseInizFaseRipresaPrestazione risultato = new ResponseInizFaseRipresaPrestazione();
		InizFaseRipresaPrestazioneEntry data = new InizFaseRipresaPrestazioneEntry();
		risultato.setEsito(true);
		try {		
						
			List<FaseSospensioneEntry> list = this.contrattiMapper.getFasiSospensione(codGara, codLotto);
			
			data.setListaSosp(list);
			risultato.setData(data);
					
									
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura dati per inizializzazione fase ripresa prestazione. codgara = "
					+ codGara + " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}
	
	public ResponseFaseSospensione getFaseRipresaPrestazione(Long codGara, Long codLotto, Long num) {
		ResponseFaseSospensione risultato = new ResponseFaseSospensione();
		risultato.setEsito(true);
		try {
			FaseSospensioneEntry fase = this.contrattiMapper.getFaseSospensione(codGara, codLotto, num);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 15L, num));
			fase.setCodiceFase(15L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura fase ripresa prestazione. codgara = " + codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseRipresaPrestazione(FaseRipresaPrestazioneInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());			
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);			
			this.contrattiMapper.updateFaseRipresaPrestazione(form);
			String idScheda = lotto.getCig() + "_15_" + calcolaIdScheda(form.getNum());
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 15L, form.getNum(), idScheda, numAppa, null);
			risultato.setData(form.getNum());
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase ripresa prestazione. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseRipresaPrestazione(FaseRipresaPrestazioneInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseRipresaPrestazione(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 15L, form.getNum());
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase ripresa prestazione. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseRipresaPrestazione(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			//this.contrattiMapper.deleteFaseRipresaPrestazione(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 15L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase ripresa prestazione. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			throw e;
		}
		return risultato;
	}
	
	public ResponseFaseSospensione getFaseSuperamentoQuartoContrattuale(Long codGara, Long codLotto, Long num) {
		ResponseFaseSospensione risultato = new ResponseFaseSospensione();
		risultato.setEsito(true);
		try {
			FaseSospensioneEntry fase = this.contrattiMapper.getFaseSospensione(codGara, codLotto, num);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 14L, num));
			fase.setCodiceFase(14L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura fase superamento del quarto del tempo contrattuale. codgara = " + codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseSuperamentoQuartoContrattuale(FaseSuperamentoQuartoContrattualeInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());			
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);			
			this.contrattiMapper.updateFaseSuperamentoQuartoTemporale(form);
			String idScheda = lotto.getCig() + "_14_" + calcolaIdScheda(form.getNum());
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 14L, form.getNum(), idScheda, numAppa, null);
			risultato.setData(form.getNum());
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase superamento del quarto del tempo contrattuale. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseSuperamentoQuartoContrattuale(FaseSuperamentoQuartoContrattualeInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseSuperamentoQuartoTemporale(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 14L, form.getNum());
		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase superamento del quarto del tempo contrattuale. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseSuperamentoQuartoContrattuale(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			//this.contrattiMapper.deleteFaseRipresaPrestazione(codGara, codLotto, num);
			this.contrattiMapper.updateFlagSuperamentoQuartoTemporale(codGara, codLotto, num, "2");
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 14L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase superamento del quarto del tempo contrattuale. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseVariante getFaseVariante(Long codGara, Long codLotto, Long num) {
		ResponseFaseVariante risultato = new ResponseFaseVariante();
		risultato.setEsito(true);
		try {
			FaseVarianteEntry fase = this.contrattiMapper.getFaseVariante(codGara, codLotto, num);

			if(num > 1){
				FaseVarianteEntry fasePrec =  this.contrattiMapper.getFaseVariante(codGara, codLotto, num - 1 );
				if(fasePrec != null && fase.getImportoComplAppalto() != null && fasePrec.getImportoComplIntervento() != null){
					fase.setImpModifica(fase.getImportoComplAppalto() - fasePrec.getImportoComplIntervento());
				}
			} else{
				FaseAggiudicazioneEntry agg = this.contrattiMapper.getFaseAggiudicazione(codGara, codLotto, 1L);
				if(agg != null && fase.getImportoComplAppalto() != null && agg.getImportoAggiudicazione()!= null){
					fase.setImpModifica(fase.getImportoComplAppalto() - agg.getImportoAggiudicazione());
				}
			}

			fase.setCountW9moti(this.contrattiMapper.getCountW9moti(codGara, codLotto));
			fase.setMotivazioniVariante(getMotivazioniFaseVariante(codGara, codLotto, num));
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 7L, num));
			fase.setCodiceFase(7L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura fase variante. codgara = " + codGara + " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseVariante(FaseVarianteInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			//VIGILANZA2-312: Al Salvataggio della scheda, se il campo "Altre motivazioni"è vuoto,
			//viene valorizzato con "-".
			form.setAltreMotivazioni(
				form.getAltreMotivazioni() == null || StringUtils.isEmpty(form.getAltreMotivazioni())
					?
						"-"
					:
						form.getAltreMotivazioni()
			);
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 7L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			Long numVari = this.contrattiMapper.getMaxNumVari(form.getCodGara(), form.getCodLotto());
			if (numVari == null) {
				numVari = 1L;
			} else {
				numVari++;
			}
			form.setNum(numVari);
			
			
			this.contrattiMapper.insertFaseVariante(form);
			Long num = 1L;
			if (form.getMotivazioniVariante() != null) {
				for (LottoDynamicValue d : form.getMotivazioniVariante()) {
					if (d.getValue() != 2L) {
						this.contrattiMapper.insertMotivazioneFaseVariante(form.getCodGara(), form.getCodLotto(),
								form.getNum(), num, d.getCodice());
						num++;
					}
				}
			}

			String idScheda = lotto.getCig() + "_7_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 7L, id, idScheda, numAppa, null);
			risultato.setData(numVari);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase variante. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseVariante(FaseVarianteInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			//VIGILANZA2-312: Al Salvataggio della scheda, se il campo "Altre motivazioni"è vuoto,
			//viene valorizzato con "-".
			form.setAltreMotivazioni(
				form.getAltreMotivazioni() == null || StringUtils.isEmpty(form.getAltreMotivazioni())
				?
					"-"
				:
					form.getAltreMotivazioni()
			);
			this.contrattiMapper.updateFaseVariante(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 7L, form.getNum());
			this.contrattiMapper.deleteMotivazioniFaseVariante(form.getCodGara(), form.getCodLotto(), form.getNum());
			Long num = 1L;
			for (LottoDynamicValue d : form.getMotivazioniVariante()) {
				if (d.getValue() != 2L) {
					this.contrattiMapper.insertMotivazioneFaseVariante(form.getCodGara(), form.getCodLotto(),
							form.getNum(), num, d.getCodice());
					num++;
				}
			}

		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase variante. codgara = " + form.getCodGara() + " codLotto = "
					+ form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseVariante(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseVariante(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 7L, num);
			this.contrattiMapper.deleteMotivazioniFaseVariante(codGara, codLotto, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase variante. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			throw e;
		}
		return risultato;
	}

	private List<LottoDynamicValue> getMotivazioniFaseVariante(Long codGara, Long numLotto, Long num) throws Exception {

		List<LottoDynamicValue> risultato = new ArrayList<LottoDynamicValue>();
		try {
			GaraFullEntry gara = this.contrattiMapper.dettaglioGaraCompleto(codGara);
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, numLotto);

			if (gara != null && lotto != null) {

				String tipoContrattoLotto = lotto.getTipologia();
				List<TabellatoEntry> tabellatoMotivazioni = new ArrayList<TabellatoEntry>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dataRiferimento = sdf.parse("2016-04-18");
				boolean dataPubblicazioneBefore = gara.getDataCreazione() == null
						|| (gara.getDataCreazione() != null && (gara.getDataCreazione().before(dataRiferimento)
								|| gara.getDataCreazione().equals(dataRiferimento)));

				if (dataPubblicazioneBefore) {
					if ("L".equals(tipoContrattoLotto)) {
						tabellatoMotivazioni = this.contrattiMapper.getMotivazioniFaseVar2();						
					} else if (!"L".equals(tipoContrattoLotto)) {
						tabellatoMotivazioni = this.contrattiMapper.getMotivazioniFaseVar1();
					}
				} else {
					if (gara.getVersioneSimog() < 3L) {
						tabellatoMotivazioni = this.contrattiMapper.getMotivazioniFaseVar3();						
					}
				}
				
				if(gara.getVersioneSimog() >= 3L && gara.getVersioneSimog() < 9L) {
					tabellatoMotivazioni = this.contrattiMapper.getMotivazioniFaseVar4();
				}
				
				if(gara.getVersioneSimog() == 9L) {
					tabellatoMotivazioni = this.contrattiMapper.getMotivazioniFaseVar5();
				}
				
				List<Long> codiciCondizioni = this.contrattiMapper.getMotiviVariazioneByFase(codGara, numLotto, num);
				for (TabellatoEntry e : tabellatoMotivazioni) {
					LottoDynamicValue v = new LottoDynamicValue();
					v.setCodice(e.getCodice());
					v.setDescrizione(e.getDescrizione());
					v.setValue(codiciCondizioni.contains(e.getCodice()) ? 1L : 2L);
					risultato.add(v);
				}
			}
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato durante l'estrazione del dettaglio della fase variazione - motivazioni variazione: codgara = "
							+ codGara + " codLotto=" + numLotto,
					e);
			throw e;
		}
		return risultato;
	}	

	public FaseVarianteIniz getInizFaseVariante(Long codGara, Long numLotto) {

		FaseVarianteIniz risultato = new FaseVarianteIniz();
		List<LottoDynamicValue> motivazioni = new ArrayList<LottoDynamicValue>();
		try {
			GaraFullEntry gara = this.contrattiMapper.dettaglioGaraCompleto(codGara);
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, numLotto);

			if (gara != null && lotto != null) {
				Long countW9moti = this.contrattiMapper.getCountW9moti(codGara, numLotto);

				String tipoContrattoLotto = lotto.getTipologia();
				List<TabellatoEntry> tabellatoMotivazioni = new ArrayList<TabellatoEntry>();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date dataRiferimento = sdf.parse("2016-04-18");
				boolean dataPubblicazioneBefore = gara.getDataCreazione() == null
						|| (gara.getDataPubblicazione() != null && (gara.getDataCreazione().before(dataRiferimento)
								|| gara.getDataCreazione().equals(dataRiferimento)));

				if (dataPubblicazioneBefore) {
					if ("L".equals(tipoContrattoLotto)) {
						tabellatoMotivazioni = this.contrattiMapper.getMotivazioniFaseVar2();						
					} else if (!"L".equals(tipoContrattoLotto)) {
						tabellatoMotivazioni = this.contrattiMapper.getMotivazioniFaseVar1();
					}
				} else {
					if (gara.getVersioneSimog() < 3L) {
						tabellatoMotivazioni = this.contrattiMapper.getMotivazioniFaseVar3();						
					}
				}
				
				if(gara.getVersioneSimog() >= 3L && gara.getVersioneSimog() < 9L) {
					tabellatoMotivazioni = this.contrattiMapper.getMotivazioniFaseVar4();
				}
				
				if(gara.getVersioneSimog() == 9L) {
					tabellatoMotivazioni = this.contrattiMapper.getMotivazioniFaseVar5();
				}

				for (TabellatoEntry e : tabellatoMotivazioni) {
					LottoDynamicValue v = new LottoDynamicValue();
					v.setCodice(e.getCodice());
					v.setDescrizione(e.getDescrizione());
					v.setValue(2L);
					motivazioni.add(v);
				}
				risultato.setMotivazioni(motivazioni);
				risultato.setCountW9moti(countW9moti);
			}

		} catch (Throwable t) {
			logger.error(
					"Errore inaspettato durante l'estrazione del dettaglio della inizializzazione della fase variante : codgara = "
							+ codGara + " codLotto=" + numLotto,
					t);
		}
		return risultato;
	}

	public ResponseTabellato getTabellatoDinamicoFaseVariante(Long codGara, Long numLotto) {

		ResponseTabellato risultato = new ResponseTabellato();
		try {

			GaraEntry gara = this.contrattiMapper.dettaglioGara(codGara);
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, numLotto);
			String tipoContrattoLotto = lotto.getTipologia();
			List<TabellatoEntry> tabellatoMotivazioni = null;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dataRiferimento = sdf.parse("2016-04-18");
			boolean dataPubblicazioneBefore = gara.getDataPubblicazione() == null
					|| (gara.getDataPubblicazione() != null && (gara.getDataPubblicazione().before(dataRiferimento)
							|| gara.getDataPubblicazione().equals(dataRiferimento)));
			if (dataPubblicazioneBefore) {
				if ("L".equals(tipoContrattoLotto)) {
					tabellatoMotivazioni = this.contrattiMapper.getMotivazioniFaseVar1();
				} else if (!"L".equals(tipoContrattoLotto)) {
					tabellatoMotivazioni = this.contrattiMapper.getMotivazioniFaseVar2();
				}
			} else {
				if (gara.getVersioneSimog() >= 3L) {
					tabellatoMotivazioni = this.contrattiMapper.getMotivazioniFaseVar3();
				} else {
					tabellatoMotivazioni = this.contrattiMapper.getMotivazioniFaseVar4();
				}
			}

			risultato.setData(tabellatoMotivazioni);
		} catch (Throwable t) {
			logger.error(
					"Errore inaspettato durante l'estrazione del tabellato fase variazione - motivazioni variazione: codgara = "
							+ codGara + " codLotto=" + numLotto,
					t);
		}
		return risultato;
	}

	public ResponseFaseAccordoBonario getFaseAccordoBonario(Long codGara, Long codLotto, Long num) {
		ResponseFaseAccordoBonario risultato = new ResponseFaseAccordoBonario();
		risultato.setEsito(true);
		try {
			FaseAccordoBonarioEntry fase = this.contrattiMapper.getFaseAccordoBonario(codGara, codLotto, num);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 8L, num));
			fase.setCodiceFase(8L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase accordo bonario. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseAccordoBonario(FaseAccordoBonarioInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 8L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			Long numAcco = this.contrattiMapper.getMaxNumAcco(form.getCodGara(), form.getCodLotto());
			if (numAcco == null) {
				numAcco = 1L;
			} else {
				numAcco++;
			}
			form.setNum(numAcco);
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			this.contrattiMapper.insertFaseAccordoBonario(form);
			String idScheda = lotto.getCig() + "_8_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 8L, id, idScheda, numAppa, null);

			this.insertW9AssociazioneCampi(form.getCodGara(), form.getCodLotto(), 8L, form.getNum(), form.getAssociazioniPagamenti());

			risultato.setData(numAcco);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase accordo bonario. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseAccordoBonario(FaseAccordoBonarioInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseAccordoBonario(form);
			this.insertW9AssociazioneCampi(form.getCodGara(), form.getCodLotto(), 8L, form.getNum(), form.getAssociazioniPagamenti());
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 8L, form.getNum());

		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase accordo bonario. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseAccordoBonario(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseAccordoBonario(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 8L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase accordo bonario. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}

	public ResponseInizFaseSubappalto getInizFaseSubappalto(Long codGara, Long codLotto) {
		ResponseInizFaseSubappalto risultato = new ResponseInizFaseSubappalto();
		try {
			InizFaseSubappaltoEntry entry = new InizFaseSubappaltoEntry();
			Long countImprese = contrattiMapper.countImpreseAggiudicatarie(codGara, codLotto);
			boolean singolaImpresa = countImprese == 1;
			entry.setSingolaImpresa(singolaImpresa);
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(codGara, codLotto);
			List<CPVSecondario> cpvSecondari = this.contrattiMapper.getCpvSecondariLotto(codGara, codLotto);
			List<CPVSecondario> cpvLotto = new ArrayList<CPVSecondario>();
			CPVSecondario cpvPrincipale = new CPVSecondario();
			cpvPrincipale.setCodCpv(lotto.getCpv());		
			
			if(lotto.getCpv() != null) {
				String cpvLike = lotto.getCpv() + "%";
				List<String> descCpv = this.contrattiMapper.getCpvDescLike(cpvLike);
				if(descCpv != null && descCpv.size() > 0 && descCpv.get(0) != null) {
					cpvPrincipale.setDescCpv(descCpv.get(0));
				}	
			}
			
			
			cpvLotto.add(cpvPrincipale);
			if (cpvSecondari != null) {
				cpvLotto.addAll(cpvSecondari);
			}
			entry.setListaCpv(cpvLotto);
			risultato.setData(entry);
			risultato.setEsito(true);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato durante il recupero delle informazioni di inizializzazione della fase di subappalto. codgara = "
							+ codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseFaseSubappalto getFaseSubappalto(Long codGara, Long codLotto, Long num) {
		ResponseFaseSubappalto risultato = new ResponseFaseSubappalto();
		risultato.setEsito(true);
		try {
			FaseSubappaltoEntry fase = this.contrattiMapper.getFaseSubappalto(codGara, codLotto, num);
			List<String> codiceMandanti = this.contrattiMapper.getMandantiFaseSubappalto(codGara, codLotto, num);
			if (fase.getCodImpresaAgg() != null) {
				fase.setImpresaAggiudicatrice(this.getImpresa(fase.getCodImpresaAgg()));
			}
			if (fase.getCodImpresa() != null) {
				fase.setImpresaSubappaltatrice(this.getImpresa(fase.getCodImpresa()));
			}
			if (fase.getIdCpv() != null) {
				String descCpv = this.contrattiMapper.getCpvDesc(fase.getIdCpv());
				if(StringUtils.isNotBlank(descCpv)){
					fase.setDescCpv(this.contrattiMapper.getCpvDesc(fase.getIdCpv()));
				} else {
					String cpvLike = fase.getIdCpv() + "%";
					List<String> descCpvList = this.contrattiMapper.getCpvDescLike(cpvLike);
					if(descCpvList != null && descCpvList.size() > 0 && descCpvList.get(0) != null) {
						fase.setDescCpv(descCpvList.get(0));
					}	
				}											
			}
			if (codiceMandanti != null && codiceMandanti.size() > 0) {
				List<ImpresaEntry> me = new ArrayList<ImpresaEntry>();
				for (String cm : codiceMandanti) {
					if (cm != null) {
						me.add(this.getImpresa(cm));
					}
				}
				fase.setMandanti(me);
			}
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 9L, num));
			fase.setCodiceFase(9L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura fase subappalto. codgara = " + codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseSubappalto(FaseSubappaltoInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 9L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			Long numSuba = this.contrattiMapper.getMaxNumSuba(form.getCodGara(), form.getCodLotto());
			if (numSuba == null) {
				numSuba = 1L;
			} else {
				numSuba++;
			}

			form.setNum(numSuba);
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);

			if(StringUtils.isBlank(form.getCodImpresaAgg())) {
				Long countImprese = contrattiMapper.countImpreseAggiudicatarie(form.getCodGara(), form.getCodLotto());
				boolean singolaImpresa = countImprese == 1;
				if(singolaImpresa) {
					List<String> impAgg = contrattiMapper.getCodImpImpreseAggiudicataria(form.getCodGara(), form.getCodLotto());
					if(impAgg != null && !impAgg.isEmpty())
					form.setCodImpresaAgg(impAgg.get(0));
				}
			}
			
			this.contrattiMapper.insertFaseSubappalto(form);

			if (form.getMandanti() != null && form.getMandanti().size() > 0) {
				Long i = 1L;
				for (MandanteEntry mand : form.getMandanti()) {
					this.contrattiMapper.insertMandantiFaseSubappalto(form.getCodGara(), form.getCodLotto(),
							form.getNum(), i, mand.getNomeMandante());
					i++;
				}
			}

			String idScheda = lotto.getCig() + "_9_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 9L, id, idScheda, numAppa, null);
			risultato.setData(numSuba);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase accordo bonario. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseSubappalto(FaseSubappaltoInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteMandantiFaseSubappalto(form.getCodGara(), form.getCodLotto(), form.getNum());
			if (form.getMandanti() != null && form.getMandanti().size() > 0) {
				Long i = 1L;
				for (MandanteEntry mand : form.getMandanti()) {
					this.contrattiMapper.insertMandantiFaseSubappalto(form.getCodGara(), form.getCodLotto(),
							form.getNum(), i, mand.getNomeMandante());
					i++;
				}
			}
			if(StringUtils.isBlank(form.getCodImpresaAgg())) {
				Long countImprese = contrattiMapper.countImpreseAggiudicatarie(form.getCodGara(), form.getCodLotto());
				boolean singolaImpresa = countImprese == 1;
				if(singolaImpresa) {
					List<String> impAgg = contrattiMapper.getCodImpImpreseAggiudicataria(form.getCodGara(), form.getCodLotto());
					if(impAgg != null && !impAgg.isEmpty())
					form.setCodImpresaAgg(impAgg.get(0));
				}
			}
			this.contrattiMapper.updateFaseSubappalto(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 9L, form.getNum());

		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase subappalto. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseSubappalto(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseSubappalto(codGara, codLotto, num);
			this.contrattiMapper.deleteMandantiFaseSubappalto(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 9L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase subappalto. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			throw e;
		}
		return risultato;
	}
	
	public ResponseFaseSubappalto getFaseRichiestaSubappalto(Long codGara, Long codLotto, Long num) {
		ResponseFaseSubappalto risultato = new ResponseFaseSubappalto();
		risultato.setEsito(true);
		try {
			FaseSubappaltoEntry fase = this.contrattiMapper.getFaseSubappalto(codGara, codLotto, num);
			List<String> codiceMandanti = this.contrattiMapper.getMandantiFaseSubappalto(codGara, codLotto, num);
			if (fase.getCodImpresaAgg() != null) {
				fase.setImpresaAggiudicatrice(this.getImpresa(fase.getCodImpresaAgg()));
			}
			if (fase.getCodImpresa() != null) {
				fase.setImpresaSubappaltatrice(this.getImpresa(fase.getCodImpresa()));
			}
			if (fase.getIdCpv() != null) {
				fase.setDescCpv(this.contrattiMapper.getCpvDesc(fase.getIdCpv()));
			}
			if (codiceMandanti != null && codiceMandanti.size() > 0) {
				List<ImpresaEntry> me = new ArrayList<ImpresaEntry>();
				for (String cm : codiceMandanti) {
					if (cm != null) {
						me.add(this.getImpresa(cm));
					}
				}
				fase.setMandanti(me);
			}
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 16L, num));
			fase.setCodiceFase(16L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura fase richiesta subappalto. codgara = " + codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseRichiestaSubappalto(FaseSubappaltoInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 16L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			Long numSuba = this.contrattiMapper.getMaxNumSuba(form.getCodGara(), form.getCodLotto());
			if (numSuba == null) {
				numSuba = 1L;
			} else {
				numSuba++;
			}

			form.setNum(numSuba);
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			
			if(StringUtils.isBlank(form.getCodImpresaAgg())) {
				Long countImprese = contrattiMapper.countImpreseAggiudicatarie(form.getCodGara(), form.getCodLotto());
				boolean singolaImpresa = countImprese == 1;
				if(singolaImpresa) {
					List<String> impAgg = contrattiMapper.getCodImpImpreseAggiudicataria(form.getCodGara(), form.getCodLotto());
					if(impAgg != null && !impAgg.isEmpty())
					form.setCodImpresaAgg(impAgg.get(0));
				}
			}
			

			this.contrattiMapper.insertFaseRichiestaSubappalto(form);

			if (form.getMandanti() != null && form.getMandanti().size() > 0) {
				Long i = 1L;
				for (MandanteEntry mand : form.getMandanti()) {
					this.contrattiMapper.insertMandantiFaseSubappalto(form.getCodGara(), form.getCodLotto(),
							form.getNum(), i, mand.getNomeMandante());
					i++;
				}
			}

			String idScheda = lotto.getCig() + "_16_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 16L, id, idScheda, numAppa, null);
			risultato.setData(numSuba);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase richiesta subappalto. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseRichiestaSubappalto(FaseSubappaltoInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteMandantiFaseSubappalto(form.getCodGara(), form.getCodLotto(), form.getNum());
			if (form.getMandanti() != null && form.getMandanti().size() > 0) {
				Long i = 1L;
				for (MandanteEntry mand : form.getMandanti()) {
					this.contrattiMapper.insertMandantiFaseSubappalto(form.getCodGara(), form.getCodLotto(),
							form.getNum(), i, mand.getNomeMandante());
					i++;
				}
			}
			
			if(StringUtils.isBlank(form.getCodImpresaAgg())) {
				Long countImprese = contrattiMapper.countImpreseAggiudicatarie(form.getCodGara(), form.getCodLotto());
				boolean singolaImpresa = countImprese == 1;
				if(singolaImpresa) {
					List<String> impAgg = contrattiMapper.getCodImpImpreseAggiudicataria(form.getCodGara(), form.getCodLotto());
					if(impAgg != null && !impAgg.isEmpty())
					form.setCodImpresaAgg(impAgg.get(0));
				}
			}
			this.contrattiMapper.updateFaseRichiestaSubappalto(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 16L, form.getNum());

		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase richiesta subappalto. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseRichiestaSubappalto(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseSubappalto(codGara, codLotto, num);
			this.contrattiMapper.deleteMandantiFaseSubappalto(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 16L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase richiesta subappalto. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			throw e;
		}
		return risultato;
	}
	
	public ResponseInizFaseSubappalto getInizFaseEsitoSubappalto(Long codGara, Long codLotto) {
		ResponseInizFaseSubappalto risultato = new ResponseInizFaseSubappalto();
		try {
			risultato.setEsito(true);
			InizFaseSubappaltoEntry entry = new InizFaseSubappaltoEntry();
			List<FaseSubappaltoEntry> list = this.contrattiMapper.getNumSubaEsitoSuba(codGara,codLotto);
			
			for (FaseSubappaltoEntry faseSubappaltoEntry : list) {
				List<String> codiceMandanti = this.contrattiMapper.getMandantiFaseSubappalto(codGara, codLotto, faseSubappaltoEntry.getNum());
				if (faseSubappaltoEntry.getCodImpresaAgg() != null) {
					faseSubappaltoEntry.setImpresaAggiudicatrice(this.getImpresa(faseSubappaltoEntry.getCodImpresaAgg()));
				}
				if (faseSubappaltoEntry.getCodImpresa() != null) {
					faseSubappaltoEntry.setImpresaSubappaltatrice(this.getImpresa(faseSubappaltoEntry.getCodImpresa()));
				}
				if (faseSubappaltoEntry.getIdCpv() != null) {
					faseSubappaltoEntry.setDescCpv(this.contrattiMapper.getCpvDesc(faseSubappaltoEntry.getIdCpv()));
				}
				if (codiceMandanti != null && codiceMandanti.size() > 0) {
					List<ImpresaEntry> me = new ArrayList<ImpresaEntry>();
					for (String cm : codiceMandanti) {
						if (cm != null) {
							me.add(this.getImpresa(cm));
						}
					}
					faseSubappaltoEntry.setMandanti(me);
				}
			}
			
			
				
			entry.setListaSuba(list);
			risultato.setData(entry);
		
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato durante il recupero delle informazioni di inizializzazione della fase di subappalto. codgara = "
							+ codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseFaseSubappalto getFaseEsitoSubappalto(Long codGara, Long codLotto, Long num) {
		ResponseFaseSubappalto risultato = new ResponseFaseSubappalto();
		risultato.setEsito(true);
		try {
			FaseSubappaltoEntry fase = this.contrattiMapper.getFaseSubappalto(codGara, codLotto, num);
			List<String> codiceMandanti = this.contrattiMapper.getMandantiFaseSubappalto(codGara, codLotto, num);
			if (fase.getCodImpresaAgg() != null) {
				fase.setImpresaAggiudicatrice(this.getImpresa(fase.getCodImpresaAgg()));
			}
			if (fase.getCodImpresa() != null) {
				fase.setImpresaSubappaltatrice(this.getImpresa(fase.getCodImpresa()));
			}
			if (fase.getIdCpv() != null) {
				fase.setDescCpv(this.contrattiMapper.getCpvDesc(fase.getIdCpv()));
			}
			if (codiceMandanti != null && codiceMandanti.size() > 0) {
				List<ImpresaEntry> me = new ArrayList<ImpresaEntry>();
				for (String cm : codiceMandanti) {
					if (cm != null) {
						me.add(this.getImpresa(cm));
					}
				}
				fase.setMandanti(me);
			}
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 17L, num));
			fase.setCodiceFase(17L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura fase esito subappalto. codgara = " + codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseEsitoSubappalto(FaseEsitoSubappaltoInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());			
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			

			this.contrattiMapper.updateFaseEsitoSubappalto(form);

			

			String idScheda = lotto.getCig() + "_17_" + calcolaIdScheda(form.getNum());
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 17L, form.getNum(), idScheda, numAppa, null);
			risultato.setData(form.getNum());
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase esito subappalto. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseEsitoSubappalto(FaseEsitoSubappaltoInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			
			this.contrattiMapper.updateFaseEsitoSubappalto(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 17L, form.getNum());

		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase esito subappalto. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseEsitoSubappalto(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 17L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase esito subappalto. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			throw e;
		}
		return risultato;
	}
	
	public ResponseInizFaseSubappalto getInizFaseConclusioneSubappalto(Long codGara, Long codLotto) {
		ResponseInizFaseSubappalto risultato = new ResponseInizFaseSubappalto();
		try {
			risultato.setEsito(true);
			InizFaseSubappaltoEntry entry = new InizFaseSubappaltoEntry();
			List<FaseSubappaltoEntry> list = this.contrattiMapper.getNumSubaConclusioneSuba(codGara,codLotto);
			for (FaseSubappaltoEntry faseSubappaltoEntry : list) {
				List<String> codiceMandanti = this.contrattiMapper.getMandantiFaseSubappalto(codGara, codLotto, faseSubappaltoEntry.getNum());
				if (faseSubappaltoEntry.getCodImpresaAgg() != null) {
					faseSubappaltoEntry.setImpresaAggiudicatrice(this.getImpresa(faseSubappaltoEntry.getCodImpresaAgg()));
				}
				if (faseSubappaltoEntry.getCodImpresa() != null) {
					faseSubappaltoEntry.setImpresaSubappaltatrice(this.getImpresa(faseSubappaltoEntry.getCodImpresa()));
				}
				if (faseSubappaltoEntry.getIdCpv() != null) {
					faseSubappaltoEntry.setDescCpv(this.contrattiMapper.getCpvDesc(faseSubappaltoEntry.getIdCpv()));
				}
				if (codiceMandanti != null && codiceMandanti.size() > 0) {
					List<ImpresaEntry> me = new ArrayList<ImpresaEntry>();
					for (String cm : codiceMandanti) {
						if (cm != null) {
							me.add(this.getImpresa(cm));
						}
					}
					faseSubappaltoEntry.setMandanti(me);
				}
			}
				
			entry.setListaSuba(list);
			risultato.setData(entry);
		
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato durante il recupero delle informazioni di inizializzazione della fase di conclusione subappalto. codgara = "
							+ codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseFaseSubappalto getFaseConclusioneSubappalto(Long codGara, Long codLotto, Long num) {
		ResponseFaseSubappalto risultato = new ResponseFaseSubappalto();
		risultato.setEsito(true);
		try {
			FaseSubappaltoEntry fase = this.contrattiMapper.getFaseSubappalto(codGara, codLotto, num);
			List<String> codiceMandanti = this.contrattiMapper.getMandantiFaseSubappalto(codGara, codLotto, num);
			if (fase.getCodImpresaAgg() != null) {
				fase.setImpresaAggiudicatrice(this.getImpresa(fase.getCodImpresaAgg()));
			}
			if (fase.getCodImpresa() != null) {
				fase.setImpresaSubappaltatrice(this.getImpresa(fase.getCodImpresa()));
			}
			if (fase.getIdCpv() != null) {
				fase.setDescCpv(this.contrattiMapper.getCpvDesc(fase.getIdCpv()));
			}
			if (codiceMandanti != null && codiceMandanti.size() > 0) {
				List<ImpresaEntry> me = new ArrayList<ImpresaEntry>();
				for (String cm : codiceMandanti) {
					if (cm != null) {
						me.add(this.getImpresa(cm));
					}
				}
				fase.setMandanti(me);
			}
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 18L, num));
			fase.setCodiceFase(18L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura fase conclusione subappalto. codgara = " + codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseConclusioneSubappalto(FaseConclusioneSubappaltoInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());			
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			
			this.contrattiMapper.updateFaseConclusioneSubappalto(form);

			

			String idScheda = lotto.getCig() + "_18_" + calcolaIdScheda(form.getNum());
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 18L, form.getNum(), idScheda, numAppa, null);
			risultato.setData(form.getNum());
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase conclusione subappalto. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseConclusioneSubappalto(FaseConclusioneSubappaltoInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			
			this.contrattiMapper.updateFaseConclusioneSubappalto(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 18L, form.getNum());

		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase conclusione subappalto. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseConclusioneSubappalto(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 18L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase esito subappalto. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseIstanzaRecesso getFaseIstanzaRecesso(Long codGara, Long codLotto, Long num) {
		ResponseFaseIstanzaRecesso risultato = new ResponseFaseIstanzaRecesso();
		risultato.setEsito(true);
		try {
			FaseIstanzaRecessoEntry fase = this.contrattiMapper.getFaseIstanzaRecesso(codGara, codLotto, num);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 10L, num));
			fase.setCodiceFase(10L);
			fase.setNum(num);
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase istanza recesso. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseIstanzaRecesso(FaseIstanzaRecessoInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 10L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			Long numRita = this.contrattiMapper.getMaxNumRita(form.getCodGara(), form.getCodLotto());
			if (numRita == null) {
				numRita = 1L;
			} else {
				numRita++;
			}
			form.setNum(numRita);

			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			this.contrattiMapper.insertFaseIstanzaRecesso(form);
			String idScheda = lotto.getCig() + "_10_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 10L, id, idScheda, numAppa, null);
			risultato.setData(numRita);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase istanza recesso. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseIstanzaRecesso(FaseIstanzaRecessoInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseIstanzaRecesso(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 10L, form.getNum());

		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase subappalto. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseIstanzaRecesso(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseIstanzaRecesso(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 10L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase istanza recesso. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseInidoneitaTecnicoProf getFaseInidoneitaTecnicoProf(Long codGara, Long codLotto, Long num) {
		ResponseFaseInidoneitaTecnicoProf risultato = new ResponseFaseInidoneitaTecnicoProf();
		risultato.setEsito(true);
		try {
			FaseInidoneitaTecnicoProfEntry fase = this.contrattiMapper.getFaseInidoneitaTecnicoProf(codGara, codLotto,
					num);
			if (fase.getCodImpresa() != null) {
				fase.setImpresa(this.getImpresa(fase.getCodImpresa()));
			}
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 997L, num));
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase idoneita tecnico professionale. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseInidoneitaTecnicoProf(FaseInidoneitaTecnicoProfInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 997L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			Long numTpro = this.contrattiMapper.getMaxNumTpro(form.getCodGara(), form.getCodLotto());
			if (numTpro == null) {
				numTpro = 1L;
			} else {
				numTpro++;
			}
			form.setNum(numTpro);
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			this.contrattiMapper.insertFaseInidoneitaTecnicoProf(form);
			String idScheda = lotto.getCig() + "_997_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 997L, id, idScheda, numAppa, null);
			risultato.setData(numTpro);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase idoneita tecnico professionale. codgara = "
					+ form.getCodGara() + " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseInidoneitaTecnicoProf(FaseInidoneitaTecnicoProfInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseInidoneitaTecnicoProf(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 997L, form.getNum());

		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase idoneita tecnico professionale. codgara = "
					+ form.getCodGara() + " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseInidoneitaTecnicoProf(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseInidoneitaTecnicoProf(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 997L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase istanza recesso. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseInidoneitaContributiva getFaseInidoneitaContributiva(Long codGara, Long codLotto, Long num) {
		ResponseFaseInidoneitaContributiva risultato = new ResponseFaseInidoneitaContributiva();
		risultato.setEsito(true);
		try {
			FaseInidoneitaContributivaEntry fase = this.contrattiMapper.getFaseInidoneitaContributiva(codGara, codLotto,
					num);
			if (fase.getCodImpresa() != null) {
				fase.setImpresa(this.getImpresa(fase.getCodImpresa()));
			}
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 996L, num));
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase inidoneita contributiva. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseInidoneitaContributiva(FaseInidoneitaContributivaInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 996L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			Long numRego = this.contrattiMapper.getMaxNumRego(form.getCodGara(), form.getCodLotto());
			if (numRego == null) {
				numRego = 1L;
			} else {
				numRego++;
			}
			form.setNum(numRego);
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			this.contrattiMapper.insertFaseInidoneitaContributiva(form);
			String idScheda = lotto.getCig() + "_996_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 996L, id, idScheda, numAppa, null);
			risultato.setData(numRego);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase inidoneita contributiva. codgara = "
					+ form.getCodGara() + " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseInidoneitaContributiva(FaseInidoneitaContributivaInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseInidoneitaContributiva(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 996L, form.getNum());

		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase inidoneita contributiva. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseInidoneitaContributiva(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseInidoneitaContributiva(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 996L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase inidoneita contributiva. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseInadempienzeSicurezza getFaseInadempienzeSicurezza(Long codGara, Long codLotto, Long num) {
		ResponseFaseInadempienzeSicurezza risultato = new ResponseFaseInadempienzeSicurezza();
		risultato.setEsito(true);
		try {
			FaseInadempienzeSicurezzaEntry fase = this.contrattiMapper.getFaseInadempienzeSicurezza(codGara, codLotto,
					num);
			if (fase.getCodImpresa() != null) {
				fase.setImpresa(this.getImpresa(fase.getCodImpresa()));
			}
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 995L, num));
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error("Errore inaspettato in lettura fase inadempienze sicurezza. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseInadempienzeSicurezza(FaseInadempienzeSicurezzaInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 995L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			Long numInad = this.contrattiMapper.getMaxNumInad(form.getCodGara(), form.getCodLotto());
			if (numInad == null) {
				numInad = 1L;
			} else {
				numInad++;
			}
			form.setNum(numInad);
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			this.contrattiMapper.insertFaseInadempienzeSicurezza(form);
			String idScheda = lotto.getCig() + "_995_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 995L, id, idScheda, numAppa, null);
			risultato.setData(numInad);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase inadempienze sicurezza. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseInadempienzeSicurezza(FaseInadempienzeSicurezzaInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseInadempienzeSicurezza(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 995L, form.getNum());

		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase inadempienze sicurezza. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseInadempienzeSicurezza(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseInadempienzeSicurezza(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 995L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase inadempienze sicurezza. codgara = " + codGara
					+ " codLotto = " + codLotto, e);
			throw e;
		}
		return risultato;
	}

	public ResponseFaseInfortuni getFaseInfortuni(Long codGara, Long codLotto, Long num) {
		ResponseFaseInfortuni risultato = new ResponseFaseInfortuni();
		risultato.setEsito(true);
		try {
			FaseInfortuniEntry fase = this.contrattiMapper.getFaseInfortuni(codGara, codLotto, num);
			if (fase.getCodImpresa() != null) {
				fase.setImpresa(this.getImpresa(fase.getCodImpresa()));
			}
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 994L, num));
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura fase infortuni. codgara = " + codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseInfortuni(FaseInfortuniInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 994L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			Long numInforn = this.contrattiMapper.getMaxNumInfor(form.getCodGara(), form.getCodLotto());
			if (numInforn == null) {
				numInforn = 1L;
			} else {
				numInforn++;
			}
			form.setNum(numInforn);
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			this.contrattiMapper.insertFaseInfortuni(form);
			String idScheda = lotto.getCig() + "_994_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 994L, id, idScheda, numAppa, null);
			risultato.setData(numInforn);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase infortuni. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseInfortuni(FaseInfortuniInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseInfortuni(form);
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 994L, form.getNum());

		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase infortuni. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseInfortuni(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseInfortuni(codGara, codLotto, num);
			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 994L, num);
		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase infortuni. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			throw e;
		}
		return risultato;
	}

	public ResponseInizFaseCantieri getInizFaseCantieri(Long codGara, Long codLotto) {
		ResponseInizFaseCantieri risultato = new ResponseInizFaseCantieri();
		risultato.setEsito(true);
		try {
			InizFaseCantieriEntry inizFase = new InizFaseCantieriEntry();
			Long numAppa = getNumAppa(codGara, codLotto, false);
			FaseInizialeEsecuzioneEntry faseIniz = this.contrattiMapper.getFaseInizialeEsecuzioneByNumAppa(codGara,
					codLotto, numAppa);
			if (faseIniz != null) {
				inizFase.setDataInizioLavori(faseIniz.getDataVerbInizio());
			}
			List<Long> idsAppalto = this.contrattiMapper.getModalitaTipologiaLavoroLotto(codGara, codLotto);
			if (idsAppalto != null && idsAppalto.size() > 0) {
				inizFase.setIdAppalto(idsAppalto.get(0));
			}

			List<LottoDynamicValue> destinatari = new ArrayList<LottoDynamicValue>();
			List<TabellatoEntry> tabellatoDestinatari = this.contrattiMapper.getDestinatariNotificaTab();
			for (TabellatoEntry e : tabellatoDestinatari) {
				if (e.getArchiviato() == null || e.getArchiviato() != 1L) {
					LottoDynamicValue v = new LottoDynamicValue();
					v.setCodice(e.getCodice());
					v.setDescrizione(e.getDescrizione());
					v.setValue(2L);
					destinatari.add(v);
				}
			}
			inizFase.setDestinatariNotifica(destinatari);
			risultato.setData(inizFase);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura dati per inizializzazione fase conclusione contratto. codgara = "
							+ codGara + " codLotto = " + codLotto,
					e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	public ResponseFaseCantieri getFaseCantieri(Long codGara, Long codLotto, Long num) {
		ResponseFaseCantieri risultato = new ResponseFaseCantieri();
		risultato.setEsito(true);
		try {
			FaseCantieriEntry fase = this.contrattiMapper.getFaseCantieri(codGara, codLotto, num);

			List<FaseCantieriImpEntry> listaImprese = this.contrattiMapper.getImpreseFaseCantieri(codGara, codLotto,
					num);
			if (listaImprese != null && listaImprese.size() > 0) {
				for (FaseCantieriImpEntry fci : listaImprese) {
					if (StringUtils.isNotBlank(fci.getCodiceImpresa())) {
						ImpresaEntry impresa = this.getImpresa(fci.getCodiceImpresa());
						fci.setImpresa(impresa);
					}
				}
			}
			fase.setImprese(listaImprese);

			List<LottoDynamicValue> destinatari = new ArrayList<LottoDynamicValue>();
			List<TabellatoEntry> tabellatoDestinatari = this.contrattiMapper.getDestinatariNotificaTab();
			List<Long> codiciDestinatari = this.contrattiMapper.getDestinatariNotificaCantiere(codGara, codLotto, num);
			for (TabellatoEntry e : tabellatoDestinatari) {
				if (e.getArchiviato() == null || e.getArchiviato() != 1L) {
					LottoDynamicValue v = new LottoDynamicValue();
					v.setCodice(e.getCodice());
					v.setDescrizione(e.getDescrizione());
					v.setValue(codiciDestinatari.contains(e.getCodice()) ? 1L : 2L);
					destinatari.add(v);
				}
			}
			fase.setDestinatariNotifica(destinatari);
			fase.setPubblicata(isFasePubblicata(codGara, codLotto, 998L, num));
			risultato.setData(fase);
		} catch (Exception e) {
			logger.error(
					"Errore inaspettato in lettura fase cantieri. codgara = " + codGara + " codLotto = " + codLotto, e);
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public ResponseInsert insertFaseCantieri(FaseCantieriInsertForm form) throws Exception {
		ResponseInsert risultato = new ResponseInsert();
		risultato.setEsito(true);
		try {
			LottoEntry lotto = this.contrattiMapper.getDettaglioLotto(form.getCodGara(), form.getCodLotto());
			Long id = this.contrattiMapper.getMaxNumW9fase(form.getCodGara(), form.getCodLotto(), 998L);
			if (id == null) {
				id = 1L;
			} else {
				id++;
			}
			Long numCant = this.contrattiMapper.getMaxNumCant(form.getCodGara(), form.getCodLotto());
			if (numCant == null) {
				numCant = 1L;
			} else {
				numCant++;
			}
			form.setNum(numCant);
			Long numAppa = this.getNumAppa(form.getCodGara(), form.getCodLotto(), false);
			form.setNumAppa(numAppa);
			this.contrattiMapper.insertFaseCantieri(form);
			Long counter = 1L;
			for (FaseCantieriImpEntry i : form.getImprese()) {
				i.setCodGara(form.getCodGara());
				i.setCodLotto(form.getCodLotto());
				i.setNum(counter);
				i.setNumCant(id);
				this.contrattiMapper.insertImpreseFaseCantieri(i);
				counter++;
			}
			for (LottoDynamicValue v : form.getDestinatariNotifica()) {
				if (v.getValue() == 1L) {
					this.contrattiMapper.insertDestinatarioFaseCantieri(form.getCodGara(), form.getCodLotto(), id,
							v.getCodice());
				}
			}
			String idScheda = lotto.getCig() + "_998_" + calcolaIdScheda(id);
			this.contrattiMapper.insertW9fase(form.getCodGara(), form.getCodLotto(), 998L, id, idScheda, numAppa, null);
			risultato.setData(numCant);
		} catch (Exception e) {
			logger.error("Errore inaspettato in inserimento fase cantieri. codgara = " + form.getCodGara()
					+ " codLotto = " + form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse updateFaseCantieri(FaseCantieriInsertForm form) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.updateFaseCantieri(form);
			this.contrattiMapper.deleteImpreseFaseCantieri(form.getCodGara(), form.getCodLotto(), form.getNum());
			this.contrattiMapper.deleteDestinatarioFaseCantieri(form.getCodGara(), form.getCodLotto(), form.getNum());
			Long counter = 1L;
			for (FaseCantieriImpEntry i : form.getImprese()) {
				i.setCodGara(form.getCodGara());
				i.setCodLotto(form.getCodLotto());
				i.setNum(counter);
				i.setNumCant(form.getNum());
				this.contrattiMapper.insertImpreseFaseCantieri(i);
				counter++;
			}
			for (LottoDynamicValue v : form.getDestinatariNotifica()) {
				if (v.getValue() == 1L) {
					this.contrattiMapper.insertDestinatarioFaseCantieri(form.getCodGara(), form.getCodLotto(),
							form.getNum(), v.getCodice());
				}
			}
			this.contrattiMapper.setW9faseDaExport(form.getCodGara(), form.getCodLotto(), 998L, form.getNum());

		} catch (Exception e) {
			logger.error("Errore inaspettato in modifica fase cantieri. codgara = " + form.getCodGara() + " codLotto = "
					+ form.getCodLotto(), e);
			throw e;
		}
		return risultato;
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public BaseResponse deleteFaseCantieri(Long codGara, Long codLotto, Long num) throws Exception {
		BaseResponse risultato = new BaseResponse();
		risultato.setEsito(true);
		try {
			this.contrattiMapper.deleteFaseCantieri(codGara, codLotto, num);
			this.contrattiMapper.deleteImpreseFaseCantieri(codGara, codLotto, num);
			this.contrattiMapper.deleteDestinatarioFaseCantieri(codGara, codLotto, num);

			this.contrattiMapper.deleteW9Fase(codGara, codLotto, 998L, num);
			List<String> sezioni = getSezioneIncaricoByFase(998L);
			for (String sezione : sezioni) {
				this.contrattiMapper.deleteIncarichiProf(codGara, codLotto, sezione, num);
			}

		} catch (Exception e) {
			logger.error("Errore inaspettato in cancellazione fase cantieri. codgara = " + codGara + " codLotto = "
					+ codLotto, e);
			throw e;
		}
		return risultato;
	}

	private static String calcolaIdScheda(Long num) {
		String output = num.toString();
		while (output.length() < 3)
			output = "0" + output;
		return output;
	}

	public ImpresaEntry getImpresa(final String codiceImpresa) {
		if (StringUtils.isNotBlank(codiceImpresa)) {
			ImpresaEntry impresa = this.contrattiMapper.getImpresa(codiceImpresa);
			LegaleRappresentanteEntry legale = this.contrattiMapper.getLegaleImpresa(codiceImpresa);
			impresa.setRappresentante(legale);
			if (impresa.getComune() != null && !"".equals(impresa.getComune())) {
				TabellatoIstatEntry comune = this.contrattiMapper.getComuneByDesc(impresa.getComune().toUpperCase());
				if (comune != null) {
					impresa.setCodComune(comune.getCodice());
				}
			}
			return impresa;
		}
		return null;
	}

	public ResponseRequestFase getRequestPubbFase(Long codGara, Long codLotto, Long codFase, Long num) {
		ResponseRequestFase risultato = new ResponseRequestFase();
		Long numAppa = getNumAppa(codGara, codLotto, false);
		risultato.setEsito(true);
		String jsonFase = "";
		try {
			switch (codFase.intValue()) {
			case 101:
				jsonFase = getRequestFaseImprese(codGara, codLotto, num);
				break;
			case 984:
				jsonFase = getRequestFaseComEsito(codGara, codLotto, num);
				break;
			case 1:
				jsonFase = getRequestFaseAggiudicazione(codGara, codLotto, num);
				break;
			case 987:
				jsonFase = getRequestFaseAggiudicazioneSemp(codGara, codLotto, num);
				break;
			case 12:
				jsonFase = getRequestFaseAdesioneAccordoQuadro(codGara, codLotto, num, numAppa);
				break;
			case 996:
				jsonFase = getRequestFaseInidoneitaContributiva(codGara, codLotto, num, numAppa);
				break;
			case 2:
				jsonFase = getRequestFaseIniziale(codGara, codLotto, num, numAppa);
				break;
			case 986:
				jsonFase = getRequestFaseInizialeSemplificata(codGara, codLotto, num, numAppa); 
				break;
			case 11:
				jsonFase = getRequestFaseStipulaAccordoQuadro(codGara, codLotto, num, numAppa);
				break;
			case 3:
				jsonFase = getRequestFaseAvanzamento(codGara, codLotto, num, numAppa);
				break;
			case 102:
				jsonFase = getRequestFaseAvanzamentoSemp(codGara, codLotto, num, numAppa); 
				break;
			case 4:
				jsonFase = getRequestFaseConclusione(codGara, codLotto, num, numAppa);
				break;
			case 985:
				jsonFase = getRequestFaseConclusioneSemplificata(codGara, codLotto, num, numAppa);
				break;
			case 5:
				jsonFase = getRequestFaseCollaudo(codGara, codLotto, num, numAppa);
				break;
			case 6:
				jsonFase = getRequestFaseSospensione(codGara, codLotto, num, numAppa);
				break;
			case 7:
				jsonFase = getRequestFaseVariante(codGara, codLotto, num, numAppa);
				break;
			case 8:
				jsonFase = getRequestFaseAccordoBonario(codGara, codLotto, num, numAppa);
				break;
			case 9:
				jsonFase = getRequestFaseSubappalto(codGara, codLotto, num, numAppa);
				break;
			case 10:
				jsonFase = getRequestFaseIstanzaRecesso(codGara, codLotto, num, numAppa);
				break;
			case 997:
				jsonFase = getRequestFaseInidoneitaTecnicoProf(codGara, codLotto, num, numAppa);
				break;
			case 995:
				jsonFase = getRequestFaseInadempienzeSicurezza(codGara, codLotto, num, numAppa);
				break;
			case 994:
				jsonFase = getRequestFaseInfortuni(codGara, codLotto, num, numAppa);
				break;
			case 998:
				jsonFase = getRequestFaseCantieri(codGara, codLotto, num, numAppa);
				break;
			}
			risultato.setData(jsonFase);
			return risultato;
		} catch (Exception e) {
			risultato.setEsito(false);
			risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
			logger.error("Si è verificato un errore nella get della requesta per la fase " + codFase + ", codgara= "
					+ codGara + " codLotto= " + codLotto + " num= " + num, e);
		}
		return risultato;
	}

	private String getRequestFaseCantieri(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseCantieriPubbEntry risultato = new FaseCantieriPubbEntry();
		FaseCantieriEntry faseEsistente = this.contrattiMapper.getFaseCantieri(codGara, codLotto, num);
		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setNum(faseEsistente.getNum());
			risultato.setCivico(faseEsistente.getCivico());
			risultato.setCodIstatComune(faseEsistente.getCodIstatComune());
			risultato.setCoordX(faseEsistente.getCoordX());
			risultato.setCoordY(faseEsistente.getCoordY());
			risultato.setDataInizio(faseEsistente.getDataInizio());
			risultato.setDescInfrsatrutturaRete(faseEsistente.getDescInfrsatrutturaRete());
			risultato.setDurataPresunta(faseEsistente.getDurataPresunta());
			risultato.setIndirizzoCantiere(faseEsistente.getIndirizzoCantiere());
			risultato.setInfrastrutturaReteA(faseEsistente.getInfrastrutturaReteA());
			risultato.setInfrastrutturaReteDa(faseEsistente.getInfrastrutturaReteDa());
			risultato.setLatitudine(faseEsistente.getLatitudine());
			risultato.setLongitudine(faseEsistente.getLongitudine());
			risultato.setNum(num);
			risultato.setNumeroImprese(faseEsistente.getNumeroImprese());
			risultato.setNumLavoratoriAutonomi(faseEsistente.getNumLavoratoriAutonomi());
			risultato.setProvincia(faseEsistente.getProvincia());
			risultato.setNumMaxLavoratori(faseEsistente.getNumMaxLavoratori());
			risultato.setTipoIntervento(faseEsistente.getTipoIntervento());
			risultato.setTipologiaCostruttiva(faseEsistente.getTipologiaCostruttiva());
			risultato.setTipoOpera(faseEsistente.getTipoOpera());
			risultato.setUlterioreMail(faseEsistente.getUlterioreMail());
			risultato.setNumAppa(numAppa);
		}

		List<FaseCantieriImpEntry> listaImprese = this.contrattiMapper.getImpreseFaseCantieri(codGara, codLotto, num);
		if (listaImprese != null && listaImprese.size() > 0) {
			for (FaseCantieriImpEntry fci : listaImprese) {
				if (StringUtils.isNotBlank(fci.getCodiceImpresa())) {
					fci.setImpresa(this.getImpresa(fci.getCodiceImpresa()));
				}
			}
		}
		risultato.setImprese(listaImprese);
		List<LottoDynamicValue> destinatari = new ArrayList<LottoDynamicValue>();
		List<Long> codiciDestinatari = this.contrattiMapper.getDestinatariNotificaCantiere(codGara, codLotto, num);
		for (Long d : codiciDestinatari) {
			LottoDynamicValue v = new LottoDynamicValue();
			v.setCodice(d);
			destinatari.add(v);
		}
		risultato.setDestinatariNotifica(destinatari);

		List<String> sezioni = getSezioneIncaricoByFase(998L);
		List<IncarichiProfPubbEntry> incarichi = new ArrayList<IncarichiProfPubbEntry>();

		for (String sezione : sezioni) {
			List<IncarichiProfPubbEntry> incarichiPerSezione = this.contrattiMapper.getIncarichiFasePubb(codGara,
					codLotto, num, sezione);
			if (incarichiPerSezione != null && !incarichiPerSezione.isEmpty()) {
				incarichi.addAll(incarichiPerSezione);
			}
		}
		for (IncarichiProfPubbEntry i : incarichi) {
			if (i.getCodiceTecnico() != null) {
				i.setTecnico(this.contrattiMapper.getTecnicoPubb(i.getCodiceTecnico()));
			}
		}
		risultato.setIncarichiProfessionali(incarichi);
		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseInfortuni(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseInfortuniPubbEntry risultato = new FaseInfortuniPubbEntry();
		FaseInfortuniEntry faseEsistente = this.contrattiMapper.getFaseInfortuni(codGara, codLotto, num);
		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setNum(faseEsistente.getNum());
			risultato.setCodImpresa(faseEsistente.getCodImpresa());
			risultato.setGiornateRiconosciute(faseEsistente.getGiornateRiconosciute());
			risultato.setInfortuniMortali(faseEsistente.getInfortuniMortali());
			risultato.setInfortuniPermanenti(faseEsistente.getInfortuniPermanenti());
			risultato.setTotaleInfortuni(faseEsistente.getTotaleInfortuni());
			if (risultato.getCodImpresa() != null) {
				risultato.setImpresa(this.contrattiMapper.getImpresaForRequestFase(risultato.getCodImpresa()));
			}
			risultato.setNumAppa(numAppa);
		}
		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseInadempienzeSicurezza(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseInadempienzeSicurezzaPubbEntry risultato = new FaseInadempienzeSicurezzaPubbEntry();
		FaseInadempienzeSicurezzaEntry faseEsistente = this.contrattiMapper.getFaseInadempienzeSicurezza(codGara,
				codLotto, num);
		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setNum(faseEsistente.getNum());
			risultato.setDescrizione(faseEsistente.getDescrizione());
			risultato.setCodImpresa(faseEsistente.getCodImpresa());
			risultato.setComma3A(faseEsistente.getComma3A());
			risultato.setComma3B(faseEsistente.getComma3B());
			risultato.setComma45A(faseEsistente.getComma45A());
			risultato.setComma5(faseEsistente.getComma5());
			risultato.setComma6(faseEsistente.getComma6());
			risultato.setCommaAltro(faseEsistente.getCommaAltro());
			risultato.setDataInadempienza(faseEsistente.getDataInadempienza());
			if (risultato.getCodImpresa() != null) {
				risultato.setImpresa(this.contrattiMapper.getImpresaForRequestFase(risultato.getCodImpresa()));
			}
			risultato.setNumAppa(numAppa);
		}
		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseInidoneitaContributiva(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseInidoneitaContributivaPubbEntry risultato = new FaseInidoneitaContributivaPubbEntry();
		FaseInidoneitaContributivaEntry faseEsistente = this.contrattiMapper.getFaseInidoneitaContributiva(codGara,
				codLotto, num);
		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setNum(faseEsistente.getNum());
			risultato.setDescrizione(faseEsistente.getDescrizione());
			risultato.setCassaEdile(faseEsistente.getCassaEdile());
			risultato.setDataComunicazione(faseEsistente.getDataComunicazione());
			risultato.setDataDurc(faseEsistente.getDataDurc());
			risultato.setEstremiDurc(faseEsistente.getEstremiDurc());
			risultato.setRiscontroIrregolare(faseEsistente.getRiscontroIrregolare());
			risultato.setCodImpresa(faseEsistente.getCodImpresa());
			risultato.setNumAppa(numAppa);
			if (risultato.getCodImpresa() != null) {
				risultato.setImpresa(this.contrattiMapper.getImpresaForRequestFase(risultato.getCodImpresa()));
			}
		}
		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseInidoneitaTecnicoProf(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseInidoneitaTecnicoProfPubbEntry risultato = new FaseInidoneitaTecnicoProfPubbEntry();
		FaseInidoneitaTecnicoProfEntry faseEsistente = this.contrattiMapper.getFaseInidoneitaTecnicoProf(codGara,
				codLotto, num);
		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setNum(faseEsistente.getNum());
			risultato.setAltreCause(faseEsistente.getAltreCause());
			risultato.setCodImpresa(faseEsistente.getCodImpresa());
			risultato.setDataUni(faseEsistente.getDataUni());
			risultato.setDescrizione(faseEsistente.getDescrizione());
			risultato.setInadeguataFormazioneSicurezza(faseEsistente.getInadeguataFormazioneSicurezza());
			risultato.setMancataNominaMedico(faseEsistente.getMancataNominaMedico());
			risultato.setMancataNominaResp(faseEsistente.getMancataNominaResp());
			risultato.setMancatoDocValutazioneRischi(faseEsistente.getMancatoDocValutazioneRischi());
			risultato.setNonIdoneitaVerificaIscrizione(faseEsistente.getNonIdoneitaVerificaIscrizione());
			risultato.setNonIndicatiContratti(faseEsistente.getNonIndicatiContratti());
			if (risultato.getCodImpresa() != null) {
				risultato.setImpresa(this.contrattiMapper.getImpresaForRequestFase(risultato.getCodImpresa()));
			}
			risultato.setNumAppa(numAppa);
		}
		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseIstanzaRecesso(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseIstanzaRecessoPubbEntry risultato = new FaseIstanzaRecessoPubbEntry();
		FaseIstanzaRecessoEntry faseEsistente = this.contrattiMapper.getFaseIstanzaRecesso(codGara, codLotto, num);

		DatiIdSchedeW9FasiEntry idSchede = this.contrattiMapper.getIdSchedeW9Fasi(codGara, codLotto,
				new Long(CostantiW9.ISTANZA_RECESSO), numAppa);

		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setNum(faseEsistente.getNum());
			risultato.setDataConsegna(faseEsistente.getDataConsegna());
			risultato.setDataIstanzaRecesso(faseEsistente.getDataIstanzaRecesso());
			risultato.setDataTermine(faseEsistente.getDataTermine());
			risultato.setDurataSospensione(faseEsistente.getDurataSospensione());
			risultato.setFlagAccolta(faseEsistente.getFlagAccolta());
			risultato.setFlagRipresa(faseEsistente.getFlagRipresa());
			risultato.setFlagRiserva(faseEsistente.getFlagRiserva());
			risultato.setFlagTardiva(faseEsistente.getFlagTardiva());
			risultato.setImportoOneri(faseEsistente.getImportoOneri());
			risultato.setImportoSpese(faseEsistente.getImportoSpese());
			risultato.setMotivoSospensione(faseEsistente.getMotivoSospensione());
			risultato.setRitardo(faseEsistente.getRitardo());
			risultato.setTipoComunicazione(faseEsistente.getTipoComunicazione());
			risultato.setNumAppa(numAppa);

			if (idSchede != null) {
				if (StringUtils.isNotBlank(idSchede.getIdSchedaLocale())) {
					risultato.setIdSchedaLocale(idSchede.getIdSchedaLocale());
				}
				if (StringUtils.isNotBlank(idSchede.getIdSchedaSimog())) {
					risultato.setIdSchedaSimog(idSchede.getIdSchedaSimog());
				}
			}

		}
		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseSubappalto(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseSubappaltoPubbEntry risultato = new FaseSubappaltoPubbEntry();
		FaseSubappaltoEntry faseEsistente = this.contrattiMapper.getFaseSubappalto(codGara, codLotto, num);

		DatiIdSchedeW9FasiEntry idSchede = this.contrattiMapper.getIdSchedeW9Fasi(codGara, codLotto,
				new Long(CostantiW9.SUBAPPALTO), numAppa);
		List<String> codiceMandanti = this.contrattiMapper.getMandantiFaseSubappalto(codGara, codLotto, num);
		List<ImpresaFasePubbEntry> mandanti = new ArrayList<ImpresaFasePubbEntry>();
		if (codiceMandanti != null && codiceMandanti.size() > 0) {
			for (String man : codiceMandanti) {
				if (man != null) {
					mandanti.add(this.getImpresaForRequestFase(man));
				}
			}
		}
		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setNum(faseEsistente.getNum());
			risultato.setCodImpresa(faseEsistente.getCodImpresa());
			risultato.setCodImpresaAgg(faseEsistente.getCodImpresaAgg());
			risultato.setDataAuth(faseEsistente.getDataAuth());
			risultato.setIdCategoria(faseEsistente.getIdCategoria());
			risultato.setIdCpv(faseEsistente.getIdCpv());
			risultato.setImportoEffettivo(faseEsistente.getImportoEffettivo());
			risultato.setImportoPresunto(faseEsistente.getImportoPresunto());
			risultato.setOggetto(faseEsistente.getOggetto());
			if (mandanti != null) {
				risultato.setMandanti(mandanti);
			}
			if (risultato.getCodImpresa() != null) {
				risultato.setImpresa(this.getImpresaForRequestFase(risultato.getCodImpresa()));
			}
			if (risultato.getCodImpresaAgg() != null) {
				risultato.setImpresaAgg(this.getImpresaForRequestFase(risultato.getCodImpresaAgg()));
			}

			if (idSchede != null) {
				if (StringUtils.isNotBlank(idSchede.getIdSchedaLocale())) {
					risultato.setIdSchedaLocale(idSchede.getIdSchedaLocale());
				}
				if (StringUtils.isNotBlank(idSchede.getIdSchedaSimog())) {
					risultato.setIdSchedaSimog(idSchede.getIdSchedaSimog());
				}
			}

			risultato.setNumAppa(numAppa);
		}
		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseAccordoBonario(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseAccordoBonarioPubbEntry risultato = new FaseAccordoBonarioPubbEntry();
		FaseAccordoBonarioEntry faseEsistente = this.contrattiMapper.getFaseAccordoBonario(codGara, codLotto, num);

		DatiIdSchedeW9FasiEntry idSchede = this.contrattiMapper.getIdSchedeW9Fasi(codGara, codLotto,
				new Long(CostantiW9.ACCORDO_BONARIO), numAppa);

		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setNum(faseEsistente.getNum());
			risultato.setDataAccordo(faseEsistente.getDataAccordo());
			risultato.setNumRiserve(faseEsistente.getNumRiserve());
			risultato.setOneriDerivanti(faseEsistente.getOneriDerivanti());
			risultato.setNumAppa(numAppa);

			if (idSchede != null) {
				if (StringUtils.isNotBlank(idSchede.getIdSchedaLocale())) {
					risultato.setIdSchedaLocale(idSchede.getIdSchedaLocale());
				}
				if (StringUtils.isNotBlank(idSchede.getIdSchedaSimog())) {
					risultato.setIdSchedaSimog(idSchede.getIdSchedaSimog());
				}
			}

		}
		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseSospensione(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseSospensionePubbEntry risultato = new FaseSospensionePubbEntry();
		FaseSospensioneEntry faseEsistente = this.contrattiMapper.getFaseSospensione(codGara, codLotto, num);

		DatiIdSchedeW9FasiEntry idSchede = this.contrattiMapper.getIdSchedeW9Fasi(codGara, codLotto,
				new Long(CostantiW9.SOSPENSIONE_CONTRATTO), numAppa);

		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setDataVerbRipr(faseEsistente.getDataVerbRipr());
			risultato.setDataVerbSosp(faseEsistente.getDataVerbSosp());
			risultato.setFlagRiserve(faseEsistente.getFlagRiserve());
			risultato.setFlagSuperoTempo(faseEsistente.getFlagSuperoTempo());
			risultato.setFlagVerbale(faseEsistente.getFlagVerbale());
			risultato.setMotivoSosp(faseEsistente.getMotivoSosp());
			risultato.setNum(faseEsistente.getNum());
			risultato.setNumAppa(numAppa);

			if (idSchede != null) {
				if (StringUtils.isNotBlank(idSchede.getIdSchedaLocale())) {
					risultato.setIdSchedaLocale(idSchede.getIdSchedaLocale());
				}
				if (StringUtils.isNotBlank(idSchede.getIdSchedaSimog())) {
					risultato.setIdSchedaSimog(idSchede.getIdSchedaSimog());
				}
			}

		}
		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseVariante(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseVariantePubbEntry risultato = new FaseVariantePubbEntry();
		FaseVarianteEntry faseEsistente = this.contrattiMapper.getFaseVariante(codGara, codLotto, num);

		DatiIdSchedeW9FasiEntry idSchede = this.contrattiMapper.getIdSchedeW9Fasi(codGara, codLotto,
				new Long(CostantiW9.VARIANTE_CONTRATTO), numAppa);

		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setNum(faseEsistente.getNum());
			risultato.setAltreMotivazioni(faseEsistente.getAltreMotivazioni());
			risultato.setDataAttoAggiuntivo(faseEsistente.getDataAttoAggiuntivo());
			risultato.setDataVerbaleAppr(faseEsistente.getDataVerbaleAppr());
			risultato.setFlagImporti(faseEsistente.getFlagImporti());
			risultato.setFlagSicurezza(faseEsistente.getFlagSicurezza());
			risultato.setFlagVariante(faseEsistente.getFlagVariante());
			risultato.setImportoComplAppalto(faseEsistente.getImportoComplAppalto());
			risultato.setImportoComplIntervento(faseEsistente.getImportoComplIntervento());
			risultato.setImportoDisposizione(faseEsistente.getImportoDisposizione());
			risultato.setImportoNonAssog(faseEsistente.getImportoNonAssog());
			risultato.setImportoProgettazione(faseEsistente.getImportoProgettazione());
			risultato.setImportoRideterminatoForniture(faseEsistente.getImportoRideterminatoForniture());
			risultato.setImportoRideterminatoLavori(faseEsistente.getImportoRideterminatoLavori());
			risultato.setImportoRideterminatoServizi(faseEsistente.getImportoRideterminatoServizi());
			risultato.setImportoSicurezza(faseEsistente.getImportoSicurezza());
			risultato.setImportoSubtotale(faseEsistente.getImportoSubtotale());
			risultato.setNumGiorniProroga(faseEsistente.getNumGiorniProroga());
			risultato.setQuintoObbligo(faseEsistente.getQuintoObbligo());
			risultato.setCigNuovaProc(faseEsistente.getCigNuovaProc());
			risultato.setUrlVariantiCo(faseEsistente.getUrlVariantiCo());
			risultato.setMotivoRevPrezzi(faseEsistente.getMotivoRevPrezzi());

			if (idSchede != null) {
				if (StringUtils.isNotBlank(idSchede.getIdSchedaLocale())) {
					risultato.setIdSchedaLocale(idSchede.getIdSchedaLocale());
				}
				if (StringUtils.isNotBlank(idSchede.getIdSchedaSimog())) {
					risultato.setIdSchedaSimog(idSchede.getIdSchedaSimog());
				}
			}

			List<LottoDynamicValue> motivazioni = new ArrayList<LottoDynamicValue>();
			List<Long> idMotivazioni = this.contrattiMapper.getMotiviVariazioneByFase(codGara, codLotto, num);
			for (Long id : idMotivazioni) {
				LottoDynamicValue v = new LottoDynamicValue();
				v.setCodice(id);
				motivazioni.add(v);
			}
			risultato.setMotivazioniVariante(motivazioni);
			risultato.setNumAppa(numAppa);
		}
		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseCollaudo(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseCollaudoPubbEntry risultato = new FaseCollaudoPubbEntry();
		FaseCollaudoEntry faseEsistente = this.contrattiMapper.getFaseCollaudo(codGara, codLotto, num);

		DatiIdSchedeW9FasiEntry idSchede = this.contrattiMapper.getIdSchedeW9Fasi(codGara, codLotto,
				new Long(CostantiW9.COLLAUDO_CONTRATTO), numAppa);

		risultato.setCodGara(codGara);
		risultato.setCodLotto(codLotto);
		risultato.setDataApprovazione(faseEsistente.getDataApprovazione());
		risultato.setDataCertEsecuzione(faseEsistente.getDataCertEsecuzione());
		risultato.setDataCollaudoStatico(faseEsistente.getDataCollaudoStatico());
		risultato.setDataDelibera(faseEsistente.getDataDelibera());
		risultato.setDataFineRiserveAmm(faseEsistente.getDataFineRiserveAmm());
		risultato.setDataFineRiserveGiu(faseEsistente.getDataFineRiserveGiu());
		risultato.setDataFineRiserveTra(faseEsistente.getDataFineRiserveTra());
		risultato.setDataInizioOperazioni(faseEsistente.getDataInizioOperazioni());
		risultato.setDataInizioRiserveAmm(faseEsistente.getDataInizioRiserveAmm());
		risultato.setDataInizioRiserveArb(faseEsistente.getDataInizioRiserveArb());
		risultato.setDataInizioRiserveGiu(faseEsistente.getDataInizioRiserveGiu());
		risultato.setDataInizioRiserveTra(faseEsistente.getDataInizioRiserveTra());
		risultato.setDataNomina(faseEsistente.getDataNomina());
		risultato.setDataRedazioneCertificato(faseEsistente.getDataRedazioneCertificato());
		risultato.setEsitoCollaudo(faseEsistente.getEsitoCollaudo());
		risultato.setFlagImporti(faseEsistente.getFlagImporti());
		risultato.setFlagSicurezza(faseEsistente.getFlagSicurezza());
		risultato.setFlagSingoloCommissione(faseEsistente.getFlagSingoloCommissione());
		risultato.setFlagSubappaltatori(faseEsistente.getFlagSubappaltatori());
		risultato.setImportoComplessivoAppalto(faseEsistente.getImportoComplessivoAppalto());
		risultato.setImportoComplessivoIntervento(faseEsistente.getImportoComplessivoIntervento());
		risultato.setImportoDisposizione(faseEsistente.getImportoDisposizione());
		risultato.setImportoEventualeDefAmm(faseEsistente.getImportoEventualeDefAmm());
		risultato.setImportoEventualeDefArb(faseEsistente.getImportoEventualeDefArb());
		risultato.setImportoEventualeDefGiu(faseEsistente.getImportoEventualeDefGiu());
		risultato.setImportoEventualeDefTra(faseEsistente.getImportoEventualeDefTra());
		risultato.setImportoFinaleForniture(faseEsistente.getImportoFinaleForniture());
		risultato.setImportoFinaleLavori(faseEsistente.getImportoFinaleLavori());
		risultato.setImportoFinaleServizi(faseEsistente.getImportoFinaleServizi());
		risultato.setImportoFinaleSicurezza(faseEsistente.getImportoFinaleSicurezza());
		risultato.setImportoProgettazione(faseEsistente.getImportoProgettazione());
		risultato.setImportoSubtotale(faseEsistente.getImportoSubtotale());
		risultato.setImportoTotaleRichiestoAmm(faseEsistente.getImportoTotaleRichiestoAmm());
		risultato.setImportoTotaleRichiestoArb(faseEsistente.getImportoTotaleRichiestoArb());
		risultato.setImportoTotaleRichiestoGiu(faseEsistente.getImportoTotaleRichiestoGiu());
		risultato.setImportoTotaleRichiestoTra(faseEsistente.getImportoTotaleRichiestoTra());
		risultato.setLavoriEstesi(faseEsistente.getLavoriEstesi());
		risultato.setModalitaCollaudo(faseEsistente.getModalitaCollaudo());
		risultato.setNum(num);
		risultato.setNumeroRiserveDaDefinireAmm(faseEsistente.getNumeroRiserveDaDefinireAmm());
		risultato.setNumeroRiserveDaDefinireArb(faseEsistente.getNumeroRiserveDaDefinireArb());
		risultato.setNumeroRiserveDaDefinireGiu(faseEsistente.getNumeroRiserveDaDefinireGiu());
		risultato.setNumeroRiserveDaDefinireTra(faseEsistente.getNumeroRiserveDaDefinireTra());
		risultato.setNumeroRiserveDefiniteAmm(faseEsistente.getNumeroRiserveDefiniteAmm());
		risultato.setNumeroRiserveDefiniteArb(faseEsistente.getNumeroRiserveDefiniteArb());
		risultato.setNumeroRiserveDefiniteGiu(faseEsistente.getNumeroRiserveDefiniteGiu());
		risultato.setNumeroRiserveDefiniteTra(faseEsistente.getNumeroRiserveDefiniteTra());
		risultato.setTipoCollaudo(faseEsistente.getTipoCollaudo());
		if (idSchede != null) {
			if (StringUtils.isNotBlank(idSchede.getIdSchedaLocale())) {
				risultato.setIdSchedaLocale(idSchede.getIdSchedaLocale());
			}
			if (StringUtils.isNotBlank(idSchede.getIdSchedaSimog())) {
				risultato.setIdSchedaSimog(idSchede.getIdSchedaSimog());
			}
		}

		List<String> sezioni = getSezioneIncaricoByFase(5L);
		List<IncarichiProfPubbEntry> incarichi = new ArrayList<IncarichiProfPubbEntry>();
		risultato.setNumAppa(numAppa);

		for (String sezione : sezioni) {
			List<IncarichiProfPubbEntry> incarichiPerSezione = this.contrattiMapper.getIncarichiFasePubb(codGara,
					codLotto, num, sezione);
			if (incarichiPerSezione != null && !incarichiPerSezione.isEmpty()) {
				incarichi.addAll(incarichiPerSezione);
			}

		}
		for (IncarichiProfPubbEntry i : incarichi) {
			if (i.getCodiceTecnico() != null) {
				i.setTecnico(this.contrattiMapper.getTecnicoPubb(i.getCodiceTecnico()));
			}
		}

		risultato.setIncarichiProfessionali(incarichi);
		return new ObjectMapper().writeValueAsString(risultato);

	}

	private String getRequestFaseConclusioneSemplificata(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseConclusioneSempEntry faseEsistente = this.contrattiMapper.getFaseConclusioneSempContratto(codGara, codLotto,
				num);
		FaseConclusioneSemplificataPubbEntry risultato = new FaseConclusioneSemplificataPubbEntry();
		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setDataRisoluzione(faseEsistente.getDataRisoluzione());
			risultato.setDataUltimazione(faseEsistente.getDataUltimazione());
			risultato.setFlagOneri(faseEsistente.getFlagOneri());
			risultato.setFlagPolizza(faseEsistente.getFlagPolizza());
			risultato.setImportoOneri(faseEsistente.getImportoOneri());
			risultato.setInterruzioneAnticipata(faseEsistente.getInterruzioneAnticipata());
			risultato.setMotivoInterruzione(faseEsistente.getMotivoInterruzione());
			risultato.setMotivoRisoluzione(faseEsistente.getMotivoRisoluzione());
			risultato.setNum(num);
			risultato.setOreLavorate(faseEsistente.getOreLavorate());
			risultato.setNumAppa(numAppa);
		}
		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseConclusione(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseConclusioneEntry faseEsistente = this.contrattiMapper.getFaseConclusioneContratto(codGara, codLotto, num);

		DatiIdSchedeW9FasiEntry idSchede = this.contrattiMapper.getIdSchedeW9Fasi(codGara, codLotto,
				new Long(CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA), numAppa);

		FaseConclusionePubbEntry risultato = new FaseConclusionePubbEntry();
		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setDataRisoluzione(faseEsistente.getDataRisoluzione());
			risultato.setDataTermineContrattuale(faseEsistente.getDataTermineContrattuale());
			risultato.setDataUltimazione(faseEsistente.getDataUltimazione());
			risultato.setDataVerbaleConsegna(faseEsistente.getDataVerbaleConsegna());
			risultato.setFlagOneri(faseEsistente.getFlagOneri());
			risultato.setFlagPolizza(faseEsistente.getFlagPolizza());
			risultato.setImportoOneri(faseEsistente.getImportoOneri());
			risultato.setInterruzioneAnticipata(faseEsistente.getInterruzioneAnticipata());
			risultato.setMotivoInterruzione(faseEsistente.getMotivoInterruzione());
			risultato.setMotivoRisoluzione(faseEsistente.getMotivoRisoluzione());
			risultato.setNum(num);
			risultato.setNumgiorniProroga(faseEsistente.getNumgiorniProroga());
			risultato.setNumInfortuni(faseEsistente.getNumInfortuni());
			risultato.setNumInfortuniMortali(faseEsistente.getNumInfortuniMortali());
			risultato.setNumInfortuniPermanenti(faseEsistente.getNumInfortuniPermanenti());
			risultato.setOreLavorate(faseEsistente.getOreLavorate());
			risultato.setNumAppa(numAppa);

			if (idSchede != null) {
				if (StringUtils.isNotBlank(idSchede.getIdSchedaLocale())) {
					risultato.setIdSchedaLocale(idSchede.getIdSchedaLocale());
				}
				if (StringUtils.isNotBlank(idSchede.getIdSchedaSimog())) {
					risultato.setIdSchedaSimog(idSchede.getIdSchedaSimog());
				}
			}

		}
		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseStipulaAccordoQuadro(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseStipulaAccordoQuadroEntry faseEsistente = this.contrattiMapper.getFaseStipulaAccordoQuadro(codGara,
				codLotto, num);
		FaseStipulaPubbEsitoForm esito = this.contrattiMapper.getFaseStipulaPubbEsito(codGara, codLotto, num);

		DatiIdSchedeW9FasiEntry idSchede = this.contrattiMapper.getIdSchedeW9Fasi(codGara, codLotto,
				new Long(CostantiW9.STIPULA_ACCORDO_QUADRO), numAppa);

		FaseStipulaAccordoQuadroPubbEntry risultato = new FaseStipulaAccordoQuadroPubbEntry();
		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setDataStipula(faseEsistente.getDataStipula());
			risultato.setPubblicazioneEsito(esito);
			risultato.setDataDecorrenza(faseEsistente.getDataDecorrenza());
			risultato.setDataScadenza(faseEsistente.getDataScadenza());
			risultato.setNumAppa(numAppa);

			if (idSchede != null) {
				if (StringUtils.isNotBlank(idSchede.getIdSchedaLocale())) {
					risultato.setIdSchedaLocale(idSchede.getIdSchedaLocale());
				}
				if (StringUtils.isNotBlank(idSchede.getIdSchedaSimog())) {
					risultato.setIdSchedaSimog(idSchede.getIdSchedaSimog());
				}
			}
		}
		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseAvanzamento(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {

		FaseAvanzamentoEntry faseEsistente = this.contrattiMapper.getFaseAvanzamento(codGara, codLotto, num);

		DatiIdSchedeW9FasiEntry idSchede = this.contrattiMapper.getIdSchedeW9Fasi(codGara, codLotto,
				new Long(CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA), numAppa);

		FaseAvanzamentoPubbEntry risultato = new FaseAvanzamentoPubbEntry();
		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setDataAnticipazione(faseEsistente.getDataAnticipazione());
			risultato.setDataCertificato(faseEsistente.getDataCertificato());
			risultato.setDataRaggiungimento(faseEsistente.getDataRaggiungimento());
			risultato.setDenomAvanzamento(faseEsistente.getDenomAvanzamento());
			risultato.setFlagPagamento(faseEsistente.getFlagPagamento());
			risultato.setFlagRitardo(faseEsistente.getFlagRitardo());
			risultato.setImportoAnticipazione(faseEsistente.getImportoAnticipazione());
			risultato.setImportoCertificato(faseEsistente.getImportoCertificato());
			risultato.setImportoSal(faseEsistente.getImportoSal());
			risultato.setNum(faseEsistente.getNum());
			risultato.setNumGiorniProroga(faseEsistente.getNumGiorniProroga());
			risultato.setNumGiorniScost(faseEsistente.getNumGiorniScost());
			risultato.setSubappalti(faseEsistente.getSubappalti());
			risultato.setNumAppa(numAppa);

			if (idSchede != null) {
				if (StringUtils.isNotBlank(idSchede.getIdSchedaLocale())) {
					risultato.setIdSchedaLocale(idSchede.getIdSchedaLocale());
				}
				if (StringUtils.isNotBlank(idSchede.getIdSchedaSimog())) {
					risultato.setIdSchedaSimog(idSchede.getIdSchedaSimog());
				}
			}
		}

		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseAvanzamentoSemp(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {

		FaseAvanzamentoSempEntry faseEsistente = this.contrattiMapper.getFaseAvanzamentoSemp(codGara, codLotto, num);
		FaseAvanzamentoSempPubbEntry risultato = new FaseAvanzamentoSempPubbEntry();
		if (faseEsistente != null) {
			risultato.setCodGara(codGara);
			risultato.setCodLotto(codLotto);
			risultato.setDataCertificato(faseEsistente.getDataCertificato());
			risultato.setImportoCertificato(faseEsistente.getImportoCertificato());
			risultato.setNum(num);
			risultato.setNumAppa(numAppa);
		}

		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseIniziale(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseInizialePubbEntry risultato = new FaseInizialePubbEntry();

		FaseInizialeEsecuzioneEntry faseEsistente = this.contrattiMapper.getFaseInizialeEsecuzione(codGara, codLotto,
				num);
		FaseInizPubbEsitoForm pubblicazioneEsito = this.contrattiMapper.getFaseInizialeEsecuzionePubbEsito(codGara,
				codLotto, num);
		SchedaSicurezzaEntry schedaSicurezza = this.contrattiMapper.getschedaSicurezza(codGara, codLotto, num);
		MisureAggiuntiveSicurezzaEntry misureAggiuntive = this.contrattiMapper.getMisureAggiuntiveSicurezza(codGara,
				codLotto, num);
		List<DocumentoFaseEntry> documents = this.contrattiMapper.getDocumentoFase(codGara, codLotto, 2L, num);

		DatiIdSchedeW9FasiEntry idSchede = this.contrattiMapper.getIdSchedeW9Fasi(codGara, codLotto,
				new Long(CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA), numAppa);

		risultato.setNum(num);
		risultato.setCodGara(codGara);
		risultato.setCodLotto(codLotto);
		risultato.setDataApprovazioneProg(faseEsistente.getDataApprovazioneProg());
		risultato.setDataEsecutivita(faseEsistente.getDataEsecutivita());
		risultato.setDataInizioProg(faseEsistente.getDataInizioProg());
		risultato.setDataStipula(faseEsistente.getDataStipula());
		risultato.setDataTermine(faseEsistente.getDataTermine());
		risultato.setDataVerbaleCons(faseEsistente.getDataVerbaleCons());
		risultato.setDataVerbaleDef(faseEsistente.getDataVerbaleDef());
		risultato.setDataVerbInizio(faseEsistente.getDataVerbInizio());
		risultato.setFrazionata(faseEsistente.getFrazionata());
		risultato.setImportoCauzione(faseEsistente.getImportoCauzione());
		risultato.setIncontriVigil(faseEsistente.getIncontriVigil());
		risultato.setRiserva(faseEsistente.getRiserva());
		risultato.setNumAppa(numAppa);

		if (idSchede != null) {
			if (StringUtils.isNotBlank(idSchede.getIdSchedaLocale())) {
				risultato.setIdSchedaLocale(idSchede.getIdSchedaLocale());
			}
			if (StringUtils.isNotBlank(idSchede.getIdSchedaSimog())) {
				risultato.setIdSchedaSimog(idSchede.getIdSchedaSimog());
			}
		}

		List<String> sezioni = getSezioneIncaricoByFase(2L);
		List<IncarichiProfPubbEntry> incarichi = new ArrayList<IncarichiProfPubbEntry>();

		for (String sezione : sezioni) {
			List<IncarichiProfPubbEntry> incarichiPerSezione = this.contrattiMapper.getIncarichiFasePubb(codGara,
					codLotto, num, sezione);
			if (incarichiPerSezione != null && !incarichiPerSezione.isEmpty()) {
				incarichi.addAll(incarichiPerSezione);
			}
		}

		for (IncarichiProfPubbEntry i : incarichi) {
			if (i.getCodiceTecnico() != null) {
				i.setTecnico(this.contrattiMapper.getTecnicoPubb(i.getCodiceTecnico()));
			}
		}

		FaseInizPubbEsitoPubbForm pubblicazioneEsitoRequest = new FaseInizPubbEsitoPubbForm();
		if (pubblicazioneEsito != null) {
			pubblicazioneEsitoRequest.setCodGara(codGara);
			pubblicazioneEsitoRequest.setCodLotto(codLotto);
			pubblicazioneEsitoRequest.setCounter(pubblicazioneEsito.getCounter());
			pubblicazioneEsitoRequest.setDataAlbo(pubblicazioneEsito.getDataAlbo());
			pubblicazioneEsitoRequest.setDataGuce(pubblicazioneEsito.getDataGuce());
			pubblicazioneEsitoRequest.setDataGuri(pubblicazioneEsito.getDataGuri());
			pubblicazioneEsitoRequest.setNumIniziale(pubblicazioneEsito.getNumIniziale());
			pubblicazioneEsitoRequest.setProfiloCommittente(pubblicazioneEsito.getProfiloCommittente());
			pubblicazioneEsitoRequest.setQuotidianiNaz(pubblicazioneEsito.getQuotidianiNaz());
			pubblicazioneEsitoRequest.setQuotidianiReg(pubblicazioneEsito.getQuotidianiReg());
			pubblicazioneEsitoRequest.setSitoMinInfTrasp(pubblicazioneEsito.getSitoMinInfTrasp());
			pubblicazioneEsitoRequest.setSitoOsservatorio(pubblicazioneEsito.getSitoOsservatorio());
		}

		SchedaSicurezzaPubbEntry schedaSicurezzaRequest = null;
		if (schedaSicurezza != null && schedaSicurezza.getNum() != null) {
			schedaSicurezzaRequest = new SchedaSicurezzaPubbEntry();
			schedaSicurezzaRequest.setCodGara(codGara);
			schedaSicurezzaRequest.setCodLotto(codLotto);
			schedaSicurezzaRequest.setDirettoreOperativo(schedaSicurezza.getDirettoreOperativo());
			schedaSicurezzaRequest.setNum(schedaSicurezza.getNum());
			schedaSicurezzaRequest.setPianoSicurezza(schedaSicurezza.getPianoSicurezza());
			schedaSicurezzaRequest.setTutor(schedaSicurezza.getTutor());
			schedaSicurezzaRequest.setNumIniz(num);
		}

		MisureAggiuntiveSicurezzaPubbEntry misureSicRequest = null;
		if (misureAggiuntive != null) {
			misureSicRequest = new MisureAggiuntiveSicurezzaPubbEntry();
			misureSicRequest.setCodGara(codGara);
			misureSicRequest.setCodiceImpresa(misureAggiuntive.getCodiceImpresa());
			misureSicRequest.setCodLotto(codLotto);
			misureSicRequest.setDescrizione(misureAggiuntive.getDescrizione());
			misureSicRequest
					.setImpresa(this.contrattiMapper.getImpresaForRequestFase(misureAggiuntive.getCodiceImpresa()));
			misureSicRequest.setNum(misureAggiuntive.getNum());
			misureSicRequest.setNumIniz(num);
			List<DocumentoFasePubbEntry> docsRequest = new ArrayList<DocumentoFasePubbEntry>();
			if (documents != null) {
				for (DocumentoFaseEntry d : documents) {
					DocumentoFasePubbEntry docRequest = new DocumentoFasePubbEntry();
					if (d.getFileAllegato() != null) {
						byte[] encodedFile = Base64.getEncoder().encode(d.getFileAllegato());
						docRequest.setBinary(new String(encodedFile));
					}
					docRequest.setCodFase(2L);
					docRequest.setCodGara(codGara);
					docRequest.setCodLotto(codLotto);
					docRequest.setTitolo(d.getTitolo());
					docsRequest.add(docRequest);
				}
				misureSicRequest.setDocuments(docsRequest);
			}
			risultato.setMisureaggsicurezza(misureSicRequest);
		}
		risultato.setIncarichiProfessionali(incarichi);
		risultato.setSchedaSicurezza(schedaSicurezzaRequest);
		risultato.setPubblicazioneEsito(pubblicazioneEsitoRequest);

		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseInizialeSemplificata(Long codGara, Long codLotto, Long num, Long numAppa)
			throws JsonProcessingException {
		FaseInizialeSemplificataPubbEntry risultato = new FaseInizialeSemplificataPubbEntry();

		FaseInizialeSemplificataEntry faseEsistente = this.contrattiMapper.getFaseInizialeSemplificata(codGara,
				codLotto, num);
		SchedaSicurezzaEntry schedaSicurezza = this.contrattiMapper.getschedaSicurezza(codGara, codLotto, num);
		MisureAggiuntiveSicurezzaEntry misureAggiuntive = this.contrattiMapper.getMisureAggiuntiveSicurezza(codGara,
				codLotto, num);
		List<DocumentoFaseEntry> documents = this.contrattiMapper.getDocumentoFase(codGara, codLotto, 986L, num);

		
		risultato.setCodGara(codGara);
		risultato.setCodLotto(codLotto);
		risultato.setDataStipula(faseEsistente.getDataStipula());
		risultato.setDataTermine(faseEsistente.getDataTermine());
		risultato.setDataVerbale(faseEsistente.getDataVerbale());
		risultato.setFlagRiserva(faseEsistente.getFlagRiserva());
		risultato.setNum(num);
		risultato.setNumAppa(numAppa);
		List<String> sezioni = getSezioneIncaricoByFase(986L);
		List<IncarichiProfPubbEntry> incarichi = new ArrayList<IncarichiProfPubbEntry>();

		for (String sezione : sezioni) {
			List<IncarichiProfPubbEntry> incarichiPerSezione = this.contrattiMapper.getIncarichiFasePubb(codGara,
					codLotto, num, sezione);
			if (incarichiPerSezione != null && !incarichiPerSezione.isEmpty()) {
				incarichi.addAll(incarichiPerSezione);
			}
		}

		for (IncarichiProfPubbEntry i : incarichi) {
			if (i.getCodiceTecnico() != null) {
				i.setTecnico(this.contrattiMapper.getTecnicoPubb(i.getCodiceTecnico()));
			}
		}

		SchedaSicurezzaPubbEntry schedaSicurezzaRequest = new SchedaSicurezzaPubbEntry();
		if (schedaSicurezza != null) {
			schedaSicurezzaRequest.setCodGara(codGara);
			schedaSicurezzaRequest.setCodLotto(codLotto);
			schedaSicurezzaRequest.setDirettoreOperativo(schedaSicurezza.getDirettoreOperativo());
			schedaSicurezzaRequest.setNum(schedaSicurezza.getNum());
			schedaSicurezzaRequest.setPianoSicurezza(schedaSicurezza.getPianoSicurezza());
			schedaSicurezzaRequest.setTutor(schedaSicurezza.getTutor());
			schedaSicurezzaRequest.setNumIniz(num);
		}

		MisureAggiuntiveSicurezzaPubbEntry misureSicRequest = new MisureAggiuntiveSicurezzaPubbEntry();
		if (misureAggiuntive != null) {
			misureSicRequest.setCodGara(codGara);
			misureSicRequest.setCodiceImpresa(misureAggiuntive.getCodiceImpresa());
			misureSicRequest.setCodLotto(codLotto);
			misureSicRequest.setDescrizione(misureAggiuntive.getDescrizione());
			misureSicRequest
					.setImpresa(this.contrattiMapper.getImpresaForRequestFase(misureAggiuntive.getCodiceImpresa()));
			misureSicRequest.setNum(misureAggiuntive.getNum());
			misureSicRequest.setNumIniz(num);

			List<DocumentoFasePubbEntry> docsRequest = new ArrayList<DocumentoFasePubbEntry>();
			if (documents != null) {
				for (DocumentoFaseEntry d : documents) {
					DocumentoFasePubbEntry docRequest = new DocumentoFasePubbEntry();
					if (d.getFileAllegato() != null) {
						byte[] encodedFile = Base64.getEncoder().encode(d.getFileAllegato());
						docRequest.setBinary(new String(encodedFile));
					}
					docRequest.setCodFase(986L);
					docRequest.setCodGara(codGara);
					docRequest.setCodLotto(codLotto);
					docRequest.setTitolo(d.getTitolo());
					docsRequest.add(docRequest);
				}
				misureSicRequest.setDocuments(docsRequest);
			}
			risultato.setMisureaggsicurezza(misureSicRequest);
		}
		risultato.setIncarichiProfessionali(incarichi);
		risultato.setSchedaSicurezza(schedaSicurezzaRequest);

		return new ObjectMapper().writeValueAsString(risultato);
	}

	private String getRequestFaseAdesioneAccordoQuadro(Long codGara, Long codLotto, Long num, Long numAppa)
			throws Exception {
		FaseAdesioneAccordoQuadroPubbEntry risultato = new FaseAdesioneAccordoQuadroPubbEntry();

		try {

			DatiIdSchedeW9FasiEntry idSchede = this.contrattiMapper.getIdSchedeW9Fasi(codGara, codLotto,
					new Long(CostantiW9.ADESIONE_ACCORDO_QUADRO), numAppa);

			FaseAdesioneAccordoQuadroEntry faseEsistente = this.contrattiMapper.getFaseAdesioneAccordo(codGara,
					codLotto, num);
			risultato.setCodGara(faseEsistente.getCodGara());
			risultato.setCodLotto(faseEsistente.getCodLotto());
			risultato.setCodStrumento(faseEsistente.getCodStrumento());
			risultato.setDataAtto(faseEsistente.getDataAtto());
			risultato.setDataVerbAggiudicazione(faseEsistente.getDataVerbAggiudicazione());
			risultato.setFlagRichSubappalto(faseEsistente.getFlagRichSubappalto());
			risultato.setImpNonAssog(faseEsistente.getImpNonAssog());
			risultato.setImportoAggiudicazione(faseEsistente.getImportoAggiudicazione());
			risultato.setImportoComplAppalto(faseEsistente.getImportoComplAppalto());
			risultato.setImportoComplIntervento(faseEsistente.getImportoComplIntervento());
			risultato.setImportoForniture(faseEsistente.getImportoForniture());
			risultato.setImportoLavori(faseEsistente.getImportoLavori());
			risultato.setImportoProgettazione(faseEsistente.getImportoProgettazione());
			risultato.setImportoServizi(faseEsistente.getImportoServizi());
			risultato.setImportoSicurezza(faseEsistente.getImportoSicurezza());
			risultato.setImportosubtotale(faseEsistente.getImportosubtotale());
			risultato.setNumeroAtto(faseEsistente.getNumeroAtto());
			risultato.setPercentRibassoAgg(faseEsistente.getPercentRibassoAgg());
			risultato.setPercOffAumento(faseEsistente.getPercOffAumento());
			risultato.setTipoAtto(faseEsistente.getTipoAtto());
			risultato.setNumAppa(numAppa);

			if (idSchede != null) {
				if (StringUtils.isNotBlank(idSchede.getIdSchedaLocale())) {
					risultato.setIdSchedaLocale(idSchede.getIdSchedaLocale());
				}
				if (StringUtils.isNotBlank(idSchede.getIdSchedaSimog())) {
					risultato.setIdSchedaSimog(idSchede.getIdSchedaSimog());
				}
			}

			List<FinanziamentiPubbEntry> finanziamenti = this.contrattiMapper.getFinanziamentiFasePubb(codGara,
					codLotto, num);
			List<String> sezioni = getSezioneIncaricoByFase(12L);
			List<IncarichiProfPubbEntry> incarichi = new ArrayList<IncarichiProfPubbEntry>();

			for (String sezione : sezioni) {
				List<IncarichiProfPubbEntry> incarichiPerSezione = this.contrattiMapper.getIncarichiFasePubb(codGara,
						codLotto, num, sezione);
				if (incarichiPerSezione != null && !incarichiPerSezione.isEmpty()) {
					incarichi.addAll(incarichiPerSezione);
				}
			}
			for (IncarichiProfPubbEntry i : incarichi) {
				if (i.getCodiceTecnico() != null) {
					i.setTecnico(this.contrattiMapper.getTecnicoPubb(i.getCodiceTecnico()));
				}
			}

			List<ImpresaAggiudicatariaPubbEntry> impreseAgg = this.contrattiMapper.getImpAggFasePubb(codGara, codLotto,
					numAppa);

			for (ImpresaAggiudicatariaPubbEntry i : impreseAgg) {
				if (i.getCodImpresa() != null) {
					i.setImpresa(this.getImpresaForRequestFase(i.getCodImpresa()));
				}
				if (i.getCodImpAus() != null) {
					i.setImpresaAusiliaria(this.getImpresaForRequestFase(i.getCodImpAus()));
				}
			}

			risultato.setFinanziamenti(finanziamenti);
			risultato.setImpreseAggiudicatarie(impreseAgg);
			risultato.setIncarichiProfessionali(incarichi);

			return new ObjectMapper().writeValueAsString(risultato);
		} catch (JsonProcessingException e) {
			throw e;
		}

	}

	private String getRequestFaseImprese(Long codGara, Long codLotto, Long num) throws Exception {
		ListaFaseImpresePubbEntry risultato = new ListaFaseImpresePubbEntry();

		try {
			List<RecordFaseImpreseEntry> fasiEsistenti = this.contrattiMapper.getFaseImprese(codGara, codLotto);
			List<FaseImpresaPubbEntry> fasiRequest = new ArrayList<FaseImpresaPubbEntry>();
			for (RecordFaseImpreseEntry f : fasiEsistenti) {

				FaseImpresaPubbEntry entry = new FaseImpresaPubbEntry();
				entry.setCodGara(f.getCodGara());
				entry.setCodImpresa(f.getCodImpresa());
				entry.setCodLotto(f.getCodLotto());
				entry.setImpresa(this.getImpresaForRequestFase(f.getCodImpresa()));
				entry.setNum(f.getNum());
				entry.setNumRagg(f.getNumRaggruppamento());
				entry.setPartecipante(f.getPartecipante());
				entry.setRuolo(f.getRuolo());
				entry.setTipologiaSoggetto(f.getTipologiaSoggetto());
				fasiRequest.add(entry);
			}
			risultato.setImprese(fasiRequest);
			return new ObjectMapper().writeValueAsString(risultato);
		} catch (JsonProcessingException e) {
			throw e;
		}
	}

	private String getRequestFaseComEsito(Long codGara, Long codLotto, Long num) throws Exception {
		FaseComPubbEsitoEntry risultato = new FaseComPubbEsitoEntry();

		try {
			Long numAppa = getNumAppa(codGara, codLotto, false);
			FaseComEsitoForm faseEsistente = this.contrattiMapper.getFaseComEsito(codGara, codLotto, numAppa);
			if (faseEsistente != null) {
				if (faseEsistente.getFileAllegato() != null) {
					byte[] encodedFile = Base64.getEncoder().encode(faseEsistente.getFileAllegato());
					risultato.setBinary(new String(encodedFile, StandardCharsets.UTF_8));
				}
				risultato.setCodGara(faseEsistente.getCodGara());
				risultato.setCodLotto(faseEsistente.getCodLotto());
				risultato.setDataAggiudicazione(faseEsistente.getDataAggiudicazione());
				risultato.setEsito(faseEsistente.getEsito());
				risultato.setImportoAggiudicazione(faseEsistente.getImportoAggiudicazione());
			}
			return new ObjectMapper().writeValueAsString(risultato);
		} catch (JsonProcessingException e) {
			throw e;
		}
	}

	private String getRequestFaseAggiudicazioneSemp(Long codGara, Long codLotto, Long num) throws Exception {
		FaseAggiudicazioneSempPubbEntry risultato = new FaseAggiudicazioneSempPubbEntry();

		try {
			Long numAppa = getNumAppa(codGara, codLotto, false);

			DatiIdSchedeW9FasiEntry idSchede = this.contrattiMapper.getIdSchedeW9Fasi(codGara, codLotto,
					new Long(CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE), numAppa);

			FaseAggiudicazioneSempEntry faseEsistente = this.contrattiMapper.getFaseAggiudicazioneSemp(codGara,
					codLotto, numAppa);

			if (faseEsistente != null) {
				risultato.setAltreSomme(faseEsistente.getAltreSomme());
				risultato.setAstaElettronica(faseEsistente.getAstaElettronica());
				risultato.setCodGara(codGara);
				risultato.setCodLotto(codLotto);
				risultato.setCounter(faseEsistente.getCounter());
				risultato.setDataStipula(faseEsistente.getDataStipula());
				risultato.setDataVerbAgg(faseEsistente.getDataVerbAgg());
				risultato.setDataAtto(faseEsistente.getDataAtto());
				risultato.setDurataCon(faseEsistente.getDurataCon());
				risultato.setImportoComplAppalto(faseEsistente.getImportoComplAppalto());
				risultato.setImportoComplIntervento(faseEsistente.getImportoComplIntervento());
				risultato.setImportoDisposizione(faseEsistente.getImportoDisposizione());
				risultato.setImportoAgg(faseEsistente.getImportoAgg());
				risultato.setImportoIva(faseEsistente.getImportoIva());
				risultato.setImportoSub(faseEsistente.getImportoSub());
				risultato.setImportoSicurezza(faseEsistente.getImportoSicurezza());
				risultato.setIva(faseEsistente.getIva());
				risultato.setNumeroAtto(faseEsistente.getNumeroAtto());
				risultato.setPercOffAumento(faseEsistente.getPercOffAumento());
				risultato.setTipoAtto(faseEsistente.getTipoAtto());
				risultato.setPercRibassoAgg(faseEsistente.getPercRibassoAgg());

				if (idSchede != null) {
					if (StringUtils.isNotBlank(idSchede.getIdSchedaLocale())) {
						risultato.setIdSchedaLocale(idSchede.getIdSchedaLocale());
					}
					if (StringUtils.isNotBlank(idSchede.getIdSchedaSimog())) {
						risultato.setIdSchedaSimog(idSchede.getIdSchedaSimog());
					}
				}

				List<ImpresaAggiudicatariaPubbEntry> impreseAgg = this.contrattiMapper.getImpAggFasePubb(codGara,
						codLotto, numAppa);

				List<String> sezioni = getSezioneIncaricoByFase(987L);
				List<IncarichiProfPubbEntry> incarichi = new ArrayList<IncarichiProfPubbEntry>();

				for (String sezione : sezioni) {
					List<IncarichiProfPubbEntry> incarichiPerSezione = this.contrattiMapper
							.getIncarichiFasePubb(codGara, codLotto, num, sezione);
					if (incarichiPerSezione != null && !incarichiPerSezione.isEmpty()) {
						incarichi.addAll(incarichiPerSezione);
					}
				}

				for (IncarichiProfPubbEntry i : incarichi) {
					if (i.getCodiceTecnico() != null) {
						i.setTecnico(this.contrattiMapper.getTecnicoPubb(i.getCodiceTecnico()));
					}
				}

				for (ImpresaAggiudicatariaPubbEntry i : impreseAgg) {
					if (i.getCodImpresa() != null) {
						i.setImpresa(this.getImpresaForRequestFase(i.getCodImpresa()));
					}
					if (i.getCodImpAus() != null) {
						i.setImpresaAusiliaria(this.getImpresaForRequestFase(i.getCodImpAus()));
					}
				}

				risultato.setImpreseAggiudicatarie(impreseAgg);
				risultato.setIncarichiProfessionali(incarichi);

			}
			return new ObjectMapper().writeValueAsString(risultato);
		} catch (JsonProcessingException e) {
			throw e;
		}
	}

	// Autocomplete imprese filtrate
	public static final String APPLICATION_CODE = "W9";
	public static final String APPLICATION_SA_FILTER = "it.eldasoft.associazioneUffintAbilitata.archiviFiltrati";

	public ResponseListaImpreseOptions getImpreseOptionsAggiSuba(SingolaImpresaAggiSubaSearchForm form) {
		ResponseListaImpreseOptions risultato = new ResponseListaImpreseOptions();
		risultato.setEsito(true);
		try {
			form.setDesc(form.getDesc().toLowerCase());
			
			List<ImpresaEntry> entry = new ArrayList<ImpresaEntry>();			
			entry = this.contrattiMapper.getImpreseOptionsAggiSubaWithoutSa(form);
			
			risultato.setData(entry);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la ricerca delle imprese", t);
			risultato.setEsito(false);
			risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
		}
		return risultato;
	}

	public ResponseListaImpreseOptions getImpreseOptionsVaraggi(SingolaImpresaAggiSubaSearchForm form) {
		ResponseListaImpreseOptions risultato = new ResponseListaImpreseOptions();
		risultato.setEsito(true);
		try {
			form.setDesc(form.getDesc().toLowerCase());

			List<ImpresaEntry> entry = new ArrayList<ImpresaEntry>();
			entry = this.contrattiMapper.getImpreseOptionsVaraggiWithoutSa(form);

			risultato.setData(entry);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la ricerca delle imprese", t);
			risultato.setEsito(false);
			risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
		}
		return risultato;
	}

	public ResponseListaImpreseOptions getImpreseOptionsAggi(SingolaImpresaAggiSubaSearchForm form) {
		ResponseListaImpreseOptions risultato = new ResponseListaImpreseOptions();
		risultato.setEsito(true);
		try {
			form.setDesc(form.getDesc().toLowerCase());
			
			List<ImpresaEntry> entry = new ArrayList<ImpresaEntry>();
			
			entry = this.contrattiMapper.getImpreseOptionsAggiWithoutSa(form);
			
			risultato.setData(entry);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la ricerca delle imprese", t);
			risultato.setEsito(false);
			risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
		}
		return risultato;
	}

	public ResponseInizFinanziamenti getInizFinanziamenti(Long codGara, Long codLotto) {
		ResponseInizFinanziamenti risultato = new ResponseInizFinanziamenti();
		risultato.setEsito(true);
		try {
			Long numAppa = getNumAppa(codGara, codLotto, false);
			BigDecimal importoComplessivoIntervento = this.contrattiMapper.getImportoComplessivoIntervento(codGara,
					codLotto, numAppa);
			risultato.setData(importoComplessivoIntervento);
		} catch (Throwable t) {
			logger.error("Errore inaspettato durante la getInizFinanziamenti", t);
			risultato.setEsito(false);
			risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
		}
		return risultato;

	}

	protected String getCodImpresa(String cfImpresa, String stazioneAppaltante, AggiudicatariType aggiudicatari)
			throws Exception {

		if (cfImpresa == null) {
			return null;
		}
		List<String> codImpList = this.contrattiMapper.findImpresa(cfImpresa, stazioneAppaltante);
		String codImp = null;
		if(codImpList != null && codImpList.size()>0) {
			codImp = codImpList.get(0);
		}
		if (codImp == null) {

			String newCodImp = calcolaCodificaAutomatica("IMPR", "CODIMP");
			ImpresaEntry impresa = new ImpresaEntry();
			impresa.setCodiceImpresa(newCodImp);
			impresa.setCodiceFiscale(cfImpresa);
			impresa.setRagioneSociale(cfImpresa);
			impresa.setStazioneAppaltante(stazioneAppaltante);
			impresa = super.setImpresaNomImp(impresa);
			this.contrattiMapper.insertImpresa(impresa);

			String nome = "";
			String cognome = "";
			String cf = cfImpresa;
			String nominativo = "Legale rapprsentante impresa " + cfImpresa;
			if (aggiudicatari != null && aggiudicatari.getAggiudicatario() != null
					&& aggiudicatari.getAggiudicatario().size() > 0) {
				AggiudicatarioType aggiudicatario = aggiudicatari.getAggiudicatario().get(0);
				nome = aggiudicatario.getNOME();
				cognome = aggiudicatario.getCOGNOME();
				cf = aggiudicatario.getCFRAPPRESENTANTE();
				nominativo = nome + " " + cognome;
			}
			this.contrattiMapper.insertTeim(newCodImp, nome, cognome, cf);
			Long maxId = wgcManager.getNextId("IMPLEG");
			this.contrattiMapper.insertImpleg(maxId, impresa.getCodiceImpresa(), nominativo);
			return newCodImp;
		} else {
			return codImp;
		}
	}

	public DocumentoFaseEntry downloadDocumentoMisureAggiuntiveSicurezza(final Long codGara, final Long codLotto,
			final Long codFase, final Long numeroProgressivoFase, final Long numDoc) {

		if (codGara != null && codLotto != null && codFase != null && numeroProgressivoFase != null && numDoc != null) {
			DocumentoFaseEntry doc = this.contrattiMapper.getDocumentoFaseByNumDoc(codGara, codLotto, codFase,
					numeroProgressivoFase, numDoc);
			if (doc != null) {
				if (doc.getFileAllegato() != null) {
					byte[] encodedFile = Base64.getEncoder().encode(doc.getFileAllegato());
					doc.setBinary(new String(encodedFile, StandardCharsets.UTF_8));
				}
				return doc;
			}
		}

		return null;
	}

	private String getNomeAppEsterno() {
		var integrazioneJSerfin = wConfigManager.getConfigurationValue("integrazione.jserfin");
		var integrazioneApk = wConfigManager.getConfigurationValue("integrazione.apk");

		return getNomeAppEsterno(integrazioneJSerfin, integrazioneApk);
	}

	private String getNomeAppEsterno(String integrazioneJSerfin, String integrazioneApk) {
		String appEsterno = null;
		if ("1".equals(integrazioneJSerfin)) {
			appEsterno = "sicra";
		} else if ("1".equals(integrazioneApk)) {
			appEsterno = "apk";
		}
		return appEsterno;
	}

	private void insertW9AssociazioneCampi(Long codGara, Long codLotto, Long fase, Long num, List<AssociazionePagamentiEntry> associazioni) {
		String appEsterno = getNomeAppEsterno();

		for (AssociazionePagamentiEntry associazione : associazioni) {

			this.contrattiMapper.deleteW9AssociazioneCampi(codGara, codLotto, fase, num, associazione.getNomeCampo());

			if(nullToZero(associazione.getImportoTotalePagamenti()).equals(nullToZero(associazione.getImportoCampo()))) {
				for (PagamentoEntry pagamento : associazione.getPagamenti()) {
					insertW9AssociazioneCampi(codGara, codLotto, fase, num, associazione.getNomeCampo(), pagamento.getCodicePagamento(), appEsterno);
				}
			}

		}
	}

	private Double nullToZero(Double val) {
		if(val == null) {
			return 0d;
		}
		return val;
	}

	private void insertW9AssociazioneCampi(Long codGara, Long codLotto, Long fase, Long num, String nomeCampo, String idEsterno, String appEsterno) {
		W9AssociazioneCampi item = new W9AssociazioneCampi();

		Long id = this.contrattiMapper.getW9AssociazioneCampiMaxId();
		if (id == null) {
			id = 1L;
		} else {
			id++;
		}

		//String appEsterno = getNomeAppEsterno();

		item.setId(id);
		item.setCodGara(codGara);
		item.setCodLotto(codLotto);
		item.setFaseEsecuzione(fase);
		item.setNum(num);
		item.setNomeCampo(nomeCampo);
		item.setAppEsterno(appEsterno);
		item.setIdEsterno(idEsterno);

		this.contrattiMapper.insertW9AssociazioneCampi(item);
	}

	public ResponseDto getInizFaseVariazioneAggiudicatari(Long codGara, Long codLotto) {
		ResponseDto res = new ResponseDto();
		res.setEsito(true);
		try {
			Long numAppa = getNumAppa(codGara, codLotto, false);
			List<ImpresaAggIdPartecipEntry> idPartecipanteList = this.contrattiMapper.getIdPartecipanteNomestAggi(codGara, codLotto, numAppa);
			res.setData(idPartecipanteList);
		} catch (Exception e) {
			logger.error(
					"Errore durante il metodo getInizFaseVariazioneAggiudicatari per il  codgara = "
							+ codGara + " codLotto = " + codLotto,
					e);
			res.setEsito(false);
			res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
		}
		return res;
	}
}
