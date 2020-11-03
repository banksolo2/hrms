-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 03, 2020 at 03:23 AM
-- Server version: 10.4.6-MariaDB
-- PHP Version: 7.3.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hrms`
--

-- --------------------------------------------------------

--
-- Table structure for table `branches`
--

CREATE TABLE `branches` (
  `branch_id` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `branches`
--

INSERT INTO `branches` (`branch_id`, `name`, `created_at`, `updated_at`, `created_by`, `updated_by`) VALUES
(2, 'Head Office', '2020-07-16 01:14:25', '2020-07-22 14:00:56', NULL, 0),
(4, 'Ikeja Office', '2020-07-22 13:47:57', '2020-07-22 13:47:57', NULL, 0),
(5, 'Abuja Office', '2020-07-26 02:59:49', '2020-08-13 22:44:14', NULL, 2);

-- --------------------------------------------------------

--
-- Table structure for table `companies`
--

CREATE TABLE `companies` (
  `company_Id` int(11) NOT NULL,
  `name` varchar(500) NOT NULL,
  `code_name` varchar(50) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `companies`
--

INSERT INTO `companies` (`company_Id`, `name`, `code_name`, `created_at`, `updated_at`, `created_by`, `updated_by`) VALUES
(2, 'Assurance Life', 'AL', '2020-07-16 16:07:38', '2020-07-30 13:05:38', NULL, 2),
(3, 'Life Group', 'LG', '2020-07-22 14:48:00', '2020-07-26 03:41:54', NULL, NULL),
(4, 'General Life', 'GL', '2020-07-23 14:34:20', '2020-07-23 19:20:25', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `departments`
--

CREATE TABLE `departments` (
  `department_id` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `code` varchar(300) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `departments`
--

INSERT INTO `departments` (`department_id`, `name`, `code`, `created_at`, `updated_at`, `created_by`, `updated_by`) VALUES
(3, 'Information Technology', 'information_technology', '2020-07-15 16:01:55', '2020-08-08 19:11:31', 2, 2),
(6, 'Accounting', 'accounting', '2020-08-08 23:42:52', '2020-08-08 23:42:52', 2, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `department_heads`
--

CREATE TABLE `department_heads` (
  `department_head_id` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `created_by` int(11) NOT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `department_heads`
--

INSERT INTO `department_heads` (`department_head_id`, `employee_id`, `department_id`, `created_at`, `created_by`, `updated_at`, `updated_by`) VALUES
(3, 2, 3, '2020-08-21 13:24:46', 2, '2020-08-21 13:24:46', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `employee_id` int(11) NOT NULL,
  `first_name` varchar(250) NOT NULL,
  `middle_name` varchar(250) DEFAULT NULL,
  `last_name` varchar(250) NOT NULL,
  `date_of_employment` date NOT NULL,
  `email` varchar(500) NOT NULL,
  `employee_status_id` int(11) DEFAULT NULL,
  `state_id` int(11) DEFAULT NULL,
  `mobile_number` varchar(100) NOT NULL,
  `department_id` int(11) DEFAULT NULL,
  `level_id` int(11) DEFAULT NULL,
  `staff_id` varchar(50) DEFAULT NULL,
  `branch_id` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `title` varchar(100) NOT NULL,
  `date_of_birth` date NOT NULL,
  `name_initials` varchar(50) NOT NULL,
  `gender_id` int(11) DEFAULT NULL,
  `martial_status_id` int(11) DEFAULT NULL,
  `current_address` text DEFAULT NULL,
  `personal_email` varchar(200) DEFAULT NULL,
  `password` varchar(500) NOT NULL,
  `leave_supervisor_id` int(11) DEFAULT NULL,
  `personal_production_target` double DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `last_login_at` timestamp NULL DEFAULT NULL ON UPDATE current_timestamp(),
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `image_url` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`employee_id`, `first_name`, `middle_name`, `last_name`, `date_of_employment`, `email`, `employee_status_id`, `state_id`, `mobile_number`, `department_id`, `level_id`, `staff_id`, `branch_id`, `company_id`, `title`, `date_of_birth`, `name_initials`, `gender_id`, `martial_status_id`, `current_address`, `personal_email`, `password`, `leave_supervisor_id`, `personal_production_target`, `created_at`, `updated_at`, `last_login_at`, `created_by`, `updated_by`, `image_url`) VALUES
(2, 'Oluwaseun', 'Joseph', 'Olotu', '2018-03-14', 'seunolo2@gmail.com', 2, 2, '(234) 808-0643-360', 3, 1, '0007', 2, 2, 'Mr', '1985-03-21', 'SJO', 1, 1, 'Iba Housing Estate', 'seunolo2@gmail.com', '4a290d0dab6aca812b26312ef2277dea', 0, 0, '2020-07-09 11:12:45', '2020-10-14 13:21:27', '2020-10-14 13:21:27', NULL, 0, NULL),
(4, 'Joseph', 'Banks', 'Olotu', '2010-03-21', 'josepholo2@yahoo.ca', 2, 4, '(234) 808-0643-360', 3, 1, '2345', 2, 3, 'Mr', '1985-03-21', 'JBO', 1, 1, 'Block 103, Flat 3, Zone A, 1st car park\r\nIba Housing Estate', 'josepholo2@yahoo.ca', '2c6a318823de58496bc99edc52881984', 2, 0, '2020-07-22 21:55:17', '2020-10-18 12:51:42', '2020-10-18 12:51:42', NULL, 2, NULL),
(5, 'Chisom', 'Charis', 'Akunaka', '2020-01-05', 'chisomakunaka@bankstech.com', 2, 6, '(234) 706-5434-814', 6, 1, '2222', 5, 2, 'Miss', '2000-05-12', 'CCA', 2, 1, 'Block 502, Flat 6, Zone E, Iba Housing Estate, Ojo, Lagos', 'charisAkunaka@gmail.com', '2c6a318823de58496bc99edc52881984', 2, 200000, '2020-07-31 13:35:54', '2020-10-11 13:56:04', '2020-10-11 13:56:04', 2, 2, NULL),
(6, 'Bunmi', 'Mary', 'Olotu', '2016-10-02', 'bunmio@bankstech.com', 2, 7, '(234) 808-0643-360', 3, 1, '5678', 2, 4, 'Mrs', '1989-10-16', 'BMO', 2, 2, 'Block 103 Iba', 'bunmiolo2@gmail.com', '117b0abd13b7eccc8fa9ed546b322a05', 4, 20000, '2020-07-31 15:30:30', '2020-08-02 14:11:14', '2020-08-02 14:11:14', 2, 2, NULL),
(7, 'Omogbolahan', 'John', 'Adeeko', '2010-05-11', 'Femex2006wes@gmail.com', 2, 4, '(234) 823-4444-444', 3, 1, '34556', 2, 2, 'Mr', '1986-04-22', 'OJA', 1, 2, 'Yaba', 'Adeeko@gmail.com', '2c6a318823de58496bc99edc52881984', 2, 500000, '2020-09-28 12:52:31', '2020-10-19 12:47:07', '2020-10-19 12:47:07', 2, 2, NULL),
(8, 'Femi', 'James', 'Ajayi', '2019-04-22', 'femiA@custodian.com', 2, 4, '(234) 555-666-6666', 3, 1, '55666', 2, 4, 'Mr', '1973-05-23', 'FJA', 1, 2, 'Yaba', 'femiA@gmail.com', '117b0abd13b7eccc8fa9ed546b322a05', 2, 750000000, '2020-09-28 12:56:30', '2020-09-28 12:56:30', NULL, 2, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `employee_roles`
--

CREATE TABLE `employee_roles` (
  `employee_role_id` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee_roles`
--

INSERT INTO `employee_roles` (`employee_role_id`, `employee_id`, `role_id`, `created_at`, `updated_at`, `created_by`, `updated_by`) VALUES
(2, 2, 2, '2020-07-12 13:34:49', '2020-07-23 00:13:12', NULL, NULL),
(6, 5, 4, '2020-08-02 23:53:58', '2020-08-03 10:40:43', 2, 2),
(8, 8, 4, '2020-09-28 12:57:10', '2020-09-28 12:57:10', 2, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `employee_status`
--

CREATE TABLE `employee_status` (
  `employee_status_id` int(11) NOT NULL,
  `name` varchar(300) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee_status`
--

INSERT INTO `employee_status` (`employee_status_id`, `name`, `created_at`, `updated_at`, `created_by`, `updated_by`) VALUES
(2, 'Active', '2020-07-14 16:39:47', '2020-07-14 16:39:47', NULL, NULL),
(3, 'Retried ', '2020-07-14 16:39:47', '2020-07-14 16:39:47', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `genders`
--

CREATE TABLE `genders` (
  `gender_id` int(11) NOT NULL,
  `name` varchar(200) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `genders`
--

INSERT INTO `genders` (`gender_id`, `name`, `created_at`, `updated_at`, `created_by`, `updated_by`) VALUES
(1, 'Male', '2020-07-17 04:17:15', '2020-07-17 04:17:15', NULL, NULL),
(2, 'Female', '2020-07-17 04:17:54', '2020-07-17 04:17:54', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `holidays`
--

CREATE TABLE `holidays` (
  `holiday_id` int(11) NOT NULL,
  `name` varchar(300) NOT NULL,
  `description` text NOT NULL,
  `date_at` date NOT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `holidays`
--

INSERT INTO `holidays` (`holiday_id`, `name`, `description`, `date_at`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES
(1, 'Christmas', 'Just a holiday', '2020-12-25', 2, '2020-08-04 00:57:31', 2, '2020-08-04 13:44:56'),
(5, 'Boxing Day', 'Boxing day', '2020-12-26', 2, '2020-08-21 05:47:52', NULL, '2020-08-21 05:47:52');

-- --------------------------------------------------------

--
-- Table structure for table `issue_types`
--

CREATE TABLE `issue_types` (
  `issue_type_id` int(11) NOT NULL,
  `name` varchar(500) NOT NULL,
  `code` varchar(500) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `issue_types`
--

INSERT INTO `issue_types` (`issue_type_id`, `name`, `code`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES
(5, 'Red Zone Error', 'red_zone_error', 2, '2020-10-04 18:27:56', 2, '2020-10-05 03:15:41');

-- --------------------------------------------------------

--
-- Table structure for table `leaves`
--

CREATE TABLE `leaves` (
  `leave_id` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `supervisor_id` int(11) NOT NULL,
  `leave_type_id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `resumption_date` date DEFAULT NULL,
  `no_of_days` int(11) NOT NULL,
  `primary_relief_office_id` int(11) NOT NULL,
  `secondary_relief_office_id` int(11) DEFAULT NULL,
  `staff_to_notify` text DEFAULT NULL,
  `comment` text DEFAULT NULL,
  `inline_with_leave_plan` varchar(200) NOT NULL DEFAULT 'no',
  `with_pay` varchar(100) NOT NULL,
  `leave_status_id` int(11) NOT NULL,
  `file_url` varchar(600) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `leaves`
--

INSERT INTO `leaves` (`leave_id`, `employee_id`, `department_id`, `supervisor_id`, `leave_type_id`, `start_date`, `end_date`, `resumption_date`, `no_of_days`, `primary_relief_office_id`, `secondary_relief_office_id`, `staff_to_notify`, `comment`, `inline_with_leave_plan`, `with_pay`, `leave_status_id`, `file_url`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES
(53, 7, 3, 2, 4, '2020-11-09', '2020-11-13', '2020-11-16', 5, 4, 5, '\'2\':\'4\'', NULL, 'no', 'no', 6, NULL, 7, '2020-11-02 20:03:47', 0, '2020-11-03 02:21:53');

-- --------------------------------------------------------

--
-- Table structure for table `leave_plans`
--

CREATE TABLE `leave_plans` (
  `leave_plan_id` int(11) NOT NULL,
  `employee_id` int(11) NOT NULL,
  `department_id` int(11) NOT NULL,
  `department_head_id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `end_date` date NOT NULL,
  `no_of_days` int(11) DEFAULT NULL,
  `leave_plan_status_id` int(11) NOT NULL,
  `on_behalf` varchar(50) NOT NULL,
  `comment` text DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `leave_plans`
--

INSERT INTO `leave_plans` (`leave_plan_id`, `employee_id`, `department_id`, `department_head_id`, `start_date`, `end_date`, `no_of_days`, `leave_plan_status_id`, `on_behalf`, `comment`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES
(3, 2, 3, 2, '2020-11-02', '2020-11-18', 13, 5, 'no', 'Done', 2, '2020-09-04 23:25:08', 2, '2020-10-25 18:11:02'),
(8, 4, 3, 2, '2020-09-23', '2020-10-07', 11, 6, 'no', NULL, 4, '2020-09-21 16:34:01', NULL, '2020-09-22 03:43:13'),
(9, 2, 3, 2, '2020-11-02', '2020-11-18', 13, 5, 'no', 'Done', 2, '2020-10-25 18:07:29', 2, '2020-10-25 18:11:02');

-- --------------------------------------------------------

--
-- Table structure for table `leave_plan_statues`
--

CREATE TABLE `leave_plan_statues` (
  `leave_plan_status_id` int(11) NOT NULL,
  `name` varchar(300) NOT NULL,
  `code` varchar(300) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `leave_plan_statues`
--

INSERT INTO `leave_plan_statues` (`leave_plan_status_id`, `name`, `code`, `created_at`, `updated_at`) VALUES
(1, 'Drafted', 'drafted', '2020-08-10 23:04:30', '2020-09-02 14:39:39'),
(4, 'Send For Approval', 'send_for_approval', '2020-08-21 13:10:59', '2020-08-21 13:10:59'),
(5, 'Approved', 'approved', '2020-08-21 13:11:08', '2020-08-21 13:11:08'),
(6, 'Declined', 'declined', '2020-08-21 13:11:18', '2020-09-02 14:26:29'),
(7, 'Send Back For Correction', 'send_back_for_correction', '2020-08-21 13:11:37', '2020-08-21 13:11:37');

-- --------------------------------------------------------

--
-- Table structure for table `leave_statues`
--

CREATE TABLE `leave_statues` (
  `leave_status_id` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `code` varchar(250) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `leave_statues`
--

INSERT INTO `leave_statues` (`leave_status_id`, `name`, `code`, `created_at`, `updated_at`) VALUES
(1, 'Drafted', 'drafted', '2020-08-05 14:17:05', '2020-10-26 14:08:14'),
(5, 'Approved', 'approved', '2020-09-10 19:24:50', '2020-09-10 19:24:50'),
(6, 'Sent To Supervisor For Approval', 'sent_to_supervisor_for_approval', '2020-09-10 19:26:04', '2020-09-10 19:26:04'),
(7, 'Sent To HR For Approval', 'sent_to_hr_for_approval', '2020-09-10 19:26:38', '2020-09-10 19:26:38'),
(8, 'Sent Back For Correction', 'sent_back_for_correction', '2020-09-10 19:27:15', '2020-09-10 19:27:15'),
(9, 'Declined', 'declined', '2020-09-10 19:29:00', '2020-09-10 19:29:00'),
(10, 'Initiate Recall Back', 'initiate_recall_back', '2020-09-10 19:38:13', '2020-09-21 02:12:52'),
(11, 'Approved Recalled Back', 'approved_recalled_back', '2020-09-18 13:54:34', '2020-09-18 13:54:34'),
(12, 'Decline Leave Recall Back', 'decline_leave_recall_back', '2020-09-23 14:35:34', '2020-09-23 14:35:34');

-- --------------------------------------------------------

--
-- Table structure for table `leave_types`
--

CREATE TABLE `leave_types` (
  `leave_type_id` int(11) NOT NULL,
  `name` varchar(300) NOT NULL,
  `code` varchar(300) NOT NULL,
  `days` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `leave_types`
--

INSERT INTO `leave_types` (`leave_type_id`, `name`, `code`, `days`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES
(4, 'Annual', 'annual', 20, 2, '2020-09-08 10:13:41', NULL, '2020-09-10 19:19:32'),
(5, 'Sick', 'sick', 7, 2, '2020-09-08 10:14:13', NULL, '2020-09-08 10:14:13'),
(6, 'Examination', 'examination', 10, 2, '2020-09-08 10:14:53', NULL, '2020-09-08 11:15:46'),
(7, 'Maternity', 'maternity', 60, 2, '2020-09-08 10:18:04', NULL, '2020-09-08 10:18:04');

-- --------------------------------------------------------

--
-- Table structure for table `levels`
--

CREATE TABLE `levels` (
  `level_id` int(11) NOT NULL,
  `name` varchar(300) NOT NULL,
  `code` varchar(300) DEFAULT NULL,
  `leave_days` int(11) DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `levels`
--

INSERT INTO `levels` (`level_id`, `name`, `code`, `leave_days`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES
(1, 'Senior Manager', 'senior_manager', 22, NULL, 2, '2020-07-15 17:05:43', '2020-07-15 17:05:43');

-- --------------------------------------------------------

--
-- Table structure for table `martial_statuses`
--

CREATE TABLE `martial_statuses` (
  `martial_status_id` int(11) NOT NULL,
  `name` varchar(300) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `martial_statuses`
--

INSERT INTO `martial_statuses` (`martial_status_id`, `name`, `created_at`, `updated_at`, `created_by`, `updated_by`) VALUES
(1, 'Single', '2020-07-17 15:42:36', '2020-07-17 15:42:36', NULL, NULL),
(2, 'Married', '2020-07-17 15:44:16', '2020-07-17 15:44:16', NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `red_zones`
--

CREATE TABLE `red_zones` (
  `red_zone_id` int(11) NOT NULL,
  `description` text NOT NULL,
  `date_from` date NOT NULL,
  `date_to` date NOT NULL,
  `department_id` int(11) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `red_zones`
--

INSERT INTO `red_zones` (`red_zone_id`, `description`, `date_from`, `date_to`, `department_id`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES
(5, 'No leave in December', '2020-12-01', '2020-12-31', 6, 2, '2020-08-10 16:39:17', NULL, '2020-08-10 16:39:17'),
(6, 'No leave in December', '2020-12-01', '2020-12-31', 3, 2, '2020-08-10 16:39:17', NULL, '2020-08-10 16:39:17');

-- --------------------------------------------------------

--
-- Table structure for table `requisitions`
--

CREATE TABLE `requisitions` (
  `requisition_id` int(11) NOT NULL,
  `created_date` date NOT NULL,
  `requisition_type_id` int(11) NOT NULL,
  `subject` varchar(500) NOT NULL,
  `requester_id` int(11) NOT NULL,
  `supervisor_id` int(11) NOT NULL,
  `recipients` varchar(500) NOT NULL,
  `file_url` varchar(500) DEFAULT NULL,
  `requisition_status_id` int(11) NOT NULL,
  `comment` text DEFAULT NULL,
  `approved_by` int(11) DEFAULT NULL,
  `declined_by` int(11) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `requisitions`
--

INSERT INTO `requisitions` (`requisition_id`, `created_date`, `requisition_type_id`, `subject`, `requester_id`, `supervisor_id`, `recipients`, `file_url`, `requisition_status_id`, `comment`, `approved_by`, `declined_by`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES
(6, '2020-10-20', 3, 'Testing', 7, 2, '\'4\':\'2\':\'7\'', 'filesUpload/requisitions/mel-michael-(a.).vcf', 7, '', 4, 4, 7, 7, '2020-10-20 18:08:44', '2020-10-21 13:57:57'),
(7, '2020-10-23', 3, 'Testing', 7, 2, '\'4\':\'2\':\'7\'', 'filesUpload/requisitions/tableExport.sql', 8, 'It just can\'t work.', 0, 4, 7, 4, '2020-10-23 10:48:09', '2020-10-24 01:24:02'),
(8, '2020-10-23', 3, 'Second Test', 7, 2, '\'4\':\'2\':\'7\'', 'filesUpload/requisitions/Project proposal.pdf', 6, '', 2, 0, 7, 2, '2020-10-23 10:53:37', '2020-10-24 13:13:23'),
(9, '2020-10-23', 3, 'Third Test', 7, 2, '\'4\':\'2\':\'7\'', 'filesUpload/requisitions/User Guide - Recruitment & Payroll.pdf', 5, 'It Can\'t work', 0, 2, 7, 2, '2020-10-23 10:55:49', '2020-10-24 01:22:47'),
(10, '2020-10-23', 3, 'Forth Test with email', 7, 2, '\'4\':\'2\':\'7\'', 'filesUpload/requisitions/New Doc 2019-03-19 17.23.30.pdf', 4, 'It can\'t work for me', 0, 2, 7, 2, '2020-10-23 10:57:02', '2020-10-23 13:29:06'),
(11, '2020-10-23', 3, 'Fifth Tests', 7, 2, '\'4\':\'2\':\'7\'', 'filesUpload/requisitions/ay6.png', 4, NULL, 0, 0, 7, 7, '2020-10-23 12:02:24', '2020-10-23 12:07:37'),
(12, '2020-10-25', 3, 'Testing 1', 7, 2, '\'4\':\'2\':\'7\'', 'filesUpload/requisitions/Leaves.sql', 7, '', 2, 0, 7, 7, '2020-10-25 18:56:12', '2020-10-25 19:00:31');

-- --------------------------------------------------------

--
-- Table structure for table `requisition_statues`
--

CREATE TABLE `requisition_statues` (
  `requisition_status_id` int(11) NOT NULL,
  `name` varchar(500) NOT NULL,
  `code` varchar(500) NOT NULL,
  `created_by` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_by` int(11) DEFAULT NULL,
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `requisition_statues`
--

INSERT INTO `requisition_statues` (`requisition_status_id`, `name`, `code`, `created_by`, `created_at`, `updated_by`, `updated_at`) VALUES
(1, 'Drafted', 'drafted', 2, '2020-10-17 16:05:08', 2, '2020-10-18 00:18:43'),
(4, 'Sent To Supervisor For Authorization', 'sent_to_supervisor_for_authorization', 2, '2020-10-18 00:23:05', 2, '2020-10-19 03:05:25'),
(5, 'Sent To Recipient For Approval', 'sent_to_recipient_for_approval', 2, '2020-10-18 00:26:35', 2, '2020-10-19 03:05:53'),
(6, 'Approved', 'approved', 2, '2020-10-18 00:28:06', NULL, '2020-10-18 00:28:06'),
(7, 'Closed', 'closed', 2, '2020-10-18 00:28:13', NULL, '2020-10-18 00:28:13'),
(8, 'Declined', 'declined', 2, '2020-10-18 13:26:10', NULL, '2020-10-18 13:26:10');

-- --------------------------------------------------------

--
-- Table structure for table `requisition_types`
--

CREATE TABLE `requisition_types` (
  `requisition_type_id` int(11) NOT NULL,
  `name` varchar(400) NOT NULL,
  `code` varchar(400) NOT NULL,
  `created_by` int(11) NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `requisition_types`
--

INSERT INTO `requisition_types` (`requisition_type_id`, `name`, `code`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES
(3, 'Testing Games', 'testing_games', 2, 2, '2020-10-16 19:22:54', '2020-10-17 15:17:34');

-- --------------------------------------------------------

--
-- Table structure for table `roles`
--

CREATE TABLE `roles` (
  `role_id` int(11) NOT NULL,
  `name` varchar(300) NOT NULL,
  `code` varchar(300) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `roles`
--

INSERT INTO `roles` (`role_id`, `name`, `code`, `created_at`, `updated_at`, `created_by`, `updated_by`) VALUES
(2, 'Super Admin', 'super_admin', '2020-07-11 16:30:01', '2020-07-11 16:30:01', NULL, 4),
(3, 'Hr Admin', 'hr_admin', '2020-07-11 16:31:11', '2020-07-11 16:31:11', NULL, 4),
(4, 'Supervisor', 'supervisor', '2020-07-11 16:31:45', '2020-07-11 16:31:45', NULL, 4);

-- --------------------------------------------------------

--
-- Table structure for table `states`
--

CREATE TABLE `states` (
  `state_id` int(11) NOT NULL,
  `name` varchar(250) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `created_by` int(11) DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `states`
--

INSERT INTO `states` (`state_id`, `name`, `created_at`, `updated_at`, `created_by`, `updated_by`) VALUES
(2, 'Lagos', '2020-07-10 12:21:19', '2020-07-10 12:21:19', NULL, NULL),
(4, 'Ogun', '2020-07-19 13:07:31', '2020-07-19 15:40:27', NULL, NULL),
(6, 'Imo', '2020-07-26 02:09:13', '2020-07-26 02:09:13', NULL, NULL),
(7, 'Ondo', '2020-07-26 02:10:25', '2020-07-26 02:10:25', NULL, NULL),
(8, 'Edo', '2020-08-03 11:15:35', '2020-08-03 11:29:51', 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `support_tickets`
--

CREATE TABLE `support_tickets` (
  `support_ticket_id` int(11) NOT NULL,
  `issue_report_date` date NOT NULL,
  `issue_type_id` int(11) NOT NULL,
  `issue_description` text NOT NULL,
  `file_url` varchar(500) DEFAULT NULL,
  `issue_for` varchar(300) NOT NULL,
  `department_id` int(11) DEFAULT NULL,
  `employees` varchar(900) DEFAULT NULL,
  `support_ticket_status_id` int(11) NOT NULL,
  `comment` text DEFAULT NULL,
  `resolved_by` int(11) DEFAULT NULL,
  `created_by` int(11) NOT NULL,
  `updated_by` int(11) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `support_tickets`
--

INSERT INTO `support_tickets` (`support_ticket_id`, `issue_report_date`, `issue_type_id`, `issue_description`, `file_url`, `issue_for`, `department_id`, `employees`, `support_ticket_status_id`, `comment`, `resolved_by`, `created_by`, `updated_by`, `created_at`, `updated_at`) VALUES
(26, '2020-10-25', 5, 'Testing support ticket', 'filesUpload/supportTickets/Olotu Oluwaseun Joseph CV (7).pdf', 'employees', NULL, '\'4\':\'7\'', 3, NULL, NULL, 2, NULL, '2020-10-25 12:51:12', '2020-10-25 14:19:36'),
(27, '2020-10-25', 5, 'Testing Departments', 'filesUpload/supportTickets/with_pay.csv', 'department', 3, NULL, 3, NULL, NULL, 2, NULL, '2020-10-25 14:15:18', '2020-10-25 14:22:18'),
(29, '2020-10-25', 5, 'Testing 1', 'filesUpload/supportTickets/mel-michael-(a.).vcf', 'employees', 0, '\'2\':\'7\'', 3, 'Closed', 2, 7, 7, '2020-10-25 18:30:15', '2020-10-25 18:51:00');

-- --------------------------------------------------------

--
-- Table structure for table `support_ticket_statues`
--

CREATE TABLE `support_ticket_statues` (
  `support_ticket_status_id` int(11) NOT NULL,
  `name` varchar(500) NOT NULL,
  `code` varchar(500) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `support_ticket_statues`
--

INSERT INTO `support_ticket_statues` (`support_ticket_status_id`, `name`, `code`, `created_at`, `updated_at`) VALUES
(1, 'Pending', 'pending', '2020-10-05 15:10:42', '2020-10-05 15:55:10'),
(2, 'Resolved', 'resolved', '2020-10-05 16:37:26', '2020-10-05 16:37:26'),
(3, 'Closed', 'closed', '2020-10-05 16:38:02', '2020-10-05 16:38:02'),
(5, 'Unresolved', 'unresolved', '2020-10-10 12:39:24', '2020-10-10 12:39:24');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `branches`
--
ALTER TABLE `branches`
  ADD PRIMARY KEY (`branch_id`);

--
-- Indexes for table `companies`
--
ALTER TABLE `companies`
  ADD PRIMARY KEY (`company_Id`);

--
-- Indexes for table `departments`
--
ALTER TABLE `departments`
  ADD PRIMARY KEY (`department_id`);

--
-- Indexes for table `department_heads`
--
ALTER TABLE `department_heads`
  ADD PRIMARY KEY (`department_head_id`);

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`employee_id`);

--
-- Indexes for table `employee_roles`
--
ALTER TABLE `employee_roles`
  ADD PRIMARY KEY (`employee_role_id`);

--
-- Indexes for table `employee_status`
--
ALTER TABLE `employee_status`
  ADD PRIMARY KEY (`employee_status_id`);

--
-- Indexes for table `genders`
--
ALTER TABLE `genders`
  ADD PRIMARY KEY (`gender_id`);

--
-- Indexes for table `holidays`
--
ALTER TABLE `holidays`
  ADD PRIMARY KEY (`holiday_id`);

--
-- Indexes for table `issue_types`
--
ALTER TABLE `issue_types`
  ADD PRIMARY KEY (`issue_type_id`);

--
-- Indexes for table `leaves`
--
ALTER TABLE `leaves`
  ADD PRIMARY KEY (`leave_id`);

--
-- Indexes for table `leave_plans`
--
ALTER TABLE `leave_plans`
  ADD PRIMARY KEY (`leave_plan_id`);

--
-- Indexes for table `leave_plan_statues`
--
ALTER TABLE `leave_plan_statues`
  ADD PRIMARY KEY (`leave_plan_status_id`);

--
-- Indexes for table `leave_statues`
--
ALTER TABLE `leave_statues`
  ADD PRIMARY KEY (`leave_status_id`);

--
-- Indexes for table `leave_types`
--
ALTER TABLE `leave_types`
  ADD PRIMARY KEY (`leave_type_id`);

--
-- Indexes for table `levels`
--
ALTER TABLE `levels`
  ADD PRIMARY KEY (`level_id`);

--
-- Indexes for table `martial_statuses`
--
ALTER TABLE `martial_statuses`
  ADD PRIMARY KEY (`martial_status_id`);

--
-- Indexes for table `red_zones`
--
ALTER TABLE `red_zones`
  ADD PRIMARY KEY (`red_zone_id`);

--
-- Indexes for table `requisitions`
--
ALTER TABLE `requisitions`
  ADD PRIMARY KEY (`requisition_id`);

--
-- Indexes for table `requisition_statues`
--
ALTER TABLE `requisition_statues`
  ADD PRIMARY KEY (`requisition_status_id`);

--
-- Indexes for table `requisition_types`
--
ALTER TABLE `requisition_types`
  ADD PRIMARY KEY (`requisition_type_id`);

--
-- Indexes for table `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`role_id`);

--
-- Indexes for table `states`
--
ALTER TABLE `states`
  ADD PRIMARY KEY (`state_id`);

--
-- Indexes for table `support_tickets`
--
ALTER TABLE `support_tickets`
  ADD PRIMARY KEY (`support_ticket_id`);

--
-- Indexes for table `support_ticket_statues`
--
ALTER TABLE `support_ticket_statues`
  ADD PRIMARY KEY (`support_ticket_status_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `branches`
--
ALTER TABLE `branches`
  MODIFY `branch_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `companies`
--
ALTER TABLE `companies`
  MODIFY `company_Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `departments`
--
ALTER TABLE `departments`
  MODIFY `department_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `department_heads`
--
ALTER TABLE `department_heads`
  MODIFY `department_head_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `employee_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `employee_roles`
--
ALTER TABLE `employee_roles`
  MODIFY `employee_role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `employee_status`
--
ALTER TABLE `employee_status`
  MODIFY `employee_status_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `genders`
--
ALTER TABLE `genders`
  MODIFY `gender_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `holidays`
--
ALTER TABLE `holidays`
  MODIFY `holiday_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `issue_types`
--
ALTER TABLE `issue_types`
  MODIFY `issue_type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `leaves`
--
ALTER TABLE `leaves`
  MODIFY `leave_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=54;

--
-- AUTO_INCREMENT for table `leave_plans`
--
ALTER TABLE `leave_plans`
  MODIFY `leave_plan_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `leave_plan_statues`
--
ALTER TABLE `leave_plan_statues`
  MODIFY `leave_plan_status_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `leave_statues`
--
ALTER TABLE `leave_statues`
  MODIFY `leave_status_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `leave_types`
--
ALTER TABLE `leave_types`
  MODIFY `leave_type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `levels`
--
ALTER TABLE `levels`
  MODIFY `level_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `martial_statuses`
--
ALTER TABLE `martial_statuses`
  MODIFY `martial_status_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `red_zones`
--
ALTER TABLE `red_zones`
  MODIFY `red_zone_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `requisitions`
--
ALTER TABLE `requisitions`
  MODIFY `requisition_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `requisition_statues`
--
ALTER TABLE `requisition_statues`
  MODIFY `requisition_status_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `requisition_types`
--
ALTER TABLE `requisition_types`
  MODIFY `requisition_type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `roles`
--
ALTER TABLE `roles`
  MODIFY `role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `states`
--
ALTER TABLE `states`
  MODIFY `state_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `support_tickets`
--
ALTER TABLE `support_tickets`
  MODIFY `support_ticket_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=30;

--
-- AUTO_INCREMENT for table `support_ticket_statues`
--
ALTER TABLE `support_ticket_statues`
  MODIFY `support_ticket_status_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
