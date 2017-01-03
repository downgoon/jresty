/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package com.github.downgoon.jresty.data.orm.dao.example.pojo;

/**
 * @title ORMBeanSpecialGet
 * @author liwei39
 * @date 2014-7-11
 * @version 1.0
 */
public class ORMBeanSpecialGet {

    private String id;

    private String other;

    public ORMBeanSpecialGet(String id, String other) {
        super();
        this.id = id;
        this.other = other;
    }

    //specail get method
    public String getCat(String c1, String c2) {
        return c1 + c2;
    }

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
