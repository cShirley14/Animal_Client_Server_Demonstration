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
    private String id;
    
    public FormEvent(Object source, String id) {
        super(source);
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
}
