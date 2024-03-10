package org.example.Pages;

import org.example.Classes.Staff;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddDeleteUserPage extends JFrame {

    public AddDeleteUserPage (Staff staff){
        String[] options = {"Add", "Delete"};
        int option = JOptionPane.showOptionDialog(null, "What do you want to do?", "To add or not to add - That is the question!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if(option == 0) {
            new AddUserPage();
        }
        if(option == 1) {
            new DeleteUserPage(staff);
        }
    }

}
