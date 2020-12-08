package com.shirley.animal.data;

import com.shirley.animal.Animal;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Chantal Shirley
 */
public class AnimalDAOMySQL {

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
            String name;
            String species;
            String gender;
            int age;
            String copyFixed;
            Boolean fixed;
            int legs;
            BigDecimal weight;
            LocalDate dateAdded;
            LocalDateTime lastFeedingTime;
            
            if(resultSet.next()){
                name = resultSet.getString("name");
                species = resultSet.getString("species");
                gender = resultSet.getString("gender");
                age = resultSet.getInt("age");
                copyFixed = resultSet.getString("fixed");
                if (copyFixed.equalsIgnoreCase("Yes")) {
                    fixed = true;
                }
                else {
                    fixed = false;
                }
                legs = resultSet.getInt("legs");
                weight = new BigDecimal (resultSet.getDouble("weight"));
                dateAdded = (LocalDate) resultSet.getObject("dateAdded");
                lastFeedingTime = (LocalDateTime)resultSet.getObject(
                        "lastFeedingTime");

                animal = new Animal(id, name, species, gender, 
                        age, fixed, legs, weight, 
                        dateAdded, lastFeedingTime);
            }
            callableStatement.close();
            conn.close();

        } catch(SQLException ex){
            throw new AnimalDataException(ex);
        }
        
        return animal;
    }

}
