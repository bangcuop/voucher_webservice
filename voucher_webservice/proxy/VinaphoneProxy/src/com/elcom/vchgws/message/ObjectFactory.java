
package com.elcom.vchgws.message;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.elcom.vchgws.message package. 
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

    private final static QName _LoadResponseMessage_QNAME = new QName("http://message.vchgws.elcom.com", "message");
    private final static QName _LoadResponseSSerialNumber_QNAME = new QName("http://message.vchgws.elcom.com", "SSerialNumber");
    private final static QName _LoginResponseSessionid_QNAME = new QName("http://message.vchgws.elcom.com", "sessionid");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.elcom.vchgws.message
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LoadResponse }
     * 
     */
    public LoadResponse createLoadResponse() {
        return new LoadResponse();
    }

    /**
     * Create an instance of {@link TodayEnquiryResponse }
     * 
     */
    public TodayEnquiryResponse createTodayEnquiryResponse() {
        return new TodayEnquiryResponse();
    }

    /**
     * Create an instance of {@link ChangeMPINResponse }
     * 
     */
    public ChangeMPINResponse createChangeMPINResponse() {
        return new ChangeMPINResponse();
    }

    /**
     * Create an instance of {@link ChangePasswordResponse }
     * 
     */
    public ChangePasswordResponse createChangePasswordResponse() {
        return new ChangePasswordResponse();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link LogoutResponse }
     * 
     */
    public LogoutResponse createLogoutResponse() {
        return new LogoutResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.vchgws.elcom.com", name = "message", scope = LoadResponse.class)
    public JAXBElement<String> createLoadResponseMessage(String value) {
        return new JAXBElement<String>(_LoadResponseMessage_QNAME, String.class, LoadResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.vchgws.elcom.com", name = "SSerialNumber", scope = LoadResponse.class)
    public JAXBElement<String> createLoadResponseSSerialNumber(String value) {
        return new JAXBElement<String>(_LoadResponseSSerialNumber_QNAME, String.class, LoadResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.vchgws.elcom.com", name = "message", scope = LoginResponse.class)
    public JAXBElement<String> createLoginResponseMessage(String value) {
        return new JAXBElement<String>(_LoadResponseMessage_QNAME, String.class, LoginResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.vchgws.elcom.com", name = "sessionid", scope = LoginResponse.class)
    public JAXBElement<String> createLoginResponseSessionid(String value) {
        return new JAXBElement<String>(_LoginResponseSessionid_QNAME, String.class, LoginResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.vchgws.elcom.com", name = "message", scope = TodayEnquiryResponse.class)
    public JAXBElement<String> createTodayEnquiryResponseMessage(String value) {
        return new JAXBElement<String>(_LoadResponseMessage_QNAME, String.class, TodayEnquiryResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.vchgws.elcom.com", name = "message", scope = ChangeMPINResponse.class)
    public JAXBElement<String> createChangeMPINResponseMessage(String value) {
        return new JAXBElement<String>(_LoadResponseMessage_QNAME, String.class, ChangeMPINResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.vchgws.elcom.com", name = "message", scope = LogoutResponse.class)
    public JAXBElement<String> createLogoutResponseMessage(String value) {
        return new JAXBElement<String>(_LoadResponseMessage_QNAME, String.class, LogoutResponse.class, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://message.vchgws.elcom.com", name = "message", scope = ChangePasswordResponse.class)
    public JAXBElement<String> createChangePasswordResponseMessage(String value) {
        return new JAXBElement<String>(_LoadResponseMessage_QNAME, String.class, ChangePasswordResponse.class, value);
    }

}
