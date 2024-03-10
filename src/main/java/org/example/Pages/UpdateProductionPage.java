package org.example.Pages;

import org.example.Classes.Episode;
import org.example.Classes.Movie;
import org.example.Classes.Production;
import org.example.Classes.Series;
import org.example.Enums.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class UpdateProductionPage extends JFrame implements ActionListener {
    JFrame frame;
    JButton submitButton;
    Production production;
    JTextField nameTextField;
    JTextField yearTextField;
    JTextField genreTextField;
    JTextField plotTextField;
    JTextField directorTextField;
    JTextField actorTextField;
    JTextField durationTextField;
    JTextField seasonsTextField;


    public UpdateProductionPage(Production production) {
        frame = new JFrame("IMDb - Update Production");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 370);
        frame.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
        frame.setIconImage(image.getImage());
        frame.setLayout(null);
        frame.setResizable(false);

        this.production = production;

        JLabel nameLabel = new JLabel("Title: ");
        nameLabel.setBounds(25, 10, 50, 30);
        nameLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(nameLabel);

        nameTextField = new JTextField(production.getTitle());
        nameTextField.setBounds(85, 10, 200, 30);
        nameTextField.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(nameTextField);

        JLabel yearLabel = new JLabel("Year: ");
        yearLabel.setBounds(25, 50, 50, 30);
        yearLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(yearLabel);

        yearTextField = new JTextField(String.valueOf(production.getReleaseYear()));
        yearTextField.setBounds(85, 50, 200, 30);
        yearTextField.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(yearTextField);

        JLabel genreLabel = new JLabel("Genre: ");
        genreLabel.setBounds(25, 90, 50, 30);
        genreLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(genreLabel);

        genreTextField = new JTextField();
        for(Genre genre:production.getGenres()){
            genreTextField.setText(genreTextField.getText()+genre+", ");
        }
        genreTextField.setBounds(85, 90, 200, 30);
        genreTextField.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(genreTextField);

        JLabel plotLabel = new JLabel("Plot: ");
        plotLabel.setBounds(25, 130, 50, 30);
        plotLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(plotLabel);

        plotTextField = new JTextField(production.getPlot());
        plotTextField.setBounds(85, 130, 200, 30);
        plotTextField.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(plotTextField);

        JLabel directorLabel = new JLabel("Directors: ");
        directorLabel.setBounds(25, 170, 50, 30);
        directorLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(directorLabel);

        directorTextField = new JTextField();
        for(String director:production.getDirectors()){
            directorTextField.setText(directorTextField.getText()+director+", ");
        }
        directorTextField.setBounds(85, 170, 200, 30);
        directorTextField.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(directorTextField);

        JLabel actorLabel = new JLabel("Actors: ");
        actorLabel.setBounds(25, 210, 50, 30);
        actorLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(actorLabel);

        actorTextField = new JTextField();
        for(String actor:production.getActors()){
            actorTextField.setText(actorTextField.getText()+actor+", ");
        }
        actorTextField.setBounds(85, 210, 200, 30);
        actorTextField.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        frame.add(actorTextField);

        if(production instanceof Movie)
        {
            JLabel durationLabel = new JLabel("Duration: ");
            durationLabel.setBounds(25, 250, 50, 30);
            durationLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
            frame.add(durationLabel);

            durationTextField = new JTextField(String.valueOf(((Movie) production).getDuration()));
            durationTextField.setBounds(85, 250, 200, 30);
            durationTextField.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
            frame.add(durationTextField);
        }

        if(production instanceof Series){
            JLabel seasonsLabel = new JLabel("Seasons: ");
            seasonsLabel.setBounds(25, 250, 50, 30);
            seasonsLabel.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
            frame.add(seasonsLabel);

            seasonsTextField = new JTextField(String.valueOf(((Series) production).getSeasons_nr()));
            seasonsTextField.setBounds(85, 250, 200, 30);
            seasonsTextField.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
            frame.add(seasonsTextField);

        }

        submitButton = new JButton("Submit");
        submitButton.setBounds(125, 290, 100, 30);
        submitButton.setFocusable(false);
        submitButton.addActionListener(this);
        frame.add(submitButton);



        frame.setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==submitButton){
            frame.dispose();
            production.setTitle(nameTextField.getText());
            try {
                production.setReleaseYear(Integer.parseInt(yearTextField.getText()));
            }
            catch (Exception exception){
                JOptionPane.showMessageDialog(null, "Wrong year input", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            production.setPlot(plotTextField.getText());
            String [] genres = genreTextField.getText().split(", ");
            List<Genre> genreList = production.getGenres();
            production.getGenres().clear();
            for(String genre:genres){
                try {
                    production.getGenres().add(Genre.valueOf(genre));
                }
                catch (Exception exception){
                    production.getGenres().clear();
                    production.setGenres(genreList);
                    JOptionPane.showMessageDialog(null, "Wrong genre input", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            String [] directors = directorTextField.getText().split(", ");
            if(!production.getDirectors().isEmpty())
                production.setDirectors(new ArrayList<>());
            for(String director:directors){
                production.getDirectors().add(director);
            }
            String [] actors = actorTextField.getText().split(", ");
            production.setActors(new ArrayList<>());
            for(String actor:actors){
                production.getActors().add(actor);
            }
            if(production instanceof Movie){
                ((Movie) production).setDuration(durationTextField.getText());
            }
            if(production instanceof Series){
                int seasons;
                try {
                    seasons = Integer.parseInt(seasonsTextField.getText());
                }
                catch (Exception e){
                    JOptionPane.showMessageDialog(null, "Wrong seasons input", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ((Series) production).setSeasons_nr(seasons);
                Map<String, List<Episode>> sez=new TreeMap<>();
                    for (String season : ((Series)production).getSeasons().keySet()) {
                        seasons--;
                        new UpdateSeasonPage(sez,season,((Series)production).getSeasons().get(season));
                    }
                for(int i=0; i<seasons; i++){
                    new UpdateSeasonPage(sez,"New Season Name",null);
                }
                ((Series) production).getSeasons().clear();
                for(String season:sez.keySet()){
                    ((Series) production).getSeasons().put(season,sez.get(season));
                }
            }
        }

    }
}
