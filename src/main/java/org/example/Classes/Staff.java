package org.example.Classes;

import org.example.Enums.AccountType;
import org.example.Enums.Genre;
import org.example.Enums.RequestTypes;
import org.example.Interfaces.StaffInterface;

import javax.swing.*;
import java.io.File;
import java.util.*;
import java.util.List;

public abstract class Staff extends User implements StaffInterface {
    private List<Request> requests;
    private SortedSet<Object> added;

    public List<Request> getRequests() {
        return requests;
    }
    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public SortedSet<Object> getAdded() {
        return added;
    }

    public void setAdded(SortedSet<Object> added) {
        this.added = added;
    }

    public void SolveRequest(Request request){

        if(request.gettype().equals(RequestTypes.MOVIE_ISSUE)){
            System.out.println("Description: "+request.getDescription());
            System.out.println("Enter 1 if you want to delete the request, 2 if you want to update the production, 3 if you want to delete the production");
            Scanner scanner = new Scanner(System.in);
            int choice;
            while (true){
                try {
                    choice = scanner.nextInt();
                    if(choice==1 || choice==2 || choice==3){
                        break;
                    }
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for delete, 2 for update or 3 for delete production");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for delete, 2 for update or 3 for delete production");
                    scanner.next();
                }
            }
            if(choice==3){
                for(Production production: IMDB.getInstance().getProductions())
                {
                    if(production.getTitle().equals(request.getMovie_name()))
                    {
                        for(User user1 : IMDB.getInstance().getUsers()){
                            if(user1.getFavorites()!=null)
                                user1.getFavorites().remove(production);
                            if(!user1.getAccountType().equals(AccountType.REGULAR))
                            {
                                Staff staff=(Staff)user1;
                                staff.added.remove(production);
                            }
                        }
                        IMDB.getInstance().getProductions().remove(production);
                        System.out.println("\u001B[32m"+"Production removed successfully"+"\u001B[0m");
                        break;
                    }
                }
            }
            if(choice==3||choice==1){
                return;
            }
            Production production = null;
            for(Production p:IMDB.getInstance().getProductions()){
                if(p.getTitle().equals(request.getMovie_name())){
                    production=p;
                    break;}
            }
            System.out.println("Previous name was: "+production.getTitle());
            System.out.print("Enter the new name of the production:");
            Scanner scanner3 = new Scanner(System.in);
            String newName = scanner3.nextLine();
            production.setTitle(newName);
            System.out.println("Previous directors were: ");
            System.out.println("\u001B[37m"+production.getDirectors()+","+"\u001B[0m");
            production.getDirectors().clear();
            System.out.print("Enter the new number of directors:");
            Scanner scanner4 = new Scanner(System.in);
            int num;
            while (true){
                try {
                    num= scanner4.nextInt();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                    scanner4.next();
                }
            }
            for(int i=0; i<num; i++){
                System.out.println("Enter the name of the director:");
                Scanner scanner5 = new Scanner(System.in);
                String director = scanner5.nextLine();
                production.getDirectors().add(director);
            }
            System.out.println("Previous actors were: ");
            System.out.println("\u001B[37m"+production.getActors()+","+"\u001B[0m");
            production.getActors().clear();
            System.out.print("Enter the new number of actors:");
            Scanner scanner6 = new Scanner(System.in);
            while (true){
                try {
                    num= scanner6.nextInt();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                    scanner6.next();
                }
            }
            for(int i=0; i<num; i++){
                System.out.println("Enter the name of the actor:");
                Scanner scanner7 = new Scanner(System.in);
                String actor = scanner7.nextLine();
                production.getActors().add(actor);
            }
            System.out.println("Previous genres were: ");
            System.out.println("\u001B[37m"+production.getGenres()+","+"\u001B[0m");
            production.getGenres().clear();
            System.out.print("Enter the new number of genres:");
            Scanner scanner8 = new Scanner(System.in);
            while (true){
                try {
                    num= scanner8.nextInt();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                    scanner8.next();
                }
            }
            for(int i=0; i<num; i++){
                System.out.println("Enter the genre:");
                Scanner scanner9 = new Scanner(System.in);
                String genre = scanner9.nextLine();
                try {
                    Genre genre1 = Genre.valueOf(genre);
                    production.getGenres().add(genre1);
                }
                catch (IllegalArgumentException e){
                    System.out.println("\u001B[31m"+"Invalid genre."+"\u001B[0m"+" Please enter a valid genre.");
                    i--;  // Decrement i to repeat the current iteration
                }
            }
            System.out.println("Previous release year was: "+production.getReleaseYear());
            System.out.print("Enter the new release year:");
            Scanner scanner10 = new Scanner(System.in);
            while (true){
                try {
                    num= scanner10.nextInt();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                    scanner10.next();
                }
            }
            production.setReleaseYear(num);
            if(production.getType().equals("Movie")){
                ((Movie) production).update_duration();
            }
            if(production.getType().equals("Series")){
                ((Series) production).add_sez();
            }
            System.out.println("\u001B[32m"+"Production updated successfully"+"\u001B[0m");
            return;
        }
        if(request.gettype().equals(RequestTypes.ACTOR_ISSUE)){
            System.out.println("Description: "+request.getDescription());
            System.out.println("Enter 1 if you want to delete the request, 2 if you want to update the actor, 3 if you want to delete the actor");
            Scanner scanner = new Scanner(System.in);
            int choice;
            while (true){
                try {
                    choice = scanner.nextInt();
                    if(choice==1 || choice==2 || choice==3){
                        break;
                    }
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for delete, 2 for update or 3 for delete production");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for delete, 2 for update or 3 for delete production");
                    scanner.next();
                }
            }
            if(choice==3){
                for(Actor actor: IMDB.getInstance().getActors())
                {
                    if(actor.getName().equals(request.getActor_name()))
                    {
                        for(User user1 : IMDB.getInstance().getUsers()){
                            if(user1.getFavorites()!=null)
                                user1.getFavorites().remove(actor);
                            if(!user1.getAccountType().equals(AccountType.REGULAR))
                            {
                                Staff staff=(Staff)user1;
                                staff.added.remove(actor);
                            }
                        }
                        IMDB.getInstance().getActors().remove(actor);
                        System.out.println("\u001B[32m"+"Actor removed successfully"+"\u001B[0m");
                        return;
                    }
                }
            }
            if(choice==3||choice==1){
                return;
            }
            Actor actor = null;
            for(Actor a:IMDB.getInstance().getActors()){
                if(a.getName().equals(request.getActor_name())){
                    actor=a;
                    break;}
            }
            System.out.println("Previous name was: "+actor.getName());
            System.out.print("Enter the new name of the actor:");
            Scanner scanner3 = new Scanner(System.in);
            String newName = scanner3.nextLine();
            actor.setName(newName);
            System.out.println("Previous biography was: "+actor.getBiography());
            System.out.print("Enter the new biography of the actor:");
            Scanner scanner4 = new Scanner(System.in);
            String newBiography = scanner4.nextLine();
            actor.setBiography(newBiography);
            int ceva=1;
            while (ceva==1)
            {
                System.out.print("Previous performances were: ");
                for (Role role : actor.getPerformances()) {
                    System.out.print("\u001B[37m" + role.displayInfo() + "," + "\u001B[0m");
                }
                System.out.println();
                System.out.println("Enter the number of the performance that you want to update, select 0 if you want to add a new performance");
                int num;
                Scanner scanner5 = new Scanner(System.in);
                while (true) {
                    try {
                        num = scanner5.nextInt();
                        if (num <= actor.getPerformances().size()&&num>=0)
                            break;
                        System.out.println("\u001B[31m" + "Invalid input" + "\u001B[0m" + ", try again using a number between 0 and " + actor.getPerformances().size());
                    } catch (InputMismatchException e) {
                        System.out.println("\u001B[31m" + "Invalid input" + "\u001B[0m" + ", try again using a number between 0 and " + actor.getPerformances().size());
                        scanner5.next();
                    }
                }

                System.out.println("Enter the name of the production");
                Scanner scanner6 = new Scanner(System.in);
                String prod = scanner6.nextLine();
                System.out.println("Enter the type of production");
                String type;
                Scanner scanner7 = new Scanner(System.in);
                while (true) {
                    type = scanner7.next();
                    if (type.equals("Movie") || type.equals("Series")) {
                        break;
                    } else {
                        System.out.println("\u001B[31m" + "Invalid input" + "\u001B[0m" + ", try again entering Movie or Series");
                        scanner7.next();
                    }
                }
                Role rol = new Role(prod, type);
                if (num == 0)
                    actor.getPerformances().add(rol);
                else {
                    actor.getPerformances().remove(num - 1);
                    actor.getPerformances().add(num - 1, rol);
                }
                System.out.println("Do you want to update performances again? (y/n)");
                Scanner scanner8 = new Scanner(System.in);
                String answer1;
                while (true) {
                    try {
                        answer1 = scanner8.nextLine();
                        if (answer1.equals("y") || answer1.equals("n")) {
                            break;
                        }
                        System.out.println("\u001B[31m" + "Invalid input" + "\u001B[0m" + ", try again using y for yes or n for no");
                    } catch (InputMismatchException e) {
                        System.out.println("\u001B[31m" + "Invalid input" + "\u001B[0m" + ", try again using y for yes or n for no");
                        scanner8.next();
                    }
                }
                if (answer1.equals("n"))
                    ceva=0;
            }
            System.out.println("\u001B[32m"+"Actor updated successfully"+"\u001B[0m");
            return;
        }
        if(request.gettype().equals(RequestTypes.DELETE_ACCOUNT)){
            System.out.println("Description: "+request.getDescription());
            System.out.println("Enter 1 if you DO NOT WANT to delete the request, 2 if you WANT to delete the account");
            Scanner scanner = new Scanner(System.in);
            int choice;
            while (true){
                try {
                    choice = scanner.nextInt();
                    if(choice==1 || choice==2){
                        break;
                    }
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for delete or 2 for delete account");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for delete or 2 for delete account");
                    scanner.next();
                }
            }
            if(choice==2){
                User deleterious = null;
                for(User user1:IMDB.getInstance().getUsers()){
                    if(user1.getUsername().equals(request.getUsername())){
                        deleterious=user1;
                        break;}
                }
                for(Production production : IMDB.getInstance().getProductions()){
                    for(Rating rating : production.getRatings()){
                        if(rating.getUsername().equals(deleterious.getUsername()))
                            production.getRatings().remove(rating);
                    }
                }
                if(deleterious.getAccountType().equals(AccountType.CONTRIBUTOR)){
                    Contributor contributor = (Contributor) deleterious;
                    for(Production production : IMDB.getInstance().getProductions()){
                        if(contributor.getAdded().contains(production))
                            System.out.println("Productie adaugata de contributor");
                    }
                    for(Actor actor : IMDB.getInstance().getActors()){
                        if(contributor.getAdded().contains(actor))
                            System.out.println("Actor adaugat de contributor");
                    }
                }
                IMDB.getInstance().getUsers().remove(deleterious);
                System.out.println("\u001B[32m"+"User deleted"+"\u001B[0m");
            }
            return;
        }
        if(request.gettype().equals(RequestTypes.OTHERS)){
            System.out.println("Description: "+request.getDescription());
            if(request.getUsername().equals("DELETED_ACCOUNT")){
                System.out.println("Enter 1 if you want to keep the object in system");
                System.out.println("Enter 2 if you want to delete the object from system");
                System.out.println("Enter 3 if you want to cancel the operation");
                int choice;
                Scanner scanner = new Scanner(System.in);
                while (true){
                    try {
                        choice = scanner.nextInt();
                        if(choice==1 || choice==2 || choice==3){
                            break;
                        }
                        System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for keep, 2 for delete or 3 for cancel");
                    }
                    catch (InputMismatchException e){
                        System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for keep, 2 for delete or 3 for cancel");
                        scanner.next();
                    }
                }
                if(choice==1){
                    if(request.getActor_name()!=null&&!request.getActor_name().isEmpty()){
                        for(Actor actor: IMDB.getInstance().getActors()){
                            if(actor.getName().equals(request.getActor_name())){
                                for(User user : IMDB.getInstance().getUsers()){
                                    if(user instanceof Admin){
                                        ((Admin) user).getAdded().add(actor);
                                    }
                                }
                            }
                        }
                    }
                    if(request.getMovie_name()!=null&&!request.getMovie_name().isEmpty()){
                        for(Production production: IMDB.getInstance().getProductions()){
                            if(production.getTitle().equals(request.getMovie_name())){
                                for(User user : IMDB.getInstance().getUsers()){
                                    if(user instanceof Admin){
                                        ((Admin) user).getAdded().add(production);
                                    }
                                }
                            }
                        }
                    }
                    Admin.RequestHolder.getRequests().remove(request);
                    IMDB.getInstance().getRequests().remove(request);
                    System.out.println("\u001B[32m"+"Object kept!"+"\u001B[0m");
                    return;
                }
                if(choice==2){
                    if(request.getMovie_name()!=null&&!request.getMovie_name().isEmpty()){
                        for(Production production : IMDB.getInstance().getProductions()){
                            if(production.getTitle().equals(request.getMovie_name())){
                                for(User user1 : IMDB.getInstance().getUsers()){
                                    if(user1 instanceof Staff staff1){
                                        if(staff1.getRequests()!=null)
                                            for(Request request1 : staff1.getRequests()){
                                                if(request1.gettype().equals(RequestTypes.MOVIE_ISSUE)&&request1.getMovie_name().equals(production.getTitle())){
                                                    staff1.getRequests().remove(request1);
                                                    break;
                                                }
                                            }
                                    }
                                    if(user1.getFavorites()!=null)
                                        user1.getFavorites().remove(production);
                                }
                                IMDB.getInstance().getProductions().remove(production);
                                Admin.RequestHolder.getRequests().remove(request);
                                System.out.println("\u001B[32m"+"Object deleted!"+"\u001B[0m");
                                return;
                            }
                        }
                    }
                    if(request.getActor_name()!=null&&!request.getActor_name().isEmpty()){
                        for(Actor actor : IMDB.getInstance().getActors()){
                            if(actor.getName().equals(request.getActor_name())){
                                for(User user1 : IMDB.getInstance().getUsers()){
                                    if(user1 instanceof Staff staff1){
                                        if(staff1.getRequests()!=null)
                                            for(Request request1 : staff1.getRequests()){
                                                if(request1.gettype().equals(RequestTypes.ACTOR_ISSUE)&&request1.getActor_name().equals(actor.getName())){
                                                    staff1.getRequests().remove(request1);
                                                    break;
                                                }
                                            }
                                    }
                                    if(user1.getFavorites()!=null)
                                        user1.getFavorites().remove(actor);
                                }
                                IMDB.getInstance().getActors().remove(actor);
                                Admin.RequestHolder.getRequests().remove(request);
                                System.out.println("\u001B[32m"+"Object deleted!"+"\u001B[0m");
                                return;
                            }
                        }
                    }
                }
                if(choice==3){
                    return;
                }
            }
            else {
                System.out.println("Enter 1 if you want to add something to the system");
                System.out.println("Enter 2 if you want to delete something from the system");
                System.out.println("Enter 3 if you want to cancel the operation");
                int choice;
                Scanner scanner = new Scanner(System.in);
                while (true){
                    try {
                        choice = scanner.nextInt();
                        if(choice==1 || choice==2 || choice==3){
                            break;
                        }
                        System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for keep, 2 for delete or 3 for cancel");
                    }
                    catch (InputMismatchException e){
                        System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for keep, 2 for delete or 3 for cancel");
                        scanner.next();
                    }
                }
                if(choice==1){
                    this.AddToIMDB();
                }
                if(choice==2){
                    System.out.println("Enter 1 to delete an actor, 2 to delete a production");
                    Scanner scanner1 = new Scanner(System.in);
                    int choice1;
                    while (true){
                        try {

                            choice1 = scanner1.nextInt();
                            if (choice1 == 1 || choice1 == 2) {
                                break;
                            }
                            System.out.println("\u001B[31m" + "Invalid input" + "\u001B[0m" + ", try again using 1 for actor or 2 for production");
                        }
                        catch (InputMismatchException e){
                            System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for actor or 2 for production");
                            scanner1.next();
                        }
                    }
                    if(choice1==1)
                        this.removeActorSystem();
                    if(choice1==2)
                        this.removeProductionSystem();
                }
                Admin.RequestHolder.getRequests().remove(request);
                IMDB.getInstance().getRequests().remove(request);

            }
        }
    }
    public void AddToIMDB (){
        System.out.println("Select 1 to add an actor, 2 to add a production");
        Scanner scanner = new Scanner(System.in);
        int choice;
        while (true){
            try {

                choice = scanner.nextInt();
                if (choice == 1 || choice == 2) {
                    break;
                }
                System.out.println("\u001B[31m" + "Invalid input" + "\u001B[0m" + ", try again using 1 for actor or 2 for production");
            }
            catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for actor or 2 for production");
                scanner.next();
            }
        }
        if(choice==1){
            System.out.println("Enter the name of the actor");
            Scanner scanner1 = new Scanner(System.in);
            String name;
            while (true){
                try {
                    int c=0;
                    name = scanner1.nextLine();
                    if(name.isEmpty())
                        name="No name";
                    for(Actor actor : IMDB.getInstance().getActors()){
                        if(actor.getName().equals(name))
                            c++;
                    }
                    if(c==0)
                        break;
                    System.out.println("\u001B[31m"+"Actor already exists"+"\u001B[0m"+", try again using a different name");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a string");
                    scanner1.next();
                }
            }
            System.out.println("Enter the biography of the actor");
            Scanner scanner2 = new Scanner(System.in);
            String biography = scanner2.nextLine();
            System.out.println("Enter the number of performances of the actor");
            int num;
            while (true){
                try {
                    num= scanner.nextInt();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                    scanner.next();
                }
            }
            List<Role> performances = new ArrayList<>();
            for(int i=0; i<num; i++){
                System.out.println("Enter the name of the production");
                Scanner scanner3 = new Scanner(System.in);
                String prod = scanner3.nextLine();
                System.out.println("Enter the type of production");
                String type;
                while (true) {
                    type= scanner.next();
                    if (type.equals("Movie") || type.equals("Series")) {
                        break;
                    } else {
                        System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again entering Movie or Series");
                    }
                }
                Role rol = new Role(prod, type);
                performances.add(rol);
            }

            for (Actor actor : IMDB.getInstance().getActors()){
                if(actor.getName().equals(name)){
                    actor.setBiography(biography);
                    actor.setPerformances(performances);
                    System.out.println("\u001B[32m"+"Actor updated successfully"+"\u001B[0m");
                    return;
                }
            }
            Actor actor = new Actor(name, performances, biography);
            IMDB.getInstance().getActors().add(actor);
            this.added.add(actor);
            System.out.println("\u001B[32m"+"Actor added successfully"+"\u001B[0m");
        }
        else {
            System.out.println("Enter the name of the production");
            Scanner scanner1 = new Scanner(System.in);
            String name;
            while (true){
                try {
                    int c=0;
                    name = scanner1.nextLine();
                    if(name.isEmpty())
                        name="No name";
                    for(Production production : IMDB.getInstance().getProductions()){
                        if(production.getTitle().equals(name))
                            c++;
                    }
                    if(c==0)
                        break;
                    System.out.println("\u001B[31m"+"Production already exists"+"\u001B[0m"+", try again using a different name");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a string");
                    scanner1.next();
                }
            }
            System.out.println("Enter the type of production");
            String type;
            while (true) {
                type= scanner.next();
                if (type.equals("Movie") || type.equals("Series")) {
                    break;
                } else {
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again entering Movie or Series");
                }
            }
            System.out.println("Genres: ");
            for (Genre genre : Genre.values()) {
                System.out.print("\u001B[37m" + genre + "," + "\u001B[0m");
            }
            System.out.println();
            System.out.println("Enter the number of genres of the production");
            int num;
            while (true){
                try {
                    num= scanner.nextInt();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                    scanner.next();
                }
            }
            List<Genre> genres = new ArrayList<>();
            System.out.println("Enter the genres one by one:");
            for (int i = 0; i < num; i++) {
                Scanner scanner11 = new Scanner(System.in);
                String inputGenre = scanner11.nextLine();
                try {
                    Genre genre = Genre.valueOf(inputGenre);
                    genres.add(genre);
                } catch (IllegalArgumentException e) {
                    System.out.println("\u001B[31m"+"Invalid genre."+"\u001B[0m"+" Please enter a valid genre.");
                    i--;  // Decrement i to repeat the current iteration
                }
            }
            System.out.println("Enter the release year of the production");
            int releaseYear;
            while (true){
                try {
                    releaseYear= scanner.nextInt();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                    scanner.next();
                }
            }
            System.out.println("Enter the plot of the production");
            Scanner scanner4 = new Scanner(System.in);
            String plot = scanner4.nextLine();
            System.out.println("Enter the number of actors of the production");
            while (true){
                try {
                    num= scanner.nextInt();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                    scanner.next();
                }
            }
            List<String> actors = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                System.out.println("Enter the name of the actor:");
                Scanner scanner12 = new Scanner(System.in);
                String actor = scanner12.nextLine();
                actors.add(actor);
            }
            System.out.println("Enter the number of directors of the production");
            while (true){
                try {
                    num= scanner.nextInt();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                    scanner.next();
                }
            }
            List<String> directors = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                System.out.println("Enter the name of the director:");
                Scanner scanner12 = new Scanner(System.in);
                String director = scanner12.nextLine();
                directors.add(director);
            }
            System.out.println("Enter name of production cover image (use 27 if no cover image)");
            Scanner scanner14 = new Scanner(System.in);
            String img;
            while (true){
                try {
                    img = scanner14.nextLine();
                    File file = new File("src/main/resources/Images/"+img+".jpg");
                    if(file.exists()){
                        break;
                    }
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a name of an existing image");
                } catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m");
                    scanner14.next();
                }
            }
            String image ="src/main/resources/Images/"+img+".jpg";
            if (type.equals("Series")){
                System.out.println("Please insert the number of seasons of the production:");
                Scanner scanner15 = new Scanner(System.in);
                int nr_seasons;
                while (true){
                    try {
                        nr_seasons= scanner15.nextInt();
                        break;
                    }
                    catch (InputMismatchException e){
                        System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                        scanner.next();
                    }
                }
                Map<String, List<Episode>> seasons = new TreeMap<>();
                for (int i = 0; i < nr_seasons; i++) {
                    System.out.println("Please insert the name of the season:");
                    Scanner scanner16 = new Scanner(System.in);
                    String season = scanner16.nextLine();
                    System.out.println("Please insert the number of episodes of the season:");
                    int nr_episodes;
                    Scanner scanner17 = new Scanner(System.in);
                    while (true){
                        try {
                            nr_episodes = scanner17.nextInt();
                            break;
                        }
                        catch (InputMismatchException e){
                            System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                            scanner.next();
                        }
                    }
                    List<Episode> episodes = new ArrayList<>();
                    for (int j = 0; j < nr_episodes; j++) {
                        System.out.println("Please insert the name of the episode:");
                        Scanner scanner18 = new Scanner(System.in);
                        String episode = scanner18.nextLine();
                        System.out.println("Please insert the duration of the episode:");
                        Scanner scanner19 = new Scanner(System.in);
                        String duration = scanner19.nextLine();
                        episodes.add(new Episode(episode, duration));
                    }
                    seasons.put(season, episodes);
                }
                for(Production production:IMDB.getInstance().getProductions())
                {
                    if(production.getTitle().equals(name)&&production.getTitle().equals("Series"))
                    {
                        Series ser = (Series) production;
                        ser.setDirectors(directors);
                        ser.setActors(actors);
                        ser.setGenres(genres);
                        ser.setPlot(plot);
                        ser.setReleaseYear(releaseYear);
                        ser.setSeasons(seasons);
                        ser.setSeasons_nr(nr_seasons);
                        System.out.println("\u001B[32m"+"Series updated successfully"+"\u001B[0m");
                        return;
                    }
                }
                Series prod = new Series(name, type, directors, actors, genres, new ArrayList<>(), plot, 0.0, releaseYear, nr_seasons, seasons, image);
                this.added.add(prod);
                IMDB.getInstance().getProductions().add(prod);
            }
            if(type.equals("Movie")){
                System.out.println("Enter the duration of the production");
                Scanner scanner5 = new Scanner(System.in);
                String duration = scanner5.nextLine();
                for(Production production:IMDB.getInstance().getProductions())
                {
                    if(production.getTitle().equals(name)&&production.getTitle().equals("Movie"))
                    {
                        Movie mov = (Movie) production;
                        mov.setDirectors(directors);
                        mov.setActors(actors);
                        mov.setGenres(genres);
                        mov.setPlot(plot);
                        mov.setReleaseYear(releaseYear);
                        mov.setDuration(duration);
                        System.out.println("\u001B[32m"+"Movie updated successfully"+"\u001B[0m");
                        return;
                    }
                }
                Movie prod = new Movie(name, type, directors, actors, genres, new ArrayList<>(), plot, 0.0, releaseYear, duration, image);
                this.added.add(prod);
                IMDB.getInstance().getProductions().add(prod);
            }

            System.out.println("\u001B[32m"+"Production added successfully"+"\u001B[0m");
        }
    }
    public void addActorSystem(){
        System.out.println("Enter the name of the actor");
        Scanner scanner1 = new Scanner(System.in);
        String name;
        while (true){
            try {
                int c=0;
                name = scanner1.nextLine();
                if(name.isEmpty())
                    name="No name";
                for(Actor actor : IMDB.getInstance().getActors()){
                    if(actor.getName().equals(name))
                        c++;
                }
                if(c==0)
                    break;
                System.out.println("\u001B[31m"+"Actor already exists"+"\u001B[0m"+", try again using a different name");
            }
            catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a string");
                scanner1.next();
            }
        }
        System.out.println("Enter the biography of the actor");
        Scanner scanner2 = new Scanner(System.in);
        String biography = scanner2.nextLine();
        System.out.println("Enter the number of performances of the actor");
        Scanner scanner = new Scanner(System.in);
        int num;
        while (true){
            try {
                num= scanner.nextInt();
                break;
            }
            catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                scanner.next();
            }
        }
        List<Role> performances = new ArrayList<>();
        for(int i=0; i<num; i++){
            System.out.println("Enter the name of the production");
            Scanner scanner3 = new Scanner(System.in);
            String prod = scanner3.nextLine();
            System.out.println("Enter the type of production");
            String type;
            while (true) {
                type= scanner.next();
                if (type.equals("Movie") || type.equals("Series")) {
                    break;
                } else {
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again entering Movie or Series");
                }
            }
            Role rol = new Role(prod, type);
            performances.add(rol);
        }
        Actor actor = new Actor(name, performances, biography);
        IMDB.getInstance().getActors().add(actor);
        Staff staff = this;
        staff.added.add(actor);
        this.experienceStrategy=addIMBD;
        this.experienceStrategy.addexp();
        System.out.println("\u001B[32m"+"Actor added successfully"+"\u001B[0m");
    }
    public void removeActorSystem() {
        System.out.println("Enter the name of the actor from the list below:");
        if(this.added==null|| this.added.isEmpty()){
            System.out.println("\u001B[31m"+"No actors to remove"+"\u001B[0m");
            return;
        }
        int c=0;
        for(Object obj : this.added)
            if(obj instanceof Actor actor){
                System.out.println("\u001B[37m"+actor.getName()+","+"\u001B[0m");
                c++;}
        if(c==0){
            System.out.println("\u001B[31m"+"No actors to remove"+"\u001B[0m");
            return;
        }
        System.out.println("Enter the name of the actor");
        Scanner scanner1 = new Scanner(System.in);
        String name;
        Actor ex = null;
        while (true){
            try {
                name = scanner1.nextLine();
                for(Object obj : this.added)
                    if(obj instanceof Actor actor)
                        if(actor.getName().equals(name)){
                            ex=actor;
                            break;
                        }
                if(ex!=null)
                    break;
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a name from the list above");
            }
            catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a name from the list above");
                scanner1.next();
            }
        }
        System.out.println("Are you sure you want to remove this actor? (y/n)");
        Scanner scanner2 = new Scanner(System.in);
        String answer;
        System.out.println("\u001B[37m"+ex.displayInfo()+"\u001B[0m");
        while (true){
                try{
                    answer = scanner2.nextLine();
                    if(answer.equals("y") || answer.equals("n")){
                        break;
                    }
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using y for yes or n for no");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using y for yes or n for no");
                    scanner2.next();
                }
            }
        if(answer.equals("n")){
            System.out.println("\u001B[32m"+"Actor not removed"+"\u001B[0m");
            return;
        }
        for(User user1 : IMDB.getInstance().getUsers()){
            if(user1 instanceof Staff staff1){
                for(Request request : staff1.getRequests()){
                    if(request.gettype().equals(RequestTypes.ACTOR_ISSUE)&&request.getActor_name().equals(ex.getName())){
                        staff1.getRequests().remove(request);
                        break;
                    }
                }
            }
            if(user1.getFavorites()!=null)
                user1.getFavorites().remove(ex);
        }
        this.added.remove(ex);
        IMDB.getInstance().getActors().remove(ex);
        System.out.println("\u001B[32m"+"Actor removed successfully"+"\u001B[0m");
    }
    public void addProductionSystem() {
        System.out.println("Enter the name of the production");
        Scanner scanner1 = new Scanner(System.in);
        String name;
        while (true){
            try {
                int c=0;
                name = scanner1.nextLine();
                if(name.isEmpty())
                    name="No name";
                for(Production production : IMDB.getInstance().getProductions()){
                    if(production.getTitle().equals(name))
                        c++;
                }
                if(c==0)
                    break;
                System.out.println("\u001B[31m"+"Production already exists"+"\u001B[0m"+", try again using a different name");
            }
            catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a string");
                scanner1.next();
            }
        }
        System.out.println("Enter the type of production");
        String type;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            type= scanner.next();
            if (type.equals("Movie") || type.equals("Series")) {
                break;
            } else {
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again entering Movie or Series");
            }
        }
        System.out.println("Genres: ");
        for (Genre genre : Genre.values()) {
            System.out.print("\u001B[37m" + genre + "," + "\u001B[0m");
        }
        System.out.println();
        System.out.println("Enter the number of genres of the production");
        int num;
        while (true){
            try {
                num= scanner.nextInt();
                if(num>=0)
                    break;
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a positive number");
            }
            catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                scanner.next();
            }
        }
        List<Genre> genres = new ArrayList<>();
        System.out.println("Enter the genres one by one:");
        for (int i = 0; i < num; i++) {
            Scanner scanner11 = new Scanner(System.in);
            String inputGenre = scanner11.nextLine();
            try {
                Genre genre = Genre.valueOf(inputGenre);
                genres.add(genre);
            } catch (IllegalArgumentException e) {
                System.out.println("\u001B[31m"+"Invalid genre."+"\u001B[0m"+" Please enter a valid genre.");
                i--;
            }
        }
        System.out.println("Enter the release year of the production");
        int releaseYear;
        while (true){
            try {
                releaseYear= scanner.nextInt();
                break;
            }
            catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                scanner.next();
            }
        }
        System.out.println("Enter the plot of the production");
        Scanner scanner4 = new Scanner(System.in);
        String plot = scanner4.nextLine();
        System.out.println("Enter the number of actors of the production");
        while (true){
            try {
                num= scanner.nextInt();
                break;
            }
            catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                scanner.next();
            }
        }
        List<String> actors = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            System.out.println("Enter the name of the actor:");
            Scanner scanner12 = new Scanner(System.in);
            String actor = scanner12.nextLine();
            actors.add(actor);
        }
        System.out.println("Enter the number of directors of the production");
        Scanner scanner13 = new Scanner(System.in);
        while (true){
            try {
                num= scanner13.nextInt();
                break;
            }
            catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                scanner13.next();
            }
        }
        List<String> directors = new ArrayList<>();
        for (int i = 0; i < num; i++) {
            System.out.println("Enter the name of the director:");
            Scanner scanner12 = new Scanner(System.in);
            String director = scanner12.nextLine();
            directors.add(director);
        }
        System.out.println("Enter name of production cover image (use 27 if no cover image)");
        Scanner scanner14 = new Scanner(System.in);
        String img;
        while (true){
            try {
                img = scanner14.nextLine();
                File file = new File("src/main/resources/Images/"+img+".jpg");
                if(file.exists()){
                    break;
                }
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a name of an existing image");
            } catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m");
                scanner14.next();
            }
        }
        String image ="src/main/resources/Images/"+img+".jpg";
        if (type.equals("Series")){
            System.out.println("Please insert the number of seasons of the production:");
            Scanner scanner15 = new Scanner(System.in);
            int nr_seasons;
            while (true){
                try {
                    nr_seasons= scanner15.nextInt();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                    scanner15.next();
                }
            }
            Map<String, List<Episode>> seasons = new TreeMap<>();
            for (int i = 0; i < nr_seasons; i++) {
                System.out.println("Please insert the name of the season:");
                Scanner scanner16 = new Scanner(System.in);
                String season = scanner16.nextLine();
                System.out.println("Please insert the number of episodes of the season:");
                int nr_episodes;
                Scanner scanner17 = new Scanner(System.in);
                while (true){
                    try {
                        nr_episodes = scanner17.nextInt();
                        break;
                    }
                    catch (InputMismatchException e){
                        System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                        scanner17.next();
                    }
                }
                List<Episode> episodes = new ArrayList<>();
                for (int j = 0; j < nr_episodes; j++) {
                    System.out.println("Please insert the name of the episode:");
                    Scanner scanner18 = new Scanner(System.in);
                    String episode = scanner18.nextLine();
                    System.out.println("Please insert the duration of the episode:");
                    Scanner scanner19 = new Scanner(System.in);
                    String duration = scanner19.nextLine();
                    episodes.add(new Episode(episode, duration));
                }
                seasons.put(season, episodes);
            }
            for(Production production:IMDB.getInstance().getProductions())
            {
                if(production.getTitle().equals(name)&&production.getType().equals("Series"))
                {
                    Series ser = (Series) production;
                    ser.setDirectors(directors);
                    ser.setActors(actors);
                    ser.setGenres(genres);
                    ser.setPlot(plot);
                    ser.setReleaseYear(releaseYear);
                    ser.setSeasons(seasons);
                    ser.setSeasons_nr(nr_seasons);
                    System.out.println("\u001B[32m"+"Series updated successfully"+"\u001B[0m");
                    return;
                }
            }
            Series prod = new Series(name, type, directors, actors, genres, new ArrayList<>(), plot, 0.0, releaseYear, nr_seasons, seasons, image);
            prod.setImage(new ImageIcon("src/main/resources/Images/"+image+".jpg"));
            Staff staff = this;
            staff.added.add(prod);
            IMDB.getInstance().getProductions().add(prod);
        }
        if(type.equals("Movie")){
            System.out.println("Enter the duration of the production");
            Scanner scanner5 = new Scanner(System.in);
            String duration = scanner5.nextLine();
            for(Production production:IMDB.getInstance().getProductions())
            {
                if(production.getTitle().equals(name)&&production.getType().equals("Movie"))
                {
                    Movie mov = (Movie) production;
                    mov.setDirectors(directors);
                    mov.setActors(actors);
                    mov.setGenres(genres);
                    mov.setPlot(plot);
                    mov.setReleaseYear(releaseYear);
                    mov.setDuration(duration);
                    System.out.println("\u001B[32m"+"Movie updated successfully"+"\u001B[0m");
                    return;
                }
            }
            Movie prod = new Movie(name, type, directors, actors, genres, new ArrayList<>(), plot, 0.0, releaseYear, duration, image);
            prod.setImage(new ImageIcon("src/main/resources/Images/"+image+".jpg"));
            Staff staff = this;
            staff.added.add(prod);
            this.experienceStrategy=addIMBD;
            this.experienceStrategy.addexp();
            IMDB.getInstance().getProductions().add(prod);
        }

        System.out.println("\u001B[32m"+"Production added successfully"+"\u001B[0m");
    }
    public void removeProductionSystem() {
        System.out.println("Enter the name of the production from the list below:");
        if(this.added==null||this.added.isEmpty()){
            System.out.println("\u001B[31m"+"Nothing added"+"\u001B[0m");
            return;
        }
        int c=0;
        for(Object obj : this.added)
            if(obj instanceof Production production){
                System.out.println("\u001B[37m"+production.getTitle()+","+"\u001B[0m");
                c++;}
        if(c==0){
            System.out.println("\u001B[31m"+"No production added"+"\u001B[0m");
            return;
        }
        System.out.println("Enter the name of the production");
        Scanner scanner1 = new Scanner(System.in);
        String name;
        Production prod=null;
        while (true){
            try {
                name = scanner1.nextLine();
                for(Object obj : this.added)
                    if(obj instanceof Production production)
                        if(production.getTitle().equals(name)){
                            prod=production;
                            break;
                        }
                if(prod!=null)
                    break;
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a name from the list above");
            }
            catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a name from the list above");
                scanner1.next();
            }
        }
        System.out.println("Are you sure you want to remove this production? (y/n)");
        Scanner scanner2 = new Scanner(System.in);
        String answer;
        System.out.println("\u001B[37m"+prod.displayInfo("")+","+"\u001B[0m");
        while (true){
            try{
                answer = scanner2.nextLine();
                if(answer.equals("y") || answer.equals("n")){
                    break;
                }
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using y for yes or n for no");
            }
            catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using y for yes or n for no");
                scanner2.next();
            }
        }
        if(answer.equals("n")) {
            System.out.println("\u001B[32m"+"Production not removed"+"\u001B[0m");
            return;
        }
                for(User user1 : IMDB.getInstance().getUsers()){
                    if(user1 instanceof Staff staff1){
                        for(Request request : staff1.getRequests()){
                            if(request.gettype().equals(RequestTypes.MOVIE_ISSUE)&&request.getMovie_name().equals(prod.getTitle())){
                                staff1.getRequests().remove(request);
                                break;
                            }
                        }
                    }
                    if(user1.getFavorites()!=null)
                        user1.getFavorites().remove(prod);
                }
                this.added.remove(prod);
                IMDB.getInstance().getProductions().remove(prod);
                System.out.println("\u001B[32m"+"Production removed successfully"+"\u001B[0m");
    }
    public void updateActor(){
        System.out.println("Enter the name of the actor from the list below:");
        int c=0;
        if(this.added==null||this.added.isEmpty()){
            System.out.println("\u001B[31m"+"Nothing added"+"\u001B[0m");
            return;
        }
        for (Object obj : this.added)
            if (obj instanceof Actor actor) {
                System.out.println("\u001B[37m" + actor.getName() + "," + "\u001B[0m");
                c++;
            }
        if(c==0){
            System.out.println("\u001B[31m"+"No actor added"+"\u001B[0m");
            return;
        }
        System.out.println("Enter the name of the actor");
        Scanner scanner1 = new Scanner(System.in);
        String name;
        Actor actor=null;
        while (true){
            try{
                name= scanner1.nextLine();
                if(name.isEmpty())
                    throw new NullPointerException();
                for (Object object : this.added)
                    if (object instanceof Actor actor1)
                        if (actor1.getName().equals(name))
                            actor=actor1;
                if(actor!=null)
                    break;
                System.out.println("\u001B[31m"+"Actor not found in favorites list"+"\u001B[0m");
            }
            catch (NullPointerException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a valid name");
            }
        }
        System.out.println("\u001B[37m"+actor.displayInfo()+","+"\u001B[0m");
        System.out.println("Are you sure that you want to update this actor? (y/n)");
        Scanner scanner2 = new Scanner(System.in);
        String answer;
        while (true){
            try{
                answer = scanner2.nextLine();
                if(answer.equals("y") || answer.equals("n")){
                    break;
                }
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using y for yes or n for no");
            }
            catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using y for yes or n for no");
                scanner2.next();
            }
        }
        if(answer.equals("y")){
            System.out.println("Previous name was: "+actor.getName());
            System.out.print("Enter the new name of the actor:");
            Scanner scanner3 = new Scanner(System.in);
            String newName = scanner3.nextLine();
            actor.setName(newName);
            System.out.println("Previous biography was: "+actor.getBiography());
            System.out.print("Enter the new biography of the actor:");
            Scanner scanner4 = new Scanner(System.in);
            String newBiography = scanner4.nextLine();
            actor.setBiography(newBiography);
            int ceva=1;
            while (ceva==1)
            {
                System.out.print("Previous performances were: ");
                for (Role role : actor.getPerformances()) {
                    System.out.print("\u001B[37m" + role.displayInfo() + "," + "\u001B[0m");
                }
                System.out.println();
                System.out.println("Enter the number of the performance that you want to update, select 0 if you want to add a new performance");
                int num;
                Scanner scanner5 = new Scanner(System.in);
                while (true) {
                    try {
                        num = scanner5.nextInt();
                        if (num <= actor.getPerformances().size()&&num>=0)
                            break;
                        System.out.println("\u001B[31m" + "Invalid input" + "\u001B[0m" + ", try again using a number between 0 and " + actor.getPerformances().size());
                    } catch (InputMismatchException e) {
                        System.out.println("\u001B[31m" + "Invalid input" + "\u001B[0m" + ", try again using a number between 0 and " + actor.getPerformances().size());
                        scanner5.next();
                    }
                }

                System.out.println("Enter the name of the production");
                Scanner scanner6 = new Scanner(System.in);
                String prod = scanner6.nextLine();
                System.out.println("Enter the type of production");
                String type;
                Scanner scanner7 = new Scanner(System.in);
                while (true) {
                    type = scanner7.next();
                    if (type.equals("Movie") || type.equals("Series")) {
                        break;
                    } else {
                        System.out.println("\u001B[31m" + "Invalid input" + "\u001B[0m" + ", try again entering Movie or Series");
                        scanner7.next();
                    }
                }
                Role rol = new Role(prod, type);
                if (num == 0)
                    actor.getPerformances().add(rol);
                else {
                    actor.getPerformances().remove(num - 1);
                    actor.getPerformances().add(num - 1, rol);
                }
                System.out.println("Do you want to update performances again? (y/n)");
                Scanner scanner8 = new Scanner(System.in);
                String answer1;
                while (true) {
                    try {
                        answer1 = scanner8.nextLine();
                        if (answer1.equals("y") || answer1.equals("n")) {
                            break;
                        }
                        System.out.println("\u001B[31m" + "Invalid input" + "\u001B[0m" + ", try again using y for yes or n for no");
                    } catch (InputMismatchException e) {
                        System.out.println("\u001B[31m" + "Invalid input" + "\u001B[0m" + ", try again using y for yes or n for no");
                        scanner8.next();
                    }
                }
                if (answer1.equals("n"))
                    ceva=0;
            }
            System.out.println("\u001B[32m"+"Actor updated successfully"+"\u001B[0m");
        }
        else{
            System.out.println("\u001B[32m"+"Actor not updated"+"\u001B[0m");
        }
    }
    public void updateProduction(){
        System.out.println("Enter the name of the production from the list below:");
        int c=0;
        if(this.added==null||this.added.isEmpty()){
            System.out.println("\u001B[31m"+"Nothing added"+"\u001B[0m");
            return;
        }
        for(Object obj : this.added)
            if(obj instanceof Production production){
                System.out.println("\u001B[37m"+production.getTitle()+","+"\u001B[0m");
                c++;}
        if(c==0){
            System.out.println("\u001B[31m"+"No production added"+"\u001B[0m");
            return;
        }
        System.out.println("Enter the name of the production");
        Scanner scanner1 = new Scanner(System.in);
        String name;
        Production production=null;
        while (true){
            try{
                name= scanner1.nextLine();
                if(name.isEmpty())
                    throw new NullPointerException();
                for(Object object : this.added)
                    if(object instanceof Production prod)
                        if(name.equals(prod.getTitle()))
                            production=prod;
                if(production!=null)
                    break;
                System.out.println("\u001B[31m"+"Production not added by you"+"\u001B[0m");
            }
            catch (NullPointerException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a non-empty string");
            }
        }
        System.out.println("\u001B[37m"+production.displayInfo("")+","+"\u001B[0m");
        System.out.println("Are you sure that you want to update this production? (y/n)");
        Scanner scanner2 = new Scanner(System.in);
        String answer;
        while (true){
            try{
                answer = scanner2.nextLine();
                if(answer.equals("y") || answer.equals("n")){
                    break;
                }
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using y for yes or n for no");
            }
            catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using y for yes or n for no");
                scanner2.next();
            }
        }
        if(answer.equals("y")){
            System.out.println("Previous name was: "+production.getTitle());
            System.out.print("Enter the new name of the production:");
            Scanner scanner3 = new Scanner(System.in);
            String newName = scanner3.nextLine();
            production.setTitle(newName);
            System.out.println("Previous directors were: ");
            System.out.println("\u001B[37m"+production.getDirectors()+","+"\u001B[0m");
            production.getDirectors().clear();
            System.out.print("Enter the new number of directors:");
            Scanner scanner4 = new Scanner(System.in);
            int num;
            while (true){
                try {
                    num= scanner4.nextInt();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                    scanner4.next();
                }
            }
            for(int i=0; i<num; i++){
                System.out.println("Enter the name of the director:");
                Scanner scanner5 = new Scanner(System.in);
                String director = scanner5.nextLine();
                production.getDirectors().add(director);
            }
            System.out.println("Previous actors were: ");
            System.out.println("\u001B[37m"+production.getActors()+","+"\u001B[0m");
            production.getActors().clear();
            System.out.print("Enter the new number of actors:");
            Scanner scanner6 = new Scanner(System.in);
            while (true){
                try {
                    num= scanner6.nextInt();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                    scanner6.next();
                }
            }
            for(int i=0; i<num; i++){
                System.out.println("Enter the name of the actor:");
                Scanner scanner7 = new Scanner(System.in);
                String actor = scanner7.nextLine();
                production.getActors().add(actor);
            }
            System.out.println("Previous genres were: ");
            System.out.println("\u001B[37m"+production.getGenres()+","+"\u001B[0m");
            production.getGenres().clear();
            System.out.print("Enter the new number of genres:");
            Scanner scanner8 = new Scanner(System.in);
            while (true){
                try {
                    num= scanner8.nextInt();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                    scanner8.next();
                }
            }
            for(int i=0; i<num; i++){
                System.out.println("Enter the genre:");
                Scanner scanner9 = new Scanner(System.in);
                String genre = scanner9.nextLine();
                try {
                    Genre genre1 = Genre.valueOf(genre);
                    production.getGenres().add(genre1);
                }
                catch (IllegalArgumentException e){
                    System.out.println("\u001B[31m"+"Invalid genre."+"\u001B[0m"+" Please enter a valid genre.");
                    i--;  // Decrement i to repeat the current iteration
                }
            }
            System.out.println("Previous release year was: "+production.getReleaseYear());
            System.out.print("Enter the new release year:");
            Scanner scanner10 = new Scanner(System.in);
            while (true){
                try {
                    num= scanner10.nextInt();
                    break;
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                    scanner10.next();
                }
            }
            production.setReleaseYear(num);
            if(production.getType().equals("Movie")){
                ((Movie) production).update_duration();
            }
            if(production.getType().equals("Series")){
                ((Series) production).add_sez();
            }
            System.out.println("\u001B[32m"+"Production updated successfully"+"\u001B[0m");
        }
        else{
            System.out.println("\u001B[32m"+"Production not updated"+"\u001B[0m");
        }
    }

    public String displayInfo() {
        StringBuilder display = new StringBuilder();
        display.append(this.getAccountType()).append(": ").append("\u001B[1m").append(this.getUsername()).append("\u001B[0m").append("\n");
        display.append(this.getInfo().displayInfo());
        if(this.getAccountType().equals(AccountType.CONTRIBUTOR)){
            this.calculateExperience();
            display.append("Experience: ").append(this.getExperience()).append("\n");
        }
//        display.append("Notifications: ").append("\n");
//        if(this.notifications==null)
//            display.append("   ").append("No notifications").append("\n");
//        else
//            for(String notification : this.notifications){
//                display.append("   ").append(notification).append("\n");
//            }
//        display.append("Private Requests: ").append("\n");
//        if(this.requests==null){
//            display.append("   ").append("No requests").append("\n");}
//        if(this.requests!=null){
//            for(Request request : this.requests){
//                display.append(request.displayInfo("    ")).append("\n");
//            }
//        }
//        display.append("All Requests: ").append("\n");
//        if(RequestHolder.getRequests()!=null)
//        {
//            for(Request request : RequestHolder.getRequests()){
//                display.append(request.displayInfo("    ")).append("\n");
//            }
//        }
//        if(RequestHolder.getRequests()==null)
//            display.append("   ").append("No requests").append("\n");
        display.append("Favorites: ").append("\n");
        if(this.getFavorites().isEmpty())
            display.append("   ").append("No favorites").append("\n");
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
        display.append("Added: ").append("\n");
        if(this.added.isEmpty())
            display.append("   ").append("No addition").append("\n");
        else {
            StringBuilder act = new StringBuilder();
            StringBuilder ser = new StringBuilder();
            StringBuilder mov = new StringBuilder();
            for(Object favorite : this.added) {
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
    public Staff(Information info, AccountType accountType, String username, int experience, List<String> notifications, SortedSet<Object> favorites, List<Request> requests, SortedSet<Object> added) {
        super(info, accountType, username, experience, notifications, favorites);
        this.requests = requests;
        this.added = added;
    }

}
