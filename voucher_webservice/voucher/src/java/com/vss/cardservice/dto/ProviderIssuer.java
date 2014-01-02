/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.dto;

/**
 *
 * @author hoangha2503
 */
public class ProviderIssuer {

    private Integer providerId;
    private Integer issuerId;
    /**
     * Kieu chay
     * 0: Only
     * 1: Cluster
     * 2: Time
     * 3: Volume
     */
    private Integer runType = 0;

}
