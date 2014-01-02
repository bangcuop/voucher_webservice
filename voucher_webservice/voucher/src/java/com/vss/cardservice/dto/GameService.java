/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.dto;

/**
 *
 * @author thang.tranquyet
 */
public class GameService {

    private Integer gameServiceId;
    private Integer partnerId;
    private String partnerCode;
    private String gameName;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Integer getGameServiceId() {
        return gameServiceId;
    }

    public void setGameServiceId(Integer gameServiceId) {
        this.gameServiceId = gameServiceId;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }
}
