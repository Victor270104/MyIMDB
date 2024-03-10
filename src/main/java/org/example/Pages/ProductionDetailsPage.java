package org.example.Pages;

import org.example.Classes.*;
import org.example.Enums.Genre;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductionDetailsPage extends JFrame {

    public ProductionDetailsPage(Production production) {
        setTitle("Details Page - " + production.getTitle());
        setSize(600, 500);
        setIconImage(new ImageIcon("src/main/resources/Images/IMDB_Logo.png").getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents(production);
        setVisible(true);
    }

    private void initComponents(Production production) {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Add image
        if (production.getImage() != null) {
            JLabel imageLabel = new JLabel(production.getImage());
            imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            detailsPanel.add(imageLabel, gbc);
            gbc.gridy++;
        }

        // Add common details
        addLabel(detailsPanel, "Title: " + production.getTitle(), Font.BOLD, 16, gbc);
        addLabel(detailsPanel, "Type: " + production.getType(), Font.PLAIN, 14, gbc);
        addLabel(detailsPanel, "Release Year: " + production.getReleaseYear(), Font.PLAIN, 14, gbc);
        addLabel(detailsPanel, "Average Rating: " + (production.getAverageRating() != null ? production.getAverageRating() : "No ratings yet"), Font.PLAIN, 14, gbc);

        if (production.getGenres() != null && !production.getGenres().isEmpty()) {
            StringBuilder genres = new StringBuilder("Genres: ");
            for (Genre genre : production.getGenres()) {
                genres.append(genre).append(", ");
            }
            genres.deleteCharAt(genres.length() - 2); // Remove the last comma
            addLabel(detailsPanel, genres.toString(), Font.PLAIN, 14, gbc);
        }

        // Add plot
        if (production.getPlot() != null && !production.getPlot().isEmpty()) {
            addLabel(detailsPanel, "Plot: " + production.getPlot(), Font.PLAIN, 14, gbc);
        }

        // Add details based on the production type
        if (production instanceof Movie) {
            Movie movie = (Movie) production;
            addLabel(detailsPanel, "Duration: " + movie.getDuration(), Font.PLAIN, 14, gbc);

            // Add actors
            if (movie.getActors() != null && !movie.getActors().isEmpty()) {
                StringBuilder actors = new StringBuilder("Actors: ");
                for (String actor : movie.getActors()) {
                    actors.append(actor).append(", ");
                }
                actors.deleteCharAt(actors.length() - 2); // Remove the last comma
                addLabel(detailsPanel, actors.toString(), Font.PLAIN, 14, gbc);
            }

            // Add directors
            if (movie.getDirectors() != null && !movie.getDirectors().isEmpty()) {
                StringBuilder directors = new StringBuilder("Directors: ");
                for (String director : movie.getDirectors()) {
                    directors.append(director).append(", ");
                }
                directors.deleteCharAt(directors.length() - 2); // Remove the last comma
                addLabel(detailsPanel, directors.toString(), Font.PLAIN, 14, gbc);
            }

        } else if (production instanceof Series) {
            Series series = (Series) production;
            addLabel(detailsPanel, "Number of Seasons: " + series.getSeasons_nr(), Font.PLAIN, 14, gbc);
            addLabel(detailsPanel, "Number of Episodes: " + series.getNumberOfEpisodes(), Font.PLAIN, 14, gbc);

            // Add seasons and episodes
            for (String season : series.getSeasons().keySet()) {
                addLabel(detailsPanel, "Season: " + season, Font.BOLD, 16, gbc);
                List<Episode> episodes = series.getSeasons().get(season);
                for (Episode episode : episodes) {
                    addLabel(detailsPanel, "   Episode: " + episode.getName() + " - Duration: " + episode.getDuration(), Font.PLAIN, 14, gbc);
                }
            }

            // Add actors
            if (series.getActors() != null && !series.getActors().isEmpty()) {
                StringBuilder actors = new StringBuilder("Actors: ");
                for (String actor : series.getActors()) {
                    actors.append(actor).append(", ");
                }
                actors.deleteCharAt(actors.length() - 2); // Remove the last comma
                addLabel(detailsPanel, actors.toString(), Font.PLAIN, 14, gbc);
            }

            // Add directors
            if (series.getDirectors() != null && !series.getDirectors().isEmpty()) {
                StringBuilder directors = new StringBuilder("Directors: ");
                for (String director : series.getDirectors()) {
                    directors.append(director).append(", ");
                }
                directors.deleteCharAt(directors.length() - 2); // Remove the last comma
                addLabel(detailsPanel, directors.toString(), Font.PLAIN, 14, gbc);
            }
        }

        // Add reviews
        if (production.getRatings() != null && !production.getRatings().isEmpty()) {
            addLabel(detailsPanel, "Reviews:", Font.BOLD, 16, gbc);

            List<Rating> ratings=production.getRatings();
            Collections.sort(ratings, Comparator.comparing(Rating::getExperience).reversed());
            for (Rating rating : ratings) {
                addLabel(detailsPanel, "   " + rating.getUsername()+" ("+rating.getRating()+") commented: "+rating.getComment(), Font.PLAIN, 14, gbc);
            }
        }

        // Add the details panel to the frame
        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        add(scrollPane);
    }

    private void addLabel(JPanel panel, String text, int style, int size, GridBagConstraints gbc) {
        JLabel label = new JLabel(text);
        Font font = new Font(Font.SANS_SERIF, style, size);
        label.setFont(font);
        panel.add(label, gbc);
        gbc.gridy++; // Move to the next row
    }
}
