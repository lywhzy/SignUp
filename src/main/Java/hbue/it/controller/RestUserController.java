package hbue.it.controller;

import hbue.it.exception.ContestNotFoundException;
import hbue.it.exception.UserNotFoundException;
import hbue.it.pojo.Alternative;
import hbue.it.pojo.Column_value;
import hbue.it.pojo.Contest;
import hbue.it.pojo.User;
import hbue.it.service.ColumnService;
import hbue.it.service.Facade.HomeService;
import hbue.it.service.Facade.SignUpFacadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

@RestController
public class RestUserController extends  BaseController{

    @Autowired
    private HomeService homeService;
    @Autowired
    private SignUpFacadeService signUpFacadeService;
    @Autowired
    private ColumnService columnService;

    @RequestMapping("getCharacterization")
    public List<Contest> getTopContest(){
        List<Contest> list = homeService.getTopCharacterization();
        return list;
    }

    @RequestMapping(value = "getValue",produces = "text/html;charset=UTF-8")
    public String getColumn_value(int cid,int status) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        User user = (User) session.getAttribute("user");
        String value;
        if(status==1){
            value = signUpFacadeService.sychroData(user.getId(), cid);
        }else{
            String key = columnService.getInfo(cid).getName();
            value= signUpFacadeService.sychroData(user.getId(),key);
        }
        return value;
    }

    @RequestMapping(value = "getAlternative")
    public List<Alternative> getAlternative(int cid) throws ContestNotFoundException {
        List<Alternative> list = signUpFacadeService.listAlternativeByCid(cid);
        return list;
    }

    @RequestMapping("updateCv")
    public String updateColumn_value(Column_value column_value, int custom){
        try{
            User user = (User) session.getAttribute("user");
            column_value.setUid(user.getId());
            signUpFacadeService.update(column_value,custom);
        }catch (ContestNotFoundException e){
            return "false";
        }catch (UserNotFoundException e){
            return "false";
        }
        return "success";
    }

    @RequestMapping("keep")
    public String keepColumn_value(Column_value column_value,int custom){
        try{
            User user = (User) session.getAttribute("user");
            column_value.setUid(user.getId());
            signUpFacadeService.keep(column_value,custom);
        }catch (ContestNotFoundException e){
            return "false";
        }catch (UserNotFoundException e){
            return "false";
        }
        return "success";
    }

    @RequestMapping("signup")
    public String signup(Column_value column_value,int custom,int contest){
        try{
            User user = (User) session.getAttribute("user");
            column_value.setUid(user.getId());
            signUpFacadeService.signUp(column_value,custom,contest);
        }catch (ContestNotFoundException e){
            return "false";
        }catch (UserNotFoundException e){
            return "false";
        }
        return "success";
    }
}
