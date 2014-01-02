
package vn.proonline.secondtelcoapi.ws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the vn.proonline.secondtelcoapi.ws package. 
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

    private final static QName _CardCharge_QNAME = new QName("http://ws.secondtelcoAPI.proonline.vn/", "CardCharge");
    private final static QName _ChangePassword_QNAME = new QName("http://ws.secondtelcoAPI.proonline.vn/", "ChangePassword");
    private final static QName _LogIn_QNAME = new QName("http://ws.secondtelcoAPI.proonline.vn/", "logIn");
    private final static QName _ChangeMPIN_QNAME = new QName("http://ws.secondtelcoAPI.proonline.vn/", "ChangeMPIN");
    private final static QName _LogInResponse_QNAME = new QName("http://ws.secondtelcoAPI.proonline.vn/", "logInResponse");
    private final static QName _ChangePasswordResponse_QNAME = new QName("http://ws.secondtelcoAPI.proonline.vn/", "ChangePasswordResponse");
    private final static QName _ChangeMPINResponse_QNAME = new QName("http://ws.secondtelcoAPI.proonline.vn/", "ChangeMPINResponse");
    private final static QName _CardChargeResponse_QNAME = new QName("http://ws.secondtelcoAPI.proonline.vn/", "CardChargeResponse");
    private final static QName _LogOut_QNAME = new QName("http://ws.secondtelcoAPI.proonline.vn/", "logOut");
    private final static QName _LogOutResponse_QNAME = new QName("http://ws.secondtelcoAPI.proonline.vn/", "logOutResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: vn.proonline.secondtelcoapi.ws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ChangePassword }
     * 
     */
    public ChangePassword createChangePassword() {
        return new ChangePassword();
    }

    /**
     * Create an instance of {@link CardCharge }
     * 
     */
    public CardCharge createCardCharge() {
        return new CardCharge();
    }

    /**
     * Create an instance of {@link ChangeMPINResponse }
     * 
     */
    public ChangeMPINResponse createChangeMPINResponse() {
        return new ChangeMPINResponse();
    }

    /**
     * Create an instance of {@link LogOutResponse }
     * 
     */
    public LogOutResponse createLogOutResponse() {
        return new LogOutResponse();
    }

    /**
     * Create an instance of {@link LogOut }
     * 
     */
    public LogOut createLogOut() {
        return new LogOut();
    }

    /**
     * Create an instance of {@link CardChargeResponse }
     * 
     */
    public CardChargeResponse createCardChargeResponse() {
        return new CardChargeResponse();
    }

    /**
     * Create an instance of {@link LogInResponse }
     * 
     */
    public LogInResponse createLogInResponse() {
        return new LogInResponse();
    }

    /**
     * Create an instance of {@link ChangeMPIN }
     * 
     */
    public ChangeMPIN createChangeMPIN() {
        return new ChangeMPIN();
    }

    /**
     * Create an instance of {@link LogIn }
     * 
     */
    public LogIn createLogIn() {
        return new LogIn();
    }

    /**
     * Create an instance of {@link ChangePasswordResponse }
     * 
     */
    public ChangePasswordResponse createChangePasswordResponse() {
        return new ChangePasswordResponse();
    }

    /**
     * Create an instance of {@link ChargeResponse }
     * 
     */
    public ChargeResponse createChargeResponse() {
        return new ChargeResponse();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link ChangeResponse }
     * 
     */
    public ChangeResponse createChangeResponse() {
        return new ChangeResponse();
    }

    /**
     * Create an instance of {@link LogoutResponse }
     * 
     */
    public LogoutResponse createLogoutResponse() {
        return new LogoutResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CardCharge }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.secondtelcoAPI.proonline.vn/", name = "CardCharge")
    public JAXBElement<CardCharge> createCardCharge(CardCharge value) {
        return new JAXBElement<CardCharge>(_CardCharge_QNAME, CardCharge.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangePassword }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.secondtelcoAPI.proonline.vn/", name = "ChangePassword")
    public JAXBElement<ChangePassword> createChangePassword(ChangePassword value) {
        return new JAXBElement<ChangePassword>(_ChangePassword_QNAME, ChangePassword.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogIn }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.secondtelcoAPI.proonline.vn/", name = "logIn")
    public JAXBElement<LogIn> createLogIn(LogIn value) {
        return new JAXBElement<LogIn>(_LogIn_QNAME, LogIn.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeMPIN }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.secondtelcoAPI.proonline.vn/", name = "ChangeMPIN")
    public JAXBElement<ChangeMPIN> createChangeMPIN(ChangeMPIN value) {
        return new JAXBElement<ChangeMPIN>(_ChangeMPIN_QNAME, ChangeMPIN.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogInResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.secondtelcoAPI.proonline.vn/", name = "logInResponse")
    public JAXBElement<LogInResponse> createLogInResponse(LogInResponse value) {
        return new JAXBElement<LogInResponse>(_LogInResponse_QNAME, LogInResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangePasswordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.secondtelcoAPI.proonline.vn/", name = "ChangePasswordResponse")
    public JAXBElement<ChangePasswordResponse> createChangePasswordResponse(ChangePasswordResponse value) {
        return new JAXBElement<ChangePasswordResponse>(_ChangePasswordResponse_QNAME, ChangePasswordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeMPINResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.secondtelcoAPI.proonline.vn/", name = "ChangeMPINResponse")
    public JAXBElement<ChangeMPINResponse> createChangeMPINResponse(ChangeMPINResponse value) {
        return new JAXBElement<ChangeMPINResponse>(_ChangeMPINResponse_QNAME, ChangeMPINResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CardChargeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.secondtelcoAPI.proonline.vn/", name = "CardChargeResponse")
    public JAXBElement<CardChargeResponse> createCardChargeResponse(CardChargeResponse value) {
        return new JAXBElement<CardChargeResponse>(_CardChargeResponse_QNAME, CardChargeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogOut }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.secondtelcoAPI.proonline.vn/", name = "logOut")
    public JAXBElement<LogOut> createLogOut(LogOut value) {
        return new JAXBElement<LogOut>(_LogOut_QNAME, LogOut.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogOutResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.secondtelcoAPI.proonline.vn/", name = "logOutResponse")
    public JAXBElement<LogOutResponse> createLogOutResponse(LogOutResponse value) {
        return new JAXBElement<LogOutResponse>(_LogOutResponse_QNAME, LogOutResponse.class, null, value);
    }

}
