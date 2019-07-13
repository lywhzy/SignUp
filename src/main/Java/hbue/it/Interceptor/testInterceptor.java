package hbue.it.Interceptor;

import hbue.it.exception.UserNotFoundException;
import hbue.it.pojo.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;

public class testInterceptor implements HandlerInterceptor {

    static HashSet<String> set = new HashSet<String>();


    static{
        set.add("getTopContest");
        set.add("isLogin");
        set.add("login");
        set.add("insert");
        set.add("mailSucc");
        set.add(null);
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("user");
        String methodName = null;
        if(o instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod) o;
            methodName = method.getMethod().getName();
        }
        System.out.println(methodName);
        if(user == null){
            if(set.contains(methodName))return true;
            else throw new UserNotFoundException();
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
