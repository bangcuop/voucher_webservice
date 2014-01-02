///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.vss.cardservice.service.job;
//
//import com.vss.cardservice.dto.Transaction;
//import com.vss.cardservice.service.util.CardServiceLocalServiceUtil;
//import java.util.List;
//
///**
// *
// * @author zannami
// */
//public class UpdateTransactionFailedJob extends AbstractCronTriggerJob {
//
//    public UpdateTransactionFailedJob() {
//        super();
//    }
//
//    @Override
//    public void excute() {
//        int count = 0;
//        int totalRow = CardServiceLocalServiceUtil.getTransactionService().countTransactionFailed();
//        int page = totalRow;
//        if (totalRow > 50) {
//            page = totalRow / 50;
//            if (page * 50 < totalRow) {
//                page++;
//            }
//        }
//        for (int i = 0; i < page; i++) {
//            int start = 50 * i;
//            int end = start + 50;
//            List<Transaction> trans = null;
//            try {
//                CardServiceLocalServiceUtil.getTransactionService().getListTransactionFailed(start, end);
//            } catch (Exception e) {
//                e.printStackTrace();
//                continue;
//            }
//            if (trans == null || trans.isEmpty()) {
//                continue;
//            }
//            for (Transaction t : trans) {
//                try {
//                    CardServiceLocalServiceUtil.getTransactionService().updateCardId(t);
//                    count++;
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    continue;
//                }
//            }
//        }
//        System.out.println(">>>>>>>>update total "+ count + 1 +" transaction");
//    }
//}
