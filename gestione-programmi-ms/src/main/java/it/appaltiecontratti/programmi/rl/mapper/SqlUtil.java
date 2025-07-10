package it.appaltiecontratti.programmi.rl.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface SqlUtil {


    @Select("select g_comuni.codcat from uffint inner join g_comuni on uffint.codcit=g_comuni.codistat where uffint.cfein=#{codFisc}")
    public String getCodiceCatastaleFromCodiceFiscaleSA(@Param("codFisc") String codFisc);

    @Select("select codcit,emaiin from uffint where uffint.codein=#{codEin}")
    Map<String,Object> getInfoFromCodiceSA(@Param("codEin") String codEin);

    @Select("select * from g_comuni WHERE codistat=#{codIstat}")
    Map<String,Object> getComuneFromCodiceIstat(@Param("codIstat") String codIstat);

    @Select("select g_comuni.codcat from g_comuni WHERE codistat=#{codIstat}")
    public String getCodiceCatastaleComuneFromCodiceIstat(@Param("codIstat") String codIstat);

}
