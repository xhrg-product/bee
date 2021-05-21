# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.33)
# Database: bee
# Generation Time: 2021-05-21 05:53:09 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table bee_api
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bee_api`;

CREATE TABLE `bee_api` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `path` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `note` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

LOCK TABLES `bee_api` WRITE;
/*!40000 ALTER TABLE `bee_api` DISABLE KEYS */;

INSERT INTO `bee_api` (`id`, `name`, `path`, `note`)
VALUES
	(1,'测试接口','/aa','无');

/*!40000 ALTER TABLE `bee_api` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table bee_api_group
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bee_api_group`;

CREATE TABLE `bee_api_group` (
  `id` int(11) NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分组名称',
  `note` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '分组描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;



# Dump of table bee_filter
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bee_filter`;

CREATE TABLE `bee_filter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `api_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `bee_filter` WRITE;
/*!40000 ALTER TABLE `bee_filter` DISABLE KEYS */;

/*!40000 ALTER TABLE `bee_filter` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table bee_router
# ------------------------------------------------------------

DROP TABLE IF EXISTS `bee_router`;

CREATE TABLE `bee_router` (
  `id` int(11) NOT NULL,
  `type` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT 'http',
  `target_Url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '目标地址',
  `api_id` int(11) DEFAULT NULL COMMENT 'api的id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin ROW_FORMAT=DYNAMIC;

LOCK TABLES `bee_router` WRITE;
/*!40000 ALTER TABLE `bee_router` DISABLE KEYS */;

INSERT INTO `bee_router` (`id`, `type`, `target_Url`, `api_id`)
VALUES
	(1,'http','http://127.0.0.1:9001/bee-demo/query',1);

/*!40000 ALTER TABLE `bee_router` ENABLE KEYS */;
UNLOCK TABLES;

/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
