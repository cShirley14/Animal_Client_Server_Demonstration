/**************************************
Author: Chantal Shirley
Animal Database Script for Java-Based Program
Date: 11/17/20
Desc: This is a file containing the sql
script needed to generate the Animal 
Database used to catalogue animals.
***************************************/

/** Needed operations
* Drop/Create Database
* Drop/Create Table
* Drop/Create Procedure to add an Animal record
* Drop/Create Procedure to get a single Animal Record
* Call procedure to add 3-5 animals
**/
/**************************************
Part 1 - The establishment of tables for
the Animal database
***************************************/
-- Create the database
DROP DATABASE IF EXISTS animal;
CREATE DATABASE animal;

-- Select the database
USE animal;

--drop any existing table
DROP TABLE animal;
-- create table
CREATE TABLE animal
(
    id                      VARCHAR(25) PRIMARY KEY,
    animal_name             VARCHAR(150) NOT NULL,
    gender                  ENUM('Male', 'Female', 'Unknown') NOT NULL DEFAULT 'Unknown',
    age                     SMALLINT UNSIGNED NOT NULL,
    fixed                   ENUM('Yes', 'No') DEFAULT 'No',
    legs                    INT UNSIGNED NOT NULL,
    weight                  FLOAT (8,4) NOT NULL,
    dateAdded               DATE NOT NULL,
    lastFeedingTime         DATETIME
);

INSERT INTO animal (
    id
    , animal_name
    , gender
    , age
    , fixed
    , legs
    , weight
    , dateAdded
    , lastFeedingTime
) VALUES (
    '18'
    , 'Aech'
    , 'Male'
    , 2
    , 'Yes'
    , 4
    , 55
    , '2018-12-25'
    , '2020-12-24'
);