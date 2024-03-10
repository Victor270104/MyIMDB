package org.example.Pages;

import com.sun.source.tree.Tree;
import org.example.Classes.*;
import org.example.Enums.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class AddProductionPage extends JFrame implements ActionListener {

    JFrame frame;
    JButton nextButton;
    String name;
    String type;
    Staff staff;
    JTextField directors;
    JTextField actors;
    JTextField releaseYear;
    TextArea plot;
    JComboBox<String> genre1;
    JComboBox<String> genre2;
    JTextField aux;
    JTextField coverImage;


    public AddProductionPage(Staff staff, String name, String type) {
        this.staff = staff;
        this.name = name;
        this.type = type;
        frame = new JFrame("IMDb - Add Production");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 350);
        frame.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("C:\\Users\\vsand\\Desktop\\POO\\src\\main\\resources\\Images\\IMDB_Logo.png");
        frame.setIconImage(image.getImage());
        frame.setResizable(false);
        frame.setLayout(null);

        JLabel label = new JLabel("Genres: ");
        label.setBounds(10, 10, 80, 25);
        frame.add(label);

        String[] options = {"Action", "Adventure", "Comedy", "Drama", "Horror", "SF", "Fantasy", "Romance", "Mystery", "Thriller", "Crime", "Biography", "War", "Cooking"};

        genre1 = new JComboBox<String>(options);
        genre1.setBounds(100, 10, 200, 25);
        genre1.setSelectedIndex(-1);
        frame.add(genre1);

        genre2 = new JComboBox<String>(options);
        genre2.setBounds(100, 40, 200, 25);
        genre2.setSelectedIndex(-1);
        frame.add(genre2);

        JLabel label2 = new JLabel("Release Year: ");
        label2.setBounds(10, 70, 80, 25);
        frame.add(label2);

        releaseYear = new JTextField();
        releaseYear.setBounds(100, 70, 200, 25);
        frame.add(releaseYear);

        JLabel label3 = new JLabel("Plot: ");
        label3.setBounds(10, 100, 80, 25);
        frame.add(label3);

        plot = new TextArea();
        plot.setBounds(100, 100, 200, 50);
        frame.add(plot);

        JLabel label4 = new JLabel("Actors: ");
        label4.setBounds(10, 160, 80, 25);
        frame.add(label4);

        actors = new JTextField("Actor_1, Actor_2, Actor_3, ");
        actors.setBounds(100, 160, 200, 25);
        frame.add(actors);

        JLabel label5 = new JLabel("Directors: ");
        label5.setBounds(10, 190, 80, 25);
        frame.add(label5);

        directors = new JTextField("Director_1, Director_2, Director_3, ");
        directors.setBounds(100, 190, 200, 25);
        frame.add(directors);

        if(type.equals("Movie")) {
            JLabel label6 = new JLabel("Duration: ");
            label6.setBounds(10, 220, 80, 25);
            frame.add(label6);

            aux = new JTextField();
            aux.setBounds(100, 220, 200, 25);
            frame.add(aux);
        }
        if(type.equals("Series")){
            JLabel label6 = new JLabel("Seasons: ");
            label6.setBounds(10, 220, 80, 25);
            frame.add(label6);

            aux = new JTextField();
            aux.setBounds(100, 220, 200, 25);
            frame.add(aux);
        }

        JLabel label7 = new JLabel("CoverName: ");
        label7.setBounds(10, 250, 80, 25);
        frame.add(label7);

        coverImage = new JTextField();
        coverImage.setBounds(100, 250, 200, 25);
        frame.add(coverImage);

        nextButton = new JButton("Next");
        nextButton.setBounds(125, 280, 100, 25);
        nextButton.addActionListener(this);
        frame.add(nextButton);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==nextButton){
            if(type.equals("Movie")){
                List<String> actorsList = List.of(actors.getText().split(", "));
                List<String> directorsList = List.of(directors.getText().split(", "));
                List<Genre> genresList = new ArrayList<>();
                genresList.add(Genre.valueOf(Objects.requireNonNull(genre1.getSelectedItem()).toString()));
                if(genre2.getSelectedIndex()!=-1)
                    genresList.add(Genre.valueOf(Objects.requireNonNull(genre2.getSelectedItem()).toString()));
                try {
                    Integer.parseInt(releaseYear.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Release year must be a number!");
                    return;
                }
                Movie movie = new Movie(name, "Movie", directorsList, actorsList,genresList, new ArrayList<>(), plot.getText(),0.0, Integer.parseInt(releaseYear.getText()), aux.getText(), "");
                if(coverImage.getText().equals("")){
                    movie.setImage(new ImageIcon((new ImageIcon("src/main/resources/Images/27.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));
                }
                else
                    movie.setImage(new ImageIcon((new ImageIcon("src/main/resources/Images/"+coverImage.getText()+".jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));
                staff.getAdded().add(movie);
                IMDB.getInstance().getProductions().add(movie);
                JOptionPane.showMessageDialog(null, "Movie added successfully!");
                staff.experienceStrategy= staff.addIMBD;
                staff.experienceStrategy.addexp();
                frame.dispose();

            }
            if(type.equals("Series")){
                List<String> actorsList = List.of(actors.getText().split(", "));
                List<String> directorsList = List.of(directors.getText().split(", "));
                List<Genre> genresList = new ArrayList<>();
                genresList.add(Genre.valueOf(Objects.requireNonNull(genre1.getSelectedItem()).toString()));
                if(genre2.getSelectedIndex()!=-1)
                    genresList.add(Genre.valueOf(Objects.requireNonNull(genre2.getSelectedItem()).toString()));
                try {
                    Integer.parseInt(releaseYear.getText());
                    Integer.parseInt(aux.getText());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Release year must be a number!");
                    return;
                }
                Map<String, List<Episode>> sez=new TreeMap<>();
                for(int i=0;i<Integer.parseInt(aux.getText());i++){
                    new UpdateSeasonPage(sez,"New Season Name",null);
                }
                Series series;
                if(coverImage.getText().equals(""))
                    series = new Series(name, "Series", directorsList, actorsList, genresList, new ArrayList<>(), plot.getText(), 0.0, Integer.parseInt(releaseYear.getText()),Integer.parseInt(aux.getText()), sez, "src/main/resources/Images/27.jpg");
                else
                    series = new Series(name, "Series", directorsList, actorsList, genresList, new ArrayList<>(), plot.getText(), 0.0, Integer.parseInt(releaseYear.getText()),Integer.parseInt(aux.getText()), sez, "src/main/resources/Images/"+coverImage.getText()+".jpg");
                if(coverImage.getText().equals("")){
                    series.setImage(new ImageIcon((new ImageIcon("C:\\Users\\vsand\\Desktop\\POO\\src\\main\\resources\\Images\\27.jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));
                }
                else
                    series.setImage(new ImageIcon((new ImageIcon("C:\\Users\\vsand\\Desktop\\POO\\src\\main\\resources\\Images\\"+coverImage.getText()+".jpg")).getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH)));
                IMDB.getInstance().getProductions().add(series);
                staff.getAdded().add(series);
                staff.experienceStrategy= staff.addIMBD;
                staff.experienceStrategy.addexp();
                frame.dispose();

            }
        }

    }
}
