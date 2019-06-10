package Test;

import hbue.it.pojo.Contest;
import hbue.it.service.Facade.ContestFacadeService;
import hbue.it.service.Facade.impl.ContestFacadeServiceImpl;
import hbue.it.util.Page;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class ContestFacadeServiceImplTest {

    @Test
    public void listContestByid() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring/ApplicationContext.xml");
        ContestFacadeService contestFacadeService = applicationContext.getBean(ContestFacadeServiceImpl.class);
        List<Contest> list = contestFacadeService.listContestByid(1,0);
        System.out.println(new Page(list,0).getFinalPage());
        System.out.println(new Page(list,0).getStart());
        System.out.println(new Page(list,0).getCount());
        System.out.println(new Page(list,0).ishasNext());
        System.out.println(new Page(list,0).ishasPre());
        list.forEach(contest ->{System.out.println(contest.toString());});
    }

    @Test
    public void deleteContest() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("Spring/ApplicationContext.xml");
        ContestFacadeService contestFacadeService = applicationContext.getBean(ContestFacadeServiceImpl.class);
        contestFacadeService.deleteContest(1,1);
    }
}