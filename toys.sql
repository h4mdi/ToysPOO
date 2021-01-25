-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  lun. 25 jan. 2021 à 14:40
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
DROP PROCEDURE IF EXISTS `GetMaxSalesByPerson`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetMaxSalesByPerson` (IN `startDate` DATE, IN `endDate` DATE)  NO SQL
SELECT o.SalesPersonId ,SUM(od.Quantity*od.UnitPrice)
FROM orderdetails od 
JOIN orders o ON od.OrderId = o.Id
WHERE o.IsValid=1
AND (startDate is null OR startDate<=o.Date)
AND (endDate is null OR endDate>=o.Date)
GROUP BY od.OrderId
ORDER BY SUM(od.Quantity*od.UnitPrice) DESC
LIMIT 1$$

DROP PROCEDURE IF EXISTS `GetSalesByPerson`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetSalesByPerson` (IN `startDate` DATE, IN `endDate` DATE)  NO SQL
SELECT o.SalesPersonId ,SUM(od.Quantity*od.UnitPrice)
FROM orderdetails od 
JOIN orders o ON od.OrderId = o.Id
WHERE o.IsValid=1
AND (startDate is null OR startDate<=o.Date)
AND (endDate is null OR endDate>=o.Date)
GROUP BY od.OrderId$$

DROP PROCEDURE IF EXISTS `GetSalesByToy`$$
CREATE DEFINER=`root`@`localhost` PROCEDURE `GetSalesByToy` (IN `startDate` DATE, IN `endDate` DATE)  NO SQL
SELECT tt.Name,SUM(od.Quantity*od.UnitPrice)
FROM orders o
JOIN orderdetails od on o.Id=od.OrderId
JOIN toys t on t.Id = od.TotyId 
JOIN toytypes tt on t.TypeId = tt.Id
WHERE o.IsValid=1
AND (startDate is null OR startDate<=o.Date)
AND (endDate is null OR endDate>=o.Date)
GROUP BY tt.Name
ORDER BY tt.Name$$

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
('bourouniahamdi7@gmail.Com'),
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
  `orderNumber` int(11) NOT NULL,
  PRIMARY KEY (`OrderId`,`TotyId`),
  KEY `TotyId` (`TotyId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `orderdetails`
--

INSERT INTO `orderdetails` (`OrderId`, `TotyId`, `Quantity`, `UnitPrice`, `orderNumber`) VALUES
(1, 71, 71, '20.000', 1),
(2, 70, 70, '10.000', 1),
(3, 70, 70, '10.000', 2),
(4, 70, 70, '10.000', 3),
(5, 71, 71, '20.000', 3);

-- --------------------------------------------------------

--
-- Structure de la table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `Date` date NOT NULL,
  `OrderNumber` int(11) NOT NULL,
  `SalesPersonId` int(11) NOT NULL,
  `IsValid` tinyint(4) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `SalesPersonId` (`SalesPersonId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `orders`
--

INSERT INTO `orders` (`Id`, `Date`, `OrderNumber`, `SalesPersonId`, `IsValid`) VALUES
(1, '2021-01-24', 1, 2, 0),
(2, '2021-01-24', 1, 2, 0),
(3, '2021-01-24', 2, 2, 1),
(4, '2021-01-24', 3, 2, 0),
(5, '2021-01-24', 3, 2, 0);

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
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `toys`
--

INSERT INTO `toys` (`Id`, `Name`, `TypeId`, `MinAge`, `MaxAge`, `PicturePath`, `Price`, `VendorId`, `Quantity`) VALUES
(70, 'xeds', 6, 0, 10, 'http://localhost/toys/photos/2000px-LaTeX_logo.svg.png', '10.000', 1, 10),
(71, 'too', 8, 1, 1, 'http://localhost/toys/photos/pwd_user.png', '20.000', 2, 1);

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`Id`, `Login`, `Password`, `Email`, `PhotoPath`, `Phone`, `FacebookUrl`, `SIN`, `IsAdmin`) VALUES
(1, 'admin', 'admin', 'h4Mdy1234@gmail.com', NULL, NULL, NULL, NULL, 1),
(2, 'user01', 'user01', 'hahahah', 'C:\\Users\\hp\\IdeaProjects\\ToysPOO\\src\\main\\resources\\usersphoto\\usersphoto\\2.jpg', 'hhh', 'hhh', 'hh', 0),
(3, 'user02', 'xec', 'h@gmail.com', 'http://localhost/toys/userphotos/http://localhost/toys/userphotos/2.jpg', '00000000', 'hhh', '1920', 0),
(7, 'hhh', 'hhh', 'hhhh', 'http://localhost/toys/userphotos/scrum-agile.png', 'hhhh', 'hhh', 'hhh', 0);

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
  `phone` varchar(50) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `vendors`
--

INSERT INTO `vendors` (`Id`, `Name`, `Email`, `Address`, `FacebookUrl`, `phone`) VALUES
(1, '3mirat', 'h4Mdy1234@gmail.com', 'Hammametj', 'https://developers.facebook.com/apps/309315746974708/settings/advanced/', '97564771'),
(2, 'Hamdi', 'bourouniahamdi7@gmail.com', 'Nabeul', 'www.fb.com', '55419494=');

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
