/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package com.github.downgoon.jresty.data.orm.dao.sql;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.downgoon.jresty.data.orm.dao.example.pojo.EmployeeAnnotation;
import com.github.downgoon.jresty.data.orm.dao.sql.ORFieldMapping;
import com.github.downgoon.jresty.data.orm.dao.sql.ORMBuilderFacade;
import com.github.downgoon.jresty.data.orm.dao.sql.ORValueMapping;

/**
 * @title ORMBuilderFacadeTest
 * @description TODO 
 * @author liwei39
 * @date 2014-8-19
 * @version 1.0
 */
public class ORMBuilderFacadeTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ORMBuilderFacadeTest.class);

    private EmployeeAnnotation pojoBeanAnnotation;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        pojoBeanAnnotation = new EmployeeAnnotation(2L, "steven", 35, true, "attachment");
    }

    @Test
    public void testBuildFieldMapping() {
        ORMBuilderFacade builderFacade = new ORMBuilderFacade(pojoBeanAnnotation);
        ORFieldMapping fieldMapping = builderFacade.buildFieldMapping();

        LOGGER.info("field mapping: {}", fieldMapping.getJava2Db());
        LOGGER.info("field mapping sorted: {}", AssertSortor.sortMapString(fieldMapping.getJava2Db()));

        //类的属性的反射顺序在不同机器上不一致，所以断言前先对KEY排序
        Assert.assertEquals("{age=my_age_field, convention=convention, id=my_id_field, name=my_name_field}",
                AssertSortor.sortMapString(fieldMapping.getJava2Db()).toString());

        Assert.assertTrue(builderFacade.isFieldMappingCached());
        Assert.assertNotNull(builderFacade.getCachedFieldMapping());

        ORFieldMapping fieldMapping2 = builderFacade.buildFieldMapping();
        Assert.assertTrue(fieldMapping == fieldMapping2);//地址相等
    }

    @Test
    public void testBuildFieldMappingNoCache() {
        ORMBuilderFacade builderFacade = new ORMBuilderFacade(pojoBeanAnnotation, false);
        ORFieldMapping fieldMapping = builderFacade.buildFieldMapping();

        LOGGER.info("field mapping: {}", fieldMapping.getJava2Db());
        LOGGER.info("field mapping sorted: {}", AssertSortor.sortMapString(fieldMapping.getJava2Db()));

        //类的属性的反射顺序在不同机器上不一致，所以断言前先对KEY排序
        Assert.assertEquals("{age=my_age_field, convention=convention, id=my_id_field, name=my_name_field}",
                AssertSortor.sortMapString(fieldMapping.getJava2Db()).toString());

        Assert.assertFalse(builderFacade.isFieldMappingCached());
        Assert.assertNull(builderFacade.getCachedFieldMapping());

        ORFieldMapping fieldMapping2 = builderFacade.buildFieldMapping();
        Assert.assertFalse(fieldMapping == fieldMapping2);//地址相等
    }

    @Test
    public void testBuildValueMapping() {
        ORMBuilderFacade builderFacade = new ORMBuilderFacade(pojoBeanAnnotation, false);
        ORValueMapping valueMapping = builderFacade.buildValueMapping();

        LOGGER.info("field mapping: {}", valueMapping.getDbFieldKV());
        LOGGER.info("field mapping sorted: {}", AssertSortor.sortMapObject(valueMapping.getDbFieldKV()));

        //类的属性的反射顺序在不同机器上不一致，所以断言前先对KEY排序
        Assert.assertEquals("{convention=true, my_age_field=35, my_id_field=2}",
                AssertSortor.sortMapObject(valueMapping.getDbFieldKV()).toString());

        Assert.assertFalse(builderFacade.isFieldMappingCached());
        Assert.assertNull(builderFacade.getCachedFieldMapping());

    }

}
