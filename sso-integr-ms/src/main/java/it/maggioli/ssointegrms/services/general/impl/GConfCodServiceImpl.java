package it.maggioli.ssointegrms.services.general.impl;

import it.maggioli.ssointegrms.common.AppConstants;
import it.maggioli.ssointegrms.domain.C0Campi;
import it.maggioli.ssointegrms.domain.GConfCod;
import it.maggioli.ssointegrms.domain.GConfCodPK;
import it.maggioli.ssointegrms.dto.GConfCodDTO;
import it.maggioli.ssointegrms.dto.GConfCodEditDTO;
import it.maggioli.ssointegrms.exceptions.amministrazione.CodificaAutomaticaNotFoundException;
import it.maggioli.ssointegrms.exceptions.amministrazione.CodificaAutomaticaSintassiNonCorrettaException;
import it.maggioli.ssointegrms.repositories.C0CampiRepository;
import it.maggioli.ssointegrms.repositories.GConfCodRepository;
import it.maggioli.ssointegrms.services.BaseService;
import it.maggioli.ssointegrms.services.general.GConfCodService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Cristiano Perin
 */
@Service
public class GConfCodServiceImpl extends BaseService implements GConfCodService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GConfCodServiceImpl.class);

    @Autowired
    private GConfCodRepository gConfCodRepository;

    @Autowired
    private C0CampiRepository c0CampiRepository;

    @Override
    public List<GConfCodDTO> loadListaCodificaAutomatica(final String codApp) {

        LOGGER.debug("Execution start GConfCodServiceImpl::loadListaCodificaAutomatica for codApp [ {} ]", codApp);

        String searchCodApp = StringUtils.isBlank(codApp) ? AppConstants.G_CODCONF_DEFAULT_CODAPP
                : codApp;

        List<GConfCodDTO> listaDTO = new ArrayList<>();

        List<String> listaCodApp = new ArrayList<>();
        listaCodApp.add(searchCodApp);

        Sort sort = Sort.by(Sort.Direction.ASC, "numOrd");

        if ("PG".equals(searchCodApp) || "PL".equals(searchCodApp)) {
            listaCodApp.add("G1");
            listaCodApp.add("G2");
        }

        List<GConfCod> listaCodifiche = gConfCodRepository.findByCodAppIn(listaCodApp, sort);

        if (listaCodifiche != null && listaCodifiche.size() > 0)
            listaDTO = listaCodifiche.stream().map(c -> getGConfCodDTOFromGConfCod(c)).collect(Collectors.toList());

        return listaDTO;
    }

    @Override
    public GConfCodDTO getDettaglio(final String nomEnt, final String nomCam, final Long tipCam) {

        LOGGER.debug(
                "Execution start GConfCodServiceImpl::getDettaglio for nomEnt [ {} ], nomCam [ {} ] and tipCam [ {} ]",
                nomEnt, nomCam, tipCam);

        if (StringUtils.isBlank(nomEnt))
            throw new IllegalArgumentException("nomEnt null");

        if (StringUtils.isBlank(nomCam))
            throw new IllegalArgumentException("nomCam null");

        if (tipCam == null)
            throw new IllegalArgumentException("tipCam null");

        GConfCodPK id = new GConfCodPK(nomEnt, nomCam, tipCam);
        GConfCod found = gConfCodRepository.findById(id).orElse(null);

        if (found == null) {
            LOGGER.error("Codifica automatica non presente per nomEnt [ {} ], nomCam [ {} ] and tipCam [ {} ]", nomEnt,
                    nomCam, tipCam);

            throw new CodificaAutomaticaNotFoundException();
        }

        return getGConfCodDTOFromGConfCod(found);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public GConfCodDTO updateCodificaAutomatica(final GConfCodEditDTO form) {

        if (form == null)
            throw new IllegalArgumentException("form null");

        LOGGER.debug("Execution start GConfCodServiceImpl::updateCodificaAutomatica for form [ {} ]", form);

        boolean codificaAutomaticaAttiva = StringUtils.isNotBlank(form.getCodificaAutomaticaAttiva())
                && AppConstants.W_CONFIG_SI.equals(form.getCodificaAutomaticaAttiva());

        String codCal = form.getCodCal();
        Long contat = form.getContat();

        String pattern_Formato_valido = "[A-Za-z0-9§\\.\\/\\$_\\-\\@ ]*";
        String format = "";
        String codcalReplace = "";
        String nomentNomcam = "";
        long contatori = 0;
        String check = "";
        long lunghezzaCampo = 0;
        long caratteri = 0;

        if (codificaAutomaticaAttiva) {

            if (StringUtils.isBlank(codCal) || contat == null) {
                throw new CodificaAutomaticaSintassiNonCorrettaException(
                        AppConstants.GESTIONE_CODIFICA_AUTOMATICA_CODCAL_CONTAT_NULL);
            }

            // Controllo formato
            format = new String(codCal);
            while (format.length() > 0) {
                // Contatore
                if ("<".equals(format.substring(0, 1))) {
                    format = format.substring(1);
                    contatori++;
                    // Controllo che ci sia la chisura del token
                    if (!format.contains(">")) {
                        throw new CodificaAutomaticaSintassiNonCorrettaException(
                                AppConstants.GESTIONE_CODIFICA_AUTOMATICA_SINTASSI_NON_CORRETTA);
                    }
                    // Controllo che cominci almeno per 0 o per 9
                    if (!"0".equals(format.substring(0, 1)) && !"9".equals(format.substring(0, 1))) {
                        throw new CodificaAutomaticaSintassiNonCorrettaException(
                                AppConstants.GESTIONE_CODIFICA_AUTOMATICA_SINTASSI_NON_CORRETTA);
                    }
                    check = format.substring(0, 1);
                    // Controllo che siano tutti 0 o tutti 9
                    while (!">".equals(format.substring(0, 1))) {
                        if (!check.equals(format.substring(0, 1))) {
                            throw new CodificaAutomaticaSintassiNonCorrettaException(
                                    AppConstants.GESTIONE_CODIFICA_AUTOMATICA_SINTASSI_NON_CORRETTA);
                        }
                        caratteri++;
                        format = format.substring(1);
                    }
                    format = format.substring(1);
                } else if ("\"".equals(format.substring(0, 1))) { // Stringa
                    format = format.substring(1);
                    // Controllo che ci sia la chisura del token
                    if (!format.contains("\"")) {
                        throw new CodificaAutomaticaSintassiNonCorrettaException(
                                AppConstants.GESTIONE_CODIFICA_AUTOMATICA_SINTASSI_NON_CORRETTA);
                    }
                    caratteri += format.indexOf("\"");
                    format = format.substring(format.indexOf("\"") + 1);
                } else {
                    throw new CodificaAutomaticaSintassiNonCorrettaException(
                            AppConstants.GESTIONE_CODIFICA_AUTOMATICA_SINTASSI_NON_CORRETTA);
                }
            }
            // Controllo che sia specificato un contatore.
            if (contatori < 1) {
                throw new CodificaAutomaticaSintassiNonCorrettaException(
                        AppConstants.GESTIONE_CODIFICA_AUTOMATICA_NO_COUNTER);
            }
            // Controllo che sia specificato solo un contatore.
            if (contatori > 1) {
                throw new CodificaAutomaticaSintassiNonCorrettaException(
                        AppConstants.GESTIONE_CODIFICA_AUTOMATICA_OVER_ONE_COUNTER);
            }

            // Controlla la validita' dei caratteri indicati
            codcalReplace = form.getCodCal().replaceAll("[<>\"]", "");
            if (!codcalReplace.matches(pattern_Formato_valido)) {
                throw new CodificaAutomaticaSintassiNonCorrettaException(
                        AppConstants.GESTIONE_CODIFICA_AUTOMATICA_CARATTERI_NON_ACCETTATI);
            }

            // Controllo che il numero di caratteri non sia superiore alla lunghezza del
            // campo
            nomentNomcam = form.getNomCam().toUpperCase() + "." + form.getNomEnt().toUpperCase() + ".";
            C0Campi campoNomentNomcam = c0CampiRepository.findByCampoLike(nomentNomcam);
            if (campoNomentNomcam != null && StringUtils.isNotBlank(campoNomentNomcam.getC0c_fs())
                    && campoNomentNomcam.getC0c_fs().length() > 1) {
                String lunghezzaCampoString = campoNomentNomcam.getC0c_fs();
                lunghezzaCampoString = lunghezzaCampoString.substring(1);
                lunghezzaCampo = Long.parseLong(lunghezzaCampoString);

                if (caratteri > lunghezzaCampo) {
                    throw new CodificaAutomaticaSintassiNonCorrettaException(
                            AppConstants.GESTIONE_CODIFICA_AUTOMATICA_CARATTERI_SUPERIORI_LUNGHEZZA_CAMPO);
                }
            }

        } else {
            codCal = null;
            contat = null;
        }

        GConfCodPK id = new GConfCodPK(form.getNomEnt(), form.getNomCam(), form.getTipCam());
        GConfCod found = gConfCodRepository.findById(id).orElse(null);

        if (found == null) {
            LOGGER.error("Codifica automatica non presente per nomEnt [ {} ], nomCam [ {} ] and tipCam [ {} ]",
                    form.getNomEnt(), form.getNomCam(), form.getTipCam());

            throw new CodificaAutomaticaNotFoundException();
        }

        found.setCodCal(codCal);
        found.setContat(contat);

        found = gConfCodRepository.save(found);

        return getGConfCodDTOFromGConfCod(found);
    }

    private GConfCodDTO getGConfCodDTOFromGConfCod(final GConfCod codifica) {

        GConfCodDTO dto = new GConfCodDTO();

        dto.setNomEnt(codifica.getId().getNomEnt());
        dto.setNomCam(codifica.getId().getNomCam());
        dto.setTipCam(codifica.getId().getTipCam());
        dto.setCodApp(codifica.getCodApp());
        dto.setDesCam(codifica.getDesCam());
        dto.setContat(codifica.getContat());
        dto.setCodCal(codifica.getCodCal());
        dto.setNumOrd(codifica.getNumOrd());

        return dto;
    }
}
