/**
 * CP_ServiceLocator.java
 *
 * This file was auto-generated from WSDL by the Apache Axis 1.4 Apr 22, 2006
 * (06:55:48 PDT) WSDL2Java emitter.
 */
package sandbox.axis;

import java.io.File;
import java.util.ResourceBundle;

public class CP_ServiceLocator extends org.apache.axis.client.Service implements sandbox.axis.CP_Service {

    public CP_ServiceLocator() {
    }

    public CP_ServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CP_ServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }
    // Use to get a proxy class for CPPort
   ResourceBundle configure = ResourceBundle.getBundle("wsconfig" + File.separator + "GATEProxy");
    private java.lang.String CPPort_address = configure.getString("gate_url");

    public java.lang.String getCPPortAddress() {
        return CPPort_address;
    }
    // The WSDD service name defaults to the port name.
    private java.lang.String CPPortWSDDServiceName = "CPPort";

    public java.lang.String getCPPortWSDDServiceName() {
        return CPPortWSDDServiceName;
    }

    public void setCPPortWSDDServiceName(java.lang.String name) {
        CPPortWSDDServiceName = name;
    }

    public sandbox.axis.CP_PortType getCPPort() throws javax.xml.rpc.ServiceException {
        System.out.println("======getCPPort() ------gate_url:" + CPPort_address);
        java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CPPort_address);
        } catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCPPort(endpoint);
    }

    public sandbox.axis.CP_PortType getCPPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            sandbox.axis.CPPortBindingStub _stub = new sandbox.axis.CPPortBindingStub(portAddress, this);
            _stub.setPortName(getCPPortWSDDServiceName());
            return _stub;
        } catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCPPortEndpointAddress(java.lang.String address) {
        CPPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation. If this service has
     * no port for the given interface, then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        System.out.println("======getPort(Class serviceEndpointInterface) ------gate_url:" + CPPort_address);
        try {
            if (sandbox.axis.CP_PortType.class.isAssignableFrom(serviceEndpointInterface)) {
                sandbox.axis.CPPortBindingStub _stub = new sandbox.axis.CPPortBindingStub(new java.net.URL(CPPort_address), this);
                _stub.setPortName(getCPPortWSDDServiceName());
                return _stub;
            }
        } catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation. If this service has
     * no port for the given interface, then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CPPort".equals(inputPortName)) {
            return getCPPort();
        } else {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ws/", "CP");
    }
    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ws/", "CPPort"));
        }
        return ports.iterator();
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {

        if ("CPPort".equals(portName)) {
            setCPPortEndpointAddress(address);
        } else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
     * Set the endpoint address for the specified port name.
     */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }
}
