package sample.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import java.util.Date;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
public class Reminder {
    private String uniqueID = UUID.randomUUID().toString();

    private String title;
    @XmlIDREF
    private Event ev;
    private Date time;

    public Reminder() {
    }

    public Reminder(String title, Event ev, Date time) {
        this.title = title;
        this.ev = ev;
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
        return ev;
    }

    public void setEv(Event ev) {
        this.ev = ev;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Reminder(Event ev, Date time) {
        this.ev = ev;
        this.time = time;
    }

    public boolean checkTime(Date currentTime){
        if(currentTime.compareTo(time) > 0){
            System.out.println("Reminder!");
            return true;
        }

        return false;
    }
}
