package it.appaltiecontratti.autenticazione.mapper;

import it.appaltiecontratti.autenticazione.entity.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * DAO Interface per l'estrazione delle informazioni relative al login
 * nell'applicazione.
 *
 * @author Michele.DiNapoli
 */
@Mapper
public interface AccountMapper {

    @Select("select count(*) from usr_ein where syscon = #{syscon}")
    Long countSAList(@Param("syscon") Long syscon);

    @Select("select count(*) from uffint")
    Long countSAListUffint();
    
    @Select("select count(*) from uffint u join w_config w on w.chiave = CONCAT('loginMultiEnte.', u.codein)")
    Long countSAListUffintFiltered();
    
    @Select("select count(*) from usr_ein where syscon = #{syscon} and codein = #{codein}")
    Long checkUserAbilitato(@Param("syscon") Long syscon, @Param("codein") String codein);

    List<SABaseEntry> getSAList(@Param("syscon") Long syscon, @Param("searchUfficioIntestatario") final String searchUfficioIntestatario);
    	 
    List<SABaseEntry> getSAList(@Param("syscon") Long syscon, @Param("searchUfficioIntestatario") final String searchUfficioIntestatario, RowBounds rowBounds);

    @Select("select u.syscon, u.sysute as nome, u.syscf as cf from usrsys u inner join usr_ein ue on u.syscon = ue.syscon where ue.codein = #{codiceSA}")
    public List<UsrSysconEntry> getUserListOfSA(@Param("codiceSA") String codiceSA);

    @Select("select syscon, sysute as nome, syscf as cf from usrsys order by syscon")
    public List<UsrSysconEntry> getAllUtenti();

    @Select("select codein  as codice, nomein  as nome from uffint where codein = #{sa}")
    public SABaseEntry getBaseSAInfo(@Param("sa") String sa);

    public List<SABaseEntry> getBaseSAInfoList(@Param("listaCodein") List<String> listaCodein);

    @Select("select codein as codice, nomein as nome from uffint order by nomein")
    public List<SABaseEntry> getAllBaseSAInfo();

    List<SABaseEntry> getAllBaseSAInfoLimited(@Param("searchUfficioIntestatario") final String searchUfficioIntestatario, RowBounds rowBounds);

    List<SABaseEntry> getAllBaseSAInfoLimitedSpid(@Param("searchUfficioIntestatario") final String searchUfficioIntestatario, RowBounds rowBounds);

    
    @Select("select count(*) from uffint")
    Long countAllBaseSAInfo();

    @Select("select codein as codice, nomein as nome, cfein as codFiscale, viaein as indirizzo, nciein as numCivico, proein as provincia, capein as cap, citein as comune, codcit as codistat, telein as telefono, faxein as fax, emai2in as email from uffint where codein = #{sa}")
    public SAEntry getSAInfo(@Param("sa") String sa);

    @Select("select codein as codice, nomein as nome, cfein as codFiscale, viaein as indirizzo, nciein as numCivico, proein as provincia, capein as cap, citein as comune, codcit as codistat, telein as telefono, faxein as fax, emai2in as email, codausa as codAusa from uffint where codein = #{stazioneAppaltante}")
    public SAEntry getStazioneAppaltanteInfo(@Param("stazioneAppaltante") String stazioneAppaltante);

    @Select("select cod_profilo from w_accpro where id_account = #{syscon}")
    public List<String> getcodiciProfiloAbilitati(@Param("syscon") Long syscon);

    @Select("select COD_PROFILO as codProfilo, NOME, DESCRIZIONE, CODAPP, FLAG_INTERNO as flagInterno, COD_CLIENTE as codCliente, CRC from w_profili where cod_profilo in (select cod_profilo from w_accpro where id_account = #{syscon}) and codapp = #{codapp}")
    public List<ProfiloInfo> getProfili(@Param("syscon") Long syscon, @Param("codapp") String codapp);

    @Select("select COD_PROFILO as codProfilo, NOME, DESCRIZIONE, CODAPP, FLAG_INTERNO as flagInterno, COD_CLIENTE as codCliente, CRC from W_PROFILI where COD_PROFILO = #{codProfilo}")
    public ProfiloInfo getInfoProfilo(@Param("codProfilo") String codProfilo);

    @Select("select tipo, azione, oggetto, valore, inor, viselenco, crc from W_AZIONI")
    public List<AzioneEntry> getAllAzioni();

    @Select("select TIPO, AZIONE, OGGETTO, VALORE, CRC from W_PROAZI where COD_PROFILO=#{codProfilo} and exists( select 1 from W_AZIONI where W_AZIONI.TIPO = W_PROAZI.TIPO and W_AZIONI.AZIONE = W_PROAZI.AZIONE and W_AZIONI.OGGETTO = '*' and W_AZIONI.INOR = 1 ) order by valore asc")
    public List<BaseAzioneEntry> getAzioniProaziAnd(@Param("codProfilo") String codProfilo);

    @Select("select TIPO, AZIONE, OGGETTO, VALORE, CRC from W_PROAZI where COD_PROFILO=#{codProfilo} and exists( select 1 from W_AZIONI where W_AZIONI.TIPO = W_PROAZI.TIPO and W_AZIONI.AZIONE = W_PROAZI.AZIONE and W_AZIONI.OGGETTO = '*' and W_AZIONI.INOR = 0 ) order by valore desc")
    public List<BaseAzioneEntry> getAzioniProaziOr(@Param("codProfilo") String codProfilo);

    @Select("SELECT syspwbou from usrsys where syscon = #{syscon}")
    public String getAbilitazioniUtente(@Param("syscon") Long syscon);
    
    @Select("SELECT syspwbou from usrsys where upper(syscf) = #{syscf}")
    public String getAbilitazioniUtenteBySyscf(@Param("syscf") String syscf);

    @Select("select chiave, valore from w_config where chiave in ('it.eldasoft.pubblicazioni.ws.clientId','it.eldasoft.pubblicazioni.ws.clientKey','it.eldasoft.pubblicazioni.ws.password','it.eldasoft.pubblicazioni.ws.username' )")
    public List<ChiaveConfigurazione> getChiaviAccessoOrt();

    @Select("select sysdisab from usrsys where syscon = #{syscon}")
    public String checkUserEnabledBySyscon(@Param("syscon") Long syscon);

    @Insert("INSERT INTO USRSYS (SYSCON, SYSNOM, SYSLOGIN, SYSPWD, SYSUTE, SYSPWBOU, SYSDAT,  SYSAB3, SYSABG, FLAG_LDAP, SYSDISAB, EMAIL, SYSSCAD, SYSCF) VALUES (#{idAccount}, #{loginCriptata}, #{login}, #{password}, #{nome}, #{opzioniUtente}, #{dataInserimento}, 'U', 'U', 3, 1, #{email}, #{scadenzaAccount}, #{codfisc})")
    public void insertAccount(UserAccountInsertForm userAccountForm);

    @Insert("INSERT INTO STOUTESYS (SYSCON, SYSNOM, SYSLOGIN, SYSPWD, SYSDAT) VALUES (#{idAccount}, #{loginCriptata}, #{login}, #{password}, #{dataInserimento})")
    public void insertStoriaAccount(UserAccountInsertForm userAccountForm);

    @Select("select max(syscon) from usrsys")
    public Long getMaxSyscon();

    @Insert("insert into w_accpro (id_account, cod_profilo) VALUES (#{idAccount},#{codProfilo})")
    public void insertAssociazioneUteProfilo(@Param("idAccount") long idAccount, @Param("codProfilo") String codProfilo);

    @Insert("insert into usr_ein (syscon, codein) VALUES (#{idAccount},#{codSA})")
    public void insertAssociazioneUteSa(@Param("idAccount") long idAccount, @Param("codSA") String codSA);

    @Select("select count(cod_profilo) from w_profili where cod_profilo = #{codProfilo}")
    public Long countByCodProfilo(@Param("codProfilo") String codProfilo);

    @Select("select count(codein) from uffint where codein = #{codein}")
    public Long countByCodein(@Param("codein") String codein);

    @Select("select count(syscon) from usrsys where UPPER(syslogin) = #{username} or UPPER(syscf) = #{username}")
    public Long countByUsername(@Param("username") String username);

    @Select("select sysab3 from usrsys where syscon = #{syscon}")
    public String getRuoloUtente(@Param("syscon") Long syscon);

    @Select("select server as host, porta as port, mail_mittente as mailMittente, id_user as username, password as password, timeout as timeout, protocollo as protocollo from w_mail where codapp = #{codApp} and idcfg=#{configurazione}")
    MailInfo getInfoMailServer(@Param("codApp") final String codApp, @Param("configurazione") final String configurazione);

    @Select("SELECT valore FROM w_att WHERE codapp = #{codApp} AND chiave = #{chiave}")
    public String getOpzioniProdotto(@Param("codApp") final String codApp, @Param("chiave") final String chiave);

    @Delete("DELETE FROM usr_ein WHERE codein = #{codiceSA}")
    public void deleteAllUsersForSA(@Param("codiceSA") final String codiceSA);

    @Select("SELECT COUNT(*) FROM usrsys WHERE syscon = #{syscon}")
    public int countUserExist(@Param("syscon") final Long syscon);

    @Select("SELECT mail_mittente FROM w_mail WHERE codapp = #{codApp} AND idcfg = #{configurazione}")
    public String getEmailMittente(@Param("codApp") final String codApp, @Param("configurazione") final String configurazione);

    @Select("SELECT email from usrsys where syscon = #{syscon}")
    public String getUserEmail(@Param("syscon") final Long syscon);


    @Select("SELECT u.sysab3, u.SYSUFFAPP as idCentroCosto, uf.denom as centroCosto FROM usrsys u left join uffici uf on u.SYSUFFAPP = uf.id WHERE syscon = #{syscon}")
    public PersonalizzazioneUtente getPersonalizzazioniUtente(@Param("syscon") Long syscon);

    @Select("select id, denom from uffici u where codein in (select codein from usrsys where syscon = #{syscon}) and ccprog = '1'")
    public List<CentroCosto> getCentriDiCostoUtente(@Param("syscon") Long syscon);

    @Update("update usrsys set sysab3 = #{sysab3}, SYSUFFAPP = #{centroCosto} WHERE syscon = #{syscon}")
    void modificaPersonalizzazioniUtente(@Param("sysab3") String sysab3, @Param("centroCosto") Long centroCosto, @Param("syscon") Long syscon);

    @Update("UPDATE usrsys SET sysultacc = #{ultimoAccesso} WHERE syscon = #{syscon}")
    void updateLoginAccess(@Param("syscon") final Long syscon, @Param("ultimoAccesso") final LocalDateTime ultimoAccesso);
    
    @Select("select sysultacc from usrsys WHERE syscon = #{syscon}")
    public Date getLastLoginDate(@Param("syscon") final Long syscon);

    @Insert("INSERT INTO w_ssojwttoken(syslogin, authcode, jwttoken, refreshtoken, dtcreazione) VALUES (#{syslogin}, #{authorizationCode}, #{jwtToken}, #{refreshToken}, #{dtCreazione})")
    void insertWSsoJwtToken(WSsoJwtToken wSsoJwtToken);

    @Delete("DELETE FROM w_ssojwttoken WHERE syslogin = #{syslogin}")
    void deleteWSsoJwtTokenBySyslogin(@Param("syslogin") final String syslogin);

    @Select("SELECT syslogin as syslogin, authcode as authorizationCode, jwttoken as jwtToken, refreshtoken as refreshToken, dtcreazione as dtCreazione FROM w_ssojwttoken WHERE authcode = #{authorizationCode}")
    WSsoJwtToken findWSsoJwtTokenByAuthorizationCode(@Param("authorizationCode") final String authorizationCode);
    
    @Select("SELECT loa FROM usr_loa WHERE syscon = #{syscon} and provider = #{provider}")
    public Long getUserLoa(@Param("syscon") final Long syscon, @Param("codein") final String codein, @Param("provider") final String provider);
    
    @Update("update usr_loa set loa= #{loa}, authdata = #{authData} where syscon = #{syscon} and codein = #{codein} and provider = #{provider}")
    public void updateLoa(@Param("syscon") final Long syscon, @Param("codein") final String codein, @Param("provider") final String provider, @Param("loa") final Long loa, @Param("authData") final Date authData);
    
    @Insert("insert into usr_loa (loa,authdata, syscon,codein, provider) values (#{loa}, #{authData}, #{syscon}, #{codein}, #{provider})")
    public void insertLoa(@Param("syscon") final Long syscon, @Param("codein") final String codein, @Param("loa") final Long loa, @Param("authData") final Date authData, @Param("provider") final String provider);

    @Insert("INSERT INTO stoutesys(sysnom, syspwd, syscon, sysdat, syslogin) VALUES (#{sysnom}, #{syspwd}, #{syscon}, #{sysdat}, #{syslogin})")
    void insertStoUteSysChangedPassword(final StoUteSys stoUteSys);

    @Select("SELECT syslogin FROM usrsys WHERE syscon = #{syscon}")
    String findUsernameBySyscon(@Param("syscon") final Long syscon);

    @Insert("insert into tecni (CODTEC, NOMETEI, COGTEI, NOMTEC, CFTEC, cgentei, indtec, ncitec, protec, captec, loctec, cittec, teltec, faxtec, ematec, syscon )  VALUES (#{codice},#{nome},#{cognome},#{nominativo},#{cf},#{stazioneAppaltante},#{indirizzo},#{numCivico},#{provincia},#{cap},#{comune},#{codIstat},#{telefono},#{fax},#{email},#{syscon})")
    public void insertTecnico(RupInsertForm form);

    @Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE UPPER(cftec) = #{cf} and UPPER(cgentei) = #{stazioneAppaltante}")
    public List<RupEntry> getTecnici(@Param("cf") String cf, @Param("stazioneAppaltante") String stazioneAppaltante);

    @Update("UPDATE tecni set syscon = #{syscon} where codtec = #{codice}")
    public void updateTecnico(@Param("codice") String codice, @Param("syscon") Long syscon);

    @Select("select tipo, oggetto, descr, ord from w_oggetti")
    public List<OggettoEntry> getWOggetti();
}