package it.appaltiecontratti.monitoraggiocontratti.attigenerali.mapper;

import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.AllegatoEntry;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.AttoGeneraleEntry;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.entries.RupEntry;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.forms.AttoGeneraleUpdateForm;
import it.appaltiecontratti.monitoraggiocontratti.attigenerali.entity.forms.RicercaAttiGeneraliForm;
import it.appaltiecontratti.monitoraggiocontratti.contratti.entity.entries.LogEventiEntry;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;

import java.util.Date;
import java.util.List;

/**
 * DAO Interface per l'estrazione delle informazioni relative alla gestione di
 * avvisi
 * 
 * @author andrea.chinellato
 */
@Mapper
public interface AttiGeneraliMapper {

    public List<AttoGeneraleEntry> getListaAttiGeneraliMap(RicercaAttiGeneraliForm form, RowBounds rowBounds);

    public int countSearchAttiGenerali(RicercaAttiGeneraliForm form);

    public AttoGeneraleEntry getDettaglioAttoGeneraleMap(RicercaAttiGeneraliForm form);

    public List<AllegatoEntry> getListaAllegatiAttoGeneraleMap(String idAtto);

    public void updateDettaglioAttoGenerale(AttoGeneraleUpdateForm form);

    public void insertLogEventi(LogEventiEntry entry);

    public AllegatoEntry getAllegatoAttoGenerale(@Param("idAtto") String idAtto, @Param("idAllegato") Long idAllegato);

    @Insert("INSERT INTO w9atti_generali (idatto, codein, tipologia, dataatto, numeroatto, descri, primapubblicazione, dataprimapubb, datapubbsistema, datascadenza, rup, utente_prop, idufficio, utente_canc, datacanc, motivo_canc) VALUES (#{idAtto}, #{stazioneAppaltante}, #{tipologia}, #{dataAtto}, #{numeroAtto}, #{descrizione}, #{primaPubb}, #{dataPrimaPubb}, #{dataPubbSistema}, #{dataScadenza}, #{rup}, #{utenteProp}, #{idUfficio}, #{utenteCanc}, #{dataCanc}, #{motivoCanc})")
    public void insertNuovoAttoGenerale(AttoGeneraleEntry attoGeneraleEntry);

    @Insert("INSERT INTO w9allegati(idallegato, key01, key02, key03, tabella, descri, file_allegato, url, tipo_file, num_ordine, utente_creatore, dataaggiunta, utente_canc, datacanc, motivo_canc) VALUES (#{idAllegato}, #{key01}, #{key02}, #{key03}, #{tabella}, #{descrizione}, #{fileAllegato}, #{url}, #{tipoFile}, #{numOrdine}, #{utenteCreatore}, #{dataAggiunta}, #{utenteCanc}, #{dataCanc}, #{motivoCanc})")
    public void insertAllegatoAttoGenerale(AllegatoEntry form);

    @Update("UPDATE w9allegati SET descri = #{descrizione}, file_allegato = #{fileAllegato}, url = #{url} WHERE key01 = #{key01} and idallegato = #{idAllegato}")
    public void updateAllegatoAttoGenerale(AllegatoEntry form);

    @Select("SELECT idallegato, key01, tabella, num_ordine, url, tipo_file as tipoFile from w9allegati WHERE key01 = #{idAtto}")
    public List<AllegatoEntry> getAttoGeneraleDocumentsWithoutAllegato(@Param("idAtto") final Long idAtto);

    @Delete("DELETE from w9allegati WHERE key01 = #{idAtto} and idallegato = #{idAllegato}")
    public void deleteAttoGeneraleDocs(@Param("idAtto") final String idAtto, @Param("idAllegato") Long idAllegato);

    @Update("UPDATE w9allegati SET motivo_canc = #{motivoCanc}, utente_canc = #{syscon}, datacanc = #{dataCanc} WHERE key01 = #{idAtto} and idallegato = #{idAllegato}")
    public void deleteLogicaAttoGeneraleDoc(@Param("idAtto") final String idAtto, @Param("idAllegato") Long idAllegato, @Param("syscon") final Long syscon, @Param("dataCanc") final Date dataCanc, @Param("motivoCanc") final String motivoCanc);

    @Select("SELECT coalesce(max(numdoc), 0) from w9allegato WHERE idallegato = #{idAllegato}")
    public Long getIdAllegatoAttoGeneraleUpdate(AttoGeneraleUpdateForm form);

    @Select("select codein from uffint where nomein = #{nomein}")
    public String getCodeinByNameSA(@Param("nomein") String nomein);

    @Select("select sysab3 from usrsys where syscon = #{syscon}")
    public String getRuolo(@Param("syscon") Long syscon);

    @Select("select nomein from uffint where codein = #{stazioneappaltante}")
    public String getNominativoSa(@Param("stazioneappaltante") String stazioneappaltante);

    @Select("SELECT nomtec from tecni WHERE codtec =#{codiceRup}")
    public String getRupBycod(@Param("codiceRup") String codiceRup);

    @Select("SELECT codtec as codice, nometei as nome, cogtei as cognome, nomtec as nominativo, cftec as cf, cgentei as stazioneAppaltante, indtec as indirizzo, ncitec as numCivico, protec as provincia, captec as cap, loctec as comune, cittec as codIstat, teltec as telefono, faxtec as fax, ematec as email from tecni WHERE codtec = #{id}")
    public RupEntry getTecnico(@Param("id") String id);

    @Delete("DELETE FROM w9atti_generali WHERE idatto = #{idAtto}")
    public void deleteAttoGenerale(@Param("idAtto") Long idAtto);

    @Delete("DELETE FROM w9allegati WHERE key01 = #{idAtto}")
    public void deleteAttoGeneraleDocsByIdAtto(@Param("idAtto") String idAtto);

    @Update("UPDATE w9atti_generali SET datapubbsistema = null WHERE idatto = #{idAtto}")
    public void annullaPubblicazione(@Param("idAtto") Long idAtto);

    @Update("UPDATE w9atti_generali SET motivo_canc = #{motivoCanc}, datacanc = #{dataCanc}, utente_canc = #{utenteCanc} WHERE idatto = #{idAtto}")
    public void annullaPubblicazioneMotivazione(@Param("idAtto") Long idAtto, @Param("motivoCanc") String motivoCanc, @Param("dataCanc") Date dataCanc, @Param("utenteCanc") Long utenteCanc);

    @Update("UPDATE w9atti_generali SET datapubbsistema = #{dataPubbSistema} WHERE idatto = #{idAtto}")
    public void pubblicaAttoGenerale(@Param("idAtto") Long idAtto, @Param("dataPubbSistema") Date dataPubbSistema);

    @Update("UPDATE w9atti_generali SET datapubbsistema = #{dataProgrammazione}, dataprimapubb = #{dataProgrammazione} WHERE idatto = #{idAtto}")
    public void pubblicaAttoGeneralePrimaPubblicazione(@Param("idAtto") Long idAtto, @Param("dataProgrammazione") Date dataProgrammazione);

    @Update("UPDATE w9atti_generali SET datapubbsistema = #{dataProgrammazione} WHERE idatto = #{idAtto}")
    public void pubblicaAttoGeneraleFuturo(@Param("idAtto") Long idAtto, @Param("dataProgrammazione") Date dataProgrammazione);

    @Select("SELECT codein FROM uffint WHERE nomein = #{nomein}")
    public String getStazioneAppaltanteCodein(@Param("nomein") String nomein);

    @Select("select valore from w_config where chiave = #{configPortaleTrasparenza} and codapp = #{codApp} ")
    public String getUrlAnac(String configPortaleTrasparenza, String codApp);
}
