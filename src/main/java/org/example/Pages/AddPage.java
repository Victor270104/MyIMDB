package org.example.Pages;

import org.example.Classes.Staff;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPage extends JFrame implements ActionListener {

    JFrame frame;
    JButton nextButton;
    JComboBox<String> comboBox;
    JTextField textField;
    Staff staff;
    public AddPage(Staff staff) {
        this.staff = staff;
        frame = new JFrame("IMDb - Add");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
        frame.setIconImage(image.getImage());
        frame.setResizable(false);
        frame.setLayout(null);

        JLabel label1 = new JLabel("Name:");
        label1.setBounds(10, 20, 80, 25);
        frame.add(label1);

        JLabel label2 = new JLabel("Type:");
        label2.setBounds(10, 50, 80, 25);
        frame.add(label2);

        textField = new JTextField();
        textField.setBounds(100, 20, 165, 25);
        frame.add(textField);

        comboBox = new JComboBox<>();
        comboBox.setBounds(100, 50, 165, 25);
        comboBox.addItem("Actor");
        comboBox.addItem("Movie");
        comboBox.addItem("Series");
        frame.add(comboBox);

        nextButton = new JButton("Next");
        nextButton.setBounds(10, 80, 255, 25);
        nextButton.addActionListener(this);
        frame.add(nextButton);

        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == nextButton) {
            String name = textField.getText();
            String type = comboBox.getSelectedItem().toString();
            if(type.equals("Actor")) {
                new AddActorPage(staff, name);
            }
            if(type.equals("Movie")) {
                new AddProductionPage(staff, name, "Movie");
            }
            if(type.equals("Series")) {
                new AddProductionPage(staff, name, "Series");
            }
            frame.dispose();
        }
    }
}
