package vcard.service.proxy;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class SoapHandler
  implements SOAPHandler<SOAPMessageContext>
{
  public Set<QName> getHeaders()
  {
    Set headers = new HashSet();
    headers.add(new QName("http://vietpay.vn/", "ReportSoapHeader_Server"));
    return headers;
  }

  public boolean handleMessage(SOAPMessageContext context) {
    Boolean outboundProperty = (Boolean)context.get("javax.xml.ws.handler.message.outbound");
    boolean result = true;

    if (outboundProperty.booleanValue()) {
      signOutgoingMessage(context);
    }

    return result;
  }

  public boolean handleFault(SOAPMessageContext context)
  {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void close(MessageContext context)
  {
  }

  private void signOutgoingMessage(SOAPMessageContext context)
  {
    SOAPMessage message = context.getMessage();
    try {
      SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();
      SOAPFactory factory = SOAPFactory.newInstance();

      String prefix = "";
      String uri = "http://vietpay.vn/";

      SOAPElement securityElem = factory.createElement("ReportSoapHeader_Server", prefix, uri);
      SOAPElement usernameElem = factory.createElement("Username", prefix, uri);
      usernameElem.addTextNode("vss");
      securityElem.addChildElement(usernameElem);

      SOAPElement passwordElem = factory.createElement("Password", prefix, uri);
      passwordElem.addTextNode("vss@123");
      securityElem.addChildElement(passwordElem);

      SOAPHeader header = envelope.addHeader();
      header.addChildElement(securityElem);
      message.saveChanges();
    } catch (Exception e) {
      System.out.println("Exception in handler: " + e);
    }
  }
}