package org.example.Pages;

import org.example.Classes.Actor;
import org.example.Classes.IMDB;
import org.example.Classes.Production;
import org.example.Classes.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class FavouritesPage implements ActionListener {

    JFrame frame;
    JButton submitButton;
    JComboBox<String> comboBox;
    User user;
    int choice;

    public FavouritesPage(User user) {
        this.user = user;
        frame = new JFrame();
        String[] options = {"Add to Favourites", "Delete from Favourites"};
        choice = JOptionPane.showOptionDialog(null, "What do you want to do?", "To add or not to add - That is the question!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(choice == 0) {
            frame = new JFrame("Add to favourites");
        }
        if(choice == 1) {
            frame = new JFrame("Delete from favourites");
        }
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 150);
        frame.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
        frame.setIconImage(image.getImage());
        frame.setResizable(false);
        frame.setLayout(null);

        SortedSet<String> options2 = new TreeSet<>();
        if(choice == 0) {
            for(Actor actor : IMDB.getInstance().getActors()) {
                options2.add("Actor: "+actor.getName());
            }
            for(Production production : IMDB.getInstance().getProductions()) {
                options2.add("Production: "+production.getTitle());
            }
            for(Object object : user.getFavorites()) {
                if(object instanceof Actor) {
                    options2.remove("Actor: "+((Actor) object).getName());
                }
                if(object instanceof Production) {
                    options2.remove("Production: "+((Production) object).getTitle());
                }
            }
        }
        if(choice == 1) {
            for(Object object : user.getFavorites()) {
                if(object instanceof Actor) {
                    options2.add("Actor: "+((Actor) object).getName());
                }
                if(object instanceof Production) {
                    options2.add("Production: "+((Production) object).getTitle());
                }
            }
        }
        if(options2.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No options available!");
            frame.dispose();
            return;
        }

        comboBox = new JComboBox<>(options2.toArray(new String[0]));
        comboBox.setBounds(55, 20, 240, 35);
        comboBox.setFocusable(false);
        frame.add(comboBox);

        submitButton = new JButton("Submit");
        submitButton.setBounds(100, 60, 150, 40);
        submitButton.addActionListener(this);
        submitButton.setFocusable(false);
        frame.add(submitButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton && choice==0) {
            if(comboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "No option selected!");
                return;
            }
            String option = (String) comboBox.getSelectedItem();
            String[] parts = option.split(": ");
            if (parts[0].equals("Actor")) {
                for (Actor actor : IMDB.getInstance().getActors()) {
                    if (actor.getName().equals(parts[1])) {
                        user.getFavorites().add(actor);
                        JOptionPane.showMessageDialog(null, "Actor added to favourites!");
                        frame.dispose();
                        break;
                    }
                }
            }
            if(parts[0].equals("Production")) {
                for (Production production : IMDB.getInstance().getProductions()) {
                    if (production.getTitle().equals(parts[1])) {
                        user.getFavorites().add(production);
                        JOptionPane.showMessageDialog(null, "Production added to favourites!");
                        frame.dispose();
                        break;
                    }
                }
            }
        }
        if (e.getSource() == submitButton && choice==1) {
            if(comboBox.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "No option selected!");
                return;
            }
            String option = (String) comboBox.getSelectedItem();
            String[] parts = option.split(": ");
            if (parts[0].equals("Actor")) {
                for (Actor actor : IMDB.getInstance().getActors()) {
                    if (actor.getName().equals(parts[1])) {
                        user.getFavorites().remove(actor);
                        JOptionPane.showMessageDialog(null, "Actor removed from favourites!");
                        frame.dispose();
                        break;
                    }
                }
            }
            if(parts[0].equals("Production")) {
                for (Production production : IMDB.getInstance().getProductions()) {
                    if (production.getTitle().equals(parts[1])) {
                        user.getFavorites().remove(production);
                        JOptionPane.showMessageDialog(null, "Production removed from favourites!");
                        frame.dispose();
                        break;
                    }
                }
            }
        }
    }
}
