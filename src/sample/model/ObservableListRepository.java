package sample.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class ObservableListRepository<T> extends ListRepository<T> implements Observable<Observer.repoNotify, T>{

    private List<Observer<Observer.repoNotify, T>> observers = new ArrayList<>();

    ObservableListRepository(List<T> repository) {
        super(repository);
    }

    public void notifyObservers(Observer.repoNotify notify, T payload){
        for(Observer<Observer.repoNotify, T> obs: observers ){
            obs.onUpdate(notify, payload);
        }
    }

    public void notifyObservers(Observer.repoNotify notify, List<T> payload){
        for(Observer<Observer.repoNotify, T> obs: observers ){
            obs.onUpdate(notify, payload);
        }
    }

    @Override
    public Boolean add(T el) {
        if(super.add(el)){
            notifyObservers(new Observer.repoNotify(Observer.notifyEnum.create), el);
            return true;
        }
        return false;
    }

    @Override
    public Boolean addAll(Collection<T> col) {
        if(super.addAll(col)){
            notifyObservers(new Observer.repoNotify(Observer.notifyEnum.create), new LinkedList<>(col));
            return true;
        }
        return false;
    }

    @Override
    public Boolean remove(int idx) {
        if(super.remove(idx)){
            T el = get(idx);
            notifyObservers(new Observer.repoNotify(Observer.notifyEnum.remove), el);
            return true;
        }
        return false;
    }

    @Override
    public Boolean remove(T el) {
        if(super.remove(el)){
            notifyObservers(new Observer.repoNotify(Observer.notifyEnum.remove), el);
            return true;
        }
        return false;
    }

    @Override
    public Boolean remove(Query<T> qr) {
        List<T> elements = super.getAll(qr);
        if(super.remove(qr)){
            notifyObservers(new Observer.repoNotify(Observer.notifyEnum.remove), elements);
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(T el, T newEl) {
        if(super.update(el, newEl)){
            notifyObservers(new Observer.repoNotify(Observer.notifyEnum.update), newEl);
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(int idx, T newEl) {
        if(super.update(idx, newEl)){
            notifyObservers(new Observer.repoNotify(Observer.notifyEnum.update), newEl);
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(Query<T> qr, T newEl) {
        List<T> elements = super.getAll(qr);

        if(super.update(qr, newEl)){
            notifyObservers(new Observer.repoNotify(Observer.notifyEnum.update), elements);
            return true;
        }

        return false;
    }

    @Override
    public void registerObserver(Observer<Observer.repoNotify, T> obs) {
        observers.add(obs);
    }

    @Override
    public void unregisterObserver(Observer obs) {
        observers.remove(obs);
    }
}
