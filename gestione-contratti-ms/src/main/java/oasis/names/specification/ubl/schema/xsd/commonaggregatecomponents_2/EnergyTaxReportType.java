//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxEnergyAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxEnergyBalanceAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TaxEnergyOnAccountAmountType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per EnergyTaxReportType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="EnergyTaxReportType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TaxEnergyAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TaxEnergyOnAccountAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TaxEnergyBalanceAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TaxScheme"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EnergyTaxReportType", propOrder = {
    "ublExtensions",
    "taxEnergyAmount",
    "taxEnergyOnAccountAmount",
    "taxEnergyBalanceAmount",
    "taxScheme"
})
public class EnergyTaxReportType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "TaxEnergyAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TaxEnergyAmountType taxEnergyAmount;
    @XmlElement(name = "TaxEnergyOnAccountAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TaxEnergyOnAccountAmountType taxEnergyOnAccountAmount;
    @XmlElement(name = "TaxEnergyBalanceAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TaxEnergyBalanceAmountType taxEnergyBalanceAmount;
    @XmlElement(name = "TaxScheme", required = true)
    protected TaxSchemeType taxScheme;

    /**
     * Recupera il valore della proprietà ublExtensions.
     * 
     * @return
     *     possible object is
     *     {@link UBLExtensionsType }
     *     
     */
    public UBLExtensionsType getUBLExtensions() {
        return ublExtensions;
    }

    /**
     * Imposta il valore della proprietà ublExtensions.
     * 
     * @param value
     *     allowed object is
     *     {@link UBLExtensionsType }
     *     
     */
    public void setUBLExtensions(UBLExtensionsType value) {
        this.ublExtensions = value;
    }

    /**
     * Recupera il valore della proprietà taxEnergyAmount.
     * 
     * @return
     *     possible object is
     *     {@link TaxEnergyAmountType }
     *     
     */
    public TaxEnergyAmountType getTaxEnergyAmount() {
        return taxEnergyAmount;
    }

    /**
     * Imposta il valore della proprietà taxEnergyAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxEnergyAmountType }
     *     
     */
    public void setTaxEnergyAmount(TaxEnergyAmountType value) {
        this.taxEnergyAmount = value;
    }

    /**
     * Recupera il valore della proprietà taxEnergyOnAccountAmount.
     * 
     * @return
     *     possible object is
     *     {@link TaxEnergyOnAccountAmountType }
     *     
     */
    public TaxEnergyOnAccountAmountType getTaxEnergyOnAccountAmount() {
        return taxEnergyOnAccountAmount;
    }

    /**
     * Imposta il valore della proprietà taxEnergyOnAccountAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxEnergyOnAccountAmountType }
     *     
     */
    public void setTaxEnergyOnAccountAmount(TaxEnergyOnAccountAmountType value) {
        this.taxEnergyOnAccountAmount = value;
    }

    /**
     * Recupera il valore della proprietà taxEnergyBalanceAmount.
     * 
     * @return
     *     possible object is
     *     {@link TaxEnergyBalanceAmountType }
     *     
     */
    public TaxEnergyBalanceAmountType getTaxEnergyBalanceAmount() {
        return taxEnergyBalanceAmount;
    }

    /**
     * Imposta il valore della proprietà taxEnergyBalanceAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxEnergyBalanceAmountType }
     *     
     */
    public void setTaxEnergyBalanceAmount(TaxEnergyBalanceAmountType value) {
        this.taxEnergyBalanceAmount = value;
    }

    /**
     * Recupera il valore della proprietà taxScheme.
     * 
     * @return
     *     possible object is
     *     {@link TaxSchemeType }
     *     
     */
    public TaxSchemeType getTaxScheme() {
        return taxScheme;
    }

    /**
     * Imposta il valore della proprietà taxScheme.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxSchemeType }
     *     
     */
    public void setTaxScheme(TaxSchemeType value) {
        this.taxScheme = value;
    }

}
