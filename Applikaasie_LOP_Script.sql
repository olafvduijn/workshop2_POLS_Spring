-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema applikaasie_lop
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `applikaasie_lop` ;

-- -----------------------------------------------------
-- Schema applikaasie_lop
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `applikaasie_lop` DEFAULT CHARACTER SET utf8mb4 ;
USE `applikaasie_lop` ;

-- -----------------------------------------------------
-- Table `applikaasie_lop`.`account`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `applikaasie_lop`.`account` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL DEFAULT NULL,
  `password` VARCHAR(45) NULL DEFAULT NULL,
  `rol` ENUM('klant', 'medewerker', 'beheerder') NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idAccount_UNIQUE` (`id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `applikaasie_lop`.`klant`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `applikaasie_lop`.`klant` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `voornaam` VARCHAR(45) NULL DEFAULT NULL,
  `tussenvoegsel` VARCHAR(45) NULL DEFAULT NULL,
  `achternaam` VARCHAR(45) NULL DEFAULT NULL,
  `account_id` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idKlant_UNIQUE` (`id` ASC),
  INDEX `fk_klant_account1_idx` (`account_id` ASC),
  CONSTRAINT `fk_klant_account1`
    FOREIGN KEY (`account_id`)
    REFERENCES `applikaasie_lop`.`account` (`id`)
    ON DELETE  CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `applikaasie_lop`.`adres`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `applikaasie_lop`.`adres` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `straatnaam` VARCHAR(45) NULL DEFAULT NULL,
  `huisnummer` VARCHAR(45) NULL DEFAULT NULL,
  `toevoeging` VARCHAR(45) NULL DEFAULT NULL,
  `postcode` VARCHAR(45) NULL DEFAULT NULL,
  `woonplaats` VARCHAR(45) NULL DEFAULT NULL,
  `Adrestype` ENUM('postadres', 'factuuradres', 'bezorgadres') NULL DEFAULT NULL,
  `Klant_idKlant` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idAdres_UNIQUE` (`id` ASC),
  INDEX `fk_Adres_Klant1_idx` (`Klant_idKlant` ASC),
  CONSTRAINT `fk_Adres_Klant1`
    FOREIGN KEY (`Klant_idKlant`)
    REFERENCES `applikaasie_lop`.`klant` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `applikaasie_lop`.`artikel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `applikaasie_lop`.`artikel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `naam` VARCHAR(45) NULL DEFAULT NULL,
  `prijs` DECIMAL(6,2) NULL DEFAULT NULL,
  `voorraad` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idArtikel_UNIQUE` (`id` ASC),
  UNIQUE INDEX `naam_UNIQUE` (`naam` ASC))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `applikaasie_lop`.`bestelling`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `applikaasie_lop`.`bestelling` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `totaalprijs` DECIMAL(6,2) NULL DEFAULT NULL,
  `Klant_idKlant` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idBestelling_UNIQUE` (`id` ASC),
  INDEX `fk_Bestelling_Klant1_idx` (`Klant_idKlant` ASC),
  CONSTRAINT `fk_Bestelling_Klant1`
    FOREIGN KEY (`Klant_idKlant`)
    REFERENCES `applikaasie_lop`.`klant` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


-- -----------------------------------------------------
-- Table `applikaasie_lop`.`bestelregel`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `applikaasie_lop`.`bestelregel` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `aantal` INT(11) NULL DEFAULT NULL,
  `prijs` DECIMAL(6,2) NULL DEFAULT NULL,
  `Bestelling_idBestelling` INT(11) NOT NULL,
  `Artikel_idArtikel` INT(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `idBestelregel_UNIQUE` (`id` ASC),
  INDEX `fk_Bestelregel_Bestelling1_idx` (`Bestelling_idBestelling` ASC),
  INDEX `fk_Bestelregel_Artikel1_idx` (`Artikel_idArtikel` ASC),
  CONSTRAINT `fk_Bestelregel_Artikel1`
    FOREIGN KEY (`Artikel_idArtikel`)
    REFERENCES `applikaasie_lop`.`artikel` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Bestelregel_Bestelling1`
    FOREIGN KEY (`Bestelling_idBestelling`)
    REFERENCES `applikaasie_lop`.`bestelling` (`id`) 
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb4;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
