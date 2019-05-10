package sample.controllers;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sample.model.Event;

import java.awt.*;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.List;

public class Controller implements Initializable{
//    private ObservableList<Event> observableList;
//    private List<Event> eventList = new ArrayList<>();

    private ObservableList<Event> observableList;
    private ListProperty<Event> eventListProperty;
    private EventManager eventManager = new EventManager(new LinkedList<>());

    public Controller() {
        System.out.println("HEllo!");
        eventManager.addSQLDAO();
    }

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

    @FXML
    private CheckBox useTimeBoundCheck;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private ChoiceBox hourChooseBox;

    @FXML
    private ChoiceBox MinuteChooseBox;

    public void addEvent(){
        String title = titleText.getText();

        LocalDate localDate = newEventDatePicker.getValue();

        Date date;
        if(localDate == null){
            date = new Date();
        }
        else{
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            int hour = Integer.parseInt(String.valueOf(hourChooseBox.getSelectionModel().getSelectedItem()));
            instant = instant.plusSeconds(hour * 3600);

            int minute = Integer.parseInt(String.valueOf(MinuteChooseBox.getSelectionModel().getSelectedItem()));
            instant = instant.plusSeconds(minute * 60);

            date = Date.from(instant);
        }

        String desc = descText.getText();

        String locations = locationText.getText().toLowerCase();
        ArrayList<String> locationList = new ArrayList<>(Arrays.asList(locations.split(" ")));
        eventManager.addEvent(new Event(title, date, desc, locationList));

        updateEventList();
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
        updateEventList();

//        System.out.println(eve);
//        System.out.println(eventList);

    }

    public void changeGoDate(){
        toDatePicker.setDisable(!useTimeBoundCheck.isSelected());
        updateEventList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableList = FXCollections.observableArrayList();
        eventListProperty = new SimpleListProperty<>();
        eventListProperty.set(observableList);
        eventListView.itemsProperty().bindBidirectional(eventListProperty);

        for (int i = 0; i <= 23; i++) {
            hourChooseBox.getItems().add(i);
        }
        hourChooseBox.getSelectionModel().selectFirst();

        for (int i = 0; i <= 59; i++) {
            MinuteChooseBox.getItems().add(i);
        }
        MinuteChooseBox.getSelectionModel().selectFirst();
    }

    private Date localToDate(LocalDate localDate){
        Date date;
        if(localDate == null){
            date = new Date();
        }
        else{
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            date = Date.from(instant);
        }
        return date;
    }

    public void updateEventList(){
        observableList.removeAll(observableList);

        Date from = localToDate(listDatePicker.getValue());

        if(useTimeBoundCheck.isSelected()){
            Date to = localToDate(toDatePicker.getValue());
            List<Event> evs = eventManager.getEventsfromTimeBound(from ,to);
            System.out.println(evs);
            observableList.addAll(evs);
            return;
        }

        List<Event> evs =  eventManager.getEventsFromDay(from);
        observableList.addAll(evs);
    }
}
