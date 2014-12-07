-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Dim 07 Décembre 2014 à 21:00
-- Version du serveur: 5.6.12-log
-- Version de PHP: 5.4.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `bibliotheque`
--
CREATE DATABASE IF NOT EXISTS `bibliotheque` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `bibliotheque`;

-- --------------------------------------------------------

--
-- Structure de la table `emprunt`
--

CREATE TABLE IF NOT EXISTS `emprunt` (
  `id_usager` int(10) NOT NULL,
  `id_livre` int(10) NOT NULL,
  KEY `fk_usager_id_usager` (`id_usager`),
  KEY `fk_id_livre` (`id_livre`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `livre`
--

CREATE TABLE IF NOT EXISTS `livre` (
  `id_livre` int(11) NOT NULL AUTO_INCREMENT,
  `ISBN` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `titre_livre` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `auteur_livre` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `editeur_livre` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `annee_livre` int(4) NOT NULL,
  `description_livre` text COLLATE utf8_unicode_ci,
  `image_livre` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `id_emprunteur` int(10) DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL,
  PRIMARY KEY (`id_livre`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=4 ;

--
-- Contenu de la table `livre`
--

INSERT INTO `livre` (`id_livre`, `ISBN`, `titre_livre`, `auteur_livre`, `editeur_livre`, `annee_livre`, `description_livre`, `image_livre`, `id_emprunteur`, `deleted`) VALUES
(1, '9782857045465', 'Le Trône de fer', 'George R. R. Martin', 'Bantam Books', 1998, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997.', NULL, NULL, 0),
(3, '9782857045694', 'Le Donjon rouge', 'George R. R. Martin', 'Bantam Books', 1999, 'Comment Lord Eddard Stark, seigneur de Winterfell, Main du Roi, gravement blessé par traîtrise, et par la même plus que jamais à la merci de la perfide reine Cersei ou des imprévisibles caprices du despotique roi Robert, aurait-il une chance d’échapper à la nasse tissée dans l’ombre pour l’abattre ? Comment, armé de sa seule et inébranlable loyauté, cerné de toutes parts par d’abominables intrigues, pourrait-il à la fois survivre, sauvegarder les siens et assurer la pérennité du royaume ?', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Structure de la table `usager`
--

CREATE TABLE IF NOT EXISTS `usager` (
  `id_usager` int(10) NOT NULL AUTO_INCREMENT,
  `nom_usager` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `prenom_usager` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id_usager`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Contenu de la table `usager`
--

INSERT INTO `usager` (`id_usager`, `nom_usager`, `prenom_usager`) VALUES
(1, 'BODIN', 'Joris');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int(10) NOT NULL AUTO_INCREMENT,
  `email_user` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `login_user` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `pass_login_user` text COLLATE utf8_unicode_ci NOT NULL,
  `type_user` tinyint(1) NOT NULL,
  `id_usager` int(10) DEFAULT NULL,
  PRIMARY KEY (`id_user`),
  KEY `fk_user_id_usager` (`id_usager`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=2 ;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id_user`, `email_user`, `login_user`, `pass_login_user`, `type_user`, `id_usager`) VALUES
(1, 'bodin.joris@gmail.com', 'bodinjo', '92dcd49d91c0ddf1c77443039371aad3', 1, 1);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `emprunt`
--
ALTER TABLE `emprunt`
  ADD CONSTRAINT `fk_id_livre` FOREIGN KEY (`id_livre`) REFERENCES `livre` (`id_livre`),
  ADD CONSTRAINT `fk_usager_id_usager` FOREIGN KEY (`id_usager`) REFERENCES `usager` (`id_usager`);

--
-- Contraintes pour la table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_id_usager` FOREIGN KEY (`id_usager`) REFERENCES `usager` (`id_usager`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
