package it.appaltiecontratti.monitoraggiocontratti.simog.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import it.appaltiecontratti.monitoraggiocontratti.simog.beans.BaseRupInfo;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.FaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.FaseSospensioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.FlussoEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.InfoUtenteEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.LottoFullEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.NoteAvvisoEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.beans.SABaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ChiaveConfigurazione;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.GaraInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.LottoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.PubblicazioneInsertForm;

/**
 * DAO Interface per l'estrazione e l'inserimento delle informazioni relative
 * alla gestione dei dati correlati ai servizi SIMOG.
 * 
 * @author Michele.DiNapoli
 */

@Mapper
public interface SimogMapper {

	@Select("select CFTEC from TECNI where SYSCON=#{syscon} and CGENTEI=#{cfStazioneAppaltante} limit 1")
	public String getCFTecniBySysconAndSa(@Param("syscon") Long syscon,
			@Param("cfStazioneAppaltante") String cfStazioneAppaltante);

	@Select("select CFEIN from UFFINT where CODEIN=#{codStazioneAppaltante}")
	public List<String> getCFSaByCodice(@Param("codStazioneAppaltante") String codStazioneAppaltante);
	
	@Select("select codein from UFFINT where CFEIN=#{cfStazioneAppaltante}")
	public List<String> getCodiceSaByCf(@Param("cfStazioneAppaltante") String cfStazioneAppaltante);

	@Select("select CODTEC from TECNI where SYSCON is null and CGENTEI=#{cfStazioneAppaltante} and UPPER(CFTEC)=(select UPPER(SYSCF) from USRSYS where SYSCON=#{syscon} and SYSCF is not null)")
	public String getCodiceRupByCgentei(@Param("syscon") Long syscon,
			@Param("cfStazioneAppaltante") String cfStazioneAppaltante);

	@Select("select count(*) from w9lott where cig = #{cig}")
	public Long getCountLottiByCig(@Param("cig") String cig);
	
	@Select("select count(*) from w9lott l, w9gara g where l.codgara = g.codgara and l.cig = #{cig} and g.codein = #{stazioneAppaltante}")
	public Long getCountLottiByCigAndSa(@Param("cig") String cig, @Param("stazioneAppaltante") String stazioneAppaltante);

	@Update("update TECNI set SYSCON=#{syscon} where CODTEC=#{codiceTecnico}")
	public void updateSysconTecnico(@Param("syscon") Long syscon, @Param("codiceTecnico") String codiceTecnico);

	@Select("select SYSUTE as nominativo, EMAIL as email, SYSCF as cf from USRSYS where SYSCON=#{syscon}")
	public InfoUtenteEntry getInfoUtente(@Param("syscon") Long syscon);

	@Insert("insert into TECNI (CODTEC, COGTEI, NOMETEI, NOMTEC, CFTEC, CGENTEI, SYSCON, EMATEC, TELTEC, FAXTEC, INDTEC, CAPTEC, CITTEC) values (#{codtec}, #{cognome}, #{nome}, #{nominativo}, #{cf}, #{cgentei}, #{syscon}, #{email}, #{telefono}, #{fax}, #{indirizzo}, #{cap}, #{codiceistatcomune})")
	public void insertTecnico(InfoUtenteEntry utente);

	@Insert("insert into w9gara (CODGARA, OGGETTO, SITUAZIONE, PROV_DATO, IDAVGARA, FLAG_ENTE_SPECIALE, ID_MODO_INDIZIONE, TIPO_APP, CIG_ACCQUADRO, SOMMA_URGENZA, IMPORTO_GARA, NLOTTI, DATA_PUBBLICAZIONE, RUP, RIC_ALLUV, CAM, SISMA, VER_SIMOG, CODEIN, IDCC, IDUFFICIO, INDSEDE, COMSEDE, PROSEDE, DURATA_ACCQUADRO, FLAG_SA_AGENTE, ID_TIPOLOGIA_SA, DENOM_SA_AGENTE, CF_SA_AGENTE, TIPOLOGIA_PROCEDURA, FLAG_CENTRALE_STIPULA, SYSCON, PREV_BANDO, DATA_CREAZIONE, ID_F_DELEGATE, ALLEGATO_IX, GARA_URGENZA ) values (#{codgara}, #{oggetto}, #{situazione}, #{provenienzaDato}, #{identificativoGara}, #{tipoSettore}, #{modalitaIndizione}, #{tipoApp}, #{cigQuadro}, #{sommaUrgenza}, #{importoGara}, #{numLotti}, #{dataPubblicazione}, #{codiceTecnico}, #{ricostruzioneAlluvione}, #{dispArtDlgs}, #{sisma}, #{versioneSimog}, #{codiceStazioneAppaltante}, #{idCentroDiCosto}, #{idUfficio}, #{indirizzoSede}, #{comuneSede}, #{provinciaSede}, #{durataAcquadro}, #{flagSaAgente}, #{tipologiaStazioneAppaltante}, #{denomSoggStazioneAppaltante}, #{cfStazioneAppaltante}, #{tipologiaProcedura}, #{flagStipula}, #{syscon}, #{prevBando}, #{dataCreazione}, #{idFDelegate}, #{modalitaIndizioneAllegato9}, #{motivoSommaUrgenza})")
	public void insertGara(GaraInsertForm form);

	@Select("select codgara from w9gara where idavgara = #{idAvGara}")
	public String getCodGaraByIdAnac(@Param("idAvGara") String idAvGara);
	
	@Select("select codgara from w9gara where idavgara = #{idAvGara} and codein = #{cfStazioneAppaltante}")
	public String getCodGaraByIdAnacAndSA(@Param("idAvGara") String idAvGara, @Param("cfStazioneAppaltante") String cfStazioneAppaltante);

	@Insert("insert into w9pubb (codgara, codlott, num_appa, num_pubb, data_guce, data_guri, data_albo, quotidiani_naz,  quotidiani_reg,  profilo_committente,  sito_ministero_inf_trasp,  sito_osservatorio_cp,  data_bore,  periodici) values (#{codgara}, #{codlott}, #{num_appa}, #{num_pubb}, #{data_guce}, #{data_guri}, #{data_albo}, #{quotidiani_naz}, #{quotidiani_reg}, #{profilo_committente}, #{sito_ministero_inf_trasp}, #{sito_osservatorio_cp}, #{data_bore}, #{periodici})")
	public void insertPubblicazione(PubblicazioneInsertForm form);
	
	@Delete("delete from w9pubb where codgara = #{codgara} and codlott = 1 and num_appa = 1 and num_pubb = 1")
	public void deletePubblicazione(@Param("codgara") Long codgara);

	@Insert("insert into w9lott (codgara, codlott, oggetto, rup, somma_urgenza, importo_lotto, cpv, id_scelta_contraente, id_scelta_contraente_50, id_categoria_prevalente, nlotto, cig, situazione, data_consulta_gara, flag_ente_speciale, daexport, importo_attuazione_sicurezza, importo_tot, clascat, tipo_contratto, manod, comcon, luogo_istat, luogo_nuts, art_e1, art_e2, cup, cupesente, data_pubblicazione, id_scheda_locale, exsottosoglia, id_scheda_simog, CONTRATTO_ESCLUSO_ALLEGGERITO, ESCLUSIONE_REGIME_SPECIALE, DEROGA_QUALIFICAZIONE_SA, CATEGORIA_MERC, FLAG_PREVEDE_RIP, DURATA_RINNOVI, MOTIVO_COLLEGAMENTO, CIG_ORIGINE_RIP, FLAG_PNRR_PNC, FLAG_PREVISIONE_QUOTA, FLAG_MISURE_PREMIALI, DATA_SCADENZA_PAGAMENTI ) values (#{codgara}, #{codlott}, #{oggetto}, #{rup}, #{somma_urgenza}, #{importo_lotto}, #{cpv}, #{id_scelta_contraente}, #{id_scelta_contraente_50}, #{id_categoria_prevalente}, #{nlotto}, #{cig}, #{situazione}, #{data_consulta_gara}, #{flag_ente_speciale}, #{daexport}, #{importo_attuazione_sicurezza}, #{importo_tot}, #{clascat}, #{tipo_contratto}, #{manod}, #{comcon}, #{luogo_istat}, #{luogo_nuts}, #{art_e1}, #{art_e2}, #{cup}, #{cupesente}, #{data_pubblicazione}, #{id_scheda_locale}, #{exsottosoglia}, #{id_scheda_simog}, #{contrattoEsclusoAlleggerito}, #{esclusioneRegimeSpeciale},#{qualificazioneStazioneAppaltante},#{categoriaMerceologica},#{flagPrevedeRip},#{durataRinnovi},#{motivoCollegamento},#{cigOrigineRip},#{flagPnrrPnc},#{flagPrevisioneQuota},#{flagMisurePreliminari},#{dataScadenzaPagamenti})")
	public void insertLotto(LottoInsertForm form);

	@Insert("insert into W9CPV (codgara, codlott, num_cpv, cpv) values (#{codiceGara},#{codiceLotto},#{numCpv},#{cpv})")
	public void insertCpvSecondariLotto(@Param("codiceGara") Long codiceGara, @Param("codiceLotto") Long codiceLotto,
			@Param("numCpv") Long numCpv, @Param("cpv") String cpv);

	@Select("select f.KEY01 as key01, f.KEY02 as key02, f.KEY03 as key03, f.KEY04 as key04, u.cfein as codiceFiscaleSA, f.IDFLUSSO as id, f.DATINV as dataInvio from W9FLUSSI f, W9GARA g, UFFINT u, W9LOTT l where f.KEY01=l.CODGARA and f.KEY02=l.CODLOTT and l.SITUAZIONE=95 and g.codgara = l.codgara and g.codein = u.codein and f.TINVIO2=-1")
	public List<FlussoEntry> getListaFlussiEliminati();

	@Select("select IDAVGARA from W9GARA where CODGARA= #{codGara}")
	public String getIdGaraByCod(@Param("codGara") Long codGara);

	@Select("select CIG from W9LOTT where CODGARA=#{codGara} and CODLOTT= #{codLotto}")
	public String getLottoCig(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select TECNI.CFTEC from W9LOTT left join TECNI ON W9LOTT.RUP=TECNI.CODTEC where CODGARA=#{codGara} and CODLOTT= #{codLotto}")
	public String getLottoRup(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Insert("insert into W9FLUSSI_ELIMINATI(IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, AUTORE, XML, VERXML, CFSA, CODCOMP, IDCOMUN, CODOGG, DATIMP) "
			+ " select IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, AUTORE, XML, VERXML, CFSA, CODCOMP, IDCOMUN, CODOGG, DATIMP FROM W9FLUSSI where KEY01= #{key01} and KEY02= #{key02}  and KEY03=#{key03}  and KEY04=#{key04}  and TINVIO2>0")
	public void insertFlussoEliminato(@Param("key01") Long key01, @Param("key02") Long key02,
			@Param("key03") Long key03, @Param("key04") Long key04);

	@Insert("insert into W9FLUSSI_ELIMINATI(IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, AUTORE, XML, VERXML, CFSA, CODCOMP, IDCOMUN, CODOGG, DATIMP) "
			+ " select #{idFlussoEliminato}, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, AUTORE, XML, VERXML, CFSA, CODCOMP, IDCOMUN, CODOGG, DATIMP FROM W9FLUSSI WHERE IDFLUSSO=#{idFlusso}")
	public void copyFlussoEliminato(@Param("idFlusso") Long idFlusso,
			@Param("idFlussoEliminato") Long idFlussoEliminato);

	@Update("update W9FLUSSI_ELIMINATI set DATCANC = #{dataCanc}, MOTIVOCANC = #{motivo} where KEY01= #{key01} and KEY02= #{key02}  and KEY03=#{key03}  and KEY04=#{key04} and DATCANC is null")
	public void setMotivoFlussoEliminato(@Param("key01") Long key01, @Param("key02") Long key02,
			@Param("key03") Long key03, @Param("key04") Long key04, @Param("dataCanc") Date dataCanc,
			@Param("motivo") String motivo);

	@Delete("delete from w9flussi where KEY01=#{key01} and KEY02=#{key02} and KEY03=#{key03} and KEY04=#{key04} and TINVIO2>0")
	public void deleteFlusso(@Param("key01") Long key01, @Param("key02") Long key02, @Param("key03") Long key03,
			@Param("key04") Long key04);

	@Delete("delete from w9flussi where KEY01=#{key01} and KEY02=#{key02} and KEY03=#{key03} and KEY04=#{key04} and TINVIO2=-1")
	public void deleteFlussoCancellazione(@Param("key01") Long key01, @Param("key02") Long key02,
			@Param("key03") Long key03, @Param("key04") Long key04);

	@Update("update W9FLUSSI set TINVIO2=-2 where IDFLUSSO=#{idFlusso}")
	public void setCancellazioneEvasaFlusso(@Param("idFlusso") Long idFlusso);

	@Select("select count(*) from W9FLUSSI where KEY01=#{codGara} and KEY02=#{codLotto} and TINVIO2=-1")
	public Long countRichiesteCancellazioneLotto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Update("update w9lott set situazione = #{situazione} where codgara = #{codGara} and codlott = #{codLotto}")
	public void updateSituazioneLotto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("situazione") Long situazione);

	@Select("select codgara as codGara, codlott as codLotto, fase_esecuzione as fase, num as progressivo, daexport as daExportDb from W9FASI where codgara = #{codgara} and codlott = #{codlott}")
	public List<FaseEntry> getFasiLotto(@Param("codgara") Long codgara, @Param("codlott") Long codlott);

	@Select("select KEY03 from W9FLUSSI where AREA=1 and KEY01=#{codGara} and KEY02=#{codLotto} and tinvio2 > 0")
	public List<Long> getFasiInviate(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select esito_procedura from w9esito where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getEsitoProcedura(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select codgara as codGara, codlott as codLotto, num_sosp as num, data_verb_sosp as dataVerbSosp, data_verb_ripr as dataVerbRipr, id_motivo_sosp as motivoSosp, flag_supero_tempo as flagSuperoTempo, flag_riserve as flagRiserve, flag_verbale as flagVerbale from w9sosp where codgara = #{codGara} and codlott = codlott and num_sosp = #{num} ")
	public FaseSospensioneEntry getFaseSospensione(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("num") Long num);

	@Insert("INSERT INTO g_noteavvisi(notecod, noteprg, noteent, notekey1, notekey2, notekey3, notekey4, notekey5, autorenota, statonota, tiponota, datanota, datachiu, titolonota, testonota) VALUES (#{notecod}, #{noteprg}, #{noteent}, #{notekey1}, #{notekey2}, #{notekey3}, #{notekey4}, #{notekey5}, #{autorenota}, #{statonota}, #{tiponota}, #{datanota}, #{datachiu}, #{titolonota}, #{testonota})")
	public void insertNoteAvviso(NoteAvvisoEntry noteAvviso);

	@Select("select max(notecod) from g_noteavvisi")
	public Long getMaxIdAvviso();

	@Select("select max(IDFLUSSO) from W9FLUSSI where KEY01= #{key01} and KEY02= #{key02}  and KEY03=#{key03}  and KEY04=#{key04} and TINVIO2=-1")
	public Long getIdFlusso(@Param("key01") Long key01, @Param("key02") Long key02, @Param("key03") Long key03,
			@Param("key04") Long key04);

	@Select("select CODGARA as codGara, CODLOTT as codLotto, CIG as cig, OGGETTO as oggetto, CUIINT as cui, URL_EPROC as urlEproc, SITUAZIONE as situazione, NLOTTO as numLotto, COMCON as contrattoPrec, DESCOM as motivoCompletamento, CIGCOM as cigContratto, TIPO_CONTRATTO as tipologia, FLAG_ENTE_SPECIALE as tipoSettore, ART_E1 as contrattoEscluso, ID_SCELTA_CONTRAENTE as sceltaContraente, ID_SCELTA_CONTRAENTE_50 as sceltaContraenteLgs, ID_MODO_GARA as criteriAggiudicazione, ID_SCHEDA_LOCALE as idSchedalocale, RUP as rup, IMPORTO_LOTTO as importoNetto, IMPORTO_ATTUAZIONE_SICUREZZA as importoSicurezza, IMPORTO_TOT as importoTotale, CUPESENTE as esenteCup, CUP as cup, CPV as cpv, MANOD as manodopera, ID_CATEGORIA_PREVALENTE as categoriaPrev, CLASCAT as classeCategoriaPrev, LUOGO_ISTAT as luogoIstat, LUOGO_NUTS as luogoNuts, ID_TIPO_PRESTAZIONE as prestazioneComprese, EXSOTTOSOGLIA as exsottosoglia, codint as codiceInterno, art_e2 as contrattoEscluso161718, somma_urgenza as sommaUrgenza FROM W9LOTT where codgara = #{codiceGara}")
	public List<LottoFullEntry> getFullLottiGara(@Param("codiceGara") Long codiceGara);

	@Update("update W9GARA set SITUAZIONE = #{situazione} where codgara = #{codGara}")
	public void updateSituazioneGara(@Param("codGara") Long codGara, @Param("situazione") Long situazione);

	@Update("update w9fasi set id_scheda_simog = null where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{fase} and num=#{num}")
	public void setIdSchedaSimogNull(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("fase") Long fase, @Param("num") Long num, @Param("daExport") Long daExport);
	
	@Update("update w9fasi set daexport = #{daExport} where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{fase} and num=#{num}")
	public void setW9faseDaExport(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("fase") Long fase, @Param("num") Long num, @Param("daExport") Long daExport);

	@Select("select valore from w_config where UPPER(codapp) = #{codapp} AND chiave = #{chiave}")
	public String getConfig(@Param("codapp") String codapp, @Param("chiave") String chiave);
	
	@Select("select sysab3 from usrsys where syscon = #{syscon}")
	public String getRuoloBySyscon(@Param("syscon") Long syscon);

	@Select("select CODTEC from TECNI where CGENTEI=#{idSa} and UPPER(CFTEC)= #{cf}")
	public List<String> getTecniByCfAndSA(@Param("cf") String cf, @Param("idSa") String idSa);

	@Select("select ematec from TECNI where CGENTEI=#{idSa} and UPPER(CFTEC)= #{cf}")
	public String getEmailTecni(@Param("cf") String cf, @Param("idSa") String idSa);

	@Select("select CIG from W9LOTT where CODGARA=#{codGara}")
	public List<String> getCigLottiGara(@Param("codGara") Long codGara);

	@Update("update w9gara set oggetto = #{oggetto}, IMPORTO_GARA = #{importoGara}, TIPO_APP = #{tipoApp}, ID_MODO_INDIZIONE = #{modalitaIndizione}, CIG_ACCQUADRO = #{cigQuadro}, DURATA_ACCQUADRO = #{durataAcquadro}, DATA_CREAZIONE = #{dataCreazione}, ID_F_DELEGATE = #{idFDelegate}, VER_SIMOG = #{versioneSimog}, SOMMA_URGENZA = #{sommaUrgenza}, idcc = #{idCentroDiCosto}, DATA_PUBBLICAZIONE = #{dataPubblicazione}, ALLEGATO_IX = #{modalitaIndizioneAllegato9}, GARA_URGENZA = #{motivoSommaUrgenza}   where codgara = #{codgara}")
	public void updateGara(GaraInsertForm form);

	@Update("update w9lott set 	OGGETTO = #{oggetto},  cup = #{cup}, cupesente = #{cupesente}, ART_E1 = #{art_e1}, IMPORTO_TOT = #{importo_tot}, IMPORTO_ATTUAZIONE_SICUREZZA = #{importo_attuazione_sicurezza}, IMPORTO_LOTTO = #{importo_lotto}, CPV = #{cpv}, ID_SCELTA_CONTRAENTE = #{id_scelta_contraente}, ID_CATEGORIA_PREVALENTE = #{id_categoria_prevalente},	FLAG_ENTE_SPECIALE = #{flag_ente_speciale}, TIPO_CONTRATTO = #{tipo_contratto},	ID_SCHEDA_LOCALE = #{id_scheda_locale}, ID_SCHEDA_SIMOG = #{id_scheda_simog}, SOMMA_URGENZA = #{somma_urgenza} , daexport = 1, CONTRATTO_ESCLUSO_ALLEGGERITO = #{contrattoEsclusoAlleggerito}, ESCLUSIONE_REGIME_SPECIALE = #{esclusioneRegimeSpeciale}, DEROGA_QUALIFICAZIONE_SA = #{qualificazioneStazioneAppaltante}, CATEGORIA_MERC = #{categoriaMerceologica}, FLAG_PREVEDE_RIP = #{flagPrevedeRip}, DURATA_RINNOVI = #{durataRinnovi}, MOTIVO_COLLEGAMENTO = #{motivoCollegamento}, CIG_ORIGINE_RIP = #{cigOrigineRip}, FLAG_PNRR_PNC = #{flagPnrrPnc}, FLAG_PREVISIONE_QUOTA = #{flagPrevisioneQuota}, FLAG_MISURE_PREMIALI = #{flagMisurePreliminari}, DATA_SCADENZA_PAGAMENTI = #{dataScadenzaPagamenti} where codgara = #{codgara} and cig = #{cig}")
	public void updateLotto(LottoInsertForm lottoInsertForm);

	@Select("select codlott from w9lott where cig = #{cig}")
	public Long getCodLottoByCig(@Param("cig") String cig);
	
	@Insert("INSERT INTO w9lottcup (codgara, codlott, numcup, cup, dati_dipe) VALUES(#{codGara}, #{codLotto}, #{numcup}, #{cup}, #{datiDipe})")
	public void insertW9lottcup(@Param("codGara") Long codGara,@Param("codLotto") Long codLotto,@Param("numcup") Long numcup,@Param("cup") String cup,@Param("datiDipe") String datiDipe);

	@Select("select NOMEIN as nome, CODEIN as codice from UFFINT where CFEIN = #{cf}")
	public List<SABaseEntry> getBaseSAInfoByCf(@Param("cf") String cf);

	@Select("select chiave, valore from w_config where chiave in ('it.eldasoft.pubblicazioni.ws.password', 'it.eldasoft.pubblicazioni.ws.username')")
	public List<ChiaveConfigurazione> getChiaviAccessoOrt();

	@Insert("INSERT INTO CENTRICOSTO (IDCENTRO,CODEIN,CODCENTRO,DENOMCENTRO) VALUES (#{idCdc}, #{codUffint}, #{codiceCentroCostoFromSIMOG}, #{denom})")
	public void insertCdc(@Param("idCdc") Long idCdc, @Param("codUffint") String codUffint,
			@Param("codiceCentroCostoFromSIMOG") String codiceCentroCostoFromSIMOG, @Param("denom") String denom);

	@Update("UPDATE CENTRICOSTO set DENOMCENTRO= #{denom} where IDCENTRO= #{idCdc}")
	public void updateCdc(@Param("idCdc") Long idCdc, @Param("denom") String denom);
	
	@Select("select IDCENTRO from CENTRICOSTO where UPPER(CODCENTRO)=#{codCentro} and CODEIN = #{codein}")
	public Long getIdCdc(@Param("codCentro") String codCentro, @Param("codein") String codein);
	
	@Select("select coalesce(max(IDCENTRO), 0) + 1 as contatore from CENTRICOSTO")
	public Long getMaxIdCdc();

	@Insert("insert into W9LOTTCATE (CODGARA, CODLOTT, NUM_CATE, CATEGORIA) values(#{codgara},#{codlotto},#{num},#{categoria})")
	public void insertLottoCate(@Param("codgara") Long codgara, @Param("codlotto") Long codlotto,
			@Param("num") Long num, @Param("categoria") String categoria);

	@Insert("insert into W9APPALAV (CODGARA, CODLOTT, NUM_APPAL, ID_APPALTO) values(#{codgara},#{codlotto},#{num},#{idAppalto})")
	public void insertAppaLav(@Param("codgara") Long codgara, @Param("codlotto") Long codlotto, @Param("num") Long num,
			@Param("idAppalto") Long idAppalto);

	@Insert("insert into W9APPAFORN (CODGARA, CODLOTT, NUM_APPAF, ID_APPALTO) values(#{codgara},#{codlotto},#{num},#{idAppalto})")
	public void insertAppaForn(@Param("codgara") Long codgara, @Param("codlotto") Long codlotto, @Param("num") Long num,
			@Param("idAppalto") Long idAppalto);

	@Update("update W9GARA set NLOTTI= #{numeroLotti}, SITUAZIONE=1 where CODGARA= #{codgara}")
	public void updateLottiGara(@Param("codgara") Long codgara, @Param("numeroLotti") Long numeroLotti);

	@Insert("INSERT INTO W9INCA (CODGARA, CODLOTT, NUM, NUM_INCA, SEZIONE, ID_RUOLO, CODTEC) values (#{codgara},#{codlotto},#{num},#{numInca},#{sezione},#{idRuolo},#{codtec})")
	public void insertW9Inca(@Param("codgara") Long codgara, @Param("codlotto") Long codlotto, @Param("num") Long num,
			@Param("numInca") Long numInca, @Param("sezione") String sezione, @Param("idRuolo") Long idRuolo,
			@Param("codtec") String codtec);

	@Select("select cron_expression from w_quartz where codapp = #{codapp} and bean_id = #{beanId}")
	public String getCronExpression(@Param("codapp") String codapp, @Param("beanId") String beanId);

	@Select("select codtec from TECNI where UPPER(CFTEC)=#{cf} and CGENTEI=#{cfStazioneAppaltante}")
	public List<String> getCodTecByCFAndSA(@Param("cf") String cf,
			@Param("cfStazioneAppaltante") String cfStazioneAppaltante);

	@Select("select max(idflusso) from w9flussi_eliminati")
	public Long getMaxIdFlussoEliminato();

	@Select("select codimp from IMPR where cfimp = #{codiceFiscale} and cgenimp = #{stazioneAppaltante}")
	public List<String> findImpresa(@Param("codiceFiscale") String codiceFiscale,
			@Param("stazioneAppaltante") String stazioneAppaltante);

	@Select("select codtec as codice, nomtec as nominativo, cftec as cf from tecni where cgentei = #{codUffInt}")
	public List<BaseRupInfo> getAllRupBySa(@Param("codUffInt") String codUffInt);

	@Select("select codtec as codice, nomtec as nominativo, cftec as cf from tecni where codtec = #{codTec}")
	public BaseRupInfo getBaseRup(@Param("codTec") String codTec);

	@Select("select tab1desc from tab1 where tab1cod = 'W9023' and tab1tip= 2")
	public String getTabellatoDateSimog3045();
	
	@Select("SELECT syspwd FROM usrsys WHERE UPPER(syslogin) = #{username}")
	public String getAccountPwd(@Param("username") String username);

	@Delete("delete from w9lottcup where codgara = #{codGara} and codlott = #{codLotto}")
	public void deleteW9lottcup(@Param("codGara")Long codGara, @Param("codLotto")Long codLotto);
}
