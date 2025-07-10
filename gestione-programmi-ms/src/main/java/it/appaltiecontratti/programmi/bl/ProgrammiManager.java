package it.appaltiecontratti.programmi.bl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.appaltiecontratti.programmi.Constants;
import it.appaltiecontratti.programmi.rl.beans.AccessTokenRL;
import it.appaltiecontratti.programmi.rl.beans.ConfigServiceRL;
import it.appaltiecontratti.programmi.rl.dto.FornitureAcquistiDto;
import it.appaltiecontratti.programmi.rl.dto.LavoriDto;
import it.appaltiecontratti.programmi.rl.service.ARestService;
import it.appaltiecontratti.programmi.rl.service.AuthServiceRL;
import it.appaltiecontratti.programmi.rl.service.ProgrammiBiennaliRLService;
import it.appaltiecontratti.programmi.rl.service.ProgrammiTriennaliRLService;
import it.appaltiecontratti.programmi.entity.*;
import it.appaltiecontratti.programmi.entity.pubblicazioni.LoginResult;
import it.appaltiecontratti.programmi.entity.pubblicazioni.*;
import it.appaltiecontratti.programmi.entity.validazione.ValidateEntry;
import it.appaltiecontratti.programmi.mapper.ProgrammiMapper;
import it.appaltiecontratti.programmi.mapper.SqlMapper;
import it.appaltiecontratti.programmi.utils.ProgrammaAnnualitaComparator;
import it.appaltiecontratti.sicurezza.bl.WConfigManager;
import it.appaltiecontratti.sicurezza.entity.UserAuthClaimsDTO;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.ServletContext;
import javax.sql.DataSource;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component(value = "programmiManager")
public class ProgrammiManager {

    /**
     * Logger di classe.
     */
    protected static Logger logger = LogManager.getLogger(ProgrammiManager.class);

    /**
     * Dao MyBatis con le primitive di estrazione dei dati.
     */
    @Autowired
    private ProgrammiMapper programmiMapper;

    @Autowired
    private SqlMapper sqlMapper;

    @Autowired
    private RestClientManager restclientmanager;

    @Autowired
    DataSource dataSource;

    @Autowired
    protected WGenChiaviManager wgcManager;

    @Autowired
    private WConfigManager wConfigManager;

    @Value("${application.enableProxy:false}")
    private boolean enableProxy;

    @Value("${http_proxy:#{null}}")
    private String httpProxy;

    @Value("${https_proxy:#{null}}")
    private String httpsProxy;

    public static final String CONFIG_CONFIGURAZIONE_APPLICATIVA = "ControlloDati";

    /**
     * Url Servizio Di pubblicazione programmi
     */
    private static final String PROP_WS_PUBBLICAZIONI_URL_LOGIN = "it.eldasoft.pubblicazioni.ws.urlLogin";

    /**
     * Id Client
     */
    private static final String PROP_WS_PUBBLICAZIONI_IDCLIENT = "it.eldasoft.pubblicazioni.ws.clientId";

    /**
     * Key Client
     */
    private static final String PROP_WS_PUBBLICAZIONI_KEYCLIENT = "it.eldasoft.pubblicazioni.ws.clientKey";

    /**
     * Username servizi di pubblicazione
     */
    private static final String PROP_WS_PUBBLICAZIONI_USERNAME = "it.eldasoft.pubblicazioni.ws.username";

    /**
     * Password servizi di pubblicazione
     */
    private static final String PROP_WS_PUBBLICAZIONI_PASSWORD = "it.eldasoft.pubblicazioni.ws.password";
    /**
     * PROGRAMMI
     */
    private static final String PROP_WS_PUBBLICAZIONI_URLPROGRAMMI = "it.eldasoft.pubblicazioni.ws.urlProgrammi";

    private static final String CONFIG_CHIAVE_APP = "it.maggioli.eldasoft.wslogin.jwtKey";
    private static final String CONFIG_TIPO_APPLICAZIONE = "it.eldasoft.wspubblicazioni.tipoInstallazione";

    public ResponseListaProgrammi getListaProgrammi(final Long loggedUserSyscon, ProgrammiSearchForm searchForm) {

        ResponseListaProgrammi risultato = new ResponseListaProgrammi();

        try {

            String idCentroCosto = this.programmiMapper.getCentroCosto(searchForm.getSyscon());
            if (idCentroCosto != null) {
                searchForm.setIdCentroCosto(Long.valueOf(idCentroCosto));
            }

            boolean filterBySyscon = filterProgrammaBySyscon(loggedUserSyscon);
            if (filterBySyscon) {
                searchForm.setSyscon(loggedUserSyscon);
            } else {
                searchForm.setSyscon(null);
            }
            if (searchForm.getSearchString() != null) {
                searchForm.setSearchString(searchForm.getSearchString().toUpperCase());
                searchForm.setSearchStringLike("%" + searchForm.getSearchString() + "%");
            }
            int totalCount = this.programmiMapper.countSearchProgrammi(searchForm);
            RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
            List<ProgrammaBaseEntry> programmi = this.programmiMapper.searchProgrammi(searchForm, rowBounds);
            for (ProgrammaBaseEntry p : programmi) {
                String nomtec = sqlMapper.executeReturnString("select nomtec from tecni where codtec = '" + p.getReferenteProgrammazione() + "'");
                p.setReferenteProgrammazione(nomtec);
            }
            risultato.setTotalCount(totalCount);
            risultato.setData(programmi);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante l'estrazione dei programmi per la stazione" + "appaltante"
                    + searchForm.getStazioneAppaltante() + " con tipologia " + searchForm.getTipologia()
                    + ", searchString " + searchForm.getSearchString(), t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return risultato;
    }

    public ResponseDettaglioProgrammi getProgramma(Long contri) {

        ResponseDettaglioProgrammi risultato = new ResponseDettaglioProgrammi();
        try {
            ProgrammaEntry programma = this.programmiMapper.getProgramma(contri);
            if (programma != null) {


                String valore = wConfigManager.getConfigurationValue(CONFIG_CONFIGURAZIONE_APPLICATIVA);

                if (!"SCP".equals(valore)) {
                    String centriCosto = this.programmiMapper.getCentroCosto(programma.getSyscon());
                    if (centriCosto != null) {
                        programma.setShowUfficio(true);
                    } else {
                        programma.setShowUfficio(false);
                    }
                } else {
                    programma.setShowUfficio(true);
                }
                RupEntry rupEntry = this.programmiMapper.getRup(programma.getResponsabile());
                UffEntry uffEntry = this.programmiMapper.getUff(programma.getIdUfficio());
                programma.setUfficio(uffEntry);
                programma.setRup(rupEntry);
                programma.setExistProgrammaAnnoPrecedente(false);

                // check esistenza programma anno precedente per la stessa tipologia e stazione
                // appaltante
                Long tipoProgramma = programma.getTipoProg();
                String codiceStazioneAppaltante = programma.getStazioneappaltante();
                Long annoInizio = programma.getAnnoInizio();
                if (annoInizio != null) {
                    Long annoPrecedente = annoInizio - 1L;
                    Long check = this.programmiMapper.checkExistsProgrammaByTipoAndSAAndAnno(tipoProgramma,
                            codiceStazioneAppaltante, annoPrecedente);
                    programma.setExistProgrammaAnnoPrecedente(check != null && check > 0L);
                }

                // check condizione VIGILANZA2-234
                programma.setShowConfrontoProgrammi(this.checkConfrontoProgrammi(programma));

                risultato.setData(programma);
                risultato.setEsito(true);
            } else {
                risultato.setErrorData(BaseResponse.ERROR_NOT_FOUND);
                risultato.setEsito(false);
            }

        } catch (Throwable t) {
            logger.error("Errore inaspettato durante l'estrazione dei programma con contri:" + contri, t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return risultato;
    }

    public ResponseListaInterventi getInterventi(InterventiSearchForm searchForm) {

        ResponseListaInterventi risultato = new ResponseListaInterventi();
        try {
            if (searchForm.getDescrizione() != null) {
                searchForm.setDescrizione("%" + searchForm.getDescrizione().toUpperCase() + "%");
            }
            if (searchForm.getCodiceCUP() != null) {
                searchForm.setCodiceCUP("%" + searchForm.getCodiceCUP().toUpperCase() + "%");
            }
            if (searchForm.getCodInterno() != null) {
                searchForm.setCodInterno("%" + searchForm.getCodInterno().toUpperCase() + "%");
            }
            if (searchForm.getNumeroCui() != null) {
                searchForm.setNumeroCui("%" + searchForm.getNumeroCui().toUpperCase() + "%");
            }

            int totalCount = this.programmiMapper.countSearchInterventi(searchForm);
            if (searchForm.getTake() == 0) {
                searchForm.setSkip(0);
                searchForm.setTake(totalCount);
            }

            RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
            List<InterventoBaseEntry> interventi = this.programmiMapper.getInterventi(searchForm, rowBounds);
            risultato.setTotalCount(totalCount);
            risultato.setData(interventi);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante l'estrazione degli interventi del programma con contri:"
                    + searchForm.getIdProgramma(), t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    public ResponseListaInterventiNR getInterventiNonRiproposti(ModulesSearchForm searchForm) {
        ResponseListaInterventiNR risultato = new ResponseListaInterventiNR();
        try {

            int totalCount = this.programmiMapper.countSearchInterventiNonRiproposti(searchForm);
            RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());

            List<InterventoNonRipropostoEntry> interventiNonRiproposti = this.programmiMapper
                    .getInterventiNonRiproposti(searchForm, rowBounds);
            risultato.setTotalCount(totalCount);
            risultato.setData(interventiNonRiproposti);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error(
                    "Errore inaspettato durante l'estrazione degli interventi non riproposti del programma con contri:"
                            + searchForm.getIdProgramma(),
                    t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;

    }

    public ResponseListaOpereIncompiute getOpereIncompiute(ModulesSearchForm searchForm) {
        ResponseListaOpereIncompiute risultato = new ResponseListaOpereIncompiute();
        try {

            int totalCount = this.programmiMapper.countSearchOpereIncompiute(searchForm);
            RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
            List<OperaIncompiutaBaseEntry> opereIncompiute = this.programmiMapper.getOpereIncompiute(searchForm,
                    rowBounds);
            risultato.setTotalCount(totalCount);
            risultato.setData(opereIncompiute);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante l'estrazione delle opere incompiute del programma con contri:"
                    + searchForm.getIdProgramma(), t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    public ResponseInsert insertProgramma(final Long loggedUserSyscon, ProgrammaInsertForm form) {
        ResponseInsert risultato = new ResponseInsert();
        try {

            if (loggedUserSyscon == null) {
                logger.error("L'utente {} non e' stato trovato", loggedUserSyscon);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_NOT_FOUND);
                return risultato;
            }

            if (StringUtils.isBlank(form.getStazioneappaltante())) {
                logger.error("Stazione appaltante null");
                throw new IllegalArgumentException();
            }

            boolean isUtenteAbilitatoTutteSA = isUtenteAbilitatoTutteSA(loggedUserSyscon);

            boolean hasUserSaPermission = hasUserSaPermission(loggedUserSyscon, form.getStazioneappaltante());

            if (!isUtenteAbilitatoTutteSA && !hasUserSaPermission) {
                logger.error("L'utente {} non risulta associato all'ufficio intestatario {}", loggedUserSyscon, form.getStazioneappaltante());
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_PERMISSION);
                return risultato;
            }

            Long contri = this.programmiMapper.getMaxContri();
            if (contri == null) {
                contri = 1L;
            } else {
                contri = contri + 1;
            }

            if (form.getAnnoInizio() > 2023) {
                form.setNorma(3L);
            } else if (form.getAnnoInizio() <= 2023) {
                form.setNorma(2L);
            }


            form.setId(contri);
            String cfStazioneAppaltante = this.programmiMapper.getCFSAByCode(form.getStazioneappaltante());
            String id = calcolaIdProgramma(form.getTipoProg(), form.getAnnoInizio(), cfStazioneAppaltante, form.getSoloScheda());
            String descrizioneBreve = calcolaDescrizioneBreveProgramma(form.getTipoProg(), form.getAnnoInizio(), form.getDataApprovazione(), id, form.getIdUfficio(), form.getDataAtto(), form.getStazioneappaltante(), form.getSoloScheda(), form.getNorma());
            form.setDescrizioneBreve(descrizioneBreve);
            form.setIdProgramma(id);
            form.setResponsabile(form.getResponsabile() != null ? form.getResponsabile().replace("'", "") : null);

            pulisciProgrammaTabInsert(form);
            this.programmiMapper.insertProgramma(form);
            risultato.setData(contri);
            risultato.setEsito(true);
        } catch (Throwable t) {
            risultato.setEsito(false);
            logger.error("Errore inaspettato durante la creazione del programma. Descrizione :"
                    + form.getDescrizioneBreve() + "Anno: " + form.getAnnoInizio(), t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return risultato;
    }

    private void pulisciProgrammaTabInsert(ProgrammaInsertForm form) {

        if (form.getDescrizioneBreve() != null) {
            form.setDescrizioneBreve(form.getDescrizioneBreve().replaceAll("[\t ]+", " "));
        }
        if (form.getNumeroAtto() != null) {
            form.setNumeroAtto(form.getNumeroAtto().replaceAll("[\t ]+", " "));
        }
        if (form.getNumeroApprovazione() != null) {
            form.setNumeroApprovazione(form.getNumeroApprovazione().replaceAll("[\t ]+", " "));
        }
        if (form.getTitoloAdozione() != null) {
            form.setTitoloAdozione(form.getTitoloAdozione().replaceAll("[\t ]+", " "));
        }
        if (form.getUrlAdozione() != null) {
            form.setUrlAdozione(form.getUrlAdozione().replaceAll("[\t ]+", " "));
        }
        if (form.getNumeroApprovazione() != null) {
            form.setNumeroApprovazione(form.getNumeroApprovazione().replaceAll("[\t ]+", " "));
        }
        if (form.getTitoloApprovazione() != null) {
            form.setTitoloApprovazione(form.getTitoloApprovazione().replaceAll("[\t ]+", " "));
        }
        if (form.getUrlApprovazione() != null) {
            form.setUrlApprovazione(form.getUrlApprovazione().replaceAll("[\t ]+", " "));
        }
        if (form.getPubblica() != null) {
            form.setPubblica(form.getPubblica().replaceAll("[\t ]+", " "));
        }
        if (form.getResponsabile() != null) {
            form.setResponsabile(form.getResponsabile().replaceAll("[\t ]+", " "));
        }
    }

    public BaseResponse updateProgramma(ProgrammaUpdateForm form) {
        BaseResponse risultato = new BaseResponse();
        try {
            ProgrammaEntry programma = this.programmiMapper.getProgramma(form.getId());
            Boolean soloScheda = false;
            if (programma.getIdProgramma().startsWith("OI")) {
                soloScheda = true;
            }
            String descrizioneBreve = calcolaDescrizioneBreveProgramma(programma.getTipoProg(), programma.getAnnoInizio(), form.getDataApprovazione(), programma.getIdProgramma(), programma.getIdUfficio(), programma.getDataAtto(), programma.getStazioneappaltante(), soloScheda, programma.getNorma());
            form.setDescrizioneBreve(descrizioneBreve);
            form.setResponsabile(form.getResponsabile() != null ? form.getResponsabile().replace("'", "") : null);
            pulisciProgrammaTabUpdate(form);
            this.programmiMapper.updateProgramma(form);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante l'update del programma. contri:" + form.getId(), t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return risultato;
    }

    private void pulisciProgrammaTabUpdate(ProgrammaUpdateForm form) {
        if (form.getDescrizioneBreve() != null) {
            form.setDescrizioneBreve(form.getDescrizioneBreve().replaceAll("[\t ]+", " "));
        }
        if (form.getNumeroAtto() != null) {
            form.setNumeroAtto(form.getNumeroAtto().replaceAll("[\t ]+", " "));
        }
        if (form.getNumeroApprovazione() != null) {
            form.setNumeroApprovazione(form.getNumeroApprovazione().replaceAll("[\t ]+", " "));
        }
        if (form.getTitoloAdozione() != null) {
            form.setTitoloAdozione(form.getTitoloAdozione().replaceAll("[\t ]+", " "));
        }
        if (form.getUrlAdozione() != null) {
            form.setUrlAdozione(form.getUrlAdozione().replaceAll("[\t ]+", " "));
        }
        if (form.getNumeroApprovazione() != null) {
            form.setNumeroApprovazione(form.getNumeroApprovazione().replaceAll("[\t ]+", " "));
        }
        if (form.getTitoloApprovazione() != null) {
            form.setTitoloApprovazione(form.getTitoloApprovazione().replaceAll("[\t ]+", " "));
        }
        if (form.getUrlApprovazione() != null) {
            form.setUrlApprovazione(form.getUrlApprovazione().replaceAll("[\t ]+", " "));
        }
        if (form.getResponsabile() != null) {
            form.setResponsabile(form.getResponsabile().replaceAll("[\t ]+", " "));
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResponse deleteProgramma(Long contri) throws Exception {
        BaseResponse risultato = new BaseResponse();
        risultato.setEsito(true);
        try {
            this.programmiMapper.deleteProgramma(contri);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la cancellazione del programma. contri:" + contri, e);
            throw (e);
        }
        try {
            this.programmiMapper.deleteInterventi(contri);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la cancellazione degli interventi. contri:" + contri, e);
            throw (e);
        }
        try {
            this.programmiMapper.deleteOpereIncompiute(contri);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la cancellazione delle opere incompiute. contri:" + contri, e);
            throw (e);
        }
        try {
            this.programmiMapper.deleteInterventiNonRiproposti(contri);
        } catch (Exception e) {
            logger.error(
                    "Errore inaspettato durante la cancellazione degli interventi non riproposti. contri:" + contri, e);
            throw (e);
        }
        try {
            this.programmiMapper.deleteImmobili(contri);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la cancellazione degli immobili. contri:" + contri, e);
            throw (e);
        }
        try {
            this.programmiMapper.deleteRisorseDiCapitoloProgramma(contri);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la cancellazione delle risorse di capitolo. contri:" + contri, e);
            throw (e);
        }
        risultato.setEsito(true && risultato.isEsito());
        return risultato;
    }

    public String calcolaIdProgramma(Long tiprog, Long anntri, String cfein, Boolean soloScheda) {

        String id;
        String idParzialeLun = "";
        String prefix = tiprog == 1L ? "LP" : tiprog == 2L ? "FS" : "03";
        if (soloScheda != null && soloScheda == true) {
            prefix = "OI";
        }

        idParzialeLun = prefix + cfein + anntri + "%";
        String maxId = this.programmiMapper.getMaxIdCounter(idParzialeLun);
        if (maxId == null) {
            id = idParzialeLun.replace("%", "001");
        } else {
            String counter = maxId.substring(maxId.length() - 3, maxId.length());
            Long counterLong = Long.parseLong(counter) + 1;
            id = idParzialeLun.replace("%", StringUtils.leftPad(counterLong.toString(), 3, "0"));
        }
        return id;
    }

    public String calcolaDescrizioneBreveProgramma(Long tipoProg, Long annoInizio, Date dataApprovazione, String idProgramma, Long idUfficio, Date dataAtto, String stazioneAppaltante, Boolean soloScheda, Long tipoNorma) {

        String descrizione = "";
        if (soloScheda != null && soloScheda == true) {
            return "Rilevazione opere incompiute anno " + (annoInizio - 1L);
        }

        boolean isFirst = idFirstProgram(idProgramma.substring(idProgramma.length() - 3), annoInizio, idUfficio, stazioneAppaltante, tipoProg);
        if (tipoProg == 1) {
            descrizione = "Programma triennale dei lavori " + annoInizio + "/" + (annoInizio + 2);
            if (isFirst && dataAtto != null && dataApprovazione == null) {
                descrizione = "Adozione dello schema della programmazione dei lavori " + annoInizio + "/"
                        + (annoInizio + 2);
            }
        } else if (tipoProg == 3) {
            descrizione = "Programma " + annoInizio + "/" + (annoInizio + 2);

        } else {
            if (tipoNorma == 3L) {
                descrizione = "Programma triennale degli acquisti " + annoInizio + "/" + (annoInizio + 2);
            } else {
                descrizione = "Programma biennale degli acquisti " + annoInizio + "/" + (annoInizio + 1);
            }

        }
        if (!isFirst && dataApprovazione != null) {
            descrizione = descrizione + " - Aggiornamento " + new SimpleDateFormat("dd/MM/yyyy").format(dataApprovazione);
        }
        return descrizione;
    }


    private boolean idFirstProgram(String counterIdProgramma, Long annoInizio, Long idUfficio, String stazioneAppaltante, Long tipoProg) {
        List<String> ids = new ArrayList<String>();
        if (idUfficio == null) {
            ids = this.programmiMapper.getListaIdProgrammiSenzaUfficio(annoInizio, stazioneAppaltante, tipoProg);
        } else {
            ids = this.programmiMapper.getListaIdProgrammi(annoInizio, idUfficio, stazioneAppaltante, tipoProg);
        }
        for (String id : ids) {
            if (id != null) {
                String idCounter = id.substring(id.length() - 3);
                if (Long.valueOf(idCounter) < Long.valueOf(counterIdProgramma)) {
                    return false;
                }
            }
        }
        return true;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResponse updateIdRicevutoL(ProgrammaPubblicatoForm form, Long syscon) throws Exception {
        BaseResponse risultato = new BaseResponse();
        risultato.setEsito(true);
        try {

            this.programmiMapper.updateIdRicevuto(form);
            // inserisco flusso
            FlussoEntry flusso = new FlussoEntry();
            Long idFlusso = this.wgcManager.getNextId("W9FLUSSI");
            Long key03 = new Long(982);
            flusso.setId(idFlusso);
            flusso.setArea(new Long(4));
            flusso.setKey01(form.getId());
            flusso.setKey03(key03);
            flusso.setTipoInvio(1L);
            flusso.setDataInvio(new Date());
            flusso.setJson(form.getPayload());
            flusso.setCodiceFiscaleSA(form.getStazioneAppaltante());
            flusso.setAutore(this.sqlMapper.getNameBySyscon(syscon));
            this.programmiMapper.insertFlusso(flusso);

        } catch (Exception e) {
            logger.error("Errore inaspettato durante update id_generato: SA=" + form.getStazioneAppaltante() + " Id="
                    + form.getId(), e);
            throw (e);
        }
        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResponse updateIdRicevutoFS(ProgrammaPubblicatoForm form, Long syscon) throws Exception {
        BaseResponse risultato = new BaseResponse();
        risultato.setEsito(true);
        try {

            this.programmiMapper.updateIdRicevuto(form);
            // inserisco flusso
            FlussoEntry flusso = new FlussoEntry();
            Long idFlusso = this.wgcManager.getNextId("W9FLUSSI");
            Long key03 = new Long(981);
            flusso.setId(idFlusso);
            flusso.setArea(new Long(4));
            flusso.setKey01(form.getId());
            flusso.setKey03(key03);
            flusso.setTipoInvio(1L);
            flusso.setDataInvio(new Date());
            flusso.setJson(form.getPayload());
            flusso.setCodiceFiscaleSA(form.getStazioneAppaltante());
            flusso.setAutore(this.sqlMapper.getNameBySyscon(syscon));
            this.programmiMapper.insertFlusso(flusso);

        } catch (Exception e) {
            logger.error("Errore inaspettato durante update id_generato: SA=" + form.getStazioneAppaltante() + " Id="
                    + form.getId(), e);
            throw (e);
        }
        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResponse updateIdRicevuto(ProgrammaPubblicatoForm form, Long syscon) throws Exception {
        BaseResponse risultato = new BaseResponse();
        risultato.setEsito(true);
        try {

            this.programmiMapper.updateIdRicevuto(form);
            // inserisco flusso
            FlussoEntry flusso = new FlussoEntry();
            Long idFlusso = this.wgcManager.getNextId("W9FLUSSI");
            Long key03 = new Long(982);
            flusso.setId(idFlusso);
            flusso.setArea(new Long(4));
            flusso.setKey01(form.getId());
            flusso.setKey03(key03);
            flusso.setTipoInvio(1L);
            flusso.setDataInvio(new Date());
            flusso.setJson(form.getPayload());
            flusso.setCodiceFiscaleSA(form.getStazioneAppaltante());
            flusso.setAutore(this.sqlMapper.getNameBySyscon(syscon));
            this.programmiMapper.insertFlusso(flusso);

        } catch (Exception e) {
            logger.error("Errore inaspettato durante update id_generato: SA=" + form.getStazioneAppaltante() + " Id="
                    + form.getId(), e);
            throw (e);
        }
        return risultato;
    }

    /**
     * INTERVENTI
     */

    public ResponseDettaglioIntervento getIntervento(Long contri, Long conint) {

        ResponseDettaglioIntervento risultato = new ResponseDettaglioIntervento();
        try {
            InterventoEntry intervento = this.programmiMapper.getIntervento(contri, conint);
            if (intervento != null) {
                List<ImmobileEntry> immobili = this.programmiMapper.getImmobili(contri, conint, null);

                if (intervento.getCodiceRup() != null) {
                    RupEntry rupEntry = this.programmiMapper.getRup(intervento.getCodiceRup());
                    intervento.setRupEntry(rupEntry);
                }
                intervento.setImmobili(immobili);
                risultato.setData(intervento);
                risultato.setEsito(true);
            } else {
                risultato.setErrorData(BaseResponse.ERROR_NOT_FOUND);
                risultato.setEsito(false);
            }

        } catch (Throwable t) {
            logger.error("Errore inaspettato durante l'estrazione dell'intervento con contri:" + contri + ", conint:"
                    + conint, t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResponse deleteIntervento(Long contri, Long conint) throws Exception {
        BaseResponse risultato = new BaseResponse();
        risultato.setEsito(true);
        try {
            this.programmiMapper.deleteIntervento(contri, conint);
            this.programmiMapper.deleteImmobiliDiInterventi(contri, conint);
            this.programmiMapper.deleteRisorseDiCapitoloIntervento(contri, conint);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la cancellazione dell'intervento. contri:" + contri + ", conint:"
                    + conint, e);
            throw (e);
        }

        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResponseInsert insertIntervento(InterventoInsertForm form) throws Throwable {
        ResponseInsert risultato = new ResponseInsert();
        try {
            Long conint = this.programmiMapper.getMaxConint(form.getIdProgramma(), "inttri");
            if (conint == null) {
                conint = 1L;
            } else {
                conint = conint + 1;
            }

            // Valori di default: se non valorizzato coperturaFinanziaria = 1
            String coperturaFinanziaria = form.getCoperturaFinanziaria() == null ? "1" : form.getCoperturaFinanziaria();
            form.setCoperturaFinanziaria(coperturaFinanziaria);
            // Valori di default: se non valorizzato in piano annuale = 1 (se annrif = 1 e
            // copfin !=2) o 2 (altirmenti)
            if (form.getInPianoAnnuale() == null) {
                Long annoAvvio = form.getAnnoAvvio();
                String inPianoAnnuale = annoAvvio == 1 && !coperturaFinanziaria.equals("2") ? "1" : "2";
                form.setInPianoAnnuale(inPianoAnnuale);
            }

            // Valori di default: se non valorizzato acqVerdi = 1
            form.setAcqVerdi(form.getAcqVerdi() == null ? 1L : form.getAcqVerdi());

            // Valori di default: se non valorizzato AcquistoBenRiciclati = 1
            form.setAcquistoBeniRiciclati(
                    form.getAcquistoBeniRiciclati() == null ? 1L : form.getAcquistoBeniRiciclati());

            form.setId(conint);
            form.setNumeroCui(calcolaCUIIntervento(form));
            form.setNumProgetto(conint);

            double importoCapitaleTot = calcolaImportoCapitaleTot(form);
            form.setTotaleCapitalePriv(importoCapitaleTot);

            pulisciInterventoTab(form);
            this.programmiMapper.insertIntervento(form);
            for (ImmobileInsertForm immobile : form.getImmobili()) {
                try {
                    String codiceUnivocoImmobile = getCodiceUnivocoImmobile(form.getIdProgramma());
                    immobile.setCui(codiceUnivocoImmobile);
                    Long numimm = this.programmiMapper.getMaxNumimm(form.getIdProgramma(), form.getId(), null);
                    if (numimm == null) {
                        numimm = 1L;
                    } else {
                        numimm = numimm + 1;
                    }
                    immobile.setIdProgramma(form.getIdProgramma());
                    immobile.setIdIntervento(form.getId());
                    immobile.setId(numimm);
                    this.programmiMapper.insertImmobile(immobile);
                } catch (Exception ex) {
                    logger.error("Errore inaspettato durante la creazione dell'immobile " + immobile.getId()
                            + " per l' intervento." + form.getIdProgramma(), ex);
                    throw (ex);
                }
            }
            risultato.setData(form.getId());
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error(
                    "Errore inaspettato durante la creazione dell'intevento per il programma." + form.getIdProgramma(),
                    t);
            throw t;
        }
        return risultato;
    }

    private void pulisciInterventoTab(InterventoInsertForm form) {

        if (form.getDescrizione() != null) {
            form.setDescrizione(form.getDescrizione().replaceAll("[\t ]+", " "));
        }
        if (form.getInPianoAnnuale() != null) {
            form.setInPianoAnnuale(form.getInPianoAnnuale().replaceAll("[\t ]+", " "));
        }
        if (form.getSettore() != null) {
            form.setSettore(form.getSettore().replaceAll("[\t ]+", " "));
        }
        if (form.getCodiceInterno() != null) {
            form.setCodiceInterno(form.getCodiceInterno().replaceAll("[\t ]+", " "));
        }
        if (form.getEsenteCup() != null) {
            form.setEsenteCup(form.getEsenteCup().replaceAll("[\t ]+", " "));
        }
        if (form.getCodiceCup() != null) {
            form.setCodiceCup(form.getCodiceCup().replaceAll("[\t ]+", " "));
        }
        if (form.getCuiCollegato() != null) {
            form.setCuiCollegato(form.getCuiCollegato().replaceAll("[\t ]+", " "));
        }
        if (form.getCodIstatComune() != null) {
            form.setCodIstatComune(form.getCodIstatComune().replaceAll("[\t ]+", " "));
        }
        if (form.getCodiceNuts() != null) {
            form.setCodiceNuts(form.getCodiceNuts().replaceAll("[\t ]+", " "));
        }
        if (form.getCodCpv() != null) {
            form.setCodCpv(form.getCodCpv().replaceAll("[\t ]+", " "));
        }
        if (form.getCodiceRup() != null) {
            form.setCodiceRup(form.getCodiceRup().replaceAll("[\t ]+", " "));
        }
        if (form.getDirezioneGenerale() != null) {
            form.setDirezioneGenerale(form.getDirezioneGenerale().replaceAll("[\t ]+", " "));
        }
        if (form.getStrutturaOperativa() != null) {
            form.setStrutturaOperativa(form.getStrutturaOperativa().replaceAll("[\t ]+", " "));
        }
        if (form.getDirigenteUfficio() != null) {
            form.setDirigenteUfficio(form.getDirigenteUfficio().replaceAll("[\t ]+", " "));
        }
        if (form.getLottoFunzionale() != null) {
            form.setLottoFunzionale(form.getLottoFunzionale().replaceAll("[\t ]+", " "));
        }
        if (form.getLavoroComplesso() != null) {
            form.setLavoroComplesso(form.getLavoroComplesso().replaceAll("[\t ]+", " "));
        }
        if (form.getNuovoAffidamento() != null) {
            form.setNuovoAffidamento(form.getNuovoAffidamento().replaceAll("[\t ]+", " "));
        }
        if (form.getClassificazioneIntervento() != null) {
            form.setClassificazioneIntervento(form.getClassificazioneIntervento().replaceAll("[\t ]+", " "));
        }
        if (form.getCategoriaIntervento() != null) {
            form.setCategoriaIntervento(form.getCategoriaIntervento().replaceAll("[\t ]+", " "));
        }
        if (form.getFinalitaIntervento() != null) {
            form.setFinalitaIntervento(form.getFinalitaIntervento().replaceAll("[\t ]+", " "));
        }
        if (form.getVerificaConfUrbanistica() != null) {
            form.setVerificaConfUrbanistica(form.getVerificaConfUrbanistica().replaceAll("[\t ]+", " "));
        }
        if (form.getVerificaConfAmbiente() != null) {
            form.setVerificaConfAmbiente(form.getVerificaConfAmbiente().replaceAll("[\t ]+", " "));
        }
        if (form.getStatoProgettazioneApprovata() != null) {
            form.setStatoProgettazioneApprovata(form.getStatoProgettazioneApprovata().replaceAll("[\t ]+", " "));
        }
        if (form.getTipologiaCapitalePrivato() != null) {
            form.setTipologiaCapitalePrivato(form.getTipologiaCapitalePrivato().replaceAll("[\t ]+", " "));
        }
        if (form.getCoperturaFinanziaria() != null) {
            form.setCoperturaFinanziaria(form.getCoperturaFinanziaria().replaceAll("[\t ]+", " "));
        }
        if (form.getNormativaRiferimento() != null) {
            form.setNormativaRiferimento(form.getNormativaRiferimento().replaceAll("[\t ]+", " "));
        }
        if (form.getOggettoAv() != null) {
            form.setOggettoAv(form.getOggettoAv().replaceAll("[\t ]+", " "));
        }
        if (form.getAvcpv() != null) {
            form.setAvcpv(form.getAvcpv().replaceAll("[\t ]+", " "));
        }
        if (form.getOggettoMr() != null) {
            form.setOggettoMr(form.getOggettoMr().replaceAll("[\t ]+", " "));
        }
        if (form.getMrcpv() != null) {
            form.setMrcpv(form.getMrcpv().replaceAll("[\t ]+", " "));
        }
        if (form.getDelegaProcAff() != null) {
            form.setDelegaProcAff(form.getDelegaProcAff().replaceAll("[\t ]+", " "));
        }
        if (form.getCodiceAusa() != null) {
            form.setCodiceAusa(form.getCodiceAusa().replaceAll("[\t ]+", " "));
        }
        if (form.getSoggettoDelegato() != null) {
            form.setSoggettoDelegato(form.getSoggettoDelegato().replaceAll("[\t ]+", " "));
        }
        if (form.getReferenteDatiComunicati() != null) {
            form.setReferenteDatiComunicati(form.getReferenteDatiComunicati().replaceAll("[\t ]+", " "));
        }
        if (form.getNote() != null) {
            form.setNote(form.getNote().replaceAll("[\t ]+", " "));
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public BaseResponse updateIntervento(InterventoInsertForm form) throws Exception {
        BaseResponse risultato = new BaseResponse();
        try {

            if (!checkNumeroCui(form.getIdProgramma(), form.getId(), form.getNumeroCui())) {
                risultato.setErrorData("Il CUI inserito è già esistente, si prega di modificarlo.");
                risultato.setEsito(false);

            } else {
                double importoCapitaleTot = calcolaImportoCapitaleTot(form);
                form.setTotaleCapitalePriv(importoCapitaleTot);
                pulisciInterventoTab(form);
                this.programmiMapper.updateIntervento(form);
                this.programmiMapper.deleteImmobiliDiInterventi(form.getIdProgramma(), form.getId());
                for (ImmobileInsertForm immobile : form.getImmobili()) {
                    if (immobile.getId() == null) {
                        Long numimm = this.programmiMapper.getMaxNumimm(form.getIdProgramma(), form.getId(), null);
                        if (numimm == null) {
                            numimm = 1L;
                        } else {
                            numimm = numimm + 1;
                        }
                        String codiceUnivocoImmobile = getCodiceUnivocoImmobile(form.getIdProgramma());
                        immobile.setCui(codiceUnivocoImmobile);
                        immobile.setId(numimm);
                    }
                    immobile.setIdProgramma(form.getIdProgramma());
                    immobile.setIdOpera(null);
                    immobile.setIdIntervento(form.getId());
                    if (checkValidCUIImmobile(form.getIdProgramma(), immobile.getCui())) {
                        this.programmiMapper.insertImmobile(immobile);
                    } else {
                        throw new Exception("Il CUI scelto per l'immobile è già esistente: " + immobile.getCui());
                    }

                }
                risultato.setEsito(true);
            }
        } catch (Exception e) {
            logger.error("Errore inaspettato durante l'update dell'intervento. contri:" + form.getIdProgramma()
                    + " idOpera:" + form.getId(), e);
            throw e;
        }

        return risultato;
    }

    private double calcolaImportoCapitaleTot(InterventoInsertForm form) {
        double result = 0d;
        if (form.getImportoCapPriv1() != null) {
            result = result + form.getImportoCapPriv1();
        }
        if (form.getImportoCapPriv2() != null) {
            result = result + form.getImportoCapPriv2();
        }
        if (form.getImportoCapPriv3() != null) {
            result = result + form.getImportoCapPriv3();
        }
        if (form.getImportoCapPrivSucc() != null) {
            result = result + form.getImportoCapPrivSucc();
        }

        return result;
    }

    public ResponseDettaglioInterventoNR getInterventoNonRiproposto(Long contri, Long conint) {

        ResponseDettaglioInterventoNR risultato = new ResponseDettaglioInterventoNR();
        try {
            InterventoNonRipropostoEntry intervento = this.programmiMapper.getInterventoNonRiproposto(contri, conint);
            risultato.setData(intervento);

        } catch (Throwable t) {
            logger.error("Errore inaspettato durante l'estrazione dell'intervento non riproposto con contri:" + contri
                    + ", conint:" + conint, t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return risultato;
    }

    private String calcolaCUIIntervento(InterventoInsertForm form) {

        ProgrammaEntry programma = this.programmiMapper.getProgramma(form.getIdProgramma());
        String codeinAmministrazione = programma.getStazioneappaltante();
        String cfAmministrazione = this.programmiMapper.getCFSAByCode(codeinAmministrazione);
        String tipoIn = form.getSettore();
        Long anntri = programma.getAnnoInizio();
        String cuiPrefix = tipoIn.toUpperCase() + cfAmministrazione.toUpperCase() + anntri;
        List<String> existingCui = this.programmiMapper.getExistingCui(cuiPrefix, codeinAmministrazione);
        Long counter = 1L;
        if (existingCui == null || existingCui.isEmpty()) {
            return cuiPrefix + "00001";
        } else {
            Set<Long> counterSet = new HashSet<Long>();
            for (String cui : existingCui) {
                Long existingCounter = Long.parseLong(cui.substring(cui.length() - 5));
                counterSet.add(existingCounter);
            }
            while (counterSet.contains(counter)) {
                counter++;
            }
            return cuiPrefix + StringUtils.leftPad(counter.toString(), 5, "0");
        }
    }

    private String getCodiceUnivocoImmobile(Long contri) {

        ProgrammaEntry programma = this.programmiMapper.getProgramma(contri);
        String codeinAmministrazione = programma.getStazioneappaltante();
        String cfAmministrazione = this.programmiMapper.getCFSAByCode(codeinAmministrazione);
        Long anntri = programma.getAnnoInizio();
        String cuiPrefix = 'I' + cfAmministrazione.toUpperCase() + anntri;
        List<String> existingCui = this.programmiMapper.getExistingCuiImmtrai(cuiPrefix);
        Long counter = 1L;
        if (existingCui == null || existingCui.isEmpty()) {
            return cuiPrefix + "00001";
        } else {
            Set<Long> counterSet = new HashSet<Long>();
            for (String cui : existingCui) {
                Long existingCounter = Long.parseLong(cui.substring(cui.length() - 5));
                counterSet.add(existingCounter);
            }
            while (counterSet.contains(counter)) {
                counter++;
            }
            return cuiPrefix + StringUtils.leftPad(counter.toString(), 5, "0");
        }
    }

    private boolean checkNumeroCui(Long contri, Long conint, String cui) {

        try {
            ProgrammaEntry programma = this.programmiMapper.getProgramma(contri);
            InterventoEntry intervento = this.programmiMapper.getIntervento(contri, conint);
            Long anno = programma.getAnnoInizio();
            if (!cui.equals(intervento.getNumeroCui())) {
                List<Long> idProgrammiStessoAnno = this.programmiMapper.getContriByAnno(anno);
                List<String> cuiEsistenti = new ArrayList<String>();
                for (Long idProgramma : idProgrammiStessoAnno) {
                    if (idProgramma.equals(programma.getId())) {
                        cuiEsistenti.addAll(this.programmiMapper.getCuiInterventiForCheckWithExclusion(idProgramma, conint));
                    } else {
                        cuiEsistenti.addAll(this.programmiMapper.getCuiInterventiForCheck(idProgramma));
                    }
                }

                for (Long idProgramma : idProgrammiStessoAnno) {
                    cuiEsistenti.addAll(this.programmiMapper.getCuiInterventiNRForCheck(idProgramma));
                }


                for (String cuiEsistente : cuiEsistenti) {
                    if (cuiEsistente != null && cuiEsistente.equals(cui)) {
                        return false;
                    }
                }


            }
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante il check del numero cui: contri =" + contri, t);
            return false;
        }
        return true;
    }

    /**
     * OPERE INCOMPIUTE
     */

    public ResponseDettaglioOperaIncompiuta getOperaIncompiuta(Long contri, Long numoi) {

        ResponseDettaglioOperaIncompiuta risultato = new ResponseDettaglioOperaIncompiuta();
        try {
            OperaIncompiutaEntry operaIncompiuta = this.programmiMapper.getOperaIncompiuta(contri, numoi);
            if (operaIncompiuta != null) {
                List<ImmobileEntry> immobili = this.programmiMapper.getImmobili(contri, null, numoi);
                operaIncompiuta.setImmobili(immobili);
                risultato.setData(operaIncompiuta);
                risultato.setEsito(true);
            } else {
                risultato.setErrorData(BaseResponse.ERROR_NOT_FOUND);
                risultato.setEsito(false);
            }
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante l'estrazione dell'opera incompiuta con contri:" + contri
                    + ", numoi:" + numoi, t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseInsert insertOperaIncompiuta(OperaIncompiutaInsertForm form) throws Exception {
        ResponseInsert risultato = new ResponseInsert();
        try {
            Long numoi = this.programmiMapper.getMaxNumoi(form.getIdProgramma());
            if (numoi == null) {
                numoi = 1L;
            } else {
                numoi = numoi + 1;
            }
            form.setId(numoi);

            if (form.getAdditionalInfo() != null) {
                Map<String, Object> additionalInfo = (Map<String, Object>) form.getAdditionalInfo();
                if (additionalInfo.containsKey("descrizione") && additionalInfo.get("descrizione") != null && form.getDescrizione() == null) {
                    form.setDescrizione(additionalInfo.get("descrizione").toString());
                }
                if (additionalInfo.containsKey("codIstat") && additionalInfo.get("codIstat") != null) {
                    form.setCodIstat(additionalInfo.get("codIstat").toString());
                }
                if (additionalInfo.containsKey("sponsorizzazione") && additionalInfo.get("sponsorizzazione") != null) {
                    form.setSponsorizzazione(additionalInfo.get("sponsorizzazione").toString());
                }
                if (additionalInfo.containsKey("finanzaProgetto") && additionalInfo.get("finanzaProgetto") != null) {
                    form.setFinanzaProgetto(additionalInfo.get("finanzaProgetto").toString());
                }
                if (additionalInfo.containsKey("costo") && additionalInfo.get("costo") != null) {
                    form.setCosto(Double.parseDouble(additionalInfo.get("costo").toString()));
                }
                if (additionalInfo.containsKey("finanziamento") && additionalInfo.get("finanziamento") != null) {
                    form.setFinanziamento(Double.parseDouble(additionalInfo.get("finanziamento").toString()));
                }
                if (additionalInfo.containsKey("statale") && additionalInfo.get("statale") != null) {
                    form.setCopStatale(additionalInfo.get("statale").toString());
                }
                if (additionalInfo.containsKey("regionale") && additionalInfo.get("regionale") != null) {
                    form.setCopRegionale(additionalInfo.get("regionale").toString());
                }
                if (additionalInfo.containsKey("provinciale") && additionalInfo.get("provinciale") != null) {
                    form.setCopProvinciale(additionalInfo.get("provinciale").toString());
                }
                if (additionalInfo.containsKey("comunale") && additionalInfo.get("comunale") != null) {
                    form.setCopComunale(additionalInfo.get("comunale").toString());
                }
                if (additionalInfo.containsKey("altrapubblica") && additionalInfo.get("altrapubblica") != null) {
                    form.setCopAltraPubblica(additionalInfo.get("altrapubblica").toString());
                }
                if (additionalInfo.containsKey("comunitaria") && additionalInfo.get("comunitaria") != null) {
                    form.setCopComunitaria(additionalInfo.get("comunitaria").toString());
                }
                if (additionalInfo.containsKey("privata") && additionalInfo.get("privata") != null) {
                    form.setCopPrivata(additionalInfo.get("privata").toString());
                }

                if (additionalInfo.containsKey("tipologia") && additionalInfo.get("tipologia") != null) {
                    form.setTipologiaIntervento(additionalInfo.get("tipologia").toString());
                }
                if (additionalInfo.containsKey("categoria") && additionalInfo.get("categoria") != null) {
                    form.setCategoriaIntervento(additionalInfo.get("categoria").toString());
                }
            }

            this.programmiMapper.insertOperaIncompiuta(form);
            for (ImmobileInsertForm immobile : form.getImmobili()) {
                try {
                    Long numimm = this.programmiMapper.getMaxNumimm(form.getIdProgramma(), 0L, null);
                    if (numimm == null) {
                        numimm = 1L;
                    } else {
                        numimm = numimm + 1;
                    }
                    String codiceUnivocoImmobile = getCodiceUnivocoImmobile(form.getIdProgramma());
                    immobile.setCui(codiceUnivocoImmobile);
                    immobile.setIdProgramma(form.getIdProgramma());
                    immobile.setId(numimm);
                    immobile.setIdOpera(form.getId());
                    immobile.setIdIntervento(0L);
                    this.programmiMapper.insertImmobile(immobile);
                } catch (Exception ex) {
                    logger.error("Errore inaspettato durante la creazione dell'immobile " + immobile.getId()
                            + " per l' intervento." + form.getIdProgramma(), ex);
                    throw (ex);
                }
            }
            risultato.setData(form.getId());
            risultato.setEsito(true);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la creazione dell'opera incompiuta per il programma."
                    + form.getIdProgramma(), e);
            throw (e);
        }
        return risultato;
    }

    @Transactional(rollbackFor = Throwable.class)
    public BaseResponse updateOperaIncompiuta(OperaIncompiutaInsertForm form) throws Exception {
        BaseResponse risultato = new BaseResponse();
        try {
            this.programmiMapper.updateOperaIncompiuta(form);
            this.programmiMapper.deleteImmobiliDiOpereIncompiute(form.getIdProgramma(), form.getId());
            for (ImmobileInsertForm immobile : form.getImmobili()) {
                if (immobile.getId() == null) {
                    String codiceUnivocoImmobile = getCodiceUnivocoImmobile(form.getIdProgramma());
                    immobile.setCui(codiceUnivocoImmobile);
                    Long numimm = this.programmiMapper.getMaxNumimm(form.getIdProgramma(), 0L, null);
                    if (numimm == null) {
                        numimm = 1L;
                    } else {
                        numimm = numimm + 1;
                    }
                    immobile.setId(numimm);

                }
                immobile.setIdProgramma(form.getIdProgramma());
                immobile.setIdOpera(form.getId());
                immobile.setIdIntervento(0L);
                if (checkValidCUIImmobile(form.getIdProgramma(), immobile.getCui())) {
                    this.programmiMapper.insertImmobile(immobile);
                } else {
                    throw new Exception("Il CUI scelto per l'immobile è già esistente: " + immobile.getCui());
                }
            }
            risultato.setEsito(true);
        } catch (Exception e) {
            risultato.setEsito(false);
            logger.error("Errore inaspettato durante l'update dell'opera. contri:" + form.getIdProgramma() + " idOpera:"
                    + form.getId(), e);
            throw e;
        }

        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResponse deleteOperaIncompiuta(Long contri, Long numoi) throws Exception {
        BaseResponse risultato = new BaseResponse();
        risultato.setEsito(true);
        try {
            this.programmiMapper.deleteOperaIncompiuta(contri, numoi);
            this.programmiMapper.deleteImmobiliDiOpereIncompiute(contri, numoi);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la cancellazione dell'opera incompiuta. contri:" + contri
                    + ", numoi:" + numoi, e);
            throw (e);
        }

        return risultato;
    }

    private boolean checkValidCUIImmobile(Long contri, String cui) {
        return this.programmiMapper.checkImmobileCUI(contri, cui).isEmpty();
    }

    /**
     * INTERVENTI NON RIPROPOSTI
     */

    public ResponseInsert insertInterventoNonRiproposto(InterventoNonRipropostoInsertForm form) {
        ResponseInsert risultato = new ResponseInsert();
        try {

            Long conint = this.programmiMapper.getMaxConint(form.getIdProgramma(), "INRTRI");
            if (conint == null) {
                conint = 1L;
            } else {
                conint = conint + 1;
            }
            form.setIdIntervento(conint);

            this.programmiMapper.insertInterventoNonRiproposto(form);
            risultato.setData(form.getIdIntervento());
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la creazione dell'intevento non riproposto per il programma."
                    + form.getIdProgramma(), t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public BaseResponse deleteInterventoNonRiproposto(Long contri, Long conint) {
        BaseResponse risultato = new BaseResponse();
        risultato.setEsito(true);
        try {
            this.programmiMapper.deleteInterventoNonRiproposto(contri, conint);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la cancellazione del dell'intervento non riproposto. contri:"
                    + contri + ", conint:" + conint, t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    public BaseResponse updateInterventoNonRiproposto(InterventoNonRipropostoUpdateForm form) {
        BaseResponse risultato = new ResponseInsert();
        try {
            ProgrammaEntry programma = this.programmiMapper.getProgramma(form.getIdProgramma());
            // check esistenza programma anno precedente per la stessa tipologia e stazione
            // appaltante
            Long tipoProgramma = programma.getTipoProg();
            String codiceStazioneAppaltante = programma.getStazioneappaltante();
            Long annoInizio = programma.getAnnoInizio();
            if (annoInizio != null) {
                Long annoPrecedente = annoInizio - 1L;
                Long check = this.programmiMapper.checkExistsProgrammaByTipoAndSAAndAnno(tipoProgramma,
                        codiceStazioneAppaltante, annoPrecedente);
                programma.setExistProgrammaAnnoPrecedente(check != null && check > 0L);
            }
            if (programma.isExistProgrammaAnnoPrecedente()) {
                this.programmiMapper.updateInterventoNonRiproposto(form);
            } else {
                this.programmiMapper.updateInterventoNonRipropostoComplete(form);
            }
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante lupdate di intervento non riproposto. contri:"
                    + form.getIdProgramma() + ", conint:" + form.getIdIntervento(), t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseInterventiDaRiproporre getListaInterventiNRToImport(Long idProgramma) {
        ResponseInterventiDaRiproporre risultato = new ResponseInterventiDaRiproporre();
        try {
            List<String> cuiAttualiList = new ArrayList<String>();
            List<InterventiDaRiproporre> interventiAnnoPrec = new ArrayList<InterventiDaRiproporre>();
            List<InterventiDaRiproporre> interventiDaImportare = new ArrayList<InterventiDaRiproporre>();

            // Prendo programma anno.
            ProgrammaEntry programmaAttuale = this.programmiMapper.getProgramma(idProgramma);
            // Prendo cui interventi per programma
            cuiAttualiList.addAll(this.programmiMapper.getCuiInterventiProgramma(idProgramma));
            // prendo cui interventi NP per programma
            cuiAttualiList.addAll(this.programmiMapper.getCuiInterventiNRProgramma(idProgramma));
            // Prendo programmi anno -1
            String stazioneAppaltante = programmaAttuale.getStazioneappaltante();
            Long tipologia = programmaAttuale.getTipoProg();
            Long anno = programmaAttuale.getAnnoInizio().longValue() - 1;
            List<Long> idProgrammi = this.programmiMapper.getIdProgrammiByAnno(stazioneAppaltante, tipologia, anno);
            // Prendo cui interventi per anno -1
            for (Long id : idProgrammi) {
                interventiAnnoPrec.addAll(this.programmiMapper.getInterventiDaRiproporreAnno1(id));
            }
            // filtro cui
            for (InterventiDaRiproporre interv : interventiAnnoPrec) {
                if (!cuiAttualiList.contains(interv.getCui())) {
                    interv.setAnno(anno);
                    interventiDaImportare.add(interv);
                    cuiAttualiList.add(interv.getCui());
                }
            }
            risultato.setData(interventiDaImportare);
        } catch (Throwable t) {
            logger.error(
                    "Errore inaspettato durante la ricerca degli interventi da importare tra i non riproposti per il programma "
                            + idProgramma,
                    t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseInterventiDaRiproporre getListaInterventiToImport(Long idProgramma) {
        ResponseInterventiDaRiproporre risultato = new ResponseInterventiDaRiproporre();
        try {
            List<String> cuiAttualiList = new ArrayList<String>();
            List<InterventiDaRiproporre> interventiAnnoPrec = new ArrayList<InterventiDaRiproporre>();
            List<InterventiDaRiproporre> interventiDaImportare = new ArrayList<InterventiDaRiproporre>();

            // Prendo programma anno.
            ProgrammaEntry programmaAttuale = this.programmiMapper.getProgramma(idProgramma);
            // Prendo cui interventi per programma
            cuiAttualiList.addAll(this.programmiMapper.getCuiInterventiProgramma(idProgramma));
            // prendo cui interventi NR per programma
            cuiAttualiList.addAll(this.programmiMapper.getCuiInterventiNRProgramma(idProgramma));
            // Prendo programmi anno -1
            String stazioneAppaltante = programmaAttuale.getStazioneappaltante();
            Long tipologia = programmaAttuale.getTipoProg();
            Long anno = programmaAttuale.getAnnoInizio().longValue() - 1;
            List<Long> idProgrammi = this.programmiMapper.getIdProgrammiByAnno(stazioneAppaltante, tipologia, anno);
            // Prendo cui interventi per anno -1
            for (Long id : idProgrammi) {
                interventiAnnoPrec.addAll(getInterventiDaRiproporre(id));
            }
            // filtro cui
            for (InterventiDaRiproporre interv : interventiAnnoPrec) {
                if (!cuiAttualiList.contains(interv.getCui())) {
                    interv.setAnno(anno);
                    interventiDaImportare.add(interv);
                }
            }
            risultato.setData(interventiDaImportare);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la ricerca degli interventi da importare per il programma "
                    + idProgramma, t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    private List<InterventiDaRiproporre> getInterventiDaRiproporre(Long contri) {
        List<InterventiDaRiproporre> risultato = new ArrayList<InterventiDaRiproporre>();
        Set<String> cuiInterventi = new HashSet<String>();
        try {
            List<InterventiDaRiproporre> interventiProgramma = this.programmiMapper.getInterventiDaRiproporre(contri);
            Map<String, Long> anniCui = new HashMap<String, Long>();
            for (InterventiDaRiproporre intervento : interventiProgramma) {
                cuiInterventi.add(intervento.getCui());
                if (anniCui.containsKey(intervento.getCui())) {
                    if (anniCui.get(intervento.getCui()) < intervento.getAnnoAvvio()) {
                        anniCui.put(intervento.getCui(), intervento.getAnnoAvvio());
                    }
                } else {
                    anniCui.put(intervento.getCui(), intervento.getAnnoAvvio());
                }
            }

            for (InterventiDaRiproporre intervento : interventiProgramma) {
                if (anniCui.get(intervento.getCui()) == intervento.getAnnoAvvio()) {
                    risultato.add(intervento);
                }
            }
        } catch (Exception ex) {
            logger.error(
                    "Errore inaspettato durante la ricerca degli interventi da importare per il programma " + contri,
                    ex);
            risultato = new ArrayList<InterventiDaRiproporre>();
        }
        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public BaseResponse importaInterventiNonRip(ImportInterventiForm form) throws Throwable {
        BaseResponse risultato = new BaseResponse();
        try {
            for (ChiaviIntervento chiavi : form.getInterventiToImport()) {
                InterventoEntry intervento = this.programmiMapper.getIntervento(chiavi.getIdProgramma(),
                        chiavi.getIdIntervento());
                InterventoNonRipropostoInsertForm formInsert = new InterventoNonRipropostoInsertForm();
                formInsert.setCup(intervento.getCodiceCup());
                formInsert.setDescrizione(intervento.getDescrizione());
                formInsert.setIdProgramma(form.getIdProgramma());
                formInsert.setImportoComplessivo(intervento.getImportoTotale());
                formInsert.setPriorita(intervento.getLivelloPriorita());
                Long conint = this.programmiMapper.getMaxConint(form.getIdProgramma(), "INRTRI");
                if (conint == null) {
                    conint = 1L;
                } else {
                    conint = conint + 1;
                }
                formInsert.setIdIntervento(conint);
                if (intervento.getNumeroCui() == null || "".equals(intervento.getNumeroCui())) {

                    String numeroCui = getNumeroCuiDaAggiornare(intervento, chiavi);
                    intervento.setNumeroCui(numeroCui);
                }
                formInsert.setCui(intervento.getNumeroCui());
                this.programmiMapper.insertInterventoNonRiproposto(formInsert);
            }

        } catch (Throwable t) {
            logger.error("Errore inaspettato durante l'import degli interventi non riproposti per il programma "
                    + form.getIdProgramma(), t);
            throw t;
        }
        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public BaseResponse importaInterventi(ImportInterventiForm form) throws Throwable {
        BaseResponse risultato = new BaseResponse();
        try {
            String choice = form.getChoice();
            for (ChiaviIntervento chiavi : form.getInterventiToImport()) {
                InterventoEntry intervento = this.programmiMapper.getIntervento(chiavi.getIdProgramma(),
                        chiavi.getIdIntervento());
                intervento.setIdProgramma(form.getIdProgramma());
                Long conint = this.programmiMapper.getMaxConint(form.getIdProgramma(), "inttri");
                if (conint == null) {
                    conint = 1L;
                } else {
                    conint = conint + 1;
                }
                intervento.setId(conint);
                if (intervento.getNumeroCui() == null || "".equals(intervento.getNumeroCui())) {

                    String numeroCui = getNumeroCuiDaAggiornare(intervento, chiavi);
                    intervento.setNumeroCui(numeroCui);
                }

                if ("1".equals(choice)) {
                    intervento.setImpDestVincolo1(null);
                    intervento.setImpDestVincolo2(null);
                    intervento.setImpDestVincolo3(null);
                    intervento.setImpDestVincoloSucc(null);
                    intervento.setImportoAcqMututo1(null);
                    intervento.setImportoAcqMututo2(null);
                    intervento.setImportoAcqMututo3(null);
                    intervento.setImportoAcqMututoSucc(null);
                    intervento.setImportoCapPriv1(null);
                    intervento.setImportoCapPriv2(null);
                    intervento.setImportoCapPriv3(null);
                    intervento.setImportoCapPrivSucc(null);
                    intervento.setStanziamentiBilancio1(null);
                    intervento.setStanziamentiBilancio2(null);
                    intervento.setStanziamentiBilancio3(null);
                    intervento.setStanziamentiBilancioSucc(null);
                    intervento.setImportoFinanz1(null);
                    intervento.setImportoFinanz2(null);
                    intervento.setImportoFinanz3(null);
                    intervento.setImportoFinanzSucc(null);
                    intervento.setTrasfImmobili1(null);
                    intervento.setTrasfImmobili2(null);
                    intervento.setTrasfImmobili3(null);
                    intervento.setTrasfImmobiliSucc(null);
                    intervento.setAltreRisorseDisp1(null);
                    intervento.setAltreRisorseDisp2(null);
                    intervento.setAltreRisorseDisp3(null);
                    intervento.setAltreRisorseDispSucc(null);
                    intervento.setImportoDispPriv1(null);
                    intervento.setImportoDispPriv2(null);
                    intervento.setImportoDispPriv3(null);
                    intervento.setImportoDispPrivSucc(null);
                    intervento.setImportoIva1(null);
                    intervento.setImportoTotale(null);
                    intervento.setImportoTotaleAv(null);
                    intervento.setImportoTotaleMr(null);
                    intervento.setTotImpDispFin(null);
                } else if ("3".equals(choice)) {
                    intervento.setImpDestVincolo1(sommaValori(intervento.getImpDestVincolo1(), intervento.getImpDestVincolo2()));
                    intervento.setImpDestVincolo2(intervento.getImpDestVincolo3());
                    intervento.setImpDestVincolo3(intervento.getImpDestVincoloSucc());
                    intervento.setImpDestVincoloSucc(null);
                    intervento.setImportoAcqMututo1(sommaValori(intervento.getImportoAcqMututo1(), intervento.getImportoAcqMututo2()));
                    intervento.setImportoAcqMututo2(intervento.getImportoAcqMututo3());
                    intervento.setImportoAcqMututo3(intervento.getImportoAcqMututoSucc());
                    intervento.setImportoAcqMututoSucc(null);
                    intervento.setImportoCapPriv1(sommaValori(intervento.getImportoCapPriv1(), intervento.getImportoCapPriv2()));
                    intervento.setImportoCapPriv2(intervento.getImportoCapPriv3());
                    intervento.setImportoCapPriv3(intervento.getImportoCapPrivSucc());
                    intervento.setImportoCapPrivSucc(null);
                    intervento.setStanziamentiBilancio1(sommaValori(intervento.getStanziamentiBilancio1(), intervento.getStanziamentiBilancio2()));
                    intervento.setStanziamentiBilancio2(intervento.getStanziamentiBilancio3());
                    intervento.setStanziamentiBilancio3(intervento.getStanziamentiBilancioSucc());
                    intervento.setStanziamentiBilancioSucc(null);
                    intervento.setImportoFinanz1(sommaValori(intervento.getImportoFinanz1(), intervento.getImportoFinanz2()));
                    intervento.setImportoFinanz2(intervento.getImportoFinanz3());
                    intervento.setImportoFinanz3(intervento.getImportoFinanzSucc());
                    intervento.setImportoFinanzSucc(null);
                    intervento.setTrasfImmobili1(sommaValori(intervento.getTrasfImmobili1(), intervento.getTrasfImmobili2()));
                    intervento.setTrasfImmobili2(intervento.getTrasfImmobili3());
                    intervento.setTrasfImmobili3(intervento.getTrasfImmobiliSucc());
                    intervento.setTrasfImmobiliSucc(null);
                    intervento.setAltreRisorseDisp1(sommaValori(intervento.getAltreRisorseDisp1(), intervento.getAltreRisorseDisp2()));
                    intervento.setAltreRisorseDisp2(intervento.getAltreRisorseDisp3());
                    intervento.setAltreRisorseDisp3(intervento.getAltreRisorseDispSucc());
                    intervento.setAltreRisorseDispSucc(null);
                    intervento.setImportoDispPriv1(sommaValori(intervento.getImportoDispPriv1(), intervento.getImportoDispPriv2()));
                    intervento.setImportoDispPriv2(intervento.getImportoDispPriv3());
                    intervento.setImportoDispPriv3(intervento.getImportoDispPrivSucc());
                    intervento.setImportoDispPrivSucc(null);
                    intervento.setImportoIva1(sommaValori(intervento.getImportoIva1(), intervento.getImportoIva2()));
                }

                this.programmiMapper.importIntervento(intervento);
                List<ImmobileEntry> immobili = this.programmiMapper.getImmobili(chiavi.getIdProgramma(), chiavi.getIdIntervento(), null);
                intervento.setImmobili(immobili);
                for (ImmobileEntry immobile : intervento.getImmobili()) {
                    try {
                        Long numimm = this.programmiMapper.getMaxNumimm(form.getIdProgramma(), conint, null);
                        if (numimm == null) {
                            numimm = 1L;
                        } else {
                            numimm = numimm + 1;
                        }
                        immobile.setIdProgramma(form.getIdProgramma());
                        immobile.setIdIntervento(conint);
                        immobile.setId(numimm);
                        this.programmiMapper.importImmobile(immobile);
                    } catch (Exception ex) {
                        logger.error("Errore inaspettato durante la creazione dell'immobile " + immobile.getId()
                                + " per l' intervento." + form.getIdProgramma(), ex);
                        throw (ex);
                    }
                }

                List<RisorsaCapitoloEntry> risorseCapitolo = this.programmiMapper
                        .getAllRisorseDiCapitolo(chiavi.getIdProgramma(), chiavi.getIdIntervento());
                for (RisorsaCapitoloEntry risorsa : risorseCapitolo) {
                    try {
                        Long numcb = this.programmiMapper.getMaxNumcb(form.getIdProgramma(), conint);
                        if (numcb == null) {
                            numcb = 1L;
                        } else {
                            numcb = numcb + 1;
                        }
                        risorsa.setIdProgramma(form.getIdProgramma());
                        risorsa.setIdIntervento(conint);
                        risorsa.setId(numcb);
                        this.programmiMapper.importRisorsaDiCapitolo(risorsa);
                    } catch (Exception ex) {
                        logger.error("Errore inaspettato durante la creazione della risorsa di capitolo "
                                + risorsa.getId() + " per l' intervento." + conint, ex);
                        throw (ex);
                    }
                }

            }
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante l'import degli interventi non riproposti per il programma "
                    + form.getIdProgramma(), t);
            throw t;
        }
        return risultato;
    }

    private Double sommaValori(Double valore1, Double valore2) {
        if (valore1 == null) {
            valore1 = 0d;
        }
        if (valore2 == null) {
            valore2 = 0d;
        }
        return valore1 + valore2;
    }


    private String getNumeroCuiDaAggiornare(InterventoEntry intervento, ChiaviIntervento chiavi) {

        String numeroCui = "";
        ProgrammaEntry programma = this.programmiMapper.getProgramma(chiavi.getIdProgramma());
        String codeinAmministrazione = programma.getStazioneappaltante();
        String cfAmministrazione = this.programmiMapper.getCFSAByCode(codeinAmministrazione);
        String tipoIn = intervento.getSettore();
        Long anntri = programma.getAnnoInizio();
        String cuiPrefix = tipoIn.toUpperCase() + cfAmministrazione.toUpperCase() + anntri;
        List<String> existingCui = this.programmiMapper.getExistingCui(cuiPrefix, codeinAmministrazione);
        Long counter = 1L;
        if (existingCui == null || existingCui.isEmpty()) {
            numeroCui = cuiPrefix + "00001";
        } else {
            Set<Long> counterSet = new HashSet<Long>();
            for (String cui : existingCui) {
                Long existingCounter = Long.parseLong(cui.substring(cui.length() - 5));
                counterSet.add(existingCounter);
            }
            while (counterSet.contains(counter)) {
                counter++;
            }
            numeroCui = cuiPrefix + StringUtils.leftPad(counter.toString(), 5, "0");
        }
        this.programmiMapper.updateNumeroCui(numeroCui, programma.getId(), chiavi.getIdIntervento());

        return numeroCui;
    }

    /**
     * RISORSE DI CAPITOLO
     */

    public ResponseInsert insertRisorsaDiCapitolo(RisorsaDiCapitoloInsertForm form) {
        ResponseInsert risultato = new ResponseInsert();
        try {
            Long numcb = this.programmiMapper.getMaxNumcb(form.getIdProgramma(), form.getIdIntervento());
            if (numcb == null) {
                numcb = 1L;
            } else {
                numcb = numcb + 1;
            }
            form.setId(numcb);
            this.programmiMapper.insertRisorsaDiCapitolo(form);
            risultato.setData(form.getId());
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la creazione della risorsa di capitolo. contri:"
                    + form.getIdIntervento() + ", conint:" + form.getIdProgramma(), t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public BaseResponse updateRisorsaDiCapitolo(RisorsaDiCapitoloInsertForm form) {
        ResponseInsert risultato = new ResponseInsert();
        try {
            this.programmiMapper.updateRisorsaDiCapitolo(form);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la modifica della risorsa di capitolo. contri:"
                    + form.getIdIntervento() + ", conint:" + form.getIdProgramma(), t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseDettaglioRisorsaCapitolo getRisorsaDiCapitolo(Long contri, Long conint, Long numcb) {
        ResponseDettaglioRisorsaCapitolo risultato = new ResponseDettaglioRisorsaCapitolo();
        try {

            RisorsaCapitoloEntry risorsaCapitolo = this.programmiMapper.getRisorsaDiCapitolo(contri, conint, numcb);

            risultato.setData(risorsaCapitolo);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la get della risorsa di capitolo. contri:" + contri + ", conint:"
                    + conint + ", numcb:" + numcb, t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseListaRisorsaCapitolo getRisorseDiCapitolo(RisorseCapitoloSearchForm searchForm) {
        ResponseListaRisorsaCapitolo risultato = new ResponseListaRisorsaCapitolo();
        try {

            int totalCount = this.programmiMapper.countSearchRisorseDiCapitolo(searchForm);
            RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
            risultato.setTotalCount(totalCount);
            List<RisorsaCapitoloBaseEntry> risorseCapitolo = this.programmiMapper.getRisorseDiCapitolo(searchForm,
                    rowBounds);

            risultato.setData(risorseCapitolo);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la get della risorsa di capitolo. contri:"
                    + searchForm.getIdProgramma() + ", conint:" + searchForm.getIdIntervento(), t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public BaseResponse deleteRisorsaDiCapitolo(Long contri, Long conint, Long numcb) {
        BaseResponse risultato = new ResponseInsert();
        try {
            this.programmiMapper.deleteRisorsaDiCapitolo(contri, conint, numcb);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la creazione della risorsa di capitolo. contri:" + contri
                    + ", conint:" + conint + ", numcb:" + numcb, t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponsePubblicazioni getPubblicazioni(String cf, String contri) {
        ResponsePubblicazioni risultato = new ResponsePubblicazioni();
        try {
            List<FlussiProgrammi> pubb = this.programmiMapper.getPubblicazioni(cf, Long.valueOf(contri));
            risultato.setData(pubb);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la ricerca della lista pubblicazioni: contri= " + contri
                    + " cfSA= " + cf, t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponsePubblicaLavori getProgrammaLavoriForPublish(Long contri, Long syscon) {
        ResponsePubblicaLavori risultato = new ResponsePubblicaLavori();
        try {

            ProgrammaEntry programma = this.programmiMapper.getProgramma(contri);
            String cfsa = this.programmiMapper.getCFSAByCode(programma.getStazioneappaltante());
            programma.setStazioneappaltante(cfsa);


            RupEntry rupProgramma = this.programmiMapper.getRup(programma.getResponsabile());
            programma.setRup(rupProgramma);

            if (programma.getIdUfficio() != null) {
                programma.setUfficio(this.programmiMapper.getUff(programma.getIdUfficio()));
            }

            List<InterventoEntry> interventi = this.programmiMapper.getInterventiProgramma(contri);
            List<InterventoNonRipropostoEntry> intyerventiNR = this.programmiMapper.getInterventiNRProgramma(contri);
            List<OperaIncompiutaEntry> opereIncompiute = this.programmiMapper.getOpereIncompiuteProgramma(contri);

            for (InterventoEntry intervento : interventi) {
                List<ImmobileEntry> immobili = this.programmiMapper.getImmobili(contri, intervento.getId(), null);
                intervento.setImmobili(immobili);
                RupEntry rupIntervento = this.programmiMapper.getRup(intervento.getCodiceRup());
                intervento.setRupEntry(rupIntervento);
            }

            for (OperaIncompiutaEntry opera : opereIncompiute) {
                List<ImmobileEntry> immobili = this.programmiMapper.getImmobili(contri, null, opera.getId());
                opera.setImmobili(immobili);
            }

            PubblicaProgrammaLavoriEntry pubblicazioneEntry = this.mappingForPubLavori(programma, interventi,
                    intyerventiNR, opereIncompiute);
            risultato.setData(pubblicazioneEntry);
            pubblicazioneEntry.setAutore(sqlMapper.getNameBySyscon(syscon));
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la ricerca dei dati di pubblicazione: contri= " + contri);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return risultato;

    }

    private PubblicaProgrammaLavoriEntry mappingForPubLavori(ProgrammaEntry p, List<InterventoEntry> i,
                                                             List<InterventoNonRipropostoEntry> inr, List<OperaIncompiutaEntry> o) {

        List<OperaIncompiutaPubbEntry> operePubb = mappingOpereForPubb(o);
        List<InterventoPubbEntry> intervPubb = mappingInterventiForPubb(i);
        List<InterventoNonRipropostoPubbEntry> intervNRPubb = mappingInterventiNRForPubb(inr);
        PubblicaProgrammaLavoriEntry programma = mappingProgrammaForPubb(p);
        programma.setInterventi(intervPubb);
        programma.setOpereIncompiute(operePubb);
        programma.setInterventiNonRiproposti(intervNRPubb);
        return programma;
    }

    private List<OperaIncompiutaPubbEntry> mappingOpereForPubb(List<OperaIncompiutaEntry> o) {
        if (o != null) {
            List<OperaIncompiutaPubbEntry> risultato = new ArrayList<OperaIncompiutaPubbEntry>();
            for (OperaIncompiutaEntry opera : o) {
                OperaIncompiutaPubbEntry out = new OperaIncompiutaPubbEntry();

                out.setAmbito(opera.getAmbitoInt());
                out.setAnno(opera.getAnnoUltimoQe());
                out.setCausa(opera.getCausa());
                out.setCessione(opera.getCessione());
                // in opera incompiuta l'idProgramma e' contri
                out.setContri(opera.getIdProgramma());
                out.setCup(opera.getCup());
                out.setDemolizione(opera.getDemolizione());
                out.setDescrizione(opera.getDescrizione());
                out.setDestinazioneUso(opera.getDestinazioneUso());
                out.setDeterminazioneAmministrazione(opera.getDeterminazioni());
                out.setFruibile(opera.getFruibile());
                out.setImmobili(mappingImmobiliForPubb(opera.getImmobili()));
                out.setImportoAvanzamento(opera.getImportoSal());
                out.setImportoIntervento(opera.getImportoInt());
                out.setImportoLavori(opera.getImportoLav());
                out.setInfrastruttura(opera.getInfrastruttura());
                out.setNumoi(opera.getId());
                out.setOneri(opera.getOneriUlt());
                out.setOneriSito(opera.getOneriSito());
                out.setPercentualeAvanzamento(opera.getAvanzamento());
                out.setPrevistaVendita(opera.getVendita());
                out.setRidimensionato(opera.getUtilizzoRid());
                out.setStato(opera.getStatoReal());
                out.setAltriDati(mappingOperaIncompiutaAltriDatiForPubb(opera));
                out.setDiscontinuitaRete(opera.getDiscontinuitaRete());
                risultato.add(out);
            }
            return risultato;
        }
        return null;
    }

    private AltriDatiOperaIncompiutaPubbEntry mappingOperaIncompiutaAltriDatiForPubb(OperaIncompiutaEntry opera) {
        if (opera != null) {
            AltriDatiOperaIncompiutaPubbEntry risultato = new AltriDatiOperaIncompiutaPubbEntry();

            risultato.setCategoriaIntervento(opera.getCategoriaIntervento());
            risultato.setContri(opera.getIdProgramma());
            risultato.setCoperturaAltro(opera.getCopAltraPubblica());
            risultato.setCoperturaComunale(opera.getCopComunale());
            risultato.setCoperturaComunitaria(opera.getCopComunitaria());
            risultato.setCoperturaPrivata(opera.getCopPrivata());
            risultato.setCoperturaProvinciale(opera.getCopProvinciale());
            risultato.setCoperturaRegionale(opera.getCopRegionale());
            risultato.setCoperturaStatale(opera.getCopStatale());
            risultato.setCostoProgetto(opera.getCosto());
            risultato.setDimensione(opera.getDimensionamentoValore());
            risultato.setFinanzaDiProgetto(opera.getFinanzaProgetto());
            risultato.setFinanziamento(opera.getFinanziamento());
            risultato.setIstat(opera.getCodIstat());
            risultato.setNumoi(opera.getId());
            risultato.setNuts(opera.getCodNuts());
            risultato.setRequisitiApprovato(opera.getRequisitiProgetto());
            risultato.setRequisitiCapitolato(opera.getRequisitiCapitolato());
            risultato.setSponsorizzazione(opera.getSponsorizzazione());
            risultato.setTipologiaIntervento(opera.getTipologiaIntervento());
            risultato.setUnitaMisura(opera.getDimensionamentoUm());

            return risultato;
        }
        return null;
    }

    private List<ImmobilePubbEntry> mappingImmobiliForPubb(List<ImmobileEntry> immobili) {
        if (immobili != null) {
            List<ImmobilePubbEntry> risultato = new ArrayList<ImmobilePubbEntry>();
            for (ImmobileEntry imm : immobili) {
                ImmobilePubbEntry out = new ImmobilePubbEntry();

                out.setAlienati(imm.getAlienati());
                out.setConint(imm.getIdIntervento());
                out.setContri(imm.getIdProgramma());
                out.setCui(imm.getCui());
                out.setDescrizione(imm.getDescrizione());
                out.setImmobileDisponibile(imm.getDirittoGodimento());
                out.setInclusoProgrammaDismissione(imm.getProgDismiss());
                out.setIstat(imm.getCodIstat());
                out.setNumimm(imm.getId());
                out.setNumoi(imm.getIdOpera());
                out.setNuts(imm.getNuts());
                out.setTipoDisponibilita(imm.getTipoDisp());
//				out.setTipoProprieta();
                out.setTrasferimentoTitoloCorrispettivo(imm.getTrasfImmCorrisp());
                out.setValoreStimato1(imm.getValStimato1());
                out.setValoreStimato2(imm.getValStimato2());
                out.setValoreStimato3(imm.getValStimato3());
                out.setValoreStimatoSucc(imm.getValAnnoSucc());

                risultato.add(out);
            }
            return risultato;
        }
        return null;
    }

    private List<InterventoPubbEntry> mappingInterventiForPubb(List<InterventoEntry> interventi) {
        List<InterventoPubbEntry> risultato = new ArrayList<InterventoPubbEntry>();
        for (InterventoEntry i : interventi) {
            InterventoPubbEntry entry = new InterventoPubbEntry();

            entry.setAcquistoMaterialiRiciclati(i.getAcquistoMaterialiRiciclati());
            if (i.getAcqVerdi() != null) {
                entry.setAcquistoVerdi(i.getAcqVerdi());
            }
            entry.setAnno(i.getAnnoAvvio());
            entry.setCategoria(i.getCategoriaIntervento());
            entry.setCodiceInterno(i.getCodiceInterno());
            entry.setCodiceRup(i.getCodiceRup());
            entry.setCodiceSoggettoDelegato(i.getCodiceAusa());
            entry.setConformitaAmbientale(i.getVerificaConfAmbiente());
            entry.setConformitaUrbanistica(i.getVerificaConfUrbanistica());
            entry.setConint(i.getId());
            entry.setContri(i.getIdProgramma());
            entry.setCoperturaFinanziaria(i.getCoperturaFinanziaria());
            entry.setCpv(i.getCodCpv());
            entry.setCpvMatRic(i.getMrcpv());
            entry.setCpvVerdi(i.getAvcpv());
            entry.setCui(i.getNumeroCui());
            entry.setCup(i.getCodiceCup());
            entry.setDelega(i.getDelegaProcAff());
            entry.setDescrizione(i.getDescrizione());
            entry.setDirezioneGenerale(i.getDirezioneGenerale());
            entry.setDirigenteResponsabile(i.getDirigenteUfficio());
            entry.setEsenteCup(i.getEsenteCup());
            entry.setFinalita(i.getFinalitaIntervento());
            if (i.getImmobili() != null) {
                entry.setImmobili(mappingImmobiliForPubb(i.getImmobili()));
            }
            entry.setImportoIvaMatRic(i.getImportoIvaMr());
            entry.setImportoIvaVerdi(i.getImportoIvaAv());
            entry.setImportoNettoIvaMatRic(i.getImportoAlnettoIvaMr());
            entry.setImportoNettoIvaVerdi(i.getImportoAlnettoIvaMr());
            entry.setImportoTotMatRic(i.getImportoTotaleMr());
            entry.setImportoTotVerdi(i.getImportoTotaleAv());
            entry.setIstat(i.getCodIstatComune());
            entry.setLavoroComplesso(i.getLavoroComplesso());
            entry.setLottoFunzionale(i.getLottoFunzionale());
            entry.setMeseAvvioProcedura(i.getMeseAvvioProc());
            entry.setNomeSoggettoDelegato(i.getSoggettoDelegato());
            entry.setNormativaRiferimento(i.getNormativaRiferimento());
            entry.setNote(i.getNote());
            entry.setNumeroProgressivo(i.getNumProgetto());
            entry.setNuts(i.getCodiceNuts());
            entry.setOggettoMatRic(i.getOggettoMr());
            entry.setOggettoVerdi(i.getOggettoAv());
            entry.setPriorita(i.getLivelloPriorita());
            entry.setProceduraAffidamento(i.getProceduraAffidamento());
            entry.setReferenteDati(i.getReferenteDatiComunicati());
            entry.setRisorseAltro1(i.getAltreRisorseDisp1());
            entry.setRisorseAltro2(i.getAltreRisorseDisp2());
            entry.setRisorseAltro3(i.getAltreRisorseDisp3());
            entry.setRisorseAltroSucc(i.getAltreRisorseDispSucc());
            entry.setRisorseArt3_1(i.getImportoFinanz1());
            entry.setRisorseArt3_2(i.getImportoFinanz2());
            entry.setRisorseArt3_3(i.getImportoFinanz3());
            entry.setRisorseArt3_Succ(i.getImportoFinanzSucc());
            entry.setRisorseBilancio1(i.getStanziamentiBilancio1());
            entry.setRisorseBilancio2(i.getStanziamentiBilancio2());
            entry.setRisorseBilancio3(i.getStanziamentiBilancio3());
            entry.setRisorseBilancioSucc(i.getStanziamentiBilancioSucc());
            entry.setRisorseImmobili1(i.getTrasfImmobili1());
            entry.setRisorseImmobili2(i.getTrasfImmobili2());
            entry.setRisorseImmobili3(i.getTrasfImmobili3());
            entry.setRisorseImmobiliSucc(i.getTrasfImmobiliSucc());
            entry.setRisorseMutuo1(i.getImportoAcqMututo1());
            entry.setRisorseMutuo2(i.getImportoAcqMututo2());
            entry.setRisorseMutuo3(i.getImportoAcqMututo3());
            entry.setRisorseMutuoSucc(i.getImportoAcqMututoSucc());
            entry.setRisorsePrivati1(i.getImportoCapPriv1());
            entry.setRisorsePrivati2(i.getImportoCapPriv2());
            entry.setRisorsePrivati3(i.getImportoCapPriv3());
            entry.setRisorsePrivatiSucc(i.getImportoCapPrivSucc());
            entry.setRisorseVincolatePerLegge1(i.getImpDestVincolo1());
            entry.setRisorseVincolatePerLegge2(i.getImpDestVincolo2());
            entry.setRisorseVincolatePerLegge3(i.getImpDestVincolo3());
            entry.setRisorseVincolatePerLeggeSucc(i.getImpDestVincoloSucc());
            if (i.getRupEntry() != null) {
                entry.setRup(mappingRup(i.getRupEntry()));
            }
            entry.setScadenzaFinanziamento(i.getScadenzaUtilizzoFinanziamento());
            entry.setSpeseSostenute(i.getSpeseSostenute());
            entry.setStatoProgettazione(i.getStatoProgettazioneApprovata());
            entry.setStrutturaOperativa(i.getStrutturaOperativa());
            entry.setTipologia(i.getClassificazioneIntervento());
            entry.setTipologiaCapitalePrivato(i.getTipologiaCapitalePrivato());
            if (i.getValutazioneResp() != null) {
                entry.setValutazione(i.getValutazioneResp());
            }
            entry.setVariato(i.getVariato());
            risultato.add(entry);
        }

        return risultato;

    }

    private List<InterventoNonRipropostoPubbEntry> mappingInterventiNRForPubb(List<InterventoNonRipropostoEntry> inr) {
        if (inr != null) {
            List<InterventoNonRipropostoPubbEntry> risultato = new ArrayList<InterventoNonRipropostoPubbEntry>();
            for (InterventoNonRipropostoEntry intervento : inr) {
                InterventoNonRipropostoPubbEntry out = new InterventoNonRipropostoPubbEntry();

                out.setConint(intervento.getIdIntervento());
                out.setContri(intervento.getIdProgramma());
                out.setCui(intervento.getCui());
                out.setCup(intervento.getCup());
                out.setDescrizione(intervento.getDescrizione());
                out.setImporto(intervento.getImportoComplessivo());
                out.setMotivo(intervento.getMotivo());
                out.setPriorita(intervento.getPriorita());

                risultato.add(out);
            }
            return risultato;
        }
        return null;
    }

    private DatiGeneraliTecnicoPubbEntry mappingRup(RupEntry rup) {
        DatiGeneraliTecnicoPubbEntry risultato = new DatiGeneraliTecnicoPubbEntry();
        risultato.setCap(rup.getCap());
        risultato.setCfPiva(rup.getCf());
        risultato.setCivico(rup.getNumCivico());
        risultato.setCodice(rup.getCodice());
        risultato.setCodiceSA(rup.getStazioneAppaltante());
        risultato.setCognome(rup.getCognome());
        risultato.setFax(rup.getFax());
        risultato.setIndirizzo(rup.getIndirizzo());
        risultato.setLocalita(rup.getProvincia());
        risultato.setLuogoIstat(rup.getCodIstat());
        risultato.setMail(rup.getEmail());
        risultato.setNome(rup.getNome());
        risultato.setNomeCognome(rup.getNominativo());
        risultato.setProvincia(rup.getProvincia());
        risultato.setTelefono(rup.getTelefono());
        return risultato;
    }

    private PubblicaProgrammaLavoriEntry mappingProgrammaForPubb(ProgrammaEntry p) {

        PubblicaProgrammaLavoriEntry risultato = new PubblicaProgrammaLavoriEntry();

        risultato.setAnno(p.getAnnoInizio());

        risultato.setClientId("clientId");
        risultato.setCodiceFiscaleSA(p.getStazioneappaltante());
        risultato.setCodiceReferente(p.getResponsabile());
        risultato.setCodiceSA(p.getStazioneappaltante());
        risultato.setCodein(p.getCodein());
        risultato.setContri(p.getId());
        risultato.setDataAdozione(p.getDataAtto());
        risultato.setDataApprovazione(p.getDataApprovazione());
        risultato.setDataPubblicazioneAdozione(p.getDataPubblicazioneAdo());
        risultato.setDataPubblicazioneApprovazione(p.getDataPubblicazioneAppr());
        risultato.setDescrizione(p.getDescrizioneBreve());
        risultato.setId(p.getIdProgramma());
        risultato.setIdRicevuto(p.getIdRicevuto());
        risultato.setIdUfficio(p.getIdUfficio());
        risultato.setNumeroAdozione(p.getNumeroAtto());
        risultato.setNumeroApprovazione(p.getNumeroApprovazione());
        if (p.getRup() != null) {
            risultato.setReferente(mappingRup(p.getRup()));
        }
        risultato.setUfficio(p.getUfficio() == null ? null : p.getUfficio().getDenominazione());
        risultato.setSyscon(p.getSyscon());
        risultato.setTitoloAttoAdozione(p.getTitoloAdozione());
        risultato.setTitoloAttoApprovazione(p.getTitoloApprovazione());
        risultato.setUrlAttoAdozione(p.getUrlAdozione());
        risultato.setUrlAttoApprovazione(p.getUrlApprovazione());
        risultato.setNorma(p.getNorma());
        return risultato;
    }

    public ResponsePubblicaServizi getProgrammaServiziForPublish(Long contri) {
        ResponsePubblicaServizi risultato = new ResponsePubblicaServizi();
        try {

            ProgrammaEntry programma = this.programmiMapper.getProgramma(contri);
            String cfsa = this.programmiMapper.getCFSAByCode(programma.getStazioneappaltante());
            programma.setStazioneappaltante(cfsa);
            RupEntry rupProgramma = this.programmiMapper.getRup(programma.getResponsabile());
            programma.setRup(rupProgramma);

            if (programma.getIdUfficio() != null) {
                programma.setUfficio(this.programmiMapper.getUff(programma.getIdUfficio()));
            }

            List<InterventoEntry> interventi = this.programmiMapper.getInterventiProgramma(contri);
            List<InterventoNonRipropostoEntry> intyerventiNR = this.programmiMapper.getInterventiNRProgramma(contri);

            for (InterventoEntry intervento : interventi) {
                List<ImmobileEntry> immobili = this.programmiMapper.getImmobili(contri, intervento.getId(), null);
                intervento.setImmobili(immobili);
                RupEntry rupIntervento = this.programmiMapper.getRup(intervento.getCodiceRup());
                intervento.setRupEntry(rupIntervento);
            }

            PubblicaProgrammaFornitureServiziEntry pubblicazioneEntry = this.mappingForPubServizi(programma, interventi,
                    intyerventiNR);
            risultato.setData(pubblicazioneEntry);

        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la ricerca dei dati di pubblicazione: contri= " + contri);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return risultato;
    }

    private PubblicaProgrammaFornitureServiziEntry mappingForPubServizi(ProgrammaEntry p, List<InterventoEntry> i,
                                                                        List<InterventoNonRipropostoEntry> inr) {
        List<AcquistoPubbEntry> intervPubb = mappingAcquistiForPubb(i);
        List<AcquistoNonRipropostoPubbEntry> intervNRPubb = mappingAcquistiNRForPubb(inr);
        PubblicaProgrammaFornitureServiziEntry programma = mappingProgrammaFornitureForPubb(p);
        programma.setAcquisti(intervPubb);
        programma.setAcquistiNonRiproposti(intervNRPubb);
        return programma;
    }

    private PubblicaProgrammaFornitureServiziEntry mappingProgrammaFornitureForPubb(ProgrammaEntry p) {
        PubblicaProgrammaFornitureServiziEntry risultato = new PubblicaProgrammaFornitureServiziEntry();
        risultato.setAnno(p.getAnnoInizio());
        risultato.setClientId("clientId");
        risultato.setCodiceFiscaleSA(p.getStazioneappaltante());
        risultato.setCodiceReferente(p.getResponsabile());
        risultato.setCodiceSA(p.getStazioneappaltante());
        risultato.setCodein(p.getCodein());
        risultato.setContri(p.getId());
        risultato.setDataApprovazione(p.getDataApprovazione());
        risultato.setDataPubblicazioneApprovazione(p.getDataPubblicazioneAppr());
        risultato.setDescrizione(p.getDescrizioneBreve());
        risultato.setId(p.getIdProgramma());
        risultato.setIdRicevuto(p.getIdRicevuto());
        risultato.setIdUfficio(p.getIdUfficio());
        risultato.setNumeroApprovazione(p.getNumeroApprovazione());
        risultato.setReferente(mappingRup(p.getRup()));
        risultato.setSyscon(p.getSyscon());
        risultato.setTitoloAttoApprovazione(p.getTitoloApprovazione());
        risultato.setUfficio(p.getUfficio() == null ? null : p.getUfficio().getDenominazione());
        risultato.setUrlAttoApprovazione(p.getUrlApprovazione());
        risultato.setNorma(p.getNorma());
        return risultato;
    }

    private List<AcquistoPubbEntry> mappingAcquistiForPubb(List<InterventoEntry> interventi) {
        List<AcquistoPubbEntry> risultato = new ArrayList<AcquistoPubbEntry>();
        for (InterventoEntry i : interventi) {
            AcquistoPubbEntry entry = new AcquistoPubbEntry();
            entry.setAcquistoMaterialiRiciclati(i.getAcquistoMaterialiRiciclati());

            entry.setAcquistoRicompreso(i.getAcquistoRicompreso());
            if (i.getAcqVerdi() != null) {
                entry.setAcquistoVerdi(i.getAcqVerdi());
            }
            entry.setAnno(i.getAnnoAvvio());
            entry.setCodiceInterno(i.getCodiceInterno());
            entry.setCodiceRup(i.getCodiceRup());
            entry.setCodiceSoggettoDelegato(i.getCodiceAusa());
            entry.setConint(i.getId());
            entry.setContri(i.getIdProgramma());
            entry.setCoperturaFinanziaria(i.getCoperturaFinanziaria());
            entry.setCpv(i.getCodCpv());
            entry.setCpvMatRic(i.getMrcpv());
            entry.setCpvVerdi(i.getAvcpv());
            entry.setCui(i.getNumeroCui());
            entry.setCuiCollegato(i.getCuiCollegato());
            entry.setCup(i.getCodiceCup());
            entry.setDelega(i.getDelegaProcAff());
            entry.setDescrizione(i.getDescrizione());
            entry.setDirezioneGenerale(i.getDirezioneGenerale());
            entry.setDirigenteResponsabile(i.getDirigenteUfficio());
            entry.setDurataInMesi(i.getDurataContratto());
            entry.setEsenteCup(i.getEsenteCup());
            entry.setImportoIva1(i.getImportoIva1());
            entry.setImportoIva2(i.getImportoIva2());
            entry.setImportoIva3(i.getImportoIva3());
            entry.setImportoIvaSucc(i.getImportoIvaSucc());
            entry.setImportoIvaMatRic(i.getImportoIvaMr());
            entry.setImportoIvaVerdi(i.getImportoIvaAv());
            entry.setImportoNettoIvaMatRic(i.getImportoAlnettoIvaMr());
            entry.setImportoNettoIvaVerdi(i.getImportoAlnettoIvaAv());
            entry.setImportoRisorseFinanziarie(i.getImportoRisorseFinanz());
            entry.setImportoRisorseFinanziarieAltro(i.getImportoRisorseFinanzAltro());
            entry.setImportoRisorseFinanziarieRegionali(i.getImportoRisorseRegionali());
            entry.setImportoTotMatRic(i.getImportoTotaleMr());
            entry.setImportoTotVerdi(i.getImportoTotaleAv());
            entry.setIstat(i.getCodIstatComune());
            entry.setLottoFunzionale(i.getLottoFunzionale());
            entry.setMeseAvvioProcedura(i.getMeseAvvioProc());
            entry.setNomeSoggettoDelegato(i.getSoggettoDelegato());
            entry.setNormativaRiferimento(i.getNormativaRiferimento());
            entry.setNote(i.getNote());
            entry.setNuovoAffidamento(i.getNuovoAffidamento());
            entry.setNuts(i.getCodiceNuts());
            entry.setOggettoMatRic(i.getOggettoMr());
            entry.setOggettoVerdi(i.getOggettoAv());
            entry.setPriorita(i.getLivelloPriorita());
            entry.setProceduraAffidamento(i.getProceduraAffidamento());
            entry.setQuantita(i.getQuantita());
            entry.setReferenteDati(i.getReferenteDatiComunicati());
            entry.setRisorseAltro1(i.getAltreRisorseDisp1());
            entry.setRisorseAltro2(i.getAltreRisorseDisp2());
            entry.setRisorseAltro3(i.getAltreRisorseDisp3());
            entry.setRisorseAltroSucc(i.getAltreRisorseDispSucc());
            entry.setRisorseArt3_1(i.getImportoFinanz1());
            entry.setRisorseArt3_2(i.getImportoFinanz2());
            entry.setRisorseArt3_3(i.getImportoFinanz3());
            entry.setRisorseArt3_Succ(i.getImportoFinanzSucc());
            entry.setRisorseBilancio1(i.getStanziamentiBilancio1());
            entry.setRisorseBilancio2(i.getStanziamentiBilancio2());
            entry.setRisorseBilancio3(i.getStanziamentiBilancio3());
            entry.setRisorseBilancioSucc(i.getStanziamentiBilancioSucc());
            entry.setRisorseImmobili1(i.getTrasfImmobili1());
            entry.setRisorseImmobili2(i.getTrasfImmobili2());
            entry.setRisorseImmobili3(i.getTrasfImmobili3());
            entry.setRisorseImmobiliSucc(i.getTrasfImmobiliSucc());
            entry.setRisorseMutuo1(i.getImportoAcqMututo1());
            entry.setRisorseMutuo2(i.getImportoAcqMututo2());
            entry.setRisorseMutuo3(i.getImportoAcqMututo3());
            entry.setRisorseMutuoSucc(i.getImportoAcqMututoSucc());
            entry.setRisorsePrivati1(i.getImportoCapPriv1());
            entry.setRisorsePrivati2(i.getImportoCapPriv2());
            entry.setRisorsePrivati3(i.getImportoCapPriv3());
            entry.setRisorsePrivatiSucc(i.getImportoCapPrivSucc());
            entry.setRisorseVincolatePerLegge1(i.getImpDestVincolo1());
            entry.setRisorseVincolatePerLegge2(i.getImpDestVincolo2());
            entry.setRisorseVincolatePerLegge3(i.getImpDestVincolo3());
            entry.setRisorseVincolatePerLeggeSucc(i.getImpDestVincoloSucc());
            if (i.getRupEntry() != null) {
                entry.setRup(mappingRup(i.getRupEntry()));
            }
            entry.setSettore(i.getSettore());
            entry.setSpeseSostenute(i.getSpeseSostenute());
            entry.setStrutturaOperativa(i.getStrutturaOperativa());
            if (i.getValutazioneResp() != null) {
                entry.setValutazione(i.getValutazioneResp());
            }
            entry.setVariato(i.getVariato());
            entry.setTipologiaCapitalePrivato(i.getTipologiaCapitalePrivato());
            entry.setUnitaMisura(i.getUnitaMisura());
            entry.setVariato(i.getVariato());
            risultato.add(entry);
        }
        return risultato;

    }

    private List<AcquistoNonRipropostoPubbEntry> mappingAcquistiNRForPubb(
            List<InterventoNonRipropostoEntry> interventi) {
        List<AcquistoNonRipropostoPubbEntry> risultato = new ArrayList<AcquistoNonRipropostoPubbEntry>();
        for (InterventoNonRipropostoEntry i : interventi) {
            AcquistoNonRipropostoPubbEntry entry = new AcquistoNonRipropostoPubbEntry();
            entry.setConint(i.getIdIntervento());
            entry.setContri(i.getIdProgramma());
            entry.setCui(i.getCui());
            entry.setCup(i.getCup());
            entry.setDescrizione(i.getDescrizione());
            entry.setImporto(i.getImportoComplessivo());
            entry.setMotivo(i.getMotivo());
            entry.setPriorita(i.getPriorita());
            risultato.add(entry);
        }
        return risultato;
    }

    public ResponseFile creaPdfNuovaNormativa(String piatriID, String tiprog) throws Exception {

        logger.info("creaPdfNuovaNormativa");
        ResponseFile risultato = new ResponseFile();
        Connection jrConnection = null;
        try {
            ProgrammaEntry programma = programmiMapper.getProgrammaById(piatriID);
            String PROP_JRREPORT_SOURCE_DIR = "jrReport/NuovaNormativa/";
            String jrReportSourceDir = null;
            // Input stream del file sorgente
            InputStream inputStreamJrReport = null;
            if (tiprog.equals("1")) {
                if (programma.getNorma() == 3L) {
                    PROP_JRREPORT_SOURCE_DIR = "jrReport/NuovaNormativa/norma3/";
                }
                jrReportSourceDir = PROP_JRREPORT_SOURCE_DIR;
                Resource resource = new ClassPathResource(PROP_JRREPORT_SOURCE_DIR + "allegatoI.jasper");
                inputStreamJrReport = resource.getInputStream();

            } else {
                if (programma.getNorma() == 3L) {
                    PROP_JRREPORT_SOURCE_DIR = "jrReport/NuovaNormativa/norma3/";
                }
                jrReportSourceDir = PROP_JRREPORT_SOURCE_DIR;
                Resource resource = new ClassPathResource(PROP_JRREPORT_SOURCE_DIR + "allegatoII.jasper");
                inputStreamJrReport = resource.getInputStream();
            }
            // Parametri
            HashMap<String, Object> jrParameters = new HashMap<String, Object>();
            jrParameters.put(JRParameter.REPORT_LOCALE, Locale.ITALIAN);
            jrParameters.put("SUBREPORT_DIR", jrReportSourceDir);
            jrParameters.put("COD_PIANOTRIEN", piatriID);
            jrParameters.put(JRParameter.REPORT_LOCALE, Locale.ITALY); // Imposta la localizzazione
            // Connessione
            jrConnection = dataSource.getConnection();
            // Stampa del formulario
            JasperPrint jrPrint = JasperFillManager.fillReport(inputStreamJrReport, jrParameters, jrConnection);
            jrPrint.setProperty("net.sf.jasperreports.print.locale", "it_IT");
            // Output stream del risultato
            ByteArrayOutputStream baosJrReport = new ByteArrayOutputStream();
            JRExporter jrExporter = new JRPdfExporter();
            jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jrPrint);
            jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baosJrReport);
            jrExporter.exportReport();

            inputStreamJrReport.close();
            baosJrReport.close();

            logger.info("creaPdfNuovaNormativa terminato con successo");
            risultato.setData(Base64.getEncoder().encodeToString(baosJrReport.toByteArray()));
            return risultato;

        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new Exception(e);
        } finally {
            try {
                if (jrConnection != null) {
                    jrConnection.close();
                }
            } catch (SQLException e) {
                logger.error("Errore durante la chiusura della transazione durante la creazione del pdf ", e);
            }
        }
    }

    /**
     * AGGIORNAMENTI COSTI
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void aggiornaCostiPiatri(Long contri) throws Exception {

        CostiProgrammiUpdateForm form = new CostiProgrammiUpdateForm();
        form.setContri(contri);
        try {
            form.setDv1tri(this.programmiMapper.sommaValoriIntervento(contri, "dv1tri"));
            form.setDv2tri(this.programmiMapper.sommaValoriIntervento(contri, "dv2tri"));
            form.setDv3tri(this.programmiMapper.sommaValoriIntervento(contri, "dv3tri"));
            form.setIm1tri(this.programmiMapper.sommaValoriIntervento(contri, "im1tri"));
            form.setIm2tri(this.programmiMapper.sommaValoriIntervento(contri, "im2tri"));
            form.setIm3tri(this.programmiMapper.sommaValoriIntervento(contri, "im3tri"));
            form.setMu1tri(this.programmiMapper.sommaValoriIntervento(contri, "mu1tri"));
            form.setMu2tri(this.programmiMapper.sommaValoriIntervento(contri, "mu2tri"));
            form.setMu3tri(this.programmiMapper.sommaValoriIntervento(contri, "mu3tri"));
            form.setPr1tri(this.programmiMapper.sommaValoriIntervento(contri, "pr1tri"));
            form.setPr2tri(this.programmiMapper.sommaValoriIntervento(contri, "pr2tri"));
            form.setPr3tri(this.programmiMapper.sommaValoriIntervento(contri, "pr3tri"));
            form.setAl1tri(this.programmiMapper.sommaValoriIntervento(contri, "al1tri"));
            form.setAl2tri(this.programmiMapper.sommaValoriIntervento(contri, "al2tri"));
            form.setAl3tri(this.programmiMapper.sommaValoriIntervento(contri, "al3tri"));
            form.setAp1tri(this.programmiMapper.sommaValoriIntervento(contri, "ap1tri"));
            form.setAp2tri(this.programmiMapper.sommaValoriIntervento(contri, "ap2tri"));
            form.setAp3tri(this.programmiMapper.sommaValoriIntervento(contri, "ap3tri"));
            form.setBi1tri(this.programmiMapper.sommaValoriIntervento(contri, "bi1tri"));
            form.setBi2tri(this.programmiMapper.sommaValoriIntervento(contri, "bi2tri"));
            form.setBi3tri(this.programmiMapper.sommaValoriIntervento(contri, "bi3tri"));
            form.setDi1int(this.programmiMapper.sommaValoriIntervento(contri, "di1int"));
            form.setDi2int(this.programmiMapper.sommaValoriIntervento(contri, "di2int"));
            form.setDi3int(this.programmiMapper.sommaValoriIntervento(contri, "di3int"));
            form.setRg1tri(this.programmiMapper.sommaValoriIntervento(contri, "rg1tri"));
            form.setImprfs(this.programmiMapper.sommaValoriIntervento(contri, "imprfs"));
            this.programmiMapper.updateCostiPiatri(form);
            this.programmiMapper.updatestatoPiatri(contri);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante l'aggiornamento dei valori calcolati per il programma " + contri,
                    e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void aggiornaCostiInttri(Long contri, Long conint) throws Exception {

        CostiInterventiUpdateForm form = new CostiInterventiUpdateForm();
        form.setContri(contri);
        form.setConint(conint);
        try {
            form.setAl1tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "AL1CB"));
            form.setAl2tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "AL2CB"));
            form.setAl3tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "AL3CB"));
            form.setAl9tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "AL9CB"));
            form.setAp1tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "AP1CB"));
            form.setAp2tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "AP2CB"));
            form.setAp3tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "AP3CB"));
            form.setAp9tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "AP9CB"));
            form.setBi1tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "BI1CB"));
            form.setBi2tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "BI2CB"));
            form.setBi3tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "BI3CB"));
            form.setBi9tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "BI9CB"));
            form.setDv1tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "DV1CB"));
            form.setDv2tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "DV2CB"));
            form.setDv3tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "DV3CB"));
            form.setDv9tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "DV9CB"));
            form.setMu1tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "MU1CB"));
            form.setMu2tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "MU2CB"));
            form.setMu3tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "MU3CB"));
            form.setMu9tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "MU9CB"));
            form.setIv1tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "IV1CB"));
            form.setIv2tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "IV2CB"));
            form.setIv3tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "IV3CB"));
            form.setIv9tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "IV9CB"));
            form.setImpalt(this.programmiMapper.sommaValoriCapitolo(contri, conint, "IMPALTCB"));
            form.setImprfs(this.programmiMapper.sommaValoriCapitolo(contri, conint, "IMPRFSCB"));
            form.setRg1tri(this.programmiMapper.sommaValoriCapitolo(contri, conint, "RG1CB"));
            form.setSpesesost(this.programmiMapper.sommaValoriCapitolo(contri, conint, "SPESESOST"));
            this.programmiMapper.updateCostiInttri(form);
            this.programmiMapper.UpdateParzialiInttri(contri, conint);
            this.programmiMapper.UpdateTotaliInttri(contri, conint);
            this.aggiornaCostiPiatri(contri);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante l'aggiornamento dei valori calcolati per l'intervento. contri: "
                    + contri + "conint: " + conint, e);
            throw e;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResponseInsert duplicaProgramma(Long contri) {
        ResponseInsert risultato = new ResponseInsert();
        try {

            ProgrammaEntry programma = this.programmiMapper.getProgramma(contri);
            List<InterventoEntry> interventi = this.programmiMapper.getInterventiProgramma(contri);
            List<InterventoNonRipropostoEntry> interventiNR = this.programmiMapper.getInterventiNRProgramma(contri);
            List<OperaIncompiutaEntry> opereIncompiute = this.programmiMapper.getOpereIncompiuteProgramma(contri);

            for (InterventoEntry intervento : interventi) {
                List<ImmobileEntry> immobili = this.programmiMapper.getImmobili(contri, intervento.getId(), null);
                intervento.setImmobili(immobili);
            }
            for (OperaIncompiutaEntry opera : opereIncompiute) {
                List<ImmobileEntry> immobili = this.programmiMapper.getImmobili(contri, null, opera.getId());
                opera.setImmobili(immobili);
            }

            Long newContri = copiaProgramma(programma);
            copiaInterventi(interventi, newContri);
            copiainterventiNR(interventiNR, newContri);
            copiaOperIncompiute(opereIncompiute, newContri);
            risultato.setData(newContri);

        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la copia del programma: contri= " + contri, t);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return risultato;

    }

    private void copiaOperIncompiute(List<OperaIncompiutaEntry> opereIncompiute, Long newContri) {
        for (OperaIncompiutaEntry o : opereIncompiute) {
            Long oldContri = o.getIdProgramma();
            o.setIdProgramma(newContri);
            this.programmiMapper.copiaOperaIncompiuta(o);
            List<ImmobileEntry> immobili = this.programmiMapper.getImmobili(oldContri, null, o.getId());
            for (ImmobileEntry immobile : immobili) {
                immobile.setIdProgramma(newContri);
                this.programmiMapper.importImmobile(immobile);
            }
        }

    }

    private void copiainterventiNR(List<InterventoNonRipropostoEntry> interventiNR, Long newContri) {
        for (InterventoNonRipropostoEntry i : interventiNR) {
            InterventoNonRipropostoInsertForm formInsert = new InterventoNonRipropostoInsertForm();
            formInsert.setCui(i.getCui());
            formInsert.setCup(i.getCup());
            formInsert.setDescrizione(i.getDescrizione());
            formInsert.setIdProgramma(newContri);
            formInsert.setImportoComplessivo(i.getImportoComplessivo());
            formInsert.setPriorita(i.getPriorita());
            formInsert.setIdIntervento(i.getIdIntervento());
            formInsert.setMotivo(i.getMotivo());
            this.programmiMapper.insertInterventoNonRiproposto(formInsert);
        }
    }

    private void copiaInterventi(List<InterventoEntry> interventi, Long newContri) throws Throwable {

        for (InterventoEntry i : interventi) {
            Long oldContri = i.getIdProgramma();
            i.setIdProgramma(newContri);
            this.programmiMapper.importIntervento(i);
            List<ImmobileEntry> immobili = this.programmiMapper.getImmobili(oldContri, i.getId(), null);
            for (ImmobileEntry immobile : immobili) {
                immobile.setIdProgramma(newContri);
                this.programmiMapper.importImmobile(immobile);
            }
            List<RisorsaCapitoloEntry> risorseCapitolo = this.programmiMapper.getAllRisorseDiCapitolo(oldContri,
                    i.getId());
            for (RisorsaCapitoloEntry risorsa : risorseCapitolo) {
                risorsa.setIdProgramma(newContri);
                this.programmiMapper.importRisorsaDiCapitolo(risorsa);
            }
            aggiornaCostiPiatri(newContri);
            // aggiornaCostiInttri(newContri, i.getId());
        }
    }

    private Long copiaProgramma(ProgrammaEntry programma) {
        ProgrammaInsertForm p = new ProgrammaInsertForm();
        Long contri = this.programmiMapper.getMaxContri();
        if (contri == null) {
            contri = 1L;
        } else {
            contri = contri + 1;
        }
        Boolean soloScheda = false;
        if (programma.getIdProgramma().startsWith("OI")) {
            soloScheda = true;
        }
        String cfStazioneAppaltante = this.programmiMapper.getCFSAByCode(programma.getStazioneappaltante());
        String id = calcolaIdProgramma(programma.getTipoProg(), programma.getAnnoInizio(), cfStazioneAppaltante, soloScheda);
        p.setAnnoInizio(programma.getAnnoInizio());
//		p.setDataAtto(programma.getDataAtto());
//		p.setDataPubblicazioneAdo(programma.getDataPubblicazioneAdo());
        String descrizioneBreve = calcolaDescrizioneBreveProgramma(programma.getTipoProg(), programma.getAnnoInizio(), programma.getDataApprovazione(), programma.getIdProgramma(), programma.getIdUfficio(), programma.getDataAtto(), programma.getStazioneappaltante(), soloScheda, programma.getNorma());
        p.setDescrizioneBreve(descrizioneBreve);
        p.setId(contri);
        p.setIdProgramma(id);
        p.setIdUfficio(programma.getIdUfficio());
        p.setNorma(programma.getNorma());
//		p.setNumeroAtto(programma.getNumeroAtto());
        p.setPubblica(programma.getPubblica());
        p.setResponsabile(programma.getResponsabile());
        p.setStazioneappaltante(programma.getStazioneappaltante());
        p.setSyscon(programma.getSyscon());
        p.setTipoProg(programma.getTipoProg());
//		p.setTitoloAdozione(programma.getTitoloAdozione());
//		p.setUrlAdozione(programma.getUrlAdozione());
        this.programmiMapper.insertProgramma(p);
        return contri;

    }

    public List<String> getSAList(Long syscon) {
        return this.programmiMapper.getSAList(syscon);
    }

    public String getJWTKey() throws CriptazioneException {

        String criptedKey = this.wConfigManager.getConfigurationValue(CONFIG_CHIAVE_APP);
        try {
            ICriptazioneByte decrypt = FactoryCriptazioneByte.getInstance(
                    FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, criptedKey.getBytes(),
                    ICriptazioneByte.FORMATO_DATO_CIFRATO);

            return new String(decrypt.getDatoNonCifrato());
        } catch (CriptazioneException e) {
            logger.error("Errore in fase di decrypt della chiave hash per generazione token", e);
            throw e;
        }
    }

    public BaseResponse cambiaSyscon(CambioSysconForm form) {
        BaseResponse risultato = new BaseResponse();
        risultato.setEsito(true);
        try {
            this.programmiMapper.updateSysconProgramma(form.getSyscon(), form.getStazioneAppaltante(),
                    form.getContri());

        } catch (Throwable t) {
            logger.error("cambiaSyscon: Errore inaspettato.", t);
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

    public ResponseListaCuiOptions getCuiOptions(CuiSearchForm form) {
        ResponseListaCuiOptions risultato = new ResponseListaCuiOptions();
        risultato.setEsito(true);
        try {

            form.setDesc(form.getDesc().toLowerCase());
            List<String> entry = programmiMapper.getCuiOptions(form);
            risultato.setData(entry);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la ricerca dei cui", t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseListaCuiOptions.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseListaProgrammiNonPaginata getListaProgrammiNonPaginata(
            final ProgrammiNonPaginatiSearchForm searchForm) {

        ResponseListaProgrammiNonPaginata risultato = new ResponseListaProgrammiNonPaginata();

        try {

            List<ProgrammaBaseEntry> entries = new ArrayList<ProgrammaBaseEntry>();

            if (StringUtils.isBlank(searchForm.getSearchString())) {
                risultato.setData(entries);
                risultato.setEsito(true);
                return risultato;
            } else {
                searchForm.setSearchStringLike("%" + searchForm.getSearchString().toLowerCase() + "%");
            }

            String searchByRup = this.wConfigManager.getConfigurationValue("ALT.PIANI.APPLICA-FILTRO-UTENTE");
            if (!"0".equals(searchByRup)) {
                String ruolo = this.programmiMapper.getRuolo(searchForm.getSyscon());
                if ("A".equals(ruolo) || "C".equals(ruolo)) {
                    searchForm.setSyscon(null);
                }
            } else {
                searchForm.setSyscon(null);
            }

            entries = this.programmiMapper.getListaProgrammiNonPaginata(searchForm);
            risultato.setData(entries);
            risultato.setEsito(true);
        } catch (Exception e) {
            logger.error("Errore durante il recupero della lista dei programmi non paginata", e);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    public ResponseInizNuovoProgramma getInizNuovoProgramma(String stazioneAppaltante, String syscon) {
        ResponseInizNuovoProgramma risultato = new ResponseInizNuovoProgramma();
        InizNuovoProgramma iniz = new InizNuovoProgramma();
        risultato.setEsito(true);

        try {
            String sysuffapp = this.programmiMapper.getCentroCosto(Long.valueOf(syscon));
            List<Long> centriCostoSA = this.programmiMapper.getCentroCostoBySA(stazioneAppaltante);
            String valore = wConfigManager.getConfigurationValue(CONFIG_CONFIGURAZIONE_APPLICATIVA);
            ;

            if (!"SCP".equals(valore)) {
                if (centriCostoSA != null && centriCostoSA.size() > 0) {
                    iniz.setShowUfficio(true);
                } else {
                    iniz.setShowUfficio(false);
                }
                if (sysuffapp != null) {
                    String denomUfficio = this.programmiMapper.getDenomUfficio(sysuffapp);
                    iniz.setDenomUfficio(denomUfficio);
                    iniz.setIdUfficio(sysuffapp);
                }
            } else {
                iniz.setShowUfficio(true);
            }

            risultato.setData(iniz);
        } catch (Exception e) {
            logger.error("Errore durante il metodo getInizNuovoProgramma", e);
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return risultato;
    }


    public ResponseResult showSoggReport(String idProgramma) {
        ResponseResult rs = new ResponseResult();
        rs.setEsito(true);

        try {
            List<ExportSogg> dati = this.programmiMapper.getSoggettiAggregatori(Long.valueOf(idProgramma));
            List<InterventoBaseEntry> interventi = this.programmiMapper.getInterventiAll(Long.valueOf(idProgramma));
            if (dati != null && dati.size() > 0 && interventi != null && interventi.size() > 0) {
                rs.setData("true");
            } else {
                rs.setData("false");
            }
        } catch (Exception e) {
            logger.error("Errore durante il metodo showSoggReport", e);
            rs.setEsito(false);
            rs.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }


        return rs;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResponse deleteFlussoProgramma(Long idProgramma) {
        BaseResponse risultato = new BaseResponse();
        try {
            String tipoInstallazione = this.wConfigManager.getConfigurationValue(CONFIG_TIPO_APPLICAZIONE);
            this.programmiMapper.deleteFlussoProgramma(idProgramma);
            if ("2".equals(tipoInstallazione) || "1".equals(tipoInstallazione)) {
                this.programmiMapper.updateIdRicevutoFlussi(idProgramma);
            } else if ("3".equals(tipoInstallazione)) {
                this.programmiMapper.updateIdGeneratoFlussi(idProgramma);
            }
            risultato.setEsito(true);

        } catch (Exception e) {
            logger.error("Errore inaspettato durante la delete del flusso della pubblicazione del programma con id: " + idProgramma, e);
            throw e;
        }
        return risultato;
    }

    public ResponseDettaglioCup getDettaglioCup(DettaglioCupForm form) {

        String encriptedPwd = this.getEncryptedPwd(form.getPassword());
        CredenzialiSimog credenziali = this.programmiMapper.getSimogCredentials(form.getSyscon());
        if (credenziali != null && credenziali.getUsername() != null && credenziali.getPassword() != null) {
            this.programmiMapper.updateCredenzialiSimog(form.getUsername(), encriptedPwd, form.getSyscon());
        } else {
            this.programmiMapper.insertCredenzialiSimog(form.getUsername(), encriptedPwd, form.getSyscon());
        }
        return this.restclientmanager.dettaglioCup(form);
    }


    private String getEncryptedPwd(String password) {
        try {
            ICriptazioneByte criptatore = FactoryCriptazioneByte.getInstance(
                    FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, password.getBytes(),
                    ICriptazioneByte.FORMATO_DATO_NON_CIFRATO);

            return new String(criptatore.getDatoCifrato());
        } catch (CriptazioneException e) {
            logger.error("Errore in fase di decrypt della chiave hash per generazione token", e);
            return null;
        }
    }

    public ResponseInizDettaglioCUP getInizDettaglioCup(Long syscon) {
        ResponseInizDettaglioCUP risultato = new ResponseInizDettaglioCUP();
        risultato.setEsito(true);
        try {
            CredenzialiSimog credenziali = this.programmiMapper.getSimogCredentials(syscon);
            if (credenziali != null && credenziali.getPassword() != null) {
                String passwordInChiaro = this.getDecryptedPwd(credenziali.getPassword());
                credenziali.setPassword(passwordInChiaro);
            } else {
                credenziali = new CredenzialiSimog();
            }
            risultato.setData(credenziali);
            return risultato;
        } catch (Exception e) {
            logger.error("Errore in getInizImportSmartcig", e);
            risultato.setEsito(false);
            risultato.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            return risultato;
        }
    }

    private String getDecryptedPwd(String password) {
        try {
            ICriptazioneByte decrypt = FactoryCriptazioneByte.getInstance(
                    FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, password.getBytes(),
                    ICriptazioneByte.FORMATO_DATO_CIFRATO);

            return new String(decrypt.getDatoNonCifrato());
        } catch (CriptazioneException e) {
            logger.error("Errore in fase di decrypt della chiave hash per generazione token", e);
            return null;
        }
    }

    private boolean checkConfrontoProgrammi(final ProgrammaEntry programma) {

        if (programma != null) {

            Long existsCurrentYear = this.programmiMapper.checkExistsProgrammaByTipoAndSAAndAnnoNotCurrent(programma.getTipoProg(), programma.getStazioneappaltante(), programma.getAnnoInizio(), programma.getId());
            Long annoPrecedente = programma.getAnnoInizio() - 1L;
            Long existsLastYear = this.programmiMapper.checkExistsProgrammaByTipoAndSAAndAnno(programma.getTipoProg(), programma.getStazioneappaltante(), annoPrecedente);

            return (existsCurrentYear != null && existsCurrentYear > 0L) || (existsLastYear != null && existsLastYear > 0L);
        }

        return false;
    }

    public GeneralBaseResponse<List<ProgrammaBaseEntry>> listaProgrammiDaConfronto(final Long contri) {
        GeneralBaseResponse<List<ProgrammaBaseEntry>> risultato = new GeneralBaseResponse<>();

        try {

            if (contri == null) {
                risultato.setEsito(false);
                risultato.setErrorData(GeneralBaseResponse.ERROR_NOT_FOUND);
                return risultato;
            }

            ProgrammaEntry programma = this.programmiMapper.getProgramma(contri);

            if (programma == null) {
                risultato.setEsito(false);
                risultato.setErrorData(GeneralBaseResponse.ERROR_NOT_FOUND);
                return risultato;
            }

            boolean check = checkConfrontoProgrammi(programma);

            if (!check) {
                risultato.setEsito(false);
                risultato.setErrorData(GeneralBaseResponse.ERROR_NOT_FOUND);
                return risultato;
            }

            Long tipoProgramma = programma.getTipoProg();
            Long annoInizio = programma.getAnnoInizio();
            String stazioneAppaltante = programma.getStazioneappaltante();

            List<ProgrammaBaseEntry> listaProgrammiDaConfronto = new ArrayList<>();

            List<ProgrammaBaseEntry> listaProgrammiAnnoAttuale = this.programmiMapper.getListaProgrammiAnnoAttualeDaConfronto(tipoProgramma, stazioneAppaltante, annoInizio, contri);

            if (listaProgrammiAnnoAttuale != null) {
                listaProgrammiDaConfronto.addAll(listaProgrammiAnnoAttuale);
            }

            Long annoPrecedente = annoInizio - 1L;

            List<ProgrammaBaseEntry> listaProgrammiAnnoPrecedente = this.programmiMapper.getListaProgrammiAnnoPrecedenteDaConfronto(tipoProgramma, stazioneAppaltante, annoPrecedente);

            // Prendo l'ultimo
            if (listaProgrammiAnnoPrecedente != null && listaProgrammiAnnoPrecedente.size() > 0) {
                if (listaProgrammiAnnoPrecedente.size() == 1) {
                    listaProgrammiDaConfronto.add(listaProgrammiAnnoPrecedente.get(0));
                } else {
                    ProgrammaBaseEntry ultimoProgrammaAnnoPrecedente = listaProgrammiAnnoPrecedente
                            .stream()
                            .max(ProgrammaAnnualitaComparator.createProgrammaAnnualitaOrderComparator())
                            .get();
                    listaProgrammiDaConfronto.add(ultimoProgrammaAnnoPrecedente);
                }
            }

            risultato.setData(listaProgrammiDaConfronto);
            risultato.setEsito(true);
        } catch (Exception e) {
            String message = "Errore durante il caricamento dei programmi da confronto per contri " + contri;
            logger.error(message, e);
            risultato.setEsito(false);
            risultato.setErrorData(message);
        }

        return risultato;
    }

    public GeneralBaseResponse<List<InterventiDaConfrontoDTO>> listaInterventiDaConfronto(final Long contri, final Long contriDaConfrontare) {
        GeneralBaseResponse<List<InterventiDaConfrontoDTO>> risultato = new GeneralBaseResponse<>();

        try {

            List<InterventiDaConfrontoDTO> listaInterventi = new ArrayList<>();

            Long countInterventiAnnoAttuale = this.programmiMapper.countInterventiByContri(contri);
            Long countInterventiAnnoPrecedente = this.programmiMapper.countInterventiByContri(contriDaConfrontare);
            List<InterventiDaConfrontoQueryResult> listaInterventiProgrammaAttuale = this.programmiMapper.exportListaInterventiDaConfronto(contri);
            List<InterventiDaConfrontoQueryResult> listaInterventiProgrammaPrecedente = this.programmiMapper.exportListaInterventiDaConfronto(contriDaConfrontare);

            if (countInterventiAnnoAttuale > 0L) {
                // check elementi aggiungi e modificati
                listaInterventiProgrammaAttuale.stream().forEach(i -> {
                    String cui = i.getCui();

                    InterventiDaConfrontoDTO dto = new InterventiDaConfrontoDTO();
                    dto.setCui(cui);
                    dto.setDescrizione(i.getDescrizione());

                    InterventiDaConfrontoQueryResult el = listaInterventiProgrammaPrecedente
                            .parallelStream()
                            .filter(iOld -> iOld.getCui().equals(cui))
                            .findFirst()
                            .orElse(null);
                    if (el != null) {
                        // variato
                        logger.debug("Attuale [ {} ]", i);
                        logger.debug("Precedente [ {} ]", el);
                        // check annualita'
                        if (i.getAnnualita().equals(el.getAnnualita())) {
                            // siamo nello stesso anno (verifico variato)
                            if (i.getVariato() == null) {
                                // anomalia, verifico annualita' e importo e flaggo il confronto come warning)
                                dto.setWarningVariatoNull(true);
                                if (!i.getCostiTotale().equals(el.getCostiTotale())) {
                                    dto.setVariazione(EInterventiDaConfrontoVariazione.VARIATO_QUADRO_ECONOMICO);
                                }
                                // TODO check annualita'
                            } else {
                                // prendo il valore del tabellato PT011 per Lavori e PT010 per Acquisti
                                String tab1Cod = i.getTipoProgramma().equals(1L) ? "PT011" : "PT010";
                                String descrizioneTabellato = this.programmiMapper.findTab1ValueFromCodAndTip(tab1Cod, i.getVariato());
                                dto.setVariazione(EInterventiDaConfrontoVariazione.TABELLATO);
                                dto.setDescrizioneTabellato(descrizioneTabellato);
                            }
                        } else {
                            // anno precedente (controllo annualita' e importo)
                            if (!i.getCostiTotale().equals(el.getCostiTotale())) {
                                dto.setVariazione(EInterventiDaConfrontoVariazione.VARIATO_QUADRO_ECONOMICO);
                            } else if (el.getAnnualita().equals(i.getAnnualita() - 1L)) {
                                // se l'annualita dell'anno precedente e' uguale a quella del programma attuale - 1
                                dto.setVariazione(EInterventiDaConfrontoVariazione.VARIATA_ANNUALITA);
                            }
                        }
                    } else {
                        // non e' presente nel programma precedente
                        dto.setVariazione(EInterventiDaConfrontoVariazione.AGGIUNTO);
                    }
                    listaInterventi.add(dto);
                });
            }

            if (countInterventiAnnoPrecedente > 0L) {
                // check elementi rimossi
                listaInterventiProgrammaPrecedente.stream().forEach(i -> {
                    String cui = i.getCui();

                    InterventiDaConfrontoQueryResult el = listaInterventiProgrammaAttuale
                            .parallelStream()
                            .filter(iNew -> iNew.getCui().equals(cui))
                            .findFirst()
                            .orElse(null);

                    if (el == null) {
                        // c'e' nell'anno precedente ma non e' stato riportato nell'anno attuale
                        InterventiDaConfrontoDTO dto = new InterventiDaConfrontoDTO();
                        dto.setCui(cui);
                        dto.setDescrizione(i.getDescrizione());
                        dto.setVariazione(EInterventiDaConfrontoVariazione.ELIMINATO);
                        listaInterventi.add(dto);
                    }
                });
            }

            risultato.setData(listaInterventi);
            risultato.setEsito(true);
        } catch (Exception e) {
            String message = "Errore durante il caricamento degli interventi da confronto per contri " + contri + " e contri da confrontare " + contriDaConfrontare;
            logger.error(message, e);
            risultato.setEsito(false);
            risultato.setErrorData(message);
        }
        return risultato;
    }

    public ResponseFile downloadListaInterventiDaConfronto(final Long contri, final Long contriDaConfrontare) {

        ResponseFile risultato = new ResponseFile();
        risultato.setEsito(true);

        try {

            ProgrammaEntry programma = this.programmiMapper.getProgramma(contri);

            GeneralBaseResponse<List<InterventiDaConfrontoDTO>> res = listaInterventiDaConfronto(contri, contriDaConfrontare);

            List<String> testata = printTestataConfrontoProgrammi(programma.getIdProgramma());
            List<List<String>> body = printBodyConfrontoProgrammi(res.getData());

            StringWriter writer = new StringWriter();
            CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
            csvPrinter.printRecord(testata);

            body.stream().forEach(r -> {
                try {
                    csvPrinter.printRecord(r);
                } catch (IOException e) {
                    logger.error(e);
                }
            });
            csvPrinter.flush();

            String output = writer.toString();
            risultato.setData(output);

        } catch (Exception e) {
            logger.error(e);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseFile.ERROR_UNEXPECTED);
        }
        return risultato;

    }

    private List<String> printTestataConfrontoProgrammi(final String idProgramma) {
        List<String> testata = new ArrayList<>();

        testata.add("Codice CUI");
        testata.add("Descrizione");
        testata.add("Variazione rispetto a programma " + idProgramma);

        return testata;
    }

    private List<List<String>> printBodyConfrontoProgrammi(final List<InterventiDaConfrontoDTO> results) {

        List<List<String>> righe = new ArrayList<>();

        results.stream().forEach(r -> {

            List<String> colonne = new ArrayList<>();

            colonne.add(r.getCui());
            colonne.add(r.getDescrizione());

            switch (r.getVariazione()) {
                case AGGIUNTO:
                    colonne.add("AGGIUNTO");
                    break;
                case ELIMINATO:
                    colonne.add("ELIMINATO");
                    break;
                case VARIATA_ANNUALITA:
                    colonne.add("VARIATA ANNUALITA'");
                    break;
                case VARIATO_QUADRO_ECONOMICO:
                    colonne.add("VARIATO QUADRO ECONOMICO");
                    break;
                case TABELLATO:
                    colonne.add(r.getDescrizioneTabellato());
                    break;
                default:
                    colonne.add(r.getVariazione().getValue());
                    break;
            }

            righe.add(colonne);
        });

        return righe;
    }

    public String getAbilitazioniUtente(Long syscon) {
        return this.programmiMapper.getAbilitazioniUtente(syscon);
    }

    public ResponseResult getTipoInstallazione() {
        ResponseResult result = new ResponseResult();
        result.setEsito(true);
        try {
            String tipoInstallazione = this.wConfigManager.getConfigurationValue(CONFIG_TIPO_APPLICAZIONE);
            if (StringUtils.isNotBlank(tipoInstallazione)) {
                result.setData(tipoInstallazione);
            }
        } catch (Exception e) {
            result.setEsito(false);
            result.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            logger.error("Errore durante il metodo getTipoInstallazione", e);
        }
        return result;
    }

    public void AggiornaImportiProgramma(Long contri, Long tipoProgramma) {
        try {
            String updateImportiInterventiLavoriDM112011 = "update inttri set di1int = COALESCE(DV1TRI,0) + COALESCE(IM1TRI,0) + COALESCE(MU1TRI,0) + COALESCE(AL1TRI,0) + COALESCE(AP1TRI,0) + COALESCE(BI1TRI,0),"
                    + " di2int = COALESCE(DV2TRI,0) + COALESCE(IM2TRI,0) + COALESCE(MU2TRI,0) + COALESCE(AL2TRI,0) + COALESCE(AP2TRI,0) + COALESCE(BI2TRI,0),"
                    + " di3int = COALESCE(DV3TRI,0) + COALESCE(IM3TRI,0) + COALESCE(MU3TRI,0) + COALESCE(AL3TRI,0) + COALESCE(AP3TRI,0) + COALESCE(BI3TRI,0),"
                    + " ICPINT = COALESCE(PR1TRI,0) + COALESCE(PR2TRI,0) + COALESCE(PR3TRI,0)" + " WHERE CONTRI = "
                    + contri + " and TIPINT=1";
            String updateImportiLavoriDM112011_2 = "update inttri set DITINT = COALESCE(di1int,0) + COALESCE(di2int,0) + COALESCE(di3int,0) WHERE CONTRI = "
                    + contri + " and TIPINT=1";
            String updateImportiLavoriDM112011_3 = "update inttri set TOTINT = COALESCE(DITINT,0) + COALESCE(ICPINT,0) WHERE CONTRI = "
                    + contri + " and TIPINT=1";
            String updateImportiInterventiFSDM112011 = "update inttri set di1int = COALESCE(AL1TRI,0) + COALESCE(BI1TRI,0) + COALESCE(RG1TRI,0) + COALESCE(IMPRFS,0) + COALESCE(MU1TRI,0) + COALESCE(PR1TRI,0) WHERE CONTRI = "
                    + contri + " and TIPINT=2";
            String updateImportiInterventi = "update inttri set di1int = COALESCE(BI1TRI,0) + COALESCE(DV1TRI,0) + COALESCE(IM1TRI,0) + COALESCE(MU1TRI,0) + COALESCE(AL1TRI,0) + COALESCE(AP1TRI,0),"
                    + " di2int = COALESCE(BI2TRI,0) + COALESCE(DV2TRI,0) + COALESCE(IM2TRI,0) + COALESCE(MU2TRI,0) + COALESCE(AL2TRI,0) + COALESCE(AP2TRI,0),"
                    + " di3int = COALESCE(BI3TRI,0) + COALESCE(DV3TRI,0) + COALESCE(IM3TRI,0) + COALESCE(MU3TRI,0) + COALESCE(AL3TRI,0) + COALESCE(AP3TRI,0),"
                    + " di9int = COALESCE(BI9TRI,0) + COALESCE(DV9TRI,0) + COALESCE(IM9TRI,0) + COALESCE(MU9TRI,0) + COALESCE(AL9TRI,0) + COALESCE(AP9TRI,0),"
                    + " ICPINT = COALESCE(PR1TRI,0) + COALESCE(PR2TRI,0) + COALESCE(PR3TRI,0) + COALESCE(PR9TRI,0)"
                    + " WHERE CONTRI = " + contri;

            String updateImportiInterventi_2 = "update inttri set DITINT = COALESCE(di1int,0) + COALESCE(di2int,0) + COALESCE(di3int,0) + COALESCE(di9int,0) WHERE CONTRI = "
                    + contri;
            String updateImportiInterventi_3 = "update inttri set TOTINT = COALESCE(DITINT,0) + COALESCE(ICPINT,0) + COALESCE(SPESESOST,0) WHERE CONTRI = "
                    + contri;
            String updateImportiImmobili = "update immtrai set VALIMM = COALESCE(VA1IMM,0) + COALESCE(VA2IMM,0) + COALESCE(VA3IMM,0) + COALESCE(VA9IMM,0) WHERE CONTRI = "
                    + contri;

            switch (tipoProgramma.intValue()) {
                case 3:
                    // Aggiorno importi interventi programma DM 11/2011
                    this.sqlMapper.execute(updateImportiInterventiLavoriDM112011);
                    this.sqlMapper.execute(updateImportiLavoriDM112011_2);
                    this.sqlMapper.execute(updateImportiLavoriDM112011_3);
                    this.sqlMapper.execute(updateImportiInterventiFSDM112011);
                    break;
                default:
                    this.sqlMapper.execute(updateImportiInterventi);
                    this.sqlMapper.execute(updateImportiInterventi_2);
                    this.sqlMapper.execute(updateImportiInterventi_3);
                    this.sqlMapper.execute(updateImportiImmobili);
                    break;
            }

            String updateImportiProgramma = "UPDATE PIATRI SET " + "DV1TRI ="
                    + sommaImportiIntervento("DV1TRI", contri, tipoProgramma) + "," + "DV2TRI ="
                    + sommaImportiIntervento("DV2TRI", contri, tipoProgramma) + "," + "DV3TRI ="
                    + sommaImportiIntervento("DV3TRI", contri, tipoProgramma) + "," + "IM1TRI ="
                    + sommaImportiIntervento("IM1TRI", contri, tipoProgramma) + "," + "IM2TRI ="
                    + sommaImportiIntervento("IM2TRI", contri, tipoProgramma) + "," + "IM3TRI ="
                    + sommaImportiIntervento("IM3TRI", contri, tipoProgramma) + "," + "MU1TRI ="
                    + sommaImportiIntervento("MU1TRI", contri, tipoProgramma) + "," + "MU2TRI ="
                    + sommaImportiIntervento("MU2TRI", contri, tipoProgramma) + "," + "MU3TRI ="
                    + sommaImportiIntervento("MU3TRI", contri, tipoProgramma) + "," + "PR1TRI ="
                    + sommaImportiIntervento("PR1TRI", contri, tipoProgramma) + "," + "PR2TRI ="
                    + sommaImportiIntervento("PR2TRI", contri, tipoProgramma) + "," + "PR3TRI ="
                    + sommaImportiIntervento("PR3TRI", contri, tipoProgramma) + "," + "AL1TRI ="
                    + sommaImportiIntervento("AL1TRI", contri, tipoProgramma) + "," + "AL2TRI ="
                    + sommaImportiIntervento("AL2TRI", contri, tipoProgramma) + "," + "AL3TRI ="
                    + sommaImportiIntervento("AL3TRI", contri, tipoProgramma) + "," + "AP1TRI ="
                    + sommaImportiIntervento("AP1TRI", contri, tipoProgramma) + "," + "AP2TRI ="
                    + sommaImportiIntervento("AP2TRI", contri, tipoProgramma) + "," + "AP3TRI ="
                    + sommaImportiIntervento("AP3TRI", contri, tipoProgramma) + "," + "BI1TRI ="
                    + sommaImportiIntervento("BI1TRI", contri, tipoProgramma) + "," + "BI2TRI ="
                    + sommaImportiIntervento("BI2TRI", contri, tipoProgramma) + "," + "BI3TRI ="
                    + sommaImportiIntervento("BI3TRI", contri, tipoProgramma) + "," + "TO1TRI ="
                    + sommaImportiIntervento("DI1INT", contri, tipoProgramma) + "," + "TO2TRI ="
                    + sommaImportiIntervento("DI2INT", contri, tipoProgramma) + "," + "TO3TRI ="
                    + sommaImportiIntervento("DI3INT", contri, tipoProgramma) + "," + "RG1TRI ="
                    + sommaImportiIntervento("RG1TRI", contri, tipoProgramma) + "," + "IMPRFS ="
                    + sommaImportiIntervento("IMPRFS", contri, tipoProgramma) + " WHERE CONTRI = " + contri;
            this.sqlMapper.execute(updateImportiProgramma);

        } catch (Exception e) {
            logger.error("Errore durante l'aggiornamento degli importi del programma", e);
        }

    }

    /**
     * @param campo  campo
     * @param contri codice programma
     * @param tiprog tipo programma
     * @return importo
     * @throws Exception GestoreException
     */
    private Double sommaImportiIntervento(final String campo, final Long contri, final Long tiprog) throws Exception {
        String sqlSelectSomma = "select SUM(" + campo + ") from INTTRI where CONTRI=" + contri
                + " and (ACQALTINT is null or ACQALTINT =1)";
        if (tiprog.equals(new Long(3))) {
            sqlSelectSomma += " AND TIPINT=1";
        }
        Double somma;
        try {
            somma = this.sqlMapper.executeReturnDouble(sqlSelectSomma);
        } catch (Exception e) {
            somma = new Double(0);
            throw new Exception(e);
        }
        return somma;
    }

    public void creaPdfNuovaNormativa(String piatriID, Long contri, Long tiprog, Long idRicevuto,
                                      ServletContext request) throws Exception {

        logger.info("creaPdfNuovaNormativa");
        Connection jrConnection = null;
        try {
            Long norma = programmiMapper.getProgrammaNormaById(piatriID);
            String PROP_JRREPORT_SOURCE_DIR = "jrReport/NuovaNormativa/";
            String jrReportSourceDir = null;
            // Input stream del file sorgente
            InputStream inputStreamJrReport = null;
            if (tiprog.equals(new Long(1))) {
                if (norma == 3L) {
                    PROP_JRREPORT_SOURCE_DIR = "jrReport/NuovaNormativa/norma3/";
                }
                jrReportSourceDir = PROP_JRREPORT_SOURCE_DIR;
                Resource resource = new ClassPathResource(PROP_JRREPORT_SOURCE_DIR + "allegatoI.jasper");
                inputStreamJrReport = resource.getInputStream();
            } else {
                if (norma == 3L) {
                    PROP_JRREPORT_SOURCE_DIR = "jrReport/NuovaNormativa/norma3/";
                }
                jrReportSourceDir = PROP_JRREPORT_SOURCE_DIR;
                Resource resource = new ClassPathResource(PROP_JRREPORT_SOURCE_DIR + "allegatoII.jasper");
                inputStreamJrReport = resource.getInputStream();
            }
            // Parametri
            HashMap<String, Object> jrParameters = new HashMap<String, Object>();
            jrParameters.put(JRParameter.REPORT_LOCALE, Locale.ITALIAN);
            jrParameters.put("SUBREPORT_DIR", jrReportSourceDir);
            jrParameters.put("COD_PIANOTRIEN", piatriID);
            jrParameters.put(JRParameter.REPORT_LOCALE, Locale.ITALY); // Imposta la localizzazione
            // Connessione
            jrConnection = dataSource.getConnection();
            // Stampa del formulario
            JasperPrint jrPrint = JasperFillManager.fillReport(inputStreamJrReport, jrParameters, jrConnection);
            jrPrint.setProperty("net.sf.jasperreports.print.locale", "it_IT");
            // Output stream del risultato
            ByteArrayOutputStream baosJrReport = new ByteArrayOutputStream();
            JRExporter jrExporter = new JRPdfExporter();
            jrExporter.setParameter(JRExporterParameter.JASPER_PRINT, jrPrint);
            jrExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baosJrReport);
            jrExporter.exportReport();

            inputStreamJrReport.close();
            baosJrReport.close();

            // Update di PIATRI per l'inserimento del file composto
            AllegatoEntry documento = new AllegatoEntry();
            documento.setFile(baosJrReport.toByteArray());
            documento.setNrDoc(idRicevuto);
            this.programmiMapper.setPdf(documento);
            logger.info("creaPdfNuovaNormativa terminato con successo");

        } catch (Exception e) {
            throw new Exception(e);
        } finally {
            try {
                if (jrConnection != null) {
                    jrConnection.close();
                }
            } catch (SQLException e) {
                logger.error("Errore durante la chiusura della transazione durante la creazione del pdf ", e);
            }
        }
    }


    public String getTokenPubblicazioneDiretta() {
        String token = null;
        try {
            String login = this.wConfigManager.getConfigurationValue(PROP_WS_PUBBLICAZIONI_USERNAME);
            String password = this.wConfigManager.getConfigurationValue(PROP_WS_PUBBLICAZIONI_PASSWORD);
            String urlLogin = this.wConfigManager.getConfigurationValue(PROP_WS_PUBBLICAZIONI_URL_LOGIN);
            String idClient = this.wConfigManager.getConfigurationValue(PROP_WS_PUBBLICAZIONI_IDCLIENT);
            String keyClient = this.wConfigManager.getConfigurationValue(PROP_WS_PUBBLICAZIONI_KEYCLIENT);

            Client client = getSSLClient();
            WebTarget webTarget = client.target(urlLogin).path("Account/LoginPubblica");
            MultivaluedMap<String, String> formData = new MultivaluedHashMap<String, String>();
            formData.add("username", login);
            formData.add("password", password);
            formData.add("clientKey", keyClient);
            formData.add("clientId", idClient);
            Response accesso = webTarget.request(MediaType.APPLICATION_JSON)
                    .post(Entity.entity(formData, MediaType.APPLICATION_FORM_URLENCODED));
            LoginResult resultAccesso = accesso.readEntity(LoginResult.class);

            if (resultAccesso.isEsito()) {
                token = resultAccesso.getToken();
            }
        } catch (Exception e) {
            logger.error("errore nel metodo: getTokenPubblicazioneDiretta ", e);
        }

        return token;
    }

    public PubblicazioneResult pubblicaFornitureServizi(PubblicaProgrammaFornitureServiziEntry p) {
        PubblicazioneResult res = new PubblicazioneResult();
        res.setEsito(false);
        String msg = "";
        int esito;
        String json = null;
        Long stato = Long.valueOf(3L);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            PubblicaProgrammaFornitureServiziSCPEntry programma = mapper.convertValue(p, PubblicaProgrammaFornitureServiziSCPEntry.class);

            String urlProgrammi = this.wConfigManager.getConfigurationValue(PROP_WS_PUBBLICAZIONI_URLPROGRAMMI);
            Client client = getSSLClient();
            WebTarget webTarget = null;


            String token = getTokenPubblicazioneDiretta();
            json = mapper.writeValueAsString(programma);
            webTarget = client.target(urlProgrammi).path("Programmi/PubblicaFornitureServizi")
                    .queryParam("token", token).queryParam("modalitaInvio", "2");
            Response risultatoProgrammaFS = webTarget.request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(programma), Response.class);

            esito = risultatoProgrammaFS.getStatus();

            PubblicazioneResultSCP risultatoPubblicazioneProgrammaFS = null;
            switch (esito) {
                case 200:
                    risultatoPubblicazioneProgrammaFS = risultatoProgrammaFS
                            .readEntity(PubblicazioneResultSCP.class);
                    msg = "ok";
                    stato = Long.valueOf(2L);
                    logger.info("inserisco il flusso");
                    ProgrammaPubblicatoForm form = new ProgrammaPubblicatoForm();
                    form.setStazioneAppaltante(p.getCodein());
                    form.setId(p.getContri());
                    form.setIdRicevuto(risultatoPubblicazioneProgrammaFS.getId().intValue());
                    this.programmiMapper.updateIdRicevuto(form);
                    // inserisco flusso
                    FlussoEntry flusso = new FlussoEntry();
                    Long idFlusso = this.wgcManager.getNextId("W9FLUSSI");
                    flusso.setId(idFlusso);
                    Long key03 = 981L;
                    flusso.setId(idFlusso);
                    flusso.setArea(4L);
                    flusso.setKey01(programma.getContri());
                    flusso.setKey03(key03);
                    flusso.setTipoInvio(1L);
                    flusso.setDataInvio(new Date());
                    flusso.setCodiceFiscaleSA(programma.getCodiceFiscaleSA());
                    flusso.setOggetto(programma.getContri()+"");
                    flusso.setJson(mapper.writeValueAsString(programma));
                    this.programmiMapper.insertFlusso(flusso);

                    break;
                case 400:
                    risultatoPubblicazioneProgrammaFS = risultatoProgrammaFS
                            .readEntity(PubblicazioneResultSCP.class);
                    for (ValidateEntry errore : risultatoPubblicazioneProgrammaFS.getValidate()) {
                        msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio()
                                + "\r\n";
                    }
                    logger.error("errore 400: {}",msg);
                    res.setErrorData(msg);
                    res.setEsito(false);
                    return res;
                default:
                    risultatoPubblicazioneProgrammaFS = risultatoProgrammaFS
                            .readEntity(PubblicazioneResultSCP.class);
                    msg = risultatoPubblicazioneProgrammaFS.getError();
                    logger.error("errore default: {}",msg);
                    res.setErrorData(msg);
                    res.setEsito(false);
                    return res;
            }
        } catch (Exception e) {
            logger.error("errore nel metodo: pubblicaFornitureServizi {} - {}", msg, e);
            res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            res.setEsito(false);
        }

        return res;
    }

    public PubblicazioneResult pubblicaLavori(PubblicaProgrammaLavoriEntry p) {
        PubblicazioneResult res = new PubblicazioneResult();
        res.setEsito(false);
        String msg = "";
        int esito;
        String json = null;
        Long stato = Long.valueOf(3L);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            PubblicaProgrammaLavoriSCPEntry programma = mapper.convertValue(p, PubblicaProgrammaLavoriSCPEntry.class);

            String urlProgrammi = this.wConfigManager.getConfigurationValue(PROP_WS_PUBBLICAZIONI_URLPROGRAMMI);
            Client client = getSSLClient();
            WebTarget webTarget = null;

            String token = getTokenPubblicazioneDiretta();
            json = mapper.writeValueAsString(programma);
            webTarget = client.target(urlProgrammi).path("Programmi/PubblicaLavori")
                    .queryParam("token", token).queryParam("modalitaInvio", "2");
            Response risultatoProgrammaLavori = webTarget.request(MediaType.APPLICATION_JSON)
                    .post(Entity.json(programma), Response.class);
            esito = risultatoProgrammaLavori.getStatus();

            PubblicazioneResultSCP risultatoPubblicazioneProgrammaLavori = null;
            switch (esito) {
                case 200:
                    risultatoPubblicazioneProgrammaLavori = risultatoProgrammaLavori
                            .readEntity(PubblicazioneResultSCP.class);
                    msg = "ok";
                    stato = Long.valueOf(2L);
                    res.setEsito(true);
                    logger.info("inserisco il flusso");
                    ProgrammaPubblicatoForm form = new ProgrammaPubblicatoForm();
                    form.setStazioneAppaltante(p.getCodein());
                    form.setId(p.getContri());
                    form.setIdRicevuto(risultatoPubblicazioneProgrammaLavori.getId().intValue());
                    this.programmiMapper.updateIdRicevuto(form);
                    // inserisco flusso
                    FlussoEntry flusso = new FlussoEntry();
                    Long idFlusso = this.wgcManager.getNextId("W9FLUSSI");
                    flusso.setId(idFlusso);
                    Long key03 = 982L;
                    flusso.setId(idFlusso);
                    flusso.setArea(4L);
                    flusso.setKey01(programma.getContri());
                    flusso.setKey03(key03);
                    flusso.setTipoInvio(1L);
                    flusso.setDataInvio(new Date());
                    flusso.setCodiceFiscaleSA(programma.getCodiceFiscaleSA());
                    flusso.setOggetto(programma.getContri()+"");
                    flusso.setJson(mapper.writeValueAsString(programma));
                    this.programmiMapper.insertFlusso(flusso);

                    break;
                case 400:
                    risultatoPubblicazioneProgrammaLavori = risultatoProgrammaLavori
                            .readEntity(PubblicazioneResultSCP.class);
                    for (ValidateEntry errore : risultatoPubblicazioneProgrammaLavori.getValidate()) {
                        msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio()
                                + "\r\n";
                    }
                    logger.error("errore 400: {}",msg);
                    List<String> errorList = List.of(msg);
                    res.setValidationError(errorList);
                    res.setEsito(false);
                    return res;
                default:
                    risultatoPubblicazioneProgrammaLavori = risultatoProgrammaLavori
                            .readEntity(PubblicazioneResultSCP.class);
                    msg = risultatoPubblicazioneProgrammaLavori.getError();
                    logger.error("errore default: {}",msg);
                    List<String> errorList2 = List.of(msg);
                    res.setValidationError(errorList2);
                    res.setEsito(false);
                    return res;
            }
        } catch (Exception e) {
            logger.error("errore nel metodo: pubblicaLavori {} - {}", msg, e);
            res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            res.setEsito(false);
        }

        return res;
    }

    /****
    **** INVIO DATI A REGIONE LOMBARDIA: INIZIO
    ****/

    //TODO: Implementazione invio Programmi Forniture e Servizi per Regione Lombardia
    @Autowired AccessTokenRL accessTokenRL;
    @Autowired AuthServiceRL authServiceRL;
    @Autowired ProgrammiBiennaliRLService programmiBiennaliRLService;
    @Autowired ProgrammiTriennaliRLService programmiTriennaliRLService;

    public PubblicazioneResult pubblicaFornitureServiziRegioneLombardia(UserAuthClaimsDTO userAuthClaimsDTO,PubblicaProgrammaFornitureServiziEntry p) {
        PubblicazioneResult res = new PubblicazioneResult();
        res.setEsito(false);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            PubblicaProgrammaFornitureServiziSCPEntry programma = mapper.convertValue(p, PubblicaProgrammaFornitureServiziSCPEntry.class);
            ConfigServiceRL configServiceRL=ConfigServiceRL.build(this.wConfigManager);
            authServiceRL.invokeToken(configServiceRL);
            String codiceFiscaleSA=programma.getCodiceFiscaleSA();
            try{
                FornitureAcquistiDto fornitureAcquistiDto=programmiBiennaliRLService.leggi(configServiceRL,accessTokenRL,programma.getAnno().intValue(),codiceFiscaleSA);
                Long id=fornitureAcquistiDto.getId();
                programma.setIdRicevuto(id);
                logger.debug(">>> READ id="+id);
            }catch(Exception e){
                logger.warn("ERRORE: anno={} codiceFiscaleSA={} errore={}",programma.getAnno(),programma.getCodiceFiscaleSA(),e);
            }

            try {
                logger.debug(">>> IdRicevuto="+programma.getIdRicevuto());
                Map<String,Object> params=new HashMap<>();
                params.put(ARestService.PARAM_RUP_NAZIONE,"IT");
                params.put(ARestService.PARAM_SA_CODICE,p.getCodein());

                Pair<Long,String> result=programmiBiennaliRLService.invia(configServiceRL,accessTokenRL,programma,params);
                logger.debug(">>> INSERT id="+result.getLeft());
                logger.debug(">>> INSERT result="+result.getRight());
                int anno=programma.getAnno().intValue();
                logger.debug(">>> anno="+anno+" codiceFiscaleSA="+codiceFiscaleSA);
                res.setEsito(true);

                ProgrammaPubblicatoForm form=new ProgrammaPubblicatoForm();
                form.setId(programma.getContri());
                form.setIdRicevuto(Math.toIntExact(result.getLeft()));
                form.setSyscon(userAuthClaimsDTO.getSyscon());
                form.setStazioneAppaltante(p.getCodein());
                form.setPayload(result.getRight());

                Long syscon = userAuthClaimsDTO.getSyscon();
                BaseResponse risultato = updateIdRicevutoFS(form, syscon);
                logger.debug(">>> "+risultato.getErrorData());
            } catch (HttpClientErrorException e) {
                logger.error("ERROR: {}",e);
                int statusCode = e.getStatusCode().value();
                String responseBody = e.getResponseBodyAsString();
                logger.error("statusCode={} response={}",statusCode,responseBody);
                List<String> errorList=programmiBiennaliRLService.getErrorResult(responseBody);
                res.setValidationError(errorList);
            } catch (HttpServerErrorException e) {
                logger.error("ERRORE: {}",e);
                res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            } catch (ResourceAccessException e) {
                logger.error("Errore di connessione");
                logger.error("ERRORE: {}",e);
                res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            } catch (RestClientException e) {
                logger.error("Errore generale");
                logger.error("ERRORE: {}",e);
                res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }
        } catch (Exception e) {
            logger.error("errore nel metodo: pubblicaFornitureServiziRegioneLombardia {}", e);
            res.setErrorData("Errore generale");
        }
        return res;
    }

    public PubblicazioneResult pubblicaLavoriRegioneLombardia(UserAuthClaimsDTO userAuthClaimsDTO,PubblicaProgrammaLavoriEntry p) {
        PubblicazioneResult res = new PubblicazioneResult();
        res.setEsito(false);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            PubblicaProgrammaLavoriSCPEntry programma = mapper.convertValue(p, PubblicaProgrammaLavoriSCPEntry.class);
            ConfigServiceRL configServiceRL=ConfigServiceRL.build(this.wConfigManager);
            authServiceRL.invokeToken(configServiceRL);
            String codiceFiscaleSA=programma.getCodiceFiscaleSA();
            try{
                LavoriDto lavoriDto=programmiTriennaliRLService.leggi(configServiceRL,accessTokenRL,programma.getAnno().intValue(),codiceFiscaleSA);
                Long id=lavoriDto.getId();
                programma.setIdRicevuto(id);
                logger.debug(">>> READ id="+id);
            }catch(Exception e){
                logger.warn("ERRORE: anno={} codiceFiscaleSA={} errore={}",programma.getAnno(),programma.getCodiceFiscaleSA(),e);
            }

            try {
                logger.debug(">>> IdRicevuto="+programma.getIdRicevuto());
                Map<String,Object> params=new HashMap<>();
                params.put(ARestService.PARAM_RUP_NAZIONE,"IT");
                params.put(ARestService.PARAM_SA_CODICE,p.getCodein());

                Pair<Long,String> result=programmiTriennaliRLService.invia(configServiceRL,accessTokenRL,programma,params);
                logger.debug(">>> INSERT id="+result.getLeft());
                logger.debug(">>> INSERT result="+result.getRight());
                int anno=programma.getAnno().intValue();
                logger.debug(">>> anno="+anno+" codiceFiscaleSA="+codiceFiscaleSA);
                res.setEsito(true);

                ProgrammaPubblicatoForm form=new ProgrammaPubblicatoForm();
                form.setId(programma.getContri());
                form.setIdRicevuto(Math.toIntExact(result.getLeft()));
                form.setSyscon(userAuthClaimsDTO.getSyscon());
                form.setStazioneAppaltante(p.getCodein());
                form.setPayload(result.getRight());

                Long syscon = userAuthClaimsDTO.getSyscon();
                BaseResponse risultato = updateIdRicevutoL(form, syscon);
                logger.debug(">>> "+risultato.getErrorData());
            } catch (HttpClientErrorException e) {
                logger.error("ERROR: {}",e);
                int statusCode = e.getStatusCode().value();
                String responseBody = e.getResponseBodyAsString();
                logger.error("statusCode={} response={}",statusCode,responseBody);
                List<String> errorList=programmiBiennaliRLService.getErrorResult(responseBody);
                res.setValidationError(errorList);
            } catch (HttpServerErrorException e) {
                logger.error("ERRORE: {}",e);
                res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            } catch (ResourceAccessException e) {
                logger.error("Errore di connessione");
                logger.error("ERRORE: {}",e);
                res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            } catch (RestClientException e) {
                logger.error("Errore generale");
                logger.error("ERRORE: {}",e);
                res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
            }
        } catch (Exception e) {
            logger.error("errore nel metodo: pubblicaFornitureServiziRegioneLombardia {}", e);
            res.setErrorData("Errore generale");
        }
        return res;
    }

    /****
     **** INVIO DATI A REGIONE LOMBARDIA: FINE
     ****/

    @SuppressWarnings("java:S5527")
    public Client getSSLClient() throws Exception {
        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, new TrustManager[]{new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

        }}, new java.security.SecureRandom());


        Client client = ClientBuilder.newBuilder().sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).build();
        if (enableProxy) {
            logger.info("Proxy applicativo abilitato");
            String proxy = StringUtils.isNotBlank(httpsProxy) ? httpsProxy : httpProxy;

            logger.info("Valore proxy " + proxy);

            ClientConfig config = new ClientConfig();
            config.property(ClientProperties.PROXY_URI, proxy);
            config.connectorProvider(new ApacheConnectorProvider());
            client = JerseyClientBuilder.newBuilder().withConfig(config).sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).build();

        }
        return client;
    }

    public ResponseDto getDettaglioInterventoNonRiproposti(Long idProgramma, Long idIntervento) {
        ResponseDto response = new ResponseDto();
        response.setEsito(true);
        try {
            InterventoNonRipropostoEntry intervento = this.programmiMapper.getInterventoNonRiproposto(idProgramma, idIntervento);
            if (intervento != null) {
                response.setData(intervento);
            }
        } catch (Exception e) {
            logger.error("Si è verificato un errore durante l'esecuzione del metodo getDettaglioInterventoNonRiproposti", e);
            response.setEsito(false);
            response.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        return response;
    }

    /**
     * Metodo che verifica l'associazione dell'utente all'ufficio intestatario
     *
     * @param syscon id utente
     * @param codein id ufficio intestatario
     * @return Booleano che indica se l'utente e' associato all'ufficio intestatario
     */
    public boolean hasUserSaPermission(final Long syscon, final String codein) {
        Long count = programmiMapper.checkUserSaPermission(syscon, codein);
        return count != null && count == 1L;
    }

    public boolean isUtenteAbilitatoTutteSA(final Long syscon) {

        if (syscon == null)
            return false;

        String opzioniUtenteString = getAbilitazioniUtente(syscon);

        if (StringUtils.isBlank(opzioniUtenteString))
            return false;

        List<String> opzioniUtente = Arrays.stream(opzioniUtenteString.split(Constants.OU_SEPARATORE_REGEX))
                .filter(str -> !str.isEmpty()).collect(Collectors.toList());

        if (opzioniUtente == null || (opzioniUtente != null && opzioniUtente.isEmpty()))
            return false;

        return opzioniUtente.contains(Constants.OU_ABILITA_TUTTE_SA);
    }

    public boolean filterProgrammaBySyscon(final Long syscon) {

        String config = wConfigManager.getConfigurationValue("it.eldasoft.pt.filtroUtente");

        if (StringUtils.isBlank(config))
            config = wConfigManager.getConfigurationValue("ALT.PIANI.APPLICA-FILTRO-UTENTE");

        if (config == null)
            return true;

        if ("1".equals(config)) {
            String ruolo = this.programmiMapper.getRuolo(syscon);
            return !"A".equals(ruolo) && !"C".equals(ruolo);
        } else return !"0".equals(config);
    }
}
