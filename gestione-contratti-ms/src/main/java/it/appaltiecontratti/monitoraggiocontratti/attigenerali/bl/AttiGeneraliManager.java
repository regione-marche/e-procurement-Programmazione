package it.appaltiecontratti.monitoraggiocontratti.attigenerali.bl;

import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.ResponseResult;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.*;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.forms.AttoGeneraleInsertForm;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.forms.AttoGeneraleUpdateForm;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.forms.RicercaAttiGeneraliForm;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.mapper.AttiGeneraliMapper;
import it.appaltiecontratti.monitoraggiocontratti.contratti.bl.WGenChiaviManager;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LogEventiEntry;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.responses.BaseResponse;
import it.appaltiecontratti.monitoraggiocontratti.contratti.utils.Constants;
import it.appaltiecontratti.sicurezza.bl.UserManager;
import it.appaltiecontratti.sicurezza.entity.UserAuthClaimsDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Manager per la gestione della business logic.
 *
 * @author andrea.chinellato
 */
@Component(value = "attiGeneraliManager")
public class AttiGeneraliManager {

    /** Logger di classe. */
    private static final Logger logger = LogManager.getLogger(AttiGeneraliManager.class);
    /**
     * Dao MyBatis con le primitive di estrazione dei dati.
     */
    @Autowired
    private AttiGeneraliMapper attiGeneraliMapper;

    @Autowired
    private UserManager userManager;

    @Autowired
    protected WGenChiaviManager wgcManager;

    public ResponseListaAttiGenerali searchAttiGenerali(RicercaAttiGeneraliForm form, String authorization, String xLoginMode){
        ResponseListaAttiGenerali response = new ResponseListaAttiGenerali();
        response.setEsito(true);

        try {

            // Estrazione Cod Fiscale
            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
            String cf = userAuthClaimsDTO.getCf();
            Long syscon = userAuthClaimsDTO.getSyscon();
            boolean cfNull = false;

            String ruolo = this.attiGeneraliMapper.getRuolo(form.getSyscon());
            if(form.getStazioneAppaltante() != null && "*".equals(form.getStazioneAppaltante())) {
                form.setStazioneAppaltante(null);
            }
            form.setSyscon(syscon);
            form.setCfTecnico(StringUtils.isNotBlank(cf) ? cf.toUpperCase() : null);
            String cfTecnico = form.getCfTecnico();
            if ("A".equals(ruolo) || "C".equals(ruolo)) {
                form.setCfTecnico(null);
            } else{
                if(StringUtils.isBlank(cfTecnico)) {
                    cfNull = true;
                }
            }
            form.setCfNull(cfNull);

            RowBounds rowBounds = new RowBounds(form.getSkip(), form.getTake());
            int totalCount = this.attiGeneraliMapper.countSearchAttiGenerali(form);
            List<AttoGeneraleEntry> listaAttiGenerali = this.attiGeneraliMapper.getListaAttiGeneraliMap(form, rowBounds);

            for(AttoGeneraleEntry attoGenerale : listaAttiGenerali){
                if(!StringUtils.isEmpty(attoGenerale.getRup())){
                    RupEntry tecnico = this.attiGeneraliMapper.getTecnico(attoGenerale.getRup());

                    attoGenerale.setTecnico(tecnico);
                }

                attoGenerale.setPubblicato(attoGenerale.getDataPubbSistema() != null && attoGenerale.getDataPubbSistema().before(new Date()));
                attoGenerale.setAnnullato(attoGenerale.getMotivoCanc() != null && attoGenerale.getDataCanc() != null && attoGenerale.getUtenteCanc() != null);
            }

            response.setData(listaAttiGenerali);
            response.setTotalCount(totalCount);

        } catch (Exception e) {
            logger.error("Errore durante la ricerca degli atti generali.", e);
            response.setEsito(false);
            response.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return response;
    }

    public ResponseDettaglioAttoGenerale getDettaglioAttoGeneraleMap(RicercaAttiGeneraliForm form, String authorization, String xLoginMode){

        ResponseDettaglioAttoGenerale response = new ResponseDettaglioAttoGenerale();
        response.setEsito(true);

        try {

            // Estrazione Cod Fiscale
            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
            String cf = userAuthClaimsDTO.getCf();
            Long syscon = userAuthClaimsDTO.getSyscon();
            boolean cfNull = false;

            String ruolo = this.attiGeneraliMapper.getRuolo(form.getSyscon());
            if(form.getStazioneAppaltante() != null && form.getStazioneAppaltante().contains("*")) {
                form.setStazioneAppaltante(null);
            }
            form.setSyscon(syscon);
            form.setCfTecnico(StringUtils.isNotBlank(cf) ? cf.toUpperCase() : null);
            String cfTecnico = form.getCfTecnico();
            if ("A".equals(ruolo) || "C".equals(ruolo)) {
                form.setCfTecnico(null);
            } else{
                if(StringUtils.isBlank(cfTecnico)) {
                    cfNull = true;
                }
            }
            form.setCfNull(cfNull);

            AttoGeneraleEntry dettaglioAttoGenerale = this.attiGeneraliMapper.getDettaglioAttoGeneraleMap(form);

            String nominativoSA = this.attiGeneraliMapper.getNominativoSa(dettaglioAttoGenerale.getStazioneAppaltante());
            if(!StringUtils.isEmpty(nominativoSA)){
                dettaglioAttoGenerale.setStazioneAppaltante(nominativoSA);
            }

            if(!StringUtils.isEmpty(dettaglioAttoGenerale.getRup())){
                RupEntry tecnico = this.attiGeneraliMapper.getTecnico(dettaglioAttoGenerale.getRup());

                dettaglioAttoGenerale.setTecnico(tecnico);
            }

            if(!StringUtils.isEmpty(dettaglioAttoGenerale.getRup())){
                dettaglioAttoGenerale.setRup(this.attiGeneraliMapper.getRupBycod(dettaglioAttoGenerale.getRup()));
            }

            //Recupero info allegati
            List<AllegatoEntry> allegati = this.attiGeneraliMapper.getListaAllegatiAttoGeneraleMap(form.getIdAtto().toString());
            dettaglioAttoGenerale.setDocuments(allegati);

            response.setData(dettaglioAttoGenerale);
            response.setTotalCount(1);

        } catch (Exception e) {
            logger.error("Errore durante la ricerca degli atti generali.", e);
            response.setEsito(false);
            response.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult updateDettaglioAttoGenerale(AttoGeneraleUpdateForm form, String authorization, String xLoginMode){

        ResponseResult response = new ResponseResult();
        response.setEsito(true);

        try {

            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
            Long syscon = userAuthClaimsDTO.getSyscon();

            if(!StringUtils.isEmpty(form.getStazioneAppaltante())){
                form.setStazioneAppaltante(this.attiGeneraliMapper.getCodeinByNameSA(form.getStazioneAppaltante()));
            }

            RicercaAttiGeneraliForm attoFormBeforeUpdate = new RicercaAttiGeneraliForm();
            attoFormBeforeUpdate.setIdAtto(form.getIdAtto());
            attoFormBeforeUpdate.setSyscon(syscon);
            AttoGeneraleEntry attoGeneraleBeforeUpdate = this.attiGeneraliMapper.getDettaglioAttoGeneraleMap(attoFormBeforeUpdate);

            if(StringUtils.equals(attoGeneraleBeforeUpdate.getPrimaPubb(), Constants.PRIMA_PUBBLICAZIONE_ATTO_NO) &&
                    StringUtils.equals(form.getPrimaPubb(), Constants.PRIMA_PUBBLICAZIONE_ATTO)) {
                //Sbianco il campo "Data prima pubblicazione"
                form.setDataPrimaPubb(null);
            }

            this.attiGeneraliMapper.updateDettaglioAttoGenerale(form);

            RicercaAttiGeneraliForm attoForm = new RicercaAttiGeneraliForm();
            attoForm.setIdAtto(form.getIdAtto());
            attoForm.setSyscon(syscon);
            AttoGeneraleEntry attoGenerale = this.attiGeneraliMapper.getDettaglioAttoGeneraleMap(attoForm);

            //GESTIONE ALLEGATI: Vengono gestiti solo l'aggiunta e l'aggiornamento di ogni allegato perché
            // la cancellazione è demandata tramite pulsante con altro metodo.
            List<AllegatoEntry> allegatiEsistenti = this.attiGeneraliMapper.getListaAllegatiAttoGeneraleMap(form.getIdAtto().toString());
            List<AllegatoEntry> daInserire = new ArrayList<>();
            List<AllegatoEntry> daAggiornare = new ArrayList<>();
            List<AllegatoEntry> daEliminare = new ArrayList<>();
            List<AllegatoMotivazioneEntry> daAnnullare = new ArrayList<>();

            for(AllegatoEntry allegatoEsistente : allegatiEsistenti){
                boolean isPresent = form.getDocuments().stream()
                        .anyMatch(allegato ->
                                allegato.getIdAllegato() != null &&
                                        allegato.getIdAllegato().toString().equals(allegatoEsistente.getIdAllegato().toString()) &&
                                        allegato.getKey01() != null &&
                                        allegato.getKey01().equals(allegatoEsistente.getKey01()) &&
                                        allegato.getTabella().equals("W9ATTI_GENERALI") &&
                                        allegatoEsistente.getTabella().equals("W9ATTI_GENERALI")
                        );

                if(!isPresent && (form.getDataPubbSistema() == null || form.getDataPubbSistema().after(new Date()))){
                    daEliminare.add(allegatoEsistente);
                }
            }

            for(AllegatoMotivazioneEntry allegato : form.getAllegatiDaAnnullare()){
                if(!StringUtils.isEmpty(allegato.getMotivoCanc())){
                    daAnnullare.add(allegato);
                }
            }

            //Cancello gli allegati che non sono presenti. A partire dal FE, se ho degli allegati da eliminare vuol dire che l'atto non è ancora pubblicato
            //e posso eliminarli tranquillamente.
            for(AllegatoEntry allegatoDaEliminare : daEliminare){
                this.attiGeneraliMapper.deleteAttoGeneraleDocs(allegatoDaEliminare.getKey01(), allegatoDaEliminare.getIdAllegato());
            }

            //Questi allegati sono da "annullare". Si procede con un'eliminazione logica:
            //Vanno popolati i campi utenteCanc, dataCanc, motivoCanc. Questi campi indicano che l'allegato è stato eliminato,
            //ma non cancellato dalla w9allegati.
            for(AllegatoMotivazioneEntry allegatoDaAnnullare : daAnnullare){
                this.attiGeneraliMapper.deleteLogicaAttoGeneraleDoc(allegatoDaAnnullare.getKey01(), allegatoDaAnnullare.getIdAllegato(), syscon, new Date(), allegatoDaAnnullare.getMotivoCanc());
            }

            if(!allegatiEsistenti.isEmpty() && form.getUpdateDocuments() != null && !form.getUpdateDocuments().isEmpty()){

                for(AllegatoEntry nuovoAllegato : form.getUpdateDocuments()){
                    AllegatoEntry esistente = allegatiEsistenti.stream()
                            .filter(allegato -> allegato.getIdAllegato() != null && allegato.getIdAllegato().equals(nuovoAllegato.getIdAllegato()))
                            .findFirst()
                            .orElse(null);

                    if(esistente == null){
                        daInserire.add(nuovoAllegato);
                    }
                    else {
                        //Controllo descrizione modificata
                        if(!StringUtils.equals(esistente.getDescrizione(), nuovoAllegato.getDescrizione())){
                            daAggiornare.add(nuovoAllegato);
                        }
                        //Controllo URL modificato
                        else if(!StringUtils.equals(esistente.getUrl(), nuovoAllegato.getUrl())){
                            daAggiornare.add(nuovoAllegato);
                        }
                        //Controllo file allegato modificato
                        else if(Arrays.equals(esistente.getFileAllegato(), nuovoAllegato.getFileAllegato())){
                            daAggiornare.add(nuovoAllegato);
                        }
                    }
                }

                //Inserisco gli allegati nuovi
                for(AllegatoEntry nuovoAllegato : daInserire){

                    Long chiave = this.wgcManager.getNextId("W9ALLEGATI");

                    AllegatoEntry nuovoAllegatoEntry = new AllegatoEntry();
                    nuovoAllegatoEntry.setIdAllegato(chiave);
                    nuovoAllegatoEntry.setKey01(form.getIdAtto().toString());
                    nuovoAllegatoEntry.setKey02(null);
                    nuovoAllegatoEntry.setKey03(null);
                    nuovoAllegatoEntry.setTabella("W9ATTI_GENERALI");
                    nuovoAllegatoEntry.setDescrizione(nuovoAllegato.getDescrizione());
                    nuovoAllegatoEntry.setUtenteCreatore(syscon);
                    nuovoAllegatoEntry.setTipoFile(nuovoAllegato.getTipoFile());
                    nuovoAllegatoEntry.setNumOrdine(0L);
                    nuovoAllegatoEntry.setUtenteCanc(null);
                    nuovoAllegatoEntry.setDataCanc(null);
                    nuovoAllegatoEntry.setMotivoCanc(null);
                    nuovoAllegatoEntry.setUrl(nuovoAllegato.getUrl());

                    if(StringUtils.equals(form.getPrimaPubb(), Constants.PRIMA_PUBBLICAZIONE_ATTO)){
                        //Decode binary e setto fileAllegato
                        if (StringUtils.isNotBlank(nuovoAllegato.getBinary())) {
                            byte[] decodedFile = Base64.getDecoder().decode(nuovoAllegato.getBinary().getBytes());
                            nuovoAllegatoEntry.setFileAllegato(decodedFile);
                        }
                    }

                    if(attoGenerale.getDataPubbSistema() != null && attoGenerale.getDataPubbSistema().before(new Date())){
                        nuovoAllegatoEntry.setDataAggiunta(new Date());

                        //Procedo a ripubblicare l'atto
                        this.attiGeneraliMapper.pubblicaAttoGenerale(form.getIdAtto(), new Date());
                    }

                    this.attiGeneraliMapper.insertAllegatoAttoGenerale(nuovoAllegatoEntry);
                }

                //A partire dagli allegati esistenti.
                boolean toUpdate = false;
                for(AllegatoEntry allegatoEsistente : allegatiEsistenti){
                    //Aggiorno gli stessi con un UPDATE
                    for(AllegatoEntry allegatoUpdate : daAggiornare){

                        if(Objects.equals(allegatoUpdate.getIdAllegato(), allegatoEsistente.getIdAllegato())){

                            if (allegatoUpdate.getUrl() != null) {
                                allegatoEsistente.setUrl(allegatoUpdate.getUrl());
                                toUpdate = true;
                            }
                            if (allegatoUpdate.getFileAllegato() != null) {
                                allegatoEsistente.setFileAllegato(allegatoUpdate.getFileAllegato());
                                toUpdate = true;
                            }
                            if (allegatoUpdate.getDescrizione() != null) {
                                allegatoEsistente.setDescrizione(allegatoUpdate.getDescrizione());
                                toUpdate = true;
                            }
                        }
                    }
                    if(toUpdate){
                        this.attiGeneraliMapper.updateAllegatoAttoGenerale(allegatoEsistente);
                    }
                    toUpdate = false;
                }
            }

            response.setData(form.getIdAtto().toString());

        } catch (Exception e) {
            logger.error("Errore durante l'esecuzione del metodo AttiGeneraliManager::updateDettaglioAttoGenerale", e);
            response.setEsito(false);
            response.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult insertAttoGenerale(AttoGeneraleInsertForm form, String authorization, String xLoginMode){

        logger.info("insertAttoGenerale::inizio metodo");

        ResponseResult response = new ResponseResult();
        response.setEsito(true);

        try {

            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
            Long syscon = userAuthClaimsDTO.getSyscon();

            // Valida input
            if (form == null) {
                response.setEsito(false);
                response.setErrorData("Form di inserimento nullo");
                return response;
            }

            AttoGeneraleEntry nuovoAtto = new AttoGeneraleEntry();

            Long chiave = this.wgcManager.getNextId("W9ATTI_GENERALI");

            nuovoAtto.setIdAtto(chiave);

            //Selezione di tutte le SA gestita da profilo utente.
            //C'è sempre una SA assegnata ad un atto generale.
            String codein = this.attiGeneraliMapper.getStazioneAppaltanteCodein(form.getStazioneAppaltante());
            nuovoAtto.setStazioneAppaltante(codein);

            nuovoAtto.setRup(form.getRup());
            nuovoAtto.setTipologia(form.getTipologia());
            nuovoAtto.setDescrizione(form.getDescrizione());
            nuovoAtto.setDataAtto(form.getDataAtto() != null ? form.getDataAtto() : null);
            nuovoAtto.setNumeroAtto(form.getNumeroAtto());
            nuovoAtto.setPrimaPubb(form.getPrimaPubb());
            nuovoAtto.setDataPrimaPubb(form.getDataPrimaPubb() != null ? form.getDataPrimaPubb() : null);
            nuovoAtto.setDataPubbSistema(form.getDataPubbSistema() != null ? form.getDataPubbSistema() : null);
            nuovoAtto.setDataScadenza(form.getDataScadenza() != null ? form.getDataScadenza() : null);
            nuovoAtto.setUtenteProp(syscon);

            this.attiGeneraliMapper.insertNuovoAttoGenerale(nuovoAtto);

            //GESTIONE ALLEGATI
            //Se presenti, vengono inseriti nella w9allegati
            if(form.getUpdateDocuments() != null) {

                for(AllegatoEntry allegato : form.getUpdateDocuments()){

                    AllegatoEntry nuovoAllegato = new AllegatoEntry();

                    Long chiaveAllegato = this.wgcManager.getNextId("W9ALLEGATI");

                    nuovoAllegato.setIdAllegato(chiaveAllegato);
                    nuovoAllegato.setKey01(nuovoAtto.getIdAtto().toString());
                    nuovoAllegato.setKey02(null);
                    nuovoAllegato.setKey03(null);
                    nuovoAllegato.setTabella("W9ATTI_GENERALI");
                    nuovoAllegato.setDescrizione(allegato.getDescrizione());
                    nuovoAllegato.setUtenteCreatore(syscon);
                    nuovoAllegato.setTipoFile(allegato.getTipoFile());
                    nuovoAllegato.setNumOrdine(0L);
                    nuovoAllegato.setDataAggiunta(null);
                    nuovoAllegato.setUtenteCanc(null);
                    nuovoAllegato.setDataCanc(null);
                    nuovoAllegato.setMotivoCanc(null);

                    if(StringUtils.equals(nuovoAtto.getPrimaPubb(), Constants.PRIMA_PUBBLICAZIONE_ATTO)){
                        if (StringUtils.isNotBlank(allegato.getBinary())) {
                            byte[] dedcodedFile = Base64.getDecoder().decode(allegato.getBinary().getBytes());
                            nuovoAllegato.setFileAllegato(dedcodedFile);
                        }
                    }
                    //PRIMA_PUBB = 2
                    else {
                        //Controllare validità URL, forse a FE in inserimento.
                        nuovoAllegato.setUrl(allegato.getUrl());
                    }

                    this.attiGeneraliMapper.insertAllegatoAttoGenerale(nuovoAllegato);
                }
            }

            response.setData(nuovoAtto.getIdAtto().toString());
        } catch (Exception e) {
            logger.error("Errore durante l'esecuzione del metodo AttiGeneraliManager::insertAttoGenerale", e);
            response.setEsito(false);
            response.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }

        return response;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public AllegatoEntry downloadDocumentoAttoGenerale(String idAtto, Long idAllegato){

        if (idAtto != null && idAllegato != null) {
            AllegatoEntry doc = this.attiGeneraliMapper.getAllegatoAttoGenerale(idAtto, idAllegato);
            if (doc != null) {
                if (doc.getFileAllegato() != null) {
                    byte[] encodedFile = Base64.getEncoder().encode(doc.getFileAllegato());
                    doc.setBinary(new String(encodedFile, StandardCharsets.UTF_8));
                }
                return doc;
            }
        }

        return null;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult deleteAttoGenerale(String idAtto, String codProfilo, String ipEvento, String authorization, String xLoginMode) {

        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);

        try {
            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
            Long syscon = userAuthClaimsDTO.getSyscon();

            this.attiGeneraliMapper.deleteAttoGenerale(Long.valueOf(idAtto));
            this.attiGeneraliMapper.deleteAttoGeneraleDocsByIdAtto(idAtto);

            //loggo wlogeventi
            LogEventiEntry entry = new LogEventiEntry();
            entry.setIdevento(wgcManager.getNextId("W_LOGEVENTI"));
            entry.setCodapp("W_");
            entry.setSyscon(syscon);
            entry.setCodProfilo(codProfilo);
            entry.setDataora(new Date());
            entry.setCodevento(Constants.DELETE_ATTO_GENERALE);
            entry.setDescr("Eliminazione atto generale con idAtto: [" + idAtto + "]");
            entry.setErrmsg(null);
            entry.setIpevento(ipEvento);
            entry.setLivevento((short) 1);
            entry.setOggevento("Eliminazione atto generale");

            this.attiGeneraliMapper.insertLogEventi(entry);

        } catch (Exception e) {
            logger.error("Errore inaspettato durante cancellazione atto generale: [{}]",idAtto, e);
            throw (e);
        }

        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult annullaPubblicazione(String idAtto, String codProfilo, String ipEvento, String authorization, String xLoginMode) {

        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);

        try {
            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
            Long syscon = userAuthClaimsDTO.getSyscon();

            //Sbianco il campo dataPubbSistema per annullare la programmazione della pubblicazione dell'atto.
            this.attiGeneraliMapper.annullaPubblicazione(Long.valueOf(idAtto));

            //loggo wlogeventi
            LogEventiEntry entry = new LogEventiEntry();
            entry.setIdevento(wgcManager.getNextId("W_LOGEVENTI"));
            entry.setCodapp("W_");
            entry.setSyscon(syscon);
            entry.setCodProfilo(codProfilo);
            entry.setDataora(new Date());
            entry.setCodevento(Constants.DELETE_ATTO_GENERALE);
            entry.setDescr("Annullata pubblicazione atto generale con idAtto: [" + idAtto + "]");
            entry.setErrmsg(null);
            entry.setIpevento(ipEvento);
            entry.setLivevento((short) 1);
            entry.setOggevento("Annullata pubb atto gen.");

            this.attiGeneraliMapper.insertLogEventi(entry);

        } catch (Exception e) {
            logger.error("Errore inaspettato durante l'annullamento della pubblicazione di un atto generale: [{}]",idAtto, e);
            throw (e);
        }

        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult annullaPubblicazioneMotivazione(String idAtto, String motivoCanc, String ipEvento, String codProfilo, String xLoginMode, String authorization) {

        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);

        try {

            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
            Long syscon = userAuthClaimsDTO.getSyscon();

            //Inserisco la motivazione dell'utente per cui vuole annullare la pubblicazione dell'atto e traccio il tutto sulla wLogEventi.
            this.attiGeneraliMapper.annullaPubblicazioneMotivazione(Long.valueOf(idAtto), motivoCanc, new Date(), syscon);

            //loggo wlogeventi
            LogEventiEntry entry = new LogEventiEntry();
            entry.setIdevento(wgcManager.getNextId("W_LOGEVENTI"));
            entry.setCodapp("W_");
            entry.setSyscon(syscon);
            entry.setCodProfilo(codProfilo);
            entry.setDataora(new Date());
            entry.setCodevento(Constants.DELETE_ATTO_GENERALE);
            entry.setDescr("Annullata pubblicazione motivazione atto generale con idAtto: [" + idAtto + "], con la motivazione: [" + motivoCanc + "]");
            entry.setErrmsg(null);
            entry.setIpevento(ipEvento);
            entry.setLivevento((short) 1);
            entry.setOggevento("Annullata pubb motivazione AG");

            this.attiGeneraliMapper.insertLogEventi(entry);

        } catch (Exception e) {
            logger.error("Errore inaspettato durante l'annullamento della pubblicazione di un atto generale con motivazione: [{}]",idAtto, e);
            throw (e);
        }

        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult pubblicaAttoGenerale(Long idAtto) {

        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);

        try {

            RicercaAttiGeneraliForm form = new RicercaAttiGeneraliForm();
            form.setIdAtto(idAtto);
            AttoGeneraleEntry attoGenerale = this.attiGeneraliMapper.getDettaglioAttoGeneraleMap(form);

            //Se è una prima pubblicazione, dataprimapubb assume lo stesso valore di dataProgrammazione.
            if(attoGenerale != null && StringUtils.equals(attoGenerale.getPrimaPubb(), Constants.PRIMA_PUBBLICAZIONE_ATTO)) {
                this.attiGeneraliMapper.pubblicaAttoGeneralePrimaPubblicazione(idAtto, new Date());
            }
            else {
                //Aggiorno dataPubbSistema con la data corrente.
                this.attiGeneraliMapper.pubblicaAttoGenerale(idAtto, new Date());
            }

        } catch (Exception e) {
            logger.error("Errore inaspettato durante la pubblicazione di un atto generale: [{}]",idAtto, e);
            throw (e);
        }

        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult pubblicaAttoGeneraleFuturo(Long idAtto, Date dataProgrammazione) {

        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);

        try {

            RicercaAttiGeneraliForm form = new RicercaAttiGeneraliForm();
            form.setIdAtto(idAtto);
            AttoGeneraleEntry attoGenerale = this.attiGeneraliMapper.getDettaglioAttoGeneraleMap(form);

            if(attoGenerale != null){

                //Se è una prima pubblicazione, dataprimapubb assume lo stesso valore di dataProgrammazione.
                if(StringUtils.equals(attoGenerale.getPrimaPubb(), Constants.PRIMA_PUBBLICAZIONE_ATTO)) {
                    this.attiGeneraliMapper.pubblicaAttoGeneralePrimaPubblicazione(idAtto, dataProgrammazione);
                }
                else {
                    //Aggiorno dataPubbSistema con data futura.
                    this.attiGeneraliMapper.pubblicaAttoGeneraleFuturo(idAtto, dataProgrammazione);
                }
            }

        } catch (Exception e) {
            logger.error("Errore inaspettato durante l'annullamento della pubblicazione di un atto generale con motivazione: [{}]",idAtto, e);
            throw (e);
        }

        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult generaUrlAnac() {

        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);

        try {

           String url = this.attiGeneraliMapper.getUrlAnac(Constants.CONFIG_PORTALE_TRASPARENZA, Constants.CODAPP);

           UUID uuid = UUID.randomUUID();
           String randomUUIDString = uuid.toString();

           String urlAnac = url + Constants.URL_PROCEDURA_UUID + randomUUIDString;

           risultato.setData(urlAnac);

        } catch (Exception e) {
            logger.error("Errore inaspettato durante la generazione dell'url per ANAC: ", e);
            throw (e);
        }

        return risultato;
    }

}
