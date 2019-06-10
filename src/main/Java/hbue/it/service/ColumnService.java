package hbue.it.service;

import hbue.it.pojo.Column_info;
import hbue.it.pojo.Column_value;

import java.util.List;

public interface ColumnService extends BaseService<Column_value>{
    Column_value getCvByUCId(int uid,int cid);
    void updateByUCId(Column_value column_value);
    List<Column_info> listByCId(int cid/*比赛id*/);
    Column_info getInfo(int cid);
    void delete(Column_value column_value);
}
