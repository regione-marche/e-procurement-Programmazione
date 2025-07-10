package it.appaltiecontratti.monitoraggiocontratti.contratti.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.session.RowBounds;

import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DelegaBaseEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.DelegaEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.GaraEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.DelegaInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.forms.DelegaSearchForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.mapper.ContrattiMapper.PureSqlProvider;

@Mapper
public interface DelegheMapper {

	@SelectProvider(type = PureSqlProvider.class, method = "sql")
	public Integer execute(String query);

	public int countSearchDeleghe(DelegaSearchForm searchForm);

	public List<DelegaBaseEntry> searchDeleghe(DelegaSearchForm searchForm, RowBounds rowBounds);

	public List<DelegaBaseEntry> getDelegheByCfRup(@Param("stazioneAppaltante") String stazioneAppaltante, @Param("cfRup") String cfRup);

	public int getMaxId();

	public DelegaEntry getDelega(@Param("id") Integer id);

	public void insertDelega(DelegaInsertForm form);

	public void updateDelega(DelegaInsertForm form);

	public void deleteDelega(@Param("id") Integer id);
	
	@Select("select count(*) from w9deleghe where cfrup = #{cfrup} and codein = #{codein} and id_collaboratore = #{syscon}")
	public Long getExistsDelegaByCfrupCodeinSyscon(@Param("cfrup") String cfrup, @Param("codein") String codein, @Param("syscon") Long syscon);
	
	@Select("select id,id_collaboratore as idCollaboratore, ruolo, cfrup from w9deleghe where cfrup = #{cfrup} and codein = #{codein} and id_collaboratore = #{syscon}")
	public DelegaEntry getDelegaByCfrupCodeinSyscon(@Param("cfrup") String cfrup, @Param("codein") String codein, @Param("syscon") Long syscon);

}