/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package com.github.downgoon.jresty.data.orm.dao.sql;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.downgoon.jresty.data.orm.dao.example.pojo.EmployeeAnnotation;
import com.github.downgoon.jresty.data.orm.dao.example.pojo.EmployeeConvention;
import com.github.downgoon.jresty.data.orm.dao.sql.ORFieldMapping;
import com.github.downgoon.jresty.data.orm.dao.sql.ORFieldMappingBuilder;
import com.github.downgoon.jresty.data.orm.dao.sql.ORValueMapping;
import com.github.downgoon.jresty.data.orm.dao.sql.ORValueMappingBuilder;

/**
 * @title ORValueMappingBuilderTest
 * @description TODO 
 * @author liwei39
 * @date 2014-7-18
 * @version 1.0
 */
public class ORValueMappingBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ORValueMappingBuilderTest.class);

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
    public void testBuildValueMappingAnnotation() {
        ORFieldMappingBuilder fieldBuilder = new ORFieldMappingBuilder(pojoBeanAnnotation.getClass());
        ORFieldMapping fieldMapping = fieldBuilder.buildFieldMapping();
        ORValueMappingBuilder valueBuilder = new ORValueMappingBuilder(pojoBeanAnnotation, fieldMapping);
        ORValueMapping valueMapping = valueBuilder.buildValueMapping();

        LOGGER.info("field mapping: {}", valueMapping.getDbFieldKV());
        LOGGER.info("field mapping sorted: {}", AssertSortor.sortMapObject(valueMapping.getDbFieldKV()));

        //类的属性的反射顺序在不同机器上不一致，所以断言前先对KEY排序
        Assert.assertEquals("{convention=true, my_age_field=35, my_id_field=2}",
                AssertSortor.sortMapObject(valueMapping.getDbFieldKV()).toString());

    }

    @Test
    public void testBuildValueMappingConvention() {
        ORFieldMappingBuilder fieldBuilder = new ORFieldMappingBuilder(pojoBeanConvention.getClass());
        ORFieldMapping fieldMapping = fieldBuilder.buildFieldMapping();
        ORValueMappingBuilder valueBuilder = new ORValueMappingBuilder(pojoBeanConvention, fieldMapping);
        ORValueMapping valueMapping = valueBuilder.buildValueMapping();

        LOGGER.info("field mapping: {}", valueMapping.getDbFieldKV());
        LOGGER.info("field mapping sorted: {}", AssertSortor.sortMapObject(valueMapping.getDbFieldKV()));

        //类的属性的反射顺序在不同机器上不一致，所以断言前先对KEY排序
        Assert.assertEquals(
                "{age=35, birthday=Mon Aug 18 22:13:07 CST 2014, geneder=true, name=steven, remark=remark}",
                AssertSortor.sortMapObject(valueMapping.getDbFieldKV()).toString());

    }

}
