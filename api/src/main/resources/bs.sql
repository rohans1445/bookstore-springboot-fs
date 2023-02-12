-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema bookstore-springboot-api
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema bookstore-springboot-api
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `bookstore-springboot-api` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ;
USE `bookstore-springboot-api` ;

-- -----------------------------------------------------
-- Table `bookstore-springboot-api`.`book_detail`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore-springboot-api`.`book_detail` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `isbn` VARCHAR(255) NULL DEFAULT NULL,
  `language` VARCHAR(255) NULL DEFAULT NULL,
  `publisher` VARCHAR(255) NULL DEFAULT NULL,
  `long_desc` VARCHAR(1024) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 64
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore-springboot-api`.`book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore-springboot-api`.`book` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `author` VARCHAR(45) NULL DEFAULT NULL,
  `img_path` VARCHAR(255) NULL DEFAULT NULL,
  `price` FLOAT NULL DEFAULT NULL,
  `short_desc` VARCHAR(512) NULL DEFAULT NULL,
  `title` VARCHAR(50) NULL DEFAULT NULL,
  `book_detail_id` INT NULL DEFAULT NULL,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `tags` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKio72xbateqjgsts3w2fwwr8l2` (`book_detail_id` ASC) VISIBLE,
  CONSTRAINT `FKio72xbateqjgsts3w2fwwr8l2`
    FOREIGN KEY (`book_detail_id`)
    REFERENCES `bookstore-springboot-api`.`book_detail` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 35
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore-springboot-api`.`bookstore_user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore-springboot-api`.`bookstore_user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(255) NOT NULL,
  `first_name` VARCHAR(20) NOT NULL,
  `last_name` VARCHAR(20) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `roles` VARCHAR(255) NULL DEFAULT NULL,
  `user_name` VARCHAR(40) NULL DEFAULT NULL,
  `credits` DOUBLE NOT NULL,
  `user_img` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 21
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore-springboot-api`.`bookstore_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore-springboot-api`.`bookstore_order` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `payment_type` VARCHAR(255) NULL DEFAULT NULL,
  `total_amt` DOUBLE NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  `status` VARCHAR(255) NULL DEFAULT NULL,
  `receipt_url` VARCHAR(255) NULL DEFAULT NULL,
  `stripe_session_id` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKi54tx5e7b9ue47868gs5egpvr` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FKi54tx5e7b9ue47868gs5egpvr`
    FOREIGN KEY (`user_id`)
    REFERENCES `bookstore-springboot-api`.`bookstore_user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 68
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore-springboot-api`.`cart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore-springboot-api`.`cart` (
  `book_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  INDEX `FK7f24kmlp6cyjqfriv5rsudgru` (`user_id` ASC) VISIBLE,
  INDEX `FK5n0sq8ulj6ykdnrh4dnk0vfmw` (`book_id` ASC) VISIBLE,
  CONSTRAINT `FK5n0sq8ulj6ykdnrh4dnk0vfmw`
    FOREIGN KEY (`book_id`)
    REFERENCES `bookstore-springboot-api`.`book` (`id`),
  CONSTRAINT `FK7f24kmlp6cyjqfriv5rsudgru`
    FOREIGN KEY (`user_id`)
    REFERENCES `bookstore-springboot-api`.`bookstore_user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore-springboot-api`.`exchange_board`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore-springboot-api`.`exchange_board` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `closed_at` DATETIME(6) NULL DEFAULT NULL,
  `created_at` DATETIME(6) NULL DEFAULT NULL,
  `status` VARCHAR(255) NULL DEFAULT NULL,
  `exchange_closer_user_id` INT NULL DEFAULT NULL,
  `exchange_opener_user_id` INT NULL DEFAULT NULL,
  `opener_exchange_book_id` INT NULL DEFAULT NULL,
  `opener_owned_book_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FKpfrna9400w5v8d3caw2rbjbjc` (`exchange_closer_user_id` ASC) VISIBLE,
  INDEX `FKsflopcx2rooeieupta7lv09h3` (`exchange_opener_user_id` ASC) VISIBLE,
  INDEX `FKol6t93jbnngt2ag2u31y3mngy` (`opener_exchange_book_id` ASC) VISIBLE,
  INDEX `FKqvn196c0pd6g50xob55mc5o3v` (`opener_owned_book_id` ASC) VISIBLE,
  CONSTRAINT `FKol6t93jbnngt2ag2u31y3mngy`
    FOREIGN KEY (`opener_exchange_book_id`)
    REFERENCES `bookstore-springboot-api`.`book` (`id`),
  CONSTRAINT `FKpfrna9400w5v8d3caw2rbjbjc`
    FOREIGN KEY (`exchange_closer_user_id`)
    REFERENCES `bookstore-springboot-api`.`bookstore_user` (`id`),
  CONSTRAINT `FKqvn196c0pd6g50xob55mc5o3v`
    FOREIGN KEY (`opener_owned_book_id`)
    REFERENCES `bookstore-springboot-api`.`book` (`id`),
  CONSTRAINT `FKsflopcx2rooeieupta7lv09h3`
    FOREIGN KEY (`exchange_opener_user_id`)
    REFERENCES `bookstore-springboot-api`.`bookstore_user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore-springboot-api`.`order_items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore-springboot-api`.`order_items` (
  `order_id` INT NOT NULL,
  `book_id` INT NOT NULL,
  INDEX `FK3urcws17ooy0lbjtwi83wf1l6` (`order_id` ASC) VISIBLE,
  INDEX `FKqscqcme08spiyt2guyqdj72eh` (`book_id` ASC) VISIBLE,
  CONSTRAINT `FK3urcws17ooy0lbjtwi83wf1l6`
    FOREIGN KEY (`order_id`)
    REFERENCES `bookstore-springboot-api`.`bookstore_order` (`id`)
    ON DELETE CASCADE,
  CONSTRAINT `FKqscqcme08spiyt2guyqdj72eh`
    FOREIGN KEY (`book_id`)
    REFERENCES `bookstore-springboot-api`.`book` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore-springboot-api`.`promo`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore-springboot-api`.`promo` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `promo_amount` DOUBLE NULL DEFAULT NULL,
  `promo_code` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore-springboot-api`.`review`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore-springboot-api`.`review` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `content` VARCHAR(512) NULL DEFAULT NULL,
  `rating` INT NOT NULL,
  `timestamp` DATETIME(6) NULL DEFAULT NULL,
  `title` VARCHAR(255) NULL DEFAULT NULL,
  `book_id` INT NULL DEFAULT NULL,
  `user_id` INT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK70yrt09r4r54tcgkrwbeqenbs` (`book_id` ASC) VISIBLE,
  INDEX `FKfoea77te8dj4ha0ewkq5iq08p` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FK70yrt09r4r54tcgkrwbeqenbs`
    FOREIGN KEY (`book_id`)
    REFERENCES `bookstore-springboot-api`.`book` (`id`),
  CONSTRAINT `FKfoea77te8dj4ha0ewkq5iq08p`
    FOREIGN KEY (`user_id`)
    REFERENCES `bookstore-springboot-api`.`bookstore_user` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


-- -----------------------------------------------------
-- Table `bookstore-springboot-api`.`user_inventory`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `bookstore-springboot-api`.`user_inventory` (
  `user_id` INT NOT NULL,
  `book_id` INT NOT NULL,
  INDEX `FKn2brxaaekd6wk77u9qrmg5v05` (`book_id` ASC) VISIBLE,
  INDEX `FKrc157o6xjq4u1lwo7xgl8dq63` (`user_id` ASC) VISIBLE,
  CONSTRAINT `FKn2brxaaekd6wk77u9qrmg5v05`
    FOREIGN KEY (`book_id`)
    REFERENCES `bookstore-springboot-api`.`book` (`id`),
  CONSTRAINT `FKrc157o6xjq4u1lwo7xgl8dq63`
    FOREIGN KEY (`user_id`)
    REFERENCES `bookstore-springboot-api`.`bookstore_user` (`id`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;


INSERT INTO `bookstore-springboot-api`.`bookstore_user`
(`email`,
`first_name`,
`last_name`,
`password`,
`roles`,
`user_name`,
`credits`,
`user_img`)
VALUES
(`admin1@test.com`,
`John`,
`Doe`,
`$2y$10$9Ws67k08gucSb9sjL8OYv.lDjPKU6J075idVDqaroCW9S6jthN71a`,
`ROLE_ADMIN`,
`admin1`,
`500`,
`https://fastly.picsum.photos/id/314/200/200.jpg?hmac=bCAc2iO5ovLPrvwDQV31aBPS13QTyv33ut2H2wY4QXU`);


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
