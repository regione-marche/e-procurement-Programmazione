package it.appaltiecontratti.inbox.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import it.appaltiecontratti.inbox.entity.SAEntry;

@Mapper
public interface StazioniAppaltantiMapper {
	@Select("select codein as codice, nomein as nome, cfein as codFiscale, viaein as indirizzo, nciein as numCivico, proein as provincia, capein as cap, citein as comune, codcit as codistat, telein as telefono, faxein as fax, emai2in as email, iscuc as iscuc, emailpec as pec, cfanac as cfAnac from uffint where codein = #{codein}")
	public SAEntry getSAInfo(@Param("codein") String codein);
}
