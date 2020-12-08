package com.shirley.animal.data;

import com.shirley.animal.Animal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Chantal Shirley
 */
public class AnimalDAOMySQL {

    private static ArrayList<Animal> animals;

    private Connection buildConnection() throws SQLException {
        String databaseUrl = "localhost";
        String databasePort = "3306";
        String databaseName = "animals";
        String userName = "root";
        String password = "root";
        String connectionString = "jdbc:mysql://" + databaseUrl + ":"
                + databasePort + "/" + databaseName + "?"
                + "user=" + userName + "&"
                + "password=" + password + "&"
                + "useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(connectionString);
    }

    public Animal getAnimalById(String id) throws AnimalDataException {
        Animal animal = null;
        
        // Use this code if you want to directly look the animal up from a database query
        try{
            Connection conn = buildConnection();
            // ? are numbered 1,2,3 etc. they don't start at 0 like arrays
            CallableStatement callableStatement
                    = conn.prepareCall("CALL sp_get_animal_by_id(?);");
            callableStatement.setString(1, id);

            ResultSet resultSet = callableStatement.executeQuery();
            String species;
            String name;
            int age;
            if(resultSet.next()){
                species = resultSet.getString("Species");
                name = resultSet.getString("Name");
                age = resultSet.getInt("Age");
                //animal = new Animal(id, species, name, age);
            }
            callableStatement.close();
            conn.close();

        } catch(SQLException ex){
            throw new AnimalDataException(ex);
        }
        
        return animal;
    }

}
