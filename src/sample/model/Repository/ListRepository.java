package sample.model.Repository;

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
            repository.add(el);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public Boolean addAll(Collection<T> col) {
        try{
            repository.addAll(col);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public Boolean remove(int idx) {
        try{
            repository.remove(idx);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public T get(int idx) {
        try{
            return repository.get(idx);
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public List<T> getAll() {
        return repository;
    }

    @Override
    public int getSize() {
        return repository.size();
    }

    @Override
    public Boolean exits(T el) {
        return repository.contains(el);
    }

    @Override
    public Boolean remove(T el) {
        try{
            repository.remove(el);
            return true;

        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public Boolean remove(Query<T> qr) {
        try{
            List<T> result = qr.getResult(repository);
            repository.removeAll(result);
            return true;
        }
        catch(Exception e){
            return false;
        }
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
    public T get(Query<T> qr) {
        return qr.getFirst(repository);
    }

    @Override
    public List<T> getAll(Query<T> qr) {
        return qr.getResult(this.repository);
    }

    @Override
    public Boolean update(T el, T newEl) {
        try{
            int idx = repository.indexOf(el);
            if (idx == -1) return false;
            repository.set(idx, newEl);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public Boolean update(int idx, T newEl) {
        try{
            repository.set(idx, newEl);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public Boolean update(Query<T> qr, T newEl) {
//        todo: try to change query to map
        try{
            T el = get(qr);
            int idx = repository.indexOf(el);
            repository.set(idx, newEl);
            return true;
        }
        catch(Exception e){
            return false;
        }
    }

    @Override
    public Boolean exits(Query<T> qr) {
        return get(qr) != null;
    }


}
