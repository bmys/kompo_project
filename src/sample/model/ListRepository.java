package sample.model;

import java.util.Collection;
import java.util.List;

public abstract class ListRepository<T> implements Repository<List<T>, T> {

    private List<T> repository;

    ListRepository(List<T> repository) {
        this.repository = repository;
    }

    @Override
    public Boolean add(T el) {
        try{
            this.repository.add(el);
            return true;
        }
        catch(Exception e){
            return false;
        }

    }

    @Override
    public Boolean addAll(Collection<T> col) {
        try{
            this.repository.addAll(col);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public Boolean remove(int idx) {
        try{
            this.repository.remove(idx);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public T get(int idx) {
        try{
            return this.repository.get(idx);
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public List<T> getAll() {
        return this.repository;
    }

    @Override
    public int getSize() {
        return this.repository.size();
    }

    @Override
    public Boolean exits(T el) {
        return this.repository.contains(el);
    }

    @Override
    public Boolean remove(T el) {
        try{
            return this.repository.remove(el);
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public Boolean remove(Query qr) {
        return null;
    }

    @Override
    public T get(T el) {
        try{
            return repository.get(repository.indexOf(el));
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public T get(Query qr) {

        return null;
    }

    @Override
    public List<T> getAll(Query<T> qr) {
        return qr.getResult(this.repository);
    }

    @Override
    public Boolean update(T el, T newEl) {
        try{

            return false;
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public Boolean update(int idx, T newEl) {
        return null;
    }

    @Override
    public Boolean update(Query qr, T newEl) {
        return null;
    }

    @Override
    public Boolean exits(Query qr) {
        return null;
    }


}
