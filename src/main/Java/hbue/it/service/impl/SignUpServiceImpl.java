package hbue.it.service.impl;

import hbue.it.exception.ContestNotFoundException;
import hbue.it.exception.UserNotFoundException;
import hbue.it.mapper.*;
import hbue.it.pojo.*;
import hbue.it.service.SignUpService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SignUpServiceImpl implements SignUpService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private Column_valueMapper column_valueMapper;
    @Autowired
    private User_infoMapper user_infoMapper;
    @Autowired
    private Column_infoMapper column_infoMapper;
    @Autowired
    private ContestMapper contestMapper;

    static ConcurrentHashMap<String,String> keyName = null;

    static Logger logger = Logger.getLogger(SignUpServiceImpl.class);

    static{
        keyName = new ConcurrentHashMap<String,String>();
        keyName.put("手机号","PhoneNumber");
        keyName.put("邮箱","Email");
        keyName.put("姓名","Name");
        keyName.put("学号","Number");
        keyName.put("院系","Faculty");
        keyName.put("班级","Clazz");
        keyName.put("专业","Profession");
        keyName.put("入学年份","Enrolment");
        keyName.put("照片","Picture");
    }

    @Override
    public void signUp(Column_value column_value) throws UserNotFoundException,ContestNotFoundException{
        judgeUserAndContest(column_value);
        column_valueMapper.insertSelective(column_value);
    }

    /**
     *
     * @param uid
     * @param key
     * @return 同步后的通用数据（第一次报名比赛时用）
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public String sychroData(int uid,String key) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        User user = userMapper.selectByPrimaryKey(uid);
        User_info user_info = user_infoMapper.selectByPrimaryKey(uid);
        if(user==null&&user_info==null) return null;
        Class clazz = null;
        String value = null;
        String methodName = keyName.get(key);
        methodName = "get" + methodName;
        if(key.equals("手机号")||key.equals("邮箱")){
            clazz = User.class;
            Method method = clazz.getMethod(methodName,null);
            value = (String) method.invoke(user,null);
        }else{
            clazz = User_info.class;
            Method method = clazz.getMethod(methodName,null);
            value = (String) method.invoke(user_info,null);
        }
        return value;
    }

    /**
     *
     * @param uid 用户id
     * @param cid 比赛id
     * @param key 栏目名称
     * @return  同步非通用的栏目值（修改报名时以及非第一次报名时用 此时通用信息以存入栏目值表）
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Override
    public String sychroData(int uid, int cid, String key) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Column_infoExample column_infoExample = new Column_infoExample();
        column_infoExample.createCriteria()
                .andCidEqualTo(cid)
                .andNameEqualTo(key);
        List<Column_info> list = column_infoMapper.selectByExample(column_infoExample);

        Column_valueExample column_valueExample = new Column_valueExample();
        column_valueExample.createCriteria()
                .andCidEqualTo(list.get(0).getId())
                .andUidEqualTo(uid);
        List<Column_value> vlist = column_valueMapper.selectByExample(column_valueExample);

        return vlist.get(0).getValue();
    }

    @Override
    public List<Column_info> listAllInfo(int cid/*比赛id*/) throws ContestNotFoundException{
        Contest contest = contestMapper.selectByPrimaryKey(cid);
        if(contest==null){
            logger.info("比赛不存在");
            throw new ContestNotFoundException();
        }
        Column_infoExample column_infoExample = new Column_infoExample();
        column_infoExample.createCriteria()
                .andCidEqualTo(cid);
        return column_infoMapper.selectByExample(column_infoExample);
    }

    @Override
    public void update(Column_value column_value) throws UserNotFoundException,ContestNotFoundException{
        judgeUserAndContest(column_value);
        column_valueMapper.updateByPrimaryKeySelective(column_value);
    }

    private void judgeUserAndContest(Column_value column_value) {
        User user = userMapper.selectByPrimaryKey(column_value.getUid());
        if(user==null){
            logger.info("用户不存在");
            throw  new UserNotFoundException();
        }
        Contest contest = contestMapper.selectByPrimaryKey(column_infoMapper.selectByPrimaryKey(column_value.getCid()).getCid());
        if(contest==null){
            logger.info("比赛不存在");
            throw  new ContestNotFoundException();
        }
    }


}
