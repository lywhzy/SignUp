package hbue.it.service;

import hbue.it.pojo.User;

import java.io.UnsupportedEncodingException;

public interface UserService {

    public int insertUser(User record);

    public int updateByPrimaryKey(User record);

    public User selLogin(String username, String password) throws UnsupportedEncodingException;

}
