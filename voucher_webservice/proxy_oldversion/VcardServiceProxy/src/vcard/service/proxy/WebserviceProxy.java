package vcard.service.proxy;

import com.vss.vcard.dto.WebServiceVcard;
import com.vss.vcard.dto.WebServiceVcard_Service;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;

public class WebserviceProxy
{
  static final String RESOURCE = "WSConfig";
  static ResourceBundle bundle = ResourceBundle.getBundle("WSConfig");

  public static WebServiceVcard getVcardWebService() throws Exception {
    WebServiceVcard service = new WebServiceVcard_Service().getWebServiceVcardPort();
    return (WebServiceVcard)registerWSDL(service, "ws-url-vcardService");
  }

  private static Object registerWSDL(Object s, String key) {
    Binding binding = ((BindingProvider)s).getBinding();
    List handlerList = binding.getHandlerChain();
    handlerList.add(new SoapHandler());
    binding.setHandlerChain(handlerList);

    Map ctx = ((BindingProvider)s).getRequestContext();
    ctx.put("javax.xml.ws.service.endpoint.address", bundle.getString(key));
    return s;
  }
}