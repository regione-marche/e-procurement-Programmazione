package it.appaltiecontratti.tabellati.bl;

import it.appaltiecontratti.sicurezza.bl.UserManager;
import it.appaltiecontratti.sicurezza.bl.WConfigManager;
import it.appaltiecontratti.sicurezza.entity.UserAuthClaimsDTO;
import it.appaltiecontratti.tabellati.constants.Constants;
import it.appaltiecontratti.tabellati.entity.*;
import it.appaltiecontratti.tabellati.exception.ExistingCFException;
import it.appaltiecontratti.tabellati.mapper.SqlMapper;
import it.appaltiecontratti.tabellati.mapper.TabellatiMapper;
import it.toscana.regione.sitat.service.authentication.utils.security.CriptazioneException;
import it.toscana.regione.sitat.service.authentication.utils.security.FactoryCriptazioneByte;
import it.toscana.regione.sitat.service.authentication.utils.security.ICriptazioneByte;
import it.toscana.regione.sitat.service.utils.UtilityFiscali;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


/**
 * Manager per la gestione della business logic.
 *
 * @author Michele.diNapoli
 */
@Component(value = "tabellatiManager")
public class TabellatiManager {

    /**
     * Logger di classe.
     */
    private final static Logger logger = LogManager.getLogger(TabellatiManager.class);

    @Autowired
    private SqlMapper sqlMapper;

    @Autowired
    private WGenChiaviManager wgcManager;

    @Autowired
    private UserManager userManager;

    @Autowired
    private WConfigManager wConfigManager;

    /**
     * Dao MyBatis con le primitive di estrazione dei dati.
     */
    @Autowired
    private TabellatiMapper tabellatiMapper;

    private static final String ENTITA_COLLEGATE_UFFICI = "UFFICI.W9";
    private static final String ENTITA_COLLEGATE_TECNI = "TECNI.GENE";
    private static final String ENTITA_COLLEGATE_IMPR = "IMPR.GENE";

    public static final String PROP_NPA_NOMECOMPONENTE = "npa.nomeComponente";
    public static final String PROP_NPA_VERSIONECOMPONENTE = "npa.versioneComponente";
    public static final String PROP_NPA_NOMEPIATTAFORMA = "npa.nomePiattaforma";
    public static final String PROP_NPA_VERSIONEPIATTAFORMA = "npa.versionePiattaforma";

    private static final String CONFIG_CHIAVE_APP = "it.maggioli.eldasoft.wslogin.jwtKey";

    public MultiTabellatoResult getValoriMultipli(List<String> cods) {

        Map<String, List<TabellatoIstatEntry>> map = new HashMap<>();
        MultiTabellatoResult risultato = new MultiTabellatoResult();
        for (String cod : cods) {
            try {
                if (cod.equals("Province")) {
                    List<TabellatoIstatEntry> righe = this.getProvince();
                    map.put(cod, righe);
                } else if (cod.equals("Regioni")) {
                    List<TabellatoIstatEntry> righe = this.getRegioni();
                    map.put(cod, righe);
                } else if (cod.equals("Nazioni")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("Ag010");
                    map.put(cod, righe);
                } else if (cod.equals("NazioniEstere")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1Estero("Ag010");
                    map.put(cod, righe);
                } else if (cod.equals("ModalitaRiaggiudicazione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3023");
                    map.put(cod, righe);
                } else if (cod.equals("SettoriSpeciali")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3009");
                    map.put(cod, righe);
                } else if (cod.equals("TipoAtto")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9014");
                    map.put(cod, righe);
                } else if (cod.equals("Comuni")) {
                    List<TabellatoIstatEntry> righe = this.getComuni();
                    map.put(cod, righe);
                } else if (cod.equals("IncontriOrganiVigilanza")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9005");
                    map.put(cod, righe);
                } else if (cod.equals("Situazione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9007");
                    map.put(cod, righe);
                } else if (cod.equals("Provenienza")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9018");
                    map.put(cod, righe);
                } else if (cod.equals("Indizione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3008");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaSA")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3001");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaProcedura")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3024");
                    map.put(cod, righe);
                } else if (cod.equals("SceltaContraente50")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9020");
                    map.put(cod, righe);
                } else if (cod.equals("SceltaContraente")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3005");
                    map.put(cod, righe);
                } else if (cod.equals("MotivoCompletamento")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3997");
                    map.put(cod, righe);
                } else if (cod.equals("Area")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9004");
                    map.put(cod, righe);
                } else if (cod.equals("Fase")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3020");
                    map.put(cod, righe);
                } else if (cod.equals("TipoInvio")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3998");
                    map.put(cod, righe);
                } else if (cod.equals("TipoAvviso")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3996");
                    map.put(cod, righe);
                } else if (cod.equals("SN")) {
                    TabellatoIstatEntry S = new TabellatoIstatEntry();
                    TabellatoIstatEntry N = new TabellatoIstatEntry();
                    S.setCodice("1");
                    S.setDescrizione("Si");
                    N.setCodice("2");
                    N.setDescrizione("No");
                    List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
                    righe.add(S);
                    righe.add(N);
                    map.put(cod, righe);
                } else if (cod.equals("TipoAppalto")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z05");
                    map.put(cod, righe);
                } else if (cod.equals("CriterioAggiudicazione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3007");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaAggiudicatario")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3010");
                    map.put(cod, righe);
                } else if (cod.equals("RuoloAssociazione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3011");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaCC")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9019");
                    map.put(cod, righe);
                } else if (cod.equals("TipoRealizzazione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3999");
                    map.put(cod, righe);
                } else if (cod.equals("FormaGiuridica")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("G_043");
                    map.put(cod, righe);
                } else if (cod.equals("Entita")) {
                    TabellatoIstatEntry avvisi = new TabellatoIstatEntry();
                    TabellatoIstatEntry bandi = new TabellatoIstatEntry();
                    TabellatoIstatEntry esiti = new TabellatoIstatEntry();
                    avvisi.setCodice("3");
                    avvisi.setDescrizione("Avvisi");
                    bandi.setCodice("1");
                    bandi.setDescrizione("Bandi");
                    esiti.setCodice("2");
                    esiti.setDescrizione("Esiti");
                    List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
                    righe.add(avvisi);
                    righe.add(bandi);
                    righe.add(esiti);
                    map.put(cod, righe);

                } else if (cod.equals("Stato")) {
                    TabellatoIstatEntry inCorso = new TabellatoIstatEntry();
                    TabellatoIstatEntry scaduti = new TabellatoIstatEntry();
                    TabellatoIstatEntry tutti = new TabellatoIstatEntry();
                    inCorso.setCodice("1");
                    inCorso.setDescrizione("In corso");
                    scaduti.setCodice("2");
                    scaduti.setDescrizione("Scaduti");
                    tutti.setCodice("3");
                    tutti.setDescrizione("Tutti");
                    List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
                    righe.add(inCorso);
                    righe.add(scaduti);
                    righe.add(tutti);
                    map.put(cod, righe);

                } else if (cod.equals("StatoCom")) {
                    TabellatoIstatEntry positivo = new TabellatoIstatEntry();
                    TabellatoIstatEntry negativo = new TabellatoIstatEntry();
                    TabellatoIstatEntry daEsportare = new TabellatoIstatEntry();
                    TabellatoIstatEntry ricevuta = new TabellatoIstatEntry();
                    TabellatoIstatEntry test = new TabellatoIstatEntry();

                    ricevuta.setCodice("1");
                    ricevuta.setDescrizione("Ricevuta");
                    positivo.setCodice("2");
                    positivo.setDescrizione("Importata");
                    negativo.setCodice("3");
                    negativo.setDescrizione("Errore");
                    daEsportare.setCodice("4");
                    daEsportare.setDescrizione("Warning");
                    test.setCodice("99");
                    test.setDescrizione("Test");
                    List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
                    righe.add(positivo);
                    righe.add(negativo);
                    righe.add(daEsportare);
                    righe.add(ricevuta);
                    righe.add(test);
                    map.put(cod, righe);
                } else if (cod.equals("CategorieAll")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z03");
                    map.put(cod, righe);
                } else if (cod.equals("Categorie")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z03");
                    map.put(cod, righe);
                } else if (cod.equals("CategorieForniture")) {
                    List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
                    TabellatoIstatEntry tie = new TabellatoIstatEntry();
                    tie.setCodice("FB");
                    tie.setDescrizione("Fornitura di beni");
                    righe.add(tie);
                    map.put(cod, righe);
                } else if (cod.equals("CategorieServizi")) {
                    List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
                    TabellatoIstatEntry tie = new TabellatoIstatEntry();
                    tie.setCodice("FS");
                    tie.setDescrizione("Fornitura di servizi");
                    righe.add(tie);
                    map.put(cod, righe);
                } else if (cod.equals("CodStrumento")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z01");
                    map.put(cod, righe);
                } else if (cod.equals("Classe")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z11");
                    map.put(cod, righe);
                } else if (cod.equals("Settore")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z08");
                    map.put(cod, righe);
                } else if (cod.equals("TipoProgramma")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9002");
                    map.put(cod, righe);
                } else if (cod.equals("Determinazioni")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx02");
                    map.put(cod, righe);
                } else if (cod.equals("Ambito")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx04");
                    map.put(cod, righe);
                } else if (cod.equals("Causa")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx05");
                    map.put(cod, righe);
                } else if (cod.equals("StatoRealizzazione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx06");
                    map.put(cod, righe);
                } else if (cod.equals("DestinazioneUso")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx07");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaIntervento")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx01");
                    map.put(cod, righe);
                } else if (cod.equals("CategoriaIntervento")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato5("PTj01");
                    map.put(cod, righe);
                } else if (cod.equals("Priorita")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT008");
                    map.put(cod, righe);
                } else if (cod.equals("Finalita")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx03");
                    map.put(cod, righe);
                } else if (cod.equals("StatoProgettazione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx09");
                    map.put(cod, righe);
                } else if (cod.equals("TrasferimentoImmobile")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT013");
                    map.put(cod, righe);
                } else if (cod.equals("ImmobileDisponibile")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT014");
                    map.put(cod, righe);
                } else if (cod.equals("ProgrammaDismissione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT015");
                    map.put(cod, righe);
                } else if (cod.equals("TipoProprieta")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("A2137");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaCapitalePrivato")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx08");
                    map.put(cod, righe);
                } else if (cod.equals("TipoDisponibilita")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT016");
                    map.put(cod, righe);
                } else if (cod.equals("TipoCollaudo")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9025");
                    map.put(cod, righe);
                } else if (cod.equals("VariatoLavori")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT011");
                    map.put(cod, righe);
                } else if (cod.equals("VariatoAcquisti")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT010");
                    map.put(cod, righe);
                } else if (cod.equals("Acquisto")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT018");
                    map.put(cod, righe);
                } else if (cod.equals("UnitaMisura")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT019");
                    map.put(cod, righe);
                } else if (cod.equals("ProceduraAffidamento")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT020");
                    map.put(cod, righe);
                } else if (cod.equals("Valutazione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT021");
                    map.put(cod, righe);
                } else if (cod.equals("AcquistoRicompreso")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT022");
                    map.put(cod, righe);
                } else if (cod.equals("MesePrevisto")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3995");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaInterventoDM112011")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabsche(
                            "select tabcod1 codice, tabdesc descrizione from tabsche where TABCOD='S2008' order by tabcod1");
                    map.put(cod, righe);
                } else if (cod.equals("CategoriaInterventoDM112011")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabsche(
                            "select tabcod1 || tabcod2 codice, tabdesc descrizione from tabsche where TABCOD='S2005' and TABCOD2<> '0' order by tabcod1, tabcod2");
                    map.put(cod, righe);
                } else if (cod.equals("FinalitaDM112011")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabsche(
                            "select tabcod1 codice, tabdesc descrizione from tabsche where TABCOD='S2016' order by tabcod3");
                    map.put(cod, righe);
                } else if (cod.equals("StatoProgettazioneDM112011")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabsche(
                            "select tabcod1 codice, tabdesc descrizione from tabsche where TABCOD='S2017' order by tabcod3");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaCapitalePrivatoDM112011")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabsche(
                            "select tabcod1 codice, tabdesc descrizione from tabsche where TABCOD='S2015' order by tabcod1");
                    map.put(cod, righe);
                } else if (cod.equals("PrestazioniComprese")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3003");
                    map.put(cod, righe);
                } else if (cod.equals("ModalitaAcquisizioneForniture")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3019");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaLavoro")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3002");
                    map.put(cod, righe);
                } else if (cod.equals("Condizione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3006");
                    map.put(cod, righe);
                } else if (cod.equals("EsitoProcedura")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3022");
                    map.put(cod, righe);
                } else if (cod.equals("Avvalimento")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z09");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaSoggetto")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3004");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaFinanziamento")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z02");
                    map.put(cod, righe);
                } else if (cod.equals("ImpresaPartecipante")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9015");
                    map.put(cod, righe);
                } else if (cod.equals("Ritardo")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z04");
                    map.put(cod, righe);
                } else if (cod.equals("Pagamento")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3012");
                    map.put(cod, righe);
                } else if (cod.equals("MotivoSospensione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3016");
                    map.put(cod, righe);
                } else if (cod.equals("FlagVariante")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W9z03");
                    map.put(cod, righe);
                } else if (cod.equals("motivoRevPrezzi")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9027");
                    map.put(cod, righe);
                } else if (cod.equals("FlagImporti")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W9z01");
                    map.put(cod, righe);
                } else if (cod.equals("MotivoInterruzione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3013");
                    map.put(cod, righe);
                } else if (cod.equals("MotivoRisoluzione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3014");
                    map.put(cod, righe);
                } else if (cod.equals("FlagOneri")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z06");
                    map.put(cod, righe);
                } else if (cod.equals("ModoCollaudo")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3015");
                    map.put(cod, righe);
                } else if (cod.equals("EsitoCollaudo")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z10");
                    map.put(cod, righe);
                } else if (cod.equals("FlagSingoloCommissione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W9z02");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaComunicazione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z07");
                    map.put(cod, righe);
                } else if (cod.equals("RiscontroIrregolarita")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9006");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaOpera")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9009");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaInterventoCantiere")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9010");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaCostruttiva")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9011");
                    map.put(cod, righe);
                } else if (cod.equals("TipologiaStazioneAppaltante")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("G_044");
                    map.put(cod, righe);
                } else if (cod.equals("StatoComunicazioneSCP")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9022");
                    map.put(cod, righe);
                } else if (cod.equals("PercentualeIva")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9013");
                    map.put(cod, righe);
                    // SDK gestione utenti
                } else if (cod.equals("UfficioAppartenenza")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("G_022");
                    map.put(cod, righe);
                } else if (cod.equals("CategoriaUtente")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("G_059");
                    map.put(cod, righe);
                } else if (cod.equals("LivelloEvento")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W_006");
                    map.put(cod, righe);
                } else if (cod.equals("TipoNorma")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT017");
                    map.put(cod, righe);
                } else if (cod.equals("ModalitaIndizione9")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3033");
                    map.put(cod, righe);
                } else if (cod.equals("GaraUrgenza")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3035");
                    map.put(cod, righe);
                } else if (cod.equals("DerogaQualificazioneSa")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3041");
                    map.put(cod, righe);
                } else if (cod.equals("CategoriaMerc")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3031");
                    map.put(cod, righe);
                } else if (cod.equals("MotivoCollegamento")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3037");
                    map.put(cod, righe);
                } else if (cod.equals("PrevisioneQuota")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z22");
                    map.put(cod, righe);
                } else if (cod.equals("TipoDocumentoModello")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("AgzC5");
                    map.put(cod, righe);
                } else if (cod.equals("TipoParametroModello")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("G_x02");
                    map.put(cod, righe);
                } else if (cod.equals("ListaTabellati")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato6();
                    map.put(cod, righe);
                } else if (cod.equals("MotivoMancatoSub")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9028");
                    map.put(cod, righe);
                } else if (cod.equals("MotivoMancataEsec")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9029");
                    map.put(cod, righe);
                } else if (cod.equals("tipoProgettazione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9031");
                    map.put(cod, righe);
                } else if(cod.equals("TipoAttoGenerale")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9032");
                    map.put(cod, righe);
                } else if(cod.equals("TipoAttoGeneraleAG")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1AG("W9032");
                    map.put(cod, righe);
                } else if(cod.equals("TipoAttoGeneraleMA")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1MA("W9032");
                    map.put(cod, righe);
                } else if(cod.equals("tipoParametroReport")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("G_x01");
                    map.put(cod, righe);
                } else if(cod.equals("RuoloVaraggi")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9036");
                    map.put(cod, righe);
                } else if(cod.equals("TipoOperatoreEconomico")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9033");
                    map.put(cod, righe);
                } else if(cod.equals("FlagAvvalimento")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9034");
                    map.put(cod, righe);
                } else if(cod.equals("MotivoVariazione")) {
                    List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9035");
                    map.put(cod, righe);
                } else {
                    risultato.setErrorData(TabellatoIstatResult.ERROR_NOT_FOUND + " " + cod);
                }
            } catch (Throwable t) {
                // qualsiasi sia l'errore si traccia nel log e si ritorna un codice
                // fisso ed il messaggio allegato all'eccezione come errore
                logger.error("Errore inaspettato durante l'estrazione del tabellato " + cod, t);
                risultato.setErrorData(TabellatoIstatResult.ERROR_UNEXPECTED + ": " + t.getMessage());
            }
        }
        risultato.setData(map);
        return risultato;
    }

    public TabellatoIstatResult getValori(String cod) {

        TabellatoIstatResult risultato = new TabellatoIstatResult();
        try {
            if (cod.equals("Province")) {
                List<TabellatoIstatEntry> righe = this.getProvince();
                risultato.setData(righe);
            } else if (cod.equals("Regioni")) {
                List<TabellatoIstatEntry> righe = this.getRegioni();
                risultato.setData(righe);
            } else if (cod.equals("Nazioni")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("Ag010");
                risultato.setData(righe);
            } else if (cod.equals("NazioniEstere")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1Estero("Ag010");
                risultato.setData(righe);
            } else if (cod.equals("ModalitaRiaggiudicazione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3023");
                risultato.setData(righe);
            } else if (cod.equals("SettoriSpeciali")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3009");
                risultato.setData(righe);
            } else if (cod.equals("TipoAtto")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9014");
                risultato.setData(righe);
            } else if (cod.equals("Comuni")) {
                List<TabellatoIstatEntry> righe = this.getComuni();
                risultato.setData(righe);
            } else if (cod.equals("Situazione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9007");
                risultato.setData(righe);
            } else if (cod.equals("IncontriOrganiVigilanza")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9005");
                risultato.setData(righe);
            } else if (cod.equals("Provenienza")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9018");
                risultato.setData(righe);
            } else if (cod.equals("Indizione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3008");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaSA")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3001");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaProcedura")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3024");
                risultato.setData(righe);
            } else if (cod.equals("SceltaContraente50")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9020");
                risultato.setData(righe);
            } else if (cod.equals("SceltaContraente")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3005");
                risultato.setData(righe);
            } else if (cod.equals("MotivoCompletamento")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3997");
                risultato.setData(righe);
            } else if (cod.equals("Area")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9004");
                risultato.setData(righe);
            } else if (cod.equals("Fase")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3020");
                risultato.setData(righe);
            } else if (cod.equals("TipoInvio")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3998");
                risultato.setData(righe);
            } else if (cod.equals("TipoAvviso")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3996");
                risultato.setData(righe);
            } else if (cod.equals("SN")) {
                TabellatoIstatEntry S = new TabellatoIstatEntry();
                TabellatoIstatEntry N = new TabellatoIstatEntry();
                S.setCodice("1");
                S.setDescrizione("Si");
                N.setCodice("2");
                N.setDescrizione("No");
                List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
                righe.add(S);
                righe.add(N);
                risultato.setData(righe);
            } else if (cod.equals("TipoAppalto")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z05");
                risultato.setData(righe);
            } else if (cod.equals("CriterioAggiudicazione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3007");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaAggiudicatario")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3010");
                risultato.setData(righe);
            } else if (cod.equals("RuoloAssociazione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3011");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaCC")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9019");
                risultato.setData(righe);
            } else if (cod.equals("TipoRealizzazione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3999");
                risultato.setData(righe);
            } else if (cod.equals("FormaGiuridica")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("G_043");
                risultato.setData(righe);
            } else if (cod.equals("Entita")) {
                TabellatoIstatEntry avvisi = new TabellatoIstatEntry();
                TabellatoIstatEntry bandi = new TabellatoIstatEntry();
                TabellatoIstatEntry esiti = new TabellatoIstatEntry();
                avvisi.setCodice("3");
                avvisi.setDescrizione("Avvisi");
                bandi.setCodice("1");
                bandi.setDescrizione("Bandi");
                esiti.setCodice("2");
                esiti.setDescrizione("Esiti");
                List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
                righe.add(avvisi);
                righe.add(bandi);
                righe.add(esiti);
                risultato.setData(righe);

            } else if (cod.equals("Stato")) {
                TabellatoIstatEntry inCorso = new TabellatoIstatEntry();
                TabellatoIstatEntry scaduti = new TabellatoIstatEntry();
                TabellatoIstatEntry tutti = new TabellatoIstatEntry();
                inCorso.setCodice("1");
                inCorso.setDescrizione("In corso");
                scaduti.setCodice("2");
                scaduti.setDescrizione("Scaduti");
                tutti.setCodice("3");
                tutti.setDescrizione("Tutti");
                List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
                righe.add(inCorso);
                righe.add(scaduti);
                righe.add(tutti);
                risultato.setData(righe);
            } else if (cod.equals("StatoCom")) {
                TabellatoIstatEntry positivo = new TabellatoIstatEntry();
                TabellatoIstatEntry negativo = new TabellatoIstatEntry();
                TabellatoIstatEntry daEsportare = new TabellatoIstatEntry();
                TabellatoIstatEntry ricevuta = new TabellatoIstatEntry();
                TabellatoIstatEntry test = new TabellatoIstatEntry();

                ricevuta.setCodice("1");
                ricevuta.setDescrizione("Ricevuta");
                positivo.setCodice("2");
                positivo.setDescrizione("Importata");
                negativo.setCodice("3");
                negativo.setDescrizione("Errore");
                daEsportare.setCodice("4");
                daEsportare.setDescrizione("Warning");
                test.setCodice("99");
                test.setDescrizione("Test");
                List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
                righe.add(positivo);
                righe.add(negativo);
                righe.add(daEsportare);
                righe.add(ricevuta);
                righe.add(test);
                risultato.setData(righe);
            } else if (cod.equals("CodStrumento")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z01");
                risultato.setData(righe);
            } else if (cod.equals("CategorieAll")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z03");
                risultato.setData(righe);
            } else if (cod.equals("Categorie")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z03");
                risultato.setData(righe);
            } else if (cod.equals("CategorieForniture")) {
                List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
                TabellatoIstatEntry tie = new TabellatoIstatEntry();
                tie.setCodice("FB");
                tie.setDescrizione("Fornitura di beni");
                righe.add(tie);
                risultato.setData(righe);
            } else if (cod.equals("CategorieServizi")) {
                List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
                TabellatoIstatEntry tie = new TabellatoIstatEntry();
                tie.setCodice("FS");
                tie.setDescrizione("Fornitura di servizi");
                righe.add(tie);
                risultato.setData(righe);
            } else if (cod.equals("Classe")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z11");
                risultato.setData(righe);
            } else if (cod.equals("Settore")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z08");
                risultato.setData(righe);
            } else if (cod.equals("TipoProgramma")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9002");
                risultato.setData(righe);
            } else if (cod.equals("Determinazioni")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx02");
                risultato.setData(righe);
            } else if (cod.equals("Ambito")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx04");
                risultato.setData(righe);
            } else if (cod.equals("Causa")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx05");
                risultato.setData(righe);
            } else if (cod.equals("StatoRealizzazione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx06");
                risultato.setData(righe);
            } else if (cod.equals("DestinazioneUso")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx07");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaIntervento")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx01");
                risultato.setData(righe);
            } else if (cod.equals("CategoriaIntervento")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato5("PTj01");
                risultato.setData(righe);
            } else if (cod.equals("Priorita")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT008");
                risultato.setData(righe);
            } else if (cod.equals("Finalita")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx03");
                risultato.setData(righe);
            } else if (cod.equals("StatoProgettazione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx09");
                risultato.setData(righe);
            } else if (cod.equals("TrasferimentoImmobile")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT013");
                risultato.setData(righe);
            } else if (cod.equals("ImmobileDisponibile")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT014");
                risultato.setData(righe);
            } else if (cod.equals("ProgrammaDismissione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT015");
                risultato.setData(righe);
            } else if (cod.equals("TipoProprieta")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("A2137");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaCapitalePrivato")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("PTx08");
                risultato.setData(righe);
            } else if (cod.equals("TipoDisponibilita")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT016");
                risultato.setData(righe);
            } else if (cod.equals("VariatoLavori")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT011");
                risultato.setData(righe);
            } else if (cod.equals("VariatoAcquisti")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT010");
                risultato.setData(righe);
            } else if (cod.equals("Acquisto")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT018");
                risultato.setData(righe);
            } else if (cod.equals("UnitaMisura")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT019");
                risultato.setData(righe);
            } else if (cod.equals("ProceduraAffidamento")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT020");
                risultato.setData(righe);
            } else if (cod.equals("Valutazione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT021");
                risultato.setData(righe);
            } else if (cod.equals("AcquistoRicompreso")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT022");
                risultato.setData(righe);
            } else if (cod.equals("MesePrevisto")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3995");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaInterventoDM112011")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabsche(
                        "select tabcod1 codice, tabdesc descrizione from tabsche where TABCOD='S2008' order by tabcod1");
                risultato.setData(righe);
            } else if (cod.equals("CategoriaInterventoDM112011")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabsche(
                        "select tabcod1 || tabcod2 codice, tabdesc descrizione from tabsche where TABCOD='S2005' and TABCOD2<> '0' order by tabcod1, tabcod2");
                risultato.setData(righe);
            } else if (cod.equals("FinalitaDM112011")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabsche(
                        "select tabcod1 codice, tabdesc descrizione from tabsche where TABCOD='S2016' order by tabcod3");
                risultato.setData(righe);
            } else if (cod.equals("StatoProgettazioneDM112011")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabsche(
                        "select tabcod1 codice, tabdesc descrizione from tabsche where TABCOD='S2017' order by tabcod3");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaCapitalePrivatoDM112011")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabsche(
                        "select tabcod1 codice, tabdesc descrizione from tabsche where TABCOD='S2015' order by tabcod1");
                risultato.setData(righe);
            } else if (cod.equals("PrestazioniComprese")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3003");
                risultato.setData(righe);
            } else if (cod.equals("ModalitaAcquisizioneForniture")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3019");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaLavoro")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3002");
                risultato.setData(righe);
            } else if (cod.equals("Condizione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3006");
                risultato.setData(righe);
            } else if (cod.equals("EsitoProcedura")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3022");
                risultato.setData(righe);
            } else if (cod.equals("Avvalimento")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z09");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaSoggetto")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3004");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaFinanziamento")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z02");
                risultato.setData(righe);
            } else if (cod.equals("ImpresaPartecipante")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9015");
                risultato.setData(righe);
            } else if (cod.equals("Ritardo")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z04");
                risultato.setData(righe);
            } else if (cod.equals("Pagamento")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3012");
                risultato.setData(righe);
            } else if (cod.equals("MotivoSospensione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3016");
                risultato.setData(righe);
            } else if (cod.equals("FlagVariante")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W9z03");
                risultato.setData(righe);
            } else if (cod.equals("motivoRevPrezzi")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9027");
                risultato.setData(righe);
            } else if (cod.equals("FlagImporti")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W9z01");
                risultato.setData(righe);
            } else if (cod.equals("MotivoInterruzione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3013");
                risultato.setData(righe);
            } else if (cod.equals("MotivoRisoluzione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3014");
                risultato.setData(righe);
            } else if (cod.equals("FlagOneri")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z06");
                risultato.setData(righe);
            } else if (cod.equals("ModoCollaudo")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3015");
                risultato.setData(righe);
            } else if (cod.equals("EsitoCollaudo")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z10");
                risultato.setData(righe);
            } else if (cod.equals("FlagSingoloCommissione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W9z02");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaComunicazione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z07");
                risultato.setData(righe);
            } else if (cod.equals("RiscontroIrregolarita")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9006");
                risultato.setData(righe);
            } else if (cod.equals("TipoOpera")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9009");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaOpera")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9009");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaInterventoCantiere")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9010");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaCostruttiva")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9011");
                risultato.setData(righe);
            } else if (cod.equals("TipologiaStazioneAppaltante")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("G_044");
                risultato.setData(righe);
            } else if (cod.equals("StatoComunicazioneSCP")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9022");
                risultato.setData(righe);
            } else if (cod.equals("PercentualeIva")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9013");
                risultato.setData(righe);
                // SDK gestione utenti
            } else if (cod.equals("UfficioAppartenenza")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("G_022");
                risultato.setData(righe);
            } else if (cod.equals("CategoriaUtente")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("G_059");
                risultato.setData(righe);
            } else if (cod.equals("LivelloEvento")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W_006");
                risultato.setData(righe);
            } else if (cod.equals("TipoNorma")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("PT017");
                risultato.setData(righe);
            } else if (cod.equals("ModalitaIndizione9")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3033");
                risultato.setData(righe);
            } else if (cod.equals("GaraUrgenza")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3035");
                risultato.setData(righe);
            } else if (cod.equals("DerogaQualificazioneSa")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3041");
                risultato.setData(righe);
            } else if (cod.equals("CategoriaMerc")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3031");
                risultato.setData(righe);
            } else if (cod.equals("MotivoCollegamento")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W3037");
                risultato.setData(righe);
            } else if (cod.equals("PrevisioneQuota")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("W3z22");
                risultato.setData(righe);
            } else if (cod.equals("TipoDocumentoModello")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato2("AgzC5");
                risultato.setData(righe);
            } else if (cod.equals("TipoParametroModello")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("G_x02");
                risultato.setData(righe);
            } else if (cod.equals("ListaTabellati")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato6();
                risultato.setData(righe);
            } else if (cod.equals("MotivoMancatoSub")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9028");
                risultato.setData(righe);
            } else if (cod.equals("MotivoMancataEsec")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9029");
                risultato.setData(righe);
            } else if (cod.equals("tipoProgettazione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9031");
                risultato.setData(righe);
            } else if(cod.equals("TipoAttoGenerale")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9032");
                risultato.setData(righe);
            } else if(cod.equals("TipoAttoGeneraleAG")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1AG("W9032");
                risultato.setData(righe);
            } else if(cod.equals("TipoAttoGeneraleMA")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1MA("W9032");
                risultato.setData(righe);
            } else if(cod.equals("tipoParametroReport")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato3("G_x01");
                risultato.setData(righe);
            }  else if(cod.equals("RuoloVaraggi")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9036");
                risultato.setData(righe);
            } else if(cod.equals("TipoOperatoreEconomico")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9033");
                risultato.setData(righe);
            } else if(cod.equals("FlagAvvalimento")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9034");
                risultato.setData(righe);
            } else if(cod.equals("MotivoVariazione")) {
                List<TabellatoIstatEntry> righe = this.tabellatiMapper.getTabellato1("W9035");
                risultato.setData(righe);
            } else {
                risultato.setErrorData(TabellatoIstatResult.ERROR_NOT_FOUND + " " + cod);
            }

        } catch (Throwable t) {
            // qualsiasi sia l'errore si traccia nel log e si ritorna un codice
            // fisso ed il messaggio allegato all'eccezione come errore
            logger.error("Errore inaspettato durante l'estrazione del tabellato " + cod, t);
            risultato.setErrorData(TabellatoIstatResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }

        return risultato;
    }

    public ResponseResult insertRup(RupInsertForm form) {
        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);

        try {
            CFPIVAInfoInsert cFPIVAInfoInsert = getCFPIVAInfoInsert(ECFPIVAInfoInsert.TECNI);
            if (form.getCf() != null) {
                form.setCf(form.getCf().toUpperCase());
            }
            String filtroSA = this.wConfigManager.getConfigurationValue(
                    Constants.APPLICATION_SA_FILTER);

            if (form.getCodice() != null && !"".equals(form.getCodice())) {
                if (cFPIVAInfoInsert.isBlockDuplicateCf()) {
                    Long occurencies;
                    if (filtroSA.contains("TECNI")) {
                        occurencies = this.tabellatiMapper.countExistingCFEditWithSa(form.getCf(), form.getCodice(),
                                form.getStazioneAppaltante());
                    } else {
                        occurencies = this.tabellatiMapper.countExistingCFEditWithoutSa(form.getCf(), form.getCodice());
                    }
                    if (occurencies > 0) {
                        throw new ExistingCFException(
                                "Il codice fiscale [ " + form.getCf() + " ] e' gia' utilizzato per un altro tecnico.");
                    }
                }

                if (cFPIVAInfoInsert.isCheckCfValid()) {
                    if (!UtilityFiscali.isValidCodiceFiscale(form.getCf(), true)
                            && !UtilityFiscali.isValidPartitaIVA(form.getCf())) {
                        risultato.setEsito(false);
                        risultato.setErrorData(ResponseResult.ERROR_INVALID_CF);
                        return risultato;
                    }
                }

                this.tabellatiMapper.updateRUP(form);
                risultato.setData(form.getCodice());
            } else {
                if (cFPIVAInfoInsert.isBlockDuplicateCf()) {
                    Long occurencies;
                    if (filtroSA.contains("TECNI")) {
                        occurencies = this.tabellatiMapper.countExistingCFNewWithSa(form.getCf(),
                                form.getStazioneAppaltante());
                    } else {
                        occurencies = this.tabellatiMapper.countExistingCFNewWithoutSa(form.getCf());
                    }
                    if (occurencies > 0) {
                        throw new ExistingCFException(
                                "Il codice fiscale [ " + form.getCf() + " ] e' gia' utilizzato per un altro tecnico.");
                    }
                }
                if (cFPIVAInfoInsert.isCheckCfValid()) {
                    if (!UtilityFiscali.isValidCodiceFiscale(form.getCf(), true)
                            && !UtilityFiscali.isValidPartitaIVA(form.getCf())) {
                        risultato.setEsito(false);
                        risultato.setErrorData(ResponseResult.ERROR_INVALID_CF);
                        return risultato;
                    }
                }
                String codTec = calcolaCodificaAutomatica("TECNI", "CODTEC");
                form.setCodice(codTec);
                this.tabellatiMapper.insertRUP(form);
                risultato.setData(codTec);
            }
        } catch (ExistingCFException ex) {
            logger.error("Errore durante creazione/modifica RUP=" + form.getStazioneAppaltante()
                    + ". Codice fiscale gia' utilizzato", ex);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_EXISTING_CF);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante creazione RUP=" + form.getStazioneAppaltante(), t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

    private CFPIVAInfoInsert getCFPIVAInfoInsert(ECFPIVAInfoInsert type) {

        CFPIVAInfoInsert risultato = new CFPIVAInfoInsert();
        try {
            List<TabellatoIstatEntry> righeG11 = this.tabellatiMapper.getTabellato1("G_011");
            List<TabellatoIstatEntry> righeG12 = this.tabellatiMapper.getTabellato1("G_012");

            boolean checkCF = false;
            boolean checkPIVA = false;

            TabellatoIstatEntry rigaG11 = null;
            if (type == ECFPIVAInfoInsert.IMPR)
                rigaG11 = righeG11 != null
                        ? righeG11.stream().filter(r -> "2".equals(r.getCodice())).findAny().orElse(null)
                        : null;
            else if (type == ECFPIVAInfoInsert.TECNI)
                rigaG11 = righeG11 != null
                        ? righeG11.stream().filter(r -> "3".equals(r.getCodice())).findAny().orElse(null)
                        : null;
            else if (type == ECFPIVAInfoInsert.UFFINT)
                rigaG11 = righeG11 != null
                        ? righeG11.stream().filter(r -> "5".equals(r.getCodice())).findAny().orElse(null)
                        : null;

            if (rigaG11 != null) {
                String valore = rigaG11.getDescrizione().substring(0, 1);
                if ("1".equals(valore)) {
                    // check CF
                    checkCF = true;
                    checkPIVA = false;
                } else if ("2".equals(valore)) {
                    // check PIVA
                    checkCF = false;
                    checkPIVA = true;
                } else if ("3".equals(valore)) {
                    // check CF and PIVA
                    checkCF = true;
                    checkPIVA = true;
                }
            }

            for (TabellatoIstatEntry rigaG12 : righeG12) {
                String valore = rigaG12.getDescrizione().substring(0, 1);
                if (rigaG12.getCodice().equals("1") && type != ECFPIVAInfoInsert.UFFINT) {
                    if (checkCF)
                        risultato.setCheckCfValid(valore.equals("1"));
                    if (checkPIVA)
                        risultato.setCheckPIVAValid(valore.equals("1"));
                }
                if (rigaG12.getCodice().equals("2")) {
                    if (checkCF)
                        risultato.setBlockDuplicateCf(valore.equals("1"));
                    if (checkPIVA)
                        risultato.setBlockDuplicatePIVA(valore.equals("1"));
                }
            }
        } catch (Exception ex) {
            logger.error("Errore nel recupero del tabellato G_011 o G_012", ex.getMessage());
            risultato.setBlockDuplicateCf(true);
            risultato.setCheckCfValid(true);
        }
        return risultato;
    }

    public String calcolaCodificaAutomatica(String entita, String campoChiave) throws Exception {
        String codice = "1";
        String formatoCodice = null;
        String codcal = null;
        Long cont = null;
        try {
            String query = "select CODCAL, CONTAT from G_CONFCOD where NOMENT = '" + entita + "'";
            List<Map<String, Object>> confcod = sqlMapper.select(query);
            if (confcod != null && confcod.size() > 0) {
                for (Map<String, Object> row : confcod) {
                    if (row.containsKey("CODCAL")) {
                        codcal = row.get("CODCAL").toString();
                    } else {
                        codcal = row.get("codcal").toString();
                    }
                    if (row.containsKey("CONTAT")) {
                        cont = Long.valueOf(row.get("CONTAT").toString());
                    } else {
                        cont = Long.valueOf(row.get("contat").toString());
                    }
                    break;
                }
                boolean codiceUnivoco = false;
                int numeroTentativi = 0;
                StringBuffer strBuffer = null;
                long tmpContatore = cont.longValue();
                while (!codiceUnivoco && numeroTentativi < 5) {
                    strBuffer = new StringBuffer();
                    // Come prima cosa eseguo l'update del contatore
                    tmpContatore++;
                    sqlMapper.execute(
                            "update G_CONFCOD set contat = " + tmpContatore + " where NOMENT = '" + entita + "'");

                    strBuffer = new StringBuffer();
                    formatoCodice = codcal;
                    while (formatoCodice.length() > 0) {
                        switch (formatoCodice.charAt(0)) {
                            case '<': // Si tratta di un'espressione numerica
                                String strNum = formatoCodice.substring(1, formatoCodice.indexOf('>'));
                                if (strNum.charAt(0) == '0') {
                                    // Giustificato a destra
                                    for (int i = 0; i < (strNum.length() - String.valueOf(tmpContatore).length()); i++)
                                        strBuffer.append('0');
                                }
                                strBuffer.append(tmpContatore);

                                formatoCodice = formatoCodice.substring(formatoCodice.indexOf('>') + 1);
                                break;
                            case '"': // Si tratta di una parte costante
                                strBuffer.append(formatoCodice, 1, formatoCodice.indexOf('"', 1));
                                formatoCodice = formatoCodice.substring(formatoCodice.indexOf('"', 1) + 1);
                                break;
                        }
                    }
                    int occorrenze = sqlMapper
                            .count(entita + " WHERE " + campoChiave + " ='" + strBuffer + "'");
                    if (occorrenze == 0) {
                        codiceUnivoco = true;
                        codice = strBuffer.toString();
                    } else {
                        numeroTentativi++;
                    }
                }
                if (!codiceUnivoco) {
                    logger.error("numeroTentativi esaurito durante il calcolo per la codifica automatica " + entita);
                    throw new Exception(
                            "numeroTentativi esaurito durante il calcolo per la codifica automatica " + entita);
                }
            }
        } catch (Exception ex) {
            logger.error("Errore inaspettato durante il calcolo per la codifica automatica " + entita, ex);
            throw new Exception(ex);
        }
        return codice;
    }


    public ResponseListaUsers getUtenteOptions(RupSearchForm form) {
        ResponseListaUsers risultato = new ResponseListaUsers();
        risultato.setEsito(true);
        try {
            List<UsrSysconEntry> entries = this.tabellatiMapper.getUtenteOptionsWithSa(form.getRup().toUpperCase(), form.getStazioneAppaltante());
            risultato.setData(entries);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante ricerca avvisi utenti: SA =" + form.getStazioneAppaltante(), t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

    public ResponseListaRup getRupOptions(RupSearchForm form) {
        ResponseListaRup risultato = new ResponseListaRup();
        risultato.setEsito(true);
        try {
            String filtroSA = this.wConfigManager.getConfigurationValue(
                    Constants.APPLICATION_SA_FILTER);
            List<RupEntry> entry = new ArrayList<RupEntry>();
            form.setRup(form.getRup().toLowerCase());
            if (filtroSA.contains("TECNI") && !"*".equals(form.getStazioneAppaltante())) {
                entry = this.tabellatiMapper.getRupOptionsWithSa(form);
            } else {
                entry = this.tabellatiMapper.getRupOptionsWithoutSa(form);
            }
            risultato.setData(entry);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante ricerca avvisi avviso: SA =" + form.getStazioneAppaltante(), t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

    public ResponseListaCig getCigOptions(String cigLike, String stazioneAppaltante, String authorization, String xLoginMode) {
        ResponseListaCig risultato = new ResponseListaCig();
        risultato.setEsito(true);
        try {

            UserAuthClaimsDTO userAuthClaimsDTO = this.userManager.parseUserJwtClaimsAndFindSyscon(authorization, xLoginMode);
            String cf = userAuthClaimsDTO.getCf();
            Long syscon = userAuthClaimsDTO.getSyscon();
            boolean cfNull = false;

            // Controllo il ruolo dell'utente. Se non è amministratore le gare vanno
            // filtrate per il codice
            // RUP associato al suo record della tabella tecni
            String ruolo = this.tabellatiMapper.getRuolo(syscon);
            if("*".equals(stazioneAppaltante)) {
                stazioneAppaltante = null;
            }

            String cfTecnico = StringUtils.isNotBlank(cf) ? cf.toUpperCase() : null;
            if ("A".equals(ruolo) || "C".equals(ruolo)) {
                cfTecnico = null;
            } else{
                if(StringUtils.isBlank(cfTecnico)) {
                    cfNull = true;
                }
            }

            List<String> entry = new ArrayList<String>();

            entry = this.tabellatiMapper.getCigOptions(cigLike.toUpperCase(), stazioneAppaltante, cfTecnico, cfNull, syscon);
            risultato.setData(entry);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante ricerca CIG. Avviso: SA = {}", stazioneAppaltante, t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

    public ResponseListaRup getSuggestedRup(SearchMainSARupForm form) {
        ResponseListaRup risultato = new ResponseListaRup();
        risultato.setEsito(true);
        try {
            List<RupEntry> entry = this.tabellatiMapper.getSuggestedRup(form);
            risultato.setData(entry);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante ricerca avvisi avviso: SA =" + form.getStazioneAppaltante(), t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

    public ResponseInfoCampo getInfoCampo(String mnemonico) {
        ResponseInfoCampo risultato = new ResponseInfoCampo();
        risultato.setEsito(true);
        try {
            InfoC0ampi campoC0Info = this.tabellatiMapper.getInfoC0Campi(mnemonico);
            if (campoC0Info != null && campoC0Info.getCoc_mne_uni() != null) {
                String[] schemaEntitaInfo = campoC0Info.getCoc_mne_uni().split("\\.");
                String schema = schemaEntitaInfo[2];
                String entita = schemaEntitaInfo[1];
                String descSchema = this.tabellatiMapper.getDescrizioneSchema(schema);
                String descEntita = this.tabellatiMapper.getDescrizioneEntita(entita + "." + schema);
                List<String> chiaveEntita = this.tabellatiMapper.getChiaveEntita("%." + entita + "." + schema);

                InfoCampo campoInfo = new InfoCampo();
                campoInfo.setCampo(campoC0Info.getCoc_mne_uni());
                campoInfo.setDescrizione(campoC0Info.getCoc_des());
                campoInfo.setFormato(calcolaFormato(campoC0Info.getC0c_fs()));
                campoInfo.setMnemonico("#" + mnemonico + "#");
                campoInfo.setSchema(schema);
                campoInfo.setEntita(entita);
                campoInfo.setDescrizioneSchema(descSchema);
                campoInfo.setDescrizioneEntita(descEntita);
                campoInfo.setChiaveEntita(chiaveEntita);

                if (campoC0Info.getC0c_tab1() != null) {
                    int numTab = getNumeroTabellaByCodice(campoC0Info.getC0c_tab1());
                    List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
                    List<String> valoriTabellati = new ArrayList<String>();
                    switch (numTab) {
                        case 1:
                            righe = this.tabellatiMapper.getTabellato1(campoC0Info.getC0c_tab1());
                            break;
                        case 2:
                            righe = this.tabellatiMapper.getTabellato2(campoC0Info.getC0c_tab1());
                            break;
                        case 3:
                            righe = this.tabellatiMapper.getTabellato3(campoC0Info.getC0c_tab1());
                            break;
                        case 5:
                            righe = this.tabellatiMapper.getTabellato5(campoC0Info.getC0c_tab1());
                            break;
                        default:
                            break;
                    }

                    for (TabellatoIstatEntry t : righe) {
                        valoriTabellati.add(t.getDescrizione());
                    }
                    campoInfo.setValoriTabellati(valoriTabellati);
                }
                risultato.setData(campoInfo);
            } else {
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_NOT_FOUND);
            }
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante ricerca descrizione del campo con mnemonico =" + mnemonico, t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

    private String calcolaFormato(String formatoCode) {
        return formatoCode;
    }

    private int getNumeroTabellaByCodice(String codiceTabellato) {
        /*
         * Attraverso il terzo carattere del codice del tabellato è possibile
         * identificare in che tabella si trova quindi faccio il case sul terzo
         * carattere del codice tabellato x e y Sono in tab3 w e k Sono in tab0 v e z
         * Sono in tab2 j Sono in tab 5 altrimenti si trova in tab 1
         */
        String lsChar = codiceTabellato.length() > 2 ? codiceTabellato.substring(2, 3) : "0";

        if ("xy".indexOf(lsChar) >= 0) {
            // Tabella 3
            return 3;
        }
        if ("wk".indexOf(lsChar) >= 0) {
            // Tabella 0
            return 0;
        }
        if ("vz".indexOf(lsChar) >= 0) {
            // Tabella 2
            return 2;
        }
        if (lsChar.equals("j")) {
            // Tabella 5
            return 5;
        }
        return 1;
    }

    public ResponseResult health() {
        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);
        try {
            this.tabellatiMapper.health();

        } catch (Throwable t) {
            logger.error("Health: Errore inaspettato.", t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

    public ResponseListaUffici getUffOptions(UffSearchForm form, boolean programmazione) {
        ResponseListaUffici risultato = new ResponseListaUffici();
        risultato.setEsito(true);
        try {
            form.setDesc(form.getDesc().toUpperCase());
            List<UffEntry> entry = null;
            if (programmazione) {
                entry = this.tabellatiMapper.getUffOptionsProg(form);
            } else {
                entry = this.tabellatiMapper.getUffOptions(form);
            }
            risultato.setData(entry);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante ricerca uffici: SA =" + form.getStazioneAppaltante(), t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

    public ResponseListaComuni getComuniOptions(String search) {
        ResponseListaComuni risultato = new ResponseListaComuni();
        risultato.setEsito(true);
        try {
            List<TabellatoIstatEntry> entry = this.tabellatiMapper.getComuniOptions(search.toUpperCase());
            risultato.setData(entry);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante ricerca comuni =" + search, t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

    public List<TabellatoIstatEntry> getRegioni() {

        List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
        try {
            righe = this.tabellatiMapper.getRegioni();
        } catch (Throwable t) {
            // qualsiasi sia l'errore si traccia nel log e si ritorna un codice
            // fisso ed il messaggio allegato all'eccezione come errore
            logger.error("Errore inaspettato durante l'estrazione delle regioni ", t);
        }
        return righe;
    }

    public List<TabellatoIstatEntry> getProvince() {
        List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
        try {
            righe = this.tabellatiMapper.getProvince();
        } catch (Throwable t) {
            // qualsiasi sia l'errore si traccia nel log e si ritorna un codice
            // fisso ed il messaggio allegato all'eccezione come errore
            logger.error("Errore inaspettato durante l'estrazione delle province Istat", t);
        }

        return righe;
    }

    public List<TabellatoIstatEntry> getComuni() {
        List<TabellatoIstatEntry> righe = new ArrayList<TabellatoIstatEntry>();
        try {
            righe = this.tabellatiMapper.getComuni();
        } catch (Throwable t) {
            // qualsiasi sia l'errore si traccia nel log e si ritorna un codice
            // fisso ed il messaggio allegato all'eccezione come errore
            logger.error("Errore inaspettato durante l'estrazione dei comuni Istat", t);
        }

        return righe;
    }

    public ResponseResult insertUff(UffInsertForm form) {
        ResponseResult risultato = new ResponseResult();
        risultato.setEsito(true);
        try {
            if (form.getId() != null) {
                this.tabellatiMapper.updateUfficio(form);
                risultato.setData(form.getId() + "");
            } else {
                Long tableId = this.tabellatiMapper.getMaxIdUff();
                Long id = tableId != null ? tableId + 1L : 1L;
                form.setId(id);
                this.tabellatiMapper.insertUfficio(form);
                risultato.setData(id + "");
            }
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante creazione Ufficio=" + form.getStazioneAppaltante(), t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

    public ResponseListaUfficiPaginata getListaUffici(UfficiSearchForm searchForm) {

        ResponseListaUfficiPaginata risultato = new ResponseListaUfficiPaginata();
        try {
            if (searchForm.getSearchString() != null) {
                searchForm.setSearchStringLike("%" + searchForm.getSearchString().toUpperCase() + "%");
            }
            int totalCount = this.tabellatiMapper.countSearchUffici(searchForm);
            RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
            risultato.setTotalCount(totalCount);
            List<UffEntry> uffici = this.tabellatiMapper.getUffici(searchForm, rowBounds);

            List<UffEntry> ufficiResults = new ArrayList<>();
            List<EntitaCollegate> entita = this.tabellatiMapper.getEntitaCollegate(ENTITA_COLLEGATE_UFFICI);
            for (UffEntry ufficio : uffici) {
                ufficio.setDeletable(checkDelete(String.valueOf(ufficio.getId()), entita));
                ufficiResults.add(ufficio);
            }
            risultato.setData(uffici);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante il recupero della lista degli uffici. SA:"
                    + searchForm.getStazioneAppaltante(), t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseUfficio getUfficio(Long id) {
        ResponseUfficio risultato = new ResponseUfficio();
        try {

            UffEntry ufficio = this.tabellatiMapper.getUfficio(id);
            risultato.setData(ufficio);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la get dell'ufficio . ID:" + id, t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseResult deleteUfficio(final Long id) {
        logger.debug("Execution start TabellatiManager::deleteUfficio for id [ {} ]", id);
        ResponseResult risultato = new ResponseResult();
        try {
            List<EntitaCollegate> entita = this.tabellatiMapper.getEntitaCollegate(ENTITA_COLLEGATE_UFFICI);
            if (checkDelete(id, entita)) {
                this.tabellatiMapper.deleteUfficio(id);
                risultato.setEsito(true);
            } else {
                risultato.setErrorData(ResponseResult.ERROR_ENTITY_HAS_CONNECTIONS);
                risultato.setEsito(false);
            }
        } catch (Throwable t) {
            logger.error("Error during deleteUfficio for id [ {} ]", id, t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseTabNuts getNutsData() {
        ResponseTabNuts risultato = new ResponseTabNuts();
        risultato.setEsito(true);
        try {
            List<TabNuts> paesi = this.tabellatiMapper.getPaesiNuts();
            List<TabNuts> aree = this.tabellatiMapper.getAreeNuts();
            List<TabNuts> regioni = this.tabellatiMapper.getRegioniNuts();
            List<TabNuts> province = this.tabellatiMapper.getProvinceNuts();
            Map<String, List<TabNuts>> nutsMap = new HashMap<String, List<TabNuts>>();
            nutsMap.put("paesi", paesi);
            nutsMap.put("aree", aree);
            nutsMap.put("regioni", regioni);
            nutsMap.put("province", province);
            risultato.setData(nutsMap);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante query per tab nuts", t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

    public ResponseTabellatoCpv getAllCpv() {
        ResponseTabellatoCpv risultato = new ResponseTabellatoCpv();
        try {
            List<TabellatoCPVEntry> lista = this.tabellatiMapper.getAllCpv();
            risultato.setData(lista);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante l'estrazione della lista di livello 0 per il codice CPV", t);
            risultato = new ResponseTabellatoCpv();
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

    public ResponseListaRupPaginata getListaTecnici(TecniciSearchForm searchForm) {

        ResponseListaRupPaginata risultato = new ResponseListaRupPaginata();
        try {
            String filtroSA = this.wConfigManager.getConfigurationValue(
                    Constants.APPLICATION_SA_FILTER);
            if (!filtroSA.contains("TECNI") || "*".equals(searchForm.getStazioneAppaltante())) {
                searchForm.setStazioneAppaltante(null);
            }
            if (searchForm.getSearchString() != null) {
                searchForm.setSearchStringLike("%" + searchForm.getSearchString().toUpperCase() + "%");
            }
            if (searchForm.getCodiceFiscale() != null) {
                searchForm.setCodiceFiscaleLike("%" + searchForm.getCodiceFiscale().toUpperCase() + "%");
            }

            int totalCount = this.tabellatiMapper.countSearchTecnici(searchForm);
            RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
            risultato.setTotalCount(totalCount);
            List<RupEntry> tecnici = this.tabellatiMapper.getTecnici(searchForm, rowBounds);

            List<RupEntry> tecniciResults = new ArrayList<RupEntry>();
            List<EntitaCollegate> entita = this.tabellatiMapper.getEntitaCollegate(ENTITA_COLLEGATE_TECNI);
            for (RupEntry tecnico : tecnici) {
                tecnico.setNominativoStazioneAppaltante(this.tabellatiMapper.getNominativoSa(tecnico.getStazioneAppaltante()));
                tecnico.setDeletable(checkDelete(tecnico.getCodice(), entita));
                tecniciResults.add(tecnico);
            }
            risultato.setData(tecniciResults);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la get dei tecnici. SA:" + searchForm.getStazioneAppaltante(), t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseRup getTecnico(String id) {
        ResponseRup risultato = new ResponseRup();
        try {

            RupEntry tecnico = this.tabellatiMapper.getTecnico(id);
            risultato.setData(tecnico);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la get del tecnico. ID:" + id, t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseResult deleteTecnico(String id) {
        ResponseResult risultato = new ResponseResult();
        try {
            List<EntitaCollegate> entita = this.tabellatiMapper.getEntitaCollegate(ENTITA_COLLEGATE_TECNI);
            if (checkDelete(id, entita)) {
                this.tabellatiMapper.deleteTecnico(id);
                risultato.setEsito(true);
            } else {
                risultato.setErrorData(ResponseResult.ERROR_ENTITY_HAS_CONNECTIONS);
                risultato.setEsito(false);
            }
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la get del tecnico. ID:" + id, t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public boolean checkDelete(Object codice, List<EntitaCollegate> entita) {

        for (EntitaCollegate e : entita) {

            if (e != null && e.getTabella() != null && e.getColonna() != null) {
                String tabella = e.getTabella().split("\\.")[0];
                String colonna = e.getColonna().split("\\.")[0];

                try {

                    Long occurrencies = 0L;

                    if (codice instanceof String) {
                        occurrencies = this.tabellatiMapper.checkRelazioneString(tabella, colonna, (String) codice);
                    } else if (codice instanceof Long) {
                        occurrencies = this.tabellatiMapper.checkRelazioneLong(tabella, colonna, (Long) codice);
                    }

                    if (occurrencies > 0) {
                        return false;
                    }
                } catch (Exception ex) {
                    logger.error("Eccezione per probabile assenza di tabella, tutto a posto", ex);
                }
            }
        }
        return true;
    }

    public ResponseListaImprese getListaImprese(ImpreseSearchForm searchForm) {

        ResponseListaImprese risultato = new ResponseListaImprese();
        try {

            String filtroSA = this.wConfigManager.getConfigurationValue(
                    Constants.APPLICATION_SA_FILTER);
            if (!filtroSA.contains("IMPR") || "*".equals(searchForm.getStazioneAppaltante())) {
                searchForm.setStazioneAppaltante(null);
            }

            if (searchForm.getCodiceFiscale() != null) {
                searchForm.setCodiceFiscale("%" + searchForm.getCodiceFiscale().toUpperCase() + "%");
            }
            if (searchForm.getRagioneSociale() != null) {
                searchForm.setRagioneSociale("%" + searchForm.getRagioneSociale().toUpperCase() + "%");
            }
            if (searchForm.getPartitaIva() != null) {
                searchForm.setPartitaIva("%" + searchForm.getPartitaIva().toUpperCase() + "%");
            }
            if (searchForm.getEmail() != null) {
                searchForm.setEmail("%" + searchForm.getEmail().toUpperCase() + "%");
            }
            if (searchForm.getPec() != null) {
                searchForm.setPec("%" + searchForm.getPec().toUpperCase() + "%");
            }
            if (searchForm.getLegale() != null) {
                searchForm.setLegale("%" + searchForm.getLegale().toUpperCase() + "%");
            }

            int totalCount = this.tabellatiMapper.countSearchImprese(searchForm);
            RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
            risultato.setTotalCount(totalCount);
            List<ImpresaBaseEntry> imprese = this.tabellatiMapper.getImprese(searchForm, rowBounds);
            List<ImpresaBaseEntry> impreseResult = new ArrayList<>();
            List<EntitaCollegate> entita = this.tabellatiMapper.getEntitaCollegate(ENTITA_COLLEGATE_IMPR);
            for (ImpresaBaseEntry impr : imprese) {
                impr.setNominativoStazioneAppaltante(this.tabellatiMapper.getNominativoSa(impr.getStazioneAppaltante()));
                impr.setDeletable(checkDelete(impr.getCodiceImpresa(), entita));
                impreseResult.add(impr);
            }

            risultato.setData(impreseResult);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la get delle imprese. SA:" + searchForm.getStazioneAppaltante(),
                    t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseImpresa getImpresa(String codiceImpresa) {
        ResponseImpresa risultato = new ResponseImpresa();
        try {

            ImpresaEntry impresa = this.tabellatiMapper.getImpresa(codiceImpresa);
            LegaleRappresentanteEntry legale = this.tabellatiMapper.getLegaleImpresa(codiceImpresa);
            impresa.setRappresentante(legale);
            if (impresa.getComune() != null && !"".equals(impresa.getComune())) {
                TabellatoIstatEntry comune = this.tabellatiMapper.getComuneByDesc(impresa.getComune().toUpperCase());
                if (comune != null) {
                    impresa.setCodComune(comune.getCodice());
                }
            }
            risultato.setData(impresa);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la get dell'impresa. ID:" + codiceImpresa, t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public List<EntitaCollegate> getEntitaCollegateImpr() {
        try {
            return this.tabellatiMapper.getEntitaCollegate(ENTITA_COLLEGATE_IMPR);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante il metodo getEntitaCollegateImpr", e);
            throw e;
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult deleteImpresa(String codiceImpresa) {
        ResponseResult risultato = new ResponseResult();
        try {
            this.tabellatiMapper.deleteImpresa(codiceImpresa);
            this.tabellatiMapper.deleteTeim(codiceImpresa);
            this.tabellatiMapper.deleteImpleg(codiceImpresa);
            risultato.setEsito(true);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la cancellazione dell'impresa. codice:" + codiceImpresa, e);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult insertImpresa(ImpresaInsertForm form) throws Exception {
        ResponseResult risultato = new ResponseResult();
        String nome = null;
        String cognome = null;
        String cf = null;
        String nominativo = null;
        try {

            CFPIVAInfoInsert cfPIVAInfoInsert = getCFPIVAInfoInsert(ECFPIVAInfoInsert.IMPR);
            String filtroSA = this.wConfigManager.getConfigurationValue(
                    Constants.APPLICATION_SA_FILTER);

            String codImp = calcolaCodificaAutomatica("IMPR", "CODIMP");
            form.setCodiceImpresa(codImp);
            if (form.getNazione() == null) {
                form.setNazione(1L);
            }
            if (form.getRappresentante() != null) {
                nome = form.getRappresentante().getNome();
                cognome = form.getRappresentante().getCognome();
                cf = form.getRappresentante().getCf();

                if (cfPIVAInfoInsert.isCheckCfValid() && StringUtils.isNotBlank(cf) && !UtilityFiscali.isValidCodiceFiscale(cf, true)
                        && !UtilityFiscali.isValidPartitaIVA(cf)) {
                    risultato.setEsito(false);
                    risultato.setErrorData(ResponseResult.ERROR_INVALID_CF_LEGALE);
                    return risultato;
                }
                if (nome != null) {
                    if (nominativo == null) {
                        nominativo = "";
                    }
                    nominativo += nome;
                }

                if (nominativo != null) {
                    nominativo += " ";
                }

                if (cognome != null) {
                    if (nominativo == null) {
                        nominativo = "";
                    }
                    nominativo += cognome;
                }
            }
            if (cfPIVAInfoInsert.isBlockDuplicateCf()) {
                Long occurencies;
                if (filtroSA.contains("IMPR")) {
                    occurencies = this.tabellatiMapper.countImprCfOccurenciesWithSa(form.getCodiceFiscale(),
                            form.getStazioneAppaltante());
                } else {
                    occurencies = this.tabellatiMapper.countImprCfOccurenciesWithoutSa(form.getCodiceFiscale());
                }
                if (occurencies > 0) {
                    risultato.setEsito(false);
                    risultato.setErrorData(ResponseResult.ERROR_EXISTING_CF);
                    return risultato;
                }
            }
            if (cfPIVAInfoInsert.isCheckCfValid() && (form.getNazione() == null || form.getNazione() == 1L)) {
                if (form.getCodiceFiscale() != null && !UtilityFiscali.isValidCodiceFiscale(form.getCodiceFiscale(), true)
                        && !UtilityFiscali.isValidPartitaIVA(form.getCodiceFiscale())) {//
                    risultato.setEsito(false);
                    risultato.setErrorData(ResponseResult.ERROR_INVALID_CF);
                    return risultato;
                }
            }

            if (cfPIVAInfoInsert.isCheckPIVAValid() && form.getPartitaIva() != null
                    && (form.getNazione() == null || form.getNazione() == 1L)
                    && !UtilityFiscali.isValidPartitaIVA(form.getPartitaIva())) {//
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_INVALID_PARTITA_IVA);
                return risultato;
            }

            if (cfPIVAInfoInsert.isBlockDuplicatePIVA()) {
                Long occurencies;
                if (filtroSA.contains("IMPR")) {
                    occurencies = this.tabellatiMapper.countImprPIVAOccurenciesWithSa(form.getPartitaIva(),
                            form.getStazioneAppaltante());
                } else {
                    occurencies = this.tabellatiMapper.countImprPIVAOccurenciesWithoutSa(form.getPartitaIva());
                }
                if (occurencies > 0) {
                    risultato.setEsito(false);
                    risultato.setErrorData(ResponseResult.ERROR_EXISTING_PARTITA_IVA);
                    return risultato;
                }
            }

            form = this.setImpresaNomImp(form);
            this.tabellatiMapper.insertTeim(codImp, nome, cognome, cf);
            Long id = wgcManager.getNextId("IMPLEG");
            this.tabellatiMapper.insertImpleg(id, form.getCodiceImpresa(), nominativo);
            this.tabellatiMapper.insertImpresa(form);
            risultato.setData(codImp);
            risultato.setEsito(true);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la creazione dell'impresa:", e);
            throw (e);
        }
        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult updateImpresa(ImpresaInsertForm form) throws Exception {
        ResponseResult risultato = new ResponseResult();
        String nome = null;
        String cognome = null;
        String cf = null;
        String nominativo = null;
        try {

            CFPIVAInfoInsert cfPIVAInfoInsert = getCFPIVAInfoInsert(ECFPIVAInfoInsert.IMPR);

            if (form.getRappresentante() != null) {
                nome = form.getRappresentante().getNome();
                cognome = form.getRappresentante().getCognome();
                cf = form.getRappresentante().getCf();

                if (cfPIVAInfoInsert.isCheckCfValid() && StringUtils.isNotBlank(cf) && !UtilityFiscali.isValidCodiceFiscale(cf, true)
                        && !UtilityFiscali.isValidPartitaIVA(cf)) {
                    risultato.setEsito(false);
                    risultato.setErrorData(ResponseResult.ERROR_INVALID_CF_LEGALE);
                    return risultato;
                }

                if (nome != null) {
                    if (nominativo == null) {
                        nominativo = "";
                    }
                    nominativo += nome;
                }

                if (nominativo != null) {
                    nominativo += " ";
                }

                if (cognome != null) {
                    if (nominativo == null) {
                        nominativo = "";
                    }
                    nominativo += cognome;
                }
            }

            String filtroSA = this.wConfigManager.getConfigurationValue(
                    Constants.APPLICATION_SA_FILTER);

            if (cfPIVAInfoInsert.isBlockDuplicateCf()) {
                Long occurencies;
                if (filtroSA.contains("IMPR")) {
                    occurencies = this.tabellatiMapper.countImprCfOccurenciesEditWithSa(form.getCodiceFiscale(),
                            form.getStazioneAppaltante(), form.getCodiceImpresa());
                } else {
                    occurencies = this.tabellatiMapper.countImprCfOccurenciesEditWithoutSa(form.getCodiceFiscale(),
                            form.getCodiceImpresa());
                }
                if (occurencies > 0) {
                    risultato.setEsito(false);
                    risultato.setErrorData(ResponseResult.ERROR_EXISTING_CF);
                    return risultato;
                }
            }
            if (cfPIVAInfoInsert.isCheckCfValid() && (form.getNazione() == null || form.getNazione() == 1L)) {
                if (!UtilityFiscali.isValidCodiceFiscale(form.getCodiceFiscale(), true)
                        && !UtilityFiscali.isValidPartitaIVA(form.getCodiceFiscale())) {
                    risultato.setEsito(false);
                    risultato.setErrorData(ResponseResult.ERROR_INVALID_CF);
                    return risultato;
                }
            }

            if (cfPIVAInfoInsert.isCheckPIVAValid() && form.getPartitaIva() != null
                    && (form.getNazione() == null || form.getNazione() == 1L)
                    && !UtilityFiscali.isValidPartitaIVA(form.getPartitaIva())) {//
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_INVALID_PARTITA_IVA);
                return risultato;
            }

            if (cfPIVAInfoInsert.isBlockDuplicatePIVA()) {
                Long occurencies;
                if (filtroSA.contains("IMPR")) {
                    occurencies = this.tabellatiMapper.countImprPIVAOccurenciesEditWithSa(form.getPartitaIva(),
                            form.getStazioneAppaltante(), form.getCodiceImpresa());
                } else {
                    occurencies = this.tabellatiMapper.countImprPIVAOccurenciesEditWithoutSa(form.getPartitaIva(),
                            form.getCodiceImpresa());
                }
                if (occurencies > 0) {
                    risultato.setEsito(false);
                    risultato.setErrorData(ResponseResult.ERROR_EXISTING_PARTITA_IVA);
                    return risultato;
                }
            }

            form = this.setImpresaNomImp(form);
            this.tabellatiMapper.updateTeim(form.getCodiceImpresa(), nome, cognome, cf);
            this.tabellatiMapper.updateImpleg(form.getCodiceImpresa(), nominativo);
            this.tabellatiMapper.updateImpresa(form);
            risultato.setEsito(true);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la creazione dell'impresa:", e);
            throw (e);
        }
        return risultato;
    }

    public ResponseListaCentriDiCosto getListaCentriDiCosto(CentriDiCostoSearchform searchForm) {

        ResponseListaCentriDiCosto risultato = new ResponseListaCentriDiCosto();
        try {
            if ("*".equals(searchForm.getStazioneAppaltante())) {
                searchForm.setStazioneAppaltante(null);
            }
            if (searchForm.getCodiceCentro() != null) {
                searchForm.setCodiceCentro("%" + searchForm.getCodiceCentro().toUpperCase() + "%");
            }
            int totalCount = this.tabellatiMapper.countSearchCentriDiCosto(searchForm);
            RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
            risultato.setTotalCount(totalCount);
            List<CentroDiCostoEntry> centriDiCosto = this.tabellatiMapper.getCentriDiCosto(searchForm, rowBounds);
            for (CentroDiCostoEntry centroDiCostoEntry : centriDiCosto) {
                centroDiCostoEntry.setNominativoStazioneAppaltante(this.tabellatiMapper.getNominativoSa(centroDiCostoEntry.getStazioneAppaltante()));
                Integer count = this.tabellatiMapper.getCountCdc(centroDiCostoEntry.getId());
                centroDiCostoEntry.setDeletable(count == 0);
            }
            risultato.setData(centriDiCosto);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la get dei centri di costo.", t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseCentroDiCosto getCentroDiCosto(Long idCdc) {

        ResponseCentroDiCosto risultato = new ResponseCentroDiCosto();
        try {
            CentroDiCostoEntry centroDiCosto = this.tabellatiMapper.getCentroDiCosto(idCdc);
            risultato.setData(centroDiCosto);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la get del centri di costo: " + idCdc, t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseResult deleteCentroDiCosto(Long idCdc) {

        ResponseResult risultato = new ResponseResult();
        try {
            this.tabellatiMapper.deleteCentroDiCosto(idCdc);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la cancellazione del centro di costo: " + idCdc, t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseResult insertCdc(CentroDiCostoInsertForm form) {
        ResponseResult risultato = new ResponseResult();
        try {
            Long idCdc = getIdCdc();
            form.setId(idCdc);
            this.tabellatiMapper.insertCdc(form);
            risultato.setData(idCdc + "");
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la creazione del centro di costo:", t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseResult updateCdc(CentroDiCostoInsertForm form) {
        ResponseResult risultato = new ResponseResult();
        try {
            this.tabellatiMapper.updateCdc(form);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante l'update del centro di costo:" + form.getId(), t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseListaCentriDiCostoOptions getCdcOptions(CentriDiCostoOptionsSearchForm form) {
        ResponseListaCentriDiCostoOptions risultato = new ResponseListaCentriDiCostoOptions();
        try {
            form.setDesc(form.getDesc().toLowerCase());
            List<CentroDiCostoEntry> entry = this.tabellatiMapper.getCdcOptions(form);
            risultato.setData(entry);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error(
                    "Errore inaspettato durante il recupero dei centri di costo per descrizione :" + form.getDesc(), t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    public ResponseMessageReceiver getMessageReceiver(final Long loggedUserSyscon, String searchString) {
        ResponseMessageReceiver risultato = new ResponseMessageReceiver();
        try {

            if (loggedUserSyscon == null) {
                logger.error("L'utente {} non e' stato trovato", loggedUserSyscon);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_NOT_FOUND);
                return risultato;
            }

            if (!userManager.isMessaggisticaInternaEnabled()) {
                logger.error("Messaggistica interna disabilitata");
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_DISABLED);
                return risultato;
            }

            boolean systemAdministrator = isUserSystemAdministrator(loggedUserSyscon);

            if (!systemAdministrator) {
                logger.error("L'utente {} non e' amministratore di sistema", loggedUserSyscon);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_PERMISSION);
                return risultato;
            }

            searchString = "%" + searchString.toUpperCase() + "%";
            List<MessageReceiverEntry> entries = this.tabellatiMapper.getMessageReceiver(searchString);
            risultato.setData(entries);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la get della lista destinatari per i messaggi", t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    private Long getIdCdc() {
        Long maxId = this.tabellatiMapper.getMaxIdCdc();
        if (maxId == null) {
            return 1L;
        } else {
            return maxId + 1;
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult sendMessage(final Long loggedUserSyscon, MessageForm form) throws Exception {
        logger.debug("Execution start {}::sendMessage", getClass().getSimpleName());
        ResponseResult risultato = new ResponseResult();
        try {

            if (loggedUserSyscon == null) {
                logger.error("L'utente {} non e' stato trovato", loggedUserSyscon);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_NOT_FOUND);
                return risultato;
            }

            if (!userManager.isMessaggisticaInternaEnabled()) {
                logger.error("Messaggistica interna disabilitata");
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_DISABLED);
                return risultato;
            }

            boolean systemAdministrator = isUserSystemAdministrator(loggedUserSyscon);

            if (!systemAdministrator) {
                logger.error("L'utente {} non e' amministratore di sistema", loggedUserSyscon);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_PERMISSION);
                return risultato;
            }

            if (!loggedUserSyscon.equals(form.getSysconSender())) {
                logger.error("L'utente {} sta tentando di inviare messaggi per conto di {}", loggedUserSyscon, form.getSysconSender());
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_PERMISSION);
                return risultato;
            }

            Long id = calculateMessageId("out");
            form.setId(id);
            this.tabellatiMapper.insertMessageOut(form);

            for (Long syscon : form.getSysconReceivers()) {
                MessageInForm inForm = new MessageInForm();
                Long idIn = calculateMessageId("in");
                inForm.setId(idIn);
                inForm.setCorpoMessaggio(form.getCorpoMessaggio());
                inForm.setDataMessaggio(form.getDataMessaggio());
                inForm.setOggetto(form.getOggetto());
                inForm.setSysconReceiver(syscon);
                inForm.setSysconSender(form.getSysconSender());
                this.tabellatiMapper.insertMessageIn(inForm);

                MessageRecForm recForm = new MessageRecForm();
                recForm.setId(id);
                recForm.setRecipientId(idIn);
                recForm.setSysconReceiver(syscon);
                this.tabellatiMapper.insertMessageRec(recForm);
            }
            risultato.setEsito(true);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante l'invio del messaggio", e);
            throw (e);
        }
        return risultato;
    }

    /**
     * Modifica metodo get Max ID per jira VIGILANZA2-150
     * Prendo il MAX tra la tabella interessata (inbox o outbox) e la tabella di riferimento destinatari
     * cosi' da poter cancellare senza problemi i messaggi da w_message_in o w_message_out senza incorrere in chiave duplicata
     */
    private Long calculateMessageId(String table) {
        Long maxId = null;
        if (table.equals("out")) {
            Long maxOutId = this.tabellatiMapper.getMaxMessageOutId();
            Long maxOutRecId = this.tabellatiMapper.getMaxMessageOutRecId();
            maxId = getMaxFromTwoLong(maxOutId, maxOutRecId);
        } else {
            Long maxInId = this.tabellatiMapper.getMaxMessageInId();
            Long maxOutRecId = this.tabellatiMapper.getMaxMessageOutRecRecipientId();
            maxId = getMaxFromTwoLong(maxInId, maxOutRecId);
        }
        if (maxId == null) {
            return 1L;
        } else {
            return maxId + 1;
        }
    }

    private Long getMaxFromTwoLong(Long a, Long b) {
        if (a != null && b != null)
            return Math.max(a, b);
        else if (a == null && b != null)
            return b;
        else if (a != null && b == null)
            return a;
        return null;
    }

    public ResponseMessages getMessages(final Long loggedUserSyscon, Long syscon, String origin) {
        ResponseMessages risultato = new ResponseMessages();
        try {

            if (loggedUserSyscon == null) {
                logger.error("L'utente {} non e' stato trovato", loggedUserSyscon);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_NOT_FOUND);
                return risultato;
            }

            if (!userManager.isMessaggisticaInternaEnabled()) {
                logger.error("Messaggistica interna disabilitata");
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_DISABLED);
                return risultato;
            }

            if (!loggedUserSyscon.equals(syscon)) {
                logger.error("L'utente {} sta tentando di accedere ai messaggi di {}", loggedUserSyscon, syscon);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_PERMISSION);
                return risultato;
            }

            List<MessageEntry> messages = new ArrayList<MessageEntry>();
            if (origin.equals("inbox")) {
                messages = this.tabellatiMapper.getMessages(syscon);
            } else {
                messages = this.tabellatiMapper.getMessagesSent(syscon);
            }
            risultato.setData(messages);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la lettura dei messaggi. syscon:" + syscon, t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    public ResponseResult setMessageSeen(final Long loggedUserSyscon, Long messageId, Long letto) {
        ResponseResult risultato = new ResponseResult();
        try {

            if (loggedUserSyscon == null) {
                logger.error("L'utente {} non e' stato trovato", loggedUserSyscon);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_NOT_FOUND);
                return risultato;
            }

            if (!userManager.isMessaggisticaInternaEnabled()) {
                logger.error("Messaggistica interna disabilitata");
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_DISABLED);
                return risultato;
            }

            Long sysconRecipient = tabellatiMapper.getInMessageRecipientSysconByMessageId(messageId);

            if (sysconRecipient == null) {
                logger.error("Il syscon del recipient del messaggio richiesto e' null");
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_PERMISSION);
                return risultato;
            }

            if (!loggedUserSyscon.equals(sysconRecipient)) {
                logger.error("L'utente {} sta tentando di modificare i messaggi di {}", loggedUserSyscon, sysconRecipient);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_PERMISSION);
                return risultato;
            }

            this.tabellatiMapper.setMessageSeen(messageId, letto);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante il set messaggio letto. messageId:" + messageId, t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
        }
        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult deleteMessage(final Long loggedUserSyscon, Long messageId, String origin) throws Exception {
        ResponseResult risultato = new ResponseResult();
        try {

            if (loggedUserSyscon == null) {
                logger.error("L'utente {} non e' stato trovato", loggedUserSyscon);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_NOT_FOUND);
                return risultato;
            }

            if (!userManager.isMessaggisticaInternaEnabled()) {
                logger.error("Messaggistica interna disabilitata");
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_DISABLED);
                return risultato;
            }

            if (origin.equals("inbox")) {

                Long sysconRecipient = tabellatiMapper.getInMessageRecipientSysconByMessageId(messageId);

                if (!loggedUserSyscon.equals(sysconRecipient)) {
                    logger.error("L'utente {} sta tentando di cancellare il messaggio {} in inbox di {}", loggedUserSyscon, messageId, sysconRecipient);
                    risultato.setEsito(false);
                    risultato.setErrorData(ResponseResult.ERROR_PERMISSION);
                    return risultato;
                }

                this.tabellatiMapper.deleteMessageIn(messageId);
            } else if (origin.equals("outbox")) {

                boolean systemAdministrator = isUserSystemAdministrator(loggedUserSyscon);

                if (!systemAdministrator) {
                    logger.error("L'utente {} non e' amministratore di sistema", loggedUserSyscon);
                    risultato.setEsito(false);
                    risultato.setErrorData(ResponseResult.ERROR_PERMISSION);
                    return risultato;
                }

                Long sysconSender = tabellatiMapper.getOutMessageSenderSysconByMessageId(messageId);

                if (!loggedUserSyscon.equals(sysconSender)) {
                    logger.error("L'utente {} sta tentando di cancellare il messaggio {} in outbox di {}", loggedUserSyscon, messageId, sysconSender);
                    risultato.setEsito(false);
                    risultato.setErrorData(ResponseResult.ERROR_PERMISSION);
                    return risultato;
                }

                this.tabellatiMapper.deleteMessageOut(messageId);
                this.tabellatiMapper.deleteAllMessageRec(messageId);
            }

            risultato.setEsito(true);
        } catch (Exception e) {
            logger.error(
                    "Errore inaspettato durante la cancellazione del messaggio. messageId:" + messageId + " " + origin,
                    e);
            throw (e);
        }
        return risultato;
    }

    public ResponseListaImpreseOptions getImpreseOptions(SingolaImpresaSearchForm form) {
        ResponseListaImpreseOptions risultato = new ResponseListaImpreseOptions();
        risultato.setEsito(true);
        try {

            String filtroSA = this.wConfigManager.getConfigurationValue(
                    Constants.APPLICATION_SA_FILTER);
            form.setDesc(form.getDesc().toLowerCase());
            List<ImpresaEntry> entry = new ArrayList<ImpresaEntry>();
            if (filtroSA.contains("IMPR")) {
                entry = this.tabellatiMapper.getImpreseOptionsWithSa(form);
            } else {
                entry = this.tabellatiMapper.getImpreseOptionsWithoutSa(form);
            }

            for (ImpresaEntry impresaEntry : entry) {
                LegaleRappresentanteEntry legRap = this.tabellatiMapper.getLegaleRappresentante(impresaEntry.getCodiceImpresa());
                if (legRap != null) {
                    impresaEntry.setRappresentante(legRap);
                }
            }


            risultato.setData(entry);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la ricerca delle imprese", t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

    public ResponseListaStazioniAppaltantiOptions getStazioniAppaltantiOptions(StazioneAppaltanteSearchForm form) {
        ResponseListaStazioniAppaltantiOptions risultato = new ResponseListaStazioniAppaltantiOptions();
        risultato.setEsito(true);
        try {
            form.setDesc(form.getDesc().toLowerCase());
            List<SAEntry> entry = this.tabellatiMapper.getStazioniAppaltantiOptions(form);
            risultato.setData(entry);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la ricerca delle imprese", t);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
        }
        return risultato;
    }

//	/**
//	 * Estrae la lista di livello 0 per il codice CPV
//	 *
//	 * @return lista di livello 0 per il codice CPV; nel caso di errore si setta il campo error con un identificativo di errore
//	 */
//	public ResponseTabellatoCpv getCod0() {
//		ResponseTabellatoCpv risultato = new ResponseTabellatoCpv();
//		try {
//			List<TabellatoCPVEntry> lista = this.tabellatiMapper.getCod0();
//			for (TabellatoCPVEntry cpv : lista) {
//				cpv.setLivello("1");
//				cpv.setText(cpv.getDescrizione());
//			}
//			
//			risultato.setData(lista);
//		} catch (Throwable t) {
//			logger.error("Errore inaspettato durante l'estrazione della lista di livello 0 per il codice CPV", t);
//			risultato = new ResponseTabellatoCpv();
//			risultato.setError(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
//		}
//		return risultato;
//	}
//
//	/**
//	 * Estrae la lista di livello 1 per il codice CPV
//	 *
//	 * @param cod0
//	 *        Codice livello 0 del CPV
//	 * @return lista di livello 1 per il codice CPV; nel caso di errore si setta il campo error con un identificativo di errore
//	 */
//	public ResponseTabellatoCpv getCod1(String cod0) {
//		ResponseTabellatoCpv risultato = new ResponseTabellatoCpv();
//		try {
//			List<TabellatoCPVEntry> lista = this.tabellatiMapper.getCod1(cod0);
//			for (TabellatoCPVEntry cpv : lista) {
//				cpv.setLivello("2");
//				cpv.setCod0(cod0);
//				cpv.setText(cpv.getDescrizione());
//			}
//			risultato.setData(lista);
//		} catch (Throwable t) {
//			// qualsiasi sia l'errore si traccia nel log e si ritorna un codice fisso ed il messaggio allegato all'eccezione come errore
//			logger.error("Errore inaspettato durante l'estrazione della lista di livello 1 per il codice 0 CPV " + cod0, t);
//			risultato = new ResponseTabellatoCpv();
//			risultato.setError(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
//		}
//
//		return risultato;
//	}
//	
//	/**
//	 * Estrae la lista di livello 2 per il codice CPV
//	 *
//	 * @param cod0
//	 *        Codice livello 0 del CPV
//	 * @param cod1
//	 *        Codice livello 1 del CPV
//	 * @return lista di livello 2 per il codice CPV; nel caso di errore si setta il campo error con un identificativo di errore
//	 */
//	public ResponseTabellatoCpv getCod2(String cod0, String cod1) {
//		ResponseTabellatoCpv risultato = new ResponseTabellatoCpv();
//		try {
//			List<TabellatoCPVEntry> lista = this.tabellatiMapper.getCod2(cod0, cod1);
//			for (TabellatoCPVEntry cpv : lista) {
//				cpv.setLivello("3");
//				cpv.setCod0(cod0);
//				cpv.setCod1(cod1);
//				cpv.setText(cpv.getDescrizione());
//			}
//			risultato.setData(lista);
//		} catch (Throwable t) {
//			logger.error("Errore inaspettato durante l'estrazione della lista di livello 2 per il codice 0 CPV " + cod0 + " e livello 1 " + cod1, t);
//			risultato = new ResponseTabellatoCpv();
//			risultato.setError(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
//		}
//		return risultato;
//	}
//	
//	/**
//	 * Estrae la lista di livello 2 per il codice CPV
//	 *
//	 * @param cod0
//	 *        Codice livello 0 del CPV
//	 * @param cod1
//	 *        Codice livello 1 del CPV
//	 * @param cod2
//	 *        Codice livello 2 del CPV
//	 * @return lista di livello 3 per il codice CPV; nel caso di errore si setta il campo error con un identificativo di errore
//	 */
//	public ResponseTabellatoCpv getCod3(String cod0, String cod1, String cod2) {
//		ResponseTabellatoCpv risultato = new ResponseTabellatoCpv();
//		try {
//			List<TabellatoCPVEntry> lista = this.tabellatiMapper.getCod3(cod0, cod1, cod2);
//			for (TabellatoCPVEntry cpv : lista) {
//				cpv.setLivello("4");
//				cpv.setCod0(cod0);
//				cpv.setCod1(cod1);
//				cpv.setCod2(cod2);
//				cpv.setText(cpv.getDescrizione());
//			}
//			risultato.setData(lista);
//		} catch (Throwable t) {
//			logger.error("Errore inaspettato durante l'estrazione della lista di livello 2 per il codice 0 CPV " + cod0 + " e livello 1 " + cod1 + " e livello 2 " + cod2, t);
//			risultato = new ResponseTabellatoCpv();
//			risultato.setError(ResponseResult.ERROR_UNEXPECTED + ": " + t.getMessage());
//		}
//
//		return risultato;
//	}

    public String getJWTKey() throws CriptazioneException {

        String criptedKey = this.wConfigManager.getConfigurationValue(CONFIG_CHIAVE_APP);
        try {
            ICriptazioneByte decript = FactoryCriptazioneByte.getInstance(
                    FactoryCriptazioneByte.CODICE_CRIPTAZIONE_LEGACY, criptedKey.getBytes(),
                    ICriptazioneByte.FORMATO_DATO_CIFRATO);

            return new String(decript.getDatoNonCifrato());
        } catch (CriptazioneException e) {
            logger.error("Errore in fase di decrypt della chiave hash per generazione token", e);
            throw e;
        }
    }

    public ResponseResult getApplicationTitle() {
        ResponseResult response = new ResponseResult();
        try {
            String titolo = this.wConfigManager.getConfigurationValue(
                    Constants.APPLICATION_TITLE);
            response.setData(titolo);
            response.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante il recupero del titolo dell'applicazione", t);
            response.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            response.setEsito(false);
        }
        return response;
    }

    public ResponseListaUsers getUserOptions(String search, String stazioneAppaltante) {
        ResponseListaUsers response = new ResponseListaUsers();
        try {
            List<UsrSysconEntry> utenti = this.tabellatiMapper.getUserOptions("%" + search.toUpperCase() + "%",
                    stazioneAppaltante);
            response.setData(utenti);
            response.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante il recupero delle opzioni utente", t);
            response.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            response.setEsito(false);
        }
        return response;
    }

    public boolean hasPermissionOnUfficio(Long syscon, Long idUfficio) {
        return this.tabellatiMapper.hasPermissionOnUfficio(syscon, idUfficio);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult insertSa(StazioneAppaltanteInsertForm form) {
        ResponseResult risultato = new ResponseResult();
        try {

            CFPIVAInfoInsert cfPIVAInfoInsert = getCFPIVAInfoInsert(ECFPIVAInfoInsert.UFFINT);
            //StazioneAppaltanteBaseEntry saAttuale = this.tabellatiMapper.getSaByCodiceFiscale(form.getCodFisc());

            if (form.getCfAnac() == null && form.getCodFisc() == null) {
                logger.error("Errore in insertSa: codice fiscale obbligatorio");
                risultato.setErrorData("Valorizzare il codice fiscale dell’ente");
                risultato.setEsito(false);
                return risultato;
            }

            if (cfPIVAInfoInsert.isBlockDuplicateCf()) {
                Long occurencies = this.tabellatiMapper.countUffintCfOccurencies(form.getCodFisc());
                if (occurencies > 0) {
                    risultato.setEsito(false);
                    risultato.setErrorData(ResponseResult.ERROR_EXISTING_CF);
                    return risultato;
                }
            }

            if (!form.getCodFisc().startsWith("CFAVCP-")
                    && !UtilityFiscali.isValidCodiceFiscale(form.getCodFisc(), true)
                    && !UtilityFiscali.isValidPartitaIVA(form.getCodFisc())) {//
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_INVALID_CF);
                return risultato;
            }

            String codein = calcolaCodificaAutomatica("UFFINT", "CODEIN");
            form.setCodein(codein);
            this.tabellatiMapper.insertSa(form);
            risultato.setData(codein + "");
            risultato.setEsito(true);

        } catch (Exception e) {
            logger.error("Errore inaspettato durante la creazione della stazione appaltante:", e);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResponse updateSa(StazioneAppaltanteUpdateForm form) {
        BaseResponse risultato = new BaseResponse();
        try {

            CFPIVAInfoInsert cfPIVAInfoInsert = getCFPIVAInfoInsert(ECFPIVAInfoInsert.UFFINT);

            if (form.getCfAnac() == null && form.getCodFisc() == null) {
                logger.error("Errore in updateSa: codice fiscale obbligatorio");
                risultato.setErrorData("Valorizzare il codice fiscale dell’ente");
                risultato.setEsito(false);
                return risultato;
            }

            if (Constants.UFFINT_RISERVATA.equals(form.getCodFisc())) {
                logger.error("Errore in updateSa: Ufficio intestatario \"RISERVATA\" non modificabile");
                risultato.setErrorData("Ufficio intestatario \"RISERVATA\" non modificabile");
                risultato.setEsito(false);
                return risultato;
            }

            if (cfPIVAInfoInsert.isBlockDuplicateCf()) {
                Long occurencies = this.tabellatiMapper.countUffintCfOccurencies(form.getCodFisc());
                if (occurencies > 0) {
                    risultato.setEsito(false);
                    risultato.setErrorData(ResponseResult.ERROR_EXISTING_CF);
                    return risultato;
                }
            }

            if (!form.getCodFisc().startsWith("CFAVCP-")
                    && !UtilityFiscali.isValidCodiceFiscale(form.getCodFisc(), true)
                    && !UtilityFiscali.isValidPartitaIVA(form.getCodFisc())) {//
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_INVALID_CF);
                return risultato;
            }

            this.tabellatiMapper.updateSa(form);
            risultato.setEsito(true);

        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la creazione della stazione appaltante:", t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    public boolean hasPermissionOnSa(Long syscon) {
        String pwbou = this.tabellatiMapper.getUserPermission(syscon);
        return pwbou != null && !pwbou.contains("ou214");
    }

    public ResponseListaStazioneAppaltante getListaStazioneAppaltante(ArchivioSaSearchform searchForm) {

        ResponseListaStazioneAppaltante risultato = new ResponseListaStazioneAppaltante();
        try {
            if (searchForm.getDenominazione() != null) {
                searchForm.setDenominazione("%" + searchForm.getDenominazione().toUpperCase() + "%");
            }
            if (searchForm.getCodFisc() != null) {
                searchForm.setCodFisc("%" + searchForm.getCodFisc().toUpperCase() + "%");
            }

            if (searchForm.getCitta() != null) {
                searchForm.setCitta("%" + searchForm.getCitta().toUpperCase() + "%");
            }
            if (searchForm.getProvincia() != null) {
                searchForm.setProvincia(searchForm.getProvincia().toUpperCase());
            }
            if (searchForm.getCodiceAnagrafico() != null) {
                searchForm.setCodiceAnagrafico("%" + searchForm.getCodiceAnagrafico().toUpperCase() + "%");
            }
            if (searchForm.getIndirizzo() != null) {
                searchForm.setIndirizzo("%" + searchForm.getIndirizzo().toUpperCase() + "%");
            }
            if(searchForm.getCodAusa() != null) {
                searchForm.setCodAusa("%" + searchForm.getCodAusa().toUpperCase() + "%");
            }
            List<StazioneAppaltanteListaEntry> stazioniAppaltantiResponse = new ArrayList<StazioneAppaltanteListaEntry>();
            int totalCount = this.tabellatiMapper.countSearchStazioniAppaltanti(searchForm);
            RowBounds rowBounds = new RowBounds(searchForm.getSkip(), searchForm.getTake());
            risultato.setTotalCount(totalCount);
            List<StazioneAppaltanteListaEntry> stazioniAppaltanti = this.tabellatiMapper
                    .getStazioniAppaltanti(searchForm, rowBounds);
            for (StazioneAppaltanteListaEntry stazioniAppaltante : stazioniAppaltanti) {
                stazioniAppaltante.setDeletable(checkDeleteSA(stazioniAppaltante));
                stazioniAppaltantiResponse.add(stazioniAppaltante);
            }
            risultato.setData(stazioniAppaltantiResponse);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la get delle stazioni appaltanti.", t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    private boolean checkDeleteSA(StazioneAppaltanteListaEntry saEntry) throws Exception {
        try {

            String codein = saEntry.getCodice();
            String codiceFiscale = saEntry.getCodFisc();

            // se l'uffint ha codice fiscale "RISERVATA" allora disabilito la cancellazione
            if (Constants.UFFINT_RISERVATA.equals(codiceFiscale)) {
                return false;
            }

            Long occorrenze = this.tabellatiMapper.countOccorenzeSA(codein);
            return occorrenze == 0;
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la checkDeleteSA", e);
            throw (e);
        }

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResult deleteStazioneAppaltante(String codein) throws Exception {

        ResponseResult risultato = new ResponseResult();
        try {

            StazioneAppaltanteEntry saEntry = this.tabellatiMapper.getStazioneAppaltante(codein);

            if (saEntry != null && Constants.UFFINT_RISERVATA.equals(saEntry.getCodFisc())) {
                logger.error("Errore in deleteStazioneAppaltante: Ufficio intestatario \"RISERVATA\" non modificabile");
                risultato.setErrorData("Ufficio intestatario \"RISERVATA\" non modificabile");
                risultato.setEsito(false);
                return risultato;
            }

            this.tabellatiMapper.deleteStazioneAppaltante(codein);
            this.tabellatiMapper.deleteUserEinSA(codein);
            this.tabellatiMapper.deleteUfficiSA(codein);
            this.tabellatiMapper.deleteCentroDiCostoSA(codein);
            risultato.setEsito(true);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la cancellazione della stazione appaltante: " + codein, e);
            throw (e);
        }
        return risultato;
    }

    public ResponseStazioneAppaltante getStazioneAppaltante(String codein) {

        ResponseStazioneAppaltante risultato = new ResponseStazioneAppaltante();
        try {
            StazioneAppaltanteEntry stazioneAppaltante = this.tabellatiMapper.getStazioneAppaltante(codein);
            risultato.setData(stazioneAppaltante);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante la get della stazione appaltante: " + codein, t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    public ResponseBaseStazioneAppaltante getSaByCodiceFiscale(String codiceFiscale) {
        ResponseBaseStazioneAppaltante risultato = new ResponseBaseStazioneAppaltante();
        try {
            StazioneAppaltanteBaseEntry stazioneAppaltante = this.tabellatiMapper.getSaByCodiceFiscale(codiceFiscale);
            risultato.setData(stazioneAppaltante);
            risultato.setEsito(true);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la get delle stazioni appaltanti", e);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    public ResponseResult getLogoutUrl() {
        ResponseResult risultato = new ResponseResult();
        try {
            String logoutUrl = this.wConfigManager.getConfigurationValue(
                    Constants.CHIAVE_LOGOUT_URL);
            risultato.setData(logoutUrl);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore durante il recupero del logout URL", t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    private ImpresaInsertForm setImpresaNomImp(ImpresaInsertForm impresa) {
        if (impresa.getNomImp() == null && StringUtils.isNotBlank(impresa.getRagioneSociale())) {
            String nomImp = impresa.getRagioneSociale().length() > 61 ? impresa.getRagioneSociale().substring(0, 61)
                    : impresa.getRagioneSociale();
            impresa.setNomImp(nomImp);
        }
        return impresa;
    }

    public ResponseListaRup getListaTecniciNonPaginata(final TecniciNonPaginatiSearchForm searchForm) {
        ResponseListaRup risultato = new ResponseListaRup();
        try {
            String filtroSA = this.wConfigManager.getConfigurationValue(
                    Constants.APPLICATION_SA_FILTER);
            if (searchForm.getSearchString() != null) {
                searchForm.setSearchStringLike("%" + searchForm.getSearchString().toLowerCase() + "%");
            }

            List<RupEntry> entries = new ArrayList<RupEntry>();
            if (filtroSA.contains("TECNI") || (searchForm.getStazioneAppaltante() != null && !"*".equals(searchForm.getStazioneAppaltante()))) {
                entries = this.tabellatiMapper.getListaTecniciNonPaginataWithSA(searchForm);
            } else {
                entries = this.tabellatiMapper.getListaTecniciNonPaginataWithoutSA(searchForm);
            }
            risultato.setData(entries);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante il recupero della lista dei tecnici. SA:"
                    + searchForm.getStazioneAppaltante(), t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    public ResponseListaImprese getListaImpreseNonPaginata(ImpreseNonPaginateSearchForm searchForm) {
        ResponseListaImprese risultato = new ResponseListaImprese();
        try {
            String filtroSA = this.wConfigManager.getConfigurationValue(
                    Constants.APPLICATION_SA_FILTER);
            if (searchForm.getSearchString() != null) {
                searchForm.setSearchStringLike("%" + searchForm.getSearchString().toLowerCase() + "%");
            }

            List<ImpresaBaseEntry> entries = new ArrayList<ImpresaBaseEntry>();
            if (filtroSA.contains("IMPR") || (searchForm.getStazioneAppaltante() != null && !"*".equals(searchForm.getStazioneAppaltante()))) {
                entries = this.tabellatiMapper.getListaImpreseNonPaginataWithSa(searchForm);
            } else {
                entries = this.tabellatiMapper.getListaImpreseNonPaginataWithoutSa(searchForm);
            }
            risultato.setData(entries);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante il recupero della lista delle imprese. SA:"
                    + searchForm.getStazioneAppaltante(), t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    public ResponseListaCentriDiCosto getListaCdcNonPaginata(CdcNonPaginatiSearchForm searchForm) {
        ResponseListaCentriDiCosto risultato = new ResponseListaCentriDiCosto();
        try {
            if (searchForm.getSearchString() != null) {
                searchForm.setSearchStringLike("%" + searchForm.getSearchString().toLowerCase() + "%");
            }

            List<CentroDiCostoEntry> entries = new ArrayList<CentroDiCostoEntry>();
            if (!"*".equals(searchForm.getStazioneAppaltante())) {
                entries = this.tabellatiMapper.getListaCdcNonPaginata(searchForm);
            } else {
                entries = this.tabellatiMapper.getListaCdcNonPaginataWithoutSA(searchForm);
            }

            risultato.setData(entries);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante il recupero della lista delle imprese. SA:"
                    + searchForm.getStazioneAppaltante(), t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    public ResponseListaUffici getListaUfficiNonPaginata(UfficiNonPaginatiSearchForm searchForm) {
        ResponseListaUffici risultato = new ResponseListaUffici();
        try {
            if (searchForm.getSearchString() != null) {
                searchForm.setSearchStringLike("%" + searchForm.getSearchString().toLowerCase() + "%");
            }

            List<UffEntry> entries = new ArrayList<UffEntry>();
            entries = this.tabellatiMapper.getListaUfficiNonPaginata(searchForm);
            risultato.setData(entries);
            risultato.setEsito(true);
        } catch (Throwable t) {
            logger.error("Errore inaspettato durante il recupero della lista degli uffici. SA:"
                    + searchForm.getStazioneAppaltante(), t);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }
        return risultato;
    }

    public ResponseResultParametric<Integer> getCurrentMessagesStatus(final Long loggedUserSyscon) {
        ResponseResultParametric<Integer> risultato = new ResponseResultParametric<>();

        try {
            risultato.setEsito(true);

            if (loggedUserSyscon == null) {
                logger.error("L'utente {} non e' stato trovato", loggedUserSyscon);
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_NOT_FOUND);
                return risultato;
            }

            if (!userManager.isMessaggisticaInternaEnabled()) {
                logger.error("Messaggistica interna disabilitata");
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResult.ERROR_DISABLED);
                return risultato;
            }

            Integer count = this.tabellatiMapper.getCountUnreadMessages(loggedUserSyscon);

            risultato.setData(count);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la getCurrentMessagesStatus", e);
            risultato.setErrorData(ResponseResult.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }

        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ResponseResultParametric<Boolean> deleteCurrentMessage(final Long loggedUserSyscon, final Long messageId) {
        ResponseResultParametric<Boolean> risultato = new ResponseResultParametric<>();
        risultato.setEsito(false);

        if (loggedUserSyscon == null) {
            logger.error("L'utente {} non e' stato trovato", loggedUserSyscon);
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_NOT_FOUND);
            return risultato;
        }

        if (!userManager.isMessaggisticaInternaEnabled()) {
            logger.error("Messaggistica interna disabilitata");
            risultato.setEsito(false);
            risultato.setErrorData(ResponseResult.ERROR_DISABLED);
            return risultato;
        }

        try {

            MessageEntry message = this.tabellatiMapper.getMessage(messageId);

            // verifico la presenza del messaggio
            if (message == null) {
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResultParametric.ERROR_NOT_FOUND);
                return risultato;
            }

            // l'utente sta provando a cancellare un messaggio non appartenente a se stesso
            if (!message.getSysconRecipient().equals(loggedUserSyscon)) {
                risultato.setEsito(false);
                risultato.setErrorData(ResponseResultParametric.ERROR_PERMISSION);
                return risultato;
            }

            /*
                cancello il messaggio ricevuto ma mantengo il record in w_message_out_rec per avere
                una traccia del messaggio ricevuto
             */
            this.tabellatiMapper.deleteMessageIn(messageId);

            risultato.setEsito(true);
            risultato.setData(true);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante la deleteCurrentMessage", e);
            risultato.setErrorData(ResponseResultParametric.ERROR_UNEXPECTED);
            risultato.setEsito(false);
        }

        return risultato;
    }

    public ResponseDto getAppInfo() {
        ResponseDto res = new ResponseDto();
        res.setEsito(true);
        try {
            String nomeComponente = this.wConfigManager.getConfigurationValue(PROP_NPA_NOMECOMPONENTE);
            String versioneComponente = this.wConfigManager.getConfigurationValue(PROP_NPA_VERSIONECOMPONENTE);
            String nomePiattaforma = this.wConfigManager.getConfigurationValue(PROP_NPA_NOMEPIATTAFORMA);
            String versionePiattaforma = this.wConfigManager.getConfigurationValue(PROP_NPA_VERSIONEPIATTAFORMA);

            AppInfoEntry info = new AppInfoEntry(nomeComponente, versioneComponente, nomePiattaforma, versionePiattaforma);
            res.setData(info);
        } catch (Exception e) {
            logger.error("Errore inaspettato durante il metodo getAppInfo", e);
            res.setEsito(false);
            res.setErrorData(BaseResponse.ERROR_UNEXPECTED);
        }
        return res;
    }

    private boolean isUserSystemAdministrator(final Long syscon) {
        String permissions = tabellatiMapper.getUserPermissionBySyscon(syscon);
        if (StringUtils.isBlank(permissions))
            return false;

        List<String> opzioniUtente = getListaOpzioniUtente(permissions);
        return opzioniUtente.contains(Constants.OU_AMMINISTRATORE);
    }

    protected List<String> getListaOpzioniUtente(final String opzioniUtenteString) {
        List<String> opzioniUtenteList = null;

        if (StringUtils.isNotBlank(opzioniUtenteString)) {
            opzioniUtenteList = new ArrayList<>(
                    Arrays.asList(opzioniUtenteString.split(Constants.OU_SEPARATORE_REGEX)));
        } else {
            opzioniUtenteList = new ArrayList<>();
        }

        return opzioniUtenteList;
    }

    public ResponseCountReportPredefiniti getCountListaReportPredefiniti(
            Long syscon,
            String idProfilo,
            String uffInt,
            String codApp
    ){

        logger.debug("START esecuzione in TabellatiManager::getCountListaReportPredefiniti");

        ResponseCountReportPredefiniti response = new ResponseCountReportPredefiniti();
        response.setDone(Constants.RESPONSE_DONE_N);

        try {
            Long countReportPredefiniti = this.tabellatiMapper.getCountListaReportPredefiniti(codApp, syscon, idProfilo, uffInt);

            response.setResponse(countReportPredefiniti);
            response.setDone(Constants.RESPONSE_DONE_Y);

        } catch (Exception e) {
            logger.error("Errore inaspettato durante il conteggio dei report predefiniti", e);
            response.setDone(Constants.RESPONSE_DONE_N);
            List<String> errorMessages = new ArrayList<String>();
            errorMessages.add(ResponseResult.ERROR_UNEXPECTED + ": " + e.getMessage());
            response.setMessages(errorMessages);
        }
        return response;
    }
}
