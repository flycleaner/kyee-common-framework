# ************************************************************
# Sequel Pro SQL dump
# Version 4135
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 202.120.40.105 (MySQL 5.5.41-0ubuntu0.14.04.1-log)
# Database: hcrm_wechat
# Generation Time: 2015-09-11 09:09:35 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_user
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `openId` varchar(100) DEFAULT NULL COMMENT '自增主键',
  `medicalCardNo` varchar(45) DEFAULT NULL COMMENT '医保卡号',
  `status` int(11) DEFAULT '0' COMMENT '用户状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `t_user` WRITE;
/*!40000 ALTER TABLE `t_user` DISABLE KEYS */;

INSERT INTO `t_user` (`id`, `openId`, `medicalCardNo`, `status`)
VALUES
	(2,'o0nipuGNsSGYMyrOZnnUN4ihcl9E','',3),
	(4,'o0nipuF_SKHk4oOa9fV4iyZtehrY','',2),
	(6,'o29yvs2dxKzTAJD9CJzoeL9dwfZI','H03206083',3),
	(7,'o29yvs79RH3adG2BmDhTTFi09tjo','123456789',3),
	(8,'o29yvs8J6Tr8G5eVtAwKa2G3HPMA','123',3),
	(16,'o29yvs31ICAL0R1XB8UgCocl2Hvs','123654',4),
	(17,'o29yvs19Nf80mvhxyuKrRpcudwjI','',3),
	(20,'123',NULL,2),
	(37,'openIdTest','medicalTest',0),
	(39,'openIdTest','medicalTest',0),
	(41,'openIdTest','medicalTest',0),
	(43,'openIdTest','medicalTest',0),
	(45,'openIdTest','medicalTest',0),
	(47,'openIdTest','medicalTest',0),
	(49,'openIdTest','medicalTest',0),
	(52,'openIdTest','medicalTest',0),
	(53,'openIdTest','medicalTest',0),
	(54,'openIdTest','medicalTest',0),
	(55,'openIdTest','medicalTest',0),
	(58,'openIdTest','medicalTest',0),
	(59,'openIdTest','medicalTest',0),
	(60,'openIdTest','medicalTest',0),
	(62,'openIdTest','medicalTest',0);

/*!40000 ALTER TABLE `t_user` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
