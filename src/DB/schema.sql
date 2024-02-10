-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Jan 10, 2024 at 01:11 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `web-scraper`
--

-- --------------------------------------------------------

--
-- Table structure for table `Company`
--

CREATE TABLE `Company` (
  `id_company` int(11) NOT NULL,
  `id_offre` int(11) DEFAULT NULL,
  `CompanyAdresse` varchar(255) DEFAULT NULL,
  `CompanyWebsite` varchar(255) DEFAULT NULL,
  `CompanyName` varchar(255) DEFAULT NULL,
  `CompanyDescription` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Langue`
--

CREATE TABLE `Langue` (
  `id_langue` int(11) NOT NULL,
  `id_offre` int(11) DEFAULT NULL,
  `langueName` varchar(255) DEFAULT NULL,
  `level` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `Offre`
--

CREATE TABLE `Offre` (
  `id_offre` int(11) NOT NULL,
  `Title` varchar(255) DEFAULT NULL,
  `OffreLink` varchar(255) DEFAULT NULL,
  `SiteName` varchar(255) DEFAULT NULL,
  `PublicationDate` varchar(255) DEFAULT NULL,
  `ApplyDate` varchar(255) DEFAULT NULL,
  `OffreDescription` text DEFAULT NULL,
  `Region` varchar(255) DEFAULT NULL,
  `City` varchar(255) DEFAULT NULL,
  `Sectors` varchar(255) DEFAULT NULL,
  `Occupation` varchar(255) DEFAULT NULL,
  `ContractType` varchar(255) DEFAULT NULL,
  `EducationLevel` varchar(255) DEFAULT NULL,
  `Diploma` varchar(255) DEFAULT NULL,
  `ExperienceLevel` varchar(255) DEFAULT NULL,
  `SearchedProfile` text DEFAULT NULL,
  `PersonalityTraits` varchar(255) DEFAULT NULL,
  `RecommandedCompetence` varchar(255) DEFAULT NULL,
  `Salary` varchar(255) DEFAULT NULL,
  `SocialAdvantages` varchar(255) DEFAULT NULL,
  `RemoteWork` varchar(255) DEFAULT NULL,
  `NumberOfPosts` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `skill`
--

CREATE TABLE `skill` (
  `id_skill` int(11) NOT NULL,
  `id_offre` int(11) DEFAULT NULL,
  `skillName` varchar(255) DEFAULT NULL,
  `skillType` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `email` varchar(30) NOT NULL,
  `password` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id_user`, `email`, `password`) VALUES
(41, 'mouad.saif@uit.ac.ma', 'MouadSaif6000');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Company`
--
ALTER TABLE `Company`
  ADD PRIMARY KEY (`id_company`),
  ADD KEY `id_offre` (`id_offre`);

--
-- Indexes for table `Langue`
--
ALTER TABLE `Langue`
  ADD PRIMARY KEY (`id_langue`),
  ADD KEY `id_offre` (`id_offre`);

--
-- Indexes for table `Offre`
--
ALTER TABLE `Offre`
  ADD PRIMARY KEY (`id_offre`);

--
-- Indexes for table `skill`
--
ALTER TABLE `skill`
  ADD PRIMARY KEY (`id_skill`),
  ADD KEY `id_offre` (`id_offre`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Company`
--
ALTER TABLE `Company`
  MODIFY `id_company` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Langue`
--
ALTER TABLE `Langue`
  MODIFY `id_langue` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `Offre`
--
ALTER TABLE `Offre`
  MODIFY `id_offre` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=51;

--
-- AUTO_INCREMENT for table `skill`
--
ALTER TABLE `skill`
  MODIFY `id_skill` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `Company`
--
ALTER TABLE `Company`
  ADD CONSTRAINT `Company_ibfk_1` FOREIGN KEY (`id_offre`) REFERENCES `Offre` (`id_offre`);

--
-- Constraints for table `Langue`
--
ALTER TABLE `Langue`
  ADD CONSTRAINT `Langue_ibfk_1` FOREIGN KEY (`id_offre`) REFERENCES `Offre` (`id_offre`);

--
-- Constraints for table `skill`
--
ALTER TABLE `skill`
  ADD CONSTRAINT `skill_ibfk_1` FOREIGN KEY (`id_offre`) REFERENCES `Offre` (`id_offre`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
