package com.shirley;

import com.shirley.ui.MainFrame;
import com.shirley.ui.Languages;
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
