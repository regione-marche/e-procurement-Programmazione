package it.appaltiecontratti.monitoraggiocontratti.avvisi.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.session.RowBounds;

import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisiNonPaginatiSearchForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoBaseForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoDocForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoPubblicatoForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoSearchForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.AvvisoUpdateForm;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.DocAvvisoEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.FlussiAvviso;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.FlussoEntry;
import it.appaltiecontratti.monitoraggiocontratti.avvisi.entity.RupEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.UffEntry;

/**
 * DAO Interface per l'estrazione delle informazioni relative alla gestione di
 * avvisi
 * 
 * @author Michele.DiNapoli
 */
@Mapper
public interface AvvisiMapper {

	@Insert("INSERT INTO avviso(codein, idavviso, codsistema, tipoavv, dataavv, descri, cig, datascadenza, cup, cuiint, rup, syscon, comsede,prosede,indsede, idufficio) VALUES (#{stazioneAppaltante},#{numeroAvviso},1,#{tipologia},#{dataAvvisoToInsert},#{descrizione},#{cig},#{dataScadenzaToInsert},#{cup},#{cui},#{rupCod},#{syscon},#{comune},#{provincia},#{indirizzo},#{idUfficio})")
	public void insertAvviso(AvvisoInsertForm form);

	@Update("UPDATE avviso SET codein=#{stazioneAppaltante}, idavviso=#{numeroAvviso}, tipoavv=#{tipologia}, dataavv=#{dataAvvisoToInsert}, descri=#{descrizione}, cig=#{cig},datascadenza=#{dataScadenzaToInsert},cup=#{cup},cuiint=#{cui}, rup=#{rupCod}, syscon=#{syscon}, comsede=#{comune}, prosede=#{provincia}, indsede = #{indirizzo}, idufficio=#{idUfficio}  where CODEIN=#{stazioneAppaltante} and idavviso=#{numeroAvviso}")
	public void updateAvviso(AvvisoUpdateForm form);

	@Update("UPDATE avviso SET id_ricevuto= #{idRicevuto} where CODEIN=#{stazioneAppaltante} and idavviso=#{numeroAvviso}")
	public void updateIdGenerato(AvvisoPubblicatoForm form);

	@Insert("INSERT INTO w9docavviso(codein, idavviso, codsistema, numdoc, titolo, file_allegato, url, tipo_file) VALUES (#{stazioneAppaltante},#{numeroAvviso},1,#{numdoc},#{titolo},#{file_allegato},#{url},#{tipoFile})")
	public void insertDocAvviso(AvvisoDocForm form);

	@Select("SELECT MAX(idAvviso) from avviso WHERE codein = #{stazioneAppaltante} and codsistema = #{codSistema}")
	public Integer getIdAvviso(AvvisoInsertForm form);

	@Select("SELECT max(numdoc) from w9docavviso WHERE codein = #{stazioneAppaltante} and idavviso = #{numeroAvviso} and codsistema = #{codSistema}")
	public Integer getIdDocAvviso(AvvisoInsertForm form);

	@Select("SELECT coalesce(max(numdoc), 0) from w9docavviso WHERE codein = #{stazioneAppaltante} and idavviso = #{numeroAvviso} and codsistema = #{codSistema}")
	public Integer getIdDocAvvisoUpdate(AvvisoUpdateForm form);

	public List<AvvisoEntry> getAvvisi(AvvisoSearchForm form, RowBounds rowBounds);

	public int countSearchAvvisi(AvvisoSearchForm form);

	@Select("SELECT codein as stazioneAppaltante, idavviso as numeroAvviso, codsistema as codSistema, tipoavv as tipoAvviso, dataavv as dataAvviso, descri as descrizione, cig, datascadenza as dataScadenza, cup, cuiint as cui, rup, id_generato as idGenerato, syscon, prosede as provincia, comsede as comune, indsede as indirizzo, idufficio as idUfficio, id_ricevuto as idRicevuto from avviso WHERE codein = #{stazioneAppaltante} and idavviso = #{numeroAvviso}")
	public AvvisoEntry getAvviso(AvvisoBaseForm form);

	@Select("SELECT codein, idavviso, codsistema, numdoc, titolo, file_allegato, url, tipo_file as tipoFile from w9docavviso WHERE codein = #{stazioneAppaltante} and idavviso = #{numeroAvviso}")
	public List<DocAvvisoEntry> getdocAvviso(AvvisoBaseForm form);

//	@Select("SELECT codein, idavviso, codsistema, numdoc, titolo from w9docavviso WHERE codein = #{stazioneAppaltante} and idavviso = #{numeroAvviso}" )
//	public List<DocAvvisoEntry> getdocAvvisoForCheck(AvvisoInsertForm form);

	@Delete("DELETE from w9docavviso WHERE codein = #{stazioneAppaltante} and idavviso = #{numeroAvviso}")
	public void deleteAvvisoDocs(AvvisoBaseForm form);

	@Delete("DELETE from w9docavviso WHERE codein = #{stazioneAppaltante} and idavviso = #{idAvviso} and numdoc = #{numeroDoc}")
	public void deleteAvvisoDoc(@Param("stazioneAppaltante") String stazioneAppaltante,
			@Param("idAvviso") Integer idAvviso, @Param("numeroDoc") Integer numeroDoc);

	@Delete("DELETE from avviso WHERE codein = #{stazioneAppaltante} and idavviso = #{numeroAvviso}")
	public void deleteAvviso(AvvisoBaseForm form);

	@Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE codtec =#{codiceRup}")
	public RupEntry getRupBycod(@Param("codiceRup") String codiceRup);

	@Insert("INSERT into W9FLUSSI(IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, CODCOMP, CFSA, AUTORE, XML)"
			+ " VALUES(#{id}, #{area}, #{key01}, #{key02}, #{key03}, #{key04}, #{tipoInvio}, #{dataInvio}, #{note}, #{idAutore}, #{codiceFiscaleSA}, #{autore}, #{json})")
	public void insertFlusso(FlussoEntry flusso);
	
	@Delete("delete from W9FLUSSI where key01 = #{idAvviso} and area = 3 and key02 = 1 and key03 = 989")
	public void deleteFlussoAvviso(@Param("idAvviso") Long idAvviso);

	@Select("select key01 as numeroAvviso, autore as autore, noteinvio as noteInvio, DATINV as dataInvio, CFSA as stazioneAppaltante, xml from w9flussi where CFSA= #{stazioneAppaltante} and area = 3 and key01 = #{numeroAvviso} order by idflusso asc")
	public List<FlussiAvviso> getPubblicazioni(AvvisoBaseForm form);

	@Select("select sysab3 from usrsys where syscon = #{syscon}")
	public String getRuolo(@Param("syscon") Long syscon);

	@Select("select codtec from tecni where syscon = #{syscon} and cgentei = #{stazioneappaltante}")
	public String getCodiceForSA(@Param("syscon") Long syscon, @Param("stazioneappaltante") String stazioneappaltante);

	@Select("select count(1) from avviso")
	public Long health();

	@Update("update avviso set syscon = #{syscon} where codein = #{stazioneAppaltante} and idavviso = #{idAvviso}")
	public void updateSysconAvviso(@Param("syscon") Long syscon, @Param("stazioneAppaltante") String stazioneAppaltante,
			@Param("idAvviso") String idAvviso);

	@Select("SELECT codein, idavviso, codsistema, numdoc, titolo, url, tipo_file as tipoFile from w9docavviso WHERE codein = #{codiceStazioneAppaltante} and idavviso = #{idAvviso}")
	public List<DocAvvisoEntry> getAvvisoDocumentsWithoutAllegato(@Param("idAvviso") final Integer idAvviso,
			@Param("codiceStazioneAppaltante") final String codiceStazioneAppaltante);

	@Select("SELECT codein, idavviso, codsistema, numdoc, titolo, file_allegato, url, tipo_file as tipoFile from w9docavviso WHERE codein = #{codiceStazioneAppaltante} and idavviso = #{idAvviso} and numdoc = #{numDoc}")
	public DocAvvisoEntry getAvvisoDocument(@Param("idAvviso") final Integer idAvviso,
			@Param("codiceStazioneAppaltante") final String codiceStazioneAppaltante,
			@Param("numDoc") final Integer numDoc);
	
	@Select("select codein from uffint where cfein = #{cfein}")
	public String getCodeinFromCodiceFiscaleSA(@Param("cfein") final String cfein);
	
	public List<AvvisoEntry> getListaAvvisiNonPaginata(final AvvisiNonPaginatiSearchForm searchForm);
	
	@Select("select nomein from uffint where codein = #{stazioneappaltante}")
	public String getNominativoSa(@Param("stazioneappaltante") String stazioneappaltante);

	@Select("select syscon from usrsys where syslogin = #{cf}")
	public long getSysconFromCf(@Param("cf") String cf);
	
	@Select("select cfein from uffint where codein =#{stazioneappaltante}")
	public String getCFSAByCode(@Param("stazioneappaltante") String stazioneappaltante);

	@Select("select id, codein as stazioneAppaltante, denom as denominazione from uffici where id = #{idUfficio}")
	public UffEntry getUfficio(@Param("idUfficio") Long idUfficio);

}
