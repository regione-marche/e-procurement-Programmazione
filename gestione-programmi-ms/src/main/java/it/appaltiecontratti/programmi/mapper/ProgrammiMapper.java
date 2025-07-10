package it.appaltiecontratti.programmi.mapper;

import it.appaltiecontratti.programmi.entity.*;
import it.appaltiecontratti.programmi.entity.pubblicazioni.AllegatoEntry;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface ProgrammiMapper {

    public int countSearchProgrammi(ProgrammiSearchForm form);

    public List<ProgrammaBaseEntry> searchProgrammi(ProgrammiSearchForm form, RowBounds rowBounds);

    /**
     * PROGRAMMI
     */
    @Select("select id as idProgramma ,contri as id ,norma as norma ,cenint as stazioneAppaltante,cenint as codein,idufficio as idUfficio, tiprog tipoProg ,bretri as descrizioneBreve ,anntri as annoInizio, \r\n"
            + "	pubblica as pubblica,respro as responsabile ,tadozi tipoAtto, nadozi as numeroAtto,dpadozi as dataPubblicazioneAdo ,dadozi as dataAtto ,titadozi as titoloAdozione,urladozi urlAdozione,\r\n"
            + "	taptri as tipoApprovazione,naptri as numeroApprovazione, dpapprov as dataPubblicazioneAppr ,daptri as dataApprovazione, titapprov as titoloApprovazione, urlapprov as urlApprovazione,\r\n"
            + "	dv1tri as importoDestVincolo1, dv2tri importoDestVincolo2, dv3tri importoDestVincolo3, im1tri as importoTrasfImmobile1, im2tri as importoTrasfImmobile2, im3tri as importoTrasfImmobile3,\r\n"
            + "	mu1tri as importoAcquistoMutuo1, mu2tri as importoAcquistoMutuo2, mu3tri as importoAcquistoMutuo3, pr1tri as importoCapitalePr1, pr2tri as importoCapitalePr2, pr3tri as importoCapitalePr3,\r\n"
            + "	al1tri as importoAltreRis1, al2tri as importoAltreRis2, al3tri as importoAltreRis3, ap1tri as importoFinanz1, ap2tri as importoFinanz2, ap3tri as importoFinanz3, \r\n"
            + "	bi1tri as importoStanzBilanciamento1, bi2tri as importoStanzBilanciamento2, bi3tri as importoStanzBilanciamento3, to1tri as totaleRisDisp1, to2tri  as totaleRisDisp2, \r\n"
            + "	to3tri  as totaleRisDisp3, rg1tri as importoRisorseFinReg, imprfs as importoRisorseFinStato, id_ricevuto as idRicevuto, syscon as syscon from piatri where contri = #{contri}")
    public ProgrammaEntry getProgramma(@Param("contri") Long contri);

    @Select("select id as idProgramma ,contri as id ,norma as norma ,cenint as stazioneAppaltante, cenint as codein, idufficio as idUfficio, tiprog tipoProg ,bretri as descrizioneBreve ,anntri as annoInizio, \r\n"
            + "	pubblica as pubblica,respro as responsabile ,tadozi tipoAtto, nadozi as numeroAtto,dpadozi as dataPubblicazioneAdo ,dadozi as dataAtto ,titadozi as titoloAdozione,urladozi urlAdozione,\r\n"
            + "	taptri as tipoApprovazione,naptri as numeroApprovazione, dpapprov as dataPubblicazioneAppr ,daptri as dataApprovazione, titapprov as titoloApprovazione, urlapprov as urlApprovazione,\r\n"
            + "	dv1tri as importoDestVincolo1, dv2tri importoDestVincolo2, dv3tri importoDestVincolo3, im1tri as importoTrasfImmobile1, im2tri as importoTrasfImmobile2, im3tri as importoTrasfImmobile3,\r\n"
            + "	mu1tri as importoAcquistoMutuo1, mu2tri as importoAcquistoMutuo2, mu3tri as importoAcquistoMutuo3, pr1tri as importoCapitalePr1, pr2tri as importoCapitalePr2, pr3tri as importoCapitalePr3,\r\n"
            + "	al1tri as importoAltreRis1, al2tri as importoAltreRis2, al3tri as importoAltreRis3, ap1tri as importoFinanz1, ap2tri as importoFinanz2, ap3tri as importoFinanz3, \r\n"
            + "	bi1tri as importoStanzBilanciamento1, bi2tri as importoStanzBilanciamento2, bi3tri as importoStanzBilanciamento3, to1tri as totaleRisDisp1, to2tri  as totaleRisDisp2, \r\n"
            + "	to3tri  as totaleRisDisp3, rg1tri as importoRisorseFinReg, imprfs as importoRisorseFinStato, id_ricevuto as idRicevuto, syscon as syscon from piatri where id = #{id}")
    public ProgrammaEntry getProgrammaById(@Param("id") String id);

    @Select("select contri from piatri where cenint = #{stazioneAppaltante} and tiprog= #{tipoProg} and anntri = #{annoInizio}  and norma >= 2")
    public List<Long> getIdProgrammiByAnno(@Param("stazioneAppaltante") String stazioneAppaltante,
                                           @Param("tipoProg") Long tipoProg, @Param("annoInizio") Long annoInizio);

    @Select("select contri from piatri where anntri = #{annoInizio}  and norma >= 2")
    public List<Long> getContriByAnno(@Param("annoInizio") Long annoInizio);


    @Select("select max (id) from piatri where id like #{idParzialeLun}")
    public String getMaxIdCounter(@Param("idParzialeLun") String idParzialeLun);

    @Insert("	INSERT INTO piatri(contri,id, cenint, tiprog, bretri,anntri,nadozi,dpadozi,dadozi,titadozi,urladozi,naptri,dpapprov,daptri,titapprov,\r\n"
            + "	urlapprov,syscon,pubblica,respro,norma,idufficio) VALUES(#{id},#{idProgramma},#{stazioneappaltante},#{tipoProg},#{descrizioneBreve},#{annoInizio},#{numeroAtto},#{dataPubblicazioneAdo},\r\n"
            + "	#{dataAtto},#{titoloAdozione},\r\n"
            + "	#{urlAdozione},#{numeroApprovazione},#{dataPubblicazioneAppr},#{dataApprovazione},\r\n"
            + "	#{titoloApprovazione},#{urlApprovazione},#{syscon},#{pubblica},#{responsabile},#{norma},#{idUfficio})")
    public void insertProgramma(ProgrammaInsertForm form);

    @Update("UPDATE PIATRI set nadozi=#{numeroAtto},dpadozi=#{dataPubblicazioneAdo},bretri=#{descrizioneBreve},dadozi=#{dataAtto},titadozi=#{titoloAdozione},\r\n"
            + "	urladozi=#{urlAdozione},naptri=#{numeroApprovazione},dpapprov=#{dataPubblicazioneAppr},daptri=#{dataApprovazione},titapprov=#{titoloApprovazione},\r\n"
            + "	urlapprov=#{urlApprovazione}, respro=#{responsabile}, idufficio=#{idUfficio} where contri = #{id}")
    public void updateProgramma(ProgrammaUpdateForm form);

    @Delete("DELETE from piatri WHERE CONTRI = #{contri}")
    public void deleteProgramma(@Param("contri") Long contri);

    @Update("UPDATE PIATRI SET STATRI = 3 where contri=#{contri} and statri in(2,5)")
    public void updatestatoPiatri(@Param("contri") Long contri);

    @Select("select max(contri) from piatri")
    public Long getMaxContri();

    @Update("UPDATE piatri SET id_ricevuto = #{idRicevuto} where cenint=#{stazioneAppaltante} and contri=#{id}")
    public void updateIdRicevuto(ProgrammaPubblicatoForm form);

    @Select("select count(*) from piatri where tiprog = #{tipoProgramma} and cenint = #{codiceStazioneAppaltante} and anntri = #{annoInizio}")
    public Long checkExistsProgrammaByTipoAndSAAndAnno(@Param("tipoProgramma") Long tipoProgramma,
                                                       @Param("codiceStazioneAppaltante") String codiceStazioneAppaltante, @Param("annoInizio") Long annoInizio);

    @Select("select count(*) from piatri where tiprog = #{tipoProgramma} and cenint = #{codiceStazioneAppaltante} and anntri = #{annoInizio} and contri <> #{contri}")
    Long checkExistsProgrammaByTipoAndSAAndAnnoNotCurrent(@Param("tipoProgramma") final Long tipoProgramma,
                                                          @Param("codiceStazioneAppaltante") final String codiceStazioneAppaltante, @Param("annoInizio") final Long annoInizio, @Param("contri") final Long contri);

    @Update("UPDATE piatri SET id_ricevuto = null where contri=#{id}")
    public void updateIdRicevutoFlussi(@Param("id") Long id);

    @Update("UPDATE piatri SET id_generato = null where contri=#{id}")
    public void updateIdGeneratoFlussi(@Param("id") Long id);

    /**
     * INTERVENTI
     */

    public int countSearchInterventi(InterventiSearchForm form);

    public List<InterventoBaseEntry> getInterventi(InterventiSearchForm form, RowBounds rowBounds);

    @Select("select cuiint from INTTRI where CONTRI = #{contri}")
    public List<String> getCuiInterventiProgramma(@Param("contri") Long contri);

    @Select("select cuiint from INTTRI where CONTRI = #{contri} and conint != #{conint}")
    public List<String> getCuiInterventiForCheckWithExclusion(@Param("contri") Long contri, @Param("conint") Long conint);

    @Select("select cuiint from INTTRI where CONTRI = #{contri}")
    public List<String> getCuiInterventiForCheck(@Param("contri") Long contri);

    @Select("select cuiint from INRTRI where CONTRI = #{contri}")
    public List<String> getCuiInterventiNRForCheck(@Param("contri") Long contri);

    @Select("select contri as idProgramma, conint as id, cuiint as cui, desint as descrizione, totint as importo from INTTRI where CONTRI = #{contri} and annrif =1")
    public List<InterventiDaRiproporre> getInterventiDaRiproporreAnno1(@Param("contri") Long contri);
    
    @Select("select p.id as piatriId, i.contri as idProgramma, i.conint as id, i.cuiint as cui, i.desint as descrizione, i.totint as importo, i.annrif as annoAvvio from INTTRI i join PIATRI p on i.contri = p.contri where i.CONTRI = #{contri} order by p.id desc")
    public List<InterventiDaRiproporre> getInterventiDaRiproporre(@Param("contri") Long contri);
    
    @Select("select contri as idProgramma, conint as id, annrif as annoAvvio, nprogint as numProgetto, cuiint as numeroCui, desint as descrizione, totint as importoTotale,\r\n"
            + "	annint as inPianoAnnuale, tipint as tipologiaIntervento, tipoin as settore, codint as codiceInterno, primann as annoInsAcquisto, mesein as meseAvvioProc,\r\n"
            + "	flag_cup as esenteCup, cupprg as codiceCup, acqaltint as acquistoRicompreso, cuicoll as cuiCollegato, comint as codIstatComune, nuts as codiceNuts, \r\n"
            + "	codcpv as codCpv, spesesost as speseSostenute, quantit as quantita, unimis as unitaMisura, prgint as livelloPriorita, codrup as codiceRup, \r\n"
            + "	dirgen as direzioneGenerale, struop as strutturaOperativa, respuf as dirigenteUfficio, lotfunz as lottoFunzionale, lavcompl as lavoroComplesso, \r\n"
            + "	durcont as durataContratto, contess as nuovoAffidamento, sezint as classificazioneIntervento, interv as categoriaIntervento, scamut as scadenzaUtilizzoFinanziamento,\r\n"
            + "	fiintr as finalitaIntervento, urcint as verificaConfUrbanistica, apcint as verificaConfAmbiente, stapro as statoProgettazioneApprovata, \r\n"
            + "	dv1tri as impDestVincolo1, dv2tri as impDestVincolo2, dv3tri as impDestVincolo3, dv9tri as impDestVincoloSucc, iv1tri as importoIva1,iv2tri as importoIva2, iv3tri as importoIva3, \r\n"
            + "	iv9tri  as importoIvaSucc, mu1tri as importoAcqMututo1, mu2tri as importoAcqMututo2, mu3tri as importoAcqMututo3, mu9tri as importoAcqMututoSucc, \r\n"
            + "	pr1tri as importoCapPriv1, pr2tri as importoCapPriv2, pr3tri as importoCapPriv3, pr9tri as importoCapPrivSucc, bi1tri as stanziamentiBilancio1, \r\n"
            + "	bi2tri as stanziamentiBilancio2, bi3tri as stanziamentiBilancio3, bi9tri as stanziamentiBilancioSucc, ap1tri as importoFinanz1, ap2tri as importoFinanz2,\r\n"
            + "	ap3tri  as importoFinanz3, ap9tri  as importoFinanzSucc, im1tri as trasfImmobili1, im2tri as trasfImmobili2, im3tri as trasfImmobili3,\r\n"
            + "	im9tri as trasfImmobiliSucc, al1tri as altreRisorseDisp1, al2tri as altreRisorseDisp2, al3tri as altreRisorseDisp3, al9tri as altreRisorseDispSucc, \r\n"
            + "	di1int as importoDispPriv1, di2int as importoDispPriv2, di3int as importoDispPriv3, di9int as importoDispPrivSucc, \r\n"
            + "	tcpint as tipologiaCapitalePrivato, rg1tri as importoRisorseRegionali, imprfs as importoRisorseFinanz, impalt as importoRisorseFinanzAltro, \r\n"
            + "	copfin as coperturaFinanziaria, acqverdi as acqVerdi, norrif as normativaRiferimento, avoggett as oggettoAv, avcpv as avcpv, avimpnet as importoAlnettoIvaAv,\r\n"
            + "	aviva as importoIvaAv, avimptot as importoTotaleAv, matric as acquistoBeniRiciclati, mroggett as oggettoMr, mrcpv as mrcpv, mrimpnet as importoAlnettoIvaMr, \r\n"
            + "	mriva as importoIvaMr, mrimptot as importoTotaleMr, proaff as proceduraAffidamento, delega as delegaProcAff, codausa as codiceAusa, sogagg as soggettoDelegato,\r\n"
            + "	variato as variato, refere as referenteDatiComunicati, valutazione as valutazioneResp, ditint as totImpDispFin,icpint as totaleCapitalePriv, \r\n"
            + "	intnote as note, CIG_ACCQUADRO as cigAccordoQuadro from INTTRI \r\n" + "	where CONTRI = #{contri} and CONINT = #{conint}")
    public InterventoEntry getIntervento(@Param("contri") Long contri, @Param("conint") Long conint);

    @Delete("DELETE from inttri WHERE CONTRI = #{contri}")
    public void deleteInterventi(@Param("contri") Long contri);

    @Insert("insert into inttri (contri, conint, annrif, nprogint, cuiint, desint, totint, annint, tipint, tipoin, codint, primann, mesein, flag_cup, cupprg, acqaltint, cuicoll, comint, nuts, codcpv,  quantit, unimis, prgint, codrup, dirgen, struop, respuf, lotfunz, lavcompl, durcont, contess, sezint, interv, scamut, fiintr, urcint, apcint, stapro, dv1tri, dv2tri, dv3tri, dv9tri, mu1tri, mu2tri, mu3tri, mu9tri, pr1tri, pr2tri, pr3tri, pr9tri, bi1tri, bi2tri, bi3tri, bi9tri, ap1tri, ap2tri, ap3tri, ap9tri, im1tri, im2tri, im3tri, im9tri, al1tri, al2tri, al3tri, al9tri, di1int, di2int, di3int, di9int, tcpint, rg1tri, imprfs, impalt, copfin, acqverdi, norrif, avoggett, avcpv, avimpnet, aviva, avimptot, matric, mroggett, mrcpv, mrimpnet, mriva, mrimptot, proaff, delega, codausa, sogagg, variato, refere, valutazione, ditint,icpint,spesesost, intnote,  iv1tri, iv2tri, iv3tri, iv9tri, CIG_ACCQUADRO) values (#{idProgramma},	#{id}, #{annoAvvio},	#{numProgetto}, #{numeroCui}, #{descrizione}, #{importoTotale}, #{inPianoAnnuale}, #{tipologiaIntervento}, #{settore}, #{codiceInterno}, #{annoInsAcquisto}, #{meseAvvioProc}, #{esenteCup}, #{codiceCup}, #{acquistoRicompreso}, #{cuiCollegato}, #{codIstatComune}, #{codiceNuts}, #{codCpv},  #{quantita}, #{unitaMisura}, #{livelloPriorita}, #{codiceRup}, #{direzioneGenerale}, #{strutturaOperativa}, #{dirigenteUfficio}, #{lottoFunzionale}, #{lavoroComplesso}, #{durataContratto}, #{nuovoAffidamento}, #{classificazioneIntervento}, #{categoriaIntervento}, #{scadenzaUtilizzoFinanziamento}, #{finalitaIntervento}, #{verificaConfUrbanistica}, #{verificaConfAmbiente}, #{statoProgettazioneApprovata}, #{impDestVincolo1}, #{impDestVincolo2}, #{impDestVincolo3}, #{impDestVincoloSucc}, #{importoAcqMututo1}, #{importoAcqMututo2}, #{importoAcqMututo3}, #{importoAcqMututoSucc}, #{importoCapPriv1}, #{importoCapPriv2}, #{importoCapPriv3}, #{importoCapPrivSucc}, #{stanziamentiBilancio1}, #{stanziamentiBilancio2}, #{stanziamentiBilancio3}, #{stanziamentiBilancioSucc}, #{importoFinanz1}, #{importoFinanz2}, #{importoFinanz3}, #{importoFinanzSucc}, #{trasfImmobili1}, #{trasfImmobili2}, #{trasfImmobili3}, #{trasfImmobiliSucc}, #{altreRisorseDisp1}, #{altreRisorseDisp2}, #{altreRisorseDisp3}, #{altreRisorseDispSucc}, #{importoDispPriv1}, #{importoDispPriv2}, #{importoDispPriv3}, #{importoDispPrivSucc}, #{tipologiaCapitalePrivato}, #{importoRisorseRegionali}, #{importoRisorseFinanz}, #{importoRisorseFinanzAltro}, #{coperturaFinanziaria}, #{acqVerdi}, #{normativaRiferimento}, #{oggettoAv}, #{avcpv}, #{importoAlnettoIvaAv}, #{importoIvaAv}, #{importoTotaleAv}, #{acquistoBeniRiciclati}, #{oggettoMr}, #{mrcpv}, #{importoAlnettoIvaMr}, #{importoIvaMr}, #{importoTotaleMr}, #{proceduraAffidamento}, #{delegaProcAff}, #{codiceAusa}, #{soggettoDelegato}, #{variato}, #{referenteDatiComunicati}, #{valutazioneResp},#{totImpDispFin},#{totaleCapitalePriv}, #{speseSostenute}, #{note}, #{importoIva1}, #{importoIva2}, #{importoIva3}, #{importoIvaSucc},#{cigAccordoQuadro})")
    public void insertIntervento(InterventoInsertForm form);

    @Insert("insert into inttri (contri, conint, annrif, nprogint, cuiint, desint, totint, annint, tipint, tipoin, codint, primann, mesein, flag_cup, cupprg, acqaltint, cuicoll, comint, nuts, codcpv,  quantit, unimis, prgint, codrup, dirgen, struop, respuf, lotfunz, lavcompl, durcont, contess, sezint, interv, scamut, fiintr, urcint, apcint, stapro, dv1tri, dv2tri, dv3tri, dv9tri, mu1tri, mu2tri, mu3tri, mu9tri, pr1tri, pr2tri, pr3tri, pr9tri, bi1tri, bi2tri, bi3tri, bi9tri, ap1tri, ap2tri, ap3tri, ap9tri, im1tri, im2tri, im3tri, im9tri, al1tri, al2tri, al3tri, al9tri, di1int, di2int, di3int, di9int, tcpint, rg1tri, imprfs, impalt, copfin, acqverdi, norrif, avoggett, avcpv, avimpnet, aviva, avimptot, matric, mroggett, mrcpv, mrimpnet, mriva, mrimptot, proaff, delega, codausa, sogagg, variato, refere, valutazione, ditint,icpint,spesesost, intnote,  iv1tri, iv2tri, iv3tri, iv9tri) values (#{idProgramma},	#{id}, #{annoAvvio},	#{numProgetto}, #{numeroCui}, #{descrizione}, #{importoTotale}, #{inPianoAnnuale}, #{tipologiaIntervento}, #{settore}, #{codiceInterno}, #{annoInsAcquisto}, #{meseAvvioProc}, #{esenteCup}, #{codiceCup}, #{acquistoRicompreso}, #{cuiCollegato}, #{codIstatComune}, #{codiceNuts}, #{codCpv},  #{quantita}, #{unitaMisura}, #{livelloPriorita}, #{codiceRup}, #{direzioneGenerale}, #{strutturaOperativa}, #{dirigenteUfficio}, #{lottoFunzionale}, #{lavoroComplesso}, #{durataContratto}, #{nuovoAffidamento}, #{classificazioneIntervento}, #{categoriaIntervento}, #{scadenzaUtilizzoFinanziamento}, #{finalitaIntervento}, #{verificaConfUrbanistica}, #{verificaConfAmbiente}, #{statoProgettazioneApprovata}, #{impDestVincolo1}, #{impDestVincolo2}, #{impDestVincolo3}, #{impDestVincoloSucc}, #{importoAcqMututo1}, #{importoAcqMututo2}, #{importoAcqMututo3}, #{importoAcqMututoSucc}, #{importoCapPriv1}, #{importoCapPriv2}, #{importoCapPriv3}, #{importoCapPrivSucc}, #{stanziamentiBilancio1}, #{stanziamentiBilancio2}, #{stanziamentiBilancio3}, #{stanziamentiBilancioSucc}, #{importoFinanz1}, #{importoFinanz2}, #{importoFinanz3}, #{importoFinanzSucc}, #{trasfImmobili1}, #{trasfImmobili2}, #{trasfImmobili3}, #{trasfImmobiliSucc}, #{altreRisorseDisp1}, #{altreRisorseDisp2}, #{altreRisorseDisp3}, #{altreRisorseDispSucc}, #{importoDispPriv1}, #{importoDispPriv2}, #{importoDispPriv3}, #{importoDispPrivSucc}, #{tipologiaCapitalePrivato}, #{importoRisorseRegionali}, #{importoRisorseFinanz}, #{importoRisorseFinanzAltro}, #{coperturaFinanziaria}, #{acqVerdi}, #{normativaRiferimento}, #{oggettoAv}, #{avcpv}, #{importoAlnettoIvaAv}, #{importoIvaAv}, #{importoTotaleAv}, #{acquistoBeniRiciclati}, #{oggettoMr}, #{mrcpv}, #{importoAlnettoIvaMr}, #{importoIvaMr}, #{importoTotaleMr}, #{proceduraAffidamento}, #{delegaProcAff}, #{codiceAusa}, #{soggettoDelegato}, #{variato}, #{referenteDatiComunicati}, #{valutazioneResp},#{totImpDispFin},#{totaleCapitalePriv}, #{speseSostenute}, #{note}, #{importoIva1}, #{importoIva2}, #{importoIva3}, #{importoIvaSucc})")
    public void importIntervento(InterventoEntry intervento);

    @Update("update inttri set cuiint = #{numeroCui}, annrif = #{annoAvvio},  desint = #{descrizione}, totint = #{importoTotale}, annint = #{inPianoAnnuale}, tipint = #{tipologiaIntervento}, tipoin = #{settore}, codint = #{codiceInterno}, primann = #{annoInsAcquisto}, mesein = #{meseAvvioProc}, flag_cup = #{esenteCup}, cupprg = #{codiceCup}, acqaltint = #{acquistoRicompreso}, cuicoll = #{cuiCollegato}, comint = #{codIstatComune}, nuts = #{codiceNuts}, codcpv = #{codCpv},  quantit = #{quantita}, unimis = #{unitaMisura}, prgint = #{livelloPriorita}, codrup = #{codiceRup}, dirgen = #{direzioneGenerale}, struop = #{strutturaOperativa}, respuf = #{dirigenteUfficio}, lotfunz = #{lottoFunzionale}, lavcompl = #{lavoroComplesso}, durcont = #{durataContratto}, contess = #{nuovoAffidamento}, sezint = #{classificazioneIntervento}, interv = #{categoriaIntervento}, scamut = #{scadenzaUtilizzoFinanziamento}, fiintr = #{finalitaIntervento}, urcint = #{verificaConfUrbanistica}, apcint = #{verificaConfAmbiente}, stapro = #{statoProgettazioneApprovata}, dv1tri = #{impDestVincolo1}, dv2tri = #{impDestVincolo2}, dv3tri = #{impDestVincolo3}, dv9tri = #{impDestVincoloSucc}, mu1tri = #{importoAcqMututo1}, mu2tri = #{importoAcqMututo2}, mu3tri = #{importoAcqMututo3}, mu9tri = #{importoAcqMututoSucc}, pr1tri = #{importoCapPriv1}, pr2tri = #{importoCapPriv2}, pr3tri = #{importoCapPriv3}, pr9tri = #{importoCapPrivSucc}, bi1tri = #{stanziamentiBilancio1}, bi2tri = #{stanziamentiBilancio2}, bi3tri = #{stanziamentiBilancio3}, bi9tri = #{stanziamentiBilancioSucc}, ap1tri = #{importoFinanz1}, ap2tri = #{importoFinanz2}, ap3tri = #{importoFinanz3}, ap9tri = #{importoFinanzSucc}, im1tri = #{trasfImmobili1}, im2tri = #{trasfImmobili2}, im3tri = #{trasfImmobili3}, im9tri = #{trasfImmobiliSucc}, al1tri = #{altreRisorseDisp1}, al2tri = #{altreRisorseDisp2}, al3tri = #{altreRisorseDisp3}, al9tri = #{altreRisorseDispSucc}, di1int = #{importoDispPriv1}, di2int = #{importoDispPriv2}, di3int = #{importoDispPriv3}, di9int = #{importoDispPrivSucc}, tcpint = #{tipologiaCapitalePrivato}, rg1tri = #{importoRisorseRegionali}, imprfs = #{importoRisorseFinanz}, impalt = #{importoRisorseFinanzAltro}, copfin = #{coperturaFinanziaria}, acqverdi = #{acqVerdi}, norrif = #{normativaRiferimento}, avoggett = #{oggettoAv}, avcpv = #{avcpv}, avimpnet = #{importoAlnettoIvaAv}, aviva = #{importoIvaAv}, avimptot = #{importoTotaleAv}, matric = #{acquistoBeniRiciclati}, mroggett = #{oggettoMr}, mrcpv = #{mrcpv}, mrimpnet = #{importoAlnettoIvaMr}, mriva = #{importoIvaMr}, mrimptot = #{importoTotaleMr}, proaff = #{proceduraAffidamento}, delega = #{delegaProcAff}, codausa = #{codiceAusa}, sogagg = #{soggettoDelegato}, variato = #{variato}, refere = #{referenteDatiComunicati}, valutazione = #{valutazioneResp}, ditint = #{totImpDispFin},icpint = #{totaleCapitalePriv},spesesost = #{speseSostenute}, intnote = #{note}, iv1tri = #{importoIva1}, iv2tri = #{importoIva2}, iv3tri = #{importoIva3}, iv9tri = #{importoIvaSucc}, CIG_ACCQUADRO = #{cigAccordoQuadro} where contri = #{idProgramma} and conint = #{id}")
    public void updateIntervento(InterventoInsertForm form);

    @Delete("DELETE from inttri WHERE CONTRI = #{contri} and conint = #{conint}")
    public void deleteIntervento(@Param("contri") Long contri, @Param("conint") Long conint);

    @Select("SELECT CUIINT from INTTRI i, piatri p where i.contri = p.contri and p.cenint =#{stazioneAppaltante} and i.cuiint like '%'|| #{cuiprefix} ||'%'")
    public List<String> getExistingCui(@Param("cuiprefix") String cuiprefix, @Param("stazioneAppaltante") String stazioneAppaltante);

    @Update("update INTTRI set cuiint = #{numeroCUI} where contri = #{contri} and conint = #{conint}")
    public void updateNumeroCui(@Param("numeroCUI") String numeroCUI, @Param("contri") Long contri,
                                @Param("conint") Long conint);

    /**
     * OPERE INCOMPIUTE
     */

    public int countSearchOpereIncompiute(ModulesSearchForm form);

    public List<OperaIncompiutaBaseEntry> getOpereIncompiute(ModulesSearchForm form, RowBounds rowBounds);

    @Insert("Insert into oitri (contri, numoi, cup, descrizione, determinazioni, ambitoint, annoultqe, importoint, importolav, importosal, oneriultim, avanzamento, causa, statoreal, infrastruttura, fruibile, utilizzorid, destinazioneuso, cessione, vendita, demolizione, oneri_sito,istat,nuts,sezint,interv,req_cap,req_prge,dim_um,dim_qta,sponsorizzazione,finanza_progetto,costo,finanziamento,cop_statale,cop_regionale,cop_provinciale,cop_comunale,cop_altrapubblica,cop_comunitaria,cop_privata, discontinuita_rete) values (#{idProgramma}, #{id}, #{cup}, #{descrizione}, #{determinazioni}, #{ambitoInt}, #{annoUltimoQe}, #{importoInt}, #{importoLav}, #{importoSal}, #{oneriUlt}, #{avanzamento}, #{causa}, #{statoReal}, #{infrastruttura}, #{fruibile}, #{utilizzoRid}, #{destinazioneUso}, #{cessione}, #{vendita}, #{demolizione}, #{oneriSito}, #{codIstat},#{codNuts},#{tipologiaIntervento},#{categoriaIntervento},#{requisitiCapitolato},#{requisitiProgetto},#{dimensionamentoUm},#{dimensionamentoValore},#{sponsorizzazione},#{finanzaProgetto},#{costo},#{finanziamento},#{copStatale},#{copRegionale},#{copProvinciale},#{copComunale},#{copAltraPubblica},#{copComunitaria},#{copPrivata}, #{discontinuitaRete})")
    public void insertOperaIncompiuta(OperaIncompiutaInsertForm form);

    @Insert("Insert into oitri (contri, numoi, cup, descrizione, determinazioni, ambitoint, annoultqe, importoint, importolav, importosal, oneriultim, avanzamento, causa, statoreal, infrastruttura, fruibile, utilizzorid, destinazioneuso, cessione, vendita, demolizione, oneri_sito,istat,nuts,sezint,interv,req_cap,req_prge,dim_um,dim_qta,sponsorizzazione,finanza_progetto,costo,finanziamento,cop_statale,cop_regionale,cop_provinciale,cop_comunale,cop_altrapubblica,cop_comunitaria,cop_privata, discontinuita_rete) values (#{idProgramma}, #{id}, #{cup}, #{descrizione}, #{determinazioni}, #{ambitoInt}, #{annoUltimoQe}, #{importoInt}, #{importoLav}, #{importoSal}, #{oneriUlt}, #{avanzamento}, #{causa}, #{statoReal}, #{infrastruttura}, #{fruibile}, #{utilizzoRid}, #{destinazioneUso}, #{cessione}, #{vendita}, #{demolizione}, #{oneriSito}, #{codIstat},#{codNuts},#{tipologiaIntervento},#{categoriaIntervento},#{requisitiCapitolato},#{requisitiProgetto},#{dimensionamentoUm},#{dimensionamentoValore},#{sponsorizzazione},#{finanzaProgetto},#{costo},#{finanziamento},#{copStatale},#{copRegionale},#{copProvinciale},#{copComunale},#{copAltraPubblica},#{copComunitaria},#{copPrivata}, #{discontinuitaRete})")
    public void copiaOperaIncompiuta(OperaIncompiutaEntry form);

    @Update("Update oitri set cup = #{cup}, descrizione = #{descrizione}, determinazioni = #{determinazioni}, ambitoint = #{ambitoInt}, annoultqe = #{annoUltimoQe}, importoint = #{importoInt}, importolav = #{importoLav}, importosal = #{importoSal}, oneriultim = #{oneriUlt}, avanzamento = #{avanzamento}, causa = #{causa}, statoreal = #{statoReal}, infrastruttura = #{infrastruttura}, fruibile = #{fruibile}, utilizzorid = #{utilizzoRid}, destinazioneuso = #{destinazioneUso}, cessione = #{cessione}, vendita = #{vendita}, demolizione = #{demolizione}, oneri_sito = #{oneriSito}, istat=#{codIstat},  nuts=#{codNuts},  sezint=#{tipologiaIntervento},  interv=#{categoriaIntervento},  req_cap=#{requisitiCapitolato},  req_prge=#{requisitiProgetto},  dim_um=#{dimensionamentoUm},  dim_qta=#{dimensionamentoValore},  sponsorizzazione=#{sponsorizzazione},  finanza_progetto=#{finanzaProgetto},  costo=#{costo},  finanziamento=#{finanziamento},  cop_statale=#{copStatale},  cop_regionale=#{copRegionale},  cop_provinciale=#{copProvinciale},  cop_comunale=#{copComunale},  cop_altrapubblica=#{copAltraPubblica},  cop_comunitaria=#{copComunitaria},  cop_privata =#{copPrivata}, discontinuita_rete=#{discontinuitaRete} where contri=#{idProgramma} and numoi = #{id}")
    public void updateOperaIncompiuta(OperaIncompiutaInsertForm form);

    @Delete("DELETE from oitri WHERE CONTRI = #{contri}")
    public void deleteOpereIncompiute(@Param("contri") Long contri);

    @Select("select contri as idProgramma, numoi as id, cup as cup, descrizione as descrizione,determinazioni as determinazioni, ambitoint as ambitoInt,annoultqe as annoUltimoQe,importoint as importoInt,importolav as importoLav,importosal as importoSal, oneriultim as oneriUlt,avanzamento as avanzamento,causa as causa,statoreal as statoReal,infrastruttura as infrastruttura,fruibile as fruibile,utilizzorid as utilizzoRid,destinazioneuso as destinazioneUso,cessione as cessione, vendita as vendita,demolizione as demolizione, oneri_sito as oneriSito, istat as codIstat, nuts as codNuts,sezint as tipologiaIntervento,interv as categoriaIntervento,req_cap as requisitiCapitolato,req_prge as requisitiProgetto,dim_um as dimensionamentoUm,dim_qta as dimensionamentoValore,sponsorizzazione as sponsorizzazione,finanza_progetto as finanzaProgetto,costo as costo,finanziamento as finanziamento, cop_statale as copStatale,cop_regionale as copRegionale,cop_provinciale as copProvinciale,cop_comunale as copComunale,cop_altrapubblica as copAltraPubblica,cop_comunitaria as copComunitaria,cop_privata as copPrivata, discontinuita_rete as discontinuitaRete from oitri where contri = #{contri} and numoi= #{numoi}")
    public OperaIncompiutaEntry getOperaIncompiuta(@Param("contri") Long contri, @Param("numoi") Long numoi);

    @Delete("DELETE from oitri WHERE CONTRI = #{contri} and numoi = #{numoi}")
    public void deleteOperaIncompiuta(@Param("contri") Long contri, @Param("numoi") Long numoi);

    @Select("select max(numoi) from oitri where contri = #{contri}")
    public Long getMaxNumoi(@Param("contri") Long contri);

    /**
     * INTERVENTI NON RIPROPOSTI
     */

    public int countSearchInterventiNonRiproposti(ModulesSearchForm form);

    public List<InterventoNonRipropostoEntry> getInterventiNonRiproposti(ModulesSearchForm form, RowBounds rowBounds);

    @Select("select cuiint from INRTRI where CONTRI = #{contri}")
    public List<String> getCuiInterventiNRProgramma(@Param("contri") Long contri);

    @Insert("Insert into INRTRI (contri, conint, cuiint, cupprg, desint, totint, prgint, motivo) VALUES (#{idProgramma}, #{idIntervento}, #{cui}, #{cup}, #{descrizione}, #{importoComplessivo}, #{priorita}, #{motivo})")
    public void insertInterventoNonRiproposto(InterventoNonRipropostoInsertForm form);

    @Delete("DELETE from INRTRI WHERE CONTRI = #{contri}")
    public void deleteInterventiNonRiproposti(@Param("contri") Long contri);

    @Delete("DELETE from INRTRI WHERE CONTRI = #{contri} and conint = #{conint}")
    public void deleteInterventoNonRiproposto(@Param("contri") Long contri, @Param("conint") Long conint);

    @Update("UPDATE INRTRI set motivo= #{motivo} WHERE CONTRI = #{idProgramma} and conint = #{idIntervento}")
    public void updateInterventoNonRiproposto(InterventoNonRipropostoUpdateForm form);

    @Update("UPDATE INRTRI set motivo= #{motivo}, cuiint = #{cui}, cupprg = #{cup}, desint = #{descrizione}, totint = #{importoComplessivo}, prgint = #{priorita} WHERE CONTRI = #{idProgramma} and conint = #{idIntervento}")
    public void updateInterventoNonRipropostoComplete(InterventoNonRipropostoUpdateForm form);

    @Select("select contri as idProgramma, conint as idIntervento, cuiint as cui, cupprg as cup, desint as descrizione, totint as importoComplessivo, prgint as priorita, motivo as motivo from INRTRI where contri = #{contri} and conint = #{conint}")
    public InterventoNonRipropostoEntry getInterventoNonRiproposto(@Param("contri") Long contri,
                                                                   @Param("conint") Long conint);

    @Select("select contri as idProgramma,conint as id,annrif as annoAvvio,nprogint as numProgetto,cuiint as numeroCui,desint as descrizione,totint as importoTotale,annint as inPianoAnnuale from inttri where contri = #{idProgramma} and totint >= 1000000")
    public List<InterventoBaseEntry> getInterventiAll(@Param("idProgramma") Long idProgramma);

    /**
     * IMMOBILI
     */

    @Delete("DELETE from IMMTRAI WHERE CONTRI = #{contri}")
    public void deleteImmobili(@Param("contri") Long contri);

    public List<ImmobileEntry> getImmobili(@Param("contri") Long contri, @Param("conint") Long conint,
                                           @Param("numoi") Long numoi);

    @Insert("Insert into immtrai (contri,conint,numoi,numimm,cuiimm,desimm,comist,nuts,titcor,immdisp,alienati,progdism,tipdisp,va1imm,va2imm,va3imm,va9imm,valimm) VALUES (#{idProgramma},#{idIntervento},#{idOpera},#{id},#{cui},#{descrizione},#{codIstat},#{nuts},#{trasfImmCorrisp},#{dirittoGodimento}, #{alienati}, #{progDismiss}, #{tipoDisp}, #{valStimato1},#{valStimato2},#{valStimato3},#{valAnnoSucc},#{valoreStimato})")
    public void insertImmobile(ImmobileInsertForm form);

    @Delete("delete from immtrai where contri = #{contri} and conint = #{conint}")
    public void deleteImmobiliDiInterventi(@Param("contri") Long contri, @Param("conint") Long conint);

    @Delete("delete from immtrai where contri = #{contri} and numoi = #{numoi}")
    public void deleteImmobiliDiOpereIncompiute(@Param("contri") Long contri, @Param("numoi") Long numoi);

    public Long getMaxNumimm(@Param("contri") Long contri, @Param("conint") Long conint, @Param("numoi") Long numoi);

    @Insert("Insert into immtrai (contri,conint,numoi,numimm,cuiimm,desimm,comist,nuts,titcor,immdisp,alienati,progdism,tipdisp,va1imm,va2imm,va3imm,va9imm,valimm) VALUES (#{idProgramma},#{idIntervento},#{idOpera},#{id},#{cui},#{descrizione},#{codIstat},#{nuts},#{trasfImmCorrisp},#{dirittoGodimento}, #{alienati}, #{progDismiss}, #{tipoDisp}, #{valStimato1},#{valStimato2},#{valStimato3},#{valAnnoSucc},#{valoreStimato})")
    public void importImmobile(ImmobileEntry immobile);

    @Select("SELECT cuiimm from IMMTRAI where cuiimm like '%'|| #{cuiprefix} ||'%'")
    public List<String> getExistingCuiImmtrai(@Param("cuiprefix") String cuiprefix);

    @Select("select numimm from IMMTRAI where cuiimm = #{cui} and contri = #{contri}")
    public List<String> checkImmobileCUI(@Param("contri") Long contri, @Param("cui") String cui);

    /**
     * RISORSE DI CAPITOLO
     */

    public int countSearchRisorseDiCapitolo(RisorseCapitoloSearchForm form);

    public List<RisorsaCapitoloBaseEntry> getRisorseDiCapitolo(RisorseCapitoloSearchForm form, RowBounds rowBounds);

    @Select("select  contri as idProgramma, conint as idIntervento, numcb as id, NCAPBIL as numCapBilancio,IMPSPE impegniSpesa,RG1CB as importoRisFinanz,\r\n"
            + "IMPRFSCB as importoRisFinanzStato,IMPALTCB as importoRisFinanzAltro, VERIFBIL as verificaCoerenza,DV1CB as entrateDestVincLegge1,DV2CB as entrateDestVincLegge2,\r\n"
            + "DV3CB as entrateDestVincLegge3,DV9CB as entrateDestVincLeggeSucc, MU1CB as entrateContrMutuo1 ,MU2CB as entrateContrMutuo2, MU3CB as entrateContrMutuo3,\r\n"
            + "MU9CB as entrateContrMutuoSucc,BI1CB as stanziamentiBilancio1,BI2CB  as stanziamentiBilancio2,BI3CB  as stanziamentiBilancio3,BI9CB as stanziamentiBilancioSucc,\r\n"
            + "AP1CB as impFinanz1, AP2CB as impFinanz2,AP3CB as impFinanz3,AP9CB as impFinanzSucc, AL1CB as altreRisDisp1,AL2CB as altreRisDisp2,AL3CB as altreRisDisp3,\r\n"
            + "AL9CB  as altreRisDispSucc, TO1CB as importoComplessivo1,TO2CB as importoComplessivo2, TO3CB as importoComplessivo3, TO9CB  as importoComplessivoSucc, \r\n"
            + "IV1CB as importoIva1,IV2CB as importoIva2,IV3CB as importoIva3, IV9CB as importoIvaSucc ,SPESESOST as speseSostenute, cbnote as note from ris_capitolo where contri = #{contri} and conint = #{conint}")
    public List<RisorsaCapitoloEntry> getAllRisorseDiCapitolo(@Param("contri") Long contri,
                                                              @Param("conint") Long conint);

    @Select("select  contri as idProgramma, conint as idIntervento, numcb as id, NCAPBIL as numCapBilancio,IMPSPE impegniSpesa,RG1CB as importoRisFinanz,\r\n"
            + "IMPRFSCB as importoRisFinanzStato,IMPALTCB as importoRisFinanzAltro, VERIFBIL as verificaCoerenza,DV1CB as entrateDestVincLegge1,DV2CB as entrateDestVincLegge2,\r\n"
            + "DV3CB as entrateDestVincLegge3,DV9CB as entrateDestVincLeggeSucc, MU1CB as entrateContrMutuo1 ,MU2CB as entrateContrMutuo2, MU3CB as entrateContrMutuo3,\r\n"
            + "MU9CB as entrateContrMutuoSucc,BI1CB as stanziamentiBilancio1,BI2CB  as stanziamentiBilancio2,BI3CB  as stanziamentiBilancio3,BI9CB as stanziamentiBilancioSucc,\r\n"
            + "AP1CB as impFinanz1, AP2CB as impFinanz2,AP3CB as impFinanz3,AP9CB as impFinanzSucc, AL1CB as altreRisDisp1,AL2CB as altreRisDisp2,AL3CB as altreRisDisp3,\r\n"
            + "AL9CB  as altreRisDispSucc, TO1CB as importoComplessivo1,TO2CB as importoComplessivo2, TO3CB as importoComplessivo3, TO9CB  as importoComplessivoSucc, \r\n"
            + "IV1CB as importoIva1,IV2CB as importoIva2,IV3CB as importoIva3, IV9CB as importoIvaSucc ,SPESESOST as speseSostenute, cbnote as note from ris_capitolo where contri = #{contri} and conint = #{conint} and numcb = #{numcb}")
    public RisorsaCapitoloEntry getRisorsaDiCapitolo(@Param("contri") Long contri, @Param("conint") Long conint,
                                                     @Param("numcb") Long numcb);

    @Insert("insert into ris_capitolo (contri, conint, numcb, ncapbil,impspe,rg1cb,imprfscb,impaltcb,verifbil,dv1cb,dv2cb,dv3cb,dv9cb,mu1cb,mu2cb,mu3cb,mu9cb,bi1cb,bi2cb,bi3cb,bi9cb,ap1cb,ap2cb,ap3cb,ap9cb,al1cb,al2cb,al3cb,al9cb,to1cb, to2cb, to3cb, to9cb,iv1cb,iv2cb,iv3cb,iv9cb,spesesost,cbnote ) values (#{idProgramma}, #{idIntervento}, #{id}, #{numCapBilancio},#{impegniSpesa},#{importoRisFinanz},#{importoRisFinanzStato},#{importoRisFinanzAltro},#{verificaCoerenza},#{entrateDestVincLegge1},#{entrateDestVincLegge2},#{entrateDestVincLegge3},#{entrateDestVincLeggeSucc},#{entrateContrMutuo1},#{entrateContrMutuo2},#{entrateContrMutuo3},#{entrateContrMutuoSucc},#{stanziamentiBilancio1},#{stanziamentiBilancio2},#{stanziamentiBilancio3},#{stanziamentiBilancioSucc},#{impFinanz1},#{impFinanz1},#{impFinanz1},#{impFinanzSucc},#{altreRisDisp1},#{altreRisDisp2},#{altreRisDisp3},#{altreRisDispSucc},#{importoComplessivo1}, #{importoComplessivo2}, #{importoComplessivo3}, #{importoComplessivoSucc}, #{importoIva1},#{importoIva2},#{importoIva3},#{importoIvaSucc},#{speseSostenute},#{note})")
    public void insertRisorsaDiCapitolo(RisorsaDiCapitoloInsertForm form);

    @Insert("insert into ris_capitolo (contri, conint, numcb, ncapbil,impspe,rg1cb,imprfscb,impaltcb,verifbil,dv1cb,dv2cb,dv3cb,dv9cb,mu1cb,mu2cb,mu3cb,mu9cb,bi1cb,bi2cb,bi3cb,bi9cb,ap1cb,ap2cb,ap3cb,ap9cb,al1cb,al2cb,al3cb,al9cb,to1cb, to2cb, to3cb, to9cb,iv1cb,iv2cb,iv9cb,spesesost,cbnote ) values (#{idProgramma}, #{idIntervento}, #{id}, #{numCapBilancio},#{impegniSpesa},#{importoRisFinanz},#{importoRisFinanzStato},#{importoRisFinanzAltro},#{verificaCoerenza},#{entrateDestVincLegge1},#{entrateDestVincLegge2},#{entrateDestVincLegge3},#{entrateDestVincLeggeSucc},#{entrateContrMutuo1},#{entrateContrMutuo2},#{entrateContrMutuo3},#{entrateContrMutuoSucc},#{stanziamentiBilancio1},#{stanziamentiBilancio2},#{stanziamentiBilancio3},#{stanziamentiBilancioSucc},#{impFinanz1},#{impFinanz1},#{impFinanz1},#{impFinanzSucc},#{altreRisDisp1},#{altreRisDisp2},#{altreRisDisp3},#{altreRisDispSucc},#{importoComplessivo1}, #{importoComplessivo2}, #{importoComplessivo3}, #{importoComplessivoSucc}, #{importoIva1},#{importoIva2},#{importoIvaSucc},#{speseSostenute},#{note})")
    public void importRisorsaDiCapitolo(RisorsaCapitoloEntry form);

    @Update("update ris_capitolo set ncapbil = #{numCapBilancio},impspe = #{impegniSpesa},rg1cb = #{importoRisFinanz},imprfscb = #{importoRisFinanzStato},impaltcb = #{importoRisFinanzAltro},verifbil = #{verificaCoerenza},dv1cb = #{entrateDestVincLegge1},dv2cb = #{entrateDestVincLegge2},dv3cb = #{entrateDestVincLegge3},dv9cb = #{entrateDestVincLeggeSucc},mu1cb = #{entrateContrMutuo1},mu2cb = #{entrateContrMutuo2},mu3cb = #{entrateContrMutuo3},mu9cb = #{entrateContrMutuoSucc},bi1cb = #{stanziamentiBilancio1},bi2cb = #{stanziamentiBilancio2},bi3cb = #{stanziamentiBilancio3},bi9cb = #{stanziamentiBilancioSucc},ap1cb = #{impFinanz1},ap2cb = #{impFinanz2},ap3cb = #{impFinanz3},ap9cb = #{impFinanzSucc},al1cb = #{altreRisDisp1},al2cb = #{altreRisDisp2},al3cb = #{altreRisDisp3},al9cb = #{altreRisDispSucc},to1cb = #{importoComplessivo1}, to2cb = #{importoComplessivo2}, to3cb = #{importoComplessivo3}, to9cb = #{importoComplessivoSucc},iv1cb = #{importoIva1},iv2cb = #{importoIva2},iv3cb = #{importoIva3},iv9cb = #{importoIvaSucc},spesesost= #{speseSostenute},cbnote=#{note} where contri = #{idProgramma} and conint = #{idIntervento} and numcb = #{id} ")
    public void updateRisorsaDiCapitolo(RisorsaDiCapitoloInsertForm form);

    @Delete("delete from ris_capitolo where contri = #{contri}")
    public void deleteRisorseDiCapitoloProgramma(@Param("contri") Long contri);

    @Delete("delete from ris_capitolo where contri = #{contri} and conint = #{conint}")
    public void deleteRisorseDiCapitoloIntervento(@Param("contri") Long contri, @Param("conint") Long conint);

    @Delete("delete from ris_capitolo where contri = #{contri} and conint = #{conint} and numcb = #{numcb}")
    public void deleteRisorsaDiCapitolo(@Param("contri") Long contri, @Param("conint") Long conint,
                                        @Param("numcb") Long numcb);

    @Select("select max(numcb) from ris_capitolo where contri = #{contri} and conint = #{conint}")
    public Long getMaxNumcb(@Param("contri") Long contri, @Param("conint") Long conint);

    /**
     * AGGIORNAMENTI COSTI PIATRI
     */

    public Double sommaValoriIntervento(@Param("contri") Long contri, @Param("column") String column);

    @Update("UPDATE PIATRI SET DV1TRI =#{dv1tri}, DV2TRI =#{dv2tri}, DV3TRI =#{dv3tri},IM1TRI =#{im1tri}, IM2TRI =#{im2tri}, IM3TRI =#{im3tri}, MU1TRI =#{mu1tri}, MU2TRI =#{mu2tri}, MU3TRI =#{mu3tri}, PR1TRI =#{pr1tri}, PR2TRI =#{pr2tri}, PR3TRI =#{pr3tri}, AL1TRI =#{al1tri}, AL2TRI =#{al2tri}, AL3TRI =#{al3tri},AP1TRI =#{ap1tri}, AP2TRI =#{ap2tri}, AP3TRI =#{ap3tri}, BI1TRI =#{bi1tri}, BI2TRI =#{bi2tri}, BI3TRI =#{bi3tri}, TO1TRI =#{di1int}, TO2TRI =#{di2int}, TO3TRI =#{di3int}, RG1TRI =#{rg1tri}, IMPRFS =#{imprfs} WHERE CONTRI =#{contri}")
    public void updateCostiPiatri(CostiProgrammiUpdateForm form);

    /**
     * AGGIORNAMENTI COSTI INTTRI
     */

    public Double sommaValoriCapitolo(@Param("contri") Long contri, @Param("conint") Long conint,
                                      @Param("column") String column);

    @Update("update inttri set al1tri =#{al1tri}, al2tri =#{al2tri}, al3tri =#{al3tri}, al9tri=#{al9tri}, ap1tri =#{ap1tri}, ap2tri =#{ap2tri}, ap3tri =#{ap3tri}, ap9tri=#{ap9tri}, bi1tri =#{bi1tri}, bi2tri =#{bi2tri}, bi3tri =#{bi3tri}, bi9tri=#{bi9tri}, dv1tri =#{dv1tri}, dv2tri =#{dv2tri}, dv3tri =#{dv3tri}, dv9tri=#{dv9tri}, mu1tri =#{mu1tri}, mu2tri =#{mu2tri}, mu3tri =#{mu3tri}, mu9tri=#{mu9tri}, iv1tri =#{iv1tri}, iv2tri =#{iv2tri}, iv3tri =#{iv3tri}, iv9tri=#{iv9tri}, impalt =#{impalt}, imprfs =#{imprfs}, rg1tri=#{rg1tri}, spesesost=#{spesesost} where contri =#{contri} and conint=#{conint}")
    public void updateCostiInttri(CostiInterventiUpdateForm form);

    @Update("UPDATE INTTRI SET DI1INT = COALESCE(BI1TRI,0) + COALESCE(DV1TRI,0) + COALESCE(IM1TRI,0) + COALESCE(MU1TRI,0) + COALESCE(AL1TRI,0) + COALESCE(AP1TRI,0), DI2INT = COALESCE(BI2TRI,0) + COALESCE(DV2TRI,0) + COALESCE(IM2TRI,0) + COALESCE(MU2TRI,0) + COALESCE(AL2TRI,0) + COALESCE(AP2TRI,0), DI3INT = COALESCE(BI3TRI,0) + COALESCE(DV3TRI,0) + COALESCE(IM3TRI,0) + COALESCE(MU3TRI,0) + COALESCE(AL3TRI,0) + COALESCE(AP3TRI,0), DI9INT = COALESCE(BI9TRI,0) + COALESCE(DV9TRI,0) + COALESCE(MU9TRI,0) + COALESCE(AL9TRI,0) + COALESCE(AP9TRI,0) where CONTRI=#{contri} and CONINT= #{conint}")
    public void UpdateParzialiInttri(@Param("contri") Long contri, @Param("conint") Long conint);

    @Update("UPDATE INTTRI SET DITINT = COALESCE(DI1INT,0) + COALESCE(DI2INT,0) + COALESCE(DI3INT,0) + COALESCE(DI9INT,0), ICPINT = COALESCE(PR1TRI,0) + COALESCE(PR2TRI,0) + COALESCE(PR3TRI,0) + COALESCE(PR9TRI,0), TOTINT = COALESCE(DI1INT,0) + COALESCE(DI2INT,0) + COALESCE(DI3INT,0) + COALESCE(DI9INT,0) + COALESCE(PR1TRI,0) + COALESCE(PR2TRI,0) + COALESCE(PR3TRI,0) + COALESCE(PR9TRI,0) + COALESCE(SPESESOST,0) where contri = #{contri} and CONINT= #{conint}")
    public void UpdateTotaliInttri(@Param("contri") Long contri, @Param("conint") Long conint);

    /**
     * OPERAZIONI TRASVERSALI
     */

    public Long getMaxConint(@Param("contri") Long contri, @Param("table") String table);

    @Insert("INSERT into W9FLUSSI(IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, CODCOMP, CFSA, AUTORE, XML)"
            + " VALUES(#{id}, #{area}, #{key01}, #{key02}, #{key03}, #{key04}, #{tipoInvio}, #{dataInvio}, #{note}, #{idAutore}, #{codiceFiscaleSA}, #{autore}, #{json})")
    public void insertFlusso(FlussoEntry flusso);

    @Delete("delete from W9FLUSSI where key01 = #{idProgramma} and area = 4 and key03 = 982")
    public void deleteFlussoProgramma(@Param("idProgramma") Long idProgramma);

    @Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE codtec =#{codiceRup}")
    public RupEntry getRup(@Param("codiceRup") String codiceRup);

    @Select("select id as id, codein as stazioneAppaltante, denom as denominazione from uffici where id = #{idUfficio}")
    public UffEntry getUff(@Param("idUfficio") Long idUfficio);

    @Select("select idflusso as idFlusso,key01 as contri, autore as autore, noteinvio as noteInvio, DATINV as dataInvio, xml from W9FLUSSI  where CFSA= #{stazioneAppaltante} and area = 4 and key01 = #{contri} order by idflusso asc")
    public List<FlussiProgrammi> getPubblicazioni(@Param("stazioneAppaltante") String stazioneAppaltante,
                                                  @Param("contri") Long contri);

    /**
     * PUBBLICAZIONI
     */

    @Select("select contri as idProgramma, conint as id, annrif as annoAvvio, nprogint as numProgetto, cuiint as numeroCui, desint as descrizione, totint as importoTotale,\r\n"
            + "	annint as inPianoAnnuale, tipint as tipologiaIntervento, tipoin as settore, codint as codiceInterno, primann as annoInsAcquisto, mesein as meseAvvioProc,\r\n"
            + "	flag_cup as esenteCup, cupprg as codiceCup, acqaltint as acquistoRicompreso, cuicoll as cuiCollegato, comint as codIstatComune, nuts as codiceNuts, \r\n"
            + "	codcpv as codCpv, spesesost as speseSostenute, quantit as quantita, unimis as unitaMisura, prgint as livelloPriorita, codrup as codiceRup, \r\n"
            + "	dirgen as direzioneGenerale, struop as strutturaOperativa, respuf as dirigenteUfficio, lotfunz as lottoFunzionale, lavcompl as lavoroComplesso, \r\n"
            + "	durcont as durataContratto, contess as nuovoAffidamento, sezint as classificazioneIntervento, interv as categoriaIntervento, scamut as scadenzaUtilizzoFinanziamento,\r\n"
            + "	fiintr as finalitaIntervento, urcint as verificaConfUrbanistica, apcint as verificaConfAmbiente, stapro as statoProgettazioneApprovata, \r\n"
            + "	dv1tri as impDestVincolo1, dv2tri as impDestVincolo2, dv3tri as impDestVincolo3, dv9tri as impDestVincoloSucc, iv1tri as importoIva1,iv2tri as importoIva2,iv3tri as importoIva3,\r\n"
            + "	iv9tri  as importoIvaSucc, mu1tri as importoAcqMututo1, mu2tri as importoAcqMututo2, mu3tri as importoAcqMututo3, mu9tri as importoAcqMututoSucc, \r\n"
            + "	pr1tri as importoCapPriv1, pr2tri as importoCapPriv2, pr3tri as importoCapPriv3, pr9tri as importoCapPrivSucc, bi1tri as stanziamentiBilancio1, \r\n"
            + "	bi2tri as stanziamentiBilancio2, bi3tri as stanziamentiBilancio3, bi9tri as stanziamentiBilancioSucc, ap1tri as importoFinanz1, ap2tri as importoFinanz2,\r\n"
            + "	ap3tri  as importoFinanz3, ap9tri  as importoFinanzSucc, im1tri as trasfImmobili1, im2tri as trasfImmobili2, im3tri as trasfImmobili3,\r\n"
            + "	im9tri as trasfImmobiliSucc, al1tri as altreRisorseDisp1, al2tri as altreRisorseDisp2, al3tri as altreRisorseDisp3, al9tri as altreRisorseDispSucc, \r\n"
            + "	di1int as importoDispPriv1, di2int as importoDispPriv2, di3int as importoDispPriv3, di9int as importoDispPrivSucc, \r\n"
            + "	tcpint as tipologiaCapitalePrivato, rg1tri as importoRisorseRegionali, imprfs as importoRisorseFinanz, impalt as importoRisorseFinanzAltro, \r\n"
            + "	copfin as coperturaFinanziaria, acqverdi as acqVerdi, norrif as normativaRiferimento, avoggett as oggettoAv, avcpv as avcpv, avimpnet as importoAlnettoIvaAv,\r\n"
            + "	aviva as importoIvaAv, avimptot as importoTotaleAv, matric as acquistoBeniRiciclati, mroggett as oggettoMr, mrcpv as mrcpv, mrimpnet as importoAlnettoIvaMr, \r\n"
            + "	mriva as importoIvaMr, mrimptot as importoTotaleMr, proaff as proceduraAffidamento, delega as delegaProcAff, codausa as codiceAusa, sogagg as soggettoDelegato,\r\n"
            + "	variato as variato, refere as referenteDatiComunicati, valutazione as valutazioneResp, ditint as totImpDispFin,icpint as totaleCapitalePriv, CIG_ACCQUADRO as cigAccordoQuadro, \r\n"
            + "	intnote as note from INTTRI \r\n" + "	where CONTRI = #{contri}")
    public List<InterventoEntry> getInterventiProgramma(@Param("contri") Long contri);

    @Select("select contri as idProgramma,conint as idIntervento,cuiint as cui,desint as descrizione,cupprg as cup,totint as importoComplessivo,prgint as priorita,motivo as motivo from INRTRI where contri = #{contri}")
    public List<InterventoNonRipropostoEntry> getInterventiNRProgramma(@Param("contri") Long contri);

    @Select("select contri as idProgramma, numoi as id, cup as cup, descrizione as descrizione,determinazioni as determinazioni, ambitoint as ambitoInt,annoultqe as annoUltimoQe,importoint as importoInt,importolav as importoLav,importosal as importoSal, oneriultim as oneriUlt,avanzamento as avanzamento,causa as causa,statoreal as statoReal,infrastruttura as infrastruttura,fruibile as fruibile,utilizzorid as utilizzoRid,destinazioneuso as destinazioneUso,cessione as cessione, vendita as vendita,demolizione as demolizione, oneri_sito as oneriSito, istat as codIstat, nuts as codNuts,sezint as tipologiaIntervento,interv as categoriaIntervento,req_cap as requisitiCapitolato,req_prge as requisitiProgetto,dim_um as dimensionamentoUm,dim_qta as dimensionamentoValore,sponsorizzazione as sponsorizzazione,finanza_progetto as finanzaProgetto,costo as costo,finanziamento as finanziamento, cop_statale as copStatale,cop_regionale as copRegionale,cop_provinciale as copProvinciale,cop_comunale as copComunale,cop_altrapubblica as copAltraPubblica,cop_comunitaria as copComunitaria,cop_privata as copPrivata, discontinuita_rete as discontinuitaRete from oitri where contri = #{contri}")
    public List<OperaIncompiutaEntry> getOpereIncompiuteProgramma(@Param("contri") Long contri);

    @Select("select codein from usr_ein where syscon = #{syscon}")
    public List<String> getSAList(@Param("syscon") Long syscon);

    @Select("select count(ue.codein) from usr_ein ue inner join usrsys u on ue.syscon = u.syscon where u.syscon = #{syscon} and ue.codein = #{codein}")
    Long checkUserSaPermission(@Param("syscon") final Long syscon, @Param("codein") final String codein);

    @Select("select cfein from uffint where codein =#{stazioneappaltante}")
    public String getCFSAByCode(@Param("stazioneappaltante") String stazioneappaltante);


    @Select("select sysab3 from usrsys where syscon = #{syscon}")
    public String getRuolo(@Param("syscon") Long syscon);

    @Select("select sysuffapp from usrsys where syscon = #{syscon}")
    public String getCentroCosto(@Param("syscon") Long syscon);

    @Select("select denom from uffici where id = #{idUfficio}")
    public String getDenomUfficio(@Param("idUfficio") String idUfficio);

    @Select("select id from uffici where codein = #{stazioneAppaltante} and ccprog = '1'")
    public List<Long> getCentroCostoBySA(@Param("stazioneAppaltante") String stazioneAppaltante);

    @Select("select codtec from tecni where syscon = #{syscon} and cgentei = #{stazioneappaltante}")
    public String getCodiceForSA(@Param("syscon") Long syscon, @Param("stazioneappaltante") String stazioneappaltante);

    @Update("update piatri set syscon = #{syscon} where contri = #{contri} and cenint = #{stazioneAppaltante}")
    public void updateSysconProgramma(@Param("syscon") Long syscon,
                                      @Param("stazioneAppaltante") String stazioneAppaltante, @Param("contri") Long contri);

    @Select("select i.cuiint from inttri i inner join piatri p on i.contri = p.contri where p.cenint = #{stazioneAppaltante} and lower(i.cuiint) like '%'|| #{desc} ||'%' and i.cuiint is not null")
    public List<String> getCuiOptions(CuiSearchForm form);

    public List<ProgrammaBaseEntry> getListaProgrammiNonPaginata(final ProgrammiNonPaginatiSearchForm searchForm);

    @Select("select id from piatri where anntri = #{annoInizio} and idufficio = #{idUfficio}  and cenint = #{stazioneAppaltante} and tiprog = #{tipoProg}")
    public List<String> getListaIdProgrammi(@Param("annoInizio") Long annoInizio, @Param("idUfficio") Long idUfficio, @Param("stazioneAppaltante") String stazioneAppaltante, @Param("tipoProg") Long tipoProg);

    @Select("select id from piatri where anntri = #{annoInizio} and idufficio is null and cenint = #{stazioneAppaltante} and tiprog = #{tipoProg}")
    public List<String> getListaIdProgrammiSenzaUfficio(@Param("annoInizio") Long annoInizio,  @Param("stazioneAppaltante") String stazioneAppaltante, @Param("tipoProg") Long tipoProg);

    @Select("select amministrazione, codiceFiscaleEnte, codiceIPA, ufficio, dipartimento, regione, provincia, indirizzoEnte, telefonoEnte, mailEnte, pecEnte, "
            + " nomeReferente, cognomeReferente, codiceFiscaleReferente, telefonoReferente, mailReferente, "
            + " numeroInterventoCUI, "
            + " codiceFiscaleAmministrazione, "
            + " primaAnnualita, "
            + " anno, "
            + " codiceCUP, "
            + " acquistoRicompreso, "
            + " cuiLavoro, "
            + " lottoFunzionale, "
            + " ambitoGeografico, "
            + " settore, "
            + " CPV,  "
            + " descrizioneAcquisto,  "
            + " priorita,  "
            + " cfResponsabileProcedimento, "
            + " durataContratto, "
            + " contrattoInEssere, "
            + " stimaCostiProgrammaPrimoAnno,  "
            + " stimaCostiProgrammaSecondoAnno, "
            + " stimaCostiProgrammaTerzoAnno, "
            + " costiAnnualitaSuccessive,  "
            + " stimaCostiProgrammaTotale,  "
            + " importoApportoCapitalePrivato,  "
            + " tipoApportoCapitalePrivato,  "
            + " codiceAUSA,  "
            + " denomAmministrazioneDelegata, "
            + " aquistoAggVar "
            + " from v_report_sogg_aggr where contri = #{idProgramma}")
    public List<ExportSogg> getSoggettiAggregatori(@Param("idProgramma") Long idProgramma);

    @Select("select username, password from usrcredest where syscon = #{syscon} and codsistema = 'WSCUP'")
    public CredenzialiSimog getSimogCredentials(@Param("syscon") Long syscon);

    @Insert("insert into usrcredest(username, password, codsistema, syscon)  values (#{user}, #{password}, 'WSCUP' ,#{syscon})")
    public void insertCredenzialiSimog(@Param("user") String user, @Param("password") String password, @Param("syscon") Long syscon);

    @Update("update usrcredest set username = #{user}, password = #{password} where syscon = #{syscon} and codsistema = 'WSCUP'")
    public void updateCredenzialiSimog(@Param("user") String user, @Param("password") String password, @Param("syscon") Long syscon);

    List<ExportInterventiQueryResult> exportListaInterventiFiltrata(@Param("contri") final Long contri, InterventiSearchForm searchForm);

    List<ExportAcquistiQueryResult> exportListaAcquistiFiltrata(@Param("contri") final Long contri, InterventiSearchForm searchForm);

    List<ExportInterventiAcquistiNonRipropostiQueryResult> exportListaInterventiAcquistiNonRiproposti(@Param("contri") final Long contri);

    @Select("select contri as id, id as idProgramma, anntri as annoInizio, bretri as descrizioneBreve, tiprog as tipoProg, syscon, norma from piatri where tiprog = #{tipoProgramma} and cenint = #{codiceStazioneAppaltante} and anntri = #{annoInizio} and contri <> #{contri} order by contri desc")
    List<ProgrammaBaseEntry> getListaProgrammiAnnoAttualeDaConfronto(@Param("tipoProgramma") final Long tipoProgramma, @Param("codiceStazioneAppaltante") final String codiceStazioneAppaltante, @Param("annoInizio") final Long annoInizio, @Param("contri") final Long contri);

    @Select("select contri as id, id as idProgramma, anntri as annoInizio, bretri as descrizioneBreve, tiprog as tipoProg, syscon, norma from piatri where tiprog = #{tipoProgramma} and cenint = #{codiceStazioneAppaltante} and anntri = #{annoInizio} order by contri desc")
    List<ProgrammaBaseEntry> getListaProgrammiAnnoPrecedenteDaConfronto(@Param("tipoProgramma") final Long tipoProgramma, @Param("codiceStazioneAppaltante") final String codiceStazioneAppaltante, @Param("annoInizio") final Long annoInizio);

    List<InterventiDaConfrontoQueryResult> exportListaInterventiDaConfronto(@Param("contri") final Long contri);

    @Select("SELECT COUNT(*) FROM INTTRI WHERE contri = #{contri}")
    Long countInterventiByContri(@Param("contri") final Long contri);

    @Select("SELECT tab1desc FROM tab1 WHERE tab1cod = #{tab1cod} and tab1tip = #{tab1tip}")
    String findTab1ValueFromCodAndTip(@Param("tab1cod") final String tab1, @Param("tab1tip") final Long tab1tip);

    @Select("SELECT syspwbou from usrsys where syscon = #{syscon}")
    public String getAbilitazioniUtente(@Param("syscon") Long syscon);
    
	@Select("select norma from piatri where id = #{id}")
    public Long getProgrammaNormaById(@Param("id") String id);

	@Update("UPDATE PIATRI SET FILE_ALLEGATO = #{file} WHERE ID_GENERATO=#{nrDoc}")
	public void setPdf(AllegatoEntry pdf);
}