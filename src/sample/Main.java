package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.model.Event;
import sample.model.Reminder;

import javax.xml.bind.JAXBException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../resources/view/sample.fxml"));
        primaryStage.setTitle("Kalendarz");
        primaryStage.setScene(new Scene(root, 840, 460));
        primaryStage.show();
    }

    public class MyThread extends Thread {
        Reminder reminder;

        public MyThread(Reminder rem) {
            reminder = rem;
        }

        public void run(){
            while(true){
                if(reminder.checkTime(new Date())){
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
//        SQLDAO sql = new SQLDAO();
//        sql.connect();
//        Query<Event> query = new Query<>();
//        Event event = new Event( "date", new Date() , "description", new ArrayList<String>(Arrays.asList("a", "b")));
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
//
//        EventSQLDAO eventDAO = new EventSQLDAO();
//        LinkedListRepository<Event> linkedListRepository = new LinkedListRepository<>();
////        linkedListRepository.registerObserver(eventDAO);
//
//        Event ev2 = new Event("Hello", new Date());
//        linkedListRepository.add(ev2);
//        linkedListRepository.add(new Event("World!", new Date()));
//        System.out.println(linkedListRepository.getSize());
//        linkedListRepository.remove(ev2);
//        System.out.println(linkedListRepository.getSize());




        launch(args);
//        Calendar cal = new GregorianCalendar();
//        cal.add(Calendar.SECOND, 15);
//
//        Event ev2 = new Event("Hello", new Date());
//
//        Reminder reminder = new Reminder(ev2, cal.getTime());
//        while(!reminder.checkTime(new Date()));
////        MyThread myThread = new MyThread(reminder);
//



    }
}
