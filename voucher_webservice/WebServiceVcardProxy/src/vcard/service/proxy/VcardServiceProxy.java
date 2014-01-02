package vcard.service.proxy;

import com.vss.vcard.dto.*;
import java.net.URL;
import java.util.*;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import net.java.dev.jaxb.array.StringArray;

public class VcardServiceProxy {

    private static WebServiceVcard service;

    public static WebServiceVcard getVcardWebService() throws Exception {
        if (service == null) {
            service = new WebServiceVcard_Service(new URL(ResourceBundle.getBundle("WSConfig").getString("ws-url-vcardService"))).getWebServiceVcardPort();
            Binding binding = ((BindingProvider) service).getBinding();
            List handlerList = binding.getHandlerChain();
            handlerList.add(new SoapHandler());
            binding.setHandlerChain(handlerList);
        }
        return service;
    }

//    public static Partner login(String arg0, String arg1)
//            throws Exception {
//        return getVcardWebService().login(arg0, arg1);
//    }
//
//    public static List<Partner> getAllListPartner(String searchKey, int start, int end) throws Exception {
//        return getVcardWebService().getAllListPartner(searchKey, start, end);
//    }
//
//    public static int getTotalPartner(String searchKey) throws Exception {
//        return getVcardWebService().getTotalPartner(searchKey);
//    }
//
//    public static int insertPartner(Partner partner) throws Exception {
//        return getVcardWebService().insertPartner(partner);
//    }
//
//    public static int updatePartner(Partner partner) throws Exception {
//        return getVcardWebService().updatePartner(partner);
//    }
//
//    public static int deletePartnerById(int partnerId) throws Exception {
//        return getVcardWebService().deletePartnerById(partnerId);
//    }
//
//    public static Partner getPartnerById(int partnerId) throws Exception {
//        return getVcardWebService().getPartnerById(partnerId);
//    }
//
//    public static void changePasswordMSSQL(int partnerId, String newPassWord) throws Exception {
//        getVcardWebService().changePasswordMSSQL(partnerId, newPassWord);
//    }
    public static List<Partner> getAllListPartnerMySql(String searchKey, Boolean isLock, Boolean isProvider, int start, int total) throws Exception {
        return getVcardWebService().getAllListPartnerMySql(searchKey, isLock, isProvider, start, total);
    }

    public static int getTotalPartnerMySql(String searchKey, Boolean isLock, Boolean isProvider) throws Exception {
        return getVcardWebService().getTotalPartnerMySql(searchKey, isLock, isProvider);
    }

    public static int insertPartnerMySql(Partner partner) throws Exception {
        return getVcardWebService().insertPartnerMySql(partner);
    }

    public static int updatePartnerMySql(Partner partner) throws Exception {
        return getVcardWebService().updatePartnerMySql(partner);
    }

    public static int deletePartnerByIdMySql(int partnerId) throws Exception {
        return getVcardWebService().deletePartnerByIdMySql(partnerId);
    }

    public static Partner getPartnerByIdMySql(int partnerId) throws Exception {
        return getVcardWebService().getPartnerByIdMySql(partnerId);
    }

    public static List<IssuerPartnerWrapper> getIssuerPartnerWrapperList(int viewType) throws Exception {
        return getVcardWebService().getIssuerPartnerWrapperList(viewType);
    }

    public static List<IssuerPartner> getIssuerPartnerList(int viewType) throws Exception {
        return getVcardWebService().getIssuerPartnerList(viewType);
    }

    public static void changePassword(int partnerId, String newPassWord) throws Exception {
        getVcardWebService().changePassword(partnerId, newPassWord);
    }

    public static void changeLockStatus(Integer partnerId, Integer issuerId, boolean isLock) throws Exception {
        getVcardWebService().changeLockStatus(partnerId, issuerId, isLock);
    }

    public static Integer updateIssuerPartner(List<IssuerPartner> issuerPartnerList) throws Exception {
        return getVcardWebService().updateIssuerPartner(issuerPartnerList);
    }

    public static List<Transaction> getTransactionListByPartner(Integer partnerId, Integer issuerId, Integer providerId, Integer par, Integer status, String fromDate, String toDate, Integer start, Integer total) throws Exception {
        return getVcardWebService().getTransactionListByPartner(partnerId, issuerId, providerId, par, status, fromDate, toDate, start, total);
    }

    public static Integer countTransactionListByPartner(Integer partnerId, Integer issuerId, Integer providerId, Integer par, Integer status, String fromDate, String toDate) throws Exception {
        return getVcardWebService().countTransactionListByPartner(partnerId, issuerId, providerId, par, status, fromDate, toDate);
    }

    public static List<Transaction> checkTransactionBySerial(Integer partnerId, Integer issuerId, String serial, Date fromDate, Date toDate, Integer start, Integer total) throws Exception {
        return getVcardWebService().checkTransactionBySerial(partnerId, issuerId, serial, convertDateToXMLGregorianCalendar(fromDate), convertDateToXMLGregorianCalendar(toDate));
    }

    public static Integer countTransactionBySerial(Integer partnerId, Integer issuerId, String serial, Date fromDate, Date toDate) throws Exception {
        throw new UnsupportedOperationException("Chuc nang nay da bi khoa");
    }

    public static List<CardVolume> getCardVolumeListByPartnerEachDay(Integer partnerId, Integer issuerId, String fromDate, String toDate, Integer start, Integer total) throws Exception {
        return getVcardWebService().getCardVolumeListByPartnerEachDay(partnerId, issuerId, fromDate, toDate, start, total);
    }

    public static Integer countCardVolumeListByPartnerEachDay(Integer partnerId, Integer issuerId, String fromDate, String toDate) throws Exception {
        return getVcardWebService().countCardVolumeListByPartnerEachDay(partnerId, issuerId, fromDate, toDate);
    }

    public static List<CardVolume> getCardVolumeList(Integer partnerId, Integer issuerId, String fromDate, String toDate, Integer start, Integer total) throws Exception {
        return getVcardWebService().getCardVolumeList(partnerId, issuerId, fromDate, toDate, start, total);
    }

    public static Integer countCardVolumeList(Integer partnerId, Integer issuerId, String fromDate, String toDate) throws Exception {
        return getVcardWebService().countCardVolumeList(partnerId, issuerId, fromDate, toDate);
    }

    public static List<ChartModel> getPartnerShareList(Integer issuerId, String fromDate, String toDate) throws Exception {
        return getVcardWebService().getPartnerShareList(issuerId, fromDate, toDate);
    }

    public static List<ChartModel> getTopPartnerList(Integer issuerId, Integer par, Integer size, boolean isTop, String fromDate, String toDate) throws Exception {
        return getVcardWebService().getTopPartnerList(issuerId, par, size, isTop, fromDate, toDate);
    }

    public static List<ChartModel> getIssuerShareList(String fromDate, String toDate) throws Exception {
        return getVcardWebService().getIssuerShareList(fromDate, toDate);
    }

    public static List<ChartModel> getParShareList(Integer partnerId, Integer issuerId, String fromDate, String toDate) throws Exception {
        return getVcardWebService().getParShareList(partnerId, issuerId, fromDate, toDate);
    }

    public static List<ChartModel> getTopParList(Integer issuerId, Integer size, boolean isTop, String fromDate, String toDate) throws Exception {
        return getVcardWebService().getTopParList(issuerId, size, isTop, fromDate, toDate);
    }

    public static List<ChartModel> getGrowthValueList(Integer issuerId, Integer partnerId, Integer par, Integer period, String fromDate, String toDate) throws Exception {
        return getVcardWebService().getGrowthValueList(issuerId, partnerId, par, period, fromDate, toDate);
    }

    public static List<CardVolume> getLatestCardVolumeList(Integer partnerId, Integer issuerId, Integer type, Integer size) throws Exception {
        return getVcardWebService().getLatestCardVolumeList(partnerId, issuerId, type, size);
    }

    public static List<Income> getIncomeListByIssuer(List<Integer> issuerIdList, String fromDate, String toDate) throws Exception {
        return getVcardWebService().getIncomeListByIssuer(issuerIdList, fromDate, toDate);
    }

    public static List<PartnerIncome> getPartnerIncomeListByPartnerAndIssuer(Integer partnerId, Integer issuerId, String fromDate, String toDate) throws Exception {
        return getVcardWebService().getPartnerIncomeListByPartnerAndIssuer(partnerId, issuerId, fromDate, toDate);
    }

    public static List<MonthIncome> getMonthIncomeList(Integer issuerId, Integer partnerId, String fromDate, String toDate) throws Exception {
        return getVcardWebService().getMonthIncomeList(issuerId, partnerId, fromDate, toDate);
    }

    public static List<Transaction> checkTransactionBySerialMySql(Integer partnerId, Integer issuerId, String serial, String transRefId, String fromDate, String toDate, Integer start, Integer total) throws Exception {
        return getVcardWebService().checkTransactionBySerialMySql(partnerId, issuerId, serial, transRefId, fromDate, toDate);
    }

    public static Integer countTransactionBySerialMySql(Integer partnerId, Integer issuerId, String serial, String fromDate, String toDate) throws Exception {
        throw new UnsupportedOperationException("Chuc nang nay da bi khoa");
    }

    public static List<Income> getPartnerIncome(Integer issuerId, Integer partnerId, String fromDate, String toDate) throws Exception {
        return getVcardWebService().getPartnerIncome(issuerId, partnerId, fromDate, toDate);
    }

    public static void updateTransaction(Transaction trans) throws Exception {
        getVcardWebService().updateTransaction(trans);
    }

    public static Transaction loadTransaction(Integer transactionId) throws Exception {
        return getVcardWebService().loadTransaction(transactionId);
    }

//    public static String[][] getCardVolumeListByPartner(Integer issuerId, Integer partnerId, String fromDate, String toDate) throws Exception {
//        List cardVolumeList = getVcardWebService().getCardVolumeListByPartner(issuerId, partnerId, fromDate, toDate);
//        List<Partner> partnerList = getAllListPartnerMySql("", null, false, 0, -1);
//        Integer partnerSize = Integer.valueOf(partnerList.size());
//        String[][] cardVolumeArray = new String[partnerSize.intValue() + 2][11];
//        int i = 0;
//        int k = 0;
//        boolean temp = true;
//        Long volume10 = new Long(0L);
//        Long volume20 = new Long(0L);
//        Long volume30 = new Long(0L);
//        Long volume50 = new Long(0L);
//        Long volume100 = new Long(0L);
//        Long volume200 = new Long(0L);
//        Long volume300 = new Long(0L);
//        Long volume500 = new Long(0L);
//        for (Partner partner : partnerList) {
//            String partnerCode = partner.getPartnerCode();
//            String[] cardVolume = new String[11];
//            cardVolume[0] = partnerCode;
//            temp = true;
//            Integer volume = Integer.valueOf(0);
//
//            Long value = new Long(0L);
//            while ((temp) && (i < cardVolumeList.size())) {
//                CardVolume cv = (CardVolume) cardVolumeList.get(i);
//                if (!cv.getPartnerName().equals(partnerCode)) {
//                    temp = false;
//                }
//                if (temp) {
//                    switch (cv.getPar().intValue()) {
//                        case 0:
//                            for (int c = 1; c < 11; c++) {
//                                cardVolume[c] = "0";
//                            }
//                            break;
//                        case 10000:
//                            volume10 = Long.valueOf(volume10.longValue() + cv.getVolume().intValue());
//                            cardVolume[1] = cv.getVolume().toString();
//                            break;
//                        case 20000:
//                            volume20 = Long.valueOf(volume20.longValue() + cv.getVolume().intValue());
//                            cardVolume[2] = cv.getVolume().toString();
//                            break;
//                        case 30000:
//                            volume30 = Long.valueOf(volume30.longValue() + cv.getVolume().intValue());
//                            cardVolume[3] = cv.getVolume().toString();
//                            break;
//                        case 50000:
//                            volume50 = Long.valueOf(volume50.longValue() + cv.getVolume().intValue());
//                            cardVolume[4] = cv.getVolume().toString();
//                            break;
//                        case 100000:
//                            volume100 = Long.valueOf(volume100.longValue() + cv.getVolume().intValue());
//                            cardVolume[5] = cv.getVolume().toString();
//                            break;
//                        case 200000:
//                            volume200 = Long.valueOf(volume200.longValue() + cv.getVolume().intValue());
//                            cardVolume[6] = cv.getVolume().toString();
//                            break;
//                        case 300000:
//                            volume300 = Long.valueOf(volume300.longValue() + cv.getVolume().intValue());
//                            cardVolume[7] = cv.getVolume().toString();
//                            break;
//                        case 500000:
//                            volume500 = Long.valueOf(volume500.longValue() + cv.getVolume().intValue());
//                            cardVolume[8] = cv.getVolume().toString();
//                    }
//
//                    volume = Integer.valueOf(volume.intValue() + cv.getVolume().intValue());
//                    value = Long.valueOf(value.longValue() + cv.getValue().longValue());
//                    i++;
//                }
//            }
//            cardVolume[9] = volume.toString();
//            cardVolume[10] = value.toString();
//            cardVolumeArray[k] = cardVolume;
//            k++;
//        }
//        String[] volumeArray = new String[11];
//        String[] valueArray = new String[11];
//        Long sumValue = new Long(0L);
//        volumeArray[1] = volume10.toString();
//        volumeArray[2] = volume20.toString();
//        volumeArray[3] = volume30.toString();
//        volumeArray[4] = volume50.toString();
//        volumeArray[5] = volume100.toString();
//        volumeArray[6] = volume200.toString();
//        volumeArray[7] = volume300.toString();
//        volumeArray[8] = volume500.toString();
//        volumeArray[9] = (volume10.longValue() + volume20.longValue() + volume30.longValue() + volume50.longValue() + volume100.longValue() + volume200.longValue() + volume300.longValue() + volume500.longValue() + "");
//        sumValue = new Long(volume10.longValue() * 10000L + volume20.longValue() * 20000L + volume30.longValue() * 30000L + volume50.longValue() * 50000L + volume100.longValue() * 100000L + volume200.longValue() * 200000L + volume300.longValue() * 300000L + volume500.longValue() * 500000L);
//        volumeArray[10] = sumValue.toString();
//        cardVolumeArray[k] = volumeArray;
//        k++;
//        valueArray[1] = (volume10.longValue() * 10000L + "");
//        valueArray[2] = (volume20.longValue() * 20000L + "");
//        valueArray[3] = (volume30.longValue() * 30000L + "");
//        valueArray[4] = (volume50.longValue() * 50000L + "");
//        valueArray[5] = (volume100.longValue() * 100000L + "");
//        valueArray[6] = (volume200.longValue() * 200000L + "");
//        valueArray[7] = (volume300.longValue() * 300000L + "");
//        valueArray[8] = (volume500.longValue() * 500000L + "");
//        valueArray[9] = sumValue.toString();
//        cardVolumeArray[k] = valueArray;
//        String[][] cardVolumeForEachPartner = new String[3][11];
//
//        if (partnerId != null) {
//            int h = 0;
//            for (int m = 0; m < cardVolumeArray.length; m++) {
//                if (!cardVolumeArray[m][1].equals("0")) {
//                    String[] strArray = new String[11];
//                    for (int j = 0; j < 11; j++) {
//                        strArray[j] = cardVolumeArray[m][j];
//                    }
//                    cardVolumeForEachPartner[h] = strArray;
//                    h++;
//                }
//            }
//
//            return cardVolumeForEachPartner;
//        }
//        return cardVolumeArray;
//    }
    public static String[][] getCardVolumeListByPartner(Integer issuerId, Integer partnerId, String fromDate, String toDate) throws Exception {
        List cardVolumeList = getVcardWebService().getCardVolumeListByPartner(issuerId, partnerId, fromDate, toDate);
        String listPartnerCode = "";
        String[] arrListPartnerCode = null;
        for (int j = 0; j < cardVolumeList.size(); j++) {
            CardVolume cardVolume = (CardVolume) cardVolumeList.get(j);
            if (!listPartnerCode.contains(cardVolume.getPartnerName())) {
                listPartnerCode += cardVolume.getPartnerName() + ";";
            }
        }
        if (listPartnerCode.length() > 0) {
            listPartnerCode = listPartnerCode.substring(0, listPartnerCode.length() - 1);
            arrListPartnerCode = listPartnerCode.split(";");
        }
        Integer partnerSize = Integer.valueOf(arrListPartnerCode.length);
        String[][] cardVolumeArray = new String[partnerSize.intValue() + 2][11];
        int i = 0;
        int k = 0;
        boolean temp = true;
        Long volume10 = new Long(0L);
        Long volume20 = new Long(0L);
        Long volume30 = new Long(0L);
        Long volume50 = new Long(0L);
        Long volume100 = new Long(0L);
        Long volume200 = new Long(0L);
        Long volume300 = new Long(0L);
        Long volume500 = new Long(0L);

        if (arrListPartnerCode != null) {
            for (int n = 0; n < arrListPartnerCode.length; n++) {
                String partnerCode = arrListPartnerCode[n];
                String[] cardVolume = new String[11];
                cardVolume[0] = partnerCode;
                temp = true;
                Integer volume = Integer.valueOf(0);

                Long value = new Long(0L);
                while ((temp) && (i < cardVolumeList.size())) {
                    CardVolume cv = (CardVolume) cardVolumeList.get(i);
                    if (!cv.getPartnerName().equals(partnerCode)) {
                        temp = false;
                    }
                    if (temp) {
                        switch (cv.getPar().intValue()) {
                            case 0:
                                for (int c = 1; c < 11; c++) {
                                    cardVolume[c] = "0";
                                }
                                break;
                            case 10000:
                                volume10 = Long.valueOf(volume10.longValue() + cv.getVolume().intValue());
                                cardVolume[1] = cv.getVolume().toString();
                                break;
                            case 20000:
                                volume20 = Long.valueOf(volume20.longValue() + cv.getVolume().intValue());
                                cardVolume[2] = cv.getVolume().toString();
                                break;
                            case 30000:
                                volume30 = Long.valueOf(volume30.longValue() + cv.getVolume().intValue());
                                cardVolume[3] = cv.getVolume().toString();
                                break;
                            case 50000:
                                volume50 = Long.valueOf(volume50.longValue() + cv.getVolume().intValue());
                                cardVolume[4] = cv.getVolume().toString();
                                break;
                            case 100000:
                                volume100 = Long.valueOf(volume100.longValue() + cv.getVolume().intValue());
                                cardVolume[5] = cv.getVolume().toString();
                                break;
                            case 200000:
                                volume200 = Long.valueOf(volume200.longValue() + cv.getVolume().intValue());
                                cardVolume[6] = cv.getVolume().toString();
                                break;
                            case 300000:
                                volume300 = Long.valueOf(volume300.longValue() + cv.getVolume().intValue());
                                cardVolume[7] = cv.getVolume().toString();
                                break;
                            case 500000:
                                volume500 = Long.valueOf(volume500.longValue() + cv.getVolume().intValue());
                                cardVolume[8] = cv.getVolume().toString();
                        }

                        volume = Integer.valueOf(volume.intValue() + cv.getVolume().intValue());
                        value = Long.valueOf(value.longValue() + cv.getValue().longValue());
                        i++;
                    }
                }
                cardVolume[9] = volume.toString();
                cardVolume[10] = value.toString();
                cardVolumeArray[k] = cardVolume;
                k++;
            }
        }
        String[] volumeArray = new String[11];
        String[] valueArray = new String[11];
        Long sumValue = new Long(0L);
        volumeArray[1] = volume10.toString();
        volumeArray[2] = volume20.toString();
        volumeArray[3] = volume30.toString();
        volumeArray[4] = volume50.toString();
        volumeArray[5] = volume100.toString();
        volumeArray[6] = volume200.toString();
        volumeArray[7] = volume300.toString();
        volumeArray[8] = volume500.toString();
        volumeArray[9] = (volume10.longValue() + volume20.longValue() + volume30.longValue() + volume50.longValue() + volume100.longValue() + volume200.longValue() + volume300.longValue() + volume500.longValue() + "");
        sumValue = new Long(volume10.longValue() * 10000L + volume20.longValue() * 20000L + volume30.longValue() * 30000L + volume50.longValue() * 50000L + volume100.longValue() * 100000L + volume200.longValue() * 200000L + volume300.longValue() * 300000L + volume500.longValue() * 500000L);
        volumeArray[10] = sumValue.toString();
        cardVolumeArray[k] = volumeArray;
        k++;
        valueArray[1] = (volume10.longValue() * 10000L + "");
        valueArray[2] = (volume20.longValue() * 20000L + "");
        valueArray[3] = (volume30.longValue() * 30000L + "");
        valueArray[4] = (volume50.longValue() * 50000L + "");
        valueArray[5] = (volume100.longValue() * 100000L + "");
        valueArray[6] = (volume200.longValue() * 200000L + "");
        valueArray[7] = (volume300.longValue() * 300000L + "");
        valueArray[8] = (volume500.longValue() * 500000L + "");
        valueArray[9] = sumValue.toString();
        cardVolumeArray[k] = valueArray;
        String[][] cardVolumeForEachPartner = new String[3][11];

        if (partnerId != null) {
            int h = 0;
            for (int m = 0; m < cardVolumeArray.length; m++) {
                if (!cardVolumeArray[m][1].equals("0")) {
                    String[] strArray = new String[11];
                    for (int j = 0; j < 11; j++) {
                        strArray[j] = cardVolumeArray[m][j];
                    }
                    cardVolumeForEachPartner[h] = strArray;
                    h++;
                }
            }

            return cardVolumeForEachPartner;
        }
        return cardVolumeArray;
    }

    public static Integer insertCard(Integer issuerId, Integer par) throws Exception {
        return getVcardWebService().insertCard(issuerId, par);
    }

    public static List<Card> getListCardByIssuerId(Integer issuerId, boolean includeError) throws Exception {
        return getVcardWebService().getListCardByIssuerId(issuerId, includeError);
    }

    public static void deleteCard(Integer cardId) throws Exception {
        getVcardWebService().deleteCard(cardId);
    }

    public static Integer insertIssuer(Issuer issuer) throws Exception {
        return getVcardWebService().insertIssuer(issuer);
    }

    public static Issuer getIssuer(Integer issuerId) throws Exception {
        return getVcardWebService().getIssuer(issuerId);
    }

    public static List<Issuer> getAllIssuer() throws Exception {
        return getVcardWebService().getAllIssuer();
    }

    public static void updateIssuer(Issuer issuer) throws Exception {
        getVcardWebService().updateIssuer(issuer);
    }

    public static void deleteIssuer(Integer issuerId) throws Exception {
        getVcardWebService().deleteIssuer(issuerId);
    }

    public static Long getSumValueByPartner(Integer partnerId, Integer issuerId, String fromDate, String toDate) throws Exception {
        return getVcardWebService().getSumValueByPartner(partnerId, issuerId, fromDate, toDate);
    }

    public static List<CardVolume> getCardVolumeByAllPartnerEachDay(Integer issuerId, Boolean isProvider, String fromDate, String toDate, Boolean isDetail, Integer start, Integer total) throws Exception {
        return getVcardWebService().getCardVolumeByAllPartnerEachDay(issuerId, isProvider, fromDate, toDate, isDetail, start, total);
    }

    public static Integer countCardVolumeByAllPartnerEachDay(Integer issuerId, Boolean isProvider, String fromDate, String toDate, Boolean isDetail) throws Exception {
        return getVcardWebService().countCardVolumeByAllPartnerEachDay(issuerId, isProvider, fromDate, toDate, isDetail);
    }

    public static String[][] getIncomeTableByProvider(String fromDate, String toDate, Boolean isDetail) throws Exception {
        List<StringArray> list = getVcardWebService().getIncomeTableByProvider(fromDate, toDate, isDetail);
        if (list.size() == 0) {
            return new String[0][0];
        }
        String[][] result = new String[list.size()][list.get(0).getItem().size()];
        for (int i = 0; i < list.size(); i++) {
            List<String> row = list.get(i).getItem();
            for (int j = 0; j < row.size(); j++) {
                result[i][j] = row.get(j);
            }
        }
        return result;
    }

    public static XMLGregorianCalendar convertDateToXMLGregorianCalendar(Date date) throws Exception {
        if (date == null) {
            return null;
        }
        GregorianCalendar gc = (GregorianCalendar) GregorianCalendar.getInstance();
        gc.setTime(date);
        DatatypeFactory dataTypeFactory = null;
        try {
            dataTypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException ex) {
            throw ex;
        }
        return dataTypeFactory.newXMLGregorianCalendar(gc);
    }

    public static List<Fee> getFeeList(Integer contractId) throws Exception {
        return getVcardWebService().getFeeList(contractId);
    }

    public static Integer insertFee(Fee fee) throws Exception {
        return getVcardWebService().insertFee(fee);
    }

    public static void updateFee(Fee fee) throws Exception {
        getVcardWebService().updateFee(fee);
    }

    public static void deleteFee(Integer feeId) throws Exception {
        getVcardWebService().deleteFee(feeId);
    }

    public static Fee getFee(Integer feeId) throws Exception {
        return getVcardWebService().getFee(feeId);
    }

    public static Float calculateFee(Integer partnerId, Integer issuerId, String fromDate, String toDate, Long money) throws Exception {
        return getVcardWebService().calculateFee(partnerId, issuerId, fromDate, toDate, money);
    }

    public static List<Fee> getFeeListByPartnerId(Integer partnerId) throws Exception {
        return getVcardWebService().getFeeListByPartnerId(partnerId);
    }

    public static List<Payment> getPaymentList(Integer partnerId, Boolean isProvider, Integer issuerId, Integer paymentStatus, String fromDate, String toDate, List<String> paymentDateList, boolean isAdmin, Integer start, Integer total) throws Exception {
        return getVcardWebService().getPaymentList(partnerId, isProvider, issuerId, paymentStatus, fromDate, toDate, paymentDateList, isAdmin, start, total);
    }

    public static Integer countPaymentList(Integer partnerId, Boolean isProvider, Integer issuerId, Integer paymentStatus, String fromDate, String toDate, List<String> paymentDateList, boolean isAdmin) throws Exception {
        return getVcardWebService().countPaymentList(partnerId, isProvider, issuerId, paymentStatus, fromDate, toDate, paymentDateList, isAdmin);
    }

    public static void updateStatus(Integer contractPeriodId, Integer paymentId, Integer currentStatus) throws Exception {
        getVcardWebService().updateStatus(contractPeriodId, paymentId, currentStatus);
    }

    public static void saveSendCompareReportTime(Integer paymentId, Integer type) throws Exception {
        getVcardWebService().saveSendCompareReportTime(paymentId, type);
    }

    public static void savePaymentRequestTime(Integer paymentId) throws Exception {
        getVcardWebService().savePaymentRequestTime(paymentId);
    }

    public static void savePaidTime(Integer contractPeriodId, Integer paymentId, Long amount, Float progress, Integer paymentMethod, String description) throws Exception {
        getVcardWebService().savePaidTime(contractPeriodId, paymentId, amount, progress, paymentMethod, description);
    }

    public static List<Payment> getNewPaymentList() throws Exception {
        return getVcardWebService().getNewPaymentList();
    }

    public static Integer countContractPeriodList(Integer partnerId, Boolean isProvider, Integer statusId, String fromDate, String toDate, List<String> paymentDateList) throws Exception {
        return getVcardWebService().countContractPeriodList(partnerId, isProvider, statusId, fromDate, toDate, paymentDateList);
    }

    public static List<ContractPeriod> getContractPeriodList(Integer partnerId, Boolean isProvider, Integer statusId, String fromDate, String toDate, List<String> paymentDateList, Integer start, Integer total) throws Exception {
        return getVcardWebService().getContractPeriodList(partnerId, isProvider, statusId, fromDate, toDate, paymentDateList, start, total);
    }

    public static ContractPeriod getContractPeriodListTotal(Integer partnerId, Boolean isProvider, Integer statusId, String fromDate, String toDate, List<String> paymentDateList) throws Exception {
        return getVcardWebService().getContractPeriodListTotal(partnerId, isProvider, statusId, fromDate, toDate, paymentDateList);
    }

    public static void updateEstimateValue(Integer contractPeriodId, Long estimateValue) throws Exception {
        getVcardWebService().updateEstimateValue(contractPeriodId, estimateValue);
    }

    public static Payment getPaymentListTotal(Integer partnerId, Boolean isProvider, Integer issuerId, Integer statusId, String fromDate, String toDate, List<String> paymentDateList) throws Exception {
        return getVcardWebService().getPaymentListTotal(partnerId, isProvider, issuerId, statusId, fromDate, toDate, paymentDateList);
    }

    public static Integer countCompareList(Integer partnerId, Integer agentId, Integer providerId, Integer issuerId, Integer statusId, String fromDate, String toDate) throws Exception {
        return getVcardWebService().countCompareList(partnerId, agentId, providerId, issuerId, statusId, fromDate, toDate);
    }

    public static List<Compare> getCompareList(Integer partnerId, Integer agentId, Integer providerId, Integer issuerId, Integer statusId, String fromDate, String toDate, Integer start, Integer total) throws Exception {
        return getVcardWebService().getCompareList(partnerId, agentId, providerId, issuerId, statusId, fromDate, toDate, start, total);
    }

    public static Compare getCompareListTotal(Integer partnerId, Integer agentId, Integer providerId, Integer issuerId, Integer statusId, String fromDate, String toDate) throws Exception {
        return getVcardWebService().getCompareListTotal(partnerId, agentId, providerId, issuerId, statusId, fromDate, toDate);
    }

    public static void updateCompare(Compare compare) throws Exception {
        getVcardWebService().updateCompare(compare);
    }

    public static void updateRealPaymentDate(List<Integer> compareIdList, String realProviderPaymentDate, String description) throws Exception {
        getVcardWebService().updateRealPaymentDate(compareIdList, realProviderPaymentDate, description);
    }

    public static void setCompareStartTime(Integer paymentId) throws Exception {
        throw new UnsupportedOperationException("Chuc nang nay da bi khoa");
    }

    public static void setCompareFinishTime(Integer paymentId, Integer result) throws Exception {
        throw new UnsupportedOperationException("Chuc nang nay da bi khoa");
    }

    public static Payment loadPayment(Integer paymentId) throws Exception {
        return getVcardWebService().loadPayment(paymentId);
    }

    public static List<PaymentHistory> getPaymentHistoryList(Integer contractPeriodId, Integer paymentId, Integer statusId) throws Exception {
        return getVcardWebService().getPaymentHistoryList(contractPeriodId, paymentId, statusId);
    }

    public static void updateCustomerValue(Integer paymentId, Long customerValue) throws Exception {
        throw new UnsupportedOperationException("Chuc nang nay da bi khoa");
    }

    public static List<PaymentStatus> getPaymentStatusList() throws Exception {
        return getVcardWebService().getPaymentStatusList();
    }

    public static void updatePayment(Payment payment) throws Exception {
        throw new UnsupportedOperationException("Chuc nang nay da bi khoa");
    }

    public static ContractPeriod loadContractPeriod(Integer contractPeriodId, Integer paymentId) throws Exception {
        return getVcardWebService().loadContractPeriod(contractPeriodId, paymentId);
    }

    public static void updateContractPeriod(ContractPeriod contractPeriod) throws Exception {
        getVcardWebService().updateContractPeriod(contractPeriod);
    }

    public static List<Contract> getContractList(Integer partnerId, Integer issuerId, Integer paymentPeriodId, Integer status, String contractNumber, boolean isCurrent) throws Exception {
        return getVcardWebService().getContractList(partnerId, issuerId, paymentPeriodId, status, contractNumber, isCurrent);
    }

    public static Integer insertContract(Contract contract) throws Exception {
        return getVcardWebService().insertContract(contract);
    }

    public static void deleteContract(Integer contractId) throws Exception {
        getVcardWebService().deleteContract(contractId);
    }

    public static Integer updateContract(Contract contract) throws Exception {
        return getVcardWebService().updateContract(contract);
    }

    public static void updateContractStatus(Integer contractId, Integer status) throws Exception {
        getVcardWebService().updateContractStatus(contractId, status);
    }

    public static Contract getContractById(Integer contractId) throws Exception {
        return getVcardWebService().getContractById(contractId);
    }

    public static List<Issuer> getIssuerListByContract(Integer contractId) throws Exception {
        return getVcardWebService().getIssuerListByContract(contractId);
    }

    public static List<CompareIssuer> getCompareIssuerList(Integer issuerId, Integer month, Integer year) throws Exception {
        return getVcardWebService().getCompareIssuerList(issuerId, month, year);
    }

    public static void updateCompareIssuerList(List<CompareIssuer> compareIssuerList) throws Exception {
        getVcardWebService().updateCompareIssuerList(compareIssuerList);
    }

    public static List<ComparePartner> getComparePartnerList(Integer paymentId) throws Exception {
        return getVcardWebService().getComparePartnerList(paymentId, null);
    }

    public static List<ComparePartner> getComparePartnerList(Integer paymentId, Integer viewType) throws Exception {
        return getVcardWebService().getComparePartnerList(paymentId, viewType);
    }

    public static void updateComparePartnerList(List<ComparePartner> comparePartnerList) throws Exception {
        getVcardWebService().updateComparePartnerList(comparePartnerList);
    }

    public static List<IssuerPartnerWrapper> getIssuerPartnerValueList(String fromDate, String toDate) throws Exception {
        return getVcardWebService().getIssuerPartnerValueList(fromDate, toDate);
    }

    public static List<String[][]> getTransactionErrorPercentReport(String fromDate, String toDate, Integer issuerId, Integer providerId, Integer partnerId) throws Exception {
        List<StringArrayDto> list = getVcardWebService().getTransactionErrorPercentReport(fromDate, toDate, issuerId, providerId, partnerId);
        List<String[][]> result = new ArrayList<String[][]>();
        for (StringArrayDto stringArrayDto : list) {
            List<StringArray> strList = stringArrayDto.getResult();
            String[][] arr = getStringArr2dFromStringArray(strList);
            result.add(arr);
        }
        return result;
    }

    public static String[][] getStringArr2dFromStringArray(List<StringArray> list) {
        if (list.isEmpty()) {
            return new String[0][0];
        }
        String[][] result = new String[list.size()][list.get(0).getItem().size()];
        for (int i = 0; i < list.size(); i++) {
            List<String> row = list.get(i).getItem();
            for (int j = 0; j < row.size(); j++) {
                result[i][j] = row.get(j);
            }
        }
        return result;
    }

    public static List<CardVolume> getCardVolumeListByAgent(Integer agentId, Integer isssuerId, String fromDate, String toDate, Integer start, Integer total)
            throws Exception {
        return getVcardWebService().getCardVolumeListByAgent(agentId, isssuerId, fromDate, toDate, start, total);
    }

    public static Integer countCardVolumeListByAgent(Integer agentId, Integer isssuerId, String fromDate, String toDate) throws Exception {
        return getVcardWebService().countCardVolumeListByAgent(agentId, isssuerId, fromDate, toDate);
    }

    public static List<Agent> getAgentList() throws Exception {
        return getVcardWebService().getAgentList();
    }

    public static Integer countAgentList() throws Exception {
        return getVcardWebService().countAgentList();
    }

    public static List<StatisticDay> getStatisticDayList(Integer partnerId, Integer issuerId, Integer cardId, Integer agentId, String fromDate, String toDate, Integer start, Integer total) throws Exception {
        return getVcardWebService().getStatisticDayList(partnerId, issuerId, cardId, agentId, fromDate, toDate, start, total);
    }

    public static String[][] getBaoCaoDoanhSo(Integer partnerId, String fromDate, String toDate) throws Exception {
        StringArrayDto table = getVcardWebService().getBaoCaoDoanhSo(partnerId, fromDate, toDate);
        String[][] result = getStringArr2dFromStringArray(table.getResult());
        return result;
    }

    public static List<String> getAllFormsOfPayment() throws Exception {
        return getVcardWebService().getAllFormsOfPayment();
    }

    public static String[][] getReportFormsOfPayment(String fromDate, String toDate, String formOfPayment, Integer issuerId) throws Exception {
        StringArrayDto table = getVcardWebService().getReportFormsOfPayment(fromDate, toDate, formOfPayment, issuerId);
        return getStringArr2dFromStringArray(table.getResult());
    }

    public static void main(String[] args) throws Exception {
        String httt = "7  + 1";
        Integer issuerId = 1;
        String fromDate = "01/07/2013";
        String toDate = "31/07/2013";
        String[][] result = getReportFormsOfPayment(fromDate, toDate, httt, issuerId);
        for (int i = 0; i < result.length; i++) {
            String[] strings = result[i];
            for (int j = 0; j < strings.length; j++) {
                String string = strings[j];
                System.out.print("| " + string);
            }
            System.out.println("");
        }
    }
}
