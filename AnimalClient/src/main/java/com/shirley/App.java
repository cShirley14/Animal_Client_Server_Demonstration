package com.shirley;

import com.shirley.animal.Animal;
import com.shirley.ui.Languages;
import com.shirley.ui.MainFrame;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chantal Shirley
 */
public class App {

    private static final int PORT = 49999;
    private static final String HOST_NAME = "localhost";
    
    private static String getAnimalFromServer(String name) throws
            UnknownHostException, IOException {
        String animalData = null;
        
        Socket socket = new Socket(HOST_NAME, PORT);
        // Get info from form submit
        /*
        DataOutputStream outputStream = new DataOutputStream(
        socket.getOutputStream());
        
        outputStream.writeString(animalName);
        outputStream.flush();
        string animal = inputStream.readString();
        outputStream.close();
        
        // read XML code into hashmap 
        // convert hashmap data into readable text
        
        */
        return animalData;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Languages.setResourceBundle(Languages.LanguageOptions.USA);
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainFrame();
            }
        });
    }
    
}
