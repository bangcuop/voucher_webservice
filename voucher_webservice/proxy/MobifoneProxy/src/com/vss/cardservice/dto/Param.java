package com.vss.cardservice.dto;
import java.io.Serializable;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

/**
 * 
 * @author zannami
 * 
 *         Jul 12, 2011 10:55:49 AM
 */
public class Param implements Serializable {
	String paramName;
	QName xmlType;
	ParameterMode paramMode;
	boolean encrypt;
	Object value;
	
	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Param(String paramName, QName xmlType, ParameterMode paramMode, Object value, boolean encrypt) {
		super();
		this.paramName = paramName;
		this.xmlType = xmlType;
		this.paramMode = paramMode;
		this.value = value;
		this.encrypt = encrypt;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public QName getXmlType() {
		return xmlType;
	}

	public void setXmlType(QName xmlType) {
		this.xmlType = xmlType;
	}

	public ParameterMode getParamMode() {
		return paramMode;
	}

	public void setParamMode(ParameterMode paramMode) {
		this.paramMode = paramMode;
	}

	public boolean isEncrypt() {
		return encrypt;
	}

	public void setEncrypt(boolean encrypt) {
		this.encrypt = encrypt;
	}
	
}
