package hbue.it.service.impl;

import hbue.it.exception.UserNotFoundException;
import hbue.it.mapper.AlternativeMapper;
import hbue.it.mapper.Column_infoMapper;
import hbue.it.pojo.Alternative;
import hbue.it.pojo.AlternativeExample;
import hbue.it.pojo.Column_value;
import hbue.it.service.AlternativeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlternativeServiceImpl implements AlternativeService {

    @Autowired
    private AlternativeMapper alternativeMapper;
    @Autowired
    private Column_infoMapper column_infoMapper;

    @Override
    public void isAddAlternative(Column_value column_value,int custom) {
        if(custom==1&&column_infoMapper.selectByPrimaryKey(column_value.getCid()).getIcontype().equals("下拉框")){
            Alternative alternative = new Alternative();
            alternative.setCid(column_value.getCid());
            alternative.setValue(column_value.getValue());
            alternative.setUser_permit(true);
            alternativeMapper.insertSelective(alternative);
        }
    }

    @Override
    public List<Alternative> listAlternativeByCid(int cid) {
        AlternativeExample alternativeExample = new AlternativeExample();
        alternativeExample.createCriteria()
                .andCidEqualTo(cid)
                .andUser_permitEqualTo(false);
        return alternativeMapper.selectByExample(alternativeExample);
    }

    @Override
    public Alternative get(int id) {
        return null;
    }

    @Override
    public void update(Alternative obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void insert(Alternative obj) {

    }

    @Override
    public List<Alternative> getAll() {
        return null;
    }
}
