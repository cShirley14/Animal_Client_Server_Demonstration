package com.shirley.animal.data;

import com.shirley.animal.Animal;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
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
    private AnimalDAOXML daoXML = new AnimalDAOXML();
    private Connection buildConnection() throws SQLException {
        String databaseUrl = "localhost";
        String databasePort = "3306";
        String databaseName = "animal";
        String userName = "";
        String password = "";
        String connectionString = "jdbc:mysql://" + databaseUrl + ":"
                + databasePort + "/" + databaseName + "?"
                + "user=" + userName + "&"
                + "password=" + password + "&"
                + "useSSL=false&serverTimezone=UTC";
        return DriverManager.getConnection(connectionString);
    }

    public String getAnimalById(String id) throws AnimalDataException {
        String animalData = null;
        
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
                
                dateAdded = resultSet.getDate("dateAdded").toLocalDate();
                
                lastFeedingTime = resultSet.getTimestamp(
                        "lastFeedingTime").toLocalDateTime();
                
                animalData = "\nAnimal Record:\nName: " + name + "\nSpecies: " 
                        + species + "\nGender: " +gender + "\nAge: " + age 
                        + "\nFixed: " + fixed + "\nNumber of Legs:" + legs +
                        "\nWeight: " + weight + "\nDate Added: " + dateAdded 
                        + "\nLast Feeding Time: " + lastFeedingTime + "\n"
                        + "\t-------------------";
                daoXML.documentLookup(id, name, species, gender, age, fixed, legs,
                        weight, dateAdded, lastFeedingTime);
            }
            callableStatement.close();
            conn.close();

        } catch(SQLException ex){
            System.out.println(ex.getMessage());
            throw new AnimalDataException(ex.getMessage());
        }
        
        return animalData;
    }

}
