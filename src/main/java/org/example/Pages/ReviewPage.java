package org.example.Pages;

import org.example.Classes.*;
import org.example.Enums.AccountType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class ReviewPage extends JFrame implements ActionListener {

    JComboBox<String> comboBox;
    JButton submitButton;
    JSlider slider;
    JTextArea reviewTextArea;
    Integer choice;
    User user;
    JFrame frame;
    JTextArea reviewDetailsTextArea;
    Rating rating=null;
    Object object=null;

    public ReviewPage(User user) {
        this.user= user;
        String[] options = {"Add Review", "Delete Review"};
        int choice = JOptionPane.showOptionDialog(null, "What do you want to do?", "To add or not to add - That is the question!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            showReviewInputDialog();
        }
        if (choice == 1) {
            showReviewDeleteDialog();
        }
    }

    private void showReviewInputDialog() {

        String[] options = {"Actor", "Production"};
        choice = JOptionPane.showOptionDialog(null, "What do you want to review?", "Select an option",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(choice==0)
            frame = new JFrame("Add Review - Actor");
        else
            frame = new JFrame("Add Review - Production");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
        frame.setIconImage(image.getImage());

        SortedSet<String> options2 = new TreeSet<>();
        if (choice == 0) {
            // Adding actor names to options
            for (Actor actor : IMDB.getInstance().getActors()) {
                int contor=0;
                for(Rating rating : actor.getRatings())
                     if(rating.getUsername().equals(user.getUsername())){
                         contor=1;
                         break;
                     }
                if(contor==0)
                    options2.add(actor.getName());
            }
        }
        if (choice == 1) {
            // Adding production titles to options
            for (Production production : IMDB.getInstance().getProductions()) {
                int contor=0;
                for(Rating rating : production.getRatings())
                    if(rating.getUsername().equals(user.getUsername())){
                        contor=1;
                        break;
                    }
                if(contor==0)
                    options2.add(production.getTitle());
            }
        }
        if(options2.isEmpty()){
            JOptionPane.showMessageDialog(null, "You have already reviewed all the "+(choice==0?"actors":"productions")+"!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        comboBox = new JComboBox<>(options2.toArray(new String[0]));
        comboBox.setBounds(25, 10, 300, 30);
        frame.add(comboBox);

        slider = new JSlider(JSlider.HORIZONTAL, 0, 10, 5);
        slider.setMajorTickSpacing(1);
        slider.setPaintLabels(true);
        slider.setBounds(25, 50, 300, 50);
        frame.add(slider);



        reviewTextArea = new JTextArea(5, 20);
        reviewTextArea.setBounds(25, 110, 300, 100);
        reviewTextArea.setLineWrap(true);
        reviewTextArea.setWrapStyleWord(true);
        reviewTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        reviewTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        frame.add(reviewTextArea);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setBounds(25, 220, 300, 25);
        submitButton.setFocusable(false);
        frame.add(submitButton);

        frame.setVisible(true);
    }
    private void showReviewDeleteDialog() {

        frame = new JFrame("Delete Review");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
        frame.setIconImage(image.getImage());
        frame.setResizable(false);


        SortedSet<String> options2 = new TreeSet<>();
        for (Actor actor : IMDB.getInstance().getActors()) {
            for(Rating rating : actor.getRatings())
                if(rating.getUsername().equals(user.getUsername())){
                    options2.add("Actor: "+actor.getName());
                    break;
                }
        }
        for (Production production : IMDB.getInstance().getProductions()) {
            for(Rating rating : production.getRatings())
                if(rating.getUsername().equals(user.getUsername())){
                    options2.add("Production: "+production.getTitle());
                    break;
                }
        }
        if(options2.isEmpty()){
            JOptionPane.showMessageDialog(null, "You have not reviewed any actor or production!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        comboBox = new JComboBox<>(options2.toArray(new String[0]));
        comboBox.setBounds(25, 10, 300, 30);
        comboBox.addActionListener(this);
        frame.add(comboBox);


        reviewDetailsTextArea = new JTextArea();
        reviewDetailsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reviewDetailsTextArea);
        scrollPane.setBounds(25, 50, 300, 150);
        frame.add(scrollPane);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setBounds(25, 220, 300, 25);
        submitButton.setFocusable(false);
        frame.add(submitButton);


        frame.setVisible(true);

    }

    private void showAddedReviewDialog() {
        // Handle the add review logic here

        JOptionPane.showMessageDialog(null, "Review added", "Review Added", JOptionPane.INFORMATION_MESSAGE);

    }
    private void showDeleteReviewDialog() {
        // Handle the delete review logic here
        JOptionPane.showMessageDialog(null, "Review deleted", "Review Deleted", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()== comboBox){

            String option = (String) comboBox.getSelectedItem();
            reviewDetailsTextArea.setText("");  // Clear previous details
            for (Actor actor : IMDB.getInstance().getActors()) {
                if (option.equals("Actor: " + actor.getName())) {
                    for (Rating rating : actor.getRatings()) {
                        if (rating.getUsername().equals(user.getUsername())) {
                            this.rating=rating;
                            this.object=actor;
                            if(rating.getComment()!=null&&!rating.getComment().isEmpty())
                                reviewDetailsTextArea.append("\nRating: " + rating.getRating() + "\n\nComment: " + rating.getComment());
                            else if(rating.getComment()!=null||rating.getComment().isEmpty())
                                reviewDetailsTextArea.append("\nRating: " + rating.getRating() + "\n\nComment: No comment");
                            break;
                        }
                    }
                    break;  // Exit the loop since we found the actor
                }
            }
            for (Production production : IMDB.getInstance().getProductions()) {
                if (option.equals("Production: " + production.getTitle())) {
                    for (Rating rating : production.getRatings()) {
                        if (rating.getUsername().equals(user.getUsername())) {
                            this.rating=rating;
                            this.object=production;
                            if(rating.getComment()!=null&&!rating.getComment().isEmpty())
                                reviewDetailsTextArea.append("\nRating: " + rating.getRating() + "\n\nComment: " + rating.getComment());
                            else if(rating.getComment()!=null||rating.getComment().isEmpty())
                                reviewDetailsTextArea.append("\nRating: " + rating.getRating() + "\n\nComment: No comment");
                            break;
                        }
                    }
                    break;  // Exit the loop since we found the production
                }
            }
        }
        if(actionEvent.getSource() == submitButton&&frame.getTitle().equals("Delete Review")){
            if(object==null||rating==null){
                JOptionPane.showMessageDialog(null, "You have not selected a review to delete", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if(object instanceof Production production) {
                for (User user1 : IMDB.getInstance().getUsers()) {
                    if (user1.getNotifications() != null) {
                        user1.getNotifications().remove("Productia \"" + production.getTitle() + "\" la care ai dat un review, a mai primit un review de la utilizatorul \"" + rating.getUsername() + "\" -> " + rating.getRating());
                        user1.getNotifications().remove("Productia \"" + production.getTitle() + "\" pe care l-ai adaugat a primit un review de la utilizatorul \"" + rating.getUsername() + "\" -> " + rating.getRating());
                        user1.getNotifications().remove("Productia \"" + production.getTitle() + "\" pe care il ai in lista de favorite a primit un review de la utilizatorul \"" + rating.getUsername() + "\" -> " + rating.getRating());
                    }

                }
                production.getRatings().remove(rating);
                user.experienceStrategy=user.addReview;
                user.experienceStrategy.removeexp();
                showDeleteReviewDialog();
                production.calculateRating();
                frame.dispose();
                return;
            }
            if(object instanceof Actor actor){
                for (User user1 : IMDB.getInstance().getUsers()) {
                    if (user1.getNotifications() != null) {
                        user1.getNotifications().remove("Actorul \"" + actor.getName() + "\" la care ai dat un review, a mai primit un review de la utilizatorul \"" + rating.getUsername() + "\" -> " + rating.getRating());
                        user1.getNotifications().remove("Actorul \"" + actor.getName() + "\" pe care l-ai adaugat a primit un review de la utilizatorul \"" + rating.getUsername() + "\" -> " + rating.getRating());
                        user1.getNotifications().remove("Actorul \"" + actor.getName() + "\" pe care il ai in lista de favorite a primit un review de la utilizatorul \"" + rating.getUsername() + "\" -> " + rating.getRating());
                    }
                }
                actor.getRatings().remove(rating);
                user.experienceStrategy=user.addReview;
                user.experienceStrategy.removeexp();
                showDeleteReviewDialog();
                actor.calculateRating();
                frame.dispose();
                return;
            }
        }
        if(actionEvent.getSource() == submitButton){
            String reviewText = reviewTextArea.getText();
            int rating = slider.getValue();
            String option = (String) comboBox.getSelectedItem();
            if(choice==0){
                for(Actor actor : IMDB.getInstance().getActors()){
                    if(actor.getName().equals(option)){
                        Rating rating1= new Rating(user.getUsername(),rating,reviewText);
                        for (Rating rating2 : actor.getRatings()) {
                            rating2.notifyObserver("Actorul \"" + actor.getName() + "\" la care ai dat un review, a mai primit un review de la utilizatorul \"" + user.getUsername() + "\" -> " + rating);
                        }
                        for (User user1 : IMDB.getInstance().getUsers()) {
                            if (user1.getNotifications() == null)
                                user1.setNotifications(new ArrayList<>());
                            if ((user1.getAccountType() == AccountType.ADMIN || user1.getAccountType() == AccountType.CONTRIBUTOR) && ((Staff) user1).getAdded().contains(actor))
                                user1.getNotifications().add("Actorul \"" + actor.getName() + "\" pe care l-ai adaugat a primit un review de la utilizatorul \"" + user.getUsername() + "\" -> " + rating);
                            if (user1.getFavorites().contains(actor))
                                user1.getNotifications().add("Actorul \"" + actor.getName() + "\" pe care il ai in lista de favorite a primit un review de la utilizatorul \"" + user.getUsername() + "\" -> " + rating);
                        }
                        rating1.addObserver(user);
                        actor.getRatings().add(rating1);
                        user.experienceStrategy = user.addReview;
                        user.experienceStrategy.addexp();
                        actor.calculateRating();
                        showAddedReviewDialog();
                        frame.dispose();
                        return;
                    }
                }
            }
            if(choice==1){
                for (Production production : IMDB.getInstance().getProductions()) {
                    if (production.getTitle().equals(option)) {
                        Rating rating1= new Rating(user.getUsername(),rating,reviewText);
                        for (Rating rating2 : production.getRatings()) {
                            rating2.notifyObserver("Productia \"" + production.getTitle() + "\" la care ai dat un review, a mai primit un review de la utilizatorul \"" + user.getUsername() + "\" -> " + rating);
                        }
                        for (User user1 : IMDB.getInstance().getUsers()) {
                            if (user1.getNotifications() == null)
                                user1.setNotifications(new ArrayList<>());
                            if ((user1.getAccountType() == AccountType.ADMIN || user1.getAccountType() == AccountType.CONTRIBUTOR) && ((Staff) user1).getAdded().contains(production))
                                user1.getNotifications().add("Productia \"" + production.getTitle() + "\" pe care ai adaugat-o a primit un review de la utilizatorul \"" + user.getUsername() + "\" -> " + rating);
                            if (user1.getFavorites().contains(production))
                                user1.getNotifications().add("Productia \"" + production.getTitle() + "\" pe care o ai in lista de favorite a primit un review de la utilizatorul \"" + user.getUsername() + "\" -> " + rating);
                        }
                        rating1.addObserver(user);
                        production.getRatings().add(rating1);
                        user.experienceStrategy = user.addReview;
                        user.experienceStrategy.addexp();
                        production.calculateRating();
                        showAddedReviewDialog();
                        frame.dispose();
                        return;
                    }
                }
            }
        }
    }
}
