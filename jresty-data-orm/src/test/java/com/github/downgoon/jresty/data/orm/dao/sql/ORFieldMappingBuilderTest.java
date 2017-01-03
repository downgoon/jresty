/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package com.github.downgoon.jresty.data.orm.dao.sql;

import java.io.PrintWriter;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.downgoon.jresty.data.orm.dao.example.Material;
import com.github.downgoon.jresty.data.orm.dao.example.pojo.EmployeeAnnotation;
import com.github.downgoon.jresty.data.orm.dao.example.pojo.EmployeeConvention;
import com.github.downgoon.jresty.data.orm.dao.example.pojo.ORMBeanSpecialGet;
import com.github.downgoon.jresty.data.orm.dao.sql.ORFieldMapping;
import com.github.downgoon.jresty.data.orm.dao.sql.ORFieldMappingBuilder;
import junit.framework.Assert;

/**
 * @title ORFieldMappingBuilderTest
 * @description TODO 
 * @author liwei39
 * @date 2014-7-18
 * @version 1.0
 */
public class ORFieldMappingBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ORFieldMappingBuilderTest.class);

    private EmployeeAnnotation pojoBeanAnnotation;

    private EmployeeConvention pojoBeanConvention;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        pojoBeanAnnotation = new EmployeeAnnotation(2L, "steven", 35, true, "attachment");

        pojoBeanConvention = new EmployeeConvention(2L, "steven", 35, true, new Date(1408371187000L), "remark",
                "hidden");
    }

    @Test
    public void testBuildFieldMappingAnnotation() {
        ORFieldMappingBuilder builder = new ORFieldMappingBuilder(pojoBeanAnnotation.getClass());
        ORFieldMapping fieldMapping = builder.buildFieldMapping();

        LOGGER.info("field mapping: {}", fieldMapping.getJava2Db());
        LOGGER.info("field mapping sorted: {}", AssertSortor.sortMapString(fieldMapping.getJava2Db()));

        //类的属性的反射顺序在不同机器上不一致，所以断言前先对KEY排序
        Assert.assertEquals("{age=my_age_field, convention=convention, id=my_id_field, name=my_name_field}",
                AssertSortor.sortMapString(fieldMapping.getJava2Db()).toString());

    }

    @Test
    public void testBuildFieldMappingConvention() {
        ORFieldMappingBuilder builder = new ORFieldMappingBuilder(pojoBeanConvention.getClass());
        ORFieldMapping fieldMapping = builder.buildFieldMapping();

        LOGGER.info("field mapping: {}", fieldMapping.getJava2Db());
        LOGGER.info("field mapping sorted: {}", AssertSortor.sortMapString(fieldMapping.getJava2Db()));

        //类的属性的反射顺序在不同机器上不一致，所以断言前先对KEY排序
        Assert.assertEquals("{age=age, birthday=birthday, geneder=geneder, id=id, name=name, remark=remark}",
                AssertSortor.sortMapString(fieldMapping.getJava2Db()).toString());

    }

    @Test
    public void testSpecialGet() {
        ORMBeanSpecialGet sg = new ORMBeanSpecialGet("99", "other_value");
        /* String getCat(String c1, String c2) 非Getter的get方法会被忽略掉 */

        ORFieldMappingBuilder builder = new ORFieldMappingBuilder(sg.getClass());
        ORFieldMapping fieldMapping = builder.buildFieldMapping();

        LOGGER.info("field mapping specail: {}", fieldMapping.getJava2Db());
        LOGGER.info("field mapping specail sorted: {}", AssertSortor.sortMapString(fieldMapping.getJava2Db()));

        Assert.assertEquals("{id=id, other=other}", AssertSortor.sortMapString(fieldMapping.getJava2Db()).toString());

    }
    
    @Test
    public void testPrint() {
    	ORFieldMapping orfm = new ORFieldMappingBuilder(Material.class).buildFieldMapping();
    	orfm.print(new PrintWriter(System.out));
    }

}
