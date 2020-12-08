/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shirley.ui.leftform;

import java.util.EventObject;

/**
 *
 * @author Chantal Shirley
 */
public class FormEvent extends EventObject {
    private String animalName;
    
    public FormEvent(Object source, String animalName) {
        super(source);
        this.animalName = animalName;
    }
    
    public String getAnimalName() {
        return animalName;
    }
    
    public void setAnimalName(String animalName) {
        this.animalName = animalName;
    }
}
