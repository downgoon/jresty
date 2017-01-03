/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package com.github.downgoon.jresty.data.orm.dao.example.pojo;

import java.util.Date;

/**
 * @title EmployeeConvention
 * @description ORM的约定俗成规则（不包含Annotation的） 
 * @author liwei39
 * @date 2014-7-9
 * @version 1.0
 */
public class EmployeeConvention {

    //主键
    private Long id;

    //普通字段，String类型
    private String name;

    //普通字段，int类型
    private Integer age;

    //普通字段，Boolean类型
    private Boolean geneder;

    //普通字段，Date类型
    private Date birthday;

    //普通字段，Object类型
    private Object remark;

    String hidden;

    public EmployeeConvention(Long id, String name, int age, boolean geneder, Date birthday, Object remark,
            String hidden) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.geneder = geneder;
        this.birthday = birthday;
        this.remark = remark;
        this.hidden = hidden;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    //注意：Boolean 的Getter方法是isXXX形式
    public Boolean isGeneder() {
        return geneder;
    }

    public void setGeneder(Boolean geneder) {
        this.geneder = geneder;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Object getRemark() {
        return remark;
    }

    public void setRemark(Object remark) {
        this.remark = remark;
    }

    String getHidden() {
        return hidden;
    }

    void setHidden(String hidden) {
        this.hidden = hidden;
    }

}
