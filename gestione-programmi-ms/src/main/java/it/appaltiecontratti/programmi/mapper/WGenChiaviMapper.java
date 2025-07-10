package it.appaltiecontratti.programmi.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.appaltiecontratti.programmi.entity.WGenChiavi;

@Mapper
@Transactional
public interface WGenChiaviMapper {

	@Transactional(propagation = Propagation.MANDATORY)
	@Select("SELECT tabella, chiave FROM w_genchiavi WHERE tabella = #{id}")
	WGenChiavi findById(@Param("id") String id);

	@Transactional(propagation = Propagation.MANDATORY)
	@Select("SELECT tabella, chiave FROM w_genchiavi WHERE UPPER(tabella) = UPPER(#{id})")
	WGenChiavi findByTabellaIgnoreCase(@Param("id") String id);

	@Transactional(propagation = Propagation.MANDATORY)
	@Update("UPDATE w_genchiavi SET chiave = #{chiaveIncrementata} WHERE UPPER(tabella) = UPPER(#{tabella})")
	void incrementChiavePerTabella(@Param("chiaveIncrementata") long chiaveIncrementata, @Param("tabella") String tabella);
}
