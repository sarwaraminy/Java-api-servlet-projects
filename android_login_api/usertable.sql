-- phpMyAdmin SQL Dump
-- version 2.11.6
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jan 15, 2014 at 08:13 AM
-- Server version: 5.0.51
-- PHP Version: 5.2.6

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `android_api`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--
CREATE DATABASE android_api;

USE android_api;

CREATE TABLE `users` (
  `uid` int(11) NOT NULL auto_increment,
  `unique_id` varchar(23) NOT NULL,
  `name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `encrypted_password` varchar(80) NOT NULL,
  `salt` varchar(10) NOT NULL,
  `created_at` datetime default NULL,
  `updated_at` datetime default NULL,
  PRIMARY KEY  (`uid`),
  UNIQUE KEY `unique_id` (`unique_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uid`, `unique_id`, `name`, `email`, `encrypted_password`, `salt`, `created_at`, `updated_at`) VALUES
(1, '529acebc448ae3.48250802', 'Mohammad Sarwar', 'samini@firstrate.com', 'ZKsNp2v6f80yk5kJKFkrbhNRgWE0ZGVhODY5NTBk', '4dea86950d', '2013-12-01 10:23:00', NULL),
(2, '52c8f67ba3c2f0.65525516', 'Farhad', 'faryan@firstrate.com', 'quz4t5btmGCKW4gAR5aLomVTRrlmZWFlZjllNThh', 'feaef9e58a', '2014-01-05 10:36:51', NULL);
