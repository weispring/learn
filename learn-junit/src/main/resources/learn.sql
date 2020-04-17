/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 80018
Source Host           : 127.0.0.1:3333
Source Database       : learn

Target Server Type    : MYSQL
Target Server Version : 80018
File Encoding         : 65001

Date: 2020-04-17 19:15:17
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_bill
-- ----------------------------
DROP TABLE IF EXISTS `order_bill`;
CREATE TABLE `order_bill` (
  `id` bigint(20) unsigned NOT NULL,
  `amount` bigint(20) unsigned NOT NULL COMMENT '订单金额总金额',
  `pay_state` tinyint(1) unsigned NOT NULL COMMENT '支付状态（1：待支付；2：支付成功；3：支付失败；4：待退款；5：退款成功；6：退款失败）',
  `order_status` tinyint(8) NOT NULL DEFAULT '10' COMMENT '10待确认20待支付30已支付40已发货50已完成60已取消70退款中80已退款',
  `buyer` bigint(20) unsigned NOT NULL COMMENT '购买人id（demo_user库t_user_info表id）',
  `sys_add_time` bigint(20) NOT NULL COMMENT '创建时间',
  `sys_upd_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `sys_del_time` bigint(20) DEFAULT NULL COMMENT '删除时间',
  `sys_add_user` bigint(20) unsigned DEFAULT NULL COMMENT '新增者',
  `sys_upd_user` bigint(20) unsigned DEFAULT NULL COMMENT '更新者',
  `sys_del_user` bigint(20) unsigned DEFAULT NULL COMMENT '删除者',
  `sys_del_state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '删除状态=={1:正常, 2:已删除}'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息';

-- ----------------------------
-- Records of order_bill
-- ----------------------------

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `id` bigint(20) unsigned NOT NULL,
  `order_id` bigint(20) unsigned NOT NULL COMMENT '订单id',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名稱',
  `product_id` bigint(20) unsigned NOT NULL COMMENT '商品id',
  `sku_id` bigint(20) unsigned NOT NULL COMMENT 'skuid',
  `price` bigint(20) DEFAULT NULL COMMENT '价格',
  `count` bigint(20) unsigned NOT NULL COMMENT '数量',
  `sys_add_time` datetime NOT NULL COMMENT '创建时间',
  `sys_upd_time` datetime DEFAULT NULL COMMENT '更新时间',
  `sys_del_time` datetime DEFAULT NULL COMMENT '删除时间',
  `sys_add_user` bigint(20) unsigned DEFAULT NULL COMMENT '新增者',
  `sys_upd_user` bigint(20) unsigned DEFAULT NULL COMMENT '更新者',
  `sys_del_user` bigint(20) unsigned DEFAULT NULL COMMENT '删除者',
  `sys_del_state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '删除状态=={1:正常, 2:已删除}'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品信息';

-- ----------------------------
-- Records of order_item
-- ----------------------------

-- ----------------------------
-- Table structure for spatial_geometry
-- ----------------------------
DROP TABLE IF EXISTS `spatial_geometry`;
CREATE TABLE `spatial_geometry` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(128) NOT NULL,
  `dot` point NOT NULL,
  PRIMARY KEY (`id`),
  SPATIAL KEY `dotIndex` (`dot`)
) ENGINE=MyISAM AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='测试空间所应';

-- ----------------------------
-- Records of spatial_geometry
-- ----------------------------

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `email` char(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT '',
  `phone` varchar(11) NOT NULL DEFAULT '33444',
  `create_time` bigint(20) NOT NULL,
  `update_time` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1229 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------

-- ----------------------------
-- Table structure for user_extend
-- ----------------------------
DROP TABLE IF EXISTS `user_extend`;
CREATE TABLE `user_extend` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户id',
  `province` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '省份',
  `city` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '城市',
  `area` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '区域',
  `detail_adress` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '详细地址',
  ` signature` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '个性签名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Records of user_extend
-- ----------------------------
