package hbue.it.mapper;

import hbue.it.pojo.User_info;
import hbue.it.pojo.User_infoExample;
import java.util.List;

public interface User_infoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_info record);

    int insertSelective(User_info record);

    List<User_info> selectByExample(User_infoExample example);

    User_info selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_info record);

    int updateByPrimaryKey(User_info record);
}