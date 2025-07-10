
package it.appaltiecontratti.monitoraggiocontratti.simog.beans;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java per CollaudoType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="CollaudoType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_REGOLARE_ESEC"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_COLLAUDO_STAT"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}MODO_COLLAUDO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_NOMINA_COLL"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_INIZIO_OPER"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_CERT_COLLAUDO"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DATA_DELIBERA"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ESITO_COLLAUDO use="required""/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMP_FINALE_LAVORI"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMP_FINALE_SERVIZI"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMP_FINALE_FORNIT"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMP_FINALE_SECUR"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMP_PROGETTAZIONE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}IMP_DISPOSIZIONE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}AMM_NUM_DEFINITE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}AMM_NUM_DADEF"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}AMM_IMPORTO_RICH"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}AMM_IMPORTO_DEF"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ARB_NUM_DEFINITE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ARB_NUM_DADEF"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ARB_IMPORTO_RICH"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ARB_IMPORTO_DEF"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}GIU_NUM_DEFINITE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}GIU_NUM_DADEF"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}GIU_IMPORTO_RICH"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}GIU_IMPORTO_DEF"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}TRA_NUM_DEFINITE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}TRA_NUM_DADEF"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}TRA_IMPORTO_RICH"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}TRA_IMPORTO_DEF"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}LAVORI_ESTESI"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_LOCALE"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_SCHEDA_SIMOG"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_STATO_SCHEDA"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
public class CollaudoType {

    protected XMLGregorianCalendar dataregolareesec;
    protected XMLGregorianCalendar datacollaudostat;
    protected String modocollaudo;
    protected XMLGregorianCalendar datanominacoll;
    protected XMLGregorianCalendar datainiziooper;
    protected XMLGregorianCalendar datacertcollaudo;
    protected XMLGregorianCalendar datadelibera;
    protected FlagEsitoCollaudoType esitocollaudo;
    protected BigDecimal impfinalelavori;
    protected BigDecimal impfinaleservizi;
    protected BigDecimal impfinalefornit;
    protected BigDecimal impfinalesecur;
    protected String impprogettazione;
    protected String impdisposizione;
    protected Integer ammnumdefinite;
    protected Integer ammnumdadef;
    protected BigDecimal ammimportorich;
    protected BigDecimal ammimportodef;
    protected Integer arbnumdefinite;
    protected Integer arbnumdadef;
    protected BigDecimal arbimportorich;
    protected BigDecimal arbimportodef;
    protected Integer giunumdefinite;
    protected Integer giunumdadef;
    protected BigDecimal giuimportorich;
    protected BigDecimal giuimportodef;
    protected Integer tranumdefinite;
    protected Integer tranumdadef;
    protected BigDecimal traimportorich;
    protected BigDecimal traimportodef;
    protected FlagSNType lavoriestesi;
    protected String idschedalocale;
    protected String idschedasimog;
    protected String idstatoscheda;

    /**
     * Recupera il valore della proprietà dataregolareesec.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAREGOLAREESEC() {
        return dataregolareesec;
    }

    /**
     * Imposta il valore della proprietà dataregolareesec.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAREGOLAREESEC(XMLGregorianCalendar value) {
        this.dataregolareesec = value;
    }

    /**
     * Recupera il valore della proprietà datacollaudostat.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATACOLLAUDOSTAT() {
        return datacollaudostat;
    }

    /**
     * Imposta il valore della proprietà datacollaudostat.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATACOLLAUDOSTAT(XMLGregorianCalendar value) {
        this.datacollaudostat = value;
    }

    /**
     * Recupera il valore della proprietà modocollaudo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMODOCOLLAUDO() {
        return modocollaudo;
    }

    /**
     * Imposta il valore della proprietà modocollaudo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMODOCOLLAUDO(String value) {
        this.modocollaudo = value;
    }

    /**
     * Recupera il valore della proprietà datanominacoll.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATANOMINACOLL() {
        return datanominacoll;
    }

    /**
     * Imposta il valore della proprietà datanominacoll.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATANOMINACOLL(XMLGregorianCalendar value) {
        this.datanominacoll = value;
    }

    /**
     * Recupera il valore della proprietà datainiziooper.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATAINIZIOOPER() {
        return datainiziooper;
    }

    /**
     * Imposta il valore della proprietà datainiziooper.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATAINIZIOOPER(XMLGregorianCalendar value) {
        this.datainiziooper = value;
    }

    /**
     * Recupera il valore della proprietà datacertcollaudo.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATACERTCOLLAUDO() {
        return datacertcollaudo;
    }

    /**
     * Imposta il valore della proprietà datacertcollaudo.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATACERTCOLLAUDO(XMLGregorianCalendar value) {
        this.datacertcollaudo = value;
    }

    /**
     * Recupera il valore della proprietà datadelibera.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDATADELIBERA() {
        return datadelibera;
    }

    /**
     * Imposta il valore della proprietà datadelibera.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDATADELIBERA(XMLGregorianCalendar value) {
        this.datadelibera = value;
    }

    /**
     * Recupera il valore della proprietà esitocollaudo.
     * 
     * @return
     *     possible object is
     *     {@link FlagEsitoCollaudoType }
     *     
     */
    public FlagEsitoCollaudoType getESITOCOLLAUDO() {
        return esitocollaudo;
    }

    /**
     * Imposta il valore della proprietà esitocollaudo.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagEsitoCollaudoType }
     *     
     */
    public void setESITOCOLLAUDO(FlagEsitoCollaudoType value) {
        this.esitocollaudo = value;
    }

    /**
     * Recupera il valore della proprietà impfinalelavori.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPFINALELAVORI() {
        return impfinalelavori;
    }

    /**
     * Imposta il valore della proprietà impfinalelavori.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPFINALELAVORI(BigDecimal value) {
        this.impfinalelavori = value;
    }

    /**
     * Recupera il valore della proprietà impfinaleservizi.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPFINALESERVIZI() {
        return impfinaleservizi;
    }

    /**
     * Imposta il valore della proprietà impfinaleservizi.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPFINALESERVIZI(BigDecimal value) {
        this.impfinaleservizi = value;
    }

    /**
     * Recupera il valore della proprietà impfinalefornit.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPFINALEFORNIT() {
        return impfinalefornit;
    }

    /**
     * Imposta il valore della proprietà impfinalefornit.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPFINALEFORNIT(BigDecimal value) {
        this.impfinalefornit = value;
    }

    /**
     * Recupera il valore della proprietà impfinalesecur.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIMPFINALESECUR() {
        return impfinalesecur;
    }

    /**
     * Imposta il valore della proprietà impfinalesecur.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIMPFINALESECUR(BigDecimal value) {
        this.impfinalesecur = value;
    }

    /**
     * Recupera il valore della proprietà impprogettazione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPPROGETTAZIONE() {
        return impprogettazione;
    }

    /**
     * Imposta il valore della proprietà impprogettazione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPPROGETTAZIONE(String value) {
        this.impprogettazione = value;
    }

    /**
     * Recupera il valore della proprietà impdisposizione.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMPDISPOSIZIONE() {
        return impdisposizione;
    }

    /**
     * Imposta il valore della proprietà impdisposizione.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMPDISPOSIZIONE(String value) {
        this.impdisposizione = value;
    }

    /**
     * Recupera il valore della proprietà ammnumdefinite.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAMMNUMDEFINITE() {
        return ammnumdefinite;
    }

    /**
     * Imposta il valore della proprietà ammnumdefinite.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAMMNUMDEFINITE(Integer value) {
        this.ammnumdefinite = value;
    }

    /**
     * Recupera il valore della proprietà ammnumdadef.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getAMMNUMDADEF() {
        return ammnumdadef;
    }

    /**
     * Imposta il valore della proprietà ammnumdadef.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setAMMNUMDADEF(Integer value) {
        this.ammnumdadef = value;
    }

    /**
     * Recupera il valore della proprietà ammimportorich.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAMMIMPORTORICH() {
        return ammimportorich;
    }

    /**
     * Imposta il valore della proprietà ammimportorich.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAMMIMPORTORICH(BigDecimal value) {
        this.ammimportorich = value;
    }

    /**
     * Recupera il valore della proprietà ammimportodef.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAMMIMPORTODEF() {
        return ammimportodef;
    }

    /**
     * Imposta il valore della proprietà ammimportodef.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAMMIMPORTODEF(BigDecimal value) {
        this.ammimportodef = value;
    }

    /**
     * Recupera il valore della proprietà arbnumdefinite.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getARBNUMDEFINITE() {
        return arbnumdefinite;
    }

    /**
     * Imposta il valore della proprietà arbnumdefinite.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setARBNUMDEFINITE(Integer value) {
        this.arbnumdefinite = value;
    }

    /**
     * Recupera il valore della proprietà arbnumdadef.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getARBNUMDADEF() {
        return arbnumdadef;
    }

    /**
     * Imposta il valore della proprietà arbnumdadef.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setARBNUMDADEF(Integer value) {
        this.arbnumdadef = value;
    }

    /**
     * Recupera il valore della proprietà arbimportorich.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getARBIMPORTORICH() {
        return arbimportorich;
    }

    /**
     * Imposta il valore della proprietà arbimportorich.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setARBIMPORTORICH(BigDecimal value) {
        this.arbimportorich = value;
    }

    /**
     * Recupera il valore della proprietà arbimportodef.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getARBIMPORTODEF() {
        return arbimportodef;
    }

    /**
     * Imposta il valore della proprietà arbimportodef.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setARBIMPORTODEF(BigDecimal value) {
        this.arbimportodef = value;
    }

    /**
     * Recupera il valore della proprietà giunumdefinite.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGIUNUMDEFINITE() {
        return giunumdefinite;
    }

    /**
     * Imposta il valore della proprietà giunumdefinite.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGIUNUMDEFINITE(Integer value) {
        this.giunumdefinite = value;
    }

    /**
     * Recupera il valore della proprietà giunumdadef.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getGIUNUMDADEF() {
        return giunumdadef;
    }

    /**
     * Imposta il valore della proprietà giunumdadef.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setGIUNUMDADEF(Integer value) {
        this.giunumdadef = value;
    }

    /**
     * Recupera il valore della proprietà giuimportorich.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGIUIMPORTORICH() {
        return giuimportorich;
    }

    /**
     * Imposta il valore della proprietà giuimportorich.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGIUIMPORTORICH(BigDecimal value) {
        this.giuimportorich = value;
    }

    /**
     * Recupera il valore della proprietà giuimportodef.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getGIUIMPORTODEF() {
        return giuimportodef;
    }

    /**
     * Imposta il valore della proprietà giuimportodef.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setGIUIMPORTODEF(BigDecimal value) {
        this.giuimportodef = value;
    }

    /**
     * Recupera il valore della proprietà tranumdefinite.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTRANUMDEFINITE() {
        return tranumdefinite;
    }

    /**
     * Imposta il valore della proprietà tranumdefinite.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTRANUMDEFINITE(Integer value) {
        this.tranumdefinite = value;
    }

    /**
     * Recupera il valore della proprietà tranumdadef.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTRANUMDADEF() {
        return tranumdadef;
    }

    /**
     * Imposta il valore della proprietà tranumdadef.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTRANUMDADEF(Integer value) {
        this.tranumdadef = value;
    }

    /**
     * Recupera il valore della proprietà traimportorich.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRAIMPORTORICH() {
        return traimportorich;
    }

    /**
     * Imposta il valore della proprietà traimportorich.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRAIMPORTORICH(BigDecimal value) {
        this.traimportorich = value;
    }

    /**
     * Recupera il valore della proprietà traimportodef.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTRAIMPORTODEF() {
        return traimportodef;
    }

    /**
     * Imposta il valore della proprietà traimportodef.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTRAIMPORTODEF(BigDecimal value) {
        this.traimportodef = value;
    }

    /**
     * Recupera il valore della proprietà lavoriestesi.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getLAVORIESTESI() {
        return lavoriestesi;
    }

    /**
     * Imposta il valore della proprietà lavoriestesi.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setLAVORIESTESI(FlagSNType value) {
        this.lavoriestesi = value;
    }

    /**
     * Recupera il valore della proprietà idschedalocale.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDSCHEDALOCALE() {
        return idschedalocale;
    }

    /**
     * Imposta il valore della proprietà idschedalocale.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDSCHEDALOCALE(String value) {
        this.idschedalocale = value;
    }

    /**
     * Recupera il valore della proprietà idschedasimog.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDSCHEDASIMOG() {
        return idschedasimog;
    }

    /**
     * Imposta il valore della proprietà idschedasimog.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDSCHEDASIMOG(String value) {
        this.idschedasimog = value;
    }

    /**
     * Recupera il valore della proprietà idstatoscheda.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDSTATOSCHEDA() {
        return idstatoscheda;
    }

    /**
     * Imposta il valore della proprietà idstatoscheda.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDSTATOSCHEDA(String value) {
        this.idstatoscheda = value;
    }

}
