package hbue.it.controller;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import hbue.it.pojo.User;
import hbue.it.service.UserService;
import hbue.it.service.UserServiceImpl;
import hbue.it.util.JavaEmailSenderUtil;
import hbue.it.util.RandomUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

@Controller
@Scope(value = "prototype")
@SessionAttributes("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;

    @RequestMapping(value = "/isLogin", method = RequestMethod.POST)
    @ResponseBody
    public String isLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        // 验证session中是否存在此用户
        User user = (User) request.getSession().getAttribute("user");
        // 验证是否包含自动登录的cookie
        Cookie[] cookies = request.getCookies();
        String username = request.getParameter("userName");
        String[] str = new String[10];
        User user1 = null;
        if (null != cookies){
            for (Cookie c : cookies){
                if ("loginCookie".equals(c.getName())){

                    str = c.getValue().split("@");
                    if (null != str && null != str[0] && str[1] != null){
                        try {
                            user1 = userService.selLogin(str[0], str[1]);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                }
            }
        }
        if (null != user || null != user1){
            System.out.println("已登录");
            PrintWriter out = response.getWriter();
            user = (user == null ? user1 : user);
            System.out.println(user.getName());
            out.println(user.getName());
            out.flush();
            out.close();
            return "SUCCESS";
            // response.sendRedirect(request.getContextPath() + "");
        }else {
            System.out.println("未登录");
            // response.sendRedirect(request.getContextPath() + "/index.jsp");
            return "FAIL";
        }
    }

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String username = request.getParameter("userName");
        String password = request.getParameter("password");
        User user = null;
        try {
            user = userService.selLogin(username, password);
        }catch (Exception e){
            e.printStackTrace();
        }

        HttpSession session = request.getSession();
        if (user != null){
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
            if (null == loginMap){
                loginMap = new HashMap<String, Object>();
            }
            // 查看application里的session id是否一致
            for (String key :loginMap.keySet()){
                if (user.getId() == Integer.parseInt(key)){
                    if (session.getId().equals(loginMap.get(key))){
                        System.out.println("同一地点已登录");
                        return request.getContextPath() + "/homePage.html?msg=" + URLEncoder.encode(username + "在同一地点重复登录", "UTF-8").toString();
                    }else {
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

        }else {
            session.setAttribute("msg", "用户名或密码错误!");
            System.out.println("用户名或密码错误!");
            // 跳转到失败页面
            // ...
            request.getRequestDispatcher("/login.html").forward(request, response);
            return null;
        }
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String outLogin(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession();
        if (null != session){
            try {
                // 清除session
                session.removeAttribute("user");
                ServletContext application = session.getServletContext();
                @SuppressWarnings("unchecked")
                Map<String, Object> loginMap = (Map<String, Object>) application.getAttribute("loginMap");
                User user = (User) session.getAttribute("user");
                loginMap.remove(user.getId());
                application.setAttribute("loginMap", loginMap);
                // 修改掉客户端cookie
                Cookie cookie = new Cookie("user", null);
                cookie.setMaxAge(0);
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "Login";
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public void insert(HttpServletRequest request, HttpServletResponse response, User user) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String name = request.getParameter("userName");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Integer level = Integer.parseInt(request.getParameter("level"));
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setEmail(email);
        user.setPassword(password);
        user.setLevel(level);
        int flag = userService.insertUser(user);
        // 获取验证码
        int trueRandNum = (int) request.getAttribute("randNum");
        String randNum = request.getParameter("idenCode");
        //if (trueRandNum == Integer.parseInt(randNum)){
            if (flag < 0){
                // 注册成功
                System.out.println("注册成功");
                response.sendRedirect(request.getContextPath() + "/homePage.html");
            }else {
                // 注册失败
                System.out.println("注册失败");
                request.getRequestDispatcher("").forward(request, response);
            }
        //}else {
        //    System.out.println("验证码错误");
        //    return;
        //}

    }

    // 使用Kaptcha框架
    @RequestMapping(value = "/Kaptcha")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        // 清除浏览器缓存
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        //Producer kaptchaProducer = null;
        // 获取Kaptcha验证的随机文本
        String capText = kaptchaProducer.createText();
        // 将图片放入会话中
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        BufferedImage bi = kaptchaProducer.createImage(capText);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            ImageIO.write(bi, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (out != null) {
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null){
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping(value = "/checkKaptcha", method = RequestMethod.POST)
    public String checkKaptcha(HttpServletRequest request){
        // 获取传入的验证码
        String code = request.getParameter("kaptcha_code");
        if (code != null){
            String sessionCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
            System.out.println(code);
            System.out.println(sessionCode);
            if (!sessionCode.isEmpty() && !code.equalsIgnoreCase(sessionCode)){
                System.out.println("验证成功!");
                return "login";
            }else {
                System.out.println("验证失败!");
            }
        }
        return "login";
    }

    @RequestMapping(value = "/mail")
    public String mailSucc(HttpServletRequest request) throws GeneralSecurityException, MessagingException {
        String toEMAIL = request.getParameter("email"); // 对方邮箱
        String TITLE = "邮箱注册成功ing了"; // 标题
        int randNum = RandomUtil.getRandNum(); // 4位验证码
        request.setAttribute("randNum", randNum);
        String CONTENT = toEMAIL + "你的验证码为" + randNum; // 内容
        JavaEmailSenderUtil.sendEmail(toEMAIL, TITLE, CONTENT);
        return "/mailSucc";
    }

}
