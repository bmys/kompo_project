package sample.model.Repository;

import java.util.List;

public interface Observable<C, T> {

    public interface notifyType<C>{
       C getType();
    }

    void registerObserver(Observer<C, T> obs);
    void unregisterObserver(Observer<C,T> obs);
    void notifyObservers(C notify, T payload);
    void notifyObservers(C notify, List<T> payload);
}
