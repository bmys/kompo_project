package sample.model;

import java.util.Date;

public class Reminder {
    private Event ev;
    private Date time;

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
