package org.example.Pages;

import org.example.Classes.Actor;
import org.example.Classes.Role;
import org.example.Classes.Series;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class UpdateRolePage extends JFrame implements ActionListener {
    JFrame frame;
    JButton submitButton;
    TextField nameTextField;
    JComboBox<String> comboBox;
    Actor actor;
    int index;
    public UpdateRolePage (String productionName, String productionType, Actor actor, int index){
        this.actor = actor;
        this.index = index;
        frame = new JFrame("IMDb - Update Role");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 180);
        frame.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
        frame.setIconImage(image.getImage());
        frame.setLayout(null);
        frame.setResizable(false);

        Label nameLabel = new Label("Name: ");
        nameLabel.setBounds(25, 10, 50, 30);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(nameLabel);

        if(productionName.isEmpty()){
            productionName = "New Production name";
        }
        nameTextField = new TextField(productionName);
        nameTextField.setBounds(85, 10, 200, 30);
        nameTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(nameTextField);

        Label performancesLabel = new Label("Type: ");
        performancesLabel.setBounds(25, 50, 50, 30);
        performancesLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(performancesLabel);

        comboBox = new JComboBox<>(new String[]{"Movie", "Series"});
        if(productionType.equals("Movie")){
            comboBox.setSelectedIndex(0);
        }
        if(productionType.equals("Series")){
            comboBox.setSelectedIndex(1);
        }
        if(productionType.isEmpty()){
            comboBox.setSelectedIndex(-1);
        }
        comboBox.setBounds(85, 50, 200, 30);
        comboBox.setSelectedIndex(0);
        frame.add(comboBox);


        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setBounds(25, 100, 300, 25);
        frame.add(submitButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== submitButton){
            String name;
            String type;
            name = nameTextField.getText();
            type = Objects.requireNonNull(comboBox.getSelectedItem()).toString();
            if(name.isEmpty()){
                JOptionPane.showMessageDialog(null, "Please enter a valid name!", "Error", JOptionPane.ERROR_MESSAGE);
                return;}


            actor.getPerformances().add(new Role(name, type));
            frame.dispose();
        }
    }
}
