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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.InstructionIDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaidAmountType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaidDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaidTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReceivedDateType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per PaymentType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="PaymentType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PaidAmount" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReceivedDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PaidDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}PaidTime" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}InstructionID" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentType", propOrder = {
    "ublExtensions",
    "id",
    "paidAmount",
    "receivedDate",
    "paidDate",
    "paidTime",
    "instructionID"
})
public class PaymentType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected IDType id;
    @XmlElement(name = "PaidAmount", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PaidAmountType paidAmount;
    @XmlElement(name = "ReceivedDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ReceivedDateType receivedDate;
    @XmlElement(name = "PaidDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PaidDateType paidDate;
    @XmlElement(name = "PaidTime", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected PaidTimeType paidTime;
    @XmlElement(name = "InstructionID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected InstructionIDType instructionID;

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
     * Recupera il valore della proprietà paidAmount.
     * 
     * @return
     *     possible object is
     *     {@link PaidAmountType }
     *     
     */
    public PaidAmountType getPaidAmount() {
        return paidAmount;
    }

    /**
     * Imposta il valore della proprietà paidAmount.
     * 
     * @param value
     *     allowed object is
     *     {@link PaidAmountType }
     *     
     */
    public void setPaidAmount(PaidAmountType value) {
        this.paidAmount = value;
    }

    /**
     * Recupera il valore della proprietà receivedDate.
     * 
     * @return
     *     possible object is
     *     {@link ReceivedDateType }
     *     
     */
    public ReceivedDateType getReceivedDate() {
        return receivedDate;
    }

    /**
     * Imposta il valore della proprietà receivedDate.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivedDateType }
     *     
     */
    public void setReceivedDate(ReceivedDateType value) {
        this.receivedDate = value;
    }

    /**
     * Recupera il valore della proprietà paidDate.
     * 
     * @return
     *     possible object is
     *     {@link PaidDateType }
     *     
     */
    public PaidDateType getPaidDate() {
        return paidDate;
    }

    /**
     * Imposta il valore della proprietà paidDate.
     * 
     * @param value
     *     allowed object is
     *     {@link PaidDateType }
     *     
     */
    public void setPaidDate(PaidDateType value) {
        this.paidDate = value;
    }

    /**
     * Recupera il valore della proprietà paidTime.
     * 
     * @return
     *     possible object is
     *     {@link PaidTimeType }
     *     
     */
    public PaidTimeType getPaidTime() {
        return paidTime;
    }

    /**
     * Imposta il valore della proprietà paidTime.
     * 
     * @param value
     *     allowed object is
     *     {@link PaidTimeType }
     *     
     */
    public void setPaidTime(PaidTimeType value) {
        this.paidTime = value;
    }

    /**
     * Recupera il valore della proprietà instructionID.
     * 
     * @return
     *     possible object is
     *     {@link InstructionIDType }
     *     
     */
    public InstructionIDType getInstructionID() {
        return instructionID;
    }

    /**
     * Imposta il valore della proprietà instructionID.
     * 
     * @param value
     *     allowed object is
     *     {@link InstructionIDType }
     *     
     */
    public void setInstructionID(InstructionIDType value) {
        this.instructionID = value;
    }

}
