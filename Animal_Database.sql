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
	dateAdded               DATETIME DEFAULT NOW(),
    lastFeedingTime         DATETIME DEFAULT NOW()
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
) VALUES (
    '18'
    , 'Aech'
    , 'Dog'
    , 'Male'
    , 2
    , 'Yes'
    , 4
    , 55
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
);

DELIMITER $$
CREATE PROCEDURE sp_add_animal(
    IN p_id VARCHAR(25),
    IN p_name VARCHAR(150),
    IN p_species ENUM('Dog', 'Cat', 'Unknown'),
    IN p_gender ENUM('Male', 'Female', 'Unknown'),
    IN p_age SMALLINT,
    IN p_fixed ENUM('Yes', 'No'),
    IN p_legs INT UNSIGNED,
    IN p_weight Float(8,2),
    IN p_date_added DATETIME,
    IN p_last_feeding_time DATETIME
)
BEGIN
    INSERT INTO animal(
        id,
        name,
        species,
        gender,
        age,
        fixed,
        legs,
        weight,
        dateAdded,
        lastFeedingTime
    )
    VALUES (
        p_id,
        p_name,
        p_species,
        p_gender,
        p_age,
        p_fixed,
        p_legs,
        p_weight,
        p_date_added,
        p_last_feeding_time
    );
END$$
DELIMITER ;

select * from animal;

CALL sp_add_animal('6485', 'Jackson', 'Dog', 'Male', 10, 'Yes', 4, 52.3, NOW(), NOW());
CALL sp_add_animal('3285', 'Oliver', 'Cat', 'Male', 20, 'Yes', 4, 15, NOW(), NOW());
CALL sp_add_animal('8494', 'Sandy', 'Dog', 'Female', 6, 'Yes', 4, 34.58, NOW(), NOW());

select * from animal;

DELIMITER $$
CREATE PROCEDURE sp_get_animal_by_id(
    IN p_id VARCHAR(25)
)
BEGIN
    SELECT *
    FROM
        animal
    WHERE
        id = p_id;
END$$
DELIMITER ;

CALL sp_get_animal_by_id('3285');
CALL sp_get_animal_by_id('68');
CALL sp_get_animal_by_id('15');