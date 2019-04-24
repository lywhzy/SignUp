package hbue.it.mapper;

import hbue.it.pojo.Status;
import hbue.it.pojo.StatusExample;
import java.util.List;

public interface StatusMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Status record);

    int insertSelective(Status record);

    List<Status> selectByExample(StatusExample example);

    Status selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Status record);

    int updateByPrimaryKey(Status record);
}