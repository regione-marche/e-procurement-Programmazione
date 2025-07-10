package it.appaltiecontratti.monitoraggiocontratti.contratti.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.AllegatoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.*;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.*;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.AttoDocument;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CPVSecondario;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CategoriaLotto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.CondizioneAtto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.W9AssociazioneCampi;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.AttiPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.CentriCostoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.CigIdAtto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DateVersionPcpEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DettaglioAttoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DocumentoFaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAccordoBonarioEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAdesioneAccordoQuadroEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAggiudicazioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAggiudicazioneSempEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAvanzamentoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseAvanzamentoSempEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseCantieriEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseCantieriImpEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseCollaudoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseConclusioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseConclusioneSempEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseEntry;
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
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FinanziamentiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FlussiAtto;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FlussiGara;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FlussoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FullDettaglioAttoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FullFlussiFase;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraBaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraFullEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaAggiudicatariaAttoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaAggiudicatariaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ImpresaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.IncarichiProfEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.IndicatoreEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.IndicatorePremEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LegaleRappresentanteEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.ListaSingoloAttoInfoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LoaderAppaltoUsrEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LogEventiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoBaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoDynamicForPublish;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoFullEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoIndEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.MisureAggiuntiveSicurezzaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.PubblicitaGaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.RecordFaseImpreseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.RupEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.SchedaSicurezzaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.TabellatoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.TabellatoFaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.TabellatoIstatEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.UffEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.W9AssociazioneCampiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.W9RegInviiPcpEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.DatiIdSchedeW9FasiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.FinanziamentiPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.ImpresaAggiudicatariaPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.ImpresaFasePubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.IncarichiProfPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.LegaleRappresentantePubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.pubblicazione.fasi.RupPubbEntry;
import it.appaltiecontratti.monitoraggiocontratti.simog.form.ChiaveConfigurazione;

/**
 * DAO Interface per l'estrazione delle informazioni relative alla gestione di
 * esiti, gare e contratti
 * 
 * @author Michele.DiNapoli
 */
@Mapper
public interface ContrattiMapper {


	class PureSqlProvider {
		public String sql(String sql) {
			return sql;
		}

		public String sqlObject(String sql) {
			return sql;
		}

		public String count(String from) {
			return "SELECT count(*) FROM " + from;
		}
	}

	@SelectProvider(type = PureSqlProvider.class, method = "sql")
	public Integer execute(String query);
	// GARE

	public int countSearchGare(GareSearchForm form);

	public List<GaraEntry> searchGareOracle(GareSearchForm form, RowBounds rowBounds);

	public List<GaraEntry> searchGare(GareSearchForm form, RowBounds rowBounds);

	public List<GaraEntry> searchGareAll(GareSearchForm form);

	public List<GaraEntry> searchGareAllOracle(GareSearchForm form);

	public List<RicercaSchedeTrasmessePcpEntry> getListaSchedeTrasmessePcp(SchedeTrasmessePcpForm form, RowBounds rowBounds);

	public int countSearchListaSchedeTrasmessePcp(SchedeTrasmessePcpForm form, RowBounds rowBounds);

	@Select("select IDAPPALTO as idAppalto, CODGARA as codgara,  OGGETTO as oggetto,  SITUAZIONE as situazione,  PROV_DATO as provenienzaDato,  IDAVGARA as identificativoGara,  FLAG_ENTE_SPECIALE as tipoSettore,  ID_MODO_INDIZIONE as modalitaIndizione,  TIPO_APP as tipoApp,  CIG_ACCQUADRO as cigQuadro,  SOMMA_URGENZA as sommaUrgenza,  IMPORTO_GARA as importoGara,  NLOTTI as numLotti,  DATA_PUBBLICAZIONE as dataPubblicazione,  RUP as codiceTecnico,  RIC_ALLUV as ricostruzioneAlluvione,  CAM as dispArtDlgs,  SISMA as sisma,  VER_SIMOG as versioneSimog,  CODEIN as codiceStazioneAppaltante,  IDCC as idCentroDiCosto,  IDUFFICIO as idUfficio,  INDSEDE as indirizzoSede,  COMSEDE as comuneSede,  PROSEDE as provinciaSede,  DURATA_ACCQUADRO as durataAcquadro,  FLAG_SA_AGENTE as flagSaAgente,  ID_TIPOLOGIA_SA as tipologiaStazioneAppaltante,  DENOM_SA_AGENTE as denomSoggStazioneAppaltante,  CF_SA_AGENTE as cfAgenteStazioneAppaltante,  TIPOLOGIA_PROCEDURA as tipologiaProcedura,  FLAG_CENTRALE_STIPULA as flagStipula, id_ricevuto as idRicevuto, syscon, id_f_delegate as idFDelegate, DATA_LETTERA_INVITO as dataLetteraInvito, DATA_CREAZIONE as dataCreazione, ALLEGATO_IX  as modalitaIndizioneAllegato9, GARA_URGENZA  as motivoSommaUrgenza, drp, DATA_SCADENZA_PRES_OFFERTA as dataScadPresentazioneOfferta, urldocumentazione as urlDocumentazione from w9gara where codgara = #{codGara}")
	public GaraEntry dettaglioGara(@Param("codGara") Long codGara);

	@Select("select IDAPPALTO as idAppalto, CODGARA as codgara,  OGGETTO as oggetto,  SITUAZIONE as situazione,  PROV_DATO as provenienzaDato,  IDAVGARA as identificativoGara,  FLAG_ENTE_SPECIALE as tipoSettore,  ID_MODO_INDIZIONE as modalitaIndizione,  TIPO_APP as tipoApp,  CIG_ACCQUADRO as cigQuadro,  SOMMA_URGENZA as sommaUrgenza,  IMPORTO_GARA as importoGara,  NLOTTI as numLotti,  DATA_PUBBLICAZIONE as dataPubblicazione,  RUP as codiceTecnico,  RIC_ALLUV as ricostruzioneAlluvione,  CAM as dispArtDlgs,  SISMA as sisma,  VER_SIMOG as versioneSimog,  CODEIN as codiceStazioneAppaltante,  IDCC as idCentroDiCosto,  IDUFFICIO as idUfficio,  INDSEDE as indirizzoSede,  COMSEDE as comuneSede,  PROSEDE as provinciaSede,  DURATA_ACCQUADRO as durataAcquadro,  FLAG_SA_AGENTE as flagSaAgente,  ID_TIPOLOGIA_SA as tipologiaStazioneAppaltante,  DENOM_SA_AGENTE as denomSoggStazioneAppaltante,  CF_SA_AGENTE as cfAgenteStazioneAppaltante,  TIPOLOGIA_PROCEDURA as tipologiaProcedura,  FLAG_CENTRALE_STIPULA as flagStipula, id_ricevuto as idRicevuto, syscon, id_f_delegate as idFDelegate, DATA_LETTERA_INVITO as dataLetteraInvito, DATA_CREAZIONE as dataCreazione, ALLEGATO_IX  as modalitaIndizioneAllegato9, GARA_URGENZA  as motivoSommaUrgenza, drp from w9gara where upper(idappalto) = #{idAppalto}")
	public GaraEntry dettaglioGaraByIdAppalto(@Param("idAppalto") String idAppalto);

	@Select("select codgara from w9lott where cig = #{cig}")
	public List<Long> getCodgaraByCig(@Param("cig") String cig);

	@Select("select count(*) from w9gara where idappalto = #{idAppalto}")
	public Long countGaraByIdAppalto(@Param("idAppalto") String idAppalto);

	@Select("select CODGARA as codgara,  OGGETTO as oggetto,  SITUAZIONE as situazione,  PROV_DATO as provenienzaDato,  IDAVGARA as identificativoGara,  FLAG_ENTE_SPECIALE as tipoSettore,  ID_MODO_INDIZIONE as modalitaIndizione,  TIPO_APP as tipoApp,  CIG_ACCQUADRO as cigQuadro,  SOMMA_URGENZA as sommaUrgenza,  IMPORTO_GARA as importoGara,  NLOTTI as numLotti,  DATA_PUBBLICAZIONE as dataPubblicazione,  RUP as codiceTecnico,  RIC_ALLUV as ricostruzioneAlluvione,  CAM as dispArtDlgs,  SISMA as sisma,  VER_SIMOG as versioneSimog,  CODEIN as codiceStazioneAppaltante,  IDCC as idCentroDiCosto,  IDUFFICIO as idUfficio,  INDSEDE as indirizzoSede,  COMSEDE as comuneSede,  PROSEDE as provinciaSede,  DURATA_ACCQUADRO as durataAcquadro,  FLAG_SA_AGENTE as flagSaAgente,  ID_TIPOLOGIA_SA as tipologiaStazioneAppaltante,  DENOM_SA_AGENTE as denomSoggStazioneAppaltante,  CF_SA_AGENTE as cfAgenteStazioneAppaltante,  TIPOLOGIA_PROCEDURA as tipologiaProcedura,  FLAG_CENTRALE_STIPULA as flagStipula, dscade as dataScadenza, altre_sa as altreSA, syscon as syscon, id_ricevuto as idRicevuto, DATA_LETTERA_INVITO as dataLetteraInvito, DATA_CREAZIONE as dataCreazione, ID_F_DELEGATE as idFDelegate, ALLEGATO_IX  as modalitaIndizioneAllegato9, GARA_URGENZA  as motivoSommaUrgenza from w9gara where codgara = #{codGara}")
	public GaraFullEntry dettaglioGaraCompleto(@Param("codGara") Long codGara);

	@Update("update w9gara set FLAG_ENTE_SPECIALE = #{tipoSettore}, IMPORTO_GARA = #{importoGara}, RUP = #{codiceTecnico}, RIC_ALLUV = #{ricostruzioneAlluvione}, INDSEDE = #{indirizzoSede},  COMSEDE = #{comuneSede},  PROSEDE = #{provinciaSede}, FLAG_SA_AGENTE = #{flagSaAgente}, ID_TIPOLOGIA_SA = #{tipologiaStazioneAppaltante},  DENOM_SA_AGENTE = #{denomSoggStazioneAppaltante},  CF_SA_AGENTE = #{cfAgenteStazioneAppaltante},  TIPOLOGIA_PROCEDURA = #{tipologiaProcedura},  FLAG_CENTRALE_STIPULA = #{flagStipula}, TIPO_APP=#{tipoApp},IDUFFICIO=#{ufficio}, DURATA_ACCQUADRO = #{durataAcquadro}, DATA_LETTERA_INVITO = #{dataLetteraInvito} where codgara = #{codGara}")
	public void updateGara(GaraUpdateForm form);

	@Update("update w9gara set OGGETTO = #{oggetto}, FLAG_ENTE_SPECIALE = #{tipoSettore}, IMPORTO_GARA = #{importoGara}, RUP = #{codiceTecnico}, RIC_ALLUV = #{ricostruzioneAlluvione}, INDSEDE = #{indirizzoSede},  COMSEDE = #{comuneSede},  PROSEDE = #{provinciaSede}, FLAG_SA_AGENTE = #{flagSaAgente}, ID_TIPOLOGIA_SA = #{tipologiaStazioneAppaltante},  DENOM_SA_AGENTE = #{denomSoggStazioneAppaltante},  CF_SA_AGENTE = #{cfAgenteStazioneAppaltante},  TIPOLOGIA_PROCEDURA = #{tipologiaProcedura},  FLAG_CENTRALE_STIPULA = #{flagStipula}, TIPO_APP=#{tipoApp}, DURATA_ACCQUADRO = #{durataAcquadro}, IDUFFICIO = #{idUfficio} where codgara = #{codGara}")
	public void updateGaraSmartCig(GaraSmartCigUpdateForm form);

	@Insert(" insert into w9gara (CODGARA,  OGGETTO,  SITUAZIONE,  PROV_DATO,  IDAVGARA,  FLAG_ENTE_SPECIALE, ID_MODO_INDIZIONE,  TIPO_APP,  CIG_ACCQUADRO,  SOMMA_URGENZA,  IMPORTO_GARA,  NLOTTI,  DATA_PUBBLICAZIONE,  RUP,  RIC_ALLUV,  CAM,  SISMA,  VER_SIMOG,  CODEIN,  IDCC,  IDUFFICIO,  INDSEDE,  COMSEDE,  PROSEDE,  DURATA_ACCQUADRO,  FLAG_SA_AGENTE,  ID_TIPOLOGIA_SA,  DENOM_SA_AGENTE,  TIPOLOGIA_PROCEDURA,  FLAG_CENTRALE_STIPULA,  dscade,  altre_sa,  syscon, cf_sa_agente, IDAPPALTO, ID_F_DELEGATE, DRP, DATA_SCADENZA_PRES_OFFERTA, URLDOCUMENTAZIONE) values (#{codgara}, #{oggetto}, #{situazione}, #{provenienzaDato}, #{identificativoGara}, #{tipoSettore}, #{modalitaIndizione}, #{tipoApp}, #{cigQuadro}, #{sommaUrgenza}, #{importoGara}, #{numLotti}, #{dataPubblicazione}, #{codiceTecnico}, #{ricostruzioneAlluvione}, #{dispArtDlgs}, #{sisma}, #{versioneSimog}, #{codiceStazioneAppaltante}, #{idCentroDiCosto}, #{idUfficio}, #{indirizzoSede}, #{comuneSede}, #{provinciaSede}, #{durataAcquadro}, #{flagSaAgente}, #{tipologiaStazioneAppaltante}, #{denomSoggStazioneAppaltante}, #{tipologiaProcedura}, #{flagStipula}, #{dataScadenza}, #{altreSA}, #{syscon}, #{cfAgenteStazioneAppaltante},#{idAppalto}, #{idFDelegate},#{drp}, #{dataScadPresentazioneOfferta}, #{urlDocumentazione})")
	public void insertGara(GaraInsertForm form);

	@Insert(" update w9gara set OGGETTO = #{oggetto}, SITUAZIONE = #{situazione},IDAVGARA = #{identificativoGara}, FLAG_ENTE_SPECIALE = #{tipoSettore}, ID_MODO_INDIZIONE = #{modalitaIndizione}, TIPO_APP = #{tipoApp}, CIG_ACCQUADRO = #{cigQuadro}, SOMMA_URGENZA = #{sommaUrgenza},  IMPORTO_GARA = #{importoGara},  NLOTTI = #{numLotti},  DATA_PUBBLICAZIONE = #{dataPubblicazione},  RUP = #{codiceTecnico},  RIC_ALLUV =  #{ricostruzioneAlluvione},  CAM  =#{dispArtDlgs},  SISMA = #{sisma}, CODEIN = #{codiceStazioneAppaltante},  IDCC = #{idCentroDiCosto},  IDUFFICIO = #{idUfficio},  INDSEDE = #{indirizzoSede},  COMSEDE = #{comuneSede},  PROSEDE = #{provinciaSede},  DURATA_ACCQUADRO = #{durataAcquadro},  FLAG_SA_AGENTE = #{flagSaAgente},  ID_TIPOLOGIA_SA = #{tipologiaStazioneAppaltante},  DENOM_SA_AGENTE = #{denomSoggStazioneAppaltante},  TIPOLOGIA_PROCEDURA = #{tipologiaProcedura},  FLAG_CENTRALE_STIPULA = #{flagStipula},  dscade = #{dataScadenza},  altre_sa = #{altreSA},  syscon = #{syscon}, cf_sa_agente = #{cfAgenteStazioneAppaltante}, IDAPPALTO = #{idAppalto}, ID_F_DELEGATE = #{idFDelegate}, DRP = #{drp}, DATA_SCADENZA_PRES_OFFERTA = #{dataScadPresentazioneOfferta}, URLDOCUMENTAZIONE = #{urlDocumentazione}  where codgara = #{codgara}")
	public void updateGaraPcp(GaraInsertForm form);

	@Delete("delete from w9gara where codgara = #{codGara}")
	public void deleteGara(@Param("codGara") Long codGara);

	@Update("update W9GARA set SITUAZIONE = #{situazione} where codgara = #{codGara}")
	public void updateSituazioneGara(@Param("codGara") Long codGara, @Param("situazione") Long situazione);

	@Update("update W9GARA set rup = #{codiceTecnico} where codgara = #{codGara}")
	public void updateRupGara(@Param("codGara") Long codGara, @Param("codiceTecnico") String codiceTecnico);

	@Select("select count(*) from W9FLUSSI where area = 2 and key01 = #{codGara} and key03 = 988 and tinvio2 > 0")
	public Long countPubblicazioniAnagraficaGara(@Param("codGara") Long codGara);

	@Update("update W9GARA set IMPORTO_GARA = #{importo} where codgara = #{codGara}")
	public void updateImportoGara(@Param("codGara") Long codGara, @Param("importo") Double importo);

	@Update("update W9GARA set DATA_LETTERA_INVITO = #{dataLetteraInvito} where codgara = #{codGara}")
	public void updateDataLetteraInvitoGara(@Param("codGara") Long codGara, @Param("dataLetteraInvito") Date dataLetteraInvito);

	@Update("update W9GARA set drp = #{drp} where codgara = #{codGara}")
	public void updateDrpW9gara(@Param("codGara") Long codGara, @Param("drp") String drp);

	@Update("update W9GARA set drp = null where codgara = #{codGara}")
	public void removeDrpW9gara(@Param("codGara") Long codGara);


	// LOTTI

	public int countSearchLotti(LottoSearchForm searchForm);

	public List<LottoBaseEntry> searchLotti(LottoSearchForm searchForm, RowBounds rowBounds);

	@Select("select codgara as codGara, codlott as codLotto, cig as cig, oggetto as oggetto, situazione as situazione, tipo_contratto as tipologia, importo_lotto as importoNetto, CIG_MASTER_ML as masterCig from W9LOTT where codgara = #{codiceGara}")
	public List<LottoBaseEntry> getLottiGara(@Param("codiceGara") Long codiceGara);

	@Select("select codgara as codGara, codlott as codLotto, cig as cig, oggetto as oggetto, situazione as situazione, tipo_contratto as tipologia, importo_lotto as importoNetto, CIG_MASTER_ML as masterCig from W9LOTT where codgara = #{codiceGara} and UPPER(cig) like #{occurrence} and CIG_MASTER_ML is null")
	public List<LottoBaseEntry> getMultiLottoOptions(@Param("codiceGara") Long codiceGara,
													 @Param("occurrence") String occurrence);

	@Select("select count(*) from W9LOTT where codgara = #{codGara}")
	public Integer countLottiGara(@Param("codGara") Long codGara);

	@Select("select CODGARA as codGara, CODLOTT as codLotto, CIG as cig, OGGETTO as oggetto, CUIINT as cui, URL_EPROC as urlEproc, SITUAZIONE as situazione, NLOTTO as numLotto,  TIPO_CONTRATTO as tipologia, FLAG_ENTE_SPECIALE as tipoSettore, ART_E1 as contrattoEscluso, ID_SCELTA_CONTRAENTE as sceltaContraente, ID_SCELTA_CONTRAENTE_50 as sceltaContraenteLgs, ID_MODO_GARA as criteriAggiudicazione, ID_SCHEDA_LOCALE as idSchedalocale, RUP as rup, IMPORTO_LOTTO as importoNetto, IMPORTO_ATTUAZIONE_SICUREZZA as importoSicurezza, IMPORTO_TOT as importoTotale, CUPESENTE as esenteCup, CUP as cup, CPV as cpv, MANOD as manodopera, ID_CATEGORIA_PREVALENTE as categoriaPrev, CLASCAT as classeCategoriaPrev, LUOGO_ISTAT as luogoIstat, LUOGO_NUTS as luogoNuts, ID_TIPO_PRESTAZIONE as prestazioneComprese, EXSOTTOSOGLIA as exsottosoglia, art_e1 as artE1, art_e2 as contrattoEscluso161718, CIG_MASTER_ML as masterCig, CONTRATTO_ESCLUSO_ALLEGGERITO as contrattoEsclusoAlleggerito, ESCLUSIONE_REGIME_SPECIALE as esclusioneRegimeSpeciale, DEROGA_QUALIFICAZIONE_SA as qualificazioneSa, CATEGORIA_MERC as categoriaMerceologica, FLAG_PREVEDE_RIP as flagPrevedeRip, DURATA_RINNOVI as durataRinnovi, MOTIVO_COLLEGAMENTO as motivoCollegamento, CIG_ORIGINE_RIP as cigOrigineRip, FLAG_PNRR_PNC as flagPnrrPnc, FLAG_PREVISIONE_QUOTA as flagPrevisioneQuota, FLAG_MISURE_PREMIALI as flagMisurePreliminari, DATA_SCADENZA_PAGAMENTI as dataScadenzaPagamenti FROM W9LOTT where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public LottoEntry getDettaglioLotto(@Param("codiceGara") Long codiceGara, @Param("codiceLotto") Long codiceLotto);

	@Select("select CODGARA as codGara, CODLOTT as codLotto, CIG as cig, OGGETTO as oggetto, CUIINT as cui, URL_EPROC as urlEproc, SITUAZIONE as situazione, NLOTTO as numLotto,  TIPO_CONTRATTO as tipologia, FLAG_ENTE_SPECIALE as tipoSettore, ART_E1 as contrattoEscluso, ID_SCELTA_CONTRAENTE as sceltaContraente, ID_SCELTA_CONTRAENTE_50 as sceltaContraenteLgs, ID_MODO_GARA as criteriAggiudicazione, ID_SCHEDA_LOCALE as idSchedalocale, RUP as rup, IMPORTO_LOTTO as importoNetto, IMPORTO_ATTUAZIONE_SICUREZZA as importoSicurezza, IMPORTO_TOT as importoTotale, CUPESENTE as esenteCup, CUP as cup, CPV as cpv, MANOD as manodopera, ID_CATEGORIA_PREVALENTE as categoriaPrev, CLASCAT as classeCategoriaPrev, LUOGO_ISTAT as luogoIstat, LUOGO_NUTS as luogoNuts, ID_TIPO_PRESTAZIONE as prestazioneComprese, EXSOTTOSOGLIA as exsottosoglia, art_e1 as artE1, art_e2 as contrattoEscluso161718, CIG_MASTER_ML as masterCig, CONTRATTO_ESCLUSO_ALLEGGERITO as contrattoEsclusoAlleggerito, ESCLUSIONE_REGIME_SPECIALE as esclusioneRegimeSpeciale, DEROGA_QUALIFICAZIONE_SA as qualificazioneSa, CATEGORIA_MERC as categoriaMerceologica, FLAG_PREVEDE_RIP as flagPrevedeRip, DURATA_RINNOVI as durataRinnovi, MOTIVO_COLLEGAMENTO as motivoCollegamento, CIG_ORIGINE_RIP as cigOrigineRip, FLAG_PNRR_PNC as flagPnrrPnc, FLAG_PREVISIONE_QUOTA as flagPrevisioneQuota, FLAG_MISURE_PREMIALI as flagMisurePreliminari, DATA_SCADENZA_PAGAMENTI as dataScadenzaPagamenti FROM W9LOTT where cig = #{cig} ")
	public LottoEntry getDettaglioLottoByCig(@Param("cig") String cig);

	@Select("select codgara as codGara, codlott as codLotto, cig as cig, oggetto as oggetto, situazione as situazione, tipo_contratto as tipologia, importo_lotto as importoNetto, CIG_MASTER_ML as masterCig from W9LOTT where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public LottoEntry getBaseDettaglioLotto(@Param("codiceGara") Long codiceGara,
											@Param("codiceLotto") Long codiceLotto);

	@Select("select CODGARA as codGara, CODLOTT as codLotto, CIG as cig, OGGETTO as oggetto, CUIINT as cui, URL_EPROC as urlEproc, SITUAZIONE as situazione, NLOTTO as numLotto, COMCON as contrattoPrec, DESCOM as motivoCompletamento, CIGCOM as cigContratto, TIPO_CONTRATTO as tipologia, FLAG_ENTE_SPECIALE as tipoSettore, ART_E1 as contrattoEscluso, ID_SCELTA_CONTRAENTE as sceltaContraente, ID_SCELTA_CONTRAENTE_50 as sceltaContraenteLgs, ID_MODO_GARA as criteriAggiudicazione, ID_SCHEDA_LOCALE as idSchedalocale, ID_SCHEDA_SIMOG as idSchedaSimog, RUP as rup, IMPORTO_LOTTO as importoNetto, IMPORTO_ATTUAZIONE_SICUREZZA as importoSicurezza, IMPORTO_TOT as importoTotale, CUPESENTE as esenteCup, CUP as cup, CPV as cpv, MANOD as manodopera, ID_CATEGORIA_PREVALENTE as categoriaPrev, CLASCAT as classeCategoriaPrev, LUOGO_ISTAT as luogoIstat, LUOGO_NUTS as luogoNuts, ID_TIPO_PRESTAZIONE as prestazioneComprese, EXSOTTOSOGLIA as exsottosoglia, codint as codiceInterno, art_e2 as contrattoEscluso161718, somma_urgenza as sommaUrgenza, cig_master_ml as masterCig, CONTRATTO_ESCLUSO_ALLEGGERITO as contrattoEsclusoAlleggerito, ESCLUSIONE_REGIME_SPECIALE as esclusioneRegimeSpeciale, DEROGA_QUALIFICAZIONE_SA as qualificazioneSa, CATEGORIA_MERC as categoriaMerceologica, FLAG_PREVEDE_RIP as flagPrevedeRip, DURATA_RINNOVI as durataRinnovi, MOTIVO_COLLEGAMENTO as motivoCollegamento, CIG_ORIGINE_RIP as cigOrigineRip, FLAG_PNRR_PNC as flagPnrrPnc, FLAG_PREVISIONE_QUOTA as flagPrevisioneQuota, FLAG_MISURE_PREMIALI as flagMisurePreliminari, DATA_SCADENZA_PAGAMENTI as dataScadenzaPagamenti FROM W9LOTT where codgara = #{codiceGara}")
	public List<LottoFullEntry> getFullLottiGara(@Param("codiceGara") Long codiceGara);

	@Update("update W9LOTT set CIG = #{cig}, OGGETTO = #{oggetto}, CUIINT = #{cui}, URL_EPROC = #{urlEproc}, SITUAZIONE = #{situazione}, NLOTTO = #{numLotto},  TIPO_CONTRATTO = #{tipologia}, FLAG_ENTE_SPECIALE = #{tipoSettore}, ART_E1 = #{contrattoEscluso}, ID_SCELTA_CONTRAENTE = #{sceltaContraente}, ID_SCELTA_CONTRAENTE_50 = #{sceltaContraenteLgs}, ID_MODO_GARA = #{criteriAggiudicazione}, ID_SCHEDA_LOCALE = #{idSchedalocale}, RUP = #{rup}, IMPORTO_LOTTO = #{importoNetto}, IMPORTO_ATTUAZIONE_SICUREZZA = #{importoSicurezza}, IMPORTO_TOT = #{importoTotale}, CUPESENTE = #{esenteCup}, CPV = #{cpv}, MANOD = #{manodopera}, ID_CATEGORIA_PREVALENTE = #{categoriaPrev}, CLASCAT = #{classeCategoriaPrev}, LUOGO_ISTAT = #{luogoIstat}, LUOGO_NUTS = #{luogoNuts}, ID_TIPO_PRESTAZIONE = #{prestazioneComprese}, EXSOTTOSOGLIA = #{exsottosoglia} where codgara = #{codGara} and codlott = #{codLotto}")
	public void updateLotto(LottoUpdateForm form);

	@Update("update W9LOTT set CIG = #{cig}, OGGETTO = #{oggetto}, CUIINT = #{cui}, URL_EPROC = #{urlEproc}, SITUAZIONE = #{situazione}, NLOTTO = #{numLotto},  TIPO_CONTRATTO = #{tipologia}, FLAG_ENTE_SPECIALE = #{tipoSettore}, ART_E1 = #{contrattoEscluso}, ID_SCELTA_CONTRAENTE = #{sceltaContraente}, ID_SCELTA_CONTRAENTE_50 = #{sceltaContraenteLgs}, ID_MODO_GARA = #{criteriAggiudicazione}, ID_SCHEDA_LOCALE = #{idSchedalocale}, RUP = #{rup}, CUP = #{cup}, IMPORTO_LOTTO = #{importoNetto}, IMPORTO_ATTUAZIONE_SICUREZZA = #{importoSicurezza}, IMPORTO_TOT = #{importoTotale}, CUPESENTE = #{esenteCup}, CPV = #{cpv}, MANOD = #{manodopera}, ID_CATEGORIA_PREVALENTE = #{categoriaPrev}, CLASCAT = #{classeCategoriaPrev}, LUOGO_ISTAT = #{luogoIstat}, LUOGO_NUTS = #{luogoNuts}, ID_TIPO_PRESTAZIONE = #{prestazioneComprese}, EXSOTTOSOGLIA = #{exsottosoglia} where codgara = #{codGara} and codlott = #{codLotto}")
	public void updateLottoSmartCig(LottoInsertForm form);

	@Update("update W9LOTT set SITUAZIONE = #{situazione} where codgara = #{codGara} and codlott = #{codLotto}")
	public void updateSituazioneLotto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
									  @Param("situazione") Long situazione);

	@Delete("delete from w9lott where codgara = #{codGara} and codlott = #{codLotto}")
	public void deleteLotto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Delete("delete from w9lott where codgara = #{codGara}")
	public void deleteLotti(@Param("codGara") Long codGara);

	@Update("update w9lott set cup = #{cup} where codgara = #{codGara} and codlott = #{codLotto}")
	public void updateCup(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("cup") String cup);

	@Update("update w9lott set cupesente = '2' where codgara = #{codGara} and codlott = #{codLotto}")
	public void updateEsenteCupSNo(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Update("update w9lott set importo_lotto = #{baseAsta}, importo_tot = #{baseAsta} + importo_attuazione_sicurezza where codgara = #{codGara} and codlott = #{codLotto}")
	public void updateValBaseAsta(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("baseAsta") Double baseAsta);

	@Select("select num_appa from w9iniz where codcontratto = #{codcontratto} and codgara = #{codGara} and codlott = #{codLotto}")
	public List<Long> getNumAppaFromCodContratto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("codcontratto") String codcontratto);

	@Update("update w9appa set importo_aggiudicazione = #{importo} where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa}")
	public void updateimportoAggiudicazione(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa, @Param("importo") Double importo);

	@Insert("insert into w9lott (CODGARA, CODLOTT, CIG, OGGETTO, CUIINT, URL_EPROC, SITUAZIONE, NLOTTO, TIPO_CONTRATTO, FLAG_ENTE_SPECIALE, ART_E1, ID_SCELTA_CONTRAENTE, ID_SCELTA_CONTRAENTE_50, ID_MODO_GARA, ID_SCHEDA_LOCALE, RUP, IMPORTO_LOTTO, IMPORTO_ATTUAZIONE_SICUREZZA, IMPORTO_TOT, CUPESENTE, CUP, CPV, MANOD, ID_CATEGORIA_PREVALENTE, CLASCAT, LUOGO_ISTAT, LUOGO_NUTS, ID_TIPO_PRESTAZIONE, EXSOTTOSOGLIA, data_consulta_gara, LOTIDENTIFIER, DAEXPORT, CATEGORIA_MERC) values (#{codGara}, #{codLotto}, #{cig}, #{oggetto}, #{cui}, #{urlEproc}, #{situazione}, #{numLotto}, #{tipologia}, #{tipoSettore}, #{contrattoEscluso}, #{sceltaContraente}, #{sceltaContraenteLgs}, #{criteriAggiudicazione}, #{idSchedalocale}, #{rup}, #{importoNetto}, #{importoSicurezza}, #{importoTotale}, #{esenteCup}, #{cup}, #{cpv}, #{manodopera}, #{categoriaPrev}, #{classeCategoriaPrev}, #{luogoIstat}, #{luogoNuts}, #{prestazioneComprese}, #{exsottosoglia}, #{dataConsultaGara}, #{lotIdentifier}, #{daExport},#{categoriaMerceologica})")
	public void insertLotto(LottoInsertForm form);

	@Update("update W9LOTT set CIG = #{cig}, OGGETTO = #{oggetto}, CUIINT = #{cui}, URL_EPROC = #{urlEproc}, SITUAZIONE = #{situazione}, NLOTTO = #{numLotto},  TIPO_CONTRATTO = #{tipologia}, FLAG_ENTE_SPECIALE = #{tipoSettore}, ART_E1 = #{contrattoEscluso}, ID_SCELTA_CONTRAENTE = #{sceltaContraente}, ID_SCELTA_CONTRAENTE_50 = #{sceltaContraenteLgs}, ID_MODO_GARA = #{criteriAggiudicazione}, ID_SCHEDA_LOCALE = #{idSchedalocale}, RUP = #{rup}, IMPORTO_LOTTO = #{importoNetto}, IMPORTO_ATTUAZIONE_SICUREZZA = #{importoSicurezza}, IMPORTO_TOT = #{importoTotale}, CUPESENTE = #{esenteCup}, CPV = #{cpv}, MANOD = #{manodopera}, ID_CATEGORIA_PREVALENTE = #{categoriaPrev}, CLASCAT = #{classeCategoriaPrev}, LUOGO_ISTAT = #{luogoIstat}, LUOGO_NUTS = #{luogoNuts}, ID_TIPO_PRESTAZIONE = #{prestazioneComprese}, EXSOTTOSOGLIA = #{exsottosoglia} where codgara = #{codGara} and codlott = #{codLotto}")
	public void updateLottoPcp(LottoInsertForm form);

	@Insert("INSERT INTO w9lottcup (codgara, codlott, numcup, cup, dati_dipe) VALUES(#{codGara}, #{codLotto}, #{numcup}, #{cup}, #{datiDipe})")
	public void insertW9lottcup(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("numcup") Long numcup, @Param("cup") String cup, @Param("datiDipe") String datiDipe);

	@Delete("delete from w9lottcup where codgara = #{codGara} and codlott = #{codLotto}")
	public void deleteW9LottoCup(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select CODAVCP from W9CODICI_AVCP where TABCOD= 'SC_W3005' and CODSITAT= #{codSitat}")
	public String getIdSceltaContraente(@Param("codSitat") String codSitat);

	@Select("SELECT tab1tip codice, tab1desc descrizione, tab1arc archiviato from tab1 where tab1cod= 'W3019' order by tab1nord")
	public List<TabellatoEntry> getModalitaAcquisizioneTab();

	@Select("select L.CODLOTT from W9LOTT L, W9GARA G where L.CODGARA=#{codiceGara} and L.CODGARA=G.CODGARA ")
	public List<Long> getIdLottiGara(@Param("codiceGara") Long codiceGara);

	@Select("SELECT tab1tip codice, tab1desc descrizione, tab1arc archiviato from tab1 where tab1cod= 'W3002' order by tab1nord, tab1tip")
	public List<TabellatoEntry> getModalitaTipologiaLavoroTab();

	@Select("select id_appalto from W9APPAFORN  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public List<Long> getModalitaAcquisizioneLotto(@Param("codiceGara") Long codiceGara,
												   @Param("codiceLotto") Long codiceLotto);

	@Select("select id_appalto as numAppalto, num_appaf as num from W9APPAFORN  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public List<LottoDynamicForPublish> getFullModalitaAcquisizioneLotto(@Param("codiceGara") Long codiceGara,
																		 @Param("codiceLotto") Long codiceLotto);

	@Delete("delete from W9APPAFORN  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public void deleteModalitaAcquisizioneLotto(@Param("codiceGara") Long codiceGara,
												@Param("codiceLotto") Long codiceLotto);

	@Insert("insert into W9APPAFORN (codgara, codlott, num_appaf, id_appalto) values (#{codiceGara},#{codiceLotto},#{num},#{idAppalto})")
	public void insertModalitaAcquisizione(@Param("codiceGara") Long codiceGara, @Param("codiceLotto") Long codiceLotto,
										   @Param("num") Long num, @Param("idAppalto") Long idAppalto);

	@Select("select id_appalto from W9APPALAV  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public List<Long> getModalitaTipologiaLavoroLotto(@Param("codiceGara") Long codiceGara,
													  @Param("codiceLotto") Long codiceLotto);

	@Select("select num_appal as num, id_appalto as numAppalto  from W9APPALAV  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public List<LottoDynamicForPublish> getFullTipologiaLavoriLotto(@Param("codiceGara") Long codiceGara,
																	@Param("codiceLotto") Long codiceLotto);

	@Delete("delete from W9APPALAV  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public void deleteModalitaTipologiaLavoroLotto(@Param("codiceGara") Long codiceGara,
												   @Param("codiceLotto") Long codiceLotto);

	@Insert("insert into W9APPALAV (codgara, codlott, num_appal, id_appalto) values (#{codiceGara},#{codiceLotto},#{num},#{idAppalto})")
	public void insertModalitaTipologiaLavoroLotto(@Param("codiceGara") Long codiceGara,
												   @Param("codiceLotto") Long codiceLotto, @Param("num") Long num, @Param("idAppalto") Long idAppalto);

	@Select("SELECT tab1tip codice, tab1desc descrizione, tab1arc archiviato from tab1 where tab1cod= 'W3006' and tab1tip < 33 order by tab1nord")
	public List<TabellatoEntry> getCondizioniRicorsoTab();

	@Select("select id_condizione from W9COND  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public List<Long> getModalitaCondizioniRicorsoLotto(@Param("codiceGara") Long codiceGara,
														@Param("codiceLotto") Long codiceLotto);

	@Select("select num_cond as num, id_condizione as numAppalto  from W9COND  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public List<LottoDynamicForPublish> getFullCondizioniRicorsoLotto(@Param("codiceGara") Long codiceGara,
																	  @Param("codiceLotto") Long codiceLotto);

	@Delete("delete from W9COND  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public void deleteCondizioniRicorsoLotto(@Param("codiceGara") Long codiceGara,
											 @Param("codiceLotto") Long codiceLotto);

	@Insert("insert into W9COND (codgara, codlott, num_cond, id_condizione) values (#{codiceGara},#{codiceLotto},#{idCondizione},#{idCondizione})")
	public void insertCondizioniRicorsoLotto(@Param("codiceGara") Long codiceGara,
											 @Param("codiceLotto") Long codiceLotto, @Param("idCondizione") Long idCondizione);

	@Select("select c.cpv as codCpv, t.cpvdesc as descCpv from W9CPV c, TABCPV t where c.cpv = t.cpvcod4 and c.codgara = #{codiceGara} and c.codlott = #{codiceLotto}")
	public List<CPVSecondario> getCpvSecondariLotto(@Param("codiceGara") Long codiceGara,
													@Param("codiceLotto") Long codiceLotto);

	@Select("select cpvdesc from TABCPV where cpvcod4 = #{codCpv}")
	public String getCpvDesc(@Param("codCpv") String codCpv);

	@Select("select cpvdesc from TABCPV where cpvcod4 like #{codCpv}")
	public List<String> getCpvDescLike(@Param("codCpv") String codCpv);

	@Select("select cpvcod4 from TABCPV where cpvcod4 like #{codCpv}")
	public List<String> getCpvLike(@Param("codCpv") String codCpv);

	@Delete("delete from W9CPV  where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public void deleteCpvSecondariLotto(@Param("codiceGara") Long codiceGara, @Param("codiceLotto") Long codiceLotto);

	@Insert("insert into W9CPV (codgara, codlott, num_cpv, cpv) values (#{codiceGara},#{codiceLotto},#{numCpv},#{cpv})")
	public void insertCpvSecondariLotto(@Param("codiceGara") Long codiceGara, @Param("codiceLotto") Long codiceLotto,
										@Param("numCpv") Long numCpv, @Param("cpv") String cpv);

	@Select("select categoria as categoria, clascat as classe, scorporabile as scorporabile, subappaltabile as subappaltabile, num_cate as num from W9LOTTCATE where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public List<CategoriaLotto> getCategorieLotto(@Param("codiceGara") Long codiceGara,
												  @Param("codiceLotto") Long codiceLotto);

	@Delete("delete from W9LOTTCATE where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public void deleteCategorieLotto(@Param("codiceGara") Long codiceGara, @Param("codiceLotto") Long codiceLotto);

	@Insert("insert into W9LOTTCATE (codgara, codlott, num_cate, categoria, clascat, scorporabile, subappaltabile) values (#{codiceGara},#{codiceLotto},#{num},#{categoria},#{classe},#{scorporabile},#{subappaltabile})")
	public void insertCategorieLotto(@Param("codiceGara") Long codiceGara, @Param("codiceLotto") Long codiceLotto,
									 @Param("num") Long num, @Param("categoria") String categoria, @Param("classe") String classe,
									 @Param("scorporabile") String scorporabile, @Param("subappaltabile") String subappaltabile);

	@Select("select codgara as codGara, codlott as codLotto, fase_esecuzione as fase, num as progressivo, daexport as daExportDb from W9FASI join TAB1 t1 on t1.TAB1COD='W3020' where codgara = #{codgara} and codlott = #{codlott} and num_appa = #{numAppa} and t1.tab1tip =fase_esecuzione order by t1.tab1nord,num,t1.tab1tip")
	public List<FaseEntry> getFasiLotto(@Param("codgara") Long codgara, @Param("codlott") Long codlott,
										@Param("numAppa") Long numAppa);

	// nuova query

	@Select("select codgara as codGara, codlott as codLotto, fase_esecuzione as fase, num as progressivo, daexport as daExportDb, "
			+ " case when min(f1.key01) is null then 0 else 1 end as numFlussi,"
			+ " case when min(f2.key01) is null then 0 else 1 end as numRichCanc "
			+ " from W9FASI join TAB1 t1 on t1.TAB1COD='W3020' and t1.tab1tip = fase_esecuzione"
			+ " left join w9flussi f1 on f1.key01 = codgara and f1.key02=codlott and f1.key03=fase_esecuzione and f1.key04 = num and f1.tinvio2 >0 "
			+ " left join w9flussi f2 on f2.key01 = codgara and f2.key02=codlott and f2.key03=fase_esecuzione and f2.key04 = num and f2.TINVIO2=-1 "
			+ " where codgara = #{codgara} and codlott = #{codlott} and num_appa = #{numAppa} "
			+ " group by codgara, codlott, fase_esecuzione, num, daexport, t1.tab1nord, t1.tab1tip "
			+ " order by t1.tab1nord,num,t1.tab1tip")
	public List<FaseEntry> getFasiLottoNuova(@Param("codgara") Long codgara, @Param("codlott") Long codlott,
											 @Param("numAppa") Long numAppa);

	@Select(" SELECT tab1tip as codice, tab1desc as descrizione, tab1arc as archiviato from tab1 where tab1cod= 'W3020' and tab1nord > 0 order by tab1nord,tab1tip")
	public List<TabellatoFaseEntry> getAllFasiLotto();

	@Select("select count(idflusso) from w9flussi where key03 = 988 and key01 = #{codGara} ")
	public Long countFaseAnagraficaBando(@Param("codGara") Long codGara);

	@Update("update W9LOTT set rup = #{codiceTecnico} where codgara = #{codGara}")
	public void updateRupLottoByGara(@Param("codGara") Long codGara, @Param("codiceTecnico") String codiceTecnico);

	@Select("select daexport from w9lott where codgara = #{codGara}")
	public Long getLottoDaexport(@Param("codGara") Long codGara);

	@Select("select count(*) from w9lott where codgara = #{codGara} and daexport = '1'")
	public Long countLottiDaEsportare(@Param("codGara") Long codGara);

	@Update("update w9lott set daexport = 2 where codgara = #{codGara}")
	public void updateDaExportDopoPubblicazione(@Param("codGara") Long codGara);

	@Update("update w9lott set daexport = #{daExport} where codgara = #{codGara} and codlott = #{codLotto}")
	public void updateDaExportLotto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
									@Param("daExport") String daExport);

	@Update("update w9lott set daexport = #{daExport} where codgara = #{codGara}")
	public void updateDaExportLotti(@Param("codGara") Long codGara, @Param("daExport") String daExport);

	@Select("select count(*) from w9lott where codgara = #{codGara}")
	public Long countLotti(@Param("codGara") Long codGara);

	@Select("select cig from w9lott where codgara = #{codGara} and codlott = #{codLotto}")
	public String getCigLotto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select importo_tot from w9lott where codgara = #{codGara}")
	public List<Double> getImportoTot(@Param("codGara") Long codGara);

	@Select("select fase_esecuzione from w9fasi where codgara = #{codGara} and id_scheda_simog = #{idScheda}")
	public Long getCodFase(@Param("codGara") Long codGara, @Param("idScheda") String idScheda);

	// ATTI

	@Select("select nome from W9CF_PUBB where id = #{tipdoc}")
	public String getNomeAtto(@Param("tipdoc") Long tipdoc);

	@Select("select nome from W9CF_PUBB where id = #{tipdoc}")
	public String getTipoAtto(@Param("tipdoc") Long tipdoc);

	@Select("select codgara, tipdoc as tipoDoc, num_pubb as numPubb from W9PUBBLICAZIONI P where P.CODGARA=#{codiceGara}")
	public List<AttiPubbEntry> getListaAtti(@Param("codiceGara") Long codiceGara);

	@Select("select count(*) from w9flussi wf where key01 = #{codgara} and key04 = #{numPubb} and key03 = 901")
	public Long checkIfAttoisPubblicato(@Param("codgara") Long codgara, @Param("numPubb") Long numPubb);

	@Select("select id as id, nome as nome, cl_where_vis as condizione, campi_vis as campiVisibili, campi_obb as campiObbligatori, tipo from W9CF_PUBB order by numord asc")
	public List<CondizioneAtto> getAllAtti();


	@Select("select id as id, nome as nome, cl_where_vis as condizione, campi_vis as campiVisibili, campi_obb as campiObbligatori, tipo, "
			+ "(select count(*) from w9lott where codgara=#{codgara}) as numLottiTotali,"
			+ "(select count(*) from w9pubblicazioni p where p.tipdoc=cf.id and codgara=#{codgara}) as numAtti,"
			+ "(select count(distinct codlott) from w9pubblicazioni p join w9publotto pl on p.codgara=pl.codgara and p.num_pubb=pl.num_pubb where p.tipdoc=cf.id and p.codgara=#{codgara}) as numLottiConAtto,"
			+ "(select count(distinct codlott) from w9pubblicazioni p join w9publotto pl on p.codgara=pl.codgara and p.num_pubb=pl.num_pubb where p.tipdoc=cf.id and p.codgara=#{codgara} and DATAPUBBSISTEMA is not null) as numLottiAttoPubblicato "
			+ "from W9CF_PUBB cf order by numord asc")
	public List<CondizioneAtto> getAllAttiCompleta(@Param("codgara") Long codgara);

	int hasAtto(@Param("codiceGara") Long codiceGara, @Param("whereCondition") String whereCondition);

	@Select("select count(*) from W9PUBBLICAZIONI P where P.CODGARA=#{codiceGara}")
	public int hasAtti(@Param("codiceGara") Long codiceGara);

	@Select("select count(*) from W9PUBBLICAZIONI P where P.CODGARA=#{codiceGara} and P.TIPDOC=#{tipoDocumento}")
	public int getNumPubblicazioni(@Param("codiceGara") Long codiceGara, @Param("tipoDocumento") Long tipoDocumento);

	@Select("select count(*) from (select 1 r from W9PUBBLICAZIONI P join W9PUBLOTTO L on L.CODGARA=P.CODGARA and L.NUM_PUBB=P.NUM_PUBB where P.CODGARA=#{codiceGara} and P.TIPDOC=#{tipoDocumento} group by L.CODGARA,L.CODLOTT) Pubb")
	public int attoIncompilazione1(@Param("codiceGara") Long codiceGara, @Param("tipoDocumento") Long tipoDocumento);

	@Select("select count(*) from W9FLUSSI F join W9PUBBLICAZIONI P on P.CODGARA= F.KEY01 and F.KEY03=901 and F.key04=P.NUM_PUBB where P.CODGARA=#{codiceGara} and P.TIPDOC=#{tipoDocumento}")
	public int attoIncompilazione2(@Param("codiceGara") Long codiceGara, @Param("tipoDocumento") Long tipoDocumento);

	@Select("select count(*) from (select 1 r from W9FLUSSI F join W9PUBBLICAZIONI P on P.CODGARA= F.KEY01 and F.KEY03=901 and F.key04=P.NUM_PUBB join W9PUBLOTTO L on L.CODGARA=P.CODGARA and L.NUM_PUBB=P.NUM_PUBB where P.CODGARA=#{codiceGara} and P.TIPDOC=#{tipoDocumento} group by L.CODGARA,L.CODLOTT) Pubb")
	public int attoPubblicato(@Param("codiceGara") Long codiceGara, @Param("tipoDocumento") Long tipoDocumento);

	@Select("select count(*) from (select 1 r from W9PUBBLICAZIONI P join W9PUBLOTTO L on L.CODGARA=P.CODGARA and L.NUM_PUBB=P.NUM_PUBB where P.CODGARA=#{codiceGara} and P.TIPDOC=#{tipoDocumento} group by L.CODGARA,L.CODLOTT) Pubb")
	public int attoInCompilazioneParziale1(@Param("codiceGara") Long codiceGara,
										   @Param("tipoDocumento") Long tipoDocumento);

	@Select("select count(*) from W9FLUSSI F join W9PUBBLICAZIONI P on P.CODGARA= F.KEY01 and F.KEY03=901 and F.key04=P.NUM_PUBB where P.CODGARA=#{codiceGara} and P.TIPDOC=#{tipoDocumento}")
	public int attoInCompilazioneParziale2(@Param("codiceGara") Long codiceGara,
										   @Param("tipoDocumento") Long tipoDocumento);

	@Select("select count(*) from (select 1 r from W9FLUSSI F join W9PUBBLICAZIONI P on P.CODGARA= F.KEY01 and F.KEY03=901 and F.key04=P.NUM_PUBB join W9PUBLOTTO L on L.CODGARA=P.CODGARA and L.NUM_PUBB=P.NUM_PUBB where P.CODGARA=#{codiceGara} and P.TIPDOC=#{tipoDocumento} group by L.CODGARA,L.CODLOTT) Pubb")
	public int attoPubblicatoParziale(@Param("codiceGara") Long codiceGara, @Param("tipoDocumento") Long tipoDocumento);

	@Select("select ID_RICEVUTO from W9PUBBLICAZIONI P where P.CODGARA=#{codiceGara} and P.TIPDOC=#{tipoDocumento}")
	public List<String> attoIdRicevuto(@Param("codiceGara") Long codiceGara, @Param("tipoDocumento") Long tipoDocumento);

	@Select("select num_pubb from W9PUBBLICAZIONI P where P.CODGARA=#{codiceGara} and P.TIPDOC=#{tipoDocumento}")
	public List<Long> getIdPubblicazioniAtti(@Param("codiceGara") Long codiceGara,
											 @Param("tipoDocumento") Long tipoDocumento);

	@Select("select codgara as codGara, num_pubb as numPubb, datapubb as dataPubb, descriz, datascad as dataScad, data_decreto as dataDecreto, data_provvedimento as dataProvvedimento, num_provvedimento as numProvvedimento, data_stipula as dataStipula, num_repertorio as numRepertorio, perc_ribasso_agg as percRibassoAgg, perc_off_aumento as percOffAumento, importo_aggiudicazione as importoAggiudicazione, data_verb_aggiudicazione as dataVerbAggiudicazione, id_generato as idGenerato, id_ricevuto as idRicevuto, url_committente as urlCommittente, url_eproc as urlEproc, tipdoc as tipDoc, primapubblicazione as primaPubblicazione, datapubbsistema as dataPubbSistema, utente_canc as utenteCancellazione, datacanc as dataCancellazione, motivo_canc as motivoCancellazione from W9PUBBLICAZIONI where codgara = #{codiceGara} and tipdoc = #{tipoDocumento} and num_pubb = #{numPubb}")
	public DettaglioAttoEntry getDettaglioAtto(@Param("codiceGara") Long codiceGara,
											   @Param("tipoDocumento") Long tipoDocumento, @Param("numPubb") Long numPubb);

	@Select("select count(1)  from w9pubblicazioni wp, w9cf_pubb wcp, w9flussi wf where wcp.id = wp.tipdoc and wf.key01 = wp.codgara and wf.key04 = wp.num_pubb and wf.key03 = 901 and wcp.tipo in ('E','A') and wp.codgara = #{codiceGara}")
	public Long getNumeroPubblicazioniAttiEsito(@Param("codiceGara") Long codiceGara);


	@Select("select codgara as codGara, num_pubb as numPubb, datapubb as dataPubb, descriz, datascad as dataScad, data_decreto as dataDecreto, data_provvedimento as dataProvvedimento, num_provvedimento as numProvvedimento, data_stipula as dataStipula, num_repertorio as numRepertorio, perc_ribasso_agg as percRibassoAgg, perc_off_aumento as percOffAumento, importo_aggiudicazione as importoAggiudicazione, data_verb_aggiudicazione as dataVerbAggiudicazione, id_generato as idGenerato, id_ricevuto as idRicevuto, url_committente as urlCommittente, url_eproc as urlEproc, tipdoc as tipDoc  from W9PUBBLICAZIONI where codgara = #{codiceGara} and num_pubb = #{numPubb}")
	public FullDettaglioAttoEntry getAtto(@Param("codiceGara") Long codiceGara, @Param("numPubb") Long numPubb);

	@Select("select count(F.idflusso) from W9FLUSSI F join W9PUBBLICAZIONI P on P.CODGARA= F.KEY01 and F.KEY03=901 and F.key04=P.NUM_PUBB where P.CODGARA=#{codiceGara} and P.num_pubb=#{numPubb}")
	public int countPubblicazioniAtto(@Param("codiceGara") Long codiceGara, @Param("numPubb") Long numPubb);

	@Select("select descriz as descrizione, datapubb as dataPubblicazione, primapubblicazione as primaPubblicazione, datapubbsistema as dataPubbSistema, utente_canc as utenteCancellazione, datacanc as dataCancellazione, motivo_canc as motivoCancellazione from w9pubblicazioni where codgara = #{codGara} and num_pubb = #{numPubb}")
	public ListaSingoloAttoInfoEntry getAttoInfo(@Param("codGara") final Long codGara,
												 @Param("numPubb") final Long numPubb);

	@Insert("insert into W9PUBBLICAZIONI (codgara, num_pubb, datapubb, descriz, datascad, data_decreto, data_provvedimento, num_provvedimento, data_stipula, num_repertorio, perc_ribasso_agg, perc_off_aumento, data_verb_aggiudicazione, url_committente, url_eproc, tipdoc, importo_aggiudicazione, primapubblicazione, datapubbsistema, utente_canc, datacanc, motivo_canc ) VALUES ( #{ codGara}, #{ numPubb}, #{ dataPubb}, #{ descriz}, #{ dataScad}, #{ dataDecreto}, #{ dataProvvedimento}, #{ numProvvedimento}, #{ dataStipula}, #{ numRepertorio}, #{ percRibassoAgg}, #{ percOffAumento}, #{ dataVerbAggiudicazione}, #{ urlCommittente}, #{ urlEproc}, #{tipDoc}, #{importoAggiudicazione}, #{primaPubblicazione}, #{dataPubbSistema}, #{utenteCancellazione}, #{dataCancellazione}, #{motivoCancellazione})")
	public void insertAtto(AttoInsertForm insertForm);

	@Update("update W9PUBBLICAZIONI set datapubb =#{dataPubb}, descriz =#{descriz}, datascad =#{dataScad}, data_decreto =#{dataDecreto}, data_provvedimento =#{dataProvvedimento}, num_provvedimento =#{numProvvedimento}, data_stipula =#{dataStipula}, num_repertorio =#{numRepertorio}, perc_ribasso_agg =#{percRibassoAgg}, perc_off_aumento =#{percOffAumento}, data_verb_aggiudicazione =#{dataVerbAggiudicazione}, url_committente =#{urlCommittente}, url_eproc =#{urlEproc}, importo_aggiudicazione  =#{importoAggiudicazione}, primapubblicazione =#{primaPubblicazione}, datapubbsistema =#{dataPubbSistema}, utente_canc =#{utenteCancellazione}, datacanc =#{dataCancellazione}, motivo_canc =#{motivoCancellazione} where codgara = #{codGara} and num_pubb = #{numPubb}")
	public void updateAtto(AttoUpdateForm insertForm);

	@Select("select codgara as codGara, num_pubb as numPubb, numdoc as numDoc, titolo, file_allegato as fileAllegato, url as url, tipo_file as tipoFile from W9DOCGARA where codgara = #{codiceGara} and num_pubb = #{numPubb}")
	public List<AttoDocument> getAttoDocuments(@Param("codiceGara") Long codiceGara, @Param("numPubb") Long numPubb);

	@Select("select codgara as codGara, num_pubb as numPubb, numdoc as numDoc from W9DOCGARA where codgara = #{codiceGara} and num_pubb = #{numPubb}")
	public List<AttoDocument> getAttoDocumentsWithoutAllegato(@Param("codiceGara") Long codiceGara,
															  @Param("numPubb") Long numPubb);

	@Select("select codgara as codGara, num_pubb as numPubb, numdoc as numDoc, titolo, file_allegato as fileAllegato, url as url, tipo_file as tipoFile from W9DOCGARA where codgara = #{codGara} and num_pubb = #{numPubb} and numdoc = #{numDoc}")
	public AttoDocument getAttoDocument(@Param("codGara") final Long codGara, @Param("numPubb") final Long numPubb,
										@Param("numDoc") final Long numDoc);

	@Select("select coalesce(max(numdoc), 0) as numdoc from W9DOCGARA where codgara = #{codGara} and num_pubb = #{numPubb}")
	public Long getMaxNumDocGara(@Param("codGara") final Long codGara, @Param("numPubb") final Long numPubb);

	@Insert("insert into W9DOCGARA (codgara, num_pubb, numdoc, titolo, file_allegato, url, tipo_file) values (#{codGara}, #{numPubb}, #{numDoc}, #{titolo}, #{fileAllegato}, #{url}, #{tipoFile})")
	public void insertAttoDocument(AttoDocument document);

	@Select("select max(num_pubb) from W9PUBBLICAZIONI where codgara = #{codiceGara}")
	public Long getMaxNumPubb(@Param("codiceGara") Long codiceGara);

	@Insert("INSERT INTO W9PUBLOTTO (CODGARA, NUM_PUBB, CODLOTT) VALUES (#{codiceGara},#{numPubb},#{codLotto})")
	public void insertPubbLotto(@Param("codiceGara") Long codiceGara, @Param("numPubb") Long numPubb,
								@Param("codLotto") Long codLotto);

	@Select("select num_pubb from w9publotto where codgara = #{codGara} and codlott = #{codLotto}")
	public List<Long> getAttiLotto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Delete("delete from W9PUBBLICAZIONI where codgara = #{codiceGara} and num_pubb=#{numPubb}")
	public void deleteAtto(@Param("codiceGara") Long codiceGara, @Param("numPubb") Long numPubb);

	@Delete("delete from W9PUBBLICAZIONI where codgara = #{codiceGara}")
	public void deleteAttiGara(@Param("codiceGara") Long codiceGara);

	@Delete("delete from W9DOCGARA where codgara = #{codiceGara}")
	public void deleteDocumentiAttiGara(@Param("codiceGara") Long codiceGara);

	@Delete("DELETE from W9ALLEGATI WHERE key01 = #{codiceGara} AND key02 = #{numPubb}")
	public void deleteAttoDocuments(@Param("codiceGara") String codiceGara, @Param("numPubb") String numPubb);

	@Delete("DELETE from W9DOCGARA WHERE CODGARA = #{codiceGara} AND NUM_PUBB = #{numPubb} and numdoc = #{numDoc}")
	public void deleteAttoDocument(@Param("codiceGara") Long codiceGara, @Param("numPubb") Long numPubb,
								   @Param("numDoc") Long numDoc);

	@Delete("DELETE from W9PUBLOTTO WHERE CODGARA = #{codiceGara} AND NUM_PUBB = #{numPubb} ")
	public void deletePubLotto(@Param("codiceGara") Long codiceGara, @Param("numPubb") Long numPubb);

	@Select("select codlott from W9PUBLOTTO WHERE CODGARA = #{codiceGara} AND NUM_PUBB = #{numPubb}")
	public List<Long> getAttoLotti(@Param("codiceGara") Long codiceGara, @Param("numPubb") Long numPubb);

	@Delete("delete from w9publotto where codgara = #{codGara} and codlott = #{codLotto}")
	public void deleteAssociazioniLottoAtto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select codgara as codGara, num_pubb as numPubb, num_aggi as numAggi, id_tipoagg as idTipoAgg, ruolo as ruolo, codimp as codImpresa, id_gruppo as idGruppo from esiti_aggiudicatari where codgara = #{codGara} and num_pubb = #{numPubb}")
	public List<ImpresaAggiudicatariaAttoEntry> getImpreseAggiudicatarieAtto(@Param("codGara") Long codGara,
																			 @Param("numPubb") Long numPubb);

	@Insert("insert into esiti_aggiudicatari(codgara, num_pubb, num_aggi, id_tipoagg, ruolo, codimp, id_gruppo) values (#{codGara},#{numPubb},#{numAggi},#{idTipoAgg},#{ruolo},#{codImpresa},#{idGruppo})")
	public void insertImpresaAggiudicatariaAtto(@Param("codGara") Long codGara, @Param("numPubb") Long numPubb,
												@Param("numAggi") Long numAggi, @Param("idTipoAgg") Long idTipoAgg, @Param("ruolo") Long ruolo,
												@Param("codImpresa") String codImpresa, @Param("idGruppo") Long idGruppo);

	@Delete("delete from esiti_aggiudicatari where codgara = #{codGara} and num_pubb= #{numPubb}")
	public void deleteImpreseAggiudicatarieAtto(@Param("codGara") Long codGara, @Param("numPubb") Long numPubb);

	@Select("select l.cig from w9publotto p inner join w9lott l on p.codgara = l.codgara and p.codlott = l.codlott where p.codgara = #{codGara} and p.num_pubb = #{numPubb}")
	public List<String> getCigAssociatiAdAtto(@Param("codGara") final Long codGara,
											  @Param("numPubb") final Long numPubb);

	// PUBBLICITA DI GARA

	@Select("select codgara as codiceGara, DATA_GUCE as gazzettaUffEuropea, DATA_GURI as gazzettaUffItaliana, DATA_ALBO as alboPretorioComuni, QUOTIDIANI_NAZ as numQuotidianiNazionali,  QUOTIDIANI_REG as numQuotidianiLocali, PROFILO_COMMITTENTE as profiloCommittente, SITO_MINISTERO_INF_TRASP as sitoMinisteriInfr, SITO_OSSERVATORIO_CP as sitoOsservatorioContratti, DATA_BORE as dataBollettino, PERIODICI as numPeriodici from W9PUBB where codgara = #{codiceGara}")
	public PubblicitaGaraEntry getPubblicitaGara(@Param("codiceGara") Long codiceGara);

	@Insert("insert into W9PUBB ( codgara, CODLOTT, NUM_APPA, NUM_PUBB, DATA_GUCE, DATA_GURI, DATA_ALBO, QUOTIDIANI_NAZ, QUOTIDIANI_REG, PROFILO_COMMITTENTE, SITO_MINISTERO_INF_TRASP, SITO_OSSERVATORIO_CP, DATA_BORE, PERIODICI) values (#{codiceGara},1,1,1,#{gazzettaUffEuropea},#{gazzettaUffItaliana},#{alboPretorioComuni},#{numQuotidianiNazionali},#{numQuotidianiLocali},#{profiloCommittente},#{sitoMinisteriInfr},#{sitoOsservatorioContratti},#{dataBollettino},#{numPeriodici})")
	public void insertPubblicitaGara(PubblicitaGaraInsertForm form);

	@Update("update W9PUBB set DATA_GUCE =#{gazzettaUffEuropea},  DATA_GURI =#{gazzettaUffItaliana},  DATA_ALBO =#{alboPretorioComuni},  QUOTIDIANI_NAZ =#{numQuotidianiNazionali},  QUOTIDIANI_REG =#{numQuotidianiLocali},  PROFILO_COMMITTENTE =#{profiloCommittente},  SITO_MINISTERO_INF_TRASP =#{sitoMinisteriInfr},  SITO_OSSERVATORIO_CP =#{sitoOsservatorioContratti} , DATA_BORE=#{dataBollettino}, PERIODICI=#{numPeriodici} where codgara = #{codiceGara}")
	public void updatePubblicitaGara(PubblicitaGaraInsertForm form);

	@Delete("delete from W9PUBB where codgara = #{codiceGara}")
	public void deletePubblicitaDiGara(@Param("codiceGara") Long codiceGara);

	// OPERAZIINI TRASVERSALI

	@Select("select esito_procedura from w9esito where codgara = #{codGara} and codlott = #{codLotto}")
	public List<Long> getEsitoProcedura(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE codtec = #{id}")
	public RupEntry getTecnico(@Param("id") String id);

	@Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE codtec = #{id}")
	public RupPubbEntry getTecnicoPubb(@Param("id") String id);

	@Select("select denom from uffici where id = #{idUfficio}")
	public String getNomeUfficio(@Param("idUfficio") Long idUfficio);

	@Select("select id, codein as stazioneAppaltante, denom as denominazione from uffici where id = #{idUfficio}")
	public UffEntry getUfficio(@Param("idUfficio") Long idUfficio);

	@Select("select idcentro as id, codcentro as codiceCDC, denomcentro as nominativoCDC, note as note, tipologia as tipologia from CENTRICOSTO where idcentro = #{idCdc}")
	public CentriCostoEntry getCentroCosto(@Param("idCdc") Long idCdc);

	@Select("select nomein from uffint where codein = #{stazioneappaltante}")
	public String getNominativoSa(@Param("stazioneappaltante") String stazioneappaltante);

	@Select("select nomein from uffint where cfein = #{cfstazioneappaltante}")
	public String getNominativoSaByCf(@Param("cfstazioneappaltante") String cfstazioneappaltante);

	@Select("select sysab3 from usrsys where syscon = #{syscon}")
	public String getRuolo(@Param("syscon") Long syscon);

	@Select("select sysute from usrsys where syscon = #{syscon}")
	public String getSysute(@Param("syscon") Long syscon);

	@Select("SELECT syspwbou from usrsys where syscon = #{syscon}")
	public String getAbilitazioniUtente(@Param("syscon") Long syscon);

	@Select("select codtec from tecni where syscon = #{syscon}")
	public String getCodiceRup(@Param("syscon") Long syscon);

	@Select("select cftec from tecni where codtec = #{codtec}")
	public String getcfRup(@Param("codtec") String codtec);

	@Select("select codtec from tecni where syscon = #{syscon} and cgentei = #{stazioneappaltante}")
	public List<String> getCodiceForSA(@Param("syscon") Long syscon, @Param("stazioneappaltante") String stazioneappaltante);

	@Select("select cftec from tecni where syscon = #{syscon} and cgentei = #{stazioneappaltante}")
	public List<String> getCfForSA(@Param("syscon") Long syscon, @Param("stazioneappaltante") String stazioneappaltante);

	@Select("select codein from usr_ein where syscon = #{syscon}")
	public List<String> getSAList(@Param("syscon") long syscon);

	@Select("select syscon from usrsys where upper(syslogin) = #{cf}")
	public Long getSysconFromCf(@Param("cf") String cf);

	@Select("select syscon from usrsys where upper(syscf) = #{cf}")
	public Long getSysconFromSysCf(@Param("cf") String cf);

	@Select("select count(1) from w9gara")
	public Long health();

	@Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE cftec = #{codiceFiscale} and cgentei = #{stazioneAppaltante}")
	public RupEntry getTecnicoByCfAndSa(@Param("codiceFiscale") String codiceFiscale,
										@Param("stazioneAppaltante") String stazioneAppaltante);

	@Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE cftec = #{codiceFiscale} and cgentei = #{stazioneAppaltante}")
	public List<RupEntry> getTecnicoByCfAndSaList(@Param("codiceFiscale") String codiceFiscale,
												  @Param("stazioneAppaltante") String stazioneAppaltante);

	// FASI

	@Select("select max(num) from w9fasi where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{fase}")
	public Long getMaxNumW9fase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
								@Param("fase") Long fase);

	@Insert("insert into w9fasi (codgara, codlott, fase_esecuzione, num, id_scheda_locale, daexport, num_appa, id_scheda_simog ) values (#{codGara},#{codLotto},#{fase},#{num},#{idScheda}, 1, #{numAppa}, #{idSchedaSimog})")
	public void insertW9fase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("fase") Long fase,
							 @Param("num") Long num, @Param("idScheda") String idScheda, @Param("numAppa") Long numAppa,
							 @Param("idSchedaSimog") String idSchedaSimog);

	@Update("update w9fasi set daexport = 1 where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{fase} and num=#{num}")
	public void setW9faseDaExport(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
								  @Param("fase") Long fase, @Param("num") Long num);

	@Update("update w9fasi set daexport = 2 where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{fase} and num=#{num}")
	public void setW9faseExported(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
								  @Param("fase") Long fase, @Param("num") Long num);

	@Update("update w9fasi set daexport = 3 where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{fase} and num=#{num}")
	public void setW9faseWaiting(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
								 @Param("fase") Long fase, @Param("num") Long num);

	@Select("select id_scheda_simog from w9fasi where daexport = '3' and codgara = #{codGara} and codlott = #{codLotto}")
	public String checkExistsSchedaWaiting(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Delete("delete from w9fasi where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{fase} and num = #{num}")
	public void deleteW9Fase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("fase") Long fase,
							 @Param("num") Long num);

	@Delete("delete from w9fasi where codgara = #{codGara} and fase_esecuzione = #{fase}")
	public void deleteW9FasePcp(@Param("codGara") Long codGara, @Param("fase") Long fase);

	@Delete("delete from w9fasi where codgara = #{codGara} and codlott = #{codlotto} and fase_esecuzione = #{fase}")
	public void deleteW9FasePcpLotto(@Param("codGara") Long codGara, @Param("codlotto") Long codlotto, @Param("fase") Long fase);

	@Select("select id_scheda_simog as idSchedaSimog, id_scheda_locale as idSchedaLocale from w9fasi where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{faseEsecuzione} and num = #{numProgressivoFase}")
	public DatiIdSchedeW9FasiEntry getIdSchedeW9Fasi(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
													 @Param("faseEsecuzione") Long faseEsecuzione, @Param("numProgressivoFase") Long numProgressivoFase);

	@Select("select count(*) from w9fasi where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{fase}")
	public Long getFase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
						@Param("fase") Long fase);

	// FASE COMUNICAZIONE

	@Insert("insert into w9esito (codgara, codlott, esito_procedura, data_verb_aggiudicazione, importo_aggiudicazione, file_allegato) values (#{codGara},#{codLotto},#{esito},#{dataAggiudicazione},#{importoAggiudicazione},#{fileAllegato})")
	public void insertFaseComEsito(FaseComEsitoForm form);

	@Insert("insert into w9pues (codgara, codlott, num_iniz, num_pues, data_guce, data_guri, data_albo, quotidiani_naz, quotidiani_reg, profilo_committente, sito_ministero_inf_trasp, sito_osservatorio_cp ) values (#{codGara},#{codLotto},1,1,#{dataGuce},#{dataGuri},#{dataAlbo},#{quotidianiNaz},#{quotidianiReg},#{profiloCommittente},#{sitoMinInfTrasp},#{sitoOsservatorio})")
	public void insertFaseComPubbEsito(FaseComPubbEsitoForm form);

	@Update("update w9esito set esito_procedura = #{esito}, data_verb_aggiudicazione = #{dataAggiudicazione}, importo_aggiudicazione = #{importoAggiudicazione}, file_allegato = #{fileAllegato} where codgara = #{codGara} and codlott = #{codLotto}")
	public void updateFaseComEsito(FaseComEsitoForm form);

	@Select("select codgara as codGara, codlott as codLotto, esito_procedura as esito, data_verb_aggiudicazione as dataAggiudicazione, importo_aggiudicazione as importoAggiudicazione, file_allegato as fileAllegato from w9esito where codgara = #{codGara} and codlott = #{codLotto}")
	public FaseComEsitoForm getFaseComEsito(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											@Param("numAppa") Long numAppa);

	@Select("select codgara as codGara, codlott as codLotto, num_iniz as numIniziale, data_guce as dataGuce, data_guri as dataGuri, data_albo as dataAlbo, quotidiani_naz as quotidianiNaz, quotidiani_reg as quotidianiReg, profilo_committente as profiloCommittente, sito_ministero_inf_trasp as sitoMinInfTrasp, sito_osservatorio_cp as sitoOsservatorio from w9pues  where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{numIniz}")
	FaseComPubbEsitoForm getFaseComPubbEsito(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("numIniz") Long numIniz);

	@Delete("delete from w9pues where codgara = #{codGara} and codlott = #{codLotto} ")
	public void deleteFaseComPubbEsito(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Delete("delete from w9esito where codgara = #{codGara} and codlott = #{codLotto} ")
	public void deleteFaseComEsito(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Delete("delete from w9esito where codgara = #{codGara}")
	public void deleteFaseComEsitoAllPcp(@Param("codGara") Long codGara);

	// FASE IMPRESE INVITATE/PARTECIPANTI

	@Select("select codgara as codGara, codlott as codLotto, num_impr as num, codimp as codImpresa, tipoagg as tipologiaSoggetto, ruolo as ruolo, partecip as partecipante, num_ragg as numRaggruppamento from w9imprese where codgara = #{codGara} and codlott = #{codLotto}")
	public List<RecordFaseImpreseEntry> getFaseImprese(@Param("codGara") Long codGara,
													   @Param("codLotto") Long codLotto);

	@Select("select codgara as codGara, codlott as codLotto, num_impr as num, codimp as codImpresa, tipoagg as tipologiaSoggetto, ruolo as ruolo, partecip as partecipante, num_ragg as numRaggruppamento from w9imprese where codgara = #{codGara} and codlott = #{codLotto} and num_impr = #{num}")
	public RecordFaseImpreseEntry dettaglioFaseImprese(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
													   @Param("num") Long num);

	@Select("select codgara as codGara, codlott as codLotto, num_impr as num, codimp as codImpresa, tipoagg as tipologiaSoggetto, ruolo as ruolo, partecip as partecipante, num_ragg as numRaggruppamento from w9imprese where codgara = #{codGara} and codlott = #{codLotto} and  num_ragg = #{numRagg}")
	public List<RecordFaseImpreseEntry> getFaseImpreseByRagg(@Param("codGara") Long codGara,
															 @Param("codLotto") Long codLotto, @Param("numRagg") Long numRagg);

	@Select("select count(codgara) from w9imprese where codgara = #{codGara} and codlott = #{codLotto}")
	public Long countFaseImprese(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Insert("insert into w9imprese (codgara, codlott, num_impr, codimp, tipoagg, ruolo, partecip, num_ragg) values (#{codGara},#{codLotto},#{num},#{codImpresa},#{tipologiaSoggetto},#{ruolo},#{partecipante},#{numRaggruppamento}) ")
	public void insertFaseImpresa(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
								  @Param("num") Long num, @Param("codImpresa") String codImpresa,
								  @Param("tipologiaSoggetto") Long tipologiaSoggetto, @Param("ruolo") Long ruolo,
								  @Param("partecipante") Long partecipante, @Param("numRaggruppamento") Long numRaggruppamento);

	@Delete("delete from w9imprese where codgara = #{codGara} and codlott = #{codLotto} and num_impr = #{num}")
	public void deleteFaseImpresaByNum(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
									   @Param("num") Long num);

	@Delete("delete from w9imprese where codgara = #{codGara} and codlott = #{codLotto}")
	public void deleteAllImpreseFase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Delete("delete from w9imprese where codgara = #{codGara} and codlott = #{codLotto} and num_ragg = #{numRagg}")
	public void deleteFaseImpresaByRaggruppamento(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												  @Param("numRagg") Long numRagg);

	@Select("select max(num_impr) from w9imprese where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxIdFaseImpresa(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_ragg) from w9imprese where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumRaggruppamento(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Delete("delete from w9imprese where codgara = #{codGara}")
	public void deleteFaseImpresaAllPcp(@Param("codGara") Long codGara);

	// AGGIUDICAZIONE

	@Select("select codgara as codGara, codlott as codLotto, num_appa as counter, COD_STRUMENTO as codStrumento, IMPORTO_LAVORI as importoLavori, IMPORTO_SERVIZI as importoServizi, IMPORTO_FORNITURE as importoForniture, IMPORTO_SUBTOTALE as importosubtotale, IMPORTO_ATTUAZIONE_SICUREZZA as importoSicurezza, IMPORTO_PROGETTAZIONE as importoProgettazione, IMP_NON_ASSOG as impNonAssog, IMPORTO_COMPL_APPALTO as importoComplAppalto, IVA as iva, IMPORTO_IVA as importoIva, ALTRE_SOMME as altreSomme, IMPORTO_DISPOSIZIONE as importoDisposizione, IMPORTO_COMPL_INTERVENTO as importoComplIntervento, OPERE_URBANIZ_SCOMPUTO as opereUrbanizScomputo, MODALITA_RIAGGIUDICAZIONE as modalitaRiaggiudicazione, REQUISITI_SETT_SPEC as requisitiSettSpec, PROCEDURA_ACC as proceduraAcc, PREINFORMAZIONE as preinformazione, TERMINE_RIDOTTO as termineRidotto, ID_MODO_INDIZIONE as idIndizione, DATA_MANIF_INTERESSE as dataManifestInteresse, NUM_MANIF_INTERESSE as numManifestInteresse, DATA_SCADENZA_RICHIESTA_INVITO as dataScadRichiestaInvito, NUM_IMPRESE_RICHIEDENTI as numImpreseRichiedenti, DATA_INVITO as dataInvito, NUM_IMPRESE_INVITATE as numImpreseInvitate, DATA_SCADENZA_PRES_OFFERTA as dataScadenzaPresOfferta, NUM_IMPRESE_OFFERENTI as numImpreseOfferenti, NUM_OFFERTE_AMMESSE as numOfferteAmmesse, NUM_OFFERTE_ESCLUSE as numOfferteEscluse, NUM_IMP_ESCL_INSUF_GIUST as impEsclusInsuffGiust, OFFERTA_MASSIMO as offertaMassima, OFFERTA_MINIMA as offertaMinima, VAL_SOGLIA_ANOMALIA as valoreSogliaAnomalia, NUM_OFFERTE_FUORI_SOGLIA as numOfferteFuoriSoglia, ASTA_ELETTRONICA as astaElettronica, PERC_RIBASSO_AGG as percentRibassoAgg, PERC_OFF_AUMENTO as percOffAumento, IMPORTO_AGGIUDICAZIONE as importoAggiudicazione, DATA_VERB_AGGIUDICAZIONE as dataVerbAggiudicazione, TIPO_ATTO as tipoAtto, DATA_ATTO as dataAtto, NUMERO_ATTO as numeroAtto, FLAG_RICH_SUBAPPALTO as flagRichSubappalto,FIN_REGIONALE as flagSubappFinReg, FLAG_IMPORTI as flagImporti, FLAG_SICUREZZA as flagSicurezza, FLAG_LIVELLO_PROGETTUALE as flagLivelloProgettuale,  VERIFICA_CAMPIONE as verificaCampione, codcui as codCui, RELAZIONE_UNICA as flagRelazioneUnica, IMPORTO_OPZIONI as importoSommeOpzioniRinnovi, IMPORTO_RIPETIZIONI as importoSommeripetizioni from w9appa where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa}")
	public FaseAggiudicazioneEntry getFaseAggiudicazione(@Param("codGara") Long codGara,
														 @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa);

	@Insert("insert into w9appa (codgara, codlott, COD_STRUMENTO, IMPORTO_LAVORI, IMPORTO_SERVIZI, IMPORTO_FORNITURE, IMPORTO_SUBTOTALE, IMPORTO_ATTUAZIONE_SICUREZZA, IMPORTO_PROGETTAZIONE, IMP_NON_ASSOG, IMPORTO_COMPL_APPALTO, IVA, IMPORTO_IVA, ALTRE_SOMME, IMPORTO_DISPOSIZIONE, IMPORTO_COMPL_INTERVENTO, OPERE_URBANIZ_SCOMPUTO, MODALITA_RIAGGIUDICAZIONE, REQUISITI_SETT_SPEC, PROCEDURA_ACC, PREINFORMAZIONE, TERMINE_RIDOTTO, ID_MODO_INDIZIONE, DATA_MANIF_INTERESSE, NUM_MANIF_INTERESSE, DATA_SCADENZA_RICHIESTA_INVITO, NUM_IMPRESE_RICHIEDENTI, DATA_INVITO, NUM_IMPRESE_INVITATE, DATA_SCADENZA_PRES_OFFERTA, NUM_IMPRESE_OFFERENTI, NUM_OFFERTE_AMMESSE, NUM_OFFERTE_ESCLUSE, NUM_IMP_ESCL_INSUF_GIUST, OFFERTA_MASSIMO, OFFERTA_MINIMA, VAL_SOGLIA_ANOMALIA, NUM_OFFERTE_FUORI_SOGLIA, ASTA_ELETTRONICA, PERC_RIBASSO_AGG, PERC_OFF_AUMENTO, IMPORTO_AGGIUDICAZIONE, DATA_VERB_AGGIUDICAZIONE, TIPO_ATTO, DATA_ATTO, NUMERO_ATTO, FLAG_RICH_SUBAPPALTO, FIN_REGIONALE, FLAG_IMPORTI, FLAG_SICUREZZA, FLAG_LIVELLO_PROGETTUALE, VERIFICA_CAMPIONE, NUM_APPA, RELAZIONE_UNICA, CODCUI, IMPORTO_OPZIONI, IMPORTO_RIPETIZIONI) values (#{codGara},#{codLotto},#{codStrumento},#{importoLavori},#{importoServizi},#{importoForniture},#{importosubtotale},#{importoSicurezza},#{importoProgettazione},#{impNonAssog},#{importoComplAppalto},#{iva},#{importoIva},#{altreSomme},#{importoDisposizione},#{importoComplIntervento},#{opereUrbanizScomputo},#{modalitaRiaggiudicazione},#{requisitiSettSpec},#{proceduraAcc},#{preinformazione},#{termineRidotto},#{idIndizione},#{dataManifestInteresse},#{numManifestInteresse},#{dataScadRichiestaInvito},#{numImpreseRichiedenti},#{dataInvito},#{numImpreseInvitate},#{dataScadenzaPresOfferta},#{numImpreseOfferenti},#{numOfferteAmmesse},#{numOfferteEscluse},#{impEsclusInsuffGiust},#{offertaMassima},#{offertaMinima},#{valoreSogliaAnomalia},#{numOfferteFuoriSoglia},#{astaElettronica},#{percentRibassoAgg},#{percOffAumento},#{importoAggiudicazione},#{dataVerbAggiudicazione},#{tipoAtto},#{dataAtto},#{numeroAtto},#{flagRichSubappalto},#{flagSubappFinReg},#{flagImporti},#{flagSicurezza},#{flagLivelloProgettuale},#{verificaCampione},#{num},#{flagRelazioneUnica}, #{cui}, #{importoSommeOpzioniRinnovi}, #{importoSommeripetizioni})")
	public void insertFaseAggiudicazione(FaseAggInsertForm form);

	@Update("update w9appa set COD_STRUMENTO = #{codStrumento}, IMPORTO_LAVORI = #{importoLavori}, IMPORTO_SERVIZI = #{importoServizi}, IMPORTO_FORNITURE = #{importoForniture}, IMPORTO_SUBTOTALE = #{importosubtotale}, IMPORTO_ATTUAZIONE_SICUREZZA = #{importoSicurezza}, IMPORTO_PROGETTAZIONE = #{importoProgettazione}, IMP_NON_ASSOG = #{impNonAssog}, IMPORTO_COMPL_APPALTO = #{importoComplAppalto}, IVA = #{iva}, IMPORTO_IVA = #{importoIva}, ALTRE_SOMME = #{altreSomme}, IMPORTO_DISPOSIZIONE = #{importoDisposizione}, IMPORTO_COMPL_INTERVENTO = #{importoComplIntervento}, OPERE_URBANIZ_SCOMPUTO = #{opereUrbanizScomputo}, MODALITA_RIAGGIUDICAZIONE = #{modalitaRiaggiudicazione}, REQUISITI_SETT_SPEC = #{requisitiSettSpec}, PROCEDURA_ACC = #{proceduraAcc}, PREINFORMAZIONE = #{preinformazione}, TERMINE_RIDOTTO = #{termineRidotto}, ID_MODO_INDIZIONE = #{idIndizione}, DATA_MANIF_INTERESSE = #{dataManifestInteresse}, NUM_MANIF_INTERESSE = #{numManifestInteresse}, DATA_SCADENZA_RICHIESTA_INVITO = #{dataScadRichiestaInvito}, NUM_IMPRESE_RICHIEDENTI = #{numImpreseRichiedenti}, DATA_INVITO = #{dataInvito}, NUM_IMPRESE_INVITATE = #{numImpreseInvitate}, DATA_SCADENZA_PRES_OFFERTA = #{dataScadenzaPresOfferta}, NUM_IMPRESE_OFFERENTI = #{numImpreseOfferenti}, NUM_OFFERTE_AMMESSE = #{numOfferteAmmesse}, NUM_OFFERTE_ESCLUSE = #{numOfferteEscluse}, NUM_IMP_ESCL_INSUF_GIUST = #{impEsclusInsuffGiust}, OFFERTA_MASSIMO = #{offertaMassima}, OFFERTA_MINIMA = #{offertaMinima}, VAL_SOGLIA_ANOMALIA = #{valoreSogliaAnomalia}, NUM_OFFERTE_FUORI_SOGLIA = #{numOfferteFuoriSoglia}, ASTA_ELETTRONICA = #{astaElettronica}, PERC_RIBASSO_AGG = #{percentRibassoAgg}, PERC_OFF_AUMENTO = #{percOffAumento}, IMPORTO_AGGIUDICAZIONE = #{importoAggiudicazione}, DATA_VERB_AGGIUDICAZIONE = #{dataVerbAggiudicazione}, TIPO_ATTO = #{tipoAtto}, DATA_ATTO = #{dataAtto}, NUMERO_ATTO = #{numeroAtto}, FLAG_RICH_SUBAPPALTO = #{flagRichSubappalto}, FIN_REGIONALE = #{flagSubappFinReg}, FLAG_IMPORTI = #{flagImporti}, FLAG_SICUREZZA = #{flagSicurezza}, FLAG_LIVELLO_PROGETTUALE = #{flagLivelloProgettuale}, VERIFICA_CAMPIONE = #{verificaCampione}, RELAZIONE_UNICA = #{flagRelazioneUnica}, IMPORTO_OPZIONI = #{importoSommeOpzioniRinnovi}, IMPORTO_RIPETIZIONI = #{importoSommeripetizioni} where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{num}")
	public void updateFaseAggiudicazione(FaseAggInsertForm form);

	@Delete("delete from w9appa where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{num}")
	public void deleteFaseAggiudicazione(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
										 @Param("num") Long num);

	@Select("select count(*) from w9appa where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa}")
	public Long countFaseAggiudicazione(@Param("codGara") Long codGara,
										@Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa);

	@Delete("delete from w9appa where codgara = #{codGara} and num_appa = #{num}")
	public void deleteFaseAggiudicazionePcp(@Param("codGara") Long codGara,
											@Param("num") Long num);

	@Select("select codgara as codGara, codlott as codLotto, num_appa as counter, COD_STRUMENTO as codStrumento, IMPORTO_LAVORI as importoLavori, IMPORTO_SERVIZI as importoServizi, IMPORTO_FORNITURE as importoForniture, IMPORTO_SUBTOTALE as importosubtotale, IMPORTO_ATTUAZIONE_SICUREZZA as importoSicurezza, IMPORTO_PROGETTAZIONE as importoProgettazione, IMP_NON_ASSOG as impNonAssog, IMPORTO_COMPL_APPALTO as importoComplAppalto, IVA as iva, IMPORTO_IVA as importoIva, ALTRE_SOMME as altreSomme, IMPORTO_DISPOSIZIONE as importoDisposizione, IMPORTO_COMPL_INTERVENTO as importoComplIntervento, OPERE_URBANIZ_SCOMPUTO as opereUrbanizScomputo, MODALITA_RIAGGIUDICAZIONE as modalitaRiaggiudicazione, REQUISITI_SETT_SPEC as requisitiSettSpec, PROCEDURA_ACC as proceduraAcc, PREINFORMAZIONE as preinformazione, TERMINE_RIDOTTO as termineRidotto, ID_MODO_INDIZIONE as idIndizione, DATA_MANIF_INTERESSE as dataManifestInteresse, NUM_MANIF_INTERESSE as numManifestInteresse, DATA_SCADENZA_RICHIESTA_INVITO as dataScadRichiestaInvito, NUM_IMPRESE_RICHIEDENTI as numImpreseRichiedenti, DATA_INVITO as dataInvito, NUM_IMPRESE_INVITATE as numImpreseInvitate, DATA_SCADENZA_PRES_OFFERTA as dataScadenzaPresOfferta, NUM_IMPRESE_OFFERENTI as numImpreseOfferenti, NUM_OFFERTE_AMMESSE as numOfferteAmmesse, NUM_OFFERTE_ESCLUSE as numOfferteEscluse, NUM_IMP_ESCL_INSUF_GIUST as impEsclusInsuffGiust, OFFERTA_MASSIMO as offertaMassima, OFFERTA_MINIMA as offertaMinima, VAL_SOGLIA_ANOMALIA as valoreSogliaAnomalia, NUM_OFFERTE_FUORI_SOGLIA as numOfferteFuoriSoglia, ASTA_ELETTRONICA as astaElettronica, PERC_RIBASSO_AGG as percentRibassoAgg, PERC_OFF_AUMENTO as percOffAumento, IMPORTO_AGGIUDICAZIONE as importoAggiudicazione, DATA_VERB_AGGIUDICAZIONE as dataVerbAggiudicazione, TIPO_ATTO as tipoAtto, DATA_ATTO as dataAtto, NUMERO_ATTO as numeroAtto, FLAG_RICH_SUBAPPALTO as flagRichSubappalto,FIN_REGIONALE as flagSubappFinReg, FLAG_IMPORTI as flagImporti, FLAG_SICUREZZA as flagSicurezza, FLAG_LIVELLO_PROGETTUALE as flagLivelloProgettuale,  VERIFICA_CAMPIONE as verificaCampione, codcui as codCui, RELAZIONE_UNICA as flagRelazioneUnica from w9appa where codgara = #{codGara}")
	public List<FaseAggiudicazioneEntry> getFasiAggiudicazione(@Param("codGara") Long codGara);

	// AGGIUDICAZIONE SEMPLIFICATA

	@Select("select codgara as codGara, codlott as codLotto, num_appa as counter, ASTA_ELETTRONICA as astaElettronica, IMPORTO_SUBTOTALE as importoSub,  IMPORTO_ATTUAZIONE_SICUREZZA as importoSicurezza, IMPORTO_COMPL_APPALTO as importoComplAppalto, IVA as iva, IMPORTO_IVA as importoIva, ALTRE_SOMME as altreSomme, IMPORTO_DISPOSIZIONE as importoDisposizione, IMPORTO_COMPL_INTERVENTO as importoComplIntervento, PERC_RIBASSO_AGG as percRibassoAgg, PERC_OFF_AUMENTO as percOffAumento, DATA_VERB_AGGIUDICAZIONE as dataVerbAgg, IMPORTO_AGGIUDICAZIONE as importoAgg, TIPO_ATTO as tipoAtto, DATA_ATTO as dataAtto, NUMERO_ATTO as numeroAtto, DATA_STIPULA as dataStipula, DURATA_CON as durataCon from w9appa where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa}")
	public FaseAggiudicazioneSempEntry getFaseAggiudicazioneSemp(@Param("codGara") Long codGara,
																 @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa);

	@Insert("insert into w9appa (codGara, codlott, num_appa, ASTA_ELETTRONICA, IMPORTO_SUBTOTALE, IMPORTO_ATTUAZIONE_SICUREZZA, IMPORTO_COMPL_APPALTO, IVA, IMPORTO_IVA, ALTRE_SOMME, IMPORTO_DISPOSIZIONE, IMPORTO_COMPL_INTERVENTO, PERC_RIBASSO_AGG, PERC_OFF_AUMENTO, DATA_VERB_AGGIUDICAZIONE, IMPORTO_AGGIUDICAZIONE, TIPO_ATTO, DATA_ATTO, NUMERO_ATTO, DATA_STIPULA, DURATA_CON) values (#{codGara},#{codLotto},1,#{astaElettronica},#{importoSub},#{importoSicurezza},#{importoComplAppalto},#{iva},#{importoIva},#{altreSomme},#{importoDisposizione},#{importoComplIntervento},#{percRibassoAgg},#{percOffAumento},#{dataVerbAgg},#{importoAgg},#{tipoAtto},#{dataAtto},#{numeroAtto},#{dataStipula},#{durataCon})")
	public void insertFaseAggiudicazioneSemp(FaseAggSempInsertForm form);

	@Update("update w9appa set ASTA_ELETTRONICA = #{astaElettronica}, IMPORTO_SUBTOTALE = #{importoSub}, IMPORTO_ATTUAZIONE_SICUREZZA = #{importoSicurezza}, IMPORTO_COMPL_APPALTO = #{importoComplAppalto}, IVA = #{iva}, IMPORTO_IVA = #{importoIva}, ALTRE_SOMME = #{altreSomme}, IMPORTO_DISPOSIZIONE = #{importoDisposizione}, IMPORTO_COMPL_INTERVENTO = #{importoComplIntervento}, PERC_RIBASSO_AGG = #{percRibassoAgg}, PERC_OFF_AUMENTO = #{percOffAumento}, DATA_VERB_AGGIUDICAZIONE = #{dataVerbAgg}, IMPORTO_AGGIUDICAZIONE = #{importoAgg}, TIPO_ATTO = #{tipoAtto}, DATA_ATTO = #{dataAtto}, NUMERO_ATTO = #{numeroAtto}, DATA_STIPULA = #{dataStipula}, DURATA_CON = #{durataCon} where codgara = #{codGara} and codlott = #{codLotto} and num_appa = 1")
	public void updateFaseAggiudicazioneSemp(FaseAggSempInsertForm form);

	@Delete("delete from w9appa where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{num}")
	public void deleteFaseAggiudicazioneSemp(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											 @Param("num") Long num);

	// FASE ADESIONE AD ACCORDO QUADRO

	@Select("select codgara as codGara, codlott as codLotto, num_appa as counter, COD_STRUMENTO as codStrumento, IMPORTO_LAVORI as importoLavori, IMPORTO_SERVIZI as importoServizi, IMPORTO_FORNITURE as importoForniture, IMPORTO_SUBTOTALE as importosubtotale, IMPORTO_ATTUAZIONE_SICUREZZA as importoSicurezza, IMPORTO_PROGETTAZIONE as importoProgettazione, IMP_NON_ASSOG as impNonAssog, IMPORTO_COMPL_APPALTO as importoComplAppalto, IMPORTO_COMPL_INTERVENTO as importoComplIntervento, PERC_RIBASSO_AGG as percentRibassoAgg, PERC_OFF_AUMENTO as percOffAumento, IMPORTO_AGGIUDICAZIONE as importoAggiudicazione, DATA_VERB_AGGIUDICAZIONE as dataVerbAggiudicazione, TIPO_ATTO as tipoAtto, DATA_ATTO as dataAtto, NUMERO_ATTO as numeroAtto, FLAG_RICH_SUBAPPALTO as flagRichSubappalto from w9appa where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{num}")
	public FaseAdesioneAccordoQuadroEntry getFaseAdesioneAccordo(@Param("codGara") Long codGara,
																 @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Insert("insert into w9appa (codgara, codlott, num_appa, COD_STRUMENTO, IMPORTO_LAVORI, IMPORTO_SERVIZI, IMPORTO_FORNITURE, IMPORTO_SUBTOTALE, IMPORTO_ATTUAZIONE_SICUREZZA, IMPORTO_PROGETTAZIONE, IMP_NON_ASSOG, IMPORTO_COMPL_APPALTO, IMPORTO_COMPL_INTERVENTO, PERC_RIBASSO_AGG, PERC_OFF_AUMENTO, IMPORTO_AGGIUDICAZIONE, DATA_VERB_AGGIUDICAZIONE, TIPO_ATTO, DATA_ATTO, NUMERO_ATTO, FLAG_RICH_SUBAPPALTO) values (#{codGara},#{codLotto},#{num},#{codStrumento},#{importoLavori},#{importoServizi},#{importoForniture},#{importosubtotale},#{importoSicurezza},#{importoProgettazione},#{impNonAssog},#{importoComplAppalto},#{importoComplIntervento},#{percentRibassoAgg},#{percOffAumento},#{importoAggiudicazione},#{dataVerbAggiudicazione},#{tipoAtto},#{dataAtto},#{numeroAtto},#{flagRichSubappalto})")
	public void insertFaseAdesioneAccordo(FaseAdesioneAccordoQuadroInsertForm form);

	@Update("update w9appa set COD_STRUMENTO = #{codStrumento}, IMPORTO_LAVORI = #{importoLavori}, IMPORTO_SERVIZI = #{importoServizi}, IMPORTO_FORNITURE = #{importoForniture}, IMPORTO_SUBTOTALE = #{importosubtotale}, IMPORTO_ATTUAZIONE_SICUREZZA = #{importoSicurezza}, IMPORTO_PROGETTAZIONE = #{importoProgettazione}, IMP_NON_ASSOG = #{impNonAssog}, IMPORTO_COMPL_APPALTO = #{importoComplAppalto}, IMPORTO_COMPL_INTERVENTO = #{importoComplIntervento}, PERC_RIBASSO_AGG = #{percentRibassoAgg}, PERC_OFF_AUMENTO = #{percOffAumento}, IMPORTO_AGGIUDICAZIONE = #{importoAggiudicazione}, DATA_VERB_AGGIUDICAZIONE = #{dataVerbAggiudicazione}, TIPO_ATTO = #{tipoAtto}, DATA_ATTO = #{dataAtto}, NUMERO_ATTO = #{numeroAtto}, FLAG_RICH_SUBAPPALTO = #{flagRichSubappalto} where codgara = #{codGara} and codlott = #{codLotto} and num_appa = 1")
	public void updateFaseAdesioneAccordo(FaseAdesioneAccordoQuadroInsertForm form);

	@Delete("delete from w9appa where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{num}")
	public void deleteFaseAdesioneAccordo(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
										  @Param("num") Long num);

	// FASE INIZIALE ESECUZIONE CONTRATTO

	@Select("select num_iniz as num,num_appa as numAppa, codgara as codGara, codlott as codLotto, data_stipula as dataStipula, data_esecutivita as dataEsecutivita, importo_cauzione as importoCauzione, incontri_vigil as incontriVigil, data_ini_prog_esec as dataInizioProg, data_app_prog_esec as dataApprovazioneProg, flag_frazionata as frazionata, data_verbale_cons as dataVerbaleCons, data_verbale_def as dataVerbaleDef, flag_riserva as riserva, data_verb_inizio as dataVerbInizio, data_termine as dataTermine from w9iniz where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = (select max(num_iniz) from w9iniz where codgara = #{codGara} and codlott = #{codLotto})")
	public FaseInizialeEsecuzioneEntry getFaseInizialeEsecuzioneMaxNumIniz(@Param("codGara") Long codGara,
																		   @Param("codLotto") Long codLotto);

	@Select("select codgara as codGara, codlott as codLotto, data_stipula as dataStipula, data_esecutivita as dataEsecutivita, importo_cauzione as importoCauzione, incontri_vigil as incontriVigil, data_ini_prog_esec as dataInizioProg, data_app_prog_esec as dataApprovazioneProg, flag_frazionata as frazionata, data_verbale_cons as dataVerbaleCons, data_verbale_def as dataVerbaleDef, flag_riserva as riserva, data_verb_inizio as dataVerbInizio, data_termine as dataTermine, num_appa as numAppa, codcontratto as codContratto from w9iniz where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public FaseInizialeEsecuzioneEntry getFaseInizialeEsecuzione(@Param("codGara") Long codGara,
																 @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("select codgara as codGara, codlott as codLotto, num_iniz as num, data_stipula as dataStipula, data_esecutivita as dataEsecutivita, importo_cauzione as importoCauzione, incontri_vigil as incontriVigil, data_ini_prog_esec as dataInizioProg, data_app_prog_esec as dataApprovazioneProg, flag_frazionata as frazionata, data_verbale_cons as dataVerbaleCons, data_verbale_def as dataVerbaleDef, flag_riserva as riserva, data_verb_inizio as dataVerbInizio, data_termine as dataTermine, num_appa as numAppa, codcontratto as codContratto from w9iniz where codcontratto = #{codcontratto}")
	public FaseInizialeEsecuzioneEntry getFaseInizialeEsecuzioneByCodContratto(@Param("codcontratto") String codcontratto);

	@Select("select codgara as codGara, codlott as codLotto, data_stipula as dataStipula, data_esecutivita as dataEsecutivita, importo_cauzione as importoCauzione, incontri_vigil as incontriVigil, data_ini_prog_esec as dataInizioProg, data_app_prog_esec as dataApprovazioneProg, flag_frazionata as frazionata, data_verbale_cons as dataVerbaleCons, data_verbale_def as dataVerbaleDef, flag_riserva as riserva, data_verb_inizio as dataVerbInizio, data_termine as dataTermine, data_decorrenza as dataDecorrenza, data_scadenza as dataScadenza, num_appa as numAppa from w9iniz where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public FaseInizialeEsecuzioneEntry getFaseInizialeSottoscrizioneContratto(@Param("codGara") Long codGara,
																			  @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("select codgara as codGara, codlott as codLotto, num_iniz as num, data_stipula as dataStipula, data_esecutivita as dataEsecutivita, importo_cauzione as importoCauzione, incontri_vigil as incontriVigil, data_ini_prog_esec as dataInizioProg, data_app_prog_esec as dataApprovazioneProg, flag_frazionata as frazionata, data_verbale_cons as dataVerbaleCons, data_verbale_def as dataVerbaleDef, flag_riserva as riserva, data_verb_inizio as dataVerbInizio, data_termine as dataTermine, data_decorrenza as dataDecorrenza, data_scadenza as dataScadenza, num_appa as numAppa from w9iniz where codgara = #{codGara} and codlott = #{codLotto} and codcontratto = #{codcontratto}")
	public FaseInizialeEsecuzioneEntry getFaseInizialeSottoscrizioneContrattoByCodContratto(@Param("codGara") Long codGara,
																							@Param("codLotto") Long codLotto, @Param("codcontratto") String codcontratto);

	@Select("select codgara as codGara, codlott as codLotto, data_stipula as dataStipula, data_esecutivita as dataEsecutivita, importo_cauzione as importoCauzione, incontri_vigil as incontriVigil, data_ini_prog_esec as dataInizioProg, data_app_prog_esec as dataApprovazioneProg, flag_frazionata as frazionata, data_verbale_cons as dataVerbaleCons, data_verbale_def as dataVerbaleDef, flag_riserva as riserva, data_verb_inizio as dataVerbInizio, data_termine as dataTermine from w9iniz where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa}")
	public FaseInizialeEsecuzioneEntry getFaseInizialeEsecuzioneByNumAppa(@Param("codGara") Long codGara,
																		  @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa);

	@Select("select codgara as codGara, codlott as codLotto, data_guce as dataGuce, data_guri as dataGuri, data_albo as dataAlbo, quotidiani_naz as quotidianiNaz, quotidiani_reg as quotidianiReg, profilo_committente as profiloCommittente, sito_ministero_inf_trasp as sitoMinInfTrasp, sito_osservatorio_cp as sitoOsservatorio from w9pues  where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	FaseInizPubbEsitoForm getFaseInizialeEsecuzionePubbEsito(@Param("codGara") Long codGara,
															 @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Insert("insert into w9pues (codgara, codlott, num_iniz, num_pues, data_guce, data_guri, data_albo, quotidiani_naz, quotidiani_reg, profilo_committente, sito_ministero_inf_trasp, sito_osservatorio_cp ) values (#{codGara},#{codLotto},#{numIniziale},1,#{dataGuce},#{dataGuri},#{dataAlbo},#{quotidianiNaz},#{quotidianiReg},#{profiloCommittente},#{sitoMinInfTrasp},#{sitoOsservatorio})")
	public void insertFaseInizPubbEsito(FaseInizPubbEsitoForm form);

	@Insert(" insert into w9iniz ( codgara, codlott, num_iniz, data_stipula, data_esecutivita, importo_cauzione, incontri_vigil, data_ini_prog_esec, data_app_prog_esec, flag_frazionata, data_verbale_cons, data_verbale_def, flag_riserva, data_verb_inizio, data_termine, num_appa, data_decorrenza, data_scadenza, codcontratto) values (#{codGara},#{codLotto},#{num}, #{dataStipula},#{dataEsecutivita},#{importoCauzione},#{incontriVigil},#{dataInizioProg},#{dataApprovazioneProg},#{frazionata},#{dataVerbaleCons},#{dataVerbaleDef},#{riserva},#{dataVerbInizio},#{dataTermine}, #{numAppa}, #{dataDecorrenza}, #{dataScadenza},#{codContratto})")
	public void insertFaseInizialeEsecuzione(FaseInizialeEsecuzioneInsertForm form);

	@Update("update w9iniz set data_stipula = #{dataStipula}, data_esecutivita = #{dataEsecutivita}, data_decorrenza = #{dataDecorrenza}, data_scadenza = #{dataScadenza}, importo_cauzione = #{importoCauzione}  where codgara = #{codGara} and codlott = #{codLotto} and codcontratto = #{codContratto}")
	public void updateFaseInizialeSottoscrizioneContrattoWithCodContratto(FaseInizialeEsecuzioneInsertForm form);

	@Update(" update w9iniz set data_stipula = #{dataStipula}, data_esecutivita = #{dataEsecutivita}, importo_cauzione = #{importoCauzione}, incontri_vigil = #{incontriVigil}, data_ini_prog_esec = #{dataInizioProg}, data_app_prog_esec = #{dataApprovazioneProg}, flag_frazionata = #{frazionata}, data_verbale_cons = #{dataVerbaleCons}, data_verbale_def = #{dataVerbaleDef}, flag_riserva = #{riserva}, data_verb_inizio = #{dataVerbInizio}, data_termine = #{dataTermine} where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public void updateFaseInizialeEsecuzione(FaseInizialeEsecuzioneInsertForm form);

	@Update(" update w9iniz set incontri_vigil = #{incontriVigil}, data_ini_prog_esec = #{dataInizioProg}, data_app_prog_esec = #{dataApprovazioneProg}, flag_frazionata = #{frazionata}, data_verbale_cons = #{dataVerbaleCons}, data_verbale_def = #{dataVerbaleDef}, flag_riserva = #{riserva}, data_verb_inizio = #{dataVerbInizio}, data_termine = #{dataTermine} where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public void updateFaseInizialeEsecuzionePcp(FaseInizialeEsecuzioneInsertForm form);

	@Update("update w9pues set data_guce = #{dataGuce}, data_guri = #{dataGuri}, data_albo = #{dataAlbo}, quotidiani_naz = #{quotidianiNaz}, quotidiani_reg = #{quotidianiReg}, profilo_committente = #{profiloCommittente}, sito_ministero_inf_trasp = #{sitoMinInfTrasp}, sito_osservatorio_cp = #{sitoOsservatorio} where codgara = #{codGara} and codlott = #{codLotto}")
	public void updateFaseInizPubbEsito(FaseInizPubbEsitoForm form);

	@Delete("delete from w9iniz where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public void deleteFaseInizialeEsecuzione(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											 @Param("num") Long num);

	@Delete("delete from w9iniz where codgara = #{codGara}")
	public void deleteFaseInizialeEsecuzionePcp(@Param("codGara") Long codGara);

	@Delete("delete from w9pues where codgara = #{codGara} and codlott = #{codLotto}  and num_iniz = #{num}")
	public void deleteFaseInizPubbEsito(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
										@Param("num") Long num);

	@Delete("delete from w9pues where codgara = #{codGara}")
	public void deleteFaseInizPubbEsitoAll(@Param("codGara") Long codGara);

	@Select("select count(*) from w9iniz where codgara = #{codGara} and codlott = #{codLotto}")
	public Long countFaseIniziale(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Update("update w9iniz set data_stipula = #{dataStipula}, data_esecutivita = #{dataEsecutivita}, data_decorrenza = #{dataDecorrenza}, data_scadenza = #{dataScadenza}, importo_cauzione = #{importoCauzione}  where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public void updateFaseInizialeSottoscrizioneContratto(FaseInizialeEsecuzioneInsertForm form);

	@Update("update w9iniz set codcontratto = #{idContratto}  where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public void setIdContrattoW9iniz(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("num") Long num, @Param("idContratto") String idContratto);

	@Update("update w9iniz set codcontratto = null  where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public void setIdContrattoNullW9iniz(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("select codcontratto from w9iniz where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public String getIdContrattoW9iniz(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("num") Long num);

	// FASE INIZIALE SEMPLIFICATA

	@Select("select codgara as codGara, codlott as codLotto, num_iniz as num, data_stipula as dataStipula, flag_riserva as flagRiserva, data_verb_inizio as dataVerbale, data_termine as dataTermine from w9iniz where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public FaseInizialeSemplificataEntry getFaseInizialeSemplificata(@Param("codGara") Long codGara,
																	 @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Insert("insert into w9iniz (codgara, codlott, num_iniz, data_stipula, flag_riserva, data_verb_inizio, data_termine, num_appa) values (#{codGara},#{codLotto},#{num},#{dataStipula},#{flagRiserva},#{dataVerbale},#{dataTermine},#{numAppa})")
	public void insertFaseInizialeSemplificata(FaseInizialeSemplificataInsertForm form);

	@Update("update w9iniz set data_stipula = #{dataStipula}, flag_riserva = #{flagRiserva}, data_verb_inizio = #{dataVerbale}, data_termine = #{dataTermine} where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public void updateFaseInizialeSemplificata(FaseInizialeSemplificataInsertForm form);

	@Delete("delete from w9iniz where codgara = #{codGara} and codlott = #{codLotto}  and num_iniz = #{num}")
	public void deleteFaseInizialeSemplificata(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											   @Param("num") Long num);

	// FASE STIPULA ACCORDO QUADRO

	@Select("select codgara as codGara, codlott as codLotto, data_stipula as dataStipula, data_decorrenza as dataDecorrenza, data_scadenza as dataScadenza from w9iniz where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public FaseStipulaAccordoQuadroEntry getFaseStipulaAccordoQuadro(@Param("codGara") Long codGara,
																	 @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("select codgara as codGara, codlott as codLotto, data_guce as dataGuce, data_guri as dataGuri, data_albo as dataAlbo, quotidiani_naz as quotidianiNaz, quotidiani_reg as quotidianiReg, profilo_committente as profiloCommittente, sito_ministero_inf_trasp as sitoMinInfTrasp, sito_osservatorio_cp as sitoOsservatorio from w9pues  where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	FaseStipulaPubbEsitoForm getFaseStipulaPubbEsito(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
													 @Param("num") Long num);

	@Insert("insert into w9pues (codgara, codlott, num_iniz, num_pues, data_guce, data_guri, data_albo, quotidiani_naz, quotidiani_reg, profilo_committente, sito_ministero_inf_trasp, sito_osservatorio_cp ) values (#{codGara},#{codLotto},#{numIniziale},1,#{dataGuce},#{dataGuri},#{dataAlbo},#{quotidianiNaz},#{quotidianiReg},#{profiloCommittente},#{sitoMinInfTrasp},#{sitoOsservatorio})")
	public void insertFaseStipulaPubbEsito(FaseStipulaPubbEsitoForm form);

	@Insert(" insert into w9iniz ( codgara, codlott, num_appa, num_iniz, data_stipula, data_decorrenza, data_scadenza) values (#{codGara},#{codLotto},1,#{num}, #{dataStipula},#{dataDecorrenza},#{dataScadenza})")
	public void insertFaseStipulaAccordoQuadro(FaseStipulaAccordoQuadroInsertForm form);

	@Update(" update w9iniz set data_stipula = #{dataStipula}, data_decorrenza=#{dataDecorrenza}, data_scadenza = #{dataScadenza} where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public void updateFaseStipulaAccordoQuadro(FaseStipulaAccordoQuadroInsertForm form);

	@Update("update w9pues set data_guce = #{dataGuce}, data_guri = #{dataGuri}, data_albo = #{dataAlbo}, quotidiani_naz = #{quotidianiNaz}, quotidiani_reg = #{quotidianiReg}, profilo_committente = #{profiloCommittente}, sito_ministero_inf_trasp = #{sitoMinInfTrasp}, sito_osservatorio_cp = #{sitoOsservatorio} where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{numIniziale}")
	public void updateFaseStipulaPubbEsito(FaseStipulaPubbEsitoForm form);

	@Delete("delete from w9iniz where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public void deleteFaseStipulaAccordoQuadro(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											   @Param("num") Long num);

	@Delete("delete from w9pues where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public void deleteFaseStipulaPubbEsito(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
										   @Param("num") Long num);

	// FASE AVANZAMENTO

	@Select("select codgara as codGara, codlott as codLotto, num_avan as num, data_raggiungimento as dataRaggiungimento, denom_avanzamento as denomAvanzamento, importo_sal as importoSal, data_certificato as dataCertificato, importo_certificato as importoCertificato, flag_ritardo as flagRitardo, num_giorni_scost as numGiorniScost, num_giorni_proroga as numGiorniProroga, flag_pagamento as flagPagamento, importo_anticipazione as importoAnticipazione, data_anticipazione as dataAnticipazione, subappalti as subappalti, num_appa as numAppa from w9avan where codgara = #{codGara} and codlott = #{codLotto} and num_avan = #{num}")
	public FaseAvanzamentoEntry getFaseAvanzamento(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												   @Param("num") Long num);

	@Insert("insert into w9avan ( codgara, codlott, num_avan, data_raggiungimento, denom_avanzamento, importo_sal, data_certificato, importo_certificato, flag_ritardo, num_giorni_scost, num_giorni_proroga, flag_pagamento, importo_anticipazione, data_anticipazione, subappalti, num_appa) values (#{codGara},#{codLotto},#{num},#{dataRaggiungimento},#{denomAvanzamento},#{importoSal},#{dataCertificato},#{importoCertificato},#{flagRitardo},#{numGiorniScost},#{numGiorniProroga},#{flagPagamento},#{importoAnticipazione},#{dataAnticipazione},#{subappalti}, #{numAppa})")
	public void insertFaseAvanzamento(FaseAvanzamentoInsertForm form);

	@Update("update w9avan set data_raggiungimento = #{dataRaggiungimento}, denom_avanzamento = #{denomAvanzamento}, importo_sal = #{importoSal}, data_certificato = #{dataCertificato}, importo_certificato = #{importoCertificato}, flag_ritardo = #{flagRitardo}, num_giorni_scost = #{numGiorniScost}, num_giorni_proroga = #{numGiorniProroga}, flag_pagamento = #{flagPagamento}, importo_anticipazione = #{importoAnticipazione}, data_anticipazione = #{dataAnticipazione}, subappalti = #{subappalti} where codgara = #{codGara} and codlott = #{codLotto} and num_avan = #{num}")
	public void updateFaseAvanzamento(FaseAvanzamentoInsertForm form);

	@Delete("delete from w9avan where codgara = #{codGara} and codlott = #{codLotto} and num_avan = #{num}")
	public void deleteFaseAvanzamento(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
									  @Param("num") Long num);

	@Delete("delete from w9avan where codgara = #{codGara}")
	public void deleteFaseAvanzamentoAll(@Param("codGara") Long codGara);

	@Select("select codgara as codGara, codlott as codLotto, num_avan as num, data_raggiungimento as dataRaggiungimento, denom_avanzamento as denomAvanzamento, importo_sal as importoSal, data_certificato as dataCertificato, importo_certificato as importoCertificato, flag_ritardo as flagRitardo, num_giorni_scost as numGiorniScost, num_giorni_proroga as numGiorniProroga, flag_pagamento as flagPagamento, importo_anticipazione as importoAnticipazione, data_anticipazione as dataAnticipazione, subappalti as subappalti from w9avan where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa}")
	public List<FaseAvanzamentoEntry> getFasiAvanzamentoLotto(@Param("codGara") Long codGara,
															  @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa);

	// FASE AVANZAMENTO SEMPLIFICATO

	@Select("select codgara as codGara, codlott as codLotto, num_avan as num, data_certificato as dataCertificato, importo_certificato as importoCertificato from w9avan where codgara = #{codGara} and codlott = #{codLotto} and num_avan = #{num}")
	public FaseAvanzamentoSempEntry getFaseAvanzamentoSemp(@Param("codGara") Long codGara,
														   @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Insert("insert into w9avan ( codgara, codlott, num_avan, data_certificato, importo_certificato, num_appa) values (#{codGara},#{codLotto},#{num},#{dataCertificato},#{importoCertificato},#{numAppa})")
	public void insertFaseAvanzamentoSemp(FaseAvanzamentoSempInsertForm form);

	@Update("update w9avan set data_certificato = #{dataCertificato}, importo_certificato = #{importoCertificato} where codgara = #{codGara} and codlott = #{codLotto} and num_avan = #{num}")
	public void updateFaseAvanzamentoSemp(FaseAvanzamentoSempInsertForm form);

	@Delete("delete from w9avan where codgara = #{codGara} and codlott = #{codLotto} and num_avan = #{num}")
	public void deleteFaseAvanzamentoSemp(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
										  @Param("num") Long num);

	// FASE CONCLUSIONE

	@Select("select codgara as codGara, codlott as codLotto, num_conc as num, intantic as interruzioneAnticipata, id_motivo_interr as motivoInterruzione, id_motivo_risol as motivoRisoluzione, data_risoluzione as dataRisoluzione, flag_oneri as flagOneri, oneri_risoluzione as importoOneri, flag_polizza as flagPolizza, data_verbale_consegna as dataVerbaleConsegna, termine_contratto_ult as dataTermineContrattuale, data_ultimazione as dataUltimazione, num_infortuni as numInfortuni, num_inf_perm as numInfortuniPermanenti, num_inf_mort as numInfortuniMortali, num_giorni_proroga as numgiorniProroga, ore_lavorate as oreLavorate, num_appa as numAppa from w9conc where codgara = #{codGara} and codlott = #{codLotto} and num_conc = #{num}")
	public FaseConclusioneEntry getFaseConclusioneContratto(@Param("codGara") Long codGara,
															@Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("select codgara as codGara, codlott as codLotto, num_conc as num, intantic as interruzioneAnticipata, id_motivo_interr as motivoInterruzione, id_motivo_risol as motivoRisoluzione, data_risoluzione as dataRisoluzione, flag_oneri as flagOneri, oneri_risoluzione as importoOneri, flag_polizza as flagPolizza, data_verbale_consegna as dataVerbaleConsegna, termine_contratto_ult as dataTermineContrattuale, data_ultimazione as dataUltimazione, num_infortuni as numInfortuni, num_inf_perm as numInfortuniPermanenti, num_inf_mort as numInfortuniMortali, num_giorni_proroga as numgiorniProroga, ore_lavorate as oreLavorate from w9conc where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa}")
	public FaseConclusioneEntry getFaseConclusioneContrattoByNumAppa(@Param("codGara") Long codGara,
																	 @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa);

	@Insert("insert into w9conc (codgara, codlott, num_conc, intantic, id_motivo_interr, id_motivo_risol, data_risoluzione, flag_oneri, oneri_risoluzione, flag_polizza, data_verbale_consegna,  termine_contratto_ult, data_ultimazione, num_infortuni, num_inf_perm, num_inf_mort, num_giorni_proroga, ore_lavorate, num_appa ) values (#{codGara},#{codLotto},#{num},#{interruzioneAnticipata},#{motivoInterruzione},#{motivoRisoluzione},#{dataRisoluzione},#{flagOneri},#{importoOneri},#{flagPolizza},#{dataVerbaleConsegna},#{dataTermineContrattuale},#{dataUltimazione},#{numInfortuni},#{numInfortuniPermanenti},#{numInfortuniMortali},#{numgiorniProroga},#{oreLavorate}, #{numAppa})")
	public void insertFaseConclusioneContratto(FaseConclusioneInsertForm form);

	@Update("update w9conc set intantic = #{interruzioneAnticipata}, id_motivo_interr = #{motivoInterruzione}, id_motivo_risol = #{motivoRisoluzione}, data_risoluzione = #{dataRisoluzione}, flag_oneri = #{flagOneri}, oneri_risoluzione = #{importoOneri}, flag_polizza = #{flagPolizza}, data_verbale_consegna = #{dataVerbaleConsegna}, termine_contratto_ult = #{dataTermineContrattuale}, data_ultimazione = #{dataUltimazione}, num_infortuni = #{numInfortuni}, num_inf_perm = #{numInfortuniPermanenti}, num_inf_mort = #{numInfortuniMortali}, num_giorni_proroga = #{numgiorniProroga}, ore_lavorate = #{oreLavorate} where codgara = #{codGara} and codlott = #{codLotto} and num_conc = #{num}")
	public void updateFaseConclusioneContratto(FaseConclusioneInsertForm form);

	@Update("update w9conc set id_motivo_interr = #{motivoInterruzione}, id_motivo_risol = #{motivoRisoluzione}, data_risoluzione = #{dataRisoluzione}, flag_oneri = #{flagOneri}, oneri_risoluzione = #{importoOneri}, flag_polizza = #{flagPolizza}, data_verbale_consegna = #{dataVerbaleConsegna}, termine_contratto_ult = #{dataTermineContrattuale}, data_ultimazione = #{dataUltimazione}, num_infortuni = #{numInfortuni}, num_inf_perm = #{numInfortuniPermanenti}, num_inf_mort = #{numInfortuniMortali}, num_giorni_proroga = #{numgiorniProroga}, ore_lavorate = #{oreLavorate} where codgara = #{codGara} and codlott = #{codLotto} and num_conc = #{num}")
	public void updateFaseConclusioneContrattoWithoutIntantic(FaseConclusioneInsertForm form);

	@Delete("delete from w9conc where codgara = #{codGara} and codlott = #{codLotto} and num_conc = #{num}")
	public void deleteFaseConclusioneContratto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											   @Param("num") Long num);

	@Delete("delete from w9conc where codgara = #{codGara}")
	public void deleteFaseConclusioneContrattoAll(@Param("codGara") Long codGara);

	// FASE CONCLUSIONE SEMPLIFICATA

	@Select("select codgara as codGara, codlott as codLotto, num_conc as num, intantic as interruzioneAnticipata, id_motivo_interr as motivoInterruzione, id_motivo_risol as motivoRisoluzione, data_risoluzione as dataRisoluzione, flag_oneri as flagOneri, oneri_risoluzione as importoOneri, flag_polizza as flagPolizza, data_ultimazione as dataUltimazione, ore_lavorate as oreLavorate from w9conc where codgara = #{codGara} and codlott = #{codLotto} and num_conc = #{num}")
	public FaseConclusioneSempEntry getFaseConclusioneSempContratto(@Param("codGara") Long codGara,
																	@Param("codLotto") Long codLotto, @Param("num") Long num);

	@Insert("insert into w9conc (codgara, codlott, num_conc, num_appa, intantic, id_motivo_interr, id_motivo_risol, data_risoluzione, flag_oneri, oneri_risoluzione, flag_polizza, data_ultimazione, ore_lavorate ) values (#{codGara},#{codLotto},#{num},1,#{interruzioneAnticipata},#{motivoInterruzione},#{motivoRisoluzione},#{dataRisoluzione},#{flagOneri},#{importoOneri},#{flagPolizza},#{dataUltimazione},#{oreLavorate})")
	public void insertFaseConclusioneSempContratto(FaseConclusioneSempInsertForm form);

	@Update("update w9conc set intantic = #{interruzioneAnticipata}, id_motivo_interr = #{motivoInterruzione}, id_motivo_risol = #{motivoRisoluzione}, data_risoluzione = #{dataRisoluzione}, flag_oneri = #{flagOneri}, oneri_risoluzione = #{importoOneri}, flag_polizza = #{flagPolizza}, data_ultimazione = #{dataUltimazione}, ore_lavorate = #{oreLavorate} where codgara = #{codGara} and codlott = #{codLotto} and num_conc = #{num}")
	public void updateFaseConclusioneSempContratto(FaseConclusioneSempInsertForm form);

	@Delete("delete from w9conc where codgara = #{codGara} and codlott = #{codLotto} and num_conc = #{num}")
	public void deleteFaseConclusioneSempContratto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												   @Param("num") Long num);

	// FASE COLLAUDO

	@Select("select codgara as codGara, codlott as codLotto, num_coll as num, data_regolare_esec as dataCertEsecuzione, data_collaudo_stat as dataCollaudoStatico, modo_collaudo as modalitaCollaudo, data_nomina_coll as dataNomina, data_inizio_oper as dataInizioOperazioni, data_cert_collaudo as dataRedazioneCertificato, data_delibera as dataDelibera, esito_collaudo as esitoCollaudo, imp_finale_lavori as importoFinaleLavori, imp_finale_servizi as importoFinaleServizi, imp_finale_fornit as importoFinaleForniture, imp_subtotale as importoSubtotale, imp_finale_secur as importoFinaleSicurezza, imp_progettazione as importoProgettazione, imp_compl_appalto as importoComplessivoAppalto, imp_disposizione as importoDisposizione, imp_compl_intervento as importoComplessivoIntervento, lavori_estesi as lavoriEstesi, amm_importo_def as importoEventualeDefAmm, amm_num_definite as numeroRiserveDefiniteAmm, amm_num_dadef as numeroRiserveDaDefinireAmm, amm_importo_rich as importoTotaleRichiestoAmm, arb_importo_def as importoEventualeDefArb, arb_num_definite as numeroRiserveDefiniteArb, arb_num_dadef as numeroRiserveDaDefinireArb, arb_importo_rich as importoTotaleRichiestoArb, giu_importo_def as importoEventualeDefGiu, giu_num_definite as numeroRiserveDefiniteGiu, giu_num_dadef as numeroRiserveDaDefinireGiu, giu_importo_rich  as importoTotaleRichiestoGiu, tra_importo_def as importoEventualeDefTra, tra_num_definite as numeroRiserveDefiniteTra, tra_num_dadef as numeroRiserveDaDefinireTra, tra_importo_rich as importoTotaleRichiestoTra, flag_singolo_commissione as flagSingoloCommissione, data_approvazione as dataApprovazione, flag_importi as flagImporti, flag_sicurezza as flagSicurezza, flag_subappaltatori as flagSubappaltatori, data_inizio_amm_riserve as dataInizioRiserveAmm, data_fine_amm_riserve as dataFineRiserveAmm, data_inizio_arb_riserve as dataInizioRiserveArb, data_fine_arb_riserve as dataFineRiserveArb, data_inizio_giu_riserve as dataInizioRiserveGiu, data_fine_giu_riserve as dataFineRiserveGiu, data_inizio_tra_riserve as dataInizioRiserveTra, data_fine_tra_riserve as dataFineRiserveTra, tipo_collaudo as tipoCollaudo, IMP_NON_ASSOG as impNonAssog, IMP_FINALE_OPZIONI as impFinaleOpzioni, IMP_FINALE_RIPETIZIONI as impFinaleRipetizioni, NUM_RISERVE as numRiserve, ONERI_DERIVANTI as oneriDerivanti, num_appa as numAppa  from w9coll  where codgara= #{codGara} and codlott = #{codLotto} and num_coll = #{num}")
	public FaseCollaudoEntry getFaseCollaudo(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											 @Param("num") Long num);

	@Insert("insert into w9coll (codgara, codlott, num_coll, data_regolare_esec, data_collaudo_stat, modo_collaudo, data_nomina_coll, data_inizio_oper, data_cert_collaudo, data_delibera, esito_collaudo, imp_finale_lavori, imp_finale_servizi, imp_finale_fornit, imp_subtotale, imp_finale_secur, imp_progettazione, imp_compl_appalto, imp_disposizione, imp_compl_intervento, lavori_estesi, amm_importo_def, amm_num_definite, amm_num_dadef, amm_importo_rich, arb_importo_def, arb_num_definite, arb_num_dadef, arb_importo_rich, giu_importo_def, giu_num_definite, giu_num_dadef, giu_importo_rich, tra_importo_def, tra_num_definite, tra_num_dadef, tra_importo_rich, flag_singolo_commissione, data_approvazione, flag_importi, flag_sicurezza, flag_subappaltatori, data_inizio_amm_riserve, data_fine_amm_riserve, data_inizio_arb_riserve, data_fine_arb_riserve, data_inizio_giu_riserve, data_fine_giu_riserve, data_inizio_tra_riserve, data_fine_tra_riserve, num_appa, tipo_collaudo, IMP_NON_ASSOG, IMP_FINALE_OPZIONI, IMP_FINALE_RIPETIZIONI, NUM_RISERVE, ONERI_DERIVANTI) values (#{codGara},#{codLotto},#{num},#{dataCertEsecuzione},#{dataCollaudoStatico},#{modalitaCollaudo},#{dataNomina},#{dataInizioOperazioni},#{dataRedazioneCertificato},#{dataDelibera},#{esitoCollaudo},#{importoFinaleLavori},#{importoFinaleServizi},#{importoFinaleForniture},#{importoSubtotale},#{importoFinaleSicurezza},#{importoProgettazione},#{importoComplessivoAppalto},#{importoDisposizione},#{importoComplessivoIntervento},#{lavoriEstesi},#{importoEventualeDefAmm},#{numeroRiserveDefiniteAmm},#{numeroRiserveDaDefinireAmm},#{importoTotaleRichiestoAmm},#{importoEventualeDefArb},#{numeroRiserveDefiniteArb},#{numeroRiserveDaDefinireArb},#{importoTotaleRichiestoArb},#{importoEventualeDefGiu},#{numeroRiserveDefiniteGiu},#{numeroRiserveDaDefinireGiu},#{importoTotaleRichiestoGiu},#{importoEventualeDefTra},#{numeroRiserveDefiniteTra},#{numeroRiserveDaDefinireTra},#{importoTotaleRichiestoTra},#{flagSingoloCommissione},#{dataApprovazione},#{flagImporti},#{flagSicurezza},#{flagSubappaltatori},#{dataInizioRiserveAmm},#{dataFineRiserveAmm},#{dataInizioRiserveArb},#{dataFineRiserveArb},#{dataInizioRiserveGiu},#{dataFineRiserveGiu},#{dataInizioRiserveTra},#{dataFineRiserveTra}, #{numAppa}, #{tipoCollaudo}, #{impNonAssog}, #{impFinaleOpzioni}, #{impFinaleRipetizioni},#{numRiserve},#{oneriDerivanti})")
	public void insertFaseCollaudo(FaseCollaudoInsertForm form);

	@Update("update w9coll set data_regolare_esec = #{dataCertEsecuzione}, data_collaudo_stat = #{dataCollaudoStatico}, modo_collaudo = #{modalitaCollaudo}, data_nomina_coll = #{dataNomina}, data_inizio_oper = #{dataInizioOperazioni}, data_cert_collaudo = #{dataRedazioneCertificato}, data_delibera = #{dataDelibera}, esito_collaudo = #{esitoCollaudo}, imp_finale_lavori = #{importoFinaleLavori}, imp_finale_servizi = #{importoFinaleServizi}, imp_finale_fornit = #{importoFinaleForniture}, imp_subtotale = #{importoSubtotale}, imp_finale_secur = #{importoFinaleSicurezza}, imp_progettazione = #{importoProgettazione}, imp_compl_appalto = #{importoComplessivoAppalto}, imp_disposizione = #{importoDisposizione}, imp_compl_intervento = #{importoComplessivoIntervento}, lavori_estesi = #{lavoriEstesi}, amm_importo_def = #{importoEventualeDefAmm}, amm_num_definite = #{numeroRiserveDefiniteAmm}, amm_num_dadef = #{numeroRiserveDaDefinireAmm}, amm_importo_rich = #{importoTotaleRichiestoAmm}, arb_importo_def = #{importoEventualeDefArb}, arb_num_definite = #{numeroRiserveDefiniteArb}, arb_num_dadef = #{numeroRiserveDaDefinireArb}, arb_importo_rich = #{importoTotaleRichiestoArb}, giu_importo_def = #{importoEventualeDefGiu}, giu_num_definite = #{numeroRiserveDefiniteGiu}, giu_num_dadef = #{numeroRiserveDaDefinireGiu}, giu_importo_rich = #{importoTotaleRichiestoGiu}, tra_importo_def = #{importoEventualeDefTra}, tra_num_definite = #{numeroRiserveDefiniteTra}, tra_num_dadef = #{numeroRiserveDaDefinireTra}, tra_importo_rich = #{importoTotaleRichiestoTra}, flag_singolo_commissione = #{flagSingoloCommissione}, data_approvazione = #{dataApprovazione}, flag_importi = #{flagImporti}, flag_sicurezza = #{flagSicurezza}, flag_subappaltatori = #{flagSubappaltatori}, data_inizio_amm_riserve = #{dataInizioRiserveAmm}, data_fine_amm_riserve = #{dataFineRiserveAmm}, data_inizio_arb_riserve = #{dataInizioRiserveArb}, data_fine_arb_riserve = #{dataFineRiserveArb}, data_inizio_giu_riserve = #{dataInizioRiserveGiu}, data_fine_giu_riserve = #{dataFineRiserveGiu}, data_inizio_tra_riserve = #{dataInizioRiserveTra}, data_fine_tra_riserve = #{dataFineRiserveTra}, tipo_collaudo = #{tipoCollaudo}, IMP_NON_ASSOG = #{impNonAssog}, IMP_FINALE_OPZIONI = #{impFinaleOpzioni}, IMP_FINALE_RIPETIZIONI = #{impFinaleRipetizioni},  NUM_RISERVE = #{numRiserve}, ONERI_DERIVANTI = #{oneriDerivanti} where codgara = #{codGara} and codlott = #{codLotto} and num_coll = #{num}")
	public void updateFaseCollaudo(FaseCollaudoInsertForm form);

	@Delete("delete from w9coll where  codgara = #{codGara} and codlott = #{codLotto} and num_coll = #{num}")
	public void deleteFaseCollaudo(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
								   @Param("num") Long num);

	@Delete("delete from w9coll where  codgara = #{codGara}")
	public void deleteFaseCollaudoAll(@Param("codGara") Long codGara);

	//FASE VARIAGGI

	@Select("select codgara as codGara, codlott as codLotto, num_varaggi as num, IDPARTECIPANTE as idPartecipante, CODIMP as codImpresa, RUOLO as idRuolo, TIPOOE as tipoOe, FLAG_AVVALIMENTO as flagAvvalimento, MOTIVO_VARI as motivoVariazione from W9VARAGGI  where codgara= #{codGara} and codlott = #{codLotto} and num_varaggi = #{num}")
	public FaseVariazioneAggiudicatariEntry getFaseVariazioneAggiudicatari(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
															@Param("num") Long num);

	@Insert("insert into W9VARAGGI (codgara, codlott, num_varaggi,IDPARTECIPANTE, CODIMP, RUOLO, TIPOOE, FLAG_AVVALIMENTO, MOTIVO_VARI) values (#{codGara},#{codLotto},#{num},#{idPartecipante},#{codImpresa},#{idRuolo},#{tipoOe},#{flagAvvalimento},#{motivoVariazione})")
	public void insertFaseVariazioneAggiudicatari(FaseVariazioneAggiudicatariInsertForm form);

	@Update("update W9VARAGGI set IDPARTECIPANTE = #{idPartecipante}, CODIMP = #{codImpresa} , RUOLO = #{idRuolo} , TIPOOE = #{tipoOe} , FLAG_AVVALIMENTO = #{flagAvvalimento}, MOTIVO_VARI = #{motivoVariazione} where codgara = #{codGara} and codlott = #{codLotto} and  num_varaggi = #{num}")
	public void updateFaseVariazioneAggiudicatari(FaseVariazioneAggiudicatariInsertForm form);

	@Delete("delete from W9VARAGGI where  codgara = #{codGara} and codlott = #{codLotto} and num_varaggi = #{num}")
	public void deleteFaseVariazioneAggiudicatari(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
								   @Param("num") Long num);

	@Delete("delete from W9VARAGGI where  codgara = #{codGara}")
	public void deleteFaseVariazioneAggiudicatariAll(@Param("codGara") Long codGara);

	// FASE SOSPENSIONE

	@Select("select codgara as codGara, codlott as codLotto, num_sosp as num, data_verb_sosp as dataVerbSosp, data_verb_ripr as dataVerbRipr, id_motivo_sosp as motivoSosp, flag_supero_tempo as flagSuperoTempo, flag_riserve as flagRiserve, flag_verbale as flagVerbale, sosp_parziale as sospParziale, inc_crono as incCrono, data_super_quarto as dataSuperQuarto, num_appa as numAppa from w9sosp where codgara = #{codGara} and codlott = #{codLotto} and num_sosp = #{num} ")
	public FaseSospensioneEntry getFaseSospensione(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												   @Param("num") Long num);

	@Insert("insert into w9sosp (codgara, codlott, num_sosp, data_verb_sosp, data_verb_ripr, id_motivo_sosp, flag_supero_tempo, flag_riserve, flag_verbale, num_appa,sosp_parziale) values (#{codGara},#{codLotto},#{num},#{dataVerbSosp},#{dataVerbRipr},#{motivoSosp},#{flagSuperoTempo},#{flagRiserve},#{flagVerbale}, #{numAppa},#{sospParziale})")
	public void insertFaseSospensione(FaseSospensioneInsertForm form);

	@Insert("insert into w9sosp (codgara, codlott, num_sosp, data_verb_sosp, data_verb_ripr, id_motivo_sosp, flag_supero_tempo, flag_riserve, flag_verbale, num_appa,sosp_parziale) values (#{codGara},#{codLotto},#{num},#{dataVerbSosp},#{dataVerbRipr},#{motivoSosp},'2',#{flagRiserve},#{flagVerbale}, #{numAppa},#{sospParziale})")
	public void insertFaseSospensionePcp(FaseSospensioneInsertForm form);

	@Update("update w9sosp set data_verb_sosp = #{dataVerbSosp}, data_verb_ripr = #{dataVerbRipr}, id_motivo_sosp = #{motivoSosp}, flag_supero_tempo = #{flagSuperoTempo}, flag_riserve = #{flagRiserve}, flag_verbale = #{flagVerbale}, sosp_parziale = #{sospParziale}  where codgara = #{codGara} and codlott = #{codLotto} and num_sosp = #{num} ")
	public void updateFaseSospensione(FaseSospensioneInsertForm form);

	@Delete("delete from w9sosp where codgara = #{codGara} and codlott = #{codLotto} and num_sosp = #{num} ")
	public void deleteFaseSospensione(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
									  @Param("num") Long num);

	@Delete("delete from w9sosp where codgara = #{codGara}")
	public void deleteFaseSospensioneAll(@Param("codGara") Long codGara);


	@Update("update w9sosp set inc_crono = #{incCrono}, data_verb_ripr = #{dataVerbRipr}, flag_riserve = #{flagRiserve}, flag_verbale = #{flagVerbale} where codgara = #{codGara} and codlott = #{codLotto} and num_sosp = #{num} ")
	public void updateFaseRipresaPrestazione(FaseRipresaPrestazioneInsertForm form);

	@Update("update w9sosp set data_super_quarto = #{dataSuperQuarto}, FLAG_SUPERO_TEMPO = '1' where codgara = #{codGara} and codlott = #{codLotto} and num_sosp = #{num} ")
	public void updateFaseSuperamentoQuartoTemporale(FaseSuperamentoQuartoContrattualeInsertForm form);

	@Update("update w9sosp set FLAG_SUPERO_TEMPO = #{flagSuperoTempo} where codgara = #{codGara} and codlott = #{codLotto} and num_sosp = #{num} ")
	public void updateFlagSuperamentoQuartoTemporale(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("num") Long num, @Param("flagSuperoTempo") String flagSuperoTempo);

	@Select("select codgara as codGara, codlott as codLotto, num_sosp as num, data_verb_sosp as dataVerbSosp, data_verb_ripr as dataVerbRipr, id_motivo_sosp as motivoSosp, flag_supero_tempo as flagSuperoTempo, flag_riserve as flagRiserve, flag_verbale as flagVerbale, sosp_parziale as sospParziale, inc_crono as incCrono, data_super_quarto as dataSuperQuarto from w9sosp where codgara = #{codGara} and codlott = #{codLotto} and num_sosp in (select num from w9fasi wf where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = 6 and num not in (select num from w9fasi wf where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = 15 )) ")
	public List<FaseSospensioneEntry> getFasiSospensione(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	// FASE VARIANTE

	@Select("select codgara as codGara, codlott as codLotto, num_vari as num, data_verb_appr as dataVerbaleAppr, altre_motivazioni as altreMotivazioni, data_atto_aggiuntivo as dataAttoAggiuntivo, num_giorni_proroga as numGiorniProroga, imp_ridet_lavori as importoRideterminatoLavori, imp_ridet_servizi as importoRideterminatoServizi, imp_ridet_fornit as importoRideterminatoForniture, imp_subtotale as importoSubtotale, imp_sicurezza as importoSicurezza, imp_progettazione as importoProgettazione, imp_non_assog as importoNonAssog, imp_compl_appalto as importoComplAppalto, imp_disposizione as importoDisposizione, imp_compl_intervento as importoComplIntervento, flag_variante as flagVariante, quinto_obbligo as quintoObbligo, flag_importi as flagImporti, flag_sicurezza as flagSicurezza, cig_nuova_proc as cigNuovaProc, URL_VARIANTI_CO as urlVariantiCo, MOTIVO_REV_PREZZI as motivoRevPrezzi, num_appa as numAppa, imp_finpa as impFinpa, entr_utenza as entrUtenza, intr_attivo as intrAttivo, imp_opzioni as impOpzioni, eform from w9vari where codgara = #{codGara} and codlott = #{codLotto} and num_vari = #{num}")
	public FaseVarianteEntry getFaseVariante(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											 @Param("num") Long num);

	@Insert("insert into w9vari (codgara, codlott, num_vari, data_verb_appr, altre_motivazioni, data_atto_aggiuntivo, num_giorni_proroga, imp_ridet_lavori, imp_ridet_servizi, imp_ridet_fornit, imp_subtotale, imp_sicurezza, imp_progettazione, imp_non_assog, imp_compl_appalto, imp_disposizione, imp_compl_intervento, flag_variante, quinto_obbligo, flag_importi, flag_sicurezza, cig_nuova_proc, num_appa,URL_VARIANTI_CO,MOTIVO_REV_PREZZI,imp_finpa, entr_utenza, intr_attivo, imp_opzioni, eform) values (#{codGara},#{codLotto},#{num},#{dataVerbaleAppr},#{altreMotivazioni},#{dataAttoAggiuntivo},#{numGiorniProroga},#{importoRideterminatoLavori},#{importoRideterminatoServizi},#{importoRideterminatoForniture},#{importoSubtotale},#{importoSicurezza},#{importoProgettazione},#{importoNonAssog},#{importoComplAppalto},#{importoDisposizione},#{importoComplIntervento},#{flagVariante},#{quintoObbligo},#{flagImporti},#{flagSicurezza},#{cigNuovaProc}, #{numAppa},#{urlVariantiCo},#{motivoRevPrezzi},#{impFinpa},#{entrUtenza},#{intrAttivo},#{impOpzioni},#{eform})")
	public void insertFaseVariante(FaseVarianteInsertForm form);

	@Update("update w9vari set data_verb_appr = #{dataVerbaleAppr}, altre_motivazioni = #{altreMotivazioni}, data_atto_aggiuntivo = #{dataAttoAggiuntivo}, num_giorni_proroga = #{numGiorniProroga}, imp_ridet_lavori = #{importoRideterminatoLavori}, imp_ridet_servizi = #{importoRideterminatoServizi}, imp_ridet_fornit = #{importoRideterminatoForniture}, imp_subtotale = #{importoSubtotale}, imp_sicurezza = #{importoSicurezza}, imp_progettazione = #{importoProgettazione}, imp_non_assog = #{importoNonAssog}, imp_compl_appalto = #{importoComplAppalto}, imp_disposizione = #{importoDisposizione}, imp_compl_intervento = #{importoComplIntervento}, flag_variante = #{flagVariante}, quinto_obbligo = #{quintoObbligo}, flag_importi = #{flagImporti}, flag_sicurezza = #{flagSicurezza} ,cig_nuova_proc = #{cigNuovaProc}, URL_VARIANTI_CO = #{urlVariantiCo}, MOTIVO_REV_PREZZI = #{motivoRevPrezzi}, imp_finpa  = #{impFinpa}, entr_utenza  = #{entrUtenza}, intr_attivo  = #{intrAttivo}, imp_opzioni  = #{impOpzioni}, eform = #{eform} where codgara = #{codGara} and codlott = #{codLotto} and num_vari = #{num}")
	public void updateFaseVariante(FaseVarianteInsertForm form);

	@Delete("delete from w9vari where codgara = #{codGara} and codlott = #{codLotto} and num_vari = #{num}")
	public void deleteFaseVariante(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
								   @Param("num") Long num);

	@Delete("delete from w9vari where codgara = #{codGara}")
	public void deleteFaseVariantiAll(@Param("codGara") Long codGara);

	@Delete("delete from w9moti where codgara = #{codGara}")
	public void deleteFaseVariantiMotiAll(@Param("codGara") Long codGara);

	// FASE ACCORDO BONARIO

	@Select("select codgara as codGara, codlott as codLotto, num_acco as num, data_accordo as dataAccordo, oneri_derivanti as oneriDerivanti, num_riserve as numRiserve, data_inizio_acc as dataInizioAcc, data_fine_acc as dataFineAcc, num_appa as numAppa from w9acco where codgara = #{codGara} and codlott = #{codLotto} and num_acco = #{num}")
	public FaseAccordoBonarioEntry getFaseAccordoBonario(@Param("codGara") Long codGara,
														 @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Insert("insert into w9acco (codgara, codlott, num_acco, data_accordo, oneri_derivanti, num_riserve, data_inizio_acc, data_fine_acc, num_appa) values (#{codGara},#{codLotto},#{num},#{dataAccordo},#{oneriDerivanti},#{numRiserve},#{dataInizioAcc},#{dataFineAcc}, #{numAppa})")
	public void insertFaseAccordoBonario(FaseAccordoBonarioInsertForm form);

	@Update("update w9acco set data_accordo = #{dataAccordo}, oneri_derivanti = #{oneriDerivanti}, num_riserve = #{numRiserve}, data_inizio_acc = #{dataInizioAcc}, data_fine_acc = #{dataFineAcc} where codgara = #{codGara} and codlott = #{codLotto} and num_acco = #{num}")
	public void updateFaseAccordoBonario(FaseAccordoBonarioInsertForm form);

	@Delete("delete from w9acco where codgara = #{codGara} and codlott = #{codLotto} and num_acco = #{num}")
	public void deleteFaseAccordoBonario(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
										 @Param("num") Long num);

	@Delete("delete from w9acco where codgara = #{codGara}")
	public void deleteFaseAccordoBonarioAll(@Param("codGara") Long codGara);

	// FASE SUBAPPALTO

	@Select("select codgara as codGara, codlott as codLotto, num_suba as num, data_autorizzazione as dataAuth, oggetto_subappalto as oggetto, importo_presunto as importoPresunto, importo_effettivo as importoEffettivo, id_categoria as idCategoria, id_cpv as idCpv, codimp as codImpresa, codimp_aggiudicatrice as codImpresaAgg, dgue, motivo_mancato_sub as motivoMancatoSub, num_appa as numAppa, data_ultimazione as dataUltimazione, motivo_mancata_esec as motivoMancataEsec   from w9suba where codgara = #{codGara} and codlott = #{codLotto} and num_suba = #{num}")
	public FaseSubappaltoEntry getFaseSubappalto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												 @Param("num") Long num);

	@Select("select codimp from W9SUBAIMPR where codgara = #{codGara} and codlott = #{codLotto} and num_suba = #{num}")
	public List<String> getMandantiFaseSubappalto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												  @Param("num") Long num);

	@Insert("insert into w9suba (codgara, codlott, num_suba, data_autorizzazione, oggetto_subappalto, importo_presunto, importo_effettivo, id_categoria, id_cpv, codimp, codimp_aggiudicatrice, num_appa) values (#{codGara},#{codLotto},#{num},#{dataAuth},#{oggetto},#{importoPresunto},#{importoEffettivo},#{idCategoria},#{idCpv},#{codImpresa},#{codImpresaAgg}, #{numAppa})")
	public void insertFaseSubappalto(FaseSubappaltoInsertForm form);

	@Insert("insert into w9suba (codgara, codlott, num_suba, data_autorizzazione, oggetto_subappalto, importo_presunto, importo_effettivo, id_categoria, id_cpv, codimp, codimp_aggiudicatrice, num_appa, dgue) values (#{codGara},#{codLotto},#{num},#{dataAuth},#{oggetto},#{importoPresunto},#{importoEffettivo},#{idCategoria},#{idCpv},#{codImpresa},#{codImpresaAgg}, #{numAppa}, #{dgue})")
	public void insertFaseRichiestaSubappalto(FaseSubappaltoInsertForm form);

	@Insert("insert into W9SUBAIMPR (codgara, codlott, num_suba, num_impr, codimp) values (#{codGara},#{codLotto},#{num},#{numImpr},#{codimp})")
	public void insertMandantiFaseSubappalto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											 @Param("num") Long num, @Param("numImpr") Long numImpr, @Param("codimp") String codimp);

	@Update("update w9suba set data_autorizzazione = #{dataAuth}, oggetto_subappalto = #{oggetto}, importo_presunto = #{importoPresunto}, importo_effettivo = #{importoEffettivo}, id_categoria = #{idCategoria}, id_cpv = #{idCpv}, codimp = #{codImpresa}, codimp_aggiudicatrice = #{codImpresaAgg} where codgara = #{codGara} and codlott = #{codLotto} and num_suba = #{num}")
	public void updateFaseSubappalto(FaseSubappaltoInsertForm form);

	@Update("update w9suba set data_autorizzazione = #{dataAuth}, oggetto_subappalto = #{oggetto}, importo_presunto = #{importoPresunto}, importo_effettivo = #{importoEffettivo}, id_categoria = #{idCategoria}, id_cpv = #{idCpv}, codimp = #{codImpresa}, codimp_aggiudicatrice = #{codImpresaAgg}, dgue = #{dgue} where codgara = #{codGara} and codlott = #{codLotto} and num_suba = #{num}")
	public void updateFaseRichiestaSubappalto(FaseSubappaltoInsertForm form);

	@Update("update W9SUBAIMPR set num_impr = #{numImpr}, codimp = #{codimp} where codgara = #{codGara} and codlott = #{codLotto} and num_suba = #{num}")
	public void updateMandantiFaseSubappalto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											 @Param("num") Long num, @Param("numImpr") Long numImpr, @Param("codimp") String codimp);

	@Delete("delete from w9suba where codgara = #{codGara} and codlott = #{codLotto} and num_suba = #{num}")
	public void deleteFaseSubappalto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
									 @Param("num") Long num);

	@Delete("delete from w9suba where codgara = #{codGara}")
	public void deleteFaseSubappaltoAll(@Param("codGara") Long codGara);

	@Delete("delete from w9subaimpr where codgara = #{codGara}")
	public void deleteW9FaseSubaImprAll(@Param("codGara") Long codGara);

	@Delete("delete from W9SUBAIMPR where codgara = #{codGara} and codlott = #{codLotto} and num_suba = #{num}")
	public void deleteMandantiFaseSubappalto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											 @Param("num") Long num);

	@Update("update w9suba set motivo_mancato_sub = #{motivoMancatoSub},data_autorizzazione = #{dataAuth} where codgara = #{codGara} and codlott = #{codLotto} and num_suba = #{num}")
	public void updateFaseEsitoSubappalto(FaseEsitoSubappaltoInsertForm form);

	@Update("update w9suba set importo_effettivo = #{importoEffettivo},data_ultimazione = #{dataUltimazione}, motivo_mancata_esec = #{motivoMancataEsec} where codgara = #{codGara} and codlott = #{codLotto} and num_suba = #{num}")
	public void updateFaseConclusioneSubappalto(FaseConclusioneSubappaltoInsertForm form);

	@Select("select codgara as codGara, codlott as codLotto, num_suba as num, data_autorizzazione as dataAuth, oggetto_subappalto as oggetto, importo_presunto as importoPresunto, importo_effettivo as importoEffettivo, id_categoria as idCategoria, id_cpv as idCpv, codimp as codImpresa, codimp_aggiudicatrice as codImpresaAgg, dgue, motivo_mancato_sub as motivoMancatoSub from w9suba where codgara = #{codGara} and codlott = #{codLotto} and num_suba in (select num from w9fasi where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = 16 and num not in (select num from w9fasi wf where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = 17 )) ")
	public List<FaseSubappaltoEntry> getNumSubaEsitoSuba(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select codgara as codGara, codlott as codLotto, num_suba as num, data_autorizzazione as dataAuth, oggetto_subappalto as oggetto, importo_presunto as importoPresunto, importo_effettivo as importoEffettivo, id_categoria as idCategoria, id_cpv as idCpv, codimp as codImpresa, codimp_aggiudicatrice as codImpresaAgg, dgue, motivo_mancato_sub as motivoMancatoSub from w9suba where codgara = #{codGara} and codlott = #{codLotto} and num_suba in ( select num from w9fasi where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = 17 and data_autorizzazione is not null and num not in ( select num from w9fasi wf where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = 18 ))")
	public List<FaseSubappaltoEntry> getNumSubaConclusioneSuba(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	// FASE ISTANZA RECESSO

	@Select("select codgara as codGara,codlott as codLotto,num_rita as num,data_termine as datatermine,data_consegna as dataConsegna,tipo_comun as tipoComunicazione,ritardo,durata_sosp as durataSospensione,motivo_sosp as motivoSospensione,data_ist_recesso as dataIstanzaRecesso,flag_accolta as flagAccolta,flag_tardiva as flagTardiva,flag_ripresa as flagRipresa,flag_riserva as flagRiserva,importo_spese as importoSpese,importo_oneri as importoOneri, num_appa as numAppa from w9rita where codgara=#{codGara} and codlott=#{codLotto} and num_rita=#{num}")
	public FaseIstanzaRecessoEntry getFaseIstanzaRecesso(@Param("codGara") Long codGara,
														 @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Insert("insert into w9rita (codgara, codlott, num_rita, data_termine, data_consegna, tipo_comun, ritardo, durata_sosp, motivo_sosp, data_ist_recesso, flag_accolta, flag_tardiva, flag_ripresa, flag_riserva, importo_spese, importo_oneri, num_appa) values (#{codGara},#{codLotto},#{num},#{dataTermine},#{dataConsegna},#{tipoComunicazione},#{ritardo},#{durataSospensione},#{motivoSospensione},#{dataIstanzaRecesso},#{flagAccolta},#{flagTardiva},#{flagRipresa},#{flagRiserva},#{importoSpese},#{importoOneri},#{numAppa})")
	public void insertFaseIstanzaRecesso(FaseIstanzaRecessoInsertForm form);

	@Update("update w9rita set data_termine=#{dataTermine}, data_consegna=#{dataConsegna}, tipo_comun=#{tipoComunicazione}, ritardo=#{ritardo}, durata_sosp=#{durataSospensione}, motivo_sosp=#{motivoSospensione}, data_ist_recesso=#{dataIstanzaRecesso}, flag_accolta=#{flagAccolta}, flag_tardiva=#{flagTardiva}, flag_ripresa=#{flagRipresa}, flag_riserva=#{flagRiserva}, importo_spese=#{importoSpese}, importo_oneri=#{importoOneri} where codgara = #{codGara} and codlott = #{codLotto} and num_rita = #{num}")
	public void updateFaseIstanzaRecesso(FaseIstanzaRecessoInsertForm form);

	@Delete("delete from w9rita where codgara = #{codGara} and codlott = #{codLotto} and num_rita = #{num}")
	public void deleteFaseIstanzaRecesso(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
										 @Param("num") Long num);

	@Delete("delete from w9rita where codgara = #{codGara}")
	public void deleteFaseIstanzaRecessoAll(@Param("codGara") Long codGara);

	// FASE INIDONEITA' TECNICO PROFESSIONALE

	@Select("select codgara as codGara, codlott as codLotto, num_tpro as num, codimp as codImpresa, descare as descrizione, datauni as dataUni, inido1 as nonIdoneitaVerificaIscrizione, inido2 as nonIndicatiContratti, inido3 as mancataNominaResp, inido4 as mancataNominaMedico, inido5 as mancatoDocValutazioneRischi, inido6 as inadeguataFormazioneSicurezza, inido7 as altreCause from W9TECPRO where codgara = #{codGara} and codlott = #{codLotto} and num_tpro = #{num}")
	public FaseInidoneitaTecnicoProfEntry getFaseInidoneitaTecnicoProf(@Param("codGara") Long codGara,
																	   @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Insert("insert into W9TECPRO (codgara, codlott, num_tpro, codimp, descare, datauni, inido1, inido2, inido3, inido4, inido5, inido6, inido7, num_appa) values (#{codGara},#{codLotto},#{num},#{codImpresa},#{descrizione},#{dataUni},#{nonIdoneitaVerificaIscrizione},#{nonIndicatiContratti},#{mancataNominaResp},#{mancataNominaMedico},#{mancatoDocValutazioneRischi},#{inadeguataFormazioneSicurezza},#{altreCause},#{numAppa}) ")
	public void insertFaseInidoneitaTecnicoProf(FaseInidoneitaTecnicoProfInsertForm form);

	@Update("update W9TECPRO set codimp = #{codImpresa}, descare = #{descrizione}, datauni = #{dataUni}, inido1 = #{nonIdoneitaVerificaIscrizione}, inido2 = #{nonIndicatiContratti}, inido3 = #{mancataNominaResp}, inido4 = #{mancataNominaMedico}, inido5 = #{mancatoDocValutazioneRischi}, inido6 = #{inadeguataFormazioneSicurezza}, inido7 = #{altreCause} where codgara = #{codGara} and codlott = #{codLotto} and num_tpro = #{num}")
	public void updateFaseInidoneitaTecnicoProf(FaseInidoneitaTecnicoProfInsertForm form);

	@Delete("delete from W9TECPRO where codgara = #{codGara} and codlott = #{codLotto} and num_tpro = #{num}")
	public void deleteFaseInidoneitaTecnicoProf(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												@Param("num") Long num);

	// FASE INIDONETA CONTRIBUTIVA

	@Select("select codgara as codGara, codlott as codLotto, num_rego as num, descare as descrizione, codimp as codImpresa, iddurc as estremiDurc, datadurc as dataDurc, cassaedi as cassaEdile, riscontro_irr as riscontroIrregolare, datacomun as dataComunicazione  from W9REGCON where codgara = #{codGara} and codlott = #{codLotto} and num_rego = #{num} ")
	public FaseInidoneitaContributivaEntry getFaseInidoneitaContributiva(@Param("codGara") Long codGara,
																		 @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Insert("insert into W9REGCON (codgara, codlott, num_rego, descare, codimp, iddurc, datadurc, cassaedi, riscontro_irr, datacomun, num_appa) values (#{codGara},#{codLotto},#{num},#{descrizione},#{codImpresa},#{estremiDurc},#{dataDurc},#{cassaEdile},#{riscontroIrregolare},#{dataComunicazione},#{numAppa})")
	public void insertFaseInidoneitaContributiva(FaseInidoneitaContributivaInsertForm form);

	@Update("update W9REGCON set descare = #{descrizione}, codimp = #{codImpresa}, iddurc = #{estremiDurc}, datadurc = #{dataDurc}, cassaedi = #{cassaEdile}, riscontro_irr = #{riscontroIrregolare}, datacomun = #{dataComunicazione} where codgara = #{codGara} and codlott = #{codLotto} and num_rego = #{num}")
	public void updateFaseInidoneitaContributiva(FaseInidoneitaContributivaInsertForm form);

	@Delete("delete from W9REGCON where codgara = #{codGara} and codlott = #{codLotto} and num_rego = #{num}")
	public void deleteFaseInidoneitaContributiva(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												 @Param("num") Long num);

	// FASE INADEMPIENZE SICUREZZA

	@Select("select codgara as codGara, codlott as codLotto, num_inad as num, dainasic as dataInadempienza, comma3a as comma3A, comma3b as comma3B, comma45a as comma45A, comma5 as comma5, comma6 as comma6, commaltro as commaAltro, descvio as descrizione, codimp as codImpresa from W9INASIC where codgara = #{codGara} and codlott = #{codLotto} and num_inad = #{num}")
	public FaseInadempienzeSicurezzaEntry getFaseInadempienzeSicurezza(@Param("codGara") Long codGara,
																	   @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Insert("insert into W9INASIC (codgara, codlott, num_inad, dainasic, comma3a, comma3b, comma45a, comma5, comma6, commaltro, descvio, codimp) values (#{codGara},#{codLotto},#{num},#{dataInadempienza},#{comma3A},#{comma3B},#{comma45A},#{comma5},#{comma6},#{commaAltro},#{descrizione}, #{codImpresa}) ")
	public void insertFaseInadempienzeSicurezza(FaseInadempienzeSicurezzaInsertForm form);

	@Update("update W9INASIC set dainasic = #{dataInadempienza}, comma3a = #{comma3A}, comma3b = #{comma3B}, comma45a = #{comma45A}, comma5 = #{comma5}, comma6 = #{comma6}, commaltro = #{commaAltro}, descvio = #{descrizione}, codimp = #{codImpresa} where codgara = #{codGara} and codlott = #{codLotto} and num_inad = #{num}")
	public void updateFaseInadempienzeSicurezza(FaseInadempienzeSicurezzaInsertForm form);

	@Delete("delete from W9INASIC where codgara = #{codGara} and codlott = #{codLotto} and num_inad = #{num}")
	public void deleteFaseInadempienzeSicurezza(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												@Param("num") Long num);

	// FASE INFORTUNI

	@Select("select codgara as codGara, codlott as codLotto, num_infor as num, ninfort as totaleInfortuni, ngiornate as giornateRiconosciute, imorte as infortuniMortali, iperma as infortuniPermanenti, codimp as codImpresa from w9infor where codgara = #{codGara} and codlott = #{codLotto} and num_infor = #{num}")
	public FaseInfortuniEntry getFaseInfortuni(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											   @Param("num") Long num);

	@Select("insert into w9infor (codgara, codlott, num_infor, ninfort, ngiornate, imorte, iperma, codimp, num_appa) values (#{codGara},#{codLotto},#{num},#{totaleInfortuni},#{giornateRiconosciute},#{infortuniMortali},#{infortuniPermanenti},#{codImpresa}, #{numAppa})")
	public void insertFaseInfortuni(FaseInfortuniInsertForm form);

	@Update("update w9infor set codgara = #{codGara}, codlott = #{codLotto}, num_infor = #{num}, ninfort = #{totaleInfortuni}, ngiornate = #{giornateRiconosciute}, imorte = #{infortuniMortali}, iperma = #{infortuniPermanenti}, codimp = #{codImpresa} where codgara = #{codGara} and codlott = #{codLotto} and num_infor = #{num}")
	public void updateFaseInfortuni(FaseInfortuniInsertForm form);

	@Delete("delete from w9infor where codgara = #{codGara} and codlott = #{codLotto} and num_infor = #{num}")
	public void deleteFaseInfortuni(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
									@Param("num") Long num);

	// FASE CANTIERI

	@Select("select codgara as codGara, codlott as codLotto,  num_cant as num, tipopera as tipoOpera, tipinterv as tipoIntervento, tipcostr as tipologiaCostruttiva, diniz as dataInizio, dlav as durataPresunta, indcan as indirizzoCantiere, civico as civico, comune as codIstatComune, prov as provincia, coord_x as coordX, coord_y as coordY, latit as latitudine, longit as longitudine, numlav as numMaxLavoratori, numimp as numeroImprese, lavaut as numLavoratoriAutonomi, infrda as infrastrutturaReteDa, infra  as infrastrutturaReteA, infrde as descInfrsatrutturaRete, mailric as ulterioreMail from w9cant where codgara = #{codGara} and codlott = #{codLotto} and num_cant = #{num}")
	public FaseCantieriEntry getFaseCantieri(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											 @Param("num") Long num);

	@Select("select codgara as codGara, codlott as codLotto, num_cant as numCant, num_imp as num, codimp as codiceImpresa, cipdurc as codiceDurc, protdurc as protocolloDurc, datdurc as dataDurc from W9CANTIMP where codgara = #{codGara} and codlott = #{codLotto} and num_cant = #{num}")
	public List<FaseCantieriImpEntry> getImpreseFaseCantieri(@Param("codGara") Long codGara,
															 @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("SELECT tab1tip codice, tab1desc descrizione, tab1arc archiviato from tab1 where tab1cod= 'W9008' order by tab1nord")
	public List<TabellatoEntry> getDestinatariNotificaTab();

	@Select("select dest from W9CANTDEST where codgara = #{codiceGara} and codlott = #{codiceLotto} and num_cant = #{num}")
	public List<Long> getDestinatariNotificaCantiere(@Param("codiceGara") Long codiceGara,
													 @Param("codiceLotto") Long codiceLotto, @Param("num") Long num);

	@Insert("insert into w9cant (codgara, codlott, num_cant, tipopera, tipinterv, tipcostr, diniz, dlav, indcan, civico, comune, prov, coord_x, coord_y, latit, longit, numlav, numimp, lavaut, infrda, infra, infrde, mailric, num_appa) values (#{codGara},#{codLotto},#{num},#{tipoOpera},#{tipoIntervento},#{tipologiaCostruttiva},#{dataInizio},#{durataPresunta},#{indirizzoCantiere},#{civico},#{codIstatComune},#{provincia},#{coordX},#{coordY},#{latitudine},#{longitudine},#{numMaxLavoratori},#{numeroImprese},#{numLavoratoriAutonomi},#{infrastrutturaReteDa},#{infrastrutturaReteA},#{descInfrsatrutturaRete},#{ulterioreMail},#{numAppa})")
	public void insertFaseCantieri(FaseCantieriInsertForm form);

	@Insert("insert into W9CANTIMP (codgara, codlott, num_cant, num_imp, codimp, cipdurc, protdurc, datdurc) values (#{codGara},#{codLotto},#{numCant},#{num},#{codiceImpresa},#{codiceDurc},#{protocolloDurc},#{dataDurc})")
	public void insertImpreseFaseCantieri(FaseCantieriImpEntry form);

	@Insert("insert into W9CANTDEST (codgara,codlott,num_cant,dest) values (#{codGara},#{codLotto},#{num},#{dest})")
	public void insertDestinatarioFaseCantieri(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											   @Param("num") Long num, @Param("dest") Long dest);

	@Delete("delete from w9cant where codgara = #{codGara} and codlott = #{codLotto} and num_cant=#{num}")
	public void deleteFaseCantieri(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
								   @Param("num") Long num);

	@Delete("delete from W9CANTIMP where codgara = #{codGara} and codlott = #{codLotto} and num_cant=#{num}")
	public void deleteImpreseFaseCantieri(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
										  @Param("num") Long num);

	@Delete("delete from W9CANTDEST where codgara = #{codGara} and codlott = #{codLotto} and num_cant = #{num}")
	public void deleteDestinatarioFaseCantieri(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											   @Param("num") Long num);

	@Update("update w9cant set tipopera = #{tipoOpera}, tipinterv = #{tipoIntervento}, tipcostr = #{tipologiaCostruttiva}, diniz = #{dataInizio}, dlav = #{durataPresunta}, indcan = #{indirizzoCantiere}, civico = #{civico}, comune = #{codIstatComune}, prov = #{provincia}, coord_x = #{coordX}, coord_y = #{coordY}, latit = #{latitudine}, longit = #{longitudine}, numlav = #{numMaxLavoratori}, numimp = #{numeroImprese}, lavaut = #{numLavoratoriAutonomi}, infrda = #{infrastrutturaReteDa}, infra = #{infrastrutturaReteA}, infrde = #{descInfrsatrutturaRete}, mailric = #{ulterioreMail} where codgara = #{codGara} and codlott = #{codLotto} and num_cant = #{num} ")
	public void updateFaseCantieri(FaseCantieriInsertForm form);

	// SCHEDE FASI

	@Select("select codgara as codGara, codlott as codLotto, num_appa as numAppa, num_aggi as numAggi, id_tipoagg as idTipoAgg, ruolo as ruolo, flag_avvalimento as flagAvvallamento, codimp as codImpresa, codimp_ausiliaria as codImpAus, id_gruppo as idGruppo,importo_aggiudicazione as importoAggiudicazione, perc_ribasso_agg as ribassoAggiudicazione ,perc_off_aumento as offertaAumento, nome_legrap as nomeLegRap, cognome_legrap as cognomeLegRap, cf_legrap as cfLegRap, idpartecipante as idPartecipante from w9aggi where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa}")
	public List<ImpresaAggiudicatariaEntry> getImpreseAggiudicatarie(@Param("codGara") Long codGara,
																	 @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa);

	@Select("select codimp from w9aggi where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa}")
	public List<String> getCodiciImpreseAggiudicatarie(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
													   @Param("numAppa") Long numAppa);

	@Select("select codgara as codGara, codlott as codLotto, num_appa as numAppa, num_aggi as numAggi, id_tipoagg as idTipoAgg, ruolo as ruolo, flag_avvalimento as flagAvvallamento, codimp as codImpresa, codimp_ausiliaria as codImpAus, id_gruppo as idGruppo, nome_legrap as nomeLegRap, cognome_legrap as cognomeLegRap, cf_legrap as cfLegRap from w9aggi where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa} and num_aggi = #{numAggi}")
	public ImpresaAggiudicatariaEntry getImpresaAggiudicataria(@Param("codGara") Long codGara,
															   @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa, @Param("numAggi") Long numAggi);

	@Select("select codgara as codGara, codlott as codLotto, num_appa as numAppa, num_aggi as numAggi, id_tipoagg as idTipoAgg, ruolo as ruolo, flag_avvalimento as flagAvvallamento, codimp as codImpresa, codimp_ausiliaria as codImpAus, id_gruppo as idGruppo, nome_legrap as nomeLegRap, cognome_legrap as cognomeLegRap, cf_legrap as cfLegRap from w9aggi where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa} and id_gruppo = #{idGruppo}")
	public List<ImpresaAggiudicatariaEntry> getImpreseAggiudicatarieGroup(@Param("codGara") Long codGara,
																		  @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa, @Param("idGruppo") Long idGruppo);

	@Insert("insert into w9aggi (codgara, codlott,num_appa, num_aggi, id_tipoagg, ruolo, flag_avvalimento, codimp, codimp_ausiliaria, id_gruppo,importo_aggiudicazione,perc_ribasso_agg,perc_off_aumento,NOME_LEGRAP, COGNOME_LEGRAP, CF_LEGRAP, IDPARTECIPANTE) values (#{codGara},#{codLotto},#{numAppa},#{numAggi},#{idTipoAgg},#{ruolo},#{flagAvvallamento},#{codImpresa},#{codImpAus},#{idGruppo},#{importoAggiudicazione},#{ribassoAggiudicazione},#{offertaAumento},#{nomeLegRap},#{cognomeLegRap},#{cfLegRap}, #{idPartecipante})")
	public void insertImpresaAggiudicataria(ImpresaAggiudicatariaInsertForm form);

	@Update("update w9aggi set id_tipoagg = #{idTipoAgg}, ruolo = #{ruolo}, flag_avvalimento = #{flagAvvallamento}, codimp = #{codImpresa}, codimp_ausiliaria = #{codImpAus}, id_gruppo = #{idGruppo}, importo_aggiudicazione = #{importoAggiudicazione}, perc_ribasso_agg = #{ribassoAggiudicazione} ,perc_off_aumento = #{offertaAumento} where codgara = #{codGara} and codlott = #{codLotto}  and  num_appa = #{numAppa} and  num_aggi = #{numAggi}")
	public void updateImpresaAggiudicataria(ImpresaAggiudicatariaInsertForm form);

	@Delete("delete from w9aggi where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa}")
	public void deleteImpreseAggiudicatarie(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											@Param("numAppa") Long numAppa);

	@Delete("delete from w9aggi where codgara = #{codGara} and num_appa = #{numAppa}")
	public void deleteImpreseAggiudicatarieAll(@Param("codGara") Long codGara, @Param("numAppa") Long numAppa);

	@Select("select max (num_aggi) from w9aggi where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{num}")
	public Long getMaxNumAggi(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Insert("insert into W9INCA (codgara, codlott, num, num_inca, id_ruolo, sezione, codtec) values (#{codGara},#{codLotto},#{num},#{numInca},#{idRuolo},#{sezione},#{codtec})")
	public void insertIncaricoProfessionale(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											@Param("num") Long num, @Param("numInca") Long numInca, @Param("idRuolo") Long idRuolo,
											@Param("sezione") String sezione, @Param("codtec") String codtec);

	@Insert("insert into W9INCA (codgara, codlott, num, num_inca, sezione, id_ruolo, cig_prog_esterna, data_aff_prog_esterna, data_cons_prog_esterna, codtec, tipo_prog) values (#{codGara},#{codLotto},#{num},#{numInca},#{form.sezione},#{form.idRuolo},#{form.cigProgEsterna},#{form.dataAffProgEsterna},#{form.dataConsProgEsterna},#{form.codiceTecnico}, #{form.tipoProgettazione})")
	public void insertIncaricoProfessionaleForm(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												@Param("num") Long num, @Param("numInca") Long numInca,
												@Param("form") IncaricoProfessionaleInsertForm form);

	@Select("select max (num_inca) from w9inca where codgara = #{codGara} and codlott = #{codLotto} and num = #{num}")
	public Long getMaxIdIncarico(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
								 @Param("num") Long num);

	@Select("select codgara as codGara, codlott as codLotto, num as num, num_inca as numInca, sezione as sezione, id_ruolo as idRuolo, cig_prog_esterna as cigProgEsterna, data_aff_prog_esterna as dataAffProgEsterna, data_cons_prog_esterna as dataConsProgEsterna, codtec as codiceTecnico, tipo_prog as tipoProgettazione from W9INCA where codgara = #{codGara} and codLott = #{codLotto} and num = #{num}")
	public List<IncarichiProfEntry> getIncarichiProfessionali(@Param("codGara") Long codGara,
															  @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("select codgara as codGara, codlott as codLotto, num as num, num_inca as numInca, sezione as sezione, id_ruolo as idRuolo, cig_prog_esterna as cigProgEsterna, data_aff_prog_esterna as dataAffProgEsterna, data_cons_prog_esterna as dataConsProgEsterna, codtec as codiceTecnico, tipo_prog as tipoProgettazione from W9INCA where codgara = #{codGara} and codLott = #{codLotto} and num = #{num} and id_ruolo in(1,2,3,4,19)")
	public List<IncarichiProfEntry> getIncarichiProfessionaliFilerRuoloLt4(@Param("codGara") Long codGara,
																		   @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("select codgara as codGara, codlott as codLotto, num as num, num_inca as numInca, sezione as sezione, id_ruolo as idRuolo, cig_prog_esterna as cigProgEsterna, data_aff_prog_esterna as dataAffProgEsterna, data_cons_prog_esterna as dataConsProgEsterna, codtec as codiceTecnico, tipo_prog as tipoProgettazione from W9INCA where codgara = #{codGara} and codLott = #{codLotto} and num = #{num} and id_ruolo in(5,6,7,8,10,13,18)")
	public List<IncarichiProfEntry> getIncarichiProfessionaliFilerRuoloGt4(@Param("codGara") Long codGara,
																		   @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("select codgara as codGara, codlott as codLotto, num as num, num_inca as numInca, sezione as sezione, id_ruolo as idRuolo, cig_prog_esterna as cigProgEsterna, data_aff_prog_esterna as dataAffProgEsterna, data_cons_prog_esterna as dataConsProgEsterna, codtec as codiceTecnico from W9INCA where codgara = #{codGara} and codLott = #{codLotto} and num = #{num} and sezione =#{sezione}")
	public List<IncarichiProfEntry> getIncarichiProfessionaliBySezione(@Param("codGara") Long codGara,
																	   @Param("codLotto") Long codLotto, @Param("num") Long num, @Param("sezione") String sezione);

	@Update("update W9INCA set sezione =#{sezione}, id_ruolo=#{idRuolo}, cig_prog_esterna=#{cigProgEsterna}, data_aff_prog_esterna=#{dataAffProgEsterna}, data_cons_prog_esterna=#{dataConsProgEsterna}, codtec=#{codiceTecnico} where codgara = #{codGara} and codLott = #{codLotto} and num = #{num} and num_inca = #{numInca}")
	public void updateIncaricoProf(IncaricoProfessionaleInsertForm form);

	@Delete("delete from W9INCA where codgara = #{codGara} and codLott = #{codLotto} and num = #{num} and num_inca = #{numInca}")
	public void deleteIncaricoProf(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
								   @Param("num") Long num, @Param("numInca") Long numInca);

	@Delete("delete from W9INCA where codgara = #{codGara} and codLott = #{codLotto} and num = #{num}")
	public void deleteIncaricoProfWithoutNumInca(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												 @Param("num") Long num);

	@Delete("delete from W9INCA where codgara = #{codGara} and codLott = #{codLotto} and sezione = #{sezione} and num = #{num}")
	public void deleteIncarichiProf(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
									@Param("sezione") String sezione, @Param("num") Long num);

	@Delete("delete from W9INCA where codgara = #{codGara}")
	public void deleteIncarichiProfAll(@Param("codGara") Long codGara);

	@Delete("delete from W9INCA where codgara = #{codGara} and codLott = #{codLotto} and num = #{num}")
	public void deleteIncarichiProfWithoutSezione(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("select codgara as codGara, codlott as codLotto, num_appa as numAppa, num_fina as numFina, id_finanziamento as idFinanziamento, importo_finanziamento as importoFinanziamento from W9FINA where codgara = #{codGara} and codLott = #{codLotto} and num_appa = #{numAppa}")
	public List<FinanziamentiEntry> getFinanziamenti(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
													 @Param("numAppa") Long numAppa);

	@Insert("insert into W9FINA (codgara, codlott, num_appa, num_fina, id_finanziamento, importo_finanziamento) values (#{codGara},#{codLotto},#{numAppa},#{numFina},#{idFinanziamento},#{importoFinanziamento})")
	public void insertFinanziamento(FinanziamentiInsertForm form);

	@Insert("insert into W9FINA (codgara, codlott, num_appa, num_fina, id_finanziamento, importo_finanziamento) values (#{codGara},#{codLotto},#{numAppa},#{numFina},#{form.idFinanziamento},#{form.importoFinanziamento})")
	public void insertFinanziamentoForm(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
										@Param("numAppa") Long numAppa, @Param("numFina") Long numFina,
										@Param("form") FinanziamentoInsertForm form);

	@Insert("select max(num_fina) from W9FINA where codgara = #{codGara} and codLott = #{codLotto} and num_appa = #{num}")
	public Long getMaxIdFinanziamenti(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
									  @Param("num") Long num);

	@Delete("delete from W9FINA where codgara = #{codGara} and codLott = #{codLotto} and num_appa = #{numAppa} and num_fina = #{numFina}")
	public void deleteFinanziamento(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
									@Param("numAppa") Long numAppa, @Param("numFina") Long numFina);

	@Delete("delete from W9FINA where codgara = #{codGara} and codLott = #{codLotto} and num_appa = #{numAppa}")
	public void deleteFinanziamenti(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
									@Param("numAppa") Long numAppa);

	@Delete("delete from W9FINA where codgara = #{codGara} and num_appa = #{numAppa}")
	public void deleteFinanziamentiAllPcp(@Param("codGara") Long codGara, @Param("numAppa") Long numAppa);

	@Select("select codimp as codiceImpresa, nomest as ragioneSociale, nomimp as nomImp, cfimp as codiceFiscale, pivimp as partitaIva, locimp as comune, natgiui as formaGiuridica, ncciaa as numeroIscrizione,\r\n"
			+ "	ninail as codiceInail, albtec as numeroIscrizioneAlbo, indimp as indirizzo, nciimp as numeroCivico, proimp as provincia, capimp as cap, nazimp as nazione, \r\n"
			+ "	telcel as telefono, emai2ip as email, emailpec as pec, faximp as fax  from IMPR where codimp = #{codiceImpresa}")
	public ImpresaEntry getImpresa(@Param("codiceImpresa") String codiceImpresa);

	@Select("select codimp as codiceImpresa, nomest as ragioneSociale, nomimp as nomImp, cfimp as codiceFiscale, pivimp as partitaIva, locimp as comune, natgiui as formaGiuridica, ncciaa as numeroIscrizione,\r\n"
			+ "	ninail as codiceInail, albtec as numeroIscrizioneAlbo, indimp as indirizzo, nciimp as numeroCivico, proimp as provincia, capimp as cap, nazimp as nazione, \r\n"
			+ "	telcel as telefono, emai2ip as email, emailpec as pec, faximp as fax  from IMPR where codimp = #{codiceImpresa}")
	public ImpresaFasePubbEntry getImpresaForRequestFase(@Param("codiceImpresa") String codiceImpresa);

	@Select("select codgara as codGara, codlott as codLotto, num_sic as num, pianscic as pianoSicurezza, dirope as direttoreOperativo, tutor as tutor  from w9sic where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public SchedaSicurezzaEntry getschedaSicurezza(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												   @Param("num") Long num);

	@Insert("insert into w9sic (codgara, codlott, num_sic, pianscic, dirope, tutor, num_iniz) values (#{codGara},#{codLotto},#{num},#{pianoSicurezza},#{direttoreOperativo},#{tutor},#{numIniz})")
	public void insertSchedaSicurezza(SchedaSicurezzaInsertForm form);

	@Update("update w9sic set pianscic = #{pianoSicurezza}, dirope = #{direttoreOperativo}, tutor = #{tutor} where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{numIniz}")
	public void updateSchedaSicurezza(SchedaSicurezzaInsertForm form);

	@Delete("delete from w9sic where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public void deleteSchedaSicurezza(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
									  @Param("num") Long num);

	@Select("select codgara as codGara, codlott as codLotto, num_miss as num, descmis as descrizione, codimp as codiceImpresa from W9MISSIC where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public MisureAggiuntiveSicurezzaEntry getMisureAggiuntiveSicurezza(@Param("codGara") Long codGara,
																	   @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("insert into W9MISSIC (codgara, codlott, num_miss, descmis, codimp, num_iniz) values (#{codGara},#{codLotto},#{num},#{descrizione},#{codiceImpresa}, #{numIniz})")
	public void insertMisureAggiuntiveSicurezza(MisureAggiuntivesicurezzaInsertForm form);

	@Update("update W9MISSIC set descmis = #{descrizione}, codimp = #{codiceImpresa} where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{numIniz}")
	public void updateMisureAggiuntiveSicurezza(MisureAggiuntivesicurezzaUpdateForm form);

	@Delete("delete from W9MISSIC where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{numIniz}")
	public void deleteMisureAggiuntiveSicurezza(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												@Param("numIniz") Long numIniz);

	@Select("select codgara as codGara, codlott as codLotto, numdoc as progressivoDocumento, titolo as titolo, file_allegato as fileAllegato from w9docfase where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{codFase} and num_fase = #{numeroProgressivoFase}")
	public List<DocumentoFaseEntry> getDocumentoFase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
													 @Param("codFase") Long codFase, @Param("numeroProgressivoFase") Long numeroProgressivoFase);

	@Insert("insert into w9docfase (codgara, codlott, fase_esecuzione, num_fase, numdoc, titolo, file_allegato) values (#{codGara},#{codLotto}, #{codFase}, #{numeroProgressivoFase}, #{progressivoDocumento},#{titolo},#{fileAllegato})")
	public void insertDocumentoFase(DocumentoFaseInsertForm form);

	@Insert("delete from w9docfase where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{codFase} and num_fase = #{numeroProgressivoFase}")
	public void deleteDocumentoFase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
									@Param("codFase") Long codFase, @Param("numeroProgressivoFase") Long numeroProgressivoFase);

	@Select("select count(*) from W9FLUSSI where AREA=1 and KEY01=#{codGara} and KEY02=#{codLotto} and KEY03 = #{fase} and KEY04 = #{numFase} and TINVIO2 > 0")
	public Long getFlussiFase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
							  @Param("fase") Long fase, @Param("numFase") Long numFase);

	@Select("select count(*) from W9FLUSSI where AREA=1 and KEY01=#{codGara} and KEY02=#{codLotto} and KEY03 = #{fase} and KEY04 = #{numFase} and TINVIO2 = 3")
	public Long getFlussiTinvio2equals3(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
										@Param("fase") Long fase, @Param("numFase") Long numFase);

	@Select("select count(*) from W9FLUSSI where AREA=1 and KEY01=#{codGara} and KEY02=#{codLotto} and KEY03 = #{fase} and KEY04 = #{numFase} and TINVIO2 = 1")
	public Long getFlussiTinvio2equals1(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
										@Param("fase") Long fase, @Param("numFase") Long numFase);

	@Select("select fl.KEY03 from W9FLUSSI fl inner join w9fasi f "
			+ " on fl.key01 = f.codgara and fl.key02 = f.codlott and fl.key03 = f.fase_esecuzione and fl.key04 = f.num "
			+ " where fl.AREA = 1 and fl.KEY01 = #{codGara} and fl.KEY02 = #{codLotto} and fl.tinvio2 > 0 and f.num_appa = #{numAppa}")
	public List<Long> getFasiInviate(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
									 @Param("numAppa") Long numAppa);

	@Select("select tab2tip codice, tab2d2 descrizione, tab2arc archiviato from tab2 where tab2cod = 'W9z05' and tab2tip in ('7','8','9','10')")
	public List<TabellatoEntry> getMotivazioniFaseVar1();

	@Select("select tab2tip codice, tab2d2 descrizione, tab2arc archiviato from tab2 where tab2cod = 'W9z05' and tab2tip <= '13'")
	public List<TabellatoEntry> getMotivazioniFaseVar2();

	@Select("select tab2tip codice, tab2d2 descrizione, tab2arc archiviato from tab2 where tab2cod = 'W9z05' and tab2tip in ('7','8','14','15','16','17','19','20','21','22')")
	public List<TabellatoEntry> getMotivazioniFaseVar3();

	@Select("select tab2tip codice, tab2d2 descrizione, tab2arc archiviato from tab2 where tab2cod = 'W9z05' and tab2tip in ('7','8','14','15','16','17','18','19','20','21','22')")
	public List<TabellatoEntry> getMotivazioniFaseVar4();

	@Select("select tab2tip codice, tab2d2 descrizione, tab2arc archiviato from tab2 where tab2cod = 'W9z05' and tab2arc is null")
	public List<TabellatoEntry> getMotivazioniFaseVar5();

	@Select("select count(*) from w9moti where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getCountW9moti(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select id_motivo_var from w9moti  where codgara = #{codGara} and codlott = #{codLotto} and num_vari = #{num}")
	public List<Long> getMotiviVariazioneByFase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												@Param("num") Long num);

	@Insert("insert into w9moti (codgara, codlott, num_vari, num_moti, id_motivo_var) values (#{codGara},#{codLotto},#{numFase},#{num},#{codice})")
	public void insertMotivazioneFaseVariante(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											  @Param("numFase") Long numFase, @Param("num") Long num, @Param("codice") Long codice);

	@Insert("delete from w9moti where codgara = #{codGara} and codlott = #{codLotto} and num_vari = #{numFase}")
	public void deleteMotivazioniFaseVariante(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											  @Param("numFase") Long numFase);

	@Select("SELECT MAX(IDFLUSSO) FROM W9FLUSSI")
	public Long getMaxIdFlussi();

	@Delete("delete from W9FLUSSI where key01 = #{codGara} and area = 2 and key03 = 988")
	public void deleteFlussoGara(@Param("codGara") Long codGara);

	@Delete("delete from W9FLUSSI where key01 = #{codGara} and area = 2 and key03 = 901 and key04 = #{numPubb}")
	public void deleteFlussoAtto(@Param("codGara") Long codGara, @Param("numPubb") Long numPubb);

	@Delete("delete from W9FLUSSI where key01 = #{codGara} and area = 1 and key02 = #{codLotto}")
	public void deleteFlussoLotto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Delete("delete from W9FLUSSI_ELIMINATI where key01 = #{codGara} and area = 1 and key02 = #{codLotto}")
	public void deleteFlussoEliminatiLotto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Update("update w9gara set id_ricevuto = #{idRicevuto} where codgara = #{codGara}")
	public void aggiornaIdRicevuto(@Param("codGara") Long codGara, @Param("idRicevuto") Long idRicevuto);

	@Update("update W9PUBBLICAZIONI set id_ricevuto = #{idRicevuto} where codgara = #{codGara} and tipdoc = #{tipoDocumento} and num_pubb = #{numPubb}")
	public void aggiornaIdRicevutoAtto(@Param("codGara") Long codGara, @Param("tipoDocumento") Long tipoDocumento,
									   @Param("numPubb") Long numPubb, @Param("idRicevuto") Long idRicevuto);

	@Insert("INSERT into W9FLUSSI(IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, CODCOMP, CFSA, AUTORE, XML, CODOGG, DATIMP, IDCOMUN, govway_message_id, govway_transaction_id)"
			+ " VALUES(#{id}, #{area}, #{key01}, #{key02}, #{key03}, #{key04}, #{tipoInvio}, #{dataInvio}, #{note}, #{idAutore}, #{codiceFiscaleSA}, #{autore}, #{json}, #{oggetto}, #{dataInvio}, #{idComunicazione},#{govwayMessageId},#{govwayTransactionId})")
	public void insertFlusso(FlussoEntry flusso);

	@Select("select idflusso as idFlusso, key01 as codGara, autore as autore, noteinvio as noteInvio, DATINV as dataInvio, xml from W9FLUSSI where area = 2 and key01 = #{codGara} and key03 = 988 and tinvio2 > 0 order by idflusso asc")
	public List<FlussiGara> getListaPubblicazioniGara(@Param("codGara") Long codGara);

	@Select("select count(idFlusso) from W9FLUSSI where key01 = #{codGara}")
	public Long esistonoPubblicazioniGara(@Param("codGara") Long codGara);

	@Select("select idflusso as idFlusso, key01 as codGara, key02 as codLotto, key03 as numFase, key04 as num, autore as autore, noteinvio as noteInvio, DATINV as dataInvio, tinvio2 as tipoInvio, xml from W9FLUSSI where area = 1 and key01 = #{codGara} and key02 = #{codLotto} and key03= #{numFase} and key04 = #{num} and tinvio2 > 0 order by idflusso asc")
	public List<FullFlussiFase> getListaPubblicazioniFase(@Param("codGara") Long codGara,
														  @Param("codLotto") Long codLotto, @Param("numFase") Long numFase, @Param("num") Long num);

	@Select("select idflusso as idFlusso from W9FLUSSI where area = 1 and key01 = #{codGara} and key02 = #{codLotto} and key03= #{numFase} and key04 = #{num} and tinvio2 > 0 order by idflusso asc")
	public List<String> listaChiaviPubblicazioniFase(@Param("codGara") Long codGara,
													 @Param("codLotto") Long codLotto, @Param("numFase") Long numFase, @Param("num") Long num);

	int countListaFlussiFase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	List<FullFlussiFase> getListaFlussiFase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											@Param("orderby") String orderby, @Param("direction") String direction, RowBounds rowBounds);

	@Select("select idflusso as idFlusso, key01 as codGara, key04 as numAtto, autore as autore, noteinvio as noteInvio, DATINV as dataInvio,'' as xml from W9FLUSSI where area = 2 and key01 = #{codGara} and key03 = 901 and key04= #{num} and tinvio2 > 0 order by idflusso asc")
	public List<FlussiAtto> getListaPubblicazioniAtto(@Param("codGara") Long codGara, @Param("num") Long num);

	@Select("select xml from W9FLUSSI where idflusso =  #{idFlusso}")
	public String getXmlFlusso(@Param("idFlusso") Long idFlusso);


	@Select("select t.cogtim as cognome, t.nometim as nome, t.cftim as cf from teim t, impleg i where t.codtim = #{codiceImpresa} and t.codtim = i.codimp2")
	public LegaleRappresentanteEntry getLegaleImpresa(@Param("codiceImpresa") String codiceImpresa);

	@Select("select t.cogtim as cognome, t.nometim as nome, t.cftim as cf from teim t, impleg i where t.codtim = #{codiceImpresa} and t.codtim = i.codimp2")
	public LegaleRappresentantePubbEntry getLegaleImpresaPubb(@Param("codiceImpresa") String codiceImpresa);

	@Select("select codgara as codGara, codlott as codLotto, num_appa as numAppa, num_fina as numFina, id_finanziamento as idFinanziamento, importo_finanziamento as importoFinanziamento from W9FINA where codgara = #{codGara} and codLott = #{codLotto} and num_appa = #{numAppa}")
	public List<FinanziamentiPubbEntry> getFinanziamentiFasePubb(@Param("codGara") Long codGara,
																 @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa);

	@Select("select codgara as codGara, codlott as codLotto, num as num, num_inca as numInca, sezione as sezione, id_ruolo as idRuolo, cig_prog_esterna as cigProgEsterna, data_aff_prog_esterna as dataAffProgEsterna, data_cons_prog_esterna as dataConsProgEsterna, codtec as codiceTecnico from W9INCA where codgara = #{codGara} and codLott = #{codLotto} and num = #{num} and sezione = #{sezione}")
	public List<IncarichiProfPubbEntry> getIncarichiFasePubb(@Param("codGara") Long codGara,
															 @Param("codLotto") Long codLotto, @Param("num") Long num, @Param("sezione") String sezione);

	@Select("select codgara as codGara, codlott as codLotto, num_appa as numAppa, num_aggi as numAggi, id_tipoagg as idTipoAgg, ruolo as ruolo, flag_avvalimento as flagAvvallamento, codimp as codImpresa, codimp_ausiliaria as codImpAus, id_gruppo as idGruppo,importo_aggiudicazione as importoAggiudicazione,perc_ribasso_agg as ribassoAggiudicazione,perc_off_aumento as offertaAumento, nome_legrap as nomeLegRap, cognome_legrap as cognomeLegRap, cf_legrap as cfLegRap from w9aggi  where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa}")
	public List<ImpresaAggiudicatariaPubbEntry> getImpAggFasePubb(@Param("codGara") Long codGara,
																  @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa);

	@Select("select max(codlott) from w9lott where codgara = #{codGara}")
	public Long getMaxCodLotto(@Param("codGara") Long codGara);

	@Select("select valore from w_config where UPPER(codapp) = #{codapp} AND chiave = #{chiave}")
	public String getCipherKey(@Param("codapp") String codapp, @Param("chiave") String chiave);

	@Select("select codgara as codGara, codlott as codLotto,  num_cant as num, tipopera as tipoOpera, tipinterv as tipoIntervento, tipcostr as tipologiaCostruttiva, diniz as dataInizio, dlav as durataPresunta, indcan as indirizzoCantiere, civico as civico, comune as codIstatComune, prov as provincia, coord_x as coordX, coord_y as coordY, latit as latitudine, longit as longitudine, numlav as numMaxLavoratori, numimp as numeroImprese, lavaut as numLavoratoriAutonomi, infrda as infrastrutturaReteDa, infra  as infrastrutturaReteA, infrde as descInfrsatrutturaRete, mailric as ulterioreMail from w9cant where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa} order by num_cant asc")
	public List<FaseCantieriEntry> getFasiCantiere(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												   @Param("numAppa") Long numAppa);

	@Update("update w9lott set rup = #{codiceRup} where codgara = #{codGara}")
	public void setRupLotti(@Param("codGara") Long codGara, @Param("codiceRup") String codiceRup);

	// Autocomplete imprese filtrate

	@Select("select codimp as codiceImpresa, nomest as ragioneSociale, nomimp as nomImp, cfimp as codiceFiscale, pivimp as partitaIva, locimp as comune, natgiui as formaGiuridica, ncciaa as numeroIscrizione,\r\n"
			+ "	ninail as codiceInail, albtec as numeroIscrizioneAlbo, indimp as indirizzo, nciimp as numeroCivico, proimp as provincia, capimp as cap, nazimp as nazione, \r\n"
			+ "	telcel as telefono, emai2ip as email, emailpec as pec, faximp as fax  from IMPR where LOWER(codimp) like '%'|| #{desc} ||'%' or LOWER(nomest) like '%'|| #{desc} ||'%' \r\n"
			+ "	and codimp IN (select distinct codimp from w9aggi where codgara = ${codGara} and codlott = ${codLotto}\r\n"
			+ "	union\r\n"
			+ "	select distinct codimp from w9suba where codgara = ${codGara} and codlott = ${codLotto})")
	public List<ImpresaEntry> getImpreseOptionsAggiSubaWithoutSa(SingolaImpresaAggiSubaSearchForm form);

	@Select("select codimp as codiceImpresa, nomest as ragioneSociale, nomimp as nomImp, cfimp as codiceFiscale, pivimp as partitaIva, locimp as comune, natgiui as formaGiuridica, ncciaa as numeroIscrizione,"
			+ "	ninail as codiceInail, albtec as numeroIscrizioneAlbo, indimp as indirizzo, nciimp as numeroCivico, proimp as provincia, capimp as cap, nazimp as nazione, "
			+ "	telcel as telefono, emai2ip as email, emailpec as pec, faximp as fax  from IMPR where LOWER(codimp) like '%'|| #{desc} ||'%' or LOWER(nomest) like '%'|| #{desc} ||'%' ")
			//+ "	and codimp IN (select distinct codimp from w9varaggi where codgara = ${codGara} and codlott = ${codLotto})")
	public List<ImpresaEntry> getImpreseOptionsVaraggiWithoutSa(SingolaImpresaAggiSubaSearchForm form);

	@Select("select codimp as codiceImpresa, nomest as ragioneSociale, nomimp as nomImp, cfimp as codiceFiscale, pivimp as partitaIva, locimp as comune, natgiui as formaGiuridica, ncciaa as numeroIscrizione,\r\n"
			+ "	ninail as codiceInail, albtec as numeroIscrizioneAlbo, indimp as indirizzo, nciimp as numeroCivico, proimp as provincia, capimp as cap, nazimp as nazione, \r\n"
			+ "	telcel as telefono, emai2ip as email, emailpec as pec, faximp as fax  from IMPR where (LOWER(codimp) like '%'|| #{desc} ||'%' or LOWER(nomest) like '%'|| #{desc} ||'%')  and cgenimp = #{stazioneAppaltante} \r\n"
			+ "	and codimp IN (select distinct codimp from w9aggi where codgara = ${codGara} and codlott = ${codLotto}\r\n"
			+ "	union\r\n"
			+ "	select distinct codimp from w9suba where codgara = ${codGara} and codlott = ${codLotto})")
	public List<ImpresaEntry> getImpreseOptionsAggiSubaWithSa(SingolaImpresaAggiSubaSearchForm form);

	@Select("select codimp as codiceImpresa, nomest as ragioneSociale, nomimp as nomImp, cfimp as codiceFiscale, pivimp as partitaIva, locimp as comune, natgiui as formaGiuridica, ncciaa as numeroIscrizione,\r\n"
			+ "	ninail as codiceInail, albtec as numeroIscrizioneAlbo, indimp as indirizzo, nciimp as numeroCivico, proimp as provincia, capimp as cap, nazimp as nazione, \r\n"
			+ "	telcel as telefono, emai2ip as email, emailpec as pec, faximp as fax  from IMPR where LOWER(cfimp) like '%'|| #{desc} ||'%' or LOWER(nomest) like '%'|| #{desc} ||'%' \r\n"
			+ "	and codimp IN (select distinct codimp from w9aggi where codgara = ${codGara} and codlott = ${codLotto})")
	public List<ImpresaEntry> getImpreseOptionsAggiWithoutSa(SingolaImpresaAggiSubaSearchForm form);

	@Select("select codimp as codiceImpresa, nomest as ragioneSociale, nomimp as nomImp, cfimp as codiceFiscale, pivimp as partitaIva, locimp as comune, natgiui as formaGiuridica, ncciaa as numeroIscrizione,\r\n"
			+ "	ninail as codiceInail, albtec as numeroIscrizioneAlbo, indimp as indirizzo, nciimp as numeroCivico, proimp as provincia, capimp as cap, nazimp as nazione, \r\n"
			+ "	telcel as telefono, emai2ip as email, emailpec as pec, faximp as fax  from IMPR where (LOWER(cfimp) like '%'|| #{desc} ||'%' or LOWER(nomest) like '%'|| #{desc} ||'%') and cgenimp = #{stazioneAppaltante} \r\n"
			+ "	and codimp IN (select distinct codimp from w9aggi where codgara = ${codGara} and codlott = ${codLotto})")
	public List<ImpresaEntry> getImpreseOptionsAggiWithSa(SingolaImpresaAggiSubaSearchForm form);

	@Select("select count(distinct codimp) from w9aggi where codgara = #{codGara} and codlott = #{codLotto}")
	public Long countImpreseAggiudicatarie(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select codimp from w9aggi where codgara = #{codGara} and codlott = #{codLotto}")
	public List<String> getCodImpImpreseAggiudicataria(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select cfein from uffint where codein =#{stazioneappaltante}")
	public String getCFSAByCode(@Param("stazioneappaltante") String stazioneappaltante);

	@Insert("insert into tecni (CODTEC, NOMETEI, COGTEI, NOMTEC, CFTEC, cgentei, indtec, ncitec, protec, captec, loctec, cittec, teltec, faxtec, ematec )  VALUES (#{codice},#{nome},#{cognome},#{nominativo},#{cf},#{stazioneAppaltante},#{indirizzo},#{numCivico},#{provincia},#{cap},#{comune},#{codIstat},#{telefono},#{fax},#{email})")
	public void insertRUP(RupEntry form);

	@Insert("insert into CENTRICOSTO (idcentro, codein, codcentro, denomcentro, note, tipologia) VALUES (#{id},#{stazioneAppaltante},#{codiceCentro},#{denominazione},#{note},#{tipologia})")
	public void insertCdc(CentroDiCostoInsertForm form);

	@Select("select CODTEC from W9INCA where CODGARA= #{codGara}")
	public List<String> getIncaricatiGara(@Param("codGara") Long codGara);

	@Select("select max(IDCENTRO) from CENTRICOSTO")
	public Long getMaxIdCentroCosto();

	@Select("select codgara as codGara, codlott as codLotto, num_appa as numAppa, num_aggi as numAggi, id_tipoagg as idTipoAgg, ruolo as ruolo, flag_avvalimento as flagAvvallamento, codimp as codImpresa, codimp_ausiliaria as codImpAus, id_gruppo as idGruppo from w9aggi where codgara = #{codGara}")
	public List<ImpresaAggiudicatariaEntry> getImpreseAggiudicatarieGara(@Param("codGara") Long codGara);

	@Select("select count(*) from IMPR where CGENIMP=#{stazioneAppaltante} and (CFIMP=(select CFIMP from IMPR where CODIMP=#{codImp}) or PIVIMP=(select PIVIMP from IMPR where CODIMP=#{codImp}))")
	public Long esisteImpresa(@Param("codImp") String codImp, @Param("stazioneAppaltante") String stazioneAppaltante);

	@Select("select CODIMP from IMPR where CGENIMP = #{stazioneAppaltante} and upper(CFIMP) = #{cf}")
	public String esisteImpresaCf(@Param("cf") String cf, @Param("stazioneAppaltante") String stazioneAppaltante);

	@Select("select CODIMP from IMPR where CGENIMP = #{stazioneAppaltante} and upper(CFIMP) = #{cf}")
	public List<String> esisteImpresaCfList(@Param("cf") String cf, @Param("stazioneAppaltante") String stazioneAppaltante);

	@Insert("insert into IMPR (codimp, nomest, nomimp, cfimp, pivimp, locimp, natgiui, ncciaa, ninail, albtec, indimp, nciimp, proimp, capimp, nazimp, telcel, emai2ip, emailpec, faximp, cgenimp) values (#{codiceImpresa},#{ragioneSociale}, #{nomImp}, #{codiceFiscale},#{partitaIva},#{comune},#{formaGiuridica},#{numeroIscrizione},#{codiceInail},#{numeroIscrizioneAlbo},#{indirizzo},#{numeroCivico},#{provincia},#{cap},#{nazione},#{telefono},#{email},#{pec},#{fax},#{stazioneAppaltante})")
	public void insertImpresa(ImpresaEntry insertForm);

	@Insert("insert into impleg (id, codimp2, codleg, nomleg) values (#{id}, #{codiceImpresa},#{codiceImpresa},#{nominativo})")
	public void insertImpleg(@Param("id") Long id, @Param("codiceImpresa") String codiceImpresa,
							 @Param("nominativo") String nominativo);

	@Insert("insert into teim (codtim, nometim, cogtim, cftim) values (#{codiceImpresa},#{nome},#{cognome},#{cf})")
	public void insertTeim(@Param("codiceImpresa") String codiceImpresa, @Param("nome") String nome,
						   @Param("cognome") String cognome, @Param("cf") String cf);

	@Select("select codgara as codGara, codlott as codLotto, num_suba as num, data_autorizzazione as dataAuth, oggetto_subappalto as oggetto, importo_presunto as importoPresunto, importo_effettivo as importoEffettivo, id_categoria as idCategoria, id_cpv as idCpv, codimp as codImpresa, codimp_aggiudicatrice as codImpresaAgg from w9suba where codgara = #{codGara}")
	public List<FaseSubappaltoEntry> getFaseSubappaltoGara(@Param("codGara") Long codGara);

	@Select("select codgara as codGara, codlott as codLotto, num_cant as numCant, num_imp as num, codimp as codiceImpresa, cipdurc as codiceDurc, protdurc as protocolloDurc, datdurc as dataDurc from W9CANTIMP where codgara = #{codGara}")
	public List<FaseCantieriImpEntry> getImpreseFaseCantieriGara(@Param("codGara") Long codGara);

	@Select("select codgara as codGara, codlott as codLotto, num_impr as num, codimp as codImpresa, tipoagg as tipologiaSoggetto, ruolo as ruolo, partecip as partecipante, num_ragg as numRaggruppamento from w9imprese where codgara = #{codGara}")
	public List<RecordFaseImpreseEntry> getFaseImpreseGara(@Param("codGara") Long codGara);

	@Select("select cogtim as cognome, nometim as nome, cftim as cf from teim where codtim = #{codiceImpresa}")
	public LegaleRappresentanteEntry getLegalempresa(@Param("codiceImpresa") String codiceImpresa);

	@Select("select codgara as codGara, codlott as codLotto, num_inad as num, dainasic as dataInadempienza, comma3a as comma3A, comma3b as comma3B, comma45a as comma45A, comma5 as comma5, comma6 as comma6, commaltro as commaAltro, descvio as descrizione, codimp as codImpresa from W9INASIC where codgara = #{codGara}")
	public List<FaseInadempienzeSicurezzaEntry> getFasiInadempienzeSicurezzaGara(@Param("codGara") Long codGara);

	@Select("select codgara as codGara, codlott as codLotto, num_sic as num, pianscic as pianoSicurezza, dirope as direttoreOperativo, tutor as tutor, codimp as codImp  from w9sic where codgara = #{codGara}")
	public List<SchedaSicurezzaEntry> getschedeSicurezzaGara(@Param("codGara") Long codGara);

	@Select("select codgara as codGara, codlott as codLotto, num_rego as num, descare as descrizione, codimp as codImpresa, iddurc as estremiDurc, datadurc as dataDurc, cassaedi as cassaEdile, riscontro_irr as riscontroIrregolare, datacomun as dataComunicazione  from W9REGCON where codgara = #{codGara}")
	public List<FaseInidoneitaContributivaEntry> getFasiInidoneitaContributivaGara(@Param("codGara") Long codGara);

	@Update("update w9gara set nlotti = (select count (codlott) from w9lott where codgara = #{codGara}) where codgara = #{codGara}")
	public void updateNlottoGara(@Param("codGara") Long codGara);

	@Select("select max(num_aggi) from w9aggi where codgara=#{codGara} and codlott=#{codLotto}")
	public Long getMaxId(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(id_gruppo) from w9aggi where codgara=#{codGara} and codlott=#{codLotto}")
	public Long getMaxIdGruppo(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select id_gruppo from w9aggi where codgara=#{codGara} and codlott=#{codLotto} and num_appa=#{numAppa} and num_aggi=#{numAggi}")
	public Long getIdGruppo(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
							@Param("numAppa") Long numAppa, @Param("numAggi") Long numAggi);

	@Delete("delete from w9aggi where codgara = #{codGara} and codlott = #{codLotto} and num_appa=#{numAppa} and id_gruppo=#{idGruppo}")
	public void deleteImpreseAggiudicatarieIdGroup(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												   @Param("numAppa") Long numAppa, @Param("idGruppo") Long idGruppo);

	@Delete("delete from w9aggi where codgara = #{codGara} and codlott = #{codLotto} and num_appa=#{numAppa} and num_aggi=#{numAggi}")
	public void deleteImpreseAggiudicatarieNoIdGroup(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
													 @Param("numAppa") Long numAppa, @Param("numAggi") Long numAggi);

	@Select("select valore from w_config where UPPER(codapp) = #{codapp} AND chiave = #{chiave}")
	public String getConfigurazione(@Param("codapp") String codapp, @Param("chiave") String chiave);

	@Select("select g.codein from w9gara g , w9lott  l  where g.codgara = l.codgara and UPPER(l.cig) = UPPER(#{cig})")
	public String countCigBySa(@Param("cig") String cig);

	@Update("update w9lott set daexport = #{daexport} where codgara = #{codGara}")
	public void setLottiDaexport(@Param("daexport") Long daexport, @Param("codGara") Long codGara);

	@Select("select codistat as codice, codistat, descri as descrizione, cap, provincia as codiceProvincia from g_comuni where UPPER(descri) = #{desc}")
	public TabellatoIstatEntry getComuneByDesc(@Param("desc") String desc);

	@Select("select max(num_appa) from w9appa where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumAppa(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select distinct(num_appa) from W9FASI where codgara = #{codGara} and codlott = #{codLotto} and num_appa is not null order by num_appa desc")
	public List<Long> getStoricoNumAppa(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select c.intantic from w9conc c inner join w9fasi f on f.codgara = c.codgara "
			+ " and f.codlott = c.codlott and f.num = c.num_conc where f.codgara = #{codGara} and f.codlott = #{codLotto} and f.fase_esecuzione = 4 and f.num_appa = #{numAppa} "
			+ " and exists (select * from w9fasi where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = 1 and num_appa = #{numAppa})")
	public Long getInterruzioneAnticipata(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
										  @Param("numAppa") Long numAppa);

	@Select("select max(num_conc) from w9conc where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumConc(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_coll) from w9coll where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumColl(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(NUM_VARAGGI) from W9VARAGGI where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumVaraggi(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_iniz) from w9iniz where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumIniz(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_avan) from w9avan where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumAvan(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_inca) from w9inca where codgara = #{codGara} and codlott = #{codLotto} and num = #{num}")
	public Long getMaxNumInca(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("select max(num_sosp) from w9sosp where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumSosp(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_vari) from w9vari where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumVari(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_suba) from w9suba where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumSuba(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_rita) from w9rita where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumRita(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_tpro) from W9TECPRO where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumTpro(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_rego) from W9REGCON where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumRego(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_inad) from W9INASIC where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumInad(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_infor) from W9INASIC where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumInfor(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_cant) from w9cant where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumCant(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_acco) from w9acco where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumAcco(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_miss) from W9MISSIC where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumMiss(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_sic) from w9sic where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumSic(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select importo_compl_intervento from w9appa where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa}")
	public BigDecimal getImportoComplessivoIntervento(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
													  @Param("numAppa") Long numAppa);

	@Select("select chiave, valore from w_config where chiave in ('it.eldasoft.pubblicazioni.ws.clientId','it.eldasoft.pubblicazioni.ws.clientKey','it.eldasoft.pubblicazioni.ws.password','it.eldasoft.pubblicazioni.ws.username' )")
	public List<ChiaveConfigurazione> getChiaviAccessoOrt();

	@Update("update w9lott set cig_master_ml = #{masterCig} where codGara = #{codGara} and codlott = #{codLotto}")
	public void updateMasterCig(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
								@Param("masterCig") String masterCig);

	@Select("select codgara as codGara, codlott as codLotto, importo_tot as importoTotale, cig as cig, oggetto as oggetto, situazione as situazione, tipo_contratto as tipologia, importo_lotto as importoNetto, CIG_MASTER_ML as masterCig from W9LOTT where cig != #{masterCig} and CIG_MASTER_ML = #{masterCig}")
	public List<LottoBaseEntry> getLottiAccorpati(@Param("masterCig") String masterCig);

	@Update("update w9lott set CIG_MASTER_ML = null where codgara = #{codGara}")
	public void cleanMasterCig(@Param("codGara") Long codGara);

	@Select("select codlott from w9lott where codgara = #{codGara} and CIG_MASTER_ML is not null")
	public List<Long> getCodLottoAccorpati(@Param("codGara") Long codGara);

	@Select("select codimp from IMPR where cfimp = #{codiceFiscale} and cgenimp = #{stazioneAppaltante}")
	public List<String> findImpresa(@Param("codiceFiscale") String codiceFiscale,
									@Param("stazioneAppaltante") String stazioneAppaltante);

	@Update("update w9lott set flag_ente_speciale = #{tipoSettore} where codgara = #{codGara}")
	public void updateTipoSettoreLotti(@Param("codGara") Long codGara, @Param("tipoSettore") String tipoSettore);

	@Select("select count(*) from W9FLUSSI where KEY01=#{codGara} and KEY02=#{codLotto} and KEY03=#{fase} and KEY04=#{num} and TINVIO2=-1")
	public Long countRichiesteCancellazioneFase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
												@Param("fase") Long fase, @Param("num") Long num);

	@Select("SELECT tab2tip FROM TAB2 WHERE TAB2COD = 'W3z12' AND TAB2d1 = #{nazioneNumber}")
	public String getCodiceNazioneByNumber(@Param("nazioneNumber") final Long nazioneNumber);

	@Select("select CODSITAT from W9CODICI_AVCP where CODAVCP = #{idCategoria} and TABCOD='W3z03'")
	public String getDatabaseCategoriaByIdCategoria(@Param("idCategoria") String idCategoria);

	@Select("select CODAVCP from W9CODICI_AVCP where CODSITAT = #{idCategoria} and TABCOD='W3z03'")
	public String getIdCategoriaByDatabaseCategoria(@Param("idCategoria") String idCategoria);

	@Select("select codgara as codGara, codlott as codLotto, numdoc as progressivoDocumento, titolo as titolo, file_allegato as fileAllegato from w9docfase where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{codFase} and num_fase = #{numeroProgressivoFase} and numdoc = #{numDoc}")
	public DocumentoFaseEntry getDocumentoFaseByNumDoc(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
													   @Param("codFase") Long codFase, @Param("numeroProgressivoFase") Long numeroProgressivoFase,
													   @Param("numDoc") Long numDoc);

	@Select("select codgara as codGara, codlott as codLotto, numdoc as progressivoDocumento, titolo as titolo from w9docfase where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{codFase} and num_fase = #{numeroProgressivoFase}")
	public List<DocumentoFaseEntry> getDocumentiFaseWithoutAllegato(@Param("codGara") Long codGara,
																	@Param("codLotto") Long codLotto, @Param("codFase") Long codFase,
																	@Param("numeroProgressivoFase") Long numeroProgressivoFase);

	@Delete("delete from w9docfase where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{codFase} and num_fase = #{numeroProgressivoFase} and numdoc = #{numDoc}")
	public void deleteDocumentoFaseByNumDoc(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
											@Param("codFase") Long codFase, @Param("numeroProgressivoFase") Long numeroProgressivoFase,
											@Param("numDoc") Long numDoc);

	@Select("select coalesce(max(numdoc), 0) as numdoc from w9docfase where codgara = #{codGara} and codlott = #{codLotto} and fase_esecuzione = #{codFase} and num_fase = #{numeroProgressivoFase}")
	public Long getMaxNumDocFase(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
								 @Param("codFase") Long codFase, @Param("numeroProgressivoFase") Long numeroProgressivoFase);

	public List<GaraBaseEntry> getListaGareNonPaginata(final GareNonPaginateSearchForm searchForm);

	@Select("select count(p.codgara) from w9gara g join w9pubblicazioni p on g.codgara=p.codgara where g.codgara=#{codGara} and P.TIPDOC=#{tipoDocumento}")
	public int checkIfGaraHAsAtto(@Param("codGara") Long codGara, @Param("tipoDocumento") Long tipo);


	@Select("select " +
			"	wf.key04 " +
			" from " +
			"	W9CF_PUBB p " +
			" join w9pubblicazioni pubb on " +
			"	p.id = pubb.tipdoc " +
			" join w9gara g on " +
			"	pubb.codgara = g.codgara " +
			" join w9flussi wf on " +
			"	wf.key01 = g.codgara " +
			"	and wf.key03 = 901 " +
			" join W9PUBLOTTO pl on pl.codgara = g.codgara and pl.num_pubb = pubb.num_pubb " +
			" where " +
			"	pl.codlott = #{codLott} and " +
			"	g.codgara = #{codGara} " +
			"	and p.tipo = 'E'")
	public List<Long> checkIfGaraHAsAttoPubblicato(@Param("codGara") Long codGara, @Param("codLott") Long codLott);


	@Select("select " +
			"	pubb.num_pubb  " +
			" from " +
			"	W9CF_PUBB p " +
			" join w9pubblicazioni pubb on " +
			"	p.id = pubb.tipdoc " +
			" join w9gara g on " +
			"	pubb.codgara = g.codgara " +
			" join W9PUBLOTTO pl on pl.codgara = g.codgara and pl.num_pubb = pubb.num_pubb " +
			" where " +
			"	pl.codlott = #{codLott} and " +
			"	g.codgara = #{codGara} " +
			"	and p.tipo = 'E'")
	public List<Long> checkIfGaraHAsAttoFaseAggiudicazione(@Param("codGara") Long codGara, @Param("codLott") Long codLott);


	@Select("select count(codlott) from W9PUBLOTTO where codgara = #{codGara} and num_pubb = #{numPubb}")
	public int checkAttoLotto(@Param("codGara") Long codGara, @Param("numPubb") Long numPubb);

	@Select("select codtec from tecni where UPPER(cftec) = #{cfRup} and cgentei = #{codiceStazioneAppaltante}")
	public String getCodtecByCfAndSA(@Param("cfRup") String cfRup, @Param("codiceStazioneAppaltante") String codiceStazioneAppaltante);


	@Select("select simoguser, simogpass as simogPassword from W9LOADER_APPALTO_USR where syscon = #{syscon}")
	public CredenzialiSimog getSimogCredentials(@Param("syscon") Long syscon);

	@Insert("insert into W9LOADER_APPALTO_USR(simoguser, simogpass, syscon)  values (#{simoguser}, #{simogpass}, #{syscon})")
	public void insertCredenzialiSimog(@Param("simoguser") String simoguser, @Param("simogpass") String simogpass, @Param("syscon") Long syscon);

	@Update("update W9LOADER_APPALTO_USR set simoguser = #{simoguser}, simogpass = #{simogpass} where syscon = #{syscon}")
	public void updateCredenzialiSimog(@Param("simoguser") String simoguser, @Param("simogpass") String simogpass, @Param("syscon") Long syscon);

	@Select("select codlott from w9lott where codgara = #{codGara}")
	public Long getCodLott(@Param("codGara") Long codGara);


	@Select("SELECT tab1desc as descrizione from tab1 where tab1cod = #{cod} and tab1tip = #{valore}")
	public String getValoreTabellato(@Param("cod") String cod, @Param("valore") Long valore);

	@Select("Select exsottosoglia from w9lott where codgara = #{codGara} and codlott = #{codLotto}")
	public String getSottosogliaLotto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select count(codgara) from w9appalav where codgara = #{codGara} and codlott = #{codLotto}")
	public Long countAppalav(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Update("update w9gara set syscon = #{syscon} where codgara = #{codGara}")
	public void updateSysconGara(@Param("codGara") Long codGara, @Param("syscon") Long syscon);

	@Select("select NOMTEC from TECNI where CODTEC=(select RUP from W9GARA where CODGARA=#{codGara}) and CGENTEI=(select CODEIN from W9GARA where CODGARA=#{codGara})")
	public List<String> getNomtecByCodgara(@Param("codGara") Long codGara);

	@Select("select l.OGGETTO as oggettoLotto, substr(coalesce(l.CPV,'00'),1,2) as codiceCPV, t2a.TAB2D2 as descrizioneTipoContratto, t2.TAB2D2 as idCategoriaPrevalente, "
			+ " g.TIPO_APP as tipoAppalto, t3.TAB1DESC as descrizioneTipoAppalto, t1.TAB1DESC as idSceltaContraente, t4.TAB1DESC as criterioAggiudicazione, "
			+ " case when l.IMPORTO_TOT < 40000 then '<40mila' "
			+ " when l.IMPORTO_TOT < 150000 then '40mila-150mila' "
			+ " when l.IMPORTO_TOT < 1000000 then '150mila-1mln' "
			+ " when l.IMPORTO_TOT < 5022500 then '1mln-5.225mln' "
			+ " else '>5.225mln' end as classeImporto, "
			+ " l.IMPORTO_TOT as importoTotaleLotto, "
			+ " p.IMPORTO_AGGIUDICAZIONE as importoAggiudicazione,"
			+ " l.TIPO_CONTRATTO as tipoContratto "
			+ " from W9LOTT l join W9GARA g on g.CODGARA = l.CODGARA "
			+ " left join TAB1 t1 on t1.TAB1COD='W3005' and t1.TAB1TIP=l.ID_SCELTA_CONTRAENTE "
			+ " left join TAB1 t3 on t3.TAB1COD='W3999' and t3.TAB1TIP=g.TIPO_APP "
			+ " left join TAB2 t2 on t2.TAB2COD='W3z03' and t2.TAB2TIP=l.ID_CATEGORIA_PREVALENTE "
			+ " left join TAB1 t4 on t4.TAB1COD='W3007' and t4.TAB1TIP=l.ID_MODO_GARA "
			+ " left join TAB2 t2a on t2a.TAB2COD='W3z05' and t2a.TAB2TIP=l.TIPO_CONTRATTO "
			+ " left join W9APPA p on p.CODGARA = l.CODGARA and p.CODLOTT = l.CODLOTT "
			+ " where l.CIG = #{cig} ")
	public List<LottoIndEntry> getLottoByCig(@Param("cig") String cig);

	@Select("select case when min(ID_APPALTO) = 8 then 99 else min(ID_APPALTO) end from W9APPALAV where CODGARA = #{codGara} and CODLOTT = #{codLotto}")
	public Long getIdAppaltoLav(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select min(ID_APPALTO) from W9APPAFORN where CODGARA = #{codGara} and CODLOTT = #{codLotto}")
	public Long getIdAppaltoForn(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select TIPO from SITAT_IND_TIPI where CIG = #{cig}")
	public String getTipoByCig(@Param("cig") String cig);


	@Select("select i.ID as id, a.CONGRUO as congruo, a.CALCOLABILE as calcolabile, a.SOGLIAINF as sogliaInferiore, a.SOGLIASUP as sogliaSuperiore, i.DESCRIZIONE as descrizione, i.UNIMIS as unitaDiMisura, "
			+ " i.STR_SQL as strSql, i.TIPO as tipo, i.TIPO_SOGLIA as tipoSoglia, i.LIVELLO as livello, a.INCONGRUITA as descrIncongruita, a.AFFIDABILITA as affidabilitaStima, a.NUMEROSITA as numerositaStima "
			+ " from SITAT_IND_ATTUAZIONE a, SITAT_IND_INDICATORI i "
			+ " where a.TIPO=#{tipoDiContratto} and a.idINDICATORE=i.ID and i.NORD > 0 and i.FASE='A' order by i.NORD asc")
	public List<IndicatoreEntry> getListaIndicatoriFaseA(@Param("tipoDiContratto") String tipoDiContratto);

	@Select("select i.ID as id, a.CONGRUO as congruo, a.CALCOLABILE as calcolabile, a.SOGLIAINF as sogliaInferiore, a.SOGLIASUP as sogliaSuperiore, i.DESCRIZIONE as descrizione, i.UNIMIS as unitaDiMisura, "
			+ " i.STR_SQL as strSql, i.TIPO as tipo, i.TIPO_SOGLIA as tipoSoglia, i.LIVELLO as livello, a.INCONGRUITA as descrIncongruita, a.AFFIDABILITA as affidabilitaStima, a.NUMEROSITA as numerositaStima "
			+ " from SITAT_IND_ATTUAZIONE a, SITAT_IND_INDICATORI i "
			+ " where a.TIPO=#{tipoDiContratto} and a.idINDICATORE=i.ID and i.NORD > 0 and i.FASE='E' order by i.NORD asc")
	public List<IndicatoreEntry> getListaIndicatoriFaseE(@Param("tipoDiContratto") String tipoDiContratto);


	@Select("select i.ID, i.DESCRIZIONE as descrizione, i.UNIMIS as unitaDiMisura, a.SOGLIAINF as sogliaInferiore, a.VALORE1Q as percentile25, a.VALORE2Q as percentile50, a.VALORE3Q as percentile75, "
			+ "	a.SOGLIASUP as sogliaSuperiore, a.MEDIA as media, c.PERC_COM as stessoComune, c.PERC_PROV as stessaProvincia, c.PERC_REG as stessaRegione, a.AFFIDABILITA as affidabilitaStima, a.NUMEROSITA as numerositaStima "
			+ "	from SITAT_IND_ATTUAZIONE a, SITAT_IND_INDICATORI i, SITAT_IND_COMUNI c "
			+ "	where a.TIPO=#{tipoDiContratto} and a.idINDICATORE=i.ID and a.idINDICATORE=c.idINDICATORE and i.NORD > 0 and i.FASE='A' "
			+ "	and c.COMUNE=#{codiceIstatComune} order by i.NORD asc")
	public List<IndicatorePremEntry> getListaIndicatoriPremFaseA(@Param("tipoDiContratto") String tipoDiContratto, @Param("codiceIstatComune") String codiceIstatComune);

	@Select("select i.ID, i.DESCRIZIONE as descrizione, i.UNIMIS as unitaDiMisura, a.SOGLIAINF as sogliaInferiore, a.VALORE1Q as percentile25, a.VALORE2Q as percentile50, a.VALORE3Q as percentile75, "
			+ " a.SOGLIASUP as sogliaSuperiore, a.MEDIA as media, c.PERC_COM as stessoComune, c.PERC_PROV as stessaProvincia, c.PERC_REG as stessaRegione, a.AFFIDABILITA as affidabilitaStima, a.NUMEROSITA as numerositaStima "
			+ "	from SITAT_IND_ATTUAZIONE a, SITAT_IND_INDICATORI i, SITAT_IND_COMUNI c "
			+ "	where a.TIPO=#{tipoDiContratto} and a.idINDICATORE=i.ID and a.idINDICATORE=c.idINDICATORE and i.NORD > 0 and i.FASE='E' "
			+ "	and c.COMUNE=#{codiceIstatComune} order by i.NORD asc")
	public List<IndicatorePremEntry> getListaIndicatoriPremFaseE(@Param("tipoDiContratto") String tipoDiContratto, @Param("codiceIstatComune") String codiceIstatComune);

	@Select("select LUOGO_ISTAT from W9LOTT where CIG = #{cig}")
	public List<String> getLuogoIstat(@Param("cig") String cig);

	@Select("select CODCIT from UFFINT where CODEIN = #{codein}")
	public List<String> getCodCit(@Param("codein") String codein);

	@Select("SELECT EXISTS ( SELECT * FROM information_schema.tables WHERE  Upper(table_name) = Upper('SITAT_IND_INDICATORI'))")
	public boolean checkExistsIndTable();

	@Select("SELECT EXISTS ( SELECT * FROM information_schema.tables WHERE  Upper(table_name) = Upper(#{table}))")
	public boolean checkExistsTable(@Param("table") String table);

	@Select("select syscf from usrsys where syscon = #{syscon}")
	public String getCfFromSyscon(@Param("syscon") Long syscon);

	@Select("select cftec from tecni where codtec = = #{rup}")
	public String getCfFromRUPId(@Param("rup") String rup);

	@Select("select max (message_id) from w_message_in")
	public Long getMaxMessageInId();

	@Select("select nomtec from tecni where cftec = #{cfTec}")
	public String getNomTecByCfTec(@Param("cfTec") String cfTec);

	@Insert("insert into w_message_in (message_id,message_date,message_subject,message_body,message_sender_syscon,message_recipient_syscon,message_recipient_read) values (#{id}, #{dataMessaggio}, #{oggetto}, #{corpoMessaggio},#{sysconSender},#{sysconReceiver},0)")
	public void insertMessageIn(MessageInForm form);

	public List<LoaderAppaltoUsrEntry> getLoaderAppaltoUsrByCfRup(@Param("cfRup") String cfRup);

	public void insertLoaderAppaltoUsr(LoaderAppaltoUsrEntry record);

	public void updateLoaderAppaltoUsr(LoaderAppaltoUsrEntry record);

	public void insertLogEventi(LogEventiEntry entry);

	public Long getNextIdEvento();

	@Select("select cogtim as cognome, nometim as nome, cftim as cf from TEIM where codtim = #{codImpresa}")
	public LegaleRappresentanteEntry getLegaleRappresentante(@Param("codImpresa") String codImpresa);

	@Select("select l.cig, p.tipdoc as tipoAtto, cfp.nome, l.oggetto, "
			+ "max(CASE WHEN p.id_ricevuto is null THEN 0 ELSE 1 end) as pubblicato "
			+ "from w9publotto  wp "
			+ " left join w9pubblicazioni p on p.codgara = wp.codgara  and wp.num_pubb = p.num_pubb "
			+ "	join w9lott l on l.codgara = wp.codgara  and wp.codlott = l.codlott "
			+ "	join w9cf_pubb cfp on cfp.id =  p.tipdoc"
			+ "	where p.codgara  = #{codGara} group by l.cig, p.tipdoc, l.oggetto, cfp.nome, l.nlotto, cfp.numord order by l.nlotto, cfp.numord")
	public List<CigIdAtto> getCigTipoAtto(@Param("codGara") Long codGara);

	@Select("select cig, oggetto from w9lott where codgara = #{codGara}")
	public List<Map<String, String>> getCigLottiOggetto(@Param("codGara") Long codGara);

	@Select("select cup from w9lottcup where codgara = #{codGara} and codlott = #{codLotto}")
	public List<String> getCupFromW9lottcup(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(num_aggi) + 1 from esiti_aggiudicatari where codgara = #{codGara} and num_pubb = #{numPubb}")
	public Long checkNumAggiImpresaAggiudicatariaAtto(@Param("codGara") Long codGara, @Param("numPubb") Long numPubb);

	@Select("select count(*) from w9fasi where codgara = #{codGara} and codlott = #{codLotto} and (fase_esecuzione = 2 or fase_esecuzione = 13)")
	public Long getFaseNonAccorpabili(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select count(*) from w9fasi where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa} and fase_esecuzione in (2,11,986)")
	public Long checkExistsFasiIniziali(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa);

	@Select("select count(*) from W9FLUSSI where AREA = 1 and KEY01 = #{codGara} and KEY02 = #{codLotto} and KEY03 = 1")
	public Long checkFaseAggPubblicata(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select sum(importo_tot) from w9lott where codgara = #{codGara} and cig_master_ml = #{cigMaster}")
	public Double getSommaImportiMultiLotto(@Param("codGara") Long codGara, @Param("cigMaster") String cigMaster);

	@Update("update w9iniz set DATA_VERB_INIZIO = #{dataVerbInizio} where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public void updateFaseAffidamentiDirettiIniz(FaseConclusioneAffidamentiDirettiInsertForm form);

	@Update("update w9avan set IMPORTO_CERTIFICATO = #{importoCertificato} where codgara = #{codGara} and codlott = #{codLotto} and num_avan = #{num}")
	public void updateFaseAffidamentiDirettiAvan(FaseConclusioneAffidamentiDirettiInsertForm form);

	@Update("update w9conc set DATA_ULTIMAZIONE = #{dataUltimazione} where codgara = #{codGara} and codlott = #{codLotto} and num_conc = #{num}")
	public void updateFaseAffidamentiDirettiConc(FaseConclusioneAffidamentiDirettiInsertForm form);

	@Select("select DATA_VERB_INIZIO from w9iniz where codgara = #{codGara} and codlott = #{codLotto} and num_iniz = #{num}")
	public Date getFaseAffidamentiDirettiIniz(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("select IMPORTO_CERTIFICATO from w9avan where codgara = #{codGara} and codlott = #{codLotto} and num_avan = #{num}")
	public Double getFaseAffidamentiDirettiAvan(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("select DATA_ULTIMAZIONE from w9conc where codgara = #{codGara} and codlott = #{codLotto} and num_conc = #{num}")
	public Date getFaseAffidamentiDirettiConc(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("select codausa from uffint where codein = #{codein}")
	public String getCodAusaUffint(@Param("codein") String codein);

	@Select("select idpartecipante from w9aggi where codgara = #{codGara} and codlott = #{codLotto} and num_appa = #{numAppa}")
	public List<String> getIdPartecipanteAggi(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa);

	@Select("select i.nomest as ragioneSociale, a.idpartecipante as idPartecipante, a.id_tipoagg as tipoAgg  from w9aggi a left join impr i on a.codimp = i.codimp where a.codgara = #{codGara} and a.codlott = #{codLotto} and a.num_appa = #{numAppa}")
	public List<ImpresaAggIdPartecipEntry> getIdPartecipanteNomestAggi(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa);

	@Select("select i.nomest as ragioneSociale from w9aggi a left join impr i on a.codimp = i.codimp where a.codgara = #{codGara} and a.codlott = #{codLotto} and a.num_appa = #{numAppa}")
	public List<String> getPartecipanteNomestAggi(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("numAppa") Long numAppa);


	@Update("update w9fasi set id_scheda_simog = #{idScheda} where codgara = #{codGara} and codlott = #{codLotto} and num = #{num} and fase_esecuzione = #{codFase}")
	public void setIdSchedaW9fasi(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("codFase") Long codFase, @Param("num") Long num, @Param("idScheda") String idScheda);

	@Update("update w9fasi set id_scheda_simog = null where codgara = #{codGara} and codlott = #{codLotto} and num = #{num} and fase_esecuzione = #{codFase}")
	public void setIdContrattoNullW9fasi(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("codFase") Long codFase, @Param("num") Long num);

	@Select("select id_scheda_simog from w9fasi where codgara = #{codGara} and codlott = #{codLotto} and num = #{num} and fase_esecuzione = #{codFase}")
	public String getIdContrattoPcp(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("codFase") Long codFase, @Param("num") Long num);

	@Update("update uffint set codausa = #{codAusa} where codein = #{codein}")
	public void updateCodusaUffint(@Param("codAusa") String codAusa, @Param("codein") String codein);

	@Select("select count(*) from w9flussi where key01  = #{codGara} and key02 = #{codLotto} and key03 = #{codFase} and tinvio2 = #{TipoInvio}")
	public Long getCountFlussi(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("codFase") Long codFase, @Param("TipoInvio") Long TipoInvio);

	@Select("select codein from uffint where cfein = #{cfein}")
	public String getCodeinByCfSa(@Param("cfein") String cfein);

	@Select("select codistat from g_comuni where codistat like '%'|| #{codistat} ||'%'")
	public List<String> getCodistatlikeCodistat(@Param("codistat") String codistat);

	@Update("update w9aggi set codimp = #{codImp} where idpartecipante = #{idPartecipante} and codgara= #{codGara} and codlott = #{codLotto}")
	public void updateIdPartecipanteW9aggi(@Param("codImp") String codImp, @Param("idPartecipante") String idPartecipante, @Param("codGara") Long codGara, @Param("codLotto") Long codLotto);


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

	@Delete("delete from W9FLUSSI where KEY01 = #{codGara} and KEY03 = #{faseEsecuzione} and TINVIO2 > 0")
	public void deleteFlussoPcp(@Param("codGara") Long codGara, @Param("faseEsecuzione") Long faseEsecuzione);

	@Delete("delete from W9FLUSSI where KEY01 = #{codGara} and KEY03 = #{faseEsecuzione} and key02 = #{codLotto} and TINVIO2 > 0")
	public void deleteFlussoPcpLotto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("faseEsecuzione") Long faseEsecuzione);


	@Delete("delete from W9FLUSSI where KEY01 = #{codGara} and KEY03 = #{numFase} and key02 = #{codLotto} and key04 = #{num} and TINVIO2 = #{tipoInvio}")
	public void deleteFlussoPcpLottoTinvio(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("numFase") Long numFase, @Param("num") Long num, @Param("tipoInvio") Long tipoInvio);

	@Select("select count(*) from usr_loa where syscon = #{syscon} and loa >= 3 and (codein = #{codein}) and (provider = 'SPID' or provider = 'CIE')")
	public Long getIdpUserLoa(@Param("syscon") Long syscon, @Param("codein") String codein);

	@Select("SELECT SUBSTRING(tab2d2, 1, POSITION(' ' IN tab2d2) - 1) AS dateVersion , tab2d1 as version FROM tab2 where TAB2COD = 'W9z06' order by tab2tip asc")
	public List<DateVersionPcpEntry> getVersionsPcp();

	@Select("SELECT SUBSTR(tab2d2, 1, INSTR(tab2d2, ' ') - 1) AS dateVersion , tab2d1 as version FROM tab2 where TAB2COD = 'W9z06'")
	public List<DateVersionPcpEntry> getVersionsPcpOra();

	@Select("SELECT SUBSTRING(tab2d2, 1, CHARINDEX(' ', tab2d2) - 1) AS dateVersion , tab2d1 as version FROM tab2 where TAB2COD = 'W9z06'")
	public List<DateVersionPcpEntry> getVersionsPcpMsq();

	@Select("select count(*) from CENTRICOSTO where UPPER(CODCENTRO) = #{codiceCentroCosto} and CODEIN = #{codein} ")
	public Long getCountcdc(@Param("codiceCentroCosto") String codiceCentroCosto, @Param("codein") String codein);

	@Select("select codlott from w9lott where cig = #{cig} and codgara = #{codgara}")
	public Long getCodLottByCig(@Param("cig") String cig, @Param("codgara") Long codgara);

	@Select("select codlott from w9iniz where codcontratto = #{codcontratto}")
	public List<Long> getCodLottByIdContratto(@Param("codcontratto") String codcontratto);

	@Select("Select tipo_app from w9gara where codgara = #{codGara} ")
	public Long getTipoAppGara(@Param("codGara") Long codGara);

	@Select("select codlott from w9fasi where id_scheda_simog = #{idSchedaSimog} and codgara = #{codGara}")
	public List<Long> getCodLottByIdScheda(@Param("idSchedaSimog") String idSchedaSimog, @Param("codGara") Long codGara);

	@Select("select num from w9fasi where id_scheda_simog = #{idSchedaSimog} and codgara = #{codGara}")
	public List<Long> getW9fasiNumByIdScheda(@Param("idSchedaSimog") String idSchedaSimog, @Param("codGara") Long codGara);

	@Select("select count(*) from w9fasi where daexport <> '2' and fase_esecuzione > 1 and codgara = #{codGara}")
	public Long checkExistsFasiNonInviate(@Param("codGara") Long codGara);

	@Select(
		"WITH lotti_aggiudicatariicati AS ( " +
		"SELECT L.*, A.CODIMP " +
		"FROM W9LOTT L " +
		"JOIN W9AGGI A ON A.CODGARA = L.CODGARA AND A.CODLOTT = L.CODLOTT " +
		"WHERE EXISTS ( SELECT 1 FROM W9FLUSSI Fl WHERE Fl.KEY01 = L.CODGARA AND Fl.KEY02 = L.CODLOTT AND Fl.KEY03 = 1 AND Fl.TINVIO2 > 0 ) " +
		"AND NOT EXISTS ( SELECT 1 FROM W9FASI Fa WHERE Fa.CODGARA = L.CODGARA AND Fa.CODLOTT = L.CODLOTT AND Fa.FASE_ESECUZIONE IN (2, 13) ) ), " +
		"aggiudicatari AS ( SELECT l1.CODGARA, l2.CODIMP FROM lotti_aggiudicatariicati l1 LEFT JOIN lotti_aggiudicatariicati l2 ON l1.CODGARA = l2.CODGARA AND l1.CODLOTT <> l2.CODLOTT AND l1.CODIMP = l2.CODIMP ) " +
		"SELECT COUNT(*) FROM aggiudicatari a1 WHERE NOT EXISTS (SELECT 1 FROM aggiudicatari a2 WHERE a1.CODGARA = a2.CODGARA AND a2.CODIMP IS NULL) AND a1.CODGARA = #{codGara} ")
	Long checkAllowMultilotto(@Param("codGara") Long codGara);

	@Update("update w9lott set ID_MODO_GARA = #{idModoGara} where codgara=  #{codGara} and codlott = #{codlott}")
	public void updateW9lottIdModoGara(@Param("codGara") Long codGara, @Param("codlott") Long codlott, @Param("idModoGara") Long idModoGara);

	@Update("update w9lott set ID_TIPO_PRESTAZIONE = #{idTipoPrestazione} where codgara=  #{codGara} and codlott = #{codlott}")
	public void updateW9lottIdTipoPrestazione(@Param("codGara") Long codGara, @Param("codlott") Long codlott, @Param("idTipoPrestazione") Long idTipoPrestazione);

	@Select("select num_appa from w9appa where codgara = #{codGara} and codlott = #{codLotto}")
	public List<Long> getlistaNumAppa(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Insert("insert into w9reg_invii_pcp (id, datinv, autore, id_scheda) values (#{id},#{dataInvio},#{autore},#{idScheda})")
	public void insertW9RegInviiPcp(W9RegInviiPcpEntry entry);

	@Update("update w9gara set importo_gara = (select sum(importo_tot) from w9lott where codgara = #{codGara})  where codgara = #{codGara}")
	public void updateImportoGaraDaLotti(@Param("codGara") Long codGara);

	public List<AllegatoEntry> getListaAllegatiEntry(@Param("codGara") String codGara, @Param("numPubb") String numPubb);

	@Update("UPDATE w9flussi SET autore = (SELECT r.autore FROM w9fasi f JOIN w9reg_invii_pcp r ON r.id_scheda = f.id_scheda_simog WHERE f.codgara = key01 AND f.codlott = key02 AND f.fase_esecuzione = key03 AND f.num = key04) WHERE key01 = #{codGara} AND key02 = #{codLotto}")
	public void updateAutoreInvioSchede(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select max(id) from W9ASSOCIAZIONECAMPI")
	public Long getW9AssociazioneCampiMaxId();

	@Insert("insert into W9ASSOCIAZIONECAMPI (ID, CODGARA, CODLOTT, FASE_ESECUZIONE, NUM,NOME_CAMPO, ID_ESTERNO, APP_ESTERNO) VALUES (#{id}, #{codGara}, #{codLotto}, #{faseEsecuzione}, #{num},#{nomeCampo}, #{idEsterno}, #{appEsterno})")
	public void insertW9AssociazioneCampi(W9AssociazioneCampi record);

	@Delete("delete from W9ASSOCIAZIONECAMPI where CODGARA = #{codGara} and CODLOTT = #{codLotto} and FASE_ESECUZIONE = #{faseEsecuzione} and NUM = #{num} and NOME_CAMPO = #{nomeCampo}" )
	void deleteW9AssociazioneCampi(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,  @Param("faseEsecuzione") Long faseEsecuzione, @Param("num") Long num, @Param("nomeCampo") String nomeCampo);

	@Select("SELECT a.ID AS id, a.CODGARA AS codGara, a.CODLOTT AS codLotto, a.FASE_ESECUZIONE AS faseEsecuzione, tbf.tab1desc AS faseDesc, a.NUM AS num, a.NOME_CAMPO AS nomeCampo, c.coc_des_web AS nomeCampoDesc, a.ID_ESTERNO AS idEsterno, a.APP_ESTERNO AS appEsterno "
			+ "FROM W9ASSOCIAZIONECAMPI a "
			+ "LEFT JOIN tab1 tbf ON tbf.tab1cod = 'W3020' AND tbf.tab1tip = a.fase_esecuzione "
			+ "LEFT JOIN c0campi c ON c.coc_mne_uni = a.nome_campo "
			+ "WHERE a.CODGARA = #{codGara} AND a.CODLOTT = #{codLotto}")
	public List<W9AssociazioneCampiEntry> getW9AssociazioneCampiByCodGaraAndCodLotto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Delete("DELETE FROM w9allegati a WHERE a.key01 = #{codGara}")
	public void deleteAllegatiByCodGaraKey01(@Param("codGara") String codGara);

	@Update("UPDATE w9allegati SET motivo_canc = #{motivoCanc}, utente_canc = #{syscon}, datacanc = #{dataCanc} WHERE key01 = #{codGara} and idallegato = #{idAllegato}")
	public void deleteLogicaAttoDocumento(@Param("codGara") final String codGara, @Param("idAllegato") Long idAllegato, @Param("syscon") final Long syscon, @Param("dataCanc") final Date dataCanc, @Param("motivoCanc") final String motivoCanc);

	@Insert("INSERT INTO w9allegati(idallegato, key01, key02, key03, tabella, descri, file_allegato, url, tipo_file, num_ordine, utente_creatore, dataaggiunta, utente_canc, datacanc, motivo_canc) VALUES (#{idAllegato}, #{key01}, #{key02}, #{key03}, #{tabella}, #{descrizione}, #{fileAllegato}, #{url}, #{tipoFile}, #{numOrdine}, #{utenteCreatore}, #{dataAggiunta}, #{utenteCanc}, #{dataCanc}, #{motivoCanc})")
	public void insertAllegatoAtto(AllegatoEntry form);

	@Update("UPDATE w9allegati SET descri = #{descrizione}, file_allegato = #{fileAllegato}, url = #{url} WHERE key01 = #{key01} and idallegato = #{idAllegato} and key02 = #{key02}")
	public void updateAllegatoAtto(AllegatoEntry form);

	public AllegatoEntry getAllegatoByCodGaraAndIdAllegato(@Param("codGara") String codGara, @Param("idAllegato") Long idAllegato);

	@Select("select codgara as codGara, num_pubb as numPubb, datapubb as dataPubb, descriz, datascad as dataScad, data_decreto as dataDecreto, data_provvedimento as dataProvvedimento, num_provvedimento as numProvvedimento, data_stipula as dataStipula, num_repertorio as numRepertorio, perc_ribasso_agg as percRibassoAgg, perc_off_aumento as percOffAumento, importo_aggiudicazione as importoAggiudicazione, data_verb_aggiudicazione as dataVerbAggiudicazione, id_generato as idGenerato, id_ricevuto as idRicevuto, url_committente as urlCommittente, url_eproc as urlEproc, tipdoc as tipDoc, primapubblicazione as primaPubblicazione, datapubbsistema as dataPubbSistema, utente_canc as utenteCancellazione, datacanc as dataCancellazione, motivo_canc as motivoCancellazione from W9PUBBLICAZIONI where codgara = #{codGara} and num_pubb = #{numPubb}")
	public DettaglioAttoEntry getDettaglioAttoSingolo(@Param("codGara") Long codGara, @Param("numPubb") Long numPubb);

	@Update("UPDATE w9pubblicazioni SET datapubbsistema = #{dataProgrammazione} WHERE codgara = #{codGara} and num_pubb = #{numPubb}")
	public void pubblicaAttoSingolo(@Param("codGara") Long codGara, @Param("numPubb") Long numPubb, @Param("dataProgrammazione") Date dataProgrammazione);

	@Delete("DELETE FROM w9pubblicazioni a WHERE a.codgara = #{codGara} AND a.num_pubb = #{numPubb}")
	public void deleteAttoSingolo(@Param("codGara") Long codGara, @Param("numPubb") Long numPubb);

	@Update("UPDATE w9pubblicazioni SET datapubbsistema = #{dataProgrammazione} WHERE codgara = #{codGara} and num_pubb = #{numPubb}")
	public void programmaPubblicazioneAtto(@Param("codGara") Long codGara, @Param("numPubb") Long numPubb, @Param("dataProgrammazione") Date dataProgrammazione);

	@Update("UPDATE w9pubblicazioni SET datapubbsistema = #{dataProgrammazione} WHERE codgara = #{codGara} and num_pubb = #{numPubb}")
	public void annullaPubblicazione(@Param("codGara") Long codGara, @Param("numPubb") Long numPubb, @Param("dataProgrammazione") Date dataProgrammazione);

	@Update("UPDATE w9pubblicazioni SET motivo_canc = #{motivazione}, utente_canc = #{utenteCanc}, datacanc = #{dataCanc} WHERE codgara = #{codGara} and num_pubb = #{numPubb}")
	public void annullaPubblicazioneMotivazione(@Param("codGara") Long codGara, @Param("numPubb") Long numPubb, @Param("motivazione") String motivazione, @Param("utenteCanc") Long utenteCanc, @Param("dataCanc") Date dataCanc);

	@Select("select count(*) from w9aggi where codgara = #{codGara} and codlott = #{codLotto} and idpartecipante = #{idPartecipante}")
	public Long checkExistAggiudicatario(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("idPartecipante") String idPartecipante);

	@Select("select id_scheda_locale from w9fasi where codgara = #{codGara} and codlott = #{codLotto} and num = #{num} and fase_esecuzione = #{numFase}")
	public String getIdSchedaLocale(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto, @Param("num") Long num, @Param("numFase") Long numFase);

	@Select("select sum(NUM_GIORNI_PROROGA) from w9vari where codgara = #{codGara} and codlott = #{codLotto}")
	Long ggProrogaModificaContrattuale(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select " +
			" usr.syscon, " +
			" g.codgara as codGara, " +
			" l.codlott as codLott, " +
			" g.idappalto as idAppalto, " +
			" l.cig  " +
			"from " +
			" w9gara g " +
			"join w9lott l on " +
			" g.codgara = l.codgara " +
			"join w9appa ap on " +
			" l.codgara = ap.codgara " +
			" and l.codlott = ap.codlott " +
			"join w_alert_schede a on " +
			" l.IMPORTO_TOT >= a.fascia_importo_min " +
			" and l.IMPORTO_TOT<a.fascia_importo_max " +
			" and l.TIPO_CONTRATTO = tipo_settore " +
			" and a.codice = 'AGG-INI' " +
			" and (DATE_PART('day', CURRENT_DATE - ap.data_verb_aggiudicazione)::int = a.numero_giorni or DATE_PART('day', CURRENT_DATE - ap.data_verb_aggiudicazione)::int = a.numero_giorni + 30 ) " +
			"join tecni t on " +
			" t.codtec = g.rup " +
			"join usrsys usr on " +
			" usr.syscf = t.cftec " +
			"where " +
			" ap.data_verb_aggiudicazione is not null " +
			" and  " +
			"not exists ( " +
			" select " +
			"  * " +
			" from " +
			"  w9fasi f " +
			" where " +
			"  f.codgara = l.codgara " +
			"  and f.codlott = l.codlott " +
			"  and f.num_appa = ap.num_appa " +
			"  and f.daexport = '2' " +
			"  and f.fase_esecuzione = 13)")
	List<AlertRupEntry> getAlertRupAggIni();

	@Select("select " +
			" usr.syscon, " +
			" g.codgara as codGara, " +
			" l.codlott as codLott, " +
			" g.idappalto as idAppalto, " +
			" l.cig  " +
			"from " +
			" w9gara g " +
			"join w9lott l on " +
			" g.codgara = l.codgara " +
			"join w9iniz i on " +
			" l.codgara = i.codgara " +
			" and l.codlott = i.codlott " +
			"join w_alert_schede a on " +
			" l.IMPORTO_TOT >= a.fascia_importo_min " +
			" and l.IMPORTO_TOT<a.fascia_importo_max " +
			" and l.TIPO_CONTRATTO = tipo_settore " +
			" and a.codice = 'STI-INI' " +
			" and (DATE_PART('day', CURRENT_DATE - i.data_stipula)::int = a.numero_giorni or DATE_PART('day', CURRENT_DATE - i.data_stipula)::int = a.numero_giorni + 30 ) " +
			"join tecni t on " +
			" t.codtec = g.rup " +
			"join usrsys usr on " +
			" usr.syscf = t.cftec " +
			"where " +
			" i.data_stipula is not null " +
			" and  " +
			"not exists ( " +
			" select " +
			"  * " +
			" from " +
			"  w9fasi f " +
			" where " +
			"  f.codgara = l.codgara " +
			"  and f.codlott = l.codlott " +
			"  and f.num_appa = i.num_appa " +
			"  and f.daexport = '2' " +
			"  and f.fase_esecuzione = 2)")
	List<AlertRupEntry> getAlertRupStiIni();





	@Select("select  " +
			"  usr.syscon,  " +
			"  g.codgara as codGara,  " +
			"  l.codlott as codLott,  " +
			"  g.idappalto as idAppalto,  " +
			"  l.cig  " +
			"      from  " +
			"  w9gara g  " +
			"  join w9lott l on  " +
			"  g.codgara = l.codgara  " +
			"  join w9iniz i on  " +
			"  l.codgara = i.codgara  " +
			"  and l.codlott = i.codlott  " +
			"  join w_alert_schede a on  " +
			"  l.IMPORTO_TOT >= a.fascia_importo_min  " +
			"  and l.IMPORTO_TOT<a.fascia_importo_max  " +
			"  and l.TIPO_CONTRATTO = tipo_settore  " +
			"  and a.codice = 'INI-SAL'  " +
			"  and (DATE_PART('day', CURRENT_DATE - i.data_ini_prog_esec)::int = a.numero_giorni or DATE_PART('day', CURRENT_DATE - i.data_ini_prog_esec)::int = a.numero_giorni + 30 )  " +
			"  join tecni t on  " +
			"  t.codtec = g.rup  " +
			"  join usrsys usr on  " +
			"  usr.syscf = t.cftec  " +
			"      where  " +
			"  i.data_ini_prog_esec is not null  " +
			"  and  " +
			"  not exists (  " +
			"      select  " +
			"    *  " +
			"  from  " +
			"  w9fasi f  " +
			"  where  " +
			"  f.codgara = l.codgara  " +
			"  and f.codlott = l.codlott  " +
			"  and f.num_appa = i.num_appa  " +
			"  and f.daexport = '2'  " +
			"  and (f.fase_esecuzione = 2 or f.fase_esecuzione = 3))")
	List<AlertRupEntry> getAlertRupIniSal();


	@Select("select " +
			" usr.syscon, " +
			" g.codgara as codGara, " +
			" l.codlott as codLott, " +
			" g.idappalto as idAppalto, " +
			" l.cig  " +
			"from " +
			" w9gara g " +
			"join w9lott l on " +
			" g.codgara = l.codgara " +
			"join w9conc c on " +
			" l.codgara = c.codgara " +
			" and l.codlott = c.codlott " +
			"join w_alert_schede a on " +
			" l.IMPORTO_TOT >= a.fascia_importo_min " +
			" and l.IMPORTO_TOT<a.fascia_importo_max " +
			" and l.TIPO_CONTRATTO = tipo_settore " +
			" and a.codice = 'CONC-COLL' " +
			" and (DATE_PART('day', CURRENT_DATE - c.data_ultimazione)::int = a.numero_giorni or DATE_PART('day', CURRENT_DATE - c.data_ultimazione)::int = a.numero_giorni + 30 ) " +
			"join tecni t on " +
			" t.codtec = g.rup " +
			"join usrsys usr on " +
			" usr.syscf = t.cftec " +
			"where " +
			" c.data_ultimazione is not null " +
			" and g.SITUAZIONE<90 " +
			" and l.EXSOTTOSOGLIA='2' " +
			" and  " +
			"not exists ( " +
			" select " +
			"  * " +
			" from " +
			"  w9fasi f " +
			" where " +
			"  f.codgara = l.codgara " +
			"  and f.codlott = l.codlott " +
			"  and f.num_appa = c.num_appa " +
			"  and f.daexport = '2' " +
			"  and f.fase_esecuzione = 5)")
	List<AlertRupEntry> getAlertRupSalConc();

	@Select("select " +
			" usr.syscon, " +
			" g.codgara as codGara, " +
			" l.codlott as codLott, " +
			" g.idappalto as idAppalto, " +
			" l.cig  " +
			"from " +
			" w9gara g " +
			"join w9lott l on " +
			" g.codgara = l.codgara " +
			"join w9avan av on " +
			" l.codgara = av.codgara " +
			" and l.codlott = av.codlott " +
			"join w_alert_schede a on " +
			" l.IMPORTO_TOT >= a.fascia_importo_min " +
			" and l.IMPORTO_TOT<a.fascia_importo_max " +
			" and l.TIPO_CONTRATTO = tipo_settore " +
			" and a.codice = 'STI-INI' " +
			" and (DATE_PART('day', CURRENT_DATE - av.data_raggiungimento)::int = a.numero_giorni or DATE_PART('day', CURRENT_DATE - av.data_raggiungimento)::int = a.numero_giorni + 30 ) " +
			"join tecni t on " +
			" t.codtec = g.rup " +
			"join usrsys usr on " +
			" usr.syscf = t.cftec " +
			"where " +
			" av.data_raggiungimento is not null " +
			" and g.SITUAZIONE<90 " +
			" and l.EXSOTTOSOGLIA='2' " +
			" and  " +
			"not exists ( " +
			" select " +
			"  * " +
			" from " +
			"  w9fasi f " +
			" where " +
			"  f.codgara = l.codgara " +
			"  and f.codlott = l.codlott " +
			"  and f.num_appa = av.num_appa " +
			"  and f.daexport = '2' " +
			"  and ( f.fase_esecuzione = 4 or (f.fase_esecuzione = 3 and f.num > av.num_avan)) " +
			"  )")
	List<AlertRupEntry> getAlertRupConcColl();
}
