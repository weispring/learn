/*
Navicat MySQL Data Transfer

Source Server         : mysql
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : learn

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2020-04-16 22:28:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for order_bill
-- ----------------------------
DROP TABLE IF EXISTS `order_bill`;
CREATE TABLE `order_bill` (
  `f_id` bigint(20) unsigned NOT NULL,
  `f_amount` bigint(20) unsigned NOT NULL COMMENT '订单金额总金额',
  `f_pay_state` tinyint(1) unsigned NOT NULL COMMENT '支付状态（1：待支付；2：支付成功；3：支付失败；4：待退款；5：退款成功；6：退款失败）',
  `f_buyer` bigint(20) unsigned NOT NULL COMMENT '购买人id（demo_user库t_user_info表f_id）',
  `f_sys_add_time` bigint(20) NOT NULL COMMENT '创建时间',
  `f_sys_upd_time` bigint(20) DEFAULT NULL COMMENT '更新时间',
  `f_sys_del_time` bigint(20) DEFAULT NULL COMMENT '删除时间',
  `f_sys_add_user` bigint(20) unsigned DEFAULT NULL COMMENT '新增者',
  `f_sys_upd_user` bigint(20) unsigned DEFAULT NULL COMMENT '更新者',
  `f_sys_del_user` bigint(20) unsigned DEFAULT NULL COMMENT '删除者',
  `f_sys_del_state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '删除状态=={1:正常, 2:已删除}'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单信息';

-- ----------------------------
-- Table structure for order_item
-- ----------------------------
DROP TABLE IF EXISTS `order_item`;
CREATE TABLE `order_item` (
  `id` bigint(20) unsigned NOT NULL,
  `order_id` bigint(20) unsigned NOT NULL COMMENT '订单id',
  `product_id` bigint(20) unsigned NOT NULL COMMENT '商品id',
  `sku_id` bigint(20) unsigned NOT NULL COMMENT 'skuid',
  `count` bigint(20) unsigned NOT NULL COMMENT '数量',
  `f_sys_add_time` datetime NOT NULL COMMENT '创建时间',
  `f_sys_upd_time` datetime DEFAULT NULL COMMENT '更新时间',
  `f_sys_del_time` datetime DEFAULT NULL COMMENT '删除时间',
  `f_sys_add_user` bigint(20) unsigned DEFAULT NULL COMMENT '新增者',
  `f_sys_upd_user` bigint(20) unsigned DEFAULT NULL COMMENT '更新者',
  `f_sys_del_user` bigint(20) unsigned DEFAULT NULL COMMENT '删除者',
  `f_sys_del_state` tinyint(1) unsigned NOT NULL DEFAULT '1' COMMENT '删除状态=={1:正常, 2:已删除}'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单商品信息';

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
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) DEFAULT NULL,
  `nick_name` varchar(20) CHARACTER SET utf8mb4 DEFAULT NULL,
  `email` char(20) CHARACTER SET utf8mb4 DEFAULT '',
  `phone` varchar(11) NOT NULL DEFAULT '33444',
  `create_time` datetime NOT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=1229 DEFAULT CHARSET=utf8;
