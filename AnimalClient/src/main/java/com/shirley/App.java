package com.shirley;

import com.shirley.ui.MainFrame;
import com.shirley.animal.Animal;
import com.shirley.ui.Languages;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.SwingUtilities;

/**
 *
 * @author Chantal Shirley
 */
public class App {
    
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
