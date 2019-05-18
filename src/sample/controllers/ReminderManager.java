package sample.controllers;

import sample.dao.ReminderSQLDAO;
import sample.model.Event;
import sample.model.Reminder;
import sample.model.Repository.LinkedListRepository;
import sample.model.Repository.Query;

import java.util.List;
import java.util.function.Predicate;

public class ReminderManager {
    private LinkedListRepository<Reminder> repository;
    private ReminderSQLDAO reminderSQLDAO;

    public ReminderManager() {
        this.repository = new LinkedListRepository<>();
        //this.repository.registerObserver(reminderSQLDAO);
    }

    public ReminderManager(List<Reminder> list) {
        this.repository = new LinkedListRepository<>(list);
        //this.repository.registerObserver(reminderSQLDAO);
    }

    public boolean addReminder(Reminder rem){
        return this.repository.add(rem);
    }

    public void addSQLDAO(){
        if(reminderSQLDAO == null){
            reminderSQLDAO = new ReminderSQLDAO();
            repository.registerObserver(reminderSQLDAO);
        }
    }

    public void removeSQLDAO(){
        if(reminderSQLDAO != null){
            repository.unregisterObserver(reminderSQLDAO);
            reminderSQLDAO = null;
        }
    }

    public void addDAO(ReminderSQLDAO obs){
        repository.registerObserver(obs);
    }

    public void removeDAO(ReminderSQLDAO obs){
        repository.unregisterObserver(obs);
    }

    public Boolean removeReminder(Reminder rem){
        return repository.remove(rem);
    }

    public Boolean removeReminder(Event ev){
        Query<Reminder> qr = new Query<>();
        qr.filter(eventQuery(ev));

        return repository.remove(qr);
    }

    public List<Reminder> getAll(){
        return repository.getAll();
    }

    private Predicate<Reminder> eventQuery(Event event){
        return ev -> ev.getEv().equals(event);
    }
}
