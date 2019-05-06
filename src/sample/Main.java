package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.EventManager;
import sample.dao.EventDAO;
import sample.dao.SQLDAO;
import sample.model.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
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


    public static void main(String[] args) throws JAXBException {
        SQLDAO sql = new SQLDAO();
        sql.connect();
        Query<Event> query = new Query<>();
        Event event = new Event(new Date());
        EventManager eventManager = new EventManager();
        Boolean wynik = eventManager.addEvent(event);
        System.out.println(wynik);

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, -1);
        dt = c.getTime();

        List<Event> ev = eventManager.getEventsOlderThan(dt);
        System.out.println(ev);

        EventDAO eventDAO = new EventDAO();
        LinkedListRepository<Event> linkedListRepository = new LinkedListRepository<>();
//        linkedListRepository.registerObserver(eventDAO);

        Event ev2 = new Event("Hello", new Date());
        linkedListRepository.add(ev2);
        linkedListRepository.add(new Event("World!", new Date()));
        System.out.println(linkedListRepository.getSize());
        linkedListRepository.remove(ev2);
        System.out.println(linkedListRepository.getSize());

//        Event ev = new Event("Hello", new Date());
//        System.out.println("elo");
//
//   try{
//       File file = new File("./out.xml");
//       JAXBContext jaxbContext = JAXBContext.newInstance(Event.class);
//       Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//
//       // output pretty printed
//       jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//
//       jaxbMarshaller.marshal(ev, file);
//       jaxbMarshaller.marshal(ev, System.out);
//
//    } catch(JAXBException e) {
//        e.printStackTrace();
//    }
//


//        launch(args);


    }
}
