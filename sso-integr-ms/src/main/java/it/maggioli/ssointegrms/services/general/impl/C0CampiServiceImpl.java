package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.domain.C0Campi;
import it.maggioli.ssointegrms.dto.C0CampiDTO;
import it.maggioli.ssointegrms.dto.C0CampiSearchResultDTO;
import it.maggioli.ssointegrms.dto.ResponseListaDTO;
import it.maggioli.ssointegrms.dto.RicercaC0CampiFormDTO;
import it.maggioli.ssointegrms.repositories.C0CampiCriteriaRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.C0CampiService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cristiano Perin
 */
@Service
public class C0CampiServiceImpl extends BaseService implements C0CampiService {

    private static final Logger LOGGER = LoggerFactory.getLogger(C0CampiServiceImpl.class);

    @Autowired
    private C0CampiCriteriaRepository c0CampiCriteriaRepository;

    @Override
    public ResponseListaDTO loadListaC0Campi(final RicercaC0CampiFormDTO form) {

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start C0CampiServiceImpl::loadListaC0Campi for form [ {} ]", form);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        // paginazione e ordinamento
        final Integer skip = form.getSkip();
        final Integer take = form.getTake();
        final String sort = form.getSort();
        final String sortDirection = form.getSortDirection();
        final String sortKey = StringUtils.isNotBlank(sort) ? sort : "c0c_mne_ber";
        final String sortDirectionKey = StringUtils.isNotBlank(sortDirection) ? sortDirection : "asc";

        String searchData = StringUtils.stripToNull(form.getSearchData());

        List<C0CampiDTO> c0CampiDTOList = new ArrayList<>();

        if (skip != null && take != null && skip >= 0L && take > 0L) {
            // ho la paginazione attiva

            C0CampiSearchResultDTO searchResult = c0CampiCriteriaRepository.loadListaC0Campi(searchData, skip, take,
                    sortKey, sortDirectionKey);

            response.setTotalCount(searchResult.getTotalCount());

            if (searchResult.getC0CampiList() != null) {
                for (C0Campi campo : searchResult.getC0CampiList()) {
                    C0CampiDTO campoDTO = dtoMapper.convertTo(campo, C0CampiDTO.class);
                    c0CampiDTOList.add(campoDTO);
                }
            }

            response.setResponse(c0CampiDTOList);
        }

        return response;
    }
}
