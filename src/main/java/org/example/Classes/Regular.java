package org.example.Classes;

import org.example.Enums.AccountType;
import org.example.Enums.RequestTypes;
import org.example.Interfaces.ExperienceStrategy;
import org.example.Interfaces.RequestsManager;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.SortedSet;

public class Regular extends User implements RequestsManager {

    public Regular(Information info, String username, int experience, List<String> notifications, SortedSet<Object> favorites) {
        super(info, AccountType.REGULAR, username, experience, notifications, favorites);
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
                            if(object instanceof Actor act){
                                if(act.getName().equals(actor_name)){
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
        request.addObserver(this);
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
    public void addRating(){
        System.out.println("Please insert the name of the production or actor:");
        System.out.println("\u001B[37m"+"Productions:"+"\u001B[0m");
        for(Production production : IMDB.getInstance().getProductions()){
            System.out.println("\u001B[37m"+production.getTitle()+","+"\u001B[0m");
        }
        System.out.println("\u001B[37m"+"Actors:"+"\u001B[0m");
        for(Actor actor : IMDB.getInstance().getActors()){
            System.out.println("\u001B[37m"+actor.getName()+","+"\u001B[0m");
        }
        Scanner scanner3 = new Scanner(System.in);
        String name = scanner3.nextLine();
        Object object=null;
        for(Production prod : IMDB.getInstance().getProductions()){
            if(prod.getTitle().equals(name)){
                if(prod.getType().equals("Movie")) {
                    for(Rating rating:prod.getRatings()){
                        if(rating.getUsername().equals(this.getUsername())){
                            System.out.println("\u001B[31m"+"You have already rated this production!"+"\u001B[0m");
                            return;
                        }
                    }
                    object=prod;
                    break;
                    }
                if(prod.getType().equals("Series")){
                    for(Rating rating:prod.getRatings()){
                        if(rating.getUsername().equals(this.getUsername())){
                            System.out.println("\u001B[31m"+"You have already rated this production!"+"\u001B[0m");
                            return;
                        }
                    }
                    object=prod;
                    break;
                }
            }
        }
        for(Actor actor : IMDB.getInstance().getActors()){
            if(actor.getName().equals(name)){
                for(Rating rating:actor.getRatings()){
                    if(rating.getUsername().equals(this.getUsername())){
                        System.out.println("\u001B[31m"+"You have already rated this actor!"+"\u001B[0m");
                        return;
                    }
                }
                object=actor;
                break;
            }
        }
        if(object == null){
            System.out.println("\u001B[31m"+"Production or actor not found!"+"\u001B[0m");
            return;
        }
        System.out.println("Please insert the rating(a number between 1 and 10):");
        Scanner scanner4 = new Scanner(System.in);
        int rating;
        while (true) {
            try {
                rating = scanner4.nextInt();
                if (rating < 1 || rating > 10) {
                    System.out.println("\u001B[31m" + "Please insert a number between 1 and 10!" + "\u001B[0m");
                } else {
                    break;
                }
            } catch (Exception e) {
                System.out.println("\u001B[31m" + "Please insert a number between 1 and 10!" + "\u001B[0m");
                scanner4.next();
            }
        }
        System.out.println("Please insert the comment:");
        Scanner scanner5 = new Scanner(System.in);
        String comment = scanner5.nextLine();
        Rating rating1 = new Rating(this.getUsername(),rating,comment);
        if(object instanceof Production) {
            Production prod = (Production) object;
            for (Rating rating2 : prod.getRatings()) {
                rating2.notifyObserver("Productia \"" + prod.getTitle() + "\" la care ai dat un review, a mai primit un review de la utilizatorul \"" + this.getUsername() + "\" -> " + rating);
            }
            for (User user1 : IMDB.getInstance().getUsers()) {
                if (user1.getNotifications() == null)
                    user1.setNotifications(new ArrayList<>());
                if ((user1.getAccountType() == AccountType.ADMIN || user1.getAccountType() == AccountType.CONTRIBUTOR) && ((Staff) user1).getAdded().contains(prod))
                    user1.getNotifications().add("Productia \"" + prod.getTitle() + "\" pe care ai adaugat-o a primit un review de la utilizatorul \"" + this.getUsername() + "\" -> " + rating);
                if (user1.getFavorites().contains(prod))
                    user1.getNotifications().add("Productia \"" + prod.getTitle() + "\" pe care o ai in lista de favorite a primit un review de la utilizatorul \"" + this.getUsername() + "\" -> " + rating);
            }
            rating1.addObserver(this);
            prod.getRatings().add(rating1);
            this.experienceStrategy = addReview;
            this.experienceStrategy.addexp();
            System.out.println("\u001B[32m" + "Rating added successfully!" + "\u001B[0m");
            prod.calculateRating();
            return;
        }
        if(object instanceof Actor){
                Actor actor = (Actor) object;
                for (Rating rating2 : actor.getRatings()) {
                    rating2.notifyObserver("Actorul \"" + actor.getName() + "\" la care ai dat un review, a mai primit un review de la utilizatorul \"" + this.getUsername() + "\" -> " + rating);
                }
                for (User user1 : IMDB.getInstance().getUsers()) {
                    if (user1.getNotifications() == null)
                        user1.setNotifications(new ArrayList<>());
                    if ((user1.getAccountType() == AccountType.ADMIN || user1.getAccountType() == AccountType.CONTRIBUTOR) && ((Staff) user1).getAdded().contains(actor))
                        user1.getNotifications().add("Actorul \"" + actor.getName() + "\" pe care l-ai adaugat a primit un review de la utilizatorul \"" + this.getUsername() + "\" -> " + rating);
                    if (user1.getFavorites().contains(actor))
                        user1.getNotifications().add("Actorul \"" + actor.getName() + "\" pe care il ai in lista de favorite a primit un review de la utilizatorul \"" + this.getUsername() + "\" -> " + rating);
                }
                rating1.addObserver(this);
                actor.getRatings().add(rating1);
                this.experienceStrategy = addReview;
                this.experienceStrategy.addexp();
                System.out.println("\u001B[32m" + "Rating added successfully!" + "\u001B[0m");
                actor.calculateRating();
        }
    }
    public void deleteRating(){
        System.out.println("Please insert the name of the production or actor:");
        System.out.println("\u001B[37m"+"Productions:"+"\u001B[0m");
        for(Production production : IMDB.getInstance().getProductions()){
            System.out.println("\u001B[37m"+production.getTitle()+","+"\u001B[0m");
        }
        System.out.println("\u001B[37m"+"Actors:"+"\u001B[0m");
        for(Actor actor : IMDB.getInstance().getActors()){
            System.out.println("\u001B[37m"+actor.getName()+","+"\u001B[0m");
        }
        Scanner scanner3 = new Scanner(System.in);
        String name = scanner3.nextLine();
        System.out.println(name);
        int contor = 0;
            for(Production production : IMDB.getInstance().getProductions()){
                if(production.getTitle().equals(name)){
                    contor = 1;
                    for(Rating rating : production.getRatings()){
                        if(rating.getUsername().equals(this.getUsername())){
                            for(User user1:IMDB.getInstance().getUsers()){
                                if(user1.getNotifications()!=null) {
                                    user1.getNotifications().remove("Filmul \"" + production.getTitle() + "\" la care ai dat un review, a mai primit un review de la utilizatorul \"" + rating.getUsername() + "\" -> " + rating.getRating());
                                    user1.getNotifications().remove("Filmul \"" + production.getTitle() + "\" pe care l-ai adaugat a primit un review de la utilizatorul \"" + rating.getUsername() + "\" -> " + rating.getRating());
                                    user1.getNotifications().remove("Filmul \"" + production.getTitle() + "\" pe care il ai in lista de favorite a primit un review de la utilizatorul \"" + rating.getUsername() + "\" -> " + rating.getRating());
                                }
                            }
                            production.getRatings().remove(rating);
                            this.experienceStrategy=addReview;
                            this.experienceStrategy.removeexp();
                            System.out.println("\u001B[32m"+"Rating deleted successfully!"+"\u001B[0m");
                            production.calculateRating();
                            return;
                        }
                    }
                }
            }
            for(Actor actor : IMDB.getInstance().getActors()){
                if(actor.getName().equals(name)){
                    contor = 1;
                    for(Rating rating : actor.getRatings()){
                        if(rating.getUsername().equals(this.getUsername())){
                            for(User user1:IMDB.getInstance().getUsers()){
                                if(user1.getNotifications()!=null) {
                                    user1.getNotifications().remove("Actorul \"" + actor.getName() + "\" la care ai dat un review, a mai primit un review de la utilizatorul \"" + rating.getUsername() + "\" -> " + rating.getRating());
                                    user1.getNotifications().remove("Actorul \"" + actor.getName() + "\" pe care l-ai adaugat a primit un review de la utilizatorul \"" + rating.getUsername() + "\" -> " + rating.getRating());
                                    user1.getNotifications().remove("Actorul \"" + actor.getName() + "\" pe care il ai in lista de favorite a primit un review de la utilizatorul \"" + rating.getUsername() + "\" -> " + rating.getRating());
                                }
                            }
                            actor.getRatings().remove(rating);
                            this.experienceStrategy=addReview;
                            this.experienceStrategy.removeexp();
                            System.out.println("\u001B[32m"+"Rating deleted successfully!"+"\u001B[0m");
                            actor.calculateRating();
                            return;
                        }
                    }
                }
            }
            if(contor == 0)
                System.out.println("\u001B[31m"+"Production or actor not found!"+"\u001B[0m");
            if(contor == 1)
                System.out.println("\u001B[31m"+"You haven't rated this production or actor!"+"\u001B[0m");
    }

    public String displayInfo() {
        this.calculateExperience();
        StringBuilder display = new StringBuilder();
        display.append(this.getAccountType()).append(": ").append("\u001B[1m").append(this.getUsername()).append("\u001B[0m").append("\n");
        display.append(this.getInfo().displayInfo());
        display.append("Experience: ").append(this.getExperience()).append("\n");
//        display.append("Notifications: ").append("\n");
//        if(this.notifications==null)
//            display.append("   ").append("No notifications").append("\n");
//        else
//            for(String notification : this.notifications){
//                display.append("   ").append(notification).append("\n");
//            }
        display.append("Favorites: ").append("\n");
        if(this.getFavorites().isEmpty())
            display.append("   ").append("No additions").append("\n");
        else {
            StringBuilder act = new StringBuilder();
            StringBuilder ser = new StringBuilder();
            StringBuilder mov = new StringBuilder();
            for(Object favorite : this.getFavorites()) {
                if(favorite instanceof Actor)
                    act.append("      ").append(((Actor) favorite).getName()).append("\n");
                if(favorite instanceof Movie)
                    mov.append("      ").append(((Production) favorite).getTitle()).append("\n");
                if(favorite instanceof Series)
                    ser.append("      ").append(((Production) favorite).getTitle()).append("\n");
            }
            if(!act.isEmpty()){
                display.append("   ").append("Actors: ").append("\n").append(act);
            }
            else display.append("   ").append("No actors").append("\n");
            if(!mov.isEmpty()){
                display.append("   ").append("Movies: ").append("\n").append(mov);
            }
            else display.append("   ").append("No movies").append("\n");
            if(!ser.isEmpty()) {
                display.append("   ").append("Series: ").append("\n").append(ser);
            }
            else display.append("   ").append("No series").append("\n");
        }
        return display.toString();
    }

}
