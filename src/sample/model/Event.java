package sample.model;

import java.util.Date;

public class Event {
    private Date dateTime;
    private String description;

    public Event(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Event(Date dateTime, String description) {
        this.dateTime = dateTime;
        this.description = description;
    }

    public Date getDate() {
        return dateTime;
    }
}
