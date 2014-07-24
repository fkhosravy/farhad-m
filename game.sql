/*
SQLyog Ultimate v9.62 
MySQL - 5.5.16 : Database - game
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`game` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci */;

USE `game`;

/*Table structure for table `game` */

DROP TABLE IF EXISTS `game`;

CREATE TABLE `game` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `charge_per_day` int(11) DEFAULT NULL,
  `file_name` varchar(100) NOT NULL,
  `parent_prefix` varchar(20) NOT NULL,
  `prefix` varchar(20) NOT NULL,
  `replacable` tinyint(1) DEFAULT NULL,
  `reserved1` varchar(25) DEFAULT NULL,
  `reserved2` varchar(25) DEFAULT NULL,
  `serviceid` varchar(100) NOT NULL,
  `version` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Table structure for table `game_keywords` */

DROP TABLE IF EXISTS `game_keywords`;

CREATE TABLE `game_keywords` (
  `id` int(8) unsigned NOT NULL,
  `game_id` int(8) NOT NULL,
  `keyword` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `type` enum('LIST','HELP','SCORE','TABLE','NEXT','CANCEL','START','FINISH') COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `game_reminder` */

DROP TABLE IF EXISTS `game_reminder`;

CREATE TABLE `game_reminder` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `game_id` int(8) NOT NULL,
  `hour` int(2) NOT NULL,
  `period` enum('ONCE_PER_DAY','ONCE_PER_WEEK','ONCE_PER_2WEEK','ONCE_PER_MONTH') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'ONCE_PER_DAY',
  `action` enum('reminder','invite','deactivation') COLLATE utf8_unicode_ci NOT NULL DEFAULT 'reminder',
  `message` text COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `game_state` */

DROP TABLE IF EXISTS `game_state`;

CREATE TABLE `game_state` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `game_id` int(8) NOT NULL,
  `code` int(8) NOT NULL,
  `name` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` int(4) DEFAULT '0',
  `score` int(8) DEFAULT '0',
  `desc` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `header` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `footer` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `question` tinyint(1) DEFAULT '1',
  `nextStageCode` int(8) DEFAULT NULL,
  `startStage` tinyint(1) DEFAULT NULL,
  `welcomeMessage` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  `finalStage` tinyint(1) DEFAULT NULL,
  `goodByMessage` varchar(512) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `player` */

DROP TABLE IF EXISTS `player`;

CREATE TABLE `player` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `game_id` bigint(20) NOT NULL,
  `game_state` int(11) DEFAULT NULL,
  `last_request_date` datetime DEFAULT NULL,
  `last_stage_id` varchar(8) NOT NULL,
  `mobile` varchar(12) NOT NULL,
  `score` int(11) NOT NULL DEFAULT '0',
  `question_no` int(11) NOT NULL DEFAULT '0',
  `charge_no` int(11) DEFAULT '0',
  `last_charge_date` datetime DEFAULT NULL,
  `register_date` datetime DEFAULT NULL,
  `reserved1` varchar(25) DEFAULT NULL,
  `reserved2` varchar(25) DEFAULT NULL,
  `reserved3` varchar(25) DEFAULT NULL,
  `version` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKC53E9AE15AB59C62` (`game_id`),
  CONSTRAINT `FKC53E9AE15AB59C62` FOREIGN KEY (`game_id`) REFERENCES `game` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;

/*Table structure for table `player_black_list` */

DROP TABLE IF EXISTS `player_black_list`;

CREATE TABLE `player_black_list` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `mobile` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `game_id` int(8) DEFAULT NULL,
  `game_prefix` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `set_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `player_state` */

DROP TABLE IF EXISTS `player_state`;

CREATE TABLE `player_state` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_label` varchar(25) NOT NULL,
  `param_name` varchar(25) NOT NULL,
  `param_value` bigint(20) NOT NULL,
  `reserved1` varchar(25) DEFAULT NULL,
  `reserved2` varchar(25) DEFAULT NULL,
  `version` int(11) NOT NULL,
  `version_` int(11) DEFAULT NULL,
  `player_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK56455B3E8752802` (`player_id`),
  CONSTRAINT `FK56455B3E8752802` FOREIGN KEY (`player_id`) REFERENCES `player` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `player_state_log` */

DROP TABLE IF EXISTS `player_state_log`;

CREATE TABLE `player_state_log` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `player_id` int(20) DEFAULT NULL,
  `mobile` varchar(12) COLLATE utf8_unicode_ci NOT NULL,
  `first_token` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `second_token` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `game_id` int(8) DEFAULT NULL,
  `current_stage` varchar(8) COLLATE utf8_unicode_ci DEFAULT NULL,
  `set_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `state_condition` */

DROP TABLE IF EXISTS `state_condition`;

CREATE TABLE `state_condition` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `state_id` int(8) NOT NULL,
  `inputCode` int(8) DEFAULT NULL,
  `targetStage` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `state_id` (`state_id`),
  CONSTRAINT `state_id` FOREIGN KEY (`state_id`) REFERENCES `game_state` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` varchar(255) NOT NULL,
  `pass` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `view1` */

DROP TABLE IF EXISTS `view1`;

/*!50001 DROP VIEW IF EXISTS `view1` */;
/*!50001 DROP TABLE IF EXISTS `view1` */;

/*!50001 CREATE TABLE  `view1`(
 `Count(player.id)` bigint(21) ,
 `game_id` bigint(20) ,
 `game_state` int(11) 
)*/;

/*View structure for view view1 */

/*!50001 DROP TABLE IF EXISTS `view1` */;
/*!50001 DROP VIEW IF EXISTS `view1` */;

/*!50001 CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `view1` AS select count(`player`.`id`) AS `Count(player.id)`,`player`.`game_id` AS `game_id`,`player`.`game_state` AS `game_state` from `player` group by `player`.`game_id`,`player`.`game_state` */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
