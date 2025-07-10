package it.appaltiecontratti.inbox.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import it.appaltiecontratti.inbox.entity.ImpresaEntry;
import it.appaltiecontratti.inbox.entity.TabellatoEntry;
import it.appaltiecontratti.inbox.entity.contratti.AttoDocument;
import it.appaltiecontratti.inbox.entity.contratti.CPVSecondario;
import it.appaltiecontratti.inbox.entity.contratti.CategoriaLotto;
import it.appaltiecontratti.inbox.entity.contratti.CentriCostoEntry;
import it.appaltiecontratti.inbox.entity.contratti.DettaglioAttoEntry;
import it.appaltiecontratti.inbox.entity.contratti.GaraFullEntry;
import it.appaltiecontratti.inbox.entity.contratti.ImpresaAggiudicatariaAttoEntry;
import it.appaltiecontratti.inbox.entity.contratti.ImpresaAggiudicatariaEntry;
import it.appaltiecontratti.inbox.entity.contratti.LottoDynamicForPublish;
import it.appaltiecontratti.inbox.entity.contratti.LottoFullEntry;
import it.appaltiecontratti.inbox.entity.contratti.PubblicitaGaraEntry;
import it.appaltiecontratti.inbox.entity.contratti.RupEntry;

@Mapper
public interface ContrattiMapper {

	@Select("select codgara as codGara, num_pubb as numPubb, datapubb as dataPubb, descriz, datascad as dataScad, data_decreto as dataDecreto, data_provvedimento as dataProvvedimento, num_provvedimento as numProvvedimento, data_stipula as dataStipula, num_repertorio as numRepertorio, perc_ribasso_agg as percRibassoAgg, perc_off_aumento as percOffAumento, importo_aggiudicazione as importoAggiudicazione, data_verb_aggiudicazione as dataVerbAggiudicazione, id_generato as idGenerato, id_ricevuto as idRicevuto, url_committente as urlCommittente, url_eproc as urlEproc, tipdoc as tipDoc from W9PUBBLICAZIONI where codgara = #{codiceGara} and num_pubb = #{numPubb}")
	public DettaglioAttoEntry getDettaglioAtto(@Param("codiceGara") Long codiceGara, @Param("numPubb") Long numPubb);

	@Select("select codgara as codGara, num_pubb as numPubb, num_aggi as numAggi, id_tipoagg as idTipoAgg, ruolo as ruolo, codimp as codImpresa, id_gruppo as idGruppo from esiti_aggiudicatari where codgara = #{codGara} and num_pubb = #{numPubb}")
	public List<ImpresaAggiudicatariaAttoEntry> getImpreseAggiudicatarieAtto(@Param("codGara") Long codGara,
			@Param("numPubb") Long numPubb);

	@Select("select codgara as codGara, num_pubb as numPubb, numdoc as numDoc, titolo, file_allegato as fileAllegato, url from W9DOCGARA where codgara = #{codiceGara} and num_pubb = #{numPubb}")
	public List<AttoDocument> getAttoDocuments(@Param("codiceGara") Long codiceGara, @Param("numPubb") Long numPubb);

	@Select("select codgara as codGara, codlott as codLotto, num_appa as numAppa, num_aggi as numAggi, id_tipoagg as idTipoAgg, ruolo as ruolo, flag_avvalimento as flagAvvallamento, codimp as codImpresa, codimp_ausiliaria as codImpAus, id_gruppo as idGruppo from w9aggi where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa} and num_aggi = #{numAggi}")
	public ImpresaAggiudicatariaEntry getImpresaAggiudicataria(@Param("codGara") Long codGara,
			@Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa, @Param("numAggi") Long numAggi);

	@Select("select codimp as codiceImpresa, nomest as ragioneSociale, nomimp as nomImp, cfimp as codiceFiscale, pivimp as partitaIva, locimp as comune, natgiui as formaGiuridica, ncciaa as numeroIscrizione,\r\n"
			+ "	ninail as codiceInail, albtec as numeroIscrizioneAlbo, indimp as indirizzo, nciimp as numeroCivico, proimp as provincia, capimp as cap, nazimp as nazione, \r\n"
			+ "	telcel as telefono, emai2ip as email, emailpec as pec, faximp as fax  from IMPR where codimp = #{codiceImpresa}")
	public ImpresaEntry getImpresa(@Param("codiceImpresa") String codiceImpresa);

	@Select("select cfein from uffint where codein =#{stazioneappaltante}")
	public String getCFSAByCode(@Param("stazioneappaltante") String stazioneappaltante);

	@Select("select CODGARA as codgara,  OGGETTO as oggetto,  SITUAZIONE as situazione,  PROV_DATO as provenienzaDato,  IDAVGARA as identificativoGara,  FLAG_ENTE_SPECIALE as tipoSettore,  ID_MODO_INDIZIONE as modalitaIndizione,  TIPO_APP as tipoApp,  CIG_ACCQUADRO as cigQuadro,  SOMMA_URGENZA as sommaUrgenza,  IMPORTO_GARA as importoGara,  NLOTTI as numLotti,  DATA_PUBBLICAZIONE as dataPubblicazione,  RUP as codiceTecnico,  RIC_ALLUV as ricostruzioneAlluvione,  CAM as dispArtDlgs,  SISMA as sisma,  VER_SIMOG as versioneSimog,  CODEIN as codiceStazioneAppaltante,  IDCC as idCentroDiCosto,  IDUFFICIO as idUfficio,  INDSEDE as indirizzoSede,  COMSEDE as comuneSede,  PROSEDE as provinciaSede,  DURATA_ACCQUADRO as durataAcquadro,  FLAG_SA_AGENTE as flagSaAgente,  ID_TIPOLOGIA_SA as tipologiaStazioneAppaltante,  DENOM_SA_AGENTE as denomSoggStazioneAppaltante,  CF_SA_AGENTE as cfAgenteStazioneAppaltante,  TIPOLOGIA_PROCEDURA as tipologiaProcedura,  FLAG_CENTRALE_STIPULA as flagStipula, dscade as dataScadenza, altre_sa as altreSA, syscon as syscon, id_ricevuto as idRicevuto from w9gara where codgara = #{codGara}")
	public GaraFullEntry dettaglioGaraCompleto(@Param("codGara") Long codGara);

	@Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE codtec = #{id}")
	public RupEntry getTecnico(@Param("id") String id);

	@Select("select nomein from uffint where codein = #{stazioneappaltante}")
	public String getNominatioSa(@Param("stazioneappaltante") String stazioneappaltante);

	@Select("select denom from uffici where id = #{idUfficio}")
	public String getNomeUfficio(@Param("idUfficio") Long idUfficio);

	@Select("select idcentro as id, codcentro as codiceCDC, denomcentro as nominativoCDC, note as note, tipologia as tipologia from CENTRICOSTO where idcentro = #{idCdc} and codein = #{stazioneappaltante}")
	public CentriCostoEntry getCentroCosto(@Param("idCdc") Long idCdc,
			@Param("stazioneappaltante") String stazioneappaltante);

	@Select("select CODGARA as codGara, CODLOTT as codLotto, CIG as cig, OGGETTO as oggetto, CUIINT as cui, URL_EPROC as urlEproc, SITUAZIONE as situazione, NLOTTO as numLotto, COMCON as contrattoPrec, DESCOM as motivoCompletamento, CIGCOM as cigContratto, TIPO_CONTRATTO as tipologia, FLAG_ENTE_SPECIALE as tipoSettore, ART_E1 as contrattoEscluso, ID_SCELTA_CONTRAENTE as sceltaContraente, ID_SCELTA_CONTRAENTE_50 as sceltaContraenteLgs, ID_MODO_GARA as criteriAggiudicazione, ID_SCHEDA_LOCALE as idSchedalocale, RUP as rup, IMPORTO_LOTTO as importoNetto, IMPORTO_ATTUAZIONE_SICUREZZA as importoSicurezza, IMPORTO_TOT as importoTotale, CUPESENTE as esenteCup, CUP as cup, CPV as cpv, MANOD as manodopera, ID_CATEGORIA_PREVALENTE as categoriaPrev, CLASCAT as classeCategoriaPrev, LUOGO_ISTAT as luogoIstat, LUOGO_NUTS as luogoNuts, ID_TIPO_PRESTAZIONE as prestazioneComprese, EXSOTTOSOGLIA as exsottosoglia, codint as codiceInterno, art_e2 as contrattoEscluso161718, somma_urgenza as sommaUrgenza FROM W9LOTT where codgara = #{codiceGara}")
	public List<LottoFullEntry> getFullLottiGara(@Param("codiceGara") Long codiceGara);

	@Select("SELECT tab1tip codice, tab1desc descrizione, tab1arc archiviato from tab1 where tab1cod= 'W3019' order by tab1nord")
	public List<TabellatoEntry> getModalitaAcquisizioneTab();

	@Select("select id_appalto from W9APPAFORN  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public List<Long> getModalitaAcquisizioneLotto(@Param("codiceGara") Long codiceGara,
			@Param("codiceLotto") Long codiceLotto);

	@Select("select c.cpv as codCpv, t.cpvdesc as descCpv from W9CPV c, TABCPV t where c.cpv = t.cpvcod4 and c.codgara = #{codiceGara} and c.codlott = #{codiceLotto}")
	public List<CPVSecondario> getCpvSecondariLotto(@Param("codiceGara") Long codiceGara,
			@Param("codiceLotto") Long codiceLotto);

	@Select("select categoria as categoria, clascat as classe, scorporabile as scorporabile, subappaltabile as subappaltabile, num_cate as num from W9LOTTCATE where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public List<CategoriaLotto> getCategorieLotto(@Param("codiceGara") Long codiceGara,
			@Param("codiceLotto") Long codiceLotto);

	@Select("select id_appalto as numAppalto, num_appaf as num from W9APPAFORN  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public List<LottoDynamicForPublish> getFullModalitaAcquisizioneLotto(@Param("codiceGara") Long codiceGara,
			@Param("codiceLotto") Long codiceLotto);

	@Select("SELECT tab1tip codice, tab1desc descrizione, tab1arc archiviato from tab1 where tab1cod= 'W3006' and tab1tip < 33 order by tab1nord")
	public List<TabellatoEntry> getCondizioniRicorsoTab();

	@Select("select id_condizione from W9COND  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public List<Long> getModalitaCondizioniRicorsoLotto(@Param("codiceGara") Long codiceGara,
			@Param("codiceLotto") Long codiceLotto);

	@Select("SELECT tab1tip codice, tab1desc descrizione, tab1arc archiviato from tab1 where tab1cod= 'W3002' order by tab1nord, tab1tip")
	public List<TabellatoEntry> getModalitaTipologiaLavoroTab();

	@Select("select id_appalto from W9APPALAV  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public List<Long> getModalitaTipologiaLavoroLotto(@Param("codiceGara") Long codiceGara,
			@Param("codiceLotto") Long codiceLotto);

	@Select("select codgara as codiceGara, DATA_GUCE as gazzettaUffEuropea, DATA_GURI as gazzettaUffItaliana, DATA_ALBO as alboPretorioComuni, QUOTIDIANI_NAZ as numQuotidianiNazionali,  QUOTIDIANI_REG as numQuotidianiLocali, PROFILO_COMMITTENTE as profiloCommittente, SITO_MINISTERO_INF_TRASP as sitoMinisteriInfr, SITO_OSSERVATORIO_CP as sitoOsservatorioContratti, DATA_BORE as dataBollettino, PERIODICI as numPeriodici from W9PUBB where codgara = #{codiceGara}")
	public PubblicitaGaraEntry getPubblicitaGara(@Param("codiceGara") Long codiceGara);

	@Select("select num_appal as num, id_appalto as numAppalto  from W9APPALAV  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public List<LottoDynamicForPublish> getFullTipologiaLavoriLotto(@Param("codiceGara") Long codiceGara,
			@Param("codiceLotto") Long codiceLotto);

	@Select("select num_cond as num, id_condizione as numAppalto  from W9COND  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public List<LottoDynamicForPublish> getFullCondizioniRicorsoLotto(@Param("codiceGara") Long codiceGara,
			@Param("codiceLotto") Long codiceLotto);

	@Update("UPDATE W9PUBBLICAZIONI SET ID_RICEVUTO = #{idRicevuto} WHERE CODGARA = #{codiceGara} AND NUM_PUBB = #{numPubb}")
	public void updateIdRicevutoAtto(@Param("idRicevuto") Long idRicevuto, @Param("codiceGara") Long codiceGara,
			@Param("numPubb") Long numPubb);

	@Update("UPDATE W9GARA SET ID_RICEVUTO = #{idRicevuto} WHERE CODGARA = #{codiceGara}")
	public void updateIdRicevutoGara(@Param("idRicevuto") Long idRicevuto, @Param("codiceGara") Long codiceGara);
	
	@Select("SELECT tab2tip FROM TAB2 WHERE TAB2COD = 'W3z12' AND TAB2d1 = #{nazioneNumber}")
	public String getCodiceNazioneByNumber(@Param("nazioneNumber") final Long nazioneNumber);
}
