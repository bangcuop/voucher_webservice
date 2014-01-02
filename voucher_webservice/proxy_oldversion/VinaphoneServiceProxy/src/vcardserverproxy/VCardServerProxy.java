package vcardserverproxy;

import com.elcom.vchgws.message.ChangeMPINResponse;
import com.elcom.vchgws.message.ChangePasswordResponse;
import com.elcom.vchgws.message.LoadResponse;
import com.elcom.vchgws.message.LoginResponse;
import com.elcom.vchgws.message.LogoutResponse;
import com.elcom.vchgws.message.TodayEnquiryResponse;
import java.util.ResourceBundle;

public class VCardServerProxy extends WebserviceProxy {

    static final String RESOURCE = "WSConfig";
    static ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE);
    private static String userName;
    private static String agentID;
    private static String mPin;
    private static String pass;
    private static String target;

    public static ChangeMPINResponse changeMpin(String sessionid, String newMPIN)
            throws Exception {
        tripleDES app = new tripleDES();
        tripleDES.key = tripleDES.createKey(sessionid);
        String oldMPINTripleDES = tripleDES.encrypt(mPin);
        String newMPINTripleDES = tripleDES.encrypt(newMPIN);
        return getVCardServerPortType().changeMpin(userName, agentID, oldMPINTripleDES, newMPINTripleDES);
    }

    public static LogoutResponse logout(String sessinid) throws Exception {
        return getVCardServerPortType().logout(sessinid);
    }

    public static LoginResponse login() throws Exception {
        String passSHA = StringUtil.encrypt(pass, "SHA");
        return getVCardServerPortType().login(userName, passSHA, agentID);
    }

    public static LoadResponse loadNew(String sessionid, String maSoBiMat, String account, int counter, String cardSerial) throws Exception {
        tripleDES app = new tripleDES();
        tripleDES.key = tripleDES.createKey(sessionid);
        String agentMPIN = tripleDES.encrypt(mPin);
        String maSoBiMatTripleDES = tripleDES.encrypt(maSoBiMat);
        return getVCardServerPortType().loadNew(userName, agentID, agentMPIN, account, maSoBiMatTripleDES, counter, cardSerial);
    }

    public static LoadResponse load(String sessionid, String maSoBiMat, String account, int counter) throws Exception {
        tripleDES app = new tripleDES();
        tripleDES.key = tripleDES.createKey(sessionid);
        String agentMPIN = tripleDES.encrypt(mPin);
        String maSoBiMatTripleDES = tripleDES.encrypt(maSoBiMat);
        return getVCardServerPortType().load(userName, agentID, agentMPIN, account, maSoBiMatTripleDES, counter);
    }

    public static TodayEnquiryResponse todayEnquiry(String sessionid) throws Exception {
        tripleDES app = new tripleDES();
        tripleDES.key = tripleDES.createKey(sessionid);
        String mPinTripleDES = tripleDES.encrypt(mPin);
        return getVCardServerPortType().todayEnquiry(userName, agentID, mPinTripleDES);
    }

    public static ChangePasswordResponse changePassword(String newPass) throws Exception {
        String passSHA = StringUtil.encrypt(pass, "SHA");

        return getVCardServerPortType().changePassword(userName, agentID, passSHA, newPass);
    }

    public static void main(String[] args) {
        try {
//       String sessionid = "230fa82f7f00000101bd06bfbf2a676a7f00000101bd06bf";
//       int counter = 4;
//       String maSoBiMat = "428392697389";
//
//       LogoutResponse logout = logout(sessionid);
//
//       System.out.println("============> status :" + logout.getStatus());
//       System.out.println("============> message :" + (String)logout.getMessage().getValue());
//       System.out.println("============> transid :" + logout.getTransid());
            LoginResponse response = login();
            String session = response.getSessionid().getValue();
            System.out.println(session);
            LoadResponse loadRes = load(session, "", "216411585140", 1657687879);
            System.out.println("serialNumber " + loadRes.getSSerialNumber().getValue());
            System.out.println("transRefId " + loadRes.getTransid());
            System.out.println("message " + loadRes.getMessage().getValue());
//            System.out.println("serialNumber " + loadRes.getSSerialNumber().getValue());
//            System.out.println("serialNumber " + loadRes.getSSerialNumber());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle("vcardserverproxy.WSConfig");
        }

        userName = bundle.getString("userName-vinaphone");
        agentID = bundle.getString("agentID-vinaphone");
        mPin = bundle.getString("mPin-vinaphone");
        pass = bundle.getString("pass-vinaphone");
        target = bundle.getString("target-vinaphone");
    }
}

/* Location:           /media/sda2/Projects/cardService/lib/vcardServerProxy.jar
 * Qualified Name:     vcardserverproxy.VCardServerProxy
 * JD-Core Version:    0.6.0
 */
