package hbue.it.controller;

import hbue.it.exception.ContestNotFoundException;
import hbue.it.exception.UserNotFoundException;
import hbue.it.pojo.*;
import hbue.it.service.HomeService;
import hbue.it.service.MyContestService;
import hbue.it.service.SignUpService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private MyContestService myContestService;

    @Autowired
    private SignUpService signUpService;
    @Autowired
    private HomeService homeService;


    @RequestMapping("listMyContest")
    public String listMyContest(User user, Model model){
        List<Contest> list = myContestService.listContestByid(user.getId(),0);
        model.addAttribute("list",list);
        model.addAttribute("user",user);
        return "admin/myConpetition";
    }

    @RequestMapping("editMyContest")
    public String editMyContestInfo(int uid,int cid,Model model){
        List<Column_info> list = signUpService.listAllInfo(cid);
        model.addAttribute("list",list);
        return "admin/editRegistrationInformation";
    }

    /**
     *
     * @param session
     * @param contest
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("downLoad")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session, Contest contest, HttpServletRequest request) throws IOException {
        String attachmentId = String.valueOf(contest.getAttachment());
        String fileName = attachmentId + "_" + contest.getName() + ".rar";
        String oldName = contest.getName()+".rar";
        ServletContext context = session.getServletContext();
        String path = context.getRealPath("/File/");
        File file = new File(path+File.separator+fileName);

        if(!file.exists()){
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        if(getBrowser(request).equals("FF")){
            headers.setContentDispositionFormData("attachment", new String(oldName.getBytes("UTF-8"),"iso-8859-1"));
        }else{
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(oldName,"UTF-8"));
        }
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, httpStatus);
        return response;
    }

    @RequestMapping("getCharacterization")
    @ResponseBody
    public List<Contest> getTopContest(){
        List<Contest> list = homeService.getTopCharacterization();
        return list;
    }

    @RequestMapping(value = "getValue",produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String getColumn_value(int uid,int cid) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException{
        String value = signUpService.sychroData(uid, cid);
        return value;
    }

    @RequestMapping(value = "getAlternative")
    @ResponseBody
    public List<Alternative> getAlternative(int cid) throws ContestNotFoundException {
        List<Alternative> list = signUpService.listAlternativeByCid(cid);
        return list;
    }

    @RequestMapping("updateCv")
    @ResponseBody
    public String updateColumn_value(Column_value column_value,int custom){
        try{
            signUpService.update(column_value,custom);
        }catch (ContestNotFoundException e){
            return "false";
        }catch (UserNotFoundException e){
            return "false";
        }
        return "success";
    }




    private String getBrowser(HttpServletRequest request){
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        if(agent!=null){
            if(agent.indexOf("msie")>0) return "IE";
            else if(agent.indexOf("firefox")>0) return "FF";
            else if(agent.indexOf("safari")>0) return "SF";
        }
        return null;
    }
}
