package org.example.Pages;

import org.example.Classes.Actor;
import org.example.Classes.IMDB;
import org.example.Classes.Production;
import org.example.Classes.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SearchUserPage extends JFrame implements ActionListener, KeyListener {

    private JFrame frame;
    private JTextField searchField;
    private JButton searchButton;

    public SearchUserPage() {
        frame = new JFrame("IMDb - Search");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setIconImage(new ImageIcon("src/main/resources/Images/IMDB_Logo.png").getImage());
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        Label label = new Label("Type the email or username:");
        label.setBounds(100, 10, 300, 25);
        frame.add(label);

        JLabel label1 = new JLabel("Search:");
        label1.setBounds(15, 35, 80, 25);
        frame.add(label1);

        searchField = new JTextField(20);
        searchField.setBounds(100, 35, 160, 25);
        searchField.addKeyListener(this);
        frame.add(searchField);

        searchButton = new JButton("Search");
        searchButton.setBounds(10, 75, 80, 25);
        searchButton.addActionListener(this);
        frame.add(searchButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == searchButton) {
            String search = searchField.getText();
            User foundUser = null;
            for (User user : IMDB.getInstance().getUsers()) {
                if (user.getUsername().equals(search) || user.getInfo().getCredentials().getEmail().equals(search)) {
                    foundUser = user;
                    break;
                }
            }
            if(foundUser!=null){
                new UserDetailsPage(foundUser);
            }
            else {
                JOptionPane.showMessageDialog(frame, "User not found!", "Search Result", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
        // Not used
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER && keyEvent.getSource() == searchField) {
            searchButton.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        // Not used
    }
}
