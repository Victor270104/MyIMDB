package org.example.Pages;

import org.example.Classes.Actor;
import org.example.Classes.IMDB;
import org.example.Classes.Production;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SearchObjectPage extends JFrame implements ActionListener, KeyListener {

    private JFrame frame;
    private JTextField searchField;
    private JButton searchButton;

    public SearchObjectPage() {
        frame = new JFrame("IMDb - Search");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 125);
        frame.setIconImage(new ImageIcon("src/main/resources/Images/IMDB_Logo.png").getImage());
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);

        JLabel label1 = new JLabel("Search:");
        label1.setBounds(15, 10, 80, 25);
        frame.add(label1);

        searchField = new JTextField(20);
        searchField.setBounds(100, 10, 160, 25);
        searchField.addKeyListener(this);
        frame.add(searchField);

        searchButton = new JButton("Search");
        searchButton.setBounds(10, 50, 80, 25);
        searchButton.addActionListener(this);
        frame.add(searchButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == searchButton) {
            String search = searchField.getText();
            Actor foundActor = null;
            for (Actor actor : IMDB.getInstance().getActors()) {
                if (actor.getName().equals(search)) {
                    foundActor = actor;
                    break;
                }
            }
            Production foundProduction = null;
            for (Production production : IMDB.getInstance().getProductions()) {
                if (production.getTitle().equals(search)) {
                    foundProduction = production;
                    break;
                }
            }

            if (foundActor != null) {
                new ActorDetailsPage(foundActor);
            } else if(foundProduction != null){
                new ProductionDetailsPage(foundProduction);
            } else {
                JOptionPane.showMessageDialog(frame, "Object not found!", "Search Result", JOptionPane.ERROR_MESSAGE);
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
