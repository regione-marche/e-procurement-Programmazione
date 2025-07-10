package it.appaltiecontratti.inbox.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import it.appaltiecontratti.inbox.entity.AnomaliaEntry;
import it.appaltiecontratti.inbox.entity.ChiaveGaraEntry;
import it.appaltiecontratti.inbox.entity.ComScpListaEntry;
import it.appaltiecontratti.inbox.entity.FaseVarianteEntry;
import it.appaltiecontratti.inbox.entity.FeedbackEntry;
import it.appaltiecontratti.inbox.entity.FeedbackListaEntry;
import it.appaltiecontratti.inbox.entity.FeedbackUpdateForm;
import it.appaltiecontratti.inbox.entity.FlussiListaEntry;
import it.appaltiecontratti.inbox.entity.FlussoEntry;
import it.appaltiecontratti.inbox.entity.InboxRecordEntry;
import it.appaltiecontratti.inbox.entity.ReinvioSchedaEntry;
import it.appaltiecontratti.inbox.entity.RichiestaCancellazioneEntry;
import it.appaltiecontratti.inbox.entity.W9FlussiEntry;
import it.appaltiecontratti.inbox.entity.W9outboxEntry;
import it.appaltiecontratti.inbox.entity.form.SearchCountAnomalieForm;

/**
 * DAO Interface per l'estrazione delle informazioni relative alla gestione del
 * profilo inbox di ORT
 * 
 * @author Michele.DiNapoli
 */
@Mapper
public interface InboxMapper {

	@Select("select valore from w_config where UPPER(codapp) = #{codapp} AND chiave = #{chiave}")
	public String getCipherKey(@Param("codapp") String codapp, @Param("chiave") String chiave);

	@Select("select vin.IDCOMUN as numComunicazione, vin.STACOM as statoComunicazione, "
			+ "vin.tipo_invio as tipoInvio, vin.KEY03 as tipologiaFaseEsecuzione, vin.KEY04 as numeroFase, "
			+ "vin.CFSA as codFiscSa, vin.NOMEIN as denominazioneSa, vin.AUTORE as nomeAutoreInvio, "
			+ "vin.CODOGG as codOgg, vin.DATRIC as dataRicezione, vin.DATIMP as dataImportazione, "
			+ "vin.MSG as messaggioErrore, vin.IDEGOV as idEgov, ib.XML as requestPayload, "
			+ "vin.VERXML as versionRequestPayload "
			+ "from V_W9INBOX vin JOIN W9INBOX ib ON vin.IDCOMUN = ib.IDCOMUN where vin.idcomun = #{idComunicazione}")
	public FlussoEntry getDettaglioFlusso(@Param("idComunicazione") Long idComunicazione);

	public int countSearchFlussi(@Param("area") Long area, @Param("codFisc") String codFisc,
			@Param("codOggetto") String codOggetto, @Param("faseEsecuzione") Long faseEsecuzione,
			@Param("dataRicezioneDa") Date dataRicezioneDa, @Param("dataRicezioneA") Date dataRicezioneA,
			@Param("numeroProg") Long numeroProg);

	public List<FlussiListaEntry> getlistaFlussi(@Param("area") Long area, @Param("codFisc") String codFisc,
			@Param("codOggetto") String codOggetto, @Param("faseEsecuzione") Long faseEsecuzione,
			@Param("dataRicezioneDa") Date dataRicezioneDa, @Param("dataRicezioneA") Date dataRicezioneA,
			@Param("numeroProg") Long numeroProg, @Param("sort") String sort,
			@Param("sortDirection") String sortDirection, RowBounds rowBounds);

	public int countSearchFeedback(@Param("numErrore") Long numErrore, @Param("cig") String cig,
			@Param("codFisc") String codFisc, @Param("dataTrasmissioneDa") Date dataTrasmissioneDa,
			@Param("feedbackAnalisi") String feedbackAnalisi, @Param("dataTrasmissioneA") Date dataTrasmissioneA,
			@Param("codiceAnomalia") String codiceAnomalia, @Param("fase") Long fase, @Param("faseNum") Long faseNum,
			@Param("escludiEliminazioni") boolean escludiEliminazioni);

	public List<FeedbackListaEntry> getListaFeedback(@Param("numErrore") Long numErrore, @Param("cig") String cig,
			@Param("codFisc") String codFisc, @Param("dataTrasmissioneDa") Date dataTrasmissioneDa,
			@Param("feedbackAnalisi") String feedbackAnalisi, @Param("dataTrasmissioneA") Date dataTrasmissioneA,
			@Param("codiceAnomalia") String codiceAnomalia, @Param("fase") Long fase, @Param("faseNum") Long faseNum,
			@Param("sort") String sort, @Param("sortDirection") String sortDirection, @Param("escludiEliminazioni") boolean escludiEliminazioni, RowBounds rowBounds);

	@Select("SELECT xm.numxml as numEsportazione, xm.data_export as dataCreAzionePayload, "
			+ "xm.data_invio as dataInvio, l.cig, uf.nomein as denominazione, "
			+ "xm.fase_esecuzione as tipologiaFaseEsecuzione, xm.num as numProgFase, "
			+ "xm.xml as requestPayload, xm.note, xm.data_elaborazione as dataElaborazione, "
			+ "xm.num_errore as numeroErrore, xm.num_warning as numeroWarning, "
			+ "xm.data_feedback as dataImportazione, xm.feedback_analisi as feedbackAnalisi "
			+ "FROM W9XML xm JOIN W9GARA g ON xm.CODGARA = g.CODGARA "
			+ "JOIN W9LOTT l ON l.CODGARA = xm.CODGARA  AND l.CODLOTT = xm.CODLOTT "
			+ "JOIN UFFINT uf ON uf.CODEIN = g.CODEIN "
			+ "WHERE 1 = 1 AND xm.numxml = #{numXml} and xm.codgara = #{codGara} and xm.codlott = #{codLott}")
	public FeedbackEntry getDettFeedback(@Param("numXml") Long numXml, @Param("codGara") Long codGara,
			@Param("codLott") Long codLott);

	@Select("SELECT livello, codice, descrizione, scheda from W9XMLANOM WHERE 1 = 1 AND numxml = #{numXml}  and codgara = #{codGara} and codlott = #{codLott} order by livello desc")
	public List<AnomaliaEntry> getDettAnomalia(@Param("numXml") Long numXml, @Param("codGara") Long codGara,
			@Param("codLott") Long codLott);

	@Update("UPDATE W9XML SET note = #{note}, feedback_analisi = #{feedbackAnalisi} WHERE numxml = #{numXml}  and codgara = #{codGara} and codlott = #{codLott}")
	public void updateFeedback(FeedbackUpdateForm feedbackUpdateForm);

	public int countSearchComScp(@Param("area") Long area, @Param("codFisc") String codFisc,
			@Param("codice") String codice, @Param("stato") Long stato, @Param("dataInvioDa") Date dataInvio,
			@Param("dataInvioA") Date dataInvioA);

	public List<ComScpListaEntry> getlistaComScp(@Param("area") Long area, @Param("codFisc") String codFisc,
			@Param("codice") String codice, @Param("stato") Long stato, @Param("dataInvioDa") Date dataInvioDa,
			@Param("dataInvioA") Date dataInvioA, @Param("sort") String sort,
			@Param("sortDirection") String sortDirection, RowBounds rowBounds);

	@Select("SELECT nomein from	UFFINT WHERE cfein =#{codFisc} limit 1")
	public String getNameByCF(@Param("codFisc") String codFisc);

	@Select("SELECT IDCOMUN as idcomun, AREA as area, key01, key04, key03, DATINV as dataInvio, STATO as stato, "
			+ "FILE_JSON as contenutoJson, MSG as messaggio, ID_RICEVUTO as idRicevuto, "
			+ "CFSA as codFisc FROM w9outbox WHERE IDCOMUN = #{idComun}")
	public W9outboxEntry getDettComScp(@Param("idComun") Long idComun);

	@Update("update w9outbox set FILE_JSON = null, MSG = null,STATO = 1 where IDCOMUN = #{idcomun}")
	public void updateMsgJsonStato(@Param("idcomun") Long idcomun);

	@Select("SELECT ID AS idProgramma FROM piatri WHERE CONTRI= #{key01}")
	public String getIdProgramma(@Param("key01") Long key01);

	@Select("SELECT IDAVGARA AS idGara FROM w9gara WHERE CODGARA = #{key01}")
	public String getIdGara(@Param("key01") Long key01);

	public int countSearchRichiesteCancellazione();

	public List<RichiestaCancellazioneEntry> getListaRichiesteCancellazione(@Param("sort") String sort,
			@Param("sortDirection") String sortDirection, RowBounds rowBounds);

	@Select("select IDCOMUN as idComunicazione, XML as xml, IDEGOV as idEGov, MSG as msg, DATRIC as dataRicezione, STACOM as stato from W9INBOX where IDCOMUN = #{idComunicazione}")
	public InboxRecordEntry loadRecordByIdComunicazione(@Param("idComunicazione") Long idComunicazione);

	@Update("update W9INBOX set stacom = #{stato} where idcomun = #{idComunicazione}")
	public void updateStatoComunicazione(@Param("idComunicazione") Long idComunicazione, @Param("stato") Long stato);

	@Update("update W9INBOX set msg = #{motivazione} where idcomun = #{idComunicazione}")
	public void updateMotivazioneComunicazione(@Param("idComunicazione") Long idComunicazione,
			@Param("motivazione") String motivazione);

	@Select("select idflusso as idFlusso, area, key01 as codGara, key02 as codLotto, key03 as faseEsecuzione, key04 as numProgressivoFase, tinvio2 as tInvio2, "
			+ "datinv as dataInvio, noteinvio as noteInvio, xml, codcomp as codComp, idcomun as idComunicazione, cfsa as codiceFiscaleStazioneAppaltante, "
			+ "codogg as codiceOggetto from w9flussi where idcomun = #{idComunicazione}")
	public W9FlussiEntry getFlussoByIdComunicazione(@Param("idComunicazione") Long idComunicazione);

	public Long countFasiSuccessiveDipendenti(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("listaFasi") List<Integer> listaFasi);

	@Select("select NUMXML from W9XML where IDFLUSSO = #{idFlusso}")
	public Long getNumXml(@Param("idFlusso") Long idFlusso);

	@Select("select CODICE from W9XMLANOM where CODGARA = #{codGara} AND CODLOTT = #{codLotto} AND NUMXML = #{numXml} limit 1")
	public String getCodiceXmlAnom(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numXml") Long numXml);

	@Select("select IDCOMUN from W9FLUSSI where AREA = 1 and KEY01 = #{codGara} and KEY02 = #{codLotto} and KEY03 = #{faseEsecuzione} and KEY04 = #{numProgressivoFase} and idflusso = #{idFlusso} and TINVIO2 < 0 order by DATINV desc")
	public Long getIdComunicazione(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("faseEsecuzione") Long faseEsecuzione, @Param("numProgressivoFase") Long numProgressivoFase,
			@Param("idFlusso") Long idFlusso);

	@Select("select a.DESCRIZIONE from W9XMLANOM a, w9xml x, w9flussi f where a.codgara = x.codgara "
			+ "and a.codlott = x.codlott and a.numXml = x.numXml and a.livello = 'ERRORE' and f.idFlusso = x.idFlusso "
			+ "and f.tinvio2 < 0 and x.idFlusso = #{idFlusso} and f.idFlusso = #{idFlusso} and f.idComun = #{idComunicazione}")
	public String getDescrizioneXmlAnom(@Param("idFlusso") Long idFlusso,
			@Param("idComunicazione") Long idComunicazione);

	@Update("update W9INBOX set MSG = #{descrizione} where IDCOMUN = #{idComunicazione}")
	public void updateMsgComunicazione(@Param("idComunicazione") Long idComunicazione,
			@Param("descrizione") String descrizione);

	@Insert("insert into W9FLUSSI_ELIMINATI(IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, AUTORE, XML, VERXML, CFSA, CODCOMP, IDCOMUN, CODOGG, DATIMP) "
			+ "select IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, AUTORE, XML, VERXML, CFSA, CODCOMP, IDCOMUN, CODOGG, DATIMP FROM W9FLUSSI WHERE "
			+ "KEY01 = #{codGara} and KEY02 = #{codLotto} and KEY03 = #{faseEsecuzione} and KEY04 = #{numProgressivoFase} and TINVIO2 > 0")
	public void insertFlussoEliminato(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("faseEsecuzione") Long faseEsecuzione, @Param("numProgressivoFase") Long numProgressivoFase);

	@Update("update W9FLUSSI_ELIMINATI set DATCANC = #{dataCancellazione}, MOTIVOCANC = #{motivoCancellazione} where KEY01 = #{codGara} and KEY02 = #{codLotto} and KEY03 = #{faseEsecuzione} and KEY04 = #{numProgressivoFase}")
	public void updateDataMotivoFlussoEliminato(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("faseEsecuzione") Long faseEsecuzione, @Param("numProgressivoFase") Long numProgressivoFase,
			@Param("dataCancellazione") Date dataCancellazione,
			@Param("motivoCancellazione") String motivoCancellazione);

	@Delete("delete from W9FLUSSI where KEY01 = #{codGara} and KEY02 = #{codLotto} and KEY03 = #{faseEsecuzione} and KEY04 = #{numProgressivoFase} and TINVIO2 > 0")
	public void deleteFlusso(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("faseEsecuzione") Long faseEsecuzione, @Param("numProgressivoFase") Long numProgressivoFase);

	
	@Select("select idflusso as idFlusso, area, key01 as codGara, key02 as codLotto, key03 as faseEsecuzione, key04 as numProgressivoFase, tinvio2 as tInvio2, "
			+ "datinv as dataInvio, noteinvio as noteInvio, xml, codcomp as codComp, idcomun as idComunicazione, cfsa as codiceFiscaleStazioneAppaltante, "
			+ "codogg as codiceOggetto from w9flussi where KEY01=#{codGara} and KEY02=#{codLotto} and KEY03=#{faseEsecuzione} and KEY04 = #{numProgressivoFase}  and TINVIO2 > 0")
	public List<W9FlussiEntry> getFlussiFase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("faseEsecuzione") Long faseEsecuzione, @Param("numProgressivoFase") Long numProgressivoFase);
	
	
	@Delete("delete from W9FASI where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{faseEsecuzione} and num = #{numProgressivoFase}")
	public void deleteFase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("faseEsecuzione") Long faseEsecuzione, @Param("numProgressivoFase") Long numProgressivoFase);

	@Update("update W9LOTT set CODCUI = null where CODGARA = #{codGara} and CODLOTT = #{codLotto}")
	public void deleteCui(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select count(*) from W9FASI where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{faseEsecuzione}")
	public Long countFasi(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("faseEsecuzione") int faseEsecuzione);

	@Delete("delete from W9ESITO where codgara = #{codGara} and codlott = #{codLotto}")
	public void deleteW9Esito(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Delete("delete from W9PUES where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{numProgressivoFase}")
	public void deleteW9Pues(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9IMPRESE where codgara = #{codGara} and codlott = #{codLotto}")
	public void deleteW9Imprese(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Delete("delete from W9APPA where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numProgressivoFase}")
	public void deleteW9Appa(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	public void deleteW9Inca(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase, @Param("listaSezioni") List<String> listaSezioni);

	@Delete("delete from W9AGGI where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numProgressivoFase}")
	public void deleteW9Aggi(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9FINA where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numProgressivoFase}")
	public void deleteW9Fina(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9REQU where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numProgressivoFase}")
	public void deleteW9Requ(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9INIZ where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{numProgressivoFase}")
	public void deleteW9Iniz(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9MISSIC where codgara = #{codGara} and codlott = #{codLotto} and num_miss = #{numProgressivoFase}")
	public void deleteW9Missic(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9SIC where codgara = #{codGara} and codlott = #{codLotto} and num_sic = #{numProgressivoFase}")
	public void deleteW9Sic(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9AVAN where codgara = #{codGara} and codlott = #{codLotto} and num_avan = #{numProgressivoFase}")
	public void deleteW9Avan(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9CONC where codgara = #{codGara} and codlott = #{codLotto} and num_conc = #{numProgressivoFase}")
	public void deleteW9Conc(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9COLL where codgara = #{codGara} and codlott = #{codLotto} and num_coll = #{numProgressivoFase}")
	public void deleteW9Coll(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9SOSP where codgara = #{codGara} and codlott = #{codLotto} and num_sosp = #{numProgressivoFase}")
	public void deleteW9Sosp(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9VARI where codgara = #{codGara} and codlott = #{codLotto} and num_vari = #{numProgressivoFase}")
	public void deleteW9Vari(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9MOTI where codgara = #{codGara} and codlott = #{codLotto} and num_vari = #{numProgressivoFase}")
	public void deleteW9Moti(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9ACCO where codgara = #{codGara} and codlott = #{codLotto} and num_acco = #{numProgressivoFase}")
	public void deleteW9Acco(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9SUBA where codgara = #{codGara} and codlott = #{codLotto} and num_suba = #{numProgressivoFase}")
	public void deleteW9Suba(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9RITA where codgara = #{codGara} and codlott = #{codLotto} and num_rita = #{numProgressivoFase}")
	public void deleteW9Rita(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9INFOR where codgara = #{codGara} and codlott = #{codLotto} and num_infor = #{numProgressivoFase}")
	public void deleteW9Infor(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9INASIC where codgara = #{codGara} and codlott = #{codLotto} and num_inad = #{numProgressivoFase}")
	public void deleteW9Inasic(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9REGCON where codgara = #{codGara} and codlott = #{codLotto} and num_rego = #{numProgressivoFase}")
	public void deleteW9Regcon(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9TECPRO where codgara = #{codGara} and codlott = #{codLotto} and num_tpro = #{numProgressivoFase}")
	public void deleteW9Tecpro(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9CANT where codgara = #{codGara} and codlott = #{codLotto} and num_cant = #{numProgressivoFase}")
	public void deleteW9Cant(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9CANTDEST where codgara = #{codGara} and codlott = #{codLotto} and num_cant = #{numProgressivoFase}")
	public void deleteW9Cantdest(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Delete("delete from W9CANTIMP where codgara = #{codGara} and codlott = #{codLotto} and num_cant = #{numProgressivoFase}")
	public void deleteW9Cantimp(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numProgressivoFase") Long numProgressivoFase);

	@Update("update W9INBOX set stacom = #{stato}, msg = #{msg} where idcomun = #{idComunicazione}")
	public void updateW9Inbox(InboxRecordEntry entry);

	@Update("update W9FLUSSI set datimp = #{datimp} where idflusso = #{idFlusso}")
	public void updateDataImpW9Flusso(@Param("idFlusso") Long idFlusso, @Param("datimp") Date datimp);

	@Select("SELECT count(xm.NUMXML) FROM W9XML xm WHERE xm.FEEDBACK_ANALISI = '2' AND xm.NUM_ERRORE = 1")
	public Long countErroriFeedback();

	@Select("SELECT count(IDCOMUN) FROM W9OUTBOX WHERE STATO = '3'")
	public Long countErroriComunicazioniSCP();

	@Select("select distinct(codice) from w9xmlanom order by codice asc")
	public List<String> getListaCodiciAnomaliaFeedback();

	@Select("select cig from w9lott where codgara = #{codGara} and codlott = #{codLotto}")
	public String getCigFromLotto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);
	
	@Select("select cig from w9lott where codgara = #{codGara}")
	public String getSmartCigFromCodgara(@Param("codGara") Long codGara);

	@Select("select num_errore from W9XML where codgara = #{codGara} and codlott = #{codLotto} and numxml = #{numXml}")
	public Long getNumeroErroriFeedbackByInvio(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numXml") Long numXml);

	public Long countAnomalie(SearchCountAnomalieForm form);

	public List<ReinvioSchedaEntry> searchAnomalie(SearchCountAnomalieForm form);

	@Update("update w9fasi set daexport = #{daExport} where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{faseEsecuzione} and num = #{progressivoFase}")
	public void setFaseDaExport(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("faseEsecuzione") Long faseEsecuzione, @Param("progressivoFase") Long progressivoFase,
			@Param("daExport") String daExport);

	@Select("select max(NUMXML) from W9XML where CODGARA = #{codGara} and CODLOTT = #{codLotto}")
	public Long selectIdW9Xml(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto);

	@Insert("insert into w9xml(CODGARA, CODLOTT, NUMXML, DATA_EXPORT, FASE_ESECUZIONE, NUM) values (#{codGara}, #{codLotto}, #{numXml}, #{dataExport}, #{faseEsecuzione}, #{numProgressivoFase})")
	public void insertW9Xml(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
			@Param("numXml") final Long numXml, @Param("dataExport") final Date dataExport,
			@Param("faseEsecuzione") final Long faseEsecuzione,
			@Param("numProgressivoFase") final Long numProgressivoFase);

	// RISOLVI ERRORI

	// CUI inesistente

	public void updateCui1(@Param("dataInvioDa") final Date dataInvioDa, @Param("dataInvioA") final Date dataInvioA);

	public void updateCui2(@Param("dataInvioDa") final Date dataInvioDa, @Param("dataInvioA") final Date dataInvioA);

	public List<ReinvioSchedaEntry> selectCui(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	// Aggiudicazione già presente

	public void updateAggPresente1(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public void updateAggPresente2(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public List<ReinvioSchedaEntry> selectAggPresente(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	// Mancanza fase iniziale o conclusione

	public List<ReinvioSchedaEntry> selectNoFaseInizConc1(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public List<ReinvioSchedaEntry> selectNoFaseInizConc2(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	// Schede senza ID locale

	public void updateNoIdLocale1(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public void updateNoIdLocale2(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public List<ReinvioSchedaEntry> selectNoIdLocale(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	// Errore imprevisto di SIMOG

	public List<ReinvioSchedaEntry> selectErroreSimog1(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public List<ReinvioSchedaEntry> selectErroreSimog2(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	// CANCELLA/MODIFICA FEEDBACK ERRORI
	
	
	@Delete("delete from w9xmlanom xa where not exists (select 1 from w9xml x where xa.codgara = x.codgara and xa.codlott = x.codlott and x.numxml = xa.numxml")
	public void cancellaAnomalieOrfane();

	public void cancellaErroriSchedeRitrasmesse(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public void modificaErroriSchedeRitrasmesse(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public void cancellaErroriSchedeNonDovute1(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public void cancellaErroriSchedeNonDovute2(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public void cancellaErroriSchedeNonDovute3(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public void cancellaErroriSchedeNonDovute4(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public void modificaErroriSchedeNonDovute1(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public void modificaErroriSchedeNonDovute2(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public void modificaErroriSchedeNonDovute3(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	public void modificaErroriSchedeNonDovute4(@Param("dataInvioDa") final Date dataInvioDa,
			@Param("dataInvioA") final Date dataInvioA);

	@Select("select codgara as codGara, codlott as codLotto from w9lott where cig=#{codiceCIG} limit 1")
	public ChiaveGaraEntry getChiaveGaraLottoByCig(@Param("codiceCIG") String codiceCIG);

	@Update("update W9LOTT set ID_SCHEDA_LOCALE=#{idSchedaLocale} where CIG=#{codiceCIG}")
	public void updateIdSchedaLocale(@Param("idSchedaLocale") String idSchedaLocale,
			@Param("codiceCIG") String codiceCIG);

	@Update("update W9LOTT set ID_SCHEDA_SIMOG=#{idSchedaSimog} where CIG=#{codiceCIG}")
	public void updateIdSchedaSimog(@Param("idSchedaSimog") String idSchedaSimog, @Param("codiceCIG") String codiceCIG);

	@Select("SELECT NUM_APPA FROM W9APPA WHERE CODCUI=#{codiceCUI}")
	public Long getNumAppaByCodCui(@Param("codiceCUI") String codiceCUI);

	@Update("update W9LOTT set CODCUI=#{codiceCUI} where CIG=#{codiceCIG}")
	public void updateCodCuiByCig(@Param("codiceCUI") String codiceCUI, @Param("codiceCIG") String codiceCIG);

	@Update("update W9APPA set CODCUI=#{codiceCUI} where CODGARA=#{codGara} and CODLOTT=#{codLotto} and NUM_APPA = #{numAppa}")
	public void updateCodCuiAppa(@Param("codiceCUI") String codiceCUI, @Param("codGara") Long codGara,
			@Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa);

	@Update("update W9FASI set ID_SCHEDA_LOCALE=#{idSchedaLocale} where CODGARA=#{codGara} and CODLOTT=#{codLotto} and FASE_ESECUZIONE=#{fase} and NUM_APPA=#{numAppa}")
	public void updateIdSchedaLocaleFaseSingola(@Param("idSchedaLocale") String idSchedaLocale,
			@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("fase") Long fase,
			@Param("numAppa") Long numAppa);

	@Update("update W9FASI set ID_SCHEDA_LOCALE=#{idSchedaLocale} where CODGARA=#{codGara} and CODLOTT=#{codLotto} and FASE_ESECUZIONE=#{fase} and NUM=#{num}")
	public void updateIdSchedaLocaleFaseMultipla(@Param("idSchedaLocale") String idSchedaLocale,
			@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("fase") Long fase,
			@Param("num") Long num);

	@Update("update W9FASI set ID_SCHEDA_SIMOG=#{idSchedaSimog} where CODGARA=#{codGara} and CODLOTT=#{codLotto} and FASE_ESECUZIONE=#{fase} and NUM_APPA=#{numAppa}")
	public void updateIdSchedaSimogFaseSingola(@Param("idSchedaSimog") String idSchedaSimog,
			@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("fase") Long fase,
			@Param("numAppa") Long numAppa);

	@Update("update W9FASI set ID_SCHEDA_SIMOG=#{idSchedaSimog} where CODGARA=#{codGara} and CODLOTT=#{codLotto} and FASE_ESECUZIONE=#{fase} and NUM=#{num}")
	public void updateIdSchedaSimogFaseMultipla(@Param("idSchedaSimog") String idSchedaSimog,
			@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("fase") Long fase,
			@Param("num") Long num);

	@Select("select count(*) from W9FASI where CODGARA=#{codGara} and CODLOTT=#{codLotto} and FASE_ESECUZIONE=#{fase} and NUM=#{num}")
	public Long countFasiPerRiallineamento(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("fase") Long fase,@Param("num") Long num);

	@Update("update W9LOTT set ID_SCHEDA_SIMOG=null, CODCUI=null where CODGARA=#{codGara} and CODLOTT=#{codLotto}")
	public void pulisciIdSchedaSimogAndCodCui(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Update("update W9APPA set CODCUI=null where CODGARA=#{codGara} and CODLOTT=#{codLotto}")
	public void pulisciCodCuiAggiudicazione(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Update("update W9FASI set ID_SCHEDA_SIMOG=null where CODGARA=#{codGara} and CODLOTT=#{codLotto}")
	public void pulisciIdSchedaSimogW9Fasi(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	// Delete Feedback

	@Delete("delete from W9XMLANOM where codgara = #{codGara} and codlott = #{codLotto} and numxml = #{numXml}")
	public void deleteW9XmlAnom(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
			@Param("numXml") final Long numXml);

	@Delete("delete from W9XML where codgara = #{codGara} and codlott = #{codLotto} and numxml = #{numXml}")
	public void deleteW9Xml(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
			@Param("numXml") final Long numXml);

	@Select("select esito_procedura from W9ESITO where codgara = #{codGara} and codlott = #{codLotto}")
	Integer getEsitoProceduraFromW9Esito(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Update("update W9LOTT set ID_SCHEDA_SIMOG=null where CODGARA=#{codGara} and CODLOTT=#{codLotto}")
	void pulisciIdSchedaSimogW9Lotto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);
	
	@Select("select num_avan from w9avan where codgara = #{codGara} and codlott = #{codLotto} and data_raggiungimento between #{dataRaggiungimentoDa} and #{dataRaggiungimentoA}")
	public Long getNumAvanByCodgaraCodlottoDataraggiugnimento(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("dataRaggiungimentoDa") Date dataRaggiungimentoDa, @Param("dataRaggiungimentoA") Date dataRaggiungimentoA);
		
	@Select("select num_sosp from w9sosp where codgara = #{codGara} and codlott = #{codLotto} and data_verb_sosp between #{dataVerbSospDa} and #{dataVerbSospA}")
	public Long getNumSospByCodgaraCodlottoDataVerbSosp(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("dataVerbSospDa") Date dataVerbSospDa, @Param("dataVerbSospA") Date dataVerbSospA);
	
	@Select("select num_suba from w9suba where codgara = #{codGara} and codlott = #{codLotto} and data_autorizzazione between #{dataAutorizzazioneDa} and #{dataAutorizzazioneA}")
	public Long getNumSubaByCodgaraCodlottoDataAutorizzazione(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("dataAutorizzazioneDa") Date dataAutorizzazioneDa, @Param("dataAutorizzazioneA") Date dataAutorizzazioneA);
	
	@Select("select num_vari from w9vari where codgara = #{codGara} and codlott = #{codLotto} and data_verb_appr between #{dataVerbApprDa} and #{dataVerbApprA}")
	public Long getNumVariByCodgaraCodlottodataVerbAppr(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("dataVerbApprDa") Date dataVerbApprDa, @Param("dataVerbApprA") Date dataVerbApprA);
	
	@Select("select num_acco from w9acco where codgara = #{codGara} and codlott = #{codLotto} and data_accordo between #{dataAccordoDa} and #{dataAccordoA}")
	public Long getNumAccoByCodgaraCodlottoDataAccordo(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("dataAccordoDa") Date dataAccordoDa, @Param("dataAccordoA") Date dataAccordoA);

	@Delete("delete from w9outbox where IDCOMUN = #{idComunicazione}")
	public void deleteComunicazione(@Param("idComunicazione") Long idComunicazione);
}
