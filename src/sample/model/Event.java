package sample.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class Event {
    private String uniqueID = UUID.randomUUID().toString();
    private String title;
    private Date dateTime;
    private String description;
    private List<String> locations = new LinkedList<>();

    public Event(String title, Date dateTime, String description, List<String> locations) {
        this.title = title;
        this.dateTime = dateTime;
        this.description = description;
        this.locations = locations;
    }

    public Event(String title, Date dateTime) {
        this.title = title;
        this.dateTime = dateTime;
        this.description = "";
    }


    public Event(String title, Date dateTime, String description) {
        this.title = title;
        this.dateTime = dateTime;
        this.description = description;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public String getTitle() {
        return title;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getLocations() {
        return locations;
    }
}
