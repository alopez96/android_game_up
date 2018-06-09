package ca.alexbalt.gameup2;

import java.util.ArrayList;

public class User {
    public String userName;
    public String userEmail;
    public String bio;
    public String favGames;
    public String uid;
    public String key;
    public ArrayList<String> friends = new ArrayList<>();
    public ArrayList<String> eventsJoined = new ArrayList<>();
    public ArrayList<String> eventsCreated = new ArrayList<>();

    private User() {}

    public User(String userName, String userEmail, String bio, String favGames,
                ArrayList<String> friends, ArrayList<String> eventsJoined,
                ArrayList<String> eventsCreated, String uid, String key) {
        this.userName = userName;
        this.userEmail = userEmail;
        this.bio = bio;
        this.favGames = favGames;
        this.friends = friends;
        this.eventsJoined = eventsJoined;
        this.eventsCreated = eventsCreated;
        this.uid = uid;
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getBio() {
        return bio;
    }

    public String getFavGames() {
        return favGames;
    }

    public ArrayList<String> getFriends() {
        return friends;
    }

    public ArrayList<String> getEventsJoined() {
        return eventsJoined;
    }

    public String getUid() {
        return uid;
    }

    public String getKey() {
        return key;
    }

    public ArrayList<String> getEventsCreated() {
        return eventsCreated;
    }
}
