package org.example.Pages;


import org.example.Classes.*;
import org.example.Enums.AccountType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;

public class Menu extends JFrame implements ActionListener {

    User user;
    JFrame frame;
    JButton productionDetailsButton;
    JButton actorsDetailsButton;
    JButton notificationsButton;
    JButton searchButton;
    JButton favoritesButton;
    JButton searchUserButton;
    JButton logoutButton;
    JButton exitButton;
    JButton aux1;
    JButton aux2;
    JButton aux3;
    JButton aux4;

    public Menu(User user) {
        this.user = user;
        frame = new JFrame("IMDb - Menu");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
        frame.setIconImage(image.getImage());
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                // Execută funcția de salvare a datelor aici
                IMDB.getInstance().save_data("src/main/resources/output/");
                // Închide fereastra
                frame.dispose();
            }
        });

        JLabel label1 = new JLabel("Welcome, " + user.getUsername() + "!");
        label1.setBounds(0, 0, 80, 25);
        label1.setHorizontalAlignment(JLabel.CENTER);
        frame.add(label1, BorderLayout.NORTH);

        productionDetailsButton = new JButton("View productions details");
        productionDetailsButton.addActionListener(this);
        productionDetailsButton.setFocusable(false);
        actorsDetailsButton = new JButton("View actors details");
        actorsDetailsButton.addActionListener(this);
        actorsDetailsButton.setFocusable(false);
        notificationsButton = new JButton("View notifications");
        notificationsButton.addActionListener(this);
        notificationsButton.setFocusable(false);
        searchButton = new JButton("Search for actor/production");
        searchButton.addActionListener(this);
        searchButton.setFocusable(false);
        favoritesButton = new JButton("Add/Delete actor/production to/from favorites");
        favoritesButton.addActionListener(this);
        favoritesButton.setFocusable(false);
        searchUserButton = new JButton("Search for a user");
        searchUserButton.addActionListener(this);
        searchUserButton.setFocusable(false);
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        logoutButton.setFocusable(false);
        exitButton = new JButton("Exit");
        exitButton.addActionListener(this);
        exitButton.setFocusable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1));

        panel.add(productionDetailsButton);
        panel.add(actorsDetailsButton);
        panel.add(notificationsButton);
        panel.add(favoritesButton);
        panel.add(searchButton);
        panel.add(searchUserButton);

        if(user.getAccountType().equals(AccountType.REGULAR)){
            aux2 = new JButton("Add/Delete a request");
            aux2.addActionListener(this);
            aux2.setFocusable(false);
            panel.add(aux2);
            aux1 = new JButton("Add/Delete a rating");
            aux1.addActionListener(this);
            aux1.setFocusable(false);
            panel.add(aux1);
        }
        if(user.getAccountType().equals(AccountType.ADMIN)){
            aux1= new JButton("Add/Delete actor/production to/from IMDB");
            aux1.addActionListener(this);
            aux1.setFocusable(false);
            panel.add(aux1);
            aux4 = new JButton("Add/Delete a user to/from IMDB");
            aux4.addActionListener(this);
            aux4.setFocusable(false);
            panel.add(aux4);
            aux3 = new JButton("Update actor/production");
            aux3.addActionListener(this);
            aux3.setFocusable(false);
            panel.add(aux3);
            aux2 = new JButton("View and solve requests");
            aux2.addActionListener(this);
            aux2.setFocusable(false);
            panel.add(aux2);
            frame.setSize(350,350);
        }
        if(user.getAccountType().equals(AccountType.CONTRIBUTOR)){
            aux1 = new JButton("Add/Delete a request");
            aux1.addActionListener(this);
            aux1.setFocusable(false);
            panel.add(aux1);
            aux2 = new JButton("Add/Delete actor/production to/from IMDB");
            aux2.addActionListener(this);
            aux2.setFocusable(false);
            panel.add(aux2);
            aux4 = new JButton("Update actor/production");
            aux4.addActionListener(this);
            aux4.setFocusable(false);
            panel.add(aux4);
            aux3 = new JButton("View and solve requests");
            aux3.addActionListener(this);
            aux3.setFocusable(false);
            panel.add(aux3);
            frame.setSize(350,350);
        }


        panel.add(logoutButton);
        panel.add(exitButton);
        frame.add(panel, BorderLayout.CENTER);
        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        switch (actionEvent.getActionCommand()){
            case "View productions details":
                new ProductionDisplayPage(IMDB.getInstance().getProductions());
                break;
            case "View actors details":
                new ActorDisplayPage(IMDB.getInstance().getActors());
                break;
            case "View notifications":
                new NotificationsPage(user);
                break;
            case "Search for actor/production":
                new SearchObjectPage();
                break;
            case "Add/Delete actor/production to/from favorites":
                new FavouritesPage(user);
                break;
            case "Search for a user":
                new SearchUserPage();
                break;
            case "Add/Delete a rating":
                new ReviewPage(user);
                break;
            case "Add/Delete a request":
                new RequestPage(user);
                break;
            case "Add/Delete actor/production to/from IMDB":
                new AddDeletePage((Staff) user);
                break;
            case "View and solve requests":
                new RequestSPage((Staff) user);
                break;
            case "Update actor/production":
                new UpdateAddedObjPage((Staff) user);
                break;
            case "Add/Delete a user to/from IMDB":
                new AddDeleteUserPage((Staff) user);
                break;
            case "Logout":
                frame.dispose();
                new LoginPage();
                break;
            case "Exit":
                String [] options = {"Default", "Custom"};
                int choice = JOptionPane.showOptionDialog(null, "Where do you want to save data?", "Last menu ever!",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if(choice==0) {
                    IMDB.getInstance().save_data("src/main/resources/output/");
                    frame.dispose();
                    break;
                }
                if(choice==1)
                {
                    JFrame frame = new JFrame("Save data");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setSize(350, 200);
                    frame.setLocationRelativeTo(null);
                    ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
                    frame.setIconImage(image.getImage());
                    frame.setResizable(false);
                    frame.setLayout(null);

                    JLabel label1 = new JLabel("Enter the path where you want to save data:");
                    label1.setBounds(20, 10, 250, 25);
                    label1.setHorizontalAlignment(JLabel.CENTER);
                    frame.add(label1, BorderLayout.NORTH);

                    JTextField textField = new JTextField("src/main/resources/output/");
                    textField.setBounds(50, 50, 200, 30);
                    frame.add(textField);

                    JButton button = new JButton("Save");
                    button.setBounds(100, 100, 100, 30);
                    button.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            try {
                                File file = new File(textField.getText());
                                FileWriter fileWriter = new FileWriter(file+"new_actors.json");
                                IMDB.getInstance().save_data(textField.getText());
                                frame.dispose();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    frame.add(button);
                    frame.setVisible(true);
                    frame.dispose();
                    break;
                }
        }
    }
}
