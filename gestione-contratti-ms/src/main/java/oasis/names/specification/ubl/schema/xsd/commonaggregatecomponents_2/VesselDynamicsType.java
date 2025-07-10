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
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.AtAnchorageIndicatorType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.CourseOverGroundDirectionType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.NavigationStatusCodeType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.RateOfTurnMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonbasiccomponents_2.SpeedOverGroundMeasureType;
import oasis.names.specification.ubl.schema.xsd.commonextensioncomponents_2.UBLExtensionsType;


/**
 * &lt;p&gt;Classe Java per VesselDynamicsType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="VesselDynamicsType"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2}UBLExtensions" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}NavigationStatusCode" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}AtAnchorageIndicator" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}CourseOverGroundDirection" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}SpeedOverGroundMeasure" minOccurs="0"/&amp;gt;
 *         &amp;lt;element ref="{urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2}RateOfTurnMeasure" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VesselDynamicsType", propOrder = {
    "ublExtensions",
    "navigationStatusCode",
    "atAnchorageIndicator",
    "courseOverGroundDirection",
    "speedOverGroundMeasure",
    "rateOfTurnMeasure"
})
public class VesselDynamicsType {

    @XmlElement(name = "UBLExtensions", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonExtensionComponents-2")
    protected UBLExtensionsType ublExtensions;
    @XmlElement(name = "NavigationStatusCode", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected NavigationStatusCodeType navigationStatusCode;
    @XmlElement(name = "AtAnchorageIndicator", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected AtAnchorageIndicatorType atAnchorageIndicator;
    @XmlElement(name = "CourseOverGroundDirection", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected CourseOverGroundDirectionType courseOverGroundDirection;
    @XmlElement(name = "SpeedOverGroundMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected SpeedOverGroundMeasureType speedOverGroundMeasure;
    @XmlElement(name = "RateOfTurnMeasure", namespace = "urn:oasis:names:specification:ubl:schema:xsd:CommonBasicComponents-2")
    protected RateOfTurnMeasureType rateOfTurnMeasure;

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
     * Recupera il valore della proprietà navigationStatusCode.
     * 
     * @return
     *     possible object is
     *     {@link NavigationStatusCodeType }
     *     
     */
    public NavigationStatusCodeType getNavigationStatusCode() {
        return navigationStatusCode;
    }

    /**
     * Imposta il valore della proprietà navigationStatusCode.
     * 
     * @param value
     *     allowed object is
     *     {@link NavigationStatusCodeType }
     *     
     */
    public void setNavigationStatusCode(NavigationStatusCodeType value) {
        this.navigationStatusCode = value;
    }

    /**
     * Recupera il valore della proprietà atAnchorageIndicator.
     * 
     * @return
     *     possible object is
     *     {@link AtAnchorageIndicatorType }
     *     
     */
    public AtAnchorageIndicatorType getAtAnchorageIndicator() {
        return atAnchorageIndicator;
    }

    /**
     * Imposta il valore della proprietà atAnchorageIndicator.
     * 
     * @param value
     *     allowed object is
     *     {@link AtAnchorageIndicatorType }
     *     
     */
    public void setAtAnchorageIndicator(AtAnchorageIndicatorType value) {
        this.atAnchorageIndicator = value;
    }

    /**
     * Recupera il valore della proprietà courseOverGroundDirection.
     * 
     * @return
     *     possible object is
     *     {@link CourseOverGroundDirectionType }
     *     
     */
    public CourseOverGroundDirectionType getCourseOverGroundDirection() {
        return courseOverGroundDirection;
    }

    /**
     * Imposta il valore della proprietà courseOverGroundDirection.
     * 
     * @param value
     *     allowed object is
     *     {@link CourseOverGroundDirectionType }
     *     
     */
    public void setCourseOverGroundDirection(CourseOverGroundDirectionType value) {
        this.courseOverGroundDirection = value;
    }

    /**
     * Recupera il valore della proprietà speedOverGroundMeasure.
     * 
     * @return
     *     possible object is
     *     {@link SpeedOverGroundMeasureType }
     *     
     */
    public SpeedOverGroundMeasureType getSpeedOverGroundMeasure() {
        return speedOverGroundMeasure;
    }

    /**
     * Imposta il valore della proprietà speedOverGroundMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link SpeedOverGroundMeasureType }
     *     
     */
    public void setSpeedOverGroundMeasure(SpeedOverGroundMeasureType value) {
        this.speedOverGroundMeasure = value;
    }

    /**
     * Recupera il valore della proprietà rateOfTurnMeasure.
     * 
     * @return
     *     possible object is
     *     {@link RateOfTurnMeasureType }
     *     
     */
    public RateOfTurnMeasureType getRateOfTurnMeasure() {
        return rateOfTurnMeasure;
    }

    /**
     * Imposta il valore della proprietà rateOfTurnMeasure.
     * 
     * @param value
     *     allowed object is
     *     {@link RateOfTurnMeasureType }
     *     
     */
    public void setRateOfTurnMeasure(RateOfTurnMeasureType value) {
        this.rateOfTurnMeasure = value;
    }

}
