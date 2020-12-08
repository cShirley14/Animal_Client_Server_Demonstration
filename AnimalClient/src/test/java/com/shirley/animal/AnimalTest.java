package com.shirley.animal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Chantal Shirley
 */
public class AnimalTest {
    
    private Animal instanceEmptyConstructor;
    private static final String GOOD_NAME_ONE = "Aech";
    private static final String GOOD_NAME_TWO = "Buster";
    private static final String GOOD_ID_ONE = "15";
    private static final String GOOD_ID_TWO = "25";
    private static final String GOOD_ID_THREE = "150";
    private static final String BAD_ID_EXISTS = "0";
    private static final String GOOD_SPECIES_DOG = "dog";
    private static final String GOOD_SPECIES_CAT = "cat";
    private static final String BAD_SPECIES = "narwhal";
    private static final String GOOD_GENDER_MALE = "Male";
    private static final String GOOD_GENDER_FEMALE = "Female";
    private static final int GOOD_AGE_LOWER_LIMIT = 0;
    private static final int GOOD_AGE_UPPER_LIMIT = 100;
    private static final int GOOD_AGE = 2;
    private static final int BAD_LOWER_LIMIT = -1;
    private static final int BAD_UPPER_LIMIT = 101;
    private static final boolean GOOD_FIXED_TRUE = true;
    private static final boolean GOOD_FIXED_FALSE = false;
    private static final int GOOD_LEGS = 3;
    private static final int GOOD_LEGS_UPPER_LIMIT = 4;
    private static final int GOOD_LEGS_LOWER_LIMIT = 1;
    private static final int BAD_LEGS_UPPER_LIMIT = 5;
    private static final int BAD_LEGS_LOWER_LIMIT = -1;
    private static final BigDecimal GOOD_WEIGHT = BigDecimal.valueOf(55);
    private static final BigDecimal GOOD_WEIGHT_UPPER_LIMIT = 
            BigDecimal.valueOf(1000);
    private static final BigDecimal GOOD_WEIGHT_LOWER_LIMIT = 
            BigDecimal.valueOf(0);
    private static final BigDecimal BAD_WEIGHT_UPPER_LIMIT = 
            BigDecimal.valueOf(1001);
    private static final BigDecimal BAD_WEIGHT_LOWER_LIMIT = 
            BigDecimal.valueOf(-1);
    private static final LocalDate GOOD_DATE_ADDED = 
            LocalDate.now().minusDays(3);
    private static final LocalDate GOOD_DATE_ADDED_LOWER_LIMIT = 
            LocalDate.now().minusDays(7);
    private static final LocalDate GOOD_DATE_ADDED_UPPER_LIMIT = 
            LocalDate.now();
    private static final LocalDate BAD_DATE_ADDED_LOWER_LIMIT = 
            LocalDate.now().minusDays(8);
    private static final LocalDate BAD_DATE_ADDED_UPPER_LIMIT = 
            LocalDate.now().plusDays(1);
    private static final LocalDateTime GOOD_LAST_FEEDING_TIME = 
            LocalDateTime.now().minusDays(1);
    private static final LocalDateTime BAD_LAST_FEEDING_TIME_UPPER_LIMIT = 
            LocalDateTime.now().plusDays(1);
    private static final LocalDateTime BAD_LAST_FEEDING_TIME_LOWER_LIMIT = 
            LocalDateTime.now().minusDays(3);
    
    public AnimalTest() {
    }
    
    @BeforeEach
    public void setUp() {
        instanceEmptyConstructor = new Animal();
        /*
        instanceDogOne = new Animal(GOOD_ID_ONE, GOOD_NAME_ONE, 
                GOOD_SPECIES_DOG, GOOD_GENDER_MALE, GOOD_AGE, GOOD_FIXED_TRUE, 
                GOOD_LEGS, GOOD_WEIGHT, GOOD_DATE_ADDED, 
                GOOD_LAST_FEEDING_TIME);
        
        instanceDogTwo = new Animal(GOOD_ID_TWO, GOOD_NAME_TWO,
                GOOD_SPECIES_DOG,GOOD_GENDER_MALE, GOOD_AGE, GOOD_FIXED_TRUE, 
                GOOD_LEGS, GOOD_WEIGHT, GOOD_DATE_ADDED, 
                GOOD_LAST_FEEDING_TIME);
        
        instanceCatOne = new Animal(GOOD_ID_THREE, GOOD_NAME_ONE,
                GOOD_SPECIES_DOG, GOOD_GENDER_MALE, GOOD_AGE, GOOD_FIXED_TRUE, 
                GOOD_LEGS, GOOD_WEIGHT, GOOD_DATE_ADDED, 
                GOOD_LAST_FEEDING_TIME);
        
        instanceCatTwo = new Animal(GOOD_ID_FOUR, GOOD_NAME_TWO,
                GOOD_SPECIES_DOG, GOOD_GENDER_MALE, GOOD_AGE, GOOD_FIXED_TRUE, 
                GOOD_LEGS, GOOD_WEIGHT, GOOD_DATE_ADDED, 
                GOOD_LAST_FEEDING_TIME);

        */
    }
    
    @Test
    public void testGetID() {
        String expected = "0";
        String actual = instanceEmptyConstructor.getId();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetIDGood() {
        String expected = GOOD_ID_THREE;
        instanceEmptyConstructor.setId(GOOD_ID_THREE);
        String actual = instanceEmptyConstructor.getId();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetIDBadAlreadyExists() {
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setId(BAD_ID_EXISTS), "Id is already in " + 
                "the list of ids.");
    }
    
    @Test
    public void testSetIDBadAlreadySet() {
        instanceEmptyConstructor.setId("14");
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setId(GOOD_ID_ONE), "You can't change an id "
                    + "that has already been set.");
    }

    @Test
    public void testGetName() {
        String expected = "Unknown";
        String actual = instanceEmptyConstructor.getName();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetName() {
        String expected = GOOD_NAME_ONE;
        instanceEmptyConstructor.setName(GOOD_NAME_ONE);
        String actual = instanceEmptyConstructor.getName();
        assertEquals(expected,actual);
    }

    @Test
    public void testGetSpecies() {
        String expected = "Unknown";
        String actual = instanceEmptyConstructor.getSpecies();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetSpeciesGoodDog() {
        String expected = GOOD_SPECIES_DOG;
        instanceEmptyConstructor.setSpecies(GOOD_SPECIES_DOG);
        String actual = instanceEmptyConstructor.getSpecies();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetSpeciesGoodCat() {
        String expected = GOOD_SPECIES_CAT;
        instanceEmptyConstructor.setSpecies(GOOD_SPECIES_CAT);
        String actual = instanceEmptyConstructor.getSpecies();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetSpeciesBadAlreadyExists() {
        instanceEmptyConstructor.setSpecies(GOOD_SPECIES_DOG);
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setSpecies(GOOD_SPECIES_DOG), "The species cannot be changed"
                    + "because it has already been set.");
    }
    
    @Test
    public void testSetSpeciesBadIllegalSpecies() {
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setSpecies(BAD_SPECIES), "The species must be a"
                    + " cat or dog.");
    }
    
    @Test
    public void testGetGender() {
        String expected = "Unknown";
        String actual = instanceEmptyConstructor.getGender();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetGenderGoodMale() {
        String expected = GOOD_GENDER_MALE;
        instanceEmptyConstructor.setGender(GOOD_GENDER_MALE);
        String actual = instanceEmptyConstructor.getGender();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetGenderGoodFemale() {
        String expected = GOOD_GENDER_FEMALE;
        instanceEmptyConstructor.setGender(GOOD_GENDER_FEMALE);
        String actual = instanceEmptyConstructor.getGender();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetGenderBadAlreadySet() {
        instanceEmptyConstructor.setGender(GOOD_GENDER_MALE);
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setGender(GOOD_GENDER_MALE), "The gender has already been"
                    + " set.");
    }
    
    @Test
    public void testSetGenderBadIllegalGender() {
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setGender("Batman"), "Please set gender as either"
                    + " male or female.");
    }
    
    @Test
    public void testGetAge() {
        int expected = 0;
        int actual = instanceEmptyConstructor.getAge();
        assertEquals(expected, actual);
    }
    
    @Test
    public void testSetAgeGood() {
        int expected = GOOD_AGE;
        instanceEmptyConstructor.setAge(GOOD_AGE);
        int actual = instanceEmptyConstructor.getAge();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetAgeGoodUpperLimit() {
        int expected = GOOD_AGE_UPPER_LIMIT;
        instanceEmptyConstructor.setAge(GOOD_AGE_UPPER_LIMIT);
        int actual = instanceEmptyConstructor.getAge();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetAgeGoodLowerLimit() {
        int expected = GOOD_AGE_LOWER_LIMIT;
        instanceEmptyConstructor.setAge(GOOD_AGE_LOWER_LIMIT);
        int actual = instanceEmptyConstructor.getAge();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetAgeBadBelowLimit() {
       assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setAge(BAD_LOWER_LIMIT), "Invalid Animal age.");
    }
    
    @Test
    public void testSetAgeBadUpperLimit() {
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setAge(BAD_UPPER_LIMIT), "Invalid Animal age.");
    }
    
    @Test
    public void testGetFixed() {
        boolean expected = false;
        boolean actual = instanceEmptyConstructor.getFixed();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetFixedGoodTrue() {
        boolean expected = GOOD_FIXED_TRUE;
        instanceEmptyConstructor.setFixed(GOOD_FIXED_TRUE);
        boolean actual = instanceEmptyConstructor.getFixed();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetFixedGoodFalse() {
        boolean expected = GOOD_FIXED_FALSE;
        instanceEmptyConstructor.setFixed(GOOD_FIXED_FALSE);
        boolean actual = instanceEmptyConstructor.getFixed();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetFixedBadAlreadyFixedTrue() {
        instanceEmptyConstructor.setFixed(GOOD_FIXED_TRUE);
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setFixed(GOOD_FIXED_TRUE), "Animal is already fixed.");
    }
    
    @Test
    public void testSetFixedBadAlreadyFixedFalse() {
        instanceEmptyConstructor.setFixed(GOOD_FIXED_TRUE);
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setFixed(GOOD_FIXED_FALSE), "Animal is already fixed.");
    }
    
    @Test
    public void testGetLegs() {
        int expected = 4;
        int actual = instanceEmptyConstructor.getLegs();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetLegsGood() {
        int expected = GOOD_LEGS;
        instanceEmptyConstructor.setLegs(GOOD_LEGS);
        int actual = instanceEmptyConstructor.getLegs();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetLegsGoodLowerLimit(){
        int expected = GOOD_LEGS_LOWER_LIMIT;
        instanceEmptyConstructor.setLegs(GOOD_LEGS_LOWER_LIMIT);
        int actual = instanceEmptyConstructor.getLegs();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetLegsGoodUpperLimit() {
        int expected = GOOD_LEGS_UPPER_LIMIT;
        instanceEmptyConstructor.setLegs(GOOD_LEGS_UPPER_LIMIT);
        int actual = instanceEmptyConstructor.getLegs();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetLegsBadLowerLimit() {
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setLegs(BAD_LEGS_LOWER_LIMIT), "Legs are only "
                    + "allowed to be between 0 and 4 limbs.");
    }
    
    @Test
    public void testSetLegsBadUpperLimit() {
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setLegs(BAD_LEGS_UPPER_LIMIT), "Legs are only "
                    + "allowed to be between 0 and 4 limbs.");
    }
    
    @Test
    public void testGetWeight() {
        BigDecimal expected = new BigDecimal(0);
        BigDecimal actual = instanceEmptyConstructor.getWeight();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetWeightGood() {
        BigDecimal expected = GOOD_WEIGHT;
        instanceEmptyConstructor.setWeight(GOOD_WEIGHT);
        BigDecimal actual = instanceEmptyConstructor.getWeight();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetWeightGoodUpperLimit() {
        BigDecimal expected = GOOD_WEIGHT_UPPER_LIMIT;
        instanceEmptyConstructor.setWeight(GOOD_WEIGHT_UPPER_LIMIT);
        BigDecimal actual = instanceEmptyConstructor.getWeight();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetWeightGoodLowerLimit() {
        BigDecimal expected = GOOD_WEIGHT_LOWER_LIMIT;
        instanceEmptyConstructor.setWeight(GOOD_WEIGHT_LOWER_LIMIT);
        BigDecimal actual = instanceEmptyConstructor.getWeight();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetWeightBadUpperLimit() {
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setWeight(BAD_WEIGHT_UPPER_LIMIT), "Invalid "
                + "weight. Acceptable weight ranges "
                + "include 0.0-1000.0.");
    }
    
    @Test
    public void testSetWeightBadLowerLimit() {
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setWeight(BAD_WEIGHT_LOWER_LIMIT), "Invalid "
                + "weight. Acceptable weight ranges "
                + "include 0.0-1000.0.");
    }
    
    @Test
    public void testGetDateAdded() {
        LocalDate expected = LocalDate.now(); 
        LocalDate actual = instanceEmptyConstructor.getDateAdded();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetDateAddedGood() {
        LocalDate expected = GOOD_DATE_ADDED;
        instanceEmptyConstructor.setDateAdded(expected);
        LocalDate actual = instanceEmptyConstructor.getDateAdded();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetDateAddedGoodLowerLimit()  {
        LocalDate expected = GOOD_DATE_ADDED_LOWER_LIMIT;
        instanceEmptyConstructor.setDateAdded(expected);
        LocalDate actual = instanceEmptyConstructor.getDateAdded();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetDateAddedGoodUpperLimit() {
        LocalDate expected = GOOD_DATE_ADDED_UPPER_LIMIT;
        instanceEmptyConstructor.setDateAdded(expected);
        LocalDate actual = instanceEmptyConstructor.getDateAdded();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetDateAddedBadLowerLimit() {
        instanceEmptyConstructor.setDateAdded(GOOD_DATE_ADDED_UPPER_LIMIT);
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setDateAdded(BAD_DATE_ADDED_LOWER_LIMIT), 
                BAD_DATE_ADDED_LOWER_LIMIT + " is more than "
                        + "one week in the past.");
    }
    
    @Test
    public void testSetDateAddedBadUpperLimit() {
        instanceEmptyConstructor.setDateAdded(GOOD_DATE_ADDED_UPPER_LIMIT);
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setDateAdded(BAD_DATE_ADDED_UPPER_LIMIT), 
                BAD_DATE_ADDED_UPPER_LIMIT + " is a date in the "
                    + "future.");
    }
    
    @Test
    public void testGetLastFeedingTime() {
        LocalDateTime expected = LocalDateTime.of(2020, 10, 1, 23, 59);
        LocalDateTime actual = instanceEmptyConstructor.getLastFeedingTime();
        assertEquals(expected,actual);
    }

    @Test
    public void testSetLastFeedingTimeGood() {
        LocalDateTime expected = GOOD_LAST_FEEDING_TIME;
        instanceEmptyConstructor.setLastFeedingTime(GOOD_LAST_FEEDING_TIME);
        LocalDateTime actual = instanceEmptyConstructor.getLastFeedingTime();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testSetLastFeedingTimeBadUpperLimit() {
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setLastFeedingTime(BAD_LAST_FEEDING_TIME_UPPER_LIMIT), 
                BAD_LAST_FEEDING_TIME_UPPER_LIMIT + " cannot be "
                    + "in the future.");
    }
    
    @Test
    public void testSetLastFeedingTimeBadLowerLimit() {
        assertThrows(IllegalArgumentException.class, () ->
        instanceEmptyConstructor.setLastFeedingTime(BAD_LAST_FEEDING_TIME_LOWER_LIMIT), 
                BAD_LAST_FEEDING_TIME_LOWER_LIMIT + " cannot be more than"
                    + " two days in the past.");
    }
    
    @Test
    public void testToString() {
        String expected = "Animal {name: Unknown, species: Unknown"
                + ", gender: Unknown, age: 0, weight: 0}";
        String actual = instanceEmptyConstructor.toString();
        assertEquals(expected,actual);
    }
    
    @Test
    public void testCompareTo() {
        Animal instanceDogOne = new Animal(GOOD_ID_ONE, GOOD_NAME_ONE, 
                GOOD_SPECIES_DOG, GOOD_GENDER_MALE, GOOD_AGE, GOOD_FIXED_TRUE, 
                GOOD_LEGS, GOOD_WEIGHT, GOOD_DATE_ADDED, 
                GOOD_LAST_FEEDING_TIME);
        Animal instanceDogTwo = new Animal(GOOD_ID_TWO, GOOD_NAME_TWO,
                GOOD_SPECIES_DOG,GOOD_GENDER_MALE, GOOD_AGE, GOOD_FIXED_TRUE, 
                GOOD_LEGS, GOOD_WEIGHT, GOOD_DATE_ADDED, 
                GOOD_LAST_FEEDING_TIME);
        boolean expected = true;
        boolean actual = instanceDogOne.compareTo(
                instanceDogTwo) < 0;
        assertEquals(expected,actual);
    }
}
