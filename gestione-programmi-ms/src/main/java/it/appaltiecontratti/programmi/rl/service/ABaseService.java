package it.appaltiecontratti.programmi.rl.service;

import it.appaltiecontratti.programmi.rl.mapper.SqlUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;
import java.util.Map;

public abstract class ABaseService {

    final SqlUtil sqlUtil;

    private static final Logger logger = LogManager.getLogger(ABaseService.class);

    protected ABaseService(SqlUtil sqlUtil) {
        this.sqlUtil = sqlUtil;
    }

    Triple<String,String,String> getInfoFromIstat(String codiceIstat){
        String codIstatRegione=null,siglaProvincia=null,cdCatastale=null;
        if(StringUtils.hasText(codiceIstat)) {
            try {
                codIstatRegione = codiceIstat.substring(1, 3) + "0";
                Map<String, Object> result = sqlUtil.getComuneFromCodiceIstat(codiceIstat);
                if (result!=null && !result.isEmpty()) {
                    siglaProvincia = result.get("provincia").toString();
                    cdCatastale = result.get("codcat").toString();
                }
            }catch (Exception e) {
                logger.error("Errore: ", e);
            }
        }
        return Triple.of(codIstatRegione,siglaProvincia,cdCatastale);
    }

    Pair<Integer,Integer> getInfoFromCategoria(String categoria){
        logger.debug(">>> categoria: " + categoria);
        Integer settore=null,sottoSettore=null;
        if(StringUtils.hasText(categoria)) {
            try {
                String[] categoriaSplit = categoria.split("\\.");
                settore=Integer.valueOf(categoriaSplit[0]);
                //sottoSettore=Integer.valueOf(categoriaSplit[1]);
                Map<String, Integer> idMapSottoSettori = Map.<String, Integer>ofEntries(
                        Map.entry("01.01", 12),
                        Map.entry("01.02", 13),
                        Map.entry("01.03", 14),
                        Map.entry("01.04", 15),
                        Map.entry("01.05", 16),
                        Map.entry("01.06", 17),
                        Map.entry("02.05", 18),
                        Map.entry("02.10", 19),
                        Map.entry("02.11", 20),
                        Map.entry("02.12", 21),
                        Map.entry("02.15", 22),
                        Map.entry("03.06", 23),
                        Map.entry("03.16", 24),
                        Map.entry("04.39", 25),
                        Map.entry("05.08", 26),
                        Map.entry("05.10", 27),
                        Map.entry("05.11", 28),
                        Map.entry("05.12", 29),
                        Map.entry("05.30", 30),
                        Map.entry("05.31", 31),
                        Map.entry("05.32", 32),
                        Map.entry("05.33", 33),
                        Map.entry("05.34", 34),
                        Map.entry("05.36", 35),
                        Map.entry("05.99", 36),
                        Map.entry("06.02", 37),
                        Map.entry("06.13", 38),
                        Map.entry("06.14", 39),
                        Map.entry("06.39", 40),
                        Map.entry("06.40", 41),
                        Map.entry("06.41", 42),
                        Map.entry("06.42", 43),
                        Map.entry("06.43", 44),
                        Map.entry("07.17", 45),
                        Map.entry("07.18", 46),
                        Map.entry("08.60", 47),
                        Map.entry("08.61", 48),
                        Map.entry("08.62", 49),
                        Map.entry("09.20", 50),
                        Map.entry("09.21", 51),
                        Map.entry("09.22", 52),
                        Map.entry("09.23", 53),
                        Map.entry("09.25", 54),
                        Map.entry("09.26", 55),
                        Map.entry("10.01", 56),
                        Map.entry("10.02", 57),
                        Map.entry("10.03", 58),
                        Map.entry("10.30", 59),
                        Map.entry("10.32", 60),
                        Map.entry("10.33", 61),
                        Map.entry("10.34", 62),
                        Map.entry("10.35", 63),
                        Map.entry("10.41", 64),
                        Map.entry("10.43", 65),
                        Map.entry("10.92", 66),
                        Map.entry("10.93", 67),
                        Map.entry("10.94", 68),
                        Map.entry("10.99", 69),
                        Map.entry("11.70", 70),
                        Map.entry("11.71", 71),
                        Map.entry("11.72", 72),
                        Map.entry("11.75", 73),
                        Map.entry("11.80", 74));

                sottoSettore=idMapSottoSettori.get(categoria);

                logger.debug("settore: "+settore);
                logger.debug("sottoSettore: "+sottoSettore);
            }catch (Exception e) {
                logger.error("Errore: ", e);
            }
        }
        return org.apache.commons.lang3.tuple.Pair.of(settore,sottoSettore);
    }

    String sN(String value){
        //0 = NO, 1 = SI
        if (StringUtils.hasText(value)) {
            if (value.charAt(0) == '2') return "0";
            else if (value.charAt(0) == '1') return "1";
        }
        return null;
    }

    Integer mapToId(String value, Map<String, Integer> idMap) {
        if (!StringUtils.hasText(value)) return null;
        Integer idCausa = idMap.get(value);
        if (idCausa == null) throw new RuntimeException(value + " ---> Id=null");
        return idCausa;
    }

    Long toLongOrZero(Number value){
        return value == null ? 0 : value.longValue();
    }

    Long toLongOrNull(Number value){
        return value == null ? null : value.longValue();
    }

    Integer toIntOrZero(Number value){
        return value == null ? 0 : value.intValue();
    }

    Integer toIntOrNull(Number value){
        return value == null ? null : value.intValue();
    }

    Integer toIntOrNull(String value){
        if (StringUtils.hasText(value)) return Integer.valueOf(value);
        return null;
    }


}
