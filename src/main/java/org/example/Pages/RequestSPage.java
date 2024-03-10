package org.example.Pages;

import org.example.Classes.*;
import org.example.Enums.AccountType;
import org.example.Enums.RequestTypes;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.SortedSet;
import java.util.TreeSet;

public class RequestSPage extends JFrame implements ActionListener {

    JFrame frame;
    JButton selectButton;
    JComboBox<String> comboBox;
    Staff staff;
    JTextArea DetailsTextArea;
    Request request;

    public RequestSPage(Staff staff) {
        this.staff = staff;
        frame = new JFrame("IMDb - Solve Request");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 300);
        frame.setLocationRelativeTo(null);
        ImageIcon image = new ImageIcon("src/main/resources/Images/IMDB_Logo.png");
        frame.setIconImage(image.getImage());
        frame.setLayout(null);
        frame.setResizable(false);


        SortedSet<String> set = new TreeSet<>();
        if(staff.getRequests()!=null)
            for(Request request : staff.getRequests()){
                if(request.gettype().equals(RequestTypes.ACTOR_ISSUE))
                    set.add("Personal ACT: "+request.getActor_name().replace(" ","_")+" - "+request.getUsername());
                if(request.gettype().equals(RequestTypes.MOVIE_ISSUE))
                    set.add("Personal MOV: "+request.getMovie_name().replace(" ","_")+" - "+request.getUsername());
            }
        if(Admin.RequestHolder.getRequests()!=null)
            for(Request request : Admin.RequestHolder.getRequests()){
                if(request.gettype().equals(RequestTypes.DELETE_ACCOUNT))
                    set.add("Global DEL: "+request.getUsername());
                if(request.gettype().equals(RequestTypes.OTHERS))
                    if(request.getUsername().equals("DELETED_ACCOUNT")){
                        if(request.getMovie_name()!=null&&!request.getMovie_name().isEmpty())
                            set.add("Global OTHER: "+request.getUsername()+" - "+request.getTime()+" - "+request.getMovie_name().replace(" ","_"));
                        if(request.getActor_name()!=null&&!request.getActor_name().isEmpty())
                            set.add("Global OTHER: "+request.getUsername()+" - "+request.getTime()+" - "+request.getActor_name().replace(" ","_"));
                    }
                    else
                        set.add("Global OTHER: "+request.getUsername()+" - "+request.getTime());

            }
        if(set.isEmpty()){
            JOptionPane.showMessageDialog(null, "No requests to display!", "Error", JOptionPane.ERROR_MESSAGE);
            return;}
        comboBox = new JComboBox<>(set.toArray(new String[0]));
        comboBox.setBounds(25, 10, 300, 30);
        comboBox.setSelectedIndex(-1);
        comboBox.addActionListener(this);
        frame.add(comboBox);

        DetailsTextArea = new JTextArea();
        DetailsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(DetailsTextArea);
        scrollPane.setBounds(25, 50, 300, 150);
        frame.add(scrollPane);

        selectButton = new JButton("Select");
        selectButton.setBounds(25, 220, 300, 25);
        selectButton.addActionListener(this);
        frame.add(selectButton);


        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==comboBox){
            if(comboBox.getSelectedItem()==null){
                return;
            }
            DetailsTextArea.setText("");
            String option = (String) comboBox.getSelectedItem();
            String[] options = option.split(" ");
            if(options[0].equals("Personal"))
                for(Request request: staff.getRequests()){
                    if(request.gettype().equals(RequestTypes.ACTOR_ISSUE)&&request.getActor_name().replace(" ","_").equals(options[2])&&request.getUsername().equals(options[4])){
                        this.request = request;
                        DetailsTextArea.append(request.gettype().toString()+"\n");
                        DetailsTextArea.append("Actor: "+request.getActor_name()+"\n");
                        DetailsTextArea.append("From user: "+request.getUsername()+" to Admin: "+request.getAdmin()+"\n");
                        DetailsTextArea.append("Time: "+request.getTime()+"\n");
                        DetailsTextArea.append("Description: "+request.getDescription()+"\n");
                        return;
                    }
                    if(request.gettype().equals(RequestTypes.MOVIE_ISSUE)&&request.getMovie_name().replace(" ","_").equals(options[2])&&request.getUsername().equals(options[4])){
                        this.request = request;
                        DetailsTextArea.append(request.gettype().toString()+"\n");
                        DetailsTextArea.append("Movie: "+request.getMovie_name()+"\n");
                        DetailsTextArea.append("From user: "+request.getUsername()+" to Admin: "+request.getAdmin()+"\n");
                        DetailsTextArea.append("Time: "+request.getTime()+"\n");
                        DetailsTextArea.append("Description: "+request.getDescription()+"\n");
                        return;
                    }
                }

            if(options[0].equals("Global"))
                for(Request request: Admin.RequestHolder.getRequests()){
                    if(request.gettype().equals(RequestTypes.DELETE_ACCOUNT)&&request.getUsername().equals(options[2])){
                        this.request = request;
                        DetailsTextArea.append(request.gettype().toString()+"\n");
                        DetailsTextArea.append("From user: "+request.getUsername()+"\n");
                        DetailsTextArea.append("Time: "+request.getTime()+"\n");
                        DetailsTextArea.append("Description: "+request.getDescription()+"\n");
                        return;
                    }
                    String time = request.getTime().toString();
                    if(request.gettype().equals(RequestTypes.OTHERS)&&request.getUsername().equals(options[2])&&time.equals(options[4])&&request.getMovie_name()!=null&&options[6].equals(request.getMovie_name().replace(" ","_"))) {
                        this.request = request;
                        DetailsTextArea.append(request.gettype().toString() + "\n");
                        DetailsTextArea.append("From user: " + request.getUsername() + "\n");
                        DetailsTextArea.append("Time: " + request.getTime() + "\n");
                        DetailsTextArea.append("Description: " + request.getDescription() + "\n");
                        if (request.getMovie_name() != null && !request.getMovie_name().isEmpty())
                            DetailsTextArea.append("Movie: " + request.getMovie_name() + "\n");
                        if (request.getActor_name() != null && !request.getActor_name().isEmpty())
                            DetailsTextArea.append("Actor: " + request.getActor_name() + "\n");
                        return;
                    }
                    if(request.gettype().equals(RequestTypes.OTHERS)&&request.getUsername().equals(options[2])&&time.equals(options[4])&&request.getActor_name()!=null&&options[6].equals(request.getActor_name().replace(" ","_"))) {
                        this.request = request;
                        DetailsTextArea.append(request.gettype().toString() + "\n");
                        DetailsTextArea.append("From user: " + request.getUsername() + "\n");
                        DetailsTextArea.append("Time: " + request.getTime() + "\n");
                        DetailsTextArea.append("Description: " + request.getDescription() + "\n");
                        if (request.getMovie_name() != null && !request.getMovie_name().isEmpty())
                            DetailsTextArea.append("Movie: " + request.getMovie_name() + "\n");
                        if (request.getActor_name() != null && !request.getActor_name().isEmpty())
                            DetailsTextArea.append("Actor: " + request.getActor_name() + "\n");
                        return;
                    }
                    if(request.gettype().equals(RequestTypes.OTHERS)&&request.getUsername().equals(options[2])&&time.equals(options[4])&&!request.getUsername().equals("DELETED_ACCOUNT")){
                        this.request = request;
                        DetailsTextArea.append(comboBox.getSelectedItem().toString());
                        DetailsTextArea.append(request.gettype().toString()+"\n");
                        DetailsTextArea.append("From user: "+request.getUsername()+"\n");
                        DetailsTextArea.append("Time: "+request.getTime()+"\n");
                        DetailsTextArea.append("Description: "+request.getDescription()+"\n");
                        if(request.getMovie_name()!=null&&!request.getMovie_name().isEmpty())
                            DetailsTextArea.append("Movie: "+request.getMovie_name()+"\n");
                        if(request.getActor_name()!=null&&!request.getActor_name().isEmpty())
                            DetailsTextArea.append("Actor: "+request.getActor_name()+"\n");
                        return;
                    }
                }
        }
        if(actionEvent.getSource()==selectButton){
            if(comboBox.getSelectedItem()==null){
                JOptionPane.showMessageDialog(null, "No request selected!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String[] options = {"Accept Request", "Delete Request"};
            int choice = JOptionPane.showOptionDialog(null, "What do you want to do?", "To add or not to add - That is the question!",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if(choice==0){
                if(request.gettype().equals(RequestTypes.ACTOR_ISSUE)||request.gettype().equals(RequestTypes.MOVIE_ISSUE)){
                    String[] options1 = {"Update", "Delete", "Cancel"};
                    int choice2 = JOptionPane.showOptionDialog(null, "What do you want to do with selected?", "Choose an option",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options1, options1[0]);
                    if(choice2==0){
                        if(request.gettype().equals(RequestTypes.ACTOR_ISSUE)){
                            for(Actor actor : IMDB.getInstance().getActors()){
                                if(actor.getName().equals(request.getActor_name())){
                                    new UpdateActorPage(actor);
                                    break;
                                }
                            }
                        }
                        if(request.gettype().equals(RequestTypes.MOVIE_ISSUE)){
                            for(Production production : IMDB.getInstance().getProductions()){
                                if(production.getTitle().equals(request.getMovie_name())){
                                    new UpdateProductionPage(production);
                                    break;
                                }
                            }
                        }
                    }
                    if(choice2==1){
                        if(request.gettype().equals(RequestTypes.ACTOR_ISSUE)){
                            for(Actor actor : IMDB.getInstance().getActors()){
                                if(actor.getName().equals(request.getActor_name())){
                                    for(User user1 : IMDB.getInstance().getUsers()){
                                        if(user1 instanceof Staff staff1){
                                            if(staff1.getRequests()!=null)
                                                for(Request request : staff1.getRequests()){
                                                    if(request.gettype().equals(RequestTypes.ACTOR_ISSUE)&&request.getActor_name().equals(actor.getName())){
                                                        staff1.getRequests().remove(request);
                                                        break;
                                                    }
                                                }
                                        }
                                        if(user1.getFavorites()!=null)
                                            user1.getFavorites().remove(actor);
                                    }
                                    staff.getAdded().remove(actor);
                                    IMDB.getInstance().getActors().remove(actor);
                                    JOptionPane.showMessageDialog(null, actor.getName()+" was deleted", "Delete", JOptionPane.INFORMATION_MESSAGE);
                                    frame.dispose();
                                    break;
                                }
                            }
                        }
                        if(request.gettype().equals(RequestTypes.MOVIE_ISSUE)){
                            for(Production production : IMDB.getInstance().getProductions()){
                                if(production.getTitle().equals(request.getMovie_name())){
                                    for(User user1 : IMDB.getInstance().getUsers()){
                                        if(user1 instanceof Staff staff1){
                                            if(staff1.getRequests()!=null)
                                                for(Request request : staff1.getRequests()){
                                                    if(request.gettype().equals(RequestTypes.MOVIE_ISSUE)&&request.getMovie_name().equals(production.getTitle())){
                                                        staff1.getRequests().remove(request);
                                                        break;
                                                    }
                                                }
                                        }
                                        if(user1.getFavorites()!=null)
                                            user1.getFavorites().remove(production);
                                    }
                                    staff.getAdded().remove(production);
                                    IMDB.getInstance().getProductions().remove(production);
                                    JOptionPane.showMessageDialog(null, production.getTitle()+" was deleted", "Delete", JOptionPane.INFORMATION_MESSAGE);
                                    frame.dispose();
                                    break;
                                }
                            }
                        }
                    }
                    if(choice2==2){
                        JOptionPane.showMessageDialog(null, "Canceled!", "Canceled!", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                if(request.gettype().equals(RequestTypes.OTHERS)){
                    if(request.getUsername().equals("DELETED_ACCOUNT")) {
                        String[] options1 = {"Keep", "Delete","Cancel"};
                        int choice2 = JOptionPane.showOptionDialog(null, "What do you want to do?", "Choose an option",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options1, options1[0]);
                        if(choice2==2) {
                            JOptionPane.showMessageDialog(null, "Canceled!", "Canceled!", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        if(choice2==1){
                            if(request.getMovie_name()!=null&&!request.getMovie_name().isEmpty()){
                                for(Production production : IMDB.getInstance().getProductions()){
                                    if(production.getTitle().equals(request.getMovie_name())){
                                        for(User user1 : IMDB.getInstance().getUsers()){
                                            if(user1 instanceof Staff staff1){
                                                if(staff1.getRequests()!=null)
                                                    for(Request request : staff1.getRequests()){
                                                        if(request.gettype().equals(RequestTypes.MOVIE_ISSUE)&&request.getMovie_name().equals(production.getTitle())){
                                                            staff1.getRequests().remove(request);
                                                            break;
                                                        }
                                                    }
                                            }
                                            if(user1.getFavorites()!=null)
                                                user1.getFavorites().remove(production);
                                        }
                                        IMDB.getInstance().getProductions().remove(production);
                                        JOptionPane.showMessageDialog(null, production.getTitle()+" was deleted", "Delete", JOptionPane.INFORMATION_MESSAGE);
                                        frame.dispose();
                                        Admin.RequestHolder.getRequests().remove(request);
                                        return;
                                    }
                                }
                            }
                            if(request.getActor_name()!=null&&!request.getActor_name().isEmpty()){
                                for(Actor actor : IMDB.getInstance().getActors()){
                                    if(actor.getName().equals(request.getActor_name())){
                                        for(User user1 : IMDB.getInstance().getUsers()){
                                            if(user1 instanceof Staff staff1){
                                                if(staff1.getRequests()!=null)
                                                    for(Request request : staff1.getRequests()){
                                                        if(request.gettype().equals(RequestTypes.ACTOR_ISSUE)&&request.getActor_name().equals(actor.getName())){
                                                            staff1.getRequests().remove(request);
                                                            break;
                                                        }
                                                    }
                                            }
                                            if(user1.getFavorites()!=null)
                                                user1.getFavorites().remove(actor);
                                        }
                                        IMDB.getInstance().getActors().remove(actor);
                                        JOptionPane.showMessageDialog(null, actor.getName()+" was deleted", "Delete", JOptionPane.INFORMATION_MESSAGE);
                                        frame.dispose();
                                        Admin.RequestHolder.getRequests().remove(request);
                                        return;
                                    }
                                }
                            }
                        }
                        if(choice2==0){
                            if(request.getActor_name()!=null&&!request.getActor_name().isEmpty()){
                                for(Actor actor: IMDB.getInstance().getActors()){
                                    if(actor.getName().equals(request.getActor_name())){
                                        for(User user : IMDB.getInstance().getUsers()){
                                            if(user instanceof Admin){
                                                ((Admin) user).getAdded().add(actor);
                                            }
                                        }
                                    }
                                }
                            }
                            if(request.getMovie_name()!=null&&!request.getMovie_name().isEmpty()){
                                for(Production production: IMDB.getInstance().getProductions()){
                                    if(production.getTitle().equals(request.getMovie_name())){
                                        for(User user : IMDB.getInstance().getUsers()){
                                            if(user instanceof Admin){
                                                ((Admin) user).getAdded().add(production);
                                            }
                                        }
                                    }
                                }
                            }
                            Admin.RequestHolder.getRequests().remove(request);
                            JOptionPane.showMessageDialog(null, options[0]+" was kept", "Keep", JOptionPane.INFORMATION_MESSAGE);
                            frame.dispose();
                        }
                    }
                    else {
                        String[] options1 = {"Add", "Delete",  "Cancel"};
                        choice = JOptionPane.showOptionDialog(null, "What do you want to do?", "Choose an option",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options1, options1[0]);
                        if (choice == 0) {
                            new AddPage(staff);
                            Admin.RequestHolder.getRequests().remove(request);
                        }
                        if(choice == 1){
                            new DeletePage(staff);
                            Admin.RequestHolder.getRequests().remove(request);
                        }
                        if (choice == 2) {
                            JOptionPane.showMessageDialog(null, "Canceled!", "Canceled!", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    }
                }
                if(request.gettype().equals(RequestTypes.DELETE_ACCOUNT)) {
                    options = new String[]{"Yes", "No", "Cancel"};
                    int choice2 = JOptionPane.showOptionDialog(null, "Are you sure you want to delete this account?", "To delete or not to delete - That is the question!",
                            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                    if(choice2==0){
                        for(User user : IMDB.getInstance().getUsers()){
                            if(user.getUsername().equals(request.getUsername())){
                                IMDB.getInstance().getUsers().remove(user);
                                IMDB.getInstance().getRequests().remove(request);
                                Admin.RequestHolder.getRequests().remove(request);
                                JOptionPane.showMessageDialog(null, "Account deleted!", "Account deleted!", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                        }
                    }
                    if(choice2==1){
                        Admin.RequestHolder.getRequests().remove(request);
                        IMDB.getInstance().getRequests().remove(request);
                        JOptionPane.showMessageDialog(null, "Account not deleted!", "Account not deleted!", JOptionPane.INFORMATION_MESSAGE);}
                    if(choice2==2){
                        JOptionPane.showMessageDialog(null, "Canceled!", "Canceled!", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }
                if(request.gettype().equals(RequestTypes.ACTOR_ISSUE)||request.gettype().equals(RequestTypes.MOVIE_ISSUE)) {
                    for (User user1 : IMDB.getInstance().getUsers()) {
                        if (user1.getAccountType() != AccountType.ADMIN && request.getUsername().equals(user1.getUsername())) {
                            user1.experienceStrategy = user1.solvedRequest;
                            user1.experienceStrategy.addexp();
                        }
                    }
                }
                request.removeObserver(staff);
                request.notifyObserver("Request solved by "+staff.getUsername());
                if(staff.getRequests()!=null&&!staff.getRequests().isEmpty()){
                    IMDB.getInstance().getRequests().remove(request);
                    staff.getRequests().remove(request);}
                JOptionPane.showMessageDialog(null, "Request resolved!", "Request resolved!", JOptionPane.INFORMATION_MESSAGE);
                frame.dispose();
            }
            if(choice==1){
                if(request.gettype().equals(RequestTypes.ACTOR_ISSUE)||request.gettype().equals(RequestTypes.MOVIE_ISSUE)){
                    request.removeObserver(staff);
                    IMDB.getInstance().getRequests().remove(request);
                    request.notifyObserver("Request declined by "+staff.getUsername());
                    staff.getRequests().remove(request);}
                if(request.gettype().equals(RequestTypes.DELETE_ACCOUNT)||request.gettype().equals(RequestTypes.OTHERS)){
                    request.removeObserver(staff);
                    IMDB.getInstance().getRequests().remove(request);
                    request.notifyObserver("Request declined by "+staff.getUsername());
                    Admin.RequestHolder.getRequests().remove(request);
                }
                frame.dispose();
                JOptionPane.showMessageDialog(null, "Request declined!", "Request declined!", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
