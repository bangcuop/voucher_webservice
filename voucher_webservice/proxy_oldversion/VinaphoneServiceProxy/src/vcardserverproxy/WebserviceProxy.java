package vcardserverproxy;

import com.elcom.vchgws.VCardServer;
import com.elcom.vchgws.VCardServerPortType;
import java.net.URL;
import java.util.ResourceBundle;
import javax.xml.namespace.QName;

public class WebserviceProxy {

    static final String RESOURCE = "WSConfig";
    static ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE);

    static {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("vcardserverproxy.WSConfig");
        }
    }

    public static VCardServerPortType getVCardServerPortType()
            throws Exception {
        VCardServerPortType service = new VCardServer(new URL(bundle.getString("ws-url-vinaphone")), new QName("http://vchgws.elcom.com", "VCardServer")).getVCardServerHttpPort();
        return service;
    }
}
