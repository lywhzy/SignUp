package hbue.it.filter;

import hbue.it.pojo.User;
import hbue.it.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 登陆拦截器
 */
/*@WebFilter("/homePage.html")
@Component
@Order*/
public class UserLoginFilter implements Filter {

    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("开始拦截请求");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        // 如果是登录有关操作的，不拦截
        String path = request.getRequestURI();

        if (path.contains("homePage")){
            // 放行
            System.out.println("path:" + path);
            chain.doFilter(request,response);
            return;
        }

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        System.out.println(session.getAttribute("user"));
        // 验证session是否有该用户信息
        if (null != user){
            // 访问放行
            System.out.println("放行");
            chain.doFilter(request, response);
            return;
        }

        // session失效，查看cookie
        // 获取cookie，拿到账号密码进行判断，对了放行，并将用户对象存储到session中
        Cookie[] cookies = request.getCookies();
        String[] str = new String[10];
        if (null != cookies){
            for (Cookie c : cookies){
                if ("loginCookie".equals(c.getName())){
                    str = c.getValue().split("@");
                }
            }
        }

        // 验证cookie信息是否正确
        // UserService userService = new UserServiceImpl();
        User user1 = null;
        if (!"".equals(str[0]) && !"".equals(str[1]) && null != str[0] && null != str[1]){
            user1 = userService.selLogin(str[0], str[1]);
        }

        if (null != user1){
            System.out.println("放");
            request.getSession().setAttribute("user", user);
            chain.doFilter(request, response);
            return;
        }
    }

    @Override
    public void destroy() {

    }


}
