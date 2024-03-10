package org.example.Terminal;

import org.example.Classes.*;
import org.example.Enums.*;
import org.example.Exceptions.*;


import java.io.File;
import java.io.FileWriter;
import java.util.*;

public class Terminal {
    int l=0;
    public void Login(IMDB imdb){
        System.out.println("Please Insert your credentials:");
        while(true) {
            for(User user : imdb.getUsers()){
                System.out.println(user.getAccountType()+"\n"+user.getInfo().getCredentials().displayInfo());
            }
            System.out.print("email:");
            Scanner scanner = new Scanner(System.in);
            String email = scanner.next();
            System.out.print("password:");
            String password = scanner.next();

            for(User user : imdb.getUsers()){
                if(user.getInfo().getCredentials().getEmail().equals(email)&&user.getInfo().getCredentials().getPassword().equals(password)){
                    menu(user);
                }
            }
            System.out.println("\033[31m"+"Wrong email or password!\nPlease Insert your credentials again:"+"\033[0m");
        }
    }

    private void menu(User user) {
        l++;
        if(l==1){
        System.out.println("\033[32m"+"Log in successful!"+"\033[0m");
        System.out.println("Welcome back, "+"\033[1m"+user.getInfo().getName()+"\033[0m"+"!");}
        System.out.println("============MENU============");
        System.out.println("Please make a selection:");
        System.out.println(" 1.) View production details");
        System.out.println(" 2.) View actors details");
        System.out.println(" 3.) View notifications");
        System.out.println(" 4.) Search for actor/production");
        System.out.println(" 5.) Add/Delete actor/production to/from favorites");
        System.out.println(" 6.) Search for a user");
        int k=6;
        if(user.getAccountType()== AccountType.ADMIN){
            System.out.println(" "+(++k)+".) Add/Delete actor/production to/from IMDB");
            System.out.println(" "+(++k)+".) View and solve requests");
            System.out.println(" "+(++k)+".) Update actor/production");
            System.out.println((++k)+".) Add/Delete a user to/from IMDB");
        }
        if(user.getAccountType()== AccountType.REGULAR){
            System.out.println(" "+(++k)+".) Add/Delete a request");
            System.out.println(" "+(++k)+".) Add/Delete a review");
        }
        if(user.getAccountType()== AccountType.CONTRIBUTOR){
            System.out.println(" "+(++k)+".) Add/Delete a request");
            System.out.println(" "+(++k)+".) Add/Delete actor/production to/from IMDB");
            System.out.println(" "+(++k)+".) View and solve requests");
            System.out.println((++k)+".) Update actor/production");
        }
        if(k<10)
            System.out.print(" ");
        System.out.println((++k)+".) Log out");
        if(k<10)
            System.out.print(" ");
        System.out.println((++k)+".) Exit");

        Scanner scanner = new Scanner(System.in);
        int selection;
        while (true){
            try {
                selection = scanner.nextInt();
                if(selection>=0&&selection<=k)
                    break;
                throw new InvalidCommandException("\u001B[31m"+"Invalid input! "+"\u001B[0m"+"Try a number between 1 and "+k);
            }
            catch (InputMismatchException e){
                System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using a number");
                scanner.next();
            } catch (InvalidCommandException e) {
                System.out.println(e.getMessage());
            }
        }
        int all_selections=k;
        if(selection==1){
            IMDB.getInstance().getProductions().sort(Comparator.comparing(Production::getTitle));
            System.out.println("Do you want any sorting? (y/n)");
            Scanner scanner1 = new Scanner(System.in);
            String answer;
            while (true){
                try{
                    answer = scanner1.nextLine();
                    if(answer.equals("y")||answer.equals("n"))
                        break;
                    throw  new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using y for yes or n for no:");
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            }
            if(answer.equals("y")){
                System.out.println("Sort after genre(1), number of ratings(2), highest rating(3), release year(4), number of actors or number of directors(5), or number of actors(6)?");
                Scanner scanner2 = new Scanner(System.in);
                int answer2;
                while (true){
                    try{
                        answer2 = scanner2.nextInt();
                        if(answer2>=1&&answer2<=6)
                            break;
                        throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using genre(1), number of ratings(2), highest rating(3), release year(4), number of actors or number of directors(5) or number of actors(6):");
                    }
                    catch (InputMismatchException e){
                        System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using genre(1), number of ratings(2), highest rating(3), release year(4), number of actors or number of directors(5) or number of actors(6):");
                        scanner2.next();
                    }
                    catch (InvalidCommandException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if(answer2==1){
                    for(Genre genre : Genre.values()){
                        System.out.println(genre+":");
                        for(Production prod : IMDB.getInstance().getProductions()){
                            for(Genre genre1 : prod.getGenres()){
                                if(genre1==genre)
                                    System.out.println(prod.displayInfo("   "));
                            }
                        }
                    }
                }
                if(answer2==2){
                    IMDB.getInstance().getProductions().sort(Comparator.comparingInt(Production::getRatingSize));
                    for(Production prod : IMDB.getInstance().getProductions()){
                        System.out.println(prod.displayInfo(""));
                    }
                }
                if(answer2==3){
                    IMDB.getInstance().getProductions().sort(Comparator.comparingDouble(Production::getAverageRating));
                    for(Production prod : IMDB.getInstance().getProductions()){
                        System.out.println(prod.displayInfo(""));
                    }
                }
                if(answer2==4){
                    IMDB.getInstance().getProductions().sort(Comparator.comparingInt(Production::getReleaseYear));
                    for(Production prod : IMDB.getInstance().getProductions()){
                        System.out.println(prod.displayInfo(""));
                    }
                }

                if(answer2==5){
                    IMDB.getInstance().getProductions().sort(Comparator.comparingInt(Production::getNumberOfDirectors));
                    for(Production prod : IMDB.getInstance().getProductions()){
                        System.out.println(prod.displayInfo(""));
                    }
                }
                if(answer2==6){
                    IMDB.getInstance().getProductions().sort(Comparator.comparingInt(Production::getNumberOfActors));
                    for(Production prod : IMDB.getInstance().getProductions()){
                        System.out.println(prod.displayInfo(""));
                    }
                }
            }
            else
                for(Production prod : IMDB.getInstance().getProductions())
                    System.out.println(prod.displayInfo(""));
            menu(user);
        } //view production details
        if(selection==2){
            IMDB.getInstance().getActors().sort(Comparator.comparing(Actor::getName));
            System.out.println("Do you want any sorting? (y/n)");
            Scanner scanner1 = new Scanner(System.in);
            String answer;
            while (true){
                try{
                    answer = scanner1.nextLine();
                    if(answer.equals("y")||answer.equals("n"))
                        break;
                    throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using y for yes or n for no:");
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            }
            if(answer.equals("n"))
                for(Actor actor : IMDB.getInstance().getActors())
                    System.out.println(actor.displayInfo());
            if(answer.equals("y")){
                System.out.println("Sort after name(1), number of ratings(2), number of performances(3) or highest rating(4)?");
                Scanner scanner2 = new Scanner(System.in);
                int answer2;
                while (true){
                    try{
                        answer2 = scanner2.nextInt();
                        if(answer2==1||answer2==2||answer2==3||answer2==4)
                            break;
                        throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using name(1), number of ratings(2), number of performances(3) or highest rating(4):");
                    }
                    catch (InputMismatchException e){
                        System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using name(1), number of ratings(2), number of performances(3) or highest rating(4):");
                        scanner2.next();
                    }
                    catch (InvalidCommandException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if(answer2==1){
                    IMDB.getInstance().getActors().sort(Comparator.comparing(Actor::getName));
                    for(Actor actor : IMDB.getInstance().getActors())
                        System.out.println(actor.displayInfo());
                }
                if(answer2==2){
                    IMDB.getInstance().getActors().sort(Comparator.comparingInt(Actor::getNumberOfRatings));
                    for(Actor actor : IMDB.getInstance().getActors())
                        System.out.println(actor.displayInfo());
                }
                if(answer2==3){
                    IMDB.getInstance().getActors().sort(Comparator.comparingInt(Actor::getNumberOfPerformances));
                    for(Actor actor : IMDB.getInstance().getActors())
                        System.out.println(actor.displayInfo());
                }
                if(answer2==4){
                    IMDB.getInstance().getActors().sort(Comparator.comparingDouble(Actor::getRating));
                    for(Actor actor : IMDB.getInstance().getActors())
                        System.out.println(actor.displayInfo());
                }
            }
            menu(user);
        } //view actors details
        if(selection==3){
            System.out.println("Notifications:");
            if(user.getNotifications()==null){
                System.out.println("No notification");}
            else{
                for(String notification : user.getNotifications()){
                    System.out.println(notification);
                }}
            System.out.println("Do you want to delete all notifications? (y/n)");
            Scanner scanner1 = new Scanner(System.in);
            String answer;
            while (true){
                try{
                    answer = scanner1.nextLine();
                    if(answer.equals("y")||answer.equals("n"))
                        break;
                    throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using y for yes or n for no:");
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            }
            if(answer.equals("y")){
                user.getNotifications().clear();
                System.out.println("\u001B[32m"+"Notifications deleted!"+"\u001B[0m");
            }
            menu(user);
        } //view notifications
        if(selection==4){
            System.out.println("Please insert 1 for actor and 2 for production:");
            Scanner scanner1 = new Scanner(System.in);
            int type;
            while (true){
                try {
                    type = scanner1.nextInt();
                    if(type==1||type==2)
                        break;
                    throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for actor or 2 for production:");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for actor or 2 for production:");
                    scanner1.next();
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            }
            if(type==1){
                System.out.println("Please insert the name of the actor:");
                Scanner scanner2 = new Scanner(System.in);
                String name = scanner2.nextLine();
                if(name!=null&&!name.isEmpty())
                    for(Actor actor : IMDB.getInstance().getActors()){
                        if(actor.getName().equals(name)){
                            System.out.println(actor.displayInfo());
                            menu(user);
                        }
                    }
                System.out.println("\u001B[31m"+"Actor not found"+"\u001B[0m");
                menu(user);
            }
            if(type==2){
                System.out.println("Please insert the name of the production:");
                Scanner scanner2 = new Scanner(System.in);
                String name = scanner2.nextLine();
                for(Production production : IMDB.getInstance().getProductions()){
                    if(production.getTitle().equals(name)){
                        System.out.println(production.displayInfo(""));
                        menu(user);
                    }
                }
                System.out.println("\u001B[31m"+"Production not found"+"\u001B[0m");
                menu(user);
            }
            menu(user);
        } //search for actor/production
        if(selection==6){
            user.SearchUser();
            menu(user);
        } //search for user
        if(selection==5){
            System.out.println("Please insert 1 for add and 2 for remove:");
            Scanner scanner1 = new Scanner(System.in);
            int type;
            while (true){
                try {
                    type = scanner1.nextInt();
                    if(type==1||type==2)
                        break;
                    throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for add or 2 for remove:");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for add or 2 for remove:");
                    scanner1.next();
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            }
            if(type==1) {
                user.AddFav();
                menu(user);
            }
            if(type==2) {
                user.RemoveFav();
                menu(user);
            }
        } //add/delete actor/production to/from favorites
        if(selection==8&&user.getAccountType()==AccountType.REGULAR){
            System.out.println("Please insert 1 for add and 2 for delete:");
            Scanner scanner2 = new Scanner(System.in);
            int selection2;
            while (true){
                try {
                    selection2 = scanner2.nextInt();
                    if(selection2==1||selection2==2)
                        break;
                    throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for add or 2 for remove:");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for add or 2 for remove:");
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            }
            if(selection2==1){
                Regular regular = (Regular) user;
                regular.addRating();
                menu(user);
            }
            if(selection2==2){
                Regular regular = (Regular) user;
                regular.deleteRating();
                menu(user);
            }
        } //add/delete a rating
        if((selection==7&&user.getAccountType()==AccountType.ADMIN)||(selection==8&&user.getAccountType()==AccountType.CONTRIBUTOR)) {
            System.out.println("Please insert 1 for add and 2 for remove:");
            Scanner scanner1 = new Scanner(System.in);
            int selection2;
            while (true) {
                try {
                    selection2 = scanner1.nextInt();
                    if (selection2 == 1 || selection2 == 2)
                        break;
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for add or 2 for remove:");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for add or 2 for remove:");
                    scanner1.next();
                }
            }
            if(selection2==1){
                Staff staff = (Staff) user;
                System.out.println("Select 1 to add an actor, 2 to add a production");
                Scanner scanner5 = new Scanner(System.in);
                int choice;
                while (true){
                    try {
                        choice = scanner5.nextInt();
                        if(choice==1 || choice==2){
                            break;
                        }
                        throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for actor or 2 for production");
                    }
                    catch (InputMismatchException e){
                        System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for actor or 2 for production");
                        scanner5.next();
                    }
                    catch (InvalidCommandException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if(choice==1)
                    staff.addActorSystem();
                if(choice==2)
                    staff.addProductionSystem();
                menu(user);
            }
            if(selection2==2){
                Staff staff = (Staff) user;
                System.out.println("Select 1 to remove an actor, 2 to remove a production");
                Scanner scanner5 = new Scanner(System.in);
                int choice;
                while (true){
                    try {
                        choice = scanner5.nextInt();
                        if(choice==1 || choice==2){
                            break;
                        }
                        throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for actor or 2 for production");
                    }
                    catch (InputMismatchException e){
                        System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for actor or 2 for production");
                        scanner5.next();
                    }
                    catch (InvalidCommandException e) {
                        System.out.println(e.getMessage());
                    }
                }
                if(choice==1)
                    staff.removeActorSystem();
                if(choice==2)
                    staff.removeProductionSystem();
                menu(user);
            }

        }//add/delete actor/production to/from IMDB
        if((selection==9&&user.getAccountType()==AccountType.ADMIN)||(selection==10&&user.getAccountType()==AccountType.CONTRIBUTOR)){
            System.out.println("Please insert 1 for actor and 2 for production:");
            Scanner scanner1 = new Scanner(System.in);
            int type;
            while (true){
                try {
                    type = scanner1.nextInt();
                    if(type==1||type==2)
                        break;
                    throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for actor or 2 for production:");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for actor or 2 for production:");
                    scanner1.next();
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (type == 1) {
                Staff staff = (Staff) user;
                staff.updateActor();
                menu(user);
            }
            if (type == 2) {
                Staff staff = (Staff) user;
                staff.updateProduction();
                menu(user);
            }
        } //update actor/production
        if(selection==7&&user.getAccountType()==AccountType.REGULAR){
            System.out.println("Please insert 1 for add and 2 for delete:");
            Scanner scanner2 = new Scanner(System.in);
            while (true){
                try {
                    selection = scanner2.nextInt();
                    if(selection==1||selection==2)
                        break;
                    throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for add or 2 for delete:");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for add or 2 for delete:");
                    scanner2.next();
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            }
            if(selection==1){
                Regular regular = (Regular) user;
                regular.createRequest();
                menu(user);
            }
            if(selection==2){
                Regular regular = (Regular) user;
                regular.removeRequest();
                menu(user);
            }
        } //add/delete a request
        if(selection==7&&user.getAccountType()==AccountType.CONTRIBUTOR){
            System.out.println("Please insert 1 for add and 2 for delete:");
            Scanner scanner2 = new Scanner(System.in);
            while (true){
                try {
                    selection = scanner2.nextInt();
                    if(selection==1||selection==2)
                        break;
                    throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for add or 2 for delete:");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for add or 2 for delete:");
                    scanner2.next();
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            }
            if(selection==1){

                Contributor contributor = (Contributor) user;
                contributor.createRequest();
                menu(user);
            }
            if(selection==2){
                Contributor contributor = (Contributor) user;
                contributor.removeRequest();
                menu(user);
            }
        } //add/delete a request
        if(selection==10&&user.getAccountType()==AccountType.ADMIN){
            System.out.println("Please insert 1 for add and 2 for delete:");
            Scanner scanner2 = new Scanner(System.in);
            int selection2;
            while (true){
                try {
                    selection2 = scanner2.nextInt();
                    if(selection2==1||selection2==2)
                        break;
                    throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for add or 2 for delete:");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for add or 2 for delete:");
                    scanner2.next();
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            }
            if(selection2==1){
                Admin admin = (Admin) user;
                admin.AddUser();
                menu(user);
            }
            if(selection2==2){
                Admin admin = (Admin) user;
                admin.DeleteUser();
                menu(user);
            }
        } //add/delete a user to/from IMDB
        if((selection==8&&user.getAccountType()==AccountType.ADMIN)||(selection==9&&user.getAccountType()==AccountType.CONTRIBUTOR)){

            System.out.println("Requests for all staff members:");
            System.out.println("\u001B[37m"+"0.) Exit"+"\u001B[0m");
            int i=1;
            for(Request request : Admin.RequestHolder.getRequests()){
                System.out.println("\u001B[37m"+(i++)+".) "+request.displayInfo("")+"\u001B[0m");
            }
            System.out.println("Requests for you:");
            Staff staff = (Staff) user;
            if(staff.getRequests()==null||staff.getRequests().isEmpty())
                System.out.println("\u001B[37m"+"No requests"+"\u001B[0m");
            else
                for(Request request : staff.getRequests()){
                    System.out.println((i++)+".) "+request.displayInfo(""));
                }
            System.out.println("Please insert the number of the request you want to solve:");
            Scanner scanner5 = new Scanner(System.in);
            int index;
            i--;
            while(true){
                try {
                    index=scanner5.nextInt();
                    if(index>=0&&index<=i)
                        break;
                    throw new InvalidCommandException("\u001B[31m" + "Invalid input! Try a number between 1 and "+i+ "\u001B[0m");
                } catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                } catch (InputMismatchException e){
                    System.out.println("\u001B[31m" + "Invalid input! Try a number between 1 and "+i+ "\u001B[0m");
                    return;
                }
            }
            if(index==0){
                System.out.println("\033[32m"+"Exit successful!"+"\033[0m");
                menu(user);
            }
            Request request;
            if(index<=Admin.RequestHolder.getRequests().size()){
                request = Admin.RequestHolder.getRequests().get(index - 1);
            }
            else
            {
                request = staff.getRequests().get(index - Admin.RequestHolder.getRequests().size() - 1);
            }
            System.out.println("Request selected: "+request.displayInfo(""));
            System.out.println("Please insert 1 for accept, 2 for reject and 0 for exit:");
            Scanner scanner6 = new Scanner(System.in);
            int selection2;
            while (true){
                try {
                    selection2 = scanner6.nextInt();
                    if(selection2==1||selection2==2||selection2==0)
                        break;
                    throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for accept, 2 for reject or 0 for exit");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for accept, 2 for reject or 0 for exit");
                    scanner6.next();
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            }
            if(index<=Admin.RequestHolder.getRequests().size()){
                request = Admin.RequestHolder.getRequests().get(index-1);
                if(selection2==1){
                    staff.SolveRequest(request);
                    request.removeObserver(user);
                    request.notifyObserver("Request solved by "+user.getUsername());
                    Admin.RequestHolder.removeReq(request);
                    IMDB.getInstance().getRequests().remove(request);
                    System.out.println("\u001B[32m"+"Request solved!"+"\u001B[0m");
                    menu(user);
                }
                if(selection2==2){
                    System.out.println("Rejected");
                    request.removeObserver(user);
                    request.notifyObserver("Request rejected by "+user.getUsername());
                    Admin.RequestHolder.removeReq(request);
                    IMDB.getInstance().getRequests().remove(request);
                    menu(user);
                }
            }
            else{
                request = staff.getRequests().get(index-Admin.RequestHolder.getRequests().size()-1);
                if(selection2==1){
                    staff.SolveRequest(request);
                    request.removeObserver(user);
                    request.notifyObserver("Request solved by "+user.getUsername());
                    staff.getRequests().remove(request);
                    IMDB.getInstance().getRequests().remove(request);
                    for(User user1 : IMDB.getInstance().getUsers()){
                        if(user1.getAccountType()!=AccountType.ADMIN&&request.getUsername().equals(user1.getUsername())){
                            user1.experienceStrategy=user1.solvedRequest;
                            user1.experienceStrategy.addexp();
                        }
                    }
                    System.out.println("\u001B[32m"+"Request solved!"+"\u001B[0m");
                    menu(user);
                }
                if(selection2==2){
                    System.out.println("Respinge");
                    request.removeObserver(user);
                    request.notifyObserver("Request rejected by "+user.getUsername());
                    staff.getRequests().remove(request);
                    IMDB.getInstance().getRequests().remove(request);
                    menu(user);
                }
            }
            if(selection2==0){
                System.out.println("\033[32m"+"Exit successful!"+"\033[0m");
                menu(user);
            }
        } //view and solve requests
        if(selection==all_selections-1){
            user.logout(IMDB.getInstance());
        } //logout
        if(selection==all_selections){
            System.out.println("Where do you want to save the data?");
            System.out.println("1.) In the default location");
            System.out.println("2.) In a custom location");
            Scanner scanner1 = new Scanner(System.in);
            int selection2;
            while (true){
                try {
                    selection2 = scanner1.nextInt();
                    if(selection2==1||selection2==2)
                        break;
                    throw new InvalidCommandException("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for default or 2 for custom:");
                }
                catch (InputMismatchException e){
                    System.out.println("\u001B[31m"+"Invalid input"+"\u001B[0m"+", try again using 1 for default or 2 for custom:");
                    scanner1.next();
                }
                catch (InvalidCommandException e) {
                    System.out.println(e.getMessage());
                }
            }
            if(selection2==1){
                IMDB.getInstance().save_data("src/main/resources/output/");
                System.out.println("\u001B[32m"+"Data saved!"+"\u001B[0m");
                System.exit(0);
            }
            if(selection2==2){
                System.out.println("Please insert the path:");
                System.out.println("Default one: src/main/resources/output/");
                Scanner scanner2 = new Scanner(System.in);
                String path = scanner2.nextLine();
                try {
                    File file = new File(path);
                    FileWriter fileWriter = new FileWriter(file+"new_actors.json");
                    IMDB.getInstance().save_data(path);
                    System.out.println("\u001B[32m"+"Data saved!"+"\u001B[0m");
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } //exit
        if(selection==0){
            for(User user1 : IMDB.getInstance().getUsers()){
                System.out.println(user1.displayInfo());
            }
            System.out.println("Requests:");
            for(Request request : Admin.RequestHolder.getRequests()){
                System.out.println(request.displayInfo(""));
            }
            for(Request request : IMDB.getInstance().getRequests()){
                System.out.println(request.displayInfo(""));
            }

            menu(user);
        }
    }
}
