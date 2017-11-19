/*
 * UploadFile
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
package com.sunrise.xdoc.entity.config;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.s3s.ssm.entity.AbstractIdOLObject;

@Entity
@Table(name = "config_upload_file")
public class UploadFile extends AbstractIdOLObject {
	private static final long serialVersionUID = 1L;
	private String title;
	private byte[] data;

	@Column(name = "title", length = 128)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "content")
	@Lob
	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
