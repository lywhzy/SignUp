package hbue.it.mapper;

import hbue.it.pojo.Column_info;
import hbue.it.pojo.Column_infoExample;
import java.util.List;

public interface Column_infoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Column_info record);

    int insertSelective(Column_info record);

    List<Column_info> selectByExample(Column_infoExample example);

    Column_info selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Column_info record);

    int updateByPrimaryKey(Column_info record);
}