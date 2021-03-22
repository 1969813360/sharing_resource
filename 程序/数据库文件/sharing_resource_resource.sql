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
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userid` int(11) DEFAULT NULL,
  `filename` varchar(45) DEFAULT NULL,
  `filetype` varchar(20) DEFAULT NULL,
  `category` int(11) DEFAULT NULL,
  `filelocation` varchar(100) DEFAULT NULL,
  `filescore` double DEFAULT NULL,
  `uploaddate` varchar(45) DEFAULT NULL,
  `changedate` varchar(45) DEFAULT NULL,
  `filestate` varchar(10) DEFAULT NULL,
  `fileintroduce` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fileuserid_idx` (`userid`),
  KEY `filecategoryid_idx` (`category`),
  CONSTRAINT `filecategoryid` FOREIGN KEY (`category`) REFERENCES `filecategory` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fileuserid` FOREIGN KEY (`userid`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES (4,12,'vedio1','.mp4',10,'D:/MyIdea/SharingResource/src/main/resources/static/Source/vedio1.mp4',0,'2020/04/15-04:23','2020/04/15-04:23','已过审',''),(5,12,'图片','.jpg',1,'D:/MyIdea/SharingResource/src/main/resources/static/Source/图片.jpg',3.375,'2020/04/15-04:31','2020/04/15-04:31','已过审',''),(6,12,'视频5','.mp4',10,'D:/MyIdea/SharingResource/src/main/resources/static/Source/视频5.mp4',0,'2020/04/15-04:52','2020/04/15-05:01','已过审','唐人街探案预告片'),(7,12,'实习证明','.docx',1,'D:/MyIdea/SharingResource/src/main/resources/static/Source/实习证明.docx',3.75,'2020/04/15-04:55','2020/04/15-04:55','已过审',''),(10,12,'视频6','.mp4',1,'D:/MyIdea/SharingResource/src/main/resources/static/Source/视频6.mp4',0,'2020/04/15-05:25','2020/04/15-05:27','已过审','英雄联盟眼位教学'),(11,12,'视频4','.mp4',1,'D:/MyIdea/SharingResource/src/main/resources/static/Source/视频4.mp4',0,'2020/04/15-05:25','2020/04/15-05:26','已过审','小刀变装，马尾'),(18,12,'视频2','.mp4',1,'D:/MyIdea/SharingResource/src/main/resources/static/Source/视频2.mp4',0,'2020/04/15-09:30','2020/04/15-09:30','已过审','45546'),(19,12,'视频4b06c643b','.mp4',1,'D:/MyIdea/SharingResource/src/main/resources/static/Source/视频4b06c643b.mp4',0,'2020/04/15-09:32','2020/04/15-09:32','已过审','6848464'),(20,12,'视频3','.mp4',1,'D:/MyIdea/SharingResource/src/main/resources/static/Source/视频3.mp4',0,'2020/04/16-10:47','2020/04/16-10:47','已过审','小刀换装--下腰'),(24,13,'16383听课名单4800f598','.xlsx',1,'D:/MyIdea/SharingResource/src/main/resources/static/Source/16383听课名单4800f598.xlsx',0,'2020/04/19-09:10','2020/04/19-09:11','未过审','修改后的内容'),(25,13,'计算机组成原理4次实验PPT','.zip',1,'D:/MyIdea/SharingResource/src/main/resources/static/Source/计算机组成原理4次实验PPT.zip',0,'2020/04/19-09:49','2020/04/19-09:49','待审核','31265'),(26,13,'准考证','.pdf',1,'D:/MyIdea/SharingResource/src/main/resources/static/Source/准考证.pdf',0,'2020/04/19-09:54','2020/04/19-09:54','待审核','42');
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
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
