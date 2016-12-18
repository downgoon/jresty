/**
 * Baidu.com Inc. Copyright (c) 2000-2014 All Rights Reserved.
 */
package io.downgoon.jresty.data.orm.dao.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @title BaseCase
 * @description 依赖环境的单测的父类，负责加载环境
 * @author liwei39
 * @date 2014年7月16日
 * @version 1.0
 */
public class BaseCase {

    protected static final ApplicationContext context;

    /*
    EmbeddedDatabaseFactory initDatabase 124 - Creating embedded database 'dataSourceH2'
    ResourceDatabasePopulator executeSqlScript 160 - Executing SQL script from class path resource [h2/fcv-schema.sql]
    ResourceDatabasePopulator executeSqlScript 215 - Done executing SQL script from class path resource [h2/fcv-schema.sql] in 4 ms.
    ResourceDatabasePopulator executeSqlScript 160 - Executing SQL script from class path resource [h2/fcv-data.sql]
    ResourceDatabasePopulator executeSqlScript 215 - Done executing SQL script from class path resource [h2/fcv-data.sql] in 5 ms.
     * */
    static {
        context = new ClassPathXmlApplicationContext("applicationContext-dao-test.xml");
    }

}
