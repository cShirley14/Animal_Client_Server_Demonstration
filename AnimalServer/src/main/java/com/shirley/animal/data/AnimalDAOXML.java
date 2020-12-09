package com.shirley.animal.data;

import com.shirley.animal.Animal;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.LocalDate;
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
            // collection of Order Record nodes; the root node is irrelevant
            // for these purposes
            NodeList animalNodeList = 
                    document.getElementsByTagName("animal");
            /*
            Test function to ensure parsing is working correctly
            System.out.println(animalNodeList.getLength());
            */
            
            // Prepare to build a new list of Order Records based
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
    
    private static Animal buildAnimalFromNode(Node orderRecordNode) {
        // Prepare for Order Record object to be returned
        Animal animalRecord = null;
        // Fields to be held for instantiation of Order Record object
        String orderNumber = null;
        LocalDate orderDate = null;
        int vendorId = 0;
        DateTimeFormatter formatter
                        = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        // Creats a Node List based on the child nodes of the passed 
        // Order Record Node
        NodeList orderRecordNodeList = orderRecordNode.getChildNodes();
        
        // Loop through the child nodes in the Node List
        for (int i = 0; i < orderRecordNodeList.getLength(); i++) {
            // Capture each child node
            Node dataNode = orderRecordNodeList.item(i);
            // Determine if it is an instance of predetermined attributes
            if (dataNode instanceof Element) {
                Element dataElement = (Element)dataNode;
                switch (dataElement.getTagName()) {
                    case "orderNumber":
                        orderNumber = dataElement.getTextContent();
                        break;
                    case "orderDate":
                        String orderDateValue = dataElement.getTextContent();
                        orderDate = LocalDate.parse(orderDateValue, formatter);
                        break;
                    case "vendorId":
                        String vendorIdValue = dataElement.getTextContent();
                        vendorId = Integer.parseInt(vendorIdValue);
                        break;
                    default:
                        break;
                }
            }
        }
        // Instantiate new order record to be returned
        if ((orderNumber != null && orderDate != null) && vendorId != 0) {
            animalRecord = new OrderRecord(orderNumber, vendorId, orderDate);
        }
        return animalRecord;
    }
    
    private void saveToFile() throws OrderRecordException {
        try(FileOutputStream fos = new FileOutputStream(FILE_NAME)){
            DocumentBuilderFactory factory = 
                    DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            
            Element rootElement = document.createElement("orderRecords");
            document.appendChild(rootElement);
            
            for (OrderRecord currOrderRecord : orderRecords) {
                DocumentFragment orderRecordFragment = 
                        buildOrderRecordFragment(document, currOrderRecord);
                rootElement.appendChild(orderRecordFragment);
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
            throw new OrderRecordException(ex);
        }
    }
    
    private void verifyOrderRecordList() throws OrderRecordException {
        if (null == orderRecords) {
            readFromFile();
        }
    }

    @Override
    public void createOrderRecord(OrderRecord orderRecord) throws 
            OrderRecordException {
        // Verify that there are order records to read from
        verifyOrderRecordList();
        // Make sure that the Order Record being created does not already exit
        OrderRecord checkedOrderRecord = getOrderRecordByOrderNumber(
                orderRecord.getOrderNumber());
        // Throw an exception an Order Record was actually retrieved
        if(null != checkedOrderRecord) {
            throw new OrderRecordException("Order Records must be unique.");
        }
        // A unique Order Record ID has been created, and so it can be added
        orderRecords.add(orderRecord);
        saveToFile();
    }

    @Override
    public OrderRecord getOrderRecordByOrderNumber(String orderNumber) 
            throws OrderRecordException {
        verifyOrderRecordList();
        // Object which will be returned depending on the results found.
        OrderRecord matchedOrderRecord = null;
	// Attempt to retrieve an Order Record according to the passed Order #
        for (OrderRecord currOrderRecord : orderRecords) {
            // See if the order record matches the passed order number
            if(currOrderRecord.getOrderNumber().equals(orderNumber)){
                // The Order Number is a match so set it to object to be 
                // returned
                matchedOrderRecord = currOrderRecord;
                // Exit loop and return found value
                break;
            }
        }
        return matchedOrderRecord;
    }

    @Override
    public ArrayList<OrderRecord> getAllOrderRecords() throws 
            OrderRecordException {
        verifyOrderRecordList();
        return orderRecords;
    }

    @Override
    public void updateOrderRecord(OrderRecord original, OrderRecord updated) 
            throws OrderRecordException {
                verifyOrderRecordList();
        OrderRecord foundOrderRecord = null;
        // Attempt to find original Order Record to be updated
        for (OrderRecord currOrderRecord : orderRecords) {
            // See if the current Order Record matches the original
            if(currOrderRecord.equals(original)){
                // The Order Record objects are duplicates
                // Prepare found object to be updated
                foundOrderRecord = currOrderRecord;
                // Update is complete exit loop
                break;
            }
        }
        
        // See if an Order Record could not be found to be updated
        if(null == foundOrderRecord){
            // Order Record was not discovered
            // Notify user of this fact
            throw new OrderRecordException("Original Order Record could not "
                    + "be discovered.");
        }
        
        // If there are no errors, set all new values barring the primary key
        // and save to the file
        foundOrderRecord.setOrderDate(updated.getOrderDate());
        foundOrderRecord.setVendorId(updated.getVendorId());
        saveToFile();
    }

    @Override
    public void deleteOrderRecord(OrderRecord orderRecord) throws 
            OrderRecordException {
        deleteOrderRecord(orderRecord.getOrderNumber());
    }

    @Override
    public void deleteOrderRecord(String orderNumber) throws 
            OrderRecordException {
        verifyOrderRecordList();
        OrderRecord matchedOrderRecord = null;
        
        for (OrderRecord currOrderRecord : orderRecords) {
            if (currOrderRecord.getOrderNumber().equals(orderNumber)){
                matchedOrderRecord = currOrderRecord;
                break;
            }
        }
        if (null == matchedOrderRecord) {
            // Order Record was not found
            throw new OrderRecordException("Order Record could not be found "
                    + "for deletion.");
        }
        // Otherwise no errors were thrown and you can proceed with deletion
        orderRecords.remove(matchedOrderRecord);
        // Save new updated list to the XML file
        saveToFile();
    }

    private DocumentFragment buildOrderRecordFragment(Document document, 
            OrderRecord currOrderRecord) {
        DocumentFragment orderRecordFragment = 
                document.createDocumentFragment();
        Element orderRecordElement = document.createElement("orderRecord");
        
        
        Element orderNumberElement = document.createElement("orderNumber");
        orderNumberElement.setTextContent(currOrderRecord.getOrderNumber());
        orderRecordElement.appendChild(orderNumberElement);
        
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        Element orderDateElement = document.createElement("orderDate");
        orderDateElement.setTextContent(
                currOrderRecord.getOrderDate().format(formatter));
        orderRecordElement.appendChild(orderDateElement);
        
        Element vendorIdElement = document.createElement("vendorId");
        vendorIdElement.setTextContent(Integer.toString(
                currOrderRecord.getVendorId()));
        orderRecordElement.appendChild(vendorIdElement);
        orderRecordFragment.appendChild(orderRecordElement);
        
        return orderRecordFragment;
    }

    
}
