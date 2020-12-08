package com.shirley.animal.data;

/**
 *
 * @author Chantal Shirley
 */
public class AnimalDAOFactory {
    private static final String DAO_SOURCE = "MYSQL";

    public static AnimalDAO getCarDAO(){
        AnimalDAO dao = null;
        switch(DAO_SOURCE){
            case "XML":
                dao = new AnimalDAOXML();
                break;
            case "MYSQL":
                dao = new AnimalDAOMySQL();
                break;
            default:
        }
        return dao;
    }

}
