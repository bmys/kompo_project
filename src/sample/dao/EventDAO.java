package sample.dao;

import sample.model.Event;
import sample.model.Observer;

import java.util.List;

public class EventDAO implements Observer<Observer.repoNotify, Event> {
    @Override
    public void onUpdate(repoNotify notify, Event payload) {
        System.out.println("EVENT DAO here ;)");
        System.out.println(notify.getType());
        System.out.println(payload.getDate());
    }

    @Override
    public void onUpdate(repoNotify notify, List<Event> payload) {

    }
}
