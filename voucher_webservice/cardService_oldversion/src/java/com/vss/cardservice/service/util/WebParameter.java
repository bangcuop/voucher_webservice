/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.vss.cardservice.dto.Partner;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * |author zannami
 */
public class WebParameter {

    public static String mobiSessionValue = null;
    public static ExecutorService executors = Executors.newCachedThreadPool();
    public static String vnptEpaySessionValue = null;
    public static boolean vinaFirstLogin = true;
    public static boolean loginAndUpdate = false;

    static {
        loginAndUpdate = (ServiceUtil.getString("loginAndUpdate") != null && ServiceUtil.getString("loginAndUpdate").equals("true"));
    }
    public static String vinaSessionValue = null;
    public static Long session_login_date = null;
    public static Long session_timeout = null;

    static {
        try {
            session_timeout = Long.valueOf(ServiceUtil.getString("session_timeout"));
        } catch (Exception e) {
            session_timeout = 20000l;
        }
    }
    public static Integer retry_login = 0;
    public static boolean invalidSession = false;
    public static Integer lock = 0; // start
    public static List<Partner> LIST_PARTNER = null;
    public static String CARD_CODE = "";
    public static int CARDCODE_RETRY_COUNT = 0;
    public static String THE_KHONG_TON_TAI = "00";
    public static String GIAO_DICH_THANH_CONG = "01";
    public static String THE_DA_DUOC_SU_DUNG = "03";
    public static String THE_DA_KHOA = "04";
    public static String THE_HET_HAN_SU_DUNG = "05";
    public static String THE_CHUA_DUOC_KICH_HOAT = "06";
    public static String THUC_HIEN_SAI_QUA_SO_LAN_CHO_PHEP = "07";
    public static String GIAO_DICH_NGHI_VAN = "08";
    public static String SAI_DINH_DANG_THONG_TIN = "09";
    public static String PARTNER_KHONG_TON_TAI = "10";
    public static String PARTNER_BI_KHOA = "11";
    public static String HE_THONG_DANG_BAN = "13";
    public static String SAI_PASSWORD = "14";
    public static String SAI_DIA_CHI_IP = "15";
    public static String SAI_CHU_KY = "16";
    public static String SAI_DO_DAI_MA_THE = "20";
    public static String SO_HIEU_GIAO_DICH_KHONG_HOP_LE = "21";
    // ap dung voi vinaphone, khi the da duoc goi it nhat 1 lan trong ngay
    public static String THE_DANG_XU_LY = "22";
    public static String SERIAL_KHONG_HOP_LE = "23";
    public static String MATHE_SERIAL_KHONG_KHOP = "24";
    public static String TRUNG_MA_GIAO_DICH = "25";
    public static String MA_GIAO_DICH_KHONG_TON_TAI = "26";
    public static String THE_KHONG_DUNG_DINH_DANG = "28";
    public static String LOI_KET_NOI_SERVER = "40";
    public static String LOI_GOI_HAM_PROVIDER = "41";
    public static String LOI_KHONG_XAC_DINH = "99";
    public static String PARTNER_HOP_LE = "50";
    public static String MA_DICH_VU_KHONG_HOP_LE = "51";
    public static String DICH_VU_CHUA_DUOC_HO_TRO = "52";
    //
    /**
     *
     */
    public static String[] GIAO_DICH_CHUA_XAC_THUC = new String[]{"0", "Giao dich chua xac thuc"};
    public static String[] GIAO_DICH_DA_XAC_THUC = new String[]{"1", "Giao dich da xac thuc"};
    public static final String INVALID_UPDATE = "0|";
    public static final String VALID_UPDATE = "1";
}
