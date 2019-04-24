package hbue.it.mapper;

import hbue.it.pojo.Alternative;
import hbue.it.pojo.AlternativeExample;
import java.util.List;

public interface AlternativeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Alternative record);

    int insertSelective(Alternative record);

    List<Alternative> selectByExample(AlternativeExample example);

    Alternative selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Alternative record);

    int updateByPrimaryKey(Alternative record);
}