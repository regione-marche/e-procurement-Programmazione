package it.appaltiecontratti.monitoraggiocontratti.simog.bl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper.SqlMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.WGenChiaviManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseAccordoBonarioInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseAdesioneAccordoQuadroInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseAggInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseAggSempInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseAvanzamentoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseCollaudoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseComEsitoForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseConclusioneInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseInizPubbEsitoForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseInizialeEsecuzioneInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseIstanzaRecessoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseSospensioneInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseStipulaAccordoQuadroInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseSubappaltoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FaseVarianteInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.FinanziamentoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.ImpresaAggiudicatariaInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.IncaricoProfessionaleInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.SchedaSicurezzaInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.AccordiBonariType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.AccordoBonarioType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.AdesioneType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.AggiudicatariType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.AggiudicatarioType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.AggiudicazioneType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.AppaltoAdesioneType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.AppaltoType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.AvanzamentiType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.AvanzamentoType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.CollaudoType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.ConclusioneType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.CondizioneType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.DatiCollaudoType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.DatiComuniType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.DatiInizioType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.DatiStipulaType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.FinanziamentoType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.FlagSNType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.IncaricatoType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.InfoUtenteEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.InizioType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.PubblicazioneType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.RecMotivoVarType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.RecVarianteType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.ResponsabileType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.ResponsabiliType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.RitardiType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.RitardoType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SchedaCompletaType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SchedaEsclusoType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SchedaSottosogliaType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SchedaType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SoggAggiudicatarioType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SospensioneType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SospensioniType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SottoEsclusoType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.StipulaType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SubappaltiType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SubappaltoType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.VarianteType;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.VariantiType;
import it.appaltiecontratti.monitoraggiocontratti.simog.mapper.SimogMapper;
import it.appaltiecontratti.monitoraggiocontratti.simog.utils.CostantiW9;

@Component(value = "schedeManager")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class SchedeManager {

	/** Logger di classe. */
	private static final Logger LOGGER = LogManager.getLogger(SchedeManager.class);

	@Autowired
	private ContrattiMapper contrattiMapper;

	@Autowired
	private SimogMapper simogMapper;

	@Autowired
	private SqlMapper sqlMapper;

	@Autowired
	private WGenChiaviManager wgcManager;

	private static final String FULL_DATE_FORMAT = "yyyy-MM-dd'T'hh:mm:ss";

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void insertSchedeLotto(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaType schedaType) throws Exception {

		LOGGER.debug("Execution start::insertSchedeLotto codGara [{}], codLotto [{}], codUffInt [{}]", codGara,
				codLotto, codUffInt);

		ResponsabiliType responsabiliType = schedaType.getResponsabili();

		SchedaCompletaType schedaCompleta = getSchedaCompleta(schedaType);

		if (hasEsito(schedaType)) {
			istanziaEsito(codGara, codLotto, schedaType);
		}

		if (hasAggiudicazione(schedaCompleta)) {
			istanziaAggiudicazione(codGara, codLotto, codUffInt, schedaCompleta, schedaType.getAggiudicatari(),
					responsabiliType);
		}

		if (hasAggiudicazioneSottosoglia(schedaCompleta)) {
			istanziaAggiudicazioneSottosoglia(codGara, codLotto, codUffInt, schedaCompleta,
					schedaType.getAggiudicatari(), responsabiliType);
		}

		if (hasAggiudicazioneEsclusa(schedaCompleta)) {
			istanziaAggiudicazioneEsclusa(codGara, codLotto, codUffInt, schedaCompleta, schedaType.getAggiudicatari(),
					responsabiliType);
		}

		if (hasAdesione(schedaCompleta)) {
			istanziaAdesioneAccordoQuadro(codGara, codLotto, codUffInt, schedaCompleta, schedaType.getAggiudicatari(),
					responsabiliType);
		}

		if (hasInizio(schedaCompleta)) {
			istanziaInizio(codGara, codLotto, codUffInt, schedaCompleta, responsabiliType);
		}

		if (hasStipula(schedaCompleta)) {
			istanziaStipulaAccordoQuadro(codGara, codLotto, codUffInt, schedaCompleta);
		}

		if (hasAvanzamento(schedaCompleta)) {
			istanziaFaseAvanzamento(codGara, codLotto, codUffInt, schedaCompleta);
		}

		if (hasConclusione(schedaCompleta)) {
			istanziaFaseConclusione(codGara, codLotto, codUffInt, schedaCompleta);
		}

		if (hasCollaudo(schedaCompleta)) {
			istanziaFaseCollaudo(codGara, codLotto, codUffInt, schedaCompleta, responsabiliType);
		}

		if (hasRitardo(schedaCompleta)) {
			istanziaRitardo(codGara, codLotto, codUffInt, schedaCompleta);
		}

		if (hasAccordoBonario(schedaCompleta)) {
			istanziaAccordBonario(codGara, codLotto, codUffInt, schedaCompleta);
		}

		if (hasSospensioni(schedaCompleta)) {
			istanziaSospensioni(codGara, codLotto, codUffInt, schedaCompleta);
		}

		if (hasVarianti(schedaCompleta)) {
			istanziaVarianti(codGara, codLotto, codUffInt, schedaCompleta);
		}

		if (hasSubappalti(schedaCompleta)) {
			istanziaSubappalti(codGara, codLotto, codUffInt, schedaCompleta, schedaType.getAggiudicatari());
		}

		LOGGER.debug("Execution end::insertSchedeLotto");
	}

	// Checks

	private boolean hasEsito(final SchedaType scheda) {
		return scheda != null && scheda.getDatiScheda() != null && scheda.getDatiScheda().getDatiComuni() != null
				&& scheda.getDatiScheda().getDatiComuni().getESITOPROCEDURA() != null;
	}

	public boolean hasAggiudicazione(final SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.getAggiudicazione() != null;
	}

	private boolean hasAggiudicazioneSottosoglia(final SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.getSottosoglia() != null;
	}

	private boolean hasAggiudicazioneEsclusa(final SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.getEscluso() != null;
	}

	private boolean hasAdesione(final SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.getAdesione() != null;
	}

	private boolean hasInizio(final SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.getDatiInizio() != null;
	}

	private boolean hasStipula(final SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.getDatiStipula() != null;
	}

	private boolean hasAvanzamento(final SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.getDatiAvanzamenti() != null;
	}

	private boolean hasConclusione(final SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.getDatiConclusione() != null;
	}

	private boolean hasCollaudo(final SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.getDatiCollaudo() != null;
	}

	private boolean hasRitardo(final SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.getDatiRitardi() != null;
	}

	private boolean hasAccordoBonario(final SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.getDatiAccordi() != null;
	}

	private boolean hasSospensioni(final SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.getDatiSospensioni() != null;
	}

	private boolean hasVarianti(final SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.getDatiVarianti() != null;
	}

	private boolean hasSubappalti(final SchedaCompletaType schedaCompleta) {
		return schedaCompleta != null && schedaCompleta.getDatiSubappalti() != null;
	}

	// End Checks

	// Inserts Schede

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void istanziaEsito(final Long codGara, final Long codLotto, final SchedaType schedaType) {

		LOGGER.debug("Execution start::istanziaEsito");

		Long faseEsecuzione = new Long(CostantiW9.COMUNICAZIONE_ESITO);
		FaseComEsitoForm esitoForm = new FaseComEsitoForm();

		if (schedaType.getDatiScheda() != null && schedaType.getDatiScheda().getDatiComuni() != null) {
			DatiComuniType datiComuni = schedaType.getDatiScheda().getDatiComuni();
			esitoForm.setCodGara(codGara);
			esitoForm.setCodLotto(codLotto);
			esitoForm
					.setEsito(datiComuni.getESITOPROCEDURA() == null ? null : new Long(datiComuni.getESITOPROCEDURA()));
			this.contrattiMapper.insertFaseComEsito(esitoForm);

			Long idW9Fase = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
			if (idW9Fase == null) {
				idW9Fase = 1L;
			} else {
				idW9Fase++;
			}
			String idScheda = datiComuni.getIDSCHEDALOCALE();
			this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, idW9Fase, idScheda, 1L, null);

		}

		LOGGER.debug("Execution end::istanziaEsito");
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void istanziaAggiudicazione(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaCompletaType schedaCompleta, final AggiudicatariType aggiudicatariType,
			final ResponsabiliType responsabiliType) throws Exception {

		LOGGER.debug("Execution start::istanziaAggiudicazione");

		try {
			AggiudicazioneType aggiudicazione = schedaCompleta.getAggiudicazione();
			if (aggiudicazione != null) {
				Long faseEsecuzione = new Long(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA);
				AppaltoType appalto = aggiudicazione.getAppalto();
				if (appalto != null) {

					double importoLavori = appalto.getIMPORTOLAVORI() == null ? 0d
							: Double.valueOf(appalto.getIMPORTOLAVORI());
					double importoForniture = appalto.getIMPORTOFORNITURE() == null ? 0d
							: Double.valueOf(appalto.getIMPORTOFORNITURE());
					double importoServizi = appalto.getIMPORTOSERVIZI() == null ? 0d
							: Double.valueOf(appalto.getIMPORTOSERVIZI());
					Double importoSubtotale = importoLavori + importoForniture + importoServizi;
					double importoSicurezza = appalto.getIMPORTOATTUAZIONESICUREZZA() == null ? 0d
							: Double.valueOf(appalto.getIMPORTOATTUAZIONESICUREZZA().doubleValue());
					double importoProgettazione = appalto.getIMPORTOPROGETTAZIONE() == null ? 0d
							: Double.valueOf(appalto.getIMPORTOPROGETTAZIONE());
					double importoNonAssog = appalto.getIMPNONASSOG() == null ? 0d
							: Double.valueOf(appalto.getIMPNONASSOG());
					Double importoComplAppalto = importoSubtotale + importoSicurezza + importoProgettazione
							+ importoNonAssog;
					double importoDisposizione = appalto.getIMPORTODISPOSIZIONE() == null ? 0d
							: Double.valueOf(appalto.getIMPORTODISPOSIZIONE());
					Double importoComplessivoIntervento = importoComplAppalto + importoDisposizione;

					FaseAggInsertForm aggInsertForm = new FaseAggInsertForm();
					aggInsertForm.setAstaElettronica(appalto.getASTAELETTRONICA());
					aggInsertForm.setCodGara(codGara);
					aggInsertForm.setCodLotto(codLotto);
					aggInsertForm.setNum(1L);
					aggInsertForm.setCodStrumento(appalto.getCODSTRUMENTO());
					aggInsertForm.setDataInvito(appalto.getDATAINVITO() == null ? null
							: appalto.getDATAINVITO().toGregorianCalendar().getTime());
					aggInsertForm.setDataManifestInteresse(appalto.getDATAMANIFINTERESSE() == null ? null
							: appalto.getDATAMANIFINTERESSE().toGregorianCalendar().getTime());
					aggInsertForm.setDataScadenzaPresOfferta(appalto.getDATASCADENZAPRESOFFERTA() == null ? null
							: appalto.getDATASCADENZAPRESOFFERTA().toGregorianCalendar().getTime());
					aggInsertForm.setDataScadRichiestaInvito(appalto.getDATASCADENZARICHIESTAINVITO() == null ? null
							: appalto.getDATASCADENZARICHIESTAINVITO().toGregorianCalendar().getTime());
					aggInsertForm.setDataVerbAggiudicazione(appalto.getDATAVERBAGGIUDICAZIONE() == null ? null
							: appalto.getDATAVERBAGGIUDICAZIONE().toGregorianCalendar().getTime());
					aggInsertForm.setFlagRichSubappalto(appalto.getFLAGRICHSUBAPPALTO());
					aggInsertForm.setIdIndizione(
							appalto.getIDMODOINDIZIONE() == null ? null : new Long(appalto.getIDMODOINDIZIONE()));
					aggInsertForm.setImpNonAssog(importoNonAssog);
					aggInsertForm.setImportoAggiudicazione(appalto.getIMPORTOAGGIUDICAZIONE() == null ? null
							: Double.valueOf(appalto.getIMPORTOAGGIUDICAZIONE()));
					aggInsertForm.setImportoDisposizione(importoDisposizione);
					aggInsertForm.setImportoForniture(importoForniture);
					aggInsertForm.setImportoLavori(importoLavori);
					aggInsertForm.setImportoProgettazione(importoProgettazione);
					aggInsertForm.setImportoServizi(importoServizi);
					aggInsertForm.setImportosubtotale(importoSubtotale);
					aggInsertForm.setImportoSicurezza(importoSicurezza);
					aggInsertForm.setImportoComplAppalto(importoComplAppalto);
					aggInsertForm.setImportoComplIntervento(importoComplessivoIntervento);
					aggInsertForm.setModalitaRiaggiudicazione(appalto.getMODALITARIAGGIUDICAZIONE() == null ? null
							: new Long(appalto.getMODALITARIAGGIUDICAZIONE()));
					aggInsertForm.setNumImpreseInvitate(new Long(appalto.getNUMIMPRESEINVITATE()));
					aggInsertForm.setNumImpreseOfferenti(new Long(appalto.getNUMIMPRESEOFFERENTI()));
					aggInsertForm.setNumImpreseRichiedenti(new Long(appalto.getNUMIMPRESERICHIEDENTI()));
					aggInsertForm.setNumManifestInteresse(new Long(appalto.getNUMMANIFINTERESSE()));
					aggInsertForm.setNumOfferteAmmesse(new Long(appalto.getNUMOFFERTEAMMESSE()));
					aggInsertForm.setNumOfferteEscluse(new Long(appalto.getNUMOFFERTEESCLUSE()));
					aggInsertForm.setNumOfferteFuoriSoglia(new Long(appalto.getNUMOFFERTEFUORISOGLIA()));
					aggInsertForm.setImpEsclusInsuffGiust(new Long(appalto.getNUMIMPESCLINSUFGIUST()));
					aggInsertForm.setOffertaMassima(
							appalto.getOFFERTAMASSIMO() == null ? null : appalto.getOFFERTAMASSIMO().doubleValue());
					aggInsertForm.setOffertaMinima(
							appalto.getOFFERTAMINIMA() == null ? null : appalto.getOFFERTAMINIMA().doubleValue());
					aggInsertForm.setOpereUrbanizScomputo(
							FlagSNType.S.equals(appalto.getOPEREURBANIZSCOMPUTO()) ? "1" : "2");
					aggInsertForm.setPercentRibassoAgg(
							appalto.getPERCRIBASSOAGG() == null ? null : Double.valueOf(appalto.getPERCRIBASSOAGG()));
					aggInsertForm.setPercOffAumento(
							appalto.getPERCOFFAUMENTO() == null ? null : Double.valueOf(appalto.getPERCOFFAUMENTO()));
					aggInsertForm.setPreinformazione(appalto.getPREINFORMAZIONE() == null ? null
							: FlagSNType.S.equals(appalto.getPREINFORMAZIONE()) ? "1" : "2");
					aggInsertForm.setProceduraAcc(appalto.getPROCEDURAACC() == null ? null
							: FlagSNType.S.equals(appalto.getPROCEDURAACC()) ? "1" : "2");
					aggInsertForm.setTermineRidotto(appalto.getTERMINERIDOTTO() == null ? null
							: FlagSNType.S.equals(appalto.getTERMINERIDOTTO()) ? "1" : "2");
					aggInsertForm.setValoreSogliaAnomalia(appalto.getVALSOGLIAANOMALIA() == null ? null
							: appalto.getVALSOGLIAANOMALIA().doubleValue());
					aggInsertForm.setFlagRelazioneUnica(appalto.getRELAZIONEUNICA() == null ? null
							: FlagSNType.S.equals(appalto.getRELAZIONEUNICA()) ? "1" : "2");
					String cui = schedaCompleta.getCUI();
					aggInsertForm.setCui(cui);
					this.contrattiMapper.insertFaseAggiudicazione(aggInsertForm);

					if(appalto.getIDMODOGARA() != null){
						this.contrattiMapper.updateW9lottIdModoGara(codGara, codLotto, Long.valueOf(appalto.getIDMODOGARA()));
					}

					if(appalto.getIDTIPOPRESTAZIONE() != null){
						this.contrattiMapper.updateW9lottIdTipoPrestazione(codGara, codLotto, Long.valueOf(appalto.getIDTIPOPRESTAZIONE()));
					}

					Long idW9Fase = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
					if (idW9Fase == null) {
						idW9Fase = 1L;
					} else {
						idW9Fase++;
					}

					this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, idW9Fase,
							appalto.getIDSCHEDALOCALE(), 1L, appalto.getIDSCHEDASIMOG());
					List<FinanziamentoType> finanziamenti = aggiudicazione.getFinanziamenti();
					insertFinanziamenti(codGara, codLotto, finanziamenti);

					List<IncaricatoType> incarichi = aggiudicazione.getIncaricati();
					insertIncarichi(codGara, codLotto, codUffInt, incarichi, responsabiliType);

					List<SoggAggiudicatarioType> aggiudicatari = aggiudicazione.getAggiudicatari();
					insertAggiudicatari(codGara, codLotto, codUffInt, aggiudicatariType, aggiudicatari);

					List<CondizioneType> condizioni = aggiudicazione.getCondizioni();
					insertCondizioniRicorso(codGara, codLotto, codUffInt, condizioni);
				}
			}
		} catch (Exception e) {
			LOGGER.error("Errore in consulta gara, creazione scheda aggiudicazione. codgara =" + codGara + " codLotto ="
					+ codLotto, e);
			throw e;
		}

		LOGGER.debug("Execution end::istanziaAggiudicazione");
	}

	private void istanziaAggiudicazioneSottosoglia(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaCompletaType schedaCompleta, final AggiudicatariType aggiudicatariType,
			final ResponsabiliType responsabiliType) throws Exception {

		LOGGER.debug("Execution start::istanziaAggiudicazioneSottosoglia");

		try {
			SchedaSottosogliaType sottosoglia = schedaCompleta.getSottosoglia();

			if (sottosoglia != null) {
				Long faseEsecuzione = new Long(CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE);
				SottoEsclusoType appalto = sottosoglia.getAppalto();
				FaseAggSempInsertForm insertForm = new FaseAggSempInsertForm();
				insertForm.setAstaElettronica(appalto.getASTAELETTRONICA());
				insertForm.setCodGara(codGara);
				insertForm.setCodLotto(codLotto);
				insertForm.setCounter(1L);

				insertForm.setDataStipula(appalto.getDATASTIPULA() == null ? null
						: appalto.getDATASTIPULA().toGregorianCalendar().getTime());
				insertForm.setDataVerbAgg(appalto.getDATAAGGIUDICAZIONE() == null ? null
						: new SimpleDateFormat(FULL_DATE_FORMAT).parse(appalto.getDATAAGGIUDICAZIONE()));
				insertForm.setDurataCon(
						appalto.getDURATACONTRATTUALE() == null ? null : appalto.getDURATACONTRATTUALE().longValue());
				insertForm.setImportoAgg(appalto.getIMPORTOAGGIUDICAZIONE() == null ? null
						: Double.valueOf(appalto.getIMPORTOAGGIUDICAZIONE()));
				insertForm.setImportoComplAppalto(
						appalto.getIMPORTOCOMPLESSIVO() == null ? null : appalto.getIMPORTOCOMPLESSIVO().doubleValue());
				insertForm.setImportoDisposizione(appalto.getIMPORTODISPOSIZIONE() == null ? null
						: Double.valueOf(appalto.getIMPORTODISPOSIZIONE()));
				insertForm.setImportoSicurezza(appalto.getIMPORTOATTUAZIONESICUREZZA() == null ? null
						: Double.valueOf(appalto.getIMPORTOATTUAZIONESICUREZZA().doubleValue()));
				insertForm.setNum(1L);
				insertForm.setPercOffAumento(
						appalto.getPERCOFFAUMENTO() == null ? null : Double.valueOf(appalto.getPERCOFFAUMENTO()));
				insertForm.setPercRibassoAgg(
						appalto.getPERCRIBASSOAGG() == null ? null : Double.valueOf(appalto.getPERCRIBASSOAGG()));

				this.contrattiMapper.insertFaseAggiudicazioneSemp(insertForm);

				Long idW9Fase = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
				if (idW9Fase == null) {
					idW9Fase = 1L;
				} else {
					idW9Fase++;
				}

				String idScheda = appalto.getIDSCHEDALOCALE();
				this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, idW9Fase, idScheda, 1L,
						appalto.getIDSCHEDASIMOG());

				List<IncaricatoType> incarichi = sottosoglia.getIncaricati();
				insertIncarichi(codGara, codLotto, codUffInt, incarichi, responsabiliType);

				List<SoggAggiudicatarioType> aggiudicatari = sottosoglia.getAggiudicatari();
				insertAggiudicatari(codGara, codLotto, codUffInt, aggiudicatariType, aggiudicatari);

				List<CondizioneType> condizioni = sottosoglia.getCondizioni();
				insertCondizioniRicorso(codGara, codLotto, codUffInt, condizioni);
			}
		} catch (Exception e) {
			LOGGER.error("Errore in consulta gara, creazione scheda aggiudicazione semp sottosoglia. codgara ="
					+ codGara + " codLotto =" + codLotto, e);
			throw e;
		}

		LOGGER.debug("Execution end::istanziaAggiudicazioneSottosoglia");
	}

	private void istanziaAggiudicazioneEsclusa(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaCompletaType schedaCompleta, final AggiudicatariType aggiudicatariType,
			final ResponsabiliType responsabiliType) throws Exception {

		LOGGER.debug("Execution start::istanziaAggiudicazioneEsclusa");

		try {
			SchedaEsclusoType escluso = schedaCompleta.getEscluso();

			if (escluso != null) {
				Long faseEsecuzione = new Long(CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE);
				SottoEsclusoType appalto = escluso.getAppalto();
				FaseAggSempInsertForm insertForm = new FaseAggSempInsertForm();
				insertForm.setAstaElettronica(appalto.getASTAELETTRONICA());
				insertForm.setCodGara(codGara);
				insertForm.setCodLotto(codLotto);
				insertForm.setCounter(1L);

				insertForm.setDataStipula(appalto.getDATASTIPULA() == null ? null
						: appalto.getDATASTIPULA().toGregorianCalendar().getTime());
				insertForm.setDataVerbAgg(appalto.getDATAAGGIUDICAZIONE() == null ? null
						: new SimpleDateFormat(FULL_DATE_FORMAT).parse(appalto.getDATAAGGIUDICAZIONE()));
				insertForm.setDurataCon(
						appalto.getDURATACONTRATTUALE() == null ? null : appalto.getDURATACONTRATTUALE().longValue());
				insertForm.setImportoAgg(appalto.getIMPORTOAGGIUDICAZIONE() == null ? null
						: Double.valueOf(appalto.getIMPORTOAGGIUDICAZIONE()));
				insertForm.setImportoComplAppalto(
						appalto.getIMPORTOCOMPLESSIVO() == null ? null : appalto.getIMPORTOCOMPLESSIVO().doubleValue());
				insertForm.setImportoDisposizione(appalto.getIMPORTODISPOSIZIONE() == null ? null
						: Double.valueOf(appalto.getIMPORTODISPOSIZIONE()));
				insertForm.setImportoSicurezza(appalto.getIMPORTOATTUAZIONESICUREZZA() == null ? null
						: Double.valueOf(appalto.getIMPORTOATTUAZIONESICUREZZA().doubleValue()));
				insertForm.setNum(1L);
				insertForm.setPercOffAumento(
						appalto.getPERCOFFAUMENTO() == null ? null : Double.valueOf(appalto.getPERCOFFAUMENTO()));
				insertForm.setPercRibassoAgg(
						appalto.getPERCRIBASSOAGG() == null ? null : Double.valueOf(appalto.getPERCRIBASSOAGG()));

				this.contrattiMapper.insertFaseAggiudicazioneSemp(insertForm);

				Long idW9Fase = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
				if (idW9Fase == null) {
					idW9Fase = 1L;
				} else {
					idW9Fase++;
				}

				String idScheda = appalto.getIDSCHEDALOCALE();
				this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, idW9Fase, idScheda, 1L,
						appalto.getIDSCHEDASIMOG());

				List<IncaricatoType> incarichi = escluso.getIncaricati();
				insertIncarichi(codGara, codLotto, codUffInt, incarichi, responsabiliType);

				List<SoggAggiudicatarioType> aggiudicatari = escluso.getAggiudicatari();
				insertAggiudicatari(codGara, codLotto, codUffInt, aggiudicatariType, aggiudicatari);
			}
		} catch (Exception e) {
			LOGGER.error("Errore in consulta gara, creazione scheda aggiudicazione esclusa. codgara =" + codGara
					+ " codLotto =" + codLotto, e);
			throw e;
		}

		LOGGER.debug("Execution end::istanziaAggiudicazioneEsclusa");
	}

	private void istanziaAdesioneAccordoQuadro(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaCompletaType schedaCompleta, final AggiudicatariType aggiudicatariType,
			final ResponsabiliType responsabiliType) throws Exception {

		LOGGER.debug("Execution start::istanziaAdesioneAccordoQuadro");

		try {

			AdesioneType adesione = schedaCompleta.getAdesione();

			if (adesione != null) {
				Long faseEsecuzione = new Long(CostantiW9.ADESIONE_ACCORDO_QUADRO);
				AppaltoAdesioneType appalto = adesione.getAppalto();
				Double importoForniture = appalto.getIMPORTOFORNITURE() == null ? null
						: Double.valueOf(appalto.getIMPORTOFORNITURE());
				Double importoServizi = appalto.getIMPORTOSERVIZI() == null ? null
						: Double.valueOf(appalto.getIMPORTOSERVIZI());
				Double importoLavori = appalto.getIMPORTOLAVORI() == null ? null
						: Double.valueOf(appalto.getIMPORTOLAVORI());
				Double importoSicurezza = appalto.getIMPORTOATTUAZIONESICUREZZA() == null ? null
						: Double.valueOf(appalto.getIMPORTOATTUAZIONESICUREZZA().doubleValue());
				Double importoProgettazione = appalto.getIMPORTOPROGETTAZIONE() == null ? null
						: Double.valueOf(appalto.getIMPORTOPROGETTAZIONE());
				Double importoSubtotale = 0d;
				if (importoForniture != null) {
					importoSubtotale = importoSubtotale + importoForniture;
				}
				if (importoServizi != null) {
					importoSubtotale = importoSubtotale + importoServizi;
				}
				if (importoLavori != null) {
					importoSubtotale = importoSubtotale + importoLavori;
				}

				FaseAdesioneAccordoQuadroInsertForm insertForm = new FaseAdesioneAccordoQuadroInsertForm();
				insertForm.setCodGara(codGara);
				insertForm.setCodLotto(codLotto);
				insertForm.setCodStrumento(appalto.getCODSTRUMENTO());
				insertForm.setDataVerbAggiudicazione(appalto.getDATAAGGIUDICAZIONE() == null ? null
						: new SimpleDateFormat(FULL_DATE_FORMAT).parse(appalto.getDATAAGGIUDICAZIONE()));
				insertForm.setFlagRichSubappalto(appalto.getFLAGRICHSUBAPPALTO());
				insertForm.setImpNonAssog(
						appalto.getIMPNONASSOG() == null ? null : Double.valueOf(appalto.getIMPNONASSOG()));
				insertForm.setImportoAggiudicazione(appalto.getIMPORTOAGGIUDICAZIONE() == null ? null
						: Double.valueOf(appalto.getIMPORTOAGGIUDICAZIONE()));
				insertForm.setImportoForniture(importoForniture);
				insertForm.setImportoLavori(importoLavori);
				insertForm.setImportoProgettazione(importoProgettazione);
				insertForm.setImportoServizi(importoServizi);
				insertForm.setImportoSicurezza(importoSicurezza);
				insertForm.setImportosubtotale(importoSubtotale);
				insertForm.setNum(1L);
				insertForm.setPercOffAumento(
						appalto.getPERCOFFAUMENTO() == null ? null : Double.valueOf(appalto.getPERCOFFAUMENTO()));
				insertForm.setPercentRibassoAgg(
						appalto.getPERCRIBASSOAGG() == null ? null : Double.valueOf(appalto.getPERCRIBASSOAGG()));

				this.contrattiMapper.insertFaseAdesioneAccordo(insertForm);

				Long idW9Fase = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
				if (idW9Fase == null) {
					idW9Fase = 1L;
				} else {
					idW9Fase++;
				}

				this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, idW9Fase,
						appalto.getIDSCHEDALOCALE(), 1L, appalto.getIDSCHEDASIMOG());

				List<IncaricatoType> incarichi = adesione.getIncaricati();
				insertIncarichi(codGara, codLotto, codUffInt, incarichi, responsabiliType);

				List<SoggAggiudicatarioType> aggiudicatari = adesione.getAggiudicatari();
				insertAggiudicatari(codGara, codLotto, codUffInt, aggiudicatariType, aggiudicatari);

				List<FinanziamentoType> finanziamenti = adesione.getFinanziamenti();
				insertFinanziamenti(codGara, codLotto, finanziamenti);

			}
		} catch (Exception e) {
			LOGGER.error("Errore in consulta gara, creazione scheda adesione accordo quadro. codgara =" + codGara
					+ " codLotto =" + codLotto, e);
			throw e;
		}

		LOGGER.debug("Execution end::istanziaAdesioneAccordoQuadro");
	}

	private void istanziaInizio(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaCompletaType schedaCompleta, final ResponsabiliType responsabiliType) throws Exception {

		LOGGER.debug("Execution start::istanziaInizio");

		try {

			DatiInizioType inizio = schedaCompleta.getDatiInizio();

			if (inizio != null) {
				Long faseEsecuzione = new Long(CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA);
				InizioType datiInizio = inizio.getInizio();
				PubblicazioneType pubblicazione = inizio.getPubblicazioneEsito();
				FaseInizialeEsecuzioneInsertForm insertForm = new FaseInizialeEsecuzioneInsertForm();
				FlagSNType frazionata = datiInizio.getFLAGFRAZIONATA();
				insertForm.setCodGara(codGara);
				insertForm.setCodLotto(codLotto);
				insertForm.setDataApprovazioneProg(datiInizio.getDATAAPPPROGESEC() == null ? null
						: datiInizio.getDATAAPPPROGESEC().toGregorianCalendar().getTime());
				insertForm.setDataEsecutivita(datiInizio.getDATAESECUTIVITA() == null ? null
						: datiInizio.getDATAESECUTIVITA().toGregorianCalendar().getTime());
				insertForm.setDataInizioProg(datiInizio.getDATAINIPROGESEC() == null ? null
						: datiInizio.getDATAINIPROGESEC().toGregorianCalendar().getTime());
				insertForm.setDataStipula(datiInizio.getDATASTIPULA() == null ? null
						: datiInizio.getDATASTIPULA().toGregorianCalendar().getTime());
				insertForm.setDataTermine(datiInizio.getDATATERMINE() == null ? null
						: new SimpleDateFormat(FULL_DATE_FORMAT).parse(datiInizio.getDATATERMINE()));
				insertForm.setDataVerbaleCons(datiInizio.getDATAVERBALECONS() == null ? null
						: datiInizio.getDATAVERBALECONS().toGregorianCalendar().getTime());
				insertForm.setDataVerbaleDef(datiInizio.getDATAVERBALEDEF() == null ? null
						: datiInizio.getDATAVERBALEDEF().toGregorianCalendar().getTime());
				insertForm.setDataVerbInizio(datiInizio.getDATAVERBINIZIO() == null ? null
						: datiInizio.getDATAVERBINIZIO().toGregorianCalendar().getTime());
				insertForm.setFrazionata(frazionata == null ? null : frazionata.value().equals("N") ? "2" : "1");
				insertForm.setImportoCauzione(
						datiInizio.getIMPORTOCAUZIONE() == null ? null : datiInizio.getIMPORTOCAUZIONE().doubleValue());
				insertForm.setNum(1L);
				insertForm.setNumAppa(1L);
				insertForm.setRiserva(datiInizio.getFLAGRISERVA());
				this.contrattiMapper.insertFaseInizialeEsecuzione(insertForm);
				insertPubblicazione(codGara, codLotto, pubblicazione);

				Long idW9Fase = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
				if (idW9Fase == null) {
					idW9Fase = 1L;
				} else {
					idW9Fase++;
				}

				this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, idW9Fase,
						datiInizio.getIDSCHEDALOCALE(), 1L, datiInizio.getIDSCHEDASIMOG());
				List<IncaricatoType> incarichi = inizio.getIncaricati();
				insertIncarichi(codGara, codLotto, codUffInt, incarichi, responsabiliType);
				SchedaSicurezzaInsertForm sicurezzaInsertForm = new SchedaSicurezzaInsertForm();
				sicurezzaInsertForm.setCodGara(codGara);
				sicurezzaInsertForm.setCodLotto(codLotto);
				sicurezzaInsertForm.setDirettoreOperativo("2");
				sicurezzaInsertForm.setNum(1L);
				sicurezzaInsertForm.setNumIniz(1L);
				sicurezzaInsertForm.setPianoSicurezza("2");
				sicurezzaInsertForm.setTutor("2");
				this.contrattiMapper.insertSchedaSicurezza(sicurezzaInsertForm);
			}
		} catch (Exception e) {
			LOGGER.error("Errore in consulta gara, creazione scheda iniziale. codgara =" + codGara + " codLotto ="
					+ codLotto, e);
			throw e;
		}

		LOGGER.debug("Execution end::istanziaInizio");
	}

	private void istanziaStipulaAccordoQuadro(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaCompletaType schedaCompleta) {

		LOGGER.debug("Execution start::istanziaStipulaAccordoQuadro");

		try {

			DatiStipulaType stipula = schedaCompleta.getDatiStipula();

			if (stipula != null) {
				Long faseEsecuzione = new Long(CostantiW9.STIPULA_ACCORDO_QUADRO);
				StipulaType datiStipula = stipula.getStipula();
				PubblicazioneType pubblicazione = stipula.getPubblicazioneEsito();
				FaseStipulaAccordoQuadroInsertForm insertForm = new FaseStipulaAccordoQuadroInsertForm();
				insertForm.setCodGara(codGara);
				insertForm.setCodLotto(codLotto);
				insertForm.setDataDecorrenza(datiStipula.getDATADECORRRENZA() == null ? null
						: datiStipula.getDATADECORRRENZA().toGregorianCalendar().getTime());
				insertForm.setDataScadenza(datiStipula.getDATASCADENZA() == null ? null
						: datiStipula.getDATASCADENZA().toGregorianCalendar().getTime());
				insertForm.setDataStipula(datiStipula.getDATASTIPULA() == null ? null
						: datiStipula.getDATASTIPULA().toGregorianCalendar().getTime());
				insertForm.setNum(1L);
				insertForm.setNumAppa(1L);
				this.contrattiMapper.insertFaseStipulaAccordoQuadro(insertForm);

				Long idW9Fase = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
				if (idW9Fase == null) {
					idW9Fase = 1L;
				} else {
					idW9Fase++;
				}
				this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, idW9Fase,
						datiStipula.getIDSCHEDALOCALE(), 1L, datiStipula.getIDSCHEDASIMOG());
				insertPubblicazione(codGara, codLotto, pubblicazione);
			}
		} catch (Exception e) {
			LOGGER.error("Errore in consulta gara, creazione scheda stipula accordo quadro. codgara =" + codGara
					+ " codLotto =" + codLotto, e);
			throw e;
		}

		LOGGER.debug("Execution end::istanziaStipulaAccordoQuadro");
	}

	private void istanziaFaseAvanzamento(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaCompletaType schedaCompleta) {

		LOGGER.debug("Execution start::istanziaFaseAvanzamento");

		try {

			AvanzamentiType avanzamenti = schedaCompleta.getDatiAvanzamenti();
			Long faseEsecuzione = new Long(CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA);

			if (avanzamenti != null && avanzamenti.getAvanzamento() != null) {

				Long num = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
				if (num == null) {
					num = 1L;
				} else {
					num++;
				}

				for (AvanzamentoType avanzamento : avanzamenti.getAvanzamento()) {
					FaseAvanzamentoInsertForm insertForm = new FaseAvanzamentoInsertForm();
					insertForm.setCodGara(codGara);
					insertForm.setCodLotto(codLotto);
					insertForm.setDataAnticipazione(avanzamento.getDATAANTICIPAZIONE() == null ? null
							: avanzamento.getDATAANTICIPAZIONE().toGregorianCalendar().getTime());
					insertForm.setDataCertificato(avanzamento.getDATACERTIFICATO() == null ? null
							: avanzamento.getDATACERTIFICATO().toGregorianCalendar().getTime());
					insertForm.setDataRaggiungimento(avanzamento.getDATARAGGIUNGIMENTO() == null ? null
							: avanzamento.getDATARAGGIUNGIMENTO().toGregorianCalendar().getTime());
					insertForm.setDenomAvanzamento(avanzamento.getDENOMAVANZAMENTO());
					insertForm.setFlagPagamento(
							avanzamento.getFLAGPAGAMENTO() == null ? null : new Long(avanzamento.getFLAGPAGAMENTO()));
					insertForm.setFlagRitardo(avanzamento.getFLAGRITARDO().value());
					insertForm.setImportoAnticipazione(avanzamento.getIMPORTOANTICIPAZIONE() == null ? null
							: avanzamento.getIMPORTOANTICIPAZIONE().doubleValue());
					insertForm.setImportoCertificato(avanzamento.getIMPORTOCERTIFICATO() == null ? null
							: avanzamento.getIMPORTOCERTIFICATO().doubleValue());
					insertForm.setImportoSal(
							avanzamento.getIMPORTOSAL() == null ? null : avanzamento.getIMPORTOSAL().doubleValue());
					insertForm.setNum(num);
					insertForm.setNumAppa(1L);
					insertForm.setNumGiorniProroga(avanzamento.getNUMGIORNIPROROGA() == null ? null
							: new Long(avanzamento.getNUMGIORNIPROROGA()));
					insertForm.setNumGiorniScost(
							avanzamento.getNUMGIORNISCOST() == null ? null : new Long(avanzamento.getNUMGIORNISCOST()));
					this.contrattiMapper.insertFaseAvanzamento(insertForm);
					this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, num,
							avanzamento.getIDSCHEDALOCALE(), 1L, avanzamento.getIDSCHEDASIMOG());
					num++;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Errore in consulta gara, creazione scheda avanzamento. codgara =" + codGara + " codLotto ="
					+ codLotto, e);
			throw e;
		}
	}

	private void istanziaFaseConclusione(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaCompletaType schedaCompleta) {

		LOGGER.debug("Execution start::insertSchedeLotto");
		LOGGER.debug("Execution end::insertSchedeLotto");

		try {

			ConclusioneType conclusione = schedaCompleta.getDatiConclusione();

			if (conclusione != null) {
				Long faseEsecuzione = new Long(CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA);
				FlagSNType polizza = conclusione.getFLAGPOLIZZA();
				FaseConclusioneInsertForm insertForm = new FaseConclusioneInsertForm();
				insertForm.setCodGara(codGara);
				insertForm.setCodLotto(codLotto);
				insertForm.setDataRisoluzione(conclusione.getDATARISOLUZIONE() == null ? null
						: conclusione.getDATARISOLUZIONE().toGregorianCalendar().getTime());
				insertForm.setDataTermineContrattuale(conclusione.getTERMINECONTRATTULTIMAZIONE() == null ? null
						: conclusione.getTERMINECONTRATTULTIMAZIONE().toGregorianCalendar().getTime());
				insertForm.setDataUltimazione(conclusione.getDATAULTIMAZIONE() == null ? null
						: conclusione.getDATAULTIMAZIONE().toGregorianCalendar().getTime());
				insertForm.setDataVerbaleConsegna(conclusione.getDATAVERBCONSEGNAAVVIO() == null ? null
						: conclusione.getDATAVERBCONSEGNAAVVIO().toGregorianCalendar().getTime());
				insertForm.setFlagOneri(conclusione.getFLAGONERI());
				insertForm.setFlagPolizza(polizza == null ? null : polizza.value().equals("N") ? "2" : "1");
				insertForm.setImportoOneri(conclusione.getONERIRISOLUZIONE() == null ? null
						: conclusione.getONERIRISOLUZIONE().doubleValue());
				insertForm.setInterruzioneAnticipata(conclusione.getIDMOTIVOINTERR());
				insertForm.setMotivoInterruzione(
						conclusione.getIDMOTIVOINTERR() == null ? null : new Long(conclusione.getIDMOTIVOINTERR()));
				insertForm.setMotivoRisoluzione(
						conclusione.getIDMOTIVORISOL() == null ? null : new Long(conclusione.getIDMOTIVORISOL()));
				insertForm.setNum(1L);
				insertForm.setNumAppa(1L);
				insertForm.setNumgiorniProroga(conclusione.getNUMGIORNIPROROGA() == null ? null
						: conclusione.getNUMGIORNIPROROGA().longValue());
				insertForm.setNumInfortuniMortali(new Long(conclusione.getNUMINFMORT()));
				insertForm.setNumInfortuniPermanenti(new Long(conclusione.getNUMINFPERM()));
				insertForm.setNumInfortuni(new Long(conclusione.getNUMINFORTUNI()));
				this.contrattiMapper.insertFaseConclusioneContratto(insertForm);

				Long idW9Fase = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
				if (idW9Fase == null) {
					idW9Fase = 1L;
				} else {
					idW9Fase++;
				}
				this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, idW9Fase,
						conclusione.getIDSCHEDALOCALE(), 1L, conclusione.getIDSCHEDASIMOG());
			}
		} catch (Exception e) {
			LOGGER.error("Errore in consulta gara, creazione scheda conclusione. codgara =" + codGara + " codLotto ="
					+ codLotto, e);
			throw e;
		}

		LOGGER.debug("Execution end::istanziaFaseAvanzamento");
	}

	private void istanziaFaseCollaudo(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaCompletaType schedaCompleta, final ResponsabiliType responsabiliType) throws Exception {

		LOGGER.debug("Execution start::istanziaFaseCollaudo");

		try {

			DatiCollaudoType collaudo = schedaCompleta.getDatiCollaudo();

			if (collaudo != null) {
				Long faseEsecuzione = new Long(CostantiW9.COLLAUDO_CONTRATTO);
				CollaudoType datiCollaudo = collaudo.getCollaudo();
				FaseCollaudoInsertForm insertForm = new FaseCollaudoInsertForm();
				FlagSNType lavoriEstesi = datiCollaudo.getLAVORIESTESI();
				insertForm.setCodGara(codGara);
				insertForm.setCodLotto(codLotto);

				Double importoFinaleLavori = datiCollaudo.getIMPFINALELAVORI() == null ? null
						: datiCollaudo.getIMPFINALELAVORI().doubleValue();
				Double importoFinaleForniture = datiCollaudo.getIMPFINALEFORNIT() == null ? null
						: datiCollaudo.getIMPFINALEFORNIT().doubleValue();
				Double importoFinaleServizi = datiCollaudo.getIMPFINALESERVIZI() == null ? null
						: datiCollaudo.getIMPFINALESERVIZI().doubleValue();
				Double importoFinaleSicurezza = datiCollaudo.getIMPFINALESECUR() == null ? null
						: datiCollaudo.getIMPFINALESECUR().doubleValue();
				Double importoProgettazione = StringUtils.isBlank(datiCollaudo.getIMPPROGETTAZIONE()) ? null
						: Double.valueOf(datiCollaudo.getIMPPROGETTAZIONE());
				Double importoDisposizione = StringUtils.isBlank(datiCollaudo.getIMPDISPOSIZIONE()) ? null
						: Double.valueOf(datiCollaudo.getIMPDISPOSIZIONE());
				Double importoSubtotale = 0d;

				if (importoFinaleLavori != null) {
					importoSubtotale += importoFinaleLavori;
				}

				if (importoFinaleForniture != null) {
					importoSubtotale += importoFinaleForniture;
				}

				if (importoFinaleServizi != null) {
					importoSubtotale += importoFinaleServizi;
				}

				Double importoFinaleComplessivoAppalto = 0d;

				if (importoSubtotale != null) {
					importoFinaleComplessivoAppalto += importoSubtotale;
				}

				if (importoFinaleSicurezza != null) {
					importoFinaleComplessivoAppalto += importoFinaleSicurezza;
				}

				if (importoProgettazione != null) {
					importoFinaleComplessivoAppalto += importoProgettazione;
				}

				Double importoFinaleComplessivoIntervento = 0d;

				if (importoFinaleComplessivoAppalto != null) {
					importoFinaleComplessivoIntervento += importoFinaleComplessivoAppalto;
				}

				if (importoDisposizione != null) {
					importoFinaleComplessivoIntervento += importoDisposizione;
				}

				insertForm.setDataCertEsecuzione(datiCollaudo.getDATAREGOLAREESEC() == null ? null
						: datiCollaudo.getDATAREGOLAREESEC().toGregorianCalendar().getTime());
				insertForm.setDataCollaudoStatico(datiCollaudo.getDATACOLLAUDOSTAT() == null ? null
						: datiCollaudo.getDATACOLLAUDOSTAT().toGregorianCalendar().getTime());
				insertForm.setDataDelibera(datiCollaudo.getDATADELIBERA() == null ? null
						: datiCollaudo.getDATADELIBERA().toGregorianCalendar().getTime());
				insertForm.setDataInizioOperazioni(datiCollaudo.getDATAINIZIOOPER() == null ? null
						: datiCollaudo.getDATAINIZIOOPER().toGregorianCalendar().getTime());
				insertForm.setDataNomina(datiCollaudo.getDATANOMINACOLL() == null ? null
						: datiCollaudo.getDATANOMINACOLL().toGregorianCalendar().getTime());
				insertForm.setDataRedazioneCertificato(datiCollaudo.getDATACERTCOLLAUDO() == null ? null
						: datiCollaudo.getDATACERTCOLLAUDO().toGregorianCalendar().getTime());
				insertForm.setEsitoCollaudo(datiCollaudo.getESITOCOLLAUDO().value());
				insertForm.setImportoEventualeDefAmm(
						datiCollaudo.getAMMIMPORTODEF() == null ? null : datiCollaudo.getAMMIMPORTODEF().doubleValue());
				insertForm.setImportoEventualeDefArb(
						datiCollaudo.getARBIMPORTODEF() == null ? null : datiCollaudo.getARBIMPORTODEF().doubleValue());
				insertForm.setImportoEventualeDefGiu(
						datiCollaudo.getGIUIMPORTODEF() == null ? null : datiCollaudo.getGIUIMPORTODEF().doubleValue());
				insertForm.setImportoEventualeDefTra(
						datiCollaudo.getTRAIMPORTODEF() == null ? null : datiCollaudo.getTRAIMPORTODEF().doubleValue());
				insertForm.setImportoFinaleLavori(importoFinaleLavori);
				insertForm.setImportoFinaleForniture(importoFinaleForniture);
				insertForm.setImportoFinaleServizi(importoFinaleServizi);
				insertForm.setImportoSubtotale(importoSubtotale);
				insertForm.setImportoFinaleSicurezza(importoFinaleSicurezza);
				insertForm.setImportoProgettazione(importoProgettazione);
				insertForm.setImportoDisposizione(importoDisposizione);
				insertForm.setImportoComplessivoAppalto(importoFinaleComplessivoAppalto);
				insertForm.setImportoComplessivoIntervento(importoFinaleComplessivoIntervento);
				insertForm.setLavoriEstesi(lavoriEstesi == null ? null : lavoriEstesi.value().equals("N") ? "2" : "1");
				insertForm.setImportoTotaleRichiestoAmm(datiCollaudo.getAMMIMPORTORICH() == null ? null
						: datiCollaudo.getAMMIMPORTORICH().doubleValue());
				insertForm.setImportoTotaleRichiestoArb(datiCollaudo.getARBIMPORTORICH() == null ? null
						: datiCollaudo.getARBIMPORTORICH().doubleValue());
				insertForm.setImportoTotaleRichiestoGiu(datiCollaudo.getGIUIMPORTORICH() == null ? null
						: datiCollaudo.getGIUIMPORTORICH().doubleValue());
				insertForm.setImportoTotaleRichiestoTra(datiCollaudo.getTRAIMPORTORICH() == null ? null
						: datiCollaudo.getTRAIMPORTORICH().doubleValue());
				insertForm.setModalitaCollaudo(
						datiCollaudo.getMODOCOLLAUDO() == null ? null : new Long(datiCollaudo.getMODOCOLLAUDO()));
				insertForm.setNum(1L);
				insertForm.setNumAppa(1L);
				insertForm.setNumeroRiserveDaDefinireAmm(
						datiCollaudo.getAMMNUMDADEF() == null ? null : datiCollaudo.getAMMNUMDADEF().longValue());
				insertForm.setNumeroRiserveDaDefinireArb(
						datiCollaudo.getARBNUMDADEF() == null ? null : datiCollaudo.getARBNUMDADEF().longValue());
				insertForm.setNumeroRiserveDaDefinireGiu(
						datiCollaudo.getGIUNUMDADEF() == null ? null : datiCollaudo.getGIUNUMDADEF().longValue());
				insertForm.setNumeroRiserveDaDefinireTra(
						datiCollaudo.getTRANUMDADEF() == null ? null : datiCollaudo.getTRANUMDADEF().longValue());
				insertForm.setNumeroRiserveDefiniteAmm(
						datiCollaudo.getAMMNUMDEFINITE() == null ? null : datiCollaudo.getAMMNUMDEFINITE().longValue());
				insertForm.setNumeroRiserveDefiniteArb(
						datiCollaudo.getARBNUMDEFINITE() == null ? null : datiCollaudo.getARBNUMDEFINITE().longValue());
				insertForm.setNumeroRiserveDefiniteGiu(
						datiCollaudo.getGIUNUMDEFINITE() == null ? null : datiCollaudo.getGIUNUMDEFINITE().longValue());
				insertForm.setNumeroRiserveDefiniteTra(
						datiCollaudo.getTRANUMDEFINITE() == null ? null : datiCollaudo.getTRANUMDEFINITE().longValue());
				this.contrattiMapper.insertFaseCollaudo(insertForm);

				insertIncarichi(codGara, codLotto, codUffInt, collaudo.getIncaricati(), responsabiliType);
				Long idW9Fase = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
				if (idW9Fase == null) {
					idW9Fase = 1L;
				} else {
					idW9Fase++;
				}
				this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, idW9Fase,
						datiCollaudo.getIDSCHEDALOCALE(), 1L, datiCollaudo.getIDSCHEDASIMOG());
			}
		} catch (Exception e) {
			LOGGER.error("Errore in consulta gara, creazione scheda collaudo. codgara =" + codGara + " codLotto ="
					+ codLotto, e);
			throw e;
		}

		LOGGER.debug("Execution end::istanziaFaseCollaudo");
	}

	private void istanziaRitardo(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaCompletaType schedaCompleta) throws Exception {

		LOGGER.debug("Execution start::istanziaRitardo");

		try {

			RitardiType ritardi = schedaCompleta.getDatiRitardi();
			Long faseEsecuzione = new Long(CostantiW9.ISTANZA_RECESSO);

			if (ritardi != null && ritardi.getRitardo() != null) {

				Long num = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
				if (num == null) {
					num = 1L;
				} else {
					num++;
				}

				for (RitardoType ritardo : ritardi.getRitardo()) {

					FlagSNType accolta = ritardo.getFLAGACCOLTA();
					FlagSNType tardiva = ritardo.getFLAGTARDIVA();
					FlagSNType ripresa = ritardo.getFLAGRIPRESA();

					FaseIstanzaRecessoInsertForm insertForm = new FaseIstanzaRecessoInsertForm();
					insertForm.setCodGara(codGara);
					insertForm.setCodLotto(codLotto);
					insertForm.setDataConsegna(ritardo.getDATACONSEGNA() == null ? null
							: ritardo.getDATACONSEGNA().toGregorianCalendar().getTime());
					insertForm.setDataIstanzaRecesso(ritardo.getDATAISTRECESSO() == null ? null
							: ritardo.getDATAISTRECESSO().toGregorianCalendar().getTime());
					insertForm.setDataTermine(ritardo.getDATATERMINE() == null ? null
							: new SimpleDateFormat(FULL_DATE_FORMAT).parse(ritardo.getDATATERMINE()));
					insertForm.setDurataSospensione(new Long(ritardo.getDURATASOSP()));
					insertForm.setFlagAccolta(accolta == null ? null : accolta.value().equals("N") ? "2" : "1");
					insertForm.setFlagRipresa(ripresa == null ? null : ripresa.value().equals("N") ? "2" : "1");
					insertForm.setFlagTardiva(tardiva == null ? null : tardiva.value().equals("N") ? "2" : "1");
					insertForm.setImportoOneri(
							ritardo.getIMPORTOONERI() == null ? null : ritardo.getIMPORTOONERI().doubleValue());
					insertForm.setImportoSpese(
							ritardo.getIMPORTOSPESE() == null ? null : ritardo.getIMPORTOSPESE().doubleValue());
					insertForm.setMotivoSospensione(ritardo.getMOTIVOSOSP());
					insertForm.setNum(num);
					insertForm.setNumAppa(1L);
					insertForm.setRitardo(new Long(ritardo.getDURATASOSP()));
					insertForm.setFlagRiserva(ritardo.getFLAGRISERVA());
					insertForm.setTipoComunicazione(ritardo.getTIPOCOMUN().value());
					this.contrattiMapper.insertFaseIstanzaRecesso(insertForm);
					this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, num,
							ritardo.getIDSCHEDALOCALE(), 1L, ritardo.getIDSCHEDASIMOG());
					num++;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Errore in consulta gara, creazione scheda istanza recesso. codgara =" + codGara
					+ " codLotto =" + codLotto, e);
			throw e;
		}

		LOGGER.debug("Execution end::istanziaRitardo");
	}

	private void istanziaAccordBonario(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaCompletaType schedaCompleta) {

		LOGGER.debug("Execution start::istanziaAccordBonario");

		try {

			AccordiBonariType accordi = schedaCompleta.getDatiAccordi();
			Long faseEsecuzione = new Long(CostantiW9.ACCORDO_BONARIO);

			if (accordi != null && accordi.getAccordoBonario() != null) {

				Long num = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
				if (num == null) {
					num = 1L;
				} else {
					num++;
				}

				for (AccordoBonarioType accordo : accordi.getAccordoBonario()) {

					FaseAccordoBonarioInsertForm insertForm = new FaseAccordoBonarioInsertForm();
					insertForm.setCodGara(codGara);
					insertForm.setCodLotto(codLotto);
					insertForm.setDataAccordo(accordo.getDATAACCORDO() == null ? null
							: accordo.getDATAACCORDO().toGregorianCalendar().getTime());
					insertForm.setNum(num);
					insertForm.setNumAppa(1L);
					insertForm.setNumRiserve(new Long(accordo.getNUMRISERVE()));
					insertForm.setOneriDerivanti(
							accordo.getONERIDERIVANTI() == null ? null : accordo.getONERIDERIVANTI().doubleValue());
					this.contrattiMapper.insertFaseAccordoBonario(insertForm);
					this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, num,
							accordo.getIDSCHEDALOCALE(), 1L, accordo.getIDSCHEDASIMOG());
					num++;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Errore in consulta gara, creazione scheda accordo bonario. codgara =" + codGara
					+ " codLotto =" + codLotto, e);
			throw e;
		}

		LOGGER.debug("Execution end::istanziaAccordBonario");
	}

	private void istanziaSospensioni(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaCompletaType schedaCompleta) {

		LOGGER.debug("Execution start::istanziaSospensioni");

		try {

			SospensioniType sospensioni = schedaCompleta.getDatiSospensioni();
			Long faseEsecuzione = new Long(CostantiW9.SOSPENSIONE_CONTRATTO);

			if (sospensioni != null && sospensioni.getSospensione() != null) {

				Long num = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
				if (num == null) {
					num = 1L;
				} else {
					num++;
				}

				for (SospensioneType sospensione : sospensioni.getSospensione()) {

					FlagSNType riserve = sospensione.getFLAGRISERVE();
					FlagSNType verbale = sospensione.getFLAGVERBALE();
					FlagSNType superoTempo = sospensione.getFLAGSUPEROTEMPO();
					FaseSospensioneInsertForm insertForm = new FaseSospensioneInsertForm();
					insertForm.setCodGara(codGara);
					insertForm.setCodLotto(codLotto);
					insertForm.setNum(num);
					insertForm.setNumAppa(1L);
					insertForm.setDataVerbRipr(sospensione.getDATAVERBRIPR() == null ? null
							: sospensione.getDATAVERBRIPR().toGregorianCalendar().getTime());
					insertForm.setDataVerbSosp(sospensione.getDATAVERBSOSP() == null ? null
							: sospensione.getDATAVERBSOSP().toGregorianCalendar().getTime());
					insertForm.setFlagRiserve(riserve == null ? null : riserve.value().equals("N") ? "2" : "1");
					insertForm.setFlagSuperoTempo(
							superoTempo == null ? null : superoTempo.value().equals("N") ? "2" : "1");
					insertForm.setFlagVerbale(verbale == null ? null : verbale.value().equals("N") ? "2" : "1");
					insertForm.setMotivoSosp(
							sospensione.getIDMOTIVOSOSP() == null ? null : new Long(sospensione.getIDMOTIVOSOSP()));
					this.contrattiMapper.insertFaseSospensione(insertForm);
					this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, num,
							sospensione.getIDSCHEDALOCALE(), 1L, sospensione.getIDSCHEDASIMOG());
					num++;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Errore in consulta gara, creazione scheda sospensione. codgara =" + codGara + " codLotto ="
					+ codLotto, e);
			throw e;
		}

		LOGGER.debug("Execution end::istanziaSospensioni");
	}

	private void istanziaVarianti(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaCompletaType schedaCompleta) {

		LOGGER.debug("Execution start::istanziaVarianti");

		try {

			VariantiType varianti = schedaCompleta.getDatiVarianti();
			Long faseEsecuzione = new Long(CostantiW9.VARIANTE_CONTRATTO);

			if (varianti != null && varianti.getVariante() != null) {

				Long num = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
				if (num == null) {
					num = 1L;
				} else {
					num++;
				}

				for (VarianteType variante : varianti.getVariante()) {

					RecVarianteType record = variante.getVariante();
					FaseVarianteInsertForm insertForm = new FaseVarianteInsertForm();
					insertForm.setCodGara(codGara);
					insertForm.setCodLotto(codLotto);
					insertForm.setNum(num);
					insertForm.setNumAppa(1L);

					Double importoForniture = record.getIMPRIDETFORNIT() == null ? null
							: record.getIMPRIDETFORNIT().doubleValue();
					Double importoLavori = record.getIMPRIDETLAVORI() == null ? null
							: record.getIMPRIDETLAVORI().doubleValue();
					Double importoServizi = record.getIMPRIDETSERVIZI() == null ? null
							: record.getIMPRIDETSERVIZI().doubleValue();
					Double importoSicurezza = record.getIMPSICUREZZA() == null ? null
							: record.getIMPSICUREZZA().doubleValue();
					Double importoProgettazione = record.getIMPPROGETTAZIONE() == null ? null
							: Double.valueOf(record.getIMPPROGETTAZIONE());
					Double importoNonAssog = record.getULTERIORISOMME() == null ? null
							: record.getULTERIORISOMME().doubleValue();
					Double importoDisposizione = record.getIMPDISPOSIZIONE() == null ? null
							: Double.valueOf(record.getIMPDISPOSIZIONE());

					Double importoSubtotale = 0d;

					if (importoForniture != null) {
						importoSubtotale += importoForniture;
					}

					if (importoLavori != null) {
						importoSubtotale += importoLavori;
					}

					if (importoServizi != null) {
						importoSubtotale += importoServizi;
					}

					Double importoComplessivoAppalto = 0d;

					if (importoSubtotale != null) {
						importoComplessivoAppalto += importoSubtotale;
					}

					if (importoSicurezza != null) {
						importoComplessivoAppalto += importoSicurezza;
					}

					if (importoProgettazione != null) {
						importoComplessivoAppalto += importoProgettazione;
					}

					if (importoNonAssog != null) {
						importoComplessivoAppalto += importoNonAssog;
					}

					Double importoComplessivoIntervento = 0d;

					if (importoComplessivoAppalto != null) {
						importoComplessivoIntervento += importoComplessivoAppalto;
					}

					if (importoDisposizione != null) {
						importoComplessivoIntervento += importoDisposizione;
					}

					insertForm.setImportoRideterminatoForniture(importoForniture);
					insertForm.setImportoRideterminatoLavori(importoLavori);
					insertForm.setImportoRideterminatoServizi(importoServizi);
					insertForm.setImportoSubtotale(importoSubtotale);
					insertForm.setImportoSicurezza(importoSicurezza);
					insertForm.setImportoProgettazione(importoProgettazione);
					insertForm.setImportoNonAssog(importoNonAssog);
					insertForm.setImportoComplAppalto(importoComplessivoAppalto);
					insertForm.setImportoDisposizione(importoDisposizione);
					insertForm.setImportoComplIntervento(importoComplessivoIntervento);

					insertForm.setAltreMotivazioni(record.getALTREMOTIVAZIONI());
					insertForm.setCigNuovaProc(record.getCIGPROCEDURA());
					insertForm.setDataAttoAggiuntivo(record.getDATAATTOAGGIUNTIVO() == null ? null
							: record.getDATAATTOAGGIUNTIVO().toGregorianCalendar().getTime());
					insertForm.setDataVerbaleAppr(record.getDATAVERBAPPR() == null ? null
							: record.getDATAVERBAPPR().toGregorianCalendar().getTime());
					insertForm.setNumGiorniProroga(
							record.getNUMGIORNIPROROGA() == null ? null : record.getNUMGIORNIPROROGA().longValue());

					this.contrattiMapper.insertFaseVariante(insertForm);
					if (variante.getMotivi() != null && variante.getMotivi().size() > 0) {
						long numMoti = 1L;
						for (RecMotivoVarType motivo : variante.getMotivi()) {
							if (motivo.getIDMOTIVOVAR() != null) {
								this.contrattiMapper.insertMotivazioneFaseVariante(codGara, codLotto, num, numMoti,
										new Long(motivo.getIDMOTIVOVAR()));
								numMoti++;
							}
						}
					}
					this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, num,
							record.getIDSCHEDALOCALE(), 1L, record.getIDSCHEDASIMOG());
					num++;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Errore in consulta gara, creazione scheda variante. codgara =" + codGara + " codLotto ="
					+ codLotto, e);
			throw e;
		}

		LOGGER.debug("Execution end::istanziaVarianti");
	}

	private void istanziaSubappalti(final Long codGara, final Long codLotto, final String codUffInt,
			final SchedaCompletaType schedaCompleta, final AggiudicatariType aggiudicatariType) throws Exception {

		LOGGER.debug("Execution start::istanziaSubappalti");

		try {

			SubappaltiType subappalti = schedaCompleta.getDatiSubappalti();
			Long faseEsecuzione = new Long(CostantiW9.SUBAPPALTO);

			if (subappalti != null && subappalti.getSubappalto() != null) {

				Long num = this.contrattiMapper.getMaxNumW9fase(codGara, codLotto, faseEsecuzione);
				if (num == null) {
					num = 1L;
				} else {
					num++;
				}

				for (SubappaltoType subappalto : subappalti.getSubappalto()) {

					FaseSubappaltoInsertForm insertForm = new FaseSubappaltoInsertForm();
					insertForm.setCodGara(codGara);
					insertForm.setCodImpresa(getCodImpresa(subappalto.getCFDITTA(), codUffInt, aggiudicatariType));
					insertForm.setCodImpresaAgg(
							getCodImpresa(subappalto.getCODICEFISCALEAGGIUDICATARIO(), codUffInt, aggiudicatariType));
					insertForm.setCodLotto(codLotto);
					insertForm.setDataAuth(subappalto.getDATAAUTORIZZAZIONE() == null ? null
							: subappalto.getDATAAUTORIZZAZIONE().toGregorianCalendar().getTime());
					insertForm.setIdCpv(subappalto.getIDCPV());
					insertForm.setImportoEffettivo(subappalto.getIMPORTOEFFETTIVO() == null ? null
							: subappalto.getIMPORTOEFFETTIVO().doubleValue());
					insertForm.setImportoPresunto(subappalto.getIMPORTOPRESUNTO() == null ? null
							: subappalto.getIMPORTOPRESUNTO().doubleValue());
					insertForm.setNum(num);
					insertForm.setNumAppa(1L);
					insertForm.setOggetto(subappalto.getOGGETTOSUBAPPALTO());

					if (StringUtils.isNotBlank(subappalto.getIDCATEGORIA())) {
						String categoria = this.contrattiMapper
								.getDatabaseCategoriaByIdCategoria(subappalto.getIDCATEGORIA());

						if (categoria == null) {
							categoria = subappalto.getIDCATEGORIA();
						}
						insertForm.setIdCategoria(categoria);
					}

					this.contrattiMapper.insertFaseSubappalto(insertForm);
					this.contrattiMapper.insertW9fase(codGara, codLotto, faseEsecuzione, num,
							subappalto.getIDSCHEDALOCALE(), 1L, subappalto.getIDSCHEDASIMOG());
					num++;
				}
			}
		} catch (Exception e) {
			LOGGER.error("Errore in consulta gara, creazione scheda subappalto. codgara =" + codGara + " codLotto ="
					+ codLotto, e);
			throw e;
		}

		LOGGER.debug("Execution end::istanziaSubappalti");
	}

	// End Inserts Schede

	// Insert collezioni accessorie

	private void insertPubblicazione(final Long codGara, final Long codLotto, final PubblicazioneType pubblicazione) {

		LOGGER.debug("Execution start::insertPubblicazione");

		FlagSNType profiloCommittente = pubblicazione.getPROFILOCOMMITTENTE();
		FlagSNType sitoInf = pubblicazione.getSITOMINISTEROINFTRASP();
		FlagSNType sitoOsservatorio = pubblicazione.getSITOOSSERVATORIOCP();
		FaseInizPubbEsitoForm pubbEsitoInsertForm = new FaseInizPubbEsitoForm();
		pubbEsitoInsertForm.setCodGara(codGara);
		pubbEsitoInsertForm.setCodLotto(codLotto);
		pubbEsitoInsertForm.setCounter(1L);
		pubbEsitoInsertForm.setDataAlbo(pubblicazione.getDATAALBO() == null ? null
				: pubblicazione.getDATAALBO().toGregorianCalendar().getTime());
		pubbEsitoInsertForm.setDataGuce(pubblicazione.getDATAGUCE() == null ? null
				: pubblicazione.getDATAGUCE().toGregorianCalendar().getTime());
		pubbEsitoInsertForm.setDataGuri(pubblicazione.getDATAGURI() == null ? null
				: pubblicazione.getDATAGURI().toGregorianCalendar().getTime());
		pubbEsitoInsertForm.setNumIniziale(1L);
		pubbEsitoInsertForm.setProfiloCommittente(
				profiloCommittente == null ? null : profiloCommittente.value().equals("N") ? "2" : "1");
		pubbEsitoInsertForm.setQuotidianiNaz(
				pubblicazione.getQUOTIDIANINAZ() == null ? null : pubblicazione.getQUOTIDIANINAZ().longValue());
		pubbEsitoInsertForm.setQuotidianiReg(
				pubblicazione.getQUOTIDIANIREG() == null ? null : pubblicazione.getQUOTIDIANIREG().longValue());
		pubbEsitoInsertForm.setSitoMinInfTrasp(sitoInf == null ? null : sitoInf.value().equals("N") ? "2" : "1");
		pubbEsitoInsertForm.setSitoOsservatorio(
				sitoOsservatorio == null ? null : sitoOsservatorio.value().equals("N") ? "2" : "1");
		this.contrattiMapper.insertFaseInizPubbEsito(pubbEsitoInsertForm);

		LOGGER.debug("Execution end::insertPubblicazione");
	}

	private void insertAggiudicatari(final Long codGara, final Long codLotto, final String codUffInt,
			final AggiudicatariType aggiudicatariType, final List<SoggAggiudicatarioType> aggiudicatari)
			throws Exception {

		LOGGER.debug("Execution start::insertAggiudicatari");

		if (aggiudicatari != null) {
			Long counter = 1L;
			for (SoggAggiudicatarioType aggiudicatario : aggiudicatari) {

				String codImp = getCodImpresa(aggiudicatario.getCODICEFISCALEAGGIUDICATARIO(), codUffInt,
						aggiudicatariType);
				String codImpAus = getCodImpresa(aggiudicatario.getCFAUSILIARIA(), codUffInt, aggiudicatariType);

				ImpresaAggiudicatariaInsertForm impInsertForm = new ImpresaAggiudicatariaInsertForm();
				impInsertForm.setCodGara(codGara);
				impInsertForm.setCodImpAus(codImpAus);
				impInsertForm.setCodImpresa(codImp);
				impInsertForm.setCodLotto(codLotto);
				impInsertForm.setNumAppa(1L);
				impInsertForm.setNumAggi(counter);
				impInsertForm.setFlagAvvallamento(aggiudicatario.getFLAGAVVALIMENTO());
				impInsertForm.setIdGruppo(
						aggiudicatario.getIDGRUPPO() == null ? null : aggiudicatario.getIDGRUPPO().longValue());
				impInsertForm.setImportoAggiudicazione(aggiudicatario.getIMPORTOAGGIUDICAZIONE() == null ? null
						: Double.valueOf(aggiudicatario.getIMPORTOAGGIUDICAZIONE()));
				impInsertForm.setIdTipoAgg(
						aggiudicatario.getIDTIPOAGG() == null ? null : new Long(aggiudicatario.getIDTIPOAGG()));
				impInsertForm.setNumAggi(counter);
				impInsertForm.setNumAppa(1L);
				impInsertForm.setOffertaAumento(aggiudicatario.getPERCOFFAUMENTO() == null ? null
						: Double.valueOf(aggiudicatario.getPERCOFFAUMENTO()));
				impInsertForm.setRibassoAggiudicazione(aggiudicatario.getPERCRIBASSOAGG() == null ? null
						: Double.valueOf(aggiudicatario.getPERCRIBASSOAGG()));
				impInsertForm.setRuolo(aggiudicatario.getRUOLO() == null ? null : new Long(aggiudicatario.getRUOLO()));
				for (AggiudicatarioType agg : aggiudicatariType.getAggiudicatario()) {
					if(agg.getCFRAPPRESENTANTE().equals(aggiudicatario.getCODICEFISCALEAGGIUDICATARIO())){
						impInsertForm.setNomeLegRap(agg.getNOME());
						impInsertForm.setCognomeLegRap(agg.getCOGNOME());
						impInsertForm.setCfLegRap(agg.getCFRAPPRESENTANTE());
					}
				}
				this.contrattiMapper.insertImpresaAggiudicataria(impInsertForm);
				counter++;
			}
		}

		LOGGER.debug("Execution end::insertAggiudicatari");
	}

	private void insertIncarichi(final Long codGara, final Long codLotto, final String codUffInt,
			final List<IncaricatoType> incarichi, final ResponsabiliType responsabiliType) throws Exception {

		LOGGER.debug("Execution start::insertIncarichi");

		if (incarichi != null) {
			Long counter = 1L;
			for (IncaricatoType incarico : incarichi) {

				String cfResponsabile = incarico.getCODICEFISCALERESPONSABILE();
				List<String> codTecList = this.simogMapper.getCodTecByCFAndSA(cfResponsabile, codUffInt);
				String codTec = null;
				if(codTecList != null && codTecList.size() > 0) {
					codTec = codTecList.get(0);
				}
				if (StringUtils.isEmpty(codTec)) {
					InfoUtenteEntry utente = new InfoUtenteEntry();
					codTec = this.calcolaCodificaAutomatica("TECNI", "CODTEC");
					utente.setCodtec(codTec);
					utente.setCgentei(codUffInt);
					utente.setCf(cfResponsabile == null ? null : cfResponsabile.toUpperCase());

					ResponsabileType responsabile = getResponsabileByCf(responsabiliType, cfResponsabile);

					if (responsabile != null) {
						utente.setCognome(responsabile.getCOGNOME());
						utente.setNome(responsabile.getNOME());
						String nominativo = (responsabile.getCOGNOME() + " " + responsabile.getNOME()).trim();
						utente.setNominativo(nominativo);
						utente.setEmail(responsabile.getEMAIL());
						utente.setTelefono(responsabile.getTELEFONO());
						utente.setFax(responsabile.getFAX());
						utente.setIndirizzo(responsabile.getINDIRIZZO());
						utente.setCap(responsabile.getCAP());
						utente.setCodiceistatcomune(responsabile.getCODICEISTATCOMUNE());
					} else {
						utente.setNominativo(cfResponsabile);
					}

					this.simogMapper.insertTecnico(utente);
				}
				IncaricoProfessionaleInsertForm incaInsertForm = new IncaricoProfessionaleInsertForm();
				incaInsertForm.setCigProgEsterna(incarico.getCIGPROGESTERNA());
				incaInsertForm.setCodiceTecnico(codTec);
				incaInsertForm.setDataAffProgEsterna(incarico.getDATAAFFPROGESTERNA() == null ? null
						: incarico.getDATAAFFPROGESTERNA().toGregorianCalendar().getTime());
				incaInsertForm.setDataConsProgEsterna(incarico.getDATACONSPROGESTERNA() == null ? null
						: incarico.getDATACONSPROGESTERNA().toGregorianCalendar().getTime());
				incaInsertForm.setIdRuolo(incarico.getIDRUOLO() == null ? null : new Long(incarico.getIDRUOLO()));
				incaInsertForm.setSezione(incarico.getSEZIONE().value());
				this.contrattiMapper.insertIncaricoProfessionaleForm(codGara, codLotto, 1L, counter, incaInsertForm);
				counter++;
			}
		}

		LOGGER.debug("Execution end::insertIncarichi");
	}

	private void insertFinanziamenti(final Long codGara, final Long codLotto,
			final List<FinanziamentoType> finanziamenti) {

		LOGGER.debug("Execution start::insertFinanziamenti");

		if (finanziamenti != null) {
			Long counter = 1L;
			for (FinanziamentoType finanziamento : finanziamenti) {
				FinanziamentoInsertForm finaInsertForm = new FinanziamentoInsertForm();
				finaInsertForm.setIdFinanziamento(finanziamento.getIDFINANZIAMENTO());
				finaInsertForm.setImportoFinanziamento(finanziamento.getIMPORTOFINANZIAMENTO() == null ? null
						: finanziamento.getIMPORTOFINANZIAMENTO().doubleValue());
				this.contrattiMapper.insertFinanziamentoForm(codGara, codLotto, 1L, counter, finaInsertForm);
				counter++;
			}
		}

		LOGGER.debug("Execution end::insertFinanziamenti");
	}

	private void insertCondizioniRicorso(final Long codGara, final Long codLotto, final String codUffInt,
			final List<CondizioneType> condizioni) {

		LOGGER.debug("Execution start::insertCondizioniRicorso");

		if (condizioni != null && condizioni.size() > 0) {
			for (CondizioneType condizione : condizioni) {
				String idCondizione = condizione.getIDCONDIZIONE();
				if (StringUtils.isNotBlank(idCondizione) && NumberUtils.isCreatable(idCondizione)) {
					this.contrattiMapper.insertCondizioniRicorsoLotto(codGara, codLotto, new Long(idCondizione));
				}
			}
		}

		LOGGER.debug("Execution end::insertCondizioniRicorso");

	}

	// End Insert collezioni accessorie

	protected String getCodImpresa(final String cfImpresa, final String stazioneAppaltante,
			final AggiudicatariType aggiudicatari) throws Exception {

		if (cfImpresa == null) {
			return null;
		}

		List<String> codImpList = this.simogMapper.findImpresa(cfImpresa, stazioneAppaltante);
		String codImp = null;
		if(codImpList != null && codImpList.size() > 0) {
			codImp = codImpList.get(0);
		}
		if (codImp == null) {

			String newCodImp = calcolaCodificaAutomatica("IMPR", "CODIMP");
			String ragioneSocialeImpresa = cfImpresa;
			String nome = "";
			String cognome = "";
			String cf = cfImpresa;
			String nominativo = "Legale rapprsentante impresa " + cfImpresa;

			if (aggiudicatari != null && aggiudicatari.getAggiudicatario() != null
					&& aggiudicatari.getAggiudicatario().size() > 0) {

				for (AggiudicatarioType aggiudicatario : aggiudicatari.getAggiudicatario()) {

					if (cfImpresa.equals(aggiudicatario.getCODICEFISCALEAGGIUDICATARIO())) {
						ragioneSocialeImpresa = aggiudicatario.getDENOMINAZIONE();
						nome = aggiudicatario.getNOME();
						cognome = aggiudicatario.getCOGNOME();
						cf = aggiudicatario.getCFRAPPRESENTANTE();
						nominativo = nome + " " + cognome;
					}
				}
			}

			ImpresaEntry impresa = new ImpresaEntry();
			impresa.setCodiceImpresa(newCodImp);
			impresa.setCodiceFiscale(cfImpresa);
			impresa.setRagioneSociale(ragioneSocialeImpresa);
			impresa.setStazioneAppaltante(stazioneAppaltante);
			impresa = this.setImpresaNomImp(impresa);
			this.contrattiMapper.insertImpresa(impresa);
			this.contrattiMapper.insertTeim(newCodImp, nome, cognome, cf);
			Long maxId = wgcManager.getNextId("IMPLEG");
			this.contrattiMapper.insertImpleg(maxId, impresa.getCodiceImpresa(), nominativo);
			return newCodImp;
		} else {
			return codImp;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public String calcolaCodificaAutomatica(final String entita, final String campoChiave) throws Exception {

		String codice = "1";
		String formatoCodice = null;
		String codcal = null;
		Long cont = null;
		try {
			String query = "select CODCAL, CONTAT from G_CONFCOD where NOMENT = '" + entita + "'";
			List<Map<String, Object>> confcod = sqlMapper.select(query);
			if (confcod != null && confcod.size() > 0) {
				for (Map<String, Object> row : confcod) {
					if (row.containsKey("CODCAL")) {
						codcal = row.get("CODCAL").toString();
					} else {
						codcal = row.get("codcal").toString();
					}
					if (row.containsKey("CONTAT")) {
						cont = new Long(row.get("CONTAT").toString());
					} else {
						cont = new Long(row.get("contat").toString());
					}
					break;
				}
				boolean codiceUnivoco = false;
				int numeroTentativi = 0;
				StringBuffer strBuffer = null;
				long tmpContatore = cont.longValue();
				while (!codiceUnivoco && numeroTentativi < 10) {
					strBuffer = new StringBuffer("");
					// Come prima cosa eseguo l'update del contatore
					tmpContatore++;
					sqlMapper.execute(
							"update G_CONFCOD set contat = " + tmpContatore + " where NOMENT = '" + entita + "'");

					strBuffer = new StringBuffer("");
					formatoCodice = codcal;
					while (formatoCodice.length() > 0) {
						switch (formatoCodice.charAt(0)) {
						case '<': // Si tratta di un'espressione numerica
							String strNum = formatoCodice.substring(1, formatoCodice.indexOf('>'));
							if (strNum.charAt(0) == '0') {
								// Giustificato a destra
								for (int i = 0; i < (strNum.length() - String.valueOf(tmpContatore).length()); i++)
									strBuffer.append('0');
							}
							strBuffer.append(String.valueOf(tmpContatore));

							formatoCodice = formatoCodice.substring(formatoCodice.indexOf('>') + 1);
							break;
						case '"': // Si tratta di una parte costante
							strBuffer.append(formatoCodice.substring(1, formatoCodice.indexOf('"', 1)));
							formatoCodice = formatoCodice.substring(formatoCodice.indexOf('"', 1) + 1);
							break;
						}
					}
					int occorrenze = sqlMapper
							.count(entita + " WHERE " + campoChiave + " ='" + strBuffer.toString() + "'");
					if (occorrenze == 0) {
						codiceUnivoco = true;
						codice = strBuffer.toString();
					} else {
						numeroTentativi++;
					}
				}
				if (!codiceUnivoco) {
					LOGGER.error("numeroTentativi esaurito durante il calcolo per la codifica automatica " + entita);
					throw new Exception(
							"numeroTentativi esaurito durante il calcolo per la codifica automatica " + entita);
				}
			}
		} catch (Exception ex) {
			LOGGER.error("Errore inaspettato durante il calcolo per la codifica automatica " + entita, ex);
			throw new Exception(ex);
		}
		return codice;
	}

	public SchedaCompletaType getSchedaCompleta(final SchedaType schedaType) {

		SchedaCompletaType schedaCompleta = null;
		if (schedaType.getDatiScheda() != null && schedaType.getDatiScheda().getSchedaCompleta() != null
				&& schedaType.getDatiScheda().getSchedaCompleta().size() > 0 && schedaType.getDatiScheda()
						.getSchedaCompleta().get(schedaType.getDatiScheda().getSchedaCompleta().size() - 1) != null) {
			schedaCompleta = schedaType.getDatiScheda().getSchedaCompleta()
					.get(schedaType.getDatiScheda().getSchedaCompleta().size() - 1);
		}
		return schedaCompleta;
	}

	public ResponsabileType getResponsabileByCf(final ResponsabiliType responsabiliType, final String cf) {

		if (responsabiliType == null || StringUtils.isBlank(cf))
			return null;

		List<ResponsabileType> listaResponsabili = responsabiliType.getResponsabile();

		if (listaResponsabili == null || listaResponsabili.size() == 0)
			return null;

		ResponsabileType responsabile = listaResponsabili.stream()
				.filter(r -> cf.toUpperCase().equals(r.getCODICEFISCALERESPONSABILE().toUpperCase())).findAny()
				.orElse(null);
		return responsabile;
	}

	private ImpresaEntry setImpresaNomImp(ImpresaEntry impresa) {
		if (impresa.getNomImp() == null && StringUtils.isNotBlank(impresa.getRagioneSociale())) {
			String nomImp = impresa.getRagioneSociale().length() > 61 ? impresa.getRagioneSociale().substring(0, 61)
					: impresa.getRagioneSociale();
			impresa.setNomImp(nomImp);
		}
		return impresa;
	}

}
