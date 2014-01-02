/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.api;

import com.vss.cardservice.dto.Issuer;
import java.util.List;

/**
 *
 * @author zannami
 */
public interface IIssuerService {

    List<Issuer> getListIssuer(Integer issuerId);

    void updateSessionValue(String name, String sessionValue);

    Issuer getIssuer(String name);
}
