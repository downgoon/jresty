/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package io.downgoon.jresty.data.orm.dao.util;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import io.downgoon.jresty.data.orm.dao.example.pojo.EmployeeAnnotation;
import io.downgoon.jresty.data.orm.dao.util.PojoReflection;

/**
 * @title PojoReflectionTest
 * @description POJO反射测试
 * @author liwei39
 * @date 2014-8-18
 * @version 1.0
 */
public class PojoReflectionTest {

    private EmployeeAnnotation pojoBean;

    private PojoReflection pojoReflection = new PojoReflection();

    @Before
    public void setUp() throws Exception {
        pojoBean = new EmployeeAnnotation(2L, "steven", 35, true, "attachment");
    }

    @Test
    public void testDoSetter() {
        Assert.assertEquals("steven", pojoBean.getName());
        pojoReflection.doSetter(pojoBean, "name", "stevenV2");
        Assert.assertEquals("stevenV2", pojoBean.getName());
    }

    @Test
    public void testDoGetter() {
        Assert.assertEquals("steven", pojoBean.getName());
        Assert.assertEquals("steven", pojoReflection.doGetter(pojoBean, "name"));

        Assert.assertEquals(2L, pojoReflection.doGetter(pojoBean, "id"));
        Assert.assertEquals(35, pojoReflection.doGetter(pojoBean, "age"));
        Assert.assertEquals(true, pojoReflection.doGetter(pojoBean, "convention"));
        Assert.assertEquals("attachment", pojoReflection.doGetter(pojoBean, "attachment"));
    }

    @Test
    public void testNewInstance() {
        Object newObj = pojoReflection.newInstance(pojoBean.getClass());
        Assert.assertNotNull(newObj);
        Assert.assertTrue(newObj instanceof EmployeeAnnotation);
    }

    @Test
    public void testAttriType() {
        Class<?> pojoClass = pojoBean.getClass();

        Assert.assertEquals(String.class, pojoReflection.attriType(pojoClass, "name"));
        Assert.assertEquals(Long.class, pojoReflection.attriType(pojoClass, "id"));
        Assert.assertEquals(Integer.class, pojoReflection.attriType(pojoClass, "age"));
        Assert.assertEquals(Boolean.class, pojoReflection.attriType(pojoClass, "convention"));
        Assert.assertEquals(String.class, pojoReflection.attriType(pojoClass, "attachment"));
    }

}
