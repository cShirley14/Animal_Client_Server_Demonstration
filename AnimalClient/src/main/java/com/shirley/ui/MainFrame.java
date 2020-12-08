package com.shirley.ui;

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
        setMinimumSize(new Dimension(800, 400));
        setSize(1000,800);
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
                if (formEvent.getAnimalName().isEmpty()) {
                    JOptionPane.showMessageDialog(rootPane, messages.getString(
                    "name-required"));
                } else {
                    String name = formEvent.getAnimalName();
                    
                    // Connect w/ server
                }
            }
        });
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
