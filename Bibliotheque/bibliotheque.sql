-- phpMyAdmin SQL Dump
-- version 4.0.4
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Lun 15 Décembre 2014 à 20:46
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
  `id_usager_emprunt` int(10) NOT NULL,
  `id_livre_emprunt` int(10) NOT NULL,
  KEY `fk_usager_id_usager` (`id_usager_emprunt`),
  KEY `fk_id_livre` (`id_livre_emprunt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Contenu de la table `emprunt`
--

INSERT INTO `emprunt` (`id_usager_emprunt`, `id_livre_emprunt`) VALUES
(1, 1),
(1, 3),
(4, 4),
(1, 18),
(7, 7);

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=21 ;

--
-- Contenu de la table `livre`
--

INSERT INTO `livre` (`id_livre`, `ISBN`, `titre_livre`, `auteur_livre`, `editeur_livre`, `annee_livre`, `description_livre`, `image_livre`, `id_emprunteur`, `deleted`) VALUES
(1, '9782857045465', 'Le Trône de fer', 'George R. R. Martin', 'Pygmalion', 1998, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997.', NULL, 1, 0),
(3, '9782857045694', 'Le Donjon rouge', 'George R. R. Martin', 'Pygmalion', 1999, 'Comment Lord Eddard Stark, seigneur de Winterfell, Main du Roi, gravement blessé par traîtrise, et par la même plus que jamais à la merci de la perfide reine Cersei ou des imprévisibles caprices du despotique roi Robert, aurait-il une chance d’échapper à la nasse tissée dans l’ombre pour l’abattre ? Comment, armé de sa seule et inébranlable loyauté, cerné de toutes parts par d’abominables intrigues, pourrait-il à la fois survivre, sauvegarder les siens et assurer la pérennité du royaume ?', NULL, 1, 0),
(4, '9781934293065', 'La Bataille des rois', 'George R. R. Martin', 'Pygmalion', 2000, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997.', NULL, 4, 0),
(5, '9781934293065', 'L’Ombre maléfique', 'George R. R. Martin', 'Pygmalion', 2000, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997.', NULL, NULL, 0),
(6, '9781934293065', 'L’Invincible Forteresse', 'George R. R. Martin', 'Pygmalion', 2000, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997.', NULL, NULL, 0),
(7, '9781934293065', 'Intrigues à Port-Réal', 'George R. R. Martin', 'Pygmalion', 2001, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997.', NULL, 7, 0),
(8, '9781934293065', 'L’Épée de feu', 'George R. R. Martin', 'Pygmalion', 2002, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997.', NULL, NULL, 0),
(9, '9781934293065', 'Les Noces pourpres', 'George R. R. Martin', 'Pygmalion', 2002, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997.', NULL, NULL, 0),
(10, '9781934293065', 'La Loi du régicide', 'George R. R. Martin', 'Pygmalion', 2003, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997.', NULL, NULL, 0),
(11, '9781934293065', 'Le Chaos', 'George R. R. Martin', 'Pygmalion', 2006, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997.', NULL, NULL, 0),
(12, '9781934293065', 'Les Sables de Dorne', 'George R. R. Martin', 'Pygmalion', 2006, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997.', NULL, NULL, 0),
(13, '9781934293065', 'Un festin pour les corbeaux', 'George R. R. Martin', 'Pygmalion', 2007, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997.', NULL, NULL, 0),
(14, '9781934293065', 'Le Bûcher d''un roi', 'George R. R. Martin', 'Pygmalion', 2012, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997.', NULL, NULL, 0),
(15, '9781934293065', 'Les Dragons de Meereen', 'George R. R. Martin', 'Pygmalion', 2012, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997.', NULL, NULL, 0),
(16, '9781934293065', 'Une danse avec les dragons', 'George R. R. Martin', 'Pygmalion', 2013, 'A Game of Thrones (littéralement « un jeu de trônes ») est le premier livre de la saga Le Trône de fer écrite par George R. R. Martin. Le livre a été publié en version originale en 1996, puis en version française de 1998 à 1999. Il a remporté le prix Locus du meilleur roman de fantasy en 1997', NULL, NULL, 0),
(17, '0345451325', 'La réponse est 42', 'Troll', 'Panda', 1942, 'C''est un livre qui parle 42 et 1337. Dans la joie et la bonne humeur.', NULL, NULL, 0),
(18, '9782756009124', 'walking dead 1. Passé dé composé', 'Robert Kirkman', 'Contrebande', 2007, 'Le monde tel que nous le connaissions n''existe plus !!!', NULL, 1, 0),
(19, '9782756009728', 'Walking dead 2', 'Robert KIRKMAN', 'CONTREBANDE', 2007, 'C''est un comics trop cool\nComme ce retour à la ligne', NULL, NULL, 0),
(20, '9782756012834', 'Walking dead 3 sain et saufs', 'Robert kirkman', 'Contrebande', 2007, 'Encore une description\nLa série est cool aussi', NULL, NULL, 0);

-- --------------------------------------------------------

--
-- Structure de la table `usager`
--

CREATE TABLE IF NOT EXISTS `usager` (
  `id_usager` int(10) NOT NULL AUTO_INCREMENT,
  `nom_usager` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `prenom_usager` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`id_usager`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=10 ;

--
-- Contenu de la table `usager`
--

INSERT INTO `usager` (`id_usager`, `nom_usager`, `prenom_usager`) VALUES
(1, 'BODIN', 'Joris'),
(2, 'admin', 'admin'),
(4, 'Freeman', 'Gordon '),
(5, 'kolli', 'jessica'),
(6, 'astruc', 'arnaud'),
(7, 'Dewinter', 'Tim'),
(8, 'Coucou', 'test'),
(9, 'Test', 'Test');

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
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=12 ;

--
-- Contenu de la table `user`
--

INSERT INTO `user` (`id_user`, `email_user`, `login_user`, `pass_login_user`, `type_user`, `id_usager`) VALUES
(1, 'bodin.joris@gmail.com', 'bodinjo', '63a9f0ea7bb98050796b649e85481845', 1, 1),
(2, 'admin@admin.com', 'admin', '21232f297a57a5a743894a0e4a801fc3', 0, 2),
(6, 'test@test.com', 'test', '098f6bcd4621d373cade4e832627b4f6', 1, 4),
(7, 'jkolli@hotmail.fr', 'sharo', '24dd63ef055457c2f11e6141a814e043', 1, 5),
(8, 'oxynux@gmail.com', 'oxynux', '366faf67dbd18b36bd0fb009112b4b48', 1, 6),
(9, 'tim@tim.com', 'tim', 'f71dbe52628a3f83a77ab494817525c6', 1, 7),
(10, 'toto@gmail.com', 'toto', '5d933eef19aee7da192608de61b6c23d', 1, 8),
(11, 'ff@fg.com', 'toto', 'f71dbe52628a3f83a77ab494817525c6', 1, 9);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `emprunt`
--
ALTER TABLE `emprunt`
  ADD CONSTRAINT `fk_id_livre` FOREIGN KEY (`id_livre_emprunt`) REFERENCES `livre` (`id_livre`),
  ADD CONSTRAINT `fk_usager_id_usager` FOREIGN KEY (`id_usager_emprunt`) REFERENCES `usager` (`id_usager`);

--
-- Contraintes pour la table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `fk_user_id_usager` FOREIGN KEY (`id_usager`) REFERENCES `usager` (`id_usager`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
