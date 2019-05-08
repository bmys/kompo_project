package sample.model.Repository;

import java.util.LinkedList;

public class LinkedListRepository <T> extends ObservableListRepository<T>{
    public LinkedListRepository() {
        super(new LinkedList<T>());
    }
}
