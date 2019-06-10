package hbue.it.mapper;

import hbue.it.pojo.User;
import hbue.it.pojo.UserExample;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {

    // List<User> selLogin(@Param("name") String name, @Param("password") String password);
    User selLogin(@Param("name") String name, @Param("password") String password);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}