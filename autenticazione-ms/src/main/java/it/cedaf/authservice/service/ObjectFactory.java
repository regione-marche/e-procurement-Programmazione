
package it.cedaf.authservice.service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.cedaf.authservice.service package. 
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

    private final static QName _Fault_QNAME = new QName("http://service.authservice.cedaf.it", "fault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.cedaf.authservice.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetAuthId }
     * 
     */
    public GetAuthId createGetAuthId() {
        return new GetAuthId();
    }

    /**
     * Create an instance of {@link GetAuthIdResponse }
     * 
     */
    public GetAuthIdResponse createGetAuthIdResponse() {
        return new GetAuthIdResponse();
    }

    /**
     * Create an instance of {@link AuthException }
     * 
     */
    public AuthException createAuthException() {
        return new AuthException();
    }

    /**
     * Create an instance of {@link IsUserSignedOut }
     * 
     */
    public IsUserSignedOut createIsUserSignedOut() {
        return new IsUserSignedOut();
    }

    /**
     * Create an instance of {@link IsUserSignedOutResponse }
     * 
     */
    public IsUserSignedOutResponse createIsUserSignedOutResponse() {
        return new IsUserSignedOutResponse();
    }

    /**
     * Create an instance of {@link RetrieveUserData }
     * 
     */
    public RetrieveUserData createRetrieveUserData() {
        return new RetrieveUserData();
    }

    /**
     * Create an instance of {@link RetrieveUserDataResponse }
     * 
     */
    public RetrieveUserDataResponse createRetrieveUserDataResponse() {
        return new RetrieveUserDataResponse();
    }

    /**
     * Create an instance of {@link AuthData }
     * 
     */
    public AuthData createAuthData() {
        return new AuthData();
    }

    /**
     * Create an instance of {@link RetrieveUserDataV2 }
     * 
     */
    public RetrieveUserDataV2 createRetrieveUserDataV2() {
        return new RetrieveUserDataV2();
    }

    /**
     * Create an instance of {@link RetrieveUserDataV2Response }
     * 
     */
    public RetrieveUserDataV2Response createRetrieveUserDataV2Response() {
        return new RetrieveUserDataV2Response();
    }

    /**
     * Create an instance of {@link AuthDataV2 }
     * 
     */
    public AuthDataV2 createAuthDataV2() {
        return new AuthDataV2();
    }

    /**
     * Create an instance of {@link RetrieveUserDataV3 }
     * 
     */
    public RetrieveUserDataV3 createRetrieveUserDataV3() {
        return new RetrieveUserDataV3();
    }

    /**
     * Create an instance of {@link RetrieveUserDataV3Response }
     * 
     */
    public RetrieveUserDataV3Response createRetrieveUserDataV3Response() {
        return new RetrieveUserDataV3Response();
    }

    /**
     * Create an instance of {@link AuthDataV3 }
     * 
     */
    public AuthDataV3 createAuthDataV3() {
        return new AuthDataV3();
    }

    /**
     * Create an instance of {@link RevalidateUserData }
     * 
     */
    public RevalidateUserData createRevalidateUserData() {
        return new RevalidateUserData();
    }

    /**
     * Create an instance of {@link RevalidateUserDataResponse }
     * 
     */
    public RevalidateUserDataResponse createRevalidateUserDataResponse() {
        return new RevalidateUserDataResponse();
    }

    /**
     * Create an instance of {@link SingleSignOut }
     * 
     */
    public SingleSignOut createSingleSignOut() {
        return new SingleSignOut();
    }

    /**
     * Create an instance of {@link SingleSignOutResponse }
     * 
     */
    public SingleSignOutResponse createSingleSignOutResponse() {
        return new SingleSignOutResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthException }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link AuthException }{@code >}
     */
    @XmlElementDecl(namespace = "http://service.authservice.cedaf.it", name = "fault")
    public JAXBElement<AuthException> createFault(AuthException value) {
        return new JAXBElement<AuthException>(_Fault_QNAME, AuthException.class, null, value);
    }

}
