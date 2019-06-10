package hbue.it.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class BaseController {

    protected HttpServletRequest request;
    protected HttpSession session;
    protected HttpServletResponse response;

    @ModelAttribute
    public void setRequest(HttpServletRequest request,HttpServletResponse response){
        this.request = request;
        this.session = request.getSession();
        this.response = response;
    }


}
