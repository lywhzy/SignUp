package hbue.it.listener;

import hbue.it.pojo.User;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;

@WebListener
public class SessionListener implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        // session销毁时，把loginMap键值对清除
        HttpSession session = httpSessionEvent.getSession();
        User user = (User) session.getAttribute("user");
        if (null != user){
            ServletContext application = session.getServletContext();
            @SuppressWarnings("unchecked")
            Map<String, Object> loginMap = (Map<String, Object>) application.getAttribute("loginMap");
            loginMap.remove(user.getId());
            application.setAttribute("loginMap", loginMap);
            session.removeAttribute("user");
        }
    }
}
