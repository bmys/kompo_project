package sample.model;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Query<T> {
    private List<Predicate<T>> tasks = new LinkedList<>();

    public Query<T> filter(Predicate<T> task){
        this.tasks.add(task);
        return this;
    }

    public List<T> getResult(List<T> col) {
        Stream<T> stream = col.stream();

        for (Predicate<T> tsk: tasks) {
             stream = stream.filter(tsk);
        }

        return stream.collect(Collectors.toList());
    }

    public class QueryResult<T> {
        List<T> result;
        List<String> errors;

    }
//    Query<T> map(){
//
//        return this;
//    }
}
