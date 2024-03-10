package org.example.Pages;

import org.example.Classes.*;
import org.example.Enums.AccountType;
import org.example.Enums.RequestTypes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

public class RequestPage extends JFrame implements ActionListener {

    JComboBox<String> comboBox;
    JButton submitButton;
    JButton deleteButton;
    JTextArea reviewTextArea;
    Integer choice;
    User user;
    JFrame frame;
    JTextArea reviewDetailsTextArea;
    Request request=null;
    Object object=null;

    public RequestPage(User user) {
        this.user= user;
        String[] options = {"Add Request", "Delete Request"};
        int choice = JOptionPane.showOptionDialog(null, "What do you want to do?", "To add or not to add - That is the question!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            showRequestInputDialog();
        }
        if (choice == 1) {
            showRequestDeleteDialog();
        }
    }

    private void showRequestInputDialog() {

        String[] options = {"ACTOR_ISSUE", "PRODUCTION_ISSUE", "DELETE_ACCOUNT", "OTHER"};
        choice = JOptionPane.showOptionDialog(null, "What do you want to review?", "Select an option",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if(choice==2){
            for(Request request : Admin.RequestHolder.getRequests()){
                if(request.gettype().equals(RequestTypes.DELETE_ACCOUNT)&&request.getUsername().equals(user.getUsername())){
                    JOptionPane.showMessageDialog(null, "You already have a request to delete your account", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
        }
        if(choice==0)
            frame = new JFrame("Add Request - Actor");
        if(choice==1)
            frame = new JFrame("Add Request - Production");
        if(choice==2)
            frame = new JFrame("Add Request - Delete Account");
        if(choice==3)
            frame = new JFrame("Add Request - Other");
        if(frame==null)
            return;
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
        frame.setIconImage(image.getImage());

        SortedSet<String> options2 = new TreeSet<>();

        if(choice==0)
            for(User user1 : IMDB.getInstance().getUsers()){
                if(user1.getAccountType().equals(AccountType.CONTRIBUTOR)||user1.getAccountType().equals(AccountType.ADMIN)){
                    Staff staff = (Staff) user1;

                    if(staff.getAdded()!=null&&!staff.getAdded().isEmpty()&&!staff.getUsername().equals(user.getUsername()))
                        for(Object object : staff.getAdded()){
                            if(object instanceof Actor actor)
                                options2.add(actor.getName());
                        }
                    if(staff.getRequests()!=null&&!staff.getRequests().isEmpty())
                        for(Request request : staff.getRequests()){
                            if(request.gettype().equals(RequestTypes.ACTOR_ISSUE))
                                options2.remove(request.getActor_name());
                        }
                }
            }
        if(choice==1)
        {
            for(User user1 : IMDB.getInstance().getUsers()){
                if(user1.getAccountType().equals(AccountType.CONTRIBUTOR)||user1.getAccountType().equals(AccountType.ADMIN)){
                    Staff staff = (Staff) user1;

                    if(staff.getAdded()!=null&&!staff.getAdded().isEmpty()&&!staff.getUsername().equals(user.getUsername()))
                        for(Object object : staff.getAdded()){
                            if(object instanceof Production production)
                                options2.add(production.getTitle());
                        }
                    if(staff.getRequests()!=null&&!staff.getRequests().isEmpty())
                        for(Request request : staff.getRequests()){
                            if(request.gettype().equals(RequestTypes.MOVIE_ISSUE))
                                options2.remove(request.getMovie_name());
                        }
                }
            }
        }
        if(choice==0||choice==1){
        comboBox = new JComboBox<>(options2.toArray(new String[0]));
        comboBox.setBounds(25, 10, 300, 30);
        frame.add(comboBox);
        }
        if(choice==2||choice==3){
            JPanel panel = new JPanel();
            panel.setBounds(25, 10, 300, 30);
            JLabel label = new JLabel("Description: ");
            panel.add(label);
            frame.add(panel);
        }

        reviewTextArea = new JTextArea(5, 20);
        reviewTextArea.setBounds(25, 60, 300, 150);
        reviewTextArea.setLineWrap(true);
        reviewTextArea.setWrapStyleWord(true);
        reviewTextArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        reviewTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        reviewTextArea.setText("Type here the request details: ");
        frame.add(reviewTextArea);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitButton.setBounds(25, 220, 300, 25);
        submitButton.setFocusable(false);
        frame.add(submitButton);

        frame.setVisible(true);

    }
    private void showRequestDeleteDialog() {

        frame = new JFrame("Delete Request");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
        frame.setIconImage(image.getImage());


        SortedSet<String> options2 = new TreeSet<>();
        for(User user1 : IMDB.getInstance().getUsers()){
            if(user1.getAccountType().equals(AccountType.CONTRIBUTOR)||user1.getAccountType().equals(AccountType.ADMIN)){
                Staff staff = (Staff) user1;
                if(staff.getRequests()!=null&&!staff.getRequests().isEmpty())
                    for(Request request : staff.getRequests()){
                        if(request.gettype().equals(RequestTypes.ACTOR_ISSUE))
                            options2.add("ACTOR_ISSUE: "+request.getActor_name());
                        if(request.gettype().equals(RequestTypes.MOVIE_ISSUE))
                            options2.add("PRODUCTION_ISSUE: "+request.getMovie_name());
                    }
            }
        }
        for(Request request : Admin.RequestHolder.getRequests()){
            if(request.gettype().equals(RequestTypes.DELETE_ACCOUNT)&&request.getUsername().equals(user.getUsername()))
                options2.add("DELETE_ACCOUNT: "+request.getDescription());
            if(request.gettype().equals(RequestTypes.OTHERS)&&request.getUsername().equals(user.getUsername()))
                options2.add("OTHER: "+request.getDescription());
        }

        if(options2.isEmpty()){
            JOptionPane.showMessageDialog(null, "No request to delete!");
            frame.dispose();
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

        deleteButton = new JButton("Submit");
        deleteButton.addActionListener(this);
        deleteButton.setBounds(25, 220, 300, 25);
        deleteButton.setFocusable(false);
        frame.add(deleteButton);


        frame.setVisible(true);

    }

    private void showAddedRequestDialog() {
        // Handle the add review logic here

        JOptionPane.showMessageDialog(null, "Request added", "Request Added", JOptionPane.INFORMATION_MESSAGE);

    }
    private void showDeleteRequestDialog() {
        // Handle the delete review logic here
        JOptionPane.showMessageDialog(null, "Request deleted", "Request Deleted", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()== comboBox){


            reviewDetailsTextArea.setText("Type here the review details: \n");
            String option = (String) comboBox.getSelectedItem();
            assert option != null;
            String[] options = option.split(": ");
            if(options[0].equals("ACTOR_ISSUE")){
                for(User user1 : IMDB.getInstance().getUsers()){
                    if(user1.getAccountType().equals(AccountType.CONTRIBUTOR)||user1.getAccountType().equals(AccountType.ADMIN)){
                        Staff staff = (Staff) user1;
                        if(staff.getRequests()!=null&&!staff.getRequests().isEmpty())
                            for(Request request : staff.getRequests()){
                                if(request.gettype().equals(RequestTypes.ACTOR_ISSUE)&&request.getActor_name().equals(options[1])){
                                    this.object=staff;
                                    this.request=request;
                                    reviewDetailsTextArea.append("Type: "+request.gettype()+"\n");
                                    reviewDetailsTextArea.append("Actor: "+request.getActor_name()+"\n");
                                    reviewDetailsTextArea.append("User: "+request.getUsername()+"\n");
                                    reviewDetailsTextArea.append("Admin: "+request.getAdmin()+"\n");
                                    reviewDetailsTextArea.append("Date: "+request.getTime()+"\n");
                                    reviewDetailsTextArea.append("Description: "+request.getDescription()+"\n");
                                    return;
                                }
                            }
                    }
                }
            }
            if(options[0].equals("PRODUCTION_ISSUE")){
                for(User user1 : IMDB.getInstance().getUsers()){
                    if(user1.getAccountType().equals(AccountType.CONTRIBUTOR)||user1.getAccountType().equals(AccountType.ADMIN)){
                        Staff staff = (Staff) user1;
                        if(staff.getRequests()!=null&&!staff.getRequests().isEmpty())
                            for(Request request : staff.getRequests()){
                                if(request.gettype().equals(RequestTypes.MOVIE_ISSUE)&&request.getMovie_name().equals(options[1])){
                                    this.object=staff;
                                    this.request=request;
                                    reviewDetailsTextArea.append("Type: "+request.gettype()+"\n");
                                    reviewDetailsTextArea.append("Movie: "+request.getMovie_name()+"\n");
                                    reviewDetailsTextArea.append("User: "+request.getUsername()+"\n");
                                    reviewDetailsTextArea.append("Admin: "+request.getAdmin()+"\n");
                                    reviewDetailsTextArea.append("Date: "+request.getTime()+"\n");
                                    reviewDetailsTextArea.append("Description: "+request.getDescription()+"\n");
                                    return;
                                }
                            }
                    }
                }
            }
            if(options[0].equals("DELETE_ACCOUNT")){
                for(Request request : Admin.RequestHolder.getRequests()){
                    if(request.gettype().equals(RequestTypes.DELETE_ACCOUNT)&&request.getUsername().equals(this.user.getUsername())){
                        this.request=request;
                        reviewDetailsTextArea.append("Type: "+request.gettype()+"\n");
                        reviewDetailsTextArea.append("User: "+request.getUsername()+"\n");
                        reviewDetailsTextArea.append("Admin: "+request.getAdmin()+"\n");
                        reviewDetailsTextArea.append("Date: "+request.getTime()+"\n");
                        reviewDetailsTextArea.append("Description: "+request.getDescription()+"\n");
                        return;
                    }
                }
            }
            if(options[0].equals("OTHER")){
                for(Request request : Admin.RequestHolder.getRequests()){
                    if(request.gettype().equals(RequestTypes.OTHERS)&&request.getUsername().equals(this.user.getUsername())){
                        this.request=request;
                        reviewDetailsTextArea.append("Type: "+request.gettype()+"\n");
                        reviewDetailsTextArea.append("User: "+request.getUsername()+"\n");
                        reviewDetailsTextArea.append("Admin: "+request.getAdmin()+"\n");
                        reviewDetailsTextArea.append("Date: "+request.getTime()+"\n");
                        reviewDetailsTextArea.append("Description: "+request.getDescription()+"\n");
                        return;
                    }
                }
            }
        }
        if(actionEvent.getSource() == deleteButton){
            if(object==null&&request==null){
                JOptionPane.showMessageDialog(null, "You have not selected a review to delete", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        if(object instanceof Staff staff){
                staff.getRequests().remove(request);
                if(staff.getNotifications()!=null)
                    staff.getNotifications().remove("Ai o cerere noua de la \""+request.getUsername()+"\" -> "+request.getDescription());
                showDeleteRequestDialog();
                frame.dispose();
                return;
            }
        Admin.RequestHolder.getRequests().remove(request);
        frame.dispose();
        showDeleteRequestDialog();
        return;
        }
        if(actionEvent.getSource() == submitButton){
            String reviewText = reviewTextArea.getText();

            if(choice==0){
                String option = (String) comboBox.getSelectedItem();
                for(User user1 : IMDB.getInstance().getUsers()){
                    if(user1.getAccountType().equals(AccountType.CONTRIBUTOR)||user1.getAccountType().equals(AccountType.ADMIN)){
                        Staff staff = (Staff) user1;
                        if(staff.getAdded()!=null&&!staff.getAdded().isEmpty())
                            for(Object object : staff.getAdded()){
                                if(object instanceof Actor actor)
                                    if(actor.getName().equals(option)){
                                        Request request = new Request(RequestTypes.ACTOR_ISSUE, java.time.LocalDateTime.now(), this.user.getUsername(), staff.getUsername(), reviewText, null, actor.getName());
                                        if(staff.getRequests()==null)
                                            staff.setRequests(new ArrayList<>());
                                        request.addObserver(staff);
                                        request.notifyObserver("Ai o cerere noua de la \""+request.getUsername()+"\" -> "+request.getDescription());
                                        request.addObserver(user);
                                        staff.getRequests().add(request);
                                        showAddedRequestDialog();
                                        frame.dispose();
                                        return;
                                    }
                            }
                    }
                }
            }
            if(choice==1){
                String option = (String) comboBox.getSelectedItem();
                for(User user1 : IMDB.getInstance().getUsers()){
                    if(user1.getAccountType().equals(AccountType.CONTRIBUTOR)||user1.getAccountType().equals(AccountType.ADMIN)){
                        Staff staff = (Staff) user1;
                        if(staff.getAdded()!=null&&!staff.getAdded().isEmpty())
                            for(Object object : staff.getAdded()){
                                if(object instanceof Production production)
                                    if(production.getTitle().equals(option)){
                                        Request request = new Request(RequestTypes.MOVIE_ISSUE, java.time.LocalDateTime.now(), this.user.getUsername(), staff.getUsername(), reviewText, production.getTitle(), null);
                                        if(staff.getRequests()==null)
                                            staff.setRequests(new ArrayList<>());
                                        request.addObserver(staff);
                                        request.notifyObserver("Ai o cerere noua de la \""+request.getUsername()+"\" -> "+request.getDescription());
                                        request.addObserver(user);
                                        staff.getRequests().add(request);
                                        showAddedRequestDialog();
                                        frame.dispose();
                                        return;
                                    }
                            }
                    }
                }
            }
            if(choice==2){
                Request request = new Request(RequestTypes.DELETE_ACCOUNT, java.time.LocalDateTime.now(), this.user.getUsername(), "ADMIN", reviewText, null, null);
                request.addObserver(this.user);
                Admin.RequestHolder.addReq(request);
                showAddedRequestDialog();
                frame.dispose();
                return;
            }
            if(choice==3){
                Request request = new Request(RequestTypes.OTHERS, java.time.LocalDateTime.now(), this.user.getUsername(), "ADMIN", reviewText, null, null);
                request.addObserver(this.user);
                Admin.RequestHolder.addReq(request);
                showAddedRequestDialog();
                frame.dispose();
            }
        }
    }
}
