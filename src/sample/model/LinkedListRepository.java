package sample.model;

import java.util.LinkedList;

public class LinkedListRepository <T> extends ListRepository<T>{
    public LinkedListRepository() {
        super(new LinkedList<T>());
    }
}
