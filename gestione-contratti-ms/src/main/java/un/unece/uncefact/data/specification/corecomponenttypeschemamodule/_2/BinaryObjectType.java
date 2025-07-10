//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package un.unece.uncefact.data.specification.corecomponenttypeschemamodule._2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import oasis.names.specification.bdndr.schema.xsd.unqualifieddatatypes_1.GraphicType;
import oasis.names.specification.bdndr.schema.xsd.unqualifieddatatypes_1.PictureType;
import oasis.names.specification.bdndr.schema.xsd.unqualifieddatatypes_1.SoundType;
import oasis.names.specification.bdndr.schema.xsd.unqualifieddatatypes_1.VideoType;


/**
 * 
 *             
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:UniqueID xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;UNDT000002&amp;lt;/ccts:UniqueID&amp;gt;
 * &lt;/pre&gt;
 * 
 *             
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:CategoryCode xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;CCT&amp;lt;/ccts:CategoryCode&amp;gt;
 * &lt;/pre&gt;
 * 
 *             
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:DictionaryEntryName xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Binary Object. Type&amp;lt;/ccts:DictionaryEntryName&amp;gt;
 * &lt;/pre&gt;
 * 
 *             
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:VersionID xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;1.0&amp;lt;/ccts:VersionID&amp;gt;
 * &lt;/pre&gt;
 * 
 *             
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:Definition xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;A set of finite-length sequences of binary octets.&amp;lt;/ccts:Definition&amp;gt;
 * &lt;/pre&gt;
 * 
 *             
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:RepresentationTermName xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;Binary Object&amp;lt;/ccts:RepresentationTermName&amp;gt;
 * &lt;/pre&gt;
 * 
 *             
 * &lt;pre&gt;
 * &amp;lt;?xml version="1.0" encoding="UTF-8"?&amp;gt;&amp;lt;ccts:PrimitiveType xmlns:ccts="urn:un:unece:uncefact:documentation:2" xmlns:cct="urn:un:unece:uncefact:data:specification:CoreComponentTypeSchemaModule:2" xmlns:xsd="http://www.w3.org/2001/XMLSchema"&amp;gt;binary&amp;lt;/ccts:PrimitiveType&amp;gt;
 * &lt;/pre&gt;
 * 
 *          
 * 
 * &lt;p&gt;Classe Java per BinaryObjectType complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="BinaryObjectType"&amp;gt;
 *   &amp;lt;simpleContent&amp;gt;
 *     &amp;lt;extension base="&amp;lt;http://www.w3.org/2001/XMLSchema&amp;gt;base64Binary"&amp;gt;
 *       &amp;lt;attribute name="format" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *       &amp;lt;attribute name="mimeCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" /&amp;gt;
 *       &amp;lt;attribute name="encodingCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" /&amp;gt;
 *       &amp;lt;attribute name="characterSetCode" type="{http://www.w3.org/2001/XMLSchema}normalizedString" /&amp;gt;
 *       &amp;lt;attribute name="uri" type="{http://www.w3.org/2001/XMLSchema}anyURI" /&amp;gt;
 *       &amp;lt;attribute name="filename" type="{http://www.w3.org/2001/XMLSchema}string" /&amp;gt;
 *     &amp;lt;/extension&amp;gt;
 *   &amp;lt;/simpleContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BinaryObjectType", propOrder = {
    "value"
})
@XmlSeeAlso({
    GraphicType.class,
    PictureType.class,
    SoundType.class,
    VideoType.class,
    oasis.names.specification.bdndr.schema.xsd.unqualifieddatatypes_1.BinaryObjectType.class
})
public class BinaryObjectType {

    @XmlValue
    protected byte[] value;
    @XmlAttribute(name = "format")
    protected String format;
    @XmlAttribute(name = "mimeCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String mimeCode;
    @XmlAttribute(name = "encodingCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String encodingCode;
    @XmlAttribute(name = "characterSetCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String characterSetCode;
    @XmlAttribute(name = "uri")
    @XmlSchemaType(name = "anyURI")
    protected String uri;
    @XmlAttribute(name = "filename")
    protected String filename;

    /**
     * Recupera il valore della proprietà value.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getValue() {
        return value;
    }

    /**
     * Imposta il valore della proprietà value.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setValue(byte[] value) {
        this.value = value;
    }

    /**
     * Recupera il valore della proprietà format.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFormat() {
        return format;
    }

    /**
     * Imposta il valore della proprietà format.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFormat(String value) {
        this.format = value;
    }

    /**
     * Recupera il valore della proprietà mimeCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMimeCode() {
        return mimeCode;
    }

    /**
     * Imposta il valore della proprietà mimeCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMimeCode(String value) {
        this.mimeCode = value;
    }

    /**
     * Recupera il valore della proprietà encodingCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncodingCode() {
        return encodingCode;
    }

    /**
     * Imposta il valore della proprietà encodingCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncodingCode(String value) {
        this.encodingCode = value;
    }

    /**
     * Recupera il valore della proprietà characterSetCode.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCharacterSetCode() {
        return characterSetCode;
    }

    /**
     * Imposta il valore della proprietà characterSetCode.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCharacterSetCode(String value) {
        this.characterSetCode = value;
    }

    /**
     * Recupera il valore della proprietà uri.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUri() {
        return uri;
    }

    /**
     * Imposta il valore della proprietà uri.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUri(String value) {
        this.uri = value;
    }

    /**
     * Recupera il valore della proprietà filename.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Imposta il valore della proprietà filename.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFilename(String value) {
        this.filename = value;
    }

}
