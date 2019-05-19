package sample.controllers;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import sample.dao.EventSQLDAO;
import sample.dao.ReminderSQLDAO;
import sample.dao.XMLDAO;
import sample.model.Event;
import sample.model.Reminder;
import sample.model.Repository.Observer;

import javax.xml.bind.annotation.*;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class Controller implements Initializable {

    private ObservableList<Event> observableList;
    private ObservableList<Reminder> observableReminderList;

    private EventManager eventManager = new EventManager(new LinkedList<>());
    private ReminderManager reminderManager= new ReminderManager(new LinkedList<>());
    private boolean isLiveSQLMode = false;

    public Controller() {
//        eventManager.addSQLDAO();
//        reminderManager.addSQLDAO();
    }

    @FXML
    private Label titleLabel;

    @FXML
    private Label dateLabel;

    @FXML
    private Label descLabel;

    @FXML
    private Label locLabel;

    @FXML
    private TextField titleText;

    @FXML
    private ListView<Event> eventListView;

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
    private ChoiceBox<Integer> hourChooseBox;

    @FXML
    private ChoiceBox<Integer> MinuteChooseBox;

    @FXML
    private ListView<Reminder> reminderListView;

    @FXML
    private MenuItem liveMenu;

    private Date getHourMinuteDate(LocalDate localDate){
        Date date;
        if (localDate == null) {
            date = new Date();
        } else {
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            int hour = Integer.parseInt(String.valueOf(hourChooseBox.getSelectionModel().getSelectedItem()));
            instant = instant.plusSeconds(hour * 3600);

            int minute = Integer.parseInt(String.valueOf(MinuteChooseBox.getSelectionModel().getSelectedItem()));
            instant = instant.plusSeconds(minute * 60);

            date = Date.from(instant);
        }
        return date;
    }

    public void addEvent() {
        String title = titleText.getText();

        LocalDate localDate = newEventDatePicker.getValue();
        Date date = getHourMinuteDate(localDate);

        String desc = descText.getText();
        String locations = locationText.getText().toLowerCase();
        ArrayList<String> locationList = new ArrayList<>(Arrays.asList(locations.split(" ")));
        eventManager.addEvent(new Event(title, date, desc, locationList));

        updateEventList();
    }

    public void deleteEvent() {
        List<Event> ev = eventListView.getSelectionModel().getSelectedItems();
        ev.forEach(eventManager::removeEvent);
        ev.forEach(reminderManager::removeReminder);
        System.out.println(observableList);
        System.out.println(eventManager.getAll());
        updateEventList();
        updateReminderList();
    }

    public void changeGoDate() {
        toDatePicker.setDisable(!useTimeBoundCheck.isSelected());
        updateEventList();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        observableList = FXCollections.observableArrayList();
        ListProperty<Event> eventListProperty = new SimpleListProperty<>();
        eventListProperty.set(observableList);
        eventListView.itemsProperty().bindBidirectional(eventListProperty);

        observableReminderList = FXCollections.observableArrayList();
        ListProperty<Reminder> reminderListProperty = new SimpleListProperty<>();
        reminderListProperty.set(observableReminderList);
        reminderListView.itemsProperty().bindBidirectional(reminderListProperty);

        for (int i = 0; i <= 23; i++) {
            hourChooseBox.getItems().add(i);
        }
        hourChooseBox.getSelectionModel().selectFirst();

        for (int i = 0; i <= 59; i++) {
            MinuteChooseBox.getItems().add(i);
        }
        MinuteChooseBox.getSelectionModel().selectFirst();
    }

    private Date localToDate(LocalDate localDate) {
        Date date;
        if (localDate == null) {
            date = new Date();
        } else {
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            date = Date.from(instant);
        }
        return date;
    }

    public void updateReminderList(){
        observableReminderList.clear();
        observableReminderList.addAll(reminderManager.getAll());
    }

    public void updateEventList() {
        observableList.clear();
        Date from = localToDate(listDatePicker.getValue());

        if (useTimeBoundCheck.isSelected()) {
            Date to = localToDate(toDatePicker.getValue());
            List<Event> evs = eventManager.getEventsFromTimeBound(from, to);
            System.out.println(evs);
            observableList.addAll(evs);
            return;
        }

        List<Event> evs = eventManager.getEventsFromDay(from);
        observableList.addAll(evs);
        eventListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        eventListView.setOnMouseClicked(mouseEvent -> {
            ObservableList<Event> selectedItems = eventListView.getSelectionModel().getSelectedItems();

            if (selectedItems.size() == 1) {
                if (selectedItems.get(0) == null) return;
                Event item = selectedItems.get(0);
                titleLabel.setText(item.getTitle());
                dateLabel.setText(item.getDateTime().toString());
                descLabel.setText(item.getDescription());
                locLabel.setText(item.getLocations().toString());
            } else {
                titleLabel.setText("tytuł");
                dateLabel.setText("data");
                descLabel.setText("opis");
                locLabel.setText("lokacje");
            }
        });
    }

    public void showCalendarPopUp() {
        Stage stage = new Stage();

        Popup popup = new Popup();
        BorderPane borderPane = new BorderPane();

        DatePicker datepicker = new DatePicker();
        Button btn = new Button("fsdsfd");

        borderPane.getChildren().add(btn);
        borderPane.getChildren().add(datepicker);

        Scene scene = new Scene(borderPane, 200, 200);
        stage.setScene(scene);
        stage.show();


        List<Event> ev = eventListView.getSelectionModel().getSelectedItems();
//        ev.forEach(eventManager::changeDate);
        updateEventList();
    }

    public void changeDate(){
        List<Event> ev = eventListView.getSelectionModel().getSelectedItems();

        if(ev.size() == 0) return;

        LocalDate localDate = newEventDatePicker.getValue();


        if (localDate == null){
            localDate = LocalDate.now();
        }

        Date date = getHourMinuteDate(localDate);

        eventManager.changeDate(ev, date);
        updateEventList();
    }

    public void removeOlderThan() {
        LocalDate localDate = newEventDatePicker.getValue();

        if (localDate == null){
            localDate = LocalDate.now();
        }

        Date date = getHourMinuteDate(localDate);

        eventManager.removeEventsOlderThan(date);
        updateEventList();
    }

    public void createReminder(ActionEvent actionEvent) {
        String title = titleText.getText();

        LocalDate localDate = newEventDatePicker.getValue();
        Date date = getHourMinuteDate(localDate);

        ObservableList<Event> selectedItems = eventListView.getSelectionModel().getSelectedItems();

        for(Event ev: selectedItems){
            reminderManager.addReminder(new Reminder(title, ev, date));
        }

        updateReminderList();
    }

    public void exportToXML() {
        EventsDatabase eventsDatabase = new EventsDatabase();
        eventsDatabase.setEvents(eventManager.getAll());

        RemindersDatabase remindersDatabase = new RemindersDatabase();
        remindersDatabase.setReminders(reminderManager.getAll());

        Database database =  new Database();
        database.setEventsDatabase(eventsDatabase);
        database.setRemindersDatabase(remindersDatabase);

//        XMLDAO.saveToFile("test.xml", database);

        XMLDAO.saveToFile("test.xml", database);
    }

    public void exportToDataBase() {
        EventSQLDAO eventSQLDAO = new EventSQLDAO();
        eventSQLDAO.insertAll(eventManager.getAll());

        ReminderSQLDAO reminderSQLDAO = new ReminderSQLDAO();
        reminderSQLDAO.insertAll(reminderManager.getAll());

    }

    public void importFromDataBase() {
        EventSQLDAO eventSQLDAO = new EventSQLDAO();
        List<Event> eventList = eventSQLDAO.getAllFromDataBase();
        for(Event ev: eventList){
            if(eventManager.getAll().contains(ev)){
                int idx = eventManager.getAll().indexOf(ev);
                eventManager.getAll().set(idx, ev);
            }
            else{
                eventManager.addEvent(ev);
            }
        }
        updateEventList();
        ReminderSQLDAO reminderSQLDAO = new ReminderSQLDAO();
        List<Reminder> reminderList = reminderSQLDAO.getAllFromDataBase(eventManager.getAll());
        
        for(Reminder rem: reminderList){
            if(reminderManager.getAll().contains(rem)){
                int idx = reminderManager.getAll().indexOf(rem);
                reminderManager.getAll().set(idx, rem);
            }
            else{
                reminderManager.addReminder(rem);
            }
        }

        updateReminderList();
    }

    public void importFromXML() {
        Database database = XMLDAO.loadFromFile("test.xml");
// TODO: export this loops to function
        for(Event ev: database.getEventsDatabase().events){
            if(eventManager.getAll().contains(ev)){
                int idx = eventManager.getAll().indexOf(ev);
                eventManager.getAll().set(idx, ev);
            }
            else{
                eventManager.addEvent(ev);
            }
        }

        for(Reminder rem: database.getRemindersDatabase().reminders){
            if(reminderManager.getAll().contains(rem)){
                int idx = reminderManager.getAll().indexOf(rem);
                reminderManager.getAll().set(idx, rem);
            }
            else{
                reminderManager.addReminder(rem);
            }
        }

        updateEventList();
        updateReminderList();
    }

    public void showInfo() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("O autorze");
        alert.setHeaderText("Program kalendarz");
        alert.setContentText("Wykonany przez Bartosza Myśliwca nr. indeksu 211827");

        alert.showAndWait();
    }

    public void turnLiveSQL(ActionEvent actionEvent) {
        if(isLiveSQLMode){
            isLiveSQLMode = false;
            liveMenu.setText("Włącz live SQL");
            eventManager.removeSQLDAO();
            reminderManager.removeSQLDAO();
        }
        else{
            isLiveSQLMode = true;
            liveMenu.setText("Wyłącz live SQL");
            eventManager.addSQLDAO();
            reminderManager.addSQLDAO();
        }
    }


    @XmlRootElement(name = "database")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Database
    {
        @XmlElement(name="events")
        EventsDatabase eventsDatabase;
        @XmlElement(name="reminders")
        RemindersDatabase remindersDatabase;

        public EventsDatabase getEventsDatabase() {
            return eventsDatabase;
        }

        public void setEventsDatabase(EventsDatabase eventsDatabase) {
            this.eventsDatabase = eventsDatabase;
        }

        public RemindersDatabase getRemindersDatabase() {
            return remindersDatabase;
        }

        public void setRemindersDatabase(RemindersDatabase remindersDatabase) {
            this.remindersDatabase = remindersDatabase;
        }
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    public static class EventsDatabase
    {

        @XmlElement(name="event")
        List<Event> events = new ArrayList<>();

        public List<Event> getEvents() {
            return events;
        }

        public void setEvents(List<Event> events) {
            this.events = events;
        }
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    public static class RemindersDatabase
    {

        @XmlElement(name="reminder")
        List<Reminder> reminders = new ArrayList<>();

        public List<Reminder> getReminders() {
            return reminders;
        }

        public void setReminders(List<Reminder> reminders) {
            this.reminders = reminders;
        }
    }
    }

