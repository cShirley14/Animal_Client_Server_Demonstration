/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shirley.taskhandler;

import com.shirley.animal.Animal;
import com.shirley.animal.data.AnimalDAOMySQL;
import com.shirley.animal.data.AnimalDataException;

/**
 *
 * @author Chantal Shirley
 */
public class FindAnimal {
    public String getAnimalByAnimalId(String id) throws AnimalDataException {
        AnimalDAOMySQL dao = new AnimalDAOMySQL();
        return dao.getAnimalById(id);
    }
}
