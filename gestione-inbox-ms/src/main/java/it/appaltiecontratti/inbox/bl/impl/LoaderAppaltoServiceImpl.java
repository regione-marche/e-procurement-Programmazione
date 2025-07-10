package it.appaltiecontratti.inbox.bl.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.appaltiecontratti.inbox.bl.LoaderAppaltoService;
import it.appaltiecontratti.inbox.common.Constants;
import it.appaltiecontratti.inbox.common.CostantiW9;
import it.appaltiecontratti.inbox.common.ETipologiaIncaricati;
import it.appaltiecontratti.inbox.entity.contratti.LottoBaseEntry;
import it.appaltiecontratti.inbox.entity.form.AggiornamentoIdSchedaForm;
import it.appaltiecontratti.inbox.entity.form.W9XmlAnomForm;
import it.appaltiecontratti.inbox.entity.form.W9XmlAppaltoResponseForm;
import it.appaltiecontratti.inbox.entity.form.W9XmlForm;
import it.appaltiecontratti.inbox.entity.loaderappalto.*;
import it.appaltiecontratti.inbox.entity.responses.FullResponseLoaderAppalto;
import it.appaltiecontratti.inbox.mapper.LoaderAppaltoMapper;
import it.appaltiecontratti.inbox.utility.Utility;
import it.avlp.simog.massload.xmlbeans.*;
import it.avlp.simog.massload.xmlbeans.ResponseLoaderAppalto.FeedBack.AnomalieSchede;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("loaderAppaltoService")
public class LoaderAppaltoServiceImpl implements LoaderAppaltoService {

    private static final Logger LOG = LogManager.getLogger(LoaderAppaltoServiceImpl.class);

    private static final String SIMOGWS_WSSMANAGER_NULL_15 = "SIMOGWS_WSSMANAGER_NULL_15";
    private static final String INTERNAL_VALIDATION_001 = "INTERNAL_VALIDATION_001";

    @Autowired
    private LoaderAppaltoMapper loaderAppaltoMapper;

    @Autowired
    private SimogGatewayRestClientManager simogGatewayRestClientManager;

    @Override
    public FullResponseLoaderAppalto cancellaScheda(final Long codGara, final Long codLotto, final Long faseEsecuzione,
                                                    final Long numProgressivoFase, final Long idFlusso) throws Exception {
        LOG.debug("Execution start::cancellaScheda codGara {}, codLotto {}, faseEsecuzione {}, numProgressivoFase {}",
                codGara, codLotto, faseEsecuzione, numProgressivoFase);

        boolean eliminaScheda = true;

        // Numero delle fasi da inviare. Se invioSingolaFase vale true allora si e'
        // in configurazione Vigilanza perche' si sta inviando la singola fase e
        // quindi il numero delle fasi da inviare e' pari a 1.
        // Se invece invioSingolaFase vale false si e' in configurazione Osservatorio e
        // bisogna determinare quante fasi ed esistono fasi con il campo DAEXPORT=1
        // allora il numero delle fasi da inviare e' pari a al numero di record
        // contenuti in datiW9ListaFasi.
        // I numeroFasiDaInviare viene utilizzato anche per ricavare l'indice di
        // collaborazione.
        // infatti se devo inviare una singola fase da vigilanza devo ricavare l'indice
        // altrimenti in
        // configurazione osservatorio deve essere valorizzato a "-1"

        LoaderAppalto la = new LoaderAppalto();

        LoaderAppalto.TrasferimentoDati td = getTrasferimentoDati(codGara, codLotto, faseEsecuzione, numProgressivoFase,
                eliminaScheda);
        la.setTrasferimentoDati(td);

        FullResponseLoaderAppalto risultato = simogGatewayRestClientManager.postLoaderAppalto(la);

        if (StringUtils.isNotBlank(risultato.getErrorData())
                && FullResponseLoaderAppalto.ERROR_XML_VALIDATION.equals(risultato.getErrorData())
                && risultato.getValidationErrors() != null && risultato.getValidationErrors().size() > 0) {
            // Salvo gli errori di validazione marshalling XML
            risultato.getValidationErrors().stream().forEach(e -> LOG.error("Errore di validazione {}", e));
            // inserisco record in W9XML
            Long numXml = insertW9XML(codGara, codLotto, idFlusso, risultato.getXml(), faseEsecuzione,
                    numProgressivoFase);

            if (numXml > 0) {
                writeMarshallingErrors(codGara, codLotto, faseEsecuzione, numProgressivoFase, numXml,
                        risultato.getValidationErrors());
                aggiornaStatoFase(codGara, codLotto, faseEsecuzione);
            }

        } else if (risultato.getData() != null) {
            String encodedLoaderAppaltoResponse = risultato.getData();
            if (StringUtils.isNotBlank(encodedLoaderAppaltoResponse)) {
                ObjectMapper objectMapper = new ObjectMapper();

                LoaderAppaltoResponse loaderAppaltoResponse = objectMapper.readValue(encodedLoaderAppaltoResponse,
                        LoaderAppaltoResponse.class);
                // inserisco record in W9XML
                Long numXml = insertW9XML(codGara, codLotto, idFlusso, risultato.getXml(), faseEsecuzione,
                        numProgressivoFase);

                if (numXml > 0) {
                    // aggiorno il feedback
                    String CUI = readResponseLoaderAppalto(codGara, codLotto, faseEsecuzione, numProgressivoFase,
                            numXml, eliminaScheda, loaderAppaltoResponse);
                    if (CUI != null && !SIMOGWS_WSSMANAGER_NULL_15.equals(CUI)) {
                        boolean isCancellazioneAggiudazione = (CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA == faseEsecuzione
                                .intValue() || CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE == faseEsecuzione.intValue()
                                || CostantiW9.ADESIONE_ACCORDO_QUADRO == faseEsecuzione.intValue());
                        aggiornaCODCUI(codGara, codLotto, faseEsecuzione, numProgressivoFase, CUI,
                                isCancellazioneAggiudazione);
                    }

                    // aggiorno lo stato del lotto, solo il metodo readResponseLoaderAppalto
                    // diversa da 'SIMOGWS_WSSMANAGER_NULL_15'
                    if (!SIMOGWS_WSSMANAGER_NULL_15.equals(CUI)) {
                        aggiornaStatoFase(codGara, codLotto, faseEsecuzione);
                    }
                }
            }
        }
        LOG.debug("Execution end::cancellaScheda");
        return risultato;
    }

    @Override
    public boolean pubblicaScheda(final Long codGaraI, final Long codLottoI, final Long faseEsecuzioneI,
                                  final Long numProgressivoFaseI, final Long idFlusso) {
        LOG.debug(
                "Execution start::pubblicaScheda codGaraI {}, codLottoI {}, faseEsecuzioneI {}, numProgressivoFaseI {}, idFlusso {}",
                codGaraI, codLottoI, faseEsecuzioneI, numProgressivoFaseI, idFlusso);

        boolean eliminaScheda = false;

        // Se codGara, codLotto, faseEsecuzione sono valorizzati allora si e' in
        // configurazione Vigilanza perche' si sta inviando la singola fase

        // Numero delle fasi da inviare. Se invioSingolaFase vale true allora si e'
        // in configurazione Vigilanza perche' si sta inviando la singola fase e
        // quindi il numero delle fasi da inviare e' pari a 1.
        // Se invece invioSingolaFase vale false si e' in configurazione Osservatorio e
        // bisogna determinare quante fasi ed esistono fasi con il campo DAEXPORT=1
        // allora il numero delle fasi da inviare e' pari a al numero di record
        // contenuti in datiW9ListaFasi.
        // I numeroFasiDaInviare viene utilizzato anche per ricavare l'indice di
        // collaborazione.
        // infatti se devo inviare una singola fase da vigilanza devo ricavare l'indice
        // altrimenti in
        // configurazione osservatorio deve essere valorizzato a "-1"

        LoaderAppalto la = new LoaderAppalto();
        Long codGara = codGaraI;
        Long codLotto = codLottoI;
        Long faseEsecuzione = faseEsecuzioneI;
        Long numProgressivoFase = numProgressivoFaseI;

        try {
            LoaderAppalto.TrasferimentoDati td = getTrasferimentoDati(codGara, codLotto, faseEsecuzione,
                    numProgressivoFase, eliminaScheda);
            la.setTrasferimentoDati(td);

            FullResponseLoaderAppalto risultato = simogGatewayRestClientManager.postLoaderAppalto(la);

            if (StringUtils.isNotBlank(risultato.getErrorData())
                    && FullResponseLoaderAppalto.ERROR_XML_VALIDATION.equals(risultato.getErrorData())
                    && risultato.getValidationErrors() != null && risultato.getValidationErrors().size() > 0) {
                // Salvo gli errori di validazione marshalling XML
                risultato.getValidationErrors().stream().forEach(e -> LOG.error("Errore di validazione {}", e));
                // inserisco record in W9XML
                Long numXml = insertW9XML(codGara, codLotto, idFlusso, risultato.getXml(), faseEsecuzione,
                        numProgressivoFase);

                if (numXml > 0) {
                    writeMarshallingErrors(codGara, codLotto, faseEsecuzione, numProgressivoFase, numXml,
                            risultato.getValidationErrors());
                    aggiornaStatoFase(codGara, codLotto, faseEsecuzione);
                }

            } else if (risultato.getData() != null) {
                String encodedLoaderAppaltoResponse = risultato.getData();
                if (StringUtils.isNotBlank(encodedLoaderAppaltoResponse)) {
                    ObjectMapper objectMapper = new ObjectMapper();

                    try {
                        LoaderAppaltoResponse loaderAppaltoResponse = objectMapper
                                .readValue(encodedLoaderAppaltoResponse, LoaderAppaltoResponse.class);
                        // inserisco record in W9XML
                        Long numXml = insertW9XML(codGara, codLotto, idFlusso, risultato.getXml(), faseEsecuzione,
                                numProgressivoFase);

                        if (numXml > 0) {
                            // aggiorno il feedback
                            String CUI = readResponseLoaderAppalto(codGara, codLotto, faseEsecuzione,
                                    numProgressivoFase, numXml, eliminaScheda, loaderAppaltoResponse);
                            if (CUI != null && !SIMOGWS_WSSMANAGER_NULL_15.equals(CUI)) {
                                boolean isCancellazioneAggiudazione = eliminaScheda
                                        && (CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA == faseEsecuzione.intValue()
                                        || CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE == faseEsecuzione
                                        .intValue()
                                        || CostantiW9.ADESIONE_ACCORDO_QUADRO == faseEsecuzione.intValue());
                                aggiornaCODCUI(codGara, codLotto, faseEsecuzione, numProgressivoFase, CUI,
                                        isCancellazioneAggiudazione);
                            }

                            // aggiorno lo stato del lotto, solo il metodo readResponseLoaderAppalto
                            // diversa da 'SIMOGWS_WSSMANAGER_NULL_15'
                            if (!SIMOGWS_WSSMANAGER_NULL_15.equals(CUI)) {
                                aggiornaStatoFase(codGara, codLotto, faseEsecuzione);
                            }
                        }

                        return true;
                    } catch (IOException e) {
                        LOG.error("Errore durante la lettura della risposta", e);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error(
                    "Errore durante l'invio della scheda a SIMOG, codGara [{}], codLotto [{}], faseEsecuzione [{}], numeroProgressivoFase [{}]",
                    codGara, codLotto, faseEsecuzione, numProgressivoFase, e);
        }

        LOG.debug("Execution end::pubblicaScheda");
        return false;
    }

    public boolean cancellaDatiComuni(final Long codGara, final Long codLotto) throws Exception {
        LOG.debug("Execution start::cancellaDatiComuni codGara {}, codLotto {}",
                codGara, codLotto);

        boolean hasErrors = false;

        ObjectFactory obf = new ObjectFactory();
        // generazione oggetto LoaderAppalto
        LoaderAppalto la = new LoaderAppalto();

        // generazione oggetto TrasferimentoDati
        LoaderAppalto.TrasferimentoDati trasferimentoDati = new LoaderAppalto.TrasferimentoDati();

        // generazione oggetto TrasferimentoDatiType
        TrasferimentoType trasferimento = new TrasferimentoType();
        trasferimento.setDATACREAZIONEFLUSSO(Utility.convertTodayToCalendar());
        trasferimento.setNUMSCHEDE(1);
        // aggiungo l'oggetto TrasferimentoDatiType al TrasferimentoDati
        trasferimentoDati.setInfoTrasferimento(trasferimento);

        // generazione oggetto RecIdSchedaElimType
        RecIdSchedaElimType schedeEliminate = new RecIdSchedaElimType();

        String cigEntry = this.loaderAppaltoMapper.getCig(codGara, codLotto);

        if (cigEntry != null) {
            schedeEliminate.setCIG(cigEntry);
        }

        // Imposto il codice CUI a stringa vuota per passare i controlli di obbligatorieta' e allo stesso tempo
        // non specificare alcun valore valido
        schedeEliminate.setCUI("");

        // imposto la scheda DATI_COMUNI come da cancellare
        schedeEliminate.setSCHEDA(TipiSchedeType.fromValue(Constants.TIPO_SCHEDA_DATI_COMUNI));

        // recupero l'id_scheda_simog e l'id_scheda_locale e se presenti li imposto in RecIdSchedaElimType
        DatiIdSchedaLocaleSimogEntry idSchedaLocaleSimogEntry = this.loaderAppaltoMapper
                .getIdSchedaLocaleSimogLotto(codGara, codLotto);

        if (idSchedaLocaleSimogEntry != null) {
            if (StringUtils.isNotBlank(idSchedaLocaleSimogEntry.getIdSchedaLocale())) {
                schedeEliminate.setIDSCHEDALOCALE(idSchedaLocaleSimogEntry.getIdSchedaLocale());
            }
            if (StringUtils.isNotBlank(idSchedaLocaleSimogEntry.getIdSchedaSimog())) {
                schedeEliminate.setIDSCHEDASIMOG(idSchedaLocaleSimogEntry.getIdSchedaSimog());
            }
        }

        // aggiungo l'oggetto RecIdSchedaElimType all'array di TrasferimentoDati
        trasferimentoDati.getSchedeEliminate().add(schedeEliminate);

        // aggiungo l'oggetto TrasferimentoDati al LoaderAppalto
        la.setTrasferimentoDati(trasferimentoDati);

        // invio alla LoaderAppalto
        FullResponseLoaderAppalto risultato = simogGatewayRestClientManager.postLoaderAppalto(la);

        if (StringUtils.isNotBlank(risultato.getErrorData())
                && FullResponseLoaderAppalto.ERROR_XML_VALIDATION.equals(risultato.getErrorData())
                && risultato.getValidationErrors() != null && risultato.getValidationErrors().size() > 0) {
            // Salvo gli errori di validazione marshalling XML
            risultato.getValidationErrors().stream().forEach(e -> LOG.error("Errore di validazione {}", e));

            hasErrors = true;

        } else if (risultato.getData() != null) {
            if (StringUtils.isNotBlank(risultato.getXml())) {
                LOG.info("cancellaDatiComuni XML {}", risultato.getXml());
            }
            String encodedLoaderAppaltoResponse = risultato.getData();
            if (StringUtils.isNotBlank(encodedLoaderAppaltoResponse)) {
                ObjectMapper objectMapper = new ObjectMapper();

                LoaderAppaltoResponse loaderAppaltoResponse = objectMapper.readValue(encodedLoaderAppaltoResponse,
                        LoaderAppaltoResponse.class);
                hasErrors = logAndReturnLogicalErrors(loaderAppaltoResponse);
            }
        }
        LOG.debug("Execution end::cancellaDatiComuni");
        return hasErrors;
    }

    public LoaderAppalto.TrasferimentoDati getTrasferimentoDati(final Long codGara, final Long codLotto,
                                                                 final Long faseEsecuzione, final Long numProgressivoFase, final boolean eliminaScheda) {
        LOG.debug("Execution start::getTrasferimentoDati");
        ObjectFactory obf = new ObjectFactory();
        LoaderAppalto.TrasferimentoDati trasferimentoDati = new LoaderAppalto.TrasferimentoDati();

        // Creazione dell'elemento principale trasferimento dati
        // Data di creazione del flusso e numero lotti (sempre 1)
        TrasferimentoType trasferimento = new TrasferimentoType();
        trasferimento.setDATACREAZIONEFLUSSO(Utility.convertTodayToCalendar());
        trasferimento.setNUMSCHEDE(1);
        trasferimentoDati.setInfoTrasferimento(trasferimento);

        if (eliminaScheda) {
            RecIdSchedaElimType schedeEliminate = new RecIdSchedaElimType();

            String cigEntry = this.loaderAppaltoMapper.getCig(codGara, codLotto);
            String cuiEntry = this.getCUI(codGara, codLotto, faseEsecuzione, numProgressivoFase);

            if (cigEntry != null) {
                schedeEliminate.setCIG(cigEntry);
            }

            if (cuiEntry != null) {
                schedeEliminate.setCUI(cuiEntry);
            }

            schedeEliminate.setSCHEDA(TipiSchedeType.fromValue(Constants.TipiSchede.get(faseEsecuzione)));

            DatiIdSchedaLocaleSimogEntry idSchedaLocaleSimogEntry = this.loaderAppaltoMapper
                    .getIdSchedaLocaleSimog(codGara, codLotto, faseEsecuzione, numProgressivoFase);

            if (idSchedaLocaleSimogEntry != null) {
                if (StringUtils.isNotBlank(idSchedaLocaleSimogEntry.getIdSchedaLocale())) {
                    schedeEliminate.setIDSCHEDALOCALE(idSchedaLocaleSimogEntry.getIdSchedaLocale());
                }
                if (StringUtils.isNotBlank(idSchedaLocaleSimogEntry.getIdSchedaSimog())) {
                    schedeEliminate.setIDSCHEDASIMOG(idSchedaLocaleSimogEntry.getIdSchedaSimog());
                }
            }

            trasferimentoDati.getSchedeEliminate().add(schedeEliminate);
        } else {

            DatiAggiudicazioneType datiAggiudicazione = obf.createDatiAggiudicazioneType();
            setSchede(obf, datiAggiudicazione, codGara, codLotto, faseEsecuzione, numProgressivoFase);
            trasferimentoDati.getSchede().add(datiAggiudicazione);

            // Se il lotto e' stato aggiudicato, allora
            if ("1".equals(datiAggiudicazione.getDatiComuni().getESITOPROCEDURA())
                    || "5".equals(datiAggiudicazione.getDatiComuni().getESITOPROCEDURA())) {
                // Creazione dell'elemento Responsabili (e' l'archivio di tutte le
                // anagrafiche dei responsabili/tecnici)
                ResponsabiliType responsabili = obf.createResponsabiliType();
                setAnagraficaResponsabili(obf, responsabili, codGara, codLotto);
                trasferimentoDati.setResponsabili(responsabili);

                // Creazione dell'elemento Aggiudicatari
                if (faseEsecuzione.equals(Long.valueOf(CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA))
                        || faseEsecuzione.equals(Long.valueOf(CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE))
                        || faseEsecuzione.equals(Long.valueOf(CostantiW9.ADESIONE_ACCORDO_QUADRO))) {
                    AggiudicatariType aggiudicatari = obf.createAggiudicatariType();
                    setAnagraficaAggiudicatari(obf, aggiudicatari, codGara, codLotto, numProgressivoFase);
                    trasferimentoDati.setAggiudicatari(aggiudicatari);
                }
            }
        }

        LOG.debug("Execution end::getTrasferimentoDati");
        return trasferimentoDati;
    }

    private void setSchede(final ObjectFactory obf, final DatiAggiudicazioneType datiAggiudicazione, final Long codGara,
                           final Long codLotto, final Long faseEsecuzione, final Long numProgressivoFase) {
        LOG.debug("Execution start::setSchede");
        DatiComuniType datiComuni = obf.createDatiComuniType();

        // Dati comuni a tutte le fasi
        setDatiComuni(datiComuni, codGara, codLotto);
        datiAggiudicazione.setDatiComuni(datiComuni);

        // Dati di pubblicazione del bando di gara
        PubblicazioneType pubblicazione = obf.createPubblicazioneType();
        if (setPubblicazione(pubblicazione, codGara, codLotto)) {
            datiAggiudicazione.setPubblicazione(pubblicazione);
        }

        // Se il lotto e' stato aggiudicato, allora si aggiunge la scheda completa
        if (faseEsecuzione != 984L) {
            // Scheda Completa (contiene tutte le possibili fasi/eventi associati al
            // contratto)
            SchedaCompletaType schedaCompleta = obf.createSchedaCompletaType();
            setSchedaCompleta(obf, schedaCompleta, codGara, codLotto, faseEsecuzione, numProgressivoFase);
            datiAggiudicazione.getSchedaCompleta().add(schedaCompleta);
        }

        LOG.debug("Execution end::setSchede");
    }

    private void setDatiComuni(final DatiComuniType datiComuni, final Long codGara, final Long codLotto) {
        LOG.debug("Execution start::setDatiComuni");

        try {
            Long maxNumAppa = this.loaderAppaltoMapper.getMaxNumAppa(codGara, codLotto);
            if (maxNumAppa == null) {
                maxNumAppa = 1L;
            }

            DatiComuniEntry datiComuniEntry = loaderAppaltoMapper.getW9DatiComuni(codGara, codLotto, maxNumAppa);

            if (datiComuniEntry != null) {
                // Valorizzazione forzata.
                // Si tratta del codice fiscale e della denominazione amministrazione
                // per la quale l’amministrazione ha indetto la gara.
                // Nel file XSD sono campi obbligatori, ma non sempre possono essere
                // valorizzati.
                datiComuni.setCFAMMAGENTE("");
                datiComuni.setDENAMMAGENTE("");
                Long verSimog = this.loaderAppaltoMapper.getVersioneSimogGara(codGara);
                if (datiComuniEntry.getCig() != null) {
                    datiComuni.setCIG(datiComuniEntry.getCig());
                }

                if (datiComuniEntry.getFlagEnteSpeciale() != null) {
                    datiComuni.setFLAGENTESPECIALE(
                            FlagSOType.fromValue(datiComuniEntry.getFlagEnteSpeciale()));
                }
                
                if (datiComuniEntry.getTipoContratto() != null) {
                    datiComuni.setTIPOCONTRATTO(TipoSchedaType.fromValue(datiComuniEntry.getTipoContratto()));
                }

                if (datiComuniEntry.getCodCentro() != null) {
                    datiComuni.setCODICECC(datiComuniEntry.getCodCentro());
                }

                if (datiComuniEntry.getDenomCentro() != null) {
                    datiComuni.setDENOMCC(datiComuniEntry.getDenomCentro());
                }

                if (datiComuniEntry.getIdTipologiaSa() != null && verSimog != null && verSimog < 3L) {
                    datiComuni.setIDTIPOLOGIASA(datiComuniEntry.getIdTipologiaSa().toString());
                }

                String flagSN = datiComuniEntry.getFlagSaAgente();

                if (verSimog < 5L) {
                    if (flagSN != null && !"".equals(flagSN)) {
                        if ("1".equals(flagSN)) {
                            datiComuni.setFLAGSAAGENTE(FlagSNType.S);
                        } else {
                            datiComuni.setFLAGSAAGENTE(FlagSNType.N);
                        }
                    } else {
                        datiComuni.setFLAGSAAGENTE(FlagSNType.N);
                    }

                    if (datiComuniEntry.getCfSaAgente() != null && verSimog != null && verSimog < 3L) {
                        datiComuni.setCFAMMAGENTE(datiComuniEntry.getCfSaAgente());
                    }

                    if (datiComuniEntry.getDenomSaAgente() != null && verSimog != null && verSimog < 3L) {
                        datiComuni.setDENAMMAGENTE(datiComuniEntry.getDenomSaAgente());
                    }

                } else {
                    datiComuni.setFLAGSAAGENTE(FlagSNType.N);
                }

                // Codice fiscale del RUP ricavato dall'anagrafica dei tecnici
                DatiTecnicoEntry datiTecnico = loaderAppaltoMapper.getCfPivaTecnico(codGara, codLotto);
                if (datiTecnico != null) {
                    String codiceFiscaleTecnico = datiTecnico.getCodiceFiscaleTecnico();
                    String partitaIvaTecnico = datiTecnico.getPartitaIvaTecnico();

                    if (codiceFiscaleTecnico != null) {
                        datiComuni.setCFRUP(codiceFiscaleTecnico);
                    } else {
                        datiComuni.setCFRUP(partitaIvaTecnico);
                    }
                }

                if (datiComuniEntry.getTipoIn() != null) {
                    datiComuni.setIDCATEGSA(datiComuniEntry.getTipoIn());
                }

                if (datiComuniEntry.getCfEin() != null) {
                    datiComuni.setCFAMM(datiComuniEntry.getCfEin());
                    datiComuni.setCFSA(datiComuniEntry.getCfEin());
                }

                if (datiComuniEntry.getNomEin() != null) {
                    datiComuni.setDENAMM(datiComuniEntry.getNomEin());
                    datiComuni.setDENSA(datiComuniEntry.getNomEin());
                }

                // Se il campo W9ESITO.ESITO_PROCEDURA e' valorizzato si valorizzato il campo
                // ESITO_PROCEDURA
                // dell'oggetto DatiComuni con il valore di tale campo, altrimenti si valorizza
                // a 1 il campo
                // ESITO_PROCEDURA dell'oggetto DatiComuni
                if (datiComuniEntry.getEsitoProceduraEsito() != null) {
                    datiComuni.setESITOPROCEDURA(datiComuniEntry.getEsitoProceduraEsito().toString());
                } else {
                    datiComuni.setESITOPROCEDURA("1");
                }

                if (StringUtils.isNotBlank(datiComuniEntry.getIdSchedaLocale())) {
                    datiComuni.setIDSCHEDALOCALE(datiComuniEntry.getIdSchedaLocale());
                }

                if (StringUtils.isNotBlank(datiComuniEntry.getIdSchedaSimog())) {
                    datiComuni.setIDSCHEDASIMOG(datiComuniEntry.getIdSchedaSimog());
                }

                if (datiComuniEntry.getTipologiaProcedura() != null && verSimog != null && verSimog < 3L) {
                    datiComuni.setTIPOLOGIAPROCEDURA(datiComuniEntry.getTipologiaProcedura().toString());
                }

                flagSN = datiComuniEntry.getFlagCentraleStipula();
                if (flagSN != null && !"".equals(flagSN) && verSimog != null && verSimog < 3L) {
                    if ("1".equals(flagSN)) {
                        datiComuni.setFLAGCENTRALESTIPULA(FlagSNType.S);
                    } else {
                        datiComuni.setFLAGCENTRALESTIPULA(FlagSNType.N);
                    }
                }

                if (verSimog != null && verSimog < 3L) {
                    if (datiComuniEntry.getDurataAccordoQuadro() != null) {
                        datiComuni.setDURATAACCQUADROCONVENZIONE(datiComuniEntry.getDurataAccordoQuadro().intValue());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati comuni del lotto (contratto)", e);
        }

        LOG.debug("Execution end::setDatiComuni");
    }

    private boolean setPubblicazione(final PubblicazioneType pubblicazione, final Long codGara, final Long codLotto) {

        LOG.debug("Execution start::setPubblicazione");

        boolean result = false;

        try {

            DatiPubblicazioneEntry datiPubblicazioneEntry = loaderAppaltoMapper.getW9DatiPubblicazione(codGara,
                    codLotto);

            if (datiPubblicazioneEntry != null) {

                if (datiPubblicazioneEntry.getDataGuce() != null) {
                    pubblicazione.setDATAGUCE(Utility.convertDateToCalendar(datiPubblicazioneEntry.getDataGuce()));
                    result = true;
                }

                if (datiPubblicazioneEntry.getDataGuri() != null) {
                    pubblicazione.setDATAGURI(Utility.convertDateToCalendar(datiPubblicazioneEntry.getDataGuri()));
                    result = true;
                }

                if (datiPubblicazioneEntry.getDataAlbo() != null) {
                    pubblicazione.setDATAALBO(Utility.convertDateToCalendar(datiPubblicazioneEntry.getDataAlbo()));
                    result = true;
                }

                if (datiPubblicazioneEntry.getQuotidianiNaz() != null) {
                    pubblicazione
                            .setQUOTIDIANINAZ(Integer.parseInt(datiPubblicazioneEntry.getQuotidianiNaz().toString()));
                    result = true;
                }

                if (datiPubblicazioneEntry.getQuotidianiReg() != null) {
                    pubblicazione
                            .setQUOTIDIANIREG(Integer.parseInt(datiPubblicazioneEntry.getQuotidianiReg().toString()));
                    result = true;
                }

                String flagSN = datiPubblicazioneEntry.getProfiloCommittente();
                if (flagSN != null && !"".equals(flagSN)) {
                    if ("1".equals(flagSN)) {
                        pubblicazione.setPROFILOCOMMITTENTE(FlagSNType.S);
                    } else {
                        pubblicazione.setPROFILOCOMMITTENTE(FlagSNType.N);
                    }
                    result = true;
                }

                flagSN = datiPubblicazioneEntry.getSitoMinisteroInfTrasp();
                if (flagSN != null && !"".equals(flagSN)) {
                    if ("1".equals(flagSN)) {
                        pubblicazione.setSITOMINISTEROINFTRASP(FlagSNType.S);
                    } else {
                        pubblicazione.setSITOMINISTEROINFTRASP(FlagSNType.N);
                    }
                    result = true;
                }

                flagSN = datiPubblicazioneEntry.getSitoOsservatorioCp();
                if (flagSN != null && !"".equals(flagSN)) {
                    if ("1".equals(flagSN)) {
                        pubblicazione.setSITOOSSERVATORIOCP(FlagSNType.S);
                    } else {
                        pubblicazione.setSITOOSSERVATORIOCP(FlagSNType.N);
                    }
                    result = true;
                }

                if (datiPubblicazioneEntry.getDataBore() != null) {
                    pubblicazione.setDATABORE(Utility.convertDateToCalendar(datiPubblicazioneEntry.getDataBore()));
                    result = true;
                }

                if (datiPubblicazioneEntry.getPeriodici() != null) {
                    pubblicazione.setPERIODICI(Integer.parseInt(datiPubblicazioneEntry.getPeriodici().toString()));
                    result = true;
                }

                if (StringUtils.isNotBlank(datiPubblicazioneEntry.getIdSchedaLocale())) {
                    pubblicazione.setIDSCHEDALOCALE(datiPubblicazioneEntry.getIdSchedaLocale());
                    result = true;
                }

                if (StringUtils.isNotBlank(datiPubblicazioneEntry.getIdSchedaSimog())) {
                    pubblicazione.setIDSCHEDASIMOG(datiPubblicazioneEntry.getIdSchedaSimog());
                    result = true;
                }
            }

        } catch (Exception e) {
            result = false;
            throw new RuntimeException("Errore nella lettura dei dati pubblicazione", e);
        }

        LOG.debug("Execution end::setPubblicazione");

        return result;
    }

    private void setSchedaCompleta(final ObjectFactory obf, final SchedaCompletaType schedaCompleta, final Long codGara,
                                   final Long codLotto, final Long faseEsecuzione, final Long numProgressivoFase) {

        LOG.debug("Execution start::setSchedaCompleta");

        // CUI - Codice di aggiudicazione
        setCUI(schedaCompleta, codGara, codLotto, faseEsecuzione, numProgressivoFase);

        boolean proseguiNonE1 = true;
        boolean proseguiNonS2 = true;

        try {
            // contratti esclusi (inviano a simog solo la scheda adesione, altrimenti l'agg.
            // semplificata)

            DatiE1S2Entry entry = loaderAppaltoMapper.getE1S2(codGara, codLotto);

            if (isE1(entry)) {
                proseguiNonE1 = false;
            }
            // sottosoglia
            if (!isS2(entry)) {
                proseguiNonS2 = false;
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nel controllo appartenenza di un lotto ad accordo quadro escluso", e);
        }

        if (faseEsecuzione != null) {
            switch (faseEsecuzione.intValue()) {
                case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
                    // Dati della fase di aggiudicazione per contratti sopra soglia
                    if (proseguiNonE1) {
                        setAggiudicazione(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
                    }
                    break;
                case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
                    // Dati della fase di aggiudicazione semplificata per contratti sotto soglia
                    setSottosoglia(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
                    // Dati della fase di aggiudicazione semplificata per contratti esclusi
                    setEscluso(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
                    break;
                case CostantiW9.ADESIONE_ACCORDO_QUADRO:
                    // Adesione ad accordo quadro
                    setAdesione(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
                    break;
                case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
                    // Dati di inizio per contratti sopra soglia
                    if (proseguiNonE1) {
                        setDatiInizio(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
                    }
                    break;
                case CostantiW9.STIPULA_ACCORDO_QUADRO:
                    // Dati di stipula per l'accordo quadro
                    if (proseguiNonE1 && proseguiNonS2) {
                        setDatiStipula(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
                    }
                    break;
                case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
                    // Dati relativi agli stati di avanzamento (SAL) per contratti sopra soglia
                    if (proseguiNonE1) {
                        setDatiAvanzamenti(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
                    }
                    break;
                case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
                    // Dati di conclusione per contratti sopra soglia
                    if (proseguiNonE1) {
                        setDatiConclusione(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
                    }
                    break;
                case CostantiW9.COLLAUDO_CONTRATTO:
                    // Dati di collaudo per contratti sopra soglia
                    if (proseguiNonE1) {
                        setDatiCollaudo(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
                    }
                    break;
                case CostantiW9.ISTANZA_RECESSO:
                    // Dati di recesso per contratti sopra soglia
                    if (proseguiNonE1) {
                        setDatiRitardo(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
                    }
                    break;
                case CostantiW9.ACCORDO_BONARIO:
                    // Dati relativi agli accordi bonaria per contratti sopra soglia
                    if (proseguiNonE1) {
                        setDatiAccordi(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
                    }
                    break;
                case CostantiW9.SOSPENSIONE_CONTRATTO:
                    // Dati relativi alle sospensioni per contratti sopra soglia
                    if (proseguiNonE1) {
                        setDatiSospensioni(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
                    }
                    break;
                case CostantiW9.VARIANTE_CONTRATTO:
                    // Dati relativi alle varianti per contratti sopra soglia
                    if (proseguiNonE1) {
                        setDatiVarianti(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
                    }
                    break;
                case CostantiW9.SUBAPPALTO:
                    // Dati relativi ai subappalti per contratti sopra soglia
                    if (proseguiNonE1) {
                        setDatiSubappalti(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
                    }
                    break;
            }

        } else {
            // Dati della fase di aggiudicazione per contratti sopra soglia
            if (proseguiNonE1) {
                setAggiudicazione(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
            }
            // Dati della fase di aggiudicazione semplificata per contratti sotto soglia
            setSottosoglia(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);

            // Dati della fase di aggiudicazione semplificata per contratti esclusi
            if (proseguiNonE1) {
                setEscluso(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
            }

            // Adesione ad accordo quadro
            setAdesione(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);

            // Dati di inizio per contratti sopra soglia
            if (proseguiNonE1) {
                setDatiInizio(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
            }

            // Dati di stipula per l'accordo quadro
            if (proseguiNonE1 && proseguiNonS2) {
                setDatiStipula(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
            }

            // Dati relativi agli stati di avanzamento (SAL) per contratti sopra soglia
            if (proseguiNonE1) {
                setDatiAvanzamenti(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
            }

            // Dati di conclusione per contratti sopra soglia
            if (proseguiNonE1) {
                setDatiConclusione(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
            }

            // Dati di collaudo per contratti sopra soglia
            if (proseguiNonE1) {
                setDatiCollaudo(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
            }

            // Dati di recesso per contratti sopra soglia
            if (proseguiNonE1) {
                setDatiRitardo(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
            }

            // Dati relativi agli accordi bonaria per contratti sopra soglia
            if (proseguiNonE1) {
                setDatiAccordi(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
            }

            // Dati relativi alle sospensioni per contratti sopra soglia
            if (proseguiNonE1) {
                setDatiSospensioni(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
            }

            // Dati relativi alle varianti per contratti sopra soglia
            if (proseguiNonE1) {
                setDatiVarianti(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
            }

            // Dati relativi ai subappalti per contratti sopra soglia
            if (proseguiNonE1) {
                setDatiSubappalti(obf, schedaCompleta, codGara, codLotto, numProgressivoFase);
            }
        }

        LOG.debug("Execution end::setSchedaCompleta");
    }

    private void setCUI(final SchedaCompletaType schedaCompleta, final Long codGara, final Long codLotto,
                        final Long faseEsecuzione, final Long numProgressivoFase) {

        LOG.debug("Execution start::setCUI");

        try {
            String codiceCUI = this.getCUI(codGara, codLotto, faseEsecuzione, numProgressivoFase);

            if (codiceCUI != null) {
                schedaCompleta.setCUI(codiceCUI);
            } else {
                schedaCompleta.setCUI("");
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura del codice identificativo dell'aggiudicazione (CUI)", e);
        }

        LOG.debug("Execution end::setCUI");
    }

    private boolean isE1(final DatiE1S2Entry entry) {
        boolean isArtE1 = "1".equals(entry.getArtE1());
        return isArtE1;
    }

    private boolean isS2(final DatiE1S2Entry entry) {
        boolean isExSottoSoglia = "1".equals(entry.getExSottosoglia());
        boolean isS2 = !isExSottoSoglia && !isE1(entry);
        return isS2;
    }

    private String convertiImporto(Double importo) {
        String result = null;

        if (importo != null) {

            DecimalFormatSymbols simbols = new DecimalFormatSymbols();
            simbols.setDecimalSeparator('.');
            DecimalFormat decimalFormat = new DecimalFormat("0.00", simbols);
            result = decimalFormat.format(importo);
        }
        return result;
    }

    private void setAggiudicazione(final ObjectFactory obf, final SchedaCompletaType schedaCompleta, final Long codGara,
                                   final Long codLotto, final Long numProgressivoFase) {
        LOG.debug("Execution start::setAggiudicazione");

        try {
            DatiAggiudicazioneEntry datiAggiudicazioneEntry = loaderAppaltoMapper.getDatiAggiudicazione(codGara,
                    codLotto, numProgressivoFase);

            if (datiAggiudicazioneEntry != null) {

                AggiudicazioneType aggiudicazione = obf.createAggiudicazioneType();

                Long versioneSimog = this.loaderAppaltoMapper.getVersioneSimogGara(codGara);

                // Dati dell'appalto
                setAppalto(obf, aggiudicazione, datiAggiudicazioneEntry, codGara, codLotto, numProgressivoFase, versioneSimog);

                // Lista delle tipologie lavoro
                setTipiAppaltoLav(obf, aggiudicazione, codGara, codLotto);

                // Lista delle tipologie di forniture
                setTipiAppaltoForn(obf, aggiudicazione, codGara, codLotto);

                // Lista delle condizioni negoziate
                // per aggiungerle all'invio su Simog controllo prima che il
                // ricorso alla procedura negoziata sia giustificato
                if (isNegoziabile(codGara, codLotto, versioneSimog)) {
                    List<CondizioneType> listaCondizioni = this.setCondizioni(obf, codGara, codLotto);
                    if (listaCondizioni != null && listaCondizioni.size() > 0) {
                        aggiudicazione.getCondizioni().addAll(listaCondizioni);
                    }
                }

                // Lista dei requisiti di partecipazione
                setRequisiti(obf, aggiudicazione, codGara, codLotto);

                // Lista dei finanziamenti
                List<FinanziamentoType> listaFinanziamenti = setFinanziamenti(obf, codGara, codLotto,
                        numProgressivoFase);
                if (listaFinanziamenti != null && listaFinanziamenti.size() > 0) {
                    aggiudicazione.getFinanziamenti().addAll(listaFinanziamenti);
                }

                // Lista delle ditte aggiudicatarie/affidatarie
                List<SoggAggiudicatarioType> listaAggiudicatari = this.setAggiudicatari(obf, codGara, codLotto, numProgressivoFase);
                if (listaAggiudicatari != null) {
                    aggiudicazione.getAggiudicatari().addAll(listaAggiudicatari);
                }

                // Lista degli incaricati (sezioni PA ed RA)
                List<IncaricatoType> listaIncaricati = setIncaricati(obf, codGara, codLotto,
                        ETipologiaIncaricati.W9INCA_AGGIUDICAZIONE, numProgressivoFase);
                if (listaIncaricati != null && listaIncaricati.size() > 0) {
                    aggiudicazione.getIncaricati().addAll(listaIncaricati);
                }

                // Lista delle ditte ausiliarie (in caso di avvalimento)
                List<DittaAusiliariaType> listaDitteAusiliarie = setDitteAusiliarie(obf, codGara, codLotto);
                if (listaDitteAusiliarie != null && listaDitteAusiliarie.size() > 0) {
                    aggiudicazione.getDitteAusiliarie().addAll(listaDitteAusiliarie);
                }

                // Elenco codici CUP e flag conferma esplicita
                CUPLOTTOType cupLotto = setCupLotto(obf, codGara, codLotto,
                        "aggiudicazione per contratti sopra soglia");
                if (cupLotto != null) {
                    aggiudicazione.setCUPLOTTO(cupLotto);
                }

                schedaCompleta.setAggiudicazione(aggiudicazione);
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella gestione della fase di aggiudicazione per contratti sopra soglia",
                    e);
        }

        LOG.debug("Execution end::setAggiudicazione");
    }

    private void setAppalto(final ObjectFactory obf, final AggiudicazioneType aggiudicazione,
                            final DatiAggiudicazioneEntry datiAggiudicazioneEntry, final Long codGara, final Long codLotto,
                            final Long numProgressivoFase, final Long versioneSimog) {

        LOG.debug("Execution start::setAppalto");

        AppaltoType appalto = obf.createAppaltoType();

        LottoBaseEntry lotto = this.loaderAppaltoMapper.getLotto(codGara, codLotto);

        String cig = lotto.getCig();
        String masterCig = lotto.getMasterCig();

        if (masterCig != null && !"".equals(masterCig)) {

            appalto.setCODICECONTRATTO(masterCig);

            FlagSNType flagAggiudicazionePrincipale = masterCig.equals(cig) ? FlagSNType.S : FlagSNType.N;
            appalto.setFLAGAGGIUDPRINCIPALE(flagAggiudicazionePrincipale);
        }

        String flagSN = datiAggiudicazioneEntry.getProceduraAcc();
        if (flagSN != null && !"".equals(flagSN)) {
            if ("1".equals(flagSN)) {
                appalto.setPROCEDURAACC(FlagSNType.S);
            } else {
                appalto.setPROCEDURAACC(FlagSNType.N);
            }
        }

        flagSN = datiAggiudicazioneEntry.getPreinformazione();
        if (flagSN != null && !"".equals(flagSN)) {
            if ("1".equals(flagSN)) {
                appalto.setPREINFORMAZIONE(FlagSNType.S);
            } else {
                appalto.setPREINFORMAZIONE(FlagSNType.N);
            }
        }

        flagSN = datiAggiudicazioneEntry.getTermineRidotto();
        if (flagSN != null && !"".equals(flagSN)) {
            if ("1".equals(flagSN)) {
                appalto.setTERMINERIDOTTO(FlagSNType.S);
            } else {
                appalto.setTERMINERIDOTTO(FlagSNType.N);
            }
        }

        if (datiAggiudicazioneEntry.getIdModoIndizione() != null) {
            appalto.setIDMODOINDIZIONE(datiAggiudicazioneEntry.getIdModoIndizione().toString());
        }

        if (datiAggiudicazioneEntry.getIdModoGara() != null) {
            appalto.setIDMODOGARA(datiAggiudicazioneEntry.getIdModoGara().toString());
        }

        if (datiAggiudicazioneEntry.getNumImpreseInvitate() != null) {
            appalto.setNUMIMPRESEINVITATE(Integer.parseInt(datiAggiudicazioneEntry.getNumImpreseInvitate().toString()));
        }

        if (datiAggiudicazioneEntry.getNumImpreseRichiedenti() != null) {
            appalto.setNUMIMPRESERICHIEDENTI(
                    Integer.parseInt(datiAggiudicazioneEntry.getNumImpreseRichiedenti().toString()));
        }

        if (datiAggiudicazioneEntry.getNumImpreseOfferenti() != null) {
            appalto.setNUMIMPRESEOFFERENTI(
                    Integer.parseInt(datiAggiudicazioneEntry.getNumImpreseOfferenti().toString()));
        }

        if (datiAggiudicazioneEntry.getNumOfferteAmmesse() != null) {
            appalto.setNUMOFFERTEAMMESSE(Integer.parseInt(datiAggiudicazioneEntry.getNumOfferteAmmesse().toString()));
        }

        if (datiAggiudicazioneEntry.getDataVerbAggiudicazione() != null) {
            appalto.setDATAVERBAGGIUDICAZIONE(
                    Utility.convertDateToCalendar(datiAggiudicazioneEntry.getDataVerbAggiudicazione()));
        }

//        if (datiAggiudicazioneEntry.getDataScadenzaRichiestaInvito() != null) {
//            Long counter = loaderAppaltoMapper.countIdSceltaContraente29(codGara, codLotto);
//            if (counter.longValue() > 0) {
//                appalto.setDATASCADENZARICHIESTAINVITO(
//                        Utility.convertDateToXMLString(datiAggiudicazioneEntry.getDataScadenzaRichiestaInvito()));
//            }
//        }

        if(numProgressivoFase != null && numProgressivoFase > 1L) {
			Long progCuiRiaggiudicato =numProgressivoFase -1L;
			appalto.setPROGCUIRIAGGIUDICATO(Integer.parseInt(progCuiRiaggiudicato+""));
		}
        
        if (datiAggiudicazioneEntry.getImpNonAssog() != null) {
            appalto.setIMPNONASSOG(this.convertiImporto(datiAggiudicazioneEntry.getImpNonAssog().doubleValue()));
        }

        if (datiAggiudicazioneEntry.getImportoAggiudicazione() != null) {
            appalto.setIMPORTOAGGIUDICAZIONE(
                    this.convertiImporto(datiAggiudicazioneEntry.getImportoAggiudicazione().doubleValue()));
        }

        if (datiAggiudicazioneEntry.getIdSceltaContraente() != null) {
            appalto.setIDSCELTACONTRAENTE(datiAggiudicazioneEntry.getIdSceltaContraente().toString());
        }

        if (datiAggiudicazioneEntry.getImportoLavori() != null) {
            appalto.setIMPORTOLAVORI(
                    new BigDecimal(this.convertiImporto(datiAggiudicazioneEntry.getImportoLavori().doubleValue()))
                            .toString());
        }

        if (datiAggiudicazioneEntry.getImportoServizi() != null) {
            appalto.setIMPORTOSERVIZI(
                    new BigDecimal(this.convertiImporto(datiAggiudicazioneEntry.getImportoServizi().doubleValue()))
                            .toString());
        }

        if (datiAggiudicazioneEntry.getImportoForniture() != null) {
            appalto.setIMPORTOFORNITURE(
                    new BigDecimal(this.convertiImporto(datiAggiudicazioneEntry.getImportoForniture().doubleValue()))
                            .toString());
        }

        if (datiAggiudicazioneEntry.getImportoAttuazioneSicurezza() != null) {
            appalto.setIMPORTOATTUAZIONESICUREZZA(new BigDecimal(this.convertiImporto(datiAggiudicazioneEntry.getImportoAttuazioneSicurezza())));
        } else {
            appalto.setIMPORTOATTUAZIONESICUREZZA(new BigDecimal(0));
        }

        if (datiAggiudicazioneEntry.getImportoDisposizione() != null) {
            appalto.setIMPORTODISPOSIZIONE(
                    new BigDecimal(this.convertiImporto(datiAggiudicazioneEntry.getImportoDisposizione().doubleValue()))
                            .toString());
        }

        if (datiAggiudicazioneEntry.getImportoProgettazione() != null) {
            appalto.setIMPORTOPROGETTAZIONE(
                    this.convertiImporto(datiAggiudicazioneEntry.getImportoProgettazione().doubleValue()));
        }

        flagSN = datiAggiudicazioneEntry.getRequisitiSettSpec2();
        if (flagSN != null && !"".equals(flagSN)) {
            if ("1".equals(flagSN)) {
                appalto.setSISTEMAQUALIFICAZIONE(FlagSNType.S);
            } else {
                appalto.setSISTEMAQUALIFICAZIONE(FlagSNType.N);
            }
        }

        flagSN = datiAggiudicazioneEntry.getRequisitiSettSpec1();
        if (flagSN != null && !"".equals(flagSN)) {
            if ("1".equals(flagSN)) {
                appalto.setCRITERISELEZIONESTABILITISA(FlagSNType.S);
            } else {
                appalto.setCRITERISELEZIONESTABILITISA(FlagSNType.N);
            }
        }

        if (datiAggiudicazioneEntry.getIdTipoPrestazione() != null) {
            appalto.setIDTIPOPRESTAZIONE(datiAggiudicazioneEntry.getIdTipoPrestazione().toString());
        }

        if (datiAggiudicazioneEntry.getCup() != null) {
            appalto.setCUP(datiAggiudicazioneEntry.getCup());
            appalto.setFLAGCUP(FlagSNType.S);
        } else {
            appalto.setCUP("");
            appalto.setFLAGCUP(FlagSNType.N);
        }

        flagSN = datiAggiudicazioneEntry.getFlagAccordoQuadro();
        if (flagSN != null && !"".equals(flagSN)) {
            if ("1".equals(flagSN)) {
                appalto.setFLAGACCORDOQUADRO(FlagSNType.S);
            } else {
                appalto.setFLAGACCORDOQUADRO(FlagSNType.N);
            }
        }

        if (datiAggiudicazioneEntry.getLuogoIstat() != null) {
            appalto.setLUOGOISTAT(datiAggiudicazioneEntry.getLuogoIstat());
        } else if (datiAggiudicazioneEntry.getLuogoNuts() != null) {
            appalto.setLUOGONUTS(datiAggiudicazioneEntry.getLuogoNuts());
        }

        flagSN = datiAggiudicazioneEntry.getAstaElettronica();
        if (flagSN != null && !"".equals(flagSN)) {
            if ("1".equals(flagSN)) {
                appalto.setASTAELETTRONICA(FlagSNType.S.toString());
            } else {
                appalto.setASTAELETTRONICA(FlagSNType.N.toString());
            }
        }

        if (datiAggiudicazioneEntry.getPercRibassoAgg() != null) {
            appalto.setPERCRIBASSOAGG(this.convertiImporto(datiAggiudicazioneEntry.getPercRibassoAgg().doubleValue()));
        }

        if (datiAggiudicazioneEntry.getPercOffAumento() != null) {
            appalto.setPERCOFFAUMENTO(this.convertiImporto(datiAggiudicazioneEntry.getPercOffAumento().doubleValue()));
        }

//        if (datiAggiudicazioneEntry.getDataInvito() != null) {
//            appalto.setDATAINVITO(Utility.convertDateToCalendar(datiAggiudicazioneEntry.getDataInvito()));
//        }

        if (datiAggiudicazioneEntry.getNumManifInteresse() != null) {
            appalto.setNUMMANIFINTERESSE(Integer.parseInt(datiAggiudicazioneEntry.getNumManifInteresse().toString()));
        }

        if (datiAggiudicazioneEntry.getDataManifInteresse() != null) {
            appalto.setDATAMANIFINTERESSE(
                    Utility.convertDateToCalendar(datiAggiudicazioneEntry.getDataManifInteresse()));
        }

        flagSN = datiAggiudicazioneEntry.getFlagRichSubappalto();
        if (flagSN != null && !"".equals(flagSN)) {
            if ("1".equals(flagSN)) {
                appalto.setFLAGRICHSUBAPPALTO(FlagSNType.S.toString());
            } else {
                appalto.setFLAGRICHSUBAPPALTO(FlagSNType.N.toString());
            }
        }

        if (datiAggiudicazioneEntry.getNumOfferteEscluse() != null) {
            appalto.setNUMOFFERTEESCLUSE(Integer.parseInt(datiAggiudicazioneEntry.getNumOfferteEscluse().toString()));
        }

        if (datiAggiudicazioneEntry.getOffertaMassimo() != null) {
            appalto.setOFFERTAMASSIMO(
                    new BigDecimal(this.convertiImporto(datiAggiudicazioneEntry.getOffertaMassimo().doubleValue())));
        }

        if (datiAggiudicazioneEntry.getOffertaMinima() != null) {
            appalto.setOFFERTAMINIMA(
                    new BigDecimal(this.convertiImporto(datiAggiudicazioneEntry.getOffertaMinima().doubleValue())));
        }

        if (datiAggiudicazioneEntry.getValSogliaAnomalia() != null) {
            appalto.setVALSOGLIAANOMALIA(
                    new BigDecimal(this.convertiImporto(datiAggiudicazioneEntry.getValSogliaAnomalia().doubleValue())));
        }

        if (datiAggiudicazioneEntry.getNumOfferteFuoriSoglia() != null) {
            appalto.setNUMOFFERTEFUORISOGLIA(
                    Integer.parseInt(datiAggiudicazioneEntry.getNumOfferteFuoriSoglia().toString()));
        }

        if (datiAggiudicazioneEntry.getNumImpEsclInsufGiust() != null) {
            appalto.setNUMIMPESCLINSUFGIUST(
                    Integer.parseInt(datiAggiudicazioneEntry.getNumImpEsclInsufGiust().toString()));
        }

        if (datiAggiudicazioneEntry.getCodStrumento() != null) {
            appalto.setCODSTRUMENTO(datiAggiudicazioneEntry.getCodStrumento());
        }

        if (datiAggiudicazioneEntry.getImpNonAssog() != null) {
            appalto.setIMPNONASSOG(this.convertiImporto(datiAggiudicazioneEntry.getImpNonAssog().doubleValue()));
        }

        flagSN = datiAggiudicazioneEntry.getOpereUrbanizScomputo();
        if (flagSN != null && !"".equals(flagSN)) {
            if ("1".equals(flagSN)) {
                appalto.setOPEREURBANIZSCOMPUTO(FlagSNType.S);
            } else {
                appalto.setOPEREURBANIZSCOMPUTO(FlagSNType.N);
            }
        }

        if (datiAggiudicazioneEntry.getModalitaRiaggiudicazione() != null) {
            appalto.setMODALITARIAGGIUDICAZIONE(datiAggiudicazioneEntry.getModalitaRiaggiudicazione().toString());
        }

        if (StringUtils.isNotBlank(datiAggiudicazioneEntry.getIdSchedaLocale())) {
            appalto.setIDSCHEDALOCALE(datiAggiudicazioneEntry.getIdSchedaLocale());
        }

        if (StringUtils.isNotBlank(datiAggiudicazioneEntry.getIdSchedaSimog())) {
            appalto.setIDSCHEDASIMOG(datiAggiudicazioneEntry.getIdSchedaSimog());
        }

        if (versioneSimog >= 6L) {
            flagSN = datiAggiudicazioneEntry.getFlagRelazioneUnica();
            if (flagSN != null && !"".equals(flagSN)) {
                if ("1".equals(flagSN)) {
                    appalto.setRELAZIONEUNICA(FlagSNType.S);
                } else {
                    appalto.setRELAZIONEUNICA(FlagSNType.N);
                }
            }
        }

        aggiudicazione.setAppalto(appalto);

        LOG.debug("Execution end::setAppalto");
    }

    private void setTipiAppaltoLav(final ObjectFactory obf, final AggiudicazioneType aggiudicazione, final Long codGara,
                                   final Long codLotto) {

        LOG.debug("Execution start::setTipiAppaltoLav");

        try {

            List<Long> listaIdAppaltiLavori = loaderAppaltoMapper.getListaIdAppaltoLavori(codGara, codLotto);

            if (listaIdAppaltiLavori != null && listaIdAppaltiLavori.size() > 0) {
                for (Long iAppaltoLavori : listaIdAppaltiLavori) {
                    if (iAppaltoLavori != null && !"".equals(iAppaltoLavori.toString())) {
                        TipiAppaltoType tipiAppalto = obf.createTipiAppaltoType();
                        tipiAppalto.setIDAPPALTO(iAppaltoLavori.toString());
                        aggiudicazione.getTipiAppaltoLav().add(tipiAppalto);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore nella lettura dei dati delle tipologia di lavorazioni nell'aggiudicazione", e);
        }

        LOG.debug("Execution end::setTipiAppaltoLav");
    }

    private void setTipiAppaltoForn(final ObjectFactory obf, final AggiudicazioneType aggiudicazione,
                                    final Long codGara, final Long codLotto) {

        LOG.debug("Execution start::setTipiAppaltoForn");

        try {

            List<Long> listaIdAppaltiForn = loaderAppaltoMapper.getListaIdAppaltoForn(codGara, codLotto);

            if (listaIdAppaltiForn != null && listaIdAppaltiForn.size() > 0) {
                for (Long iAppaltoForn : listaIdAppaltiForn) {
                    if (iAppaltoForn != null && !"".equals(iAppaltoForn.toString())) {
                        TipiAppaltoType tipiAppalto = obf.createTipiAppaltoType();
                        tipiAppalto.setIDAPPALTO(iAppaltoForn.toString());
                        aggiudicazione.getTipiAppaltoForn().add(tipiAppalto);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Errore nella lettura dei dati della modalita' di acquisizione forniture/servizi", e);
        }

        LOG.debug("Execution end::setTipiAppaltoForn");
    }

    private boolean isNegoziabile(final Long codGara, final Long codLotto, final Long versioneSimog) {
        Long idSceltaContraente = loaderAppaltoMapper.getIdSceltaContraente(codGara, codLotto);
        return versioneSimog != null && versioneSimog == 1L && (Long.valueOf(4L).equals(idSceltaContraente) || Long.valueOf(10L).equals(idSceltaContraente));
    }

    private List<CondizioneType> setCondizioni(final ObjectFactory obf, final Long codGara, final Long codLotto) {

        LOG.debug("Execution start::setCondizioni");

        List<CondizioneType> listaCondizioniType = null;

        try {
            List<Long> listaIdCondizioni = loaderAppaltoMapper.getListaIdCondizioni(codGara, codLotto);

            if (listaIdCondizioni != null && listaIdCondizioni.size() > 0) {
                for (Long idCondizione : listaIdCondizioni) {
                    if (idCondizione != null && !"".equals(idCondizione.toString())) {
                        CondizioneType condizione = obf.createCondizioneType();
                        condizione.setIDCONDIZIONE(idCondizione.toString());

                        if (listaCondizioniType == null) {
                            listaCondizioniType = new ArrayList<>();
                        }

                        listaCondizioniType.add(condizione);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati delle condizioni dell'aggiudicazione", e);
        }

        LOG.debug("Execution end::setCondizioni");

        return listaCondizioniType;
    }

    private void setRequisiti(final ObjectFactory obf, final AggiudicazioneType aggiudicazione, final Long codGara,
                              final Long codLotto) {

        LOG.debug("Execution start::setRequisiti");

        try {
            List<DatiRequisitiPartecipazioneEntry> listaRequisitiPartecipazione = loaderAppaltoMapper
                    .getListaRequisitiPartecipazione(codGara, codLotto);

            if (listaRequisitiPartecipazione != null && listaRequisitiPartecipazione.size() > 0) {

                boolean isEmpty = true;

                for (DatiRequisitiPartecipazioneEntry requisitoEntry : listaRequisitiPartecipazione) {
                    RequisitoType requisito = obf.createRequisitoType();

                    if (requisitoEntry.getIdCategoriaPrevalente() != null) {
                        requisito.setIDCATEGORIA(requisitoEntry.getIdCategoriaPrevalente());
                        isEmpty = false;
                    }

                    if (requisitoEntry.getClasseImporto() != null && !"".equals(requisitoEntry.getClasseImporto())) {
                        requisito.setCLASSEIMPORTO(requisitoEntry.getClasseImporto());
                        isEmpty = false;
                    }

                    String flagSN = requisitoEntry.getPrevalente();
                    if (flagSN != null && !"".equals(flagSN)) {
                        if ("1".equals(flagSN)) {
                            requisito.setPREVALENTE(FlagSNType.S);
                        } else {
                            requisito.setPREVALENTE(FlagSNType.N);
                        }
                        isEmpty = false;
                    }

                    flagSN = requisitoEntry.getScorporabile();
                    if (flagSN != null && !"".equals(flagSN)) {
                        if ("1".equals(flagSN)) {
                            requisito.setSCORPORABILE(FlagSNType.S);
                        } else {
                            requisito.setSCORPORABILE(FlagSNType.N);
                        }
                        isEmpty = false;
                    }

                    flagSN = requisitoEntry.getSubappaltabile();
                    if (flagSN != null && !"".equals(flagSN)) {
                        if ("1".equals(flagSN)) {
                            requisito.setSUBAPPALTABILE(FlagSNType.S);
                        } else {
                            requisito.setSUBAPPALTABILE(FlagSNType.N);
                        }
                        isEmpty = false;
                    }

                    if (!isEmpty) {
                        aggiudicazione.getRequisiti().add(requisito);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati dei requisiti di partecipazione", e);
        }

        LOG.debug("Execution end::setRequisiti");
    }

    private List<FinanziamentoType> setFinanziamenti(final ObjectFactory obf, final Long codGara, final Long codLotto,
                                                     final Long numProgressivoFase) {

        LOG.debug("Execution start::setFinanziamenti");

        List<FinanziamentoType> listaFinanziamentiType = null;

        try {

            List<DatiFinanziamentiEntry> listaFinanziamenti = loaderAppaltoMapper.getListaFinanziamenti(codGara,
                    codLotto, numProgressivoFase);

            if (listaFinanziamenti != null && listaFinanziamenti.size() > 0) {

                boolean isEmpty = true;

                for (DatiFinanziamentiEntry finanziamentoEntry : listaFinanziamenti) {
                    FinanziamentoType finanziamento = obf.createFinanziamentoType();

                    if (finanziamentoEntry.getIdFinanziamento() != null) {
                        finanziamento.setIDFINANZIAMENTO(finanziamentoEntry.getIdFinanziamento());
                        isEmpty = false;
                    }

                    if (finanziamentoEntry.getImportoFinanziamento() != null
                            && !"".equals(finanziamentoEntry.getImportoFinanziamento().toString())) {
                        finanziamento.setIMPORTOFINANZIAMENTO(new BigDecimal(
                                this.convertiImporto(finanziamentoEntry.getImportoFinanziamento().doubleValue())));
                        isEmpty = false;
                    }

                    if (!isEmpty) {
                        if (listaFinanziamentiType == null) {
                            listaFinanziamentiType = new ArrayList<>();
                        }

                        listaFinanziamentiType.add(finanziamento);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati dei finanziamenti", e);
        }

        LOG.debug("Execution end::setFinanziamenti");

        return listaFinanziamentiType;
    }

    private List<SoggAggiudicatarioType> setAggiudicatari(final ObjectFactory obf, final Long codGara,
                                                          final Long codLotto, final Long numProgressivoFase) {

        LOG.debug("Execution start::setAggiudicatari");

        boolean isMultiaggiudicatario = false;

        List<SoggAggiudicatarioType> listaAggiudicatariType = null;

        try {

            // verifico se sono in una situazione con più aggiudicatari
            List<DatiRaggruppamentiW9AggiEntry> numeriRaggruppamentiW9AGGI = this.loaderAppaltoMapper
                    .getNumeriRaggruppamentoW9Aggi(codGara, codLotto, numProgressivoFase);
            if (numeriRaggruppamentiW9AGGI.size() > 1) {
                isMultiaggiudicatario = true;
            } else {
                // verifico se ho più imprese singole o GEIE
                Long numeroSingole = this.loaderAppaltoMapper.getCountImpreseSingoleOrGeie(codGara, codLotto, numProgressivoFase);
                if (numeroSingole > 1) {
                    isMultiaggiudicatario = true;
                }
            }

            List<DatiAggiudicatarioEntry> listaAggiudicatari = loaderAppaltoMapper.getListaAggiudicatari(codGara,
                    codLotto, numProgressivoFase);

            List<String> codImpAusiliarie = new ArrayList<String>();
            for (DatiAggiudicatarioEntry aggiudicatarioEntry : listaAggiudicatari) {
                if (aggiudicatarioEntry.getCfAusiliaria() != null) {
                    codImpAusiliarie.add(aggiudicatarioEntry.getCfAusiliaria());
                }
            }


            if (listaAggiudicatari != null && listaAggiudicatari.size() > 0) {

                boolean isEmpty = true;

                for (DatiAggiudicatarioEntry aggiudicatarioEntry : listaAggiudicatari) {
                    if (!codImpAusiliarie.contains(aggiudicatarioEntry.getCodiceFiscaleAggiudicatario())) {
                        SoggAggiudicatarioType soggAggiudicatari = obf.createSoggAggiudicatarioType();

                        if (aggiudicatarioEntry.getIdTipoAgg() != null) {
                            soggAggiudicatari.setIDTIPOAGG(aggiudicatarioEntry.getIdTipoAgg().toString());
                            isEmpty = false;
                        }

                        if (aggiudicatarioEntry.getRuolo() != null
                                && !"".equals(aggiudicatarioEntry.getRuolo().toString())) {
                            soggAggiudicatari.setRUOLO(aggiudicatarioEntry.getRuolo().toString());
                            isEmpty = false;
                        }

                        if (aggiudicatarioEntry.getFlagAvvalimento() != null
                                && !"".equals(aggiudicatarioEntry.getFlagAvvalimento())) {
                            soggAggiudicatari.setFLAGAVVALIMENTO(aggiudicatarioEntry.getFlagAvvalimento());
                            isEmpty = false;
                        }

                        if (aggiudicatarioEntry.getCodiceFiscaleAggiudicatario() != null
                                && !"".equals(aggiudicatarioEntry.getCodiceFiscaleAggiudicatario())) {
                            soggAggiudicatari.setCODICEFISCALEAGGIUDICATARIO(
                                    aggiudicatarioEntry.getCodiceFiscaleAggiudicatario());
                            isEmpty = false;
                        } else {
                            if (aggiudicatarioEntry.getPivImp() != null
                                    && !"".equals(aggiudicatarioEntry.getPivImp())) {
                                soggAggiudicatari
                                        .setCODICEFISCALEAGGIUDICATARIO(aggiudicatarioEntry.getPivImp());
                                isEmpty = false;
                            }
                        }

                        if (aggiudicatarioEntry.getCfAusiliaria() != null
                                && !"".equals(aggiudicatarioEntry.getCfAusiliaria())) {
                            soggAggiudicatari.setCFAUSILIARIA(aggiudicatarioEntry.getCfAusiliaria());
                            isEmpty = false;
                        } else {
                            if (aggiudicatarioEntry.getPivImpAusiliaria() != null
                                    && !"".equals(aggiudicatarioEntry.getPivImpAusiliaria())) {
                                soggAggiudicatari.setCFAUSILIARIA(aggiudicatarioEntry.getPivImpAusiliaria());
                                isEmpty = false;
                            }
                        }

                        // id_gruppo
                        if (aggiudicatarioEntry.getIdGruppo() != null) {
                            soggAggiudicatari.setIDGRUPPO(aggiudicatarioEntry.getIdGruppo().intValue());
                            isEmpty = false;
                        }
                        // se multiaggiudicatario e l'impresa non è una mandante invio anche l'importo
                        // di aggiudicazione
                        if (isMultiaggiudicatario
                                && (soggAggiudicatari.getRUOLO() == null || soggAggiudicatari.getRUOLO().equals("1"))) {
                            // importo aggiudicazione
                            if (aggiudicatarioEntry.getImportoAggiudicazione() != null) {
                                soggAggiudicatari.setIMPORTOAGGIUDICAZIONE(
                                        convertiImporto(aggiudicatarioEntry.getImportoAggiudicazione().doubleValue()));
                                isEmpty = false;
                            }
                            // Ribasso di aggiudicazione
                            if (aggiudicatarioEntry.getPercRibassoAgg() != null) {
                                soggAggiudicatari.setPERCRIBASSOAGG(
                                        convertiImporto(aggiudicatarioEntry.getPercRibassoAgg().doubleValue()));
                                isEmpty = false;
                            }
                            // Offerta in aumento
                            if (aggiudicatarioEntry.getPercOffAumento() != null) {
                                soggAggiudicatari.setPERCOFFAUMENTO(
                                        convertiImporto(aggiudicatarioEntry.getPercOffAumento().doubleValue()));
                                isEmpty = false;
                            }
                        }

                        // Gestione della nazione
                        if (aggiudicatarioEntry.getNazimp() != null
                                && !"".equals(aggiudicatarioEntry.getNazimp().toString())) {

                            // Se lo stato e' 1 - ITALIA
                            if ("1".equals(aggiudicatarioEntry.getNazimp().toString())) {
                                soggAggiudicatari.setCODICESTATO("");
                            } else {
                                // Ricavo la corrispondenza con il tabellato W3z12
                                String codiceNazione = aggiudicatarioEntry.getNazimp().toString();
                                String statoEstero = this.getStatoEstero(codiceNazione);
                                soggAggiudicatari.setCODICESTATO(statoEstero);
                            }

                            isEmpty = false;
                        } else {
                            soggAggiudicatari.setCODICESTATO("");
                        }

                        if (!isEmpty) {

                            if (listaAggiudicatariType == null) {
                                listaAggiudicatariType = new ArrayList<>();
                            }

                            listaAggiudicatariType.add(soggAggiudicatari);
                        }
                    }

                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati degli aggiudicatari", e);
        }

        LOG.debug("Execution end::setAggiudicatari");

        return listaAggiudicatariType;
    }

    private String getStatoEstero(final String codiceNazione) {

        LOG.debug("Execution start::getStatoEstero");

        String statoEstero = "";
        try {
            statoEstero = loaderAppaltoMapper.getStatoEstero(codiceNazione);
        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura della nazione dell'impresa", e);
        }
        if (statoEstero == null) {
            statoEstero = "";
        }

        LOG.debug("Execution end::getStatoEstero");

        return statoEstero;
    }

    private List<IncaricatoType> setIncaricati(final ObjectFactory obf, final Long codGara, final Long codLotto,
                                               final ETipologiaIncaricati tipologiaIncaricati, final Long numProgressivoFase) {

        LOG.debug("Execution start::setIncaricati for {}", tipologiaIncaricati.toString());

        List<IncaricatoType> listaIncaricatiType = null;

        try {

            List<DatiIncaricatoEntry> listaIncaricati = null;

            switch (tipologiaIncaricati) {
                case W9INCA_AGGIUDICAZIONE:
                    listaIncaricati = loaderAppaltoMapper.getW9IncaAggiudicazione(codGara, codLotto, numProgressivoFase);
                    break;
                case W9INCA_ADESIONE:
                    listaIncaricati = loaderAppaltoMapper.getW9IncaAdesione(codGara, codLotto, numProgressivoFase);
                    break;
                case W9INCA_COLLAUDO:
                    listaIncaricati = loaderAppaltoMapper.getW9IncaCollaudo(codGara, codLotto, numProgressivoFase);
                    break;
                case W9INCA_ESCLUSO:
                    listaIncaricati = loaderAppaltoMapper.getW9IncaEscluso(codGara, codLotto, numProgressivoFase);
                    break;
                case W9INCA_INIZIO:
                    listaIncaricati = loaderAppaltoMapper.getW9IncaIniz(codGara, codLotto, numProgressivoFase);
                    break;
                case W9INCA_SOTTOSOGLIA:
                    listaIncaricati = loaderAppaltoMapper.getW9IncaSottosoglia(codGara, codLotto, numProgressivoFase);
                    break;
                default:
                    break;
            }

            if (listaIncaricati != null && listaIncaricati.size() > 0) {

                for (DatiIncaricatoEntry incaricatoEntry : listaIncaricati) {
                    IncaricatoType incaricato = obf.createIncaricatoType();

                    if (incaricatoEntry.getSezione() != null) {
                        incaricato.setSEZIONE(SezioneType.fromValue(incaricatoEntry.getSezione()));
                    }

                    if (incaricatoEntry.getIdRuolo() != null && !"".equals(incaricatoEntry.getIdRuolo().toString())) {
                        incaricato.setIDRUOLO(incaricatoEntry.getIdRuolo().toString());
                    }

                    if (incaricatoEntry.getCigProgEsterna() != null
                            && !"".equals(incaricatoEntry.getCigProgEsterna())) {
                        incaricato.setCIGPROGESTERNA(incaricatoEntry.getCigProgEsterna());
                    }

                    if (incaricatoEntry.getDataAffProgEsterna() != null) {
                        incaricato.setDATAAFFPROGESTERNA(
                                Utility.convertDateToCalendar(incaricatoEntry.getDataAffProgEsterna()));
                    }

                    if (incaricatoEntry.getDataConsProgEsterna() != null) {
                        incaricato.setDATACONSPROGESTERNA(
                                Utility.convertDateToCalendar(incaricatoEntry.getDataConsProgEsterna()));
                    }

                    if (incaricatoEntry.getCodiceFiscaleResponsabile() != null
                            && !"".equals(incaricatoEntry.getCodiceFiscaleResponsabile())) {
                        incaricato.setCODICEFISCALERESPONSABILE(
                                incaricatoEntry.getCodiceFiscaleResponsabile());
                    } else if (incaricatoEntry.getpIvaTec() != null
                            && !"".equals(incaricatoEntry.getpIvaTec())) {
                        incaricato.setCODICEFISCALERESPONSABILE(incaricatoEntry.getpIvaTec());
                    } else {
                        // set stringa vuota per obbligatorieta' xml
                        incaricato.setCODICEFISCALERESPONSABILE("");
                    }

                    if (listaIncaricatiType == null) {
                        listaIncaricatiType = new ArrayList<>();
                    }

                    listaIncaricatiType.add(incaricato);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati degli incaricati", e);
        }

        LOG.debug("Execution end::setIncaricati");

        return listaIncaricatiType;
    }

    private List<DittaAusiliariaType> setDitteAusiliarie(final ObjectFactory obf, final Long codGara,
                                                         final Long codLotto) {

        LOG.debug("Execution start::setDitteAusiliarie");

        List<DittaAusiliariaType> listaDitteAusiliarieType = null;

        try {

            List<DatiDittaAusiliariaEntry> listaDitteAusiliarieAggi = loaderAppaltoMapper
                    .getListaDitteAusiliarieAggi(codGara, codLotto);

            if (listaDitteAusiliarieAggi != null && listaDitteAusiliarieAggi.size() > 0) {

                boolean isEmpty = true;

                for (DatiDittaAusiliariaEntry dittaAusiliariaEntry : listaDitteAusiliarieAggi) {
                    DittaAusiliariaType dittaAusiliaria = obf.createDittaAusiliariaType();

                    if (dittaAusiliariaEntry.getFlagAvvalimento() != null
                            && !"0".equals(dittaAusiliariaEntry.getFlagAvvalimento())) {
                        dittaAusiliaria.setFLAGAVVALIMENTO(dittaAusiliariaEntry.getFlagAvvalimento());

                        if (dittaAusiliariaEntry.getCodiceFiscaleAggiudicatario() != null
                                && !"".equals(dittaAusiliariaEntry.getCodiceFiscaleAggiudicatario())) {
                            dittaAusiliaria.setCODICEFISCALEAGGIUDICATARIO(
                                    dittaAusiliariaEntry.getCodiceFiscaleAggiudicatario());
                            isEmpty = false;
                        } else {
                            if (dittaAusiliariaEntry.getpIvImp() != null
                                    && !"".equals(dittaAusiliariaEntry.getpIvImp())) {
                                dittaAusiliaria
                                        .setCODICEFISCALEAGGIUDICATARIO(dittaAusiliariaEntry.getpIvImp());
                                isEmpty = false;
                            }
                        }

                        if (dittaAusiliariaEntry.getNazImp() != null
                                && !"".equals(dittaAusiliariaEntry.getNazImp().toString())) {
                            // Se lo stato e' 1 - ITALIA
                            if ("1".equals(dittaAusiliariaEntry.getNazImp().toString())) {
                                dittaAusiliaria.setCODICESTATOAGGIUDICATARIO("");
                            } else {
                                // Ricavo la corrispondenza con il tabellato
                                // W3z12
                                String codiceNazione = dittaAusiliariaEntry.getNazImp().toString();
                                String statoEstero = this.getStatoEstero(codiceNazione);
                                dittaAusiliaria.setCODICESTATOAGGIUDICATARIO(statoEstero);
                            }
                            isEmpty = false;
                        } else {
                            dittaAusiliaria.setCODICESTATOAGGIUDICATARIO("");
                        }

                        if (dittaAusiliariaEntry.getCodiceFiscaleAusiliaria() != null
                                && !"".equals(dittaAusiliariaEntry.getCodiceFiscaleAusiliaria())) {
                            dittaAusiliaria.setCODICEFISCALEAUSILIARIA(
                                    dittaAusiliariaEntry.getCodiceFiscaleAusiliaria());
                            isEmpty = false;
                        } else {
                            if (dittaAusiliariaEntry.getpIvImpAusiliaria() != null
                                    && !"".equals(dittaAusiliariaEntry.getpIvImpAusiliaria())) {
                                dittaAusiliaria.setCODICEFISCALEAUSILIARIA(
                                        dittaAusiliariaEntry.getpIvImpAusiliaria());
                                isEmpty = false;
                            }
                        }

                        if (dittaAusiliariaEntry.getNazImpAusiliaria() != null
                                && !"".equals(dittaAusiliariaEntry.getNazImpAusiliaria().toString())) {
                            // Se lo stato e' 1 - ITALIA
                            if ("1".equals(dittaAusiliariaEntry.getNazImpAusiliaria().toString())) {
                                dittaAusiliaria.setCODICESTATOAUSILIARIA("");
                            } else {
                                // Ricavo la corrispondenza con il tabellato
                                // W3z12
                                String codiceNazione = dittaAusiliariaEntry.getNazImpAusiliaria().toString();
                                String statoEstero = getStatoEstero(codiceNazione);
                                dittaAusiliaria.setCODICESTATOAUSILIARIA(statoEstero);
                            }
                            isEmpty = false;
                        } else {
                            dittaAusiliaria.setCODICESTATOAUSILIARIA("");
                        }

                        if (!isEmpty) {

                            if (listaDitteAusiliarieType == null) {
                                listaDitteAusiliarieType = new ArrayList<>();
                            }

                            listaDitteAusiliarieType.add(dittaAusiliaria);
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati delle ditte ausiliarie", e);
        }

        LOG.debug("Execution end::setDitteAusiliarie");

        return listaDitteAusiliarieType;
    }

    private CUPLOTTOType setCupLotto(final ObjectFactory obf, final Long codGara, final Long codLotto,
                                     final String nomeSchedaCupLotto) {

        LOG.debug("Execution start::setCupLotto");

        try {

            List<DatiCupCigLottoEntry> listaCupCig = loaderAppaltoMapper.getCupCigLotto(codGara, codLotto);

            if (listaCupCig != null && listaCupCig.size() > 0) {

                DatiCupCigLottoEntry first = listaCupCig.get(0);

                if (first.getCup() != null && !"".equals(first.getCup())) {

                    CUPLOTTOType cupLotto = obf.createCUPLOTTOType();
                    cupLotto.setCIG(first.getCig());

                    DatiCUPType datiCup = obf.createDatiCUPType();
                    datiCup.setCUP(first.getCup());
                    datiCup.setOKUTENTE(FlagSNType.S);

                    cupLotto.getCODICICUP().add(datiCup);
                    return cupLotto;
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati del CUP della " + nomeSchedaCupLotto, e);
        }

        LOG.debug("Execution end::setCupLotto");

        return null;
    }

    private void setSottosoglia(final ObjectFactory obf, final SchedaCompletaType schedaCompleta, final Long codGara,
                                final Long codLotto, final Long numeroProgressivoFase) {

        LOG.debug("Execution start::setSottosoglia");

        try {

            DatiAppaltoSottosogliaEntry datiW9APPASOTTOSOGLIA = this.loaderAppaltoMapper
                    .getDatiAppaltoSottosoglia(codGara, codLotto, numeroProgressivoFase);

            if (datiW9APPASOTTOSOGLIA != null) {

                SchedaSottosogliaType schedaSottoSoglia = obf.createSchedaSottosogliaType();

                Long versioneSimog = this.loaderAppaltoMapper.getVersioneSimogGara(codGara);

                // Dati dell'appalto
                this.setAppaltoSottoSoglia(obf, schedaSottoSoglia, datiW9APPASOTTOSOGLIA);

                // Lista delle condizioni negoziate
                // per aggiungerle all'invio su Simog controllo prima che il
                // ricorso alla procedura negoziata sia giustificato
                if (isNegoziabile(codGara, codLotto, versioneSimog)) {
                    List<CondizioneType> listaCondizioni = this.setCondizioni(obf, codGara, codLotto);
                    if (listaCondizioni != null && listaCondizioni.size() > 0) {
                        schedaSottoSoglia.getCondizioni().addAll(listaCondizioni);
                    }
                }
                // Lista degli aggiudicatari
                List<SoggAggiudicatarioType> listaAggiudicatari = this.setAggiudicatari(obf, codGara, codLotto, numeroProgressivoFase);
                if (listaAggiudicatari != null) {
                    schedaSottoSoglia.getAggiudicatari().addAll(listaAggiudicatari);
                }

                // Lista degli incaricati (sezione RS)
                List<IncaricatoType> listaIncaricati = setIncaricati(obf, codGara, codLotto,
                        ETipologiaIncaricati.W9INCA_SOTTOSOGLIA, numeroProgressivoFase);
                if (listaIncaricati != null && listaIncaricati.size() > 0) {
                    schedaSottoSoglia.getIncaricati().addAll(listaIncaricati);
                }

                // Lista dei CUP
                CUPLOTTOType cupLotto = this.setCupLotto(obf, codGara, codLotto,
                        "aggiudicazione per contratti sottosoglia");
                if (cupLotto != null) {
                    schedaSottoSoglia.setCUPLOTTO(cupLotto);
                }

                schedaCompleta.setSottosoglia(schedaSottoSoglia);
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella gestione dei di aggiudicazione per contratti sottosoglia", e);
        }

        LOG.debug("Execution end::setSottosoglia");
    }

    private void setAppaltoSottoSoglia(final ObjectFactory obf, final SchedaSottosogliaType schedaSottoSoglia,
                                       final DatiAppaltoSottosogliaEntry datiW9APPASOTTOSOGLIA) throws RuntimeException {

        LOG.debug("Execution start::setAppaltoSottoSoglia");

        try {

            if (datiW9APPASOTTOSOGLIA != null) {

                SottoEsclusoType sottoEscluso = obf.createSottoEsclusoType();

                if (datiW9APPASOTTOSOGLIA.getLuogoIstat() != null) {
                    sottoEscluso.setLUOGOISTAT(datiW9APPASOTTOSOGLIA.getLuogoIstat());
                } else if (datiW9APPASOTTOSOGLIA.getLuogoNuts() != null) {
                    sottoEscluso.setLUOGONUTS(datiW9APPASOTTOSOGLIA.getLuogoNuts());
                }
                if (datiW9APPASOTTOSOGLIA.getCup() != null) {
                    sottoEscluso.setCUP(datiW9APPASOTTOSOGLIA.getCup());
                    sottoEscluso.setFLAGCUP(FlagSNType.S.toString());
                } else {
                    sottoEscluso.setFLAGCUP(FlagSNType.N.toString());
                }

                if (datiW9APPASOTTOSOGLIA.getImportoComplAppalto() != null) {
                    sottoEscluso.setIMPORTOCOMPLESSIVO(
                            new BigDecimal(this.convertiImporto(datiW9APPASOTTOSOGLIA.getImportoComplAppalto())));
                }
                if (datiW9APPASOTTOSOGLIA.getImportoDisposizione() != null) {
                    sottoEscluso.setIMPORTODISPOSIZIONE(
                            new BigDecimal(this.convertiImporto((datiW9APPASOTTOSOGLIA.getImportoDisposizione())))
                                    .toString());
                }
                if (datiW9APPASOTTOSOGLIA.getIdSceltaContraente() != null) {
                    sottoEscluso.setIDSCELTACONTRAENTE(datiW9APPASOTTOSOGLIA.getIdSceltaContraente().toString());
                }
                if (datiW9APPASOTTOSOGLIA.getAstaElettronica() != null) {
                    if ("1".equals(datiW9APPASOTTOSOGLIA.getAstaElettronica())) {
                        sottoEscluso.setASTAELETTRONICA(FlagSNType.S.toString());
                    } else {
                        sottoEscluso.setASTAELETTRONICA(FlagSNType.N.toString());
                    }
                }
                if (datiW9APPASOTTOSOGLIA.getPercRibasso() != null) {
                    sottoEscluso.setPERCRIBASSOAGG(this.convertiImporto(datiW9APPASOTTOSOGLIA.getPercRibasso()));
                }
                if (datiW9APPASOTTOSOGLIA.getPercAumento() != null) {
                    sottoEscluso.setPERCOFFAUMENTO(this.convertiImporto(datiW9APPASOTTOSOGLIA.getPercAumento()));
                }
                if (datiW9APPASOTTOSOGLIA.getImportoAggiudicazione() != null) {
                    sottoEscluso.setIMPORTOAGGIUDICAZIONE(
                            this.convertiImporto(datiW9APPASOTTOSOGLIA.getImportoAggiudicazione()));
                }
                if (datiW9APPASOTTOSOGLIA.getDataVerbAggiudicazione() != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
                    String formattedDate = sdf.format(datiW9APPASOTTOSOGLIA.getDataVerbAggiudicazione());
                    sottoEscluso.setDATAAGGIUDICAZIONE(formattedDate);
                }
                if (datiW9APPASOTTOSOGLIA.getDataStipula() != null) {
                    sottoEscluso.setDATASTIPULA(Utility.convertDateToCalendar(datiW9APPASOTTOSOGLIA.getDataStipula()));
                }
                if (datiW9APPASOTTOSOGLIA.getDataTermine() != null) {
                    sottoEscluso.setTERMINECONTRATTUALE(
                            Utility.convertDateToCalendar(datiW9APPASOTTOSOGLIA.getDataTermine()));
                }
                if (datiW9APPASOTTOSOGLIA.getDurataContratto() != null) {
                    sottoEscluso.setDURATACONTRATTUALE(
                            Integer.parseInt(datiW9APPASOTTOSOGLIA.getDurataContratto().toString()));
                }
                if (StringUtils.isNotBlank(datiW9APPASOTTOSOGLIA.getIdSchedaLocale())) {
                    sottoEscluso.setIDSCHEDALOCALE(datiW9APPASOTTOSOGLIA.getIdSchedaLocale());
                }
                if (StringUtils.isNotBlank(datiW9APPASOTTOSOGLIA.getIdSchedaSimog())) {
                    sottoEscluso.setIDSCHEDASIMOG(datiW9APPASOTTOSOGLIA.getIdSchedaSimog());
                }
                schedaSottoSoglia.setAppalto(sottoEscluso);
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati di aggiudicazione per contratti sottosoglia", e);
        }

        LOG.debug("Execution end::setAppaltoSottoSoglia");
    }

    /**
     * Fase di aggiudicazione ridotta per contratti esclusi.
     *
     * @param obf                   ObjectFactory
     * @param schedaCompleta        SchedaCompletaType
     * @param codGara               codice della gara
     * @param codLotto              codice del lotto
     * @param numeroProgressivoFase numero progressivo fase
     */
    private void setEscluso(final ObjectFactory obf, final SchedaCompletaType schedaCompleta, final Long codGara,
                            final Long codLotto, final Long numeroProgressivoFase) {

        LOG.debug("Execution start::setEscluso");

        try {

            DatiAppaltoEsclusioneEntry datiW9APPAESCLUSO = this.loaderAppaltoMapper.getDatiAppaltoEsclusione(codGara,
                    codLotto, numeroProgressivoFase);

            if (datiW9APPAESCLUSO != null) {

                SchedaEsclusoType schedaEscluso = obf.createSchedaEsclusoType();

                // Dati dell'aggiudicazione
                this.setAppaltoEscluso(obf, schedaEscluso, datiW9APPAESCLUSO);

                // Lista delle ditte aggiudicatarie/affidatarie
                List<SoggAggiudicatarioType> listaAggiudicatari = this.setAggiudicatari(obf, codGara, codLotto, numeroProgressivoFase);
                if (listaAggiudicatari != null && listaAggiudicatari.size() > 0) {
                    schedaEscluso.getAggiudicatari().addAll(listaAggiudicatari);
                }

                // Lista degli incaricati (sezione RE)
                List<IncaricatoType> listaIncaricati = setIncaricati(obf, codGara, codLotto,
                        ETipologiaIncaricati.W9INCA_ESCLUSO, numeroProgressivoFase);
                if (listaIncaricati != null && listaIncaricati.size() > 0) {
                    schedaEscluso.getIncaricati().addAll(listaIncaricati);
                }

                // Lista dei CUP
                CUPLOTTOType cupLotto = this.setCupLotto(obf, codGara, codLotto,
                        "aggiudicazione per contratti esclusi");
                if (cupLotto != null) {
                    schedaEscluso.setCUPLOTTO(cupLotto);
                }

                schedaCompleta.setEscluso(schedaEscluso);

            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella gestione dei dati di aggiudicazione per contratti esclusi", e);
        }

        LOG.debug("Execution end::setEscluso");

    }

    /**
     * Dati di aggiudicazione semplificata per contratti esclusi.
     *
     * @param obf               ObjectFactory
     * @param schedaEscluso     SchedaEsclusoType
     * @param datiW9APPAESCLUSO DatiAppaltoEsclusioneEntry
     */
    private void setAppaltoEscluso(final ObjectFactory obf, final SchedaEsclusoType schedaEscluso,
                                   final DatiAppaltoEsclusioneEntry datiW9APPAESCLUSO) {

        LOG.debug("Execution start::setAppaltoEscluso");

        try {

            if (datiW9APPAESCLUSO != null) {

                SottoEsclusoType sottoEscluso = obf.createSottoEsclusoType();

                if (datiW9APPAESCLUSO.getLuogoIstat() != null) {
                    sottoEscluso.setLUOGOISTAT(datiW9APPAESCLUSO.getLuogoIstat());
                } else if (datiW9APPAESCLUSO.getLuogoNuts() != null) {
                    sottoEscluso.setLUOGONUTS(datiW9APPAESCLUSO.getLuogoNuts());
                }
                if (datiW9APPAESCLUSO.getCup() != null) {
                    sottoEscluso.setCUP(datiW9APPAESCLUSO.getCup());
                    sottoEscluso.setFLAGCUP(FlagSNType.S.toString());
                } else {
                    sottoEscluso.setFLAGCUP(FlagSNType.N.toString());
                }

                if (datiW9APPAESCLUSO.getImportoComplAppalto() != null) {
                    sottoEscluso.setIMPORTOCOMPLESSIVO(
                            new BigDecimal(this.convertiImporto(datiW9APPAESCLUSO.getImportoComplAppalto())));
                }
                if (datiW9APPAESCLUSO.getImportoDisposizione() != null) {
                    sottoEscluso.setIMPORTODISPOSIZIONE(
                            new BigDecimal(this.convertiImporto(datiW9APPAESCLUSO.getImportoDisposizione()))
                                    .toString());
                }
                if (datiW9APPAESCLUSO.getIdSceltaContraente() != null) {
                    sottoEscluso.setIDSCELTACONTRAENTE(datiW9APPAESCLUSO.getIdSceltaContraente().toString());
                }

                if (datiW9APPAESCLUSO.getAstaElettronica() != null
                        && !"".equals(datiW9APPAESCLUSO.getAstaElettronica())) {
                    if ("1".equals(datiW9APPAESCLUSO.getAstaElettronica())) {
                        sottoEscluso.setASTAELETTRONICA(FlagSNType.S.toString());
                    } else {
                        sottoEscluso.setASTAELETTRONICA(FlagSNType.N.toString());
                    }
                }
                if (datiW9APPAESCLUSO.getPercRibasso() != null) {
                    sottoEscluso.setPERCRIBASSOAGG(this.convertiImporto(datiW9APPAESCLUSO.getPercRibasso()));
                }
                if (datiW9APPAESCLUSO.getPercAumento() != null) {
                    sottoEscluso.setPERCOFFAUMENTO(this.convertiImporto(datiW9APPAESCLUSO.getPercAumento()));
                }
                if (datiW9APPAESCLUSO.getImportoAggiudicazione() != null) {
                    sottoEscluso.setIMPORTOAGGIUDICAZIONE(
                            this.convertiImporto(datiW9APPAESCLUSO.getImportoAggiudicazione()));
                }
                if (datiW9APPAESCLUSO.getDataVerbAggiudicazione() != null) {
                    sottoEscluso.setDATAAGGIUDICAZIONE(
                            Utility.convertDateToXMLString(datiW9APPAESCLUSO.getDataVerbAggiudicazione()));
                }
                if (datiW9APPAESCLUSO.getDataStipula() != null) {
                    sottoEscluso.setDATASTIPULA(Utility.convertDateToCalendar(datiW9APPAESCLUSO.getDataStipula()));
                }
                if (datiW9APPAESCLUSO.getDataTermine() != null) {
                    sottoEscluso
                            .setTERMINECONTRATTUALE(Utility.convertDateToCalendar(datiW9APPAESCLUSO.getDataTermine()));
                }
                if (datiW9APPAESCLUSO.getDurataContratto() != null) {
                    sottoEscluso
                            .setDURATACONTRATTUALE(Integer.parseInt(datiW9APPAESCLUSO.getDurataContratto().toString()));
                }
                if (StringUtils.isNotBlank(datiW9APPAESCLUSO.getIdSchedaLocale())) {
                    sottoEscluso.setIDSCHEDALOCALE(datiW9APPAESCLUSO.getIdSchedaLocale());
                }
                if (StringUtils.isNotBlank(datiW9APPAESCLUSO.getIdSchedaSimog())) {
                    sottoEscluso.setIDSCHEDASIMOG(datiW9APPAESCLUSO.getIdSchedaSimog());
                }

                schedaEscluso.setAppalto(sottoEscluso);
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati di aggiudicazione per contratti esclusi", e);
        }
        LOG.debug("Execution end::setAppaltoEscluso");
    }

    /**
     * Fase di adesione ad accordo quadro.
     *
     * @param obf                   ObjectFactory
     * @param schedaCompleta        SchedaCompletaType
     * @param codGara               codice della gara
     * @param codLotto              codice del lotto
     * @param numeroProgressivoFase numero progressivo fase
     */
    private void setAdesione(final ObjectFactory obf, final SchedaCompletaType schedaCompleta, final Long codGara,
                             final Long codLotto, final Long numeroProgressivoFase) {

        LOG.debug("Execution start::setAdesione");

        try {

            DatiAdesioneAccordoEntry datiW9APPAADESIONE = this.loaderAppaltoMapper.getDatiAdesioneAccordoQuadro(codGara,
                    codLotto, numeroProgressivoFase);
            if (datiW9APPAADESIONE != null) {

                AdesioneType adesione = obf.createAdesioneType();

                // Dati dell'aggiudicazione
                this.setAppaltoAdesione(obf, adesione, datiW9APPAADESIONE);

                // Lista dei finanziamenti
                List<FinanziamentoType> listaFinanziamenti = setFinanziamenti(obf, codGara, codLotto,
                        numeroProgressivoFase);
                if (listaFinanziamenti != null && listaFinanziamenti.size() > 0) {
                    adesione.getFinanziamenti().addAll(listaFinanziamenti);
                }

                // Lista delle ditta aggiudicatarie/affidatarie
                List<SoggAggiudicatarioType> listaAggiudicatari = this.setAggiudicatari(obf, codGara, codLotto, numeroProgressivoFase);
                if (listaAggiudicatari != null && listaAggiudicatari.size() > 0) {
                    adesione.getAggiudicatari().addAll(listaAggiudicatari);
                }

                // Lista degli incaricati (sezione RQ)
                List<IncaricatoType> listaIncaricati = setIncaricati(obf, codGara, codLotto,
                        ETipologiaIncaricati.W9INCA_ADESIONE, numeroProgressivoFase);
                if (listaIncaricati != null && listaIncaricati.size() > 0) {
                    adesione.getIncaricati().addAll(listaIncaricati);
                }

                List<DittaAusiliariaType> listaDitteAusiliarie = setDitteAusiliarie(obf, codGara, codLotto);
                if (listaDitteAusiliarie != null && listaDitteAusiliarie.size() > 0) {
                    adesione.getDitteAusiliarie().addAll(listaDitteAusiliarie);
                }

                schedaCompleta.setAdesione(adesione);
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella gestione della fase di adesione all'accordo quadro", e);
        }

        LOG.debug("Execution end::setAdesione");

    }

    /**
     * Dati di adesione all'accordo quadro.
     *
     * @param obf          ObjectFactory
     * @param adesione     AdesioneType
     * @param datiAdesione DatiAdesioneAccordoEntry
     */
    private void setAppaltoAdesione(final ObjectFactory obf, final AdesioneType adesione,
                                    final DatiAdesioneAccordoEntry datiAdesione) {
        try {

            if (datiAdesione != null) {

                AppaltoAdesioneType appaltoAdesione = obf.createAppaltoAdesioneType();

                if (datiAdesione.getLuogoIstat() != null) {
                    appaltoAdesione.setLUOGOISTAT(datiAdesione.getLuogoIstat());
                } else if (datiAdesione.getLuogoNuts() != null) {
                    appaltoAdesione.setLUOGONUTS(datiAdesione.getLuogoNuts());
                }
                if (datiAdesione.getCodStrumento() != null) {
                    appaltoAdesione.setCODSTRUMENTO(datiAdesione.getCodStrumento());
                }
                if (datiAdesione.getImportoLavori() != null) {
                    appaltoAdesione.setIMPORTOLAVORI(
                            new BigDecimal(this.convertiImporto(datiAdesione.getImportoLavori())).toString());
                }
                if (datiAdesione.getImportoServizi() != null) {
                    appaltoAdesione.setIMPORTOSERVIZI(
                            new BigDecimal(this.convertiImporto(datiAdesione.getImportoServizi())).toString());
                }
                if (datiAdesione.getImportoForniture() != null) {
                    appaltoAdesione.setIMPORTOFORNITURE(
                            new BigDecimal(this.convertiImporto(datiAdesione.getImportoForniture())).toString());
                }
                if (datiAdesione.getImportoAttuazioneSicurezza() != null) {
                    appaltoAdesione.setIMPORTOATTUAZIONESICUREZZA(
                            new BigDecimal(this.convertiImporto(datiAdesione.getImportoAttuazioneSicurezza())));
                }

                if (datiAdesione.getImpNonAssog() != null) {
                    appaltoAdesione.setIMPNONASSOG(new BigDecimal(this.convertiImporto(datiAdesione.getImpNonAssog())).toString());
                }
                if (datiAdesione.getPercRibasso() != null) {
                    appaltoAdesione.setPERCRIBASSOAGG(this.convertiImporto(datiAdesione.getPercRibasso()));
                }
                if (datiAdesione.getPercAumento() != null) {
                    appaltoAdesione.setPERCOFFAUMENTO(this.convertiImporto(datiAdesione.getPercAumento()));
                }
                if (datiAdesione.getImportoAgg() != null) {
                    appaltoAdesione.setIMPORTOAGGIUDICAZIONE(this.convertiImporto((datiAdesione.getImportoAgg())));
                }
                if (datiAdesione.getDataVerbAgg() != null) {
                    appaltoAdesione.setDATAAGGIUDICAZIONE(
                            Utility.convertDateToCalendar(datiAdesione.getDataVerbAgg()).toString());
                }
                if (datiAdesione.getRichSubappalto() != null && !"".equals(datiAdesione.getRichSubappalto())) {
                    if ("1".equals(datiAdesione.getRichSubappalto())) {
                        appaltoAdesione.setFLAGRICHSUBAPPALTO(FlagSNType.S.toString());
                    } else {
                        appaltoAdesione.setFLAGRICHSUBAPPALTO(FlagSNType.N.toString());
                    }
                }
                if (StringUtils.isNotBlank(datiAdesione.getIdSchedaLocale())) {
                    appaltoAdesione.setIDSCHEDALOCALE(datiAdesione.getIdSchedaLocale());
                }
                if (StringUtils.isNotBlank(datiAdesione.getIdSchedaSimog())) {
                    appaltoAdesione.setIDSCHEDASIMOG(datiAdesione.getIdSchedaSimog());
                }
                adesione.setAppalto(appaltoAdesione);
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati di aggiudicazione per adesione ad accordo quadro",
                    e);
        }

        LOG.debug("setAppaltoAdesione: fine metodo");
    }

    private void setDatiInizio(final ObjectFactory obf, final SchedaCompletaType schedaCompleta, final Long codGara,
                               final Long codLotto, final Long numeroProgressivoFase) {

        LOG.debug("Execution start::setDatiInizio");

        try {

            DatiInizioEntry datoIni = this.loaderAppaltoMapper.getDatiInizio(codGara, codLotto, numeroProgressivoFase);
            if (datoIni != null) {

                DatiInizioType datiInizioType = obf.createDatiInizioType();

                // Pubblicazione dell'esito
                this.setPubblicazioneEsito(obf, datiInizioType, codGara, codLotto, numeroProgressivoFase);

                // Inizio
                this.setInizio(obf, datiInizioType, datoIni);

                // Posizioni contributive/assicurative delle imprese aggiudicatrici
                this.setPosizioni(obf, datiInizioType, codGara, codLotto, numeroProgressivoFase);

                // Lista degli incaricati (sezione IN)
                List<IncaricatoType> listaIncaricati = setIncaricati(obf, codGara, codLotto,
                        ETipologiaIncaricati.W9INCA_INIZIO, numeroProgressivoFase);
                if (listaIncaricati != null && listaIncaricati.size() > 0) {
                    datiInizioType.getIncaricati().addAll(listaIncaricati);
                }

                schedaCompleta.setDatiInizio(datiInizioType);

            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati della fase di inizio", e);
        }

        LOG.debug("Execution end::setDatiInizio");
    }

    private void setPubblicazioneEsito(ObjectFactory obf, DatiInizioType datiInizio, Long codGara, Long codLotto, Long numIniz) {

        LOG.debug("Execution start::setPubblicazioneEsito");

        DatiPubbEsitoEntry datiPubbEsito = this.loaderAppaltoMapper.getDatiPubbEsito(codGara, codLotto, numIniz);

        try {
            if (datiPubbEsito != null) {

                boolean isEmpty = true;
                PubblicazioneType pubblicazioneEsito = obf.createPubblicazioneType();

                if (datiPubbEsito.getDataGuce() != null) {
                    pubblicazioneEsito.setDATAGUCE(Utility.convertDateToCalendar(datiPubbEsito.getDataGuce()));
                    isEmpty = false;
                }
                if (datiPubbEsito.getDataGuri() != null) {
                    pubblicazioneEsito.setDATAGURI(Utility.convertDateToCalendar(datiPubbEsito.getDataGuri()));
                    isEmpty = false;
                }
                if (datiPubbEsito.getDataAlbo() != null) {
                    pubblicazioneEsito.setDATAALBO(Utility.convertDateToCalendar(datiPubbEsito.getDataAlbo()));
                    isEmpty = false;
                }
                if (datiPubbEsito.getQuotidianiNaz() != null) {
                    pubblicazioneEsito.setQUOTIDIANINAZ(Integer.parseInt(datiPubbEsito.getQuotidianiNaz().toString()));
                    isEmpty = false;
                }
                if (datiPubbEsito.getQuotidianiReg() != null) {
                    pubblicazioneEsito.setQUOTIDIANIREG(Integer.parseInt(datiPubbEsito.getQuotidianiReg().toString()));
                    isEmpty = false;
                }

                if (datiPubbEsito.getProfiloCommittente() != null
                        && !"".equals(datiPubbEsito.getProfiloCommittente().toString())) {
                    if ("1".equals(datiPubbEsito.getProfiloCommittente().toString())) {
                        pubblicazioneEsito.setPROFILOCOMMITTENTE(FlagSNType.S);
                        isEmpty = false;
                    } else {
                        pubblicazioneEsito.setPROFILOCOMMITTENTE(FlagSNType.N);
                        isEmpty = false;
                    }
                }
                if (datiPubbEsito.getSitoMinistrInfr() != null
                        && !"".equals(datiPubbEsito.getSitoMinistrInfr().toString())) {
                    if ("1".equals(datiPubbEsito.getSitoMinistrInfr().toString())) {
                        pubblicazioneEsito.setSITOMINISTEROINFTRASP(FlagSNType.S);
                        isEmpty = false;
                    } else {
                        pubblicazioneEsito.setSITOMINISTEROINFTRASP(FlagSNType.N);
                        isEmpty = false;
                    }
                }
                if (datiPubbEsito.getSitoOsservatorio() != null
                        && !"".equals(datiPubbEsito.getSitoOsservatorio().toString())) {
                    if ("1".equals(datiPubbEsito.getSitoOsservatorio().toString())) {
                        pubblicazioneEsito.setSITOOSSERVATORIOCP(FlagSNType.S);
                        isEmpty = false;
                    } else {
                        pubblicazioneEsito.setSITOOSSERVATORIOCP(FlagSNType.N);
                        isEmpty = false;
                    }
                }
                if (StringUtils.isNotBlank(datiPubbEsito.getIdSchedaLocale())) {
                    pubblicazioneEsito.setIDSCHEDALOCALE(datiPubbEsito.getIdSchedaLocale());
                    isEmpty = false;
                }
                if (StringUtils.isNotBlank(datiPubbEsito.getIdSchedaSimog())) {
                    pubblicazioneEsito.setIDSCHEDASIMOG(datiPubbEsito.getIdSchedaSimog());
                    isEmpty = false;
                }

                if (!isEmpty) {
                    datiInizio.setPubblicazioneEsito(pubblicazioneEsito);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati di pubblicazione dell'esito", e);
        }

        LOG.debug("Execution end::setPubblicazioneEsito");
    }

    private void setInizio(ObjectFactory obf, DatiInizioType datiInizio, DatiInizioEntry riga) {

        LOG.debug("Execution start::setInizio");

        try {

            if (riga != null) {
                InizioType inizio = obf.createInizioType();

                if (riga.getDataStipula() != null) {
                    inizio.setDATASTIPULA(Utility.convertDateToCalendar(riga.getDataStipula()));
                }
                if (riga.getDataEsecutivita() != null) {
                    inizio.setDATAESECUTIVITA(Utility.convertDateToCalendar(riga.getDataEsecutivita()));
                }
                if (riga.getImportoCauzione() != null) {
                    inizio.setIMPORTOCAUZIONE(new BigDecimal(this.convertiImporto(riga.getImportoCauzione())));
                }
                if (riga.getDataInizProg() != null) {
                    inizio.setDATAINIPROGESEC(Utility.convertDateToCalendar(riga.getDataInizProg()));
                }
                if (riga.getDataApprovProg() != null) {
                    inizio.setDATAAPPPROGESEC(Utility.convertDateToCalendar(riga.getDataApprovProg()));
                }
                if (riga.getFlagFrazionata() != null && !"".equals(riga.getFlagFrazionata())) {
                    if ("1".equals(riga.getFlagFrazionata())) {
                        inizio.setFLAGFRAZIONATA(FlagSNType.S);
                    } else {
                        inizio.setFLAGFRAZIONATA(FlagSNType.N);
                    }
                }
                if (riga.getDataVerbCons() != null) {
                    inizio.setDATAVERBALECONS(Utility.convertDateToCalendar(riga.getDataVerbCons()));
                }
                if (riga.getDataVerbDef() != null) {
                    inizio.setDATAVERBALEDEF(Utility.convertDateToCalendar(riga.getDataVerbDef()));
                }
                if (riga.getFlagRiserva() != null && !"".equals(riga.getFlagRiserva())) {
                    if ("1".equals(riga.getFlagRiserva())) {
                        inizio.setFLAGRISERVA(FlagSNType.S.toString());
                    } else {
                        inizio.setFLAGRISERVA(FlagSNType.N.toString());
                    }
                }
                if (riga.getDataVerbInizio() != null) {
                    inizio.setDATAVERBINIZIO(Utility.convertDateToCalendar(riga.getDataVerbInizio()));
                }
                if (riga.getDataTermine() != null) {
                    inizio.setDATATERMINE(Utility.convertDateToXMLString(riga.getDataTermine()));
                }
                if (StringUtils.isNotBlank(riga.getIdSchedaLocale())) {
                    inizio.setIDSCHEDALOCALE(riga.getIdSchedaLocale());
                }
                if (StringUtils.isNotBlank(riga.getIdSchedaSimog())) {
                    inizio.setIDSCHEDASIMOG(riga.getIdSchedaSimog());
                }
                datiInizio.setInizio(inizio);

            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati di inizio", e);
        }

        LOG.debug("Execution end::setInizio");

    }

    private void setPosizioni(ObjectFactory obf, DatiInizioType datiInizio, Long codGara, Long codLotto, Long numeroProgressivoFase) {

        LOG.debug("Execution start::setPosizioni");

        try {
            List<DatiPosizioniEntry> datiPosizioni = this.loaderAppaltoMapper.getDatiPosizioni(codGara, codLotto, numeroProgressivoFase);

            if (datiPosizioni != null && datiPosizioni.size() > 0) {

                for (DatiPosizioniEntry riga : datiPosizioni) {

                    PosizioneType posizione = obf.createPosizioneType();

                    if (riga.getCfAggiudicatario() != null) {
                        posizione.setCODICEFISCALEAGGIUDICATARIO(riga.getCfAggiudicatario());
                    } else {
                        if (riga.getPivimp() != null) {
                            posizione.setCODICEFISCALEAGGIUDICATARIO(riga.getPivimp());
                        }
                    }

                    if (riga.getCodiceInps() != null) {
                        posizione.setCODICEINPS(riga.getCodiceInps());
                    }
                    if (riga.getCodiceInail() != null) {
                        posizione.setCODICEINAIL(riga.getCodiceInail());
                    }
                    if (riga.getCodiceCassa() != null) {
                        posizione.setCODICECASSA(riga.getCodiceCassa());
                    }

                    if (riga.getNazimp() != null && !"".equals(riga.getNazimp().toString())) {
                        // Se lo stato e' 1 - ITALIA
                        if ("1".equals(riga.getNazimp().toString())) {
                            posizione.setCODICESTATO("");
                        } else {
                            // Ricavo la corrispondenza con il tabellato W3z12
                            String codiceNazione = riga.getNazimp().toString();
                            String statoEstero = getStatoEstero(codiceNazione);
                            posizione.setCODICESTATO(statoEstero);
                        }
                    } else {
                        posizione.setCODICESTATO("");
                    }
                    datiInizio.getPosizioni().add(posizione);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati delle posizioni contributive / assicurative", e);
        }
        LOG.debug("Execution end::setPosizioni");
    }

    private void setDatiStipula(final ObjectFactory obf, final SchedaCompletaType schedaCompleta, final Long codGara,
                                final Long codLotto, final Long numeroProgressivoFase) {

        LOG.debug("Execution start::setDatiStipula");

        try {

            DatiStipulaEntry datiW9INIZSTIPULA = this.loaderAppaltoMapper.getDatiStipula(codGara, codLotto,
                    numeroProgressivoFase);

            if (datiW9INIZSTIPULA != null) {

                DatiStipulaType datiStipula = obf.createDatiStipulaType();

                // Dati dell'accordo quadro e dei termini di esecuzione
                this.setStipula(obf, datiStipula, datiW9INIZSTIPULA);

                // Dati di pubblicazione dell'esito procedura di selezione
                this.setPubblicazioneStipula(obf, datiStipula, codGara, codLotto);

                schedaCompleta.setDatiStipula(datiStipula);

            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella gestione della fase di adesione all'accordo quadro", e);
        }

        LOG.debug("Execution end::setDatiStipula");

    }

    private void setStipula(final ObjectFactory obf, final DatiStipulaType datiStipula,
                            final DatiStipulaEntry stipula) {

        LOG.debug("Execution start::setStipula");

        try {
            if (stipula != null) {

                StipulaType stipulaType = obf.createStipulaType();

                if (stipula.getDataStipula() != null) {
                    stipulaType.setDATASTIPULA(Utility.convertDateToCalendar(stipula.getDataStipula()));
                }

                if (stipula.getDataDecorrenza() != null) {
                    stipulaType.setDATADECORRRENZA(Utility.convertDateToCalendar(stipula.getDataDecorrenza()));
                }

                if (stipula.getDataScadenza() != null) {
                    stipulaType.setDATASCADENZA(Utility.convertDateToCalendar(stipula.getDataScadenza()));
                }

                if (StringUtils.isNotBlank(stipula.getIdSchedaLocale())) {
                    stipulaType.setIDSCHEDALOCALE(stipula.getIdSchedaLocale());
                }

                if (StringUtils.isNotBlank(stipula.getIdSchedaSimog())) {
                    stipulaType.setIDSCHEDASIMOG(stipula.getIdSchedaSimog());
                }

                datiStipula.setStipula(stipulaType);
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati di stipula dell'accordo quadro", e);
        }
        LOG.debug("Execution end::setStipula");
    }

    private void setPubblicazioneStipula(ObjectFactory obf, DatiStipulaType datiStipula, Long codGara, Long codLotto) {

        LOG.debug("Execution start::setPubblicazioneStipula");

        try {
            DatiPubbEsitoStipulaEntry recordStipula = this.loaderAppaltoMapper.getDatiPubbEsitoStipula(codGara,
                    codLotto);

            if (recordStipula != null) {

                boolean isEmpty = true;

                PubblicazioneType pubblicazione = obf.createPubblicazioneType();

                if (recordStipula.getDataGuce() != null) {
                    pubblicazione.setDATAGUCE(Utility.convertDateToCalendar(recordStipula.getDataGuce()));
                    isEmpty = false;
                }
                if (recordStipula.getDataGuri() != null) {
                    pubblicazione.setDATAGURI(Utility.convertDateToCalendar(recordStipula.getDataGuri()));
                    isEmpty = false;
                }
                if (recordStipula.getQuotidianiNaz() != null) {
                    pubblicazione.setQUOTIDIANINAZ(Integer.parseInt(recordStipula.getQuotidianiNaz().toString()));
                    isEmpty = false;
                }
                if (recordStipula.getQuotidianiReg() != null) {
                    pubblicazione.setQUOTIDIANIREG(Integer.parseInt(recordStipula.getQuotidianiReg().toString()));
                    isEmpty = false;
                }

                if (recordStipula.getProfiloCommittente() != null
                        && !"".equals(recordStipula.getProfiloCommittente())) {
                    if ("1".equals(recordStipula.getProfiloCommittente())) {
                        pubblicazione.setPROFILOCOMMITTENTE(FlagSNType.S);
                        isEmpty = false;
                    } else {
                        pubblicazione.setPROFILOCOMMITTENTE(FlagSNType.N);
                        isEmpty = false;
                    }
                }
                if (recordStipula.getSitoMinistrInfr() != null && !"".equals(recordStipula.getSitoMinistrInfr())) {
                    if ("1".equals(recordStipula.getSitoMinistrInfr())) {
                        pubblicazione.setSITOMINISTEROINFTRASP(FlagSNType.S);
                        isEmpty = false;
                    } else {
                        pubblicazione.setSITOMINISTEROINFTRASP(FlagSNType.N);
                        isEmpty = false;
                    }
                }
                if (recordStipula.getSitoOsservatorioPC() != null
                        && !"".equals(recordStipula.getSitoOsservatorioPC())) {
                    if ("1".equals(recordStipula.getSitoOsservatorioPC())) {
                        pubblicazione.setSITOOSSERVATORIOCP(FlagSNType.S);
                        isEmpty = false;
                    } else {
                        pubblicazione.setSITOOSSERVATORIOCP(FlagSNType.N);
                        isEmpty = false;
                    }
                }
                if (StringUtils.isNotBlank(recordStipula.getIdSchedaLocale())) {
                    pubblicazione.setIDSCHEDALOCALE(recordStipula.getIdSchedaLocale());
                    isEmpty = false;
                }
                if (StringUtils.isNotBlank(recordStipula.getIdSchedaSimog())) {
                    pubblicazione.setIDSCHEDASIMOG(recordStipula.getIdSchedaSimog());
                    isEmpty = false;
                }

                if (!isEmpty) {
                    datiStipula.setPubblicazioneEsito(pubblicazione);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati di pubblicazione dell'esito", e);
        }

        LOG.debug("Execution end::setPubblicazioneStipula");
    }

    private void setDatiAvanzamenti(ObjectFactory obf, SchedaCompletaType schedaCompleta, Long codGara, Long codLotto,
                                    Long num) {

        LOG.debug("Execution start::setDatiAvanzamenti");

        try {
            List<DatiAvanzamentoEntry> datiW9AVAN = this.loaderAppaltoMapper.getDatiAvanzamento(codGara, codLotto);

            if (datiW9AVAN != null && datiW9AVAN.size() > 0) {

                boolean isEmpty = true;
                AvanzamentiType avanzamenti = obf.createAvanzamentiType();

                for (DatiAvanzamentoEntry riga : datiW9AVAN) {
                    isEmpty = true;
                    AvanzamentoType avanzamento = obf.createAvanzamentoType();
                    if (num != null && num.longValue() > 0) {
                        Long numAvanzamento = riga.getNumAvan();
                        if (num.equals(numAvanzamento)) {
                            isEmpty = valorizzaAvanzamento(isEmpty, riga, avanzamento);
                        }
                    } else {
                        isEmpty = valorizzaAvanzamento(isEmpty, riga, avanzamento);
                    }

                    if (!isEmpty) {
                        avanzamenti.getAvanzamento().add(avanzamento);
                        break;
                    }
                }

                schedaCompleta.setDatiAvanzamenti(avanzamenti);
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati degli avanzamenti", e);
        }
        LOG.debug("Execution end::setDatiAvanzamenti");
    }

    private boolean valorizzaAvanzamento(boolean isEmpty, DatiAvanzamentoEntry avan, AvanzamentoType avanzamento) {

        if (avan.getFlagPagamento() != null) {
            avanzamento.setFLAGPAGAMENTO(avan.getFlagPagamento().toString());
            isEmpty = false;
        }
        if (avan.getDataAnticipazione() != null) {
            avanzamento.setDATAANTICIPAZIONE(Utility.convertDateToCalendar(avan.getDataAnticipazione()));
            isEmpty = false;
        }

        if (avan.getImportoAnticipazione() != null) {
            avanzamento.setIMPORTOANTICIPAZIONE(new BigDecimal(this.convertiImporto(avan.getImportoAnticipazione())));
            isEmpty = false;
        }
        if (avan.getDataRaggiungimento() != null) {
            avanzamento.setDATARAGGIUNGIMENTO(Utility.convertDateToCalendar(avan.getDataRaggiungimento()));
            isEmpty = false;
        }
        if (avan.getImportoSal() != null) {
            avanzamento.setIMPORTOSAL(new BigDecimal(this.convertiImporto(avan.getImportoSal())));
            isEmpty = false;
        }
        if (avan.getDataCertificato() != null) {
            avanzamento.setDATACERTIFICATO(Utility.convertDateToCalendar(avan.getDataCertificato()));
            isEmpty = false;
        }
        if (avan.getImportoCertificato() != null) {
            avanzamento.setIMPORTOCERTIFICATO(new BigDecimal(this.convertiImporto(avan.getImportoCertificato())));
            isEmpty = false;
        }
        if (avan.getFlagRitardo() != null) {
            avanzamento.setFLAGRITARDO(FlagRitardoType.fromValue(avan.getFlagRitardo()));
            isEmpty = false;
        }
        if (avan.getNumGiorniScost() != null) {
            avanzamento.setNUMGIORNISCOST(Integer.parseInt(avan.getNumGiorniScost().toString()));
            isEmpty = false;
        }
        if (avan.getNumGiorniProroga() != null) {
            avanzamento.setNUMGIORNIPROROGA(Integer.parseInt(avan.getNumGiorniProroga().toString()));
            isEmpty = false;
        }
        if (avan.getDenomAvanzamento() != null) {
            avanzamento.setDENOMAVANZAMENTO(avan.getDenomAvanzamento());
            isEmpty = false;
        }
        if (StringUtils.isNotBlank(avan.getIdSchedaLocale())) {
            avanzamento.setIDSCHEDALOCALE(avan.getIdSchedaLocale());
            isEmpty = false;
        }
        if (StringUtils.isNotBlank(avan.getIdSchedaSimog())) {
            avanzamento.setIDSCHEDASIMOG(avan.getIdSchedaSimog());
            isEmpty = false;
        }
        return isEmpty;
    }

    private void setDatiConclusione(ObjectFactory obf, SchedaCompletaType schedaCompleta, Long codGara, Long codLotto,
                                    final Long numeroProgressivoFase) {

        LOG.debug("Execution start::setDatiConclusione");

        try {
            DatiConclusioneEntry datiConclusione = this.loaderAppaltoMapper.getDatiConclusione(codGara, codLotto,
                    numeroProgressivoFase);
            if (datiConclusione != null) {

                boolean isEmpty = true;
                ConclusioneType conclusione = obf.createConclusioneType();

                if (datiConclusione.getMotivoInterr() != null) {
                    conclusione.setIDMOTIVOINTERR(datiConclusione.getMotivoInterr().toString());
                    isEmpty = false;
                }
                if (datiConclusione.getMotivoRisol() != null) {
                    conclusione.setIDMOTIVORISOL(datiConclusione.getMotivoRisol().toString());
                    isEmpty = false;
                }
                if (datiConclusione.getDataRisol() != null) {
                    conclusione.setDATARISOLUZIONE(Utility.convertDateToCalendar(datiConclusione.getDataRisol()));
                    isEmpty = false;
                }
                if (datiConclusione.getFlagOneri() != null) {
                    conclusione.setFLAGONERI(datiConclusione.getFlagOneri());
                    isEmpty = false;
                }
                if (datiConclusione.getOneriRisoluzione() != null) {
                    conclusione.setONERIRISOLUZIONE(
                            new BigDecimal(this.convertiImporto(datiConclusione.getOneriRisoluzione())));
                    isEmpty = false;
                }
                if (datiConclusione.getFlagPolizza() != null) {
                    if ("1".equals(datiConclusione.getFlagPolizza())) {
                        conclusione.setFLAGPOLIZZA(FlagSNType.S);
                    } else {
                        conclusione.setFLAGPOLIZZA(FlagSNType.N);
                    }
                    isEmpty = false;
                }

                if (datiConclusione.getDataUltimazione() != null) {
                    conclusione.setDATAULTIMAZIONE(Utility.convertDateToCalendar(datiConclusione.getDataUltimazione()));
                    isEmpty = false;
                }
                if (datiConclusione.getNumInfortuni() != null) {
                    conclusione.setNUMINFORTUNI(Integer.parseInt(datiConclusione.getNumInfortuni().toString()));
                    isEmpty = false;
                }
                if (datiConclusione.getNumInfPerm() != null) {
                    conclusione.setNUMINFPERM(Integer.parseInt(datiConclusione.getNumInfPerm().toString()));
                    isEmpty = false;
                }
                if (datiConclusione.getNumInfortMortali() != null) {
                    conclusione.setNUMINFMORT(Integer.parseInt(datiConclusione.getNumInfortMortali().toString()));
                    isEmpty = false;
                }
                // Data verbale consegna definitiva
                if (datiConclusione.getDataVerbale() != null) {
                    conclusione
                            .setDATAVERBCONSEGNAAVVIO(Utility.convertDateToCalendar(datiConclusione.getDataVerbale()));
                    isEmpty = false;
                }
                // Termine contrattuale ultimazione
                if (datiConclusione.getTermineContrattoUltimo() != null) {
                    conclusione.setTERMINECONTRATTULTIMAZIONE(
                            Utility.convertDateToCalendar(datiConclusione.getTermineContrattoUltimo()));
                    isEmpty = false;
                }
                // Numero giorni di proroga concessi
                if (datiConclusione.getNumGiorniProroga() != null) {
                    conclusione.setNUMGIORNIPROROGA(Integer.parseInt(datiConclusione.getNumGiorniProroga().toString()));
                    isEmpty = false;
                }

                if (StringUtils.isNotBlank(datiConclusione.getIdSchedaLocale())) {
                    conclusione.setIDSCHEDALOCALE(datiConclusione.getIdSchedaLocale());
                    isEmpty = false;
                }

                if (StringUtils.isNotBlank(datiConclusione.getIdSchedaSimog())) {
                    conclusione.setIDSCHEDASIMOG(datiConclusione.getIdSchedaSimog());
                    isEmpty = false;
                }
                if (!isEmpty) {
                    schedaCompleta.setDatiConclusione(conclusione);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati di conclusione", e);
        }

        LOG.debug("Execution end::setDatiConclusione");

    }

    private void setDatiCollaudo(final ObjectFactory obf, final SchedaCompletaType schedaCompleta, final Long codGara,
                                 final Long codLotto, final Long numeroProgressivoFase) {

        LOG.debug("Execution start::setDatiCollaudo");

        try {

            DatiCollaudoEntry datiColl = this.loaderAppaltoMapper.getDatiCollaudo(codGara, codLotto,
                    numeroProgressivoFase);

            if (datiColl != null) {
                DatiCollaudoType datiCollaudo = obf.createDatiCollaudoType();
                CollaudoType collaudo = obf.createCollaudoType();

                if (datiColl.getDataRegolareEsec() != null) {
                    collaudo.setDATAREGOLAREESEC(Utility.convertDateToCalendar(datiColl.getDataRegolareEsec()));
                }
                if (datiColl.getDataCollaudoStatico() != null) {
                    collaudo.setDATACOLLAUDOSTAT(Utility.convertDateToCalendar(datiColl.getDataCollaudoStatico()));
                }

                if (datiColl.getModoCollaudo() != null) {
                    collaudo.setMODOCOLLAUDO(datiColl.getModoCollaudo().toString());
                }
                if (datiColl.getDataNominaColl() != null) {
                    collaudo.setDATANOMINACOLL(Utility.convertDateToCalendar(datiColl.getDataNominaColl()));
                }

                if (datiColl.getDataInizioOp() != null) {
                    collaudo.setDATAINIZIOOPER(Utility.convertDateToCalendar(datiColl.getDataInizioOp()));
                }
                if (datiColl.getDataCertCollaudo() != null) {
                    collaudo.setDATACERTCOLLAUDO(Utility.convertDateToCalendar(datiColl.getDataCertCollaudo()));
                }
                if (datiColl.getDataDelibera() != null) {
                    collaudo.setDATADELIBERA(Utility.convertDateToCalendar(datiColl.getDataDelibera()));
                }
                if (datiColl.getEsitoCollaudo() != null) {
                    collaudo.setESITOCOLLAUDO(FlagEsitoCollaudoType.fromValue(datiColl.getEsitoCollaudo()));
                }
                if (datiColl.getImportoFinaleLavori() != null) {
                    collaudo.setIMPFINALELAVORI(
                            new BigDecimal(this.convertiImporto(datiColl.getImportoFinaleLavori())));
                }
                if (datiColl.getImportoFinaleServizi() != null) {
                    collaudo.setIMPFINALESERVIZI(
                            new BigDecimal(this.convertiImporto(datiColl.getImportoFinaleServizi())));
                }
                if (datiColl.getImportoFinaleForniture() != null) {
                    collaudo.setIMPFINALEFORNIT(
                            new BigDecimal(this.convertiImporto(datiColl.getImportoFinaleForniture())));
                }
                if (datiColl.getImportoFinaleSicurezza() != null) {
                    collaudo.setIMPFINALESECUR(
                            new BigDecimal(this.convertiImporto(datiColl.getImportoFinaleSicurezza())));
                }
                if (datiColl.getImportoProgettazione() != null) {
                    collaudo.setIMPPROGETTAZIONE(
                            new BigDecimal(this.convertiImporto(datiColl.getImportoProgettazione())).toString());
                }
                if (datiColl.getImportoDisposizione() != null) {
                    collaudo.setIMPDISPOSIZIONE(
                            new BigDecimal(this.convertiImporto(datiColl.getImportoDisposizione())).toString());
                }
                if (datiColl.getAmmNumDef() != null) {
                    collaudo.setAMMNUMDEFINITE(Integer.parseInt(datiColl.getAmmNumDef().toString()));
                }
                if (datiColl.getAmmNumDadef() != null) {
                    collaudo.setAMMNUMDADEF(Integer.parseInt(datiColl.getAmmNumDadef().toString()));
                }
                if (datiColl.getAmmImportoRich() != null) {
                    collaudo.setAMMIMPORTORICH(new BigDecimal(this.convertiImporto(datiColl.getAmmImportoRich())));
                }
                if (datiColl.getAmmImportoDef() != null) {
                    collaudo.setAMMIMPORTODEF(new BigDecimal(this.convertiImporto(datiColl.getAmmImportoDef())));
                }
                if (datiColl.getArbNumDefinite() != null) {
                    collaudo.setARBNUMDEFINITE(Integer.parseInt(datiColl.getArbNumDefinite().toString()));
                }
                if (datiColl.getArbNumDadef() != null) {
                    collaudo.setARBNUMDADEF(Integer.parseInt(datiColl.getArbNumDadef().toString()));
                }
                if (datiColl.getArbImportoRich() != null) {
                    collaudo.setARBIMPORTORICH(new BigDecimal(this.convertiImporto(datiColl.getArbImportoRich())));
                }
                if (datiColl.getArbImportoDef() != null) {
                    collaudo.setARBIMPORTODEF(new BigDecimal(this.convertiImporto(datiColl.getArbImportoDef())));
                }
                if (datiColl.getGiuNumDefinite() != null) {
                    collaudo.setGIUNUMDEFINITE(Integer.parseInt(datiColl.getGiuNumDefinite().toString()));
                }
                if (datiColl.getGiuNumDadef() != null) {
                    collaudo.setGIUNUMDADEF(Integer.parseInt(datiColl.getGiuNumDadef().toString()));
                }
                if (datiColl.getGiuImportoRich() != null) {
                    collaudo.setGIUIMPORTORICH(new BigDecimal(this.convertiImporto(datiColl.getGiuImportoRich())));
                }
                if (datiColl.getGiuImportoDef() != null) {
                    collaudo.setGIUIMPORTODEF(new BigDecimal(this.convertiImporto(datiColl.getGiuImportoDef())));
                }
                if (datiColl.getTraNumDef() != null) {
                    collaudo.setTRANUMDEFINITE(Integer.parseInt(datiColl.getTraNumDef().toString()));
                }
                if (datiColl.getTraNumDadef() != null) {
                    collaudo.setTRANUMDADEF(Integer.parseInt(datiColl.getTraNumDadef().toString()));
                }
                if (datiColl.getTraImportoRich() != null) {
                    collaudo.setTRAIMPORTORICH(new BigDecimal(this.convertiImporto(datiColl.getTraImportoRich())));
                }
                if (datiColl.getTraImportoDef() != null) {
                    collaudo.setTRAIMPORTODEF(new BigDecimal(this.convertiImporto(datiColl.getTraImportoDef())));
                }

                if (datiColl.getLavoriEstesi() != null) {
                    if ("1".equals(datiColl.getLavoriEstesi())) {
                        collaudo.setLAVORIESTESI(FlagSNType.S);
                    } else {
                        collaudo.setLAVORIESTESI(FlagSNType.N);
                    }
                }
                if (StringUtils.isNotBlank(datiColl.getIdSchedaLocale())) {
                    collaudo.setIDSCHEDALOCALE(datiColl.getIdSchedaLocale());
                }
                if (StringUtils.isNotBlank(datiColl.getIdSchedaSimog())) {
                    collaudo.setIDSCHEDASIMOG(datiColl.getIdSchedaSimog());
                }

                // Incaricati del collaudo
                List<IncaricatoType> listaIncaricati = setIncaricati(obf, codGara, codLotto,
                        ETipologiaIncaricati.W9INCA_COLLAUDO, numeroProgressivoFase);
                if (listaIncaricati != null && listaIncaricati.size() > 0) {
                    datiCollaudo.getIncaricati().addAll(listaIncaricati);
                }

                datiCollaudo.setCollaudo(collaudo);
                schedaCompleta.setDatiCollaudo(datiCollaudo);
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati del collaudo", e);
        }
        LOG.debug("Execution end::setDatiCollaudo");
    }

    private void setDatiRitardo(final ObjectFactory obf, final SchedaCompletaType schedaCompleta, final Long codGara,
                                final Long codLotto, final Long numeroProgressivoFase) {

        LOG.debug("Execution start::setDatiRitardo");

        try {
            List<DatiRitardoEntry> datiW9RITA = this.loaderAppaltoMapper.getDatiRitardo(codGara, codLotto,
                    numeroProgressivoFase);

            if (datiW9RITA != null && datiW9RITA.size() > 0) {
                boolean isEmpty = true;
                RitardiType ritardi = obf.createRitardiType();

                for (DatiRitardoEntry rita : datiW9RITA) {
                    RitardoType ritardo = obf.createRitardoType();

                    if (rita.getDataTermine() != null) {
                        ritardo.setDATATERMINE(Utility.convertDateToXMLString(rita.getDataTermine()));
                        isEmpty = false;
                    }
                    if (rita.getDataConsegna() != null) {
                        ritardo.setDATACONSEGNA(Utility.convertDateToCalendar(rita.getDataConsegna()));
                        isEmpty = false;
                    }
                    if (rita.getTipoComun() != null) {
                        ritardo.setTIPOCOMUN(FlagTCType.fromValue(rita.getTipoComun()));
                        isEmpty = false;
                    }
                    if (rita.getDurataSosp() != null) {
                        ritardo.setDURATASOSP(Integer.parseInt(rita.getDurataSosp().toString()));
                        isEmpty = false;
                    }
                    if (rita.getMotivoSosp() != null) {
                        ritardo.setMOTIVOSOSP(rita.getMotivoSosp());
                        isEmpty = false;
                    }
                    if (rita.getDataIstRecesso() != null) {
                        ritardo.setDATAISTRECESSO(Utility.convertDateToCalendar(rita.getDataIstRecesso()));
                        isEmpty = false;
                    }
                    if (rita.getFlagAccolta() != null) {
                        if ("1".equals(rita.getFlagAccolta())) {
                            ritardo.setFLAGACCOLTA(FlagSNType.S);
                        } else {
                            ritardo.setFLAGACCOLTA(FlagSNType.N);
                        }
                        isEmpty = false;
                    }
                    if (rita.getFlagTardiva() != null) {
                        if ("1".equals(rita.getFlagTardiva())) {
                            ritardo.setFLAGTARDIVA(FlagSNType.S);
                        } else {
                            ritardo.setFLAGTARDIVA(FlagSNType.N);
                        }
                        isEmpty = false;
                    }
                    if (rita.getFlagRipresa() != null) {
                        if ("1".equals(rita.getFlagRipresa())) {
                            ritardo.setFLAGRIPRESA(FlagSNType.S);
                        } else {
                            ritardo.setFLAGRIPRESA(FlagSNType.N);
                        }
                        isEmpty = false;
                    }
                    if (rita.getFlagRiserva() != null) {
                        if ("1".equals(rita.getFlagRiserva())) {
                            ritardo.setFLAGRISERVA(FlagSNType.S.toString());
                        } else {
                            ritardo.setFLAGRISERVA(FlagSNType.N.toString());
                        }
                        isEmpty = false;
                    }
                    if (rita.getImportoSpese() != null) {
                        ritardo.setIMPORTOSPESE(new BigDecimal(this.convertiImporto(rita.getImportoSpese())));
                        isEmpty = false;
                    }
                    if (rita.getImportoOneri() != null) {
                        ritardo.setIMPORTOONERI(new BigDecimal(this.convertiImporto(rita.getImportoOneri())));
                        isEmpty = false;
                    }
                    if (StringUtils.isNotBlank(rita.getIdSchedaLocale())) {
                        ritardo.setIDSCHEDALOCALE(rita.getIdSchedaLocale());
                        isEmpty = false;
                    }
                    if (StringUtils.isNotBlank(rita.getIdSchedaSimog())) {
                        ritardo.setIDSCHEDASIMOG(rita.getIdSchedaSimog());
                        isEmpty = false;
                    }
                    ritardi.getRitardo().add(ritardo);
                }
                if (!isEmpty) {
                    schedaCompleta.setDatiRitardi(ritardi);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati relativi all'istanza di recesso", e);
        }
        LOG.debug("Execution end::setDatiRitardo");
    }

    private void setDatiAccordi(final ObjectFactory obf, final SchedaCompletaType schedaCompleta, final Long codGara,
                                final Long codLotto, final Long numProgressivoFase) {

        LOG.debug("Execution start::setDatiAccordi");

        try {

            List<DatiAccordoEntry> listaAccordi = this.loaderAppaltoMapper.getListaDatiAccordo(codGara, codLotto);

            if (listaAccordi != null && listaAccordi.size() > 0) {
                AccordiBonariType accordiBonari = obf.createAccordiBonariType();
                boolean isEmpty = true;

                boolean accordoValorizzato = false;

                for (int i = 0; i < listaAccordi.size() && !accordoValorizzato; i++) {

                    DatiAccordoEntry accordoEntry = listaAccordi.get(i);

                    if (numProgressivoFase != null && numProgressivoFase.longValue() > 0) {
                        Long numAccordo = accordoEntry.getNumAcco();
                        if (numProgressivoFase.equals(numAccordo)) {
                            AccordoBonarioType accordoBonario = obf.createAccordoBonarioType();
                            isEmpty = valorizzaAccordo(isEmpty, accordoEntry, accordoBonario);
                            accordoValorizzato = true;
                            accordiBonari.getAccordoBonario().add(accordoBonario);
                        }
                    } else {
                        AccordoBonarioType accordoBonario = obf.createAccordoBonarioType();
                        isEmpty = valorizzaAccordo(isEmpty, accordoEntry, accordoBonario);
                        accordiBonari.getAccordoBonario().add(accordoBonario);
                    }

                }

                if (!isEmpty) {
                    schedaCompleta.setDatiAccordi(accordiBonari);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati dei dati degli accordi", e);
        }

        LOG.debug("Execution end::setDatiAccordi");
    }

    private boolean valorizzaAccordo(boolean isEmpty, final DatiAccordoEntry accordoEntry,
                                     AccordoBonarioType accordoBonario) {

        if (accordoEntry.getDataAccordo() != null) {
            accordoBonario.setDATAACCORDO(Utility.convertDateToCalendar(accordoEntry.getDataAccordo()));
            isEmpty = false;
        }

        if (accordoEntry.getOneriDerivanti() != null && !"".equals(accordoEntry.getOneriDerivanti().toString())) {
            accordoBonario
                    .setONERIDERIVANTI(new BigDecimal(convertiImporto(accordoEntry.getOneriDerivanti().doubleValue())));
            isEmpty = false;
        }

        if (accordoEntry.getNumRiserve() != null && !"".equals(accordoEntry.getNumRiserve().toString())) {
            accordoBonario.setNUMRISERVE(Integer.parseInt(accordoEntry.getNumRiserve().toString()));
            isEmpty = false;
        }

        if (StringUtils.isNotBlank(accordoEntry.getIdSchedaLocale())) {
            accordoBonario.setIDSCHEDALOCALE(accordoEntry.getIdSchedaLocale());
            isEmpty = false;
        }

        if (StringUtils.isNotBlank(accordoEntry.getIdSchedaSimog())) {
            accordoBonario.setIDSCHEDASIMOG(accordoEntry.getIdSchedaSimog());
            isEmpty = false;
        }

        return isEmpty;
    }

    private void setDatiSospensioni(final ObjectFactory obf, final SchedaCompletaType schedaCompleta,
                                    final Long codGara, final Long codLotto, final Long numProgressivoFase) {

        LOG.debug("Execution start::setDatiSospensioni");

        try {

            List<DatiSospensioneEntry> listaSospensioni = this.loaderAppaltoMapper.getListaDatiSospensione(codGara,
                    codLotto);

            if (listaSospensioni != null && listaSospensioni.size() > 0) {

                SospensioniType sospensioni = obf.createSospensioniType();
                boolean isEmpty = true;

                boolean sospensioneValorizzata = false;

                for (int i = 0; i < listaSospensioni.size() && !sospensioneValorizzata; i++) {

                    DatiSospensioneEntry sospensioneEntry = listaSospensioni.get(i);

                    if (numProgressivoFase != null && numProgressivoFase.longValue() > 0) {
                        Long numSospensione = sospensioneEntry.getNumSosp();
                        if (numProgressivoFase.equals(numSospensione)) {
                            SospensioneType sospensione = obf.createSospensioneType();
                            isEmpty = valorizzaSospensione(isEmpty, sospensioneEntry, sospensione);
                            sospensioneValorizzata = true;
                            sospensioni.getSospensione().add(sospensione);
                        }
                    } else {
                        SospensioneType sospensione = obf.createSospensioneType();
                        isEmpty = valorizzaSospensione(isEmpty, sospensioneEntry, sospensione);
                        sospensioni.getSospensione().add(sospensione);
                    }
                }

                if (!isEmpty) {
                    schedaCompleta.setDatiSospensioni(sospensioni);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati delle sospensioni", e);
        }

        LOG.debug("Execution end::setDatiSospensioni");
    }

    private boolean valorizzaSospensione(boolean isEmpty, final DatiSospensioneEntry sospensioneEntry,
                                         SospensioneType sospensione) {

        if (sospensioneEntry.getDataVerbSosp() != null) {
            sospensione.setDATAVERBSOSP(Utility.convertDateToCalendar(sospensioneEntry.getDataVerbSosp()));
            isEmpty = false;
        }

        if (sospensioneEntry.getDataVerbRipr() != null) {
            sospensione.setDATAVERBRIPR(Utility.convertDateToCalendar(sospensioneEntry.getDataVerbRipr()));
            isEmpty = false;
        }

        if (sospensioneEntry.getIdMotivoSosp() != null && !"".equals(sospensioneEntry.getIdMotivoSosp().toString())) {
            sospensione.setIDMOTIVOSOSP(sospensioneEntry.getIdMotivoSosp().toString());
            isEmpty = false;
        }

        if (sospensioneEntry.getFlagSuperoTempo() != null && !"".equals(sospensioneEntry.getFlagSuperoTempo())) {
            if ("1".equals(sospensioneEntry.getFlagSuperoTempo())) {
                sospensione.setFLAGSUPEROTEMPO(FlagSNType.S);
            } else {
                sospensione.setFLAGSUPEROTEMPO(FlagSNType.N);
            }
            isEmpty = false;
        }

        if (sospensioneEntry.getFlagRiserve() != null && !"".equals(sospensioneEntry.getFlagRiserve())) {
            if ("1".equals(sospensioneEntry.getFlagRiserve())) {
                sospensione.setFLAGRISERVE(FlagSNType.S);
            } else {
                sospensione.setFLAGRISERVE(FlagSNType.N);
            }
            isEmpty = false;
        }

        if (sospensioneEntry.getFlagVerbale() != null && !"".equals(sospensioneEntry.getFlagVerbale())) {
            if ("1".equals(sospensioneEntry.getFlagVerbale())) {
                sospensione.setFLAGVERBALE(FlagSNType.S);
            } else {
                sospensione.setFLAGVERBALE(FlagSNType.N);
            }
            isEmpty = false;
        }

        if (StringUtils.isNotBlank(sospensioneEntry.getIdSchedaLocale())) {
            sospensione.setIDSCHEDALOCALE(sospensioneEntry.getIdSchedaLocale());
            isEmpty = false;
        }

        if (StringUtils.isNotBlank(sospensioneEntry.getIdSchedaSimog())) {
            sospensione.setIDSCHEDASIMOG(sospensioneEntry.getIdSchedaSimog());
            isEmpty = false;
        }

        return isEmpty;
    }

    private void setDatiVarianti(final ObjectFactory obf, final SchedaCompletaType schedaCompleta, final Long codGara,
                                 final Long codLotto, final Long numProgressivoFase) {

        LOG.debug("Execution start::setDatiVarianti");

        try {

            List<DatiVarianteEntry> listaVarianti = this.loaderAppaltoMapper.getListaDatiVariante(codGara, codLotto);

            if (listaVarianti != null && listaVarianti.size() > 0) {

                VariantiType varianti = obf.createVariantiType();

                for (DatiVarianteEntry varianteEntry : listaVarianti) {
                    Long numP = null;

                    // Prende i parametri per la seconda selezione
                    if (varianteEntry.getNumVari() != null && !"".equals(varianteEntry.getNumVari().toString())) {
                        numP = Long.valueOf(varianteEntry.getNumVari().toString());
                    }

                    if (numProgressivoFase != null && numProgressivoFase.intValue() != 0) {
                        Long numeroVariante = varianteEntry.getNumVari();

                        if (numProgressivoFase.equals(numeroVariante)) {
                            numP = numProgressivoFase;

                            VarianteType variante = obf.createVarianteType();
                            RecVarianteType recVariante = obf.createRecVarianteType();

                            valorizzaVariante(varianteEntry, recVariante);
                            // Gestione dei motivi
                            valorizzaMotivazioniVariante(obf, codGara, codLotto, numP, variante);

                            variante.setVariante(recVariante);
                            varianti.getVariante().add(variante);
                        }
                    } else {
                        numP = varianteEntry.getNumVari();

                        VarianteType variante = obf.createVarianteType();
                        RecVarianteType recVariante = obf.createRecVarianteType();

                        valorizzaVariante(varianteEntry, recVariante);
                        // Gestione dei motivi
                        valorizzaMotivazioniVariante(obf, codGara, codLotto, numP, variante);

                        variante.setVariante(recVariante);
                        varianti.getVariante().add(variante);
                    }
                }

                schedaCompleta.setDatiVarianti(varianti);
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati delle varianti", e);
        }

        LOG.debug("Execution end::setDatiVarianti");
    }

    private void valorizzaVariante(DatiVarianteEntry varianteEntry, RecVarianteType recVariante) {

        if (varianteEntry.getDataVerbAppr() != null) {
            recVariante.setDATAVERBAPPR(Utility.convertDateToCalendar(varianteEntry.getDataVerbAppr()));
        }

        if (varianteEntry.getAltreMotivazioni() != null && !"".equals(varianteEntry.getAltreMotivazioni())) {
            recVariante.setALTREMOTIVAZIONI(varianteEntry.getAltreMotivazioni());
        }

        if (varianteEntry.getImpRidetLavori() != null && !"".equals(varianteEntry.getImpRidetLavori().toString())) {
            recVariante.setIMPRIDETLAVORI(
                    new BigDecimal(this.convertiImporto(varianteEntry.getImpRidetLavori().doubleValue())));
        }

        if (varianteEntry.getImpRidetServizi() != null && !"".equals(varianteEntry.getImpRidetServizi().toString())) {
            recVariante.setIMPRIDETSERVIZI(
                    new BigDecimal(this.convertiImporto(varianteEntry.getImpRidetServizi().doubleValue())));
        }

        if (varianteEntry.getImpRidetFornit() != null && !"".equals(varianteEntry.getImpRidetFornit().toString())) {
            recVariante.setIMPRIDETFORNIT(
                    new BigDecimal(this.convertiImporto(varianteEntry.getImpRidetFornit().doubleValue())));
        }

        if (varianteEntry.getImpSicurezza() != null && !"".equals(varianteEntry.getImpSicurezza().toString())) {
            recVariante.setIMPSICUREZZA(
                    new BigDecimal(this.convertiImporto(varianteEntry.getImpSicurezza().doubleValue())));
        }

        if (varianteEntry.getImpProgettazione() != null && !"".equals(varianteEntry.getImpProgettazione().toString())) {
            recVariante.setIMPPROGETTAZIONE(
                    new BigDecimal(this.convertiImporto(varianteEntry.getImpProgettazione().doubleValue())).toString());
        }

        if (varianteEntry.getImpDisposizione() != null && !"".equals(varianteEntry.getImpDisposizione().toString())) {
            recVariante.setIMPDISPOSIZIONE(
                    new BigDecimal(this.convertiImporto(varianteEntry.getImpDisposizione().doubleValue())).toString());
        }

        if (varianteEntry.getDataAttoAggiuntivo() != null) {
            recVariante.setDATAATTOAGGIUNTIVO(Utility.convertDateToCalendar(varianteEntry.getDataAttoAggiuntivo()));
        }

        if (varianteEntry.getNumGiorniProroga() != null && !"".equals(varianteEntry.getNumGiorniProroga().toString())) {
            recVariante.setNUMGIORNIPROROGA(Integer.parseInt(varianteEntry.getNumGiorniProroga().toString()));
        }

        if (varianteEntry.getImpNonAssog() != null && !"".equals(varianteEntry.getImpNonAssog().toString())) {
            recVariante.setULTERIORISOMME(
                    new BigDecimal(this.convertiImporto(varianteEntry.getImpNonAssog().doubleValue())));
        }

        if (varianteEntry.getCigNuovaProcedura() != null && !"".equals(varianteEntry.getCigNuovaProcedura())) {
            recVariante.setCIGPROCEDURA(varianteEntry.getCigNuovaProcedura());
        }

        if (StringUtils.isNotBlank(varianteEntry.getIdSchedaLocale())) {
            recVariante.setIDSCHEDALOCALE(varianteEntry.getIdSchedaLocale());
        }

        if (StringUtils.isNotBlank(varianteEntry.getIdSchedaSimog())) {
            recVariante.setIDSCHEDASIMOG(varianteEntry.getIdSchedaSimog());
        }
        
        if (StringUtils.isNotBlank(varianteEntry.getUrlVariantiCo())) {
			recVariante.setLINKVARIANTI(varianteEntry.getUrlVariantiCo());
		}
		
		if (varianteEntry.getMotivoRevPrezzi() != null && !"".equals(varianteEntry.getMotivoRevPrezzi().toString())) {
			recVariante.setIDMOTIVOREVPREZZI(varianteEntry.getMotivoRevPrezzi().toString());
		}
    }

    private void valorizzaMotivazioniVariante(final ObjectFactory obf, final Long codGara, final Long codLotto,
                                              final Long numP, VarianteType variante) {

        List<Long> listaMotivazioni = this.loaderAppaltoMapper.getListaMotivazioniFaseVariante(codGara, codLotto, numP);

        if (listaMotivazioni != null && listaMotivazioni.size() > 0) {

            for (Long idMotivazione : listaMotivazioni) {
                RecMotivoVarType recMotivo = obf.createRecMotivoVarType();

                if (idMotivazione != null && !"".equals(idMotivazione.toString())) {
                    recMotivo.setIDMOTIVOVAR(idMotivazione.toString());
                }

                variante.getMotivi().add(recMotivo);
            }
        }
    }

    private void setDatiSubappalti(final ObjectFactory obf, final SchedaCompletaType schedaCompleta, final Long codGara,
                                   final Long codLotto, final Long numProgressivoFase) {

        LOG.debug("Execution start::setDatiSubappalti");

        try {

            List<DatiSubappaltoEntry> listaSubappalti = this.loaderAppaltoMapper.getListaDatiSubappalto(codGara,
                    codLotto);

            if (listaSubappalti != null && listaSubappalti.size() > 0) {
                SubappaltiType subappalti = obf.createSubappaltiType();

                boolean isEmpty = true;
                boolean subappaltoValorizzato = false;

                for (int i = 0; i < listaSubappalti.size() && !subappaltoValorizzato; i++) {

                    DatiSubappaltoEntry subappaltoEntry = listaSubappalti.get(i);

                    if (numProgressivoFase != null && numProgressivoFase.longValue() > 0) {
                        Long numSubappalto = subappaltoEntry.getNumSuba();
                        if (numProgressivoFase.equals(numSubappalto)) {
                            SubappaltoType subappalto = obf.createSubappaltoType();
                            isEmpty = valorizzaSubappalto(isEmpty, subappaltoEntry, subappalto);
                            subappaltoValorizzato = true;
                            subappalti.getSubappalto().add(subappalto);
                        }
                    } else {
                        SubappaltoType subappalto = obf.createSubappaltoType();
                        isEmpty = valorizzaSubappalto(isEmpty, subappaltoEntry, subappalto);
                        subappalti.getSubappalto().add(subappalto);
                    }
                }

                if (!isEmpty) {
                    schedaCompleta.setDatiSubappalti(subappalti);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati dei subappalti", e);
        }

        LOG.debug("Execution end::setDatiSubappalti");
    }

    private boolean valorizzaSubappalto(boolean isEmpty, final DatiSubappaltoEntry subappaltoEntry,
                                        SubappaltoType subappalto) {

        if (subappaltoEntry.getCfDitta() != null && !"".equals(subappaltoEntry.getCfDitta())) {
            subappalto.setCFDITTA(subappaltoEntry.getCfDitta());
            isEmpty = false;
        } else {
            // Se il codice fiscale non e' valorizzato si deve considerare la partita IVA
            if (subappaltoEntry.getpIvImp() != null && !"".equals(subappaltoEntry.getpIvImp())) {
                subappalto.setCFDITTA(subappaltoEntry.getpIvImp());
                isEmpty = false;
            }
        }
        
        if(subappaltoEntry.getNazione() == null || subappaltoEntry.getNazione() == 1L) {
			subappalto.setFLAGDITTASUBESTERA(FlagSNType.N);
			isEmpty = false;
		} else {
			subappalto.setFLAGDITTASUBESTERA(FlagSNType.S);
			isEmpty = false;
		}	
		

        if (subappaltoEntry.getDataAutorizzazione() != null) {
            subappalto.setDATAAUTORIZZAZIONE(Utility.convertDateToCalendar(subappaltoEntry.getDataAutorizzazione()));
            isEmpty = false;
        }

        if (subappaltoEntry.getOggettoSubappalto() != null && !"".equals(subappaltoEntry.getOggettoSubappalto())) {
            subappalto.setOGGETTOSUBAPPALTO(subappaltoEntry.getOggettoSubappalto());
            isEmpty = false;
        }

        if (subappaltoEntry.getImportoPresunto() != null
                && !"".equals(subappaltoEntry.getImportoPresunto().toString())) {
            subappalto.setIMPORTOPRESUNTO(
                    new BigDecimal(this.convertiImporto(subappaltoEntry.getImportoPresunto().doubleValue())));
            isEmpty = false;
        }

        if (subappaltoEntry.getImportoEffettivo() != null
                && !"".equals(subappaltoEntry.getImportoEffettivo().toString())) {
            subappalto.setIMPORTOEFFETTIVO(
                    new BigDecimal(this.convertiImporto(subappaltoEntry.getImportoEffettivo().doubleValue())));
            isEmpty = false;
        }

        if (subappaltoEntry.getIdCategoria() != null && !"".equals(subappaltoEntry.getIdCategoria())) {
            subappalto.setIDCATEGORIA(subappaltoEntry.getIdCategoria());
            isEmpty = false;
        }

        if (subappaltoEntry.getIdCpv() != null && !"".equals(subappaltoEntry.getIdCpv())) {
            subappalto.setIDCPV(subappaltoEntry.getIdCpv());
            isEmpty = false;
        }

        // Ditta aggiudicataria
        if (subappaltoEntry.getCfDitta2() != null && !"".equals(subappaltoEntry.getCfDitta2())) {
            subappalto.setCODICEFISCALEAGGIUDICATARIO(subappaltoEntry.getCfDitta2());
            isEmpty = false;
        } else {
            // Se il codice fiscale non e' valorizzato si deve considerare la partita IVA
            if (subappaltoEntry.getpIvImp2() != null && !"".equals(subappaltoEntry.getpIvImp2())) {
                subappalto.setCODICEFISCALEAGGIUDICATARIO(subappaltoEntry.getpIvImp2());
                isEmpty = false;
            }
        }

        if (StringUtils.isNotBlank(subappaltoEntry.getIdSchedaLocale())) {
            subappalto.setIDSCHEDALOCALE(subappaltoEntry.getIdSchedaLocale());
            isEmpty = false;
        }

        if (StringUtils.isNotBlank(subappaltoEntry.getIdSchedaSimog())) {
            subappalto.setIDSCHEDASIMOG(subappaltoEntry.getIdSchedaSimog());
            isEmpty = false;
        }
        return isEmpty;
    }

    /**
     * Il metodo, inserisce nella tabella W9XML la scheda inviata a loaderAppalto
     *
     * @param codGara            codice gara
     * @param codLotto           codice lotto
     * @param idFlusso           da associare a W9XML
     * @param xml                xml inviato
     * @param faseEsecuzione     codice fase esecuzione
     * @param numProgressivoFase progressivo fase
     * @return num_xml -1 se l'inserimento non va a buon fine, >0 altrimenti
     */
    // overloading del metodo, nel primo invio non compare il numxml
    private Long insertW9XML(final Long codGara, final Long codLotto, final Long idFlusso, final String xml,
                             final Long faseEsecuzione, final Long numProgressivoFase) {
        Long numXml = null;
        try {
            numXml = this.loaderAppaltoMapper.getNumXml(codGara, codLotto, faseEsecuzione, numProgressivoFase);
            return insertW9XML(codGara, codLotto, numXml, idFlusso, xml, faseEsecuzione, numProgressivoFase);
        } catch (Exception e) {
            throw new RuntimeException("SIMOG-loaderAppalto: (insertW9XML)", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private Long insertW9XML(final Long codGara, final Long codLotto, final Long numXml, final Long idFlusso,
                             final String xml, final Long faseEsecuzione, final Long numProgressivoFase) {
        LOG.debug("Execution start::insertW9XML");

        Long generatedNumXml = Long.valueOf(-1L);

        try {
            if (idFlusso != null) {
                // Aggiorno la W9FLUSSI solo nella configurazione Vigilanza, ovvero quando
                // associata la chiamata del LoaderAppalto viene creato anche un flusso
                this.loaderAppaltoMapper.updateXmlFlusso(idFlusso, xml);
            }

            // test se il valore del w9xml e' già presente sono nel caso di un
            // update/reinvio
            if (numXml != null) {
                generatedNumXml = numXml;
                W9XmlForm form = new W9XmlForm();
                form.setCodGara(codGara);
                form.setCodLotto(codLotto);
                form.setIdFlusso(idFlusso);
                form.setNumXml(generatedNumXml);
                form.setXml(xml);
                Date now = new Date();
                form.setDataInvio(now);
                this.loaderAppaltoMapper.updateW9Xml(form);
            } else {
                // altrimenti mi trovo nel caso di un primo invio ed il numerico chiave devo
                // calcolarlo
                Long conteggio = this.loaderAppaltoMapper.selectW9Xml(codGara, codLotto);
                if (conteggio != null && conteggio.longValue() > 0) {
                    conteggio++;
                } else {
                    conteggio = Long.valueOf(1L);
                }
                generatedNumXml = conteggio;
                W9XmlForm form = new W9XmlForm();
                form.setCodGara(codGara);
                form.setCodLotto(codLotto);
                form.setNumXml(generatedNumXml);
                Date now = new Date();
                form.setDataExport(now);
                form.setDataInvio(now);
                form.setXml(xml);
                form.setIdFlusso(idFlusso);
                form.setFaseEsecuzione(faseEsecuzione);
                form.setNumProgressivoFase(numProgressivoFase);
                this.loaderAppaltoMapper.insertW9Xml(form);
            }

        } catch (Exception e) {
            throw new RuntimeException("SIMOG-loaderAppalto: (insertW9XML)", e);
        }

        LOG.debug("Execution end::insertW9XML");

        return generatedNumXml;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private String readResponseLoaderAppalto(final Long codGara, final Long codLotto, final Long faseEsecuzione,
                                             final Long numProgressivoFase, final Long numXml, boolean eliminaScheda,
                                             LoaderAppaltoResponse responseLoaderAppalto) {

        LOG.debug("Execution start::readResponseLoaderAppalto");

        String codCUI = null;
        String feedBackAnalisi = "2";

        try {

            if (responseLoaderAppalto.getReturn() != null) {
                if (responseLoaderAppalto.getReturn().getFeedBack() != null) {

                    ResponseLoaderAppalto.FeedBack feedback = responseLoaderAppalto.getReturn().getFeedBack();
                    Calendar dataElaborazione = new GregorianCalendar();
                    Long numElaborate = Long.valueOf(0L);
                    Long numErrore = Long.valueOf(0L);
                    Long numWarning = Long.valueOf(0L);
                    Long numCaricate = Long.valueOf(0L);

                    if (feedback.getInfoFlusso() != null) {
                        dataElaborazione = feedback.getInfoFlusso().getDATAELABORAZIONE().toGregorianCalendar();
                        numElaborate = Long.valueOf(feedback.getInfoFlusso().getNUMELABORATE());
                        numErrore = Long.valueOf(feedback.getInfoFlusso().getNUMERRORE());
                        numWarning = Long.valueOf(feedback.getInfoFlusso().getNUMWARNING());
                        numCaricate = Long.valueOf(feedback.getInfoFlusso().getNUMCARICATE());

                        Long contatoreAnomalie = Long.valueOf(0L);
                        for (int i = 0; i < feedback.getAnomalieSchede().size(); i++) {
                            for (int j = 0; j < feedback.getAnomalieSchede().get(i).getAnomalia().size(); j++) {
                                if (feedback.getAnomalieSchede().get(i).getAnomalia().get(j).getLIVELLO().toString()
                                        .equals("ERRORE")) {
                                    contatoreAnomalie++;
                                }
                            }
                        }
                        if (contatoreAnomalie > 0L) {
                            numErrore = Long.valueOf(1L);
                        } else {
                            feedBackAnalisi = "1";
                        }

                    } else {
                        // Se infoFlusso non e' valorizzato, allora si e' verificato un errore
                        // legato all'utente che ha fatto accesso ai servizi SIMOG e all'indice
                        // di collaborazione usato nella richiesta per il Loader Appalto.
                        numErrore = Long.valueOf(1L);
                    }

                    W9XmlAppaltoResponseForm form = new W9XmlAppaltoResponseForm();
                    form.setCodGara(codGara);
                    form.setCodLotto(codLotto);
                    form.setNumXml(numXml);
                    Date now = new Date();
                    form.setDataFeedback(now);
                    form.setDataElaborazione(dataElaborazione.getTime());
                    form.setNumElaborate(numElaborate);
                    form.setNumErrore(numErrore);
                    form.setNumWarning(numWarning);
                    form.setNumCaricate(numCaricate);
                    form.setFeedbackAnalisi(feedBackAnalisi);

                    this.loaderAppaltoMapper.updateW9XMLAppaltoResponse(form);

                    // Cancellazione della tabella W9XMLANOM
                    this.loaderAppaltoMapper.deleteW9XmlAnom(codGara, codLotto, numXml);

                    List<AnomalieSchede> anomalieSchede = feedback.getAnomalieSchede();

                    int index = 1;
                    for (int j = 0; j < anomalieSchede.size(); j++) {
                        if (StringUtils.isEmpty(codCUI) && anomalieSchede.get(j).getCUI() != null
                                && StringUtils.isNotEmpty(anomalieSchede.get(j).getCUI())) {
                            codCUI = anomalieSchede.get(j).getCUI();
                        }

                        List<AnomaliaType> anomalie = anomalieSchede.get(j).getAnomalia();

                        String id_scheda_locale = null;
                        String id_scheda_simog = null;
                        String scheda = null;

                        if (anomalie.size() > 0) {
                            // Ciclo su tutte le anomalie presenti nell'oggetto anomalieSchede
                            for (AnomaliaType anomalia : anomalie) {
                                id_scheda_locale = null;
                                id_scheda_simog = null;
                                scheda = null;

                                String codice = null;
                                if (anomalia.getCODICE() != null) {
                                    codice = anomalia.getCODICE();
                                }
                                String descrizione = anomalia.getDESCRIZIONE();
                                String livello = anomalia.getLIVELLO().toString();
                                Long elemento = Long.valueOf(anomalia.getELEMENTO());
                                Long progressivo = null;
                                if (anomalia.getPROGRESSIVO() != null) {
                                    progressivo = Long.valueOf(anomalia.getPROGRESSIVO());
                                }
                                String campo_xml = null;
                                if (anomalia.getCAMPOXML() != null) {
                                    campo_xml = anomalia.getCAMPOXML();
                                }
                                if (anomalia.getIDSCHEDASIMOG() != null) {
                                    id_scheda_simog = anomalia.getIDSCHEDASIMOG();
                                }
                                if (anomalia.getIDSCHEDALOCALE() != null) {
                                    id_scheda_locale = anomalia.getIDSCHEDALOCALE();
                                }

                                if (anomalia.getSCHEDA() != null) {
                                    scheda = anomalia.getSCHEDA().toString();
                                }

                                if (!StringUtils.equals(SIMOGWS_WSSMANAGER_NULL_15, codice)) {

                                    W9XmlAnomForm formAnom = new W9XmlAnomForm();
                                    formAnom.setCodGara(codGara);
                                    formAnom.setCodLotto(codLotto);
                                    formAnom.setNumXml(numXml);
                                    formAnom.setNum(Long.valueOf(index));
                                    formAnom.setCodice(codice);
                                    formAnom.setDescrizione(StringUtils.substring(descrizione, 0, 1950));
                                    formAnom.setLivello(livello);
                                    formAnom.setElemento(elemento);
                                    formAnom.setScheda(scheda);
                                    formAnom.setProgressivo(progressivo);
                                    formAnom.setCampoXml(campo_xml);
                                    formAnom.setIdSchedaLocale(id_scheda_locale);
                                    formAnom.setIdSchedaSimog(id_scheda_simog);

                                    this.loaderAppaltoMapper.insertW9XmlAnom(formAnom);
                                }

                                aggiornaIdSchedaLocaleIdSchedaSimog(codGara, codLotto, faseEsecuzione,
                                        numProgressivoFase, id_scheda_locale, id_scheda_simog, scheda);

                                index++;
                            }
                        }

                        id_scheda_locale = null;
                        id_scheda_simog = null;
                        scheda = null;

                        if (!eliminaScheda && anomalieSchede.get(j).getIdScheda() != null
                                && anomalieSchede.get(j).getIdScheda().size() > 0) {
                            List<RecIdSchedaInsType> idSchedaArray = feedback.getAnomalieSchede().get(j).getIdScheda();
                            RecIdSchedaInsType idSchedaIns = idSchedaArray.get(0);
                            if (idSchedaIns != null) {
                                scheda = idSchedaIns.getSCHEDA().toString();
                                id_scheda_simog = idSchedaIns.getIDSCHEDASIMOG();
                                if (idSchedaIns.getIDSCHEDALOCALE() != null) {
                                    id_scheda_locale = idSchedaIns.getIDSCHEDALOCALE();
                                }
                            }

                            aggiornaIdSchedaLocaleIdSchedaSimog(codGara, codLotto, faseEsecuzione, numProgressivoFase,
                                    id_scheda_locale, id_scheda_simog, scheda);

                        } else {
                            if (numErrore == 0 && eliminaScheda) {
                                List<RecIdSchedaInsType> arrayRecIdSchedaIns = anomalieSchede.get(j).getIdScheda();
                                if (arrayRecIdSchedaIns.size() > 0) {

                                    RecIdSchedaInsType idSchedaIns = null;
                                    if (arrayRecIdSchedaIns.size() >= 1) {
                                        for (int u = 0; u < arrayRecIdSchedaIns.size() && idSchedaIns != null; u++) {
                                            if (arrayRecIdSchedaIns.get(u) != null) {
                                                idSchedaIns = arrayRecIdSchedaIns.get(u);
                                            }
                                        }
                                    }

                                    if (idSchedaIns != null) {
                                        if (idSchedaIns.getSCHEDA() != null) {
                                            scheda = idSchedaIns.getSCHEDA().toString();
                                        }

                                        id_scheda_simog = idSchedaIns.getIDSCHEDASIMOG();
                                        if (idSchedaIns.getIDSCHEDALOCALE() != null) {
                                            id_scheda_locale = idSchedaIns.getIDSCHEDALOCALE();
                                        }
                                    }

                                    W9XmlAnomForm formAnom = new W9XmlAnomForm();
                                    formAnom.setCodGara(codGara);
                                    formAnom.setCodLotto(codLotto);
                                    formAnom.setNumXml(numXml);
                                    formAnom.setNum(Long.valueOf(index));
                                    formAnom.setCodice("ELIMINAZIONE");
                                    formAnom.setDescrizione("Cancellazione fase avvenuta con successo");
                                    formAnom.setLivello("AVVISO");
                                    formAnom.setElemento(null);
                                    formAnom.setScheda(scheda);
                                    formAnom.setProgressivo(null);
                                    formAnom.setCampoXml(null);
                                    formAnom.setIdSchedaLocale(id_scheda_locale);
                                    formAnom.setIdSchedaSimog(id_scheda_simog);

                                    this.loaderAppaltoMapper.insertW9XmlAnom(formAnom);

                                    AggiornamentoIdSchedaForm formIdScheda = new AggiornamentoIdSchedaForm();
                                    formIdScheda.setCodGara(codGara);
                                    formIdScheda.setCodLotto(codLotto);
                                    if (faseEsecuzione.intValue() != CostantiW9.COMUNICAZIONE_ESITO) {
                                        formIdScheda.setFaseEsecuzione(faseEsecuzione);
                                        if (numProgressivoFase != null) {
                                            formIdScheda.setNumProgressivoFase(numProgressivoFase);
                                        }
                                    }
                                    formIdScheda.setIdSchedaLocale(id_scheda_locale);
                                    formIdScheda.setIdSchedaSimog(id_scheda_simog);

                                    this.loaderAppaltoMapper.updateIdSchedaCancellazioneFase(formIdScheda);
                                }
                            }
                            index++;
                        }
                    }
                }
            } else {
                // TODO - gestire il caso in cui l'oggetto LoaderAppaltoResponse non
                // sia settato l'oggetto return
            }

        } catch (Exception e) {
            throw new RuntimeException("SIMOG-loaderAppalto: (readResponseLoaderAppalto)", e);
        }

        LOG.debug("Execution end::readResponseLoaderAppalto");

        return codCUI;
    }

    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    private void aggiornaIdSchedaLocaleIdSchedaSimog(final Long codGara, final Long codLotto, final Long faseEsecuzione,
                                                     final Long numProgressivoFase, String idSchedaLocale, String idSchedaSimog, String scheda) {

        AggiornamentoIdSchedaForm form = new AggiornamentoIdSchedaForm();
        form.setCodGara(codGara);
        form.setCodLotto(codLotto);
        form.setScheda(scheda);
        form.setFaseEsecuzione(faseEsecuzione);
        form.setNumProgressivoFase(numProgressivoFase);
        form.setIdSchedaLocale(idSchedaLocale);
        form.setIdSchedaSimog(idSchedaSimog);

        this.loaderAppaltoMapper.updateIdScheda(form);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void aggiornaCODCUI(Long codGara, Long codLotto, final Long faseEsecuzione, final Long numProgressivoFase,
                                String CUI, boolean isCancellazioneAggiudicazione) {

        LOG.debug("Execution start::aggiornaCODCUI");

        try {

            String cuiToUpdate = isCancellazioneAggiudicazione ? null : (StringUtils.isNotEmpty(CUI) ? CUI : null);

//			if (isCancellazioneAggiudicazione) {
//				this.loaderAppaltoMapper.updateCodCui(codGara, codLotto, null);
//			} else if (StringUtils.isNotEmpty(CUI)) {
//				this.loaderAppaltoMapper.updateCodCui(codGara, codLotto, CUI);
//			} else {
//				this.loaderAppaltoMapper.updateCodCui(codGara, codLotto, null);
//			}

            switch (faseEsecuzione.intValue()) {
                case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
                case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
                case CostantiW9.ADESIONE_ACCORDO_QUADRO:
                    this.loaderAppaltoMapper.updateCodCuiW9Appa(codGara, codLotto, numProgressivoFase, cuiToUpdate);
                    break;
                case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
                case CostantiW9.STIPULA_ACCORDO_QUADRO:
                    this.loaderAppaltoMapper.updateCodCuiW9Iniz(codGara, codLotto, numProgressivoFase, cuiToUpdate);
                    break;
                case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
                    this.loaderAppaltoMapper.updateCodCuiW9Avan(codGara, codLotto, numProgressivoFase, cuiToUpdate);
                    break;
                case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
                    this.loaderAppaltoMapper.updateCodCuiW9Conc(codGara, codLotto, numProgressivoFase, cuiToUpdate);
                    break;
                case CostantiW9.COLLAUDO_CONTRATTO:
                    this.loaderAppaltoMapper.updateCodCuiW9Coll(codGara, codLotto, numProgressivoFase, cuiToUpdate);
                    break;
                case CostantiW9.ISTANZA_RECESSO:
                    this.loaderAppaltoMapper.updateCodCuiW9Rita(codGara, codLotto, numProgressivoFase, cuiToUpdate);
                    break;
                case CostantiW9.ACCORDO_BONARIO:
                    this.loaderAppaltoMapper.updateCodCuiW9Acco(codGara, codLotto, numProgressivoFase, cuiToUpdate);
                    break;
                case CostantiW9.SOSPENSIONE_CONTRATTO:
                    this.loaderAppaltoMapper.updateCodCuiW9Sosp(codGara, codLotto, numProgressivoFase, cuiToUpdate);
                    break;
                case CostantiW9.VARIANTE_CONTRATTO:
                    this.loaderAppaltoMapper.updateCodCuiW9Vari(codGara, codLotto, numProgressivoFase, cuiToUpdate);
                    break;
                case CostantiW9.SUBAPPALTO:
                    this.loaderAppaltoMapper.updateCodCuiW9Suba(codGara, codLotto, numProgressivoFase, cuiToUpdate);
                    break;
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nell'aggiornamento del campo CODCUI", e);
        }

        LOG.debug("Execution end::aggiornaCODCUI");
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void aggiornaStatoFase(Long codGara, Long codLotto, Long faseEsecuzione) {

        LOG.debug("Execution start::aggiornaStatoFase");

        try {
            this.loaderAppaltoMapper.updateStatoFase(codGara, codLotto, faseEsecuzione);
        } catch (Exception e) {
            throw new RuntimeException("Errore nell'aggiornamento dello stato di esportazione della fase", e);
        }

        LOG.debug("Execution end::aggiornaStatoFase");
    }

    private void setAnagraficaResponsabili(final ObjectFactory obf, final ResponsabiliType responsabili,
                                           final Long codGara, final Long codLotto) {

        LOG.debug("Execution start::setAnagraficaResponsabili");

        try {

            List<DatiTecniEntry> listaDatiTecni = this.loaderAppaltoMapper.getListaDatiTecni(codGara, codLotto);

            if (listaDatiTecni != null && listaDatiTecni.size() > 0) {

                boolean isEmpty = true;

                for (DatiTecniEntry datiTecniEntry : listaDatiTecni) {

                    ResponsabileType responsabile = obf.createResponsabileType();

                    if (datiTecniEntry.getCftec() != null && !"".equals(datiTecniEntry.getCftec())) {
                        String cfResponsabile = datiTecniEntry.getCftec();
                        if (cfResponsabile.indexOf("\"") >= 0) {
                            cfResponsabile = StringUtils.replace(cfResponsabile, "\"", "''");
                        }
                        responsabile.setCODICEFISCALERESPONSABILE(cfResponsabile);
                        isEmpty = false;
                    }


                    if (datiTecniEntry.getCogtei() != null) {
                        if ("".equals(datiTecniEntry.getCogtei())) {
                            responsabile.setCOGNOME(" ");
                        } else {
                            String cognome = datiTecniEntry.getCogtei();
                            if (cognome.length() > 50)
                                cognome = cognome.substring(0, 50);
                            if (cognome.indexOf("\"") >= 0) {
                                cognome = StringUtils.replace(cognome, "\"", "''");
                            }
                            responsabile.setCOGNOME(cognome);
                        }

                        isEmpty = false;
                    }

                    if (datiTecniEntry.getNometei() != null && !"".equals(datiTecniEntry.getNometei())) {
                        if ("".equals(datiTecniEntry.getNometei())) {
                            responsabile.setNOME(" ");
                        } else {
                            String nome = datiTecniEntry.getNometei();
                            if (nome.length() > 50)
                                nome = nome.substring(0, 50);
                            if (nome.indexOf("\"") >= 0) {
                                nome = StringUtils.replace(nome, "\"", "''");
                            }
                            responsabile.setNOME(nome);
                        }

                        isEmpty = false;
                    }

                    if (datiTecniEntry.getTeltec() != null) {
                        if ("".equals(datiTecniEntry.getTeltec())) {
                            responsabile.setTELEFONO(" ");
                        } else {
                            String telefono = datiTecniEntry.getTeltec();
                            if (telefono.length() > 20)
                                telefono = telefono.substring(0, 20);
                            if (telefono.indexOf("\"") >= 0) {
                                telefono = StringUtils.replace(telefono, "\"", "''");
                            }
                            responsabile.setTELEFONO(telefono);
                        }

                        isEmpty = false;
                    }

                    if (datiTecniEntry.getEmatec() != null) {

                        if ("".equals(datiTecniEntry.getEmatec())) {
                            responsabile.setEMAIL(" ");
                        } else {
                            String email = datiTecniEntry.getEmatec();
                            if (email.length() > 64)
                                email = email.substring(0, 64);
                            if (email.indexOf("\"") >= 0) {
                                email = StringUtils.replace(email, "\"", "''");
                            }
                            responsabile.setEMAIL(email);
                        }
                        isEmpty = false;
                    }

                    if (datiTecniEntry.getFaxtec() != null) {
                        if ("".equals(datiTecniEntry.getFaxtec())) {
                            responsabile.setFAX(" ");
                        } else {
                            String fax = datiTecniEntry.getFaxtec();
                            if (fax.length() > 20)
                                fax = fax.substring(0, 20);
                            if (fax.indexOf("\"") >= 0) {
                                fax = StringUtils.replace(fax, "\"", "''");
                            }
                            responsabile.setFAX(fax);
                        }
                        isEmpty = false;
                    }
                    // Gestione dell'indirizzo
                    String indirizzo = "";
                    if (datiTecniEntry.getLoctec() != null && !"".equals(datiTecniEntry.getLoctec())) {
                        indirizzo += datiTecniEntry.getLoctec();
                        isEmpty = false;
                    }
                    if (datiTecniEntry.getIndtec() != null && !"".equals(datiTecniEntry.getIndtec())) {
                        indirizzo += " - " + datiTecniEntry.getIndtec();
                        isEmpty = false;
                    }
                    if (datiTecniEntry.getNcitec() != null) {
                        if ("".equals(datiTecniEntry.getNcitec())) {
                            indirizzo = " ";
                        } else {
                            indirizzo += ", " + datiTecniEntry.getNcitec();
                        }

                        isEmpty = false;
                    }

                    if (!"".equals(indirizzo)) {
                        if (indirizzo.length() > 100)
                            indirizzo = indirizzo.substring(0, 100);
                        if (indirizzo.indexOf("\"") >= 0) {
                            indirizzo = StringUtils.replace(indirizzo, "\"", "''");
                        }
                        responsabile.setINDIRIZZO(indirizzo);
                        isEmpty = false;
                    } else {
                        responsabile.setINDIRIZZO(" ");
                    }

                    if (datiTecniEntry.getCaptec() != null) {
                        if ("".equals(datiTecniEntry.getCaptec())) {
                            responsabile.setCAP(" ");
                        } else {
                            responsabile.setCAP(datiTecniEntry.getCaptec());
                        }
                        isEmpty = false;
                    }

                    if (datiTecniEntry.getCittec() != null) {
                        if ("".equals(datiTecniEntry.getCittec())) {
                            responsabile.setCODICEISTATCOMUNE(" ");
                        } else {
                            responsabile.setCODICEISTATCOMUNE(datiTecniEntry.getCittec());
                        }

                        isEmpty = false;
                    }

                    if (!isEmpty) {
                        responsabili.getResponsabile().add(responsabile);
                    }

                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati dei responsabili", e);
        }

        LOG.debug("Execution end::setAnagraficaResponsabili");
    }

    private void setAnagraficaAggiudicatari(final ObjectFactory obf, final AggiudicatariType aggiudicatari,
                                            final Long codGara, final Long codLotto, final Long numeroProgressivoFase) {

        LOG.debug("Execution start::setAnagraficaAggiudicatari");

        try {

            List<DatiImprEntry> listDatiImpr = this.loaderAppaltoMapper.getListaDatiImpr(codGara, codLotto, numeroProgressivoFase);

            if (listDatiImpr != null && listDatiImpr.size() > 0) {

                boolean isEmpty = true;

                for (DatiImprEntry datiImprEntry : listDatiImpr) {

                    AggiudicatarioType aggiudicatario = obf.createAggiudicatarioType();

                    String cfImp = null;

                    if (datiImprEntry.getCfimp() != null && !"".equals(datiImprEntry.getCfimp())) {
                        cfImp = datiImprEntry.getCfimp();
                        aggiudicatario.setCODICEFISCALEAGGIUDICATARIO(cfImp);
                        isEmpty = false;
                    }

                    String denominazione = datiImprEntry.getNomest();
                    if (denominazione.length() > 250) {
                        denominazione = denominazione.substring(0, 250);
                    }
                    if (denominazione.indexOf("\"") >= 0) {
                        denominazione = StringUtils.replace(denominazione, "\"", "''");
                    }
                    aggiudicatario.setDENOMINAZIONE(denominazione);

                    // IMPR_LEGALIRAPPRESENTANTI                                           
                    if (StringUtils.isNotBlank(datiImprEntry.getNome())){
                    	aggiudicatario.setNOME(datiImprEntry.getNome());
                    }
                    
					if (StringUtils.isNotBlank(datiImprEntry.getCognome())){
						aggiudicatario.setCOGNOME(datiImprEntry.getCognome());
					}
					
					if (StringUtils.isNotBlank(datiImprEntry.getCf())){
						aggiudicatario.setCFRAPPRESENTANTE(datiImprEntry.getCf());
					}
                    
                                              
                    if (datiImprEntry.getNcciaa() != null && !"".equals(datiImprEntry.getNcciaa())) {
                        String cameraCommercio = datiImprEntry.getNcciaa();
                        if (cameraCommercio.length() > 50) {
                            cameraCommercio = cameraCommercio.substring(0, 50);
                        }
                        if (cameraCommercio.indexOf("\"") >= 0) {
                            cameraCommercio = StringUtils.replace(cameraCommercio, "\"", "''");
                        }
                        aggiudicatario.setCAMERACOMMERCIO(cameraCommercio);
                        isEmpty = false;
                    }

                    if (datiImprEntry.getPivimp() != null && !"".equals(datiImprEntry.getPivimp())) {
                        String pIva = datiImprEntry.getPivimp();
                        if (pIva.indexOf("\"") >= 0) {
                            pIva = StringUtils.replace(pIva, "\"", "''");
                        }
                        aggiudicatario.setPARTITAIVA(pIva);
                        isEmpty = false;
                    }

                    if (datiImprEntry.getIndimp() != null && !"".equals(datiImprEntry.getIndimp())) {
                        String indirizzo = datiImprEntry.getIndimp();
                        if (indirizzo.length() > 50) {
                            indirizzo = indirizzo.substring(0, 50);
                        }
                        if (indirizzo.indexOf("\"") >= 0) {
                            indirizzo = StringUtils.replace(indirizzo, "\"", "''");
                        }
                        aggiudicatario.setINDIRIZZO(indirizzo);
                        isEmpty = false;
                    }

                    if (datiImprEntry.getNciimp() != null && !"".equals(datiImprEntry.getNciimp())) {
                        String civico = datiImprEntry.getNciimp();
                        if (civico.indexOf("\"") >= 0) {
                            civico = StringUtils.replace(civico, "\"", "''");
                        }
                        aggiudicatario.setCIVICO(civico);
                        isEmpty = false;
                    }

                    if (datiImprEntry.getCapimp() != null && !"".equals(datiImprEntry.getCapimp())) {
                        aggiudicatario.setCAP(datiImprEntry.getCapimp());
                        isEmpty = false;
                    }

                    if (datiImprEntry.getLocimp() != null && !"".equals(datiImprEntry.getLocimp())) {
                        String citta = datiImprEntry.getLocimp();
                        if (citta.length() > 50)
                            citta = citta.substring(0, 50);
                        if (citta.indexOf("\"") >= 0) {
                            citta = StringUtils.replace(citta, "\"", "''");
                        }
                        aggiudicatario.setCITTA(citta);
                        isEmpty = false;
                    }

                    if (datiImprEntry.getProimp() != null && !"".equals(datiImprEntry.getProimp())) {
                        aggiudicatario.setPROVINCIA(datiImprEntry.getProimp());
                        isEmpty = false;
                    }

                    // Gestione della nazione
                    if (datiImprEntry.getNazimp() != null && !"".equals(datiImprEntry.getNazimp())) {

                        // Se lo stato e' 1 - ITALIA
                        if ("1".equals(datiImprEntry.getNazimp())) {
                            aggiudicatario.setSOGGETTOESTERO(FlagSNType.N);
                            aggiudicatario.setCODICESTATO("");
                        } else {
                            aggiudicatario.setSOGGETTOESTERO(FlagSNType.S);

                            // Ricavo la corrispondenza con il tabellato W3z12
                            String codiceNazione = datiImprEntry.getNazimp();
                            String statoEstero = this.getStatoEstero(codiceNazione);
                            aggiudicatario.setCODICESTATO(statoEstero);
                        }

                        isEmpty = false;
                    } else {
                        aggiudicatario.setSOGGETTOESTERO(FlagSNType.N);
                        aggiudicatario.setCODICESTATO("");
                    }

                    if (!isEmpty) {
                        aggiudicatari.getAggiudicatario().add(aggiudicatario);
                    }
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura dei dati degli aggiudicatari", e);
        }

        LOG.debug("Execution end::setAnagraficaAggiudicatari");
    }

    public String getCUI(final Long codGara, final Long codLotto, final Long faseEsecuzione,
                         final Long numProgressivoFase) {

        LOG.debug("Execution start::getCUI");

        String codiceCUI = null;

        try {
            switch (faseEsecuzione.intValue()) {
                case CostantiW9.AGGIUDICAZIONE_SOPRA_SOGLIA:
                case CostantiW9.FASE_SEMPLIFICATA_AGGIUDICAZIONE:
                case CostantiW9.ADESIONE_ACCORDO_QUADRO:
                    codiceCUI = this.loaderAppaltoMapper.getCodCuiW9Appa(codGara, codLotto, numProgressivoFase);
                    break;
                case CostantiW9.INIZIO_CONTRATTO_SOPRA_SOGLIA:
                case CostantiW9.STIPULA_ACCORDO_QUADRO:
                    codiceCUI = this.loaderAppaltoMapper.getCodCuiW9Iniz(codGara, codLotto, numProgressivoFase);
                    break;
                case CostantiW9.AVANZAMENTO_CONTRATTO_SOPRA_SOGLIA:
                    codiceCUI = this.loaderAppaltoMapper.getCodCuiW9Avan(codGara, codLotto, numProgressivoFase);
                    break;
                case CostantiW9.CONCLUSIONE_CONTRATTO_SOPRA_SOGLIA:
                    codiceCUI = this.loaderAppaltoMapper.getCodCuiW9Conc(codGara, codLotto, numProgressivoFase);
                    break;
                case CostantiW9.COLLAUDO_CONTRATTO:
                    codiceCUI = this.loaderAppaltoMapper.getCodCuiW9Coll(codGara, codLotto, numProgressivoFase);
                    break;
                case CostantiW9.ISTANZA_RECESSO:
                    codiceCUI = this.loaderAppaltoMapper.getCodCuiW9Rita(codGara, codLotto, numProgressivoFase);
                    break;
                case CostantiW9.ACCORDO_BONARIO:
                    codiceCUI = this.loaderAppaltoMapper.getCodCuiW9Acco(codGara, codLotto, numProgressivoFase);
                    break;
                case CostantiW9.SOSPENSIONE_CONTRATTO:
                    codiceCUI = this.loaderAppaltoMapper.getCodCuiW9Sosp(codGara, codLotto, numProgressivoFase);
                    break;
                case CostantiW9.VARIANTE_CONTRATTO:
                    codiceCUI = this.loaderAppaltoMapper.getCodCuiW9Vari(codGara, codLotto, numProgressivoFase);
                    break;
                case CostantiW9.SUBAPPALTO:
                    codiceCUI = this.loaderAppaltoMapper.getCodCuiW9Suba(codGara, codLotto, numProgressivoFase);
                    break;
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore nella lettura del codice identificativo dell'aggiudicazione (CUI)", e);
        }

        LOG.debug("Execution end::getCUI");

        return codiceCUI;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    private void writeMarshallingErrors(final Long codGara, final Long codLotto, final Long faseEsecuzione,
                                        final Long numProgressivoFase, final Long numXml, final List<String> validationErrors) {

        LOG.debug("Execution start::writeMarshallingErrors");

        String feedBackAnalisi = "2";

        try {

            if (validationErrors != null && validationErrors.size() > 0) {

                Long numElaborate = Long.valueOf(0L);
                Long numErrore = Long.valueOf(validationErrors.size());
                Long numWarning = Long.valueOf(0L);
                Long numCaricate = Long.valueOf(0L);

                W9XmlAppaltoResponseForm form = new W9XmlAppaltoResponseForm();
                form.setCodGara(codGara);
                form.setCodLotto(codLotto);
                form.setNumXml(numXml);
                Date now = new Date();
                form.setDataFeedback(now);
                form.setDataElaborazione(now);
                form.setNumElaborate(numElaborate);
                form.setNumErrore(numErrore);
                form.setNumWarning(numWarning);
                form.setNumCaricate(numCaricate);
                form.setFeedbackAnalisi(feedBackAnalisi);

                this.loaderAppaltoMapper.updateW9XMLAppaltoResponse(form);

                // Cancellazione della tabella W9XMLANOM
                this.loaderAppaltoMapper.deleteW9XmlAnom(codGara, codLotto, numXml);

                int index = 1;

                for (String validationError : validationErrors) {
                    W9XmlAnomForm formAnom = new W9XmlAnomForm();
                    formAnom.setCodGara(codGara);
                    formAnom.setCodLotto(codLotto);
                    formAnom.setNumXml(numXml);
                    formAnom.setNum(Long.valueOf(index));
                    formAnom.setCodice(INTERNAL_VALIDATION_001);
                    formAnom.setDescrizione(StringUtils.substring(validationError, 0, 1950));
                    formAnom.setLivello("ERRORE");
                    formAnom.setElemento(0L);
                    formAnom.setProgressivo(0L);

                    this.loaderAppaltoMapper.insertW9XmlAnom(formAnom);

                    index++;
                }

            } else {
                // TODO - gestire il caso in cui l'oggetto LoaderAppaltoResponse non
                // sia settato l'oggetto return
            }

        } catch (Exception e) {
            throw new RuntimeException("SIMOG-loaderAppalto: (writeMarshallingErrors)", e);
        }

        LOG.debug("Execution end::writeMarshallingErrors");
    }

    /**
     * Metodo che logga tutti gli errori logici non legati alla validazione XML su XSD
     *
     * @param responseLoaderAppalto Oggetto di response Loader Appalto
     * @return Se sono stati riscontrati errori
     */
    private boolean logAndReturnLogicalErrors(final LoaderAppaltoResponse responseLoaderAppalto) {

        LOG.debug("Execution start::logLogicalErrors");

        boolean hasErrors = false;

        try {

            if (responseLoaderAppalto.getReturn() != null) {
                if (responseLoaderAppalto.getReturn().getFeedBack() != null) {

                    ResponseLoaderAppalto.FeedBack feedback = responseLoaderAppalto.getReturn().getFeedBack();

                    if (feedback.getInfoFlusso() != null) {
                        Long numErrore = Long.valueOf(feedback.getInfoFlusso().getNUMERRORE());
                        hasErrors = numErrore != null && numErrore > 0L;

                        Long contatoreAnomalie = 0L;
                        for (int i = 0; i < feedback.getAnomalieSchede().size(); i++) {
                            for (int j = 0; j < feedback.getAnomalieSchede().get(i).getAnomalia().size(); j++) {
                                if (feedback.getAnomalieSchede().get(i).getAnomalia().get(j).getLIVELLO().toString()
                                        .equals("ERRORE")) {
                                    contatoreAnomalie++;
                                }
                            }
                        }

                        hasErrors = contatoreAnomalie > 0L;

                    } else {
                        // Se infoFlusso non e' valorizzato, allora si e' verificato un errore
                        // legato all'utente che ha fatto accesso ai servizi SIMOG e all'indice
                        // di collaborazione usato nella richiesta per il Loader Appalto.
                        hasErrors = true;
                    }

                    List<AnomalieSchede> anomalieSchede = feedback.getAnomalieSchede();

                    for (AnomalieSchede anomalieSchedeSingle : anomalieSchede) {

                        List<AnomaliaType> anomalie = anomalieSchedeSingle.getAnomalia();

                        if (anomalie.size() > 0) {
                            // Ciclo su tutte le anomalie presenti nell'oggetto anomalieSchede
                            anomalie.stream().forEach(a -> LOG.error("Anomalia {}", a.getDESCRIZIONE()));
                        }
                    }
                }
            } else {
                // TODO - gestire il caso in cui l'oggetto LoaderAppaltoResponse non
                // sia settato l'oggetto return
            }

        } catch (Exception e) {
            LOG.error("SIMOG-loaderAppalto: (logLogicalErrors)", e);
            hasErrors = true;
        }

        LOG.debug("Execution end::logLogicalErrors");

        return hasErrors;
    }
}
