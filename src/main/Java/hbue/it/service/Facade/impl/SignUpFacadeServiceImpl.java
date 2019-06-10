package hbue.it.service.Facade.impl;

import hbue.it.exception.ContestNotFoundException;
import hbue.it.exception.UserNotFoundException;
import hbue.it.pojo.*;
import hbue.it.service.AlternativeService;
import hbue.it.service.ColumnService;
import hbue.it.service.ContestService;
import hbue.it.service.Facade.SignUpFacadeService;
import hbue.it.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SignUpFacadeServiceImpl implements SignUpFacadeService {
    @Autowired
    private UserService userService;
    @Autowired
    private ColumnService column_Service;
    @Autowired
    private AlternativeService alternativeService;
    @Autowired
    private ContestService contestService;


    static ConcurrentHashMap<String,String> keyName = null;

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

    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception")
    @Override
    public void signUp(Column_value column_value,int custom,int contest) throws UserNotFoundException,ContestNotFoundException{
        column_Service.delete(column_value);
        column_Service.insert(column_value);
        if(!contestService.isRelationExist(column_value.getUid(),contest))
            contestService.insertRelation(column_value.getUid(),contest);
        alternativeService.isAddAlternative(column_value,custom);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception")
    @Override
    public void keep(Column_value column_value,int custom) {
        column_Service.insert(column_value);

        alternativeService.isAddAlternative(column_value,custom);
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
        User user = userService.get(uid);
        User_info user_info = userService.getInfo(uid);
        if(user==null&&user_info==null) return null;
        Class clazz = null;
        String value = null;
        String methodName = null;
        if(keyName.containsKey(key))
             methodName = keyName.get(key);
        else{
            return null;
        }
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
     * @param cid 栏目id
     * @return  同步非通用的栏目值（修改报名时以及非第一次报名时用 此时通用信息以存入栏目值表）
     */
    @Override
    public String sychroData(int uid, int cid){

        Column_value column_value = column_Service.getCvByUCId(uid,cid);
        return column_value==null ? null : column_value.getValue();
    }

    @Override
    public List<Column_info> listAllInfo(int cid/*比赛id*/) throws ContestNotFoundException{
        return column_Service.listByCId(cid);
    }

    @Override
    public List<Alternative> listAlternativeByCid(int cid) {
        return alternativeService.listAlternativeByCid(cid);
    }


    @Transactional(propagation = Propagation.REQUIRED,rollbackForClassName = "Exception")
    @Override
    public void update(Column_value column_value,int custom) throws UserNotFoundException,ContestNotFoundException{
        if(column_Service.getCvByUCId(column_value.getUid(),column_value.getCid())==null){
            column_Service.insert(column_value);
        }else{
            column_Service.updateByUCId(column_value);
        }

        alternativeService.isAddAlternative(column_value,custom);
    }




}
