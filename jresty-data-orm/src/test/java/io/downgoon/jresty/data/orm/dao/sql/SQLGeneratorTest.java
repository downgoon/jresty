/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package io.downgoon.jresty.data.orm.dao.sql;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.downgoon.jresty.data.orm.dao.example.pojo.EmployeeAnnotation;
import io.downgoon.jresty.data.orm.dao.example.pojo.EmployeeConvention;
import io.downgoon.jresty.data.orm.dao.example.pojo.ORMBeanNoKeys;
import io.downgoon.jresty.data.orm.dao.example.pojo.ORMBeanSpecialGet;
import io.downgoon.jresty.data.orm.dao.sql.HQL;
import io.downgoon.jresty.data.orm.dao.sql.SQLGenerator;

/**
 * @title SQLGeneratorTest
 * @description TODO 
 * @author liwei39
 * @date 2014-7-12
 * @version 1.0
 */
public class SQLGeneratorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SQLGeneratorTest.class);

    @Before
    public void setUp() throws Exception {

    }
    
    /**
     * test json escape
     * */
    @Test
    public void testConventionUpdateJSON() throws Exception {
        SQLGenerator orm = new SQLGenerator();
        EmployeeConvention ec = new EmployeeConvention(1L, "steven", 35, true, new Date(1404977018000L), "{'comments':'conmments in json'}",
                "hidden");

        String sql1 = orm.genSQLUpdate(ec);
        LOGGER.info("actual sql1: {}", sql1);

        Assert.assertEquals(
                AssertSortor
                        .sortByChar("UPDATE `employeeconvention` SET `name`='steven',`age`=35,`geneder`=1,`birthday`='2014-07-10 15:23:38',`remark`='{''comments'':''conmments in json''}' where `id`=1"),
                AssertSortor.sortByChar(sql1));

        ec.setAge(null);
        ec.setBirthday(null);
        String sql2 = orm.genSQLUpdate(ec);
        LOGGER.info("actual sql2: {}", sql2);
    }


    @Test
    public void testConventionUpdate() throws Exception {
        SQLGenerator orm = new SQLGenerator();
        EmployeeConvention ec = new EmployeeConvention(1L, "steven", 35, true, new Date(1404977018000L), "备注信息",
                "hidden");

        String sql1 = orm.genSQLUpdate(ec);
        LOGGER.info("actual sql1: {}", sql1);

        Assert.assertEquals(
                AssertSortor
                        .sortByChar("UPDATE `employeeconvention` SET `name`='steven',`age`=35,`geneder`=1,`birthday`='2014-07-10 15:23:38',`remark`='备注信息' where `id`=1"),
                AssertSortor.sortByChar(sql1));

        //有些字段不需要更新
        ec.setAge(null);
        ec.setBirthday(null);
        String sql2 = orm.genSQLUpdate(ec);
        LOGGER.info("actual sql2: {}", sql2);
//        AssertSortor.assertEqualsSQL(
//        		"UPDATE employeeconvention SET `name`='steven',`geneder`=1,`remark`='备注信息' where `id`=1",
//                sql2);
    }

    @Test
    public void testAnnotationUpdate() throws Exception {
        SQLGenerator orm = new SQLGenerator();
        EmployeeAnnotation ea = new EmployeeAnnotation(2L, "steven", 35, true, "attachment");

        String sql1 = orm.genSQLUpdate(ea);
        LOGGER.info("actual sql1: {}", sql1);
        Assert.assertEquals(
                AssertSortor
                        .sortByChar("UPDATE `my_bean_table` SET `my_id_field`=2,`my_age_field`=35,`convention`=1 where `my_name_field`='steven'"),
                AssertSortor.sortByChar(sql1));

        //有些字段不需要更新
        ea.setAge(null);
        String sql2 = orm.genSQLUpdate(ea);
        LOGGER.info("actual sql2: {}", sql2);
//        Assert.assertEquals(
//                AssertSortor
//                        .sortByChar("UPDATE `my_bean_table` SET `my_id_field`=2,`convention`=1 where `my_name_field`='steven'"),
//                AssertSortor.sortByChar(sql2));
    }

    @Test
    public void testWhereUpdate() throws Exception {
        SQLGenerator orm = new SQLGenerator();
        EmployeeAnnotation ea = new EmployeeAnnotation(2L, "steven", 35, true, "attachment");

        String sql1 = orm.genSQLUpdate(ea, "age", "my_id_field");
        LOGGER.info("testWhereUpdate actual sql1: {}", sql1);

        Assert.assertEquals(AssertSortor
                .sortByChar("UPDATE my_bean_table SET `convention`=1 where `my_age_field`=35 and `my_id_field`=2"),
                AssertSortor.sortByChar(sql1));

        //有些字段不需要更新
        ea.setAge(null);
        String sql2 = orm.genSQLUpdate(ea, "id");
        LOGGER.info("testWhereUpdate actual sql2: {}", sql2);

//        Assert.assertEquals(AssertSortor.sortByChar("UPDATE my_bean_table SET `convention`=1 where `my_id_field`=2"),
//                AssertSortor.sortByChar(sql2));
    }

    @Test
    public void testNoId() throws Exception {
        ORMBeanNoKeys beanNoKeys = new ORMBeanNoKeys();
        beanNoKeys.setId("CA1103");
        beanNoKeys.setOther("memo info");

        SQLGenerator orm = new SQLGenerator();

        try {
            String sql = orm.genSQLUpdate(beanNoKeys);
            LOGGER.info("testNoId sql: {}", sql);
            Assert.assertTrue(false);
        } catch (Exception iae) {
            Assert.assertTrue(true);
        }

    }

    @Test
    public void testSpecialGet() throws Exception {
        ORMBeanSpecialGet bean = new ORMBeanSpecialGet("CA1103", "memo info");
        SQLGenerator orm = new SQLGenerator();
        String sql = orm.genSQLUpdate(bean);
        LOGGER.info("testSpecialGet sql: {}", sql);
        Assert.assertEquals(-1, sql.indexOf("cat"));
//        Assert.assertEquals(
//                AssertSortor.sortByChar("UPDATE `ormbeanspecialget` SET `other`='memo info' where `id`='CA1103'"),
//                AssertSortor.sortByChar(sql));
        
//        AssertSortor.assertEqualsSQL(
//              "UPDATE `ormbeanspecialget` SET `other`='memo info' where `id`='CA1103'",sql);
    }

    @Test
    public void testInsert() throws Exception {
        SQLGenerator orm = new SQLGenerator();
        EmployeeAnnotation ea = new EmployeeAnnotation(2L, "steven", 35, true, "attachment");

        String sql = orm.genSQLInsert(ea);
        LOGGER.info("sql insert: {}", sql);
        Assert.assertEquals(
                AssertSortor
                        .sortByChar("INSERT INTO `my_bean_table`( `my_name_field`,`my_id_field`,`my_age_field`,`convention`) VALUES ('steven',2,35,1)"),
                AssertSortor.sortByChar(sql));

        ea.setAge(null);
        sql = orm.genSQLInsert(ea);
        LOGGER.info("sql insert v2: {}", sql);
//        Assert.assertEquals(
//                AssertSortor
//                        .sortByChar("INSERT INTO `my_bean_table`( `my_name_field`,`my_id_field`,`convention`) VALUES ('steven',2,1)"),
//                AssertSortor.sortByChar(sql));
    }

    @Test
    public void testSelect() throws Exception {
        SQLGenerator sqlGenerator = new SQLGenerator();
        EmployeeAnnotation ea = new EmployeeAnnotation(2L, "steven", 35, true, "attachment");
        String sql = sqlGenerator.genSQLSelect(ea);
        LOGGER.info("select sql: {}", sql);
        Assert.assertEquals(
                AssertSortor
                        .sortByChar("SELECT * FROM `my_bean_table` WHERE `my_name_field`='steven' and `my_id_field`=2 and `my_age_field`=35 and `convention`=1"),
                AssertSortor.sortByChar(sql));

        //WHERE条件减少
        ea.setAge(null);
        ea.setAge(null);
        ea.setId(null);
        sql = sqlGenerator.genSQLSelect(ea);
        LOGGER.info("select sql: {}", sql);
//        Assert.assertEquals(AssertSortor
//                .sortByChar("SELECT * FROM `my_bean_table` WHERE `my_name_field`='steven' and `convention`=1"),
//                AssertSortor.sortByChar(sql));

        sql = sqlGenerator.genSQLSelect(ea, HQL.orderBy("my_name_field"), HQL.limit(2, 5));
        LOGGER.info("select sql: {}", sql);
//        Assert.assertEquals(
//                AssertSortor
//                        .sortByChar("SELECT * FROM `my_bean_table` WHERE `my_name_field`='steven' and `convention`=1 ORDER BY `my_name_field` LIMIT 2,5"),
//                AssertSortor.sortByChar(sql));

    }

    @Test
    public void testSelectHQL() throws Exception {
        SQLGenerator sqlGenerator = new SQLGenerator();
        EmployeeAnnotation ea = new EmployeeAnnotation(2L, "steven", 35, true, "attachment");

        //WHERE条件减少
        ea.setAge(null);
        ea.setAge(null);
        ea.setId(null);

        //HQL的字段名既可以是JAVA属性名，又可以是DB列名
        String sql = sqlGenerator.genSQLSelect(ea, HQL.orderBy("my_name_field"), HQL.limit(2, 5),
                HQL.select("name", "age"));
        LOGGER.info("select sql: {}", sql);
        Assert.assertEquals(
                AssertSortor
                        .sortByChar("SELECT `my_name_field`, `my_age_field` FROM `my_bean_table` WHERE `my_name_field`='steven' and `convention`=1 ORDER BY `my_name_field` LIMIT 2,5"),
                AssertSortor.sortByChar(sql));

    }

    @Test
    public void testSelectKey() throws Exception {
        SQLGenerator sqlGenerator = new SQLGenerator();
        EmployeeAnnotation ea = new EmployeeAnnotation(2L, null, null, null, null);
        String sql = sqlGenerator.genSQLSelect(ea);
        LOGGER.info("select sql: {}", sql);
        Assert.assertEquals(AssertSortor.sortByChar("SELECT * FROM `my_bean_table` WHERE `my_id_field`=2"),
                AssertSortor.sortByChar(sql));
    }

    @Test
    public void testSelectField() throws Exception {
        SQLGenerator sqlGenerator = new SQLGenerator();
        EmployeeAnnotation ea = new EmployeeAnnotation(null, "steven", null, null, null);
        String sql = sqlGenerator.genSQLSelect(ea);
        LOGGER.info("select sql: {}", sql);
        Assert.assertEquals(AssertSortor.sortByChar("SELECT * FROM `my_bean_table` WHERE `my_name_field`='steven'"),
                AssertSortor.sortByChar(sql));
    }
}
