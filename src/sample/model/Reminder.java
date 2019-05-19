package sample.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
public class Reminder {
    private String uniqueID = UUID.randomUUID().toString();

    private String title;
    @XmlIDREF
    private Event event;
    private Date time;

    public Reminder() {
    }

    public Reminder(String title, Event event, Date time) {
        this.title = title;
        this.event = event;
        this.time = time;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Event getEv() {
        return event;
    }

    public void setEv(Event event) {
        this.event = event;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Reminder(Event event, Date time) {
        this.event = event;
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reminder reminder = (Reminder) o;
        return Objects.equals(uniqueID, reminder.uniqueID);
    }

    @Override
    public int hashCode() {

        return Objects.hash(uniqueID);
    }

    public boolean checkTime(Date currentTime){
        if(currentTime.compareTo(time) > 0){
            System.out.println("Reminder!");
            return true;
        }

        return false;
    }

    @Override
    public String toString() {
        return  title + ' ' +
                event.getTitle() +
                " " + time;
    }
}
