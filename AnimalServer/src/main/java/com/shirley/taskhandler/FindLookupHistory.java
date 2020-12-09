/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shirley.taskhandler;

import com.shirley.animal.data.AnimalDAOXML;
import com.shirley.animal.data.AnimalDataException;

/**
 *
 * @author Chantal Shirley
 */
public class FindLookupHistory {
    private AnimalDAOXML dao = new AnimalDAOXML(); 
    public String getHistory() throws AnimalDataException {
        String result = null;
        result = dao.getXMLFile();
        return result;
    }
    
}
