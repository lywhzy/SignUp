package hbue.it.service.impl;

import hbue.it.mapper.UserMapper;
import hbue.it.mapper.User_infoMapper;
import hbue.it.pojo.User;
import hbue.it.pojo.User_info;
import hbue.it.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private User_infoMapper user_infoMapper;
    @Autowired
    private UserMapper userMapper;

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(User obj) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void insert(User obj) {

    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User_info getInfo(int uid) {
        return user_infoMapper.selectByPrimaryKey(uid);
    }

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
        if (name != null && password != null && !"".equals(name) && !"".equals(password)){
            try{
                user = (User) userMapper.selLogin(name, password);
            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return user;
    }
}
