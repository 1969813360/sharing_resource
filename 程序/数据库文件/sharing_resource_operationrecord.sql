-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: sharing_resource
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `operationrecord`
--

DROP TABLE IF EXISTS `operationrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operationrecord` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `fileid` int(11) DEFAULT NULL,
  `operationtype` varchar(20) DEFAULT NULL,
  `datail` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `operationuserid_idx` (`userid`),
  KEY `operationfileid_idx` (`fileid`),
  CONSTRAINT `operationfileid` FOREIGN KEY (`fileid`) REFERENCES `resource` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `operationuserid` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operationrecord`
--

LOCK TABLES `operationrecord` WRITE;
/*!40000 ALTER TABLE `operationrecord` DISABLE KEYS */;
INSERT INTO `operationrecord` VALUES (6,12,4,'上传','用户：王玉柯于2020/04/15-04:23上传该文件，等待审核'),(7,12,5,'上传','用户：王玉柯于2020/04/15-04:31上传该文件，等待审核'),(8,12,6,'上传','用户：王玉柯于2020/04/15-04:52上传该文件，等待审核'),(9,12,7,'上传','用户：王玉柯于2020/04/15-04:55上传该文件，等待审核'),(10,12,6,'修改','用户：王玉柯于2020/04/15-05:01修改该文件'),(13,12,10,'上传','用户：王玉柯于2020/04/15-05:25上传该文件，等待审核'),(14,12,11,'上传','用户：王玉柯于2020/04/15-05:25上传该文件，等待审核'),(15,12,11,'修改','用户：王玉柯于2020/04/15-05:26修改该文件'),(16,11,10,'修改','用户：Admin于2020/04/15-05:27修改该文件'),(23,12,18,'上传','用户：王玉柯于2020/04/15-09:30上传该文件，等待审核'),(24,12,19,'上传','用户：王玉柯于2020/04/15-09:32上传该文件，等待审核'),(25,12,20,'上传','用户：王玉柯于2020/04/16-10:47上传该文件，等待审核'),(26,12,20,'修改','用户：王玉柯于2020/04/16-10:47修改该文件'),(29,11,4,'修改','用户:Admin于2020/04/19-08:34修改该文件'),(30,11,5,'修改','用户:Admin于2020/04/19-08:34修改该文件'),(31,11,6,'修改','用户:Admin于2020/04/19-08:34修改该文件'),(32,11,7,'修改','用户:Admin于2020/04/19-08:34修改该文件'),(33,11,10,'修改','用户:Admin于2020/04/19-08:34修改该文件'),(34,11,11,'修改','用户:Admin于2020/04/19-08:34修改该文件'),(35,11,18,'修改','用户:Admin于2020/04/19-08:34修改该文件'),(36,11,19,'修改','用户:Admin于2020/04/19-08:34修改该文件'),(37,11,20,'修改','用户:Admin于2020/04/19-08:34修改该文件'),(40,13,24,'上传','用户：演员一号于2020/04/19-09:10上传该文件，等待审核'),(41,13,24,'修改','用户：演员一号于2020/04/19-09:11修改该文件'),(42,13,4,'下载','用户：演员一号于2020/04/19-09:30下载该文件至：D：/download'),(43,13,25,'上传','用户：演员一号于2020/04/19-09:49上传该文件，等待审核'),(44,13,26,'上传','用户：演员一号于2020/04/19-09:54上传该文件，等待审核'),(46,11,19,'修改','用户:Admin于2020/04/19-11:03修改该文件'),(47,11,19,'修改','用户:Admin于2020/04/19-11:03修改该文件'),(48,11,19,'修改','用户:Admin于2020/04/19-11:05修改该文件'),(49,11,19,'修改','用户:Admin于2020/04/19-11:05修改该文件'),(50,14,5,'下载','用户：演员二号于2020/04/20-09:08下载该文件至：D：/download'),(51,14,7,'下载','用户：演员二号于2020/04/20-09:09下载该文件至：D：/download');
/*!40000 ALTER TABLE `operationrecord` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-04-20 15:53:28
