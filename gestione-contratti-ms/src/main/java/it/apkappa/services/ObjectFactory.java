
package it.apkappa.services;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.apkappa.services package. 
 * <p>An ObjectFactory allows you to programatically 
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

    private final static QName _AuthenticationDetails_QNAME = new QName("http://services.apkappa.it/", "AuthenticationDetails");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.apkappa.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetCapitoli }
     * 
     */
    public GetCapitoli createGetCapitoli() {
        return new GetCapitoli();
    }

    /**
     * Create an instance of {@link InGetCapitoli }
     * 
     */
    public InGetCapitoli createInGetCapitoli() {
        return new InGetCapitoli();
    }

    /**
     * Create an instance of {@link GetCapitoliResponse }
     * 
     */
    public GetCapitoliResponse createGetCapitoliResponse() {
        return new GetCapitoliResponse();
    }

    /**
     * Create an instance of {@link ArrayOfOutGetCapitoli }
     * 
     */
    public ArrayOfOutGetCapitoli createArrayOfOutGetCapitoli() {
        return new ArrayOfOutGetCapitoli();
    }

    /**
     * Create an instance of {@link AuthenticationDetails }
     * 
     */
    public AuthenticationDetails createAuthenticationDetails() {
        return new AuthenticationDetails();
    }

    /**
     * Create an instance of {@link GetImpegni }
     * 
     */
    public GetImpegni createGetImpegni() {
        return new GetImpegni();
    }

    /**
     * Create an instance of {@link InGetImpegni }
     * 
     */
    public InGetImpegni createInGetImpegni() {
        return new InGetImpegni();
    }

    /**
     * Create an instance of {@link GetImpegniResponse }
     * 
     */
    public GetImpegniResponse createGetImpegniResponse() {
        return new GetImpegniResponse();
    }

    /**
     * Create an instance of {@link ArrayOfOutGetImpegni }
     * 
     */
    public ArrayOfOutGetImpegni createArrayOfOutGetImpegni() {
        return new ArrayOfOutGetImpegni();
    }

    /**
     * Create an instance of {@link GetMandati }
     * 
     */
    public GetMandati createGetMandati() {
        return new GetMandati();
    }

    /**
     * Create an instance of {@link InGetMandati }
     * 
     */
    public InGetMandati createInGetMandati() {
        return new InGetMandati();
    }

    /**
     * Create an instance of {@link GetMandatiResponse }
     * 
     */
    public GetMandatiResponse createGetMandatiResponse() {
        return new GetMandatiResponse();
    }

    /**
     * Create an instance of {@link ArrayOfOutGetMandati }
     * 
     */
    public ArrayOfOutGetMandati createArrayOfOutGetMandati() {
        return new ArrayOfOutGetMandati();
    }

    /**
     * Create an instance of {@link OutGetCapitoli }
     * 
     */
    public OutGetCapitoli createOutGetCapitoli() {
        return new OutGetCapitoli();
    }

    /**
     * Create an instance of {@link OutGetImpegni }
     * 
     */
    public OutGetImpegni createOutGetImpegni() {
        return new OutGetImpegni();
    }

    /**
     * Create an instance of {@link OutGetMandati }
     * 
     */
    public OutGetMandati createOutGetMandati() {
        return new OutGetMandati();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthenticationDetails }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AuthenticationDetails }{@code >}
     */
    @XmlElementDecl(namespace = "http://services.apkappa.it/", name = "AuthenticationDetails")
    public JAXBElement<AuthenticationDetails> createAuthenticationDetails(AuthenticationDetails value) {
        return new JAXBElement<AuthenticationDetails>(_AuthenticationDetails_QNAME, AuthenticationDetails.class, null, value);
    }

}
