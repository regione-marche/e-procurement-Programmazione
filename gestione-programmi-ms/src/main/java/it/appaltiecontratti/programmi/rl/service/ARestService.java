package it.appaltiecontratti.programmi.rl.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Arrays;
import it.appaltiecontratti.programmi.rl.beans.AccessTokenRL;
import it.appaltiecontratti.programmi.rl.beans.ConfigServiceRL;
import it.appaltiecontratti.programmi.rl.mapper.SqlUtil;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class ARestService<T,R> extends ABaseService {

    private static final Logger logger = LogManager.getLogger(ARestService.class);

    public static String PARAM_RUP_NAZIONE="rup.nazione";
    public static String PARAM_RUP_CODICE_CATASTALE="rup.codice_catastale";
    public static String PARAM_SA_CODICE ="sa.codein";
    public static String PARAM_SA_EMAIL ="sa.email";
    public static String PARAM_PROG_ANNO_INIZIO="prog.anno_inizio";

    private static final Logger LOGGER = LogManager.getLogger(ARestService.class);

    final String apiSuffix;
    final RestTemplate restTemplate;

    ARestService(RestTemplate restTemplate, String apiSuffix, SqlUtil sqlUtil){
        super(sqlUtil);
        this.restTemplate=restTemplate;
        this.apiSuffix = apiSuffix;
    }

    public Pair<Long,String> invia(ConfigServiceRL configServiceRL, AccessTokenRL accessTokenRL, R obj, Map<String, Object> params) throws Exception {
        if (params==null) params=new HashMap<>();

        String apiUri=configServiceRL.getEndpointApi().concat(apiSuffix);
        logger.debug("requestAPI="+apiUri);
        Long id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenRL.getToken());
        HttpEntity entity = new HttpEntity(getJson(obj,params), headers);

        ResponseEntity<String> response = restTemplate.exchange(apiUri, HttpMethod.POST, entity, String.class);
        String result=response.getBody();
        logger.debug("result="+ result);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResult = objectMapper.readTree(result);
        String esito = jsonResult.path("esito").asText();
        if (StringUtils.hasText(esito) && "OK".equals(esito)) {
            JsonNode record = jsonResult.path("record");
            id = record.path("id").asLong();
            logger.debug("esito="+esito+" id="+id);
        }else{
            throw new RuntimeException(jsonResult.toString());
        }
        return Pair.of(id,result);
    }

    public T leggi(ConfigServiceRL configServiceRL,AccessTokenRL accessTokenRL,Integer anno, String apiCodiceFiscale){
        String apiUri=configServiceRL.getEndpointApi().concat(apiSuffix)
                .concat("?anno=").concat(anno.toString())
                .concat("&enteCdFiscale=").concat(apiCodiceFiscale);
        logger.debug("requestAPI="+apiUri);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessTokenRL.getToken());
        HttpEntity entity = new HttpEntity("", headers);

        ResponseEntity<T> response = restTemplate.exchange(apiUri, HttpMethod.GET, entity, modelDtoClass());
        T result=response.getBody();
        logger.debug("result="+result);
        return result;
    }

    public abstract String getJson(R obj, Map<String, Object> params) throws JsonProcessingException;

    public abstract T mapper(R programma, Map<String,Object> params);

    protected Class<T> modelDtoClass(){
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public List<String> getErrorResult(String value) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(value);
        JsonNode erroriNode = rootNode.path("errori");
        List<String> result=new ArrayList<>();
        if (erroriNode.isArray())
            erroriNode.forEach(error -> {
                result.addAll(
                    Arrays.asList(
                        error.asText().split(";")).stream()
                        .filter(StringUtils::hasText)
                        .collect(Collectors.toList()));
            });
        return result;
    }
}
