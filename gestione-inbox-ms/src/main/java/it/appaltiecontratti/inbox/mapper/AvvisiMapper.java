package it.appaltiecontratti.inbox.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import it.appaltiecontratti.inbox.entity.avvisi.AvvisoEntry;
import it.appaltiecontratti.inbox.entity.avvisi.DocAvvisoEntry;
import it.appaltiecontratti.inbox.entity.contratti.RupEntry;

@Mapper
public interface AvvisiMapper {

	@Select("SELECT codein as stazioneAppaltante, idavviso as numeroAvviso, codsistema as codSistema, tipoavv as tipoAvviso, dataavv as dataAvviso, descri as descrizione, cig, datascadenza as dataScadenza, cup, cuiint as cui, rup, id_ricevuto as idRicevuto from avviso WHERE codein = #{stazioneAppaltante} and idavviso = #{numeroAvviso}" )
	public AvvisoEntry getAvviso(@Param("numeroAvviso") Long numeroAvviso, @Param("stazioneAppaltante") String stazioneAppaltante);

	@Select("SELECT codein, idavviso, codsistema, numdoc, titolo, file_allegato, url from w9docavviso WHERE codein = #{stazioneAppaltante} and idavviso = #{numeroAvviso}" )
	public List<DocAvvisoEntry> getdocAvviso(@Param("numeroAvviso") Long numeroAvviso, @Param("stazioneAppaltante") String stazioneAppaltante);

	@Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE codtec = #{id}")
	public RupEntry getTecnico(@Param("id") String id);
	
	@Update("UPDATE AVVISO SET ID_RICEVUTO = #{idRicevuto} WHERE CODEIN = #{stazioneAppaltante} AND IDAVVISO = #{numeroAvviso}" )
	public void updateIdRicevutoAvviso(@Param("idRicevuto") Long idRicevuto, @Param("stazioneAppaltante") String stazioneAppaltante, @Param("numeroAvviso") Long numeroAvviso);
	
	@Select("select cfein from uffint where codein =#{stazioneappaltante}")
	public String getCFSAByCode(@Param("stazioneappaltante") String stazioneappaltante);
}
