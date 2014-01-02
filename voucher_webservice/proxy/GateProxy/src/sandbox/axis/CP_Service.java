/**
 * CP_Service.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package sandbox.axis;

public interface CP_Service extends javax.xml.rpc.Service {
    public java.lang.String getCPPortAddress();

    public sandbox.axis.CP_PortType getCPPort() throws javax.xml.rpc.ServiceException;

    public sandbox.axis.CP_PortType getCPPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
