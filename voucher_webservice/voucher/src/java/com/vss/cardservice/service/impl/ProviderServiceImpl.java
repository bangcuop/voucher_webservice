///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.vss.cardservice.service.impl;
//
//import com.vss.cardservice.api.IProviderService;
//import com.vss.cardservice.dto.Provider;
//import com.vss.cardservice.service.exception.CardServiceDBException;
//import com.vss.cardservice.service.util.BaseService;
//import java.sql.SQLException;
//
///**
// *
// * @author thibt
// */
//public class ProviderServiceImpl extends BaseService implements IProviderService {
//
//    public Provider loadProvider(Integer providerId) {
//        try {
//            return (Provider) mysqlMap.queryForObject("provider.loadProvider", providerId);
//        } catch (SQLException ex) {
//            throw new CardServiceDBException(ex);
//        }
//    }
//}
