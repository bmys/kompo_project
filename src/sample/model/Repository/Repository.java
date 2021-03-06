package sample.model.Repository;


import java.util.Collection;

public interface  Repository <S, T>{
    Boolean add(T el);
    Boolean addAll(Collection<T> col);

    Boolean remove(T el);
    Boolean remove(int idx);
    Boolean remove(Query<T> qr);

    T get(T el);
    T get(int idx);
    T get(Query<T> qr);
    S getAll(Query<T> qr);

    Boolean update(T el, T newEl);
    Boolean update(int idx, T newEl);
    Boolean update(Query<T> qr, T newEl);

    S getAll();
    int getSize();
    Boolean exits(T el);
    Boolean exits(Query<T> qr);

}
