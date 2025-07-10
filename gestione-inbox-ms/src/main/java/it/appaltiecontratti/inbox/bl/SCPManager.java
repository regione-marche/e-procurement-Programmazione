package it.appaltiecontratti.inbox.bl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.appaltiecontratti.inbox.entity.DatiGeneraliStazioneAppaltanteEntry;
import it.appaltiecontratti.inbox.entity.InserimentoResult;
import it.appaltiecontratti.inbox.entity.OutboxEntry;
import it.appaltiecontratti.inbox.entity.PubblicazioneResult;
import it.appaltiecontratti.inbox.entity.avvisi.PubblicaAvvisoEntry;
import it.appaltiecontratti.inbox.entity.contratti.PubblicaAttoEntry;
import it.appaltiecontratti.inbox.entity.contratti.PubblicaGaraEntry;
import it.appaltiecontratti.inbox.entity.contratti.PubblicazioneAttoResult;
import it.appaltiecontratti.inbox.entity.contratti.ValidateEntry;
import it.appaltiecontratti.inbox.entity.programmi.PubblicaProgrammaFornitureServiziEntry;
import it.appaltiecontratti.inbox.entity.programmi.PubblicaProgrammaLavoriEntry;
import it.appaltiecontratti.inbox.mapper.SCPMapper;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.JerseyClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.Timestamp;


@SuppressWarnings("java:S4830")
@Component(value = "scpManager")
public class SCPManager {

    /**
     * Url Servizio Di pubblicazione avvisi, atti e fasi gara lotto
     */
    private static final String PROP_WS_PUBBLICAZIONI_URL = "it.eldasoft.pubblicazioni.ws.url";

    /**
     * Url Servizio Di pubblicazione programmi
     */
    private static final String PROP_WS_PUBBLICAZIONI_URLPROGRAMMI = "it.eldasoft.pubblicazioni.ws.urlProgrammi";

    /**
     * Url Servizio Delle tabelle di contesto
     */
    private static final String PROP_WS_PUBBLICAZIONI_URLTABELLECONTESTO = "it.eldasoft.pubblicazioni.ws.urlTabelleContesto";

    /**
     * Logger di classe.
     */
    private static Logger logger = LogManager.getLogger(SCPManager.class);

    @Autowired
    ProgrammiManager programmiManager;

    @Autowired
    ContrattiManager contrattiManager;

    @Autowired
    StazioniAppaltantiManager stazioniAppaltantiManager;

    @Autowired
    AvvisiManager avvisiManager;

    @Autowired
    SCPMapper scpMapper;

    @Value("${application.enableProxy:false}")
    private boolean enableProxy;

    @Value("${http_proxy:#{null}}")
    private String httpProxy;

    @Value("${no_proxy:#{null}}")
    private String noProxy;

    @Value("${https_proxy:#{null}}")
    private String httpsProxy;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void invioSCP(OutboxEntry itemW9OUTBOX, String token, final String fileAllegatoAvvisiUrl, final String fileAllegatoAttiUrl) throws Exception {
        String msg = "";
        int esito;
        Long idRicevuto = null;
        String json = null;
        Long idComun = Long.valueOf(itemW9OUTBOX.getIdcomun());
        Long stato = Long.valueOf(3L);
        boolean goToPubblicazione = false;
        try {
            String url = this.scpMapper.getConfigurazione(PROP_WS_PUBBLICAZIONI_URL);
            String urlProgrammi = this.scpMapper.getConfigurazione(PROP_WS_PUBBLICAZIONI_URLPROGRAMMI);
            String urlTabelleContesto = this.scpMapper.getConfigurazione(PROP_WS_PUBBLICAZIONI_URLTABELLECONTESTO);
            Client client = getSSLClient();
            WebTarget webTarget = null;
            Long key01 = null, key02 = null, key03 = null, key04 = null;
            ObjectMapper mapper = new ObjectMapper();
            String cfsa = null;
            String codein = null;

            if (itemW9OUTBOX.getCfsa() != null) {
                cfsa = itemW9OUTBOX.getCfsa();
            }
            if (cfsa != null) {
                codein = scpMapper.getCodeinByCfSa(cfsa);
            } else {
                msg = "CSFA nullo";
                logger.info("Flusso IDCOMUN = " + itemW9OUTBOX.getIdcomun() + " non inviato, CFSA nullo");
            }
            if (codein != null) {
                DatiGeneraliStazioneAppaltanteEntry stazioneAppaltante = stazioniAppaltantiManager
                        .valorizzaStazioneAppaltante(codein);
                if (stazioneAppaltante == null) {
                    throw new Exception("Errore in invio a SCP. Non esiste nel db la stazione appaltante " + codein
                            + " per il record idcomun =" + itemW9OUTBOX.getIdcomun());
                }
                json = mapper.writeValueAsString(stazioneAppaltante);
                webTarget = client.target(urlTabelleContesto).path("StazioniAppaltanti/Pubblica").queryParam("token",
                        token);
                Response verificaStazioneAppaltante = webTarget.request(MediaType.APPLICATION_JSON)
                        .post(Entity.json(stazioneAppaltante), Response.class);
                esito = verificaStazioneAppaltante.getStatus();
                InserimentoResult risultatoValidazioneSA = verificaStazioneAppaltante
                        .readEntity(InserimentoResult.class);
                switch (esito) {
                    case 200:
                        goToPubblicazione = true;
                        break;
                    case 400:
                        for (ValidateEntry errore : risultatoValidazioneSA.getValidate()) {
                            msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio() + "\r\n";
                        }
                        break;
                    default:
                        msg = risultatoValidazioneSA.getError();
                        break;
                }
                if (goToPubblicazione) {
                    key01 = itemW9OUTBOX.getKey01();
                    if (itemW9OUTBOX.getKey02() != null) {
                        key02 = itemW9OUTBOX.getKey02();
                    }
                    key03 = itemW9OUTBOX.getKey03();
                    if (itemW9OUTBOX.getKey04() != null) {
                        key04 = itemW9OUTBOX.getKey04();
                    }

                    switch (key03.intValue()) {
                        case 989:
                            // avviso
                            PubblicaAvvisoEntry avviso = avvisiManager.valorizzaAvviso(codein, key01, fileAllegatoAvvisiUrl);

                            if (avviso == null) {
                                throw new Exception(
                                        "Errore in invio a SCP. Non esiste nel db l'avviso con stazione appaltante "
                                                + codein + " e id " + itemW9OUTBOX.getIdcomun() + " per il record idcomun ="
                                                + itemW9OUTBOX.getIdcomun());
                            }
                            json = mapper.writeValueAsString(avviso);
                            webTarget = client.target(url).path("Avvisi/Pubblica").queryParam("token", token)
                                    .queryParam("modalitaInvio", "2");
                            Response risultatoAvviso = webTarget.request(MediaType.APPLICATION_JSON)
                                    .post(Entity.json(avviso), Response.class);
                            esito = risultatoAvviso.getStatus();
                            PubblicazioneResult risultatoPubblicazioneAvviso = risultatoAvviso
                                    .readEntity(PubblicazioneResult.class);
                            // JSONObject output = new JSONObject(risultatoAvviso.readEntity(String.class));
                            switch (esito) {
                                case 200:
                                    avviso.setIdRicevuto(risultatoPubblicazioneAvviso.getId());
                                    this.avvisiManager.updateIdRicevuto(avviso.getIdRicevuto(), codein, key01);
                                    idRicevuto = risultatoPubblicazioneAvviso.getId();
                                    msg = "ok";
                                    stato = Long.valueOf(2L);
                                    break;
                                case 400:
                                    for (ValidateEntry errore : risultatoPubblicazioneAvviso.getValidate()) {
                                        msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio()
                                                + "\r\n";
                                    }
                                    break;
                                default:
                                    msg = risultatoPubblicazioneAvviso.getError();
                                    break;
                            }
                            break;
                        case 901:
                            // atti
                            PubblicaAttoEntry pubblicazione = this.contrattiManager.valorizzaAtto(key01, key04, fileAllegatoAttiUrl);
                            if (pubblicazione == null) {
                                throw new Exception("Errore in invio a SCP. Non esiste nel db l'atto con codgara " + key01
                                        + " e numpubb " + key04 + " per il record idcomun =" + itemW9OUTBOX.getIdcomun());
                            }
                            json = mapper.writeValueAsString(pubblicazione);
                            webTarget = client.target(url).path("Atti/Pubblica").queryParam("token", token)
                                    .queryParam("modalitaInvio", "2");
                            Response risultatoAtto = webTarget.request(MediaType.APPLICATION_JSON)
                                    .post(Entity.json(pubblicazione), Response.class);
                            esito = risultatoAtto.getStatus();
                            PubblicazioneAttoResult risultatoPubblicazioneAtto = risultatoAtto
                                    .readEntity(PubblicazioneAttoResult.class);
                            switch (esito) {
                                case 200:
                                    pubblicazione.setIdRicevuto(risultatoPubblicazioneAtto.getIdExArt29());
                                    pubblicazione.getGara().setIdRicevuto(risultatoPubblicazioneAtto.getIdGara());

                                    this.contrattiManager.updateIdRicevutoAtto(pubblicazione.getIdRicevuto(), key01, key04);
                                    this.contrattiManager.updateIdRicevutoGara(pubblicazione.getGara().getIdRicevuto(), key01);
                                    idRicevuto = risultatoPubblicazioneAtto.getIdExArt29();
                                    msg = "ok";
                                    stato = Long.valueOf(2L);
                                    break;
                                case 400:
                                    for (ValidateEntry errore : risultatoPubblicazioneAtto.getValidate()) {
                                        msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio()
                                                + "\r\n";
                                    }
                                    break;
                                default:
                                    msg = risultatoPubblicazioneAtto.getError();
                                    break;
                            }
                            break;
                        case 988:
                            // anagrafica gara lotti
                            PubblicaGaraEntry gara = contrattiManager.valorizzaGara(key01);
                            json = mapper.writeValueAsString(gara);
                            webTarget = client.target(url).path("Anagrafiche/GaraLotti").queryParam("token", token)
                                    .queryParam("modalitaInvio", "2");
                            Response risultatoGaraLotti = webTarget.request(MediaType.APPLICATION_JSON)
                                    .post(Entity.json(gara), Response.class);
                            esito = risultatoGaraLotti.getStatus();
                            PubblicazioneResult risultatoPubblicazioneGaraLotti = risultatoGaraLotti
                                    .readEntity(PubblicazioneResult.class);
                            switch (esito) {
                                case 200:
                                    gara.setIdRicevuto(risultatoPubblicazioneGaraLotti.getId());
                                    this.contrattiManager.updateIdRicevutoGara(gara.getIdRicevuto(), key01);
                                    idRicevuto = risultatoPubblicazioneGaraLotti.getId();
                                    msg = "ok";
                                    stato = Long.valueOf(2L);
                                    break;
                                case 400:
                                    for (ValidateEntry errore : risultatoPubblicazioneGaraLotti.getValidate()) {
                                        msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio()
                                                + "\r\n";
                                    }
                                    break;
                                default:
                                    msg = risultatoPubblicazioneGaraLotti.getError();
                                    break;
                            }
                            break;
                        case 982:
                            // programma lavori
                            PubblicaProgrammaLavoriEntry programma = programmiManager.valorizzaProgrammaLavori(key01);
                            json = mapper.writeValueAsString(programma);
                            webTarget = client.target(urlProgrammi).path("Programmi/PubblicaLavori")
                                    .queryParam("token", token).queryParam("modalitaInvio", "2");
                            Response risultatoProgrammaLavori = webTarget.request(MediaType.APPLICATION_JSON)
                                    .post(Entity.json(programma), Response.class);
                            esito = risultatoProgrammaLavori.getStatus();
                            PubblicazioneResult risultatoPubblicazioneProgrammaLavori = risultatoProgrammaLavori
                                    .readEntity(PubblicazioneResult.class);
                            switch (esito) {
                                case 200:
                                    programma.setIdRicevuto(risultatoPubblicazioneProgrammaLavori.getId());
                                    this.programmiManager.updateIdRicevutoProgramma(programma.getIdRicevuto(), key01);
                                    idRicevuto = risultatoPubblicazioneProgrammaLavori.getId();
                                    msg = "ok";
                                    stato = Long.valueOf(2L);
                                    break;
                                case 400:
                                    for (ValidateEntry errore : risultatoPubblicazioneProgrammaLavori.getValidate()) {
                                        msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio()
                                                + "\r\n";
                                    }
                                    break;
                                default:
                                    msg = risultatoPubblicazioneProgrammaLavori.getError();
                                    break;
                            }
                            break;
                        case 981:
                            // programma forniture servizi
                            PubblicaProgrammaFornitureServiziEntry programmaFS = programmiManager
                                    .valorizzaProgrammaFornitureServizi(key01);
                            logger.debug("##### Attributi dell'oggetto programmaFS: " + programmaFS.toString());
                            json = mapper.writeValueAsString(programmaFS);
                            logger.debug("##### Attributi json: " + json);
                            webTarget = client.target(urlProgrammi).path("Programmi/PubblicaFornitureServizi")
                                    .queryParam("token", token).queryParam("modalitaInvio", "2");
                            Response risultatoProgrammaFS = webTarget.request(MediaType.APPLICATION_JSON)
                                    .post(Entity.json(programmaFS), Response.class);
                            logger.debug("##### Response: {}", risultatoProgrammaFS);
                            logger.debug("##### entity: {}", risultatoProgrammaFS.getEntity());
                            
                            esito = risultatoProgrammaFS.getStatus();
                            logger.debug("##### esito: " + esito);
                            
                            PubblicazioneResult risultatoPubblicazioneProgrammaFS = risultatoProgrammaFS
                                    .readEntity(PubblicazioneResult.class);
                            switch (esito) {
                                case 200:
                                    programmaFS.setIdRicevuto(risultatoPubblicazioneProgrammaFS.getId());
                                    programmiManager.updateIdRicevutoProgramma(programmaFS.getIdRicevuto(), key01);
                                    idRicevuto = risultatoPubblicazioneProgrammaFS.getId();
                                    msg = "ok";
                                    stato = Long.valueOf(2L);
                                    break;
                                case 400:
                                    for (ValidateEntry errore : risultatoPubblicazioneProgrammaFS.getValidate()) {
                                        msg += errore.getNome() + "(" + errore.getTipo() + ") - " + errore.getMessaggio()
                                                + "\r\n";
                                    }
                                    break;
                                default:
                                    msg = risultatoPubblicazioneProgrammaFS.getError();
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                }
            } else {
                msg = "CODEIN nullo";
                logger.info("Flusso IDCOMUN = " + itemW9OUTBOX.getIdcomun() + " non inviato, CODEIN nullo");
            }
            if (msg.length() > 2000) {
                msg = msg.substring(0, 1999);
            }
            // aggiorno record tabella W9OUTBOX
            this.scpMapper.updateOutbox(new Timestamp(System.currentTimeMillis()), json, stato, msg, idRicevuto,
                    idComun);
        } catch (Exception ex) {
            msg = "Errore non previsto " + ex.getMessage();
            logger.error(msg, ex);
            throw ex;
        }
    }


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
            String proxyHost = null;
            int proxyPort = 0;
//            Disabilitato per implementazione libreria apache connector che vuole http:// o https://
//            if (StringUtils.isNotBlank(proxy)) {
//                proxy = proxy.replace("http://", "").replace("https://", "");
//                proxyHost = proxy.substring(0, proxy.lastIndexOf(":"));
//                proxyPort = Integer.valueOf(proxy.substring(proxy.lastIndexOf(":") + 1)).intValue();
//            }
            logger.info("Valore proxy " + proxy);

            ClientConfig config = new ClientConfig();
            config.property(ClientProperties.PROXY_URI, proxy);
            config.connectorProvider(new ApacheConnectorProvider());
            client = JerseyClientBuilder.newBuilder().withConfig(config).sslContext(sslcontext).hostnameVerifier((s1, s2) -> true).build();

        }
        return client;
    }

}
