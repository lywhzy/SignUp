package hbue.it.service.impl;

import hbue.it.mapper.Column_infoMapper;
import hbue.it.mapper.Column_valueMapper;
import hbue.it.pojo.Column_info;
import hbue.it.pojo.Column_infoExample;
import hbue.it.pojo.Column_value;
import hbue.it.pojo.Column_valueExample;
import hbue.it.service.ColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Column_ServiceImpl implements ColumnService {
    @Autowired
    private Column_valueMapper column_valueMapper;
    @Autowired
    private Column_infoMapper column_infoMapper;

    @Override
    public Column_value get(int id) {
        return column_valueMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Column_value obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void delete(Column_value column_value) {
        column_valueMapper.deleteColumn_value(column_value);
    }

    @Override
    public void insert(Column_value obj) {
        column_valueMapper.insertSelective(obj);
    }

    @Override
    public List<Column_value> getAll() {
        return null;
    }

    @Override
    public Column_value getCvByUCId(int uid, int cid) {
        Column_valueExample column_valueExample = new Column_valueExample();
        column_valueExample.createCriteria()
                .andUidEqualTo(uid)
                .andCidEqualTo(cid);
        List<Column_value> list = column_valueMapper.selectByExample(column_valueExample);
        if(list.isEmpty()) return null;
        return list.get(0);
    }

    @Override
    public void updateByUCId(Column_value column_value) {
        column_valueMapper.updateByUCId(column_value);
    }

    @Override
    public List<Column_info> listByCId(int cid) {
        Column_infoExample column_infoExample = new Column_infoExample();
        column_infoExample.createCriteria()
                .andCidEqualTo(cid);
        column_infoExample.setOrderByClause("sequence asc");
        return column_infoMapper.selectByExample(column_infoExample);
    }

    @Override
    public Column_info getInfo(int cid) {
        return column_infoMapper.selectByPrimaryKey(cid);
    }
}
