package it.appaltiecontratti.inbox.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import it.appaltiecontratti.inbox.entity.contratti.RupEntry;
import it.appaltiecontratti.inbox.entity.programmi.ImmobileEntry;
import it.appaltiecontratti.inbox.entity.programmi.InterventoEntry;
import it.appaltiecontratti.inbox.entity.programmi.InterventoNonRipropostoEntry;
import it.appaltiecontratti.inbox.entity.programmi.OperaIncompiutaEntry;
import it.appaltiecontratti.inbox.entity.programmi.ProgrammaEntry;

@Mapper
public interface ProgrammiMapper {

	@Select("select id as idProgramma ,contri as id ,norma as norma ,cenint as stazioneAppaltante,idufficio as idUfficio, tiprog tipoProg ,bretri as descrizioneBreve ,anntri as annoInizio, \r\n"
			+ "	pubblica as pubblica,respro as responsabile ,tadozi tipoAtto, nadozi as numeroAtto,dpadozi as dataPubblicazioneAdo ,dadozi as dataAtto ,titadozi as titoloAdozione,urladozi urlAdozione,\r\n"
			+ "	taptri as tipoApprovazione,naptri as numeroApprovazione, dpapprov as dataPubblicazioneAppr ,daptri as dataApprovazione, titapprov as titoloApprovazione, urlapprov as urlApprovazione,\r\n"
			+ "	dv1tri as importoDestVincolo1, dv2tri importoDestVincolo2, dv3tri importoDestVincolo3, im1tri as importoTrasfImmobile1, im2tri as importoTrasfImmobile2, im3tri as importoTrasfImmobile3,\r\n"
			+ "	mu1tri as importoAcquistoMutuo1, mu2tri as importoAcquistoMutuo2, mu3tri as importoAcquistoMutuo3, pr1tri as importoCapitalePr1, pr2tri as importoCapitalePr2, pr3tri as importoCapitalePr3,\r\n"
			+ "	al1tri as importoAltreRis1, al2tri as importoAltreRis2, al3tri as importoAltreRis3, ap1tri as importoFinanz1, ap2tri as importoFinanz2, ap3tri as importoFinanz3, \r\n"
			+ "	bi1tri as importoStanzBilanciamento1, bi2tri as importoStanzBilanciamento2, bi3tri as importoStanzBilanciamento3, to1tri as totaleRisDisp1, to2tri  as totaleRisDisp2, \r\n"
			+ "	to3tri  as totaleRisDisp3, rg1tri as importoRisorseFinReg, imprfs as importoRisorseFinStato, id_ricevuto as idRicevuto, syscon as syscon from piatri where contri = #{contri}")
	public ProgrammaEntry getProgramma(@Param("contri") Long contri);

	@Select("select cfein from uffint where codein =#{stazioneappaltante}")
	public String getCFSAByCode(@Param("stazioneappaltante") String stazioneappaltante);

	@Select("select contri as idProgramma, conint as id, annrif as annoAvvio, nprogint as numProgetto, cuiint as numeroCui, desint as descrizione, totint as importoTotale,\r\n"
			+ "	annint as inPianoAnnuale, tipint as tipologiaIntervento, tipoin as settore, codint as codiceInterno, primann as annoInsAcquisto, mesein as meseAvvioProc,\r\n"
			+ "	flag_cup as esenteCup, cupprg as codiceCup, acqaltint as acquistoRicompreso, cuicoll as cuiCollegato, comint as codIstatComune, nuts as codiceNuts, \r\n"
			+ "	codcpv as codCpv, spesesost as speseSostenute, quantit as quantita, unimis as unitaMisura, prgint as livelloPriorita, codrup as codiceRup, \r\n"
			+ "	dirgen as direzioneGenerale, struop as strutturaOperativa, respuf as dirigenteUfficio, lotfunz as lottoFunzionale, lavcompl as lavoroComplesso, \r\n"
			+ "	durcont as durataContratto, contess as nuovoAffidamento, sezint as classificazioneIntervento, interv as categoriaIntervento, scamut as scadenzaUtilizzoFinanziamento,\r\n"
			+ "	fiintr as finalitaIntervento, urcint as verificaConfUrbanistica, apcint as verificaConfAmbiente, stapro as statoProgettazioneApprovata, \r\n"
			+ "	dv1tri as impDestVincolo1, dv2tri as impDestVincolo2, dv3tri as impDestVincolo3, dv9tri as impDestVincoloSucc, iv1tri as importoIva1,iv2tri as importoIva2, iv3tri as importoIva3,\r\n"
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
			+ "	cig_accquadro as cigAccordoQuadro, variato as variato, refere as referenteDatiComunicati, valutazione as valutazioneResp, ditint as totImpDispFin,icpint as totaleCapitalePriv, \r\n"
			+ "	intnote as note from INTTRI \r\n" + "	where CONTRI = #{contri}")
	public List<InterventoEntry> getInterventiProgramma(@Param("contri") Long contri);
	
	@Select("select contri as idProgramma,conint as idIntervento,cuiint as cui,desint as descrizione,cupprg as cup,totint as importoComplessivo,prgint as priorita,motivo as motivo from INRTRI where contri = #{contri}")
	public List<InterventoNonRipropostoEntry> getInterventiNRProgramma(@Param("contri")Long contri);

    @Select("select contri as idProgramma, numoi as id, cup as cup, descrizione as descrizione,determinazioni as determinazioni, ambitoint as ambitoInt,annoultqe as annoUltimoQe,importoint as importoInt,importolav as importoLav,importosal as importoSal, oneriultim as oneriUlt,avanzamento as avanzamento,causa as causa,statoreal as statoReal,infrastruttura as infrastruttura,fruibile as fruibile,utilizzorid as utilizzoRid,destinazioneuso as destinazioneUso,cessione as cessione, vendita as vendita,demolizione as demolizione, oneri_sito as oneriSito, istat as codIstat, nuts as codNuts,sezint as tipologiaIntervento,interv as categoriaIntervento,req_cap as requisitiCapitolato,req_prge as requisitiProgetto,dim_um as dimensionamentoUm,dim_qta as dimensionamentoValore,sponsorizzazione as sponsorizzazione,finanza_progetto as finanzaProgetto,costo as costo,finanziamento as finanziamento, cop_statale as copStatale,cop_regionale as copRegionale,cop_provinciale as copProvinciale,cop_comunale as copComunale,cop_altrapubblica as copAltraPubblica,cop_comunitaria as copComunitaria,cop_privata as copPrivata, discontinuita_rete as discontinuitaRete from oitri where contri = #{contri}")
	public List<OperaIncompiutaEntry> getOpereIncompiuteProgramma(@Param("contri")Long contri);
    
    public List<ImmobileEntry> getImmobili(@Param("contri") Long contri, @Param("conint") Long conint, @Param("numoi") Long numoi);
	
    @Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE codtec =#{codiceRup}" )
	public RupEntry getRup(@Param("codiceRup")String codiceRup);
    
    @Update("UPDATE PIATRI SET ID_RICEVUTO = #{idRicevuto} WHERE CONTRI = #{contri} ")
    public void updateIdRicevutoProgramma(@Param("idRicevuto")Long idRicevuto, @Param("contri")Long contri);

}
