package sample.model;

import java.util.List;

public interface Observer<C, T> {
    void onUpdate(C notify, T payload);
    void onUpdate(C notify, List<T> payload);
}
