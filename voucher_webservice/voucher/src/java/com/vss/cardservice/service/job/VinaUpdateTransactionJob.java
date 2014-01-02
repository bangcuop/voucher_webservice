///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.vss.cardservice.service.job;
//
//import com.vss.cardservice.api.ITransactionService;
//import com.vss.cardservice.dto.Transaction;
//import java.util.List;
//import vcardserverproxy.VCardServerProxy;
//
///**
// *
// * @author zannami
// */
//public class VinaUpdateTransactionJob extends AbstractCronTriggerJob {
//
//    ITransactionService transactionService;
//
//    public void setTransactionService(ITransactionService transactionService) {
//        this.transactionService = transactionService;
//    }
//
//    public VinaUpdateTransactionJob() {
//        super();
//    }
//
//    @Override
//    public void excute() {
//        int totalRow = transactionService.countTransactionByDay(0, 2);
//        int page = totalRow / 50;
//        if (page * 50 < totalRow) {
//            page++;
//        }
//
//        for (int i = 0; i < page; i++) {
//            int start = 50 * i;
//            int end = start + 50;
//            List<Transaction> lst = transactionService.getListTransactionByDay(0, 2, start, end);
//            for (Transaction tran : lst) {
//            }
//        }
//    }
//}
