package hbue.it.service.Facade;

import hbue.it.exception.ContestNotFoundException;
import hbue.it.exception.UserNotFoundException;
import hbue.it.pojo.Alternative;
import hbue.it.pojo.Column_info;
import hbue.it.pojo.Column_value;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface SignUpFacadeService {
    //报名增加栏目值
    void signUp(Column_value column_value,int custom,int contest) throws UserNotFoundException,ContestNotFoundException;

    void keep(Column_value column_value,int custom);

    //修改栏目值
    void update(Column_value column_value,int custom) throws UserNotFoundException,ContestNotFoundException;

    //同步栏目值（第一次）
    String sychroData(int uid,String key) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    //同步栏目值
    String sychroData(int uid,int cid) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;

    //显示栏目信息
    List<Column_info> listAllInfo(int cid) throws ContestNotFoundException;

    List<Alternative> listAlternativeByCid(int cid);


}
