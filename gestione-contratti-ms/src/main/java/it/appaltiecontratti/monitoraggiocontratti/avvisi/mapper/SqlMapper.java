package it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.FaseSospensioneEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.IncarichiProfEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LottoFullEntry;

@Mapper
public interface SqlMapper {
	
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
    
    @Select("select sysute from usrsys where syscon = #{syscon}")
    public String getNameBySyscon(@Param("syscon") Long syscon);
    
    @Select("select codein from uffint where cfein = #{cfein}")
    public String getCodeinByCf(@Param("cfein") String cfein);
    
    
    @SelectProvider(type = PureSqlProvider.class, method = "sql")
    public List<Map<String,Object>> select(String sql);

    @SelectProvider(type = PureSqlProvider.class, method = "count")
    public Integer count(String from);

    @SelectProvider(type = PureSqlProvider.class, method = "sql")
    public Integer execute(String query);

    @SelectProvider(type = PureSqlProvider.class, method = "sqlObject")
    public String executeReturnString(String query);
	


	@Select("select count(codimp) from IMPR where cfimp = #{codiceFiscale} and cgenimp = #{stazioneAppaltante}")
	public Long findImpresa(@Param("codiceFiscale") String codiceFiscale,
			@Param("stazioneAppaltante") String stazioneAppaltante);

	@Select("select CODGARA as codGara, CODLOTT as codLotto, CIG as cig, OGGETTO as oggetto, CUIINT as cui, URL_EPROC as urlEproc, SITUAZIONE as situazione, NLOTTO as numLotto, COMCON as contrattoPrec, DESCOM as motivoCompletamento, CIGCOM as cigContratto, TIPO_CONTRATTO as tipologia, FLAG_ENTE_SPECIALE as tipoSettore, ART_E1 as contrattoEscluso, ID_SCELTA_CONTRAENTE as sceltaContraente, ID_SCELTA_CONTRAENTE_50 as sceltaContraenteLgs, ID_MODO_GARA as criteriAggiudicazione, ID_SCHEDA_LOCALE as idSchedalocale, RUP as rup, IMPORTO_LOTTO as importoNetto, IMPORTO_ATTUAZIONE_SICUREZZA as importoSicurezza, IMPORTO_TOT as importoTotale, CUPESENTE as esenteCup, CUP as cup, CPV as cpv, MANOD as manodopera, ID_CATEGORIA_PREVALENTE as categoriaPrev, CLASCAT as classeCategoriaPrev, LUOGO_ISTAT as luogoIstat, LUOGO_NUTS as luogoNuts, ID_TIPO_PRESTAZIONE as prestazioneComprese, EXSOTTOSOGLIA as exsottosoglia, id_scheda_simog as idSchedaSimog, CIG_MASTER_ML as masterCig FROM W9LOTT where codgara = #{codiceGara} and codlott = #{codiceLotto}")
	public LottoEntry getDettaglioLotto(@Param("codiceGara") Long codiceGara, @Param("codiceLotto") Long codiceLotto);

	@Select("select ESITO_PROCEDURA from W9ESITO where codgara = #{codGara} and codlott = #{codLotto}")
	public String getEsitoProcedura(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

	@Select("select count(codgara) from w9moti where codgara = #{codGara} and codlott = #{codLotto} and id_motivo_var='18'")
	public int getCountIdMotivoVar(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);
	
	@Select("select imp_compl_appalto from w9vari where codgara = #{codGara} and codlott = #{codLotto} and num_vari =(select max(num_vari) from w9vari where codgara = #{codGara} and codlott = #{codLotto}) ")
	public Double getImportoUltimaFaseVariante(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);
	
	@Select("select data_verb_aggiudicazione from w9appa where codlott = #{codLotto} and codgara = #{codGara} and num_appa = #{numAppa}")
	public Date getDataVerbAggiudicazione(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numAppa") Long numAppa);

	@Select("select data_ultimazione from w9conc where codlott = #{codLotto} and codgara = #{codGara} and num_appa = #{numAppa}")
	public Date getDataUltimazione(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,  @Param("numAppa") Long numAppa);

	@Select("select data_verb_inizio from w9iniz where codlott = #{codLotto} and codgara = #{codGara}  and num_appa = #{numAppa}")
	public Date getDataVerbInizio(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,  @Param("numAppa") Long numAppa);

	@Select("select min(a.data_verb_aggiudicazione) from w9appa a, w9lott l where a.codgara = l.codgara and a.codlott = l.codlott and a.codgara = #{codGara} and a.num_appa = #{numAppa} and l.CIG_MASTER_ML = #{masterCig}")
	public Date getDataVerbAggiudicazioneMinMultilotto(@Param("codGara") Long codGara, @Param("numAppa") Long numAppa, @Param("masterCig")String masterCig);
	
	@Select("select max(a.data_verb_aggiudicazione) from w9appa a, w9lott l where a.codgara = l.codgara and a.codlott = l.codlott and a.codgara = #{codGara} and a.num_appa = #{numAppa} and l.CIG_MASTER_ML = #{masterCig}")
	public Date getDataVerbAggiudicazioneMaxMultilotto(@Param("codGara") Long codGara, @Param("numAppa") Long numAppa, @Param("masterCig")String masterCig);
	
	@Select("select importo_compl_appalto from w9appa where codlott = #{codLotto} and codgara = #{codGara} and num_appa = #{numAppa}")
	public Double getImportoComplessivoAppalto(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numAppa") Long numAppa);
	
	@Select("select SUM(COALESCE(a.importo_compl_appalto,0)) from w9appa a, w9lott l where a.codgara = l.codgara and a.codlott = l.codlott and a.codgara = #{codGara} and a.num_appa = #{numAppa} and l.CIG_MASTER_ML = #{masterCig}")
	public Double getImportoComplessivoAppaltoMultilotto(@Param("codGara") Long codGara, @Param("masterCig") String masterCig);

	@Select("select importo_compl_intervento from w9appa where codlott = #{codLotto} and codgara = #{codGara} and num_appa = #{numAppa}")
	public Double getImportoComplessivoIntervento(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numAppa") Long numAppa);
	
	@Select("select SUM(COALESCE(a.importo_compl_intervento,0)) from w9appa a, w9lott l where a.codgara = l.codgara and a.codlott = l.codlott and a.codgara = #{codGara} and a.num_appa = #{numAppa} and l.CIG_MASTER_ML = #{masterCig}")
	public Double getImportoComplessivoInterventoMultilotto(@Param("codGara") Long codGara, @Param("numAppa") Long numAppa, @Param("masterCig")String masterCig);
	
	@Select("select codgara as codGara, codlott as codLotto, num as num, num_inca as numInca, sezione as sezione, id_ruolo as idRuolo, cig_prog_esterna as cigProgEsterna, data_aff_prog_esterna as dataAffProgEsterna, data_cons_prog_esterna as dataConsProgEsterna, codtec as codiceTecnico from W9INCA where codgara = #{codGara} and codLott = #{codLotto} and num = #{num} and sezione in('PA','RA')")
	public List<IncarichiProfEntry> getIncarichiProfessionaliFaseAggiudicazione(@Param("codGara") Long codGara,
			@Param("codLotto") Long codLotto, @Param("num") Long num);

	@Select("select codgara as codGara, codlott as codLotto, fase_esecuzione as fase, num as progressivo, daexport as daExportDb from W9FASI join TAB1 t1 on t1.TAB1COD='W3020' where codgara = #{codgara} and codlott = #{codlott} and num_appa = #{numAppa} and t1.tab1tip =fase_esecuzione order by t1.tab1nord,num")
	public List<FaseEntry> getFasiLotto(@Param("codgara") Long codgara, @Param("codlott") Long codlott,
			@Param("numAppa") Long numAppa);
	
	@Select("select fl.KEY03 from W9FLUSSI fl inner join w9fasi f "
			+ " on fl.key01 = f.codgara and fl.key02 = f.codlott and fl.key03 = f.fase_esecuzione and fl.key04 = f.num "
			+ " where fl.AREA = 1 and fl.KEY01 = #{codGara} and fl.KEY02 = #{codLotto} and fl.tinvio2 > 0 and f.num_appa = #{numAppa}")
	public List<Long> getFasiInviate(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("numAppa") Long numAppa);
	
	@Select("select codgara as codGara, codlott as codLotto, num_sosp as num, data_verb_sosp as dataVerbSosp, data_verb_ripr as dataVerbRipr, id_motivo_sosp as motivoSosp, flag_supero_tempo as flagSuperoTempo, flag_riserve as flagRiserve, flag_verbale as flagVerbale, sosp_parziale as sospParziale, inc_crono as incCrono, data_super_quarto as dataSuperQuarto from w9sosp where codgara = #{codGara} and codlott = #{codLotto} and num_sosp = #{num} ")
	public FaseSospensioneEntry getFaseSospensione(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto,
			@Param("num") Long num);

	@Select("select max(num_appa) from w9appa where codgara = #{codGara} and codlott = #{codLotto}")
	public Long getMaxNumAppa(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);
	
	@Select("select CODGARA as codGara, CODLOTT as codLotto, CIG as cig, OGGETTO as oggetto, CUIINT as cui, URL_EPROC as urlEproc, SITUAZIONE as situazione, NLOTTO as numLotto, COMCON as contrattoPrec, DESCOM as motivoCompletamento, CIGCOM as cigContratto, TIPO_CONTRATTO as tipologia, FLAG_ENTE_SPECIALE as tipoSettore, ART_E1 as contrattoEscluso, ID_SCELTA_CONTRAENTE as sceltaContraente, ID_SCELTA_CONTRAENTE_50 as sceltaContraenteLgs, ID_MODO_GARA as criteriAggiudicazione, ID_SCHEDA_LOCALE as idSchedalocale, ID_SCHEDA_SIMOG as idSchedaSimog, RUP as rup, IMPORTO_LOTTO as importoNetto, IMPORTO_ATTUAZIONE_SICUREZZA as importoSicurezza, IMPORTO_TOT as importoTotale, CUPESENTE as esenteCup, CUP as cup, CPV as cpv, MANOD as manodopera, ID_CATEGORIA_PREVALENTE as categoriaPrev, CLASCAT as classeCategoriaPrev, LUOGO_ISTAT as luogoIstat, LUOGO_NUTS as luogoNuts, ID_TIPO_PRESTAZIONE as prestazioneComprese, EXSOTTOSOGLIA as exsottosoglia, codint as codiceInterno, art_e2 as contrattoEscluso161718, somma_urgenza as sommaUrgenza, cig_master_ml as masterCig, CONTRATTO_ESCLUSO_ALLEGGERITO as contrattoEsclusoAlleggerito, ESCLUSIONE_REGIME_SPECIALE as esclusioneRegimeSpeciale FROM W9LOTT where codgara = #{codiceGara}")
	public List<LottoFullEntry> getFullLottiGara(@Param("codiceGara") Long codiceGara);
	
	@Select("select data_stipula from w9iniz where codlott = #{codLotto} and codgara = #{codGara}")
	public Date getDataStipula(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);
    
}