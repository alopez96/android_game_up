package ca.alexbalt.gameup2;

public class event {

    private String eventName;
    private String date;
    private String description;

    private event() {}

    public event(String eventName, String date, String description) {
        this.eventName = eventName;
        this.date = date;
        this.description = description;
    }

    public String getEventName() {
        return eventName;
    }

    public String getDate() {
        return date;
    }

    public String getDescription(){
        return description;
    }
}
