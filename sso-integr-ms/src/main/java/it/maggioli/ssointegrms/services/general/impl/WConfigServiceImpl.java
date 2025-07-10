package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.domain.WConfig;
import it.maggioli.ssointegrms.domain.WConfigPK;
import it.maggioli.ssointegrms.dto.*;
import it.maggioli.ssointegrms.exceptions.amministrazione.ConfigurationNotFoundException;
import it.maggioli.ssointegrms.repositories.WConfigCriteriaRepository;
import it.maggioli.ssointegrms.repositories.WConfigRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.WConfigService;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Cristiano Perin
 */
@Service
public class WConfigServiceImpl extends BaseService implements WConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WConfigServiceImpl.class);

    @Autowired
    private WConfigRepository wConfigRepository;

    @Autowired
    private WConfigCriteriaRepository wConfigCriteriaRepository;

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
    public String getStandardConfigurationValue(final String chiave) {
        LOGGER.debug("Execution start WConfigServiceImpl::getStandardConfigurationValue for chiave [ {} ]", chiave);

        WConfigDTO config = this.getStandardConfiguration(chiave);

        return config != null ? config.getValore() : null;
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

    @Override
    public String getConfigurationValue(final String chiave) {
        LOGGER.debug("Execution start WConfigServiceImpl::getConfigurationValue for chiave [ {} ]", chiave);

        WConfigDTO config = this.getConfiguration(chiave);

        return config != null ? config.getValore() : null;
    }

    @Override
    public RicercaConfigurazioniInizDTO getInizRicercaConfigurazioni() {
        LOGGER.debug("Execution start WConfigServiceImpl::getInizRicercaConfigurazioni");

        RicercaConfigurazioniInizDTO dto = new RicercaConfigurazioniInizDTO();

        List<String> sezioni = wConfigRepository.findSezioniDistinct();

        sezioni.removeIf(Objects::isNull);
        sezioni.removeIf(String::isEmpty);

        dto.setListaSezioni(sezioni);

        return dto;
    }

    @Override
    public ResponseListaDTO loadListaConfigurazioni(final RicercaConfigurazioniFormDTO form) {

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start WConfigServiceImpl::loadListaConfigurazioni for form [ {} ]", form);

        ResponseListaDTO response = new ResponseListaDTO();
        response.setDone(AppConstants.RESPONSE_DONE_Y);

        // paginazione e ordinamento
        final Integer skip = form.getSkip();
        final Integer take = form.getTake();
        final String sort = form.getSort();
        final String sortDirection = form.getSortDirection();
        final String sortKey = StringUtils.isNotBlank(sort) ? sort : "chiave";
        final String sortDirectionKey = StringUtils.isNotBlank(sortDirection) ? sortDirection : "asc";

        // clear attributi con stringa vuota
        form.setSezione(StringUtils.stripToNull(form.getSezione()));
        form.setChiave(StringUtils.stripToNull(form.getChiave()));
        form.setValore(StringUtils.stripToNull(form.getValore()));
        form.setDescrizione(StringUtils.stripToNull(form.getDescrizione()));

        List<WConfigDTO> wConfigDTOList = new ArrayList<>();

        if (skip != null && take != null && skip >= 0L && take > 0L) {
            // ho la paginazione attiva

            WConfigSearchResultDTO searchResult = wConfigCriteriaRepository.loadListaConfigurazioni(form.getSezione(),
                    form.getChiave(), form.getValore(), form.getDescrizione(), skip, take, sortKey, sortDirectionKey);

            response.setTotalCount(searchResult.getTotalCount());

            if (searchResult.getConfigList() != null) {
                for (WConfig config : searchResult.getConfigList()) {
                    WConfigDTO wConfigDTO = getWConfigDTOFromWConfig(config, true);
                    wConfigDTOList.add(wConfigDTO);
                }
            }

            response.setResponse(wConfigDTOList);
        }

        return response;
    }

    @Override
    public WConfigDTO getDettaglioConfiguration(final String codApp, final String chiave) {
        LOGGER.debug(
                "Execution start WConfigServiceImpl::getDettaglioConfiguration for codApp [ {} ] and chiave [ {} ]",
                codApp, chiave);

        if (StringUtils.isBlank(codApp))
            throw new IllegalArgumentException("codApp null");

        if (StringUtils.isBlank(chiave))
            throw new IllegalArgumentException("chiave null");

        WConfig config = wConfigRepository.findById(new WConfigPK(codApp, chiave)).orElse(null);

        if (config == null) {
            LOGGER.error("Configurazione non presente per codApp [ {} ] e chiave [ {} ]", codApp, chiave);

            throw new ConfigurationNotFoundException();
        }

        return getWConfigDTOFromWConfig(config, true);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public WConfigDTO updateConfiguration(final WConfigEditDTO form) throws CriptazioneException {

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start WConfigServiceImpl::updateConfiguration with form [ {} ]", form);

        WConfigPK id = new WConfigPK(form.getCodApp(), form.getChiave());

        WConfig toUpdate = wConfigRepository.findById(id).orElse(null);

        if (toUpdate == null) {
            LOGGER.error("Configurazione non presente per codApp [ {} ] e chiave [ {} ]", form.getCodApp(),
                    form.getChiave());

            throw new ConfigurationNotFoundException();
        }

        if (AppConstants.W_CONFIG_SI.equals(toUpdate.getCriptato())) {

            try {
                ICriptazioneByte crypt = FactoryCriptazioneByte.getInstance(
                        FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, form.getValore().getBytes(),
                        ICriptazioneByte.FORMATO_DATO_NON_CIFRATO);
                toUpdate.setValore(new String(crypt.getDatoCifrato()));
            } catch (CriptazioneException e) {
                LOGGER.error("Errore in fase di crittazione della chiave hash per la verifica dell'utente", e);
                throw e;
            }

        } else {
            toUpdate.setValore(form.getValore());
        }

        toUpdate = wConfigRepository.save(toUpdate);

        return getWConfigDTOFromWConfig(toUpdate, true);
    }

    private WConfigDTO getWConfigDTOFromWConfig(final WConfig config, final boolean cleanCriptato) {

        WConfigDTO dto = new WConfigDTO();

        dto.setCodApp(config.getId().getCodApp());
        dto.setChiave(config.getId().getChiave());
        dto.setValore(config.getValore());
        dto.setSezione(config.getSezione());
        dto.setDescrizione(config.getDescrizione());
        dto.setCriptato(config.getCriptato());

        if (cleanCriptato && AppConstants.W_CONFIG_SI.equals(config.getCriptato()) && StringUtils.isNotBlank(config.getValore())) {
            dto.setValore("*******");
        }

        return dto;
    }
}
