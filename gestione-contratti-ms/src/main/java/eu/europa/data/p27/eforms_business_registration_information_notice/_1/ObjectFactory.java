//
// Questo file è stato generato dall'Eclipse Implementation of JAXB, v2.3.3 
// Vedere https://eclipse-ee4j.github.io/jaxb-ri 
// Qualsiasi modifica a questo file andrà persa durante la ricompilazione dello schema di origine. 
// Generato il: 2023.12.27 alle 09:54:01 AM CET 
//


package eu.europa.data.p27.eforms_business_registration_information_notice._1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the eu.europa.data.p27.eforms_business_registration_information_notice._1 package. 
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

    private final static QName _BusinessRegistrationInformationNotice_QNAME = new QName("http://data.europa.eu/p27/eforms-business-registration-information-notice/1", "BusinessRegistrationInformationNotice");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: eu.europa.data.p27.eforms_business_registration_information_notice._1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BusinessRegistrationInformationNoticeType }
     * 
     */
    public BusinessRegistrationInformationNoticeType createBusinessRegistrationInformationNoticeType() {
        return new BusinessRegistrationInformationNoticeType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessRegistrationInformationNoticeType }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BusinessRegistrationInformationNoticeType }{@code >}
     */
    @XmlElementDecl(namespace = "http://data.europa.eu/p27/eforms-business-registration-information-notice/1", name = "BusinessRegistrationInformationNotice")
    public JAXBElement<BusinessRegistrationInformationNoticeType> createBusinessRegistrationInformationNotice(BusinessRegistrationInformationNoticeType value) {
        return new JAXBElement<BusinessRegistrationInformationNoticeType>(_BusinessRegistrationInformationNotice_QNAME, BusinessRegistrationInformationNoticeType.class, null, value);
    }

}
