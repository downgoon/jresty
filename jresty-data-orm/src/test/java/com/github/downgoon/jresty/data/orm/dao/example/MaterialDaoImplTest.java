/**
 * Baidu.com Inc.
 * Copyright (c) 2000-2014 All Rights Reserved.
 */
package com.github.downgoon.jresty.data.orm.dao.example;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import junit.framework.Assert;

/**
 * @title MaterialDaoImplTest
 * @description 使用样例之测试用例
 * @author liwei39
 * @date 2014-7-11
 * @version 1.0
 */
public class MaterialDaoImplTest extends BaseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(MaterialDaoImplTest.class);

    private MaterialDao materialDao; // = context.getBean(MaterialDao.class);

    @Before
    public void setUp() {
        // 每次重新建表，这样所有的DAO单元测试都相互隔离
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext-dao-test.xml");
        materialDao = context.getBean(MaterialDao.class);
    }

    /**
     * 测试读取操作：数据依赖初始化的数据库
     * */
    @Test
    public void testSelect() {
        Material condi = new Material();
        condi.setMaterialId(1L); // 初始化的时候已经插入了ID=1的记录 见fcv-data.sql脚本
        Material found = materialDao.findObject(condi);
        /* CRUDDaoSupport findObject 72 - SQL: SELECT * FROM FC_Video.material WHERE `materialid`=1 */
        LOGGER.info("found by id: {}", found);
        Assert.assertNotNull(found);
        Assert.assertEquals(1L, found.getMaterialId().longValue());
        Assert.assertEquals("test material", found.getMaterialName());
    }

    @Test
    public void testSelectNoWhere() {
        Material condi = new Material();
        // No where cause
        Material found = materialDao.findObject(condi);
        /* CRUDDaoSupport findObject 72 - SQL: SELECT * FROM FC_Video.material WHERE `materialid`=1 */
        LOGGER.info("found by id: {}", found);
        Assert.assertNotNull(found);
        Assert.assertEquals(1L, found.getMaterialId().longValue());
        Assert.assertEquals("test material", found.getMaterialName());
    }
    
    /**
     * 测试插入操作
     * */
    @Test
    public void testInsertWithId() {
        long materialId = 998L;
        Material material = newMaterial(materialId, 430L);
        /*
         * SQL: INSERT INTO FC_Video.material(
         * `materialid`,`materialname`,`rawurl`,`width`,`height`,`videobitrate`,`thumbnail
         * `,`previewurl`,`auditstatus`,`createtime
         * `,`updatetime`,`userid`,`materialmd5`,`unpassreason`,`unpassotherreason
         * `,`isdel`,`duration`,`fileformat`,`framerate
         * `,`robotminorstatus`,`robotmajorstatus`,`robotmajortime`,`audittime`,`auditor`) VALUES
         * (999,'物料名称','http://bs.baidu.com/fcvideo/123.mp4',0,0,0,'http://thumbnail','http://previewurl',1,'2014-07-10
         * 15:23:38','2014-07-10
         * 15:23:38',430,'12345678901234567890123456789012',0,'unPassOtherReason',0,0.0,'mov,mp4',29.97,1,1,'2014-07-10
         * 15:23:38','2014-07-10 15:23:38',6789012)
         */
        int savedCnt = materialDao.saveObject(material);
        LOGGER.info("saved cnt: {}", savedCnt);
        Assert.assertEquals(1, savedCnt);

        try {
            int savedCnt2 = materialDao.saveObject(material);
            LOGGER.info("saved2 cnt: {}", savedCnt2);
            fail("DuplicateKeyException Expected");

        } catch (org.springframework.dao.DuplicateKeyException dke) {
            Assert.assertNotNull(dke);
        }
    }
    
    
    @Test
    public void testInsertAutoIncrement() {
        long materialId = 998L;
        Material material = newMaterial(materialId, 430L);
        material.setMaterialId(null); // AUTO_INCREMENT
        
        int savedCnt = materialDao.saveObject(material);
        LOGGER.info("saved cnt: {}", savedCnt);
        Assert.assertEquals(1, savedCnt);
        Long id1 = material.getMaterialId();
        
       
        Assert.assertEquals(3L, id1.longValue());

    }

    /**
     * 测试Update操作，数据依赖初始化的内嵌数据库
     * */
    @Test
    public void testUpdate() {
        Material condi = new Material();
        condi.setMaterialId(2L); // 初始化的时候已经插入了ID=1的记录 见fcv-data.sql脚本
        Material found = materialDao.findObject(condi);
        LOGGER.info("found by id: {}", found);
        Assert.assertNotNull(found);
        Assert.assertEquals(2L, found.getMaterialId().longValue());
        Assert.assertEquals("test material2", found.getMaterialName());

        condi.setMaterialName("test material2-V2");
        int updatedCnt = materialDao.updateObject(condi);
        LOGGER.info("updated cnt: {}", updatedCnt);
        Assert.assertEquals(1, updatedCnt);

        Material condi2 = new Material();
        condi2.setMaterialId(2L); // 初始化的时候已经插入了ID=1的记录 见fcv-data.sql脚本
        Material found2 = materialDao.findObject(condi2);
        LOGGER.info("updated name: {}", found2.getMaterialName());
        Assert.assertEquals("test material2-V2", found2.getMaterialName());
    }

    /**
     * 测试插入操作：先插入一个记录，然后读取，并断言
     * */
    @Test
    public void testInsertAndSelect() {
        long materialId = 999L;
        Material material = newMaterial(materialId, 430L);
        /*
         * SQL: INSERT INTO FC_Video.material(
         * `materialid`,`materialname`,`rawurl`,`width`,`height`,`videobitrate`,`thumbnail
         * `,`previewurl`,`auditstatus`,`createtime
         * `,`updatetime`,`userid`,`materialmd5`,`unpassreason`,`unpassotherreason
         * `,`isdel`,`duration`,`fileformat`,`framerate
         * `,`robotminorstatus`,`robotmajorstatus`,`robotmajortime`,`audittime`,`auditor`) VALUES
         * (999,'物料名称','http://bs.baidu.com/fcvideo/123.mp4',0,0,0,'http://thumbnail','http://previewurl',1,'2014-07-10
         * 15:23:38','2014-07-10
         * 15:23:38',430,'12345678901234567890123456789012',0,'unPassOtherReason',0,0.0,'mov,mp4',29.97,1,1,'2014-07-10
         * 15:23:38','2014-07-10 15:23:38',6789012)
         */
        int savedCnt = materialDao.saveObject(material);
        LOGGER.info("saved cnt: {}", savedCnt);
        Assert.assertEquals(1, savedCnt);

        Material condi = new Material();
        condi.setMaterialId(materialId);
        Material found = materialDao.findObject(condi);
        LOGGER.info("found: {}", found);
        Assert.assertNotNull(found);
        Assert.assertEquals(material.getUserId(), found.getUserId());
        Assert.assertEquals(material.getMaterialName(), found.getMaterialName());
        Assert.assertEquals(material.getFrameRate(), found.getFrameRate());
    }

    /**
     * 构建一个默认的物料对象
     * 
     * @param materialId 物料ID
     * @param userId 物料所属广告主ID
     * @return 返回取值默认的物料对象
     * */
    protected Material newMaterial(long materialId, long userId) {
        Material material = new Material();
        material.setMaterialId(materialId);
        material.setUserId(userId);
        material.setMaterialName("物料名称");
        material.setRawUrl("http://bs.baidu.com/fcvideo/123.mp4");
        material.setMaterialMD5("12345678901234567890123456789012");

        material.setFileFormat("mov,mp4");
        material.setDuration(0.0); // int->double
        material.setWidth(0);
        material.setHeight(0);
        material.setBitrate(0); // 码率
        material.setFrameRate(29.97); // 帧率
        material.setThumbnail("http://thumbnail");
        material.setPreviewUrl("http://previewurl");

        material.setRobotMinorStatus(1);
        material.setRobotMajorStatus(1);
        material.setRobotMajorTime(new Date(1404977018000L));

        material.setAuditStatus(1); // 等待审核
        material.setUnpassReason(0);
        material.setUnpassOtherReason("unPassOtherReason");

        material.setAuditTime(new Date(1404977018000L)); // 动态时间
        material.setAutitor(6789012);
        material.setCreateTime(new Date(1404977018000L));
        material.setUpdateTime(new Date(1404977018000L));
        material.setIsDel(0);
        return material;
    }

    /**
     * 测试支持手写（不依赖ORMapping）原生SQL
     * 
     * */
    @Test
    public void testRawSQL() {
        String rawSQL =
                String.format("select materialId,userId,materialName from FC_Video.material "
                        + "where materialid in (%d,%d) order by materialid ", 1, 2);
        List<Material> list = materialDao.findObjects(rawSQL, Material.class);

        LOGGER.info("testRawSQL : {}", list);
        Assert.assertNotNull(list);
        Assert.assertEquals(2, list.size());

        Assert.assertEquals("test material", list.get(0).getMaterialName());
        Assert.assertEquals("test material2", list.get(1).getMaterialName());

        Assert.assertNull(list.get(0).getFileFormat()); // 没筛选的字段为NULL
    }
    
    
    @Test 
    public void testRowSQLAny() {
    	List<MaterialStat> stats = materialDao.findObjectsAny("SELECT userid, count(userid) as count FROM FC_Video.material group by userid", MaterialStat.class);
    	Assert.assertEquals(1, stats.size());
    	Assert.assertEquals(2, stats.get(0).getCount().intValue()); // [MaterialStat [userId=430, count=2, extraInfo=null]]
    }
    
    @Test
    public void testDelete() {
        Material condi = new Material();
        condi.setMaterialId(1L); // 初始化的时候已经插入了ID=1的记录 见fcv-data.sql脚本
        Material found = materialDao.findObject(condi);
        /* CRUDDaoSupport findObject 72 - SQL: SELECT * FROM FC_Video.material WHERE `materialid`=1 */
        LOGGER.info("found by id: {}", found);
        Assert.assertNotNull(found);
        Assert.assertEquals(1L, found.getMaterialId().longValue());
        Assert.assertEquals("test material", found.getMaterialName());
        
        condi = new Material();
        condi.setMaterialId(1L); 
        int rows = materialDao.removeObjects(condi);
        LOGGER.info("delete rows: {}", rows);
        Assert.assertEquals(1, rows);
        found = materialDao.findObject(condi);
        Assert.assertNull(found);
    }
    
    public static class MaterialStat { // support ORMFiled Mapping
    	
    	private Long userId;
    	
    	private Integer count;
    	
    	private String extraInfo;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public Integer getCount() {
			return count;
		}

		public void setCount(Integer count) {
			this.count = count;
		}
		

		public String getExtraInfo() {
			return extraInfo;
		}

		public void setExtraInfo(String extraInfo) {
			this.extraInfo = extraInfo;
		}

		@Override
		public String toString() {
			return "MaterialStat [userId=" + userId + ", count=" + count + ", extraInfo=" + extraInfo + "]";
		}

    }
}
