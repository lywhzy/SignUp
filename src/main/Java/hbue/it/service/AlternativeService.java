package hbue.it.service;

import hbue.it.pojo.Alternative;
import hbue.it.pojo.Column_value;

import java.util.List;

public interface AlternativeService extends BaseService<Alternative> {
    void isAddAlternative(Column_value column_value,int custom);
    List<Alternative> listAlternativeByCid(int cid);
}
