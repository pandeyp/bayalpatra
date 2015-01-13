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
-- Table structure for table `bayalpatra_email`
--

DROP TABLE IF EXISTS `bayalpatra_email`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bayalpatra_email` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `cc_address` varchar(255) DEFAULT NULL,
  `message_body` longtext NOT NULL,
  `sent_date` datetime DEFAULT NULL,
  `status` bit(1) NOT NULL,
  `subject` varchar(255) NOT NULL,
  `to_address` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bayalpatra_email`
--

LOCK TABLES `bayalpatra_email` WRITE;
/*!40000 ALTER TABLE `bayalpatra_email` DISABLE KEYS */;
INSERT INTO `bayalpatra_email` VALUES (1,1,'psychovipers@gmail.com, abc@gmail.com','Congratulations being appointed as Nephrologits with effect from Wed Jan 07 00:00:00 NPT 2015<br><br> Welcome to /Emergency A as one of the important member of this institution.<br>\n                                                    <br>Username: resham<br>Password: a<br>\n                                                    <br><b>Appointment Details:</b><br>\n                                                    Name: Resham null Lala<br>\n                                                    Id  : 0400001<br>\n                                                    Designation: Nephrologits <br>Department: Emergency A<br>Appointment Date: Wed Jan 07 00:00:00 NPT 2015<br>Thanking You,<br>The Manager<br>Human Resources<br>Bayalpatra-NEPAL','2015-01-13 14:04:33','','Notification of New Staff Appointment','abc@xyz.com'),(2,1,'psychovipers@gmail.com, abc@gmail.com','Congratulations being appointed as Chief Operating Officer with effect from Wed Jan 07 00:00:00 NPT 2015<br><br> Welcome to /BAYALPATRA as one of the important member of this institution.<br>\n                                                    <br>Username: head<br>Password: a<br>\n                                                    <br><b>Appointment Details:</b><br>\n                                                    Name: Head null Boss<br>\n                                                    Id  : 0100002<br>\n                                                    Designation: Chief Operating Officer <br>Department: BAYALPATRA<br>Appointment Date: Wed Jan 07 00:00:00 NPT 2015<br>Thanking You,<br>The Manager<br>Human Resources<br>Bayalpatra-NEPAL','2015-01-13 14:04:38','','Notification of New Staff Appointment','abc@xyz.com'),(3,1,'psychovipers@gmail.com, abc@gmail.com','Congratulations being appointed as Supervisor with effect from Wed Jan 07 00:00:00 NPT 2015<br><br> Welcome to /Emergency as one of the important member of this institution.<br>\n                                                    <br>Username: su<br>Password: a<br>\n                                                    <br><b>Appointment Details:</b><br>\n                                                    Name: Supervisor null Prime<br>\n                                                    Id  : 0400003<br>\n                                                    Designation: Supervisor <br>Department: Emergency<br>Appointment Date: Wed Jan 07 00:00:00 NPT 2015<br>Thanking You,<br>The Manager<br>Human Resources<br>Bayalpatra-NEPAL','2015-01-13 14:04:44','','Notification of New Staff Appointment','abc@xyz.com'),(4,1,'psychovipers@gmail.com, abc@gmail.com','Congratulations being appointed as Dept Head with effect from Wed Jan 07 00:00:00 NPT 2015<br><br> Welcome to /Emergency as one of the important member of this institution.<br>\n                                                    <br>Username: depthead<br>Password: a<br>\n                                                    <br><b>Appointment Details:</b><br>\n                                                    Name: Depthead null dept<br>\n                                                    Id  : 0400004<br>\n                                                    Designation: Dept Head <br>Department: Emergency<br>Appointment Date: Wed Jan 07 00:00:00 NPT 2015<br>Thanking You,<br>The Manager<br>Human Resources<br>Bayalpatra-NEPAL','2015-01-13 14:04:50','','Notification of New Staff Appointment','abc@xyz.com'),(5,1,'psychovipers@gmail.com, abc@gmail.com','Congratulations being appointed as Oncologist with effect from Wed Jan 07 00:00:00 NPT 2015<br><br> Welcome to /Emergency B as one of the important member of this institution.<br>\n                                                    <br>Username: gonad<br>Password: a<br>\n                                                    <br><b>Appointment Details:</b><br>\n                                                    Name: Gonadd null Lal<br>\n                                                    Id  : 0400005<br>\n                                                    Designation: Oncologist <br>Department: Emergency B<br>Appointment Date: Wed Jan 07 00:00:00 NPT 2015<br>Thanking You,<br>The Manager<br>Human Resources<br>Bayalpatra-NEPAL','2015-01-13 14:04:56','','Notification of New Staff Appointment','prasannapandey@gmail.com'),(6,1,'psychovipers@gmail.com, abc@gmail.com, abc@xyz.com','Congratulations being appointed as Nephrologits with effect from Thu Jan 08 00:00:00 NPT 2015<br><br> Welcome to /Emergency A as one of the important member of this institution.<br>\n                                                    <br>Username: supa<br>Password: a<br>\n                                                    <br><b>Appointment Details:</b><br>\n                                                    Name: Supervisor null A<br>\n                                                    Id  : 0400006<br>\n                                                    Designation: Nephrologits <br>Department: Emergency A<br>Appointment Date: Thu Jan 08 00:00:00 NPT 2015<br>Thanking You,<br>The Manager<br>Human Resources<br>Bayalpatra-NEPAL','2015-01-13 14:05:03','','Notification of New Staff Appointment','abc@xyz.com'),(7,1,'psychovipers@gmail.com, abc@gmail.com, abc@xyz.com','Congratulations being appointed as Oncologist with effect from Thu Jan 08 00:00:00 NPT 2015<br><br> Welcome to /OPD as one of the important member of this institution.<br>\n                                                    <br>Username: jose<br>Password: a<br>\n                                                    <br><b>Appointment Details:</b><br>\n                                                    Name: jose null a<br>\n                                                    Id  : 0300007<br>\n                                                    Designation: Oncologist <br>Department: OPD<br>Appointment Date: Thu Jan 08 00:00:00 NPT 2015<br>Thanking You,<br>The Manager<br>Human Resources<br>Bayalpatra-NEPAL','2015-01-13 14:05:09','','Notification of New Staff Appointment','abc@xyz.com'),(8,1,'psychovipers@gmail.com, abc@gmail.com','Congratulations being appointed as Nephrologits with effect from Fri Jan 09 00:00:00 NPT 2015<br><br> Welcome to /Account A as one of the important member of this institution.<br>\n                                                    <br>Username: kat<br>Password: a<br>\n                                                    <br><b>Appointment Details:</b><br>\n                                                    Name: katty null williem<br>\n                                                    Id  : 0200008<br>\n                                                    Designation: Nephrologits <br>Department: Account A<br>Appointment Date: Fri Jan 09 00:00:00 NPT 2015<br>Thanking You,<br>The Manager<br>Human Resources<br>Bayalpatra-NEPAL','2015-01-13 14:05:15','','Notification of New Staff Appointment','abc@xyz.com'),(9,1,'psychovipers@gmail.com, abc@gmail.com, abc@xyz.com','Congratulations being appointed as Nephrologits with effect from Fri Jan 09 00:00:00 NPT 2015<br><br> Welcome to /Account B as one of the important member of this institution.<br>\n                                                    <br>Username: empx<br>Password: asdf<br>\n                                                    <br><b>Appointment Details:</b><br>\n                                                    Name: EmpX null asdfasdf<br>\n                                                    Id  : 0200009<br>\n                                                    Designation: Nephrologits <br>Department: Account B<br>Appointment Date: Fri Jan 09 00:00:00 NPT 2015<br>Thanking You,<br>The Manager<br>Human Resources<br>Bayalpatra-NEPAL','2015-01-13 14:05:21','','Notification of New Staff Appointment','abc@xyz.com'),(10,1,'psychovipers@gmail.com, abc@gmail.com, abc@xyz.com',' We would like to inform you that you have been transferred from Account B Department to Account B effective from Fri Jan 09 11:16:02 NPT 2015<br><br><b>Transfer Details:</b><br>Name: EmpX null asdfasdf<br>ID: 0200009<br>Designation: Nephrologits<br>Department: Account B<br>Transferred to: Account B<br>Staff Status: Permanent<br>Allowance: <br><br> Your efforts in the previous department have always been commendable and we look forward seeing more excellent performance from you in new place. Good luck and hope  you will do much better.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL','2015-01-13 14:05:27','','Notification of Transfer of EmpX null asdfasdf','abc@xyz.com'),(11,1,'psychovipers@gmail.com, abc@gmail.com, abc@xyz.com',' We would like to inform you that you have been transferred from Account B Department to Account B effective from Fri Jan 09 12:01:18 NPT 2015<br><br><b>Transfer Details:</b><br>Name: EmpX null asdfasdf<br>ID: 0200009<br>Designation: Nephrologits<br>Department: Account B<br>Transferred to: Account B<br>Staff Status: Permanent<br>Allowance: <br><br> Your efforts in the previous department have always been commendable and we look forward seeing more excellent performance from you in new place. Good luck and hope  you will do much better.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL','2015-01-13 14:05:34','','Notification of Transfer of EmpX null asdfasdf','abc@xyz.com'),(12,1,'psychovipers@gmail.com, abc@gmail.com, abc@xyz.com',' We would like to inform you that you have been transferred from Account B Department to Account B effective from Fri Jan 09 12:01:56 NPT 2015<br><br><b>Transfer Details:</b><br>Name: EmpX null asdfasdf<br>ID: 0200009<br>Designation: Nephrologits<br>Department: Account B<br>Transferred to: Account B<br>Staff Status: Permanent<br>Allowance: <br><br> Your efforts in the previous department have always been commendable and we look forward seeing more excellent performance from you in new place. Good luck and hope  you will do much better.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL','2015-01-13 14:05:40','','Notification of Transfer of EmpX null asdfasdf','abc@xyz.com'),(13,1,'psychovipers@gmail.com, abc@gmail.com','Congratulations being appointed as Nephrologits with effect from Fri Jan 09 00:00:00 NPT 2015<br><br> Welcome to /Account A as one of the important member of this institution.<br>\n                                                    <br>Username: constant<br>Password: a<br>\n                                                    <br><b>Appointment Details:</b><br>\n                                                    Name: constant null change<br>\n                                                    Id  : 0200010<br>\n                                                    Designation: Nephrologits <br>Department: Account A<br>Appointment Date: Fri Jan 09 00:00:00 NPT 2015<br>Thanking You,<br>The Manager<br>Human Resources<br>Bayalpatra-NEPAL','2015-01-13 14:05:49','','Notification of New Staff Appointment','abc@xyz.com'),(14,1,'psychovipers@gmail.com, abc@gmail.com',' We would like to inform you that you have been transferred from Account A Department to Account A effective from Fri Jan 09 12:07:11 NPT 2015<br><br><b>Transfer Details:</b><br>Name: constant null change<br>ID: 0200010<br>Designation: Nephrologits<br>Department: Account A<br>Transferred to: Account A<br>Staff Status: Permanent<br>Allowance: <br><br> Your efforts in the previous department have always been commendable and we look forward seeing more excellent performance from you in new place. Good luck and hope  you will do much better.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL','2015-01-13 14:05:55','','Notification of Transfer of constant null change','abc@xyz.com'),(15,1,'psychovipers@gmail.com, abc@gmail.com, abc@xyz.com',' We would like to inform you that you have been transferred from Account A Department to Account A effective from Fri Jan 09 12:12:02 NPT 2015<br><br><b>Transfer Details:</b><br>Name: katty null williem<br>ID: 0200008<br>Designation: Nephrologits<br>Department: Account A<br>Transferred to: Account A<br>Staff Status: Permanent<br>Allowance: <br><br> Your efforts in the previous department have always been commendable and we look forward seeing more excellent performance from you in new place. Good luck and hope  you will do much better.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL','2015-01-13 14:06:01','','Notification of Transfer of katty null williem','abc@xyz.com'),(16,1,'psychovipers@gmail.com, abc@gmail.com, abc@xyz.com','Congratulations being appointed as Nephrologits with effect from Fri Jan 09 00:00:00 NPT 2015<br><br> Welcome to /Emergency A as one of the important member of this institution.<br>\n                                                    <br>Username: tst<br>Password: a<br>\n                                                    <br><b>Appointment Details:</b><br>\n                                                    Name: tstChnange null asdfsadf<br>\n                                                    Id  : 0400011<br>\n                                                    Designation: Nephrologits <br>Department: Emergency A<br>Appointment Date: Fri Jan 09 00:00:00 NPT 2015<br>Thanking You,<br>The Manager<br>Human Resources<br>Bayalpatra-NEPAL','2015-01-13 14:06:07','','Notification of New Staff Appointment','abc@xyz.com'),(17,1,'psychovipers@gmail.com, abc@gmail.com, abc@xyz.com',' We would like to inform you that you have been transferred from Emergency A Department to Emergency A effective from Fri Jan 09 12:24:31 NPT 2015<br><br><b>Transfer Details:</b><br>Name: tstChnange null asdfsadf<br>ID: 0400011<br>Designation: Nephrologits<br>Department: Emergency A<br>Transferred to: Emergency A<br>Staff Status: Permanent<br>Allowance: <br><br> Your efforts in the previous department have always been commendable and we look forward seeing more excellent performance from you in new place. Good luck and hope  you will do much better.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL','2015-01-13 14:06:13','','Notification of Transfer of tstChnange null asdfsadf','abc@xyz.com'),(18,1,'psychovipers@gmail.com, abc@gmail.com','Congratulations being appointed as Chief Operating Officer with effect from Fri Jan 09 00:00:00 NPT 2015<br><br> Welcome to /Account A as one of the important member of this institution.<br>\n                                                    <br>Username: wat<br>Password: a<br>\n                                                    <br><b>Appointment Details:</b><br>\n                                                    Name: wat null angkor<br>\n                                                    Id  : 0200012<br>\n                                                    Designation: Chief Operating Officer <br>Department: Account A<br>Appointment Date: Fri Jan 09 00:00:00 NPT 2015<br>Thanking You,<br>The Manager<br>Human Resources<br>Bayalpatra-NEPAL','2015-01-13 14:06:19','','Notification of New Staff Appointment','abc@xyz.com'),(19,1,'psychovipers@gmail.com, abc@gmail.com',' We would like to inform you that you have been transferred from Account A Department to Account A effective from Fri Jan 09 12:28:18 NPT 2015<br><br><b>Transfer Details:</b><br>Name: wat null angkor<br>ID: 0200012<br>Designation: Chief Operating Officer<br>Department: Account A<br>Transferred to: Account A<br>Staff Status: Permanent<br>Allowance: <br><br> Your efforts in the previous department have always been commendable and we look forward seeing more excellent performance from you in new place. Good luck and hope  you will do much better.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL','2015-01-13 14:06:25','','Notification of Transfer of wat null angkor','abc@xyz.com'),(20,1,'psychovipers@gmail.com, abc@gmail.com',' We would like to inform you that you have been transferred from Account A Department to Account A effective from Fri Jan 09 12:37:44 NPT 2015<br><br><b>Transfer Details:</b><br>Name: wat null angkor<br>ID: 0200012<br>Designation: Chief Operating Officer<br>Department: Account A<br>Transferred to: Account A<br>Staff Status: Permanent<br>Allowance: <br><br> Your efforts in the previous department have always been commendable and we look forward seeing more excellent performance from you in new place. Good luck and hope  you will do much better.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL','2015-01-13 14:06:30','','Notification of Transfer of wat null angkor','abc@xyz.com'),(21,1,'psychovipers@gmail.com, abc@gmail.com',' We would like to inform you that you have been transferred from Account B Department to Account B effective from Fri Jan 09 13:15:40 NPT 2015<br><br><b>Transfer Details:</b><br>Name: constant null change<br>ID: 0200010<br>Designation: Nephrologits<br>Department: Account B<br>Transferred to: Account B<br>Staff Status: Permanent<br>Allowance: <br><br> Your efforts in the previous department have always been commendable and we look forward seeing more excellent performance from you in new place. Good luck and hope  you will do much better.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL','2015-01-13 14:06:36','','Notification of Transfer of constant null change','abc@xyz.com'),(22,1,NULL,'The Dependents for EmpX null asdfasdf-0200009 has been updated.<br><b>Updated Fields:</b><br>Father Name<br>Child 2 Year of Birth<br><br>Thank You,<br>Annapurna Support','2015-01-13 14:06:42','','Notification On Update Of Employee Dependents','psychovipers@gmail.com'),(23,1,'psychovipers@gmail.com, abc@gmail.com, abc@xyz.com, abc@xyz.com','This is to notify you that you have been nominated for the training starting from 2015-01-09 to<br>2015-01-10 for 2 Days. Please contact HR for the training detail.<br><br><b>Training Details:</b><br>Name: jose null a<br>ID: 0300007<br>Designation: Oncologist<br>Department: OPD<br>Training from: 2015-01-09 to 2015-01-10<br><br>Please contact HR and the unit incharge/HOD to start your regular duty as soon as training will be over.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL','2015-01-13 14:06:48','',' Notification for Training of jose null a','abc@xyz.com'),(24,1,'psychovipers@gmail.com, abc@gmail.com, abc@xyz.com, abc@xyz.com','This is to notify you that you have been nominated for the training starting from 2015-01-09 to<br>2015-01-09 for 1 Days. Please contact HR for the training detail.<br><br><b>Training Details:</b><br>Name: jose null a<br>ID: 0300007<br>Designation: Oncologist<br>Department: OPD<br>Training from: 2015-01-09 to 2015-01-09<br><br>Please contact HR and the unit incharge/HOD to start your regular duty as soon as training will be over.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL','2015-01-13 14:06:54','',' Notification for Training of jose null a','abc@xyz.com'),(25,1,'psychovipers@gmail.com, abc@gmail.com',' We would like to inform you that you have been transferred from Account A Department to Account B effective from Fri Jan 09 14:22:45 NPT 2015<br><br><b>Transfer Details:</b><br>Name: constant null change<br>ID: 0200010<br>Designation: Nephrologits<br>Department: Account A<br>Transferred to: Account B<br>Staff Status: Trainee<br>Allowance: <br><br> Your efforts in the previous department have always been commendable and we look forward seeing more excellent performance from you in new place. Good luck and hope  you will do much better.<br><br>Thanking You,<br>The Manager<br>Human Resources<br>phect-NEPAL','2015-01-13 14:07:00','','Notification of Transfer of constant null change','abc@xyz.com'),(26,1,'abc@deerwalk.com','Dear Sir,<br/><br/>katty null williem-0200008 has requested for Floating Leave.<br/><br/> Please take action by logging into Bayalpatra.<br/><br/>\n                            Thank You.<br>\n                            Bayalpatra Support.','2015-01-13 14:07:05','','Leave Application katty null williem-0200008','abc@xyz.com'),(27,1,NULL,'Dear katty null williem-0200008,<br><br> You have requested for Floating Leave.<br/><br/> Please log into Bayalpatra to see the status of leave.<br/><br/>\n                            Thank You.<br>\n                            Bayalpatra Support.','2015-01-13 14:07:10','','Leave Application for katty null williem-0200008','abc@xyz.com');
/*!40000 ALTER TABLE `bayalpatra_email` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `company`
--

DROP TABLE IF EXISTS `company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `district` varchar(255) DEFAULT NULL,
  `fax` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `tax_id` varchar(255) NOT NULL,
  `zone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `company`
--

LOCK TABLES `company` WRITE;
/*!40000 ALTER TABLE `company` DISABLE KEYS */;
INSERT INTO `company` VALUES (1,0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'Public Health Fund',NULL,'******',NULL);
/*!40000 ALTER TABLE `company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `id_number` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `parent_id` int(11) NOT NULL,
  `root_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_name` (`parent_id`,`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,0,'01','BAYALPATRA',0,1),(2,0,'02','Admin',1,1),(3,0,'03','OPD',1,1),(4,0,'04','Emergency',1,1),(5,0,'05','Inpatient',1,1),(6,0,'02','Account A',2,2),(7,0,'02','Account B',2,2),(8,0,'04','Emergency A',4,4),(9,0,'04','Emergency B',4,4);
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department_user`
--

DROP TABLE IF EXISTS `department_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department_user` (
  `department_user_id` bigint(20) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  KEY `FK_3ns3d5j79pehbkusthg18b24j` (`user_id`),
  KEY `FK_ci5iv5jae7nijwotcpcveinit` (`department_user_id`),
  CONSTRAINT `FK_3ns3d5j79pehbkusthg18b24j` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_ci5iv5jae7nijwotcpcveinit` FOREIGN KEY (`department_user_id`) REFERENCES `department` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department_user`
--

LOCK TABLES `department_user` WRITE;
/*!40000 ALTER TABLE `department_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `department_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `designation`
--

DROP TABLE IF EXISTS `designation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `designation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `job_description` varchar(255) NOT NULL,
  `job_title_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_7r9lwbcr58sx3dv46prr1h1ks` (`job_title_name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `designation`
--

LOCK TABLES `designation` WRITE;
/*!40000 ALTER TABLE `designation` DISABLE KEYS */;
INSERT INTO `designation` VALUES (1,0,'COO','Chief Operating Officer'),(2,0,'Department Head','Dept Head'),(3,0,'Sup','Supervisor'),(4,0,'Onco','Oncologist'),(5,0,'Nep','Nephrologits');
/*!40000 ALTER TABLE `designation` ENABLE KEYS */;
UNLOCK TABLES;

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
  `suspension_days` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_hr5ovw667hkx0jl5cmyo66wb8` (`department_id`),
  KEY `FK_fvanju2gyowte98s1drrw2g2s` (`designation_id`),
  KEY `FK_5rn0jtava3v3n8gac95ijjfcj` (`salaryclass_id`),
  KEY `FK_prk26n14tsxfnjuygd7ri9k0e` (`supervisor_id`),
  KEY `FK_6jmm3iovfjm7t63bwegg0atfq` (`updated_by_id`),
  KEY `FK_8bkmlw1ix557x3mrcxfyjp3ew` (`updated_department_by_id`),
  CONSTRAINT `FK_5rn0jtava3v3n8gac95ijjfcj` FOREIGN KEY (`salaryclass_id`) REFERENCES `salary_class` (`id`),
  CONSTRAINT `FK_6jmm3iovfjm7t63bwegg0atfq` FOREIGN KEY (`updated_by_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_8bkmlw1ix557x3mrcxfyjp3ew` FOREIGN KEY (`updated_department_by_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_fvanju2gyowte98s1drrw2g2s` FOREIGN KEY (`designation_id`) REFERENCES `designation` (`id`),
  CONSTRAINT `FK_hr5ovw667hkx0jl5cmyo66wb8` FOREIGN KEY (`department_id`) REFERENCES `department` (`id`),
  CONSTRAINT `FK_prk26n14tsxfnjuygd7ri9k0e` FOREIGN KEY (`supervisor_id`) REFERENCES `supervisor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,0,NULL,NULL,'npl','1999-01-07 00:00:00',8,5,NULL,NULL,'abc@xyz.com','0400001','Resham','Male',NULL,'\0','2015-01-07 00:00:00','Lala','Single',NULL,NULL,NULL,'asdfsadfsdf','2015-01-07 00:00:00',NULL,'Permanent',NULL,NULL,NULL,NULL,NULL,NULL,'2015-01-07 00:00:00',0),(2,0,NULL,NULL,'npl','1998-01-07 00:00:00',1,1,NULL,NULL,'abc@xyz.com','0100002','Head','Male',NULL,'\0','2015-01-07 00:00:00','Boss','Married',NULL,NULL,NULL,'adsfsdf','2015-01-07 00:00:00',NULL,'Permanent',NULL,NULL,NULL,NULL,NULL,NULL,'2015-01-07 00:00:00',0),(3,0,NULL,NULL,'npl','1998-01-07 00:00:00',4,3,NULL,NULL,'abc@xyz.com','0400003','Supervisor','Male',NULL,'\0','2015-01-07 00:00:00','Prime','Single',NULL,NULL,NULL,'asfasdfsdf','2015-01-07 00:00:00',NULL,'Permanent',NULL,NULL,NULL,NULL,NULL,NULL,'2015-01-07 00:00:00',0),(4,0,NULL,NULL,'npl','1999-01-07 00:00:00',4,2,NULL,NULL,'abc@xyz.com','0400004','Depthead','Male',NULL,'\0','2015-01-07 00:00:00','dept','Single',NULL,NULL,NULL,'asdfasdfsdf','2015-01-07 00:00:00',NULL,'Permanent',NULL,NULL,NULL,NULL,NULL,NULL,'2015-01-07 00:00:00',0),(5,0,NULL,NULL,'npl','1997-01-07 00:00:00',9,4,NULL,NULL,'abc@xyz.com','0400005','Gonadd','Male',NULL,'\0','2015-01-07 00:00:00','Lal','Single',NULL,NULL,NULL,'asdfasfsdf','2015-01-07 00:00:00',NULL,'Permanent',NULL,NULL,NULL,NULL,NULL,NULL,'2015-01-07 00:00:00',0),(6,0,NULL,NULL,'npl','1998-01-08 00:00:00',8,5,NULL,NULL,'abc@xyz.com','0400006','Supervisor','Male',NULL,'\0','2015-01-08 00:00:00','A','Single',NULL,NULL,NULL,'asdfsadf','2015-01-08 00:00:00',NULL,'Permanent',NULL,NULL,NULL,NULL,NULL,NULL,'2015-01-08 00:00:00',0),(7,0,NULL,NULL,'npl','1997-01-08 00:00:00',3,4,NULL,NULL,'abc@xyz.com','0300007','jose','Male',NULL,'\0','2015-01-08 00:00:00','a','Single',NULL,NULL,NULL,'asdfasdfsdf','2015-01-08 00:00:00',NULL,'Permanent',NULL,5,NULL,NULL,NULL,NULL,'2015-01-08 00:00:00',0),(8,4,NULL,NULL,'npl','1998-01-09 00:00:00',7,5,NULL,NULL,'abc@xyz.com','0200008','katty','Female',NULL,'\0','2015-01-09 00:00:00','williem','Single',NULL,NULL,NULL,'asdfsadf','2015-01-09 00:00:00',NULL,'Permanent',NULL,4,NULL,NULL,NULL,NULL,'2015-01-09 00:00:00',0),(9,3,NULL,'Account A','npl','1999-01-09 00:00:00',7,5,NULL,NULL,'abc@xyz.com','0200009','EmpX','Male',NULL,'\0','2015-01-09 00:00:00','asdfasdf','Single',NULL,NULL,NULL,'asdfasdf','2015-01-09 00:00:00',NULL,'Permanent',NULL,4,NULL,NULL,NULL,NULL,'2015-01-09 00:00:00',0),(10,10,NULL,NULL,'npl','1997-01-09 00:00:00',7,5,NULL,NULL,'abc@xyz.com','0200010','constant','Male',NULL,'\0','2015-01-09 00:00:00','change','Single',NULL,NULL,NULL,'asdfsdfsdf','2015-01-09 00:00:00',NULL,'Trainee',NULL,NULL,NULL,NULL,NULL,NULL,'2015-01-09 00:00:00',0),(11,4,NULL,NULL,'npl','1998-01-09 00:00:00',9,5,NULL,NULL,'abc@xyz.com','0400011','tstChnange','Male',NULL,'\0','2015-01-09 00:00:00','asdfsadf','Single',NULL,NULL,NULL,'asdfsadfsdf','2015-01-09 00:00:00',NULL,'Permanent',NULL,NULL,NULL,NULL,NULL,NULL,'2015-01-09 00:00:00',0),(12,2,NULL,'Emergency A','npl','1997-01-09 00:00:00',6,1,NULL,NULL,'abc@xyz.com','0200012','wat','Male',NULL,'\0','2015-01-09 00:00:00','angkor','Single',NULL,NULL,NULL,'asfasfsdf','2015-01-09 00:00:00',NULL,'Permanent',NULL,NULL,NULL,NULL,NULL,NULL,'2015-01-09 00:00:00',0);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_dependents`
--

DROP TABLE IF EXISTS `employee_dependents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_dependents` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `child1dob` datetime NOT NULL,
  `child2dob` datetime NOT NULL,
  `child_name1` varchar(255) NOT NULL,
  `child_name2` varchar(255) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  `father_address` varchar(255) NOT NULL,
  `father_dob` datetime NOT NULL,
  `father_name` varchar(255) NOT NULL,
  `mother_dob` datetime NOT NULL,
  `mother_name` varchar(255) NOT NULL,
  `spouse_address` varchar(255) NOT NULL,
  `spouse_dob` datetime NOT NULL,
  `spouse_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_65akgnkkb1eqkpbium59hyvb7` (`employee_id`),
  CONSTRAINT `FK_65akgnkkb1eqkpbium59hyvb7` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_dependents`
--

LOCK TABLES `employee_dependents` WRITE;
/*!40000 ALTER TABLE `employee_dependents` DISABLE KEYS */;
INSERT INTO `employee_dependents` VALUES (1,0,'2008-01-01 00:00:00','2015-01-01 00:00:00','asdfsadf','asfasdfsdf',11,'asdfsadf','1997-01-01 00:00:00','asdfsdf','2003-01-01 00:00:00','asdfsdf','asdfsdf','2015-01-01 00:00:00','asdfsadf'),(2,0,'2008-01-01 00:00:00','2005-01-01 00:00:00','asfsaf','asdfsadf',12,'asdfasdf','1999-01-01 00:00:00','asfasdfasdf','2007-01-01 00:00:00','asdfsadf','asdfsadf','2005-01-01 00:00:00','asdfsadf'),(3,0,'2008-01-01 00:00:00','2005-01-01 00:00:00','asfsaf','asdfsadf',12,'asdfasdf','1999-01-01 00:00:00','asfasdfasdf','2007-01-01 00:00:00','asdfsadf','asdfsadf','2005-01-01 00:00:00','asdfsadf'),(4,0,'1997-01-01 00:00:00','2007-01-01 00:00:00','asdfsdf','asdfsdf',5,'asdfsdf','2015-01-01 00:00:00','asdfasdf','2015-01-01 00:00:00','asdfsdf','asdfsadf','2010-01-01 00:00:00','asdfsdf'),(5,1,'2015-01-01 00:00:00','2000-01-01 00:00:00','asdfsdf','asdfsdfsdf',9,'dsafsdf','2015-01-01 00:00:00','asdfasdffffffffffffff','2015-01-01 00:00:00','asdfsdf','asdfsdf','2015-01-01 00:00:00','asdfsdf');
/*!40000 ALTER TABLE `employee_dependents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_education`
--

DROP TABLE IF EXISTS `employee_education`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_education` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `college` varchar(255) NOT NULL,
  `date` datetime NOT NULL,
  `degree` varchar(255) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  `remarks` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_i7k18vnh2y8eilw1okiv7jrop` (`employee_id`),
  CONSTRAINT `FK_i7k18vnh2y8eilw1okiv7jrop` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_education`
--

LOCK TABLES `employee_education` WRITE;
/*!40000 ALTER TABLE `employee_education` DISABLE KEYS */;
INSERT INTO `employee_education` VALUES (1,1,'katta','1998-01-09 00:00:00','BCA',7,'asdfsdf'),(2,0,'asdfsadf','1999-01-09 00:00:00','asdfsadf',7,'asdfsadf');
/*!40000 ALTER TABLE `employee_education` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_history`
--

DROP TABLE IF EXISTS `employee_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  `field_type` varchar(255) DEFAULT NULL,
  `from_date` datetime DEFAULT NULL,
  `old_value` varchar(255) DEFAULT NULL,
  `to_date` datetime DEFAULT NULL,
  `updated_date` datetime NOT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_4usyh5cxrc69imwxe9pyk4fpi` (`employee_id`),
  KEY `FK_p8gpawnm96nib1ojvuyrbiw50` (`user_id`),
  CONSTRAINT `FK_4usyh5cxrc69imwxe9pyk4fpi` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_p8gpawnm96nib1ojvuyrbiw50` FOREIGN KEY (`user_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_history`
--

LOCK TABLES `employee_history` WRITE;
/*!40000 ALTER TABLE `employee_history` DISABLE KEYS */;
INSERT INTO `employee_history` VALUES (1,0,10,'Department','2015-01-09 00:00:00','Account A','2015-01-09 00:00:00','2015-01-09 12:11:30',NULL),(2,0,8,'Department','2015-01-09 00:00:00','Account A','2015-01-09 00:00:00','2015-01-09 12:16:20',NULL),(3,0,10,'Department','2015-01-09 00:00:00','Account B','2015-01-09 00:00:00','2015-01-09 13:17:20',NULL),(4,0,11,'Department','2015-01-09 00:00:00','Emergency A','2015-01-09 00:00:00','2015-01-09 13:18:10',NULL),(5,0,10,'Service Type','2015-01-09 00:00:00','Permanent','2015-01-09 13:20:07','2015-01-09 13:20:07',NULL),(6,0,10,'Department','2015-01-09 00:00:00','Account A','2015-01-09 00:00:00','2015-01-09 14:22:45',NULL);
/*!40000 ALTER TABLE `employee_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_leave_detail`
--

DROP TABLE IF EXISTS `employee_leave_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_leave_detail` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `approved_by` varchar(255) DEFAULT NULL,
  `employee_id` bigint(20) NOT NULL,
  `from_date` datetime NOT NULL,
  `leave_days` double NOT NULL,
  `leave_difference` double NOT NULL,
  `leave_reason` varchar(300) DEFAULT NULL,
  `leave_type_id` bigint(20) NOT NULL,
  `status` varchar(255) NOT NULL,
  `to_date` datetime NOT NULL,
  `which_half` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_ggomf090mia42sus1gmhhto00` (`employee_id`),
  KEY `FK_j9a8kj5la67jcoc0cpim4lsar` (`leave_type_id`),
  CONSTRAINT `FK_ggomf090mia42sus1gmhhto00` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`),
  CONSTRAINT `FK_j9a8kj5la67jcoc0cpim4lsar` FOREIGN KEY (`leave_type_id`) REFERENCES `leave_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_leave_detail`
--

LOCK TABLES `employee_leave_detail` WRITE;
/*!40000 ALTER TABLE `employee_leave_detail` DISABLE KEYS */;
INSERT INTO `employee_leave_detail` VALUES (1,0,NULL,8,'2015-01-12 00:00:00',0,3,NULL,3,'Unapproved','2015-01-14 00:00:00',NULL);
/*!40000 ALTER TABLE `employee_leave_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_training`
--

DROP TABLE IF EXISTS `employee_training`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_training` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `bound_period_from` datetime DEFAULT NULL,
  `bound_period_to` datetime DEFAULT NULL,
  `employee_id` bigint(20) NOT NULL,
  `end_date` datetime NOT NULL,
  `start_date` datetime NOT NULL,
  `title` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_93yeuhd467kil0sem5kjyc6h4` (`employee_id`),
  CONSTRAINT `FK_93yeuhd467kil0sem5kjyc6h4` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_training`
--

LOCK TABLES `employee_training` WRITE;
/*!40000 ALTER TABLE `employee_training` DISABLE KEYS */;
INSERT INTO `employee_training` VALUES (1,1,'1971-02-14 00:00:00','1983-08-17 00:00:00',7,'2015-01-10 00:00:00','2015-01-09 00:00:00','Techieeee'),(2,1,'1972-01-15 00:00:00','1972-02-18 00:00:00',7,'2015-01-09 00:00:00','2015-01-09 00:00:00','blargh');
/*!40000 ALTER TABLE `employee_training` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_report`
--

DROP TABLE IF EXISTS `leave_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `leave_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `annual_leave_days` double NOT NULL,
  `balance_days` double NOT NULL,
  `earned_leave` double NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  `extra_day` double NOT NULL,
  `extra_time` double NOT NULL,
  `leave_date` datetime NOT NULL,
  `opening_balance` double NOT NULL,
  `paid_leave` double NOT NULL,
  `unpaid_leave` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_employee_id` (`leave_date`,`employee_id`),
  KEY `FK_mnf4ckf715rcttcjyl7qnqh80` (`employee_id`),
  CONSTRAINT `FK_mnf4ckf715rcttcjyl7qnqh80` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_report`
--

LOCK TABLES `leave_report` WRITE;
/*!40000 ALTER TABLE `leave_report` DISABLE KEYS */;
/*!40000 ALTER TABLE `leave_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `leave_type`
--

DROP TABLE IF EXISTS `leave_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `leave_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `days` double NOT NULL,
  `leave_type` varchar(255) NOT NULL,
  `paid_unpaid` varchar(6) NOT NULL,
  `status` varchar(8) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_8vreghr323gs6rhulsigiswe0` (`leave_type`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `leave_type`
--

LOCK TABLES `leave_type` WRITE;
/*!40000 ALTER TABLE `leave_type` DISABLE KEYS */;
INSERT INTO `leave_type` VALUES (1,0,10,'Personal','Paid','Active'),(2,0,10,'Sick','Paid','Active'),(3,0,8,'Floating','Paid','Active'),(5,0,0,'Day Off','Paid','Active'),(6,0,0,'Night Off','Paid','Active'),(7,0,0,'Substitute Leave','Paid','Active'),(8,0,0,'Festival Off','Paid','Active');
/*!40000 ALTER TABLE `leave_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registration_code`
--

DROP TABLE IF EXISTS `registration_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `registration_code` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_created` datetime NOT NULL,
  `token` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registration_code`
--

LOCK TABLES `registration_code` WRITE;
/*!40000 ALTER TABLE `registration_code` DISABLE KEYS */;
/*!40000 ALTER TABLE `registration_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `requestmap`
--

DROP TABLE IF EXISTS `requestmap`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `requestmap` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `config_attribute` varchar(255) NOT NULL,
  `url` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_26v03gjht0pvthrnhv99bbgvn` (`url`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `requestmap`
--

LOCK TABLES `requestmap` WRITE;
/*!40000 ALTER TABLE `requestmap` DISABLE KEYS */;
INSERT INTO `requestmap` VALUES (1,0,'permitAll','/favicon.ico'),(2,0,'permitAll','/assets/**'),(3,0,'permitAll','/js/**'),(4,0,'permitAll','/css/**'),(5,0,'permitAll','/images/**'),(6,0,'permitAll','/login'),(7,0,'permitAll','/login.*'),(8,0,'permitAll','/login/*'),(9,0,'permitAll','/logout'),(10,0,'permitAll','/logout.*'),(11,0,'permitAll','/logout/*'),(12,0,'permitAll','/register/**'),(13,0,'permitAll','/image/**'),(14,0,'IS_AUTHENTICATED_FULLY','/**'),(15,0,'ROLE_ADMIN','/user/**'),(16,0,'ROLE_ADMIN','/role/**'),(17,0,'permitAll','/plugins*//**'),(18,0,'ROLE_ADMIN','/department*//**'),(19,0,'ROLE_ADMIN,ROLE_DEPARTMENTHEAD,ROLE_EMPLOYEE,ROLE_SUPERVISOR','/dashboard*//**');
/*!40000 ALTER TABLE `requestmap` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `authority` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_irsamgnera6angm0prq1kemt2` (`authority`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,0,'ROLE_ADMIN'),(2,0,'ROLE_DEPARTMENTHEAD'),(3,0,'ROLE_SUPERVISOR'),(4,0,'ROLE_EMPLOYEE');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salary`
--

DROP TABLE IF EXISTS `salary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salary` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `basic_salary` double NOT NULL,
  `designation_id` bigint(20) NOT NULL,
  `level` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_49vlu0b8n3134rlv0lwtmol15` (`designation_id`),
  CONSTRAINT `FK_49vlu0b8n3134rlv0lwtmol15` FOREIGN KEY (`designation_id`) REFERENCES `designation` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salary`
--

LOCK TABLES `salary` WRITE;
/*!40000 ALTER TABLE `salary` DISABLE KEYS */;
INSERT INTO `salary` VALUES (1,1,40001,1,'1'),(2,1,30010,2,'2'),(3,0,20000,5,'3'),(4,0,30000,4,'4');
/*!40000 ALTER TABLE `salary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salary_class`
--

DROP TABLE IF EXISTS `salary_class`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salary_class` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `day_of_month` int(11) NOT NULL,
  `identifier` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_g2llfhlritwljptqnrg880b24` (`identifier`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salary_class`
--

LOCK TABLES `salary_class` WRITE;
/*!40000 ALTER TABLE `salary_class` DISABLE KEYS */;
/*!40000 ALTER TABLE `salary_class` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salary_report`
--

DROP TABLE IF EXISTS `salary_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salary_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  `salary_date` datetime NOT NULL,
  `salary_month` varchar(255) NOT NULL,
  `tax` double NOT NULL,
  `total` double NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_employee_id` (`salary_month`,`employee_id`),
  KEY `FK_ibb76dl9k0fc8cqbhohw806h4` (`employee_id`),
  CONSTRAINT `FK_ibb76dl9k0fc8cqbhohw806h4` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salary_report`
--

LOCK TABLES `salary_report` WRITE;
/*!40000 ALTER TABLE `salary_report` DISABLE KEYS */;
INSERT INTO `salary_report` VALUES (1,0,2,'2014-05-13 18:23:30','05-2014',6000.15,34000.85),(2,0,12,'2014-05-13 18:23:30','05-2014',6000.15,34000.85),(3,0,4,'2014-05-13 18:23:30','05-2014',4501.5,25508.5),(4,0,1,'2014-05-13 18:23:30','05-2014',3000,17000),(5,0,6,'2014-05-13 18:23:30','05-2014',3000,17000),(6,0,8,'2014-05-13 18:23:30','05-2014',3000,17000),(7,0,9,'2014-05-13 18:23:30','05-2014',3000,17000),(8,0,10,'2014-05-13 18:23:30','05-2014',3000,17000),(9,0,11,'2014-05-13 18:23:30','05-2014',3000,17000),(10,0,5,'2014-05-13 18:23:30','05-2014',4500,25500),(11,0,7,'2014-05-13 18:23:30','05-2014',4500,25500),(16,0,2,'2014-06-15 18:23:44','06-2014',6000.15,34000.85),(17,0,12,'2014-06-15 18:23:44','06-2014',6000.15,34000.85),(18,0,4,'2014-06-15 18:23:44','06-2014',4501.5,25508.5),(19,0,1,'2014-06-15 18:23:44','06-2014',3000,17000),(20,0,6,'2014-06-15 18:23:44','06-2014',3000,17000),(21,0,8,'2014-06-15 18:23:44','06-2014',3000,17000),(22,0,9,'2014-06-15 18:23:44','06-2014',3000,17000),(23,0,10,'2014-06-15 18:23:44','06-2014',3000,17000),(24,0,11,'2014-06-15 18:23:44','06-2014',3000,17000),(25,0,5,'2014-06-15 18:23:44','06-2014',4500,25500),(26,0,7,'2014-06-15 18:23:44','06-2014',4500,25500),(31,0,2,'2014-07-16 18:24:02','07-2014',6000.15,34000.85),(32,0,12,'2014-07-16 18:24:02','07-2014',6000.15,34000.85),(33,0,4,'2014-07-16 18:24:02','07-2014',4501.5,25508.5),(34,0,1,'2014-07-16 18:24:02','07-2014',3000,17000),(35,0,6,'2014-07-16 18:24:02','07-2014',3000,17000),(36,0,8,'2014-07-16 18:24:02','07-2014',3000,17000),(37,0,9,'2014-07-16 18:24:02','07-2014',3000,17000),(38,0,10,'2014-07-16 18:24:02','07-2014',3000,17000),(39,0,11,'2014-07-16 18:24:02','07-2014',3000,17000),(40,0,5,'2014-07-16 18:24:02','07-2014',4500,25500),(41,0,7,'2014-07-16 18:24:02','07-2014',4500,25500),(46,0,2,'2014-08-15 18:24:20','08-2014',6000.15,34000.85),(47,0,12,'2014-08-15 18:24:20','08-2014',6000.15,34000.85),(48,0,4,'2014-08-15 18:24:20','08-2014',4501.5,25508.5),(49,0,1,'2014-08-15 18:24:20','08-2014',3000,17000),(50,0,6,'2014-08-15 18:24:20','08-2014',3000,17000),(51,0,8,'2014-08-15 18:24:20','08-2014',3000,17000),(52,0,9,'2014-08-15 18:24:20','08-2014',3000,17000),(53,0,10,'2014-08-15 18:24:20','08-2014',3000,17000),(54,0,11,'2014-08-15 18:24:20','08-2014',3000,17000),(55,0,5,'2014-08-15 18:24:20','08-2014',4500,25500),(56,0,7,'2014-08-15 18:24:20','08-2014',4500,25500),(63,0,2,'2014-09-16 18:24:49','09-2014',6000.15,34000.85),(64,0,12,'2014-09-16 18:24:49','09-2014',6000.15,34000.85),(65,0,4,'2014-09-16 18:24:49','09-2014',4501.5,25508.5),(66,0,1,'2014-09-16 18:24:49','09-2014',3000,17000),(67,0,6,'2014-09-16 18:24:49','09-2014',3000,17000),(68,0,8,'2014-09-16 18:24:49','09-2014',3000,17000),(69,0,9,'2014-09-16 18:24:49','09-2014',3000,17000),(70,0,10,'2014-09-16 18:24:49','09-2014',3000,17000),(71,0,11,'2014-09-16 18:24:49','09-2014',3000,17000),(72,0,5,'2014-09-16 18:24:49','09-2014',4500,25500),(73,0,7,'2014-09-16 18:24:49','09-2014',4500,25500),(78,0,2,'2014-10-15 18:25:11','10-2014',6000.15,34000.85),(79,0,12,'2014-10-15 18:25:11','10-2014',6000.15,34000.85),(80,0,4,'2014-10-15 18:25:11','10-2014',4501.5,25508.5),(81,0,1,'2014-10-15 18:25:11','10-2014',3000,17000),(82,0,6,'2014-10-15 18:25:11','10-2014',3000,17000),(83,0,8,'2014-10-15 18:25:11','10-2014',3000,17000),(84,0,9,'2014-10-15 18:25:11','10-2014',3000,17000),(85,0,10,'2014-10-15 18:25:11','10-2014',3000,17000),(86,0,11,'2014-10-15 18:25:11','10-2014',3000,17000),(87,0,5,'2014-10-15 18:25:11','10-2014',4500,25500),(88,0,7,'2014-10-15 18:25:11','10-2014',4500,25500),(94,0,2,'2014-11-15 18:25:49','11-2014',6000.15,34000.85),(95,0,12,'2014-11-15 18:25:49','11-2014',6000.15,34000.85),(96,0,4,'2014-11-15 18:25:49','11-2014',4501.5,25508.5),(97,0,1,'2014-11-15 18:25:49','11-2014',3000,17000),(98,0,6,'2014-11-15 18:25:49','11-2014',3000,17000),(99,0,8,'2014-11-15 18:25:49','11-2014',3000,17000),(100,0,9,'2014-11-15 18:25:49','11-2014',3000,17000),(101,0,10,'2014-11-15 18:25:49','11-2014',3000,17000),(102,0,11,'2014-11-15 18:25:49','11-2014',3000,17000),(103,0,5,'2014-11-15 18:25:49','11-2014',4500,25500),(104,0,7,'2014-11-15 18:25:49','11-2014',4500,25500),(109,0,2,'2014-12-16 18:26:09','12-2014',6000.15,34000.85),(110,0,12,'2014-12-16 18:26:09','12-2014',6000.15,34000.85),(111,0,4,'2014-12-16 18:26:09','12-2014',4501.5,25508.5),(112,0,1,'2014-12-16 18:26:09','12-2014',3000,17000),(113,0,6,'2014-12-16 18:26:09','12-2014',3000,17000),(114,0,8,'2014-12-16 18:26:09','12-2014',3000,17000),(115,0,9,'2014-12-16 18:26:09','12-2014',3000,17000),(116,0,10,'2014-12-16 18:26:09','12-2014',3000,17000),(117,0,11,'2014-12-16 18:26:09','12-2014',3000,17000),(118,0,5,'2014-12-16 18:26:09','12-2014',4500,25500),(119,0,7,'2014-12-16 18:26:09','12-2014',4500,25500),(125,0,2,'2015-01-13 18:26:50','01-2015',6000.15,34000.85),(126,0,12,'2015-01-13 18:26:50','01-2015',6000.15,34000.85),(127,0,4,'2015-01-13 18:26:50','01-2015',4501.5,25508.5),(128,0,1,'2015-01-13 18:26:50','01-2015',3000,17000),(129,0,6,'2015-01-13 18:26:50','01-2015',3000,17000),(130,0,8,'2015-01-13 18:26:50','01-2015',3000,17000),(131,0,9,'2015-01-13 18:26:50','01-2015',3000,17000),(132,0,10,'2015-01-13 18:26:50','01-2015',3000,17000),(133,0,11,'2015-01-13 18:26:50','01-2015',3000,17000),(134,0,5,'2015-01-13 18:26:50','01-2015',4500,25500),(135,0,7,'2015-01-13 18:26:50','01-2015',4500,25500);
/*!40000 ALTER TABLE `salary_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supervisor`
--

DROP TABLE IF EXISTS `supervisor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supervisor` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_qg7ic3p9hr8b3nhbuggrjtjj2` (`employee_id`),
  CONSTRAINT `FK_qg7ic3p9hr8b3nhbuggrjtjj2` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supervisor`
--

LOCK TABLES `supervisor` WRITE;
/*!40000 ALTER TABLE `supervisor` DISABLE KEYS */;
INSERT INTO `supervisor` VALUES (4,0,6),(5,0,3),(6,0,5);
/*!40000 ALTER TABLE `supervisor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suspended_employee_details`
--

DROP TABLE IF EXISTS `suspended_employee_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suspended_employee_details` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `employee_id` bigint(20) NOT NULL,
  `end_date` datetime DEFAULT NULL,
  `start_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_3bcj11cpxwgqprpa0y8j3hi8w` (`employee_id`),
  CONSTRAINT `FK_3bcj11cpxwgqprpa0y8j3hi8w` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suspended_employee_details`
--

LOCK TABLES `suspended_employee_details` WRITE;
/*!40000 ALTER TABLE `suspended_employee_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `suspended_employee_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` bigint(20) NOT NULL,
  `account_expired` bit(1) NOT NULL,
  `account_locked` bit(1) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `employee_id` bigint(20) DEFAULT NULL,
  `enabled` bit(1) NOT NULL,
  `last_updated_date` datetime DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `password_expired` bit(1) NOT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  KEY `FK_r1usl9qoplqsbrhha5e0niqng` (`employee_id`),
  CONSTRAINT `FK_r1usl9qoplqsbrhha5e0niqng` FOREIGN KEY (`employee_id`) REFERENCES `employee` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,0,'\0','\0',NULL,NULL,'',NULL,'2c419c2a9ead35afeff0f32452bee98d','\0','admin'),(2,0,'\0','\0',NULL,1,'',NULL,'59f60291e0d08d02b169b727c2c976ba','\0','resham'),(3,0,'\0','\0',NULL,2,'',NULL,'59f60291e0d08d02b169b727c2c976ba','\0','head'),(4,1,'\0','\0',NULL,3,'',NULL,'25c137fb19e7b51b1fbce8b36809ab93','\0','su'),(5,0,'\0','\0',NULL,4,'',NULL,'59f60291e0d08d02b169b727c2c976ba','\0','depthead'),(6,0,'\0','\0',NULL,5,'',NULL,'59f60291e0d08d02b169b727c2c976ba','\0','gonad'),(7,0,'\0','\0',NULL,6,'',NULL,'59f60291e0d08d02b169b727c2c976ba','\0','supa'),(8,1,'\0','\0',NULL,7,'',NULL,'25c137fb19e7b51b1fbce8b36809ab93','\0','jose'),(9,0,'\0','\0',NULL,8,'',NULL,'59f60291e0d08d02b169b727c2c976ba','\0','kat'),(10,0,'\0','\0',NULL,9,'',NULL,'154253623f518778ce057b378808f7b7','\0','empx'),(11,0,'\0','\0',NULL,10,'',NULL,'59f60291e0d08d02b169b727c2c976ba','\0','constant'),(12,0,'\0','\0',NULL,11,'',NULL,'59f60291e0d08d02b169b727c2c976ba','\0','tst'),(13,0,'\0','\0',NULL,12,'',NULL,'59f60291e0d08d02b169b727c2c976ba','\0','wat');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_role` (
  `role_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`role_id`,`user_id`),
  KEY `FK_apcc8lxk2xnug8377fatvbn04` (`user_id`),
  CONSTRAINT `FK_apcc8lxk2xnug8377fatvbn04` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_it77eq964jhfqtu54081ebtio` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1),(4,2),(4,3),(3,4),(2,5),(4,6),(3,7),(2,8),(4,9),(4,10),(4,11),(4,12),(4,13);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-01-13 18:31:15
