package sample.controllers;

import sample.model.Event;
import sample.model.LinkedListRepository;
import sample.model.Query;

import java.util.Date;
import java.util.List;
import java.util.function.Predicate;

public class EventManager {
    private LinkedListRepository<Event> repository;

    public EventManager() {
        this.repository = new LinkedListRepository<>();
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
}
