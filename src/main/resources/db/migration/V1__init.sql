DROP TABLE IF EXISTS `manufacturer`;
CREATE TABLE `manufacturer` (
                                `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                                `title` varchar(85) NOT NULL,
                                `info` varchar(1800) DEFAULT NULL,
                                `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
                              `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                              `parent_id` bigint unsigned DEFAULT NULL,
                              `title` varchar(45) NOT NULL,
                              `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                              `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                              PRIMARY KEY (`id`),
                              KEY `fk_id_parentid_idx` (`parent_id`),
  CONSTRAINT `fk_id_parentid` FOREIGN KEY (`parent_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
                            `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                            `article` varchar(45) NOT NULL,
                            `modification` varchar(255) DEFAULT NULL,
                            `category_id` bigint unsigned NOT NULL,
                            `manufacturer_id` bigint unsigned NOT NULL,
                            `title` varchar(255) DEFAULT NULL,
                            `price` double DEFAULT NULL,
                            `purchase_price` double DEFAULT NULL,
                            `old_price` double DEFAULT NULL,
                            `count` int DEFAULT NULL,
                            `short_description` varchar(255) DEFAULT NULL,
                            `description` varchar(1200) DEFAULT NULL,
                            `path` varchar(255) DEFAULT NULL,
                            `images_title` varchar(2000) DEFAULT NULL,
                            `images_linc` varchar(2000) DEFAULT NULL,
                            `specifications` varchar(3000) DEFAULT NULL,
                            `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                            `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                            PRIMARY KEY (`id`),
                            KEY `fk_product_categories_idx` (`category_id`),
                            UNIQUE KEY `article_UNIQUE` (`article`),
  KEY `fk_product_manufacturer_idx` (`manufacturer_id`),
  CONSTRAINT `fk_product_categories` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `fk_product_manufacturer` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturer` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
                         `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                         `username` varchar(80) NOT NULL,
                         `password` varchar(255) NOT NULL,
                         `email` varchar(85) DEFAULT NULL,
                         `phone` varchar(45) DEFAULT NULL,
                         `birthday` date DEFAULT NULL,
                         `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                         `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
                         `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                         `role` varchar(45) NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `id_UNIQUE` (`id`),
                         UNIQUE KEY `role_UNIQUE` (`role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
                              `user_id` bigint unsigned NOT NULL,
                              `role_id` bigint unsigned NOT NULL,
                              PRIMARY KEY (`user_id`,`role_id`),
                              KEY `fk_role_id_idx` (`role_id`),
  CONSTRAINT `fk_role_id` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `cart`;
CREATE TABLE `cart` (
                        `user_id` bigint unsigned NOT NULL,
                        `product_id` bigint unsigned NOT NULL,
                        `count` int unsigned DEFAULT NULL,
                        PRIMARY KEY (`user_id`,`product_id`),
                        KEY `fk_cart_productid_idx` (`product_id`),
  CONSTRAINT `fk_cart_productid` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `fk_cart_userid` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
                          `id` bigint unsigned NOT NULL AUTO_INCREMENT,
                          `user_id` bigint unsigned NOT NULL,
                          `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                          `completed_at` timestamp NULL DEFAULT NULL,
                          `history` varchar(255) DEFAULT NULL,
                          `address` varchar(255) DEFAULT NULL,
                          `info` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`),
                          KEY `fk_orders_userid_idx` (`user_id`),
  CONSTRAINT `fk_orders_userid` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `order_products`;
CREATE TABLE `order_products` (
                                  `order_id` bigint unsigned NOT NULL,
                                  `product_id` bigint unsigned NOT NULL,
                                  `count` int unsigned DEFAULT NULL,
                                  PRIMARY KEY (`order_id`,`product_id`),
                                  KEY `fk_order_productid_idx` (`product_id`),
  CONSTRAINT `fk_order_productid` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `fk_orderid` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;