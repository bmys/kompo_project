package sample.dao;

import sample.model.Event;
import sample.model.Observer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class EventDAO extends SQLDAO implements Observer<Observer.repoNotify, Event> {

    @Override
    public void onUpdate(repoNotify notify, Event payload) {
        switch(notify.getType()){
            case create:
                onCreate(payload);
                break;
            case remove:
                onRemove(payload);
                break;
            case update:
                onUpdate(payload);
                break;

            default:
                throw(new IllegalArgumentException("Unknown notify type"));
        }
    }

    @Override
    public void onUpdate(repoNotify notify, List<Event> payload) {
        switch(notify.getType()){
            case create:
                onCreateAll(payload);
                break;
            case remove:
                onRemoveAll(payload);
                break;
            case update:
                onUpdateAll(payload);
                break;

            default:
                throw(new IllegalArgumentException("Unknown notify type"));

        }
    }

    private void onCreate(Event ev){
        Connection conn = connect();
        if (conn == null) return;

        String SQL = "INSERT INTO events(ID, title, description, locations) VALUES(?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, ev.getUniqueID());
            pstmt.setString(1, ev.getTitle());
            pstmt.setString(1, ev.getDescription());
            //TODO: location as other table
            String result = String.join(", ", ev.getLocations());
            pstmt.setString(1, result);

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

    private void onRemove(Event ev){
        Connection conn = connect();
        if (conn == null) return;

        String SQL = "DELETE FROM events WHERE id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ev.getUniqueID());

            Boolean allgood = pstmt.execute();
            if (allgood){
                System.out.println("Query OK! Deleted new event.");
            }

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onUpdate(Event ev){
        Connection conn = connect();
        if (conn == null) return;

        String SQL = "UPDATE events SET title = ?, description = ?, locations = ? WHERE ID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
//            pstmt.setString(1, ev.getUniqueID());
            pstmt.setString(1, ev.getTitle());
            pstmt.setString(1, ev.getDescription());
            //TODO: location as other table
            String result = String.join(", ", ev.getLocations());
            pstmt.setString(1, result);
            pstmt.setString(1, ev.getUniqueID());

            Boolean allgood = pstmt.execute();
            if (allgood){
                System.out.println("Query OK! Updated new event.");
            }

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onCreateAll(List<Event> evs){
        Connection conn = connect();
        if (conn == null) return;

        String SQL = "INSERT INTO events(ID, title, description, locations) VALUES(?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            for(Event ev: evs) {
                pstmt.setString(1, ev.getUniqueID());
                pstmt.setString(1, ev.getTitle());
                pstmt.setString(1, ev.getDescription());
                String result = String.join(", ", ev.getLocations());
                pstmt.setString(1, result);
                pstmt.addBatch();
            }


            int[] allgood = pstmt.executeBatch();

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void onRemoveAll(List<Event> evs){
        Connection conn = connect();
        if (conn == null) return;

        String SQL = "DELETE FROM events WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            for(Event ev: evs) {
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

    private void onUpdateAll(List<Event> evs){
        Connection conn = connect();
        if (conn == null) return;

        String SQL = "UPDATE events SET title = ?, description = ?, locations = ? WHERE ID = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            for(Event ev: evs) {
                pstmt.setString(1, ev.getTitle());
                pstmt.setString(1, ev.getDescription());
                //TODO: location as other table
                String result = String.join(", ", ev.getLocations());
                pstmt.setString(1, result);
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
