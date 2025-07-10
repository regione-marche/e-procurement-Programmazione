//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package oasis.names.specification.ubl.schema.xsd.commonaggregatecomponents_2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BusinessClassificationEvidenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.BusinessIdentityEvidenceIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EmployeeQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OperatingYearsQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ParticipationPercentType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PersonalSituationType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TendererRoleCodeType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per QualifyingPartyType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="QualifyingPartyType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ParticipationPercent" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PersonalSituation" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}OperatingYearsQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}EmployeeQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}BusinessClassificationEvidenceID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}BusinessIdentityEvidenceID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TendererRoleCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}BusinessClassificationScheme" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TechnicalCapability" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}FinancialCapability" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CompletedTask" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Declaration" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Party" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}EconomicOperatorRole" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QualifyingPartyType", propOrder = {
    "ublExtensions",
    "participationPercent",
    "personalSituation",
    "operatingYearsQuantity",
    "employeeQuantity",
    "businessClassificationEvidenceID",
    "businessIdentityEvidenceID",
    "tendererRoleCode",
    "businessClassificationScheme",
    "technicalCapability",
    "financialCapability",
    "completedTask",
    "declaration",
    "party",
    "economicOperatorRole"
})
public class QualifyingPartyType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ParticipationPercent", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ParticipationPercentType participationPercent;
    @XmlElement(name = "PersonalSituation", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<PersonalSituationType> personalSituation;
    @XmlElement(name = "OperatingYearsQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected OperatingYearsQuantityType operatingYearsQuantity;
    @XmlElement(name = "EmployeeQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected EmployeeQuantityType employeeQuantity;
    @XmlElement(name = "BusinessClassificationEvidenceID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected BusinessClassificationEvidenceIDType businessClassificationEvidenceID;
    @XmlElement(name = "BusinessIdentityEvidenceID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected BusinessIdentityEvidenceIDType businessIdentityEvidenceID;
    @XmlElement(name = "TendererRoleCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TendererRoleCodeType tendererRoleCode;
    @XmlElement(name = "BusinessClassificationScheme")
    protected ClassificationSchemeType businessClassificationScheme;
    @XmlElement(name = "TechnicalCapability")
    protected List<CapabilityType> technicalCapability;
    @XmlElement(name = "FinancialCapability")
    protected List<CapabilityType> financialCapability;
    @XmlElement(name = "CompletedTask")
    protected List<CompletedTaskType> completedTask;
    @XmlElement(name = "Declaration")
    protected List<DeclarationType> declaration;
    @XmlElement(name = "Party")
    protected PartyType party;
    @XmlElement(name = "EconomicOperatorRole")
    protected EconomicOperatorRoleType economicOperatorRole;

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
     * Recupera il valore della proprietà participationPercent.
     * 
     * @return
     *     possible object is
     *     {@link ParticipationPercentType }
     *     
     */
    public ParticipationPercentType getParticipationPercent() {
        return participationPercent;
    }

    /**
     * Imposta il valore della proprietà participationPercent.
     * 
     * @param value
     *     allowed object is
     *     {@link ParticipationPercentType }
     *     
     */
    public void setParticipationPercent(ParticipationPercentType value) {
        this.participationPercent = value;
    }

    /**
     * Gets the value of the personalSituation property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the personalSituation property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getPersonalSituation().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link PersonalSituationType }
     * 
     * 
     */
    public List<PersonalSituationType> getPersonalSituation() {
        if (personalSituation == null) {
            personalSituation = new ArrayList<PersonalSituationType>();
        }
        return this.personalSituation;
    }

    /**
     * Recupera il valore della proprietà operatingYearsQuantity.
     * 
     * @return
     *     possible object is
     *     {@link OperatingYearsQuantityType }
     *     
     */
    public OperatingYearsQuantityType getOperatingYearsQuantity() {
        return operatingYearsQuantity;
    }

    /**
     * Imposta il valore della proprietà operatingYearsQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link OperatingYearsQuantityType }
     *     
     */
    public void setOperatingYearsQuantity(OperatingYearsQuantityType value) {
        this.operatingYearsQuantity = value;
    }

    /**
     * Recupera il valore della proprietà employeeQuantity.
     * 
     * @return
     *     possible object is
     *     {@link EmployeeQuantityType }
     *     
     */
    public EmployeeQuantityType getEmployeeQuantity() {
        return employeeQuantity;
    }

    /**
     * Imposta il valore della proprietà employeeQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeeQuantityType }
     *     
     */
    public void setEmployeeQuantity(EmployeeQuantityType value) {
        this.employeeQuantity = value;
    }

    /**
     * Recupera il valore della proprietà businessClassificationEvidenceID.
     * 
     * @return
     *     possible object is
     *     {@link BusinessClassificationEvidenceIDType }
     *     
     */
    public BusinessClassificationEvidenceIDType getBusinessClassificationEvidenceID() {
        return businessClassificationEvidenceID;
    }

    /**
     * Imposta il valore della proprietà businessClassificationEvidenceID.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessClassificationEvidenceIDType }
     *     
     */
    public void setBusinessClassificationEvidenceID(BusinessClassificationEvidenceIDType value) {
        this.businessClassificationEvidenceID = value;
    }

    /**
     * Recupera il valore della proprietà businessIdentityEvidenceID.
     * 
     * @return
     *     possible object is
     *     {@link BusinessIdentityEvidenceIDType }
     *     
     */
    public BusinessIdentityEvidenceIDType getBusinessIdentityEvidenceID() {
        return businessIdentityEvidenceID;
    }

    /**
     * Imposta il valore della proprietà businessIdentityEvidenceID.
     * 
     * @param value
     *     allowed object is
     *     {@link BusinessIdentityEvidenceIDType }
     *     
     */
    public void setBusinessIdentityEvidenceID(BusinessIdentityEvidenceIDType value) {
        this.businessIdentityEvidenceID = value;
    }

    /**
     * Recupera il valore della proprietà tendererRoleCode.
     * 
     * @return
     *     possible object is
     *     {@link TendererRoleCodeType }
     *     
     */
    public TendererRoleCodeType getTendererRoleCode() {
        return tendererRoleCode;
    }

    /**
     * Imposta il valore della proprietà tendererRoleCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TendererRoleCodeType }
     *     
     */
    public void setTendererRoleCode(TendererRoleCodeType value) {
        this.tendererRoleCode = value;
    }

    /**
     * Recupera il valore della proprietà businessClassificationScheme.
     * 
     * @return
     *     possible object is
     *     {@link ClassificationSchemeType }
     *     
     */
    public ClassificationSchemeType getBusinessClassificationScheme() {
        return businessClassificationScheme;
    }

    /**
     * Imposta il valore della proprietà businessClassificationScheme.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassificationSchemeType }
     *     
     */
    public void setBusinessClassificationScheme(ClassificationSchemeType value) {
        this.businessClassificationScheme = value;
    }

    /**
     * Gets the value of the technicalCapability property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the technicalCapability property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTechnicalCapability().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CapabilityType }
     * 
     * 
     */
    public List<CapabilityType> getTechnicalCapability() {
        if (technicalCapability == null) {
            technicalCapability = new ArrayList<CapabilityType>();
        }
        return this.technicalCapability;
    }

    /**
     * Gets the value of the financialCapability property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the financialCapability property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getFinancialCapability().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CapabilityType }
     * 
     * 
     */
    public List<CapabilityType> getFinancialCapability() {
        if (financialCapability == null) {
            financialCapability = new ArrayList<CapabilityType>();
        }
        return this.financialCapability;
    }

    /**
     * Gets the value of the completedTask property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the completedTask property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCompletedTask().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link CompletedTaskType }
     * 
     * 
     */
    public List<CompletedTaskType> getCompletedTask() {
        if (completedTask == null) {
            completedTask = new ArrayList<CompletedTaskType>();
        }
        return this.completedTask;
    }

    /**
     * Gets the value of the declaration property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the declaration property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDeclaration().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DeclarationType }
     * 
     * 
     */
    public List<DeclarationType> getDeclaration() {
        if (declaration == null) {
            declaration = new ArrayList<DeclarationType>();
        }
        return this.declaration;
    }

    /**
     * Recupera il valore della proprietà party.
     * 
     * @return
     *     possible object is
     *     {@link PartyType }
     *     
     */
    public PartyType getParty() {
        return party;
    }

    /**
     * Imposta il valore della proprietà party.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyType }
     *     
     */
    public void setParty(PartyType value) {
        this.party = value;
    }

    /**
     * Recupera il valore della proprietà economicOperatorRole.
     * 
     * @return
     *     possible object is
     *     {@link EconomicOperatorRoleType }
     *     
     */
    public EconomicOperatorRoleType getEconomicOperatorRole() {
        return economicOperatorRole;
    }

    /**
     * Imposta il valore della proprietà economicOperatorRole.
     * 
     * @param value
     *     allowed object is
     *     {@link EconomicOperatorRoleType }
     *     
     */
    public void setEconomicOperatorRole(EconomicOperatorRoleType value) {
        this.economicOperatorRole = value;
    }

}
