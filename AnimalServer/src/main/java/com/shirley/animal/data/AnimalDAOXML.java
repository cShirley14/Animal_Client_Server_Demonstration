package com.shirley.animal.data;

import com.shirley.animal.Animal;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private static ArrayList<Animal> animals;
    
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
        LocalDateTime lookupDate = null;
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
                    case "lookupDate":
                        String curLookupDate = 
                                dataElement.getTextContent();
                        lookupDate = LocalDateTime.parse(
                                curLookupDate, formatter);                        
                    default:
                        break;
                }
            }
        }
        // Instantiate new Animal to be returned
        if (id != null) {
            animalCopy = new Animal(id, name, species, gender, age,
            fixed, legs, weight, dateAdded, lastFeedingTime);
        }
        return animalCopy;
    }
    
    private void saveToFile() throws AnimalDataException {
        try(FileOutputStream fos = new FileOutputStream(FILE_NAME)){
            DocumentBuilderFactory factory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            
            Element rootElement = document.createElement("animals");
            document.appendChild(rootElement);
            
            for (Animal currAnimal : animals) {
                DocumentFragment animalFragment = 
                        buildAnimalFragment(document, currAnimal);
                rootElement.appendChild(animalFragment);
            }
            
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
            throw new AnimalDataException(ex);
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

    private DocumentFragment buildAnimalFragment(Document document, 
            Animal currAnimal) {
        DocumentFragment animalFragment = 
                document.createDocumentFragment();
        Element animalElement = document.createElement("animal");
        
        
        Element idElement = document.createElement("id");
        idElement.setTextContent(currAnimal.getId());
        animalElement.appendChild(idElement);
        
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        Element dateAddedElement = document.createElement("dateAdded");
        dateAddedElement.setTextContent(
                currAnimal.getDateAdded().format(formatter));
        animalElement.appendChild(dateAddedElement);
        
        animalFragment.appendChild(animalElement);
        
        return animalFragment;
    }

    
}
