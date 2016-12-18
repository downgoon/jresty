/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package io.downgoon.jresty.data.orm.dao.example.pojo;

import io.downgoon.jresty.data.orm.annotation.ORMField;

/**
 * @title ORMBeanNoKeys
 * @description TODO 
 * @author liwei39
 * @date 2014-7-11
 * @version 1.0
 */
public class ORMBeanNoKeys {

    private String id;

    private String other;

    @ORMField(name = "id", isKey = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}
