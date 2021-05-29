/*
 Navicat Premium Data Transfer
 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50734
 Source Host           : localhost:3306
 Source Schema         : bee
 Target Server Type    : MySQL
 Target Server Version : 50734
 File Encoding         : 65001
 Date: 29/05/2021 10:39:17
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for bee_api
-- ----------------------------
DROP TABLE IF EXISTS `bee_api`;
CREATE TABLE `bee_api`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '描述',
  `group_id` int(11) NULL DEFAULT NULL,
  `create_time` datetime(0) NULL DEFAULT NULL,
  `update_time` datetime(0) NULL DEFAULT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `status` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bee_api
-- ----------------------------
INSERT INTO `bee_api` VALUES (1, '测试接口1', '/demo', '这是一个测试接口', 1, '2021-05-27 22:27:37', '2021-05-27 22:27:40', 'zhangsan', 1);
INSERT INTO `bee_api` VALUES (2, '测试接口2', '/demo2', 'aa', 1, '2021-05-27 23:22:20', '2021-05-27 23:22:22', 'wangwu', NULL);
INSERT INTO `bee_api` VALUES (3, '测试接口3', '/demo3', 'aaa', 1, '2021-05-27 23:23:07', '2021-05-27 23:22:22', 'lisi', NULL);
INSERT INTO `bee_api` VALUES (4, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (5, '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (6, '3', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (7, '4', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (8, '5', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (9, '6', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (10, 'a', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (11, 'b', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (12, 'a', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (13, 'g', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (14, 'f', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (15, 'v', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (16, 'u', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (17, 'i', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (18, 'v', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (19, 'o', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (20, 'y', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (21, 'n', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (22, 'm', NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `bee_api` VALUES (23, 'f', NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for bee_api_group
-- ----------------------------
DROP TABLE IF EXISTS `bee_api_group`;
CREATE TABLE `bee_api_group`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '分组名称',
  `note` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '分组描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bee_api_group
-- ----------------------------

-- ----------------------------
-- Table structure for bee_filter
-- ----------------------------
DROP TABLE IF EXISTS `bee_filter`;
CREATE TABLE `bee_filter`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `api_id` int(11) NULL DEFAULT NULL,
  `data` varchar(255) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL,
  `status` int(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = latin1 COLLATE = latin1_swedish_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bee_filter
-- ----------------------------

-- ----------------------------
-- Table structure for bee_router
-- ----------------------------
DROP TABLE IF EXISTS `bee_router`;
CREATE TABLE `bee_router`  (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'http',
  `data` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '目标地址',
  `api_id` int(11) NULL DEFAULT NULL COMMENT 'api的id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bee_router
-- ----------------------------
INSERT INTO `bee_router` VALUES (1, 'http', 'http://127.0.0.1:20000/bee-demo/query', 2);
INSERT INTO `bee_router` VALUES (2, 'dubbo', '{\"zookeeper_addr\":\"zookeeper://127.0.0.1:2181\",\"interface\":\"com.github.xhrg.bee.demo.dubbo.HelloService\",\"method\":\"sayHello\",\"returnType\":\"java.lang.String\"}', 1);

-- ----------------------------
-- Table structure for bee_user
-- ----------------------------
DROP TABLE IF EXISTS `bee_user`;
CREATE TABLE `bee_user`  (
  `id` int(11) NOT NULL,
  `name` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  `password` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bee_user
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
