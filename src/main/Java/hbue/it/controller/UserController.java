package hbue.it.controller;

import hbue.it.pojo.*;
import hbue.it.service.Facade.ContestFacadeService;
import hbue.it.service.Facade.SignUpFacadeService;
import hbue.it.service.UserService;
import hbue.it.util.Page;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import hbue.it.util.JavaEmailSenderUtil;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController extends BaseController {
    @Autowired
    private ContestFacadeService contestFacadeService;

    @Autowired
    private SignUpFacadeService signUpFacadeService;

    @Autowired
    private UserService userService;

    @Autowired
    private Producer kaptchaProducer;


    @RequestMapping("listMyContest")
    public String listMyContest(Model model, int start) {
        User user = (User) session.getAttribute("user");
        List<Contest> list = contestFacadeService.listContestByid(user.getId(), start);
        model.addAttribute("list", list);
        Page page = new Page(list, start);
        model.addAttribute("page", page);
        return "admin/myConpetition";
    }

    @RequestMapping("editMyContest")
    public String editMyContestInfo(int cid, Model model, int status) {
        User user = (User) session.getAttribute("user");
        List<Column_info> list = signUpFacadeService.listAllInfo(cid);
        status = contestFacadeService.judgeUCStatus(user.getId(), cid, status);
        model.addAttribute("list", list);
        model.addAttribute("status", status);
        model.addAttribute("cid", cid);
        return "admin/editRegistrationInformation";
    }

    /**
     * @param contest
     * @return
     * @throws IOException
     */
    @RequestMapping("downLoad")
    public ResponseEntity<byte[]> testResponseEntity(Contest contest) throws IOException {
        String attachmentId = String.valueOf(contest.getAttachment());
        String fileName = attachmentId + "_" + contest.getName() + ".rar";
        String oldName = contest.getName() + ".rar";
        ServletContext context = session.getServletContext();
        String path = context.getRealPath("/File/");
        File file = new File(path + File.separator + fileName);

        if (!file.exists()) {
            return null;
        }

        HttpHeaders headers = new HttpHeaders();
        if (getBrowser().equals("FF")) {
            headers.setContentDispositionFormData("attachment", new String(oldName.getBytes("UTF-8"), "iso-8859-1"));
        } else {
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(oldName, "UTF-8"));
        }
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        HttpStatus httpStatus = HttpStatus.OK;
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, httpStatus);
        return response;
    }

    @RequestMapping("listContest")
    public String listContests(Model model, int start) {
        User user = (User) session.getAttribute("user");
        List<Contest> list = contestFacadeService.listContests(user, start);
        Page page = new Page(3, start, list);
        model.addAttribute("list", list);
        model.addAttribute("page", page);
        return "admin/Competitions";
    }


    private String getBrowser() {
        String agent = request.getHeader("USER-AGENT").toLowerCase();
        if (agent != null) {
            if (agent.indexOf("msie") > 0) return "IE";
            else if (agent.indexOf("firefox") > 0) return "FF";
            else if (agent.indexOf("safari") > 0) return "SF";
        }
        return null;
    }




    @RequestMapping(value = "/isLogin", method = RequestMethod.POST)
    @ResponseBody
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
            PrintWriter out = response.getWriter();
            user = (user == null ? user1 : user);
            System.out.println(user.getName());
            out.println(user.getName());
            out.flush();
            out.close();
            return "SUCCESS";
            // response.sendRedirect(request.getContextPath() + "");
        } else {
            System.out.println("未登录");
            // response.sendRedirect(request.getContextPath() + "/index.jsp");
            return "FAIL";
        }
    }

    @RequestMapping(value = "/Login", method = RequestMethod.POST)
    @ResponseBody
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
    @ResponseBody
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
                    loginMap.remove(user.getId());
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
    @ResponseBody
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

    // 使用Kaptcha框架
    @RequestMapping(value = "/Kaptcha")
    public ModelAndView handleRequest() {
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
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
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
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @RequestMapping(value = "/checkKaptcha", method = RequestMethod.POST)
    public String checkKaptcha() {
        // 获取传入的验证码
        String code = request.getParameter("kaptcha_code");
        if (code != null) {
            String sessionCode = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
            System.out.println(code);
            System.out.println(sessionCode);
            if (!sessionCode.isEmpty() && !code.equalsIgnoreCase(sessionCode)) {
                System.out.println("验证成功!");
                return "login";
            } else {
                System.out.println("验证失败!");
            }
        }
        return "login";
    }

    @RequestMapping(value = "/mail")
    @ResponseBody
    public String mailSucc() throws GeneralSecurityException, MessagingException {
        String toEMAIL = request.getParameter("email"); // 对方邮箱
        System.out.println(toEMAIL);
        String content = "您正在注册成为湖北经济学院比赛报名官网用户，欢迎您的加入！";
        int randNum = JavaEmailSenderUtil.sendCodeEmail(toEMAIL, content);
        session.setAttribute("randNum", randNum);
        return "/mailSucc";
    }

}
