/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.webservice;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

/**
 *
 * @author zannami
 */
public class SimpleHandler implements SOAPHandler<SOAPMessageContext> {

    public Set<QName> getHeaders() {
        Set headers = new HashSet();
        headers.add(new QName("http://vcard.vn/", "ReportSoapHeader_Server"));
        return headers;
    }

    public boolean handleMessage(SOAPMessageContext context) {
        try {
            SOAPMessage soapMessage = context.getMessage();
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            Source sc = soapMessage.getSOAPPart().getContent();

            ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(streamOut);
            tf.transform(sc, result);
            log(context);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        Boolean outboundProperty = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        boolean result = true;

        if (outboundProperty.booleanValue() == false) {
            result = checkIncomingMesssage(context);
        }

        return result;
    }

    public boolean handleFault(SOAPMessageContext context) {
        log(context);
//        logToSystemOut(context);
//        throw new UnsupportedOperationException("Not supported yet.");
        return true;
    }

    public void close(MessageContext context) {
    }

    private boolean checkIncomingMesssage(SOAPMessageContext context) {
        int i = 0;
        log(context);
        try {
//            SOAPEnvelope envelope = context.getMessage().getSOAPPart().getEnvelope();
//            SOAPHeader header = envelope.getHeader();
//            Iterator it = header.getChildElements();
//
//            while (it.hasNext()) {
//                SOAPHeaderElement element = (SOAPHeaderElement) it.next();
//                Iterator i2 = element.getChildElements();
//                while (i2.hasNext()) {
//                    SOAPElement e = (SOAPElement) i2.next();
//                    if ("Username".equals(e.getNodeName()) && "vss".equals(e.getValue())) {
//                        i++;
//                    } else if ("Password".equals(e.getNodeName()) && "vss@123".equals(e.getValue())) {
//                        i++;
//                    }
//                }
//            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return true;
    }

    private void log(SOAPMessageContext messageContext) {
        SOAPMessage msg = messageContext.getMessage(); //Line 1
        try {
            msg.writeTo(System.out);  //Line 3
        } catch (SOAPException ex) {
        } catch (IOException ex) {
        }
    }
}
