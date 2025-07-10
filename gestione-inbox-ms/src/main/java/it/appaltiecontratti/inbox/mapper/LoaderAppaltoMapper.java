package it.appaltiecontratti.inbox.mapper;

import it.appaltiecontratti.inbox.entity.contratti.LottoBaseEntry;
import it.appaltiecontratti.inbox.entity.form.AggiornamentoIdSchedaForm;
import it.appaltiecontratti.inbox.entity.form.W9XmlAnomForm;
import it.appaltiecontratti.inbox.entity.form.W9XmlAppaltoResponseForm;
import it.appaltiecontratti.inbox.entity.form.W9XmlForm;
import it.appaltiecontratti.inbox.entity.loaderappalto.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface LoaderAppaltoMapper {

    @Select("select W9LOTT.CIG as cig, W9GARA.FLAG_ENTE_SPECIALE as flagEnteSpeciale, W9LOTT.TIPO_CONTRATTO as tipoContratto, W9GARA.CF_SA_AGENTE as cfSaAgente, substring(W9GARA.DENOM_SA_AGENTE, 1, 250) as denomSaAgente, "
            + "CENTRICOSTO.CODCENTRO as codCentro, CENTRICOSTO.DENOMCENTRO as denomCentro, W9GARA.ID_TIPOLOGIA_SA as idTipologiaSa, W9GARA.FLAG_SA_AGENTE as flagSaAgente, "
            + "lpad(uffint.tipoin::TEXT, 2, '0') as tipoIn, UFFINT.CFEIN as cfEin, UFFINT.NOMEIN as nomEin, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9LOTT.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9LOTT.ID_SCHEDA_SIMOG else '' end as idSchedaSimog, "
            + "W9GARA.TIPOLOGIA_PROCEDURA as tipologiaProcedura, coalesce(W9ESITO.ESITO_PROCEDURA,CASE when W9APPA.CODGARA is not null then 1 end) as esitoProceduraEsito, FLAG_CENTRALE_STIPULA as flagCentraleStipula, "
            + "ver_simog as versioneSimog, w9gara.DURATA_ACCQUADRO as durataAccordoQuadro "
            + "from W9GARA, W9LOTT left outer join W9esito on W9LOTT.CODLOTT = W9ESITO.CODLOTT AND W9LOTT.CODGARA = W9ESITO.CODGARA "
            + "left join w9appa on W9LOTT.CODLOTT = w9appa.CODLOTT AND W9LOTT.CODGARA = w9appa.CODGARA AND w9appa.num_appa = #{numAppa} "
            + ", CENTRICOSTO, UFFINT "
            + "where W9LOTT.CODGARA = #{codGara} AND W9LOTT.CODLOTT = #{codLotto} AND "
            + "W9GARA.CODGARA = W9LOTT.CODGARA AND "
            + "W9GARA.IDCC = CENTRICOSTO.IDCENTRO AND W9GARA.CODEIN = UFFINT.CODEIN")
    DatiComuniEntry getW9DatiComuni(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto, @Param("numAppa") final Long numAppa);

    @Select("select TECNI.CFTEC as codiceFiscaleTecnico, TECNI.PIVATEC as partitaIvaTecnico from TECNI, W9LOTT where W9LOTT.CODGARA = #{codGara} AND W9LOTT.CODLOTT = #{codLotto} AND W9LOTT.RUP = TECNI.CODTEC")
    DatiTecnicoEntry getCfPivaTecnico(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto);

    @Select("select W9PUBB.DATA_GUCE as dataGuce, W9PUBB.DATA_GURI as dataGuri, W9PUBB.DATA_ALBO as dataAlbo, COALESCE(W9PUBB.QUOTIDIANI_NAZ, 0) as quotidianiNaz, "
            + "COALESCE(W9PUBB.QUOTIDIANI_REG, 0) as quotidianiReg, COALESCE(W9PUBB.PROFILO_COMMITTENTE, '1') as profiloCommittente, COALESCE(W9PUBB.SITO_MINISTERO_INF_TRASP, '2') as sitoMinisteroInfTrasp, "
            + "COALESCE(W9PUBB.SITO_OSSERVATORIO_CP, W9GARA.PREV_BANDO) as sitoOsservatorioCp, W9PUBB.DATA_BORE as dataBore, W9PUBB.PERIODICI as periodici, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is null then W9LOTT.ID_SCHEDA_LOCALE else '' end as idSchedaLocale, W9LOTT.ID_SCHEDA_SIMOG as idSchedaSimog "
            + "from W9GARA join W9LOTT on W9GARA.CODGARA=W9LOTT.CODGARA left outer join W9PUBB on W9PUBB.CODGARA = W9GARA.CODGARA "
            + "where W9LOTT.CODGARA = #{codGara} AND W9LOTT.CODLOTT = #{codLotto}")
    DatiPubblicazioneEntry getW9DatiPubblicazione(@Param("codGara") final Long codGara,
                                                  @Param("codLotto") final Long codLotto);

    @Select("select art_e1 as artE1, exsottosoglia as exSottosoglia, importo_tot as importoTotale from w9lott where codgara = #{codGara} and codlott = #{codLotto}")
    DatiE1S2Entry getE1S2(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto);

    @Select("select PROCEDURA_ACC as proceduraAcc, PREINFORMAZIONE as preinformazione, TERMINE_RIDOTTO as termineRidotto, ID_MODO_INDIZIONE as idModoIndizione, "
            + "W9LOTT.ID_MODO_GARA as idModoGara, COALESCE(NUM_IMPRESE_INVITATE, 0) as numImpreseInvitate,  NUM_IMPRESE_RICHIEDENTI as numImpreseRichiedenti, "
            + "COALESCE(NUM_IMPRESE_OFFERENTI, 0) as numImpreseOfferenti, NUM_OFFERTE_AMMESSE as numOfferteAmmesse, DATA_VERB_AGGIUDICAZIONE as dataVerbAggiudicazione, "
            + "DATA_SCADENZA_RICHIESTA_INVITO as dataScadenzaRichiestaInvito, DATA_SCADENZA_PRES_OFFERTA as dataScadenzaPresOfferta, IMPORTO_AGGIUDICAZIONE as importoAggiudicazione, "
            + "W9LOTT.ID_SCELTA_CONTRAENTE as idSceltaContraente, COALESCE(IMPORTO_LAVORI, 0) as importoLavori, COALESCE(IMPORTO_SERVIZI, 0) as importoServizi, "
            + "COALESCE(IMPORTO_FORNITURE, 0) as importoForniture, COALESCE(W9APPA.IMPORTO_ATTUAZIONE_SICUREZZA, 0) as importoAttuazioneSicurezza, IMPORTO_DISPOSIZIONE as importoDisposizione, "
            + "COALESCE(IMPORTO_PROGETTAZIONE, 0) as importoProgettazione, case when W9APPA.REQUISITI_SETT_SPEC = 2 then '1' else '2' end as requisitiSettSpec2, "
            + "case when W9APPA.REQUISITI_SETT_SPEC = 1 then '1' else '2' end as requisitiSettSpec1, W9LOTT.ID_TIPO_PRESTAZIONE as idTipoPrestazione, "
            + "W9LOTT.CUP as cup, FLAG_ACCORDO_QUADRO as flagAccordoQuadro, W9LOTT.LUOGO_ISTAT as luogoIstat, W9LOTT.LUOGO_NUTS as luogoNuts, COALESCE(ASTA_ELETTRONICA, '2') as astaElettronica, "
            + "ROUND(PERC_RIBASSO_AGG, 5) as percRibassoAgg, ROUND(PERC_OFF_AUMENTO, 5) as percOffAumento, DATA_INVITO as dataInvito, COALESCE(NUM_MANIF_INTERESSE, 0) as numManifInteresse, "
            + "DATA_MANIF_INTERESSE as dataManifInteresse, FLAG_RICH_SUBAPPALTO as flagRichSubappalto, COALESCE(NUM_OFFERTE_ESCLUSE, 0) as numOfferteEscluse, OFFERTA_MASSIMO as offertaMassimo, "
            + "OFFERTA_MINIMA as offertaMinima, ROUND(VAL_SOGLIA_ANOMALIA, 5) as valSogliaAnomalia, COALESCE(NUM_OFFERTE_FUORI_SOGLIA, 0) as numOfferteFuoriSoglia, "
            + "COALESCE(NUM_IMP_ESCL_INSUF_GIUST, 0) as numImpEsclInsufGiust, COD_STRUMENTO as codStrumento, IMPORTO_SUBTOTALE as importoSubtotale, IMPORTO_COMPL_APPALTO as importoComplAppalto, "
            + "IMPORTO_COMPL_INTERVENTO as importoComplIntervento, IMP_NON_ASSOG as impNonAssog, W9APPA.OPERE_URBANIZ_SCOMPUTO as opereUrbanizScomputo, W9APPA.MODALITA_RIAGGIUDICAZIONE as modalitaRiaggiudicazione, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else ''  end as idSchedaSimog, "
            + "RELAZIONE_UNICA as flagRelazioneUnica "
            + "from W9APPA, W9FASI, W9LOTT "
            + "where W9APPA.CODGARA = #{codGara} AND W9APPA.CODLOTT = #{codLotto} AND W9APPA.NUM_APPA = #{num} AND W9FASI.CODGARA = W9APPA.CODGARA AND W9FASI.CODLOTT = W9APPA.CODLOTT AND W9FASI.FASE_ESECUZIONE = 1 AND "
            + "W9FASI.NUM = W9APPA.NUM_APPA AND W9LOTT.CODGARA = W9APPA.CODGARA AND W9LOTT.CODLOTT = W9APPA.CODLOTT")
    DatiAggiudicazioneEntry getDatiAggiudicazione(@Param("codGara") final Long codGara,
                                                  @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select count(*) from W9LOTT where ID_SCELTA_CONTRAENTE in (2, 9) and CODGARA = #{codGara} and CODLOTT = #{codLotto}")
    Long countIdSceltaContraente29(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto);

    @Select("select ID_APPALTO from W9APPALAV, W9FASI where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND W9APPALAV.CODGARA = W9FASI.CODGARA AND W9APPALAV.CODLOTT = W9FASI.CODLOTT AND W9FASI.FASE_ESECUZIONE = 1")
    List<Long> getListaIdAppaltoLavori(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto);

    @Select("select ID_APPALTO from W9APPAFORN, W9FASI where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND W9APPAFORN.CODGARA = W9FASI.CODGARA AND W9APPAFORN.CODLOTT = W9FASI.CODLOTT AND W9FASI.FASE_ESECUZIONE = 1")
    List<Long> getListaIdAppaltoForn(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto);

    @Select("select ID_SCELTA_CONTRAENTE from W9LOTT where CODGARA = #{codGara} and CODLOTT = #{codLotto}")
    Long getIdSceltaContraente(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto);

    @Select("select ID_CONDIZIONE from W9COND, W9FASI where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND W9COND.CODGARA = W9FASI.CODGARA AND W9COND.CODLOTT = W9FASI.CODLOTT AND (W9FASI.FASE_ESECUZIONE = 1 OR W9FASI.FASE_ESECUZIONE = 987)")
    List<Long> getListaIdCondizioni(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto);

    @Select("select C1.CODAVCP as idCategoriaPrevalente, C2.CODAVCP as classeImporto, '1' as prevalente, '2' as scorporabile, '2' as subappaltabile "
            + "from W9LOTT LEFT OUTER JOIN W9CODICI_AVCP C2 ON W9LOTT.CLASCAT = C2.CODSITAT, W9CODICI_AVCP C1 "
            + "where W9LOTT.CODGARA = #{codGara} and W9LOTT.CODLOTT = #{codLotto} and W9LOTT.ID_CATEGORIA_PREVALENTE IS NOT NULL "
            + "and W9LOTT.ID_CATEGORIA_PREVALENTE = C1.CODSITAT and C1.TABCOD = 'W3z03' and C2.TABCOD = 'W3z11' "
            + "union select C1.CODAVCP as idCategoriaPrevalente, C2.CODAVCP as classeImporto, '2' as prevalente, "
            + "W9LOTTCATE.SCORPORABILE as scorporabile, W9LOTTCATE.SUBAPPALTABILE as subappaltabile "
            + "from W9LOTTCATE LEFT OUTER JOIN W9CODICI_AVCP C2 ON W9LOTTCATE.CLASCAT = C2.CODSITAT, W9CODICI_AVCP C1 "
            + "where W9LOTTCATE.CODGARA = #{codGara} and W9LOTTCATE.CODLOTT = #{codLotto} "
            + "and W9LOTTCATE.CATEGORIA = C1.CODSITAT and C1.TABCOD = 'W3z03' and C2.TABCOD = 'W3z11'")
    List<DatiRequisitiPartecipazioneEntry> getListaRequisitiPartecipazione(@Param("codGara") final Long codGara,
                                                                           @Param("codLotto") final Long codLotto);

    @Select("select ID_FINANZIAMENTO as idFinanziamento, COALESCE(IMPORTO_FINANZIAMENTO, 0) as importoFinanziamento from W9FINA, W9FASI where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND W9FINA.CODGARA = W9FASI.CODGARA AND W9FINA.CODLOTT = W9FASI.CODLOTT AND W9FASI.NUM = W9FINA.NUM_APPA  AND W9FASI.NUM = #{num}  AND (W9FASI.FASE_ESECUZIONE = 1 OR W9FASI.FASE_ESECUZIONE = 12) ")
    List<DatiFinanziamentiEntry> getListaFinanziamenti(@Param("codGara") final Long codGara,
                                                       @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select ID_TIPOAGG as idTipoAgg, RUOLO as ruolo, FLAG_AVVALIMENTO as flagAvvalimento, "
            + "(select IMPR.CFIMP from IMPR where IMPR.CODIMP = W9AGGI.CODIMP) as codiceFiscaleAggiudicatario, "
            + "(select IMPR.CFIMP from IMPR where IMPR.CODIMP = W9AGGI.CODIMP_AUSILIARIA) as cfAusiliaria, "
            + "(select IMPR.PIVIMP from IMPR where IMPR.CODIMP = W9AGGI.CODIMP) as pivImp, "
            + "(select IMPR.PIVIMP from IMPR where IMPR.CODIMP = W9AGGI.CODIMP_AUSILIARIA) as pivImpAusiliaria, "
            + "(select IMPR.NAZIMP from IMPR where IMPR.CODIMP = W9AGGI.CODIMP) as nazImp, "
            + "ID_GRUPPO as idGruppo, IMPORTO_AGGIUDICAZIONE as importoAggiudicazione, PERC_RIBASSO_AGG as percRibassoAgg, "
            + "PERC_OFF_AUMENTO as percOffAumento from W9AGGI, W9FASI "
            + "where W9AGGI.CODGARA = #{codGara} AND W9AGGI.CODLOTT = #{codLotto} AND W9AGGI.CODGARA = W9FASI.CODGARA AND "
            + "W9AGGI.CODLOTT = W9FASI.CODLOTT AND "
            + "(W9FASI.FASE_ESECUZIONE = 1 OR W9FASI.FASE_ESECUZIONE = 12 OR W9FASI.FASE_ESECUZIONE = 987) AND "
            + "W9FASI.NUM = W9AGGI.NUM_APPA AND W9FASI.NUM = #{num}")
    List<DatiAggiudicatarioEntry> getListaAggiudicatari(@Param("codGara") final Long codGara,
                                                        @Param("codLotto") final Long codLotto,
                                                        @Param("num") final Long num);

    @Select("select tab2tip from tab2 where tab2d1 = #{codiceNazione} and tab2cod = 'W3z12'")
    String getStatoEstero(@Param("codiceNazione") final String codiceNazione);

    @Select("select W9INCA.SEZIONE as sezione, W9INCA.ID_RUOLO as idRuolo, W9INCA.CIG_PROG_ESTERNA as cigProgEsterna, "
            + "W9INCA.DATA_AFF_PROG_ESTERNA as dataAffProgEsterna, W9INCA.DATA_CONS_PROG_ESTERNA as dataConsProgEsterna, "
            + "(select TECNI.CFTEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as codiceFiscaleResponsabile, "
            + "(select TECNI.PIVATEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as pIvaTec "
            + "from W9INCA, W9FASI where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND W9FASI.CODGARA = W9INCA.CODGARA AND W9FASI.NUM = #{num} AND "
            + "W9FASI.CODLOTT = W9INCA.CODLOTT AND W9FASI.NUM = W9INCA.NUM AND (W9INCA.SEZIONE = 'PA' OR W9INCA.SEZIONE = 'RA') AND W9FASI.FASE_ESECUZIONE = 1")
    List<DatiIncaricatoEntry> getW9IncaAggiudicazione(@Param("codGara") final Long codGara,
                                                      @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select W9INCA.SEZIONE as sezione, W9INCA.ID_RUOLO as idRuolo, W9INCA.CIG_PROG_ESTERNA as cigProgEsterna, "
            + "W9INCA.DATA_AFF_PROG_ESTERNA as dataAffProgEsterna, W9INCA.DATA_CONS_PROG_ESTERNA as dataConsProgEsterna, "
            + "(select TECNI.CFTEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as codiceFiscaleResponsabile, "
            + "(select TECNI.PIVATEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as pIvaTec "
            + "from W9INCA, W9FASI where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND W9FASI.CODGARA = W9INCA.CODGARA AND W9FASI.NUM = #{num} AND "
            + "W9FASI.CODLOTT = W9INCA.CODLOTT AND W9FASI.NUM = W9INCA.NUM AND (W9INCA.SEZIONE = 'RS') AND W9FASI.FASE_ESECUZIONE = 987")
    List<DatiIncaricatoEntry> getW9IncaSottosoglia(@Param("codGara") final Long codGara,
                                                   @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select W9INCA.SEZIONE as sezione, W9INCA.ID_RUOLO as idRuolo, W9INCA.CIG_PROG_ESTERNA as cigProgEsterna, "
            + "W9INCA.DATA_AFF_PROG_ESTERNA as dataAffProgEsterna, W9INCA.DATA_CONS_PROG_ESTERNA as dataConsProgEsterna, "
            + "(select TECNI.CFTEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as codiceFiscaleResponsabile, "
            + "(select TECNI.PIVATEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as pIvaTec "
            + "from W9INCA, W9FASI where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND W9FASI.NUM = #{num}  AND "
            + "W9FASI.CODGARA = W9INCA.CODGARA AND " + "W9FASI.CODLOTT = W9INCA.CODLOTT AND "
            + "W9FASI.NUM = W9INCA.NUM AND W9INCA.SEZIONE = 'RQ' AND W9FASI.FASE_ESECUZIONE = 12 ")
    List<DatiIncaricatoEntry> getW9IncaAdesione(@Param("codGara") final Long codGara,
                                                @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select W9INCA.SEZIONE as sezione, W9INCA.ID_RUOLO as idRuolo, W9INCA.CIG_PROG_ESTERNA as cigProgEsterna, "
            + "W9INCA.DATA_AFF_PROG_ESTERNA as dataAffProgEsterna, W9INCA.DATA_CONS_PROG_ESTERNA as dataConsProgEsterna, "
            + "(select TECNI.CFTEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as codiceFiscaleResponsabile, "
            + "(select TECNI.PIVATEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as pIvaTec "
            + "from W9INCA, W9FASI where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND W9FASI.CODGARA = W9INCA.CODGARA AND W9FASI.NUM = #{num}  AND "
            + "W9FASI.CODLOTT = W9INCA.CODLOTT AND W9FASI.NUM = W9INCA.NUM AND (W9INCA.SEZIONE = 'RE') AND W9FASI.FASE_ESECUZIONE = 987")
    List<DatiIncaricatoEntry> getW9IncaEscluso(@Param("codGara") final Long codGara,
                                               @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select W9INCA.SEZIONE as sezione, W9INCA.ID_RUOLO as idRuolo, W9INCA.CIG_PROG_ESTERNA as cigProgEsterna, "
            + "W9INCA.DATA_AFF_PROG_ESTERNA as dataAffProgEsterna, W9INCA.DATA_CONS_PROG_ESTERNA as dataConsProgEsterna, "
            + "(select TECNI.CFTEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as codiceFiscaleResponsabile, "
            + "(select TECNI.PIVATEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as pIvaTec "
            + "from W9INCA, W9FASI, W9GARA where W9FASI.CODGARA = #{codGara} AND W9FASI.CODGARA = W9GARA.codgara AND W9FASI.CODLOTT = #{codLotto} AND W9FASI.CODGARA = W9INCA.CODGARA AND W9FASI.NUM = #{num}  AND "
            + "W9FASI.CODLOTT = W9INCA.CODLOTT AND W9FASI.NUM = W9INCA.NUM AND (W9INCA.SEZIONE = 'IN') AND W9GARA.FLAG_ENTE_SPECIALE='O' AND W9INCA.ID_RUOLO NOT IN (98,99) AND W9FASI.FASE_ESECUZIONE = 2")
    List<DatiIncaricatoEntry> getW9IncaIniz(@Param("codGara") final Long codGara,
                                            @Param("codLotto") final Long codLotto, @Param("num") final Long num);


    @Select("select W9LOTT.LUOGO_ISTAT as luogoIstat, W9LOTT.LUOGO_NUTS as luogoNuts, W9LOTT.CUP as cup, "
            + "	W9APPA.IMPORTO_COMPL_APPALTO as importoComplAppalto, W9APPA.IMPORTO_DISPOSIZIONE as importoDisposizione, "
            + "	W9LOTT.ID_SCELTA_CONTRAENTE as idSceltaContraente, COALESCE(W9APPA.ASTA_ELETTRONICA, '2') as astaElettronica, "
            + "	ROUND(W9APPA.PERC_RIBASSO_AGG, 5) as percRibasso, ROUND(W9APPA.PERC_OFF_AUMENTO, 5) as percAumento, "
            + "	W9APPA.IMPORTO_AGGIUDICAZIONE as importoAggiudicazione, W9APPA.DATA_VERB_AGGIUDICAZIONE as dataVerbAggiudicazione, "
            + "	W9APPA.DATA_STIPULA as dataStipula, W9APPA.DATA_TERMINE as dataTermine, W9APPA.DURATA_CON as durataContratto, "
            + "	case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "	case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog  "
            + "	from W9APPA, W9FASI, W9LOTT "
            + "	 where W9APPA.CODGARA = #{codGara} AND W9APPA.CODLOTT = #{codLotto} AND W9APPA.NUM_APPA = #{num} AND "
            + "    W9FASI.CODGARA = W9APPA.CODGARA AND W9FASI.CODLOTT = W9APPA.CODLOTT AND "
            + "    W9FASI.FASE_ESECUZIONE = 987 AND W9LOTT.ART_E1 = '1' AND "
            + "    W9FASI.NUM = W9APPA.NUM_APPA AND W9LOTT.CODGARA = W9APPA.CODGARA AND "
            + "    W9LOTT.CODLOTT = W9APPA.CODLOTT ")
    DatiAppaltoEsclusioneEntry getDatiAppaltoEsclusione(@Param("codGara") final Long codGara,
                                                        @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select FLAG_AVVALIMENTO as flagAvvalimento, "
            + "(select IMPR.CFIMP from IMPR where IMPR.CODIMP = W9AGGI.CODIMP) as codiceFiscaleAggiudicatario, "
            + "(select IMPR.PIVIMP from IMPR where IMPR.CODIMP = W9AGGI.CODIMP) as pIvImp, "
            + "(select IMPR.NAZIMP from IMPR where IMPR.CODIMP = W9AGGI.CODIMP) as nazImp, "
            + "(select IMPR.CFIMP from IMPR where IMPR.CODIMP = W9AGGI.CODIMP_AUSILIARIA) as codiceFiscaleAusiliaria, "
            + "(select IMPR.PIVIMP from IMPR where IMPR.CODIMP = W9AGGI.CODIMP_AUSILIARIA) as pIvImpAusiliaria, "
            + "(select IMPR.NAZIMP from IMPR where IMPR.CODIMP = W9AGGI.CODIMP_AUSILIARIA) as nazImpAusiliaria "
            + "from W9AGGI, W9FASI where W9AGGI.CODGARA = #{codGara} AND W9AGGI.CODLOTT = #{codLotto} AND "
            + "W9AGGI.CODGARA = W9FASI.CODGARA AND W9AGGI.CODLOTT = W9FASI.CODLOTT AND "
            + "(W9FASI.FASE_ESECUZIONE = 1 OR W9FASI.FASE_ESECUZIONE = 12) AND W9FASI.NUM = W9AGGI.NUM_APPA")
    List<DatiDittaAusiliariaEntry> getListaDitteAusiliarieAggi(@Param("codGara") final Long codGara,
                                                               @Param("codLotto") final Long codLotto);

    @Select("select CUP, CIG from W9LOTT where CODGARA = #{codGara} AND CODLOTT = #{codLotto}")
    List<DatiCupCigLottoEntry> getCupCigLotto(@Param("codGara") final Long codGara,
                                              @Param("codLotto") final Long codLotto);

    @Select("select W9LOTT.LUOGO_ISTAT as luogoIstat, W9LOTT.LUOGO_NUTS as luogoNuts, W9LOTT.CUP as cup, "
            + "W9APPA.IMPORTO_COMPL_APPALTO as importoComplAppalto, W9APPA.IMPORTO_DISPOSIZIONE as importoDisposizione, "
            + "W9LOTT.ID_SCELTA_CONTRAENTE as idSceltaContraente, COALESCE(W9APPA.ASTA_ELETTRONICA, '2') as astaElettronica,  "
            + "ROUND(W9APPA.PERC_RIBASSO_AGG, 5) as percRibasso, ROUND(W9APPA.PERC_OFF_AUMENTO, 5) as percAumento, "
            + "W9APPA.IMPORTO_AGGIUDICAZIONE as importoAggiudicazione, W9APPA.DATA_VERB_AGGIUDICAZIONE as dataVerbAggiudicazione, "
            + "W9APPA.DATA_STIPULA as dataStipula, W9APPA.DATA_TERMINE as dataTermine, W9APPA.DURATA_CON as durataContratto, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog, "
            + "W9LOTT.CIG as cig from W9APPA, W9FASI, W9LOTT, W9GARA "
            + "where W9APPA.CODGARA = #{codGara} AND W9APPA.CODLOTT = #{codLotto} AND W9APPA.NUM_APPA = #{num} AND "
            + "W9FASI.CODGARA = W9APPA.CODGARA AND W9FASI.CODLOTT = W9APPA.CODLOTT AND "
            + "W9LOTT.CODGARA = W9GARA.CODGARA AND W9FASI.NUM = W9APPA.NUM_APPA AND "
            + "W9LOTT.CODGARA = W9APPA.CODGARA AND W9LOTT.CODLOTT = W9APPA.CODLOTT AND W9LOTT.ART_E1 = '2' AND "
            + "W9FASI.FASE_ESECUZIONE = 987 AND W9GARA.TIPO_APP <> 11")
    DatiAppaltoSottosogliaEntry getDatiAppaltoSottosoglia(@Param("codGara") final Long codGara,
                                                          @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select W9LOTT.LUOGO_ISTAT as luogoIstat, W9LOTT.LUOGO_NUTS as luogoNuts, W9APPA.COD_STRUMENTO as codStrumento, "
            + "COALESCE(W9APPA.IMPORTO_LAVORI, 0) as importoLavori, COALESCE(W9APPA.IMPORTO_SERVIZI, 0) as importoServizi, COALESCE(W9APPA.IMPORTO_FORNITURE, 0) as importoForniture, "
            + "COALESCE(W9APPA.IMPORTO_ATTUAZIONE_SICUREZZA, 0) as importoAttuazioneSicurezza, ROUND(W9APPA.PERC_RIBASSO_AGG, 5) as percRibasso, ROUND(W9APPA.PERC_OFF_AUMENTO, 5) as percAumento, "
            + "W9APPA.IMPORTO_AGGIUDICAZIONE as importoAgg, W9APPA.DATA_VERB_AGGIUDICAZIONE as dataVerbAgg, "
            + "W9APPA.FLAG_RICH_SUBAPPALTO as richSubappalto,  W9APPA.IMP_NON_ASSOG as impNonAssog ,"
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog "
            + "from W9APPA, W9FASI, W9LOTT where W9APPA.CODGARA = #{codGara} AND W9APPA.CODLOTT = #{codLotto}  AND W9APPA.NUM_APPA = #{num} AND "
            + "W9FASI.CODGARA = W9APPA.CODGARA AND W9FASI.CODLOTT = W9APPA.CODLOTT AND "
            + "W9FASI.FASE_ESECUZIONE = 12 AND W9FASI.NUM = W9APPA.NUM_APPA AND "
            + "W9LOTT.CODGARA = W9APPA.CODGARA AND W9LOTT.CODLOTT = W9APPA.CODLOTT")
    DatiAdesioneAccordoEntry getDatiAdesioneAccordoQuadro(@Param("codGara") final Long codGara,
                                                          @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select W9INIZ.DATA_STIPULA as dataStipula, W9INIZ.DATA_ESECUTIVITA as dataEsecutivita, W9INIZ.IMPORTO_CAUZIONE as importoCauzione, W9INIZ.DATA_INI_PROG_ESEC as dataInizProg, W9INIZ.DATA_APP_PROG_ESEC as dataApprovProg, "
            + "W9INIZ.FLAG_FRAZIONATA as flagFrazionata, W9INIZ.DATA_VERBALE_CONS as dataVerbCons, W9INIZ.DATA_VERBALE_DEF as dataVerbDef, W9INIZ.FLAG_RISERVA as flagRiserva, W9INIZ.DATA_VERB_INIZIO as dataVerbInizio, W9INIZ.DATA_TERMINE as dataTermine, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog "
            + "from W9LOTT "
            + "join W9INIZ on W9LOTT.CODGARA = W9INIZ.CODGARA AND W9LOTT.CODLOTT=W9INIZ.CODLOTT "
            + "join W9FASI on  W9FASI.CODGARA = W9INIZ.CODGARA AND W9FASI.CODLOTT = W9INIZ.CODLOTT AND W9FASI.NUM = W9INIZ.NUM_INIZ "
            + "where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND W9FASI.NUM = #{num} AND "
            + "W9FASI.FASE_ESECUZIONE = 2 ")
    DatiInizioEntry getDatiInizio(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select W9PUES.DATA_GUCE as dataGuce, W9PUES.DATA_GURI as dataGuri, W9PUES.DATA_ALBO as dataAlbo, COALESCE(W9PUES.QUOTIDIANI_NAZ, 0) as quotidianiNaz, COALESCE(W9PUES.QUOTIDIANI_REG, 0) as quotidianiReg, "
            + "W9PUES.PROFILO_COMMITTENTE as profiloCommittente, COALESCE(W9PUES.SITO_MINISTERO_INF_TRASP, '2') as sitoMinistrInfr, COALESCE(W9PUES.SITO_OSSERVATORIO_CP, '2') as sitoOsservatorio, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog "
            + "from W9LOTT "
            + "join W9PUES on W9LOTT.CODGARA = W9PUES.CODGARA AND W9LOTT.CODLOTT=W9PUES.CODLOTT "
            + "join W9FASI on  W9FASI.CODGARA = W9PUES.CODGARA AND W9FASI.CODLOTT = W9PUES.CODLOTT AND W9FASI.NUM = W9PUES.NUM_INIZ "
            + "where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND W9FASI.NUM = #{num} AND "
            + "W9FASI.FASE_ESECUZIONE = 2")
    DatiPubbEsitoEntry getDatiPubbEsito(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select (select IMPR.CFIMP from IMPR where IMPR.CODIMP = W9AGGI.CODIMP) as cfAggiudicatario, "
            + "W9AGGI.CODICE_INPS as codiceInps, W9AGGI.CODICE_INAIL as codiceInail, W9AGGI.CODICE_CASSA as codiceCassa, "
            + "(select IMPR.PIVIMP from IMPR where IMPR.CODIMP = W9AGGI.CODIMP) as pivimp, "
            + "(select IMPR.NAZIMP from IMPR where IMPR.CODIMP = W9AGGI.CODIMP) as nazimp "
            + "from W9AGGI "
            + "join W9APPA on W9AGGI.CODGARA = W9APPA.CODGARA "
            + "and W9AGGI.CODLOTT = W9APPA.CODLOTT "
            + "and W9AGGI.NUM_APPA = W9APPA.NUM_APPA "
            + "join W9FASI on W9AGGI.CODGARA = W9FASI.CODGARA "
            + "and W9AGGI.CODLOTT = W9FASI.CODLOTT "
            + "and W9AGGI.NUM_APPA = W9FASI.NUM_APPA "
            + "where W9AGGI.CODGARA = #{codGara} AND W9AGGI.CODLOTT = #{codLotto} AND W9FASI.NUM = #{num} AND "
            + "W9FASI.CODGARA = W9AGGI.CODGARA AND W9FASI.CODLOTT = W9AGGI.CODLOTT AND "
            + "W9FASI.FASE_ESECUZIONE = 2 ")
    List<DatiPosizioniEntry> getDatiPosizioni(@Param("codGara") final Long codGara,
                                              @Param("codLotto") final Long codLotto,
                                              @Param("num") final Long num);

    @Select("select W9INIZ.DATA_STIPULA as dataStipula, W9INIZ.DATA_DECORRENZA as dataDecorrenza, W9INIZ.DATA_SCADENZA as dataScadenza, "
            + "		case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "		case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog "
            + "	from W9LOTT "
            + "				join W9INIZ on W9LOTT.CODGARA = W9INIZ.CODGARA AND W9LOTT.CODLOTT=W9INIZ.CODLOTT "
            + "				join W9FASI on  W9FASI.CODGARA = W9INIZ.CODGARA AND W9FASI.CODLOTT = W9INIZ.CODLOTT AND W9FASI.NUM = W9INIZ.NUM_INIZ "
            + "	where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND W9FASI.NUM = #{num} AND "
            + "				W9FASI.FASE_ESECUZIONE = 11")
    DatiStipulaEntry getDatiStipula(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select W9PUES.DATA_GUCE as dataGuce, W9PUES.DATA_GURI as dataGuri, COALESCE(W9PUES.QUOTIDIANI_NAZ, 0) as quotidianiNaz, COALESCE(W9PUES.QUOTIDIANI_REG, 0) as quotidianiReg, "
            + "W9PUES.PROFILO_COMMITTENTE as profiloCommittente, COALESCE(W9PUES.SITO_MINISTERO_INF_TRASP, '2') as sitoMinistrInfr, COALESCE(W9PUES.SITO_OSSERVATORIO_CP, '2') as sitoOsservatorioPC, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog "
            + "from W9LOTT join W9PUES on W9LOTT.CODGARA = W9PUES.CODGARA AND W9LOTT.CODLOTT=W9PUES.CODLOTT "
            + "join W9FASI on  W9FASI.CODGARA = W9PUES.CODGARA AND W9FASI.CODLOTT = W9PUES.CODLOTT AND W9FASI.NUM = W9PUES.NUM_INIZ "
            + "where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND "
            + "W9FASI.FASE_ESECUZIONE = 11 ")
    DatiPubbEsitoStipulaEntry getDatiPubbEsitoStipula(@Param("codGara") final Long codGara,
                                                      @Param("codLotto") final Long codLotto);

    @Select("select W9AVAN.FLAG_PAGAMENTO as flagPagamento, case when W9AVAN.NUM_AVAN=1 then W9AVAN.DATA_ANTICIPAZIONE else null end as dataAnticipazione, "
            + "case when W9AVAN.NUM_AVAN=1 then W9AVAN.IMPORTO_ANTICIPAZIONE else null end as importoAnticipazione, W9AVAN.DATA_RAGGIUNGIMENTO as dataRaggiungimento, "
            + "W9AVAN.IMPORTO_SAL as importoSal, W9AVAN.DATA_CERTIFICATO as dataCertificato, W9AVAN.IMPORTO_CERTIFICATO as importoCertificato, W9AVAN.FLAG_RITARDO as flagRitardo, "
            + "W9AVAN.NUM_GIORNI_SCOST as numGiorniScost, W9AVAN.NUM_GIORNI_PROROGA as numGiorniProroga, W9AVAN.DENOM_AVANZAMENTO as denomAvanzamento, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog, "
            + "W9AVAN.NUM_AVAN as numAvan from W9LOTT "
            + "join W9AVAN on W9LOTT.CODGARA = W9AVAN.CODGARA AND W9LOTT.CODLOTT=W9AVAN.CODLOTT "
            + "join W9FASI on  W9FASI.CODGARA = W9AVAN.CODGARA AND W9FASI.CODLOTT = W9AVAN.CODLOTT AND W9FASI.NUM = W9AVAN.NUM_AVAN "
            + "where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND "
            + " W9FASI.FASE_ESECUZIONE = 3")
    List<DatiAvanzamentoEntry> getDatiAvanzamento(@Param("codGara") final Long codGara,
                                                  @Param("codLotto") final Long codLotto);

    @Select("select W9CONC.ID_MOTIVO_INTERR as motivoInterr, W9CONC.ID_MOTIVO_RISOL as motivoRisol, W9CONC.DATA_RISOLUZIONE as dataRisol, W9CONC.FLAG_ONERI as flagOneri, "
            + "W9CONC.ONERI_RISOLUZIONE as oneriRisoluzione, COALESCE(W9CONC.FLAG_POLIZZA, '2') as flagPolizza, W9CONC.DATA_ULTIMAZIONE as dataUltimazione, COALESCE(W9CONC.NUM_INFORTUNI, 0) as numInfortuni, COALESCE(W9CONC.NUM_INF_PERM, 0) as numInfPerm, "
            + "COALESCE(W9CONC.NUM_INF_MORT, 0) as numInfortMortali, "
            + "W9CONC.DATA_VERBALE_CONSEGNA as dataVerbale, W9CONC.TERMINE_CONTRATTO_ULT as termineContrattoUltimo, "
            + "W9CONC.NUM_GIORNI_PROROGA as numGiorniProroga, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog "
            + "from W9LOTT "
            + "join W9CONC on W9LOTT.CODGARA = W9CONC.CODGARA AND W9LOTT.CODLOTT=W9CONC.CODLOTT "
            + "join W9FASI on  W9FASI.CODGARA = W9CONC.CODGARA AND W9FASI.CODLOTT = W9CONC.CODLOTT AND W9FASI.NUM = W9CONC.NUM_CONC "
            + "where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND W9FASI.NUM = #{num} AND "
            + " W9FASI.FASE_ESECUZIONE = 4")
    DatiConclusioneEntry getDatiConclusione(@Param("codGara") final Long codGara,
                                            @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select W9COLL.DATA_REGOLARE_ESEC as dataRegolareEsec, W9COLL.DATA_COLLAUDO_STAT as dataCollaudoStatico, W9COLL.MODO_COLLAUDO as modoCollaudo, W9COLL.DATA_NOMINA_COLL as dataNominaColl, "
            + "W9COLL.DATA_INIZIO_OPER as dataInizioOp, W9COLL.DATA_CERT_COLLAUDO as dataCertCollaudo, W9COLL.DATA_DELIBERA as dataDelibera, W9COLL.ESITO_COLLAUDO as esitoCollaudo, "
            + "W9COLL.IMP_FINALE_LAVORI as importoFinaleLavori, W9COLL.IMP_FINALE_SERVIZI as importoFinaleServizi, W9COLL.IMP_FINALE_FORNIT as importoFinaleForniture, W9COLL.IMP_FINALE_SECUR as importoFinaleSicurezza, "
            + "W9COLL.IMP_PROGETTAZIONE as importoProgettazione, W9COLL.IMP_DISPOSIZIONE as importoDisposizione, "
            + "W9COLL.AMM_NUM_DEFINITE as ammNumDef, W9COLL.AMM_NUM_DADEF as ammNumDadef, W9COLL.AMM_IMPORTO_RICH as ammImportoRich, W9COLL.AMM_IMPORTO_DEF as ammImportoDef, "
            + "W9COLL.ARB_NUM_DEFINITE as arbNumDefinite, W9COLL.ARB_NUM_DADEF as arbNumDadef, W9COLL.ARB_IMPORTO_RICH as arbImportoRich, W9COLL.ARB_IMPORTO_DEF as arbImportoDef, "
            + "W9COLL.GIU_NUM_DEFINITE as giuNumDefinite, W9COLL.GIU_NUM_DADEF as giuNumDadef, W9COLL.GIU_IMPORTO_RICH as giuImportoRich, W9COLL.GIU_IMPORTO_DEF as giuImportoDef, "
            + "W9COLL.TRA_NUM_DEFINITE as traNumDef, W9COLL.TRA_NUM_DADEF as traNumDadef, W9COLL.TRA_IMPORTO_RICH as traImportoRich, W9COLL.TRA_IMPORTO_DEF as traImportoDef, "
            + "W9COLL.LAVORI_ESTESI as lavoriEstesi, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog "
            + "from W9LOTT "
            + "join W9COLL on W9LOTT.CODGARA = W9COLL.CODGARA AND W9LOTT.CODLOTT=W9COLL.CODLOTT "
            + "join W9FASI on  W9FASI.CODGARA = W9COLL.CODGARA AND W9FASI.CODLOTT = W9COLL.CODLOTT AND W9FASI.NUM = W9COLL.NUM_COLL "
            + "where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND W9FASI.NUM = #{num} AND "
            + "W9FASI.FASE_ESECUZIONE = 5 ")
    DatiCollaudoEntry getDatiCollaudo(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select W9INCA.SEZIONE as sezione, W9INCA.ID_RUOLO as idRuolo, W9INCA.CIG_PROG_ESTERNA as cigProgEsterna, "
            + "W9INCA.DATA_AFF_PROG_ESTERNA as dataAffProgEsterna, W9INCA.DATA_CONS_PROG_ESTERNA as dataConsProgEsterna, "
            + "(select TECNI.CFTEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as codiceFiscaleResponsabile, "
            + "(select TECNI.PIVATEC from TECNI where TECNI.CODTEC = W9INCA.CODTEC) as pIvaTec "
            + "from W9INCA, W9FASI where W9FASI.CODGARA = #{codGara} AND "
            + "W9FASI.CODLOTT = #{codLotto} AND "
            + "W9FASI.CODGARA = W9INCA.CODGARA AND W9FASI.CODLOTT = W9INCA.CODLOTT AND W9FASI.NUM = #{num} AND "
            + "W9FASI.NUM = W9INCA.NUM AND W9INCA.SEZIONE = 'CO' AND "
            + "W9FASI.FASE_ESECUZIONE = 5 ")
    List<DatiIncaricatoEntry> getW9IncaCollaudo(@Param("codGara") final Long codGara,
                                                @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select W9RITA.DATA_TERMINE as dataTermine, W9RITA.DATA_CONSEGNA as dataConsegna, W9RITA.TIPO_COMUN as tipoComun, W9RITA.DURATA_SOSP as durataSosp, W9RITA.MOTIVO_SOSP as motivoSosp, W9RITA.DATA_IST_RECESSO as dataIstRecesso, "
            + "W9RITA.FLAG_ACCOLTA as flagAccolta, W9RITA.FLAG_TARDIVA as flagTardiva, W9RITA.FLAG_RIPRESA as flagRipresa, W9RITA.FLAG_RISERVA as flagRiserva, W9RITA.IMPORTO_SPESE as importoSpese, W9RITA.IMPORTO_ONERI as importoOneri, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog "
            + "from W9LOTT "
            + "join W9RITA on W9LOTT.CODGARA = W9RITA.CODGARA AND W9LOTT.CODLOTT=W9RITA.CODLOTT "
            + "join W9FASI on  W9FASI.CODGARA = W9RITA.CODGARA AND W9FASI.CODLOTT = W9RITA.CODLOTT AND W9FASI.NUM = W9RITA.NUM_RITA "
            + "where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND W9FASI.NUM = #{num} AND "
            + "W9FASI.FASE_ESECUZIONE = 10 ")
    List<DatiRitardoEntry> getDatiRitardo(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto, @Param("num") final Long num);

    @Select("select distinct id_gruppo, id_tipoagg from W9AGGI, W9FASI where W9AGGI.CODGARA = #{codGara} AND "
            + "W9AGGI.CODLOTT = #{codLotto} AND W9AGGI.CODGARA = W9FASI.CODGARA AND "
            + "W9AGGI.CODLOTT = W9FASI.CODLOTT AND "
            + "(W9FASI.FASE_ESECUZIONE = 1 OR W9FASI.FASE_ESECUZIONE = 12 OR W9FASI.FASE_ESECUZIONE = 987) AND "
            + "W9FASI.NUM = W9AGGI.NUM_APPA AND W9FASI.NUM = #{num}")
    List<DatiRaggruppamentiW9AggiEntry> getNumeriRaggruppamentoW9Aggi(@Param("codGara") final Long codGara,
                                                                      @Param("codLotto") final Long codLotto,
                                                                      @Param("num") final Long num);

    @Select("select count(*) from W9AGGI, W9FASI where W9AGGI.CODGARA = #{codGara} AND "
            + "W9AGGI.CODLOTT = #{codLotto} AND W9AGGI.CODGARA = W9FASI.CODGARA AND "
            + "W9AGGI.CODLOTT = W9FASI.CODLOTT AND "
            + "(W9FASI.FASE_ESECUZIONE = 1 OR W9FASI.FASE_ESECUZIONE = 12 OR W9FASI.FASE_ESECUZIONE = 987) AND "
            + "W9FASI.NUM = W9AGGI.NUM_APPA AND id_tipoagg in (3, 4) AND W9FASI.NUM = #{num}")
    Long getCountImpreseSingoleOrGeie(@Param("codGara") final Long codGara,
                                      @Param("codLotto") final Long codLotto,
                                      @Param("num") final Long num);

    @Select("select W9ACCO.DATA_ACCORDO as dataAccordo, W9ACCO.ONERI_DERIVANTI as oneriDerivanti, COALESCE(W9ACCO.NUM_RISERVE, 0) as numRiserve, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog, "
            + "W9ACCO.NUM_ACCO as numAcco from W9LOTT "
            + "join W9ACCO on W9LOTT.CODGARA = W9ACCO.CODGARA AND W9LOTT.CODLOTT=W9ACCO.CODLOTT "
            + "join W9FASI on  W9FASI.CODGARA = W9ACCO.CODGARA AND W9FASI.CODLOTT = W9ACCO.CODLOTT AND W9FASI.NUM = W9ACCO.NUM_ACCO "
            + "where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND "
            + "W9FASI.FASE_ESECUZIONE = 8")
    List<DatiAccordoEntry> getListaDatiAccordo(@Param("codGara") final Long codGara,
                                               @Param("codLotto") final Long codLotto);

    @Select("select W9SOSP.DATA_VERB_SOSP as dataVerbSosp, W9SOSP.DATA_VERB_RIPR as dataVerbRipr, W9SOSP.ID_MOTIVO_SOSP as idMotivoSosp, "
            + "W9SOSP.FLAG_SUPERO_TEMPO as flagSuperoTempo, W9SOSP.FLAG_RISERVE as flagRiserve, W9SOSP.FLAG_VERBALE as flagVerbale, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog, "
            + "W9SOSP.NUM_SOSP as numSosp from W9LOTT "
            + "join W9SOSP on W9LOTT.CODGARA = W9SOSP.CODGARA AND W9LOTT.CODLOTT=W9SOSP.CODLOTT "
            + "join W9FASI on  W9FASI.CODGARA = W9SOSP.CODGARA AND W9FASI.CODLOTT = W9SOSP.CODLOTT AND W9FASI.NUM = W9SOSP.NUM_SOSP "
            + "where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND "
            + "W9FASI.FASE_ESECUZIONE = 6")
    List<DatiSospensioneEntry> getListaDatiSospensione(@Param("codGara") final Long codGara,
                                                       @Param("codLotto") final Long codLotto);

    @Select("select W9VARI.DATA_VERB_APPR as dataVerbAppr, W9VARI.ALTRE_MOTIVAZIONI as altreMotivazioni, "
			+ "W9VARI.IMP_RIDET_LAVORI as impRidetLavori, W9VARI.IMP_RIDET_SERVIZI as impRidetServizi, W9VARI.IMP_RIDET_FORNIT as impRidetFornit, "
			+ "W9VARI.IMP_SICUREZZA as impSicurezza, W9VARI.IMP_PROGETTAZIONE as impProgettazione, W9VARI.IMP_DISPOSIZIONE as impDisposizione, "
			+ "W9VARI.DATA_ATTO_AGGIUNTIVO as dataAttoAggiuntivo, W9VARI.NUM_GIORNI_PROROGA as numGiorniProroga, W9VARI.NUM_VARI as numVari, "
			+ "W9VARI.imp_non_assog as impNonAssog, W9VARI.CIG_NUOVA_PROC as cigNuovaProcedura, "
			+ "W9VARI.URL_VARIANTI_CO as urlVariantiCo, W9VARI.MOTIVO_REV_PREZZI as motivoRevPrezzi, "
			+ "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
			+ "case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog "
			+ "from W9LOTT "
			+ "join W9VARI on W9LOTT.CODGARA = W9VARI.CODGARA AND W9LOTT.CODLOTT=W9VARI.CODLOTT "
			+ "join W9FASI on  W9FASI.CODGARA = W9VARI.CODGARA AND W9FASI.CODLOTT = W9VARI.CODLOTT AND W9FASI.NUM = W9VARI.NUM_VARI "
			+ "where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND "
			+ " W9FASI.FASE_ESECUZIONE = 7")
	List<DatiVarianteEntry> getListaDatiVariante(@Param("codGara") final Long codGara,
			@Param("codLotto") final Long codLotto);

    @Select("select W9MOTI.ID_MOTIVO_VAR from W9MOTI where W9MOTI.CODGARA = #{codGara} AND W9MOTI.CODLOTT = #{codLotto} AND W9MOTI.NUM_VARI = #{numProgressivoFase} AND W9MOTI.ID_MOTIVO_VAR IS NOT NULL")
    List<Long> getListaMotivazioniFaseVariante(@Param("codGara") final Long codGara,
                                               @Param("codLotto") final Long codLotto, @Param("numProgressivoFase") final Long numProgressivoFase);

    @Select("select (select IMPR.CFIMP from IMPR where IMPR.CODIMP = W9SUBA.CODIMP) as cfDitta, "
            + "W9SUBA.DATA_AUTORIZZAZIONE as dataAutorizzazione, W9SUBA.OGGETTO_SUBAPPALTO as oggettoSubappalto, "
            + "W9SUBA.IMPORTO_PRESUNTO as importoPresunto, W9SUBA.IMPORTO_EFFETTIVO as importoEffettivo, "
            + "C.CODAVCP as idCategoria, W9SUBA.ID_CPV as idCpv, "
            + "(select IMPR.PIVIMP from IMPR where IMPR.CODIMP = W9SUBA.CODIMP) as pIvImp, "
			+ "(select IMPR.nazimp from IMPR where IMPR.CODIMP = W9SUBA.CODIMP) as nazione, "
            + "(select IMPR.CFIMP from IMPR where IMPR.CODIMP = W9SUBA.CODIMP_AGGIUDICATRICE) as cfDitta2, "
            + "(select IMPR.PIVIMP from IMPR where IMPR.CODIMP = W9SUBA.CODIMP_AGGIUDICATRICE) as pIvImp2, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then '' else W9FASI.ID_SCHEDA_LOCALE end as idSchedaLocale, "
            + "case when W9LOTT.ID_SCHEDA_SIMOG is not null then W9FASI.ID_SCHEDA_SIMOG else '' end as idSchedaSimog, "
            + "W9SUBA.NUM_SUBA as numSuba from W9SUBA, W9FASI, W9LOTT, W9CODICI_AVCP C "
            + "where W9FASI.CODGARA = #{codGara} AND W9FASI.CODLOTT = #{codLotto} AND "
            + "W9FASI.CODGARA = W9LOTT.CODGARA AND W9FASI.CODLOTT = W9LOTT.CODLOTT AND W9FASI.CODGARA = W9SUBA.CODGARA AND "
            + "W9FASI.CODLOTT = W9SUBA.CODLOTT AND W9FASI.NUM = W9SUBA.NUM_SUBA AND "
            + "W9FASI.FASE_ESECUZIONE = 9 AND ART_E1='2' AND "
            + "W9SUBA.ID_CATEGORIA = C.CODSITAT and C.TABCOD = 'W3z03'")
    List<DatiSubappaltoEntry> getListaDatiSubappalto(@Param("codGara") final Long codGara,
                                                     @Param("codLotto") final Long codLotto);

    @Select("select max(NUMXML) from W9XML where CODGARA = #{codGara} and CODLOTT = #{codLotto}")
    Long selectW9Xml(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto);

    @Insert("insert into w9xml(CODGARA, CODLOTT, NUMXML, DATA_EXPORT, DATA_INVIO, XML, IDFLUSSO, FASE_ESECUZIONE, NUM) values (#{codGara}, #{codLotto}, #{numXml}, #{dataExport}, #{dataInvio}, #{xml}, #{idFlusso}, #{faseEsecuzione}, #{numProgressivoFase})")
    void insertW9Xml(W9XmlForm form);

    void updateW9Xml(W9XmlForm form);

    @Update("UPDATE W9FLUSSI SET XML = #{xml} WHERE IDFLUSSO = #{idFlusso}")
    void updateXmlFlusso(@Param("idFlusso") Long idFlusso, @Param("xml") String xml);

    @Delete("delete from w9xmlanom where codgara = #{codGara} and codlott = #{codLotto} and numxml = #{numXml}")
    void deleteW9XmlAnom(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                         @Param("numXml") final Long numXml);

    @Insert("insert into w9xmlanom (codgara, codlott, numxml, num, codice, descrizione, livello, elemento, scheda, progressivo, campo_xml, id_scheda_locale, id_scheda_simog) values (#{codGara}, #{codLotto}, #{numXml}, #{num}, #{codice}, #{descrizione}, #{livello}, #{elemento}, #{scheda}, #{progressivo}, #{campoXml}, #{idSchedaLocale}, #{idSchedaSimog})")
    void insertW9XmlAnom(W9XmlAnomForm form);

    @Update("update w9xml set data_feedback = #{dataFeedback}, data_elaborazione = #{dataElaborazione}, num_elaborate = #{numElaborate}, num_errore = #{numErrore}, num_warning = #{numWarning}, num_caricate = #{numCaricate}, feedback_analisi = #{feedbackAnalisi} where codgara = #{codGara} and codlott = #{codLotto} and numxml = #{numXml}")
    void updateW9XMLAppaltoResponse(W9XmlAppaltoResponseForm form);

    void updateIdScheda(AggiornamentoIdSchedaForm form);

    void updateIdSchedaCancellazioneFase(AggiornamentoIdSchedaForm form);

    @Update("update W9FASI set DAEXPORT = '2' where CODGARA = #{codGara} and CODLOTT = #{codLotto} and FASE_ESECUZIONE = #{faseEsecuzione}")
    void updateStatoFase(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                         @Param("faseEsecuzione") final Long faseEsecuzione);

    @Select("select distinct CFTEC as cftec, COALESCE(COGTEI,NOMTEC) as cogtei, COALESCE(NOMETEI,' ') as nometei, "
            + "COALESCE(TELTEC,' ') as teltec, COALESCE(EMATEC,' ') as ematec, COALESCE(FAXTEC,' ') as faxtec, "
            + "LOCTEC as loctec, COALESCE(INDTEC,' ') as indtec, COALESCE(NCITEC,' ') as ncitec, COALESCE(CAPTEC,' ') as captec, "
            + "COALESCE(CITTEC,' ') as cittec from "
            + "(select i.CODGARA as CODGARA,i.CODLOTT as CODLOTT,i.CODTEC as CODTEC from W9INCA i join W9FASI f on i.CODGARA = f.CODGARA AND i.CODLOTT = f.CODLOTT "
            + "where i.ID_RUOLO NOT IN (98,99) "
            + "UNION SELECT W9LOTT.CODGARA as CODGARA, W9LOTT.CODLOTT as CODLOTT,W9LOTT.RUP as CODTEC FROM W9LOTT) u "
            + "join TECNI t on t.codtec = u.codtec where CODGARA = #{codGara} AND CODLOTT = #{codLotto}")
    List<DatiTecniEntry> getListaDatiTecni(@Param("codGara") final Long codGara,
                                           @Param("codLotto") final Long codLotto);

    @Select("SELECT IMP.CFIMP as cfimp, IMP.NOMEST as nomest, COALESCE(IMP.NCCIAA,' ') as ncciaa, COALESCE(IMP.PIVIMP,' ') as pivimp, COALESCE(IMP.INDIMP,' ') as indimp, COALESCE(IMP.NCIIMP,' ') as nciimp, "
            + "COALESCE(IMP.CAPIMP,' ') as capimp, COALESCE(IMP.LOCIMP,' ') as locimp, COALESCE(IMP.PROIMP,' ') as proimp, IMP.NAZIMP as nazimp, IMP.CODIMP as codimp,ag.nome_legrap as nome, ag.cognome_legrap as cognome, ag.cf_legrap as cf "
            + "FROM IMPR IMP, W9AGGI ag "
            + "WHERE IMP.CODIMP = ag.CODIMP AND ag.CODGARA = #{codGara} AND ag.CODLOTT = #{codLotto} and ag.num_appa = #{numAppa}")
    List<DatiImprEntry> getListaDatiImpr(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto, @Param("numAppa") final Long numAppa);

    @Select("SELECT COALESCE(TEIM.COGTIM, ' ') as cogtim, COALESCE(TEIM.NOMETIM, ' ') as nometim, COALESCE(TEIM.CFTIM,' ') as cfRappresentante "
            + "FROM IMPR, TEIM, IMPLEG "
            + "WHERE IMPR.CODIMP = IMPLEG.CODIMP2 AND IMPLEG.CODLEG = TEIM.CODTIM AND IMPR.CODIMP = #{codImp} AND IMPLEG.LEGFIN IS NULL")
    List<DatiImprLegaliRappresentantiEntry> getImprLegaliRappresentanti(@Param("codImp") final String codImp);

    @Select("SELECT COALESCE(TEIM.COGTIM, ' ') as cogtim, COALESCE(TEIM.NOMETIM, ' ') as nometim, COALESCE(TEIM.CFTIM,' ') as cfRappresentante, IMPLEG.LEGFIN as legFin "
            + "FROM IMPR, TEIM, IMPLEG "
            + "WHERE IMPR.CODIMP = IMPLEG.CODIMP2 AND IMPLEG.CODLEG = TEIM.CODTIM AND IMPR.CODIMP = #{codImp} AND IMPLEG.LEGFIN IS NOT NULL "
            + "ORDER BY IMPLEG.LEGFIN DESC")
    List<DatiImprLegaliRappresentantiEntry> getImprLegaliRappresentantiDisponibili(
            @Param("codImp") final String codImp);

    @Select("select ID_SCHEDA_LOCALE as idSchedaLocale, ID_SCHEDA_SIMOG as idSchedaSimog from W9FASI where CODGARA = #{codGara} and CODLOTT = #{codLotto} and FASE_ESECUZIONE = #{faseEsecuzione} and NUM = #{numProgressivoFase}")
    DatiIdSchedaLocaleSimogEntry getIdSchedaLocaleSimog(@Param("codGara") final Long codGara,
                                                        @Param("codLotto") final Long codLotto, @Param("faseEsecuzione") final Long faseEsecuzione,
                                                        @Param("numProgressivoFase") final Long numProgressivoFase);

    @Select("select CIG from W9LOTT where CODGARA = #{codGara} and CODLOTT = #{codLotto}")
    String getCig(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto);

    @Select("select ver_simog from w9gara where codgara = #{codGara}")
    Long getVersioneSimogGara(@Param("codGara") final Long codGara);

    /**
     * Gestione codice CUI
     */

    @Select("select CODCUI from W9APPA where CODGARA = #{codGara} AND CODLOTT = #{codLotto} and num_appa = #{numProgressivoFase}")
    String getCodCuiW9Appa(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                           @Param("numProgressivoFase") final Long numProgressivoFase);

    @Select("select a.CODCUI from W9INIZ i inner join W9APPA a on i.codgara = a.codgara and i.codlott = a.codlott and i.num_appa = a.num_appa "
            + " where i.CODGARA = #{codGara} AND i.CODLOTT = #{codLotto} and i.num_iniz = #{numProgressivoFase}")
    String getCodCuiW9Iniz(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                           @Param("numProgressivoFase") final Long numProgressivoFase);

    @Select("select a.CODCUI from W9AVAN av inner join W9APPA a on av.codgara = a.codgara and av.codlott = a.codlott and av.num_appa = a.num_appa "
            + " where av.CODGARA = #{codGara} AND av.CODLOTT = #{codLotto} and av.num_avan = #{numProgressivoFase}")
    String getCodCuiW9Avan(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                           @Param("numProgressivoFase") final Long numProgressivoFase);

    @Select("select a.CODCUI from W9CONC c inner join W9APPA a on c.codgara = a.codgara and c.codlott = a.codlott and c.num_appa = a.num_appa "
            + " where c.CODGARA = #{codGara} AND c.CODLOTT = #{codLotto} and c.num_conc = #{numProgressivoFase}")
    String getCodCuiW9Conc(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                           @Param("numProgressivoFase") final Long numProgressivoFase);

    @Select("select a.CODCUI from W9COLL c inner join W9APPA a on c.codgara = a.codgara and c.codlott = a.codlott and c.num_appa = a.num_appa "
            + " where c.CODGARA = #{codGara} AND c.CODLOTT = #{codLotto} and c.num_coll = #{numProgressivoFase}")
    String getCodCuiW9Coll(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                           @Param("numProgressivoFase") final Long numProgressivoFase);

    @Select("select a.CODCUI from W9RITA r inner join W9APPA a on r.codgara = a.codgara and r.codlott = a.codlott and r.num_appa = a.num_appa "
            + " where r.CODGARA = #{codGara} AND r.CODLOTT = #{codLotto} and r.num_rita = #{numProgressivoFase}")
    String getCodCuiW9Rita(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                           @Param("numProgressivoFase") final Long numProgressivoFase);

    @Select("select a.CODCUI from W9ACCO ac inner join W9APPA a on ac.codgara = a.codgara and ac.codlott = a.codlott and ac.num_appa = a.num_appa "
            + " where ac.CODGARA = #{codGara} AND ac.CODLOTT = #{codLotto} and ac.num_acco = #{numProgressivoFase}")
    String getCodCuiW9Acco(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                           @Param("numProgressivoFase") final Long numProgressivoFase);

    @Select("select a.CODCUI from W9SOSP s inner join W9APPA a on s.codgara = a.codgara and s.codlott = a.codlott and s.num_appa = a.num_appa "
            + " where s.CODGARA = #{codGara} AND s.CODLOTT = #{codLotto} and s.num_sosp = #{numProgressivoFase}")
    String getCodCuiW9Sosp(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                           @Param("numProgressivoFase") final Long numProgressivoFase);

    @Select("select a.CODCUI from W9VARI v inner join W9APPA a on v.codgara = a.codgara and v.codlott = a.codlott and v.num_appa = a.num_appa "
            + " where v.CODGARA = #{codGara} AND v.CODLOTT = #{codLotto} and v.num_vari = #{numProgressivoFase}")
    String getCodCuiW9Vari(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                           @Param("numProgressivoFase") final Long numProgressivoFase);

    @Select("select a.CODCUI from W9SUBA s inner join W9APPA a on s.codgara = a.codgara and s.codlott = a.codlott and s.num_appa = a.num_appa "
            + " where s.CODGARA = #{codGara} AND s.CODLOTT = #{codLotto} and s.num_suba = #{numProgressivoFase}")
    String getCodCuiW9Suba(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                           @Param("numProgressivoFase") final Long numProgressivoFase);

    @Update("update W9APPA set CODCUI = #{cui} where CODGARA = #{codGara} AND CODLOTT = #{codLotto} and num_appa = #{numProgressivoFase}")
    void updateCodCuiW9Appa(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                            @Param("numProgressivoFase") final Long numProgressivoFase, @Param("cui") final String cui);

    @Update("UPDATE W9APPA t1 SET codcui = #{cui} FROM W9INIZ t2 "
            + " WHERE t2.codgara = t1.codgara and t2.codlott = t1.codlott and t2.num_appa = t1.num_appa and t2.codgara = #{codGara} and t2.codlott = #{codLotto} and t2.num_iniz = #{numProgressivoFase}")
    void updateCodCuiW9Iniz(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                            @Param("numProgressivoFase") final Long numProgressivoFase, @Param("cui") final String cui);

    @Update("UPDATE W9APPA t1 SET codcui = #{cui} FROM W9AVAN t2 "
            + " WHERE t2.codgara = t1.codgara and t2.codlott = t1.codlott and t2.num_appa = t1.num_appa and t2.codgara = #{codGara} and t2.codlott = #{codLotto} and t2.num_avan = #{numProgressivoFase}")
    void updateCodCuiW9Avan(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                            @Param("numProgressivoFase") final Long numProgressivoFase, @Param("cui") final String cui);

    @Update("UPDATE W9APPA t1 SET codcui = #{cui} FROM W9CONC t2 "
            + " WHERE t2.codgara = t1.codgara and t2.codlott = t1.codlott and t2.num_appa = t1.num_appa and t2.codgara = #{codGara} and t2.codlott = #{codLotto} and t2.num_conc = #{numProgressivoFase}")
    void updateCodCuiW9Conc(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                            @Param("numProgressivoFase") final Long numProgressivoFase, @Param("cui") final String cui);

    @Update("UPDATE W9APPA t1 SET codcui = #{cui} FROM W9COLL t2 "
            + " WHERE t2.codgara = t1.codgara and t2.codlott = t1.codlott and t2.num_appa = t1.num_appa and t2.codgara = #{codGara} and t2.codlott = #{codLotto} and t2.num_coll = #{numProgressivoFase}")
    void updateCodCuiW9Coll(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                            @Param("numProgressivoFase") final Long numProgressivoFase, @Param("cui") final String cui);

    @Update("UPDATE W9APPA t1 SET codcui = #{cui} FROM W9RITA t2 "
            + " WHERE t2.codgara = t1.codgara and t2.codlott = t1.codlott and t2.num_appa = t1.num_appa and t2.codgara = #{codGara} and t2.codlott = #{codLotto} and t2.num_rita = #{numProgressivoFase}")
    void updateCodCuiW9Rita(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                            @Param("numProgressivoFase") final Long numProgressivoFase, @Param("cui") final String cui);

    @Update("UPDATE W9APPA t1 SET codcui = #{cui} FROM W9ACCO t2 "
            + " WHERE t2.codgara = t1.codgara and t2.codlott = t1.codlott and t2.num_appa = t1.num_appa and t2.codgara = #{codGara} and t2.codlott = #{codLotto} and t2.num_acco = #{numProgressivoFase}")
    void updateCodCuiW9Acco(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                            @Param("numProgressivoFase") final Long numProgressivoFase, @Param("cui") final String cui);

    @Update("UPDATE W9APPA t1 SET codcui = #{cui} FROM W9SOSP t2 "
            + " WHERE t2.codgara = t1.codgara and t2.codlott = t1.codlott and t2.num_appa = t1.num_appa and t2.codgara = #{codGara} and t2.codlott = #{codLotto} and t2.num_sosp = #{numProgressivoFase}")
    void updateCodCuiW9Sosp(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                            @Param("numProgressivoFase") final Long numProgressivoFase, @Param("cui") final String cui);

    @Update("UPDATE W9APPA t1 SET codcui = #{cui} FROM W9VARI t2 "
            + " WHERE t2.codgara = t1.codgara and t2.codlott = t1.codlott and t2.num_appa = t1.num_appa and t2.codgara = #{codGara} and t2.codlott = #{codLotto} and t2.num_vari = #{numProgressivoFase}")
    void updateCodCuiW9Vari(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                            @Param("numProgressivoFase") final Long numProgressivoFase, @Param("cui") final String cui);

    @Update("UPDATE W9APPA t1 SET codcui = #{cui} FROM W9SUBA t2 "
            + " WHERE t2.codgara = t1.codgara and t2.codlott = t1.codlott and t2.num_appa = t1.num_appa and t2.codgara = #{codGara} and t2.codlott = #{codLotto} and t2.num_suba = #{numProgressivoFase}")
    void updateCodCuiW9Suba(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto,
                            @Param("numProgressivoFase") final Long numProgressivoFase, @Param("cui") final String cui);

    @Select("select codgara as codGara, codlott as codLotto, cig as cig, oggetto as oggetto, situazione as situazione, tipo_contratto as tipologia, importo_lotto as importoNetto, CIG_MASTER_ML as masterCig from W9LOTT where codgara = #{codGara} and codlott = #{codLotto}")
	LottoBaseEntry getLotto(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto);

    @Select("select NUMXML from W9XML where CODGARA = #{codGara} and CODLOTT = #{codLotto} and FASE_ESECUZIONE = #{fase} and NUM = #{num} and DATA_INVIO is null")
	Long getNumXml(@Param("codGara") final Long codGara, @Param("codLotto") final Long codLotto, @Param("fase") final Long fase, @Param("num") final Long num);

    @Select("select max(num_appa) from w9appa where codgara = #{codGara} and codlott = #{codLotto}")
	Long getMaxNumAppa(@Param("codGara") Long codGara, @Param("codLotto") Long codLotto);

    @Select("select ID_SCHEDA_LOCALE as idSchedaLocale, ID_SCHEDA_SIMOG as idSchedaSimog from W9LOTT where CODGARA = #{codGara} and CODLOTT = #{codLotto}")
    DatiIdSchedaLocaleSimogEntry getIdSchedaLocaleSimogLotto(@Param("codGara") final Long codGara,
                                                             @Param("codLotto") final Long codLotto);
}
