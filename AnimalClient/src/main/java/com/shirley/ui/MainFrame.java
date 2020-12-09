package com.shirley.ui;

import com.shirley.client.ClientConnection;
import com.shirley.jsonbuilder.JSONArrayBuilder;
import com.shirley.ui.leftform.FormEvent;
import com.shirley.ui.leftform.FormListener;
import com.shirley.ui.leftform.FormPanel;
import com.shirley.ui.rightform.TextPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ResourceBundle;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Chantal Shirley
 */
public class MainFrame extends JFrame {
    private ResourceBundle messages = Languages.getResourceBundle();
    private TextPanel textPanel;
    private FormPanel formPanel;
    
     public MainFrame() {
        super();
        setTitle(messages.getString("title"));
        setMinimumSize(new Dimension(800, 500));
        setSize(800,500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
           @Override
           public void windowClosing(WindowEvent e) {
               closeWindow();
           }
        });
        
        setLayout(new BorderLayout());
        
        formPanel = new FormPanel();
        add(formPanel, BorderLayout.WEST);
        formPanel.setListener(new FormListener() {
            @Override
            public void formSubmit(FormEvent formEvent) {
                if (formEvent.getId().isEmpty()) {
                    JOptionPane.showMessageDialog(rootPane, messages.getString(
                    "id-required"));
                } else {
                    String id = formEvent.getId();
                    // Connect w/ server
                    try {
                        ClientConnection cc = new ClientConnection();
                        // COnstruct JSON object
                        JSONArrayBuilder jBuilder = new JSONArrayBuilder();
                        String jsonArray = jBuilder.JSONGenerator(id);
                        System.out.println(jsonArray);
                        String animalInformation = 
                                cc.getAnimalFromServer(jsonArray);
                        textPanel.appendText(animalInformation);
                    }
                    catch (Exception ex) {
                        JOptionPane.showMessageDialog(MainFrame.this, 
                                    ex.getLocalizedMessage(),
                                    messages.getString("animal-not-found"),
                                    JOptionPane.ERROR_MESSAGE);
                    }
                    
                }
            }
        });
        
        textPanel = new TextPanel();
        add(textPanel, BorderLayout.CENTER);
    
        setVisible(true);
        
     }
     
     private void closeWindow() {
        int action = JOptionPane.showConfirmDialog(MainFrame.this, 
            messages.getString("quit-question"),
            messages.getString("confirm-exit"), JOptionPane.OK_CANCEL_OPTION);
        if(action == JOptionPane.OK_OPTION){
            System.exit(0); 
        }
        else if (action == JOptionPane.CANCEL_OPTION) {
            // Return back to Main Frame
            JOptionPane.getFrameForComponent(MainFrame.this);
        }
    }
    
}
