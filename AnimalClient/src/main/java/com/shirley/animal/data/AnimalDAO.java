/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shirley.animal.data;

import com.shirley.animal.Animal;
import java.util.ArrayList;

/**
 *
 * @author Chantal Shirley
 */
public interface AnimalDAO {
    /**
     * Creates a new Animal record based on the values in the supplied Animal.
     *
     * @param animal
     * @throws com.shirley.animal.data.AnimalDataException
     */
    void createAnimalRecord(Animal animal) throws AnimalDataException;

    /**
     * Returns the Animal record associated with the id or null if there
     * is no such Animal.
     *
     * @param id the identifier of the desired Animal record
     * @return the associated Animal or null if not found
     * @throws com.shirley.animal.data.AnimalDataException
     */
    Animal getAnimalById(String id) throws AnimalDataException;

    /**
     * Returns a list of all the current Animal records.
     *
     * @return list of Animal records
     * @throws AnimalDataException
     */
    ArrayList<Animal> getAllAnimals() throws AnimalDataException;

    /**
     * Looks for the original Animal and updates 
     * it to match the updated Animal.If the original Animal cannot be found, 
     * the method will throw an Exception.
     *
     * @param original The Animal record to be updated
     * @param updated The Animal containing the updated values
     * @throws com.shirley.animal.data.AnimalDataException
     */
    void updateAnimal(Animal original, Animal updated) 
            throws AnimalDataException;

    /**
     * Permanently deletes the supplied Animal record
     *
     * @param animal the Animal to delete
     * @throws com.shirley.animal.data.AnimalDataException
     */
    void deleteAnimal(Animal animal) throws AnimalDataException;

    /**
     * Permanently deletes the Animal record with the supplied id value.
     *
     * @param id the unique identifier for the Animal to be deleted
     * @throws AnimalDataException
     */
    void deleteAnimal(String id) throws AnimalDataException;
}
