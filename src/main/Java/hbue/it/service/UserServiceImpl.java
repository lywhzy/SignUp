package hbue.it.service;

import hbue.it.mapper.UserMapper;
import hbue.it.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    // 虽然报错但项目运行没有问题，不加注入会显示空指针异常
    @Autowired
    private UserMapper userMapper;

    @Override
    public int insertUser(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public User selLogin(String name, String password) {
        User user = null;
        //userList.remove(null);
        if (name != null && password != null){
            try{
                user = (User) userMapper.selLogin(name, password);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return user;
    }
}
