# ************************************************************
# Sequel Pro SQL dump
# Version 4096
#
# http://www.sequelpro.com/
# http://code.google.com/p/sequel-pro/
#
# Host: us-cdbr-iron-east-01.cleardb.net (MySQL 5.5.37-log)
# Database: heroku_69bff24b1b73e43
# Generation Time: 2014-10-23 08:39:22 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table kontakti
# ------------------------------------------------------------

DROP TABLE IF EXISTS `kontakti`;

CREATE TABLE `kontakti` (
  `KONTAKT_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `KONTAKT_BROJ` varchar(20) NOT NULL,
  `KONTAKT_OPIS` varchar(20) NOT NULL,
  `OSOBA_ID` int(10) unsigned NOT NULL,
  `KONTAKT_VRSTA` varchar(20) DEFAULT '',
  PRIMARY KEY (`KONTAKT_ID`),
  KEY `FK_OSOBA_ID` (`OSOBA_ID`),
  CONSTRAINT `FK_OSOBA_ID` FOREIGN KEY (`OSOBA_ID`) REFERENCES `osoba` (`OSOBA_ID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `kontakti` WRITE;
/*!40000 ALTER TABLE `kontakti` DISABLE KEYS */;

INSERT INTO `kontakti` (`KONTAKT_ID`, `KONTAKT_BROJ`, `KONTAKT_OPIS`, `OSOBA_ID`, `KONTAKT_VRSTA`)
VALUES
	(31,'091234543','Poslovni',161,'Telefon'),
	(41,'barack@usa.com','Osobno',161,'Email'),
	(51,'12345678','Remetinec',171,'Mobitel'),
	(61,'zoki@vlada.hr','Osobno',181,'Email'),
	(91,'0955679258','Osobni',191,'Mobitel'),
	(101,'123456789','Osobni',201,'Mobitel'),
	(111,'ivek@ivo.com','Osobni',211,'Email'),
	(121,'1234567890','Poslovni',221,'Fax'),
	(131,'123454545','test',231,'Telefon'),
	(211,'034567676','Osobni',281,'Telefon'),
	(221,'0923466565','Poslovni',291,'Mobitel'),
	(241,'123123123','Osobni',301,'Mobitel'),
	(301,'343434','test',271,'Mobitel');

/*!40000 ALTER TABLE `kontakti` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table osoba
# ------------------------------------------------------------

DROP TABLE IF EXISTS `osoba`;

CREATE TABLE `osoba` (
  `OSOBA_ID` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `OSOBA_IME` varchar(20) NOT NULL,
  `OSOBA_PREZIME` varchar(20) NOT NULL,
  `OSOBA_GRAD` varchar(20) NOT NULL,
  `OSOBA_OPIS` varchar(20) NOT NULL,
  `OSOBA_SLIKA` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`OSOBA_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `osoba` WRITE;
/*!40000 ALTER TABLE `osoba` DISABLE KEYS */;

INSERT INTO `osoba` (`OSOBA_ID`, `OSOBA_IME`, `OSOBA_PREZIME`, `OSOBA_GRAD`, `OSOBA_OPIS`, `OSOBA_SLIKA`)
VALUES
	(161,'Barack','Obama','Washington','Elektri?ar','fjddluxpndaymfxltixa'),
	(171,'Milan','Bandic','Zagreb','U zatvoru je','fjo6kmzlfistmyck6zzg'),
	(181,'Zoran','Milanovic','Zagreb','Premijer RH','idcgx65sk0khbyte3qya'),
	(191,'Ivo','Sanader','Zagreb','U zatvoru','lieub48hxrsot05ypmb4'),
	(201,'Bill','Gates','Redmond','Faca','wyuuh63qjh5clrmnixrn'),
	(211,'Ivica','Olic','Zadar','Nogometas','hp4nveo40igctoddvrqy'),
	(221,'Tim','Cook','Cupertino','Apple CEO','tgc0hdrk6tlt1o6vkg7h'),
	(231,'Ivo','Josipovic','Zagreb','President RH','ezqgj3us5oldf2gtx91e'),
	(271,'Marko','Poljanic','Varazdin','Programer','qe1kjgv9wihoxkxwe9rk'),
	(281,'Ivo','Ivic','Split','Prijatelj','iliolkzkduxsbpyscm9e'),
	(291,'Rudolf','Rudolfic','Osijek','Staklar','b9fi0lxkaf5txrovs0yr'),
	(301,'Marko','Markovic','Sisak','Testiram','boh2arpvfj9knqelc9yd');

/*!40000 ALTER TABLE `osoba` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
