package com.shirley.animal;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Chantal Shirley
 */
public class Animal implements Comparable<Animal> {
    private String id = "0";
    private static List<String> idList = new ArrayList<>();
    private String name = "Unknown";
    private String species = "Unknown";
    private String gender ="Unknown";
    private int age = 0;
    private boolean fixed = false;
    private int legs = 4;
    private BigDecimal weight = BigDecimal.valueOf(0);
    private LocalDate dateAdded;
    private LocalDateTime lastFeedingTime;

    public Animal(String id, String name, String species, String gender, 
            int age, boolean fixed, int legs, BigDecimal weight, 
            LocalDate dateAdded, LocalDateTime lastFeedingTime) {
        setId(id);
        setName(name);
        setSpecies(species);
        setGender(gender);
        setAge(age);
        setFixed(fixed);
        setLegs(legs);
        setWeight(weight);
        setDateAdded(dateAdded);
        setLastFeedingTime(lastFeedingTime);
    }
    
    public Animal() {
        id = "0";
        idList.add(id);
        name = "Unknown";
        species = "Unknown";
        gender = "Unknown";
        age = 0;
        fixed = false;
        legs = 4;
        weight = BigDecimal.valueOf(0);
        dateAdded = LocalDate.now(); 
        lastFeedingTime = LocalDateTime.of(2020, 10, 1, 23, 59); // October 1, 2020 at 11:59pm        
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        idValidator(id);
        idList.add(id);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        speciesValidator(species);
        this.species = species;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        genderValidator(gender);
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        ageValidator(age);
        this.age = age;
    }

    public boolean getFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        fixedValidator(fixed);
        this.fixed = fixed;
    }

    public int getLegs() {
        return legs;
    }

    public void setLegs(int legs) {
        legsValidator(legs);
        this.legs = legs;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        weightValidator(weight);
        this.weight = weight;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(LocalDate dateAdded) {
        dateValidator(dateAdded);
        this.dateAdded = dateAdded;
    }

    public LocalDateTime getLastFeedingTime() {
        return lastFeedingTime;
    }

    public void setLastFeedingTime(LocalDateTime lastFeedingTime) {
        feedingValidator(lastFeedingTime);
        this.lastFeedingTime = lastFeedingTime;
    }
    
    @Override
    public int compareTo(Animal other) {
        if (this.species.compareTo(other.species) != 0) {
            return this.species.compareTo(other.species);
        }
        else {
            return this.name.compareTo(other.name);
        }
    }
    
    private void idValidator(String id) {
        if (!this.id.equals("0")) {
            throw new IllegalArgumentException("You can't change an id "
                    + "that has already been set.");
        }
        if (idList.contains(id)) {
            throw new IllegalArgumentException("Id is already in "
                    + "the list of ids.");
        }
    }
    
    private void speciesValidator(String species) {
        if(!this.species.equals("Unknown")){
            throw new IllegalArgumentException("The species cannot be changed"
                    + "because it has already been set.");
        }
        else if (species.equalsIgnoreCase("cat") == false &&
                species.equalsIgnoreCase("dog") == false) {
            throw new IllegalArgumentException("The species must be a"
                    + " cat or dog.");
        }

    }

    private void genderValidator(String gender) {
        if (this.gender.equalsIgnoreCase("female") || 
                this.gender.equalsIgnoreCase("male")) {
            throw new IllegalArgumentException("The gender has already been"
                    + " set.");
        }
        if (gender.equalsIgnoreCase("male") == false &&
                gender.equalsIgnoreCase("female") == false) {
            throw new IllegalArgumentException("Please set gender as either"
                    + " male or female.");
        }
    }

    private void ageValidator(int age) {
        if (age > 100 || age < 0) {
            throw new IllegalArgumentException("Invalid Animal age.");
        }
    }

    private void fixedValidator(boolean fixed) {
        if (this.fixed == true) {
            throw new IllegalArgumentException("Animal is already fixed.");
        }
    }

    private void legsValidator(int legs) {
        if (legs > 4 || legs < 0)
        {
            throw new IllegalArgumentException("Legs are only "
                    + "allowed to be between 0 and 4 limbs.");
        }
    }

    private void weightValidator(BigDecimal weight) {
        BigDecimal max = new BigDecimal("1000.0");
        BigDecimal min = new BigDecimal("0.0");
        if (weight.compareTo(max) == 1 || weight.compareTo(min) == -1) {
            throw new IllegalArgumentException("Invalid weight. Acceptable "
                    + "weight ranges include 0.0-1000.0.");
        }
    }

    private void dateValidator(LocalDate dateAdded) {
        LocalDate oneWeekAgo = LocalDate.now().minusDays(7);
        if(dateAdded.isBefore(oneWeekAgo)){
            throw new IllegalArgumentException(dateAdded + " is more than"
                    + " one week in the past.");
        }
        else if(dateAdded.isAfter(LocalDate.now())){
            throw new IllegalArgumentException(dateAdded + " is a date in the "
                    + "future.");
        }
    }

    private void feedingValidator(LocalDateTime lastFeedingTime) {
        LocalDateTime twoDaysAgo = LocalDateTime.now().minusDays(2);
        if(lastFeedingTime.isBefore(twoDaysAgo)){
            throw new IllegalArgumentException(lastFeedingTime
                    + " cannot be more than"
                    + " two days in the past.");
        } else if (LocalDateTime.now().isBefore(lastFeedingTime)){
            throw new IllegalArgumentException(lastFeedingTime
                    + " cannot be "
                    + "in the future.");
        }
    }

    @Override
    public String toString() {
        return "Animal {name: " + name + ", species: " + species + ", gender: "
                + gender + ", age: " + age + ", weight: " + weight + "}";
    }
    
}
