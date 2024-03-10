package org.example.Classes;



import org.example.Enums.AccountType;
import org.example.Enums.Genre;
import org.example.Enums.RequestTypes;
import org.example.Pages.*;
import org.example.Terminal.Terminal;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class IMDB {

    private List<User> users=new ArrayList<>();
    private List <Actor> actors=new ArrayList<>();
    private List<Request> requests=new ArrayList<>();
    private List <Production> productions=new ArrayList<>();

    public List<Actor> getActors() {
        return actors;
    }
    public List<Request> getRequests() {
        return requests;
    }

    public List<Production> getProductions() {
        return productions;
    }

    public List<User> getUsers() {
        return users;
    }
    private void load_accounts(String path){
        JSONParser parser = new JSONParser();
        FileReader file;
        try {
            file = new FileReader(path+"accounts.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            JSONArray prod_array = (JSONArray) parser.parse(file);

            for (Object prodObject : prod_array) {
                JSONObject accJson = (JSONObject) prodObject;
                String username = (String) accJson.get("username");
                String exp = (String) accJson.get("experience");
                int experience;
                if(exp!=null){
                    experience = Integer.parseInt(exp);
                }
                else{
                    experience = 0;
                }
                JSONObject infoJson = (JSONObject) accJson.get("information");
                JSONObject credJson = (JSONObject) infoJson.get("credentials");
                String email = (String) credJson.get("email");
                String password = (String) credJson.get("password");
                String name = (String) infoJson.get("name");
                String country = (String) infoJson.get("country");
                int age;
                if(infoJson.get("age")!=null){
                    age = (int)((long) infoJson.get("age"));
                }
                else{
                    age = 0;
                }
                String gender = (String) infoJson.get("gender");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDateTime time;
                if (infoJson.get("birthDate") != null) {
                    String birthDateString = infoJson.get("birthDate")+ "T00:00:00";
                    time = LocalDateTime.parse(birthDateString, formatter);
                }
                else{
                    time = null;
                }
                String type = (String) accJson.get("userType");
                SortedSet<Object> favorites = new TreeSet<>();
                SortedSet<String> fav = new TreeSet<>();
                if(accJson.get("favoriteProductions")!=null){
                    JSONArray fav_prodJson = (JSONArray) accJson.get("favoriteProductions");
                    for (Object fav_prodObject : fav_prodJson) {
                        for(Production production : productions){
                            if(production.getTitle().equals((String) fav_prodObject)){
                                favorites.add(production);
                            }
                        }
                    }
                }
                if(accJson.get("favoriteActors")!=null){
                    JSONArray fav_actorsJson = (JSONArray) accJson.get("favoriteActors");
                    for (Object fav_actorObject : fav_actorsJson) {
                        for(Actor actor : actors){
                            if(actor.getName()!=null&&actor.getName().equals((String) fav_actorObject)){
                                favorites.add(actor);
                            }
                        }
                    }
                }
                List<String> notifications = new ArrayList<>();
                if(accJson.get("notifications")!=null){
                    JSONArray notificationsJson= (JSONArray) accJson.get("notifications");
                    for (Object notificationObject : notificationsJson) {
                        notifications.add((String) notificationObject);
                    }
                }
                else{
                    notifications=null;
                }

                SortedSet<Object> added = new TreeSet<>();
                SortedSet<String> ad = new TreeSet<>();
                if(accJson.get("productionsContribution")!=null){
                    JSONArray fav_prodJson = (JSONArray) accJson.get("productionsContribution");
                    for (Object fav_prodObject : fav_prodJson) {
                        for(Production production : productions){
                            if(production.getTitle().equals((String) fav_prodObject)){
                                added.add(production);
                            }
                        }
                    }
                }
                if(accJson.get("actorsContribution")!=null){
                    JSONArray fav_actorsJson = (JSONArray) accJson.get("actorsContribution");
                    for (Object fav_actorObject : fav_actorsJson) {
                        for(Actor actor : actors){
                            if(actor.getName()!=null&&actor.getName().equals((String) fav_actorObject)){
                                added.add(actor);
                            }
                        }
                    }
                }

                Credentials credentials = new Credentials(email, password);
                User.Information information = new User.Information.Builder(credentials, name).country(country).age(age).gender(gender).birthDate(time).build();
                switch (type) {
                    case "Regular" -> {

                        User regular = UserFactory.createUser(AccountType.REGULAR,information,username,experience,notifications,favorites,new ArrayList<>(),added);
                        users.add(regular);
                    }
                    case "Admin" -> {
                        User admin = UserFactory.createUser(AccountType.ADMIN,information,username,experience,notifications,favorites,new ArrayList<>(),added);
                        users.add(admin);
                    }
                    case "Contributor" -> {
                        User contributor = UserFactory.createUser(AccountType.CONTRIBUTOR,information,username,experience,notifications,favorites,new ArrayList<>(),added);
                        users.add(contributor);
                    }
                }
            }
        }
        catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private void load_actors(String path) {
        JSONParser parser = new JSONParser();
        FileReader file;
        try {
             file = new FileReader(path+"actors.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
        JSONArray actorArray = (JSONArray) parser.parse(file);

        for (Object actorObject : actorArray) {
            JSONObject actorJson = (JSONObject) actorObject;
            String name = (String) actorJson.get("name");
            String biography = (String) actorJson.get("biography");
            JSONArray performancesJson = (JSONArray) actorJson.get("performances");
            List<Role> performances = new ArrayList<>();
            if(performancesJson!=null)
                for (Object performanceObject : performancesJson) {
                    JSONObject performanceJson = (JSONObject) performanceObject;
                    String title = (String) performanceJson.get("title");
                    String type = (String) performanceJson.get("type");

                    Role role = new Role(title, type);
                    performances.add(role);
                }

            // Create Actor object and add to the list
            Actor actor = new Actor(name, performances, biography);
            actors.add(actor);
        }
        }
        catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private void load_production(String path){
        JSONParser parser = new JSONParser();
        FileReader file;
        try {
            file = new FileReader(path+"production.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            JSONArray prod_array = (JSONArray) parser.parse(file);

            for (Object prodObject : prod_array) {
                JSONObject prodJson = (JSONObject) prodObject;
                String type = (String) prodJson.get("type");
                String title = (String) prodJson.get("title");
                if(title.equals("Titanic")){
                    System.out.println("GATA");
                }
                String image_path = (String) prodJson.get("image");
                ImageIcon image = new ImageIcon(image_path);
                Image scaled = image.getImage().getScaledInstance(100, 150, Image.SCALE_SMOOTH);
                ImageIcon scaledimg = new ImageIcon(scaled);
                JSONArray directorsJson = (JSONArray) prodJson.get("directors");
                List<String> directors = new ArrayList<>();
                if(directorsJson!=null&&!directorsJson.isEmpty())
                    for (Object directorObject : directorsJson) {
                        directors.add((String) directorObject);
                    }
                JSONArray actorsJson = (JSONArray) prodJson.get("actors");
                List<String> actors = new ArrayList<>();
                if(actorsJson!=null&&!actorsJson.isEmpty())
                    for (Object actorObject : actorsJson) {
                        actors.add((String) actorObject);
                    }
                JSONArray genresJson = (JSONArray) prodJson.get("genres");
                List<Genre> genres = new ArrayList<>();
                if(genresJson!=null&&!genresJson.isEmpty())
                    for (Object genreObject : genresJson) {
                        genres.add(Genre.valueOf((String) genreObject));
                    }
                JSONArray ratingsJson = (JSONArray) prodJson.get("ratings");
                List<Rating> ratings = new ArrayList<>();
                if(ratingsJson!=null&&!ratingsJson.isEmpty())
                    for(Object ratingObject : ratingsJson){
                        JSONObject ratingJson = (JSONObject) ratingObject;
                        String source = (String) ratingJson.get("username");
                        int rating = (int)((long) ratingJson.get("rating"));
                        String comment = (String) ratingJson.get("comment");
                        ratings.add(new Rating(source, rating,comment));
                    }
                String plot = (String) prodJson.get("plot");
                Double averageRating = (Double) prodJson.get("averageRating");
                int releaseYear;
                if(prodJson.get("releaseYear")!=null){
                    releaseYear = (int)((long) prodJson.get("releaseYear"));
                }
                else{
                    releaseYear = 0;
                }
                if(type.equals("Movie")){
                    String duration = (String) prodJson.get("duration");

                    Movie production= new Movie(title, "Movie", directors, actors, genres, ratings, plot, averageRating, releaseYear, duration, image_path);
                    production.setImage(scaledimg);
                    productions.add(production);
                }
                else if(type.equals("Series")){
                    int seasons_nr = (int)((long) prodJson.get("numSeasons"));
                    Map<String, List<Episode>> seasons = new TreeMap<>();
                    JSONObject seasonsJson = (JSONObject) prodJson.get("seasons");
                    for(int i=1;i<=seasons_nr;i++){
                        JSONArray episodesJson = (JSONArray) seasonsJson.get("Season "+i);
                        List<Episode> episodes = new ArrayList<>();
                        for(Object episodeObject : episodesJson){
                            JSONObject episodeJson = (JSONObject) episodeObject;
                            String episode_title = (String) episodeJson.get("episodeName");
                            String episode_duration = (String) episodeJson.get("duration");
                            episodes.add(new Episode(episode_title, episode_duration));
                        }
                        seasons.put("Season "+i,episodes);
                    }

                    Series production = new Series(title, "Series", directors, actors, genres, ratings, plot, averageRating, releaseYear, seasons_nr, seasons, image_path);
                    production.setImage(scaledimg);
                    productions.add(production);
                }
            }
        }
        catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private void load_requests(String path){

        JSONParser parser = new JSONParser();
        FileReader file;
        try {
            file = new FileReader(path+"requests.json");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            JSONArray requestArray = (JSONArray) parser.parse(file);

            for (Object requestObject : requestArray) {
                JSONObject requestJson = (JSONObject) requestObject;
                String type_string = (String) requestJson.get("type");
                RequestTypes type = RequestTypes.valueOf(type_string);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDateTime time = LocalDateTime.parse((String) requestJson.get("createdDate"), formatter);
                String user = (String) requestJson.get("username");
                String admin = (String) requestJson.get("to");
                String description = (String) requestJson.get("description");
                String actor_name = (String) requestJson.get("actorName");
                String movie_name = (String) requestJson.get("movieTitle");
                Request request = new Request(type, time, user, admin, description, movie_name, actor_name);
                int c=0;
                if((type_string.equals("ACTOR_ISSUE"))||(type_string.equals("MOVIE_ISSUE"))){
                    for(User user1:users){
                        if(user1.getUsername().equals(admin)&&(user1.getAccountType().equals(AccountType.ADMIN)||user1.getAccountType().equals(AccountType.CONTRIBUTOR))){
                            Staff staff = (Staff) user1;
                            if(staff.getRequests()==null){
                                staff.setRequests(new ArrayList<>());
                            }
                            staff.getRequests().add(request);
                            c=1;
                            break;
                        }
                    }
                }
                if(c==0){
                    Admin.RequestHolder.addReq(request);
                }
                IMDB.getInstance().requests.add(request);
            }
        }
        catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
    private void corrections(){
        int c;
        for(Production p : IMDB.getInstance().getProductions()){
            for(String a : p.getActors()){
                c=0;
                for(Actor actor : IMDB.getInstance().getActors()){
                    if(actor.getName().equals(a)){
                        c=1;
                    }
                }
                if(c==0){
                    Admin.RequestHolder.addReq(new Request(RequestTypes.OTHERS,LocalDateTime.now(),"System","STAFF","Actor '"+a+"' not found in database in the prod "+p.getTitle(),p.getTitle(),a));
                }
            }
        }
        for(Actor a:IMDB.getInstance().getActors()){
            for(Role r:a.getPerformances()){
                c=0;
                for(Production p:IMDB.getInstance().getProductions()){
                    if(p.getTitle().equals(r.getProduction())){
                        c=1;
                    }
                }
                if(c==0){
                    Admin.RequestHolder.addReq(new Request(RequestTypes.OTHERS,LocalDateTime.now(),"System","STAFF","Production '"+r.getProduction()+"' not found in database",r.getProduction(),a.getName()));
                }
            }
        }
    }
    public void save_data(String path){

            String filePath = path + "new_actors.json";
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                StringBuilder json = new StringBuilder();
                if(!IMDB.getInstance().getActors().isEmpty()) {
                fileWriter.write("[\n");
                for (Actor actor : IMDB.getInstance().getActors()) {
                    json.append(actor.toJson());
                }
                json.deleteCharAt(json.length() - 2);
                json.append("]");
                json.append("\n");
                }
                fileWriter.write(json.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


            filePath = path + "new_production.json";
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                StringBuilder json = new StringBuilder();
                if(!IMDB.getInstance().getProductions().isEmpty()) {
                    fileWriter.write("[\n");
                    for (Production production : IMDB.getInstance().getProductions()) {
                        json.append(production.toJson());
                    }
                    json.deleteCharAt(json.length() - 2);
                    json.append("]");
                    json.append("\n");
                }
                fileWriter.write(json.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            filePath = path + "new_requests.json";
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                StringBuilder json = new StringBuilder();
                if(!IMDB.getInstance().getRequests().isEmpty()) {
                    fileWriter.write("[\n");
                    for (Request request : IMDB.getInstance().getRequests()) {
                        json.append(request.toJson());
                    }
                    json.deleteCharAt(json.length() - 2);
                    json.append("]");
                    json.append("\n");
                }
                fileWriter.write(json.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }


            filePath = path + "new_accounts.json";
            try (FileWriter fileWriter = new FileWriter(filePath)) {
                StringBuilder json = new StringBuilder();
                if(!IMDB.getInstance().getUsers().isEmpty()) {
                    fileWriter.write("[\n");
                    for (User account : IMDB.getInstance().getUsers()) {
                        json.append(account.toJson());
                    }
                    json.deleteCharAt(json.length() - 2);
                    json.append("]");
                    json.append("\n");
                }
                fileWriter.write(json.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    private IMDB(){}
    public void run(String path){
        load_actors(path);
        load_production(path);
        load_accounts(path);
        load_requests(path);
        //corrections();
        System.out.println("Tastati cum doriti aplicatia! "+"\033[34m"+"Terminal(1) "+"\033[0m"+"sau"+"\033[34m"+" Interfata Grafica(2)"+"\033[0m"+":");
        Scanner scanner = new Scanner(System.in);
        int userInput;
        while (true) {
            try {
                userInput = scanner.nextInt();
                if (userInput == 1 || userInput == 2) {
                    break;
                }
                else{
                    System.out.println("\u001B[31m"+"Greseala in tastare!"+"\u001B[0m"+" Tastati 1 sau 2!");
                }
            } catch (InputMismatchException e) {
                System.out.println("\u001B[31m"+"Greseala in tastare!"+"\u001B[0m"+" Tastati 1 sau 2!");
                scanner.next();
            }
        }

        if(userInput==1){
            CLI(this);
        }
        else{
            GUI(this);
        }
    }
    public void GUI(IMDB imdb){
        new LoginPage();
    }
    public void CLI(IMDB imdb){
        new Terminal().Login(imdb);
    }
    private static IMDB instance;
    public static IMDB getInstance(){
        if (instance == null) {
            instance = new IMDB();
        }
        return instance;
    }
}

