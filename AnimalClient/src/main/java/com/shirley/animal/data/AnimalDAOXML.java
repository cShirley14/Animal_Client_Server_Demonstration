package com.shirley.animal.data;

import com.shirley.animal.Animal;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Chantal Shirley
 */
public class AnimalDAOXML implements AnimalDAO {
    private static final String FILE_NAME = "animals.xml";
    private static ArrayList<Animal> animals;

    private void readFromFile() throws AnimalDataException {
        try (InputStream inputStream = new FileInputStream(FILE_NAME)) {
            DocumentBuilderFactory factory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);

            NodeList animalNodeList = document.getElementsByTagName("animal");
            //System.out.println(animalNodeList.getLength());
            animals = new ArrayList<>();
            for(int i = 0; i < animalNodeList.getLength(); i++) {
                Node currentAnimalNode = animalNodeList.item(i);
                animals.add(buildAnimalFromNode(currentAnimalNode));
            }
        } catch (Exception ex) {
            throw new AnimalDataException(ex);
            }
        }
    
    private static Animal buildAnimalFromNode(Node animalNode) {
    Animal newAnimal = new Animal();
    
    NamedNodeMap animalAttributeMap = animalNode.getAttributes();
    Attr attr = (Attr)animalAttributeMap.getNamedItem("animal-id");
    newAnimal.setId(attr.getValue());
    
    NodeList animalDataNodeList = animalNode.getChildNodes();
    for(int i = 0; i < animalDataNodeList.getLength(); i++) {
        Node dataNode = animalDataNodeList.item(i);
                if(dataNode instanceof Element) {
                    Element dataElement = (Element)dataNode;
                    switch(dataElement.getTagName()) {
                        case "species":
                            String speciesValue = dataElement.getTextContent();
                            newAnimal.setSpecies(speciesValue);
                            break;
                        case "name":
                            String nameValue = dataElement.getTextContent();
                            newAnimal.setName(nameValue);
                            break;
                        case "age":
                            int ageValue = Integer.parseInt(dataElement.getTextContent());
                            newAnimal.setAge(ageValue);
                            break;
                        default:
                            break;
                    }	
                }
    }
    return newAnimal;
}

    private void saveToFile() throws AnimalDataException {
    try(FileOutputStream fos = new FileOutputStream(FILE_NAME)) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
            
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();
        Element rootElement = document.createElement("animals");
        document.appendChild(rootElement);    
        
        	
        for(Animal currentAnimal: animals) {
            DocumentFragment animalFragment = buildAnimalFragment(document, currentAnimal);
            rootElement.appendChild(animalFragment);
        }
        
        DOMSource source = new DOMSource(document);
        
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        
        transformer.transform(source, new StreamResult(fos));
        
        } catch(Exception ex) {
            throw new AnimalDataException(ex);
        }
    }
    private static DocumentFragment buildAnimalFragment(Document document, Animal animal) {
    DocumentFragment animalFragment = document.createDocumentFragment();
        
    Element animalElement = document.createElement("animal");
    animalElement.setAttribute("animal-id", animal.getId());
    animalFragment.appendChild(animalElement);
    
    Element speciesElement = document.createElement("species");
    speciesElement.setTextContent(animal.getSpecies());
    animalElement.appendChild(speciesElement);
    
    Element nameElement = document.createElement("name");
    nameElement.setTextContent(animal.getName());
    animalElement.appendChild(nameElement);
	
    Element ageElement = document.createElement("age");
    ageElement.setTextContent(Integer.toString(animal.getAge()));	
    animalElement.appendChild(ageElement);
    
    return animalFragment;
    }
    
    private void verifyAnimalList() throws AnimalDataException {
        if(null == animals){
            readFromFile();
        }
    }

    @Override
    public void createAnimalRecord(Animal animal) throws AnimalDataException {
        verifyAnimalList();
        // Look to see if there is already a animal with the same id
        // value
        Animal checkAnimal = getAnimalById(animal.getId());
        // If there was a matching animal, throw an exception.  The id
        // is used as a unique identifier in this example.
        if(null != checkAnimal){
            throw new AnimalDataException("Ids must be unique.");
        }
        // No other animal has the same id, so we can add this Animal to
        // the data store.
        animals.add(animal);
        saveToFile();
    }

    @Override
    public Animal getAnimalById(String id) throws AnimalDataException {
        verifyAnimalList();
        Animal animalCopy = null;
        for (Animal animal : animals) {
            // See if the animal has a matching id
            if(animal.getId().equals(id)){
                // found a match, so it is the animal we want
                animalCopy = animal;
                // leave the loop
                break;
            }
        }
        return animalCopy;
    }

    @Override
    public ArrayList<Animal> getAllAnimals() throws AnimalDataException {
        verifyAnimalList();
        return animals;
    }

    @Override
    public void updateAnimal(Animal original, Animal updated) throws AnimalDataException {
        verifyAnimalList();
        Animal foundAnimal = null;
        for (Animal animal : animals) {
            if(animal.equals(original)){
                // found a match!
                foundAnimal = animal;
                break;
            }
        }
        if(null == foundAnimal){
            // did not find a match to the original!
            throw new AnimalDataException("Original record not found for update.");
        }
        // If no error, update all but the unique identifier
        foundAnimal.setSpecies(updated.getSpecies());
        foundAnimal.setName(updated.getName());
        foundAnimal.setAge(updated.getAge());
        saveToFile();
    }

    @Override
    public void deleteAnimal(Animal animal) throws AnimalDataException {
        deleteAnimal(animal.getId());
    }

    @Override
    public void deleteAnimal(String id) throws AnimalDataException {
        verifyAnimalList();
        Animal foundAnimal = null;
        for (Animal animal : animals) {
            if(animal.getId().equals(id)){
                foundAnimal = animal;
                break;
            }
        }
        if(null == foundAnimal){
            // did not find a match to the original!
            throw new AnimalDataException("Record not found for delete.");
        }
        // If no error, update all but the unique identifier
        animals.remove(foundAnimal);
        saveToFile();
    }
    
}
