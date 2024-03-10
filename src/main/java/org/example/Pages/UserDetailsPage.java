package org.example.Pages;

import org.example.Classes.*;
import org.example.Enums.AccountType;

import javax.swing.*;
import java.awt.*;
import java.util.SortedSet;

public class UserDetailsPage extends JFrame {

    public UserDetailsPage(User user) {
        user.calculateExperience();
        setTitle("User Details - " + user.getUsername());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon("src/main/resources/Images/IMDB_Logo.png").getImage());

        initComponents(user);
        setVisible(true);
    }

    private void initComponents(User user) {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        addFormattedLabel(detailsPanel, "Username: " + user.getUsername(), Font.BOLD, 16, gbc);
        addFormattedLabel(detailsPanel, "Account Type: " + user.getAccountType(), Font.BOLD, 14, gbc);

        if (user.getAccountType() != AccountType.ADMIN) {
            addFormattedLabel(detailsPanel, "Experience: " + user.getExperience(), Font.PLAIN, 14, gbc);
        }
        addFormattedLabel(detailsPanel, "Information: ", Font.BOLD, 14, gbc);
        addFormattedLabel(detailsPanel, "   " +"Name: "+ user.getInfo().getName(), Font.PLAIN, 14, gbc);
        addFormattedLabel(detailsPanel, "   " +"Country: "+ user.getInfo().getCountry(), Font.PLAIN, 14, gbc);
        addFormattedLabel(detailsPanel, "   " +"Age: "+ user.getInfo().getAge(), Font.PLAIN, 14, gbc);
        addFormattedLabel(detailsPanel, "   "+"Gender: "+user.getInfo().getGender(), Font.PLAIN, 14, gbc);

        if (user.getFavorites() != null) {
            addFavoritesSection(detailsPanel, user.getFavorites(), gbc);
        } else {
            addFormattedLabel(detailsPanel, "Favorite actors: none", Font.BOLD, 14, gbc);
            addFormattedLabel(detailsPanel, "Favorite productions: none", Font.BOLD, 14, gbc);
        }

        if (user.getAccountType() != AccountType.REGULAR) {
            addStaffSection(detailsPanel, (Staff) user, gbc);
        }

        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        add(scrollPane);
    }

    private void addFavoritesSection(JPanel panel, SortedSet<Object> favorites, GridBagConstraints gbc) {
        addFormattedLabel(panel, "Favorite actors:", Font.BOLD, 14, gbc);
        int actorCount = 0;
        for (Object fav : favorites) {
            if (fav instanceof Actor) {
                actorCount++;
                addFormattedLabel(panel, "   " + ((Actor) fav).getName(), Font.PLAIN, 14, gbc);
            }
        }
        if (actorCount == 0) {
            addFormattedLabel(panel, "   none", Font.PLAIN, 14, gbc);
        }

        addFormattedLabel(panel, "Favorite productions:", Font.BOLD, 14, gbc);
        int productionCount = 0;
        for (Object fav : favorites) {
            if (fav instanceof Production) {
                productionCount++;
                addFormattedLabel(panel, "   " + ((Production) fav).getTitle(), Font.PLAIN, 14, gbc);
            }
        }
        if (productionCount == 0) {
            addFormattedLabel(panel, "   none", Font.PLAIN, 14, gbc);
        }
    }

    private void addStaffSection(JPanel panel, Staff staff, GridBagConstraints gbc) {
        if (staff.getAdded() != null && !staff.getAdded().isEmpty()) {
            addFormattedLabel(panel, "Added productions:", Font.BOLD, 14, gbc);
            int productionCount = 0;
            for (Object object : staff.getAdded()) {
                if (object instanceof Production) {
                    productionCount++;
                    addFormattedLabel(panel, "   " + ((Production) object).getTitle(), Font.PLAIN, 14, gbc);
                }
            }
            if (productionCount == 0) {
                addFormattedLabel(panel, "   none", Font.PLAIN, 14, gbc);
            }

            addFormattedLabel(panel, "Added actors:", Font.BOLD, 14, gbc);
            int actorCount = 0;
            for (Object object : staff.getAdded()) {
                if (object instanceof Actor) {
                    actorCount++;
                    addFormattedLabel(panel, "   " + ((Actor) object).getName(), Font.PLAIN, 14, gbc);
                }
            }
            if (actorCount == 0) {
                addFormattedLabel(panel, "   none", Font.PLAIN, 14, gbc);
            }
        } else {
            addFormattedLabel(panel, "Added productions: none", Font.BOLD, 14, gbc);
            addFormattedLabel(panel, "Added actors: none", Font.BOLD, 14, gbc);
        }
    }

    private void addFormattedLabel(JPanel panel, String text, int style, int size, GridBagConstraints gbc) {
        JLabel label = new JLabel(text);
        Font font = new Font(Font.SANS_SERIF, style, size);
        label.setFont(font);
        panel.add(createFormattedLabel(label), gbc);
        gbc.gridy++; // Move to the next row
    }

    private JPanel createFormattedLabel(JLabel label) {
        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        labelPanel.add(label);
        return labelPanel;
    }
}
