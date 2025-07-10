package it.appaltiecontratti.pubblprogrammi.bl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.appaltiecontratti.pubblprogrammi.entity.AcquistoEntry;
import it.appaltiecontratti.pubblprogrammi.entity.AcquistoNonRipropostoEntry;
import it.appaltiecontratti.pubblprogrammi.entity.AllegatoEntry;
import it.appaltiecontratti.pubblprogrammi.entity.FlussoEntry;
import it.appaltiecontratti.pubblprogrammi.entity.ImmobileEntry;
import it.appaltiecontratti.pubblprogrammi.entity.InterventoEntry;
import it.appaltiecontratti.pubblprogrammi.entity.InterventoNonRipropostoEntry;
import it.appaltiecontratti.pubblprogrammi.entity.OperaIncompiutaEntry;
import it.appaltiecontratti.pubblprogrammi.entity.PubblicaProgrammaFornitureServiziEntry;
import it.appaltiecontratti.pubblprogrammi.entity.PubblicaProgrammaLavoriEntry;
import it.appaltiecontratti.pubblprogrammi.entity.PubblicazioneResult;
import it.appaltiecontratti.pubblprogrammi.entity.ResponseResult;
import it.appaltiecontratti.pubblprogrammi.mapper.ProgrammiMapper;
import it.appaltiecontratti.pubblprogrammi.mapper.SqlMapper;
import it.appaltiecontratti.pubblprogrammi.mapper.TecniciMapper;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@Component(value = "programmiManager")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ProgrammiManager {

	public static final String APPLICATION_CODE = "W9";	
	public static final String URL_PUBBLICAZIONE = "it.eldasoft.pubblicazione.programmi.url";
	
	/** Logger di classe. */
	private static Logger logger = LogManager.getLogger(ProgrammiManager.class);

	/**
	 * Dao MyBatis per le operazioni nelle entit� Avviso.
	 */
	@Autowired
	private ProgrammiMapper programmiMapper;

	/**
	 * Dao MyBatis con le primitive di estrazione dei dati.
	 */
	@Autowired
	private SqlMapper sqlMapper;

	/**
	 * Dao MyBatis per le operazioni nelle entit� Tecnici.
	 */
	@Autowired
	private TecniciMapper tecniciMapper;

	/**
	 * @param avvisiMapper avvisiMapper da settare internamente alla classe.
	 */

	@Autowired
	DataSource dataSource;
	
	@Autowired
	protected WGenChiaviManager wgcManager;

	public void setAvvisiMapper(ProgrammiMapper programmiMapper) {
		this.programmiMapper = programmiMapper;
	}

	/**
	 * @param sqlMapper sqlMapper da settare internamente alla classe.
	 */
	public void setSqlMapper(SqlMapper sqlMapper) {
		this.sqlMapper = sqlMapper;
	}

	/**
	 * @param tecniciMapper tecniciMapper da settare internamente alla classe.
	 */
	public void setTecniciMapper(TecniciMapper tecniciMapper) {
		this.tecniciMapper = tecniciMapper;
	}

	/**
	 * pubblica un programma nel DB
	 * 
	 * @param programma programma lavori
	 * @return risultato dell'operazione di pubblicazione del programma
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PubblicazioneResult pubblicaLavori(PubblicaProgrammaLavoriEntry programma) throws Exception {
		PubblicazioneResult risultato = new PubblicazioneResult();
		// ricavo il codice della Stazione appaltante, se non esiste la creo
		String codiceSA = this.sqlMapper.executeReturnString(
				"SELECT MAX(CODEIN) FROM UFFINT WHERE CFEIN='" + programma.getCodiceFiscaleSA() + "'");
		programma.setCodiceSA(codiceSA);
		// ricavo il codice del referente, se non esiste lo creo
		String codiceReferente = this.sqlMapper.executeReturnString("SELECT MAX(CODTEC) FROM TECNI WHERE CFTEC='"
				+ programma.getReferente().getCfPiva() + "' AND CGENTEI = '" + programma.getCodiceSA() + "'");
		if (codiceReferente == null) {
			// il rup non esiste lo creo
			codiceReferente = this.calcolaCodificaAutomatica("TECNI", "CODTEC");
			programma.getReferente().setCodice(codiceReferente);
			programma.getReferente().setCodiceSA(codiceSA);
			if(programma.getReferente().getCfPiva() != null) {
				programma.getReferente().setCfPiva(programma.getReferente().getCfPiva().toUpperCase());
			}
			this.tecniciMapper.insertTecnico(programma.getReferente());
		}
		// ricavo l'id ufficio
		if (programma.getUfficio() != null) {
			String idUfficio = this.sqlMapper.executeReturnString("SELECT MAX(ID) FROM UFFICI WHERE CODEIN='" + codiceSA
					+ "' and DENOM='" + programma.getUfficio().replaceAll("'", "''") + "'");
			if (idUfficio == null) {
				// l'ufficio non esiste, lo creo
				// ricavo l'id dell'ufficio
				Integer i = this.sqlMapper.execute("SELECT MAX(ID) FROM UFFICI");
				Long id = new Long(1);
				if (i != null) {
					id = new Long(i) + 1;
				}
				idUfficio = id.toString();
				this.programmiMapper.insertUfficio(id, codiceSA, programma.getUfficio());
			}
			programma.setIdUfficio(new Long(idUfficio));
		}
		programma.setCodiceReferente(codiceReferente);
		Long tipoInvio = new Long(1);
		if (programma.getIdRicevuto() == null) {
			// inserisco un nuovo programma
			// ricavo l'id del programma per la stazione applatante
			Integer i = this.sqlMapper.execute("SELECT MAX(CONTRI) FROM PIATRI");
			Long contri = new Long(1);
			if (i != null) {
				contri = new Long(i) + 1;
			}
			programma.setContri(contri);
			// ricavo l'id univoco della pubblicazione
			
			Long idRicevuto = this.wgcManager.getNextId("W9PUBBLICAZIONI_GEN");
			risultato.setId(idRicevuto);
			programma.setIdRicevuto(idRicevuto);
			this.programmiMapper.pubblicaProgrammaLavori(programma);
		} else {
			tipoInvio = new Long(2);
			// aggiorno il programma
			// ricavo l'id del programma
			Integer i = this.sqlMapper
					.execute("SELECT CONTRI FROM PIATRI WHERE ID_GENERATO=" + programma.getIdRicevuto());
			programma.setContri(new Long(i));
			this.programmiMapper.modificaPubblicazioneProgrammaLavori(programma);
			risultato.setId(programma.getIdRicevuto());
			// cancello le eventuali tabelle collegate
			this.sqlMapper.execute("DELETE FROM OITRI WHERE CONTRI = " + programma.getContri());
			this.sqlMapper.execute("DELETE FROM INTTRI WHERE CONTRI = " + programma.getContri());
			this.sqlMapper.execute("DELETE FROM IMMTRAI WHERE CONTRI = " + programma.getContri());
			this.sqlMapper.execute("DELETE FROM INRTRI WHERE CONTRI = " + programma.getContri());
		}
		// inserisco le opere incompiute
		int numoi = 1;
		int numimm = 1;
		if (programma.getOpereIncompiute() != null) {
			for (OperaIncompiutaEntry opera : programma.getOpereIncompiute()) {
				opera.setContri(programma.getContri());
				opera.setNumoi(new Long(numoi));
				this.programmiMapper.insertOperaIncompiuta(opera);
				// Inserimento Altri Dati
				if (opera.getAltriDati() != null) {
					opera.getAltriDati().setContri(programma.getContri());
					opera.getAltriDati().setNumoi(new Long(numoi));
					this.programmiMapper.insertAltriDatiOperaIncompiuta(opera.getAltriDati());
				}
				// inserisco gli immobili per l'opera incompiuta
				if (opera.getImmobili() != null) {
					for (ImmobileEntry immobile : opera.getImmobili()) {
						immobile.setContri(opera.getContri());
						immobile.setConint(new Long(0));
						immobile.setNumoi(opera.getNumoi());
						immobile.setNumimm(new Long(numimm));
						this.programmiMapper.insertImmobile(immobile);
						numimm++;
					}
				}
				numoi++;
			}
		}

		// inserisco gli interventi
		int conint = 1;
		if (programma.getInterventi() != null) {
			for (InterventoEntry intervento : programma.getInterventi()) {
				intervento.setContri(programma.getContri());
				intervento.setConint(new Long(conint));
				// ricavo il codice del rup, se non esiste lo creo
				if (intervento.getRup() != null) {
					String codiceRup = this.sqlMapper.executeReturnString("SELECT MAX(CODTEC) FROM TECNI WHERE CFTEC='"
							+ intervento.getRup().getCfPiva() + "' AND CGENTEI = '" + programma.getCodiceSA() + "'");
					if (codiceRup == null) {
						// il rup non esiste lo creo
						codiceRup = this.calcolaCodificaAutomatica("TECNI", "CODTEC"); // Autogenero un codice per il
						intervento.getRup().setCodiceSA(codiceSA);														// tecnico
						intervento.getRup().setCodice(codiceRup);
						if(intervento.getRup().getCfPiva() != null) {
							intervento.getRup().setCfPiva(intervento.getRup().getCfPiva().toUpperCase());
						}
						this.tecniciMapper.insertTecnico(intervento.getRup());
					}
					intervento.setCodiceRup(codiceRup);
				}
				this.programmiMapper.insertIntervento(intervento);
				// inserisco gli immobili per l'opera incompiuta
				numimm = 1;
				if (intervento.getImmobili() != null) {
					for (ImmobileEntry immobile : intervento.getImmobili()) {
						immobile.setContri(intervento.getContri());
						immobile.setConint(intervento.getConint());
						immobile.setNumimm(new Long(numimm));
						this.programmiMapper.insertImmobile(immobile);
						numimm++;
					}
				}
				conint++;
			}
		}
		// inserisco gli interventi non riproposti
		conint = 1;
		if (programma.getInterventiNonRiproposti() != null) {
			for (InterventoNonRipropostoEntry interventoNonRiproposto : programma.getInterventiNonRiproposti()) {
				interventoNonRiproposto.setContri(programma.getContri());
				interventoNonRiproposto.setConint(new Long(conint));
				this.programmiMapper.insertInterventoNonRiproposto(interventoNonRiproposto);
				conint++;
			}
		}
		// inserisco flusso
		FlussoEntry flusso = new FlussoEntry();
		Long idFlusso = this.wgcManager.getNextId("W9FLUSSI");
		Long key03 = new Long(982);
		flusso.setId(idFlusso);
		flusso.setArea(new Long(4));
		flusso.setKey01(programma.getContri());
		flusso.setKey03(key03);
		flusso.setTipoInvio(tipoInvio);
		flusso.setDataInvio(new Date());
		flusso.setCodiceFiscaleSA(programma.getCodiceFiscaleSA());
		flusso.setOggetto(programma.getContri()+"");
		flusso.setAutore(programma.getAutore());
		ObjectMapper mapper = new ObjectMapper();
		
		flusso.setJson(mapper.writeValueAsString(programma));
		flusso.setIdComunicazione(this.insertInboxOutbox(flusso));
		this.programmiMapper.insertFlusso(flusso);
		return risultato;
	}

	/**
	 * pubblica un programma nel DB
	 * 
	 * @param programma programma forniture e servizi
	 * @return risultato dell'operazione di pubblicazione del programma
	 * 
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public PubblicazioneResult pubblicaFornitureServizi(PubblicaProgrammaFornitureServiziEntry programma)
			throws Exception {
		PubblicazioneResult risultato = new PubblicazioneResult();
		// ricavo il codice della Stazione appaltante, se non esiste la creo
		String codiceSA = this.sqlMapper.executeReturnString(
				"SELECT MAX(CODEIN) FROM UFFINT WHERE CFEIN='" + programma.getCodiceFiscaleSA() + "'");
		programma.setCodiceSA(codiceSA);
		// ricavo il codice del referente, se non esiste lo creo
		String codiceReferente = this.sqlMapper.executeReturnString("SELECT MAX(CODTEC) FROM TECNI WHERE CFTEC='"
				+ programma.getReferente().getCfPiva() + "' AND CGENTEI = '" + programma.getCodiceSA() + "'");
		if (codiceReferente == null) {
			// il rup non esiste lo creo
			codiceReferente = this.calcolaCodificaAutomatica("TECNI", "CODTEC"); // Autogenero un codice per il tecnico
			programma.getReferente().setCodice(codiceReferente);
			programma.getReferente().setCodiceSA(codiceSA);
			if(programma.getReferente().getCfPiva() != null) {
				programma.getReferente().setCfPiva(programma.getReferente().getCfPiva().toUpperCase());
			}
			this.tecniciMapper.insertTecnico(programma.getReferente());
		}
		// ricavo l'id ufficio
		if (programma.getUfficio() != null) {
			String idUfficio = this.sqlMapper.executeReturnString("SELECT MAX(ID) FROM UFFICI WHERE CODEIN='" + codiceSA
					+ "' and DENOM='" + programma.getUfficio().replaceAll("'", "''") + "'");
			if (idUfficio == null) {
				// l'ufficio non esiste, lo creo
				// ricavo l'id dell'ufficio
				Integer i = this.sqlMapper.execute("SELECT MAX(ID) FROM UFFICI");
				Long id = new Long(1);
				if (i != null) {
					id = new Long(i) + 1;
				}
				idUfficio = id.toString();
				this.programmiMapper.insertUfficio(id, codiceSA, programma.getUfficio());
			}
			programma.setIdUfficio(new Long(idUfficio));
		}
		programma.setCodiceReferente(codiceReferente);
		Long tipoInvio = new Long(1);
		if (programma.getIdRicevuto() == null) {
			// inserisco un nuovo programma
			// ricavo l'id del programma per la stazione applatante
			Integer i = this.sqlMapper.execute("SELECT MAX(CONTRI) FROM PIATRI");
			Long contri = new Long(1);
			if (i != null) {
				contri = new Long(i) + 1;
			}
			programma.setContri(contri);
			// ricavo l'id univoco della pubblicazione
			
			Long idRicevuto = this.wgcManager.getNextId("W9PUBBLICAZIONI_GEN");
			risultato.setId(idRicevuto);
			programma.setIdRicevuto(idRicevuto);
			this.programmiMapper.pubblicaProgrammaFornitureServizi(programma);
		} else {
			tipoInvio = new Long(2);
			// aggiorno il programma
			// ricavo l'id del programma
			Integer i = this.sqlMapper
					.execute("SELECT CONTRI FROM PIATRI WHERE ID_GENERATO=" + programma.getIdRicevuto());
			programma.setContri(new Long(i));
			this.programmiMapper.modificaPubblicazioneProgrammaFornitureServizi(programma);
			risultato.setId(programma.getIdRicevuto());
			// cancello le eventuali tabelle collegate
			this.sqlMapper.execute("DELETE FROM INTTRI WHERE CONTRI = " + programma.getContri());
			this.sqlMapper.execute("DELETE FROM INRTRI WHERE CONTRI = " + programma.getContri());
		}

		// inserisco gli acquisti
		int conint = 1;
		if (programma.getAcquisti() != null) {
			for (AcquistoEntry acquisto : programma.getAcquisti()) {
				acquisto.setContri(programma.getContri());
				acquisto.setConint(new Long(conint));
				// ricavo il codice del rup, se non esiste lo creo
				if (acquisto.getRup() != null) {
					String codiceRup = this.sqlMapper.executeReturnString("SELECT MAX(CODTEC) FROM TECNI WHERE CFTEC='"
							+ acquisto.getRup().getCfPiva() + "' AND CGENTEI = '" + programma.getCodiceSA() + "'");
					if (codiceRup == null) {
						// il rup non esiste lo creo
						codiceRup = this.calcolaCodificaAutomatica("TECNI", "CODTEC"); // Autogenero un codice per il
																						// tecnico
						acquisto.getRup().setCodice(codiceRup);
						acquisto.getRup().setCodiceSA(codiceSA);
						if(acquisto.getRup().getCfPiva() != null) {
							acquisto.getRup().setCfPiva(acquisto.getRup().getCfPiva().toUpperCase());
						}
						this.tecniciMapper.insertTecnico(acquisto.getRup());
					}
					acquisto.setCodiceRup(codiceRup);
				}
				this.programmiMapper.insertAcquisto(acquisto);
				conint++;
			}
		}

		// inserisco gli acquisti non riproposti
		conint = 1;
		if (programma.getAcquistiNonRiproposti() != null) {
			for (AcquistoNonRipropostoEntry acquistoNonRiproposto : programma.getAcquistiNonRiproposti()) {
				acquistoNonRiproposto.setContri(programma.getContri());
				acquistoNonRiproposto.setConint(new Long(conint));
				this.programmiMapper.insertAcquistoNonRiproposto(acquistoNonRiproposto);
				conint++;
			}
		}

		// inserisco flusso
		FlussoEntry flusso = new FlussoEntry();
		Long idFlusso = this.wgcManager.getNextId("W9FLUSSI");
		flusso.setId(idFlusso);
		Long key03 = new Long(981);
		flusso.setId(idFlusso);
		flusso.setArea(new Long(4));
		flusso.setKey01(programma.getContri());
		flusso.setKey03(key03);
		flusso.setTipoInvio(tipoInvio);
		flusso.setDataInvio(new Date());
		flusso.setCodiceFiscaleSA(programma.getCodiceFiscaleSA());
		flusso.setOggetto(programma.getContri()+"");
		ObjectMapper mapper = new ObjectMapper();
		flusso.setJson(mapper.writeValueAsString(programma));
		flusso.setIdComunicazione(this.insertInboxOutbox(flusso));
		this.programmiMapper.insertFlusso(flusso);
		return risultato;
	}

	public String calcolaCodificaAutomatica(String entita, String campoChiave) throws Exception {
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
				while (!codiceUnivoco && numeroTentativi < 5) {
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
					logger.error("numeroTentativi esaurito durante il calcolo per la codifica automatica " + entita);
					throw new Exception(
							"numeroTentativi esaurito durante il calcolo per la codifica automatica " + entita);
				}
			}
		} catch (Exception ex) {
			logger.error("Errore inaspettato durante il calcolo per la codifica automatica " + entita, ex);
			throw new Exception(ex);
		}
		return codice;
	}

	public void AggiornaImportiProgramma(Long contri, Long tipoProgramma) {
		try {
			String updateImportiInterventiLavoriDM112011 = "update inttri set di1int = COALESCE(DV1TRI,0) + COALESCE(IM1TRI,0) + COALESCE(MU1TRI,0) + COALESCE(AL1TRI,0) + COALESCE(AP1TRI,0) + COALESCE(BI1TRI,0),"
					+ " di2int = COALESCE(DV2TRI,0) + COALESCE(IM2TRI,0) + COALESCE(MU2TRI,0) + COALESCE(AL2TRI,0) + COALESCE(AP2TRI,0) + COALESCE(BI2TRI,0),"
					+ " di3int = COALESCE(DV3TRI,0) + COALESCE(IM3TRI,0) + COALESCE(MU3TRI,0) + COALESCE(AL3TRI,0) + COALESCE(AP3TRI,0) + COALESCE(BI3TRI,0),"
					+ " ICPINT = COALESCE(PR1TRI,0) + COALESCE(PR2TRI,0) + COALESCE(PR3TRI,0)" + " WHERE CONTRI = "
					+ contri + " and TIPINT=1";
			String updateImportiLavoriDM112011_2 = "update inttri set DITINT = COALESCE(di1int,0) + COALESCE(di2int,0) + COALESCE(di3int,0) WHERE CONTRI = "
					+ contri + " and TIPINT=1";
			String updateImportiLavoriDM112011_3 = "update inttri set TOTINT = COALESCE(DITINT,0) + COALESCE(ICPINT,0) WHERE CONTRI = "
					+ contri + " and TIPINT=1";
			String updateImportiInterventiFSDM112011 = "update inttri set di1int = COALESCE(AL1TRI,0) + COALESCE(BI1TRI,0) + COALESCE(RG1TRI,0) + COALESCE(IMPRFS,0) + COALESCE(MU1TRI,0) + COALESCE(PR1TRI,0) WHERE CONTRI = "
					+ contri + " and TIPINT=2";
			String updateImportiInterventi = "update inttri set di1int = COALESCE(BI1TRI,0) + COALESCE(DV1TRI,0) + COALESCE(IM1TRI,0) + COALESCE(MU1TRI,0) + COALESCE(AL1TRI,0) + COALESCE(AP1TRI,0),"
					+ " di2int = COALESCE(BI2TRI,0) + COALESCE(DV2TRI,0) + COALESCE(IM2TRI,0) + COALESCE(MU2TRI,0) + COALESCE(AL2TRI,0) + COALESCE(AP2TRI,0),"
					+ " di3int = COALESCE(BI3TRI,0) + COALESCE(DV3TRI,0) + COALESCE(IM3TRI,0) + COALESCE(MU3TRI,0) + COALESCE(AL3TRI,0) + COALESCE(AP3TRI,0),"
					+ " di9int = COALESCE(BI9TRI,0) + COALESCE(DV9TRI,0) + COALESCE(IM9TRI,0) + COALESCE(MU9TRI,0) + COALESCE(AL9TRI,0) + COALESCE(AP9TRI,0),"
					+ " ICPINT = COALESCE(PR1TRI,0) + COALESCE(PR2TRI,0) + COALESCE(PR3TRI,0) + COALESCE(PR9TRI,0)"
					+ " WHERE CONTRI = " + contri;

			String updateImportiInterventi_2 = "update inttri set DITINT = COALESCE(di1int,0) + COALESCE(di2int,0) + COALESCE(di3int,0) + COALESCE(di9int,0) WHERE CONTRI = "
					+ contri;
			String updateImportiInterventi_3 = "update inttri set TOTINT = COALESCE(DITINT,0) + COALESCE(ICPINT,0) + COALESCE(SPESESOST,0) WHERE CONTRI = "
					+ contri;
			String updateImportiImmobili = "update immtrai set VALIMM = COALESCE(VA1IMM,0) + COALESCE(VA2IMM,0) + COALESCE(VA3IMM,0) + COALESCE(VA9IMM,0) WHERE CONTRI = "
					+ contri;

			switch (tipoProgramma.intValue()) {
			case 3:
				// Aggiorno importi interventi programma DM 11/2011
				this.sqlMapper.execute(updateImportiInterventiLavoriDM112011);
				this.sqlMapper.execute(updateImportiLavoriDM112011_2);
				this.sqlMapper.execute(updateImportiLavoriDM112011_3);
				this.sqlMapper.execute(updateImportiInterventiFSDM112011);
				break;
			default:
				this.sqlMapper.execute(updateImportiInterventi);
				this.sqlMapper.execute(updateImportiInterventi_2);
				this.sqlMapper.execute(updateImportiInterventi_3);
				this.sqlMapper.execute(updateImportiImmobili);
				break;
			}

			String updateImportiProgramma = "UPDATE PIATRI SET " + "DV1TRI ="
					+ sommaImportiIntervento("DV1TRI", contri, tipoProgramma) + "," + "DV2TRI ="
					+ sommaImportiIntervento("DV2TRI", contri, tipoProgramma) + "," + "DV3TRI ="
					+ sommaImportiIntervento("DV3TRI", contri, tipoProgramma) + "," + "IM1TRI ="
					+ sommaImportiIntervento("IM1TRI", contri, tipoProgramma) + "," + "IM2TRI ="
					+ sommaImportiIntervento("IM2TRI", contri, tipoProgramma) + "," + "IM3TRI ="
					+ sommaImportiIntervento("IM3TRI", contri, tipoProgramma) + "," + "MU1TRI ="
					+ sommaImportiIntervento("MU1TRI", contri, tipoProgramma) + "," + "MU2TRI ="
					+ sommaImportiIntervento("MU2TRI", contri, tipoProgramma) + "," + "MU3TRI ="
					+ sommaImportiIntervento("MU3TRI", contri, tipoProgramma) + "," + "PR1TRI ="
					+ sommaImportiIntervento("PR1TRI", contri, tipoProgramma) + "," + "PR2TRI ="
					+ sommaImportiIntervento("PR2TRI", contri, tipoProgramma) + "," + "PR3TRI ="
					+ sommaImportiIntervento("PR3TRI", contri, tipoProgramma) + "," + "AL1TRI ="
					+ sommaImportiIntervento("AL1TRI", contri, tipoProgramma) + "," + "AL2TRI ="
					+ sommaImportiIntervento("AL2TRI", contri, tipoProgramma) + "," + "AL3TRI ="
					+ sommaImportiIntervento("AL3TRI", contri, tipoProgramma) + "," + "AP1TRI ="
					+ sommaImportiIntervento("AP1TRI", contri, tipoProgramma) + "," + "AP2TRI ="
					+ sommaImportiIntervento("AP2TRI", contri, tipoProgramma) + "," + "AP3TRI ="
					+ sommaImportiIntervento("AP3TRI", contri, tipoProgramma) + "," + "BI1TRI ="
					+ sommaImportiIntervento("BI1TRI", contri, tipoProgramma) + "," + "BI2TRI ="
					+ sommaImportiIntervento("BI2TRI", contri, tipoProgramma) + "," + "BI3TRI ="
					+ sommaImportiIntervento("BI3TRI", contri, tipoProgramma) + "," + "TO1TRI ="
					+ sommaImportiIntervento("DI1INT", contri, tipoProgramma) + "," + "TO2TRI ="
					+ sommaImportiIntervento("DI2INT", contri, tipoProgramma) + "," + "TO3TRI ="
					+ sommaImportiIntervento("DI3INT", contri, tipoProgramma) + "," + "RG1TRI ="
					+ sommaImportiIntervento("RG1TRI", contri, tipoProgramma) + "," + "IMPRFS ="
					+ sommaImportiIntervento("IMPRFS", contri, tipoProgramma) + " WHERE CONTRI = " + contri;
			this.sqlMapper.execute(updateImportiProgramma);

		} catch (Exception e) {
			logger.error("Errore durante l'aggiornamento degli importi del programma", e);
		}

	}

	/**
	 *
	 * @param campo  campo
	 * @param contri codice programma
	 * @param tiprog tipo programma
	 * @return importo
	 * @throws GestoreException GestoreException
	 */
	private Double sommaImportiIntervento(final String campo, final Long contri, final Long tiprog) throws Exception {
		String sqlSelectSomma = "select SUM(" + campo + ") from INTTRI where CONTRI=" + contri
				+ " and (ACQALTINT is null or ACQALTINT =1)";
		if (tiprog.equals(new Long(3))) {
			sqlSelectSomma += " AND TIPINT=1";
		}
		Double somma;
		try {
			somma = this.sqlMapper.executeReturnDouble(sqlSelectSomma);
		} catch (Exception e) {
			somma = new Double(0);
			throw new Exception(e);
		}
		return somma;
	}

	public void creaPdfNuovaNormativa(String piatriID, Long contri, Long tiprog, Long idRicevuto,
			ServletContext request) throws Exception {

		logger.info("creaPdfNuovaNormativa");
		Connection jrConnection = null;
		try {
			Long norma = programmiMapper.getProgrammaNormaById(piatriID);
			String PROP_JRREPORT_SOURCE_DIR = "jrReport/NuovaNormativa/";
			String jrReportSourceDir = null;
			// Input stream del file sorgente
			InputStream inputStreamJrReport = null;			
			if (tiprog.equals(new Long(1))) {
				if (norma == 3L) {
					PROP_JRREPORT_SOURCE_DIR = "jrReport/NuovaNormativa/norma3/";
				}
				jrReportSourceDir = PROP_JRREPORT_SOURCE_DIR;
				Resource resource = new ClassPathResource(PROP_JRREPORT_SOURCE_DIR + "allegatoI.jasper");
				inputStreamJrReport = resource.getInputStream();
			} else {
				if (norma == 3L) {
					PROP_JRREPORT_SOURCE_DIR = "jrReport/NuovaNormativa/norma3/";
				}
				jrReportSourceDir = PROP_JRREPORT_SOURCE_DIR;
				Resource resource = new ClassPathResource(PROP_JRREPORT_SOURCE_DIR + "allegatoII.jasper");
				inputStreamJrReport = resource.getInputStream();
			}
			// Parametri
			HashMap<String, Object> jrParameters = new HashMap<String, Object>();
			jrParameters.put(JRParameter.REPORT_LOCALE, Locale.ITALIAN);
			jrParameters.put("SUBREPORT_DIR", jrReportSourceDir);
			jrParameters.put("COD_PIANOTRIEN", piatriID);
			// Connessione
			jrConnection = dataSource.getConnection();
			// Stampa del formulario
			JasperPrint jrPrint = JasperFillManager.fillReport(inputStreamJrReport, jrParameters, jrConnection);
			// Output stream del risultato
			ByteArrayOutputStream baosJrReport = new ByteArrayOutputStream();
			JRExporter jrExporter = new JRPdfExporter();
			jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jrPrint);
			jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baosJrReport);
			jrExporter.exportReport();

			inputStreamJrReport.close();
			baosJrReport.close();

			// Update di PIATRI per l'inserimento del file composto
			AllegatoEntry documento = new AllegatoEntry();
			documento.setFile(baosJrReport.toByteArray());
			documento.setNrDoc(idRicevuto);
			this.programmiMapper.setPdf(documento);
			logger.info("creaPdfNuovaNormativa terminato con successo");

		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			try {
				if (jrConnection != null) {
					jrConnection.close();
				}
			} catch (SQLException e) {
				logger.error("Errore durante la chiusura della transazione durante la creazione del pdf ", e);
			}
		}
	}

	/**
	 * inserisce un record nella inbox per l'avvenuto salvataggio del programma
	 * genera se richiesto un record in w9outbox 
	 * 
	 * @param programma
	 *            programma lavori
	 * @return risultato dell'operazione di pubblicazione del programma
	 *         
	 */
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	private Long insertInboxOutbox(FlussoEntry flusso) throws Exception{
		//Inserimento pubblicazione in W9Inbox
		//ricavo l'id univoco della pubblicazione
	    Long idComun = this.wgcManager.getNextId("W9INBOX");
	    this.programmiMapper.insertInbox(idComun, new Date(), new Long(2), flusso.getJson());
	    Long idComunOut = this.wgcManager.getNextId("W9OUTBOX");
	    this.programmiMapper.insertOutbox(idComunOut, flusso.getArea(), flusso.getKey01(), flusso.getKey02(), flusso.getKey03(), flusso.getKey04(), new Long(1), flusso.getCodiceFiscaleSA());
	    return idComun;
	}
	
	public ResponseResult health() {
		ResponseResult risultato = new ResponseResult();
		risultato.setEsito(true);
		return risultato;
	}

	public ResponseResult getPubblicazioneLink(Long idRicevuto) {
		ResponseResult risultato = new ResponseResult();
		risultato.setEsito(true);
		try {
			String contri = this.programmiMapper.getPubblicazioneLink(idRicevuto);
			if(contri == null || "".equals(contri)) {
				risultato.setEsito(false);
				risultato.setErrorData(ResponseResult.ERROR_NO_PUBBLICAZIONE_PROGRAMMA);
			} else {
				String url = this.programmiMapper.getConfig(APPLICATION_CODE,URL_PUBBLICAZIONE);
				url = url.replace("<CONTRI>",contri);			
				risultato.setData(url);
			}
			
		} catch (Exception e) {
			logger.error("Si è verificato un errore nel metodo getPubblicazioneLink",e);
			risultato.setEsito(false);
		}
		return risultato;
	}

}