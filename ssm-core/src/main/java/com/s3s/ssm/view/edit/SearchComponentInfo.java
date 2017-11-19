/*
 * SearchComponentInfo
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

package com.s3s.ssm.view.edit;

import com.s3s.ssm.entity.AbstractBaseIdObject;
import com.s3s.ssm.view.component.ASearchComponent;

/**
 * @author Phan Hong Phuc
 * @since Apr 20, 2012
 */
public class SearchComponentInfo implements IComponentInfo {
    private ASearchComponent<? extends AbstractBaseIdObject> searchComponent;

    public SearchComponentInfo(ASearchComponent<? extends AbstractBaseIdObject> searchComponent) {
        this.searchComponent = searchComponent;
    }

    public ASearchComponent<? extends AbstractBaseIdObject> getSearchComponent() {
        return searchComponent;
    }

    public void setSearchComponent(ASearchComponent<? extends AbstractBaseIdObject> searchComponent) {
        this.searchComponent = searchComponent;
    }
}
