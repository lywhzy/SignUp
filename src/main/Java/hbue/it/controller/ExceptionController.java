package hbue.it.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(RuntimeException.class)
    public ModelAndView exception(Exception ex){
        ModelAndView modelAndView = new ModelAndView("exception");
        modelAndView.addObject("ex",ex.getStackTrace()[0].toString());
        modelAndView.addObject("message",ex.getMessage());
        return modelAndView;
    }
}
