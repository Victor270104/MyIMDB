package org.example.Pages;

import org.example.Classes.*;
import org.example.Enums.Genre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Comparator;
import java.util.List;

public class ProductionDisplayPage extends JFrame {

    private final List<Production> productions;
    private JPanel productionPanel;
    private boolean ascendingOrder = true; // Default sorting order
    private Comparator<Production> currentComparator = Comparator.comparing(Production::getTitle); // Default sorting by title

    public ProductionDisplayPage(List<Production> productions) {
        this.productions = productions;

        setTitle("IMDB - Productions");
        setSize(600, 500); // Increased size for better visibility
        setIconImage(new ImageIcon("src/main/resources/Images/IMDB_Logo.png").getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Create a panel to hold productions
        productionPanel = new JPanel();
        productionPanel.setLayout(new BoxLayout(productionPanel, BoxLayout.Y_AXIS));

        // Add productions to the panel
        sortProductions(); // Default sorting
        updateProductionPanel();

        // Create a JScrollPane to make the panel scrollable
        JScrollPane scrollPane = new JScrollPane(productionPanel);

        // Create sorting buttons
        JButton sortByTitleButton = new JButton("Sort by Title");
        sortByTitleButton.addActionListener(e -> {
            currentComparator = Comparator.comparing(Production::getTitle);
            sortProductions();
            updateProductionPanel();
        });

        JButton sortByYearButton = new JButton("Sort by Year");
        sortByYearButton.addActionListener(e -> {
            currentComparator = Comparator.comparingInt(Production::getReleaseYear);
            sortProductions();
            updateProductionPanel();
        });

        JButton sortByRatingButton = new JButton("Sort by Rating");
        sortByRatingButton.addActionListener(e -> {
            currentComparator = Comparator.comparingDouble(Production::getAverageRating);
            sortProductions();
            updateProductionPanel();
        });

        JButton sortByNumberOfRatingsButton = new JButton("Sort by Number of Ratings");
        sortByNumberOfRatingsButton.addActionListener(e -> {
            currentComparator = Comparator.comparingInt(Production::getRatingSize);
            sortProductions();
            updateProductionPanel();
        });

        JButton sortByNumberOfActorsButton = new JButton("Sort by Number of Actors");
        sortByNumberOfActorsButton.addActionListener(e -> {
            currentComparator = Comparator.comparingInt(Production::getNumberOfActors);
            sortProductions();
            updateProductionPanel();
        });

        // Create a combo box for sorting buttons
        String[] sortingOptions = {"Sort by Title", "Sort by Year", "Sort by Rating", "Sort by Number of Ratings", "Sort by Number of Actors"};
        JComboBox<String> sortingComboBox = new JComboBox<>(sortingOptions);
        sortingComboBox.addActionListener(e -> {
            // Update the current comparator based on the selected option
            switch ((String) sortingComboBox.getSelectedItem()) {
                case "Sort by Title":
                    currentComparator = Comparator.comparing(Production::getTitle);
                    break;
                case "Sort by Year":
                    currentComparator = Comparator.comparingInt(Production::getReleaseYear);
                    break;
                case "Sort by Rating":
                    currentComparator = Comparator.comparingDouble(Production::getAverageRating);
                    break;
                case "Sort by Number of Ratings":
                    currentComparator = Comparator.comparingInt(Production::getRatingSize);
                    break;
                case "Sort by Number of Actors":
                    currentComparator = Comparator.comparingInt(Production::getNumberOfActors);
                    break;
            }
            sortProductions();
            updateProductionPanel();
        });

        // Toggle button for ascending/descending order
        JToggleButton ascendingDescendingButton = new JToggleButton("Toggle Order");
        ascendingDescendingButton.addActionListener(e -> {
            ascendingOrder = !ascendingOrder;
            sortProductions();
            updateProductionPanel();
        });

        // Set layout manager to BorderLayout
        setLayout(new BorderLayout());

        // Add combo box and the last button to the top
        JPanel sortingPanel = new JPanel();
        sortingPanel.add(sortingComboBox);
        sortingPanel.add(ascendingDescendingButton);
        add(sortingPanel, BorderLayout.NORTH);

        // Add the scroll pane to the center
        add(scrollPane, BorderLayout.CENTER);
    }

    private void updateProductionPanel() {
        productionPanel.removeAll();
        sortProductions();
        for (Production production : productions) {
            JPanel productionItemPanel = createProductionPanel(production);
            productionPanel.add(productionItemPanel);
        }
        productionPanel.revalidate();
        productionPanel.repaint();
    }

    private void sortProductions() {
        if (ascendingOrder) {
            productions.sort(currentComparator);
        } else {
            productions.sort(currentComparator.reversed());
        }
    }

    private JPanel createProductionPanel(Production production) {
        // Create a panel for a single production item with FlowLayout
        JPanel productionItemPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Add image label
        JLabel imageLabel = new JLabel(production.getImage());
        productionItemPanel.add(imageLabel);

        // Create a panel for details (including actors and directors)
        JPanel details = new JPanel();
        details.setLayout(new BoxLayout(details, BoxLayout.Y_AXIS));

        // Add title label
        JLabel titleLabel = new JLabel(production.getType() + " Title: " + production.getTitle());
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        details.add(titleLabel);

        // Add year label
        JLabel yearLabel = new JLabel("Release Year: " + production.getReleaseYear());
        yearLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        details.add(yearLabel);

        // Add number of ratings label
        JLabel numberOfRatingsLabel = new JLabel("Rating: " + production.getAverageRating() + " (" + production.getRatingSize() + ")");
        numberOfRatingsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        details.add(numberOfRatingsLabel);

        // Add genre label
        StringBuilder genreString = new StringBuilder();
        for (Genre genre : production.getGenres()) {
            genreString.append(genre).append(", ");
        }
        if (genreString.length() > 2) {
            genreString.deleteCharAt(genreString.length() - 1);
            genreString.deleteCharAt(genreString.length() - 1);
        }
        JLabel genreLabel = new JLabel("Genre: " + genreString);
        genreLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        details.add(genreLabel);

        if (production.getType().equals("Movie")) {
            Movie movie = (Movie) production;
            // Add duration label
            JLabel durationLabel = new JLabel("Duration: " + movie.getDuration());
            durationLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            details.add(durationLabel);
        } else if (production.getType().equals("Series")) {
            Series series = (Series) production;
            JLabel seasonsLabel = new JLabel("Seasons: " + series.getSeasons_nr() + " (" + series.getNumberOfEpisodes() + " Episodes)");
            seasonsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            details.add(seasonsLabel);
        }

        productionItemPanel.add(details);

        // Add action listener for showing more details on image click
        imageLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open the details page for the selected production
                showMoreDetailsPage(production);
            }
        });

        return productionItemPanel;
    }

    private void showMoreDetailsPage(Production production) {
        // Create the details page for the selected production
        new ProductionDetailsPage(production);
    }
}
