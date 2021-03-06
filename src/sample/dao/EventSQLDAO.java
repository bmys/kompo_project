package sample.dao;

import com.mysql.cj.protocol.Resultset;
import sample.model.Event;
import sample.model.Repository.Observer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class EventSQLDAO extends SQLDAO implements Observer<Observer.repoNotify, Event> {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

        String SQL = "INSERT INTO events(ID, title, date, description, locations) VALUES(?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "title=VALUES(title), date=VALUES(date), description=VALUES(description), locations=VALUES(locations)";

        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setString(1, ev.getUniqueID());
            pstmt.setString(2, ev.getTitle());

            String date = sdf.format(ev.getDateTime());

            pstmt.setString(3, date);
            pstmt.setString(4, ev.getDescription());

            //TODO: location as other table
            String result = String.join(", ", ev.getLocations());
            pstmt.setString(5, result);

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

        String SQL = "DELETE FROM reminders WHERE event = ?";
        String SQL2 = "DELETE FROM events WHERE id = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ev.getUniqueID());

            Boolean allgood = pstmt.execute();
            if (allgood){
                System.out.println("Query OK! Deleted reminder.");
            }

            pstmt.close();

            pstmt = conn.prepareStatement(SQL2);
            pstmt.setString(1, ev.getUniqueID());

            allgood = pstmt.execute();

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
            pstmt.setString(2, ev.getDescription());
            //TODO: location as other table
            String result = String.join(", ", ev.getLocations());
            pstmt.setString(3, result);
            pstmt.setString(4, ev.getUniqueID());

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

    public void insertAll(List<Event> evs){
        onCreateAll(evs);
    }

    private void onCreateAll(List<Event> evs){
        Connection conn = connect();
        if (conn == null) return;

        String SQL = "INSERT INTO events(ID, title, date, description, locations) VALUES(?, ?, ?, ?, ?)" +
                "ON DUPLICATE KEY UPDATE " +
                "title=VALUES(title), date=VALUES(date), description=VALUES(description), locations=VALUES(locations)";

        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            for(Event ev: evs) {
                pstmt.setString(1, ev.getUniqueID());
                pstmt.setString(2, ev.getTitle());
                String date = sdf.format(ev.getDateTime());
                // TODO: no time
                pstmt.setString(3, date);
                pstmt.setString(4, ev.getDescription());

                String result = String.join(", ", ev.getLocations());
                pstmt.setString(5, result);

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

        String SQL = "DELETE FROM events WHERE id = ?; DELETE FROM events WHERE id = ?";

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

    public List<Event>  getAllFromDataBase(){

        List<Event> eventList = new LinkedList<>();

        Connection conn = connect();
        if (conn == null) return eventList;

        String SQL = "SELECT * FROM events ";

        try (PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            ResultSet res =pstmt.executeQuery();
            while(res.next())
            {
                Event nevEvent = new Event();
                nevEvent.setUniqueID(res.getString(1));
                nevEvent.setTitle(res.getString(2));
                nevEvent.setDateTime(res.getDate(3));
                nevEvent.setDescription(res.getString(4));
                List<String> locations = Arrays.asList(res.getString(5).split("\\s+"));
                nevEvent.setLocations(locations);

                eventList.add(nevEvent);
            }

            pstmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(eventList);
        return eventList;
    }
}
