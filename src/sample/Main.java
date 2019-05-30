package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.controllers.Controller;
import sample.controllers.ReminderManager;
import sample.model.Reminder;

import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.LinkedList;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../resources/view/sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Kalendarz");
        primaryStage.setScene(new Scene(root, 840, 460));
        ReminderManager reminderManager = new ReminderManager(new LinkedList<>());
        Controller controller = loader.getController();
        controller.setReminderManager(reminderManager);

        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Runnable updater = new Runnable() {

                    @Override
                    public void run() {
                        try {
                           controller.removeRemainder();
                        }
                        catch (ConcurrentModificationException e){
                            System.out.println("Poczekaj");
                        }
                    }
                };

                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                    }

                    // UI update is run on the Application thread

                        Platform.runLater(updater);
                }
            }

        });
        // don't let thread prevent JVM shutdown
        thread.setDaemon(true);
        thread.start();

        primaryStage.show();
    }
//    public class reminderThread extends Thread {
//        Controller controller;
//
//        public reminderThread(Controller controller) {
//            this.controller = controller;
//        }
//
//        public void run(){
//            while(true){
//                try {
//                    Thread.sleep(1000);
//                    for(Reminder rem: controller.getReminders()){
//                        if(rem.checkTime(new Date())){
//                            System.out.println("Alarm " + rem.getEv().getTitle()) ;
//                            controller.removeRemainder(rem);
//                        }
//                    }
//
//                } catch (InterruptedException ex) {
//                }
//
//
//
//            }
//        }
//    }


    public static void main(String[] args) {
//        SQLDAO sql = new SQLDAO();
//        sql.connect();
//        Query<Event> query = new Query<>();
//        Event event = new Event( "date", new Date() , "description", new ArrayList<String>(Arrays.asList("a", "b")));
//        EventManager eventManager = new EventManager();
//        Boolean wynik = eventManager.addReminder(event);
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
////        reminderThread myThread = new reminderThread(reminder);
//



    }
}
