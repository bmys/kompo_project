package sample.controllers;

import javafx.util.Pair;
import sample.dao.EventSQLDAO;
import sample.model.Event;
import sample.model.Repository.LinkedListRepository;
import sample.model.Repository.Query;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class EventManager {
    private LinkedListRepository<Event> repository;
    private EventSQLDAO eventSQLDAO = new EventSQLDAO();

    public EventManager() {
        this.repository = new LinkedListRepository<>();
        //this.repository.registerObserver(eventSQLDAO);
    }

    public EventManager(List<Event> list) {
        this.repository = new LinkedListRepository<>(list);
        //this.repository.registerObserver(eventSQLDAO);
    }

    public boolean addEvent(Event ev){
        return this.repository.add(ev);
    }

    public List<Event> getEventsWithLocation(List<String> Location){
        Query<Event> qr = new Query<>();
        qr.filter(locsQuery(Location));
        return repository.getAll(qr);
    }

    public List<Event> getEventsOlderThan(Date date){
        Query<Event> qr = new Query<>();
        qr.filter(dateQuery(dateQr.older, date));
        return repository.getAll(qr);
    }

    public List<Event> getEventsnewerThan(Date date){
        Query<Event> qr = new Query<>();
        qr.filter(dateQuery(dateQr.newer, date));
        return repository.getAll(qr);
    }

    public List<Event> getEventsfromTimeBound(Date dateFirst, Date dateSecond){
        Query<Event> qr = new Query<>();
                qr
                .filter(dateQuery(dateQr.newer, dateFirst))
                .filter(dateQuery(dateQr.older, dateSecond));
        return repository.getAll(qr);
    }

    public Boolean removeEventsfromTimeBound(Date dateFirst, Date dateSecond){
        Query<Event> qr = new Query<>();
        qr
                .filter(dateQuery(dateQr.newer, dateFirst))
                .filter(dateQuery(dateQr.older, dateSecond));
        return repository.remove(qr);
    }

    public Boolean removeEventsOlderThan(Date date){
        Query<Event> qr = new Query<>();
        qr.filter(dateQuery(dateQr.older, date));

        return repository.remove(qr);
    }

    public Boolean removeEvent(Event ev){
        return repository.remove(ev);
    }

    public Boolean changeDate(Event event, Date time){
        Event newEvent = new Event(event);
        newEvent.setDateTime(time);
        return repository.update(event, newEvent);
    }

    public Boolean changeDate(List<Event> events, Date time){
        List<Pair<Event, Event>> updatedEvents = new LinkedList<>();

        for (Event ev: events) {
            Event nev = new Event(ev);
            nev.setDateTime(time);
            updatedEvents.add(new Pair<Event, Event>(ev, nev));
        }

        for(Pair<Event, Event> pair: updatedEvents){
            if(!repository.update(pair.getKey(), pair.getValue())){
                // log error
                return false;
            }
        }
        return true;
    }


    enum dateQr{
        older, same, newer
    }

    private Predicate<Event> dateQuery(dateQr time, Date date){
        switch(time){

            case older:
                return ev -> ev.getDateTime().compareTo(date) < 0;

            case same:
                return ev -> ev.getDateTime().compareTo(date) == 0;

            case newer:
                return ev -> ev.getDateTime().compareTo(date) > 0;

            default:
                throw new IllegalArgumentException();
        }
    }

    private Predicate<Event> locsQuery(List<String> locations){
        return ev -> ev.getLocations().containsAll(locations);
    }

    public List<Event> getAll(){
        return repository.getAll();
    }
}
