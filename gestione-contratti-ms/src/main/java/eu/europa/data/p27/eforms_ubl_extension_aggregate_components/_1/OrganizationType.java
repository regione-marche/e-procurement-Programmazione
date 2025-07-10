//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package eu.europa.data.p27.eforms_ubl_extension_aggregate_components._1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AcquiringCPBIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.AwardingCPBIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.GroupLeadIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.ListedOnRegulatedMarketIndicatorType;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.NaturalPersonIndicatorType;


/**
 * &lt;p&gt;Classe Java per OrganizationType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="OrganizationType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}GroupLeadIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}AcquiringCPBIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}AwardingCPBIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}ListedOnRegulatedMarketIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1}NaturalPersonIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}UltimateBeneficialOwner" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}Company" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{http://data.europa.eu/p27/eforms-ubl-extension-aggregate-components/1}TouchPoint" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganizationType", propOrder = {
    "groupLeadIndicator",
    "acquiringCPBIndicator",
    "awardingCPBIndicator",
    "listedOnRegulatedMarketIndicator",
    "naturalPersonIndicator",
    "ultimateBeneficialOwner",
    "company",
    "touchPoint"
})
public class OrganizationType {

    @XmlElement(name = "GroupLeadIndicator", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected GroupLeadIndicatorType groupLeadIndicator;
    @XmlElement(name = "AcquiringCPBIndicator", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected AcquiringCPBIndicatorType acquiringCPBIndicator;
    @XmlElement(name = "AwardingCPBIndicator", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected AwardingCPBIndicatorType awardingCPBIndicator;
    @XmlElement(name = "ListedOnRegulatedMarketIndicator", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected ListedOnRegulatedMarketIndicatorType listedOnRegulatedMarketIndicator;
    @XmlElement(name = "NaturalPersonIndicator", namespace = "http://data.europa.eu/p27/eforms-ubl-extension-basic-components/1")
    protected NaturalPersonIndicatorType naturalPersonIndicator;
    @XmlElement(name = "UltimateBeneficialOwner")
    protected List<UltimateBeneficialOwnerType> ultimateBeneficialOwner;
    @XmlElement(name = "Company")
    protected CompanyType company;
    @XmlElement(name = "TouchPoint")
    protected List<TouchPointType> touchPoint;

    /**
     * Recupera il valore della proprietà groupLeadIndicator.
     * 
     * @return
     *     possible object is
     *     {@link GroupLeadIndicatorType }
     *     
     */
    public GroupLeadIndicatorType getGroupLeadIndicator() {
        return groupLeadIndicator;
    }

    /**
     * Imposta il valore della proprietà groupLeadIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupLeadIndicatorType }
     *     
     */
    public void setGroupLeadIndicator(GroupLeadIndicatorType value) {
        this.groupLeadIndicator = value;
    }

    /**
     * Recupera il valore della proprietà acquiringCPBIndicator.
     * 
     * @return
     *     possible object is
     *     {@link AcquiringCPBIndicatorType }
     *     
     */
    public AcquiringCPBIndicatorType getAcquiringCPBIndicator() {
        return acquiringCPBIndicator;
    }

    /**
     * Imposta il valore della proprietà acquiringCPBIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link AcquiringCPBIndicatorType }
     *     
     */
    public void setAcquiringCPBIndicator(AcquiringCPBIndicatorType value) {
        this.acquiringCPBIndicator = value;
    }

    /**
     * Recupera il valore della proprietà awardingCPBIndicator.
     * 
     * @return
     *     possible object is
     *     {@link AwardingCPBIndicatorType }
     *     
     */
    public AwardingCPBIndicatorType getAwardingCPBIndicator() {
        return awardingCPBIndicator;
    }

    /**
     * Imposta il valore della proprietà awardingCPBIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link AwardingCPBIndicatorType }
     *     
     */
    public void setAwardingCPBIndicator(AwardingCPBIndicatorType value) {
        this.awardingCPBIndicator = value;
    }

    /**
     * Recupera il valore della proprietà listedOnRegulatedMarketIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ListedOnRegulatedMarketIndicatorType }
     *     
     */
    public ListedOnRegulatedMarketIndicatorType getListedOnRegulatedMarketIndicator() {
        return listedOnRegulatedMarketIndicator;
    }

    /**
     * Imposta il valore della proprietà listedOnRegulatedMarketIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ListedOnRegulatedMarketIndicatorType }
     *     
     */
    public void setListedOnRegulatedMarketIndicator(ListedOnRegulatedMarketIndicatorType value) {
        this.listedOnRegulatedMarketIndicator = value;
    }

    /**
     * Recupera il valore della proprietà naturalPersonIndicator.
     * 
     * @return
     *     possible object is
     *     {@link NaturalPersonIndicatorType }
     *     
     */
    public NaturalPersonIndicatorType getNaturalPersonIndicator() {
        return naturalPersonIndicator;
    }

    /**
     * Imposta il valore della proprietà naturalPersonIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link NaturalPersonIndicatorType }
     *     
     */
    public void setNaturalPersonIndicator(NaturalPersonIndicatorType value) {
        this.naturalPersonIndicator = value;
    }

    /**
     * Gets the value of the ultimateBeneficialOwner property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the ultimateBeneficialOwner property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getUltimateBeneficialOwner().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link UltimateBeneficialOwnerType }
     * 
     * 
     */
    public List<UltimateBeneficialOwnerType> getUltimateBeneficialOwner() {
        if (ultimateBeneficialOwner == null) {
            ultimateBeneficialOwner = new ArrayList<UltimateBeneficialOwnerType>();
        }
        return this.ultimateBeneficialOwner;
    }

    /**
     * Recupera il valore della proprietà company.
     * 
     * @return
     *     possible object is
     *     {@link CompanyType }
     *     
     */
    public CompanyType getCompany() {
        return company;
    }

    /**
     * Imposta il valore della proprietà company.
     * 
     * @param value
     *     allowed object is
     *     {@link CompanyType }
     *     
     */
    public void setCompany(CompanyType value) {
        this.company = value;
    }

    /**
     * Gets the value of the touchPoint property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the touchPoint property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTouchPoint().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TouchPointType }
     * 
     * 
     */
    public List<TouchPointType> getTouchPoint() {
        if (touchPoint == null) {
            touchPoint = new ArrayList<TouchPointType>();
        }
        return this.touchPoint;
    }

}
