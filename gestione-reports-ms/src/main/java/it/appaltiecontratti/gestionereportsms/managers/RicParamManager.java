package it.appaltiecontratti.gestionereportsms.managers;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import it.appaltiecontratti.gestionereportsms.common.AppConstants;
import it.appaltiecontratti.gestionereportsms.domain.User;
import it.appaltiecontratti.gestionereportsms.domain.WCacheRicPar;
import it.appaltiecontratti.gestionereportsms.domain.WLogEventi;
import it.appaltiecontratti.gestionereportsms.domain.WRicParam;
import it.appaltiecontratti.gestionereportsms.dto.ResponseListaDTO;
import it.appaltiecontratti.gestionereportsms.dto.WLogEventiDTO;
import it.appaltiecontratti.gestionereportsms.dto.WRicParamDTO;
import it.appaltiecontratti.gestionereportsms.exceptions.GenericReportOperationException;
import it.appaltiecontratti.gestionereportsms.repositories.*;
import it.appaltiecontratti.gestionereportsms.specifications.RicParamSpecification;
import it.appaltiecontratti.gestionereportsms.utils.UtilsMethods;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class RicParamManager {

    /**
     * Logger di classe
     * */
    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Variabile d'ambiente. Vedere application.properties
     * */
    @Value("${application.codiceProdotto}")
    private String COD_APP;

    private static final long DEFAULT_INCREMENT = 1;

    /**
     * Repositories di ogni entity.
     * */
    @Autowired
    private WRicParamRepository wrParamRepository;

    @Autowired
    private WCacheRicParRepository wCacheRicParRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WGenChiaviRepository wGenChiaviRepository;

    @Autowired
    private WLogEventiRepository wLogEventiRepository;

    @Autowired
    private WRicercheRepository wrRepository;

    /**
     * Object Mapper
     * */
    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * Metodi di Utility
     * */

    @Transactional(propagation = Propagation.REQUIRES_NEW, timeout = 60, rollbackFor=Exception.class)
    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "3000") })
    public Long getNextId(final String tabella) {

        logger.debug("START esecuzione in RicercheManager nel metodo getNextId per la tabella [ {} ]", tabella);

        Long chiave = wGenChiaviRepository.findByTabellaIgnoreCase(tabella).get().getChiave();

        long chiaveIncrementata = chiave + DEFAULT_INCREMENT;
        wGenChiaviRepository.incrementChiavePerTabella(chiaveIncrementata, tabella);

        logger.debug("END esecuzione in RicercheManager nel metodo getNextId per la tabella [ {} ]", tabella);

        return wGenChiaviRepository.findByTabellaIgnoreCase(tabella).get().getChiave();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertLogEvent(final WLogEventiDTO dto) {

        if (dto == null)
            throw new IllegalArgumentException("dto null");

        logger.debug("Execution start WLogEventiServiceImpl::insertLogEvent for dto [ {} ]", dto);

        WLogEventi po = objectMapper.convertValue(dto, WLogEventi.class);

        User user = null;

        if (dto.getSyscon() != null)
            user = userRepository.findById(dto.getSyscon()).orElse(null);

        po.setUser(user);

        Long id = getNextId("W_LOGEVENTI");

        po.setIdEvento(id);

        wLogEventiRepository.save(po);
    }

    /**
     * Metodi del manager
     * */
    @Transactional(rollbackFor=Exception.class)
    public ResponseListaDTO getListaParametri(
            Long idRicerca,
            Long syscon,
            String fieldToSort,
            String sortDirection
    ){

        logger.debug("START esecuzione in RicParamManager::getListaParametri per il report con idRicerca [{}]", idRicerca);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_N);

        try{
            List<WRicParam> ricercheList = wrParamRepository.findAll(
                    RicParamSpecification.getParamsSpecification(
                            idRicerca,
                            fieldToSort,
                            sortDirection
                    )
            );
            response.setTotalCount((long) ricercheList.size());

            String defSql = wrRepository.getDefSqlByIdRicerca(idRicerca);
            if(!StringUtils.isEmpty(defSql)){
                defSql = UtilsMethods.removeCommentsAndSeparators(defSql);

                List<String> paramsInDefSql = new ArrayList<>();
                paramsInDefSql = UtilsMethods.extractSubstrings(defSql);

                List<String> mandatoryParams = new ArrayList<>();
                for(WRicParam param : ricercheList){
                    if (param.getObbligatorio()!= null && param.getObbligatorio() == 1L){
                        mandatoryParams.add(param.getCodice());
                    }
                }

                List<String> mandatoryParamsNotUsed = new ArrayList<>();
                for(String param : mandatoryParams){
                    if(!paramsInDefSql.contains(param)) {
                        mandatoryParamsNotUsed.add(param);
                    }
                }

                response.setMandatoryParamsNotUsed(mandatoryParamsNotUsed);
            }


            List<WRicParamDTO> paramsMapped = new ArrayList<>();
            for(WRicParam single : ricercheList ){

                Long idRicercaSingle = single.getId().getIdRicerca();
                Long progressivoSingle = single.getId().getProgressivo();
                Long obbligatorioSingle = single.getObbligatorio();

                WRicParamDTO report = objectMapper.convertValue(single, WRicParamDTO.class);
                report.setIdRicerca(idRicercaSingle);
                report.setProgressivo(progressivoSingle);
                report.setObbligatorio(UtilsMethods.mapFieldTwoValues(obbligatorioSingle, 0L, "No", 1L, "Si"));

                report.setMenuField(single.getMenu());
                //Se presente in WCacheRicParam lo inserisco direttamente come parametro.
                Optional<WCacheRicPar> cacheValue = wCacheRicParRepository.findByIdAccountAndIdRicercaAndCodice(syscon, idRicerca, single.getCodice());
                cacheValue.ifPresent(wCacheRicPar -> report.setValue(wCacheRicPar.getValore()));

                paramsMapped.add(report);
            }

            response.setResponse(paramsMapped);
            response.setDone(AppConstants.RESPONSE_DONE_Y);
            response.setTotalCount((long) paramsMapped.size());

            //Loggo la WLogEventi
            //WLogEventiDTO wLogEventiDTO = LogEventiUtils.createGetListaParametriReportEvent(syscon, ipEvento, COD_APP, idRicerca, idProfilo);
            //insertLogEvent(wLogEventiDTO);

        } catch (GenericReportOperationException e){

            if (e.getErrorMessages().isEmpty()) {

                e.getErrorMessages().add(AppConstants.GESTIONE_REPORTS_ERROR_PARAMETRI_REPORT);
                logger.debug("Si è verificato un errore in fase di get parametri del report con idRicerca: [{}]", idRicerca);
            }
            throw e;
        }

        logger.debug("END esecuzione in RicParamManager::getListaParametri per il report con idRicerca [{}]", idRicerca);

        return response;
    }
}
