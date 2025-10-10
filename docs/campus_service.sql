/*
 Navicat Premium Data Transfer

 Source Server         : mysql8.0
 Source Server Type    : MySQL
 Source Server Version : 80035
 Source Host           : localhost:3306
 Source Schema         : campus_service

 Target Server Type    : MySQL
 Target Server Version : 80035
 File Encoding         : 65001

 Date: 16/07/2025 11:48:34
*/

-- Ensure database exists and is selected
CREATE DATABASE IF NOT EXISTS `campus_service`;
USE `campus_service`;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for address_book
-- ----------------------------
DROP TABLE IF EXISTS `address_book`;
CREATE TABLE `address_book`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `consignee` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '收货人',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '性别',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '手机号',
  `college_id` bigint(0) NOT NULL COMMENT '学院id',
  `dormitory_id` bigint(0) NOT NULL COMMENT '宿舍楼id',
  `detail` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '详细地址',
  `label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '标签：宿舍 0  教室 1 校园 2',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '默认 0 否 1是',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '地址簿' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of address_book
-- ----------------------------
INSERT INTO `address_book` VALUES (2, 17, '郑世民', '1', '13123718520', 1, 32, 'a428教室第一排', '教室', 0, '2024-06-11 16:10:56', '2024-08-01 10:45:11', 17, 17);
INSERT INTO `address_book` VALUES (3, 17, '李萌', '1', '13123718520', 1, 1, '608宿舍', '宿舍', 1, '2024-06-11 16:12:41', '2024-08-01 10:45:11', 17, 17);
INSERT INTO `address_book` VALUES (4, 17, '高子豪', '1', '13123718520', 1, 1, '605宿舍', '宿舍', 0, '2024-06-11 17:34:23', '2024-08-01 10:45:11', 17, 17);
INSERT INTO `address_book` VALUES (5, 17, '貂蝉', '0', '1512345678', 27, 23, '509宿舍', '宿舍', 0, '2024-06-11 20:54:40', '2024-08-01 10:45:11', 17, 17);
INSERT INTO `address_book` VALUES (6, 9, '张飞', '1', '12345678910', 23, 1, '505宿舍', '宿舍', 1, '2024-06-12 16:47:45', '2024-06-12 16:47:47', 9, 9);
INSERT INTO `address_book` VALUES (7, 18, '123', '1', '13566365236', 25, 1, '3213123213', '宿舍', 1, '2025-06-25 00:44:43', '2025-06-25 00:45:02', 18, 18);
INSERT INTO `address_book` VALUES (8, 20, '李子涵', '0', '1651561131', 1, 1, '楼下自提', '宿舍', 1, '2025-06-28 00:08:53', '2025-06-28 01:38:58', 20, 20);
INSERT INTO `address_book` VALUES (9, 19, '马隆', '1', '12321312321', 1, 1, '123123213', '宿舍', 1, '2025-06-28 01:22:07', '2025-06-28 01:22:10', 19, 19);
INSERT INTO `address_book` VALUES (10, 20, '马龙龙', '1', '135164614', 26, 25, '3123123123', '宿舍', 0, '2025-06-28 01:38:47', '2025-06-28 01:38:58', 20, 20);

-- ----------------------------
-- Table structure for announcement
-- ----------------------------
DROP TABLE IF EXISTS `announcement`;
CREATE TABLE `announcement`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '公告标题',
  `content` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '公告内容',
  `status` int(0) NULL DEFAULT 1 COMMENT '公告状态 0:禁用，1:启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_dormitory_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 25 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '公告内容' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of announcement
-- ----------------------------
INSERT INTO `announcement` VALUES (1, '系统更新第一次', '维护需要2个小时，希望大家耐心等待，为此表达真诚的谦意。', 1, '2024-05-29 15:51:20', '2025-06-27 23:44:41', 1, 1);
INSERT INTO `announcement` VALUES (2, '系统更新第二次', '系统进行第二次更新，维护需要2个小时，希望大家耐心等待，为此表达真诚的谦意。', 1, '2024-05-29 15:51:20', '2025-06-27 23:44:48', 1, 1);
INSERT INTO `announcement` VALUES (3, '系统更新第三次', '系统进行第三次更新，维护需要2个小时，希望大家耐心等待，为此表达真诚的谦意。', 1, '2024-05-29 15:51:20', '2025-06-27 23:44:57', 1, 1);
INSERT INTO `announcement` VALUES (4, '系统更新第四次', '系统进行第四次更新，维护需要2个小时，希望大家耐心等待，为此表达真诚的谦意。', 1, '2024-05-29 15:51:20', '2025-06-27 23:45:02', 1, 1);
INSERT INTO `announcement` VALUES (5, '系统更新第五次', '系统进行第五次更新，维护需要2个小时，希望大家耐心等待，为此表达真诚的谦意。', 1, '2024-05-29 15:51:20', '2025-06-27 23:45:06', 1, 1);
INSERT INTO `announcement` VALUES (24, '系统新增AI智能助手', '系统新增AI智能助手', 1, '2025-06-28 01:40:12', '2025-06-28 01:40:12', 1, 1);

-- ----------------------------
-- Table structure for category
-- ----------------------------
DROP TABLE IF EXISTS `category`;
CREATE TABLE `category`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '分类名称',
  `status` int(0) NULL DEFAULT 1 COMMENT '分类状态 0:禁用，1:启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_category_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '物品分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of category
-- ----------------------------
INSERT INTO `category` VALUES (1, '衣物鞋子', 1, '2024-05-29 15:51:20', '2024-06-06 13:54:20', 1, 1);
INSERT INTO `category` VALUES (23, '图书教材', 1, '2024-06-03 11:16:34', '2024-06-06 13:54:29', 1, 1);
INSERT INTO `category` VALUES (25, '数码产品', 1, '2024-06-03 13:56:20', '2024-06-06 13:54:39', 1, 1);
INSERT INTO `category` VALUES (26, '运动健身', 1, '2024-06-03 13:56:45', '2024-06-06 13:55:03', 1, 1);
INSERT INTO `category` VALUES (27, '生活用品', 1, '2024-06-06 13:55:27', '2024-06-06 13:55:27', 1, 1);
INSERT INTO `category` VALUES (28, '首饰配饰', 0, '2024-06-06 13:57:21', '2024-06-06 13:58:12', 1, 1);
INSERT INTO `category` VALUES (29, '家居装饰', 0, '2024-06-06 13:57:35', '2024-06-06 13:58:09', 1, 1);
INSERT INTO `category` VALUES (30, '儿童用品', 0, '2024-06-06 13:57:44', '2024-06-06 13:58:07', 1, 1);
INSERT INTO `category` VALUES (31, '工艺收藏', 0, '2024-06-06 13:57:56', '2024-06-06 13:58:05', 1, 1);
INSERT INTO `category` VALUES (32, '其它物品', 1, '2024-06-06 13:58:26', '2024-06-06 13:58:26', 1, 1);

-- ----------------------------
-- Table structure for college
-- ----------------------------
DROP TABLE IF EXISTS `college`;
CREATE TABLE `college`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '分类名称',
  `status` int(0) NULL DEFAULT 1 COMMENT '分类状态 0:禁用，1:启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_college_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 42 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '学院分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of college
-- ----------------------------
INSERT INTO `college` VALUES (1, '计算机学院', 1, '2024-05-29 15:51:20', '2024-06-13 19:26:38', 1, 1);
INSERT INTO `college` VALUES (23, '土木学院', 0, '2024-06-01 23:45:29', '2024-06-03 09:09:35', 1, 1);
INSERT INTO `college` VALUES (25, '医学院', 0, '2024-06-01 23:46:49', '2024-06-02 21:38:22', 1, 1);
INSERT INTO `college` VALUES (26, '法学院', 1, '2024-06-01 23:46:57', '2024-06-03 09:13:40', 1, 1);
INSERT INTO `college` VALUES (27, '汉语言学院', 1, '2024-06-01 23:47:07', '2024-06-01 23:47:07', 1, 1);
INSERT INTO `college` VALUES (28, '数理学院', 1, '2024-06-01 23:47:16', '2024-06-01 23:47:16', 1, 1);
INSERT INTO `college` VALUES (29, '测绘学院', 1, '2024-06-01 23:47:22', '2024-06-01 23:47:22', 1, 1);
INSERT INTO `college` VALUES (30, '市政学院', 1, '2024-06-01 23:47:41', '2024-06-01 23:47:41', 1, 1);
INSERT INTO `college` VALUES (31, '管理学院', 1, '2024-06-01 23:47:53', '2024-06-01 23:47:53', 1, 1);
INSERT INTO `college` VALUES (36, '外语学院', 1, '2024-06-02 21:02:03', '2024-06-02 21:02:03', 1, 1);
INSERT INTO `college` VALUES (37, '电气学院', 1, '2024-06-02 21:07:16', '2024-06-02 21:07:16', 1, 1);
INSERT INTO `college` VALUES (41, '数电院', 1, '2025-06-28 01:40:42', '2025-06-28 01:40:42', 1, 1);

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `content` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '内容',
  `user_id` bigint(0) NOT NULL COMMENT '用户id',
  `user_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '用户名字',
  `avatar` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '头像',
  `foreign_id` bigint(0) NOT NULL COMMENT '业务模块id',
  `foreign_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '业务模块名字',
  `pid` bigint(0) NULL DEFAULT NULL COMMENT '父评论id',
  `target` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '回复对象',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 132 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (70, '我是关羽，这是我第一次评论', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 81, 'goods', 0, '', '2024-06-14 10:43:04', 17);
INSERT INTO `comment` VALUES (71, '我是关羽，这是我第二次评论', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 81, 'goods', 0, '', '2024-06-14 10:44:56', 17);
INSERT INTO `comment` VALUES (72, '我是关羽，这是我第三次评论', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 81, 'goods', 0, '', '2024-06-14 10:54:21', 17);
INSERT INTO `comment` VALUES (73, '我是关羽，这是我第四次评论', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 81, 'goods', 0, '', '2024-06-14 11:27:29', 17);
INSERT INTO `comment` VALUES (74, '我是关羽，这是我第五次评论', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 81, 'goods', 0, '', '2024-06-14 11:30:24', 17);
INSERT INTO `comment` VALUES (75, '我是关羽，真的好看', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 79, 'goods', 0, '', '2024-06-14 11:31:11', 17);
INSERT INTO `comment` VALUES (76, '我是关羽，好喜欢这个商品', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 79, 'goods', 0, '', '2024-06-14 11:32:17', 17);
INSERT INTO `comment` VALUES (77, '你要买吗', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 81, 'goods', 70, '关羽', '2024-06-14 14:12:42', 17);
INSERT INTO `comment` VALUES (78, '我有', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 81, 'goods', 73, '关羽', '2024-06-14 14:14:10', 17);
INSERT INTO `comment` VALUES (79, '好看吗', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 81, 'goods', 70, '关羽', '2024-06-14 14:56:40', 17);
INSERT INTO `comment` VALUES (80, '我是张飞1111', 9, '张飞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/1a5c209d-e72c-4d90-b9c0-93b2cb110feb.jpg', 81, 'goods', 0, '', '2024-06-14 15:08:55', 9);
INSERT INTO `comment` VALUES (81, '回复你了', 9, '张飞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/1a5c209d-e72c-4d90-b9c0-93b2cb110feb.jpg', 81, 'goods', 71, '关羽', '2024-06-14 15:09:13', 9);
INSERT INTO `comment` VALUES (82, '我是张飞', 9, '张飞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/1a5c209d-e72c-4d90-b9c0-93b2cb110feb.jpg', 81, 'goods', 70, '关羽', '2024-06-14 15:09:34', 9);
INSERT INTO `comment` VALUES (83, '11111', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 81, 'goods', 0, '', '2024-06-14 16:06:13', 17);
INSERT INTO `comment` VALUES (84, '222', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 81, 'goods', 80, '张飞', '2024-06-14 16:06:20', 17);
INSERT INTO `comment` VALUES (85, '好看吗', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 70, 'forum', 0, '', '2024-06-14 21:31:25', 17);
INSERT INTO `comment` VALUES (86, '我想明天就去看', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 70, 'forum', 0, '', '2024-06-14 21:37:48', 17);
INSERT INTO `comment` VALUES (87, '很好看的，一起去看', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 70, 'forum', 85, '关羽', '2024-06-14 21:41:22', 17);
INSERT INTO `comment` VALUES (88, '很好看的，一起去看', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 70, 'forum', 85, '关羽', '2024-06-14 21:41:25', 17);
INSERT INTO `comment` VALUES (89, '去看吧，挺好看的', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 70, 'forum', 86, '关羽', '2024-06-14 21:44:51', 17);
INSERT INTO `comment` VALUES (90, '我也叫关羽', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 70, 'forum', 86, '关羽', '2024-06-14 21:45:05', 17);
INSERT INTO `comment` VALUES (91, '一起去看吧', 9, '张飞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/1a5c209d-e72c-4d90-b9c0-93b2cb110feb.jpg', 70, 'forum', 86, '关羽', '2024-06-14 21:45:57', 9);
INSERT INTO `comment` VALUES (95, '好呀', 9, '张飞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/1a5c209d-e72c-4d90-b9c0-93b2cb110feb.jpg', 70, 'forum', 85, '关羽', '2024-06-14 21:51:53', 9);
INSERT INTO `comment` VALUES (96, '我叫张飞，我想和你一起玩，加我v********', 9, '张飞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/1a5c209d-e72c-4d90-b9c0-93b2cb110feb.jpg', 71, 'forum', 0, '', '2024-06-14 21:52:25', 9);
INSERT INTO `comment` VALUES (97, '我叫王五，我想和你一起玩，加我v********', 3, '王五', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/3478c52d-a17f-4b03-b7b5-0781a9876099.jpg', 71, 'forum', 0, '', '2024-06-14 22:23:03', 3);
INSERT INTO `comment` VALUES (98, '111', 3, '王五', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/3478c52d-a17f-4b03-b7b5-0781a9876099.jpg', 71, 'forum', 96, '张飞', '2024-06-14 22:23:17', 3);
INSERT INTO `comment` VALUES (99, '我叫张三，我想和你一起玩，加我v********', 4, '张三', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/9a47c791-2e3b-47e1-a58c-99ae66ea5171.jpg', 71, 'forum', 0, '', '2024-06-14 22:24:31', 4);
INSERT INTO `comment` VALUES (100, '111', 4, '张三', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/9a47c791-2e3b-47e1-a58c-99ae66ea5171.jpg', 71, 'forum', 97, '王五', '2024-06-14 22:24:36', 4);
INSERT INTO `comment` VALUES (101, '222', 4, '张三', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/9a47c791-2e3b-47e1-a58c-99ae66ea5171.jpg', 71, 'forum', 96, '王五', '2024-06-14 22:24:43', 4);
INSERT INTO `comment` VALUES (102, '我叫周六，我想和你一起玩，加我v********', 5, '周六', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/b4a7c893-3e6c-415c-b9ea-fb17248359e9.jpg', 71, 'forum', 0, '', '2024-06-14 22:25:39', 5);
INSERT INTO `comment` VALUES (103, '333', 5, '周六', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/b4a7c893-3e6c-415c-b9ea-fb17248359e9.jpg', 71, 'forum', 96, '张三', '2024-06-14 22:25:45', 5);
INSERT INTO `comment` VALUES (104, '222', 5, '周六', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/b4a7c893-3e6c-415c-b9ea-fb17248359e9.jpg', 71, 'forum', 97, '张三', '2024-06-14 22:25:53', 5);
INSERT INTO `comment` VALUES (105, '111', 5, '周六', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/b4a7c893-3e6c-415c-b9ea-fb17248359e9.jpg', 71, 'forum', 99, '张三', '2024-06-14 22:25:56', 5);
INSERT INTO `comment` VALUES (106, '444', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 71, 'forum', 96, '周六', '2024-06-14 22:42:26', 17);
INSERT INTO `comment` VALUES (107, '人齐了，出发！！！', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 71, 'forum', 96, '张飞', '2024-06-14 22:42:40', 17);
INSERT INTO `comment` VALUES (108, '333', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 71, 'forum', 97, '周六', '2024-06-14 22:42:46', 17);
INSERT INTO `comment` VALUES (109, '222', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 71, 'forum', 99, '周六', '2024-06-14 22:42:50', 17);
INSERT INTO `comment` VALUES (110, '111', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 71, 'forum', 102, '周六', '2024-06-14 22:42:54', 17);
INSERT INTO `comment` VALUES (111, '111', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 70, 'seek', 0, '', '2024-06-16 01:02:00', 17);
INSERT INTO `comment` VALUES (112, '222', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 70, 'seek', 111, '关羽', '2024-06-16 01:02:05', 17);
INSERT INTO `comment` VALUES (113, '我有高数书，我可以低价卖给你', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 70, 'seek', 111, '关羽', '2024-06-16 01:08:32', 17);
INSERT INTO `comment` VALUES (114, '55555', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 71, 'forum', 0, '', '2024-08-01 10:44:17', 17);
INSERT INTO `comment` VALUES (115, '555677888', 17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 71, 'forum', 114, '关羽', '2024-08-01 10:44:23', 17);
INSERT INTO `comment` VALUES (116, '1', 18, 'ji123', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/e7d1f121-a123-4f98-9b82-3d48753eea6e.png', 79, 'goods', 0, '', '2025-06-25 00:52:46', 18);
INSERT INTO `comment` VALUES (117, '3213123', 18, '57575', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/d5785703-8675-4f2e-b090-415ea5602585.png', 77, 'forum', 0, '', '2025-06-25 01:01:23', 18);
INSERT INTO `comment` VALUES (118, '454545', 18, '57575', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/d5785703-8675-4f2e-b090-415ea5602585.png', 71, 'goods', 0, '', '2025-06-25 01:21:51', 18);
INSERT INTO `comment` VALUES (119, '78787', 18, '57575', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/d5785703-8675-4f2e-b090-415ea5602585.png', 71, 'goods', 118, '57575', '2025-06-25 01:21:56', 18);
INSERT INTO `comment` VALUES (120, '27525722', 19, NULL, NULL, 78, 'goods', 0, '', '2025-06-27 01:30:08', 19);
INSERT INTO `comment` VALUES (121, '17171', 19, '刘瑞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/749fce2d-9818-491b-8a49-acdfae970691.png', 78, 'goods', 120, NULL, '2025-06-27 01:30:59', 19);
INSERT INTO `comment` VALUES (122, '171741', 19, '刘瑞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/749fce2d-9818-491b-8a49-acdfae970691.png', 75, 'goods', 0, '', '2025-06-27 01:31:03', 19);
INSERT INTO `comment` VALUES (123, '我服了\n', 19, '刘瑞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/749fce2d-9818-491b-8a49-acdfae970691.png', 76, 'forum', 0, '', '2025-06-27 01:34:34', 19);
INSERT INTO `comment` VALUES (124, '1', 19, '刘瑞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/749fce2d-9818-491b-8a49-acdfae970691.png', 78, 'forum', 0, '', '2025-06-27 23:46:14', 19);
INSERT INTO `comment` VALUES (125, '有人吗', 19, '刘瑞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/749fce2d-9818-491b-8a49-acdfae970691.png', 76, 'seek', 0, '', '2025-06-27 23:47:10', 19);
INSERT INTO `comment` VALUES (126, '有人吗', 19, '刘瑞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/749fce2d-9818-491b-8a49-acdfae970691.png', 78, 'forum', 0, '', '2025-06-27 23:58:09', 19);
INSERT INTO `comment` VALUES (127, '还可以', 20, '李子涵', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/f8cd33ac-153a-48b4-a725-0ca05416a425.png', 79, 'goods', 0, '', '2025-06-28 00:08:11', 20);
INSERT INTO `comment` VALUES (128, '17172', 19, '刘瑞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/749fce2d-9818-491b-8a49-acdfae970691.png', 81, 'goods', 0, '', '2025-06-28 01:33:43', 19);
INSERT INTO `comment` VALUES (129, '782872872', 19, '刘瑞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/749fce2d-9818-491b-8a49-acdfae970691.png', 74, 'goods', 0, '', '2025-06-28 01:34:05', 19);
INSERT INTO `comment` VALUES (130, '3123123', 20, '李子涵', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/f8cd33ac-153a-48b4-a725-0ca05416a425.png', 78, 'forum', 124, '刘瑞', '2025-06-28 01:36:21', 20);
INSERT INTO `comment` VALUES (131, '312321321', 20, '李子涵', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/f8cd33ac-153a-48b4-a725-0ca05416a425.png', 78, 'forum', 0, '', '2025-06-28 01:36:23', 20);
INSERT INTO `comment` VALUES (132, '我有', 19, '刘瑞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/749fce2d-9818-491b-8a49-acdfae970691.png', 78, 'seek', 0, '', '2025-06-28 01:37:26', 19);

-- ----------------------------
-- Table structure for dormitory
-- ----------------------------
DROP TABLE IF EXISTS `dormitory`;
CREATE TABLE `dormitory`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '分类名称',
  `status` int(0) NULL DEFAULT 1 COMMENT '分类状态 0:禁用，1:启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_dormitory_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 36 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '宿舍分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dormitory
-- ----------------------------
INSERT INTO `dormitory` VALUES (1, '8号宿舍楼', 1, '2024-05-29 15:51:20', '2025-06-27 23:43:53', 1, 1);
INSERT INTO `dormitory` VALUES (23, '9号宿舍楼', 1, '2024-06-02 00:23:48', '2025-06-27 23:43:59', 1, 1);
INSERT INTO `dormitory` VALUES (24, '10号宿舍楼', 1, '2024-06-02 00:23:54', '2025-06-27 23:44:04', 1, 1);
INSERT INTO `dormitory` VALUES (25, '11号宿舍楼', 1, '2024-06-02 00:23:58', '2025-06-27 23:44:09', 1, 1);
INSERT INTO `dormitory` VALUES (28, '12号宿舍楼', 1, '2024-06-02 20:07:13', '2025-06-27 23:44:14', 1, 1);
INSERT INTO `dormitory` VALUES (29, '7号宿舍楼', 0, '2024-06-02 20:08:17', '2025-06-27 23:44:19', 1, 1);
INSERT INTO `dormitory` VALUES (30, '6号宿舍楼', 1, '2024-06-02 20:08:29', '2025-06-27 23:44:24', 1, 1);
INSERT INTO `dormitory` VALUES (31, '10号教学楼', 1, '2024-06-11 15:54:12', '2024-06-11 15:54:12', 1, 1);
INSERT INTO `dormitory` VALUES (32, '9号教学楼', 1, '2024-06-11 15:54:22', '2024-06-11 15:54:22', 1, 1);
INSERT INTO `dormitory` VALUES (33, '8号教学楼', 1, '2024-06-11 15:54:35', '2024-06-11 15:54:35', 1, 1);
INSERT INTO `dormitory` VALUES (34, '7号教学楼', 1, '2024-06-11 15:54:46', '2024-06-11 15:54:46', 1, 1);
INSERT INTO `dormitory` VALUES (35, '6号教学楼', 1, '2024-06-11 15:54:54', '2024-06-11 15:54:54', 1, 1);

-- ----------------------------
-- Table structure for forum
-- ----------------------------
DROP TABLE IF EXISTS `forum`;
CREATE TABLE `forum`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '分类名称',
  `status` int(0) NULL DEFAULT 1 COMMENT '分类状态 0:禁用，1:启用',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_forum_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '论坛分类' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of forum
-- ----------------------------
INSERT INTO `forum` VALUES (1, '生活娱乐', 1, '2024-05-29 15:51:20', '2024-06-13 08:38:01', 1, 1);
INSERT INTO `forum` VALUES (2, '影视电影', 1, '2024-05-29 15:51:20', '2024-05-29 15:51:20', 1, 1);
INSERT INTO `forum` VALUES (3, '音乐演唱', 1, '2024-05-29 15:51:20', '2024-05-29 15:51:20', 1, 1);
INSERT INTO `forum` VALUES (4, '综艺娱乐', 1, '2024-05-29 15:51:20', '2024-05-29 15:51:20', 1, 1);
INSERT INTO `forum` VALUES (5, '时尚美妆', 1, '2024-05-29 15:51:20', '2024-05-29 15:51:20', 1, 1);
INSERT INTO `forum` VALUES (6, '明星八卦', 1, '2024-05-29 15:51:20', '2024-05-29 15:51:20', 1, 1);

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '商品名称',
  `category_id` bigint(0) NOT NULL COMMENT '商品分类id',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品价格',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '图片',
  `olds` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '成色',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '描述信息',
  `status` int(0) NULL DEFAULT 1 COMMENT '0 下架 1 起售 2 强制下架',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  `collection_total` bigint(0) NULL DEFAULT 0 COMMENT '收藏总数',
  `praise_total` bigint(0) NULL DEFAULT 0 COMMENT '点赞总数',
  `comment_total` bigint(0) NULL DEFAULT 0 COMMENT '评论总数',
  `look_total` bigint(0) NULL DEFAULT 0 COMMENT '浏览总数',
  `popularity` bigint(0) NULL DEFAULT 0 COMMENT '人气值',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_goods_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 91 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '商品' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods
-- ----------------------------
INSERT INTO `goods` VALUES (70, 'Nick球鞋', 1, 159.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/02faa446-a3b6-46a5-a8f4-afae34e668d8.jpg', '九成新', '<p> </p>', 1, '2024-06-03 15:00:11', '2024-06-12 08:12:16', 9, 1, 0, 0, 0, 7, 5);
INSERT INTO `goods` VALUES (71, '20打包全部', 27, 20.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4b1fd0ba-3518-493e-bbf0-e7bf8732f029.jpeg', '十成新', '<p>刚买没多久，几乎全新，自提！！！</p>', 1, '2024-06-03 16:09:19', '2025-06-27 23:41:41', 9, 1, 3, 4, 0, 12, 61);
INSERT INTO `goods` VALUES (73, '李宁篮球', 26, 129.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4d2f4d4a-617f-4be9-b778-b3f51c2a9652.jpg', '十成新', '<p>刚买的篮球全新，手里缺钱，低价出了</p>', 1, '2024-06-03 19:48:44', '2024-06-13 17:19:29', 9, 17, 0, 1, 0, 7, 11);
INSERT INTO `goods` VALUES (74, '黑色风衣', 1, 149.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/67d2e52c-446d-4be4-b07f-e424d87ce4ba.png', '八成新', '<p>几乎全新，穿过几次，吃胖了，不合身了，低价出，包邮！！！</p>', 1, '2024-06-03 19:58:28', '2025-06-28 01:33:56', 9, 19, 1, 2, 0, 8, 44);
INSERT INTO `goods` VALUES (75, '乔丹球鞋', 1, 199.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/08bbd2aa-00c4-40ea-9c5e-4912f5e0e844.jpg', '八成新', '<p> </p>', 1, '2024-06-03 20:28:52', '2025-06-25 01:01:06', 9, 18, 0, 2, 0, 9, 24);
INSERT INTO `goods` VALUES (76, '蓝色牛仔裤', 1, 39.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/2f575ee1-22cb-4afb-a50c-555dd3dcdf2f.jpg', '十成新', '<p> 全新，买回来就小了，懒得退，便宜出了！！！</p>', 1, '2024-06-03 20:50:50', '2025-06-28 01:26:36', 9, 19, 2, 2, 0, 11, 54);
INSERT INTO `goods` VALUES (77, '皮鞋', 1, 200.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/f9108061-88c7-4a81-8915-2cebb8650007.jpg', '九成新', '<p>刚买一个月，不怎么穿，低价出了！！！</p>', 1, '2024-06-05 16:17:26', '2024-06-12 08:12:23', 9, 1, 0, 0, 0, 8, 18);
INSERT INTO `goods` VALUES (78, '衣服架', 1, 2.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/f47f4f53-d405-45c2-8af0-01a4b60fcfbd.jpg', '九成新', '<p> </p>', 1, '2024-06-05 20:31:24', '2024-06-13 17:13:47', 9, 17, 1, 0, 0, 8, 21);
INSERT INTO `goods` VALUES (79, '苹果12，8成新', 25, 1500.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/d5c87159-f634-4f1b-85a3-c95c967f3a08.jpg', '八成新', '<p>低价出了！！！！自提，刀手勿扰</p>', 1, '2024-06-06 13:50:19', '2025-06-28 00:08:18', 9, 20, 1, 8, 0, 9, 159);
INSERT INTO `goods` VALUES (81, '洗衣机，自提', 1, 299.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/996db16c-cf11-400c-b587-3a5c0c04722d.jpeg', '八成新', '<p>毕业了，低价处理</p>', 1, '2024-06-06 14:11:34', '2025-06-27 23:47:34', 17, 19, 4, 6, 0, 13, 87);
INSERT INTO `goods` VALUES (83, '数码相机', 25, 599.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/8acf6905-1247-4f42-a78c-3524bf5cac7b.jpg', '八成新', '<p>毕业了，低价出了！！！</p>', 1, '2024-06-12 17:21:13', '2024-06-13 22:21:38', 17, 17, 0, 0, 0, 2, 2);
INSERT INTO `goods` VALUES (84, '欧文5', 1, 299.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/60b7bd44-deca-4a0e-a359-e730cbcc83c9.jpg', '九成新', '<p>挺干净的，最近缺钱，低价出了！！！</p>', 1, '2024-06-14 22:31:29', '2025-06-27 23:56:44', 9, 19, 0, 1, 0, 1, 6);
INSERT INTO `goods` VALUES (85, '考研真相', 23, 5.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/b1cb7c14-c95c-41ee-bfe6-5e2de04d5bd4.jfif', '十成新', '<p>新买的，放弃考研了，低价出了！！！</p>', 1, '2024-06-14 22:36:48', '2024-06-14 22:36:48', 9, 9, 0, 0, 0, 2, 2);
INSERT INTO `goods` VALUES (86, '耳机', 25, 99.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/bf3d4552-eaa3-4180-a451-0b3c50676d50.jpg', '九成新', '<p> 耳机挺好看的，性能良好，卖了！！</p>', 1, '2024-06-14 22:38:36', '2024-06-14 22:38:36', 9, 9, 0, 0, 0, 1, 1);
INSERT INTO `goods` VALUES (87, '雅马哈吉他', 32, 999.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/20aa8380-186b-48e0-ae74-bfe1a2eba978.jfif', '九成新', '<p> 吉他完整，无瑕疵，没有时间弹了，卖了！！</p>', 1, '2024-06-14 22:40:05', '2025-06-27 17:33:21', 9, 19, 1, 1, 0, 2, 17);
INSERT INTO `goods` VALUES (88, '小电驴，轻微掉漆', 32, 600.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/ba18f627-26a6-4579-9df6-e8c1a035a2bf.png', '八成新', '<p> 小电驴，轻微掉漆，无其他问题，可试开</p>', 1, '2025-06-27 23:39:42', '2025-06-27 23:47:52', 19, 19, 1, 1, 0, 1, 16);
INSERT INTO `goods` VALUES (89, '自行车，几乎全新', 26, 500.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/f384e971-00a6-4df8-8b44-13d13167843a.jpeg', '十成新', '<p> 自行车，几乎全新，实习用不上了</p>', 1, '2025-06-27 23:56:21', '2025-06-28 01:34:32', 19, 20, 1, 2, 0, 3, 23);
INSERT INTO `goods` VALUES (90, '13pro九成新', 25, 2599.00, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/a713121b-872c-4fd4-8630-16aa62ba5555.jpeg', '九成新', '<p> 13pro九成新</p>', 1, '2025-06-28 01:35:59', '2025-06-28 01:35:59', 20, 20, 0, 0, 0, 1, 1);

-- ----------------------------
-- Table structure for goods_detail
-- ----------------------------
DROP TABLE IF EXISTS `goods_detail`;
CREATE TABLE `goods_detail`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '商品名称',
  `user_id` bigint(0) NOT NULL COMMENT '用户Id',
  `goods_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `collection` int(0) NOT NULL DEFAULT 0 COMMENT '收藏',
  `praise` int(0) NOT NULL DEFAULT 0 COMMENT '点赞',
  `comment` int(0) NOT NULL DEFAULT 0 COMMENT '评论',
  `look` int(0) NOT NULL DEFAULT 0 COMMENT '浏览',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 89 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '商品详细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of goods_detail
-- ----------------------------
INSERT INTO `goods_detail` VALUES (34, '黑色风衣', 17, 74, 1, 1, 0, 1, '2024-06-12 08:35:34', '2025-06-28 01:33:56', 17, 19);
INSERT INTO `goods_detail` VALUES (36, 'Nick球鞋', 17, 70, 0, 0, 0, 1, '2024-06-12 08:36:00', '2024-06-12 08:36:00', 17, 17);
INSERT INTO `goods_detail` VALUES (37, '李宁篮球', 17, 73, 0, 1, 0, 1, '2024-06-12 08:36:04', '2024-06-13 17:19:29', 17, 17);
INSERT INTO `goods_detail` VALUES (38, '衣服架', 17, 78, 1, 0, 0, 1, '2024-06-12 08:36:11', '2024-06-13 17:13:47', 17, 17);
INSERT INTO `goods_detail` VALUES (39, '乔丹球鞋', 17, 75, 0, 1, 0, 1, '2024-06-12 08:36:16', '2025-06-25 01:01:06', 17, 18);
INSERT INTO `goods_detail` VALUES (40, '皮鞋', 17, 77, 0, 0, 0, 1, '2024-06-12 08:36:23', '2024-06-12 08:36:23', 17, 17);
INSERT INTO `goods_detail` VALUES (51, '黑色风衣', 9, 74, 1, 1, 0, 1, '2024-06-12 16:53:15', '2025-06-28 01:33:56', 9, 19);
INSERT INTO `goods_detail` VALUES (52, '李宁篮球', 9, 73, 0, 1, 0, 1, '2024-06-12 16:53:20', '2024-06-13 17:19:29', 9, 17);
INSERT INTO `goods_detail` VALUES (53, '乔丹球鞋', 9, 75, 0, 1, 0, 1, '2024-06-12 16:53:33', '2025-06-25 01:01:06', 9, 18);
INSERT INTO `goods_detail` VALUES (55, '数码相机', 17, 83, 0, 0, 0, 1, '2024-06-13 22:21:43', '2024-06-13 22:21:43', 17, 17);
INSERT INTO `goods_detail` VALUES (56, '蓝色牛仔裤', 17, 76, 1, 1, 0, 1, '2024-06-14 11:35:13', '2025-06-28 01:26:36', 17, 19);
INSERT INTO `goods_detail` VALUES (57, '蓝色牛仔裤', 9, 76, 1, 1, 0, 1, '2024-06-14 21:49:48', '2025-06-28 01:26:36', 9, 19);
INSERT INTO `goods_detail` VALUES (58, '雅马哈吉他', 17, 87, 1, 1, 0, 1, '2024-06-14 22:55:00', '2025-06-27 17:33:21', 17, 19);
INSERT INTO `goods_detail` VALUES (59, '考研真相', 17, 85, 0, 0, 0, 1, '2024-06-14 22:55:04', '2024-06-14 22:55:04', 17, 17);
INSERT INTO `goods_detail` VALUES (60, '耳机', 17, 86, 0, 0, 0, 1, '2024-06-14 22:55:10', '2024-06-14 22:55:10', 17, 17);
INSERT INTO `goods_detail` VALUES (64, '乔丹球鞋', 18, 75, 0, 1, 0, 1, '2025-06-25 01:00:48', '2025-06-25 01:01:06', 18, 18);
INSERT INTO `goods_detail` VALUES (65, '皮鞋', 18, 77, 0, 0, 0, 1, '2025-06-25 01:01:01', '2025-06-25 01:01:01', 18, 18);
INSERT INTO `goods_detail` VALUES (66, 'Nick球鞋', 18, 70, 0, 0, 0, 1, '2025-06-25 01:36:25', '2025-06-25 01:36:25', 18, 18);
INSERT INTO `goods_detail` VALUES (67, '衣服架', 19, 78, 0, 0, 0, 1, '2025-06-27 01:30:04', '2025-06-27 01:30:04', 19, 19);
INSERT INTO `goods_detail` VALUES (68, '乔丹球鞋', 19, 75, 0, 0, 0, 1, '2025-06-27 01:31:02', '2025-06-27 01:31:02', 19, 19);
INSERT INTO `goods_detail` VALUES (70, '蓝色牛仔裤', 19, 76, 1, 1, 0, 1, '2025-06-27 17:31:28', '2025-06-28 01:26:36', 19, 19);
INSERT INTO `goods_detail` VALUES (71, '皮鞋', 19, 77, 0, 0, 0, 1, '2025-06-27 17:31:41', '2025-06-27 17:31:41', 19, 19);
INSERT INTO `goods_detail` VALUES (72, '雅马哈吉他', 19, 87, 1, 1, 0, 1, '2025-06-27 17:31:52', '2025-06-27 17:33:21', 19, 19);
INSERT INTO `goods_detail` VALUES (75, '黑色风衣', 19, 74, 1, 1, 0, 1, '2025-06-27 17:33:31', '2025-06-28 01:33:56', 19, 19);
INSERT INTO `goods_detail` VALUES (76, '小电驴，轻微掉漆', 19, 88, 1, 1, 0, 1, '2025-06-27 23:39:46', '2025-06-27 23:47:52', 19, 19);
INSERT INTO `goods_detail` VALUES (77, '考研真相', 19, 85, 0, 0, 0, 1, '2025-06-27 23:39:50', '2025-06-27 23:39:50', 19, 19);
INSERT INTO `goods_detail` VALUES (78, '欧文5', 19, 84, 0, 1, 0, 1, '2025-06-27 23:40:02', '2025-06-27 23:56:44', 19, 19);
INSERT INTO `goods_detail` VALUES (79, '苹果12，8成新', 19, 79, 1, 1, 0, 1, '2025-06-27 23:47:19', '2025-06-28 00:08:18', 19, 20);
INSERT INTO `goods_detail` VALUES (80, '洗衣机，自提', 19, 81, 1, 1, 0, 1, '2025-06-27 23:47:21', '2025-06-27 23:47:34', 19, 19);
INSERT INTO `goods_detail` VALUES (81, '20打包全部', 19, 71, 0, 0, 0, 1, '2025-06-27 23:47:23', '2025-06-27 23:47:23', 19, 19);
INSERT INTO `goods_detail` VALUES (83, '苹果12，8成新', 20, 79, 1, 1, 0, 1, '2025-06-28 00:07:57', '2025-06-28 00:08:18', 20, 20);
INSERT INTO `goods_detail` VALUES (84, '洗衣机，自提', 20, 81, 0, 0, 0, 1, '2025-06-28 00:09:09', '2025-06-28 00:09:09', 20, 20);
INSERT INTO `goods_detail` VALUES (86, '自行车，几乎全新', 20, 89, 1, 1, 0, 1, '2025-06-28 01:34:26', '2025-06-28 01:34:32', 20, 20);
INSERT INTO `goods_detail` VALUES (87, '13pro九成新', 20, 90, 0, 0, 0, 1, '2025-06-28 01:36:03', '2025-06-28 01:36:03', 20, 20);
INSERT INTO `goods_detail` VALUES (88, '蓝色牛仔裤', 20, 76, 0, 0, 0, 1, '2025-06-28 01:39:07', '2025-06-28 01:39:07', 20, 20);
INSERT INTO `goods_detail` VALUES (89, '20打包全部', 20, 71, 0, 0, 0, 1, '2025-06-28 01:39:10', '2025-06-28 01:39:10', 20, 20);

-- ----------------------------
-- Table structure for order_detail
-- ----------------------------
DROP TABLE IF EXISTS `order_detail`;
CREATE TABLE `order_detail`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '名字',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '图片',
  `order_id` bigint(0) NOT NULL COMMENT '订单id',
  `goods_id` bigint(0) NULL DEFAULT NULL COMMENT '商品id',
  `number` int(0) NOT NULL DEFAULT 1 COMMENT '数量',
  `amount` decimal(10, 2) NOT NULL COMMENT '金额',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of order_detail
-- ----------------------------
INSERT INTO `order_detail` VALUES (5, '张飞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 4, 71, 1, 100.00);
INSERT INTO `order_detail` VALUES (6, '张飞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 5, 71, 1, 100.00);
INSERT INTO `order_detail` VALUES (7, '张飞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 6, 71, 1, 100.00);
INSERT INTO `order_detail` VALUES (8, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 7, 71, 1, 100.00);
INSERT INTO `order_detail` VALUES (9, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/ee425aec-cb0d-4f54-93b2-6ccc307953ab.jpg', 8, 79, 1, 399.00);
INSERT INTO `order_detail` VALUES (10, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/67d2e52c-446d-4be4-b07f-e424d87ce4ba.png', 9, 74, 1, 149.00);
INSERT INTO `order_detail` VALUES (11, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 10, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (12, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 11, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (13, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 12, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (14, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 13, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (15, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 14, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (16, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 15, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (17, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 16, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (18, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 17, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (19, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 18, 71, 1, 100.00);
INSERT INTO `order_detail` VALUES (20, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 19, 71, 1, 100.00);
INSERT INTO `order_detail` VALUES (21, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/0d29e11b-cd82-4915-8855-cce699196ee1.png', 20, 81, 1, 299.00);
INSERT INTO `order_detail` VALUES (22, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 21, 71, 1, 100.00);
INSERT INTO `order_detail` VALUES (23, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 22, 71, 1, 100.00);
INSERT INTO `order_detail` VALUES (24, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4d2f4d4a-617f-4be9-b778-b3f51c2a9652.jpg', 23, 73, 1, 129.00);
INSERT INTO `order_detail` VALUES (25, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 24, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (26, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 25, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (27, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/ee425aec-cb0d-4f54-93b2-6ccc307953ab.jpg', 26, 79, 1, 399.00);
INSERT INTO `order_detail` VALUES (28, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 27, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (29, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 28, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (30, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 29, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (31, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/ee425aec-cb0d-4f54-93b2-6ccc307953ab.jpg', 30, 79, 1, 399.00);
INSERT INTO `order_detail` VALUES (32, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/ee425aec-cb0d-4f54-93b2-6ccc307953ab.jpg', 31, 79, 1, 399.00);
INSERT INTO `order_detail` VALUES (33, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/f9108061-88c7-4a81-8915-2cebb8650007.jpg', 32, 77, 1, 200.00);
INSERT INTO `order_detail` VALUES (34, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4d2f4d4a-617f-4be9-b778-b3f51c2a9652.jpg', 33, 73, 1, 129.00);
INSERT INTO `order_detail` VALUES (35, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 34, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (36, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4d2f4d4a-617f-4be9-b778-b3f51c2a9652.jpg', 35, 73, 1, 129.00);
INSERT INTO `order_detail` VALUES (37, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 36, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (38, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 37, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (39, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 38, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (40, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 39, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (41, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/ee425aec-cb0d-4f54-93b2-6ccc307953ab.jpg', 40, 79, 1, 399.00);
INSERT INTO `order_detail` VALUES (42, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4d2f4d4a-617f-4be9-b778-b3f51c2a9652.jpg', 41, 73, 1, 129.00);
INSERT INTO `order_detail` VALUES (43, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4d2f4d4a-617f-4be9-b778-b3f51c2a9652.jpg', 42, 73, 1, 129.00);
INSERT INTO `order_detail` VALUES (44, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4d2f4d4a-617f-4be9-b778-b3f51c2a9652.jpg', 43, 73, 1, 129.00);
INSERT INTO `order_detail` VALUES (45, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 44, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (46, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 45, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (47, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 46, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (48, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 47, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (49, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/ee425aec-cb0d-4f54-93b2-6ccc307953ab.jpg', 48, 79, 1, 399.00);
INSERT INTO `order_detail` VALUES (50, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 49, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (51, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 50, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (52, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 51, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (53, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/0d29e11b-cd82-4915-8855-cce699196ee1.png', 52, 81, 1, 299.00);
INSERT INTO `order_detail` VALUES (54, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 53, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (55, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 54, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (56, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/2f575ee1-22cb-4afb-a50c-555dd3dcdf2f.jpg', 55, 76, 1, 39.00);
INSERT INTO `order_detail` VALUES (57, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/2f575ee1-22cb-4afb-a50c-555dd3dcdf2f.jpg', 56, 76, 1, 39.00);
INSERT INTO `order_detail` VALUES (58, '张飞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/0d29e11b-cd82-4915-8855-cce699196ee1.png', 57, 81, 1, 299.00);
INSERT INTO `order_detail` VALUES (59, '张飞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/0d29e11b-cd82-4915-8855-cce699196ee1.png', 58, 81, 1, 299.00);
INSERT INTO `order_detail` VALUES (60, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 59, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (61, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/0d29e11b-cd82-4915-8855-cce699196ee1.png', 60, 81, 1, 299.00);
INSERT INTO `order_detail` VALUES (62, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/0d29e11b-cd82-4915-8855-cce699196ee1.png', 61, 81, 1, 299.00);
INSERT INTO `order_detail` VALUES (63, '关羽', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/c5ba6c9c-a8a2-448d-b65d-976dad65cebf.jpg', 62, 71, 1, 79.00);
INSERT INTO `order_detail` VALUES (64, NULL, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/ee425aec-cb0d-4f54-93b2-6ccc307953ab.jpg', 63, 79, 1, 399.00);
INSERT INTO `order_detail` VALUES (65, '李子涵', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/f384e971-00a6-4df8-8b44-13d13167843a.jpeg', 64, 89, 1, 500.00);
INSERT INTO `order_detail` VALUES (66, '刘瑞', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/2f575ee1-22cb-4afb-a50c-555dd3dcdf2f.jpg', 65, 76, 1, 39.00);
INSERT INTO `order_detail` VALUES (67, '李子涵', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/f384e971-00a6-4df8-8b44-13d13167843a.jpeg', 66, 89, 1, 500.00);

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `number` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '订单号',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '订单状态 1待付款 2待接单 3已接单 4运送中 5已完成 6已取消 7退款',
  `buy_id` bigint(0) NOT NULL COMMENT '下单用户',
  `sell_id` bigint(0) NOT NULL COMMENT '接单用户',
  `address_book_id` bigint(0) NOT NULL COMMENT '地址id',
  `order_time` datetime(0) NOT NULL COMMENT '下单时间',
  `checkout_time` datetime(0) NULL DEFAULT NULL COMMENT '结账时间',
  `pay_method` int(0) NOT NULL DEFAULT 1 COMMENT '支付方式 1微信,2支付宝',
  `pay_status` tinyint(0) NOT NULL DEFAULT 0 COMMENT '支付状态 0未支付 1已支付 2退款',
  `amount` decimal(10, 2) NOT NULL COMMENT '实收金额',
  `remark` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '备注',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '手机号',
  `address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '地址',
  `consignee` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '收货人',
  `cancel_reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '订单取消原因',
  `rejection_reason` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '订单拒绝原因',
  `cancel_time` datetime(0) NULL DEFAULT NULL COMMENT '订单取消时间',
  `begin_delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '交易开始时间',
  `end_delivery_time` datetime(0) NULL DEFAULT NULL COMMENT '交易成功时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 67 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of orders
-- ----------------------------
INSERT INTO `orders` VALUES (4, '1718173836075', 6, 9, 17, 5, '2024-06-12 14:30:36', NULL, 1, 0, 100.00, '测试定订单', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-12 15:54:52', NULL);
INSERT INTO `orders` VALUES (5, '1718173836393', 6, 9, 17, 5, '2024-06-12 14:30:36', NULL, 1, 0, 100.00, '测试定订单', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-12 15:54:52', NULL);
INSERT INTO `orders` VALUES (6, '1718175774731', 6, 9, 17, 5, '2024-06-12 15:02:55', NULL, 1, 0, 100.00, '测试定订单', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-12 15:54:52', NULL);
INSERT INTO `orders` VALUES (7, '1718183632944', 7, 17, 9, 5, '2024-06-12 17:13:53', NULL, 1, 0, 100.00, '测试定订单', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '无理由', NULL, '2024-06-14 03:00:59', '2024-06-12 15:54:52', NULL);
INSERT INTO `orders` VALUES (8, '1718184352402', 6, 17, 17, 5, '2024-06-12 17:25:52', NULL, 1, 0, 399.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-13 12:00:00', NULL);
INSERT INTO `orders` VALUES (9, '1718184684815', 6, 17, 17, 5, '2024-06-12 17:31:25', NULL, 1, 0, 149.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-05-29 12:00:00', NULL);
INSERT INTO `orders` VALUES (10, '1718184949680', 6, 17, 9, 5, '2024-06-12 17:35:50', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-05-29 12:00:00', NULL);
INSERT INTO `orders` VALUES (11, '1718185349682', 6, 17, 9, 5, '2024-06-12 17:42:30', NULL, 2, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-11 12:00:00', NULL);
INSERT INTO `orders` VALUES (12, '1718185945610', 6, 17, 9, 5, '2024-06-12 17:52:26', NULL, 2, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-19 12:00:00', NULL);
INSERT INTO `orders` VALUES (13, '1718186046825', 6, 17, 9, 5, '2024-06-12 17:54:07', NULL, 2, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-12 12:00:00', NULL);
INSERT INTO `orders` VALUES (14, '1718186578881', 6, 17, 9, 5, '2024-06-12 18:02:59', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-10 12:00:00', NULL);
INSERT INTO `orders` VALUES (15, '1718186664481', 6, 17, 9, 5, '2024-06-12 18:04:24', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-24 12:00:00', NULL);
INSERT INTO `orders` VALUES (16, '1718186703946', 6, 17, 9, 5, '2024-06-12 18:05:04', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-18 12:00:00', NULL);
INSERT INTO `orders` VALUES (17, '1718186931480', 6, 17, 9, 5, '2024-06-12 18:08:51', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-10 12:00:00', NULL);
INSERT INTO `orders` VALUES (18, '1718187250047', 6, 17, 9, 5, '2024-06-12 18:14:10', NULL, 1, 0, 100.00, '测试定订单', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-12 15:54:52', NULL);
INSERT INTO `orders` VALUES (19, '1718187294239', 6, 17, 9, 5, '2024-06-12 18:14:54', NULL, 1, 0, 100.00, '测试定订单', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-12 15:54:52', NULL);
INSERT INTO `orders` VALUES (20, '1718187407356', 6, 17, 17, 5, '2024-06-12 18:16:47', NULL, 1, 0, 299.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-03 12:00:00', NULL);
INSERT INTO `orders` VALUES (21, '1718187443908', 6, 17, 17, 5, '2024-06-12 18:17:24', NULL, 1, 0, 100.00, '测试定订单', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-12 15:54:52', NULL);
INSERT INTO `orders` VALUES (22, '1718187496976', 6, 17, 17, 5, '2024-06-12 18:18:17', NULL, 1, 0, 100.00, '测试定订单', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-12 15:54:52', NULL);
INSERT INTO `orders` VALUES (23, '1718187506133', 6, 17, 9, 5, '2024-06-12 18:18:26', NULL, 1, 0, 129.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-10 12:00:00', NULL);
INSERT INTO `orders` VALUES (24, '1718187514315', 6, 17, 9, 5, '2024-06-12 18:18:34', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-12 12:00:00', NULL);
INSERT INTO `orders` VALUES (25, '1718187550628', 6, 17, 9, 5, '2024-06-12 18:19:11', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-02 12:00:00', NULL);
INSERT INTO `orders` VALUES (26, '1718187589099', 6, 17, 9, 5, '2024-06-12 18:19:49', NULL, 1, 0, 399.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-05-29 12:00:00', NULL);
INSERT INTO `orders` VALUES (27, '1718187627282', 3, 17, 9, 5, '2024-06-12 18:20:27', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', NULL, NULL, NULL, '2024-06-13 12:00:00', NULL);
INSERT INTO `orders` VALUES (28, '1718187890897', 6, 17, 9, 5, '2024-06-12 18:24:51', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-17 12:00:00', NULL);
INSERT INTO `orders` VALUES (29, '1718187921723', 6, 17, 9, 5, '2024-06-12 18:25:22', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-11 12:00:00', NULL);
INSERT INTO `orders` VALUES (30, '1718187963713', 3, 17, 9, 5, '2024-06-12 18:26:04', NULL, 1, 0, 399.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', NULL, NULL, NULL, '2024-06-06 12:00:00', NULL);
INSERT INTO `orders` VALUES (31, '1718188008602', 3, 17, 9, 5, '2024-06-12 18:26:49', NULL, 2, 0, 399.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', NULL, NULL, NULL, '2024-05-29 12:00:00', NULL);
INSERT INTO `orders` VALUES (32, '1718188215824', 2, 17, 9, 5, '2024-06-12 18:30:16', '2024-06-14 02:45:51', 2, 1, 200.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', NULL, NULL, NULL, '2024-06-11 12:00:00', NULL);
INSERT INTO `orders` VALUES (33, '1718188271976', 6, 17, 9, 5, '2024-06-12 18:31:12', NULL, 1, 0, 129.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '嫌贵', NULL, '2024-06-14 03:05:21', '2024-06-11 12:00:00', NULL);
INSERT INTO `orders` VALUES (34, '1718188534752', 6, 17, 9, 5, '2024-06-12 18:35:35', NULL, 2, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-10 12:00:00', NULL);
INSERT INTO `orders` VALUES (35, '1718190311220', 6, 17, 9, 5, '2024-06-12 19:05:11', NULL, 2, 0, 129.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-12 19:05:09', NULL);
INSERT INTO `orders` VALUES (36, '1718190407299', 6, 17, 9, 5, '2024-06-12 19:06:47', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-11 12:00:00', NULL);
INSERT INTO `orders` VALUES (37, '1718190511161', 6, 17, 9, 5, '2024-06-12 19:08:31', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-11 12:00:00', NULL);
INSERT INTO `orders` VALUES (38, '1718190595847', 6, 17, 9, 5, '2024-06-12 19:09:56', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-03 12:00:00', NULL);
INSERT INTO `orders` VALUES (39, '1718190653708', 6, 17, 9, 5, '2024-06-12 19:10:54', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-12 12:00:00', NULL);
INSERT INTO `orders` VALUES (40, '1718190723316', 6, 17, 9, 5, '2024-06-12 19:12:03', NULL, 1, 0, 399.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-12 19:12:00', NULL);
INSERT INTO `orders` VALUES (41, '1718191129114', 6, 17, 9, 5, '2024-06-12 19:18:49', NULL, 2, 0, 129.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-10 12:00:00', NULL);
INSERT INTO `orders` VALUES (42, '1718191145256', 6, 17, 9, 5, '2024-06-12 19:19:05', NULL, 1, 0, 129.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-11 12:00:00', NULL);
INSERT INTO `orders` VALUES (43, '1718191257513', 6, 17, 9, 5, '2024-06-12 19:20:58', NULL, 1, 0, 129.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-11 12:00:00', NULL);
INSERT INTO `orders` VALUES (44, '1718191264179', 6, 17, 9, 5, '2024-06-12 19:21:04', NULL, 2, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-10 12:00:00', NULL);
INSERT INTO `orders` VALUES (45, '1718191330307', 6, 17, 9, 5, '2024-06-12 19:22:10', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-12 19:22:08', NULL);
INSERT INTO `orders` VALUES (46, '1718192564579', 6, 17, 9, 5, '2024-06-12 19:42:45', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-14 03:10:01', '2024-06-11 12:00:00', NULL);
INSERT INTO `orders` VALUES (47, '1718192645422', 3, 17, 9, 5, '2024-06-12 19:44:05', NULL, 1, 0, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', NULL, NULL, NULL, '2024-06-12 12:00:00', NULL);
INSERT INTO `orders` VALUES (48, '1718192776481', 2, 17, 9, 5, '2024-06-12 19:46:16', '2024-06-12 21:44:28', 2, 1, 399.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', NULL, NULL, NULL, '2024-06-05 12:00:00', NULL);
INSERT INTO `orders` VALUES (49, '1718193455852', 2, 17, 9, 5, '2024-06-12 19:57:36', '2024-06-12 19:57:40', 1, 1, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', NULL, NULL, NULL, '2024-06-11 12:00:00', NULL);
INSERT INTO `orders` VALUES (50, '1718193550314', 2, 17, 9, 5, '2024-06-12 19:59:10', '2024-06-12 19:59:12', 1, 1, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', NULL, NULL, NULL, '2024-06-12 12:00:00', NULL);
INSERT INTO `orders` VALUES (51, '1718193752994', 7, 17, 9, 5, '2024-06-12 20:02:33', '2024-06-12 20:02:36', 1, 1, 79.00, '123455', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', NULL, '订单数量较多，暂时无法接单', '2024-06-14 01:11:30', '2024-06-12 20:02:28', NULL);
INSERT INTO `orders` VALUES (52, '1718245777062', 5, 17, 17, 5, '2024-06-13 10:29:37', '2024-06-13 10:29:40', 1, 1, 299.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', NULL, NULL, NULL, '2024-06-13 10:29:35', '2024-06-14 00:17:20');
INSERT INTO `orders` VALUES (53, '1718246722921', 5, 17, 9, 5, '2024-06-13 10:45:23', '2024-06-13 10:45:34', 2, 1, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', NULL, NULL, NULL, '2024-06-13 10:45:13', NULL);
INSERT INTO `orders` VALUES (54, '1718252815147', 5, 17, 9, 5, '2024-06-13 12:26:55', '2024-06-13 12:27:29', 2, 1, 79.00, '00', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', NULL, NULL, NULL, '2024-06-13 12:26:49', NULL);
INSERT INTO `orders` VALUES (55, '1718274106214', 7, 17, 9, 5, '2024-06-13 18:21:46', '2024-06-13 18:21:49', 1, 1, 39.00, '匹配 ', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '最近比较忙，没有时间时间交易', NULL, '2024-06-14 01:13:20', '2024-06-13 17:31:05', NULL);
INSERT INTO `orders` VALUES (56, '1718274119094', 7, 17, 9, 5, '2024-06-13 18:21:59', '2024-06-13 18:22:00', 2, 1, 39.00, '45', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '不想卖了，交易不了', NULL, '2024-06-14 01:12:21', '2024-06-29 12:00:00', NULL);
INSERT INTO `orders` VALUES (57, '1718300322190', 2, 9, 17, 6, '2024-06-14 01:38:42', '2024-06-14 01:38:43', 2, 1, 299.00, 'wddw', '12345678910', '河南城建学院/土木学院/菊园8号/505宿舍', '张飞', NULL, NULL, NULL, '2024-06-14 01:38:39', NULL);
INSERT INTO `orders` VALUES (58, '1718300332418', 2, 9, 17, 6, '2024-06-14 01:38:52', '2024-06-14 01:38:54', 1, 1, 299.00, '', '12345678910', '河南城建学院/土木学院/菊园8号/505宿舍', '张飞', NULL, NULL, NULL, '2024-06-26 12:00:00', NULL);
INSERT INTO `orders` VALUES (59, '1718304110757', 2, 17, 9, 5, '2024-06-14 02:41:51', '2024-06-14 02:45:07', 2, 1, 79.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', NULL, NULL, NULL, '2024-06-10 12:00:00', NULL);
INSERT INTO `orders` VALUES (60, '1718352357897', 2, 17, 17, 5, '2024-06-14 16:05:58', '2024-06-14 16:06:00', 2, 1, 299.00, '', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', NULL, NULL, NULL, '2024-06-14 16:05:52', NULL);
INSERT INTO `orders` VALUES (61, '1718469523168', 6, 17, 17, 5, '2024-06-16 00:38:43', NULL, 2, 0, 299.00, '很喜欢，买了', '1512345678', '河南城建学院/汉语言学院/菊园9号/509宿舍', '貂蝉', '订单超时，自动取消', NULL, '2024-06-16 00:54:00', '2024-06-16 00:38:21', NULL);
INSERT INTO `orders` VALUES (62, '1722480848054', 6, 17, 9, 3, '2024-08-01 10:54:08', NULL, 2, 0, 79.00, '', '13123718520', '河南城建学院/计算机学院/菊园8号/608宿舍', '李萌', '订单超时，自动取消', NULL, '2024-08-01 16:55:00', '2024-08-13 12:00:00', NULL);
INSERT INTO `orders` VALUES (63, '1750783513243', 2, 18, 9, 7, '2025-06-25 00:45:13', '2025-06-25 00:45:15', 1, 1, 399.00, '312321', '13566365236', '河南城建学院/医学院/菊园8号/3213123213', '123', NULL, NULL, NULL, '2025-06-25 12:00:00', NULL);
INSERT INTO `orders` VALUES (64, '1751040567630', 5, 20, 19, 8, '2025-06-28 00:09:28', '2025-06-28 00:09:29', 1, 1, 500.00, '3123123', '1651561131', '河南城建学院/计算机学院/8号宿舍楼/楼下自提', '李子涵', NULL, NULL, NULL, '2025-06-28 12:00:00', '2025-06-28 00:09:50');
INSERT INTO `orders` VALUES (65, '1751045203793', 2, 19, 9, 9, '2025-06-28 01:26:44', '2025-06-28 01:26:46', 1, 1, 39.00, '', '12321312321', '河南城建学院/计算机学院/8号宿舍楼/123123213', '马隆', NULL, NULL, NULL, '2025-06-28 12:00:00', NULL);
INSERT INTO `orders` VALUES (66, '1751045681618', 5, 20, 19, 8, '2025-06-28 01:34:42', '2025-06-28 01:34:43', 1, 1, 500.00, '', '1651561131', '河南城建学院/计算机学院/8号宿舍楼/楼下自提', '李子涵', NULL, NULL, NULL, '2025-06-28 12:00:00', '2025-06-28 01:35:07');

-- ----------------------------
-- Table structure for posts
-- ----------------------------
DROP TABLE IF EXISTS `posts`;
CREATE TABLE `posts`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '帖子名称',
  `category_id` bigint(0) NOT NULL COMMENT '帖子分类id',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '图片',
  `description` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '描述信息',
  `status` int(0) NULL DEFAULT 1 COMMENT '0 禁用 1 启用',
  `comment_total` bigint(0) NULL DEFAULT 0 COMMENT '评论总数',
  `popularity` bigint(0) NULL DEFAULT 0 COMMENT '人气值',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_posts_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 80 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '帖子表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of posts
-- ----------------------------
INSERT INTO `posts` VALUES (70, '《云边有个小卖部》新预告 刘十三程霜天台对视', 2, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/7887583a-6bc0-43b5-9228-8417865f688e.jpg', '<p> 将于6月22日公映的电影《云边有个小卖部》今日发布“十三程霜”预告和人物海报，重回云边镇的刘十三（彭昱畅 饰），正在为如何一年完成一千单保险发愁的时候，童年好友程霜（周也 饰）为他瞄准了第一个“客户”，同样也是一位熟人——刘十三小时候的同桌牛大田（孔连顺 饰）。程霜绘声绘色地讲述牛大田的资料时，刘十三猝不及防轻声唤出了她的名字，这一幕动人心弦。童年好友多年后重逢再结伴，云边镇的画卷也将徐徐展开，期待着更多观众在这个美妙动人的夏天与云边镇重逢。</p>', 1, 0, 99, '2024-06-13 09:40:10', '2024-06-13 22:43:27', 1, 1);
INSERT INTO `posts` VALUES (71, '寻找麻友', 1, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/8f5c9a2e-8098-4599-8348-5b82db14271e.jpg', '<p> 城建有没有一起打麻将的小伙伴，一起玩呀，ddd</p>', 1, 0, 9999, '2024-06-13 09:40:57', '2024-06-13 09:40:57', 1, 1);
INSERT INTO `posts` VALUES (72, '近期强对流天气为何这么多？', 1, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/5b1bf97f-1929-4f19-ac3a-af76af73b442.jfif', '<p><span style=\"background-color: rgb(244, 246, 248); color: rgb(36, 41, 47);\">春季和夏季是强对流天气最为活跃的季节。当冷空气与暖湿空气相遇时，会形成不稳定的气象条件，从而产生雷暴、龙卷风等强对流天气现象。</span></p>', 1, 0, 22, '2024-06-13 10:55:00', '2024-06-13 23:00:16', 17, 1);
INSERT INTO `posts` VALUES (75, '432432', 1, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/cceb771a-b69b-42b9-b640-d60a3acd8327.png', '<p> 432432</p>', 1, 0, 0, '2025-06-25 00:44:09', '2025-06-25 00:44:09', 18, 18);
INSERT INTO `posts` VALUES (76, '1', 1, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/1da75b2a-18fd-4f02-8704-7dd8ccfc6407.png', '<p> 123213</p>', 1, 0, 0, '2025-06-25 00:52:59', '2025-06-25 00:52:59', 18, 18);
INSERT INTO `posts` VALUES (77, '4545', 1, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/d261d7d9-26a5-4480-94d5-179a95c36948.png', '<p> 4545</p>', 1, 0, 0, '2025-06-25 00:54:37', '2025-06-25 00:54:37', 18, 18);
INSERT INTO `posts` VALUES (78, '凳子谁要，自提不收费', 1, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/63b71927-59a4-423f-b8a7-143c42df84c3.jpg', '<p> 凳子谁要，自提不收费</p>', 1, 0, 0, '2025-06-27 23:46:07', '2025-06-27 23:46:07', 19, 19);
INSERT INTO `posts` VALUES (79, '求一个14pro', 1, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/8677a404-f236-4ab9-9959-4d38437fb351.jpg', '<p> 31232132</p>', 1, 0, 0, '2025-06-28 01:36:49', '2025-06-28 01:36:49', 20, 20);

-- ----------------------------
-- Table structure for seek
-- ----------------------------
DROP TABLE IF EXISTS `seek`;
CREATE TABLE `seek`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '商品名称',
  `category_id` bigint(0) NOT NULL COMMENT '商品分类id',
  `image` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '图片',
  `description` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '描述信息',
  `status` int(0) NULL DEFAULT 1 COMMENT '0 下架 1 起售 2 强制下架',
  `comment_total` bigint(0) NULL DEFAULT 0 COMMENT '评论总数',
  `popularity` bigint(0) NULL DEFAULT 0 COMMENT '人气值',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `create_user` bigint(0) NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_goods_name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 79 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '求购表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of seek
-- ----------------------------
INSERT INTO `seek` VALUES (75, '31312321', 28, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/5a5aec56-e4e5-443f-8f7e-fe4bb5a0f0bb.png', '<p> 3123213</p>', 1, 0, 0, '2025-06-25 01:00:05', '2025-06-25 01:00:05', 18, 18);
INSERT INTO `seek` VALUES (76, '求一个15pro，9成新的', 25, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/0067f96b-7443-4b7a-a409-6eac0cc64dd4.jpeg', '<p> 求一个15pro，9成新的</p>', 2, 0, 0, '2025-06-27 23:47:02', '2025-06-28 01:22:17', 19, 19);
INSERT INTO `seek` VALUES (77, '求一个洗衣机', 27, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/b846f92a-f4c1-450a-849e-1014b39e9bed.jpeg', '<p> 1111111111</p>', 1, 0, 0, '2025-06-28 01:22:56', '2025-06-28 01:22:56', 19, 19);
INSERT INTO `seek` VALUES (78, '求一个自行车', 26, 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/a8e3ad38-8c14-4c56-a00a-e1d829ecd5db.jpeg', '<p> 1</p>', 2, 0, 0, '2025-06-28 01:37:08', '2025-06-28 01:37:42', 20, 20);

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(0) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '姓名',
  `username` varchar(32) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL COMMENT '密码',
  `phone` varchar(11) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '手机号',
  `sex` varchar(2) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '性别',
  `email` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '邮箱',
  `avatar` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NULL DEFAULT NULL COMMENT '头像',
  `college_id` bigint(0) NULL DEFAULT NULL COMMENT '学院id',
  `dormitory_id` bigint(0) NULL DEFAULT NULL COMMENT '宿舍楼id',
  `status` int(0) NOT NULL DEFAULT 1 COMMENT '权限状态 0:管理员，1:用户',
  `locking` int(0) NULL DEFAULT 1 COMMENT '账号状态 0:异常 1:正常',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint(0) NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `idx_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_bin COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '管理员', 'admin', 'e10adc3949ba59abbe56e057f20f883e', '13123718520', '1', '2016513384@qq.com', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/aff4f69d-1c32-4f90-bf6a-b5c41e7619dc.png', 1, 23, 0, 1, '2024-05-29 15:51:20', '2025-06-27 01:29:01', 1);
INSERT INTO `user` VALUES (3, '王五', 'wangwu', 'e10adc3949ba59abbe56e057f20f883e', '13123718520', '1', '2016513384@qq.com', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/3478c52d-a17f-4b03-b7b5-0781a9876099.jpg', 23, 29, 1, 1, '2024-06-01 19:19:05', '2024-06-03 15:52:14', 1);
INSERT INTO `user` VALUES (4, '张三', 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e', '13123718520', '0', '2016513384@qq.com', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/9a47c791-2e3b-47e1-a58c-99ae66ea5171.jpg', 29, 29, 1, 1, '2024-06-03 15:54:28', '2024-06-04 15:25:56', 1);
INSERT INTO `user` VALUES (5, '周六', 'zhouliu', 'e10adc3949ba59abbe56e057f20f883e', '13123718520', '1', '2016513384@qq.com', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/b4a7c893-3e6c-415c-b9ea-fb17248359e9.jpg', 30, 23, 1, 1, '2024-06-03 15:54:38', '2024-06-03 15:57:49', 1);
INSERT INTO `user` VALUES (6, '刘七', 'liuqi', 'e10adc3949ba59abbe56e057f20f883e', '13123718520', '1', '2016513384@qq.com', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/6388997f-a771-4e88-86c5-42965d216588.jpg', 23, 28, 1, 1, '2024-06-03 15:54:44', '2024-06-03 17:00:58', 1);
INSERT INTO `user` VALUES (7, '韩八', 'hanba', 'e10adc3949ba59abbe56e057f20f883e', '13123718520', '1', '2016513384@qq.com', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/e9f15d63-f4e6-44f5-93f1-f298b426311f.jpg', 25, 24, 1, 1, '2024-06-03 15:54:52', '2024-06-03 15:56:10', 1);
INSERT INTO `user` VALUES (9, '张飞', 'zhangfei', 'e10adc3949ba59abbe56e057f20f883e', '13123718520', '1', '2016513384@qq.com', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/1a5c209d-e72c-4d90-b9c0-93b2cb110feb.jpg', 25, 25, 1, 1, '2024-06-04 13:19:51', '2024-07-20 16:29:05', 1);
INSERT INTO `user` VALUES (17, '关羽', 'guanyu', 'e10adc3949ba59abbe56e057f20f883e', '13123718520', '1', '2016513384@qq.com', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/4ef6d196-f916-4fc6-83a7-dc76e78ccdf2.jpg', 27, 23, 1, 1, '2024-06-05 15:09:59', '2024-06-10 20:40:44', 17);
INSERT INTO `user` VALUES (18, '57575', 'jli123', 'e10adc3949ba59abbe56e057f20f883e', '3123123', '1', '312343@qq.com', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/d5785703-8675-4f2e-b090-415ea5602585.png', 30, 1, 1, 1, '2025-06-25 00:30:45', '2025-06-25 01:04:46', 18);
INSERT INTO `user` VALUES (19, '刘瑞', 'l1234', 'e10adc3949ba59abbe56e057f20f883e', '123443243', '1', '312312321@qq.com', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/749fce2d-9818-491b-8a49-acdfae970691.png', 41, 33, 1, 1, '2025-06-27 01:29:17', '2025-06-28 01:40:53', 19);
INSERT INTO `user` VALUES (20, '李子涵', 'y1233', 'e10adc3949ba59abbe56e057f20f883e', NULL, '0', '312321@qq.com', 'https://web-sky-lm.oss-cn-beijing.aliyuncs.com/ea677ed9-1222-49ea-909e-f87363a7dadf.png', 1, 24, 1, 1, '2025-06-28 00:07:05', '2025-06-28 01:38:08', 20);

SET FOREIGN_KEY_CHECKS = 1;
