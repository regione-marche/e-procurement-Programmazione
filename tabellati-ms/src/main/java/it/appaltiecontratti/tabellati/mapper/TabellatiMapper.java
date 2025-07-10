package it.appaltiecontratti.tabellati.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import it.appaltiecontratti.tabellati.entity.ArchivioSaSearchform;
import it.appaltiecontratti.tabellati.entity.CdcNonPaginatiSearchForm;
import it.appaltiecontratti.tabellati.entity.CentriDiCostoOptionsSearchForm;
import it.appaltiecontratti.tabellati.entity.CentriDiCostoSearchform;
import it.appaltiecontratti.tabellati.entity.CentroDiCostoEntry;
import it.appaltiecontratti.tabellati.entity.CentroDiCostoInsertForm;
import it.appaltiecontratti.tabellati.entity.EntitaCollegate;
import it.appaltiecontratti.tabellati.entity.ImpresaBaseEntry;
import it.appaltiecontratti.tabellati.entity.ImpresaEntry;
import it.appaltiecontratti.tabellati.entity.ImpresaInsertForm;
import it.appaltiecontratti.tabellati.entity.ImpreseNonPaginateSearchForm;
import it.appaltiecontratti.tabellati.entity.ImpreseSearchForm;
import it.appaltiecontratti.tabellati.entity.InfoC0ampi;
import it.appaltiecontratti.tabellati.entity.LegaleRappresentanteEntry;
import it.appaltiecontratti.tabellati.entity.MessageEntry;
import it.appaltiecontratti.tabellati.entity.MessageForm;
import it.appaltiecontratti.tabellati.entity.MessageInForm;
import it.appaltiecontratti.tabellati.entity.MessageRecForm;
import it.appaltiecontratti.tabellati.entity.MessageReceiverEntry;
import it.appaltiecontratti.tabellati.entity.RupEntry;
import it.appaltiecontratti.tabellati.entity.RupInsertForm;
import it.appaltiecontratti.tabellati.entity.RupSearchForm;
import it.appaltiecontratti.tabellati.entity.SAEntry;
import it.appaltiecontratti.tabellati.entity.SearchMainSARupForm;
import it.appaltiecontratti.tabellati.entity.SingolaImpresaSearchForm;
import it.appaltiecontratti.tabellati.entity.StazioneAppaltanteBaseEntry;
import it.appaltiecontratti.tabellati.entity.StazioneAppaltanteEntry;
import it.appaltiecontratti.tabellati.entity.StazioneAppaltanteInsertForm;
import it.appaltiecontratti.tabellati.entity.StazioneAppaltanteListaEntry;
import it.appaltiecontratti.tabellati.entity.StazioneAppaltanteSearchForm;
import it.appaltiecontratti.tabellati.entity.StazioneAppaltanteUpdateForm;
import it.appaltiecontratti.tabellati.entity.TabNuts;
import it.appaltiecontratti.tabellati.entity.TabellatoCPVEntry;
import it.appaltiecontratti.tabellati.entity.TabellatoIstatEntry;
import it.appaltiecontratti.tabellati.entity.TecniciNonPaginatiSearchForm;
import it.appaltiecontratti.tabellati.entity.TecniciSearchForm;
import it.appaltiecontratti.tabellati.entity.UffEntry;
import it.appaltiecontratti.tabellati.entity.UffInsertForm;
import it.appaltiecontratti.tabellati.entity.UffSearchForm;
import it.appaltiecontratti.tabellati.entity.UfficiNonPaginatiSearchForm;
import it.appaltiecontratti.tabellati.entity.UfficiSearchForm;
import it.appaltiecontratti.tabellati.entity.UsrSysconEntry;
import org.springframework.data.jpa.repository.Query;

/**
 * DAO Interface per l'estrazione delle informazioni relative ai tabellati
 * mediante MyBatis.
 * 
 * @author Michele.DiNapoli
 */
@Mapper
public interface TabellatiMapper {

	class PureSqlProvider {
		public String sql(String sql) {
			return sql;
		}
	}

	/**
	 * Estrae le righe da tab1 con tab1cod uguale al filtro .
	 * 
	 * @param cod codice da filtrare nella tab1
	 * @return elenco delle righe filtrate
	 */
	@Select("SELECT tab1tip codice, tab1desc descrizione, tab1arc archiviato from tab1 where tab1cod= #{cod} order by tab1nord, tab1tip")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public List<TabellatoIstatEntry> getTabellato1(@Param("cod") String cod);
	
	@Select("SELECT tab1tip codice, tab1desc descrizione, tab1arc archiviato from tab1 where tab1cod= #{cod} and tab1tip != '1' order by tab1nord, tab1tip")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public List<TabellatoIstatEntry> getTabellato1Estero(@Param("cod") String cod);

	@Select("SELECT tab1tip codice, tab1desc descrizione, tab1arc archiviato from tab1 where tab1cod= #{cod} and tab1tip < 100 order by tab1nord, tab1tip")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public List<TabellatoIstatEntry> getTabellato1AG(@Param("cod") String cod);

	@Select("SELECT tab1tip codice, tab1desc descrizione, tab1arc archiviato from tab1 where tab1cod= #{cod} and tab1tip > 100 order by tab1nord, tab1tip")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public List<TabellatoIstatEntry> getTabellato1MA(@Param("cod") String cod);
	

	/**
	 * Estrae la riga da tab1 con tab1cod, tab1tip uguale al filtro .
	 * 
	 * @param cod    codice da filtrare nella tab1
	 * @param valore codice da filtrare nel tabellato
	 * @return elenco delle righe filtrate
	 */
	@Select("SELECT tab1tip codice, tab1desc descrizione, tab1arc archiviato from tab1 where tab1cod= #{cod} and tab1tip=#{valore}")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public TabellatoIstatEntry getValore1(@Param("cod") String cod, @Param("valore") Long valore);

	/**
	 * Estrae le righe da tab3 con tab3cod uguale al filtro .
	 * 
	 * @param cod codice da filtrare nella tab3
	 * @return elenco delle righe filtrate
	 */
	@Select("SELECT tab3tip codice, tab3desc descrizione, tab3arc archiviato from tab3 where tab3cod= #{cod} order by tab3nord, tab3tip")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public List<TabellatoIstatEntry> getTabellato3(@Param("cod") String cod);
	
	
	
	@Select("SELECT tab6tip codice, CONCAT (tab6tip,' - ', tab6desc) descrizione from tab6")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public List<TabellatoIstatEntry> getTabellato6();
	
	

	@SelectProvider(type = PureSqlProvider.class, method = "sql")
	public List<TabellatoIstatEntry> getTabsche(String sql);

	/**
	 * Estrae la riga da tabsche con tabcod, tabcod1 uguale al filtro .
	 * 
	 * @param cod    codice da filtrare nella tabsche
	 * @param valore codice da filtrare nel tabellato
	 * @return elenco delle righe filtrate
	 */
	@Select("SELECT tabcod1 codice, tabdesc descrizione, '0' archiviato from tabsche where tabcod= #{cod} and tabcod1=#{valore}")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public TabellatoIstatEntry getValoreTabsche1(@Param("cod") String cod, @Param("valore") String valore);

	/**
	 * Estrae la riga da tabsche con tabcod, tabcod2 uguale al filtro .
	 * 
	 * @param cod    codice da filtrare nella tabsche
	 * @param valore codice da filtrare nel tabellato
	 * @return elenco delle righe filtrate
	 */
	@Select("SELECT tabcod1 || tabcod2 codice, tabdesc descrizione, '0' archiviato from tabsche where tabcod= #{cod} and tabcod1 || tabcod2 =#{valore}")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public TabellatoIstatEntry getValoreCategoriaDM112011(@Param("cod") String cod, @Param("valore") String valore);

	/**
	 * Estrae la riga da tab3 con tab3cod, tab3tip uguale al filtro .
	 * 
	 * @param cod    codice da filtrare nella tab3
	 * @param valore codice da filtrare nel tabellato
	 * @return elenco delle righe filtrate
	 */
	@Select("SELECT tab3tip codice, tab3desc descrizione, tab3arc archiviato from tab3 where tab3cod= #{cod} and tab3tip=#{valore}")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public TabellatoIstatEntry getValore3(@Param("cod") String cod, @Param("valore") String valore);

	/**
	 * Estrae le righe da tab2 con tab2cod uguale al filtro .
	 * 
	 * @param cod codice da filtrare nella tab2
	 * @return elenco delle righe filtrate
	 */
	@Select("SELECT tab2tip codice, tab2d2 descrizione, tab2arc archiviato from tab2 where tab2cod= #{cod} order by tab2nord, tab2tip")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public List<TabellatoIstatEntry> getTabellato2(@Param("cod") String cod);

	@Select("SELECT tab2tip codice, tab2d2 descrizione, tab2arc archiviato from tab2 where tab2cod= #{cod} order by tab2nord, tab2tip")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public List<TabellatoIstatEntry> getTabellatoCategorie(@Param("cod") String cod);

	/**
	 * Estrae la riga da tab2 con tab2cod, tab2tip uguale al filtro .
	 * 
	 * @param cod    codice da filtrare nella tab2
	 * @param valore codice da filtrare nel tabellato
	 * @return elenco delle righe filtrate
	 */
	@Select("SELECT tab2tip codice, tab2d2 descrizione, tab2arc archiviato from tab2 where tab2cod= #{cod} and tab2tip=#{valore}")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public TabellatoIstatEntry getValore2(@Param("cod") String cod, @Param("valore") String valore);

	/**
	 * Estrae le righe da tab5 con tab5cod uguale al filtro .
	 * 
	 * @param cod codice da filtrare nella tab3
	 * @return elenco delle righe filtrate
	 */
	@Select("SELECT tab5tip codice, tab5desc descrizione, tab5arc archiviato from tab5 where tab5cod= #{cod} order by tab5nord")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public List<TabellatoIstatEntry> getTabellato5(@Param("cod") String cod);

	/**
	 * Estrae la riga da tab5 con tab5cod, tab5tip uguale al filtro .
	 * 
	 * @param cod    codice da filtrare nella tab5
	 * @param valore codice da filtrare nel tabellato
	 * @return elenco delle righe filtrate
	 */
	@Select("SELECT tab5tip codice, tab5desc descrizione, tab5arc archiviato from tab5 where tab5cod= #{cod} and tab5tip=#{valore}")
	@Results({ @Result(property = "codice", column = "codice"),
			@Result(property = "descrizione", column = "descrizione") })
	public TabellatoIstatEntry getValore5(@Param("cod") String cod, @Param("valore") String valore);

	@Insert("insert into tecni (CODTEC, NOMETEI, COGTEI, NOMTEC, CFTEC, cgentei, indtec, ncitec, protec, captec, loctec, cittec, teltec, faxtec, ematec )  VALUES (#{codice},#{nome},#{cognome},#{nominativo},#{cf},#{stazioneAppaltante},#{indirizzo},#{numCivico},#{provincia},#{cap},#{comune},#{codIstat},#{telefono},#{fax},#{email})")
	public void insertRUP(RupInsertForm form);

	@Update("update tecni set NOMETEI=#{nome}, COGTEI=#{cognome}, NOMTEC=#{nominativo}, CFTEC=#{cf}, cgentei=#{stazioneAppaltante}, indtec=#{indirizzo}, ncitec=#{numCivico}, protec=#{provincia}, captec=#{cap}, loctec=#{comune}, cittec=#{codIstat}, teltec=#{telefono}, faxtec=#{fax}, ematec = #{email} where codtec = #{codice}")
	public void updateRUP(RupInsertForm form);

	@Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE LOWER(nomtec) like '%'|| #{rup} ||'%' and cgentei = #{stazioneAppaltante}")
	public List<RupEntry> getRupOptionsWithSa(RupSearchForm form);
	
	@Select("select distinct(u.sysute) as nome, u.syscf as cf, u.syscon as syscon  from usrsys u, usr_ein ue  where u.syscon = ue.syscon and upper(u.sysute) like  '%'|| #{nome} ||'%' and ue.codein = #{stazioneAppaltante}")
	public List<UsrSysconEntry> getUtenteOptionsWithSa(@Param("nome") String nome, @Param("stazioneAppaltante") String stazioneAppaltante);

	@Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE LOWER(nomtec) like '%'|| #{rup} ||'%'")
	public List<RupEntry> getRupOptionsWithoutSa(RupSearchForm form);

	@Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE cgentei = #{stazioneAppaltante} and syscon = #{syscon}")
	public List<RupEntry> getSuggestedRup(SearchMainSARupForm form);

	@Select("SELECT coc_des, coc_mne_uni, c0c_fs, c0c_tab1 from c0campi where c0c_mne_ber = #{mnemonico}")
	public InfoC0ampi getInfoC0Campi(@Param("mnemonico") String mnemonico);

	@Select("select tab5desc from tab5 where tab5cod='G_j00' and tab5tip = #{schema}")
	public String getDescrizioneSchema(@Param("schema") String schema);

	@Select("select c0e_des from c0entit where c0e_nom = #{entita}")
	public String getDescrizioneEntita(@Param("entita") String entita);

	@Select("select c0c_mne_ber from c0campi where coc_mne_uni like #{entita} and c0c_chi='P'")
	public List<String> getChiaveEntita(@Param("entita") String entita);

	@Select("select count(1) from tab1")
	public Long health();

	@Select("select id as id, codein as stazioneAppaltante, denom as denominazione from uffici where codein = #{stazioneAppaltante} and UPPER(denom) like '%'|| #{desc} ||'%' ")
	public List<UffEntry> getUffOptions(UffSearchForm form);

	@Select("select id as id, codein as stazioneAppaltante, denom as denominazione from uffici where codein = #{stazioneAppaltante} and UPPER(denom) like '%'|| #{desc} ||'%' and ccprog = '1'")
	public List<UffEntry> getUffOptionsProg(UffSearchForm form);

	@Select("select max(id) from uffici")
	public Long getMaxIdUff();

	@Insert("insert into uffici(id, codein, denom, ccprog) values (#{id}, #{stazioneAppaltante}, #{denominazione}, #{centroCosto})")
	public void insertUfficio(UffInsertForm form);

	@Update("update uffici set denom = #{denominazione}, ccprog = #{centroCosto} where id = #{id} and codein = #{stazioneAppaltante}")
	public void updateUfficio(UffInsertForm form);

	@Select("SELECT id, codein as stazioneAppaltante, denom as denominazione, ccprog as centroCosto from uffici where id = #{id}")
	public UffEntry getUfficio(@Param("id") Long id);

	@Delete("DELETE FROM uffici WHERE id = #{id}")
	public void deleteUfficio(@Param("id") final Long id);

	public int countSearchUffici(UfficiSearchForm searchForm);

	public List<UffEntry> getUffici(UfficiSearchForm searchForm, RowBounds rowBounds);

	/**
	 * Estrae tutte le regioni.
	 *
	 * @return elenco regioni
	 */
	public List<TabellatoIstatEntry> getRegioni();

	/**
	 * Estrae tutte le province.
	 *
	 * @return elenco province
	 */
	@Select("select tabcod3 as codice, tabcod2 as codistat, tabdesc as descrizione from tabsche where tabcod='S2003' and tabcod1='07' and tabcod2 = #{istat}")
	public List<TabellatoIstatEntry> getProvinceIstat(@Param("istat") String istat);

	/**
	 * Estrae tutte le province comprendenti di coduice istat.
	 *
	 * @return elenco province
	 */
	@Select("select tabcod3 as codice, tabcod2 as codistat, tabdesc as descrizione from tabsche where tabcod='S2003' and tabcod1='07' order by tabdesc asc")
	public List<TabellatoIstatEntry> getProvince();

	@Select("select codistat as codice, codistat, descri as descrizione, cap, provincia as codiceProvincia from g_comuni")
	public List<TabellatoIstatEntry> getComuni();

	@Select("select codistat as codice, codistat, descri as descrizione, cap, provincia as codiceProvincia from g_comuni where UPPER(descri) like '%'|| #{search} ||'%' or UPPER(codistat) like '%'|| #{search} ||'%'")
	public List<TabellatoIstatEntry> getComuniOptions(@Param("search") String search);

	@Select("select codistat as codice, codistat, descri as descrizione, cap, provincia as codiceProvincia from g_comuni where UPPER(descri) = #{desc}")
	public TabellatoIstatEntry getComuneByDesc(@Param("desc") String desc);

	@Select("Select codice, paese, area, regione, provincia, descrizione from tabnuts where area is null and regione is null and provincia is null order by paese")
	public List<TabNuts> getPaesiNuts();

	@Select("Select codice, paese, area, regione, provincia, descrizione from tabnuts where area is not null and regione is null and provincia is null order by codice")
	public List<TabNuts> getAreeNuts();

	@Select("Select codice, paese, area, regione, provincia, descrizione from tabnuts where area is not null and regione is not null and provincia is null order by codice")
	public List<TabNuts> getRegioniNuts();

	@Select("Select codice, paese, area, regione, provincia, descrizione from tabnuts where area is not null and regione is not null and provincia is not null order by codice")
	public List<TabNuts> getProvinceNuts();

	@Select("select cpvcod4,cpvdesc from tabcpv where cpvcod='S2020' and cpvcod4 like #{codcpv} || '%'")
	@Results({ @Result(property = "codice", column = "cpvcod4"),
			@Result(property = "descrizione", column = "cpvdesc") })
	public TabellatoCPVEntry getDettaglio(@Param("codcpv") String codcpv);

	/**
	 * Estrae la lista di livello 0 per il codice CPV
	 *
	 * @return lista di livello 0 per il codice CPV
	 */
	@Select("Select CPVCOD0, CPVDESC from TABCPV Where CPVCOD1 = '00' and "
			+ "CPVCOD2 = '00' and CPVCOD3 = '00' and CPVCOD0<>'00'  and CPVCOD = 'S2020' " + "Order by CPVDESC")
	@Results({ @Result(property = "codice", column = "CPVCOD0"),
			@Result(property = "descrizione", column = "CPVDESC") })
	public List<TabellatoCPVEntry> getCod0();

	/**
	 * Estrae la lista di livello 1 per il codice CPV
	 *
	 * @param cod0 Codice livello 0 del cpv
	 * @return lista di livello 1 per il codice CPV
	 */
	@Select("Select CPVCOD1, CPVDESC from TABCPV "
			+ "Where CPVCOD2 = '00' and CPVCOD3 = '00' and CPVCOD1<>'00' and CPVCOD0=#{cod0} and CPVCOD = 'S2020' "
			+ "Order by CPVDESC")
	@Results({ @Result(property = "codice", column = "CPVCOD1"),
			@Result(property = "descrizione", column = "CPVDESC") })
	public List<TabellatoCPVEntry> getCod1(@Param("cod0") String cod0);

	/**
	 * Estrae la lista di livello 2 per il codice CPV
	 *
	 * @param cod0 Codice livello 0 del cpv
	 * @param cod1 Codice livello 1 del cpv
	 * @return lista di livello 2 per il codice CPV
	 */
	@Select("Select CPVCOD2, CPVDESC from TABCPV "
			+ "Where CPVCOD2 <> '00' and CPVCOD3 = '00' and CPVCOD1 = #{cod1} and CPVCOD0= #{cod0} and CPVCOD = 'S2020' "
			+ "Order by CPVDESC")
	@Results({ @Result(property = "codice", column = "CPVCOD2"),
			@Result(property = "descrizione", column = "CPVDESC") })
	public List<TabellatoCPVEntry> getCod2(@Param("cod0") String cod0, @Param("cod1") String cod1);

	/**
	 * Estrae la lista di livello 3 per il codice CPV
	 *
	 * @param cod0 Codice livello 0 del cpv
	 * @param cod1 Codice livello 1 del cpv
	 * @param cod2 Codice livello 2 del cpv
	 * @return lista di livello 3 per il codice CPV
	 */
	@Select("Select CPVCOD3, CPVDESC from TABCPV "
			+ "Where CPVCOD2 = #{cod2} and CPVCOD3 <> '00' and CPVCOD1 = #{cod1} and CPVCOD0= #{cod0} and CPVCOD = 'S2020' "
			+ "Order by CPVDESC")
	@Results({ @Result(property = "codice", column = "CPVCOD3"),
			@Result(property = "descrizione", column = "CPVDESC") })
	public List<TabellatoCPVEntry> getCod3(@Param("cod0") String cod0, @Param("cod1") String cod1,
			@Param("cod2") String cod2);

	@Select("Select CPVCOD0 as cod1, CPVCOD1 as cod2, CPVCOD2 as cod3, CPVCOD3 as cod4, CPVCOD4 as cod, CPVDESC as label from TABCPV where CPVCOD = 'S2020' Order by CPVDESC")
	public List<TabellatoCPVEntry> getAllCpv();

	public int countSearchTecnici(TecniciSearchForm searchForm);

	public List<RupEntry> getTecnici(TecniciSearchForm searchForm, RowBounds rowBounds);

	@Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE codtec = #{id}")
	public RupEntry getTecnico(@Param("id") String id);

	@Select("delete from tecni WHERE codtec = #{id}")
	public void deleteTecnico(@Param("id") String id);

	public int countSearchImprese(ImpreseSearchForm searchForm);

	public List<ImpresaBaseEntry> getImprese(ImpreseSearchForm searchForm, RowBounds rowBounds);

	@Select("select codimp as codiceImpresa, nomest as ragioneSociale, nomimp as nomImp, cfimp as codiceFiscale, pivimp as partitaIva, locimp as comune, natgiui as formaGiuridica, ncciaa as numeroIscrizione,\r\n"
			+ "	ninail as codiceInail, albtec as numeroIscrizioneAlbo, indimp as indirizzo, nciimp as numeroCivico, proimp as provincia, capimp as cap, nazimp as nazione, \r\n"
			+ "	telcel as telefono, emai2ip as email, emailpec as pec, faximp as fax  from IMPR where codimp = #{codiceImpresa}")
	public ImpresaEntry getImpresa(@Param("codiceImpresa") String codiceImpresa);

	@Select("select t.cogtim as cognome, t.nometim as nome, t.cftim as cf from teim t, impleg i where t.codtim = #{codiceImpresa} and t.codtim = i.codimp2")
	public LegaleRappresentanteEntry getLegaleImpresa(@Param("codiceImpresa") String codiceImpresa);

	@Select("delete from impr WHERE codimp = #{codiceImpresa}")
	public void deleteImpresa(@Param("codiceImpresa") String codiceImpresa);

	@Select("delete from teim WHERE codtim = #{codiceImpresa}")
	public void deleteTeim(@Param("codiceImpresa") String codiceImpresa);

	@Select("delete from impleg WHERE codimp2 = #{codiceImpresa}")
	public void deleteImpleg(@Param("codiceImpresa") String codiceImpresa);

	@Insert("insert into IMPR (codimp, nomest, nomimp, cfimp, pivimp, locimp, natgiui, ncciaa, ninail, albtec, indimp, nciimp, proimp, capimp, nazimp, telcel, emai2ip, emailpec, faximp, cgenimp) values (#{codiceImpresa},#{ragioneSociale}, #{nomImp}, #{codiceFiscale},#{partitaIva},#{comune},#{formaGiuridica},#{numeroIscrizione},#{codiceInail},#{numeroIscrizioneAlbo},#{indirizzo},#{numeroCivico},#{provincia},#{cap},#{nazione},#{telefono},#{email},#{pec},#{fax},#{stazioneAppaltante})")
	public void insertImpresa(ImpresaInsertForm insertForm);

	@Insert("insert into teim (codtim, nometim, cogtim, cftim) values (#{codiceImpresa},#{nome},#{cognome},#{cf})")
	public void insertTeim(@Param("codiceImpresa") String codiceImpresa, @Param("nome") String nome,
			@Param("cognome") String cognome, @Param("cf") String cf);

	@Insert("insert into impleg (id, codimp2, codleg, nomleg) values (#{id}, #{codiceImpresa},#{codiceImpresa},#{nominativo})")
	public void insertImpleg(@Param("id") Long id, @Param("codiceImpresa") String codiceImpresa,
			@Param("nominativo") String nominativo);

	@Update("update impr set nomest = #{ragioneSociale}, nomimp = #{nomImp}, cfimp = #{codiceFiscale}, pivimp = #{partitaIva}, locimp = #{comune}, natgiui = #{formaGiuridica}, ncciaa = #{numeroIscrizione}, ninail = #{codiceInail}, albtec = #{numeroIscrizioneAlbo}, indimp = #{indirizzo}, nciimp = #{numeroCivico}, proimp = #{provincia}, capimp = #{cap}, nazimp = #{nazione}, telcel = #{telefono}, emai2ip = #{email}, emailpec = #{pec}, faximp = #{fax} where codimp = #{codiceImpresa}")
	public void updateImpresa(ImpresaInsertForm insertForm);

	@Update("update teim set nometim = #{nome}, cogtim=#{cognome}, cftim=#{cf} WHERE codtim = #{codiceImpresa}")
	public void updateTeim(@Param("codiceImpresa") String codiceImpresa, @Param("nome") String nome,
			@Param("cognome") String cognome, @Param("cf") String cf);

	@Update("update impleg set nomleg = #{nominativo} where codimp2 = #{codiceImpresa}")
	public void updateImpleg(@Param("codiceImpresa") String codiceImpresa, @Param("nominativo") String nominativo);

	@Select("select c0e_nom_ex as tabella, coe_key_ex as colonna from c0entit where c0e_nom like #{entita} ||'%'")
	public List<EntitaCollegate> getEntitaCollegate(@Param("entita") final String entita);

	Long checkRelazioneString(@Param("table") String table, @Param("column") String column,
			@Param("codice") String codice);

	Long checkRelazioneLong(@Param("table") String table, @Param("column") String column,
							  @Param("codice") Long codice);

	@Select("select count(*) from tecni where cftec = #{cf} and cgentei =#{stazioneAppaltante} ")
	public Long countExistingCFNewWithSa(@Param("cf") String cf,
			@Param("stazioneAppaltante") String stazioneAppaltante);

	@Select("select count(*) from tecni where cftec = #{cf} ")
	public Long countExistingCFNewWithoutSa(@Param("cf") String cf);

	@Select("select count(*) from tecni where cftec = #{cf} and codtec <> #{codtec} and cgentei =#{stazioneAppaltante}")
	public Long countExistingCFEditWithSa(@Param("cf") String cf, @Param("codtec") String codtec,
			@Param("stazioneAppaltante") String stazioneAppaltante);

	@Select("select count(*) from tecni where cftec = #{cf} and codtec <> #{codtec}")
	public Long countExistingCFEditWithoutSa(@Param("cf") String cf, @Param("codtec") String codtec);

	@Select("select message_id as id, message_date as dataMessaggio, message_subject as oggetto, message_body as corpoMessaggio, message_sender_syscon as sysconSender, message_recipient_read as letto from w_message_in where message_recipient_syscon = #{syscon} order by message_date desc")
	public List<MessageEntry> getMessages(@Param("syscon") Long syscon);

	@Select("select message_id as id, message_date as dataMessaggio, message_subject as oggetto, message_body as corpoMessaggio from w_message_out where message_sender_syscon = #{syscon} order by message_date desc")
	public List<MessageEntry> getMessagesSent(@Param("syscon") Long syscon);

	@Select("select syscon, sysute as nominativo from usrsys where UPPER(sysute) like #{searchString}")
	public List<MessageReceiverEntry> getMessageReceiver(@Param("searchString") String searchString);

	@Insert("insert into w_message_out (message_id,message_date,message_subject,message_body,message_sender_syscon) values (#{id}, #{dataMessaggio}, #{oggetto}, #{corpoMessaggio},#{sysconSender})")
	public void insertMessageOut(MessageForm form);

	@Insert("insert into w_message_in (message_id,message_date,message_subject,message_body,message_sender_syscon,message_recipient_syscon,message_recipient_read) values (#{id}, #{dataMessaggio}, #{oggetto}, #{corpoMessaggio},#{sysconSender},#{sysconReceiver},0)")
	public void insertMessageIn(MessageInForm form);

	@Insert("insert into w_message_out_rec (message_id,recipient_id,recipient_syscon) values (#{id}, #{recipientId}, #{sysconReceiver})")
	public void insertMessageRec(MessageRecForm form);

	@Select("select max (message_id) from w_message_out")
	public Long getMaxMessageOutId();

	@Select("select max (message_id) from w_message_in")
	public Long getMaxMessageInId();

	@Select("select max (message_id) from w_message_out_rec")
	Long getMaxMessageOutRecId();

	@Select("select max (recipient_id) from w_message_out_rec")
	Long getMaxMessageOutRecRecipientId();

	@Select("select message_recipient_syscon from w_message_in where message_id = #{messageId}")
	Long getInMessageRecipientSysconByMessageId(@Param("messageId") final Long messageId);

	@Select("select message_sender_syscon from w_message_out where message_id = #{messageId}")
	Long getOutMessageSenderSysconByMessageId(@Param("messageId") final Long messageId);

	@Update("update w_message_in set message_recipient_read = #{letto} where message_id = #{messageId}")
	public void setMessageSeen(@Param("messageId") Long messageId, @Param("letto") Long letto);

	@Delete("delete from w_message_in where message_id = #{messageId}")
	public void deleteMessageIn(@Param("messageId") Long messageId);

	@Delete("delete from w_message_out where message_id = #{messageId}")
	public void deleteMessageOut(@Param("messageId") Long messageId);

	@Delete("delete from w_message_out_rec where message_id = #{messageId}")
	public void deleteAllMessageRec(@Param("messageId") Long messageId);

	public int countSearchCentriDiCosto(CentriDiCostoSearchform searchForm);

	public List<CentroDiCostoEntry> getCentriDiCosto(CentriDiCostoSearchform searchForm, RowBounds rowBounds);

	@Select("select idcentro as id, codein as stazioneAppaltante, codcentro as codiceCentro, denomcentro as denominazione, note as note, tipologia as tipologia from CENTRICOSTO where idcentro = #{idCentro}")
	public CentroDiCostoEntry getCentroDiCosto(@Param("idCentro") Long idCentro);

	@Select("select idcentro as id, codein as stazioneAppaltante, codcentro as codiceCentro, denomcentro as denominazione, note as note, tipologia as tipologia from CENTRICOSTO where LOWER(denomcentro) like '%'|| #{desc} ||'%' and codein = #{stazioneAppaltante}")
	public List<CentroDiCostoEntry> getCdcOptions(CentriDiCostoOptionsSearchForm form);

	@Delete("delete from CENTRICOSTO where idcentro = #{idCentro}")
	public void deleteCentroDiCosto(@Param("idCentro") Long idCentro);

	@Insert("insert into CENTRICOSTO (idcentro, codein, codcentro, denomcentro, note, tipologia) VALUES (#{id},#{stazioneAppaltante},#{codiceCentro},#{denominazione},#{note},#{tipologia})")
	public void insertCdc(CentroDiCostoInsertForm form);

	@Update("update CENTRICOSTO set codcentro = #{codiceCentro}, denomcentro = #{denominazione}, note = #{note}, tipologia = #{tipologia} where idcentro = #{id}")
	public void updateCdc(CentroDiCostoInsertForm form);

	@Select("select max(idcentro) from CENTRICOSTO")
	public Long getMaxIdCdc();

	@Select("select codimp as codiceImpresa, nomest as ragioneSociale, nomimp as nomImp, cfimp as codiceFiscale, pivimp as partitaIva, locimp as comune, natgiui as formaGiuridica, ncciaa as numeroIscrizione,\r\n"
			+ "	ninail as codiceInail, albtec as numeroIscrizioneAlbo, indimp as indirizzo, nciimp as numeroCivico, proimp as provincia, capimp as cap, nazimp as nazione, \r\n"
			+ "	telcel as telefono, emai2ip as email, emailpec as pec, faximp as fax  from IMPR where (LOWER(cfimp) like '%'|| #{desc} ||'%' or LOWER(nomest) like '%'|| #{desc} ||'%') and cgenimp = #{stazioneAppaltante}")
	public List<ImpresaEntry> getImpreseOptionsWithSa(SingolaImpresaSearchForm form);

	@Select("select codimp as codiceImpresa, nomest as ragioneSociale, nomimp as nomImp, cfimp as codiceFiscale, pivimp as partitaIva, locimp as comune, natgiui as formaGiuridica, ncciaa as numeroIscrizione,\r\n"
			+ "	ninail as codiceInail, albtec as numeroIscrizioneAlbo, indimp as indirizzo, nciimp as numeroCivico, proimp as provincia, capimp as cap, nazimp as nazione, \r\n"
			+ "	telcel as telefono, emai2ip as email, emailpec as pec, faximp as fax  from IMPR where LOWER(cfimp) like '%'|| #{desc} ||'%' or LOWER(nomest) like '%'|| #{desc} ||'%'")
	public List<ImpresaEntry> getImpreseOptionsWithoutSa(SingolaImpresaSearchForm form);

	@Select("select codein, nomein, cfein, viaein, nciein, proein, capein, citein, codcit, telein, faxein, emai2in from uffint where LOWER(cfein) like '%'|| #{desc} ||'%' or LOWER(nomein) like '%'|| #{desc} ||'%'")
	public List<SAEntry> getStazioniAppaltantiOptions(StazioneAppaltanteSearchForm form);

	@Select("select sysute as nome, syscf as cf, syscon as syscon from usrsys u where UPPER(u.sysute) like #{searchString} and syscon > 50 and sysdisab = '0' and exists (select syscon from usr_ein where codein = #{stazioneAppaltante} and syscon = u.syscon)")
	public List<UsrSysconEntry> getUserOptions(@Param("searchString") String searchString,
			@Param("stazioneAppaltante") String stazioneAppaltante);

	@Select("select exists(select uf.id from uffici uf inner join usr_ein ue on uf.codein = ue.codein \r\n"
			+ "inner join usrsys us on ue.syscon = us.syscon \r\n"
			+ "where us.syscon = #{syscon} and uf.id = #{idUfficio})")
	boolean hasPermissionOnUfficio(@Param("syscon") Long syscon, @Param("idUfficio") Long idUfficio);

	@Insert("insert into uffint (codein, nomein, cfein, tipoin, idammin, profco, codcit, viaein, nciein, capein, telein, faxein, emaiin, iscuc, cfanac, citein, proein, codausa) VALUES (#{codein},#{denominazione},#{codFisc},#{tipologia},#{idAmministrazione},#{urlProfiloC},#{codIstat},#{indirizzo},#{nCivico},#{cap},#{telefono},#{fax},#{email},#{isCuc},#{cfAnac},#{citta},#{provincia},#{codausa})")
	public void insertSa(StazioneAppaltanteInsertForm form);

	@Update("update uffint set nomein = #{denominazione}, cfein = #{codFisc}, tipoin=#{tipologia}, idammin = #{idAmministrazione}, profco =#{urlProfiloC}, codcit =#{codIstat}, viaein =#{indirizzo}, nciein =#{nCivico}, capein =#{cap}, telein =#{telefono}, faxein =#{fax}, emaiin =#{email}, iscuc =#{isCuc}, cfanac =#{cfAnac}, citein=#{citta}, proein=#{provincia}, codausa=#{codausa} where codein = #{codein}")
	public void updateSa(StazioneAppaltanteUpdateForm form);

	@Select("select syspwbou from usrsys where syscon = #{syscon}")
	String getUserPermission(@Param("syscon") Long syscon);

	@Select("select syspwbou from usrsys where syscon = #{syscon}")
	String getUserPermissionBySyscon(@Param("syscon") final Long syscon);

	public int countSearchStazioniAppaltanti(ArchivioSaSearchform searchForm);

	public List<StazioneAppaltanteListaEntry> getStazioniAppaltanti(ArchivioSaSearchform searchForm,
			RowBounds rowBounds);

	@Delete("delete from uffint where codein = #{codein}")
	public void deleteStazioneAppaltante(@Param("codein") String codein);

	@Delete("delete from USR_EIN where codein = #{codein}")
	public void deleteUserEinSA(@Param("codein") String codein);

	@Delete("delete from UFFICI where codein = #{codein}")
	public void deleteUfficiSA(@Param("codein") String codein);

	@Delete("delete from CENTRICOSTO where codein = #{codein}")
	public void deleteCentroDiCostoSA(@Param("codein") String codein);

	@Select("select codein as codice, nomein as denominazione, cfein as codFisc, tipoin as tipologia , idammin as idAmministrazione, profco as urlProfiloC , codcit as codIstat, viaein as indirizzo, nciein as nCivico, capein as cap, telein as telefono, faxein as fax, emaiin as email, iscuc as isCuc, cfanac as cfAnac, citein as citta, proein as provincia, codausa  from uffint where codein = #{codein}")
	public StazioneAppaltanteEntry getStazioneAppaltante(@Param("codein") String codein);

	@Select("select sum(presenze) as somma_presenze " 
			+ "from( "
			+ "	select count(1) as presenze from PIATRI where cenint=#{codein} " 
			+ "	union "
			+ "	select count(1) as presenze from FABBISOGNI where codein=#{codein} " 
			+ "	union "
			+ "	select count(1) as presenze from AVVISO where codein=#{codein} " 
			+ "	union "
			+ "	select count(1) as presenze from W9GARA  where codein=#{codein} " 
			+ "	union "
			+ "	select count(1) as presenze from W3GARA where codein=#{codein} " 
			+ "	union "
			+ "	select count(1) as presenze from TECNI where cgentei=#{codein} " 
			+ "	union "
			+ "	select count(1) as presenze from IMPR where cgenimp=#{codein}"
			+ ") somma_presenza")
	public Long countOccorenzeSA(@Param("codein") String codein);

	@Select("select codein, nomein as denominazione, cfein as codFisc from uffint where cfein = #{codiceFiscale}")
	public StazioneAppaltanteBaseEntry getSaByCodiceFiscale(@Param("codiceFiscale") String codiceFiscale);

	@Select("select count(codimp) from impr where cfimp = #{codiceFiscale} and cgenimp = #{stazioneAppaltante}")
	public Long countImprCfOccurenciesWithSa(@Param("codiceFiscale") String codiceFiscale,
			@Param("stazioneAppaltante") String stazioneAppaltante);

	@Select("select count(codimp) from impr where cfimp = #{codiceFiscale} ")
	public Long countImprCfOccurenciesWithoutSa(@Param("codiceFiscale") String codiceFiscale);

	@Select("select count(codimp) from impr where pivimp = #{partitaIva} and cgenimp = #{stazioneAppaltante}")
	public Long countImprPIVAOccurenciesWithSa(@Param("partitaIva") String partitaIva,
			@Param("stazioneAppaltante") String stazioneAppaltante);

	@Select("select count(codimp) from impr where pivimp = #{partitaIva} ")
	public Long countImprPIVAOccurenciesWithoutSa(@Param("partitaIva") String partitaIva);

	@Select("select count(codimp) from impr where cfimp = #{codiceFiscale} and cgenimp = #{stazioneAppaltante} and codimp <> #{codice}")
	public Long countImprCfOccurenciesEditWithSa(@Param("codiceFiscale") String codiceFiscale,
			@Param("stazioneAppaltante") String stazioneAppaltante, @Param("codice") String codice);

	@Select("select count(codimp) from impr where cfimp = #{codiceFiscale} and codimp <> #{codice}")
	public Long countImprCfOccurenciesEditWithoutSa(@Param("codiceFiscale") String codiceFiscale,
			@Param("codice") String codice);

	@Select("select count(codimp) from impr where pivimp = #{partitaIva} and cgenimp = #{stazioneAppaltante} and codimp <> #{codice}")
	public Long countImprPIVAOccurenciesEditWithSa(@Param("partitaIva") String partitaIva,
			@Param("stazioneAppaltante") String stazioneAppaltante, @Param("codice") String codice);

	@Select("select count(codimp) from impr where pivimp = #{partitaIva} and codimp <> #{codice}")
	public Long countImprPIVAOccurenciesEditWithoutSa(@Param("partitaIva") String partitaIva,
			@Param("codice") String codice);

	@Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE LOWER(nomtec) like #{searchStringLike} and cgentei = #{stazioneAppaltante}")
	public List<RupEntry> getListaTecniciNonPaginataWithSA(TecniciNonPaginatiSearchForm form);

	@Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE LOWER(nomtec) like #{searchStringLike}")
	public List<RupEntry> getListaTecniciNonPaginataWithoutSA(TecniciNonPaginatiSearchForm form);

	@Select("select codimp as codiceImpresa, nomest as ragioneSociale, nomimp as nomImp, cfimp as codiceFiscale, pivimp as partitaIva, locimp as comune from IMPR where (LOWER(cfimp) like #{searchStringLike} or LOWER(nomest) like #{searchStringLike}) and cgenimp = #{stazioneAppaltante}")
	public List<ImpresaBaseEntry> getListaImpreseNonPaginataWithSa(ImpreseNonPaginateSearchForm form);

	@Select("select codimp as codiceImpresa, nomest as ragioneSociale, nomimp as nomImp, cfimp as codiceFiscale, pivimp as partitaIva, locimp as comune from IMPR where LOWER(cfimp) like #{searchStringLike} or LOWER(nomest) like #{searchStringLike}")
	public List<ImpresaBaseEntry> getListaImpreseNonPaginataWithoutSa(ImpreseNonPaginateSearchForm form);

	@Select("select idcentro as id, codein as stazioneAppaltante, codcentro as codiceCentro, denomcentro as denominazione, note as note, tipologia as tipologia from CENTRICOSTO where LOWER(denomcentro) like #{searchStringLike} and codein = #{stazioneAppaltante}")
	public List<CentroDiCostoEntry> getListaCdcNonPaginata(CdcNonPaginatiSearchForm form);
	
	@Select("select idcentro as id, codein as stazioneAppaltante, codcentro as codiceCentro, denomcentro as denominazione, note as note, tipologia as tipologia from CENTRICOSTO where LOWER(denomcentro) like #{searchStringLike}")
	public List<CentroDiCostoEntry> getListaCdcNonPaginataWithoutSA(CdcNonPaginatiSearchForm form);

	@Select("select id as id, codein as stazioneAppaltante, denom as denominazione from uffici where codein = #{stazioneAppaltante} and LOWER(denom) like #{searchStringLike}")
	public List<UffEntry> getListaUfficiNonPaginata(UfficiNonPaginatiSearchForm form);

	@Select("select count(cfein) from uffint where cfein = #{codiceFiscale} ")
	public Long countUffintCfOccurencies(@Param("codiceFiscale") String codiceFiscale);
	
	@Select("select nomein from uffint where codein = #{stazioneappaltante}")
	public String getNominativoSa(@Param("stazioneappaltante") String stazioneappaltante);

	@Select("select syscon from usrsys where syslogin = #{cf}")
	Long getSysconFromLogin(@Param("cf") final String cf);

	@Select("select count(*) from w_message_in where message_recipient_syscon = #{syscon} and message_recipient_read = 0")
	Integer getCountUnreadMessages(@Param("syscon") Long syscon);
	
	@Select("select count(*) from w9gara where idcc = #{idcc}")
	Integer getCountCdc(@Param("idcc") Long idcc);

	@Select("select message_id as id, message_date as dataMessaggio, message_subject as oggetto, message_body as corpoMessaggio, message_sender_syscon as sysconSender, message_recipient_syscon as sysconRecipient, message_recipient_read as letto from w_message_in where message_id = #{messageId}")
	MessageEntry getMessage(@Param("messageId") Long messageId);
	
	@Select("select cogtim as cognome, nometim as nome, cftim as cf from TEIM where codtim = #{codImpresa}")
	public LegaleRappresentanteEntry getLegaleRappresentante(@Param("codImpresa") String codImpresa);

	@Select("SELECT DISTINCT COUNT(*) FROM w_ricerche w LEFT JOIN w_ricpro p ON w.id_ricerca = p.id_ricerca LEFT JOIN w_ricuffint ui ON w.id_ricerca = ui.id_ricerca " +
			"WHERE w.codapp = #{codApp} AND w.disp = 1 AND (w.personale <> 1 OR w.owner = #{syscon}) AND (coalesce(w.tutti_profili,1) = 1 OR p.cod_profilo = #{codProfilo}) " +
			"AND (coalesce(w.tutti_uffici,1) = 1 OR ui.codein = #{uffInt})")
	public Long getCountListaReportPredefiniti(@Param("codApp") String codApp, @Param("syscon") Long syscon, @Param("codProfilo") String codProfilo, @Param("uffInt") String uffInt);

	public List<String> getCigOptions(@Param("cigLike") String cigLike, @Param("stazioneAppaltante") String stazioneAppaltante, @Param("cfTecnico") String cfTecnico, @Param("cfNull") Boolean cfNull, @Param("syscon") Long syscon);

	@Select("select sysab3 from usrsys where syscon = #{syscon}")
	public String getRuolo(@Param("syscon") Long syscon);
}
