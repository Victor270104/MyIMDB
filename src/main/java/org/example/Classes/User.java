package org.example.Classes;

import org.example.Enums.AccountType;
import org.example.Interfaces.ExperienceStrategy;
import org.example.Strategy.*;
import org.example.Terminal.Terminal;
import org.example.Interfaces.Observer;

import java.time.LocalDateTime;
import java.util.*;


public abstract class User implements  Observer {
    private Information info;
    private AccountType accountType;
    private String username;
    private Integer experience;
    private List<String> notifications;
    private SortedSet<Object> favorites;
    public ExperienceStrategy experienceStrategy;
    public AddReview addReview=new AddReview();
    public SolvedRequest solvedRequest=new SolvedRequest();
    public AddedToIMDB addIMBD=new AddedToIMDB();

    public void logout(IMDB imdb){
        System.out.println("\033[32m"+"Log out successful!"+"\033[0m");
        new Terminal().Login(imdb);
    }

    public void AddFav() {
        System.out.println("Enter the name of the actor/production you want to add to your favorites from the list below:");
        System.out.println("Actors:");
        for(Actor actor : IMDB.getInstance().getActors())
        {
            System.out.println("\u001B[37m"+actor.getName()+"\u001B[0m");
        }
        System.out.println("Productions:");
        for (Production production : IMDB.getInstance().getProductions())
        {
            System.out.println("\u001B[37m"+production.getTitle()+"\u001B[0m");
        }
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        for(Object object: this.favorites)
        {
            if(object instanceof Actor){
                if(name.equals(((Actor) object).getName()))
                {
                    System.out.println("\u001B[31m"+"Actor already in favorites"+"\u001B[0m");
                    return;
                }
            }
            if(object instanceof Production){
                if(name.equals(((Production) object).getTitle()))
                {
                    System.out.println("\u001B[31m"+"Production already in favorites"+"\u001B[0m");
                    return;
                }
            }
        }
        int c=0;
        for(Actor actor : IMDB.getInstance().getActors())
        {
            if(actor.getName().equals(name))
            {
                c=1;
                break;
            }
        }
        for (Production production : IMDB.getInstance().getProductions())
        {
            if(production.getTitle().equals(name))
            {
                if(c==0)
                    c=2;
                if(c==1)
                    c=3;
                break;
            }
        }
        if(c==0)
        {
            System.out.println("\u001B[31m"+"Actor/Production not found"+"\u001B[0m");
            return;
        }
        if(c==3)
        {
            System.out.println("Multiple results found, enter 1 to add the actor to your favorites or 2 to add the production to your favorites");
            Scanner scanner1 = new Scanner(System.in);
            int choice;
            while (true)
            {
                try {
                    choice = scanner1.nextInt();
                    if(choice==1||choice==2)
                        break;
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", please try again with 1 or 2");
                }
                catch (InputMismatchException e)
                {
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", please try again with 1 or 2");
                    scanner1.next();
                }
            }
            if(choice==1)
            {
                c=1;
            }
            else
            {
                c=2;
            }
        }
        if(c==1)
        {
            for (Actor actor : IMDB.getInstance().getActors())
            {
                if(actor.getName().equals(name))
                {
                    this.favorites.add(actor);
                    System.out.println("\u001B[32m"+"Actor added to favorites"+"\u001B[0m");
                    return;
                }
            }
        }
        if(c==2)
        {
            for (Production production : IMDB.getInstance().getProductions())
            {
                if(production.getTitle().equals(name))
                {
                    this.favorites.add(production);
                    System.out.println("\u001B[32m"+"Production added to favorites"+"\u001B[0m");
                    return;
                }
            }
        }
    }
    public void RemoveFav(){
        System.out.println("Enter the name of the actor/production you want to remove from your favorites");
        System.out.println("Actors:");
        for(Object object: this.favorites)
        {
            if(object instanceof Actor){
                System.out.println("\u001B[37m"+((Actor) object).getName()+"\u001B[0m");
            }
        }
        System.out.println("Productions:");
        for(Object object: this.favorites)
        {
            if(object instanceof Production){
                System.out.println("\u001B[37m"+((Production) object).getTitle()+"\u001B[0m");
            }
        }
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        int c=0;
        for(Object object: this.favorites)
        {
                if(object instanceof Actor){
                    if(name.equals(((Actor) object).getName()))
                    {
                        if(c==0)
                            c=1;
                        if(c==2)
                            c=3;
                    }
                }
                if(object instanceof Production){
                    if(name.equals(((Production) object).getTitle()))
                    {
                        if(c==0)
                            c=2;
                        if(c==1)
                            c=3;
                    }
                }
        }
        if(c==0)
        {
            System.out.println("\u001B[31m"+"Actor/Production not found"+"\u001B[0m");
            return;
        }
        if(c==3)
        {
            System.out.println("Multiple results found, enter 1 to remove the actor from your favorites or 2 to remove the production from your favorites");
            Scanner scanner1 = new Scanner(System.in);
            int choice;
            while (true)
            {
                try {
                    choice = scanner1.nextInt();
                    if(choice==1||choice==2)
                        break;
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", please try again with 1 or 2");
                }
                catch (InputMismatchException e)
                {
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", please try again with 1 or 2");
                    scanner1.next();
                }
            }
            if(choice==1)
            {
                c=1;
            }
            else
            {
                c=2;
            }
        }
        if(c==1)
        {
            for(Object object: this.favorites)
            {
                if(object instanceof Actor){
                    if(name.equals(((Actor) object).getName()))
                    {
                        this.favorites.remove(object);
                        System.out.println("\u001B[32m"+"Actor removed from favorites"+"\u001B[0m");
                        return;
                    }
                }
            }
        }
        if(c==2)
        {
            for(Object object: this.favorites)
            {
                if(object instanceof Production){
                    if(name.equals(((Production) object).getTitle()))
                    {
                        this.favorites.remove(object);
                        System.out.println("\u001B[32m"+"Production removed from favorites"+"\u001B[0m");
                        return;
                    }
                }
            }
        }

    }
    public void SearchUser(){
        System.out.println("Enter the email of the user you want to search for:");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        User user = null;
        for(User user1:IMDB.getInstance().getUsers())
            if(user1.info.credentials.getEmail().equals(username)){
                user=user1;
                break;}
        if(user==null){
            System.out.println("\u001B[31m"+"User not found!"+"\u001B[0m");
            return;
        }
        System.out.println("\u001B[32m"+"User found!"+"\u001B[0m");
        System.out.println(user.displayInfo());

    }
    public User(Information info, AccountType accountType, String username, int experience, List<String> notifications, SortedSet<Object> favorites) {
        this.info = info;
        this.accountType = accountType;
        this.username = username;
        this.experience = experience;
        this.notifications = notifications;
        this.favorites = favorites;
    }
    public void calculateExperience(){
        int exp=0;
        experienceStrategy=addReview;
        exp=exp+experienceStrategy.calculateExperience();
        experienceStrategy=solvedRequest;
        exp=exp+experienceStrategy.calculateExperience();
        experienceStrategy=addIMBD;
        exp=exp+experienceStrategy.calculateExperience();
        experience=experience+exp;
    }
    public void notify(String message){
        if(notifications==null)
            notifications=new ArrayList<>();
        notifications.add(message);
    }

    public Information getInfo() {
        return info;
    }

    public void setInfo(Information info) {
        this.info = info;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public List<String> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<String> notifications) {
        this.notifications = notifications;
    }

    public SortedSet<Object> getFavorites() {
        return favorites;
    }

    public void setFavorites(SortedSet<Object> favorites) {
        this.favorites = favorites;
    }

    public ExperienceStrategy getExperienceStrategy() {
        return experienceStrategy;
    }

    public void setExperienceStrategy(ExperienceStrategy experienceStrategy) {
        this.experienceStrategy = experienceStrategy;
    }

    public AddReview getAddReview() {
        return addReview;
    }

    public void setAddReview(AddReview addReview) {
        this.addReview = addReview;
    }

    public SolvedRequest getSolvedRequest() {
        return solvedRequest;
    }

    public void setSolvedRequest(SolvedRequest solvedRequest) {
        this.solvedRequest = solvedRequest;
    }

    public AddedToIMDB getAddIMBD() {
        return addIMBD;
    }

    public void setAddIMBD(AddedToIMDB addIMBD) {
        this.addIMBD = addIMBD;
    }

    abstract public String displayInfo();
    public String toJson(){
        this.calculateExperience();
        StringBuilder json = new StringBuilder();
        json.append("  {\n");
        json.append("    \"username\": ").append("\"").append(username).append("\"").append(",\n");
        if(accountType.equals(AccountType.ADMIN))
            json.append("    \"experience\": ").append("null").append(",\n");
        else
            json.append("    \"experience\": \"").append(experience).append("\",\n");
        json.append("    \"information\": ").append(info.toJson()).append("\n");
        json.append("    \"userType\": ").append("\"");
        if(accountType.equals(AccountType.ADMIN))
            json.append("Admin").append("\",\n");
        if(accountType.equals(AccountType.CONTRIBUTOR))
            json.append("Contributor").append("\",\n");
        if(accountType.equals(AccountType.REGULAR))
            json.append("Regular").append("\",\n");
        if(this instanceof Admin||this instanceof Contributor)
        {   int act=0,prod=0;
            for (Object object : ((Staff) this).getAdded()) {
                if (act > 0 && prod > 0)
                    break;
                if (object instanceof Actor)
                    act++;
                if (object instanceof Production)
                    prod++;
            }
            if(prod!=0) {
                json.append("    \"productionsContribution\": [\n");
                for (Object object : ((Staff) this).getAdded())
                    if (object instanceof Production)
                        json.append("      ").append("\"").append(((Production) object).getTitle()).append("\"").append(",\n");
                json.deleteCharAt(json.length() - 2);
                json.append("    ],\n");
            }
            if(act!=0) {
                json.append("    \"actorsContribution\": [\n");
                for (Object object : ((Staff) this).getAdded())
                    if (object instanceof Actor)
                        json.append("      ").append("\"").append(((Actor) object).getName()).append("\"").append(",\n");
                json.deleteCharAt(json.length() - 2);
                json.append("    ],\n");
            }
        }
        int act=0,prod=0;
        for(Object object: favorites){
            if(object instanceof Actor)
                act=1;
            if(object instanceof Production)
                prod=1;
            if(act==1&&prod==1)
                break;
        }
        if(prod!=0) {
            json.append("    \"favoriteProductions\": [\n");
            for (Object object : favorites) {
                if (object instanceof Production)
                    json.append("      ").append("\"").append(((Production) object).getTitle()).append("\"").append(",\n");
            }
            json.deleteCharAt(json.length() - 2);
            json.append("    ],\n");
        }
        if(act!=0) {
            json.append("    \"favoriteActors\": [\n");
            for (Object object : favorites) {
                if (object instanceof Actor)
                    json.append("      ").append("\"").append(((Actor) object).getName()).append("\"").append(",\n");
            }
            json.deleteCharAt(json.length() - 2);
            json.append("    ],\n");
        }
        if(notifications!=null&&!notifications.isEmpty()) {
            json.append("    \"notifications\": [\n");
            for(String notification: notifications){
                json.append("      ").append("\"").append(notification.replace("\"","\\\"")).append("\"").append(",\n");
            }
            json.deleteCharAt(json.length()-2);
            json.append("    ],\n");
        }
        json.deleteCharAt(json.length()-2);
        json.append("  },\n");

        return json.toString();
    }

    public static class Information {
        private final Credentials credentials;
        private final String name;
        private final String country;
        private final int age;
        private final String gender;
        private final LocalDateTime birthDate;
        private Information(Builder builder) {
            this.credentials = builder.credentials;
            this.name = builder.name;
            this.country = builder.country;
            this.age = builder.age;
            this.gender = builder.gender;
            this.birthDate = builder.birthDate;
        }
        public Credentials getCredentials() {
            return credentials;
        }

        public String getName() {
            return name;
        }

        public String getCountry() {
            return country;
        }

        public int getAge() {
            return age;
        }

        public String getGender() {
            return gender;
        }

        public static class Builder {
            private Credentials credentials;
            private String name;
            private String country;
            private int age;
            private String gender;
            private LocalDateTime birthDate;

            public Builder(Credentials credentials, String name) {
                this.credentials = credentials;
                this.name = name;
            }

            public Builder country(String country) {
                this.country = country;
                return this;
            }

            public Builder age(int age) {
                this.age = age;
                return this;
            }

            public Builder gender(String gender) {
                this.gender = gender;
                return this;
            }

            public Builder birthDate(LocalDateTime birthDate) {
                this.birthDate = birthDate;
                return this;
            }


            public Information build() {
                return new Information(this);
            }
        }

        public String displayInfo() {
            StringBuilder display = new StringBuilder();
            //isplay.append(credentials.displayInfo()).append("\n");
            display.append("Name: ").append(name).append("\n");
            if(!country.isEmpty())
                display.append("Country: ").append(country).append("\n");
            if(age!=0)
                display.append("Age: ").append(age).append("\n");
            if(!gender.isEmpty())
                display.append("Gender: ").append(gender).append("\n");
            if(birthDate!=null)
                display.append("Birth date: ").append(birthDate).append("\n");
            return display.toString();
        }

        public String toJson(){
            StringBuilder json = new StringBuilder();
            json.append("{\n");
            json.append("      \"credentials\": {\n").append(credentials.toJson());
            json.append("      },\n");
            json.append("      \"name\": ").append("\"").append(name).append("\"").append(",\n");
            if(country!=null&&!country.isEmpty())
                json.append("      \"country\": ").append("\"").append(country).append("\"").append(",\n");
            if(age!=0)
                json.append("      \"age\": "+age+",\n");
            if(gender!=null&&!gender.isEmpty())
                json.append("      \"gender\": \""+gender+"\",\n");
            json.append("      \"birthDate\": ").append("\"").append(birthDate).append("\"").append(",\n");
            json.deleteCharAt(json.length()-2);
            json.deleteCharAt(json.length()-3);
            json.deleteCharAt(json.length()-3);
            json.deleteCharAt(json.length()-3);
            json.deleteCharAt(json.length()-3);
            json.deleteCharAt(json.length()-3);
            json.deleteCharAt(json.length()-3);
            json.append("    },");
            return json.toString();
        }
    }
}