/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.service.util;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author huyp
 */
public class BaseService {

    protected SqlMapClient mysqlMap;

    public void setMysqlMap(SqlMapClient mysqlMap) {
        this.mysqlMap = mysqlMap;
    }
}
