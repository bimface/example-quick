-- --------------------------------------------------------
-- 主机:                           172.16.233.144
-- 服务器版本:                        5.1.73 - Source distribution
-- 服务器操作系统:                      redhat-linux-gnu
-- HeidiSQL 版本:                  9.3.0.4984
-- --------------------------------------------------------

-- 导出  表 bimface_biz.example_quick_file 结构
CREATE TABLE IF NOT EXISTS `example_quick_file` (
  `id` bigint(20) NOT NULL COMMENT 'file id',
  `name` varchar(200) NOT NULL COMMENT '文件名',
  `length` bigint(20) NOT NULL COMMENT '文件大小',
  `translate_status` varchar(20) DEFAULT NULL COMMENT '转换状态',
  `databag_status` varchar(20) DEFAULT NULL COMMENT '离线包状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 导出  表 bimface_biz.example_quick_integrate 结构
CREATE TABLE IF NOT EXISTS `example_quick_integrate` (
  `id` bigint(20) NOT NULL COMMENT 'integrate id',
  `name` varchar(200) NOT NULL COMMENT '集成项目名',
  `file_num` int(11) NOT NULL COMMENT '集成文件数目',
  `integrate_status` varchar(50) NOT NULL COMMENT '集成状态，prepare，processing，success，failed',
  `databag_status` varchar(50) DEFAULT NULL COMMENT '离线包状态',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='快速入门集成模型表';

-- 导出  表 bimface_biz.example_quick_integrate_file 结构
CREATE TABLE IF NOT EXISTS `example_quick_integrate_file` (
  `id` bigint(20) NOT NULL,
  `integrate_id` bigint(20) NOT NULL,
  `file_id` bigint(20) NOT NULL,
  `file_name` varchar(200) NOT NULL,
  `specialty` varchar(32) DEFAULT NULL,
  `specialty_sort` float(255,0) DEFAULT NULL,
  `floor` varchar(32) DEFAULT NULL,
  `floor_sort` float(255,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `integrate_id` (`integrate_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

