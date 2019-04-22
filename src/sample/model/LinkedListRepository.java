package sample.model;

import java.util.LinkedList;
import java.util.List;

public class LinkedListRepository <T> extends ObservableListRepository<T>{
    public LinkedListRepository() {
        super(new LinkedList<T>());
    }

}
