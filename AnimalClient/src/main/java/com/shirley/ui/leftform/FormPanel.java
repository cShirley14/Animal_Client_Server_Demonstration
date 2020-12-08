package com.shirley.ui.leftform;

import com.shirley.ui.Languages;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author Chantal Shirley
 */
public class FormPanel extends JPanel {
    private JLabel title_label;
    private JLabel find_label;
    private JTextField find_textfield;
    private JButton lookup_button;
    private JButton exit_button;
    private FormListener formListener;
    private ResourceBundle messages = Languages.getResourceBundle();

    public FormPanel() {
        Dimension size = getPreferredSize();
        size.width = 500;
        setPreferredSize(size);
        
        Border inner_border = BorderFactory.createTitledBorder(
                messages.getString("title"));
        Border outer_border = BorderFactory.createEmptyBorder(5,5,5,5);
        setBorder(BorderFactory.createCompoundBorder(outer_border, 
                inner_border));
    }
    
}
