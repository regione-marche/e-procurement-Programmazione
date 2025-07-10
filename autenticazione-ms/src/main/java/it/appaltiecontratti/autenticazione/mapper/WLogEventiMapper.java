package it.appaltiecontratti.autenticazione.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import it.appaltiecontratti.autenticazione.entity.WLogEventi;

/**
 * 
 * @author Cristiano Perin
 *
 */
@Mapper
public interface WLogEventiMapper {

	@Insert("INSERT INTO w_logeventi(idevento, codapp, cod_profilo, syscon, ipevento, dataora, livevento, codevento, descr, errmsg) VALUES (#{idEvento}, #{codApp, jdbcType=VARCHAR}, #{codProfilo, jdbcType=VARCHAR}, #{syscon}, #{ipEvento, jdbcType=VARCHAR}, #{dataOra}, #{livelloEvento}, #{codiceEvento, jdbcType=VARCHAR}, #{descrizione, jdbcType=VARCHAR}, #{errorMessage, jdbcType=VARCHAR})")
	void insertLogEvento(WLogEventi wLogEventiDTO);
}
