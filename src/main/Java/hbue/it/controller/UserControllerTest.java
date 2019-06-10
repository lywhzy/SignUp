package hbue.it.controller;

import hbue.it.pojo.User;
import hbue.it.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.io.UnsupportedEncodingException;

@RunWith(value = SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring/ApplicationContext.xml"})
public class UserControllerTest {

    @Resource
    private UserService userService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void login() throws UnsupportedEncodingException {
        User user = userService.selLogin("wym", "123");
        if (user != null){
            System.out.println(1);
        }else {
            System.out.println(2);
        }
    }
}