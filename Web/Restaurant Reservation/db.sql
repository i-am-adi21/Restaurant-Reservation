/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.7.9 : Database - restorent_reservation
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`restorent_reservation` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `restorent_reservation`;

/*Table structure for table `categories` */

DROP TABLE IF EXISTS `categories`;

CREATE TABLE `categories` (
  `category_id` int(11) NOT NULL AUTO_INCREMENT,
  `category_name` varchar(100) DEFAULT NULL,
  `category_description` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`category_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `categories` */

insert  into `categories`(`category_id`,`category_name`,`category_description`) values (1,'c1','testingscdz'),(3,'c2','rdfgtkhvfdretxdxxxax'),(4,'c3','rdfgtk');

/*Table structure for table `customers` */

DROP TABLE IF EXISTS `customers`;

CREATE TABLE `customers` (
  `customer_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(100) DEFAULT NULL,
  `last_name` varchar(100) DEFAULT NULL,
  `place` varchar(100) DEFAULT NULL,
  `pincode` varchar(200) DEFAULT NULL,
  `phone` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`customer_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `customers` */

insert  into `customers`(`customer_id`,`login_id`,`first_name`,`last_name`,`place`,`pincode`,`phone`,`email`) values (1,3,'anu','k','kannur',NULL,'9847967456','anu@g.com');

/*Table structure for table `facilities` */

DROP TABLE IF EXISTS `facilities`;

CREATE TABLE `facilities` (
  `facility_id` int(11) NOT NULL AUTO_INCREMENT,
  `restaurant_id` int(11) DEFAULT NULL,
  `facility_name` varchar(100) DEFAULT NULL,
  `facility_description` varchar(100) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`facility_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `facilities` */

insert  into `facilities`(`facility_id`,`restaurant_id`,`facility_name`,`facility_description`,`image`) values (2,NULL,'ascz','testing',NULL),(4,NULL,'dxbhcn','testingss',NULL),(5,NULL,'ertyuio','dfghj','static/uploads84dbc74a-56d2-4f9e-abce-1e51485ccbd9p2.jpg'),(6,1,'qqqq','qqqqqqqq','static/uploads12b70f00-3f8d-4fdf-8bed-83d773e13b4deliabe-costa-tzsUJD0TGkk-unsplash (2).jpg');

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `feedback_description` varchar(100) DEFAULT NULL,
  `reply_descr` varchar(100) DEFAULT NULL,
  `date_time` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`feedback_id`,`customer_id`,`feedback_description`,`reply_descr`,`date_time`) values (1,1,'sdfghjk','sss','2/3/21'),(2,NULL,'tttf','NA','2021-05-14'),(3,NULL,'fffgg','NA','2021-05-15'),(4,1,'qqqqqqqqq','NA','2021-05-15');

/*Table structure for table `food_ordered` */

DROP TABLE IF EXISTS `food_ordered`;

CREATE TABLE `food_ordered` (
  `ordered_id` int(11) NOT NULL AUTO_INCREMENT,
  `reservation_id` int(11) DEFAULT NULL,
  `menu_id` int(11) DEFAULT NULL,
  `quantity` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ordered_id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

/*Data for the table `food_ordered` */

insert  into `food_ordered`(`ordered_id`,`reservation_id`,`menu_id`,`quantity`) values (1,1,1,'2'),(2,1,4,'5'),(3,1,3,'10'),(4,1,3,'1'),(5,1,3,'1'),(6,1,3,'1'),(7,1,3,'1'),(8,1,3,'1'),(9,1,3,'1'),(10,1,4,'2'),(11,1,1,'2'),(12,1,4,'3'),(13,1,1,'3'),(14,1,1,'3');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `user_type` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`user_type`) values (1,'admin','admin','admin'),(2,'ss','ss','restaurant'),(3,'dd','dd','customer');

/*Table structure for table `menu` */

DROP TABLE IF EXISTS `menu`;

CREATE TABLE `menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `restaurant_id` int(11) DEFAULT NULL,
  `item_name` varchar(100) DEFAULT NULL,
  `item_image` varchar(500) DEFAULT NULL,
  `item_quantity` varchar(100) DEFAULT NULL,
  `item_price` varchar(100) DEFAULT NULL,
  `item_availability` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `menu` */

insert  into `menu`(`menu_id`,`restaurant_id`,`item_name`,`item_image`,`item_quantity`,`item_price`,`item_availability`) values (1,1,'adf','static/uploads/0b6d8530-de43-4216-a986-c93b4e241d79paris.jpg','292','100','available'),(3,1,'qefd edf','static/uploads/1a855875-42bf-49e3-9da9-6dc3eae4a07d453386.jpg','400','450','no'),(4,1,'qqqqq','static/uploads/cbbfade1-163c-48df-bb6a-a5258722b96celiabe-costa-tzsUJD0TGkk-unsplash (2).jpg','90','2000','available');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `reservation_id` int(11) DEFAULT NULL,
  `amount_paid` varchar(100) DEFAULT NULL,
  `payment_date` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=26 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`payment_id`,`reservation_id`,`amount_paid`,`payment_date`) values (1,1,'500','2/3/21'),(2,1,'200','2021-05-16'),(3,1,'200','2021-05-16'),(4,1,'200','2021-05-16'),(5,1,'200','2021-05-16'),(6,1,'200','2021-05-16'),(7,1,'200','2021-05-16'),(8,1,'200','2021-05-16'),(9,1,'500','2021-05-16'),(10,1,'500','2021-05-16'),(11,1,'500','2021-05-16'),(12,1,'500','2021-05-16'),(13,1,'500','2021-05-16'),(14,1,'500','2021-05-16'),(15,1,'500','2021-05-16'),(16,1,'500','2021-05-16'),(17,1,'500','2021-05-16'),(18,1,'500','2021-05-16'),(19,1,'500','2021-05-16'),(20,1,'500','2021-05-16'),(21,1,'500','2021-05-16'),(22,1,'500','2021-05-16'),(23,1,'100','2021-05-16'),(24,1,'100','2021-05-16'),(25,1,'200','2021-05-16');

/*Table structure for table `ratings` */

DROP TABLE IF EXISTS `ratings`;

CREATE TABLE `ratings` (
  `rating_id` int(11) NOT NULL AUTO_INCREMENT,
  `customer_id` int(11) DEFAULT NULL,
  `restaurant_id` int(11) DEFAULT NULL,
  `rate` varchar(200) DEFAULT NULL,
  `review` varchar(200) DEFAULT NULL,
  `date_time` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`rating_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `ratings` */

insert  into `ratings`(`rating_id`,`customer_id`,`restaurant_id`,`rate`,`review`,`date_time`) values (1,1,1,'5','dfghj','2021-12-21');

/*Table structure for table `reservations` */

DROP TABLE IF EXISTS `reservations`;

CREATE TABLE `reservations` (
  `reservation_id` int(11) NOT NULL AUTO_INCREMENT,
  `table_id` int(11) DEFAULT NULL,
  `customer_id` int(11) DEFAULT NULL,
  `reservation_date_time` varchar(100) DEFAULT NULL,
  `reservation_status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`reservation_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `reservations` */

insert  into `reservations`(`reservation_id`,`table_id`,`customer_id`,`reservation_date_time`,`reservation_status`) values (1,1,1,'2/3/21','confirmed'),(2,NULL,NULL,NULL,NULL),(3,1,NULL,'2021-05-16 12:57:05','pending'),(4,11,NULL,'2021-05-16 12:57:21','pending'),(5,11,NULL,'2021-05-16 17:46:42','pending');

/*Table structure for table `restaurant` */

DROP TABLE IF EXISTS `restaurant`;

CREATE TABLE `restaurant` (
  `restaurant_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `name` varchar(200) DEFAULT NULL,
  `place` varchar(200) DEFAULT NULL,
  `landmark` varchar(200) DEFAULT NULL,
  `street` varchar(200) DEFAULT NULL,
  `pincode` varchar(200) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  `status` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`restaurant_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `restaurant` */

insert  into `restaurant`(`restaurant_id`,`login_id`,`name`,`place`,`landmark`,`street`,`pincode`,`phone`,`email`,`status`) values (1,2,'White House','Kaloor','Kaloor Stadium','kaloor','123456','1234567890','qq@gmail.com','accepted');

/*Table structure for table `tables` */

DROP TABLE IF EXISTS `tables`;

CREATE TABLE `tables` (
  `table_id` int(11) NOT NULL AUTO_INCREMENT,
  `restaurant_id` int(11) DEFAULT NULL,
  `category_id` int(11) DEFAULT NULL,
  `table_name` varchar(100) DEFAULT NULL,
  `number_of_seats` varchar(100) DEFAULT NULL,
  `table_availability` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`table_id`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

/*Data for the table `tables` */

insert  into `tables`(`table_id`,`restaurant_id`,`category_id`,`table_name`,`number_of_seats`,`table_availability`) values (1,1,1,'table 2','3','not available'),(7,NULL,3,'table 1','10','not available'),(10,NULL,4,'table 3','4','available'),(11,1,1,'fghjkl','4','available'),(12,1,4,'qqqqqqqqq','6','not available'),(13,1,4,'qqqqqqqqq','6','not available');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
