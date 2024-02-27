/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80029
 Source Host           : localhost:3306
 Source Schema         : springboot_vue

 Target Server Type    : MySQL
 Target Server Version : 80029
 File Encoding         : 65001

 Date: 14/01/2024 20:10:25
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_book
-- ----------------------------
CREATE DATABASE db_library;
USE db_library;
DROP TABLE IF EXISTS `t_book`;
CREATE TABLE `t_book`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `isbn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书编号',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `author` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '作者',
  `publisher` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '出版社',
  `create_time` date NULL DEFAULT NULL COMMENT '出版时间',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '1' COMMENT '0：未归还 1：已归还 2归还中',
  `borrownum` int NOT NULL DEFAULT 0 COMMENT '此书被借阅次数',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `isbn`(`isbn` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_book
-- ----------------------------
INSERT INTO `t_book` VALUES (1, '1001', '操作系统', 20.00, NULL, NULL, NULL, '0', 9);
INSERT INTO `t_book` VALUES (2, '1002', '数据结构', 20.00, NULL, NULL, NULL, '1', 3);
INSERT INTO `t_book` VALUES (3, '1003', '计算机网络', 15.00, NULL, NULL, NULL, '2', 2);

-- ----------------------------
-- Table structure for t_book_with_user
-- ----------------------------
DROP TABLE IF EXISTS `t_book_with_user`;
CREATE TABLE `t_book_with_user`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `reader_id` bigint NOT NULL COMMENT '读者id',
  `isbn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书编号',
  `book_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书名',
  `nick_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '读者姓名',
  `lend_time` datetime NULL DEFAULT NULL COMMENT '借阅时间',
  `dead_time` datetime NULL DEFAULT NULL COMMENT '应归还时间',
  `prolong` int NULL DEFAULT NULL COMMENT '续借次数',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `id`(`reader_id` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_book_with_user
-- ----------------------------
INSERT INTO `t_book_with_user` VALUES (20, 2, '1003', '计算机网络', 'aaa', '2023-11-20 16:36:46', '2023-12-20 16:36:46', 1);
INSERT INTO `t_book_with_user` VALUES (21, 2, '1001', '操作系统', 'aaa', '2023-11-20 16:36:56', '2023-12-20 16:36:56', 1);

-- ----------------------------
-- Table structure for t_lend_record
-- ----------------------------
DROP TABLE IF EXISTS `t_lend_record`;
CREATE TABLE `t_lend_record`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'id',
  `reader_id` bigint NOT NULL COMMENT '读者id',
  `isbn` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图书编号',
  `bookname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图书名',
  `lend_time` datetime NULL DEFAULT NULL COMMENT '借书日期',
  `return_time` datetime NULL DEFAULT NULL COMMENT '还书日期',
  `status` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '0：未归还 1：已归还',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 22 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_lend_record
-- ----------------------------
INSERT INTO `t_lend_record` VALUES (19, 2, '1002', '数据结构', '2023-11-20 16:36:45', '2023-11-20 16:37:32', '1');
INSERT INTO `t_lend_record` VALUES (20, 2, '1003', '计算机网络', '2023-11-20 16:36:46', NULL, '2');
INSERT INTO `t_lend_record` VALUES (21, 2, '1001', '操作系统', '2023-11-20 16:36:56', NULL, '0');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `phone` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '电话号码',
  `sex` varchar(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别',
  `address` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '地址',
  `role` int NOT NULL COMMENT '角色、1：管理员 2：普通用户',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'admin', '12345', '小明', '18321299982', '男', 'A大学学生公寓2号楼213', 1);
INSERT INTO `t_user` VALUES (2, 'aaa', '12345', '小红', '13913282823', '女', 'A大学学生公寓1号楼312', 2);
INSERT INTO `t_user` VALUES (3, 'bbb', '12345', '小新', '18921255432', '男', 'A大学学生公寓2号楼113', 2);

SET FOREIGN_KEY_CHECKS = 1;
