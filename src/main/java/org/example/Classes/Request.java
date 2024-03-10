package org.example.Classes;

import org.example.Enums.RequestTypes;
import org.example.Interfaces.Observer;
import org.example.Interfaces.RequestsManager;
import org.example.Interfaces.Subject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Request implements Subject {
    private RequestTypes type;
    private LocalDateTime time;

    private String user;
    private String admin;
    private String description;
    private String movie_name;
    private String actor_name;
    private List<Observer> observers=new ArrayList<>();

    public String getUsername(){
        return this.user;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getAdmin() {
        return admin;
    }

    public String getDescription() {
        return description;
    }

    public String getMovie_name() {
        return movie_name;
    }

    public String getActor_name() {
        return actor_name;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver(String message) {
        for(Observer observer:observers){

            observer.notify(message);
        }
    }

    public RequestTypes gettype(){
        return this.type;
    }

    public Request(RequestTypes type, LocalDateTime time, String user, String admin, String description, String movie_name, String actor_name) {
        this.type = type;
        this.time = time;
        this.user = user;
        this.admin = admin;
        this.description = description;
        this.movie_name = movie_name;
        this.actor_name = actor_name;
    }

    public String displayInfo(String front) {
        StringBuilder display = new StringBuilder();
        display.append(front).append("Request ").append("\u001B[1m").append(type).append("\u001B[0m").append("\n");
        display.append(front).append("From user ").append("\u001B[1m").append(user).append("\u001B[0m");
        display.append(front).append(" to Admin: ").append("\u001B[1m").append(admin).append("\u001B[0m").append("\n");
        if(description!=null&&!description.equals(""))
            display.append(front).append("Description: ").append(description).append("\n");
        display.append(front).append("Time: ").append(time).append("\n");
        if(movie_name!=null&& !movie_name.isEmpty())
            display.append(front).append("Movie: ").append(movie_name).append("\n");
        if(actor_name!=null&& !actor_name.isEmpty())
            display.append(front).append("Actor: ").append(actor_name).append("\n");
        return display.toString();
    }

    public String toJson(){
        StringBuilder json = new StringBuilder();
        json.append("  {\n");
        json.append("    \"type\": \"").append(type).append("\",\n");
        json.append("    \"createdDate\": \"").append(time).append(":00").append("\",\n");
        json.append("    \"username\": \"").append(user).append("\",\n");
        if(movie_name!=null&& !movie_name.isEmpty())
            json.append("    \"movieTitle\": \"").append(movie_name).append("\",\n");
        if(actor_name!=null&& !actor_name.isEmpty())
            json.append("    \"actorName\": \"").append(actor_name).append("\",\n");
        json.append("    \"to\": \"").append(admin).append("\",\n");
        if(description!=null&& !description.isEmpty())
            json.append("    \"description\": \"").append(description).append("\",\n");
        json.deleteCharAt(json.length()-2);
        json.append("  },\n");
        return json.toString();
    }
}
