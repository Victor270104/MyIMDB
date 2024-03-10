package org.example.Classes;

import org.example.Interfaces.Observer;
import org.example.Interfaces.Subject;

import java.util.ArrayList;
import java.util.List;

public class Rating implements Subject {
    private String username;
    private Integer rating;
    private String comment;

    List <Observer> observers = new ArrayList<>();
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

    public int getRating() {
        return rating;
    }
    public String getComment() {
        return comment;
    }

    public Rating(String username, int rating, String comment) {
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public int getExperience() {
        for(User user : IMDB.getInstance().getUsers()){
            if(user.getUsername().equals(this.username)){
                user.calculateExperience();
                return user.getExperience();
            }
        }
        return 0;
    }

    public String toJson(){
        StringBuilder json = new StringBuilder();
        json.append("      {\n");
        json.append("        \"username\": ").append("\"").append(username).append("\"").append(",\n");
        json.append("        \"rating\": ").append(rating).append(",\n");
        json.append("        \"comment\": ").append("\"").append(comment).append("\"\n");
        json.append("      },\n");
        return json.toString();
    }

    public String displayInfo() {
        StringBuilder display = new StringBuilder();
        display.append("Username: ").append(username);
        display.append(", rating: ").append(rating);
        if(comment!=null&&!comment.isEmpty())
            display.append(", comment: ").append(comment);
        return display.toString();
    }
}
