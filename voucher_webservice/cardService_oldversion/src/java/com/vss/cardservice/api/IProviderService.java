/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.cardservice.api;

import com.vss.cardservice.dto.Provider;

/**
 *
 * @author thibt
 */
public interface IProviderService {

    public Provider loadProvider(Integer providerId);
}
