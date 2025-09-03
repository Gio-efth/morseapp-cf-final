-- Database
CREATE DATABASE IF NOT EXISTS morseapp
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

-- App user 
DROP USER IF EXISTS 'morseUser'@'localhost';
CREATE USER 'morseUser'@'localhost' IDENTIFIED BY '12345';
ALTER USER 'morseUser'@'localhost'
  IDENTIFIED WITH mysql_native_password BY '12345';
GRANT ALL PRIVILEGES ON morseapp.* TO 'morseUser'@'localhost';
FLUSH PRIVILEGES;

USE morseapp;

SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS `translations`;
DROP TABLE IF EXISTS `users`;
SET FOREIGN_KEY_CHECKS = 1;

-- users
CREATE TABLE `users` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `uuid` VARCHAR(36) NOT NULL,
  `username` VARCHAR(50) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `role` ENUM('ADMIN','USER') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `uuid_UNIQUE` (`uuid`)
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;

-- translations
CREATE TABLE `translations` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `input_text` TEXT NOT NULL,
  `output_text` TEXT NOT NULL,
  `direction` ENUM('TEXT_TO_MORSE','MORSE_TO_TEXT') NOT NULL,
  `timestamp` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` BIGINT NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_user_id_idx` (`user_id`),
  CONSTRAINT `fk_user_id`
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
    ON DELETE CASCADE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8mb4
  COLLATE=utf8mb4_0900_ai_ci;

-- Insert User
INSERT INTO `users` (`uuid`, `username`, `password`, `role`)
VALUES
  ('492de216-e4c3-47c4-8ddc-4982eaf03082',
   'morseUser',
   '$2a$11$AcUUQAfoeO2K7Yl0ilZxUOG7C2snrWcHd0bEaTylbO9A0VIfuOsO6',
   'USER');
