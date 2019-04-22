package sample.model;

import java.util.Date;

public abstract class Reminder {
    private Event ev;
    private Date time;

    public Reminder(Event ev, Date time) {
        this.ev = ev;
        this.time = time;
    }

    public void checkTime(Date currentTime){
        if(currentTime.compareTo(time) > 0){
            System.out.println("Reminder!");
        }
    }
}
