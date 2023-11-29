-- 订单表
CREATE TABLE orders (
                        id INT PRIMARY KEY AUTO_INCREMENT COMMENT '订单ID', -- 订单ID，主键，自动递增
                        customer_id INT NOT NULL COMMENT '客户ID', -- 客户ID，非空字段
                        order_date DATETIME NOT NULL COMMENT '下单日期和时间', -- 下单日期和时间，非空字段
                        total_amount DECIMAL(10, 2) NOT NULL COMMENT '订单总金额', -- 订单总金额，最大10位数，其中2位小数，非空字段
                        shipping_address VARCHAR(255) NOT NULL COMMENT '配送地址', -- 配送地址，最大长度255个字符，非空字段
                        payment_method VARCHAR(50) NOT NULL COMMENT '支付方式', -- 支付方式，最大长度50个字符，非空字段
                        status VARCHAR(20) NOT NULL COMMENT '订单状态', -- 订单状态，最大长度20个字符，非空字段
                        create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间', -- 记录创建时间，默认为当前时间
                        update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录更新时间' -- 记录更新时间，默认为当前时间，且在更新时自动更新
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单表';

-- 订单明细表
CREATE TABLE order_details (
                               id INT PRIMARY KEY AUTO_INCREMENT COMMENT '明细ID', -- 明细ID，主键，自动递增
                               order_id INT NOT NULL COMMENT '订单ID', -- 订单ID，非空字段
                               product_id INT NOT NULL COMMENT '商品ID', -- 商品ID，非空字段
                               quantity INT NOT NULL COMMENT '商品数量', -- 商品数量，非空字段
                               price DECIMAL(10, 2) NOT NULL COMMENT '商品单价' -- 商品单价，最大10位数，其中2位小数，非空字段
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单明细表';

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

CREATE TABLE `product` (
                           `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键ID',
                           `name` varchar(100) NOT NULL COMMENT '商品名称',
                           `category_id` int NOT NULL COMMENT '分类ID',
                           `price` bigint NOT NULL COMMENT '商品价格',
                           `remain` int NOT NULL COMMENT '剩余数量',
                           `status` varchar(20) NOT NULL COMMENT '商品状态',
                           `intro` varchar(255) NOT NULL COMMENT '商品介绍',
                           `cover_image` varchar(255) DEFAULT NULL COMMENT '封面图',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='商品表'

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