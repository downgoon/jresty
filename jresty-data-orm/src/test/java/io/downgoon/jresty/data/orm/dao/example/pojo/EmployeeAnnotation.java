/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package io.downgoon.jresty.data.orm.dao.example.pojo;

import io.downgoon.jresty.data.orm.annotation.ORMField;
import io.downgoon.jresty.data.orm.annotation.ORMTable;

/**
 * @title BeanAnnotation
 * @description 按指定的名称进行ORM 
 * @author liwei39
 * @date 2014-7-9
 * @version 1.0
 */

@ORMTable(name = "my_bean_table")
public class EmployeeAnnotation {

    //ORM 并且定义为普通字段
    private Long id;

    //ORM 并且定义为主键
    private String name;

    //ORM
    private Integer age;

    //普通约定俗成
    private Boolean convention;

    //JAVA域专用，跳过
    private String attachment;

    public EmployeeAnnotation() {
        super();
    }

    public EmployeeAnnotation(Long id, String name, Integer age, Boolean convention, String attachment) {
        super();
        this.id = id;
        this.name = name;
        this.age = age;
        this.convention = convention;
        this.attachment = attachment;
    }

    /**
     * 约定俗称的普通字段，被isKey=true，变成了KEY
     * */
    @ORMField(name = "my_name_field", isKey = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ORMField(name = "my_age_field")
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 约定俗称的KEY，被isKey=false，否定了。
     * */
    @ORMField(name = "my_id_field", isKey = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 没有特别ORM标记，则按约定进行转换。
     * */
    public Boolean isConvention() {
        return convention;
    }

    public void setConvention(Boolean convention) {
        this.convention = convention;
    }

    /**
     * 仅仅在JAVA域中使用的字段，可以跳过ORM
     * */
    @ORMField(name = "my_attachment_filed", isSkip = true)
    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

}
