# ************************************************************
# Sequel Pro SQL dump
# Version 4135
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: 127.0.0.1 (MySQL 5.5.38)
# Database: hcrm_test
# Generation Time: 2015-09-11 09:09:22 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table t_user_hospital
# ------------------------------------------------------------

DROP TABLE IF EXISTS `t_user_hospital`;

CREATE TABLE `t_user_hospital` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `openId` varchar(11) DEFAULT NULL,
  `HospitalId` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `t_user_hospital` WRITE;
/*!40000 ALTER TABLE `t_user_hospital` DISABLE KEYS */;

INSERT INTO `t_user_hospital` (`id`, `openId`, `HospitalId`)
VALUES
	(1,'12','0'),
	(2,'medicalTest','100'),
	(4,'medicalTest','100'),
	(6,'medicalTest','100'),
	(9,'medicalTest','100'),
	(10,'medicalTest','100'),
	(11,'medicalTest','100'),
	(12,'medicalTest','100'),
	(15,'medicalTest','100'),
	(16,'medicalTest','100'),
	(18,'medicalTest','100'),
	(19,'medicalTest','100');

/*!40000 ALTER TABLE `t_user_hospital` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
