package sample.model;

import java.util.List;

public interface Observable<T> {

    public interface notifyType<C>{
       C getType();
    }

    void registerObserver(Observer obs);
    void unregisterObserver(Observer obs);
    void notifyObservers(notifyType notify, T payload);
    void notifyObservers(notifyType notify, List<T> payload);
}
