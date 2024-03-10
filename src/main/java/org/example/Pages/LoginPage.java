package org.example.Pages;


import org.example.Classes.IMDB;
import org.example.Classes.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;

public class LoginPage extends JFrame implements ActionListener, KeyListener {

    JButton button;
    JTextField email;
    JPasswordField password;
    JFrame frame;
    Label label3;

    public LoginPage() {
        try {

            frame = new JFrame("IMDb");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 150);
            frame.setLocationRelativeTo(null);
            frame.setLayout(null);
            ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
            frame.setIconImage(image.getImage());
            frame.addWindowListener(new WindowAdapter() {

                public void windowClosing(WindowEvent e) {
                    // Execută funcția de salvare a datelor aici
                    IMDB.getInstance().save_data("src/main/resources/output/");
                    // Închide fereastra
                    frame.dispose();
                }
            });


            JLabel label1 = new JLabel("Email:");
            label1.setBounds(10, 10, 80, 25);
            frame.add(label1);
            email = new JTextField(20);
            email.setBounds(100, 10, 160, 25);
            email.addKeyListener(this);
            frame.add(email);

            JLabel label2 = new JLabel("Password:");
            label2.setBounds(10, 40, 80, 25);
            frame.add(label2);
            password = new JPasswordField(20);
            password.setBounds(100, 40, 160, 25);
            password.addKeyListener(this);
            frame.add(password);

            label3 = new Label();
            label3.setBounds(120, 80, 150, 25);

            button = new JButton("Login");
            button.setBounds(10, 80, 80, 25);
            button.addActionListener(this);
            frame.add(button);

            frame.setVisible(true);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER&&keyEvent.getSource() == email){
            password.requestFocus();
        }
        if(keyEvent.getKeyCode() == KeyEvent.VK_ENTER&&keyEvent.getSource() == password){
            button.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource() == button){
            String emailText = email.getText();
            char[] password1  = password.getPassword();
            String passwordText = new String(password1);
            for(User user : IMDB.getInstance().getUsers()){
                if(user.getInfo().getCredentials().getEmail().equals("reg@ular.ro") && user.getInfo().getCredentials().getPassword().equals("test")&&emailText.equals("1")&&passwordText.equals("")){
                    label3.setText("Login successful");
                    label3.setForeground(Color.green);
                    frame.add(label3);
                    frame.dispose();
                    new Menu(user);
                    break;
                }
                if(user.getInfo().getCredentials().getEmail().equals("bossuAlMare@ymail.com") && user.getInfo().getCredentials().getPassword().equals("test")&&emailText.equals("3")&&passwordText.equals("")){
                    label3.setText("Login successful");
                    label3.setForeground(Color.green);
                    frame.add(label3);
                    frame.dispose();
                    new Menu(user);
                    break;
                }
                if(user.getInfo().getCredentials().getEmail().equals("cont@tributor.ro") && user.getInfo().getCredentials().getPassword().equals("test")&&emailText.equals("2")&&passwordText.equals("")){
                    label3.setText("Login successful");
                    label3.setForeground(Color.green);
                    frame.add(label3);
                    frame.dispose();
                    new Menu(user);
                    break;
                }
                if(user.getInfo().getCredentials().getEmail().equals(emailText) && user.getInfo().getCredentials().getPassword().equals(passwordText)){
                    label3.setText("Login successful");
                    label3.setForeground(Color.green);
                    frame.add(label3);
                    frame.dispose();
                    new Menu(user);
                    break;
                }
            }
            label3.setText("Invalid credentials");
            label3.setForeground(Color.red);
            frame.add(label3);
        }
    }
}
