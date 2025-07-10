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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CallBaseAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CallDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CallExtensionAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CallTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.MovieTitleType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PayPerViewType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.QuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RoamingPartnerNameType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ServiceNumberCalledType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TelecommunicationsServiceCallCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TelecommunicationsServiceCallType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TelecommunicationsServiceCategoryCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TelecommunicationsServiceCategoryType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per TelecommunicationsServiceType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TelecommunicationsServiceType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CallDate"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CallTime"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ServiceNumberCalled"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TelecommunicationsServiceCategory" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TelecommunicationsServiceCategoryCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}MovieTitle" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RoamingPartnerName" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PayPerView" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Quantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TelecommunicationsServiceCall" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TelecommunicationsServiceCallCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CallBaseAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CallExtensionAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Price" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Country" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}ExchangeRate" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}AllowanceCharge" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TaxTotal" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}CallDuty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}TimeDuty" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TelecommunicationsServiceType", propOrder = {
    "ublExtensions",
    "id",
    "callDate",
    "callTime",
    "serviceNumberCalled",
    "telecommunicationsServiceCategory",
    "telecommunicationsServiceCategoryCode",
    "movieTitle",
    "roamingPartnerName",
    "payPerView",
    "quantity",
    "telecommunicationsServiceCall",
    "telecommunicationsServiceCallCode",
    "callBaseAmount",
    "callExtensionAmount",
    "price",
    "country",
    "exchangeRate",
    "allowanceCharge",
    "taxTotal",
    "callDuty",
    "timeDuty"
})
public class TelecommunicationsServiceType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "CallDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected CallDateType callDate;
    @XmlElement(name = "CallTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected CallTimeType callTime;
    @XmlElement(name = "ServiceNumberCalled", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected ServiceNumberCalledType serviceNumberCalled;
    @XmlElement(name = "TelecommunicationsServiceCategory", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TelecommunicationsServiceCategoryType telecommunicationsServiceCategory;
    @XmlElement(name = "TelecommunicationsServiceCategoryCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TelecommunicationsServiceCategoryCodeType telecommunicationsServiceCategoryCode;
    @XmlElement(name = "MovieTitle", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected MovieTitleType movieTitle;
    @XmlElement(name = "RoamingPartnerName", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RoamingPartnerNameType roamingPartnerName;
    @XmlElement(name = "PayPerView", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PayPerViewType payPerView;
    @XmlElement(name = "Quantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected QuantityType quantity;
    @XmlElement(name = "TelecommunicationsServiceCall", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TelecommunicationsServiceCallType telecommunicationsServiceCall;
    @XmlElement(name = "TelecommunicationsServiceCallCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TelecommunicationsServiceCallCodeType telecommunicationsServiceCallCode;
    @XmlElement(name = "CallBaseAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CallBaseAmountType callBaseAmount;
    @XmlElement(name = "CallExtensionAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CallExtensionAmountType callExtensionAmount;
    @XmlElement(name = "Price")
    protected PriceType price;
    @XmlElement(name = "Country")
    protected CountryType country;
    @XmlElement(name = "ExchangeRate")
    protected List<ExchangeRateType> exchangeRate;
    @XmlElement(name = "AllowanceCharge")
    protected List<AllowanceChargeType> allowanceCharge;
    @XmlElement(name = "TaxTotal")
    protected List<TaxTotalType> taxTotal;
    @XmlElement(name = "CallDuty")
    protected List<DutyType> callDuty;
    @XmlElement(name = "TimeDuty")
    protected List<DutyType> timeDuty;

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
     * Recupera il valore della proprietà id.
     * 
     * @return
     *     possible object is
     *     {@link IDType }
     *     
     */
    public IDType getID() {
        return id;
    }

    /**
     * Imposta il valore della proprietà id.
     * 
     * @param value
     *     allowed object is
     *     {@link IDType }
     *     
     */
    public void setID(IDType value) {
        this.id = value;
    }

    /**
     * Recupera il valore della proprietà callDate.
     * 
     * @return
     *     possible object is
     *     {@link CallDateType }
     *     
     */
    public CallDateType getCallDate() {
        return callDate;
    }

    /**
     * Imposta il valore della proprietà callDate.
     * 
     * @param value
     *     allowed object is
     *     {@link CallDateType }
     *     
     */
    public void setCallDate(CallDateType value) {
        this.callDate = value;
    }

    /**
     * Recupera il valore della proprietà callTime.
     * 
     * @return
     *     possible object is
     *     {@link CallTimeType }
     *     
     */
    public CallTimeType getCallTime() {
        return callTime;
    }

    /**
     * Imposta il valore della proprietà callTime.
     * 
     * @param value
     *     allowed object is
     *     {@link CallTimeType }
     *     
     */
    public void setCallTime(CallTimeType value) {
        this.callTime = value;
    }

    /**
     * Recupera il valore della proprietà serviceNumberCalled.
     * 
     * @return
     *     possible object is
     *     {@link ServiceNumberCalledType }
     *     
     */
    public ServiceNumberCalledType getServiceNumberCalled() {
        return serviceNumberCalled;
    }

    /**
     * Imposta il valore della proprietà serviceNumberCalled.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceNumberCalledType }
     *     
     */
    public void setServiceNumberCalled(ServiceNumberCalledType value) {
        this.serviceNumberCalled = value;
    }

    /**
     * Recupera il valore della proprietà telecommunicationsServiceCategory.
     * 
     * @return
     *     possible object is
     *     {@link TelecommunicationsServiceCategoryType }
     *     
     */
    public TelecommunicationsServiceCategoryType getTelecommunicationsServiceCategory() {
        return telecommunicationsServiceCategory;
    }

    /**
     * Imposta il valore della proprietà telecommunicationsServiceCategory.
     * 
     * @param value
     *     allowed object is
     *     {@link TelecommunicationsServiceCategoryType }
     *     
     */
    public void setTelecommunicationsServiceCategory(TelecommunicationsServiceCategoryType value) {
        this.telecommunicationsServiceCategory = value;
    }

    /**
     * Recupera il valore della proprietà telecommunicationsServiceCategoryCode.
     * 
     * @return
     *     possible object is
     *     {@link TelecommunicationsServiceCategoryCodeType }
     *     
     */
    public TelecommunicationsServiceCategoryCodeType getTelecommunicationsServiceCategoryCode() {
        return telecommunicationsServiceCategoryCode;
    }

    /**
     * Imposta il valore della proprietà telecommunicationsServiceCategoryCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TelecommunicationsServiceCategoryCodeType }
     *     
     */
    public void setTelecommunicationsServiceCategoryCode(TelecommunicationsServiceCategoryCodeType value) {
        this.telecommunicationsServiceCategoryCode = value;
    }

    /**
     * Recupera il valore della proprietà movieTitle.
     * 
     * @return
     *     possible object is
     *     {@link MovieTitleType }
     *     
     */
    public MovieTitleType getMovieTitle() {
        return movieTitle;
    }

    /**
     * Imposta il valore della proprietà movieTitle.
     * 
     * @param value
     *     allowed object is
     *     {@link MovieTitleType }
     *     
     */
    public void setMovieTitle(MovieTitleType value) {
        this.movieTitle = value;
    }

    /**
     * Recupera il valore della proprietà roamingPartnerName.
     * 
     * @return
     *     possible object is
     *     {@link RoamingPartnerNameType }
     *     
     */
    public RoamingPartnerNameType getRoamingPartnerName() {
        return roamingPartnerName;
    }

    /**
     * Imposta il valore della proprietà roamingPartnerName.
     * 
     * @param value
     *     allowed object is
     *     {@link RoamingPartnerNameType }
     *     
     */
    public void setRoamingPartnerName(RoamingPartnerNameType value) {
        this.roamingPartnerName = value;
    }

    /**
     * Recupera il valore della proprietà payPerView.
     * 
     * @return
     *     possible object is
     *     {@link PayPerViewType }
     *     
     */
    public PayPerViewType getPayPerView() {
        return payPerView;
    }

    /**
     * Imposta il valore della proprietà payPerView.
     * 
     * @param value
     *     allowed object is
     *     {@link PayPerViewType }
     *     
     */
    public void setPayPerView(PayPerViewType value) {
        this.payPerView = value;
    }

    /**
     * Recupera il valore della proprietà quantity.
     * 
     * @return
     *     possible object is
     *     {@link QuantityType }
     *     
     */
    public QuantityType getQuantity() {
        return quantity;
    }

    /**
     * Imposta il valore della proprietà quantity.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityType }
     *     
     */
    public void setQuantity(QuantityType value) {
        this.quantity = value;
    }

    /**
     * Recupera il valore della proprietà telecommunicationsServiceCall.
     * 
     * @return
     *     possible object is
     *     {@link TelecommunicationsServiceCallType }
     *     
     */
    public TelecommunicationsServiceCallType getTelecommunicationsServiceCall() {
        return telecommunicationsServiceCall;
    }

    /**
     * Imposta il valore della proprietà telecommunicationsServiceCall.
     * 
     * @param value
     *     allowed object is
     *     {@link TelecommunicationsServiceCallType }
     *     
     */
    public void setTelecommunicationsServiceCall(TelecommunicationsServiceCallType value) {
        this.telecommunicationsServiceCall = value;
    }

    /**
     * Recupera il valore della proprietà telecommunicationsServiceCallCode.
     * 
     * @return
     *     possible object is
     *     {@link TelecommunicationsServiceCallCodeType }
     *     
     */
    public TelecommunicationsServiceCallCodeType getTelecommunicationsServiceCallCode() {
        return telecommunicationsServiceCallCode;
    }

    /**
     * Imposta il valore della proprietà telecommunicationsServiceCallCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TelecommunicationsServiceCallCodeType }
     *     
     */
    public void setTelecommunicationsServiceCallCode(TelecommunicationsServiceCallCodeType value) {
        this.telecommunicationsServiceCallCode = value;
    }

    /**
     * Recupera il valore della proprietà callBaseAmount.
     * 
     * @return
     *     possible object is
     *     {@link CallBaseAmountType }
     *     
     */
    public CallBaseAmountType getCallBaseAmount() {
        return callBaseAmount;
    }

    /**
     * Imposta il valore della proprietà callBaseAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link CallBaseAmountType }
     *     
     */
    public void setCallBaseAmount(CallBaseAmountType value) {
        this.callBaseAmount = value;
    }

    /**
     * Recupera il valore della proprietà callExtensionAmount.
     * 
     * @return
     *     possible object is
     *     {@link CallExtensionAmountType }
     *     
     */
    public CallExtensionAmountType getCallExtensionAmount() {
        return callExtensionAmount;
    }

    /**
     * Imposta il valore della proprietà callExtensionAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link CallExtensionAmountType }
     *     
     */
    public void setCallExtensionAmount(CallExtensionAmountType value) {
        this.callExtensionAmount = value;
    }

    /**
     * Recupera il valore della proprietà price.
     * 
     * @return
     *     possible object is
     *     {@link PriceType }
     *     
     */
    public PriceType getPrice() {
        return price;
    }

    /**
     * Imposta il valore della proprietà price.
     * 
     * @param value
     *     allowed object is
     *     {@link PriceType }
     *     
     */
    public void setPrice(PriceType value) {
        this.price = value;
    }

    /**
     * Recupera il valore della proprietà country.
     * 
     * @return
     *     possible object is
     *     {@link CountryType }
     *     
     */
    public CountryType getCountry() {
        return country;
    }

    /**
     * Imposta il valore della proprietà country.
     * 
     * @param value
     *     allowed object is
     *     {@link CountryType }
     *     
     */
    public void setCountry(CountryType value) {
        this.country = value;
    }

    /**
     * Gets the value of the exchangeRate property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the exchangeRate property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getExchangeRate().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ExchangeRateType }
     * 
     * 
     */
    public List<ExchangeRateType> getExchangeRate() {
        if (exchangeRate == null) {
            exchangeRate = new ArrayList<ExchangeRateType>();
        }
        return this.exchangeRate;
    }

    /**
     * Gets the value of the allowanceCharge property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the allowanceCharge property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getAllowanceCharge().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link AllowanceChargeType }
     * 
     * 
     */
    public List<AllowanceChargeType> getAllowanceCharge() {
        if (allowanceCharge == null) {
            allowanceCharge = new ArrayList<AllowanceChargeType>();
        }
        return this.allowanceCharge;
    }

    /**
     * Gets the value of the taxTotal property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the taxTotal property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTaxTotal().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link TaxTotalType }
     * 
     * 
     */
    public List<TaxTotalType> getTaxTotal() {
        if (taxTotal == null) {
            taxTotal = new ArrayList<TaxTotalType>();
        }
        return this.taxTotal;
    }

    /**
     * Gets the value of the callDuty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the callDuty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getCallDuty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DutyType }
     * 
     * 
     */
    public List<DutyType> getCallDuty() {
        if (callDuty == null) {
            callDuty = new ArrayList<DutyType>();
        }
        return this.callDuty;
    }

    /**
     * Gets the value of the timeDuty property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the timeDuty property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getTimeDuty().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DutyType }
     * 
     * 
     */
    public List<DutyType> getTimeDuty() {
        if (timeDuty == null) {
            timeDuty = new ArrayList<DutyType>();
        }
        return this.timeDuty;
    }

}
