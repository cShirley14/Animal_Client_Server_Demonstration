/**************************************
Author: Chantal Shirley
Animal Database Script for Java-Based Program
Date: 11/17/20
Desc: This is a file containing the sql
script needed to generate the Animal 
Database used to catalogue animals.
***************************************/

/** Needed operations
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

-- create table
CREATE TABLE animal
(
    id                      VARCHAR(25) PRIMARY KEY,
    name             		VARCHAR(150) NOT NULL,
    species                 ENUM('Dog', 'Cat', 'Unknown') NOT NULL DEFAULT 'Unknown',
    gender                  ENUM('Male', 'Female', 'Unknown') NOT NULL DEFAULT 'Unknown',
    age                     SMALLINT UNSIGNED NOT NULL,
    fixed                   ENUM('Yes', 'No') DEFAULT 'No',
    legs                    INT UNSIGNED NOT NULL,
    weight                  FLOAT (8,2) NOT NULL,
    dateAdded               DATE NOT NULL,
    lastFeedingTime         DATETIME
);

INSERT INTO animal (
    id
    , name
    , species    
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
    , 'Dog'
    , 'Male'
    , 2
    , 'Yes'
    , 4
    , 55
    , '2018-12-25'
    , '2020-12-24 11:30:27'
),
(
    '68'
    , 'Buster'
    , 'Dog'
    , 'Male'
    , 4
    , 'Yes'
    , 4
    , 75
    , '2016-04-05'
    , '2020-12-24 15:24:45'
),
(
    '12863'
    , 'Farrow'
    , 'Cat'
    , 'Female'
    , 1
    , 'Yes'
    , 4
    , 8.5
    , '2017-02-13'
    , '2020-12-24 18:45:32'
),
(
    '15'
    , 'Totoro'
    , 'Cat'
    , 'Male'
    , 9
    , 'Yes'
    , 4
    , 17.12
    , '2020-05-27'
    , '2020-12-24 06:24:17'
),
(
    '14'
    , 'Unknown'
    , 'Unknown'
    , 'Unknown'
    , 5
    , 'No'
    , 4
    , 5
    , '2020-11-16'
    , '2020-12-24 04:17:35'
);

DELIMITER $$
CREATE PROCEDURE sp_add_animal(
    IN p_id                     VARCHAR(25),
    IN p_name                   VARCHAR(150),
    IN p_species                ENUM('Dog', 'Cat', 'Unknown'),
    IN p_gender                 ENUM('Male', 'Female', 'Unknown'),
    IN p_age                    SMALLINT,
    IN p_fixed                  ENUM('Yes', 'No'),
    IN p_legs                   INT
    IN p_weight                 Float(8,2),
    IN p_date_added             DATE,
    IN p_last_feeding_time      DATETIME
)

select * from animal;