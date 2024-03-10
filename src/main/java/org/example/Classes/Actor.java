package org.example.Classes;

import java.util.ArrayList;
import java.util.List;

public class Actor implements Comparable{
    private String name;
    private List<Role> performances;
    private String biography;
    private List<Rating> ratings;
    private Double averageRating;

    public Actor(String name, List<Role> performances, String biography) {
        this.name = name;
        this.performances = performances;
        this.biography = biography;
        this.ratings = new ArrayList<>();
        this.averageRating = 0.0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Role> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Role> performances) {
        this.performances = performances;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }
    public Double getAverageRating() {
        return averageRating;
    }
    public int getNumberOfPerformances(){
        return performances.size();
    }
    public int getNumberOfRatings(){
        return ratings.size();
    }

    public Double getRating(){
        return averageRating;
    }
    @Override
    public int compareTo(Object o) {
        if(o instanceof Actor){
            Actor actor=(Actor) o;
            return this.name.compareTo(actor.name);}
        if(o instanceof Movie){
            Movie movie=(Movie) o;
            return this.name.compareTo(movie.getTitle());}
        if(o instanceof Series){
            Series series=(Series) o;
            return this.name.compareTo(series.getTitle());}
        return 0;
    }
    public void calculateRating(){
        if(ratings.isEmpty())
            this.averageRating=0.0;
        else{
            double sum=0;
            for(Rating rating:ratings){
                sum+=rating.getRating();
            }
            this.averageRating=sum/ratings.size();
        }
    }

    public String displayInfo() {
        StringBuilder display = new StringBuilder();
        display.append("Actor: ").append(name).append("\n");
        if(biography!=null)
            display.append("Biography: ").append(biography).append("\n");
        if(!performances.isEmpty()) {
            display.append("Performances: ").append("\n");
            for (Role role : performances) {
                display.append("   ").append(role.displayInfo()).append("\n");
            }
        }
        if(averageRating!=0.0)
            display.append("Average rating: ").append(averageRating).append("\n");
        else
            display.append("Average rating: ").append("No ratings yet").append("\n");
        if(!ratings.isEmpty()) {
            display.append("Ratings: ").append("\n");
            for (Rating rating : ratings) {
                display.append("   ").append(rating.displayInfo()).append("\n");
            }
        }
        return display.toString();
    }
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("  {\n");
        if(name!=null&&!name.isEmpty())
            json.append("    \"name\": \"").append(name).append("\",\n");
        if(performances!=null&&!performances.isEmpty())
        {
            json.append("    \"performances\": [\n");
            for (Role role : performances) {
                json.append(role.toJson());
            }
            json.deleteCharAt(json.length() - 2);
            json.append("    ],\n");
        }
        if(biography!=null&&!biography.isEmpty())
            json.append("    \"biography\": \"").append(biography).append("\",\n");
        if(this.getRatings()!=null&&!this.getRatings().isEmpty())
        {
            json.append("    \"ratings\": [\n");
            for (Rating rating : this.getRatings()) {
                json.append(rating.toJson());
            }
            json.append("    ],\n");
        }
        json.deleteCharAt(json.length() - 2);
        json.append("  },\n");
        return json.toString();
    }
}
