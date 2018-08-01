-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema linkedIn
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema linkedIn
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `linkedIn` DEFAULT CHARACTER SET utf8 ;
USE `linkedIn` ;

-- -----------------------------------------------------
-- Table `linkedIn`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `linkedIn`.`User` (
  `idUser` INT NOT NULL AUTO_INCREMENT,
  `email` VARCHAR(100) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `phoneNumber` VARCHAR(45) NOT NULL,
  `isModerator` INT NOT NULL,
  `photoUrl` VARCHAR(100) NULL,
  `educationText` TEXT(1000) NULL,
  `jobExperienceText` TEXT(1000) NULL,
  PRIMARY KEY (`idUser`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `linkedIn`.`Connection`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `linkedIn`.`Connection` (
  `idConnection` INT NOT NULL AUTO_INCREMENT,
  `connectedUserId` INT NOT NULL,
  `User_idUser` INT NOT NULL,
  PRIMARY KEY (`idConnection`, `User_idUser`),
  INDEX `fk_Connection_User_idx` (`User_idUser` ASC),
  CONSTRAINT `fk_Connection_User`
    FOREIGN KEY (`User_idUser`)
    REFERENCES `linkedIn`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `linkedIn`.`ConnectionRequest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `linkedIn`.`ConnectionRequest` (
  `idConnectionRequest` INT NOT NULL AUTO_INCREMENT,
  `senderId` INT NOT NULL,
  `sendTime` DATETIME NOT NULL,
  `User_idUser` INT NOT NULL,
  PRIMARY KEY (`idConnectionRequest`, `User_idUser`),
  INDEX `fk_ConnectionRequest_User1_idx` (`User_idUser` ASC),
  CONSTRAINT `fk_ConnectionRequest_User1`
    FOREIGN KEY (`User_idUser`)
    REFERENCES `linkedIn`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `linkedIn`.`Advertisment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `linkedIn`.`Advertisment` (
  `idAdvertisment` INT NOT NULL AUTO_INCREMENT,
  `uploadTime` DATETIME NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `descriptionText` TEXT(3000) NOT NULL,
  `User_idUser` INT NOT NULL,
  PRIMARY KEY (`idAdvertisment`, `User_idUser`),
  INDEX `fk_Advertisment_User1_idx` (`User_idUser` ASC),
  CONSTRAINT `fk_Advertisment_User1`
    FOREIGN KEY (`User_idUser`)
    REFERENCES `linkedIn`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `linkedIn`.`Application`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `linkedIn`.`Application` (
  `idApplication` INT NOT NULL AUTO_INCREMENT,
  `applicantId` INT NOT NULL,
  `Advertisment_idAdvertisment` INT NOT NULL,
  `Advertisment_User_idUser` INT NOT NULL,
  PRIMARY KEY (`idApplication`, `Advertisment_idAdvertisment`, `Advertisment_User_idUser`),
  INDEX `fk_Application_Advertisment1_idx` (`Advertisment_idAdvertisment` ASC, `Advertisment_User_idUser` ASC),
  CONSTRAINT `fk_Application_Advertisment1`
    FOREIGN KEY (`Advertisment_idAdvertisment` , `Advertisment_User_idUser`)
    REFERENCES `linkedIn`.`Advertisment` (`idAdvertisment` , `User_idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `linkedIn`.`Skill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `linkedIn`.`Skill` (
  `idSkill` INT NOT NULL AUTO_INCREMENT,
  `skillName` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`idSkill`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `linkedIn`.`Conversation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `linkedIn`.`Conversation` (
  `idConversation` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idConversation`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `linkedIn`.`Message`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `linkedIn`.`Message` (
  `idMessage` INT NOT NULL AUTO_INCREMENT,
  `contentText` TEXT(500) NOT NULL,
  `sendTime` DATETIME NOT NULL,
  `senderId` INT NOT NULL,
  `Conversation_idConversation` INT NOT NULL,
  PRIMARY KEY (`idMessage`, `Conversation_idConversation`),
  INDEX `fk_Message_Conversation1_idx` (`Conversation_idConversation` ASC),
  CONSTRAINT `fk_Message_Conversation1`
    FOREIGN KEY (`Conversation_idConversation`)
    REFERENCES `linkedIn`.`Conversation` (`idConversation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `linkedIn`.`Article`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `linkedIn`.`Article` (
  `idArticle` INT NOT NULL AUTO_INCREMENT,
  `uploadTime` DATETIME NOT NULL,
  `title` VARCHAR(100) NOT NULL,
  `contentText` TEXT(4000) NULL,
  `photoUrl` VARCHAR(100) NULL,
  `soundUrl` VARCHAR(100) NULL,
  `videoUrl` VARCHAR(100) NULL,
  `User_idUser` INT NOT NULL,
  PRIMARY KEY (`idArticle`, `User_idUser`),
  INDEX `fk_Article_User1_idx` (`User_idUser` ASC),
  CONSTRAINT `fk_Article_User1`
    FOREIGN KEY (`User_idUser`)
    REFERENCES `linkedIn`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `linkedIn`.`Comment`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `linkedIn`.`Comment` (
  `idComment` INT NOT NULL AUTO_INCREMENT,
  `uploadTime` DATETIME NOT NULL,
  `contentText` TEXT(500) NOT NULL,
  `Article_idArticle` INT NOT NULL,
  `Article_User_idUser` INT NOT NULL,
  PRIMARY KEY (`idComment`, `Article_idArticle`, `Article_User_idUser`),
  INDEX `fk_Comment_Article1_idx` (`Article_idArticle` ASC, `Article_User_idUser` ASC),
  CONSTRAINT `fk_Comment_Article1`
    FOREIGN KEY (`Article_idArticle` , `Article_User_idUser`)
    REFERENCES `linkedIn`.`Article` (`idArticle` , `User_idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `linkedIn`.`Interest`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `linkedIn`.`Interest` (
  `idInterest` INT NOT NULL AUTO_INCREMENT,
  `interestTime` DATETIME NOT NULL,
  `Article_idArticle` INT NOT NULL,
  `Article_User_idUser` INT NOT NULL,
  PRIMARY KEY (`idInterest`, `Article_idArticle`, `Article_User_idUser`),
  INDEX `fk_Interest_Article1_idx` (`Article_idArticle` ASC, `Article_User_idUser` ASC),
  CONSTRAINT `fk_Interest_Article1`
    FOREIGN KEY (`Article_idArticle` , `Article_User_idUser`)
    REFERENCES `linkedIn`.`Article` (`idArticle` , `User_idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `linkedIn`.`User_has_Skill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `linkedIn`.`User_has_Skill` (
  `User_idUser` INT NOT NULL,
  `Skill_idSkill` INT NOT NULL,
  PRIMARY KEY (`User_idUser`, `Skill_idSkill`),
  INDEX `fk_User_has_Skill_Skill1_idx` (`Skill_idSkill` ASC),
  INDEX `fk_User_has_Skill_User1_idx` (`User_idUser` ASC),
  CONSTRAINT `fk_User_has_Skill_User1`
    FOREIGN KEY (`User_idUser`)
    REFERENCES `linkedIn`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Skill_Skill1`
    FOREIGN KEY (`Skill_idSkill`)
    REFERENCES `linkedIn`.`Skill` (`idSkill`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `linkedIn`.`User_has_Conversation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `linkedIn`.`User_has_Conversation` (
  `User_idUser` INT NOT NULL,
  `Conversation_idConversation` INT NOT NULL,
  PRIMARY KEY (`User_idUser`, `Conversation_idConversation`),
  INDEX `fk_User_has_Conversation_Conversation1_idx` (`Conversation_idConversation` ASC),
  INDEX `fk_User_has_Conversation_User1_idx` (`User_idUser` ASC),
  CONSTRAINT `fk_User_has_Conversation_User1`
    FOREIGN KEY (`User_idUser`)
    REFERENCES `linkedIn`.`User` (`idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_User_has_Conversation_Conversation1`
    FOREIGN KEY (`Conversation_idConversation`)
    REFERENCES `linkedIn`.`Conversation` (`idConversation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `linkedIn`.`Advertisment_has_Skill`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `linkedIn`.`Advertisment_has_Skill` (
  `Advertisment_idAdvertisment` INT NOT NULL,
  `Advertisment_User_idUser` INT NOT NULL,
  `Skill_idSkill` INT NOT NULL,
  PRIMARY KEY (`Advertisment_idAdvertisment`, `Advertisment_User_idUser`, `Skill_idSkill`),
  INDEX `fk_Advertisment_has_Skill_Skill1_idx` (`Skill_idSkill` ASC),
  INDEX `fk_Advertisment_has_Skill_Advertisment1_idx` (`Advertisment_idAdvertisment` ASC, `Advertisment_User_idUser` ASC),
  CONSTRAINT `fk_Advertisment_has_Skill_Advertisment1`
    FOREIGN KEY (`Advertisment_idAdvertisment` , `Advertisment_User_idUser`)
    REFERENCES `linkedIn`.`Advertisment` (`idAdvertisment` , `User_idUser`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Advertisment_has_Skill_Skill1`
    FOREIGN KEY (`Skill_idSkill`)
    REFERENCES `linkedIn`.`Skill` (`idSkill`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
