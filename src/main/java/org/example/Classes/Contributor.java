package org.example.Classes;

import org.example.Enums.AccountType;
import org.example.Enums.RequestTypes;
import org.example.Interfaces.RequestsManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;

public class Contributor extends Staff implements RequestsManager {

    public Contributor(Information info, String username, int experience, List<String> notifications, SortedSet<Object> favorites, List<Request> requests, SortedSet<Object> added) {
        super(info, AccountType.CONTRIBUTOR, username, experience, notifications, favorites, requests, added);
    }

    public void createRequest(){
        System.out.println("Please insert the type of the request:");
        for(RequestTypes requestTypes : RequestTypes.values()){
            System.out.println("\u001B[37m"+requestTypes.toString()+","+"\u001B[0m");
        }
        Scanner scanner3 = new Scanner(System.in);
        RequestTypes types;
        while (true){
            try {
                String type = scanner3.nextLine();
                types = RequestTypes.valueOf(type.toUpperCase());
                if(types.equals(RequestTypes.DELETE_ACCOUNT)||types.equals(RequestTypes.ACTOR_ISSUE)||types.equals(RequestTypes.MOVIE_ISSUE)||types.equals(RequestTypes.OTHERS))
                    break;
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using one of the following: ACTOR_ISSUE, MOVIE_ISSUE,DELETE_ACCOUNT, OTHERS");
            }
            catch (Exception e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using one of the following: ACTOR_ISSUE, MOVIE_ISSUE,DELETE_ACCOUNT, OTHERS");
            }
        }
        if(types.equals(RequestTypes.ACTOR_ISSUE)){
            System.out.println("Please insert the name of the actor from the list below:");
            for(Actor actor : IMDB.getInstance().getActors()){
                System.out.println("\u001B[37m"+actor.getName()+","+"\u001B[0m");
            }
            int c=0;
            Scanner scanner5 = new Scanner(System.in);
            String actor_name = scanner5.nextLine();
            for (Actor actor : IMDB.getInstance().getActors()){
                if(actor.getName().equals(actor_name)) {
                    c=1;
                    break;
                }
            }
            if(c==0) {
                System.out.println("\u001B[31m"+"Actor not found!"+"\u001B[0m");
                return;
            }
            for(Object object : this.getAdded()){
                if(object instanceof Actor actor){
                    if(actor.getName().equals(actor_name)){
                        System.out.println("\u001B[31m"+"Actor added by you!"+"\u001B[0m");
                        return;
                    }
                }
            }
            for(User user1 : IMDB.getInstance().getUsers()){
                if(user1.getAccountType().equals(AccountType.ADMIN)||user1.getAccountType().equals(AccountType.CONTRIBUTOR)){
                    Staff staff = (Staff) user1;
                    if(staff.getRequests()!=null)
                        for(Request request : staff.getRequests()){
                            if(request.getActor_name()!=null&&request.getActor_name().equals(actor_name)&&request.getUsername().equals(this.getUsername())){
                                System.out.println("\u001B[31m"+"Request already exists!"+"\u001B[0m");
                                return;
                            }
                    }
                }
            }
            System.out.println("Please insert the description of the request:");
            Scanner scanner4 = new Scanner(System.in);
            String description = scanner4.nextLine();
            String admin="";
            for(User user1 : IMDB.getInstance().getUsers()){
                if(user1.getAccountType().equals(AccountType.ADMIN)||user1.getAccountType().equals(AccountType.CONTRIBUTOR)){
                    Staff staff = (Staff) user1;
                    if(!staff.getAdded().isEmpty()){
                        for(Object object : staff.getAdded()){
                            if(object instanceof Actor actor){
                                if(actor.getName().equals(actor_name)){
                                    admin=user1.getUsername();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            Request request = new Request(types, LocalDateTime.now(),this.getUsername(),admin,description,"",actor_name);
            for(User user : IMDB.getInstance().getUsers()){
                if(user.getUsername().equals(request.getAdmin())&&(user.getUsername().equals(AccountType.ADMIN)||user.getUsername().equals(AccountType.CONTRIBUTOR))){
                    Staff staff = (Staff) user;
                    if(staff.getRequests()==null)
                        staff.setRequests(new ArrayList<>());
                    request.addObserver(staff);
                    request.notifyObserver("Ai o cerere noua de la \""+request.getUsername()+"\" -> "+request.getDescription());
                    request.addObserver(this);
                    staff.getRequests().add(request);
                    System.out.println("\u001B[32m"+"Request created successfully!"+"\u001B[0m");
                    return;
                }
            }
            System.out.println("\u001B[31m"+"Admin not found!"+"\u001B[0m");
            return;
        }
        if(types.equals(RequestTypes.MOVIE_ISSUE)){
            System.out.println("Please insert the name of the production from the list below:");
            for(Production production : IMDB.getInstance().getProductions()){
                System.out.println("\u001B[37m"+production.getTitle()+","+"\u001B[0m");
            }
            int c=0;
            Scanner scanner5 = new Scanner(System.in);
            String movie_name = scanner5.nextLine();
            for (Production production : IMDB.getInstance().getProductions()){
                if(production.getTitle().equals(movie_name)) {
                    c=1;
                    break;
                }
            }
            if(c==0) {
                System.out.println("\u001B[31m"+"Production not found!"+"\u001B[0m");
                return;
            }
            for(Object object : this.getAdded()){
                if(object instanceof Production production){
                    if(production.getTitle().equals(movie_name)){
                        System.out.println("\u001B[31m"+"Production added by you!"+"\u001B[0m");
                        return;
                    }
                }
            }
            for(User user1 : IMDB.getInstance().getUsers()){
                if(user1.getAccountType().equals(AccountType.ADMIN)||user1.getAccountType().equals(AccountType.CONTRIBUTOR)){
                    Staff staff = (Staff) user1;
                    if(staff.getRequests()!=null)
                        for(Request request : staff.getRequests()){
                            if(request.getMovie_name()!=null&&request.getMovie_name().equals(movie_name)&&request.getUsername().equals(this.getUsername())){
                                System.out.println("\u001B[31m"+"Request already exists!"+"\u001B[0m");
                                return;
                        }
                    }
                }
            }
            System.out.println("Please insert the description of the request:");
            Scanner scanner4 = new Scanner(System.in);
            String description = scanner4.nextLine();
            String admin="";
            for(User user1 : IMDB.getInstance().getUsers()){
                if(user1.getAccountType().equals(AccountType.ADMIN)||user1.getAccountType().equals(AccountType.CONTRIBUTOR)){
                    Staff staff = (Staff) user1;
                    if(!staff.getAdded().isEmpty()){
                        for(Object object : staff.getAdded()){
                            if(object instanceof Production production){
                                if(production.getTitle().equals(movie_name)){
                                    admin=user1.getUsername();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
            Request request = new Request(types, LocalDateTime.now(),this.getUsername(),admin,description,movie_name,"");
            for(User user : IMDB.getInstance().getUsers()){
                if(user.getUsername().equals(request.getAdmin())&&(user.getAccountType().equals(AccountType.ADMIN)||user.getAccountType().equals(AccountType.CONTRIBUTOR))){
                    Staff staff = (Staff) user;
                    if(staff.getRequests()==null)
                        staff.setRequests(new ArrayList<>());
                    request.addObserver(staff);
                    request.notifyObserver("Ai o cerere noua de la \""+request.getUsername()+"\" -> "+request.getDescription());
                    request.addObserver(this);
                    staff.getRequests().add(request);
                    System.out.println("\u001B[32m"+"Request created successfully!"+"\u001B[0m");
                    return;
                }
            }
            System.out.println("\u001B[31m"+"Admin not found!"+"\u001B[0m");
            return;
        }
        if(types.equals(RequestTypes.DELETE_ACCOUNT)){
            for(Request request : Admin.RequestHolder.getRequests()){
                if(request.getUsername().equals(this.getUsername())&&request.gettype().equals(RequestTypes.DELETE_ACCOUNT)) {
                    System.out.println("\u001B[31m" + "Request already exists!" + "\u001B[0m");
                    return;
                }
            }
        }
        System.out.println("Please insert the description of the request:");
        Scanner scanner4 = new Scanner(System.in);
        String description = scanner4.nextLine();
        Request request = new Request(types, LocalDateTime.now(),this.getUsername(),"ADMIN",description,"","");
        Admin.RequestHolder.addReq(request);
        System.out.println("\u001B[32m"+"Request created successfully!"+"\u001B[0m");
    }
    public void removeRequest(){
        System.out.println("Please insert the type of the request:");
        for(RequestTypes requestTypes : RequestTypes.values()){
            System.out.println("\u001B[37m"+requestTypes.toString()+","+"\u001B[0m");
        }
        Scanner scanner3 = new Scanner(System.in);
        RequestTypes types;
        while (true){
            try {
                String type = scanner3.nextLine();
                types = RequestTypes.valueOf(type.toUpperCase());
                if(types.equals(RequestTypes.DELETE_ACCOUNT)||types.equals(RequestTypes.ACTOR_ISSUE)||types.equals(RequestTypes.MOVIE_ISSUE)||types.equals(RequestTypes.OTHERS))
                    break;
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using one of the following: DELETE_ACCOUNT, ACTOR_ISSUE, MOVIE_ISSUE, OTHERS");
            }
            catch (Exception e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using one of the following: DELETE_ACCOUNT, ACTOR_ISSUE, MOVIE_ISSUE, OTHERS");
            }
        }
        if(types.equals(RequestTypes.ACTOR_ISSUE)) {
            System.out.println("Please insert the name of the actor from the list below:");
            for (Actor actor : IMDB.getInstance().getActors()) {
                System.out.println("\u001B[37m" + actor.getName() + "," + "\u001B[0m");
            }
            Scanner scanner5 = new Scanner(System.in);
            String actor_name = scanner5.nextLine();
            int c = 0;
            for (Actor actor : IMDB.getInstance().getActors()) {
                if (actor.getName().equals(actor_name)) {
                    c = 1;
                    break;
                }
            }
            if (c == 0) {
                System.out.println("\u001B[31m" + "Actor not found!" + "\u001B[0m");
                return;
            }
            for (User user1 : IMDB.getInstance().getUsers()) {
                if (user1.getAccountType().equals(AccountType.ADMIN) || user1.getAccountType().equals(AccountType.CONTRIBUTOR)) {
                    Staff staff = (Staff) user1;
                    if(staff.getRequests()!=null)
                        for (Request request : staff.getRequests()) {
                            if ((request.getActor_name()!=null)&&(request.getActor_name().equals(actor_name)) && (request.getUsername().equals(this.getUsername()))) {
                                staff.getNotifications().remove("Ai o cerere noua de la \"" + request.getUsername() + "\" -> " + request.getUsername());
                                staff.getRequests().remove(request);
                                System.out.println("\u001B[32m" + "Request removed successfully!" + "\u001B[0m");
                                return;
                            }
                        }
                }
            }
            System.out.println("\u001B[31m" + "Request not found!" + "\u001B[0m");
            return;
        }
        if(types.equals(RequestTypes.MOVIE_ISSUE)) {
            System.out.println("Please insert the name of the production from the list below:");
            for (Production production : IMDB.getInstance().getProductions()) {
                System.out.println("\u001B[37m" + production.getTitle() + "," + "\u001B[0m");
            }
            Scanner scanner5 = new Scanner(System.in);
            String movie_name = scanner5.nextLine();
            int c = 0;
            for (Production production : IMDB.getInstance().getProductions()) {
                if (production.getTitle().equals(movie_name)) {
                    c = 1;
                    break;
                }
            }
            if (c == 0) {
                System.out.println("\u001B[31m" + "Production not found!" + "\u001B[0m");
                return;
            }
            for (User user1 : IMDB.getInstance().getUsers()) {
                if (user1.getAccountType().equals(AccountType.ADMIN) || user1.getAccountType().equals(AccountType.CONTRIBUTOR)) {
                    Staff staff = (Staff) user1;
                    if(staff.getRequests()!=null)
                        for (Request request : staff.getRequests()) {
                            if ((request.getMovie_name()!=null)&&(request.getMovie_name().equals(movie_name) )&&( request.getUsername().equals(this.getUsername()))) {
                                staff.getNotifications().remove("Ai o cerere noua de la \"" + request.getUsername() + "\" -> " + request.getDescription());
                                staff.getRequests().remove(request);
                                System.out.println("\u001B[32m" + "Request removed successfully!" + "\u001B[0m");
                                return;
                            }
                        }
                }
            }
            System.out.println("\u001B[31m" + "Request not found!" + "\u001B[0m");
            return;
        }
        if(types.equals(RequestTypes.DELETE_ACCOUNT)){
            for(Request request : Admin.RequestHolder.getRequests()){
                if(request.getUsername().equals(this.getUsername())&&request.gettype().equals(RequestTypes.DELETE_ACCOUNT)){
                    Admin.RequestHolder.removeReq(request);
                    System.out.println("\u001B[32m" + "Request removed successfully!" + "\u001B[0m");
                    return;
                }
            }
            System.out.println("\u001B[31m" + "Request not found!" + "\u001B[0m");
        }
        if(types.equals(RequestTypes.OTHERS)){
            int i=0;
            for(Request request : Admin.RequestHolder.getRequests()){
                if(request.getUsername().equals(this.getUsername())&&request.gettype().equals(RequestTypes.OTHERS)){
                    i=1;
                    break;
                }
            }
            if(i==0){
                System.out.println("\u001B[31m" + "Request not found!" + "\u001B[0m");
                return;
            }
            System.out.println("Select the request you want to remove from the list below:");
            System.out.println("\u001B[37m"+"0.) ABORT"+"\u001B[0m");
            for(Request request : Admin.RequestHolder.getRequests()){
                if(request.getUsername().equals(this.getUsername())&&request.gettype().equals(RequestTypes.OTHERS)){
                    System.out.println("\u001B[37m"+(i++)+".) "+request.displayInfo("")+"\u001B[0m");
                }
            }
            Scanner scanner5 = new Scanner(System.in);
            i--;
            int index;
            while(true){
                try {
                    index=scanner5.nextInt();
                    if(index>=0&&index<=i)
                        break;
                    System.out.println("\u001B[31m" + "Invalid input! Try a number between 1 and "+i+ "\u001B[0m");
                }
                catch (Exception e){
                    System.out.println("\u001B[31m" + "Invalid input! Try a number between 1 and "+i+ "\u001B[0m");
                    return;
                }
            }
            if(index==0){
                System.out.println("\u001B[32m" + "Aborted!" + "\u001B[0m");
                return;
            }
            i=1;
            for(Request request : Admin.RequestHolder.getRequests()){
                if(request.getUsername().equals(this.getUsername())&&request.gettype().equals(RequestTypes.OTHERS)){
                    if(i==index){
                        Admin.RequestHolder.removeReq(request);
                        System.out.println("\u001B[32m" + "Request removed successfully!" + "\u001B[0m");
                        return;
                    }
                    i++;
                }
            }
        }

    }
}
