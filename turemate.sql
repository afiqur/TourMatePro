-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: May 17, 2017 at 04:47 PM
-- Server version: 10.1.13-MariaDB
-- PHP Version: 5.5.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `turemate`
--

-- --------------------------------------------------------

--
-- Table structure for table `event`
--

CREATE TABLE `event` (
  `eventid` int(11) NOT NULL,
  `userid` int(11) NOT NULL,
  `eventname` varchar(100) NOT NULL,
  `startdate` varchar(20) NOT NULL,
  `enddate` varchar(25) NOT NULL,
  `budget` varchar(25) NOT NULL,
  `econtact` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `event`
--

INSERT INTO `event` (`eventid`, `userid`, `eventname`, `startdate`, `enddate`, `budget`, `econtact`) VALUES
(1, 3, 'DIU', '2015', '2019', '633000', '016*******'),
(3, 3, 'F', '13/4/2017', '14/4/2017', '50', '9999'),
(4, 5, 'AA', '17/4/2017', '27/4/2017', '565', '84545454'),
(5, 3, 'BB', '13/4/2017', '27/4/2017', '555', '554645'),
(6, 6, 'CC', '17/4/2017', '18/4/2017', '500', '555'),
(7, 3, 'DD', '17/4/2017', '18/4/2017', '4', '22');

-- --------------------------------------------------------

--
-- Table structure for table `expense`
--

CREATE TABLE `expense` (
  `expense_id` int(11) NOT NULL,
  `user_id` varchar(250) DEFAULT NULL,
  `event_id` varchar(250) DEFAULT NULL,
  `expense_title` varchar(250) DEFAULT NULL,
  `expense_cost` varchar(250) DEFAULT NULL,
  `saved_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `expense`
--

INSERT INTO `expense` (`expense_id`, `user_id`, `event_id`, `expense_title`, `expense_cost`, `saved_time`) VALUES
(2, '3', '5', 'tt', '22', '2017-05-15 07:09:10'),
(3, '3', '1', 'gh', '22', '2017-05-17 10:25:49'),
(4, '3', '1', 'aa', '20', '2017-05-17 14:40:04'),
(5, '3', '3', 'aa', '1', '2017-05-17 14:41:38'),
(6, '3', '7', 'aw', '2', '2017-05-17 14:43:03');

-- --------------------------------------------------------

--
-- Table structure for table `photo`
--

CREATE TABLE `photo` (
  `photo_id` int(11) NOT NULL,
  `user_id` varchar(250) DEFAULT NULL,
  `event_id` varchar(250) DEFAULT NULL,
  `photo_name` varchar(250) DEFAULT NULL,
  `photo_caption` varchar(250) DEFAULT NULL,
  `saved_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `userid` int(11) NOT NULL,
  `name` varchar(250) DEFAULT NULL,
  `username` varchar(250) DEFAULT NULL,
  `password` varchar(250) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`userid`, `name`, `username`, `password`) VALUES
(3, 'a', 'a', 'a'),
(4, 'isty', 'isty', 'isty'),
(5, 'arp', 'arp', 'arp'),
(6, 'arp', 'arp', 'arp');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `event`
--
ALTER TABLE `event`
  ADD PRIMARY KEY (`eventid`);

--
-- Indexes for table `expense`
--
ALTER TABLE `expense`
  ADD PRIMARY KEY (`expense_id`);

--
-- Indexes for table `photo`
--
ALTER TABLE `photo`
  ADD PRIMARY KEY (`photo_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`userid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `event`
--
ALTER TABLE `event`
  MODIFY `eventid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `expense`
--
ALTER TABLE `expense`
  MODIFY `expense_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
--
-- AUTO_INCREMENT for table `photo`
--
ALTER TABLE `photo`
  MODIFY `photo_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `userid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
