/*
 * AbstractCodeOLObject
 * 
 * Project: SSM
 * 
 * Copyright 2010 by HBASoft
 * All rights reserved.
 * 
 * This software is the confidential and proprietary information
 * of HBASoft. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license
 * agreements you entered into with HBASoft.
 */
package com.s3s.ssm.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@MappedSuperclass
public abstract class AbstractCodeOLObject extends AbstractIdOLObject implements
		ICodeObject {
	private static final long serialVersionUID = -6181674957253464888L;
	private String code;

	@Override
	@Column(name = "code", length = 32, unique = true, nullable = false)
	public String getCode() {
		return this.code;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Transient
	@Override
	public String toString() {
		return getCode();
	}
}
