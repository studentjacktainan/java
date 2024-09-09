/*
 Navicat Premium Dump SQL

 Source Server         : mysql8
 Source Server Type    : MySQL
 Source Server Version : 80011 (8.0.11)
 Source Host           : 127.0.0.1:3306
 Source Schema         : mygame

 Target Server Type    : MySQL
 Target Server Version : 80011 (8.0.11)
 File Encoding         : 65001

 Date: 09/09/2024 19:10:29
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo`  (
  `user_id` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `userpwd` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `registration_date` datetime NULL DEFAULT NULL,
  `score` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `leaderboard_id` int(11) NULL DEFAULT NULL,
  `rank` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `enemies_destroyed` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`, `username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES ('6625f76a', 'kevin', 'vnejk', 'xenjnelkeqs', '2024-09-08 18:20:31', NULL, NULL, NULL, NULL);
INSERT INTO `userinfo` VALUES ('901885d0', 'jack', 'h2V586mv', 'jack1019k@gmail.com', '2024-09-06 11:12:29', NULL, NULL, NULL, NULL);
INSERT INTO `userinfo` VALUES ('aaf206b2', 'd', 'd', 'd', '2024-09-08 18:47:22', NULL, NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
