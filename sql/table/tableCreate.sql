CREATE TABLE `order` (
                         `id` INT AUTO_INCREMENT COMMENT '订单ID',
                         `user_id` INT NOT NULL COMMENT '用户ID',
                         `product_id` INT NOT NULL COMMENT '商品ID',
                         `status` VARCHAR(20) NOT NULL COMMENT '订单状态',
                         `create_time` DATETIME NOT NULL COMMENT '订单日期',
                         `order_amount` DECIMAL(10, 2) NOT NULL COMMENT '订单金额',
                         PRIMARY KEY (`id`),
                         INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

CREATE TABLE `shopping_cart` (
                                 `id` INT AUTO_INCREMENT COMMENT '购物车ID',
                                 `user_id` INT NOT NULL COMMENT '用户ID',
                                 `product_id` INT NOT NULL COMMENT '商品ID',
                                 `quantity` INT NOT NULL COMMENT '数量',
                                 `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 PRIMARY KEY (`id`),
                                 INDEX `idx_user_id` (`user_id`),
                                 INDEX `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

CREATE TABLE `product_category` (
                            `id` INT AUTO_INCREMENT COMMENT '分类ID',
                            `parent_id` INT COMMENT '父分类ID',
                            `category_name` VARCHAR(50) NOT NULL COMMENT '分类名称',
                            PRIMARY KEY (`id`),
                            INDEX `idx_parent_id` (`parent_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品分类表';

CREATE TABLE `product_inbound` (
                                   `id` INT AUTO_INCREMENT COMMENT '入库ID',
                                   `product_id` INT NOT NULL COMMENT '商品ID',
                                   `inbound_quantity` INT NOT NULL COMMENT '入库数量',
                                   `inbound_date` DATE NOT NULL COMMENT '入库日期',
                                   `operator_id` INT NOT NULL COMMENT '操作人员ID',
                                   PRIMARY KEY (`id`),
                                   INDEX `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品入库表';


CREATE TABLE `product_outbound` (
                                    `id` INT AUTO_INCREMENT COMMENT '出库ID',
                                    `product_id` INT NOT NULL COMMENT '商品ID',
                                    `outbound_quantity` INT NOT NULL COMMENT '出库数量',
                                    `outbound_date` DATE NOT NULL COMMENT '出库日期',
                                    `operator_id` INT NOT NULL COMMENT '操作人员ID',
                                    PRIMARY KEY (`id`),
                                    INDEX `idx_product_id` (`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品出库表';

CREATE TABLE `product_inventory` (
                                     `product_id` INT PRIMARY KEY COMMENT '商品ID',
                                     `inventory_quantity` INT NOT NULL COMMENT '库存数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品库存表';

CREATE TABLE `user_action_log` (
                                   `log_id` INT AUTO_INCREMENT COMMENT '日志ID',
                                   `user_id` INT NOT NULL COMMENT '用户ID',
                                   `action_type` VARCHAR(50) NOT NULL COMMENT '操作类型',
                                   `action_time` TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '操作时间',
                                   PRIMARY KEY (`log_id`),
                                   INDEX `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户操作记录表';