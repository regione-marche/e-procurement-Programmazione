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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IDType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NoteType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OversupplyQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.QuantityDiscrepancyCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReceivedDateType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReceivedQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RejectActionCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RejectReasonCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RejectReasonType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RejectedQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ShortQuantityType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ShortageActionCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TimingComplaintCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.TimingComplaintType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.UUIDType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per ReceiptLineType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ReceiptLineType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ID"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}UUID" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}Note" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReceivedQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ShortQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ShortageActionCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RejectedQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RejectReasonCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RejectReason" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RejectActionCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}QuantityDiscrepancyCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}OversupplyQuantity" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}ReceivedDate" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TimingComplaintCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}TimingComplaint" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}OrderLineReference" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DespatchLineReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}DocumentReference" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Item" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonAggregateComponents-2}Shipment" maxOccurs="unbounded" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReceiptLineType", propOrder = {
    "ublExtensions",
    "id",
    "uuid",
    "note",
    "receivedQuantity",
    "shortQuantity",
    "shortageActionCode",
    "rejectedQuantity",
    "rejectReasonCode",
    "rejectReason",
    "rejectActionCode",
    "quantityDiscrepancyCode",
    "oversupplyQuantity",
    "receivedDate",
    "timingComplaintCode",
    "timingComplaint",
    "orderLineReference",
    "despatchLineReference",
    "documentReference",
    "item",
    "shipment"
})
public class ReceiptLineType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "ID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2", required = true)
    protected IDType id;
    @XmlElement(name = "UUID", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected UUIDType uuid;
    @XmlElement(name = "Note", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<NoteType> note;
    @XmlElement(name = "ReceivedQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ReceivedQuantityType receivedQuantity;
    @XmlElement(name = "ShortQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ShortQuantityType shortQuantity;
    @XmlElement(name = "ShortageActionCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ShortageActionCodeType shortageActionCode;
    @XmlElement(name = "RejectedQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RejectedQuantityType rejectedQuantity;
    @XmlElement(name = "RejectReasonCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RejectReasonCodeType rejectReasonCode;
    @XmlElement(name = "RejectReason", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected List<RejectReasonType> rejectReason;
    @XmlElement(name = "RejectActionCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RejectActionCodeType rejectActionCode;
    @XmlElement(name = "QuantityDiscrepancyCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected QuantityDiscrepancyCodeType quantityDiscrepancyCode;
    @XmlElement(name = "OversupplyQuantity", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected OversupplyQuantityType oversupplyQuantity;
    @XmlElement(name = "ReceivedDate", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected ReceivedDateType receivedDate;
    @XmlElement(name = "TimingComplaintCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TimingComplaintCodeType timingComplaintCode;
    @XmlElement(name = "TimingComplaint", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected TimingComplaintType timingComplaint;
    @XmlElement(name = "OrderLineReference")
    protected OrderLineReferenceType orderLineReference;
    @XmlElement(name = "DespatchLineReference")
    protected List<LineReferenceType> despatchLineReference;
    @XmlElement(name = "DocumentReference")
    protected List<DocumentReferenceType> documentReference;
    @XmlElement(name = "Item")
    protected List<ItemType> item;
    @XmlElement(name = "Shipment")
    protected List<ShipmentType> shipment;

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
     * Recupera il valore della proprietà uuid.
     * 
     * @return
     *     possible object is
     *     {@link UUIDType }
     *     
     */
    public UUIDType getUUID() {
        return uuid;
    }

    /**
     * Imposta il valore della proprietà uuid.
     * 
     * @param value
     *     allowed object is
     *     {@link UUIDType }
     *     
     */
    public void setUUID(UUIDType value) {
        this.uuid = value;
    }

    /**
     * Gets the value of the note property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the note property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getNote().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link NoteType }
     * 
     * 
     */
    public List<NoteType> getNote() {
        if (note == null) {
            note = new ArrayList<NoteType>();
        }
        return this.note;
    }

    /**
     * Recupera il valore della proprietà receivedQuantity.
     * 
     * @return
     *     possible object is
     *     {@link ReceivedQuantityType }
     *     
     */
    public ReceivedQuantityType getReceivedQuantity() {
        return receivedQuantity;
    }

    /**
     * Imposta il valore della proprietà receivedQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link ReceivedQuantityType }
     *     
     */
    public void setReceivedQuantity(ReceivedQuantityType value) {
        this.receivedQuantity = value;
    }

    /**
     * Recupera il valore della proprietà shortQuantity.
     * 
     * @return
     *     possible object is
     *     {@link ShortQuantityType }
     *     
     */
    public ShortQuantityType getShortQuantity() {
        return shortQuantity;
    }

    /**
     * Imposta il valore della proprietà shortQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link ShortQuantityType }
     *     
     */
    public void setShortQuantity(ShortQuantityType value) {
        this.shortQuantity = value;
    }

    /**
     * Recupera il valore della proprietà shortageActionCode.
     * 
     * @return
     *     possible object is
     *     {@link ShortageActionCodeType }
     *     
     */
    public ShortageActionCodeType getShortageActionCode() {
        return shortageActionCode;
    }

    /**
     * Imposta il valore della proprietà shortageActionCode.
     * 
     * @param value
     *     allowed object is
     *     {@link ShortageActionCodeType }
     *     
     */
    public void setShortageActionCode(ShortageActionCodeType value) {
        this.shortageActionCode = value;
    }

    /**
     * Recupera il valore della proprietà rejectedQuantity.
     * 
     * @return
     *     possible object is
     *     {@link RejectedQuantityType }
     *     
     */
    public RejectedQuantityType getRejectedQuantity() {
        return rejectedQuantity;
    }

    /**
     * Imposta il valore della proprietà rejectedQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link RejectedQuantityType }
     *     
     */
    public void setRejectedQuantity(RejectedQuantityType value) {
        this.rejectedQuantity = value;
    }

    /**
     * Recupera il valore della proprietà rejectReasonCode.
     * 
     * @return
     *     possible object is
     *     {@link RejectReasonCodeType }
     *     
     */
    public RejectReasonCodeType getRejectReasonCode() {
        return rejectReasonCode;
    }

    /**
     * Imposta il valore della proprietà rejectReasonCode.
     * 
     * @param value
     *     allowed object is
     *     {@link RejectReasonCodeType }
     *     
     */
    public void setRejectReasonCode(RejectReasonCodeType value) {
        this.rejectReasonCode = value;
    }

    /**
     * Gets the value of the rejectReason property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the rejectReason property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getRejectReason().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link RejectReasonType }
     * 
     * 
     */
    public List<RejectReasonType> getRejectReason() {
        if (rejectReason == null) {
            rejectReason = new ArrayList<RejectReasonType>();
        }
        return this.rejectReason;
    }

    /**
     * Recupera il valore della proprietà rejectActionCode.
     * 
     * @return
     *     possible object is
     *     {@link RejectActionCodeType }
     *     
     */
    public RejectActionCodeType getRejectActionCode() {
        return rejectActionCode;
    }

    /**
     * Imposta il valore della proprietà rejectActionCode.
     * 
     * @param value
     *     allowed object is
     *     {@link RejectActionCodeType }
     *     
     */
    public void setRejectActionCode(RejectActionCodeType value) {
        this.rejectActionCode = value;
    }

    /**
     * Recupera il valore della proprietà quantityDiscrepancyCode.
     * 
     * @return
     *     possible object is
     *     {@link QuantityDiscrepancyCodeType }
     *     
     */
    public QuantityDiscrepancyCodeType getQuantityDiscrepancyCode() {
        return quantityDiscrepancyCode;
    }

    /**
     * Imposta il valore della proprietà quantityDiscrepancyCode.
     * 
     * @param value
     *     allowed object is
     *     {@link QuantityDiscrepancyCodeType }
     *     
     */
    public void setQuantityDiscrepancyCode(QuantityDiscrepancyCodeType value) {
        this.quantityDiscrepancyCode = value;
    }

    /**
     * Recupera il valore della proprietà oversupplyQuantity.
     * 
     * @return
     *     possible object is
     *     {@link OversupplyQuantityType }
     *     
     */
    public OversupplyQuantityType getOversupplyQuantity() {
        return oversupplyQuantity;
    }

    /**
     * Imposta il valore della proprietà oversupplyQuantity.
     * 
     * @param value
     *     allowed object is
     *     {@link OversupplyQuantityType }
     *     
     */
    public void setOversupplyQuantity(OversupplyQuantityType value) {
        this.oversupplyQuantity = value;
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
     * Recupera il valore della proprietà timingComplaintCode.
     * 
     * @return
     *     possible object is
     *     {@link TimingComplaintCodeType }
     *     
     */
    public TimingComplaintCodeType getTimingComplaintCode() {
        return timingComplaintCode;
    }

    /**
     * Imposta il valore della proprietà timingComplaintCode.
     * 
     * @param value
     *     allowed object is
     *     {@link TimingComplaintCodeType }
     *     
     */
    public void setTimingComplaintCode(TimingComplaintCodeType value) {
        this.timingComplaintCode = value;
    }

    /**
     * Recupera il valore della proprietà timingComplaint.
     * 
     * @return
     *     possible object is
     *     {@link TimingComplaintType }
     *     
     */
    public TimingComplaintType getTimingComplaint() {
        return timingComplaint;
    }

    /**
     * Imposta il valore della proprietà timingComplaint.
     * 
     * @param value
     *     allowed object is
     *     {@link TimingComplaintType }
     *     
     */
    public void setTimingComplaint(TimingComplaintType value) {
        this.timingComplaint = value;
    }

    /**
     * Recupera il valore della proprietà orderLineReference.
     * 
     * @return
     *     possible object is
     *     {@link OrderLineReferenceType }
     *     
     */
    public OrderLineReferenceType getOrderLineReference() {
        return orderLineReference;
    }

    /**
     * Imposta il valore della proprietà orderLineReference.
     * 
     * @param value
     *     allowed object is
     *     {@link OrderLineReferenceType }
     *     
     */
    public void setOrderLineReference(OrderLineReferenceType value) {
        this.orderLineReference = value;
    }

    /**
     * Gets the value of the despatchLineReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the despatchLineReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDespatchLineReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link LineReferenceType }
     * 
     * 
     */
    public List<LineReferenceType> getDespatchLineReference() {
        if (despatchLineReference == null) {
            despatchLineReference = new ArrayList<LineReferenceType>();
        }
        return this.despatchLineReference;
    }

    /**
     * Gets the value of the documentReference property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the documentReference property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getDocumentReference().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentReferenceType }
     * 
     * 
     */
    public List<DocumentReferenceType> getDocumentReference() {
        if (documentReference == null) {
            documentReference = new ArrayList<DocumentReferenceType>();
        }
        return this.documentReference;
    }

    /**
     * Gets the value of the item property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the item property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getItem().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ItemType }
     * 
     * 
     */
    public List<ItemType> getItem() {
        if (item == null) {
            item = new ArrayList<ItemType>();
        }
        return this.item;
    }

    /**
     * Gets the value of the shipment property.
     * 
     * &lt;p&gt;
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a &lt;CODE&gt;set&lt;/CODE&gt; method for the shipment property.
     * 
     * &lt;p&gt;
     * For example, to add a new item, do as follows:
     * &lt;pre&gt;
     *    getShipment().add(newItem);
     * &lt;/pre&gt;
     * 
     * 
     * &lt;p&gt;
     * Objects of the following type(s) are allowed in the list
     * {@link ShipmentType }
     * 
     * 
     */
    public List<ShipmentType> getShipment() {
        if (shipment == null) {
            shipment = new ArrayList<ShipmentType>();
        }
        return this.shipment;
    }

}
