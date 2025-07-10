package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.domain.WAtt;
import it.maggioli.ssointegrms.domain.WAttPK;
import it.maggioli.ssointegrms.repositories.WAttRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.WAttService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Cristiano Perin
 */
@Service
public class WAttServiceImpl extends BaseService implements WAttService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WAttServiceImpl.class);

    @Autowired
    private WAttRepository wAttRepository;

    @Override
    public List<String> getOpzioniProdotto(final String codApp) {

        LOGGER.debug("Execution start WAttServiceImpl::getOpzioniProdotto for codApp [ {} ]", codApp);

        if (StringUtils.isNotBlank(codApp)) {

            WAtt wAtt = wAttRepository.findById(new WAttPK(codApp, AppConstants.W_ATT_OPZIONI))
                    .orElse(null);

            if (wAtt != null && StringUtils.isNotBlank(wAtt.getValore())) {

                // divido la stringa per il separatore |
                List<String> opzioniProdotto = null;

                String valore = wAtt.getValore();
                if (StringUtils.isNotBlank(valore)) {
                    // e aggiungo le parti nella lista finale
                    opzioniProdotto = new ArrayList<>(Arrays.asList(valore.split(AppConstants.OU_SEPARATORE_REGEX)));
                } else {
                    opzioniProdotto = new ArrayList<>();
                }

                return opzioniProdotto;
            }
        }

        return null;
    }
}
