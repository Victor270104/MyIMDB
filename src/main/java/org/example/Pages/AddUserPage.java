package org.example.Pages;

import org.example.Classes.*;
import org.example.Enums.AccountType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.TreeSet;

public class AddUserPage extends JFrame implements ActionListener {

    JFrame frame;

    JComboBox<String> typeBox;
    JTextField nameField;
    JTextField emailField;
    JTextField countryField;
    JTextField ageField;
    JComboBox<String> genderBox;
    JTextField birthDateField;
    JButton addButton;

        public AddUserPage (){
            frame = new JFrame("IMDb - Add a new user");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(300, 300);
            frame.setLocationRelativeTo(null);
            ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
            frame.setIconImage(image.getImage());
            frame.setResizable(false);
            frame.setLayout(null);

            JLabel label1 = new JLabel("Name: ");
            label1.setBounds(10, 10, 80, 25);
            frame.add(label1);

            nameField = new JTextField("Ion POPESCU");
            nameField.setBounds(100, 10, 165, 25);
            frame.add(nameField);

            JLabel label2 = new JLabel("Email: ");
            label2.setBounds(10, 40, 80, 25);
            frame.add(label2);

            emailField = new JTextField("ion_popescu@gmail.com");
            emailField.setBounds(100, 40, 165, 25);
            frame.add(emailField);

            JLabel label3 = new JLabel("Country: ");
            label3.setBounds(10, 70, 80, 25);
            frame.add(label3);

            countryField = new JTextField("RO");
            countryField.setBounds(100, 70, 165, 25);
            frame.add(countryField);

            JLabel label4 = new JLabel("Age: ");
            label4.setBounds(10, 100, 80, 25);
            frame.add(label4);

            ageField = new JTextField("20");
            ageField.setBounds(100, 100, 165, 25);
            frame.add(ageField);

            JLabel label7 = new JLabel("Birth date: ");
            label7.setBounds(10, 130, 80, 25);
            frame.add(label7);

            birthDateField = new JTextField("2004-01-27");
            birthDateField.setBounds(100, 130, 165, 25);
            frame.add(birthDateField);

            JLabel label5 = new JLabel("Type: ");
            label5.setBounds(10, 160, 80, 25);
            frame.add(label5);

            String[] types = {"Admin", "Contributor", "Regular"};
            typeBox = new JComboBox<>(types);
            typeBox.setSelectedIndex(2);
            typeBox.setBounds(100, 160, 80, 25);
            frame.add(typeBox);

            JLabel label6 = new JLabel("Gender: ");
            label6.setBounds(10, 190, 80, 25);
            frame.add(label6);

            String[] genders = {"Male", "Female", "Other"};
            genderBox = new JComboBox<>(genders);
            genderBox.setSelectedIndex(2);
            genderBox.setBounds(100, 190, 80, 25);
            frame.add(genderBox);

            addButton = new JButton("Add");
            addButton.setBounds(100, 220, 100, 25);
            addButton.setFocusable(false);
            addButton.addActionListener(this);
            frame.add(addButton);


            frame.setVisible(true);


        }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==addButton){
            //TODO: Add user to database
            try {
                Integer.parseInt(ageField.getText());
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,"Invalid age","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            LocalDateTime birthDate;
            try{
                birthDate=LocalDateTime.parse(birthDateField.getText()+"T00:00:00");
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null,"Invalid birth date","Error",JOptionPane.ERROR_MESSAGE);
                return;
            }
            Random random = new Random();
            String validCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
            StringBuilder password = new StringBuilder();
            for(int i=0;i<10;i++){
                int randomIndex = random.nextInt(validCharacters.length());
                char randomChar = validCharacters.charAt(randomIndex);
                password.append(randomChar);
                password.append(random.nextInt(100));
            }
            String pass = String.valueOf(password);
            String un="";
            int c=0;
            while (c==0){
                c=1;
                un= nameField.getText().replace(" ","_")+random.nextInt(1000)+validCharacters.charAt(random.nextInt(validCharacters.length()));
                for(User user : IMDB.getInstance().getUsers()){
                    if (user.getUsername().equals(un)) {
                        c = 0;
                        break;
                    }
                }
            }
            User.Information information = new User.Information.Builder(new Credentials(emailField.getText(),pass),nameField.getText()).country(countryField.getText()).age(Integer.parseInt(ageField.getText())).gender(genderBox.getSelectedItem().toString()).birthDate(birthDate).build();
            if(typeBox.getSelectedItem().toString().equals("Admin")){
                Admin admin = (Admin) UserFactory.createUser(AccountType.ADMIN,information,un,0,new ArrayList<>(),new TreeSet<>(),new ArrayList<>(),new TreeSet<>());
                IMDB.getInstance().getUsers().add(admin);
            }
            if(typeBox.getSelectedItem().toString().equals("Contributor")) {
                Contributor contributor = (Contributor) UserFactory.createUser(AccountType.CONTRIBUTOR, information, un, 0, new ArrayList<>(), new TreeSet<>(), new ArrayList<>(), new TreeSet<>());
                IMDB.getInstance().getUsers().add(contributor);
            }
            if(typeBox.getSelectedItem().toString().equals("Regular")) {
                Regular regular = (Regular) UserFactory.createUser(AccountType.REGULAR, information, un, 0, new ArrayList<>(), new TreeSet<>(), new ArrayList<>(), new TreeSet<>());
                IMDB.getInstance().getUsers().add(regular);
            }
            JOptionPane.showMessageDialog(null,"The password is: "+pass,"Success",JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        }
    }
}
