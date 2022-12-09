/*
Navicat MySQL Data Transfer

Source Database       : cloud_files
Target Server Type    : MYSQL
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_file_info
-- ----------------------------
DROP TABLE IF EXISTS `tb_file_info`;
CREATE TABLE `tb_file_info` (
  `id` varchar(50) NOT NULL COMMENT '文件id',
  `name` varchar(128) NOT NULL COMMENT '文件名',
  `application_name` varchar(128) NOT NULL COMMENT '服务名称',
  `content_type` varchar(128) NOT NULL COMMENT '文件类型',
  `size` bigint(20) NOT NULL COMMENT '文件大小',
  `thumb_url` varchar(100) DEFAULT NULL COMMENT '缩略图',
  `url` varchar(100) NOT NULL COMMENT '文件网络url',
  `create_user` varchar(60) NOT NULL COMMENT '创建人',
  `create_time` bigint(20) NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='文件信息表';
