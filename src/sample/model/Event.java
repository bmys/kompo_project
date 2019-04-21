package sample.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Event {
    private Date dateTime;
    private String description;
    private List<String> locations = new LinkedList<>();

    public Event(Date dateTime) {
        this.dateTime = dateTime;
        this.description = "";
    }

    public Event(Date dateTime, String description) {
        this.dateTime = dateTime;
        this.description = description;
    }

    public Event(Date dateTime, String description, List<String> locations) {
        this.dateTime = dateTime;
        this.description = description;
        this.locations = locations;
    }

    public Date getDate() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getLocations() {
        return locations;
    }
}
