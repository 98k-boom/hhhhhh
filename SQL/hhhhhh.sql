CREATE TABLE `contact` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `postcode` varchar(20) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `customer_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='顾客配送方式';

CREATE TABLE `customer` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nick` varchar(50) DEFAULT NULL,
  `wechat` varchar(50) DEFAULT NULL,
  `intro` varchar(255) DEFAULT NULL,
  `login_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='顾客数据';

CREATE TABLE `item` (
  `id` bigint(20) unsigned NOT NULL,
  `title` varchar(100) NOT NULL COMMENT '商品标题',
  `sell_point` varchar(500) DEFAULT NULL COMMENT '商品卖点',
  `original_price` decimal(10,2) NOT NULL COMMENT '商品原价',
  `buying_price` decimal(10,2) NOT NULL COMMENT '商品进货价',
  `discount_price` decimal(10,2) NOT NULL COMMENT '商品折扣价',
  `color` varchar(10) NOT NULL COMMENT '颜色（暂时）',
  `size` varchar(10) NOT NULL COMMENT '尺寸（暂时）',
  `is_recommend` tinyint(1) NOT NULL COMMENT '是否推荐，0-否、1-是',
  `status` tinyint(2) NOT NULL COMMENT '商品状态，1-正常，2-下架，3-删除',
  `creator_uid` int(10) unsigned NOT NULL COMMENT '创建者id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_uid` int(10) unsigned NOT NULL COMMENT '修改者id',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近一次修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品基本信息';

CREATE TABLE `item_image` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `item_id` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '商品id',
  `path` varchar(500) NOT NULL COMMENT '图片地址',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '图片类型，1-头图、2-详情图',
  `sort` smallint(6) NOT NULL DEFAULT '1' COMMENT '排序',
  `creator_uid` int(10) unsigned NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `modify_uid` int(10) unsigned NOT NULL COMMENT '最近更新者',
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最近更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品图片信息';

CREATE TABLE `order` (
  `id` bigint(20) NOT NULL,
  `customer_id` bigint(20) NOT NULL COMMENT '顾客ID',
  `address` varchar(500) NOT NULL COMMENT '顾客配送地址',
  `phone` varchar(20) NOT NULL COMMENT '顾客配送手机',
  `postcode` varchar(20) DEFAULT NULL COMMENT '顾客配送邮编',
  `payment` decimal(10,2) NOT NULL COMMENT '总价',
  `close_time` datetime DEFAULT NULL COMMENT '交易关闭时间',
  `end_time` datetime DEFAULT NULL COMMENT '交易完成时间',
  `payment_time` datetime DEFAULT NULL COMMENT '付款时间',
  `consign_time` datetime DEFAULT NULL COMMENT '发货时间',
  `create_time` datetime NOT NULL COMMENT '订单创建时间',
  `status` smallint(6) unsigned NOT NULL COMMENT '状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='主订单信息';

CREATE TABLE `order_item` (
  `id` bigint(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `order_id` bigint(20) NOT NULL,
  `amount` bigint(20) NOT NULL COMMENT '购买数量',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='子订单信息';
