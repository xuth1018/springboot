/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50562
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50562
File Encoding         : 65001

Date: 2020-03-17 17:20:38
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_dict`
-- ----------------------------
DROP TABLE IF EXISTS `t_dict`;
CREATE TABLE `t_dict` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `PARENT_CODE` varchar(20) DEFAULT NULL,
  `CODE` varchar(20) DEFAULT NULL,
  `VALUE` varchar(100) DEFAULT NULL,
  `CREATE_DATE` date DEFAULT NULL,
  `CREATE_BY` varchar(100) DEFAULT NULL,
  `UPDATE_DATE` date DEFAULT NULL,
  `UPDATE_BY` varchar(100) DEFAULT NULL,
  `IS_DELETE` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_dict
-- ----------------------------
INSERT INTO `t_dict` VALUES ('4', 'AGE', 'A01', '12岁以下', '2020-03-17', 'system', '2020-03-17', 'system', '0');
INSERT INTO `t_dict` VALUES ('5', 'AGE', 'A02', '12-18', null, 'system', null, 'system', null);
INSERT INTO `t_dict` VALUES ('6', 'AGE', 'A03', '18-25', null, 'system', null, 'system', null);
INSERT INTO `t_dict` VALUES ('7', 'AGE', 'A04', '25-35', null, 'system', null, 'system', null);
INSERT INTO `t_dict` VALUES ('9', 'AGE', 'A06', '35-50', null, 'system', null, 'system', null);
