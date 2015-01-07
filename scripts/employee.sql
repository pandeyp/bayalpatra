-- MySQL dump 10.13  Distrib 5.5.40, for debian-linux-gnu (x86_64)
--
-- Host: localhost    Database: bayalpatra_dev
-- ------------------------------------------------------
-- Server version	5.5.40-0ubuntu0.14.04.1

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
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `alter_email` varchar(255) DEFAULT NULL,
  `change_department` varchar(255) DEFAULT NULL,
  `country` varchar(255) NOT NULL,
  `date_of_birth` datetime NOT NULL,
  `department_id` bigint(20) NOT NULL,
  `designation_id` bigint(20) NOT NULL,
  `effective_date` datetime DEFAULT NULL,
  `effective_date_for_department` datetime DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `employee_id` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `home_phone` varchar(255) DEFAULT NULL,
  `is_doc` bit(1) DEFAULT NULL,
  `join_date` datetime NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `marital_status` varchar(255) NOT NULL,
  `middle_name` varchar(255) DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `nationality` varchar(255) DEFAULT NULL,
  `permanent_address` varchar(255) NOT NULL,
  `promotion_date` datetime NOT NULL,
  `salaryclass_id` bigint(20) DEFAULT NULL,
  `status` varchar(10) NOT NULL,
  `status_changed_to` varchar(255) DEFAULT NULL,
  `supervisor_id` bigint(20) DEFAULT NULL,
  `temporary_address` varchar(255) DEFAULT NULL,
  `terminated_date` datetime DEFAULT NULL,
  `updated_by_id` bigint(20) DEFAULT NULL,
  `updated_department_by_id` bigint(20) DEFAULT NULL,
  `updated_join_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_hr5ovw667hkx0jl5cmyo66wb8` (`department_id`),
  KEY `FK_fvanju2gyowte98s1drrw2g2s` (`designation_id`),
  KEY `FK_5rn0jtava3v3n8gac95ijjfcj` (`salaryclass_id`),
  KEY `FK_prk26n14tsxfnjuygd7ri9k0e` (`supervisor_id`),
  KEY `FK_6jmm3iovfjm7t63bwegg0atfq` (`updated_by_id`),
  KEY `FK_8bkmlw1ix557x3mrcxfyjp3ew` (`updated_department_by_id`),
  CONSTRAINT `FK_8bkmlw1ix557x3mrcxfyjp3ew` FOREIGN KEY (`updated_department_by_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_5rn0jtava3v3n8gac95ijjfcj` FOREIGN KEY (`salaryclass_id`) REFERENCES `salary_class` (`id`),
  CONSTRAINT `FK_6jmm3iovfjm7t63bwegg0atfq` FOREIGN KEY (`updated_by_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_fvanju2gyowte98s1drrw2g2s` FOREIGN KEY (`designation_id`) REFERENCES `designation` (`id`),
  CONSTRAINT `FK_hr5ovw667hkx0jl5cmyo66wb8` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
  CONSTRAINT `FK_prk26n14tsxfnjuygd7ri9k0e` FOREIGN KEY (`supervisor_id`) REFERENCES `supervisor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,0,NULL,NULL,'npl','1999-01-07 00:00:00',8,5,NULL,NULL,'abc@xyz.com','0400001','Resham','Male',NULL,'\0','2015-01-07 00:00:00','Lala','Single',NULL,NULL,NULL,'asdfsadfsdf','2015-01-07 00:00:00',NULL,'Permanent',NULL,NULL,NULL,NULL,NULL,NULL,'2015-01-07 00:00:00'),(2,0,NULL,NULL,'npl','1998-01-07 00:00:00',1,1,NULL,NULL,'abc@xyz.com','0100002','Head','Male',NULL,'\0','2015-01-07 00:00:00','Boss','Married',NULL,NULL,NULL,'adsfsdf','2015-01-07 00:00:00',NULL,'Permanent',NULL,NULL,NULL,NULL,NULL,NULL,'2015-01-07 00:00:00'),(3,0,NULL,NULL,'npl','1998-01-07 00:00:00',4,3,NULL,NULL,'abc@xyz.com','0400003','Supervisor','Male',NULL,'\0','2015-01-07 00:00:00','Prime','Single',NULL,NULL,NULL,'asfasdfsdf','2015-01-07 00:00:00',NULL,'Permanent',NULL,NULL,NULL,NULL,NULL,NULL,'2015-01-07 00:00:00'),(4,0,NULL,NULL,'npl','1999-01-07 00:00:00',4,2,NULL,NULL,'abc@xyz.com','0400004','Depthead','Male',NULL,'\0','2015-01-07 00:00:00','dept','Single',NULL,NULL,NULL,'asdfasdfsdf','2015-01-07 00:00:00',NULL,'Permanent',NULL,NULL,NULL,NULL,NULL,NULL,'2015-01-07 00:00:00'),(5,0,NULL,NULL,'npl','1997-01-07 00:00:00',9,4,NULL,NULL,'abc@xyz.com','0400005','Gonadd','Male',NULL,'\0','2015-01-07 00:00:00','Lal','Single',NULL,NULL,NULL,'asdfasfsdf','2015-01-07 00:00:00',NULL,'Permanent',NULL,NULL,NULL,NULL,NULL,NULL,'2015-01-07 00:00:00');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-01-07 12:50:57
