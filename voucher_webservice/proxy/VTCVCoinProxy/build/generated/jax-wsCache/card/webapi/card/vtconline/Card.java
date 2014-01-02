
package webapi.card.vtconline;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2-hudson-752-
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "card", targetNamespace = "VTCOnline.Card.WebAPI", wsdlLocation = "http://sandbox2.vtcebank.vn/WSCard2010/card.asmx?wsdl")
public class Card
    extends Service
{

    private final static URL CARD_WSDL_LOCATION;
    private final static WebServiceException CARD_EXCEPTION;
    private final static QName CARD_QNAME = new QName("VTCOnline.Card.WebAPI", "card");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://sandbox2.vtcebank.vn/WSCard2010/card.asmx?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CARD_WSDL_LOCATION = url;
        CARD_EXCEPTION = e;
    }

    public Card() {
        super(__getWsdlLocation(), CARD_QNAME);
    }

    public Card(WebServiceFeature... features) {
        super(__getWsdlLocation(), CARD_QNAME, features);
    }

    public Card(URL wsdlLocation) {
        super(wsdlLocation, CARD_QNAME);
    }

    public Card(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CARD_QNAME, features);
    }

    public Card(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public Card(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns CardSoap
     */
    @WebEndpoint(name = "cardSoap")
    public CardSoap getCardSoap() {
        return super.getPort(new QName("VTCOnline.Card.WebAPI", "cardSoap"), CardSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CardSoap
     */
    @WebEndpoint(name = "cardSoap")
    public CardSoap getCardSoap(WebServiceFeature... features) {
        return super.getPort(new QName("VTCOnline.Card.WebAPI", "cardSoap"), CardSoap.class, features);
    }

    /**
     * 
     * @return
     *     returns CardSoap
     */
    @WebEndpoint(name = "cardSoap12")
    public CardSoap getCardSoap12() {
        return super.getPort(new QName("VTCOnline.Card.WebAPI", "cardSoap12"), CardSoap.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CardSoap
     */
    @WebEndpoint(name = "cardSoap12")
    public CardSoap getCardSoap12(WebServiceFeature... features) {
        return super.getPort(new QName("VTCOnline.Card.WebAPI", "cardSoap12"), CardSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CARD_EXCEPTION!= null) {
            throw CARD_EXCEPTION;
        }
        return CARD_WSDL_LOCATION;
    }

}
