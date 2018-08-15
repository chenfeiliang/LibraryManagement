/*
SQLyog Enterprise - MySQL GUI v6.06
Host - 5.1.12-beta-community-nt : Database - library
*********************************************************************
Server version : 5.1.12-beta-community-nt
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

create database if not exists `library`;

USE `library`;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

/*Table structure for table `allbook` */

DROP TABLE IF EXISTS `allbook`;

CREATE TABLE `allbook` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `bookName` char(20) CHARACTER SET utf8 DEFAULT NULL,
  `count` int(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `allbook` */

insert  into `allbook`(`id`,`bookName`,`count`) values (1,'活着',1),(15,'C语言程序设计',13),(17,'李白诗集',3),(18,'白夜行',1);

/*Table structure for table `borrowrecord` */

DROP TABLE IF EXISTS `borrowrecord`;

CREATE TABLE `borrowrecord` (
  `id` int(6) NOT NULL AUTO_INCREMENT,
  `name` char(10) CHARACTER SET utf8 DEFAULT NULL,
  `book` char(20) CHARACTER SET utf8 DEFAULT NULL,
  `count` int(2) DEFAULT NULL,
  `date` char(10) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `borrowrecord` */

insert  into `borrowrecord`(`id`,`name`,`book`,`count`,`date`) values (1,'陈飞良','活着',12,'20170631'),(2,'宋东情','美食鉴赏',1,'20170308'),(13,'陈飞良','C语言程序设计',3,'20170701');

/*Table structure for table `managerland` */

DROP TABLE IF EXISTS `managerland`;

CREATE TABLE `managerland` (
  `user` char(20) CHARACTER SET utf8 DEFAULT NULL,
  `password` char(20) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `managerland` */

insert  into `managerland`(`user`,`password`) values ('宋东情','2015');

/*Table structure for table `studentland` */

DROP TABLE IF EXISTS `studentland`;

CREATE TABLE `studentland` (
  `user` char(20) CHARACTER SET utf8 DEFAULT NULL,
  `password` char(20) CHARACTER SET utf8 DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `studentland` */

insert  into `studentland`(`user`,`password`) values ('陈飞良','2015'),('利俊霖','2015');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
