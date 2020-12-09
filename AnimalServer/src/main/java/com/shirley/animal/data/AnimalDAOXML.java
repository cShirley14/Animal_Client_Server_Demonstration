package com.shirley.animal.data;

import com.shirley.animal.Animal;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 *
 * @author Chantal Shirley
 */

public class AnimalDAOXML {
   
    private static final String FILE_NAME = "animal.xml";
    private static ArrayList<Animal> animals = new ArrayList<Animal>();
    private static ArrayList<Animal> animalLookups = new ArrayList<Animal>();
    
    private void readFromFile() throws AnimalDataException {
        try (InputStream inputStream = new FileInputStream(FILE_NAME)) {
            // Prepare to parse through the XML file
            DocumentBuilderFactory factory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            // Collect parsed data into a Document object
            Document document = builder.parse(inputStream);
            
            // Parse through the document object and retrieve an ordered 
            // collection of Animal nodes; the root node is irrelevant
            // for these purposes
            NodeList animalNodeList = 
                    document.getElementsByTagName("animal");
            /*
            Test function to ensure parsing is working correctly
            System.out.println(animalNodeList.getLength());
            */
            
            // Prepare to build a new list of Animals based
            // on the read in XML file
    
            animals = new ArrayList<>();
            for (int i = 0; i < animalNodeList.getLength(); i++) {
                Node currentAnimalNode = animalNodeList.item(i);
                animals.add(buildAnimalFromNode(currentAnimalNode));
            }
           
        } catch (Exception ex) {
            throw new AnimalDataException ("Could not read from the file "
                    + "because :\n" + ex.getMessage() );
        }
    }
    
    // Move to client side
    private static Animal buildAnimalFromNode(Node animalRecord) {
        // Prepare for Animal object to be returned
        Animal animalCopy = null;
        // Fields to be held for instantiation of Animal object
        String id = null;
        String name = null;
        String species = null;
        String gender = null;
        int age = 0;
        boolean fixed = false;
        int legs = 0;
        BigDecimal weight = null;
        LocalDate dateAdded = null;
        LocalDateTime lastFeedingTime = null;
        DateTimeFormatter formatter
                        = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        // Creats a Node List based on the child nodes of the passed 
        // Animal Node
        NodeList animalNodeList = animalRecord.getChildNodes();
        
        // Loop through the child nodes in the Node List
        for (int i = 0; i < animalNodeList.getLength(); i++) {
            // Capture each child node
            Node dataNode = animalNodeList.item(i);
            // Determine if it is an instance of predetermined attributes
            if (dataNode instanceof Element) {
                Element dataElement = (Element)dataNode;
                switch (dataElement.getTagName()) {
                    case "id":
                        id = dataElement.getTextContent();
                        break;
                    case "name":
                        name = dataElement.getTextContent();
                        break;
                    case "species":
                        species = dataElement.getTextContent();
                        break;
                    case "gender":
                        gender = dataElement.getTextContent();
                        break;
                    case "age":
                        String curAge = dataElement.getTextContent();
                        age = Integer.parseInt(curAge);
                        break;
                    case "fixed":
                        String curFixed = dataElement.getTextContent();
                        fixed = Boolean.parseBoolean(curFixed);
                        break;      
                    case "legs":
                        String curLegs = dataElement.getTextContent();
                        legs = Integer.parseInt(curLegs);
                        break;
                    case "weight":
                        String curWeight = dataElement.getTextContent();
                        weight = BigDecimal.valueOf(
                                Double.parseDouble(curWeight));
                        break;
                    case "dateAdded":
                        String dateAddedValue = dataElement.getTextContent();
                        dateAdded = LocalDate.parse(dateAddedValue, formatter);
                        break;
                    case "lastFeedingTime":
                        String curLastFeedingTime = 
                                dataElement.getTextContent();
                        lastFeedingTime = LocalDateTime.parse(
                                curLastFeedingTime, formatter);
                        break;                       
                    default:
                        break;
                }
            }
        }
        // Instantiate new Animal to be returned
        boolean flag  = false;
        if (!animals.isEmpty()) {
            for (Animal animal : animals) {
                if (animal.getId() == id) {
                    flag = true;
                }
            }
        }
        
        if (id != null && flag != true) {
            animalCopy = new Animal(id, name, species, gender, age,
            fixed, legs, weight, dateAdded, lastFeedingTime);
        }
        return animalCopy;
    }
    
    public void documentLookup(String id, String name, String species, String gender,
    int age, boolean fixed, int legs, BigDecimal weight, LocalDate dateAdded,
    LocalDateTime lastFeedingTime) throws AnimalDataException {
        // Check to see if animal exists already
        boolean reachedHere = false;
        if (animals.isEmpty()) {
            Animal newAnimal = new Animal(id, name, species, gender, age,
                    fixed, legs, weight, dateAdded, lastFeedingTime);
                    createAnimalRecord(newAnimal);
        } else {
            for (Animal animal : animals) {
                if (animal.getId().equals(id)) {
                    createAnimalRecord(animal);
                    reachedHere = true;
                }
            }
            if (reachedHere == false) {
                Animal newAnimal = new Animal(id, name, species, gender, age,
                    fixed, legs, weight, dateAdded, lastFeedingTime);
                    createAnimalRecord(newAnimal);
            }
        }
    }
    
    private void saveToFile() throws AnimalDataException {
        try(FileOutputStream fos = new FileOutputStream(FILE_NAME)){
            DocumentBuilderFactory factory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            
            Element rootElement = document.createElement("animals");
            document.appendChild(rootElement);
            
            
            if (!animals.isEmpty()) {
                // New animals added that have never been looked up
                for (Animal currAnimal : animals) {
                    DocumentFragment animalFragment = 
                            buildAnimalFragment(document, currAnimal);
                    rootElement.appendChild(animalFragment);
                }
            }
            if (!animalLookups.isEmpty()) {
                // Old animals with a lookup history
                for (Animal animal : animalLookups) {
                    DocumentFragment animalFragment = 
                            buildAnimalFragment(document, animal);
                    rootElement.appendChild(animalFragment);
                }
            }
            // Clear Lookup history for next time
            //animalLookups.clear();
            
            DOMSource source = new DOMSource(document);
            
            TransformerFactory transformerFactory = 
                    TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            // Inspiration derived from
            // https://stackoverflow.com/questions/
            // 139076/how-to-pretty-print-xml-from-java
            
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(
                    "{http://xml.apache.org/xslt}indent-amount", "4");
            
            
            transformer.transform(source, new StreamResult(fos));
            
        } catch (Exception ex) {
            throw new AnimalDataException(ex.getMessage());
        }
    }
    
    private void verifyAnimalList() throws AnimalDataException {
        if (null == animals) {
            readFromFile();
        }
    }

    public ArrayList<Animal> getAllAnimals() throws 
            AnimalDataException {
        verifyAnimalList();
        return animals;
    }
    
    public ArrayList<Animal> animalExists() {
        return animals;
    }
    
        public void createAnimalRecord(Animal animalRecord) throws 
            AnimalDataException {
            
            // Verify that there are animal records to read from
            verifyAnimalList();
            // Make sure that the Animal Record being created does not already exit
            Animal checkedAnimal = getAnimalById(
                    animalRecord.getId());

            // Throw an exception an Animal Record was actually retrieved
            if(null != checkedAnimal) {
                // Just add new node with different time stamp, no exception needed
                // Duplicates can be differentiated by time stamps
                animalLookups.add(animalRecord);

            } else {
                // A unique Animal Record ID has been created, and so it can be added
                animals.add(animalRecord);
            } 
            saveToFile();
        }

    public Animal getAnimalById(String id) 
            throws AnimalDataException {
        verifyAnimalList();
        // Object which will be returned depending on the results found.
        Animal matchedAnimalRecord = null;
	// Attempt to retrieve an Order Record according to the passed Order #
        for (Animal currAnimal : animals) {
            // See if the order record matches the passed order number
            if(currAnimal.getId().equals(id)){
                // The Order Number is a match so set it to object to be 
                // returned
                matchedAnimalRecord = currAnimal;
                // Exit loop and return found value
                break;
            }
        }
        return matchedAnimalRecord;
    }
    
        
    private DocumentFragment buildAnimalFragment(Document document, 
            Animal currAnimal) {
        System.out.println(currAnimal);
        DocumentFragment animalFragment = 
                document.createDocumentFragment();
        // Single Animal 
        Element animalElement = document.createElement("animal");

        // Id
        Element idElement = document.createElement("id");
        idElement.setTextContent(currAnimal.getId());
        animalElement.appendChild(idElement);
        
        // Name
        Element nameElement = document.createElement("name");
        nameElement.setTextContent(currAnimal.getName());
        animalElement.appendChild(nameElement);
        
        // Species
        Element speciesElement = document.createElement("species");
        speciesElement.setTextContent(currAnimal.getSpecies());
        animalElement.appendChild(speciesElement);
        
        // Gender
        Element genderElement = document.createElement("gender");
        genderElement.setTextContent(currAnimal.getGender());
        animalElement.appendChild(genderElement);
        
        // Age
        Element ageElement = document.createElement("age");
        ageElement.setTextContent(String.valueOf(currAnimal.getAge()));
        animalElement.appendChild(ageElement);
        
        // Fixed
        Element fixedElement = document.createElement("fixed");
        fixedElement.setTextContent(String.valueOf(currAnimal.getFixed()));
        animalElement.appendChild(fixedElement);
        
        // Legs
        Element legsElement = document.createElement("legs");
        legsElement.setTextContent(String.valueOf(currAnimal.getLegs()));
        animalElement.appendChild(legsElement);
        
        // Weight
        Element weightElement = document.createElement("weight");
        weightElement.setTextContent(String.valueOf(currAnimal.getWeight()));
        animalElement.appendChild(weightElement);
        
        // Date Added
        Element dateAddedElement = document.createElement("dateAdded");
        dateAddedElement.setTextContent(String.valueOf(currAnimal.getDateAdded()));
        animalElement.appendChild(dateAddedElement);
        
        // Last Feeding Time
        Element lastFeedingTimeElement = document.createElement("lastFeedingTime");
        lastFeedingTimeElement.setTextContent(String.valueOf(
                currAnimal.getLastFeedingTime()));
        animalElement.appendChild(lastFeedingTimeElement);
        
        // Date Animal Was Viewed
        Element dateViewedElement = document.createElement("dateViewed");
        dateViewedElement.setTextContent(String.valueOf(LocalDateTime.now()));
        animalElement.appendChild(dateViewedElement);
        
        animalFragment.appendChild(animalElement);
        
        return animalFragment;
    }

    
}
