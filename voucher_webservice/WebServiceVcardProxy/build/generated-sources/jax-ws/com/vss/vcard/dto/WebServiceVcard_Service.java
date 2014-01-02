
package com.vss.vcard.dto;

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
@WebServiceClient(name = "WebServiceVcard", targetNamespace = "http://com/vss/vcard/dto", wsdlLocation = "http://10.26.5.52:8080/WebServiceVcard/WebServiceVcard?wsdl")
public class WebServiceVcard_Service
    extends Service
{

    private final static URL WEBSERVICEVCARD_WSDL_LOCATION;
    private final static WebServiceException WEBSERVICEVCARD_EXCEPTION;
    private final static QName WEBSERVICEVCARD_QNAME = new QName("http://com/vss/vcard/dto", "WebServiceVcard");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://10.26.5.52:8080/WebServiceVcard/WebServiceVcard?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        WEBSERVICEVCARD_WSDL_LOCATION = url;
        WEBSERVICEVCARD_EXCEPTION = e;
    }

    public WebServiceVcard_Service() {
        super(__getWsdlLocation(), WEBSERVICEVCARD_QNAME);
    }

    public WebServiceVcard_Service(WebServiceFeature... features) {
        super(__getWsdlLocation(), WEBSERVICEVCARD_QNAME, features);
    }

    public WebServiceVcard_Service(URL wsdlLocation) {
        super(wsdlLocation, WEBSERVICEVCARD_QNAME);
    }

    public WebServiceVcard_Service(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, WEBSERVICEVCARD_QNAME, features);
    }

    public WebServiceVcard_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public WebServiceVcard_Service(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns WebServiceVcard
     */
    @WebEndpoint(name = "WebServiceVcardPort")
    public WebServiceVcard getWebServiceVcardPort() {
        return super.getPort(new QName("http://com/vss/vcard/dto", "WebServiceVcardPort"), WebServiceVcard.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns WebServiceVcard
     */
    @WebEndpoint(name = "WebServiceVcardPort")
    public WebServiceVcard getWebServiceVcardPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://com/vss/vcard/dto", "WebServiceVcardPort"), WebServiceVcard.class, features);
    }

    private static URL __getWsdlLocation() {
        if (WEBSERVICEVCARD_EXCEPTION!= null) {
            throw WEBSERVICEVCARD_EXCEPTION;
        }
        return WEBSERVICEVCARD_WSDL_LOCATION;
    }

}
