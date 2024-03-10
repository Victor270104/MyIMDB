package org.example.Pages;

import org.example.Classes.Actor;
import org.example.Classes.IMDB;
import org.example.Classes.Role;
import org.example.Classes.Staff;
import org.example.Pages.UpdateRolePage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddActorPage extends JFrame implements ActionListener {
    JFrame frame;
    JButton nextButton;
    JTextField textField;
    JTextArea textArea;
    Staff staff;
    String name;
    public AddActorPage(Staff staff, String name){
        this.staff= staff;
        this.name=name;
        frame= new JFrame("IMDb - Add Actor");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 220);
        frame.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("C:\\Users\\vsand\\Desktop\\POO\\src\\main\\resources\\Images\\IMDB_Logo.png");
        frame.setIconImage(image.getImage());
        frame.setResizable(false);
        frame.setLayout(null);

        JLabel label1 = new JLabel("Biography:");
        label1.setBounds(10, 50, 80, 80);
        frame.add(label1);

        textArea = new JTextArea();
        textArea.setBounds(100, 50, 165, 80);
        frame.add(textArea);

        JLabel label2 = new JLabel("Number of performances:");
        label2.setBounds(10, 10, 80, 25);
        frame.add(label2);

        textField = new JTextField();
        textField.setBounds(100, 10, 165, 25);
        frame.add(textField);

        nextButton = new JButton("Submit");
        nextButton.setBounds(10, 150, 255, 25);
        nextButton.addActionListener(this);
        frame.add(nextButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==nextButton){
            String biography = textArea.getText();
            int numberOfPerformances;
            try {
                numberOfPerformances = Integer.parseInt(textField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            List<Role> roles = new ArrayList<>();
            Actor actor = new Actor(name, roles, biography);

            for(int i = numberOfPerformances-1; i >= 0; i--)
                new UpdateRolePage("","",actor,i);
            IMDB.getInstance().getActors().add(actor);
            staff.getAdded().add(actor);
            staff.experienceStrategy= staff.addIMBD;
            staff.experienceStrategy.addexp();
            frame.dispose();
        }
    }

}
