package org.example.Classes;

import org.example.Enums.Genre;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;

public abstract class Production implements Comparable {
    private String title;
    private String type;
    private List<String> directors;
    private List<String> actors;
    private List<Genre> genres;
    private List<Rating> ratings;
    private String plot;
    private Double averageRating;
    private Integer releaseYear;
    private String image_path;
    private ImageIcon image;


    public void setImage(ImageIcon image) {
        this.image = image;
    }
    public int getRatingSize() {
        int i=0;
        for(Rating rating:this.ratings){
            if(rating.getRating()!=0)
                i++;
            else
                System.out.println("Rating not available");
        }
        return i;
    }
    public int getNumberOfActors(){
        return actors.size();
    }
    public int getNumberOfDirectors(){
        return directors.size();
    }

    public Production(String title, String type, List<String> directors, List<String> actors, List<Genre> genres, List<Rating> ratings, String plot, Double averageRating, int releaseYear, String image_path) {
        this.title = title;
        this.type = type;
        this.directors = directors;
        this.actors = actors;
        this.genres = genres;
        this.ratings = ratings;
        this.plot = plot;
        this.averageRating = averageRating;
        this.releaseYear = releaseYear;
        this.image_path = image_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getDirectors() {
        return directors;
    }

    public void setDirectors(List<String> directors) {
        this.directors = directors;
    }

    public List<String> getActors() {
        return actors;
    }

    public void setActors(List<String> actors) {
        this.actors = actors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public ImageIcon getImage() {
        return image;
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

    public abstract String displayInfo(String front);
    public String toJson() {
        StringBuilder json = new StringBuilder();
        json.append("  {\n");
        if(this.getTitle()!=null&&!this.getTitle().isEmpty())
            json.append("    \"title\": \"").append(this.getTitle()).append("\",\n");
        json.append("    \"type\": \"").append(this.getType()).append("\",\n");

        if(this.getDirectors()!=null&&!this.getDirectors().isEmpty()) {
            json.append("    \"directors\": [\n");
            for (String director : this.getDirectors()) {
                json.append("      \"").append(director).append("\",\n");
            }
            json.deleteCharAt(json.length() - 2);
            json.append("    ],\n");
        }

        if(this.getActors()!=null&&!this.getActors().isEmpty())
        {
            json.append("    \"actors\": [\n");
            for (String actor : this.getActors()) {
                json.append("      \"").append(actor).append("\",\n");
            }
            json.deleteCharAt(json.length() - 2);
            json.append("    ],\n");
        }
        if(this.getGenres()!=null&&!this.getGenres().isEmpty())
        {
            json.append("    \"genres\": [\n");
            for (Genre genre : this.getGenres()) {
                json.append("      \"").append(genre).append("\",\n");
            }
            json.deleteCharAt(json.length() - 2);
            json.append("    ],\n");
        }
        if(this.getRatings()!=null&&!this.getRatings().isEmpty())
        {
            json.append("    \"ratings\": [\n");
            for (Rating rating : this.getRatings()) {
                json.append(rating.toJson());
            }
            json.deleteCharAt(json.length() - 2);
            json.append("    ],\n");
        }
        if(this.getPlot()!=null&&!this.getPlot().isEmpty())
            json.append("    \"plot\": \"").append(this.getPlot()).append("\",\n");
        if(this.averageRating!=null&&this.getAverageRating()!=0.0)
            json.append("    \"averageRating\": ").append(this.getAverageRating()).append(",\n");
        if(this instanceof Movie&&((Movie) this).getDuration()!=null&&!((Movie) this).getDuration().isEmpty())
            json.append("    \"duration\": \"").append(((Movie) this).getDuration()).append("\",\n");
        if(this.getReleaseYear()!=0)
            json.append("    \"releaseYear\": ").append(this.getReleaseYear()).append(",\n");
        json.append("    \"image\": \"").append(image_path).append("\",\n");

        if (this instanceof Series){
                json.append("    \"numSeasons\": ").append(((Series) this).getSeasons_nr()).append(",\n");
                if (((Series) this).getSeasons_nr() != 0) {
                    json.append("    \"seasons\": {\n");
                    for (Map.Entry<String, List<Episode>> entry : ((Series) this).getSeasons().entrySet()) {
                        json.append("      \"").append(entry.getKey()).append("\": [\n");
                        for (Episode episode : entry.getValue()) {
                            json.append(episode.toJson());
                        }
                        json.deleteCharAt(json.length() - 2);
                        json.append("      ],\n");

                    }
                    json.deleteCharAt(json.length() - 2);
                    json.append("    },\n");
                }
        }
        json.deleteCharAt(json.length() - 2);
        json.append("  },\n");
        return json.toString();
    }

    public int compareTo(Production production) {
        return this.title.compareTo(production.title);
    }
}
