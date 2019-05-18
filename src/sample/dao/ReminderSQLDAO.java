package sample.dao;

import sample.model.Event;
import sample.model.Reminder;
import sample.model.Repository.Observer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

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
