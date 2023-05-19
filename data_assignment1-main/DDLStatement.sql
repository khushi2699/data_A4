-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`faculty`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`faculty` (
  `faculty_id` INT NOT NULL,
  `faculty_name` VARCHAR(45) NULL,
  `faculty_email` VARCHAR(45) NULL,
  `faculty_pay` VARCHAR(45) NULL,
  `faculty_sin` VARCHAR(45) NULL,
  `faculty_bank_no` VARCHAR(45) NULL,
  PRIMARY KEY (`faculty_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`program`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`program` (
  `program_id` INT NOT NULL,
  `program_name` VARCHAR(45) NULL,
  `program_level` VARCHAR(45) NULL,
  `program_mode` VARCHAR(45) NULL,
  `program_capacity` VARCHAR(45) NULL,
  `program_duration` VARCHAR(45) NULL,
  `program_prof_id` INT NULL,
  PRIMARY KEY (`program_id`),
  INDEX `professor_id_idx` (`program_prof_id` ASC) VISIBLE,
  CONSTRAINT `program_professor_id`
    FOREIGN KEY (`program_prof_id`)
    REFERENCES `mydb`.`faculty` (`faculty_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`subject` (
  `subject_id` INT NOT NULL,
  `subject_name` VARCHAR(45) NULL,
  `subject_prof` INT NULL,
  `subject_credits` VARCHAR(45) NULL,
  `subject_TA` VARCHAR(45) NULL,
  `subject_prerequisites` VARCHAR(45) NULL,
  `subject_markers` VARCHAR(45) NULL,
  `subject_program_id` INT NULL,
  PRIMARY KEY (`subject_id`),
  INDEX `professor_id_idx` (`subject_prof` ASC) VISIBLE,
  INDEX `program_id_idx` (`subject_program_id` ASC) VISIBLE,
  CONSTRAINT `subject_professor_id`
    FOREIGN KEY (`subject_prof`)
    REFERENCES `mydb`.`faculty` (`faculty_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `subject_program_id`
    FOREIGN KEY (`subject_program_id`)
    REFERENCES `mydb`.`program` (`program_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`student_subjects`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`student_subjects` (
  `student_subject_id` INT NOT NULL,
  `student_subjects_program_id` INT NULL,
  `student_subjects_subject_id` INT NULL,
  PRIMARY KEY (`student_subject_id`),
  INDEX `student_subject_prog_idx` (`student_subjects_program_id` ASC) VISIBLE,
  INDEX `student_subject_sub_idx` (`student_subjects_subject_id` ASC) VISIBLE,
  CONSTRAINT `student_subject_prog`
    FOREIGN KEY (`student_subjects_program_id`)
    REFERENCES `mydb`.`program` (`program_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `student_subject_sub`
    FOREIGN KEY (`student_subjects_subject_id`)
    REFERENCES `mydb`.`subject` (`subject_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`student` (
  `student_banner_id` VARCHAR(9) NOT NULL,
  `student_name` VARCHAR(45) NULL,
  `student_email` VARCHAR(45) NULL,
  `student_fee` VARCHAR(45) NULL,
  `student_permission` VARCHAR(45) NULL,
  `student_term` VARCHAR(45) NULL,
  `student_grades` VARCHAR(45) NULL,
  `student_sub_id` INT NULL,
  PRIMARY KEY (`student_banner_id`),
  INDEX `student_sub_id_idx` (`student_sub_id` ASC) VISIBLE,
  CONSTRAINT `student_sub_id`
    FOREIGN KEY (`student_sub_id`)
    REFERENCES `mydb`.`student_subjects` (`student_subject_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`research`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`research` (
  `research_id` INT NOT NULL,
  `research_topic` VARCHAR(45) NULL,
  `research_budget` VARCHAR(45) NULL,
  `research_supervisor` INT NULL,
  `research_publication` VARCHAR(45) NULL,
  PRIMARY KEY (`research_id`),
  INDEX `prof_id_idx` (`research_supervisor` ASC) VISIBLE,
  CONSTRAINT `research_prof_id`
    FOREIGN KEY (`research_supervisor`)
    REFERENCES `mydb`.`faculty` (`faculty_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`campus`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`campus` (
  `campus_id` INT NOT NULL,
  `campus_name` VARCHAR(45) NULL,
  `campus_address` VARCHAR(45) NULL,
  `campus_building_no` VARCHAR(45) NULL,
  PRIMARY KEY (`campus_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`library`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`library` (
  `library_id` INT NOT NULL,
  `library_name` VARCHAR(45) NULL,
  `library_rooms` VARCHAR(45) NULL,
  `library_books` VARCHAR(45) NULL,
  `library_laptops` VARCHAR(45) NULL,
  `library_campus` INT NULL,
  PRIMARY KEY (`library_id`),
  INDEX `campus_id_idx` (`library_campus` ASC) VISIBLE,
  CONSTRAINT `library_campus_id`
    FOREIGN KEY (`library_campus`)
    REFERENCES `mydb`.`campus` (`campus_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`building`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`building` (
  `building_id` INT NOT NULL,
  `building_name` VARCHAR(45) NULL,
  `building_campus` INT NULL,
  `building_program` INT NULL,
  PRIMARY KEY (`building_id`),
  INDEX `campus_id_idx` (`building_campus` ASC) VISIBLE,
  INDEX `program_id_idx` (`building_program` ASC) VISIBLE,
  CONSTRAINT `building_campus_id`
    FOREIGN KEY (`building_campus`)
    REFERENCES `mydb`.`campus` (`campus_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `building_program_id`
    FOREIGN KEY (`building_program`)
    REFERENCES `mydb`.`program` (`program_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`residence`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`residence` (
  `residence_id` INT NOT NULL,
  `residence_name` VARCHAR(45) NULL,
  `residence_cost` VARCHAR(45) NULL,
  `residence_house_no` VARCHAR(45) NULL,
  `residence_campus_id` INT NULL,
  `residence_student_id` VARCHAR(9) NULL,
  PRIMARY KEY (`residence_id`),
  INDEX `campus_id_idx` (`residence_campus_id` ASC) VISIBLE,
  INDEX `student_id_idx` (`residence_student_id` ASC) VISIBLE,
  CONSTRAINT `residence_campus_id`
    FOREIGN KEY (`residence_campus_id`)
    REFERENCES `mydb`.`campus` (`campus_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `residence_student_id`
    FOREIGN KEY (`residence_student_id`)
    REFERENCES `mydb`.`student` (`student_banner_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
