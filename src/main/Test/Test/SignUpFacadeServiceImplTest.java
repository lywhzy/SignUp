package Test;

import hbue.it.pojo.Column_info;
import hbue.it.pojo.Column_value;
import hbue.it.service.Facade.SignUpFacadeService;
import hbue.it.service.Facade.impl.SignUpFacadeServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class SignUpFacadeServiceImplTest {

    @Test
    public void signUp() {
        Column_value column_value = new Column_value();
        column_value.setCid(1);
        column_value.setUid(1);
        column_value.setValue("leyu");

        ApplicationContext context = new ClassPathXmlApplicationContext("Spring/ApplicationContext.xml");
        SignUpFacadeService signUpFacadeService = context.getBean(SignUpFacadeServiceImpl.class);

        signUpFacadeService.signUp(column_value,1,0);
    }

    @Test
    public void sychro() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring/ApplicationContext.xml");
        SignUpFacadeService signUpFacadeService = context.getBean(SignUpFacadeServiceImpl.class);
        System.out.println(signUpFacadeService.sychroData(1,"手机号"));
        //System.out.println(signUpService.sychroData(1,1,"1"));
    }

    @Test
    public void update() {
    }

    @Test
    public void list(){
        ApplicationContext context = new ClassPathXmlApplicationContext("Spring/ApplicationContext.xml");
        SignUpFacadeService signUpFacadeService = context.getBean(SignUpFacadeServiceImpl.class);
        List<Column_info> list = signUpFacadeService.listAllInfo(1);
        list.forEach(column_info -> {System.out.println(column_info.getName());});

    }
}