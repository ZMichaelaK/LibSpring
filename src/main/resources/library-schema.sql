DROP TABLE `item`;
DROP TABLE `person`;
CREATE TABLE `person` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`name` VARCHAR
);

CREATE TABLE `item` (
	`id` INT PRIMARY KEY AUTO_INCREMENT,
	`type` VARCHAR,
	`name` VARCHAR,
	`person_id` INT,
	FOREIGN KEY (`person_id`) REFERENCES `person` (`id`)
);