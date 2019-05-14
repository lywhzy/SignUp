package hbue.it.service;

import hbue.it.exception.ContestNotFoundException;
import hbue.it.exception.UserNotFoundException;
import hbue.it.pojo.Column_info;
import hbue.it.pojo.Column_value;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface SignUpService {
    void signUp(Column_value column_value) throws UserNotFoundException,ContestNotFoundException;
    void update(Column_value column_value) throws UserNotFoundException,ContestNotFoundException;
    String sychroData(int uid,String key) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
    String sychroData(int uid,int cid,String key) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
    List<Column_info> listAllInfo(int cid) throws ContestNotFoundException;
}
