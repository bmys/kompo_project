package sample.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class ObservableListRepository<T> extends ListRepository<T> implements Observable<T>{

    private List<Observer<repoNotify, T>> observers = new LinkedList<>();

    public ObservableListRepository(List<T> repository) {
        super(repository);
    }

    @Override
    public Boolean add(T el) {
        if(super.add(el)){
            notifyObservers(new repoNotify(notifyEnum.create), el);
            return true;
        }
        return false;
    }

    @Override
    public Boolean addAll(Collection<T> col) {
        if(super.addAll(col)){
            notifyObservers(new repoNotify(notifyEnum.create), new LinkedList<>(col));
            return true;
        }
        return false;
    }

    @Override
    public Boolean remove(int idx) {
        if(super.remove(idx)){
            T el = get(idx);
            notifyObservers(new repoNotify(notifyEnum.remove), el);
            return true;
        }
        return false;
    }

    @Override
    public Boolean remove(T el) {
        if(super.remove(el)){
            notifyObservers(new repoNotify(notifyEnum.remove), el);
            return true;
        }
        return false;
    }

    @Override
    public Boolean remove(Query<T> qr) {
        List<T> elements = super.getAll(qr);
        if(super.remove(qr)){
            notifyObservers(new repoNotify(notifyEnum.remove), elements);
            return true;
        }
        return false;
    }

    @Override
    public Boolean update(T el, T newEl) {
        if(super.update(el, newEl)){
            notifyObservers(new repoNotify(notifyEnum.update), newEl);
        }
        return false;
    }

    @Override
    public Boolean update(int idx, T newEl) {
        if(super.update(idx, newEl)){
            notifyObservers(new repoNotify(notifyEnum.update), newEl);
        }
        return false;
    }

    @Override
    public Boolean update(Query<T> qr, T newEl) {
        List<T> elements = super.getAll(qr);

        if(super.update(qr, newEl)){
            notifyObservers(new repoNotify(notifyEnum.update), elements);
            return true;
        }

        return false;
    }

    @Override
    public void registerObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void unregisterObserver(Observer obs) {
        observers.remove(obs);
    }

    public void notifyObservers(repoNotify notify, T payload){
        for(Observer<repoNotify, T> obs: observers ){
            obs.onUpdate(notify, payload);
        }
    }

    public void notifyObservers(repoNotify notify, List<T> payload){
        for(Observer<repoNotify, T> obs: observers ){
            obs.onUpdate(notify, payload);
        }
    }

    class repoNotify implements notifyType{
        notifyEnum en;
        public repoNotify(notifyEnum ne) {
        this.en = en;
        }

        @Override
        public Object getType() {
            return en;
        }
    }

    enum notifyEnum{
        create, update, remove
    }
}
