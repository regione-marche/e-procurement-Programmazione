package it.appaltiecontratti.inbox.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import it.appaltiecontratti.inbox.entity.OutboxEntry;

@Mapper
public interface SCPMapper {
	
	@Select("select valore from w_config where codapp = 'W9' and chiave= #{chiave}")
	public String getConfigurazione(@Param("chiave") String chiave);
	
	@Select("SELECT idcomun, area, key01, key02, key03, key04, cfsa FROM W9OUTBOX WHERE STATO = 1 ORDER BY IDCOMUN")
	public List<OutboxEntry> getDatiDaEsportareSCP();
	
	@Select("SELECT CODEIN FROM UFFINT WHERE CFEIN = #{cfSa}")
	public String getCodeinByCfSa(@Param("cfSa") String cfSa);
	
	@Update("UPDATE W9OUTBOX SET DATINV = #{dataInvio}, FILE_JSON =  #{json}, STATO = #{stato}, MSG = #{msg}, ID_RICEVUTO = #{idRicevuto} WHERE IDCOMUN = #{idcomun}")
	public void updateOutbox(@Param("dataInvio") Date dataInvio, @Param("json") String json, @Param("stato") Long stato, @Param("msg") String msg, @Param("idRicevuto") Long idRicevuto, @Param("idcomun") Long idComun);

	@Select("select cron_expression from w_quartz where codapp = #{codapp} and bean_id = #{beanId}")
    public String getCronExpression(@Param("codapp")String codapp, @Param("beanId")String beanId);
}
