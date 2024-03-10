package org.example.Classes;

import org.example.Enums.AccountType;

import java.util.List;
import java.util.SortedSet;

public class UserFactory {

    public static User createUser(AccountType accountType, User.Information infoBuilder, String username, int experience,
                                  List<String> notifications, SortedSet<Object> favorites, List<Request> requests, SortedSet<Object> added) {
        return switch (accountType) {
            case CONTRIBUTOR ->
                    new Contributor(infoBuilder, username, experience, notifications, favorites, requests, added);
            case REGULAR ->
                    new Regular(infoBuilder, username, experience, notifications, favorites);
            case ADMIN ->
                    new Admin(infoBuilder, username, experience, notifications, favorites, requests, added);
        };
    }
}
