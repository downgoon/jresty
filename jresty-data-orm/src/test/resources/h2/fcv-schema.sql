DROP SCHEMA IF EXISTS FC_Video;
CREATE SCHEMA FC_Video;

DROP TABLE IF EXISTS FC_Video.material;
CREATE TABLE IF NOT EXISTS FC_Video.material (
  `materialid` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `userid` bigint(20) unsigned NOT NULL DEFAULT '0',
  `materialname` varchar(32) NOT NULL DEFAULT '',
  `rawurl` varchar(1024) NOT NULL DEFAULT '',
  `materialmd5` char(32) NOT NULL DEFAULT '',
  `fileformat` varchar(64) NOT NULL DEFAULT '',
  `duration` decimal(11,3) unsigned NOT NULL DEFAULT '0',
  `width` int(11) unsigned NOT NULL DEFAULT '0',
  `height` int(11) unsigned NOT NULL DEFAULT '0',
  `videobitrate` int(11) unsigned NOT NULL DEFAULT '0',
  `framerate` decimal(11,3) unsigned NOT NULL DEFAULT '0',
  `thumbnail` varchar(1024) NOT NULL DEFAULT '',
  `previewurl` varchar(1024) NOT NULL DEFAULT '',
  `referer` int(11) unsigned NOT NULL DEFAULT 0,
  `robotminorstatus` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `robotmajorstatus` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `robotmajortime` datetime NOT NULL,
  `auditstatus` tinyint(3) unsigned NOT NULL DEFAULT '1',
  `unpassreason` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `unpassotherreason` varchar(30) NOT NULL DEFAULT '',
  `audittime` datetime NOT NULL,
  `auditor` int(11) unsigned NOT NULL DEFAULT '0',
  `createtime` datetime NOT NULL,
  `updatetime` datetime NOT NULL,
  `isdel` tinyint(3) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`materialid`)
);


