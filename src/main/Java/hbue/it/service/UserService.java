package hbue.it.service;

import hbue.it.pojo.User;
import hbue.it.pojo.User_info;

public interface UserService extends BaseService<User>{
    User_info getInfo(int uid);
}
