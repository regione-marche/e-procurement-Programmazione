//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package oasis.names.specification.ubl.schema.xsd.priorinformationnotice_2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the oasis.names.specification.ubl.schema.xsd.priorinformationnotice_2 package. 
 * &lt;p&gt;An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _PriorInformationNotice_QNAME = new QName("urn:oasis:names:specification:ubl:schema:xsd:PriorInformationNotice-2", "PriorInformationNotice");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: oasis.names.specification.ubl.schema.xsd.priorinformationnotice_2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PriorInformationNoticeType }
     * 
     */
    public PriorInformationNoticeType createPriorInformationNoticeType() {
        return new PriorInformationNoticeType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PriorInformationNoticeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link PriorInformationNoticeType }{@code >}
     */
    @XmlElementDecl(namespace = "urn:oasis:names:specification:ubl:schema:xsd:PriorInformationNotice-2", name = "PriorInformationNotice")
    public JAXBElement<PriorInformationNoticeType> createPriorInformationNotice(PriorInformationNoticeType value) {
        return new JAXBElement<PriorInformationNoticeType>(_PriorInformationNotice_QNAME, PriorInformationNoticeType.class, null, value);
    }

}
