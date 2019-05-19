package sample.dao;

import sample.model.Event;
import sample.model.Reminder;
import sample.model.Repository.Observer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ReminderSQLDAO extends SQLDAO implements Observer<Observer.repoNotify, Reminder> {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void onUpdate(repoNotify notify, Reminder payload) {
        switch(notify.getType()){
            case create:
                onCreate(payload);
                break;
            case remove:
                onRemove(payload);
                break;

            default:
                throw(new IllegalArgumentException("Unknown notify type"));
        }
    }

    @Override
    public void onUpdate(repoNotify notify, List<Reminder> payload) {
        switch(notify.getType()){
            case create:
                onCreateAll(payload);
                break;
            case remove:
                onRemoveAll(payload);
                break;

            default:
                throw(new IllegalArgumentException("Unknown notify type"));

        }
    }

    public void insertAll(List<Reminder> rem){
        onCreateAll(rem);
    }
    public List<Reminder> getAllFromDataBase(List<Event> eventList){
        List<Reminder> reminderList = new LinkedList<>();

        Connection conn = connect();
        if (conn == null) return reminderList;

        String SQL = "SELECT * FROM reminders";

        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet res =pstmt.executeQuery();
            while(res.next())
            {
                Reminder nevReminder= new Reminder();
                nevReminder.setUniqueID(res.getString(2));
                nevReminder.setTitle(res.getString(3));
                nevReminder.setTime(res.getDate(4));
//                event
                Optional ev = eventList.stream().filter(e-> {
                    try {
                        return e.getUniqueID().equals(res.getString(5));
                    } catch (SQLException exc) {
                        exc.printStackTrace();
                        return false;
                    }
                }).findFirst();
                if(ev.isPresent()){
                    nevReminder.setEv((Event)ev.get());
                }
                else{
                    System.out.println("Optional failed");
                    nevReminder.setEv(null);
                }

                reminderList.add(nevReminder);
            }

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(eventList);
        return reminderList;
    }
    private void onCreate(Reminder reminder){
        Connection conn = connect();
        if (conn == null) return;

        String SQL = "INSERT INTO reminders(ID, title, event, date) VALUES(?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, reminder.getUniqueID());
            pstmt.setString(2, reminder.getTitle());

            pstmt.setString(3, reminder.getEv().getUniqueID());

            String date = sdf.format(reminder.getTime());
            pstmt.setString(4, date);

            Boolean allgood = pstmt.execute();

            if (allgood){
                System.out.println("Query OK! Added new event.");
            }

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onRemove(Reminder reminder){
        Connection conn = connect();
        if (conn == null) return;

        String SQL = "DELETE FROM reminders WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, reminder.getUniqueID());

            Boolean allgood = pstmt.execute();
            if (allgood){
                System.out.println("Query OK! Deleted reminder.");
            }

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void onCreateAll(List<Reminder> reminders){
        Connection conn = connect();
        if (conn == null) return;

        String SQL = "INSERT INTO reminders(ID, title, event, date) VALUES(?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            for(Reminder rem: reminders) {
                pstmt.setString(1, rem.getUniqueID());
                pstmt.setString(2, rem.getTitle());
                pstmt.setString(3, rem.getEv().getUniqueID());

                String date = sdf.format(rem.getTime());
                pstmt.setString(4, date);

                pstmt.addBatch();
            }


            int[] allgood = pstmt.executeBatch();

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onRemoveAll(List<Reminder> evs){
        Connection conn = connect();
        if (conn == null) return;

        String SQL = "DELETE FROM reminders WHERE ID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            for(Reminder ev: evs) {
                pstmt.setString(1, ev.getUniqueID());
                pstmt.addBatch();
            }


            int[] allgood = pstmt.executeBatch();

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
