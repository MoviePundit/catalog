CREATE TABLE `product_catelog` (
  `product_id` bigint NOT NULL AUTO_INCREMENT,
  `product_category` varchar(255) DEFAULT NULL,
  `product_description` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) DEFAULT NULL,
  `units` int DEFAULT NULL,
  PRIMARY KEY (`product_id`)
);


INSERT INTO `product_catelog` (`product_id`,`product_description`,`product_name`,`product_category`,`units`) 
VALUES (1,'Passenger Aircraft Family','A350','Commercial',5);

INSERT INTO `product_catelog` (`product_id`,`product_description`,`product_name`,`product_category`,`units`) 
VALUES (2,'Intermideate Single','H125','Helicopter',5);

INSERT INTO `product_catelog` (`product_id`,`product_description`,`product_name`,`product_category`,`units`) 
VALUES (3,'Satellite family','Sentinel','Space',1);

INSERT INTO `product_catelog` (`product_id`,`product_description`,`product_name`,`product_category`,`units`) 
VALUES (4,'Legacy Aircraft Family','A350','Commercial',4);

INSERT INTO `product_catelog` (`product_id`,`product_description`,`product_name`,`product_category`,`units`) 
VALUES (5,'Defence Aircraft Family','F16','Private',3);

INSERT INTO `product_catelog` (`product_id`,`product_description`,`product_name`,`product_category`,`units`) 
VALUES (6,'Commercial Aircraft Family','A350','Commercial',2);

INSERT INTO `product_catelog` (`product_id`,`product_description`,`product_name`,`product_category`,`units`) 
VALUES (7,'Jet Aircraft Family','A350','Private',1);

INSERT INTO `product_catelog` (`product_id`,`product_description`,`product_name`,`product_category`,`units`) 
VALUES (8,'Jet Aircraft Family','A350','Private',1);

