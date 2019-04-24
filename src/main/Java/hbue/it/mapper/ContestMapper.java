package hbue.it.mapper;

import hbue.it.pojo.Contest;
import hbue.it.pojo.ContestExample;
import java.util.List;

public interface ContestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Contest record);

    int insertSelective(Contest record);

    List<Contest> selectByExample(ContestExample example);

    Contest selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Contest record);

    int updateByPrimaryKey(Contest record);
}