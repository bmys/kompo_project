package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.EventManager;
import sample.dao.EventDAO;
import sample.model.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../resources/view/sample.fxml"));
        primaryStage.setTitle("Kalendarz");
        primaryStage.setScene(new Scene(root, 840, 460));
        primaryStage.show();
    }


    public static void main(String[] args) {

//        Query<Event> query = new Query<>();
//        Event event = new Event(new Date());
//        EventManager eventManager = new EventManager();
//        Boolean wynik = eventManager.addEvent(event);
//        System.out.println(wynik);
//
//        Date dt = new Date();
//        Calendar c = Calendar.getInstance();
//        c.setTime(dt);
//        c.add(Calendar.DATE, -1);
//        dt = c.getTime();
//
//        List<Event> ev = eventManager.getEventsOlderThan(dt);
//        System.out.println(ev);

        EventDAO eventDAO = new EventDAO();
        LinkedListRepository<Event> linkedListRepository = new LinkedListRepository<>();
//        linkedListRepository.registerObserver(eventDAO);

        Event ev = new Event("Hello", new Date());
        linkedListRepository.add(ev);
        linkedListRepository.add(new Event("World!", new Date()));
        System.out.println(linkedListRepository.getSize());
        linkedListRepository.remove(ev);
        System.out.println(linkedListRepository.getSize());

//        launch(args);
    }
}
