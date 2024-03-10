package org.example.Pages;

import org.example.Classes.Actor;
import org.example.Classes.Role;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class ActorDisplayPage extends JFrame {

    private final List<Actor> actors;
    private JPanel actorPanel;
    private boolean ascendingOrder = true; // Default sorting order
    private Comparator<Actor> currentComparator = Comparator.comparing(Actor::getName); // Default sorting by name

    public ActorDisplayPage(List<Actor> actors) {
        this.actors = actors;

        setTitle("IMDB - Actors");
        setSize(800, 600); // Increased size for better visibility
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/main/resources/Images/IMDB_Logo.png").getImage());

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Create a panel to hold actors
        actorPanel = new JPanel();
        actorPanel.setLayout(new BoxLayout(actorPanel, BoxLayout.Y_AXIS));

        // Add actors to the panel
        sortActors(); // Default sorting
        updateActorPanel();

        // Create a JScrollPane to make the panel scrollable
        JScrollPane scrollPane = new JScrollPane(actorPanel);

        JButton sortByNameButton = new JButton("Sort by Name");
        sortByNameButton.addActionListener(e -> {
            currentComparator = Comparator.comparing(Actor::getName);
            sortActors();
            updateActorPanel();
        });

        JButton sortByPerformanceButton = new JButton("Sort by Number of Performances");
        sortByPerformanceButton.addActionListener(e -> {
            currentComparator = Comparator.comparingInt(Actor::getNumberOfPerformances);
            sortActors();
            updateActorPanel();
        });

        JButton sortByRatingButton = new JButton("Sort by Average Rating");
        sortByRatingButton.addActionListener(e -> {
            currentComparator = Comparator.comparingDouble(Actor::getAverageRating);
            sortActors();
            updateActorPanel();
        });

        JButton sortByMostRatingsButton = new JButton("Sort by Most Ratings");
        sortByMostRatingsButton.addActionListener(e -> {
            currentComparator = Comparator.comparingInt(Actor::getNumberOfRatings).thenComparing(Comparator.comparing(Actor::getName));
            sortActors();
            updateActorPanel();
        });

        // Create a combo box for sorting buttons
        String[] sortingOptions = {"Sort by Name", "Sort by Number of Performances", "Sort by Average Rating", "Sort by Most Ratings"};
        JComboBox<String> sortingComboBox = new JComboBox<>(sortingOptions);
        sortingComboBox.addActionListener(e -> {
            // Update the current comparator based on the selected option
            switch ((String) Objects.requireNonNull(sortingComboBox.getSelectedItem())) {
                case "Sort by Name":
                    currentComparator = Comparator.comparing(Actor::getName);
                    break;
                case "Sort by Number of Performances":
                    currentComparator = Comparator.comparingInt(Actor::getNumberOfPerformances);
                    break;
                case "Sort by Average Rating":
                    currentComparator = Comparator.comparingDouble(Actor::getAverageRating);
                    break;
                case "Sort by Most Ratings":
                    currentComparator = Comparator.comparingInt(Actor::getNumberOfRatings).thenComparing(Comparator.comparing(Actor::getName));
                    break;
            }
            sortActors();
            updateActorPanel();
        });

        JToggleButton ascendingDescendingButton = new JToggleButton("Toggle Order");
        ascendingDescendingButton.addActionListener(e -> {
            ascendingOrder = !ascendingOrder;
            sortActors();
            updateActorPanel();
        });

        setLayout(new BorderLayout());

        JPanel sortingPanel = new JPanel();
        sortingPanel.add(sortingComboBox);
        sortingPanel.add(ascendingDescendingButton);
        add(sortingPanel, BorderLayout.NORTH);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void updateActorPanel() {
        actorPanel.removeAll();
        sortActors();
        for (Actor actor : actors) {
            JPanel actorItemPanel = createActorPanel(actor);
            actorPanel.add(actorItemPanel);
        }
        actorPanel.revalidate();
        actorPanel.repaint();
    }

    private void sortActors() {
        if (ascendingOrder) {
            actors.sort(currentComparator);
        } else {
            actors.sort(currentComparator.reversed());
        }
    }

    private JPanel createActorPanel(Actor actor) {
        JPanel actorItemPanel = new JPanel();
        actorItemPanel.setLayout(new BoxLayout(actorItemPanel, BoxLayout.Y_AXIS));

        // Make actor's name bold
        JLabel nameLabel = new JLabel("Name: " + actor.getName());
        nameLabel.setFont(new Font(nameLabel.getFont().getName(), Font.BOLD, nameLabel.getFont().getSize()));
        actorItemPanel.add(createFormattedLabel(nameLabel));

        JLabel performancesLabel = new JLabel("Number of Performances: " + actor.getNumberOfPerformances());
        actorItemPanel.add(createFormattedLabel(performancesLabel));

        // Display a message if the average rating is 0.0
        double averageRating = actor.getAverageRating();
        String averageRatingText = (averageRating > 0.0) ? String.valueOf(averageRating) : "Not rated yet";
        JLabel averageRatingLabel = new JLabel("Average Rating: " + averageRatingText);
        actorItemPanel.add(createFormattedLabel(averageRatingLabel));

        JLabel biographyLabel = new JLabel("Biography: " + actor.getBiography());
        actorItemPanel.add(createFormattedLabel(biographyLabel));

        JLabel rolesLabel = new JLabel("Roles:");
        actorItemPanel.add(createFormattedLabel(rolesLabel));

        for (Role role : actor.getPerformances()) {
            JLabel roleLabel = new JLabel("   " + role.displayInfo());
            actorItemPanel.add(createFormattedLabel(roleLabel));
        }

        actorItemPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        return actorItemPanel;
    }


    private JPanel createFormattedLabel(JLabel label) {
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelPanel.add(label);
        return labelPanel;
    }
}
