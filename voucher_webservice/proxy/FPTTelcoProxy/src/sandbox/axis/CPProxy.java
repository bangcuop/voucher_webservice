package sandbox.axis;

public class CPProxy implements sandbox.axis.CP_PortType {
  private String _endpoint = null;
  private sandbox.axis.CP_PortType cP_PortType = null;
  
  public CPProxy() {
    _initCPProxy();
  }
  
  public CPProxy(String endpoint) {
    _endpoint = endpoint;
    _initCPProxy();
  }
  
  private void _initCPProxy() {
    try {
      cP_PortType = (new sandbox.axis.CP_ServiceLocator()).getCPPort();
      if (cP_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)cP_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
            _endpoint = (String)((javax.xml.rpc.Stub)cP_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (cP_PortType != null)
      ((javax.xml.rpc.Stub)cP_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public sandbox.axis.CP_PortType getCP_PortType() {
    if (cP_PortType == null)
      _initCPProxy();
    return cP_PortType;
  }
  
  public java.lang.String ProcessRequest(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException{
    if (cP_PortType == null)
      _initCPProxy();
    return cP_PortType.ProcessRequest(arg0, arg1);
  }
  
  
}