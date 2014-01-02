/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vss.clientws.util;

import java.io.Serializable;

/**
 *
 * @author zannami
 */
public class ClassTypeMapping implements Serializable {

    Class cls;
    String nameSpaceURI;
    String localPart;

    public String getLocalPart() {
        return localPart;
    }

    public void setLocalPart(String localPart) {
        this.localPart = localPart;
    }

    public Class getCls() {
        return cls;
    }

    public void setCls(Class cls) {
        this.cls = cls;
    }

    public String getNameSpaceURI() {
        return nameSpaceURI;
    }

    public void setNameSpaceURI(String nameSpaceURI) {
        this.nameSpaceURI = nameSpaceURI;
    }
}
