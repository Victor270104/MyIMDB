package org.example.Pages;

import org.example.Classes.*;
import org.example.Enums.AccountType;
import org.example.Enums.Genre;
import org.example.Enums.RequestTypes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.SortedSet;
import java.util.TreeSet;

public class UpdateAddedObjPage extends JFrame implements ActionListener  {

    JFrame frame;
    JButton selectButton;
    JComboBox<String> comboBox;
    JTextArea DetailsTextArea;

    public UpdateAddedObjPage(Staff user){
        frame = new JFrame("IMDb - Update");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
        frame.setIconImage(image.getImage());
        frame.setLayout(null);
        frame.setResizable(false);

        SortedSet<String> strings = new TreeSet<>();
        for(Object obj : user.getAdded()){
            if(obj instanceof Actor){
                strings.add("Actor: "+((Actor) obj).getName());
            }
            if(obj instanceof Production){
                strings.add("Production: "+((Production) obj).getTitle());
            }
        }

        if(strings.isEmpty()){
            JOptionPane.showMessageDialog(null, "You have nothing added", "Error", JOptionPane.ERROR_MESSAGE);
            frame.dispose();
            return;
        }

        comboBox = new JComboBox<>(strings.toArray(new String[0]));
        comboBox.setBounds(25, 10, 300, 30);
        comboBox.setSelectedIndex(-1);
        comboBox.addActionListener(this);
        frame.add(comboBox);

        DetailsTextArea = new JTextArea();
        DetailsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(DetailsTextArea);
        scrollPane.setBounds(25, 50, 300, 150);
        frame.add(scrollPane);

        selectButton = new JButton("Select");
        selectButton.setBounds(25, 220, 300, 25);
        selectButton.addActionListener(this);
        frame.add(selectButton);


        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== comboBox){
            if(comboBox.getSelectedItem()==null){
                return;
            }
            DetailsTextArea.setText("");
            String option = (String) comboBox.getSelectedItem();
            String[] options = option.split(": ");
            if(options[0].equals("Actor")){
                for(Actor actor : IMDB.getInstance().getActors()){
                    if(actor.getName().equals(options[1])){
                        DetailsTextArea.append("Name: " + actor.getName());
                        DetailsTextArea.append("\nNumber of Performances: " + actor.getNumberOfPerformances());
                        DetailsTextArea.append("\nAverage Rating: " + (actor.getAverageRating() != 0.0 ? actor.getAverageRating() : "No ratings yet"));
                        if(actor.getBiography() != null && !actor.getBiography().isEmpty())
                            DetailsTextArea.append("\nBiography: " + actor.getBiography());
                        if (actor.getPerformances() != null && !actor.getPerformances().isEmpty()) {
                            DetailsTextArea.append("\nRoles:");
                            for (Role role : actor.getPerformances()) {
                                DetailsTextArea.append("\n   " + role.displayInfo());
                            }
                        }
                        return;
                    }
                }
            }
            if(options[0].equals("Production")){
                for(Production production : IMDB.getInstance().getProductions()){
                    if(production.getTitle().equals(options[1])){
                        DetailsTextArea.append("Title: " + production.getTitle());
                        DetailsTextArea.append("\nRelease Date: " + production.getReleaseYear());
                        DetailsTextArea.append("\nAverage Rating: " + (production.getAverageRating() != 0.0 ? production.getAverageRating() : "No ratings yet"));
                        if(production.getPlot() != null && !production.getPlot().isEmpty())
                            DetailsTextArea.append("\nPlot: " + production.getPlot());
                        DetailsTextArea.append("\nType: " + production.getType());

                        DetailsTextArea.append("\nGenres: ");
                        for(Genre genre : production.getGenres()){
                            DetailsTextArea.append(genre + ", ");
                        }
                        DetailsTextArea.append("\nDirectors: ");
                        for(String director : production.getDirectors()){
                            DetailsTextArea.append(director + ", ");
                        }
                        DetailsTextArea.append("\nActors: ");
                        for(String actor : production.getActors()){
                            DetailsTextArea.append(actor + ", ");
                        }
                        if(production instanceof Movie){
                            DetailsTextArea.append("\nDuration: " + ((Movie) production).getDuration());
                        }
                        if(production instanceof Series){
                            DetailsTextArea.append("\nNumber of Seasons: " + ((Series) production).getSeasons_nr());
                        }
                        if(production instanceof Series){
                            for(String s : ((Series) production).getSeasons().keySet()){
                                DetailsTextArea.append("\n" + s+": ");
                                for(Episode episode : ((Series) production).getSeasons().get(s)){
                                    DetailsTextArea.append("\n   Episode: " + episode.getName() + " - Duration: " + episode.getDuration());
                                }
                            }
                        }
                        return;
                    }
                }
            }

        }
        if(e.getSource()== selectButton){
            if(comboBox.getSelectedItem()==null){
                JOptionPane.showMessageDialog(null, "You have nothing selected", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String option = (String) comboBox.getSelectedItem();
            String[] options = option.split(": ");
            if(options[0].equals("Actor")){
                for(Actor actor : IMDB.getInstance().getActors()){
                    if(actor.getName().equals(options[1])){
                        new UpdateActorPage(actor);
                        frame.dispose();
                        return;
                    }
                }
            }
            if(options[0].equals("Production")){
                for(Production production : IMDB.getInstance().getProductions()){
                    if(production.getTitle().equals(options[1])){
                        new UpdateProductionPage(production);
                        frame.dispose();
                        return;
                    }
                }
            }
        }
    }

}
