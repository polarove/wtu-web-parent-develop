-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 140.210.195.116    Database: wtu
-- ------------------------------------------------------
-- Server version	8.1.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cron`
--

DROP TABLE IF EXISTS `cron`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cron` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT 'Primary Key',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT 'Create Time',
  `update_time` datetime DEFAULT NULL,
  `name` char(15) NOT NULL,
  `cron` char(20) NOT NULL,
  `comment` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COMMENT='定时任务表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cron`
--

LOCK TABLES `cron` WRITE;
/*!40000 ALTER TABLE `cron` DISABLE KEYS */;
INSERT INTO `cron` VALUES (1,'2023-10-24 09:56:50',NULL,'voidTrader','0 0 * * * ?','每小时更新一次'),(2,'2023-10-24 10:36:43',NULL,'fissures','0 * * * * ?','每分钟更新一次'),(3,'2023-10-24 11:35:22',NULL,'alerts','0 * * * * ?','每分钟更新一次'),(4,'2023-10-24 11:38:02',NULL,'arbitration','0 * * * * ?','每分钟更新一次'),(5,'2023-10-24 11:38:02',NULL,'cambionCycle','0 * * * * ?','每分钟更新一次'),(6,'2023-10-24 11:38:02',NULL,'vallisCycle','0 * * * * ?','每分钟更新一次'),(7,'2023-10-24 11:38:02',NULL,'zarimanCycle','0 * * * * ?','每分钟更新一次'),(8,'2023-10-24 11:38:02',NULL,'duviriCycle','0 * * * * ?','每分钟更新一次'),(9,'2023-10-24 11:38:13',NULL,'cetusCycle','0 * * * * ?','每分钟更新一次'),(10,'2023-10-24 12:25:57',NULL,'earthCycle','0 * * * * ?','每分钟更新一次'),(11,'2023-10-24 16:33:56',NULL,'archonHunt','0 * * * * ?','每小时更新一次'),(12,'2023-10-24 19:00:25',NULL,'sortie','0 * * * * ?','每小时更新一次'),(13,'2023-11-09 08:26:46',NULL,'events','0 * * * * ?','每分钟更新一次');
/*!40000 ALTER TABLE `cron` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-10  9:18:56
