package org.example.Classes;

import org.example.Enums.AccountType;
import org.example.Enums.RequestTypes;
import org.example.Exceptions.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.Random;
public class Admin extends Staff{
    public Admin(Information info, String username, int experience, List<String> notifications, SortedSet<Object> favorites, List<Request> requests, SortedSet<Object> added) {
        super(info, AccountType.ADMIN, username, experience, notifications, favorites, requests, added);
    }
    public void AddUser(){
        System.out.println("Please insert the account type:");
        Scanner scanner10 = new Scanner(System.in);
        AccountType accountType;
        while (true){
            try {
                accountType = AccountType.valueOf(scanner10.nextLine().toUpperCase());
                if(accountType.equals(AccountType.ADMIN)||accountType.equals(AccountType.CONTRIBUTOR)||accountType.equals(AccountType.REGULAR))
                    break;
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using one of the following: admin, contributor, regular");
            }
            catch (Exception e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using one of the following: admin, contributor, regular");
            }
        }
        System.out.println("Please insert the name of the user:");
        Scanner scanner4 = new Scanner(System.in);
        String name = scanner4.nextLine();
        System.out.println("Please insert the email of the user:");
        Scanner scanner5 = new Scanner(System.in);
        String email = scanner5.nextLine();
        System.out.println("Please insert the country of the user:");
        Scanner scanner6 = new Scanner(System.in);
        String country = scanner6.nextLine();
        System.out.println("Please insert the age of the user:");
        Scanner scanner7 = new Scanner(System.in);
        int age;
        while (true){
            try {
                age = scanner7.nextInt();
                if(age<0)
                    throw new Exception();
                break;
            }
            catch (Exception e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a positive number:");
                scanner7.next();
            }
        }
        System.out.println("Please insert the gender of the user:");
        Scanner scanner8 = new Scanner(System.in);
        String gender = scanner8.nextLine();
        System.out.println("Please insert the birthDate of the user:");
        Scanner scanner9 = new Scanner(System.in);
        LocalDateTime birthDate;
        while (true){
            try {
                String date = scanner9.nextLine();
                birthDate = LocalDateTime.parse(date+"T00:00:00");
                break;
            }
            catch (Exception e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using the format yyyy-MM-dd");
            }
        }
        Random random = new Random();
        String validCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";
        StringBuilder password = new StringBuilder();
        for(int i=0;i<10;i++){
            int randomIndex = random.nextInt(validCharacters.length());
            char randomChar = validCharacters.charAt(randomIndex);
            password.append(randomChar);
            password.append(random.nextInt(100));
        }
        String pass = String.valueOf(password);
        Credentials credentials = new Credentials(email,pass);
        try {
            if(credentials.getEmail().isEmpty() || credentials.getPassword().isEmpty()||name.isEmpty())
                throw new InformationIncompleteException("Information is incomplete. Please provide all required details.");
        } catch (InformationIncompleteException e) {
            System.out.println("\u001B[31m"+e.getMessage()+"\u001B[0m");
            return;
        }
        String un="";
        int c=0;
        while (c==0){
            c=1;
            un= name.replace(" ","_")+random.nextInt(1000)+validCharacters.charAt(random.nextInt(validCharacters.length()));
            for(User user : IMDB.getInstance().getUsers()){
                if (user.getUsername().equals(un)) {
                    c = 0;
                    break;
                }
            }
        }

            User.Information information = new User.Information.Builder(credentials,name).country(country).age(age).gender(gender).birthDate(birthDate).build();
        if(accountType.equals(AccountType.ADMIN)){
            Admin admin = (Admin) UserFactory.createUser(AccountType.ADMIN,information,un,0,new ArrayList<>(),new TreeSet<>(),new ArrayList<>(),new TreeSet<>());
            IMDB.getInstance().getUsers().add(admin);
        }
        if(accountType.equals(AccountType.CONTRIBUTOR)){
            Contributor contributor = (Contributor) UserFactory.createUser(AccountType.CONTRIBUTOR,information,un,0,new ArrayList<>(),new TreeSet<>(),new ArrayList<>(),new TreeSet<>());
            IMDB.getInstance().getUsers().add(contributor);
        }
        if(accountType.equals(AccountType.REGULAR)){
            Regular regular = (Regular) UserFactory.createUser(AccountType.REGULAR,information,un,0,new ArrayList<>(),new TreeSet<>(),new ArrayList<>(),new TreeSet<>());
            IMDB.getInstance().getUsers().add(regular);
        }
        System.out.println("\u001B[32m"+"User added"+"\u001B[0m");
        System.out.println("The password is: "+pass);
    }
    public void DeleteUser(){
        System.out.println("Please select one of the following users:");
        for(User user : IMDB.getInstance().getUsers()){
            if(!user.getAccountType().equals(AccountType.ADMIN))
                System.out.println(user.displayInfo());
        }
        System.out.println("Please insert the username of the user:");
        Scanner scanner3 = new Scanner(System.in);
        String username = scanner3.nextLine();
        User user = null;
        for(User user1:IMDB.getInstance().getUsers())
            if(user1.getUsername().equals(username)){
                user=user1;
                break;}
        if(user==null||user.getAccountType().equals(AccountType.ADMIN)){
            System.out.println("\u001B[31m"+"User not found!"+"\u001B[0m");
            return;
        }
        System.out.println("Are you sure you want to delete this user? (y/n)\n"+user.displayInfo());
        Scanner scanner4 = new Scanner(System.in);
        String answer = scanner4.nextLine();
        while (true){
            try {
                if(answer.equals("y") || answer.equals("n"))
                    break;
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using y or n:");
            }
            catch (Exception e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using y or n:");
                scanner4.next();
            }
        }
        if(answer.equals("n")) {
            System.out.println("\u001B[32m"+"User not deleted"+"\u001B[0m");
            return;
        }

        for(Production production : IMDB.getInstance().getProductions()){
            List<Rating> toRemove = new ArrayList<>();
            for(Rating rating : production.getRatings()){
                if(rating.getUsername().equals(user.getUsername()))
                    toRemove.add(rating);
            }
            for(Rating rating : toRemove)
                production.getRatings().remove(rating);
        }
        for(Actor actor : IMDB.getInstance().getActors()){
            List<Rating> toRemove = new ArrayList<>();
            for(Rating rating : actor.getRatings()){
                if(rating.getUsername().equals(user.getUsername()))
                    toRemove.add(rating);
            }
            for(Rating rating : toRemove)
                actor.getRatings().remove(rating);
        }
        if(user instanceof Contributor contributor){
            for(Object obj : contributor.getAdded()) {
                if(obj instanceof Production)
                    RequestHolder.addReq(new Request(RequestTypes.OTHERS, LocalDateTime.now(),"DELETED_ACCOUNT","ADMIN","Contributor "+user.getUsername()+" added "+((Production) obj).getTitle()+" and his account has been deleted",((Production) obj).getTitle(),null));
                if(obj instanceof Actor)
                    RequestHolder.addReq(new Request(RequestTypes.OTHERS,LocalDateTime.now(),"DELETED_ACCOUNT","ADMIN","Contributor "+user.getUsername()+" added "+((Actor) obj).getName()+" and his account has been deleted",null,((Actor) obj).getName()));
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
        System.out.println("\u001B[32m"+"User deleted"+"\u001B[0m");
    }

    public static class RequestHolder {
        private static List<Request> requests=new ArrayList<>();
        public static List<Request> getRequests() {
            return requests;
        }
        public static void addReq(Request request){
            RequestHolder.requests.add(request);
        }
        public static void removeReq(Request request){
            RequestHolder.requests.remove(request);
        }

        public static String displayInfo(){
            String s=", RequestH";
            if(RequestHolder.getRequests()!=null)
                for(Request request:RequestHolder.getRequests()){
                    s="{user: "+request.getUsername()+", admin: "+request.getAdmin()+" "+request.getDescription()+" ,type: "+(request.gettype())+"}, ";
                }
            return s;
        }

    }
}
