package sample.controllers;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import sample.model.Event;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Controller implements Initializable{
//    private ObservableList<Event> observableList;
//    private List<Event> eventList = new ArrayList<>();

//    private List<String> strList = new LinkedList<>();
    private ObservableList<Event> observableList;
    private ListProperty<Event> eventListProperty;

    public Controller() {
        System.out.println("HEllo!");
    }

//    @Override
//    public void initialize() {
//
//        ObservableList<String> observableList = FXCollections.observableList(strList);
//        eventListView.setItems(observableList);

//        eventListView.setCellFactory(new Callback<ListView<Event>, ListCell<Event>>(){
//
//            @Override
//            public ListCell<Event> call(ListView<Event> p) {
//
//                ListCell<Event> cell = new ListCell<Event>(){
//
//                    @Override
//                    protected void updateItem(Event t, boolean bln) {
//                        super.updateItem(t, bln);
//                        if (t != null) {
//                            setText(t.getTitle() + ":" + t.getDateTime());
//                        }
//                    }
//
//                };
//
//                return cell;
//            }
//        });
//
//    }




    private EventManager eventManager = new EventManager();

    @FXML
    private TextField titleText;

    @FXML
    private ListView<Event> eventListView;

    @FXML
    private Button addEventBtn;

    @FXML
    private Button changeDateBtn;

    @FXML
    private Button deleteEventBtn;

    @FXML
    private DatePicker newEventDatePicker;

    @FXML
    private TextArea descText;

    @FXML
    private DatePicker listDatePicker;

    @FXML
    private TextField locationText;

    public void addEvent(){
        String title = titleText.getText();

        LocalDate localDate = newEventDatePicker.getValue();

        Date date;
        if(localDate == null){
            date = new Date();
        }
        else{
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            date = Date.from(instant);
        }


//        strList.add(title);

        String desc = descText.getText();
//        System.out.println("Title: " + title + "Description: " + desc + "\n");
//        eventList.add();

//        System.out.println(strList);
        String locations = locationText.getText().toLowerCase();
        List<String> locationList = new ArrayList(Arrays.asList(locations.split(" ")));
        eventManager.addEvent(new Event(title, date, desc, locationList));
//        eventListView.getItems().clear();
//        eventListView.getItems().addAll(eventList);

    }

    public void deleteEvent(){
        Event ev = eventListView.getSelectionModel().getSelectedItem();
        if(ev != null){
            eventManager.removeEvent(ev);
        }
        System.out.println(observableList);
        System.out.println(eventManager.getAll());

//        System.out.println(eve);
//        System.out.println(eventList);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableList = FXCollections.observableArrayList();
        eventListProperty = new SimpleListProperty<>();
        eventListProperty.set(observableList);
        eventListView.itemsProperty().bindBidirectional(eventListProperty);
        eventManager = new EventManager(observableList);
        eventManager.addSQLDAO();

//        eventListView.setCellFactory(new Callback<ListView<Event>, ListCell<Event>>(){

//            @Override
//            public ListCell<String> call(ListView<String> p) {
//
//                ListCell<String> cell = new ListCell<String>(){
//
//                    @Override
//                    protected void updateItem(String t, boolean bln) {
//                        super.updateItem(t, bln);
//                        if (t != null) {
//                            setText(t + "xD");
//                        }
//                    }
//
//                };
//
//                return cell;
//            }
//        });
//            @Override
//            public ListCell<Event> call(ListView<Event> p) {
//
//                ListCell<Event> cell = new ListCell<Event>(){
//
//                    @Override
//                    protected void updateItem(Event t, boolean bln) {
//                        super.updateItem(t, bln);
//                        if (t != null) {
//                            setText(t.getDateTime().toString() + ":" );
//                        }
//                    }
//
//                };
//
//                return cell;
//            }
//        });
    }
}
