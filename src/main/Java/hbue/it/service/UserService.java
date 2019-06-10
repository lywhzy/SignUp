package hbue.it.service;

import hbue.it.pojo.User;
import hbue.it.pojo.User_info;

import java.io.UnsupportedEncodingException;

public interface UserService extends BaseService<User>{
    User_info getInfo(int uid);
    int insertUser(User record);

    int updateByPrimaryKey(User record);

    User selLogin(String username, String password) throws UnsupportedEncodingException;

}
