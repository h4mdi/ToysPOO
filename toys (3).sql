-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  ven. 01 jan. 2021 à 14:30
-- Version du serveur :  5.7.26
-- Version de PHP :  7.1.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `toys`
--

DELIMITER $$
--
-- Procédures
--
DROP PROCEDURE IF EXISTS `GetSalesByPerson`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetSalesByPerson` (IN `startDate` DATE, IN `endDate` DATE)  NO SQL
SELECT o.SalesPersonId ,SUM(od.Quantity*od.UnitPrice)
FROM orderdetails od 
JOIN orders o ON od.OrderId = o.Id
WHERE o.IsValid=1
AND (startDate is null OR startDate<=o.Date)
AND (endDate is null OR endDate>=o.Date)
GROUP BY od.OrderId$$

DROP PROCEDURE IF EXISTS `GetTotalSales`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetTotalSales` (IN `startDate` DATE, IN `endDate` DATE)  NO SQL
SELECT SUM(od.Quantity*od.UnitPrice)
FROM orderdetails od 
JOIN orders o ON od.OrderId = o.Id
WHERE o.IsValid=1
AND (startDate is null OR startDate<=o.Date)
AND (endDate is null OR endDate>=o.Date)$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `mailinglist`
--

DROP TABLE IF EXISTS `mailinglist`;
CREATE TABLE IF NOT EXISTS `mailinglist` (
  `Email` varchar(50) NOT NULL,
  PRIMARY KEY (`Email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `mailinglist`
--

INSERT INTO `mailinglist` (`Email`) VALUES
('h4Mdy1234@gmail.com');

-- --------------------------------------------------------

--
-- Structure de la table `orderdetails`
--

DROP TABLE IF EXISTS `orderdetails`;
CREATE TABLE IF NOT EXISTS `orderdetails` (
  `OrderId` int(11) NOT NULL,
  `TotyId` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  `UnitPrice` decimal(8,3) NOT NULL,
  PRIMARY KEY (`OrderId`,`TotyId`),
  KEY `TotyId` (`TotyId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `orderdetails`
--

INSERT INTO `orderdetails` (`OrderId`, `TotyId`, `Quantity`, `UnitPrice`) VALUES
(1, 7, 200, '100.000'),
(4, 7, 10, '100.000');

-- --------------------------------------------------------

--
-- Structure de la table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Date` date NOT NULL,
  `OrderNumber` varchar(50) NOT NULL,
  `SalesPersonId` int(11) NOT NULL,
  `IsValid` tinyint(4) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `SalesPersonId` (`SalesPersonId`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `orders`
--

INSERT INTO `orders` (`Id`, `Date`, `OrderNumber`, `SalesPersonId`, `IsValid`) VALUES
(1, '2020-11-01', '10', 1, 1),
(2, '2020-11-01', '10', 1, 1),
(3, '2020-10-01', '11', 1, 1),
(4, '2020-09-01', '12', 2, 1),
(5, '2020-11-01', '15', 1, 1),
(6, '2020-10-01', '70', 2, 1),
(7, '2020-12-01', '10', 2, 1);

-- --------------------------------------------------------

--
-- Structure de la table `phonetypes`
--

DROP TABLE IF EXISTS `phonetypes`;
CREATE TABLE IF NOT EXISTS `phonetypes` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(20) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `phonetypes`
--

INSERT INTO `phonetypes` (`Id`, `Name`) VALUES
(1, 'Bureau'),
(2, 'Portable'),
(3, 'Personnel');

-- --------------------------------------------------------

--
-- Structure de la table `toys`
--

DROP TABLE IF EXISTS `toys`;
CREATE TABLE IF NOT EXISTS `toys` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `TypeId` int(11) NOT NULL,
  `MinAge` int(11) NOT NULL,
  `MaxAge` int(11) DEFAULT NULL,
  `PicturePath` varchar(100) NOT NULL,
  `Price` decimal(8,3) DEFAULT NULL,
  `VendorId` int(11) NOT NULL,
  `Quantity` int(11) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `TypeId` (`TypeId`),
  KEY `VendorId` (`VendorId`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `toys`
--

INSERT INTO `toys` (`Id`, `Name`, `TypeId`, `MinAge`, `MaxAge`, `PicturePath`, `Price`, `VendorId`, `Quantity`) VALUES
(7, 'Updateddc', 120, 5, 4, 'http://localhost/php/img/7.jpg', '200.000', 1, 120),
(8, 'Bazzare', 120, 0, 4, 'http://localhost/php/img/7.jpg', '20.000', 1, 120),
(9, 'ToyJoy', 120, 0, 4, 'http://localhost/php/img/10.png', '500.000', 1, 120),
(10, 'ToyJoy', 120, 0, 4, 'http://localhost/php/img/7.jpg', '850.000', 1, 120),
(11, 'Azz', 120, 0, 4, 'http://localhost/php/img/7.jpg', '65.000', 1, 120),
(58, 'Jozef', 22, 22, 22, '11', '22.000', 22, 11),
(59, 'test', 44, 44, 44, '44', '44.000', 44, 44);

-- --------------------------------------------------------

--
-- Structure de la table `toytypes`
--

DROP TABLE IF EXISTS `toytypes`;
CREATE TABLE IF NOT EXISTS `toytypes` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `toytypes`
--

INSERT INTO `toytypes` (`Id`, `Name`) VALUES
(1, 'Educatif'),
(2, 'Bricolage'),
(3, 'Poupee'),
(4, 'Jeux videos'),
(5, 'Interactif'),
(6, 'Creatif'),
(7, 'Sportif'),
(8, 'testing'),
(9, 'zz');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Login` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `PhotoPath` varchar(200) DEFAULT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `FacebookUrl` varchar(200) DEFAULT NULL,
  `SIN` varchar(20) DEFAULT NULL,
  `IsAdmin` tinyint(4) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`Id`, `Login`, `Password`, `Email`, `PhotoPath`, `Phone`, `FacebookUrl`, `SIN`, `IsAdmin`) VALUES
(1, 'admin', 'admin', 'h4Mdy1234@gmail.com', NULL, NULL, NULL, NULL, 1),
(2, 'user01', 'user01', 'hahahah', 'hhh', 'hhh', 'hhh', 'hh', 0);

-- --------------------------------------------------------

--
-- Structure de la table `vendorphones`
--

DROP TABLE IF EXISTS `vendorphones`;
CREATE TABLE IF NOT EXISTS `vendorphones` (
  `Id` int(11) NOT NULL,
  `PhoneNumber` int(11) NOT NULL,
  `VendorId` int(11) NOT NULL,
  `PhoneTypeId` int(11) DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `PhoneTypeId` (`PhoneTypeId`),
  KEY `VendorId` (`VendorId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `vendors`
--

DROP TABLE IF EXISTS `vendors`;
CREATE TABLE IF NOT EXISTS `vendors` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Address` varchar(100) DEFAULT NULL,
  `FacebookUrl` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `vendors`
--

INSERT INTO `vendors` (`Id`, `Name`, `Email`, `Address`, `FacebookUrl`) VALUES
(1, '3mirat', 'h4Mdy1234@gmail.com', 'Hammamet', 'https://developers.facebook.com/apps/309315746974708/settings/advanced/');

-- --------------------------------------------------------

--
-- Structure de la table `vendortoystypes`
--

DROP TABLE IF EXISTS `vendortoystypes`;
CREATE TABLE IF NOT EXISTS `vendortoystypes` (
  `VendorId` int(11) NOT NULL,
  `ToyTypeId` int(11) NOT NULL,
  PRIMARY KEY (`VendorId`,`ToyTypeId`),
  KEY `ToyTypeId` (`ToyTypeId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `orderdetails`
--
ALTER TABLE `orderdetails`
  ADD CONSTRAINT `Order_OrderDetails` FOREIGN KEY (`OrderId`) REFERENCES `orders` (`Id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
