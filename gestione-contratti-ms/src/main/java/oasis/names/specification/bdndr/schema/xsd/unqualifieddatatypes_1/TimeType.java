//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package oasis.names.specification.bdndr.schema.xsd.unqualifieddatatypes_1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.datatype.XMLGregorianCalendar;
import eu.europa.data.p27.eforms_ubl_extension_basic_components._1.TransmissionTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ActualDeliveryTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ActualDespatchTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ActualPickupTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AwardTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CallTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ComparisonForecastIssueTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EarliestPickupTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EffectiveTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EndTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedDeliveryTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.EstimatedDespatchTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ExpiryTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.GuaranteedDespatchTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.IssueTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LastRevisionTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LatestDeliveryTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LatestPickupTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.LatestReplyTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ManufactureTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NominationTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.OccurrenceTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.PaidTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ReferenceTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RegisteredTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequestedDespatchTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RequiredDeliveryTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResolutionTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ResponseTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RevisionTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SourceForecastIssueTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.StartTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.ValidationTimeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.WeighingTimeType;


/**
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:UniqueID xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;BDNDRUDT0000010&amp;lt;/ccts:UniqueID&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:CategoryCode xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;UDT&amp;lt;/ccts:CategoryCode&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:DictionaryEntryName xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Time. Type&amp;lt;/ccts:DictionaryEntryName&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:VersionID xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;1.0&amp;lt;/ccts:VersionID&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:Definition xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;An instance of time that occurs every day.&amp;lt;/ccts:Definition&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:RepresentationTermName xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Time&amp;lt;/ccts:RepresentationTermName&amp;gt;
 * &lt;/pre&gt;
 * 
 *         
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:PrimitiveType xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:ccts-cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;string&amp;lt;/ccts:PrimitiveType&amp;gt;
 * &lt;/pre&gt;
 * 
 *       
 * 
 * &lt;p&gt;Classe Java per TimeType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="TimeType"&amp;gt;
 *   &amp;lt;simpleContent&amp;gt;
 *     &amp;lt;extension base="&amp;lt;http://www.w3.org/2001/XMLSchema&amp;gt;time"&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/simpleContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TimeType", propOrder = {
    "value"
})
@XmlSeeAlso({
    ActualDeliveryTimeType.class,
    ActualDespatchTimeType.class,
    ActualPickupTimeType.class,
    AwardTimeType.class,
    CallTimeType.class,
    ComparisonForecastIssueTimeType.class,
    EarliestPickupTimeType.class,
    EffectiveTimeType.class,
    EndTimeType.class,
    EstimatedDeliveryTimeType.class,
    EstimatedDespatchTimeType.class,
    ExpiryTimeType.class,
    GuaranteedDespatchTimeType.class,
    IssueTimeType.class,
    LastRevisionTimeType.class,
    LatestDeliveryTimeType.class,
    LatestPickupTimeType.class,
    LatestReplyTimeType.class,
    ManufactureTimeType.class,
    NominationTimeType.class,
    OccurrenceTimeType.class,
    PaidTimeType.class,
    ReferenceTimeType.class,
    RegisteredTimeType.class,
    RequestedDespatchTimeType.class,
    RequiredDeliveryTimeType.class,
    ResolutionTimeType.class,
    ResponseTimeType.class,
    RevisionTimeType.class,
    SourceForecastIssueTimeType.class,
    StartTimeType.class,
    ValidationTimeType.class,
    WeighingTimeType.class,
    TransmissionTimeType.class
})
public class TimeType {

    @XmlValue
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar value;

    /**
     * Recupera il valore della proprietà value.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getValue() {
        return value;
    }

    /**
     * Imposta il valore della proprietà value.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setValue(XMLGregorianCalendar value) {
        this.value = value;
    }

}
