package hbue.it.service;


import java.util.List;

/**
 * @author lyw
 */

public interface BaseService<T> {
    T get(int id);
    void update(T obj);
    void delete(int id);
    void insert(T obj);
    List<T> getAll();
}
