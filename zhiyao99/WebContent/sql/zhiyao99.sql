/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50144
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50144
File Encoding         : 65001

Date: 2013-04-28 13:32:21
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `zy_website`
-- ----------------------------
DROP TABLE IF EXISTS `zy_website`;
CREATE TABLE `zy_website` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL COMMENT '网站名称',
  `starturl` varchar(512) NOT NULL COMMENT '网址入口',
  `domain` char(128) NOT NULL COMMENT '域名',
  `inuse` tinyint(4) NOT NULL DEFAULT '1' COMMENT '监控状态 1.监控中 2.未监控',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `zy_mail`
-- ----------------------------
DROP TABLE IF EXISTS `zy_mail`;
CREATE TABLE `zy_mail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `host` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of zy_mail
-- ----------------------------
INSERT INTO `zy_mail` VALUES ('1', 'smtp.exmail.qq.com', 'favorite_01@zhiyao99.com', 'Fzhiyao99');
INSERT INTO `zy_mail` VALUES ('2', 'smtp.exmail.qq.com', 'favorite_02@zhiyao99.com', 'Fzhiyao99');
INSERT INTO `zy_mail` VALUES ('3', 'smtp.exmail.qq.com', 'favorite_03@zhiyao99.com', 'Fzhiyao99');
INSERT INTO `zy_mail` VALUES ('4', 'smtp.exmail.qq.com', 'favorite_04@zhiyao99.com', 'Fzhiyao99');
INSERT INTO `zy_mail` VALUES ('5', 'smtp.exmail.qq.com', 'favorite_05@zhiyao99.com', 'Fzhiyao99');
INSERT INTO `zy_mail` VALUES ('6', 'smtp.exmail.qq.com', 'favorite_06@zhiyao99.com', 'Fzhiyao99');
INSERT INTO `zy_mail` VALUES ('7', 'smtp.exmail.qq.com', 'favorite_07@zhiyao99.com', 'Fzhiyao99');
INSERT INTO `zy_mail` VALUES ('8', 'smtp.exmail.qq.com', 'favorite_08@zhiyao99.com', 'Fzhiyao99');
INSERT INTO `zy_mail` VALUES ('9', 'smtp.exmail.qq.com', 'favorite_09@zhiyao99.com', 'Fzhiyao99');
INSERT INTO `zy_mail` VALUES ('10', 'smtp.exmail.qq.com', 'favorite_10@zhiyao99.com', 'Fzhiyao99');
-- ----------------------------
-- Table structure for `zy_pageview`
-- ----------------------------
DROP TABLE IF EXISTS `zy_pageview`;
CREATE TABLE `zy_pageview` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `clientIp` varchar(40) NOT NULL  COMMENT '访问者IP',
  `viewTime` datetime DEFAULT NULL COMMENT '访问时间',
  `productId` int(11) NOT NULL,
  `viewType` tinyint(4) NOT NULL COMMENT '访问方式1.来自网页2.来自邮箱',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `zy_product`
-- ----------------------------
DROP TABLE IF EXISTS `zy_product`;
CREATE TABLE `zy_product` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productMd5` char(32) CHARACTER SET ascii NOT NULL,
  `productName` varchar(255) NOT NULL COMMENT '商品名称',
  `productUrl` varchar(512) NOT NULL COMMENT '商品URL',
  `imageUrl` varchar(512) NOT NULL COMMENT '商品图片URL',
  `price` double NOT NULL DEFAULT '0' COMMENT '商品价格',
  `freight` double DEFAULT '0' COMMENT '运费',
  `isFreeSend` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否包运费 1.包邮 0.不包邮',
  `shopkeeper` varchar(50) NOT NULL COMMENT '店主名称',
  `shopkeeperUrl` varchar(512) DEFAULT '' COMMENT '店主URL',
  `location` varchar(50) DEFAULT '' COMMENT '所在地',
  `bargainCount` int(11) NOT NULL DEFAULT '0' COMMENT '成交数量',
  `commentCount` int(11) COMMENT '评论数量',
  `isOnSale` tinyint(4) NOT NULL DEFAULT '1' COMMENT '销售状态 1.销售 2.下架',
  `siteId` int(11) NOT NULL COMMENT '来源网站编号',
  `siteName` varchar(30) NOT NULL COMMENT '来源网站',
  `crawlDate` datetime DEFAULT NULL COMMENT '最后一次出现时间',
  PRIMARY KEY (`id`),
  KEY `PsearchproductMd5` (`productMd5`),
  KEY `Psearchcrawldate` (`crawlDate`),
  KEY `PsearchbargainCount` (`bargainCount`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `zy_onsale`
-- ----------------------------
DROP TABLE IF EXISTS `zy_onsale`;
CREATE TABLE `zy_onsale` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productMd5` char(32) CHARACTER SET ascii NOT NULL,
  `productName` varchar(255) NOT NULL COMMENT '商品名称',
  `productUrl` varchar(512) DEFAULT ''  COMMENT '商品URL',
  `iproductUrl` varchar(512) DEFAULT ''  COMMENT 'IP访问链接URL',
  `imageUrl` varchar(512) NOT NULL COMMENT '商品图片URL',
  `price` double NOT NULL DEFAULT '0' COMMENT '商品价格',
  `freight` double DEFAULT '0' COMMENT '运费',
  `isFreeSend` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否包运费 1.包邮 0.不包邮',
  `bargainCount` int(11) NOT NULL DEFAULT '0' COMMENT '成交数量',
  `siteId` int(11) NOT NULL COMMENT '来源网站编号',
  `siteName` varchar(30) NOT NULL COMMENT '来源网站',
  `onSale` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否在售 1：在售 0：已下架',
  `isTop` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否置顶 1：置顶 0：非置顶',
	`forSaleTime` datetime DEFAULT '0000-00-00 00:00:00' COMMENT '带发布时间',
	`downTime` datetime DEFAULT '0000-00-00 00:00:00',
  `isDeleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否在售 1：已删除 0：未删除',
  `clickUrl` varchar(512) NOT NULL  DEFAULT '' COMMENT '佣金URL',
  `realUrl` varchar(512) NOT NULL  DEFAULT '' COMMENT '实际URL',
  `productPic` blob COMMENT '商品URL',
  `iproductPic` blob COMMENT 'IP访问链接商品URL',
  `userId` int(11) NOT NULL DEFAULT '0' COMMENT '',
  `userName` varchar(30) NOT NULL DEFAULT '' COMMENT '',
  PRIMARY KEY (`id`),
  KEY `PforSaleTime` (`forSaleTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `zy_keyword`
-- ----------------------------
DROP TABLE IF EXISTS `zy_keyword`;
CREATE TABLE `zy_keyword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `keyword` varchar(512) NOT NULL COMMENT '关键词',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `zy_sendMail`
-- ----------------------------
DROP TABLE IF EXISTS `zy_sendMail`;
CREATE TABLE `zy_sendMail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productMd5` char(32) CHARACTER SET ascii NOT NULL,
  `productName` varchar(255) NOT NULL COMMENT '商品名称',
  `productUrl` varchar(512) NOT NULL COMMENT '商品URL',
  `receiver` varchar(100) NOT NULL  COMMENT '接收人邮件',
  `sender` varchar(50) NOT NULL  COMMENT '发送人邮件',
  `sendTime` datetime DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`),
  KEY `ksearch` (`productMd5`,`receiver`,`sendTime`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for `zy_onsale`
-- ----------------------------
DROP TABLE IF EXISTS `zy_sys_user`;
CREATE TABLE `zy_sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `loginName` varchar(50) NOT NULL COMMENT '登录名',
  `password` varchar(100) NOT NULL COMMENT '登录密码',
  `status` char(2) NOT NULL DEFAULT '1' COMMENT '用户状态,0：禁用 1：可用',
  `createTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updateTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `zy_sys_user` VALUES ('1', 'kansuny', '212075c4ba93f12109a90d12211cc5eb', '1', '0000-00-00 00:00:00', '0000-00-00 00:00:00');


-- ----------------------------
-- Table structure for `zy_user_advice`
-- ----------------------------
DROP TABLE IF EXISTS `zy_user_advice`;
CREATE TABLE `zy_user_advice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) COMMENT '留言人邮箱',
  `ip` varchar(30) COMMENT 'IP地址',
  `phone` varchar(20) COMMENT '手机',
  `qq` varchar(20) COMMENT 'QQ号码',
  `content` varchar(1024) COMMENT '内容',
  `createTime` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for `zy_tm_ut`
-- ----------------------------
DROP TABLE IF EXISTS `zy_tm_ut`;
CREATE TABLE `zy_tm_ut` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `productName` varchar(255) NOT NULL COMMENT '商品名称',
  `productUrl` varchar(512) NOT NULL COMMENT '商品URL',
  `siteId` int(11) NOT NULL COMMENT '',
  `siteName` varchar(30) NOT NULL COMMENT '',
  `productMd5` char(32) CHARACTER SET ascii NOT NULL,
  `contentMd5` char(32) CHARACTER SET ascii NOT NULL,
  `crawlDate` datetime DEFAULT NULL COMMENT '第一次爬取时间',
  `updateDate` datetime DEFAULT NULL COMMENT '最后更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `psearchproductMd5` (`productMd5`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table copy by `zy_tm_ut`
-- ----------------------------
DROP TABLE IF EXISTS zy_ut_00;
CREATE TABLE zy_ut_00 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_01;
CREATE TABLE zy_ut_01 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_02;
CREATE TABLE zy_ut_02 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_03;
CREATE TABLE zy_ut_03 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_04;
CREATE TABLE zy_ut_04 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_05;
CREATE TABLE zy_ut_05 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_06;
CREATE TABLE zy_ut_06 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_07;
CREATE TABLE zy_ut_07 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_08;
CREATE TABLE zy_ut_08 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_09;
CREATE TABLE zy_ut_09 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_0a;
CREATE TABLE zy_ut_0a LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_0b;
CREATE TABLE zy_ut_0b LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_0c;
CREATE TABLE zy_ut_0c LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_0d;
CREATE TABLE zy_ut_0d LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_0e;
CREATE TABLE zy_ut_0e LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_0f;
CREATE TABLE zy_ut_0f LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_10;
CREATE TABLE zy_ut_10 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_11;
CREATE TABLE zy_ut_11 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_12;
CREATE TABLE zy_ut_12 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_13;
CREATE TABLE zy_ut_13 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_14;
CREATE TABLE zy_ut_14 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_15;
CREATE TABLE zy_ut_15 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_16;
CREATE TABLE zy_ut_16 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_17;
CREATE TABLE zy_ut_17 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_18;
CREATE TABLE zy_ut_18 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_19;
CREATE TABLE zy_ut_19 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_1a;
CREATE TABLE zy_ut_1a LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_1b;
CREATE TABLE zy_ut_1b LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_1c;
CREATE TABLE zy_ut_1c LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_1d;
CREATE TABLE zy_ut_1d LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_1e;
CREATE TABLE zy_ut_1e LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_1f;
CREATE TABLE zy_ut_1f LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_20;
CREATE TABLE zy_ut_20 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_21;
CREATE TABLE zy_ut_21 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_22;
CREATE TABLE zy_ut_22 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_23;
CREATE TABLE zy_ut_23 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_24;
CREATE TABLE zy_ut_24 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_25;
CREATE TABLE zy_ut_25 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_26;
CREATE TABLE zy_ut_26 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_27;
CREATE TABLE zy_ut_27 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_28;
CREATE TABLE zy_ut_28 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_29;
CREATE TABLE zy_ut_29 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_2a;
CREATE TABLE zy_ut_2a LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_2b;
CREATE TABLE zy_ut_2b LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_2c;
CREATE TABLE zy_ut_2c LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_2d;
CREATE TABLE zy_ut_2d LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_2e;
CREATE TABLE zy_ut_2e LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_2f;
CREATE TABLE zy_ut_2f LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_30;
CREATE TABLE zy_ut_30 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_31;
CREATE TABLE zy_ut_31 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_32;
CREATE TABLE zy_ut_32 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_33;
CREATE TABLE zy_ut_33 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_34;
CREATE TABLE zy_ut_34 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_35;
CREATE TABLE zy_ut_35 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_36;
CREATE TABLE zy_ut_36 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_37;
CREATE TABLE zy_ut_37 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_38;
CREATE TABLE zy_ut_38 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_39;
CREATE TABLE zy_ut_39 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_3a;
CREATE TABLE zy_ut_3a LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_3b;
CREATE TABLE zy_ut_3b LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_3c;
CREATE TABLE zy_ut_3c LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_3d;
CREATE TABLE zy_ut_3d LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_3e;
CREATE TABLE zy_ut_3e LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_3f;
CREATE TABLE zy_ut_3f LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_40;
CREATE TABLE zy_ut_40 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_41;
CREATE TABLE zy_ut_41 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_42;
CREATE TABLE zy_ut_42 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_43;
CREATE TABLE zy_ut_43 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_44;
CREATE TABLE zy_ut_44 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_45;
CREATE TABLE zy_ut_45 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_46;
CREATE TABLE zy_ut_46 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_47;
CREATE TABLE zy_ut_47 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_48;
CREATE TABLE zy_ut_48 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_49;
CREATE TABLE zy_ut_49 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_4a;
CREATE TABLE zy_ut_4a LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_4b;
CREATE TABLE zy_ut_4b LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_4c;
CREATE TABLE zy_ut_4c LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_4d;
CREATE TABLE zy_ut_4d LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_4e;
CREATE TABLE zy_ut_4e LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_4f;
CREATE TABLE zy_ut_4f LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_50;
CREATE TABLE zy_ut_50 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_51;
CREATE TABLE zy_ut_51 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_52;
CREATE TABLE zy_ut_52 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_53;
CREATE TABLE zy_ut_53 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_54;
CREATE TABLE zy_ut_54 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_55;
CREATE TABLE zy_ut_55 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_56;
CREATE TABLE zy_ut_56 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_57;
CREATE TABLE zy_ut_57 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_58;
CREATE TABLE zy_ut_58 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_59;
CREATE TABLE zy_ut_59 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_5a;
CREATE TABLE zy_ut_5a LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_5b;
CREATE TABLE zy_ut_5b LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_5c;
CREATE TABLE zy_ut_5c LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_5d;
CREATE TABLE zy_ut_5d LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_5e;
CREATE TABLE zy_ut_5e LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_5f;
CREATE TABLE zy_ut_5f LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_60;
CREATE TABLE zy_ut_60 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_61;
CREATE TABLE zy_ut_61 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_62;
CREATE TABLE zy_ut_62 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_63;
CREATE TABLE zy_ut_63 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_64;
CREATE TABLE zy_ut_64 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_65;
CREATE TABLE zy_ut_65 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_66;
CREATE TABLE zy_ut_66 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_67;
CREATE TABLE zy_ut_67 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_68;
CREATE TABLE zy_ut_68 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_69;
CREATE TABLE zy_ut_69 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_6a;
CREATE TABLE zy_ut_6a LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_6b;
CREATE TABLE zy_ut_6b LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_6c;
CREATE TABLE zy_ut_6c LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_6d;
CREATE TABLE zy_ut_6d LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_6e;
CREATE TABLE zy_ut_6e LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_6f;
CREATE TABLE zy_ut_6f LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_70;
CREATE TABLE zy_ut_70 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_71;
CREATE TABLE zy_ut_71 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_72;
CREATE TABLE zy_ut_72 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_73;
CREATE TABLE zy_ut_73 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_74;
CREATE TABLE zy_ut_74 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_75;
CREATE TABLE zy_ut_75 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_76;
CREATE TABLE zy_ut_76 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_77;
CREATE TABLE zy_ut_77 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_78;
CREATE TABLE zy_ut_78 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_79;
CREATE TABLE zy_ut_79 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_7a;
CREATE TABLE zy_ut_7a LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_7b;
CREATE TABLE zy_ut_7b LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_7c;
CREATE TABLE zy_ut_7c LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_7d;
CREATE TABLE zy_ut_7d LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_7e;
CREATE TABLE zy_ut_7e LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_7f;
CREATE TABLE zy_ut_7f LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_80;
CREATE TABLE zy_ut_80 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_81;
CREATE TABLE zy_ut_81 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_82;
CREATE TABLE zy_ut_82 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_83;
CREATE TABLE zy_ut_83 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_84;
CREATE TABLE zy_ut_84 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_85;
CREATE TABLE zy_ut_85 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_86;
CREATE TABLE zy_ut_86 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_87;
CREATE TABLE zy_ut_87 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_88;
CREATE TABLE zy_ut_88 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_89;
CREATE TABLE zy_ut_89 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_8a;
CREATE TABLE zy_ut_8a LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_8b;
CREATE TABLE zy_ut_8b LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_8c;
CREATE TABLE zy_ut_8c LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_8d;
CREATE TABLE zy_ut_8d LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_8e;
CREATE TABLE zy_ut_8e LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_8f;
CREATE TABLE zy_ut_8f LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_90;
CREATE TABLE zy_ut_90 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_91;
CREATE TABLE zy_ut_91 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_92;
CREATE TABLE zy_ut_92 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_93;
CREATE TABLE zy_ut_93 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_94;
CREATE TABLE zy_ut_94 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_95;
CREATE TABLE zy_ut_95 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_96;
CREATE TABLE zy_ut_96 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_97;
CREATE TABLE zy_ut_97 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_98;
CREATE TABLE zy_ut_98 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_99;
CREATE TABLE zy_ut_99 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_9a;
CREATE TABLE zy_ut_9a LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_9b;
CREATE TABLE zy_ut_9b LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_9c;
CREATE TABLE zy_ut_9c LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_9d;
CREATE TABLE zy_ut_9d LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_9e;
CREATE TABLE zy_ut_9e LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_9f;
CREATE TABLE zy_ut_9f LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_a0;
CREATE TABLE zy_ut_a0 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_a1;
CREATE TABLE zy_ut_a1 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_a2;
CREATE TABLE zy_ut_a2 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_a3;
CREATE TABLE zy_ut_a3 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_a4;
CREATE TABLE zy_ut_a4 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_a5;
CREATE TABLE zy_ut_a5 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_a6;
CREATE TABLE zy_ut_a6 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_a7;
CREATE TABLE zy_ut_a7 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_a8;
CREATE TABLE zy_ut_a8 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_a9;
CREATE TABLE zy_ut_a9 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_aa;
CREATE TABLE zy_ut_aa LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_ab;
CREATE TABLE zy_ut_ab LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_ac;
CREATE TABLE zy_ut_ac LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_ad;
CREATE TABLE zy_ut_ad LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_ae;
CREATE TABLE zy_ut_ae LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_af;
CREATE TABLE zy_ut_af LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_b0;
CREATE TABLE zy_ut_b0 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_b1;
CREATE TABLE zy_ut_b1 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_b2;
CREATE TABLE zy_ut_b2 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_b3;
CREATE TABLE zy_ut_b3 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_b4;
CREATE TABLE zy_ut_b4 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_b5;
CREATE TABLE zy_ut_b5 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_b6;
CREATE TABLE zy_ut_b6 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_b7;
CREATE TABLE zy_ut_b7 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_b8;
CREATE TABLE zy_ut_b8 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_b9;
CREATE TABLE zy_ut_b9 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_ba;
CREATE TABLE zy_ut_ba LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_bb;
CREATE TABLE zy_ut_bb LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_bc;
CREATE TABLE zy_ut_bc LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_bd;
CREATE TABLE zy_ut_bd LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_be;
CREATE TABLE zy_ut_be LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_bf;
CREATE TABLE zy_ut_bf LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_c0;
CREATE TABLE zy_ut_c0 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_c1;
CREATE TABLE zy_ut_c1 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_c2;
CREATE TABLE zy_ut_c2 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_c3;
CREATE TABLE zy_ut_c3 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_c4;
CREATE TABLE zy_ut_c4 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_c5;
CREATE TABLE zy_ut_c5 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_c6;
CREATE TABLE zy_ut_c6 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_c7;
CREATE TABLE zy_ut_c7 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_c8;
CREATE TABLE zy_ut_c8 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_c9;
CREATE TABLE zy_ut_c9 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_ca;
CREATE TABLE zy_ut_ca LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_cb;
CREATE TABLE zy_ut_cb LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_cc;
CREATE TABLE zy_ut_cc LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_cd;
CREATE TABLE zy_ut_cd LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_ce;
CREATE TABLE zy_ut_ce LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_cf;
CREATE TABLE zy_ut_cf LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_d0;
CREATE TABLE zy_ut_d0 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_d1;
CREATE TABLE zy_ut_d1 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_d2;
CREATE TABLE zy_ut_d2 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_d3;
CREATE TABLE zy_ut_d3 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_d4;
CREATE TABLE zy_ut_d4 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_d5;
CREATE TABLE zy_ut_d5 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_d6;
CREATE TABLE zy_ut_d6 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_d7;
CREATE TABLE zy_ut_d7 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_d8;
CREATE TABLE zy_ut_d8 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_d9;
CREATE TABLE zy_ut_d9 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_da;
CREATE TABLE zy_ut_da LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_db;
CREATE TABLE zy_ut_db LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_dc;
CREATE TABLE zy_ut_dc LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_dd;
CREATE TABLE zy_ut_dd LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_de;
CREATE TABLE zy_ut_de LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_df;
CREATE TABLE zy_ut_df LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_e0;
CREATE TABLE zy_ut_e0 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_e1;
CREATE TABLE zy_ut_e1 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_e2;
CREATE TABLE zy_ut_e2 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_e3;
CREATE TABLE zy_ut_e3 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_e4;
CREATE TABLE zy_ut_e4 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_e5;
CREATE TABLE zy_ut_e5 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_e6;
CREATE TABLE zy_ut_e6 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_e7;
CREATE TABLE zy_ut_e7 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_e8;
CREATE TABLE zy_ut_e8 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_e9;
CREATE TABLE zy_ut_e9 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_ea;
CREATE TABLE zy_ut_ea LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_eb;
CREATE TABLE zy_ut_eb LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_ec;
CREATE TABLE zy_ut_ec LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_ed;
CREATE TABLE zy_ut_ed LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_ee;
CREATE TABLE zy_ut_ee LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_ef;
CREATE TABLE zy_ut_ef LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_f0;
CREATE TABLE zy_ut_f0 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_f1;
CREATE TABLE zy_ut_f1 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_f2;
CREATE TABLE zy_ut_f2 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_f3;
CREATE TABLE zy_ut_f3 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_f4;
CREATE TABLE zy_ut_f4 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_f5;
CREATE TABLE zy_ut_f5 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_f6;
CREATE TABLE zy_ut_f6 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_f7;
CREATE TABLE zy_ut_f7 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_f8;
CREATE TABLE zy_ut_f8 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_f9;
CREATE TABLE zy_ut_f9 LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_fa;
CREATE TABLE zy_ut_fa LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_fb;
CREATE TABLE zy_ut_fb LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_fc;
CREATE TABLE zy_ut_fc LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_fd;
CREATE TABLE zy_ut_fd LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_fe;
CREATE TABLE zy_ut_fe LIKE zy_tm_ut;
DROP TABLE IF EXISTS zy_ut_ff;
CREATE TABLE zy_ut_ff LIKE zy_tm_ut;
