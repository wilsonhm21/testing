/*
SQLyog Community v13.2.1 (64 bit)
MySQL - 10.4.28-MariaDB : Database - facturacion
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`facturacion` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `facturacion`;

/*Table structure for table `cab_factura` */

DROP TABLE IF EXISTS `cab_factura`;

CREATE TABLE `cab_factura` (
  `id_factura` int(11) NOT NULL AUTO_INCREMENT,
  `igv` decimal(10,2) DEFAULT NULL,
  `num_factura` int(11) DEFAULT NULL,
  `ruc_cliente` varchar(255) DEFAULT NULL,
  `subtotal` decimal(10,2) DEFAULT NULL,
  `total` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id_factura`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `cab_factura` */

/*Table structure for table `cliente` */

DROP TABLE IF EXISTS `cliente`;

CREATE TABLE `cliente` (
  `id_cliente` int(11) NOT NULL AUTO_INCREMENT,
  `activo` tinyint(4) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `fecha_creacion` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `ruc_dni` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `cliente` */

/*Table structure for table `det_factura` */

DROP TABLE IF EXISTS `det_factura`;

CREATE TABLE `det_factura` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cantidad` int(11) DEFAULT NULL,
  `codigo_producto` int(11) DEFAULT NULL,
  `pk_cab_factura` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7p2rc9ecd4v0a2a2lrgesxe9i` (`pk_cab_factura`),
  CONSTRAINT `FK7p2rc9ecd4v0a2a2lrgesxe9i` FOREIGN KEY (`pk_cab_factura`) REFERENCES `cab_factura` (`id_factura`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `det_factura` */

/*Table structure for table `producto` */

DROP TABLE IF EXISTS `producto`;

CREATE TABLE `producto` (
  `id_producto` int(11) NOT NULL AUTO_INCREMENT,
  `activo` tinyint(4) DEFAULT NULL,
  `codigo` varchar(15) DEFAULT NULL,
  `fecha_creacion` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `stock` double DEFAULT NULL,
  PRIMARY KEY (`id_producto`),
  UNIQUE KEY `UK_kxdt4u9c4w6vveo7ylph4pd09` (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `producto` */

/*Table structure for table `proveedor` */

DROP TABLE IF EXISTS `proveedor`;

CREATE TABLE `proveedor` (
  `id_proveedor` int(11) NOT NULL AUTO_INCREMENT,
  `correo` varchar(255) DEFAULT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `fecha_creacion` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `ruc` varchar(25) DEFAULT NULL,
  PRIMARY KEY (`id_proveedor`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `proveedor` */

/*Table structure for table `usuario` */

DROP TABLE IF EXISTS `usuario`;

CREATE TABLE `usuario` (
  `username` varchar(255) NOT NULL,
  `bloqueado` tinyint(4) DEFAULT NULL,
  `intentos_fallidos` int(11) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `usuario` */

insert  into `usuario`(`username`,`bloqueado`,`intentos_fallidos`,`password`) values 
('admin',0,0,'admin');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
