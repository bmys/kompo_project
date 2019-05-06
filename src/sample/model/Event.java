package sample.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.xml.bind.JAXB;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Event {
    private String uniqueID = UUID.randomUUID().toString();
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

    public Event(Date dateTime) {
        this.title = "0";
        this.dateTime = dateTime;
        this.description = "1";
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

    @XmlAttribute
    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    @XmlElement
    public void setTitle(String title) {
        this.title = title;
    }

    @XmlElement
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement
    public void setLocations(List<String> locations) {
        this.locations = locations;
    }
}
