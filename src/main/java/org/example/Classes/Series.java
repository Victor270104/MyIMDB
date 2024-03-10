package org.example.Classes;

import org.example.Enums.Genre;

import java.util.*;

public class Series extends Production implements Comparable{
    private int seasons_nr;
    private Map<String, List<Episode>> seasons;

    public Series(String title, String type, List<String> directors, List<String> actors, List<Genre> genres, List<Rating> ratings, String plot, Double averageRating, Integer releaseYear, Integer seasons_nr, Map<String, List<Episode>> seasons, String image_path) {
        super(title, type, directors, actors, genres, ratings, plot, averageRating, releaseYear, image_path);
        this.seasons_nr = seasons_nr;
        this.seasons = seasons;
    }

    public void setSeasons_nr(int seasons_nr) {
        this.seasons_nr = seasons_nr;
    }

    public int getSeasons_nr() {
        return seasons_nr;
    }

    public int getNumberOfEpisodes(){
        int i=0;
        for(Map.Entry<String, List<Episode>> entry : this.seasons.entrySet()) {
            i+=entry.getValue().size();
        }
        return i;
    }

    public void add_sez(){
       System.out.println("Previous number of seasons was: "+this.seasons_nr);
       System.out.print("Enter the new number of seasons: ");
       int num;
       Scanner scanner12 = new Scanner(System.in);
       while (true){
           try {
               num= scanner12.nextInt();
               break;
           }
           catch (InputMismatchException e){
               System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
               scanner12.next();
           }
       }
       this.seasons_nr=num;
       System.out.println("Previous seasons were: ");
       System.out.println("\u001B[37m"+this.getSeasons().keySet()+","+"\u001B[0m");
       this.seasons.clear();
       for(int i=0; i<num; i++){
           System.out.println("Enter the name of the season:");
           Scanner scanner13 = new Scanner(System.in);
           String season = scanner13.nextLine();
           System.out.println("Enter the number of episodes:");
           Scanner scanner14 = new Scanner(System.in);
           while (true){
               try {
                   num= scanner14.nextInt();
                   break;
               }
               catch (InputMismatchException e){
                   System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                   scanner14.next();
               }
           }
           List<Episode> episodes = new ArrayList<>();
           for(int j=0; j<num; j++){
               System.out.println("Enter the name of the episode:");
               Scanner scanner15 = new Scanner(System.in);
               String episode = scanner15.nextLine();
               System.out.println("Enter the duration of the episode:");
               Scanner scanner16 = new Scanner(System.in);
               String duration = scanner16.nextLine();
               episodes.add(new Episode(episode, duration));
           }
           this.seasons.put(season, episodes);
       }
   }

    public void setSeasons(Map<String, List<Episode>> seasons) {
        this.seasons = seasons;
    }

    public Map<String, List<Episode>> getSeasons() {
        return seasons;
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
        display.append(front).append("Release year: ").append(this.getReleaseYear()).append("\n");
        if(this.seasons_nr!=0) {
            display.append(front).append("Number of seasons: ").append(this.seasons_nr).append("\n");
            for(Map.Entry<String, List<Episode>> entry : this.seasons.entrySet()) {
                display.append(front).append("   ").append(entry.getKey()).append("\n");
                for(Episode episode : entry.getValue()) {
                    display.append(front).append("      ").append(episode.displayInfo());
                }
            }
        }
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
        if(this.getRatings()!=null&&!this.getRatings().isEmpty()){
            display.append(front).append("Ratings: ").append("\n");
            for (Rating rating : this.getRatings()) {
                display.append(front).append("   ").append(rating.displayInfo()).append("\n");
            }
        }
        return display.toString();
    }
}
