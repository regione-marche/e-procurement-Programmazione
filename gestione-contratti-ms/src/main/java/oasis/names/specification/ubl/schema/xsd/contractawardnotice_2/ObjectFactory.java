//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package oasis.names.specification.ubl.schema.xsd.contractawardnotice_2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the oasis.names.specification.ubl.schema.xsd.contractawardnotice_2 package. 
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

    private final static QName _ContractAwardNotice_QNAME = new QName("urn:oasis:names:specification:ubl:schema:xsd:ContractAwardNotice-2", "ContractAwardNotice");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: oasis.names.specification.ubl.schema.xsd.contractawardnotice_2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ContractAwardNoticeType }
     * 
     */
    public ContractAwardNoticeType createContractAwardNoticeType() {
        return new ContractAwardNoticeType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContractAwardNoticeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ContractAwardNoticeType }{@code >}
     */
    @XmlElementDecl(namespace = "urn:oasis:names:specification:ubl:schema:xsd:ContractAwardNotice-2", name = "ContractAwardNotice")
    public JAXBElement<ContractAwardNoticeType> createContractAwardNotice(ContractAwardNoticeType value) {
        return new JAXBElement<ContractAwardNoticeType>(_ContractAwardNotice_QNAME, ContractAwardNoticeType.class, null, value);
    }

}
