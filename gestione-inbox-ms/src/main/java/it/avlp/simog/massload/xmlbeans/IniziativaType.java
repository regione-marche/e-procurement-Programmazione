
package it.avlp.simog.massload.xmlbeans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java per IniziativaType complex type.
 * 
 * <p>Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * <pre>
 * &lt;complexType name="IniziativaType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="CATEGORIE_I" type="{xmlbeans.massload.simog.avlp.it}CategLottoType" minOccurs="0"/&gt;
 *         &lt;element name="TERRITORI_I" type="{xmlbeans.massload.simog.avlp.it}TerritorioType" minOccurs="0"/&gt;
 *         &lt;element name="AMBITI_LOTTO_I" type="{xmlbeans.massload.simog.avlp.it}AmbitoType" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}ID_GARA_I"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CIG_I"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DESCRIZIONE_SOGG_AGG_I"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}DESCRIZIONE_INIZIATIVA_I"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}SSAA_RIF_I"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}STATO_I"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}CONFRONTO_COMPETITIVO_I"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}NOTE_I"/&gt;
 *       &lt;attribute ref="{xmlbeans.massload.simog.avlp.it}URL_I"/&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IniziativaType", propOrder = {
    "categoriei",
    "territorii",
    "ambitilottoi"
})
public class IniziativaType {

    @XmlElement(name = "CATEGORIE_I")
    protected CategLottoType categoriei;
    @XmlElement(name = "TERRITORI_I")
    protected TerritorioType territorii;
    @XmlElement(name = "AMBITI_LOTTO_I")
    protected AmbitoType ambitilottoi;
    @XmlAttribute(name = "ID_GARA_I", namespace = "xmlbeans.massload.simog.avlp.it")
    protected Long idgarai;
    @XmlAttribute(name = "CIG_I", namespace = "xmlbeans.massload.simog.avlp.it")
    protected String cigi;
    @XmlAttribute(name = "DESCRIZIONE_SOGG_AGG_I", namespace = "xmlbeans.massload.simog.avlp.it")
    protected String descrizionesoggaggi;
    @XmlAttribute(name = "DESCRIZIONE_INIZIATIVA_I", namespace = "xmlbeans.massload.simog.avlp.it")
    protected String descrizioneiniziativai;
    @XmlAttribute(name = "SSAA_RIF_I", namespace = "xmlbeans.massload.simog.avlp.it")
    protected String ssaarifi;
    @XmlAttribute(name = "STATO_I", namespace = "xmlbeans.massload.simog.avlp.it")
    protected FlagSNType statoi;
    @XmlAttribute(name = "CONFRONTO_COMPETITIVO_I", namespace = "xmlbeans.massload.simog.avlp.it")
    protected FlagSNType confrontocompetitivoi;
    @XmlAttribute(name = "NOTE_I", namespace = "xmlbeans.massload.simog.avlp.it")
    protected String notei;
    @XmlAttribute(name = "URL_I", namespace = "xmlbeans.massload.simog.avlp.it")
    protected String urli;

    /**
     * Recupera il valore della proprietà categoriei.
     * 
     * @return
     *     possible object is
     *     {@link CategLottoType }
     *     
     */
    public CategLottoType getCATEGORIEI() {
        return categoriei;
    }

    /**
     * Imposta il valore della proprietà categoriei.
     * 
     * @param value
     *     allowed object is
     *     {@link CategLottoType }
     *     
     */
    public void setCATEGORIEI(CategLottoType value) {
        this.categoriei = value;
    }

    /**
     * Recupera il valore della proprietà territorii.
     * 
     * @return
     *     possible object is
     *     {@link TerritorioType }
     *     
     */
    public TerritorioType getTERRITORII() {
        return territorii;
    }

    /**
     * Imposta il valore della proprietà territorii.
     * 
     * @param value
     *     allowed object is
     *     {@link TerritorioType }
     *     
     */
    public void setTERRITORII(TerritorioType value) {
        this.territorii = value;
    }

    /**
     * Recupera il valore della proprietà ambitilottoi.
     * 
     * @return
     *     possible object is
     *     {@link AmbitoType }
     *     
     */
    public AmbitoType getAMBITILOTTOI() {
        return ambitilottoi;
    }

    /**
     * Imposta il valore della proprietà ambitilottoi.
     * 
     * @param value
     *     allowed object is
     *     {@link AmbitoType }
     *     
     */
    public void setAMBITILOTTOI(AmbitoType value) {
        this.ambitilottoi = value;
    }

    /**
     * Recupera il valore della proprietà idgarai.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIDGARAI() {
        return idgarai;
    }

    /**
     * Imposta il valore della proprietà idgarai.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIDGARAI(Long value) {
        this.idgarai = value;
    }

    /**
     * Recupera il valore della proprietà cigi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCIGI() {
        return cigi;
    }

    /**
     * Imposta il valore della proprietà cigi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCIGI(String value) {
        this.cigi = value;
    }

    /**
     * Recupera il valore della proprietà descrizionesoggaggi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRIZIONESOGGAGGI() {
        return descrizionesoggaggi;
    }

    /**
     * Imposta il valore della proprietà descrizionesoggaggi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRIZIONESOGGAGGI(String value) {
        this.descrizionesoggaggi = value;
    }

    /**
     * Recupera il valore della proprietà descrizioneiniziativai.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDESCRIZIONEINIZIATIVAI() {
        return descrizioneiniziativai;
    }

    /**
     * Imposta il valore della proprietà descrizioneiniziativai.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDESCRIZIONEINIZIATIVAI(String value) {
        this.descrizioneiniziativai = value;
    }

    /**
     * Recupera il valore della proprietà ssaarifi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSSAARIFI() {
        return ssaarifi;
    }

    /**
     * Imposta il valore della proprietà ssaarifi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSSAARIFI(String value) {
        this.ssaarifi = value;
    }

    /**
     * Recupera il valore della proprietà statoi.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getSTATOI() {
        return statoi;
    }

    /**
     * Imposta il valore della proprietà statoi.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setSTATOI(FlagSNType value) {
        this.statoi = value;
    }

    /**
     * Recupera il valore della proprietà confrontocompetitivoi.
     * 
     * @return
     *     possible object is
     *     {@link FlagSNType }
     *     
     */
    public FlagSNType getCONFRONTOCOMPETITIVOI() {
        return confrontocompetitivoi;
    }

    /**
     * Imposta il valore della proprietà confrontocompetitivoi.
     * 
     * @param value
     *     allowed object is
     *     {@link FlagSNType }
     *     
     */
    public void setCONFRONTOCOMPETITIVOI(FlagSNType value) {
        this.confrontocompetitivoi = value;
    }

    /**
     * Recupera il valore della proprietà notei.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOTEI() {
        return notei;
    }

    /**
     * Imposta il valore della proprietà notei.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOTEI(String value) {
        this.notei = value;
    }

    /**
     * Recupera il valore della proprietà urli.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURLI() {
        return urli;
    }

    /**
     * Imposta il valore della proprietà urli.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURLI(String value) {
        this.urli = value;
    }

}
