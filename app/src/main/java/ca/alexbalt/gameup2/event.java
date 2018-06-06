package ca.alexbalt.gameup2;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class event {

    public String title;
    public String console;
    public String game;
    public String date;
    public String body;
    public String id;
    public String creator;
    ArrayList<String> joinedList = new ArrayList<>();


    private event() {}


    public event(String title, String console, String game, String date, String body, String id,
                 String creator, ArrayList<String> joinedList)
    {
        this.title = title;
        this.console = console;
        this.game = game;

        this.date = date;
        this.body = body;
        this.id = id;
        this.creator = creator;
        this.joinedList = joinedList;
    }


    public String getTitle() {
        return title;
    }

    public String getConsole() {
        return console;
    }

    public String getGame() {
        return game;
    }

    public String getDate() {
        return date;
    }

    public String getBody() {
        return body;
    }

    public String getId() {return id;}

    public String getCreator() {return creator;}

    public ArrayList<String> getJoinedList() {
        return joinedList;
    }
}
