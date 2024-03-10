package org.example.Classes;

import org.example.Enums.Genre;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Movie extends Production implements Comparable{
    private String duration;

    public Movie(String title, String type, List<String> directors, List<String> actors, List<Genre> genres, List<Rating> ratings, String plot, Double averageRating, Integer releaseYear, String duration, String image_path) {
        super(title, type, directors, actors, genres, ratings, plot, averageRating, releaseYear, image_path);
        this.duration = duration;
    }
    public void update_duration() {
        System.out.println("Previous duration was: "+this.duration);
        System.out.print("Enter the new duration:");
        Scanner scanner11 = new Scanner(System.in);
        String duration = scanner11.nextLine();
        this.setDuration(duration);
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    @Override
    public int compareTo(Object o) {
        if(o instanceof Actor){
            Actor actor=(Actor) o;
            return this.getTitle().compareTo(actor.getName());}
        if(o instanceof Movie){
            Movie movie=(Movie) o;
            return this.getTitle().compareTo(movie.getTitle());}
        if(o instanceof Series){
            Series series=(Series) o;
            return this.getTitle().compareTo(series.getTitle());}
        return 0;
    }

    public String displayInfo(String front) {
        StringBuilder display = new StringBuilder();
        display.append(front).append("Title: ").append(this.getTitle()).append("\n");
        display.append(front).append("Type: ").append(this.getType()).append("\n");
        if(this.getPlot()!=null&&!this.getPlot().equals(""))
            display.append(front).append("Plot: ").append(this.getPlot()).append("\n");
        if(!this.getGenres().isEmpty()) {
            display.append(front).append("Genres: ").append("\n");
            for (Genre genre : this.getGenres()) {
                display.append(front).append("   ").append(genre).append("\n");
            }
        }
        if(this.getAverageRating()!=null)
            display.append(front).append("Average rating: ").append(this.getAverageRating()).append("\n");
        else
            display.append(front).append("Average rating: ").append("No ratings yet").append("\n");
        if (this.duration!=null&&!this.duration.equals(""))
            display.append(front).append("Duration: ").append(this.duration).append("\n");
        display.append(front).append("Release year: ").append(this.getReleaseYear()).append("\n");
        if(!this.getActors().isEmpty()) {
            display.append(front).append("Actors: ").append("\n");
            for (String actor : this.getActors()) {
                display.append(front).append("   ").append(actor).append("\n");
            }
        }
        if(!this.getDirectors().isEmpty()) {
            display.append(front).append("Directors: ").append("\n");
            for (String director : this.getDirectors()) {
                display.append(front).append("   ").append(director).append("\n");
            }
        }
        if(this.getRatings()!=null){
            display.append(front).append("Ratings: ").append("\n");
            for (Rating rating : this.getRatings()) {
                display.append(front).append("   ").append(rating.displayInfo()).append("\n");
            }
        }
        return display.toString();
    }
}
