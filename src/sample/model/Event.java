package sample.model;

import java.util.*;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.*;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)

public class Event {
    @XmlAttribute
    @XmlID
    private String uniqueID;
    private String title;
    private Date dateTime;
    private String description;
    private List<String> locations = new LinkedList<>();

    public Event() {
    }

    public Event(Event ev) {
        this.uniqueID = ev.uniqueID;
        this.title = ev.title;
        this.dateTime = ev.dateTime;
        this.description = ev.description;
        this.locations = ev.locations;
    }

    public Event(String title, Date dateTime, String description, List<String> locations) {
        this.uniqueID = UUID.randomUUID().toString();
        this.title = title;
        this.dateTime = dateTime;
        this.description = description;
        this.locations = locations;
    }

    public Event(String title, Date dateTime) {
        this.uniqueID = UUID.randomUUID().toString();
        this.title = title;
        this.dateTime = dateTime;
        this.description = "";
    }

    public Event(Date dateTime) {
        this.uniqueID = UUID.randomUUID().toString();
        this.title = "0";
        this.dateTime = dateTime;
        this.description = "1";
    }


    public Event(String title, Date dateTime, String description) {
        this.uniqueID = UUID.randomUUID().toString();
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

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return  title + " " + dateTime.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(uniqueID, event.uniqueID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uniqueID);
    }
}
