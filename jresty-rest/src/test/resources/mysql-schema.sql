create database if not exists pubsub CHARACTER SET=utf8  COLLATE=utf8_unicode_ci;
use pubsub;

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
--  Table structure for `application`
-- ----------------------------
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application` (
  `AppId` bigint(18) NOT NULL AUTO_INCREMENT COMMENT '应用Id',
  `ApplicationName` varchar(64) NOT NULL,
  `ApplicationPinyin` varchar(64) NOT NULL,
  `ApplicationIntro` varchar(255) NOT NULL COMMENT '应用描述',
  `CateId` int(11) NOT NULL COMMENT '所属分类',
  `CreateById` bigint(18) NOT NULL default 0,
  `CreateBy` varchar(64) NOT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT now(),
  `Status` tinyint(2) NOT NULL COMMENT '状态：待审核|已通过|已拒绝|删除',
  PRIMARY KEY (`AppId`),
  KEY `CateId` (`CateId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `application_category`
-- ----------------------------
DROP TABLE IF EXISTS `application_category`;
CREATE TABLE `application_category` (
  `CateId` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类Id',
  `CateName` varchar(64) NOT NULL COMMENT '分类名称',
  `CatePinyin` varchar(64) NOT NULL COMMENT '分类拼音',
  `ParentId` int(11) NOT NULL COMMENT '上级分类',
  `CreateById` bigint(18) NOT NULL default 0,
  `CreateBy` varchar(64) NOT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT now(),
  `Status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '0删除1正常',
  PRIMARY KEY (`CateId`),
  KEY `ParentId` (`ParentId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records of `application_category`
-- ----------------------------
BEGIN;
INSERT INTO `application_category` VALUES ('1', '飞凡', 'ffan', '0', '1111111111111111', '1400000000', '1');
COMMIT;

-- ----------------------------
--  Table structure for `logs`
-- ----------------------------
DROP TABLE IF EXISTS `logs`;
CREATE TABLE `logs` (
  `logId` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `operatorName` varchar(60) NOT NULL COMMENT '操作人名称',
  `operatorId` varchar(64) NOT NULL COMMENT '操作人id',
  `operatorTime` timestamp NOT NULL DEFAULT '1970-01-01 08:00:01' COMMENT '创建时间',
  `dbname` varchar(60) NOT NULL,
  `table` varchar(60) NOT NULL COMMENT 'table编号',
  `tableId` varchar(60) NOT NULL COMMENT 'table-item 编号',
  `contents` varchar(400) NOT NULL COMMENT '操作内容',
  PRIMARY KEY (`logId`) USING BTREE,
  KEY `dbname` (`dbname`,`table`,`tableId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11858 DEFAULT CHARSET=utf8 COMMENT='操作日志表';

-- ----------------------------
--  Table structure for `topics`
-- ----------------------------
DROP TABLE IF EXISTS `topics`;
CREATE TABLE `topics` (
  `TopicId` bigint(18) NOT NULL AUTO_INCREMENT,
  `AppId` bigint(18) NOT NULL,
  `CategoryId` int(11) NOT NULL COMMENT 'APP分类ID',
  `TopicName` varchar(64) NOT NULL COMMENT '主题名称',
  `TopicIntro` varchar(255) NOT NULL COMMENT '主题描述',
  `IDC` varchar(255) NOT NULL COMMENT '主题产生地（数据中心）',
  `CreateById` bigint(18) NOT NULL default 0,
  `CreateBy` varchar(64) NOT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT now(),
  `Status` tinyint(2) NOT NULL COMMENT '状态：正常|废弃',
  PRIMARY KEY (`TopicId`),
  KEY `AppId` (`AppId`),
  KEY `CategoryId` (`CategoryId`),
  KEY `TopicName` (`TopicName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `topics_subscriber`
-- ----------------------------
DROP TABLE IF EXISTS `topics_subscriber`;
CREATE TABLE `topics_subscriber` (
  `TopicId` bigint(18) NOT NULL,
  `AppId` bigint(18) NOT NULL,
  `TopicName` varchar(64) NOT NULL COMMENT '主题名称',
  `IDC` int(11) NOT NULL,
  `Callback` varchar(255) NOT NULL,
  `CreateById` bigint(18) NOT NULL default 0,
  `CreateBy` varchar(64) NOT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT now(),
  `Status` tinyint(2) NOT NULL COMMENT '状态：正常|废弃',
  PRIMARY KEY `subscribe` (`AppId`,`TopicId`),
  KEY `TopicName` (`TopicName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `topics_version`
-- ----------------------------
DROP TABLE IF EXISTS `topics_version`;
CREATE TABLE `topics_version` (
  `TopicId` bigint(18) NOT NULL,
  `VerId` int(11) NOT NULL COMMENT '主题版本ID',
  `Instance` tinytext NOT NULL COMMENT '主题消息实例',
  `InstanceIntro` tinytext NOT NULL COMMENT '实例描述',
  `CreateById` bigint(18) NOT NULL default 0,
  `CreateBy` varchar(64) NOT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT now(),
  `Status` tinyint(2) NOT NULL COMMENT '状态（使用中|已停用）',
  PRIMARY KEY `version` (`TopicId`, `VerId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `UserId` bigint(18) NOT NULL,
  `UserName` varchar(64) NOT NULL COMMENT 'CTX账号名称',
  `RealName` varchar(64) NOT NULL,
  `Mobile` varchar(32) NOT NULL,
  `Email` varchar(64) NOT NULL,
  `CreateById` bigint(18) NOT NULL default 0,
  `CreateBy` varchar(64) NOT NULL,
  `CreateTime` timestamp NOT NULL DEFAULT now(),
  `Status` tinyint(2) NOT NULL DEFAULT '1' COMMENT '正常',
  PRIMARY KEY (`UserId`),
  KEY `CtxName` (`CtxName`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息';

-- ----------------------------
--  Table structure for `user_access`
-- ----------------------------
DROP TABLE IF EXISTS `user_access`;
CREATE TABLE `user_access` (
  `UserId` bigint(18) NOT NULL,
  `Role` varchar(32) NOT NULL COMMENT '角色：admin(负责人)|developer(开发者)',
  `Group` int(11) NOT NULL COMMENT '组（APP分类Id）',
  `Apps` tinytext NOT NULL COMMENT '应用Id,多个逗号分隔',
  PRIMARY KEY (`UserId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户权限';


-- ----------------------------
--  Table structure for `news`
-- ----------------------------
DROP TABLE IF EXISTS `news`;
CREATE TABLE `news` (
  `NewsId` bigint(18) NOT NULL,
  `Title` varchar(32) NOT NULL COMMENT '',
  `CreateTime` timestamp NOT NULL DEFAULT now(),
  PRIMARY KEY (`NewsId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='测试表';



