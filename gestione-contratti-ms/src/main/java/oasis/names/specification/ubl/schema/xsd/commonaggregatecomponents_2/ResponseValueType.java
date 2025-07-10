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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.DescriptionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseBinaryObjectType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseNumericType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseURIType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ResponseValueType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ResponseValueType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Description" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Response" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ResponseAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ResponseBinaryObject" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ResponseCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ResponseDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ResponseID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ResponseIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ResponseMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ResponseNumeric" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ResponseQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ResponseTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ResponseURI" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseValueType", propOrder = {
    "ublExtensions",
    "id",
    "description",
    "response",
    "responseAmount",
    "responseBinaryObject",
    "responseCode",
    "responseDate",
    "responseID",
    "responseIndicator",
    "responseMeasure",
    "responseNumeric",
    "responseQuantity",
    "responseTime",
    "responseURI"
})
public class ResponseValueType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "Description", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<DescriptionType> description;
    @XmlElement(name = "Response", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<ResponseType> response;
    @XmlElement(name = "ResponseAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ResponseAmountType responseAmount;
    @XmlElement(name = "ResponseBinaryObject", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ResponseBinaryObjectType responseBinaryObject;
    @XmlElement(name = "ResponseCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ResponseCodeType responseCode;
    @XmlElement(name = "ResponseDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ResponseDateType responseDate;
    @XmlElement(name = "ResponseID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ResponseIDType responseID;
    @XmlElement(name = "ResponseIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ResponseIndicatorType responseIndicator;
    @XmlElement(name = "ResponseMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ResponseMeasureType responseMeasure;
    @XmlElement(name = "ResponseNumeric", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ResponseNumericType responseNumeric;
    @XmlElement(name = "ResponseQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ResponseQuantityType responseQuantity;
    @XmlElement(name = "ResponseTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ResponseTimeType responseTime;
    @XmlElement(name = "ResponseURI", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ResponseURIType responseURI;

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
     * Gets the value of the description property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the description property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDescription().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DescriptionType }
     * 
     * 
     */
    public List<DescriptionType> getDescription() {
        if (description == null) {
            description = new ArrayList<DescriptionType>();
        }
        return this.description;
    }

    /**
     * Gets the value of the response property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the response property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getResponse().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ResponseType }
     * 
     * 
     */
    public List<ResponseType> getResponse() {
        if (response == null) {
            response = new ArrayList<ResponseType>();
        }
        return this.response;
    }

    /**
     * Recupera il valore della proprietà responseAmount.
     * 
     * @return
     *     possible object is
     *     {@link ResponseAmountType }
     *     
     */
    public ResponseAmountType getResponseAmount() {
        return responseAmount;
    }

    /**
     * Imposta il valore della proprietà responseAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseAmountType }
     *     
     */
    public void setResponseAmount(ResponseAmountType value) {
        this.responseAmount = value;
    }

    /**
     * Recupera il valore della proprietà responseBinaryObject.
     * 
     * @return
     *     possible object is
     *     {@link ResponseBinaryObjectType }
     *     
     */
    public ResponseBinaryObjectType getResponseBinaryObject() {
        return responseBinaryObject;
    }

    /**
     * Imposta il valore della proprietà responseBinaryObject.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseBinaryObjectType }
     *     
     */
    public void setResponseBinaryObject(ResponseBinaryObjectType value) {
        this.responseBinaryObject = value;
    }

    /**
     * Recupera il valore della proprietà responseCode.
     * 
     * @return
     *     possible object is
     *     {@link ResponseCodeType }
     *     
     */
    public ResponseCodeType getResponseCode() {
        return responseCode;
    }

    /**
     * Imposta il valore della proprietà responseCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseCodeType }
     *     
     */
    public void setResponseCode(ResponseCodeType value) {
        this.responseCode = value;
    }

    /**
     * Recupera il valore della proprietà responseDate.
     * 
     * @return
     *     possible object is
     *     {@link ResponseDateType }
     *     
     */
    public ResponseDateType getResponseDate() {
        return responseDate;
    }

    /**
     * Imposta il valore della proprietà responseDate.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseDateType }
     *     
     */
    public void setResponseDate(ResponseDateType value) {
        this.responseDate = value;
    }

    /**
     * Recupera il valore della proprietà responseID.
     * 
     * @return
     *     possible object is
     *     {@link ResponseIDType }
     *     
     */
    public ResponseIDType getResponseID() {
        return responseID;
    }

    /**
     * Imposta il valore della proprietà responseID.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseIDType }
     *     
     */
    public void setResponseID(ResponseIDType value) {
        this.responseID = value;
    }

    /**
     * Recupera il valore della proprietà responseIndicator.
     * 
     * @return
     *     possible object is
     *     {@link ResponseIndicatorType }
     *     
     */
    public ResponseIndicatorType getResponseIndicator() {
        return responseIndicator;
    }

    /**
     * Imposta il valore della proprietà responseIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseIndicatorType }
     *     
     */
    public void setResponseIndicator(ResponseIndicatorType value) {
        this.responseIndicator = value;
    }

    /**
     * Recupera il valore della proprietà responseMeasure.
     * 
     * @return
     *     possible object is
     *     {@link ResponseMeasureType }
     *     
     */
    public ResponseMeasureType getResponseMeasure() {
        return responseMeasure;
    }

    /**
     * Imposta il valore della proprietà responseMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseMeasureType }
     *     
     */
    public void setResponseMeasure(ResponseMeasureType value) {
        this.responseMeasure = value;
    }

    /**
     * Recupera il valore della proprietà responseNumeric.
     * 
     * @return
     *     possible object is
     *     {@link ResponseNumericType }
     *     
     */
    public ResponseNumericType getResponseNumeric() {
        return responseNumeric;
    }

    /**
     * Imposta il valore della proprietà responseNumeric.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseNumericType }
     *     
     */
    public void setResponseNumeric(ResponseNumericType value) {
        this.responseNumeric = value;
    }

    /**
     * Recupera il valore della proprietà responseQuantity.
     * 
     * @return
     *     possible object is
     *     {@link ResponseQuantityType }
     *     
     */
    public ResponseQuantityType getResponseQuantity() {
        return responseQuantity;
    }

    /**
     * Imposta il valore della proprietà responseQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseQuantityType }
     *     
     */
    public void setResponseQuantity(ResponseQuantityType value) {
        this.responseQuantity = value;
    }

    /**
     * Recupera il valore della proprietà responseTime.
     * 
     * @return
     *     possible object is
     *     {@link ResponseTimeType }
     *     
     */
    public ResponseTimeType getResponseTime() {
        return responseTime;
    }

    /**
     * Imposta il valore della proprietà responseTime.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseTimeType }
     *     
     */
    public void setResponseTime(ResponseTimeType value) {
        this.responseTime = value;
    }

    /**
     * Recupera il valore della proprietà responseURI.
     * 
     * @return
     *     possible object is
     *     {@link ResponseURIType }
     *     
     */
    public ResponseURIType getResponseURI() {
        return responseURI;
    }

    /**
     * Imposta il valore della proprietà responseURI.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseURIType }
     *     
     */
    public void setResponseURI(ResponseURIType value) {
        this.responseURI = value;
    }

}
