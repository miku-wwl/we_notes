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

 Date: 10/04/2024 11:11:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_user_info
-- ----------------------------
DROP TABLE IF EXISTS `t_user_info`;
CREATE TABLE `t_user_info`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键id自增',
  `nick` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `password` varchar(128) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '密码',
  `sex` int NULL DEFAULT NULL COMMENT '性别（0女 1男）',
  `phone` varchar(18) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '手机',
  `email` varchar(80) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `address` varchar(150) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '住址',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 32 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user_info
-- ----------------------------
INSERT INTO `t_user_info` VALUES (11, 'pat', '$2a$10$WDhVclK0lXBFYAaRd7CI4uAdsD5xpEgxh/KpT/OCGgTUzE/SY9ACO', 0, '13700000008', 'pat@163.com', '河北石家庄', '2023-05-16 18:03:36', NULL);
INSERT INTO `t_user_info` VALUES (12, 'hanhan', '$2a$10$WDhVclK0lXBFYAaRd7CI4uAdsD5xpEgxh/KpT/OCGgTUzE/SY9ACO', 0, '13700000009', 'zar@163.com', '南京江宁区', '2023-05-16 20:05:53', NULL);
INSERT INTO `t_user_info` VALUES (25, 'gaohan', '$2a$10$WDhVclK0lXBFYAaRd7CI4uAdsD5xpEgxh/KpT/OCGgTUzE/SY9ACO', 0, '13709879098', '123213@qq.com', '江苏苏州', '2023-10-22 17:03:18', NULL);
INSERT INTO `t_user_info` VALUES (27, 'xuyan', '$2a$10$WDhVclK0lXBFYAaRd7CI4uAdsD5xpEgxh/KpT/OCGgTUzE/SY9ACO', -1, '15319495107', '1274908062@qq.com', '北京大兴区经济技术开发区', '2023-06-12 22:43:12', NULL);
INSERT INTO `t_user_info` VALUES (28, 'zhangsansan', '123456', 0, '13509090909', 'zhangsansan@qq.com', '北京大兴区', '2023-10-22 19:44:07', NULL);

SET FOREIGN_KEY_CHECKS = 1;
