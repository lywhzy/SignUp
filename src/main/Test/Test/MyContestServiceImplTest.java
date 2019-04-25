package Test;

import hbue.it.pojo.Contest;
import hbue.it.service.MyContestService;
import hbue.it.service.impl.MyContestServiceImpl;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

public class MyContestServiceImplTest {

    @Test
    public void listContestByid() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring/ApplicationContext.xml");
        MyContestService myContestService = applicationContext.getBean(MyContestServiceImpl.class);
        List<Contest> list = myContestService.listContestByid(1,0);
        list.forEach(contest ->{System.out.println(contest.toString());});
    }

    @Test
    public void deleteContest() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring/ApplicationContext.xml");
        MyContestService myContestService = applicationContext.getBean(MyContestServiceImpl.class);
        myContestService.deleteContest(1,1);
    }
}