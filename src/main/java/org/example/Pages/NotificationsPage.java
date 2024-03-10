package org.example.Pages;

import org.example.Classes.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NotificationsPage extends JFrame implements ActionListener {

    private JButton clearButton;
    private User user;

    public NotificationsPage(User user) {
        this.user = user;
        setTitle("IMDb - Notifications");
        setSize(400, 200);
        setIconImage(new ImageIcon("src/main/resources/Images/IMDB_Logo.png").getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents(user);
        setVisible(true);
    }

    private void initComponents(User user) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Notifications for " + user.getUsername());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel, BorderLayout.NORTH);


        JPanel notificationPanel = new JPanel();
        notificationPanel.setLayout(new BoxLayout(notificationPanel, BoxLayout.Y_AXIS));

        if(user.getNotifications()!=null&&!user.getNotifications().isEmpty())
            for (String notification : user.getNotifications()) {
                JLabel notificationLabel = new JLabel(" • " + notification);
                notificationLabel.setHorizontalAlignment(SwingConstants.LEFT);
                notificationLabel.setFont(new Font("Arial", Font.PLAIN, 14));
                notificationPanel.add(notificationLabel);
            }
        JScrollPane scrollPane = new JScrollPane(notificationPanel);
        add(scrollPane, BorderLayout.CENTER);

        clearButton = new JButton("Clear Notifications");
        clearButton.addActionListener(this);
        clearButton.setFont(new Font("Arial", Font.PLAIN, 14));
        clearButton.setBackground(new Color(255, 69, 0));
        clearButton.setForeground(Color.WHITE);
        add(clearButton, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == clearButton) {
            this.user.getNotifications().clear();
            this.dispose();
        }
    }

}
