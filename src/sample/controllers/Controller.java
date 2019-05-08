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

public class Controller implements Initializable{
//    private ObservableList<Event> observableList;
//    private List<Event> eventList = new ArrayList<>();

    private ObservableList<Event> observableList;
    private ListProperty<Event> eventListProperty;

    public Controller() {
        System.out.println("HEllo!");
    }

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

    @FXML
    private CheckBox useTimeBoundCheck;

    @FXML
    private DatePicker toDatePicker;

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

        String desc = descText.getText();

        String locations = locationText.getText().toLowerCase();
        ArrayList<String> locationList = new ArrayList<>(Arrays.asList(locations.split(" ")));
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

    public void changeGoDate(){
        toDatePicker.setDisable(!useTimeBoundCheck.isSelected());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableList = FXCollections.observableArrayList();
        eventListProperty = new SimpleListProperty<>();
        eventListProperty.set(observableList);
        eventListView.itemsProperty().bindBidirectional(eventListProperty);
        eventManager = new EventManager(observableList);
        eventManager.addSQLDAO();
    }
}
