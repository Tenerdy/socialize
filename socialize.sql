/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50743
 Source Host           : localhost:3306
 Source Schema         : socialize

 Target Server Type    : MySQL
 Target Server Version : 50743
 File Encoding         : 65001

 Date: 06/10/2023 16:08:55
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for article_category
-- ----------------------------
DROP TABLE IF EXISTS `article_category`;
CREATE TABLE `article_category`  (
  `artile_category_id` int(11) NOT NULL,
  `article_id` int(11) NOT NULL COMMENT '文章编号',
  `category_id` int(11) NOT NULL COMMENT '分类编号',
  PRIMARY KEY (`artile_category_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_like
-- ----------------------------
DROP TABLE IF EXISTS `article_like`;
CREATE TABLE `article_like`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL COMMENT '文章编号',
  `user_id` int(11) NOT NULL COMMENT '点赞用户编号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_article_like_user_article`(`user_id`, `article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 75 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for article_picture
-- ----------------------------
DROP TABLE IF EXISTS `article_picture`;
CREATE TABLE `article_picture`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片路径',
  `article_id` int(11) NULL DEFAULT NULL COMMENT '文章编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_article_picture_article`(`article_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 60 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for black_list
-- ----------------------------
DROP TABLE IF EXISTS `black_list`;
CREATE TABLE `black_list`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户编号',
  `black_id` int(11) NULL DEFAULT NULL COMMENT '拉黑用户编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_black_list_user_black`(`user_id`, `black_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for chat_group
-- ----------------------------
DROP TABLE IF EXISTS `chat_group`;
CREATE TABLE `chat_group`  (
  `group_id` int(11) NOT NULL COMMENT '群聊编号',
  `group_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '群聊名称',
  PRIMARY KEY (`group_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for comment_like
-- ----------------------------
DROP TABLE IF EXISTS `comment_like`;
CREATE TABLE `comment_like`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `comment_id` int(11) NOT NULL COMMENT '评论编号',
  `user_id` int(11) NOT NULL COMMENT '点赞用户编号',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '点赞时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group_chat_read
-- ----------------------------
DROP TABLE IF EXISTS `group_chat_read`;
CREATE TABLE `group_chat_read`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `record_id` int(11) NULL DEFAULT NULL COMMENT '群聊记录编号',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户编号',
  `isread` tinyint(4) NULL DEFAULT NULL COMMENT '是否已读,未读为0,已读为1',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 407 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for group_chat_record
-- ----------------------------
DROP TABLE IF EXISTS `group_chat_record`;
CREATE TABLE `group_chat_record`  (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `from_id` int(11) NULL DEFAULT NULL COMMENT '发送人编号',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '发送时间',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `type` int(11) NULL DEFAULT NULL COMMENT '消息类型(1为消息,2为图片,3为文件)',
  `group_id` int(11) NULL DEFAULT NULL COMMENT '群聊编号',
  PRIMARY KEY (`record_id`) USING BTREE,
  INDEX `idx_group_chat_record_group`(`group_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 157 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for single_chat_record
-- ----------------------------
DROP TABLE IF EXISTS `single_chat_record`;
CREATE TABLE `single_chat_record`  (
  `record_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '记录id',
  `from_id` int(11) NULL DEFAULT NULL COMMENT '发送人id',
  `to_id` int(11) NULL DEFAULT NULL COMMENT '接收人id',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  `time` datetime(0) NULL DEFAULT NULL COMMENT '时间',
  `type` tinyint(4) NULL DEFAULT NULL COMMENT '消息类型(1为文本,2为图片,3为文件)',
  `isread` tinyint(4) NULL DEFAULT NULL COMMENT '是否已读(已读为0,否则为1)',
  PRIMARY KEY (`record_id`) USING BTREE,
  INDEX `idx_single_chat_record_from_to`(`from_id`, `to_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 337 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tag_categories
-- ----------------------------
DROP TABLE IF EXISTS `tag_categories`;
CREATE TABLE `tag_categories`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '分类描述',
  `weight` int(11) NULL DEFAULT 1 COMMENT '标签分类权重(用于匹配)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '标签ID',
  `category_id` int(11) NOT NULL COMMENT '所属分类ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签名称',
  `weight` int(11) NULL DEFAULT 1 COMMENT '标签权重(用于匹配)',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `category_id`(`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 108 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_article
-- ----------------------------
DROP TABLE IF EXISTS `user_article`;
CREATE TABLE `user_article`  (
  `article_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '文章编号',
  `user_id` int(11) NOT NULL COMMENT '用户编号',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章文本内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '发布时间',
  `transfer` int(11) NULL DEFAULT NULL COMMENT '转载数量',
  `love` int(11) NULL DEFAULT NULL COMMENT '喜爱数量',
  `comment` int(11) NULL DEFAULT NULL COMMENT '评论数量',
  `from` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来自何处',
  `scope` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '可见范围(0代表广场可见,1代表关注可见)',
  `category_id` int(11) NULL DEFAULT NULL COMMENT '分类编号',
  PRIMARY KEY (`article_id`) USING BTREE,
  INDEX `idx_user_article_user`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 84 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_comments
-- ----------------------------
DROP TABLE IF EXISTS `user_comments`;
CREATE TABLE `user_comments`  (
  `comment_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论编号',
  `article_id` int(11) NOT NULL COMMENT '文章编号',
  `user_id` int(11) NOT NULL COMMENT '评论用户编号',
  `parent_comment_id` int(11) NULL DEFAULT NULL COMMENT '父级评论编号，若为顶级评论则为空',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '评论内容',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '评论时间',
  `like_count` int(11) NULL DEFAULT NULL COMMENT '点赞数量',
  PRIMARY KEY (`comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 37 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户编号',
  `group_id` int(11) NULL DEFAULT NULL COMMENT '群组编号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_user_group_user`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 255 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号',
  `nickname` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `picture` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户头像',
  `real_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `sex` int(11) NULL DEFAULT NULL COMMENT '用户性别',
  `birthday` date NULL DEFAULT NULL COMMENT '用户生日',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `phone` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `password` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录密码',
  `register_time` datetime(0) NULL DEFAULT NULL COMMENT '注册时间',
  `watch` int(255) NULL DEFAULT NULL COMMENT '看过我的人数',
  `background_picture` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '背景图片',
  PRIMARY KEY (`user_id`) USING BTREE,
  INDEX `phone_index`(`phone`) USING BTREE,
  INDEX `idx_user_info_user`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_relation
-- ----------------------------
DROP TABLE IF EXISTS `user_relation`;
CREATE TABLE `user_relation`  (
  `relation_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关系编号',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户编号',
  `follower_id` int(11) NULL DEFAULT NULL COMMENT '该用户关注的人的id',
  PRIMARY KEY (`relation_id`) USING BTREE,
  INDEX `idx_user_relation_user_follower`(`user_id`, `follower_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 68 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_tags
-- ----------------------------
DROP TABLE IF EXISTS `user_tags`;
CREATE TABLE `user_tags`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '关系ID',
  `user_id` int(11) NOT NULL COMMENT '用户ID',
  `tag_id` int(11) NOT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `user_id`(`user_id`) USING BTREE,
  INDEX `tag_id`(`tag_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 154 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
