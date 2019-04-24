package hbue.it.mapper;

import hbue.it.pojo.Column_value;
import hbue.it.pojo.Column_valueExample;
import java.util.List;

public interface Column_valueMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Column_value record);

    int insertSelective(Column_value record);

    List<Column_value> selectByExample(Column_valueExample example);

    Column_value selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Column_value record);

    int updateByPrimaryKey(Column_value record);
}