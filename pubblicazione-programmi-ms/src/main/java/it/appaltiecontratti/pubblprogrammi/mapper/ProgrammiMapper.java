package it.appaltiecontratti.pubblprogrammi.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import it.appaltiecontratti.pubblprogrammi.entity.AcquistoEntry;
import it.appaltiecontratti.pubblprogrammi.entity.AcquistoNonRipropostoEntry;
import it.appaltiecontratti.pubblprogrammi.entity.AllegatoEntry;
import it.appaltiecontratti.pubblprogrammi.entity.AltriDatiOperaIncompiutaEntry;
import it.appaltiecontratti.pubblprogrammi.entity.DatiGeneraliTecnicoEntry;
import it.appaltiecontratti.pubblprogrammi.entity.FlussoEntry;
import it.appaltiecontratti.pubblprogrammi.entity.ImmobileEntry;
import it.appaltiecontratti.pubblprogrammi.entity.InterventoEntry;
import it.appaltiecontratti.pubblprogrammi.entity.InterventoNonRipropostoEntry;
import it.appaltiecontratti.pubblprogrammi.entity.OperaIncompiutaEntry;
import it.appaltiecontratti.pubblprogrammi.entity.PubblicaProgrammaFornitureServiziEntry;
import it.appaltiecontratti.pubblprogrammi.entity.PubblicaProgrammaLavoriEntry;

/**
 * DAO Interface per l'estrazione delle informazioni relative agli avvisi
 * mediante MyBatis.
 * 
 * @author Michele.DiNapoli
 */
@Mapper
public interface ProgrammiMapper {

	/**
	 * Inserisce le informazioni per la pubblicazione di un programma.
	 */

	@Insert("INSERT INTO PIATRI (CONTRI,ID,NORMA,ANNTRI,BRETRI,CENINT,IDUFFICIO,TIPROG,NAPTRI,DAPTRI,DPAPPROV,TITAPPROV,URLAPPROV,NADOZI,DADOZI,DPADOZI,TITADOZI,URLADOZI,RESPRO,ID_GENERATO,ID_CLIENT,SYSCON) "
			+ "VALUES (#{contri},#{id},#{norma},#{anno},#{descrizione},#{codiceSA},#{idUfficio},1,#{numeroApprovazione},#{dataApprovazione},#{dataPubblicazioneApprovazione},#{titoloAttoApprovazione},#{urlAttoApprovazione}," 
			+ "#{numeroAdozione},#{dataAdozione},#{dataPubblicazioneAdozione},#{titoloAttoAdozione},#{urlAttoAdozione},#{codiceReferente},#{idRicevuto},#{clientId},#{syscon})")
			public void pubblicaProgrammaLavori(PubblicaProgrammaLavoriEntry programma);

	@Update("UPDATE PIATRI SET ID=#{id},ANNTRI=#{anno},BRETRI=#{descrizione},CENINT=#{codiceSA}," +
			"NAPTRI=#{numeroApprovazione},DAPTRI=#{dataApprovazione},DPAPPROV=#{dataPubblicazioneApprovazione},TITAPPROV=#{titoloAttoApprovazione},URLAPPROV=#{urlAttoApprovazione},IDUFFICIO = #{idUfficio}," +
			"NADOZI=#{numeroAdozione},DADOZI=#{dataAdozione},DPADOZI=#{dataPubblicazioneAdozione},TITADOZI=#{titoloAttoAdozione},URLADOZI=#{urlAttoAdozione},RESPRO=#{codiceReferente},SYSCON=#{syscon} " +
			" WHERE CONTRI=#{contri} AND ID_GENERATO=#{idRicevuto}" )
			public void modificaPubblicazioneProgrammaLavori(PubblicaProgrammaLavoriEntry programma);

	
	@Insert("INSERT INTO PIATRI (CONTRI,ID,NORMA,ANNTRI,BRETRI,CENINT,IDUFFICIO,TIPROG,NAPTRI,DAPTRI,DPAPPROV,TITAPPROV,URLAPPROV,RESPRO,ID_GENERATO,ID_CLIENT,SYSCON) "
			+ "VALUES (#{contri},#{id},#{norma},#{anno},#{descrizione},#{codiceSA},#{idUfficio},2,#{numeroApprovazione},#{dataApprovazione},#{dataPubblicazioneApprovazione},#{titoloAttoApprovazione},#{urlAttoApprovazione}," 
			+ "#{codiceReferente},#{idRicevuto},#{clientId},#{syscon})")
			public void pubblicaProgrammaFornitureServizi(PubblicaProgrammaFornitureServiziEntry programma);

	@Update("UPDATE PIATRI SET ID=#{id},ANNTRI=#{anno},BRETRI=#{descrizione},CENINT=#{codiceSA}," +
			"NAPTRI=#{numeroApprovazione},DAPTRI=#{dataApprovazione},DPAPPROV=#{dataPubblicazioneApprovazione},TITAPPROV=#{titoloAttoApprovazione},URLAPPROV=#{urlAttoApprovazione},IDUFFICIO = #{idUfficio}," +
			"RESPRO=#{codiceReferente},SYSCON=#{syscon} " +
			" WHERE CONTRI=#{contri} AND ID_GENERATO=#{idRicevuto}" )
			public void modificaPubblicazioneProgrammaFornitureServizi(PubblicaProgrammaFornitureServiziEntry programma);

	
    @Insert("INSERT into W9FLUSSI(IDFLUSSO, AREA, KEY01, KEY02, KEY03, KEY04, TINVIO2, DATINV, NOTEINVIO, CODCOMP, CFSA, AUTORE, XML, CODOGG, DATIMP, IDCOMUN)" +
	  " VALUES(#{id}, #{area}, #{key01}, #{key02}, #{key03}, #{key04}, #{tipoInvio}, #{dataInvio}, #{note}, #{idAutore}, #{codiceFiscaleSA}, #{autore}, #{json}, #{oggetto}, #{dataInvio}, #{idComunicazione})")
	public void insertFlusso(FlussoEntry flusso);

	@Update("UPDATE PIATRI SET FILE_ALLEGATO = #{file} WHERE ID_GENERATO=#{nrDoc}")
	public void setPdf(AllegatoEntry pdf);

	@Insert("INSERT INTO UFFICI (ID, CODEIN, DENOM) VALUES (#{id},#{codein},#{denom})")
	public void insertUfficio(@Param("id")Long id, @Param("codein")String codein, @Param("denom")String denom);

	@Insert("INSERT INTO W9INBOX(IDCOMUN, DATRIC, STACOM, XML) VALUES (#{id},#{data},#{stato},#{json})")
	public void insertInbox(@Param("id")Long id, @Param("data")Date data, @Param("stato")Long stato, @Param("json")String json);

	@Insert("INSERT INTO W9OUTBOX(IDCOMUN, AREA, KEY01, KEY02, KEY03, KEY04, STATO, CFSA) VALUES (#{id},#{area},#{key01},#{key02},#{key03},#{key04},#{stato},#{cfsa})")
	public void insertOutbox(@Param("id")Long id, @Param("area")Long area, @Param("key01")Long key01, @Param("key02")Long key02, @Param("key03")Long key03, @Param("key04")Long key04, @Param("stato")Long stato, @Param("cfsa")String cfsa);

	/**
	 * Inserisce le informazioni di un'opera incompiuta.
	 */
	@Insert("INSERT INTO OITRI (CONTRI,NUMOI,CUP,DESCRIZIONE,DETERMINAZIONI,AMBITOINT,ANNOULTQE,IMPORTOINT,IMPORTOLAV,ONERIULTIM,IMPORTOSAL,AVANZAMENTO,CAUSA,STATOREAL,INFRASTRUTTURA,FRUIBILE,UTILIZZORID,DESTINAZIONEUSO,CESSIONE,VENDITA,DEMOLIZIONE,ONERI_SITO, DISCONTINUITA_RETE) "
			+ "VALUES (#{contri},#{numoi},#{cup},#{descrizione},#{determinazioneAmministrazione},#{ambito},#{anno},#{importoIntervento},#{importoLavori},#{oneri},#{importoAvanzamento},#{percentualeAvanzamento},#{causa}," 
			+ "#{stato},#{infrastruttura},#{fruibile},#{ridimensionato},#{destinazioneUso},#{cessione},#{previstaVendita},#{demolizione},#{oneriSito},#{discontinuitaRete})")
			public void insertOperaIncompiuta(OperaIncompiutaEntry opera);

	/**
	 * Inserisce le informazioni di altri dati di un'opera incompiuta.
	 */
	@Update("UPDATE OITRI SET ISTAT=#{istat}, NUTS=#{nuts}, SEZINT=#{tipologiaIntervento}, INTERV=#{categoriaIntervento}, REQ_CAP=#{requisitiCapitolato}, REQ_PRGE=#{requisitiApprovato}"
			+ ", DIM_UM=#{unitaMisura}, DIM_QTA=#{dimensione}, SPONSORIZZAZIONE=#{sponsorizzazione}, FINANZA_PROGETTO=#{finanzaDiProgetto}, COSTO=#{costoProgetto}, FINANZIAMENTO=#{finanziamento}" 
			+ ", COP_STATALE=#{coperturaStatale}, COP_REGIONALE=#{coperturaRegionale}, COP_PROVINCIALE=#{coperturaProvinciale}, COP_COMUNALE=#{coperturaComunale}, COP_ALTRAPUBBLICA=#{coperturaAltro}, COP_COMUNITARIA=#{coperturaComunitaria}, COP_PRIVATA=#{coperturaPrivata}"
			+ " WHERE CONTRI=#{contri} AND NUMOI=#{numoi}")
			public void insertAltriDatiOperaIncompiuta(AltriDatiOperaIncompiutaEntry opera);


	
	/**
	 * Inserisce un immobile per opere incompiute e interventi lavori
	 */
	@Insert("INSERT INTO IMMTRAI (CONTRI,CONINT,NUMIMM,NUMOI,DESIMM,COMIST,NUTS,CUIIMM,TITCOR,IMMDISP,"
			+"ALIENATI,PROGDISM,TIPDISP,PROIMM,VA1IMM,VA2IMM,VA3IMM,VA9IMM) "
			+ "VALUES (#{contri},#{conint},#{numimm},#{numoi},#{descrizione},#{istat},#{nuts},#{cui},#{trasferimentoTitoloCorrispettivo},#{immobileDisponibile},"
			+ "#{alienati},#{inclusoProgrammaDismissione},#{tipoDisponibilita},#{tipoProprieta},#{valoreStimato1},#{valoreStimato2},#{valoreStimato3},#{valoreStimatoSucc})")
			public void insertImmobile(ImmobileEntry immobile);
	
	/**
	 * Inserisce un intervento in un programma di lavori
	 */
	@Insert("INSERT INTO INTTRI(CONTRI,CONINT,CODINT,NPROGINT,TIPINT,CUIINT,DESINT,ANNRIF,ANNINT,"
			+ "COMINT,NUTS,FLAG_CUP,CUPPRG,CODCPV,TIPOIN,PRGINT,LOTFUNZ,LAVCOMPL,SEZINT,INTERV,"
			+ "SCAMUT,FIINTR,APCINT,URCINT,"
			+ "STAPRO,DV1TRI,DV2TRI,DV3TRI,DV9TRI,MU1TRI,MU2TRI,MU3TRI,MU9TRI,"
			+ "IM1TRI,IM2TRI,IM3TRI,IM9TRI,BI1TRI,BI2TRI,BI3TRI,BI9TRI,AL1TRI,AL2TRI,AL3TRI,AL9TRI,"
			+ "AP1TRI,AP2TRI,AP3TRI,AP9TRI,PR1TRI,PR2TRI,PR3TRI,PR9TRI,SPESESOST,"
			+ "DIRGEN,STRUOP,REFERE,RESPUF,PROAFF,COPFIN,VALUTAZIONE,"
			+ "ACQVERDI,NORRIF,AVOGGETT,AVCPV,AVIMPNET,AVIVA,AVIMPTOT,"
			+ "MATRIC,MROGGETT,MRCPV,MRIMPNET,MRIVA,MRIMPTOT,"
			+ "TCPINT,MESEIN,DELEGA,CODAUSA,SOGAGG,CIG_ACCQUADRO,VARIATO,INTNOTE,CODRUP)"
			+ "VALUES (#{contri},#{conint},#{codiceInterno},#{numeroProgressivo},1,#{cui},#{descrizione},#{anno},#{anno},"
			+ "#{istat},#{nuts},#{esenteCup},#{cup},#{cpv},'L',#{priorita},#{lottoFunzionale},#{lavoroComplesso},#{tipologia},#{categoria},"
			+ "#{scadenzaFinanziamento},#{finalita},#{conformitaAmbientale},#{conformitaUrbanistica},"
			+ "#{statoProgettazione},#{risorseVincolatePerLegge1},#{risorseVincolatePerLegge2},#{risorseVincolatePerLegge3},#{risorseVincolatePerLeggeSucc},#{risorseMutuo1},#{risorseMutuo2},#{risorseMutuo3},#{risorseMutuoSucc},"
			+ "#{risorseImmobili1},#{risorseImmobili2},#{risorseImmobili3},#{risorseImmobiliSucc},#{risorseBilancio1},#{risorseBilancio2},#{risorseBilancio3},#{risorseBilancioSucc},#{risorseAltro1},#{risorseAltro2},#{risorseAltro3},#{risorseAltroSucc},"
			+ "#{risorseArt3_1},#{risorseArt3_2},#{risorseArt3_3},#{risorseArt3_Succ},#{risorsePrivati1},#{risorsePrivati2},#{risorsePrivati3},#{risorsePrivatiSucc},#{speseSostenute},"
			+ "#{direzioneGenerale},#{strutturaOperativa},#{referenteDati},#{dirigenteResponsabile},#{proceduraAffidamento},#{coperturaFinanziaria},#{valutazione},"
			+ "#{acquistoVerdi},#{normativaRiferimento},#{oggettoVerdi},#{cpvVerdi},#{importoNettoIvaVerdi},#{importoIvaVerdi},#{importoTotVerdi},"
			+ "#{acquistoMaterialiRiciclati},#{oggettoMatRic},#{cpvMatRic},#{importoNettoIvaMatRic},#{importoIvaMatRic},#{importoTotMatRic},"
			+ "#{tipologiaCapitalePrivato},#{meseAvvioProcedura},#{delega},#{codiceSoggettoDelegato},#{nomeSoggettoDelegato},#{cigAccordoQuadro},#{variato},#{note},#{codiceRup})")
			public void insertIntervento(InterventoEntry intervento);
	
	/**
	 * Inserisce un intervento non riproposto
	 */
	@Insert("INSERT INTO INRTRI (CONTRI,CONINT,CUIINT,CUPPRG,DESINT,TOTINT,PRGINT,MOTIVO) "
			+ "VALUES (#{contri},#{conint},#{cui},#{cup},#{descrizione},#{importo},#{priorita},#{motivo})")
			public void insertInterventoNonRiproposto(InterventoNonRipropostoEntry interventoNonRiproposto);
	
	/**
	 * Inserisce un acquisto in un programma di forniture e servizi
	 */
	@Insert("INSERT INTO INTTRI(CONTRI,CONINT,CODINT,NPROGINT,TIPINT,CUIINT,DESINT,ANNINT,ANNRIF,"
			+ "COMINT,NUTS,FLAG_CUP,CUPPRG,CODCPV,TIPOIN,ACQALTINT,CUICOLL,"
			+ "QUANTIT,UNIMIS,PRGINT,LOTFUNZ,DURCONT,CONTESS,"
			+ "DV1TRI,DV2TRI,DV3TRI,DV9TRI,MU1TRI,MU2TRI,MU3TRI,MU9TRI,"
			+ "IM1TRI,IM2TRI,IM3TRI,IM9TRI,BI1TRI,BI2TRI,BI3TRI,BI9TRI,AL1TRI,AL2TRI,AL3TRI,AL9TRI,"
			+ "AP1TRI,AP2TRI,AP3TRI,AP9TRI,PR1TRI,PR2TRI,PR3TRI,PR9TRI,SPESESOST,"
			+ "DIRGEN,STRUOP,REFERE,RESPUF,PROAFF,"
			+ "ACQVERDI,NORRIF,AVOGGETT,AVCPV,AVIMPNET,AVIVA,AVIMPTOT,"
			+ "MATRIC,MROGGETT,MRCPV,MRIMPNET,MRIVA,MRIMPTOT,"
			+ "IV1TRI,IV2TRI,IV3TRI,IV9TRI,COPFIN,VALUTAZIONE,"
			+ "TCPINT,IMPRFS,RG1TRI,IMPALT,MESEIN,DELEGA,CODAUSA,SOGAGG,VARIATO,INTNOTE,CODRUP)"
			+ "VALUES (#{contri},#{conint},#{codiceInterno},#{conint},2,#{cui},#{descrizione},#{anno},#{anno},"
			+ "#{istat},#{nuts},#{esenteCup},#{cup},#{cpv},#{settore},#{acquistoRicompreso},#{cuiCollegato},"
			+ "#{quantita},#{unitaMisura},#{priorita},#{lottoFunzionale},#{durataInMesi},#{nuovoAffidamento},"
			+ "#{risorseVincolatePerLegge1},#{risorseVincolatePerLegge2},#{risorseVincolatePerLegge3},#{risorseVincolatePerLeggeSucc},#{risorseMutuo1},#{risorseMutuo2},#{risorseMutuo3},#{risorseMutuoSucc},"
			+ "#{risorseImmobili1},#{risorseImmobili2},#{risorseImmobili3},#{risorseImmobiliSucc},#{risorseBilancio1},#{risorseBilancio2},#{risorseBilancio3},#{risorseBilancioSucc},#{risorseAltro1},#{risorseAltro2},#{risorseAltro3},#{risorseAltroSucc},"
			+ "#{risorseArt3_1},#{risorseArt3_2},#{risorseArt3_3},#{risorseArt3_Succ},#{risorsePrivati1},#{risorsePrivati2},#{risorsePrivati3},#{risorsePrivatiSucc},#{speseSostenute},"
			+ "#{direzioneGenerale},#{strutturaOperativa},#{referenteDati},#{dirigenteResponsabile},#{proceduraAffidamento},"
			+ "#{acquistoVerdi},#{normativaRiferimento},#{oggettoVerdi},#{cpvVerdi},#{importoNettoIvaVerdi},#{importoIvaVerdi},#{importoTotVerdi},"
			+ "#{acquistoMaterialiRiciclati},#{oggettoMatRic},#{cpvMatRic},#{importoNettoIvaMatRic},#{importoIvaMatRic},#{importoTotMatRic},"
			+ "#{importoIva1},#{importoIva2},#{importoIva3},#{importoIvaSucc},#{coperturaFinanziaria},#{valutazione},"
			+ "#{tipologiaCapitalePrivato},#{importoRisorseFinanziarie},#{importoRisorseFinanziarieRegionali},#{importoRisorseFinanziarieAltro},#{meseAvvioProcedura},#{delega},#{codiceSoggettoDelegato},#{nomeSoggettoDelegato},#{variato},#{note},#{codiceRup})")
			public void insertAcquisto(AcquistoEntry acquisto);
	
	/**
	 * Inserisce un acquisto non riproposto
	 */
	@Insert("INSERT INTO INRTRI (CONTRI,CONINT,CUIINT,CUPPRG,DESINT,TOTINT,PRGINT,MOTIVO) "
			+ "VALUES (#{contri},#{conint},#{cui},#{cup},#{descrizione},#{importo},#{priorita},#{motivo})")
			public void insertAcquistoNonRiproposto(AcquistoNonRipropostoEntry acquistoNonRiproposto);
	
	/**
	 * Estrae i dati generali del programma Lavori
	 *
	 * @param idProgramma
	 *        identificativo del programma
	 * @return dati generali del programma
	 */
	@Select("select CONTRI, PIATRI.ID, ANNTRI, BRETRI, CFEIN, NAPTRI, DAPTRI, DPAPPROV, TITAPPROV, URLAPPROV, "
			+"UFFICI.DENOM, NADOZI, DADOZI, DPADOZI, TITADOZI, URLADOZI, RESPRO "
			+"from PIATRI LEFT JOIN UFFINT ON PIATRI.CENINT=UFFINT.CODEIN LEFT JOIN UFFICI ON PIATRI.IDUFFICIO=UFFICI.ID where ID_GENERATO = #{idProgramma}")
	@Results({
		@Result(property = "contri", column = "CONTRI"),
		@Result(property = "id", column = "ID"),
		@Result(property = "anno", column = "ANNTRI"),
		@Result(property = "descrizione", column = "BRETRI"),
		@Result(property = "codiceFiscaleSA", column = "CFEIN"),
		@Result(property = "numeroApprovazione", column = "NAPTRI"),
		@Result(property = "dataApprovazione", column = "DAPTRI"),
		@Result(property = "dataPubblicazioneApprovazione", column = "DPAPPROV"),
		@Result(property = "titoloAttoApprovazione", column = "TITAPPROV"),
		@Result(property = "urlAttoApprovazione", column = "URLAPPROV"),
		@Result(property = "ufficio", column = "DENOM"),
		@Result(property = "numeroAdozione", column = "NADOZI"),
		@Result(property = "dataAdozione", column = "DADOZI"),
		@Result(property = "dataPubblicazioneAdozione", column = "DPADOZI"),
		@Result(property = "titoloAttoAdozione", column = "TITADOZI"),
		@Result(property = "urlAttoAdozione", column = "URLADOZI"),
		@Result(property = "codiceReferente", column = "RESPRO")
	})
	public PubblicaProgrammaLavoriEntry getDettaglioLavori(@Param("idProgramma") Long idProgramma);

	/**
	 * Estrae i dati del referente
	 *
	 * @param codiceReferente
	 *        identificativo del referente
	 * @return dati del referente
	 */
	@Select("select COGTEI, NOMETEI, NOMTEC, INDTEC, NCITEC, LOCTEC, PROTEC, TELTEC, FAXTEC, CFTEC, EMATEC "
			+"from TECNI where CODTEC = #{codiceReferente}")
	@Results({
		@Result(property = "cognome", column = "COGTEI"),
		@Result(property = "nome", column = "NOMETEI"),
		@Result(property = "nomeCognome", column = "NOMTEC"),
		@Result(property = "indirizzo", column = "INDTEC"),
		@Result(property = "civico", column = "NCITEC"),
		@Result(property = "localita", column = "LOCTEC"),
		@Result(property = "provincia", column = "PROTEC"),
		@Result(property = "cap", column = "CAPTEC"),
		@Result(property = "telefono", column = "TELTEC"),
		@Result(property = "fax", column = "FAXTEC"),
		@Result(property = "cfPiva", column = "CFTEC"),
		@Result(property = "mail", column = "EMATEC")
	})
	public DatiGeneraliTecnicoEntry getReferente(@Param("codiceReferente") String codiceReferente);
	
	/**
	 * Estrae i dati delle opere incompiute
	 *
	 * @param contri
	 *        identificativo del programma
	 * @return dati delle opere incompiute
	 */
	@Select("select NUMOI, CUP, DESCRIZIONE, DETERMINAZIONI, AMBITOINT, ANNOULTQE, "
			+"IMPORTOINT, IMPORTOLAV, ONERIULTIM, IMPORTOSAL, AVANZAMENTO, CAUSA, STATOREAL, "
			+"INFRASTRUTTURA, FRUIBILE, UTILIZZORID, DESTINAZIONEUSO, CESSIONE, VENDITA, DEMOLIZIONE, ONERI_SITO "
			+"from OITRI where CONTRI = #{contri} order by NUMOI")
	@Results({
		@Result(property = "numoi", column = "NUMOI"),
		@Result(property = "cup", column = "CUP"),
		@Result(property = "descrizione", column = "DESCRIZIONE"),
		@Result(property = "determinazioneAmministrazione", column = "DETERMINAZIONI"),
		@Result(property = "ambito", column = "AMBITOINT"),
		@Result(property = "anno", column = "ANNOULTQE"),
		@Result(property = "importoIntervento", column = "IMPORTOINT"),
		@Result(property = "importoLavori", column = "IMPORTOLAV"),
		@Result(property = "oneri", column = "ONERIULTIM"),
		@Result(property = "importoAvanzamento", column = "IMPORTOSAL"),
		@Result(property = "percentualeAvanzamento", column = "AVANZAMENTO"),
		@Result(property = "causa", column = "CAUSA"),
		@Result(property = "stato", column = "STATOREAL"),
		@Result(property = "infrastruttura", column = "INFRASTRUTTURA"),
		@Result(property = "fruibile", column = "FRUIBILE"),
		@Result(property = "ridimensionato", column = "UTILIZZORID"),
		@Result(property = "destinazioneUso", column = "DESTINAZIONEUSO"),
		@Result(property = "cessione", column = "CESSIONE"),
		@Result(property = "previstaVendita", column = "VENDITA"),
		@Result(property = "demolizione", column = "DEMOLIZIONE"),
		@Result(property = "oneriSito", column = "ONERI_SITO")
	})
	public List<OperaIncompiutaEntry> getOpereIncompiute(@Param("contri") Long contri);
	
	/**
	 * Estrae i gli altri dati dell'opera incompiuta
	 *
	 * @param contri
	 *        identificativo del programma
	 * @param numoi
	 *        identificativo ddell'opera
	 * @return altri dati dell'opera incompiuta
	 */
	@Select("select ISTAT, NUTS, SEZINT, INTERV, REQ_CAP, REQ_PRGE, DIM_UM, DIM_QTA, SPONSORIZZAZIONE, FINANZA_PROGETTO, COSTO, "
			+"FINANZIAMENTO, COP_STATALE, COP_REGIONALE, COP_PROVINCIALE, COP_COMUNALE, COP_ALTRAPUBBLICA, COP_COMUNITARIA, COP_PRIVATA "
			+"from OITRI where CONTRI = #{contri} AND NUMOI = #{numoi}")
	@Results({
		@Result(property = "istat", column = "ISTAT"),
		@Result(property = "nuts", column = "NUTS"),
		@Result(property = "tipologiaIntervento", column = "SEZINT"),
		@Result(property = "categoriaIntervento", column = "INTERV"),
		@Result(property = "requisitiCapitolato", column = "REQ_CAP"),
		@Result(property = "requisitiApprovato", column = "REQ_PRGE"),
		@Result(property = "unitaMisura", column = "DIM_UM"),
		@Result(property = "dimensione", column = "DIM_QTA"),
		@Result(property = "sponsorizzazione", column = "SPONSORIZZAZIONE"),
		@Result(property = "finanzaDiProgetto", column = "FINANZA_PROGETTO"),
		@Result(property = "costoProgetto", column = "COSTO"),
		@Result(property = "finanziamento", column = "FINANZIAMENTO"),
		@Result(property = "coperturaStatale", column = "COP_STATALE"),
		@Result(property = "coperturaRegionale", column = "COP_REGIONALE"),
		@Result(property = "coperturaProvinciale", column = "COP_PROVINCIALE"),
		@Result(property = "coperturaComunale", column = "COP_COMUNALE"),
		@Result(property = "coperturaAltro", column = "COP_ALTRAPUBBLICA"),
		@Result(property = "coperturaComunitaria", column = "COP_COMUNITARIA"),
		@Result(property = "coperturaPrivata", column = "COP_PRIVATA")
	})
	public AltriDatiOperaIncompiutaEntry getAltriDatiOperaIncompiuta(@Param("contri") Long contri, @Param("numoi") Long numoi);
	
	/**
	 * Estrae gli immobili dell'opera incompiuta
	 *
	 * @param contri
	 *        identificativo del programma
	 * @param numoi
	 *        identificativo ddell'opera
	 * @return immobili dell'opera incompiuta
	 */
	@Select("select NUMIMM, DESIMM, COMIST, NUTS, CUIIMM, TITCOR, IMMDISP, ALIENATI, PROGDISM, TIPDISP, PROIMM, VA1IMM, VA2IMM, VA3IMM, VA9IMM "
			+"from IMMTRAI where CONTRI = #{contri} AND NUMOI = #{numoi} ORDER BY NUMIMM")
	@Results({
		@Result(property = "numimm", column = "NUMIMM"),
		@Result(property = "descrizione", column = "DESIMM"),
		@Result(property = "istat", column = "COMIST"),
		@Result(property = "nuts", column = "NUTS"),
		@Result(property = "cui", column = "CUIIMM"),
		@Result(property = "trasferimentoTitoloCorrispettivo", column = "TITCOR"),
		@Result(property = "immobileDisponibile", column = "IMMDISP"),
		@Result(property = "alienati", column = "ALIENATI"),
		@Result(property = "inclusoProgrammaDismissione", column = "PROGDISM"),
		@Result(property = "tipoDisponibilita", column = "TIPDISP"),
		@Result(property = "tipoProprieta", column = "PROIMM"),
		@Result(property = "valoreStimato1", column = "VA1IMM"),
		@Result(property = "valoreStimato2", column = "VA2IMM"),
		@Result(property = "valoreStimato3", column = "VA3IMM"),
		@Result(property = "valoreStimatoSucc", column = "VA9IMM")
	})
	public List<ImmobileEntry> getImmobiliOperaIncompiuta(@Param("contri") Long contri, @Param("numoi") Long numoi);
	
	/**
	 * Estrae gli interventi dei lavori del programma
	 *
	 * @param contri
	 *        identificativo del programma
	 * @return interventi dei lavori del programma
	 */
	@Select("select CONINT, CODINT, NPROGINT, CUIINT, DESINT, ANNRIF, COMINT, NUTS, FLAG_CUP, CUPPRG, CODCPV, "
			+"PRGINT, LOTFUNZ, LAVCOMPL, SEZINT, INTERV, SCAMUT, FIINTR, APCINT, URCINT, STAPRO, DV1TRI, "
			+"DV2TRI, DV3TRI, DV9TRI, MU1TRI, MU2TRI, MU3TRI, MU9TRI, IM1TRI, IM2TRI, IM3TRI, IM9TRI, "
			+"BI1TRI, BI2TRI, BI3TRI, BI9TRI, AL1TRI, AL2TRI, AL3TRI, AL9TRI, AP1TRI, AP2TRI, AP3TRI, AP9TRI, "
			+"PR1TRI, PR2TRI, PR3TRI, PR9TRI, SPESESOST, DIRGEN, STRUOP, REFERE, RESPUF, PROAFF, COPFIN, "
			+"VALUTAZIONE, ACQVERDI, NORRIF, AVOGGETT, AVCPV, AVIMPNET, AVIVA, AVIMPTOT, "
			+"MATRIC, MROGGETT, MRCPV, MRIMPNET, MRIVA, MRIMPTOT, "
			+"TCPINT, MESEIN, DELEGA, CODAUSA, SOGAGG, VARIATO, INTNOTE, CODRUP "
			+"from INTTRI where CONTRI = #{contri} ORDER BY NPROGINT")
	@Results({
		@Result(property = "conint", column = "CONINT"),
		@Result(property = "codiceInterno", column = "CODINT"),
		@Result(property = "numeroProgressivo", column = "NPROGINT"),
		@Result(property = "cui", column = "CUIINT"),
		@Result(property = "descrizione", column = "DESINT"),
		@Result(property = "anno", column = "ANNRIF"),
		@Result(property = "istat", column = "COMINT"),
		@Result(property = "nuts", column = "NUTS"),
		@Result(property = "esenteCup", column = "FLAG_CUP"),
		@Result(property = "cup", column = "CUPPRG"),
		@Result(property = "cpv", column = "CODCPV"),
		@Result(property = "priorita", column = "PRGINT"),
		@Result(property = "lottoFunzionale", column = "LOTFUNZ"),
		@Result(property = "lavoroComplesso", column = "LAVCOMPL"),
		@Result(property = "tipologia", column = "SEZINT"),
		@Result(property = "categoria", column = "INTERV"),
		@Result(property = "scadenzaFinanziamento", column = "SCAMUT"),
		@Result(property = "finalita", column = "FIINTR"),
		@Result(property = "conformitaAmbientale", column = "APCINT"),
		@Result(property = "conformitaUrbanistica", column = "URCINT"),
		@Result(property = "statoProgettazione", column = "STAPRO"),
		@Result(property = "risorseVincolatePerLegge1", column = "DV1TRI"),
		@Result(property = "risorseVincolatePerLegge2", column = "DV2TRI"),
		@Result(property = "risorseVincolatePerLegge3", column = "DV3TRI"),
		@Result(property = "risorseVincolatePerLeggeSucc", column = "DV9TRI"),
		@Result(property = "risorseMutuo1", column = "MU1TRI"),
		@Result(property = "risorseMutuo2", column = "MU2TRI"),
		@Result(property = "risorseMutuo3", column = "MU3TRI"),
		@Result(property = "risorseMutuoSucc", column = "MU9TRI"),
		@Result(property = "risorseImmobili1", column = "IM1TRI"),
		@Result(property = "risorseImmobili2", column = "IM2TRI"),
		@Result(property = "risorseImmobili3", column = "IM3TRI"),
		@Result(property = "risorseImmobiliSucc", column = "IM9TRI"),
		@Result(property = "risorseBilancio1", column = "BI1TRI"),
		@Result(property = "risorseBilancio2", column = "BI2TRI"),
		@Result(property = "risorseBilancio3", column = "BI3TRI"),
		@Result(property = "risorseBilancioSucc", column = "BI9TRI"),
		@Result(property = "risorseAltro1", column = "AL1TRI"),
		@Result(property = "risorseAltro2", column = "AL2TRI"),
		@Result(property = "risorseAltro3", column = "AL3TRI"),
		@Result(property = "risorseAltroSucc", column = "AL9TRI"),
		@Result(property = "risorseArt3_1", column = "AP1TRI"),
		@Result(property = "risorseArt3_2", column = "AP2TRI"),
		@Result(property = "risorseArt3_3", column = "AP3TRI"),
		@Result(property = "risorseArt3_Succ", column = "AP9TRI"),
		@Result(property = "risorsePrivati1", column = "PR1TRI"),
		@Result(property = "risorsePrivati2", column = "PR2TRI"),
		@Result(property = "risorsePrivati3", column = "PR3TRI"),
		@Result(property = "risorsePrivatiSucc", column = "PR9TRI"),
		@Result(property = "speseSostenute", column = "SPESESOST"),
		@Result(property = "direzioneGenerale", column = "DIRGEN"),
		@Result(property = "strutturaOperativa", column = "STRUOP"),
		@Result(property = "referenteDati", column = "REFERE"),
		@Result(property = "dirigenteResponsabile", column = "RESPUF"),
		@Result(property = "proceduraAffidamento", column = "PROAFF"),
		@Result(property = "coperturaFinanziaria", column = "COPFIN"),
		@Result(property = "valutazione", column = "VALUTAZIONE"),
		@Result(property = "acquistoVerdi", column = "ACQVERDI"),
		@Result(property = "normativaRiferimento", column = "NORRIF"),
		@Result(property = "oggettoVerdi", column = "AVOGGETT"),
		@Result(property = "cpvVerdi", column = "AVCPV"),
		@Result(property = "importoNettoIvaVerdi", column = "AVIMPNET"),
		@Result(property = "importoIvaVerdi", column = "AVIVA"),
		@Result(property = "importoTotVerdi", column = "AVIMPTOT"),
		@Result(property = "acquistoMaterialiRiciclati", column = "MATRIC"),
		@Result(property = "oggettoMatRic", column = "MROGGETT"),
		@Result(property = "cpvMatRic", column = "MRCPV"),
		@Result(property = "importoNettoIvaMatRic", column = "MRIMPNET"),
		@Result(property = "importoIvaMatRic", column = "MRIVA"),
		@Result(property = "importoTotMatRic", column = "MRIMPTOT"),
		@Result(property = "tipologiaCapitalePrivato", column = "TCPINT"),
		@Result(property = "meseAvvioProcedura", column = "MESEIN"),
		@Result(property = "delega", column = "DELEGA"),
		@Result(property = "codiceSoggettoDelegato", column = "CODAUSA"),
		@Result(property = "nomeSoggettoDelegato", column = "SOGAGG"),
		@Result(property = "variato", column = "VARIATO"),
		@Result(property = "note", column = "INTNOTE"),
		@Result(property = "codiceRup", column = "CODRUP")
	})
	public List<InterventoEntry> getInterventi(@Param("contri") Long contri);

	/**
	 * Estrae gli immobili dell'intervento
	 *
	 * @param contri
	 *        identificativo del programma
	 * @param numoi
	 *        identificativo dell'opera
	 * @return immobili dell'intervento
	 */
	@Select("select NUMIMM, DESIMM, COMIST, NUTS, CUIIMM, TITCOR, IMMDISP, ALIENATI, PROGDISM, TIPDISP, PROIMM, VA1IMM, VA2IMM, VA3IMM, VA9IMM "
			+"from IMMTRAI where CONTRI = #{contri} AND CONINT = #{conint} ORDER BY NUMIMM")
	@Results({
		@Result(property = "numimm", column = "NUMIMM"),
		@Result(property = "descrizione", column = "DESIMM"),
		@Result(property = "istat", column = "COMIST"),
		@Result(property = "nuts", column = "NUTS"),
		@Result(property = "cui", column = "CUIIMM"),
		@Result(property = "trasferimentoTitoloCorrispettivo", column = "TITCOR"),
		@Result(property = "immobileDisponibile", column = "IMMDISP"),
		@Result(property = "alienati", column = "ALIENATI"),
		@Result(property = "inclusoProgrammaDismissione", column = "PROGDISM"),
		@Result(property = "tipoDisponibilita", column = "TIPDISP"),
		@Result(property = "tipoProprieta", column = "PROIMM"),
		@Result(property = "valoreStimato1", column = "VA1IMM"),
		@Result(property = "valoreStimato2", column = "VA2IMM"),
		@Result(property = "valoreStimato3", column = "VA3IMM"),
		@Result(property = "valoreStimatoSucc", column = "VA9IMM")
	})
	public List<ImmobileEntry> getImmobiliIntervento(@Param("contri") Long contri, @Param("conint") Long conint);

	
	
	/**
	 * Estrae gli interventi non riproposti
	 *
	 * @param contri
	 *        identificativo del programma
	 * @return interventi non riproposti
	 */
	@Select("select CUIINT, CUPPRG, DESINT, TOTINT, PRGINT, MOTIVO "
			+"from INRTRI where CONTRI = #{contri}")
	@Results({
		@Result(property = "cui", column = "CUIINT"),
		@Result(property = "cup", column = "CUPPRG"),
		@Result(property = "descrizione", column = "DESINT"),
		@Result(property = "importo", column = "TOTINT"),
		@Result(property = "priorita", column = "PRGINT"),
		@Result(property = "motivo", column = "MOTIVO")
	})
	public List<InterventoNonRipropostoEntry> getInterventiNonRiproposti(@Param("contri") Long contri);
	
	
	/**
	 * Estrae i dati generali del programma Forniture e servizi
	 *
	 * @param idProgramma
	 *        identificativo del programma
	 * @return dati generali del programma
	 */
	@Select("select CONTRI, PIATRI.ID, ANNTRI, BRETRI, CFEIN, NAPTRI, DAPTRI, DPAPPROV, TITAPPROV, URLAPPROV, "
			+"UFFICI.DENOM, RESPRO "
			+"from PIATRI LEFT JOIN UFFINT ON PIATRI.CENINT=UFFINT.CODEIN LEFT JOIN UFFICI ON PIATRI.IDUFFICIO=UFFICI.ID where ID_GENERATO = #{idProgramma}")
	@Results({
		@Result(property = "contri", column = "CONTRI"),
		@Result(property = "id", column = "ID"),
		@Result(property = "anno", column = "ANNTRI"),
		@Result(property = "descrizione", column = "BRETRI"),
		@Result(property = "codiceFiscaleSA", column = "CFEIN"),
		@Result(property = "numeroApprovazione", column = "NAPTRI"),
		@Result(property = "dataApprovazione", column = "DAPTRI"),
		@Result(property = "dataPubblicazioneApprovazione", column = "DPAPPROV"),
		@Result(property = "titoloAttoApprovazione", column = "TITAPPROV"),
		@Result(property = "urlAttoApprovazione", column = "URLAPPROV"),
		@Result(property = "ufficio", column = "DENOM"),
		@Result(property = "codiceReferente", column = "RESPRO")
	})
	public PubblicaProgrammaFornitureServiziEntry getDettaglioFornitureServizi(@Param("idProgramma") Long idProgramma);
	
	
	
	

	
	/**
	 * Estrae gli acquisti non riproposti
	 *
	 * @param contri
	 *        identificativo del programma
	 * @return acquisti non riproposti
	 */
	@Select("select CUIINT, CUPPRG, DESINT, TOTINT, PRGINT, MOTIVO "
			+"from INRTRI where CONTRI = #{contri}")
	@Results({
		@Result(property = "cui", column = "CUIINT"),
		@Result(property = "cup", column = "CUPPRG"),
		@Result(property = "descrizione", column = "DESINT"),
		@Result(property = "importo", column = "TOTINT"),
		@Result(property = "priorita", column = "PRGINT"),
		@Result(property = "motivo", column = "MOTIVO")
	})
	public List<AcquistoNonRipropostoEntry> getAcquistiNonRiproposti(@Param("contri") Long contri);
	
	
	
	/**
	 * Estrae gli acquisti del programma Forniture e servizi
	 *
	 * @param contri
	 *        identificativo del programma
	 * @return acquisti del programma Forniture e servizi
	 */

	@Select("select CONINT, CODINT, CUIINT, DESINT, ANNRIF, COMINT, NUTS, FLAG_CUP, CUPPRG, CODCPV, TIPOIN, ACQALTINT, CUICOLL, "
			+"QUANTIT, UNIMIS, PRGINT, LOTFUNZ, DURCONT, CONTESS, "
			+"DV1TRI, DV2TRI, DV9TRI, MU1TRI, MU2TRI, MU9TRI, IM1TRI, IM2TRI, IM9TRI, "
			+"BI1TRI, BI2TRI, BI9TRI, AL1TRI, AL2TRI, AL9TRI, AP1TRI, AP2TRI, AP9TRI, "
			+"PR1TRI, PR2TRI, PR9TRI, SPESESOST, DIRGEN, STRUOP, REFERE, RESPUF, PROAFF, "
			+"ACQVERDI, NORRIF, AVOGGETT, AVCPV, AVIMPNET, AVIVA, AVIMPTOT, "
			+"MATRIC, MROGGETT, MRCPV, MRIMPNET, MRIVA, MRIMPTOT, "
			+"IV1TRI, IV2TRI, IV9TRI, COPFIN, VALUTAZIONE, "
			+"TCPINT, IMPRFS, RG1TRI, IMPALT, MESEIN, DELEGA, CODAUSA, SOGAGG, VARIATO, INTNOTE, CODRUP "
			+"from INTTRI where CONTRI = #{contri} ORDER BY CONINT")
	@Results({
		@Result(property = "conint", column = "CONINT"),
		@Result(property = "codiceInterno", column = "CODINT"),
		@Result(property = "cui", column = "CUIINT"),
		@Result(property = "descrizione", column = "DESINT"),
		@Result(property = "anno", column = "ANNRIF"),
		@Result(property = "istat", column = "COMINT"),
		@Result(property = "nuts", column = "NUTS"),
		@Result(property = "esenteCup", column = "FLAG_CUP"),
		@Result(property = "cup", column = "CUPPRG"),
		@Result(property = "cpv", column = "CODCPV"),
		@Result(property = "settore", column = "TIPOIN"),
		@Result(property = "acquistoRicompreso", column = "ACQALTINT"),
		@Result(property = "cuiCollegato", column = "CUICOLL"),
		@Result(property = "quantita", column = "QUANTIT"),
		@Result(property = "unitaMisura", column = "UNIMIS"),
		@Result(property = "priorita", column = "PRGINT"),
		@Result(property = "lottoFunzionale", column = "LOTFUNZ"),
		@Result(property = "durataInMesi", column = "DURCONT"),
		@Result(property = "nuovoAffidamento", column = "CONTESS"),
		@Result(property = "risorseVincolatePerLegge1", column = "DV1TRI"),
		@Result(property = "risorseVincolatePerLegge2", column = "DV2TRI"),
		@Result(property = "risorseVincolatePerLeggeSucc", column = "DV9TRI"),
		@Result(property = "risorseMutuo1", column = "MU1TRI"),
		@Result(property = "risorseMutuo2", column = "MU2TRI"),
		@Result(property = "risorseMutuoSucc", column = "MU9TRI"),
		@Result(property = "risorseImmobili1", column = "IM1TRI"),
		@Result(property = "risorseImmobili2", column = "IM2TRI"),
		@Result(property = "risorseImmobiliSucc", column = "IM9TRI"),
		@Result(property = "risorseBilancio1", column = "BI1TRI"),
		@Result(property = "risorseBilancio2", column = "BI2TRI"),
		@Result(property = "risorseBilancioSucc", column = "BI9TRI"),
		@Result(property = "risorseAltro1", column = "AL1TRI"),
		@Result(property = "risorseAltro2", column = "AL2TRI"),
		@Result(property = "risorseAltroSucc", column = "AL9TRI"),
		@Result(property = "risorseArt3_1", column = "AP1TRI"),
		@Result(property = "risorseArt3_2", column = "AP2TRI"),
		@Result(property = "risorseArt3_Succ", column = "AP9TRI"),
		@Result(property = "risorsePrivati1", column = "PR1TRI"),
		@Result(property = "risorsePrivati2", column = "PR2TRI"),
		@Result(property = "risorsePrivatiSucc", column = "PR9TRI"),
		@Result(property = "speseSostenute", column = "SPESESOST"),
		@Result(property = "direzioneGenerale", column = "DIRGEN"),
		@Result(property = "strutturaOperativa", column = "STRUOP"),
		@Result(property = "referenteDati", column = "REFERE"),
		@Result(property = "dirigenteResponsabile", column = "RESPUF"),
		@Result(property = "proceduraAffidamento", column = "PROAFF"),
		@Result(property = "acquistoVerdi", column = "ACQVERDI"),
		@Result(property = "normativaRiferimento", column = "NORRIF"),
		@Result(property = "oggettoVerdi", column = "AVOGGETT"),
		@Result(property = "cpvVerdi", column = "AVCPV"),
		@Result(property = "importoNettoIvaVerdi", column = "AVIMPNET"),
		@Result(property = "importoIvaVerdi", column = "AVIVA"),
		@Result(property = "importoTotVerdi", column = "AVIMPTOT"),
		@Result(property = "acquistoMaterialiRiciclati", column = "MATRIC"),
		@Result(property = "oggettoMatRic", column = "MROGGETT"),
		@Result(property = "cpvMatRic", column = "MRCPV"),
		@Result(property = "importoNettoIvaMatRic", column = "MRIMPNET"),
		@Result(property = "importoIvaMatRic", column = "MRIVA"),
		@Result(property = "importoTotMatRic", column = "MRIMPTOT"),
		@Result(property = "importoIva1", column = "IV1TRI"),
		@Result(property = "importoIva2", column = "IV2TRI"),
		@Result(property = "importoIvaSucc", column = "IV9TRI"),
		@Result(property = "coperturaFinanziaria", column = "COPFIN"),
		@Result(property = "valutazione", column = "VALUTAZIONE"),
		@Result(property = "tipologiaCapitalePrivato", column = "TCPINT"),
		@Result(property = "importoRisorseFinanziarie", column = "IMPRFS"),
		@Result(property = "importoRisorseFinanziarieRegionali", column = "RG1TRI"),
		@Result(property = "importoRisorseFinanziarieAltro", column = "IMPALT"),
		@Result(property = "meseAvvioProcedura", column = "MESEIN"),
		@Result(property = "delega", column = "DELEGA"),
		@Result(property = "codiceSoggettoDelegato", column = "CODAUSA"),
		@Result(property = "nomeSoggettoDelegato", column = "SOGAGG"),
		@Result(property = "variato", column = "VARIATO"),
		@Result(property = "note", column = "INTNOTE"),
		@Result(property = "codiceRup", column = "CODRUP")
	})
	public List<AcquistoEntry> getAcquisti(@Param("contri") Long contri);


	@Select("select valore from w_config where UPPER(codapp) = #{codapp} AND chiave = #{chiave}")
	public String getConfig(@Param("codapp") String codapp, @Param("chiave") String chiave);
	
    @Select("select count(1) from avviso")
   	public Long health();

    @Select("select contri from piatri where id_generato = #{idRicevuto}")
	public String getPubblicazioneLink(@Param("idRicevuto")Long idRicevuto);

	@Select("select norma from piatri where id = #{id}")
    public Long getProgrammaNormaById(@Param("id") String id);
}
