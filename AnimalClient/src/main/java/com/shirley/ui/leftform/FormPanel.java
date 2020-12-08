package com.shirley.ui.leftform;

import com.shirley.ui.Languages;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author Chantal Shirley
 */
public class FormPanel extends JPanel {
    private JLabel find_label;
    private JLabel search_label;
    private JTextField find_field;
    private JButton find_button;
    private JButton lookup_button;
    private JButton exit_button;
    private FormListener formListener;
    private ResourceBundle messages = Languages.getResourceBundle();

    public FormPanel() {
        Dimension size = getPreferredSize();
        size.width = 300;
        setPreferredSize(size);
        
        Border inner_border = BorderFactory.createTitledBorder(
                messages.getString("title"));
        Border outer_border = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outer_border, 
                inner_border));
        
        find_label = new JLabel(messages.getString("find-animal"));
        find_field = new JTextField(10);
        find_button = new JButton(messages.getString("search"));
        
        search_label = new JLabel(messages.getString("search-history"));
        lookup_button = new JButton(messages.getString("lookup-history"));
        
        exit_button = new JButton(messages.getString("exit"));
        
        find_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve animal information
                String animalName = find_field.getText();
                
                FormEvent event = new FormEvent(this, animalName);
                
                if (formListener != null) {
                    formListener.formSubmit(event);
                }
            }
        });
        
        lookup_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Request XML doc from server
            }
        });
        
        exit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int action = JOptionPane.showConfirmDialog(FormPanel.this, 
                messages.getString("quit-question"),
                messages.getString("confirm-exit"), JOptionPane.OK_CANCEL_OPTION);
                if(action == JOptionPane.OK_OPTION){
                    System.exit(0); // or e.getWindow().dispose();
                }
                else if (action == JOptionPane.CANCEL_OPTION) {
                    // Return back to FormPanel
                    JOptionPane.getFrameForComponent(FormPanel.this);
                }
            }
        });
        
        layoutComponents();
    }
    
        public void setListener(FormListener formListener) {
        this.formListener = formListener;
    }
    
    public void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        
        gc.fill = GridBagConstraints.HORIZONTAL;
        
        /******** First row ********/
        gc.weightx = 1;
        gc.weighty = 0;
        
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(find_label, gc);
        
        gc.gridx = 1;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(find_field, gc);
        
        /******** second row ********/
        
        gc.gridwidth = 2;
        gc.gridx = 0;
        gc.gridy = 1;
        add(find_button, gc);
        
        /******** third row ********/
        gc.weightx = 1;
        gc.weighty = 1;
        
        gc.fill = 3;
        gc.gridx = 0;
        gc.gridy = 3;
        add(search_label, gc);
        gc.fill = 0;
        /******** fourth row ********/
        gc.weightx = 1;
        gc.weighty = 1;
        
        gc.anchor = GridBagConstraints.CENTER;
        //gc.gridwidth = 2;
        gc.gridx = 1;
        gc.gridy = 3;
        add(lookup_button, gc);
        
        /******** third row ********/
        gc.weightx = 1;
        gc.weighty = 1;
        
        gc.gridwidth = 2;
        gc.gridx = 0;
        gc.gridy = 4;
        add(exit_button, gc);
    }
        
}
