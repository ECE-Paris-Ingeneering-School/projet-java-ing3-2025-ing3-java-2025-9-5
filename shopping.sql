-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : mer. 23 avr. 2025 à 21:07
-- Version du serveur : 8.3.0
-- Version de PHP : 8.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `shopping`
--

-- --------------------------------------------------------

--
-- Structure de la table `activitylogs`
--

DROP TABLE IF EXISTS `activitylogs`;
CREATE TABLE IF NOT EXISTS `activitylogs` (
  `log_id` int NOT NULL AUTO_INCREMENT,
  `admin_id` int DEFAULT NULL,
  `action` varchar(255) DEFAULT NULL,
  `log_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`log_id`),
  KEY `admin_id` (`admin_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `discounts`
--

DROP TABLE IF EXISTS `discounts`;
CREATE TABLE IF NOT EXISTS `discounts` (
  `discount_id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `bulk_quantity` int DEFAULT NULL,
  `bulk_price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`discount_id`),
  KEY `product_id` (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `discounts`
--

INSERT INTO `discounts` (`discount_id`, `product_id`, `bulk_quantity`, `bulk_price`) VALUES
(1, 3, 3, 15.99),
(10, 13, 3, 18.00);

-- --------------------------------------------------------

--
-- Structure de la table `orderdetails`
--

DROP TABLE IF EXISTS `orderdetails`;
CREATE TABLE IF NOT EXISTS `orderdetails` (
  `order_detail_id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `price_per_unit` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`order_detail_id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `orderdetails`
--

INSERT INTO `orderdetails` (`order_detail_id`, `order_id`, `product_id`, `quantity`, `price_per_unit`) VALUES
(16, 14, 3, 1, 19.99),
(8, 6, 4, 1, 29.99),
(21, 17, 3, 1, 19.99),
(14, 12, 3, 2, 19.99),
(13, 12, 4, 3, 29.99),
(12, 11, 4, 4, 29.99),
(17, 14, 4, 1, 29.99),
(18, 14, 9, 1, 121.00),
(19, 15, 9, 1, 121.00),
(20, 16, 4, 1, 29.99),
(22, 18, 4, 1, 29.99),
(23, 19, 3, 3, 19.99),
(24, 20, 3, 3, 19.99),
(25, 21, 3, 3, 19.99),
(26, 22, 11, 6, 2423.00),
(27, 22, 3, 5, 19.99),
(28, 23, 12, 10, 350.00),
(29, 24, 4, 2, 29.99),
(30, 24, 3, 3, 19.99),
(31, 24, 12, 11, 350.00),
(32, 25, 4, 2, 29.99),
(33, 25, 12, 4, 350.00);

-- --------------------------------------------------------

--
-- Structure de la table `orders`
--

DROP TABLE IF EXISTS `orders`;
CREATE TABLE IF NOT EXISTS `orders` (
  `order_id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `order_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `total_amount` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`order_id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `orders`
--

INSERT INTO `orders` (`order_id`, `user_id`, `order_date`, `total_amount`) VALUES
(25, 1, '2025-04-23 20:57:18', 1459.98),
(17, 3, '2025-04-21 08:36:01', 19.99),
(18, 5, '2025-04-21 09:01:07', 29.99),
(19, 1, '2025-04-22 08:50:38', 59.97),
(20, 1, '2025-04-22 08:54:11', 59.97),
(21, 1, '2025-04-22 09:01:48', 59.97),
(22, 1, '2025-04-22 09:03:31', 14637.95),
(23, 1, '2025-04-23 17:54:32', 3500.00),
(24, 6, '2025-04-23 19:55:36', 3969.95);

-- --------------------------------------------------------

--
-- Structure de la table `products`
--

DROP TABLE IF EXISTS `products`;
CREATE TABLE IF NOT EXISTS `products` (
  `product_id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `brand` varchar(50) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `bulk_price` decimal(10,2) DEFAULT NULL,
  `bulk_quantity` int DEFAULT NULL,
  `stock` int NOT NULL,
  `image_path` varchar(255) DEFAULT NULL,
  `description` text,
  PRIMARY KEY (`product_id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `products`
--

INSERT INTO `products` (`product_id`, `name`, `brand`, `price`, `bulk_price`, `bulk_quantity`, `stock`, `image_path`, `description`) VALUES
(3, 'T-shirt bleu', 'Lacoste', 19.99, NULL, NULL, 100, 'C:\\Users\\tilho\\Documents\\ECE\\Java\\Projet\\tshirt.jpg', 'Un très bon produit pour usage quotidien.'),
(4, 'Pantalon jean', 'Levis', 29.99, NULL, NULL, 50, 'C:\\Users\\tilho\\Documents\\ECE\\Java\\Projet\\pantalon.jpg', 'Produit premium avec finitions de qualité.'),
(12, 'Chaussure', 'Dior', 350.00, NULL, NULL, 100, 'dior.jpg', 'Belle paire de shoes bien luxe');

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `user_type` enum('client','admin') NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `users`
--

INSERT INTO `users` (`user_id`, `email`, `password`, `first_name`, `last_name`, `user_type`) VALUES
(1, 'ab@gmail.com', 'azerty', 'Al', 'Bert', 'client'),
(2, 'admin@ece.fr', 'admin', 'admin', 'admin', 'admin'),
(3, 'a@fr.fr', 'aaaa', 'Loïc', 'Cass', 'client'),
(7, 'daidhz@flm.fr', 'dalknd', 'azoieuazo', 'ndozneflz', 'client');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
