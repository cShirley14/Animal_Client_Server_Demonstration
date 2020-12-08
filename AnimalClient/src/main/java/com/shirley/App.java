package com.shirley;

import com.shirley.animal.Animal;
import com.shirley.ui.Languages;
import com.shirley.ui.MainFrame;
import java.io.IOException;
import java.net.UnknownHostException;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chantal Shirley
 */
public class App {

    private static final int PORT = 49999;
    private static final String HOST_NAME = "localhost";
    
    private static Animal getAnimalFromServer(String name) throws
            UnknownHostException, IOException {
        Animal a = null;
            
        
        return a;
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
