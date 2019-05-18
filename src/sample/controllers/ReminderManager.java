package sample.controllers;

import sample.dao.ReminderSQLDAO;
import sample.model.Reminder;
import sample.model.Repository.LinkedListRepository;

import java.util.List;

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

    public List<Reminder> getAll(){
        return repository.getAll();
    }
}
