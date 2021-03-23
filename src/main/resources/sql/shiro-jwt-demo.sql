/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50732
 Source Host           : localhost:3306
 Source Schema         : shiro-jwt-demo

 Target Server Type    : MySQL
 Target Server Version : 50732
 File Encoding         : 65001

 Date: 23/03/2021 13:42:11
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(255) NOT NULL COMMENT '微信openID，用户唯一ID',
  `name` varchar(255) NOT NULL COMMENT '昵称',
  `photo` varchar(255) NOT NULL COMMENT '头像url',
  `sex` varchar(255) NOT NULL COMMENT '性别',
  `grade` varchar(255) NOT NULL COMMENT '专业',
  `college` varchar(255) NOT NULL COMMENT '学校',
  `contact` varchar(255) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
