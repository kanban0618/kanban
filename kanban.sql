/*
 Navicat Premium Data Transfer

 Source Server         : jsj
 Source Server Type    : MySQL
 Source Server Version : 50626
 Source Host           : localhost:3306
 Source Schema         : kanban

 Target Server Type    : MySQL
 Target Server Version : 50626
 File Encoding         : 65001

 Date: 05/08/2022 20:21:00
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for r_flow_task
-- ----------------------------
DROP TABLE IF EXISTS `r_flow_task`;
CREATE TABLE `r_flow_task`  (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `flow_id` int(255) NULL DEFAULT 1 COMMENT '工作流id',
  `task_id` int(255) NULL DEFAULT 1 COMMENT '任务id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of r_flow_task
-- ----------------------------
INSERT INTO `r_flow_task` VALUES (1, 1, 29);
INSERT INTO `r_flow_task` VALUES (2, 2, 26);
INSERT INTO `r_flow_task` VALUES (3, 2, 27);
INSERT INTO `r_flow_task` VALUES (4, 2, 29);
INSERT INTO `r_flow_task` VALUES (5, 3, 28);
INSERT INTO `r_flow_task` VALUES (6, 4, 2);

-- ----------------------------
-- Table structure for r_story_flow
-- ----------------------------
DROP TABLE IF EXISTS `r_story_flow`;
CREATE TABLE `r_story_flow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `story_id` int(11) NULL DEFAULT NULL,
  `flow_id` int(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of r_story_flow
-- ----------------------------
INSERT INTO `r_story_flow` VALUES (1, 58, 1);
INSERT INTO `r_story_flow` VALUES (2, 59, 1);
INSERT INTO `r_story_flow` VALUES (3, 60, 1);

-- ----------------------------
-- Table structure for r_story_task
-- ----------------------------
DROP TABLE IF EXISTS `r_story_task`;
CREATE TABLE `r_story_task`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `story_id` int(11) NOT NULL DEFAULT 0 COMMENT '故事主键',
  `task_id` int(11) NOT NULL DEFAULT 0 COMMENT '任务主键',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `story_id_task_id`(`story_id`, `task_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '故事任务关系表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of r_story_task
-- ----------------------------
INSERT INTO `r_story_task` VALUES (1, 58, 2);
INSERT INTO `r_story_task` VALUES (2, 58, 3);
INSERT INTO `r_story_task` VALUES (3, 59, 3);

-- ----------------------------
-- Table structure for r_story_user
-- ----------------------------
DROP TABLE IF EXISTS `r_story_user`;
CREATE TABLE `r_story_user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `story_id` int(11) NULL DEFAULT NULL COMMENT '故事Id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for r_user_story
-- ----------------------------
DROP TABLE IF EXISTS `r_user_story`;
CREATE TABLE `r_user_story`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL DEFAULT 0 COMMENT '用户主键',
  `story_id` int(11) NOT NULL DEFAULT 0 COMMENT '故事主键',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id_story_id`(`user_id`, `story_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户故事关系表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of r_user_story
-- ----------------------------
INSERT INTO `r_user_story` VALUES (1, 1, 82);
INSERT INTO `r_user_story` VALUES (2, 1, 83);
INSERT INTO `r_user_story` VALUES (3, 2, 84);

-- ----------------------------
-- Table structure for r_user_task
-- ----------------------------
DROP TABLE IF EXISTS `r_user_task`;
CREATE TABLE `r_user_task`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` int(11) NOT NULL DEFAULT 0 COMMENT '用户主键',
  `task_id` int(11) NOT NULL DEFAULT 0 COMMENT '故事主键',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id_story_id`(`user_id`, `task_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户故事关系表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of r_user_task
-- ----------------------------
INSERT INTO `r_user_task` VALUES (1, 1, 26);
INSERT INTO `r_user_task` VALUES (2, 1, 28);
INSERT INTO `r_user_task` VALUES (3, 2, 28);

-- ----------------------------
-- Table structure for tb_flow
-- ----------------------------
DROP TABLE IF EXISTS `tb_flow`;
CREATE TABLE `tb_flow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `workname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作流名称',
  `category` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '类别',
  `capacity` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '在制品限制',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '工作流表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_flow
-- ----------------------------
INSERT INTO `tb_flow` VALUES (2, '前端开发', '开发', '4', 3);
INSERT INTO `tb_flow` VALUES (3, '后端开发', '开发', '3', 2);
INSERT INTO `tb_flow` VALUES (4, '已完成', '已完成', '13', 10);

-- ----------------------------
-- Table structure for tb_rule
-- ----------------------------
DROP TABLE IF EXISTS `tb_rule`;
CREATE TABLE `tb_rule`  (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `userid` int(255) NULL DEFAULT NULL COMMENT '用户id',
  `url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '访问网址的地址',
  `token` bit(1) NULL DEFAULT b'0' COMMENT '令牌 0表示关 1表示开 ',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_rule
-- ----------------------------
INSERT INTO `tb_rule` VALUES (1, 1, '/task/all', b'1');
INSERT INTO `tb_rule` VALUES (2, 1, '/task/save', b'0');
INSERT INTO `tb_rule` VALUES (3, 1, '/task/delete', b'1');
INSERT INTO `tb_rule` VALUES (4, 1, '/task/update', b'1');
INSERT INTO `tb_rule` VALUES (7, 2, '/user/all', b'0');
INSERT INTO `tb_rule` VALUES (8, 3, '/rule/all', b'0');

-- ----------------------------
-- Table structure for tb_story
-- ----------------------------
DROP TABLE IF EXISTS `tb_story`;
CREATE TABLE `tb_story` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `originator` varchar(50) DEFAULT NULL COMMENT '发起人',
  `person_in_charge` int(11) unsigned NOT NULL COMMENT '负责人',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `estimation_point` int(11) DEFAULT NULL COMMENT '估算点',
  `flow_id` int(11) DEFAULT NULL COMMENT '工作流外键',
  `state` varchar(50) DEFAULT NULL COMMENT '状态',
  `release_time` datetime DEFAULT NULL COMMENT '发布时间',
  `est_com_time` datetime DEFAULT NULL COMMENT '预估时间',
  `act_com_time` datetime DEFAULT NULL COMMENT '实际时间',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `person_in_charge` (`person_in_charge`),
  CONSTRAINT `tb_story_ibfk_1` FOREIGN KEY (`person_in_charge`) REFERENCES `tb_user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;

/*Data for the table `tb_story` */

insert  into `tb_story`(`id`,`title`,`originator`,`person_in_charge`,`content`,`estimation_point`,`flow_id`,`state`,`release_time`,`est_com_time`,`act_com_time`,`create_time`,`update_time`,`sort`) values
(1,'测试标题1','测试发起人1',1,'测试内容1',1,1,'测试状态1','2022-06-24 17:22:08','2022-06-24 17:22:08','2022-06-24 17:22:08','2022-06-24 17:22:08','2022-07-15 19:31:38',1),
(2,'测试标题2','测试发起人2',2,'测试内容2',2,2,'测试状态2','2022-06-24 17:22:08','2022-06-24 17:22:08','2022-06-24 17:22:08','2022-06-24 17:22:08','2022-07-15 19:31:39',3),
(3,'测试标题3','测试发起人3',3,'测试内容3',3,3,'测试状态3','2022-06-24 17:22:08','2022-06-24 17:22:08','2022-06-24 17:22:08','2022-06-24 17:22:08','2022-07-15 19:31:42',2);

-- ----------------------------
-- Table structure for tb_task
-- ----------------------------
DROP TABLE IF EXISTS `tb_task`;
CREATE TABLE `tb_task`  (
  `id` int(255) NOT NULL AUTO_INCREMENT COMMENT '任务id',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '空' COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '空' COMMENT '内容',
  `po` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '空' COMMENT '负责人',
  `sponsor` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '空' COMMENT '发起人',
  `publishtime` datetime(0) NULL DEFAULT '1999-01-01 01:01:00' COMMENT '发布时间',
  `ectime` datetime(0) NULL DEFAULT '1999-01-01 01:01:00' COMMENT '预计完成时间',
  `actime` datetime(0) NULL DEFAULT '1999-01-01 01:01:00' COMMENT '实际完成时间',
  `storyid` int(255) NULL DEFAULT 1 COMMENT '故事',
  `estimate` int(255) NULL DEFAULT 1 COMMENT '估算点',
  `flowid` int(255) NULL DEFAULT 1 COMMENT '工作流',
  `state` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '未启动' COMMENT '状态',
  `sort` int(255) NOT NULL DEFAULT 1 COMMENT '排序',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 39 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '任务表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_task
-- ----------------------------
INSERT INTO `tb_task` VALUES (2, '测试', '修改', '小明', '张三', '2022-06-22 14:48:00', '2022-06-22 14:48:00', '2022-07-05 09:07:00', 1, 1, 2, '已完成', 1);
INSERT INTO `tb_task` VALUES (26, '测试', '添加', '小红', '李四', '2022-07-14 12:50:39', '2022-06-22 14:10:00', '2022-06-22 14:10:00', 2, 1, 2, '测试', 2);
INSERT INTO `tb_task` VALUES (27, '测试', '查询', '小刚', '王五', '2022-07-14 13:08:08', '2022-06-22 15:03:00', '2022-06-22 15:03:00', 3, 1, 2, '测试中', 3);
INSERT INTO `tb_task` VALUES (28, '测试', '修改', '小明', '现在', '2022-07-14 13:08:10', '2022-06-22 14:48:00', '2022-07-13 15:59:20', 1, 1, 5, '已完成', 34);
INSERT INTO `tb_task` VALUES (29, '测试', '模糊', '小赵', '钱七', '2022-07-14 12:50:42', '2022-06-24 12:43:00', '2022-06-25 01:47:41', 5, 1, 2, '未启动', 15);
INSERT INTO `tb_task` VALUES (38, '测试', '测试', '张三', '李五', '2022-07-14 13:10:00', '2022-07-02 05:21:00', '2022-07-03 03:46:00', 2, 1, 2, '已完成', 4);

-- ----------------------------
-- Table structure for tb_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user`  (
  `id` int(255) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '账号',
  `password` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '密码',
  `jobno` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工号',
  `position` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '职位',
  `headimg` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UNIQUE_username`(`username`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表\r\n' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES (1, '测试姓名', 'cs', '123', '1', '测试职位', '/uploadFile/b4a3f525-c406-4547-921b-ad5e6cf7c788.jpg');
INSERT INTO `tb_user` VALUES (2, '张三', 'zs', '123', '1', '无', '/uploadFile/46f8d0c4-f4d4-43ad-aeeb-584516e80418.jpg');
INSERT INTO `tb_user` VALUES (3, '老刘', 'll', '123', '1', '老六', '/uploadFile/6972bed1-879c-4218-baa0-cc722d1e3655.jpg');
INSERT INTO `tb_user` VALUES (4, '野兽仙贝', 'ec', '123', '1', '散发恶臭', '/uploadFile/f2265d58-d929-46d2-820e-a66a01222186.png');

SET FOREIGN_KEY_CHECKS = 1;
