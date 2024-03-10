package org.example.Pages;

import org.example.Classes.*;
import org.example.Enums.AccountType;
import org.example.Enums.RequestTypes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

public class DeleteUserPage extends JFrame implements ActionListener {

    JFrame frame;
    JComboBox<String> comboBox;
    JTextArea DetailsTextArea;
    JButton selectButton;
    JButton deleteButtom;
    Staff staff;

    public DeleteUserPage(Staff staff) {
        this.staff= staff;
        frame = new JFrame("IMDb - Delete");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
        frame.setIconImage(image.getImage());
        frame.setLayout(null);
        frame.setResizable(false);

        SortedSet<String> usernames = new TreeSet<>();
        for(User user : IMDB.getInstance().getUsers()){
            if(!user.getUsername().equals(staff.getUsername())&&!user.getAccountType().equals(AccountType.ADMIN))
                usernames.add(user.getAccountType()+" "+user.getUsername());
        }

        if(usernames.isEmpty()){
            JOptionPane.showMessageDialog(null, "There are no users to delete!", "Error", JOptionPane.ERROR_MESSAGE);
            frame.dispose();
            return;
        }

        comboBox = new JComboBox<>(usernames.toArray(new String[0]));
        comboBox.setBounds(25, 10, 300, 30);
        comboBox.setSelectedIndex(-1);
        comboBox.addActionListener(this);
        frame.add(comboBox);

        DetailsTextArea = new JTextArea();
        DetailsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(DetailsTextArea);
        scrollPane.setBounds(25, 50, 300, 150);
        frame.add(scrollPane);

        selectButton = new JButton("Details");
        selectButton.setBounds(25, 220, 140, 25);
        selectButton.addActionListener(this);
        frame.add(selectButton);

        deleteButtom = new JButton("Delete");
        deleteButtom.setBounds(185, 220, 140, 25);
        deleteButtom.addActionListener(this);
        frame.add(deleteButtom);

        frame.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == comboBox) {
            DetailsTextArea.setText("");
            for(User user : IMDB.getInstance().getUsers()){
                if((user.getAccountType()+" "+user.getUsername()).equals(comboBox.getSelectedItem())){
                    DetailsTextArea.append("Username: "+user.getUsername()+"\n");
                    DetailsTextArea.append("Account: "+user.getAccountType()+"\n");
                    DetailsTextArea.append("Name: "+user.getInfo().getName()+"\n");
                    DetailsTextArea.append("Experience: "+user.getExperience()+"\n");
                    if(user instanceof Staff){
                        DetailsTextArea.append("Contributions: ");
                        for(Object obj : ((Staff) user).getAdded()){
                            if(obj instanceof Production)
                                DetailsTextArea.append("   "+((Production) obj).getTitle()+", ");
                            if(obj instanceof Actor)
                                DetailsTextArea.append("   "+((Actor) obj).getName()+", ");
                        }
                    }
                    break;
                }
            }
        }
        if (actionEvent.getSource() == selectButton){
            if(comboBox.getSelectedIndex() == -1){
                JOptionPane.showMessageDialog(null, "Please select a user!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            for(User user : IMDB.getInstance().getUsers()){
                if((user.getAccountType()+" "+user.getUsername()).equals(comboBox.getSelectedItem())){
                    new UserDetailsPage(user);
                    break;
                }
            }
        }
        if (actionEvent.getSource() == deleteButtom){
            if(comboBox.getSelectedIndex() == -1){
                JOptionPane.showMessageDialog(null, "Please select a user!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String[] opt = {"Yes", "No"};
            String option = (String) comboBox.getSelectedItem();
            String[] options = option.split(" ");
            int choice = JOptionPane.showOptionDialog(null, "Are you sure you want to delete "+options[1]+" ?", "Delete", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, opt, opt);
            if(choice==0){
                for(User user : IMDB.getInstance().getUsers()){
                    if((user.getAccountType()+" "+user.getUsername()).equals(comboBox.getSelectedItem())){
                        if(!(user instanceof Staff)) {
                            for (Production production : IMDB.getInstance().getProductions()) {
                                List<Rating> toRemove = new ArrayList<>();
                                for (Rating rating : production.getRatings()) {
                                    if (rating.getUsername().equals(user.getUsername()))
                                        toRemove.add(rating);
                                }
                                for (Rating rating : toRemove)
                                    production.getRatings().remove(rating);
                            }
                            for (Actor actor : IMDB.getInstance().getActors()) {
                                List<Rating> toRemove = new ArrayList<>();
                                for (Rating rating : actor.getRatings()) {
                                    if (rating.getUsername().equals(user.getUsername()))
                                        toRemove.add(rating);
                                }
                                for (Rating rating : toRemove)
                                    actor.getRatings().remove(rating);
                            }
                        }
                        if(user instanceof Contributor contributor){
                            for(Object obj : contributor.getAdded()) {
                                if(obj instanceof Production)
                                    Admin.RequestHolder.addReq(new Request(RequestTypes.OTHERS, LocalDateTime.now(),"DELETED_ACCOUNT","ADMIN","Contributor "+user.getUsername()+" added "+((Production) obj).getTitle()+" and his account has been deleted",((Production) obj).getTitle(),null));
                                if(obj instanceof Actor)
                                    Admin.RequestHolder.addReq(new Request(RequestTypes.OTHERS,LocalDateTime.now(),"DELETED_ACCOUNT","ADMIN","Contributor "+user.getUsername()+" added "+((Actor) obj).getName()+" and his account has been deleted",null,((Actor) obj).getName()));
                            }
                        }
                        for(User user1 : IMDB.getInstance().getUsers()){
                            if(user1.getAccountType().equals(AccountType.ADMIN)||user1.getAccountType().equals(AccountType.CONTRIBUTOR))
                            {   Staff staff = (Staff) user1;
                                List<Request> toRemove = new ArrayList<>();
                                if(staff.getRequests()!=null&&!staff.getRequests().isEmpty())
                                    for(Request request : staff.getRequests()){
                                        if(request.getUsername().equals(user.getUsername()))
                                            toRemove.add(request);
                                    }
                                for(Request request : toRemove)
                                    staff.getRequests().remove(request);
                            }
                        }
                        for(Request request : IMDB.getInstance().getRequests()){
                            if(request.getUsername().equals(user.getUsername()))
                                IMDB.getInstance().getRequests().remove(request);
                        }
                        IMDB.getInstance().getUsers().remove(user);

                        frame.dispose();
                        JOptionPane.showMessageDialog(null, "User deleted!", "Delete", JOptionPane.INFORMATION_MESSAGE);
                        break;


                    }
                }
            }
            if(choice==1){
                JOptionPane.showMessageDialog(null, "User not deleted!", "Delete", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
