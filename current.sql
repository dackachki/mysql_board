/*
SQLyog Community v13.1.7 (64 bit)
MySQL - 10.4.17-MariaDB : Database - a2
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`a2` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `a2`;

/*Table structure for table `article` */

DROP TABLE IF EXISTS `article`;

CREATE TABLE `article` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `regDate` datetime NOT NULL DEFAULT current_timestamp(),
  `updateDate` datetime NOT NULL DEFAULT current_timestamp(),
  `title` char(200) NOT NULL,
  `body` text NOT NULL,
  `memberId` int(10) unsigned NOT NULL,
  `boardId` int(10) unsigned NOT NULL,
  `hit` int(10) NOT NULL DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

/*Data for the table `article` */

insert  into `article`(`id`,`regDate`,`updateDate`,`title`,`body`,`memberId`,`boardId`,`hit`) values 
(1,'2020-12-17 13:43:36','2020-12-17 13:43:36','Java 1회','# Java 20-12-17\r\n## Java 1강\r\n- Test\r\n```java \r\nint a = 5;\r\nint b = 10;\r\n\r\na+b=?\r\n```',1,3,0),
(2,'2020-12-17 14:08:31','2020-12-17 14:08:31','Java 2회','# Java 20-12-17\r\n## Java 2강\r\n- String to Int, Int to String\r\n```java \r\nString a;\r\nInt b;\r\n// String to Int\r\n    b = Integer.parseInt(a);\r\n// Int to String\r\n    a = Integer.toString(b);\r\n```',1,3,0),
(3,'2020-12-18 10:22:52','2020-12-18 10:22:52','공지 Test','# 공지사항\r\n\r\n## 공지 TEST\r\n\r\n- Test\r\n\r\n공지공지 \r\n공지공지 \r\n공지공지 \n공지공지 \r\n공지공지    ',1,2,0),
(4,'2020-12-21 12:08:29','2020-12-21 12:08:29','홈페이지 용도','# 공지사항\r\n\r\n## 공지\r\n - 포트폴리오용 개인 블로그 입니다.',1,2,0),
(6,'2020-12-21 12:11:34','2020-12-21 12:11:34','Java 3회','# Java 20-12-21\r\n\r\n## Java 3강\r\n\r\n- Array,ArrayList\r\n\r\n```java\r\n// Array\r\nint[] numbers  = {0,1,2};\r\n\r\n//ArrayList\r\nList<자료형(Integer,String 등등)> abc = new ArrayList<>();\r\n// 여기선 Integer형\r\n    abc.add(0);\r\n    abc.add(1);\r\n    abc.add(2);\r\n\r\n```\r\n',1,3,0),
(9,'2020-12-22 18:27:15','2020-12-22 18:27:15','이모티콘 추가','???????\r\n이모티콘을 사용하기 위해서 utf-8이 아닌 utf8mb4으로 인코딩이 필요하다.\r\n사용중인 sqlylog은 기본적으로 utf-8을 사용하기 떄문에 \r\nmy.ini에서 [mysqld] 아래에 character-set-client-handshake = FALSE 을 설정하여\r\nclient의 charset을 서버의 것으로 동기화를 시켜 매번 바꿔줘야 하는\r\n불편함을 없앨 수 있다.',1,3,0),
(10,'2020-12-29 22:44:57','2020-12-29 22:44:57','Java & JavaScript','# Java 20-12-29\r\n\r\n## Java와 JavaScript\r\n\r\n - 이름이 비슷하여 서로 관련이 있을 줄 알았지만\r\n    전혀 별개의 언어이며 Java는 서버측 JavaScript는 클라이언트측 언어로\r\n    구분하며 Node.js를 사용하면 JavaScript로도 서버개발이 가능하다고 한다.',1,3,0),
(11,'2021-01-04 19:14:01','2021-01-04 19:14:01','Vue Computed를 사용한 더하기 구현','# Vue 21-01-04\r\n\r\n## Computed를 사용하여 더하기 기능 구현\r\n\r\n- 저장 버튼을 누르면 입력했던 숫자가 저장이 되어 리스트 형태로 출력됨\r\n  [해당 Codepen 바로가기](https://codepen.io/dackachki/pen/YzGaMyy?editors=1111)\r\n- funtion submit에서 var를 맨 처음 선언하고 그 다음부터 안해도 상관이 없는지..\r\n  각각 선언하면 console에서 Unexpect Token \'var\' 에러 출력\r\n- 배열에 초기값을 넣어줘야 되서 모두 1로 선언 후 v-if로 index가 0이상만 나오도록 함',1,3,0);

/*Table structure for table `board` */

DROP TABLE IF EXISTS `board`;

CREATE TABLE `board` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `boardName` char(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `board` */

insert  into `board`(`id`,`boardName`) values 
(2,'info'),
(3,'IT');

/*Table structure for table `members` */

DROP TABLE IF EXISTS `members`;

CREATE TABLE `members` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `memberName` char(50) NOT NULL,
  `memberId` char(50) NOT NULL,
  `memberPw` char(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `members` */

insert  into `members`(`id`,`memberName`,`memberId`,`memberPw`) values 
(1,'admin','admin','admin');

/*Table structure for table `recommand` */

DROP TABLE IF EXISTS `recommand`;

CREATE TABLE `recommand` (
  `articleNum` int(10) NOT NULL,
  `memberId` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `recommand` */

insert  into `recommand`(`articleNum`,`memberId`) values 
(1,4),
(2,2),
(3,3),
(1,1);

/*Table structure for table `replies` */

DROP TABLE IF EXISTS `replies`;

CREATE TABLE `replies` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `bodyR` text NOT NULL,
  `articleNumber` int(10) NOT NULL,
  `writeMemberId` int(10) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `replies` */

insert  into `replies`(`id`,`bodyR`,`articleNumber`,`writeMemberId`) values 
(1,'asdasddas',3,2);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
