package org.example.Pages;

import org.example.Classes.Actor;
import org.example.Classes.Rating;
import org.example.Classes.Role;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ActorDetailsPage extends JFrame {

    public ActorDetailsPage(Actor actor) {
        setTitle("Actor Details - " + actor.getName());
        setSize(600, 500);
        setIconImage(new ImageIcon("src/main/resources/Images/IMDB_Logo.png").getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents(actor);
        setVisible(true);
    }

    private void initComponents(Actor actor) {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        // Common details
        addFormattedLabel(detailsPanel, "Name: " + actor.getName(), Font.BOLD, 16, gbc);
        addFormattedLabel(detailsPanel, "Number of Performances: " + actor.getNumberOfPerformances(), Font.PLAIN, 14, gbc);
        addFormattedLabel(detailsPanel, "Average Rating: " + (actor.getAverageRating() != 0.0 ? actor.getAverageRating() : "No ratings yet"), Font.PLAIN, 14, gbc);

        // Biography
        if(actor.getBiography() != null && !actor.getBiography().isEmpty())
            addFormattedLabel(detailsPanel, "Biography: " + actor.getBiography(), Font.PLAIN, 14, gbc);

        // Roles
        if (actor.getPerformances() != null && !actor.getPerformances().isEmpty()) {
            addFormattedLabel(detailsPanel, "Roles:", Font.BOLD, 16, gbc);

            for (Role role : actor.getPerformances()) {
                addFormattedLabel(detailsPanel, "   " + role.displayInfo(), Font.PLAIN, 14, gbc);
            }
        }
        if(actor.getRatings() != null && !actor.getRatings().isEmpty()) {
            addFormattedLabel(detailsPanel, "Ratings:", Font.BOLD, 16, gbc);

            for (Rating rating : actor.getRatings()) {
                addFormattedLabel(detailsPanel, "   " + rating.getUsername() + " (" + rating.getRating() + ") commented: " + rating.getComment(), Font.PLAIN, 14, gbc);
            }
        }
        // Add the details panel to the frame
        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        add(scrollPane);
    }

    private void addFormattedLabel(JPanel panel, String text, int style, int size, GridBagConstraints gbc) {
        JLabel label = new JLabel(text);
        Font font = new Font(Font.SANS_SERIF, style, size);
        label.setFont(font);
        panel.add(createFormattedLabel(label, gbc), gbc);
        gbc.gridy++; // Move to the next row
    }

    private JPanel createFormattedLabel(JLabel label, GridBagConstraints gbc) {
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelPanel.add(label);
        return labelPanel;
    }
}
