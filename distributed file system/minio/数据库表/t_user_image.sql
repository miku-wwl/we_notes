/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.11.128
 Source Server Type    : MySQL
 Source Server Version : 80033 (8.0.33)
 Source Host           : 192.168.11.128:3306
 Source Schema         : minio

 Target Server Type    : MySQL
 Target Server Version : 80033 (8.0.33)
 File Encoding         : 65001

 Date: 10/04/2024 11:11:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user_image
-- ----------------------------
DROP TABLE IF EXISTS `t_user_image`;
CREATE TABLE `t_user_image`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `uid` int NOT NULL,
  `bucket` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `object` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_user_image
-- ----------------------------
INSERT INTO `t_user_image` VALUES (4, 11, 'user-bucket', '11.jpg', '2024-04-08 12:13:29', NULL);
INSERT INTO `t_user_image` VALUES (5, 12, 'user-bucket', '12.jpg', '2024-04-09 09:20:06', NULL);

SET FOREIGN_KEY_CHECKS = 1;
