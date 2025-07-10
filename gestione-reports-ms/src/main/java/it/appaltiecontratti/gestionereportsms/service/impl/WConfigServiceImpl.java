package it.appaltiecontratti.gestionereportsms.service.impl;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.appaltiecontratti.gestionereportsms.common.AppConstants;
import it.appaltiecontratti.gestionereportsms.domain.WConfig;
import it.appaltiecontratti.gestionereportsms.domain.WConfigPK;
import it.appaltiecontratti.gestionereportsms.dto.WConfigDTO;
import it.appaltiecontratti.gestionereportsms.repositories.WConfigRepository;
import it.appaltiecontratti.gestionereportsms.service.BaseService;
import it.appaltiecontratti.gestionereportsms.service.WConfigService;

/**
 * @author Cristiano Perin
 */
@Service
public class WConfigServiceImpl extends BaseService implements WConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WConfigServiceImpl.class);

    @Autowired
    private WConfigRepository wConfigRepository;

    @Override
    public WConfigDTO getStandardConfiguration(final String chiave) {
        LOGGER.debug("Execution start WConfigServiceImpl::getStandardConfiguration for chiave [ {} ]", chiave);

        if (StringUtils.isNotBlank(chiave)) {
            Optional<WConfig> oConfig = wConfigRepository
                    .findById(new WConfigPK(AppConstants.W_CONFIG_COD_APP_STANDARD, chiave));
            if (oConfig.isPresent()) {
                return getWConfigDTOFromWConfig(oConfig.get(), false);
            }

            LOGGER.warn("La chiave [ {} ] non e' stata trovata per il codApp standard \"W_\", la cerco per \"G_\"", chiave);

            oConfig = wConfigRepository
                    .findById(new WConfigPK(AppConstants.W_CONFIG_COD_APP_GENE_STANDARD, chiave));
            if (oConfig.isPresent()) {
                return getWConfigDTOFromWConfig(oConfig.get(), false);
            }

            LOGGER.warn("La chiave [ {} ] non e' stata trovata per il codApp standard \"G_\"", chiave);
        }

        return null;
    }
    
    @Override
    public WConfigDTO getConfiguration(final String chiave) {
        LOGGER.debug("Execution start WConfigServiceImpl::getConfiguration for chiave [ {} ]", chiave);

        if (StringUtils.isNotBlank(chiave)) {
            WConfig config = wConfigRepository.findById(new WConfigPK(codiceProdotto, chiave)).orElse(null);
            if (config != null) {
                return getWConfigDTOFromWConfig(config, false);
            } else {
                LOGGER.warn("La chiave [ {} ] non e' stata trovata per il codApp [ {} ], la cerco per il codApp standard", chiave, codiceProdotto);
                return getStandardConfiguration(chiave);
            }
        }

        return null;
    }

   
    private WConfigDTO getWConfigDTOFromWConfig(final WConfig config, final boolean cleanCriptato) {

        WConfigDTO dto = new WConfigDTO();

        dto.setCodApp(config.getId().getCodApp());
        dto.setChiave(config.getId().getChiave());
        dto.setValore(config.getValore());
        dto.setSezione(config.getSezione());
        dto.setDescrizione(config.getDescrizione());
        dto.setCriptato(config.getCriptato());

        if (cleanCriptato && "1".equals(config.getCriptato()) && StringUtils.isNotBlank(config.getValore())) {
            dto.setValore("*******");
        }

        return dto;
    }
}
