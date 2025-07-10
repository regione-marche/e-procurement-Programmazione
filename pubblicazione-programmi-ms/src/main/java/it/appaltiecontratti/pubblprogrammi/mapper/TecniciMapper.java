package it.appaltiecontratti.pubblprogrammi.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import it.appaltiecontratti.pubblprogrammi.entity.DatiGeneraliTecnicoEntry;

/**
 * DAO Interface per l'estrazione delle informazioni relative ai soggetti
 * mediante MyBatis.
 * 
 * @author Michele.DiNapoli
 */
@Mapper
public interface TecniciMapper {

	@Insert("INSERT INTO TECNI (CODTEC,COGTEI,NOMETEI,NOMTEC,INDTEC,NCITEC,LOCTEC,PROTEC,CAPTEC,TELTEC,FAXTEC,CFTEC,EMATEC, CGENTEI) VALUES (#{codice},#{cognome},#{nome},#{nomeCognome},#{indirizzo},#{civico},#{localita},#{provincia},#{cap},#{telefono},#{fax},#{cfPiva},#{mail},#{codiceSA})")
	public void insertTecnico(DatiGeneraliTecnicoEntry tecnico);

}
