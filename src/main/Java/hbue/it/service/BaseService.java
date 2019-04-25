package hbue.it.service;


import java.util.List;

/**
 * @author lyw
 */

public interface BaseService {
    Object getObject(int id);
    void update(Object obj);
    void delete(int id);
    void insert(Object obj);
    List<Object> getAll();
}
