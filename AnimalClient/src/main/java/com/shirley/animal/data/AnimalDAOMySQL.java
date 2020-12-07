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
public class AnimalDAOMySQL implements AnimalDAO {

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

    private void readFromDatabase() throws AnimalDataException {
        try (Connection connection = buildConnection()) {
            if (connection.isValid(2)) {
                animals = new ArrayList<>();
                //Statement statement = connection.createStatement();
                //ResultSet resultSet = statement.executeQuery("SELECT * FROM Animal;");
                CallableStatement callableStatement
                        = connection.prepareCall("CALL sp_get_all_Animals();");
                ResultSet resultSet = callableStatement.executeQuery();

                String id;
                String species;
                String name;
                int age;
                while (resultSet.next()) {
                    id = resultSet.getString("Id");
                    species = resultSet.getString("Species");
                    name = resultSet.getString("Name");
                    age = resultSet.getInt("Age");
                    //animals.add(new Animal(id, species, name, age));
                }

                resultSet.close();
                callableStatement.close();
                //statement.close();
            }
        } catch (Exception exception) {
            System.out.println("Exception message: " + exception.getMessage());
            if (exception instanceof SQLException) {
                SQLException sqlException = (SQLException) exception;
                System.out.println("Error Code: " + sqlException.getErrorCode());
                System.out.println("SQL State: " + sqlException.getSQLState());
            }
        }
    }

    private void verifyAnimalList() throws AnimalDataException {
        if (null == animals) {
            readFromDatabase();
        }
    }

    @Override
    public void createAnimalRecord(Animal animal) throws AnimalDataException {
        // Verifies that the animal isn't in the ArrayList before adding it
        verifyAnimalList();
        Animal checkAnimal = getAnimalById(animal.getId());
        if(null != checkAnimal){
            throw new AnimalDataException("Ids must be unique.");
        }
        animals.add(animal);
        // Creates new animal record in the database
        try{
            Connection conn = buildConnection();
            CallableStatement callableStatement
                    = conn.prepareCall("CALL sp_add_animal(?,?,?,?);");
            callableStatement.setString(1, animal.getId());
            callableStatement.setString(2, animal.getSpecies());
            callableStatement.setString(3, animal.getName());
            callableStatement.setInt(4, animal.getAge());

            callableStatement.execute();
            callableStatement.close();
            conn.close();

        } catch(SQLException ex){
            throw new AnimalDataException(ex);
        }
    }

    @Override
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

    @Override
    public ArrayList<Animal> getAllAnimals() throws AnimalDataException {
        verifyAnimalList();
        return animals;
    }

    @Override
    public void updateAnimal(Animal original, Animal updated) throws AnimalDataException {
        // Verify that the original animal is in the ArrayList before updating it
        verifyAnimalList();
        Animal foundAnimal = null;
        for (Animal animal : animals) {
            if(animal.equals(original)){
                foundAnimal = animal;
                break;
            }
        }
        if(null == foundAnimal){
            throw new AnimalDataException("Original record not found for update.");
        }
        foundAnimal.setSpecies(updated.getSpecies());
        foundAnimal.setName(updated.getName());
        foundAnimal.setAge(updated.getAge());
        // Update the animal in the database
        try{
            Connection conn = buildConnection();
            CallableStatement callableStatement
                    = conn.prepareCall("CALL sp_update_Animal(?,?,?,?,?);");
            callableStatement.setString(1, original.getId());
            callableStatement.setString(2, updated.getId());
            callableStatement.setString(3, updated.getSpecies());
            callableStatement.setString(4, updated.getName());
            callableStatement.setInt(5, updated.getAge());

            callableStatement.execute();
            callableStatement.close();
            conn.close();

        } catch(SQLException ex){
            throw new AnimalDataException(ex);
        }
    }

    @Override
    public void deleteAnimal(Animal animal) throws AnimalDataException {
        deleteAnimal(animal.getId());
    }

    @Override
    public void deleteAnimal(String id) throws AnimalDataException {
        // Verify that the animal is in the ArrayList before removing it
        verifyAnimalList();
        Animal foundAnimal = null;
        for (Animal animal : animals) {
            if(animal.getId().equals(id)){
                foundAnimal = animal;
                break;
            }
        }
        if(null == foundAnimal){
            throw new AnimalDataException("Record not found for delete.");
        }
        String idToDelete = foundAnimal.getId();
        animals.remove(foundAnimal);
        // Delete the animal from the database
        try{
            Connection conn = buildConnection();
            CallableStatement callableStatement
                    = conn.prepareCall("CALL sp_delete_from_Animal(?);");
            callableStatement.setString(1, idToDelete);
            callableStatement.execute();
            callableStatement.close();
            conn.close();

        } catch(SQLException ex){
            throw new AnimalDataException(ex);
        }
    }

}
