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
import hbue.it.service.UserService;
import hbue.it.util.JavaEmailSenderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class RestUserController extends  BaseController{

    @Autowired
    private HomeService homeService;
    @Autowired
    private SignUpFacadeService signUpFacadeService;
    @Autowired
    private ColumnService columnService;
    @Autowired
    private UserService userService;

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

    @RequestMapping(value = "/isLogin", method = RequestMethod.POST)
    public String isLogin() throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 验证session中是否存在此用户
        User user = (User) session.getAttribute("user");
        // 验证是否包含自动登录的cookie
        Cookie[] cookies = request.getCookies();
        //String username = request.getParameter("userName");
        String[] str = new String[10];
        User user1 = null;
        if (null != cookies) {
            for (Cookie c : cookies) {
                if ("loginCookie".equals(c.getName())) {

                    str = c.getValue().split("@");
                    if (null != str && null != str[0] && str[1] != null) {
                        try {
                            user1 = userService.selLogin(str[0], str[1]);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        if (null != user) {
            System.out.println("session");
        }
        if (null != user) {
            System.out.println("cookie");
        }
        if (null != user || null != user1) {
            System.out.println("已登录");
//            PrintWriter out = response.getWriter();
            user = (user == null ? user1 : user);
            System.out.println(user.getName());
            session.setAttribute("user",user);
//            out.println(user.getName());
//            out.flush();
//            out.close();
            return "SUCCESS";
            // response.sendRedirect(request.getContextPath() + "");
        } else {
            System.out.println("未登录");
            // response.sendRedirect(request.getContextPath() + "/index.jsp");
            return "FAIL";
        }
    }

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public String login(Model model) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username + password);
        User user = null;
        try {
            user = userService.selLogin(username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (user != null) {
            // shiro spring整合异常，暂弃之
            /*Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated() && subject.isRemembered()){
                Object principal = subject.getPrincipal();
                if (null != principal){
                    UsernamePasswordToken token = new UsernamePasswordToken(user.getName(), user.getPassword());
                    token.setRememberMe(true);
                    subject.login(token);//登录
                }
            }*/

            ServletContext application = session.getServletContext();
            @SuppressWarnings("unchecked")
            Map<String, Object> loginMap = (Map<String, Object>) application.getAttribute("loginMap");
            if (null == loginMap) {
                loginMap = new HashMap<String, Object>();
            }
            // 查看application里的session id是否一致
            for (String key : loginMap.keySet()) {
                if (user.getId() == Integer.parseInt(key)) {
                    if (session.getId().equals(loginMap.get(key))) {
                        System.out.println("同一地点已登录");
                        return request.getContextPath() + "/homePage.html?msg=" + URLEncoder.encode(username + "在同一地点重复登录", "UTF-8").toString();
                    } else {
                        System.out.println("异地登录");
                        // 执行。。。
                        return request.getContextPath() + "/homePage.html?msg=" + URLEncoder.encode(username + "异地登录", "UTF-8").toString();
                    }
                }
            }
            loginMap.put(user.getId().toString(), session.getId());
            application.setAttribute("loginMap", loginMap);
            // 将用户保存在session
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(10 * 60);

            // 生成cookie保存登录信息
            Cookie cookie = new Cookie("loginCookie", username + "@" + password);
            cookie.setMaxAge(60 * 60 * 24 * 7);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);

            System.out.println("登陆成功!");
            // 跳转到主页,返回字符串success
            // ...
            response.sendRedirect(request.getContextPath() + "/homePage.jsp");
            return null;

        } else {
            session.setAttribute("msg", "用户名或密码错误!");
            System.out.println("用户名或密码错误!");
            // 跳转到失败页面
            // ...
            request.getRequestDispatcher("/login.html").forward(request, response);
            return null;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String outLogin() {
        if (null != session) {
            try {
                User user = (User) session.getAttribute("user");
                // 清除session
                session.removeAttribute("user");
                if ((User) session.getAttribute("user") != null) {
                    System.out.println("2");
                }
                ServletContext application = session.getServletContext();
                @SuppressWarnings("unchecked")
                Map<String, Object> loginMap = (Map<String, Object>) application.getAttribute("loginMap");

                if (null != user) {
                    loginMap.remove(user.getId().toString());
                }
                application.setAttribute("loginMap", loginMap);
                // 修改掉客户端cookie
                Cookie cookie = new Cookie("user", null);
                cookie.setMaxAge(0);
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);

                Cookie[] cookies = request.getCookies();
                String[] str = null;
                for (Cookie c : cookies) {
                    if ("loginCookie".equals(c.getName())) {
                        str = c.getValue().split("@");
                        if (str[0].equals(user.getName())) {
                            c.setMaxAge(0);
                            c.setPath(request.getContextPath());
                            response.addCookie(c);

                            if (null != c) {
                                System.out.println(c.getValue());
                            }
                        }

                    }
                }

                for (Cookie c : cookies) {
                    if ("loginCookie".equals(c.getName())) {
                        str = c.getValue().split("@");
                        if (str[0].equals(user.getName())) {
                            System.out.println("cookie清除");
                        }

                    }
                }

                return "SUCCESS";
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public String insert(User user) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("username");
        String phoneNumber = request.getParameter("phone");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setPassword(password);
        user.setLevel(0);

        // 获取验证码
        int trueRandNum = (int) request.getSession().getAttribute("randNum");
        String randNum = request.getParameter("code");
        if (trueRandNum == Integer.parseInt(randNum)) {
            int flag = userService.insertUser(user);
            if (flag > 0) {
                // 注册成功
                System.out.println("注册成功");

                //response.sendRedirect(request.getContextPath() + "/homePage.html");
                return "SUCCESS";
            } else {
                // 注册失败
                System.out.println("注册失败");
                request.getRequestDispatcher("").forward(request, response);
                return "FAIL";
            }
        } else {
            System.out.println("验证码错误");
            return "ERROR";
        }

    }

    @RequestMapping(value = "/mail")
    public String mailSucc() throws GeneralSecurityException, MessagingException {
        String toEMAIL = request.getParameter("email"); // 对方邮箱
        System.out.println(toEMAIL);
        String content = "您正在注册成为湖北经济学院比赛报名官网用户，欢迎您的加入！";
        int randNum = JavaEmailSenderUtil.sendCodeEmail(toEMAIL, content);
        session.setAttribute("randNum", randNum);
        return "/mailSucc";
    }
}
