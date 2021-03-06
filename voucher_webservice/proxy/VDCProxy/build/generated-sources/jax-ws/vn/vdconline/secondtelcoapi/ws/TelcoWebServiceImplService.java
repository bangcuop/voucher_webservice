
package vn.vdconline.secondtelcoapi.ws;

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
 * JAX-WS RI 2.2.6-1b01 
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "TelcoWebServiceImplService", targetNamespace = "http://ws.secondtelcoAPI.vdconline.vn/", wsdlLocation = "file:/home/liemnh/Desktop/VDCTelcoAPI")
public class TelcoWebServiceImplService
    extends Service
{

    private final static URL TELCOWEBSERVICEIMPLSERVICE_WSDL_LOCATION;
    private final static WebServiceException TELCOWEBSERVICEIMPLSERVICE_EXCEPTION;
    private final static QName TELCOWEBSERVICEIMPLSERVICE_QNAME = new QName("http://ws.secondtelcoAPI.vdconline.vn/", "TelcoWebServiceImplService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("file:/home/liemnh/Desktop/VDCTelcoAPI");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        TELCOWEBSERVICEIMPLSERVICE_WSDL_LOCATION = url;
        TELCOWEBSERVICEIMPLSERVICE_EXCEPTION = e;
    }

    public TelcoWebServiceImplService() {
        super(__getWsdlLocation(), TELCOWEBSERVICEIMPLSERVICE_QNAME);
    }

    public TelcoWebServiceImplService(WebServiceFeature... features) {
        super(__getWsdlLocation(), TELCOWEBSERVICEIMPLSERVICE_QNAME, features);
    }

    public TelcoWebServiceImplService(URL wsdlLocation) {
        super(wsdlLocation, TELCOWEBSERVICEIMPLSERVICE_QNAME);
    }

    public TelcoWebServiceImplService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, TELCOWEBSERVICEIMPLSERVICE_QNAME, features);
    }

    public TelcoWebServiceImplService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public TelcoWebServiceImplService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns TelcoWebService
     */
    @WebEndpoint(name = "TelcoWebServiceImplPort")
    public TelcoWebService getTelcoWebServiceImplPort() {
        return super.getPort(new QName("http://ws.secondtelcoAPI.vdconline.vn/", "TelcoWebServiceImplPort"), TelcoWebService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns TelcoWebService
     */
    @WebEndpoint(name = "TelcoWebServiceImplPort")
    public TelcoWebService getTelcoWebServiceImplPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://ws.secondtelcoAPI.vdconline.vn/", "TelcoWebServiceImplPort"), TelcoWebService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (TELCOWEBSERVICEIMPLSERVICE_EXCEPTION!= null) {
            throw TELCOWEBSERVICEIMPLSERVICE_EXCEPTION;
        }
        return TELCOWEBSERVICEIMPLSERVICE_WSDL_LOCATION;
    }

}
